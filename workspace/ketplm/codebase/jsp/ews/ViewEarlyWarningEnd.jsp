<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "wt.fc.PersistenceHelper,
                  wt.fc.QueryResult,
                  wt.doc.WTDocument,
                  wt.doc.WTDocumentMaster,
                  wt.content.*,
                  wt.org.WTUser,
                  wt.session.SessionHelper,
                  e3ps.common.web.ParamUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.obj.ObjectData,
                  e3ps.common.obj.ObjectUtil,
                  e3ps.common.content.*,
                  e3ps.ews.entity.KETEarlyWarning,
                  e3ps.ews.entity.KETEarlyWarningEndReqLink,
                  e3ps.ews.entity.KETEarlyWarningEndReq,
                  e3ps.ews.entity.KETEndReqLink,
                  e3ps.ews.entity.KETEarlyWarningEnd,
                  e3ps.ews.entity.KETEarlyWarningStepLink,
                  e3ps.ews.entity.KETEarlyWarningStep,
                  e3ps.wfm.util.WorkflowSearchHelper,
                  java.util.Vector" %>

<%
  /********************************    ketEarlyWarningEndReq object    ********************************/

  String endReqOid = ParamUtil.getParameter(request, "endReqOid");
  KETEarlyWarningEndReq ketEarlyWarningEndReq = (KETEarlyWarningEndReq)CommonUtil.getObject(endReqOid);
  // 결재대상 화면 여부
  boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

  /********************************    ketEarlyWarningEndReq object    ********************************/

  /********************************      ketEarlyWarningEnd object     ********************************/

  String endOid = ParamUtil.getParameter(request, "endOid");
  KETEarlyWarningEnd ketEarlyWarningEnd = null;

  if (endOid != null && !endOid.equals("")){
    ketEarlyWarningEnd = (KETEarlyWarningEnd)CommonUtil.getObject(endOid);
  }else {
    QueryResult isEnd = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningEndReq.getMaster(), KETEndReqLink.ROLE_BOBJECT_ROLE, KETEndReqLink.class, false);
    KETEndReqLink ketEndReqLink = null;
    Object endMaster = null;
    ObjectData endMasterData = null;

    if (isEnd != null && isEnd.size() != 0){
      while(isEnd.hasMoreElements()){
        ketEndReqLink = (KETEndReqLink)isEnd.nextElement();
         endMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEndReqLink.getRoleBObject());
         endMasterData = new ObjectData((WTDocument)endMaster);
      }

      endOid = endMasterData.getOid();
      ketEarlyWarningEnd = (KETEarlyWarningEnd)CommonUtil.getObject(endOid);
    }
  }

  /********************************      ketEarlyWarningEnd object     ********************************/

  /********************************        ketEarlyWarning object      ********************************/

  QueryResult isEarlyWarning = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningEndReq.getMaster(), KETEarlyWarningEndReqLink.ROLE_AOBJECT_ROLE, KETEarlyWarningEndReqLink.class, false);
  KETEarlyWarningEndReqLink ketEarlyWarningEndReqLink = null;
  Object earlyWarningMaster = null;
  ObjectData earlyWarninMasterData = null;

  while(isEarlyWarning.hasMoreElements()){
     ketEarlyWarningEndReqLink = (KETEarlyWarningEndReqLink)isEarlyWarning.nextElement();
    earlyWarningMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningEndReqLink.getRoleAObject());
    earlyWarninMasterData = new ObjectData((WTDocument)earlyWarningMaster);
  }

  String oid = earlyWarninMasterData.getOid();
  KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);

  /********************************        ketEarlyWarning object      ********************************/

  /********************************       ketEarlyWarningStep object       ********************************/

  QueryResult isEarlyWarningStep = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningStepLink.ROLE_AOBJECT_ROLE, KETEarlyWarningStepLink.class, false);
  KETEarlyWarningStepLink ketEarlyWarningStepLink = null;
  Object earlyWarningStepMaster = null;
  ObjectData earlyWarninStepMasterData = null;

  while(isEarlyWarningStep.hasMoreElements()){
    ketEarlyWarningStepLink = (KETEarlyWarningStepLink)isEarlyWarningStep.nextElement();
    earlyWarningStepMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningStepLink.getRoleAObject());
    earlyWarninStepMasterData = new ObjectData((WTDocument)earlyWarningStepMaster);
  }

  String stepOid = earlyWarninStepMasterData.getOid();
  KETEarlyWarningStep ketEarlyWarningStep = (KETEarlyWarningStep)CommonUtil.getObject(stepOid);
  String ewsStep = ketEarlyWarningStep.getLifeCycleState().toString();

  /********************************       ketEarlyWarningStep object       ********************************/

  String isEnd = ParamUtil.getParameter(request, "isEnd");
  String isPopup = ParamUtil.getParameter(request, "isPopup");
  String isTodo = ParamUtil.getParameter(request, "todo");

  WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
  WTUser owner = null;
  boolean isOwner = false;
  if (ketEarlyWarningEnd != null){
    owner = (WTUser)ketEarlyWarningEnd.getCreator().getPrincipal();
    if( owner.equals( loginUser ) ) {
      isOwner = true;
    }
  }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
img {   
    vertical-align:baseline;
}
</style>
<script type="text/javascript">
<!--

  // 탭이동
  function goTab(tabType) {
    if (tabType == '1'){
      document.getElementById("tab1").style.display='block';
      document.getElementById("tab2").style.display='none';

      document.getElementById("tab1_1").src = "../../portal/images/tab_1.png";
      document.getElementById("tab1_2").background = "../../portal/images/tab_3.png";
      document.getElementById("tab1_3").src = "../../portal/images/tab_2.png";

      document.getElementById("tab2_1").src = "../../portal/images/tab_4.png";
      document.getElementById("tab2_2").background = "../../portal/images/tab_6.png";
      document.getElementById("tab2_3").src = "../../portal/images/tab_5.png";
    }else if (tabType == '2'){
      document.getElementById("tab1").style.display='none';
      document.getElementById("tab2").style.display='block';

      document.getElementById("tab2_1").src = "../../portal/images/tab_1.png";
      document.getElementById("tab2_2").background = "../../portal/images/tab_3.png";
      document.getElementById("tab2_3").src = "../../portal/images/tab_2.png";

      document.getElementById("tab1_1").src = "../../portal/images/tab_4.png";
      document.getElementById("tab1_2").background = "../../portal/images/tab_6.png";
      document.getElementById("tab1_3").src = "../../portal/images/tab_5.png";
    }
  }

  //iframe 높이 resize
  function resizeHeight(obj){
    var objBody = obj.contentWindow.document.body;
    obj.style.height = objBody.scrollHeight + (objBody.offsetHeight - objBody.clientHeight);
  }

  //해제신청서 이력목록화면 이동
  function goEndList(){
    var oid = document.forms[0].oid.value;

    var url="../../jsp/ews/SearchEarlyWarningEnd.jsp?oid="+oid;
    openWindow(url,"","500","350","status=1,scrollbars=no,resizable=no");
  }

  //해제판정 수정화면 이동
  function goUpdate(){
    var endOid = document.forms[0].endOid.value;

    showProcessing();
    disabledAllBtn();

    window.location= '/plm/jsp/ews/UpdateEarlyWarningEnd.jsp?endOid='+endOid;
  }

  //해제신청서 삭제
  function goDelete(){
    if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')){
      showProcessing();
      disabledAllBtn();

      document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningEndServlet?cmd=delete';
      document.forms[0].encoding = 'multipart/form-data';
      document.forms[0].submit();
    }
  }

//-->
</script>
</head>
<% if (isEnd != null && isEnd.equals("Y")){ %>
<body class="<%=(isIframe)?"": "popup-background02 popup-space02"%>" onload="goTab('2')">
<% }else { %>
<body class="<%=(isIframe)?"": "popup-background02 popup-space02"%>">
<% } %>
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='endReqOid' value=<%=endReqOid%> >
<input type='hidden' name='endOid' value=<%=endOid%> >
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%> >
        <tr>
          <td>
            <table width="760" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03195") %><%--해제신청서 상세조회--%></td>
                
              </tr>
            </table>
          </td>
        </tr>
       <tr>
          <td  style="height: 5px;"></td>
        </tr>
       <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5"></td>
                <td width="5"></td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img id='tab1_1' src="../../portal/images/tab_1.png"></td>
                          <td id='tab1_2' background="../../portal/images/tab_3.png"><a href="javascript:goTab('1');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03192") %><%--해제신청--%></td>
                      <td><img id='tab1_3' src="../../portal/images/tab_2.png"></td>
                    </tr>
                  </table>
                </td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0" <%if(ketEarlyWarningEnd == null)out.print("style='display:none'");%> >
                    <tr>
                      <td><img id='tab2_1' src="../../portal/images/tab_4.png"></td>
                          <td id='tab2_2' background="../../portal/images/tab_6.png"><a href="javascript:goTab('2');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03198") %><%--해제판정--%></a></td>
                      <td><img id='tab2_3' src="../../portal/images/tab_5.png"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0" <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%> >
              <tr>
                <% if( isTodo != null && isTodo.equals("Y")){ %>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--목록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <% }else{ %>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goEndList();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02030") %><%--신청이력--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td><!-- 기본정보 -->
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goBack();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--목록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <% } %>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_9.gif"></td>
          <td height="10" background="../../portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="../../portal/images/box_13.gif" style="background-attachment: fixed;">&nbsp;</td>
          <td valign="top">

            <!------------------------------------------해제신청서 Tab1 Start----------------------------------->

            <table id="tab1" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:block">
              <tr>
                <td align="left" valign="top">
                  <iframe src='/plm/jsp/ews/ViewEarlyWarningEndReq.jsp?endReqOid=<%=endReqOid%>&endOid=<%=endOid%>&todo=<%=isTodo%>&isPopup=<%=isPopup%>' frameborder="0" style="margin:0;width:100%;" scrolling="no" onload="resizeHeight(this)"></iframe>
                </td>
              </tr>
            </table>

             <!------------------------------------------해제신청서 Tab1 End-------------------------------------->

             <!------------------------------------------해제신청서 Tab2 Start-------------------------------------->

             <%  if(ketEarlyWarningEnd != null){  %>
             <table id="tab2" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none">
            <tr>
              <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%> >
                  <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                      <table border="0" cellspacing="0" cellpadding="0" >
                        <tr>
                          <% String state = ketEarlyWarningEnd.getLifeCycleState().toString(); %>
                          <% if (state != null && (state.equals("INWORK") || state.equals("REWORK")) && isOwner) { %>
                          <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goUpdate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
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
                          <%    if ( ewsStep != null && !ewsStep.equals("EWRCANCELED") ){ %>
                          <td width="5">&nbsp;</td>
                          <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goPage('<%=endOid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                          </td>
                          <%    } %>
                          <% } %>
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
                <colgroup>
                 <col width="17%">
                 <col width="33%">
                 <col width="17%">
                 <col width="33%">
                </colgroup>
                  <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
                    <td class="tdwhiteL"><%=ketEarlyWarning.getNumber()%></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
                    <td class="tdwhiteL0"><a href="javascript:viewHistory('<%=endOid%>');"><%=ketEarlyWarningEnd.getLifeCycleState().getDisplay(messageService.getLocale())%></a></td>
                  </tr>
                  <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                    <td class="tdwhiteL"><%=ketEarlyWarningEnd.getCreatorFullName()%></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                    <td class="tdwhiteL0"><%=DateUtil.getTimeFormat(ketEarlyWarningEnd.getCreateTimestamp(),"yyyy-MM-dd")%></td>
                  </tr>
                  <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01952") %><%--수정자--%></td>
                    <td class="tdwhiteL"><%=ketEarlyWarningEnd.getModifierFullName()%></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
                    <td class="tdwhiteL0"><%=DateUtil.getTimeFormat(ketEarlyWarningEnd.getModifyTimestamp(),"yyyy-MM-dd")%></td>
                  </tr>
                  <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                      <td class="tdwhiteL">
                        <% if (state != null && !state.equals("INWORK")) {
                              if (WorkflowSearchHelper.manager.getLastApprover(ketEarlyWarningEnd) != null) {%>
                                <%=WorkflowSearchHelper.manager.getLastApprover(ketEarlyWarningEnd).getFullName()%>
                        <%   }
                           } %>
                        &nbsp;
                                        </td>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                      <td class="tdwhiteL0">
                        <% if (state != null && !state.equals("INWORK")) { %>
                          <%=WorkflowSearchHelper.manager.getLastApprovalDate(ketEarlyWarningEnd)%>
                        <% } %>
                        &nbsp;
                      </td>
                  </tr>
                  <tr>
                    <td height="27" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03197") %><%--해제여부--%></td>
                    <td colspan="3" class="tdwhiteL0">
                      <% if (ketEarlyWarningEnd.getEndresult().equals("Y")){ %>
                         <%=messageService.getString("e3ps.message.ket_message", "03191") %><%--해제--%>
                        <% } else { %>
                      <%=messageService.getString("e3ps.message.ket_message", "01112") %><%--기간연장--%> [ <%=DateUtil.getTimeFormat(ketEarlyWarningEnd.getExtensiondate(),"yyyy-MM-dd")%> ]
                      <% } %>
                    </td>
                  </tr>
                  <tr>
                    <td class="tdblueL" style="height:100"><%=messageService.getString("e3ps.message.ket_message", "02131") %><%--연장사유--%></td>
                    <td colspan="3" class="tdwhiteM0" style="height:100">
                      <textarea class="txt_field" style="width:100%; height:96%" readonly ><%=StringUtil.checkNull(ketEarlyWarningEnd.getReason())%></textarea>
                    </td>
                  </tr>
                  <%
                    ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(endOid));
                    Vector files = ContentHelper.getContentList(holder);
                  %>
                  <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00887") %><%--공정점검{0} 결과보고서--%></td>
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
          </table>
          <%  }  %>

          <!------------------------------------------해제신청서 Tab2 End-------------------------------------->

          </td>
          <td width="10" background="../../portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_11.gif"></td>
          <td height="10" background="../../portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_12.gif"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr <%if(isPopup != null && isPopup.equals("Y"))out.print("style='display:none'");%> >
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
<form method="post"  name="SearchListForm">
<input type="hidden" name="page" value="">
<input type="hidden" name="ewsno" value="">
<input type="hidden" name="pjtno" value="">
<input type="hidden" name="partno" value="">
<input type="hidden" name="partname" value="">
<input type="hidden" name="inout" value="">
<input type="hidden" name="production" value="">
<input type="hidden" name="fstcharge" value="">
<input type="hidden" name="step" value="">
<input type="hidden" name="creator" value="">
<input type="hidden" name="createdate" value="">
</form>
</body>
</html>
