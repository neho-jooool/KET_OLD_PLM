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

package e3ps.project;

import java.sql.Timestamp;

import wt.content.ContentHolder;
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

import e3ps.common.impl.OwnableTree;
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
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency

/**
 * 
 * <p>
 * Use the <code>newProjectOutput</code> static factory method(s), not the <code>ProjectOutput</code> constructor, to construct instances of
 * this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(interfaces = { OwnableTree.class, ContentHolder.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "outputName", type = String.class, javaDoc = "산출물 정의 이름"),
        @GeneratedProperty(name = "outputDesc", type = String.class, javaDoc = "산출물 정의 설명"),
        @GeneratedProperty(name = "outputLocation", type = String.class, javaDoc = "산출물 정의 위치(문서분류체계)"),
        @GeneratedProperty(name = "outputRole", type = String.class, javaDoc = "Role"),
        @GeneratedProperty(name = "outputHistory", type = int.class, javaDoc = "산출물 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시"),
        @GeneratedProperty(name = "completion", type = int.class),
        @GeneratedProperty(name = "isPrimary", type = boolean.class, initialValue = "false", javaDoc = "TRUE: Primary ProjectOutput≪≫FALSE: Secondary ProjectOutput"),
        @GeneratedProperty(name = "objType", type = String.class, javaDoc = "DOC: Document≪≫DRW: EPMDocument"),
        @GeneratedProperty(name = "outputKeyType", type = String.class),
        @GeneratedProperty(name = "complete_reason", type = String.class, constraints = @PropertyConstraints(upperLimit = 6000)),
        @GeneratedProperty(name = "etcCompletion", type = Timestamp.class),
        @GeneratedProperty(name = "subjectType", type = String.class, javaDoc = "Gate대상"),
        @GeneratedProperty(name = "gateCheckType", type = String.class, javaDoc = "추가점검Gate대상"),
        @GeneratedProperty(name = "oldOutputOid", type = String.class, javaDoc = "이전버전 Output Oid"),
        @GeneratedProperty(name = "totalCost", type = String.class, javaDoc = "총원가(원) 검토"),
        @GeneratedProperty(name = "rate", type = String.class, javaDoc = "수익율 검토"),
        @GeneratedProperty(name = "bigo", type = String.class, javaDoc = "비고", constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "totalCostFinal", type = String.class, javaDoc = "총원가(원) 최종"),
        @GeneratedProperty(name = "rateFinal", type = String.class, javaDoc = "수익율 최종"),
        @GeneratedProperty(name = "salesTarget", type = String.class, javaDoc = "판매목표가 검토"),
        @GeneratedProperty(name = "yearAverageQty", type = String.class, javaDoc = "연평균수량(EA) 검토"),
        @GeneratedProperty(name = "salesTargetFinal", type = String.class, javaDoc = "판매목표가 최종"),
        @GeneratedProperty(name = "yearAverageQtyFinal", type = String.class, javaDoc = "연평균수량(EA) 최종"),
        @GeneratedProperty(name = "outputDocOid", type = String.class, javaDoc = "표준양식Oid"),
        @GeneratedProperty(name = "outputDocName", type = String.class, javaDoc = "표준양식 이름") }, foreignKeys = {
        @GeneratedForeignKey(name = "ProjectOutputLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "project", type = e3ps.project.TemplateProject.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "output")),
        @GeneratedForeignKey(name = "TaskOutputLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "task", type = e3ps.project.TemplateTask.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "output")),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "outputType", type = e3ps.project.outputtype.ProjectOutPutType.class), myRole = @MyRole(name = "theProjectOutput", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "costDivision", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "theProjectOutput")) })
public class ProjectOutput extends _ProjectOutput {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return ProjectOutput
     * @exception wt.util.WTException
     **/
    public static ProjectOutput newProjectOutput() throws WTException {

	ProjectOutput instance = new ProjectOutput();
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
