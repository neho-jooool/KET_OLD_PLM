<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import="e3ps.common.drm.E3PSDRMHelper ,
                                e3ps.common.web.ParamUtil,
                                 e3ps.common.util.StringUtil,
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

    ArrayList list = (ArrayList)request.getAttribute("moldList");
    ArrayList addlist = (ArrayList)request.getAttribute("addMoldList");

    //file path
    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    String sFilePath = sWtHome + "/codebase/jsp/project" ;

    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    String sFileName = "MoldTaskList_" +  ff.format(new Date()) + ".xls";

    //sheet 생성
    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("금형Task현황", 0);

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
    Label lr = new Label(0, 0, "금형Task현황");
    s1.addCell(lr);

    lr = new Label(0, row, "구분", headerFormat);
    s1.addCell(lr);

    lr = new Label(2, row, "전체", headerFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "Press", headerFormat);
    s1.addCell(lr);

    lr = new Label(7, row, "Mold", headerFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(3, row, "완료", headerFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "진행", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "지연", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, "전체", headerFormat);
    s1.addCell(lr);

    lr = new Label(7, row, "완료", headerFormat);
    s1.addCell(lr);

    lr = new Label(8, row, "진행", headerFormat);
    s1.addCell(lr);

    lr = new Label(9, row, "지연", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, "전체", headerFormat);
    s1.addCell(lr);

    Hashtable moldProject = null;
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

            if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "완료".equals((String)moldProject.get("taskstate")) ) {
                p11 = p11 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "진행".equals((String)moldProject.get("taskstate")) ) {
                p12 = p12 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p13 = p13 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "완료".equals((String)moldProject.get("taskstate")) ) {
                p21 = p21 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "진행".equals((String)moldProject.get("taskstate"))   ) {
                p22 = p22 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p23 = p23 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "완료".equals((String)moldProject.get("taskstate")) ) {
                p31 = p31 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "진행".equals((String)moldProject.get("taskstate")) ) {
                p32 = p32 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p33 = p33 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p41 = p41 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "진행".equals((String)moldProject.get("taskstate")) ) {
                p42 = p42 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p43 = p43 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p51 = p51 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "진행".equals((String)moldProject.get("taskstate")) ) {
                p52 = p52 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p53 = p53 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p61 = p61 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "진행".equals((String)moldProject.get("taskstate")) ) {
                p62 = p62 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p63 = p63 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p71 = p71 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "진행".equals((String)moldProject.get("taskstate")) ) {
                p72 = p72 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p73 = p73 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p81 = p81 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "진행".equals((String)moldProject.get("taskstate")) ) {
                p82 = p82 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p83 = p83 + Integer.parseInt((String)moldProject.get("count"));
            }
            else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m11 = m11 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "진행".equals((String)moldProject.get("taskstate")) ) {
                m12 = m12 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m13 = m13 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m21 = m21 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "진행".equals((String)moldProject.get("taskstate")) ) {
                m22 = m22 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "지연".equals((String)moldProject.get("taskstate"))  && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m23 = m23 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산")  && "완료".equals((String)moldProject.get("taskstate")) ) {
                m31 = m31 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "진행".equals((String)moldProject.get("taskstate")) ) {
                m32 = m32 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m33 = m33 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "완료".equals((String)moldProject.get("taskstate")) ) {
                  m41 = m41 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "진행".equals((String)moldProject.get("taskstate")) ) {
                m42 = m42 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") )&& "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m43 = m43 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m51 = m51 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "진행".equals((String)moldProject.get("taskstate")) ) {
                m52 = m52 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m53 = m53 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m61 = m61 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "진행".equals((String)moldProject.get("taskstate")) ) {
                m62 = m62 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") ) && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m63 = m63 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m71 = m71 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "진행".equals((String)moldProject.get("taskstate")) ) {
                m72 = m72 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m73 = m73 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") )&& "완료".equals((String)moldProject.get("taskstate"))  ) {
                m81 = m81 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") )&& "진행".equals((String)moldProject.get("taskstate")) ) {
                m82 = m82 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") )  && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m83 = m83 + Integer.parseInt((String)moldProject.get("count"));
            }else{}
        }

        p14 = p11+p12+p13;
        m14 = m11+m12+m13;
        p24 = p21+p22+p23;
        m24 = m21+m22+m23;
        p34 = p31+p32+p33;
        m34 = m31+m32+m33;
        p44 = p41+p42+p43;
        m44 = m41+m42+m43;
        p54 = p51+p52+p53;
        m54 = m51+m52+m53;
        p64 = p61+p62+p63;
        m64 = m61+m62+m63;
        p74 = p71+p72+p73;
        m74 = m71+m72+m73;
        p84 = p81+p82+p83;
        m84 = m81+m82+m83;
    }

    row++;

    lr = new Label(0, row, "사내제작", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "시작금형", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p14+m14 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p11 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p12 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p13 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p14 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m11 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m12 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m13 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m14 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "시작Mo/Fa", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p24+m24 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p21 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p22 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p23 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p24 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m21 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m22 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m23 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m24 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산금형", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p34+m34 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p31 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p32 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p33 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p34 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m31 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m32 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m33 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m34 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산Mo/Fa", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p44+m44 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p43 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p44 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m43 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m44 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "소계", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p14+m14+p24+m24+p34+m34+p44+m44 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p11+p21+p31+p41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p12+p22+p32+p42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p13+p23+p33+p43 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p14+p24+p34+p44 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m11+m21+m31+m41 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m12+m22+m32+m42 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m13+m23+m33+m43 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m14+m24+m34+m44 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(0, row, "외주제작", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "시작금형", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p54+m54 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p53 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p54 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m53 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m54 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "시작Mo/Fa", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p64+m64 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p63 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p64 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m63 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m64 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산금형" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p74+m74 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p73 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p74 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m73 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m74 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산Mo/Fa" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p84+m84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p83 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m83 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m84 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "소계" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p54+m54+p64+m64+p74+m74+p84+m84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p51+p61+p71+p81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p52+p62+p72+p82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p53+p63+p73+p83 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p54+p64+p74+p84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m51+m61+m71+m81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m52+m62+m72+m82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m53+m63+m73+m83 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m54+m64+m74+m84 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(0, row, "전체" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, "시작금형" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p14+p54+m14+m54  + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p11+p51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p12+p52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p13+p53 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p14+p54 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m11+m51 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m12+m52 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m13+m53 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m14+m54 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "시작Mo/Fa" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p24+p64+m24+m64 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p21+p61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p22+p62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p23+p63 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p24+p64 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m21+m61 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m22+m62 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m23+m63 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m24+m64 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산금형" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p34+p74+m34+m74 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p31+p71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p32+p72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p33+p73 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p34+p74 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m31+m71 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m32+m72 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m33+m73 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m34+m74 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "양산Mo/Fa" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p44+p84+m44+m84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p41+p81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p42+p82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p43+p83 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p44+p84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m41+m81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m42+m82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m43+m83 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m44+m84 + "", cellFormat);
    s1.addCell(lr);

    row++;

    lr = new Label(1, row, "전체" + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, p14+m14+p24+m24+p34+m34+p44+m44+p54+m54+p64+m64+p74+m74+p84+m84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, p11+p21+p31+p41+p51+p61+p71+p81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, p12+p22+p32+p42+p52+p62+p72+p82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, p13+p23+p33+p43+p53+p63+p73+p83 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(6, row, p14+p24+p34+p44+p54+p64+p74+p84 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, m11+m21+m31+m41+m51+m61+m71+m81 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, m12+m22+m32+m42+m52+m62+m72+m82 + "", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, m13+m23+m33+m43+m53+m63+m73+m83 + "", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, row, m14+m24+m34+m44+m54+m64+m74+m84 + "", cellFormat);
    s1.addCell(lr);

    s1.mergeCells(0,0,10,0);
    s1.mergeCells(0,2,1,3);
    s1.mergeCells(2,2,2,3);
    s1.mergeCells(3,2,6,2);
    s1.mergeCells(7,2,10,2);
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
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");

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
