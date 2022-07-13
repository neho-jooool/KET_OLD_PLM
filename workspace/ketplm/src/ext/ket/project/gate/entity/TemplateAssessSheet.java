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

import wt.fc.WTObject;
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

import e3ps.common.impl.OwnPersistable;

/**
 * 
 * <p>
 * Use the <code>newTemplateAssessSheet</code> static factory method(s), not the <code>TemplateAssessSheet</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTObject.class, interfaces = { OwnPersistable.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "sheetName", type = String.class, javaDoc = "Check Sheet Name"),
        @GeneratedProperty(name = "sheetDesc", type = String.class, javaDoc = "설명"),
        @GeneratedProperty(name = "partOid", type = String.class, javaDoc = "부품Oid"),
        @GeneratedProperty(name = "active", type = String.class, javaDoc = "활성여부") }, foreignKeys = {
        @GeneratedForeignKey(name = "TmplAssSheetDevTypeLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "devType", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theTemplateAssessSheet", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "TmplAssSheetDevDivLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "devDiv", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theTemplateAssessSheet", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "TmplAssSheetDivisionLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "division", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theTemplateAssessSheet", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "TmplAssSheetProdCtgrLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "prodCategory", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theTemplateAssessSheet", cardinality = Cardinality.ONE)) }, tableProperties = @TableProperties(tableName = "KETTemplateAssessSheet"))
public class TemplateAssessSheet extends _TemplateAssessSheet {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return TemplateAssessSheet
     * @exception wt.util.WTException
     **/
    public static TemplateAssessSheet newTemplateAssessSheet() throws WTException {

	TemplateAssessSheet instance = new TemplateAssessSheet();
	instance.initialize();
	return instance;
    }

}
