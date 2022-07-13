/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EarlyWarningEndReqServlet.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 11. 09.
 */
package e3ps.ews.servlet;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.util.WTException;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.ews.entity.KETEarlyWarningEndReq;
import e3ps.ews.service.KETEwsHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : EarlyWarningEndReqServlet
 * 설명 : 초기유동관리 해제신청서 서블릿
 * 작성자 : 김경희
 * 작성일자 : 2010. 11. 09.
 */
public class EarlyWarningEndReqServlet extends CommonServlet{

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String cmd = ParamUtil.getParameter(req, "cmd");

        if (cmd.equals("create")){
            //해제신청서 생성
            create(req, res);
        }else if (cmd.equals("update")){
            //해제신청서 수정
            update(req, res);
        }else if (cmd.equals("delete")){
            //해제신청서 삭제
            delete(req, res);
        }
    }

    /**
     * 함수명 : create
     * 설명 : 해제신청서 등록
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2010. 11. 09.
     */
    private void create(HttpServletRequest req, HttpServletResponse res){

        try {

            Kogger.debug(getClass(), "********************"+"서블릿 저장 시작입니다");

            KETMessageService messageService = KETMessageService.getMessageService(req);

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

            Hashtable hash = uploader.getFormParameters();
            Vector files = uploader.getFiles();

            String isTodo = (String)hash.get("isTodo");
            String msg = messageService.getString("e3ps.message.ket_message", "02460");//저장되었습니다.

            // 해제신청서 저장 실행
            KETEarlyWarningEndReq ketEarlyWarningEndReq =KETEwsHelper.service.createEarlyWarningEndReq(hash, files);

            if (ketEarlyWarningEndReq != null){
                //저장 성공 후 화면 이동
                alertNgo(res,msg, "/plm/jsp/ews/ViewEarlyWarningEnd.jsp?endReqOid=" + CommonUtil.getOIDString(ketEarlyWarningEndReq)+ "&todo=" + isTodo);
            }else {
                alert(res, "저장 실패!");
            }

            Kogger.debug(getClass(), "********************"+"서블릿 저장 끝입니다");
        } catch (WTException e) {
            Kogger.error(getClass(), e);
        }
    }

    /**
     * 함수명 : update
     * 설명 : 해제신청서 수정
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2010. 11. 09.
     */
    private void update(HttpServletRequest req, HttpServletResponse res){

        try {

            Kogger.debug(getClass(), "********************"+"서블릿 수정 시작입니다");

            KETMessageService messageService = KETMessageService.getMessageService(req);

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

            Hashtable hash = uploader.getFormParameters();
            Vector files = uploader.getFiles();

            String isTodo = (String)hash.get("isTodo");
            String msg = messageService.getString("e3ps.message.ket_message", "01947");//수정되었습니다.

            // 해제신청서 수정 실행
            KETEarlyWarningEndReq ketEarlyWarningEndReq =KETEwsHelper.service.updateEarlyWarningEndReq(hash, files);

            if (ketEarlyWarningEndReq != null){
                //수정 성공 후 화면 이동
                alertNgo(res,msg, "/plm/jsp/ews/ViewEarlyWarningEnd.jsp?endReqOid=" + CommonUtil.getOIDString(ketEarlyWarningEndReq) + "&todo=" + isTodo);
            }else {
                alert(res, "수정 실패!");
            }

            Kogger.debug(getClass(), "********************"+"서블릿 수정 끝입니다");
        } catch (WTException e) {
            Kogger.error(getClass(), e);
        }
    }

    /**
     * 함수명 : delete
     * 설명 : 해제신청서 삭제
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2010. 11. 09.
     */
    private void delete(HttpServletRequest req, HttpServletResponse res){

        try {

            Kogger.debug(getClass(), "********************"+"서블릿 삭제 시작입니다");

            KETMessageService messageService = KETMessageService.getMessageService(req);

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

            Hashtable hash = uploader.getFormParameters();
            Vector files = uploader.getFiles();

            String isTodo = (String)hash.get("isTodo");

            String msg = messageService.getString("e3ps.message.ket_message", "01699");//삭제되었습니다.

            // 해제신청서 삭제 실행
            boolean flag =KETEwsHelper.service.deleteEarlyWarningEndReq(hash, files);

            if (flag){
                if( isTodo != null && isTodo.equals("Y")){
                    //삭제 성공 후 화면 이동
                    alertNgoParent(res,msg, "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr");
                }else{
                    //삭제 성공 후 화면 이동
                    alertNgoParent(res,msg, "/plm/jsp/ews/ViewEarlyWarning.jsp?oid="+(String)hash.get("oid"));
                }
            }else {
                alert(res, "삭제 실패!");
            }

            Kogger.debug(getClass(), "********************"+"서블릿 삭제 끝입니다");
        } catch (WTException e) {
            Kogger.error(getClass(), e);
        }
    }


}
