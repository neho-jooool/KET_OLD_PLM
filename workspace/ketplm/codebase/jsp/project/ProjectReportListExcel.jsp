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
    String sFilePath = sWtHome + "/codebase/jsp/project" ;

    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    String sFileName = "Project Report_" +  ff.format(new Date()) + ".xls";

    //sheet 생성
    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("Project Report", 0);

  //셀의 스타일을 지정
    WritableCellFormat cellFormat = new WritableCellFormat();
  cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);
  cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
  cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

  WritableCellFormat cellFormat2 = new WritableCellFormat();
  cellFormat2.setBorder(Border.ALL , BorderLineStyle.THIN);
  cellFormat2.setAlignment(jxl.format.Alignment.LEFT);
  cellFormat2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

  WritableCellFormat cellFormat3 = new WritableCellFormat();
  cellFormat3.setBorder(Border.ALL , BorderLineStyle.THIN);
  cellFormat3.setAlignment(jxl.format.Alignment.RIGHT);
  cellFormat3.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

  WritableCellFormat headerFormat = new WritableCellFormat();
  headerFormat.setBorder(Border.ALL , BorderLineStyle.THIN);
  headerFormat.setBackground(jxl.format.Colour.LIGHT_TURQUOISE);
  headerFormat.setAlignment(jxl.format.Alignment.CENTRE);
  headerFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    int row = 2;
    int rowCount = list.size()+1;

    //문서제목
    Label lr = new Label(0, 0, "Project Report");
    s1.addCell(lr);

    lr = new Label(0, row, "번호", headerFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "개발구분", headerFormat);
    s1.addCell(lr);

    lr = new Label(2, row, "Project No", headerFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "Project Name", headerFormat);
    s1.addCell(lr);

  if( condition.get("page") != null && (((String)condition.get("cmd")).equals("reportList4Excel") || ((String)condition.get("cmd")).equals("reportList5Excel")) ) {
        lr = new Label(4, row, "금형구분", headerFormat);
    }else{
        lr = new Label(4, row, "제품구분", headerFormat);
    }
    s1.addCell(lr);

    if( condition.get("page") != null && (((String)condition.get("cmd")).equals("reportList1Excel") || ((String)condition.get("cmd")).equals("reportList2Excel")) ) {
        lr = new Label(5, row, "대표차종", headerFormat);
        s1.addCell(lr);

        lr = new Label(6, row, "개발구분", headerFormat);
        s1.addCell(lr);

        lr = new Label(7, row, "고객", headerFormat);
        s1.addCell(lr);

        lr = new Label(8, row, "개발시작일", headerFormat);
        s1.addCell(lr);

        lr = new Label(9, row, "개발완료", headerFormat);
        s1.addCell(lr);

        lr = new Label(11, row, "진행일", headerFormat);
        s1.addCell(lr);

        lr = new Label(12, row, "주관부서", headerFormat);
        s1.addCell(lr);

        lr = new Label(13, row, "PM", headerFormat);
        s1.addCell(lr);

        lr = new Label(14, row, "비용(단위:천원)", headerFormat);
        s1.addCell(lr);

        lr = new Label(16, row, "진행현황", headerFormat);
        s1.addCell(lr);

        row++;

        lr = new Label(9, row, "계획일", headerFormat);
        s1.addCell(lr);

        lr = new Label(10, row, "실제일", headerFormat);
        s1.addCell(lr);

        lr = new Label(14, row, "예산", headerFormat);
        s1.addCell(lr);

        lr = new Label(15, row, "실적", headerFormat);
        s1.addCell(lr);
    }else{
        lr = new Label(5, row, "고객", headerFormat);
        s1.addCell(lr);

        lr = new Label(6, row, "개발시작일", headerFormat);
        s1.addCell(lr);

        lr = new Label(7, row, "개발완료", headerFormat);
        s1.addCell(lr);

        lr = new Label(9, row, "진행일", headerFormat);
        s1.addCell(lr);

        lr = new Label(10, row, "주관부서", headerFormat);
        s1.addCell(lr);

        lr = new Label(11, row, "PM", headerFormat);
        s1.addCell(lr);

        lr = new Label(12, row, "비용(단위:천원)", headerFormat);
        s1.addCell(lr);

        lr = new Label(14, row, "진행현황", headerFormat);
        s1.addCell(lr);

        row++;

        lr = new Label(7, row, "계획일", headerFormat);
        s1.addCell(lr);

        lr = new Label(8, row, "실제일", headerFormat);
        s1.addCell(lr);

        lr = new Label(12, row, "예산", headerFormat);
        s1.addCell(lr);

        lr = new Label(13, row, "실적", headerFormat);
        s1.addCell(lr);
    }

    Hashtable project = null;
  if( list != null && list.size() > 0 ){
        for(int inx = 0 ; inx < list.size(); inx++){
            project = (Hashtable)list.get(inx);

            row++;
            rowCount--;

            //번호
            lr = new Label(0, row, rowCount + "", cellFormat);
            s1.addCell(lr);

            //개발구분
            lr = new Label(1, row, StringUtil.checkNull((String)project.get("devType")), cellFormat);
            s1.addCell(lr);

            //Project No
            lr = new Label(2, row, StringUtil.checkNull((String)project.get("pjtno")), cellFormat);
            s1.addCell(lr);

            //Project Name
            lr = new Label(3, row, StringUtil.checkNull((String)project.get("pjtname")), cellFormat2);
            s1.addCell(lr);

            //제품구분
            lr = new Label(4, row, StringUtil.checkNull((String)project.get("prodType")), cellFormat);
            s1.addCell(lr);

            if( condition.get("page") != null && (((String)condition.get("cmd")).equals("reportList1Excel") || ((String)condition.get("cmd")).equals("reportList2Excel")) ) {
                //대표차종
                lr = new Label(5, row, StringUtil.checkNull((String)project.get("oemname")), cellFormat);
                s1.addCell(lr);

                //개발구분
                lr = new Label(6, row, StringUtil.checkNull((String)project.get("designType")), cellFormat);
                s1.addCell(lr);

                //고객
                lr = new Label(7, row, StringUtil.checkNull((String)project.get("customername")), cellFormat);
                s1.addCell(lr);

                //시작일
                if (project.get("planstartdate") != null && ((String)project.get("planstartdate")).length() > 10){
                  lr = new Label(8, row, ((String)project.get("planstartdate")).substring(0, 10), cellFormat);
                }else{
                    lr = new Label(8, row, StringUtil.checkNull((String)project.get("planstartdate")), cellFormat);
                }
                s1.addCell(lr);

                //완료일(계획)
                if (project.get("planenddate") != null && ((String)project.get("planenddate")).length() > 10){
                  lr = new Label(9, row, ((String)project.get("planenddate")).substring(0, 10), cellFormat);
                }else{
                    lr = new Label(9, row, StringUtil.checkNull((String)project.get("planenddate")), cellFormat);
                }
                s1.addCell(lr);

                //완료일
                if (project.get("execenddate") != null && ((String)project.get("execenddate")).length() > 10){
                  lr = new Label(10, row, ((String)project.get("execenddate")).substring(0, 10), cellFormat);
                }else{
                    lr = new Label(10, row, StringUtil.checkNull((String)project.get("execenddate")), cellFormat);
                }
                s1.addCell(lr);

                //경과일
                lr = new Label(11, row, StringUtil.checkNull((String)project.get("term")) + "일", cellFormat);
                s1.addCell(lr);

                //주관부서
                lr = new Label(12, row, StringUtil.checkNull((String)project.get("dept")), cellFormat);
                s1.addCell(lr);

                //PM
                lr = new Label(13, row, StringUtil.checkNull((String)project.get("pmname")), cellFormat);
                s1.addCell(lr);

                //예산
                lr = new Label(14, row, StringUtil.checkNull((String)project.get("budget")), cellFormat3);
                s1.addCell(lr);

                //실적
                lr = new Label(15, row, StringUtil.checkNull((String)project.get("execution")), cellFormat3);
                s1.addCell(lr);

                //진행현황
                if (project.get("statestate") != null && ((String)(project.get("statestate"))).equals("COMPLETED")) {
                    lr = new Label(16, row, "완료", cellFormat);
                }else if (project.get("statestate") != null && ((String)(project.get("statestate"))).equals("PROGRESS")) {
                    if (project.get("pjtstate") != null && ((String)(project.get("pjtstate"))).equals("4")) {
                  lr = new Label(16, row, "지연", cellFormat);
                }else if (project.get("pjtstate") != null && ((String)(project.get("pjtstate"))).equals("3")) {
                lr = new Label(16, row, "예상지연", cellFormat);
                  }else {
                    lr = new Label(16, row, "정상진행", cellFormat);
                  }
                }else {
                    lr = new Label(16, row, "", cellFormat);
                  }
                s1.addCell(lr);
            }else{
                //고객
                lr = new Label(5, row, StringUtil.checkNull((String)project.get("customername")), cellFormat);
                s1.addCell(lr);

                //시작일
                if (project.get("planstartdate") != null && ((String)project.get("planstartdate")).length() > 10){
                  lr = new Label(6, row, ((String)project.get("planstartdate")).substring(0, 10), cellFormat);
                }else{
                    lr = new Label(6, row, StringUtil.checkNull((String)project.get("planstartdate")), cellFormat);
                }
                s1.addCell(lr);

                //완료일(계획)
                if (project.get("planenddate") != null && ((String)project.get("planenddate")).length() > 10){
                  lr = new Label(7, row, ((String)project.get("planenddate")).substring(0, 10), cellFormat);
                }else{
                    lr = new Label(7, row, StringUtil.checkNull((String)project.get("planenddate")), cellFormat);
                }
                s1.addCell(lr);

                //완료일
                if (project.get("execenddate") != null && ((String)project.get("execenddate")).length() > 10){
                  lr = new Label(8, row, ((String)project.get("execenddate")).substring(0, 10), cellFormat);
                }else{
                    lr = new Label(8, row, StringUtil.checkNull((String)project.get("execenddate")), cellFormat);
                }
                s1.addCell(lr);

                //경과일
                lr = new Label(9, row, StringUtil.checkNull((String)project.get("term")) + "일", cellFormat);
                s1.addCell(lr);

                //주관부서
                lr = new Label(10, row, StringUtil.checkNull((String)project.get("dept")), cellFormat);
                s1.addCell(lr);

                //PM
                lr = new Label(11, row, StringUtil.checkNull((String)project.get("pmname")), cellFormat);
                s1.addCell(lr);

                //예산
                lr = new Label(12, row, StringUtil.checkNull((String)project.get("budget")), cellFormat3);
                s1.addCell(lr);

                //실적
                lr = new Label(13, row, StringUtil.checkNull((String)project.get("execution")), cellFormat3);
                s1.addCell(lr);

                //진행현황
                if (project.get("statestate") != null && ((String)(project.get("statestate"))).equals("COMPLETED")) {
                    lr = new Label(14, row, "완료", cellFormat);
                }else if (project.get("statestate") != null && ((String)(project.get("statestate"))).equals("PROGRESS")) {
                    if (project.get("pjtstate") != null && ((String)(project.get("pjtstate"))).equals("4")) {
                  lr = new Label(14, row, "지연", cellFormat);
                }else if (project.get("pjtstate") != null && ((String)(project.get("pjtstate"))).equals("3")) {
                lr = new Label(14, row, "예상지연", cellFormat);
                  }else {
                    lr = new Label(14, row, "정상진행", cellFormat);
                  }
                }else {
                    lr = new Label(14, row, "", cellFormat);
                  }
                s1.addCell(lr);
            }
        }
    }

      if( condition.get("page") != null && (((String)condition.get("cmd")).equals("reportList1Excel") || ((String)condition.get("cmd")).equals("reportList2Excel")) ) {
        s1.mergeCells(0,2,0,3);
        s1.mergeCells(1,2,1,3);
        s1.mergeCells(2,2,2,3);
        s1.mergeCells(3,2,3,3);
        s1.mergeCells(4,2,4,3);
        s1.mergeCells(5,2,5,3);
        s1.mergeCells(6,2,6,3);
        s1.mergeCells(7,2,7,3);
        s1.mergeCells(8,2,8,3);
        s1.mergeCells(9,2,10,2);
        s1.mergeCells(11,2,11,3);
        s1.mergeCells(12,2,12,3);
        s1.mergeCells(13,2,13,3);
        s1.mergeCells(14,2,15,2);
        s1.mergeCells(16,2,16,3);
      }else{
        s1.mergeCells(0,2,0,3);
        s1.mergeCells(1,2,1,3);
        s1.mergeCells(2,2,2,3);
        s1.mergeCells(3,2,3,3);
        s1.mergeCells(4,2,4,3);
        s1.mergeCells(5,2,5,3);
        s1.mergeCells(6,2,6,3);
        s1.mergeCells(7,2,8,2);
        s1.mergeCells(9,2,9,3);
        s1.mergeCells(10,2,10,3);
        s1.mergeCells(11,2,11,3);
        s1.mergeCells(12,2,13,2);
        s1.mergeCells(14,2,14,3);
      }

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
