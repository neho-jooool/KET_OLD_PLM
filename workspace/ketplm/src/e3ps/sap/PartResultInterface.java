package e3ps.sap;

import java.util.ArrayList;
import java.util.Hashtable;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class PartResultInterface
{
	public static Config conf = ConfigImpl.getInstance();
	public static boolean isERPCheck = conf.getBoolean( "ERPCHECK" );

	public static ArrayList getPartResult( String startDate, String endDate, String partNo) {
		ArrayList<Hashtable<String, String>> resultList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> result = null;

		Client client = null;
		IRepository repository = null;

		try {
			client = RFCConnect.getConnection();
	        client.connect();

	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "Z_ST_SELECT_DEFECT_DATA" );
	        Function function = tmpl.getFunction();
	        
	        function.getImportParameterList().setValue( startDate, "I_SPMON_FR" );
	        function.getImportParameterList().setValue( endDate, "I_SPMON_TO" );
	        function.getImportParameterList().setValue( partNo, "I_MATNR" );

	        Table tables = function.getTableParameterList().getTable( "T_ZDEF" );

	        try {
	        	client.execute(function);
	        }catch( Exception e ) {
	        	Kogger.debug(PartResultInterface.class,  "Interface[getPartResult]>>> "+e.toString() );
	        }

	        String r = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int c = function.getExportParameterList().getInt( "E_CNT" );
	        String msg = (String)function.getExportParameterList().getValue( "E_MSG" );
	        Kogger.debug(PartResultInterface.class, "********************msg : "+msg);

	        for( int j = 0; j < tables.getNumRows(); j++ ) {
	        	tables.setRow(j);
	        	
	        	result = new Hashtable<String, String>();
	        	result.put("partNo", partNo); // 부품번호
	        	result.put("month", StringUtil.checkNull(tables.getString("SPMON") )); // 실적월
	        	result.put("custError", StringUtil.checkNull(tables.getString("TDSEA") )); // 고객불량수량
	        	result.put("outputCnt", StringUtil.checkNull(tables.getString("UMMENGE") )); // 출하수량
	        	result.put("custErrorCnt", StringUtil.checkNull(tables.getString("TDSEA_C") )); // 고객불량건수
	        	result.put("proError", StringUtil.checkNull(tables.getString("AUSMG") )); // 공정불량수량
	        	result.put("proCnt", StringUtil.checkNull(tables.getString("RCVRG") )); // 생산수량
	        	result.put("facility", StringUtil.checkNull(tables.getString("TMEAT") )); // 설비가동율
	        	result.put("perform", StringUtil.checkNull(tables.getString("PERNR") )); // 성능가동율
	        	result.put("good", StringUtil.checkNull(tables.getString("ACPER") )); // 양품율
	        	result.put("inspectCnt", StringUtil.checkNull(tables.getString("LOSMENGE") )); // 수입검사수량
	        	result.put("inspectError", StringUtil.checkNull(tables.getString("LMENGE02") )); // 수입검사불량수량
	        	
	        	resultList.add(result);
	        }
	        
		}catch( Exception e ) {
			Kogger.error(PartResultInterface.class, e);
		}finally {
			client.disconnect();
			repository = null;
		}

		return resultList;
	}

}
