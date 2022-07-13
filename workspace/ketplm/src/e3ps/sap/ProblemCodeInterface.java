package e3ps.sap;

import java.util.ArrayList;
import java.util.HashMap;

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

public class ProblemCodeInterface
{
	public static Config conf = ConfigImpl.getInstance();
	public static boolean isERPCheck = conf.getBoolean( "ERPCHECK" );

	public static ArrayList getProblemCode() {
		ArrayList<HashMap<String, String>> codeList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> codeInfo = null;

		Client client = null;
		IRepository repository = null;

		try {
			client = RFCConnect.getConnection();
	        client.connect();

	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "Z_ST_SELECT_DEFECT_CODE" );
	        Function function = tmpl.getFunction();

	        Table tables = function.getTableParameterList().getTable( "T_QPCD" );

	        try {
	        	client.execute(function);
	        }catch( Exception e ) {
	        	Kogger.debug(ProblemCodeInterface.class,  "Interface[getProblemCode]>>> "+e.toString() );
	        }

	        String r = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int c = function.getExportParameterList().getInt( "E_CNT" );

	        for( int j = 0; j < tables.getNumRows(); j++ ) {
	        	tables.setRow(j);
	        	
	        	codeInfo = new HashMap<String, String>();
	        	codeInfo.put("code", StringUtil.checkNull(tables.getString("CODE") )); // 코드
	        	codeInfo.put("name", StringUtil.checkNull(tables.getString("KURZTEXT") )); // 코드명
	        	codeInfo.put("group", StringUtil.checkNull(tables.getString("CODEGRUPPE") )); // 상위그룹
				
	        	codeList.add(codeInfo);
	        }
		}catch( Exception e ) {
			Kogger.error(ProblemCodeInterface.class, e);
		}finally {
			client.disconnect();
			repository = null;
		}

		return codeList;
	}

}
