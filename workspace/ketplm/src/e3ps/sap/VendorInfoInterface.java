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

public class VendorInfoInterface
{
	public static Config conf = ConfigImpl.getInstance();
	public static boolean isERPCheck = conf.getBoolean( "ERPCHECK" );

	public static ArrayList getVendorInfo() {
		ArrayList<Hashtable<String, String>> vendorList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> vendor = null;

		Client client = null;
		IRepository repository = null;

		try {
			client = RFCConnect.getConnection();
	        client.connect();

	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "Z_ST_SELECT_VENDOR_INFO" );
	        Function function = tmpl.getFunction();

	        Table tables = function.getTableParameterList().getTable( "T_LFA1" );

	        try {
	        	client.execute(function);
	        }catch( Exception e ) {
	        	Kogger.debug(VendorInfoInterface.class,  "Interface[getVendorInfo]>>> "+e.toString() );
	        }

	        String r = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int c = function.getExportParameterList().getInt( "E_CNT" );

	        for( int j = 0; j < tables.getNumRows(); j++ ) {
	        	tables.setRow(j);
	        	
	        	vendor = new Hashtable<String, String>();
	        	vendor.put("code", StringUtil.checkNull(tables.getString("LIFNR") )); // 
	        	vendor.put("name", StringUtil.checkNull(tables.getString("NAME1")));
	        	vendor.put("address", StringUtil.checkNull(tables.getString("ORT01") )); // 
	        	vendor.put("representative", StringUtil.checkNull(tables.getString("J_1KFREPRE") )); // 
	        	vendor.put("type", StringUtil.checkNull(tables.getString("J_1KFTIND") )); // 
	        	
				vendorList.add(vendor);
	        }
		}catch( Exception e ) {
			Kogger.error(VendorInfoInterface.class, e);
		}finally {
			client.disconnect();
			repository = null;
		}

		return vendorList;
	}

}
