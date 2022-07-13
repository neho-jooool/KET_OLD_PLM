/**
 * @(#)	PeopleHelper.java
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.groupware.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.Base64;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.PlmDBUtil;
import ext.ket.shared.log.Kogger;

public class PeopleHelper {
    public static PeopleHelper manager = new PeopleHelper();

    private PeopleHelper() {
    };

    public void syncStore(WTUser _user) {
	try {
	    People people = People.newPeople();
	    Vector<String> userVec = new Vector<String>();
	    userVec = getHRUserInfo(_user.getName());

	    people.setUser(_user);
	    people.setId(_user.getName());
	    people.setName(_user.getFullName());
	    people.setEmail((_user.getEMail() == null) ? "" : _user.getEMail());
	    people.setPwChangeDate(new Timestamp(new Date().getTime()));

	    if (userVec.size() > 0) {
		people.setPassword(userVec.elementAt(0));
		people.setPerNo(userVec.elementAt(1));
	    }

	    PersistenceHelper.manager.save(people);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void syncModify(WTUser _user) {
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(_user, "people", WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
		People people = (People) qr.nextElement();
		people.setId(_user.getName());
		people.setName(_user.getFullName());
		people.setEmail((_user.getEMail() == null) ? "" : _user.getEMail());
		PersistenceHelper.manager.modify(people);
	    } else {
		syncStore(_user);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void syncDelete(WTUser _user) {
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(_user, "people", WTUserPeopleLink.class);

	    if (qr.hasMoreElements()) {
		People people = (People) qr.nextElement();
		PersistenceHelper.manager.delete(people);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void syncDisabled(WTUser _user) {
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(_user, "people", WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
		People people = (People) qr.nextElement();
		people.setIsDisable(true);
		PersistenceHelper.manager.modify(people);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void syncEnabled(WTUser _user) {
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(_user, "people", WTUserPeopleLink.class);

	    if (qr.hasMoreElements()) {
		People people = (People) qr.nextElement();
		people.setIsDisable(false);
		PersistenceHelper.manager.modify(people);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void syncWTUser() {
	try {
	    QuerySpec query = new QuerySpec(WTUser.class);
	    QueryResult result = PersistenceHelper.manager.find(query);
	    WTUser wtuser = null;
	    while (result.hasMoreElements()) {
		wtuser = (WTUser) result.nextElement();
		if (!wtuser.isDisabled())
		    syncModify(wtuser);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public People getPeople(String id) {
	try {
	    QuerySpec spec = new QuerySpec();
	    Class mainClass = People.class;
	    int mainClassPos = spec.addClassList(mainClass, true);
	    spec.appendWhere(new SearchCondition(mainClass, People.ID, "=", id), new int[] { mainClassPos });
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    if (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		return (People) obj[0];
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public HashMap<String, String> getPeopleFromOid(String oid) {

	Connection conn = null;
	HashMap<String, String> map = new HashMap<String, String>();
	try {
	    conn = PlmDBUtil.getConnection();
	    String sql = "SELECT P.ID ID," + " P.NAME NAME," + " D.CODE DEPTCODE," + " D.NAME DEPTNAME," + " P.DUTYCODE DUTYCODE,"
		    + " P.DUTY DUTY" + " FROM PEOPLE P, DEPARTMENT D" + " WHERE     1 = 1" + " AND D.IDA2A2 = P.IDA3C4"
		    + " AND P.IDA3B4 = ?";
	    PreparedStatement statement = conn.prepareStatement(sql);
	    statement.setLong(1, Long.parseLong(oid));
	    ResultSet rs = statement.executeQuery();

	    if (rs.next()) {
		String id = rs.getString("ID");
		String name = rs.getString("NAME");
		String deptcode = rs.getString("DEPTCODE");
		String deptname = rs.getString("DEPTNAME");
		String dutycode = rs.getString("DUTYCODE");
		String duty = rs.getString("DUTY");
		map.put("id", id);
		map.put("name", name);
		map.put("deptcode", deptcode);
		map.put("deptname", deptname);
		map.put("dutycode", dutycode);
		map.put("duty", duty);
	    }
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(statement);
	    PlmDBUtil.close(conn);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (!conn.isClosed())
		    PlmDBUtil.close(conn);
	    } catch (SQLException e) {
		Kogger.error(getClass(), e);
	    }
	}

	return map;
    }

    public void savePassword(HttpServletRequest req) {
	String auth = req.getHeader("authorization");
	auth = auth.substring("Basic ".length());
	String authDecoded = Base64.decodeToString(auth);
	String userPassword = authDecoded.substring(authDecoded.indexOf(":") + 1);

	try {
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    People people = getPeople(user.getName());
	    people.setPassword(userPassword);
	    PersistenceHelper.manager.modify(people);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * _user의 패스워드 변경 경고 횟수를 반환
     * 
     * @param _user
     * @return
     */
    public int checkPasswordWarning(WTUser _user) {
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(_user, "people", WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
		Timestamp currentDate = new Timestamp(new Date().getTime());

		People people = (People) qr.nextElement();
		Timestamp lastestUpdate = people.getPwChangeDate();
		if (lastestUpdate == null) {
		    people.setPwChangeDate(currentDate);
		    PersistenceHelper.manager.modify(people);
		} else {
		    // Calendar ca = Calendar.getInstance();
		    // ca.setTime((Date) lastestUpdate);
		    // ca.add(Calendar.MONTH, 1);

		    // if (currentDate.after(ca.getTime()))
		    if (currentDate.after(lastestUpdate)) {
			int warning = people.getWarningCount() + 1;
			people.setWarningCount(warning);
			PersistenceHelper.manager.modify(people);

			return warning;
		    }
		}
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
	return 0;
    }

    public String getPasswordWarning(WTUser _user) {
	String result = "";

	try {
	    if (CommonUtil.isAdmin() || !ConfigImpl.getInstance().getBoolean("warning.password", false)) {
		return result;
	    }
	    QueryResult qr = PersistenceHelper.manager.navigate(_user, "people", WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
		People people = (People) qr.nextElement();
		if (people.getWarningCount() == 0) {
		    return "";
		}
		result = people.getWarningCount() + "차 경고!! 패스워드를 변경하세요";
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return result;
    }

    public WTUser getChiefUser(Department depart) {
	WTUser user = null;

	try {
	    QuerySpec spec = new QuerySpec(People.class);
	    spec.appendWhere(new SearchCondition(People.class, "departmentReference.key.id", "=", CommonUtil.getOIDLongValue(depart)),
		    new int[] { 0 });
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(People.class, People.IS_DISABLE, SearchCondition.IS_FALSE), new int[] { 0 });
	    spec.appendAnd();
	    spec.appendOpenParen();
	    spec.appendWhere(new SearchCondition(People.class, People.CHIEF, SearchCondition.NOT_EQUAL, ""), new int[] { 0 });
	    spec.appendOr();
	    spec.appendWhere(new SearchCondition(People.class, People.CHIEF, SearchCondition.NOT_NULL, true), new int[] { 0 });
	    spec.appendCloseParen();
	    QueryResult qr = PersistenceHelper.manager.find(spec);

	    while (qr.hasMoreElements()) {
		user = ((People) qr.nextElement()).getUser();
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return user;
    }

    public static Vector<String> getHRUserInfo(String userId) {
	Vector<String> returnVec = new Vector<String>();

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    StringBuffer sqlString = new StringBuffer();
	    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

	    java.util.Properties prop = new java.util.Properties();
	    prop.put("user", "dpuser");
	    prop.put("password", "dpuser12!@");

	    conn = DriverManager.getConnection("jdbc:odbc:KET_EP", prop);

	    sqlString.append(" SELECT u.passWd									Password		\n");
	    sqlString.append("         , u.empCode									empCode		\n");
	    sqlString.append("  FROM xclick_ket.dbo.v_DP_User_All_plm u						\n");
	    sqlString.append(" WHERE u.LoginID = ?												\n");
	    sqlString.append("    AND u.PrimaryYN = 'Y'											\n");
	    // sqlString.append("    AND u.Prop_Gubn = '0'											\n");

	    pstmt = conn.prepareStatement(sqlString.toString());
	    pstmt.setString(1, userId);
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		returnVec.addElement(rs.getString("Password"));
		returnVec.addElement(rs.getString("empCode"));
	    }
	} catch (Exception e) {
	    Kogger.error(PeopleHelper.class, e);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		}

		if (conn != null) {
		    conn.close();
		}
	    } catch (Exception e) {
		Kogger.error(PeopleHelper.class, e);
	    }
	}

	return returnVec;
    }

}
