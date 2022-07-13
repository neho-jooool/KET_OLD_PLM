package e3ps.sap;

import java.util.HashMap;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Table;

import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.StringUtil;
//import com.sap.mw.jco.IFunctionTemplate;
//import com.sap.mw.jco.IRepository;
//import com.sap.mw.jco.JCO;
//import com.sap.mw.jco.JCO.Table;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;

import ext.ket.shared.log.Kogger;

public class ProcessInterface {
	public static Config conf = ConfigImpl.getInstance();
	public static boolean isERPCheck = conf.getBoolean("ERPCHECK");
	
	public static String AllWorkCenter() throws Exception{
		
		HashMap map = new HashMap();
		map.put("type", "WORKCENTER");		
		map.put("isParent", "false");
		
		QuerySpec qs = new QuerySpec(); 
		QuerySpec subQs = new QuerySpec(); 
		QueryResult qr = null;
		QueryResult subQr = null;
		NumberCode mainNC = null;
		NumberCode subNC = null;
		
		qs = NumberCodeHelper.getCodeQuerySpec(map);
		qr = PersistenceHelper.manager.find(qs);
		Object[] obj = null;	
		Kogger.debug(ProcessInterface.class, "##########################   WorkCenter Erp 동기화 ############################# Start :"+ qr.size());
		while(qr.hasMoreElements()){
			obj = (Object[])qr.nextElement();
			String pcode = "";
			mainNC = (NumberCode)obj[0];
			pcode = mainNC.getCode();
			
			HashMap submap = new HashMap();
			submap.put("parent", mainNC);
			submap.put("type", "WORKCENTER");	
			subQs = NumberCodeHelper.getCodeQuerySpec(submap);
			
			
			
			subQr = PersistenceHelper.manager.find(subQs);
			Kogger.debug(ProcessInterface.class, "subQr.size() ====>"+subQr.size());
			ProcessInterface pi = new ProcessInterface();
			Object[] subobj = null;
			while(subQr.hasMoreElements()){
				String code = "";
				String wctype = "";
				String name = "";
				subobj = (Object[])subQr.nextElement();
				subNC = (NumberCode)subobj[0];
				
				code = subNC.getCode();
				wctype = subNC.getWcType();
				name = subNC.getName();
				
				Kogger.debug(ProcessInterface.class, "code==================>" + code);
				HashMap hm = new HashMap();
				String zFlag = "";
				if(wctype != null){
					if(wctype.trim().equals("사내")){
						zFlag = "A";
					}else if(wctype.trim().equals("사내외주")){
						zFlag = "B";
					}else if(wctype.trim().equals("사외외주")){
						zFlag = "C";
					}else if(wctype.trim().equals("해외외주")){
						zFlag = "D";
					}
				
				
				hm.put("WERKS", pcode);
				hm.put("ARBPL", code);//대문자
				hm.put("Z_DATASTA", "C");
				hm.put("Z_FLAG", zFlag);
				hm.put("STEXT", name);
				pi.WorkCenter(hm);
				
				}
			}
			
		}
		
		Kogger.debug(ProcessInterface.class, "##########################   WorkCenter Erp 동기화 ############################# END");
		return "성공";
	}
	
	public static HashMap WorkCenter(HashMap map)throws Exception{
		
		HashMap remap = new HashMap();
		JCO.Client client = null;
		IRepository repository = null;
		
		try{
		
			client = RFCConnect.getConnection();
	        client.connect();
        
	        repository = JCO.createRepository("BFREPOSITORY", client);
	        
	        IFunctionTemplate tmpl = repository.getFunctionTemplate("ZFSTD_PLM_CREATE_WORKCENTER");
	        
	        JCO.Function function = tmpl.getFunction();
	     	
	     	function.getImportParameterList().setValue("name", "kkk");
	     	function.getImportParameterList().setValue("number", "kkk");
	        /*	
	     	ZFSTD_PLM_CREATE_WORKCENTER
	     	PT_WORKCENTER
	     	WERKS	플랜트 
			ARBPL	작업장 
			Z_DATASTA	STATUS(N(생성), D(삭제), C(수정), R(재생성) )
	
	     	Z_FLAG	사내,사외 구분(사내(A),사내외주(B),사외외주(C),해외외주(D))
			STEXT	작업장 내역
	
			EXPORTING
			PE_FLAG		처리내역
			PE_MSEG		에러 메시지
	
	     	PT_RESULT
	     	Z_SAPSTA	처리코드
			Z_SAPMSG	처리내역
		
			SUBSYS		벤더 코드
	
			hm_New.put("SUBSYS", numberCode.getVenderCode());	
	
			*/
	     	
	     	Table tables = function.getTableParameterList().getTable("PT_WORKCENTER");	
	     	
	     	tables.appendRow();
	     	
	     	tables.setValue(map.get("WERKS"), "WERKS" );
	     	tables.setValue(map.get("ARBPL"), "ARBPL" );
	     	tables.setValue(map.get("Z_DATASTA"), "Z_DATASTA");
	     	tables.setValue(map.get("Z_FLAG"), "Z_FLAG");
	     	tables.setValue(map.get("STEXT"), "STEXT");
	     	tables.setValue(map.get("SUBSYS"), "SUBSYS");
	     	
	        Kogger.debug(ProcessInterface.class, "###################   parameterList - ###############");
	        Kogger.debug(ProcessInterface.class, "WERKS     :" +map.get("WERKS"));
	        Kogger.debug(ProcessInterface.class, "ARBPL     :" +map.get("ARBPL"));
	        Kogger.debug(ProcessInterface.class, "Z_DATASTA :" +map.get("Z_DATASTA"));
	        Kogger.debug(ProcessInterface.class, "Z_FLAG    :" +map.get("Z_FLAG"));
	        Kogger.debug(ProcessInterface.class, "STEXT     :" +map.get("STEXT"));
	        Kogger.debug(ProcessInterface.class, "SUBSYS    :" +map.get("SUBSYS"));
	        
	        client.execute(function);													// Sap 실행
	        
		    
	        String re = (String)function.getExportParameterList().getValue("PE_FLAG"); 							// E: 에러 , 정상 일경우 '' 1차 체크 
			String msg = (String)function.getExportParameterList().getValue("PE_MSEG");  							// 
			
			JCO.Table reDatas = function.getTableParameterList().getTable("PT_RESULT");	
			
			
	        String zre ="";
	        String zmsg ="";
			
	        if( (reDatas == null) || reDatas.isEmpty()) {
	        	zre ="S";
	        	zmsg ="정상적으로 처리 되었습니다.";
			}else{
				Kogger.debug(ProcessInterface.class, "reDatas.getNumRows() ==>" + reDatas.getNumRows());
				for(int i = 0; i < reDatas.getNumRows(); i++) {
					reDatas.setRow(i);
					zre = reDatas.getString("Z_SAPSTA");
					zmsg = reDatas.getString("Z_SAPMSG");
				}
			}
	        
	        Kogger.debug(ProcessInterface.class, "PT_WORKCENTER RE = " + re);
	        Kogger.debug(ProcessInterface.class, "PT_WORKCENTER MSG = " + msg);
	        Kogger.debug(ProcessInterface.class, "PT_WORKCENTER ZRE = " + zre);
	        Kogger.debug(ProcessInterface.class, "PT_WORKCENTER ZMSG = " + zmsg);
	        
	        remap.put("RE", StringUtil.checkNull(zre) );
			remap.put("MSG", StringUtil.checkNull(zmsg) );
			
	        
	        return remap;
		}catch(Exception e){
			Kogger.error(ProcessInterface.class, e); 
		}finally{
			client.disconnect();
			repository = null;
		}
		
	    return remap;
	}
	
	
	public static HashMap ROUTING(HashMap map)throws Exception{
		
		HashMap remap = new HashMap();
		
//		JCO.Client client = null;
//		IRepository repository = null;
		try{
		
//			client = RFCConnect.getConnection();
//	        client.connect();
//	        
//	        repository = JCO.createRepository("BFREPOSITORY", client);
//	        
//	        IFunctionTemplate tmpl = repository.getFunctionTemplate("ZFSTD_PLM_CREATE_ROUTING");
//	        
//	        JCO.Function function = tmpl.getFunction();
	     	
		
			//import
	     	//function.getImportParameterList().setValue("name", "kkk");
	     	//function.getImportParameterList().setValue("number", "kkk");
	        /*	
	        ZFSTD_PLM_CREATE_ROUTING
	     	PT_ROUTING
	     	
			MATNR	자재 번호
			WERKS	플랜트 
			Z_DATASTA	FLAG(N(생성), D(삭제), C(수정), R(재생성) )
			Z_FLAG	사내 사외 구분(사내(A),사내외주(B),사외외주(C),해외외주(D))
			ARBPL	작업장 
			KTEXT	공정 텍스트
			LTXA1	작업내역
			
			VGW01	표준값 
			VGE01	표준 값 단위
			VGW02	표준값 
			VGE02	표준 값 단위
	
	
			EXPORTING
			PE_FLAG		처리내역
			PE_MSEG		에러 메시지
			
	     	PT_RESULT
	     	Z_SAPSTA	처리코드
			Z_SAPMSG	처리내역
	
			*/
	     	
//	     	Table tables = function.getTableParameterList().getTable("PT_ROUTING");	
//	     	
//	     	tables.appendRow();
//	     	
//	     	tables.setValue(map.get("MATNR"), "MATNR" );
//	     	tables.setValue(map.get("WERKS"), "WERKS" );
//	     	tables.setValue(map.get("Z_DATASTA"), "Z_DATASTA");
//	     	tables.setValue(map.get("Z_FLAG"), "Z_FLAG");
//	     	tables.setValue(map.get("ARBPL"), "ARBPL");
//	     	tables.setValue(map.get("KTEXT"), "KTEXT");
//	     	tables.setValue(map.get("LTXA1"), "LTXA1");
//	     	
//	     	tables.setValue(map.get("VGW01"), "VGW01");
//	     	tables.setValue(map.get("VGE01"), "VGE01");
//	     	tables.setValue(map.get("VGW02"), "VGW02");
//	     	tables.setValue(map.get("VGE02"), "VGE02");
//	     	
//	       
//	        client.execute(function);																		// Sap 실행
//	        
//	        String re = (String)function.getExportParameterList().getValue("PE_FLAG"); 							// E: 에러 , 정상 일경우 '' 1차 체크 
//			String msg = (String)function.getExportParameterList().getValue("PE_MSEG");
//			
//			Table result = function.getTableParameterList().getTable("PT_RESULT");	
			String zre = "";
			String zmsg = "";
//			if( (result == null) || result.isEmpty()) {
//				zre = "S";
//				zmsg = "정상적으로 처리 되었습니다.";
//			}else{
//				Kogger.debug(getClass(), "result.getNumRows() ==>" + result.getNumRows());
//				for(int i = 0; i < result.getNumRows(); i++) {
//					result.setRow(i);
//					zre = result.getString("Z_SAPSTA");
//					zmsg = result.getString("Z_SAPMSG");
//				}
//			}
			
			
//			Kogger.debug(getClass(), "PT_ROUTING RE = " + re);
//	        Kogger.debug(getClass(), "PT_ROUTING MSG = " + msg);
//	        Kogger.debug(getClass(), "PT_RESULT zRE = " + zre);
//	        Kogger.debug(getClass(), "PT_RESULT zMSG = " + zmsg);
//			
//			remap.put("RE", StringUtil.checkNull(zre));
//			remap.put("MSG", StringUtil.checkNull(zmsg));
			
		}catch(Exception e){
	        Kogger.error(ProcessInterface.class, e);
	    }finally{
//			client.disconnect();
//			repository = null;
		}
	    
	    return remap;
	    
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		
	}

}
