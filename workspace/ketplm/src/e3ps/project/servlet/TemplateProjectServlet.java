package e3ps.project.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.common.web.WebUtil;
import e3ps.project.E3PSProjectHelper;
import e3ps.project.TemplateProject;
import e3ps.project.beans.ProjectScheduleHelper;
import ext.ket.shared.log.Kogger;

public class TemplateProjectServlet extends CommonServlet {
    public void doService(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {
		Hashtable paramHash = WebUtil.getHttpParams(req);
		String command = ParamUtil.getStrParameter(req.getParameter("command"));
		String oid = ParamUtil.getStrParameter(req.getParameter("oid"));

		if ( command.equals("create") ) {
		    create(req);
		    this.alertNgo(res,"템플릿 프로젝트가 생성되었습니다.","/plm/servlet/e3ps/SearchProjectTemplateServlet");
		} else if ( command.equals("update") ) {
		    update(req);
		    this.alertNgo(res,"템플릿 프로젝트가 수정되었습니다.","/plm/jsp/project/manageTemplateProjectTaskList.jsp?oid="+oid);
		} else if ( command.equals("delete") ) {
		    delete(req);
		    this.alertNgo(res,"템플릿 프로젝트가 삭제되었습니다.","/plm/jsp/project/manageTemplateProjectTaskList.jsp?oid="+oid);
		} else if ( command.equals("excelDown")){

			excelDown(req, res);
		}
		else {
		    res.sendRedirect("/plm/servlet/e3ps/SearchProjectTemplateServlet");
		}
	}

    private void excelDown(HttpServletRequest req, HttpServletResponse res){
		String strClient = req.getHeader("User-Agent");

		String oid = req.getParameter("oid");
		TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);

		String fileName = "WBS";

		if (strClient.indexOf("MSIE 5.5") > -1){
            res.setHeader("Content-Disposition", "filename=" + fileName + ".xls;");
        }

        else{
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls;");
        }

		try {
			ProjectScheduleHelper.excelWBS(project, res.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Kogger.error(getClass(), e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Kogger.error(getClass(), e);
		}
	}

	private void create(HttpServletRequest req) {
		Hashtable paramHash = WebUtil.getHttpParams(req);
		Hashtable data = new Hashtable();
		data.put("NAME",(String)paramHash.get("name"));
		data.put("DURATION",(String)paramHash.get("duration"));
		data.put("DESCRIPTION",(String)paramHash.get("description"));
		data.put("TEMPID",(String)paramHash.get("tempid"));
		data.put("IMSI",(String)paramHash.get("IMSI"));

	    try {
			E3PSProjectHelper.service.createTemplateProject(data);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
	}

	private void update(HttpServletRequest req) {

	}

	private void delete(HttpServletRequest req) {

	}
}
