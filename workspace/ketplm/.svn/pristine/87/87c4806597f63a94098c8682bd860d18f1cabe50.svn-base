<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import="e3ps.common.drm.E3PSDRMHelper ,
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
    ArrayList list = (ArrayList)request.getAttribute("moldList");
    ArrayList addlist = (ArrayList)request.getAttribute("addMoldList");
    Hashtable condition = (Hashtable)request.getAttribute("condition");

    //file path
    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    String sFilePath = sWtHome + "/codebase/jsp/project" ;

    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    String sFileName = "MoldReportList_" +  ff.format(new Date()) + ".xls";

    //sheet 생성
    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("금형제작현황", 0);

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
    Label lr = new Label(0, 0, "금형제작현황");
    s1.addCell(lr);

    lr = new Label(0, row, "구분", headerFormat);
    s1.addCell(lr);

  lr = new Label(2, row, "전체", headerFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "Press", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, "Mold", headerFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(3, row, "신규", headerFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "이월", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "전체", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, "완료", headerFormat);
    s1.addCell(lr);

    lr = new Label(7, row, "진행", headerFormat);
    s1.addCell(lr);

    lr = new Label(8, row, "중단", headerFormat);
    s1.addCell(lr);

    lr = new Label(9, row, "지연", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, "신규", headerFormat);
    s1.addCell(lr);

    lr = new Label(11, row, "이월", headerFormat);
    s1.addCell(lr);

    lr = new Label(12, row, "전체", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, "완료", headerFormat);
    s1.addCell(lr);

    lr = new Label(14, row, "진행", headerFormat);
    s1.addCell(lr);

    lr = new Label(15, row, "중단", headerFormat);
    s1.addCell(lr);

    lr = new Label(16, row, "지연", headerFormat);
    s1.addCell(lr);

    Hashtable moldProject = null;
    int sp11 = 0;
    int sp12 = 0;
    int sp21 = 0;
    int sp22 = 0;
    int sp31 = 0;
    int sp32 = 0;
    int sp41 = 0;
    int sp42 = 0;
    int sp51 = 0;
    int sp52 = 0;
    int sp61 = 0;
    int sp62 = 0;
    int sp71 = 0;
    int sp72 = 0;
    int sp81 = 0;
    int sp82 = 0;

    int sm11 = 0;
    int sm12 = 0;
    int sm21 = 0;
    int sm22 = 0;
    int sm31 = 0;
    int sm32 = 0;
    int sm41 = 0;
    int sm42 = 0;
    int sm51 = 0;
    int sm52 = 0;
    int sm61 = 0;
    int sm62 = 0;
    int sm71 = 0;
    int sm72 = 0;
    int sm81 = 0;
    int sm82 = 0;

    int p11 = 0;
    int p12 = 0;
    int p13 = 0;
    int p14 = 0;
    int p21 = 0;
    int p22 = 0;
    int p23 = 0;
    int p24 = 0;
    int p31 = 0;
    int p32 = 0;
    int p33 = 0;
    int p34 = 0;
    int p41 = 0;
    int p42 = 0;
    int p43 = 0;
    int p44 = 0;
    int p51 = 0;
    int p52 = 0;
    int p53 = 0;
    int p54 = 0;
    int p61 = 0;
    int p62 = 0;
    int p63 = 0;
    int p64 = 0;
    int p71 = 0;
    int p72 = 0;
    int p73 = 0;
    int p74 = 0;
    int p81 = 0;
    int p82 = 0;
    int p83 = 0;
    int p84 = 0;

    int m11 = 0;
    int m12 = 0;
    int m13 = 0;
    int m14 = 0;
    int m21 = 0;
    int m22 = 0;
    int m23 = 0;
    int m24 = 0;
    int m31 = 0;
    int m32 = 0;
    int m33 = 0;
    int m34 = 0;
    int m41 = 0;
    int m42 = 0;
    int m43 = 0;
    int m44 = 0;
    int m51 = 0;
    int m52 = 0;
    int m53 = 0;
    int m54 = 0;
    int m61 = 0;
    int m62 = 0;
    int m63 = 0;
    int m64 = 0;
    int m71 = 0;
    int m72 = 0;
    int m73 = 0;
    int m74 = 0;
    int m81 = 0;
    int m82 = 0;
    int m83 = 0;
    int m84 = 0;

    if( list != null && list.size() > 0 ){
        for(int inx = 0 ; inx < list.size(); inx++){
            moldProject = (Hashtable)list.get(inx);
            if( ((String)moldProject.get("itemType")).equals("Press")
                 && ((String)moldProject.get("state")).equals("COMPLETED")
                 && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p11 = p11 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p12 = p12 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p14 = p14 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p13 = p13 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p21 = p21 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p22 = p22 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p24 = p24 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p23 = p23 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                        && ((String)moldProject.get("state")).equals("COMPLETED")
                        && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                        && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p31 = p31 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p32 = p32 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p34 = p34 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p33 = p33 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p41 = p41 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p42 = p42 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p44 = p44 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p43 = p43 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p51 = p51 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p52 = p52 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p54 = p54 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p53 = p53 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p61 = p61 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p62 = p62 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p64 = p64 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p63 = p63 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p71 = p71 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p72 = p72 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p74 = p74 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p73 = p73 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p81 = p81 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p82 = p82 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p84 = p84 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p83 = p83 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                        && ((String)moldProject.get("state")).equals("COMPLETED")
                        && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                        && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m11 = m11 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m12 = m12 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m14 = m14 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m13 = m13 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m21 = m21 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m22 = m22 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m24 = m24 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m23 = m23 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                        && ((String)moldProject.get("state")).equals("COMPLETED")
                        && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                        && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m31 = m31 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m32 = m32 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m34 = m34 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m33 = m33 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m41 = m41 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m42 = m42 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m44 = m44 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m43 = m43 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m51 = m51 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m52 = m52 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m54 = m54 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m53 = m53 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m61 = m61 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m62 = m62 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m64 = m64 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m63 = m63 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m71 = m71 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m72 = m72 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m74 = m74 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m73 = m73 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m81 = m81 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m82 = m82 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m84 = m84 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m83 = m83 + Integer.parseInt((String)moldProject.get("count"));
          }
        }
  }

  if( addlist != null && addlist.size() > 0 ){
        for(int inx = 0 ; inx < addlist.size(); inx++){
            moldProject = (Hashtable)addlist.get(inx);
            if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sp11 = sp11 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sp12 = sp12 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sp21 = sp21 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sp22 = sp22 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sp31 = sp31 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sp32 = sp32 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sp41 = sp41 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sp42 = sp42 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sp51 = sp51 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sp52 = sp52 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sp61 = sp61 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sp62 = sp62 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sp71 = sp71 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sp72 = sp72 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sp81 = sp81 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sp82 = sp82 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sm11 = sm11 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sm12 = sm12 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sm21 = sm21 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sm22 = sm22 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sm31 = sm31 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sm32 = sm32 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sm41 = sm41 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sm42 = sm42 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sm51 = sm51 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sm52 = sm52 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sm61 = sm61 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sm62 = sm62 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sm71 = sm71 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sm72 = sm72 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sm81 = sm81 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sm82 = sm82 + Integer.parseInt((String)moldProject.get("count"));
          }
        }
  }

  row++;

    lr = new Label(0, row, "사내제작", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "시작금형", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp11+sp12+sm11+sm12 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp11 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp12 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp11+sp12 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p11 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p12 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p13 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p14 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm11 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm12 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm11+sm12 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m11 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m12 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m13 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m14 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "시작Mo/Fa", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp21+sp22+sm21+sm22 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp21 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp22 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp21+sp22 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p21 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p22 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p23 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p24 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm21 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm22 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm21+sm22 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m21 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m22 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m23 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m24 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산금형", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp31+sp32+sm31+sm32 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp31 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp32 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp31+sp32 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p31 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p32 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p33 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p34 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm31 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm32 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm31+sm32 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m31 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m32 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m33 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m34 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산Mo/Fa", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp41+sp42+sm41+sm42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp41+sp42 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p43 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p44 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm41+sm42 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m43 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m44 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "소계", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp11+sp12+sm11+sm12+sp21+sp22+sm21+sm22+sp31+sp32+sm31+sm32+sp41+sp42+sm41+sm42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp11+sp21+sp31+sp41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp12+sp22+sp32+sp42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp11+sp12+sp21+sp22+sp31+sp32+sp41+sp42 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p11+p21+p31+p41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p12+p22+p32+p42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p13+p23+p33+p43 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p14+p24+p34+p44 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm11+sm21+sm31+sm41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm12+sm22+sm32+sm42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm11+sm12+sm21+sm22+sm31+sm32+sm41+sm42 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m11+m21+m31+m41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m12+m22+m32+m42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m13+m23+m33+m43 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m14+m24+m34+m44 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(0, row, "외주제작", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "시작금형", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp51+sp52+sm51+sm52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp51+sp52 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p53 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p54 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm51+sm52 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m53 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m54 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "시작Mo/Fa", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp61+sp62+sm61+sm62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp61+sp62 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p63 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p64 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm61+sm62 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m63 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m64 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산금형" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp71+sp72+sm71+sm72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp71+sp72 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p73 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p74 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm71+sm72 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m73 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m74 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산Mo/Fa" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp81+sp82+sm81+sm82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp81+sp82 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p83 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm81+sm82 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m83 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m84 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "소계" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp51+sp52+sm51+sm52+sp61+sp62+sm61+sm62+sp71+sp72+sm71+sm72+sp81+sp82+sm81+sm82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp51+sp61+sp71+sp81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp52+sp62+sp72+sp82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp51+sp52+sp61+sp62+sp71+sp72+sp81+sp82 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p51+p61+p71+p81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p52+p62+p72+p82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p53+p63+p73+p83 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p54+p64+p74+p84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm51+sm61+sm71+sm81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm52+sm62+sm72+sm82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm51+sm52+sm61+sm62+sm71+sm72+sm81+sm82 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m51+m61+m71+m81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m52+m62+m72+m82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m53+m63+m73+m83 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m54+m64+m74+m84 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(0, row, "전체" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "시작금형" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp11+sp12+sm11+sm12+sp51+sp52+sm51+sm52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp11+sp51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp12+sp52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp11+sp12+sp51+sp52 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p11+p51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p12+p52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p13+p53 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p14+p54 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm11+sm51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm12+sm52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm11+sm12+sm51+sm52 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m11+m51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m12+m52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m13+m53 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m14+m54 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "시작Mo/Fa" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp21+sp22+sm21+sm22+sp61+sp62+sm61+sm62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp21+sp61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp22+sp62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp21+sp22+sp61+sp62 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p21+p61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p22+p62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p23+p63 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p24+p64 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm21+sm61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm22+sm62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm21+sm22+sm61+sm62 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m21+m61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m22+m62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m23+m63 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m24+m64 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산금형" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp31+sp32+sm31+sm32+sp71+sp72+sm71+sm72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp31+sp71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp32+sp72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp31+sp32+sp71+sp72 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p31+p71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p32+p72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p33+p73 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p34+p74 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm31+sm71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm32+sm72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm31+sm32+sm71+sm72 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m31+m71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m32+m72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m33+m73 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m34+m74 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산Mo/Fa" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp41+sp42+sm41+sm42+sp81+sp82+sm81+sm82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp41+sp81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp42+sp82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp41+sp42+sp81+sp82 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p41+p81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p42+p82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p43+p83 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p44+p84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm41+sm81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm42+sm82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm41+sm42+sm81+sm82 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m41+m81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m42+m82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m43+m83 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m44+m84 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "전체" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, sp11+sp12+sm11+sm12+sp21+sp22+sm21+sm22+sp31+sp32+sm31+sm32+sp41+sp42+sm41+sm42+sp51+sp52+sm51+sm52+sp61+sp62+sm61+sm62+sp71+sp72+sm71+sm72+sp81+sp82+sm81+sm82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, sp11+sp21+sp31+sp41+sp51+sp61+sp71+sp81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, sp12+sp22+sp32+sp42+sp52+sp62+sp72+sp82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, sp11+sp12+sp21+sp22+sp31+sp32+sp41+sp42+sp51+sp52+sp61+sp62+sp71+sp72+sp81+sp82 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p11+p21+p31+p41+p51+p61+p71+p81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, p12+p22+p32+p42+p52+p62+p72+p82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, p13+p23+p33+p43+p53+p63+p73+p83 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, p14+p24+p34+p44+p54+p64+p74+p84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sm11+sm21+sm31+sm41+sm51+sm61+sm71+sm81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sm12+sm22+sm32+sm42+sm52+sm62+sm72+sm82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, sm11+sm12+sm21+sm22+sm31+sm32+sm41+sm42+sm51+sm52+sm61+sm62+sm71+sm72+sm81+sm82 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, row, m11+m21+m31+m41+m51+m61+m71+m81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, m12+m22+m32+m42+m52+m62+m72+m82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, m13+m23+m33+m43+m53+m63+m73+m83 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, m14+m24+m34+m44+m54+m64+m74+m84 + "", cellFormat);
    s1.addCell(lr);

    s1.mergeCells(0,0,13,0);
    s1.mergeCells(0,2,1,3);
    s1.mergeCells(2,2,2,3);
    s1.mergeCells(3,2,9,2);
    s1.mergeCells(10,2,16,2);
    s1.mergeCells(0,4,0,8);
    s1.mergeCells(0,9,0,13);
    s1.mergeCells(0,14,0,18);

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
