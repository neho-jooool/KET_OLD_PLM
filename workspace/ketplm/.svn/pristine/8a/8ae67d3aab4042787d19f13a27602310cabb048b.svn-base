<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import="e3ps.common.drm.E3PSDRMHelper ,
                e3ps.common.util.*,
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
    String checkDept = StringUtil.checkNull(request.getParameter("checkDept"));

    ArrayList list = (ArrayList)request.getAttribute("productList");

    //file path
    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    String sFilePath = sWtHome + "/codebase/jsp/project" ;

    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    String sFileName = "ProductTaskReportList_" +  ff.format(new Date()) + ".xls";

    //sheet 생성
    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("Task진행현황", 0);

    //셀의 스타일을 지정
    WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);
    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
    cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    WritableCellFormat headerFormat = new WritableCellFormat();
    headerFormat.setBorder(Border.ALL , BorderLineStyle.THIN);
    headerFormat.setBackground(jxl.format.Colour.LIGHT_TURQUOISE);
    headerFormat.setAlignment(jxl.format.Alignment.CENTRE);
    headerFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    int row = 2;

    //문서제목
    Label lr = new Label(0, 0, "ProjectTask진행현황");
    s1.addCell(lr);

    lr = new Label(0, row, "구분", headerFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "개발프로젝트", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "개발검토", headerFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(0, row, "", headerFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "완료", headerFormat);
    s1.addCell(lr);

    lr = new Label(2, row, "진행", headerFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "지연", headerFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "전체", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "완료", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, "진행", headerFormat);
    s1.addCell(lr);

    lr = new Label(7, row, "지연", headerFormat);
    s1.addCell(lr);

    lr = new Label(8, row, "전체", headerFormat);
    s1.addCell(lr);

    Hashtable productProject = null;
    /*  프로젝트 TASK START  */
    int p11 = 0;
    int p12 = 0;
    int p13 = 0;
    int p21 = 0;
    int p22 = 0;
    int p23 = 0;
    int p31 = 0;
    int p32 = 0;
    int p33 = 0;
    int p41 = 0;
    int p42 = 0;
    int p43 = 0;
    /*  프로젝트 TASK END  */
    /*  검토프로젝트 TASK START  */
    int r11 = 0;
    int r12 = 0;
    int r13 = 0;
    int r21 = 0;
    int r22 = 0;
    int r23 = 0;
    int r31 = 0;
    int r32 = 0;
    int r33 = 0;
    int r41 = 0;
    int r42 = 0;
    int r43 = 0;
    /*  검토프로젝트 TASK END  */

    if( list != null && list.size() > 0 ){
        for(int inx = 0 ; inx < list.size(); inx++){
            productProject = (Hashtable)list.get(inx);
            if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                p11 = p11 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                p12 = p12 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("D") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("지연")
                 && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                p13 = p13 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                p21 = p21 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                p22 = p22 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("D") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                p23 = p23 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                p31 = p31 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                p32 = p32 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("D") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                p33 = p33 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("완료")  ){
                p41 = p41 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("진행")  ){
                p42 = p42 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("D") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                p43 = p43 +  Integer.parseInt((String)productProject.get("count"));
            }
            else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                r11 = r11 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                r12 = r12 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("J") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                r13 = r13 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                r21 = r21 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                r22 = r22 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("J") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                r23 = r23 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                r31 = r31 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                r32 = r32 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("J") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                r33 = r33 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("완료")  ){
                r41 = r41 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("진행")  ){
                r42 = r42 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("J") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                r43 = r43 +  Integer.parseInt((String)productProject.get("count"));
            }else{}
        }
    }


    row++;

    lr = new Label(0, row, "전략개발", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, p11 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p12 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p13 + "", cellFormat );
    s1.addCell(lr);

    lr = new Label(4, row, p11+p12 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, r11 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(6, row, r12 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, r13 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, r11+r12 + "", headerFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(0, row, "수주개발", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, p21 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p22 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p23 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p21+p22 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, r21 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(6, row, r22 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, r23 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, r21+r22 + "", headerFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(0, row, "연구개발", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, p31 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p32 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p33 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p31+p32 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, r31 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(6, row, r32 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, r33 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, r31+r32 + "", headerFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(0, row, "추가금형", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, p41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p43 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p41+p42 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, r41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(6, row, r42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, r43 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, r41+r42 + "", headerFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(0, row, "전체", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, p11+p21+p31+p41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p12+p22+p32+p42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p13+p23+p33+p43 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p11+p12+p21+p22+p31+p32+p41+p42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, r11+r21+r31+r41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(6, row, r12+r22+r32+r42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, r13+r23+r33+r43 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, r11+r12+r21+r22+r31+r32+r41+r42 + "", cellFormat);
    s1.addCell(lr);


    s1.mergeCells(0,0,11,0);
    s1.mergeCells(0,2,0,3);
    s1.mergeCells(1,2,4,2);
    s1.mergeCells(5,2,8,2);

    writebook.write();
    writebook.close();

    try {
        File file = new File(sFilePath+ "/" + sFileName);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Added by MJOH, 2011-04-18
        // 엑셀 다운로드 파일 DRM 암호화 적용
        //String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
        //file = E3PSDRMHelper.downloadExcel( file, sFileName, contentID, request.getRemoteAddr() );
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        FileInputStream fin = new FileInputStream(file);
        int ifilesize = (int)file.length();
        byte b[] = new byte[ifilesize];

        response.setContentLength(ifilesize);
        response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
        response.setHeader("Content-Disposition","attachment; filename="+sFileName+";");

        out.clear();
        out=pageContext.pushBody();

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
