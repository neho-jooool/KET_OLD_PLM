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

import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.beans.E3PSProjectData;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : MoldProjectScheduleCheckAjax
 * 설명 : [PLM System 1차개선] 금형 Project 일정 체크 Ajax
 * 작성자 : BoLee
 * 작성일자 : 2013. 8. 14.
 */
public class MoldProjectScheduleCheckAjax extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public MoldProjectScheduleCheckAjax() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 요청에 대한 한글처리
        request.setCharacterEncoding("UTF-8");

        JSONObject jsonObject = new JSONObject();

        E3PSProject project = null;
        MoldProject moldProject = null;
        MoldItemInfo moldInfo = null;
        ProductProject productProject = null;
        E3PSProjectData data = null;
        E3PSProjectData ppdata = null;
        String oid = request.getParameter("oid");
        String proudctPlanEnd = "";
        List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultDataMap = new HashMap<String, Object>();
        KETMessageService messageService = KETMessageService.getMessageService(request);

        try {

            if ( StringUtil.checkString(oid) ) {

                // Project OID로부터 Project 객체 가져오기
                project = (E3PSProject)CommonUtil.getObject(oid);

                // Project Data
                data = new E3PSProjectData(project);
            }

            // 금형 Project일 경우 Project 계획완료일 체크(제품 Project 계획완료일 내에서 일정 수립), 2013-08-05, BoLee
            if ( project instanceof MoldProject ) {

                moldProject = (MoldProject)project;
                moldInfo = moldProject.getMoldInfo();

                if ( moldInfo == null ) {
                    moldInfo = MoldItemInfo.newMoldItemInfo();
                }

                if ( moldInfo != null ) {
                    productProject = moldInfo.getProject();
                    ppdata = new E3PSProjectData(productProject);
                }

                if ( data.pjtPlanEndDate != null && ppdata.pjtPlanEndDate != null ) {

                    if ( ppdata.pjtPlanEndDate.compareTo(data.pjtPlanEndDate) == -1 ) {

                        proudctPlanEnd = DateUtil.getDateString(ppdata.pjtPlanEndDate, "D");

                        resultDataMap.put("result", "T");
                        // 제품 프로젝트의 계획 종료일({0}) 일정 안에 \n금형 일정을 수립해야 합니다 \n금형 프로젝트 일정을 조정 하시기 바랍니다
                        resultDataMap.put("message", messageService.getString("e3ps.message.ket_message", "02554", proudctPlanEnd));

                        resultData.add(resultDataMap);
                    }
                }
            }
            if ( resultData.size() == 0 ) {

                resultDataMap.put("result",     "");
                resultDataMap.put("message",    "");

                resultData.add(resultDataMap);
            }

            jsonObject.put("resultData", resultData);

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }

        response.setContentType("application/x-json; charset=UTF-8");
        response.getWriter().print(jsonObject);
    }
}
