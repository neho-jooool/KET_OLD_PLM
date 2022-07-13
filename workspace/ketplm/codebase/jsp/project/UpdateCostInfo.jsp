<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.fc.*,
              wt.org.*,
              wt.project.Role,
              wt.query.*,
              wt.session.*,
              wt.team.*"%>
<%@page import = "e3ps.common.code.*,
              e3ps.common.util.*,
              e3ps.common.web.*,
              e3ps.groupware.company.*,
              e3ps.common.jdf.config.Config,
              e3ps.common.jdf.config.ConfigImpl,
              e3ps.project.*,
              e3ps.project.beans.*,
              e3ps.project.outputtype.OEMProjectType"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
  String oid = request.getParameter("oid");
  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  E3PSProjectData projectData = new E3PSProjectData(project);

  String initType = request.getParameter("initType");
  String projectConsignment = request.getParameter("projectConsignment");
  String projectSite = request.getParameter("projectSite");
  String projectAcceptanceType = request.getParameter("projectAcceptanceType");
  String projectSaleType = request.getParameter("projectSaleType");
  String projectType = "2";
  String pmoid = request.getParameter("pmoid");
  String tempOid = request.getParameter("tempOid");
  String product = request.getParameter("product");
  String pwlinkOid = request.getParameter("pwlinkOid");
  if(pwlinkOid == null){
    pwlinkOid = "";
  }

  if(product == null){
    product ="";
  }

  ProjectManager pm = null;
  if(pmoid == null){
    pmoid = "";
  }else{
    pm = (ProjectManager)CommonUtil.getObject(pmoid);
  }

  if(tempOid == null){
    tempOid = "";
  }

  String oemTypeValue = "";
  if(pm != null){
    OEMProjectType ot = pm.getOemType();
    oemTypeValue = CommonUtil.getOIDString(ot);
  }

  Config conf = ConfigImpl.getInstance();

  String lifecycle = conf.getString("lifecycle.newproject");

  if(initType == null) {
    initType = "produceproject";
  }

  if(projectConsignment == null) {
    projectConsignment = "";
  }

  if(projectSite == null) {
    projectSite = "";
  }

  if(projectAcceptanceType == null) {
    projectAcceptanceType = "";
  }

  if(projectSaleType == null) {
    projectSaleType = "";
  }

  String tmpTitle = "";
  Vector tMap = null;

  String orderCheck = "";
  //2013-03-15 황정태 수정 -이준경 요청으로 투자오더 입력제한없앰
  /*if( project.getProcess() != null && "Proto".equals( project.getProcess().getName() ) )
    orderCheck = "readonly";*/
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<TITLE><%=messageService.getString("e3ps.message.ket_message", "01650") %><%--비용정보 수정--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 10px;
  margin-bottom: 5px;
}
-->
</style>

<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="JavaScript">
<!--

$(document).ready(function(e) {
	
});

window.typeChangeAfter = function(type,row,cellIdx){
    if(type == '금형(로컬)'){
        row.cells[cellIdx].childNodes[0].readOnly = true;
        row.cells[cellIdx].childNodes[0].className = 'txt_fieldRO';
        row.cells[cellIdx].childNodes[0].value = '';
    }else{
        row.cells[cellIdx].childNodes[0].readOnly = false;
        row.cells[cellIdx].childNodes[0].className = 'txt_field';
    }
}


function costTypeListener(obj){
	var costType = obj.value;
    var row = obj.parentNode.parentNode;
    typeChangeAfter(costType,row,6);
    typeChangeAfter(costType,row,8);
    typeChangeAfter(costType,row,9);
}
// str은 0~9까지 숫자만 가능하다.
  function checkNumber(str) {
      var flag=true;
      if (str.length > 0) {
          for (i = 0; i < str.length; i++) {
        	  if(i==0 && str.charAt(i) == '-'){
        		   continue;
        	  }
              if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                  flag=false;
              }
          }
      }
      return flag;
  }

  //Create Function
  function onSave(){

    if(!checkValidate()) {
      return;
    }

//    onProgressBar();

    //document.forms[0].action = "/plm/jsp/project/ActionProductProject.jsp";
    //document.forms[0].submit();
    $.ajax( {
        url : "/plm/jsp/project/ActionProductProject.jsp",
        type : "POST",
        data : $('[name=prodForm]').serialize(),
        async : false,
        success: function(data) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다. --%>');
            opener.location.href='/plm/jsp/project/ProjectExtView4.jsp?oid='+data.replace(/(^\s*)|(\s*$)/gi, '')+'&popup=popup';
            self.close();
        },
        fail : function(){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다. --%>');
            hideProcessing();
        }
    });
  }
  
  function isDuplicate(arr)  {
	  var isDup = arr.some(function(x) {
	    return arr.indexOf(x) !== arr.lastIndexOf(x);
	  });
	                         
	  return isDup;
  }

  //Check Function
  function checkValidate() {
	var checkResult = true;  
    var tBody = document.getElementById("costtable2");
    
    var costTypeArr = new Array(); 
    	
    $('#costtable2 tr').each(function(){
    	var tr = $(this);
    	var td = tr.children();
    	//var text = td.eq(2).text();
    	var costType = td[1].childNodes[0].value;
    	var partNo = td[2].childNodes[1].value;
    	var dieNo = td[3].childNodes[0].value;
    	var costName = td[4].childNodes[0].value;
    	var order = td[5].childNodes[0].value;
    	
    	if(costType == "") {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01647") %>'); //비용관리의 구분을 선택하시기 바랍니다
            checkResult = false;
            return false;
    	}
    	if(costName == ''){
    		alert('<%=messageService.getString("e3ps.message.ket_message", "01646") %>'); //비용관리의 Name을 입력하시기 바랍니다
    		checkResult = false;
    		return false;
    	}
    	
    	if(costType == '금형(로컬)'){
    		if(dieNo == ''){
    			alert("dieNo를 입력하시기 바랍니다.");
    			checkResult = false;
    			return false;
    		}
    		if(order == ''){
                alert("투자오더를 입력하시기 바랍니다.");
                checkResult = false;
                return false;
            }
    		
    	}
    	costTypeArr.push(order);
    });
    
    if(isDuplicate(costTypeArr)){
    	alert("투자오더번호가 중복입니다.");
    	checkResult = false;
    }
    return checkResult;
  }

  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  //비용관리 가져오기 시작

  function addCost(typeValue)
  {
    var targetTable;

    if("mold" == typeValue) {
      targetTable = document.getElementById("costtable");
    }else {
      targetTable = document.getElementById("costtable2");
    }

    tableRows = targetTable.rows.length;
    newTr = targetTable.insertRow(tableRows);

    //
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    if("mold" == typeValue) {
      newTd.innerHTML = "&nbsp;";
    }else {
      newTd.innerHTML = "<input name=\"costoid\" type=\"checkbox\" value=''/>";
    }

    //구분
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    if("mold" == typeValue) {
      newTd.innerHTML = "<input type=hidden name=costType value='금형'><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%>";
    }else {
      newTd.innerHTML = "<select onchange=\"javascript:costTypeListener(this);\" name=\"costType\" class=\"fm_jmp\" style=\"width:100%\"><option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option><option value=\"설비\"><%=messageService.getString("e3ps.message.ket_message", "01874") %><%--설비--%></option><option value=\"JIG\">JIG</option><option value=\"금형(로컬)\">금형(로컬)</option><option value=\"설비(로컬)\">설비(로컬)</option><option value=\"JIG(로컬)\">JIG(로컬)</option><option value=\"경비(로컬)\">경비(로컬)</option></select>";
    }

    //Part No
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=hidden name=moldTypeCost>";
    if("mold" == typeValue) {
      newTd.innerHTML += "<input type=text name=\"partNoCost\" class=\"txt_field\" style=\"width:70px\" readonly>";
    }else {
      newTd.innerHTML += "<input type=text name=\"partNoCost\" class=\"txt_field\" style=\"width:70px\">";
    }

    //Die No
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    if("mold" == typeValue) {
      newTd.innerHTML = "<input type=text name=\"dieNoCost\" class=\"txt_field\" style=\"width:60px\" readonly>";
    }else {
      newTd.innerHTML = "<input type=text name=\"dieNoCost\" class=\"txt_field\" style=\"width:60px\">";
    }

    //Name
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    if("mold" == typeValue) {
      newTd.innerHTML = "<input type=text name=\"costName\" class=\"txt_field\"  style=\"width:165px\" readonly>";
    }else {
      newTd.innerHTML = "<input type=text name=\"costName\" class=\"txt_field\"  style=\"width:165px\">";
    }

    //투자오더
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=text name=\"order\" class=\"txt_field\" style=\"width:70px\" <%=orderCheck%>>";

    //목표가
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=text name=\"targetCost\" class=\"txt_field\"   style=\"width:70px\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";
    
    //집행가
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=text name=\"executionCost\" class=\"txt_fieldRO\" readonly style=\"width:70px\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

    //수정비
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<input type=text name=\"editCost\" class=\"txt_field\"  style=\"width:70px\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

    //확정가
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=text name=\"decideCost\" class=\"txt_field\"  style=\"width:70px\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

    
  }

  function deleteCost2() {

    var form = document.forms[0];

    if(form.costoid == "undefined" ||  form.costoid == null || form.costoid.length == "undefined"){
      return;
    }else{

      index = form.costoid.length;
      if(index > 1) {
        for(i=index; i>0; i--) {
          if(form.costoid[i-1].checked == true) {
            costtable2.deleteRow(i-1);
          }
        }
      }else{
        if(form.costoid.checked == true || form.costoid[0].checked == true) {
          costtable2.deleteRow(0);
        }
        return;
      }
    }
  }
  function deleteCost() {
    //alert("rrrrrr");
    var form = document.forms[0];
    var tBody = document.getElementById("costtable2");
    var rowCnt = tBody.rows.length;
    
    for(i=0; i<rowCnt;i++){
    	var row = tBody.rows[i];
        var chkBox = row.cells[0].childNodes[0];

        if(chkBox != null && chkBox.checked == true){
        	tBody.deleteRow(i);
            rowCnt--;
            i--;
        }
    }

  }

  //비용관리 가져오기 끝
  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

function checkAll(obj, selectObj) {
/*  form = document.forms[0];
  if(form.oid) {
    var chkLen = form.oid.length;
    if(chkLen) {
      for(var i = 0; i < chkLen; i++) {
        form.oid[i].checked = form.chkAll.checked;
      }
    }else{
      form.oid.checked = form.chkAll.checked;
    }
  }
*/
  if(selectObj) {
    var chkLen = selectObj.length;
    if(chkLen) {
      for(var i = 0; i < chkLen; i++) {
        selectObj[i].checked = obj.checked;
      }
    }else{
      selectObj.checked = obj.checked;
    }
  }

}

  
  function setOrderRate(dieNo){
	  if(dieNo == '' || dieNo == '미입력'){
		  alert('Die No가 미입력  입니다.')
		  return;
	  }
	  var selectedIndex = 0;
      for(var i=0; i<$('[name=dieNoCost]').length;i++){
    	  if(dieNo == $('[name=dieNoCost]')[i].value){
    		  selectedIndex = i;
    		  break;
    	  }
      }
	  if($('[name=costType]')[i].value == '금형' && $('[name=dieNoCost]')[i].value != '' && $('[name=dieNoCost]')[i].value != '미입력'){
    		$.ajax({
    			url : '/plm/ext/sap/getOrderCostByDieNo.do',
    			async : false,
    			data : {
    				dieNo : $('[name=dieNoCost]')[i].value
    			},
    			success : function(result){
    				if(result && result.I_AUFNR){
    					$('[name=order]')[i].value = result.I_AUFNR; //투자오더
    					$('[name=decideCost]')[i].value = result.E_WOGBTR;//확정가
    					$('[name=executionCost]')[i].value = result.E_WTGES_R;//초기비용
    					$('[name=targetCost]')[i].value = result.E_WTGES_B;//초기비용
    					$('[name=editCost]')[i].value = result.E_WTGES_S;//추가비용
    				}else{
    					alert('투자오더 정보가 없습니다.');
    					return;
    				}
    				
    			}
    		});    	    	
	  }    	  
      
  }
//-->
</script>
</head>
<body >
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form method="post" name="prodForm">
        <!-- hidden begin -->
        <input type="hidden" name="initType" value="<%=initType%>"> 
        <input type="hidden" name="tempProjectNo" value=""> 
        <input type="hidden" name="lifecycle" value="Default"> 
        <input type="hidden" name="pmoid" value="<%=pmoid%>"> 
        <input type="hidden" name="pwlinkOid" value="<%=pwlinkOid%>"> 
        <input type="hidden" name="projectType" value="<%=projectType%>">
        <input type="hidden" name="deletePOid" value=""> 
        <input type="hidden" name="oid" value="<%=oid%>"> 
        <input type="hidden" name="command" value="CostInfoUpdate">
        <!-- hidden end -->
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="../../portal/images/logo_popupbg.png">
                                <table height="28" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="../../portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01650") %><%--비용정보 수정--%></td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="100%" height="200" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top" align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                        <td width="5">&nbsp;</td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <td><table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table></td>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
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
                                            <!--내용-->
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01643") %><%--비용관리--%></td>

                                                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td align="right" valign="bottom"><%=messageService.getString("e3ps.message.ket_message", "01195") %><%--단위 : 원--%>
                                                                    &nbsp;</td>
                                                                <!-- <td><table border="0" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue"
                                                                                background="../../portal/images/btn_bg1.gif"><a
                                                                                href="#" onClick="javascript:setOrderRate();"
                                                                                class="btn_blue">투자오더 가져오기</a></td>
                                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                        </tr>
                                                                    </table></td>
                                                                <td width="5">&nbsp;</td> -->
                                                                <td><table border="0" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue"
                                                                                background="../../portal/images/btn_bg1.gif"><a
                                                                                href="#" onClick="javascript:addCost('general');"
                                                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                        </tr>
                                                                    </table></td>
                                                                <td width="5">&nbsp;</td>
                                                                <td><table border="0" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue"
                                                                                background="../../portal/images/btn_bg1.gif"><a
                                                                                href="#" onClick="javascript:deleteCost();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                        </tr>
                                                                    </table></td>
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
                                                    <td class="tab_btm2"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="costtable" style="table-layout: fixed;">
                                                <COL width="30px">
                                                <COL width="60px">
                                                <COL width="75px">
                                                <COL width="70px">
                                                <COL width="*">
                                                <COL width="90px">
                                                <COL width="80px">
                                                <COL width="80px">
                                                <COL width="80px">
                                                <COL width="80px">
                                                <tr>
                                                    <td class="tdblueM" rowspan=2>&nbsp; <!-- input type="checkbox" name="chkAll2" onclick="javascript:checkAll(this, document.forms[0].costoid);" --></td>
                                                    <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                                                    <td class="tdblueM" rowspan=2>Part No</td>
                                                    <td class="tdblueM" rowspan=2>Die No</td>
                                                    <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02942") %><%--투자명--%></td>
                                                    <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02944") %><%--투자오더--%></td>
                                                    <td class="tdblueM" colspan=3><%=messageService.getString("e3ps.message.ket_message", "02143") %><%--예산--%></td>
                                                    <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02742") %><%--집행--%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueM">계획</td>
                                                    <td class="tdblueM">기초</td>
                                                    <td class="tdblueM">증액+삭감</td>
                                                    <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "03229") %><%--확정가--%></td>
                                                </tr>
                                                <TBODY id="costtable2">
                                            </table>
                                        </td>
                                        <td width="10" background="../../portal/images/box_15.gif">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width="10" height="10"><img src="../../portal/images/box_11.gif"></td>
                                        <td height="10" background="../../portal/images/box_16.gif"></td>
                                        <td width="10" height="10"><img src="../../portal/images/box_12.gif"></td>
                                    </tr>
                                </table>
                        </tr>
                    </table></td>
            </tr>
        </table>
    </form>
</body>
</html>

<%
  QuerySpec spec = new QuerySpec();
  int idx = spec.addClassList(MoldItemInfo.class, true);
  SearchCondition sc = new SearchCondition(MoldItemInfo.class, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(project) );
  spec.appendWhere(sc, new int[] { idx });

  spec.appendAnd();
  spec.appendOpenParen();
  sc = new SearchCondition(MoldItemInfo.class, "itemType", SearchCondition.EQUAL, "Press" );
  spec.appendWhere(sc, new int[] { idx });
  spec.appendOr();
  sc = new SearchCondition(MoldItemInfo.class, "itemType", SearchCondition.EQUAL, "Mold" );
  spec.appendWhere(sc, new int[] { idx });
  spec.appendCloseParen();
  spec.appendAnd();
  spec.appendWhere(new SearchCondition(MoldItemInfo.class, "shrinkage", SearchCondition.EQUAL , "신규"), new int[]  {idx});

  e3ps.common.query.SearchUtil.setOrderBy(spec, MoldItemInfo.class, idx, "dieNo", "dieNo", false);

  QueryResult rt = PersistenceHelper.manager.find(spec);

  String dieNoValue = "";

  while(rt.hasMoreElements()){
    Object[] obj = (Object[])rt.nextElement();
    MoldItemInfo miInfo = (MoldItemInfo)obj[0];

    if(!dieNoValue.equals(miInfo.getDieNo())) {
      QuerySpec specCost = new QuerySpec();
      int idx_Cost = specCost.addClassList(CostInfo.class, true);
      SearchCondition scCost = new SearchCondition(CostInfo.class, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(project) );
      specCost.appendWhere(scCost, new int[] { idx_Cost });

      specCost.appendAnd();
      scCost = new SearchCondition(CostInfo.class, "costType", SearchCondition.EQUAL, "금형" );
      specCost.appendWhere(scCost, new int[] { idx_Cost });

      specCost.appendAnd();
      scCost = new SearchCondition(CostInfo.class, "dieNo", SearchCondition.EQUAL, miInfo.getDieNo() );
      specCost.appendWhere(scCost, new int[] { idx_Cost });


      //e3ps.common.query.SearchUtil.setOrderBy(specCost, CostInfo.class, idx_Cost, "dieNo", "dieNo", true);

      QueryResult rtCost = PersistenceHelper.manager.find(specCost);
      CostInfo costInfo = null;
      if(rtCost.hasMoreElements()){
         Object[] costObj = (Object[])rtCost.nextElement();
        costInfo = (CostInfo)costObj[0];
      }
      String partNoStr = miInfo.getPartNo()!=null ? miInfo.getPartNo() : "";
      String partNoStrL = StringUtil.getLeft(partNoStr, 8);
      String partNameStr = miInfo.getPartName()!=null? miInfo.getPartName() : "" ;
      String partNameStrL = StringUtil.getLeft(partNameStr, 24);

%>
    <script>
      var targetTable = document.getElementById("costtable");

      tableRows = targetTable.rows.length;
      newTr = targetTable.insertRow(tableRows);

      //
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;<input name=\"costoid\" type=\"hidden\" value=''/>";

      //구분
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type=hidden name=costType value='금형'><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%>";

      //Part No
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.title = "<%=partNoStr%>";
      newTd.innerHTML = "<input type=hidden name=moldTypeCost <%if(costInfo!=null) {%>value=\"<%=costInfo.getMoldType() != null ? costInfo.getMoldType().getName() : ""%>\"<%}%>>";
      newTd.innerHTML += "<input type=hidden name=\"partNoCost\" value=\"<%=miInfo.getPartNo()!=null ? miInfo.getPartNo() : ""%>\"><%=miInfo.getPartNo()!=null ? partNoStrL : "&nbsp;"%>";

      //Die No
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type=hidden name=\"dieNoCost\" value=\"<%=miInfo.getDieNo()!=null ? miInfo.getDieNo() : ""%>\"><%=miInfo.getDieNo()!=null ? miInfo.getDieNo() : "&nbsp;"%>";

      //Name
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.title = "<%=partNameStr%>";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type=hidden name=\"costName\" value=\"<%=miInfo.getPartName()!=null ? miInfo.getPartName() : ""%>\">";
      newTd.innerHTML += "<div style=\"width: 95%; text-align:left; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;\"><nobr><%=miInfo.getPartName()!=null ? partNameStrL : "&nbsp;"%></div>";

      //투자오더
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type=text name=\"order\" class=\"txt_fieldRO\" style=\"width:60px\" readonly <%=orderCheck%> <%if(costInfo!=null) {%>value=\"<%=costInfo.getOrderInvest()!=null ? costInfo.getOrderInvest() : ""%>\"<%}%>> <a href=\"javascript:setOrderRate('<%=miInfo.getDieNo()!=null ? miInfo.getDieNo() : ""%>');\"><img src=\"/plm/extcore/image/coins.png\"></a>";

      //목표가
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type=text name=\"targetCost\" class=\"txt_fieldRO\" style=\"width:70px\" readonly <%if(costInfo!=null) {%>value=\"<%=costInfo.getTargetCost()!=null ? costInfo.getTargetCost() : ""%>\"<%}%> onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";
      
      //집행가
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type=text name=\"executionCost\" class=\"txt_fieldRO\" style=\"width:70px\" readonly <%if(costInfo!=null) {%>value=\"<%=costInfo.getExecutionCost()!=null ? costInfo.getExecutionCost() : ""%>\"<%}%> onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

      //수정비
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<input type=text name=\"editCost\" class=\"txt_fieldRO\" style=\"width:70px\" readonly <%if(costInfo!=null) {%>value=\"<%=costInfo.getEditCost()!=null ? costInfo.getEditCost() : ""%>\"<%}%> onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";
      
      //집행가
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type=text name=\"decideCost\" class=\"txt_fieldRO\" style=\"width:70px\" readonly <%if(costInfo!=null) {%>value=\"<%=costInfo.getDecideCost()!=null ? costInfo.getDecideCost() : ""%>\"<%}%> onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

      
    </script>
<%
    }
    dieNoValue = miInfo.getDieNo();
  }

  QuerySpec specCost = new QuerySpec();
  int idx_Cost = specCost.addClassList(CostInfo.class, true);
  SearchCondition scCost = new SearchCondition(CostInfo.class, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(project) );
  specCost.appendWhere(scCost, new int[] { idx_Cost });

  specCost.appendAnd();
  scCost = new SearchCondition(CostInfo.class, "costType", SearchCondition.NOT_EQUAL, "금형" );
  specCost.appendWhere(scCost, new int[] { idx_Cost });

  QueryResult rtCost = PersistenceHelper.manager.find(specCost);
  while(rtCost.hasMoreElements()){
     Object[] obj = (Object[])rtCost.nextElement();
    CostInfo costInfo = (CostInfo)obj[0];
    String costType_ = costInfo.getCostType();
    String tdClass = "txt_field";
    String readOnly = "";
    if("금형(로컬)".equals(costType_)){
	    tdClass = "txt_fieldRO";
	    readOnly = "readOnly";
    }
%>
  <script>
    var targetTable = document.getElementById("costtable2");

    tableRows = targetTable.rows.length;
    newTr = targetTable.insertRow(tableRows);
    //costoid
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input name=\"costoid\" type=\"checkbox\" value='<%=CommonUtil.getOIDString(costInfo)%>'/>";

    //구분
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<select onchange=\"javascript:costTypeListener(this);\" name=\"costType\" class=\"fm_jmp\" style=\"width:98%\"><option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option><option value=\"설비\" <%="설비".equals(costInfo.getCostType()) ? "selected" : ""%>><%=messageService.getString("e3ps.message.ket_message", "01874") %><%--설비--%></option><option value=\"JIG\" <%="JIG".equals(costInfo.getCostType()) ? "selected" : ""%>>JIG</option><option value=\"금형(로컬)\" <%="금형(로컬)".equals(costInfo.getCostType()) ? "selected" : ""%>>금형(로컬)</option><option value=\"설비(로컬)\" <%="설비(로컬)".equals(costInfo.getCostType()) ? "selected" : ""%>>설비(로컬)</option><option value=\"JIG(로컬)\" <%="JIG(로컬)".equals(costInfo.getCostType()) ? "selected" : ""%>>JIG(로컬)</option><option value=\"경비(로컬)\" <%="경비(로컬)".equals(costInfo.getCostType()) ? "selected" : ""%>>경비(로컬)</option></select>";

    //Part No
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=hidden name=moldTypeCost value=\"<%=costInfo.getMoldType() != null ? costInfo.getMoldType().getName() : ""%>\">";
    newTd.innerHTML += "<input type=text name=\"partNoCost\" class=\"txt_field\" style=\"width:95%\" value=\"<%=costInfo.getPartNo()!=null ? costInfo.getPartNo() : ""%>\">";

    //Die No
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=text name=\"dieNoCost\" class=\"txt_field\" style=\"width:95%\" value=\"<%=costInfo.getDieNo()!=null ? costInfo.getDieNo() : ""%>\">";

    //Name
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=text name=\"costName\" class=\"txt_field\" style=\"width:95%\" value=\"<%=costInfo.getCostName()!=null ? costInfo.getCostName() : ""%>\">";

    //투자오더
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=text name=\"order\" class=\"txt_field\" style=\"width:95%\" <%=orderCheck%> value=\"<%=costInfo.getOrderInvest()!=null ? costInfo.getOrderInvest() : ""%>\">";

    //목표가
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    
    newTd.innerHTML = "<input type=text name=\"targetCost\" class=\"<%=tdClass%>\" style=\"width:70px\" <%=readOnly%> value=\"<%=costInfo.getTargetCost()!=null ? costInfo.getTargetCost() : ""%>\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

    //집행가
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=text name=\"executionCost\" class=\"txt_fieldRO\" style=\"width:70px\" readonly value=\"<%=costInfo.getExecutionCost()!=null ? costInfo.getExecutionCost() : ""%>\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

    //수정비
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<input type=text name=\"editCost\" class=\"<%=tdClass%>\" style=\"width:70px\" <%=readOnly%> value=\"<%=costInfo.getEditCost()!=null ? costInfo.getEditCost() : ""%>\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";
    
    //확정가
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input type=text name=\"decideCost\" class=\"<%=tdClass%>\" style=\"width:70px\" <%=readOnly%> value=\"<%=costInfo.getDecideCost()!=null ? costInfo.getDecideCost() : ""%>\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\">";

    
  </script>
<%
  }
%>
