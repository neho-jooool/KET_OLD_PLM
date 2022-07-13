<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.ecm.entity.KETMoldECALinkVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoDetailVO" %>
<%@page import="e3ps.common.drm.E3PSDRMHelper "  %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Hashtable"%>
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

    ArrayList<Hashtable<String, String>> stdPartList = data.getStdPartList();

    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    sFileName = "StandardPartList("+data.getKetMoldChangeOrder().getEcoId()+")_" +  ff.format(new Date()) + ".xls";

    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("표준품리스트("+data.getKetMoldChangeOrder().getEcoId()+")", 0);


    WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

    WritableCellFormat headerFormat = new WritableCellFormat();
    headerFormat.setBorder(Border.ALL , BorderLineStyle.THIN);
    headerFormat.setAlignment(Alignment.CENTRE); // 셀 가운데 정렬
    headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 수직 가운데 정렬


    int row = 2;
    int rowCount = 0;

    //문서제목
    Label lr = new Label(0, 0, "Die No : "+ ((stdPartList != null && stdPartList.size() > 0) ? stdPartList.get(0).get("die_no") : "") );
    s1.addCell(lr);

    //타이틀
    lr = new Label(0, row, "No", headerFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "금형 부품번호", headerFormat);
    s1.addCell(lr);

    lr = new Label(2, row, "부품명", headerFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "유형", headerFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "비고", headerFormat);
    s1.addCell(lr);

    //int size = data.getResultRows();
    int size = stdPartList.size();
    Hashtable<String, String> stdPartHash = null;
    for(int j=0; j<size; j++) {
        stdPartHash =  stdPartList.get(j);
        row++;

        //No
        rowCount++;
        lr = new Label(0, row, rowCount + "", cellFormat);
        s1.addCell(lr);

        //Part No
        lr = new Label(1, row, stdPartHash.get("part_no"), headerFormat);
        s1.addCell(lr);

        //Part Name
        lr = new Label(2, row, StringUtil.checkNull(stdPartHash.get("part_name")), cellFormat);
        s1.addCell(lr);

        //유형
        lr = new Label(3, row, StringUtil.checkNull(stdPartHash.get("change_type")), headerFormat);
        s1.addCell(lr);

        //비고
        lr = new Label(4, row, StringUtil.checkNull(stdPartHash.get("part_desc")), cellFormat);
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
