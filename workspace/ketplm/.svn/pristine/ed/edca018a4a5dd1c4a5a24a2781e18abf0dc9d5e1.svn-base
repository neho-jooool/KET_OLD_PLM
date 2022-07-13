<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import="java.util.*,java.text.SimpleDateFormat"%>
<%@page import="jxl.write.WritableWorkbook"%>
<%@page import="java.io.File"%>
<%@page import="jxl.*,jxl.write.*"%>
<%@page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*"%>
<%@page import="e3ps.common.query.*,e3ps.common.util.*,e3ps.bom.*"%>
<%@page import="e3ps.bom.common.util.XlsCellFormat"%>
<%@page import = "java.util.ArrayList,
                  java.util.Hashtable,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.ParamUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<%
    Hashtable condition = (Hashtable)request.getAttribute("condition");
    Kogger.debug("----------------------condition"+condition);
    ArrayList list = (ArrayList)request.getAttribute("list");

    XlsCellFormat xlsFormat = new XlsCellFormat();

    String sWtHome = "";
    String sFilePath = "";
    String sFileName = "";

    //file path
    sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    sFilePath = sWtHome + "/codebase/jsp/bom" ;

    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMdd");
    sFileName = "MoldChange_" +  ff.format(new Date()) + ".xls";

    //copy file
    File copyfile = new File(sFilePath+"/ExcelForm.xls") ;

    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
    response.setHeader("Content-Disposition","attachment; filename="+sFileName);

    WorkbookSettings ws = new WorkbookSettings();
    ws.setLocale(new Locale("ko","KR"));
    WritableWorkbook writebook = Workbook.createWorkbook(response.getOutputStream(), ws);
    WritableSheet s1 = writebook.createSheet("MoldChangePlanList", 1);
    s1.setColumnView( 0, 5);
    s1.setColumnView( 1, 15);
    s1.setColumnView( 2, 15);
    s1.setColumnView( 3, 40);
    s1.setColumnView( 4, 5);
    s1.setColumnView( 5, 10);

    int intCurrRow = 0;
    int intColCount = 6;
    //int intRowCount = result.size();

    WritableCellFormat titleFormat = null;
    WritableCellFormat titleRightAlignFormat = null;

    // [타이틀 포맷]
    titleFormat = new WritableCellFormat();
    titleFormat.setAlignment(Alignment.CENTRE); // 셀 가로정열(좌/우/가운데설정가능)
    titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 세로정렬(상단/중단/하단설정가능)
    titleFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
    titleFormat.setWrap(true); // 한 cell에 여러줄 표현

    // [타이틀 오른쪽 정렬 포맷]
    titleRightAlignFormat = new WritableCellFormat();
    //titleRightAlignFormat.setFont(new WritableFont(WritableFont.TAHOMA, 10));
    titleRightAlignFormat.setAlignment(Alignment.RIGHT);
    titleRightAlignFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
    titleRightAlignFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
    titleRightAlignFormat.setWrap(true);

    intCurrRow = 0;

    s1.addCell(new Label(0, intCurrRow, "No", xlsFormat.getHeadFormat()));
    s1.addCell(new Label(1, intCurrRow, messageService.getString("e3ps.message.ket_message", "01077")/*금형부품코드*/, xlsFormat.getHeadFormat()));
    s1.addCell(new Label(2, intCurrRow, messageService.getString("e3ps.message.ket_message", "01586")/*부품명*/, xlsFormat.getHeadFormat()));
    s1.addCell(new Label(3, intCurrRow, messageService.getString("e3ps.message.ket_message", "01498")/*변경 사유*/, xlsFormat.getHeadFormat()));
    s1.addCell(new Label(4, intCurrRow, messageService.getString("e3ps.message.ket_message", "01524")/*변경횟수*/, xlsFormat.getHeadFormat()));
    s1.addCell(new Label(5, intCurrRow, messageService.getString("e3ps.message.ket_message", "01521")/*변경일자*/, xlsFormat.getHeadFormat()));

    int inx = 0;
    Hashtable moldChange = null;
    if( list !=null && list.size() > 0 ){
          for(int i = 0 ; i < list.size(); i++){
              moldChange = (Hashtable)list.get(i);
              inx = i+1;
              s1.addCell(new Label(0, inx, moldChange.get("row_id") + "",xlsFormat.getCommonTextFormat()));
              s1.addCell(new Label(1, inx, moldChange.get("itemcode") + "",xlsFormat.getCommonTextFormat()));
              s1.addCell(new Label(2, inx, moldChange.get("itemname") + "",xlsFormat.getCommonTextFormat()));
              s1.addCell(new Label(3, inx, moldChange.get("modesc") + "",xlsFormat.getCommonTextFormat()));
              s1.addCell(new Label(4, inx, moldChange.get("changeno") + "",xlsFormat.getCommonTextFormat()));
              s1.addCell(new Label(5, inx, moldChange.get("changedate") + "",xlsFormat.getCommonTextFormat()));
          }
    }
    writebook.write();
      writebook.close();
%>
