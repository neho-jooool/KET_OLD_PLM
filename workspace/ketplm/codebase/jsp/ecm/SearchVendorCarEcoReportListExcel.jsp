<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.ecm.entity.KETSearchEcoVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoDetailVO" %>
<%@page import="e3ps.common.drm.E3PSDRMHelper "  %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="e3ps.common.util.StringUtil"%>
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
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoVO" scope="request" />
<%
String sortCol1 = messageService.getString("e3ps.message.ket_message", "00199")/*ECO번호*/;
String sortSQL = data.getSortColumn().replace(" ", "");
if(sortSQL.equals("1ASC")) {
    sortCol1 += "(▲)";
} else if(sortSQL.equals("1DESC")) {
    sortCol1 += "(▼)";
}
String sortCol2 = "ECO 제목";
if(sortSQL.equals("2ASC")) {
    sortCol2 += "(▲)";
} else if(sortSQL.equals("2DESC")) {
    sortCol2 += "(▼)";
}
String sortCol6 = "예산확보여부";
if(sortSQL.equals("6ASC")) {
    sortCol6 += "(▲)";
} else if(sortSQL.equals("6DESC")) {
    sortCol6 += "(▼)";
}
String sortCol7 = "작성자";
if(sortSQL.equals("7ASC")) {
    sortCol7 += "(▲)";
} else if(sortSQL.equals("7DESC")) {
    sortCol7 += "(▼)";
}
String sortCol8 = "작성일자";
if(sortSQL.equals("8ASC")) {
    sortCol8 += "(▲)";
} else if(sortSQL.equals("8DESC")) {
    sortCol8 += "(▼)";
}
String sortCol9 = "결재상태";
if(sortSQL.equals("9ASC")) {
    sortCol9 += "(▲)";
} else if(sortSQL.equals("9DESC")) {
    sortCol9 += "(▼)";
}

    String sWtHome = "";
    String sFilePath = "", sFileName = "";
    //file path
    sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    sFilePath = sWtHome + "/codebase/jsp/ecm" ;
    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    sFileName = "SearchVendorCarECOList_" +  ff.format(new Date()) + ".xls";

    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("ECO 목록", 0);

    WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

    int row = 2;
    int rowCount = 0;

    //문서제목
    Label lr = new Label(0, 0, "금형 ECO 목록");
    s1.addCell(lr);

    //타이틀
    lr = new Label(0, row, "번호", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, sortCol1, cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sortCol2, cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "부품 번호", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "부품명", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "설계변경 사유", cellFormat);
    s1.addCell(lr);

    lr = new Label(6, row, sortCol6, cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, sortCol7, cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, sortCol8, cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, sortCol9, cellFormat);
    s1.addCell(lr);

    int size = data.getResultRows();
    KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
    for(int j=0; j<size; j++) {
        ketSearchEcoDetailVO =  data.getKetSearchEcoDetailVOList().get(j);

        row++;
        //번호
        rowCount++;
        lr = new Label(0, row, rowCount + "", cellFormat);
        s1.addCell(lr);

        //ECO 번호
        lr = new Label(1, row, StringUtil.checkNull(ketSearchEcoDetailVO.getEcoId()), cellFormat);
        s1.addCell(lr);

        //ECO 제목
        lr = new Label(2, row, StringUtil.checkNull(ketSearchEcoDetailVO.getEcoName()), cellFormat);
        s1.addCell(lr);

        //부품 번호
        lr = new Label(3, row, StringUtil.checkNull(ketSearchEcoDetailVO.getPartNumber()), cellFormat);
        s1.addCell(lr);

        //부품명
        lr = new Label(4, row, StringUtil.checkNull(ketSearchEcoDetailVO.getPartName()), cellFormat);
        s1.addCell(lr);

        //설계변경 사유
        lr = new Label(5, row, StringUtil.checkNull(ketSearchEcoDetailVO.getChangeReason()), cellFormat);
        s1.addCell(lr);

        //예산확보여부
        lr = new Label(6, row, StringUtil.checkNull(ketSearchEcoDetailVO.getSecureBudgetYn()), cellFormat);
        s1.addCell(lr);

        //작성자
        lr = new Label(7, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCreatorName()), cellFormat);
        s1.addCell(lr);

        //작성일자
        lr = new Label(8, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDate()), cellFormat);
        s1.addCell(lr);

        //결재상태
        lr = new Label(9, row, StringUtil.checkNull(ketSearchEcoDetailVO.getSancStateFlag()), cellFormat);
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
//     e.printStackTrace();
    }
%>
