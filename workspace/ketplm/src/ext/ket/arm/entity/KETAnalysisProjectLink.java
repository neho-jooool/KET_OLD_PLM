/* bcwti�����Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.�����This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.�����ecwti
 */

package ext.ket.arm.entity;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import e3ps.project.E3PSProject;

/**
 * 
 * <p>
 * Use the <code>newKETAnalysisProjectLink</code> static factory method(s), not the <code>KETAnalysisProjectLink</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC, roleA = @GeneratedRole(name = "project", type = E3PSProject.class, cardinality = Cardinality.ONE), roleB = @GeneratedRole(name = "master", type = KETAnalysisRequestMaster.class, cascade = false), tableProperties = @TableProperties(tableName = "KETAnalysisProjectLink"))
public class KETAnalysisProjectLink extends _KETAnalysisProjectLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param project
     * @param master
     * @return KETAnalysisProjectLink
     * @exception wt.util.WTException
     **/
    public static KETAnalysisProjectLink newKETAnalysisProjectLink(E3PSProject project, KETAnalysisRequestMaster master) throws WTException {

	KETAnalysisProjectLink instance = new KETAnalysisProjectLink();
	instance.initialize(project, master);
	return instance;
    }

}
