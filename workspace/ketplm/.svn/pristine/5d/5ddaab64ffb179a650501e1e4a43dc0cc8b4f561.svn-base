/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package ext.ket.project.program.entity;

import wt.util.WTException;
import wt.vc.ObjectToVersionLink;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import e3ps.project.ProductProject;

/**
 * 
 * <p>
 * Use the <code>newKETProgramProjectLink</code> static factory method(s), not the <code>KETProgramProjectLink</code> constructor, to construct instances of this class. Instances must be constructed
 * using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToVersionLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "isBase", type = boolean.class, initialValue = "false", javaDoc = "기준 프로그램"),
        @GeneratedProperty(name = "isCheckedEvent", type = Boolean.class, initialValue = "false", javaDoc = "일정 확인") }, roleA = @GeneratedRole(name = "project", type = ProductProject.class), roleB = @GeneratedRole(name = "program", type = KETProgramSchedule.class, cardinality = Cardinality.ZERO_TO_ONE), tableProperties = @TableProperties(tableName = "KETProgramProjectLink"))
public class KETProgramProjectLink extends _KETProgramProjectLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param project
     * @param program
     * @return KETProgramProjectLink
     * @exception wt.util.WTException
     **/
    public static KETProgramProjectLink newKETProgramProjectLink(ProductProject project, KETProgramSchedule program) throws WTException {

	KETProgramProjectLink instance = new KETProgramProjectLink();
	instance.initialize(project, program);
	return instance;
    }

}
