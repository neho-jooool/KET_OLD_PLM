package e3ps.qms.service;

import e3ps.qms.control.QmsCtl;

public class UpdatePlmProject {
    public static void main(String[] args) throws Exception {
	QmsCtl qms = new QmsCtl();
	//qms.InsertPLM();
	try {
	    // 사용자 인증
	    wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
	    wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);
	    qms.InsertPLM();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}