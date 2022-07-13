/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.project.purchase.entity;

import java.sql.Timestamp;

import wt.content.ContentHolder;
import wt.enterprise.Managed;
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

/**
 * 외주제작 구매품 일정관리
 * <p>
 * Use the <code>newKETPurchaseProject</code> static factory method(s), not the <code>KETPurchaseProject</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = Managed.class, interfaces = { ContentHolder.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {

@GeneratedProperty(name = "partNo", type = String.class, javaDoc = "품번"),
        @GeneratedProperty(name = "partName", type = String.class, javaDoc = "품명"),
        @GeneratedProperty(name = "outSourcing", type = String.class, javaDoc = "협력사(제조사)"),
        @GeneratedProperty(name = "outSourcingGubun", type = String.class, javaDoc = "기존,신규"),
        @GeneratedProperty(name = "managerName", type = String.class, javaDoc = "담당자이름"),
        @GeneratedProperty(name = "managerCode", type = String.class, javaDoc = "담당자아이디"),
        @GeneratedProperty(name = "managerDeptName", type = String.class, javaDoc = "담당자부서"),
        @GeneratedProperty(name = "managerDeptCode", type = String.class, javaDoc = "담당자부서코드"),
        @GeneratedProperty(name = "pjtNo", type = String.class, javaDoc = "프로젝트번호"),
        @GeneratedProperty(name = "devOrderStartDate", type = Timestamp.class, javaDoc = "개발발주계획일"),
        @GeneratedProperty(name = "devOrderEndDate", type = Timestamp.class, javaDoc = "개발발주종료일"),
        @GeneratedProperty(name = "imspStartDate", type = Timestamp.class, javaDoc = "수입검사기준서계획일"),
        @GeneratedProperty(name = "imspEndDate", type = Timestamp.class, javaDoc = "수입검사기준서종료일"),
        @GeneratedProperty(name = "exaAgreeStartDate", type = Timestamp.class, javaDoc = "검사협정서접수계획일"),
        @GeneratedProperty(name = "exaAgreeEndDate", type = Timestamp.class, javaDoc = "검사협정서접수종료일"),
        @GeneratedProperty(name = "moldmkStartDate", type = Timestamp.class, javaDoc = "금형제작계획일"),
        @GeneratedProperty(name = "moldmkEndDate", type = Timestamp.class, javaDoc = "금형제작종료일"),
        @GeneratedProperty(name = "moldTryStartDate", type = Timestamp.class, javaDoc = "Try계획일"),
        @GeneratedProperty(name = "moldTryEndDate", type = Timestamp.class, javaDoc = "Try종료일"),
        @GeneratedProperty(name = "imsizeStartDate", type = Timestamp.class, javaDoc = "중요치수계획일"),
        @GeneratedProperty(name = "imsizeEndDate", type = Timestamp.class, javaDoc = "중요치수종료일"),
        @GeneratedProperty(name = "trustStartDate", type = Timestamp.class, javaDoc = "신뢰성계획일"),
        @GeneratedProperty(name = "trustEndDate", type = Timestamp.class, javaDoc = "신뢰성종료일"),
        @GeneratedProperty(name = "p1StartDate", type = Timestamp.class, javaDoc = "P1입고계획일"),
        @GeneratedProperty(name = "p1EndDate", type = Timestamp.class, javaDoc = "P1입고종료일"),
        @GeneratedProperty(name = "allSizeStartDate", type = Timestamp.class, javaDoc = "전치수계획일"),
        @GeneratedProperty(name = "allSizeEndDate", type = Timestamp.class, javaDoc = "전치수종료일"),
        @GeneratedProperty(name = "ppapStartDate1", type = Timestamp.class, javaDoc = "PPAP서류제출계획일"),
        @GeneratedProperty(name = "ppapEndDate1", type = Timestamp.class, javaDoc = "PPAP서류제출종료일"),
        @GeneratedProperty(name = "ppapStartDate2", type = Timestamp.class, javaDoc = "PPAP공정감사계획일"),
        @GeneratedProperty(name = "ppapEndDate2", type = Timestamp.class, javaDoc = "PPAP공정감사종료일"),
        @GeneratedProperty(name = "ppapStartDate3", type = Timestamp.class, javaDoc = "PPAP승인계획일"),
        @GeneratedProperty(name = "ppapEndDate3", type = Timestamp.class, javaDoc = "PPAP승인종료일"),
        @GeneratedProperty(name = "p2StartDate", type = Timestamp.class, javaDoc = "P2계획일"),
        @GeneratedProperty(name = "p2EndDate", type = Timestamp.class, javaDoc = "P2종료일"),
        @GeneratedProperty(name = "projectExecStartDate", type = Timestamp.class, javaDoc = "프로젝트실제시작일"),
        @GeneratedProperty(name = "projectExecEndDate", type = Timestamp.class, javaDoc = "프로젝트실제종료일"),
        @GeneratedProperty(name = "projectPlanStartDate", type = Timestamp.class, javaDoc = "프로젝트계획시작일"),
        @GeneratedProperty(name = "projectPlanEndDate", type = Timestamp.class, javaDoc = "프로젝트계획종료일"),
        @GeneratedProperty(name = "pjtState", type = String.class, javaDoc = "상태"),
        @GeneratedProperty(name = "bigo", type = String.class, javaDoc = "비고", constraints = @PropertyConstraints(upperLimit = 4000)) }, foreignKeys = {
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "department", type = e3ps.groupware.company.Department.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETPurchaseProject", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "pm", type = wt.org.WTUser.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETPurchaseProject", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "createUser", type = wt.org.WTUser.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETPurchaseProject", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "modifyUser", type = wt.org.WTUser.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETPurchaseProject", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "project", type = e3ps.project.E3PSProjectMaster.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETPurchaseProject", cardinality = Cardinality.ONE)) })
public class KETPurchaseProject extends _KETPurchaseProject {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETPurchaseProject
     * @exception wt.util.WTException
     **/
    public static KETPurchaseProject newKETPurchaseProject() throws WTException {

	KETPurchaseProject instance = new KETPurchaseProject();
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
