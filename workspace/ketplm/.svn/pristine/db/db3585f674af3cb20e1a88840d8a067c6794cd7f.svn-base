<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.ecm.entity.KETSearchEcoVO" %>
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
<%@page import="jxl.write.WritableCellFormat"%>
<%@page import="jxl.format.Border"%>
<%@page import="jxl.format.BorderLineStyle"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoVO" scope="request" />
<%
String sortCol1 = "ECR No";
String sortSQL = data.getSortColumn().replace(" ", "");

String sortCol2 = "ECR 제목";
String sortCol5 = "작성자";
String sortCol6 = "승인일자";
String sortCol7 = "결재상태";


    String sWtHome = "";
    String sFilePath = "", sFileName = "";
    //file path
    sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    sFilePath = sWtHome + "/codebase/jsp/ecm" ;
    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    sFileName = "SearchECRList_" +  ff.format(new Date()) + ".xls";

    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("ECR 목록", 0);

    WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

    int row = 2;
    int rowCount = 0;

    //문서제목
    Label lr = new Label(0, 0, "ECR 목록");
    s1.addCell(lr);

    //타이틀
    lr = new Label(0, row, "No", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, sortCol1, cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, "연계 ECO No", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "ECR구분", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "단계구분", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "Project No", cellFormat);
    s1.addCell(lr);

    lr = new Label(6, row, "Part No/Die No", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, "Part Name", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, sortCol2, cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, "작성부서", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, sortCol5, cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sortCol6, cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, "의뢰구분", cellFormat);
    s1.addCell(lr);

    lr = new Label(13, row, "설계변경유형", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, "생산처", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, "완료요청일", cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, "처리구분", cellFormat);
    s1.addCell(lr);

    lr = new Label(17, row, sortCol7, cellFormat);
    s1.addCell(lr);

    int size = data.getResultRows();
    KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
    for(int j=0; j<size; j++) {
        ketSearchEcoDetailVO =  data.getKetSearchEcoDetailVOList().get(j);

        row++;
        //번호
        rowCount++;
        lr = new Label(0, row, rowCount + "", cellFormat);
        s1.addCell(lr);

        //ECR 번호
        lr = new Label(1, row, StringUtil.checkNull(ketSearchEcoDetailVO.getEcoId()), cellFormat);
        s1.addCell(lr);

        //연계 ECO No
        lr = new Label(2, row, StringUtil.checkNull(ketSearchEcoDetailVO.getRelatedECOStr()), cellFormat);
        s1.addCell(lr);

        //ECR 구분
        lr = new Label(3, row, StringUtil.checkNull(ketSearchEcoDetailVO.getProdMoldClsName()), cellFormat);
        s1.addCell(lr);

        //단계구분
        lr = new Label(4, row, StringUtil.checkNull(ketSearchEcoDetailVO.getDevFlag()), cellFormat);
        s1.addCell(lr);

        //Project No
        lr = new Label(5, row, StringUtil.checkNull(ketSearchEcoDetailVO.getProjectNo()), cellFormat);
        s1.addCell(lr);

        //Part No/Die No setPartNumber
        lr = new Label(6, row, StringUtil.checkNull(ketSearchEcoDetailVO.getPartNumber()), cellFormat);
        s1.addCell(lr);

        //Part Name
        lr = new Label(7, row, StringUtil.checkNull(ketSearchEcoDetailVO.getPartName()), cellFormat);
        s1.addCell(lr);

        //ECR 제목
        lr = new Label(8, row, StringUtil.checkNull(ketSearchEcoDetailVO.getEcoName()), cellFormat);
        s1.addCell(lr);

        //작성부서명
        lr = new Label(9, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDeptName()), cellFormat);
        s1.addCell(lr);

        //작성자
        lr = new Label(10, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCreatorName()), cellFormat);
        s1.addCell(lr);

        //승인일자
        lr = new Label(11, row, StringUtil.checkNull(ketSearchEcoDetailVO.getApproveDate()), cellFormat);
        s1.addCell(lr);

        //의뢰구분
        lr = new Label(12, row, StringUtil.checkNull(ketSearchEcoDetailVO.getReqType() + ketSearchEcoDetailVO.getReqTypeDesc()), cellFormat);
        s1.addCell(lr);

        //설계변경유형
        lr = new Label(13, row, StringUtil.checkNull(ketSearchEcoDetailVO.getMoldReqChgType() + ketSearchEcoDetailVO.getMoldReqChgTypeDesc()), cellFormat);
        s1.addCell(lr);

        //생산처
        lr = new Label(14, row, StringUtil.checkNull(ketSearchEcoDetailVO.getProdVendor()), cellFormat);
        s1.addCell(lr);

        //완료요청일
        lr = new Label(15, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCompleteReqDate()), cellFormat);
        s1.addCell(lr);

        //처리구분
        lr = new Label(16, row, StringUtil.checkNull(ketSearchEcoDetailVO.getProcType() + ketSearchEcoDetailVO.getProdTypeDesc() ), cellFormat);
        s1.addCell(lr);

        //결재상태
        lr = new Label(17, row, StringUtil.checkNull(ketSearchEcoDetailVO.getSancStateFlag()), cellFormat);
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
	    Kogger.error(e);
    }
%>
