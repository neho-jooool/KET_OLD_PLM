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

package ext.ket.project.gate.entity;

import wt.doc.WTDocument;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newAssessSheet</code> static factory method(s), not the <code>AssessSheet</code> constructor, to construct instances of
 * this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTDocument.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "sheetName", type = String.class, javaDoc = "Check Sheet Name"),
        @GeneratedProperty(name = "sheetDesc", type = String.class, javaDoc = "설명"),
        @GeneratedProperty(name = "partOid", type = String.class, javaDoc = "부품Oid"),
        @GeneratedProperty(name = "active", type = String.class, javaDoc = "활성여부") }, foreignKeys = {
        @GeneratedForeignKey(name = "AssSheetDevTypeLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "devType", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theAssessSheet", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "AssSheetDevDivLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "devDiv", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theAssessSheet", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "AssSheetDivisionLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "division", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theAssessSheet", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "AssSheetProdCtgrLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "prodCategory", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theAssessSheet", cardinality = Cardinality.ONE)) }, tableProperties = @TableProperties(tableName = "KETAssessSheet"))
public class AssessSheet extends _AssessSheet {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return AssessSheet
     * @exception wt.util.WTException
     **/
    public static AssessSheet newAssessSheet() throws WTException {

	AssessSheet instance = new AssessSheet();
	instance.initialize();
	return instance;
    }

}
