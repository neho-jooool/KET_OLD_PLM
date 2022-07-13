<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.project.material.beans.MaterialHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.code.NumberCode"%>

<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.project.material.MoldMaterial"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02210") %><%--원재료 검색--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
</head>
<SCRIPT language=JavaScript src="/plm/portal/js/ajax.js"></SCRIPT>
<script>


<%
  String materialOid = request.getParameter("materialOid");
  //materialOid = "e3ps.project.material.Material:226248";
  String gradeOid = "";
  String makerOid = "";
  String typeOid = "";
  String materialGubun = "";
  String grade = "";

  String height = "";
  String wide = "";

  String itemTypeCheck = "";
  if(request.getParameter("itemTypeCheck") != null){
    itemTypeCheck = request.getParameter("itemTypeCheck");
  }

  String moldType = "";
  if(request.getParameter("moldType") != null){
    moldType = request.getParameter("moldType");
  }
  Kogger.debug("moldType == " + moldType);
  if(request.getParameter("height") != null && !request.getParameter("height").equals("undefined")){
    height = request.getParameter("height");
  }
  if(request.getParameter("wide") != null && !request.getParameter("wide").equals("undefined")){
    wide = request.getParameter("wide");
  }

  String etc = "";
  if(request.getParameter("etc") != null && !request.getParameter("etc").equals("undefined")){
    etc = request.getParameter("etc");
  }

  String pOid = "";
  if(request.getParameter("pOid") != null){
    pOid = request.getParameter("pOid");
  }

  MoldMaterial material = null;
  if(materialOid != null && materialOid.length() > 0){
    material = (MoldMaterial)CommonUtil.getObject(materialOid);
    if(material.getMaterial() != null && material.getMaterial().length() > 0){
      materialGubun = material.getMaterial();
    }
    Kogger.debug("materialGubun = " + materialGubun);
    gradeOid = materialOid;
    makerOid = CommonUtil.getOIDString(material.getMaterialMaker());
    typeOid = CommonUtil.getOIDString(material.getMaterialType());
    grade = material.getGrade();

  }else if("true".equals(itemTypeCheck)){
    if("Mold".equals(moldType)){
      materialGubun = "수지";
    }else if("Press".equals(moldType)){
      materialGubun = "비철";
    }
  }

  String isTry = "";
  if(request.getParameter("isTry") != null){
    isTry = request.getParameter("isTry");
  }

  Vector tMap = null;
%>


function addSelectNode(vArr, type) {

  var fTD = document.all.item(type);
  var re = document.getElementById(type);
  var len = re.length;

  for(var j = len ; j > 0 ; j--){
    re.remove(j);
  }

  for(var i = 0; i < vArr.length; i++) {
    var newSpan = document.createElement("option");

    newSpan.innerHTML = vArr[i][1];
    newSpan.value= vArr[i][0];
    fTD.appendChild(newSpan);
  }

}


function select(){

  if(document.getElementById("material").value == "비철"){
    document.getElementById("addLine").style.display = 'block';

  }else{
    document.getElementById("addLine").style.display = 'none';
    document.getElementById("height").value = "";
    document.getElementById("wide").value = "";

  }
}
var ajax = new sack();

function selected(actionType){

  document.forms[0].actionType.value = actionType;
  ajax.requestFile = "/plm/jsp/project/material/MaterialSelectAjax.jsp";
  ajax.URLString = getPostData(document.forms[0]);
  ajax.onCompletion = selectedList;
  ajax.runAJAX();

}


types = new Array("maker", "type", "grade");

function removeAllOption(type){

  var re = document.getElementById(type);
  var len = re.length;

  for(var j = len ; j > 0 ; j--){
    re.remove(j);
  }
}

function selectedList(){
    xmlDoc = ajax.responseXML;
  var showElements = xmlDoc.selectNodes("//data_info");
  var l_Oid = showElements[0].getElementsByTagName("l_Oid");
  var l_name = showElements[0].getElementsByTagName("l_name");

  var showType = xmlDoc.selectNodes("//typeInfo");
  var type = decodeURIComponent(showType[0].getElementsByTagName("type")[0].text);

  var isDelete = false;

  for(var i = 0; i < types.length; i++){
    if(type == types[i]){
      isDelete = true;
    }

    if(isDelete){

      removeAllOption(types[i]);
      if(types[i] == "maker"){
        removeAllOption("properties");
      }
    }

  }





  var defualt = document.createElement("option");
  defualt.value = "";
  defualt.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>";

  if(l_Oid != null){
    var fTD = document.all.item(type);
    if(type == "grade"){
      for(var i = 0; i < l_Oid.length; i++) {
        mate = new Array();
        mate[0] = decodeURIComponent(l_Oid[i].text);
        mate[1] = decodeURIComponent(l_name[i].text);

        var newSpan = document.createElement("option");
        newSpan.innerHTML = decodeURIComponent(l_name[i].text);
        newSpan.value = mate;//decodeURIComponent(l_Oid[i].text);
        fTD.appendChild(newSpan);
      }
    }else{
      for(var i = 0; i < l_Oid.length; i++) {
        var newSpan = document.createElement("option");
        newSpan.innerHTML = decodeURIComponent(l_name[i].text);
        newSpan.value = decodeURIComponent(l_Oid[i].text);
        fTD.appendChild(newSpan);
      }
    }
  }

  if(type == "maker"){
     showElements = xmlDoc.selectNodes("//attribute");
       l_Oid = showElements[0].getElementsByTagName("l_Oid");
       l_name = showElements[0].getElementsByTagName("l_name");

       if(l_Oid != null){
      fTD = document.all.item("properties");
      for(var i = 0; i < l_Oid.length; i++) {
        var newSpan = document.createElement("option");
        newSpan.innerHTML = decodeURIComponent(l_name[i].text);
        newSpan.value= decodeURIComponent(l_Oid[i].text);
        fTD.appendChild(newSpan);
      }
    }
  }

}


function onSelect() {
  if(!checkedCheck()) {
    return;
  }
  var form = document.forms[0];
  var arr = new Array();
  var subArr = new Array();
  var idx = 0;

      subArr = new Array();

      mate = form.grade.value;

      var result = mate.split(',');
      subArr[0] = result[0];    // 원재료oid
      subArr[1] = result[1];    // 원재료 명

      if(subArr[1] == null || subArr[1] == "undefined"){
        subArr[1] = "<%=grade%>";
      }

      subArr[2] = form.properties.value;  // 원재료 특성

      if(form.material.value == "비철"){
        subArr[3] = form.height.value;  // 두께
        subArr[4] = form.wide.value;  // 폭
      }else{
    	subArr[2] = form.properties.options[form.properties.selectedIndex].text;  // 원재료 특성
        subArr[3] = form.maker.options[form.maker.selectedIndex].text;  // type
        subArr[4] = form.grade.options[form.grade.selectedIndex].text;// grade   	  
      }

      subArr[5] = form.etc.value;  // etc
      subArr[6] = form.properties.options[form.properties.selectedIndex].text //원재료 특성 text
      arr[idx++] = subArr;


  selectModalDialog(arr);
}

function selectModalDialog(arrObj) {
  window.returnValue= arrObj;
  window.close();
}

function checkedCheck(){
  var form = document.forms[0];

  if(form.material.value == ""){
    alert("<%=messageService.getString("e3ps.message.ket_message", "00973") %><%--구분을 입력하세요--%>");
    return false;
  }

  if(form.maker.value == ""){
    alert("<%=messageService.getString("e3ps.message.ket_message", "00313") %><%--Maker를 입력하시기 바랍니다--%>");
    return false;
  }

  if(form.type.value == ""){
    alert("<%=messageService.getString("e3ps.message.ket_message", "00540") %><%--Type을 입력하시기 바랍니다--%>");
    return false;
  }

  if(form.grade.value == ""){
    alert("<%=messageService.getString("e3ps.message.ket_message", "00248") %><%--Grade를 입력하시기 바랍니다--%>");
    return false;
  }

  if(form.properties.value == ""){
        alert('<%=messageService.getString("e3ps.message.ket_message", "02947") %><%--특성을 입력하세요--%>');
    return false;
  }

  if(form.material.value == "비철"){
    if(form.height.value == ""){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01308") %><%--두께를 입력하세요--%>');
      return false;
    }
    if(form.wide.value == ""){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02996") %><%--폭을 입력하세요--%>');
      return false;
    }
  }
  return true;
}


</script>
<body>
    <form>
        <input type="hidden" name="actionType" value=""> <input type="hidden" name="materialOid" value=""></input> <input
            type="hidden" name="materialName" value=""></input>

        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0"
                                    cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02217")%><%--원재료 선택--%></td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top"><table width="430" height="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10">&nbsp;</td>
                            <td valign="top"><table width="420" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:onSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <!-- td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td-->
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="420">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="420">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="420">
                                    <tr>
                                        <td width="90" class="tdblueL">Maker<input type="hidden" name="material"
                                            value="<%=materialGubun%>"></input></td>
                                        <td width="120" class="tdwhiteL"><select name="maker" class="fm_jmp" style="width: 90"
                                            onchange="javascript:selected('type');">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>

                                                <%
                                                    if (material != null) {
                                                		Vector vector = MaterialHelper.getMakerList(material);
                                                		for (int i = 0; i < vector.size(); i++) {
                                                		    NumberCode code = (NumberCode) vector.get(i);
                                                		    String value = CommonUtil.getOIDString(code);
                                                		    String name = code.getName();
                                                		    String selected = "";
                                                		    if (value.equals(makerOid)) {
                                                			selected = "selected";
                                                		    }
                                                %>
                                                <option value="<%=value%>" <%=selected%>><%=name%></option>


                                                <%
                                                         }
                                                    }
                                                %>

                                        </select></td>
                                        <td width="90" class="tdblueL">Type</td>
                                        <td width="120" class="tdwhiteL0">
                                            <select name="type" class="fm_jmp" style="width: 90" onchange="javascript:selected('grade');">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>

                                                <%
                                                    if (material != null) {
                                                		Vector vector = MaterialHelper.getTypeList(material);
                                                		for (int i = 0; i < vector.size(); i++) {
                                                		    NumberCode code = (NumberCode) vector.get(i);
                                                		    String value = CommonUtil.getOIDString(code);
                                                		    String name = code.getName();
                                                		    String selected = "";
                                                		    if (value.equals(typeOid)) {
                                                			selected = "selected";
                                                		    }
                                                %>
                                                <option value="<%=value%>" <%=selected%>><%=name%></option>


                                                <%
                                                        }
                                                    }
                                                %>

                                        </select></td>
                                    </tr>
                                    <tr>
                                        <td width="90" class="tdblueL">Grade</td>
                                        <td width="120" class="tdwhiteL">
                                            <select name="grade" class="fm_jmp" style="width: 90">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>

                                                <%
                                                    if (material != null) {
                                                		Vector vector = MaterialHelper.getGradeList(material);
                                                		for (int i = 0; i < vector.size(); i++) {
                                                		    MoldMaterial data = (MoldMaterial) vector.get(i);
                                                		    String value = CommonUtil.getOIDString(data);
                                                		    String name = data.getGrade();
                                                		    String selected = "";
                                                		    if (value.equals(materialOid)) {
                                                			selected = "selected";
                                                		    }
                                                %>
                                                <option value="<%=value%>" <%=selected%>><%=name%></option>


                                                <%
                                                        }
                                                    }
                                                %>

                                        </select></td>
                                        <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02946")%><%--특성--%></td>
                                        <td width="120" class="tdwhiteL0">
                                            <select name="properties" class="fm_jmp" style="width: 90">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                                <%
                                                    if (materialGubun != null && materialGubun.length() > 0) {
                                                		tMap = NumberCodeHelper.manager.getNumberCodeLevelType("MATERIALPROPERTIES", materialGubun);
                                                		String value = "";
                                                		for (int i = 0; i < tMap.size(); i++) {
                                                		    NumberCode tNum = (NumberCode) tMap.get(i);
                                                		    value = CommonUtil.getOIDString(tNum);
                                                		    String selected = "";
                                                		    //Kogger.debug("poid ==========  " + pOid);
                                                		    if (value.equals(pOid)) {
                                                			   selected = "selected";
                                                		    }
                                                %>
                                                <option value="<%=value %>" <%=selected %>><%=tNum.getName()%></option>
                                                <%
                                                    }
                                                  }
                                                %>
                                        </select></td>
                                    </tr>
                                    <tr <%if("true".equals(isTry)){ %> style="display: none" <%} %>>
                                        <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
                                        <td class="tdwhiteL0" colspan="3"><input type="text" name="etc" class="txt_field"
                                            style="width: 93%" id="etc" value="<%=etc %>"></td>

                                    </tr>
                                    <tr id="addLine" style="display: none">
                                        <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01306") %><%--두께--%>(mm)</td>
                                        <td width="120" class="tdwhiteL"><input type="text" name="height" class="txt_field"
                                            style="width: 90" id="height" value=""></td>
                                        <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02995") %><%--폭(mm)--%></td>
                                        <td width="120" class="tdwhiteL0"><input type="text" name="wide" class="txt_field"
                                            style="width: 90" id="wide" value=""></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table></td>
            </tr>
        </table>

        <script>
<%if(material == null){%>
  selected('maker');
<%} %>
  select();
<%
if(materialGubun.equals("비철")){
%>
  document.getElementById("height").value = "<%=height%>";
  document.getElementById("wide").value = "<%=wide%>";
<%
}
%>
</script>

    </form>
</body>
</html>
