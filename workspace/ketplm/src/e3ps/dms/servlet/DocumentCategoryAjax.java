package e3ps.dms.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import e3ps.common.message.KETMessageService;
import e3ps.dms.beans.DMSUtil;
import ext.ket.shared.log.Kogger;

public class DocumentCategoryAjax extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public DocumentCategoryAjax() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 요청에 대한 한글처리
        request.setCharacterEncoding("UTF-8");

        // 다국어 처리
        KETMessageService messageService = KETMessageService.getMessageService(request);

        JSONObject jsonObject = new JSONObject();

        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("locale",      messageService.getLocale());
        parameter.put("docTypeCode", request.getParameter("docTypeCode"));
        parameter.put("parentCode",  request.getParameter("parentCode"));

        List<Map<String, Object>> returnObj = null;
        try {
            returnObj = DMSUtil.getDocumentCategory(parameter);
            jsonObject.put("returnObj", returnObj);

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }

        response.setContentType("application/x-json; charset=UTF-8");
        response.getWriter().print(jsonObject);
    }
}
