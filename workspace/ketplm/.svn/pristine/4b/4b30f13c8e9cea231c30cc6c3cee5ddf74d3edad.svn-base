package e3ps.dms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTAttributeNameIfc;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.treegrid.TreeGridUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.ews.dao.EarlyWarningDao;
import e3ps.project.E3PSProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : CommissonSearchPopupServlet.java 설명 : 개발(검토)의뢰 검색 작성자 : 남현승 작성일자 : 2013.06.27
 */
public class CommissonSearchPopupServlet extends CommonServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String cmd = ParamUtil.getParameter(req, "cmd");
	if (cmd.equals("search")) {
	    // 초기유동관리 지정 생성
	    search(req, res);
	}
    }

    /**
     * 함수명 : search 설명 : 개발(검토)의뢰 검색
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 남현승 작성일자 : 2013.06.27
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {

	// 커넥션
	Connection conn = null;
	ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;
	String detailInfoUrl = null;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // [START][KET PLM 고도화 프로젝트] 개발검토의뢰 팝업 검색 개선, 2014. 6. 18. Jason, Han
	    Class main_class = KETDevelopmentRequest.class;
	    QuerySpec spec = new QuerySpec();
	    int main_idx = spec.addClassList(main_class, true);

	    // 개발단계
	    String developmentStep = (String) hash.get("DevelopmentStepStr");
	    if (StringUtil.checkString(developmentStep)) {
		String[] developmentStepArr = developmentStep.split(",");
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "developmentStep", developmentStepArr, true, false), new int[] { main_idx });
	    }
	    /*
	     * // 사업부구분 String divCode = (String) hash.get("divCode"); if(StringUtil.checkString(divCode)){ if(spec.getConditionCount() > 0) spec.appendAnd(); spec.appendWhere(new
	     * SearchCondition(main_class, "divisionCode", "=", divCode), new int[]{main_idx}); }
	     */
	    // 의뢰번호
	    String requestNo = (String) hash.get("RequestNo");
	    if (StringUtil.checkString(requestNo)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		Class master_class = WTDocumentMaster.class;
		int master_idx = spec.addClassList(master_class, false);
		ClassAttribute ca1 = new ClassAttribute(main_class, "masterReference.key.id");
		ClassAttribute ca2 = new ClassAttribute(master_class, "thePersistInfo.theObjectIdentifier.id");
		SearchCondition sc = new SearchCondition(ca1, "=", ca2);
		sc.setFromIndicies(new int[] { main_idx, master_idx }, 0);
		sc.setOuterJoin(0);
		spec.appendWhere(sc, new int[] { main_idx, master_idx });
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(master_class, WTDocumentMaster.NUMBER, "LIKE", StringUtil.changeString(requestNo, "*", "%"), false), new int[] { master_idx });
	    }

	    // 개발제품명
	    String devProductName = (String) hash.get("DevProductName");
	    if (StringUtil.checkString(devProductName)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "devProductName", "LIKE", "%" + devProductName + "%", false), new int[] { main_idx });
	    }

	    // Project No
	    String projectNo = (String) hash.get("projectNo");
	    if (StringUtil.checkString(projectNo)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		Class proj_class = E3PSProject.class;
		int proj_idx = spec.addClassList(proj_class, false);
		ClassAttribute ca1 = new ClassAttribute(main_class, "thePersistInfo.theObjectIdentifier.id");
		ClassAttribute ca2 = new ClassAttribute(proj_class, "devRequestReference.key.id");
		SearchCondition sc = new SearchCondition(ca1, "=", ca2);
		sc.setFromIndicies(new int[] { main_idx, proj_idx }, 0);
		sc.setOuterJoin(0);
		spec.appendWhere(sc, new int[] { main_idx, proj_idx });
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(proj_class, E3PSProject.PJT_NO, "LIKE", "%" + projectNo + "%", false), new int[] { proj_idx });
	    }

	    // 최신 이터레이션
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(main_class, WTAttributeNameIfc.LATEST_ITERATION, SearchCondition.IS_TRUE), new int[] { main_idx });

	    // 결재상태
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(main_class, "state.state", "=", "APPROVED"), new int[] { main_idx });

	    Kogger.debug(getClass(), spec);

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) spec);
	    if (qr != null && qr.hasMoreElements()) {
		while (qr.hasMoreElements()) {
		    Object[] objArr = (Object[]) qr.nextElement();
		    KETDevelopmentRequest d_request = (KETDevelopmentRequest) objArr[0];
		    String developmentStepStr = ("R".equals(d_request.getDevelopmentStep())) ? "검토의뢰" : "개발의뢰";
		    String prodProjNo = "";
		    QueryResult qrPrj = ProjectHelper.getDevRequestProject(d_request);
		    if (qr != null && qrPrj.size() > 0) {
			if (qr.hasMoreElements()) {
			    Object[] objArr2 = (Object[]) qrPrj.nextElement();
			    E3PSProject proj = (E3PSProject) objArr2[0];
			    prodProjNo = proj.getPjtNo() + " 외 " + (qrPrj.size() - 1) + "건";
			}
		    }

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		    strBuffer.append(" oid=&quot;" + CommonUtil.getOIDString(d_request) + "&quot;"); // oid
		    strBuffer.append(" drState=&quot;" + StringUtil.checkNull(d_request.getLifeCycleState().getDisplay()) + "&quot;"); // state
		    strBuffer.append(" RequestDivision=&quot;" + developmentStepStr + "&quot;"); // 구분
		    strBuffer.append(" RequestNo=&quot;" + StringUtil.checkNull(d_request.getNumber()) + "&quot;"); // 의뢰번호
		    strBuffer.append(" DevPjtNo=&quot;" + prodProjNo + "&quot;"); // projectNo
		    strBuffer.append(" DevProdName=&quot;" + TreeGridUtil.replaceContentForI(StringUtil.checkNull(d_request.getDevProductName())) + "&quot;"); // 개발 제품명
		    strBuffer.append("/>");
		}
	    }
	    // [END][KET PLM 고도화 프로젝트] 개발검토의뢰 팝업 검색 개선, 2014. 6. 18. Jason, Han

	    /*
	     * // 커넥션 생성 conn = PlmDBUtil.getConnection(); //목록 결과 DevRequestDao devRequestDao = new DevRequestDao(conn); Kogger.debug(getClass(), "---------------------- search()   검색조건 ----\n   " + hash
	     * +" ##################");
	     * 
	     * List<Map<String, String>> devRequestOids = (List<Map<String, String>>) devRequestDao.ViewPopDRList(hash);
	     * 
	     * Kogger.debug(getClass(), "---------------------- CommissonSearchPopupServlet   search()     쿼리 결과 ----   " + devRequestOids +" ##################");
	     * 
	     * 
	     * for ( Map<String, String> devReqMap : devRequestOids ) {
	     * 
	     * String oid = (String)devReqMap.get("oid"); String drNo = (String)devReqMap.get("drNo"); String developmentStep = (String)devReqMap.get("developmentStep");
	     * 
	     * if( developmentStep.equals("R") ){ developmentStep = "검토의뢰"; }else{ developmentStep = "개발의뢰"; }
	     * 
	     * String drState = (String)devReqMap.get("drState"); String devProductName = (String)devReqMap.get("devProductName"); String s_prjNo = "";//(String)devReqMap.get("pjtNo");
	     * 
	     * KETDevelopmentRequest dr = (KETDevelopmentRequest)CommonUtil.getObject(oid); QueryResult qrPrj = ProjectHelper.getDevRequestProject(dr); Object[] objPrj = null; E3PSProject eep = null;
	     * if(qrPrj.hasMoreElements()){ String s_prjCnt = String.valueOf(qrPrj.size()-1); objPrj = (Object[])qrPrj.nextElement(); eep = (E3PSProject)objPrj[0]; s_prjNo =
	     * StringUtil.checkNull(eep.getPjtNo()); if(qrPrj.size()>1){ s_prjNo = s_prjNo + "외 " + s_prjCnt + "건"; } }
	     * 
	     * strBuffer.append( "<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;" ); strBuffer.append( " oid=&quot;" + oid + "&quot;" ); // oid strBuffer.append( " drState=&quot;" + drState +
	     * "&quot;" ); // state
	     * 
	     * strBuffer.append( " RequestDivision=&quot;" + developmentStep + "&quot;" ); // 구분 strBuffer.append( " RequestNo=&quot;" + drNo + "&quot;" ); // 의뢰번호 strBuffer.append( " DevPjtNo=&quot;"
	     * + s_prjNo + "&quot;" ); // projectNo strBuffer.append( " DevProdName=&quot;" + TreeGridUtil.replaceContentForI( devProductName ) + "&quot;" ); // 개발 제품명
	     * 
	     * strBuffer.append( "/>" ); }
	     */
	    // 검색조건 셋팅
	    req.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/dms/SearchDevRequestPop.jsp"); //

	    // Kogger.debug(getClass(), "@@@@@@@@@@@  서블릿종료 --\n " + strBuffer.toString() + " ##########   " );

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    // 커넥션 종료
	    PlmDBUtil.close(conn);
	}
    }

    /**
     * 함수명 : excel 설명 : 초기유동관리 검색(엑셀)
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 11. 23.
     */
    private void excel(HttpServletRequest req, HttpServletResponse res) {

	Kogger.debug(getClass(), "----------------------서블릿시작");

	// 커넥션
	Connection conn = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    Hashtable hash = uploader.getFormParameters();

	    // 엑셀여부 셋팅
	    hash.put("isExcel", "Y");
	    hash.put("sort", StringUtil.checkReplaceStr((String) hash.get("sort"), "1 DESC"));

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    EarlyWarningDao earlyWarningDao = new EarlyWarningDao(conn);
	    // 목록 결과
	    // ArrayList list = earlyWarningDao.ViewEarlyWarningList(hash);

	    // 검색조건 셋팅
	    // req.setAttribute( "condition", hash );
	    // 목록 결과 셋팅
	    // req.setAttribute( "list", list);

	    // 검색화면으로 이동
	    gotoResult(req, res, "/jsp/ews/SearchEarlyWarningExcel.jsp");

	    Kogger.debug(getClass(), "----------------------서블릿종료");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    // 커넥션 종료
	    PlmDBUtil.close(conn);
	}
    }

}
