/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EcoBudgetInterface.java
 * 작성자 : 오승연
 * 작성일자 : 2011. 1. 25.
 */
package e3ps.sap;

import java.util.Hashtable;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;

import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : EcoBudgetInterface
 * 설명 : 설계변경 ECO 비용확보여부를 ERP에서 확인하는 클래스
 * 작성자 : 오승연
 * 작성일자 : 2011. 1. 25.
 */
public class EcoBudgetInterface {
	
	public static Config conf = ConfigImpl.getInstance();
	
	public static Hashtable<String, String> getEcoBudget( String devYn, String division, String dieNo, int budget )
	{
		Hashtable<String, String> budgetRtnHash = new Hashtable<String, String>();
		Client client = null;
		IRepository repository = null;
		
		try
		{
			client = RFCConnect.getConnection();
	        client.connect();
	        repository = JCO.createRepository( "BFREPOSITORY", client );
	        
	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "Z_ST_CHECK_BUDGET" );
	        Function function = tmpl.getFunction();
	        	        
	        function.getImportParameterList().setValue( devYn, "I_ZCHK1" );
	        function.getImportParameterList().setValue( division, "I_ZCHK2" );
	        function.getImportParameterList().setValue( dieNo, "I_USER0" );
	        function.getImportParameterList().setValue( budget, "I_WTGES" );
	        
	        client.execute(function);
	        
	        budgetRtnHash.put( "remain_budget" , StringUtil.checkNull((String)function.getExportParameterList().getValue( "E_WTGES" )) );
	        budgetRtnHash.put( "budget_exist_yn" , StringUtil.checkNull((String)function.getExportParameterList().getValue( "E_FLGYN" )) );
	        budgetRtnHash.put( "success_yn" , StringUtil.checkNull((String)function.getExportParameterList().getValue( "E_RETURN" )) );
	        budgetRtnHash.put( "count" , StringUtil.checkNull((String)(function.getExportParameterList().getValue( "E_CNT" )+"") ) );
	        budgetRtnHash.put( "msg" , StringUtil.checkNull((String)function.getExportParameterList().getValue( "E_MSG" )) );
	        
		}
		catch( Exception e )
		{
			Kogger.error(EcoBudgetInterface.class, e);
		}
		finally
		{
			client.disconnect();
			repository = null;
		}
		
		return budgetRtnHash;
	}

}
