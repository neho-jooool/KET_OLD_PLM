package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.sap.ErpPartInterFace;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.log.Kogger;

public class MigPartSendToErp implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigPartSendToErp manager = new MigPartSendToErp();

    public MigPartSendToErp() {

    }

    public static void main(String[] args) {

	try {

	    Kogger.debug(MigPartSendToErp.class, "@start");
	    MigPartSendToErp.manager.saveFromExcel();
	    Kogger.debug(MigPartSendToErp.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigPartSendToErp.class, e);
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
	try {


	    SessionHelper.manager.setAdministrator();
	    
	    String [] partTypes = new String[]{"F","H","R","K","S","D","M","W"};
	    for(String partType : partTypes ) {
		List<String> oidList = getOidList(partType);
		Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");
		// ECO No 없어도 저장되도록 수정
		for(String oid : oidList){

		    WTPart wtpart = (WTPart)CommonUtil.getObject(oid);
		    List<WTPart> partList = new ArrayList<WTPart>();
		    partList.add(wtpart);
		    
		    String ecoNo = ""; // partBaseDao.searchEONo(branchId);
		    Map<String, String> workerNameMap = new HashMap<String, String>();
		    ErpPartInterFace erpPartInterFace = new ErpPartInterFace();
		    Map resultMap = erpPartInterFace.sendPartInfoToErp(partList, ecoNo, workerNameMap, false);

		    boolean success = (Boolean) resultMap.get(ErpPartInterFace.SUCCESS);
//		    List<String> errorLogList = (List<String>) resultMap.get(ErpPartInterFace.PLM_ERROR_LIST);
//		    String erpMsg = (String) resultMap.get(ErpPartInterFace.ERP_ERROR_MSG);
		    Kogger.debug(getClass(), "##" + wtpart.getNumber() + " : " + success);
		    
		}
		Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");
	    }


	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    private String getQuery(String partType) throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append(" select P.CLASSNAMEA2A2||':'||P.IDA2A2 OID \n");
	sb.append(" from wtpartmaster m, wtpart p \n");
	sb.append(" , ( SELECT J.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO \n");
	sb.append("     FROM WTPART I, WTPARTMASTER J  \n");
	sb.append("     WHERE I.IDA3MASTERREFERENCE = J.IDA2A2 \n");
	sb.append("          AND I.LATESTITERATIONINFO = 1   \n");
	sb.append("          AND I.STATECHECKOUTINFO != 'wrk' \n");
	sb.append("     GROUP BY J.IDA2A2  \n");
	sb.append("  ) MAXVERPART  \n");
	sb.append(" where 1=1 \n");
	sb.append(" and m.ida2a2 = P.IDA3MASTERREFERENCE \n");
	sb.append(" and P.LATESTITERATIONINFO = 1 \n");
	sb.append(" and P.STATECHECKOUTINFO != 'wrk' \n");
	sb.append(" and P.STATESTATE = 'APPROVED' \n");
	sb.append(" and M.IDA2A2 = MAXVERPART.IDA2A2 \n");
	sb.append(" AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO \n");
	sb.append(" AND P.PTC_STR_3TYPEINFOWTPART = '" + partType + "' \n");

	return sb.toString();
    }

    private List<String> getOidList(String partType) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	try {

	    String query = getQuery(partType);

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
