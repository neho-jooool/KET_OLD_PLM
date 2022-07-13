package e3ps.load.ecm;

import e3ps.ecm.beans.MoldEcoBeans;
import ext.ket.shared.log.Kogger;

public class CancelReviseEpmDocCmd {

	/**
	 * 함수명 : main
	 * 설명 : 
	 * @param args
	 * 작성자 : 홍길동
	 * 작성일자 : 2011. 6. 1.
	 */
	public static void main(String[] args) {
		
		if( args.length != 3 )
        {
            // 사용법 설명
            Kogger.debug(CancelReviseEpmDocCmd.class, ">>> Usage  : windchill CancelReviseEpmDocCmd wcadmin wcadmin objectOid");
            return;
        }

    	// 사용자 인증
		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);
		
		MoldEcoBeans beans = new MoldEcoBeans();
		
		try {
			beans.cancelRevEpm(args[2]);
		} catch (Exception e) {
			Kogger.error(CancelReviseEpmDocCmd.class, e);
		}

	}

}
