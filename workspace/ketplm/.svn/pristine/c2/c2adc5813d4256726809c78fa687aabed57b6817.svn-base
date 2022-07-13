/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : SearchProjectPopServlet.java
 * 작성자 : 오승연
 * 작성일자 : 2010. 11. 10.
 */
package e3ps.project.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QuerySpec;
import e3ps.common.code.NumberCode;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProjectProductInfoLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.beans.SearchPagingProjectHelper;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.TimerUtil;

/**
 * 클래스명 : SearchProjectPopServlet 설명 : 프로젝트 검색 Popup Servlet 작성자 : 오승연 작성일자 : 2010. 11. 10.
 */
public class SearchProjectPopServlet extends CommonServlet {

    /*
     * (non-Javadoc)
     * 
     * @see e3ps.common.web.CommonServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String command = ParamUtil.getStrParameter(req.getParameter("command"));

	if (command.equalsIgnoreCase("search")) {
	    search(req, res);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 프로젝트 검색 서블릿 적용 수정일자 : 2013. 7. 2 수정자 : 김종호
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader.getFormParameters());

	    HashMap<String, String> reqData = new HashMap<String, String>();

	    reqData.put("pjtNo", map.getString("project_no"));
	    reqData.put("pjtName", map.getString("project_name"));
	    // reqData.put( "pjtType", KETParamMapUtil.toString(map.getStringArray("project_type")) );
	    reqData.put("pjtState", KETParamMapUtil.toString(map.getStringArray("project_status")));
	    reqData.put("dType", StringUtil.checkNull(req.getParameter("div_type")));
	    reqData.put("command", StringUtil.checkNull(req.getParameter("command")));

	    TimerUtil timer = new TimerUtil(getServletName());

	    String[] pjtTypes = map.getStringArray("project_type");
	    int pjtTypesLength = (pjtTypes != null) ? pjtTypes.length : 0;
	    if (pjtTypesLength == 0) {
		pjtTypesLength = 3;
		String[] newPjtTypes = { "1", "2", "3" };
		pjtTypes = newPjtTypes;
		map.put("project_type", "total");
	    }
	    for (int k = 0; k < pjtTypesLength; k++) {

		String value = pjtTypes[k];
		reqData.put("pjtType", value);

		timer.setStartPoint("SearchPagingProjectHelper.find");
		result = SearchPagingProjectHelper.find(reqData);
		timer.setEndPoint();

		if (result.size() > 0) {

		    Object resultObj[] = null;

		    timer.setStartPoint("strBuffer.append");
		    while (result.hasMoreElements()) {
			// E3PSProjectData data = null;
			E3PSProject project = null;
			String maker = "";
			String category = "";
			String domain = "";
			String pjtPmName = "";

			String customerCode = "";
			String customerName = "";

			resultObj = (Object[]) result.nextElement();
			project = (E3PSProject) resultObj[0];
			// data = new E3PSProjectData((E3PSProject) resultObj[0]);
			WTUser pm = ProjectUserHelper.manager.getPM(project);
			if (pm != null) {
			    pjtPmName = pm.getFullName();
			}
			if (project.getOemType() != null) {
			    maker = project.getOemType().getMaker().getCode();
			    category = project.getOemType().getCode();
			    if (maker.startsWith("10")) {
				domain = "1000";
			    }
			    else {
				domain = "2000";
			    }
			}

			E3PSProject s_project = null;
			if (project instanceof MoldProject) {
			    MoldProject moldProject = (MoldProject) project;
			    s_project = moldProject.getMoldInfo().getProject();
			}
			else {
			    s_project = project;
			}
			QuerySpec psspec = new QuerySpec(ProjectSubcontractorLink.class);
			SearchUtil.appendEQUAL(psspec, ProjectSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(s_project), 0);
			QueryResult psresult = PersistenceHelper.manager.find(psspec);
			while (psresult != null && psresult.hasMoreElements()) {
			    ProjectSubcontractorLink link = (ProjectSubcontractorLink) psresult.nextElement();
			    if (link != null) {
				NumberCode ncode = link.getSubcontractor();
				customerCode += "," + CommonUtil.getOIDString(ncode);
				customerName += "," + ncode.getName();
			    }
			}

			// [START][KET PLM 고도화 프로젝트] 프로젝트 검색 팝업 속도 개선, 2014. 6. 12. Jason, Han
			String state = project.getState().getState().getDisplay(messageService.getLocale());
			ExtendScheduleData esdata = (ExtendScheduleData) project.getPjtSchedule().getObject();

			strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
			strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
			strBuffer.append(" PjtNo=&quot;" + StringUtil.checkNull(project.getPjtNo()) + "&quot;");
			strBuffer.append(" PjtName=&quot;" + StringUtil.checkNull(project.getPjtName()) + "&quot;");
			strBuffer.append(" PjtPlanStartDate=&quot;" + DateUtil.getDateString(esdata.getPlanStartDate(), "d") + "&quot;");
			strBuffer.append(" PjtPlanEndDate=&quot;" + DateUtil.getDateString(esdata.getPlanEndDate(), "d") + "&quot;");
			strBuffer.append(" PjtState=&quot;" + state + "&quot;");
			strBuffer.append(" PjtOid=&quot;" + CommonUtil.getOIDString(project) + "&quot;");
			strBuffer.append(" Domain=&quot;" + domain + "&quot;");
			strBuffer.append(" Maker=&quot;" + maker + "&quot;");
			strBuffer.append(" Category=&quot;" + category + "&quot;");
			try {
			    strBuffer.append(" Rank=&quot;" + (project.getRank() != null ? project.getRank().getName() : "") + "&quot;");
			} catch (Exception e) {
			}
			strBuffer.append(" Pm=&quot;" + pjtPmName + "&quot;");

			// ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
			String projectInfos4ECM = "";

			// 프로젝트 상태
			projectInfos4ECM += project.getLifeCycleState().toString() + "↕";

			// 개발단계 = 단계구분
			NumberCode process = project.getProcess();
			String devYnOid = (process != null) ? CommonUtil.getOIDString(process) : "";
			String devYnName = (process != null) ? process.getName() : "";
			String devYnCode = (process != null) ? process.getCode() : "";
			projectInfos4ECM += devYnOid + "↔" + devYnName + "↔" + devYnCode + "↕";

			// 대표 차종
			OEMProjectType oemType = project.getOemType();
			String carTypeOid = (oemType != null) ? CommonUtil.getOIDString(oemType) : "";
			String carTypeName = (oemType != null) ? oemType.getName() : "";
			String carTypeCode = (oemType != null) ? oemType.getCode() : "";
			projectInfos4ECM += carTypeOid + "↔" + carTypeName + "↔" + carTypeCode + "↕";

			// 제품정보
			KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
			QueryResult qr = PersistenceHelper.manager.navigate(project, ProjectProductInfoLink.PRODUCT_INFO_ROLE, ProjectProductInfoLink.class, false);
			while (qr.hasMoreElements()) {
			    ProjectProductInfoLink projectProductInfoLink = (ProjectProductInfoLink) qr.nextElement();
			    ProductInfo productInfo = projectProductInfoLink.getProductInfo();

			    String pNum = productInfo.getPNum();
			    String pName = productInfo.getPName();
			    @SuppressWarnings("rawtypes")
			    Hashtable partInfo = ketBOMQueryBean.getPartInfo(pNum);
			    String relPartVersion = (partInfo != null) ? (String) partInfo.get("rev") : "";
			    String relPartOid = (partInfo != null) ? (String) partInfo.get("oid") : "";

			    projectInfos4ECM += relPartOid + "↔" + pNum + "↔" + pName + "↔" + relPartVersion + "↕";

			}


			if (!projectInfos4ECM.equals("")) projectInfos4ECM = projectInfos4ECM.substring(0, projectInfos4ECM.lastIndexOf("↕"));
			strBuffer.append(" projectInfos4ECM=&quot;" + projectInfos4ECM + "&quot;");

			strBuffer.append(" customerCode=&quot;" + customerCode + "&quot;");
			strBuffer.append(" customerName=&quot;" + customerName + "&quot;");

			strBuffer.append("/>");
			// [END][KET PLM 고도화 프로젝트] 프로젝트 검색 팝업 속도 개선, 2014. 6. 12. Jason, Han

			/*
			 * String state = State.toState( data.state ).getDisplay(messageService.getLocale());
			 * 
			 * strBuffer.append( "<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;" ); strBuffer.append( " RowNum=&quot;" + rowCount++ + "&quot;" ); strBuffer.append( " PjtNo=&quot;" +
			 * StringUtil.checkNull(data.pjtNo) + "&quot;" ); strBuffer.append( " PjtName=&quot;" + StringUtil.checkNull(data.pjtName) + "&quot;" ); strBuffer.append(
			 * " PjtPlanStartDate=&quot;" + DateUtil.getDateString(data.pjtPlanStartDate, "d") + "&quot;" ); strBuffer.append( " PjtPlanEndDate=&quot;" +
			 * DateUtil.getDateString(data.pjtPlanEndDate, "d") + "&quot;" ); strBuffer.append( " PjtState=&quot;" + state + "&quot;" ); strBuffer.append( " PjtOid=&quot;" +
			 * data.e3psPjtOID + "&quot;" ); strBuffer.append( " Domain=&quot;" + domain + "&quot;" ); strBuffer.append( " Maker=&quot;" + maker + "&quot;" ); strBuffer.append(
			 * " Category=&quot;" + category + "&quot;" ); strBuffer.append( " Rank=&quot;" + project.getRank().getName() + "&quot;" ); strBuffer.append( "/>" );
			 */
		    }
		}

	    }

	    timer.setEndPoint();
	    timer.display();

	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/project/SearchPjtPop.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }
}
