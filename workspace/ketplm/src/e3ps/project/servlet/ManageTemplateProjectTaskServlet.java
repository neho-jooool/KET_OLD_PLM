package e3ps.project.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamHash;
import e3ps.common.web.ParamUtil;
import e3ps.common.web.WebUtil;
import e3ps.project.beans.ProjectTaskHelper;

public class ManageTemplateProjectTaskServlet extends CommonServlet {
    
	public void doService(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {
		String command = ParamUtil.getStrParameter(req.getParameter("command"));		
		String oid = ParamUtil.getStrParameter(req.getParameter("oid"));
		
		if ( command.equals("create") ) {
		    create(req, res);
		} else if ( command.equals("update") || command.equals("updateTask") ) {
		    update(req, res);
		    this.onlyAlert(res,"Task가 수정되었습니다.");
		} else if ( command.equals("delete") ) {
		    boolean deleteCheck = delete2(req, res);
		    if(deleteCheck) this.onlyAlert(res,"Task가 삭제되었습니다.");
		} else {
		    res.sendRedirect("/plm/jsp/project/manage/ManageTemplateProjectTaskList.jsp?oid="+oid);    
		}		
	}
	
	private void create(HttpServletRequest req, HttpServletResponse res) {
		String isAjax = req.getParameter("isAjax");
		
		ParamHash paramHash = null;
		
		if("true".equals(isAjax)){
			paramHash = WebUtil.getHttpParamsFromAjax(req);
		}else{
			paramHash = WebUtil.getHttpParams(req);
		}
		
		try{
			ProjectTaskHelper.manager.createTemplateTask(paramHash);
		}catch(Exception e){
			onlyAlertFromException(e, res);
		}
	}
	
	private void update(HttpServletRequest req, HttpServletResponse res) {
		String isAjax = req.getParameter("isAjax");
		
		ParamHash paramHash = null;
		
		if("true".equals(isAjax)){
			paramHash = WebUtil.getHttpParamsFromAjax(req);
		}else{
			paramHash = WebUtil.getHttpParams(req);
		}

	    try{
	    	ProjectTaskHelper.manager.updateTemplateTask(paramHash);
	    } catch(Exception e) {
	    	onlyAlertFromException(e, res);
        }
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse res) {
		
		ParamHash paramHash = null;
		String isAjax = req.getParameter("isAjax");
		if("true".equals(isAjax)){
			paramHash = WebUtil.getHttpParamsFromAjax(req);
		}else{
			paramHash = WebUtil.getHttpParams(req);
		}
	    
	    try {
	    	ProjectTaskHelper.deleteTemplateTask(paramHash);
        } catch (Exception e) {
        	onlyAlertFromException(e, res);
        }
	}

	private boolean delete2(HttpServletRequest req, HttpServletResponse res) {
		
		ParamHash paramHash = null;
		String isAjax = req.getParameter("isAjax");
		if("true".equals(isAjax)){
			paramHash = WebUtil.getHttpParamsFromAjax(req);
		}else{
			paramHash = WebUtil.getHttpParams(req);
		}
	    
	    try {
	    	ProjectTaskHelper.deleteTemplateTask(paramHash);
        } catch (Exception e) {
        	onlyAlertFromException(e, res);
        	return false;
        }
        return true;
	}
}
