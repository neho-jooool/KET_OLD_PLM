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

import wt.fc.InvalidAttributeException;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

/**
 * 
 * <p>
 * Use the <code>newKETPartAttribute</code> static factory method(s), not the <code>KETPartAttribute</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "attrCode", type = String.class), @GeneratedProperty(name = "attrName", type = String.class),
        @GeneratedProperty(name = "attrEName", type = String.class), @GeneratedProperty(name = "attrOotbName", type = String.class),
        @GeneratedProperty(name = "columnName", type = String.class), @GeneratedProperty(name = "attrInputType", type = String.class),
        @GeneratedProperty(name = "attrCodeType", type = String.class),
        @GeneratedProperty(name = "attrMultiSelect", type = boolean.class, initialValue = "false"),
        @GeneratedProperty(name = "attrDesc", type = String.class, constraints = @PropertyConstraints(upperLimit = 2000)),
        @GeneratedProperty(name = "sendErp", type = boolean.class, initialValue = "false"),
        @GeneratedProperty(name = "receiveErp", type = boolean.class, initialValue = "false"),
        @GeneratedProperty(name = "erpDesc", type = String.class, constraints = @PropertyConstraints(upperLimit = 2000)),
        @GeneratedProperty(name = "useNumbering", type = boolean.class, initialValue = "false") })
public class KETPartAttribute extends _KETPartAttribute {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETPartAttribute
     * @exception wt.util.WTException
     **/
    public static KETPartAttribute newKETPartAttribute() throws WTException {

	KETPartAttribute instance = new KETPartAttribute();
	instance.initialize();
	return instance;
    }

    /**
     * Supports initialization, following construction of an instance. Invoked by "new" factory having the same signature.
     * 
     * @exception wt.util.WTException
     **/
    protected void initialize() throws WTException {

    }

    /**
     * Gets the value of the attribute: IDENTITY. Supplies the identity of the object for business purposes. The identity is composed of
     * name, number or possibly other attributes. The identity does not include the type of the object.
     * 
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayIdentifier(object) to return a localizable equivalent of getIdentity(). To return a
     *             localizable value which includes the object type, use IdentityFactory.getDisplayIdentity(object). Other alternatives are
     *             ((WTObject)obj).getDisplayIdentifier() and ((WTObject)obj).getDisplayIdentity().
     * 
     * @return String
     **/
    public String getIdentity() {

	return null;
    }

    /**
     * Gets the value of the attribute: TYPE. Identifies the type of the object for business purposes. This is typically the class name of
     * the object but may be derived from some other attribute of the object.
     * 
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayType(object) to return a localizable equivalent of getType(). Another alternative is
     *             ((WTObject)obj).getDisplayType().
     * 
     * @return String
     **/
    public String getType() {

	return null;
    }

    @Override
    public void checkAttributes() throws InvalidAttributeException {

    }

}
