/* bcwti�����Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.�����This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.�����ecwti
 */

package e3ps.ecm.entity;

import wt.change2.WTChangeOrder2;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

/**
 * 
 * <p>
 * Use the <code>newKETProdChangeOrder</code> static factory method(s), not the <code>KETProdChangeOrder</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTChangeOrder2.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "ecoId", type = String.class),
        @GeneratedProperty(name = "ecoName", type = String.class),
        @GeneratedProperty(name = "devYn", type = String.class),
        @GeneratedProperty(name = "divisionFlag", type = String.class),
        @GeneratedProperty(name = "projectOid", type = String.class),
        @GeneratedProperty(name = "changeReason", type = String.class),
        @GeneratedProperty(name = "otherChangeReason", type = String.class),
        @GeneratedProperty(name = "custormerFlag", type = String.class),
        @GeneratedProperty(name = "otherCustFlagDesc", type = String.class),
        @GeneratedProperty(name = "changeFlag", type = String.class),
        @GeneratedProperty(name = "effectiveDateFlag", type = String.class),
        @GeneratedProperty(name = "inventoryClear", type = String.class),
        @GeneratedProperty(name = "cuDrawingChangeYn", type = String.class),
        @GeneratedProperty(name = "deptName", type = String.class),
        @GeneratedProperty(name = "domesticYn", type = String.class),
        @GeneratedProperty(name = "carMaker", type = String.class),
        @GeneratedProperty(name = "carCategory", type = String.class),
        @GeneratedProperty(name = "attribute1", type = String.class),
        @GeneratedProperty(name = "attribute2", type = String.class),
        @GeneratedProperty(name = "attribute3", type = String.class),
        @GeneratedProperty(name = "attribute4", type = String.class),
        @GeneratedProperty(name = "attribute5", type = String.class),
        @GeneratedProperty(name = "attribute6", type = String.class),
        @GeneratedProperty(name = "attribute7", type = String.class),
        @GeneratedProperty(name = "attribute8", type = String.class),
        @GeneratedProperty(name = "attribute9", type = String.class),
        @GeneratedProperty(name = "attribute10", type = String.class),
        @GeneratedProperty(name = "changeType", type = String.class, javaDoc = "설계변경 상세사유", constraints = @PropertyConstraints(upperLimit = 1024)),
        @GeneratedProperty(name = "reviewResult", type = String.class, javaDoc = "검토결과"),
        @GeneratedProperty(name = "designGuideYn", type = String.class, javaDoc = "설계가이드 반영"),
        @GeneratedProperty(name = "designChecksheetYn", type = String.class, javaDoc = "설계검증체크시트 반영"),
        @GeneratedProperty(name = "defectDivCode", type = String.class, javaDoc = "불량구분코드"),
        @GeneratedProperty(name = "defectDivName", type = String.class, javaDoc = "불량구분"),
        @GeneratedProperty(name = "defectTypeCode", type = String.class, javaDoc = "불량유형코드"),
        @GeneratedProperty(name = "defectTypeName", type = String.class, javaDoc = "불량유형"),
        @GeneratedProperty(name = "costChangeCode", type = String.class, javaDoc = "원가변동"),
        @GeneratedProperty(name = "costVariationRate", type = String.class, javaDoc = "원가증감비율"),
        @GeneratedProperty(name = "pointYn", type = String.class, javaDoc = "중요포인트 반영/변경"),
        @GeneratedProperty(name = "specChangeYn", type = String.class, javaDoc = "사양변경 식별표기"),
        @GeneratedProperty(name = "ecoApplyPoint", type = String.class, javaDoc = "ECO적용시점"),
        @GeneratedProperty(name = "webEditor", type = Object.class, javaDoc = "현상", columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),
        @GeneratedProperty(name = "webEditorText", type = Object.class, javaDoc = "현상", columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),
        @GeneratedProperty(name = "webEditor1", type = Object.class, javaDoc = "문제점 및 요구사항", columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),
        @GeneratedProperty(name = "webEditorText1", type = Object.class, javaDoc = "문제점 및 요구사항", columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)) })
public class KETProdChangeOrder extends _KETProdChangeOrder {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETProdChangeOrder
     * @exception wt.util.WTException
     **/
    public static KETProdChangeOrder newKETProdChangeOrder() throws WTException {

	KETProdChangeOrder instance = new KETProdChangeOrder();
	instance.initialize();
	return instance;
    }

}
