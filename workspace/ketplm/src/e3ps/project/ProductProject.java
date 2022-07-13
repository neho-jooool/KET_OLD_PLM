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

import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency

/**
 * 
 * <p>
 * Use the <code>newProductProject</code> static factory method(s), not the <code>ProductProject</code> constructor, to construct instances of this class. Instances must be constructed using the
 * static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = E3PSProject.class, properties = { @GeneratedProperty(name = "teamType", type = String.class, javaDoc = "본부구분(자동차/전자)"),
        @GeneratedProperty(name = "partNo", type = String.class, javaDoc = "조립 Ass'y No"), @GeneratedProperty(name = "model", type = String.class, javaDoc = "적용차종"),
        @GeneratedProperty(name = "costsDate", type = Timestamp.class, javaDoc = "원가제출일"), @GeneratedProperty(name = "assembly", type = String.class),
        @GeneratedProperty(name = "partnerNo", type = String.class), @GeneratedProperty(name = "isPM", type = boolean.class), @GeneratedProperty(name = "itDivision", type = String.class),
        @GeneratedProperty(name = "planEndDate1", type = Timestamp.class, javaDoc = "개발시작회의(DR1)_계획완료일"),
        @GeneratedProperty(name = "execEndDate1", type = Timestamp.class, javaDoc = "개발시작회의(DR1)_실제완료일"),
        @GeneratedProperty(name = "planEndDate2", type = Timestamp.class, javaDoc = "투자품의_계획완료일"),
        @GeneratedProperty(name = "execEndDate2", type = Timestamp.class, javaDoc = "투자품의_실제완료일"),
        @GeneratedProperty(name = "planEndDate3", type = Timestamp.class, javaDoc = "Design Review(DR2)_계획완료일"),
        @GeneratedProperty(name = "execEndDate3", type = Timestamp.class, javaDoc = "Design Review(DR2)_실제완료일"),
        @GeneratedProperty(name = "planEndDate4", type = Timestamp.class, javaDoc = "도면출도_계획완료일"),
        @GeneratedProperty(name = "execEndDate4", type = Timestamp.class, javaDoc = "도면출도_실제완료일"),
        @GeneratedProperty(name = "planEndDate5", type = Timestamp.class, javaDoc = "금형제작_계획완료일"),
        @GeneratedProperty(name = "execEndDate5", type = Timestamp.class, javaDoc = "금형제작_실제완료일"),
        @GeneratedProperty(name = "planEndDate6", type = Timestamp.class, javaDoc = "설비제작_계획완료일"),
        @GeneratedProperty(name = "execEndDate6", type = Timestamp.class, javaDoc = "설비제작_실제완료일"),
        @GeneratedProperty(name = "planEndDate7", type = Timestamp.class, javaDoc = "초도품제작(proto)_계획완료일"),
        @GeneratedProperty(name = "execEndDate7", type = Timestamp.class, javaDoc = "초도품제작(proto)_실제완료일"),
        @GeneratedProperty(name = "planEndDate8", type = Timestamp.class, javaDoc = "초도품평가(proto)_계획완료일"),
        @GeneratedProperty(name = "execEndDate8", type = Timestamp.class, javaDoc = "초도품평가(proto)_실제완료일"),
        @GeneratedProperty(name = "planEndDate9", type = Timestamp.class, javaDoc = "초도품납품(proto)_계획완료일"),
        @GeneratedProperty(name = "execEndDate9", type = Timestamp.class, javaDoc = "초도품납품(proto)_실제완료일"),
        @GeneratedProperty(name = "planEndDate10", type = Timestamp.class, javaDoc = "PROTO샘플제작_계획완료일"),
        @GeneratedProperty(name = "execEndDate10", type = Timestamp.class, javaDoc = "PROTO샘플제작_실제완료일"),
        @GeneratedProperty(name = "planEndDate11", type = Timestamp.class, javaDoc = "PROTO샘플납품_계획완료일"),
        @GeneratedProperty(name = "execEndDate11", type = Timestamp.class, javaDoc = "PROTO샘플납품_실제완료일"),
        @GeneratedProperty(name = "planEndDate12", type = Timestamp.class, javaDoc = "신뢰성평가(DV)_계획완료일"),
        @GeneratedProperty(name = "execEndDate12", type = Timestamp.class, javaDoc = "신뢰성평가(DV)_실제완료일"),
        @GeneratedProperty(name = "planEndDate13", type = Timestamp.class, javaDoc = "GATE1_계획완료일"),
        @GeneratedProperty(name = "execEndDate13", type = Timestamp.class, javaDoc = "GATE1_실제완료일"),
        @GeneratedProperty(name = "planEndDate14", type = Timestamp.class, javaDoc = "GATE2_계획완료일"),
        @GeneratedProperty(name = "execEndDate14", type = Timestamp.class, javaDoc = "GATE2_실제완료일"),
        @GeneratedProperty(name = "planEndDate15", type = Timestamp.class, javaDoc = "양산계획(DR3)_계획완료일"),
        @GeneratedProperty(name = "execEndDate15", type = Timestamp.class, javaDoc = "양산계획(DR3)_실제완료일"),
        @GeneratedProperty(name = "planEndDate16", type = Timestamp.class, javaDoc = "금형제작_계획완료일"),
        @GeneratedProperty(name = "execEndDate16", type = Timestamp.class, javaDoc = "금형제작_실제완료일"),
        @GeneratedProperty(name = "planEndDate17", type = Timestamp.class, javaDoc = "설비제작_계획완료일"),
        @GeneratedProperty(name = "execEndDate17", type = Timestamp.class, javaDoc = "설비제작_실제완료일"),
        @GeneratedProperty(name = "planEndDate18", type = Timestamp.class, javaDoc = "초도품제작_계획완료일"),
        @GeneratedProperty(name = "execEndDate18", type = Timestamp.class, javaDoc = "초도품제작_실제완료일"),
        @GeneratedProperty(name = "planEndDate19", type = Timestamp.class, javaDoc = "초도품평가_계획완료일"),
        @GeneratedProperty(name = "execEndDate19", type = Timestamp.class, javaDoc = "초도품평가_실제완료일"),
        @GeneratedProperty(name = "planEndDate45", type = Timestamp.class, javaDoc = "초도품납품_계획완료일"),
        @GeneratedProperty(name = "execEndDate45", type = Timestamp.class, javaDoc = "초도품납품_실제완료일"),
        @GeneratedProperty(name = "planEndDate20", type = Timestamp.class, javaDoc = "제품유효성평가(DR4)_계획완료일"),
        @GeneratedProperty(name = "execEndDate20", type = Timestamp.class, javaDoc = "제품유효성평가(DR4)_실제완료일"),
        @GeneratedProperty(name = "planEndDate21", type = Timestamp.class, javaDoc = "신뢰성평가(DV)_계획완료일"),
        @GeneratedProperty(name = "execEndDate21", type = Timestamp.class, javaDoc = "신뢰성평가(DV)_실제완료일"),
        @GeneratedProperty(name = "planEndDate22", type = Timestamp.class, javaDoc = "All-Tool 준비_계획완료일"),
        @GeneratedProperty(name = "execEndDate22", type = Timestamp.class, javaDoc = "All-Tool 준비_실제완료일"),
        @GeneratedProperty(name = "planEndDate23", type = Timestamp.class, javaDoc = "All-Tool 준비회의_계획완료일"),
        @GeneratedProperty(name = "execEndDate23", type = Timestamp.class, javaDoc = "All-Tool 준비회의_실제완료일"),
        @GeneratedProperty(name = "planEndDate24", type = Timestamp.class, javaDoc = "All-Tool 검증_계획완료일"),
        @GeneratedProperty(name = "execEndDate24", type = Timestamp.class, javaDoc = "All-Tool 검증_실제완료일"),
        @GeneratedProperty(name = "planEndDate25", type = Timestamp.class, javaDoc = "All-Tool 점검_계획완료일"),
        @GeneratedProperty(name = "execEndDate25", type = Timestamp.class, javaDoc = "All-Tool 점검_실제완료일"),
        @GeneratedProperty(name = "planEndDate26", type = Timestamp.class, javaDoc = "GATE3_계획완료일"),
        @GeneratedProperty(name = "execEndDate26", type = Timestamp.class, javaDoc = "GATE3_실제완료일"),
        @GeneratedProperty(name = "planEndDate27", type = Timestamp.class, javaDoc = "P1납품_계획완료일"),
        @GeneratedProperty(name = "execEndDate27", type = Timestamp.class, javaDoc = "P1납품_실제완료일"),
        @GeneratedProperty(name = "planEndDate28", type = Timestamp.class, javaDoc = "신뢰성평가(PV)_계획완료일"),
        @GeneratedProperty(name = "execEndDate28", type = Timestamp.class, javaDoc = "신뢰성평가(PV)_실제완료일"),
        @GeneratedProperty(name = "planEndDate29", type = Timestamp.class, javaDoc = "Full-Tool 준비회의_계획완료일"),
        @GeneratedProperty(name = "execEndDate29", type = Timestamp.class, javaDoc = "Full-Tool 준비회의_실제완료일"),
        @GeneratedProperty(name = "planEndDate30", type = Timestamp.class, javaDoc = "Full-Tool 검증_계획완료일"),
        @GeneratedProperty(name = "execEndDate30", type = Timestamp.class, javaDoc = "Full-Tool 검증_실제완료일"),
        @GeneratedProperty(name = "planEndDate31", type = Timestamp.class, javaDoc = "양산유효성평가(DR5)_계획완료일"),
        @GeneratedProperty(name = "execEndDate31", type = Timestamp.class, javaDoc = "양산유효성평가(DR5)_실제완료일"),
        @GeneratedProperty(name = "planEndDate32", type = Timestamp.class, javaDoc = "제품합격_계획완료일"),
        @GeneratedProperty(name = "execEndDate32", type = Timestamp.class, javaDoc = "제품합격_실제완료일"),
        @GeneratedProperty(name = "planEndDate33", type = Timestamp.class, javaDoc = "협력사 ISIR승인_계획완료일"),
        @GeneratedProperty(name = "execEndDate33", type = Timestamp.class, javaDoc = "협력사 ISIR승인_실제완료일"),
        @GeneratedProperty(name = "planEndDate34", type = Timestamp.class, javaDoc = "Full-Tool 점검_계획완료일"),
        @GeneratedProperty(name = "execEndDate34", type = Timestamp.class, javaDoc = "Full-Tool 점검_실제완료일"),
        @GeneratedProperty(name = "planEndDate35", type = Timestamp.class, javaDoc = "GATE4_계획완료일"),
        @GeneratedProperty(name = "execEndDate35", type = Timestamp.class, javaDoc = "GATE4_실제완료일"),
        @GeneratedProperty(name = "planEndDate36", type = Timestamp.class, javaDoc = "P2납품_계획완료일"),
        @GeneratedProperty(name = "execEndDate36", type = Timestamp.class, javaDoc = "P2납품_실제완료일"),
        @GeneratedProperty(name = "planEndDate37", type = Timestamp.class, javaDoc = "ISIR/PPAP제출_계획완료일"),
        @GeneratedProperty(name = "execEndDate37", type = Timestamp.class, javaDoc = "ISIR/PPAP제출_실제완료일"),
        @GeneratedProperty(name = "planEndDate38", type = Timestamp.class, javaDoc = "양산이관(금형)_계획완료일"),
        @GeneratedProperty(name = "execEndDate38", type = Timestamp.class, javaDoc = "양산이관(금형)_실제완료일"),
        @GeneratedProperty(name = "planEndDate39", type = Timestamp.class, javaDoc = "양산이관(설비)_계획완료일"),
        @GeneratedProperty(name = "execEndDate39", type = Timestamp.class, javaDoc = "양산이관(설비)_실제완료일"),
        @GeneratedProperty(name = "planEndDate40", type = Timestamp.class, javaDoc = "양산이관_계획완료일"),
        @GeneratedProperty(name = "execEndDate40", type = Timestamp.class, javaDoc = "양산이관_실제완료일"),
        @GeneratedProperty(name = "planEndDate41", type = Timestamp.class, javaDoc = "초도양산_계획완료일"),
        @GeneratedProperty(name = "execEndDate41", type = Timestamp.class, javaDoc = "초도양산_실제완료일"),
        @GeneratedProperty(name = "planEndDate42", type = Timestamp.class, javaDoc = "양산최종평가(DR6)_계획완료일"),
        @GeneratedProperty(name = "execEndDate42", type = Timestamp.class, javaDoc = "양산최종평가(DR6)_실제완료일"),
        @GeneratedProperty(name = "planEndDate43", type = Timestamp.class, javaDoc = "GATE5_계획완료일"),
        @GeneratedProperty(name = "execEndDate43", type = Timestamp.class, javaDoc = "GATE5_실제완료일"),
        @GeneratedProperty(name = "planEndDate44", type = Timestamp.class, javaDoc = "프로젝트완료_계획완료일"),
        @GeneratedProperty(name = "execEndDate44", type = Timestamp.class, javaDoc = "프로젝트완료_실제완료일"),
        @GeneratedProperty(name = "planEndDate46", type = Timestamp.class, javaDoc = "Tool제작_계획완료일"),
        @GeneratedProperty(name = "execEndDate46", type = Timestamp.class, javaDoc = "Tool제작_실제완료일")
	})
public class ProductProject extends _ProductProject {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return ProductProject
     * @exception wt.util.WTException
     **/
    public static ProductProject newProductProject() throws WTException {

	ProductProject instance = new ProductProject();
	instance.initialize();
	return instance;
    }

}
