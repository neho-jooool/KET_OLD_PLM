package ext.ket.cost.code.sap;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.beans.ProjectHelper;
import e3ps.sap.RFCConnect;
import ext.ket.common.util.ObjectUtil;
import ext.ket.cost.code.service.CostCodeHelper;
import ext.ket.cost.entity.CostInterfaceChildHistory;
import ext.ket.cost.entity.CostInterfaceHistory;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostPartType;
import ext.ket.cost.entity.CostQuantity;
import ext.ket.cost.entity.CostReport;
import ext.ket.cost.entity.PartList;
import ext.ket.cost.service.CostHelper;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.cost.util.CostTreeUtil;
import ext.ket.cost.util.CostUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.code.service.CodeHelper;

public class ErpProductCostInterface implements RemoteAccess {

    public Map<String, Object> getCodeList() throws Exception {
	Map<String, Object> codeList = new HashMap<String, Object>();

	List<NumberCode> mftCodeList = NumberCodeHelper.manager.getNumberCodeList("MFTFACTORY");
	QueryResult qr = null;
	QuerySpec qs = new QuerySpec();
	qs.appendClassList(CostPartType.class, true);
	qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostPartType obj = (CostPartType) o[0];
	    codeList.put(CommonUtil.getOIDLongValue2Str(obj), obj.getName());
	}
	for (NumberCode obj : mftCodeList) {
	    codeList.put(CommonUtil.getOIDLongValue2Str(obj), obj.getName());
	}
	return codeList;
    }

    public String erpDivideConvert(String cost) throws Exception {// 이해는 안되지만 sap 에서 KRW로 세팅되있기 때문에 소수점문제로 무조건 100나눠달라고 요청함.. SAP에서 별도로 데이터
	                                                          // 가공해서 사용한다고 함 (100으로 곱해서)
	return (String) CostCalculateUtil.manager.eval(cost + "/100");
    }

    public void costProductSendErp(List<CostInterfaceHistory> list) throws Exception {
	Client client = null;
	IRepository repository = null;
	String functionName = "Z_CO_PLM_COST";
	List<CostInterfaceHistory> parentList = new ArrayList<CostInterfaceHistory>();
	try {

	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate(functionName);
	    Function function = tmpl.getFunction();
	    for (CostInterfaceHistory obj : list) {

		Table table = function.getTableParameterList().getTable("IT_PLM");
		table.appendRow();

		try {
		    if (!"Y".equals(obj.getDelFlag())) {
			float costTotal = Float.parseFloat(obj.getProductCostTotal());
			float orgCostTotal = Float.parseFloat(obj.getOrgProductCostTotal());
			if (costTotal != orgCostTotal) {
			    obj.setTransferFlag("E");
			    obj.setTransferMsg("기계산된 총원가와 재계산 총원가가 일치하지 않습니다.");
			    continue;
			}
		    }

		    table.setValue(obj.getRealPartNo(), "MATNR");// 자재번호
		    table.setValue(obj.getDrStep(), "STEP");// 개발단계
		    table.setValue(erpDivideConvert(obj.getSalesTargetCostTotal()), "KWBRUM");// 판가
		    table.setValue(erpDivideConvert(obj.getProductCostTotal()), "VV510");// 총원가
		    table.setValue(erpDivideConvert(obj.getMaterialCost()), "VV700");// 재료비
		    table.setValue(erpDivideConvert(obj.getLaborCost()), "KST001");// 직접인건비
		    table.setValue(erpDivideConvert(obj.getInDirectLaborCost()), "KST002");// 간접인건비
		    table.setValue(erpDivideConvert(obj.getFacReducePrice()), "KST003");// 설비감가비
		    table.setValue(erpDivideConvert(obj.getDirectCost()), "KST004");// 직접경비
		    table.setValue(erpDivideConvert(obj.getInDirectCost()), "KST005");// 간접경비
		    table.setValue(erpDivideConvert(obj.getMoldReducePrice()), "KST006");// 금형감가비
		    table.setValue(erpDivideConvert(obj.getOutUnitCost()), "KST007");// 외주가공비
		    table.setValue(erpDivideConvert(obj.getEtcCost()), "KST008");// 기타원가
		    table.setValue(erpDivideConvert(obj.getSalesManageCost()), "KST009");// 판관비
		    table.setValue(erpDivideConvert(obj.getScrapSalesCost()), "KST010");// 스크랩매출

		    table.setValue(obj.getCostCalcDate(), "BRDAT");// 원가산출일(보고서최종수정일자)
		    table.setValue(obj.getSopSyear(), "SGJAHR");// SOP시작년도
		    table.setValue(obj.getSopEyear(), "EGJAHR");// SOP종료년도
		    table.setValue(obj.getSalesQty1(), "ABSMG01");// 월평균 판매수량1
		    table.setValue(obj.getSalesQty2(), "ABSMG02");// 월평균 판매수량2
		    table.setValue(obj.getSalesQty3(), "ABSMG03");// 월평균 판매수량3
		    table.setValue(obj.getSalesQty4(), "ABSMG04");// 월평균 판매수량4
		    table.setValue(obj.getSalesQty5(), "ABSMG05");// 월평균 판매수량5
		    table.setValue(obj.getSalesQty6(), "ABSMG06");// 월평균 판매수량6
		    table.setValue(obj.getSalesQty7(), "ABSMG07");// 월평균 판매수량7
		    table.setValue(obj.getSalesQty8(), "ABSMG08");// 월평균 판매수량8
		    table.setValue(obj.getSalesQty9(), "ABSMG09");// 월평균 판매수량9
		    table.setValue(obj.getSalesQty10(), "ABSMG10");// 월평균 판매수량10
		    table.setValue(obj.getSalesQty11(), "ABSMG11");// 월평균 판매수량11
		    table.setValue(obj.getSalesQty12(), "ABSMG12");// 월평균 판매수량12
		    table.setValue(obj.getSalesQty13(), "ABSMG13");// 월평균 판매수량13
		    table.setValue(obj.getSalesQty14(), "ABSMG14");// 월평균 판매수량14
		    table.setValue(obj.getSalesQty15(), "ABSMG15");// 월평균 판매수량15
		    table.setValue(obj.getSalesQty16(), "ABSMG16");// 월평균 판매수량16
		    table.setValue(obj.getSalesQty17(), "ABSMG17");// 월평균 판매수량17
		    table.setValue(obj.getSalesQty18(), "ABSMG18");// 월평균 판매수량18
		    table.setValue(obj.getSalesQty19(), "ABSMG19");// 월평균 판매수량19
		    table.setValue(obj.getSalesQty20(), "ABSMG20");// 월평균 판매수량20

		    if ("Y".equals(obj.getDelFlag())) {
			table.setValue("Y", "FLAG");// 자재삭제플래그
		    }

		    client.execute(function);

		    String eReturn = (String) function.getExportParameterList().getValue("E_RETURN");
		    int eCnt = function.getExportParameterList().getInt("E_CNT");
		    String eMsg = (String) function.getExportParameterList().getValue("E_MSG");
		    obj.setBomTreeCheck(true);
		    if (eReturn != null && "S".equals((eReturn.toUpperCase()))) {
			obj.setTransferFlag("Y");
			obj.setTransferMsg("");
			obj.setTransferDate(DateUtil.getCurrentTimestamp());
			if ("Y".equals(obj.getDelFlag())) {
			    obj.setTransferMsg("ERP 삭제");
			    obj.setTransferFlag("N");
			    obj.setDelFlag("");
			}
		    } else {
			obj.setTransferFlag("E");
			obj.setTransferMsg(eMsg);
		    }
		} catch (Exception e) {
		    obj.setTransferFlag("E");
		    obj.setTransferMsg(ExceptionUtils.getMessage(e));
		    e.printStackTrace();
		} finally {
		    obj = (CostInterfaceHistory) PersistenceHelper.manager.save(obj);
		    if (!"E".equals(obj.getTransferFlag())) {// erp 전송완료 또는 삭제 처리된 완제품의 자부품만 전송하기 위해
			parentList.add(obj);
		    }

		}

	    }

	    costChildPartSendErp(parentList, "Z_CO_PLM_COST_2");// 자부품 erp 전송 : 가공비
	    costChildPartSendErp(parentList, "Z_CO_PLM_COST_3");// 자부품 erp 전송 : 재료비

	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	} finally {
	    client.disconnect();
	    repository = null;
	}
    }

    public List<CostInterfaceChildHistory> getChildList(CostInterfaceHistory rootPart) throws Exception {

	List<CostInterfaceChildHistory> childList = new ArrayList<CostInterfaceChildHistory>();

	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(CostInterfaceChildHistory.class, true);

	qs.appendWhere(new SearchCondition(CostInterfaceChildHistory.class, CostInterfaceChildHistory.PARENT_HISTORY_REFERENCE + "."
	        + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(rootPart)), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostInterfaceChildHistory child = (CostInterfaceChildHistory) o[0];
	    childList.add(child);
	}

	return childList;
    }

    public Map<String, String> erpSendChildColumnConfig2() {// 재료비 세팅

	// 숫자는 소수점 둘째자리까지 맞춘다 0.00, SAP 필드추가시 0.00 형태의 숫자로 컨버팅해야한다면 ETC 또는 AMT 로 시작해야한다

	Map<String, String> columnMap = new HashMap<String, String>();
	columnMap.put("pjtNo", "PJTNO");// 프로젝트번호
	columnMap.put("drStep", "STEP");// 개발단계
	columnMap.put("sopYear", "SOP");// SOP
	columnMap.put("rootPartNo", "MATNR_FG");// 완제품
	columnMap.put("parentPartNo", "MATNR_UP");// 1레벨 상위 partNo
	columnMap.put("parentPartUs", "MENGE_UP");// 1레벨 상위 partNo의 u/s
	columnMap.put("bomLevel", "LVEL");// level
	columnMap.put("partType", "ORT02");// 구분

	columnMap.put("targetPartNo", "MATNR");// 부품번호(이관시 기준이 되는 품번)
	columnMap.put("targetPartName", "MAKTX");// 부품명(이관시 기준이 되는 품명)

	columnMap.put("realPartNo", "MATNR_O");// org 부품번호
	columnMap.put("partName", "MAKTX_O");// org 제품명

	columnMap.put("sujiPartNo", "SJMAT");// 수지제번 (원재료번호)
	columnMap.put("sujiPartName", "SJMAT_TXT");// 수지명칭 (원재료명)

	columnMap.put("metalPartNo", "NFMAT");// 비철제번 (원재료번호)
	columnMap.put("metalPartName", "NFMAT_TXT");// 비철명칭 (원재료명)

	columnMap.put("partNo", "PLMNO");// PLM제번

	columnMap.put("lastCompany", "LIFNR");// 최종 업체명(수지나 비철일 경우 maker, 그게 아니면 company)

	columnMap.put("mftFactory1", "OPNAT");// 생산국
	columnMap.put("mftFactory2", "OPARE");// 생산지
	columnMap.put("company", "LIFNR_TXT");// 업체명

	columnMap.put("materialCompany", "NF_LIFNR");// 원재료 업체명 (수지,비철) 아직 원가프로그램에 없는 컬럼
	columnMap.put("packUnitPriceOption", "AMT005");// 포장비합계 - 개별포장비 합계(옵션)
	columnMap.put("rawMaterialCostExpr", "AMT006");// 원재료비
	columnMap.put("assyLossExpr", "AMT007");// 조립로스
	columnMap.put("materialCost", "AMT008");// 재료비
	columnMap.put("scrapSalesCost", "AMT044");// 스크랩매출
	columnMap.put("waers", "WAERS");// 통화 - KRW 고정

	columnMap.put("lastUnitCost", "AMT062");// 단가 - (구매품 or 수지 or 비철)
	columnMap.put("lastUnit", "MEINS62");// 단위 - (구매품 or 수지 or 비철) EA, G
	columnMap.put("lastMonetaryUnit", "WAERS62");// 통화 - (구매품 or 수지 or 비철)

	columnMap.put("pUnitCost", "AMT063");// 구매단가
	columnMap.put("pMonetaryUnit", "WAERS63");// 구매단가 화폐단위
	columnMap.put("pUnit", "MEINS63");// 기준단위 - EA 로 하드코딩

	columnMap.put("mtrSujiPrice", "AMT064");// 수지 원재료 단가
	columnMap.put("mtrSujiMonetaryUnit", "WAERS64");// 수지화폐단위 - KRW로 하드코딩
	columnMap.put("mtrSujiUnit", "MEINS64");// 수지기준단위 - EA 로 하드코딩

	columnMap.put("mtrMetalPrice", "AMT065");// 비철 원재료 단가
	columnMap.put("mtrMetalMonetaryUnit", "WAERS65");// 비철화폐단위 - KRW로 하드코딩
	columnMap.put("mtrMetalUnit", "MEINS65");// 비철기준단위 - EA 로 하드코딩

	columnMap.put("metalLmeStd", "AMT066");// LME시세
	columnMap.put("pMonetaryUnitCurrency", "AMT067");// 구매 환율

	columnMap.put("qaDiffInput", "AMT068");// 수량차이-투입
	columnMap.put("qaDiffUnit", "AMT069");// 수량차이-단위

	columnMap.put("productLossRate", "AMT070");// 생산Loss율

	columnMap.put("us", "MENGE");// US

	columnMap.put("productWeight", "MENGE01");// 제품중량
	columnMap.put("scrapWeight", "MENGE02");// 스크랩중량
	columnMap.put("totalWeight", "MENGE03");// 총중량

	return columnMap;
    }

    public Map<String, String> erpSendChildColumnConfig() {

	// 숫자는 소수점 둘째자리까지 맞춘다 0.00, SAP 필드추가시 0.00 형태의 숫자로 컨버팅해야한다면 ETC 또는 AMT 로 시작해야한다

	Map<String, String> columnMap = new HashMap<String, String>();
	columnMap.put("pjtNo", "PJTNO");// 프로젝트번호
	columnMap.put("partNo", "PLMNO");// PLM제번
	columnMap.put("rootPartNo", "MATNR_FG");// 완제품
	columnMap.put("realPartNo", "MATNR");// 공정제번
	columnMap.put("drStep", "STEP");// 개발단계
	columnMap.put("partName", "MAKTX");// 제품명
	columnMap.put("us", "MENGE");// US
	columnMap.put("partType", "ORT02");// 구분
	columnMap.put("mftFactory1", "OPNAT");// 생산국
	columnMap.put("mftFactory2", "OPARE");// 생산지
	columnMap.put("company", "OPCOM");// 생산처 - 생산정보 - 업체명
	columnMap.put("facilities", "MCTON");// 적용설비(Ton)
	columnMap.put("sopYear", "SOP");// SOP
	columnMap.put("waers", "WAERS");// 통화 - KRW 고정

	columnMap.put("productCostTotal", "AMT001");// 총원가
	columnMap.put("materialCostExpr", "AMT002");// 재료비(수식)
	columnMap.put("processingCost", "AMT003");// 가공비
	columnMap.put("salesManageCost", "AMT004");// 판관비
	columnMap.put("packUnitPriceOption", "AMT005");// 포장비합계 - 개별포장비 합계(옵션)
	columnMap.put("rawMaterialCostExpr", "AMT006");// 원재료비
	columnMap.put("assyLossExpr", "AMT007");// 조립로스
	columnMap.put("materialCost", "AMT008");// 재료비
	columnMap.put("indirectRndRate", "AMT009");// 간접인건비비율R&D
	columnMap.put("indirectLabourRate", "AMT010");// 간접인건비비율노무비
	columnMap.put("rndExpr", "AMT011");// R&D비
	columnMap.put("laborCostExpr", "AMT012");// 노무비
	columnMap.put("indirectLabourRndCost", "AMT013");// 간접인건비R&D
	columnMap.put("laborCost", "AMT014");// 직접인건비
	columnMap.put("inDirectLaborCost", "AMT015");// 간접인건비
	columnMap.put("facReduceCost", "AMT016");// 설비감가
	columnMap.put("machineDpcCostExpr", "AMT017");// 기계감가
	columnMap.put("facReducePrice", "AMT018");// 설비감가비
	columnMap.put("elecCostExpr", "AMT019");// 전력비
	columnMap.put("tabaryuExpr", "AMT020");// 타발유
	columnMap.put("moldMaintenance", "AMT021");// 금형유지비
	columnMap.put("etcReducePrice", "AMT022");// 기타감가비
	columnMap.put("directCost", "AMT023");// 직접경비
	columnMap.put("packUnitPriceSum", "AMT024");// 개별포장비합계
	columnMap.put("inDirectCostExpr", "AMT025");// 간접경비
	columnMap.put("inDirectCost2Expr", "AMT026");// 간접경비2
	columnMap.put("manageMtrLogisExpr", "AMT027");// 원재료물류비
	columnMap.put("manageMtrdutieExpr", "AMT028");// 원재료관세
	columnMap.put("mtrLtCostExpr", "AMT029");// 공정물류비
	columnMap.put("mtrLtRateExpr", "AMT030");// 공정관세
	columnMap.put("payCostLtExpr", "AMT031");// 납입물류비
	columnMap.put("payRateLtExpr", "AMT032");// 납입관세
	columnMap.put("indirectCostRnd", "AMT033");// 간접경비R&D
	columnMap.put("inDirectCost", "AMT034");// 간접경비
	columnMap.put("dfMoldDep", "AMT051");// 금형감가
	columnMap.put("outUnitCostVal", "AMT036");// 외주단가
	columnMap.put("apUnitCostVal", "AMT037");// 후도금단가
	columnMap.put("apUnitCostOption", "AMT038");// 후도금비(옵션)
	columnMap.put("corporateMarginCostExpr", "AMT039");// 법인간마진비용
	columnMap.put("outUnitCost", "AMT040");// 외주가공비
	columnMap.put("etcCost", "AMT041");// 기타원가
	columnMap.put("coManageExpr", "AMT042");// 일반관리비

	columnMap.put("defectCostExpr", "ETC001");// 불량비율

	columnMap.put("salesManageCost", "AMT043");// 판관비
	columnMap.put("scrapSalesCost", "AMT044");// 스크랩매출
	columnMap.put("dfDirectLaborCost", "AMT045");// 직접인건비
	columnMap.put("dfInDirectLaborCost", "AMT046");// 간접인건비
	columnMap.put("dfMachineDep", "AMT047");// 설비감가비
	columnMap.put("dfDirectExpenses", "AMT048");// 직접경비
	columnMap.put("dfInDirectExpenses", "AMT049");// 간접경비

	columnMap.put("outputExpr", "ETC002");// 시간당 생산량
	columnMap.put("dfCt", "ETC003");// CT
	columnMap.put("ctSPMMold", "ETC004");// CT(금형)
	columnMap.put("ctSPMAssemble", "ETC005");// CT(설비)
	columnMap.put("cv", "ETC006");// CV
	columnMap.put("cvMold", "ETC007");// CV(금형)
	columnMap.put("cvAssemble", "ETC008");// CV(설비)
	columnMap.put("efficientRate", "ETC009");// 효율
	columnMap.put("personQLH", "ETC010");// 인시당생산량
	columnMap.put("convertWorker", "ETC011");// 투입인원
	columnMap.put("unitManage", "ETC012");// 관리대수(금형)
	columnMap.put("worker", "ETC013");// 투입인원(설비)

	columnMap.put("productLossExpr", "AMT050");// 생산로스

	return columnMap;
    }

    public void erpSendChildTableConfig(Map<String, String> columnMap, CostInterfaceChildHistory child, Table table) throws Exception {
	Map<String, Object> data = ObjectUtil.manager.converObjectToMap(child);
	// Map<String, Object> convertData = new HashMap<String, Object>();
	Set<String> st = columnMap.keySet();
	Iterator<String> it = st.iterator();

	while (it.hasNext()) {
	    String plmColumnKey = it.next();
	    String erpColumnKey = columnMap.get(plmColumnKey);
	    String value = (String) data.get(plmColumnKey);
	    if (erpColumnKey.startsWith("AMT") || erpColumnKey.startsWith("ETC")) {// 금액 관련 필드일 때 소수점 둘째자리까지 맞추기
		value = CostTreeUtil.manager.erpFormatConvert(value, 3);
		// convertData.put(plmColumnKey, value);
	    }
	    table.setValue(value, erpColumnKey);
	}
	// ObjectUtil.manager.convertMapToObject(convertData, child);
	// PersistenceHelper.manager.save(child);
    }

    private void costChildPartSendErp(List<CostInterfaceHistory> parentList, String functionName) throws Exception {
	if (parentList == null || parentList.size() < 1) {
	    return;
	}
	Client client = null;
	IRepository repository = null;

	try {
	    Map<String, String> columnMap = null;
	    if ("Z_CO_PLM_COST_2".equals(functionName)) {// 가공비
		columnMap = erpSendChildColumnConfig();
	    } else if ("Z_CO_PLM_COST_3".equals(functionName)) {// 재료비
		columnMap = erpSendChildColumnConfig2();
	    }
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate(functionName);
	    Function function = tmpl.getFunction();
	    for (CostInterfaceHistory parent : parentList) {
		parent.setBomTreeCheck(true);
		List<CostInterfaceChildHistory> childList = getChildList(parent);

		for (CostInterfaceChildHistory child : childList) {
		    if (StringUtils.isEmpty(child.getRealPartNo())) {// 실제 품번이 없는 경우 스킵.. DR0의 품번에 대해 향후 착수프로젝트에서 삭제된 경우가 있을 수 있다
			continue;
		    }
		    // 조립(반자동) 일때 인터페이스 대상에서 제외했으나 경영기획팀(김재갑)의 요청으로 해당 로직은 주석처리한다 2021.12.27
		    // if ("Z_CO_PLM_COST_3".equals(functionName) && "조립(반자동)".equals(child.getPartType())) {// 재료비는 특정 TYPE 스킵.. 김한호 책임 요청
		    // continue;
		    // }
		    Table table = function.getTableParameterList().getTable("IT_PLM");
		    table.appendRow();

		    try {
			if (!"ERP 삭제".equals(parent.getTransferMsg())) {
			    float costTotal = Float.parseFloat(child.getProductCostTotal());
			    float orgCostTotal = Float.parseFloat(child.getOrgProductCostTotal());
			    if (costTotal != orgCostTotal) {
				child.setTransferFlag("E");
				child.setTransferMsg("기계산된 총원가와 재계산 총원가가 일치하지 않습니다.");
				parent.setBomTreeCheck(false);
				continue;
			    }
			}

			erpSendChildTableConfig(columnMap, child, table);

			if ("ERP 삭제".equals(parent.getTransferMsg())) {
			    table.setValue("Y", "DELFLAG");// 자재삭제플래그
			}

			client.execute(function);

			String eReturn = (String) function.getExportParameterList().getValue("E_RETURN");
			int eCnt = function.getExportParameterList().getInt("E_CNT");
			String eMsg = (String) function.getExportParameterList().getValue("E_MSG");

			if (eReturn != null && "S".equals((eReturn.toUpperCase()))) {
			    child.setTransferFlag("Y");
			    child.setTransferMsg("");
			    child.setTransferDate(DateUtil.getCurrentTimestamp());
			    if ("ERP 삭제".equals(parent.getTransferMsg())) {
				child.setTransferMsg("ERP 삭제");
				child.setTransferFlag("N");
			    }
			} else {
			    child.setTransferFlag("E");
			    child.setTransferMsg(eMsg);
			    parent.setBomTreeCheck(false);
			}
		    } catch (Exception e) {
			child.setTransferFlag("E");
			child.setTransferMsg(ExceptionUtils.getMessage(e));
			parent.setBomTreeCheck(false);
			e.printStackTrace();
		    } finally {
			PersistenceHelper.manager.save(child);
		    }
		}

		PersistenceHelper.manager.save(parent);

	    }

	} catch (Exception e) {
	    for (CostInterfaceHistory parent : parentList) {
		parent.setBomTreeCheck(false);
		PersistenceHelper.manager.save(parent);
	    }
	    e.printStackTrace();
	    throw e;
	} finally {
	    client.disconnect();
	    repository = null;
	}
    }

    public List<CostInterfaceHistory> getCostIFhistoryListAll() throws Exception {

	List<CostInterfaceHistory> list = new ArrayList<CostInterfaceHistory>();

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec(CostInterfaceHistory.class);

	qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    CostInterfaceHistory obj = (CostInterfaceHistory) qr.nextElement();
	    list.add(obj);
	}

	return list;
    }

    public List<CostInterfaceHistory> getCostSendTargetList() throws Exception {// 미전송, 오류 난 것만 전송대상으로 추출한다

	List<CostInterfaceHistory> list = new ArrayList<CostInterfaceHistory>();
	List<Map<String, Object>> dupList = getDupCalcDateList();
	String[] transferFlags = { "N", "E" };// N은 전송대기, E는 오류
	QueryResult qr = null;
	QuerySpec qs = new QuerySpec(CostInterfaceHistory.class);
	qs.appendWhere(new SearchCondition(new ClassAttribute(CostInterfaceHistory.class, CostInterfaceHistory.TRANSFER_FLAG),
	        SearchCondition.IN, new ArrayExpression(transferFlags)), new int[] { 0 });
	qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    CostInterfaceHistory obj = (CostInterfaceHistory) qr.nextElement();
	    if (!isTargetSendErp(obj, dupList)) {
		continue;
	    }
	    list.add(obj);
	}

	return list;
    }

    public List<CostInterfaceHistory> getErpTargetList() throws Exception {// 미전송, 오류 난 것만 전송대상으로 추출한다

	List<CostInterfaceHistory> list = new ArrayList<CostInterfaceHistory>();
	List<Map<String, Object>> dupList = getDupCalcDateList();
	String[] transferFlags = { "N", "E" };// N은 전송대기, E는 오류
	QueryResult qr = null;
	QuerySpec qs = new QuerySpec(CostInterfaceHistory.class);
	qs.appendWhere(new SearchCondition(new ClassAttribute(CostInterfaceHistory.class, CostInterfaceHistory.TRANSFER_FLAG),
	        SearchCondition.IN, new ArrayExpression(transferFlags)), new int[] { 0 });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}

	qs.appendWhere(new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.LASTEST, SearchCondition.IS_TRUE, true),
	        new int[] { 0 });
	qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    CostInterfaceHistory obj = (CostInterfaceHistory) qr.nextElement();
	    list.add(obj);
	}

	return list;
    }

    public List<CostInterfaceHistory> setListByJsonStr(Map<String, Object> reqMap) throws Exception {

	List<CostInterfaceHistory> list = new ArrayList<CostInterfaceHistory>();

	ObjectMapper mapper = new ObjectMapper();

	String reSendArrListStr = (String) reqMap.get("reSendArrList");
	List<Map<String, Object>> reSendArrList = mapper.readValue(reSendArrListStr, new TypeReference<List<Map<String, Object>>>() {
	});

	for (Map<String, Object> map : reSendArrList) {
	    String oid = (String) map.get("oid");
	    CostInterfaceHistory history = (CostInterfaceHistory) CommonUtil.getObject(oid);
	    list.add(history);
	}

	return list;

    }

    public String getMessageSendErpCheck(List<CostInterfaceHistory> list) throws Exception {

	String message = "";
	List<Map<String, Object>> dupList = getDupCalcDateList();
	try {

	    for (CostInterfaceHistory history : list) {
		if (!isTargetSendErp(history, dupList)) {
		    message += history.getRealPartNo() + " / " + history.getVersion() + " / " + history.getDrStep()
			    + System.getProperty("line.separator");
		}
	    }

	    // message = StringUtils.removeEnd(message, ",");

	    if (StringUtils.isNotEmpty(message)) {
		message = "해당 DR단계의 Part 버전이 최신이 아닙니다." + System.getProperty("line.separator") + System.getProperty("line.separator")
		        + message;
	    }

	} catch (Exception e) {
	    throw e;
	}

	return message;
    }

    private void partLastestSetting(CostInterfaceHistory history, List<Map<String, Object>> dupList) throws Exception {

	history.setLastest(isTargetSendErp(history, dupList));
	history = (CostInterfaceHistory) PersistenceHelper.manager.save(history);
    }

    private void updateLatest() throws Exception {
	List<Map<String, Object>> dupList = getDupCalcDateList();

	QuerySpec qs = new QuerySpec();
	qs.appendClassList(CostInterfaceHistory.class, true);
	QueryResult qr = PersistenceServerHelper.manager.query(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostInterfaceHistory history = (CostInterfaceHistory) o[0];
	    partLastestSetting(history, dupList);
	}
    }

    public void updateLatest(List<CostInterfaceHistory> list) throws Exception {
	List<Map<String, Object>> dupList = getDupCalcDateList();
	for (CostInterfaceHistory history : list) {
	    partLastestSetting(history, dupList);
	}
    }

    private void updateChildPart(CostInterfaceChildHistory child, CostInterfaceHistory history, Map<String, Object> codeList,
	    Map<String, Object> paramMap) throws Exception {

	child.setPjtNo(history.getPjtNo());
	child.setPjtType(history.getPjtType());
	child.setRootPartNo(history.getRealPartNo());
	child.setDrStep(history.getDrStep());
	child.setVersion(history.getVersion());
	child.setParentHistory(history);
	child.setTask(history.getTask());
	child.setProject(history.getProject());
	child.setPartList(history.getPartList());

	String mftFactory1 = child.getMftFactory1();
	String mftFactory2 = child.getMftFactory2();
	String partType = child.getPartType();
	child.setMftFactory1("");
	child.setMftFactory2("");
	child.setPartType("");

	Set<String> st = codeList.keySet();
	Iterator<String> it = st.iterator();

	while (it.hasNext()) {
	    String key = it.next();
	    if (key.equals(mftFactory1)) {
		child.setMftFactory1((String) codeList.get(key));
	    }
	    if (key.equals(mftFactory2)) {
		child.setMftFactory2((String) codeList.get(key));
	    }
	    if (key.equals(partType)) {
		child.setPartType((String) codeList.get(key));
	    }
	}

	if (StringUtils.isEmpty(child.getCompany())) {// 값이 없을 때 생산처2를 세팅
	    child.setCompany(child.getMftFactory2());
	}

	if ("검토".equals(child.getPjtType()) && StringUtils.isEmpty(child.getRealPartNo())) {
	    Long productPjtOid = (Long) paramMap.get("PRODUCTPJTOID");
	    String productStep = (String) paramMap.get("PRODUCTSTEP");

	    List<Map<String, Object>> realPartInfo = getResultList(getRealPartNo(child.getCostPart().getPartNo(), productStep,
		    productPjtOid));
	    String realPartNo = "";
	    if (realPartInfo != null && realPartInfo.size() > 0) {
		realPartNo = (String) realPartInfo.get(0).get("REALPARTNO");
	    }

	    child.setRealPartNo(realPartNo);
	}

	updateChildPartMaterial(child);// 재료비 가공

	PersistenceHelper.manager.save(child);
    }

    public void updateChildPartMaterial(CostInterfaceChildHistory child) throws Exception {
	// 재료비 convert
	CostPart orgPart = child.getCostPart();
	if (child.getParent() != null) {// 상위제번, 상위제번 us 세팅
	    CostInterfaceChildHistory parent = (CostInterfaceChildHistory) child.getParent();
	    child.setParentPartNo(parent.getRealPartNo());
	    child.setParentPartUs(parent.getUs());
	} else {
	    child.setParentPartNo(child.getRealPartNo());
	    child.setParentPartUs(child.getUs());
	}
	child.setBomLevel(orgPart.getBomLevel());
	String targetPartNo = child.getRealPartNo();
	String targetPartName = child.getPartName();

	String sujiPartNo = orgPart.getSujiPartNo();
	String sujiPartName = orgPart.getSujiPartName();
	String metalPartNo = orgPart.getMetalPartNo();

	String metalPartName = CodeHelper.manager.getCodeValue("RAWMATERIAL", orgPart.getRMatNMetalName());
	String lastUnitCost = orgPart.getPUnitCost();
	String lastUnit = "EA";
	String lastMonetaryUnit = CodeHelper.manager.getCodeValue("MONETARYUNIT", orgPart.getPMonetaryUnit());
	String pMonetaryUnitCurrency = "";
	if (StringUtils.isNotEmpty(lastMonetaryUnit) && !"KRW".equals(lastMonetaryUnit)) {
	    pMonetaryUnitCurrency = orgPart.getPMonetaryUnitCurrency();
	}

	child.setPMonetaryUnit(lastMonetaryUnit);
	String pUnit = "EA";

	child.setSujiPartNo(sujiPartNo);
	child.setSujiPartName(sujiPartName);
	child.setMetalPartNo(metalPartNo);
	child.setMetalPartName(metalPartName);

	String lastCompany = orgPart.getCompany();
	String materialCompany = "";
	String qaDiffInput = orgPart.getUs();
	String qaDiffUnit = "";
	if (StringUtils.isNotEmpty(sujiPartNo)) {
	    targetPartNo = sujiPartNo;
	    targetPartName = sujiPartName;
	    WTPart wtPart = PartBaseHelper.service.getLatestPart(targetPartNo);

	    if (wtPart != null) {
		materialCompany = getMaterialMaker(wtPart);
	    }

	    lastCompany = materialCompany;
	    lastUnitCost = orgPart.getMtrSujiPrice();
	    lastUnit = "G";
	    lastMonetaryUnit = "KRW";
	    qaDiffInput = (String) CostCalculateUtil.manager.eval("(" + orgPart.getUs() + "*" + orgPart.getTotalWeight() + ")");
	    child.setMtrSujiMonetaryUnit(lastMonetaryUnit);
	    child.setMtrSujiUnit(lastUnit);
	} else if (StringUtils.isNotEmpty(metalPartNo)) {
	    targetPartNo = metalPartNo;
	    targetPartName = metalPartName;
	    WTPart wtPart = PartBaseHelper.service.getLatestPart(targetPartNo);

	    if (wtPart != null) {
		materialCompany = getMaterialMaker(wtPart);
		lastCompany = materialCompany;
		lastUnitCost = orgPart.getMtrMetalPrice();
		lastUnit = "G";
		lastMonetaryUnit = "KRW";
		child.setMtrMetalMonetaryUnit(lastMonetaryUnit);
		child.setMtrMetalUnit(lastUnit);
	    }

	}
	qaDiffUnit = lastUnit;
	child.setTargetPartNo(targetPartNo);
	child.setTargetPartName(targetPartName);
	child.setMaterialCompany(materialCompany);
	child.setLastCompany(lastCompany);
	child.setQaDiffInput(qaDiffInput);
	child.setLastUnitCost(lastUnitCost);
	child.setLastUnit(lastUnit);
	child.setLastMonetaryUnit(lastMonetaryUnit);
	child.setQaDiffUnit(qaDiffUnit);
	child.setPUnit(pUnit);
	child.setPMonetaryUnitCurrency(pMonetaryUnitCurrency);
    }

    public String getMaterialMaker(WTPart wtpart) throws Exception {
	String spMaterMaker = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMaterMaker);
	String m_maker = "";
	if (StringUtils.isNotEmpty(spMaterMaker)) {
	    m_maker = CodeHelper.manager.getCodeValue(PartSpecEnum.SpMaterMaker.getAttrCodeType(), spMaterMaker);
	}
	return m_maker;
    }

    public List<Map<String, Object>> getDupCalcDateList() throws Exception {
	return getResultList(getDupCalcDateListQuery());
    }

    private boolean dupCostCalcCheck(List<Map<String, Object>> dupList, CostInterfaceHistory obj) throws Exception {
	Map<String, Object> paramMap = CostUtil.manager.converObjectToMap(obj);
	String orgData = (String) paramMap.get("costCalcDate") + (String) paramMap.get("realPartNo") + (String) paramMap.get("drStep");
	String targetData = "";

	for (Map<String, Object> data : dupList) {
	    targetData = (String) data.get("COSTCALCDATE") + (String) data.get("REALPARTNO") + (String) data.get("DRSTEP");
	    if (orgData.equals(targetData)) {
		return true;
	    }
	}
	return false;
    }

    private boolean isTargetSendErp(CostInterfaceHistory history, List<Map<String, Object>> dupCalcList) throws Exception {

	// DR단계의 마지막 버전인지 아닌지 체크

	// 1. 프로젝트, DR단계 기준으로 최종 산출된 버전인지 확인
	// 2. 동일 REALPARTNO 로 여러 프로젝트에서 산출된 이력이 있으면 최종 산출일자의 데이터가 맞는지 확인
	// 3. 최종산출일자가 중복인 데이터가 있을 때는 마지막으로 산출한 프로젝트가 맞는지 확인
	// 만약 프로젝트가 최신이 아닌데도 불구하고 산출일자가 최종인경우 가장 마지막 산출일자를 최종버전으로 판단하고 있다 - 문제가 있을수도 있으나 달리 판단해줄사람이 있는것도 아님..
	QuerySpec qs = new QuerySpec();

	int idx = qs.appendClassList(CostPart.class, false);
	ClassAttribute ver = new ClassAttribute(CostPart.class, "version");
	ClassAttribute subVer = new ClassAttribute(CostPart.class, "subVersion");

	SQLFunction versionConCat = SQLFunction.newSQLFunction(SQLFunction.CONCAT, new ColumnExpression[] { ver, subVer });

	SQLFunction versionToNumber = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, versionConCat);

	SQLFunction maxVersion = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionToNumber);

	qs.appendSelect(maxVersion, false);

	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.PROJECT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(history.getProject())), new int[] { idx });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.PART_NO, SearchCondition.EQUAL, history.getCostPart().getPartNo()),
	        new int[] { 0 });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.DR_STEP, SearchCondition.EQUAL, history.getDrStep()), new int[] { 0 });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.CASE_ORDER, SearchCondition.EQUAL, 1), new int[] { 0 });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}

	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.LASTEST, SearchCondition.IS_TRUE, true), new int[] { 0 });

	if (!qs.isAdvancedQueryEnabled())
	    qs.setAdvancedQueryEnabled(true);

	System.out.println("@@@@@@@@@@@ isTargetSendErp query Start @@@@@@@@@@@@@");
	System.out.println(qs.toString());
	System.out.println("@@@@@@@@@@@ isTargetSendErp query End @@@@@@@@@@@@@");

	QueryResult qr = PersistenceServerHelper.manager.query(qs);

	int maxVer = 0;
	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    if (obj[0] != null) {
		maxVer = Integer.parseInt(String.valueOf(obj[0]));
	    }
	}

	int historyMaxVersion = Integer.parseInt(StringUtils.remove(history.getVersion(), "."));

	System.out.println("@@@@@@@@@@ historyMaxVersion : " + historyMaxVersion + " maxVer : " + maxVer);

	if (historyMaxVersion == maxVer) {// 프로젝트의 DR단계별 마지막 버전인지 확인 한 후
	    return isLastestPart(history, dupCalcList);// 동일 품번에 대해 다른 프로젝트로 중복 산출이 가능한 구조이므로 최종 산출된 버전인지 확인
	} else {
	    return false;
	}
    }

    public boolean isLastestPart(CostInterfaceHistory history, List<Map<String, Object>> dupCalcList) throws Exception {
	QuerySpec qs = new QuerySpec();

	int idx = qs.appendClassList(CostInterfaceHistory.class, false);
	ClassAttribute costCalcDate = new ClassAttribute(CostInterfaceHistory.class, CostInterfaceHistory.COST_CALC_DATE);

	SQLFunction maxDate = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, costCalcDate);

	qs.appendSelect(maxDate, new int[] { 0 }, false);

	qs.appendWhere(
	        new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.REAL_PART_NO, SearchCondition.EQUAL, history
	                .getRealPartNo()), new int[] { idx });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}

	qs.appendWhere(
	        new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.DR_STEP, SearchCondition.EQUAL, history.getDrStep()),
	        new int[] { idx });

	qs.setAdvancedQueryEnabled(true);
	QueryResult qr = PersistenceServerHelper.manager.query((StatementSpec) qs);

	System.out.println("@@@@@@@@@@@ isLastestPart query Start @@@@@@@@@@@@@");
	System.out.println(qs.toString());
	System.out.println("@@@@@@@@@@@ isLastestPart query End @@@@@@@@@@@@@");

	String maxCalcDate = "";
	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    if (obj[0] != null) {
		maxCalcDate = String.valueOf(obj[0]);
	    }
	}

	System.out.println("@@@@@@@@@@@ maxCalcDate : " + maxCalcDate);

	String maxPjtNo = "";
	/*
	 * 해당 dr단계의 part가 가장 마지막 프로젝트에서 산출된 것인지 확인 (원가산출일자가 동일한 경우가 있음)
	 */
	if (history.getCostCalcDate().equals(maxCalcDate) && dupCostCalcCheck(dupCalcList, history)) {
	    System.out.println("@@@@@@@@@@@ 해당 dr단계의 part가 가장 마지막 프로젝트에서 산출된 것인지 확인 @@@@@@@@@@@");
	    qs = new QuerySpec();

	    idx = qs.appendClassList(CostInterfaceHistory.class, false);
	    ClassAttribute pjtNo = new ClassAttribute(CostInterfaceHistory.class, CostInterfaceHistory.PJT_NO);

	    SQLFunction costPjtNo = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, pjtNo);

	    qs.appendSelect(costPjtNo, new int[] { 0 }, false);

	    qs.appendWhere(new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.REAL_PART_NO, SearchCondition.EQUAL,
		    history.getRealPartNo()), new int[] { idx });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }

	    qs.appendWhere(
		    new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.DR_STEP, SearchCondition.EQUAL, history
		            .getDrStep()), new int[] { idx });

	    qs.setAdvancedQueryEnabled(true);

	    System.out.println("@@@@@@@@@@@ maxPjtNo 추출 query Start @@@@@@@@@@@@@");
	    System.out.println(qs.toString());
	    System.out.println("@@@@@@@@@@@ maxPjtNo 추출 query End @@@@@@@@@@@@@");

	    qr = PersistenceServerHelper.manager.query((StatementSpec) qs);

	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		if (obj[0] != null) {
		    maxPjtNo = String.valueOf(obj[0]);
		}
	    }
	    System.out.println("@@@@@@@@@@@ maxPjtNo : " + maxPjtNo);
	} else {
	    System.out.println("@@@@@@@@@@@ CostCalcDate : " + history.getCostCalcDate());
	    System.out.println("@@@@@@@@@@@ maxCalcDate : " + maxCalcDate);
	    return history.getCostCalcDate().equals(maxCalcDate);
	}

	System.out.println("@@@@@@@@@@@ history.getPjtNo() : " + history.getPjtNo());
	System.out.println("@@@@@@@@@@@ maxPjtNo : " + maxPjtNo);

	return history.getPjtNo().equals(maxPjtNo);
    }

    public void costProductHistoryCreate(Map<String, Object> paramMap) throws Exception {

	String projectState = (String) paramMap.get("state");

	List<Map<String, Object>> targetList = getResultList(getSendTargetListQuery());// 프로젝트의 DR단계별 최종 원가 데이터를 제품 기준으로 끌어온다.(승인된 보고서 필수)

	Map<String, Object> reqMap = new HashMap<String, Object>();
	reqMap.put("lastest", "1");
	reqMap.put("caseOrder", "1");
	reqMap.put("DATATYPE", "COSTPART");
	reqMap.put("orderByPartNo", "OK");

	Map<String, Object> codeMap = getCodeList();

	for (Map<String, Object> targetMap : targetList) {

	    String pjtType = (String) targetMap.get("PJTTYPENAME");
	    String partListOid = (String) targetMap.get("PARTLISTOID");
	    String pjtOid = (String) targetMap.get("PJTOID");
	    String version = (String) targetMap.get("VERSION");

	    if ("검토".equals(pjtType)) {// 검토프로젝트를 스킵하는 이유는 제품프로젝트에서 연계검토프로젝트를 찾고, 그 검토프로젝트를 이미 데이터가 적재되있는 targetMap에서 꺼내 쓰기 위함
		continue;
	    }
	    @SuppressWarnings("unused")
	    E3PSProjectMaster pjtMaster = (E3PSProjectMaster) CommonUtil.getObject(pjtOid);
	    @SuppressWarnings("static-access")
	    E3PSProject project = ProjectHelper.manager.getLastProject((E3PSProjectMaster) CommonUtil.getObject(pjtOid));

	    if (!"ALL".equals(projectState) && "COMPLETED".equals(project.getState().toString())) {
		continue;
	    }

	    /*
	     * 제품프로젝트에서 검토프로젝트의 DR0 마지막 원가를 찾기 위함
	     */

	    if (project.getDevRequest() != null && StringUtil.checkString(project.getDevRequest().getProjectOID())) {
		// ReviewProject rp = (ReviewProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());

		String pjtNos[] = project.getReviewPjtNo().split(",");
		for (String pjtNo : pjtNos) {
		    Map<String, Object> reviewMap = getReviewProjectInfo(targetList, pjtNo);// targetMap에서 검토프로젝트 정보를 꺼내온다

		    if (reviewMap != null) {// 제품 리스트를 조회해오기 위한 reqMap의 파라미터를 검토프로젝트 기준으로 put
			reqMap.put("partListOid", (String) reviewMap.get("PARTLISTOID"));
			reqMap.put("version", (String) reviewMap.get("VERSION"));
			reviewMap.put("PRODUCTPJTOID", CommonUtil.getOIDLongValue(pjtOid));
			reviewMap.put("PRODUCTSTEP", (String) targetMap.get("STEP"));

			/*
		         * 검토프로젝트 DR0 마지막 버전의 원가 정보를 CostInterfaceHistory로 적재
		         */

			objectCreate(CostHelper.service.getCostRootList(reqMap), reviewMap, codeMap);
		    }
		}
	    }

	    reqMap.put("partListOid", partListOid);
	    reqMap.put("version", version);

	    /*
	     * 제품프로젝트 DR별 마지막 버전의 원가 정보를 CostInterfaceHistory로 적재
	     */
	    objectCreate(CostHelper.service.getCostRootList(reqMap), targetMap, codeMap);
	}
	updateLatest();
    }

    private String existPartNumber(String partNo, String version, String pjtNo) {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT REALPARTNO FROM CostInterfaceHistory WHERE REALPARTNO ='" + partNo + "' AND VERSION = '" + version
	        + "' AND PJTNO = '" + pjtNo + "'\n");
	return sql.toString();
    }

    private String getRealPartNo(String partNo, String step, Long pjtOid) {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT DISTINCT REALPARTNO FROM COSTPART WHERE PARTNO ='" + partNo + "' AND REALPARTNO IS NOT NULL AND IDA3B4 = "
	        + pjtOid + " AND DRSTEP = '" + step + "' \n");
	return sql.toString();
    }

    private Map<String, Object> getReviewProjectInfo(List<Map<String, Object>> targetList, String pjtNo) {

	for (Map<String, Object> targetMap : targetList) {
	    if (targetMap.get("PJTNO").equals(pjtNo)) {
		return targetMap;
	    }
	}
	return null;

    }

    private void objectCreate(List<Persistable> rootList, Map<String, Object> paramMap, Map<String, Object> codeMap) throws Exception {

	String pjtType = (String) paramMap.get("PJTTYPENAME");

	boolean isReview = "검토".equals(pjtType);

	List<Map<String, Object>> convertPartList = null;

	for (Persistable obj : rootList) {

	    CostPart part = (CostPart) obj;

	    String partNo = part.getPartNo();
	    String realPartNo = part.getRealPartNo();

	    if (isReview) {// 검토프로젝트의 경우 연계품번(RealPartNo)이 없기 때문에 임시품번(partNo)으로 RealPartNo를 꺼내온다

		Long productPjtOid = (Long) paramMap.get("PRODUCTPJTOID");
		String productStep = (String) paramMap.get("PRODUCTSTEP");

		List<Map<String, Object>> realPartInfo = getResultList(getRealPartNo(part.getPartNo(), productStep, productPjtOid));

		if (realPartInfo != null && realPartInfo.size() > 0) {
		    realPartNo = (String) realPartInfo.get(0).get("REALPARTNO");
		}

	    }

	    if (realPartNo == null || StringUtils.isEmpty(realPartNo)) {
		continue;
	    }

	    if (StringUtils.startsWith(realPartNo, "H")) {// 최상위 품번이 반제품일 경우 제품 품번으로 변경 (ERP에서는 최상위가 제품기준이기 때문이다)
		realPartNo = StringUtils.removeStart(realPartNo, "H");
	    }

	    String version = part.getVersion().toString() + "." + part.getSubVersion().toString();
	    // 품번,버전 조합으로 이미 데이터가 있으면 스킵하는 로직. 이미 전송된 데이터를 중복으로 쌓을 필요도, erp로 전송할 필요도 없기 때문
	    if (getResultList(existPartNumber(realPartNo, version, (String) paramMap.get("PJTNO"))).size() > 0) {
		continue;
	    }

	    CostInterfaceHistory history = CostInterfaceHistory.newCostInterfaceHistory();
	    Map<String, Object> resMap = costTreeConvertProduct(CommonUtil.getOIDString(part));

	    convertPartList = (List<Map<String, Object>>) resMap.get("partList");
	    ObjectUtil.manager.convertMapToObject((Map<String, Object>) resMap.get("rootPartData"), history);
	    history.setRealPartNo(realPartNo);
	    history.setPartNo(partNo);
	    PartList partList = (PartList) CommonUtil.getObject((String) paramMap.get("PARTLISTOID"));
	    E3PSProjectMaster project = (E3PSProjectMaster) CommonUtil.getObject((String) paramMap.get("PJTOID"));
	    history.setPartList(partList);
	    history.setProject(project);
	    history.setCostPart(part);
	    history.setPjtNo((String) paramMap.get("PJTNO"));
	    history.setPjtType((String) paramMap.get("PJTTYPENAME"));
	    history.setVersion(version);
	    history.setTransferFlag("N");
	    history.setDrStep((String) paramMap.get("STEP"));
	    history.setOwner(SessionHelper.manager.getPrincipalReference());
	    history.setSalesTargetCostTotal(CostTreeUtil.manager.erpFormatConvert(part.getSalesTargetCostExpr(), 3));
	    history.setOrgProductCostTotal(CostTreeUtil.manager.erpFormatConvert(part.getProductCostTotal(), 3));
	    CostReport report = CostUtil.manager.getCostReport(CommonUtil.getOIDString(project), String.valueOf(part.getVersion()));
	    if (!"APPROVED".equals(report.getState().toString())) {
		continue;
	    }
	    history.setTask(report.getTask());
	    history.setCostCalcDate(new SimpleDateFormat("yyyyMMdd").format(report.getModifyTimestamp()));
	    quantitySetting(history, part);
	    history.setBomTreeCheck(true);
	    history = (CostInterfaceHistory) PersistenceHelper.manager.save(history);

	    try {
		childPartCreate(convertPartList, null, history, codeMap, paramMap);// 자부품생성
	    } catch (Exception e) {
		history.setBomTreeCheck(false);
		PersistenceHelper.manager.save(history);
		e.printStackTrace();
	    }

	    // Long partOid = CommonUtil.getOIDLongValue(part);
	    //
	    // List<Map<String, Object>> targetList = getResultList(getProductCostQuery(partOid));// 제품기준 원가 정보를 가져온다
	    //
	    // for (Map<String, Object> targetMap : targetList) {
	    //
	    // CostInterfaceHistory history = CostInterfaceHistory.newCostInterfaceHistory();
	    // // jdbc로 불러온 컬럼은 대문자이기때문에 Object의 컬럼기준으로 맵을 다시 말아주고 해당 맵을 Object로 컨버트한다
	    // ObjectUtil.manager.convertMapToObject(ObjectUtil.manager.converMapKeyChangeToObject(history, targetMap), history);
	    // history.setRealPartNo(realPartNo);
	    // history.setPartNo(partNo);
	    // PartList partList = (PartList) CommonUtil.getObject((String) paramMap.get("PARTLISTOID"));
	    // E3PSProjectMaster project = (E3PSProjectMaster) CommonUtil.getObject((String) paramMap.get("PJTOID"));
	    // history.setPartList(partList);
	    // history.setProject(project);
	    // history.setCostPart(part);
	    // history.setPjtNo((String) paramMap.get("PJTNO"));
	    // history.setPjtType((String) paramMap.get("PJTTYPENAME"));
	    // history.setVersion(version);
	    // history.setTransferFlag("N");
	    // history.setDrStep((String) paramMap.get("STEP"));
	    // history.setOwner(SessionHelper.manager.getPrincipalReference());
	    // history.setSalesTargetCostTotal(part.getSalesTargetCostExpr());
	    // history.setOrgProductCostTotal(part.getProductCostTotal());
	    // CostReport report = CostUtil.manager.getCostReport(CommonUtil.getOIDString(project), String.valueOf(part.getVersion()));
	    // history.setTask(report.getTask());
	    //
	    // PersistenceHelper.manager.save(history);
	    // }

	}

    }

    public void quantitySetting(CostInterfaceHistory history, CostPart part) throws Exception {

	Map<String, Object> qtyMap = new HashMap<String, Object>();
	qtyMap.put("sopSyear", "");
	qtyMap.put("sopEyear", "");
	for (int i = 1; i < 21; i++) {
	    qtyMap.put("salesQty" + i, "");
	}
	ObjectUtil.manager.convertMapToObject(qtyMap, history);// 초기화

	int maxQty = 21;

	List<CostQuantity> quantityList = CostCodeHelper.service.getLastestCostQuantity(part);

	String sopSyear = "";
	String sopEyear = "";

	int yearCnt = 0;
	int qtyStart = 0;
	boolean isContinue = quantityList.size() > 20;

	if (isContinue) {
	    qtyStart = quantityList.size() - maxQty;
	}

	for (int i = 0; i < quantityList.size(); i++) {
	    if (isContinue && i <= qtyStart) {
		continue;
	    }
	    yearCnt++;
	    CostQuantity qty = quantityList.get(i);
	    sopSyear = qty.getSopYear();
	    qtyMap.put("sopSyear", qty.getSopYear());
	    qtyMap.put("salesQty" + yearCnt,
		    CostTreeUtil.manager.erpFormatConvert((String) CostCalculateUtil.manager.eval(qty.getQuantity() + "/12"), 3));
	}

	if (StringUtils.isNotEmpty(sopSyear)) {

	    sopEyear = Integer.toString(Integer.parseInt(sopSyear) + quantityList.size());

	    if (isContinue) {
		sopSyear = Integer.toString(Integer.parseInt(sopSyear) + quantityList.size() - 20);
	    }

	    sopSyear = Integer.toString(Integer.parseInt(sopSyear) + 1);

	    qtyMap.put("sopSyear", sopSyear);
	    qtyMap.put("sopEyear", sopEyear);
	    ObjectUtil.manager.convertMapToObject(qtyMap, history);
	}
    }

    public void deleteErpPart(List<CostInterfaceHistory> list) throws Exception {
	for (CostInterfaceHistory obj : list) {
	    if (!"ERP 삭제".equals(obj.getTransferFlag())) {
		obj.setDelFlag("Y");
	    }
	}
	costProductSendErp(list);
    }

    public void reCalaulate(List<CostInterfaceHistory> list) throws Exception {
	Map<String, Object> codeMap = getCodeList();
	Map<String, Object> tempMap = new HashMap<String, Object>();

	for (CostInterfaceHistory obj : list) {

	    CostPart part = obj.getCostPart();
	    // List<Map<String, Object>> targetList = getResultList(getProductCostQuery(CommonUtil.getOIDLongValue(part)));// 제품기준 원가 정보를
	    // 가져온다
	    //
	    // for (Map<String, Object> targetMap : targetList) {
	    // ObjectUtil.manager.convertMapToObject(ObjectUtil.manager.converMapKeyChangeToObject(obj, targetMap), obj);
	    // obj.setSalesTargetCostTotal(part.getSalesTargetCostExpr());
	    // obj.setOrgProductCostTotal(part.getProductCostTotal());
	    // }

	    quantitySetting(obj, part);

	    Map<String, Object> resMap = costTreeConvertProduct(CommonUtil.getOIDString(part));

	    List<Map<String, Object>> convertPartList = (List<Map<String, Object>>) resMap.get("partList");
	    ObjectUtil.manager.convertMapToObject((Map<String, Object>) resMap.get("rootPartData"), obj);

	    obj.setSalesTargetCostTotal(CostTreeUtil.manager.erpFormatConvert(part.getSalesTargetCostExpr(), 3));
	    obj.setOrgProductCostTotal(CostTreeUtil.manager.erpFormatConvert(part.getProductCostTotal(), 3));

	    CostReport report = CostUtil.manager
		    .getCostReport(CommonUtil.getOIDString(obj.getProject()), String.valueOf(part.getVersion()));
	    obj.setTask(report.getTask());
	    obj.setCostCalcDate(new SimpleDateFormat("yyyyMMdd").format(report.getModifyTimestamp()));

	    obj = (CostInterfaceHistory) PersistenceHelper.manager.save(obj);
	    reCalaulateChild(convertPartList, null, obj, codeMap, tempMap, getChildList(obj));

	}

    }

    private void reCalaulateChild(List<Map<String, Object>> dataList, CostInterfaceChildHistory parentNewPart,
	    CostInterfaceHistory parentHistory, Map<String, Object> codeMap, Map<String, Object> paramMap,
	    List<CostInterfaceChildHistory> childList) throws Exception {

	Transaction trx = new Transaction();
	try {
	    for (Map<String, Object> data : dataList) {
		String oid = (String) data.get("oid");
		CostPart part = (CostPart) CommonUtil.getObject(oid);

		CostInterfaceChildHistory newProduct = null;

		for (CostInterfaceChildHistory obj : childList) {
		    if (obj.getPartNo().equals((String) data.get("partNo"))) {
			newProduct = obj;
			break;
		    }
		}
		data.remove("sortLocation");
		data.remove("version");
		data.remove("projectReference");
		data.remove("project");
		data.remove("parent");

		ObjectUtil.manager.convertMapToObject(data, newProduct);

		newProduct.setCostPart(part);
		newProduct.setSortLocation(part.getSortLocation());

		if (parentNewPart == null) {
		    parentNewPart = newProduct;
		} else {
		    newProduct.setParent(parentNewPart);
		}
		newProduct.setProductCostTotal(CostTreeUtil.manager.erpFormatConvert(newProduct.getProductCostTotal(), 3));
		newProduct = (CostInterfaceChildHistory) PersistenceHelper.manager.save(newProduct);
		updateChildPart(newProduct, parentHistory, codeMap, paramMap);

		if (data.get("Items") != null) {
		    reCalaulateChild((ArrayList<Map<String, Object>>) data.get("Items"), newProduct, parentHistory, codeMap, paramMap,
			    childList);
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

    }

    public void migChildPartCreate() throws Exception {
	Map<String, Object> codeMap = getCodeList();

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec(CostInterfaceHistory.class);
	qs.appendWhere(new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.LASTEST, SearchCondition.IS_TRUE, true),
	        new int[] { 0 });
	qr = PersistenceHelper.manager.find(qs);

	Map<String, Object> pjtMap = new HashMap<String, Object>();
	List<Map<String, Object>> pjtList = new ArrayList<Map<String, Object>>();

	List<CostInterfaceHistory> parentList = new ArrayList<CostInterfaceHistory>();

	while (qr.hasMoreElements()) {

	    CostInterfaceHistory obj = (CostInterfaceHistory) qr.nextElement();
	    parentList.add(obj);

	    if ("제품".equals(obj.getPjtType())) {

		E3PSProject project = ProjectHelper.getProject(obj.getProject().getPjtNo());

		pjtMap.put("pjtNo", project.getReviewPjtNo());
		pjtMap.put("step", obj.getDrStep());
		pjtMap.put("pjtOid", CommonUtil.getOIDLongValue(obj.getProject()));
		pjtMap.put("partNo", obj.getPartNo());

		pjtList.add(pjtMap);
	    }
	}

	for (CostInterfaceHistory obj : parentList) {

	    Map<String, Object> paramMap = new HashMap<String, Object>();

	    if ("검토".equals(obj.getPjtType())) {
		for (Map<String, Object> pjtInfo : pjtList) {
		    String reviewPjtNos = (String) pjtInfo.get("pjtNo");
		    String partNo = (String) pjtInfo.get("partNo");

		    if (reviewPjtNos.contains(obj.getPjtNo()) && obj.getPartNo().equals(partNo)) {
			paramMap.put("PRODUCTSTEP", pjtInfo.get("step"));
			paramMap.put("PRODUCTPJTOID", pjtInfo.get("pjtOid"));
			break;
		    }
		}
	    }

	    Map<String, Object> resMap = costTreeConvertProduct(CommonUtil.getOIDString(obj.getCostPart()));
	    List<Map<String, Object>> convertPartList = (List<Map<String, Object>>) resMap.get("partList");
	    try {
		childPartCreate(convertPartList, null, obj, codeMap, paramMap);

	    } catch (Exception e) {
		obj.setBomTreeCheck(false);
		PersistenceHelper.manager.save(obj);
		e.printStackTrace();
	    }
	}

    }

    public void migChildPartMaterailUpdate() throws Exception {
	QueryResult qr = null;

	QuerySpec qs = new QuerySpec(CostInterfaceHistory.class);
	qs.appendWhere(new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.LASTEST, SearchCondition.IS_TRUE, true),
	        new int[] { 0 });
	qr = PersistenceHelper.manager.find(qs);
	List<CostInterfaceHistory> parentList = new ArrayList<CostInterfaceHistory>();

	while (qr.hasMoreElements()) {
	    CostInterfaceHistory obj = (CostInterfaceHistory) qr.nextElement();
	    List<CostInterfaceChildHistory> childList = getChildList(obj);
	    for (CostInterfaceChildHistory child : childList) {
		updateChildPartMaterial(child);
		PersistenceHelper.manager.save(child);
	    }
	}
    }

    public void migChildPartERPSend() throws Exception {
	QueryResult qr = null;

	QuerySpec qs = new QuerySpec(CostInterfaceHistory.class);
	qs.appendWhere(new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.LASTEST, SearchCondition.IS_TRUE, true),
	        new int[] { 0 });
	qr = PersistenceHelper.manager.find(qs);
	List<CostInterfaceHistory> parentList = new ArrayList<CostInterfaceHistory>();

	while (qr.hasMoreElements()) {
	    CostInterfaceHistory obj = (CostInterfaceHistory) qr.nextElement();
	    parentList.add(obj);
	}

	costChildPartSendErp(parentList, "Z_CO_PLM_COST_2");// 자부품 가공비 erp 전송
	costChildPartSendErp(parentList, "Z_CO_PLM_COST_3");// 자부품 재료비 erp 전송
    }

    private Map<String, Object> costTreeConvertProduct(String oid) throws Exception {
	Map<String, Object> reqMap = new HashMap<String, Object>();
	reqMap.put("DATATYPE", "COSTPART");
	reqMap.put("oid", oid);

	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	List<DefaultMutableTreeNode> nodeList = CostTreeUtil.manager.getCostTreeList(reqMap);

	for (DefaultMutableTreeNode node : nodeList) {

	    Persistable data = (Persistable) node.getUserObject();
	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
	    dataMap.put("oid", CommonUtil.getOIDString(data));
	    dataList.add(dataMap);
	    costTreeGridSetting(node, dataMap, reqMap);
	}

	Map<String, Object> resMap = new HashMap<String, Object>();

	resMap.put("rootPartData", CostTreeUtil.manager.calcConvertProductCost(dataList));
	resMap.put("partList", dataList);

	return resMap;
    }

    private void costTreeGridSetting(DefaultMutableTreeNode parent, Map<String, Object> treeMap, Map<String, Object> reqMap)
	    throws Exception {

	ArrayList<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < parent.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    Persistable data = (Persistable) child.getUserObject();
	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
	    dataMap.put("oid", CommonUtil.getOIDString(data));
	    childList.add(dataMap);
	    costTreeGridSetting(child, dataMap, reqMap);
	}

	if (childList.size() > 0)
	    treeMap.put("Items", childList);
    }

    private List<Map<String, Object>> getResultList(String sql) throws Exception {
	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();
	    rs = stat.executeQuery(sql);

	    list = ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	return list;
    }

    private String getDupCalcDateListQuery() {
	StringBuffer sql = new StringBuffer();

	sql.append("SELECT COSTCALCDATE,REALPARTNO,DRSTEP FROM COSTINTERFACEHISTORY \n");
	sql.append("GROUP BY REALPARTNO,DRSTEP,COSTCALCDATE					  \n");
	sql.append("HAVING COUNT(*) > 1                                                                            \n");

	return sql.toString();
    }

    private String getSendTargetListQuery() {
	StringBuffer sql = new StringBuffer();

	sql.append("SELECT DISTINCT A.PJTNO, VERSION, STEP,'e3ps.project.E3PSProjectMaster:'||B.IDA2A2 PJTOID,'ext.ket.cost.entity.PartList:'||D.IDA2A2 AS PARTLISTOID, PJTTYPENAME \n");
	sql.append("  FROM (SELECT PJTNO, MAX(TO_NUMBER(VERSION)) VERSION,STEP FROM COSTREPORT                                                                                                             \n");
	sql.append("         GROUP BY PJTNO,STEP  ORDER BY PJTNO) A, E3PSPROJECTMASTER B, PJTMASTERPARTLISTLINK C, PARTLIST D                                                    \n");
	sql.append(" WHERE A.PJTNO = B.PJTNO                                                                                                                           \n");
	sql.append("   AND B.IDA2A2 = C.IDA3B5                                                                                                                           \n");
	sql.append("   AND C.IDA3A5 = D.IDA2A2                                                                                                                           \n");
	sql.append(" ORDER BY PJTNO                                                                                                                                          \n");

	return sql.toString();
    }

    // public void test() throws Exception {
    // Map<String, Object> reqMap = new HashMap<String, Object>();
    // reqMap.put("DATATYPE", "COSTPART");
    // reqMap.put("oid", "ext.ket.cost.entity.CostPart:996687562");
    //
    // List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    // List<DefaultMutableTreeNode> nodeList = CostTreeUtil.manager.getCostTreeList(reqMap);
    //
    // for (DefaultMutableTreeNode node : nodeList) {
    //
    // Persistable data = (Persistable) node.getUserObject();
    // Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
    // dataMap.put("oid", CommonUtil.getOIDString(data));
    // dataList.add(dataMap);
    // costTreeGridSetting(node, dataMap, reqMap);
    // }
    // copytest(dataList, null);
    // }

    public void childPartCreate(List<Map<String, Object>> dataList, CostInterfaceChildHistory parentNewPart,
	    CostInterfaceHistory parentHistory, Map<String, Object> codeMap, Map<String, Object> paramMap) throws Exception {

	Transaction trx = new Transaction();
	try {
	    for (Map<String, Object> data : dataList) {
		String oid = (String) data.get("oid");
		CostPart part = (CostPart) CommonUtil.getObject(oid);

		CostInterfaceChildHistory newProduct = CostInterfaceChildHistory.newCostInterfaceChildHistory();
		data.remove("sortLocation");
		data.remove("version");
		data.remove("projectReference");
		data.remove("project");
		data.remove("parent");

		ObjectUtil.manager.convertMapToObject(data, newProduct);

		newProduct.setCostPart(part);
		newProduct.setSortLocation(part.getSortLocation());

		if (parentNewPart == null) {
		    parentNewPart = newProduct;
		} else {
		    newProduct.setParent(parentNewPart);
		}

		newProduct.setProductCostTotal(CostTreeUtil.manager.erpFormatConvert(newProduct.getProductCostTotal(), 3));
		newProduct = (CostInterfaceChildHistory) PersistenceHelper.manager.save(newProduct);
		updateChildPart(newProduct, parentHistory, codeMap, paramMap);

		if (data.get("Items") != null) {
		    childPartCreate((ArrayList<Map<String, Object>>) data.get("Items"), newProduct, parentHistory, codeMap, paramMap);
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

    }

    @SuppressWarnings("unused")
    // 사용안함
    private String getProductCostQuery(Long partOid) {

	/*
	 * ERP 전송대상 항목 수식 정리
	 * 
	 * 1.재료비 = 포장비합계 + 원재료비 + 생산Loss --> materialCost = PACKUNITPRICEOPTION + RAWMATERIALCOSTEXPR + PRODUCTLOSSEXPR
	 * 
	 * 2.직접인건비 = 노무비(laborCostExpr)
	 * 
	 * 3.간접인건비 = r&d (rndExpr)
	 * 
	 * 4.설비감가비 = facReducePrice
	 * 
	 * 5.직접경비 = 전력비 + 타발유 + 금형유지비+기타감가비 --> directCost = ELECCOSTEXPR + TABARYUEXPR + MOLDMAINTENANCE + etcReducePrice
	 * 
	 * 6.간접경비 = 개별포장비 합계 + 간접경비 + 간접경비2 + 원재료물류비 + 원재료관세 + 공정물류비 + 공정관세 + 납입물류비 + 납입관세 --> inDirectCost = PACKUNITPRICESUM +
	 * INDIRECTCOSTEXPR+ INDIRECTCOST2EXPR + MANAGEMTRLOGISEXPR + MANAGEMTRDUTIEEXPR + MTRLTCOSTEXPR + MTRLTRATEEXPR+PAYCOSTLTEXPR +
	 * PAYRATELTEXPR
	 * 
	 * 7.금형감가비 = moldReducePrice계
	 * 
	 * 8.외주가공비 = 외주단가 + 후도금단가 + 후도금비(옵션) + 법인간마진비용 --> outUnitCost = OUTUNITCOSTVAL + APUNITCOSTVAL + APUNITCOSTOPTION
	 * +CORPORATEMARGINCOSTEXP
	 * 
	 * 9.기타원가 = 재료관리비 --> etcCost = MTRMANAGEEXPR
	 * 
	 * 10.판관비 = 일반관리비 + 불량비용 --> salesManageCost = COMANAGEEXPR + DEFECTCOSTEXPR
	 * 
	 * 11.스크랩매출 = SCRAPSALESCOSTEXPR (스크랩판매비)
	 */

	StringBuffer sql = new StringBuffer();

	sql.append(" SELECT SUM(materialCost) AS materialCost, SUM(LABORCOSTEXPR) laborCost, SUM(facReducePrice) facReducePrice, sum(rndExpr) as rndExpr,SUM(directCost) AS directCost ,                                                                                                      \n");
	sql.append("            SUM(inDirectCost) AS inDirectCost, SUM(moldReducePrice) AS moldReducePrice, SUM(outUnitCost) AS outUnitCost, SUM(etcCost) AS etcCost,                                                                                                    \n");
	sql.append("            SUM(salesManageCost) AS salesManageCost, SUM(scrapSalesCost) AS scrapSalesCost   ,                                                                                                                                                      \n");
	sql.append("            SUM(materialCost) + SUM(LABORCOSTEXPR) + sum(rndExpr) +  SUM(facReducePrice) + SUM(directCost) + SUM(inDirectCost) +  SUM(moldReducePrice) + SUM(outUnitCost) + SUM(etcCost)  + SUM(salesManageCost) + SUM(scrapSalesCost)  as  PRODUCTCOSTTOTAL                                                                                                                                                    \n");
	sql.append("   FROM                                                                                                                                                                                                        \n");
	sql.append(" (                                                                                                                                                                                                             \n");
	sql.append(" 	SELECT REALPARTNO, (PACKUNITPRICEOPTION+RAWMATERIALCOSTEXPR+PRODUCTLOSSEXPR) * US AS materialCost, LABORCOSTEXPR, nvl(rndExpr,0) * us as rndExpr, NVL(facReducePrice,0) * US AS facReducePrice,                                                                        \n");
	sql.append("           (ELECCOSTEXPR + TABARYUEXPR + MOLDMAINTENANCE+etcReducePrice) * US AS directCost,                                                                                                                                      \n");
	sql.append("           (PACKUNITPRICESUM + INDIRECTCOSTEXPR+ INDIRECTCOST2EXPR + MANAGEMTRLOGISEXPR + MANAGEMTRDUTIEEXPR + MTRLTCOSTEXPR + MTRLTRATEEXPR + PAYCOSTLTEXPR + PAYRATELTEXPR) * US AS inDirectCost,                  \n");
	sql.append("           NVL(MoldReducePrice ,0)* US AS moldReducePrice, \n");
	sql.append("           (OUTUNITCOSTVAL + APUNITCOSTVAL + APUNITCOSTOPTION +CORPORATEMARGINCOSTEXPR ) * US AS outUnitCost,                                                                                                     \n");
	sql.append("           MTRMANAGEEXPR * US AS etcCost,                                                                                                                                                                       \n");
	sql.append("           (COMANAGEEXPR + DEFECTCOSTEXPR) * US AS salesManageCost,                                                                                                                                                      \n");
	sql.append("           SCRAPSALESCOSTEXPR * US AS scrapSalesCost                                                                                                                                                                 \n");
	sql.append("       FROM COSTPART WHERE IDA2A2 = " + partOid + "\n");
	sql.append("  UNION ALL                                                                                                                                                                                                    \n");
	sql.append("     SELECT * FROM (SELECT REALPARTNO, (PACKUNITPRICEOPTION+RAWMATERIALCOSTEXPR+PRODUCTLOSSEXPR) * US AS materialCost, LABORCOSTEXPR, nvl(rndExpr,0) * us as rndExpr, nvl(facReducePrice,0) * US AS facReducePrice,                                                        \n");
	sql.append(" 	                    (ELECCOSTEXPR + TABARYUEXPR + MOLDMAINTENANCE+etcReducePrice) * US AS directCost,                                                                                                                        \n");
	sql.append("                           (PACKUNITPRICESUM + INDIRECTCOSTEXPR+ INDIRECTCOST2EXPR + MANAGEMTRLOGISEXPR + MANAGEMTRDUTIEEXPR + MTRLTCOSTEXPR + MTRLTRATEEXPR + PAYCOSTLTEXPR + PAYRATELTEXPR)  * US AS inDirectCost,  \n");
	sql.append("                           NVL(MoldReducePrice ,0)* US AS moldReducePrice,      \n");
	sql.append("                          (OUTUNITCOSTVAL + APUNITCOSTVAL + APUNITCOSTOPTION +CORPORATEMARGINCOSTEXPR ) * US AS outUnitCost,                                                                                      \n");
	sql.append("                           MTRMANAGEEXPR * US AS etcCost,                                                                                                                                                       \n");
	sql.append("                          (COMANAGEEXPR + DEFECTCOSTEXPR) * US AS salesManageCost,                                                                                                                                       \n");
	sql.append("                          SCRAPSALESCOSTEXPR * US AS scrapSalesCost                                                                                                                                                  \n");
	sql.append(" 				     FROM COSTPART A                                                                                                                                                                           \n");
	sql.append("                      START WITH IDA3PARENTREFERENCE = " + partOid + " \n");
	sql.append("                      CONNECT BY PRIOR IDA2A2 = IDA3PARENTREFERENCE                                                                                                                                            \n");
	sql.append("                      ORDER SIBLINGS BY SORTLOCATION                                                                                                                                                           \n");
	sql.append("                    )                                                                                                                                                                                          \n");
	sql.append(" )  \n");

	return sql.toString();
    }

    public static void main(String[] args) {
    }
}
