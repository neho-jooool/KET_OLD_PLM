<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.ecm.entity.KETSearchEcoReportVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoReportDetailVO" %>
<%@page import="e3ps.common.drm.E3PSDRMHelper "  %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="jxl.write.WritableWorkbook"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="jxl.Workbook"%>
<%@page import="jxl.write.WritableSheet"%>
<%@page import="jxl.write.Label"%>
<%@page import="jxl.write.WritableCellFormat"%>
<%@page import="jxl.format.Border"%>
<%@page import="jxl.format.BorderLineStyle"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoReportVO" scope="request" />

<%
    String sWtHome = "";
    String sFilePath = "", sFileName = "";
    //file path
    sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    sFilePath = sWtHome + "/codebase/jsp/ecm" ;
    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    sFileName = "SearchMonthlyECOList_" +  ff.format(new Date()) + ".xls";

    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("월간 ECO 현황 조회", 0);

    WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

    int row = 2;
    int rowCount = 0;

    Label lr = new Label(0, 0, "월간 ECO 현황 조회");
    s1.addCell(lr);

    lr = new Label(0, row, "번호", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "부서명", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, "작성자", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "전년도합계", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "합계", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "1월", cellFormat);
    s1.addCell(lr);

    lr = new Label(6, row, "2월", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, "3월", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, "4월", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, "5월", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, "6월", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, "7월", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, "8월", cellFormat);
    s1.addCell(lr);

    lr = new Label(13, row, "9월", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, "10월", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, "11월", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, "12월", cellFormat);
    s1.addCell(lr);

    int size = data.getResultRows();
    KETSearchEcoReportDetailVO detailVO = null;
    for(int j=0; j<size; j++) {
        detailVO =  data.getKetSearchEcoReportDetailVOList().get(j);

        row++;
        //번호
        rowCount++;
        lr = new Label(0, row, rowCount + "", cellFormat);
        s1.addCell(lr);

        //부서명
        lr = new Label(1, row, StringUtil.checkNull(detailVO.getObjectNo()), cellFormat);
        s1.addCell(lr);

        //작성자
        lr = new Label(2, row, StringUtil.checkNull(detailVO.getObjectName()), cellFormat);
        s1.addCell(lr);

        //전년도합계
        lr = new Label(3, row, KETStringUtil.money(detailVO.getOldEcoCount()), cellFormat);
        s1.addCell(lr);

        //합계
        lr = new Label(4, row, KETStringUtil.money(detailVO.getEcoCount()), cellFormat);
        s1.addCell(lr);

        //1월
        lr = new Label(5, row, KETStringUtil.money(detailVO.getDataCnt1()), cellFormat);
        s1.addCell(lr);

        //2월
        lr = new Label(6, row, KETStringUtil.money(detailVO.getDataCnt2()), cellFormat);
        s1.addCell(lr);

        //3월
        lr = new Label(7, row, KETStringUtil.money(detailVO.getDataCnt3()), cellFormat);
        s1.addCell(lr);

        //4월
        lr = new Label(8, row, KETStringUtil.money(detailVO.getDataCnt4()), cellFormat);
        s1.addCell(lr);

        //5월
        lr = new Label(9, row, KETStringUtil.money(detailVO.getDataCnt5()), cellFormat);
        s1.addCell(lr);

        //6월
        lr = new Label(10, row, KETStringUtil.money(detailVO.getDataCnt6()), cellFormat);
        s1.addCell(lr);

        //7월
        lr = new Label(11, row, KETStringUtil.money(detailVO.getDataCnt7()), cellFormat);
        s1.addCell(lr);

        //8월
        lr = new Label(12, row, KETStringUtil.money(detailVO.getDataCnt8()), cellFormat);
        s1.addCell(lr);

        //9월
        lr = new Label(13, row, KETStringUtil.money(detailVO.getDataCnt9()), cellFormat);
        s1.addCell(lr);

        //10월
        lr = new Label(14, row, KETStringUtil.money(detailVO.getDataCnt10()), cellFormat);
        s1.addCell(lr);

        //11월
        lr = new Label(15, row, KETStringUtil.money(detailVO.getDataCnt11()), cellFormat);
        s1.addCell(lr);

        //12월
        lr = new Label(16, row, KETStringUtil.money(detailVO.getDataCnt12()), cellFormat);
        s1.addCell(lr);
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
    }catch (Exception e) {
//	   e.printStackTrace();
    }
%>
