package org.dllearner.core.dl;

import java.util.Map;

/**
 * Interface for all elements of the knowledge base.
 * 
 * @author Jens Lehmann
 *
 */
public interface KBElement {
	
	public int getLength();
	
    public String toString(String baseURI, Map<String,String> prefixes);
}
