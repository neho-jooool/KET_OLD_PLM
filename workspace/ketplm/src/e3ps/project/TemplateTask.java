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

import wt.fc.InvalidAttributeException;
import wt.fc.ObjectReference;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

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
 * Use the <code>newTemplateTask</code> static factory method(s), not the <code>TemplateTask</code> constructor, to construct instances of
 * this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(interfaces = { Tree.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "taskNo", type = String.class, javaDoc = "TASK NO"),
        @GeneratedProperty(name = "taskName", type = String.class, javaDoc = "TASK 명"),
        @GeneratedProperty(name = "taskNameEn", type = String.class, javaDoc = "TASK 명 (영문)", constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "taskSeq", type = int.class, javaDoc = "TASK 순서"),
        @GeneratedProperty(name = "taskDesc", type = String.class, javaDoc = "TASK 설명", constraints = @PropertyConstraints(upperLimit = 5000)),
        @GeneratedProperty(name = "taskSchedule", type = ObjectReference.class, javaDoc = "TASK 일정"),
        @GeneratedProperty(name = "taskType", type = String.class, javaDoc = "TASK Type (=pjtType 동일)≪≫≪≫0: 사전개발계획(영업수주)≪≫1: 양산≪≫2: 개발"),
        @GeneratedProperty(name = "taskHistory", type = int.class, javaDoc = "TASK 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시"),
        @GeneratedProperty(name = "taskCode", type = String.class, javaDoc = "TASK Sequence값 (대?r8자리)"),
        @GeneratedProperty(name = "taskKey", type = String.class),
        @GeneratedProperty(name = "personRole", type = String.class, javaDoc = "담당자 Role"),
        @GeneratedProperty(name = "mileStone", type = boolean.class),
        @GeneratedProperty(name = "sub", type = boolean.class, javaDoc = "일정 종속 여부"),
        @GeneratedProperty(name = "superTaskName", type = String.class, javaDoc = "종속시 제품 프로젝트 테스크명"),
        @GeneratedProperty(name = "optionType", type = boolean.class, javaDoc = "옵션/필수"),
        @GeneratedProperty(name = "outTask", type = boolean.class, javaDoc = "금형 Task에서 제품 Task 여부≪≫제품 Task에서 제품 Task 여부"),
        @GeneratedProperty(name = "drValue", type = String.class), @GeneratedProperty(name = "drValueCondition", type = String.class),
        @GeneratedProperty(name = "mainScheduleCode", type = String.class, javaDoc = "주요일정 식별 코드"),
        @GeneratedProperty(name = "scheduleType", type = String.class, javaDoc = "타스크 일정연계 코드"),
        @GeneratedProperty(name = "priorityControl", type = String.class, javaDoc = "중점관리 Task 여부"),
        @GeneratedProperty(name = "planWorkTime", type = float.class, javaDoc = "계획작업시간"),
        @GeneratedProperty(name = "execWorkTime", type = float.class, javaDoc = "실제작업시간"),
        @GeneratedProperty(name = "delayReason", type = String.class, javaDoc = "지연사유"),
        @GeneratedProperty(name = "delayType", type = String.class, javaDoc = "지연사유유형"),
        @GeneratedProperty(name = "newType", type = int.class, javaDoc = "구분-신규"),
        @GeneratedProperty(name = "modifyType", type = int.class, javaDoc = "구분-Modify"),
        @GeneratedProperty(name = "common", type = int.class, javaDoc = "Category-공통"),
        @GeneratedProperty(name = "mdraw", type = int.class, javaDoc = "Category-기구"),
        @GeneratedProperty(name = "hw", type = int.class, javaDoc = "Category-HW"),
        @GeneratedProperty(name = "sw", type = int.class, javaDoc = "Category-SW"),
        @GeneratedProperty(name = "m", type = int.class, javaDoc = "Category-M"),
        @GeneratedProperty(name = "p", type = int.class, javaDoc = "Category-P"),
        @GeneratedProperty(name = "buy", type = int.class, javaDoc = "Category-구매"),
        @GeneratedProperty(name = "system", type = int.class, javaDoc = "Category-설비"),
        @GeneratedProperty(name = "taskTypeCategory", type = String.class, javaDoc = "taskType유형"),
        @GeneratedProperty(name = "planStartDate", type = String.class, javaDoc = "계획시작일"),
        @GeneratedProperty(name = "planEndDate", type = String.class, javaDoc = "계획완료일"),
        @GeneratedProperty(name = "oldTaskOid", type = String.class, javaDoc = "과거TaskOid") }, foreignKeys = {
        @GeneratedForeignKey(name = "TemplateProjectTemplateTaskLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "project", type = e3ps.project.TemplateProject.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "task")),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "department", type = e3ps.groupware.company.Department.class), myRole = @MyRole(name = "theTemplateTask", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "taskWork", type = e3ps.project.PlanTaskWork.class), myRole = @MyRole(name = "theTemplateTask", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "oemType", type = e3ps.project.outputtype.OEMProjectType.class), myRole = @MyRole(name = "theTemplateTask", cardinality = Cardinality.ONE)) })
public class TemplateTask extends _TemplateTask {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return TemplateTask
     * @exception wt.util.WTException
     **/
    public static TemplateTask newTemplateTask() throws WTException {

	TemplateTask instance = new TemplateTask();
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
