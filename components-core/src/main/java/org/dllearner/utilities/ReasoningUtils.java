package org.dllearner.utilities;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dllearner.core.AbstractReasonerComponent;
import org.dllearner.core.Component;
import org.dllearner.learningproblems.AccMethodTwoValued;
import org.dllearner.learningproblems.AccMethodTwoValuedApproximate;
import org.dllearner.reasoning.SPARQLReasoner;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;

import com.google.common.collect.Sets;

public class ReasoningUtils implements Component {
	
	public class CoverageCount {
		public int trueCount;
		public int falseCount;
		public int total;
	};
	
	public class Coverage extends CoverageCount {
		public SortedSet<OWLIndividual> trueSet = new TreeSet<>();
		public SortedSet<OWLIndividual> falseSet = new TreeSet<>();
	}

	private AbstractReasonerComponent reasoner;

	public ReasoningUtils(AbstractReasonerComponent reasoner) {
		this.reasoner = reasoner;
	}
	
	@SafeVarargs
	public final Coverage[] getCoverage(OWLClassExpression concept, Set<OWLIndividual>... sets) {
		Coverage[] rv = new Coverage [ sets.length ];

		if(reasoner instanceof SPARQLReasoner &&
				!((SPARQLReasoner)reasoner).isUseSingleTypeChecks()) {
			if (((SPARQLReasoner)reasoner).isUseValueLists()) {
				for (int i = 0; i < sets.length; ++i) {
					SortedSet<OWLIndividual> trueSet = reasoner.hasType(concept, sets[i]);

					rv[i] = new Coverage();
					rv[i].total = sets[i].size();

					rv[i].trueSet.addAll(trueSet);
					rv[i].falseSet.addAll(Sets.difference(sets[i], trueSet));

					rv[i].trueCount = rv[i].trueSet.size();
					rv[i].falseCount = rv[i].falseSet.size();
				}
			} else {
				SortedSet<OWLIndividual> individuals = reasoner.getIndividuals(concept);
				for (int i = 0; i < sets.length; ++i) {
					rv[i] = new Coverage();
					rv[i].total = sets[i].size();

					rv[i].trueSet.addAll(Sets.intersection(sets[i], individuals));
					rv[i].falseSet.addAll(Sets.difference(sets[i], individuals));

					rv[i].trueCount = rv[i].trueSet.size();
					rv[i].falseCount = rv[i].falseSet.size();
				}
			}
		} else {
			for (int i = 0; i < sets.length; ++i) {
				rv[i] = new Coverage();
				rv[i].total = sets[i].size();

				for (OWLIndividual example : sets[i]) {
					if (getReasoner().hasType(concept, example)) {
						rv[i].trueSet.add(example);
					} else {
						rv[i].falseSet.add(example);
					}
				}

				rv[i].trueCount = rv[i].trueSet.size();
				rv[i].falseCount = rv[i].falseSet.size();
			}
		}
		return rv;
	}

	@SafeVarargs
	public final CoverageCount[] getCoverageCount(OWLClassExpression concept,
												  Set<OWLIndividual>... sets) {
		CoverageCount[] rv = new CoverageCount [ sets.length ];

		if(reasoner instanceof SPARQLReasoner &&
				!((SPARQLReasoner)reasoner).isUseSingleTypeChecks()) {
			if (((SPARQLReasoner)reasoner).isUseValueLists()) {

				for (int i = 0; i < sets.length; ++i) {
					int trueCount = ((SPARQLReasoner) reasoner).getIndividualsCount(concept, sets[i]);

					rv[i] = new CoverageCount();
					rv[i].total = sets[i].size();

					rv[i].trueCount = trueCount;
					rv[i].falseCount = sets[i].size()- trueCount;
				}
			} else {
				SortedSet<OWLIndividual> individuals = reasoner.getIndividuals(concept);
				for (int i = 0; i < sets.length; ++i) {
					rv[i] = new CoverageCount();
					rv[i].total = sets[i].size();
				
					rv[i].trueCount  = Sets.intersection(sets[i], individuals).size();
					rv[i].falseCount = Sets.difference(sets[i], individuals).size();
				}
			}
		} else {
			for (int i = 0; i < sets.length; ++i) {
				rv[i] = new CoverageCount();
				rv[i].total = sets[i].size();

				for (OWLIndividual example : sets[i]) {
					if (getReasoner().hasType(concept, example)) {
						++rv[i].trueCount;
					} else {
						++rv[i].falseCount;
					}
				}
			}
		}
		return rv;
	}

	public double getAccuracyOrTooWeak2(AccMethodTwoValued accuracyMethod, OWLClassExpression description, Set<OWLIndividual> positiveExamples,
			Set<OWLIndividual> negativeExamples, double noise) {
		if (accuracyMethod instanceof AccMethodTwoValuedApproximate) {
			return ((AccMethodTwoValuedApproximate) accuracyMethod).getAccApprox2(description, positiveExamples, negativeExamples, noise);
		} else {
			CoverageCount[] cc = this.getCoverageCount(description, positiveExamples, negativeExamples);
			return accuracyMethod.getAccOrTooWeak2(cc[0].trueCount, cc[0].falseCount, cc[1].trueCount, cc[1].falseCount, noise);
		}
	}

	@Override
	public void init() {
	}

	public AbstractReasonerComponent getReasoner() {
		return reasoner;
	}

	public void setReasoner(AbstractReasonerComponent reasoner) {
		this.reasoner = reasoner;
	}

}
