package ext.ket.cost.code.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ext.ket.cost.code.service.CostCodeHelper;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipal;
import wt.queue.QueuePseudoMethodArgs;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.WTProperties;

@Component
public class CostSapPriceSchedule implements RemoteAccess {

    private static final Logger LOGGER = Logger.getLogger(CostSapPriceSchedule.class);

    @Scheduled(cron = "0 * 16 * * ?")
    public static void syncPriceFromSap() {

	if (!RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] {};

	    Object args[] = new Object[] {};

	    try {
		//RemoteMethodServer.getDefault().invoke("syncPriceFromSap", CostSapPriceSchedule.class.getName(), null, argTypes, args);
		
		WTProperties prover = WTProperties.getServerProperties(); 
		String server = prover.getProperty("wt.server.codebase"); 
	        URL url = new URL(server + "/"); 
	        RemoteMethodServer.getInstance(url, "BackgroundMethodServer").invoke("_syncPriceFromSap", CostSapPriceSchedule.class.getName(), null, argTypes, args); //Background에서 동작
	    } catch (RemoteException e) {
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
            }

	    return;
	}
	
    }
    
    public static void _syncPriceFromSap() {
	boolean bool = false;
	WTPrincipal localWTPrincipal = null;
	MethodContext localMethodContext = null;
	SessionContext localSessionContext = null;

	try {

	    WTPrincipal localObject = getAdministrator();

	    localMethodContext = new MethodContext(new QueuePseudoMethodArgs(CostSapPriceSchedule.class.getName(), "_syncPriceFromSap"));

	    bool = SessionServerHelper.manager.setAccessEnforced(false);

	    localWTPrincipal = SessionContext.setEffectivePrincipal((WTPrincipal) localObject);

	    localSessionContext = SessionContext.newContext();

	    SessionHelper.manager.setPrincipal(SessionContext.getEffectivePrincipal().getName());
	    
	    LOGGER.info("#############START purchasePriceSapInterface ##########");
	    CostCodeHelper.service.purchasePriceSapInterface();
	    LOGGER.info("#############END purchasePriceSapInterface ##########");

	} catch (Exception e) {
	    e.printStackTrace();
	    LOGGER.info("#############ERROR COSTSAPPRICE SCHEDULE#########" + e.getLocalizedMessage());
	} finally {

	    SessionServerHelper.manager.setAccessEnforced(bool);
	    SessionContext.setEffectivePrincipal(localWTPrincipal);
	    SessionContext.setContext(localSessionContext);
	    localMethodContext.unregister();
	    localMethodContext = null;
	}
    }

    public static WTPrincipal getAdministrator() {
	WTPrincipal localWTPrincipal = null;
	MethodContext localMethodContext = MethodContext.getContext(Thread.currentThread());
	int i = localMethodContext == null ? 1 : 0;

	try {

	    if (i != 0) {
		localMethodContext = new MethodContext(new QueuePseudoMethodArgs(CostSapPriceSchedule.class.getName(), "getAdministrator"));
	    }

	    localWTPrincipal = SessionHelper.manager.getAdministrator();

	} catch (Exception localException) {
	    localException.printStackTrace();
	} finally {
	    if (i != 0)
		localMethodContext.unregister();
	}
	return localWTPrincipal;
    }

    // cron
    // Cron Expression을 이용하여 Task 실행 주기 정의.
    //
    // Cron Expression은 6개의 Field로 구성되며 각 Field는 순서대로 second, minute, hour, day, month, weekday를 의미한다. 각 Field의 구분은 Space로 한다. 또한 month와
    // weekday는 영어로 된 단어의 처음 3개의 문자로 정의할 수 있다.
    //
    // 0 0 * * * * : 매일 매시 시작 시점
    //
    // */10 * * * * * : 10초 간격
    //
    // 0 0 8-10 * * * : 매일 8,9,10시
    //
    // 0 0/30 8-10 * * * : 매일 8:00, 8:30, 9:00, 9:30, 10:00
    //
    // 0 0 9-17 * * MON-FRI : 주중 9시부터 17시까지
    //
    // 0 0 0 25 12 ? : 매년 크리스마스 자정
    //
    // org.springframework.scheduling.support.CronSequenceGenerator API 참조
    //
    // fixed-delay 이전에 실행된 Task의 종료 시간으로부터의 fixed-delay로 정의한 시간만큼 소비한 이후 Task 실행. (Milliseconds 단위로 정의)
    // fixed-rate 이전에 실행된 Task의 시작 시간으로부터 fixed-rate로 정의한 시간만큼 소비한 이후 Task 실행. (Milliseconds 단위로 정의)

    // @Scheduled(fixedDelay=5000)
    // public void printWithFixedDelay() {
    // System.out.println("execute printWithFixedDelay() of Annotated PrintTask at "
    // + new Date());
    // }
    //
    // @Scheduled(fixedRate=10000)
    // public void printWithFixedRate() {
    // System.out.println("execute printWithFixedRate() of Annotated PrintTask at "
    // + new Date());
    // }
    //
    // @Scheduled(cron="*/5 * * * * *")
    // public void printWithCron() {
    // System.out.println("execute printWithCron() of Annotated PrintTask at "
    // + new Date());
    // }
}
