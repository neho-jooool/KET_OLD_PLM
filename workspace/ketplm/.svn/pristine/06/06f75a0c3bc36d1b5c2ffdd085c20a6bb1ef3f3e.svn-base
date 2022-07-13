package e3ps.project.servlet;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.ptc.windchill.uwgm.soap.uwgmdb.Organization;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.OrganizationServicesHelper;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.groupware.company.People;
import e3ps.project.E3PSProject;
import e3ps.project.OutputDocumentLink;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOutput;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.beans.ProjectTaskHelper;
import ext.ket.shared.log.Kogger;

public class SearchProjectOutputServlet extends CommonServlet {
    public void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	// initType=produceproject&command=search&
	String oid = ParamUtil.getStrParameter(req.getParameter("oid"));

	// String initType = ParamUtil.getStrParameter(req.getParameter("initType"));

	req.setAttribute("control", this.getPageControl(req, res));

	// if ( oid != null && oid.length() > 0 ) {
	String type = ParamUtil.getStrParameter(req.getParameter("projectType"));
	if (type.equals("temp")) {
	    // Kogger.debug(getClass(), "TTTTTTTTT");
	    gotoResult(req, res, "/jsp/project/template/TemplateProjectOutputList.jsp");
	} else {
	    // Kogger.debug(getClass(), "EEEEEEEEEE");
	    gotoResult(req, res, "/jsp/project/ProjectOutputList.jsp");
	}
	// }
	/*
	 * else { gotoResult(req, res,"/jsp/project/WorkOutputList.jsp?initType="+initType); }
	 */
    }

    protected QuerySpec getQuerySpec(HttpServletRequest req, HttpServletResponse res) {
	QuerySpec spec = null;

	// String initType = req.getParameter("initType");

	try {
	    String projectType = ParamUtil.getStrParameter(req.getParameter("projectType"));
	    String category = ParamUtil.getStrParameter(req.getParameter("category"));
	    String oid = ParamUtil.getStrParameter(req.getParameter("oid"));
	    String searchPjtNo = ParamUtil.getStrParameter(req.getParameter("searchPjtNo"));
	    String searchDocTitle = ParamUtil.getStrParameter(req.getParameter("searchDocTitle"));
	    String subAll = ParamUtil.getStrParameter(req.getParameter("subAll"));
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    String taskType = ParamUtil.getStrParameter(req.getParameter("taskType"));

	    String chkAll = ParamUtil.getStrParameter(req.getParameter("chkAll"));
	    String isAPQP = ParamUtil.getStrParameter(req.getParameter("isAPQP"));
	    String isPSO10 = ParamUtil.getStrParameter(req.getParameter("isPSO10"));
	    String isESIR = ParamUtil.getStrParameter(req.getParameter("isESIR"));
	    String isISIR = ParamUtil.getStrParameter(req.getParameter("isISIR"));

	    int outputIndex = 0;
	    if ("task".equals(taskType)) {
		spec = new QuerySpec();
		outputIndex = spec.appendClassList(ProjectOutput.class, true);

		SearchCondition sc = null;

		if (oid != null && oid.length() > 0) {

		    if (subAll != null && subAll.length() > 0) {

			TemplateTask task = (TemplateTask) CommonUtil.getObject(oid);
			Vector list = ProjectTaskHelper.getSubTask(task);
			spec.appendOpenParen();

			for (int i = 0; i < list.size(); i++) {
			    if (i > 0) {
				spec.appendOr();
			    }
			    Persistable data = (Persistable) list.get(i);

			    long lid = data.getPersistInfo().getObjectIdentifier().getId();
			    sc = new SearchCondition(ProjectOutput.class, "taskReference.key.id", SearchCondition.EQUAL, lid);
			    spec.appendWhere(sc, new int[] { outputIndex });

			}

			spec.appendCloseParen();

		    } else {
			long oidValue = CommonUtil.getOIDLongValue(oid);

			sc = new SearchCondition(ProjectOutput.class, "taskReference.key.id", SearchCondition.EQUAL, oidValue);
			spec.appendWhere(sc, new int[] { outputIndex });
		    }

		}

	    } else {
		spec = new QuerySpec();
		Class projectOutputClass = ProjectOutput.class;
		Class projectClass;

		if (projectType.equals("temp")) {
		    projectClass = TemplateProject.class;
		} else {
		    projectClass = E3PSProject.class;
		}

		outputIndex = spec.addClassList(projectOutputClass, true);
		int projectClassPos = spec.addClassList(projectClass, false);

		SearchCondition sc = new SearchCondition(new ClassAttribute(projectOutputClass, "projectReference.key.id"), "=",
		        new ClassAttribute(projectClass, "thePersistInfo.theObjectIdentifier.id"));
		sc.setFromIndicies(new int[] { outputIndex, projectClassPos }, 0); // 위에 정의한 SearchCondition 에 인덱스 순서대로 배열을 넘
		sc.setOuterJoin(0); // 0,1,-1 이냐에 따라 outer 조인인지 Inner조인인지 .. +=, =+ ,=
		spec.appendWhere(sc, outputIndex, projectClassPos); // 위에 정의한 SearchCondition 에 인덱스 순서대로 넘김

		long oidValue;
		if (oid != null && oid.length() > 0) {
		    oidValue = CommonUtil.getOIDLongValue(oid);
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendWhere(new SearchCondition(projectOutputClass, "projectReference.key.id", SearchCondition.EQUAL, oidValue),
			    outputIndex);
		} else {
		    oidValue = CommonUtil.getOIDLongValue(wtuser);
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendWhere(new SearchCondition(projectOutputClass, "owner.key.id", SearchCondition.EQUAL, oidValue), outputIndex);
		}

		if (StringUtil.checkString(searchPjtNo)) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendWhere(new SearchCondition(projectClass, "pjtNo", SearchCondition.LIKE, "%" + searchPjtNo + "%"),
			    new int[] { outputIndex });
		}

		if (StringUtil.checkString(searchDocTitle)) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendWhere(
			    new SearchCondition(projectOutputClass, "outputName", SearchCondition.LIKE, "%" + searchDocTitle + "%"),
			    new int[] { outputIndex });
		}

		if (category != null && category.length() > 0) {
		    if ("pre".equals(category)) {
			QuerySpec subSpec = new QuerySpec(OutputDocumentLink.class);
			QueryResult subQr = PersistenceHelper.manager.find(subSpec);
			if (subQr != null) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();
			    int i = 1;
			    spec.appendOpenParen();
			    while (subQr.hasMoreElements()) {
				OutputDocumentLink link = (OutputDocumentLink) subQr.nextElement();
				spec.appendWhere(new SearchCondition(projectOutputClass, "thePersistInfo.theObjectIdentifier.id",
				        SearchCondition.NOT_EQUAL, CommonUtil.getOIDLongValue(link.getOutput())), outputIndex);
				if (i != subQr.size())
				    spec.appendAnd();
				i++;
			    }
			    spec.appendCloseParen();
			}
		    } else if ("post".equals(category)) {
			QuerySpec subSpec = new QuerySpec(OutputDocumentLink.class);
			QueryResult subQr = PersistenceHelper.manager.find(subSpec);
			if (subQr != null) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();
			    int i = 1;
			    spec.appendOpenParen();
			    while (subQr.hasMoreElements()) {
				OutputDocumentLink link = (OutputDocumentLink) subQr.nextElement();
				spec.appendWhere(new SearchCondition(projectOutputClass, "thePersistInfo.theObjectIdentifier.id",
				        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(link.getOutput())), outputIndex);
				if (i != subQr.size())
				    spec.appendOr();
				i++;
			    }
			    spec.appendCloseParen();
			}
		    }
		}

		if (!projectType.equals("temp") && !(oid != null && oid.length() > 0)) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendWhere(new SearchCondition(projectClass, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
			    new int[] { projectClassPos });

		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendWhere(new SearchCondition(projectClass, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
			    new int[] { projectClassPos });
		}

		/*
	         * if(spec.getConditionCount() > 0){ spec.appendAnd(); } spec.appendOpenParen(); spec.appendWhere(new
	         * SearchCondition(projectOutputClass, ProjectOutput.OBJ_TYPE, SearchCondition.EQUAL, "DOC"), new
	         * int[]{projectOutputClassPos}); spec.appendOr(); spec.appendWhere(new SearchCondition(projectOutputClass,
	         * ProjectOutput.OBJ_TYPE, SearchCondition.EQUAL, "DWG"), new int[]{projectOutputClassPos}); spec.appendCloseParen();
	         */

		// SearchUtil.setOrderBy(spec,projectOutputClass,projectOutputClassPos,"taskReference.key.id","task",false);
	    }

	    if (isAPQP.length() > 0 || isPSO10.length() > 0 || isESIR.length() > 0 || isISIR.length() > 0) {
		int categoryIndex = spec.appendClassList(KETDocumentCategory.class, false);

		spec.appendAnd();
		SearchCondition sc = new SearchCondition(new ClassAttribute(ProjectOutput.class, ProjectOutput.OUTPUT_KEY_TYPE), "=",
		        new ClassAttribute(KETDocumentCategory.class, KETDocumentCategory.CATEGORY_CODE));
		sc.setFromIndicies(new int[] { outputIndex, categoryIndex }, 0); // 위에 정의한 SearchCondition 에 인덱스 순서대로 배열을 넘
		sc.setOuterJoin(0); // 0,1,-1 이냐에 따라 outer 조인인지 Inner조인인지 .. +=, =+ ,=
		spec.appendWhere(sc, new int[] { outputIndex, categoryIndex }); // 위에 정의한 SearchCondition 에 인덱스 순서대로 넘김

		boolean isOr = false;
		spec.appendAnd();

		spec.appendOpenParen();
		if (isAPQP.length() > 0) {
		    if (isOr) {
			spec.appendOr();
		    }
		    isOr = true;
		    spec.appendWhere(new SearchCondition(KETDocumentCategory.class, KETDocumentCategory.IS_APQP, SearchCondition.IS_TRUE,
			    true), new int[] { categoryIndex });
		}

		if (isPSO10.length() > 0) {
		    if (isOr) {
			spec.appendOr();
		    }
		    isOr = true;
		    spec.appendWhere(new SearchCondition(KETDocumentCategory.class, KETDocumentCategory.IS_PSO10, SearchCondition.IS_TRUE,
			    true), new int[] { categoryIndex });
		}

		if (isESIR.length() > 0) {
		    if (isOr) {
			spec.appendOr();
		    }
		    isOr = true;
		    spec.appendWhere(new SearchCondition(KETDocumentCategory.class, KETDocumentCategory.IS_ESIR, SearchCondition.IS_TRUE,
			    true), new int[] { categoryIndex });
		}
		if (isISIR.length() > 0) {
		    if (isOr) {
			spec.appendOr();
		    }
		    isOr = true;
		    boolean isCheckType = CommonUtil.isMember("전자사업부");
		    if (isCheckType) {
			spec.appendWhere(new SearchCondition(KETDocumentCategory.class, KETDocumentCategory.IS_ISIRELEC,
			        SearchCondition.IS_TRUE, true), new int[] { categoryIndex });
		    } else {
			spec.appendWhere(new SearchCondition(KETDocumentCategory.class, KETDocumentCategory.IS_ISIRCAR,
			        SearchCondition.IS_TRUE, true), new int[] { categoryIndex });
		    }
		}

		spec.appendCloseParen();
	    }

	    int taskIndex = spec.addClassList(TemplateTask.class, false);

	    SearchCondition sc1 = new SearchCondition(new ClassAttribute(ProjectOutput.class, ProjectOutput.TASK_REFERENCE + ".key.id"),
		    "=", new ClassAttribute(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id"));
	    // sc.setFromIndicies(new int[]{outputIndex, taskIndex}); //위에 정의한 SearchCondition 에 인덱스 순서대로 배열을 넘
	    sc1.setOuterJoin(0); // 0,1,-1 이냐에 따라 outer 조인인지 Inner조인인지 .. +=, =+ ,=
	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }

	    spec.appendWhere(sc1, new int[] { outputIndex, taskIndex });

	    String sortKey = req.getParameter("sortKey");
	    String dsc = req.getParameter("dsc");
	    boolean ascent = false;

	    if (dsc != null && dsc.length() > 0) {
		ascent = Boolean.valueOf(dsc);
	    }

	    if (sortKey != null && sortKey.length() > 0) {
		if (TemplateTask.TASK_NAME.equals(sortKey)) {

		    SearchUtil.setOrderBy(spec, TemplateTask.class, taskIndex, sortKey, "attr", ascent);

		} else if ("Master".equals(sortKey)) {

		    int taskMemberLinkIndex = spec.addClassList(TaskMemberLink.class, false);
		    int projectMemberLinkIndex = spec.addClassList(ProjectMemberLink.class, false);
		    int peopleIndex = spec.addClassList(People.class, false);

		    SearchCondition sc = new SearchCondition(new ClassAttribute(ProjectOutput.class, ProjectOutput.TASK_REFERENCE
			    + ".key.id"), "=", new ClassAttribute(TaskMemberLink.class, "roleAObjectRef.key.id"));
		    // sc.setFromIndicies(new int[]{outputIndex, taskIndex}); //위에 정의한 SearchCondition 에 인덱스 순서대로 배열을 넘
		    sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN); // 0,1,-1 이냐에 따라 outer 조인인지 Inner조인인지 .. +=, =+ ,=
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendWhere(sc, new int[] { outputIndex, taskMemberLinkIndex });

		    sc = new SearchCondition(new ClassAttribute(TaskMemberLink.class, "roleBObjectRef.key.id"), "=", new ClassAttribute(
			    ProjectMemberLink.class, "thePersistInfo.theObjectIdentifier.id"));

		    // sc.setFromIndicies(new int[]{outputIndex, taskIndex}); //위에 정의한 SearchCondition 에 인덱스 순서대로 배열을 넘
		    sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN); // 0,1,-1 이냐에 따라 outer 조인인지 Inner조인인지 .. +=, =+ ,=
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendWhere(sc, new int[] { taskMemberLinkIndex, projectMemberLinkIndex });

		    sc = new SearchCondition(new ClassAttribute(People.class, People.USER_REFERENCE + ".key.id"), "=", new ClassAttribute(
			    ProjectMemberLink.class, "roleBObjectRef.key.id"));
		    // sc.setFromIndicies(new int[]{outputIndex, taskIndex}); //위에 정의한 SearchCondition 에 인덱스 순서대로 배열을 넘
		    sc.setOuterJoin(SearchCondition.LEFT_OUTER_JOIN); // 0,1,-1 이냐에 따라 outer 조인인지 Inner조인인지 .. +=, =+ ,=
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    spec.appendWhere(sc, new int[] { peopleIndex, projectMemberLinkIndex });

		    SearchUtil.setOrderBy(spec, People.class, peopleIndex, People.NAME, "attr", ascent);

		} else if ("isRegistry".equals(sortKey)) {
		    int docLinkIdx = spec.addClassList(OutputDocumentLink.class, false);

		    SearchCondition sc = new SearchCondition(new ClassAttribute(OutputDocumentLink.class, "roleAObjectRef.key.id"), "=",
			    new ClassAttribute(ProjectOutput.class, "thePersistInfo.theObjectIdentifier.id"));

		    sc.setOuterJoin(SearchCondition.LEFT_OUTER_JOIN); // "+="
		    spec.appendAnd();
		    spec.appendWhere(sc, new int[] { docLinkIdx, outputIndex });
		    SearchUtil.setOrderBy(spec, OutputDocumentLink.class, docLinkIdx, "thePersistInfo.theObjectIdentifier.id", "attr",
			    ascent);
		}

		else {
		    SearchUtil.setOrderBy(spec, ProjectOutput.class, outputIndex, sortKey, "attr", ascent);
		}

	    }
	    SearchUtil.setOrderBy(spec, TemplateTask.class, taskIndex, TemplateTask.TASK_NO, "attr1", false);
	    // SearchUtil.setOrderBy(spec, ProjectOutput.class, outputIndex, "thePersistInfo.createStamp", "time", true);

	    // Kogger.debug(getClass(), " SearchProjectOutputServlet QUERY>>>>> \n"+spec+"\n");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return spec;
    }

    private boolean isProjectUser(WTUser wtuser, int memberType) {
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
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return false;
    }

    public static void main(String args[]) throws Exception {
	Class projectClass = E3PSProject.class;
	QuerySpec spec = new QuerySpec();
	Class projectOutputClass = ProjectOutput.class;
	int projectOutputClassPos = spec.addClassList(projectOutputClass, true);
	int projectClassPos = spec.addClassList(projectClass, false);

	SearchCondition sc = new SearchCondition(new ClassAttribute(projectOutputClass, "projectReference.key.id"), "=",
	        new ClassAttribute(projectClass, "thePersistInfo.theObjectIdentifier.id"));
	sc.setFromIndicies(new int[] { projectOutputClassPos, projectClassPos }, 0); // 위에 정의한 SearchCondition 에 인덱스 순서대로 배열을 넘
	sc.setOuterJoin(0); // 0,1,-1 이냐에 따라 outer 조인인지 Inner조인인지 .. +=, =+ ,=
	spec.appendWhere(sc, projectOutputClassPos, projectClassPos); // 위에 정의한 SearchCondition 에 인덱스 순서대로 넘김
	spec.appendAnd();
	spec.appendWhere(
	        new SearchCondition(projectOutputClass, "owner.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(OrganizationServicesHelper.manager.getAuthenticatedUser("205083"))), projectOutputClassPos);

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}

	spec.appendWhere(new SearchCondition(projectClass, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { projectClassPos });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(projectClass, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
	        new int[] { projectClassPos });
	Kogger.debug(SearchProjectOutputServlet.class, "QQQQQQQQQQQQQQQQQQQQ<<<<<<  " + spec);
	QueryResult rs = PersistenceHelper.manager.find(spec);
	// Kogger.debug(getClass(), rs.size());

    }
}
