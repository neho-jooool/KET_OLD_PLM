<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "wt.fc.PersistenceHelper,
                                    wt.fc.QueryResult,
                                    wt.content.*,
                                    wt.doc.WTDocument,
                                    wt.doc.WTDocumentMaster,
                                    wt.org.WTUser,
                                    wt.session.SessionHelper,
                  e3ps.common.web.ParamUtil,
                                    e3ps.common.util.DateUtil,
                                    e3ps.common.util.CommonUtil,
                                    e3ps.common.util.StringUtil,
                                    e3ps.common.obj.ObjectData,
                                    e3ps.common.obj.ObjectUtil,
                                    e3ps.common.content.*,
                                    e3ps.common.code.NumberCode,
                                    e3ps.common.code.NumberCodeHelper,
                                    e3ps.ews.entity.KETEarlyWarning,
                                    e3ps.ews.entity.KETEarlyWarningResult,
                                    e3ps.ews.entity.KETEarlyWarningResultLink,
                                    e3ps.ews.entity.KETEarlyWarningProblem,
                                    e3ps.ews.entity.KETResultProblemLink,
                                    e3ps.ews.dao.EarlyWarningEndDao,
                                    e3ps.ews.beans.EWSUtil,
                                    java.util.Vector,
                                    java.util.StringTokenizer" %>

<%
    /********************************    ketEarlyWarningResult object    ********************************/

    String resultOid = ParamUtil.getParameter(request, "resultOid");
    KETEarlyWarningResult ketEarlyWarningResult = (KETEarlyWarningResult)CommonUtil.getObject(resultOid);
    // 결재대상 화면 여부
    boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

    /********************************    ketEarlyWarningResult object    ********************************/

    /********************************       ketEarlyWarning object       ********************************/

    QueryResult isEarlyWarning = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningResult.getMaster(), KETEarlyWarningResultLink.ROLE_AOBJECT_ROLE, KETEarlyWarningResultLink.class, false);
    KETEarlyWarningResultLink ketEarlyWarningResultLink = null;
  Object earlyWarningMaster = null;
  ObjectData earlyWarninMasterData = null;

  while(isEarlyWarning.hasMoreElements()){
         ketEarlyWarningResultLink = (KETEarlyWarningResultLink)isEarlyWarning.nextElement();
        earlyWarningMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningResultLink.getRoleAObject());
        earlyWarninMasterData = new ObjectData((WTDocument)earlyWarningMaster);
    }

    String oid = earlyWarninMasterData.getOid();
    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);
    /********************************       ketEarlyWarning object       ********************************/

    EarlyWarningEndDao endDao = new EarlyWarningEndDao();
    String endReqOid = endDao.ViewEndReqId (CommonUtil.getOIDString((WTDocumentMaster)ketEarlyWarning.getMaster()));

    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    WTUser owner = (WTUser)ketEarlyWarningResult.getCreator().getPrincipal();
    boolean isOwner = false;
    if( owner.equals( loginUser ) ) {
        isOwner = true;
    }
    boolean isMember = EWSUtil.isMember(ketEarlyWarning);
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
    margin-top: 20px;
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
        for(var inx = 1 ; inx < 5 ; inx++){
            document.getElementById("tab"+inx).style.display='none';

            document.getElementById("tab"+inx+"_1").src = "../../portal/images/tab_4.png"
            document.getElementById("tab"+inx+"_2").background = "../../portal/images/tab_6.png"
            document.getElementById("tab"+inx+"_3").src = "../../portal/images/tab_5.png"
        }
        document.getElementById(tabType).style.display='block';

        document.getElementById(tabType+"_1").src = "../../portal/images/tab_1.png"
        document.getElementById(tabType+"_2").background = "../../portal/images/tab_3.png"
        document.getElementById(tabType+"_3").src = "../../portal/images/tab_2.png"

        if( tabType == 'tab2' ){
            resizeHeight(document.getElementById('itab2'));
        }
    }

    //iframe 높이 resize
    function resizeHeight(obj){
        var objBody = obj.contentWindow.document.body;
        obj.style.height = objBody.scrollHeight + (objBody.offsetHeight - objBody.clientHeight);
    }

    //실적보고서 수정화면 이동
    function goUpdate(){
        var resultOid = document.forms[0].resultOid.value;

        showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/UpdateEarlyWarningResult.jsp?resultOid='+resultOid;
    }

    //실적보고서 삭제
    function goDelete(){
       if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')){
            showProcessing();
          disabledAllBtn();

          document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningResultServlet?cmd=delete';
          document.forms[0].encoding = 'multipart/form-data';
          document.forms[0].submit();
      }
    }

    //초기유동관리 지정 상세화면 이동
    function goView(){
        var oid = document.forms[0].oid.value;

        showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/ViewEarlyWarning.jsp?oid='+oid;
    }

//-->
</script>
</head>
<body class="<%=(isIframe)?"": "popup-background02"%>">
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='resultOid' value=<%=resultOid%> >
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
        <tr>
          <td>
              <table width="98%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02039") %><%--실적보고서 상세조회--%></td>
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
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"></td>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img id='tab1_1' src="../../portal/images/tab_1.png"></td>
                          <td id='tab1_2' style="padding:8 0 0 0;" background="../../portal/images/tab_3.png"><a href="javascript:goTab('tab1');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01385") %><%--목표 및 실적--%></a></td>
                          <td><img id='tab1_3' src="../../portal/images/tab_2.png"></td>
                        </tr>
                      </table>
                    </td>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img id='tab2_1' src="../../portal/images/tab_4.png"></td>
                          <td id='tab2_2' style="padding:8 0 0 0;"  background="../../portal/images/tab_6.png"><a href="javascript:goTab('tab2');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01747") %><%--상세실적현황--%></a></td>
                          <td><img id='tab2_3' src="../../portal/images/tab_5.png"></td>
                        </tr>
                      </table>
                    </td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img id='tab3_1' src="../../portal/images/tab_4.png"></td>
                          <td id='tab3_2' style="padding:8 0 0 0;" background="../../portal/images/tab_6.png"><a href="javascript:goTab('tab3');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01439") %><%--문제점 List--%></a></td>
                          <td><img id='tab3_3' src="../../portal/images/tab_5.png"></td>
                        </tr>
                      </table>
                    </td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img id='tab4_1' src="../../portal/images/tab_4.png"></td>
                          <td id='tab4_2' style="padding:8 0 0 0;" background="../../portal/images/tab_6.png"><a href="javascript:goTab('tab4');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00877") %><%--공정감사현황--%></a></td>
                          <td><img id='tab4_3' src="../../portal/images/tab_5.png"></td>
                        </tr>
                      </table>
                    </td>
                </tr>
              </table>
          </td>
          <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <% if (isOwner || isMember) { %>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goUpdate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <%   if ( endReqOid == null || endReqOid.equals("")) {  %>
                  <td width="5"></td>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                  </td>
                  <%   }
                     } %>
                  <td width="5"></td>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td> <!-- 기본정보 -->
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goView();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td width="5"></td>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
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
            <td width="10" background="../../portal/images/box_13.gif">&nbsp;</td>
            <td valign="top">

              <!------------------------------------------실적보고서 Tab1 Start----------------------------------->

              <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tab1" style="display:block" >
              <tr>
                <td align="left" valign="top">
                    <iframe src='/plm/jsp/ews/EarlyWarningResultTab1.jsp?oid=<%=oid%>' frameborder="0" style="margin:0;width:100%;" scrolling="no" onload="resizeHeight(this)"></iframe>
                </td>
              </tr>
            </table>

            <!------------------------------------------실적보고서 Tab1 End------------------------------------->

            <!------------------------------------------실적보고서 Tab2 Start----------------------------------->

              <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tab2" style="display:none" >
              <tr>
                <td align="left" valign="top">
                    <iframe id="itab2" src='/plm/jsp/ews/EarlyWarningResultTab2.jsp?oid=<%=oid%>' frameborder="0" style="margin:0;width:100%;" scrolling="no" onload="resizeHeight(this)"></iframe>
                </td>
              </tr>
            </table>

            <!------------------------------------------실적보고서 Tab2 End------------------------------------->

            <!------------------------------------------실적보고서 Tab3 Start----------------------------------->

            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tab3" style="display:none" >
              <tr>
                <td valign="top">
                    <table border="0" cellspacing="0" cellpadding="0" width="100%%">
                    <tr>
                      <td  class="tab_btm2"></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td width="70" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01438") %><%--문제점--%></td>
                      <td class="tdwhiteM0">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td class="space5"></td>
                            </tr>
                          </table>
                        <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                            <col width="3%">
                            <col width="15%">
                            <col width="15%">
                            <col width="19%">
                            <col width="19%">
                            <col width="13%">
                            <col width="16%">
                          <tr>
                            <td class="tdgrayM">No</td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00882") %><%--공정명--%></td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01624") %><%--불량유형--%></td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01438") %><%--문제점--%></td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01235") %><%--대책--%></td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01200") %><%--담당--%></td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                          </tr>
                          <%
                                        QueryResult problemResult = PersistenceHelper.navigate(ketEarlyWarningResult, KETResultProblemLink.ROLE_BOBJECT_ROLE, KETResultProblemLink.class, false);
                                        KETResultProblemLink problemLink = null;
                                        KETEarlyWarningProblem ketEarlyWarningProblem = null;
                                        int problemNo = 1;
                                          if (problemResult != null){
                                             while(problemResult.hasMoreElements()){
                                                 problemLink = (KETResultProblemLink)problemResult.nextElement();
                                                 ketEarlyWarningProblem = (KETEarlyWarningProblem)problemLink.getRoleBObject();
                                                 if ( ketEarlyWarningProblem.getProblemkind() != null && ((String)ketEarlyWarningProblem.getProblemkind()).equals("problem") ){
                                      %>
                          <tr>
                            <td class="tdwhiteM"><%=problemNo++%></td>
                            <td class="tdwhiteLT" valign="top"><%=StringUtil.checkNull(ketEarlyWarningProblem.getProblemstep())%></td>
                            <td class="tdwhiteM">
                                <%
                                   String strParentCode = null;
                                   String strNumberCode = null;

                                   NumberCode parentCode = null;
                                   NumberCode numberCode = null;

                                   int inx = 0;

                                   if (ketEarlyWarningProblem.getProblemtype() != null){
                                        StringTokenizer st = new StringTokenizer(ketEarlyWarningProblem.getProblemtype(), "/");
                                        String[] typelist = new String[st.countTokens()];
                                        while (st.hasMoreTokens()){
                                          typelist[inx] = st.nextToken();
                                                                         inx++;
                                        }

                                        if (typelist.length == 2){
                                          parentCode = NumberCodeHelper.manager.getNumberCode("PROBLEMTYPE", typelist[0]);
                                          numberCode = NumberCodeHelper.manager.getNumberCode("PROBLEMTYPE", typelist[1]);
                                        }else if (typelist.length == 1){
                                             parentCode = NumberCodeHelper.manager.getNumberCode("PROBLEMTYPE", typelist[0]);
                                        }

                                        if ( parentCode != null ){
                                             strParentCode = parentCode.getName();
                                        }
                                        if ( numberCode != null ){
                                          strNumberCode = numberCode.getName();
                                        }
                                   }
                                %>
                                <%=StringUtil.checkNull(strParentCode)%><br><%=StringUtil.checkNull(strNumberCode)%>
                            </td>
                            <td class="tdwhiteM"><textarea name='problemDesc' class='txt_field' style='width:145; height:40;' readonly ><%=StringUtil.checkNull(ketEarlyWarningProblem.getProblemdesc())%></textarea></td>
                            <td class="tdwhiteM"><textarea name='measure' class='txt_field' style='width:145; height:40;' readonly ><%=StringUtil.checkNull(ketEarlyWarningProblem.getMeasure())%></textarea></td>
                            <td class="tdwhiteMT" valign="top">
                                <% if (ketEarlyWarningProblem.getCharge() != null){ %>
                                <%=((WTUser)CommonUtil.getObject(ketEarlyWarningProblem.getCharge())).getFullName()%>
                                <% } %>
                            </td>
                            <td class="tdwhiteMT" valign="top">
                                <% if(ketEarlyWarningProblem.getEnddate() != null){ %>
                                                      <%=DateUtil.getTimeFormat(ketEarlyWarningProblem.getEnddate(),"yyyy-MM-dd")%>
                                                  <% } %>
                                              </td>
                          </tr>
                          <%
                                                 }
                                             }
                                        }
                                      %>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <%
                                            ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(resultOid));
                                            Vector files = ContentHelper.getContentList(holder);
                                        %>
                                <tr>
                                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01439") %><%--문제점 List--%></td>
                                  <td colspan="3" class="tdwhiteL0">
                                      <%
                                          if (files != null && files.size() > 0){
                                              for (int inx = 0 ; inx < files.size() ; inx++){
                                                  ApplicationData appData = (ApplicationData)files.get(inx);
                                                  String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                                                  //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                                  String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
                                            if (appData.getDescription() != null && appData.getDescription().equals("problem")) {
                                      %>
                                                  <a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a><%if(inx<files.size()){%><br><%} %>
                                      <%    }
                                              }
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

            <!------------------------------------------실적보고서 Tab3 End-------------------------------------->

            <!------------------------------------------실적보고서 Tab4 Start----------------------------------->

            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tab4" style="display:none">
              <tr>
                  <td valign="top">
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td  class="tab_btm2"></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td width="60" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00876", "<br>") %><%--공정감사{0}지적사항--%></td>
                      <td class="tdwhiteM0">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td class="space5"></td>
                            </tr>
                          </table>
                        <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                          <tr>
                            <td width="25"  class="tdgrayM">No</td>
                            <td width="40"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
                            <td width="200" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02710") %><%--지적사항--%></td>
                            <td width="200" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01235") %><%--대책--%></td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02651") %><%--조치여부--%></td>
                          </tr>
                          <%
                                        problemResult = PersistenceHelper.navigate(ketEarlyWarningResult, KETResultProblemLink.ROLE_BOBJECT_ROLE, KETResultProblemLink.class, false);
                                        int processNo = 1;
                                          if (problemResult != null){
                                             while(problemResult.hasMoreElements()){
                                                 problemLink = (KETResultProblemLink)problemResult.nextElement();
                                                 ketEarlyWarningProblem = (KETEarlyWarningProblem)problemLink.getRoleBObject();
                                                 if ( ketEarlyWarningProblem.getProblemkind() != null && ((String)ketEarlyWarningProblem.getProblemkind()).equals("process") ){
                                      %>
                          <tr>
                            <td class="tdwhiteM"><%=processNo++%></td>
                            <td class="tdwhiteMT" valign="top">
                                <%
                                   String strParentCode2 = null;
                                   String strNumberCode2 = null;

                                   int inx2 = 0;

                                   if (ketEarlyWarningProblem.getProblemtype() != null){
                                        StringTokenizer st2 = new StringTokenizer(ketEarlyWarningProblem.getProblemtype(), "/");
                                        String[] typelist2 = new String[st2.countTokens()];
                                        while (st2.hasMoreTokens()){
                                          typelist2[inx2] = st2.nextToken();
                                                                         inx2++;
                                        }

                                        if (typelist2.length == 2){
                                          strParentCode2 = typelist2[0];
                                          strNumberCode2 = typelist2[1];
                                        }
                                   }
                                %>
                                <%=StringUtil.checkNull(strNumberCode2)%>
                            </td>
                            <td class="tdwhiteM">
                                <textarea name='problemDesc' class='txt_field' style='width:215; height:40;' readonly ><%=StringUtil.checkNull(ketEarlyWarningProblem.getProblemdesc())%></textarea></td>
                            <td class="tdwhiteM">
                                <textarea name='measure' class='txt_field' style='width:220; height:40;' readonly ><%=StringUtil.checkNull(ketEarlyWarningProblem.getMeasure())%></textarea></td>
                            <td class="tdwhiteMT" valign="top">
                                <% if(ketEarlyWarningProblem.getEnddate() != null){ %>
                                                      <%=DateUtil.getTimeFormat(ketEarlyWarningProblem.getEnddate(),"yyyy-MM-dd")%>
                                                  <% } %>
                                              </td>
                            <td class="tdwhiteMT" valign="top"><%=StringUtil.checkNull(ketEarlyWarningProblem.getIsend())%></td>
                          </tr>
                          <%
                                                 }
                                             }
                                        }
                                      %>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                                  <td colspan="3" class="tdwhiteL0">
                                      <%
                                          if (files != null && files.size() > 0){
                                              for (int inx = 0 ; inx < files.size() ; inx++){
                                                  ApplicationData appData = (ApplicationData)files.get(inx);
                                                  String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                                                  //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                                  String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
                                            if (appData.getDescription() != null && appData.getDescription().equals("process")) {
                                      %>
                                                  <a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a><%if(inx<files.size()){%><br><%} %>
                                      <%    }
                                              }
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

            <!------------------------------------------실적보고서 Tab4 End-------------------------------------->

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
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="740" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
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
