/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.cost.entity;

import java.sql.Timestamp;

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

import e3ps.common.impl.Tree;

/**
 * 개발원가 ERP 인터페이스 이력 (하위BOM)
 * <p>
 * Use the <code>newCostInterfaceChildHistory</code> static factory method(s), not the <code>CostInterfaceChildHistory</code> constructor,
 * to construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(interfaces = { Tree.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "pjtNo", type = String.class, javaDoc = "프로젝트번호"),
        @GeneratedProperty(name = "pjtType", type = String.class, javaDoc = "제품,검토"),
        @GeneratedProperty(name = "partNo", type = String.class, javaDoc = "가품번"),
        @GeneratedProperty(name = "rootPartNo", type = String.class, javaDoc = "완제품품번"),
        @GeneratedProperty(name = "realPartNo", type = String.class, javaDoc = "공정품번"),
        @GeneratedProperty(name = "drStep", type = String.class, javaDoc = "DR단계"),
        @GeneratedProperty(name = "partName", type = String.class, javaDoc = "제품명"),
        @GeneratedProperty(name = "us", type = String.class, javaDoc = "U/S"),
        @GeneratedProperty(name = "version", type = String.class, javaDoc = "version + subVersion"),
        @GeneratedProperty(name = "partType", type = String.class, javaDoc = "제품분류"),
        @GeneratedProperty(name = "mftFactory1", type = String.class, javaDoc = "생산정보 - 생산국(입고처)"),
        @GeneratedProperty(name = "mftFactory2", type = String.class, javaDoc = "생산지(유/무상)"),
        @GeneratedProperty(name = "company", type = String.class, javaDoc = "생산정보 - 업체명"),
        @GeneratedProperty(name = "facilities", type = String.class, initialValue = "\"0\"", javaDoc = "생산정보 - 적용설비(Ton)"),
        @GeneratedProperty(name = "productLossExpr", type = String.class, javaDoc = "생산Loss", initialValue = "\"0\""),
        @GeneratedProperty(name = "assyLossExpr", type = String.class, javaDoc = "조립Loss", initialValue = "\"0\""),
        @GeneratedProperty(name = "sopYear", type = String.class, javaDoc = "SOP년도"),
        @GeneratedProperty(name = "waers", type = String.class, javaDoc = "통화", initialValue = "\"KRW\""),
        @GeneratedProperty(name = "productCostTotal", type = String.class, javaDoc = "총원가(컨버팅)"),
        @GeneratedProperty(name = "orgProductCostTotal", type = String.class, javaDoc = "총원가(PLM)"),
        @GeneratedProperty(name = "materialCostExpr", type = String.class, javaDoc = "재료비(수식)", initialValue = "\"0\""),
        @GeneratedProperty(name = "processingCost", type = String.class, javaDoc = "가공비", initialValue = "\"0\""),
        @GeneratedProperty(name = "salesManageCost", type = String.class, initialValue = "\"0\"", javaDoc = "판관비"),
        @GeneratedProperty(name = "packUnitPriceOption", type = String.class, javaDoc = "개별포장비 합계(옵션)", initialValue = "\"0\""),
        @GeneratedProperty(name = "rawMaterialCostExpr", type = String.class, javaDoc = "원재료비", initialValue = "\"0\""),

        @GeneratedProperty(name = "materialCost", type = String.class, javaDoc = "재료비", initialValue = "\"0\""),
        @GeneratedProperty(name = "indirectRndRate", type = String.class, javaDoc = "간접인건비비율R&D", initialValue = "\"0\""),
        @GeneratedProperty(name = "indirectLabourRate", type = String.class, javaDoc = "간접인건비비율노무비", initialValue = "\"0\""),
        @GeneratedProperty(name = "rndExpr", type = String.class, javaDoc = "R&D비", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborCostExpr", type = String.class, javaDoc = "노무비", initialValue = "\"0\""),
        @GeneratedProperty(name = "indirectLabourRndCost", type = String.class, javaDoc = "간접인건비R&D", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborCost", type = String.class, javaDoc = "직접인건비", initialValue = "\"0\""),
        @GeneratedProperty(name = "inDirectLaborCost", type = String.class, javaDoc = "간접인건비", initialValue = "\"0\""),
        @GeneratedProperty(name = "facReduceCost", type = String.class, javaDoc = "설비감가", initialValue = "\"0\""),
        @GeneratedProperty(name = "machineDpcCostExpr", type = String.class, javaDoc = "기계감가", initialValue = "\"0\""),
        @GeneratedProperty(name = "facReducePrice", type = String.class, javaDoc = "설비감가비", initialValue = "\"0\""),

        @GeneratedProperty(name = "elecCostExpr", type = String.class, javaDoc = "전력비", initialValue = "\"0\""),
        @GeneratedProperty(name = "tabaryuExpr", type = String.class, javaDoc = "타발유", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldMaintenance", type = String.class, javaDoc = "금형유지비", initialValue = "\"0\""),
        @GeneratedProperty(name = "etcReducePrice", type = String.class, javaDoc = "기타감가비", initialValue = "\"0\""),
        @GeneratedProperty(name = "directCost", type = String.class, javaDoc = "직접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "packUnitPriceSum", type = String.class, javaDoc = "개별포장비합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "inDirectCostExpr", type = String.class, javaDoc = "간접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "inDirectCost2Expr", type = String.class, javaDoc = "간접경비2", initialValue = "\"0\""),
        @GeneratedProperty(name = "manageMtrLogisExpr", type = String.class, javaDoc = "원재료물류비", initialValue = "\"0\""),
        @GeneratedProperty(name = "manageMtrdutieExpr", type = String.class, javaDoc = "원재료관세", initialValue = "\"0\""),
        @GeneratedProperty(name = "mtrLtCostExpr", type = String.class, javaDoc = "공정물류비", initialValue = "\"0\""),
        @GeneratedProperty(name = "mtrLtRateExpr", type = String.class, javaDoc = "공정관세", initialValue = "\"0\""),
        @GeneratedProperty(name = "payCostLtExpr", type = String.class, javaDoc = "납입물류비", initialValue = "\"0\""),
        @GeneratedProperty(name = "payRateLtExpr", type = String.class, javaDoc = "납입관세", initialValue = "\"0\""),
        @GeneratedProperty(name = "indirectCostRnd", type = String.class, javaDoc = "간접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "inDirectCost", type = String.class, javaDoc = "간접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldReducePrice", type = String.class, javaDoc = "금형감가비", initialValue = "\"0\""),
        @GeneratedProperty(name = "outUnitCostVal", type = String.class, javaDoc = "외주단가", initialValue = "\"0\""),
        @GeneratedProperty(name = "apUnitCostVal", type = String.class, javaDoc = "후도금단가", initialValue = "\"0\""),
        @GeneratedProperty(name = "apUnitCostOption", type = String.class, javaDoc = "후도금비(옵션)", initialValue = "\"0\""),
        @GeneratedProperty(name = "corporateMarginCostExpr", type = String.class, javaDoc = "법인간마진비용", initialValue = "\"0\""),
        @GeneratedProperty(name = "outUnitCost", type = String.class, javaDoc = "외주가공비", initialValue = "\"0\""),
        @GeneratedProperty(name = "etcCost", type = String.class, javaDoc = "기타원가", initialValue = "\"0\""),
        @GeneratedProperty(name = "coManageExpr", type = String.class, javaDoc = "일반관리비", initialValue = "\"0\""),
        @GeneratedProperty(name = "defectCostExpr", type = String.class, javaDoc = "불량비율", initialValue = "\"0\""),

        @GeneratedProperty(name = "scrapSalesCost", type = String.class, javaDoc = "스크랩매출", initialValue = "\"0\""),

        @GeneratedProperty(name = "dfDirectLaborCost", type = String.class, javaDoc = "가격차이-직접인건비", initialValue = "\"0\""),
        @GeneratedProperty(name = "dfInDirectLaborCost", type = String.class, javaDoc = "가격차이-간접인건비", initialValue = "\"0\""),
        @GeneratedProperty(name = "dfMachineDep", type = String.class, javaDoc = "가격차이-설비감가비", initialValue = "\"0\""),
        @GeneratedProperty(name = "dfDirectExpenses", type = String.class, javaDoc = "가격차이-직접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "dfInDirectExpenses", type = String.class, javaDoc = "가격차이-간접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "dfMoldDep", type = String.class, javaDoc = "가격차이-금형감가", initialValue = "\"0\""),

        @GeneratedProperty(name = "outputExpr", type = String.class, javaDoc = "수량차이-시간당생산량", initialValue = "\"0\""),
        @GeneratedProperty(name = "dfCt", type = String.class, javaDoc = "수량차이-CT", initialValue = "\"0\""),

        @GeneratedProperty(name = "ctSPMMold", type = String.class, initialValue = "\"0\"", javaDoc = "수량차이-CT(금형)"),
        @GeneratedProperty(name = "ctSPMAssemble", type = String.class, initialValue = "\"0\"", javaDoc = "수량차이-CT(설비)"),
        @GeneratedProperty(name = "cv", type = String.class, initialValue = "\"0\"", javaDoc = "수량차이-CV"),
        @GeneratedProperty(name = "cvMold", type = String.class, initialValue = "\"0\"", javaDoc = "수량차이-CV(금형)"),
        @GeneratedProperty(name = "cvAssemble", type = String.class, initialValue = "\"0\"", javaDoc = "수량차이-CV(설비)"),
        @GeneratedProperty(name = "efficientRate", type = String.class, javaDoc = "수량차이-생산효율", initialValue = "\"0\""),
        @GeneratedProperty(name = "personQLH", type = String.class, javaDoc = "수량차이-인시당생산량", initialValue = "\"0\""),
        @GeneratedProperty(name = "convertWorker", type = String.class, javaDoc = "수량차이-투입인원(계산)", initialValue = "\"0\""),
        @GeneratedProperty(name = "unitManage", type = String.class, javaDoc = "수량차이-관리대수", initialValue = "\"0\""),
        @GeneratedProperty(name = "worker", type = String.class, initialValue = "\"0\"", javaDoc = "수량차이-투입인원(설비)"),
        @GeneratedProperty(name = "sortLocation", type = Integer.class),

        @GeneratedProperty(name = "parentPartNo", type = String.class, javaDoc = "재료비 가공 -1레벨 상위 partNo"),
        @GeneratedProperty(name = "parentPartUs", type = String.class, javaDoc = "재료비 가공 -1레벨 상위 partNo의 u/s"),
        @GeneratedProperty(name = "bomLevel", type = String.class, javaDoc = "재료비 가공 -bom level"),
        @GeneratedProperty(name = "targetPartNo", type = String.class, javaDoc = "재료비 가공 - 부품번호(이관시 기준이 되는 품번)"),
        @GeneratedProperty(name = "targetPartName", type = String.class, javaDoc = "재료비 가공 - 부품명(이관시 기준이 되는 품명)"),
        @GeneratedProperty(name = "sujiPartNo", type = String.class, javaDoc = "재료비 가공 - 수지제번 (원재료번호)"),
        @GeneratedProperty(name = "sujiPartName", type = String.class, javaDoc = "재료비 가공 - 수지명칭 (원재료명)"),
        @GeneratedProperty(name = "metalPartNo", type = String.class, javaDoc = "재료비 가공 - 비철제번 (원재료번호)"),
        @GeneratedProperty(name = "metalPartName", type = String.class, javaDoc = "재료비 가공 - 비철명칭 (원재료명)"),
        @GeneratedProperty(name = "lastCompany", type = String.class, javaDoc = "최종 업체명(수지나 비철일 경우 maker, 그게 아니면 company)"),
        @GeneratedProperty(name = "materialCompany", type = String.class, javaDoc = "원재료 업체명 (수지,비철) 아직 원가프로그램에 없는 컬럼"),
        @GeneratedProperty(name = "lastUnitCost", type = String.class, javaDoc = "단가 - (구매품 or 수지 or 비철)"),
        @GeneratedProperty(name = "lastUnit", type = String.class, javaDoc = "단위 - (구매품 or 수지 or 비철) EA, G"),
        @GeneratedProperty(name = "lastMonetaryUnit", type = String.class, javaDoc = "통화 - (구매품 or 수지 or 비철)"),
        @GeneratedProperty(name = "pUnitCost", type = String.class, javaDoc = "구매단가"),
        @GeneratedProperty(name = "pMonetaryUnit", type = String.class, javaDoc = "구매단가 화폐단위"),
        @GeneratedProperty(name = "pUnit", type = String.class, initialValue = "\"EA\"", javaDoc = "기준단위 - EA 로 하드코딩"),
        @GeneratedProperty(name = "mtrSujiPrice", type = String.class, javaDoc = "수지 원재료 단가"),
        @GeneratedProperty(name = "mtrSujiMonetaryUnit", type = String.class, initialValue = "\"KRW\"", javaDoc = "수지화폐단위 - KRW로 하드코딩"),
        @GeneratedProperty(name = "mtrSujiUnit", type = String.class, initialValue = "\"EA\"", javaDoc = "수지기준단위 - EA 로 하드코딩"),
        @GeneratedProperty(name = "mtrMetalPrice", type = String.class, javaDoc = "비철 원재료 단가"),
        @GeneratedProperty(name = "mtrMetalMonetaryUnit", type = String.class, initialValue = "\"KRW\"", javaDoc = "비철화폐단위 - KRW로 하드코딩"),
        @GeneratedProperty(name = "mtrMetalUnit", type = String.class, initialValue = "\"EA\"", javaDoc = "비철기준단위 - EA 로 하드코딩"),
        @GeneratedProperty(name = "metalLmeStd", type = String.class, javaDoc = "LME시세"),
        @GeneratedProperty(name = "pMonetaryUnitCurrency", type = String.class, javaDoc = "구매 환율"),

        @GeneratedProperty(name = "qaDiffInput", type = String.class, javaDoc = "수량차이-투입"),
        @GeneratedProperty(name = "qaDiffUnit", type = String.class, javaDoc = "수량차이-단위"),

        @GeneratedProperty(name = "productLossRate", type = String.class, javaDoc = "생산Loss율"),
        @GeneratedProperty(name = "productWeight", type = String.class, javaDoc = "제품중량"),
        @GeneratedProperty(name = "scrapWeight", type = String.class, javaDoc = "스크랩중량"),
        @GeneratedProperty(name = "totalWeight", type = String.class, javaDoc = "총중량"),

        @GeneratedProperty(name = "transferDate", type = Timestamp.class, javaDoc = "ERP전송일"),
        @GeneratedProperty(name = "transferFlag", type = String.class, javaDoc = "ERP전송여부"),
        @GeneratedProperty(name = "transferMsg", type = String.class, javaDoc = "ERP전송로그", constraints = @PropertyConstraints(upperLimit = 4000)) }, foreignKeys = {
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "parentHistory", type = ext.ket.cost.entity.CostInterfaceHistory.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostInterfaceChildHistory", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "task", type = e3ps.project.E3PSTask.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostInterfaceChildHistory", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "costPart", type = ext.ket.cost.entity.CostPart.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostInterfaceChildHistory", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "project", type = e3ps.project.E3PSProjectMaster.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostInterfaceChildHistory", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "partList", type = ext.ket.cost.entity.PartList.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostInterfaceChildHistory", cardinality = Cardinality.ONE)) })
public class CostInterfaceChildHistory extends _CostInterfaceChildHistory {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return CostInterfaceChildHistory
     * @exception wt.util.WTException
     **/
    public static CostInterfaceChildHistory newCostInterfaceChildHistory() throws WTException {

	CostInterfaceChildHistory instance = new CostInterfaceChildHistory();
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
