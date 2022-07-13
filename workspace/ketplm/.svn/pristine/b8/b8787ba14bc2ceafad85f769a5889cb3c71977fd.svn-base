<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.ecm.entity.KETMoldECALinkVO" %>
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
<%@page import="jxl.write.VerticalAlignment"%>
<%@page import="jxl.write.WritableCellFormat"%>
<%@page import="jxl.format.Border"%>
<%@page import="jxl.format.Alignment"%>
<%@page import="jxl.format.BorderLineStyle"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETMoldChangeOrderVO" scope="request" />
<%
    String sWtHome = "";
    String sFilePath = "", sFileName = "";
    //file path
    sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    sFilePath = sWtHome + "/codebase/jsp/ecm" ;

    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    sFileName = "DrawingList("+data.getKetMoldChangeOrder().getEcoId()+")_" +  ff.format(new Date()) + ".xls";

    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("도면리스트("+data.getKetMoldChangeOrder().getEcoId()+")", 0);

    s1.mergeCells(0,2,0,3);
    s1.mergeCells(1,2,1,3);
    s1.mergeCells(2,2,2,3);
    s1.mergeCells(3,2,4,2);
    s1.mergeCells(5,2,5,3);
    s1.mergeCells(6,2,6,3);
    s1.mergeCells(7,2,7,3);
    s1.mergeCells(8,2,8,3);

    WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

    WritableCellFormat headerFormat = new WritableCellFormat();
    headerFormat.setBorder(Border.ALL , BorderLineStyle.THIN);
    headerFormat.setAlignment(Alignment.CENTRE); // 셀 가운데 정렬
    headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 수직 가운데 정렬


    int row = 2;
    int rowCount = 0;

    //문서제목
    Label lr = new Label(0, 0, "도면리스트("+data.getKetMoldChangeOrder().getEcoId()+")");
    s1.addCell(lr);

    //타이틀
    lr = new Label(0, row, "No", headerFormat);
    s1.addCell(lr);

    lr = new Label(0, 3, "", headerFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "도면번호", headerFormat);
    s1.addCell(lr);

    lr = new Label(1, 3, "", headerFormat);
    s1.addCell(lr);

    lr = new Label(2, row, "도면명", headerFormat);
    s1.addCell(lr);

    lr = new Label(2, 3, "", headerFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "버전", headerFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "", headerFormat);
    s1.addCell(lr);

    lr = new Label(3, 3, "변경 전", headerFormat);
    s1.addCell(lr);

    lr = new Label(4, 3, "변경 후", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "유형", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, 3, "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, "담당자", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, 3, "", headerFormat);
    s1.addCell(lr);

    lr = new Label(7, row, "완료일", headerFormat);
    s1.addCell(lr);

    lr = new Label(7, 3, "", headerFormat);
    s1.addCell(lr);

    lr = new Label(8, row, "변경내용", headerFormat);
    s1.addCell(lr);

    lr = new Label(8, 3, "", headerFormat);
    s1.addCell(lr);

    row = 3;

    //int size = data.getResultRows();
    int size = data.getKetMoldECALinkVOList().size();
    KETMoldECALinkVO ketMoldECALinkVO = null;
    for(int j=0; j<size; j++) {
        ketMoldECALinkVO =  data.getKetMoldECALinkVOList().get(j);

        if( ketMoldECALinkVO.getActivityType().equals("1") )
        {
            row++;
            //No
            rowCount++;
            lr = new Label(0, row, rowCount + "", cellFormat);
            s1.addCell(lr);

            //도면번호
            lr = new Label(1, row, StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectNo()), cellFormat);
            s1.addCell(lr);

            //도면명
            lr = new Label(2, row, StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectName()), cellFormat);
            s1.addCell(lr);

            //변경 전
            lr = new Label(3, row, StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectPreRev()), headerFormat);
            s1.addCell(lr);

            //변경 후
            lr = new Label(4, row, StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectAfterRev()), headerFormat);
            s1.addCell(lr);

            //유형
            lr = new Label(5, row, StringUtil.checkNull(ketMoldECALinkVO.getChangeType()), headerFormat);
            s1.addCell(lr);

            //담당자
            lr = new Label(6, row, StringUtil.checkNull(ketMoldECALinkVO.getWorkerName()), headerFormat);
            s1.addCell(lr);

            //완료일
            lr = new Label(7, row, StringUtil.checkReplaceStr(ketMoldECALinkVO.getCompleteDate(), ketMoldECALinkVO.getRelEcaObjectPlanDate()), headerFormat);
            s1.addCell(lr);

            //변경내용
            lr = new Label(8, row, StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectActivityComment()), cellFormat);
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
    }catch (Exception e) {
//	   e.printStackTrace();
    }
%>
