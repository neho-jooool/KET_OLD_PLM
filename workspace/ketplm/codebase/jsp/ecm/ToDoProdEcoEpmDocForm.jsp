<%@page import="java.sql.Timestamp"%>

<%@page import="e3ps.common.util.*"%>
<%@page import="e3ps.ecm.entity.*"%>
<%@page import="ext.ket.part.util.*"%>
<%@page import="ext.ket.bom.query.KETBOMQueryBean"%>

<%
KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">
.headerLock4 {
  position: relative;
  top:expression(document.getElementById("div_scroll4").scrollTop);
  z-index:99;
 }

</style>
<script type="text/javascript">
<!--
<% /* 저장 */ %>
function doSave4Epm()
{
  var form = document.forms[0];

  document.forms[0].isChanged.value = 'N';
  document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
  document.forms[0].cmd.value='Modify';
  document.forms[0].target='download';
  disabledAllBtn();
  showProcessing();
  document.forms[0].submit();
  
}

<% /* 작업완료 */ %>
function doComplete4Epm()
{

    if( document.forms[0].isChanged.value == 'N' ) {
        // Do nothing..!!
    } else {
        // <entry key="04024">변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.</entry>
        alert('<%=messageService.getString("e3ps.message.ket_message", "04024") %><%--변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.--%>');
        return;
    }
    
    
    document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
    document.forms[0].cmd.value='Complete';
    //document.forms[0].target='_self';
    document.forms[0].target='download';
    disabledAllBtn();
    showProcessing();
    document.forms[0].submit();
    
}

function lfn_feedback_reStart(msg){
	if(confirm(msg)){
		document.forms[0].budgetYn.value = "Y";
		doComplete4Epm();
	}else{
		try { opener.location.reload(); } catch(e) {}
	}
}

function lfn_feedback_After_Complete() {
    //alert('lfn_feedback_After_Complete');
    try { opener.location.reload(); } catch(e) {}
    window.close();
}

// 도면 검색 팝업 호출
function popupRelBom2() {
    var url = "/plm/jsp/ecm/SearchActivityPopupForm.jsp"
	        //+ "?obj=prodMoldCls^1&obj=mode^multi&obj=partType^P";
	        + "?prodMoldCls=1&mode=multi&partType=P";


    // 기본사항에 추가된 제품
    var pNums = "";
    var relPartNumber = document.getElementsByName("relPartNumber");
    var relPartNumberLength = (relPartNumber != null) ? relPartNumber.length : 0;
    for(var i=0; i < relPartNumberLength; i++) {
        pNums += "&pNums="+  relPartNumber[i].value;
        
    }
    url += pNums;

    // 설변부품/도면에 추가된 부품
    /* 
    var pNums2 = "";
    $("#relEpmTable").find("[name=addedPartNumberAtEca]").each(function(i) {
        pNums2 += "&pNums2="+  $(this).val();
        
    });
    url += pNums2;
    */
    
    // 설계변경사유
    /* 
    $("[name=chk_chg_reason]").each(function(i) {
        if($(this).is(':checked')) {
            if($(this).val() == 'REASON_12') {
                url += "&isChodo=Y";
                return;
            }
        }
    });
    */
    <%
    if(isTheFirst) {
    %>
    url += "&isChodo=Y";
    <%
    }
    %>

    // ECO No
    url += "&currentEcoNo="+ $("[name=ecoId]").val();
        
    openWindow(url,"popupRelEpm2","1000","600","status=1,scrollbars=no,resizable=no");
    //attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=725px; dialogHeight:570px; center:yes");
    
}

<% /* deprecated */ %>
function popupRelEpm() {
	var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchActivityPopupForm.jsp";
	url += "&obj=prodMoldCls^1&obj=mode^multi&obj=ecoEcaCls^eca&obj=epmBomCls^1";
	//openWindow(url,"","770","600","status=1,scrollbars=no,resizable=no");
	attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=900px; dialogHeight:580px; center:yes");
	if(typeof attache == "undefined" || attache == null) {
	  return;
	}
	//var attache = getSampleRelEpmData();
	insertRelEpmLine(attache);
}

//변경예정일 팝업
<% /* deprecated */ %>
function popupEpmCal(objName, tableId){
	var form = document.forms[0];
	var targetTable = document.getElementById(tableId);
	var tableRows = targetTable.rows.length;
	var pos = eval(tableId).clickedRowIndex - 1;
	if(tableRows > 2){
	  objName = objName + "[" + pos + "]";
	}
	showCal(objName);
}

//변경예정일 초기화
<% /* deprecated */ %>
function clearEpmCal(objName, tableId){
var form = document.forms[0];
var targetTable = document.getElementById(tableId);
var tableRows = targetTable.rows.length;
var pos = eval(tableId).clickedRowIndex - 1;
if(tableRows > 2){
  objName = objName + "[" + pos + "]";
}
eval("document.forms[0]." + objName).value = '';
}

//도면 라인추가
function insertRelEpmLine(objArr) {
	if(objArr.length == 0) {
	  return;
	}
	var targetTable = document.getElementById("relEpmTable");
	
	var trArr;
	var str = "";
	 var isAdded = false;
	for(var i = 0; i < objArr.length; i++) {
	  trArr = objArr[i];
	  
	  //if( !isDuplicateEpm(trArr[1]) )
	  if( !isDuplicateLine2(trArr[0], trArr[2])) 
	  {
		  var tableRows = targetTable.rows.length;
		  var newTr = targetTable.insertRow(tableRows);
		  newTr.height="29";
		  newTr.onmouseover=function(){relEpmTable.clickedRowIndex=this.rowIndex};
		  var currRow = targetTable.rows.length - 2;
		
		  //전체선택 checkbox
		  newTd = newTr.insertCell(newTr.cells.length);
		  newTd.className = "tdwhiteM";
		  
		  var uniqueTime = lfn_getUniqueTime();
	      str = "";
		  str += "<input type='hidden' name='relEcaActivityType' value='" + trArr[0] + "'>";
		  str += "<input type='hidden' name='relEcaLinkOid' value=''>";
		  str += "<input type='hidden' name='relObjOid' value='" + trArr[1] + "'>";
	      str += "<input type='hidden' name='relEcaEpmNo' value='" + trArr[2] + "'>";
		  str += "<input type='hidden' name='relObjPreRev' value='" + trArr[4] + "'>";
		  str += "<input type='hidden' name='relEcaEpmAfterRev' value=''>";
		  str += "<input type='hidden' name='prodEcaOid' value=''>";
		  str += "<input type='hidden' name='relEcaWorkerOid' value='" + trArr[5] + "'>";
		  str += "<input type='hidden' name='relEcaPlanDate' value=''>";
		  str += "<input type='hidden' name='relEcaEpmChangeYn' value='N'>";
		  str += "<input type='hidden' name='relEcaEpmDocReviseYn' value='N'>";
		  str += "<input type='hidden' name='relEcaEpmDocType' value='" + trArr[7] + "'>";
		  str += "<input type='hidden' name='relEcaActivityComment'>";
		  
	      str += "<input type='checkbox' name='chkSelectRelEpm' value='"+ uniqueTime +"'>";
	      str += "<input type='hidden' name='ecaUniqueKey' value='"+ uniqueTime +"'>";
		  newTd.innerHTML = str;
		
		  //구분
		  /* 
		  newTd = newTr.insertCell(newTr.cells.length);
		  newTd.className = "tdwhiteM";
		  newTd.innerHTML = trArr[7];
		  */
		  
          //도면유형
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.className = "tdwhiteM";
          newTd.innerHTML = trArr[13];
        
		  //도면번호
		  newTd = newTr.insertCell(newTr.cells.length);
		  newTd.className = "tdwhiteM";
		  newTd.innerHTML = "<font title='"+trArr[2]+"'><div class='ellipsis' style='width:95;'><nobr>"
		                    + trArr[2] +"</nobr></div></font>";
		
		  //도면명
		  newTd = newTr.insertCell(newTr.cells.length);
		  newTd.className = "tdwhiteL";
		  newTd.innerHTML = "<font title=\""+trArr[3]+"\"><div class='ellipsis' style='width:115;'><nobr>"
		                    + trArr[3] +"</nobr></div></font>";
		
		  //작업
		  newTd = newTr.insertCell(newTr.cells.length);
		  newTd.className = "tdwhiteM";
		  str = "";
		//  if(trArr[7] == "2D") {
		//    str = str + "<td width='' class='tdwhiteM0' align='left'><table border='0' cellspacing='0' cellpadding='0'>";
		//    str = str + "<tr>";
		//    str = str + "<td><table border='0' cellspacing='0' cellpadding='0'>";
		//    str = str + "<tr>";
		//    str = str + "<td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
		//    str = str + "<td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:changeEpmDoc();' class='btn_blue' onfocus='this.blur();'>도면변경</a></td>";
		//    str = str + "<td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
		//    str = str + "</tr>";
		//    str = str + "</table></td>";
		//    str = str + "</tr>";
		//    str = str + "</table></td>";
		//  } else {
		//    str = "&nbsp;";
		//  }
		  str = "&nbsp;";
		  newTd.innerHTML = str;
		
		  //PrevRev
		  newTd = newTr.insertCell(newTr.cells.length);
		  newTd.className = "tdwhiteM";
		  newTd.innerHTML = trArr[4];
		
		  //NewRev
		  newTd = newTr.insertCell(newTr.cells.length);
		  newTd.className = "tdwhiteM";
		  newTd.innerHTML = "&nbsp;";
		
		  //연계일정
		  newTd = newTr.insertCell(newTr.cells.length);
		  newTd.className = "tdwhiteM";
		  str = "";
		  //if( isExistMoldChg() )
		  //{
		    str += "<input type='hidden' name='oldMoldChangePlanOid' value=''>";
		    str += "<input type='hidden' name='newMoldChangePlanOid' value=''>";
		    str += "<input type='hidden' name='moldEcoPlanLinkOid' value=''>";
		    str += "<input type='hidden' name='dieNo' value=''>";
		    str += "<input type='text' name='scheduleId' class='txt_fieldRO' style='width:80px' readonly>";
		    str += "&nbsp;<a href=\"javascript:popupRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
		    str += "&nbsp;<a href=\"javascript:clearRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
		  //}
		  //else
		  //{
		//    str += "<input type='hidden' name='oldMoldChangePlanOid' value=''>";
		//    str += "<input type='hidden' name='newMoldChangePlanOid' value=''>";
		//    str += "<input type='hidden' name='moldEcoPlanLinkOid' value=''>";
		//    str += "<input type='hidden' name='scheduleId' value=''>";
		//    str += "-<input type='hidden' name='dieNo' value=''>";
		  //}
		  newTd.innerHTML = str;
		
		  //변경내용
		  /* 
		  newTd = newTr.insertCell(newTr.cells.length);
		  newTd.className = "tdwhiteM0";
		  str = "";
		  str += "<input type='text' name='relEcaActivityComment' class='txt_field'  style='width:90%'>";
		  newTd.innerHTML = str;
		  */
		  
		  document.forms[0].isChanged.value = 'Y';
		  isAdded = true;
	  }else{
		  isAdded = false;
	  }
	}

	if(isAdded) {
	      alert('<%=messageService.getString("e3ps.message.ket_message", "04045") %><%--\'선택\'하신 Part가 추가되었습니다.--%>');
	      newwin.window.focus();
	  }else{
	      alert("중복된 부품이나 도면을 추가 하실 수 없습니다.");
	  }
}
function lfn_getUniqueTime() {
    var time = new Date().getTime();
    while (time == new Date().getTime());
    return new Date().getTime();
}

function isDuplicateEpm( item_code )
{
    var isDuplicate = false;
    var epmList = document.getElementsByName("relObjOid");

    for( var inx=0; inx < epmList.length; inx++ )
    {
        if( epmList[inx].value == item_code )
        {
            isDuplicate = true;
        }
    }
    
    return isDuplicate;
}


function isDuplicateLine2( type, number )
    {
	
	
    var isDuplicate = false;
    var numberList = document.getElementsByName("relEcaEpmNo");
    var typeList = document.getElementsByName("relEcaActivityType");
    
    for( var i=0; i < numberList.length ; i++)
    {
      if( number == numberList[i].value && type == typeList[i].value )
      {
        isDuplicate = true;
      }
    }
    
    var bomNoList = document.getElementsByName("bomNo");
    
    alert(bomNoList.length);
    
    for( var i=0; i < bomNoList.length ; i++)
    {
      if( number == bomNoList[i].value)
      {
        isDuplicate = true;
      }
    }
    
    return isDuplicate;
}

//도면개정
function reviseEpmDoc2() {
	
    if( document.forms[0].isChanged.value == 'N' ) {
        // Do nothing..!!
    } else {
        // <entry key="04024">변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.</entry>
        alert('<%=messageService.getString("e3ps.message.ket_message", "04024") %><%--변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.--%>');
        return;
    }
    
        
    var body = document.getElementById("relEpmTable");
    if (body.rows.length < 3) return;
    var formNameStr = "document.forms[0].";
    var form = document.forms[0];
    var objChecks = eval(formNameStr + "chkSelectRelEpm");
    
    var listVal = "";
    
    var chkComp = document.getElementsByName("chkSelectRelEpm");
    var afterRev = document.getElementsByName("relEcaEpmAfterRev");
    
    var rtn = false;
    for( var t=0; t <chkComp.length; t++)
    {
      if( chkComp[t].checked && afterRev[t].value == '' )
      {
        rtn = true;
      }
    }
    
    var size = body.rows.length - 2;
    var selCnt = 0;
    var curRow = 0;
    if( rtn )
    {
       if( form.isChanged.value == 'N' )
       {
        if(size <= 1) {
          if (objChecks.checked || objChecks[0].checked) {
            if(objChecks.checked &&  form.relEcaEpmAfterRev.value == '' ) {
              listVal = form.relObjOid.value;
              form.relEcaEpmDocReviseYn.value = "Y";
            } else if(objChecks[0].checked &&  form.relEcaEpmAfterRev[0].value == '' ) {
              listVal = form.relObjOid[0].value;
              form.relEcaEpmDocReviseYn[0].value = "Y";
            }
            selCnt++;
          }
        } else {
          for (var i = body.rows.length; i > 2; i--) {
            curRow = i - 3;
            if (objChecks[curRow].checked &&  form.relEcaEpmAfterRev[curRow].value == '' ) {
              if(selCnt < 1) {
                listVal = form.relObjOid[curRow].value;
                form.relEcaEpmDocReviseYn[curRow].value = "Y";
              } else {
                listVal += "*" + form.relObjOid[curRow].value;
                form.relEcaEpmDocReviseYn[curRow].value = "Y";
              }
              selCnt++;
            }
          }
        }
      }
      else
      {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02456") %><%--저장 후 개정을 수행하세요--%>');
        return;
      }
    }
    //form.reviseRelBomList.value = "";
    if(selCnt > 0) {
    //  form.reviseRelBomList.value = "listVal";
    //  alert("개정:" + listVal);
      doRevise('ReviseEpmDoc');
    }
    else
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "04720") %><%--개정할 도면을 선택하시기 바랍니다.--%>");
    }
}

<% /* @deprecated */ %>
function reviseEpmDoc() {
	var body = document.getElementById("relEpmTable");
	if (body.rows.length < 3) return;
	var formNameStr = "document.forms[0].";
	var form = document.forms[0];
	var objChecks = eval(formNameStr + "chkSelectRelEpm");
	
	var listVal = "";
	
	var chkComp = document.getElementsByName("chkSelectRelEpm");
	var afterRev = document.getElementsByName("relEcaEpmAfterRev");
	
	var rtn = false;
	for( var t=0; t <chkComp.length; t++)
	{
	  if( chkComp[t].checked && afterRev[t].value == '' )
	  {
	    rtn = true;
	  }
	}
	
	var size = body.rows.length - 2;
	var selCnt = 0;
	var curRow = 0;
	if( rtn )
	{
	   if( form.isChanged.value == 'N' )
	   {
	    if(size <= 1) {
	      if (objChecks.checked || objChecks[0].checked) {
	        if(objChecks.checked &&  form.relEcaEpmAfterRev.value == '' ) {
	          listVal = form.relObjOid.value;
	          form.relEcaEpmDocReviseYn.value = "Y";
	        } else if(objChecks[0].checked &&  form.relEcaEpmAfterRev[0].value == '' ) {
	          listVal = form.relObjOid[0].value;
	          form.relEcaEpmDocReviseYn[0].value = "Y";
	        }
	        selCnt++;
	      }
	    } else {
	      for (var i = body.rows.length; i > 2; i--) {
	        curRow = i - 3;
	        if (objChecks[curRow].checked &&  form.relEcaEpmAfterRev[curRow].value == '' ) {
	          if(selCnt < 1) {
	            listVal = form.relObjOid[curRow].value;
	            form.relEcaEpmDocReviseYn[curRow].value = "Y";
	          } else {
	            listVal += "*" + form.relObjOid[curRow].value;
	            form.relEcaEpmDocReviseYn[curRow].value = "Y";
	          }
	          selCnt++;
	        }
	      }
	    }
	  }
	  else
	  {
	    alert('<%=messageService.getString("e3ps.message.ket_message", "02456") %><%--저장 후 개정을 수행하세요--%>');
	    return;
	  }
	}
	//form.reviseRelBomList.value = "";
	if(selCnt > 0) {
	//  form.reviseRelBomList.value = "listVal";
	//  alert("개정:" + listVal);
	  doRevise('ReviseEpmDoc');
	}
	else
	{
	  alert("<%=messageService.getString("e3ps.message.ket_message", "00692") %><%--개정할 도면이 없습니다--%>");
	}
}

//도면 개정취소
function cancelRevEpmDoc2() {

    if( document.forms[0].isChanged.value == 'N' ) {
        // Do nothing..!!
    } else {
        // <entry key="04024">변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.</entry>
        alert('<%=messageService.getString("e3ps.message.ket_message", "04024") %><%--변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.--%>');
        return;
    }
    
    
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03286") %><%--개정을 취소하시겠습니까?--%>")) return;
    
    var body = document.getElementById("relEpmTable");
    if (body.rows.length < 3) return;
    var formNameStr = "document.forms[0].";
    var form = document.forms[0];
    var objChecks = eval(formNameStr + "chkSelectRelEpm");
    
    var chkComp = document.getElementsByName("chkSelectRelEpm");
    var afterRev = document.getElementsByName("relEcaEpmAfterRev");
    
    var rtn = false;
    for( var t=0; t <chkComp.length; t++)
    {
      if(chkComp[t].checked && afterRev[t].value != '' )
      {
        rtn = true;
      }
    }
    var listVal = "";
    
    var size = body.rows.length - 2;
    var selCnt = 0;
    var curRow = 0;
    if( rtn )
    {
       if( form.isChanged.value == 'N' )
       {
        if(size <= 1) {
          if (objChecks.checked || objChecks[0].checked) {
            if(objChecks.checked) {
              if(Number(form.relObjPreRev.value) < Number(form.relEcaEpmAfterRev.value)) {
                form.relEcaEpmDocCancelRevYn.value = "Y";
                selCnt++;
              }
            } else if(objChecks[0].checked) {
              if(Number(form.relObjPreRev[0].value) < Number(form.relEcaEpmAfterRev[0].value)) {
                form.relEcaEpmDocCancelRevYn[0].value = "Y";
                selCnt++;
              }
            }
          }
        } else {
          for (var i = body.rows.length; i > 2; i--) {
            curRow = i - 3;
            if (objChecks[curRow].checked) {
              if(Number(form.relObjPreRev[curRow].value) < Number (form.relEcaEpmAfterRev[curRow].value)) {
                form.relEcaEpmDocCancelRevYn[curRow].value = "Y";
                selCnt++;
              }
            }
          }
        }
      }
      else
      {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02457") %><%--저장 후 개정취소를 수행하세요--%>');
        return;
      }
    }
    if(selCnt > 0) {
      doRevise('CancelReviseEpmDoc');
    } else {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00687") %><%--개정 취소할 도면이 없습니다--%>");
    }
}

<% /* @deprecated */ %>
function cancelRevEpmDoc() {
	if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03286") %><%--개정을 취소하시겠습니까?--%>")) return;
	
	var body = document.getElementById("relEpmTable");
	if (body.rows.length < 3) return;
	var formNameStr = "document.forms[0].";
	var form = document.forms[0];
	var objChecks = eval(formNameStr + "chkSelectRelEpm");
	
	var chkComp = document.getElementsByName("chkSelectRelEpm");
	var afterRev = document.getElementsByName("relEcaEpmAfterRev");
	
	var rtn = false;
	for( var t=0; t <chkComp.length; t++)
	{
	  if(chkComp[t].checked && afterRev[t].value != '' )
	  {
	    rtn = true;
	  }
	}
	var listVal = "";
	
	var size = body.rows.length - 2;
	var selCnt = 0;
	var curRow = 0;
	if( rtn )
	{
	   if( form.isChanged.value == 'N' )
	   {
	    if(size <= 1) {
	      if (objChecks.checked || objChecks[0].checked) {
	        if(objChecks.checked) {
	          if(form.relObjPreRev.value < form.relEcaEpmAfterRev.value) {
	            form.relEcaEpmDocCancelRevYn.value = "Y";
	            selCnt++;
	          }
	        } else if(objChecks[0].checked) {
	          if(form.relObjPreRev[0].value < form.relEcaEpmAfterRev[0].value) {
	            form.relEcaEpmDocCancelRevYn[0].value = "Y";
	            selCnt++;
	          }
	        }
	      }
	    } else {
	      for (var i = body.rows.length; i > 2; i--) {
	        curRow = i - 3;
	        if (objChecks[curRow].checked) {
	          if(form.relObjPreRev[curRow].value < form.relEcaEpmAfterRev[curRow].value) {
	            form.relEcaEpmDocCancelRevYn[curRow].value = "Y";
	            selCnt++;
	          }
	        }
	      }
	    }
	  }
	  else
	  {
	    alert('<%=messageService.getString("e3ps.message.ket_message", "02457") %><%--저장 후 개정취소를 수행하세요--%>');
	    return;
	  }
	}
	if(selCnt > 0) {
	  doRevise('CancelReviseEpmDoc');
	} else {
	  alert("<%=messageService.getString("e3ps.message.ket_message", "00687") %><%--개정 취소할 도면이 없습니다--%>");
	}
}

function setCallBackEpmPopup(arr){
	
	if(typeof arr == "undefined" || arr == null) {
	      return;
	}
	if(arr == false) {
		//alert("도면변경이 실패했습니다.")
	} else {
		//alert("도면변경이 완료되었습니다.")
		if(tableRows > 3){
			form.relEcaEpmChangeYn[pos].value = "Y";
		} else {
			form.relEcaEpmChangeYn.value = "Y";
		}
		doRevise( "EpmDocChanged" );
	}
}

//도면변경 팝업
function changeEpmDoc(oid) {
	var form = document.forms[0];
	var pos = relEpmTable.clickedRowIndex - 2;
	var targetTable = document.getElementById("relEpmTable");
	var tableRows = targetTable.rows.length;
	
	var isMDrawing = '<%=isMDrawing%>';
	var url = "/plm/jsp/ecm/ModalFormScroll.jsp?url=/plm/jsp/edm/CreateEPMDocument.jsp&obj=oid^" + oid+"&obj=mDrawing^"+isMDrawing+"&obj=isECM^"+"Y";
	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=1024,height=768";
    window.open(url,'CreateEPMDocument',opts);
	
	
}

//도면 C/O Workspace
function callCOWorkspace( oid ) {
	var path = '<%=WTProperties.getServerCodebase()%>';
	//var uri = path+'servlet/WindchillAuthGW/wt.enterprise.URLProcessor/URLTemplateAction?u8&newpjl=true&ref=project&oid=OR:'+oid+'&action=WFDOWNLOAD&soln=pjl';
	var uri = path+'servlet/WindchillAuthGW/wt.enterprise.URLProcessor/URLTemplateAction?u8&Action=UwgmRequestProcessor&newpjl=true&ref=project&oid=OR:'+oid+'&action=WFDOWNLOAD&soln=pjl';
    	
	AddToWorkSpaceWin = window.open(uri,'AddToWorkSpaceWin','width=1200,height=700, status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes , left=0, top=0');
	AddToWorkSpaceWin.focus()
}

//-->
</script>
            <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td height="1">&nbsp;</td>
            </tr>
            </table -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td valign="top"><table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%></td>
                      <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
					                          
					       <td><table border="0" cellspacing="0" cellpadding="0">
					           <tr>
					             <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
					             <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave4Epm();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
					             <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
					           </tr>
					         </table></td>
					       <td width="5">&nbsp;</td>
					       <td><table border="0" cellspacing="0" cellpadding="0">
					           <tr>
					             <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
					             <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doComplete4Epm();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02438") %><%--작업완료--%></a></td>
					             <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
					           </tr>
					         </table></td>
					       
					       <%
					       if(!isTheFirst) {
					       %>
 					       <td width="5">&nbsp;</td>
					       <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelBom2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01259") %><%--도면 추가--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLineMinus2('forms[0]', 'relEpmTable', 'chkSelectRelEpm', 'chkAllRelEpm', 'deleteRelEpmList');");" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01281") %><%--도면삭제--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table></td>
                            <td width="5">&nbsp;</td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseEpmDoc2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                                </table>
                           </td>
                            <td width="5">&nbsp;</td>
                           <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:cancelRevEpmDoc2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00691") %><%--개정취소--%></a></td>
                                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                                </table>
                           </td>
                           <%
					       }
                           %>
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
                  <div id="div_scroll4" style="height:198;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relEpmTable" >
                  <col width='35'><col width='80'><col width='200'><col width='230'><col width='120'><col width='45'><col width='45'><col width='110'>
                  <tr class="headerLock4">
                      <td colspan="10">
                          <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                              <col width='35'><col width='80'><col width='200'><col width='230'><col width='120'><col width='45'><col width='45'><col width='110'>
                              <tr>
                                <td class="tdblueM" rowspan='2'><input name="chkAllRelEpm" type="checkbox" onclick="javascript:allCheck( 'chkSelectRelEpm', this.checked);"></td>
                                <td class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%></td>
                                <td class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                                <td class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                                <td class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
                                <td class="tdblueM" colspan='2'><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td class="tdblueM" rowspan='2'><%=messageService.getString("e3ps.message.ket_message", "02125") %><%--연계일정--%></td>
                              </tr>
                              <tr>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
                              </tr>
                            </table>
                          </td>
                      </tr>
                      <tr><td></td></tr>
                  <%
                  Hashtable<String, String> epmDocHash = null;

                  if( epmDocList != null && epmDocList.size() > 0 )
                  {
                      int epmDocLength = epmDocList.size();
                      String selectTxt = "";
                      for( int epmDocCnt=0; epmDocCnt < epmDocList.size(); epmDocCnt++ )
                      {
                          epmDocHash = epmDocList.get(epmDocCnt);


                         /*  KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(epmDocHash.get("eca_oid"));
                          

                          // To-Do 에서 오는 경우로 수신일을 set 한다.
                          Timestamp receiveConfirmedDate = eca.getReceiveConfirmedDate();
                          if(receiveConfirmedDate == null) {
                              eca.setReceiveConfirmedDate(DateUtil.getCurrentTimestamp());
                              
                              PersistenceHelper.manager.save(eca);
                              PersistenceHelper.manager.refresh(eca);
                          }
                              
                          
                          boolean isClosed = false;
                          WfProcess wfProcess = null;
                          QuerySpec spec = new QuerySpec(WorkItem.class);
                          SearchUtil.appendEQUAL(spec, WorkItem.class, "primaryBusinessObject.key.classname", CommonUtil.getRefOID(eca), 0);
                          QueryResult qr = PersistenceHelper.manager.find((StatementSpec) spec);
                          if (qr.hasMoreElements()) {
                              WorkItem item = (WorkItem) qr.nextElement();
                              WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
                              wfProcess = activity.getParentProcess();
                          }
                          if (wfProcess != null) {
                              String processState = wfProcess.getState().toString();    
                              // 실행중일 경우 : OPEN_RUNNING, 완료일 경우 :CLOSED_COMPLETED_EXECUTED
                              isClosed = (processState.equalsIgnoreCase("CLOSED_COMPLETED_EXECUTED")) ? true : false;
                          } */
                          
                          
                          //selectTxt = epmDocHash.get("activity_type") + "|"+epmDocHash.get("eca_obj_link_oid");
                  %>
                  <tr height="29" onMouseOver='relEpmTable.clickedRowIndex=this.rowIndex'>
                  <td class='tdwhiteM'>
                    <input type='hidden' name='relEcaActivityType' value='<%=epmDocHash.get("activity_type")%>'>
                    <input type='hidden' name='relEcaLinkOid' value='<%=epmDocHash.get("eca_obj_link_oid")%>'>
                    <input type='hidden' name='relObjOid' value='<%=epmDocHash.get("eca_obj_oid")%>'>
                    <input type='hidden' name='relEcaEpmNo' value='<%=epmDocHash.get("eca_obj_no")%>'>
                    <input type='hidden' name='relObjPreRev' value='<%=epmDocHash.get("eca_obj_pre_rev")%>'>
                    <input type='hidden' name='relEcaEpmAfterRev' value='<%=epmDocHash.get("eca_obj_after_rev")%>'>
                    <input type='hidden' name='prodEcaOid' value='<%=epmDocHash.get("eca_oid")%>'>
                    <input type='hidden' name='relEcaWorkerOid' value='<%=epmDocHash.get("worker_id")%>'>
                    <input type='hidden' name='relEcaPlanDate' value='<%=epmDocHash.get("eca_obj_plan_date")%>'>
                    <input type='hidden' name='relEcaEpmChangeYn' value='<%=epmDocHash.get("change_yn")%>'>
                    <input type='hidden' name='relEcaEpmDocReviseYn' value='N'>
                    <input type="hidden" name='relEcaEpmDocCancelRevYn' value='N'>
                    <input type='hidden' name='relEcaEpmDocType' value='<%=epmDocHash.get("obj_type")%>'>
                    <input type='hidden' name='relEcaActivityComment' value='<%=epmDocHash.get("eca_obj_act_comment")%>'>
                              
                    <input type='checkbox' name='chkSelectRelEpm' value='<%=epmDocHash.get("eca_oid") %>'>
                    <input type='hidden' name="ecaUniqueKey" value='<%=epmDocHash.get("eca_oid") %>'>    
                  </td>
                  <td class="tdwhiteM" title='<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(epmDocHash.get("CADCategory")) %>'><%=epmDocHash.get("CADCategory")%></td>
                  <td class='tdwhiteM' title='<%=epmDocHash.get("eca_obj_no")%>'><%=epmDocHash.get("eca_obj_no")%></td>
                  <td class='tdwhiteL' title="<%=epmDocHash.get("eca_obj_name") %>"><div class="ellipsis" style="width:215px;"><nobr><%=epmDocHash.get("eca_obj_name")%></nobr></div></td>
                  <%
                  if("2D".equals(epmDocHash.get("obj_type")) && StringUtil.parseInt(epmDocHash.get("eca_obj_after_rev"),0) > StringUtil.parseInt(epmDocHash.get("eca_obj_pre_rev"),0)) {
                  %>
                  <td class='tdwhiteM'>
                  
                    <%
                    if(!isTheFirst) {
                    %>
                    <table border='0' cellspacing='0' cellpadding='0'>
                      <tr>
                        <td><table border='0' cellspacing='0' cellpadding='0'>
                            <tr>
                              <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>
                              <%
                              if( epmDocHash.get("change_yn").equals("") || epmDocHash.get("change_yn").equals("N") )
                              {
                              %>
                              <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:changeEpmDoc("<%=EcmUtil.getEpmDocAfterOid(epmDocHash.get("eca_obj_oid"), epmDocHash.get("eca_obj_after_rev"))%>");' class='btn_blue' onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01280") %><%--도면변경--%></a></td>
                              <%
                              }else{%>
                              <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:changeEpmDoc("<%=EcmUtil.getEpmDocAfterOid(epmDocHash.get("eca_obj_oid"), epmDocHash.get("eca_obj_after_rev"))%>");' class='btn_blue' onfocus='this.blur();'><font color="#0000FF"><%=messageService.getString("e3ps.message.ket_message", "01516") %><%--변경완료--%></font></a></td>
                              <%} %>
                              <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>
                            </tr>
                          </table></td>
                      </tr>
                    </table>
                    <%
                    }
                    %>
                  
                  </td>
                  <%
                  } else if( "3D".equals(epmDocHash.get("obj_type")) && StringUtil.parseInt(epmDocHash.get("eca_obj_after_rev"),0) > StringUtil.parseInt(epmDocHash.get("eca_obj_pre_rev"),0) ) {
                  %>
                  <td class='tdwhiteM'>
                  
                    <%
                    if(!isTheFirst) {
                    %>
                    <table border='0' cellspacing='0' cellpadding='0'>
                      <tr>
                        <td><table border='0' cellspacing='0' cellpadding='0'>
                            <tr>
                              <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>
                              <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:callCOWorkspace("<%=EcmUtil.getEpmDocAfterOid(epmDocHash.get("eca_obj_oid"), epmDocHash.get("eca_obj_after_rev"))%>");' class='btn_blue' onfocus='this.blur();'>C/O Workspace</a></td>
                              <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>
                            </tr>
                          </table></td>
                      </tr>
                   </table>
                   <%
                   }
                   %>
                     
                 </td>
                  <%
                  } else {
                  %>
                  <td class='tdwhiteM'>&nbsp;</td>
                  <%
                  }
                  %>
                  <td class='tdwhiteM'><a href="javascript:viewEpmDocPopup('<%=EcmUtil.getEPMDocumentOid( epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_pre_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode2(epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_pre_rev")) %></a></td>
                  <td class='tdwhiteM'><a href="javascript:viewEpmDocPopup('<%=EcmUtil.getEPMDocumentOid( epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_after_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode2(epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_after_rev")) %></a></td>
                  <%
                  if(true) { //if( isExistMChange ){ 
                  %>
                  <td class='tdwhiteM'
                  ><input type='hidden' name='dieNo' value='<%=epmDocHash.get("die_no")%>'
                  ><input type='hidden' name='oldMoldChangePlanOid' value='<%=epmDocHash.get("plan_oid")%>'
                  ><input type='hidden' name='newMoldChangePlanOid' value='<%=epmDocHash.get("plan_oid")%>'
                  ><input type='text' name='scheduleId' class='txt_fieldRO' style='width:80px' readonly value='<%=epmDocHash.get("schedule_id")%>'
                  >&nbsp;<a href="javascript:popupRelPlan('relEpmTable');"><img src='/plm/portal/images/icon_5.png' border='0'
                  ></a>&nbsp;<a href="javascript:clearRelPlan('relEpmTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
                  </td>
                  <%
                  }
                  else
                  { 
                  %>
                  <td class='tdwhiteM'
                  ><input type='hidden' name='dieNo' value='<%=epmDocHash.get("die_no")%>'
                  ><input type='hidden' name='scheduleId' value='<%=epmDocHash.get("schedule_id")%>'
                  ><input type='hidden' name='oldMoldChangePlanOid' value='<%=epmDocHash.get("plan_oid")%>'
                  ><input type='hidden' name='newMoldChangePlanOid' value='<%=epmDocHash.get("plan_oid")%>'
                  >&nbsp;
                  </td>
                  <%} %>
                </tr>
                <%
                    }
                }
                %>
                  </table>
                  </div>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space15"></td>
                </tr>
            </table>
            <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                  <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "04700") %><%--부품--%></td>
                  <td align="right">&nbsp;</td>
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
<!-- div id="div_scroll5" style="height:162;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border"  -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr class="">
    <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="bomList">
        <tr>
          <!-- td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td -->
          <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01377") %><%--모부품번호--%></td>
          <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04021") %><%--모 부품명--%></td>          
          <td width="40"  rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02388") %><%--자 부품번호--%></td>
          <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04022") %><%--자 부품명--%></td>          
          <td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01236") %><%--대체부품--%></td>
          <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td width="100" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <!--<td width="120" rowspan="2" class="tdblueM0">변경내용</td>-->
        </tr>
        <tr>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
        </tr>
      <!-- /table>
    </td>
  </tr>
  <tr>
      <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="bomList" style=table-layout:fixed  -->
        <%
        Hashtable<String, String> bomHash = null;
        int totalBomCnt = 1;
        int totalBom = 0;
        if( bomList != null && bomList.size() >0 )
        {
            int bomLength = bomList.size();
            totalBom = bomLength;
            for( int bomCnt=0; bomCnt < bomList.size(); bomCnt++ )
            {
                bomHash = bomList.get(bomCnt);
        %>
        <tr>
          <!-- td width="40" class="tdwhiteM"><%=totalBomCnt++%>&nbsp;</td -->
          <td class="tdwhiteM"><%=bomHash.get("bom_type") %></td>
          <td class="tdwhiteM"><%=bomHash.get("parent_item_code") %>&nbsp; </td>
          <input type="hidden" name="bomNo" value="<%=bomHash.get("parent_item_code") %>" />
          <td class="tdwhiteM" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("parent_item_name")) %>"><div class="ellipsis" style="width:150px;"><nobr><%=bomHash.get("parent_item_name") %></nobr></div></td>
          <td class="tdwhiteM"><a href="javascript:viewPart('<%=EcmUtil.getPartOid(bomHash.get("parent_item_code"),bomHash.get("eca_obj_after_rev") )%>');"><%=bomHash.get("eca_obj_after_rev") %>&nbsp;</a></td>
          <td class="tdwhiteM"><%=bomHash.get("child_item_code") %>&nbsp; </td>
          <td class="tdwhiteM" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("child_item_name")) %>"><div class="ellipsis" style="width:150px;"><nobr><%=bomHash.get("child_item_name") %></nobr></div></td>
          <td class="tdwhiteM"><%=bomHash.get("before_qty") %>&nbsp; </td>
          <td class="tdwhiteM"><%=bomHash.get("atfer_qty") %>&nbsp; </td>
          <td class="tdwhiteM" title="<%=bomHash.get("before_substitute") %>"><div class="ellipsis" style="width:200;"><nobr><%=bomHash.get("before_substitute") %>&nbsp;</nobr></div></td>
          <td class="tdwhiteM" title="<%=bomHash.get("after_substitute")%>"><div class="ellipsis" style="width:200;"><nobr><%=bomHash.get("after_substitute") %>&nbsp;</nobr></div> </td>
          <td class="tdwhiteM"><%=bomHash.get("worker_name") %>&nbsp;</td>
          <%
          if( StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() > 0 )
          {
          %>
          <td class="tdwhiteM"><%=StringUtil.checkNull( bomHash.get("eca_complete_date") )%>&nbsp;</td>
          <%
          } else {
          %>
          <td class="tdwhiteM"><font color="#A9A9A9"><%=StringUtil.checkNull(bomHash.get("plan_date")) %>&nbsp;</font></td>
          <%
          }
          %>
          <!-- <td width="120" class="tdwhiteL0" title="<%=bomHash.get("eca_obj_act_comment")%>"><div class="ellipsis" style="width:120;"><nobr><%=bomHash.get("eca_obj_act_comment") %>&nbsp;</nobr></div></td>-->
        </tr>
        <%
              }
          }
          if( parentItemList != null && parentItemList.size() > 0 )
          {
              int parentItemLength = parentItemList.size();
              totalBom +=parentItemLength;
              for( int itemCnt=0; itemCnt < parentItemList.size(); itemCnt++ )
              {
                  bomHash = parentItemList.get(itemCnt);
        %>
        <tr>
          <!-- td class="tdwhiteM"><%=totalBomCnt++%>&nbsp; </td -->
          <td class="tdwhiteM">BOM</td>
          <%if( StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() == 0 && StringUtil.checkNull( bomHash.get("masschange_yn") ).equals("Y") ){ %>
          <td class="tdwhiteM"><%=bomHash.get("eca_parent_item_no") %>&nbsp;</td>
          <input type="hidden" name="bomNo" value="<%=bomHash.get("eca_parent_item_no") %>"/>
          <td class="tdwhiteM" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("eca_parent_item_name")) %>"><div class="ellipsis" style="width:150px;"><nobr><%=bomHash.get("eca_parent_item_name") %></nobr></div></td>
          <%}else{ %>
          <td class="tdwhiteM"><%=bomHash.get("eca_obj_no") %>&nbsp; </td>
              <input type="hidden" name="bomNo" value="<%=bomHash.get("eca_obj_no") %>"/>
          <td class="tdwhiteM" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("eca_obj_name")) %>"><div class="ellipsis" style="width:150px;"><nobr><%=bomHash.get("eca_obj_name") %></nobr></div></td>
          <%} %>
          <%
          /* 
          if( StringUtil.checkNull( bomHash.get("eca_obj_after_rev") ).length() > 0 &&  StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() > 0 ) {
          */
          if( StringUtil.checkNull( bomHash.get("eca_obj_after_rev") ).length() > 0 ) {
          %>
          <td class="tdwhiteM"><a href="javascript:viewPart('<%=EcmUtil.getPartOid(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_after_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_after_rev")) %>&nbsp;</a></td>
          <%}else{%>
           <td class="tdwhiteM"><a href="javascript:viewPart('<%=EcmUtil.getPartOid(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_pre_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_pre_rev")) %>&nbsp;</a></td>
          <%} %>
          <%if( StringUtil.checkNull( bomHash.get("masschange_yn") ).equals("N") ){ %>
          <td class="tdwhiteM">&nbsp; </td>
          <td class="tdwhiteM">&nbsp; </td>
          <%}else{ %>
          <td class="tdwhiteM"><%=bomHash.get("eca_obj_no") %>&nbsp;</td>
          <td class="tdwhiteM" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(bomHash.get("eca_obj_name")) %>"><div class="ellipsis" style="width:150px;"><nobr><%=bomHash.get("eca_obj_name") %></nobr></div></td>
          <%} %>
          <td class="tdwhiteM">&nbsp; </td>
          <td class="tdwhiteM">&nbsp; </td>
          <td class="tdwhiteM">&nbsp; </td>
          <td class="tdwhiteM">&nbsp; </td>
          <td class="tdwhiteM"><%=bomHash.get("worker_name") %></td>
           <%
           if( StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() > 0 )
           {
           %>
          <td class="tdwhiteM0"><%=StringUtil.checkNull( bomHash.get("eca_complete_date") )%>&nbsp;</td>
          <%
           }else{
          %>
          <td class="tdwhiteM0"><font color="#A9A9A9"><%=StringUtil.checkNull(bomHash.get("plan_date")) %>&nbsp;</font></td>
          <%} %>
          <!-- <td width="120" class="tdwhiteL0" title="<%=bomHash.get("eca_obj_act_comment")%>"><div class="ellipsis" style="width:120;"><nobr><%=bomHash.get("eca_obj_act_comment") %>&nbsp;</nobr></div></td>-->
        </tr>
        <%
                 }
          }
          if(totalBom==0)
          {
        %>
           <tr>
               <td colspan=11 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "01494") %><%--변경 BOM이 없습니다--%>.</td>
           </tr>
        <%
        }
        %>
      </table>
    </td>
  </tr>
</table>
<!-- /div -->
