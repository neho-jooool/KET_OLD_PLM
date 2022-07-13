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

import wt.content.ContentHolder;
import wt.enterprise.Managed;
import wt.fc.ObjectReference;
import wt.inf.container.WTContained;
import wt.org.WTPrincipalReference;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.DerivedProperty;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.SupportedAPI;

import e3ps.project.historyprocess.MasterUtil;
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency

/**
 * 
 * <p>
 * Use the <code>newTemplateProject</code> static factory method(s), not the <code>TemplateProject</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = Managed.class, interfaces = { WTContained.class, ContentHolder.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "pjtSeq", type = int.class, javaDoc = "프로젝트 순서"),
        @GeneratedProperty(name = "pjtDesc", type = String.class, javaDoc = "프로젝트 설명", constraints = @PropertyConstraints(upperLimit = 5000)),
        @GeneratedProperty(name = "pjtSchedule", type = ObjectReference.class, javaDoc = "프로젝트 일정"),
        @GeneratedProperty(name = "pjtType", type = int.class, javaDoc = "프로젝트 종류≪≫≪≫0: 사전개발계획(영업수주)≪≫1: 양산≪≫2: 개발"),
        @GeneratedProperty(name = "pjtHistory", type = int.class, javaDoc = "프로젝트 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시"),
        @GeneratedProperty(name = "lastest", type = boolean.class, initialValue = "true", javaDoc = "1(true): List에 나타남≪≫0(false): List에 나타나지 않음"),
        @GeneratedProperty(name = "checkOut", type = boolean.class),
        @GeneratedProperty(name = "workingCopy", type = boolean.class),
        @GeneratedProperty(name = "checkOutState", type = String.class, initialValue = "\"c/i\"", columnProperties = @ColumnProperties(index = true)),
        @GeneratedProperty(name = "diffPLM", type = boolean.class, initialValue = "true", javaDoc = "OLD/NEW PLM 구분자≪≫true: NEW PLM≪≫false: OLD PLM"),
        @GeneratedProperty(name = "isDirection", type = boolean.class, initialValue = "true", javaDoc = "1(true) : LH [Default]≪≫0(false) : RH"),
        @GeneratedProperty(name = "historyNote", type = String.class),
        @GeneratedProperty(name = "pjtID", type = String.class),
        @GeneratedProperty(name = "historyNoteType", type = int.class),
        @GeneratedProperty(name = "orderName", type = String.class, javaDoc = "수주 프로젝트 시 \"수주/미수주\" 확인용 필드"),
        @GeneratedProperty(name = "stopedDetil", type = String.class, constraints = @PropertyConstraints(upperLimit = 3000)),
        @GeneratedProperty(name = "reStartDetil", type = String.class, constraints = @PropertyConstraints(upperLimit = 3000)),
        @GeneratedProperty(name = "historyNoteWebEditor", type = Object.class, javaDoc = "HistoryNote Web Editor", columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),
        @GeneratedProperty(name = "historyNoteWebEditorText", type = Object.class, javaDoc = "HistoryNote Web Editor Text", columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),
        @GeneratedProperty(name = "waterPoof", type = String.class, javaDoc = "방수여부"),
        @GeneratedProperty(name = "series", type = String.class, javaDoc = "시리즈"),
        @GeneratedProperty(name = "pjtIteration", type = int.class, javaDoc = "프로젝트 이력 Iteration≪≫≪≫0 : 프로젝트 이력(pjtHistory) 최초 생성 시, 결재 프로세스 통해서 일정 변경 시≪≫1~N : 결재 프로세스 없이 일정 변경 시"),
        @GeneratedProperty(name = "historyRole", type = String.class, constraints = @PropertyConstraints(upperLimit = 2000)),
        @GeneratedProperty(name = "productTypeLevel2", type = String.class),
        @GeneratedProperty(name = "modifier", type = WTPrincipalReference.class, constraints = @PropertyConstraints(required = true), columnProperties = @ColumnProperties(columnName = "F")),
        @GeneratedProperty(name = "activeType", type = String.class, javaDoc = "상태"),
        @GeneratedProperty(name = "devType", type = String.class, javaDoc = "개발구분"),
        @GeneratedProperty(name = "clientCompany", type = String.class, javaDoc = "고객처"),
        @GeneratedProperty(name = "devStep", type = String.class, javaDoc = "개발단계"),
        @GeneratedProperty(name = "makeOffice", type = String.class, javaDoc = "제작처"),
        @GeneratedProperty(name = "moldType", type = String.class, javaDoc = "금형구분"),
        @GeneratedProperty(name = "making", type = String.class, javaDoc = "제작구분"),
        @GeneratedProperty(name = "division", type = String.class, javaDoc = "사업부") }, foreignKeys = {
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "master", type = e3ps.project.ProjectMaster.class, autoNavigate = true, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "history", cardinality = Cardinality.ONE_TO_MANY)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "outputType", type = e3ps.project.outputtype.ProjectOutPutType.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theTemplateProject", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "oemType", type = e3ps.project.outputtype.OEMProjectType.class), myRole = @MyRole(name = "theTemplateProject", cardinality = Cardinality.ONE)) }, derivedProperties = {
        @DerivedProperty(name = "pjtNo", derivedFrom = "master>pjtNo", supportedAPI = SupportedAPI.PUBLIC),
        @DerivedProperty(name = "pjtName", derivedFrom = "master>pjtName", supportedAPI = SupportedAPI.PUBLIC),
        @DerivedProperty(name = "pjtTypeName", derivedFrom = "master>pjtTypeName", supportedAPI = SupportedAPI.PUBLIC) })
public class TemplateProject extends _TemplateProject {

    static final long serialVersionUID = 1;

    /**
     * Initializes objects created by new-factory methods.
     * 
     * @exception wt.util.WTException
     **/
    @Override
    protected void initialize() throws WTException {

	super.initialize();
	try {

	    setMaster(MasterUtil.newMasterFor(this));

	} catch (Exception e) {
	    throw new WTException(e);
	}
    }

    /**
     * Default factory for the class.
     * 
     * @return TemplateProject
     * @exception wt.util.WTException
     **/
    public static TemplateProject newTemplateProject() throws WTException {

	TemplateProject instance = new TemplateProject();
	instance.initialize();
	return instance;
    }

}
