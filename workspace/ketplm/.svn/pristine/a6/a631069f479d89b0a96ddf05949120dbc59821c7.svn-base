package e3ps.common.code;

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
import ext.ket.shared.log.Kogger;

public class NumberCodeAjax extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public NumberCodeAjax() {
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
        parameter.put("codeType",    request.getParameter("codeType"));
        parameter.put("code",        request.getParameter("code"));
        parameter.put("name",        request.getParameter("name"));
        parameter.put("oid",         request.getParameter("oid"));
        parameter.put("depthLevel",  request.getParameter("depthLevel"));
        parameter.put("exclude",     request.getParameter("exclude"));
        parameter.put("venderCode",  request.getParameter("venderCode"));
        parameter.put("carCategory", request.getParameter("carCategory"));
        parameter.put("myCode",      request.getParameter("myCode"));

        List<Map<String, Object>> numCode = null;
        try {
            if ( request.getParameter("carCategory") != null && request.getParameter("carCategory").length() > 0 ) {
                numCode = NumberCodeHelper.manager.getCatCategoryList(parameter);
            }
            else {
                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
            }
            jsonObject.put("numCode", numCode);

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }

        response.setContentType("application/x-json; charset=UTF-8");
        response.getWriter().print(jsonObject);
    }
}
