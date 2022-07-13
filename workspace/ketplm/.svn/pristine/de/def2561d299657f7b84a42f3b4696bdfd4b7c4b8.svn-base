/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : CreatePartTestServlet.java
 * 작성자 : 이현미
 * 작성일자 : 2010. 9. 13.
 */
package e3ps.bom.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.util.WTException;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.common.web.WebUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : CreatePartTestServlet
 * 설명 : 부품정보 등록 테스트 서블릿
 * 작성자 : 이현미
 * 작성일자 : 2010. 9. 13.
 */
public class CreatePartTestServlet extends CommonServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

            String cmd = ParamUtil.getParameter(req, "cmd");

            if (cmd.equals("create")){
                create(req,res);
            }else if(cmd.equals("update")){
//                update(req,res);
            }else if(cmd.equals("revise")){
//                revise(req,res);
            }else if(cmd.equals("delete")){
//                delete(req,res);
            }
    }

    /**
     * 함수명 : create
     * 설명 : 부품정보 등록
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 이현미
     * 작성일자 : 2010. 9. 13.
     */
    private void create(HttpServletRequest req, HttpServletResponse res){

        Hashtable<?, ?> hashTestPart = null;
        Hashtable<String, String> hash = null;
        try {

            Kogger.debug(getClass(), "********************"+"서블릿 저장 시작입니다");

            hash = WebUtil.getHttpParams(req);

            hashTestPart = KETPartHelper.service.create(hash);
            if (hashTestPart != null){
                alert(res,KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00307")/*저장 성공*/ + "!");
            }else{
                alert(res,KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00308")/*저장 실패*/ + "!");
            }
            Kogger.debug(getClass(), "********************"+"서블릿 저장 끝입니다");
        } catch (WTException e) {
            Kogger.error(getClass(), e);
        }
    }

}
