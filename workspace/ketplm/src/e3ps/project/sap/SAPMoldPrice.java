package e3ps.project.sap;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.DateUtil;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldProject;
import e3ps.project.beans.DebugingData;
import e3ps.sap.RFCConnect;
import ext.ket.shared.log.Kogger;

public class SAPMoldPrice {

    /**
     * @param args
     */
    // public static void getMold

    public static String MOLD_DESIGN = "금형설계";
    public static String MOLD_PART = "부품가공";
    public static String MOLD_PART_GOSU = "부품가공공수";
    public static String MOLD_ASSEMBLING = "조립";
    public static String MOLD_TRY = "Try";
    public static String TOTAL = "TOTAL";
    public static String MOLD_MAKING = "금형제작";

    public static final String[] MOLD_IN = { "금형설계", "부품가공", "조립", "Try" };
    public static final String[] MOLD_OUT = { "금형설계", "금형제작", "Try" };

    public static final String Fun1 = "공수비";
    public static final String Fun2 = "구매발주가";

    public static Vector moldPrice(String orderNumber, String type) {
	JCO.Client client = null;
	IRepository repository = null;
	Vector datas = new Vector();
	try {

	    client = RFCConnect.getConnection();
	    client.connect();
	    repository = JCO.createRepository("BFREPOSITORY", client);

	    String functionName = "";
	    if (Fun1.equals(type)) {
		functionName = "Z_ST_SELECT_LABOR_COST";
	    } else if (Fun2.equals(type)) {
		functionName = "Z_ST_SELECT_PUR_FIXED_PRICE";
	    }

	    IFunctionTemplate tmpl = repository.getFunctionTemplate(functionName);

	    JCO.Function function = tmpl.getFunction();
	    function.getImportParameterList().setValue(orderNumber, "I_AUFNR");
	    client.execute(function);
	    String re = (String) function.getExportParameterList().getValue("E_RETURN");
	    Kogger.debug(SAPMoldPrice.class, "re = " + re);
	    String msg = (String) function.getExportParameterList().getValue("E_MSG");
	    Kogger.debug(SAPMoldPrice.class, "msg = " + msg);
	    JCO.Table reDatas = function.getTableParameterList().getTable("T_COVP");

	    Kogger.debug(SAPMoldPrice.class, "size = " + reDatas.getNumRows());

	    Kogger.debug(SAPMoldPrice.class, "reDatas.getNumRows() ==>" + reDatas.getNumRows());
	    // 작업장 : 7900;
	    // AUFNR CHAR 12 오더 번호
	    // OBJNR CHAR 22 오브젝트 번호
	    // BELNR CHAR 10 회계 전표 번호
	    // GJAHR NUMC 4 회계연도
	    // BUDAT DATS 8 전표의 전기일
	    // WTGBTR CHAR 17 금액
	    // ARBPL CHAR 8 작업장
	    // KTEXT CHAR 40 간단한 내역

	    for (int i = 0; i < reDatas.getNumRows(); i++) {
		reDatas.setRow(i);

		MoldPriceData moldPriceData = new MoldPriceData(reDatas);
		datas.add(moldPriceData);
		String price = reDatas.getString("WTGBTR");
		String date = reDatas.getString("BUDAT");
		String workId = reDatas.getString("ARBPL");
		String number = reDatas.getString("BELNR");
		String content = reDatas.getString("KTEXT");
		// Kogger.debug(SAPMoldPrice.class, "dd = " + date + " price = " + price + "workId = " + workId);
	    }

	} finally {
	    client.disconnect();
	    repository = null;
	}

	return datas;
    }

    public static Vector getMoldPrice(Vector datas, String startDate, String endDate, Hashtable workIdKey) {

	Vector reData = new Vector();
	for (int i = 0; i < datas.size(); i++) {

	    MoldPriceData priceData = (MoldPriceData) datas.get(i);

	    if (startDate.compareTo(priceData.date) <= 0 && endDate.compareTo(priceData.date) >= 0) {
		String workId = priceData.workId;

		if (workIdKey != null) {
		    if (workIdKey.containsKey(workId)) {
			reData.add(priceData);
		    }
		} else {
		    reData.add(priceData);
		}
	    }
	}
	return reData;
    }

    public static Hashtable getWorkCode() {
	Vector v = NumberCodeHelper.manager.getNumberCodeForQuery("WORKSHOPCODE");
	Hashtable hash = new Hashtable();

	for (int i = 0; i < v.size(); i++) {
	    NumberCode numberCode = (NumberCode) v.get(i);
	    if (numberCode.getParent() != null) {
		NumberCode parentNumber = numberCode.getParent();
		String key = parentNumber.getName();
		Hashtable datas = (Hashtable) hash.get(key);
		if (datas == null) {
		    datas = new Hashtable();
		}
		datas.put(numberCode.getName(), numberCode.getCode());
		hash.put(key, datas);
	    }
	}
	return hash;
    }

    public static Vector getMoldProjectPrice(String orderNumber, MoldProject project, Vector debugDatas) throws Exception {

	String making = "";
	if (project != null) {
	    making = project.getMoldInfo().getMaking();
	}

	boolean isIn = false;
	if ("사내".equals(making)) {
	    isIn = true;
	}

	String startDate = "1900-01-01";

	Vector re = new Vector();

	for (int i = 0; i < debugDatas.size(); i++) {
	    DebugingData data = (DebugingData) debugDatas.get(i);
	    E3PSTask task = data.nchaTask;
	    ExtendScheduleData schdata = (ExtendScheduleData) task.getTaskSchedule().getObject();

	    String dStartDate = "";
	    String dEndDate = "";
	    if (schdata.getExecStartDate() != null) {
		dStartDate = DateUtil.getDateString(schdata.getExecStartDate(), "d");
	    } else {
		dStartDate = DateUtil.getDateString(schdata.getPlanStartDate(), "d");
	    }
	    if (schdata.getExecEndDate() != null) {
		dEndDate = DateUtil.getDateString(schdata.getExecEndDate(), "d");
	    } else {
		dEndDate = DateUtil.getDateString(schdata.getPlanEndDate(), "d");
	    }

	    if (re.size() == 0) {
		Timestamp temp = DateUtil.getTimestampFormat(dStartDate, "yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(temp.getTime());
		c.add(Calendar.DATE, -1);
		String endDate = DateUtil.getDateString(c.getTime(), "d");
		re.add(new DateData(startDate, endDate));
	    }
	    re.add(new DateData(dStartDate, dEndDate));
	}

	if (re.size() == 0) {
	    re.add(new DateData(startDate, "9999-01-01"));

	}
	/*
	 * 예)초도의 범위 기간끝은 1차 디버깅의 계획시작일 - 1 또는 실제시작일 - 1(우선) 1차 디버깅의 범위 기간 끝은 2차 디버깅의 계획시작일 -1 또는 실제시작일 -1(우선)
	 */
	for (int i = 0; i < re.size(); i++) {
	    DateData dateDate = (DateData) re.get(i);
	    if ((i + 1) < re.size()) {
		DateData nextDate = (DateData) re.get(i + 1);
		Timestamp temp = DateUtil.getTimestampFormat(nextDate.startDate, "yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(temp.getTime());
		c.add(Calendar.DATE, -1);
		String endDate = DateUtil.getDateString(c.getTime(), "d");
		dateDate.endDate = endDate;
	    }
	}

	DateData lastData = (DateData) re.get(re.size() - 1);
	lastData.endDate = "9999-01-01";

	for (int i = 0; i < re.size(); i++) {
	    DateData dateDate = (DateData) re.get(i);
	    Kogger.debug(SAPMoldPrice.class, "i = " + i + "  " + dateDate.startDate + " ~ " + dateDate.endDate);
	}

	/*
	 * 예)초도의 범위 기간끝은 1차 디버깅의 계획시작일 또는 실제시작일(우선) 1차 디버깅의 범위 기간 끝은 2차 디버깅의 계획시작일 또는 실제시작일(우선) 구현 끝....
	 */

	String keys[] = null;
	if (isIn) {
	    keys = MOLD_IN;
	} else {
	    keys = MOLD_OUT;
	}

	Vector datas = new Vector();

	Hashtable workH = getWorkCode();

	Vector gongsubi = moldPrice(orderNumber, Fun1); // 공수비
	Vector gumebasubi = moldPrice(orderNumber, Fun2); // 발주가

	for (int i = 0; i < re.size(); i++) {
	    DateData dateDate = (DateData) re.get(i);
	    Hashtable hash = new Hashtable();
	    int total = 0;

	    // Kogger.debug(SAPMoldPrice.class, "디버깅 " + (i + 1) + " " + dateDate.startDate + "~" + dateDate.endDate);
	    for (int j = 0; j < keys.length; j++) {
		int price = 0;
		String key = keys[j];
		Hashtable hh = (Hashtable) workH.get(key);
		if ("부품가공".equals(key)) {
		    price = getMoldPriceFromCach(gongsubi, dateDate.startDate, dateDate.endDate, hh);
		    int gume = getMoldPriceFromCach(gumebasubi, dateDate.startDate, dateDate.endDate, null);
		    Kogger.debug(SAPMoldPrice.class, "gume = " + gume);
		    hash.put(MOLD_PART_GOSU, price);
		    price += gume;

		} else if ("금형제작".equals(key)) {
		    price = getMoldPriceFromCach(gumebasubi, dateDate.startDate, dateDate.endDate, null);
		    Kogger.debug(SAPMoldPrice.class, "gume = " + price);
		} else {
		    price = getMoldPriceFromCach(gongsubi, dateDate.startDate, dateDate.endDate, hh);
		}

		hash.put(key, price);
		total += price;
		Kogger.debug(SAPMoldPrice.class, "gume total= " + total);
	    }
	    hash.put(TOTAL, total);
	    datas.add(hash);
	}
	return datas;
    }

    public static int getMoldPriceFromCach(Vector all, String startDate, String endDate, Hashtable hash) {

	Vector datas = getMoldPrice(all, startDate, endDate, hash);
	int price = 0;

	for (int i = 0; i < datas.size(); i++) {

	    MoldPriceData moldPriceData = (MoldPriceData) datas.get(i);
	    try {

		price += Integer.parseInt(moldPriceData.price);
	    } catch (Exception ex) {
		Kogger.error(SAPMoldPrice.class, ex);
		// Kogger.debug(SAPMoldPrice.class, "price ======  " + moldPriceData.price);
		// Kogger.error(SAPMoldPrice.class, ex);
	    }
	    // Kogger.debug(SAPMoldPrice.class, "dd = " + moldPriceData.date + " price = " + moldPriceData.price + "workId = " +
	    // moldPriceData.workId);
	}

	return price;
    }

    public static Long totalPrice(String orderNo, String type) {
	Vector datas = SAPMoldPrice.moldPrice(orderNo, type);

	Long price = 0L;

	for (int i = 0; i < datas.size(); i++) {

	    MoldPriceData moldPriceData = (MoldPriceData) datas.get(i);

	    try {
		price += Long.parseLong(moldPriceData.price);
	    } catch (Exception ex) {
		Kogger.error(SAPMoldPrice.class, ex);
	    }
	}

	return price;
    }

    public static void main(String[] args) throws Exception {
	// TODO Auto-generated method stub
	// "2010-04-17" "2010-05-12"

	// long current = System.currentTimeMillis();

	Vector fucA = moldPrice("22653000", Fun1);
	Vector all = moldPrice("22653000", Fun2);

	Vector datas = getMoldPrice(all, "1111-01-01", "9999-09-09", null);

	for (int i = 0; i < datas.size(); i++) {
	    MoldPriceData moldPriceData = (MoldPriceData) datas.get(i);
	    // Kogger.debug(SAPMoldPrice.class, "dd = " + moldPriceData.date + " price = " + moldPriceData.price + "workId = " +
	    // moldPriceData.workId);
	}

	// Kogger.debug(SAPMoldPrice.class, System.currentTimeMillis() - current);
    }

}

/*
 * class DateData{ public String startDate = ""; public String endDate = ""; public DateData(String startDate, String endDate){
 * this.startDate = startDate; this.endDate = endDate; } }
 */
