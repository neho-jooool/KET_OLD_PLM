package ext.ket.project.mig;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

import wt.fc.*;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.project.ProductProject;
import e3ps.project.beans.ProjectTaskHelper;
import ext.ket.shared.log.Kogger;


public class MigMainScheduleCodeProject implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    private int cnt = 0;
    private int cnt2 = 0;
    
    public static MigMainScheduleCodeProject manager = new MigMainScheduleCodeProject();
    
    public static void main(String[] args) throws Exception {

	try{


	    Timestamp beginTimestamp = new Timestamp(System.currentTimeMillis());

	    System.out.println("## MIG start " + new Date());

	    MigMainScheduleCodeProject.manager.saveMig(args);

	    Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());

	    System.out.println("## MIG end " + new Date());
	    System.out.println("## MIG elapsed " + ((endTimestamp.getTime() - beginTimestamp.getTime()) / 1000 / 60) + ":"
		    + ((endTimestamp.getTime() - beginTimestamp.getTime()) / 1000 % 60));

	    
	}catch(Exception e){
	    e.printStackTrace();
	}finally{

	}
	
	    
	System.exit(0);
    }
    
    public void saveMig(String[] args) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String[].class };
		Object aobj[] = { args };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveMig", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration(args);
	}
    }
    
    public void executeMigration(String args[]) throws Exception {
	
	SessionContext sessioncontext = SessionContext.newContext();

	try {
	    SessionHelper.manager.setAdministrator();
	    
	    updateMainSchedule(MigMainScheduleCodeProject.getAllLastProductProject(false), false); // last 프로젝트 (checkin 상태)
	    updateMainSchedule(MigMainScheduleCodeProject.getAllLastProductProject(true), true); // last 프로젝트 (checkout 상태, 즉 일정변경 직전 프로젝트)
	    
	    System.out.println("체크아웃 Last 프로젝트 업데이트 건 : "+MigMainScheduleCodeProject.manager.cnt + " 건");
	    System.out.println("체크인 Last 프로젝트 업데이트 건 : "+MigMainScheduleCodeProject.manager.cnt2 + " 건");
	} catch (Exception e) {
	    
	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    
	    throw e;
	    
	} finally{
	    
	    SessionContext.setContext(sessioncontext);
	    
	}

    }
    
    public static void updateMainSchedule(QueryResult qr, boolean checkout) throws Exception{
	
	if (qr != null && qr.hasMoreElements()) {
	    
	    while (qr.hasMoreElements()) {
		
		Object obj[] = (Object[]) qr.nextElement();
		ProductProject project = (ProductProject) obj[0];
		
		boolean target = ProjectTaskHelper.manager.mainSchedulUpdate(project, "", true);
		
		if(target && checkout){
		    MigMainScheduleCodeProject.manager.cnt++;
		}
		if(target && !checkout){
		    MigMainScheduleCodeProject.manager.cnt2++;
		}
	    }
	    
	}
    }
    
    
    public static QueryResult getAllLastProductProject(boolean checkOut) {

	QueryResult qr = null;
        try {

            QuerySpec qs = new QuerySpec();
            int idx = qs.appendClassList(ProductProject.class, true);
            
            qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.LASTEST, SearchCondition.IS_TRUE, true), new int[] { idx });
            
            if(checkOut){
        	if (qs.getConditionCount() > 0) qs.appendAnd();
		qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.CHECK_OUT_STATE, SearchCondition.EQUAL, "c/o"), new int[] { idx });
            }else{
        	if (qs.getConditionCount() > 0) qs.appendAnd();
		qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.CHECK_OUT_STATE, SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx });
            }
            
	    if (qs.getConditionCount() > 0) qs.appendAnd();
		qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.PJT_TYPE, SearchCondition.EQUAL, 2), new int[] { idx });
		
            qr = PersistenceHelper.manager.find(qs);


        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return qr;
    }
}
