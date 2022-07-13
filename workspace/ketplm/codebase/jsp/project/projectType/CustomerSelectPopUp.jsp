<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*,wt.query.*"%>
<%@page import="e3ps.common.code.*,
        e3ps.common.util.StringUtil,
        e3ps.common.util.CommonUtil,
        wt.fc.PagingQueryResult"%>
        
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>        
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<%
  e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigEx.getInstance("message");

  String codetype = StringUtil.trimToEmpty(request.getParameter("codetype"));
  String parentOid = StringUtil.trimToEmpty(request.getParameter("parentOid"));
  String depth = StringUtil.trimToEmpty(request.getParameter("depth"));

  String numberCode = StringUtil.trimToEmpty(request.getParameter("numberCode"));
  String designTeam = StringUtil.trimToEmpty(request.getParameter("designTeam"));

  String command = "CUSTOMEREVENT";
  String mode =  StringUtil.trimToEmpty(request.getParameter("mode"));
  String invokeMethod = StringUtil.trimToEmpty(request.getParameter("invokeMethod"));

  String expandedDepth = StringUtil.trimToEmpty(request.getParameter("expandedDepth"));
  String selectedDepth = StringUtil.trimToEmpty(request.getParameter("selectedDepth"));

  if(depth.length()==0)
    depth = "0";

  if(expandedDepth.length()==0)
    expandedDepth = "-1";

  if(selectedDepth.length()==0)
    selectedDepth = "-1";

  if(codetype.equals(parentOid))
    parentOid = "";

/*
  if(parentOid.length() == 0 && designTeam.length() > 0) {
    NumberCode parent = NumberCodeHelper.manager.getNumberCode("JELPROCESSTYPE", designTeam);
    parentOid = parent.getPersistInfo().getObjectIdentifier().getStringValue();
  }
*/
  if(parentOid.length() == 0 && numberCode.length() > 0) {
    NumberCode defaultCode = NumberCodeHelper.manager.getNumberCode(codetype, numberCode);
    if(defaultCode != null && defaultCode.getParent() != null) {
      parentOid = (defaultCode.getParent()).getPersistInfo().getObjectIdentifier().getStringValue();
    }
  }

  int psize = 15;
  int cpage = 1;
  int total = 0;
  int pageCount = 10;

  String sessionidstring = request.getParameter("sessionid");
  if(sessionidstring==null)sessionidstring = "0";
  if(sessionidstring.equals(""))sessionidstring = "0";

  long sessionid = Long.parseLong(sessionidstring);
  String pagestring = request.getParameter("tpage");
  if(pagestring!=null && pagestring.length()>0)cpage = Integer.parseInt(pagestring);

  PagingQueryResult result = null;

  boolean isSearch = true;
  if(Integer.parseInt(selectedDepth) > 0 && parentOid.length() == 0) {
    isSearch = false;
  }

  String sc_type = request.getParameter("sc_type");
  String sc_name = request.getParameter("sc_name");
  String sc_value = request.getParameter("sc_value");
  if(sc_type == null){
    sc_type = "";
  }
  if(sc_name == null){
    sc_name = "";
  }
  if(sc_value == null){
    sc_value = "";
  }

  String erpmsg ="";
  %>
    <script> alert("<%=erpmsg%>"); </script>
  <%

  if(isSearch) {
    if(sessionid <= 0){
      HashMap map = new HashMap();
      map.put("type", codetype);
      if(parentOid.length() == 0) {
        map.put("isParent", "false");
        if(sc_name.length() > 0)
        map.put("code", sc_name);
        if(sc_value.length() > 0)
        map.put("name", sc_value);
      }
      else{
        //out.println("else==>"+sc_type +" : "+sc_name);
        if(sc_type.length() > 0)
        map.put("wctype", sc_type);
        if(sc_name.length() > 0)
        map.put("code", sc_name);
        if(sc_value.length() > 0)
        map.put("name", sc_value);

        map.put("parent", CommonUtil.getObject(parentOid));
      }

      QuerySpec qs = NumberCodeHelper.getCodeQuerySpec(map);

      result = PagingSessionHelper.openPagingSession(0, psize, qs);
    }
    else {
      result = PagingSessionHelper.fetchPagingSession((cpage-1) * psize, psize, sessionid);
    }
  }

  if(result!=null){
    total = result.getTotalSize();
    sessionid = result.getSessionId();
  }
%>
<html>
<head>
<base target="_self">
<title><%=messageService.getString("e3ps.message.ket_message", "01135") %><%--기준정보 목록--%></title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<script language="javascript">


function Search(){

  document.forms[0].sessionid.value='0';
  document.forms[0].submit();
}


function report(url){
  document.location.href=url + "?oid=<%=parentOid%>"+"&codetype=<%=codetype%>";
}



function pageClose() {
  if(opener || parent.opener) {
    parent.pageClose();
  }
  else {
    window.close();
  }
}

function onKeyPress() {
  if (window.event) {
    if (window.event.keyCode == 13) Search();
  } else return;
}

document.onkeypress = onKeyPress;

</script>

<style type="text/css">
<!--
body {
  background-image: url(/plm/portal/images/img_default/background2.gif);
  background-repeat:repeat-x;
  background-color: #ffffff;
  margin-top: 24px;
  margin-left:15px;

  overflow-x:auto;
  overflow-y:auto;
  scrollbar-highlight-color:#f4f6fb;
  scrollbar-3dlight-color:#c7d0e6;
  scrollbar-face-color:#f4f6fb;
  scrollbar-shadow-color:#f4f6fb;
  scrollbar-darkshadow-color:#c7d0e6;
  scrollbar-track-color:#f4f6fb;
  scrollbar-arrow-color:#607ddb;


}
-->
</style>
</head>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<body>
<form>
<!-- Hidden Value -->
<input type='hidden' name='codetype' value='<%=codetype%>'>
<input type='hidden' name='parentOid' value='<%=parentOid%>'>
<input type='hidden' name='depth' value='<%=depth%>'>
<input type='hidden' name='command' value='<%=command%>'>
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>
<input type='hidden' name='mode' value="<%=mode%>">
<input type='hidden' name='invokeMethod' value="<%=invokeMethod%>">
<input type='hidden' name='expandedDepth' value="<%=expandedDepth%>">
<input type='hidden' name='selectedDepth' value="<%=selectedDepth%>">

<!-- 본문외관테두리 시작 //-->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
        <tr>
        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
        <td align="right" style="padding:8px 0px 0px 0px"></td>
        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" >
        <tr>
          <td align="right">
          <%
            if(  (!"selectView".equals(command)) && !("select".equals(command)) ){
              //if("PROCESSDIVISIONCODE".equals(codetype)) {
          %>
            <!--a href="#" onclick="javascript:onErpSend();">
            <img src="/plm/portal/images/img_default/button/board_btn_erp.gif" alt="ERP 전송" width="98" height="20" border="0">
            </a-->
          <%
          e3ps.common.jdf.config.Config conf2 = e3ps.common.jdf.config.ConfigEx.getInstance("processcontrol");
          String[] allAttrName = conf2.getArray("workCenter.type.list");

          Vector vt = new Vector();
          for(int i = 0 ; i < allAttrName.length ; i++){
            vt.add(allAttrName[i]);
          }


          %>
          <%
            if(!(parentOid.length() == 0) && (NumberCodeType.toNumberCodeType(codetype).getStringValue()).substring(32).equals("WORKCENTER")){
              //if(!selectedDepth.equals("0") && (NumberCodeType.toNumberCodeType(codetype).getStringValue()).substring(32).equals("WORKCENTER")){
            //parentOid.length() == 0
          %>
            <%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%> : <select name=sc_type onchange="javascript:Search();">
               <option value=""> <%=messageService.getString("e3ps.message.ket_message", "01815") %><%--선택없음--%> </option>
               <%
                 for(int k = 0 ; k < vt.size() ; k++){

                 String value = vt.get(k).toString().trim();
                 //Kogger.debug(sc_type+ ":" + value);

               %>
               <option value="<%=value%>" <%=(value.equals(sc_type.trim()))?"selected":"" %> ><%=vt.get(k)%></option>
               <%
                 }
               %>
               </select>

            W/C :&nbsp;<input type=text name="sc_name" value="<%=sc_name%>" class="txt_field" />
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:Search();">
            <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
            </a>
          <%}else{ %>
                        <%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%> :&nbsp;<input type=text name="sc_value" value="<%=sc_value%>" class="txt_field" />
            &nbsp;&nbsp;
            <%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%> :&nbsp;<input type=text name="sc_name" value="<%=sc_name%>" class="txt_field" />
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:Search();">
            <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
            </a>

          <%} %>
          <%
          if(selectedDepth.equals("0") && ( (NumberCodeType.toNumberCodeType(codetype).getStringValue()).substring(32).equals("CUSTOMERCODE") || (NumberCodeType.toNumberCodeType(codetype).getStringValue()).substring(32).equals("MODELCODE")  ) ){
            String tmp = "";
          %>
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:erpSync('<%=tmp %>');">
            <img src="/plm/portal/images/img_default/btn1/icon_erp.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00216") %><%--ERP 동기화--%>" width="22" height="23" border="0">
            </a>
          <%
          }

          %>
            <%if(CommonUtil.isAdmin()){ %>
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:create();">
                        <img src="/plm/portal/images/img_default/button/board_btn_ok.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%>' width="62" height="20" border="0">
            </a>
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:modify();">
            <img src="/plm/portal/images/img_default/button/board_btn_modify.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" width="62" height="20" border="0">
            </a>
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:remove();">
            <img src="/plm/portal/images/img_default/button/board_btn_delete.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>" width="62" height="20" border="0">
            </a>
            <%} %>

            <%if(parentOid.length() > 0||true){ %>
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:report('/plm/jsp/common/code/NumberCodeExcel.jsp')">
                        <img src="/plm/portal/images/img_default/button/board_btn_excel.gif" alt="'<%=messageService.getString("e3ps.message.ket_message", "02117") %><%--엑셀 다운로드--%>' width="62" height="20" border="0">
            </a>

            <%} %>
          <%
              }

            if("select".equals(command)) {
          %>
                        <%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%> :&nbsp;<input type=text name="sc_value" value="<%=sc_value%>" class="txt_field" />
            &nbsp;&nbsp;
            <%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%> :&nbsp;<input type=text name="sc_name" value="<%=sc_name%>" class="txt_field" />
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:Search();">
            <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
            </a>
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:addSelectCode();">
            <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
            </a>
            &nbsp;&nbsp;
            <a href="#" onclick="javascript:self.close();">
            <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
            </a>
          <%
            }
          %>
          <%
          if("selectView".equals(command)) {
          %>


          <%
            }
          %>

          </td>
        </tr>
      </table>
      <!-- div style="width:100%;height:600px;border:0;padding:0;margin-top:10px;overflow-x:auto;overflow-y:auto" -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td  class="tab_btm1"></td>
        </tr>
      </table>

      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
      <%if("select".equals(command) || "selectView".equals(command) || CommonUtil.isAdmin()) {%>
      <col width="5%">
      <%}%>

      <col width="50%"><col width="5%"><col width="*">

      <tr bgcolor="#D8E0E7" align=center height=23>
        <%if("select".equals(command) || CommonUtil.isAdmin()) {%>
          <td class="tdblueM">
            <%if("multi".equals(mode)){%><input name="chkAll" type="checkbox" class="Checkbox" onClick="javascript:checkAll();"><%}else{%>&nbsp;<%}%>
          </td>
        <%}%>


                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
              <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
              <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>

        </tr>
      <%
        if(result == null || result.size() == 0) {
      %>
      <%
          if(parentOid.length() == 0){
      %>

          <TR class="tdwhiteM0" >
            <td class="tdwhiteM0" colspan='4'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
          </TR>
        <%} %>
      <%
        } else {
          NumberCode nCode = null;
          String nCodeOid = "";
          String checked = "";
          Object obj[] = null;
          while(result.hasMoreElements()) {
            obj = (Object[])result.nextElement();
            nCode = (NumberCode)obj[0];
            nCodeOid = nCode.getPersistInfo().getObjectIdentifier().getStringValue();
            checked = "";
            String dsCode = "";
            if(nCode.getParent() != null){
              dsCode = nCode.getParent().getName();
            }

            if(numberCode.equals(nCode.getCode())) {
              checked = "checked";
            }
      %>
          <tr>
            <%  if("select".equals(command) || CommonUtil.isAdmin()) {%>
                <td class="tdwhiteM">
                  <input type="checkbox" value="<%=nCodeOid%>" name="oid" codename='<%=nCode.getName()%>' codecode='<%=nCode.getCode()%>' codedesc='<%=StringUtil.checkNull(nCode.getDescription())%>' codelong='<%=CommonUtil.getOIDLongValue(nCode)%>' <%if(!"multi".equals(mode)){%>onClick="oneClick(this)"<%}%> <%=checked%>>
                </td>
            <%  }%>
                <td class="tdwhiteL" title="<%=nCode.getName()%>"><%=nCode.getName()%></td>
                <td class="tdwhiteM"><%=nCode.getCode()%></td>
                <td class="tdwhiteL0" >&nbsp;<%=StringUtil.checkNull(nCode.getDescription())%></td>
          </tr>
      <%
          } // end of while(pr.hasMoreElements())
        }
      %>
      </TABLE>
      <%
        int ksize = total/psize;
        int x = total%psize;
        if(x>0) ksize++;
        int temp = cpage/pageCount;
        if( (cpage%pageCount)>0)temp++;
        int start = (temp-1)*pageCount+1;
        int end = start + pageCount-1;
        if(end> ksize){
          end = ksize;
        }
      %>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
        <tr>
                  <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
          <td>
            <table border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                                <td width="40" align="center"><%if(start>1){%><a href="#" onclick="JavaScript:gotoPage(1)" class="small"><%=messageService.getString("e3ps.message.ket_message", "02792") %><%--처음--%></a><%}%></td>
                <td width="1" bgcolor="#dddddd"></td>
                <%if(start>1){%>
                                <td width="60" class="quick" align="center"><a href="#" onclick="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "02322") %><%--이전--%></a></td>
                <td width="1" bgcolor="#dddddd"></td>
                <%}
                for(int i=start; i<= end; i++){
                %>
                <td style="padding:2 8 0 7;cursor:hand" onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on">
                  <%if(i==cpage){%><b><%=i%><%}else{%><a href="#" onclick="JavaScript:gotoPage(<%=i%>)"><%=i%></a><%}%></td>
                <%}
                if(end < ksize){
                %>
                <td width="1" bgcolor="#dddddd"></td>
                <td width="60" align="center"><a href="#" onclick="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "01186") %><%--다음--%></a></td>
                <%}%>
                <td width="1" bgcolor="#dddddd"></td>
                                <td width="45"align="center"><%if(end<ksize){%><a href="#" onclick="JavaScript:gotoPage(<%=ksize%>)" class="small"><%=messageService.getString("e3ps.message.ket_message", "01354") %><%--마지막--%></a><%}%></td>
              </tr>
            </table>
          </td>
                    <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
        </tr>
      </table>

    </td>
  </tr>
</table>
</form>
<iframe src="" name="hiddenFrame" scrolling=no frameborder=no marginwidth=0 marginheight=0 style="display:none"></iframe>
</body>
</html>
<script language="JavaScript">
<!--
function addSelectCode() {
  if(!checkedCheck()) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "02912") %><%--코드를 선택하십시오--%>");
    return;
  }


  form = document.forms[0];

  var arr = new Array();
  var subArr = new Array();
  var idx = 0;
  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      if(form.oid[i].checked == true) {
        subArr = new Array();
        subArr[0] = form.oid[i].value;
        subArr[1] = form.oid[i].codecode;
        subArr[2] = form.oid[i].codename;
        subArr[3] = form.oid[i].codedesc;
        subArr[4] = form.oid[i].codelong;
        arr[idx++] = subArr;
      }
    }
  }
  else {
    if(form.oid.checked == true) {
      subArr = new Array();
      subArr[0] = form.oid.value;
      subArr[1] = form.oid.codecode;
      subArr[2] = form.oid.codename;
      subArr[3] = form.oid.codedesc;
      subArr[4] = form.oid.codelong;
      arr[idx++] = subArr;
    }
  }

<%  if(invokeMethod.length() == 0) {  %>
  //modal dialog
  selectModalDialog(arr);
<%  } else {  %>
  //open window
  selectOpenWindow(arr);
<%  }  %>

}

function selectModalDialog(arrObj) {
  window.returnValue= arrObj;
  window.close();
}

<%  if(invokeMethod.length() > 0) {  %>

function selectOpenWindow(arrObj) {
  //...이하 원하는 스크립트를 만들어서 작업...
  if(opener) {
    if(opener.<%=invokeMethod%>) {
      opener.<%=invokeMethod%>(arrObj);
    }
  }

  if(parent.opener) {
    if(parent.opener.<%=invokeMethod%>) {
      parent.opener.<%=invokeMethod%>(arrObj);
    }
  }

  if(opener || parent) {
    parent.pageClose();
  }
  else {
    window.close();
  }
}

<%  }  %>

function checkAll() {
  form = document.forms[0];
  if(form.oid == null) {
    return;
  }

  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      form.oid[i].checked = form.chkAll.checked;
    }
  }
  else {
    form.oid.checked = form.chkAll.checked;
  }
}

function checkedCheck() {
  form = document.forms[0];
  if(form.oid == null) {
    return false;
  }

  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      if(form.oid[i].checked == true) {
        return true;
      }
    }
  }
  else {
    if(form.oid.checked == true) {
      return true;
    }
  }

  return false;

}

function selectedCode() {
  var arr = new Array();

  form = document.forms[0];
  if(form.oid == null) {
    return arr;
  }

  var idx = 0;
  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      if(form.oid[i].checked == true) {
        arr[idx++] = form.oid[i].value;
      }
    }
  }
  else {
    if(form.oid.checked == true) {
      arr[idx++] = form.oid.value;
    }
  }

  return arr;
}

function create()
{
  var codetype = document.forms[0].codetype.value;
  var param = "?command=create&codetype="+codetype+"&parentOid=<%=parentOid%>&depth=<%=depth%>";
  //openWindow("/plm/jsp/common/code/NumberCodeMgtRegister.jsp"+param, "create", "600", "250");

  var url = "/plm/jsp/common/code/NumberCodeMgtRegister.jsp"+param;
  openEditPage(url);
}

function modify()
{
  if(!checkedCheck()) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "02912") %><%--코드를 선택하십시오--%>");
    return;
  }

  var arr = selectedCode();
  var oid = arr[0];


  var codetype = document.forms[0].codetype.value;
  var param = "?command=modify&codetype="+codetype+"&parentOid=<%=parentOid%>&depth=<%=depth%>&oid="+oid;
  //openWindow("/plm/jsp/common/code/NumberCodeMgtRegister.jsp"+param, "modify", "600", "250");

  var url = "/plm/jsp/common/code/NumberCodeMgtRegister.jsp"+param;
  openEditPage(url);
}

function openEditPage(url) {
  //var url = "/plm/jsp/common/code/NumberCodeMgtRegister.jsp"+param;
  codeReg = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=450px; dialogHeight:350px; center:yes");
  if(typeof codeReg == "undefined" || codeReg == null) {
    return;
  }
/*
  if(parent) {
    if(parent.tree) {
      parent.tree.document.location.href = "/plm/jsp/common/code/NumberCodeMgtTree.jsp?command=<%=command%>&mode=<%=mode%>&invokeMethod=<%=invokeMethod%>&codetype="+codeReg[0]+"&parentOid=" + codeReg[1];
    }
  }
*/
  document.forms[0].codetype.value = codeReg[0];
  document.forms[0].parentOid.value = codeReg[1];
  onSubmit();
}

function onSubmit(){

  treeReload();

  document.forms[0].sessionid.value = '0';
  document.forms[0].action = "/plm/jsp/common/code/NumberCodeMgtList.jsp";
  document.forms[0].method ="post";
  document.forms[0].target = "_self";
  document.forms[0].submit();
}

function treeReload() {
  var ctype = document.forms[0].codetype.value;
  var poid = document.forms[0].parentOid.value;
  if(parent) {
    if(parent.tree) {
      parent.tree.document.location.href = "/plm/jsp/common/code/NumberCodeMgtTree.jsp?command=<%=command%>&mode=<%=mode%>&invokeMethod=<%=invokeMethod%>&codetype="+ctype+"&parentOid=" + poid;
    }
  }
}

function remove()
{
  if(!checkedCheck()) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "02912") %><%--코드를 선택하십시오--%>");
    return;
  }

  if(!confirm("<%=conf.getString("confirm.delete")%>")) return;
  //showProcessing();
  //disabledAllBtn();

  var arr = selectedCode();
  var oid = arr[0];
  var codetype = document.forms[0].codetype.value;
  var param = "?command=delete&codetype=" + codetype + "&oid=" + oid;
  document.forms[0].action ="/plm/jsp/common/code/searchChildCode.jsp"+param;
  document.forms[0].method ="post";
  document.forms[0].target = "hiddenFrame";
  document.forms[0].submit();
}

function onErpSend() {
  var codetype = document.forms[0].codetype.value;
  var param = "?command=erpSend&codetype=" + codetype;

  onProgressBar();

  var url = "/plm/jsp/common/code/CodeAjaxAction.jsp" + param;
  callServer(url, onMessage);
}

function onMessage(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'true') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00220") %><%--ERP 전송 완료 했습니다--%>");
  } else {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
  }
}

//-->
</script>
