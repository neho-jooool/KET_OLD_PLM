package e3ps.sap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

import e3ps.cost.util.StringUtil;
import ext.ket.cost.code.sap.PurchasePriceInterface;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.shared.log.Kogger;

public class BudgetExpenseInterface {

    static Map<String, String> erpColumnMap = erpColumnConfig();

    public static String eval(String exp) throws Exception {
	return removeNumberFormating((String) CostCalculateUtil.manager.eval(StringUtils.removeEnd(exp, "+")));
    }

    public static String removeNumberFormating(String num) {
	if (StringUtils.isEmpty(num)) {
	    return "0";
	}
	return num.replace(".0000", "").replace(".00", "");
    }

    public static Map<String, Object> getTotalExpenseByProject(Map<String, Object> reqMap) throws Exception {
	// amt01 : 경비 예산총합 , amt07 : 경비 집행 실적, amt06 : 경비 잔액
	String paramAmt01 = (String) reqMap.get("amt01");
	String paramAmt07 = (String) reqMap.get("amt07");
	String paramAmt06 = (String) reqMap.get("amt06");

	String paramAmt01Total = (String) reqMap.get("totalAmt01");
	String paramAmt07Total = (String) reqMap.get("totalAmt07");
	String paramAmt06Total = (String) reqMap.get("totalAmt06");

	Map<String, Object> resMap = (Map<String, Object>) BudgetExpenseInterface.getBudgetList(reqMap).get("totalData");

	String amt01 = (String) resMap.get("amt01");
	String amt07 = (String) resMap.get("amt07");
	String amt06 = (String) resMap.get("amt06");

	String totalAmt01 = eval(amt01 + "+" + paramAmt01Total);
	String totalAmt07 = eval(amt07 + "+" + paramAmt07Total);
	String totalAmt06 = eval(amt06 + "+" + paramAmt06Total);

	amt01 = eval(amt01 + "+" + paramAmt01);
	amt07 = eval(amt07 + "+" + paramAmt07);
	amt06 = eval(amt06 + "+" + paramAmt06);

	resMap.put("amt01", amt01);
	resMap.put("amt07", amt07);
	resMap.put("amt06", amt06);
	resMap.put("totalAmt01", totalAmt01);
	resMap.put("totalAmt07", totalAmt07);
	resMap.put("totalAmt06", totalAmt06);

	return resMap;
    }

    public static void sortByWbs(List<Map<String, Object>> list) {
	Collections.sort(list, new Comparator<Object>() {

	    @Override
	    public int compare(Object obj1, Object obj2) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map1 = (HashMap<String, Object>) obj1;
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map2 = (HashMap<String, Object>) obj2;

		String x = (String) map1.get("posId");
		String y = (String) map2.get("posId");

		if (x.equals(y)) {
		    return 0;
		} else {
		    return x.compareTo(y);
		}

	    }
	});
    }

    public static List<Map<String, Object>> dataGrouping(List<Map<String, Object>> budgetList) throws Exception {

	Map<String, String> calcMap = new HashMap<String, String>();

	List<Map<String, Object>> reBudgetList = new ArrayList<Map<String, Object>>();

	for (Map<String, Object> org : budgetList) {// 원본 깊은 복사
	    Map<String, Object> copy = new HashMap<String, Object>();
	    copy.putAll(org);
	    reBudgetList.add(copy);
	}

	sortByWbs(reBudgetList);// wbs 요소 기준으로 정렬, 그룹핑해서 금액을 sum하기 위해

	for (Map<String, Object> data : reBudgetList) {
	    String posId = (String) data.get("posId");
	    String amt01 = (String) data.get("amt01");
	    System.out.println("posId : " + posId + "   amt01 : " + amt01);
	}
	System.out.println("-------------------------------------------");
	List<Map<String, Object>> sumBudgetList = new ArrayList<Map<String, Object>>();

	String compareWbs = "";
	String wbs = "";

	Iterator<String> it = erpColumnMap.keySet().iterator();

	String columnKey = "";
	while (it.hasNext()) {
	    String key = erpColumnMap.get(it.next());
	    if (key.startsWith("amt")) {
		columnKey += key + ",";
	    }
	}

	String[] columnKeys = StringUtils.removeEnd(columnKey, ",").split(",");// 금액 관련 컬럼key를 담아놓은 배열

	for (Map<String, Object> data : reBudgetList) {

	    boolean isWbsEq = false;

	    compareWbs = wbs;

	    wbs = (String) data.get("posId");

	    if (StringUtils.isNotEmpty(wbs) && wbs.equals(compareWbs)) {// 아래에서 wbs요소 기준으로 이미 그룹핑한 데이터는 스킵한다
		continue;
	    }

	    String bigo = "";
	    String maxYear = "";
	    for (Map<String, Object> budget : reBudgetList) {
		isWbsEq = wbs.equals((String) budget.get("posId"));

		if (isWbsEq) {
		    String year = (String) budget.get("year");
		    if (maxYear.equals("")) {
			maxYear = year;
		    }
		    if (maxYear.compareTo(year) >= 0) {// max년도의 비고를 가져온다
			maxYear = year;
			bigo = (String) budget.get("bigo");
		    }

		    for (String key : columnKeys) {// 미리 담아놓은 컬럼키 배열 기준으로 금액을 summary 하는 수식을 담는다
			calcMap.put(key, (String) budget.get(key) + "+" + StringUtil.checkNullZero(calcMap.get(key)));
		    }
		    System.out.println("posId : " + wbs + "   " + calcMap.get("amt01"));
		}
	    }

	    if (calcMap.size() > 0) {
		System.out.println("-------------------------------------------");
		data.put("bigo", bigo);
		for (int i = 0; i < calcMap.size(); i++) {// 컬럼키 기준으로 쌓아놓은 수식을 연산하여 결과값을 담는다
		    if (columnKeys[i].equals("amt01")) {
			System.out.println("amt01 : " + calcMap.get(columnKeys[i]) + "  eval = " + eval(calcMap.get(columnKeys[i])));
		    }
		    data.put(columnKeys[i], eval(calcMap.get(columnKeys[i])));
		}
	    }

	    calcMap = new HashMap<String, String>();

	    sumBudgetList.add(data);
	}

	sortByWbs(sumBudgetList);

	return sumBudgetList;
    }

    public static Map<String, Object> getBudgetList(Map<String, Object> reqMap) throws Exception {
	List<Map<String, Object>> budgetList = new ArrayList<Map<String, Object>>();
	Map<String, Object> resultMap = new HashMap<String, Object>();
	Client client = null;
	IRepository repository = null;
	try {
	    String pjtNo = (String) reqMap.get("pjtNo");
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("ZCO_ZCO0390");
	    Function function = tmpl.getFunction();

	    Table projectParamTable = function.getTableParameterList().getTable("IT_PROJECT");

	    // Table wbsParamTable = function.getTableParameterList().getTable("IT_WBS"); //wbs코드 현재 불필요함

	    // Table bukrsParamTable = function.getTableParameterList().getTable("IT_BUKRS");//회사코드별로 조회하려면 세팅해줄것

	    projectParamTable.appendRow();
	    // A20B116
	    projectParamTable.setValue(pjtNo, "PSPID_F");
	    projectParamTable.setValue(pjtNo, "PSPID_T");

	    /**
	     * 회계년도는 디폴트로 sap에서 from : 2000년 ~ to : 오늘날짜 로 세팅해주니 파라미터 세팅 불필요
	     */
	    // function.getImportParameterList().setValue("2020", "I_GJAHR_F");
	    // function.getImportParameterList().setValue("2020", "I_GJAHR_T");

	    Table tables = function.getTableParameterList().getTable("ET_RESULT");

	    Map<String, Object> budgetTotalData = new HashMap<String, Object>();

	    budgetTotalData.put("amt01", "0");
	    budgetTotalData.put("amt02", "0");
	    budgetTotalData.put("amt03", "0");
	    budgetTotalData.put("amt04", "0");
	    budgetTotalData.put("amt05", "0");
	    budgetTotalData.put("amt06", "0");
	    budgetTotalData.put("amt07", "0");

	    try {
		client.execute(function);
	    } catch (Exception e) {
		Kogger.debug(PurchasePriceInterface.class, "Interface[getBudgetList]>>> " + e.toString());
	    }

	    // String r = (String) function.getExportParameterList().getValue("E_RETURN");
	    // int c = function.getExportParameterList().getInt("E_CNT");

	    for (int j = 0; j < tables.getNumRows(); j++) {
		Map<String, Object> budgetData = new HashMap<String, Object>();
		tables.setRow(j);
		for (int i = 0; i < tables.getNumFields(); i++) {
		    String key = tables.getName(i);
		    // System.out.println("key : " + key + " value : " + tables.getValue(key));
		    String plmKey = erpColumnMap.get(key);
		    if (StringUtils.isEmpty(plmKey)) {
			continue;
		    }
		    budgetData.put("CanDelete", "0");
		    budgetData.put("CanSelect", "1");
		    String value = (String) tables.getValue(key);

		    if (plmKey.startsWith("amt")) {
			value = eval(value + "*" + "100");
			String totalValue = eval((String) budgetTotalData.get(plmKey) + "+" + value);
			budgetTotalData.put(plmKey, totalValue);
		    }

		    budgetData.put(plmKey, value);

		}
		budgetList.add(budgetData);

	    }

	    if (budgetList.size() > 0) {
		// 집행실적 : 실적+구매오더+구매요청+임시전표
		String amt07 = (String) budgetTotalData.get("amt02") + "+" + (String) budgetTotalData.get("amt03") + "+"
		        + (String) budgetTotalData.get("amt04") + "+" + (String) budgetTotalData.get("amt05");
		amt07 = eval(amt07);
		budgetTotalData.put("amt07", amt07);
	    }

	    resultMap.put("groupingList", dataGrouping(budgetList));

	    resultMap.put("budgetList", budgetList);
	    resultMap.put("totalData", budgetTotalData);

	} catch (Exception e) {
	    Kogger.error(BudgetExpenseInterface.class, e);
	    throw e;
	} finally {
	    client.disconnect();
	    repository = null;
	}

	return resultMap;
    }

    public static Map<String, String> erpColumnConfig() {

	Map<String, String> columnMap = new HashMap<String, String>();

	columnMap.put("TXT50", "investAcc");// 투자계정
	columnMap.put("KTEXT", "costCenter");// 책임코스트센터
	columnMap.put("WRTTP_41", "amt01");// 총예산
	columnMap.put("WRTTP_04", "amt02");// 실적
	columnMap.put("WRTTP_22", "amt03");// 구매오더
	columnMap.put("WRTTP_21", "amt04");// 구매요청
	columnMap.put("WRTTP_PK", "amt05");// 임시전표
	columnMap.put("WRTTP_AB", "amt06");// 사용가능금액
	columnMap.put("TWAER", "currency");// 거래통화
	columnMap.put("ZTEXT", "bigo");// 비고
	columnMap.put("POSID", "posId");// WBS요소
	columnMap.put("GJAHR", "year");// 회계연도

	return columnMap;
    }
}
