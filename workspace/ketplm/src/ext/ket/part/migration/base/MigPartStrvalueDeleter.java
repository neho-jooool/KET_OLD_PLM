package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.iba.value.StringValue;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.log.Kogger;

public class MigPartStrvalueDeleter implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigPartStrvalueDeleter manager = new MigPartStrvalueDeleter();

    public MigPartStrvalueDeleter() {

    }

    // windchill ext.ket.part.migration.spec.PartSpecAttrWcLoader
    public static void main(String[] args) {

	try {

	    Kogger.debug(MigPartStrvalueDeleter.class, "@start");
	    MigPartStrvalueDeleter.manager.saveFromExcel();
	    Kogger.debug(MigPartStrvalueDeleter.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigPartStrvalueDeleter.class, e);
	}
    }

    public void saveFromExcel() throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {};
		Object aobj[] = {};

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
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
	    executeMigration();
	}
    }

    public void executeMigration() throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();
	    
	    for(int idx = 200000; idx < 100000000; idx= idx+200000){

		List<String> oidList =  searchStringValSomeList(idx);
		for( String oid : oidList){
		    trx = new Transaction();
		    trx.start();
		    StringValue stringValue = (StringValue) rf.getReference(oid).getObject();
		    PersistenceHelper.manager.delete(stringValue);
		    trx.commit();
		    stringValue = null;
		    oid = null;
		}
		oidList = null;
	    }
	    
	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");


	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    private String getStringValue(int strvalueIda2a2) throws Exception {

//	SELECT A.CLASSNAMEA2A2||':'||A.IDA2A2 OID 
//	 FROM STRINGVALUE A
//	 WHERE A.CLASSNAMEKEYA4 = 'wt.part.WTPart' 
//	 AND A.IDA2A2 < 1000000
	 
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT A.CLASSNAMEA2A2||':'||A.IDA2A2 OID \n");
	sb.append(" FROM STRINGVALUE A \n");
	sb.append(" WHERE A.CLASSNAMEKEYA4 = 'wt.part.WTPart' \n");
	sb.append(" AND A.IDA2A2 < " + strvalueIda2a2 + " \n");

	return sb.toString();
    }

    private List<String> searchStringValSomeList(int strvalueIda2a2) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	try {

	    String query = getStringValue(strvalueIda2a2);

	    drwList = oDaoManager.queryForList(query, new StampRowSetStrategy<String>() {
		@Override
		public String mapRow(ResultSet rs) throws SQLException {

		    String oid = rs.getString("OID");
		    return oid;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return drwList;
    }

}
