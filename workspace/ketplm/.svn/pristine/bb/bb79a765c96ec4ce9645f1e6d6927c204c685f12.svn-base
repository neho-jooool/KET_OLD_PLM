package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.util.StringUtil;

public class CostMainListDao {
    private Connection conn;

    public CostMainListDao(Connection conn) {
	this.conn = conn;
    }

    public CostMainListDao() {
	super();
    }

    /**
     * 함수명 : getsearchMainList 설명 : 메인화면 리스트 조회
     * 
     * @param String
     *            select_name,String auth, String dept_no, String position, String group_m, String team, String Ename
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 08. 13.
     */
    public ArrayList getsearchMainList(String select_name, String auth, String dept_no, String position, String group_m, String team,
	    String Ename, String select_team, String pjt_name, String pjt_no, String f_name, String a_name, String customer_F,
	    String car_type, String search_state) throws Exception {
	ArrayList<Hashtable<String, String>> MainList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> MainHash = null;
	String data_view = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT NVL(STEP_NO,0) STEP_NO, \n");
	sb.append("        B.W_NAME, \n");
	sb.append("        B.TABLE_ROW, \n");
	sb.append("        B.DEV_STEP, \n");
	sb.append("        B.REV_NO, \n");
	sb.append("        B.PJT_NO, \n");
	sb.append("        B.PJT_NAME, \n");
	sb.append("        B.PART_NO, \n");
	sb.append("        B.CUSTOMER_F, \n");
	sb.append("        B.CAR_TYPE, \n");
	sb.append("        TO_CHAR(A.REQUEST_DAY,'MM-DD') AS REQUEST_DAY, \n");
	sb.append("        TO_CHAR(A.REQUEST_DAY-2,'MM-DD') AS REQUEST_DAY_2, \n");
	sb.append("        TO_CHAR(A.REQUEST_DAY-1,'MM-DD') AS REQUEST_DAY_1, \n");
	sb.append("        B.PK_CR_GROUP, \n");
	sb.append("        B.REV_PK, \n");
	sb.append("        B.CASE_TYPE_USER, \n");
	sb.append("        D.PK_CRP, \n");
	sb.append("        C.APPROVAL, \n");
	sb.append("        B.A_NAME, \n");
	sb.append("        B.F_NAME, \n");
	sb.append("        TO_CHAR(C.LEADER_DAY,'MM-DD') AS LEADER_DAY, \n");
	sb.append("        TO_CHAR(C.CO_AC_DAY,'MM-DD') AS CO_AC_DAY, \n");
	sb.append("        TO_CHAR(A.FINISH_DAY,'MM-DD') AS FINISH_DAY, \n");
	sb.append("        C.R_PRE_DAY, \n");
	sb.append("        PASS_TYPE, \n");
	sb.append("        INPUT_GB, \n");
	sb.append("        division \n");
	sb.append("   FROM COST_REQUEST A, COST_PRODUCTINFO B, WFINFO C, COST_REPORT D \n");
	sb.append("  WHERE A.FK_PID      = B.PK_PID \n");
	sb.append("    AND A.PK_CR       = C.FK_COST(+) \n");
	// sb.append("    AND B.PK_PID      = D.FK_PID(+)");
	sb.append("    AND B.REPORT_PK = D.PK_CRP(+) \n");
	// sb.append("    AND B.PK_CR_GROUP = '09A106' ");
	sb.append("    AND B.GROUP_NO = 'T001' \n");
	sb.append("    AND B.DATA_TYPE = 'main' \n");
	if (!"90".equals(team)) {
	    sb.append("    AND nvl(division,'1') != '2' \n");
	}
	// sb.append("    AND (C.COST_FLAG IS NULL OR C.COST_FLAG = 'R') ");
	// sb.append("    AND (B.REPORT_PK IS NULL OR B.REPORT_PK = D.PK_CRP) ");

	if (select_name.equals("")) {
	    if (!auth.equals("A") && !auth.equals("S")) {
		if (auth.equals("L")) {// 팀장 또는 그룹장 AND 영업팀 아닐때
		    sb.append("    AND B.TEAM = " + team + " \n");
		} else if (!team.equals("") && auth.equals("D")) { // 개별 권한
		    sb.append("    AND (B.F_NAME = " + "'" + Ename + "'" + " or B.A_NAME = " + "'" + Ename + "'" + ") \n");
		} else {
		    data_view = "no";
		}
	    }
	} else if (select_name.equals("request")) {

	    sb.append("    AND NVL(C.STEP_NO,'0') IN ('0','0.5','1','2E') ");
	    if (!team.equals("") && auth.equals("D")) {
		sb.append("    AND (B.F_NAME = " + "'" + Ename + "'" + " or B.A_NAME = " + "'" + Ename + "'" + ") \n");
	    }
	} else if (select_name.equals("work")) {
	    sb.append("    AND NVL(C.STEP_NO,'0') = '2' ");
	    if (!team.equals("") && auth.equals("D")) {
		sb.append("    AND (B.F_NAME = " + "'" + Ename + "'" + " or B.A_NAME = " + "'" + Ename + "'" + ") \n");
	    }
	} else if (select_name.equals("im_complete")) {
	    sb.append("    AND (NVL(C.STEP_NO,'0') in ('3','4','5','5.1','5.2') or (   NVL(C.STEP_NO,'0') = '6' and DEV_STEP != '개발검토' and R_PRE_DAY is null and pass_type is null  )) \n");
	    if (!team.equals("") && auth.equals("D")) {
		sb.append("    AND (B.F_NAME = " + "'" + Ename + "'" + " or B.A_NAME = " + "'" + Ename + "'" + " \n)");
	    }
	    // 원가팀 이경무 요청으로 2013년 기준으로 [결재중] 데이터를 조회함 by 황정태
	    sb.append("    AND to_char(A.REQUEST_DAY,'YYYYMMDD') >= '2013' \n");
	} else if (select_name.equals("complete")) {
	    sb.append("    AND NVL(C.STEP_NO,'0') = '6' \n");
	    if (!team.equals("") && auth.equals("D")) {
		sb.append("    AND (B.F_NAME = " + "'" + Ename + "'" + " or B.A_NAME = " + "'" + Ename + "'" + ") \n");
	    }
	}
	if (search_state.equals("ok")) {
	    if (!select_team.trim().equals("")) {
		sb.append("    AND (B.TEAM = " + "'" + select_team + "'" + ") \n");
	    }

	    String parameter[];
	    String plus = "";

	    if (!pjt_name.trim().equals("")) {
		pjt_name = java.net.URLDecoder.decode(pjt_name, "UTF-8");
		parameter = pjt_name.split(",");
		for (int i = 0; i < parameter.length; i++) {
		    if (i == parameter.length - 1) {
			plus += "'" + parameter[i] + "'";
		    } else {
			plus += "'" + parameter[i] + "'" + ",";
		    }

		}

		sb.append("    AND B.PJT_NAME IN (" + plus + ") \n");
	    }
	    plus = "";
	    if (!pjt_no.trim().equals("")) {
		parameter = pjt_no.split(",");
		for (int i = 0; i < parameter.length; i++) {
		    if (i == parameter.length - 1) {
			plus += "'" + parameter[i] + "'";
		    } else {
			plus += "'" + parameter[i] + "'" + ",";
		    }

		}

		sb.append("    AND B.PJT_NO IN (" + plus + ") \n");
	    }

	    if (!f_name.trim().equals("")) {
		f_name = java.net.URLDecoder.decode(f_name, "UTF-8");
		sb.append("    AND B.F_NAME =" + "'" + f_name + "' \n");
	    }

	    if (!a_name.trim().equals("")) {
		a_name = java.net.URLDecoder.decode(a_name, "UTF-8");
		sb.append("    AND B.A_NAME =" + "'" + a_name + "' \n");
	    }

	    if (!customer_F.trim().equals("")) {
		customer_F = java.net.URLDecoder.decode(customer_F, "UTF-8");
		sb.append("    AND B.CUSTOMER_F LIKE " + "'%" + customer_F + "%' \n");
	    }

	    if (!car_type.trim().equals("")) {
		car_type = java.net.URLDecoder.decode(car_type, "UTF-8");
		sb.append("    AND B.CAR_TYPE Like " + "'%" + car_type + "%' \n");
	    }
	}

	sb.append(" ORDER BY NVL(to_char(a.request_day,'yyyymmdd'),'16990101') DESC");
	System.out.println(sb.toString());

	String add_yn = "Y";

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    // pstmt.setString(1, "1");
	    if (data_view.equals("no")) {
		MainHash = new Hashtable<String, String>();
		MainHash.put("data_view", data_view);
		MainList.add(MainHash);
	    } else {
		rs = pstmt.executeQuery();
		while (rs.next()) {
		    MainHash = new Hashtable<String, String>();
		    if (select_name.equals("complete") && !StringUtil.checkNull(rs.getString("DEV_STEP")).equals("개발검토")
			    && StringUtil.checkNull(rs.getString("R_PRE_DAY")).equals("")) {
			add_yn = "N";
		    } else {
			add_yn = "Y";
		    }
		    MainHash.put("STEP_NO", StringUtil.checkNull(rs.getString("STEP_NO")));
		    MainHash.put("W_NAME", StringUtil.checkNull(rs.getString("W_NAME")));
		    MainHash.put("TABLE_ROW", StringUtil.checkNull(rs.getString("TABLE_ROW")));
		    MainHash.put("DEV_STEP", StringUtil.checkNull(rs.getString("DEV_STEP")));
		    MainHash.put("REV_NO", StringUtil.checkNull(rs.getString("REV_NO")));
		    MainHash.put("PJT_NO", StringUtil.checkNull(rs.getString("PJT_NO")));
		    MainHash.put("PJT_NAME", StringUtil.checkNull(rs.getString("PJT_NAME")));
		    MainHash.put("PART_NO", StringUtil.checkNull(rs.getString("PART_NO")));
		    MainHash.put("CUSTOMER_F", StringUtil.checkNull(rs.getString("CUSTOMER_F")));
		    MainHash.put("CAR_TYPE", StringUtil.checkNull(rs.getString("CAR_TYPE")));
		    MainHash.put("REQUEST_DAY", StringUtil.checkNull(rs.getString("REQUEST_DAY")));
		    MainHash.put("REQUEST_DAY_2", StringUtil.checkNull(rs.getString("REQUEST_DAY_2")));
		    MainHash.put("REQUEST_DAY_1", StringUtil.checkNull(rs.getString("REQUEST_DAY_1")));
		    MainHash.put("PK_CR_GROUP", StringUtil.checkNull(rs.getString("PK_CR_GROUP")));
		    MainHash.put("REV_PK", StringUtil.checkNull(rs.getString("REV_PK")));
		    MainHash.put("CASE_TYPE_USER", StringUtil.checkNull(rs.getString("CASE_TYPE_USER")));
		    MainHash.put("PK_CRP", StringUtil.checkNull(rs.getString("PK_CRP")));
		    MainHash.put("APPROVAL", StringUtil.checkNull(rs.getString("APPROVAL")));
		    MainHash.put("A_NAME", StringUtil.checkNull(rs.getString("A_NAME")));
		    MainHash.put("F_NAME", StringUtil.checkNull(rs.getString("F_NAME")));
		    MainHash.put("LEADER_DAY", StringUtil.checkNull(rs.getString("LEADER_DAY")));
		    MainHash.put("CO_AC_DAY", StringUtil.checkNull(rs.getString("CO_AC_DAY")));
		    MainHash.put("FINISH_DAY", StringUtil.checkNull(rs.getString("FINISH_DAY")));
		    MainHash.put("R_PRE_DAY", StringUtil.checkNull(rs.getString("R_PRE_DAY")));
		    MainHash.put("PASS_TYPE", StringUtil.checkNull(rs.getString("PASS_TYPE")));
		    MainHash.put("INPUT_GB", StringUtil.checkNull(rs.getString("INPUT_GB")));
		    MainHash.put("division", StringUtil.checkNull(rs.getString("division")));
		    if (add_yn.equals("Y")) {
			MainList.add(MainHash);
		    }
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
	    if (conn != null) {
		conn.close();
	    }
	}
	return MainList;
    }

    /**
     * 함수명 : getsearchDetailMainList 설명 : 메인화면 리스트 상세조회
     * 
     * @param String
     *            select_name,String select_team, String pjt_name, String pjt_no, String f_name, String a_name, String car_type
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 08. 20.
     */
    public ArrayList getsearchDetailMainList(String select_name, String select_team, String pjt_name, String pjt_no, String f_name,
	    String a_name, String customer_F, String car_type) throws Exception {
	ArrayList<Hashtable<String, String>> MainList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> MainHash = null;
	String data_view = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT STEP_NO,   B.W_NAME,     B.TABLE_ROW, B.DEV_STEP,	B.REV_NO,	B.PJT_NO,	B.PJT_NAME,     							   ");
	sb.append("        B.PART_NO, B.CUSTOMER_F, B.CAR_TYPE,  TO_CHAR(A.REQUEST_DAY,'MM-DD') AS REQUEST_DAY, TO_CHAR(A.REQUEST_DAY-2,'MM-DD') REQUEST_DAY_2, TO_CHAR(A.REQUEST_DAY-1,'MM-DD') REQUEST_DAY_1,B.PK_CR_GROUP,	B.REV_PK,    B.CASE_TYPE_USER,   							   ");
	sb.append("        D.PK_CRP,  C.APPROVAL,   B.A_NAME,    B.F_NAME,      TO_CHAR(C.LEADER_DAY,'MM-DD') AS LEADER_DAY,   TO_CHAR(C.CO_AC_DAY,'MM-DD') AS CO_AC_DAY, TO_CHAR(A.FINISH_DAY,'MM-DD') AS FINISH_DAY, C.R_PRE_DAY,PASS_TYPE, INPUT_GB,division       ");
	sb.append("   FROM COST_REQUEST A, COST_PRODUCTINFO B, WFINFO C, COST_REPORT D                                           ");
	sb.append("  WHERE A.FK_PID      = B.PK_PID                                                                              ");
	sb.append("    AND A.PK_CR       = C.FK_COST(+)                                                                          ");
	sb.append("    AND B.PK_PID      = D.FK_PID(+)                                                                           ");
	sb.append("    AND B.REPORT_PK = D.PK_CRP(+)   ");
	// sb.append("    AND B.PK_CR_GROUP = '09A106'   																			 ");
	sb.append("    AND B.GROUP_NO = 'T001'                                                                                   ");
	sb.append("    AND (B.REPORT_PK IS NULL OR B.REPORT_PK = D.PK_CRP)                                                   ");
	if (!select_team.trim().equals("")) {
	    sb.append(" AND (B.TEAM = " + "'" + select_team + "'" + ")");
	}
	String parameter[];
	String plus = "";

	if (!pjt_name.trim().equals("")) {
	    pjt_name = java.net.URLDecoder.decode(pjt_name, "UTF-8");
	    parameter = pjt_name.split(",");
	    for (int i = 0; i < parameter.length; i++) {
		if (i == parameter.length - 1) {
		    plus += "'" + parameter[i] + "'";
		} else {
		    plus += "'" + parameter[i] + "'" + ",";
		}

	    }

	    sb.append(" AND B.PJT_NAME IN (" + plus + ")");
	}
	plus = "";
	if (!pjt_no.trim().equals("")) {
	    parameter = pjt_no.split(",");
	    for (int i = 0; i < parameter.length; i++) {
		if (i == parameter.length - 1) {
		    plus += "'" + parameter[i] + "'";
		} else {
		    plus += "'" + parameter[i] + "'" + ",";
		}

	    }

	    sb.append(" AND B.PJT_NO IN (" + plus + ")");
	}

	if (!f_name.trim().equals("")) {
	    f_name = java.net.URLDecoder.decode(f_name, "UTF-8");
	    sb.append(" AND B.F_NAME =" + "'" + f_name + "'");
	}

	if (!a_name.trim().equals("")) {
	    a_name = java.net.URLDecoder.decode(a_name, "UTF-8");
	    sb.append(" AND B.A_NAME =" + "'" + a_name + "'");
	}

	if (!customer_F.trim().equals("")) {
	    customer_F = java.net.URLDecoder.decode(customer_F, "UTF-8");
	    sb.append(" AND B.CUSTOMER_F =" + "'" + customer_F + "'");
	}

	if (!car_type.trim().equals("")) {
	    car_type = java.net.URLDecoder.decode(car_type, "UTF-8");
	    sb.append(" AND B.CAR_TYPE =" + "'" + car_type + "'");
	}

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    // pstmt.setString(1, "1");
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		MainHash = new Hashtable<String, String>();
		MainHash.put("STEP_NO", StringUtil.checkNull(rs.getString("STEP_NO")));
		MainHash.put("W_NAME", StringUtil.checkNull(rs.getString("W_NAME")));
		MainHash.put("TABLE_ROW", StringUtil.checkNull(rs.getString("TABLE_ROW")));
		MainHash.put("DEV_STEP", StringUtil.checkNull(rs.getString("DEV_STEP")));
		MainHash.put("REV_NO", StringUtil.checkNull(rs.getString("REV_NO")));
		MainHash.put("PJT_NO", StringUtil.checkNull(rs.getString("PJT_NO")));
		MainHash.put("PJT_NAME", StringUtil.checkNull(rs.getString("PJT_NAME")));
		MainHash.put("PART_NO", StringUtil.checkNull(rs.getString("PART_NO")));
		MainHash.put("CUSTOMER_F", StringUtil.checkNull(rs.getString("CUSTOMER_F")));
		MainHash.put("CAR_TYPE", StringUtil.checkNull(rs.getString("CAR_TYPE")));
		MainHash.put("REQUEST_DAY", StringUtil.checkNull(rs.getString("REQUEST_DAY")));
		MainHash.put("REQUEST_DAY_2", StringUtil.checkNull(rs.getString("REQUEST_DAY_2")));
		MainHash.put("REQUEST_DAY_1", StringUtil.checkNull(rs.getString("REQUEST_DAY_1")));
		MainHash.put("PK_CR_GROUP", StringUtil.checkNull(rs.getString("PK_CR_GROUP")));
		MainHash.put("REV_PK", StringUtil.checkNull(rs.getString("REV_PK")));
		MainHash.put("CASE_TYPE_USER", StringUtil.checkNull(rs.getString("CASE_TYPE_USER")));
		MainHash.put("PK_CRP", StringUtil.checkNull(rs.getString("PK_CRP")));
		MainHash.put("APPROVAL", StringUtil.checkNull(rs.getString("APPROVAL")));
		MainHash.put("A_NAME", StringUtil.checkNull(rs.getString("A_NAME")));
		MainHash.put("F_NAME", StringUtil.checkNull(rs.getString("F_NAME")));
		MainHash.put("LEADER_DAY", StringUtil.checkNull(rs.getString("LEADER_DAY")));
		MainHash.put("CO_AC_DAY", StringUtil.checkNull(rs.getString("CO_AC_DAY")));
		MainHash.put("FINISH_DAY", StringUtil.checkNull(rs.getString("FINISH_DAY")));
		MainHash.put("R_PRE_DAY", StringUtil.checkNull(rs.getString("R_PRE_DAY")));
		MainHash.put("PASS_TYPE", StringUtil.checkNull(rs.getString("PASS_TYPE")));
		MainHash.put("INPUT_GB", StringUtil.checkNull(rs.getString("INPUT_GB")));
		MainHash.put("division", StringUtil.checkNull(rs.getString("division")));
		MainList.add(MainHash);
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
	return MainList;
    }
}
