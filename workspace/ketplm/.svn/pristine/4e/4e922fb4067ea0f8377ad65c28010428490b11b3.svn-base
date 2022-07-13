/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : UpdateProblemCode.java
 * 작성자 : 김경희
 * 작성일자 : 2011. 2. 1.
 */
package e3ps.ews.beans;

import java.util.ArrayList;
import java.util.HashMap;

import wt.method.RemoteMethodServer;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCodeHelper;
import e3ps.sap.ProblemCodeInterface;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : UpdateProblemCode
 * 설명 : 불량유형코드 인터페이스 
 * 작성자 : 김경희
 * 작성일자 : 2011. 2. 1.
 */
public class UpdateProblemCode {

	/**
	 * 함수명 : main
	 * 설명 : 불량유형코드 인터페이스 
	 * @param args
	 * 작성자 : 김경희
	 * 작성일자 : 2011. 2. 1.
	 */
	public static void main(String[] args) throws Exception {
		try {
			/*RemoteMethodServer methodServer = RemoteMethodServer.getDefault();

			if( methodServer.getUserName() == null ){
				String [] userInfo = {"wcadmin", "wcadmin"};

				if( args.length >= 2 ){
					userInfo[0] = args[0];
					userInfo[1] = args[1];
				}

				methodServer.setUserName( userInfo[0] );
				methodServer.setPassword( userInfo[1] );

				Kogger.debug(UpdateProblemCode.class, "AUTHENTICATION SUCCESS");
			}else {
				Kogger.debug(UpdateProblemCode.class, "AUTHENTICATION FAILE");
			}*/
			SessionServerHelper.manager.setAccessEnforced(true);
			Kogger.debug(UpdateProblemCode.class, "********************"+"불량유형코드 인터페이스 시작입니다.");
			
			ProblemCodeInterface codeif = new ProblemCodeInterface();
			ArrayList codeList = codeif.getProblemCode();
			String oid = null;
			String parentOid = null;
			String msg = null;
			boolean flag = false;
			
			HashMap<String, String> codeInfo = null;
			
			for ( int inx = 0 ; inx < codeList.size() ; inx++){
				codeInfo = (HashMap)codeList.get(inx);
				
				if ( codeInfo.get("group") == null ||  codeInfo.get("group").equals("") ) {
					codeInfo.put("type", "PROBLEMTYPE");
					codeInfo.put("checkSapSubmit", "false");
					
					oid = NumberCodeHelper.manager.getNumberCodeOid("PROBLEMTYPE", codeInfo.get("code"));
					codeInfo.put("oid", oid);
					
					msg = NumberCodeHelper.saveNumberCode(codeInfo);
					if (!msg.equals("저장  되었습니다.") ) {
						flag = true;
					}
				}
			}
			
			for ( int inx = 0 ; inx < codeList.size() ; inx++){
				codeInfo = (HashMap)codeList.get(inx);
				
				if ( codeInfo.get("group") != null && !codeInfo.get("group").equals("") ) {
					codeInfo.put("type", "PROBLEMTYPE");
					codeInfo.put("checkSapSubmit", "false");
					
					oid = NumberCodeHelper.manager.getNumberCodeOid("PROBLEMTYPE", codeInfo.get("code"));
					codeInfo.put("oid", oid);

					parentOid = NumberCodeHelper.manager.getNumberCodeOid("PROBLEMTYPE", codeInfo.get("group"));
					codeInfo.put("parentOid", parentOid);
					
					msg = NumberCodeHelper.saveNumberCode(codeInfo);
					if (!msg.equals("저장  되었습니다.") ) {
						flag = true;
					}
				}
			}
			
			if (!flag ) {
				Kogger.debug(UpdateProblemCode.class, "********************"+"인터페이스 성공!.");
			}else{
				Kogger.debug(UpdateProblemCode.class, "********************"+"인터페이스 실패!.");
			}
			
			Kogger.debug(UpdateProblemCode.class, "********************"+"불량유형코드 인터페이스 끝입니다.");
			
		} catch (WTException e) {
			Kogger.error(UpdateProblemCode.class, e);
		} catch( Exception e ) {
			Kogger.error(UpdateProblemCode.class, e);
		}

	}

}
