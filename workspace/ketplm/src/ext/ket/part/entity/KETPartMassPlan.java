/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.part.entity;

import java.sql.Timestamp;

import wt.fc.InvalidAttributeException;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.OwnPersistable;

/**
 * 자재마스터 양산시작일 관리
 * <p>
 * Use the <code>newKETPartMassPlan</code> static factory method(s), not the <code>KETPartMassPlan</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(interfaces = { OwnPersistable.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {

@GeneratedProperty(name = "partNo", type = String.class, javaDoc = "품번"),
        @GeneratedProperty(name = "partName", type = String.class, javaDoc = "품명"),
        @GeneratedProperty(name = "pjtNo", type = String.class, javaDoc = "대표 프로젝트번호"),
        @GeneratedProperty(name = "pjtName", type = String.class, javaDoc = "프로젝트명"),
        @GeneratedProperty(name = "pjtNos", type = String.class, javaDoc = "연관프로젝트번호"),
        @GeneratedProperty(name = "processGb", type = String.class, javaDoc = "Pilot,양산"),
        @GeneratedProperty(name = "ecoNo", type = String.class, javaDoc = "ECO번호"),
        @GeneratedProperty(name = "masStartDate", type = Timestamp.class, javaDoc = "양산시작일"),
        @GeneratedProperty(name = "pjtEndDate", type = Timestamp.class, javaDoc = "프로젝트종료일"),
        @GeneratedProperty(name = "dr6EndDate", type = Timestamp.class, javaDoc = "DR6종료일"),
        @GeneratedProperty(name = "ecoApproveDate", type = Timestamp.class, javaDoc = "최초ECO승인일(양산이관)"),
        @GeneratedProperty(name = "modifyName", type = String.class, javaDoc = "수정자이름"),
        @GeneratedProperty(name = "modifyCode", type = String.class, javaDoc = "수정자아이디"),
        @GeneratedProperty(name = "modifyDeptName", type = String.class, javaDoc = "수정자부서"),
        @GeneratedProperty(name = "modifyDeptCode", type = String.class, javaDoc = "수정자부서코드"),
        @GeneratedProperty(name = "bigo", type = String.class, javaDoc = "비고", constraints = @PropertyConstraints(upperLimit = 4000)), }, foreignKeys = { @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "partMaster", type = wt.part.WTPartMaster.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETPartMassPlan", cardinality = Cardinality.ONE)) })
public class KETPartMassPlan extends _KETPartMassPlan {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETPartMassPlan
     * @exception wt.util.WTException
     **/
    public static KETPartMassPlan newKETPartMassPlan() throws WTException {

	KETPartMassPlan instance = new KETPartMassPlan();
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
