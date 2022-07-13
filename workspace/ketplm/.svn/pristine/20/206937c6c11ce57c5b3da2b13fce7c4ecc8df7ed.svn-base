package ext.ket.project.mig;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

import wt.fc.*;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.PartDieInfoHandler;
import ext.ket.shared.log.Kogger;


public class MigMoldItemInfoUpdateByPart implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    
    public static MigMoldItemInfoUpdateByPart manager = new MigMoldItemInfoUpdateByPart();
    
    public static void main(String[] args) throws Exception {

	try{


	    Timestamp beginTimestamp = new Timestamp(System.currentTimeMillis());

	    System.out.println("## MIG start " + new Date());

	    MigMoldItemInfoUpdateByPart.manager.saveMig(args);

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
	    
	    updateMoldItemInfo(MigMoldItemInfoUpdateByPart.getAllLastMoldProject(false), false); // last 프로젝트 (checkin 상태)
	    updateMoldItemInfo(MigMoldItemInfoUpdateByPart.getAllLastMoldProject(true), true); // last 프로젝트 (checkout 상태, 즉 일정변경 직전 프로젝트)
	    
	} catch (Exception e) {
	    
	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    
	    throw e;
	    
	} finally{
	    
	    SessionContext.setContext(sessioncontext);
	    
	}

    }
    
    public static void updateMoldItemInfo(QueryResult qr, boolean checkout) throws Exception{
	
	if (qr != null && qr.hasMoreElements()) {
	    PartDieInfoHandler partDieInfoHandler = new PartDieInfoHandler();
	    while (qr.hasMoreElements()) {
		
		Object obj[] = (Object[]) qr.nextElement();
		MoldProject project = (MoldProject) obj[0];
		MoldItemInfo mi = null;
		try {
		    mi = project.getMoldInfo();
		    if (mi != null) {
			String partNo = mi.getPartNo();
			WTPart part = PartBaseHelper.service.getLatestPart(partNo);
			if (part != null) {
			    partDieInfoHandler.updatePartDieInfo(part);
			}
			partNo = project.getPjtNo();
			part = PartBaseHelper.service.getLatestPart(partNo);
			if (part != null) {
			    partDieInfoHandler.updatePartDieInfo(part);
			}
		    }
		} catch (Exception e) {
		    continue;
		}
		
		
	    }
	    
	}
    }
    
    
    public static QueryResult getAllLastMoldProject(boolean checkOut) {

	QueryResult qr = null;
        try {

            QuerySpec qs = new QuerySpec();
            int idx = qs.appendClassList(MoldProject.class, true);
            
            qs.appendWhere(new SearchCondition(MoldProject.class, MoldProject.LASTEST, SearchCondition.IS_TRUE, true), new int[] { idx });
            
            if(checkOut){
        	if (qs.getConditionCount() > 0) qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldProject.class, MoldProject.CHECK_OUT_STATE, SearchCondition.EQUAL, "c/o"), new int[] { idx });
            }else{
        	if (qs.getConditionCount() > 0) qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldProject.class, MoldProject.CHECK_OUT_STATE, SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx });
            }
		
            qr = PersistenceHelper.manager.find(qs);


        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return qr;
    }
}
