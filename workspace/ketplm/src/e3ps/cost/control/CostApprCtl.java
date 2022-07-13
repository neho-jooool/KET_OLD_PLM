package e3ps.cost.control;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import wt.util.WTProperties;
//import e3ps.common.util.StringUtil;
import e3ps.cost.dao.CostApprDao;
import e3ps.cost.util.DBUtil;
import e3ps.cost.util.MSDBUtil;
import e3ps.cost.util.MailUtil;
import e3ps.cost.util.StringUtil;

public class CostApprCtl {
    String report_add_html = "";
    String report_add_html_2 = "";

    String co_subject = "";

    public boolean Appr_ver_1(String pk_cr_group, String rev_no, String team, String step_no, String group_case_count, String login_id,
	    String GroupJang_Id, String GroupJang_name) {// 개발담당자 결재
	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}

	String pjt_name = "";
	String f_name = "";
	String M_email = "";
	String L_email = "";
	String Le_name = "";
	boolean mail_ok = false;

	try {
	    ArrayList nameList = new ArrayList();
	    CostApprDao dao = new CostApprDao(plm_conn);

	    nameList = dao.SearchRepass(pk_cr_group, rev_no, team, GroupJang_name);
	    /*
	     * if (plm_conn != null) { plm_conn.close(); }
	     */

	    Hashtable nameHash = null;
	    if (nameList != null) {
		for (int i = 0; i < nameList.size(); i++) {
		    nameHash = (Hashtable) nameList.get(i);
		    pjt_name = (String) nameHash.get("pjt_name");
		    f_name = (String) nameHash.get("f_name");
		}
	    }

	    ArrayList MailList = new ArrayList();
	    try {
		dao = new CostApprDao(ep_conn);

		MailList = dao.SearchEmail(f_name, team, step_no, "", GroupJang_Id);

	    } catch (Exception e) {
		e.printStackTrace();
		e.getMessage();
	    }
	    /*
	     * if (ep_conn != null) { ep_conn.close(); }
	     */
	    Hashtable MailHash = null;
	    for (int i = 0; i < MailList.size(); i++) {
		MailHash = (Hashtable) MailList.get(i);
		M_email = (String) MailHash.get("M_email");
		L_email = (String) MailHash.get("L_email");
		System.out.println("L_email : " + L_email);
		Le_name = (String) MailHash.get("Le_name");
	    }
	    M_email = login_id + "@ket.com";
	    if (!"".equals(f_name)) {
		mail_ok = mail_call(M_email, L_email, f_name, Le_name, pjt_name, step_no, pk_cr_group, rev_no, group_case_count, "", "",
		        "", "", "");
		// mail_ok = true;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}
	return mail_ok;
    }

    public int GroupJangSearch(String f_name, String team, String step_no) {// 해당 팀에 그룹장 존재 여부
	Connection ep_conn = null;
	String Le_name = "";
	int cnt = 0;
	try {
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}

	ArrayList MailList = new ArrayList();
	try {
	    CostApprDao dao = new CostApprDao(ep_conn);
	    dao = new CostApprDao(ep_conn);

	    MailList = dao.SearchEmail(f_name, team, step_no, "", "");
	    Hashtable MailHash = null;
	    for (int i = 0; i < MailList.size(); i++) {
		MailHash = (Hashtable) MailList.get(i);
		Le_name = (String) MailHash.get("Le_name");
	    }
	    System.out.println(Le_name + " " + team + " " + f_name + " " + step_no);
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}
	if (!Le_name.equals("")) {
	    cnt = 1;
	}
	return cnt;
    }

    public ArrayList GroupJangList(String team) {
	// connection
	Connection conn = null;
	System.out.println("team : " + team);
	ArrayList GroupList = new ArrayList();
	try {
	    // connection creation
	    conn = MSDBUtil.getConnection();
	    CostApprDao Dao = new CostApprDao(conn);

	    // result
	    GroupList = Dao.SearchGroupJang(team);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    MSDBUtil.close(conn);
	}

	return GroupList;
    }

    public boolean Appr_ver_2(String pk_cr_group, String rev_no, String team, String step_no, String group_case_count) {// 그룹장 결재
	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	CostApprDao dao = new CostApprDao(plm_conn);
	String pjtinfo = dao.getSearchPjtinfo(pk_cr_group, rev_no);
	String[] parameters = null;
	parameters = pjtinfo.split(";");
	String f_name = parameters[0];
	String pjt_name = parameters[1];
	String G_name = parameters[3];
	f_name = f_name.trim();
	pjt_name = pjt_name.trim();
	String M_email = "";
	String L_email = "";
	String Le_name = "";

	boolean mail_ok = false;

	ArrayList MailList = new ArrayList();
	try {
	    dao = new CostApprDao(ep_conn);
	    // G_name = dao.SearchGname(team);
	    MailList = dao.SearchEmail(G_name, team, step_no, "", "");
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    dao = new CostApprDao(plm_conn);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	Hashtable MailHash = null;
	for (int i = 0; i < MailList.size(); i++) {
	    MailHash = (Hashtable) MailList.get(i);
	    M_email = (String) MailHash.get("M_email");
	    L_email = (String) MailHash.get("L_email");
	    Le_name = (String) MailHash.get("Le_name");
	}

	try {
	    dao = new CostApprDao(plm_conn);

	    dao.Appr_Gr(pk_cr_group, rev_no, G_name);
	    if (plm_conn != null) {
		plm_conn.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	mail_ok = mail_call(M_email, L_email, f_name, Le_name, pjt_name, step_no, pk_cr_group, rev_no, group_case_count, "", "", "", "", "");
	// mail_ok = true;
	return mail_ok;

    }

    public boolean Reject_ver_2(String pk_cr_group, String rev_no, String team, String step_no, String group_case_count) {// 그룹장 반려
	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	CostApprDao dao = new CostApprDao(plm_conn);
	String pjtinfo = dao.getSearchPjtinfo(pk_cr_group, rev_no);
	String[] parameters = null;
	parameters = pjtinfo.split(";");
	String f_name = parameters[0];
	String pjt_name = parameters[1];
	String G_name = parameters[3];
	String fk_wid = parameters[4];
	fk_wid = fk_wid.substring(1, (fk_wid.length() - 1));
	String[] widParameters = null;
	widParameters = fk_wid.split(",");
	fk_wid = "";
	for (int i = 0; i < widParameters.length; i++) {
	    if (i == (widParameters.length - 1)) {
		fk_wid = fk_wid + widParameters[i];
	    } else {
		fk_wid = fk_wid + widParameters[i] + ",";
	    }
	}

	f_name = f_name.trim();
	pjt_name = pjt_name.trim();
	fk_wid = fk_wid.trim();
	String M_email = "";
	String L_email = "";
	String Le_name = "";

	boolean mail_ok = false;

	boolean wfReset = false;

	try {
	    wfReset = dao.RejectWfinfo(fk_wid);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	ArrayList MailList = new ArrayList();
	try {
	    dao = new CostApprDao(ep_conn);
	    // G_name = dao.SearchGname(team);
	    MailList = dao.SearchEmail(G_name, team, "0", "", "");
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    dao = new CostApprDao(plm_conn);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	Hashtable MailHash = null;
	for (int i = 0; i < MailList.size(); i++) {
	    MailHash = (Hashtable) MailList.get(i);
	    M_email = (String) MailHash.get("M_email");
	    L_email = (String) MailHash.get("L_email");
	    Le_name = (String) MailHash.get("Le_name");
	}

	mail_ok = mail_call(M_email, L_email, f_name, Le_name, pjt_name, "R", pk_cr_group, rev_no, group_case_count, "", "", "", "", "");

	// mail_ok = true;
	return mail_ok;

    }

    public boolean Appr_ver_3(String pk_cr_group, String rev_no, String team, String step_no, String group_case_count, String leader_name)
	    throws UnsupportedEncodingException {// 팀장 결재

	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	CostApprDao dao = new CostApprDao(plm_conn);
	String pjtinfo = dao.getSearchPjtinfo(pk_cr_group, rev_no);
	String[] parameters = null;
	parameters = pjtinfo.split(";");
	String f_name = parameters[0];
	String pjt_name = parameters[1];
	String a_name = parameters[2];
	f_name = f_name.trim();
	pjt_name = pjt_name.trim();
	a_name = a_name.trim();

	String M_email = "";
	String L_email = "";
	String Le_name = "";
	leader_name = java.net.URLDecoder.decode(leader_name, "UTF-8");

	boolean mail_ok = false;

	ArrayList MailList = new ArrayList();
	try {
	    dao.Appr_leader(pk_cr_group, rev_no, leader_name);
	    dao = new CostApprDao(ep_conn);
	    MailList = dao.SearchEmail(f_name, team, step_no, a_name, "");
	    // if (plm_conn != null) {
	    // plm_conn.close();
	    // }
	    // if (ep_conn != null) {
	    // ep_conn.close();
	    // }
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	Hashtable MailHash = null;
	for (int i = 0; i < MailList.size(); i++) {
	    MailHash = (Hashtable) MailList.get(i);
	    M_email = (String) MailHash.get("M_email");
	    L_email = (String) MailHash.get("L_email");
	}

	mail_ok = mail_call(M_email, L_email, f_name, Le_name, pjt_name, step_no, pk_cr_group, rev_no, group_case_count, a_name, "", "",
	        "", "");
	mail_ok = true;
	return mail_ok;

    }

    public boolean Appr_reject(String pk_cr_group, String rev_no, String team, String L_email, String Le_name, String group_case_count,
	    String consent_txt) {// 팀장의 요청서 반려
	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	CostApprDao dao = new CostApprDao(plm_conn);
	String pjtinfo = "";
	String f_name = "";
	String pjt_name = "";
	try {
	    pjtinfo = dao.AprovalReject(pk_cr_group, rev_no);
	    String[] parameters = null;
	    parameters = pjtinfo.split(";");
	    f_name = parameters[0];
	    pjt_name = parameters[1];
	    f_name = f_name.trim();
	    pjt_name = pjt_name.trim();
	    if (plm_conn != null) {
		plm_conn.close();
	    }
	} catch (Exception e1) {
	    e1.printStackTrace();
	}

	String M_email = "";

	boolean mail_ok = false;

	try {
	    dao = new CostApprDao(ep_conn);
	    M_email = dao.SearchF_email(f_name, team);
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    // dao = new CostApprDao(plm_conn);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	mail_ok = mail_call(M_email, L_email, f_name, Le_name, pjt_name, "99", pk_cr_group, rev_no, group_case_count, "", consent_txt, "",
	        "", "");
	return mail_ok;

    }

    public boolean change_w_name(String pk_cr_group, String rev_no, String w_name, String group_case_count, String dept_no) {// 산출 담당자 지정
	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	String pjt_name = "";
	String f_name = "";
	String team = "";

	try {
	    w_name = java.net.URLDecoder.decode(w_name, "utf-8");
	} catch (UnsupportedEncodingException e1) {
	    e1.printStackTrace();
	}
	CostApprDao dao = new CostApprDao(plm_conn);

	try {
	    ArrayList SearchList = dao.ChangeWname(w_name, pk_cr_group, rev_no);
	    Hashtable searchMap = null;
	    for (int i = 0; i < SearchList.size(); i++) {
		searchMap = (Hashtable) SearchList.get(i);
		pjt_name = (String) searchMap.get("pjt_name");
		f_name = (String) searchMap.get("f_name");
		team = (String) searchMap.get("team");
	    }
	    if (plm_conn != null) {
		plm_conn.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	dept_no = StringUtil.ChangeDeptNoCP(team);
	/*
	 * if (team.equals("1")) { // team = "11735";//연구개발1팀 dept_no = "11721";// 커넥터연구개발1팀 } else if (team.equals("11")) { dept_no =
	 * "11722";// 커넥터연구개발2팀 } else if (team.equals("12")) { dept_no = "11723";// 커넥터연구개발3팀 } else if (team.equals("13")) { dept_no =
	 * "11681";// 커넥터연구개발센타 } else if (team.equals("22")) { dept_no = "11724";// 전장모듈연구개발1팀 } else if (team.equals("23")) { dept_no =
	 * "11725";// 전장모듈연구개발2팀 } else if (team.equals("24")) { dept_no = "11726";// 전장모듈연구개발3팀 } else if (team.equals("3")) { dept_no =
	 * "11737";// 연구개발3팀 } else if (team.equals("4")) { dept_no = "11738";// 연구개발4팀 } else if (team.equals("6")) { dept_no = "11740";//
	 * 연구개발6팀 } else if (team.equals("21")) { dept_no = "11741";// 시작개발1팀 }
	 */
	String f_email = "";
	String M_email = "";
	try {
	    dao = new CostApprDao(ep_conn);
	    M_email = dao.SearchF_email2(w_name);
	    f_email = dao.SearchF_email(f_name, dept_no);
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    // dao = new CostApprDao(plm_conn);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	boolean mail_ok = false;
	mail_ok = mail_call(M_email, f_email, f_name, w_name, pjt_name, "98", pk_cr_group, rev_no, group_case_count, "", "", "", "", "");
	return mail_ok;
    }

    public boolean Appr_reject_work(String pk_cr_group, String rev_no, String team, String group_case_count, String pps_note, String dept_no) {// 팀장의
	                                                                                                                                       // 요청서
	                                                                                                                                       // 반려
	Connection plm_conn = null;
	Connection ep_conn = null;

	dept_no = StringUtil.ChangeDeptNoCP(team);
	/*
	 * if (team == "1") { dept_no = "11721";// 커넥터연구개발1팀 } else if (team == "11") { dept_no = "11722";// 커넥터연구개발2팀 } else if (team ==
	 * "12") { dept_no = "11723";// 커넥터연구개발3팀 } else if (team == "13") { dept_no = "11681";// 커넥터연구개발센타 } else if (team == "22") {
	 * dept_no = "11724";// 전장모듈연구개발1팀 } else if (team == "23") { dept_no = "11725";// 전장모듈연구개발2팀 } else if (team == "24") { dept_no =
	 * "11726";// 전장모듈연구개발3팀 } else if (team == "3") { dept_no = "11737";// 연구개발3팀 } else if (team == "4") { dept_no = "11738";// 연구개발4팀
	 * } else if (team == "6") { dept_no = "11740";// 연구개발6팀 } else if (team == "21") { dept_no = "11741";// 시작개발1팀 }
	 */

	try {
	    pps_note = java.net.URLDecoder.decode(pps_note, "utf-8");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}

	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	CostApprDao dao = new CostApprDao(plm_conn);
	String pjtinfo = "";
	String pjt_name = "";
	String f_name = "";
	String L_name = "";
	try {
	    System.out.println("1");
	    pjtinfo = dao.Reject_work(pk_cr_group, rev_no, pps_note);
	    System.out.println("2");
	    String[] parameters = null;
	    parameters = pjtinfo.split(";");
	    pjt_name = parameters[0];
	    f_name = parameters[1];
	    L_name = parameters[2];
	    System.out.println("3");
	    pjt_name = pjt_name.trim();
	    f_name = f_name.trim();
	    L_name = L_name.trim();
	    System.out.println("f_name==>" + f_name);
	    System.out.println("f_name==>" + L_name);
	    System.out.println("4");
	    if (plm_conn != null) {
		plm_conn.close();
	    }
	} catch (Exception e1) {
	    e1.printStackTrace();
	}

	String f_email = "";
	String L_email = "";

	boolean mail_ok = false;

	try {
	    dao = new CostApprDao(ep_conn);
	    System.out.println("team==>" + team);
	    f_email = dao.SearchF_email(f_name, dept_no);
	    L_email = dao.SearchF_email(L_name, dept_no);
	    System.out.println("f_email==>" + f_email);
	    System.out.println("L_email==>" + L_email);
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    dao = new CostApprDao(plm_conn);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	mail_ok = mail_call(f_email, L_email, f_name, L_name, pjt_name, "97", pk_cr_group, rev_no, group_case_count, "", pps_note, "", "",
	        "");
	return mail_ok;

    }

    public boolean Appr_report_ver_1(String report_pk, String pk_cr_group, String rev_no, String Ename, String team, String table_row,
	    String user_case_count) {// 보고서 담당자 결재
	Connection plm_conn = null;
	Connection ep_conn = null;

	String pjt_name = "";
	String M_email = "";
	String L_email = "";
	String Le_name = "";
	boolean mail_ok = false;

	try {
	    plm_conn = DBUtil.getConnection(false);
	    ep_conn = MSDBUtil.getConnection();
	    CostApprDao dao = new CostApprDao(plm_conn);
	    pjt_name = dao.ReportAppr_1(report_pk, pk_cr_group, rev_no, Ename);
	    /*
	     * if (plm_conn != null) { plm_conn.close(); }
	     */
	    ArrayList MailList = new ArrayList();

	    dao = new CostApprDao(ep_conn);

	    MailList = dao.SearchEmail(Ename, team, "0.5", "", "");

	    Hashtable MailHash = null;
	    for (int i = 0; i < MailList.size(); i++) {
		MailHash = (Hashtable) MailList.get(i);
		M_email = (String) MailHash.get("M_email");
		L_email = (String) MailHash.get("L_email");
		Le_name = (String) MailHash.get("Le_name");
	    }
	    mail_ok = mail_call(M_email, L_email, Ename, Le_name, pjt_name, "96", pk_cr_group, rev_no, "", "", "", table_row,
		    user_case_count, report_pk);

	    plm_conn.commit();

	} catch (Exception e) {
	    try {
		plm_conn.rollback();
	    } catch (SQLException e1) {
		e1.printStackTrace();
	    }
	    e.printStackTrace();
	} finally {
	    if (ep_conn != null) {
		try {
		    ep_conn.close();
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    if (plm_conn != null) {
		DBUtil.close(plm_conn);
	    }

	}
	return mail_ok;
    }

    public boolean Appr_report_ver_2(String pk_cr_group, String cost_report_add, String user_case_count, String report_pk,
	    String table_row, String team, String rev_no, String Ename, String step_no, String L_email, String consent_txt, String pass_type) {// 보고서
	                                                                                                                                       // 팀장
	                                                                                                                                       // 결재

	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}

	String PJT_NAME = "";
	String W_NAME = "";
	String F_NAME = "";
	String LEADER_NAME = "";
	String A_NAME = "";

	String M_email = "";
	String f_email = "";
	String FL_email = "";
	String a_email = "";
	String ALe_email = "";
	String ALe_name = "";

	String team_gubun_name = "";
	String team_gubun_mail = "";
	boolean mail_ok = false;
	ArrayList ReportList = new ArrayList();
	CostApprDao dao = null;
	try {
	    
	    dao = new CostApprDao(plm_conn);
	    ReportList = dao.ReportAppr_2(report_pk, pk_cr_group, rev_no, Ename, step_no, consent_txt, pass_type);

	    Hashtable MailHash = null;
	    
	    for (int i = 0; i < ReportList.size(); i++) {
		MailHash = (Hashtable) ReportList.get(i);
		PJT_NAME = (String) MailHash.get("PJT_NAME");
		W_NAME = (String) MailHash.get("W_NAME");
		F_NAME = (String) MailHash.get("F_NAME");
		LEADER_NAME = (String) MailHash.get("LEADER_NAME");
		A_NAME = (String) MailHash.get("A_NAME");
	    }
	    

	    ArrayList MailList = new ArrayList();
	    dao = new CostApprDao(ep_conn);

	    MailList = dao.SearchReportEmail(W_NAME, F_NAME, team, LEADER_NAME, A_NAME);

	    

	    Hashtable MailHash2 = null;
	    for (int i = 0; i < MailList.size(); i++) {
		MailHash2 = (Hashtable) MailList.get(i);
		M_email = (String) MailHash2.get("M_email");
		f_email = (String) MailHash2.get("f_email");
		FL_email = (String) MailHash2.get("FL_email");
		a_email = (String) MailHash2.get("a_email");
		ALe_email = (String) MailHash2.get("ALe_email");
		ALe_name = (String) MailHash2.get("ALe_name");
	    }
	    
	    String Le_name = Ename;
	    String to_email = "";
	    String to_name = "";
	    System.out.println("여기~~~~~~" + M_email + " ... " + f_email + "......" + FL_email + "..." + a_email + ".." + ALe_email + "...."
		    + ALe_name);
	    if (pass_type.equals("6")) {
		report_add_html = "<td height=23><font size=2 color=blue ><strong>※ 상기품은 센터장 전결본으로 전결사유는</strong></font></td></tr>";
		report_add_html = report_add_html + "<td height=23><font size=2 color=blue ><strong>'" + consent_txt
		        + "' 입니다.</strong></font></td></tr>";
	    }
	    if (pass_type.equals("7")) {
		report_add_html = "<td height=23><font size=2 color=blue ><strong>※ 상기품은 팀장 전결본으로 전결사유는</strong></font></td></tr>";
		report_add_html = report_add_html + "<td height=23><font size=2 color=blue ><strong>'" + consent_txt
		        + "' 입니다.</strong></font></td></tr>";
	    }
	    if (pass_type.equals("6") || pass_type.equals("7")) {
		co_subject = " - 원가산출결과 최종배포 ";
	    }
	    if (team.equals("11721") || team.equals("11722") || team.equals("11723") || team.equals("11735") || team.equals("11739")
		    || team.equals("11681")) {
		team_gubun_name = F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";김석;";
		team_gubun_mail = f_email+ ";"+ M_email+ ";"+ a_email+ ";"+ FL_email+ ";"+ ALe_email
		        + ";leejh@ket.com;ois@ket.com;kmlee@ket.com;pch61@ket.com;kimosik@ket.com;shyun@ket.com;babiss@ket.com;briandsk@ket.com;neho@ket.com;";
	    } else {
		team_gubun_name = F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";김석;";
		team_gubun_mail = f_email+ ";"+ M_email+ ";"+ a_email+ ";"+ FL_email+ ";"+ ALe_email
			+ ";leejh@ket.com;ois@ket.com;kmlee@ket.com;kimosik@ket.com;jungchul0312@ket.com;wlgnslgg@ket.com;babiss@ket.com;neho@ket.com;kimseok@ket.com";

	    }
	    // step_no = 4 센터장 step_no = 3 팀장
	    if (step_no.equals("4")) { // 센터장 결재선 누락으로 인해 팀장 결재시 센타장 결재와 똑같은 프로세스를 타게 함 ..추후 센터장이 추가되면
		                       // step_no.equals("3") 없앨 것 2014.03.12
		to_email = team_gubun_mail;
		to_name = team_gubun_name;
		mail_ok = mail_call(to_email, FL_email, "", to_name, PJT_NAME, "94", pk_cr_group, rev_no, "", "", "", table_row,
		        user_case_count, report_pk);
	    } else if (step_no.equals("3")) {
		mail_ok = mail_call(M_email, L_email, "", Le_name, PJT_NAME, "95", pk_cr_group, rev_no, "", "", "", table_row,
		        user_case_count, report_pk);
	    }
	    plm_conn.commit();
	    
	} catch (Exception e) {
	    try {
		plm_conn.rollback();
	    } catch (SQLException e1) {
		e1.printStackTrace();
	    }
	    e.printStackTrace();
	} finally {
	    if (ep_conn != null) {
		try {
		    ep_conn.close();
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    if (plm_conn != null) {
		DBUtil.close(plm_conn);
	    }

	}

	return mail_ok;
    }

    public boolean Appr_report_ver_3(String table_row, String team, String step_no, String pass_type, String consent_txt, String report_pk,
	    String pk_cr_group, String rev_no, String JB_day, String p_leader_day, String r_owner_day, String r_pre_day, String note_4,
	    String Ename, String L_email) {// 보고서배포

	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}

	String PJT_NAME = "";
	String W_NAME = "";
	String F_NAME = "";
	String LEADER_NAME = "";
	String A_NAME = "";
	String CASE_TYPE_USER = "";
	String DEV_STEP = "";

	String M_email = "";
	String f_email = "";
	String FL_email = "";
	String a_email = "";
	String ALe_email = "";
	String ALe_name = "";

	boolean mail_ok = false;

	ArrayList ReportList = new ArrayList();
	CostApprDao dao = new CostApprDao(plm_conn);
	try {
	    ReportList = dao.ReportAppr_3(step_no, pass_type, consent_txt, report_pk, pk_cr_group, rev_no, JB_day, p_leader_day,
		    r_owner_day, r_pre_day, Ename);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	Hashtable MailHash = null;
	for (int i = 0; i < ReportList.size(); i++) {
	    MailHash = (Hashtable) ReportList.get(i);
	    PJT_NAME = (String) MailHash.get("PJT_NAME");
	    W_NAME = (String) MailHash.get("W_NAME");
	    F_NAME = (String) MailHash.get("F_NAME");
	    LEADER_NAME = (String) MailHash.get("LEADER_NAME");
	    A_NAME = (String) MailHash.get("A_NAME");
	    CASE_TYPE_USER = (String) MailHash.get("CASE_TYPE_USER");
	    DEV_STEP = (String) MailHash.get("DEV_STEP");
	}
	try {
	    if (plm_conn != null) {
		plm_conn.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	ArrayList MailList = new ArrayList();
	try {
	    dao = new CostApprDao(ep_conn);

	    MailList = dao.SearchReportEmail(W_NAME, F_NAME, team, LEADER_NAME, A_NAME);

	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	Hashtable MailHash2 = null;
	for (int i = 0; i < MailList.size(); i++) {
	    MailHash2 = (Hashtable) MailList.get(i);
	    M_email = (String) MailHash2.get("M_email");
	    f_email = (String) MailHash2.get("f_email");
	    FL_email = (String) MailHash2.get("FL_email");
	    a_email = (String) MailHash2.get("a_email");
	    ALe_email = (String) MailHash2.get("ALe_email");
	    ALe_name = (String) MailHash2.get("ALe_name");
	}

	try {
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}
	String Le_name = Ename;
	String to_email = "";
	String to_name = "";
	System.out.println("여기~~~~~~" + M_email + " ... " + f_email + "......" + FL_email + "..." + a_email + ".." + ALe_email + "...."
	        + ALe_name);

	// to_email = f_email +";"+ M_email +";"+ a_email +";" + FL_email +";"+ ALe_email
	// +";jbhong@ket.com;suk@ket.com;leejh@ket.com;ois@ket.com;kmlee@ket.com;kji@ket.com;hslim@ket.com;babiss@ket.com;";
	// to_name = F_NAME +";"+LEADER_NAME +";"+A_NAME +";"+ALe_name +";"+W_NAME+";홍종범;" ;

	to_email = M_email
	        + ";leejh@ket.com;ois@ket.com;kmlee@ket.com;pch61@ket.com;kimosik@ket.com;shyun@ket.com;babiss@ket.com;briandsk@ket.com;neho@ket.com;";

	/*
	 * if(pass_type.equals("1")){ report_add_html
	 * ="<td height=23><font size=2 color=blue ><strong>※ 상기품은 팀장 전결본으로 전결사유는</strong></font></td></tr>"; report_add_html =
	 * report_add_html + "<td height=23><font size=2 color=blue ><strong>'"+ consent_txt +"' 입니다.</strong></font></td></tr>"; }
	 * if(pass_type.equals("2")){ report_add_html
	 * ="<td height=23><font size=2 color=blue ><strong>※ 상기품은 본부장 전결본으로 전결사유는</strong></font></td></tr>"; report_add_html =
	 * report_add_html + "<td height=23><font size=2 color=blue ><strong>'"+ consent_txt +"' 입니다.</strong></font></td></tr>"; }
	 */
	note_4.replace(System.getProperty("line.separator"), "");
	if (pass_type.equals("5")) {
	    note_4 = "";
	}
	if (pass_type.equals("4")) {
	    report_add_html_2 = "<td height=23><font size=2 color=blue><strong>■ 경영층 지시사항 : </strong>" + consent_txt + "</font></td></tr>";
	}
	if (pass_type.equals("2")) {// 본부장 결재
	    pass_type = "93";
	    to_email += "sblee@ket.com;";
	    to_name += "김석";
	    L_email = "briandsk@ket.com";
	} else if (pass_type.equals("3")) {// 부문장 결재
	    pass_type = "93_1";
	    to_email += "ketcolwj@ket.com;";
	    to_name += "이원준";
	    L_email = "kimseok@ket.com";
	} else if (pass_type.equals("4")) {// 사장 결재
	    pass_type = "93_2";

	    if (DEV_STEP.equals("개발검토")) {
		if (team.equals("11721") || team.equals("11722") || team.equals("11723") || team.equals("11735") || team.equals("11739")
		        || team.equals("11681")) {
		    to_email += f_email + ";" + M_email + ";" + a_email + ";" + FL_email + ";" + ALe_email + ";kimseok@ket.com;";
		    to_name += F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";김석;";
		} else {
		    to_email += f_email + ";" + M_email + ";" + a_email + ";" + FL_email + ";" + ALe_email + ";kimseok@ket.com;";
		    to_name += F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";김석;";
		}
	    }
	    L_email = "ketcolwj@ket.com";
	    // to_email += "briandsk@ket.com;sblee@ket.com;ketcolwj@;";
	    // to_name += "김동식;이성범;이원준;";
	} else if (pass_type.equals("5")) {// 회장 수기 결재
	    pass_type = "93_2";
	    if (team.equals("11721") || team.equals("11722") || team.equals("11723") || team.equals("11735") || team.equals("11736")
		    || team.equals("11739") || team.equals("11681")) {
		to_email += f_email + ";" + M_email + ";" + a_email + ";" + FL_email + ";" + ALe_email + ";briandsk@ket.com;";
		to_name += F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";김석;";
	    } else {
		to_email += f_email + ";" + M_email + ";" + a_email + ";" + FL_email + ";" + ALe_email
		        + ";dschoi@ket.com;briandsk@ket.com;";
		to_name += F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";김석;";
	    }
	}

	mail_ok = mail_call(to_email, L_email, "", to_name, PJT_NAME, pass_type, pk_cr_group, rev_no, "", "", "", table_row,
	        CASE_TYPE_USER, report_pk);

	return mail_ok;
    }

    public boolean Appr_reportReject(String table_row, String team, String step_no, String pass_type, String consent_txt, String report_pk,
	    String pk_cr_group, String rev_no, String user_case_count, String Ename, String L_email) {// 보고서 반려

	Connection plm_conn = null;
	Connection ep_conn = null;
	boolean mail_ok = false;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();

	    String PJT_NAME = "";
	    String W_NAME = "";
	    String F_NAME = "";
	    String LEADER_NAME = "";
	    String A_NAME = "";
	    String DEV_STEP = "";

	    String M_email = "";
	    String f_email = "";
	    String FL_email = "";
	    String a_email = "";
	    String ALe_email = "";
	    String ALe_name = "";

	    ArrayList ReportList = new ArrayList();
	    CostApprDao dao = new CostApprDao(plm_conn);

	    ReportList = dao.ReportLeaderReturn(report_pk, pk_cr_group, rev_no);

	    Hashtable MailHash = null;
	    for (int i = 0; i < ReportList.size(); i++) {
		MailHash = (Hashtable) ReportList.get(i);
		PJT_NAME = (String) MailHash.get("PJT_NAME");
		W_NAME = (String) MailHash.get("W_NAME");
		F_NAME = (String) MailHash.get("F_NAME");
		A_NAME = (String) MailHash.get("A_NAME");
		LEADER_NAME = (String) MailHash.get("LEADER_NAME");
		DEV_STEP = (String) MailHash.get("DEV_STEP");
	    }

	    ArrayList MailList = new ArrayList();
	    dao = new CostApprDao(ep_conn);

	    MailList = dao.SearchReportEmail(W_NAME, F_NAME, team, LEADER_NAME, A_NAME);

	    Hashtable MailHash2 = null;
	    for (int i = 0; i < MailList.size(); i++) {
		MailHash2 = (Hashtable) MailList.get(i);
		M_email = (String) MailHash2.get("M_email");
		f_email = (String) MailHash2.get("f_email");
		FL_email = (String) MailHash2.get("FL_email");
		a_email = (String) MailHash2.get("a_email");
		ALe_email = (String) MailHash2.get("ALe_email");
		ALe_name = (String) MailHash2.get("ALe_name");
	    }

	    String Le_name = Ename;
	    String to_email = "";
	    String to_name = "";
	    System.out.println("여기~~~~~~" + M_email + " ... " + f_email + "......" + FL_email + "..." + a_email + ".." + ALe_email + "...."
		    + ALe_name);

	    // to_email += "briandsk@ket.com;sblee@ket.com;";
	    // to_name += "김동식;이성범;";

	    if (pass_type.equals("no_2")) {// 본부장
		to_email = f_email + ";" + M_email + ";" + a_email + ";" + FL_email + ";" + ALe_email + ";neho@ket.com;";
		to_name = F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";";
		// report_add_html ="<font size=2 color=red>다음과 같이 <b>1차 배포되었던 "+ PJT_NAME +"의 원가결과보고서가</b> <br> 반려되어 알려드립니다.</font>";
		report_add_html = "<font size=2 color=red>다음과 같이 " + PJT_NAME + "의 원가결과보고서가 반려되었습니다.</font>";
		mail_ok = mail_call(to_email, L_email, "", to_name, PJT_NAME, "92", pk_cr_group, rev_no, "", "", consent_txt, table_row,
		        user_case_count, report_pk);
	    } else if (pass_type.equals("no_3")) {// 부문장
		to_email = f_email + ";" + M_email + ";" + a_email + ";" + FL_email + ";" + ALe_email + ";neho@ket.com;";
		to_name = F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";";
		// report_add_html ="<font size=2 color=red>다음과 같이 <b>2차 배포되었던 "+ PJT_NAME +"의 원가결과보고서가</b> <br> 반려되어 알려드립니다.</font>";
		report_add_html = "<font size=2 color=red>다음과 같이 " + PJT_NAME + "의 원가결과보고서가 반려되었습니다.</font>";
		mail_ok = mail_call(to_email, L_email, "", to_name, PJT_NAME, "92", pk_cr_group, rev_no, "", "", consent_txt, table_row,
		        user_case_count, report_pk);
	    } else if (pass_type.equals("no_4")) {// 사장
		if ("개발검토".equals(DEV_STEP)) {
		    if (team.equals("11721") || team.equals("11722") || team.equals("11723") || team.equals("11735")
			    || team.equals("11739") || team.equals("11681")) {
			to_email = f_email
			        + ";"
			        + M_email
			        + ";"
			        + a_email
			        + ";"
			        + FL_email
			        + ";"
			        + ALe_email
			        + ";leejh@ket.com;ois@ket.com;kmlee@ket.com;kimosik@ket.com;jungchul0312@ket.com;wlgnslgg@ket.com;neho@ket.com;";
			to_name = F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";김석;";
		    } else {
			to_email = f_email
			        + ";"
			        + M_email
			        + ";"
			        + a_email
			        + ";"
			        + FL_email
			        + ";"
			        + ALe_email
			        + ";leejh@ket.com;ois@ket.com;kmlee@ket.com;kimosik@ket.com;jungchul0312@ket.com;wlgnslgg@ket.com;neho@ket.com;";
			to_name = F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";김석;";
		    }
		} else {
		    to_email = f_email
			    + ";"
			    + M_email
			    + ";"
			    + a_email
			    + ";"
			    + FL_email
			    + ";"
			    + ALe_email
			    + ";leejh@ket.com;ois@ket.com;kmlee@ket.com;kimosik@ket.com;jungchul0312@ket.com;wlgnslgg@ket.com;neho@ket.com;";
		    to_name = F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";";
		}
		// report_add_html ="<font size=2 color=red>다음과 같이 <b>3차 배포되었던 "+ PJT_NAME +"의 원가결과보고서가</b> <br> 반려되어 알려드립니다.</font>";
		report_add_html = "<font size=2 color=red>다음과 같이 " + PJT_NAME + "의 원가결과보고서가 반려되었습니다.</font>";
		mail_ok = mail_call(to_email, L_email, "", to_name, PJT_NAME, "92", pk_cr_group, rev_no, "", "", consent_txt, table_row,
		        user_case_count, report_pk);
	    } else {// 팀장, 센터장
		to_email = f_email + ";" + M_email + ";" + a_email + ";" + FL_email + ";" + ALe_email + ";neho@ket.com;";
		to_name = F_NAME + ";" + LEADER_NAME + ";" + A_NAME + ";" + ALe_name + ";" + W_NAME + ";";
		report_add_html = "<font size=2 color=red>다음과 같이 " + PJT_NAME + "의 원가결과보고서가 반려되었습니다.</font>";
		mail_ok = mail_call(to_email, L_email, "", W_NAME, PJT_NAME, "92", pk_cr_group, rev_no, "", "", consent_txt, table_row,
		        user_case_count, report_pk);
	    }
	    plm_conn.commit();

	} catch (Exception e) {
	    try {
		plm_conn.rollback();
	    } catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	} finally {
	    try {
		if (ep_conn != null) {
		    ep_conn.close();
		}
		DBUtil.close(plm_conn);
	    } catch (Exception e) {
		e.printStackTrace();
		e.getMessage();
	    }
	}

	return mail_ok;
    }

    public boolean mail_call(String M_email, String L_email, String f_name, String Le_name, String pjt_name, String step_no,
	    String pk_cr_group, String rev_no, String group_case_count, String a_name, String consent_txt, String table_row,
	    String user_case_count, String report_pk) {

	MailUtil mail = new MailUtil();

	boolean mail_send = false;

	Date date;
	SimpleDateFormat formatter;
	String pattern = "yyyy년 M월 d일 a h시 m분";
	String now_date;
	formatter = new SimpleDateFormat(pattern, new Locale("ko", "KOREA"));
	date = new Date();
	now_date = formatter.format(date);
	String host = "";
	try {
	    host = WTProperties.getLocalProperties().getProperty("wt.server.codebase");
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	// String host = "http://" + hostName;
	// host = "http://plmapdev.ket.com";
	// String cost_id = L_email.substring(0, L_email.indexOf("@"));
	String cost_id = "";
	String link_url = "";
	String link_url_2 = "";
	String subject = "";
	String subject_2 = "";
	String from = "";// 받는주소
	String email_to = "";
	String html = "";
	String to_name = "";

	if (step_no.equals("0") || step_no.equals("0.5")) {// 개발자 또는 그룹장 결재
	    String urlStep = "";
	    if (step_no.equals("0")) {
		urlStep = "0.5";
	    }
	    if (step_no.equals("0.5")) {
		urlStep = "1";
	    }

	    // subject = "[알림]- 원가의뢰요청서 결재요망";
	    subject = pjt_name + " - 원가의뢰요청서 결재요망";

	    from = M_email;

	    email_to = M_email + ";" + L_email + ";"; // 보내는 주소
	    email_to = email_to + "babiss@ket.com;neho@ket.com";

	    to_name = f_name + ";" + Le_name + ";";

	    System.out.println("from===>" + from);
	    System.out.println("email_to==>" + email_to);
	    System.out.println("to_name==>" + to_name);

	    // from = "neho@ket.com";
	    // email_to = "neho@ket.com";

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costdb/cost_re_view_test.jsp?visitor=1@step_no=" + urlStep + "@pk_cr_group=" + pk_cr_group
		        + "@rev_no=" + rev_no + "@data_type=main@group_case_count=" + group_case_count;

		html = "<html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가의뢰요청서 결재를 요청합니다.</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "</table></body></html> ";
		if (!"".equals(cost_id)) {
		    mail_send = mail.sendMail(from, cost_id, subject, html);
		}
	    }

	}

	// 그룹장 반려
	if ("R".equals(step_no)) {// 개발자 또는 그룹장 결재
	    // subject = "[알림]- 원가의뢰요청서 반려";
	    subject = pjt_name + " - 원가의뢰요청서 반려";

	    from = L_email;

	    email_to = M_email + ";babiss@ket.com;neho@ket.com";

	    to_name = f_name;

	    System.out.println("from===>" + from);
	    System.out.println("email_to==>" + email_to);
	    System.out.println("to_name==>" + to_name);

	    // from = "neho@ket.com";
	    // email_to = "neho@ket.com";

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costdb/cost_re_view_test.jsp?visitor=1@step_no=0@pk_cr_group=" + pk_cr_group + "@rev_no=" + rev_no
		        + "@data_type=main@group_case_count=" + group_case_count;

		html = "<html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가의뢰요청서가 반려되었습니다.</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "</table></body></html> ";

		mail_send = true;
		if (!"".equals(cost_id)) {
		    mail_send = mail.sendMail(from, cost_id, subject, html);
		}
	    }
	}

	if (step_no.equals("1")) {// 팀장 결재

	    // subject = "[알림]- 원가산출요청";
	    subject = pjt_name + " - 원가산출요청";
	    from = M_email;
	    email_to = "kimosik@ket.com;jungchul0312@ket.com;wlgnslgg@ket.com;kmlee@ket.com;pch61@ket.com;leejh@ket.com;ois@ket.com";
	    if (!StringUtil.checkNull(L_email).equals("")) {
		email_to = email_to + ";" + L_email;
	    }
	    if (!StringUtil.checkNull(M_email).equals("")) {
		email_to = email_to + ";" + M_email;
	    }
	    email_to = email_to + ";babiss@ket.com;neho@ket.com";
	    to_name = a_name + ";" + f_name + "김호식; 김정철; 박찬호; 신지훈; 이경무;오인석;이정환;";

	    System.out.println("from===>" + from);
	    System.out.println("email_to==>" + email_to);
	    System.out.println("to_name==>" + to_name);

	    // from = "neho@ket.com";
	    // email_to = "neho@ket.com";

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costdb/cost_re_view_test.jsp?visitor=1@step_no=2@pk_cr_group=" + pk_cr_group + "@rev_no=" + rev_no
		        + "@data_type=main@group_case_count=" + group_case_count;
		html = " <html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + "의 원가산출을 요청합니다.</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "</table></body></html> ";
		if (!"".equals(cost_id)) {
		    mail_send = mail.sendMail(from, cost_id, subject, html);
		}
	    }
	}

	if (step_no.equals("99")) {// 팀장의 요청서 반려
	    String txt = "";
	    try {
		txt = java.net.URLDecoder.decode(consent_txt, "utf-8");
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }

	    // subject = "[알림]- 원가의뢰요청서 반려";
	    subject = "[반려] " + pjt_name + " 원가의뢰요청서가 반려되었습니다.";
	    from = M_email;
	    email_to = L_email + ";" + M_email + ";";
	    email_to = email_to + "babiss@ket.com";
	    to_name = a_name + ";" + f_name;

	    System.out.println("from===>" + from);
	    System.out.println("email_to==>" + email_to);
	    System.out.println("to_name==>" + to_name);

	    // from = "neho@ket.com";
	    // email_to = "neho@ket.com";

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costdb/cost_re_view_test.jsp?step_no=0@visitor=2@pk_cr_group=" + pk_cr_group + "@rev_no=" + rev_no
		        + "@data_type=main@group_case_count=" + group_case_count;

		html = " <html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + "의 원가의뢰요청서 반려되었습니다.</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 의견 : </strong>" + txt + "</font></td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "</table></body></html> ";
		if (!"".equals(cost_id)) {
		    mail_send = mail.sendMail(from, cost_id, subject, html);
		}
	    }
	}

	if (step_no.equals("98")) {// 산출 담당자 지정

	    subject = "[알림]- 원가계산요청 알림";
	    from = M_email;
	    email_to = M_email + ";" + L_email + ";";
	    email_to = email_to + "babiss@ket.com";
	    to_name = Le_name + ";" + f_name + ";";
	    System.out.println("email_to==>" + email_to);
	    System.out.println("to_name==>" + to_name);
	    // email_to = "neho@ket.com";

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costdb/cost_re_view_test.jsp?visitor=5@step_no=0@pk_cr_group=" + pk_cr_group + "@rev_no=" + rev_no
		        + "@data_type=main@group_case_count=" + group_case_count;

		html = " <html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name
		        + "의 원가계산요청서가 접수되어 전달하오니 업무진행바랍니다.</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "</table></body></html> ";
		if (!"".equals(cost_id)) {
		    mail_send = mail.sendMail(from, cost_id, subject, html);
		}
	    }
	}

	if (step_no.equals("97")) {// 원가요청서 거부

	    subject = "[알림]- 원가계산요청서 수정요청";
	    from = "babiss@ket.com";
	    email_to = M_email + ";" + L_email + ";";
	    email_to = email_to + "babiss@ket.com";
	    to_name = f_name + ";" + Le_name + ";";
	    System.out.println("to_name==>" + to_name);
	    // email_to = "neho@ket.com";

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costdb/cost_re_view_test.jsp?visitor=2@pk_cr_group=" + pk_cr_group + "@group_case_count="
		        + group_case_count + "@rev_no=" + rev_no + "@data_type=main@step_no=0";

		html = " <html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + "의 원가계산요청서의 정보 수정을 요청합니다.</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 의견 : </strong>" + consent_txt + "</font></td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "</table></body></html> ";
		if (!"".equals(cost_id)) {
		    mail_send = mail.sendMail(from, cost_id, subject, html);
		}
	    }
	}

	if (step_no.equals("96")) {// 보고서 담당자 결재
	    // subject = "[알림]- 원가산출 결과 결재요망 ";
	    subject = pjt_name + " - 원가산출 결과 결재요망 ";

	    from = M_email;

	    email_to = M_email + ";" + L_email + ";"; // 보내는 주소
	    // email_to = email_to+"babiss@ket.com"
	    System.out.println("from==>" + from);
	    System.out.println("email_to==>" + email_to);

	    // email_to = "neho@ket.com";
	    to_name = f_name + ";" + Le_name + ";";
	    System.out.println("to_name==>" + to_name);

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costreport/cost_report_1.jsp?cost_report_add=ok@visitor=1@pk_cr_group=" + pk_cr_group
		        + "@table_row=" + table_row + "@report_pk=" + report_pk + "@rev_no=" + rev_no + "@user_case_count="
		        + user_case_count;
		html = "<html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가산출결과 결재를 요청합니다.</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "</table></body></html> ";
		if (!"".equals(cost_id)) {
		    mail_send = mail.sendMail(from, cost_id, subject, html);
		}
	    }
	}

	if (step_no.equals("95")) {// 보고서 팀장 결재

	    // subject = "[알림]- 원가산출 결과 결재요망 ";
	    subject = pjt_name + " - 원가산출 결과 결재요망 ";
	    if (!"".equals(co_subject)) {
		co_subject = pjt_name + co_subject;
	    }
	    from = L_email;

	    email_to = "yswgold@ket.com;" + M_email + ";";// 보내는 주소
	    System.out.println("from==>" + from);
	    System.out.println("email_to==>" + email_to);
	    // email_to = "neho@ket.com";

	    to_name = "유승우";

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costreport/cost_report_1.jsp?cost_report_add=ok@visitor=1@pk_cr_group=" + pk_cr_group
		        + "@table_row=" + table_row + "@report_pk=" + report_pk + "@rev_no=" + rev_no + "@user_case_count="
		        + user_case_count;

		html = "<html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		if ("".equals(co_subject)) {
		    html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가산출결과 결재를 요청합니다.</font></td></tr>";
		}
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + report_add_html;
		html = html + "</table></body></html> ";
		// cost_id = "neho";
		if (!"".equals(cost_id)) {
		    if (!"".equals(co_subject)) {
			mail_send = mail.sendMail(from, cost_id, co_subject, html);
		    } else {
			mail_send = mail.sendMail(from, cost_id, subject, html);
		    }
		}
	    }
	}

	if (step_no.equals("94")) {// 보고서 센터장 결재

	    // subject = "[알림]- 원가산출결과 1차 배포 ";
	    subject = pjt_name + " - 원가산출결과 1차 배포 ";
	    subject_2 = pjt_name + " - 원가산출 결과 결재요망 ";
	    if (!"".equals(co_subject)) {
		co_subject = pjt_name + co_subject;
	    }
	    from = L_email;

	    email_to = M_email;// 보내는 주소
	    System.out.println("from==>" + from);
	    System.out.println("email_to==>" + email_to);
	    // email_to = "neho@ket.com";

	    to_name = Le_name;

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    link_url_2 = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=kimseok&returnUrl=" + "/plm/jsp/cost/costreport/appr_list.jsp";
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costreport/cost_report_1.jsp?cost_report_add=ok@visitor=3@pk_cr_group=" + pk_cr_group
		        + "@table_row=" + table_row + "@report_pk=" + report_pk + "@rev_no=" + rev_no + "@user_case_count="
		        + user_case_count;

		html = "<html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		if (!"".equals(co_subject)) {
		    html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가산출결과를 알려드립니다.</font></td></tr>";
		} else {
		    if (cost_id.equals("kimseok")) {
			html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name
			        + " 의 원가산출결과 결재를 요청합니다.</font></td></tr>";
		    } else {
			html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가산출결과를 알려드립니다.</font></td></tr>";
		    }
		}

		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
/*		if (cost_id.equals("kimseok") && "".equals(co_subject)) {
		    html = html + "<tr><td height=23><font size=2><strong>■ 결재대상 목록으로 이동 : </strong><a href='" + link_url_2
			    + "'> 이동 </a></font><br>";
		}*/
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + report_add_html;
		html = html + "</table></body></html> ";
		// cost_id = "neho";

		if (!"".equals(cost_id)) {
		    System.out.println("cost_id == " + cost_id);
		    if (!"".equals(co_subject)) {
			// cost_id = "neho";
			mail_send = mail.sendMail(from, cost_id, co_subject, html);
		    } else {
			if (cost_id.equals("kimseok")) {
			    // cost_id = "neho";
			    mail_send = mail.sendMail(from, cost_id, subject_2, html);
			} else {
			    // cost_id = "neho";
			    mail_send = mail.sendMail(from, cost_id, subject, html);
			}
		    }
		}
	    }
	}

	if (step_no.equals("93")) {// 보고서2차 배포 부문장 결재

	    // subject = "[알림]- 원가산출 결과 결재요망 ";
	    subject = pjt_name + " - 원가산출결과 2차 배포 ";
	    subject_2 = pjt_name + " - 원가산출 결과 결재요망 ";
	    from = L_email;

	    email_to = M_email;// 보내는 주소
	    System.out.println("from==>" + from);
	    System.out.println("email_to==>" + email_to);

	    to_name = Le_name;

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);

	    link_url_2 = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=briandsk&returnUrl=" + "/plm/jsp/cost/costreport/appr_list.jsp";
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costreport/cost_report_1.jsp?cost_report_add=ok@visitor=4@pk_cr_group=" + pk_cr_group
		        + "@table_row=" + table_row + "@report_pk=" + report_pk + "@rev_no=" + rev_no + "@user_case_count="
		        + user_case_count;
		if (cost_id.equals("sblee") || cost_id.equals("briandsk")) {
		    link_url_2 = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
			    + "/plm/jsp/cost/costreport/appr_list.jsp";
		}
		html = "<html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		if (cost_id.equals("sblee")) {
		    html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가산출결과 결재를 요청합니다.</font></td></tr>";
		} else {
		    html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가산출결과를 알려드립니다.</font></td></tr>";
		}
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + report_add_html;
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + report_add_html_2;
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		/*if (cost_id.equals("sblee")) {
		    html = html + "<tr><td height=23><font size=2><strong>■ 결재대상 목록으로 이동 : </strong><a href='" + link_url_2
			    + "'> 이동 </a></font><br>";
		}*/
		html = html + "</table></body></html> ";
		// if(cost_id.equals("sblee") || cost_id.equals("briandsk") || cost_id.equals("ketcolwj")){
		// cost_id = "neho";
		if (!"".equals(cost_id)) {
		    if (cost_id.equals("sblee")) {
			mail_send = mail.sendMail(from, cost_id, subject_2, html);
		    } else {
			mail_send = mail.sendMail(from, cost_id, subject, html);
		    }
		}
		// }
	    }
	}

	if (step_no.equals("93_1")) {// 보고서3차 배포 본부장 결재

	    // subject = "[알림]- 원가산출 결과 결재요망 ";
	    subject = pjt_name + " - 원가산출결과 2차 배포 ";
	    subject_2 = pjt_name + " - 원가산출 결과 결재요망 ";
	    from = L_email;

	    email_to = M_email;// 보내는 주소
	    System.out.println("from==>" + from);
	    System.out.println("email_to==>" + email_to);
	    // email_to = "neho@ket.com";

	    to_name = Le_name;

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);

	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costreport/cost_report_1.jsp?cost_report_add=ok@visitor=4@pk_cr_group=" + pk_cr_group
		        + "@table_row=" + table_row + "@report_pk=" + report_pk + "@rev_no=" + rev_no + "@user_case_count="
		        + user_case_count;
		/*
	         * if (cost_id.equals("kimseok") || cost_id.equals("briandsk") || cost_id.equals("ketcolwj")) { link_url_2 = host +
	         * "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl=" + "/plm/jsp/cost/costreport/appr_list.jsp"; }
	         */

		html = "<html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		if (cost_id.equals("ketcolwj")) {
		    html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가산출결과 결재를 요청합니다.</font></td></tr>";
		} else {
		    html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 원가산출결과를 알려드립니다.</font></td></tr>";
		}
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + report_add_html;
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + report_add_html_2;
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		/*
	         * if (cost_id.equals("ketcolwj")) { html = html +
	         * "<tr><td height=23><font size=2><strong>■ 결재대상 목록으로 이동 : </strong><a href='" + link_url_2 + "'> 이동 </a></font><br>"; }
	         */
		html = html + "</table></body></html> ";
		// if(cost_id.equals("sblee") || cost_id.equals("briandsk") || cost_id.equals("ketcolwj")){
		if (!"".equals(cost_id)) {
		    // cost_id = "neho";
		    if (cost_id.equals("ketcolwj")) {
			mail_send = mail.sendMail(from, cost_id, subject_2, html);
		    } else {
			mail_send = mail.sendMail(from, cost_id, subject, html);
		    }
		}
		// }
	    }
	}

	if (step_no.equals("93_2")) {// 보고서 최종배포 사장 결재

	    // subject = "[알림]- 원가산출 결과 결재요망 ";
	    subject = pjt_name + " - 원가산출 결과 알림 ";
	    from = L_email;

	    email_to = M_email;// 보내는 주소
	    System.out.println("from==>" + from);
	    System.out.println("email_to==>" + email_to);
	    // email_to = "neho@ket.com";

	    to_name = Le_name;

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);

	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];

		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}

		cost_id.replaceAll(" ", "");
		link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
		        + "/plm/jsp/cost/costreport/cost_report_1.jsp?cost_report_add=ok@visitor=4@pk_cr_group=" + pk_cr_group
		        + "@table_row=" + table_row + "@report_pk=" + report_pk + "@rev_no=" + rev_no + "@user_case_count="
		        + user_case_count;
		if (cost_id.equals("sblee") || cost_id.equals("briandsk") || cost_id.equals("ketcolwj")) {
		    link_url_2 = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
			    + "/plm/jsp/cost/costreport/appr_list.jsp";
		}
		html = "<html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + " 의 원가산출결과를 알려드립니다.</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + report_add_html;
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + report_add_html_2;
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		/*if (cost_id.equals("sblee") || cost_id.equals("briandsk") || cost_id.equals("ketcolwj")) {
		    html = html + "<tr><td height=23><font size=2><strong>■ 결재대상 목록으로 이동 : </strong><a href='" + link_url_2
			    + "'> 이동 </a></font><br>";
		}*/
		html = html + "</table></body></html> ";
		// if(cost_id.equals("sblee") || cost_id.equals("briandsk") || cost_id.equals("ketcolwj")){
		if (!"".equals(cost_id)) {
		    // cost_id = "neho";
		    mail_send = mail.sendMail(from, cost_id, subject, html);
		}
		// }
	    }
	}

	if (step_no.equals("92")) {// 보고서 반려

	    // subject = "[알림]- 원가결과보고서 반려";
	    subject = pjt_name + " - 원가결과보고서 반려";

	    from = L_email;

	    email_to = M_email;
	    System.out.println("from==>" + from);
	    System.out.println("email_to==>" + email_to);
	    // email_to = "neho@ket.com";

	    to_name = Le_name;

	    String[] parmeter = email_to.split(";");
	    parmeter = dup_delete(parmeter);
	    for (int i = 0; i < parmeter.length; i++) {
		cost_id = parmeter[i];
		if (!StringUtil.checkNull(cost_id).equals("")) {
		    cost_id = cost_id.substring(0, cost_id.indexOf("@"));
		}
		cost_id.replaceAll(" ", "");
		if ("ketcolwj".equals(cost_id)) {
		    link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
			    + "/plm/jsp/cost/costdb/cost_re_view_test.jsp?step_no=0@visitor=2@pk_cr_group=" + pk_cr_group + "@rev_no="
			    + rev_no + "@data_type=main@group_case_count=" + group_case_count;
		} else {
		    link_url = host + "/jsp/SSOLogin.jsp?mode=cost_mail&pax_webid=" + cost_id + "&returnUrl="
			    + "/plm/jsp/cost/costreport/cost_report_1.jsp?pk_cr_group=" + pk_cr_group + "@table_row=" + table_row
			    + "@report_pk=" + report_pk + "@cost_report_add=ok@rev_no=" + rev_no + "@group_case_count=" + group_case_count;
		}
		html = "<html> ";
		html = html + "<head>";
		html = html + "<title></title>";
		html = html + "</head>";
		html = html + "<body bgcolor=white>";
		html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
		html = html + "<tr><td height=23>" + report_add_html + "</td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		html = html + "<td height=23><font size=2><strong>■ 의견 : </strong>" + consent_txt + "</font></td></tr>";
		html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url
		        + "'> 이동 </a></font><br>";
		html = html + "<tr><td height=10>&nbsp;</td></tr>";
		// cost_id = "neho";
		html = html + "</table></body></html> ";
		if (!"".equals(cost_id)) {
		    mail_send = mail.sendMail(from, cost_id, subject, html);
		}
	    }
	}

	return mail_send;
    }

    public String[] dup_delete(String[] parameter) {// 중복 제거
	String[] parmeter = parameter;
	String cost_id = "";
	for (int i = 0; i < parmeter.length; i++) {
	    cost_id = parmeter[i];

	    for (int j = 0; j < parmeter.length; j++) {
		if (i != j && cost_id.equals(parmeter[j])) {
		    parmeter[j] = "";
		}
	    }
	}
	return parameter;
    }

}
