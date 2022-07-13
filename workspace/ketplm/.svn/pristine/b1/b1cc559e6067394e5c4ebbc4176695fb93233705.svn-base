<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.SimpleDateFormat
                           ,java.io.File
                           ,java.util.Date
                           ,java.util.Hashtable
                           ,jxl.write.WritableWorkbook
                           ,jxl.Workbook
                           ,jxl.write.WritableSheet
                           ,jxl.write.Label
                           ,e3ps.common.util.StringUtil"%>
<%@page import="jxl.write.VerticalAlignment"%>
<%@page import="jxl.write.WritableCellFormat"%>
<%@page import="e3ps.common.drm.E3PSDRMHelper "  %>
<%@page import="jxl.format.Border"%>
<%@page import="jxl.format.Alignment"%>
<%@page import="jxl.format.BorderLineStyle"%>
<%@page import="java.io.FileInputStream"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="planList" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="reqData" class="java.util.Hashtable" scope="request"/>
<%
    String sortCol1 = "Die No";
    String sortSQL = "";
    if( reqData != null && !reqData.isEmpty() )
    {
        sortSQL = reqData.get("sort").toString().replace(" ", "");
    }

    if(sortSQL.equals("3ASC")) {
        sortCol1 += "(▲)";
    } else if(sortSQL.equals("3DESC")) {
        sortCol1 += "(▼)";
    }
    String sortCol2 = "Part No";
    if(sortSQL.equals("4ASC")) {
        sortCol2 += "(▲)";
    } else if(sortSQL.equals("4DESC")) {
        sortCol2 += "(▼)";
    }

    String sortCol3 = "금형 ECO";
    if(sortSQL.equals("5ASC")) {
        sortCol3 += "(▲)";
    } else if(sortSQL.equals("5DESC")) {
        sortCol3 += "(▼)";
    }

    String sortCol4 = "제품 ECO";
    if(sortSQL.equals("6ASC")) {
        sortCol4 += "(▲)";
    } else if(sortSQL.equals("6DESC")) {
        sortCol4 += "(▼)";
    }

    String sortCol5 = "작성자";
    if(sortSQL.equals("7ASC")) {
        sortCol5 += "(▲)";
    } else if(sortSQL.equals("7DESC")) {
        sortCol5 += "(▼)";
    }

    String sortCol6 = "작성일자";
    if(sortSQL.equals("8ASC")) {
        sortCol6 += "(▲)";
    } else if(sortSQL.equals("8DESC")) {
        sortCol6 += "(▼)";
    }

    String sWtHome = "";
    String sFilePath = "", sFileName = "";
    //file path
    sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    sFilePath = sWtHome + "/codebase/jsp/ecm" ;

    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    sFileName = "MoldPlanListExcel" +  ff.format(new Date()) + ".xls";

    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("개조수정 일정 리스트", 0);

    s1.mergeCells(0,1,0,2);
    s1.mergeCells(1,1,1,2);
    s1.mergeCells(2,1,2,2);
    s1.mergeCells(3,1,3,2);
    s1.mergeCells(4,1,4,2);
    s1.mergeCells(5,1,6,1);
    s1.mergeCells(7,1,7,2);
    s1.mergeCells(8,1,8,2);
    s1.mergeCells(9,1,9,2);
    s1.mergeCells(10,1,10,2);
    s1.mergeCells(11,1,11,2);
    s1.mergeCells(12,1,12,2);
    s1.mergeCells(13,1,13,2);
    s1.mergeCells(14,1,14,2);
    s1.mergeCells(15,1,15,2);
    s1.mergeCells(16,1,16,2);
    s1.mergeCells(17,1,25,1);
    s1.mergeCells(26,1,26,2);
    s1.mergeCells(27,1,27,2);
    s1.mergeCells(28,1,32,1);
    s1.mergeCells(33,1,33,2);

    WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

    WritableCellFormat headerFormat = new WritableCellFormat();
    headerFormat.setBorder(Border.ALL , BorderLineStyle.THIN);
    headerFormat.setAlignment(Alignment.CENTRE); // 셀 가운데 정렬
    headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 수직 가운데 정렬;

    Label lr = new Label(0, 1, "No", headerFormat);
    s1.addCell(lr);

    lr = new Label(1, 1, "구분", headerFormat);
    s1.addCell(lr);

    lr = new Label(2, 1, "Die No", headerFormat );
    s1.addCell(lr);

    lr = new Label(3, 1, "부품번호", headerFormat);
    s1.addCell(lr);

    lr = new Label(4, 1, "부품명", headerFormat );
    s1.addCell(lr);

    lr = new Label(5, 1, "등록근거", headerFormat);
    s1.addCell(lr);

    lr = new Label(5, 2, "사유", headerFormat );
    s1.addCell(lr);
    lr = new Label(6, 2, "일자", headerFormat);
    s1.addCell(lr);

    lr = new Label(7, 1, "등록자", headerFormat);
    s1.addCell(lr);

    lr = new Label(8, 1, "등록부서", headerFormat);
    s1.addCell(lr);

    lr = new Label(9, 1, "고객사4M", headerFormat);
    s1.addCell(lr);

    lr = new Label(10, 1, "수정내용", headerFormat);
    s1.addCell(lr);

    lr = new Label(11, 1, "생산처", headerFormat);
    s1.addCell(lr);

    lr = new Label(12, 1, "개발담당부서", headerFormat);
    s1.addCell(lr);

    lr = new Label(13, 1, "제품ECO No", headerFormat);
    s1.addCell(lr);

    lr = new Label(14, 1, "제품ECO 담당", headerFormat);
    s1.addCell(lr);

    lr = new Label(15, 1, "금형ECO No", headerFormat);
    s1.addCell(lr);

    lr = new Label(16, 1, "금형ECO 담당", headerFormat);
    s1.addCell(lr);

    lr = new Label(17, 1, "일정정보", headerFormat);
    s1.addCell(lr);

    lr = new Label(17, 2, "제품도", headerFormat);
    s1.addCell(lr);

    lr = new Label(18, 2, "금형설계", headerFormat);
    s1.addCell(lr);

    lr = new Label(19, 2, "금형입고", headerFormat);
    s1.addCell(lr);

    lr = new Label(20, 2, "금형부품", headerFormat);
    s1.addCell(lr);

    lr = new Label(21, 2, "금형조립", headerFormat);
    s1.addCell(lr);

    lr = new Label(22, 2, "금형Try", headerFormat);
    s1.addCell(lr);

    lr = new Label(23, 2, "제품측정", headerFormat);
    s1.addCell(lr);

    lr = new Label(24, 2, "제품검토협의", headerFormat);
    s1.addCell(lr);

    lr = new Label(25, 2, "4M완료일", headerFormat);
    s1.addCell(lr);

    lr = new Label(26, 1, "단계", headerFormat);
    s1.addCell(lr);

    lr = new Label(27, 1, "상태", headerFormat);
    s1.addCell(lr);

    lr = new Label(28, 1, "진행내용", headerFormat );
    s1.addCell(lr);

    lr = new Label(28, 2, "측정구분", headerFormat);
    s1.addCell(lr);

    lr = new Label(29, 2, "조치", headerFormat);
    s1.addCell(lr);

    lr = new Label(30, 2, "결과", headerFormat);
    s1.addCell(lr);

    lr = new Label(31, 2, "측정일", headerFormat);
    s1.addCell(lr);

    lr = new Label(32, 2, "등록일자", headerFormat);
    s1.addCell(lr);

    lr = new Label(33, 1, "비고", headerFormat);
    s1.addCell(lr);

    int rowCount = 0;
    int row = 2;

    Hashtable<String, String> plan = null;
    for( int listCnt=0 ; listCnt< planList.size() ; listCnt++ )
    {
        plan = (Hashtable)planList.get(listCnt);

        row++;

        //No
        rowCount++;
        lr = new Label(0, row, rowCount + "", cellFormat);
        s1.addCell(lr);

        //구분
        lr = new Label(1, row, StringUtil.checkNull(plan.get("plan_type")), headerFormat);
        s1.addCell(lr);

        //Die No
        lr = new Label(2, row, StringUtil.checkNull(plan.get("die_no")), headerFormat);
        s1.addCell(lr);

        //Part No
        lr = new Label(3, row, StringUtil.checkNull(plan.get("part_no")), headerFormat);
        s1.addCell(lr);

        //Part Name
        lr = new Label(4, row, StringUtil.checkNull(plan.get("part_name")), cellFormat);
        s1.addCell(lr);

        //사유
        lr = new Label(5, row, StringUtil.checkNull(plan.get("reg_basis")), headerFormat);
        s1.addCell(lr);

        //일자
        lr = new Label(6, row, StringUtil.checkNull(plan.get("basis_date")), headerFormat);
        s1.addCell(lr);

        //등록자
        lr = new Label(7, row, StringUtil.checkNull(plan.get("owner_name")), headerFormat);
        s1.addCell(lr);

        //등록부서
        lr = new Label(8, row, StringUtil.checkNull(plan.get("owner_dept")), headerFormat);
        s1.addCell(lr);

        //고객4M
        lr = new Label(9, row, StringUtil.checkNull(plan.get("ATTRIBUTE1")), headerFormat);
        s1.addCell(lr);

        //수정내용
        lr = new Label(10, row, StringUtil.checkNull(plan.get("modify_desc")), cellFormat);
        s1.addCell(lr);

        //생산처
        lr = new Label(11, row, StringUtil.checkNull(plan.get("vendor_name")), headerFormat);
        s1.addCell(lr);

        //개발담당부서
        lr = new Label(12, row, StringUtil.checkNull(plan.get("prod_dept_name")), headerFormat);
        s1.addCell(lr);

        //제품ECO No
        lr = new Label(13, row, StringUtil.checkNull(plan.get("prod_eco_id")), headerFormat);
        s1.addCell(lr);

        //제품ECO 담당
        lr = new Label(14, row, StringUtil.checkNull(plan.get("prod_owner")), headerFormat);
        s1.addCell(lr);

        //금형ECO No
        lr = new Label(15, row, StringUtil.checkNull(plan.get("mold_eco_id")), headerFormat);
        s1.addCell(lr);

        //금형ECO 담당
        lr = new Label(16, row, StringUtil.checkNull(plan.get("mold_owner")), headerFormat);
        s1.addCell(lr);

        //제품도
        lr = new Label(17, row, StringUtil.changeString(plan.get("prod_dwg_date"), "()", ""), headerFormat);
        s1.addCell(lr);

        //금형설계
        lr = new Label(18, row, StringUtil.changeString(plan.get("mold_chg_date"), "()", ""), headerFormat);
        s1.addCell(lr);

        //금형입고
        lr = new Label(19, row, StringUtil.changeString(plan.get("work_date"), "()", ""), headerFormat);
        s1.addCell(lr);

        //금형부품
        lr = new Label(20, row, StringUtil.changeString(plan.get("store_date"), "()", ""), headerFormat);
        s1.addCell(lr);

        //금형조립
        lr = new Label(21, row, StringUtil.changeString(plan.get("assembly_date"), "()", ""), headerFormat);
        s1.addCell(lr);

        //금형 Try
        lr = new Label(22, row, StringUtil.changeString(plan.get("try_date"), "()", ""), headerFormat);
        s1.addCell(lr);

        //제품측정
        lr = new Label(23, row, StringUtil.changeString(plan.get("test_date"), "()", "" ), headerFormat);
        s1.addCell(lr);

        //제품검토협의
        lr = new Label(24, row, StringUtil.changeString(plan.get("approve_date"), "()", ""), headerFormat);
        s1.addCell(lr);

        //4M완료일
        lr = new Label(25, row, StringUtil.changeString(plan.get("ATTRIBUTE2"), "()", ""), headerFormat);
        s1.addCell(lr);

        //단계
        lr = new Label(26, row, StringUtil.checkNull(plan.get("cur_proc")), headerFormat);
        s1.addCell(lr);

        //상태
        lr = new Label(27, row, StringUtil.checkNull(plan.get("status")), headerFormat);
        s1.addCell(lr);

        //측정구분
        lr = new Label(28, row, StringUtil.checkNull(plan.get("measure_type")), headerFormat);
        s1.addCell(lr);

        //조치
        lr = new Label(29, row, StringUtil.checkNull(plan.get("fail_action")), headerFormat);
        s1.addCell(lr);

        //결과
        lr = new Label(30, row, StringUtil.checkNull(plan.get("rs")), headerFormat);
        s1.addCell(lr);

        //측정일
        lr = new Label(31, row, StringUtil.checkNull(plan.get("measure_date")), headerFormat);
        s1.addCell(lr);

        //측정일
        lr = new Label(32, row, StringUtil.checkNull(plan.get("createstampa2")), headerFormat);
        s1.addCell(lr);

        //비고
        lr = new Label(33, row, StringUtil.checkNull(plan.get("plan_desc")), cellFormat);
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
