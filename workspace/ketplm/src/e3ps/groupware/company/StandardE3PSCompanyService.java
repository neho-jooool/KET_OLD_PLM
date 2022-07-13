/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.groupware.company;

import java.io.BufferedWriter; // Preserved unmodeled dependency
import java.io.File; // Preserved unmodeled dependency
import java.io.FileWriter; // Preserved unmodeled dependency
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.ModificationItem;

import org.apache.commons.lang.StringUtils;

import wt.events.KeyedEvent;
import wt.events.KeyedEventListener;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceManagerEvent;
import wt.fc.QueryResult;
import wt.federation.PrincipalManager.DirContext;
import wt.method.MethodContext;
import wt.org.OrganizationServicesEvent;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTProperties; // Preserved unmodeled dependency

import com.infoengine.au.NamingService;

import e3ps.common.impl.Tree;
import e3ps.common.impl.TreeHelper;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.mail.SendMail;
import e3ps.common.util.LoginAuthUtil;
import e3ps.groupware.company.beans.OrgDao;
import ext.ket.common.util.ObjectUtil;
import ext.ket.project.purchase.util.EPDBUtil;
import ext.ket.shared.log.Kogger;

/**
 * 
 * <p>
 * Use the <code>newStandardE3PSCompanyService</code> static factory method(s), not the <code>StandardE3PSCompanyService</code> constructor,
 * to construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

public class StandardE3PSCompanyService extends StandardManager implements E3PSCompanyService, Serializable {

    private static final String RESOURCE = "e3ps.groupware.company.companyResource";
    private static final String CLASSNAME = StandardE3PSCompanyService.class.getName();

    private KeyedEventListener listener;

    private static String dir;
    private static String defaultAdapter;
    private static String defaultDirectoryUser;
    private static String userSearchBase;
    // Log ???.
    private static BufferedWriter writer_log = null;
    private static String WT_LOG_HOME = "d:\\ptc\\Windchill_10.2\\logs\\userinfo\\";

    /**
     * Returns the conceptual (modeled) name for the class.
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated
     * 
     * @return String
     **/
    public String getConceptualClassname() {

	return CLASSNAME;
    }

    /**
     * @exception wt.services.ManagerException
     **/
    @Override
    protected void performStartupProcess() throws ManagerException {

	super.performStartupProcess();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	listener = new WTUserEventListener(this.getConceptualClassname());
	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey("POST_STORE"));
	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey("POST_MODIFY"));
	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey("POST_DELETE"));
	getManagerService().addEventListener(listener, OrganizationServicesEvent.generateEventKey("POST_DISABLE"));
	getManagerService().addEventListener(listener, OrganizationServicesEvent.generateEventKey("POST_ENABLE"));
	// getManagerService().addEventListener( listener, WfEngineServiceEvent.generateEventKey(WfEventAuditType.ACTIVITY_STATE_CHANGED) );
	// ?? ?? - 2 Method Server ??? ?? ?? (2006/02/20 Choi Seung-hwan)
	// Kogger.debug(getClass(), "PeopleHelper.manager.syncWTUser() called!!!!");
	// PeopleHelper.manager.syncWTUser();

    }

    /**
     * Default factory for the class.
     * 
     * @return StandardE3PSCompanyService
     * @exception wt.util.WTException
     **/
    public static StandardE3PSCompanyService newStandardE3PSCompanyService() throws WTException {

	StandardE3PSCompanyService instance = new StandardE3PSCompanyService();
	instance.initialize();
	return instance;
    }

    @Override
    public void syncPeopleWTUser() throws WTException {
	boolean enableMail = ConfigImpl.getInstance().getBoolean("e3ps.mail.enable", false);
	String toMailAddress = ConfigImpl.getInstance().getString("email.admin.mailTo", "neho@ket.com");

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sqlString = new StringBuffer();

	java.text.SimpleDateFormat log_formatter = new java.text.SimpleDateFormat("_yyyyMMdd_HHmmss");
	java.util.Date log_currentTime = new java.util.Date();

	File logfile = new File(WT_LOG_HOME + "syncPeopleWTUser_" + log_formatter.format(log_currentTime) + ".log");

	try {
	    WTProperties properties = WTProperties.getLocalProperties();
	    String hostName = properties.getProperty("java.rmi.server.hostname");

	    int rowCount = 0;
	    logfile.createNewFile();
	    writer_log = new BufferedWriter(new FileWriter(logfile));

	    try {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

		java.util.Properties prop = new java.util.Properties();
		prop.put("user", "dpuser");
		prop.put("password", "dpuser12!@");

		conn = DriverManager.getConnection("jdbc:odbc:KET_EP", prop);

		if (conn != null)
		    Kogger.debug(getClass(), "Connection Successful!");
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		Kogger.debug(getClass(), "Error Trace in getConnection() : " + e.getMessage());
	    }

	    sqlString.append(" SELECT u.empName                                 UserName          \n");
	    sqlString.append("      , u.LoginID                                 UserID            \n");
	    sqlString.append("      , u.empcode                                 EmpNo             \n");
	    sqlString.append("      , u.passWd                                  Password          \n");
	    // sqlString.append( "      , u.MailID                                  MailAddress       \n" );
	    sqlString.append("      , u.loginid+'@ket.com'                      MailAddress       \n");
	    sqlString.append("      , d.DeptName                                DeptName          \n");
	    sqlString.append("      , u.legacydeptcode                          DeptCode          \n");
	    sqlString.append("      , (CASE                                                       \n");
	    sqlString.append("             WHEN substring(u.legacydeptcode, 1, 2) = '6C' THEN 'zh'      \n");
	    // ?????? hostName.equals("ketplmdev.ket.com")
	    // ?????? hostName.equals("plm.ket.com")
	    // ?????? ??? ??? ????????? en ???
	    // if ( !hostName.equals("plm.ket.com") ) {
	    // sqlString.append( "             WHEN u.legacydeptcode = '11202' THEN 'en'                \n" );
	    // }
	    sqlString.append("             ELSE 'ko'                                              \n");
	    sqlString.append("        END)                                      Lang              \n");
	    sqlString.append("      , pd.DeptName                               ParentDeptName    \n");
	    sqlString.append("      , d.legacyparentdept                        ParentDeptCode    \n");
	    sqlString.append("      , u.legacyJobPosition                       Position          \n");
	    sqlString.append("      , u.JobPositionName                         PositionName      \n");
	    sqlString.append("      , u.legacyJobDuty                           Duty              \n");
	    sqlString.append("      , u.JobDutyName                             DutyName          \n");
	    sqlString.append("   FROM xclick_ket.dbo.v_DP_User_All_plm u                            \n");
	    sqlString.append("      , xclick_ket.dbo.v_DP_Dept     d                            \n");
	    sqlString.append("      , xclick_ket.dbo.v_DP_Dept     pd                           \n");
	    sqlString.append("  WHERE u.LoginID = ?                                               \n");
	    sqlString.append("    AND u.PrimaryYN = 'Y'                                           \n");
	    // sqlString.append( "    AND u.Prop_Gubn = '0'                                           \n" );
	    sqlString.append("    AND u.DeptCode = d.DeptCode                                     \n");
	    sqlString.append("    AND d.ParentDept = pd.DeptCode                                  \n");

	    pstmt = conn.prepareStatement(sqlString.toString());

	    // All User Search
	    QuerySpec qs = new QuerySpec();

	    int ii = qs.addClassList(People.class, true);
	    int jj = qs.addClassList(WTUser.class, true);

	    qs.appendWhere(new SearchCondition(People.class, "userReference.key.id", WTUser.class, "thePersistInfo.theObjectIdentifier.id"), new int[] { ii, jj });
	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    Object[] obj = null;
	    WTUser wtuser = null;
	    People people = null;
	    Department department = null;
	    String deptCode = "";
	    String deptName = "";

	    String userName = "";
	    String userId = "";
	    String userNo = "";
	    String passWd = "";
	    String eMail = "";
	    String groupName = "";
	    String groupId = "";
	    String positionCode = "";
	    String positionName = "";
	    String dutyCode = "";
	    String dutyName = "";
	    String ccCode = "";
	    String nationalCode = "";

	    while (qr.hasMoreElements()) {
		rowCount++;

		obj = (Object[]) qr.nextElement();
		people = (People) obj[0];
		wtuser = (WTUser) obj[1];

		if ("Administrator".equals(people.getId())) {
		    continue;
		}

		if (!wtuser.getName().startsWith("kplus")) {
		    Kogger.debug(getClass(), "=====> StandardE3PSCompanyService : syncPeopleWTUser() : People. Name : " + people.getName());
		    writeLog("##### " + rowCount + " : " + "People Name : " + people.getName() + " User ID : " + people.getId() + "\n");

		    department = people.getDepartment();
		    ccCode = people.getCcCode();

		    pstmt.clearParameters();
		    pstmt.setString(1, wtuser.getName());
		    rs = pstmt.executeQuery();

		    if (rs.next()) {
			userName = getString(rs.getString("UserName"));
			userId = getString(rs.getString("UserID"));
			userNo = getString(rs.getString("EmpNo"));
			passWd = getString(rs.getString("Password"));
			eMail = getString(rs.getString("MailAddress"));
			groupName = getString(rs.getString("DeptName"));
			groupId = getString(rs.getString("DeptCode"));
			nationalCode = getString(rs.getString("Lang"));
			positionCode = getString(rs.getString("Position"));
			positionName = getString(rs.getString("PositionName"));
			dutyCode = getString(rs.getString("Duty"));
			dutyName = getString(rs.getString("DutyName"));

			// update User Info.
			people.setIsDisable(false);
			people.setName(userName);
			people.setAccountNo(userNo);
			people.setPassword(passWd);
			people.setEmail(eMail);
			people.setDuty(positionName);
			people.setDutyCode(positionCode);
			people.setNationalCode(nationalCode);
			people.setChief(null);
			people.setCcCode(null);
			/*
		         * D31 : 법인장 C55 : 본부장 C51 : 사업부장 D30 : 센터장 E10 : 실장 C81 : 연구소장 C36 : 연구원장 J10 : 팀장
		         */
			if (dutyCode.equalsIgnoreCase("D31") || dutyCode.equalsIgnoreCase("C55") || dutyCode.equalsIgnoreCase("C51") || dutyCode.equalsIgnoreCase("D30")
			        || dutyCode.equalsIgnoreCase("E10") || dutyCode.equalsIgnoreCase("C81") || dutyCode.equalsIgnoreCase("C36") || dutyCode.equalsIgnoreCase("J10")) {
			    if (department != null) {
				people.setChief(department.getName());
			    }
			    people.setCcCode(dutyCode);
			}

			people = (People) PersistenceHelper.manager.save(people);

			wtuser.setEMail(eMail);
			wtuser.setFullName(userName);
			wtuser.setDisabled(false);
			wtuser = (WTUser) PersistenceHelper.manager.modify(wtuser);

			if (department != null) {
			    deptCode = department.getCode();
			    deptName = department.getName();
			} else {
			    deptCode = "";
			    deptName = "";
			}

			writeLog("##### " + rowCount + " : " + "PLM Department Name/Code : " + deptName + "/" + deptCode + " Groupware Department Name/Code : " + people.getId() + groupName + "/"
			        + groupId + "\n");

			// Change User Department Info
			if (!deptCode.equals(groupId)) {
			    writeLog("##### " + rowCount + " : " + "People's Department needs to modify" + "\n");

			    Department newDept = getDepartment(groupId, true);

			    if (newDept != null) {
				people = (People) PersistenceHelper.manager.refresh(people);
				people.setDepartment(newDept);
				people = (People) PersistenceHelper.manager.modify(people);

				writeLog("##### " + rowCount + " : " + "People's Department successfully modified" + "\n");
			    } else {
				writeLog("##### " + rowCount + " : " + "People's Department didn't register in PLM System." + "\n");

				SendMail mail = new SendMail();

				mail.setFromMailAddress("neho@ket.com", "PLM System");
				mail.setToMailAddress(toMailAddress, "neho@ket.com");
				mail.setSubject("[Update User Info]User Sync Error");

				String msg = " @ User = " + userName + "(" + userId + ")\n";
				msg = msg + " @ PLM User Department = " + deptName + "[" + deptCode + "]\n";
				msg = msg + " @ Groupware GroupName = " + groupName + "[" + groupId + "]\n";
				mail.setText(msg);

				try {
				    if (enableMail) {
					mail.send();
					writeLog("##### " + rowCount + " : " + "People Department Mail send success" + "\n");
				    }
				} catch (Exception e) {
				    Kogger.debug(getClass(), "[WARNING]Failed to Send Mail !");
				    writeLog("##### " + rowCount + " : " + "People Department Mail send fail" + "\n");
				}
			    }
			} else {
			    writeLog("##### " + rowCount + " : " + "People n't modify!!!" + "\n");
			}
		    } else {
			// Set Disable
			// wtuser.setDisabled( true );
			people.setIsDisable(true);
			people = (People) PersistenceHelper.manager.save(people);
			writeLog("##### " + rowCount + " : " + "people was disabled" + "\n");
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    end_log();

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
		throw new WTException(e);
	    }
	}

	Transaction trx = new Transaction();

	try {

	    this.updatePepleRetireDate();

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    System.out.println(":::::: 퇴사일 정보 업데이트 중 오류 발생");
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}
    }

    @Override
    public void syncWTGroupUser() throws Exception, WTException {
	ArrayList list = OrgDao.service.getDepartmentTree(0);

	for (int i = 0; i < list.size(); i++) {
	    String[] ss = (String[]) list.get(i);
	    syncWTGroupUser(ss[3]);
	}
    }

    @Override
    public String changePassword(String id, String password) throws WTException {

	String returnStr = "";
	Transaction trx = null;

	dirInit();

	try {
	    trx = new Transaction();
	    trx.start();

	    QueryResult qr = PersistenceHelper.manager.navigate((WTUser) wt.org.OrganizationServicesMgr.getPrincipal(id), "people", WTUserPeopleLink.class);

	    if (qr.hasMoreElements()) {
		People people = (People) qr.nextElement();
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.MONTH, 1);

		people.setPassword(password);
		people.setPwChangeDate(new java.sql.Timestamp(ca.getTimeInMillis()));
		people.setWarningCount(0);
		PersistenceHelper.manager.modify(people);

		// Ldap password 수정
		this.changeLdapPassword(id, password);

		// String s5 = DirContext.getMapping( defaultAdapter, "user.uniqueIdAttribute", DirContext.getMapping(defaultAdapter,
		// "user.uid") );
		// String object = s5 + '=' + id + ',' + userSearchBase;
		// Task task = new Task( "/wt/federation/UpdatePrincipal.xml" );
		// task.addParam( "object", object );
		// task.addParam( "field", DirContext.getMapping(defaultAdapter, "user.userPassword") + '=' + password );
		// task.addParam( "modification", "replace" );
		// task.addParam( "instance", defaultAdapter );
		// task.setUsername( defaultDirectoryUser );
		// task.invoke();

		trx.commit();
		trx = null;
		returnStr = "SUCCESS";
	    } else {
		trx.rollback();
		trx = null;
		returnStr = "FAILED!!!";
	    }
	} catch (WTException wte) {
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }

	    // Kogger.debug(getClass(), "######## Password Change Result : " + returnStr + "########" );
	}

	return returnStr;
    }

    private void changeLdapPassword(String userId, String userPassword) {
	try {
	    if (LoginAuthUtil.getCtx() == null) {
		LoginAuthUtil.initialize();
	    }
	    ModificationItem[] mods = new ModificationItem[1];
	    mods[0] = new ModificationItem(javax.naming.directory.DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", userPassword));
	    // mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sn", "윤도혁"));
	    if (LoginAuthUtil.getCtx() != null) {
		// Kogger.debug(getClass(), "uid=" + userId);
		LoginAuthUtil.getCtx().modifyAttributes("uid=" + userId + "," + LoginAuthUtil.getBaseDn(), mods);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public List<Map<String, Object>> getAllDeptListByEP(String plmDeptCode) throws Exception {
	List<Map<String, Object>> list = null;
	Statement stat = null;
	ResultSet rs = null;
	Connection conn = null;

	try {
	    StringBuffer sql = new StringBuffer();
	    sql.append(" WITH recursive_query(legacydeptcode,legacyparentDept,name,sort,dept_name,deptorder) AS (                                               \n");
	    sql.append("   SELECT                                                                                                                               \n");
	    sql.append("          legacydeptcode                                                                                                                \n");
	    sql.append("        , legacyparentDept                                                                                                              \n");
	    sql.append("        , deptname as name                                                                                                              \n");
	    sql.append("        , convert(varchar(255), legacydeptcode) sort                                                                                    \n");
	    sql.append("        , convert(varchar(255), deptname) dept_name                                                                                     \n");
	    sql.append("        , deptorder                                                                                                                     \n");
	    sql.append("     FROM v_dp_dept                                                                                                                     \n");
	    sql.append("     WHERE legacyparentDept = '" + plmDeptCode + "'                                                                                                   \n");
	    sql.append("     UNION ALL                                                                                                                          \n");
	    sql.append("     SELECT                                                                                                                             \n");
	    sql.append("           b.legacydeptcode                                                                                                             \n");
	    sql.append("         , b.legacyparentDept                                                                                                           \n");
	    sql.append("         , b.deptname as bane                                                                                                           \n");
	    sql.append("         , convert(varchar(255), convert(nvarchar,c.sort) + ' > ' +  convert(varchar(255), b.legacydeptcode)) sort                      \n");
	    sql.append("         , convert(varchar(255), convert(nvarchar,c.dept_name) + ' > ' +  convert(varchar(255), b.deptname)) dept_name                  \n");
	    sql.append("         , b.deptorder                                                                                                                  \n");
	    sql.append("     FROM  v_dp_dept b, recursive_query c                                                                                               \n");
	    sql.append("     WHERE b.legacyparentDept = c.legacydeptcode                                                                                        \n");
	    sql.append(" )                                                                                                                                      \n");
	    sql.append(" SELECT name, legacydeptcode,legacyparentDept,deptorder FROM recursive_query order by sort;                                             \n");
	    conn = EPDBUtil.getConnection();
	    stat = conn.createStatement();
	    rs = stat.executeQuery(sql.toString());

	    list = ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    EPDBUtil.close(conn);
	}

	return list;
    }

    public void departmentTreeConfig() throws Exception {

	try {
	    java.text.SimpleDateFormat log_formatter = new java.text.SimpleDateFormat("_yyyyMMdd_HHmmss");
	    java.util.Date log_currentTime = new java.util.Date();

	    File logfile = new File(WT_LOG_HOME + "departmentTreeConfig_" + log_formatter.format(log_currentTime) + ".log");
	    logfile.createNewFile();

	    QuerySpec qs_department_all = new QuerySpec(Department.class);
	    QueryResult qr_department_all = PersistenceHelper.manager.find(qs_department_all);

	    List<Department> plmDeptAllList = new ArrayList<Department>();
	    List<Map<String, Object>> parentMissingList = new ArrayList<Map<String, Object>>();

	    while (qr_department_all.hasMoreElements()) {
		plmDeptAllList.add((Department) qr_department_all.nextElement());
	    }

	    QuerySpec qs = new QuerySpec(Department.class);
	    qs.appendWhere(new SearchCondition(Department.class, Department.PARENT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, Long.parseLong("0")), new int[] { 0 });

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    List<Map<String, Object>> list = null;

	    writer_log = new BufferedWriter(new FileWriter(logfile));

	    while (qr.hasMoreElements()) {// 최상위 Root 부서기준으로 하위 조직도 Sync한다.즉 Root부서는 수동으로 만들어야함

		Department PLMDEPT = (Department) qr.nextElement();
		String plmDeptCode = PLMDEPT.getCode();

		writeLog("#############################################################" + "\n");
		writeLog("#                                                                                                                      #" + "\n");
		writeLog("#                                                                                                                      #" + "\n");
		writeLog("Start tree Config deptCode : " + plmDeptCode + " ## deptName : " + PLMDEPT.getName() + "\n");

		list = getAllDeptListByEP(plmDeptCode);// PLM최상위 Root기준으로 EP조직도 Tree를 가져옴

		for (Map<String, Object> data : list) {

		    String name = (String) data.get("name");
		    if ("퇴직부서".equals(name)) {
			continue;
		    }
		    String deptCode = (String) data.get("legacydeptcode");
		    String parentDeptCode = (String) data.get("legacyparentDept");
		    String sort = (String) data.get("deptorder");

		    Department childDept = null;
		    Department parentDept = null;

		    for (Department plmDept : plmDeptAllList) {// 부서코드가 기존 plm에 존재하면 update해주기 위함
			if (parentDeptCode.equals(plmDept.getCode())) {
			    parentDept = plmDept;
			}
			if (deptCode.equals(plmDept.getCode())) {
			    childDept = plmDept;
			}
		    }
		    if (childDept == null) {// 신규부서면 새로 만든다
			childDept = Department.newDepartment();
		    }
		    childDept.setName(name);
		    childDept.setCode(deptCode);
		    childDept.setSort(Integer.parseInt(sort));
		    childDept.setEnabled(true);
		    boolean isParentExist = StringUtils.isNotEmpty(parentDeptCode) && parentDept != null;
		    String parentDeptName = "";
		    if (isParentExist) {
			parentDeptName = parentDept.getName();
			childDept.setParent(parentDept);
		    }
		    childDept = (Department) PersistenceHelper.manager.save(childDept);
		    if (!isParentExist) {// parent부서가 신규부서인 경우 기존 plm db에서 정보가 없을테니 나중에 돌리기 위해 담아둠
			Map<String, Object> child = new HashMap<String, Object>();
			child.put("dept", childDept);
			child.put("parentDeptCode", parentDeptCode);
			parentMissingList.add(child);
		    }
		    writeLog("update ChildDeptInfo : " + deptCode + " ## deptName : " + name + " ## parentCode : " + parentDeptCode + " ## parentDeptName : " + parentDeptName + "\n");
		}

		for (Map<String, Object> data : parentMissingList) {// parent 정보가 없었던 부서들의 parent정보찾기
		    Department child = (Department) data.get("dept");
		    String parentDeptCode = (String) data.get("parentDeptCode");

		    Department parent = DepartmentHelper.manager.getDepartment(parentDeptCode);
		    child.setParent(parent);
		    child = (Department) PersistenceHelper.manager.save(child);

		    writeLog("update setParent  : " + child.getCode() + " ## deptName : " + child.getName() + " ## parentCode : " + parentDeptCode + " ## parentDeptName : " + parent.getName() + "\n");
		}

		ArrayList childList = TreeHelper.manager.getAllChildList(Department.class, (Tree) PLMDEPT, new ArrayList());

		for (Object obj : childList) {// 퇴직부서 처리
		    Department child = (Department) obj;

		    boolean isDisabled = true;
		    for (Map<String, Object> data : list) {
			if (child.getCode().equals((String) data.get("legacydeptcode"))) {
			    isDisabled = false;
			    break;
			}
		    }
		    if (isDisabled) {
			child.setEnabled(false);
			PersistenceHelper.manager.save(child);
			writeLog("disabled deptName : " + child.getName() + " ## deptCode : " + child.getName() + "\n");
		    }
		}

		writeLog("End tree Config deptCode : " + plmDeptCode + " ## deptName : " + PLMDEPT.getName() + "\n");

		writeLog("#                                                                                                                      #" + "\n");
		writeLog("#                                                                                                                      #" + "\n");
		writeLog("#############################################################" + "\n");

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new WTException(e);
	} finally {
	    end_log();
	}

    }

    @Override
    public void syncDepartment() throws WTException, Exception {
	// 기존 소스 주석처리히고 TREE 구조 자동연동 방식으로 변경함 2021.03.02 황정태
	departmentTreeConfig();
    }

    // @Override
    // public void syncDepartment() throws WTException {
    // boolean enableMail = ConfigImpl.getInstance().getBoolean("e3ps.mail.enable", false);
    // String toMailAddress = ConfigImpl.getInstance().getString("email.admin.mailTo", "neho@ket.com");
    //
    // Connection conn = null;
    // PreparedStatement pstmt = null;
    // ResultSet rs = null;
    // StringBuffer sqlString = new StringBuffer();
    //
    // java.text.SimpleDateFormat log_formatter = new java.text.SimpleDateFormat("_yyyyMMdd_HHmmss");
    // java.util.Date log_currentTime = new java.util.Date();
    //
    // File logfile = new File(WT_LOG_HOME + "syncDepartment_" + log_formatter.format(log_currentTime) + ".log");
    //
    // try {
    // int rowCount = 0;
    // logfile.createNewFile();
    // writer_log = new BufferedWriter(new FileWriter(logfile));
    //
    // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    //
    // java.util.Properties prop = new java.util.Properties();
    // prop.put("user", "dpuser");
    // prop.put("password", "dpuser12!@");
    //
    // conn = DriverManager.getConnection("jdbc:odbc:KET_EP", prop);
    //
    // sqlString.append(" SELECT d.DeptName							DeptName			\n");
    // sqlString.append("	       , d.legacydeptcode							DeptCode				\n");
    // sqlString.append("	       , d.legacyparentdept	        			ParentDeptCode		\n");
    // sqlString.append("	FROM xclick_ket.dbo.v_DP_Dept d							\n");
    // sqlString.append(" WHERE d.legacydeptcode = ?												\n");
    //
    // pstmt = conn.prepareStatement(sqlString.toString());
    //
    // // All Department search
    // boolean flag = true;
    // QuerySpec qs = new QuerySpec(Department.class);
    // qs.appendWhere(new SearchCondition(Department.class, Department.CODE, SearchCondition.LIKE, "%"), new int[] { 0 });
    //
    // QueryResult qr = PersistenceHelper.manager.find(qs);
    //
    // Department department = null;
    // String deptName = "";
    // String deptCode = "";
    // String parentDeptCode = "";
    // String plmDeptCode = "";
    //
    // Department currentParentDept = null;
    // String currentParentDeptName = "";
    // String currentParentDeptCode = "";
    //
    // while (qr.hasMoreElements()) {
    // rowCount++;
    //
    // currentParentDept = null;
    //
    // currentParentDeptName = "";
    // currentParentDeptCode = "";
    //
    // department = (Department) qr.nextElement();
    // if (department.isEnabled()) {
    // try {
    // currentParentDept = (Department) department.getParent();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // if (currentParentDept != null) {
    // currentParentDeptName = getString(currentParentDept.getName());
    // currentParentDeptCode = getString(currentParentDept.getCode());
    // } else {
    // Kogger.debug(getClass(), "=====> StandardE3PSCompanyService : syncDepartment() : Parent Dept. is NULL");
    // }
    //
    // writeLog("##### " + rowCount + " : " + "Department Name : " + department.getName() + " Department Code : "
    // + department.getCode() + " Current Parent Department Name : " + currentParentDeptName
    // + " Current Parent Department Code : " + currentParentDeptCode + "\n");
    //
    // pstmt.clearParameters();
    // // ??????????? ????????????????ep?????????????????? ?????enabled??update??? ??? ??????
    // plmDeptCode = department.getCode();
    // if (plmDeptCode.equals("10074")) {// ??????
    // plmDeptCode = "10076";
    // }
    // if (plmDeptCode.equals("10042")) {// ??????
    // plmDeptCode = "10043";
    // }
    // pstmt.setString(1, plmDeptCode);
    // rs = pstmt.executeQuery();
    //
    // if (rs.next()) {
    // deptName = getString(rs.getString("DeptName"));
    // deptCode = getString(rs.getString("DeptCode"));
    // parentDeptCode = getString(rs.getString("ParentDeptCode"));
    //
    // // Update Department Info.
    // department.setName(deptName);
    // department.setEnabled(true);
    //
    // // Parent Department Changed
    // if (!parentDeptCode.equalsIgnoreCase("E000") && !currentParentDeptCode.equalsIgnoreCase(parentDeptCode)) {
    // writeLog("##### " + rowCount + " : " + "Parent Department needs to modify!!!" + "\n");
    //
    // if (parentDeptCode != null && parentDeptCode.trim().length() > 0) {
    // Department newParentDept = getDepartment(parentDeptCode, true);
    //
    // if (newParentDept == null) {
    // writeLog("##### " + rowCount + " : " + "New Parent Department n't register in PLM System." + "\n");
    //
    // SendMail mail = new SendMail();
    //
    // mail.setFromMailAddress("neho@ket.com", "PLM System");
    // mail.setToMailAddress(toMailAddress, "neho@ket.com");
    // mail.setSubject("[UpdateUserInfo]Department Sync Error");
    //
    // String msg = "Parent Department didn't Exist\n";
    // msg = msg + " @ Department : " + deptName + " [" + deptCode + "]\n";
    // msg = msg + " @ Parent Department : " + parentDeptCode + "\n";
    // mail.setText(msg);
    //
    // try {
    // if (enableMail) {
    // mail.send();
    // writeLog("##### " + rowCount + " : " + "Parent Department Mail send success" + "\n");
    // }
    // } catch (Exception e) {
    // Kogger.debug(getClass(), "[WARNING]Failed to Send Mail !");
    // writeLog("##### " + rowCount + " : " + "Parent Department Mail send fail" + "\n");
    // }
    // } else {
    // // Change Parent Department
    // writeLog("##### " + rowCount + " : " + "New Parent Department Name : " + newParentDept.getName()
    // + " New Parent Department Code : " + newParentDept.getCode() + "\n");
    // department = (Department) PersistenceHelper.manager.refresh(department);
    // department.setParent(newParentDept);
    //
    // writeLog("##### " + rowCount + " : " + "New Parent Department change success" + "\n");
    // }
    // }
    // // else
    // // {
    // // // Change Parent Department
    // // PersistenceHelper.manager.refresh( department );
    // // department.setParent( null );
    // //
    // // writeLog( "##### " + rowCount + " : " + "Parent Department change success" + " : Parent Department Code : null" +
    // // "\n" );
    // // }
    // } else {
    // writeLog("##### " + rowCount + " : " + "Parent Department didn't modify!!!" + "\n");
    // }
    //
    // department = (Department) PersistenceHelper.manager.save(department);
    // } else {
    // // Department Disable
    // department.setEnabled(false);
    // department = (Department) PersistenceHelper.manager.save(department);
    //
    // writeLog("##### " + rowCount + " : " + "Department was disabled" + "\n");
    // }
    // }
    // } catch (Exception e) {
    // Kogger.error(getClass(), e);
    // throw new WTException(e);
    // } finally {
    // end_log();
    //
    // try {
    // if (rs != null) {
    // rs.close();
    // }
    //
    // if (pstmt != null) {
    // pstmt.close();
    // }
    //
    // if (conn != null) {
    // conn.close();
    // }
    // } catch (Exception e) {
    // throw new WTException(e);
    // }
    // }
    // }

    public void updatePepleRetireDate() throws Exception {

	Statement stat = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	System.out.println(":::::: 퇴사일 정보 업데이트 Start ::::::::::::::::");
	System.out.println("::::::                         ::::::::::::::::");
	System.out.println("::::::                         ::::::::::::::::");
	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();
	    StringBuffer sql = new StringBuffer();

	    sql.append(" UPDATE PEOPLE																																			 ");
	    sql.append("    SET RETRIREDATE = ( SELECT MAX(TO_DATE(RETIRE_DATE)) FROM (SELECT DISTINCT RETIRE_DATE,EMP_NO FROM VW_RETIRE_USER_LIST@LEGACY) WHERE EMP_NO = ACCOUNTNO)  ");
	    sql.append("  WHERE RETRIREDATE IS NULL                                                                                                                              ");
	    sql.append("    AND ISDISABLE = 1                                                                                                                                    ");
	    sql.append("    AND ACCOUNTNO IN (SELECT EMP_NO FROM VW_RETIRE_USER_LIST@LEGACY)                                                                                 ");

	    stat.executeUpdate(sql.toString());

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}
	System.out.println("::::::                         ::::::::::::::::");
	System.out.println("::::::                         ::::::::::::::::");
	System.out.println(":::::: 퇴사일 정보 업데이트 End ::::::::::::::::");

    }

    @Override
    public void checkChiefInfo() throws WTException {
	boolean enableMail = ConfigImpl.getInstance().getBoolean("e3ps.mail.enable", false);
	String toMailAddress = ConfigImpl.getInstance().getString("email.admin.mailTo", "neho@ket.com");

	Department department = null;
	WTUser chief = null;

	try {
	    // All Department search
	    QuerySpec qs = new QuerySpec(Department.class);
	    qs.appendWhere(new SearchCondition(Department.class, Department.ENABLED, SearchCondition.IS_TRUE, true), new int[] { 0 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(Department.class, Department.CODE, SearchCondition.LIKE, "%"), new int[] { 0 });

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    while (qr.hasMoreElements()) {
		department = (Department) qr.nextElement();
		chief = DepartmentHelper.manager.getDepartChief(department);

		if (chief == null) {
		    // Send Mail
		    SendMail mail = new SendMail();

		    mail.setFromMailAddress("neho@ket.com", "PLM System");
		    mail.setToMailAddress(toMailAddress, "neho@ket.com");
		    mail.setSubject("[Update User Info]Checking Chief Error");

		    String msg = " @ Department Chief didn't exist.\n";
		    msg = msg + " @ PLM Department = " + department.getName() + " (" + department.getCode() + ")\n";
		    mail.setText(msg);

		    try {
			if (enableMail) {
			    mail.send();
			}
		    } catch (Exception e) {
			Kogger.debug(getClass(), "[WARNING]Failed to Send Mail !");
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    @Override
    public String getPassword(String userId) throws WTException {
	String returnStr = "";

	QuerySpec qs = new QuerySpec(People.class);
	qs.appendWhere(new SearchCondition(People.class, People.ID, SearchCondition.EQUAL, userId), new int[] { 0 });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.size() > 0) {
	    People people = (People) qr.nextElement();
	    returnStr = people.getPassword();
	}

	return returnStr;
    }

    class WTUserEventListener extends ServiceEventListenerAdapter {
	public WTUserEventListener(String manager_name) {
	    super(manager_name);
	}

	public void notifyVetoableEvent(Object obj) throws WTException {
	    if (!(obj instanceof KeyedEvent)) {
		return;
	    }

	    KeyedEvent keyEvent = (KeyedEvent) obj;
	    Object eventObj = keyEvent.getEventTarget();
	    String eventTypeStr = keyEvent.getEventType();

	    // if ( eventTypeStr.equals("ACTIVITY_STATE_CHANGED") && eventObj instanceof WfAssignedActivity)
	    // {
	    // WFItemHelper.manager.changeProxy((WfAssignedActivity) eventObj);
	    // }

	    if (!(eventObj instanceof WTUser)) {
		return;
	    }

	    if (eventTypeStr.equals("POST_DISABLE")) {
		PeopleHelper.manager.syncDelete((WTUser) eventObj);
		// PeopleHelper.manager.syncDisabled( (WTUser) eventObj );
	    } else if (eventTypeStr.equals("POST_ENABLE")) {
		PeopleHelper.manager.syncEnabled((WTUser) eventObj);
	    } else if (eventTypeStr.equals("POST_DELETE")) {
		PeopleHelper.manager.syncDelete((WTUser) eventObj);
	    } else if (eventTypeStr.equals("POST_MODIFY")) {
		PeopleHelper.manager.syncModify((WTUser) eventObj);
	    } else if (eventTypeStr.equals("POST_STORE")) {
		PeopleHelper.manager.syncStore((WTUser) eventObj);
	    }
	}
    }

    public static void dirInit() throws WTException {
	if (defaultAdapter != null) {
	    return;
	}

	try {
	    WTProperties wtproperties = WTProperties.getServerProperties();
	    String s = wtproperties.getProperty("wt.federation.ie.namingService", "namingService");
	    String s1 = wtproperties.getProperty("wt.federation.ie.propertyResource");
	    NamingService namingservice = NamingService.newInstance(s, s1);
	    String s2 = wtproperties.getProperty("wt.federation.taskRootDirectory");
	    String s3 = wtproperties.getProperty("wt.federation.taskCodebase");
	    String s4 = wtproperties.getProperty("wt.federation.task.mapCredentials");
	    defaultDirectoryUser = wtproperties.getProperty("wt.federation.org.defaultDirectoryUser", wtproperties.getProperty("wt.admin.defaultAdministratorName"));
	    String s5 = wtproperties.getProperty("wt.federation.ie.VMName");

	    if (s5 != null) {
		System.setProperty("com.infoengine.vm.name", s5);
	    }

	    if (s3 != null) {
		System.setProperty("com.infoengine.taskProcessor.codebase", s3);
	    }

	    if (s2 != null) {
		System.setProperty("com.infoengine.taskProcessor.templateRoot", s2);
	    }

	    if (s4 != null && System.getProperty("com.infoengine.credentialsMapper") == null) {
		System.setProperty("com.infoengine.credentialsMapper", s4);
	    }

	    defaultAdapter = DirContext.getDefaultJNDIAdapter();
	    userSearchBase = DirContext.getJNDIAdapterSearchBase(defaultAdapter);

	} catch (Exception e) {
	    Kogger.error(StandardE3PSCompanyService.class, e);

	}
    }

    public void syncWTGroupUser(final String groupCode) throws Exception {
	// WTGroup group = UserHelper.service.getWTGroup(groupCode);
	WTGroup group = getWTGroup(groupCode);
	if (group == null) {
	    return;
	}

	ArrayList<Object[]> userList = getGroupUser(groupCode);
	Enumeration en = OrganizationServicesHelper.manager.members(group);
	ArrayList<WTPrincipal> deleteUser = new ArrayList<WTPrincipal>();

	while (en.hasMoreElements()) {
	    WTPrincipal pp = (WTPrincipal) en.nextElement();
	    String oname = pp.getName();
	    boolean flag = true;

	    for (int i = 0; i < userList.size(); i++) {
		Object[] o = (Object[]) userList.get(i);
		WTUser user = (WTUser) o[1];
		String name = user.getName();

		if (name.equals(oname)) {
		    userList.remove(o);
		    flag = false;
		    break;
		}
	    }

	    if (flag) {
		deleteUser.add(pp);
	    }
	}

	WTPrincipal[] add = new WTPrincipal[userList.size()];
	for (int i = 0; i < userList.size(); i++) {
	    Object[] o = (Object[]) userList.get(i);
	    WTUser user = (WTUser) o[1];
	    add[i] = user;

	    OrganizationServicesHelper.manager.addMember(group, user);
	}

	WTPrincipal[] delete = new WTPrincipal[deleteUser.size()];
	for (int i = 0; i < deleteUser.size(); i++) {
	    WTPrincipal user = (WTPrincipal) deleteUser.get(i);
	    delete[i] = user;

	    OrganizationServicesHelper.manager.removeMember(group, user);
	}

	// OrganizationServicesHelper.manager.addMembers(group,add);
	// OrganizationServicesHelper.manager.removeMembers(group,add);
    }

    public Department getDepartment(String code, boolean enabled) throws Exception {
	Department dept = null;

	QuerySpec qs = new QuerySpec(Department.class);
	qs.appendWhere(new SearchCondition(Department.class, Department.CODE, SearchCondition.EQUAL, code), new int[] { 0 });

	if (enabled) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(Department.class, Department.ENABLED, SearchCondition.IS_TRUE, true), new int[] { 0 });
	}

	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.hasMoreElements()) {
	    dept = (Department) qr.nextElement();
	}

	return dept;
    }

    public ArrayList<Object[]> getGroupUser(final String groupCode) throws Exception {
	QuerySpec qs = new QuerySpec();

	int ii = qs.addClassList(People.class, true);
	int jj = qs.addClassList(WTUser.class, true);
	int kk = qs.addClassList(Department.class, true);

	qs.appendWhere(new SearchCondition(People.class, "userReference.key.id", WTUser.class, "thePersistInfo.theObjectIdentifier.id"), new int[] { ii, jj });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(People.class, "departmentReference.key.id", Department.class, "thePersistInfo.theObjectIdentifier.id"), new int[] { ii, kk });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(Department.class, Department.CODE, "=", groupCode), new int[] { kk });

	QueryResult qr = PersistenceHelper.manager.find(qs);
	ArrayList<Object[]> list = new ArrayList<Object[]>();

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    list.add(o);
	}

	return list;
    }

    public WTGroup getWTGroup(String code) throws Exception {
	WTGroup dept = null;
	QuerySpec qs = new QuerySpec(WTGroup.class);
	qs.appendWhere(new SearchCondition(WTGroup.class, WTGroup.NAME, "=", code), new int[] { 0 });
	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.hasMoreElements()) {
	    dept = (WTGroup) qr.nextElement();
	}

	return dept;
    }

    public static boolean isPLMUser(String userId) throws Exception {
	boolean returnFlag = false;

	QuerySpec qs = new QuerySpec(People.class);
	qs.appendWhere(new SearchCondition(People.class, People.ID, "=", userId), new int[] { 0 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(People.class, People.IS_DISABLE, "=", false), new int[] { 0 });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.size() > 0) {
	    returnFlag = true;
	}

	return returnFlag;
    }

    public String getString(final String str) {
	if (str == null) {
	    return "";
	} else if (str.equals("null")) {
	    return "";
	} else {
	    return str;
	}
    }

    static void writeLog(String str) {
	try {
	    writer_log.write(str);
	} catch (Exception ioex) {
	    Kogger.error(StandardE3PSCompanyService.class, ioex);
	    writeLog("[FN] writeLog [Exception] [" + ioex.toString() + "]\n");
	}
    }

    static void end_log() {
	try {
	    writer_log.close();
	} catch (Exception ioex) {
	    Kogger.error(StandardE3PSCompanyService.class, ioex);
	    writeLog("[FN] end_log [Exception] [" + ioex.toString() + "]\n");
	}
    }
}
