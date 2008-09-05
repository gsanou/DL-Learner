/**
 * Copyright (C) 2007-2008, Jens Lehmann
 *
 * This file is part of DL-Learner.
 *
 * DL-Learner is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * DL-Learner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

package org.dllearner.core.configuration;

import org.dllearner.core.ComponentManager;
import org.dllearner.core.KnowledgeSource;
import org.dllearner.core.config.ConfigEntry;
import org.dllearner.core.configuration.Configurator;
import org.dllearner.reasoning.OWLAPIReasoner;

/**
* automatically generated, do not edit manually
**/
@SuppressWarnings("unused")
public class OWLAPIReasonerConfigurator extends Configurator {

private boolean reinitNecessary = false;
private OWLAPIReasoner OWLAPIReasoner;
private String reasonerType = "pellet";

public OWLAPIReasonerConfigurator (OWLAPIReasoner OWLAPIReasoner){
this.OWLAPIReasoner = OWLAPIReasoner;
}

/**
**/
public static OWLAPIReasoner getOWLAPIReasoner (ComponentManager cm, KnowledgeSource knowledgeSource ) {
OWLAPIReasoner component = cm.reasoner(OWLAPIReasoner.class, knowledgeSource );
return component;
}

@SuppressWarnings({ "unchecked" })
public <T> void applyConfigEntry(ConfigEntry<T> entry){
String optionName = entry.getOptionName();
if(false){//empty block 
}else if (optionName.equals("reasonerType")){
reasonerType = (String)  entry.getValue();
}
}

/**
* option name: reasonerType
* FaCT++ or Pellet
* default value: pellet
**/
public String getReasonerType ( ) {
return this.reasonerType;
}

/**
* option name: reasonerType
* FaCT++ or Pellet
* default value: pellet
**/
public void setReasonerType ( ComponentManager cm, String reasonerType) {
cm.applyConfigEntry(OWLAPIReasoner, "reasonerType", reasonerType);
}

public boolean isReinitNecessary(){
return reinitNecessary;
}


}
