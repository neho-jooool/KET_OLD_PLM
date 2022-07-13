package ext.ket.dqm;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import ext.ket.dqm.service.DqmHelper;
import ext.ket.shared.log.Kogger;

public class sampleDqmDelayMail implements RemoteAccess, Serializable{
    public static sampleDqmDelayMail manager = new sampleDqmDelayMail();
    
    public sampleDqmDelayMail(){
	
    }
    
    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    
    public static void main(String[] args) throws Exception {
	
	try{
	    if(args[0] == null || StringUtils.isEmpty(args[0])){//1이면 메일 발송 0이면 수신자를 보여주기만한다
		args[0] = "0"; 
	    }
	    sampleDqmDelayMail.manager.callDqmService(args[0]);
	}catch(Exception e){
	    e.printStackTrace();
	}
	
    }
    
    public void callDqmService(String args) throws Exception {
	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = {args};
		System.out.println("start");
		RemoteMethodServer.getDefault().invoke("callDqmService", null, this, aclass, aobj);
		System.out.println("end");
		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeTest(args);
	}
    }
    public void executeTest(String args) throws Exception {
	SessionContext sessioncontext = SessionContext.newContext();
	try{
	    DqmHelper.service.dqmDelayMailSend(args);
	}catch(Exception e){
	    e.printStackTrace();
	}finally {
	    SessionContext.setContext(sessioncontext);
	}
    }
}