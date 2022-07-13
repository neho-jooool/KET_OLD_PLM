/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EarlyWarningStepServlet.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 12. 27.
 */
package e3ps.ews.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.util.WTException;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.ews.entity.KETEarlyWarningStep;
import e3ps.ews.service.KETEwsHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : EarlyWarningServlet
 * 설명 : 초기유동관리 지정 서블릿
 * 작성자 : 김경희
 * 작성일자 : 2010. 8. 31.
 */
public class EarlyWarningStepServlet extends CommonServlet{

	@Override
	protected void doService(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		String cmd = ParamUtil.getParameter(req, "cmd");
		
		if (cmd.equals("stop")){
			//초기유동관리 지정 생성
			stop(req,res);
		}else if(cmd.equals("reopen")){
			//초기유동관리 지정 수정
			reopen(req,res);
		}
	}
	
	/**
	 * 함수명 : stop
	 * 설명 : 초기유동관리 중단
	 * @param HttpServletRequest req , HttpServletResponse res
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 12. 27.
	 */
	private void stop(HttpServletRequest req, HttpServletResponse res){
		try {
			
			Kogger.debug(getClass(), "********************"+"서블릿 중단 시작입니다");
			
			//Form 데이터 받기
			FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
			Hashtable hash = uploader.getFormParameters();
			
			String msg = "중단되었습니다.";
			
			// 초기유동관리 중단 실행
			KETEarlyWarningStep ketEarlyWarningStep = KETEwsHelper.service.stopEarlyWarning(hash);
			
			if (ketEarlyWarningStep != null){
				//중단 성공 후 화면 이동
				alertNgoNcloseParent2(res,msg, "/plm/jsp/ews/ViewEarlyWarning.jsp?oid="+(String)hash.get("oid"));
			}else{
				alert(res,"중단 실패!");
			}
			
			Kogger.debug(getClass(), "********************"+"서블릿 중단 끝입니다");
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		} 

	}
	
	/**
	 * 함수명 : reopen
	 * 설명 : 초기유동관리 재개
	 * @param HttpServletRequest req , HttpServletResponse res
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 12. 27.
	 */
	private void reopen(HttpServletRequest req, HttpServletResponse res){
		try {
			
			Kogger.debug(getClass(), "********************"+"서블릿 재개 시작입니다");
			
			//Form 데이터 받기
			FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
			Hashtable hash = uploader.getFormParameters();
			
			String msg = "재개되었습니다.";
			
			// 초기유동관리 재개 실행
			KETEarlyWarningStep ketEarlyWarningStep = KETEwsHelper.service.reopenEarlyWarning(hash);
			
			if (ketEarlyWarningStep != null){
				//재개 성공 후 화면 이동
				alertNgo(res,msg, "/plm/jsp/ews/ViewEarlyWarning.jsp?oid="+(String)hash.get("oid"));
			}else{
				alert(res,"재개 실패!");
			}
			
			Kogger.debug(getClass(), "********************"+"서블릿 재개 끝입니다");
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		} 

	}
	
}
