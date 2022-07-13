package e3ps.load.bom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import e3ps.bom.service.KETPartHelper;
import e3ps.common.util.DateUtil;
import e3ps.sap.ItemMasterInterface;
import ext.ket.shared.log.Kogger;

// 일배치로 ERP와 PLM의 부품정보를 동기화 시키는 Class
public class ItemSyncBatchCmd {

	public static String strDirPath = "D:\\ptc\\Windchill\\logs\\bom";
	
    public static void main(String[] args) throws SQLException, IOException 
    {
    	if( args.length != 2 )
        {
            // 사용법 설명
            Kogger.debug(ItemSyncBatchCmd.class, ">>> Usage  : java e3ps.load.bom.ItemSyncBatchCmd userID passwd");
            return;
        }
    	
    	// 사용자 인증 
		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);
		
    	String strReturn = "";
    	String[] strArry = {"","","","","","","","","",""}; 		// 0:number, 1:name, 2:unit, 3:partType, 4:partGroup, 5:IsDeleted, 6:BomFlag, 7:prodCode, 8:prodName, 9:isNew
    	
    	Vector<String> vec = new Vector<String>();
    	Hashtable<String, String> hash = new Hashtable<String, String>();
    	Hashtable<?, ?> hashTestPart = null;
    	
    	StringTokenizer token = null;
    	String strToekn = "";	
    	int index = 0;
    	
    	String strDel = "";
    	String strBomFlag = "";
        String strProdCode = "";
        String strProdName = "";
    	
		FileWriter fw= null;
    	String currentDate = DateUtil.getCurrentDateString( new SimpleDateFormat("yyyy-MM-dd"));
    	String currentTime = DateUtil.getCurrentDateString( new SimpleDateFormat("HH:mm:ss"));
    	String strFileName = "ItemSyncLog_" + currentDate + "_" + currentTime.replace(":", "") + ".log";
    	
    	File dir = new File(strDirPath);
    	if (!dir.exists()) {		// 로그 저장 폴더가 존재하지 않는 경우 생성함
    		dir.mkdir();
    	}
    	
    	Kogger.debug(ItemSyncBatchCmd.class, "@@@@@@@@@ strFilePath : " + strDirPath + "\\" + strFileName);    	
    	File file = new File(strDirPath + "\\" + strFileName);
    	if (!file.exists()) {		// 로그 저장 파일이 존재하지 않는 경우 생성함
    		file.createNewFile();
    	}

    	try {
    			// 로그 남길 파일 생성
	    		fw = new FileWriter(file, true);  
    		
	    		vec = ItemMasterInterface.getItemMasterInfo("");
	
	            Kogger.debug(ItemSyncBatchCmd.class,  "@@@@@@@@@@  SAP Part Interface [ vec size ] : " + vec.size() + "@@@@@@@@@@\n" );
	            fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
	    		fw.write(":::::::::::::: SAP PART INTERFACE [size] : " + vec.size() + " 개 \n" );
	    		fw.write(":::::::::::::: START TIME :: " + currentDate + " " + currentTime + "\n");
	    		fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
	    		fw.flush(); 
	
	    		for( int i = 0; i < vec.size(); i++ ){
//	    		for( int i = 0; i < 1000; i++ ){			// 테스트로 1000개 먼저 시간 측정해봄
	    		
	    			strReturn = (String)vec.elementAt(i);
	    			
	    			index = 0;
	    	        token = new StringTokenizer(strReturn, "||");
	    	        while(token.hasMoreElements()) {
	    	        	strToekn = token.nextToken();
	    	        	strArry[index] = strToekn;
	    	        	if(index < 9) index++;
	    	        }
	    	        
	    	        hash.clear();
	    	        hash.put("number", strArry[0]);
	    	        hash.put("name", strArry[1]);
	    	        hash.put("unit", strArry[2]);
	    	        hash.put("partType", strArry[3]);
	    	        hash.put("partGroup", strArry[4]);
	    	        hash.put("isDeleted", strArry[5]);
	    	        hash.put("bomFlag", strArry[6]);
	    	        hash.put("prodCode", strArry[7]);
	    	        hash.put("prodName", strArry[8]);
	    	        hash.put("isNew", strArry[9]);

	    	        // 부품 생성 (신규, 변경 구분)	
	    	        if (!strArry[9].equals("") && strArry[9].equals("I")) {
	    	        	Kogger.debug(ItemSyncBatchCmd.class, "@@@@@@@@@@ " + i + "  번째 부품 생성 @@@@@@@@@@");
	    	        	fw.write(":::::::::::::: [생성] " + i + " 번째 부품번호 : " + strArry[0] + "\n");
	    	        	try {
	    	        		hashTestPart = KETPartHelper.service.create(hash);
	    	        	} catch(Exception e ){
	    	        		fw.write(":::::::::::::: [생성실패]  : " + strArry[0] + "\n");
	    	        		fw.write(":::::::::::::: " + e  + "\n");
	    	        		
	    	    	    	// 인터페이스 결과 Update 하기 전에 빈문자열 처리 다시 변경해주기
	    	    	    	strDel = hash.get("isDeleted");
	    	    	    	strBomFlag = hash.get("bomFlag");
	    	    	    	strProdCode = hash.get("prodCode");
	    	    	    	strProdName = hash.get("prodName");

	    	    	    	if (strDel != null && strDel.equals("O"))		hash.put("isDeleted", "");
	    	    	    	if (strBomFlag != null && strBomFlag.equals("O"))	hash.put("bomFlag", "");
	    	    	    	if (strProdCode != null && strProdCode.equals("O"))	hash.put("prodCode", "");
	    	    	    	if (strProdName != null && strProdName.equals("O"))	hash.put("prodName", "");
	    	        		
	    	        		ItemMasterInterface.setItemMasterResultInfo(hash, false);
	    	        	}
	    	    		fw.flush();	
	    	        } else {
	    	        	Kogger.debug(ItemSyncBatchCmd.class, "@@@@@@@@@@ " + i + "  번째 부품 수정 @@@@@@@@@@");
	    	        	fw.write(":::::::::::::: [수정] " + i + " 번째 부품번호 : " + strArry[0] + "\n");
	    	        	try {
	    	        		hashTestPart = KETPartHelper.service.modify(hash);
	    	        	} catch(Exception e ){
	    	        		fw.write(":::::::::::::: [수정실패]  : " + strArry[0] + "\n");
	    	        		fw.write(":::::::::::::: " + e  + "\n");
	    	        		
	    	    	    	// 인터페이스 결과 Update 하기 전에 빈문자열 처리 다시 변경해주기
	    	    	    	strDel = hash.get("isDeleted");
	    	    	    	strBomFlag = hash.get("bomFlag");
	    	    	    	strProdCode = hash.get("prodCode");
	    	    	    	strProdName = hash.get("prodName");

	    	    	    	if (strDel != null && strDel.equals("O"))		hash.put("isDeleted", "");
	    	    	    	if (strBomFlag != null && strBomFlag.equals("O"))	hash.put("bomFlag", "");
	    	    	    	if (strProdCode != null && strProdCode.equals("O"))	hash.put("prodCode", "");
	    	    	    	if (strProdName != null && strProdName.equals("O"))	hash.put("prodName", "");
	    	        		
	    	        		ItemMasterInterface.setItemMasterResultInfo(hash, false);
	    	        	}
	    	    		fw.flush();	
	    	        }

	    	    	// 인터페이스 결과 Update 하기 전에 빈문자열 처리 다시 변경해주기 
	    	    	strDel = hash.get("isDeleted"); 
	    	    	strBomFlag = hash.get("bomFlag");
	    	    	strProdCode = hash.get("prodCode");
	    	    	strProdName = hash.get("prodName");
	    	    		
	    	    	if (strDel != null && strDel.equals("O"))		hash.put("isDeleted", "");
	    	    	if (strBomFlag != null && strBomFlag.equals("O"))	hash.put("bomFlag", "");
	    	    	if (strProdCode != null && strProdCode.equals("O"))	hash.put("prodCode", "");
	    	    	if (strProdName != null && strProdName.equals("O"))	hash.put("prodName", "");
	    	        
					// I/F 결과 테이블 업데이트
	    	    	String partOid = (String) hashTestPart.get("oid");
	    			if (partOid != null && !partOid.equals("") && !partOid.equals("null")){
	    				Kogger.debug(ItemSyncBatchCmd.class, "@@@@@@@@@@ 저장 성공!");
	    	    		fw.write(":::::::::::::: 저장 완료 :: + "  + partOid + "\n");
	    	    		fw.flush();	
	    				ItemMasterInterface.setItemMasterResultInfo(hash, true);
	    			}else{
	    				Kogger.debug(ItemSyncBatchCmd.class, "@@@@@@@@@@ 저장 실패!");
	    	    		fw.write(":::::::::::::: 저장 실패 \n");
	    	    		fw.flush();	
	    				ItemMasterInterface.setItemMasterResultInfo(hash, false);
	    			}
	    		}
    	}catch( Exception e ){
    		Kogger.error(ItemSyncBatchCmd.class, e);
    	}finally{
        	currentDate = DateUtil.getCurrentDateString( new SimpleDateFormat("yyyy-MM-dd"));
        	currentTime = DateUtil.getCurrentDateString( new SimpleDateFormat("HH:mm:ss"));
    		fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
    		fw.write(":::::::::::::: END TIME :: " + currentDate + " " + currentTime + "\n");
    		fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
    		fw.flush();     		
			fw.close(); 
		}
    }
}
