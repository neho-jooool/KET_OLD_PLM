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

package ext.ket.part.entity;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newKETPartClassAttrLink</code> static factory method(s), not the <code>KETPartClassAttrLink</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "essential", type = boolean.class, initialValue = "false"),
        @GeneratedProperty(name = "checked", type = boolean.class, initialValue = "false"),
        @GeneratedProperty(name = "indexRow", type = int.class), @GeneratedProperty(name = "indexCol", type = int.class),
        @GeneratedProperty(name = "hpYn", type = boolean.class, initialValue = "false"),
        @GeneratedProperty(name = "indexSort", type = int.class) }, roleA = @GeneratedRole(name = "attr", type = KETPartAttribute.class), roleB = @GeneratedRole(name = "classific", type = KETPartClassification.class), tableProperties = @TableProperties(tableName = "KETPartClassAttrLink"))
public class KETPartClassAttrLink extends _KETPartClassAttrLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param attr
     * @param classific
     * @return KETPartClassAttrLink
     * @exception wt.util.WTException
     **/
    public static KETPartClassAttrLink newKETPartClassAttrLink(KETPartAttribute attr, KETPartClassification classific) throws WTException {

	KETPartClassAttrLink instance = new KETPartClassAttrLink();
	instance.initialize(attr, classific);
	return instance;
    }

}
