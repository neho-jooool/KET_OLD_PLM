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

package e3ps.dms.entity;

import wt.doc.WTDocument;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;
// Preserved unmodeled dependency
// Preserved unmodeled dependency

/**
 * 
 * <p>
 * Use the <code>newKETProjectDocument</code> static factory method(s), not the <code>KETProjectDocument</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTDocument.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = { @GeneratedProperty(name = "documentNo", type = String.class, javaDoc = "문서코드"),
        @GeneratedProperty(name = "documentDescription", type = String.class, javaDoc = "문서 설명", constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "divisionCode", type = String.class, javaDoc = "사업부코드"), @GeneratedProperty(name = "deptName", type = String.class, javaDoc = "등록부서명"),
        @GeneratedProperty(name = "firstRegistrationStage", type = int.class, javaDoc = "최초등록시점"), @GeneratedProperty(name = "isBuyerSummit", type = String.class, javaDoc = "고객제출자료여부"),
        @GeneratedProperty(name = "buyerCode", type = String.class, javaDoc = "고객사코드", constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "dRCheckPoint", type = int.class, javaDoc = "DR평가점수"), @GeneratedProperty(name = "approvalResult", type = String.class, javaDoc = "승인결과"),
        @GeneratedProperty(name = "lastApprovalComment", type = String.class, javaDoc = "최종승인의견", constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "security", type = String.class, javaDoc = "보안등급"), @GeneratedProperty(name = "attribute1", type = String.class, javaDoc = "기타1"),
        @GeneratedProperty(name = "attribute2", type = String.class, javaDoc = "기타2"), @GeneratedProperty(name = "attribute3", type = String.class, javaDoc = "기타3"),
        @GeneratedProperty(name = "attribute4", type = String.class, javaDoc = "기타4"), @GeneratedProperty(name = "attribute5", type = String.class, javaDoc = "기타5"),
        @GeneratedProperty(name = "attribute6", type = String.class, javaDoc = "기타6"), @GeneratedProperty(name = "attribute7", type = String.class, javaDoc = "기타7"),
        @GeneratedProperty(name = "attribute8", type = String.class, javaDoc = "기타8"), @GeneratedProperty(name = "attribute9", type = String.class, javaDoc = "기타9"),
        @GeneratedProperty(name = "attribute10", type = String.class, javaDoc = "기타10"),
        @GeneratedProperty(name = "costComment", type = String.class, javaDoc = "원가의견", constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "webEditor", type = Object.class, javaDoc = "Web Editor", columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),
        @GeneratedProperty(name = "webEditorText", type = Object.class, columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)) })
public class KETProjectDocument extends _KETProjectDocument {


    static final long serialVersionUID = 1;




    /**
     * Default factory for the class.
     * 
     * @return KETProjectDocument
     * @exception wt.util.WTException
     **/
    public static KETProjectDocument newKETProjectDocument() throws WTException {

	KETProjectDocument instance = new KETProjectDocument();
	instance.initialize();
	return instance;
    }

}
