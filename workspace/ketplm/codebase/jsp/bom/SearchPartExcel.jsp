<%@page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Hashtable,
         java.util.ArrayList,
         java.util.Locale,
         java.text.SimpleDateFormat,
         java.io.File,
         java.util.Date,
         java.io.FileInputStream,
         jxl.Workbook,
         jxl.WorkbookSettings,
         jxl.write.WritableWorkbook,
         jxl.write.WritableSheet,
         jxl.write.WritableCellFormat,
         jxl.write.Label,
         jxl.format.Border,
         jxl.format.BorderLineStyle,
         jxl.format.Alignment,
         jxl.format.VerticalAlignment,
         wt.part.WTPart,
         e3ps.common.util.DateUtil,
         e3ps.common.util.KETObjectUtil,
         e3ps.bom.common.iba.IBAUtil,
         e3ps.common.drm.E3PSDRMHelper,
         e3ps.bom.service.KETPartHelper,
         e3ps.bom.service.KETBOMHeaderQueryBean"%>
<%@ page import="ext.ket.part.util.*" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
  Hashtable condition = (Hashtable)request.getAttribute("condition");
  ArrayList partList = (ArrayList)request.getAttribute("partList");
//System.out.println("@@@@@@@@@@ condition : " + condition);
//System.out.println("@@@@@@@@@@ partList : " + partList);

  String sWtHome = "";
  String sFilePath = "", sFileName = "";
  //file path
  sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
  sFilePath = sWtHome + "/codebase/jsp/bom" ;
  //file name
  SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
  sFileName = "SearchPartList_" +  ff.format(new Date()) + ".xls";
  //copy file
  File copyfile = new File(sFilePath+"/ExcelForm.xls") ;

  response.reset();
  response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
  response.setHeader("Content-Disposition","attachment; filename="+sFileName);
  ServletOutputStream objSos=response.getOutputStream();

  //WorkbookSettings ws = new WorkbookSettings();
  //ws.setLocale(new Locale("ko","KR"));
  //WritableWorkbook writebook = Workbook.createWorkbook(objSos, ws);

  WritableWorkbook writebook = Workbook.createWorkbook( new File(sFilePath+ "/" + sFileName) );
  WritableSheet s1 = writebook.createSheet("PartList", 1);

    // 컬럼, 로우 Count
    int intCurrRow = 0;
    int intColCount = 11;

    WritableCellFormat titleFormat = null;
    WritableCellFormat titleRightAlignFormat = null;

    // [타이틀 포맷]
    titleFormat = new WritableCellFormat();
    //titleFormat.setFont(new WritableFont(WritableFont.TAHOMA, 14, WritableFont.BOLD));
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

  int i= 4;
  int rowCount = 0;

    // 첫번째 행 세팅
    s1.setRowView(0, 20);
    s1.mergeCells(0, 0, intColCount - 1, 0);

    // 두번째 행 세팅 (Title Line)
    s1.addCell(new Label(0, 1, messageService.getString("e3ps.message.ket_message", "01581")/*부품검색 결과*/, titleFormat));
    s1.setRowView(1, 50);
    s1.mergeCells(0, 1, intColCount - 1, 1);

    // 세번째 행 세팅
    s1.setRowView(2, 20);
    s1.mergeCells(0, 2, intColCount - 1, 2);

    intCurrRow = 3;

  // 출력일자
    String strPrintDate = DateUtil.getCurrentDateString("d") +" " + DateUtil.getCurrentDateString("t");
    int intPrintDateRow = intCurrRow - 1;

    s1.addCell(new Label(0, intPrintDateRow, strPrintDate, titleRightAlignFormat));
    s1.setRowView(intPrintDateRow, 20);


  s1.addCell(new Label(0, intCurrRow, "No"));
  s1.addCell(new Label(1, intCurrRow, messageService.getString("e3ps.message.ket_message", "01604")/*부품타입*/));
  s1.addCell(new Label(2, intCurrRow, messageService.getString("e3ps.message.ket_message", "01569")/*부품 번호*/));
  s1.addCell(new Label(3, intCurrRow, messageService.getString("e3ps.message.ket_message", "01586")/*부품명*/));
  s1.addCell(new Label(4, intCurrRow, messageService.getString("e3ps.message.ket_message", "01481")/*버전*/));
  s1.addCell(new Label(5, intCurrRow, messageService.getString("e3ps.message.ket_message", "00771")/*결재상태*/));
  s1.addCell(new Label(6, intCurrRow, messageService.getString("e3ps.message.ket_message", "01119")/*기본단위*/));
  s1.addCell(new Label(7, intCurrRow, messageService.getString("e3ps.message.ket_message", "02419")/*자재그룹*/));
  s1.addCell(new Label(8, intCurrRow, messageService.getString("e3ps.message.ket_message", "02619")/*제품중량*/));
  s1.addCell(new Label(9, intCurrRow, messageService.getString("e3ps.message.ket_message", "00569")/*YAZAKI여부*/));
  s1.addCell(new Label(10, intCurrRow, messageService.getString("e3ps.message.ket_message", "00570")/*YAZAKI제번*/));

  Hashtable part = null;
  KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
  WTPart wt = null;
  String part_group ="";
  String part_weight ="";
  String part_isYazaki ="";
  String part_isYazakiNo ="";

    if( partList != null && partList.size() > 0 ){
    for(int inx = 0 ; inx < partList.size(); inx++){
      part = (Hashtable)partList.get(inx);
      wt = (WTPart) KETObjectUtil.getObject("wt.part.WTPart:" + (String)part.get("oid"));

      part_group = null; // 사용안함 - 1차고도화 // PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpPartGroup);
      if ( part_group == null || (part_group != null && part_group.equals("")) ) {
        part_group = "-";
      }
      part_weight = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpNetWeight);
      if ( part_weight == null || (part_weight != null && part_weight.equals("")) ) {
        part_weight = "-";
      }
      part_isYazaki = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpIsYazaki);
      if ( part_isYazaki == null || (part_isYazaki != null && part_isYazaki.equals(""))) {
        part_isYazaki = "-";
      }
      part_isYazakiNo = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpYazakiNo);
      if ( part_isYazakiNo == null || (part_isYazakiNo != null && part_isYazakiNo.equals(""))) {
        part_isYazakiNo = "-";
      }

      //No
      rowCount++;
      s1.addCell(new Label(0, i, rowCount + ""));

      //부품타입
      s1.addCell(new Label(1, i, (String)part.get("type")));

      //부품번호
      s1.addCell(new Label(2, i, (String)part.get("number")));

      //부품명
      s1.addCell(new Label(3, i, (String)part.get("name")));

      //버전
      s1.addCell(new Label(4, i, (String)part.get("version")));

      //결재상태
      s1.addCell(new Label(5, i, (String)part.get("stateKr") ));

      //기본단위
      s1.addCell(new Label(6, i, bean.getUnitDisplayValue((String)part.get("unit"))));

      //자재그룹
      s1.addCell(new Label(7, i, part_group ));

      //제품중량
      s1.addCell(new Label(8, i, part_weight ));

      //YAZAKI여부
      s1.addCell(new Label(9, i, part_isYazaki ));

      //YAZAKI제번
      s1.addCell(new Label(10, i, part_isYazakiNo ));

      i++;
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
  catch( Exception e )
  {
     e.printStackTrace();
     throw e;
  }
%>
