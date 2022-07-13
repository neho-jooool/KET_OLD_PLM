/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package e3ps.project;

import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

/**
 * 
 * <p>
 * Use the <code>newE3PSProject</code> static factory method(s), not the <code>E3PSProject</code> constructor, to construct instances of
 * this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = TemplateProject.class, properties = {
        @GeneratedProperty(name = "pjtCompletion", type = int.class, javaDoc = "프로젝트 진행율"),
        @GeneratedProperty(name = "pjtState", type = int.class, javaDoc = "프로젝트 상태≪≫≪≫0: 진행중≪≫1: 중지(Hold)≪≫2: 취소(Revocation)≪≫3: 재시작(Restart)≪≫4: 지연(Delay)"),
        @GeneratedProperty(name = "templateCode", type = String.class), @GeneratedProperty(name = "preferComp", type = String.class),
        @GeneratedProperty(name = "developedType", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "devRequestNo", type = String.class), @GeneratedProperty(name = "reviewPjtNo", type = String.class),
        @GeneratedProperty(name = "manufacPlace", type = String.class, javaDoc = "생산처구분"), }, foreignKeys = {
        @GeneratedForeignKey(name = "ProjectProductTypeLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "productType", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "ProjectLankLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "rank", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "ProjectAssembledTypeLInk", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "assembledType", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "ProjectDevelopentTypeLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "developentType", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "ProjectAssemblyPlaceLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "assemblyPlace", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "ProjectProcessLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "process", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "ProjectDesignTypeLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "designType", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "MasterE3PSHistory" /* first defined by: TemplateProject */, myRoleIsRoleA = true, foreignKeyRole = @ForeignKeyRole(name = "master", type = e3ps.project.E3PSProjectMaster.class, cascade = false, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "history", cascade = false)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "devRequest", type = e3ps.dms.entity.KETDevelopmentRequest.class), myRole = @MyRole(name = "theE3PSProject", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "business", type = wt.org.WTUser.class), myRole = @MyRole(name = "theE3PSProject", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "template", type = e3ps.project.TemplateProject.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theE3PSProject", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "ProjectdevelopPattern", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "developPattern", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "goalOneLevel", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "mainGoalType", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "goalTwoLevel", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "subGoalType", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "manageProduct", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "manageProductType", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "obtainOrder", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "obtainType", type = e3ps.common.code.NumberCode.class), myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)) })
public class E3PSProject extends _E3PSProject {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return E3PSProject
     * @exception wt.util.WTException
     **/
    public static E3PSProject newE3PSProject() throws WTException {

	E3PSProject instance = new E3PSProject();
	instance.initialize();
	return instance;
    }

}
