<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.session.SessionHelper"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    String command = request.getParameter("command");

    String pjtType = "";
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    ReviewProject rp = null;
    ProductProject pp = null;
    MoldProject mp = null;
    if(project instanceof ReviewProject){
        pjtType = "review";
        rp = (ReviewProject)project;
    }if(project instanceof ProductProject){
        pjtType = "product";
        pp = (ProductProject)project;
    }if(project instanceof MoldProject){
        pjtType = "mold";
        mp = (MoldProject)project;
    }


    String contentType = request.getContentType();
    FileUploader uploader = null;

    e3ps.common.content.fileuploader.FormUploader fileUploader = null;
    String stopDetil = "";

    if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 ) {
        uploader = FileUploader.newFileUploader(null, request, response);
        command = uploader.getFormParameter("command");
        stopDetil = uploader.getFormParameter("stopDetil");
        oid = uploader.getFormParameter("oid");
        fileUploader = e3ps.common.content.fileuploader.FormUploader.newFormUploader(request);
    } else {
        command = CharUtil.E2K(request.getParameter("command"));
        oid = CharUtil.E2K(request.getParameter("oid"));
    }






    if(command.equals("create")){
        project.setStopedDetil(stopDetil);
        project = (E3PSProject)PersistenceHelper.manager.save(project);
        Vector tVec = new Vector();
        //기존 존재하던 모든 파일 목록
        Vector oldFiles = ContentUtil.getSecondaryContents(project);
        for(int i = 0; i < oldFiles.size(); i++) {
            ContentInfo info = (ContentInfo) oldFiles.elementAt(i);
            //Kogger.debug(info.getContentOid());
            //if( tVec.contains(info.getContentOid()) ) {
                project = (E3PSProject)E3PSContentHelper.service.delete(project, (ApplicationData)CommonUtil.getObject(info.getContentOid()));
            //}
        }

        java.util.Vector files = uploader.getFiles();
        for(int i = 0; i < files.size(); i++) {
            project = (E3PSProject)E3PSContentHelper.service.attach(project, (WBFile)files.get(i), "", ContentRoleType.SECONDARY);
        }
        //Kogger.debug("stop save stop:" + project.getStopedDetil());
    }


        if(command.equals("create")){
%>
<script>
        alert('<%=messageService.getString("e3ps.message.ket_message", "02460") %><%--저장되었습니다--%>');
        <%if("review".equals(pjtType)){%>
        opener.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>";
        <%}else if("product".equals(pjtType)){%>
        opener.location.href = "/plm/jsp/project/ProjectView.jsp?oid=<%=oid%>";
        <%}else if("mold".equals(pjtType)){%>
        opener.location.href = "/plm/jsp/project/MoldProjectView.jsp?oid=<%=oid%>";
        <%}%>
        self.close();
</script>

<%
        }
%>

<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.common.content.uploader.FileUploader"%>
<%@page import="e3ps.common.util.CharUtil"%>
<%@page import="e3ps.common.content.E3PSContentHelper"%>
<%@page import="e3ps.common.content.uploader.WBFile"%>
<%@page import="wt.content.ContentRoleType"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="wt.content.ApplicationData"%>
<HTML>
<head>
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/script.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<script language=JavaScript>

// 첨부파일 시작 *****************************************************************************************************************
function fileDown(){
    location.href = "/plm/jsp/project/FileDown.jsp";
}

// 첨부 파일 끝 *****************************************************************************************************************

</script>

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<style type="text/css">
<!--
body {
    margin-top: 12px;
    margin-left:15px;
}
-->
</style>
<title></title>

</head>
<body background="/plm/portal/images/img_common/contents_bg.gif">
<!-------------------------------------- 컨텐츠 시작 //-------------------------------------->
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<form method=POST enctype="multipart/form-data">
<input type=hidden name=oid value="<%=oid%>" >
<input type=hidden name=command>

<table border="0" cellspacing="0" cellpadding="0" width="560">
    <tr>
        <td valign="top">

<!-------------------------------------- 상단 제목 버튼 시작 //-------------------------------------->
            <table border="0" cellpadding="0" cellspacing="0" width="540">
                    <tr>
                        <td>
                            <table width="560" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td background="/plm/portal/images/logo_popupbg.png">
                              <table height="28" border="0" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02552") %><%--제품 프로젝트 표준 양식 등록--%></td>
                                    <td width="10"></td>
                                  </tr>
                              </table>
                              </td>
                              <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                            </tr>
                              </table>
                            <table border="0" cellspacing="0" cellpadding="0" width="560">
                                <tr>
                                    <td class="space10"> </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
            </table>
             <table width="560" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                           <tr>
                          <td>&nbsp;</td>
                           <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="Javascript:doCreateJsp();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                        </tr>
                    </table>
                </td>
              </tr>
            </table>

<!-------------------------------------- 상단 제목 버튼 끝 //-------------------------------------->

            <table border="0" cellpadding="0" cellspacing="0" width="560">
                <tr>
                    <td>

            <!------------------------------ 본문 Start //------------------------------>
                        <table border="0" cellspacing="0" cellpadding="0" >
                            <tr>
                                <td class="space15"> </td>
                            </tr>
                        </table>

                        <table border="0" cellspacing="0" cellpadding="0" width="560">
                              <tr>
                            <td>
                            <%
                                String sFilePath = "/plm/jsp/project/ProjectCard.ppt";
                            %>
                            <a href="<%=sFilePath%>" ><%=messageService.getString("e3ps.message.ket_message", "03011") %><%--표준양식 다운로드--%></a>
                            </td>
                            </tr>
                        </table>

                        <table border="0" cellspacing="0" cellpadding="0" width="560">
                          <tr>
                            <td  class="tab_btm2"></td>
                          </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="560">
                            <!-- tr>
                            <td width="100" class="tdblueL">내용</td>
                            <td class="tdwhiteL0">
                                <textarea name="stopDetil" rows=5 style="width:100%;"><%//=(project.getStopedDetil()==null)?"":project.getStopedDetil() %></textarea>
                            </td>
                            </tr -->
                            <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                                <td class="tdwhiteM0">
                                <input type="file" name="upFile" style="width:100%;"></input>
                                    </td>
                            </tr>
                        </table>
                    </td>
               </tr>
            </table>

        </td>
    </tr>
    <tr>
        <td height="30" valign="bottom">
            <table width="560" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10">&nbsp;</td>
                <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="560" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
              </tr>
            </table>
        </td>
      </tr>
</table>

</form>
</body>
</HTML>

<script>

    function doCreateJsp(){
        if(!checkValidate()){
            return;
        }
        if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "03007") %><%--표준 양식을 등록 하시겠습니까?--%>')) {
            return;
        }
        document.forms[0].command.value = "create";
        document.forms[0].method = "post";
        document.forms[0].submit();

    }
    function checkValidate(){
        //if(document.forms[0].stopDetil.value == ""){
        //	alert("중지 사유를 입력 하십시오.");
        //	return false;
        //}
        if(document.forms[0].upFile.value == ''){
            alert('<%=messageService.getString("e3ps.message.ket_message", "03006") %><%--표준 양식 파일을 선택 해주십시오--%>');
            return;
        }

        return true;
    }



</script>


