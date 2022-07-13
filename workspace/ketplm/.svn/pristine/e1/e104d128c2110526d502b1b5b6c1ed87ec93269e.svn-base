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

package ext.ket.project.program.entity;

import java.sql.Timestamp;

import wt.doc.WTDocument;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GetAccess;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyAccessors;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.SetAccess;

/**
 * 
 * <p>
 * Use the <code>newKETProgramSchedule</code> static factory method(s), not the <code>KETProgramSchedule</code> constructor, to construct instances of this class. Instances must be constructed using
 * the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTDocument.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = { @GeneratedProperty(name = "programName", type = String.class, javaDoc = "프로그램명"),
        @GeneratedProperty(name = "startDate", type = Timestamp.class, javaDoc = "프로그램 시작일"), @GeneratedProperty(name = "endDate", type = Timestamp.class, javaDoc = "프로그램 종료일"),
        @GeneratedProperty(name = "divisionCode", type = String.class, javaDoc = "사업부코드"), @GeneratedProperty(name = "isNotify", type = Boolean.class, initialValue = "false", javaDoc = "공지유무") }, foreignKeys = {
        @GeneratedForeignKey(name = "KETProgramScheduleLink" /* first defined by: Iterated */, foreignKeyRole = @ForeignKeyRole(name = "master", type = ext.ket.project.program.entity.KETProgramMaster.class, cascade = false, constraints = @PropertyConstraints(required = true), accessors = @PropertyAccessors(getAccess = GetAccess.PRIVATE)), myRole = @MyRole(name = "iteration", cascade = false, accessors = @PropertyAccessors(getAccess = GetAccess.PRIVATE, setAccess = SetAccess.PROTECTED))),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "subContractor", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETProgramSchedule", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "lastUsingBuyer", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETProgramSchedule", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "formType", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETProgramSchedule", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "carType", type = e3ps.project.outputtype.OEMProjectType.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETProgramSchedule", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "KETProgramWTUserLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "programAdmin", type = wt.org.WTUser.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "program", cardinality = Cardinality.ONE)) })
public class KETProgramSchedule extends _KETProgramSchedule {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETProgramSchedule
     * @exception wt.util.WTException
     **/
    public static KETProgramSchedule newKETProgramSchedule() throws WTException {

	KETProgramSchedule instance = new KETProgramSchedule();
	instance.initialize();
	return instance;
    }

}
