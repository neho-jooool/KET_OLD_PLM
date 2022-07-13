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

import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.TemplateProject;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : DebugSubTaskAjax
 * 설명 : [PLM System 1차개선] Project 일정 변경 화면에서 Debug Sub Task일 경우 Task명 Enueration Type 구성
 * 작성자 : BoLee
 * 작성일자 : 2013. 7. 21.
 */
public class DebugSubTaskAjax extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public DebugSubTaskAjax() {
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
        String oid = request.getParameter("oid");
        String making = "";
        String debugTaskNames = "";
        List<Map<String, Object>> taskNameEnum = new ArrayList<Map<String, Object>>();
        Map<String, Object> taskNameEnumMap = new HashMap<String, Object>();

        try {

            if ( StringUtil.checkString(oid) ) {

                // Project OID로부터 Project 객체 가져오기
                project = (TemplateProject)CommonUtil.getObject(oid);
            }

            if ( project != null && project instanceof MoldProject) {

                MoldItemInfo info = ((MoldProject)project).getMoldInfo();
                making = info.getMaking();

                if ( making.equals("사내") ) {

                    debugTaskNames = ConfigImpl.getInstance().getString("debgingName_in");
                }
                else {

                    debugTaskNames = ConfigImpl.getInstance().getString("debgingName_out");
                }

                debugTaskNames = "|" + debugTaskNames.replaceAll(";", "|");

                taskNameEnumMap.put("name", debugTaskNames);

                taskNameEnum.add(taskNameEnumMap);
            }

            jsonObject.put("taskNameEnum", taskNameEnum);

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }

        response.setContentType("application/x-json; charset=UTF-8");
        response.getWriter().print(jsonObject);
    }
}
