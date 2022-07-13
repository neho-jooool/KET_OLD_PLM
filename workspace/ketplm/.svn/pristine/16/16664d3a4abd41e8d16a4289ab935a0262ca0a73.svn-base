<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*" %>
<%@page import = "java.sql.*" %>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>
<%@page import = "e3ps.edm.*,e3ps.edm.util.*,e3ps.edm.beans.*"%>
<%@page import = "e3ps.wfm.service.*" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "wt.fc.*" %>
<%@page import = "wt.query.*" %>
<%@page import = "wt.iba.value.IBAHolder" %>
<%@page import = "wt.inf.container.*" %>
<%@page import = "wt.session.*" %>
<%@page import = "wt.org.*" %>
<%@page import = "wt.epm.*" %>
<%@page import = "wt.lifecycle.*" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
    EDMProperties edmProperties = EDMProperties.getInstance();
    EDMAttributes edmAttributes = EDMAttributes.getInstance();

    String webAppName = CommonUtil.getWebAppName();

    WTContainerRef wtContainerRef = EDMUtil.getWTContainerRef(edmProperties.getContainer());

    WTUser currentUser = (WTUser) SessionHelper.manager.getPrincipal();

    String cmd = request.getParameter("cmd");

    String number = request.getParameter("number");
    String name = request.getParameter("name");

    String createStart = request.getParameter("create_start");
    String createEnd = request.getParameter("create_end");

    String multiReqOid = ParamUtil.getStrParameter(request.getParameter("multiReqOid"),"");
    multiReqOid = multiReqOid.replaceAll("OR:","");

    String creator = request.getParameter("creator");
    WTUser ucreator = null;

    if( creator != null )
        ucreator = (WTUser) CommonUtil.getObject(creator);

    if( number == null )
        number = "";

    if( name == null )
        name = "";

    if( createStart == null )
        createStart = "";

    if( createEnd == null )
        createEnd = "";

    if( creator == null )
        creator = "";

    //페이지 관련 Parameter
    int pageNo = ParamUtil.getIntParameter(request.getParameter("page"),1);
    int perPage = ParamUtil.getIntParameter(request.getParameter("perPage"),100);

    //페이지 처리
    int total = 0;

    String sessionid = ParamUtil.getStrParameter(request.getParameter("sessionid"),"");

    PageControl control = null;
    //QueryResult result = null;
    PagingQueryResult result = null;

    try
    {

            HashMap map = new HashMap();
            map.put("number", number);
            map.put("name", name);
            map.put("state", "INWORK");

            map.put("create_start", createStart);
            map.put("create_end", createEnd);

            map.put("creator", creator);
            map.put("devType","개발검토단계");
            map.put("className", wt.epm.EPMDocument.class.getName());
            map.put("sessionid", sessionid);
            map.put("page", Integer.toString(pageNo));
            map.put("perPage", Integer.toString(perPage));

            result = KETWfmHelper.service.getEDMQuerySpec(map);
            total = result.getTotalSize();

            control = new PageControl(result, pageNo, 10, perPage);
            control.setHref("/plm/jsp/wfm/SearchEPMDocumentList.jsp");
            control.setParam( "creator=" + creator + "&name=" + name +
                    "&number=" + number + "&create_start=" + createStart + "&create_end=" + createEnd +
                    "&multiReqOid=" + multiReqOid +"&cmd=" + cmd);

    }
    catch( Exception e )
    {
	Kogger.error(e);
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript">
function doViewEPM(_oid) {
    var url = "/<%=webAppName%>/jsp/edm/ViewEPMDocument.jsp?oid="+_oid;
    newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100,width=820,height=800');
    newWinSPart.focus();
    newWinSPart.location.href = url;
}

function doSearch(){
    document.searchEPM.action="/<%=webAppName%>/jsp/wfm/SearchEPMDocumentList.jsp";
    document.searchEPM.submit();
}


function sendPrt(){
    var obj = document.searchEPM;
    var allbox = document.getElementById("checkAll");
    var numArray = window.opener.createMulti.sendList.value.split(',');
    var size = <%=result.size()%>;
    var cnt = 0;
    if(size>0){
        if(obj.epmbox.length>1){
            for(var i=0; i<obj.epmbox.length; i++){
                if(obj.epmbox[i].checked == true){
                    if(numArray.length>0){
                        for(var index=0; index<numArray.length; index++){
                            if(numArray[index]==obj.epmbox[i].oid){
                                alert(obj.epmbox[i].tnumber+"<%=messageService.getString("e3ps.message.ket_message", "00025") %><%--{0}는 이미 추가된 도면입니다--%>");
                                 return;
                             }
                        }
                    }
                    window.opener.addTR(obj.epmbox[i]);
                    obj.epmbox[i].checked = false;
                    cnt++;
                }
            }
            if(allbox.checked==true) allbox.checked = false;
            alert(cnt+"<%=messageService.getString("e3ps.message.ket_message", "00014") %><%--{0}개의 도면이 등록되었습니다.--%>");
        }else {
            if(obj.epmbox.checked == true){
                window.opener.addTR(obj.epmbox);
                obj.epmbox.checked = false;
                alert("<%=messageService.getString("e3ps.message.ket_message", "00014", 1) %><%--{0}개의 도면이 등록되었습니다.--%>");
            }
        }
    }
}

function clearDate(str) {
        var tartxt = document.getElementById(str);
        tartxt.value = "";
}

function selectAll(){
    var box = document.searchEPM.epmbox;
    var allbox = document.getElementById("checkAll");
    var total = <%=result.size()%>;

    if(total!=0){
        if(total>1){
            if(allbox.checked==true){
                for(var i=total-1; i>=0; i--){
                    if(box[i].disabled!=true){
                        box[i].checked = true;
                    }
                }
            }
            else{
                for(i=total-1; i>=0; i--){
                    box[i].checked = false;
                }
            }
        }
        else{
            if(allbox.checked==true){
              if(box.disabled!=true){
                    box.checked = true;
                }
            }
            else{
                box.checked = false;
            }
        }
    }
}

function onKeyPress() {
    var keycode;
    if (window.event) keycode = window.event.keyCode;
    else if ( e ) keycode = e.which;
    else return true;
    if (keycode == 13) {    //엔터키를 눌렀을때
        doSearch();
        return false;
    }
    return true;
}


function doDeleteUser(_id,_name) {
    var idObj = document.getElementById(_id);
    var nameObj = document.getElementById(_name);

    if((idObj != null) && (idObj != 'undefined')) {
        idObj.value = "";
    }

    if((nameObj != null) && (nameObj != 'undefined')) {
        nameObj.value = "";
    }
}

function selectUser(rname) {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }
    acceptRoleMember(rname,attacheMembers);
}

function acceptRoleMember(rname, objArr) {
    if(objArr.length == 0) {
        return;
    }

    var displayName = document.getElementById(rname+'name');
    var keyName = document.getElementById(rname);

    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
        infoArr = objArr[i];
        displayName.value = infoArr[4];
        keyName.value = infoArr[0];
    }
}

function clearAll(){
    doDeleteUser('creator','creatorName');
    clearDate('create_start');
    clearDate('create_end');
    var dnumber = document.getElementById("number");
    var dname = document.getElementById("name");
    dnumber.value = '';
    dname.value = '';
}

</script>
</head>
<body>
<form name="searchEPM">
<input type="hidden" name="multiReqOid" id="multiReqOid" value="<%=multiReqOid %>">
<input type="hidden" name="cmd" id="cmd" value="search">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="710" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02335") %><%--일괄결재도면검색--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="710" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td  class="tab_btm2"></td>
          <td valign="top"><table width="700" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:clearAll()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSearch()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                  <td width="80px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="200" class="tdwhiteL">
                <input type="hidden" name="creator" value="<%=creator%>">
                <input type="text" name="creatorName" class="txt_fieldRO"  style="width:150" id="textfield4" onkeydown="javascript:onKeyPress()" readnOnly value="<%=(ucreator != null) ? ucreator.getFullName() : ""%>">
                  &nbsp;<a href="#" onClick="javascript:selectUser('creator');"><img src="../../portal/images/icon_user.gif" border="0"></a>
                  &nbsp;<a href="#" onClick="javascript:doDeleteUser('creator','creatorName');"><img src="../../portal/images/icon_delete.gif" border="0"></a>
                </td>
                <td width="80px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                <td class="tdwhiteL0"><input type="text" name="create_start" class="txt_fieldRO"  style="width:70" id="create_start" value="<%=createStart%>" readonly>
                  &nbsp;<img src="../../portal/images/icon_6.png" border="0" onclick="javascript:showCal(create_start)" style="cursor:hand;" >&nbsp;
                  <a href="javascript:clearDate('create_start')"><img src="../../portal/images/icon_delete.gif" border="0"></a>&nbsp;~&nbsp;
                  <input type="text" name="create_end" class="txt_fieldRO"  style="width:70;" id="create_end" value="<%=createEnd%>" readonly>
                  &nbsp;<img src="../../portal/images/icon_6.png" border="0" onclick="javascript:showCal(create_end)" style="cursor:hand;" >&nbsp;
                  <a href="javascript:clearDate('create_end')"><img src="../../portal/images/icon_delete.gif" border="0"></a>&nbsp;</td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                <td class="tdwhiteL"><input type="text" name="number" class="txt_field"  style="width:195" id="number" onkeydown="javascript:onKeyPress()" value="<%=number%>"></td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                <td class="tdwhiteL0"><input type="text" name="name" class="txt_field"  style="width:260" id="name" onkeydown="javascript:onKeyPress()" value="<%=name%>"></td>
              </tr>

            </table>
            <table width="700" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700"">
              <tr>
                <td width="30" class="tdblueM">
                  <input type="checkbox" name="checkAll" id="checkAll" onClick="javascript:selectAll()">
                </td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                <td width="170" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                <td width="220" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                <td width="30" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="90" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                <td width="20" class="tdblueM0">&nbsp;</td>
              </tr>
            </table>
            <div style="height=370;width=700;overflow-x:hidden;overflow-y:auto;">
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <%
                  //if( control.getResult().size() > 0 )
               if("search".equals(cmd)){
                  if( control.getTotalCount() > 0 )
                  {
                      int totalCnt = control.getTotalCount();
                      int rowOrderNumber = totalCnt - (perPage * (pageNo - 1));
                      ReferenceFactory rf = new ReferenceFactory();
                      Object oo[] = null;

                      while( result.hasMoreElements() )
                      {
                          oo = (Object[]) result.nextElement();

                          EPMDocument epmDoc = (EPMDocument)oo[0];
                          String nr = (String) epmDoc.getNumber(); //도면번호
                          String nm = (String) epmDoc.getName(); //도면명
                          String nv = (String) KETObjectUtil.getVersion(epmDoc); //버전
                          String estate = (String) epmDoc.getLifeCycleState().getDisplay(); //상태
                          String noid = (String) epmDoc.toString();
                          Timestamp createStamp = (Timestamp) epmDoc.getPersistInfo().getCreateStamp();

                          HashMap ibaValues = null;
                          String manageType = "";
                        String disableMsg = "";
                          WTPrincipalReference creator0 = epmDoc.getCreator();

                          try
                          {
                              ibaValues = edmAttributes.getIBAValues((IBAHolder) rf.getReference(noid).getObject(), Locale.KOREAN);
                              if( ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE) != null )
                              {
                                  manageType = EDMEnumeratedTypeUtil.getDevStage((String) ibaValues.get(EDMHelper.IBA_DEV_STAGE),Locale.KOREAN);
                              }

                              if( WorkflowSearchHelper.manager.IsInMultiApproval(epmDoc , multiReqOid) )
                                  disableMsg = "disabled title=\"" + messageService.getString("e3ps.message.ket_message", "02283")/*이미 다른 요청서에 등록된 도면입니다*/ + "\"";
                          }
                          catch( Exception e )
                          {
                              Kogger.error(e);
                              creator = null;
                          }
              %>
              <tr>
                <td width="30"  class="tdwhiteM">
                  <input type="checkbox" name="epmbox" id="epmbox" tnumber="<%=nr%>"
                  tname="<%=nm%>" state="<%=estate%>" version="<%=nv%>"
                  creator="<%=creator0.getFullName()%>" oid="<%=noid%>" <%=disableMsg %>>
                </td>
                <td width="50"  class="tdwhiteM"><%=rowOrderNumber--%></td>
                <td width="163"  class="tdwhiteL" title="<%=nr %>"><a href="javascript:doViewEPM('<%=noid%>');"><nobr style="text-overflow:ellipsis;overflow:hidden;width:163;cursor:hand;"><%=nr%></nobr></a></td>
                <td width="213"  class="tdwhiteL" title="<%=nm %>"><nobr style="text-overflow:ellipsis;overflow:hidden;width:213;"><%=nm%></nobr></td>
                <td width="30"  class="tdwhiteM"><%=nv%></td>
                <td width="90"  class="tdwhiteM"><%=creator0.getFullName()%></td>
                <td width="90"  class="tdwhiteM0"><%=DateUtil.getDateString(createStamp, "d")%></td>
                <td width="20" class="tdwhiteM0">&nbsp;</td>
              </tr>
              <%
                      }
                  }
              }
              else
              {
              %>
              <tr>
                <td colspan="9" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></font></td>
              </tr>
              <%
              }
              %>
            </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
<%             if("search".equals(cmd)){ %>
        <jsp:include page="/jsp/common/pageControl700.jsp" flush="false">
        <jsp:param name="totalCount" value="<%=control.getTotalCount()%>"/>
        <jsp:param name="totalPage" value="<%=control.getTotalPage()%>"/>
        <jsp:param name="currentPage" value="<%=control.getCurrentPage()%>"/>
        <jsp:param name="perPage" value="<%=perPage%>"/>
        <jsp:param name="startPage" value="<%=control.getStartPage()%>"/>
        <jsp:param name="endPage" value="<%=control.getEndPage()%>"/>

        <jsp:param name="href" value="<%=control.getHref()%>"/>
        <jsp:param name="param" value="<%=control.getParam() %>"/>
        <jsp:param name="sessionid" value="<%=control.getSessionId()%>"/>
        <jsp:param name="isPost" value="<%=control.isPostMethod()%>"/>
        <jsp:param name="type" value="normal"/>
        </jsp:include>

           <table width="700" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table width="700" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td background="../../portal/images/btn_bg1.gif"><a href="javascript:sendPrt();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
  <%} %>
            </td>
        </tr>
      </table></td>
  </tr>

  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="710" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
