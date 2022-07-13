/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.cost.entity;

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
 * 부품
 * <p>
 * Use the <code>newCostPart</code> static factory method(s), not the <code>CostPart</code>
 * constructor, to construct instances of this class.  Instances must be
 * constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

/**
 * 
 * 
 ********************************************************************************************************************************************************************************************************************************** 
 ********************************************************************************************************************************************************************************************************************************** 
 ********************************************************************************************************************************************************************************************************************************** 
 * ******************************************************************* *******************************************************************
 * ******************************************************************* !!!!!!!!!!!!!!!!!!!!!!!!!!!!! 컬럼 추가 시 주의
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! *******************************************************************
 * ******************************************************************* *******************************************************************
 * ******************************************************************* *******************************************************************
 * ******************************************************************* *******************************************************************
 * ******************************************************************* 환율 필드 추가시 반드시 명명규칙을 지켜야한다. 예를 들어 화폐단위 컬럼이 xxxUnit 이면 환율 필드는
 * xxxUnitCurrency 로 할 것 !!!!!!!!!!!!!!!!!!! ******************************************************************* updatePartCurrency 메서드 에서
 * part 동기화시 해당 규칙을 활용함 *******************************************************************
 * ******************************************************************* *******************************************************************
 * ******************************************************************* *******************************************************************
 * *******************************************************************
 ********************************************************************************************************************************************************************************************************************************** 
 ********************************************************************************************************************************************************************************************************************************** 
 ********************************************************************************************************************************************************************************************************************************** 
 */

@GenAsPersistable(interfaces = { Tree.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "version", type = Integer.class, initialValue = "0", javaDoc = "버전"),
        @GeneratedProperty(name = "devType", type = String.class, initialValue = "\"0\"", javaDoc = "개발/검토"),
        @GeneratedProperty(name = "partType", type = String.class, javaDoc = "제품구분"),
        @GeneratedProperty(name = "bomLevel", type = String.class, javaDoc = "레벨"),
        @GeneratedProperty(name = "sizeW", type = String.class, initialValue = "\"0\"", javaDoc = "제품 SIZE W"),
        @GeneratedProperty(name = "sizeL", type = String.class, initialValue = "\"0\"", javaDoc = "제품 SIZE L"),
        @GeneratedProperty(name = "sizeH", type = String.class, initialValue = "\"0\"", javaDoc = "제품 SIZE H"),
        @GeneratedProperty(name = "partNo", type = String.class, javaDoc = "임시번호"),
        @GeneratedProperty(name = "partName", type = String.class, javaDoc = "품명"),
        @GeneratedProperty(name = "us", type = String.class, javaDoc = "U/S"),
        @GeneratedProperty(name = "cvMold", type = String.class, initialValue = "\"0\"", javaDoc = "생산정보 - C/V - 금형"),
        @GeneratedProperty(name = "cvAssemble", type = String.class, initialValue = "\"0\"", javaDoc = "생산정보 - C/V - 조립"),
        @GeneratedProperty(name = "ctSPMMold", type = String.class, initialValue = "\"0\"", javaDoc = "생산정보 - C/T&SPM - 금형"),
        @GeneratedProperty(name = "ctSPMAssemble", type = String.class, initialValue = "\"0\"", javaDoc = "생산정보 - C/T&SPM - 조립"),
        @GeneratedProperty(name = "mftFactory1", type = String.class, javaDoc = "생산정보 - 생산국(입고처)"),
        @GeneratedProperty(name = "mftFactory2", type = String.class, javaDoc = "생산지(유/무상)"),
        @GeneratedProperty(name = "company", type = String.class, javaDoc = "생산정보 - 업체명"),
        @GeneratedProperty(name = "facilities", type = String.class, initialValue = "\"0\"", javaDoc = "생산정보 - 적용설비(Ton)"),
        @GeneratedProperty(name = "worker", type = String.class, initialValue = "\"0\"", javaDoc = "생산정보 - 작업인원(명)"),
        @GeneratedProperty(name = "tos", type = String.class, javaDoc = "구매/외주 - 선적조건"),
        @GeneratedProperty(name = "duty", type = String.class, initialValue = "\"0\"", javaDoc = "구매/외주 - 관세(%)"),
        @GeneratedProperty(name = "distributionCost", type = String.class, initialValue = "\"0\"", javaDoc = "구매/외주 - 물류비(%)"),
        @GeneratedProperty(name = "pUnitCost", type = String.class, initialValue = "\"0\"", javaDoc = "구매/외주 - 구매단가"),
        @GeneratedProperty(name = "pMonetaryUnit", type = String.class, javaDoc = "구매/외주 - 구매단가 화폐단위"),
        @GeneratedProperty(name = "apUnitCost", type = String.class, initialValue = "\"0\"", javaDoc = "구매/외주 - 후도금단가"),
        @GeneratedProperty(name = "apMonetaryUnit", type = String.class, javaDoc = "구매/외주 - 후도금단가 화폐단위"),
        @GeneratedProperty(name = "apPlatingSpec", type = String.class, javaDoc = "구매/외주 - 후도금 사양"),
        @GeneratedProperty(name = "apUnitCostVal", type = String.class, initialValue = "\"0\"", javaDoc = "구매/외주 - 후도금단가(환율적용)"),
        @GeneratedProperty(name = "outUnitCost", type = String.class, initialValue = "\"0\"", javaDoc = "구매/외주 - 외주단가"),
        @GeneratedProperty(name = "outMonetaryUnit", type = String.class, javaDoc = "구매/외주 - 외주단가 화폐단위"),
        @GeneratedProperty(name = "outUnitCostVal", type = String.class, initialValue = "\"0\"", javaDoc = "구매/외주 - 외주단가(환율적용)"),
        @GeneratedProperty(name = "productWeight", type = String.class, initialValue = "\"0\"", javaDoc = "제품중량 - 제품중량"),
        @GeneratedProperty(name = "scrapWeight", type = String.class, initialValue = "\"0\"", javaDoc = "제품중량 - 스크랩중량"),
        @GeneratedProperty(name = "totalWeight", type = String.class, initialValue = "\"0\"", javaDoc = "제품중량 - 총중량"),
        @GeneratedProperty(name = "moldOrder", type = String.class, javaDoc = "금형 투자비 - 투자오더"),
        @GeneratedProperty(name = "moldDieNo", type = String.class, javaDoc = "금형 투자비 - Die No"),
        @GeneratedProperty(name = "moldStructure", type = String.class, javaDoc = "금형 투자비 - 금형구조"),
        @GeneratedProperty(name = "moldMftDivision", type = String.class, javaDoc = "금형 투자비 - 제작구분"),
        @GeneratedProperty(name = "moldNFactory", type = String.class, javaDoc = "금형 투자비 - 신규 - 제작처"),
        @GeneratedProperty(name = "moldNIC", type = String.class, initialValue = "\"0\"", javaDoc = "금형 투자비 - 신규 - 투자비"),
        @GeneratedProperty(name = "moldNICMUnit", type = String.class, javaDoc = "금형 투자비 - 신규 - 투자비 화폐단위"),
        @GeneratedProperty(name = "moldNPay", type = String.class, initialValue = "\"0\"", javaDoc = "금형 투자비 - 신규 - 지급액"),
        @GeneratedProperty(name = "moldNPayMUnit", type = String.class, javaDoc = "금형 투자비 - 신규 - 지급액 화폐단위"),
        @GeneratedProperty(name = "moldMPFactory", type = String.class, javaDoc = "금형 투자비 - 양산 - 제작처"),
        @GeneratedProperty(name = "moldMPIC", type = String.class, initialValue = "\"0\"", javaDoc = "금형 투자비 - 양산 - 투자비"),
        @GeneratedProperty(name = "moldMPICMUnit", type = String.class, javaDoc = "금형 투자비 - 양산 - 투자비 화폐단위"),
        @GeneratedProperty(name = "moldMPQTotal", type = String.class, initialValue = "\"0\"", javaDoc = "금형 투자비 - 양산수량 - Total"),
        @GeneratedProperty(name = "moldMPQMax", type = String.class, initialValue = "\"0\"", javaDoc = "금형 투자비 - 양산수량 - MAX"),
        @GeneratedProperty(name = "facOrder", type = String.class, javaDoc = "설비 투자비 - 투자오더"),
        @GeneratedProperty(name = "facMftDivision", type = String.class, javaDoc = "설비 투자비 - 제작구분"),
        @GeneratedProperty(name = "facNFactory", type = String.class, javaDoc = "설비 투자비 - 신규 - 제작처"),
        @GeneratedProperty(name = "facNIC", type = String.class, initialValue = "\"0\"", javaDoc = "설비 투자비 - 신규 - 투자비"),
        @GeneratedProperty(name = "facNICMUnit", type = String.class, javaDoc = "설비 투자비 - 신규 - 투자비 화폐단위"),
        @GeneratedProperty(name = "facNPay", type = String.class, initialValue = "\"0\"", javaDoc = "설비 투자비 - 신규 - 지급액"),
        @GeneratedProperty(name = "facNPayMUnit", type = String.class, javaDoc = "설비 투자비 - 신규 - 지급액 화폐단위"),
        @GeneratedProperty(name = "facMPFactory", type = String.class, javaDoc = "설비 투자비 - 양산 - 제작처"),
        @GeneratedProperty(name = "facMPIC", type = String.class, initialValue = "\"0\"", javaDoc = "설비 투자비 - 양산 - 투자비"),
        @GeneratedProperty(name = "facMPICMUnit", type = String.class, javaDoc = "설비 투자비 - 양산 - 투자비 화폐단위"),
        @GeneratedProperty(name = "facMPQTotal", type = String.class, initialValue = "\"0\"", javaDoc = "설비 투자비 - 양산수량 - Total"),
        @GeneratedProperty(name = "facMPQMax", type = String.class, initialValue = "\"0\"", javaDoc = "설비 투자비 - 양산수량 - Max"),
        @GeneratedProperty(name = "etcNIC", type = String.class, initialValue = "\"0\"", javaDoc = "기타 투자비 - 신규 - 투자비(합계)"),
        @GeneratedProperty(name = "etcNPay", type = String.class, initialValue = "\"0\"", javaDoc = "기타 투자비 - 신규 - 지급액(합계)"),
        @GeneratedProperty(name = "etcMPIC", type = String.class, initialValue = "\"0\"", javaDoc = "기타 투자비 - 양산 - 투자비(합계)"),
        @GeneratedProperty(name = "etcMPQTotal", type = String.class, initialValue = "\"0\"", javaDoc = "기타 투자비 - 양산수량 - Total"),
        @GeneratedProperty(name = "etcMPQMax", type = String.class, initialValue = "\"0\"", javaDoc = "기타 투자비 - 양산수량 - Max"),
        @GeneratedProperty(name = "etcMPFactory", type = String.class, javaDoc = "기타 투자비 - 양산 - 제작처"),
        @GeneratedProperty(name = "etcNPFactory", type = String.class, javaDoc = "기타 투자비 - 신규 - 제작처"),
        @GeneratedProperty(name = "itemName", type = String.class, javaDoc = "항목"),
        @GeneratedProperty(name = "rMatNMetalName", type = String.class, javaDoc = "원재료정보 - 비철원재료 - 원재료명"),
        @GeneratedProperty(name = "prePlatingSpec", type = String.class, javaDoc = "원재료정보 - 비철원재료 - 선도금사양"),
        @GeneratedProperty(name = "prePlatingCost", type = String.class, initialValue = "\"0\"", javaDoc = "구매/도금/외주 - 선도금단가"),
        @GeneratedProperty(name = "prePlatingUnit", type = String.class, javaDoc = "구매/도금/외주 - 선도금단가 화폐단위"),
        @GeneratedProperty(name = "rMatNMetalT", type = String.class, initialValue = "\"0\"", javaDoc = "원재료정보 - 비철원재료 - 두께"),
        @GeneratedProperty(name = "rMatNMetalW", type = String.class, initialValue = "\"0\"", javaDoc = "원재료정보 - 비철원재료 - 폭"),
        @GeneratedProperty(name = "rMatNMetalP", type = String.class, initialValue = "\"0\"", javaDoc = "원재료정보 - 비철원재료 - 피치"),
        @GeneratedProperty(name = "costDeliver", type = String.class, javaDoc = "입고처"),
        @GeneratedProperty(name = "moldReducePrice", type = String.class, initialValue = "\"0\"", javaDoc = "금형감가비합계"),
        @GeneratedProperty(name = "facReducePrice", type = String.class, initialValue = "\"0\"", javaDoc = "설비감가비합계"),
        @GeneratedProperty(name = "etcReducePrice", type = String.class, initialValue = "\"0\"", javaDoc = "기타감가비합계"),
        @GeneratedProperty(name = "workHour", type = String.class, // initialValue="\"21\"",
        javaDoc = "조업도(시간)"),
        @GeneratedProperty(name = "workDay", type = String.class, // initialValue="\"20\"",
        javaDoc = "조업도(일)"),
        @GeneratedProperty(name = "workYear", type = String.class, // initialValue="\"12\"",
        javaDoc = "년조업도(월)"),
        @GeneratedProperty(name = "eaOutput", type = String.class, initialValue = "\"0\"", javaDoc = "년 생산량(EA)"),
        @GeneratedProperty(name = "capa", type = String.class, initialValue = "\"0\"", javaDoc = "Capa 분석결과"),
        @GeneratedProperty(name = "capaNote", type = String.class, javaDoc = "Capa 비고"),
        @GeneratedProperty(name = "mtrSujiPrice", type = String.class, initialValue = "\"0\"", javaDoc = "수지 원재료 단가"),
        @GeneratedProperty(name = "mtrMetalPrice", type = String.class, initialValue = "\"0\"", javaDoc = "비철 원재료 단가"),
        @GeneratedProperty(name = "mtrLtRate", type = String.class, initialValue = "\"0\"", javaDoc = "원재료 관세율"),
        @GeneratedProperty(name = "mtrLtCost", type = String.class, initialValue = "\"0\"", javaDoc = "원재료 물류비용"),
        @GeneratedProperty(name = "partLtRate", type = String.class, initialValue = "\"0\"", javaDoc = "부품 관세율"),
        @GeneratedProperty(name = "partLtCost", type = String.class, initialValue = "\"0\"", javaDoc = "부품 물류비용"),
        @GeneratedProperty(name = "productLossRate", type = String.class, initialValue = "\"0\"", javaDoc = "생산 Loss 율"),
        @GeneratedProperty(name = "assyLossExpr", type = String.class, javaDoc = "조립Loss", initialValue = "\"0\""),
        @GeneratedProperty(name = "assyLossRate", type = String.class, javaDoc = "조립Loss율", initialValue = "\"0\""),
        @GeneratedProperty(name = "coManageCost", type = String.class, javaDoc = "일반관리비용", initialValue = "\"0\""),
        @GeneratedProperty(name = "coManageExpr", type = String.class, javaDoc = "일반관리비", initialValue = "\"0\""),
        @GeneratedProperty(name = "coManageRate", type = String.class, javaDoc = "일반관리비율", initialValue = "\"0\""),
        @GeneratedProperty(name = "defectCostExpr", type = String.class, javaDoc = "불량비용", initialValue = "\"0\""),
        @GeneratedProperty(name = "defectRate", type = String.class, javaDoc = "불량율", initialValue = "\"0\""),
        @GeneratedProperty(name = "directCostExpr", type = String.class, javaDoc = "직접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "efficientRate", type = String.class, javaDoc = "생산효율", initialValue = "\"0\""),
        @GeneratedProperty(name = "elecCost", type = String.class, javaDoc = "표준전력비", initialValue = "\"0\""),
        @GeneratedProperty(name = "elecCostClimbRate", type = String.class, javaDoc = "표준전력비상승율", initialValue = "\"0\""),
        @GeneratedProperty(name = "elecCostExpr", type = String.class, javaDoc = "전력비", initialValue = "\"0\""),
        @GeneratedProperty(name = "elecCostYear", type = String.class, javaDoc = "표준전력비년도", initialValue = "\"0\""),
        @GeneratedProperty(name = "expenseExpr", type = String.class, javaDoc = "경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "inDirectCost", type = String.class, javaDoc = "간접경비비용", initialValue = "\"0\""),
        @GeneratedProperty(name = "inDirectCostExpr", type = String.class, javaDoc = "간접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "inDirectCost2Expr", type = String.class, javaDoc = "간접경비2", initialValue = "\"0\""),
        @GeneratedProperty(name = "indirectCostRate", type = String.class, javaDoc = "간접경비율", initialValue = "\"0\""),
        @GeneratedProperty(name = "buyBackIndirectCostRate", type = String.class, javaDoc = "BuyBack간접경비율(본사)", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborCostClimbRate", type = String.class, javaDoc = "표준임율상승율", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborCostExpr", type = String.class, javaDoc = "노무비", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborCostRate", type = String.class, javaDoc = "표준임율", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborCostYear", type = String.class, javaDoc = "표준임율년도", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborRateExpr", type = String.class, javaDoc = "임율(표준)", initialValue = "\"0\""),
        @GeneratedProperty(name = "machineDpcCost", type = String.class, javaDoc = "기계감가(사출,프레스)", initialValue = "\"0\""),
        @GeneratedProperty(name = "machineDpcCostExpr", type = String.class, javaDoc = "기계감가", initialValue = "\"0\""),
        @GeneratedProperty(name = "machineReduceCost", type = String.class, javaDoc = "기계감가(표준)", initialValue = "\"0\""),
        @GeneratedProperty(name = "materialCostExpr", type = String.class, javaDoc = "재료비", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalScrapCost", type = String.class, javaDoc = "비철스크랩판매", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalScrapRecycle", type = String.class, javaDoc = "비철재생단가", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldMaintenance", type = String.class, javaDoc = "금형유지비", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldReadyTime", type = String.class, javaDoc = "금형준비시간", initialValue = "\"0\""),
        @GeneratedProperty(name = "mtrLtCostExpr", type = String.class, javaDoc = "공정물류비", initialValue = "\"0\""),
        @GeneratedProperty(name = "mtrLtRateExpr", type = String.class, javaDoc = "공정관세", initialValue = "\"0\""),
        @GeneratedProperty(name = "mtrManageExpr", type = String.class, javaDoc = "재료관리비", initialValue = "\"0\""),
        @GeneratedProperty(name = "mtrManageRate", type = String.class, javaDoc = "재료관리비율", initialValue = "\"0\""),
        @GeneratedProperty(name = "outputExpr", type = String.class, javaDoc = "생산량(표준)", initialValue = "\"0\""),
        @GeneratedProperty(name = "partTotalCost", type = String.class, javaDoc = "부품전체합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "payCostLtExpr", type = String.class, javaDoc = "납입물류비", initialValue = "\"0\""),
        @GeneratedProperty(name = "payLtCost", type = String.class, javaDoc = "납입물류비용", initialValue = "\"0\""),
        @GeneratedProperty(name = "payRateLt", type = String.class, javaDoc = "납입관세율", initialValue = "\"0\""),
        @GeneratedProperty(name = "payRateLtExpr", type = String.class, javaDoc = "납입관세", initialValue = "\"0\""),
        @GeneratedProperty(name = "productHaveMonth", type = String.class, javaDoc = "생산보유개월", initialValue = "\"0\""),
        @GeneratedProperty(name = "productLossExpr", type = String.class, javaDoc = "생산Loss", initialValue = "\"0\""),
        @GeneratedProperty(name = "pMonetaryUnitCurrency", type = String.class, javaDoc = "구매단가환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "rawMaterialCostExpr", type = String.class, javaDoc = "원재료비", initialValue = "\"0\""),
        @GeneratedProperty(name = "reduceCostExpr", type = String.class, javaDoc = "감가비", initialValue = "\"0\""),
        @GeneratedProperty(name = "rnd", type = String.class, javaDoc = "R&D율", initialValue = "\"0\""),
        @GeneratedProperty(name = "rndExpr", type = String.class, javaDoc = "R&D", initialValue = "\"0\""),
        @GeneratedProperty(name = "scrapSalesCostExpr", type = String.class, javaDoc = "스크랩판매비", initialValue = "\"0\""),
        @GeneratedProperty(name = "sujiScrapCost", type = String.class, javaDoc = "수지스크랩판매", initialValue = "\"0\""),
        @GeneratedProperty(name = "sujiScrapRecycle", type = String.class, javaDoc = "수지재생단가", initialValue = "\"0\""),
        @GeneratedProperty(name = "tabaryu", type = String.class, javaDoc = "타발유(표준)", initialValue = "\"0\""),
        @GeneratedProperty(name = "tabaryuExpr", type = String.class, javaDoc = "타발유", initialValue = "\"0\""),
        @GeneratedProperty(name = "unitManage", type = String.class, javaDoc = "관리대수", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalImportance", type = String.class, javaDoc = "비철-비중", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalScenario", type = String.class, javaDoc = "비철-시나리오"),
        @GeneratedProperty(name = "metalLmeStd", type = String.class, javaDoc = "비철-LME기준", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalLmeCost", type = String.class, javaDoc = "비철-LME금액", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalPlateCost", type = String.class, javaDoc = "비철-도금단가", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalCuttingCost", type = String.class, javaDoc = "비철-절단비", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalDeTinCost", type = String.class, javaDoc = "비철-DeTin", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalTollPrcCost", type = String.class, javaDoc = "비철-임가공비", initialValue = "\"0\""),
        @GeneratedProperty(name = "metalPartNo", type = String.class, javaDoc = "비철-원자재번호"),
        @GeneratedProperty(name = "sujiPartNo", type = String.class, javaDoc = "수지-원자재번호"),
        @GeneratedProperty(name = "sujiPartName", type = String.class, javaDoc = "수지-원자재명"),
        @GeneratedProperty(name = "sujiGrade", type = String.class, javaDoc = "수지-Grade"),
        @GeneratedProperty(name = "sujiColor", type = String.class, javaDoc = "수지-Color"),
        @GeneratedProperty(name = "apMonetaryUnitCurrency", type = String.class, javaDoc = "후도금단가 환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "outMonetaryUnitCurrency", type = String.class, javaDoc = "외주단가 환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldNICMUnitCurrency", type = String.class, javaDoc = "금형투자비(신규) 환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldNPayMUnitCurrency", type = String.class, javaDoc = "금형지급액(신규) 환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldMPICMUnitCurrency", type = String.class, javaDoc = "금형투자비(양산) 환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "facNICMUnitCurrency", type = String.class, javaDoc = "설비투자비(신규) 환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "facNPayMUnitCurrency", type = String.class, javaDoc = "설비지급액(신규) 환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "facMPICMUnitCurrency", type = String.class, javaDoc = "설비투자비(양산) 환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "prePlatingUnitCurrency", type = String.class, javaDoc = "선도금단가환율", initialValue = "\"0\""),
        @GeneratedProperty(name = "calcStdMaterial", type = String.class, javaDoc = "산출기준(재료비)"),
        @GeneratedProperty(name = "calcStdLabor", type = String.class, javaDoc = "산출기준(노무비)"),
        @GeneratedProperty(name = "calcStdExpense", type = String.class, javaDoc = "산출기준(경비)"),
        @GeneratedProperty(name = "calcStdManage", type = String.class, javaDoc = "산출기준(관리비)"),
        @GeneratedProperty(name = "calcStdOutPut", type = String.class, javaDoc = "산출기준(생산량)"),
        @GeneratedProperty(name = "calcOptionMaterial", type = String.class, javaDoc = "산출옵션(재료비)"),
        @GeneratedProperty(name = "calcOptionManage", type = String.class, javaDoc = "산출옵션(관리비)"),
        @GeneratedProperty(name = "calcOptionLabor", type = String.class, javaDoc = "산출옵션(노무비)"),
        @GeneratedProperty(name = "packBoxUnit", type = String.class, javaDoc = "포장수량(Box)", initialValue = "\"0\""),
        @GeneratedProperty(name = "packPalletUnit", type = String.class, javaDoc = "포장수량(pallet)", initialValue = "\"0\""),
        @GeneratedProperty(name = "packUnitPriceSum", type = String.class, javaDoc = "개별포장비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "quantityTotal", type = String.class, javaDoc = "예상수량합계(Total 수량)", initialValue = "\"0\""),
        @GeneratedProperty(name = "quantityAvg", type = String.class, javaDoc = "Avg 수량", initialValue = "\"0\""),
        @GeneratedProperty(name = "quantityMax", type = String.class, javaDoc = "Max 수량", initialValue = "\"0\""),
        @GeneratedProperty(name = "sopYear", type = String.class, javaDoc = "SOP년도"),
        @GeneratedProperty(name = "payPlace", type = String.class, javaDoc = "완제품입고처"),
        @GeneratedProperty(name = "totalCostExpr", type = String.class, javaDoc = "총원가", initialValue = "\"0\""),
        @GeneratedProperty(name = "manageCostExpr", type = String.class, javaDoc = "관리비", initialValue = "\"0\""),
        @GeneratedProperty(name = "salesTargetCostExpr", type = String.class, javaDoc = "판매목표가", initialValue = "\"0\""),
        @GeneratedProperty(name = "profitRateExpr", type = String.class, javaDoc = "수익율", initialValue = "\"0\""),
        @GeneratedProperty(name = "mfcCostExpr", type = String.class, javaDoc = "제조원가", initialValue = "\"0\""),
        @GeneratedProperty(name = "materialCostTotal", type = String.class, javaDoc = "제품 원재료비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborCostTotal", type = String.class, javaDoc = "제품 노무비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "expenseTotal", type = String.class, javaDoc = "제품 경비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "mfcCostTotal", type = String.class, javaDoc = "제품 제조원가 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "manageCostTotal", type = String.class, javaDoc = "제품 관리비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "reduceCostTotal", type = String.class, javaDoc = "제품 감가비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "productCostTotal", type = String.class, javaDoc = "제품 총원가 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "productNInvestTotal", type = String.class, javaDoc = "제품 신규 투자비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldinvestPriceTotal", type = String.class, javaDoc = "금형 신규 투자비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "pressinvestPriceTotal", type = String.class, javaDoc = "Press 신규 투자비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "equipinvestPriceTotal", type = String.class, javaDoc = "조립 설비 신규 투자비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "purchaseinvestPriceTotal", type = String.class, javaDoc = "구매 금형비 신규 투자비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "etcinvestPriceTotal", type = String.class, javaDoc = "기타 신규 투자비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "subVersion", type = Integer.class, initialValue = "0", javaDoc = "case버전"),
        @GeneratedProperty(name = "caseNote", type = String.class, javaDoc = "case비고"),
        @GeneratedProperty(name = "capaMaxQty", type = String.class, javaDoc = "capa max물량", initialValue = "\"0\""),
        @GeneratedProperty(name = "caseOrder", type = Integer.class, javaDoc = "case순서"),
        @GeneratedProperty(name = "lastest", type = boolean.class, initialValue = "false"),
        @GeneratedProperty(name = "subCostAllTotal", type = String.class, javaDoc = "부품합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "subCostAllTotalOption", type = String.class, javaDoc = "부품합계(옵션)", initialValue = "\"0\""),
        @GeneratedProperty(name = "subCostExceptTotal", type = String.class, javaDoc = "지정품합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourCt_1", type = String.class, javaDoc = "추가공수CT_1", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourEff_1", type = String.class, javaDoc = "추가공수효율_1", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourLb_1", type = String.class, javaDoc = "추가임율_1", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourClb_1", type = String.class, javaDoc = "추가임율상승율_1", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourYear_1", type = String.class, javaDoc = "추가임율년도_1", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourCt_2", type = String.class, javaDoc = "추가공수CT_2", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourEff_2", type = String.class, javaDoc = "추가공수효율_2", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourLb_2", type = String.class, javaDoc = "추가임율_2", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourClb_2", type = String.class, javaDoc = "추가임율상승율_2", initialValue = "\"0\""),
        @GeneratedProperty(name = "addManHourYear_2", type = String.class, javaDoc = "추가임율년도_2", initialValue = "\"0\""),
        @GeneratedProperty(name = "packUnitPriceOption", type = String.class, javaDoc = "개별포장비 합계(옵션)", initialValue = "\"0\""),
        @GeneratedProperty(name = "apUnitCostOption", type = String.class, javaDoc = "후도금비(옵션)", initialValue = "\"0\""),
        @GeneratedProperty(name = "coManageRateOption", type = String.class, javaDoc = "일반관리비율(옵션)", initialValue = "\"0\""),
        @GeneratedProperty(name = "subCostExceptTotalOption", type = String.class, javaDoc = "지정품합계(옵션)", initialValue = "\"0\""),
        @GeneratedProperty(name = "rndOption", type = String.class, javaDoc = "R&D율(옵션)", initialValue = "\"0\""),
        @GeneratedProperty(name = "capaYear", type = String.class, javaDoc = "적용년수(capa)" // ,initialValue="\"6\""
        ),
        @GeneratedProperty(name = "reportUS", type = String.class, javaDoc = "보고서U/S", initialValue = "\"1\""),
        @GeneratedProperty(name = "drStep", type = String.class, javaDoc = "DR단계"),
        @GeneratedProperty(name = "freeRawMaterialCostTotal", type = String.class, javaDoc = "무상 원재료비 합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "initFlag", type = String.class, javaDoc = "동기화여부"),
        @GeneratedProperty(name = "formulaVersion", type = Integer.class, initialValue = "0", javaDoc = "수식 버전"),
        @GeneratedProperty(name = "realPartNo", type = String.class, javaDoc = "품번"),
        @GeneratedProperty(name = "facReduceCtSpm", type = String.class, javaDoc = "범용감가CT(SPM)", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldProductAssyExpr", type = String.class, javaDoc = "노무비_수식_생산조립", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldPrepareExpr", type = String.class, javaDoc = "노무비_수식_금형준비", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborExpenseAdd1Expr", type = String.class, javaDoc = "노무비_수식_추가공수1", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborExpenseAdd2Expr", type = String.class, javaDoc = "노무비_수식_추가공수2", initialValue = "\"0\""),
        @GeneratedProperty(name = "manageMtrLogisExpr", type = String.class, javaDoc = "관리비_수식_원재료물류비", initialValue = "\"0\""),
        @GeneratedProperty(name = "manageMtrdutieExpr", type = String.class, javaDoc = "관리비_수식_원재료관세", initialValue = "\"0\""),
        @GeneratedProperty(name = "m_pallet_container", type = String.class, javaDoc = "원재료_pallet/Container", initialValue = "\"0\""),
        @GeneratedProperty(name = "p_pallet_container", type = String.class, javaDoc = "부품_pallet/Container", initialValue = "\"0\""),
        @GeneratedProperty(name = "deliverName", type = String.class, javaDoc = "납입처"),
        @GeneratedProperty(name = "disposableCr", type = String.class, javaDoc = "C/R적용율(1회성-최초 원가산출시에만 사용됨)", initialValue = "\"0\""),
        @GeneratedProperty(name = "disposableApplyYear", type = String.class, javaDoc = "C/R적용년수(1회성-최초 원가산출시에만 사용됨)", initialValue = "\"0\""),
        @GeneratedProperty(name = "attrLocked", type = String.class, javaDoc = "원가산출 기준정보 Lock", constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "salesTargetGb", type = String.class, javaDoc = "판가구분"),
        @GeneratedProperty(name = "subCostDefectTotal", type = String.class, javaDoc = "불량률 제외 부품합계", initialValue = "\"0\""),
        @GeneratedProperty(name = "corporateMarginRate", type = String.class, javaDoc = "법인간마진율", initialValue = "\"0\""),
        @GeneratedProperty(name = "corporateMarginCostExpr", type = String.class, javaDoc = "법인간마진비용", initialValue = "\"0\""),
        @GeneratedProperty(name = "indirectLabourRate", type = String.class, javaDoc = "간접인건비비율(노무비)", initialValue = "\"0\""),
        @GeneratedProperty(name = "indirectRndRate", type = String.class, javaDoc = "간접인건비비율(R&D)", initialValue = "\"0\""),
        /***
         * 
         * 
         * 환율 관련 필드 추가시 상단 주의 사항 확인 할 것!!!!!!!!!!
         * 
         * 
         * 
         */
        @GeneratedProperty(name = "sortLocation", type = Integer.class) }, foreignKeys = {
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "master", type = ext.ket.cost.entity.ProductMaster.class), myRole = @MyRole(name = "theCostPart", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "project", type = e3ps.project.E3PSProjectMaster.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostPart", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "costReportPartLink", foreignKeyRole = @ForeignKeyRole(name = "report", type = ext.ket.cost.entity.CostReport.class), myRole = @MyRole(name = "part", cardinality = Cardinality.ONE_TO_MANY)) })
public class CostPart extends _CostPart {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return CostPart
     * @exception wt.util.WTException
     **/
    public static CostPart newCostPart() throws WTException {

	CostPart instance = new CostPart();
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
