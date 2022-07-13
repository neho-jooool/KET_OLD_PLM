package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.util.StringUtil;

public class CostApprDao {

    private Connection conn;

    public CostApprDao(Connection conn) {
	this.conn = conn;
    }

    public CostApprDao() {
	super();
    }

    /**
     * 함수명 : SearchRepass 설명 : 개발담당자 결재
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList SearchRepass(String pk_cr_group, String rev_no, String team, String GroupJang_name) throws Exception {

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	PreparedStatement pstmt3 = null;
	PreparedStatement pstmt4 = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	StringBuffer sb2 = new StringBuffer();
	StringBuffer sb3 = new StringBuffer();
	StringBuffer sb4 = new StringBuffer();

	sb.append("select pk_cr, f_name, '['||dev_step||'] '||pk_cr_group ||' '||pjt_name as pjt_name, wfinfo_pk_wid.nextval as	pk_wid  ");
	sb.append("  from cost_request a ,cost_productinfo b                  ");
	sb.append(" where a.fk_pid = b.pk_pid                                 ");
	sb.append("   and pk_cr_group = ?                                     ");
	sb.append("   and rev_no      = ?                                     ");
	sb.append("   and fk_wid is null                                      ");

	sb2.append("insert into wfinfo (  						  			               ");
	sb2.append("       pk_wid, fk_cost, cost_flag, f_name, step_no, approval, f_day, gr_name   ) ");
	sb2.append("values(?,      ?,       'R',       ?,      '0.5',     'ok',     sysdate, ?) ");

	sb3.append("update cost_request");
	sb3.append("   set fk_wid = ?  ");
	sb3.append(" where pk_cr  = ?  ");

	sb4.append("update cost_work");
	sb4.append("   set fk_wid = ?  ");
	sb4.append(" where fk_cost_request  = ? ");

	String pk_cr = "";
	String f_name = "";
	String pjt_name = "";
	String pk_wid = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		pk_cr = StringUtil.checkNull(rs.getString("pk_cr"));
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		pk_wid = StringUtil.checkNull(rs.getString("pk_wid"));

		pstmt2 = conn.prepareStatement(sb2.toString());
		pstmt2.setString(1, pk_wid);
		pstmt2.setString(2, pk_cr);
		pstmt2.setString(3, f_name);
		pstmt2.setString(4, GroupJang_name);

		pstmt2.executeUpdate();

		pstmt3 = conn.prepareStatement(sb3.toString());
		pstmt3.setString(1, pk_wid);
		pstmt3.setString(2, pk_cr);
		pstmt3.executeUpdate();

		pstmt4 = conn.prepareStatement(sb4.toString());
		pstmt4.setString(1, pk_wid);
		pstmt4.setString(2, pk_cr);
		pstmt4.executeUpdate();
	    }

	    SearchMap = new Hashtable<String, String>();
	    SearchMap.put("pjt_name", pjt_name);
	    SearchMap.put("f_name", f_name);
	    SearchList.add(SearchMap);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    sb2.delete(0, sb2.length());
	    sb3.delete(0, sb3.length());
	    sb4.delete(0, sb4.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (pstmt2 != null) {
		pstmt2.close();
	    }
	    if (pstmt3 != null) {
		pstmt3.close();
	    }
	    if (pstmt4 != null) {
		pstmt4.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : SearchEmail 설명 : 그룹장,팀장 결재시 메일정보 알아오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList SearchEmail(String f_name, String team, String step_no, String a_name, String GroupJang_Id) throws Exception {

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	if (step_no.equals("0")) { // 그룹장에게 결재요청
	    sb.append("select M_email,leader,le_email from                 ");
	    sb.append("(select master.dbo.xdbdec('normal',B.Email) as M_email                             ");
	    sb.append("   from ghost_ket.auth_cost A, ghost_ket.CDUser B   ");
	    sb.append(" where A.empno = B.Account                          ");
	    sb.append("   and B.GroupCode = ?                        ");
	    sb.append("   and B.Name = ?) a,                               ");
	    sb.append("(select A.name as leader, master.dbo.xdbdec('normal',B.Email) as le_email       ");
	    sb.append("  from ghost_ket.auth_cost A, ghost_ket.CDUser B    ");
	    sb.append(" where A.empno = B.Account                          ");
	    sb.append("   and B.GroupCode = ?                              ");
	    if (!GroupJang_Id.equals("")) {
		sb.append(" and B.AccountAlias = '" + GroupJang_Id + "')b");
	    }
//	    sb.append("   and A.group_m   = 'G') b                         ");
	}

	if (step_no.equals("0.5")) { // 팀장에게 결재요청

	    sb.append(" SELECT A.M_email, B.Name as leader,master.dbo.xdbdec('normal',B.Email)  as le_email ");
	    sb.append("   FROM (SELECT GroupCode,master.dbo.xdbdec('normal',Email)  as M_email              ");
	    sb.append("                 FROM ghost_ket.CDUser                        ");
	    sb.append("              WHERE Name = ?) A, ghost_ket.CDUser B         ");
	    if ("11681".equals(team)) {// 커넥터연구개발센타는 센터장이 팀장이므로 분기처리..2014-09-24 황정태 수정
		sb.append(" WHERE B.PositionCode = 'D30'                           ");
	    } else {
		sb.append(" WHERE B.PositionCode = 'J10'                           ");
	    }
	    sb.append("   AND A.GroupCode = ?                                  ");
	    sb.append("   AND B.GroupCode = A.GroupCode                        ");
	}

	if (step_no.equals("1")) { // 팀장이 결재시
	    sb.append(" select a.M_email,'' as leader , b.le_email                  ");
	    sb.append("   from (select master.dbo.xdbdec('normal',Email)  as M_email                            ");
	    sb.append("           from ghost_ket.CDUser                            ");
	    sb.append("          where Name = ?                                    ");
	    sb.append("            and GroupCode = ?) a,                           ");
	    sb.append("        (select master.dbo.xdbdec('normal',Email)  as le_email                           ");
	    sb.append("           from ghost_ket.CDUser                            ");
	    sb.append("          where Name = ?) b                                    ");
	    // sb.append("            and GroupCode not in ('11614','11612','11616')) b   ");
	}

	String M_email = "";
	String L_email = "";
	String Le_name = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    if (step_no.equals("0")) {
		pstmt.setString(1, team);
		pstmt.setString(2, f_name);
		pstmt.setString(3, team);
	    }
	    if (step_no.equals("0.5")) {
		pstmt.setString(1, f_name);
		pstmt.setString(2, team);
	    }
	    if (step_no.equals("1")) {
		pstmt.setString(1, f_name);
		pstmt.setString(2, team);
		pstmt.setString(3, a_name);
	    }

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		M_email = StringUtil.checkNull(rs.getString("M_email"));
		Le_name = StringUtil.checkNull(rs.getString("leader"));
		L_email = StringUtil.checkNull(rs.getString("le_email"));
		;
	    }
	    SearchMap = new Hashtable<String, String>();
	    SearchMap.put("M_email", M_email);
	    SearchMap.put("Le_name", Le_name);
	    SearchMap.put("L_email", L_email);

	    SearchList.add(SearchMap);
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

	return SearchList;
    }

    /**
     * 함수명 : SearchGroupJang 설명 : 그룹장 List 조회
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2013. 07. 10.
     */
    public ArrayList SearchGroupJang(String team) throws Exception {

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("  select B.Name as Name, AccountAlias as id                           ");
	sb.append("   from ghost_ket.auth_cost A, ghost_ket.CDUser B   ");
	sb.append(" where A.empno = B.Account                          ");
	sb.append("   and B.GroupCode = ?                        ");
	sb.append("   and A.group_m = 'G'                         ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, team);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("Name", StringUtil.checkNull(rs.getString("Name")));
		SearchMap.put("Id", StringUtil.checkNull(rs.getString("id")));
		SearchList.add(SearchMap);
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
	    // if(conn!=null) {conn.close();}
	}

	return SearchList;
    }

    /**
     * 함수명 : getSearchPjtinfo 설명 : 프로젝트 명,개발담당자 이름 얻기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */

    public String getSearchPjtinfo(String pk_cr_group, String rev_no) {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	String pjt_name = "";
	String f_name = "";
	String a_name = "";
	String g_name = "";
	String fk_wid = "";

	sb.append("select f_name,'['||dev_step||'] '||pk_cr_group ||' '||pjt_name as pjt_name, a_name, fk_wid from cost_request a, cost_productinfo b    ");
	sb.append(" where a.fk_pid    = b.pk_pid ");
	sb.append("   and pk_cr_group = ?        ");
	sb.append("   and rev_no      = ?        ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		a_name = StringUtil.checkNull(rs.getString("a_name"));
		fk_wid = fk_wid + "," + StringUtil.checkNull(rs.getString("fk_wid"));
	    }

	} catch (Exception e) {

	    e.printStackTrace();
	}
	if (f_name.equals("")) {
	    f_name = " ";
	}
	if (pjt_name.equals("")) {
	    pjt_name = " ";
	}
	if (a_name.equals("")) {
	    a_name = " ";
	}
	if (fk_wid.equals("")) {
	    fk_wid = " ";
	}
	try {
	    g_name = SearchGname(pk_cr_group, rev_no);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (a_name.equals("")) {
	    g_name = " ";
	}
	return f_name + ";" + pjt_name + ";" + a_name + ";" + g_name + ";" + fk_wid;
    }

    /**
     * 함수명 : SearchGname 설명 : 그룹장 이름 가져오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String SearchGname(String PK_CR_GROUP, String REV_NO) throws Exception {
	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	/*
	 * sb.append("select a.name        "); sb.append("  from ghost_ket.auth_cost A, ghost_ket.CDUser B    ");
	 * sb.append(" where A.empno = b.Account                          ");
	 * sb.append("   and B.GroupCode = ?                              ");
	 * sb.append("   and A.group_m   = 'G'                         ");
	 */

	sb.append("  SELECT GR_NAME FROM WFINFO WHERE FK_COST IN ( ");
	sb.append("  SELECT PK_CR FROM COST_REQUEST WHERE FK_PID IN ( ");
	sb.append("  SELECT PK_PID  FROM COST_PRODUCTINFO WHERE PK_CR_GROUP = ? AND REV_NO = ?)) ");
	sb.append("  AND ROWNUM = 1 ");

	String G_name = "";

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, PK_CR_GROUP);
	    pstmt.setString(2, REV_NO);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		G_name = StringUtil.checkNull(rs.getString("GR_NAME"));
		System.out.println("G_name : " + G_name);
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
	    // if(conn!=null) {conn.close();}
	}
	return G_name;
    }

    /**
     * 함수명 : Appr_Gr 설명 : 그룹장 결재
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public void Appr_Gr(String pk_cr_group, String rev_no, String GR_NAME) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("update wfinfo                                                    ");
	sb.append("   set GR_DAY = SYSDATE                                          ");
	sb.append("      ,STEP_NO = '1'                                             ");
	sb.append(" where pk_wid                                                    ");
	sb.append("    in (select fk_wid from cost_request a, cost_productinfo b    ");
	sb.append("        where a.fk_pid    = b.pk_pid                             ");
	sb.append("          and pk_cr_group = ?                                    ");
	sb.append("          and rev_no      = ?)                                   ");
	sb.append("   and cost_flag = 'R'                                           ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}

    }

    /**
     * 함수명 : Appr_leader 설명 : 팀장 결재
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public void Appr_leader(String pk_cr_group, String rev_no, String LEADER_NAME) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("update wfinfo                                                    ");
	sb.append("   set LEADER_DAY  = SYSDATE                                     ");
	sb.append("      ,LEADER_NAME = ?                                           ");
	sb.append("      ,STEP_NO  = '2'                                            ");
	sb.append("      ,APPROVAL = 'finish'                                       ");
	sb.append(" where pk_wid                                                    ");
	sb.append("    in (select fk_wid from cost_request a, cost_productinfo b    ");
	sb.append("        where a.fk_pid    = b.pk_pid                             ");
	sb.append("          and pk_cr_group = ?                                    ");
	sb.append("          and rev_no      = ?)                                   ");
	sb.append("   and cost_flag = 'R'                                           ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, LEADER_NAME);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}

    }

    /**
     * 함수명 : AprovalReject 설명 : 팀장의 요청서 반려
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String AprovalReject(String pk_cr_group, String rev_no) throws Exception {
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	StringBuffer sb2 = new StringBuffer();

	sb.append(" select f_name,pk_cr_group||' '||pjt_name as pjt_name,pk_cr from cost_request a, cost_productinfo b ");
	sb.append("  where a.fk_pid    = b.pk_pid                                        ");
	sb.append("    and pk_cr_group = ?                                               ");
	sb.append("    and rev_no      = ?                                               ");

	String f_name = "";
	String pjt_name = "";
	String pk_cr = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		pk_cr = StringUtil.checkNull(rs.getString("pk_cr"));
		sb2.append("delete wfinfo                           ");
		sb2.append(" where fk_cost   = ?                    ");
		sb2.append("   and cost_flag = 'R'                  ");

		try {

		    pstmt2 = conn.prepareStatement(sb2.toString());
		    pstmt2.setString(1, pk_cr);

		    pstmt2.executeUpdate();

		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    sb2.delete(0, sb2.length());
		}

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
	    if (pstmt2 != null) {
		pstmt2.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return f_name + ";" + pjt_name;
    }

    /**
     * 함수명 : SearchF_email 설명 : 이메일 정보 가져오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String SearchF_email(String f_name, String team) throws Exception {
	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select master.dbo.xdbdec('normal',Email) as email              ");
	sb.append("  from ghost_ket.CDUser   ");
	sb.append(" where GroupCode = ?      ");
	sb.append("   and name      = ?      ");

	String M_email = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, team);
	    pstmt.setString(2, f_name);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		M_email = StringUtil.checkNull(rs.getString("email"));

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
	    // if(conn!=null) {conn.close();}
	}
	return M_email;
    }

    /**
     * 함수명 : ChangeWname 설명 : 산출담당자 변경
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList ChangeWname(String w_name, String pk_cr_group, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" select pjt_name,f_name,team from cost_productinfo ");
	sb.append("  where pk_cr_group = ? AND rev_no = ?          ");

	String pjt_name = "";
	String f_name = "";
	String team = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    rs = pstmt.executeQuery();
	    SearchMap = new Hashtable<String, String>();
	    while (rs.next()) {
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		team = StringUtil.checkNull(rs.getString("team"));

		SearchMap.put("pjt_name", pjt_name);
		SearchMap.put("f_name", f_name);
		SearchMap.put("team", team);
	    }

	    SearchList.add(SearchMap);
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
	    // if(conn!=null) {conn.close();}
	}

	sb.append(" update cost_productinfo ");
	sb.append("    set w_name = ?       ");
	sb.append("  where pk_cr_group = ?  ");
	sb.append("    and rev_no = ?       ");

	pstmt = null;
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, w_name);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	return SearchList;
    }

    /**
     * 함수명 : SearchF_email2 설명 : 이메일 정보 가져오기2
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String SearchF_email2(String w_name) throws Exception {
	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select master.dbo.xdbdec('normal',Email) as email               ");
	sb.append("  from ghost_ket.CDUser    ");
	sb.append(" where GroupCode = '11651' ");
	sb.append("   and name      = ?       ");

	String M_email = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, w_name);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		M_email = StringUtil.checkNull(rs.getString("email"));

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
	    // if(conn!=null) {conn.close();}
	}
	return M_email;
    }

    /**
     * 함수명 : Reject_work 설명 : 요청서반려
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String Reject_work(String pk_cr_group, String rev_no, String pps_note) throws Exception {

	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" select pjt_name,b.f_name,Leader_name from COST_REQUEST a,cost_productinfo b,wfinfo c ");
	sb.append("  where b.pk_cr_group = ?  AND b.rev_no = ?                                           ");
	sb.append("    and a.fk_pid = b.pk_pid                                                           ");
	sb.append("    and a.PK_CR = c.fk_cost(+)                                                        ");
	sb.append("    and nvl(cost_flag,'R') = 'R'                                                      ");

	String pjt_name = "";
	String f_name = "";
	String L_name = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		L_name = StringUtil.checkNull(rs.getString("Leader_name"));

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
	    // if(conn!=null) {conn.close();}
	}

	sb.append(" UPDATE cost_request                          ");
	sb.append("    SET PPS_NOTE = ?                          ");
	sb.append("  WHERE FK_PID                                ");
	sb.append("     IN (select PK_PID from cost_productinfo  ");
	sb.append("          where pk_cr_group = ?               ");
	sb.append("            and rev_no      = ?)              ");

	pstmt = null;
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pps_note);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	sb.append(" DELETE wfinfo                                            ");
	sb.append("  WHERE FK_COST                                           ");
	sb.append("     IN (SELECT PK_CR FROM COST_REQUEST                   ");
	sb.append("          WHERE FK_PID                                    ");
	sb.append(" 		    IN (select PK_PID from cost_productinfo      ");
	sb.append("                  where pk_cr_group = ?                   ");
	sb.append("                    and rev_no      = ?                   ");
	sb.append(" 			   )                                         ");
	sb.append(" 	   )                                                 ");
	sb.append("    AND cost_flag = 'R'                                   ");

	pstmt = null;
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	return pjt_name + ";" + f_name + ";" + L_name;
    }

    /**
     * 함수명 : ReportAppr_1 설명 : report 담당자 승인
     * 
     * @param
     * @return String
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 09. 26.
     */
    public String ReportAppr_1(String report_pk, String pk_cr_group, String rev_no, String Ename) throws Exception {
	System.out.println("~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!@@@@@@@@@@@@@");
	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT '['||dev_step||'] '||pk_cr_group ||' '||pjt_name as pjt_name,CUSTOMER_F,PJT_NO,F_NAME	       ");
	sb.append("   FROM COST_REPORT A, COST_PRODUCTINFO B   ");
	sb.append("  WHERE A.FK_PID      = B.PK_PID            ");
	sb.append("    AND A.CRP_GROUP     =  ? ");
	sb.append("    AND B.PK_CR_GROUP = ? ");
	sb.append("    AND B.REV_NO           =  ? ");

	String PJT_NAME = "";
	String CUSTOMER_F = "";
	String PJT_NO = "";
	String F_NAME = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    System.out.println(sb.toString());
	    pstmt.setString(1, report_pk);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);
	    System.out.println("hello! ~~~~~~~~~~````11111");
	    rs = pstmt.executeQuery();
	    System.out.println("hello! ~~~~~~~~~~````22222222222");
	    while (rs.next()) {
		System.out.println("hello! ~~~~~~~~~~````33333");
		PJT_NAME = StringUtil.checkNull(rs.getString("PJT_NAME"));
		System.out.println("hello! ~~~~~~~~~~````4");
		CUSTOMER_F = StringUtil.checkNull(rs.getString("CUSTOMER_F"));
		System.out.println("hello! ~~~~~~~~~~````5");
		PJT_NO = StringUtil.checkNull(rs.getString("PJT_NO"));
		System.out.println("hello! ~~~~~~~~~~````6");
		F_NAME = StringUtil.checkNull(rs.getString("F_NAME"));

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
	    // if(conn!=null) {conn.close();}
	}
	System.out.println("hello! ~~~~~~~~~~````7");
	sb.append(" UPDATE COST_PRODUCTINFO                                                               ");
	sb.append("       SET W_NAME  =  ?");
	sb.append("     where PK_CR_GROUP = ? ");
	sb.append("       AND REV_NO      = ?");

	pstmt = null;
	try {
	    System.out.println("hello! ~~~~~~~~~~````8");
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, Ename);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);
	    System.out.println("hello! ~~~~~~~~~~````9");
	    pstmt.executeUpdate();
	    System.out.println("hello! ~~~~~~~~~~````10");
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	sb.append(" UPDATE WFINFO                                                               ");
	sb.append("    SET STEP_NO  = STEP_NO + 1,                                              ");
	sb.append("           APPROVAL = 'ok',                                                     ");
	sb.append("           F_DAY_2    = SYSDATE,                                                   ");
	sb.append("           CO_AC_DAY    = SYSDATE,                                                  ");
	sb.append("           F_NAME_2    = ?                                                  ");
	sb.append("  WHERE PK_WID   ");
	sb.append(" in (select fk_wid from cost_request 	  ");
	sb.append("          where fk_pid 	  ");
	sb.append("             in (select pk_pid from cost_productinfo where (rev_no,pk_cr_group) in 	  ");
	sb.append("                    (select rev_no,pk_cr_group from cost_productinfo 	  ");
	sb.append("                      where pk_pid in (SELECT fk_pid FROM COST_REPORT WHERE CRP_GROUP = ?))))	  ");

	pstmt = null;
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, F_NAME);
	    pstmt.setString(2, report_pk);
	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return PJT_NAME;
    }

    /**
     * 함수명 : ReportAppr_2 설명 : report 팀장 승인
     * 
     * @param
     * @return String
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 09. 26.
     */
    public ArrayList ReportAppr_2(String report_pk, String pk_cr_group, String rev_no, String Ename, String step_no, String consent_txt,
	    String pass_type) throws Exception {
	System.out.println("~~~~~~~~ReportAppr_2~~~!@@@ReportAppr_2@@@@@@@@@");
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" UPDATE WFINFO                                                               ");

	if (pass_type.equals("6") || pass_type.equals("7")) {
	    sb.append("    SET STEP_NO  = 6,                                              ");
	    sb.append("        approval              = 'complete',                                            ");
	    if (pass_type.equals("6")) {
		sb.append("           Leader_day_2    	= sysdate,                                                 ");
		sb.append("           Leader_name_2    = ?                                                 ");
	    } else if (pass_type.equals("7")) {
		sb.append("           Gr_day_2    	= sysdate,                                                 ");
		sb.append("           Gr_name_2     = ?                                                 ");
	    }
	} else {
	    if (step_no.equals("3")) {
		sb.append("    SET STEP_NO  = 4,                                              "); //센타장 결재선 누락에 따른 주석처리 2014-03-12 .. 센터장 생겨서 다시 집어넣음 옘병 .. 2017.01.09
		//sb.append("    SET STEP_NO  = 5.1,                                              ");// 센터장이 결재선에서 누락됨에 따라 팀장 결재시 센터장 건너뛰고
		                                                                                   // 연구원장까지 결재올라감 2014-03-12
	    }
	    if (step_no.equals("4")) {
		sb.append("    SET STEP_NO  = 5.1,                                              ");
	    }
	    if (step_no.equals("3")) {
		sb.append("           Gr_day_2       = SYSDATE,                                                   ");
		sb.append("           Gr_name_2    = ?                                                  ");
	    }
	    if (step_no.equals("4")) {
		sb.append("           approval         	= 'im_complete_2',                                                   ");
		sb.append("           Leader_day_2    	= sysdate,                                                 ");
		sb.append("           Leader_name_2    = ?                                                 ");
	    }
	}

	sb.append("  WHERE PK_WID ");
	sb.append(" in (select fk_wid from cost_request 	  ");
	sb.append("          where fk_pid 	  ");
	sb.append("             in (select pk_pid from cost_productinfo where (rev_no,pk_cr_group) in 	  ");
	sb.append("                    (select rev_no,pk_cr_group from cost_productinfo 	  ");
	sb.append("                      where pk_pid in (SELECT fk_pid FROM COST_REPORT WHERE CRP_GROUP = " + report_pk + "))))	  ");

	pstmt = null;
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, Ename);
	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	if (pass_type.equals("6") || pass_type.equals("7")) {
	    sb.append(" UPDATE cost_report                                                               ");
	    sb.append("    set  pass_content = " + "'" + consent_txt + "'" + ",                                            ");
	    if (pass_type.equals("6")) {
		sb.append("       pass_type    = '센터장전결'                                       ");
	    } else if (pass_type.equals("7")) {
		sb.append("       pass_type    = '팀장전결'                                       ");
	    }
	    sb.append("  WHERE CRP_GROUP =" + report_pk);
	    System.out.println("전결 query : " + sb.toString());
	    pstmt = null;
	    try {

		pstmt = conn.prepareStatement(sb.toString());
		pstmt.executeUpdate();

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}

	if (step_no.equals("3")) {
	    sb.append("  SELECT '['||dev_step||'] '||pk_cr_group ||' '||pjt_name as pjt_name, B.W_NAME, B.F_NAME, C.Gr_name_2 as LEADER_NAME, B.A_NAME 	");
	} else if (step_no.equals("4")) {
	    sb.append("  SELECT '['||dev_step||'] '||pk_cr_group ||' '||pjt_name as pjt_name, B.W_NAME, B.F_NAME, C.LEADER_NAME_2 as LEADER_NAME, B.A_NAME 	");
	}
	sb.append("    FROM COST_REPORT A, COST_PRODUCTINFO B, WFINFO C             				");
	sb.append("   WHERE A.FK_PID       	    = B.PK_PID                               									");
	sb.append("     AND A.CRP_GROUP      =  ?                                     											");
	sb.append("     AND B.PK_CR_GROUP  =  ?                                     											");
	sb.append("     AND B.REV_NO             =  ?                                     											");
	sb.append("     AND A.FK_WID              = C.PK_WID                                     								");
	System.out.println("666666");
	String PJT_NAME = "";
	String W_NAME = "";
	String F_NAME = "";
	String LEADER_NAME = "";
	String A_NAME = "";
	System.out.println("77777");
	try {

	    System.out.println("report_pk==>" + report_pk);
	    System.out.println("pk_cr_group==>" + pk_cr_group);
	    System.out.println("rev_no==>" + rev_no);
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, report_pk);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);
	    System.out.println(sb.toString());

	    rs = pstmt.executeQuery();
	    System.out.println("hello! ~~~~~~~~~~````22222222222");
	    while (rs.next()) {
		PJT_NAME = StringUtil.checkNull(rs.getString("PJT_NAME"));
		W_NAME = StringUtil.checkNull(rs.getString("W_NAME"));
		F_NAME = StringUtil.checkNull(rs.getString("F_NAME"));
		LEADER_NAME = StringUtil.checkNull(rs.getString("LEADER_NAME"));
		A_NAME = StringUtil.checkNull(rs.getString("A_NAME"));
	    }
	    SearchMap = new Hashtable<String, String>();
	    SearchMap.put("PJT_NAME", PJT_NAME);
	    SearchMap.put("W_NAME", W_NAME);
	    SearchMap.put("F_NAME", F_NAME);
	    SearchMap.put("LEADER_NAME", LEADER_NAME);
	    SearchMap.put("A_NAME", A_NAME);

	    SearchList.add(SearchMap);
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
	    // if(conn!=null) {conn.close();}
	}

	/*
	 * if(!StringUtil.checkNull(consent_txt).equals("") && !"6".equals(pass_type) && !"7".equals(pass_type)){ if(step_no.equals("3")){
	 * consent_txt = "[석상식] "+consent_txt; }else if(step_no.equals("4")){ consent_txt = "[홍종범] "+consent_txt; }
	 * 
	 * sb.append(" UPDATE cost_report                                                               ");
	 * sb.append("      SET NOTE_4 = NOTE_4||'" +consent_txt+"'||chr(13)||chr(10)" ); sb.append("  WHERE CRP_GROUP ="+report_pk );
	 * System.out.println("query : "+sb.toString()); pstmt = null; try{
	 * 
	 * pstmt = conn.prepareStatement(sb.toString()); pstmt.executeUpdate();
	 * 
	 * }catch(Exception e){ e.printStackTrace(); }finally{ sb.delete( 0 , sb.length() ); if(pstmt!=null) {pstmt.close();}
	 * //if(conn!=null) {conn.close();} } }
	 */
	return SearchList;
    }

    /**
     * 함수명 : SearchEmail 설명 : 보고서 결재시 (팀장,센터장) 메일정보 알아오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */

    public ArrayList SearchReportEmail(String w_name, String f_name, String team, String FLe_name, String a_name) throws Exception {

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	String M_email = ""; // 작성자 email
	String f_email = ""; // 개발담당자 email
	String FL_email = ""; // 센터장 email
	String a_email = ""; // 영업담장자 email
	String ALe_email = ""; // 영업팀장 email
	String ALe_name = ""; // 영업팀장 이름

	sb.append(" select M_email,f_email,FL_email,a_email,ALe_email,ALe_name from    		");
	sb.append(" (select master.dbo.xdbdec('normal',Email)  as M_email from ghost_ket.CDUser                               		");
	sb.append("   where Name = ?) a,                                                              					");
	sb.append(" (select master.dbo.xdbdec('normal',Email)  as f_email from ghost_ket.CDUser                     					");
	sb.append("   where Name = ?                                            										");
	sb.append("    and GroupCode = ?)b,                                      										");
	sb.append(" (select master.dbo.xdbdec('normal',Email)  as FL_email from ghost_ket.CDUser                    					");
	sb.append("   where Name = ?                                            										");
	sb.append("  )c,                                      										");
	sb.append(" (select master.dbo.xdbdec('normal',a.Email) AS a_email,master.dbo.xdbdec('normal',c.email)  as ALe_email,c.Name as ALe_name 	");
	sb.append("    from ghost_ket.CDUser a,ghost_ket.CDGroup b,ghost_ket.CDUser c  		");
	sb.append("   where a.GroupCode = b.GroupCode                                  						");
	sb.append("     and b.Name LIKE '%영업%'                            							");
	sb.append("     and a.Name = ?                                         											");
	sb.append("     and a.GroupCode = c.GroupCode                                  						");
	sb.append("     and c.PositionCode = 'J10') d                                  								");

	System.out.println("w_name=>" + w_name);
	System.out.println("f_name=>" + f_name);
	System.out.println(" team=>" + team);
	System.out.println("FLe_name=>" + FLe_name);
	System.out.println("a_name=>" + a_name);

	try {

	    pstmt = conn.prepareStatement(sb.toString());

	    pstmt.setString(1, w_name);
	    pstmt.setString(2, f_name);
	    pstmt.setString(3, team);
	    pstmt.setString(4, FLe_name);
	    pstmt.setString(5, a_name);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		M_email = StringUtil.checkNull(rs.getString("M_email"));
		f_email = StringUtil.checkNull(rs.getString("f_email"));
		FL_email = StringUtil.checkNull(rs.getString("FL_email"));
		a_email = StringUtil.checkNull(rs.getString("a_email"));
		ALe_email = StringUtil.checkNull(rs.getString("ALe_email"));
		ALe_name = StringUtil.checkNull(rs.getString("ALe_name"));
	    }
	    SearchMap = new Hashtable<String, String>();
	    SearchMap.put("M_email", M_email);
	    SearchMap.put("f_email", f_email);
	    SearchMap.put("FL_email", FL_email);
	    SearchMap.put("a_email", a_email);
	    SearchMap.put("ALe_email", ALe_email);
	    SearchMap.put("ALe_name", ALe_name);

	    SearchList.add(SearchMap);
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
	    // if(conn!=null) {conn.close();}
	}

	return SearchList;
    }

    /**
     * 함수명 : ReportAppr_3 설명 : report 최종배포
     * 
     * @param
     * @return String
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 09. 26.
     */
    public ArrayList ReportAppr_3(String step_no, String pass_type, String consent_txt, String report_pk, String pk_cr_group,
	    String rev_no, String JB_day, String p_leader_day, String r_owner_day, String r_pre_day, String Ename) throws Exception {
	System.out.println("~~~~~~~~ReportAppr_3~~~!@@@ReportAppr_2@@@@@@@@@");
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("  SELECT '['||dev_step||'] '||pk_cr_group ||' '||pjt_name as pjt_name, B.W_NAME, B.F_NAME, C.LEADER_NAME, B.A_NAME,  NVL(B.CASE_TYPE_USER,0) AS CASE_TYPE_USER, DEV_STEP	");
	sb.append("    FROM COST_REPORT A, COST_PRODUCTINFO B, WFINFO C             				");
	sb.append("   WHERE A.FK_PID       	    = B.PK_PID                               									");
	sb.append("     AND A.CRP_GROUP      =  ?                                     											");
	sb.append("     AND B.PK_CR_GROUP  =  ?                                     											");
	sb.append("     AND B.REV_NO             =  ?                                     											");
	sb.append("     AND A.FK_WID              = C.PK_WID                                     								");

	String PJT_NAME = "";
	String W_NAME = "";
	String F_NAME = "";
	String LEADER_NAME = "";
	String A_NAME = "";
	String CASE_TYPE_USER = "";
	String DEV_STEP = "";

	try {

	    System.out.println("report_pk==>" + report_pk);
	    System.out.println("pk_cr_group==>" + pk_cr_group);
	    System.out.println("rev_no==>" + rev_no);
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, report_pk);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);
	    System.out.println(sb.toString());

	    rs = pstmt.executeQuery();
	    System.out.println("hello! ~~~~~~~~~~````22222222222");
	    while (rs.next()) {
		PJT_NAME = StringUtil.checkNull(rs.getString("PJT_NAME"));
		W_NAME = StringUtil.checkNull(rs.getString("W_NAME"));
		F_NAME = StringUtil.checkNull(rs.getString("F_NAME"));
		LEADER_NAME = StringUtil.checkNull(rs.getString("LEADER_NAME"));
		A_NAME = StringUtil.checkNull(rs.getString("A_NAME"));
		CASE_TYPE_USER = StringUtil.checkNull(rs.getString("CASE_TYPE_USER"));
		DEV_STEP = StringUtil.checkNull(rs.getString("DEV_STEP"));
	    }
	    SearchMap = new Hashtable<String, String>();
	    SearchMap.put("PJT_NAME", PJT_NAME);
	    SearchMap.put("W_NAME", W_NAME);
	    SearchMap.put("F_NAME", F_NAME);
	    SearchMap.put("LEADER_NAME", LEADER_NAME);
	    SearchMap.put("A_NAME", A_NAME);
	    SearchMap.put("CASE_TYPE_USER", CASE_TYPE_USER);
	    SearchMap.put("DEV_STEP", DEV_STEP);

	    SearchList.add(SearchMap);
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
	    // if(conn!=null) {conn.close();}
	}

	sb.append(" UPDATE WFINFO                                                               ");
	sb.append("       SET STEP_NO    		= " + "'" + step_no + "'" + ",                                            ");
	if (pass_type.equals("2")) {
	    sb.append("       JB_day   		= sysdate,                                          ");
	    sb.append("       JB_name   	= '김석' ,");
	    sb.append("       approval     			= 'im_complete_1'                                            ");
	} else if (pass_type.equals("3")) {
	    sb.append("       p_leader_day   		= sysdate,                                            ");
	    sb.append("       p_leader_name   	= '김석',                                            ");
	    sb.append("       approval     			= 'im_complete_2'                                            ");
	} else if (pass_type.equals("4")) {
	    sb.append("       r_owner_day   		= sysdate,                                            ");
	    sb.append("       r_owner_name   	= '이원준',                                            ");
	    sb.append("       approval     			= 'complete'                                            ");
	} else if (pass_type.equals("5")) {
	    sb.append("       r_pre_day         = '" + r_pre_day + "' ,                                         ");
	    sb.append("       r_pre_name    = '이창원',                                            ");
	    sb.append("       approval              = 'complete'                                            ");
	}

	sb.append("  WHERE PK_WID ");
	sb.append(" in (select fk_wid from cost_request 	  ");
	sb.append("          where fk_pid 	  ");
	sb.append("             in (select pk_pid from cost_productinfo where (rev_no,pk_cr_group) in 	  ");
	sb.append("                    (select rev_no,pk_cr_group from cost_productinfo 	  ");
	sb.append("                      where pk_pid in (SELECT fk_pid FROM COST_REPORT WHERE CRP_GROUP = " + report_pk + "))))	  ");

	pstmt = null;
	try {
	    System.out.println(sb.toString());
	    pstmt = conn.prepareStatement(sb.toString());
	    System.out.println("1111");
	    pstmt.executeUpdate();
	    System.out.println("2222");

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	/*
	 * sb.append(" UPDATE COST_REPORT				  "); if(pass_type.equals("2")){ sb.append("  SET PASS_TYPE = '본부장결재' ,"); }
	 * if(pass_type.equals("3")){ sb.append("  SET PASS_TYPE = '부문장결재' ,"); } if(pass_type.equals("4")){
	 * sb.append("  SET PASS_TYPE = '사장결재' ,"); } sb.append("              PASS_CONTENT = "+"'"+consent_txt+"'");
	 * sb.append("              WHERE CRP_GROUP = "+"'"+report_pk+"'") ; pstmt = null; try{ System.out.println(sb.toString());
	 * System.out.println("3333"); pstmt = conn.prepareStatement(sb.toString()); pstmt.executeUpdate();
	 * 
	 * }catch(Exception e){ e.printStackTrace(); }finally{ sb.delete( 0 , sb.length() ); if(pstmt!=null) {pstmt.close();}
	 * //if(conn!=null) {conn.close();} }
	 */

	if (!StringUtil.checkNull(consent_txt).equals("") && (pass_type.equals("2") || pass_type.equals("3") || pass_type.equals("4"))) {
	    if (pass_type.equals("2")) {
		consent_txt = "[김동식] " + consent_txt;
	    } else if (pass_type.equals("3")) {
		consent_txt = "[김석] " + consent_txt;
	    } else if (pass_type.equals("4")) {
		consent_txt = "[이원준] " + consent_txt;
	    }

	    sb.append(" UPDATE cost_report                                                               ");
	    sb.append("      SET NOTE_4 = NOTE_4||'" + consent_txt + "'||chr(13)||chr(10)");
	    sb.append("  WHERE CRP_GROUP =" + report_pk);
	    System.out.println("query : " + sb.toString());
	    pstmt = null;
	    try {

		pstmt = conn.prepareStatement(sb.toString());
		pstmt.executeUpdate();

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}

	if (pass_type.equals("4")) {
	    sb.append("UPDATE COST_WORK");
	    sb.append("      SET FINISH_DAY = SYSDATE  ");
	    sb.append("WHERE FK_PID IN ( SELECT PK_PID FROM COST_PRODUCTINFO WHERE PK_CR_GROUP = " + "'" + pk_cr_group + "'"
		    + "AND REV_NO = " + "'" + rev_no + "')");
	    pstmt = null;
	    try {
		System.out.println(sb.toString());
		System.out.println("4444");
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.executeUpdate();

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	    sb.append("UPDATE COST_REQUEST");
	    sb.append("      SET FINISH_DAY = SYSDATE  ");
	    sb.append("WHERE FK_PID IN ( SELECT PK_PID FROM COST_PRODUCTINFO WHERE PK_CR_GROUP = " + "'" + pk_cr_group + "'"
		    + "AND REV_NO = " + "'" + rev_no + "')");

	    pstmt = null;
	    try {
		System.out.println(sb.toString());
		System.out.println("5555");
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.executeUpdate();
		System.out.println("6666");
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}

	return SearchList;
    }

    /**
     * 함수명 : ReportLeaderReturn 설명 : 보고서 팀장,센터장 반려
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */

    public ArrayList ReportLeaderReturn(String report_pk, String pk_cr_group, String rev_no) throws Exception {
	System.out.println("~~~~~~~~ReportAppr_2~~~!@@@ReportAppr_2@@@@@@@@@");
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("  SELECT '['||dev_step||'] '||pk_cr_group ||' '||pjt_name as pjt_name, B.W_NAME, B.F_NAME, B.A_NAME, LEADER_NAME, DEV_STEP");
	sb.append("    FROM COST_REPORT A, COST_PRODUCTINFO B, WFINFO C             				");
	sb.append("   WHERE A.FK_PID       	    = B.PK_PID                               									");
	sb.append("     AND A.CRP_GROUP      =  ?                                     											");
	sb.append("     AND B.PK_CR_GROUP  =  ?                                     											");
	sb.append("     AND B.REV_NO             =  ?                                     											");
	sb.append("     AND A.FK_WID              = C.PK_WID                                     								");
	System.out.println("666666");
	String PJT_NAME = "";
	String W_NAME = "";
	String F_NAME = "";
	String A_NAME = "";
	String LEADER_NAME = "";
	String DEV_STEP = "";
	System.out.println("77777");
	try {

	    System.out.println("report_pk==>" + report_pk);
	    System.out.println("pk_cr_group==>" + pk_cr_group);
	    System.out.println("rev_no==>" + rev_no);
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, report_pk);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);
	    System.out.println(sb.toString());

	    rs = pstmt.executeQuery();
	    System.out.println("hello! ~~~~~~~~~~````22222222222");
	    while (rs.next()) {
		PJT_NAME = StringUtil.checkNull(rs.getString("PJT_NAME"));
		W_NAME = StringUtil.checkNull(rs.getString("W_NAME"));
		F_NAME = StringUtil.checkNull(rs.getString("F_NAME"));
		A_NAME = StringUtil.checkNull(rs.getString("A_NAME"));
		LEADER_NAME = StringUtil.checkNull(rs.getString("LEADER_NAME"));
		DEV_STEP = StringUtil.checkNull(rs.getString("DEV_STEP"));
	    }
	    SearchMap = new Hashtable<String, String>();
	    SearchMap.put("PJT_NAME", PJT_NAME);
	    SearchMap.put("W_NAME", W_NAME);
	    SearchMap.put("F_NAME", F_NAME);
	    SearchMap.put("A_NAME", A_NAME);
	    SearchMap.put("LEADER_NAME", LEADER_NAME);
	    SearchMap.put("DEV_STEP", DEV_STEP);

	    SearchList.add(SearchMap);
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
	    // if(conn!=null) {conn.close();}
	}

	System.out.println("4444");
	sb.append("UPDATE WFINFO");
	sb.append("      SET STEP_NO = '2'  ");
	sb.append("            ,APPROVAL = 'finish' ");
	sb.append("            ,GR_NAME_2  = '' ");
	sb.append("            ,GR_DAY_2     = '' ");
	sb.append("            ,LEADER_DAY_2    = '' ");
	sb.append("            ,LEADER_NAME_2 = '' ");
	sb.append("            ,JB_DAY = '' ");
	sb.append("            ,JB_NAME = '' ");
	sb.append("            ,P_LEADER_DAY = '' ");
	sb.append("            ,P_LEADER_NAME = '' ");
	sb.append("            ,R_OWNER_DAY = '' ");
	sb.append("            ,R_OWNER_NAME = '' ");
	sb.append("  WHERE PK_WID ");
	sb.append(" in (select fk_wid from cost_request 	  ");
	sb.append("          where fk_pid 	  ");
	sb.append("             in (select pk_pid from cost_productinfo where (rev_no,pk_cr_group) in 	  ");
	sb.append("                    (select rev_no,pk_cr_group from cost_productinfo 	  ");
	sb.append("                      where pk_pid in (SELECT fk_pid FROM COST_REPORT WHERE CRP_GROUP = " + report_pk + "))))	  ");

	pstmt = null;
	try {
	    System.out.println(sb.toString());
	    System.out.println("5555");
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.executeUpdate();
	    System.out.println("6666");
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	return SearchList;

    }

    /**
     * 함수명 : RejectWfinfo 설명 : 그룹장 결재 반려 wfinfo 삭제
     * 
     * @param
     * @return boolean
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2015. 05. 12.
     */
    public boolean RejectWfinfo(String pk_wid) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt_wfInfo = null;
	PreparedStatement pstmt_wfRquest = null;
	PreparedStatement pstmt_wfWork = null;
	StringBuffer sb_wfInfo = new StringBuffer();
	StringBuffer sb_wfRquest = new StringBuffer();
	StringBuffer sb_wfWork = new StringBuffer();
	int complet = 0;

	sb_wfInfo.append(" DELETE FROM wfinfo WHERE pk_wid in (" + pk_wid + ") ");
	sb_wfRquest.append(" UPDATE cost_request SET fk_wid = '' WHERE fk_wid in (" + pk_wid + ") ");
	sb_wfWork.append(" UPDATE cost_work SET fk_wid = '' WHERE fk_wid in (" + pk_wid + ") ");

	try {
	    pstmt_wfInfo = conn.prepareStatement(sb_wfInfo.toString());
	    complet = pstmt_wfInfo.executeUpdate();

	    pstmt_wfRquest = conn.prepareStatement(sb_wfRquest.toString());
	    complet = pstmt_wfRquest.executeUpdate();

	    pstmt_wfWork = conn.prepareStatement(sb_wfRquest.toString());
	    complet = pstmt_wfWork.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb_wfInfo.delete(0, sb_wfInfo.length());
	    sb_wfRquest.delete(0, sb_wfInfo.length());
	    sb_wfWork.delete(0, sb_wfInfo.length());
	    if (pstmt_wfInfo != null) {
		pstmt_wfInfo.close();
	    }
	    if (pstmt_wfRquest != null) {
		pstmt_wfRquest.close();
	    }
	    if (pstmt_wfWork != null) {
		pstmt_wfWork.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	if (complet > 0) {
	    return true;
	} else {
	    return false;
	}
    }
}