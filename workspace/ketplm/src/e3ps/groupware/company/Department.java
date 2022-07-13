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

package e3ps.groupware.company;

import java.io.IOException;
import java.io.ObjectInput;
import java.sql.Timestamp;

import wt.fc.InvalidAttributeException;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import e3ps.common.impl.Tree;
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency

/**
 * 
 * <p>
 * Use the <code>newDepartment</code> static factory method(s), not the <code>Department</code> constructor, to construct instances of this
 * class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(interfaces = { Tree.class }, versions = { -6953212342971754756L }, properties = {
        @GeneratedProperty(name = "name", type = String.class), @GeneratedProperty(name = "enName", type = String.class),
        @GeneratedProperty(name = "code", type = String.class, javaDoc = "부서 코드", columnProperties = @ColumnProperties(unique = true)),
        @GeneratedProperty(name = "centerCode", type = String.class),
        @GeneratedProperty(name = "sort", type = int.class, initialValue = "0"),
        @GeneratedProperty(name = "abailableStart", type = Timestamp.class, javaDoc = "유효시작일≪≫"),
        @GeneratedProperty(name = "abailableEnd", type = Timestamp.class, javaDoc = "유효종료일"),
        @GeneratedProperty(name = "ccCode", type = String.class), @GeneratedProperty(name = "enabled", type = boolean.class) })
public class Department extends _Department {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return Department
     * @exception wt.util.WTException
     **/
    public static Department newDepartment() throws WTException {

	Department instance = new Department();
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

    /**
     * Reads the non-transient fields of this class from an external source.
     * 
     * @param input
     * @param readSerialVersionUID
     * @param superDone
     * @return boolean
     * @exception java.io.IOException
     * @exception java.lang.ClassNotFoundException
     **/
    boolean readVersion_6953212342971754756L(ObjectInput input, long readSerialVersionUID, boolean superDone) throws IOException,
	    ClassNotFoundException {

	abailableEnd = (Timestamp) input.readObject();
	abailableStart = (Timestamp) input.readObject();
	ccCode = (String) input.readObject();
	code = (String) input.readObject();
	enabled = input.readBoolean();
	name = (String) input.readObject();
	parentReference = (ObjectReference) input.readObject();
	sort = input.readInt();
	thePersistInfo = (PersistInfo) input.readObject();

	return true;
    }

}
