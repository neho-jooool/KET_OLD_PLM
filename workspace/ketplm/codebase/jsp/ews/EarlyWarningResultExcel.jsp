<%@page contentType="text/html; charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import="e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.ews.entity.KETEarlyWarning,
                e3ps.common.drm.E3PSDRMHelper,
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
	ArrayList list = (ArrayList)request.getAttribute("list");
	Hashtable condition = (Hashtable)request.getAttribute("condition");
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject((String)condition.get("oid"));
%>

<%
	//file path
	String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	String sFilePath = sWtHome + "/codebase/jsp/ews" ;

	//file name
	SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	String sFileName = "SearchEWSList_" +  ff.format(new Date()) + ".xls";

	//sheet 생성
	WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
	WritableSheet s1 = writebook.createSheet("실적 목록", 0);

  //셀의 스타일을 지정
	WritableCellFormat cellFormat = new WritableCellFormat();
  cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);

	int row = 2;

	//문서제목
	Label lr = new Label(0, 0, "실적 목록");
	s1.addCell(lr);

	lr = new Label(0, row, "부품번호", cellFormat);
	s1.addCell(lr);

	lr = new Label(1, row, "부품명", cellFormat);
	s1.addCell(lr);

	lr = new Label(2, row, "월", cellFormat);
	s1.addCell(lr);

	lr = new Label(3, row, "출하수량", cellFormat);
	s1.addCell(lr);

	lr = new Label(4, row, "고객불량수량", cellFormat);
	s1.addCell(lr);

	lr = new Label(5, row, "고객불량건수", cellFormat);
	s1.addCell(lr);

	lr = new Label(6, row, "불량PPM", cellFormat);
	s1.addCell(lr);

	String inoutCol1 = null;
	String inoutCol2 = null;
	if (ketEarlyWarning.getInOut().equals("i")) {
		inoutCol1 = "생산수량";
		inoutCol2 = "공정불량수량";
	}else {
		inoutCol1 = "입고수량";
		inoutCol2 = "수입불량수량";
	}

	lr = new Label(7, row, inoutCol1, cellFormat);
	s1.addCell(lr);

	lr = new Label(8, row, inoutCol2, cellFormat);
	s1.addCell(lr);

	lr = new Label(9, row, "불량PPM", cellFormat);
	s1.addCell(lr);

	lr = new Label(10, row, "시간가동율", cellFormat);
	s1.addCell(lr);

	lr = new Label(11, row, "성능가동율", cellFormat);
	s1.addCell(lr);

	lr = new Label(12, row, "양품율", cellFormat);
	s1.addCell(lr);

	lr = new Label(13, row, "종합효율 ", cellFormat);
	s1.addCell(lr);

	Hashtable<String, String> part = null;

  if( list != null && list.size() > 0 ){
		for(int inx = 0 ; inx < list.size(); inx++){
			part = (Hashtable)list.get(inx);

			row++;

			//부품번호
			lr = new Label(0, row, StringUtil.checkNull((String)part.get("partNo")), cellFormat);
			s1.addCell(lr);

			//부품명
			lr = new Label(1, row, StringUtil.checkNull((String)part.get("partName")), cellFormat);
			s1.addCell(lr);

			//월
			lr = new Label(2, row, StringUtil.checkNull((String)part.get("month")), cellFormat);
			s1.addCell(lr);

			//출하수량
			lr = new Label(3, row, StringUtil.checkNull((String)part.get("outputCnt")), cellFormat);
			s1.addCell(lr);

			//고객불량수량
			lr = new Label(4, row, StringUtil.checkNull((String)part.get("custError")), cellFormat);
			s1.addCell(lr);

			//고객불량건수
			lr = new Label(5, row, StringUtil.checkNull((String)part.get("custErrorCnt")), cellFormat);
			s1.addCell(lr);

			//불량PPM
			lr = new Label(6, row, StringUtil.checkNull((String)part.get("custErrorPPM")), cellFormat);
			s1.addCell(lr);

			//생산수량
			lr = new Label(7, row, StringUtil.checkNull((String)part.get("proCnt")), cellFormat);
			s1.addCell(lr);

			//공정불량수량
			lr = new Label(8, row, StringUtil.checkNull((String)part.get("proError")), cellFormat);
			s1.addCell(lr);

			//불량PPM
			lr = new Label(9, row, StringUtil.checkNull((String)part.get("proErrorPPM")), cellFormat);
			s1.addCell(lr);

			//시간가동율
			lr = new Label(10, row, StringUtil.checkNull((String)part.get("facility")), cellFormat);
			s1.addCell(lr);

			//성능가동율
			lr = new Label(11, row, StringUtil.checkNull((String)part.get("perform")), cellFormat);
			s1.addCell(lr);

			//양품율
			lr = new Label(12, row, StringUtil.checkNull((String)part.get("good")), cellFormat);
			s1.addCell(lr);

			//종합효율
			lr = new Label(13, row, StringUtil.checkNull((String)part.get("total")), cellFormat);
			s1.addCell(lr);
		}
	}
	writebook.write();
	writebook.close();

	try
	{
		File drmfile = new File(sFilePath+ "/" + sFileName);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
		drmfile = E3PSDRMHelper.downloadExcel( drmfile, sFileName, contentID, request.getRemoteAddr() );
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	    FileInputStream fin = new FileInputStream(drmfile);
	    int ifilesize = (int)drmfile.length();
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
	    drmfile.delete();
	}
	catch (Exception e)
	{
	    Kogger.error(e);
	}
%>
