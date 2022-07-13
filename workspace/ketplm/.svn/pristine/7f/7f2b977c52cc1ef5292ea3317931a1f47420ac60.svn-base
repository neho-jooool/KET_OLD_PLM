package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import e3ps.cost.util.HomePageDBUtil;
import e3ps.sap.ErpPartInterFace;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.edm.service.DrawingDistHelper;
import ext.ket.edm.service.PlmHpIfCtl;
import ext.ket.edm.service.PlmHpIfSendDao;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.log.Kogger;

public class MigPartSendToHompage implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigPartSendToHompage manager = new MigPartSendToHompage();

    public MigPartSendToHompage() {

    }

    public static void main(String[] args) {

	try {

	    Kogger.debug(MigPartSendToHompage.class, "@start");
	    MigPartSendToHompage.manager.saveFromExcel();
	    Kogger.debug(MigPartSendToHompage.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigPartSendToHompage.class, e);
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
	
	Connection conn = null;
	
	try {

	    SessionHelper.manager.setAdministrator();

	    List<String> oidList = getOidList();
	    int total = oidList.size();
	    int sCnt = 0;
	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");
	    
	    
	    DrawingDistHelper.service.sendPartAttr();//홈페이지 형상관리 리셋 후 신규 insert
	    
	    boolean bSuccess = false;
	    
	    conn = HomePageDBUtil.getConnection();
	    this.deleteHompagePartAll(conn);
	    
	    PlmHpIfSendDao dao = new PlmHpIfSendDao(conn);
	    
	    KETDrawingDistReqDTO paramObject = new KETDrawingDistReqDTO();
	    
	    for (String oid : oidList) {

		paramObject.setDirect2Hompage("Y");
		paramObject.setPartOid(oid);

		List<Map<String, Object>> distReqEpmDocList = DrawingDistHelper.service.sendHpList(paramObject);

		// distReqEpmDocList 정보로 mssql 에 저장 후 리턴
		
		int i = dao.savePlmHpIf(distReqEpmDocList);
		if (i > 0)
		    bSuccess = true;
		
		if(bSuccess){
		    sCnt++;
		}else{
		    WTPart part = (WTPart)CommonUtil.getObject(oid);
		    if(part != null){
			System.out.println("전송 실패 : "+part.getNumber()); 
		    }else{
			System.out.println("없는 객체 : "+oid);
		    }
		}

	    }
	    Kogger.debug(getClass(), "마이그레이션 대상 건수 : " + total + " 건");
	    Kogger.debug(getClass(), "성공 건수 : " + sCnt + " 건");
	    Kogger.debug(getClass(), "실패 : " + (total-sCnt) + " 건");
	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");
	    
	    

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    HomePageDBUtil.close(conn);
	    SessionContext.setContext(sessioncontext);
	}
    }
    
    
    private void deleteHompagePartAll(Connection conn) throws Exception {

	PreparedStatement pstmt = null;
	try {
		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE FROM KETPLM.dbo.WKET_IF001 ");

		pstmt = conn.prepareStatement(sb.toString());
		pstmt.executeUpdate();

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (pstmt != null) {
			pstmt.close();
		}

	}

    }

    private String getQuery() throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT  'wt.part.WTPart:'||P.IDA2A2 AS OID                                                                                                 ");
	sb.append("   FROM WTPARTMASTER M                                                                                                                          ");
	sb.append("      , WTPART P                                                                                                                                ");
	sb.append("     ,( SELECT J.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO                                                                       ");
	sb.append("          FROM WTPART I, WTPARTMASTER J                                                                                                         ");
	sb.append("         WHERE I.IDA3MASTERREFERENCE = J.IDA2A2                                                                                                 ");
	sb.append("           AND I.LATESTITERATIONINFO = 1                                                                                                        ");
	sb.append("           AND I.STATECHECKOUTINFO != 'wrk'                                                                                                     ");
	sb.append("      GROUP BY J.IDA2A2) MAXVERPART                                                                                                             ");
	sb.append("      , KETPART_HP_MIG HP                                                                                                                                ");
	sb.append("  WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE                                                                                                        ");
	sb.append("    AND P.LATESTITERATIONINFO = 1                                                                                                               ");
	sb.append("    AND P.STATECHECKOUTINFO NOT IN ('wrk')                                                                                                      ");
	sb.append("    AND M.IDA2A2 = MAXVERPART.IDA2A2                                                                                                            ");
	sb.append("    AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO                                                                              ");
	sb.append("    AND M.WTPARTNUMBER = HP.REAL_WTPARTNUMBER												   ");
	//sb.append("    AND PTC_STR_174TYPEINFOWTPART = 'YES'                                                                                                       ");
	//sb.append("    AND WTPARTNUMBER IN (SELECT C.WTPARTNUMBER FROM KETPARTCLASSLINK A, KETPARTCLASSIFICATION B, WTPARTMASTER C                                 ");
	//sb.append("                          WHERE A.IDA3A5 = B.IDA2A2 AND CATALOGUECODE IS NOT NULL AND A.IDA3B5 =  C.IDA2A2 AND SUBSTR(WTPARTNUMBER,1,1) = 'H')  ");

	return sb.toString();
    }

    private List<String> getOidList() throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	try {

	    String query = getQuery();

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
