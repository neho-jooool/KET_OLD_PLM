package e3ps.qms;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;
import e3ps.load.migration.edm.log.LogToFile;
import e3ps.qms.control.QmsCtl;
import e3ps.qms.util.QMSDBUtil;
import ext.ket.shared.log.Kogger;

public class QmsDataLoader implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    private static String logFile = "QmsDataLoader.log";

    public static QmsDataLoader manager = new QmsDataLoader();

    public QmsDataLoader() {

    }

    // windchill e3ps.qms.QmsDataLoader
    public static void main(String[] args) {

	try {

	    String fileName = null;

	    Kogger.debug(QmsDataLoader.class, "@start");
	    QmsDataLoader.manager.saveFromExcel(fileName);
	    Kogger.debug(QmsDataLoader.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(QmsDataLoader.class, e);
	}
    }

    public void saveFromExcel(String fileName) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { fileName };

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
	    executeMigration(fileName);
	}
    }

    public void executeMigration(String fileName) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	// Transaction trx = null;

	// Connection plmConn = null;
	Connection qmsConn = null;

	PreparedStatement pstmt = null;
	StringBuffer sb = null;

	try {

	    Kogger.debug(getClass(), "**************** Qms Interface Start **************************");

	    SessionHelper.manager.setAdministrator();

	    // plmConn = DBUtil.getConnection(false);
	    // PlmDao plmDao = new PlmDao(plmConn);

	    qmsConn = QMSDBUtil.getConnection(false);
	    // QmsDao qmsDao = new QmsDao(qmsConn);

	    String initTargetTb = "TBL_INF_PLM_PJT";

	    initQms(initTargetTb, qmsConn, pstmt, sb);

	    // trx = new Transaction();
	    // trx.start();

	    QmsCtl qmsCtl = new QmsCtl();

	    ArrayList<Hashtable<String, String>> projectList = qmsCtl.PlmProjectList();

	    int complete = setInsertPLMProject(qmsConn, projectList, pstmt, sb);

	    System.out.println(complete + " 건 Insert 성공..");

	    // trx.commit();
	    qmsConn.commit();
	    Kogger.debug(getClass(), "**************** Qms Interface End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    try {
		qmsConn.rollback();
	    } catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	    // trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);

	    QMSDBUtil.close(qmsConn);
	    DBUtil.close(pstmt);
	}
    }

    public void initQms(String table, Connection qmsConn, PreparedStatement pstmt, StringBuffer sb) throws Exception {
	sb = null;
	sb = new StringBuffer();
	sb.append("DELETE FROM " + table);

	try {
	    pstmt = qmsConn.prepareStatement(sb.toString());
	    pstmt.executeUpdate();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();

	    throw new Exception("QMS 프로젝트 삭제 중 오류 발생");
	}

    }

    public ArrayList getPlmProjectList(Connection plmConn, PreparedStatement pstmt, StringBuffer sb, ResultSet rs) throws Exception {
	ArrayList<Hashtable<String, String>> projectList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> projectHash = null;
	sb = null;
	sb = new StringBuffer();

	sb.append("SELECT \n");
	sb.append("  A.ida2a2, \n");
	sb.append("  A.pjtno, \n"); // 프로젝트번호
	sb.append("  A.pjtname, \n"); // 프로젝트명
	sb.append("  J.pnum, \n"); // 부품코드
	sb.append("  J.pname, \n"); // 부품명
	sb.append("  C.code AS cartype_code, \n"); // 차종코드
	sb.append("  C.name AS cartype_name, \n"); // 차종명
	sb.append("  (SELECT c.ERPPRODCODE FROM WTPARTMASTER m, KETPartClassLink pclink, KETPartClassification c  WHERE m.wtpartnumber = J.pnum AND m.ida2a2 = pclink.ida3b5 AND c.ida2a2 = pclink.ida3a5) AS producttype_code, \n"); // 제품군코드
	sb.append("  (SELECT classkrname FROM KETPartClassification kpc WHERE D.ida3a3 = kpc.ida2a2) ||'/'||D.classkrname||'/'||(SELECT c.CLASSKRNAME FROM WTPARTMASTER m, KETPartClassLink pclink, KETPartClassification c  WHERE m.wtpartnumber = J.pnum AND m.ida2a2 = pclink.ida3b5 AND c.ida2a2 = pclink.ida3a5) AS producttype_name, \n"); // 제품군명
	sb.append("  (SELECT md.code FROM projectmemberlink ml, wtuser mu, people mp, department md WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT01' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4 AND mp.ida3c4 = md.ida2a2) as dept_code, \n"); // 개발부서코드
	sb.append("  (SELECT md.name FROM projectmemberlink ml, wtuser mu, people mp, department md WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT01' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4 AND mp.ida3c4 = md.ida2a2) as dept_name, \n"); // 개발부서명
	sb.append("  pmp.accountno as account_no, \n"); // pm사번
	sb.append("  pmp.name as pm_name, \n"); // pm명
	// sb.append("  E.code as developenttype_code, \n"); // 개발구분코드
	// sb.append("  E.name as developenttype_name, \n"); // 개발구분명
	sb.append(" case when B.ida3n9 = '1077500563' then 'DEV005' 	\n");
	sb.append(" 	 when B.ida3n9 = '1077500565' then 'DEV004'	\n");
	sb.append("      else E.code					\n"); // 개발구분코드
	sb.append(" end developenttype_code,				\n");
	sb.append(" case when B.ida3n9 = '1077500563' then '양산변경개발'	\n");
	sb.append("      when B.ida3n9 = '1077500565' then '추가금형'	\n");
	sb.append("      else E.name					\n"); // 개발구분명
	sb.append("  end developenttype_name, 		\n");
	sb.append("  B.statestate as statestate, \n"); // 상태코드
	sb.append("  to_char(F.planenddate, 'YYYYMMDD') as planenddate, \n"); // 계획완료일
	sb.append("  G.code as process_code, \n"); // 개발단계코드
	sb.append("  G.name as process_name, \n"); // 개발단계
	sb.append("  (SELECT count(mold.itemtype) as mold_total FROM MoldItemInfo mold, Moldproject moldpjt, PRODUCTPROJECT pjt WHERE mold.ida2a2 = moldpjt.ida3a10 AND mold.ida3a3 = pjt.ida2a2 AND mold.itemtype = 'Mold' AND moldpjt.lastest = 1 AND moldpjt.checkoutstate != 'c/o' AND pjt.ida2a2 = B.ida2a2 GROUP BY pjt.ida2a2) as mold_count, \n"); // mold수
	sb.append("  (SELECT count(mold.itemtype) as mold_total FROM MoldItemInfo mold, Moldproject moldpjt, PRODUCTPROJECT pjt WHERE mold.ida2a2 = moldpjt.ida3a10 AND mold.ida3a3 = pjt.ida2a2 AND mold.itemtype = 'Mold' AND moldpjt.lastest = 1 AND moldpjt.statestate = 'COMPLETED' AND moldpjt.checkoutstate != 'c/o' AND pjt.ida2a2 = B.ida2a2 GROUP BY pjt.ida2a2) as mold_state, \n"); // mold완료수
	sb.append("  (SELECT count(mold.itemtype) as mold_total FROM MoldItemInfo mold, Moldproject moldpjt, PRODUCTPROJECT pjt WHERE mold.ida2a2 = moldpjt.ida3a10 AND mold.ida3a3 = pjt.ida2a2 AND mold.itemtype = 'Press' AND moldpjt.lastest = 1 AND moldpjt.checkoutstate != 'c/o' AND pjt.ida2a2 = B.ida2a2 GROUP BY pjt.ida2a2) as press_count, \n"); // press수
	sb.append("  (SELECT count(mold.itemtype) as mold_total FROM MoldItemInfo mold, Moldproject moldpjt, PRODUCTPROJECT pjt WHERE mold.ida2a2 = moldpjt.ida3a10 AND mold.ida3a3 = pjt.ida2a2 AND mold.itemtype = 'Press' AND moldpjt.lastest = 1 AND moldpjt.statestate = 'COMPLETED' AND moldpjt.checkoutstate != 'c/o' AND pjt.ida2a2 = B.ida2a2 GROUP BY pjt.ida2a2) as press_state, \n"); // press완료수
	sb.append("  (SELECT to_char(op.oemenddate, 'YYYYMMDD') FROM OEMProjectType oempt, ModelPlan mp, OEMPlan op, OEMProjectType ceoempt WHERE B.ida3d8 = oempt.ida2a2 AND oempt.ida2a2 = mp.ida3b3 AND mp.ida2a2 = op.ida3b3 AND op.ida3a3 = ceoempt.ida2a2 AND ceoempt.NAME = 'PILOT1') AS oem1plan, \n"); // p1일자
	sb.append("  (SELECT to_char(op.oemenddate, 'YYYYMMDD') FROM OEMProjectType oempt, ModelPlan mp, OEMPlan op, OEMProjectType ceoempt WHERE B.ida3d8 = oempt.ida2a2 AND oempt.ida2a2 = mp.ida3b3 AND mp.ida2a2 = op.ida3b3 AND op.ida3a3 = ceoempt.ida2a2 AND ceoempt.NAME = 'PILOT2') AS oem2plan, \n"); // p2일자
	sb.append("  (SELECT to_char(op.oemenddate, 'YYYYMMDD') FROM OEMProjectType oempt, ModelPlan mp, OEMPlan op, OEMProjectType ceoempt WHERE B.ida3d8 = oempt.ida2a2 AND oempt.ida2a2 = mp.ida3b3 AND mp.ida2a2 = op.ida3b3 AND op.ida3a3 = ceoempt.ida2a2 AND ceoempt.NAME = 'SOP') AS oemSOPplan, \n"); // sop일자
	sb.append("  (SELECT max(to_char(es.PLANENDDATE, 'YYYYMMDD')) FROM e3pstask task, ExtendScheduleData es WHERE REPLACE(task.taskname, ' ', '') = 'GATE4' AND task.ida3a4 = es.ida2a2 AND task.ida3b4 = B.ida2a2) AS gate4date, \n"); // gate4일자
	sb.append("  (SELECT pd FROM (SELECT TO_CHAR(es.PLANENDDATE, 'YYYYMMDD') AS pd, ROW_NUMBER() OVER (ORDER BY es.PLANENDDATE) AS rn FROM e3pstask task, ExtendScheduleData es WHERE REPLACE(task.taskname, ' ', '') like '양산Tool%' AND task.ida3a4 = es.ida2a2) WHERE rn = 1 ) AS systemenddate, \n"); // 설비완료일자
	sb.append("  (SELECT mp.accountno FROM projectmemberlink ml, wtuser mu, people mp WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT15' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4) AS rolemember_code, \n"); // 선행품질담당사번
	// 생산처명
	sb.append("  (SELECT mp.name FROM projectmemberlink ml, wtuser mu, people mp WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT15' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4) AS rolemember_name, \n"); // 선행품질담당명
	sb.append("  H.name AS rank, \n"); // rank
	sb.append("  (SELECT listagg(K.code, ',') WITHIN GROUP (ORDER BY K.code) FROM ProjectSubcontractorLink J, NumberCode K WHERE B.ida2a2 = J.ida3b5(+) AND J.ida3A5 = K.ida2a2(+)) AS subcontractor_code, \n"); // 고객처코드
	sb.append("  (SELECT listagg(K.name, ',') WITHIN GROUP (ORDER BY K.code) FROM ProjectSubcontractorLink J, NumberCode K WHERE B.ida2a2 = J.ida3b5(+) AND J.ida3A5 = K.ida2a2(+)) AS subcontractor_name, \n"); // 고객처명
	sb.append("  I.code AS designtype_code, \n"); // 설계구분코드
	sb.append("  I.name AS designtype_name, \n"); // 설계구분명
	sb.append("  replace(B.developedType, '|', ',') AS developpattern_code, \n"); // 개발유형코드
	sb.append("  (SELECT listagg(C.name, ',') WITHIN GROUP (ORDER BY C.code) FROM numbercode C WHERE C.code IN (SELECT regexp_substr(replace(B.developedType, '|', ','), '[^,]+', 1, level) AS developedTypeName FROM dual CONNECT BY regexp_substr(replace(B.developedType, '|', ','), '[^,]+', 1, level) is not null)) AS develppattern_name, \n"); // 개발유형명
	sb.append("  to_char(F.execstartdate, 'YYYYMMDD') as execstartdate, \n"); // 개발시작일
	sb.append("  to_char(F.execenddate, 'YYYYMMDD') as execenddate, \n"); // 개발종료일
	sb.append("  (SELECT MAX(DISTINCT to_char(es.PLANENDDATE, 'YYYYMMDD')) AS enddate FROM e3pstask tq, ExtendScheduleData es WHERE 1=1 AND tq.ida3b4 = B.ida2a2 AND tq.taskname = '신뢰성평가' AND tq.ida3a4 = es.ida2a2) AS taskexecenddate, \n"); // 신뢰성완료일
	sb.append("  to_char(B.createstampa2, 'YYYYMMDD') AS createdate, \n"); // 프로젝트생성일
	sb.append("  to_char(B.updatestampa2, 'YYYYMMDD') AS updatedate \n"); // 프로젝트수정일
	sb.append("FROM (SELECT MAX(JB.ida2a2) AS ida2a2 FROM E3PSPROJECTMASTER JA, PRODUCTPROJECT JB WHERE JA.ida2a2 = JB.ida3b8 GROUP BY JA.ida2a2) K, \n");
	sb.append("     E3PSPROJECTMASTER A, \n");
	sb.append("     PRODUCTPROJECT B, \n");
	sb.append("     OEMProjectType C, \n");
	sb.append("     KETPartClassification D, \n");
	sb.append("     wtuser pmu, \n");
	sb.append("     people pmp, \n");
	sb.append("     ProjectMemberLink pml, \n");
	sb.append("     numbercode E, \n");
	sb.append("     ExtendScheduleData F, \n");
	sb.append("     numbercode G, \n");
	sb.append("     numbercode H, \n");
	sb.append("     numbercode I, \n");
	sb.append("     PRODUCTINFO J \n");
	sb.append("WHERE 1=1 \n");
	sb.append("  AND K.ida2a2 = B.ida2a2 \n");
	sb.append("  AND A.ida2a2 = B.ida3b8 \n");
	sb.append("  AND B.ida2a2 = J.ida3a3(+) \n");
	sb.append("  AND B.ida3d8 = C.ida2a2(+) \n");
	sb.append("  AND substr(B.productTypeLevel2, instr(B.productTypeLevel2, ':')+1, length(B.productTypeLevel2)) = D.ida2a2(+) \n");
	sb.append("  AND pml.PJTMEMBERTYPE = 0 \n");
	sb.append("  AND B.ida2a2 = pml.ida3a5 \n");
	sb.append("  AND pmu.ida2a2 = pml.ida3b5 \n");
	sb.append("  AND pmu.ida2a2 = pmp.ida3b4 \n");
	sb.append("  AND B.ida3d9 = E.ida2a2(+) \n");
	sb.append("  AND B.ida3a8 = F.ida2a2(+) \n");
	sb.append("  AND B.ida3f9 = G.ida2a2(+) \n");
	sb.append("  AND B.ida3b9 = H.ida2a2(+) \n");
	sb.append("  AND B.ida3G9 = I.ida2a2(+) \n");
	sb.append("  AND B.LASTEST = 1 \n");
	sb.append("  AND B.checkoutstate != 'c/o' \n");
	sb.append("ORDER BY A.pjtno \n");

	try {
	    pstmt = plmConn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		projectHash = new Hashtable<String, String>();
		projectHash.put("ida2a2", StringUtil.checkNull(rs.getString("ida2a2")) + StringUtil.checkNull(rs.getString("pnum")));
		projectHash.put("pjtno", StringUtil.checkNull(rs.getString("pjtno")));
		projectHash.put("pjtname", StringUtil.checkNull(rs.getString("pjtname")));
		projectHash.put("pnum", StringUtil.checkNull(rs.getString("pnum")));
		projectHash.put("pname", StringUtil.checkNull(rs.getString("pname")));
		projectHash.put("cartype_code", StringUtil.checkNull(rs.getString("cartype_code")));
		projectHash.put("cartype_name", StringUtil.checkNull(rs.getString("cartype_name")));
		projectHash.put("producttype_code", StringUtil.checkNull(rs.getString("producttype_code")));
		projectHash.put("producttype_name", StringUtil.checkNull(rs.getString("producttype_name")));
		projectHash.put("dept_code", StringUtil.checkNull(rs.getString("dept_code")));
		projectHash.put("dept_name", StringUtil.checkNull(rs.getString("dept_name")));
		projectHash.put("account_no", StringUtil.checkNull(rs.getString("account_no")));
		projectHash.put("pm_name", StringUtil.checkNull(rs.getString("pm_name")));
		projectHash.put("developenttype_code", StringUtil.checkNull(rs.getString("developenttype_code")));
		projectHash.put("developenttype_name", StringUtil.checkNull(rs.getString("developenttype_name")));
		projectHash.put("statestate", StringUtil.checkNull(rs.getString("statestate")));
		projectHash.put("planenddate", StringUtil.checkNull(rs.getString("planenddate")));
		projectHash.put("process_code", StringUtil.checkNull(rs.getString("process_code")));
		projectHash.put("process_name", StringUtil.checkNull(rs.getString("process_name")));
		projectHash.put("mold_count", StringUtil.checkNull(rs.getString("mold_count")));
		projectHash.put("mold_state", StringUtil.checkNull(rs.getString("mold_state")));
		projectHash.put("press_count", StringUtil.checkNull(rs.getString("press_count")));
		projectHash.put("press_state", StringUtil.checkNull(rs.getString("press_state")));
		projectHash.put("oem1plan", StringUtil.checkNull(rs.getString("oem1plan")));
		projectHash.put("oem2plan", StringUtil.checkNull(rs.getString("oem2plan")));
		projectHash.put("oemSOPplan", StringUtil.checkNull(rs.getString("oemSOPplan")));
		projectHash.put("gate4date", StringUtil.checkNull(rs.getString("gate4date")));
		projectHash.put("systemenddate", StringUtil.checkNull(rs.getString("systemenddate")));
		projectHash.put("rolemember_code", StringUtil.checkNull(rs.getString("rolemember_code")));
		projectHash.put("rolemember_name", StringUtil.checkNull(rs.getString("rolemember_name")));
		projectHash.put("rank", StringUtil.checkNull(rs.getString("rank")));
		projectHash.put("subcontractor_code", StringUtil.checkNull(rs.getString("subcontractor_code")));
		projectHash.put("subcontractor_name", StringUtil.checkNull(rs.getString("subcontractor_name")));
		projectHash.put("designtype_code", StringUtil.checkNull(rs.getString("designtype_code")));
		projectHash.put("designtype_name", StringUtil.checkNull(rs.getString("designtype_name")));
		projectHash.put("developpattern_code", StringUtil.checkNull(rs.getString("developpattern_code")));
		projectHash.put("develppattern_name", StringUtil.checkNull(rs.getString("develppattern_name")));
		projectHash.put("execstartdate", StringUtil.checkNull(rs.getString("execstartdate")));
		projectHash.put("execenddate", StringUtil.checkNull(rs.getString("execenddate")));
		projectHash.put("taskexecenddate", StringUtil.checkNull(rs.getString("taskexecenddate")));
		projectHash.put("createdate", StringUtil.checkNull(rs.getString("createdate")));
		projectHash.put("updatedate", StringUtil.checkNull(rs.getString("updatedate")));
		projectList.add(projectHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();

	    throw new Exception("PLM 프로젝트 Data Load 오류 발생");
	}

	return projectList;
    }

    public int setInsertPLMProject(Connection qmsConn, ArrayList projectList, PreparedStatement pstmt, StringBuffer sb) throws Exception {
	ArrayList returnItemList = new ArrayList();

	Hashtable projectItem = null;
	sb = null;
	sb = new StringBuffer();
	int complet = 0;
	sb.append(" INSERT INTO TBL_INF_PLM_PJT(");
	sb.append("ida2a2, \n");
	sb.append("pjt_no, \n");
	sb.append("pjt_name, \n");
	sb.append("pnum, \n");
	sb.append("pname, \n");
	sb.append("cartype_code, \n");
	sb.append("cartype_name, \n");
	sb.append("producttype_code, \n");
	sb.append("producttype_name, \n");
	sb.append("dept_code, \n");
	sb.append("dept_name, \n");
	sb.append("account_no, \n");
	sb.append("pm_name, \n");
	sb.append("developenttype_code, \n");
	sb.append("developenttype_name, \n");
	sb.append("statestate, \n");
	// // sb.append("statename, \n");
	sb.append("planenddate, \n");
	sb.append("process_code, \n");
	sb.append("process_name, \n");
	sb.append("mold_count, \n");
	sb.append("mold_state, \n");
	sb.append("press_count, \n");
	sb.append("press_state, \n");
	sb.append("oem1plan, \n");
	sb.append("oem2plan, \n");
	sb.append("oemSOPplan, \n");
	sb.append("gate4date, \n");
	sb.append("systemenddate, \n");
	// sb.append("productiondept_name, \n");
	sb.append("rolemember_code, \n");
	sb.append("rolemember_name, \n");
	sb.append("rank, \n");
	sb.append("subcontractor_code, \n");
	sb.append("subcontractor_name, \n");
	sb.append("designtype_code, \n");
	sb.append("designtype_name, \n");
	sb.append("developpattern_code, \n");
	sb.append("develppattern_name, \n");
	sb.append("execstartdate, \n");
	sb.append("execenddate, \n");
	sb.append("taskexecenddate \n");
	sb.append(") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	String pjtNo = "";
	try {
	    Kogger.debug("TBL_INF_PLM_PJT insert Start !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    for (int i = 0; i < projectList.size(); i++) {
		projectItem = (Hashtable) projectList.get(i);

		pjtNo = (String) projectItem.get("pjtno");

		pstmt = qmsConn.prepareStatement(sb.toString());
		pstmt.setString(1, (String) projectItem.get("ida2a2"));
		pstmt.setString(2, (String) projectItem.get("pjtno"));
		pstmt.setString(3, (String) projectItem.get("pjtname"));
		pstmt.setString(4, (String) projectItem.get("pnum"));
		pstmt.setString(5, (String) projectItem.get("pname"));
		pstmt.setString(6, (String) projectItem.get("cartype_code"));
		pstmt.setString(7, (String) projectItem.get("cartype_name"));
		pstmt.setString(8, (String) projectItem.get("producttype_code"));
		pstmt.setString(9, (String) projectItem.get("producttype_name"));
		pstmt.setString(10, (String) projectItem.get("dept_code"));
		pstmt.setString(11, (String) projectItem.get("dept_name"));
		pstmt.setString(12, (String) projectItem.get("account_no"));
		pstmt.setString(13, (String) projectItem.get("pm_name"));
		pstmt.setString(14, (String) projectItem.get("developenttype_code"));
		pstmt.setString(15, (String) projectItem.get("developenttype_name"));
		pstmt.setString(16, (String) projectItem.get("statestate"));
		pstmt.setString(17, (String) projectItem.get("planenddate"));
		pstmt.setString(18, (String) projectItem.get("process_code"));
		pstmt.setString(19, (String) projectItem.get("process_name"));
		pstmt.setString(20, (String) projectItem.get("mold_count"));
		pstmt.setString(21, (String) projectItem.get("mold_state"));
		pstmt.setString(22, (String) projectItem.get("press_count"));
		pstmt.setString(23, (String) projectItem.get("press_state"));
		pstmt.setString(24, (String) projectItem.get("oem1plan"));
		pstmt.setString(25, (String) projectItem.get("oem2plan"));
		pstmt.setString(26, (String) projectItem.get("oemSOPplan"));
		pstmt.setString(27, (String) projectItem.get("gate4date"));
		pstmt.setString(28, (String) projectItem.get("systemenddate"));
		pstmt.setString(29, (String) projectItem.get("rolemember_code"));
		pstmt.setString(30, (String) projectItem.get("rolemember_name"));
		pstmt.setString(31, (String) projectItem.get("rank"));
		pstmt.setString(32, (String) projectItem.get("subcontractor_code"));
		pstmt.setString(33, (String) projectItem.get("subcontractor_name"));
		pstmt.setString(34, (String) projectItem.get("designtype_code"));
		pstmt.setString(35, (String) projectItem.get("designtype_name"));
		pstmt.setString(36, (String) projectItem.get("developpattern_code"));
		pstmt.setString(37, (String) projectItem.get("develppattern_name"));
		pstmt.setString(38, (String) projectItem.get("execstartdate"));
		pstmt.setString(39, (String) projectItem.get("execenddate"));
		pstmt.setString(40, (String) projectItem.get("taskexecenddate"));
		complet += pstmt.executeUpdate();
	    }
	    System.out.println("TBL_INF_PLM_PJT insert Completed !!");
	    Kogger.debug("TBL_INF_PLM_PJT insert Completed !!!!!!!!!!!!!!!");

	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("QMS 프로젝트 Data insert 오류 발생 PjtNo = " + pjtNo);
	    System.out.println(sb.toString());
	    throw new Exception("QMS 프로젝트 Data insert 오류 발생 PjtNo = " + pjtNo);
	    // conn.close();
	}
	return complet;
    }

    public static void log(String msg) {
	try {
	    LogToFile logger = new LogToFile(logFile, true);
	    logger.log(msg);
	} catch (IOException e) {
	    Kogger.error(QmsDataLoader.class, e);
	}
    }

}
