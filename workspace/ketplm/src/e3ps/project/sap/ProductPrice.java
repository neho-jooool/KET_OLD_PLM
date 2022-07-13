package e3ps.project.sap;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;

import e3ps.sap.RFCConnect;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.shared.log.Kogger;

public class ProductPrice {

    public static final String INITPRICE = "초기예산";
    public static final String ADDPRICE = "추가예산";
    public static final String TOTALPRICE = "총예산";
    public static final String TOTALEXPENSE = "총집행가";
    public static final String INITEXPENSE = "초기총집행가";
    public static final String ADDEXPENSE = "추가집행가";
    public static final String CURRENCY = "통화";
    public static final String EXCHANGE = "환율";

    public static Hashtable getPrice(String orderNumber) {
	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("orderNumber", orderNumber);
	paramMap.put("moldType", "");
	return getProductPrice(paramMap);
    }

    public static Hashtable getPrice(String orderNumber, String moldType) {
	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("orderNumber", orderNumber);
	paramMap.put("moldType", moldType);
	return getProductPrice(paramMap);
    }

    public static Hashtable getProductPrice(Map<String, String> paramMap) {
	String orderNumber = paramMap.get("orderNumber");
	String moldType = paramMap.get("moldType");
	JCO.Client client = null;
	IRepository repository = null;
	Hashtable datas = new Hashtable();
	long tempVal = 0L;

	datas.put(INITPRICE, tempVal);
	datas.put(ADDPRICE, tempVal);
	datas.put(TOTALPRICE, tempVal);
	datas.put(INITEXPENSE, tempVal);
	datas.put(ADDEXPENSE, tempVal);
	datas.put(CURRENCY, tempVal);
	datas.put(EXCHANGE, "KRW");

	try {
	    client = RFCConnect.getConnection();
	    client.connect();
	    repository = JCO.createRepository("BFREPOSITORY", client);
	    // Kogger.debug(getClass(), "Sap #######################################################");

	    // E_AUART
	    // E_WTGES_B //초기예산
	    // E_WTGES_S //추가예산
	    // E_WOGBTR //집행가
	    // E_RETURN //성공여부 "S"성공
	    // E_CNT //처리건수
	    // E_MSG //Message

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_SELECT_BUDGET_EXECUTION");

	    JCO.Function function = tmpl.getFunction();
	    function.getImportParameterList().setValue(orderNumber, "I_AUFNR");
	    client.execute(function);
	    String re = (String) function.getExportParameterList().getValue("E_RETURN");

	    if ("S".equals(re)) {
		// String initplanPrice = (String) function.getExportParameterList().getString("E_WTGES_B");//사업계획 초기 예산
		String initplanPrice = (String) function.getExportParameterList().getString("E_WTGES_R");// 최초 예산
		String addplanPrice = (String) function.getExportParameterList().getString("E_WTGES_S");
		String currency = (String) function.getExportParameterList().getString("E_WAERS");// 통화
		String exchange = (String) function.getExportParameterList().getString("E_EXCHANGE");// 환율

		long initP = 0L;
		long addP = 0L;
		try {
		    // Kogger.debug(getClass(), "JCO  초기예산 :" + initplanPrice);
		    if (initplanPrice.length() > 0) {

			if ("금형(로컬)".equals(moldType)) {
			    initplanPrice = (String) CostCalculateUtil.manager.eval(initplanPrice + "*" + exchange);
			}

			BigDecimal b = new BigDecimal(initplanPrice);
			initP = new Long(b.longValue());
		    }
		} catch (Exception ex) {
		    Kogger.error(ProductPrice.class, ex);
		}

		try {

		    // Kogger.debug(ProductPrice.class, "JCO  추가예산 :" + addplanPrice);
		    if (addplanPrice.length() > 0) {
			if ("금형(로컬)".equals(moldType)) {
			    addplanPrice = (String) CostCalculateUtil.manager.eval(addplanPrice + "*" + exchange);
			}
			BigDecimal b = new BigDecimal(addplanPrice);
			addP = b.longValue();
		    }
		} catch (Exception ex) {
		    Kogger.error(ProductPrice.class, ex);
		}

		long totalPrice = initP + addP;

		long totalex = 0L;

		String exPrice = (String) function.getExportParameterList().getString("E_WOGBTR");

		try {
		    // Kogger.debug(ProductPrice.class, "JCO  집행가 :" + exPrice);
		    if (exPrice.length() > 0) {
			if ("금형(로컬)".equals(moldType)) {
			    exPrice = (String) CostCalculateUtil.manager.eval(exPrice + "*" + exchange);
			}
			BigDecimal b = new BigDecimal(exPrice);
			totalex = b.longValue();
		    }

		} catch (Exception ex) {
		    Kogger.error(ProductPrice.class, ex);
		}

		datas.put(TOTALEXPENSE, totalex);

		long addEx = 0L;
		long initEx = 0L;
		if (totalex > initP) {
		    addEx = totalex - initP;
		    initEx = initP;
		} else {
		    initEx = totalex;
		}
		datas.put(INITPRICE, initP);
		datas.put(ADDPRICE, addP);
		datas.put(TOTALPRICE, totalPrice);
		datas.put(INITEXPENSE, initEx);
		datas.put(ADDEXPENSE, addEx);
		datas.put(CURRENCY, currency);
		datas.put(EXCHANGE, exchange);

		// 집행가
	    }
	    String msg = (String) function.getExportParameterList().getValue("E_MSG");
	    // Kogger.debug(ProductPrice.class, "msg = " + msg);

	} catch (Exception ex) {
	    Kogger.error(ProductPrice.class, ex);
	} finally {
	    if (client != null) {
		client.disconnect();
		repository = null;
	    }
	}

	return datas;
    }

    public static void main(String[] args) throws Exception {
	getPrice("402937");
	// h.put("k", 2);
	// Integer i = (Integer)h.get("k");
	// Kogger.debug(ProductPrice.class, "hh = " + h.get("k").getClass().getName());
    }
}
