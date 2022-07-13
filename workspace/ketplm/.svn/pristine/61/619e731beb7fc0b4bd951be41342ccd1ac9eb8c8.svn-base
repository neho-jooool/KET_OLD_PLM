<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "wt.fc.PersistenceHelper,
                                    wt.fc.QueryResult,
                                    wt.doc.WTDocument,
                  wt.doc.WTDocumentMaster,
                  wt.content.*,
                  e3ps.common.web.ParamUtil,
                                    e3ps.common.util.DateUtil,
                                    e3ps.common.util.CommonUtil,
                                    e3ps.common.util.StringUtil,
                                    e3ps.common.obj.ObjectData,
                                    e3ps.common.obj.ObjectUtil,
                                    e3ps.common.content.*,
                                    e3ps.ews.entity.KETEarlyWarning,
                                    e3ps.ews.entity.KETEarlyWarningEndReq,
                                    e3ps.ews.entity.KETEarlyWarningEndReqLink,
                                    e3ps.ews.entity.KETEarlyWarningEnd,
                                    e3ps.ews.entity.KETEndReqLink,
                                    java.util.Vector" %>

<%
    /********************************     ketEarlyWarningEnd object      ********************************/

    String endOid = ParamUtil.getParameter(request, "endOid");
    KETEarlyWarningEnd ketEarlyWarningEnd = (KETEarlyWarningEnd)CommonUtil.getObject(endOid);

    /********************************     ketEarlyWarningEnd object      ********************************/

    /********************************    ketEarlyWarningEndReq object    ********************************/

    QueryResult isEndReq = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningEnd.getMaster(), KETEndReqLink.ROLE_AOBJECT_ROLE, KETEndReqLink.class, false);
    KETEndReqLink ketEndReqLink = null;
  Object endReqMaster = null;
  ObjectData endReqMasterData = null;

  while(isEndReq.hasMoreElements()){
         ketEndReqLink = (KETEndReqLink)isEndReq.nextElement();
        endReqMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEndReqLink.getRoleAObject());
        endReqMasterData = new ObjectData((WTDocument)endReqMaster);
    }

    String endReqOid = endReqMasterData.getOid();
    KETEarlyWarningEndReq ketEarlyWarningEndReq = (KETEarlyWarningEndReq)CommonUtil.getObject(endReqOid);

    /********************************    ketEarlyWarningEndReq object    ********************************/

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
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=JavaScript src='../common/content/content.js'></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
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

    //해제결과에 따라 연장일 활성/비활성 결정
  function changeDecision(param){
      if (param == "Y"){
          deleteValue("extensionDate");

          document.getElementById("extensionBtn1").disabled = true;
          document.getElementById("extensionBtn2").disabled = true;

      } else {
          document.getElementById("extensionBtn1").disabled = false;
          document.getElementById("extensionBtn2").disabled = false;
      }
  }

    //해제판정 저장
    function save(){
        showProcessing();
      disabledAllBtn();

      document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningEndServlet?cmd=update';
      document.forms[0].encoding = 'multipart/form-data';
      document.forms[0].submit();
    }

    //해제판정 상세화면 이동
    function goView(){
        if(confirm("<%=messageService.getString("e3ps.message.ket_message", "01950") %><%--수정을 취소하시겠습니까?--%>")){
            var endReqOid = document.forms[0].endReqOid.value;

            showProcessing();
          disabledAllBtn();

          window.location= '/plm/jsp/ews/ViewEarlyWarningEnd.jsp?endReqOid='+endReqOid;
        }
    }

//-->
</script>
</head>
<body onload="goTab('2')" class="popup-background02 popup-space02">
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='endReqOid' value=<%=endReqOid%> >
<input type='hidden' name='endOid' value=<%=endOid%> >
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
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03200") %><%--해제판정 수정--%></td>
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
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10"></td>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img id='tab1_1' src="../../portal/images/tab_4.png"></td>
                <td id='tab1_2' background="../../portal/images/tab_6.png"><a href="javascript:goTab('1');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03192") %><%--해제신청--%></a></td>
                <td><img id='tab1_3' src="../../portal/images/tab_5.png""></td>
              </tr>
            </table>
          </td>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img id='tab2_1' src="../../portal/images/tab_1.png"></td>
                <td id='tab2_2' background="../../portal/images/tab_3.png"><a href="javascript:goTab('2');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03198") %><%--해제판정--%></a></td>
                <td><img id='tab2_3' src="../../portal/images/tab_2.png"></td>
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
                    <iframe src='/plm/jsp/ews/ViewEarlyWarningEndReq.jsp?endReqOid=<%=endReqOid%>&isEnd=Y' frameborder="0" style="margin:0;width:'100%';" scrolling="no" onload="resizeHeight(this)"></iframe>
                </td>
              </tr>
            </table>

               <!------------------------------------------해제신청서 Tab1 End-------------------------------------->

               <!------------------------------------------해제신청서 Tab2 Start-------------------------------------->

              <table id="tab2" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none">
              <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td>&nbsp;</td>
                      <td align="right">
                          <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goView();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goList('update');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378") %><%--목록--%></a></td>
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
                      <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
                      <td width="630" class="tdwhiteL0"><%=ketEarlyWarning.getNumber()%></td>
                    </tr>
                    <tr>
                      <td height="27" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03197") %><%--해제여부--%><span class="red">*</span></td>
                      <td class="tdwhiteL0">
                          <table border="0" cellspacing="0" cellpadding="0" >
                          <tr>
                            <td width="139">
                                <input name="endResult" type="radio" class="Checkbox" value="Y" onClick="javascript:changeDecision('Y');"
                                    <%if(ketEarlyWarningEnd.getEndresult().equals("Y"))out.print("checked");%> > <%=messageService.getString("e3ps.message.ket_message", "03191") %><%--해제--%>
                              <input name="endResult" type="radio" class="Checkbox" value="N" onClick="javascript:changeDecision('N');"
                                  <%if(ketEarlyWarningEnd.getEndresult().equals("N"))out.print("checked");%> > <%=messageService.getString("e3ps.message.ket_message", "01112") %><%--기간연장--%>
                            </td>
                            <td>
                            <% if (ketEarlyWarningEnd.getEndresult().equals("Y")) { %>
                                <input type="text" id="extensionDate" name="extensionDate" class="txt_fieldRO" style="width:100" readonly >
                                &nbsp;<img id="extensionBtn1" src="../../portal/images/icon_6.png" border="0" onclick="javascript:showCal(extensionDate);" style="cursor:hand;" disabled>
                              &nbsp;<img id="extensionBtn2" src="../../portal/images/icon_delete.gif" border="0" onclick="javascript:deleteValue('extensionDate');" style="cursor:hand;" disabled>
                            <% }else { %>
                                <input type="text" id="extensionDate" name="extensionDate" class="txt_fieldRO" style="width:100" value=<%=DateUtil.getTimeFormat(ketEarlyWarningEnd.getExtensiondate(),"yyyy-MM-dd")%> readonly >
                                &nbsp;<img id="extensionBtn1" src="../../portal/images/icon_6.png" border="0" onclick="javascript:showCal(extensionDate);" style="cursor:hand;" >
                              &nbsp;<img id="extensionBtn2" src="../../portal/images/icon_delete.gif" border="0" onclick="javascript:deleteValue('extensionDate');" style="cursor:hand;" >
                            <% } %>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td class="tdblueL" style="height:100"><%=messageService.getString("e3ps.message.ket_message", "02131") %><%--연장사유--%></td>
                      <td class="tdwhiteM0" style="height:100">
                          <textarea name="reason" class="txt_field" style="width:100%; height:96%"><%=StringUtil.checkNull(ketEarlyWarningEnd.getReason())%></textarea>
                      </td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00887", "<br>") %><%--공정점검{0} 결과보고서--%></td>
                      <td class="tdwhiteM0">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0"  id="fileTable">
                                                    <tr>
                                                        <td></td>
                                                        <td align="right">
                                                            <table>
                                                                <tr>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertSecondaryFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                        </tr>
                                                      </table>
                                                    </td>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteSecondaryFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                          <td width="3">&nbsp;</td>
                                                        </tr>
                                                      </table>
                                                    </td>
                                                  </tr>
                                                </table>
                                                        </td>
                                                    </tr>
                                                    <tr id="fileTableRow" style="display:none">
                                                        <td align="center" valign="middle" width="20" height="22">
                                                            <input type="checkbox" name="fileDelete">
                                                        </td>
                                                        <td>
                                                            <input type="file" name="filePath" id=input onchange='isValidSecondarySize(this)' onKeyDown="this.blur()" style="ime-mode:disabled" class="txt_fieldRO" size="100">
                                                        </td>
                                                    </tr>
                                                    <%
                                                        ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(endOid));
                                                        Vector files = ContentHelper.getContentList(holder);
                                                        if (files != null && files.size() > 0){
                                                  for (int inx = 0 ; inx < files.size() ; inx++){
                                                      ApplicationData appData = (ApplicationData)files.get(inx);
                                                      String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                                                      //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                                      String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
                                                    %>
                                                            <tr>
                                                                <td align="left" width="20" height="22"><input type="checkbox" name="fileDelete"></td>
                                                                <td align="left"><input type="hidden" name="existedFile" value="<%=appDataOid%>">&nbsp;
                                                                    <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;<a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a></td>
                                                            </tr>
                                                    <%
                                                  }
                                            }
                                          %>
                                                </table>
                                    <table border="0" cellspacing="0" cellpadding="0" width="600">
                                      <tr>
                                        <td></td>
                                      </tr>
                                    </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>

            <!------------------------------------------해제신청서 Tab2 End-------------------------------------->

          </td>
          <td width="10" background="../../portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_11.gif"></td>
          <td height="10" background="../../portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_12.gif"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
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
