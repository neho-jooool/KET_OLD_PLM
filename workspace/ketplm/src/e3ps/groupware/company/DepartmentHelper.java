/**
 * @(#) DepartmentHelper.java Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.groupware.company;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.org.WTUser;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.impl.Tree;
import e3ps.common.impl.TreeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import ext.ket.shared.log.Kogger;

public class DepartmentHelper {
    public static final DepartmentHelper manager = new DepartmentHelper();

    protected DepartmentHelper() {
    }

    public ArrayList getAllList() {
	ArrayList returnData = new ArrayList();
	try {
	    QuerySpec spec = new QuerySpec();
	    Class mainClass = Department.class;
	    int mainClassPos = spec.addClassList(mainClass, true);
	    // CommonUtil.setOrderBy(spec, mainClass, mainClassPos, Department.CODE, "code", false);
	    SearchUtil.setOrderBy(spec, mainClass, mainClassPos, "thePersistInfo.createStamp", "createStamp", false);
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		returnData.add(obj[0]);
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
	return returnData;
    }

    public ArrayList getTopList() throws Exception {
	ArrayList returnData = new ArrayList();
	try {
	    QuerySpec spec = getTopQuerySpec();
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		if (CommonUtil.isKETSUser()) {
		    Department depart = (Department) obj[0];
		    if ("케이이티솔루션㈜".equals(depart.getName())) {
			returnData.add(obj[0]);
		    }
		} else {
		    returnData.add(obj[0]);
		}
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
	return returnData;
    }

    public QuerySpec getTopQuerySpec() throws WTPropertyVetoException, QueryException, WTIntrospectionException {
	QuerySpec spec = new QuerySpec();
	Class mainClass = Department.class;
	int mainClassPos = spec.addClassList(mainClass, true);
	spec.appendWhere(new SearchCondition(mainClass, "parentReference.key.id", SearchCondition.EQUAL, (long) 0),
	        new int[] { mainClassPos });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(mainClass, Department.ENABLED, SearchCondition.IS_TRUE, true), new int[] { mainClassPos });

	// CommonUtil.setOrderBy(spec, mainClass, mainClassPos, Department.CODE, "code", false);
	SearchUtil.setOrderBy(spec, mainClass, mainClassPos, "sort", "sort", false);
	return spec;
    }

    public ArrayList getChildList(Department department) {
	ArrayList returnData = new ArrayList();
	try {
	    QuerySpec spec = getChildQuerySpec(department);
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		returnData.add(obj[0]);
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return returnData;
    }

    public QuerySpec getChildQuerySpec(Department tree) throws WTPropertyVetoException, QueryException, Exception, WTIntrospectionException {
	QuerySpec spec = new QuerySpec();
	Class mainClass = Department.class;
	int mainClassPos = spec.addClassList(mainClass, true);
	spec.appendWhere(new SearchCondition(mainClass, "parentReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(tree)),
	        new int[] { mainClassPos });

	if (!CommonUtil.isAdmin()) {
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(mainClass, Department.ENABLED, SearchCondition.IS_TRUE, true), new int[] { mainClassPos });
	}
	// CommonUtil.setOrderBy(spec, mainClass, mainClassPos, Department.CODE, "code", false);
	SearchUtil.setOrderBy(spec, mainClass, mainClassPos, Department.SORT, "createStamp", false);
	return spec;
    }

    public ArrayList getAllChildList(Department department, ArrayList returnlist) {
	try {

	    QuerySpec spec = getChildQuerySpec(department);
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		returnlist.add(obj[0]);
		getAllChildList((Department) obj[0], returnlist);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return returnlist;
    }

    public ArrayList getParentsList(Department department, ArrayList returnlist) {
	if (department == null) {
	    return returnlist;
	}
	Department parent = (Department) department.getParent();
	if (parent != null) {
	    returnlist.add(parent);
	    getParentsList(parent, returnlist);
	}
	return returnlist;
    }

    public Department getRootParentDepartment(Department department, Department temp, String excludeCode) {
	if (department == null) {
	    return null;
	}
	Department parent = (Department) department.getParent();
	if (parent != null && !excludeCode.equals(parent.getCode())) {
	    temp = parent;
	    getRootParentDepartment(parent, temp, excludeCode);
	}
	return temp;
    }

    public Department getDepartment(WTUser user) throws Exception {
	Department department = null;
	QueryResult qr = PersistenceHelper.manager.navigate(user, "people", WTUserPeopleLink.class);
	if (qr.hasMoreElements()) {
	    People people = (People) qr.nextElement();
	    QueryResult subQr = PersistenceHelper.manager.navigate(people, "department", DepartmentPeopleLink.class);
	    if (subQr.hasMoreElements()) {
		department = (Department) subQr.nextElement();
	    }
	}
	return department;

    }

    public String getDepartmentName(WTUser user) {
	String returnData = "";
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(user, "people", WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
		People people = (People) qr.nextElement();
		QueryResult subQr = PersistenceHelper.manager.navigate(people, "department", DepartmentPeopleLink.class);
		if (subQr.hasMoreElements()) {
		    Department department = (Department) subQr.nextElement();
		    returnData = department.getName();
		}
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return returnData;
    }

    public String getDepartmentEnName(WTUser user) {
	String returnData = "";
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(user, "people", WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
		People people = (People) qr.nextElement();
		QueryResult subQr = PersistenceHelper.manager.navigate(people, "department", DepartmentPeopleLink.class);
		if (subQr.hasMoreElements()) {
		    Department department = (Department) subQr.nextElement();
		    returnData = department.getEnName();
		}
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return returnData;
    }

    public Department getDepartment(String code, boolean enabled) {

	try {
	    QuerySpec spec = new QuerySpec();
	    Class mainClass = Department.class;
	    int mainClassPos = spec.addClassList(mainClass, true);
	    spec.appendWhere(new SearchCondition(mainClass, Department.CODE, SearchCondition.EQUAL, code), new int[] { mainClassPos });
	    if (enabled) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(mainClass, Department.ENABLED, SearchCondition.IS_TRUE, true),
		        new int[] { mainClassPos });
	    }
	    QueryResult qr = PersistenceHelper.manager.find(spec);

	    if (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		return (Department) obj[0];
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public Department getDepartment(String code) {
	return getDepartment(code, true);
    }

    public Department getDepartmentObj(String departmentName) {
	try {
	    QuerySpec spec = new QuerySpec();
	    Class mainClass = Department.class;
	    int mainClassPos = spec.addClassList(mainClass, true);
	    spec.appendWhere(new SearchCondition(mainClass, Department.NAME, SearchCondition.EQUAL, departmentName),
		    new int[] { mainClassPos });
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    if (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		return (Department) obj[0];
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getDepartmentPeople(Department dept) {

	QuerySpec spec = null;
	QueryResult rs = null;
	try {
	    String oid = CommonUtil.getOIDString(dept);
	    spec = new QuerySpec();
	    Class target = People.class;
	    int idx = spec.addClassList(target, true);

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    // 하위 부서도 모두 출력되게 수정 ( 2006.04.04 Choi Seunghwan )
	    ArrayList list = TreeHelper.manager.getAllChildList(Department.class, (Tree) CommonUtil.getObject(oid), new ArrayList());
	    spec.appendOpenParen();
	    for (int i = list.size() - 1; i > -1; i--) {
		Department temp = (Department) list.get(i);
		spec.appendWhere(new SearchCondition(target, "departmentReference.key.id", "=", CommonUtil.getOIDLongValue(temp)),
		        new int[] { idx });
		spec.appendOr();
	    }
	    spec.appendWhere(new SearchCondition(target, "departmentReference.key.id", "=", CommonUtil.getOIDLongValue(oid)),
		    new int[] { idx });
	    spec.appendCloseParen();

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(target, People.IS_DISABLE, SearchCondition.IS_FALSE), new int[] { idx });

	    rs = PersistenceHelper.manager.find(spec);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return rs;
    }

    public QueryResult getDepartmentPeople(Department dept, String userName, boolean isValid) {

	QuerySpec spec = null;
	QueryResult rs = null;
	try {

	    spec = new QuerySpec();
	    Class target = People.class;
	    int idx = spec.addClassList(target, true);

	    if (dept != null) {
		String oid = CommonUtil.getOIDString(dept);
		ArrayList list = TreeHelper.manager.getAllChildList(Department.class, (Tree) CommonUtil.getObject(oid), new ArrayList());
		spec.appendOpenParen();
		for (int i = list.size() - 1; i > -1; i--) {
		    Department temp = (Department) list.get(i);
		    spec.appendWhere(new SearchCondition(target, "departmentReference.key.id", "=", CommonUtil.getOIDLongValue(temp)),
			    new int[] { idx });
		    spec.appendOr();
		}
		spec.appendWhere(new SearchCondition(target, "departmentReference.key.id", "=", CommonUtil.getOIDLongValue(oid)),
		        new int[] { idx });
		spec.appendCloseParen();
	    }

	    if (isValid) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(target, People.IS_DISABLE, SearchCondition.IS_FALSE), new int[] { idx });
	    }

	    if (userName != null && userName.trim().length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(target, People.NAME, SearchCondition.LIKE, userName.trim() + "%"), new int[] { idx });
	    }

	    rs = PersistenceHelper.manager.find(spec);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return rs;
    }

    public QueryResult getDepartmentPeople(Department dept, String userName, boolean isValid, String fromDate, String toDate) {

	QuerySpec spec = null;
	QueryResult rs = null;
	try {

	    spec = new QuerySpec();
	    Class target = People.class;
	    int idx = spec.addClassList(target, true);

	    if (dept != null) {
		String oid = CommonUtil.getOIDString(dept);
		ArrayList list = TreeHelper.manager.getAllChildList(Department.class, (Tree) CommonUtil.getObject(oid), new ArrayList());
		spec.appendOpenParen();
		for (int i = list.size() - 1; i > -1; i--) {
		    Department temp = (Department) list.get(i);
		    spec.appendWhere(new SearchCondition(target, "departmentReference.key.id", "=", CommonUtil.getOIDLongValue(temp)),
			    new int[] { idx });
		    spec.appendOr();
		}
		spec.appendWhere(new SearchCondition(target, "departmentReference.key.id", "=", CommonUtil.getOIDLongValue(oid)),
		        new int[] { idx });
		spec.appendCloseParen();
	    }

	    if (isValid) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(target, People.IS_DISABLE, SearchCondition.IS_TRUE), new int[] { idx });
	    }

	    if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
		ext.ket.shared.util.SearchUtil.appendTimeFrom(spec, target, "retrireDate", DateUtil.convertStartDate2(fromDate), idx);
		ext.ket.shared.util.SearchUtil.appendTimeTo(spec, target, "retrireDate", DateUtil.convertEndDate2(toDate), idx);
	    }

	    if (userName != null && userName.trim().length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(target, People.NAME, SearchCondition.LIKE, userName.trim() + "%"), new int[] { idx });
	    }

	    rs = PersistenceHelper.manager.find(spec);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return rs;
    }

    public ArrayList getChildDeptTree(Department dept) {
	ArrayList result = null;
	try {
	    result = new ArrayList();
	    setChildDeptTree(dept, result);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return result;
    }

    public void setChildDeptTree(Department dept, ArrayList list) {
	try {
	    if (list == null) {
		list = new ArrayList();
	    }
	    QuerySpec spec = getChildQuerySpec(dept);
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    Department child = null;
	    Object[] obj = null;
	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		child = (Department) obj[0];
		list.add(child);
		setChildDeptTree(child, list);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public ArrayList getChildEndNodeDept(Department dept) {
	ArrayList result = null;
	try {
	    result = new ArrayList();
	    setChildEndNodeDept(dept, result);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return result;
    }

    public void setChildEndNodeDept(Department dept, ArrayList list) {
	try {
	    if (list == null) {
		list = new ArrayList();
	    }
	    QuerySpec spec = getChildQuerySpec(dept);
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    if (qr.size() == 0) {
		list.add(dept);
	    } else {
		Department child = null;
		Object[] obj = null;
		while (qr.hasMoreElements()) {
		    obj = (Object[]) qr.nextElement();
		    child = (Department) obj[0];
		    setChildEndNodeDept(child, list);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public WTUser getDepartChief(Department depart) throws Exception {

	if (depart == null) {
	    return null;
	}

	WTUser user = null;

	QuerySpec spec = new QuerySpec();
	Class mainClass = People.class;
	int mainClassPos = spec.addClassList(mainClass, true);
	spec.appendWhere(
	        new SearchCondition(mainClass, "departmentReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(depart)),
	        new int[] { mainClassPos });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(mainClass, "chief", SearchCondition.NOT_NULL, true), new int[] { mainClassPos });

	QueryResult qr = PersistenceHelper.manager.find(spec);
	if (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    People peop = (People) obj[0];
	    user = peop.getUser();
	}
	return user;

    }

    public Department getRootDepartment(Object obj) throws Exception {

	Department tempDept = null;
	Department parent = null;

	if (obj instanceof Department) {
	    tempDept = (Department) obj;

	} else if (obj instanceof WTUser || obj instanceof People) {
	    PeopleData peo = new PeopleData(obj);
	    tempDept = peo.department;
	}

	while (tempDept != null) {
	    parent = tempDept;
	    tempDept = (Department) tempDept.getParent();
	}

	return parent;
    }
}
