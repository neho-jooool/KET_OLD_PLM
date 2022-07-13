package e3ps.common.util;

import java.beans.PropertyVetoException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.FormatContentHolder;
import wt.enterprise.Managed;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.epm.structure.EPMDescribeLink;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.httpgw.GatewayServletHelper;
import wt.httpgw.URLFactory;
import wt.inf.container.WTContainerHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.locks.LockHelper;
import wt.method.MethodContext;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.project.Role;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.team.TeamHelper;
import wt.team.TeamManaged;
import wt.team.WTRoleHolder2;
import wt.util.WTException;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;
import wt.workflow.work.WorkItem;
import e3ps.common.query.SearchUtil;
import e3ps.groupware.company.PeopleData;
import ext.ket.shared.log.Kogger;

public class KETObjectUtil {
    private static ReferenceFactory rf    = null;
    private static boolean          DEBUG = true;

    private KETObjectUtil() {
    }

    /**
     * <br>
     * 객체의 Oid를 리턴하는 Method <br>
     * 
     * @param object
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getOidString(Persistable object) throws WTException {
	if (object == null) {
	    return null;
	}

	return PersistenceHelper.getObjectIdentifier(object).getStringValue();
    }


    /**
     * <br>
     * 객체에서 ida2a2값만을 리턴하는 Method <br>
     * 
     * @param object
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getIda2a2(Persistable object) throws WTException {
	String tempoid = getOidString(object);
	tempoid = tempoid.substring(tempoid.lastIndexOf(":") + 1);

	return tempoid;
    }


    /**
     * <br>
     * oid에서 ida2a2값만을 리턴하는 Method <br>
     * 
     * @param oid
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getIda2a2(String oid) throws WTException {
	String tempoid = oid;
	tempoid = tempoid.substring(tempoid.lastIndexOf(":") + 1);

	return tempoid;
    }


    /**
     * <br>
     * 객체 checkOut 여부를 판단하는 Method <br>
     * 
     * @param object
     * @return boolean
     * @exception wt.util.WTException
     **/
    public static boolean isCheckout(WTObject object) throws WTException {
	return WorkInProgressHelper.isCheckedOut((Workable) object);
    }


    /**
     * <br>
     * 객체가 현재 로그인 사용자에 의해 checkOut 되었는지 여부를 판단하는 Method <br>
     * 
     * @param object
     * @return boolean
     * @exception wt.util.WTException
     **/
    public static boolean isCheckoutCurrentUser(WTObject object) throws WTException {
	WTPrincipal currentPrincipal = SessionHelper.manager.getPrincipal();

	return WorkInProgressHelper.isCheckedOut((Workable) object, currentPrincipal);
    }


    /**
     * <br>
     * 객체를 CheckOut하는 Method <br>
     * 
     * @param object
     * @return Hashtable
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public static Hashtable checkOut(WTObject object) throws Exception, WTException {
	Hashtable returnValue = new Hashtable();
	Workable workable = (Workable) object;

	boolean flag = false;

	if (!WorkInProgressHelper.isWorkingCopy(workable)) {
	    flag = !WorkInProgressHelper.isCheckedOut(workable) && !LockHelper.isLocked(workable);
	}

	if (flag) {
	    wt.folder.Folder folder = WorkInProgressHelper.service.getCheckoutFolder();
	    CheckoutLink checkoutlink = WorkInProgressHelper.service.checkout(workable, folder, "");
	    workable = checkoutlink.getWorkingCopy();
	    Workable original = checkoutlink.getOriginalCopy();

	    returnValue.put("WorkableObject", workable);
	    returnValue.put("OriginalObject", original);
	    //			returnValue.put("Message", initiateDownloadHtml(workable));

	    return returnValue;
	}

	return null;
    }


    /**
     * <br>
     * 객체의 oid를 사용하여 CheckOut하는 Method <br>
     * 
     * @param oid
     * @return Hashtable
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public static Hashtable checkOut(String oid) throws Exception, WTException {
	WTObject object = (WTObject) getObject(oid);

	return checkOut(object);
    }

    /**
     * <br>
     * 객체를 CheckIn하는 Method <br>
     * 
     * @param object
     * @return WTObject
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public static WTObject checkIn(WTObject object) throws Exception, WTException {
	Workable workable = (Workable) object;
	boolean flag = false;
	WTObject returnObject = null;

	if (WorkInProgressHelper.isWorkingCopy(workable)) {
	    flag = isCheckoutCurrentUser(object);
	}

	if (flag) {
	    returnObject = (WTObject) WorkInProgressHelper.service.checkin(workable, "");
	}

	return returnObject;
    }

    /**
     * <br>
     * Method <br>
     * 
     * @param object
     * @return WTObject
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public static WTObject revise(WTObject object) throws Exception, WTException {
	return null;
    }


    /**
     * <br>
     * Oid를 가지고 객체를 생성하는 Method <br>
     * 
     * @param oid
     * @return Object
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/

    public static Object getObject(String oid) throws WTException {
	if (oid == null) {
	    return null;
	}

	try {
	    if (rf == null) {
		rf = new ReferenceFactory();
	    }

	    return rf.getReference(oid).getObject();
	} catch (WTException e) {
	    return null;
	}
    }


    /**
     * <br>
     * 로그인 한 사용자의 WTUser객체를 반환하는 Method <br>
     * 
     * @return WTUser
     * @exception wt.util.WTException
     **/
    public static WTUser getLoginUser() throws WTException {
	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();

	return user;
    }


    /**
     * <br>
     * 로그인한 사용자의 Id를 반환하는 Method <br>
     * 
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getLoginUserId() throws WTException {
	return getLoginUser().getName();
    }


    /**
     * <br>
     * 로그인한 사용자의 이름을 반환하는 Method <br>
     * 
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getLoginUserName() throws WTException {
	return getLoginUser().getFullName();
    }


    /**
     * <br>
     * 사용자의 아이디를 이용하여 사용자의 그룹리스트를 가져오는 Method <br>
     * 
     * @param userId
     * @return Vector
     * @exception wt.util.WTException
     **/
    public static Vector getUserGroups(String userId) throws WTException {
	Vector userGroups = new Vector();
	WTUser user = getUser(userId);
	Enumeration en = user.parentGroupNames();

	while (en.hasMoreElements()) {
	    String st = (String) en.nextElement();
	    userGroups.addElement(st);
	}

	return userGroups;
    }


    /**
     * <br>
     * 사용자의 Id를 사용하여 WTUser객체를 가져오는 Method <br>
     * 
     * @param userId
     * @return WTUser
     * @exception wt.util.WTException
     **/
    public static WTUser getUser(String userId) throws WTException {
	return OrganizationServicesHelper.manager.getAuthenticatedUser(userId);
    }


    /**
     * <br>
     * 사용자의 Id와 그룹이름을 가지고 멤버인지 체크하는 Method <br>
     * 
     * @param userId
     * @param groupName
     * @return boolean
     * @exception wt.util.WTException
     **/
    public static boolean isMember(String userId, String groupName) throws WTException {
	WTUser user = getUser(userId);
	Enumeration en = user.parentGroupNames();
	boolean returnFlag = false;

	while (en.hasMoreElements()) {
	    String st = (String) en.nextElement();
	    if (st.equals(groupName)) {
		returnFlag = true;
	    }
	}

	return returnFlag;
    }


    /**
     * <br>
     * 그룹이름을 가지고 현재 로그인한 사용자가 멤버인지 체크하는 Method <br>
     * 
     * @param groupName
     * @return boolean
     * @exception wt.util.WTException
     **/
    public static boolean isMember(String groupName) throws WTException {
	WTUser user = getLoginUser();
	Enumeration en = user.parentGroupNames();
	boolean returnFlag = false;

	while (en.hasMoreElements()) {
	    String st = (String) en.nextElement();
	    if (st.equals(groupName)) {
		returnFlag = true;
	    }
	}

	return returnFlag;
    }


    /**
     * <br>
     * 현재 로그인한 사용자가 Admin인지 체크하는 Method <br>
     * 
     * @return boolean
     * @exception wt.util.WTException
     **/
    public static boolean isAdmin() throws Exception {
	return isMember("Administrators");
    }


    /**
     * <br>
     * 현재 로그인한 사용자가 Biz Admin인지 체크하는 Method <br>
     * 
     * @return boolean
     * @exception wt.util.WTException
     **/
    public static boolean isBizAdmin() throws Exception {
	return isMember("Business Administrators");
    }


    public static String initiateDownloadHtml(Workable workable) throws WTException, PropertyVetoException {
	String returnStr = "";

	if (workable instanceof FormatContentHolder) {
	    FormatContentHolder formatcontentholder = (FormatContentHolder) workable;
	    formatcontentholder = (FormatContentHolder) ContentHelper.service.getContents(formatcontentholder);
	    wt.content.ContentItem contentitem = ContentHelper.getPrimary(formatcontentholder);

	    if (contentitem != null && (contentitem instanceof ApplicationData)) {
		ApplicationData applicationdata = (ApplicationData) contentitem;
		HashMap hashmap = new HashMap();
		hashmap.put("ContentHolder", PersistenceHelper.getObjectIdentifier(formatcontentholder).getStringValue());
		hashmap.put("HttpOperationItem", PersistenceHelper.getObjectIdentifier(applicationdata).getStringValue());
		URLFactory urlfactory = (URLFactory) MethodContext.getContext().get("URLFactory");

		if (urlfactory == null) {
		    urlfactory = new URLFactory();
		}

		URL url = GatewayServletHelper.buildAuthenticatedURL(urlfactory, "wt.content.ContentHttp", "viewContent", applicationdata.getFileName(), hashmap);
		returnStr = "<SCRIPT LANGUAGE=\"JavaScript1.1\">\nthis.top.location.replace(\"" + url.toExternalForm() + "\");</SCRIPT>";
	    }
	}

	return returnStr;
    }


    /**
     * <br>
     * 객체의 현재 LifeCycleState를 반환하는 Method <br>
     * 
     * @param Managed
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getCurrentLifeCycleState(Managed object) throws WTException {
	return object.getLifeCycleState().getDisplay();
    }


    /**
     * <br>
     * 객체의 현재 Version을 가져오는 Method <br>
     * 
     * @param RevisionControlled
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getVersion(RevisionControlled object) throws WTException {
	return object.getVersionIdentifier().getValue();
    }


    /**
     * <br>
     * 객체의 현재 Iteration을 가져오는 Method <br>
     * 
     * @param RevisionControlled
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getIteration(RevisionControlled object) throws WTException {
	return object.getIterationIdentifier().getValue();
    }


    /**
     * <br>
     * 객체의 Team 정보를 가져오는 Method <br>
     * 
     * @param String
     * @return Team
     * @exception wt.util.WTException
     **/
    public static Team getObjTeam(String oid) throws Exception, WTException {
	TeamManaged obj = (TeamManaged) getObject(oid);
	Team team = (Team) obj.getTeamId().getObject();

	return team;
    }


    /**
     * <br>
     * 사용자의 소속 WTGroup 이름을 가져오는 Method <br>
     * 
     * @param WTUser
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getUserGroup(WTUser user) throws Exception, WTException {
	String department_name = "-";
	String tempGroup = "-";
	Enumeration groupEnum = user.parentGroupNames();

	while (groupEnum.hasMoreElements()) {
	    tempGroup = (String) groupEnum.nextElement();

	    if (tempGroup.startsWith("[G]")) {
		department_name = tempGroup.substring(3);

		break;
	    }
	}

	return department_name;
    }


    /**
     * <br>
     * 객체의 현재 Co-Worker 정보를 Vector(WTUser Object) 형태로 가져오는 Method <br>
     * 
     * @param String
     * @return Vector
     * @exception wt.util.WTException
     **/

    public static Vector getCoWorkerVector(String oid) throws Exception, WTException {
	Team team = getObjTeam(oid);
	Enumeration userList = team.getPrincipalTarget(Role.toRole("COWORKER"));
	WTUser wtUser = null;
	Vector userVector = new Vector();
	Hashtable userInfo = new Hashtable();

	while (userList.hasMoreElements()) {
	    wtUser = (WTUser) ((WTPrincipalReference) userList.nextElement()).getPrincipal();
	    String department_name = getUserGroup(wtUser);

	    userInfo.put("userId", wtUser.getAuthenticationName());
	    userInfo.put("userName", wtUser.getFullName());
	    userInfo.put("userDept", department_name);
	    userInfo.put("userMail", wtUser.getEMail());

	    userVector.addElement(userInfo);
	    userInfo = new Hashtable();
	}

	return userVector;
    }


    /**
     * <br>
     * 객체의 현재 Co-Worker 정보를 String(협업자 목록) 형태로 가져오는 Method <br>
     * 
     * @param String
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getCoWorkerString(String oid) throws Exception, WTException {
	Team team = getObjTeam(oid);
	Enumeration userList = team.getPrincipalTarget(Role.toRole("COWORKER"));
	WTUser wtUser = null;
	StringBuffer coWorker = new StringBuffer();
	int i = 0;

	while (userList.hasMoreElements()) {
	    wtUser = (WTUser) ((WTPrincipalReference) userList.nextElement()).getPrincipal();

	    if (i == 0) {
		coWorker.append(wtUser.getFullName());
	    }
	    else {
		coWorker.append(", ").append(wtUser.getFullName());
	    }

	    i++;
	}

	return coWorker.toString();
    }


    /**
     * <br>
     * 객체의 Co-Worker 정보를 변경하는 Method <br>
     * 
     * @param String
     * @param Vector
     * @return void
     * @exception wt.util.WTException
     **/
    public static void setCoWorker(String oid, Vector coWorkerList) throws Exception, WTException {
	Team team = getObjTeam(oid);
	Enumeration userList = team.getPrincipalTarget(Role.toRole("Collaboration Manager"));
	WTPrincipal wtPrincipal = null;

	while (userList.hasMoreElements()) {
	    wtPrincipal = ((WTPrincipalReference) userList.nextElement()).getPrincipal();
	    TeamHelper.service.deleteRolePrincipalMap(Role.toRole("Collaboration Manager"), wtPrincipal, (WTRoleHolder2) team);
	}

	userList = coWorkerList.elements();
	WTUser wtUser = null;
	String webID = "";

	while (userList.hasMoreElements()) {
	    webID = (String) userList.nextElement();
	    wtUser = OrganizationServicesHelper.manager.getAuthenticatedUser(webID);

	    if (wtUser != null) {
		TeamHelper.service.addRolePrincipalMap(Role.toRole("Collaboration Manager"), wtUser, (WTRoleHolder2) team);
	    }
	}

	LifeCycleHelper.service.augmentRoles((LifeCycleManaged) getObject(oid));
    }


    /**
     * <br>
     * WTUser의 이름을 가져오는 Method <br>
     * 
     * @param String
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getUserName(String userId) throws WTException {
	WTUser wtUser = OrganizationServicesHelper.manager.getAuthenticatedUser(userId);

	return wtUser.getFullName();
    }


    /**
     * <br>
     * WTUser의 이름을 가져오는 Method <br>
     * 
     * @param String
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getUserName(WTUser wtuser) throws WTException {
	return wtuser.getFullName();
    }


    /**
     * <br>
     * 객체의 현재 상태를 나타내는 Method <br>
     * 
     * @param Workable
     * @return String
     * @exception wt.util.WTException
     **/
    public static String getObjectStatus(Workable workable) throws WTException, WorkInProgressException {
	return WorkInProgressHelper.getState(workable).getDisplay(new Locale("en"));
    }


    /**
     * <br>
     * 객체의 체크아웃 상태를 확인하는 Method <br>
     * 
     * @param Workable
     * @return boolean
     * @exception wt.util.WTException
     **/
    public static boolean isCheckedOut(Workable workable) throws WTException {
	return WorkInProgressHelper.isCheckedOut(workable);
    }


    /**
     * <br>
     * 현재사용자가 체크아웃한 사용자인지를 확인하는 Method <br>
     * 
     * @param Workable
     * @return boolean
     * @exception wt.util.WTException
     **/
    public static boolean isCheckedOutUser(Workable workable, String userId) throws WTException {
	boolean ischeck = false;
	Workable original = null;

	if (WorkInProgressHelper.isCheckedOut(workable)) {
	    Kogger.debug(KETObjectUtil.class, "workable owner getName : " + workable.getOwnership().getOwner().getName());

	    if (WorkInProgressHelper.isWorkingCopy(workable)) {
		ischeck = workable.getOwnership().getOwner().getName().equals(userId);
	    }
	    else {
		original = WorkInProgressHelper.service.workingCopyOf(workable);
		ischeck = original.getOwnership().getOwner().getName().equals(userId);
	    }
	}

	return ischeck;
    }


    /**
     * <br>
     * 체크인한 사용자를 확인하는 Method <br>
     * 
     * @param Workable
     * @return boolean
     * @exception wt.util.WTException
     **/

    public static WTUser getCheckedOutUser(Workable workable) throws WTException {
	WTUser user = null;

	if (WorkInProgressHelper.isCheckedOut(workable)) {
	    if (WorkInProgressHelper.isWorkingCopy(workable)) {
		user = (WTUser) workable.getOwnership().getOwner().getObject();
	    }
	    else {
		Workable original = WorkInProgressHelper.service.workingCopyOf(workable);
		user = (WTUser) original.getOwnership().getOwner().getObject();
	    }
	}

	return user;
    }


    /**
     * <br>
     * 이름이나 부서로 사용자를 검색하는 Method<br>
     * 
     * @param String
     * @param String
     * @return Vector
     * @exception wt.util.WTException
     **/
    public static Hashtable findUser(String userName, String deptName) throws WTException {
	Hashtable multiData = new Hashtable();
	ArrayList userIdList = new ArrayList();
	Enumeration enumUser = OrganizationServicesHelper.manager.findLikeUsers(WTUser.FULL_NAME, userName, WTContainerHelper.service.getExchangeContainer().getContextProvider());

	while (enumUser.hasMoreElements()) {
	    WTUser wtuser = (WTUser) enumUser.nextElement();
	    userIdList.add(wtuser.getName());
	    multiData.put(WTUser.NAME, wtuser.getName());
	    multiData.put(WTUser.FULL_NAME, wtuser.getFullName());
	    multiData.put(WTUser.TELEPHONE_NUMBER, wtuser.getTelephoneNumber());
	    multiData.put(WTUser.EMAIL, wtuser.getEMail());
	    multiData.put("OID", KETObjectUtil.getOidString(wtuser));
	}

	if (deptName != null && "".equals(deptName)) {
	    multiData = new Hashtable();
	    Enumeration enumGroup = OrganizationServicesHelper.manager.findLikeGroups(deptName, WTContainerHelper.service.getExchangeContainer().getContextProvider());

	    while (enumGroup.hasMoreElements()) {
		WTGroup wtgroup = (WTGroup) enumGroup.nextElement();
		Enumeration enumMember = OrganizationServicesHelper.manager.members(wtgroup);

		if (deptName != null && !deptName.equals("")) {
		    while (enumMember.hasMoreElements()) {
			WTPrincipal wtprincipal = (WTPrincipal) enumMember.nextElement();

			if (wtprincipal instanceof WTUser) {
			    WTUser memberUser = (WTUser) wtprincipal;

			    for (int i = 0; i < userIdList.size(); i++) {
				if (memberUser.getName().equals((String) userIdList.get(i))) {
				    multiData.put(WTUser.NAME, memberUser.getName());
				    multiData.put(WTUser.FULL_NAME, memberUser.getFullName());
				    multiData.put(WTUser.TELEPHONE_NUMBER, memberUser.getTelephoneNumber());
				    multiData.put(WTUser.EMAIL, memberUser.getEMail());
				    multiData.put("OID", KETObjectUtil.getOidString(memberUser));
				}
			    }
			}
		    }
		}
	    }

	    return multiData;
	}

	if (userName != null && userName.equals("")) {
	    return multiData;
	}

	return null;
    }


    public static boolean isWorkItemCreated(String pboOid, String userId) throws Exception {
	WorkItem workItem = null;
	WorkItem workItemE = null;
	WTObject wto = null;

	if (!KETStringUtil.isNull(pboOid)) {
	    WTUser wtu = OrganizationServicesHelper.manager.getAuthenticatedUser(userId);
	    wto = (WTObject) KETObjectUtil.getObject(pboOid);
	    boolean IS_CONTINUE = true;

	    while (IS_CONTINUE) {
		Thread.sleep(1000);
		Enumeration workItemEnum = wt.workflow.work.WorkflowHelper.service.getWorkItems(wto, wtu);

		while (workItemEnum.hasMoreElements()) {
		    workItemE = (WorkItem) workItemEnum.nextElement();
		    workItem = workItemE;
		}

		if (workItem != null) {
		    IS_CONTINUE = false;
		    Kogger.debug(KETObjectUtil.class, "");
		    Kogger.debug(KETObjectUtil.class, "*** Work Item Created");
		}
		else {
		    System.out.print(".");
		}
	    }
	}

	return true;
    }


    /**
     * Item 관련 link중복생성을 막는다.(created by kim, cheol soo 2007/03/26)
     * 
     * @param lsisItem
     * @param epmdocument
     * @return
     * @throws WTException
     */
    public static boolean canCreateEPMDescribeLink(WTPart part, EPMDocument epmdocument) throws WTException {
	boolean returnval = false;
	try {
	    QuerySpec qs = new QuerySpec();

	    int itmIndex = qs.appendClassList(WTPart.class, false);
	    int lnkIndex = qs.appendClassList(EPMDescribeLink.class, true);
	    int epmIndex = qs.appendClassList(EPMDocument.class, false);

	    SearchCondition sc = new SearchCondition(new ClassAttribute(WTPart.class, "thePersistInfo.theObjectIdentifier.id"), "=", new ClassAttribute(EPMDescribeLink.class, "roleAObjectRef.key.id"));
	    sc.setFromIndicies(new int[] { itmIndex, lnkIndex }, 0);
	    qs.appendWhere(sc, new int[] { itmIndex, lnkIndex });
	    qs.appendAnd();

	    sc = new SearchCondition(new ClassAttribute(EPMDescribeLink.class, "roleBObjectRef.key.id"), "=", new ClassAttribute(EPMDocument.class, "thePersistInfo.theObjectIdentifier.id"));
	    sc.setFromIndicies(new int[] { lnkIndex, epmIndex }, 0);
	    qs.appendWhere(sc, new int[] { lnkIndex, epmIndex });
	    qs.appendAnd();

	    //sc = new SearchCondition(new ClassAttribute(LSISItem.class,"thePersistInfo.theObjectIdentifier.id"),"=", lsisItem.getPersistInfo().getObjectIdentifier().getStringValue());
	    sc = new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", "=", part.getPersistInfo().getObjectIdentifier().getStringValue());
	    sc.setFromIndicies(new int[] { itmIndex }, 0);
	    qs.appendWhere(sc, new int[] { itmIndex });

	    qs.appendAnd();
	    //sc = new SearchCondition(new ClassAttribute(EPMDocument.class,"thePersistInfo.theObjectIdentifier.id"),"=", epmdocument.getPersistInfo().getObjectIdentifier().getStringValue());
	    sc = new SearchCondition(EPMDocument.class, "thePersistInfo.theObjectIdentifier.id", "=", epmdocument.getPersistInfo().getObjectIdentifier().getStringValue());
	    sc.setFromIndicies(new int[] { epmIndex }, 0);
	    qs.appendWhere(sc, new int[] { epmIndex });

	    if (DEBUG) {
		Kogger.debug(KETObjectUtil.class, "canCreateEPMDescribeLink query to find EPMDescribeLink " + qs.toString());
	    }

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	    //if (qr != null || qr.size() < 1 ){
	    if (qr != null && qr.size() < 1) {
		returnval = true;
	    }
	    else {
		returnval = false;
	    }
	} catch (Exception e) {
	    Kogger.error(KETObjectUtil.class, e);
	    throw new WTException(e);
	}

	return returnval;
    }

    /**
     * 함수명 : getCurrentUserGroup 설명 : 현재 사용자가 속한 UserGroup Return
     * 
     * @return
     * @throws WTException
     *             작성자 : 오승연 작성일자 : 2010. 11. 23.
     */
    public static String getCurrentUserGroup() throws WTException {
	String userGroup = "";

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

	if (isMember(user.getName(), "자동차사업부")) {
	    userGroup = "자동차사업부";
	}
	else if (isMember(user.getName(), "전자사업부")) {
	    userGroup = "전자사업부";
	}
	else if (isMember(user.getName(), "지원조직")) {
	    userGroup = "지원조직";
	}
	else if (isMember(user.getName(), "지원조직2")) {
	    userGroup = "지원조직2";
	}
	else {
	    try {

		boolean isKETUser = CommonUtil.isKETSUser();
		if (isKETUser) {
		    userGroup = "KETS";
		}

	    } catch (Exception e) {
		Kogger.error(KETObjectUtil.class, e);
	    }

	}

	return userGroup;
    }

    /**
     * 함수명 : getCurrentPartialUserGroup 설명 : 기본 그룹 이외에 부분적으로 쓰이는 그룹 얻는 함수
     * 
     * @return
     * @throws WTException
     *             작성자 : 오승연 작성일자 : 2011. 2. 15.
     */
    public static String getCurrentPartialUserGroup() throws WTException {
	String userGroup = "";

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

	if (isPartialMember(user.getName(), "자동차사업부")) {
	    userGroup = "자동차사업부";
	}
	else if (isPartialMember(user.getName(), "전자사업부")) {
	    userGroup = "전자사업부";
	}
	else if (isPartialMember(user.getName(), "지원조직")) {
	    userGroup = "지원조직";
	}
	else if (isPartialMember(user.getName(), "지원조직2")) {
	    userGroup = "지원조직2";
	}

	return userGroup;
    }

    /**
     * <br>
     * 사용자의 Id와 그룹이름을 가지고 멤버인지 체크하는 Method <br>
     * 
     * @param userId
     * @param groupName
     * @return boolean
     * @exception wt.util.WTException
     **/
    public static boolean isPartialMember(String userId, String groupName) throws WTException {
	WTUser user = getUser(userId);
	Enumeration en = user.parentGroupNames();
	boolean returnFlag = false;

	while (en.hasMoreElements()) {
	    String st = (String) en.nextElement();
	    if (st.startsWith(groupName)) {
		returnFlag = true;
	    }
	}

	return returnFlag;
    }

    public static String getUserDeptName() throws Exception {
	PeopleData pData = new PeopleData();

	return pData.departmentName;
    }

    public static WTGroup getGroup(String groupName) {
	WTGroup group = null;
	QuerySpec spec = null;
	try {
	    spec = new QuerySpec(WTGroup.class);
	    SearchUtil.appendEQUAL(spec, WTGroup.class, WTGroup.NAME, groupName, 0);
	    QueryResult result = PersistenceHelper.manager.find(spec);

	    while (result.hasMoreElements()) {
		group = (WTGroup) result.nextElement();
	    }

	} catch (WTException e) {
	    Kogger.error(KETObjectUtil.class, e);
	}

	return group;
    }
}
