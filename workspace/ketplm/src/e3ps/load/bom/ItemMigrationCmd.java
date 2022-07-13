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

public class ItemMigrationCmd {

	public static String strDirPath = "D:\\ptc\\Windchill\\logs\\bom";
	
	static String[]  strAdd = {
	};

    public static void main(String[] args) throws SQLException, IOException
    {
    	if( args.length != 2 )
        {
            // 사용법 설명
            Kogger.debug(ItemMigrationCmd.class, ">>> Usage  : windchill e3ps.load.bom.ItemMigrationCmd userID passwd");
            return;
        }

    	// 사용자 인증
		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);

    	String strReturn = "";
    	String[] strArry = {"", "", "", "", "", "", "", "", "", "", "", "", "", ""};	// 0:number, 1:name, 2:unit, 3:partType, 4:partGroup, 5:IsDeleted, 6:BomFlag, 7:prodCode, 8:prodName
    																				// 9:YAZAKI여부, 10:YAZAKI제번, 11:순중량, 12:중량단위, 13: 자재그룹내역

    	Vector<String> vec = new Vector<String>();
    	Hashtable<String, String> hash = new Hashtable<String, String>();
    	Hashtable<?, ?> hashTestPart = null;
    	String strDel = "";
    	String strBomFlag = "";
        String strProdCode = "";
        String strProdName = "";
        String strIsYazaki = "";
        String strYazakiNo = "";
        String strPartWeight = "";
        String strWeightUnit = "";
        String strPartGroupDesc = "";

    	StringTokenizer token = null;
    	String strToekn = "";
    	int index = 0;

		FileWriter fw= null;
    	String currentDate = DateUtil.getCurrentDateString( new SimpleDateFormat("yyyy-MM-dd"));
    	String currentTime = DateUtil.getCurrentDateString( new SimpleDateFormat("HH:mm:ss"));
    	String strFileName = "ItemMigrationLog_" + currentDate + "_" + currentTime.replace(":", "") + ".log";

    	File dir = new File(strDirPath);
    	if (!dir.exists()) {		// 로그 저장 폴더가 존재하지 않는 경우 생성함
    		dir.mkdir();
    	}

    	Kogger.debug(ItemMigrationCmd.class, "@@@@@@@@@ strFilePath : " + strDirPath + "\\" + strFileName);
    	File file = new File(strDirPath + "\\" + strFileName);
    	if (!file.exists()) {		// 로그 저장 파일이 존재하지 않는 경우 생성함
    		file.createNewFile();
    	}

    	try {
    			// 로그 남길 파일 생성
	    		fw = new FileWriter(file, true);

//	    		for (int inx = 0; inx < strAdd.length; inx++) {// 미등록 데이터 입력용 
//	    		vec = ItemMasterInterface.getItemMigration(strAdd[inx], "", "");// 미등록 데이터 입력용 
	    		
//	    		vec = ItemMasterInterface.getItemMigration("", "20110101", "20111231");		// 원래 기간을 나눠서 3개로 동시에 돌리기 위해 소스파일 분리한 것임
	    		vec = ItemMasterInterface.getItemMigration("", "", "");							// 전체 부품 조회

	            Kogger.debug(ItemMigrationCmd.class,  "@@@@@@@@@@  SAP Part Interface [ vec size ] : " + vec.size() + "@@@@@@@@@@\n" );
	            fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
	    		fw.write(":::::::::::::: SAP PART INTERFACE [size] : " + vec.size() + " 개 \n" );
	    		fw.write(":::::::::::::: START TIME :: " + currentDate + " " + currentTime + "\n");
	    		fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
	    		fw.flush();

	    		for( int i = 0; i < vec.size(); i++ ){

	    			strReturn = (String)vec.elementAt(i);
//	    			strReturn = (String)vec.elementAt(0);// 미등록 데이터 입력용 

	    			index = 0;
	    	        token = new StringTokenizer(strReturn, "||");
	    	        while(token.hasMoreElements()) {
	    	        	strToekn = token.nextToken();
	    	        	strArry[index] = strToekn;
	    	        	if(index < 13) index++;
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
	    	        hash.put("yazaki", strArry[9]);
	    	        hash.put("yazakiNo", strArry[10]);
	    	        hash.put("partWeight", strArry[11]);
	    	        hash.put("weightUnit", strArry[12]);
	    	        hash.put("partGroupDesc", strArry[13]);

	    	        // 부품 생성 (신규, 변경 구분)	 : modify 함수 안에서 존재하지 않는 경우에는 생성하고, 존재하는 경우에는 수정함
    	        	Kogger.debug(ItemMigrationCmd.class, "@@@@@@@@@@ " + i + "  번째 부품 생성 : " + strArry[0] +"@@@@@@@@@@");
    	        	fw.write(":::::::::::::: [생성] " + i + " 번째 부품번호 : " + strArry[0] + "\n");
//    	        	Kogger.debug(getClass(), "@@@@@@@@@@ " + inx + "  번째 부품 생성 : " + strArry[0] +"@@@@@@@@@@");// 미등록 데이터 입력용 
//    	        	fw.write(":::::::::::::: [생성] " + inx + " 번째 부품번호 : " + strArry[0] + "\n");// 미등록 데이터 입력용 
    	        	try {
    	        		hashTestPart = KETPartHelper.service.modify(hash);
    	        	} catch(Exception e ){
    	        		fw.write(":::::::::::::: [생성실패]  : " + strArry[0] + "\n");
    	        		fw.write(":::::::::::::: " + e  + "\n");
    	        		
    	    	    	// 인터페이스 결과 Update 하기 전에 빈문자열 처리 다시 변경해주기
    	    	    	strDel = hash.get("isDeleted");
    	    	    	strBomFlag = hash.get("bomFlag");
    	    	    	strProdCode = hash.get("prodCode");
    	    	    	strProdName = hash.get("prodName");
    	    	    	strIsYazaki = hash.get("yazaki");
    	    	    	strYazakiNo = hash.get("yazakiNo");
    	    	    	strPartWeight = hash.get("partWeight");
    	    	    	strWeightUnit = hash.get("weightUnit");
    	    	    	strPartGroupDesc = hash.get("partGroupDesc");

    	    	    	if (strDel != null && strDel.equals("O"))		hash.put("isDeleted", "");
    	    	    	if (strBomFlag != null && strBomFlag.equals("O"))	hash.put("bomFlag", "");
    	    	    	if (strProdCode != null && strProdCode.equals("O"))	hash.put("prodCode", "");
    	    	    	if (strProdName != null && strProdName.equals("O"))	hash.put("prodName", "");
    	    	    	if (strIsYazaki != null && strIsYazaki.equals("O"))	hash.put("yazaki", "");
    	    	    	if (strYazakiNo != null && strYazakiNo.equals("O"))	hash.put("yazakiNo", "");
    	    	    	if (strPartWeight != null && strPartWeight.equals("O"))	hash.put("partWeight", "");
    	    	    	if (strWeightUnit != null && strWeightUnit.equals("O"))	hash.put("weightUnit", "");
    	    	    	if (strPartGroupDesc != null && strPartGroupDesc.equals("O"))	hash.put("partGroupDesc", "");
    	        		
    	        		ItemMasterInterface.setItemMasterResultInfo(hash, false);
    	        	}
    	    		fw.flush();

	    	    	// 인터페이스 결과 Update 하기 전에 빈문자열 처리 다시 변경해주기
	    	    	strDel = hash.get("isDeleted");
	    	    	strBomFlag = hash.get("bomFlag");
	    	    	strProdCode = hash.get("prodCode");
	    	    	strProdName = hash.get("prodName");
	    	    	strIsYazaki = hash.get("yazaki");
	    	    	strYazakiNo = hash.get("yazakiNo");
	    	    	strPartWeight = hash.get("partWeight");
	    	    	strWeightUnit = hash.get("weightUnit");
	    	    	strPartGroupDesc = hash.get("partGroupDesc");

	    	    	if (strDel != null && strDel.equals("O"))		hash.put("isDeleted", "");
	    	    	if (strBomFlag != null && strBomFlag.equals("O"))	hash.put("bomFlag", "");
	    	    	if (strProdCode != null && strProdCode.equals("O"))	hash.put("prodCode", "");
	    	    	if (strProdName != null && strProdName.equals("O"))	hash.put("prodName", "");
	    	    	if (strIsYazaki != null && strIsYazaki.equals("O"))	hash.put("yazaki", "");
	    	    	if (strYazakiNo != null && strYazakiNo.equals("O"))	hash.put("yazakiNo", "");
	    	    	if (strPartWeight != null && strPartWeight.equals("O"))	hash.put("partWeight", "");
	    	    	if (strWeightUnit != null && strWeightUnit.equals("O"))	hash.put("weightUnit", "");
	    	    	if (strPartGroupDesc != null && strPartGroupDesc.equals("O"))	hash.put("partGroupDesc", "");

					// I/F 결과 테이블 업데이트
	    	    	String partOid = (String) hashTestPart.get("oid");
	    			if (partOid != null && !partOid.equals("") && !partOid.equals("null")){
	    				Kogger.debug(ItemMigrationCmd.class, "@@@@@@@@@@ 저장 성공!");
	    	    		fw.write(":::::::::::::: 저장 완료 :: + "  + partOid + "\n");
	    	    		fw.flush();
	    				ItemMasterInterface.setItemMasterResultInfo(hash, true);
	    			}else{
	    				Kogger.debug(ItemMigrationCmd.class, "@@@@@@@@@@ 저장 실패!");
	    	    		fw.write(":::::::::::::: 저장 실패 \n");
	    	    		fw.flush();
	    				ItemMasterInterface.setItemMasterResultInfo(hash, false);
	    			}
	    		}
    	}catch( Exception e ){
    		Kogger.error(ItemMigrationCmd.class, e);
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
