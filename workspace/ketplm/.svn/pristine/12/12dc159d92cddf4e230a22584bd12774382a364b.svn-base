package e3ps.project.beans;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.enterprise.Master;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.ObjectNoLongerExistsException;
import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.introspection.BaseTableInfo;
import wt.introspection.ClassInfo;
import wt.introspection.DatabaseInfo;
import wt.introspection.WTIntrospector;
import wt.lifecycle.State;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.pds.DatabaseInfoUtilities;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.CompoundQuerySpec;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.query.SubSelectExpression;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.db.DBCPManager;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentOutputLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.service.KETDmsHelper;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.project.AssessProjectOutputLink;
import e3ps.project.AssessTemplateTaskLink;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.KETDqmRaiseOutputLink;
import e3ps.project.KETMoldChangeOrderOutputLink;
import e3ps.project.KETProdChangeOrderOutputLink;
import e3ps.project.KETTryMoldOutputLink;
import e3ps.project.KETTryPressOutputLink;
import e3ps.project.OutputDocumentLink;
import e3ps.project.OutputViewDepartMentLink;
import e3ps.project.OutputViewMemberLink;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOutput;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.TaskAssessResult;
import e3ps.project.TaskTrustResult;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.TrustProjectOutputLink;
import e3ps.project.TrustTemplateTaskLink;
import e3ps.project.historyprocess.HistoryHelper;
import e3ps.project.outputtype.OEMProjectType;
import e3ps.project.outputtype.ProjectOutPutType;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.project.gate.entity.AssessResultOutputLink;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.project.trycondition.entity.KETTryMold;
import ext.ket.project.trycondition.entity.KETTryPress;
import ext.ket.shared.log.Kogger;

public class ProjectOutputHelper implements Serializable, RemoteAccess {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    public static final ProjectOutputHelper manager = new ProjectOutputHelper();

    private ProjectOutputHelper() {
    }

    public static QuerySpec getOutputLinkList(HashMap map) throws WTException {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault()
		        .invoke("getOutputLinkList", ProjectOutputHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		// TODO Auto-generated catch block
		Kogger.error(ProjectOutputHelper.class, e);
	    } catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		Kogger.error(ProjectOutputHelper.class, e);
	    }
	    return (QuerySpec) obj;
	}

	String docTypeOid = StringUtil.checkNull((String) map.get("docTypeOid"));
	String name = StringUtil.checkNull((String) map.get("name"));
	String number = StringUtil.checkNull((String) map.get("number"));
	String predate = StringUtil.checkNull((String) map.get("predate"));
	String postdate = StringUtil.checkNull((String) map.get("postdate"));
	QuerySpec mainSpec = null;
	QuerySpec spec = null;
	try {
	    mainSpec = new QuerySpec();
	    int main_idx = mainSpec.addClassList(KETProjectDocument.class, false);

	    if (!mainSpec.isAdvancedQueryEnabled()) {
		mainSpec.setAdvancedQueryEnabled(true);
	    }

	    KeywordExpression kem = new KeywordExpression("A0.CLASSNAMEA2A2||':'||A0.IDA2A2");
	    // kem.setColumnAlias("OID");
	    mainSpec.appendSelect(kem, new int[] { main_idx }, true);

	    spec = new QuerySpec();
	    int idx_doc = spec.addClassList(KETProjectDocument.class, true);
	    // int idx_master = spec.addClassList(WTDocumentMaster.class, false);
	    int idx_mctg = spec.addClassList(KETDocumentCategory.class, false);
	    int idx_ctg = spec.addClassList(KETDocumentCategory.class, false);
	    int idx_mctgLink = spec.addClassList(KETDocumentCategoryLink.class, false);

	    // A.CLASSNAMEA2A2||':'||A.IDA2A2 projectId,
	    // ClassAttribute ca = new ClassAttribute(KETProjectDocument.class, wt.util.WTAttributeNameIfc.ID_NAME);
	    // ca.setColumnAlias("projectId");
	    // spec.appendSelect(ca, true);
	    spec.isAdvancedQuery();
	    // spec.isSingleResultCompatible();
	    // spec.isUseBind();

	    KeywordExpression ke1 = new KeywordExpression("A0.CLASSNAMEA2A2||':'||A0.IDA2A2");
	    // ke1.setColumnAlias("OID");
	    spec.appendSelect(ke1, new int[] { idx_doc }, true);

	    ClassAttribute viv = new ClassAttribute(KETProjectDocument.class, "versionInfo.identifier.versionId");
	    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, viv);

	    KeywordExpression ke2 = new KeywordExpression(sqlfunction + " OVER(PARTITION BY A0.DOCUMENTNO, A0.LATESTITERATIONINFO)");
	    ke2.setColumnAlias("MAXVER");
	    spec.appendSelect(ke2, new int[] { idx_doc }, true);

	    ClassAttribute ca = new ClassAttribute(KETProjectDocument.class, "versionInfo.identifier.versionId");
	    spec.appendSelect(ca, new int[] { idx_doc }, true);

	    SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.EQUAL,
		    new KeywordExpression("A0.VERSIONIDA2VERSIONINFO"));
	    spec.appendWhere(dsc, new int[] { idx_doc });

	    ClassAttribute ca1 = null;
	    ClassAttribute ca2 = null;
	    ca1 = new ClassAttribute(KETProjectDocument.class, wt.util.WTAttributeNameIfc.ID_NAME);
	    ca2 = new ClassAttribute(KETDocumentCategoryLink.class, "roleBObjectRef.key.id");
	    SearchCondition where = new SearchCondition(ca1, "=", ca2);
	    where.setFromIndicies(new int[] { idx_doc, idx_mctgLink }, 0);
	    where.setOuterJoin(0);
	    spec.appendWhere(where, new int[] { idx_doc, idx_mctgLink });

	    spec.appendAnd();
	    ca1 = new ClassAttribute(KETDocumentCategory.class, wt.util.WTAttributeNameIfc.ID_NAME);
	    ca2 = new ClassAttribute(KETDocumentCategoryLink.class, "roleAObjectRef.key.id");
	    SearchCondition where2 = new SearchCondition(ca1, "=", ca2);
	    where2.setFromIndicies(new int[] { idx_ctg, idx_mctgLink }, 0);
	    where2.setOuterJoin(0);
	    spec.appendWhere(where2, new int[] { idx_ctg, idx_mctgLink });

	    /*
	     * spec.appendAnd(); ca1 = new ClassAttribute(KETProjectDocument.class, "masterReference.key.id"); ca2 = new
	     * ClassAttribute(WTDocumentMaster.class, wt.util.WTAttributeNameIfc.ID_NAME); SearchCondition where3 = new SearchCondition(ca1,
	     * "=", ca2); where3.setFromIndicies(new int[] {idx_doc, idx_master}, 0); where3.setOuterJoin(0); spec.appendWhere(where3, new
	     * int[]{idx_doc, idx_master});
	     */

	    // 문서 분류
	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", SearchCondition.EQUAL, docTypeOid),
		    new int[] { idx_mctg });

	    if (number.length() > 0) {
		number = number.replace("*", "%");
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(
		        new SearchCondition(KETProjectDocument.class, KETProjectDocument.DOCUMENT_NO, SearchCondition.LIKE, number),
		        new int[] { idx_doc });
		number = number.replace("%", "*");

	    }
	    if (name.length() > 0) {
		name = name.replace("*", "%");
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(KETProjectDocument.class, KETProjectDocument.TITLE, SearchCondition.LIKE, name),
		        new int[] { idx_doc });
		name = name.replace("%", "*");
	    }

	    if (predate.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(
		        new SearchCondition(KETProjectDocument.class, "thePersistInfo.createStamp", ">=", DateUtil
		                .convertStartDate2(predate.trim())), new int[] { idx_doc });
	    }

	    if (postdate.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(
		        new SearchCondition(KETProjectDocument.class, "thePersistInfo.createStamp", "<", DateUtil.convertEndDate2(postdate
		                .trim())), new int[] { idx_doc });
	    }

	    if (postdate.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(
		        new SearchCondition(KETProjectDocument.class, "thePersistInfo.createStamp", "<", DateUtil.convertEndDate2(postdate
		                .trim())), new int[] { idx_doc });
	    }

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendOpenParen();
	    spec.appendWhere(
		    new SearchCondition(KETProjectDocument.class, "iterationInfo.creator.key.id", "=", CommonUtil
		            .getOIDLongValue(SessionHelper.manager.getPrincipal())), new int[] { idx_doc });
	    // spec.appendOr();
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(KETProjectDocument.class, "state.state", "=", "APPROVED"), new int[] { idx_doc });
	    spec.appendCloseParen();

	    // versionInfo.identifier.versionId
	    // iterationInfo.latest

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(KETProjectDocument.class, "iterationInfo.latest", SearchCondition.IS_TRUE, true),
		    new int[] { idx_doc });

	    SubSelectExpression subfrom = new SubSelectExpression(spec);
	    subfrom.setFromAlias(new String[] { "B0" }, 0);
	    int sub_pjt_index = mainSpec.appendFrom(subfrom);

	    if (mainSpec.getConditionCount() > 0)
		mainSpec.appendAnd();

	    SearchCondition sc = new SearchCondition(new ClassAttribute(KETProjectDocument.class, "versionInfo.identifier.versionId"), "=",
		    new KeywordExpression("A1.MAXVER"));
	    sc.setFromIndicies(new int[] { main_idx, sub_pjt_index }, 0);
	    sc.setOuterJoin(0);
	    mainSpec.appendWhere(sc, new int[] { main_idx, sub_pjt_index });

	    Kogger.debug(ProjectOutputHelper.class, "");
	    Kogger.debug(ProjectOutputHelper.class, mainSpec);

	} catch (QueryException e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}

	return spec;
    }

    public static Object getOutputStatusList(E3PSProject project) throws WTException {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { E3PSProject.class };
	    Object args[] = new Object[] { project };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("getOutputStatusList", ProjectOutputHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		// TODO Auto-generated catch block
		Kogger.error(ProjectOutputHelper.class, e);
	    } catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		Kogger.error(ProjectOutputHelper.class, e);
	    }
	    return obj;
	}

	try {
	    // long pjtID = project.getPersistInfo().getObjectIdentifier().getId();

	    QuerySpec docSpec = getLatestDocCondition();
	    QuerySpec userSpec = getUserCondition();

	    SubSelectExpression subfrom = new SubSelectExpression(docSpec);
	    subfrom.setFromAlias(new String[] { "docSpec0" }, 0);

	    SubSelectExpression subfrom1 = new SubSelectExpression(userSpec);
	    subfrom1.setFromAlias(new String[] { "userSpec0" }, 0);

	    // Class pjt_class = JELProject.class;
	    Class task_calss = E3PSTask.class;
	    Class output_class = ProjectOutput.class;
	    Class link_class = OutputDocumentLink.class;
	    // Class doc_class = WTDocument.class;

	    QuerySpec spec = new QuerySpec();
	    spec.setAdvancedQueryEnabled(true);

	    // int idx_pjt = subQs.addClassList(pjt_class, false);
	    int idx_task = spec.appendClassList(task_calss, false);
	    int idx_output = spec.appendClassList(output_class, false);
	    int idx_link = spec.appendClassList(link_class, false);
	    int idx_doc = spec.appendFrom(subfrom);
	    int idx_user = spec.appendFrom(subfrom1);

	    // select 절
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
	    String docBranchIdAlias = "doc_branchId";

	    String wtuserOidAlias = "wtuser_oid";
	    String userIdAlias = "user_id";
	    String userNameAlias = "user_name";
	    String deptCodeAlias = "dept_code";
	    String deptNameAlias = "dept_name";

	    String wtuserIdAlias = "wtuser_id";

	    SearchCondition sc = null;
	    ClassAttribute ca = null;
	    SQLFunction upper = null;
	    ColumnExpression ce = null;

	    // task_oid
	    ClassInfo classinfo = WTIntrospector.getClassInfo(task_calss);
	    DatabaseInfo databaseinfo = classinfo.getDatabaseInfo();
	    BaseTableInfo basetableinfo = databaseinfo.getBaseTableInfo();
	    String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_task) + "." + objname + "||':'||"
		    + spec.getFromClause().getAliasAt(idx_task) + "." + objid);
	    ke.setColumnAlias(taskOidAlias);
	    spec.appendSelect(ke, new int[] { idx_task }, false);

	    // output_oid
	    classinfo = WTIntrospector.getClassInfo(output_class);
	    databaseinfo = classinfo.getDatabaseInfo();
	    basetableinfo = databaseinfo.getBaseTableInfo();
	    objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_output) + "." + objname + "||':'||"
		    + spec.getFromClause().getAliasAt(idx_output) + "." + objid);
	    ke.setColumnAlias(outputOidAlias);
	    spec.appendSelect(ke, new int[] { idx_task }, false);

	    // task_name
	    ca = new ClassAttribute(task_calss, E3PSTask.TASK_NAME);
	    ca.setColumnAlias(taskNameAlias);
	    spec.appendSelect(ca, new int[] { idx_task }, false);

	    // output_name
	    ca = new ClassAttribute(output_class, ProjectOutput.OUTPUT_NAME);
	    ca.setColumnAlias(outputNameAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);

	    // output_location
	    ca = new ClassAttribute(output_class, ProjectOutput.OUTPUT_LOCATION);
	    ca.setColumnAlias(outputLocationAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);

	    // output_role
	    ca = new ClassAttribute(output_class, ProjectOutput.OUTPUT_ROLE);
	    ca.setColumnAlias(outputRoleAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);

	    // output_completion
	    ca = new ClassAttribute(output_class, ProjectOutput.COMPLETION);
	    ca.setColumnAlias(outputCompletionAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);

	    // output_create_date
	    ca = new ClassAttribute(output_class, "thePersistInfo.createStamp");
	    ca.setColumnAlias(outputCreateDateAlias);
	    spec.appendSelect(ca, new int[] { idx_output }, false);

	    // doc_oid
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + docOidAlias);
	    ke.setColumnAlias(docOidAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);

	    // doc_number
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + docNumberAlias);
	    ke.setColumnAlias(docNumberAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);

	    // doc_name
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + docNameAlias);
	    ke.setColumnAlias(docNameAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);

	    // doc_state
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + docStateAlias);
	    ke.setColumnAlias(docStateAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);

	    // wtuser_oid
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + wtuserOidAlias);
	    ke.setColumnAlias(wtuserOidAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    // user_id
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + userIdAlias);
	    ke.setColumnAlias(userIdAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    // user_name
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + userNameAlias);
	    ke.setColumnAlias(userNameAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    // dept_code
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + deptCodeAlias);
	    ke.setColumnAlias(deptCodeAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    // dept_name
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + deptNameAlias);
	    ke.setColumnAlias(deptNameAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    // where 절

	    SearchCondition sc0 = null;
	    ClassAttribute ca0 = null;
	    ClassAttribute ca1 = null;

	    // sc0 = new SearchCondition(task_calss, "projectReference.key.id",
	    // SearchCondition.EQUAL, pjtID);
	    // spec.appendWhere(sc0, new int[]{idx_task});
	    //
	    // spec.appendAnd();
	    //
	    // ca0 = new ClassAttribute(task_calss, "thePersistInfo.theObjectIdentifier.id");
	    // ca1 = new ClassAttribute(output_class, "taskReference.key.id");
	    // sc0 = new SearchCondition(ca0, "=", ca1);
	    // sc0.setFromIndicies(new int[]{idx_task, idx_output}, 0);
	    // sc0.setOuterJoin(0);
	    // spec.appendWhere(sc0, new int[]{idx_task, idx_output});
	    //
	    // spec.appendAnd();
	    //
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
	    // sc0.setFromIndicies(new int[]{idx_link, idx_doc}, 0);
	    // sc0.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	    spec.appendWhere(sc0, new int[] { idx_link, idx_doc });

	    spec.appendAnd();

	    ca0 = new ClassAttribute(output_class, "owner.key.id");
	    ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + wtuserIdAlias + "(+)");
	    sc0 = new SearchCondition(ca0, "=", ke);
	    spec.appendWhere(sc0, new int[] { idx_output, idx_user });

	    // order by 절
	    ClassAttribute sortCa = null;
	    OrderBy orderby = null;
	    int sortIdx = 0;

	    sortCa = new ClassAttribute(task_calss, "thePersistInfo.theObjectIdentifier.id");
	    sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	    spec.appendSelect(sortCa, new int[] { idx_task }, false);
	    orderby = new OrderBy(sortCa, false, null);
	    spec.appendOrderBy(orderby, new int[] { idx_task });

	    QueryResult qr = PersistenceHelper.manager.find(spec);

	    return qr;
	    /*
	     * ArrayList result = new ArrayList();
	     * 
	     * Object arr[] = null;
	     * 
	     * Object obj[] = null; if(qr.hasMoreElements()) { obj = (Object[])qr.nextElement(); for(int i = 0; i < obj.length; i++) {
	     * Kogger.debug(getClass(), i + " : " + (obj[i]==null? "null":obj[i].getClass().getName()) ); } }
	     */

	    /*
	     * 0 : java.lang.String 1 : java.lang.String 2 : java.lang.String 3 : java.lang.String 4 : java.lang.String 5 : null 6 :
	     * java.math.BigDecimal 7 : wt.util.WrappedTimestamp 8 : java.lang.String 9 : java.lang.String 10 : java.lang.String 11 :
	     * java.math.BigDecimal
	     */
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}

	return null;
    }

    public static QuerySpec getLatestDocCondition() throws WTException {
	if (!SERVER) {
	    Class argTypes[] = null;
	    Object args[] = null;
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getLatestDocCondition", ProjectOutputHelper.class.getName(), null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
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
	    // oid
	    ClassInfo classinfo = WTIntrospector.getClassInfo(doc_class);
	    DatabaseInfo databaseinfo = classinfo.getDatabaseInfo();
	    BaseTableInfo basetableinfo = databaseinfo.getBaseTableInfo();
	    String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_doc) + "." + objname + "||':'||"
		    + spec.getFromClause().getAliasAt(idx_doc) + "." + objid);
	    ke.setColumnAlias(docOidAlias);
	    spec.appendSelect(ke, new int[] { idx_doc }, false);

	    // number
	    ca = new ClassAttribute(doc_class, "master>number");
	    ca.setColumnAlias(docNumberAlias);
	    spec.appendSelect(ca, new int[] { idx_doc }, false);

	    // name
	    ca = new ClassAttribute(doc_class, "master>name");
	    ca.setColumnAlias(docNameAlias);
	    spec.appendSelect(ca, new int[] { idx_doc }, false);

	    // state
	    ca = new ClassAttribute(doc_class, "state.state");
	    ca.setColumnAlias(docStateAlias);
	    spec.appendSelect(ca, new int[] { idx_doc }, false);

	    // branchId
	    ca = new ClassAttribute(doc_class, "iterationInfo.branchId");
	    ca.setColumnAlias(docBranchIdAlias);
	    spec.appendSelect(ca, new int[] { idx_doc }, false);

	    SearchCondition sc0 = null;
	    sc0 = new SearchCondition(doc_class, "iterationInfo.latest", SearchCondition.IS_TRUE);
	    spec.appendWhere(sc0, new int[] { idx_doc });
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}
	return spec;
    }

    public static QuerySpec getUserCondition() throws WTException {
	if (!SERVER) {
	    Class argTypes[] = null;
	    Object args[] = null;
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getUserCondition", ProjectOutputHelper.class.getName(), null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
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
	    // oid
	    ClassInfo classinfo = WTIntrospector.getClassInfo(user_class);
	    DatabaseInfo databaseinfo = classinfo.getDatabaseInfo();
	    BaseTableInfo basetableinfo = databaseinfo.getBaseTableInfo();
	    String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke = new KeywordExpression(spec.getFromClause().getAliasAt(idx_user) + "." + objname + "||':'||"
		    + spec.getFromClause().getAliasAt(idx_user) + "." + objid);
	    ke.setColumnAlias(wtuserOidAlias);
	    spec.appendSelect(ke, new int[] { idx_user }, false);

	    // user_id
	    ca = new ClassAttribute(people_class, People.ID);
	    ca.setColumnAlias(userIdAlias);
	    spec.appendSelect(ca, new int[] { idx_people }, false);

	    // user_name
	    ca = new ClassAttribute(people_class, People.NAME);
	    ca.setColumnAlias(userNameAlias);
	    spec.appendSelect(ca, new int[] { idx_people }, false);

	    // dept_code
	    // ca = new ClassAttribute(dept_class, Department.CODE);
	    ca.setColumnAlias(deptCodeAlias);
	    spec.appendSelect(ca, new int[] { idx_dept }, false);

	    // dept_name
	    // ca = new ClassAttribute(dept_class, Department.NAME);
	    ca.setColumnAlias(deptNameAlias);
	    spec.appendSelect(ca, new int[] { idx_dept }, false);

	    // wtuser_id
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
	    Kogger.error(ProjectOutputHelper.class, e);
	}
	return spec;
    }

    public static int getOutputCount(TemplateProject project, boolean completed) throws WTException {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { TemplateProject.class, boolean.class };
	    Object args[] = new Object[] { project, new Boolean(completed) };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("getOutputCount", ProjectOutputHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		// TODO Auto-generated catch block
		Kogger.error(ProjectOutputHelper.class, e);
	    } catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		Kogger.error(ProjectOutputHelper.class, e);
	    }
	    return ((Integer) obj).intValue();
	}

	try {

	    String columnAlias = "count0";
	    long pjtID = project.getPersistInfo().getObjectIdentifier().getId();

	    Class target = ProjectOutput.class;

	    QuerySpec subQs = new QuerySpec();
	    subQs.setAdvancedQueryEnabled(true);

	    // int idx_pjt = subQs.addClassList(pjt_class, false);
	    int idx_target = subQs.addClassList(target, false);

	    ClassAttribute ca0 = null;
	    ClassAttribute ca1 = null;

	    ca0 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
	    SQLFunction countfunction = SQLFunction.newSQLFunction(SQLFunction.COUNT, ca0);
	    countfunction.setColumnAlias(columnAlias);
	    subQs.appendSelect(countfunction, new int[] { idx_target }, false);

	    SearchCondition sc0 = null;
	    sc0 = new SearchCondition(target, "projectReference.key.id", SearchCondition.EQUAL, pjtID);
	    subQs.appendWhere(sc0, new int[] { idx_target });

	    subQs.appendAnd();
	    subQs.appendOpenParen();
	    subQs.appendWhere(new SearchCondition(target, ProjectOutput.OBJ_TYPE, SearchCondition.EQUAL, "DOC"), new int[] { idx_target });
	    subQs.appendOr();
	    subQs.appendWhere(new SearchCondition(target, ProjectOutput.OBJ_TYPE, SearchCondition.EQUAL, "DWG"), new int[] { idx_target });
	    subQs.appendCloseParen();

	    if (completed) {
		Class link_class = OutputDocumentLink.class;
		Class doc_class = WTDocument.class;

		int idx_link = subQs.addClassList(link_class, false);
		int idx_doc = subQs.addClassList(doc_class, false);

		subQs.appendAnd();

		ca0 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
		ca1 = new ClassAttribute(link_class, "roleAObjectRef.key.id");
		sc0 = new SearchCondition(ca0, "=", ca1);
		sc0.setFromIndicies(new int[] { idx_target, idx_link }, 0);
		sc0.setOuterJoin(0);
		subQs.appendWhere(sc0, new int[] { idx_target, idx_link });

		subQs.appendAnd();

		ca0 = new ClassAttribute(link_class, OutputDocumentLink.BRANCH_IDENTIFIER);
		ca1 = new ClassAttribute(doc_class, "iterationInfo.branchId");
		sc0 = new SearchCondition(ca0, "=", ca1);
		sc0.setFromIndicies(new int[] { idx_link, idx_doc }, 0);
		sc0.setOuterJoin(0);
		subQs.appendWhere(sc0, new int[] { idx_link, idx_doc });

		subQs.appendAnd();

		sc0 = new SearchCondition(doc_class, "iterationInfo.latest", SearchCondition.IS_TRUE);
		subQs.appendWhere(sc0, new int[] { idx_doc });

		subQs.appendAnd();

		sc0 = new SearchCondition(doc_class, "state.state", SearchCondition.EQUAL, State.toState("APPROVED"));
		subQs.appendWhere(sc0, new int[] { idx_doc });
	    }

	    SubSelectExpression subfrom = new SubSelectExpression(subQs);
	    subfrom.setFromAlias(new String[] { "SUB0" }, 0);

	    QuerySpec mainQs = new QuerySpec();
	    mainQs.setAdvancedQueryEnabled(true);
	    int main_idx = mainQs.appendFrom(subfrom);

	    KeywordExpression ke = new KeywordExpression(columnAlias);
	    mainQs.appendSelect(ke, new int[] { main_idx }, false);

	    QueryResult qr = PersistenceHelper.manager.find(mainQs);

	    int count = 0;
	    Object obj[] = null;
	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		if (obj[0] instanceof BigDecimal) {
		    BigDecimal bd = (BigDecimal) obj[0];
		    count += bd.intValue();
		}
	    }

	    return count;

	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}

	return 0;
    }

    public static QueryResult getOutputDocumentLink(WTDocumentMaster docMaster) throws Exception {

	long masterId = docMaster.getPersistInfo().getObjectIdentifier().getId();
	QuerySpec spec = new QuerySpec();

	int index0 = spec.addClassList(E3PSProject.class, false);
	int index1 = spec.addClassList(ProjectOutput.class, false);
	int index2 = spec.addClassList(OutputDocumentLink.class, true);

	ClassAttribute ca0 = new ClassAttribute(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute ca1 = new ClassAttribute(ProjectOutput.class, "projectReference.key.id");

	SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	sc.setFromIndicies(new int[] { index0, index1 }, 0);
	try {
	    sc.setOuterJoin(0);
	} catch (WTPropertyVetoException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(ProjectOutputHelper.class, e);
	}
	spec.appendWhere(sc, new int[] { index0, index1 });

	spec.appendAnd();

	ca0 = new ClassAttribute(ProjectOutput.class, "thePersistInfo.theObjectIdentifier.id");
	ca1 = new ClassAttribute(OutputDocumentLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca0, "=", ca1);
	sc.setFromIndicies(new int[] { index1, index2 }, 0);

	try {
	    sc.setOuterJoin(0);
	} catch (WTPropertyVetoException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(ProjectOutputHelper.class, e);
	}

	spec.appendWhere(sc, new int[] { index1, index2 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(OutputDocumentLink.class, "roleBObjectRef.key.id", "=", masterId), new int[] { index2 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index0 });
	spec.appendAnd();
	// spec.appendWhere(new SearchCondition(JELProject.class, JELProject.CHECK_OUT_STATE, SearchCondition.NOT_EQUAL, "c/o"), new
	// int[]{index0});
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(ProjectOutput.class, ProjectOutput.COMPLETION, SearchCondition.NOT_EQUAL, 100),
	        new int[] { index1 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	return rs;

    }

    public QueryResult getProjectOutput(TemplateProject project) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class mainClass = ProjectOutput.class;
	    int mainClassPos = qs.addClassList(mainClass, true);
	    long oidValue = CommonUtil.getOIDLongValue(project);
	    qs.appendWhere(new SearchCondition(mainClass, "projectReference.key.id", SearchCondition.EQUAL, oidValue), mainClassPos);
	    // if (project instanceof JELProject) {

	    // } else {
	    // qs.appendAnd();
	    // qs.appendWhere(new SearchCondition(mainClass,"taskReference.key.id",SearchCondition.EQUAL,(long)0),mainClassPos);
	    // }
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getTaskOutputDocLink(TemplateTask task) throws Exception {
	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(ProjectOutput.class, false);
	int idx_link = qs.appendClassList(OutputDocumentLink.class, true);

	SearchCondition sc = null;
	sc = new SearchCondition(ProjectOutput.class, "taskReference.key.id", SearchCondition.EQUAL, task.getPersistInfo()
	        .getObjectIdentifier().getId());
	qs.appendWhere(sc, new int[] { idx });

	qs.appendAnd();

	sc = new SearchCondition(new ClassAttribute(ProjectOutput.class, "thePersistInfo.theObjectIdentifier.id"), "=", new ClassAttribute(
	        OutputDocumentLink.class, "roleAObjectRef.key.id"));
	sc.setOuterJoin(0);
	qs.appendWhere(sc, idx, idx_link);

	return PersistenceHelper.manager.find(qs);

    }

    public static boolean isPrimaryOutPut(TemplateTask task) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { TemplateTask.class };
	    Object args[] = new Object[] { task };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("isPrimaryOutPut", ProjectOutputHelper.class.getName(), null, argTypes,
		        args);
		return ((Boolean) obj).booleanValue();

	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    }
	}

	String tableName = "";
	Class taskClass = ProjectOutput.class;
	ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
	    tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
	} else {
	    tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
	}

	String taskReferenceColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, "taskReference.key.id");

	String isPrimaryColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, ProjectOutput.IS_PRIMARY);

	Connection con = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	int childLength = 0;
	try {

	    con = DBCPManager.getConnection("plm");

	    String sql = "select count(" + taskReferenceColumnName + ") from " + tableName + " where " + taskReferenceColumnName
		    + " = ? and " + isPrimaryColumnName + "=?";

	    st = con.prepareStatement(sql);
	    long taskId = task.getPersistInfo().getObjectIdentifier().getId();
	    st.setLong(1, taskId);
	    st.setBoolean(2, true);

	    rs = st.executeQuery();

	    if (rs.next()) {
		childLength = rs.getInt(1);
	    } else {
		throw new Exception("error..");
	    }

	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	    throw e;
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}
		if (st != null) {
		    st.close();
		}
		if (con != null)
		    con.close();
	    } catch (SQLException e) {
		Kogger.error(ProjectOutputHelper.class, e);
	    }
	}
	return childLength > 0;
    }

    public ProjectOutput getTaskOutput(TemplateTask task, String outputName) throws Exception {

	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(ProjectOutput.class, true);

	SearchCondition sc = null;

	sc = new SearchCondition(ProjectOutput.class, "taskReference.key.id", SearchCondition.EQUAL, task.getPersistInfo()
	        .getObjectIdentifier().getId());
	qs.appendWhere(sc, new int[] { idx });

	qs.appendAnd();

	ClassAttribute ca0 = new ClassAttribute(ProjectOutput.class, ProjectOutput.OUTPUT_NAME);
	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
	ColumnExpression ce = ConstantExpression.newExpression(outputName.trim().toUpperCase());
	SearchCondition sc0 = new SearchCondition(upper, SearchCondition.EQUAL, ce);
	qs.appendWhere(sc0, new int[] { idx });

	ProjectOutput output = null;

	QueryResult result = PersistenceHelper.manager.find(qs);

	if (result.hasMoreElements()) {
	    Object o[] = (Object[]) result.nextElement();
	    output = (ProjectOutput) o[0];
	}

	return output;
    }

    public ProjectOutput getEqualTaskOutput(TemplateTask task, String outputName) throws Exception {
	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(ProjectOutput.class, true);

	SearchCondition sc = null;

	sc = new SearchCondition(ProjectOutput.class, "taskReference.key.id", SearchCondition.EQUAL, task.getPersistInfo()
	        .getObjectIdentifier().getId());
	qs.appendWhere(sc, new int[] { idx });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ProjectOutput.class, ProjectOutput.OUTPUT_NAME, "=", outputName), new int[] { idx });
	ProjectOutput output = null;

	QueryResult result = PersistenceHelper.manager.find(qs);

	if (result.hasMoreElements()) {
	    Object o[] = (Object[]) result.nextElement();
	    output = (ProjectOutput) o[0];
	}

	return output;
    }

    public ProjectOutput getLikeTaskOutput(TemplateTask task, String outputName) throws Exception {

	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(ProjectOutput.class, true);

	SearchCondition sc = null;

	sc = new SearchCondition(ProjectOutput.class, "taskReference.key.id", SearchCondition.EQUAL, task.getPersistInfo()
	        .getObjectIdentifier().getId());
	qs.appendWhere(sc, new int[] { idx });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ProjectOutput.class, ProjectOutput.OUTPUT_NAME, SearchCondition.LIKE, "%" + outputName + "%"),
	        new int[] { idx });
	ProjectOutput output = null;

	QueryResult result = PersistenceHelper.manager.find(qs);
	if (result.hasMoreElements()) {
	    Object o[] = (Object[]) result.nextElement();
	    output = (ProjectOutput) o[0];
	}

	return output;
    }

    public List<ProjectOutput> getTaskOutputByKqisTrust(TemplateTask task) {
	List<ProjectOutput> resultList = new ArrayList<ProjectOutput>();

	try {

	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(ProjectOutput.class, true);

	    SearchCondition sc = null;

	    sc = new SearchCondition(ProjectOutput.class, "taskReference.key.id", SearchCondition.EQUAL, task.getPersistInfo()
		    .getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx });

	    qs.appendAnd();

	    qs.appendWhere(new SearchCondition(ProjectOutput.class, ProjectOutput.OBJ_TYPE, "=", "KQISTRUST"), new int[] { idx });

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    while (qr.hasMoreElements()) {
		Object[] object = (Object[]) qr.nextElement();
		ProjectOutput output = (ProjectOutput) object[0];
		resultList.add(output);
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return resultList;
    }

    public QueryResult getTaskOutput(TemplateTask task) {
	try {
	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(ProjectOutput.class, true);

	    SearchCondition sc = null;

	    sc = new SearchCondition(ProjectOutput.class, "taskReference.key.id", SearchCondition.EQUAL, task.getPersistInfo()
		    .getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx });

	    int sortIdx = 0;
	    ClassAttribute sortCa = new ClassAttribute(ProjectOutput.class, "thePersistInfo.createStamp");
	    sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	    qs.appendSelect(sortCa, new int[] { idx }, false);
	    OrderBy orderBy = new OrderBy(sortCa, false, null);
	    // 정렬순서 수정. 2014.11.01
	    // qs.appendOrderBy(orderBy, new int[] { idx });
	    qs.appendOrderBy(new OrderBy(new ClassAttribute(ProjectOutput.class, "thePersistInfo.createStamp"), false), new int[] { idx });
	    // ClassAttribute sortCa = new ClassAttribute(ProjectOutput.class, ProjectOutput.OUTPUT_ROLE);
	    // sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	    // qs.appendSelect(sortCa, new int[] { idx }, false);
	    // OrderBy orderBy = new OrderBy(sortCa, false, null);
	    // qs.appendOrderBy(orderBy, new int[] { idx });

	    sortCa = new ClassAttribute(ProjectOutput.class, ProjectOutput.OUTPUT_NAME);
	    sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	    qs.appendSelect(sortCa, new int[] { idx }, false);
	    orderBy = new OrderBy(sortCa, false, null);
	    qs.appendOrderBy(orderBy, new int[] { idx });

	    QueryResult result = PersistenceHelper.manager.find(qs);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult checkTaskOutput(TemplateTask task) {
	QueryResult qr = getTaskOutput(task);
	return qr;
    }

    public static ProjectOutput createProjectOutput(ProjectOutput output) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ProjectOutput.class };
	    Object args[] = new Object[] { output };
	    Object obj = RemoteMethodServer.getDefault().invoke("createProjectOutput", ProjectOutputHelper.class.getName(), null, argTypes,
		    args);
	    return (ProjectOutput) obj;
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    output = (ProjectOutput) PersistenceHelper.manager.save(output);
	    E3PSTask task = (E3PSTask) output.getTask();
	    // ProjectTaskHelper.updateCompletionFromOutput(task);
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    // Kogger.error(getClass(), e);
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
	return output;
    }

    public ProjectOutput modifyProjectOutput(ProjectOutput _output) {
	try {
	    return (ProjectOutput) PersistenceHelper.manager.modify(_output);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public static void deleteProjectOutput(ProjectOutput output) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ProjectOutput.class };
	    Object args[] = new Object[] { output };
	    RemoteMethodServer.getDefault().invoke("deleteProjectOutput", ProjectOutputHelper.class.getName(), null, argTypes, args);
	    return;
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    E3PSTask task = (E3PSTask) output.getTask();
	    PersistenceHelper.manager.delete(output);
	    ProjectTaskHelper.updateCompletionFromOutput(task);
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    public static void deleteProjectOutputTemplate(ProjectOutput output) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { ProjectOutput.class };
	    Object args[] = new Object[] { output };
	    RemoteMethodServer.getDefault()
		    .invoke("deleteProjectOutputTemplate", ProjectOutputHelper.class.getName(), null, argTypes, args);
	    return;
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();
	    PersistenceHelper.manager.delete(output);
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    public void registryProjectOutput(ProjectOutput _output, RevisionControlled rc) {
	try {
	    WTPrincipal principal = SessionHelper.manager.getPrincipal();
	    WTPrincipalReference wtref = null;
	    wtref = WTPrincipalReference.newWTPrincipalReference(principal);
	    _output.setOwner(wtref);
	    if (rc.getState().toString().equals("APPROVED")) {
		_output.setCompletion(100);
	    }
	    _output = (ProjectOutput) PersistenceHelper.manager.modify(_output);
	    OutputDocumentLink link = OutputDocumentLink.newOutputDocumentLink(_output, (Master) rc.getMaster());
	    link.setBranchIdentifier(rc.getBranchIdentifier());
	    link.setDocClassName(rc.getClass().getName());
	    PersistenceHelper.manager.save(link);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * public QueryResult getProjectOutput(WTDocument doc) { try { QuerySpec spec = new QuerySpec(OutputDocumentLink.class);
     * SearchUtil.appendEQUAL(spec, OutputDocumentLink.class, OutputDocumentLink.DOCUMENT_ROLE, CommonUtil.getOIDLongValue(doc), 0);
     * QueryResult result = PersistenceHelper.manager.find(spec); if(result != null) return result; } catch (WTException e){
     * Kogger.error(getClass(), e); } return null; }
     */

    public void deleteProjectOutput(TemplateProject project) {
	try {
	    QueryResult qr = getProjectOutput(project);
	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		ProjectOutput output = (ProjectOutput) objArr[0];
		PersistenceHelper.manager.delete(output);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void deleteTaskOutput(TemplateTask task) {
	try {
	    QueryResult qr = getTaskOutput(task);
	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		ProjectOutput output = (ProjectOutput) objArr[0];
		PersistenceHelper.manager.delete(output);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 템플릿 프로젝트의 산출물 정보를 타켓 프로젝트에 복사한다.
     * 
     * @param project
     *            타켓 프로젝트
     * @param template
     *            원본 템플릿 프로젝트
     */
    public void copyProjectOutputInfo(TemplateProject project, TemplateProject template) {
	try {
	    QueryResult qr = getProjectOutput(template);
	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		ProjectOutput tempOutput = (ProjectOutput) objArr[0];

		ProjectOutput output = ProjectOutput.newProjectOutput();
		output.setOutputName(tempOutput.getOutputName());
		output.setOutputDesc(tempOutput.getOutputDesc());
		output.setOutputLocation(tempOutput.getOutputLocation());
		output.setOwner(tempOutput.getOwner());
		output.setProject(project);
		output = (ProjectOutput) PersistenceHelper.manager.save(output);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 템플릿 태스크의 산출물 정보를 타켓 태스크에 복사한다.
     * 
     * @param task
     *            타켓 태스크
     * @param template
     *            원본 템플릿 태스크
     */
    public void copyTaskOutputInfo(TemplateTask task, TemplateTask template) {
	try {
	    QueryResult qr = getTaskOutput(template);
	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		ProjectOutput tempOutput = (ProjectOutput) objArr[0];

		ProjectOutput output = ProjectOutput.newProjectOutput();
		output.setOutputName(tempOutput.getOutputName());
		output.setOutputDesc(tempOutput.getOutputDesc());
		output.setOutputLocation(tempOutput.getOutputLocation());
		output.setOutputHistory(tempOutput.getOutputHistory());
		output.setOwner(tempOutput.getOwner());
		output.setProject(task.getProject());
		output.setTask(task);
		output = (ProjectOutput) PersistenceHelper.manager.save(output);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 템플릿 태스크의 산출물 정보를 타켓 태스크에 복사한다.
     * 
     * @param toTask
     *            타켓 태스크
     * @param fromTask
     *            원본 템플릿 태스크
     */
    public void copyTaskOutputInfo(HashMap map, HashMap viewDepartMent, TemplateTask toTask, TemplateTask fromTask) {
	try {
	    QueryResult qr = getTaskOutput(fromTask);

	    boolean isTasktoTask = toTask instanceof E3PSTask && fromTask instanceof E3PSTask;

	    boolean isTemplateToTask = toTask instanceof E3PSTask && !(fromTask instanceof E3PSTask);

	    boolean isTemplateToTemplate = !(toTask instanceof E3PSTask) && !(fromTask instanceof E3PSTask);

	    boolean isFormTasktoTemplate = !(toTask instanceof E3PSTask) && fromTask instanceof E3PSTask;

	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();

		ProjectOutput tempOutput = (ProjectOutput) objArr[0];

		ProjectOutput output = (ProjectOutput) HistoryHelper.duplicate(tempOutput);
		// 2014.09.01 표준 WBS copy할 경우 표준문서 링크정보를 저장하는 항목도 COPY > 위의 함수(HistoryHelper.duplicate) 호출시 복사됨
		// output.setOutputDocOid(tempOutput.getOutputDocOid());
		// output.setOutputDocName(tempOutput.getOutputDocName());

		output.setTask(toTask);
		output.setOldOutputOid(CommonUtil.getOIDString(tempOutput));
		output.setProject(toTask.getProject());

		// output.setOutputName(tempOutput.getOutputName());
		// output.setOutputDesc(tempOutput.getOutputDesc());
		// output.setOutputLocation(tempOutput.getOutputLocation());
		// output.setOutputHistory(tempOutput.getOutputHistory());
		// // output.setOwner(tempOutput.getOwner());
		// output.setProject(toTask.getProject());
		// output.setTask(toTask);
		// // Role 및 산출물 타입 추가
		// output.setOutputRole(tempOutput.getOutputRole());
		// output.setOutputType(tempOutput.getOutputType());

		if (isTemplateToTemplate) {
		    long fromOId = 0L;
		    long toOId = 0L;

		    ProjectOutPutType fromOutputType = fromTask.getProject().getOutputType();

		    ProjectOutPutType toOutputType = toTask.getProject().getOutputType();

		    if (toOutputType != null) {

			toOId = toOutputType.getPersistInfo().getObjectIdentifier().getId();
		    }
		    if (fromOutputType != null) {

			fromOId = fromOutputType.getPersistInfo().getObjectIdentifier().getId();
		    }

		    if (toOId != fromOId) {
			output.setOutputType(null);
		    }
		}

		if (isTemplateToTask) {
		    output.setOwner(WTPrincipalReference.newWTPrincipalReference());
		    output.setCompletion(0);
		}

		if (isTasktoTask) {

		    output = (ProjectOutput) PersistenceHelper.manager.save(output);

		    QueryResult rs = PersistenceHelper.manager.navigate(tempOutput, OutputViewMemberLink.VIEW_MEMBER_ROLE,
			    OutputViewMemberLink.class);
		    while (rs.hasMoreElements()) {
			ProjectMemberLink link = (ProjectMemberLink) rs.nextElement();
			long userId = link.getMember().getPersistInfo().getObjectIdentifier().getId();
			int linkType = link.getPjtMemberType();
			ProjectMemberLink plink = (ProjectMemberLink) map.get(userId + "$" + linkType);
			OutputViewMemberLink olink = OutputViewMemberLink.newOutputViewMemberLink(output, plink);
			PersistenceHelper.manager.save(olink);
		    }

		    rs = PersistenceHelper.manager.navigate(tempOutput, OutputViewDepartMentLink.VIEW_DEPARTMENT_ROLE,
			    OutputViewDepartMentLink.class, false);
		    while (rs.hasMoreElements()) {
			OutputViewDepartMentLink link = (OutputViewDepartMentLink) rs.nextElement();
			long departmentId = link.getViewDepartment().getDepartment().getPersistInfo().getObjectIdentifier().getId();
			ProjectViewDepartmentLink vdlink = (ProjectViewDepartmentLink) viewDepartMent.get(String.valueOf(departmentId));
			OutputViewDepartMentLink odlink = OutputViewDepartMentLink.newOutputViewDepartMentLink(output, vdlink);
			PersistenceHelper.manager.save(odlink);
		    }

		    RevisionControlled doc = getDocMasterForOutput(tempOutput);
		    if (doc != null) {
			ProjectOutputHelper.manager.registryProjectOutput(output, doc);
		    }

		    // 문서 산출물 관계
		    rs = PersistenceHelper.manager.navigate(tempOutput, KETDocumentOutputLink.DOCUMENT_ROLE, KETDocumentOutputLink.class);
		    while (rs.hasMoreElements()) {
			KETProjectDocument tDoc = (KETProjectDocument) rs.nextElement();
			KETDocumentOutputLink DoLink;
			DoLink = KETDocumentOutputLink.newKETDocumentOutputLink(tDoc, output);
			wt.fc.PersistenceServerHelper.manager.insert(DoLink);
		    }

		    // Gate 산출물 관계(Replace)
		    rs = PersistenceHelper.manager.navigate(tempOutput, AssessResultOutputLink.ASSESS_ROLE, AssessResultOutputLink.class,
			    false);
		    while (rs.hasMoreElements()) {
			// AssessResultOutputLink assessResultOutputLink = (AssessResultOutputLink) rs.nextElement();
			// AssessResultOutputLink assessResultOutputLink = AssessResultOutputLink.newAssessResultOutputLink(assessResult,
			// output);
			// assessResultOutputLink.setOutputCharge(null);
			// assessResultOutputLink.setOutputCharge()
			// assessResultOutputLink.setOutputOid(CommonUtil.getOIDString(output));
			// PersistenceServerHelper.manager.insert(assessResultOutputLink);

			AssessResultOutputLink assessResultOutputLink = (AssessResultOutputLink) rs.nextElement();
			AssessResultOutputLink newAssessResultOutputLink = AssessResultOutputLink.newAssessResultOutputLink(
			        assessResultOutputLink.getAssess(), output);
			newAssessResultOutputLink.setOutputCharge(assessResultOutputLink.getOutputCharge());
			newAssessResultOutputLink.setOutputCheck(assessResultOutputLink.getOutputCheck());
			newAssessResultOutputLink.setOutputComment(assessResultOutputLink.getOutputComment());
			newAssessResultOutputLink.setOutputName(assessResultOutputLink.getOutputName());
			newAssessResultOutputLink.setOutputState(assessResultOutputLink.getOutputState());
			newAssessResultOutputLink.setOutputOid(CommonUtil.getOIDString(output));
			newAssessResultOutputLink.setRev(assessResultOutputLink.getRev());
			PersistenceServerHelper.manager.insert(newAssessResultOutputLink);
		    }

		    // 프로젝트 재설정, 등록 일때 신뢰성 태스크 산출물 복사 안함
		    if ((toTask instanceof TemplateTask || toTask.getProject().getState().getState().getDisplay().equals("PLANCHANGE"))) {
			// 신뢰성 산출물 관계
			rs = PersistenceHelper.manager
			        .navigate(tempOutput, TrustProjectOutputLink.TRUST_ROLE, TrustProjectOutputLink.class);
			while (rs.hasMoreElements()) {
			    TaskTrustResult trustResult = (TaskTrustResult) rs.nextElement();
			    int oldRev = trustResult.getRev();

			    TaskTrustResult newTrustResult = null;
			    QueryResult trustLinkRs = PersistenceHelper.manager.navigate(toTask, TrustTemplateTaskLink.TRUST_ROLE,
				    TrustTemplateTaskLink.class);
			    while (trustLinkRs.hasMoreElements()) {
				newTrustResult = (TaskTrustResult) trustLinkRs.nextElement();
				if (newTrustResult.getRev() == oldRev) {
				    break;
				}
			    }
			    TrustProjectOutputLink trustProjectOutputLink = TrustProjectOutputLink.newTrustProjectOutputLink(output,
				    newTrustResult);
			    PersistenceServerHelper.manager.insert(trustProjectOutputLink);
			}

			if ("DR".equalsIgnoreCase(toTask.getTaskType())) {
			    rs = PersistenceHelper.manager.navigate(tempOutput, AssessProjectOutputLink.ASSESS_ROLE,
				    AssessProjectOutputLink.class);
			    while (rs.hasMoreElements()) {
				TaskAssessResult assessResult = (TaskAssessResult) rs.nextElement();
				int oldRev = assessResult.getRev();

				TaskAssessResult newAssessResult = null;
				QueryResult assessLinkRs = PersistenceHelper.manager.navigate(toTask, AssessTemplateTaskLink.ASSESS_ROLE,
				        AssessTemplateTaskLink.class);
				while (assessLinkRs.hasMoreElements()) {
				    newAssessResult = (TaskAssessResult) assessLinkRs.nextElement();
				    if (newAssessResult.getRev() == oldRev) {
					break;
				    }
				}
				if (newAssessResult != null) {
				    AssessProjectOutputLink assessProjectOutputLink = AssessProjectOutputLink.newAssessProjectOutputLink(
					    output, newAssessResult);
				    PersistenceServerHelper.manager.insert(assessProjectOutputLink);
				}

			    }
			}

		    }

		    // 금형Try조건표 press
		    rs = PersistenceHelper.manager.navigate(tempOutput, KETTryPressOutputLink.TRY_PRESS_ROLE, KETTryPressOutputLink.class);
		    while (rs.hasMoreElements()) {
			KETTryPress tryPress = (KETTryPress) rs.nextElement();
			KETTryPressOutputLink tryPressLink = KETTryPressOutputLink.newKETTryPressOutputLink(tryPress, output);
			PersistenceServerHelper.manager.insert(tryPressLink);
		    }
		    // 금형Try조건표 mold
		    rs = PersistenceHelper.manager.navigate(tempOutput, KETTryMoldOutputLink.TRY_MOLD_ROLE, KETTryMoldOutputLink.class);
		    while (rs.hasMoreElements()) {
			KETTryMold tryMold = (KETTryMold) rs.nextElement();
			KETTryMoldOutputLink tryMoldLink = KETTryMoldOutputLink.newKETTryMoldOutputLink(tryMold, output);
			PersistenceServerHelper.manager.insert(tryMoldLink);
		    }
		    // 제품 eco link 복사
		    rs = PersistenceHelper.manager.navigate(tempOutput, KETProdChangeOrderOutputLink.CHANGE_ROLE,
			    KETProdChangeOrderOutputLink.class);
		    while (rs.hasMoreElements()) {
			KETProdChangeOrder prodEco = (KETProdChangeOrder) rs.nextElement();
			KETProdChangeOrderOutputLink prodEcolink = KETProdChangeOrderOutputLink.newKETProdChangeOrderOutputLink(prodEco,
			        output);
			PersistenceServerHelper.manager.insert(prodEcolink);
		    }
		    // 금형 eco link 복사
		    rs = PersistenceHelper.manager.navigate(tempOutput, KETMoldChangeOrderOutputLink.CHANGE_ROLE,
			    KETMoldChangeOrderOutputLink.class);
		    while (rs.hasMoreElements()) {
			KETMoldChangeOrder moldEco = (KETMoldChangeOrder) rs.nextElement();
			KETMoldChangeOrderOutputLink moldEcolink = KETMoldChangeOrderOutputLink.newKETMoldChangeOrderOutputLink(moldEco,
			        output);
			PersistenceServerHelper.manager.insert(moldEcolink);
		    }
		    // 개발품질문제 link 복사
		    rs = PersistenceHelper.manager.navigate(tempOutput, KETDqmRaiseOutputLink.DQM_ROLE, KETDqmRaiseOutputLink.class);
		    while (rs.hasMoreElements()) {
			KETDqmRaise dqmRaise = (KETDqmRaise) rs.nextElement();
			KETDqmRaiseOutputLink dqmLink = KETDqmRaiseOutputLink.newKETDqmRaiseOutputLink(dqmRaise, output);
			PersistenceServerHelper.manager.insert(dqmLink);
		    }
		    // // Gate 산출물 관계
		    // rs = PersistenceHelper.manager.navigate(tempOutput, AssessResultOutputLink.ASSESS_ROLE, AssessResultOutputLink.class,
		    // false);
		    // while (rs.hasMoreElements()) {
		    // AssessResultOutputLink assessResultOutputLink = (AssessResultOutputLink) rs.nextElement();
		    // assessResultOutputLink.setOutput(output);
		    // PersistenceHelper.manager.save(assessResultOutputLink);
		    // }
		    // // 신뢰성 Task 링크 관계
		    // rs = PersistenceHelper.manager.navigate(tempOutput, TrustTemplateTaskLink.TRUST_ROLE, TrustTemplateTaskLink.class,
		    // false);
		    // while (rs.hasMoreElements()) {
		    // TrustTemplateTaskLink trustTemplateTaskLink = (TrustTemplateTaskLink) rs.nextElement();
		    // trustTemplateTaskLink.setTask(toTask);
		    // PersistenceHelper.manager.save(trustTemplateTaskLink);
		    // }
		    // // 신뢰성 산출물 관계
		    // rs = PersistenceHelper.manager.navigate(tempOutput, TrustProjectOutputLink.TRUST_ROLE, TrustProjectOutputLink.class,
		    // false);
		    // while (rs.hasMoreElements()) {
		    // TrustProjectOutputLink trustProjectOutputLink = (TrustProjectOutputLink) rs.nextElement();
		    // trustProjectOutputLink.setOutput(output);
		    // PersistenceHelper.manager.save(trustProjectOutputLink);
		    // }
		    // // 금형Try조건표 press
		    // rs = PersistenceHelper.manager.navigate(tempOutput, KETTryPressOutputLink.TRY_PRESS_ROLE,
		    // KETTryPressOutputLink.class);
		    // while (rs.hasMoreElements()) {
		    // KETTryPress tryPress = (KETTryPress) rs.nextElement();
		    // KETTryPressOutputLink tryPressLink = KETTryPressOutputLink.newKETTryPressOutputLink(tryPress, output);
		    // PersistenceServerHelper.manager.insert(tryPressLink);
		    // }
		    // // 금형Try조건표 mold
		    // rs = PersistenceHelper.manager.navigate(tempOutput, KETTryMoldOutputLink.TRY_MOLD_ROLE, KETTryMoldOutputLink.class);
		    // while (rs.hasMoreElements()) {
		    // KETTryMold tryMold = (KETTryMold) rs.nextElement();
		    // KETTryMoldOutputLink tryMoldLink = KETTryMoldOutputLink.newKETTryMoldOutputLink(tryMold, output);
		    // PersistenceServerHelper.manager.insert(tryMoldLink);
		    // }
		    // // 제품 eco link 복사
		    // rs = PersistenceHelper.manager.navigate(tempOutput, KETProdChangeOrderOutputLink.CHANGE_ROLE,
		    // KETProdChangeOrderOutputLink.class);
		    // while (rs.hasMoreElements()) {
		    // KETProdChangeOrder prodEco = (KETProdChangeOrder) rs.nextElement();
		    // KETProdChangeOrderOutputLink prodEcolink = KETProdChangeOrderOutputLink.newKETProdChangeOrderOutputLink(prodEco,
		    // output);
		    // PersistenceServerHelper.manager.insert(prodEcolink);
		    // }
		    // // 금형 eco link 복사
		    // rs = PersistenceHelper.manager.navigate(tempOutput, KETMoldChangeOrderOutputLink.CHANGE_ROLE,
		    // KETMoldChangeOrderOutputLink.class);
		    // while (rs.hasMoreElements()) {
		    // KETMoldChangeOrder moldEco = (KETMoldChangeOrder) rs.nextElement();
		    // KETMoldChangeOrderOutputLink moldEcolink = KETMoldChangeOrderOutputLink.newKETMoldChangeOrderOutputLink(moldEco,
		    // output);
		    // PersistenceServerHelper.manager.insert(moldEcolink);
		    // }
		    // // 개발품질문제 link 복사
		    // rs = PersistenceHelper.manager.navigate(tempOutput, KETDqmRaiseOutputLink.DQM_ROLE, KETDqmRaiseOutputLink.class);
		    // while (rs.hasMoreElements()) {
		    // KETDqmRaise dqmRaise = (KETDqmRaise) rs.nextElement();
		    // KETDqmRaiseOutputLink dqmLink = KETDqmRaiseOutputLink.newKETDqmRaiseOutputLink(dqmRaise, output);
		    // PersistenceServerHelper.manager.insert(dqmLink);
		    // }

		} else if (isTemplateToTask) {

		    WTUser user = (WTUser) map.get(tempOutput.getOutputRole());

		    if (user != null) {
			output.setOwner(WTPrincipalReference.newWTPrincipalReference(user));
		    }
		}

		if (!PersistenceHelper.isPersistent(output)) {
		    Object taskClass = toTask.getClass().toString();
		    if (((taskClass.equals("class e3ps.project.TemplateTask")) || toTask.getProject().getState().getState().getDisplay()
			    .equals("일정변경"))) {
			output = (ProjectOutput) PersistenceHelper.manager.save(output);
		    } else {
			if (!"신뢰성평가".equals(toTask.getTaskName())) {
			    output = (ProjectOutput) PersistenceHelper.manager.save(output);
			}
		    }
		}

		/*
	         * 
	         * boolean isTasktoTask = toTask instanceof e3psTask && fromTask instanceof e3psTask;
	         * 
	         * if(!isTasktoTask){ WTUser user = (WTUser) map.get(tempOutput.getOutputRole()); if (user != null) {
	         * output.setOwner(WTPrincipalReference.newWTPrincipalReference(user)); } }
	         * 
	         * output = (ProjectOutput) PersistenceHelper.manager.save(output); Kogger.debug(getClass(), "output save = " + output);
	         * 
	         * if(isTasktoTask){ WTDocument doc = getDocMasterForOutput(tempOutput); if(doc != null){
	         * ProjectOutputHelper.manager.registryProjectOutput(output, doc); } }
	         */
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public static QueryResult getProjectOutput(RevisionControlled doc, boolean isprojectLatest) throws Exception {
	QuerySpec spec = new QuerySpec(ProjectOutput.class, OutputDocumentLink.class);
	if (isprojectLatest) {
	    int projectIndex = spec.addClassList(E3PSProject.class, false);
	    SearchCondition sc = new SearchCondition(new ClassAttribute(ProjectOutput.class, "projectReference.key.id"), "=",
		    new ClassAttribute(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id"));
	    sc.setFromIndicies(new int[] { 0, projectIndex }, 0); // 위에 정의 한 SearchCondition 에 인덱스 순서대로 배열을 넘
	    sc.setOuterJoin(0); // 0,1,-1 이냐에 따라 outer 조인인지 Inner조인인지 .. +=, =+ ,=
	    spec.appendWhere(sc, 0, projectIndex); // 위에 정의한 SearchCondition 에 인덱스 순서대로 넘김
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.LASTEST, SearchCondition.IS_TRUE, true),
		    new int[] { projectIndex });
	    spec.appendAnd();
	}

	spec.appendWhere(new SearchCondition(OutputDocumentLink.class, "branchIdentifier", "=", doc.getBranchIdentifier()), new int[] { 1 });
	return PersistenceHelper.manager.navigate(doc.getMaster(), "output", spec, false);
	// return null;

    }

    public RevisionControlled getDocMasterForOutput(ProjectOutput output) {
	OutputDocumentLink link = null;
	// WTDocumentMaster documentMaster = null;
	RevisionControlled document = null;

	try {
	    QuerySpec spec = new QuerySpec(OutputDocumentLink.class);

	    SearchCondition sc = new SearchCondition(OutputDocumentLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, output
		    .getPersistInfo().getObjectIdentifier().getId());
	    spec.appendWhere(sc, new int[] { 0 });

	    // SearchUtil.appendEQUAL(spec, OutputDocumentLink.class, "roleAObjectRef.key.id", CommonUtil.getOIDLongValue(output), 0);

	    QueryResult result = PersistenceHelper.manager.find(spec);

	    // Kogger.debug(getClass(), "xxx@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	    // Kogger.debug(getClass(), ">>>>>>>>>>>>>>>>> : " + result.size());
	    if (result != null) {
		while (result.hasMoreElements()) {
		    link = (OutputDocumentLink) result.nextElement();
		    String docOID = "VR:" + link.getDocClassName() + ":" + link.getBranchIdentifier();
		    // Kogger.debug(getClass(), "docOID = " + docOID);
		    document = (RevisionControlled) CommonUtil.getObject(docOID);
		    if (document == null) {
			PersistenceHelper.manager.delete(link);
		    } else {
			break;
		    }

		    // ReferenceFactory rf = new ReferenceFactory();
		    // document = (WTDocument) rf.getReference(docOID).getObject();
		}
	    }
	    // Kogger.debug(getClass(), "xxx@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return document;
    }

    public QueryResult getOutputByRole(TemplateProject project, String role, WTUser user) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class mainClass = ProjectOutput.class;
	    int idx = qs.addClassList(mainClass, true);
	    long oidValue = CommonUtil.getOIDLongValue(project);
	    qs.appendWhere(new SearchCondition(mainClass, "projectReference.key.id", "=", oidValue), new int[] { idx });
	    qs.appendAnd();
	    if (role != null) {
		qs.appendWhere(new SearchCondition(mainClass, "outputRole", "=", role), new int[] { idx });
	    } else {
		qs.appendWhere(new SearchCondition(mainClass, "outputRole", true), new int[] { idx });
	    }
	    qs.appendAnd();
	    long userId = user.getPersistInfo().getObjectIdentifier().getId();
	    qs.appendWhere(new SearchCondition(mainClass, "owner.key.id", "=", userId), new int[] { idx });

	    return PersistenceHelper.manager.find(qs);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getOutputByRole(TemplateProject project, String role) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class mainClass = ProjectOutput.class;
	    int idx = qs.addClassList(mainClass, true);
	    long oidValue = CommonUtil.getOIDLongValue(project);
	    qs.appendWhere(new SearchCondition(mainClass, "projectReference.key.id", "=", oidValue), new int[] { idx });
	    qs.appendAnd();
	    if (role != null) {
		qs.appendWhere(new SearchCondition(mainClass, "outputRole", "=", role), new int[] { idx });
	    } else {
		qs.appendWhere(new SearchCondition(mainClass, "outputRole", true), new int[] { idx });
	    }

	    return PersistenceHelper.manager.find(qs);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public void updateCopyTaskOutputInfo(HashMap map, E3PSTask newChildTask, E3PSTask childOldTask) {
	// HashMap map, TemplateTask task, TemplateTask template
	try {
	    QueryResult qr = getTaskOutput(childOldTask);
	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		ProjectOutput oldOutput = (ProjectOutput) objArr[0];
		ProjectOutputData oldOutputData = new ProjectOutputData(oldOutput);

		ProjectOutput newOutput = ProjectOutput.newProjectOutput();
		newOutput.setOutputName(oldOutputData.name);
		newOutput.setOutputDesc(StringUtil.checkNull(oldOutputData.description));
		newOutput.setOutputLocation(oldOutputData.location);
		newOutput.setOutputHistory(oldOutput.getOutputHistory() + 1);
		// newOutput.setProject((JELProject)newChildTask.getProject());
		newOutput.setTask(newChildTask);
		// Role 및 산출물 타입 추가
		newOutput.setOutputRole(oldOutputData.role_en);
		// ROLE에 정의된 WTUser가 있을 경우
		WTUser user = (WTUser) map.get(oldOutputData.role_en);
		if (user != null) {
		    newOutput.setOwner(WTPrincipalReference.newWTPrincipalReference(user));
		}
		// ROLE이 정의되지 않는 산출물일 경우
		if (oldOutputData.registryUser != null) {
		    newOutput.setOwner(WTPrincipalReference.newWTPrincipalReference(oldOutputData.registryUser));
		}
		newOutput = (ProjectOutput) PersistenceHelper.manager.save(newOutput);

		if (oldOutputData.document != null) {
		    RevisionControlled oldDoc = oldOutputData.document;
		    ProjectOutputHelper.manager.registryProjectOutput(newOutput, oldDoc);
		}
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public QueryResult getOutputByProject(TemplateProject project) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class mainClass = ProjectOutput.class;
	    int idx = qs.addClassList(mainClass, true);
	    long oidValue = CommonUtil.getOIDLongValue(project);
	    qs.appendWhere(new SearchCondition(mainClass, "projectReference.key.id", "=", oidValue), new int[] { idx });

	    return PersistenceHelper.manager.find(qs);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public void deleteProjectOutputOwner(TemplateProject project) {
	// 주: 해당 Project에 존재하는 모든 ProjectOutput에 Owner 삭제

	try {
	    // 1. Project에서 ProjectOutput 있을 경우
	    QueryResult qr = getProjectOutput(project);
	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		ProjectOutput tempOutput = (ProjectOutput) objArr[0];
		tempOutput = (ProjectOutput) PersistenceHelper.manager.refresh(tempOutput);

		// 2. ProjectOutput 에 WTDocument가 없을 경우
		RevisionControlled tempDoc = getDocMasterForOutput(tempOutput);
		if (tempDoc == null) {
		    tempOutput.setOwner(WTPrincipalReference.newWTPrincipalReference());
		    PersistenceHelper.manager.modify(tempOutput);
		}
	    }
	} catch (ObjectNoLongerExistsException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 마지막 분류체계 DocCodeType Object로 1분류체계로 부터에 PATH return 단, /ROOT/법규/인증/사외규정&인증심사&인증규격&SYSTEM 인증
     * 
     * @param docType
     * @return
     */
    // public String getDocCodeTypePath(DocCodeType docType, String returnPath) {
    // String tempReturnPath = "";
    // if(!StringUtil.checkString(returnPath)) {
    // tempReturnPath = "^" + docType.getName();
    // }
    // try {
    // QueryResult qr = PersistenceHelper.manager.navigate(docType, "parent", DocCodeTypeParentChild.class);
    // while(qr.hasMoreElements()) {
    // DocCodeType tempDocType = (DocCodeType) qr.nextElement();
    // if(StringUtil.checkString(tempReturnPath)) {
    // returnPath = "^" + tempDocType.getName() + tempReturnPath;
    // } else {
    // returnPath = "^" + tempDocType.getName();
    // }
    // returnPath = getDocCodeTypePath(tempDocType, returnPath) + returnPath;
    // }
    // } catch (WTException e) {
    // Kogger.error(getClass(), e);
    // }
    // return returnPath;
    // }

    public PagingQueryResult searchProjectOutput(HashMap param, int size) throws Exception {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class, int.class };
	    Object args[] = new Object[] { param, new Integer(size) };
	    try {
		return (PagingQueryResult) RemoteMethodServer.getDefault().invoke("searchProjectOutput", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	}

	QuerySpec spec = null;
	String initType = (String) param.get("initType");
	String type = ParamUtil.getStrParameter((String) param.get("type"));
	String category = ParamUtil.getStrParameter((String) param.get("category"));
	String oid = ParamUtil.getStrParameter((String) param.get("oid"));
	String searchPjtNo = ParamUtil.getStrParameter((String) param.get("searchPjtNo"));
	String searchDocTitle = ParamUtil.getStrParameter((String) param.get("searchDocTitle"));

	if (initType == null) {
	    initType = "";
	}
	if (type == null) {
	    type = "";
	}
	if (category == null) {
	    category = "";
	}
	if (searchPjtNo == null) {
	    searchPjtNo = "";
	}
	if (searchDocTitle == null) {
	    searchDocTitle = "";
	}
	if (oid == null) {
	    oid = "";
	}

	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();

	spec = new QuerySpec();
	Class projectOutputClass = ProjectOutput.class;
	Class projectClass;
	if (type.equals("temp"))
	    projectClass = TemplateProject.class;
	else
	    projectClass = E3PSProject.class;
	int projectOutputClassPos = spec.addClassList(projectOutputClass, true);
	int projectClassPos = spec.addClassList(projectClass, false);

	SearchCondition sc = new SearchCondition(new ClassAttribute(projectOutputClass, "projectReference.key.id"), "=",
	        new ClassAttribute(projectClass, "thePersistInfo.theObjectIdentifier.id"));
	sc.setFromIndicies(new int[] { projectOutputClassPos, projectClassPos }, 0); // 위에 정의한 SearchCondition 에 인덱스 순서대로 배열을 넘
	sc.setOuterJoin(0); // 0,1,-1 이냐에 따라 outer 조인인지 Inner조인인지 .. +=, =+ ,=
	spec.appendWhere(sc, projectOutputClassPos, projectClassPos); // 위에 정의한 SearchCondition 에 인덱스 순서대로 넘김

	long oidValue;
	if (oid != null && oid.length() > 0) {
	    oidValue = CommonUtil.getOIDLongValue(oid);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(projectOutputClass, "projectReference.key.id", SearchCondition.EQUAL, oidValue),
		    projectOutputClassPos);
	} else {
	    oidValue = CommonUtil.getOIDLongValue(wtuser);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(projectOutputClass, "owner.key.id", SearchCondition.EQUAL, oidValue),
		    projectOutputClassPos);
	}

	if (StringUtil.checkString(searchPjtNo)) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    // spec.appendWhere(new SearchCondition(projectClass,JELProject.PJT_NO, SearchCondition.LIKE, "%"+searchPjtNo+"%"), new
	    // int[]{projectClassPos});
	}

	if (StringUtil.checkString(searchDocTitle)) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(projectOutputClass, "outputName", SearchCondition.LIKE, "%" + searchDocTitle + "%"),
		    new int[] { projectOutputClassPos });
	}

	if (category != null && category.length() > 0) {

	    String inoutState = null;

	    if ("pre".equals(category)) {
		inoutState = SearchCondition.NOT_IN;
	    } else if ("post".equals(category)) {
		inoutState = SearchCondition.IN;
	    }

	    if (inoutState != null) {

		QuerySpec subQs = new QuerySpec();
		int docLinkClassPos = subQs.addClassList(OutputDocumentLink.class, false);
		// int docClassPos = subQs.addClassList(JELDocument.class, false);
		ClassAttribute ca1 = new ClassAttribute(OutputDocumentLink.class, "roleAObjectRef.key.id");
		subQs.appendSelect(ca1, new int[] { docLinkClassPos }, false);

		// subQs.appendWhere(new SearchCondition(OutputDocumentLink.class, "roleBObjectRef.key.id",
		// JELDocument.class,"masterReference.key.id"), new int[]{docLinkClassPos,docClassPos});

		subQs.appendAnd();

		// subQs.appendWhere(VersionControlHelper.getSearchCondition(JELDocument.class, true), new int[] { docClassPos });

		subQs.appendAnd();

		// subQs.appendWhere(new SearchCondition(JELDocument.class, "state.state", "=", "APPROVED"), new int[]{docClassPos});

		// SearchUtil.addLastVersionCondition(subQs, JELDocument.class, docClassPos);

		spec.setAdvancedQueryEnabled(true);

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		spec.appendWhere(new SearchCondition(new ClassAttribute(projectOutputClass, "thePersistInfo.theObjectIdentifier.id"),
		        inoutState, new SubSelectExpression(subQs)), new int[] { projectOutputClassPos });

	    }
	}

	if (!type.equals("temp")) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(projectClass, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		    new int[] { projectClassPos });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(projectClass, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
		    new int[] { projectClassPos });

	}

	if (!CommonUtil.isAdmin()) {
	    // if(isProjectUser((WTUser)SessionHelper.manager.getPrincipal(), ProjectUserHelper.PM)) {
	    // //프로젝트 상태 Field
	    // if(spec.getConditionCount() > 0) spec.appendAnd();
	    // spec.appendOpenParen();
	    // SearchCondition where1 = new SearchCondition(projectClass, JELProject.PJT_STATE, SearchCondition.LIKE,
	    // ProjectStateFlag.PROJECT_STATE_PROGRESS);
	    // spec.appendWhere(where1, new int[]{projectClassPos});
	    // spec.appendOr();
	    // SearchCondition where2 = new SearchCondition(projectClass, JELProject.PJT_STATE, SearchCondition.LIKE,
	    // ProjectStateFlag.PROJECT_STATE_READY);
	    // spec.appendWhere(where2, new int[]{projectClassPos});
	    // spec.appendCloseParen();
	    // } else {
	    // //프로젝트 상태 Field
	    // if(spec.getConditionCount() > 0) spec.appendAnd();
	    // spec.appendOpenParen();
	    // SearchCondition where1 = new SearchCondition(projectClass, JELProject.PJT_STATE, SearchCondition.NOT_LIKE,
	    // ProjectStateFlag.PROJECT_STATE_READY);
	    // spec.appendWhere(where1, new int[]{projectClassPos});
	    // spec.appendCloseParen();
	    // }
	}

	if (StringUtil.checkString(initType)) {
	    // if(initType.equalsIgnoreCase("produceproject")) { //양산 프로젝트
	    // if(spec.getConditionCount() > 0) spec.appendAnd();
	    // spec.appendWhere(new SearchCondition(projectClass, JELProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("1")), new
	    // int[]{projectClassPos});
	    // } else if(initType.equalsIgnoreCase("devproject")) { //개발 프로젝트
	    // if(spec.getConditionCount() > 0) spec.appendAnd();
	    // spec.appendWhere(new SearchCondition(projectClass, JELProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("2")), new
	    // int[]{projectClassPos});
	    // }
	}
	SearchUtil.setOrderBy(spec, projectOutputClass, projectOutputClassPos, "taskReference.key.id", "task", false);
	// Kogger.debug(getClass(), "ProjectOutputHelper(searchProjectOutput)>>> \n"+spec);
	return PagingSessionHelper.openPagingSession(0, size, spec);
    }

    private static boolean isProjectUser(WTUser wtuser, int memberType) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class peopleProjectLinkClass = ProjectMemberLink.class;
	    int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

	    qs.appendWhere(
		    new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, memberType),
		    peopleProjectLinkClassPos);
	    qs.appendAnd();
	    long oidValue = CommonUtil.getOIDLongValue(wtuser);
	    qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    peopleProjectLinkClassPos);
	    QueryResult queryresult = PersistenceHelper.manager.find(qs);
	    if (queryresult != null && queryresult.size() > 0) {
		return true;
	    }
	} catch (QueryException e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}
	return false;
    }

    public static QueryResult getOutputViewMember(ProjectOutput output, boolean isViewLink) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { ProjectOutput.class, boolean.class };
	    Object args[] = new Object[] { output, new Boolean(isViewLink) };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("getOutputViewMember", "e3ps.project.beans.ProjectOutputHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
	    }

	    return (QueryResult) obj;
	}

	QueryResult result = null;
	try {
	    /*
	     * isViewLink : false -> ProjectMemberLink or ProjectViewDepartmentLink 의 OID isViewLink : true -> OutputViewMemberLink or
	     * OutputViewDepartMentLink 의 OID return
	     */
	    Class target = null;
	    int target_idx = -1;
	    // View User Query .......................................................................... begin
	    QuerySpec userQs = new QuerySpec();
	    userQs.setAdvancedQueryEnabled(true);
	    int idx_viewUser = userQs.appendClassList(OutputViewMemberLink.class, false);
	    int idx_member = -1;

	    target = OutputViewMemberLink.class;
	    target_idx = idx_viewUser;
	    if (!isViewLink) {
		target = ProjectMemberLink.class;
		idx_member = userQs.appendClassList(ProjectMemberLink.class, false);
		target_idx = idx_member;
	    }

	    SearchCondition sc = null;
	    ClassAttribute ca0 = null;
	    ClassAttribute ca1 = null;
	    String tableAlias = userQs.getFromClause().getAliasAt(target_idx);

	    BaseTableInfo basetableinfo = (WTIntrospector.getClassInfo(target)).getDatabaseInfo().getBaseTableInfo();
	    String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke = new KeywordExpression(tableAlias + "." + objname + "||':'||" + tableAlias + "." + objid);
	    ke.setColumnAlias("VIEW_OID");
	    userQs.appendSelect(ke, new int[] { target_idx }, false);

	    sc = new SearchCondition(OutputViewMemberLink.class, "roleAObjectRef.key.id", "=", output.getPersistInfo()
		    .getObjectIdentifier().getId());
	    userQs.appendWhere(sc, new int[] { idx_viewUser });

	    if (!isViewLink) {
		userQs.appendAnd();
		ca0 = new ClassAttribute(OutputViewMemberLink.class, "roleBObjectRef.key.id");
		ca1 = new ClassAttribute(ProjectMemberLink.class, "thePersistInfo.theObjectIdentifier.id");
		sc = new SearchCondition(ca0, "=", ca1);
		sc.setFromIndicies(new int[] { idx_viewUser, idx_member }, 0);
		sc.setOuterJoin(0);
		userQs.appendWhere(sc, new int[] { idx_viewUser, idx_member });
	    }
	    // View User Query .......................................................................... end

	    // View Department Query .......................................................................... begin
	    QuerySpec deptQs = new QuerySpec();
	    deptQs.setAdvancedQueryEnabled(true);
	    int idx_viewDept = deptQs.appendClassList(OutputViewDepartMentLink.class, false);
	    int idx_Dept = -1;

	    target = OutputViewDepartMentLink.class;
	    target_idx = idx_viewDept;
	    if (!isViewLink) {
		target = ProjectViewDepartmentLink.class;
		idx_Dept = deptQs.appendClassList(ProjectViewDepartmentLink.class, false);
		target_idx = idx_Dept;
	    }

	    tableAlias = deptQs.getFromClause().getAliasAt(target_idx);
	    // select
	    basetableinfo = (WTIntrospector.getClassInfo(target)).getDatabaseInfo().getBaseTableInfo();
	    objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();
	    ke = new KeywordExpression(tableAlias + "." + objname + "||':'||" + tableAlias + "." + objid);
	    ke.setColumnAlias("VIEW_OID");
	    deptQs.appendSelect(ke, new int[] { target_idx }, false);

	    sc = new SearchCondition(OutputViewDepartMentLink.class, "roleAObjectRef.key.id", "=", output.getPersistInfo()
		    .getObjectIdentifier().getId());
	    deptQs.appendWhere(sc, new int[] { idx_viewDept });

	    if (!isViewLink) {
		deptQs.appendAnd();
		ca0 = new ClassAttribute(OutputViewDepartMentLink.class, "roleBObjectRef.key.id");
		ca1 = new ClassAttribute(ProjectViewDepartmentLink.class, "thePersistInfo.theObjectIdentifier.id");
		sc = new SearchCondition(ca0, "=", ca1);
		sc.setFromIndicies(new int[] { idx_viewDept, idx_Dept }, 0);
		sc.setOuterJoin(0);
		deptQs.appendWhere(sc, new int[] { idx_viewDept, idx_Dept });
	    }
	    // View Department Query .......................................................................... end

	    CompoundQuerySpec compound = new CompoundQuerySpec();
	    compound.setSetOperator(SetOperator.UNION_ALL);
	    compound.addComponent(userQs);
	    compound.addComponent(deptQs);

	    result = PersistenceHelper.manager.find(compound);
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}
	return result;
    }

    public static boolean deleteOutputViewMember(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("deleteOutputViewMember", "e3ps.project.beans.ProjectOutputHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    ArrayList viewMemberArr = (ArrayList) map.get("viewMember");
	    if (viewMemberArr == null) {
		return true;
	    }

	    ReferenceFactory rf = new ReferenceFactory();
	    trx.start();
	    for (int i = 0; i < viewMemberArr.size(); i++) {
		PersistenceHelper.manager.delete(rf.getReference((String) viewMemberArr.get(i)).getObject());
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return true;
    }

    public static boolean setOutputViewMember(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("setOutputViewMember", "e3ps.project.beans.ProjectOutputHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    String oid = (String) map.get("oid");
	    String viewMemberArr[] = (String[]) map.get("viewMember");

	    ReferenceFactory rf = new ReferenceFactory();
	    ProjectOutput output = null;
	    output = (ProjectOutput) rf.getReference(oid).getObject();

	    trx.start();

	    QueryResult ovdlQr = getOutputViewMember(output, true);
	    Object ovdlObj[] = null;
	    while (ovdlQr.hasMoreElements()) {
		ovdlObj = (Object[]) ovdlQr.nextElement();
		PersistenceHelper.manager.delete(rf.getReference((String) ovdlObj[0]).getObject());
	    }

	    if (viewMemberArr != null && viewMemberArr.length > 0) {
		OutputViewMemberLink ovml = null;
		OutputViewDepartMentLink ovdl = null;
		QueryResult viewQr = null;
		String mOid = null;
		Object mObj = null;
		for (int i = 0; i < viewMemberArr.length; i++) {
		    mOid = viewMemberArr[i];
		    mObj = rf.getReference(mOid).getObject();
		    if (mObj instanceof ProjectMemberLink) {
			ovml = OutputViewMemberLink.newOutputViewMemberLink(output, (ProjectMemberLink) mObj);
			PersistenceHelper.manager.save(ovml);
		    } else if (mObj instanceof WTUser) {
			viewQr = ProjectUserHelper.manager.getQueryForTeamUsers(output.getProject(), (WTUser) mObj,
			        ProjectUserHelper.MEMBER);
			if (viewQr.hasMoreElements()) {
			    ovml = OutputViewMemberLink.newOutputViewMemberLink(output, (ProjectMemberLink) viewQr.nextElement());
			    PersistenceHelper.manager.save(ovml);
			}
		    } else if (mObj instanceof ProjectViewDepartmentLink) {
			ovdl = OutputViewDepartMentLink.newOutputViewDepartMentLink(output, (ProjectViewDepartmentLink) mObj);
			PersistenceHelper.manager.save(ovdl);
		    } else if (mObj instanceof Department) {
			viewQr = ProjectUserHelper.manager.getViewDepartmentLink(output.getProject(), (Department) mObj);
			if (viewQr.hasMoreElements()) {
			    Object o[] = (Object[]) viewQr.nextElement();
			    ovdl = OutputViewDepartMentLink.newOutputViewDepartMentLink(output, (ProjectViewDepartmentLink) o[0]);
			    PersistenceHelper.manager.save(ovdl);
			}
		    }
		}
	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return true;
    }

    public static QueryResult getOutputViewDepartMentLink(ProjectOutput output, ProjectViewDepartmentLink viewLink) {
	QueryResult result = null;
	try {
	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(OutputViewDepartMentLink.class, true);

	    SearchCondition sc = null;

	    sc = new SearchCondition(OutputViewDepartMentLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, output
		    .getPersistInfo().getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx });

	    if (viewLink != null) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(OutputViewDepartMentLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, viewLink
		        .getPersistInfo().getObjectIdentifier().getId());
		qs.appendWhere(sc, new int[] { idx });

	    }

	    result = PersistenceHelper.manager.find(qs);
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}
	return result;
    }

    public static QueryResult getOutputViewMemberLink(ProjectOutput output, ProjectMemberLink memberLink) {
	QueryResult result = null;
	try {
	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(OutputViewMemberLink.class, true);

	    SearchCondition sc = null;

	    sc = new SearchCondition(OutputViewMemberLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, output.getPersistInfo()
		    .getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx });

	    if (memberLink != null) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(OutputViewMemberLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, memberLink
		        .getPersistInfo().getObjectIdentifier().getId());
		qs.appendWhere(sc, new int[] { idx });

	    }

	    result = PersistenceHelper.manager.find(qs);
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}
	return result;
    }

    public static ProjectOutput saveDefProjectOutput(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("saveDefProjectOutput", "e3ps.project.beans.ProjectOutputHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    }
	    return (ProjectOutput) obj;
	}

	ProjectOutput output = null;

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String oid = (String) map.get("oid");
	    String taskOid = (String) map.get("taskOid");
	    String projectOid = (String) map.get("projectOid");
	    String name = (String) map.get("name");
	    String description = (String) map.get("description");
	    String docTypeOid = (String) map.get("docTypeOid");
	    String role = (String) map.get("role");
	    String outputUser = (String) map.get("outputUser");
	    String location = "";
	    String objType = (String) map.get("objType");
	    String outputtype = (String) map.get("outputtype");
	    String isPrimary = (String) map.get("isPrimary");
	    String docOid = StringUtil.checkNull((String) map.get("docOid"));
	    String selectTrustOid = StringUtil.checkNull((String) map.get("selectTrustOid"));
	    String selectAssessOid = StringUtil.checkNull((String) map.get("selectAssessOid"));
	    String docName = StringUtil.checkNull((String) map.get("docName"));
	    String subjectType = StringUtil.checkNull((String) map.get("subjectType"));

	    String costReview1 = StringUtil.checkNull((String) map.get("costReview1"));
	    String costReview2 = StringUtil.checkNull((String) map.get("costReview2"));
	    String costFinal1 = StringUtil.checkNull((String) map.get("costFinal1"));
	    String costFinal2 = StringUtil.checkNull((String) map.get("costFinal2"));

	    String gateCheckType = StringUtil.checkNull((String) map.get("gateCheckType"));

	    if (StringUtils.isNotEmpty(gateCheckType)) {
		gateCheckType = StringUtils.removeEnd(gateCheckType, ",");
	    }

	    ProjectOutPutType popType = (ProjectOutPutType) CommonUtil.getObject(outputtype);

	    if (taskOid == null || name == null) {
		return null;
	    }

	    if (oid == null) {
		oid = "";
	    }

	    if (projectOid == null) {
		projectOid = "";
	    }

	    if (name == null) {
		name = "";
	    }

	    if (description == null) {
		description = "";
	    }

	    if (docTypeOid == null) {
		docTypeOid = "";
	    }

	    if (role == null) {
		role = "";
	    }

	    if (outputUser == null) {
		outputUser = "";
	    }

	    TemplateProject project = null;
	    TemplateTask task = null;
	    ReferenceFactory rf = new ReferenceFactory();
	    E3PSTask e3psTask = null;
	    boolean isUpdate = false;

	    if (oid.length() > 0) {
		output = (ProjectOutput) rf.getReference(oid).getObject();
		isUpdate = true;
	    }
	    if (projectOid.length() > 0) {
		project = (TemplateProject) rf.getReference(projectOid).getObject();
	    }

	    task = (TemplateTask) rf.getReference(taskOid).getObject();

	    if (project == null) {
		project = task.getProject();
	    }

	    Kogger.debug(ProjectOutputHelper.class, "######## docTypeOid= " + docTypeOid);
	    if (docTypeOid.length() > 0) {
		location = KETDmsHelper.service.selectCategoryPath(docTypeOid);

		Kogger.debug(ProjectOutputHelper.class, "######## location= " + location);
	    }

	    if (output == null) {
		output = ProjectOutput.newProjectOutput();
	    }

	    if (!StringUtil.isEmpty(docOid)) {
		// 표준문서 양식정보를 추가하기
		output.setOutputDocOid(docOid);
		output.setOutputDocName(docName);

	    }

	    output.setOutputName(name);
	    output.setOutputDesc(description);
	    output.setOutputLocation(location);
	    output.setOutputRole(role);
	    output.setProject(project);
	    output.setTask(task);
	    output.setObjType(objType);
	    output.setOutputKeyType(docTypeOid);
	    output.setIsPrimary("1".equals(isPrimary));
	    output.setSubjectType(subjectType);

	    if (!"1".equals(isPrimary)) {
		output.setCompletion(0);
	    }

	    if (popType != null) {
		output.setOutputType(popType);
	    } else {
		output.setOutputType(ProjectOutPutType.newProjectOutPutType());
	    }
	    e3psTask = (E3PSTask) task;
	    if (e3psTask.getTaskState() == ProjectStateFlag.PROJECT_STATE_COMPLETE) {
		output.setCompletion(-1);
	    }
	    /*
	     * else{ if(ProjectTaskHelper.isChild(e3psTask)){ output.setCompletion(taskData.taskCompletion); } }
	     */

	    WTPrincipalReference wtuserRef = null;
	    if (outputUser.length() > 0) {
		wtuserRef = null;
		Object ownerObj = rf.getReference(outputUser).getObject();
		if (ownerObj instanceof WTUser) {
		    wtuserRef = WTPrincipalReference.newWTPrincipalReference((WTUser) ownerObj);
		} else if (ownerObj instanceof ProjectMemberLink) {
		    wtuserRef = WTPrincipalReference.newWTPrincipalReference(((ProjectMemberLink) ownerObj).getMember());
		}
	    }
	    if (wtuserRef != null) {
		output.setOwner(wtuserRef);
	    } else {
		output.setOwner(new WTPrincipalReference());
	    }

	    if ("COST".equals(objType) || "SALES".equals(objType)) {
		output.setCostDivision(NumberCodeHelper.manager.getNumberCode("OUPPUTCOSTDIVISION", objType));// 원가구분
	    }

	    if ("COST".equals(objType)) {

		output.setTotalCost(costReview1);// 총원가 검토
		output.setTotalCostFinal(costFinal1);// 총원가 최종
		output.setRate(costReview2);// 수익율 검토
		output.setRateFinal(costFinal2); // 수익율 최종

	    } else if ("SALES".equals(objType)) {

		output.setSalesTarget(costReview1);// 판매목표가 검토
		output.setSalesTargetFinal(costFinal1);// 판매목표가 최종
		output.setYearAverageQty(costReview2); // 연평균수량 검토
		output.setYearAverageQtyFinal(costFinal2); // 연평균수량 최종

	    }
	    output.setGateCheckType(gateCheckType);// 추가점검대상 gate
	    output = (ProjectOutput) PersistenceHelper.manager.save(output);

	    ProjectTaskHelper.updateCompletionFromOutput(e3psTask);

	    // 신뢰성테스크의 경우 산출물 추가시 산출물과 차수객체(selectTrustOid)와의 링크를 생성해 준다
	    if (!StringUtil.isEmpty(selectTrustOid)) {
		setSelectTrustOid(output, selectTrustOid);
	    }

	    if ("newAssess".equals(selectAssessOid)) {
		TaskAssessResult taskAssess = ProjectTaskCompHelper.service.saveTaskAssessResult(taskOid);
		selectAssessOid = CommonUtil.getOIDString(taskAssess);
	    }

	    if (!StringUtil.isEmpty(selectAssessOid)) {
		setSelectAssessOid(output, selectAssessOid);
	    }

	    if (!isUpdate) {
		Kogger.debug(ProjectOutputHelper.class, "산출물 추가  진행률 업데이트 여부 false");
		// ProjectTaskHelper.updateCompletionFromOutput((E3PSTask)output.getTask());
	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return output;
    }

    public static ProjectOutput saveDefProjectOutputGateCheckType(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("saveDefProjectOutputGateCheckType", "e3ps.project.beans.ProjectOutputHelper",
		        null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    }
	    return (ProjectOutput) obj;
	}

	ProjectOutput output = null;

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String oid = (String) map.get("oid");

	    String gateCheckType = StringUtil.checkNull((String) map.get("gateCheckType"));

	    if (StringUtils.isNotEmpty(gateCheckType)) {
		gateCheckType = StringUtils.removeEnd(gateCheckType, ",");
	    }

	    if (oid == null) {
		oid = "";
	    }
	    ReferenceFactory rf = new ReferenceFactory();

	    if (oid.length() > 0) {
		output = (ProjectOutput) rf.getReference(oid).getObject();
		output.setGateCheckType(gateCheckType);// 추가점검대상 gate
		output = (ProjectOutput) PersistenceHelper.manager.save(output);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return output;
    }

    public static ProjectOutput saveDefProjectOutputTemplate(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("saveDefProjectOutputTemplate", "e3ps.project.beans.ProjectOutputHelper",
		        null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    }
	    return (ProjectOutput) obj;
	}

	ProjectOutput output = null;

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String oid = (String) map.get("oid");
	    String taskOid = (String) map.get("taskOid");
	    String projectOid = (String) map.get("projectOid");
	    String name = (String) map.get("name");
	    String description = (String) map.get("description");
	    String docTypeOid = (String) map.get("docTypeOid");
	    String role = (String) map.get("role");
	    String location = "";

	    String objType = (String) map.get("objType");
	    String outputtype = (String) map.get("outputtype");
	    String isPrimary = (String) map.get("isPrimary");

	    String docOid = (String) map.get("docOid");
	    String docName = (String) map.get("docName");

	    String category4 = (String) map.get("category4");
	    String gateCheckType = StringUtil.checkNull((String) map.get("gateCheckType"));

	    // Kogger.debug(getClass(), "isPrimary =====> " + isPrimary);
	    ProjectOutPutType popType = (ProjectOutPutType) CommonUtil.getObject(outputtype);

	    if (taskOid == null || name == null) {
		return null;
	    }

	    if (oid == null) {
		oid = "";
	    }

	    if (projectOid == null) {
		projectOid = "";
	    }

	    if (name == null) {
		name = "";
	    }

	    if (description == null) {
		description = "";
	    }

	    if (docTypeOid == null) {
		docTypeOid = "";
	    }

	    if (role == null) {
		role = "";
	    }

	    if (docOid == null) {
		docOid = "";
	    }

	    if (docName == null) {
		docName = "";
	    }

	    if (category4 == null) {
		category4 = "";
	    }

	    TemplateProject project = null;
	    TemplateTask task = null;
	    // DocCodeType docType = null;
	    ReferenceFactory rf = new ReferenceFactory();

	    if (oid.length() > 0) {
		output = (ProjectOutput) rf.getReference(oid).getObject();
	    }

	    if (projectOid.length() > 0) {
		project = (TemplateProject) rf.getReference(projectOid).getObject();
	    }

	    task = (TemplateTask) rf.getReference(taskOid).getObject();
	    if (project == null) {
		project = task.getProject();
	    }
	    Kogger.debug(ProjectOutputHelper.class, "######## docTypeOid= " + docTypeOid);
	    if (docTypeOid.length() > 0) {
		// docType = (DocCodeType)rf.getReference(docTypeOid).getObject();
		location = KETDmsHelper.service.selectCategoryPath(docTypeOid);
		Kogger.debug(ProjectOutputHelper.class, "######## docTypeOid222= " + location);
	    }

	    // if(docType != null) {
	    // location = docType.getPath();
	    // }else{
	    // location = "";
	    // }

	    if (output == null) {
		output = ProjectOutput.newProjectOutput();
	    }

	    if (StringUtils.isNotEmpty(gateCheckType)) {
		gateCheckType = StringUtils.removeEnd(gateCheckType, ",");
	    }

	    output.setOutputName(name);
	    output.setOutputDesc(description);
	    output.setOutputLocation(location);
	    output.setOutputRole(role);
	    output.setProject(project);
	    output.setTask(task);
	    output.setObjType(objType);
	    output.setOutputKeyType(docTypeOid);
	    output.setIsPrimary("1".equals(isPrimary));
	    output.setOutputDocOid(docOid);
	    output.setOutputDocName(docName);
	    output.setSubjectType(category4);
	    output.setGateCheckType(gateCheckType);// 추가점검대상 gate

	    if (popType != null) {
		output.setOutputType(popType);
	    } else {
		output.setOutputType(ProjectOutPutType.newProjectOutPutType());
	    }

	    // Kogger.debug(getClass(), "######## location= " +location);
	    // Kogger.debug(getClass(), "######## objType= " +popType);
	    // Kogger.debug(getClass(), "######## ProjectOutPutType= " +objType);

	    output = (ProjectOutput) PersistenceHelper.manager.save(output);

	    if (rf == null) {
		rf = new ReferenceFactory();
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return output;
    }

    public static boolean saveGwDocLink(Hashtable hash) throws WTException {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { Hashtable.class };
	    Object args[] = new Object[] { hash };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("saveGwDocLink", ProjectOutputHelper.class.getName(), null, argTypes,
		        args);
		return ((Boolean) obj).booleanValue();

	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    }
	}

	boolean returnValue = false;

	String oid = (String) hash.get("oid"); // ProjectOutput OID
	String docTypeOid = (String) hash.get("docTypeOid");
	String targetDbPath = (String) hash.get("targetDbPath"); // PATH
	String targetUnId = (String) hash.get("targetUnId"); // Unique ID
	String targetSubject = (String) hash.get("targetSubject"); // Title
	String EmpNo = (String) hash.get("EmpNo");

	// url = "http://dsgw2.dsec.co.kr/test/PLMLinkDoc.nsf/AgAutoLoginPLM?OpenAgent&EmpNo=" + EmpNo + "&UserID=" + UserID + "&Source=" +
	// Source + "&State=DocOpen&DbPath=" + document.forms[0].TargetDbPath.value + "&UNID=" + document.forms[0].TargetUNID.value + "&"
	String url = "http://dsgw.dsec.co.kr/PLMLinkDoc.nsf/AgAutoLoginPLM?OpenAgent";
	url += "&EmpNo=" + EmpNo + "&UserID=&Source=PLM&State=DocOpen&DbPath=" + targetDbPath;
	url += "&UNID=" + targetUnId + "&";

	Kogger.debug(ProjectOutputHelper.class, "GWURL<<<<<<<<<<<<<<<<<<<<<<< " + url);

	Hashtable newHash = new Hashtable();

	newHash.put("oid", oid);
	newHash.put("docTypeOid", docTypeOid);
	newHash.put("url", url);

	// test
	newHash.put("lifecycle", "LC_Default");
	newHash.put("name", targetSubject);

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    ReferenceFactory rf = new ReferenceFactory();
	    ProjectOutput output = (ProjectOutput) rf.getReference(oid).getObject();

	    // WTDocument doc = DocumentHelper.saveDocument(newHash);

	    // Kogger.debug(getClass(), "WTDocument Create["+doc.getNumber()+"]>>>> "+CommonUtil.getOIDString(doc));
	    //
	    // LifeCycleManaged lcm = (wt.lifecycle.LifeCycleManaged)doc;
	    // String state = "APPROVED";
	    // LifeCycleHelper.service.setLifeCycleState(lcm, wt.lifecycle.State.toState(state), true);
	    //
	    // Kogger.debug(getClass(), "WTDocument State Change["+doc.getLifeCycleState().getDisplay(Locale.KOREA)+"]");
	    //
	    // ProjectOutputHelper.manager.registryProjectOutput(output, doc);

	    trx.commit();
	    trx = null;

	    return true;
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return returnValue;
    }

    public static ProjectOutput saveProjectOutputDocLink(Hashtable hash) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Hashtable.class };
	    Object args[] = new Object[] { hash };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("saveProjectOutputDocLink", "e3ps.project.beans.ProjectOutputHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectOutputHelper.class, e);
		throw new WTException(e);
	    }
	    return (ProjectOutput) obj;
	}

	ProjectOutput output = null;

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String oid = (String) hash.get("oid");
	    String docOid = (String) hash.get("docOid");

	    if (oid == null || docOid == null) {
		return null;
	    }

	    E3PSProject project = null;
	    KETProjectDocument doc = null;
	    E3PSTask e3psTask = null;
	    RevisionControlled rc = null;
	    ReferenceFactory rf = new ReferenceFactory();
	    output = (ProjectOutput) rf.getReference(oid).getObject();
	    rc = (RevisionControlled) rf.getReference(docOid).getObject();
	    project = (E3PSProject) output.getProject();
	    e3psTask = (E3PSTask) output.getTask();

	    ProjectOutputHelper.manager.registryProjectOutput(output, rc);

	    if (!ProjectTaskHelper.isChild(e3psTask)) {
		if (e3psTask.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE) {
		    output.setCompletion(-1);
		    output.setIsPrimary(false);
		    ProjectTaskHelper.manager.updateCompletion(output);
		} else {
		    // output.setCompletion(100);
		    if (rc.getState().toString().equals("APPROVED")) {
			output.setCompletion(100);
		    }

		    ProjectTaskHelper.manager.updateCompletion(output);
		}

	    } else {
		output.setCompletion(-1);
		ProjectTaskHelper.manager.updateCompletion(output);
	    }

	    if (rc instanceof KETProjectDocument) {
		doc = (KETProjectDocument) rf.getReference(docOid).getObject();
	    }
	    // 프로젝트와 문서 관계 저장
	    // if (rc instanceof KETProjectDocument) {
	    if (false) {
		/*
	         * doc =(KETProjectDocument)rc; if(doc != null) { KETDocumentProjectLink dmLink =
	         * KETDocumentProjectLink.newKETDocumentProjectLink(doc, project); PersistenceHelper.manager.save(dmLink); }
	         */
	    }

	    WTPrincipalReference wtref = null;

	    WTPrincipal principal = SessionHelper.manager.getPrincipal();
	    if (output.getOwner() == null) {
		wtref = WTPrincipalReference.newWTPrincipalReference(principal);
	    } else {
		WTPrincipalReference wr = output.getOwner();
		String oldname = wr.getName() == null ? "" : wr.getName();
		if (!oldname.equals(principal.getName())) {
		    wtref = WTPrincipalReference.newWTPrincipalReference(principal);
		}
	    }

	    if (wtref != null) {
		output.setOwner(wtref);
		output = (ProjectOutput) PersistenceHelper.manager.save(output);
	    }
	    // 산출물과 문서 관계 저장
	    if (output != null && doc != null) {
		KETDocumentOutputLink DoLink;
		DoLink = KETDocumentOutputLink.newKETDocumentOutputLink(doc, output);
		wt.fc.PersistenceServerHelper.manager.insert(DoLink);
		ProjectOutputHelper.manager.registryProjectOutput(output, doc);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return output;
    }

    public static QuerySpec getProjectOutputSpec(ProjectOutPutType type, TemplateProject project) throws Exception {

	QuerySpec spec = new QuerySpec(ProjectOutput.class);
	spec.appendWhere(new SearchCondition(ProjectOutput.class, "outputTypeReference.key.id", "=", type.getPersistInfo()
	        .getObjectIdentifier().getId()), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(ProjectOutput.class, "projectReference.key.id", "=", project.getPersistInfo()
	        .getObjectIdentifier().getId()), new int[] { 0 });
	return spec;

    }

    private static String objToString(Object object) throws WTException {
	if (object == null) {
	    return "";
	}

	String rtnStr = "";
	if (object instanceof String) {
	    rtnStr = (String) object;
	} else if (object instanceof Vector) {
	    String objStr = "";
	    Vector ptVec = (Vector) object;
	    for (int i = 0; i < ptVec.size(); i++) {
		objStr = (String) ptVec.get(i);
		if (objStr != null && objStr.length() > 0) {
		    rtnStr = objStr;
		}
	    }
	}
	return rtnStr;
    }

    public static ProjectOutPutType getOutputTypeObj(ProjectOutPutType obj, String outputType) {
	try {
	    QuerySpec spec = new QuerySpec();
	    Class mainClass = ProjectOutPutType.class;
	    int mainClassPos = spec.addClassList(mainClass, true);
	    spec.appendWhere(
		    new SearchCondition(mainClass, "parentReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(obj)),
		    new int[] { mainClassPos });
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(mainClass, OEMProjectType.NAME, SearchCondition.EQUAL, outputType),
		    new int[] { mainClassPos });
	    Kogger.debug(ProjectOutputHelper.class, "getOutputTypeObj[SPEC]>>>>> " + spec);
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    if (qr.hasMoreElements()) {
		Object[] object = (Object[]) qr.nextElement();
		return (ProjectOutPutType) object[0];
	    }
	} catch (QueryException e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}

	return null;
    }

    public KETProjectDocument getDRTaskOutput(TemplateTask task) {
	KETProjectDocument drDocument = null;
	try {
	    QuerySpec qs = new QuerySpec();
	    int idx_link = qs.appendClassList(OutputDocumentLink.class, true);
	    // int idx_output = qs.appendClassList(ProjectOutput.class, false);
	    int idx_output = qs.appendClassList(ProjectOutput.class, true);

	    SearchCondition sc = null;

	    ClassAttribute ca0 = new ClassAttribute(OutputDocumentLink.class, "roleAObjectRef.key.id");
	    ClassAttribute ca1 = new ClassAttribute(ProjectOutput.class, "thePersistInfo.theObjectIdentifier.id");
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(ca0, "=", ca1);
	    qs.appendWhere(sc, new int[] { idx_link, idx_output });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    sc = new SearchCondition(ProjectOutput.class, "taskReference.key.id", SearchCondition.EQUAL, task.getPersistInfo()
		    .getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx_output });

	    // 산출물의 이름이 DR이 들어간 경우는 조건 제외. 2014.11.01
	    // qs.appendAnd();

	    // sc = new SearchCondition(ProjectOutput.class, ProjectOutput.OUTPUT_NAME, SearchCondition.LIKE, "%DR%");
	    //
	    // qs.appendWhere(sc, new int[] { idx_output });

	    /*
	     * int sortIdx = 0; ClassAttribute sortCa = new ClassAttribute(OutputDocumentLink.class, "thePersistInfo.updateStamp");
	     * sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++)); qs.appendSelect(sortCa, new int[]{idx_link}, false); OrderBy
	     * orderBy = new OrderBy(sortCa, false, null); qs.appendOrderBy(orderBy, new int[]{idx_link});
	     */
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    if (result != null) {
		Object[] obj = null;
		while (result.hasMoreElements()) {

		    Kogger.debug(getClass(), ">>>>>>>>>>>> hasMoreElements ::::::::::: " + result.hasMoreElements());

		    if (drDocument == null) {
			obj = (Object[]) result.nextElement();
			OutputDocumentLink link = (OutputDocumentLink) obj[0];
			ProjectOutput output = (ProjectOutput) obj[1];

			// 산출물의 문서분류가 DR 이름이 들어간 산출물에 대해 DR값을 가져오도록 수정 2014.11.01
			/*
		         * ProjectOutputData outputData = new ProjectOutputData(output); String outputLocationStr = outputData.location; if
		         * (!StringUtil.isEmpty(outputLocationStr)) { outputLocationStr =
		         * outputLocationStr.substring(outputLocationStr.lastIndexOf("/")); if (outputLocationStr.indexOf("DR") < 0) {
		         * continue; } }
		         */

			Kogger.debug(getClass(), ">>>>>>>>>>> link.getOutput ::::::: " + link.getOutput().getOutputName());

			Kogger.debug(getClass(), "=========" + output.getOutputName());

			String docOID = "VR:" + link.getDocClassName() + ":" + link.getBranchIdentifier();
			RevisionControlled document = (RevisionControlled) CommonUtil.getObject(docOID);
			if (document instanceof EPMDocument) {
			    continue;
			}
			/*
		         * Object objRev = (Object) document; drDocument = (KETProjectDocument) objRev;
		         */

			if (document instanceof KETProjectDocument) {
			    // 문서분류체계의 DRCHECKSHEET대상여부를 가지고 DR관리 대상인지 판단하도록 수정 2017.05.15 BY 황정태
			    KETDocumentCategory docCate = null;
			    Kogger.debug(getClass(), "KETProjectDocument////////////");

			    QueryResult r = PersistenceHelper.manager.navigate(document, "documentCategory", KETDocumentCategoryLink.class);
			    if (r != null) {
				if (r.hasMoreElements()) {
				    docCate = (KETDocumentCategory) r.nextElement();
				    if (docCate.getIsDRCheckSheet()) {
					drDocument = (KETProjectDocument) document;
					return drDocument;
				    }
				}
			    }

			}
			/*
		         * if(document instanceof KETProjectDocument) { KETDocumentCategory docCate = null; Kogger.debug(getClass(),
		         * "KETProjectDocument////////////"); QueryResult r = PersistenceHelper.manager.navigate(document,
		         * "documentCategory", KETDocumentCategoryLink.class); if (r.hasMoreElements()){
		         * 
		         * docCate = (KETDocumentCategory) r.nextElement();
		         * 
		         * if(docCate.getIsDRCheckSheet().booleanValue()) drDocument = (KETProjectDocument)document; } }
		         */
		    }
		}
	    }

	} catch (Exception e) {
	    System.out.println("DR 문서 Excepiton 발생");
	    e.printStackTrace();
	    Kogger.error(getClass(), e);
	}
	return drDocument;
    }

    public static E3PSProject getProjectOutPutLinkInfo(ProjectOutput obj) {
	E3PSProject project = null;
	try {
	    if (obj != null) {
		project = (E3PSProject) obj.getProject();
	    }
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}

	return project;
    }

    public static NumberCode getNumberCodeLinkProjectInfo(E3PSProject obj) {
	NumberCode nc = null;
	String ncname = "";
	/*
	 * projectType : 0 개발검토 전자 projectType : 1 개발검토 자동차 projectType : 2 제품 자동차 projectType : 3 금형 자동차 projectType : 4 제품 전자
	 */
	try {
	    if (obj != null) {
		if (obj.getPjtType() == 0) {
		    ncname = "전자사업부";
		} else if (obj.getPjtType() == 1) {
		    ncname = "자동차사업부";
		} else if (obj.getPjtType() == 2) {
		    ncname = "자동차사업부";
		} else if (obj.getPjtType() == 3) {
		    ncname = "자동차사업부";
		} else if (obj.getPjtType() == 4) {
		    ncname = "전자사업부";
		}
		QuerySpec select = new QuerySpec(NumberCode.class);
		select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", "DIVISIONFLAG"), new int[] { 0 });

		select.appendAnd();
		select.appendWhere(new SearchCondition(NumberCode.class, "parentReference.key.id", SearchCondition.EQUAL, (long) 0),
		        new int[] { 0 });

		QueryResult result = PersistenceHelper.manager.find(select);
		NumberCode tempNc = null;
		while (result.hasMoreElements()) {
		    tempNc = (NumberCode) result.nextElement();
		    if (ncname.equals(tempNc.getName())) {
			nc = tempNc;
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}

	return nc;
    }

    /**
     * 신뢰성 평가 테스크의 경우 차수를 관리하는 링크 객체(TaskTrustResult)를 별도로 둬서 각산출물은 차수정보를 가지게 되며 산출물 추가시 차수와의 링크를 생성해서 신뢰도 테스크는 차수정보를 갖게된다 Task와 차수와의
     * 링크정보(TrustTemplateTaskLink)는 차수객체(TaskTrustResult) 생성시 이미 만들어져 있으므로 별도의 링크를 생성하지 않는다
     * 
     * @param task
     * @param output
     * @param selectTrustOid
     * @메소드명 : setSelectTrustOid
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static void setSelectTrustOid(ProjectOutput output, String selectTrustOid) {
	try {
	    ReferenceFactory rf = new ReferenceFactory();
	    TaskTrustResult trustRst = (TaskTrustResult) rf.getReference(selectTrustOid).getObject();
	    TrustProjectOutputLink trustOutLink = TrustProjectOutputLink.newTrustProjectOutputLink(output, trustRst);
	    trustOutLink = (TrustProjectOutputLink) PersistenceHelper.manager.save(trustOutLink);
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}
    }

    /**
     * 
     * 
     * @param output
     * @param selectAssessOid
     * @메소드명 : setSelectAssessOid
     * @작성자 : 황정태
     * @작성일 : 2017. 7. 19.
     * @설명 : 평가관리 차수정보객체와 OUTPUT 링크생성
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static void setSelectAssessOid(ProjectOutput output, String selectAssessOid) {
	try {
	    ReferenceFactory rf = new ReferenceFactory();
	    TaskAssessResult assessRst = (TaskAssessResult) rf.getReference(selectAssessOid).getObject();
	    AssessProjectOutputLink assessOutLink = AssessProjectOutputLink.newAssessProjectOutputLink(output, assessRst);
	    assessOutLink = (AssessProjectOutputLink) PersistenceHelper.manager.save(assessOutLink);
	} catch (Exception e) {
	    Kogger.error(ProjectOutputHelper.class, e);
	}
    }
}
