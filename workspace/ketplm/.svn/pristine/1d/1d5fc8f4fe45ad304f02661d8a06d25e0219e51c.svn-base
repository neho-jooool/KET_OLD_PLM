package e3ps.cost.servlet;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import e3ps.cost.dao.CostLoginDao;
import e3ps.cost.util.MSDBUtil;
import e3ps.cost.util.StringUtil;

public class LoginManager implements HttpSessionBindingListener {
    private static LoginManager loginManager = null;
    private static Hashtable loginUsers = new Hashtable();

    private LoginManager() {
	super();
    }

    public static synchronized LoginManager getInstance() {

	if (loginManager == null) {
	    loginManager = new LoginManager();
	}

	return loginManager;
    }

    // 아이디가 맞는지 체크
    public boolean isValid(String userID, String password) {
	boolean isEp = false; // ep 계정확인

	isEp = isEpUsed(userID, password);

	return isEp;
    }

    // EP의 계정확인
    public boolean isEpUsed(String userID, String password) {
	boolean isEp = false;
	// connection
	Connection conn = null;
	try {
	    // connection creation
	    conn = MSDBUtil.getConnection();
	    CostLoginDao loginDao = new CostLoginDao(conn);
	    isEp = loginDao.getLoginBool(userID, password);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return isEp;
    }

    // 설계원가 권한 표시
    public String getCostAuth(String userID) {
	String auth = "";
	// connection
	Connection conn = null;
	try {
	    // connection creation
	    conn = MSDBUtil.getConnection();
	    CostLoginDao loginDao = new CostLoginDao(conn);
	    auth = loginDao.getCostAuth(userID);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return auth;
    }

    // 사용자 이름
    public String getUserName(String userID) {
	String empNo = (String) loginUsers.get(userID);
	String name = "";
	// connection
	Connection conn = null;
	try {
	    // connection creation
	    conn = MSDBUtil.getConnection();
	    CostLoginDao loginDao = new CostLoginDao(conn);
	    name = loginDao.getUserName(empNo);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return name;
    }

    // 해당 세션에 이미 로그인 되있는지 체크
    public boolean isLogin(String sessionID) {

	boolean isLogin = false;
	Enumeration e = loginUsers.keys();
	String key = "";
	while (e.hasMoreElements()) {
	    key = (String) e.nextElement();
	    if (sessionID.equals(key)) {
		isLogin = true;
	    }
	}
	return isLogin;
    }

    // 중복 로그인 막기 위해 아이디 사용중인지 체크
    public boolean isUsing(String userID) {
	boolean isUsing = false;
	Enumeration e = loginUsers.keys();
	String key = "";
	while (e.hasMoreElements()) {
	    key = (String) e.nextElement();
	    if (userID.equals(loginUsers.get(key))) {
		isUsing = true;
	    }
	}
	return isUsing;
    }

    // 세션 생성
    public void setSession(HttpSession session, String userID) {

	String auth = "";
	auth = getCostAuth(userID);
	loginUsers.put(session.getId(), userID);
	// loginUsers.put(auth, auth);

	String emp_no = ""; // 사번
	String Ename = ""; // 로그인한 사용자 이름
	String dept_no = ""; // 로그인한 사용자 부서코드
	String K_pass = ""; // 로그인한 사용자 패스워드
	String Dname = ""; // 로그인한 사용자 부서명
	String position = ""; // 로그인한 사용자 직책
	String group_m = ""; // 로그인한 사용자의 그룹장여부
	String login_id = "";// 권한관리용 id

	String etc_auth = ""; // 개발팀, 선행해석팀,설계원가팀용 권한

	ArrayList UserInfoList = new ArrayList();

	Hashtable<String, String> UserInfoMap = null;
	UserInfoMap = new Hashtable<String, String>();
	// connection
	Connection conn = null;
	try {
	    // connection creation
	    conn = MSDBUtil.getConnection();
	    CostLoginDao loginDao = new CostLoginDao(conn);
	    UserInfoList = loginDao.getUserInfo(userID);
	    UserInfoMap = (Hashtable) UserInfoList.get(0);

	    emp_no = (String) UserInfoMap.get("emp_no");
	    Ename = (String) UserInfoMap.get("Ename");
	    dept_no = (String) UserInfoMap.get("dept_no");
	    K_pass = (String) UserInfoMap.get("K_pass");
	    Dname = (String) UserInfoMap.get("Dname");
	    position = (String) UserInfoMap.get("position");
	    group_m = (String) UserInfoMap.get("group_m");
	} catch (Exception e) {
	    e.printStackTrace();
	}

	if (auth.equals("A")) {
	    login_id = "admin";
	} else {
	    if (dept_no.equals("11731") || dept_no.equals("11690")) {// 연구기획팀,연구기획센타
		login_id = "pro_man";
	    }
	}

	etc_auth = StringUtil.etcOk(dept_no);

	System.out.print("login_id==>" + login_id);
	session.setAttribute("costAuth", auth);
	session.setAttribute("login", this.getInstance());

	session.setAttribute("emp_no", emp_no);
	session.setAttribute("Ename", Ename);
	session.setAttribute("dept_no", dept_no);
	session.setAttribute("K_pass", K_pass);
	session.setAttribute("Dname", Dname);
	session.setAttribute("position", position);
	session.setAttribute("cost_id", userID);
	session.setAttribute("group_m", group_m);
	session.setAttribute("login_id", login_id);
	session.setAttribute("etc_auth", etc_auth);
    }

    // 세션 성립될 때
    public void valueBound(HttpSessionBindingEvent event) {
    }

    // 세션 끊길때
    public void valueUnbound(HttpSessionBindingEvent event) {
	loginUsers.remove(event.getSession().getId());
    }

    // 세션 ID로 로긴된 ID 구분
    public String getUserID(String sessionID) {
	return (String) loginUsers.get(sessionID);
    }

    // 현재 접속자수
    public int getUserCount() {
	return loginUsers.size();
    }
}