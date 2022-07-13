package e3ps.project.beans;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.introspection.BaseTableInfo;
import wt.introspection.ClassInfo;
import wt.introspection.DatabaseInfo;
import wt.introspection.WTIntrospector;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.util.WTException;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.OutputDocumentLink;
import e3ps.project.ProjectOutput;
import ext.ket.shared.log.Kogger;

public class ProjectManageHelper implements Serializable, RemoteAccess {
    static final boolean                    SERVER  = wt.method.RemoteMethodServer.ServerFlag;
    public static final ProjectManageHelper manager = new ProjectManageHelper();

    private ProjectManageHelper() {
    }

    public static Object getOutputStatusList(HashMap map) throws WTException {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("getOutputStatusList", ProjectManageHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectManageHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectManageHelper.class, e);
	    }
	    return obj;
	}

	try {
	    String pjtNo = (String) map.get("pjtNo");
	    String pjtName = (String) map.get("pjtName");
	    String pjtType = (String) map.get("pjtType");

	    String taskName = (String) map.get("taskName");

	    String outputName = (String) map.get("outputName");
	    String outputRole = (String) map.get("outputRole");
	    String outputLocation = (String) map.get("outputLocation");
	    String ownerOid = (String) map.get("ownerOid");

	    if (pjtNo == null) pjtNo = "";
	    if (pjtName == null) pjtName = "";
	    if (pjtType == null) pjtType = "";
	    if (taskName == null) taskName = "";
	    if (outputName == null) outputName = "";
	    if (outputRole == null) outputRole = "";
	    if (outputLocation == null) outputLocation = "";
	    if (ownerOid == null) ownerOid = "";

	    QuerySpec docSpec = getLatestDocCondition();
	    QuerySpec userSpec = getUserCondition();

	    SubSelectExpression subfrom = new SubSelectExpression(docSpec);
	    subfrom.setFromAlias(new String[] { "docSpec0" }, 0);

	    SubSelectExpression subfrom1 = new SubSelectExpression(userSpec);
	    subfrom1.setFromAlias(new String[] { "userSpec0" }, 0);

	    Class pjt_class = E3PSProject.class;
	    Class task_class = E3PSTask.class;
	    Class output_class = ProjectOutput.class;
	    Class link_class = OutputDocumentLink.class;
	    //Class doc_class = WTDocument.class;

	    QuerySpec spec = new QuerySpec();
	    spec.setAdvancedQueryEnabled(true);

	    int idx_pjt = spec.addClassList(pjt_class, false);
	    int idx_task = spec.appendClassList(task_class, false);
	    int idx_output = spec.appendClassList(output_class, false);
	    int idx_link = spec.appendClassList(link_class, false);
	    int idx_doc = spec.appendFrom(subfrom);
	    int idx_user = spec.appendFrom(subfrom1);


	    //select 절
	    String taskOidAlias = "task_oid";
	    String outputOidAlias = "output_oid";

	    String taskNameAlias = "task_name";

	    String outputNameAlias = "output_name";
	    String outputLocationAlias = "output_location";
	    String outputRoleAlias = "output_role";
	    String outputCompletionAlias = "output_completion";
	    String outputCreateDateAlias = "output_create_date";

	    String docOidAlias = "doc_oid";
	    String docNumberAlias = "doc_number";
	    String docNameAlias = "doc_name";
	    String docStateAlias = "doc_state";
	    String docBranchIdAlias = "doc_branchId";//제외

	    String wtuserOidAlias = "wtuser_oid";
	    String userIdAlias = "user_id";
	    String userNameAlias = "user_name";
	    String deptCodeAlias = "dept_code";
	    String deptNameAlias = "dept_name";

	    String wtuserIdAlias = "wtuser_id";//제외

	    String pjtOidAlias = "pjt_oid";
	    String pjtNoAlias = "pjt_no";
	    String pjtNameAlias = "pjt_name";
	    String pjtTypeAlias = "pjt_type";

	    SearchCondition sc = null;
	    ClassAttribute ca = null;
	    SQLFunction upper = null;
	    ColumnExpression ce = null;

	    //task_oid
	    ClassInfo classinfo = WTIntrospector.getClassInfo(task_class);
	    DatabaseInfo databaseinfo = classinfo.getDatabaseInfo();
	    BaseTableInfo basetableinfo = databaseinfo.getBaseTableInfo();
	    String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_task) + "." + objname + "||':'||" + spec.getFromClause().getAliasAt(idx_task) + "." + objid);
	    ke.setColumnAlias(taskOidAlias);
	    spec.appendSelect(ke, new int[] { idx_task }, false);

	    //output_oid
	    classinfo = WTIntrospector.getClassInfo(output_class);
	    databaseinfo = classinfo.getDatabaseInfo();
	    basetableinfo = databaseinfo.getBaseTableInfo();
	    objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_output) + "." + objname + "||':'||" + spec.getFromClause().getAliasAt(idx_output) + "." + objid);
	    ke.setColumnAlias(outputOidAlias);
	    spec.appendSelect(ke, new int[] { idx_output }, false);

	    //task_name
	    ca = new ClassAttribute(task_class, E3PSTask.TASK_NAME);
	    ca.setColumnAlias(taskNameAlias);
	    spec.appendSelect(ca, new int[] { idx_task }, false);


	    //output_name
	    ca = new ClassAttribute(output_class, ProjectOutput.OUTPUT_NAME);
	    ca.setColumnAlias(outputNameAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);


	    //output_location
	    ca = new ClassAttribute(output_class, ProjectOutput.OUTPUT_LOCATION);
	    ca.setColumnAlias(outputLocationAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);


	    //output_role
	    ca = new ClassAttribute(output_class, ProjectOutput.OUTPUT_ROLE);
	    ca.setColumnAlias(outputRoleAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);


	    //output_completion
	    ca = new ClassAttribute(output_class, ProjectOutput.COMPLETION);
	    ca.setColumnAlias(outputCompletionAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);


	    //output_create_date
	    ca = new ClassAttribute(output_class, "thePersistInfo.createStamp");
	    ca.setColumnAlias(outputCreateDateAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);


	    //doc_oid
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + docOidAlias);
	    ke.setColumnAlias(docOidAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);


	    //doc_number
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + docNumberAlias);
	    ke.setColumnAlias(docNumberAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);


	    //doc_name
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + docNameAlias);
	    ke.setColumnAlias(docNameAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);


	    //doc_state
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + docStateAlias);
	    ke.setColumnAlias(docStateAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);


	    //wtuser_oid
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + wtuserOidAlias);
	    ke.setColumnAlias(wtuserOidAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    //user_id
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + userIdAlias);
	    ke.setColumnAlias(userIdAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    //user_name
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + userNameAlias);
	    ke.setColumnAlias(userNameAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    //dept_code
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + deptCodeAlias);
	    ke.setColumnAlias(deptCodeAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    //dept_name
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + deptNameAlias);
	    ke.setColumnAlias(deptNameAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    //pjt_oid
	    classinfo = WTIntrospector.getClassInfo(pjt_class);
	    databaseinfo = classinfo.getDatabaseInfo();
	    basetableinfo = databaseinfo.getBaseTableInfo();
	    objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_pjt) + "." + objname + "||':'||" + spec.getFromClause().getAliasAt(idx_pjt) + "." + objid);
	    ke.setColumnAlias(pjtOidAlias);
	    spec.appendSelect(ke, new int[] { idx_pjt }, false);

	    //			//pjt_no
	    //			ca = new ClassAttribute(pjt_class, JELProject.PJT_NO);
	    //			ca.setColumnAlias(pjtNoAlias);
	    //			spec.appendSelect(ca, new int[]{idx_pjt}, false);
	    //			
	    //			
	    //    		//pjt_name
	    //			ca = new ClassAttribute(pjt_class, JELProject.PJT_NAME);
	    //			ca.setColumnAlias(pjtNameAlias);
	    //			spec.appendSelect(ca, new int[]{idx_pjt}, false);
	    //			
	    //			
	    //    		//pjt_type
	    //			ca = new ClassAttribute(pjt_class, JELProject.PJT_TYPE);
	    //			ca.setColumnAlias(pjtTypeAlias);
	    //			spec.appendSelect(ca, new int[]{idx_pjt}, false);



	    //where 절

	    SearchCondition sc0 = null;
	    ClassAttribute ca0 = null;
	    ClassAttribute ca1 = null;

	    sc0 = new SearchCondition(pjt_class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true"));
	    spec.appendWhere(sc0, new int[] { idx_pjt });

	    spec.appendAnd();

	    sc0 = new SearchCondition(pjt_class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o");
	    spec.appendWhere(sc0, new int[] { idx_pjt });


	    if (pjtNo.trim().length() > 0) {

		spec.appendAnd();

		//	    		ca0 = new ClassAttribute(pjt_class, JELProject.PJT_NO);
		//	    		upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
		ce = ConstantExpression.newExpression("%" + pjtNo.trim().toUpperCase() + "%");
		sc0 = new SearchCondition(upper, SearchCondition.LIKE, ce);
		spec.appendWhere(sc0, new int[] { idx_pjt });
	    }

	    if (pjtName.trim().length() > 0) {

		spec.appendAnd();

		//	    		ca0 = new ClassAttribute(pjt_class, JELProject.PJT_NAME);
		//	    		upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
		ce = ConstantExpression.newExpression("%" + pjtName.trim().toUpperCase() + "%");
		sc0 = new SearchCondition(upper, SearchCondition.LIKE, ce);
		spec.appendWhere(sc0, new int[] { idx_pjt });
	    }

	    if (pjtType.trim().length() > 0) {

		spec.appendAnd();

		//	    		sc0 = new SearchCondition(pjt_class, JELProject.PJT_TYPE, SearchCondition.EQUAL, Integer.parseInt(pjtType.trim()));
		//	    		spec.appendWhere(sc0, new int[] {idx_pjt});
	    }


	    spec.appendAnd();

	    ca0 = new ClassAttribute(pjt_class, "thePersistInfo.theObjectIdentifier.id");
	    ca1 = new ClassAttribute(task_class, "projectReference.key.id");
	    sc0 = new SearchCondition(ca0, "=", ca1);
	    sc0.setFromIndicies(new int[] { idx_pjt, idx_task }, 0);
	    sc0.setOuterJoin(0);
	    spec.appendWhere(sc0, new int[] { idx_pjt, idx_task });


	    if (taskName.trim().length() > 0) {

		spec.appendAnd();

		ca0 = new ClassAttribute(task_class, E3PSTask.TASK_NAME);
		upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
		ce = ConstantExpression.newExpression("%" + taskName.trim().toUpperCase() + "%");
		sc0 = new SearchCondition(upper, SearchCondition.LIKE, ce);
		spec.appendWhere(sc0, new int[] { idx_task });
	    }

	    spec.appendAnd();

	    ca0 = new ClassAttribute(task_class, "thePersistInfo.theObjectIdentifier.id");
	    ca1 = new ClassAttribute(output_class, "taskReference.key.id");
	    sc0 = new SearchCondition(ca0, "=", ca1);
	    sc0.setFromIndicies(new int[] { idx_task, idx_output }, 0);
	    sc0.setOuterJoin(0);
	    spec.appendWhere(sc0, new int[] { idx_task, idx_output });


	    if (outputName.trim().length() > 0) {

		spec.appendAnd();

		ca0 = new ClassAttribute(output_class, ProjectOutput.OUTPUT_NAME);
		upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
		ce = ConstantExpression.newExpression("%" + outputName.trim().toUpperCase() + "%");
		sc0 = new SearchCondition(upper, SearchCondition.LIKE, ce);
		spec.appendWhere(sc0, new int[] { idx_output });
	    }

	    if (outputRole.trim().length() > 0) {

		spec.appendAnd();

		sc0 = new SearchCondition(output_class, ProjectOutput.OUTPUT_ROLE, SearchCondition.EQUAL, outputRole.trim());
		spec.appendWhere(sc0, new int[] { idx_output });
	    }

	    if (outputLocation.trim().length() > 0) {

		spec.appendAnd();

		ca0 = new ClassAttribute(output_class, ProjectOutput.OUTPUT_LOCATION);
		upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
		ce = ConstantExpression.newExpression("%" + outputLocation.trim().toUpperCase() + "%");
		sc0 = new SearchCondition(upper, SearchCondition.LIKE, ce);
		spec.appendWhere(sc0, new int[] { idx_output });
	    }


	    if (ownerOid.trim().length() > 0) {

		ReferenceFactory rf = new ReferenceFactory();
		WTUser owner = (WTUser) rf.getReference(ownerOid).getObject();
		long ownerId = owner.getPersistInfo().getObjectIdentifier().getId();

		spec.appendAnd();

		sc0 = new SearchCondition(output_class, "owner.key.id", SearchCondition.EQUAL, ownerId);
		spec.appendWhere(sc0, new int[] { idx_output });
	    }

	    spec.appendAnd();

	    ca0 = new ClassAttribute(output_class, "thePersistInfo.theObjectIdentifier.id");
	    ca1 = new ClassAttribute(link_class, "roleAObjectRef.key.id");
	    sc0 = new SearchCondition(ca0, "=", ca1);
	    sc0.setFromIndicies(new int[] { idx_output, idx_link }, 0);
	    sc0.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	    spec.appendWhere(sc0, new int[] { idx_output, idx_link });


	    spec.appendAnd();


	    ca0 = new ClassAttribute(link_class, OutputDocumentLink.BRANCH_IDENTIFIER);
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + docBranchIdAlias + "(+)");
	    sc0 = new SearchCondition(ca0, "=", ke);
	    //sc0.setFromIndicies(new int[]{idx_link, idx_doc}, 0);
	    //sc0.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);            
	    spec.appendWhere(sc0, new int[] { idx_link, idx_doc });


	    spec.appendAnd();


	    ca0 = new ClassAttribute(output_class, "owner.key.id");
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + wtuserIdAlias + "(+)");
	    sc0 = new SearchCondition(ca0, "=", ke);
	    spec.appendWhere(sc0, new int[] { idx_output, idx_user });


	    //order by 절
	    ClassAttribute sortCa = null;
	    OrderBy orderby = null;
	    int sortIdx = 0;

	    //			sortCa = new ClassAttribute(pjt_class, JELProject.PJT_NO);
	    sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	    spec.appendSelect(sortCa, new int[] { idx_pjt }, false);
	    orderby = new OrderBy(sortCa, false, null);
	    spec.appendOrderBy(orderby, new int[] { idx_pjt });


	    sortCa = new ClassAttribute(task_class, "thePersistInfo.theObjectIdentifier.id");
	    sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	    spec.appendSelect(sortCa, new int[] { idx_task }, false);
	    orderby = new OrderBy(sortCa, false, null);
	    spec.appendOrderBy(orderby, new int[] { idx_task });

	    //Kogger.debug(getClass(), "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	    //Kogger.debug(getClass(), spec.toString());
	    //Kogger.debug(getClass(), "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

	    QueryResult qr = PersistenceHelper.manager.find(spec);

	    return qr;
	    /*
	    ArrayList result = new ArrayList();
	    
	    Object arr[] = null;
	    
	    Object obj[] = null;
	    if(qr.hasMoreElements()) {
	    	obj = (Object[])qr.nextElement();
	    	for(int i = 0; i < obj.length; i++) {
	    		Kogger.debug(getClass(), i + " : " + (obj[i]==null? "null":obj[i].getClass().getName()) );
	    	}
	    }
	    */

	    /*
	     * 0 : java.lang.String
	     * 1 : java.lang.String
	     * 2 : java.lang.String
	     * 3 : java.lang.String
	     * 4 : java.lang.String
	     * 5 : null
	     * 6 : java.math.BigDecimal
	     * 7 : wt.util.WrappedTimestamp
	     * 8 : java.lang.String
	     * 9 : java.lang.String
	     * 10 : java.lang.String
	     * 11 : java.math.BigDecimal
	     */
	} catch (Exception e) {
	    Kogger.error(ProjectManageHelper.class, e);
	}

	return null;
    }


    public static QuerySpec getLatestDocCondition() throws WTException {
	if (!SERVER) {
	    Class argTypes[] = null;
	    Object args[] = null;
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getLatestDocCondition", ProjectManageHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectManageHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectManageHelper.class, e);
		throw new WTException(e);
	    }
	    return (QuerySpec) obj;
	}

	QuerySpec spec = null;
	try {
	    String docOidAlias = "doc_oid";
	    String docNumberAlias = "doc_number";
	    String docNameAlias = "doc_name";
	    String docStateAlias = "doc_state";
	    String docBranchIdAlias = "doc_branchId";

	    Class doc_class = WTDocument.class;

	    spec = new QuerySpec();
	    int idx_doc = spec.appendClassList(doc_class, false);

	    ClassAttribute ca = null;
	    //oid
	    ClassInfo classinfo = WTIntrospector.getClassInfo(doc_class);
	    DatabaseInfo databaseinfo = classinfo.getDatabaseInfo();
	    BaseTableInfo basetableinfo = databaseinfo.getBaseTableInfo();
	    String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + objname + "||':'||" + spec.getFromClause().getAliasAt(idx_doc) + "." + objid);
	    ke.setColumnAlias(docOidAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);

	    //number
	    ca = new ClassAttribute(doc_class, "master>number");
	    ca.setColumnAlias(docNumberAlias);
	    spec.appendSelect(ca, new int[] { idx_doc }, false);


	    //name
	    ca = new ClassAttribute(doc_class, "master>name");
	    ca.setColumnAlias(docNameAlias);
	    spec.appendSelect(ca, new int[] { idx_doc }, false);


	    //state
	    ca = new ClassAttribute(doc_class, "state.state");
	    ca.setColumnAlias(docStateAlias);
	    spec.appendSelect(ca, new int[] { idx_doc }, false);

	    //branchId
	    ca = new ClassAttribute(doc_class, "iterationInfo.branchId");
	    ca.setColumnAlias(docBranchIdAlias);
	    spec.appendSelect(ca, new int[] { idx_doc }, false);


	    SearchCondition sc0 = null;
	    sc0 = new SearchCondition(doc_class, "iterationInfo.latest", SearchCondition.IS_TRUE);
	    spec.appendWhere(sc0, new int[] { idx_doc });
	} catch (Exception e) {
	    Kogger.error(ProjectManageHelper.class, e);
	}
	return spec;
    }


    public static QuerySpec getUserCondition() throws WTException {
	if (!SERVER) {
	    Class argTypes[] = null;
	    Object args[] = null;
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getUserCondition", ProjectManageHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectManageHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectManageHelper.class, e);
		throw new WTException(e);
	    }
	    return (QuerySpec) obj;
	}

	QuerySpec spec = null;
	try {
	    String wtuserOidAlias = "wtuser_oid";
	    String userIdAlias = "user_id";
	    String userNameAlias = "user_name";
	    String deptCodeAlias = "dept_code";
	    String deptNameAlias = "dept_name";

	    String wtuserIdAlias = "wtuser_id";

	    Class user_class = WTUser.class;
	    Class people_class = People.class;
	    Class dept_class = Department.class;

	    spec = new QuerySpec();
	    int idx_user = spec.appendClassList(user_class, false);
	    int idx_people = spec.appendClassList(people_class, false);
	    int idx_dept = spec.appendClassList(dept_class, false);

	    ClassAttribute ca = null;
	    //oid
	    ClassInfo classinfo = WTIntrospector.getClassInfo(user_class);
	    DatabaseInfo databaseinfo = classinfo.getDatabaseInfo();
	    BaseTableInfo basetableinfo = databaseinfo.getBaseTableInfo();
	    String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + objname + "||':'||" + spec.getFromClause().getAliasAt(idx_user) + "." + objid);
	    ke.setColumnAlias(wtuserOidAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    //user_id
	    ca = new ClassAttribute(people_class, People.ID);
	    ca.setColumnAlias(userIdAlias);
	    spec.appendSelect(ca, new int[] { idx_people }, false);


	    //user_name
	    ca = new ClassAttribute(people_class, People.NAME);
	    ca.setColumnAlias(userNameAlias);
	    spec.appendSelect(ca, new int[] { idx_people }, false);


	    //	        //dept_code
	    //			ca = new ClassAttribute(dept_class, Department.CODE);
	    //			ca.setColumnAlias(deptCodeAlias);
	    //			spec.appendSelect(ca, new int[]{idx_dept}, false);
	    //	        
	    //	        //dept_name
	    //			ca = new ClassAttribute(dept_class, Department.NAME);
	    //			ca.setColumnAlias(deptNameAlias);
	    //			spec.appendSelect(ca, new int[]{idx_dept}, false);

	    //wtuser_id
	    ca = new ClassAttribute(user_class, "thePersistInfo.theObjectIdentifier.id");
	    ca.setColumnAlias(wtuserIdAlias);
	    spec.appendSelect(ca, new int[] { idx_user }, false);


	    SearchCondition sc0 = null;
	    ClassAttribute ca0 = null;
	    ClassAttribute ca1 = null;

	    ca0 = new ClassAttribute(user_class, "thePersistInfo.theObjectIdentifier.id");
	    ca1 = new ClassAttribute(people_class, "userReference.key.id");
	    sc0 = new SearchCondition(ca0, "=", ca1);
	    sc0.setFromIndicies(new int[] { idx_user, idx_people }, 0);
	    sc0.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	    spec.appendWhere(sc0, new int[] { idx_user, idx_people });

	    spec.appendAnd();

	    ca0 = new ClassAttribute(people_class, "departmentReference.key.id");
	    ca1 = new ClassAttribute(dept_class, "thePersistInfo.theObjectIdentifier.id");
	    sc0 = new SearchCondition(ca0, "=", ca1);
	    sc0.setFromIndicies(new int[] { idx_people, idx_dept }, 0);
	    sc0.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	    spec.appendWhere(sc0, new int[] { idx_people, idx_dept });

	} catch (Exception e) {
	    Kogger.error(ProjectManageHelper.class, e);
	}
	return spec;
    }

}
