package e3ps.cost.util;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import e3ps.cost.dao.CostComDao;

public class ApprUtil {

    public boolean Appr_ver_1(String pk_cr_group, String rev_no, String team, String step_no, String group_case_count) {//개발담당자 결재
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
	    CostComDao dao = new CostComDao(plm_conn);

	    nameList = dao.SearchRepass(pk_cr_group, rev_no, team);
	    if (plm_conn != null) {
		plm_conn.close();
	    }

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
		dao = new CostComDao(ep_conn);

		MailList = dao.SearchEmail(f_name, team, step_no, "");

	    } catch (Exception e) {
		e.printStackTrace();
		e.getMessage();
	    }
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    Hashtable MailHash = null;
	    for (int i = 0; i < MailList.size(); i++) {
		MailHash = (Hashtable) MailList.get(i);
		M_email = (String) MailHash.get("M_email");
		L_email = (String) MailHash.get("L_email");
		Le_name = (String) MailHash.get("Le_name");
	    }

	    mail_ok = mail_call(M_email, L_email, f_name, Le_name, pjt_name, step_no, pk_cr_group, rev_no, group_case_count, "", "");

	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}
	return mail_ok;
    }

    public boolean Appr_ver_2(String pk_cr_group, String rev_no, String team, String step_no, String group_case_count) {//그룹장 결재
	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	CostComDao dao = new CostComDao(plm_conn);
	String pjtinfo = dao.getSearchPjtinfo(pk_cr_group, rev_no);
	String[] parameters = null;
	parameters = pjtinfo.split(";");
	String f_name = parameters[0];
	String pjt_name = parameters[1];
	f_name = f_name.trim();
	pjt_name = pjt_name.trim();
	String M_email = "";
	String L_email = "";
	String Le_name = "";
	String G_name = "";
	boolean mail_ok = false;

	ArrayList MailList = new ArrayList();
	try {
	    dao = new CostComDao(ep_conn);
	    G_name = dao.SearchGname(team);
	    MailList = dao.SearchEmail(G_name, team, step_no, "");
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    dao = new CostComDao(plm_conn);
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
	    dao = new CostComDao(plm_conn);

	    dao.Appr_Gr(pk_cr_group, rev_no, G_name);
	    if (plm_conn != null) {
		plm_conn.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	mail_ok = mail_call(M_email, L_email, f_name, Le_name, pjt_name, step_no, pk_cr_group, rev_no, group_case_count, "", "");
	return mail_ok;

    }

    public boolean Appr_ver_3(String pk_cr_group, String rev_no, String team, String step_no, String group_case_count, String leader_name) {//팀장 결재
	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	CostComDao dao = new CostComDao(plm_conn);
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
	boolean mail_ok = false;

	ArrayList MailList = new ArrayList();
	try {
	    dao.Appr_leader(pk_cr_group, rev_no, leader_name);
	    dao = new CostComDao(ep_conn);
	    MailList = dao.SearchEmail(f_name, team, step_no, a_name);
	    if (plm_conn != null) {
		plm_conn.close();
	    }
	    if (ep_conn != null) {
		ep_conn.close();
	    }
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

	mail_ok = mail_call(M_email, L_email, f_name, Le_name, pjt_name, step_no, pk_cr_group, rev_no, group_case_count, a_name, "");
	return mail_ok;

    }

    public boolean Appr_reject(String pk_cr_group, String rev_no, String team, String L_email, String Le_name, String group_case_count, String consent_txt) {//팀장의 요청서 반려
	Connection plm_conn = null;
	Connection ep_conn = null;
	try {
	    plm_conn = DBUtil.getConnection();
	    ep_conn = MSDBUtil.getConnection();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	CostComDao dao = new CostComDao(plm_conn);
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
	    dao = new CostComDao(ep_conn);
	    M_email = dao.SearchF_email(f_name, team);
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    dao = new CostComDao(plm_conn);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}


	mail_ok = mail_call(M_email, L_email, f_name, Le_name, pjt_name, "99", pk_cr_group, rev_no, group_case_count, "", consent_txt);
	return mail_ok;

    }

    public boolean change_w_name(String pk_cr_group, String rev_no, String w_name, String group_case_count, String dept_no) {//산출 담당자 지정
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
	CostComDao dao = new CostComDao(plm_conn);

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

	//11721	커넥터연구개발1팀
	//11722	커넥터연구개발2팀
	//11723	커넥터연구개발3팀
	if(team == "1"){
    	dept_no = "11732";//커넥터설계1팀
    }else if(team == "11"){
    	dept_no = "11728";//커넥터개발팀
    }else if(team == "12"){
    	dept_no = "11729";//커넥터양산개선팀
    }else if(team == "22"){
    	dept_no = "11724";//전장모듈연구개발1팀
    }else if(team == "23"){
    	dept_no = "11725";//전장모듈연구개발2팀
    }else if(team == "24"){
    	dept_no = "11726";//전장모듈연구개발3팀
    }else if(team == "3"){
    	dept_no = "11737";//연구개발3팀
    }else if(team == "4"){
    	dept_no = "11738";//연구개발4팀
    }else if(team == "5"){
    	dept_no = "11743";//연구개발5팀
    }else if(team == "6"){
    	dept_no = "11740";//연구개발6팀
    }else if(team == "21"){
    	dept_no = "11745";//시작개발팀
    }else if(team == "7"){
    	dept_no = "11734";//전장부품개발팀
    }
	String f_email = "";
	String M_email = "";
	try {
	    dao = new CostComDao(ep_conn);
	    M_email = dao.SearchF_email2(w_name);
	    f_email = dao.SearchF_email(f_name, dept_no);
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    dao = new CostComDao(plm_conn);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}

	boolean mail_ok = false;
	mail_ok = mail_call(M_email, f_email, f_name, w_name, pjt_name, "98", pk_cr_group, rev_no, group_case_count, "", "");
	return mail_ok;
    }


    public boolean Appr_reject_work(String pk_cr_group, String rev_no, String team, String group_case_count, String pps_note, String dept_no) {//팀장의 요청서 반려
	Connection plm_conn = null;
	Connection ep_conn = null;

	//11721	커넥터연구개발1팀
	//11722	커넥터연구개발2팀
	//11723	커넥터연구개발3팀
	if(team == "1"){
    	dept_no = "11732";//커넥터설계1팀
    }else if(team == "11"){
    	dept_no = "11728";//커넥터개발팀
    }else if(team == "12"){
    	dept_no = "11729";//커넥터양산개선팀
    }else if(team == "22"){
    	dept_no = "11724";//전장모듈연구개발1팀
    }else if(team == "23"){
    	dept_no = "11725";//전장모듈연구개발2팀
    }else if(team == "24"){
    	dept_no = "11726";//전장모듈연구개발3팀
    }else if(team == "3"){
    	dept_no = "11737";//연구개발3팀
    }else if(team == "4"){
    	dept_no = "11738";//연구개발4팀
    }else if(team == "5"){
    	dept_no = "11743";//연구개발5팀
    }else if(team == "6"){
    	dept_no = "11740";//연구개발6팀
    }else if(team == "21"){
    	dept_no = "11745";//시작개발팀
    }else if(team == "7"){
    	dept_no = "11734";//전장부품개발팀
    }

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
	CostComDao dao = new CostComDao(plm_conn);
	String pjtinfo = "";
	String pjt_name = "";
	String f_name = "";
	String L_name = "";
	try {
	    pjtinfo = dao.Reject_work(pk_cr_group, rev_no, pps_note);
	    String[] parameters = null;
	    parameters = pjtinfo.split(";");
	    pjt_name = parameters[0];
	    f_name = parameters[1];
	    L_name = parameters[2];

	    pjt_name = pjt_name.trim();
	    f_name = f_name.trim();
	    L_name = L_name.trim();
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
	    dao = new CostComDao(ep_conn);
	    f_email = dao.SearchF_email(f_name, team);
	    L_email = dao.SearchF_email(L_name, team);
	    if (ep_conn != null) {
		ep_conn.close();
	    }
	    dao = new CostComDao(plm_conn);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	}


	mail_ok = mail_call(f_email, L_email, f_name, L_name, pjt_name, "97", pk_cr_group, rev_no, group_case_count, "", pps_note);
	return mail_ok;

    }

    public boolean mail_call(String M_email, String L_email, String f_name, String Le_name, String pjt_name, String step_no, String pk_cr_group, String rev_no, String group_case_count, String a_name,
	    String consent_txt) {
	Date date;
	SimpleDateFormat formatter;
	String pattern = "yyyy년 M월 d일 a h시 m분";
	String now_date;
	formatter = new SimpleDateFormat(pattern, new Locale("ko", "KOREA"));
	date = new Date();
	now_date = formatter.format(date);

	String link_url = "";
	String subject = "";
	String from = "";// 받는주소
	String email_to = "";
	String html = "";
	String to_name = "";

	if (step_no.equals("0") || step_no.equals("0.5")) {//개발자 또는 그룹장 결재
	    link_url = "http://192.168.12.67:8080/codebase/costdb/cost_re_view_test.jsp?visitor=1&step_no=" + step_no + "&pk_cr_group=" + pk_cr_group + "&rev_no=" + rev_no
		    + "&data_type=main&group_case_count=" + group_case_count;
	    subject = "[알림]- 원가의뢰요청서 결재요망";

	    from = M_email;

	    email_to = M_email + ";" + L_email + ";"; //보내는 주소
	    email_to = email_to + "babiss@ket.com";

	    to_name = f_name + ";" + Le_name + ";";

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
	    html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url + "'> 이동 </a></font><br>";
	    html = html + "<tr><td height=10>&nbsp;</td></tr>";
	    html = html + "</table></body></html> ";
	}

	if (step_no.equals("1")) {//팀장 결재
	    link_url = "http://192.168.12.67:8080/codebase/costdb/cost_re_view_test.jsp?visitor=1&step_no=" + step_no + "&pk_cr_group=" + pk_cr_group + "&rev_no=" + rev_no
		    + "&data_type=main&group_case_count=" + group_case_count;
	    subject = "[알림]- 원가산출요청";
	    from = M_email;
	    email_to = L_email + ";" + M_email + ";kmlee@ket.com;leejh@ket.com;ois@ket.com;suk@ket.com;jbhong@ket.com;";
	    email_to = email_to + "babiss@ket.com";
	    to_name = a_name + ";" + f_name + ";이경무;이정환;오인석;석상식;홍종범;";

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
	    html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url + "'> 이동 </a></font><br>";
	    html = html + "<tr><td height=10>&nbsp;</td></tr>";
	    html = html + "</table></body></html> ";
	}

	if (step_no.equals("99")) {//팀장의 요청서 반려
	    String txt = "";
	    try {
		txt = java.net.URLDecoder.decode(consent_txt, "utf-8");
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }

	    link_url = "http://192.168.12.67:8080/codebase/costdb/cost_re_view_test.jsp?visitor=2&&pk_cr_group=" + pk_cr_group + "&rev_no=" + rev_no + "&data_type=main&group_case_count="
		    + group_case_count;
	    subject = "[알림]- 원가의뢰요청서 반려";
	    from = M_email;
	    email_to = L_email + ";" + M_email + ";";
	    email_to = email_to + "babiss@ket.com";
	    to_name = a_name + ";" + f_name;
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
	    html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url + "'> 이동 </a></font><br>";
	    html = html + "<tr><td height=10>&nbsp;</td></tr>";
	    html = html + "</table></body></html> ";
	}

	if (step_no.equals("98")) {//산출 담당자 지정
	    link_url = "http://192.168.12.67:8080/codebase/costdb/cost_re_view_test.jsp?visitor=5&step_no=0&pk_cr_group=" + pk_cr_group + "&rev_no=" + rev_no + "&data_type=main&group_case_count="
		    + group_case_count;
	    subject = "[알림]- 원가계산요청 알림";
	    from = M_email;
	    email_to = M_email + ";" + L_email + ";";
	    email_to = email_to + "babiss@ket.com";
	    to_name = Le_name + ";" + f_name + ";";
	    System.out.println("email_to : " + email_to);
	    //email_to = "neho@ket.com";
	    html = " <html> ";
	    html = html + "<head>";
	    html = html + "<title></title>";
	    html = html + "</head>";
	    html = html + "<body bgcolor=white>";
	    html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
	    html = html + "<tr><td height=23><font size=2 color=red>다음과 같이 " + pjt_name + "의 원가계산요청서가 접수되어 전달하오니 업무진행바랍니다.</font></td></tr>";
	    html = html + "<tr><td height=10>&nbsp;</td></tr>";
	    html = html + "<td height=23><font size=2><strong>■ 보낸날짜 : </strong>" + now_date + "</font></td></tr>";
	    html = html + "<td height=23><font size=2><strong>■ 수신인 : </strong>" + to_name + "</font></td></tr>";
	    html = html + "<tr><td height=10>&nbsp;</td></tr>";
	    html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url + "'> 이동 </a></font><br>";
	    html = html + "<tr><td height=10>&nbsp;</td></tr>";
	    html = html + "</table></body></html> ";
	}

	if (step_no.equals("97")) {//원가요청서 거부
	    link_url = "http://192.168.12.67:8080/codebase/costdb/cost_re_view_test.jsp?visitor=2&step_no=0&pk_cr_group=" + pk_cr_group + "&rev_no=" + rev_no + "&data_type=main&group_case_count="
		    + group_case_count;
	    subject = "[알림]- 원가계산요청서 수정요청";
	    from = "babiss@ket.com";
	    email_to = M_email + ";" + L_email + ";";
	    email_to = email_to + "babiss@ket.com";
	    to_name = f_name + ";" + Le_name + ";";

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
	    html = html + "<tr><td height=23><font size=2><strong>■ 해당 문서로 이동 : </strong><a href='" + link_url + "'> 이동 </a></font><br>";
	    html = html + "<tr><td height=10>&nbsp;</td></tr>";
	    html = html + "</table></body></html> ";
	}

	MailUtil mail = new MailUtil();
	boolean mail_send = mail.sendMail(from, email_to, subject, html);
	return mail_send;
    }

}
