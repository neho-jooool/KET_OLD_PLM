package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Hashtable;

import wt.pom.Transaction;
import wt.util.WTException;
import ext.ket.shared.log.Kogger;



public class TaskMailHelper implements wt.method.RemoteAccess, java.io.Serializable {
        static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static TaskMailHelper manager = new TaskMailHelper();


    public synchronized String sendMail(Hashtable hash)throws Exception{

        if(!SERVER) {
            Class argTypes[] = new Class[]{ Hashtable.class };
            Object args[] = new Object[]{hash};
            try {
                return (String)wt.method.RemoteMethodServer.getDefault().invoke(
                        "sendMail",
                        null,
                        this,
                        argTypes,
                        args);
            }
            catch(RemoteException e) {
                Kogger.error(getClass(), e);
                throw new WTException(e);
            }
            catch(InvocationTargetException e) {
                Kogger.error(getClass(), e);
                throw new WTException(e);
            }
            catch(Exception e){
                Kogger.error(getClass(), e);
                throw e;
            }
        }

       Transaction trx = new Transaction();

       try {
    	   trx.start();






            trx.commit();
            trx = null;

       } catch(Exception e) {
           throw e;
       } finally {
           if(trx!=null){
                trx.rollback();
               // SessionHelper.manager.setPrincipal(orgPrincipal.getName());
        }
       }
        return "";
    }




}
