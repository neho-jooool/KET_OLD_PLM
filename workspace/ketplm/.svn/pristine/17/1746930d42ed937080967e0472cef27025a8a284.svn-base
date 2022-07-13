<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoReportVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoReportDetailVO" %>
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

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoReportVO" scope="request" />

<%
    String sWtHome = "";
    String sFilePath = "", sFileName = "";
    //file path
    sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    sFilePath = sWtHome + "/codebase/jsp/ecm" ;
    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMdd");
    sFileName = "TypeEcoListExcelForm" +  ff.format(new Date()) + ".xls";

    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("설계변경 유형별 ECO 현황 조회", 0);

    WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

    int row = 2;
    int rowCount = 0;

    //문서제목
    Label lr = new Label(0, 0, "설계변경 유형별 ECO 현황 조회");
    s1.addCell(lr);

    //타이틀
    if("C".equals(data.getEcoReasonTypeCls())) {
        if("1".equals(data.getProdMoldCls())) {

            lr = new Label(0, row, "번호", cellFormat);
            s1.addCell(lr);

            lr = new Label(1, row, "부품번호", cellFormat);
            s1.addCell(lr);

            lr = new Label(2, row, "부품명", cellFormat);
            s1.addCell(lr);

            lr = new Label(3, row, "건수", cellFormat);
            s1.addCell(lr);

            lr = new Label(4, row, "자동차사업부", cellFormat);
            s1.addCell(lr);

            lr = new Label(5, row, "전자사업부", cellFormat);
            s1.addCell(lr);

            lr = new Label(6, row, "개발단계", cellFormat);
            s1.addCell(lr);

            lr = new Label(7, row, "양산단계", cellFormat);
            s1.addCell(lr);

            lr = new Label(8, row, "고객요청", cellFormat);
            s1.addCell(lr);

            lr = new Label(9, row, "생산성향상", cellFormat);
            s1.addCell(lr);

            lr = new Label(10, row, "품질문제", cellFormat);
            s1.addCell(lr);

            lr = new Label(11, row, "도면양산이관", cellFormat);
            s1.addCell(lr);

            lr = new Label(12, row, "BOM양산이관", cellFormat);
            s1.addCell(lr);

            lr = new Label(13, row, "기타", cellFormat);
            s1.addCell(lr);

        }else if("2".equals(data.getProdMoldCls())) {

            lr = new Label(0, row, "번호", cellFormat);
            s1.addCell(lr);

            lr = new Label(1, row, "Type", cellFormat);
            s1.addCell(lr);

            lr = new Label(2, row, "Die No", cellFormat);
            s1.addCell(lr);

            lr = new Label(3, row, "부품명", cellFormat);
            s1.addCell(lr);

            lr = new Label(4, row, "건수", cellFormat);
            s1.addCell(lr);

            lr = new Label(5, row, "자동차사업부", cellFormat);
            s1.addCell(lr);

            lr = new Label(6, row, "전자사업부", cellFormat);
            s1.addCell(lr);

            lr = new Label(7, row, "개발단계", cellFormat);
            s1.addCell(lr);

            lr = new Label(8, row, "양산단계", cellFormat);
            s1.addCell(lr);

            lr = new Label(9, row, "고객요청", cellFormat);
            s1.addCell(lr);

            lr = new Label(10, row, "품질문제", cellFormat);
            s1.addCell(lr);

            lr = new Label(11, row, "제품설변", cellFormat);
            s1.addCell(lr);

            lr = new Label(12, row, "금형보완", cellFormat);
            s1.addCell(lr);

            lr = new Label(13, row, "생산성향상", cellFormat);
            s1.addCell(lr);

            lr = new Label(14, row, "도면정리", cellFormat);
            s1.addCell(lr);

            lr = new Label(15, row, "기타", cellFormat);
            s1.addCell(lr);

        }
    } else if("I".equals(data.getEcoReasonTypeCls())) {

            lr = new Label(0, row, "번호", cellFormat);
            s1.addCell(lr);

            lr = new Label(1, row, "Type", cellFormat);
            s1.addCell(lr);

            lr = new Label(2, row, "Die No", cellFormat);
            s1.addCell(lr);

            lr = new Label(3, row, "부품명", cellFormat);
            s1.addCell(lr);

            lr = new Label(4, row, "건수", cellFormat);
            s1.addCell(lr);

            lr = new Label(5, row, "자동차사업부", cellFormat);
            s1.addCell(lr);

            lr = new Label(6, row, "전자사업부", cellFormat);
            s1.addCell(lr);

            lr = new Label(7, row, "개발단계", cellFormat);
            s1.addCell(lr);

            lr = new Label(8, row, "양산단계", cellFormat);
            s1.addCell(lr);

            lr = new Label(9, row, "미성형", cellFormat);
            s1.addCell(lr);

            lr = new Label(10, row, "제품치수", cellFormat);
            s1.addCell(lr);

            lr = new Label(11, row, "BURR", cellFormat);
            s1.addCell(lr);

            lr = new Label(12, row, "제품이송", cellFormat);
            s1.addCell(lr);

            lr = new Label(13, row, "WELD", cellFormat);
            s1.addCell(lr);

            lr = new Label(14, row, "자국발생", cellFormat);
            s1.addCell(lr);

            lr = new Label(15, row, "GAS", cellFormat);
            s1.addCell(lr);

            lr = new Label(16, row, "스크랩상승", cellFormat);
            s1.addCell(lr);

            lr = new Label(17, row, "제품취출", cellFormat);
            s1.addCell(lr);

            lr = new Label(18, row, "부품파손", cellFormat);
            s1.addCell(lr);

            lr = new Label(19, row, "금형조립", cellFormat);
            s1.addCell(lr);

            lr = new Label(20, row, "기타", cellFormat);
            s1.addCell(lr);

    }

    int size = data.getResultRows();
    KETSearchEcoReportDetailVO detailVO = null;
    for(int j=0; j<size; j++) {
        detailVO =  data.getKetSearchEcoReportDetailVOList().get(j);

        row++;
        rowCount++;

        if("C".equals(data.getEcoReasonTypeCls())) {
            if("1".equals(data.getProdMoldCls())) {

                //번호
                lr = new Label(0, row, rowCount + "", cellFormat);
                s1.addCell(lr);

                //부품번호
                lr = new Label(1, row, StringUtil.checkNull(detailVO.getObjectNo()), cellFormat);
                s1.addCell(lr);

                //부품명
                lr = new Label(2, row, StringUtil.checkNull(detailVO.getObjectName()), cellFormat);
                s1.addCell(lr);

                //건수
                lr = new Label(3, row, KETStringUtil.money(detailVO.getEcoCount()), cellFormat);
                s1.addCell(lr);

                //자동차사업부
                lr = new Label(4, row, KETStringUtil.money(detailVO.getCarDivEcoCnt()), cellFormat);
                s1.addCell(lr);

                //전자사업부
                lr = new Label(5, row, KETStringUtil.money(detailVO.getElecDivEcoCnt()), cellFormat);
                s1.addCell(lr);

                //개발단계
                lr = new Label(6, row, KETStringUtil.money(detailVO.getDevEcoCnt()), cellFormat);
                s1.addCell(lr);

                //양산단계
                lr = new Label(7, row, KETStringUtil.money(detailVO.getProdEcoCnt()), cellFormat);
                s1.addCell(lr);

                //고객요청 건수
                lr = new Label(8, row, KETStringUtil.money(detailVO.getDataCnt1()), cellFormat);
                s1.addCell(lr);

                //생산성향상 건수
                lr = new Label(9, row, KETStringUtil.money(detailVO.getDataCnt2()), cellFormat);
                s1.addCell(lr);

                //품질문제 건수
                lr = new Label(10, row, KETStringUtil.money(detailVO.getDataCnt3()), cellFormat);
                s1.addCell(lr);

                //도면양산이관 건수
                lr = new Label(11, row, KETStringUtil.money(detailVO.getDataCnt4()), cellFormat);
                s1.addCell(lr);

                //BOM양산이관 건수
                lr = new Label(12, row, KETStringUtil.money(detailVO.getDataCnt5()), cellFormat);
                s1.addCell(lr);

                //기타 건수
                lr = new Label(13, row, KETStringUtil.money(detailVO.getDataCnt6()), cellFormat);
                s1.addCell(lr);

            }else if("2".equals(data.getProdMoldCls())) {

                //번호
                lr = new Label(0, row, rowCount + "", cellFormat);
                s1.addCell(lr);

                //Type
                lr = new Label(1, row, StringUtil.checkReplaceStr(detailVO.getObjType(),"-"), cellFormat);
                s1.addCell(lr);

                //Die No
                lr = new Label(2, row, StringUtil.checkNull(detailVO.getObjectNo()), cellFormat);
                s1.addCell(lr);

                //부품명
                lr = new Label(3, row, StringUtil.checkNull(detailVO.getObjectName()), cellFormat);
                s1.addCell(lr);

                //건수
                lr = new Label(4, row, KETStringUtil.money(detailVO.getEcoCount()), cellFormat);
                s1.addCell(lr);

                //자동차사업부
                lr = new Label(5, row, KETStringUtil.money(detailVO.getCarDivEcoCnt()), cellFormat);
                s1.addCell(lr);

                //전자사업부
                lr = new Label(6, row, KETStringUtil.money(detailVO.getElecDivEcoCnt()), cellFormat);
                s1.addCell(lr);

                //개발단계
                lr = new Label(7, row, KETStringUtil.money(detailVO.getDevEcoCnt()), cellFormat);
                s1.addCell(lr);

                //양산단계
                lr = new Label(8, row, KETStringUtil.money(detailVO.getProdEcoCnt()), cellFormat);
                s1.addCell(lr);

                //고객요청 건수
                lr = new Label(9, row, KETStringUtil.money(detailVO.getDataCnt1()), cellFormat);
                s1.addCell(lr);

                //품질문제 건수
                lr = new Label(10, row, KETStringUtil.money(detailVO.getDataCnt2()), cellFormat);
                s1.addCell(lr);

                //제품설변 건수
                lr = new Label(11, row, KETStringUtil.money(detailVO.getDataCnt3()), cellFormat);
                s1.addCell(lr);

                //금형보완 건수
                lr = new Label(12, row, KETStringUtil.money(detailVO.getDataCnt4()), cellFormat);
                s1.addCell(lr);

                //생산성향상 건수
                lr = new Label(13, row, KETStringUtil.money(detailVO.getDataCnt5()), cellFormat);
                s1.addCell(lr);

                //도면정리 건수
                lr = new Label(14, row, KETStringUtil.money(detailVO.getDataCnt6()), cellFormat);
                s1.addCell(lr);

                //기타 건수
                lr = new Label(15, row, KETStringUtil.money(detailVO.getDataCnt7()), cellFormat);
                s1.addCell(lr);

            }

        } else if("I".equals(data.getEcoReasonTypeCls())) {
            //번호
                lr = new Label(0, row, rowCount + "", cellFormat);
                s1.addCell(lr);

                //Type
                lr = new Label(1, row, StringUtil.checkReplaceStr(detailVO.getObjType(),"-"), cellFormat);
                s1.addCell(lr);

                //Die No
                lr = new Label(2, row, StringUtil.checkNull(detailVO.getObjectNo()), cellFormat);
                s1.addCell(lr);

                //부품명
                lr = new Label(3, row, StringUtil.checkNull(detailVO.getObjectName()), cellFormat);
                s1.addCell(lr);

                //건수
                lr = new Label(4, row, KETStringUtil.money(detailVO.getEcoCount()), cellFormat);
                s1.addCell(lr);

                //자동차사업부
                lr = new Label(5, row, KETStringUtil.money(detailVO.getCarDivEcoCnt()), cellFormat);
                s1.addCell(lr);

                //전자사업부
                lr = new Label(6, row, KETStringUtil.money(detailVO.getElecDivEcoCnt()), cellFormat);
                s1.addCell(lr);

                //개발단계
                lr = new Label(7, row, KETStringUtil.money(detailVO.getDevEcoCnt()), cellFormat);
                s1.addCell(lr);

                //양산단계
                lr = new Label(8, row, KETStringUtil.money(detailVO.getProdEcoCnt()), cellFormat);
                s1.addCell(lr);

                //미성형 건수
                lr = new Label(9, row, KETStringUtil.money(detailVO.getDataCnt1()), cellFormat);
                s1.addCell(lr);

                //제품치수 건수
                lr = new Label(10, row, KETStringUtil.money(detailVO.getDataCnt2()), cellFormat);
                s1.addCell(lr);

                //BURR 건수
                lr = new Label(11, row, KETStringUtil.money(detailVO.getDataCnt3()), cellFormat);
                s1.addCell(lr);

                //제품 이송 건수
                lr = new Label(12, row, KETStringUtil.money(detailVO.getDataCnt4()), cellFormat);
                s1.addCell(lr);

                //WELD 건수
                lr = new Label(13, row, KETStringUtil.money(detailVO.getDataCnt5()), cellFormat);
                s1.addCell(lr);

                //자국 발생 건수
                lr = new Label(14, row, KETStringUtil.money(detailVO.getDataCnt6()), cellFormat);
                s1.addCell(lr);

                //GAS 건수
                lr = new Label(15, row, KETStringUtil.money(detailVO.getDataCnt7()), cellFormat);
                s1.addCell(lr);

                //스크랩 상승 건수
                lr = new Label(16, row, KETStringUtil.money(detailVO.getDataCnt8()), cellFormat);
                s1.addCell(lr);

                //제품취출 건수
                lr = new Label(17, row, KETStringUtil.money(detailVO.getDataCnt9()), cellFormat);
                s1.addCell(lr);

                //부품 파손 건수
                lr = new Label(18, row, KETStringUtil.money(detailVO.getDataCnt10()), cellFormat);
                s1.addCell(lr);

                //금형조립 건수
                lr = new Label(19, row, KETStringUtil.money(detailVO.getDataCnt11()), cellFormat);
                s1.addCell(lr);

                //기타 건수
                lr = new Label(20, row, KETStringUtil.money(detailVO.getDataCnt12()), cellFormat);
                s1.addCell(lr);
        }
    }
    writebook.write();
    writebook.close();

    try {
        File file = new File(sFilePath+ "/" + sFileName);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Added by MJOH, 2011-04-18
        // 엑셀 다운로드 파일 DRM 암호화 적용
        String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
        file = E3PSDRMHelper.download( file, sFileName, contentID, request.getRemoteAddr() );
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
