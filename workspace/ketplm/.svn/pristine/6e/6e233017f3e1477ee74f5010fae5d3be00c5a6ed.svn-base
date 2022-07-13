<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<%@page import="e3ps.project.beans.TemplateProjectTreeNodeData"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="e3ps.project.beans.TemplateProjectTreeNode"%>
<%@page import="e3ps.project.beans.TaskHelper"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import="e3ps.common.util.StringUtil,
                                e3ps.common.drm.E3PSDRMHelper ,
                                    e3ps.common.util.DateUtil,
                                     e3ps.common.web.ParamUtil,
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

    Hashtable condition = (Hashtable)request.getAttribute("condition");
    ArrayList list = (ArrayList)request.getAttribute("list");
%>

<%
    //file path
    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    String sFilePath = sWtHome + "/codebase/jsp/project" ;

    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    String sFileName = "TASK Report_" +  ff.format(new Date()) + ".xls";

    //sheet 생성
    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("Task Report", 0);

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
    int days = DateUtil.getDaysFrom21Century( DateUtil.getCurrentDateString("d")  );
    int planend = 0;

    String planEDT = "";

    //문서제목
    Label lr = new Label(0, 0, "TASK Report");
    s1.addCell(lr);

    lr = new Label(0, row, "번호", headerFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "금형유형", headerFormat);
    s1.addCell(lr);

    lr = new Label(2, row, "금형구분", headerFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "Project No", headerFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "Project Name", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "진행현황", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, "TASK", headerFormat);
    s1.addCell(lr);

    lr = new Label(7, row, "주관부서", headerFormat);
    s1.addCell(lr);

    lr = new Label(8, row, "담당자", headerFormat);
    s1.addCell(lr);

    lr = new Label(9, row, "TASK완료", headerFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(9, row, "계획일", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, "실제일", headerFormat);
    s1.addCell(lr);

    lr = new Label(11, 2, "Issue 등록 여부", headerFormat);
    s1.addCell(lr);

    lr = new Label(12, 2, "TASK설명", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, 2, "최상위 Task", headerFormat);
    s1.addCell(lr);

    Hashtable project = null;
      if( list != null && list.size() > 0 ){
        for(int inx = 0 ; inx < list.size(); inx++){
            project = (Hashtable)list.get(inx);

            row++;
            rowCount--;

            String taskOid = "e3ps.project.E3PSTask:";
            String fullOid = taskOid + (String)project.get("taskOid");

            E3PSTask tt = (E3PSTask)CommonUtil.getObject(fullOid);

            String parentTaskName = "";
            boolean endparent = true;
            E3PSTask pt = (E3PSTask)tt.getParent();

            while(endparent){
                if(pt != null) {
                    parentTaskName =  pt.getTaskName();
                    try{
                        pt =  (E3PSTask)pt.getParent();
                    }catch(Exception e){
                    }
                }else{
                    endparent = false;
                }
            }
            if(parentTaskName.equals("")){
                parentTaskName =  StringUtil.checkNull((String)project.get("taskname"));
            }

            //번호
            lr = new Label(0, row, rowCount + "", cellFormat);
            s1.addCell(lr);

            //금형유형
            lr = new Label(1, row, StringUtil.checkNull((String)project.get("name")), cellFormat);
            s1.addCell(lr);

            //금형구분
            lr = new Label(2, row, StringUtil.checkNull((String)project.get("itemtype")), cellFormat);
            s1.addCell(lr);

            //Project No
            lr = new Label(3, row, StringUtil.checkNull((String)project.get("pjtno")), cellFormat);
            s1.addCell(lr);

            //Project Name
            lr = new Label(4, row, StringUtil.checkNull((String)project.get("pjtname")), cellFormat2);
            s1.addCell(lr);

            //진행현황
            if( "5".equals(StringUtil.checkNull((String)project.get("taskstate"))) ){
                lr = new Label(5, row, "완료", cellFormat);
                s1.addCell(lr);
            }else{
                planEDT = project.get("planenddate").toString().substring(0,10);
                planend = DateUtil.getDaysFrom21Century( planEDT  );

                if(days < planend){
                    lr = new Label(5, row, "진행", cellFormat);
                    s1.addCell(lr);
                }else{
                    lr = new Label(5, row, "지연", cellFormat);
                    s1.addCell(lr);
                }
            }

            //TASK
            lr = new Label(6, row, StringUtil.checkNull((String)project.get("taskname")), cellFormat);
            s1.addCell(lr);

            //TASK 주관부서
            lr = new Label(7, row, StringUtil.checkNull((String)project.get("departName")), cellFormat);
            s1.addCell(lr);

            // TASK 담당자
            lr = new Label(8, row, StringUtil.checkNull((String)project.get("userName")), cellFormat);
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

            //issue 등록여부
            lr = new Label(11, row, StringUtil.checkNull((String)project.get("issue_yn")), cellFormat);
            s1.addCell(lr);

            // TASK 설명
            lr = new Label(12, row, StringUtil.checkNull((String)project.get("taskdesc")), cellFormat);
            s1.addCell(lr);

            //최상위 Task
            lr = new Label(13, row, StringUtil.checkNull(parentTaskName), cellFormat);
            s1.addCell(lr);
        }
    }

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
