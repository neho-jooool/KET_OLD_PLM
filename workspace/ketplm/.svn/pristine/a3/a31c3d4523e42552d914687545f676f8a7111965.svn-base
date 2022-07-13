package ext.ket.cost.code.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

import e3ps.sap.RFCConnect;
import e3ps.common.util.StringUtil;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.shared.log.Kogger;

public class ErpBomInterface {
    
    public static ErpBomInterface manager = new ErpBomInterface();
    
    @SuppressWarnings("rawtypes")
    public static List<Map<String, Object>> getErpBomInfo(Map<String, Object> req) throws Exception {
	
	List<Map<String, Object>> bomList = new ArrayList<Map<String, Object>>();
	HashMap<String, Object> bom = null;

	Client client = null;
	IRepository repository = null;
	
	String partNumber = (String)req.get("partNo");
	String startMonth = (String)req.get("startMonth");
	String endMonth = (String)req.get("endMonth");
	
	if(partNumber.startsWith("H")){
	    partNumber = partNumber.substring(1);
	}

	try {
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_SELECT_REAL_COST_DATA");
	    Function function = tmpl.getFunction();
	    
	    if(!"".equals(partNumber)){
		function.getImportParameterList().setValue(partNumber, "I_MATNR");
	    }
	    if(!"".equals(startMonth)){
		function.getImportParameterList().setValue(startMonth, "I_FRYYMM");
	    }
	    if(!"".equals(endMonth)){
		function.getImportParameterList().setValue(endMonth, "I_TOYYMM");
	    }
	    
	    Table tables = function.getTableParameterList().getTable("IT_LIST");

	    try {
		client.execute(function);
	    } catch (Exception e) {
		Kogger.debug(ErpBomInterface.class, "Interface[getErpBomInfo]>>> " + e.toString());
	    }

	    //String r = (String) function.getExportParameterList().getValue("E_RETURN");
	    //int c = function.getExportParameterList().getInt("E_CNT");
	    
	    /*
	     * MATNR : 최상위제품
	     * IDNRK : 자재번호
	     * PMATNR : 모부품
	     * LOSS  : loss율
	     * MENGE : US
	     * NETPR : 원자재단가
	     * ZUPH : 생산효율
	     * INWON : 작업인원
	     * OUTPR : 외주단가
	     */
	    
	    for (int j = 0; j < tables.getNumRows(); j++) {
		tables.setRow(j);
		
		String realPartNo = StringUtil.checkNull(tables.getString("IDNRK"));
		
		bom = new HashMap<String, Object>();
		bom.put("rootNo", StringUtil.checkNull(tables.getString("MATNR"))); //최상위 제품
		bom.put("realPartNo", realPartNo);
		bom.put("parentPartNo", StringUtil.checkNull(tables.getString("PMATNR")));//1레벨 부모
		String assyLossRate = StringUtil.checkNull(tables.getString("LOSS"));
		String exp = "";
		
		if(StringUtils.isNotEmpty(assyLossRate)){
		    exp = assyLossRate + "/100";
		    assyLossRate = (String)CostCalculateUtil.manager.eval(exp);
		}
		
		bom.put("assyLossRate", assyLossRate); //LOSS율
		
		if(j == 0){
		    bom.put("us", "1"); //root는 1
		}else{
		    bom.put("us", StringUtil.checkNull(tables.getString("MENGE"))); //US    
		}
		
		bom.put("pUnitCost", StringUtil.checkNull(tables.getString("NETPR"))); //구매단가
		bom.put("worker", StringUtil.checkNull(tables.getString("INWON"))); //작업인원
		bom.put("outUnitCost", StringUtil.checkNull(tables.getString("OUTPR"))); //외주단가
		bom.put("cv", StringUtil.checkNull(tables.getString("RUNCV"))); //RUN Cavity
		bom.put("spm", StringUtil.checkNull(tables.getString("STSPM"))); //SPM
		bom.put("ct", StringUtil.checkNull(tables.getString("CTTIM"))); //CT

		bomList.add(bom);
	    }
	} catch (Exception e) {
	    Kogger.error(ErpBomInterface.class, e);
	    throw e;
	} finally {
	    client.disconnect();
	    repository = null;
	}

	return bomList;
    }

}
