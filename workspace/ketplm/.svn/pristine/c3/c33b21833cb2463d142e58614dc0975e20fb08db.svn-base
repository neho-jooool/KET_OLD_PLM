<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import="e3ps.common.util.StringUtil,
								e3ps.common.drm.E3PSDRMHelper ,
								java.text.SimpleDateFormat,
								java.util.Date,
								java.util.Hashtable,
								java.util.ArrayList,
								java.io.File,
								java.io.FileInputStream,
								jxl.Workbook,
								jxl.write.WritableWorkbook,
								jxl.write.WritableSheet,
								jxl.write.WritableCellFormat,
								jxl.write.Label,
								jxl.format.Border,
								jxl.format.BorderLineStyle"%>
								
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>								

<%
	Hashtable condition = (Hashtable)request.getAttribute("condition");
	ArrayList list = (ArrayList)request.getAttribute("list");
%>

<%
	//file path
	String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	String sFilePath = sWtHome + "/codebase/jsp/ews" ;

	//file name
	SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	String sFileName = "EwsSearchList_" +  ff.format(new Date()) + ".xls";

	//sheet 생성
	WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
	WritableSheet s1 = writebook.createSheet("초기유동관리 목록", 0);

  //셀의 스타일을 지정
	WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);

	int row = 2;
	int rowCount = 0;

	//문서제목
	Label lr = new Label(0, 0, "초기유동관리 목록");
	s1.addCell(lr);

	lr = new Label(0, row, "번호", cellFormat);
	s1.addCell(lr);

	lr = new Label(1, row, "초기유동관리 No", cellFormat);
	s1.addCell(lr);

	lr = new Label(2, row, "프로젝트번호", cellFormat);
	s1.addCell(lr);

	lr = new Label(3, row, "부품", cellFormat);
	s1.addCell(lr);

	lr = new Label(4, row, "생산처", cellFormat);
	s1.addCell(lr);

	lr = new Label(5, row, "수행담당자(정)", cellFormat);
	s1.addCell(lr);

	lr = new Label(6, row, "작성자", cellFormat);
	s1.addCell(lr);

	lr = new Label(7, row, "작성일자", cellFormat);
	s1.addCell(lr);

	lr = new Label(8, row, "진행단계", cellFormat);
	s1.addCell(lr);

	Hashtable earlyWarning = null;
  if( list != null && list.size() > 0 ){
		for(int inx = 0 ; inx < list.size(); inx++){
			earlyWarning = (Hashtable)list.get(inx);

			row++;
			rowCount++;

			//번호
			lr = new Label(0, row, rowCount + "", cellFormat);
			s1.addCell(lr);

			//초기유동관리 No
			lr = new Label(1, row, StringUtil.checkNull((String)earlyWarning.get("ewsno")), cellFormat);
			s1.addCell(lr);

			//프로젝트번호
			lr = new Label(2, row, StringUtil.checkNull((String)earlyWarning.get("pjtno")), cellFormat);
			s1.addCell(lr);

			//부품
			lr = new Label(3, row, StringUtil.checkNull((String)earlyWarning.get("partno")), cellFormat);
			s1.addCell(lr);

			//생산처
			lr = new Label(4, row, StringUtil.checkNull((String)earlyWarning.get("inout")), cellFormat);
			s1.addCell(lr);

			//수행담당자(정)
			lr = new Label(5, row, StringUtil.checkNull((String)earlyWarning.get("fstcharge")), cellFormat);
			s1.addCell(lr);

			//작성자
			lr = new Label(6, row, StringUtil.checkNull((String)earlyWarning.get("creator")), cellFormat);
			s1.addCell(lr);

			//작성일자
			lr = new Label(7, row, StringUtil.checkNull((String)earlyWarning.get("createdate")), cellFormat);
			s1.addCell(lr);

			//진행단계
			lr = new Label(8, row, StringUtil.checkNull((String)earlyWarning.get("step")), cellFormat);
			s1.addCell(lr);
		}
	}
	writebook.write();
	writebook.close();

	try {
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
	}
	catch (Exception e) {
	    Kogger.error(e);
	}
%>
