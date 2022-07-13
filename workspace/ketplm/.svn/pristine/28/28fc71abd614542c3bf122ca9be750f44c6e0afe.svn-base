package e3ps.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.query.LoggableStatement;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import ext.ket.shared.log.Kogger;

public class IssueDao {

    private final Connection conn;

    public IssueDao(Connection conn) {
	this.conn = conn;
    }

    public List<Map<String, Object>> searchIssueGridList(List<Map<String, Object>> conditionList, TgPagingControl pager) throws Exception {

	List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	String query = oDaoManager.getOrderByQuery(this.getIssueListQuery(conditionList), pager);
	query = oDaoManager.getPagingQuery(query, pager);
	returnObjList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
	    @Override
	    public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		returnObj.put("issueOid", rs.getString("ISSUEOID"));
		returnObj.put("ida2a2", rs.getString("IDA2A2"));
		returnObj.put("taskOid", rs.getString("TASKOID"));
		returnObj.put("subject", rs.getString("SUBJECT"));
		returnObj.put("issueType", rs.getString("ISSUETYPE"));
		returnObj.put("urgency", rs.getString("URGENCY"));
		returnObj.put("importance", rs.getString("IMPORTANCE"));
		returnObj.put("createDate", rs.getString("CREATEDATE"));
		returnObj.put("isDone", rs.getString("ISDONE"));
		returnObj.put("pjtOid", rs.getString("PJTOID"));
		returnObj.put("pjtNo", rs.getString("PJTNO"));
		returnObj.put("pjtName", rs.getString("PJTNAME"));
		returnObj.put("pjtTypeName", rs.getString("PJTTYPENAME"));
		returnObj.put("ownerName", rs.getString("OWNERNAME"));
		returnObj.put("managerName", rs.getString("MANAGERNAME"));
		returnObj.put("answerPlanDate", rs.getString("ANSWERPLANDATE"));
		return returnObj;
	    }
	});

	return returnObjList;
    }

    public int searchIssueGridListCount(List<Map<String, Object>> conditionList) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	return oDaoManager.queryForCount(this.getIssueListQuery(conditionList));
    }

    public List<Map<String, Object>> searchIssueList(List<Map<String, Object>> conditionList) throws Exception {

	LoggableStatement pstmt = null;
	String str = null;
	ResultSet rs = null;

	Map<String, Object> returnObj = new HashMap<String, Object>();
	List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();

	try {

	    str = this.getIssueListQuery(conditionList);
	    pstmt = new LoggableStatement(conn, str);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		returnObj = new HashMap<String, Object>();
		returnObj.put("issueOid", rs.getString("ISSUEOID"));
		returnObj.put("ida2a2", rs.getString("IDA2A2"));
		returnObj.put("taskOid", rs.getString("TASKOID"));
		returnObj.put("subject", rs.getString("SUBJECT"));
		returnObj.put("issueType", rs.getString("ISSUETYPE"));
		returnObj.put("urgency", rs.getString("URGENCY"));
		returnObj.put("importance", rs.getString("IMPORTANCE"));
		returnObj.put("createDate", rs.getString("CREATEDATE"));
		returnObj.put("isDone", rs.getString("ISDONE"));
		returnObj.put("pjtOid", rs.getString("PJTOID"));
		returnObj.put("pjtNo", rs.getString("PJTNO"));
		returnObj.put("pjtName", rs.getString("PJTNAME"));
		returnObj.put("pjtTypeName", rs.getString("PJTTYPENAME"));
		returnObj.put("ownerName", rs.getString("OWNERNAME"));
		returnObj.put("managerName", rs.getString("MANAGERNAME"));
		returnObj.put("answerPlanDate", rs.getString("ANSWERPLANDATE"));
		returnObjList.add(returnObj);
	    }
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}
	return returnObjList;
    }

    public String getIssueListQuery(List<Map<String, Object>> conditionList) throws Exception {

	StringBuffer sb = null;

	sb = new StringBuffer();
	sb.append(" SELECT I.ISSUEOID                                         \n");
	sb.append("       ,I.IDA2A2                                           \n");
	sb.append("       ,I.TASKOID                                          \n");
	sb.append("       ,I.SUBJECT                                          \n");
	sb.append("       ,I.ISSUETYPE                                        \n");
	sb.append("       ,FN_GET_NUMBERCODEVALUE('ISSUEURGENCY',I.URGENCY,'" + conditionList.get(0).get("locale") + "') AS URGENCY             \n");
	sb.append("       ,FN_GET_NUMBERCODEVALUE('ISSUEIMPORTANCE',I.IMPORTANCE,'" + conditionList.get(0).get("locale") + "') AS IMPORTANCE    \n");
	sb.append("       ,I.CREATEDATE                                       \n");
	sb.append("       ,I.ISDONE                                           \n");
	sb.append("       ,I.PJTOID                                           \n");
	sb.append("       ,I.PJTNO                                            \n");
	sb.append("       ,I.PJTNAME                                          \n");
	sb.append("       ,I.PJTTYPENAME                                      \n");
	sb.append("       ,I.OWNER                                            \n");
	sb.append("       ,P.NAME                         AS OWNERNAME        \n");
	sb.append("       ,I.MANAGER                                          \n");
	sb.append("       ,D.NAME                         AS MANAGERNAME      \n");
	sb.append("       ,(SELECT L.PLANDATE                                 \n");
	sb.append("           FROM QUESTIONANSWERLINK  Q                      \n");
	sb.append("               ,PROJECTISSUEANSWER  L                      \n");
	sb.append("          WHERE Q.IDA3A5 = L.IDA2A2                        \n");
	sb.append("            AND L.ISCHECK = '0'                            \n");
	sb.append("            AND Q.IDA3B5 = I.IDA2A2    		      \n");
	sb.append("            AND ROWNUM = 1)  AS ANSWERPLANDATE   	      \n");
	sb.append("   FROM ISSUEVIEW I                                        \n");
	sb.append("       ,PEOPLE    P                                        \n");
	sb.append("       ,PEOPLE    D                                        \n");
	for (Map<String, Object> condistion : conditionList) {
	    KETParamMapUtil queryTable = KETParamMapUtil.getMap(condistion);
	    // 담당부서가 존재하는 경우만 Join Table 설정
	    if (!queryTable.getString("orgOid").equals("")) {
		sb.append("       ,PEOPLE    M                                \n");
		break;
	    }
	}
	sb.append("  WHERE I.OWNER = P.IDA3B4(+)                              \n");
	sb.append("   AND  I.MANAGER = D.IDA3B4(+)                            \n");

	for (Map<String, Object> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

	    if (!map.getString("myIssueList").equals("") && !map.getString("myAnswerList").equals("")) {
		// MY 제기이슈 || MY 담당이슈
		sb.append("    AND ( ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.MANAGER", KETParamMapUtil.OidToString(map.getString("user")), false));
		sb.append(" OR ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.OWNER", KETParamMapUtil.OidToString(map.getString("user")), false)).append(") \n");
	    } else if (!map.getString("myIssueList").equals("") && "all".equals(map.getString("myIssueList"))) {
		// MY 제기이슈 || MY 담당이슈
		sb.append("    AND ( ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.MANAGER", KETParamMapUtil.OidToString(map.getString("user")), false)).append(" ");
		sb.append(" OR ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.OWNER", KETParamMapUtil.OidToString(map.getString("user")), false)).append(") \n");
	    } else {
		if (!map.getString("myIssueList").equals("")) {
		    // MY 제기이슈
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.OWNER", KETParamMapUtil.OidToString(map.getString("user")), false)).append("     \n");
		} else if (!map.getString("myAnswerList").equals("")) {
		    // MY 담당이슈
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.MANAGER", KETParamMapUtil.OidToString(map.getString("user")), false)).append("     \n");
		}
	    }
	    // Project No
	    if (!map.getString("projectNo").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.PJTNO", map.getString("projectNo"), true)).append("     \n");
	    }
	    // 완료여부
	    String[] category = map.getStringArray("category");
	    if (category != null && category.length > 0) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.ISDONE", map.getString("category"), false)).append("     \n");
	    }
	    // Project Name
	    if (!map.getString("projectName").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.PJTNAME", map.getString("projectName"), true)).append("     \n");
	    }
	    // 담당부서
	    if (!map.getString("orgOid").equals("")) {
		sb.append("    AND I.MANAGER = M.IDA3B4    \n");
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("M.IDA3C4", KETParamMapUtil.OidToString(map.getString("orgOid")), false)).append("     \n");
	    }
	    // 담당자
	    if (map.getString("manager") != "") {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.MANAGER", KETParamMapUtil.OidToString(map.getString("manager")), false)).append("     \n");
	    }
	    // 제목
	    if (!map.getString("subject").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.SUBJECT", map.getString("subject"), true)).append("     \n");
	    }
	    // 제기자
	    if (!map.getString("owner").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.OWNER", KETParamMapUtil.OidToString(map.getString("owner")), false)).append("     \n");
	    }
	    // 제기일
	    if (!map.getString("startCreateDate").equals("")) {
		String predate = map.getString("startCreateDate");
		predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		sb.append("    AND I.CREATEDATE >= TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')                 \n");
	    }
	    if (!map.getString("endCreateDate").equals("")) {
		String postdate = map.getString("endCreateDate");
		postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		sb.append("    AND I.CREATEDATE < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')                \n");
	    }
	    // 구분
	    String[] type = map.getStringArray("type");
	    if (type != null && type.length > 0) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.ISSUETYPE", map.getString("type"), false)).append("     \n");
	    }
	    // 긴급여부
	    String[] urgency = map.getStringArray("urgency");
	    if (urgency != null && urgency.length > 0) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.URGENCY", map.getString("urgency"), false)).append("     \n");
	    }
	    // 중요도
	    String[] importance = map.getStringArray("importance");
	    if (importance != null && importance.length > 0) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.IMPORTANCE", map.getString("importance"), false)).append("     \n");
	    }
	    
	    // 사업부
	    String[] dType1 = map.getStringArray("dType1");
	    if (dType1 != null && dType1.length > 0) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.PJTTYPE", map.getString("dType1"), false)).append("     \n");
	    }
	}

	return sb.toString();
    }
}
