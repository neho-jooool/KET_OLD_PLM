<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import = "e3ps.common.code.NumberCode,
                                    e3ps.common.code.NumberCodeHelper,
                                    e3ps.common.util.CommonUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.ParamUtil,
                  e3ps.common.content.*,
                  e3ps.dms.entity.KETStandardTemplate,
                  wt.fc.QueryResult,
                  wt.fc.PersistenceHelper,
                  wt.vc.VersionControlHelper,
                  wt.org.WTUser,
                  wt.content.*,
                                    java.util.Vector"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
    String oid = ParamUtil.getParameter(request, "oid");
    KETStandardTemplate ketStandardTemplate = (KETStandardTemplate)CommonUtil.getObject(oid);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=JavaScript src="../common/content/content.js"></script>

<style type="text/css">
<!--
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
-->
</style>
<script language='javascript'>
<!--

    // 수정화면으로 이동
    function goUpdate(){
        var oid = document.forms[0].oid.value;

        showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/dms/UpdateStandardDoc.jsp?oid='+oid;
    }
   
    // 표준양식 삭제
    function goDelete(){
        if(confirm("<%=messageService.getString("e3ps.message.ket_message", "01707") %><%--삭제하시겠습니까?--%>")){
            showProcessing();
          disabledAllBtn();

          document.forms[0].action = '/plm/servlet/e3ps/StandardDocServlet?cmd=delete';
          document.forms[0].encoding = 'multipart/form-data';
          document.forms[0].submit();
      }
    }

   //표준양식 목록화면 이동
   function goList(){

        showProcessing();
      disabledAllBtn();

      document.SearchListForm.action = '/plm/servlet/e3ps/StandardDocServlet?cmd=search';
      document.SearchListForm.submit();
    }
    
   /* 2014.07.24 */
   function goRevision(){
	   var oid = document.forms[0].oid.value;

       showProcessing();
       disabledAllBtn();
       
       document.forms[0].action = '/plm/servlet/e3ps/StandardDocServlet?cmd=revision&oid='+oid;
       document.forms[0].encoding = 'multipart/form-data';
       document.forms[0].submit();
       
   }
   
   /* 2014.07.24 이력조회팝업 추가 */
   function historySearch(){
       var url = "/plm/jsp/dms/CreateStandardHistorySearch.jsp?oid=<%=oid%>";
       window.open(url, "_blank","width=500, height=300,status=no,scrollbars=yes,resizable=no");
   }
//-->
</script>
</head>
<body class="popup-background02 popup-space">
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03013") %><%--표준양식 상세조회--%></td>
                
              </tr>
            </table>
          </td>
        </tr>
       </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <%  if(CommonUtil.isAdmin()) {  %>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goRevision();" class="btn_blue">개정<%--개정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td> 
                <td width="5">&nbsp;</td> 
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goUpdate('update');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%  }  %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <%-- <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goList();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378") %>목록</a></td> --%>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue">닫기<%--닫기--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
          <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
          <td width="270" class="tdwhiteL">
              <%
                   NumberCode numberCode = NumberCodeHelper.manager.getNumberCode("DIVISIONFLAG", StringUtil.checkNull(ketStandardTemplate.getDivisionCode()));
                   String strNumberCode = null;
                   if ( numberCode != null ){
                        strNumberCode = numberCode.getName();
                   }else {
                        strNumberCode = "공통";
                   }
                %>
                <%=StringUtil.checkNull(strNumberCode)%>
          </td>
          <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00947") %><%--관리부서--%></td>
          <td width="270" class="tdwhiteL0"><%=StringUtil.checkNull(ketStandardTemplate.getDeptCode())%>&nbsp;</td>
        </tr>
        <tr>
          <td class="tdblueL">Rev<%--Rev--%></td>
          <td class="tdwhiteL"><a href="javascript:historySearch();"><%=StringUtil.checkNull(ketStandardTemplate.getVersionInfo().getIdentifier().getValue())%></a></td>
          <td class="tdblueL non-back">최종배포일</td>
          <td class="tdwhiteL0"><%=DateUtil.getDateString(ketStandardTemplate.getModifyTimestamp(), "date")  %></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
          <td colspan="3" class="tdwhiteL0"><%=StringUtil.checkNull(ketStandardTemplate.getTitle())%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02102") %><%--양식설명--%></td>
          <td colspan="3" class="tdwhiteL0" style="height:80">
              <textarea name="docDesc" class="txt_fieldRO" style="width:98%; height:96%" readonly ><%=StringUtil.checkNull(ketStandardTemplate.getTemplateDescription())%></textarea>
          </td>
        </tr>
        <%
                    ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(oid));
                    Vector files = ContentHelper.getContentList(holder);
                %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03008") %><%--표준양식--%></td>
          <td colspan="3" class="tdwhiteL0">
              <%
                  if (files != null && files.size() > 0){
                      for (int inx = 0 ; inx < files.size() ; inx++){
                          ApplicationData appData = (ApplicationData)files.get(inx);
                          String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                          //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                          String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
              %>
                          <a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a><%if(inx<files.size()){%><br><%} %>
              <%  }
                }else if(files == null || files.size() < 1){
              %>
                       &nbsp;
            <%}%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
<form method="post"  name="SearchListForm">
<input type="hidden" name="page" value="">
<input type="hidden" name="divisionCode" value="">
<input type="hidden" name="deptCode" value="">
<input type="hidden" name="docName" value="">
<input type="hidden" name="docDesc" value="">
</form>
</body>
</html>
