package ext.ket.common;

import wt.method.RemoteMethodServer;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.wfm.service.KETWorkspaceHelper;

public class SyncEtcWork2 {
    public static void main(String[] args) throws Exception {

	RemoteMethodServer methodServer = RemoteMethodServer.getDefault();

	if (methodServer.getUserName() == null) {
	    String[] userInfo = { "wcadmin", "wcadmin" };

	    if (args.length >= 2) {
		userInfo[0] = args[0];
		userInfo[1] = args[1];
	    }

	    methodServer.setUserName(userInfo[0]);
	    methodServer.setPassword(userInfo[1]);

	    Kogger.debug(SyncEtcWork2.class, "AUTHENTICATION SUCCESS");
	} else {
	    Kogger.debug(SyncEtcWork2.class, "AUTHENTICATION FAILE");
	}

	Kogger.debug("@@@@@@@ AEGIS 전송을 위한 자재별양산시작일 업데이트 Start");
	try {
	    PartBaseHelper.service.partMassSync();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	Kogger.debug("@@@@@@@ AEGIS 전송을 위한 자재별양산시작일 업데이트 End");

	Kogger.debug("@@@@@@@ 30일 초과 수신목록 자동완료처리 Start");
	KETWorkspaceHelper.service.completeExceedReceive();
	Kogger.debug("@@@@@@@ 30일 초과 수신목록 자동완료처리 End");

    }
}
