package e3ps.project.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import wt.fc.QueryResult;
import wt.org.WTUser;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.TemplateProject;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.ProjectUserHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : TaskMemberAjax
 * 설명 : [PLM System 1차개선] Project 일정 변경 화면에서 Role 변경 시 책임자 Setting
 * 작성자 : BoLee
 * 작성일자 : 2013. 7. 10.
 */
public class TaskMemberAjax extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public TaskMemberAjax() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 요청에 대한 한글처리
        request.setCharacterEncoding("UTF-8");

        JSONObject jsonObject = new JSONObject();

        TemplateProject project = null;
        E3PSProjectData projectData = null;
        String oid          = request.getParameter("oid");
        String personRole   = request.getParameter("personRole");
        QueryResult masterListResult = null;
        List<Map<String, Object>> taskMaster = new ArrayList<Map<String, Object>>();
        Map<String, Object> taskMasterMap = new HashMap<String, Object>();
        ProjectMemberLink pjtMemberLink = null;
        WTUser projectUser = null;

        try {

            if ( StringUtil.checkString(oid) ) {

                // Project OID로부터 Project 객체 가져오기
                project = (TemplateProject)CommonUtil.getObject(oid);
            }

            if ( project != null && StringUtil.checkString(personRole) ) {

                if ( project instanceof E3PSProject ) {

                    // Project 객체로부터 Project 정보 bean 가져오기
                    projectData = new E3PSProjectData( (E3PSProject)project );
                }

                if ( "PM".equals(personRole) ) {

                    // Project PM 가져오기
                    projectUser = projectData.pjtPm;

                    if ( projectUser != null ) {

                        taskMasterMap.put("id",     CommonUtil.getOIDString(projectUser));
                        taskMasterMap.put("name",   projectUser.getFullName());

                        taskMaster.add(taskMasterMap);
                    }
                }
                else {

                    // Project CFT Role 정보에서 Person Role에 해당하는 담당자 가져오기

                    masterListResult = ProjectUserHelper.manager.getProjectRoleMember(project, null, personRole);

                    if ( masterListResult.hasMoreElements() ) {

                        pjtMemberLink = (ProjectMemberLink)masterListResult.nextElement();
                        projectUser = pjtMemberLink.getMember();

                        if ( projectUser != null ) {

                            taskMasterMap.put("id",     CommonUtil.getOIDString(projectUser));
                            taskMasterMap.put("name",   projectUser.getFullName());

                            taskMaster.add(taskMasterMap);
                        }
                    }
                }
            }

            // Role 담당자 정보가 없을 경우 공백 처리
            if ( taskMaster.size() == 0 ) {

                taskMasterMap.put("id",     "");
                taskMasterMap.put("name",   "");

                taskMaster.add(taskMasterMap);
            }

            jsonObject.put("taskMaster", taskMaster);

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }

        response.setContentType("application/x-json; charset=UTF-8");
        response.getWriter().print(jsonObject);
    }
}
