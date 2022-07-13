<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "java.util.StringTokenizer,
                  java.util.Vector,
                  java.util.Hashtable,
                  java.util.ArrayList,
                  wt.fc.PersistenceHelper,
                  wt.fc.QueryResult,
                  wt.vc.VersionControlHelper,
                  wt.org.WTUser,
                  wt.content.*,
                  wt.doc.WTDocument,
                  wt.doc.WTDocumentMaster,
                  e3ps.common.web.ParamUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.obj.ObjectUtil,
                  e3ps.common.obj.ObjectData,
                  e3ps.common.content.*,
                  e3ps.groupware.company.People,
                  e3ps.groupware.company.PeopleHelper,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.ews.beans.EWSHelper,
                  e3ps.ews.entity.KETEarlyWarning,
                  e3ps.ews.entity.KETEarlyWarningPlan,
                  e3ps.ews.entity.KETEarlyWarningPlanLink" %>

<%
  String cmd = ParamUtil.getParameter(request, "cmd");

  /********************************     ketEarlyWarningPlan object     ********************************/

  String planOid = ParamUtil.getParameter(request, "planOid");
  KETEarlyWarningPlan ketEarlyWarningPlan = (KETEarlyWarningPlan)CommonUtil.getObject(planOid);

  /********************************     ketEarlyWarningPlan object     ********************************/

  /********************************       ketEarlyWarning object       ********************************/

  QueryResult isEarlyWarning = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningPlan.getMaster(), KETEarlyWarningPlanLink.ROLE_AOBJECT_ROLE, KETEarlyWarningPlanLink.class, false);
  KETEarlyWarningPlanLink ketEarlyWarningPlanLink = null;
  Object earlyWarningMaster = null;
  ObjectData earlyWarninMasterData = null;

  while(isEarlyWarning.hasMoreElements()){
     ketEarlyWarningPlanLink = (KETEarlyWarningPlanLink)isEarlyWarning.nextElement();
    earlyWarningMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningPlanLink.getRoleAObject());
    earlyWarninMasterData = new ObjectData((WTDocument)earlyWarningMaster);
  }

  String oid = earlyWarninMasterData.getOid();
  KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);

  /********************************       ketEarlyWarning object       ********************************/

  String isTodo = ParamUtil.getParameter(request, "todo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=JavaScript src='../common/content/content.js'></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>
<style type="text/css">
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 10px;
  margin-bottom: 5px;
} 
</style>
<script language='javascript'>
<!--

  //사용자 셋팅
  function acceptMember(objArr) {
    if(objArr.length == 0) {
      return;
    }

    var nonUserArr = new Array();
    var cftMember = document.forms[0].cftMember;
    var flag = false;
    var listLength;

    for(var i = 0; i < objArr.length; i++) {
      infoArr = objArr[i];
      flag = false;

      for(var j = 0; j < cftMember.length; j++){
        if(infoArr[0] == cftMember.options[j].value){
          flag = true;
        }
      }

      if(!flag){
        if( infoArr[0] == '<%=ketEarlyWarning.getFstCharge()%>' || infoArr[0] == '<%=ketEarlyWarning.getSndCharge()%>'){
          alert(infoArr[4] + "<%=messageService.getString("e3ps.message.ket_message", "00029") %><%--{0}님은 수행담당자입니다--%>");
        }else{
          listLength = document.forms[0].cftMember.length;
          document.forms[0].cftMember.length += 1;
          cftMember.options[listLength].text = infoArr[5]+" / "+infoArr[6]+" / "+infoArr[4];
          cftMember.options[listLength].value = infoArr[0];
        }
      }else{
        alert(infoArr[4] + "<%=messageService.getString("e3ps.message.ket_message", "00030") %><%--{0}님은 이미 추가되었습니다--%>");
      }
    }
  }

  //활동멤버 삭제
  function deleteMember() {
    var idx = 0;
    var len = document.forms[0].cftMember.length;

    for (var inx = 0 ; inx < len ; inx++){
      if (document.forms[0].cftMember.options[inx-idx].selected){
        document.forms[0].cftMember.remove(inx-idx);
        idx++;
      }
    }
  }

  //초기유동관리 계획서 저장
  function save(){
    var flag = false;
    var fileTable = document.getElementById("fileTable");

    for (var inx = 1 ; inx < fileTable.rows.length ; inx++){
      if (fileTable.rows[inx].cells[1].children[0].value != ""){
        flag = true;
      }
    }

    if (!flag){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00813") %><%--계획서 첨부파일을 등록하십시오--%>");
      return;
    }

    for (var inx = 0 ; inx < document.forms[0].cftMember.length ; inx++){
      document.forms[0].cftMember.options[inx].selected = true;
    }

    showProcessing();
    disabledAllBtn();

    var cmd = document.forms[0].cmd.value;
    var oid = document.forms[0].oid.value;
    var planOid = document.forms[0].planOid.value;

    document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningPlanServlet?cmd='+cmd +'&oid='+oid+'&planOid'+planOid;
    document.forms[0].encoding = 'multipart/form-data';
    document.forms[0].submit();
  }

  //초기유동관리 계획서 상세화면 이동
  function goView(){
    //
    if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01950") %><%--수정을 취소하시겠습니까?--%>')){
      var oid = document.forms[0].oid.value;
      var planOid = document.forms[0].planOid.value;

      showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/ViewEarlyWarningPlan.jsp?oid='+oid+'&planOid='+planOid;
    }
  }

//-->
</script>
</head>
<body class="popup-background02 popup-space">
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='planOid' value=<%=planOid%> >
<input type='hidden' name='cmd' value=<%=cmd%> >
<input type='hidden' name='isTodo' value=<%=isTodo%> >
<!-- hidden end -->
<%  String title = (cmd.equals("update"))?messageService.getString("e3ps.message.ket_message", "01936")/*수정*/:messageService.getString("e3ps.message.ket_message", "00684")/*개정*/;  %>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03378") %><%--초기유동관리 계획서--%>  <%=title%></td>
                
              </tr>
            </table>
          </td>
        </tr>
        
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
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
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif">
                        <a href="javascript:goView();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <!--  
                        <% if( isTodo != null && isTodo.equals("Y")){ %>
                          <a href="javascript:goTodoList('update');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a>
                        <% }else{ %>
                          <a href="javascript:goView();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                        <% } %>
                      -->
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <% if( isTodo == null || !isTodo.equals("Y")){ %>
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
            </table></td>
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
        <col width="15%">
        <col width="35%">
        <col width="15%">
        <col width="35%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
          <td class="tdwhiteL"><%=ketEarlyWarning.getNumber()%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td class="tdwhiteL0">
            <%
              int version = 0;
              if (cmd.equals("update")){
                version = Integer.parseInt(VersionControlHelper.getVersionIdentifier(ketEarlyWarningPlan).getSeries().getValue());
              } else {
                version = Integer.parseInt(VersionControlHelper.getVersionIdentifier(ketEarlyWarningPlan).getSeries().getValue()) + 1;
              }
            %>
            <a href="javascript:viewVersionHistory('<%=planOid%>');"><%=version%></a></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
          <td class="tdwhiteL">
            <a href="javascript:viewProjectPopup('<%=EWSUtil.ViewPjtOid(ketEarlyWarning.getPjtNo())%>');"><%=ketEarlyWarning.getPjtNo()%></a>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
          <td class="tdwhiteL0">
            <a href="javascript:viewProjectPopup('<%=EWSUtil.ViewPjtOid(ketEarlyWarning.getPjtNo())%>');"><%=EWSUtil.ViewPjtName(ketEarlyWarning.getPjtNo())%></a>&nbsp;
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
          <td colspan="3" class="tdwhiteL0"><%=EWSUtil.ViewInout(ketEarlyWarning)%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03238") %><%--활동기간--%></td>
          <td class="tdwhiteL">
            <%=DateUtil.getTimeFormat(ketEarlyWarning.getStartDate(),"yyyy-MM-dd")%>&nbsp;~&nbsp;
            <%=DateUtil.getTimeFormat(ketEarlyWarning.getEndDate(),"yyyy-MM-dd")%>&nbsp;
            <%
               String startDate = DateUtil.getTimeFormat(ketEarlyWarning.getStartDate(), "yyyyMMdd");
               String endDate = DateUtil.getTimeFormat(ketEarlyWarning.getEndDate(), "yyyyMMdd");
            %>
            (<%=messageService.getString("e3ps.message.ket_message", "00138", DateUtil.getDaysFromTo(endDate,startDate)) %><%--{0}일--%>)
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00811") %><%--계획서 제출기한--%></td>
          <td class="tdwhiteL0">
            <% if(ketEarlyWarning.getPlanDate() != null){ %>
              <%=DateUtil.getTimeFormat(ketEarlyWarning.getPlanDate(),"yyyy-MM-dd")%>
            <% } %>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
          <td colspan="3" class="tdwhiteM0">
            <table border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="80" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td width="165" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <td rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00833") %><%--고객불량--%><br>(PPM)</td>
                <td rowspan="2" class="tdgrayM">
                  <% if (ketEarlyWarning.getInOut().equals("i")) { %>
                        <%=messageService.getString("e3ps.message.ket_message", "00883") %><%--공정불량--%>
                  <% }else { %>
                       <%=messageService.getString("e3ps.message.ket_message", "01934") %><%--수입불량--%>
                  <% } %>
                  <br>(PPM)</td>
                <td colspan="4" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01785") %><%--생산성(%)--%></td>
              </tr>
              <tr>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02002") %><%--시간가동--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01905") %><%--성능가동--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02103") %><%--양품율--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02665") %><%--종합효율--%></td>
              </tr>
              <%
                ArrayList partList = EWSHelper.manager.getPartTarget(ketEarlyWarning);
                Hashtable<String, String> part = new Hashtable<String, String>();

                for ( int inx = 0; inx < partList.size() ; inx++ ) {
                  part = (Hashtable)partList.get(inx);
              %>
                  <tr>
                    <td class="tdwhiteM"><%=(String)part.get("partNo")%></td>
                    <td class="tdwhiteL"><%=(String)part.get("partName")%></td>
                    <td class="tdwhiteR" style="word-break:break-all;"><%=(String)part.get("cusError")%></td>
                    <td class="tdwhiteR" style="word-break:break-all;"><%=(String)part.get("workError")%></td>
                    <td class="tdwhiteR"><%=(String)part.get("facility")%></td>
                    <td class="tdwhiteR"><%=(String)part.get("perform")%></td>
                    <td class="tdwhiteR"><%=(String)part.get("good")%></td>
                    <td class="tdwhiteR"><%=(String)part.get("targetTotal")%></td>
                  </tr>
              <%}%>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03242") %><%--활동멤버--%></td>
          <td colspan="3" class="tdwhiteL0" valign="top">
            <select name="cftMember" style="width:270" size='3' multiple >
              <%
                if (ketEarlyWarningPlan.getCftMember() != null){
                  String cftMember = null;
                  People people = null;
                  String dept = null;
                  String duty = null;
                  String memberName = null;
                  StringTokenizer st = new StringTokenizer(ketEarlyWarningPlan.getCftMember(), "/");
                  while(st.hasMoreTokens()) {
                    cftMember = st.nextToken();
                    people = PeopleHelper.manager.getPeople(((WTUser)CommonUtil.getObject(cftMember)).getName());
                    if(people.getDepartment() == null) {
                       dept = messageService.getString("e3ps.message.ket_message", "03147")/*한국단자*/;
                    }else {
                       dept = people.getDepartment().getName();
                    }
                    duty = people.getDuty();
                    memberName = ((WTUser)CommonUtil.getObject(cftMember)).getFullName();
              %>
                    <option value=<%=cftMember%> > <%=StringUtil.checkNull(dept) + " / " + StringUtil.checkNull(duty) + " / " + StringUtil.checkNull(memberName)%> </option>
              <%  }
                }%>
            </select>
            &nbsp;<a href="javascript:selectUser2();"><img src="../../portal/images/icon_user.gif" border="0"></a>
            &nbsp;<a href="javascript:deleteMember();"><img src="../../portal/images/icon_delete.gif" border="0"></a>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02694") %><%--중점관리 사항--%></td>
          <td colspan="3" style="height:80" class="tdwhitel0">
            <textarea name="manageDesc" class="txt_field" style="width:98%; height:96%"><%=StringUtil.checkNull(ketEarlyWarningPlan.getManageDesc())%></textarea>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00810") %><%--계획서--%></td>
          <td colspan="3" class="tdwhitel0">
            <table width="700" border="0" cellspacing="0" cellpadding="0"  id="fileTable">
              <tr>
                <td width="20"></TD>
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
              <tr align="left" id="fileTableRow" style="display:none">
                <td align="center" valign="middle" width="24" height="22">
                  <input type="checkbox" name="fileDelete">
                </td>
                <td align="left">
                  <input type="file" name="filePath" id=input onchange='isValidSecondarySize(this)' onKeyDown="this.blur()" style="ime-mode:disabled" class="txt_fieldRO" size="100">
                </td>
              </tr>
              <%
                ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(planOid));
                Vector files = ContentHelper.getContentList(holder);

                for(int inx=0; files !=null && inx<files.size(); inx++) {
                  ApplicationData appData = (ApplicationData)files.get(inx);
                  String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                  //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                  String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
              %>
                  <tr align="center" bgcolor="#f7f7f7" >
                    <td align="center" width="24" height="22"><input type="checkbox" name="fileDelete"></td>
                    <td align="left"><input type="hidden" name="secondaryDelFile" value="<%=appDataOid%>" >&nbsp;
                      <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;<a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a></td>
                  </tr>
              <%
                }
              %>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td></td>
              </tr>
            </table>
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
