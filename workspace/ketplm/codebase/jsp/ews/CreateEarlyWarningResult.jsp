<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "e3ps.common.web.ParamUtil,
                                    e3ps.common.util.CommonUtil,
                                    e3ps.common.content.*,
                                    e3ps.common.code.NumberCode,
                                    e3ps.common.code.NumberCodeHelper,
                                    e3ps.ews.entity.KETEarlyWarning,
                                    wt.content.*,
                                    java.util.Vector" %>

<%@include file="/jsp/project/template/ajaxProgress.html"%>

<%
    String oid = ParamUtil.getParameter(request, "oid");
    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);

    String isTodo = ParamUtil.getParameter(request, "todo");

    Vector vecProblemGroup = NumberCodeHelper.manager.getNumberCodeLevel("PROBLEMTYPE", "top");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src='../common/content/content.js'></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
img {   
    vertical-align:baseline;
}
-->
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

    var problemcnt = 0;
    //문제점 입력
  function insertProblem(){
      var problemTable = document.getElementById("problemTable");
      problemcnt = problemcnt +1;
      var newTr = problemTable.insertRow();

      var newTd;

      newTd = newTr.insertCell();
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type='checkbox' name='problemCheck'>"
                        + "<input type='hidden' name='problemKind' value='problem'>"
                        + "<input type='hidden' name='isEnd'>";

      newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type='text' name='problemStep' class='txt_field'  style='width:85'><br>&nbsp;";

      newTd = newTr.insertCell();
      newTd.className = "tdwhiteMT2";
      newTd.innerHTML = "<select name='problemGroup' id='problemGroup"+problemcnt+"' class='fm_jmp' style=width:120;'margin:2;' onchange=\"javascript:changeProblemType(this.value,'problemType"+problemcnt+"');\">"
                        + "<option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>"
                        + "<% for(int inx = 0; inx < vecProblemGroup.size(); inx++) { %>"
                        + "<option value='<%=((NumberCode)vecProblemGroup.get(inx)).getCode()%>'><%=((NumberCode)vecProblemGroup.get(inx)).getName()%></option>"
                        + "<% } %>"
                        + "</select>"
                        + "<br>"
                        + "<select name='problemType' id='problemType"+problemcnt+"' class='fm_jmp' style='width:120;margin:2;'>"
                        + "<option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>"
                        + "</select>";

      newTd = newTr.insertCell();
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<textarea name='problemDesc' class='txt_field' style='width:145; height:40;'></textarea>";

      newTd = newTr.insertCell();
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<textarea name='measure' class='txt_field' style='width:145; height:40;'></textarea>";

      newTd = newTr.insertCell();
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type='hidden' name='charge' id='charge"+problemcnt+"' >"
                        + "<input type='text' name='namecharge' id='namecharge"+problemcnt+"' class='txt_fieldRO'  style='width:80' readonly>"
                        + "<img src='../../portal/images/icon_user.gif' border='0' onclick=\"javascript:selectUser('charge"+problemcnt+"');\" style='cursor:hand;'>&nbsp;"
                        + "<img src='../../portal/images/icon_delete.gif' border='0' onclick=\"javascript:deleteValue('charge"+problemcnt+"');deleteValue('namecharge"+problemcnt+"');\" style='cursor:hand;'>";

      newTd = newTr.insertCell();
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type='text' name='endDate' id='endDate"+problemcnt+"' class='txt_fieldRO' style='width:80' readonly>"
                        + "<img src='../../portal/images/icon_6.png' border='0' onclick=\"javascript:showCal(endDate"+problemcnt+");\" style='cursor:hand;'>&nbsp;"
                        + "<img src='../../portal/images/icon_delete.gif' border='0' onclick=\"javascript:deleteValue('endDate"+problemcnt+"');\" style='cursor:hand;'>";
  }

  //불량유형 리스트 생성
  function changeProblemType(parentValue, type) {
        var url = "/plm/jsp/ews/ChangeCodeAjaxAction.jsp?parentValue=" + parentValue + "&child=" + type;
        callServer(url, setProblemType);
  }

  //불량유형 리스트 셋팅
  function setProblemType(req) {
        var xmlDoc = req.responseXML;

        var showElements = xmlDoc.selectNodes("//message");
        var child = showElements[0].getElementsByTagName("l_child")[0].text;

        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");
        var l_name = showElements[0].getElementsByTagName("l_name");
        var arr = new Array();
        for(var i = 0; i < l_oid.length; i++) {
            subArr = new Array();
            subArr[0] = decodeURIComponent(l_oid[i].text);
            subArr[1] = decodeURIComponent(l_name[i].text);
            arr[i] = subArr;
        }

        var fTD = document.all.item(child);
        var childList = document.getElementById(child);
        var len = childList.length;
        for(var j = len ; j > 0 ; j--){
            childList.remove(j);
        }
        var newSpan;
        for(var i = 0; i < arr.length; i++) {
            newSpan = document.createElement("option");
            newSpan.innerHTML = arr[i][1];
            newSpan.value= arr[i][0];
            fTD.appendChild(newSpan);
        }
    }

    //사용자 셋팅
    function acceptMember(rname, objArr) {
        if(objArr.length == 0) {
            return;
        }

        var displayName = document.getElementById('name'+rname);
        var keyName = document.getElementById(rname);

        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            displayName.value = infoArr[4];
            keyName.value = infoArr[0];
        }
    }

  //공정감사 지적사항 입력
  function insertProcess(){
      var processTable = document.getElementById("processTable");
      problemcnt = problemcnt +1;
      var newTr = processTable.insertRow();

      var newTd;

      newTd = newTr.insertCell();
      newTd.align = "center";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type='checkbox' name='processCheck'>"
                        + "<input type='hidden' name='problemKind' value='process'>"
                        + "<input type='hidden' name='problemStep'>"
                        + "<input type='hidden' name='charge'>";

      newTd = newTr.insertCell();
      newTd.align = "center";
      newTd.className = "tdwhiteMT3";
      newTd.innerHTML = "<select name='problemType' class='fm_jmp' style='width:55'>"
                        + "<option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>"
                        + "<option value='사내'><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option>"
                        + "<option value='고객'><%=messageService.getString("e3ps.message.ket_message", "00828") %><%--고객--%></option>"
                        + "</select><br>&nbsp;"
                        + "<input type='hidden' name='problemGroup' value=' '>";

      newTd = newTr.insertCell();
      newTd.align = "center";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<textarea name='problemDesc' class='txt_field' style='width:215; height:40;'></textarea>";

      newTd = newTr.insertCell();
      newTd.align = "center";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<textarea name='measure' class='txt_field' style='width:220; height:40;'></textarea>";

      newTd = newTr.insertCell();
      newTd.align = "center";
      newTd.className = "tdwhiteMT2";
      newTd.innerHTML = "<input type='text' name='endDate' id='endDate"+problemcnt+"' class='txt_fieldRO' style='width:70' readonly>"
                        + "<br><img src='../../portal/images/icon_6.png' border='0' onclick=\"javascript:showCal(endDate"+problemcnt+");\" style='cursor:hand;'>&nbsp;"
                        + "<img src='../../portal/images/icon_delete.gif' border='0' onclick=\"javascript:deleteValue('endDate"+problemcnt+"');\" style='cursor:hand;'>";

      newTd = newTr.insertCell();
      newTd.align = "center";
      newTd.className = "tdwhiteMT3";
      newTd.innerHTML = "<select name='isEnd' class='fm_jmp' style='width:50'>"
                        + "<option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>"
                        + "<option value='완료'><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></option>"
                        + "<option value='미결'><%=messageService.getString("e3ps.message.ket_message", "01443") %><%--미결--%></option>"
                        + "</select><br>&nbsp;";
  }

  //문제점 삭제
  function deleteProblem() {
        var problemTable = document.getElementById("problemTable");
        if (problemTable.rows.length == 2) return;
        var problemCheck = document.forms[0].problemCheck;

        for (var inx = problemTable.rows.length; inx > 2; inx--) {
            if (problemCheck[inx-1].checked) {
                problemTable.deleteRow(inx-1);
            }
        }
        var  isCheck = document.forms[0].problemCheck[0].checked;
        var problemCheck = document.forms[0].problemCheck;

        if (isCheck) {
            problemCheck[0].checked = false;
        }
    }

    //공정감사 지적사항 삭제
  function deleteProcess() {
        var processTable = document.getElementById("processTable");
        if (processTable.rows.length == 1) return;
        var processCheck = document.forms[0].processCheck;

        for (var inx = processTable.rows.length; inx > 1; inx--) {
            if (processCheck[inx-1].checked) {
                processTable.deleteRow(inx-1);
            }
        }

        var  isCheck = document.forms[0].processCheck[0].checked;
        var processCheck = document.forms[0].processCheck;

        if (isCheck) {
            processCheck[0].checked = false;
        }
    }

    //문제점 체크박스
    function allCheckProblem() {
        var isCheck;
        if (problemTable.rows.length == 2){
            return;
        }
        if (problemTable.rows.length > 2){
            isCheck = document.forms[0].problemCheck[0].checked;
        }

        var body = document.getElementById("problemTable");
        var problemCheck = document.forms[0].problemCheck;

        if (isCheck) {
            for (var inx = 0; inx < problemCheck.length; inx++) {
                problemCheck[inx].checked = true;
            }
        }else {
            for (var inx = 0; inx < problemCheck.length; inx++) {
                problemCheck[inx].checked = false;
            }
        }
    }

    //공정감사 지적사항 체크박스
    function allcheckProcess() {
        var isCheck;
        if (processTable.rows.length == 1){
            return;
        }
        if (processTable.rows.length > 1){
            isCheck = document.forms[0].processCheck[0].checked;
        }

        var body = document.getElementById("processTable");
        var processCheck = document.forms[0].processCheck;

        if (isCheck) {
            for (var inx = 0; inx < processCheck.length; inx++) {
                processCheck[inx].checked = true;
            }
        }else {
            for (var inx = 0; inx < processCheck.length; inx++) {
                processCheck[inx].checked = false;
            }
        }
    }

    //문제점 List 첨부파일 추가
    function insertProblemFile() {
        var index = problemFileTable.rows.length;
        if(index > 10) return;

        if(index >= 2) {
            if(problemFileTableRow.style != null)
                problemFileTableRow.style.display = 'block';
            else
                problemFileTableRow[0].style.display = 'block';
        }
        trObj = problemFileTable.insertRow(index);
        trObj.replaceNode(problemFileTable.rows[1].cloneNode(true));

        var tempFile = problemFileTable.rows[index].cells[1].children[0];
        tempFile.name = tempFile.name+index;

        problemFileTableRow[0].style.display = 'none';
    }

    //문제점 List 첨부파일 삭제
    function deleteProblemFile() {
        index = document.forms[0].problemFileDelete.length-1;

        for(i=index; i>=1; i--) {
            if(document.forms[0].problemFileDelete[i].checked == true) problemFileTable.deleteRow(i+1);
        }
    }

    //공정감사 지적사항 첨부파일 추가
    function insertProcessFile() {
        var index = processFileTable.rows.length;
        if(index > 10) return;

        if(index >= 2) {
            if(processFileTableRow.style != null)
                processFileTableRow.style.display = 'block';
            else
                processFileTableRow[0].style.display = 'block';
        }
        trObj = processFileTable.insertRow(index);
        trObj.replaceNode(processFileTable.rows[1].cloneNode(true));

        var tempFile = processFileTable.rows[index].cells[1].children[0];
        tempFile.name = tempFile.name+index;

        processFileTableRow[0].style.display = 'none';
    }

    //공정감사 지적사항 첨부파일 삭제
    function deleteProcessFile() {
        index = document.forms[0].processFileDelete.length-1;

        for(i=index; i>=1; i--) {
            if(document.forms[0].processFileDelete[i].checked == true) processFileTable.deleteRow(i+1);
        }
    }

    //실적보고서 저장
    function save(){
        if (document.getElementById("problemTable").rows.length > 2 || document.getElementById("processTable").rows.length > 1){
          document.getElementById("isProblem").value = "Y";
      }

        showProcessing();
      disabledAllBtn();

      document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningResultServlet?cmd=create';
      document.forms[0].encoding = 'multipart/form-data';
      document.forms[0].submit();
    }

//-->
</script>
</head>
<body>
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='isProblem' value='N'>
<input type='hidden' name='isTodo' value=<%=isTodo%> >
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02038") %><%--실적보고서 등록--%></td>
                </tr>
                </table>
            </td>
            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        <!--  이전 디자인 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
              <table width="760" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02038") %><%--실적보고서 등록--%></td>
                <td align="right">
                    <img src="../../portal/images/icon_navi.gif">Home
                    <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02809") %><%--초기유동관리--%>
                    <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02041") %><%--실적보고서등록 등록--%></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      -->
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="20">&nbsp;</td>
            <td class="font_03">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="5"></td>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img id='tab1_1' src="../../portal/images/tab_1.png"></td>
                          <td id='tab1_2' class="btn_tab" background="../../portal/images/tab_3.png">
                            <a href="javascript:goTab('tab1');" class="btn_tab" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01385") %><%--목표 및 실적--%></a>
                          </td>
                          <td><img id='tab1_3' src="../../portal/images/tab_2.png"></td>
                        </tr>
                      </table>
                    </td>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img id='tab2_1' src="/plm/portal/images/tab_4.png"></td>
                          <td id='tab2_2' class="btn_tab" onfocus="this.blur();" background="/plm/portal/images/tab_6.png"><a href="javascript:goTab('tab2');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01747") %><%--상세실적현황--%></a></td>
                          <td><img id='tab2_3' src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                      </table>
                    </td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img id='tab3_1' src="../../portal/images/tab_4.png"></td>
                          <td id='tab3_2' class="btn_tab" onfocus="this.blur();" background="../../portal/images/tab_6.png"><a href="javascript:goTab('tab3');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01439") %><%--문제점 List--%></a></td>
                          <td><img id='tab3_3' src="../../portal/images/tab_5.png"></td>
                        </tr>
                      </table>
                    </td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img id='tab4_1' src="../../portal/images/tab_4.png"></td>
                          <td id='tab4_2' class="btn_tab" onfocus="this.blur();" background="../../portal/images/tab_6.png"><a href="javascript:goTab('tab4');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00877") %><%--공정감사현황--%></a></td>
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
                              <% if( isTodo != null && isTodo.equals("Y")){ %>
                          <a href="javascript:goBack('create');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a>
                        <% }else{ %>
                              <a href="javascript:goBack('create');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a>
                              <% } %>
                          </td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                  </td>
                  <td width="5">&nbsp;</td>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                  <!-- 
                  <% if( isTodo == null || !isTodo.equals("Y")){ %>
                  <td width="5">&nbsp;</td>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goList('create');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378") %><%--목록--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <% } %>
                    -->
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
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="/plm/portal/images/box_13.gif" style="background-attachment: fixed;">&nbsp;</td>
          <td valign="top">

              <!------------------------------------------실적보고서 Tab1 Start----------------------------------->

              <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tab1" style="display:block" >
              <tr>
                <td align="left" valign="top">
                    <iframe src='/plm/jsp/ews/EarlyWarningResultTab1.jsp?oid=<%=oid%>' frameborder="0" style="margin:0;width:'100%';" scrolling="no" onload="resizeHeight(this)"></iframe>
                </td>
              </tr>
            </table>

            <!------------------------------------------실적보고서 Tab1 End------------------------------------->

            <!------------------------------------------실적보고서 Tab2 Start----------------------------------->

              <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tab2" style="display:none" >
              <tr>
                <td align="left" valign="top">
                    <iframe id="itab2" src='/plm/jsp/ews/EarlyWarningResultTab2.jsp?oid=<%=oid%>' frameborder="0" style="margin:0;width:'100%';" scrolling="no" onload="resizeHeight(this)"></iframe>
                </td>
              </tr>
            </table>

            <!------------------------------------------실적보고서 Tab2 End------------------------------------->

            <!------------------------------------------실적보고서 Tab3 Start----------------------------------->

            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tab3" style="display:none">
              <tr>
                <td valign="top">
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td  class="tab_btm2"></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td width="65" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01438") %><%--문제점--%></td>
                      <td width="*" class="tdwhiteM0">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td class="space5"></td>
                            </tr>
                          </table>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td>&nbsp;</td>
                              <td align="right">
                                  <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                      <td>
                                          <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                              <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertProblem();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                              <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                          </table>
                                        </td>
                                      <td width="5">&nbsp;</td>
                                      <td>
                                          <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                              <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteProblem();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                              <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                          </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                    </tr>
                              </table>
                            </td>
                          </tr>
                      </table>
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td class="space5"></td>
                        </tr>
                      </table>
                      <table id="problemTable" width="100%" cellpadding="0" cellspacing="0" class="table_border" >
                        <tr>
                          <td width="25" class="tdgrayM"><input type="checkbox" name="problemCheck" onClick="javascript:allCheckProblem();"></td>
                          <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00882") %><%--공정명--%></td>
                          <td width="125" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01624") %><%--불량유형--%></td>
                          <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01438") %><%--문제점--%></td>
                          <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01235") %><%--대책--%></td>
                          <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01200") %><%--담당--%></td>
                          <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                        </tr>
                        <tr style="display:none">
                          <td><input type="checkbox" name="problemCheck"></td>
                          <td><input type="hidden" name="problemKind" ></td>
                          <td><input name="problemStep" ></td>
                          <td><input name="problemGroup"></td>
                          <td><input name="problemType"></td>
                          <td><input name="problemDesc" ></td>
                          <td><input name="measure" ></td>
                          <td><input name="charge" ></td>
                          <td><input name="endDate" ></td>
                          <td><input type="hidden" name="isEnd" ></td>
                        </tr>
                      </table>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td class="space5"></td>
                            </tr>
                          </table></td>
                    </tr>
                    <tr>
                       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01439") %><%--문제점 List--%></td>
                       <td colspan="3" class="tdwhiteM0">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="problemFileTable">
                                <tr>
                                    <td></td>
                                    <td align="right">
                                        <table>
                                            <tr>
                                   <td>
                                       <table border="0" cellspacing="0" cellpadding="0">
                                       <tr>
                                         <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                         <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertProblemFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                         <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                       </tr>
                                     </table>
                                   </td>
                                   <td>
                                       <table border="0" cellspacing="0" cellpadding="0">
                                       <tr>
                                         <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                         <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteProblemFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                         <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                         <td width="2"></td>
                                       </tr>
                                     </table>
                                   </td>
                                 </tr>
                               </table>
                                    </td>
                                </tr>
                                <tr align="center" id="problemFileTableRow" style="display:none">
                                    <td align="center" valign="middle" width="24" height="22">
                                        <input type="checkbox" name="problemFileDelete">
                                    </td>
                                    <td align="left">
                                        <input type="file" name="problem" id=input onChange='isValidSecondarySize(this)' onkeyDown="this.blur()" style="ime-mode:disabled" class="txt_fieldRO" size="135">
                                    </td>
                                </tr>
                                <%
                                  ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(oid));
                                    Vector files = ContentHelper.getContentList(holder);

                                    for(int inx=0; files !=null && inx<files.size(); inx++) {
                                        ApplicationData appData = (ApplicationData)files.get(inx);
                                        String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                                        //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                        String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
                                %>
                                        <tr>
                                            <td align="center" width="24" height="22"><input type="checkbox" name="problemFileDelete"></td>
                                            <td align="left"><input type="hidden" name="oldProblemFile" value="<%=appDataOid%>" style="width:100%">&nbsp;
                                                <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;<a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a></td>
                                        </tr>
                                <%
                                    }
                                %>
                            </table>
                         <table width="100%" border="0" cellspacing="0" cellpadding="0">
                           <tr>
                             <td class="space5"></td>
                           </tr>
                         </table>
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
                      <td width="65" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00876", "<br>") %><%--공정감사{0}지적사항--%></td>
                      <td width="*" class="tdwhiteM0">
                          <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="space5"></td>
                            </tr>
                          </table>
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
                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertProcess();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                          </tr>
                                        </table>
                                      </td>
                                    <td width="5">&nbsp;</td>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                          <tr>
                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteProcess();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                          </tr>
                                        </table>
                                      </td>
                                      <td width="5">&nbsp;</td>
                                  </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                        <table id="processTable" width="100%" cellpadding="0" cellspacing="0" class="table_border">
                          <tr>
                            <td width="25"  class="tdgrayM"><input type="checkbox" name="processCheck" onClick="javascript:allcheckProcess();"></td>
                            <td width="60"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
                            <td width="220" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02710") %><%--지적사항--%></td>
                            <td width="225" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01235") %><%--대책--%></td>
                            <td width="75"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                            <td width="60"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02651") %><%--조치여부--%></td>
                          </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="640">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                                  <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                                  <td colspan="3" class="tdwhiteM0">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0"  id="processFileTable">
                                                    <tr>
                                                        <td></td>
                                                        <td align="right">
                                                            <table>
                                                                <tr>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertProcessFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                        </tr>
                                                      </table>
                                                    </td>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteProcessFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                          <td width="2"></td>
                                                        </tr>
                                                      </table>
                                                    </td>
                                                  </tr>
                                                </table>
                                                        </td>
                                                    </tr>
                                                    <tr id="processFileTableRow" style="display:none">
                                                        <td align="center" valign="middle" width="24" height="22">
                                                            <input type="checkbox" name="processFileDelete">
                                                        </td>
                                                        <td>
                                                            <input type="file" name="process" id=input onChange='isValidSecondarySize(this)' onkeyDown="this.blur()" style="ime-mode:disabled" class="txt_fieldRO" size="135">
                                                        </td>
                                                    </tr>
                                                </table>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                          <td width="5"></td>
                                        <td>( <%=messageService.getString("e3ps.message.ket_message", "00875") %><%--공정감사 결과 보고서, 회의록 등을 첨부함--%> )</td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                  </table>
                </td>
              </tr>
            </table>

            <!------------------------------------------실적보고서 Tab4 End-------------------------------------->

          </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
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
