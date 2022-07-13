/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : PartnerServlet.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 9. 7.
 */
package e3ps.ews.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.util.WTException;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.ews.dao.PartnerDao;
import e3ps.ews.service.KETEwsHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : PartnerServlet
 * 설명 : 협력사 정보 조회 서블릿
 * 작성자 : 김경희
 * 작성일자 : 2010. 9. 7.
 */
public class PartnerServlet extends CommonServlet{

	@Override
	protected void doService(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		Kogger.debug(getClass(), "----------------------서블릿시작");

		String cmd = ParamUtil.getParameter(req, "cmd");

	    if (cmd.equals("interface")){
			//협력사 정보 인터페이스
			partenrInterface(req, res);
		}else{
			//협력사 검색
			search(req, res);
		}

		Kogger.debug(getClass(), "----------------------서블릿종료");
	}

	/**
	 * 함수명 : partenrInterface
	 * 설명 : 협력사 인터페이스
	 * @param HttpServletRequest req , HttpServletResponse res
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 12. 06.
	 */
	private void partenrInterface(HttpServletRequest req, HttpServletResponse res){

		boolean flag = false;

		try {
			flag = KETEwsHelper.service.updatePartner();
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}

		if (flag ){
			//삭제 성공 후 화면 이동
			alertNgo(res,"인터페이스 성공!", "/plm/jsp/ews/interfaceTest.jsp");
		}else{
			alert(res,"인터페이스 실패!");
		}

	}

	/**
	 * 함수명 : search
	 * 설명 : 협력사 검색
	 * @param HttpServletRequest req , HttpServletResponse res
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 12. 06.
	 */
	@SuppressWarnings("unchecked")
	private void search(HttpServletRequest req, HttpServletResponse res){

		Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

		try {

			//Form 데이터 받기
			FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
			Hashtable hash = uploader.getFormParameters();


			// 커넥션 생성
			conn = PlmDBUtil.getConnection();
			PartnerDao partnerDao = new PartnerDao(conn);
			StringBuffer strBuffer = new StringBuffer();
		    int rowCount = 1;

		    ArrayList list = new ArrayList();
			//목록 결과
		    list = partnerDao.ViewPartnerList(hash);

		    Kogger.debug(getClass(), "----------------------서블릿 시작 ");

			for ( int i=0;i< list.size();i++ ) {
				Hashtable<String, String> arrList = new Hashtable<String, String>();
				arrList = ((Hashtable<String, String>)list.get(i));

				String partnerno = (String)arrList.get("partnerno"); // 협력사 번호
				String partnername = (String)arrList.get("partnername"); // 협력사명
				String partnertype = (String)arrList.get("partnertype"); // 종목
				String address = (String)arrList.get("address");  // 주소
				String representative = (String)arrList.get("representative"); // 대표자 성명

				Kogger.debug(getClass(), "---------------------- representative =   " + representative );

                strBuffer.append( "<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;" );
                strBuffer.append( " RowNum=&quot;" + rowCount++ + "&quot;" );
                strBuffer.append( " PartnerNo=&quot;" + partnerno + "&quot;" );
                strBuffer.append( " PartnerName=&quot;" + partnername + "&quot;"  );
                strBuffer.append( " PartnerType=&quot;" + partnertype + "&quot;"  );
                strBuffer.append( " Address=&quot;" + address + "&quot;" );
                strBuffer.append( " Representative=&quot;" + representative + "&quot;" );
                strBuffer.append( "/>" );
			}

			//검색조건 셋팅
			req.setAttribute( "condition", hash );
			req.setAttribute( "searchCondition", hash );	// 검색 화면에서 받은 검색조건
			req.setAttribute( "tgData", strBuffer.toString() );		// 검색 결과 데이터

			//검색화면으로 이동
			gotoResult(req, res, "/jsp/ews/SelectPartnerPopup.jsp");

			Kogger.debug(getClass(), "----------------------서블릿종료");

		} catch (ServletException e) {
			Kogger.error(getClass(), e);
		} catch (IOException e) {
			Kogger.error(getClass(), e);
		}  catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			//커넥션 종료
			PlmDBUtil.close(conn);
		}
	}

}
