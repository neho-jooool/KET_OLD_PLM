package ext.ket.wfm;

import java.rmi.RemoteException;

import wt.method.RemoteMethodServer;
import wt.session.SessionMgr;
import ext.ket.shared.log.Kogger;
import ext.ket.wfm.service.KETWorkflowHelper;

/**
 * @클래스명 : KETWfProcessTest
 * @작성자 : Jason, Han
 * @작성일 : 2014. 10. 22.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class KETWfProcessTest {

    private static boolean connected = false;

    public static void main(String[] args) {

	String login = "wcadmin";
	String passwd = "wcadmin";

	try {
	    logonToServer(login, passwd);
	    KETWorkflowHelper.service.deleteWfProcessTest();
	} catch (Exception e) {
	    Kogger.error(KETWfProcessTest.class, e);
	    System.exit(1);
	}
	System.exit(0);
    }

    public static void logonToServer(String usr, String pass) throws Exception {
	int i = 1;
	boolean blnStarted = false;
	// Will try to connect 10 times, sleeping 1 minute between times
	while (i < 10) {
	    blnStarted = isSystemStarted();
	    if (blnStarted) {
		if (!connected) {
		    RemoteMethodServer remotemethodserver = RemoteMethodServer.getDefault();
		    remotemethodserver.setUserName(usr);
		    remotemethodserver.setPassword(pass);
		    SessionMgr.getPrincipal();
		    connected = true;
		    return;
		}
	    } else {
		i++;
		// Sleep for 1 minute
		Kogger.debug(KETWfProcessTest.class, "Attempt " + i + " could not connect to Windchill, sleeping for 1 minute");
		try {
		    Thread.sleep(1000 * 60);
		} catch (InterruptedException ex) {
		    // Just ignore
		}
	    }
	}
	if (!blnStarted) {
	    Kogger.debug(KETWfProcessTest.class, "Could not connect after 10 minutes, giving up");
	    System.exit(1);
	}
    }

    /**
     * Checks to see if system is started
     */
    public static final boolean isSystemStarted() {
	try {
	    RemoteMethodServer.ping();
	    return true;
	} catch (final RemoteException ex) {
	    // Just ignore
	    return false;
	}
    }
}
