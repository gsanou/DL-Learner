/**
 * 
 */
package org.dllearner.examples;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.jena.riot.Lang;
import org.dllearner.algorithms.celoe.CELOE;
import org.dllearner.algorithms.qtl.impl.QueryTreeFactory;
import org.dllearner.algorithms.qtl.impl.QueryTreeFactoryBase;
import org.dllearner.cli.CLI;
import org.dllearner.cli.CrossValidation;
import org.dllearner.core.AbstractCELA;
import org.dllearner.core.AbstractClassExpressionLearningProblem;
import org.dllearner.core.AbstractKnowledgeSource;
import org.dllearner.core.AbstractReasonerComponent;
import org.dllearner.core.ComponentInitException;
import org.dllearner.core.KnowledgeSource;
import org.dllearner.kb.OWLAPIOntology;
import org.dllearner.kb.OWLFile;
import org.dllearner.learningproblems.ClassLearningProblem;
import org.dllearner.reasoning.ClosedWorldReasoner;
import org.dllearner.refinementoperators.RhoDRDown;
import org.dllearner.utilities.OwlApiJenaUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

import com.google.common.collect.Sets;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.XSD;

import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
//import ////uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxOWLObjectRendererImpl;

/**
 * A basic example how to use DL-Learner.
 * 
 * Knowledge base: a family ontology
 * Target Concept: father
 * 
 * @author Lorenz Buehmann
 *
 */
public class TenFold {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
//		ToStringRenderer.getInstance().setRenderer(new ManchesterOWLSyntaxOWLObjectRendererImpl());
//		String conffileName="../examples/carcinogenesis/tenfold.conf";
//		File file = new File("../examples/carcinogenesis/carcinogenesis.owl");
		
		String conffileName="../examples/mutagenesis/train2.conf";
		File file = new File("../examples/mutagenesis/mutagenesis.owl");
	////////////////////////////CONF FILE //////////////////////////////////
		
		
		//String conffileName ="../examples/lymphography/lymphography_Class3.conf";
		//String conffileName ="../UCI/mush2.conf";
		File confFile= new File(conffileName);
		CLI cl = new CLI(confFile);
		cl.init();
		


		
		
		
		// read Owl file 
		KnowledgeSource ks = cl.getKnowledgeSource();
		ks.init();
		// setup the reasoner
		ClosedWorldReasoner rc = new ClosedWorldReasoner(ks);
		rc.init();
		//create the learning problem
		AbstractClassExpressionLearningProblem lp = cl.getLearningProblem();
		lp.setReasoner(rc);
		lp.init();
		
		
		
		
		CELOE alg = new CELOE(cl.getLearningProblem(), cl.getReasonerComponent());
		alg.setMaxExecutionTimeInSeconds(20);
		alg.setWriteSearchTree(true);
		alg.setSearchTreeFile("/tmp/dllearner/search-treel.log");
		alg.setReplaceSearchTree(true);
		
//		/////////// Ignoring a concept //////////////
//		OWLClassImpl cls1 = new OWLClassImpl(IRI.create("http://dllearner.org/mushroom/canEat"));
//		Set<OWLClass> ignoredConcepts = Sets.newHashSet();
//		ignoredConcepts.add(cls1);
//		alg.setIgnoredConcepts(ignoredConcepts);
		////////////////////////////////////////
		alg.init();
		
		////////////////////////////////////////////////////////////

		
		
		
		
		//new CrossValidation(cl.getLearningAlgorithm(),cl.getLearningProblem(),cl.getReasonerComponent(),10,true,true,"../Dtree/Folds/Mush");
		//new CrossValidation(cl.getLearningAlgorithm(),cl.getLearningProblem(),cl.getReasonerComponent(),10,true);
		new CrossValidation( alg,  lp, rc, 10, false) ;
					

	}

}
