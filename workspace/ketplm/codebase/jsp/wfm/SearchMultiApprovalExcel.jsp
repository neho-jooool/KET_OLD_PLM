<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "jxl.write.WritableWorkbook"%>
<%@page import = "java.io.File"%>
<%@page import = "java.io.FileInputStream" %>
<%@page import = "jxl.Workbook"%>
<%@page import = "jxl.write.WritableSheet"%>
<%@page import = "jxl.write.Label"%>
<%@page import = "java.text.SimpleDateFormat"%>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>
<%@page import = "e3ps.common.drm.E3PSDRMHelper" %>
<%@page import = "e3ps.wfm.entity.*" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "wt.session.*" %>
<%@page import = "wt.query.*" %>
<%@page import = "wt.fc.*" %>
<%@page import = "wt.org.*" %>
<%@page import = "wt.lifecycle.*" %>
<%@page import = "java.util.*" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="result" class="wt.fc.QueryResult" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

  String sWtHome = "";
  String sFilePath = "", sFileName = "";
  //file path
  sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
  sFilePath = sWtHome + "/codebase/jsp/wfm" ;
  //file name
  SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
  WTPrincipal user = SessionHelper.manager.getPrincipal();
  sFileName = "SearchMultiApprovalList_" +  ff.format(new Date()) + ".xls";
  //copy file
  File copyfile = new File(sFilePath + sFileName) ;

  response.reset();
  response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
  response.setHeader("Content-Disposition","attachment; filename="+sFileName);

  WritableWorkbook writebook = Workbook.createWorkbook( new File(sFilePath + "/" + sFileName) );
  WritableSheet s1 = writebook.createSheet(messageService.getString("e3ps.message.ket_message", "02341")/*일괄결재요청리스트*/, 1);

  int row = 2;

  //문서제목
  Label lr = new Label(0, 0, messageService.getString("e3ps.message.ket_message", "02341")/*일괄결재요청리스트*/);
  s1.addCell(lr);

  //타이틀
  lr = new Label(0, row, messageService.getString("e3ps.message.ket_message", "01486")/*번호*/);
  s1.addCell(lr);

  lr = new Label(1, row, messageService.getString("e3ps.message.ket_message", "02197")/*요청제목*/);
  s1.addCell(lr);

  lr = new Label(2, row, messageService.getString("e3ps.message.ket_message", "02196")/*요청자*/);
  s1.addCell(lr);

  lr = new Label(3, row, messageService.getString("e3ps.message.ket_message", "02195")/*요청일자*/);
  s1.addCell(lr);

  lr = new Label(4, row, messageService.getString("e3ps.message.ket_message", "00771")/*결재상태*/);
  s1.addCell(lr);

  int cnt = result.size();
  while(result.hasMoreElements()){
    row++;
    cnt--;

    lr = new Label(0, row, Integer.toString(cnt));
    s1.addCell(lr);

    Object[] obj = (Object[])result.nextElement();
    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest)obj[0];
    String reqDate = DateUtil.getDateString(multiReq.getRequestDate(),"d");
        if(reqDate.equals("")||reqDate==null)reqDate = "";

        lr = new Label(1, row, multiReq.getReqName());
    s1.addCell(lr);

    lr = new Label(2, row, multiReq.getOwner().getFullName());
    s1.addCell(lr);

    lr = new Label(3, row, reqDate);
    s1.addCell(lr);

    lr = new Label(4, row, multiReq.getLifeCycleState().getDisplay());
    s1.addCell(lr);

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
      Kogger.error(e);
     throw e;
  }
%>
