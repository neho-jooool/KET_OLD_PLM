package e3ps.project.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamHash;
import e3ps.common.web.ParamUtil;
import e3ps.common.web.WebUtil;
import e3ps.project.E3PSTask;
import e3ps.project.beans.ProjectStateFlag;
import e3ps.project.beans.ProjectTaskHelper;
import ext.ket.shared.log.Kogger;

public class ManageProjectTaskServlet extends CommonServlet {

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String command = ParamUtil.getStrParameter(req.getParameter("command"));
	String oid = ParamUtil.getStrParameter(req.getParameter("oid"));

	if (command.equalsIgnoreCase("create")) {
	    create(req, res);
	}
	else if (command.equalsIgnoreCase("update") || command.equalsIgnoreCase("taskUpdate")) {
	    update(req, res);
	}
	else if (command.equalsIgnoreCase("delete")) {
	    delete(req, res);
	}
	else if (command.equals("complete")) {
	    this.alertNreloadNclose(res, complete(req));
	}
	else {
	    res.sendRedirect("/plm/jsp/project/manage/ManageProjectTaskList.jsp?oid=" + oid);
	}
    }

    private void create(HttpServletRequest req, HttpServletResponse res) {
	String isAjax = req.getParameter("isAjax");
	ParamHash paramHash = null;

	if ("true".equals(isAjax)) {
	    paramHash = WebUtil.getHttpParamsFromAjax(req);
	}
	else {
	    paramHash = WebUtil.getHttpParams(req);
	}
	try {
	    ProjectTaskHelper.createTask(paramHash);
	} catch (Exception e) {
	    onlyAlertFromException(e, res);
	}
    }

    private void update(HttpServletRequest req, HttpServletResponse res) {

	String isAjax = req.getParameter("isAjax");


	ParamHash paramHash = null;

	if ("true".equals(isAjax)) {
	    paramHash = WebUtil.getHttpParamsFromAjax(req);
	}
	else {
	    paramHash = WebUtil.getHttpParams(req);
	}


	try {
	    ProjectTaskHelper.updateTask(paramHash);
	} catch (Exception e) {
	    onlyAlertFromException(e, res);
	}
    }

    private void delete(HttpServletRequest req, HttpServletResponse res) {

	ParamHash paramHash = null;
	String isAjax = req.getParameter("isAjax");
	if ("true".equals(isAjax)) {
	    paramHash = WebUtil.getHttpParamsFromAjax(req);
	}
	else {
	    paramHash = WebUtil.getHttpParams(req);
	}

	try {
	    ProjectTaskHelper.deleteTask(paramHash);
	} catch (Exception e) {
	    onlyAlertFromException(e, res);
	}
    }

    public String complete(HttpServletRequest req) {
	String oid = (String) req.getParameter("oid");
	int isComplete = Integer.parseInt((String) req.getParameter("isComplete"));

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	String msg = "";

	if (task.getTaskCompletion() == 100) {
	    try {
		task.setTaskState(ProjectStateFlag.TASK_STATE_COMPLETE);
		ProjectTaskHelper.updateCompletion(task);
		msg = "Task가 완료되었습니다.";
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	}
	else {
	    msg = "Task가 100%가 되어 있지 않습니다.";
	}

	return msg;
    }
}
