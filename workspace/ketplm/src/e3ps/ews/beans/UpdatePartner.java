/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : UpdatePartner.java
 * 작성자 : 김경희
 * 작성일자 : 2011. 2. 7.
 */
package e3ps.ews.beans;

import wt.method.RemoteMethodServer;
import e3ps.ews.service.KETEwsHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : UpdatePartner
 * 설명 : 협력사 인터페이스 
 * 작성자 : 김경희
 * 작성일자 : 2011. 2. 7.
 */
public class UpdatePartner {

	/**
	 * 함수명 : main
	 * 설명 : 협력사 인터페이스 
	 * @param args
	 * 작성자 : 김경희
	 * 작성일자 : 2011. 2. 7.
	 */
	public static void main(String[] args) throws Exception {
		try {
			RemoteMethodServer methodServer = RemoteMethodServer.getDefault();

			if( methodServer.getUserName() == null ){
				String [] userInfo = {"wcadmin", "wcadmin"};

				if( args.length >= 2 ){
					userInfo[0] = args[0];
					userInfo[1] = args[1];
				}

				methodServer.setUserName( userInfo[0] );
				methodServer.setPassword( userInfo[1] );

				Kogger.debug(UpdatePartner.class, "AUTHENTICATION SUCCESS");
			}else {
				Kogger.debug(UpdatePartner.class, "AUTHENTICATION FAILE");
			}

			Kogger.debug(UpdatePartner.class, "********************"+"협력사 인터페이스 시작입니다.");
			
			boolean flag = KETEwsHelper.service.updatePartner();
			
			if (flag ){
				Kogger.debug(UpdatePartner.class, "********************"+"인터페이스 성공!.");
			}else{
				Kogger.debug(UpdatePartner.class, "********************"+"인터페이스 실패!.");
			}
			
			Kogger.debug(UpdatePartner.class, "********************"+"협력사 인터페이스 끝입니다.");
			
		} catch( Exception e ) {
			Kogger.error(UpdatePartner.class, e);
		}

	}

}
