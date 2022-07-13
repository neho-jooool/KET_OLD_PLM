package ext.ket.cost.code.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

import e3ps.sap.RFCConnect;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class PurchasePriceInterface {
    
    public static PurchasePriceInterface manager = new PurchasePriceInterface();
    
    @SuppressWarnings("rawtypes")
    public static ArrayList getpurchaseInfo(String partNumber) throws Exception {
	ArrayList<HashMap<String, Object>> purchaseList = new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> purchase = null;

	Client client = null;
	IRepository repository = null;

	try {
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_SELECT_PURCHASE_PRICE");
	    Function function = tmpl.getFunction();
	    
	    if(!"".equals(partNumber)){
		function.getImportParameterList().setValue(partNumber, "I_MATNR");
	    }
	    
	    Table tables = function.getTableParameterList().getTable("IT_LIST");

	    try {
		client.execute(function);
	    } catch (Exception e) {
		Kogger.debug(PurchasePriceInterface.class, "Interface[getpurchaseInfo]>>> " + e.toString());
	    }

	    //String r = (String) function.getExportParameterList().getValue("E_RETURN");
	    //int c = function.getExportParameterList().getInt("E_CNT");
	    
	    String DelFlag1 = "";
	    String DelFlag2 = "";
	    String DelFlag3 = "";

	    for (int j = 0; j < tables.getNumRows(); j++) {
		tables.setRow(j);
		
		DelFlag1 = StringUtil.checkNull(tables.getString("DEL1")); //삭제표시1
		DelFlag2 = StringUtil.checkNull(tables.getString("DEL2")); //삭제표시2
		DelFlag3 = StringUtil.checkNull(tables.getString("DEL3")); //삭제표시3
		
		if("X".equals(DelFlag1) || "X".equals(DelFlag2) || "X".equals(DelFlag3)){
		    continue;
		}

		purchase = new HashMap<String, Object>();
		purchase.put("partNo", StringUtil.checkNull(tables.getString("MATNR"))); //품번
		purchase.put("supplierCode", StringUtil.checkNull(tables.getString("LIFNR"))); //공급업체
		purchase.put("supplierName", StringUtil.checkNull(tables.getString("NAME1"))); //공급업체명
		purchase.put("purchaseOrg", StringUtil.checkNull(tables.getString("EKORG"))); //구매조직
		purchase.put("price", StringUtil.checkNull(tables.getString("KBETR"))); //단가
		
		purchase.put("currency", StringUtil.checkNull(tables.getString("KONWA"))); //통화
		purchase.put("per", StringUtil.checkNull(tables.getString("KPEIN"))); //PER
		purchase.put("unit", StringUtil.checkNull(tables.getString("KMEIN"))); //단위
		purchase.put("validDate", DateUtil.getTimestampFormat(StringUtil.checkNull(tables.getString("DATBI")), "yyyy-MM-dd")); //유효종료일
		purchase.put("purchaseOrderDate", DateUtil.getTimestampFormat(StringUtil.checkNull(tables.getString("DATLB")), "yyyy-MM-dd")); //구매오더증빙일
		
		purchaseList.add(purchase);
	    }
	} catch (Exception e) {
	    Kogger.error(PurchasePriceInterface.class, e);
	    throw e;
	} finally {
	    client.disconnect();
	    repository = null;
	}

	return purchaseList;
    }

}
