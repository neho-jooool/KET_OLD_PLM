<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
				e3ps.dms.entity.*,
				e3ps.dms.beans.DMSUtil,
				wt.fc.QueryResult,
				wt.fc.PersistenceHelper,
				wt.doc.WTDocument,
				wt.doc.WTDocumentMaster,
				wt.content.*,
				wt.lifecycle.LifeCycleHelper,
				wt.lifecycle.LifeCycleManaged,
				e3ps.common.content.*,
				java.util.Vector,
				java.util.Hashtable,
				java.text.SimpleDateFormat,
				java.util.Date,
				java.util.StringTokenizer,
				java.io.*,
				jxl.Workbook,
				jxl.format.Border,
				jxl.format.BorderLineStyle,
				jxl.write.Label,
				jxl.write.WritableCellFormat,
				jxl.write.WritableSheet,
				jxl.write.WritableWorkbook,
				jxl.write.WriteException,
				e3ps.project.*,
			    e3ps.project.beans.*,
				e3ps.common.util.CommonUtil,
				e3ps.common.util.StringUtil,
				e3ps.common.util.DateUtil"%>
				
				
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>				
<%@page import="e3ps.common.drm.E3PSDRMHelper "  %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%

	KETTechnicalDocument docu = null;
	Hashtable tempHash = new Hashtable();
	int i;
	String docuOid;

	String cmd = request.getParameter("cmd");
	String documentNo = request.getParameter("documentNo");
	String divisionCode = request.getParameter("divisionCode");
    String categoryCode = request.getParameter("categoryCode");
    String documentName = request.getParameter("documentName");
    String authorStatus = request.getParameter("authorStatus");
    String creatorName = request.getParameter("creatorName");
    String predate = request.getParameter("predate");
    String postdate = request.getParameter("postdate");
    String islastversion = request.getParameter("islastversion");
    String sortKey = request.getParameter("sortKey");
    String sortKeyD = request.getParameter("sortKeyD");

    sortKeyD=StringUtil.checkNull(sortKeyD);
	if(sortKeyD.equals("ASC")){
		sortKeyD="DESC";
	}else{
		sortKeyD="ASC";
	}

	tempHash.put("documentNo" , new String(documentNo));
	if(divisionCode.equals("0")) divisionCode = "";
	tempHash.put("divisionCode" , new String(divisionCode));
	tempHash.put("categoryCode" , new String(categoryCode));
	tempHash.put("documentName" , new String(documentName));
	tempHash.put("authorStatus" , new String(authorStatus));
	tempHash.put("creator" , new String(creatorName));
	tempHash.put("predate" , new String(predate));
	tempHash.put("postdate" , new String(postdate));
	tempHash.put("islastversion" , new String(islastversion));
	tempHash.put("sortKey" , new String(sortKey));
	tempHash.put("sortKeyD" , new String(sortKeyD));

	Vector docuOids = DMSUtil.serTechDocumentList(tempHash);

	String sWtHome = "";
	String sFilePath = "", sFileName = "";

	sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	sFilePath = sWtHome + "/codebase/jsp/dms" ;

	SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	sFileName = "SearchTechDocumentList_" +  ff.format(new Date()) + ".xls";

	try {

		WritableWorkbook workbook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
		WritableSheet s1 = workbook.createSheet("First Sheet", 0);

		s1.setName("문서검색목록");

		WritableCellFormat cellFormat = new WritableCellFormat();
	    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

        //문서제목
		Label lr = new jxl.write.Label(0, 0, "문서검색목록");
		s1.addCell(lr);

		int row = 2;
		int rowCount = 0;
		String rowCnt;

		//사업부
		lr = new jxl.write.Label(0, row, "사업부", cellFormat);
		s1.addCell(lr);
		//문서번호
		lr = new jxl.write.Label(1, row, "문서번호", cellFormat);
		s1.addCell(lr);
		//버전
		lr = new jxl.write.Label(2, row, "버전", cellFormat);
		s1.addCell(lr);
		//결재상태
		lr = new jxl.write.Label(3, row, "결재상태", cellFormat);
		s1.addCell(lr);
		//대분류
		lr = new jxl.write.Label(4, row, "대분류", cellFormat);
		s1.addCell(lr);
		//중분류
		lr = new jxl.write.Label(5, row, "중분류", cellFormat);
		s1.addCell(lr);
		//소분류
		lr = new jxl.write.Label(6, row, "소분류", cellFormat);
		s1.addCell(lr);
		//문서명
		lr = new jxl.write.Label(7, row, "문서명", cellFormat);
		s1.addCell(lr);
		//문서설명
		lr = new jxl.write.Label(8, row, "문서설명", cellFormat);
		s1.addCell(lr);
		//작성자
		lr = new jxl.write.Label(9, row, "작성자", cellFormat);
		s1.addCell(lr);
		//작성부서
		lr = new jxl.write.Label(10, row, "작성부서", cellFormat);
		s1.addCell(lr);
		//작성일자
		lr = new jxl.write.Label(11, row, "작성일자", cellFormat);
		s1.addCell(lr);


		if(docuOids.size()>0){
	 	 QueryResult r = null;
	 	 KETDocumentCategory docCate = null;
	 	 String docCatePath = "";
	 	 String versionInfo = "";
	 	 String lifeCycleState = "";
	 	 String insUser = "";
	 	 String insDate = "";

	 	 FormatContentHolder holder = null;
	 	 ContentInfo info = null;
	 	 ContentItem ctf = null;
	 	 String appDataOid = "";
	 	 String urlpath = "";
	 	 String iconpath = "";

	     for(i = 0; i < docuOids.size(); i++) {

	   		docuOid = docuOids.get(i).toString();
	   		docu = (KETTechnicalDocument)CommonUtil.getObject(docuOid);

	   		row++;
			rowCount++;
			rowCnt = String.valueOf(rowCount);

			divisionCode =  StringUtil.checkNull(docu.getDivisionCode());
			if(divisionCode.equals("CA")){
				divisionCode = "자동차";
			}else if(divisionCode.equals("ER")){
				divisionCode = "전자";
			}else{
				divisionCode = "-";
			}

			//사업부
			lr = new jxl.write.Label(0, row, divisionCode, cellFormat);
			s1.addCell(lr);
			//문서번호
			lr = new jxl.write.Label(1, row, docu.getNumber(), cellFormat);
			s1.addCell(lr);
			//버전
			lr = new jxl.write.Label(2, row, StringUtil.checkNull(docu.getVersionIdentifier().getValue()), cellFormat);
			s1.addCell(lr);
			//결재상태
			lr = new jxl.write.Label(3, row, StringUtil.checkNull(docu.getLifeCycleState().getDisplay()), cellFormat);
			s1.addCell(lr);

			String docCatePath1 = "";
			String docCatePath2 = "";
			String docCatePath3 = "";

			try{
				docCate = null;
				r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETTechnicalCategoryLink.class);
		        if (r.hasMoreElements()){
		        	docCate = (KETDocumentCategory) r.nextElement();
		        	categoryCode=docCate.getCategoryCode();

	            	docCatePath= KETDmsHelper.service.selectCategoryPath(categoryCode);
	            	docCatePath=docCatePath.substring(1);

	            	StringTokenizer st = new StringTokenizer(docCatePath, "/");
	            	if (st.hasMoreTokens()) {
		        	   docCatePath1 = st.nextToken();
	            	}
		            if (st.hasMoreTokens()) {
		        	   docCatePath1 = st.nextToken();
	            	}
	            	if (st.hasMoreTokens()) {
		        	   docCatePath2 = st.nextToken();
	            	}
	            	if (st.hasMoreTokens()) {
		        	   docCatePath3 = st.nextToken();
	            	}
	           }
			}catch (Exception e) {
			     Kogger.error(e);
			}

			//대분류
			lr = new jxl.write.Label(4, row, StringUtil.checkNull(docCatePath1), cellFormat);
			s1.addCell(lr);
			//중분류
			lr = new jxl.write.Label(5, row, StringUtil.checkNull(docCatePath2), cellFormat);
			s1.addCell(lr);
			//소분류
			lr = new jxl.write.Label(6, row, StringUtil.checkNull(docCatePath3), cellFormat);
			s1.addCell(lr);

			//문서명
			lr = new jxl.write.Label(7, row, StringUtil.checkNull(docu.getTitle()), cellFormat);
			s1.addCell(lr);

			//문서설명
			lr = new jxl.write.Label(8, row, StringUtil.checkNull(docu.getDocumentDescription()), cellFormat);
			s1.addCell(lr);

			//작성자
			insUser = docu.getCreator().getFullName();
			lr = new jxl.write.Label(9, row, insUser, cellFormat);
			s1.addCell(lr);

			//작성부서
			lr = new jxl.write.Label(10, row, StringUtil.checkNull(docu.getDeptName()), cellFormat);
			s1.addCell(lr);

			//작성일
			insDate = DateUtil.getTimeFormat(docu.getCreateTimestamp(),"yyyy-MM-dd");
			lr = new jxl.write.Label(11, row, insDate, cellFormat);
			s1.addCell(lr);


	   	 }
	   	}
		workbook.write();
		workbook.close();
	} catch (Exception e) {
	    Kogger.error(e);
	   throw e;

	}

	try{
		File file = new File(sFilePath+ "/" + sFileName);

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
		file = E3PSDRMHelper.downloadExcel( file, sFileName, contentID, request.getRemoteAddr() );
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        FileInputStream fin = new FileInputStream(file);
        int ifilesize = (int)file.length();
        byte b[] = new byte[ifilesize];

        response.setContentLength(ifilesize);
        response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
        response.setHeader("Content-Disposition","attachment; filename="+sFileName+";");

        out.clear();
        out.close();

        ServletOutputStream oout = response.getOutputStream();
        fin.read(b);
        oout.write(b,0,ifilesize);
        oout.close();
        fin.close();
        file.delete();

	}catch (Exception e) {
	    Kogger.error(e);
	   throw e;

	}

%>
