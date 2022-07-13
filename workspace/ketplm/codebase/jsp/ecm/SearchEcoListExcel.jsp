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

<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="wt.fc.*"%>
<%@page import="e3ps.common.service.*"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoVO" scope="request" />
<%
String sortCol1 = "ECO No";
String sortSQL = data.getSortColumn().replace(" ", "");

String sortCol2 = "ECO 제목";
String sortCol6 = "예산확보여부";
String sortCol7 = "작성자";
String sortCol8 = "승인일자";
String sortCol9 = "결재상태";

String activityName = "";
String completeDate = "";
String requestDate = "";


    String sWtHome = "";
    String sFilePath = "", sFileName = "";
    //file path
    sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    sFilePath = sWtHome + "/codebase/jsp/ecm" ;
    //file name
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    sFileName = "SearchECOList_" +  ff.format(new Date()) + ".xls";

    WritableWorkbook writebook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = writebook.createSheet("ECO 목록", 0);

    WritableCellFormat cellFormat = new WritableCellFormat();
    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

    int row = 2;
    int rowCount = 0;

    //문서제목
    Label lr = new Label(0, 0, "ECO 목록");
    s1.addCell(lr);

    //타이틀
    lr = new Label(0, row, "No", cellFormat);
    s1.addCell(lr);

    lr = new Label(1, row, sortCol1, cellFormat);
    s1.addCell(lr);

    lr = new Label(2, row, "연계 ECR No", cellFormat);
    s1.addCell(lr);

    lr = new Label(3, row, "연계 ECO No", cellFormat);
    s1.addCell(lr);

    lr = new Label(4, row, "ECO구분", cellFormat);
    s1.addCell(lr);

    lr = new Label(5, row, "단계구분", cellFormat);
    s1.addCell(lr);

    lr = new Label(6, row, "Project No", cellFormat);
    s1.addCell(lr);

    lr = new Label(7, row, "Part No/Die No", cellFormat);
    s1.addCell(lr);

    lr = new Label(8, row, "Part Name", cellFormat);
    s1.addCell(lr);

    lr = new Label(9, row, "고객사", cellFormat);
    s1.addCell(lr);

    lr = new Label(10, row, "차종", cellFormat);
    s1.addCell(lr);

    lr = new Label(11, row, sortCol2, cellFormat);
    s1.addCell(lr);

    lr = new Label(12, row, "설계변경 사유", cellFormat);
    s1.addCell(lr);

    lr = new Label(13, row, "생산성 향상 유형", cellFormat);
    s1.addCell(lr);

    lr = new Label(14, row, "생산처", cellFormat);
    s1.addCell(lr);

    lr = new Label(15, row, sortCol6, cellFormat);
    s1.addCell(lr);

    lr = new Label(16, row, "고객확인 구분", cellFormat);
    s1.addCell(lr);

    lr = new Label(17, row, "조정 및 변경사항", cellFormat);
    s1.addCell(lr);

    lr = new Label(18, row, "CU도면 변경여부", cellFormat);
    s1.addCell(lr);

    lr = new Label(19, row, "작성부서", cellFormat);
    s1.addCell(lr);

    lr = new Label(20, row, sortCol7, cellFormat);
    s1.addCell(lr);

    lr = new Label(21, row, "상신일자", cellFormat);
    s1.addCell(lr);

    lr = new Label(22, row, sortCol8, cellFormat);
    s1.addCell(lr);

    lr = new Label(23, row, sortCol9, cellFormat);
    s1.addCell(lr);

    int size = data.getResultRows();
    KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
    for(int j=0; j<size; j++) {
        ketSearchEcoDetailVO =  data.getKetSearchEcoDetailVOList().get(j);


        //=========결재 요청일 관련 시작====================//
           //Object obj = CommonUtil.getObject(ketSearchEcoDetailVO.getOid());
           //ReferenceFactory rf = new ReferenceFactory();
           KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
           KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
           KETWfmApprovalMaster master = null;
           Object temp = new Object();
           Vector vec = null;
           WTObject targetObj = KETCommonHelper.service.getPBO((WTObject)CommonUtil.getObject(ketSearchEcoDetailVO.getOid()));
           if(targetObj!=null)master = (KETWfmApprovalMaster)WorkflowSearchHelper.manager.getMaster(targetObj);
           if(master!=null){

               vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
               for(int k=0; k<vec.size()-1; k++){
                   for(int i=k+1; i<vec.size(); i++){
                       history1 = (KETWfmApprovalHistory)vec.get(k);
                       history2 = (KETWfmApprovalHistory)vec.get(k);
                       if(history1.getSeqNum() < history2.getSeqNum()){
                           temp = vec.get(k);
                           vec.set(k , vec.get(i));
                           vec.set(i , temp);
                       }
                   }
               }

           }

           if( vec != null )
        {
            boolean isApprover = true;
            activityName = "";
            for( int x = 0; x< vec.size(); x++ ){
                KETWfmApprovalHistory history = (KETWfmApprovalHistory)vec.get(x);
                activityName = StringUtil.checkNull( history.getActivityName() );
                  if( isApprover && activityName.equals("검토") )
                  {
                      activityName = "승인";
                      isApprover = false;
                  }
                 if(history.getCompletedDate()!=null)completeDate = DateUtil.getTimeFormat(history.getCompletedDate(),"yyyy-MM-dd");
                 if(activityName.equals("요청"))requestDate =  completeDate;

            }
        }


          //=========결재 요청일 관련 끝====================//
        row++;
        //No
        rowCount++;
        lr = new Label(0, row, rowCount + "", cellFormat);
        s1.addCell(lr);

        //ECO No
        lr = new Label(1, row, StringUtil.checkNull(ketSearchEcoDetailVO.getEcoId()), cellFormat);
        s1.addCell(lr);

        //연계 ECR No
        lr = new Label(2, row, StringUtil.checkNull(ketSearchEcoDetailVO.getRelatedECRStr()), cellFormat);
        s1.addCell(lr);

        //연계 ECO No
        lr = new Label(3, row, StringUtil.checkNull(ketSearchEcoDetailVO.getRelatedECOStr()), cellFormat);
        s1.addCell(lr);

        //ECO 구분
        lr = new Label(4, row, StringUtil.checkNull(ketSearchEcoDetailVO.getProdMoldClsName()), cellFormat);
        s1.addCell(lr);

        //단계구분
        lr = new Label(5, row, StringUtil.checkNull(ketSearchEcoDetailVO.getDevFlag()), cellFormat);
        s1.addCell(lr);

        //Project No
        lr = new Label(6, row, StringUtil.checkNull(ketSearchEcoDetailVO.getProjectNo() ), cellFormat);
        s1.addCell(lr);

        //Part No/Die No
        lr = new Label(7, row, StringUtil.checkNull(ketSearchEcoDetailVO.getPartNumber()), cellFormat);
        s1.addCell(lr);

        //Part Name
        lr = new Label(8, row, StringUtil.checkNull(ketSearchEcoDetailVO.getPartName()), cellFormat);
        s1.addCell(lr);

        //고객사
        lr = new Label(9, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCarMaker()), cellFormat);
        s1.addCell(lr);

        //차종
        lr = new Label(10, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCarCategory()), cellFormat);
        s1.addCell(lr);

        //ECO 제목
        lr = new Label(11, row, StringUtil.checkNull(ketSearchEcoDetailVO.getEcoName()), cellFormat);
        s1.addCell(lr);

        //설계변경 사유
        lr = new Label(12, row, StringUtil.checkNull(ketSearchEcoDetailVO.getChangeReason()+ketSearchEcoDetailVO.getOtherChgReason()), cellFormat);
        s1.addCell(lr);

        //생산성 향상 유형
        lr = new Label(13, row, StringUtil.checkNull(ketSearchEcoDetailVO.getIncProdType()+ketSearchEcoDetailVO.getOtherIncProdType()), cellFormat);
        s1.addCell(lr);

        //생산처
        lr = new Label(14, row, StringUtil.checkNull(ketSearchEcoDetailVO.getProdVendor()), cellFormat);
        s1.addCell(lr);

        //예산확보여부
        lr = new Label(15, row, StringUtil.checkNull(ketSearchEcoDetailVO.getSecureBudgetYn()), cellFormat);
        s1.addCell(lr);

        //고객확인 구분
        lr = new Label(16, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCustomChk() + ketSearchEcoDetailVO.getCustomChkDesc() ), cellFormat);
        s1.addCell(lr);

        //조정 및 변경사항
        lr = new Label(17, row, StringUtil.checkNull( ketSearchEcoDetailVO.getControlFlag() ), cellFormat);
        s1.addCell(lr);

        //CU도면 변경여부
        lr = new Label(18, row, StringUtil.checkNull( ketSearchEcoDetailVO.getCuDrawingChk() ), cellFormat);
        s1.addCell(lr);

        //작성부서
        lr = new Label(19, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDeptName()), cellFormat);
        s1.addCell(lr);

        //작성자
        lr = new Label(20, row, StringUtil.checkNull(ketSearchEcoDetailVO.getCreatorName()), cellFormat);
        s1.addCell(lr);

        //상신일자
        lr = new Label(21, row, StringUtil.checkNull(requestDate), cellFormat);
        s1.addCell(lr);

        //승인일자
        lr = new Label(22, row, StringUtil.checkNull(ketSearchEcoDetailVO.getApproveDate()), cellFormat);
        s1.addCell(lr);

        //결재상태
        lr = new Label(23, row, StringUtil.checkNull(ketSearchEcoDetailVO.getSancStateFlag()), cellFormat);
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
//     e.printStackTrace();
    }
%>
