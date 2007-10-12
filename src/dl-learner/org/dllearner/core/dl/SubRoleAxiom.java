package org.dllearner.core.dl;

import java.util.Map;

public class SubRoleAxiom extends RBoxAxiom {

	private AtomicRole role;
	private AtomicRole subRole;
	
	public SubRoleAxiom(AtomicRole subRole, AtomicRole role) {
		this.role = role;
		this.subRole = subRole;
	}
	
	public AtomicRole getRole() {
		return role;
	}

	public AtomicRole getSubRole() {
		return subRole;
	}

	public int getLength() {
		return 1 + role.getLength() + subRole.getLength();
	}
		
	public String toString(String baseURI, Map<String,String> prefixes) {
		return "Subrole(" + subRole.toString(baseURI, prefixes) + "," + role.toString(baseURI, prefixes) + ")";
	}		
}
