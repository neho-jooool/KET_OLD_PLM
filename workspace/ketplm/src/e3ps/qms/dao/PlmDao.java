package e3ps.qms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import e3ps.common.util.CommonUtil;
import e3ps.cost.util.StringUtil;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETProdChangeOrder;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.shared.log.Kogger;

public class PlmDao {

    private Connection conn;

    public PlmDao(Connection conn) {
	this.conn = conn;
    }

    public PlmDao() {
	super();
    }

    /**
     * 함수명 : getPlmProjectList 설명 : PLM의 Project정보를 가져온다
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 06. 04.
     */
    public ArrayList getPlmProjectList() throws Exception {
	ArrayList<Hashtable<String, String>> projectList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> projectHash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

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
	sb.append("  (SELECT md.code FROM projectmemberlink ml, wtuser mu, people mp, department md WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT01' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4 AND mp.ida3c4 = md.ida2a2 and rownum = 1) as dept_code, \n"); // 개발부서코드
	sb.append("  (SELECT md.name FROM projectmemberlink ml, wtuser mu, people mp, department md WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT01' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4 AND mp.ida3c4 = md.ida2a2 and rownum = 1) as dept_name, \n"); // 개발부서명
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
	sb.append("  (SELECT mp.accountno FROM projectmemberlink ml, wtuser mu, people mp WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT15' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4 and rownum = 1) AS rolemember_code, \n"); // 선행품질담당사번
	// 생산처명
	sb.append("  (SELECT mp.name FROM projectmemberlink ml, wtuser mu, people mp WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT15' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4 and rownum = 1) AS rolemember_name, \n"); // 선행품질담당명
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
	    pstmt = conn.prepareStatement(sb.toString());
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
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return projectList;
    }

    /**
     * 함수명 : getPlmECOList 설명 : PLM의 ECO정보를 가져온다
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 06. 11.
     */
    public ArrayList getPlmEcoPjtList(String attrEcoOid) throws Exception {
	ArrayList<Hashtable<String, String>> ecoList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> ecoHash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	String ecoOid = "";
	String ecrOid = "";
	String[] ecrOidArr = null;
	String ecrText = "";
	String checkEcrText = "";
	String checkEcoText = "";

	sb.append("SELECT \n");
	sb.append("BB.ida2a2, \n");
	sb.append("BB.pjtno, \n");
	sb.append("BB.pjtname, \n");
	sb.append("BB.statestate, \n");
	sb.append("L.WTPARTNUMBER AS pnum, \n");
	sb.append("L.NAME AS pname, \n");
	sb.append("BB.cartype_code, \n");
	sb.append("BB.cartype_name, \n");
	sb.append("AH.approvedate, \n");
	sb.append("(SELECT c.ERPPRODCODE FROM WTPARTMASTER m, KETPartClassLink pclink, KETPartClassification c  WHERE m.wtpartnumber = L.WTPARTNUMBER AND m.ida2a2 = pclink.ida3b5 AND c.ida2a2 = pclink.ida3a5) AS producttype_code, \n");
	sb.append("BB.producttype_name||'/'||(SELECT c.CLASSKRNAME FROM WTPARTMASTER m, KETPartClassLink pclink, KETPartClassification c  WHERE m.wtpartnumber = L.WTPARTNUMBER AND m.ida2a2 = pclink.ida3b5 AND c.ida2a2 = pclink.ida3a5) AS producttype_name,  \n");
	sb.append("E.ecoid, \n");
	sb.append("E.classnamea2a2||':'||E.ida2a2 as ecoOid,\n");
	sb.append("(SELECT listagg(I.classnamea2a2||':'||I.ida2a2, ',') WITHIN GROUP (ORDER BY I.ida2a2) FROM KETProdChangeLink F, KETProdChangeRequest H, KETChangeRequestExpansion I WHERE E.ida2a2 = F.ida3a5(+) AND F.ida3b5 = H.ida2a2(+) AND H.ida2a2 = I.ida3a8(+) GROUP BY E.ida2a2) as ecrOid, \n");
	sb.append("(SELECT dp.code FROM department dp WHERE E.deptname = dp.name) AS dept_code,\n");
	sb.append("E.deptname AS dept_name, \n");
	sb.append("(SELECT p.accountno FROM wtuser u, people p WHERE u.ida2a2 = E.ida3b7 AND p.ida3b4 = u.ida2a2) AS creator_perno,\n");
	sb.append("(SELECT p.name FROM wtuser u, people p WHERE u.ida2a2 = E.ida3b7 AND p.ida3b4 = u.ida2a2) AS creator_name, \n");
	sb.append("BB.process_code, \n");
	sb.append("BB.process_name \n");
	sb.append("FROM \n");
	sb.append("    KETProdChangeOrder E,\n");
	sb.append("    KETProdECOPartLink J, \n");
	sb.append("    WTPART K, \n");
	sb.append("    WTPARTMASTER L, \n");
	sb.append("    KETWfmApprovalMaster AM, \n");
	sb.append("    (SELECT ida3a4, LAST, to_char(completeddate, 'YYYYMMDD') AS approvedate FROM KETWfmApprovalHistory WHERE decision='승인' AND LAST = 1) AH, \n");
	sb.append("    (SELECT \n");
	sb.append("     B.ida2a2, \n");
	sb.append("     B.classnamea2a2||':'||B.ida2a2 AS pjtid, \n");
	sb.append("     A.pjtno, \n"); // 프로젝트번호
	sb.append("     A.pjtname, \n"); // 프로젝트명
	sb.append("     B.statestate as statestate, \n"); // 상태코드
	sb.append("     C.code AS cartype_code, \n"); // 차종코드
	sb.append("     C.name AS cartype_name, \n"); // 차종명
	sb.append("     (SELECT classkrname||'/' FROM KETPartClassification kpc WHERE D.ida3a3 = kpc.ida2a2) ||D.classkrname AS producttype_name, \n"); // 제품군명
	sb.append("     G.code as process_code, \n"); // 개발단계코드
	sb.append("     G.name as process_name \n"); // 개발단계
	sb.append("     FROM E3PSPROJECTMASTER A, \n");
	sb.append("     PRODUCTPROJECT B, \n");
	sb.append("     OEMProjectType C, \n");
	sb.append("     KETPartClassification D, \n");
	sb.append("     numbercode G \n");
	sb.append("     WHERE 1=1 \n");
	sb.append("     AND A.ida2a2 = B.ida3b8 \n");
	sb.append("     AND A.ida2a2 = B.ida3b8 \n");
	sb.append("     AND B.ida3d8 = C.ida2a2(+) \n");
	sb.append("     AND SUBSTR(B.productTypeLevel2, INSTR(B.productTypeLevel2, ':')+1, LENGTH(B.productTypeLevel2)) = D.ida2a2(+) \n");
	sb.append("     AND B.ida3f9 = G.ida2a2(+) \n");
	sb.append("     AND B.LASTEST = 1 \n");
	sb.append("     AND B.checkoutstate != 'c/o') BB \n");
	sb.append("WHERE 1=1 \n");
	// sb.append("AND E.statestate = 'APPROVED' \n");
	sb.append("AND E.projectoid = BB.pjtid(+) \n");
	sb.append("AND E.ida2a2 = J.ida3b5 \n");
	sb.append("AND J.ida3a5 = K.ida2a2 \n");
	sb.append("AND K.IDA3MASTERREFERENCE = L.ida2a2 \n");
	sb.append("AND E.ida2a2 = AM.ida3b4(+) \n");
	sb.append("AND AM.ida2a2 = AH.ida3a4(+) \n");
	sb.append("AND LENGTH(REPLACE(REPLACE(E.changereason, 'REASON_10', ''), 'REASON_12', '')) > 0 \n");
	if (!"".equals(attrEcoOid)) {
	    // sb.append("AND E.classnamea2a2||':'||E.ida2a2 = ? \n");
	    sb.append("AND E.ida2a2 = ? \n");
	}
	sb.append("ORDER BY E.ecoid DESC \n");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    if (!"".equals(attrEcoOid)) {
		pstmt.setString(1, attrEcoOid);
	    }
	    // e3ps.ecm.entity.KETChangeRequestExpansion:
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ecoHash = new Hashtable<String, String>();
		ecrOidArr = StringUtil.checkNull(rs.getString("ecrOid")).split(",");
		// ecrOid = StringUtil.checkNull(rs.getString("ecrOid"));
		ecoOid = StringUtil.checkNull(rs.getString("ecoOid"));

		if (!"".equals(StringUtil.checkNull(rs.getString("ecrOid")).replaceAll(":", "").replaceAll(",", ""))) {
		    for (int i = 0; i < ecrOidArr.length; i++) {
			ecrOid = StringUtil.checkNull(ecrOidArr[i].toString());
			KETChangeRequestExpansion prodRequest = (KETChangeRequestExpansion) CommonUtil.getObject(ecrOid);
			if (i == (ecrOidArr.length - 1)) {
			    ecrText = ecrText + StringUtil.checkNull(StringUtils.stripToEmpty((String) prodRequest.getWebEditorText()));
			} else {
			    ecrText = ecrText + StringUtil.checkNull(StringUtils.stripToEmpty((String) prodRequest.getWebEditorText())) + ", ";
			}
		    }
		    ecoHash.put("ecr_webeditortext", ecrText);
		    checkEcrText = ecrText;
		    ecrText = "";
		} else {
		    if (!"".equals(ecoOid.replaceAll(":", ""))) {
			KETProdChangeOrder prodOrder = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);
			ecoHash.put("ecr_webeditortext", StringUtils.stripToEmpty((String) prodOrder.getWebEditorText()));
			checkEcrText = StringUtils.stripToEmpty((String) prodOrder.getWebEditorText());
		    } else {
			ecoHash.put("ecr_webeditortext", "");
			checkEcrText = "";
		    }
		}
		System.out.println("by hooni 1::::::::::::::::::::: " + rs.getString("ecoid") + " ::: " + ecrText.length());
		Kogger.debug("by hooni 1::::::::::::::::::::: " + rs.getString("ecoid") + " ::: " + ecrText.length());

		if (!"".equals(ecoOid.replaceAll(":", ""))) {
		    KETProdChangeOrder prodOrder = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);
		    ecoHash.put("eco_after_webeditortext", StringUtils.stripToEmpty((String) prodOrder.getWebEditorText1()));
		    checkEcoText = StringUtils.stripToEmpty((String) prodOrder.getWebEditorText1());
		} else {
		    ecoHash.put("eco_after_webeditortext", "");
		    checkEcoText = "";
		}
		System.out.println("by hooni 2::::::::::::::::::::: " + rs.getString("ecoid") + " ::: " + checkEcoText.length());
		Kogger.debug("by hooni 2::::::::::::::::::::: " + rs.getString("ecoid") + " ::: " + checkEcoText.length());

		ecoHash.put("ida2a2", StringUtil.checkNull(rs.getString("ida2a2")));
		ecoHash.put("pjtno", StringUtil.checkNull(rs.getString("pjtno")));
		ecoHash.put("pjtname", StringUtil.checkNull(rs.getString("pjtname")));
		ecoHash.put("statestate", StringUtil.checkNull(rs.getString("statestate")));
		ecoHash.put("pnum", StringUtil.checkNull(rs.getString("pnum")));
		ecoHash.put("pname", StringUtil.checkNull(rs.getString("pname")));
		ecoHash.put("cartype_code", StringUtil.checkNull(rs.getString("cartype_code")));
		ecoHash.put("cartype_name", StringUtil.checkNull(rs.getString("cartype_name")));
		ecoHash.put("approvedate", StringUtil.checkNull(rs.getString("approvedate")));
		ecoHash.put("producttype_code", StringUtil.checkNull(rs.getString("producttype_code")));
		ecoHash.put("producttype_name", StringUtil.checkNull(rs.getString("producttype_name")));
		ecoHash.put("ecoid", StringUtil.checkNull(rs.getString("ecoid")));
		ecoHash.put("dept_code", StringUtil.checkNull(rs.getString("dept_code")));
		ecoHash.put("dept_name", StringUtil.checkNull(rs.getString("dept_name")));
		ecoHash.put("creator_perno", StringUtil.checkNull(rs.getString("creator_perno")));
		ecoHash.put("creator_name", StringUtil.checkNull(rs.getString("creator_name")));
		ecoHash.put("process_code", StringUtil.checkNull(rs.getString("process_code")));
		ecoHash.put("process_name", StringUtil.checkNull(rs.getString("process_name")));

		if (checkEcrText.length() > 0 || checkEcoText.length() > 0) {
		    ecoList.add(ecoHash);
		}
		checkEcrText = "";
		checkEcoText = "";
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    // if (pstmt != null) {
	    // pstmt.close();
	    // }
	    // if (conn != null) {
	    // conn.close();
	    // }
	}
	return ecoList;
    }

    /**
     * 함수명 : getPlmDqmList 설명 : PLM의 품질문제정보를 가져온다
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 06. 11.
     */
    public ArrayList getPlmDqmList(String attrDqmOid) throws Exception {
	ArrayList<Hashtable<String, String>> dqmList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> dqmHash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	String raiOid = "";
	String actOid = "";

	sb.append("SELECT \n");
	sb.append("B.ida2a2, \n");
	sb.append("A.pjtno, \n"); // 프로젝트번호
	sb.append("A.pjtname, \n"); // 프로젝트명
	sb.append("B.statestate as statestate, \n"); // 상태코드
	sb.append("K.WTPARTNUMBER AS pnum, \n"); // 부품코드
	sb.append("K.name AS pname, \n"); // 부품명
	sb.append("C.code AS cartype_code, \n"); // 차종코드
	sb.append("C.name AS cartype_name, \n"); // 차종명
	sb.append("to_char(I.reviewdate, 'YYYYMMDD') AS dqm_reviewdate, \n"); // 개발품질종결일
	sb.append("(SELECT c.ERPPRODCODE FROM WTPARTMASTER m, KETPartClassLink pclink, KETPartClassification c  WHERE m.wtpartnumber = K.WTPARTNUMBER AND m.ida2a2 = pclink.ida3b5 AND c.ida2a2 = pclink.ida3a5) AS producttype_code, \n"); // 제품군코드
	sb.append("(SELECT classkrname||'/' FROM KETPartClassification kpc WHERE D.ida3a3 = kpc.ida2a2) ||D.classkrname||(SELECT '/'||c.CLASSKRNAME FROM WTPARTMASTER m, KETPartClassLink pclink, KETPartClassification c  WHERE m.wtpartnumber = K.WTPARTNUMBER AND m.ida2a2 = pclink.ida3b5 AND c.ida2a2 = pclink.ida3a5) AS producttype_name, \n"); // 제품군명
	sb.append("F.problemno AS problem_no, \n"); // 품질문제번호
	sb.append(" E.classnamea2a2||':'||E.ida2a2 as raiOid, \n"); // raiOid
	sb.append(" H.classnamea2a2||':'||H.ida2a2 as actOid, \n"); // actOid
	sb.append("E.webeditortext AS problem_webeditortext, \n"); // 문제내용
	sb.append("H.improveWebEditorText AS reform_webeditortext, \n"); // 개선대책
	sb.append("(SELECT dp.code FROM WTUser wu, people pp, department dp WHERE wu.ida2a2 = E.ida3a7 AND pp.ida3b4 = wu.ida2a2 AND pp.ida3c4 = dp.ida2a2) AS dept_code, \n"); // 작성부서코드
	sb.append("(SELECT dp.name FROM WTUser wu, people pp, department dp WHERE wu.ida2a2 = E.ida3a7 AND pp.ida3b4 = wu.ida2a2 AND pp.ida3c4 = dp.ida2a2) AS dept_name, \n"); // 작성부서명
	sb.append("(SELECT pp.accountno FROM WTUser wu, people pp WHERE wu.ida2a2 = H.ida3a8 AND pp.ida3b4 = wu.ida2a2) AS actionuser_perno, \n"); // 검토자
	sb.append("(SELECT pp.name FROM WTUser wu, people pp WHERE wu.ida2a2 = H.ida3a8 AND pp.ida3b4 = wu.ida2a2) AS actionuser_name, \n"); // 검토자
	sb.append("G.code as process_code, \n"); // 개발단계코드
	sb.append("G.name as process_name \n"); // 개발단계
	sb.append("FROM E3PSPROJECTMASTER A,  \n");
	sb.append("     PRODUCTPROJECT B, \n");
	sb.append("     OEMProjectType C, \n");
	sb.append("     KETPartClassification D, \n");
	sb.append("     KETDqmRaise E, \n");
	sb.append("     KETDqmHeader F,    \n");
	sb.append("     numbercode G, \n");
	sb.append("     KETDqmAction H, \n");
	sb.append("     KETDqmClose I, \n");
	sb.append("     WTPART J, \n");
	sb.append("     WTPARTMASTER K \n");
	sb.append("WHERE 1=1 \n");
	sb.append("AND A.ida2a2 = B.ida3b8 \n");
	sb.append("AND B.ida3d8 = C.ida2a2(+) \n");
	sb.append("AND SUBSTR(B.productTypeLevel2, INSTR(B.productTypeLevel2, ':')+1, LENGTH(B.productTypeLevel2)) = D.ida2a2(+) \n");
	sb.append("AND F.dqmstatecode = 'END' \n");
	sb.append("AND F.ida3c3 = E.ida2a2 \n");
	sb.append("AND F.ida3b3 = H.ida2a2(+) \n");
	sb.append("AND B.ida2a2 = E.ida3d8 \n");
	sb.append("AND B.ida3f9 = G.ida2a2(+) \n");
	sb.append("AND F.ida3a3 = I.ida2a2(+) \n");
	sb.append("AND E.ida3b8 = J.ida2a2 \n");
	sb.append("AND K.ida2a2 = J.IDA3MASTERREFERENCE \n");
	sb.append("AND E.ISSUECODE = 'DQMIS8' \n");
	// sb.append("AND B.LASTEST = 1 \n");
	// sb.append("AND B.checkoutstate != 'c/o' \n");
	if (!"".equals(attrDqmOid)) {
	    // sb.append("AND F.classnamea2a2||':'||F.ida2a2 = ? \n");
	    sb.append("AND F.ida2a2 = ? \n");
	}

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    if (!"".equals(attrDqmOid)) {
		pstmt.setString(1, attrDqmOid);
	    }
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		dqmHash = new Hashtable<String, String>();
		raiOid = StringUtil.checkNull(rs.getString("raiOid"));
		actOid = StringUtil.checkNull(rs.getString("actOid"));

		if (!"".equals(raiOid.replaceAll(":", "")) && raiOid != null) {
		    KETDqmRaise Raise = (KETDqmRaise) CommonUtil.getObject(raiOid);
		    dqmHash.put("problem_webeditortext", StringUtils.stripToEmpty((String) Raise.getWebEditorText()));
		} else {
		    dqmHash.put("problem_webeditortext", "");
		}

		if (!"".equals(actOid.replaceAll(":", "")) && actOid != null) {
		    KETDqmAction action = (KETDqmAction) CommonUtil.getObject(actOid);
		    dqmHash.put("reform_webeditortext", StringUtils.stripToEmpty((String) action.getImproveWebEditorText()));
		} else {
		    dqmHash.put("reform_webeditortext", "");
		}

		dqmHash.put("ida2a2", StringUtil.checkNull(rs.getString("ida2a2")));
		dqmHash.put("pjtno", StringUtil.checkNull(rs.getString("pjtno")));
		dqmHash.put("pjtname", StringUtil.checkNull(rs.getString("pjtname")));
		dqmHash.put("statestate", StringUtil.checkNull(rs.getString("statestate")));
		dqmHash.put("pnum", StringUtil.checkNull(rs.getString("pnum")));
		dqmHash.put("pname", StringUtil.checkNull(rs.getString("pname")));
		dqmHash.put("cartype_code", StringUtil.checkNull(rs.getString("cartype_code")));
		dqmHash.put("cartype_name", StringUtil.checkNull(rs.getString("cartype_name")));
		dqmHash.put("dqm_reviewdate", StringUtil.checkNull(rs.getString("dqm_reviewdate")));
		dqmHash.put("producttype_code", StringUtil.checkNull(rs.getString("producttype_code")));
		dqmHash.put("producttype_name", StringUtil.checkNull(rs.getString("producttype_name")));
		dqmHash.put("problem_no", StringUtil.checkNull(rs.getString("problem_no")));
		dqmHash.put("dept_code", StringUtil.checkNull(rs.getString("dept_code")));
		dqmHash.put("dept_name", StringUtil.checkNull(rs.getString("dept_name")));
		dqmHash.put("actionuser_perno", StringUtil.checkNull(rs.getString("actionuser_perno")));
		dqmHash.put("actionuser_name", StringUtil.checkNull(rs.getString("actionuser_name")));
		dqmHash.put("process_code", StringUtil.checkNull(rs.getString("process_code")));
		dqmHash.put("process_name", StringUtil.checkNull(rs.getString("process_name")));
		dqmList.add(dqmHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return dqmList;
    }

    /**
     * 함수명 : getPlmReqList 설명 : PLM의 시험의뢰진행관리를 가져온다
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 06. 16.
     */
    public ArrayList getPlmReqList() throws Exception {
	ArrayList<Hashtable<String, String>> reqList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> reqHash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT \n");
	sb.append(" B.ida2a2, \n");
	sb.append(" A.pjtno, \n"); // 프로젝트번호
	sb.append(" A.pjtname, \n"); // 프로젝트명
	sb.append(" B.statestate as statestate, \n"); // 상태코드
	sb.append(" (SELECT listagg(tk.TaskName, ',') WITHIN GROUP (ORDER BY tk.TaskName) FROM E3PSTASK tk WHERE B.ida2a2 = tk.ida3b4 AND tk.TaskName Like '%신뢰성%') AS task_name, \n"); // Task명
	sb.append(" C.code AS cartype_code, \n"); // 차종코드
	sb.append(" C.name AS cartype_name, \n"); // 차종명
	sb.append(" D.ERPPRODCODE AS producttype_code, \n"); // 제품군코드
	sb.append(" (SELECT classkrname FROM KETPartClassification kpc WHERE D.ida3a3 = kpc.ida2a2) ||'/'||D.classkrname AS producttype_name, \n"); // 제품군명
	sb.append(" (SELECT md.code FROM projectmemberlink ml, wtuser mu, people mp, department md WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT01' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4 AND mp.ida3c4 = md.ida2a2) as dept_code, \n"); // 개발부서코드
	sb.append(" (SELECT md.name FROM projectmemberlink ml, wtuser mu, people mp, department md WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT01' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4 AND mp.ida3c4 = md.ida2a2) as dept_name, \n"); // 개발부서명
	sb.append(" pmp.accountno as pm_perno, \n"); // pm사번
	sb.append(" pmp.name as pm_name, \n"); // pm명
	sb.append(" 'Team_PRODUCT01' AS role_code, \n"); // role_code
	sb.append(" '제품개발' AS role_name, \n"); // role명
	sb.append(" (SELECT mp.accountno FROM projectmemberlink ml, wtuser mu, people mp WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT01' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4) AS rolemember_code, \n"); // 개발담당사번
	sb.append(" (SELECT mp.name FROM projectmemberlink ml, wtuser mu, people mp WHERE B.ida2a2 = ml.ida3a5 AND ml.PJTRole = 'Team_PRODUCT01' AND ml.IDA3B5 = mu.IDA2A2 AND mu.ida2a2 = mp.ida3b4) AS rolemember_name, \n"); // 개발담당명
	sb.append(" G.pnum, \n"); // 부품코드
	sb.append(" G.pname, \n"); // 부품명
	sb.append(" (SELECT listagg(K.code, ',') WITHIN GROUP (ORDER BY K.code) FROM ProjectSubcontractorLink J, NumberCode K WHERE B.ida2a2 = J.ida3b5(+) AND J.ida3A5 = K.ida2a2(+)) AS subcontractor_code, \n"); // 고객처코드
	sb.append(" (SELECT listagg(K.name, ',') WITHIN GROUP (ORDER BY K.code) FROM ProjectSubcontractorLink J, NumberCode K WHERE B.ida2a2 = J.ida3b5(+) AND J.ida3A5 = K.ida2a2(+)) AS subcontractor_name, \n"); // 고객처명
	sb.append(" (SELECT to_char(op.oemenddate, 'YYMMDD') FROM OEMProjectType oempt, ModelPlan mp, OEMPlan op, OEMProjectType ceoempt WHERE B.ida3d8 = oempt.ida2a2 AND oempt.ida2a2 = mp.ida3b3 AND mp.ida2a2 = op.ida3b3 AND op.ida3a3 = ceoempt.ida2a2 AND ceoempt.ida2a2 = 27437) AS oem1plan, \n"); // p1일자
	sb.append(" (SELECT to_char(op.oemenddate, 'YYMMDD') FROM OEMProjectType oempt, ModelPlan mp, OEMPlan op, OEMProjectType ceoempt WHERE B.ida3d8 = oempt.ida2a2 AND oempt.ida2a2 = mp.ida3b3 AND mp.ida2a2 = op.ida3b3 AND op.ida3a3 = ceoempt.ida2a2 AND ceoempt.ida2a2 = 27438) AS oem2plan, \n"); // p2일자
	sb.append(" (SELECT to_char(op.oemenddate, 'YYMMDD') FROM OEMProjectType oempt, ModelPlan mp, OEMPlan op, OEMProjectType ceoempt WHERE B.ida3d8 = oempt.ida2a2 AND oempt.ida2a2 = mp.ida3b3 AND mp.ida2a2 = op.ida3b3 AND op.ida3a3 = ceoempt.ida2a2 AND ceoempt.ida2a2 = 27441) AS oemSOPplan, \n"); // sop일자
	sb.append(" (SELECT to_char(es.planstartdate, 'YYMMDD') FROM e3pstask task, ExtendScheduleData es WHERE REPLACE(task.taskname, ' ', '') = 'GATE4' AND task.ida3a4 = es.ida2a2 AND task.ida3b4 = B.ida2a2) AS gate4date, \n"); // gate4일자
	sb.append(" to_char(F.planenddate, 'YYMMDD') as planstartdate, \n"); // 계획완료일
	sb.append(" to_char(F.planenddate, 'YYMMDD') as planenddate \n"); // 계획완료일
	sb.append(" FROM E3PSPROJECTMASTER A, \n");
	sb.append("      PRODUCTPROJECT B, \n");
	sb.append("      OEMProjectType C, \n");
	sb.append("      KETPartClassification D, \n");
	sb.append("      wtuser pmu, \n");
	sb.append("      people pmp, \n");
	sb.append("      ExtendScheduleData F, \n");
	sb.append("      PRODUCTINFO G \n");
	sb.append(" WHERE 1=1 \n");
	sb.append(" AND A.ida2a2 = B.ida3b8 \n");
	sb.append(" AND B.ida2a2 = G.ida3a3(+) \n");
	sb.append(" AND B.ida3d8 = C.ida2a2(+) \n");
	sb.append(" AND substr(B.productTypeLevel2, instr(B.productTypeLevel2, ':')+1, length(B.productTypeLevel2)) = D.ida2a2(+) \n");
	sb.append(" AND B.ida3f8 = pmu.ida2a2(+) \n");
	sb.append(" AND pmu.ida2a2 = pmp.ida3b4(+) \n");
	sb.append(" AND B.ida3a8 = F.ida2a2(+) \n");
	sb.append(" AND B.LASTEST = 1 \n");
	sb.append(" AND B.checkoutstate != 'c/o' \n");
	sb.append(" ORDER BY B.ida2a2 \n");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		reqHash = new Hashtable<String, String>();
		reqHash.put("ida2a2", StringUtil.checkNull(rs.getString("ida2a2")) + StringUtil.checkNull(rs.getString("pnum")));
		reqHash.put("pjtno", StringUtil.checkNull(rs.getString("pjtno")));
		reqHash.put("pjtname", StringUtil.checkNull(rs.getString("pjtname")));
		reqHash.put("statestate", StringUtil.checkNull(rs.getString("statestate")));
		reqHash.put("task_name", StringUtil.checkNull(rs.getString("task_name")));
		reqHash.put("cartype_code", StringUtil.checkNull(rs.getString("cartype_code")));
		reqHash.put("cartype_name", StringUtil.checkNull(rs.getString("cartype_name")));
		reqHash.put("producttype_code", StringUtil.checkNull(rs.getString("producttype_code")));
		reqHash.put("producttype_name", StringUtil.checkNull(rs.getString("producttype_name")));
		reqHash.put("dept_code", StringUtil.checkNull(rs.getString("dept_code")));
		reqHash.put("dept_name", StringUtil.checkNull(rs.getString("dept_name")));
		reqHash.put("pm_perno", StringUtil.checkNull(rs.getString("pm_perno")));
		reqHash.put("pm_name", StringUtil.checkNull(rs.getString("pm_name")));
		reqHash.put("role_code", StringUtil.checkNull(rs.getString("role_code")));
		reqHash.put("role_name", StringUtil.checkNull(rs.getString("role_name")));
		reqHash.put("rolemember_code", StringUtil.checkNull(rs.getString("rolemember_code")));
		reqHash.put("rolemember_name", StringUtil.checkNull(rs.getString("rolemember_name")));
		reqHash.put("pnum", StringUtil.checkNull(rs.getString("pnum")));
		reqHash.put("pname", StringUtil.checkNull(rs.getString("pname")));
		reqHash.put("subcontractor_code", StringUtil.checkNull(rs.getString("subcontractor_code")));
		reqHash.put("subcontractor_name", StringUtil.checkNull(rs.getString("subcontractor_name")));
		reqHash.put("oem1plan", StringUtil.checkNull(rs.getString("oem1plan")));
		reqHash.put("oem2plan", StringUtil.checkNull(rs.getString("oem2plan")));
		reqHash.put("oemSOPplan", StringUtil.checkNull(rs.getString("oemSOPplan")));
		reqHash.put("gate4date", StringUtil.checkNull(rs.getString("gate4date")));
		reqHash.put("planstartdate", StringUtil.checkNull(rs.getString("planstartdate")));
		reqHash.put("planenddate", StringUtil.checkNull(rs.getString("planenddate")));
		reqList.add(reqHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return reqList;
    }

    /**
     * 함수명 : getPlmDocumentList 설명 : PLM의 문서를 가져온다
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 06. 18.
     */
    public ArrayList getPlmDocumentList() throws Exception {
	ArrayList<Hashtable<String, String>> documentList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> documentHash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT A.attribute1 AS qms_no, A.documentno AS document_no, A.TITLE AS DOCUMENT_NAME, MAX(A.versionida2versioninfo) AS version_info, MAX(to_char(A.createstampa2, 'YYMMDD')) AS approvedate \n");
	sb.append("FROM KETProjectDocument A, \n");
	sb.append("KETDocumentCategoryLink B, \n");
	sb.append("KETDocumentCategory C \n");
	sb.append("WHERE 1=1 \n");
	sb.append("AND A.ida2a2 = B.ida3b5 \n");
	sb.append("AND C.ida2a2 = B.ida3a5 \n");
	sb.append("AND C.categorycode in ('PD303066', 'PD303072', 'PD303001', 'PD303017', 'PD303034', 'PD303046', 'PD303047') \n");
	sb.append("AND A.DOCUMENTNO != 'PF-656816-5 ' \n");
	sb.append("GROUP BY A.documentno, C.categorycode,  A.attribute1, A.title \n");
	sb.append("ORDER BY A.documentno DESC \n");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		documentHash = new Hashtable<String, String>();
		documentHash.put("qms_no", StringUtil.checkNull(rs.getString("qms_no")));
		documentHash.put("document_no", StringUtil.checkNull(rs.getString("document_no")));
		documentHash.put("document_name", StringUtil.checkNull(rs.getString("document_name")));
		documentHash.put("version_info", StringUtil.checkNull(rs.getString("version_info")));
		documentHash.put("approvedate", StringUtil.checkNull(rs.getString("approvedate")));
		documentList.add(documentHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return documentList;
    }

    /**
     * 함수명 : getPlmEcoList 설명 : PLM의 eco를 가져온다
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 06. 18.
     */
    public ArrayList getPlmEcoList() throws Exception {
	ArrayList<Hashtable<String, String>> ecoList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> ecoHash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT \n");
	sb.append("E.ecoid, \n");
	sb.append("E.econame, \n");
	sb.append("AA.pjtno, \n");
	sb.append("L.WTPARTNUMBER AS pnum, \n");
	sb.append("L.NAME AS pname, \n");
	sb.append("to_char(E.createstampa2, 'YYYYMMDD') AS create_date \n");
	sb.append("FROM \n");
	sb.append("KETProdChangeOrder E, \n");
	sb.append("KETProdECOPartLink J, \n");
	sb.append("WTPART K, \n");
	sb.append("WTPARTMASTER L, \n");
	sb.append("(SELECT \n");
	sb.append("    A.pjtno, \n");
	sb.append("    B.classnamea2a2||':'||B.ida2a2 AS bid \n");
	sb.append("    FROM E3PSPROJECTMASTER A, \n");
	sb.append("    PRODUCTPROJECT B \n");
	sb.append("    WHERE 1=1 \n");
	sb.append("    AND A.ida2a2 = B.ida3b8 \n");
	sb.append("    AND B.LASTEST = 1) AA \n");
	sb.append("WHERE 1=1 \n");
	sb.append("AND E.ida2a2 = J.ida3b5 \n");
	sb.append("AND J.ida3a5 = K.ida2a2 \n");
	sb.append("AND K.IDA3MASTERREFERENCE = L.ida2a2 \n");
	sb.append("AND E.projectoid = AA.bid(+) \n");
	sb.append("order by ecoid  \n");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ecoHash = new Hashtable<String, String>();
		ecoHash.put("ecoid", StringUtil.checkNull(rs.getString("ecoid")));
		ecoHash.put("econame", StringUtil.checkNull(rs.getString("econame")));
		ecoHash.put("pjtno", StringUtil.checkNull(rs.getString("pjtno")));
		ecoHash.put("pnum", StringUtil.checkNull(rs.getString("pnum")));
		ecoHash.put("pname", StringUtil.checkNull(rs.getString("pname")));
		ecoHash.put("create_date", StringUtil.checkNull(rs.getString("create_date")));
		ecoList.add(ecoHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return ecoList;
    }

    /**
     * 함수명 : getPlmEcrList 설명 : PLM의 ecr를 가져온다
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 06. 18.
     */
    public ArrayList getPlmEcrList() throws Exception {
	ArrayList<Hashtable<String, String>> ecrList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> ecrHash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT  \n");
	sb.append("E.ecrid,  \n");
	sb.append("E.ecrname,  \n");
	sb.append("AA.pjtno,  \n");
	sb.append("L.WTPARTNUMBER AS pnum, \n");
	sb.append("L.NAME AS pname, \n");
	sb.append("to_char(E.createstampa2, 'YYYYMMDD') AS create_date  \n");
	sb.append("FROM  \n");
	sb.append("KETProdChangeRequest E,  \n");
	sb.append("KETProdECRPartLink J, \n");
	sb.append("WTPART K, \n");
	sb.append("WTPARTMASTER L, \n");
	sb.append("(SELECT  \n");
	sb.append("    A.pjtno,  \n");
	sb.append("    B.classnamea2a2||':'||B.ida2a2 AS bid \n");
	sb.append("    FROM E3PSPROJECTMASTER A,  \n");
	sb.append("    PRODUCTPROJECT B  \n");
	sb.append("    WHERE 1=1  \n");
	sb.append("    AND A.ida2a2 = B.ida3b8  \n");
	sb.append("    AND B.LASTEST = 1) AA  \n");
	sb.append("WHERE 1=1 \n");
	sb.append("AND E.ida2a2 = J.ida3b5 \n");
	sb.append("AND J.ida3a5 = K.ida2a2 \n");
	sb.append("AND K.IDA3MASTERREFERENCE = L.ida2a2 \n");
	sb.append("AND E.projectoid = AA.bid(+)  \n");
	sb.append("order by ecrid  \n");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ecrHash = new Hashtable<String, String>();
		ecrHash.put("ecrid", StringUtil.checkNull(rs.getString("ecrid")));
		ecrHash.put("ecrname", StringUtil.checkNull(rs.getString("ecrname")));
		ecrHash.put("pjtno", StringUtil.checkNull(rs.getString("pjtno")));
		ecrHash.put("pnum", StringUtil.checkNull(rs.getString("pnum")));
		ecrHash.put("pname", StringUtil.checkNull(rs.getString("pname")));
		ecrHash.put("create_date", StringUtil.checkNull(rs.getString("create_date")));
		ecrList.add(ecrHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return ecrList;
    }

    /**
     * 함수명 : getPlmOEMCarTypeList 설명 : PLM의 차종정보를 가져온다
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 07. 27.
     */
    public ArrayList getPlmOEMCarTypeList() throws Exception {
	ArrayList<Hashtable<String, String>> oemList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> oemHash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT code, name FROM OEMPROJECTTYPE WHERE oemtype = 'CARTYPE'");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		oemHash = new Hashtable<String, String>();
		oemHash.put("code", StringUtil.checkNull(rs.getString("code")));
		oemHash.put("name", StringUtil.checkNull(rs.getString("name")));
		oemList.add(oemHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return oemList;
    }

    /**
     * 함수명 : getThisPlmEcoList 설명 : PLM의 ECO정보를 가져온다
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 06. 11.
     */
    public ArrayList getThisDayPlmEcoList() throws Exception {
	ArrayList<Hashtable<String, String>> ecoList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> ecoHash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT \n");
	sb.append("E.ecoid, \n");
	sb.append("E.classnamea2a2||':'||E.ida2a2 as ecoOid, \n");
	sb.append("AM.COMPLETEDDATE \n");
	sb.append("FROM \n");
	sb.append("    KETProdChangeOrder E, \n");
	sb.append("    KETWfmApprovalMaster AM, \n");
	sb.append("    (SELECT ida3a4, LAST, to_char(completeddate, 'YYYYMMDD') AS approvedate FROM KETWfmApprovalHistory WHERE decision='승인' AND LAST = 1) AH \n");
	sb.append("WHERE 1=1 \n");
	sb.append("  AND E.statestate = 'APPROVED' \n");
	sb.append("  AND E.ida2a2 = AM.ida3b4(+) \n");
	sb.append("  AND AM.ida2a2 = AH.ida3a4(+) \n");
	sb.append("  AND LENGTH(REPLACE(REPLACE(E.changereason, 'REASON_10', ''), 'REASON_12', '')) > 0 \n");
	sb.append("  AND TO_CHAR(AM.COMPLETEDDATE, 'YYYYMMDD') = TO_CHAR(SYSDATE - 1 , 'YYYYMMDD') \n");
	sb.append("ORDER BY AM.COMPLETEDDATE desc, E.ecoid DESC  \n");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ecoHash = new Hashtable<String, String>();
		ecoHash.put("ecoid", StringUtil.checkNull(rs.getString("ecoid")));
		ecoHash.put("ecoOid", StringUtil.checkNull(rs.getString("ecoOid")));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    // if (pstmt != null) {
	    // pstmt.close();
	    // }
	    // if (conn != null) {
	    // conn.close();
	    // }
	}
	return ecoList;
    }
}
