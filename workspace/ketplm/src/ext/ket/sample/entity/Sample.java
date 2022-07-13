/*
 * bcwti
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 * ecwti
 */

package ext.ket.sample.entity;

import wt.access.AccessControlled;
import wt.content.FormatContentHolder;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * <p>
 * Use the <code>newSample</code> static factory method(s), not the <code>Sample</code> constructor, to construct instances of this class. Instances must be constructed using the
 * static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * @version 1.0
 **/

@GenAsPersistable(
	interfaces = { FormatContentHolder.class, AccessControlled.class },
	serializable = Serialization.EXTERNALIZABLE_BASIC,
	properties = { @GeneratedProperty(name = "num", type = int.class, initialValue = "0", javaDoc = "??"), @GeneratedProperty(name = "name", type = String.class, javaDoc = "??"),
		@GeneratedProperty(name = "title", type = String.class, javaDoc = "??"),
		@GeneratedProperty(name = "webEditor", type = Object.class, javaDoc = "???? Data", columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),
		@GeneratedProperty(name = "webEditorText", type = Object.class, javaDoc = "???? Text", columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)) },
	tableProperties = @TableProperties(tableName = "KETSAMPLE"))
public class Sample extends _Sample {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return Sample
     * @exception wt.util.WTException
     **/
    public static Sample newSample() throws WTException {

	Sample instance = new Sample();
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
     * Gets the value of the attribute: IDENTITY. Supplies the identity of the object for business purposes. The identity is composed of name, number or possibly other attributes.
     * The identity does
     * not include the type of the object. <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayIdentifier(object) to return a localizable equivalent of getIdentity(). To return a localizable value which includes the
     *             object type, use
     *             IdentityFactory.getDisplayIdentity(object). Other alternatives are ((WTObject)obj).getDisplayIdentifier() and ((WTObject)obj).getDisplayIdentity().
     * @return String
     **/
    public String getIdentity() {

	return null;
    }

    /**
     * Gets the value of the attribute: TYPE. Identifies the type of the object for business purposes. This is typically the class name of the object but may be derived from some
     * other attribute of
     * the object. <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayType(object) to return a localizable equivalent of getType(). Another alternative is ((WTObject)obj).getDisplayType().
     * @return String
     **/
    public String getType() {

	return null;
    }

    @Override
    public void checkAttributes() throws InvalidAttributeException {

    }

}
