<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="wt.part.WTPart"%>

<%@page import="e3ps.common.util.*"%>
<%@page import="e3ps.ecm.entity.*"%>
<%@page import="ext.ket.part.util.*"%>
<%@page import="ext.ket.part.sap.ErpPartHandler"%>


<%
ErpPartHandler erpPartHandler = new ErpPartHandler();
ext.ket.bom.query.KETBOMQueryBean ketBOMQueryBean = new ext.ket.bom.query.KETBOMQueryBean();
%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
.headerLock5 {
  position: relative;
  top:expression(document.getElementById("div_scroll5").scrollTop);
  z-index:99;
 }
</style>
<script type="text/javascript">
<!--
<% /* 저장 */ %>
function doSave4Bom()
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
function doComplete4Bom()
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
        doComplete4Bom();
    }
}
function lfn_feedback_After_Complete() {
	//alert('lfn_feedback_After_Complete');
	try { opener.location.reload(); } catch(e) {}
	window.close();
}

//BOM 검색 팝업 호출
var newwin = null;
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
    $("#relBomTable").find("[name=addedPartNumberAtEca]").each(function(i) {
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
    
    newwin = openWindow(url,"popupRelEpm2","1000","600","status=1,scrollbars=no,resizable=no");
    //attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=725px; dialogHeight:570px; center:yes");
    
}

<% /* deprecated */ %>
function popupRelBom() {
  var form = document.forms[0];

  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchActivityPopupForm.jsp";
  url += "&obj=prodMoldCls^1&obj=mode^multi&obj=ecoEcaCls^eca&obj=epmBomCls^2&obj=partType^P";
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=900px; dialogHeight:580px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  insertRelBomLine(attache);
}

//BOM 라인추가
function insertRelEpmLine(objArr) {
	insertRelBomLine(objArr);
}
function insertRelBomLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relBomTable");

  var trArr;
  var str = "";
  var isAdded = false;
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
 // 도면일 경우
    if(trArr[0] == '1') {
    if( !isDuplicateLine2(trArr[0], trArr[2])) 
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      newTr.height="29";
      newTr.onmouseover=function(){relBomTable.clickedRowIndex=this.rowIndex};
      var currRow = targetTable.rows.length - 3;

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      
      var uniqueTime = lfn_getUniqueTime();
      str = "";
      str += "<input type='hidden' name='relEcaActivityType' value='" + trArr[0] + "'>";
      str += "<input type='hidden' name='relEcaBomHeaderOid' value=''>";
      str += "<input type='hidden' name='relEcaBomChildPartNo' value=''>";
      str += "<input type='hidden' name='relEcaLinkOid' value=''>";
      str += "<input type='hidden' name='relObjOid' value='" + trArr[1] + "'>";
      str += "<input type='hidden' name='relEcaBomNo' value='" + trArr[2] + "'>";
      str += "<input type='hidden' name='relEcaBomName' value='" + trArr[3] + "'>";
      str += "<input type='hidden' name='relObjPreRev' value='" + trArr[4] + "'>";
      str += "<input type='hidden' name='relEcaBomAfterRev' value=''>";
      str += "<input type='hidden' name='prodEcaOid' value=''>";
      str += "<input type='hidden' name='beforePartOid' value='"+trArr[1]+"'>";
      str += "<input type='hidden' name='relEcaWorkerOid' value='" + trArr[5] + "'>";
      str += "<input type='hidden' name='relEcaBomChangeYn' value='N'>";
      str += "<input type='hidden' name='relEcaPlanDate' value=''>";
      str += "<input type='hidden' name='relEcaBomChangeYn' value='N'>";
      str += "<input type='hidden' name='relEcaBomReviseYn' value='N'>";
      str += "<input type='hidden' name='relEcaActivityComment'>";
      
      str += "<input type='hidden' name='afterWTPartOid' value=''>";

      <!-- 활동추가를 위한 정보 -->
      str += "<input type='hidden' name='addedPartNumberAtEca' value='"+ trArr[2] +"'>";

      str += "<input type='checkbox' name='chkSelectRelBom' value='"+ uniqueTime +"'>";
      str += "<input type='hidden' name='ecaUniqueKey' value='"+ uniqueTime +"'>";
      str += "<input type='hidden' name='masschange_yn' value='N'>";
      newTd.innerHTML = str;

      //부품번호
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<font title='"+trArr[2]+"'><div class='ellipsis' style='width:80;'><nobr>"
                      + trArr[2] +"</nobr></div></font>";

      //부품명
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerHTML = "<font title='"+trArr[3]+"'><div class='ellipsis' style='width:100;'><nobr>"
                      + trArr[3] +"</nobr></div></font>";

      //PrevRev
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      //newTd.innerHTML = trArr[4];
      newTd.innerHTML = trArr[11];

      //NewRev
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;";

      /* 
      //일괄변경 여부
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      str += "<select name='masschange_yn'>";
      str += "<option value='Y'>Y&nbsp;</option>";
      str += "<option value='N' selected>N&nbsp;</option>";
      str += "</select>";
      newTd.innerHTML = str;

      //관련 모부품 정보
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='text' name='parentPart' class='txt_field'  style='width:70'>";
      str += "&nbsp;<a href=\"javascript:popupPart('setParentPart');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
      str += "&nbsp;<a href=\"javascript:clearEpmCal('parentPart', 'relBomTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
      newTd.innerHTML = str;
      
      //변경내용
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='text' name='relEcaActivityComment' class='txt_field'  style='width:290px' maxlength='100'>";
      newTd.innerHTML = str;
      */
      
      //작업
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "&nbsp;";
      str = "";
      str += "<table border='0' cellspacing='0' cellpadding='0'>";
      str += "<tr>";
      str += "  <td width='2'>&nbsp;</td>";
      str += "  <td>";
      str += "    <table border='0' cellspacing='0' cellpadding='0'>";
      str += "      <tr>";
      str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
      str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:doSave4Bom();' class='btn_blue'><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>";
      str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
      str += "      </tr>";
      str += "    </table>";
      str += "  </td>";
      str += "</tr>";
      str += "</table>";
      
      
      newTd.innerHTML = str;
      
      isAdded = true;
    }else{
    	isAdded = false;
    }

  }else{
	  if(trArr[0] == '2') {
		    if( !isDuplicateLine2(trArr[0], trArr[2])) 
		    {
				   var tableRows = targetTable.rows.length;
				      var newTr = targetTable.insertRow(tableRows);
				      newTr.height="29";
				      newTr.onmouseover=function(){relBomTable.clickedRowIndex=this.rowIndex};
				      var currRow = targetTable.rows.length - 3;
			
				      //전체선택 checkbox
				      newTd = newTr.insertCell(newTr.cells.length);
				      newTd.className = "tdwhiteM";
				      
				      var uniqueTime = lfn_getUniqueTime();
				      str = "";
				      str += "<input type='hidden' name='relEcaActivityType' value='" + trArr[0] + "'>";
				      str += "<input type='hidden' name='relEcaBomHeaderOid' value=''>";
				      str += "<input type='hidden' name='relEcaBomChildPartNo' value=''>";
				      str += "<input type='hidden' name='relEcaLinkOid' value=''>";
				      str += "<input type='hidden' name='relObjOid' value='" + trArr[1] + "'>";
				      str += "<input type='hidden' name='relEcaBomNo' value='" + trArr[2] + "'>";
				      str += "<input type='hidden' name='relEcaBomName' value='" + trArr[3] + "'>";
				      str += "<input type='hidden' name='relObjPreRev' value='" + trArr[4] + "'>";
				      str += "<input type='hidden' name='relEcaBomAfterRev' value=''>";
				      str += "<input type='hidden' name='prodEcaOid' value=''>";
				      str += "<input type='hidden' name='beforePartOid' value='"+trArr[1]+"'>";
				      str += "<input type='hidden' name='relEcaWorkerOid' value='" + trArr[5] + "'>";
				      str += "<input type='hidden' name='relEcaBomChangeYn' value='N'>";
				      str += "<input type='hidden' name='relEcaPlanDate' value=''>";
				      str += "<input type='hidden' name='relEcaBomChangeYn' value='N'>";
				      str += "<input type='hidden' name='relEcaBomReviseYn' value='N'>";
				      str += "<input type='hidden' name='relEcaActivityComment'>";
				      
				      str += "<input type='hidden' name='afterWTPartOid' value=''>";
			
				      <!-- 활동추가를 위한 정보 -->
				      str += "<input type='hidden' name='addedPartNumberAtEca' value='"+ trArr[2] +"'>";
			
				      str += "<input type='checkbox' name='chkSelectRelBom' value='"+ uniqueTime +"'>";
				      str += "<input type='hidden' name='ecaUniqueKey' value='"+ uniqueTime +"'>";
				      str += "<input type='hidden' name='masschange_yn' value='N'>";
				      newTd.innerHTML = str;
			
				      //부품번호
				      newTd = newTr.insertCell(newTr.cells.length);
				      newTd.className = "tdwhiteM";
				      newTd.innerHTML = "<font title='"+trArr[2]+"'><div class='ellipsis' style='width:80;'><nobr>"
				                      + trArr[2] +"</nobr></div></font>";
			
				      //부품명
				      newTd = newTr.insertCell(newTr.cells.length);
				      newTd.className = "tdwhiteL";
				      newTd.innerHTML = "<font title='"+trArr[3]+"'><div class='ellipsis' style='width:100;'><nobr>"
				                      + trArr[3] +"</nobr></div></font>";
			
				      //PrevRev
				      newTd = newTr.insertCell(newTr.cells.length);
				      newTd.className = "tdwhiteM";
				      //newTd.innerHTML = trArr[4];
				      newTd.innerHTML = trArr[11];
			
				      //NewRev
				      newTd = newTr.insertCell(newTr.cells.length);
				      newTd.className = "tdwhiteM";
				      newTd.innerHTML = "&nbsp;";
			
				      /* 
				      //일괄변경 여부
				      newTd = newTr.insertCell(newTr.cells.length);
				      newTd.className = "tdwhiteM";
				      str = "";
				      str += "<select name='masschange_yn'>";
				      str += "<option value='Y'>Y&nbsp;</option>";
				      str += "<option value='N' selected>N&nbsp;</option>";
				      str += "</select>";
				      newTd.innerHTML = str;
			
				      //관련 모부품 정보
				      newTd = newTr.insertCell(newTr.cells.length);
				      newTd.className = "tdwhiteM";
				      str = "";
				      str += "<input type='text' name='parentPart' class='txt_field'  style='width:70'>";
				      str += "&nbsp;<a href=\"javascript:popupPart('setParentPart');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
				      str += "&nbsp;<a href=\"javascript:clearEpmCal('parentPart', 'relBomTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
				      newTd.innerHTML = str;
				      
				      //변경내용
				      newTd = newTr.insertCell(newTr.cells.length);
				      newTd.className = "tdwhiteM";
				      str = "";
				      str += "<input type='text' name='relEcaActivityComment' class='txt_field'  style='width:290px' maxlength='100'>";
				      newTd.innerHTML = str;
				      */
				      
				      //작업
				      newTd = newTr.insertCell(newTr.cells.length);
				      newTd.className = "tdwhiteM";
				      str = "&nbsp;";
				      str = "";
				      str += "<table border='0' cellspacing='0' cellpadding='0'>";
				      str += "<tr>";
				      str += "  <td width='2'>&nbsp;</td>";
				      str += "  <td>";
				      str += "    <table border='0' cellspacing='0' cellpadding='0'>";
				      str += "      <tr>";
				      str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
				      str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:doSave4Bom();' class='btn_blue'><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>";
				      str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
				      str += "      </tr>";
				      str += "    </table>";
				      str += "  </td>";
				      str += "</tr>";
				      str += "</table>";
				      
				      
				      newTd.innerHTML = str;
				      
				      isAdded = true;
		    }else{
		    	isAdded = false;
		    }
  }
  }
    if(isAdded) document.forms[0].isChanged.value = 'Y';
  }

 
  
  //if(newwin != null) newwin.window.close();
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

function isDuplicateBom( item_code )
{
    var isDuplicate = false;
    var bomList = document.getElementsByName("relObjOid");

    for( var inx=0; inx < bomList.length; inx++ )
    {
        if( bomList[inx].value == item_code )
        {
             isDuplicate = true;
        }
    }

    return isDuplicate;
}


function isDuplicateLine2( type, number )
    {
    var isDuplicate = false;
    
    var numberList = document.getElementsByName("addedPartNumberAtEca");
    var typeList = document.getElementsByName("relEcaActivityType");
    
    for( var i=0; i < numberList.length ; i++)
    {
      if( number == numberList[i].value && type == typeList[i].value )
      {
        isDuplicate = true;
      }
    }
    
    var epmList =document.getElementsByName("epmNo"); 
    
    
    
    for( var i=0; i < epmList.length ; i++)
    {
      if( number == epmList[i].value )
      {
        isDuplicate = true;
      }
    }
    
    return isDuplicate;
}

<% /* 개정 */ %>
function reviseBom2(pos) {
	
	if( document.forms[0].isChanged.value == 'N' ) {
	    // Do nothing..!!
	} else {
		// <entry key="04024">변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.</entry>
	    alert('<%=messageService.getString("e3ps.message.ket_message", "04024") %><%--변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.--%>');
	    return;
	}
	
	
	// 일괄개정일 경우에만
    if(pos == null || typeof pos == 'undefined') {
		// 체크가 하나라도 되어 있어야 한다.
		if($("input:checkbox[name='chkSelectRelBom']").is(":checked") == false) {
			alert('<%=messageService.getString("e3ps.message.ket_message", "04012") %><%--개정할 부품을 리스트에서 선택하여 주십시오.--%>');
			return;
		}
    }
	
	
    var form = document.forms[0];
    //var pos = relBomTable.clickedRowIndex - 2;
    
    var chkSelectRelBom = document.getElementsByName("chkSelectRelBom");
    if(chkSelectRelBom == null || typeof chkSelectRelBom == 'undefined') return;
    var chkSelectRelBomLength = chkSelectRelBom.length;
    
    
    // 일괄개정
    if(pos == null || typeof pos == 'undefined') {
        if (!confirm('<%=messageService.getString("e3ps.message.ket_message", "04340") %><%--부품을 일괄개정하시겠습니까?--%>') ) return;
        
        if(chkSelectRelBomLength == 1) {
                if(form.relObjOid.value != '') {
                    form.chkSelectRelBom.checked = true;
                } else {
                    
                    alert('<%=messageService.getString("e3ps.message.ket_message", "04300") %><%--개정할 부품이 존재하지 않습니다.--%>');
                    form.chkSelectRelBom.checked = false;
                    return;
                    
                }
            
        } else {
            for(var i=0; i < chkSelectRelBomLength; i++) {
                    if(form.relObjOid[i].value != '') {
                        form.chkSelectRelBom[i].checked = true;
                    } else {
                        
                        alert('<%=messageService.getString("e3ps.message.ket_message", "04300") %><%--개정할 부품이 존재하지 않습니다.--%>');
                        for(var i=0; i < chkSelectRelBomLength; i++) {
                            form.chkSelectRelBom[i].checked = false;
                        }
                        return;
                        
                    }
                    
            }
            
        }
        
    } else {    
        if (!confirm('<%=messageService.getString("e3ps.message.ket_message", "04320") %><%--부품을 개정하시겠습니까?--%>') ) return;
                
        if(chkSelectRelBomLength == 1) {
                if(form.relObjOid.value != '') {
                    form.chkSelectRelBom.checked = true;
                } else {
                    
                    alert('<%=messageService.getString("e3ps.message.ket_message", "04300") %><%--개정할 부품이 존재하지 않습니다.--%>');
                    form.chkSelectRelBom.checked = false;
                    return;
                    
                }
            
        } else {
            for(var i=0; i < chkSelectRelBomLength; i++) {
                if(pos == i) {
                        if(form.relObjOid[i].value != '') {
                            form.chkSelectRelBom[i].checked = true;
                        } else {
                            
                            alert('<%=messageService.getString("e3ps.message.ket_message", "04210") %><%--개정할 문서가 존재하지 않습니다.--%>');
                            for(var i=0; i < chkSelectRelBomLength; i++) {
                                form.chkSelectRelBom[i].checked = false;
                            }
                            return;
                            
                        }
                            
                } else {
                    form.chkSelectRelBom[i].checked = false;
                    
                }
            }
            
        }
      
    }
    
    doRevise("ReviseBom");
    
    // 에러시 Refresh를 하지 않을 경우를 대비해 초기화하여야 한다.
    if(chkSelectRelBomLength == 1) {
        form.chkSelectRelBom.checked = false;
    } else {
        for(var i=0; i < chkSelectRelBomLength; i++) {
            form.chkSelectRelBom[i].checked = false;
        }
    }
    
}

<% /* @deprecated */ %>
function reviseBom()
{
  var form = document.forms[0];
  var pos = relBomTable.clickedRowIndex - 2;
  var targetTable = document.getElementById("relBomTable");
  var tableRows = targetTable.rows.length;

  if( tableRows > 3){
    form.relEcaBomReviseYn[pos].value = "Y";
    form.bomPartNo.value = form.relEcaBomNo[pos].value;
    form.bomLinkOid.value = form.relEcaLinkOid[pos].value;
    form.bomReviseYn.value = form.relEcaBomReviseYn[pos].value;
    form.bomMassChange.value = "N";     //form.masschange_yn[pos].value;
    //form.relatedParentPart.value = form.parentPart[pos].value;
  } else {
    form.relEcaBomReviseYn.value = "Y";
    form.bomPartNo.value = form.relEcaBomNo.value;
    form.bomLinkOid.value = form.relEcaLinkOid.value;
    form.bomReviseYn.value = form.relEcaBomReviseYn.value;
    form.bomMassChange.value = "N";     //form.masschange_yn.value;
    //form.relatedParentPart.value = form.parentPart.value;
  }

  <%-- 
  if( form.bomMassChange.value == 'Y' && form.relatedParentPart.value == '' )
  {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02344") %>일괄변경의 경우 모부품번호를 입력하세요');
    return;
  }
  else
  {
    doRevise("ReviseBom");
  }
  --%>
  
  
  doRevise("ReviseBom");
  
  
}

<% /* 개정취소 */ %>
function reviseCancelBom2(pos) {

    if( document.forms[0].isChanged.value == 'N' ) {
        // Do nothing..!!
    } else {
        // <entry key="04024">변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.</entry>
        alert('<%=messageService.getString("e3ps.message.ket_message", "04024") %><%--변경된 정보가 있습니다. 저장후 작업을 진행하시기 바랍니다.--%>');
        return;
    }
    
    
	// 일괄개정취소일 경우에만
    if(typeof pos == 'undefined') {
	    // 체크가 하나라도 되어 있어야 한다.
	    if($("input:checkbox[name='chkSelectRelBom']").is(":checked") == false) {
	        alert('<%=messageService.getString("e3ps.message.ket_message", "04013") %><%--개정취소할 부품을 리스트에서 선택하여 주십시오.--%>');
	        return;
	    }
    }
	   
    
    var form = document.forms[0];
    //var pos = relBomTable.clickedRowIndex - 2;
       
    var chkSelectRelBom = document.getElementsByName("chkSelectRelBom");
    if(chkSelectRelBom == null || typeof chkSelectRelBom == 'undefined') return;
    var chkSelectRelBomLength = chkSelectRelBom.length;

    
    // 일괄개정취소
    if(pos == null || typeof pos == 'undefined') {
        if (!confirm('<%=messageService.getString("e3ps.message.ket_message", "04350") %><%--부품을 일괄개정취소하시겠습니까?--%>') ) return;
        
        if(chkSelectRelBomLength == 1) {
                if(form.relObjOid.value != '') {
                    form.chkSelectRelBom.checked = true;
                } else {
                    
                    alert('<%=messageService.getString("e3ps.message.ket_message", "04310") %><%--개정취소할 부품이 존재하지 않습니다.--%>');
                    form.chkSelectRelBom.checked = false;
                    return;
                    
                }
            
        } else {
            for(var i=0; i < chkSelectRelBomLength; i++) {
                    if(form.relObjOid[i].value != '') {
                        form.chkSelectRelBom[i].checked = true;
                    } else {
                        
                        alert('<%=messageService.getString("e3ps.message.ket_message", "04310") %><%--개정취소할 부품이 존재하지 않습니다.--%>');
                        for(var i=0; i < chkSelectRelBomLength; i++) {
                            form.chkSelectRelBom[i].checked = false;
                        }
                        return;
                        
                    }
                
            }
            
        }
                
    } else {    
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "04330") %><%--개정을 취소하시겠습니까?--%>")) return;
        
        if(chkSelectRelBomLength == 1) {
                if(form.relEcaBomAfterRev.value != '') {
                    form.chkSelectRelBom.checked = true;
                } else {
                    
                    alert('<%=messageService.getString("e3ps.message.ket_message", "04310") %><%--개정취소할 부품이 존재하지 않습니다.--%>');
                    form.chkSelectRelBom.checked = false;
                    return;
                    
                }
               
        } else {
            for(var i=0; i < chkSelectRelBomLength; i++) {
                if(pos == i) {
                        if(form.relEcaBomAfterRev[i].value != '') {
                            form.chkSelectRelBom[i].checked = true;
                        } else {
                            
                            alert('<%=messageService.getString("e3ps.message.ket_message", "04310") %><%--개정취소할 부품이 존재하지 않습니다.--%>');
                            for(var i=0; i < chkSelectRelBomLength; i++) {
                                form.chkSelectRelBom[i].checked = false;
                            }
                            return;
                            
                        }
                           
                } else {
                    form.chkSelectRelBom[i].checked = false;
                    
                }
            }
            
        }
      
    }
        
    doRevise('CancelReviseBom');
    
    // 에러시 Refresh를 하지 않을 경우를 대비해 초기화하여야 한다.
    if(chkSelectRelBomLength == 1) {
        form.chkSelectRelBom.checked = false;
    } else {
        for(var i=0; i < chkSelectRelBomLength; i++) {
            form.chkSelectRelBom[i].checked = false;
        }
    }

}

<% /* @deprecated */ %>
function reviseCancelBom()
{
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03286") %><%--개정을 취소하시겠습니까?--%>")) return;

  var form = document.forms[0];
  var pos = relBomTable.clickedRowIndex - 2;
  var targetTable = document.getElementById("relBomTable");
  var tableRows = targetTable.rows.length;

  if( tableRows > 3){
    form.relEcaBomReviseYn[pos].value = "N";
    form.bomPartNo.value = form.relEcaBomNo[pos].value;
    form.bomLinkOid.value = form.relEcaLinkOid[pos].value;
    form.bomReviseYn.value = form.relEcaBomReviseYn[pos].value;
    form.bomMassChange.value = "N";     //form.masschange_yn[pos].value;
    //form.relatedParentPart.value = form.parentPart[pos].value;
  } else {
    form.relEcaBomReviseYn.value = "N";
    form.bomPartNo.value = form.relEcaBomNo.value;
    form.bomLinkOid.value = form.relEcaLinkOid.value;
    form.bomReviseYn.value = form.relEcaBomReviseYn.value;
    form.bomMassChange.value = "N";     //form.masschange_yn.value;
    //form.relatedParentPart.value = form.parentPart.value;
  }

  <%-- 
  if( form.bomMassChange.value == 'Y' && form.relatedParentPart.value == '' )
  {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02344") %>일괄변경의 경우 모부품번호를 입력하세요');
    return;
  }
  else
  {
    doRevise("CancelReviseBom");
  }
  --%>
  
  
  doRevise("CancelReviseBom");
  
  
}

<% /* 변경 */ %>
function changeBom2(pos)
{
	var form = document.forms[0];
	//var pos = relBomTable.clickedRowIndex - 2;
       
    var chkSelectRelBom = document.getElementsByName("chkSelectRelBom");
    if(chkSelectRelBom == null || typeof chkSelectRelBom == 'undefined') return;
    var chkSelectRelBomLength = chkSelectRelBom.length;

	  
	// 일괄변경
    if(pos == null || typeof pos == 'undefined') {

    } else {
    	var afterWTPartOid = "";
    	var relObjOid = "";
    	if(chkSelectRelBomLength == 1) {
	    	afterWTPartOid = form.afterWTPartOid.value;
    		relObjOid = form.relObjOid.value;
    				        
    	} else {
    		afterWTPartOid = form.afterWTPartOid[pos].value;
            relObjOid = form.relObjOid[pos].value;
    	}
    	
    	if(afterWTPartOid != null && afterWTPartOid != '') {
            relObjOid = afterWTPartOid;
            
        }
    	
    	form.isChanged.value = 'Y';
    	
/*     	var url = "/plm/extcore/jsp/bom/KETBomEditorMain.jsp?oid="+ relObjOid;
	    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
	    
	    leftpos = (screen.availWidth - screen.availWidth) / 2;
        toppos = (screen.availHeight - screen.availHeight) / 2;

        rest = "width=" + (screen.availWidth) + ",height=" + (screen.availHeight) + ',left=' + leftpos + ',top=' + toppos;
        
        open(url, "BOMEditor", (rest + opts)); */
        var url = "/plm/extcore/jsp/bom/KETBomEditorMain.jsp?oid="+ relObjOid;
        openFullWindow(url,relObjOid);
        
	    //openWindow(url,"BOMEditor","1024","700","menubar=no,toolbar=no,location=no,scrollbars=no,status=no");    	
	    	
    }	
    
}

<% /* @deprecated */ %>
function changeBom()
{
  var oid;
  var form = document.forms[0];
  var pos = relBomTable.clickedRowIndex - 2;
  var targetTable = document.getElementById("relBomTable");
  var tableRows = targetTable.rows.length;

  var headerOid = "";
  var childPart = "";
  var mass_yn = "";
  var parentPart = "";

  if(tableRows > 3)
  {
    headerOid = form.relEcaBomHeaderOid[pos].value;
    mass_yn = "N";  //form.masschange_yn[pos].value;
    childPart = form.relEcaBomChildPartNo[pos].value;
    //parentPart = form.parentPart[pos].value;
  }
  else
  {
    headerOid = form.relEcaBomHeaderOid.value;
    mass_yn = "N";  //form.masschange_yn.value;
    childPart = form.relEcaBomChildPartNo.value;
    //parentPart = form.parentPart.value;
  }

  if( mass_yn == 'Y' )
  {
      form.isChanged.value = 'Y';
    var url = "/plm/jsp/bom/BOMEditorLoading.jsp?oid=multiple&ecoType=multiple&child=" + childPart + "&ecoNo=" +'<%=prodEco.getEcoId()%>' + "&parent=" + parentPart;
    openWindow(url,"BOMEditor","1024","768","menubar=no,toolbar=no,location=no,scrollbars=no,status=no");
  }
  else
  {
    form.isChanged.value = 'Y';
    var url = "/plm/jsp/bom/BOMEditorLoading.jsp?oid=VR:" + headerOid + "&ecoType=standard";
    openWindow(url,"BOMEditor","1024","768","menubar=no,toolbar=no,location=no,scrollbars=no,status=no");
  }
}

//-->
</script>
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "04700") %><%--부품--%></td>
    <td align="right">
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
  
          <td><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave4Bom();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table></td>
          <td width="5">&nbsp;</td>
          <td><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doComplete4Bom();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02438") %><%--작업완료--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table></td>
          
          <%
          if(!isTheFirst) {
          %>  
          <td width="5">&nbsp;</td>
          <td>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelBom2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01573") %><%--부품 추가--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table>
          </td>
          <td width="5">&nbsp;</td>
          <td>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLineMinus2('forms[0]', 'relBomTable', 'chkSelectRelBom', 'chkAllRelBom', 'deleteRelBomList');");" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01591") %><%--부품삭제--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table>
          </td>
          <%
          }
          %>
          
          <%
          if( !isTheFirst && parentItemList != null && parentItemList.size() > 0 ) {
          %>
            <td width="5">&nbsp;</td>
            <td>
                <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseBom2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "04220") %><%--일괄개정--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
                </table>
            </td>
            <td width="5">&nbsp;</td>
            <td>
                <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseCancelBom2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "04230") %><%--일괄개정취소--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
                </table>
            </td>
         <%
         }
         %>  

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
<!-- div id="div_scroll5" style="height:198;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border" -->
<table border="0" cellspacing="0" cellpadding="0" width="100%" id="relBomTable">
  <tr class="">
     <td width="30" rowspan="2" class="tdblueM"><input name="chkAllRelBom" type="checkbox" onclick="javascript:allCheck( 'chkSelectRelBom', this.checked);"></td>
     <td width="200" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
     <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
     <td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
     <%-- 
     <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02332") %>일괄</td>
     <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00928") %>관련모부품번호</td>
     --%>
     <td width="170" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
   </tr>
   <tr>
     <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
     <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
  </tr>
  <%
  int rowIndex = 0;
  
  Hashtable<String, String> bomHash = null;

  if( parentItemList != null && parentItemList.size() > 0 )
  {
      int bomLength = parentItemList.size();

      for( int itemCnt=0; itemCnt < parentItemList.size(); itemCnt++ )
      {
          bomHash = parentItemList.get(itemCnt);
          
          
/*           KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(bomHash.get("eca_oid"));
          

          // To-Do 에서 오는 경우로 수신일을 set 한다.
          Timestamp receiveConfirmedDate = eca.getReceiveConfirmedDate();
          if(receiveConfirmedDate == null) {
              eca.setReceiveConfirmedDate(DateUtil.getCurrentTimestamp());
              
              PersistenceHelper.manager.save(eca);
              PersistenceHelper.manager.refresh(eca);
          }
               */
          
          boolean isClosed = false;
          /* WfProcess wfProcess = null;
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
          }

          // 부품이 초도일 경우 KETBomHeader, 아닐 경우 KETEcoHeader에 set 한다.
          //boolean existErp = erpPartHandler.existErpPart(bomHash.get("eca_obj_no"));
          */
          String before_part_oid = (String) bomHash.get("before_part_oid");
          WTPart part = (WTPart) CommonUtil.getObject(before_part_oid);
          String partState = part.getState().toString(); 
                    
          /* 
          String bomflag = PartSpecGetter.getPartSpec((WTPart) part, PartSpecEnum.SpBOMFlag);
          if (bomflag != null && bomflag.equalsIgnoreCase("OLD")) {
              existErp = false;
          }
          else {
              existErp = true;
          }
          */
  %>
          <tr height="29" onMouseOver='relBomTable.clickedRowIndex=this.rowIndex'>
            <td class='tdwhiteM'>
              <input type='hidden' name='relEcaActivityType' value='<%=bomHash.get("activity_type")%>'>
              <input type='hidden' name='relEcaBomHeaderOid' value='<%=bomHash.get("eca_obj_oid")%>'>
              <input type='hidden' name='relEcaBomChildPartNo' value='<%=bomHash.get("eca_obj_no")%>'>
              <input type='hidden' name='relEcaLinkOid' value='<%=bomHash.get("eca_obj_link_oid")%>'>
              <input type='hidden' name='relObjOid' value='<%=bomHash.get("before_part_oid")%>'>
              <input type='hidden' name='relEcaBomNo' value='<%=bomHash.get("eca_obj_no")%>'>
              <input type='hidden' name='relEcaBomName' value='<%=bomHash.get("eca_obj_name")%>'>
              <input type='hidden' name='relObjPreRev' value='<%=bomHash.get("eca_obj_pre_rev")%>'>
              <input type='hidden' name='relEcaBomAfterRev' value='<%=bomHash.get("eca_obj_after_rev")%>'>
              <input type='hidden' name='prodEcaOid' value='<%=bomHash.get("eca_oid")%>'>
              <input type='hidden' name='beforePartOid' value='<%=bomHash.get("before_part_oid")%>'>
              <input type='hidden' name='relEcaWorkerOid' value='<%=bomHash.get("worker_id")%>'>
              <input type='hidden' name='relEcaBomChangeYn' value='<%=bomHash.get("change_yn")%>'>
              <input type='hidden' name='relEcaPlanDate' value='<%=bomHash.get("eca_obj_plan_date")%>'>
              <input type='hidden' name='relEcaBomReviseYn' value='N'>
              <input type="hidden" name='relEcaActivityComment'" value='<%=bomHash.get("eca_obj_act_comment") %>'>
            
              <%
              String afterWTPartOid = ketBOMQueryBean.getPartOid(bomHash.get("eca_obj_no"), bomHash.get("eca_obj_after_rev"));
              %>
              <input type='hidden' name='afterWTPartOid' value='<%=afterWTPartOid %>'>
              
              <!-- 활동추가를 위한 정보 -->
              <input type='hidden' name='addedPartNumberAtEca' value='<%=bomHash.get("eca_obj_no")%>'>
              
              <%
              String disabled = (isClosed) ? "disabled='disabled'" : "";
              %>
              <input type='checkbox' name='chkSelectRelBom' value='<%=bomHash.get("eca_oid") %>' <%=disabled %>>
              <input type='hidden' name="ecaUniqueKey" value='<%=bomHash.get("eca_oid") %>'>
              <input type='hidden' name="masschange_yn" value='N'>
                 
            </td>
            <td class="tdwhiteM"><%=bomHash.get("eca_obj_no")%></td>
            <td class="tdwhiteL" title='<%=StringEscapeUtils.escapeHtml(bomHash.get("eca_obj_name")) %>'><div class="ellipsis" style="width:450px;"><nobr><%=bomHash.get("eca_obj_name")%></nobr></div></td>
            <!-- td class="tdwhiteM"><%=bomHash.get("eca_obj_pre_rev")%>&nbsp; </td>
            <td class="tdwhiteM"><%=bomHash.get("eca_obj_after_rev") %>&nbsp; </td -->
            <td class="tdwhiteM"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("eca_obj_no"), bomHash.get("eca_obj_pre_rev")) %>&nbsp; </td>
            <td class="tdwhiteM"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("eca_obj_no"), bomHash.get("eca_obj_after_rev")) %>&nbsp; </td>
            <%-- 
            <td class="tdwhiteM">
              <select name='masschange_yn'>
              <%
              if( bomHash.get("masschange_yn").equals("Y")) {
              %>
                <option value='Y' selected>Y</option>
                <option value='N'>N</option>
              <%
              }else{ 
              %>
                <option value='Y' >Y</option>
                <option value='N' selected>N</option>
              <%
              } 
              %>
              </select>
            </td>
            <td class="tdwhiteM">
              <input type="text" name="parentPart" class="txt_field"  style="width:70"  value='<%=bomHash.get("related_part_list") %>'>
              &nbsp;<a href="javascript:popupPart('setParentPart');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
              &nbsp;<a href="javascript:clearEpmCal('parentPart', 'relBomTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
            </td>
            --%>
            <td class="tdwhiteM">
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="2">&nbsp;</td>
                  <td>
                    <%
                    if((partState.equalsIgnoreCase("APPROVED"))) {
                    %>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <%
                        if( bomHash.get("eca_obj_after_rev").length() == 0 )
                        {
                        %>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseBom2(<%=rowIndex %>);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
                        <%
                        }
                        else
                        {
                        %>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseCancelBom2(<%=rowIndex %>);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00691") %><%--개정취소--%></a></td>
                        <%
                        }
                        %>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                    <%
                    }
                    %>
                  </td>
                  <td width="2">&nbsp;</td>
                  <%
                  //if( bomHash.get("masschange_yn").equals("N") &&  bomHash.get("eca_obj_after_rev").length() > 0 )
                  if((bomHash.get("eca_obj_after_rev").length() > 0 || (!partState.equalsIgnoreCase("APPROVED"))) )
                  {
                  %>
                      <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:changeBom2(<%=rowIndex %>);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01491") %><%--변경--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="2">&nbsp;</td>
                  <%
                  } 
                  /* 
                  else if( bomHash.get("masschange_yn").equals("Y")&&  bomHash.get("eca_obj_after_rev").length() > 0  ) { 
                  */
                  %>
                    <!-- td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:changeBom();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02343") %><%--일괄변경--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td -->
                  <%
                  /* 
                  }
                  */
                  %>
                </tr>
              </table>
            </td>
          </tr>
<%
          rowIndex++;

      }
  }
%>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td class="space15"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td valign="top">
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%></td>
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
	      
	  <!-- div id="div_scroll4" style="height:162;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border" -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	    <tr class="">
	      <td>
	        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="epmDocList">
	          <tr>
	            <!-- td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td -->
	            <td width="100" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%></td>
                <td width="200" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
	            <td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
	            <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
	            <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
	            <!-- td width="200" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td -->
	          </tr>
	          <tr>
	            <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!--  변경 전 -->
	            <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
	          </tr>
	        <!-- /table>
	      </td>
	    </tr>
	    <tr>
	      <td>
	        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="epmDocList" style=table-layout:fixed  -->
	        <%
	        Hashtable<String, String> epmDocHash = null;
	
	        if( epmDocList != null && epmDocList.size() > 0 )
	        {
	            int epmDocLength = epmDocList.size();
	            for( int epmDocCnt=0; epmDocCnt < epmDocList.size(); epmDocCnt++ )
	            {
	                epmDocHash =  epmDocList.get(epmDocCnt);
	        %>
		            <tr>
		              <!-- td width="40" class="tdwhiteM"><%=epmDocCnt+1 %></td -->
		              <td class="tdwhiteM"><%=epmDocHash.get("CADCategory")%></td>
                      <td class="tdwhiteM"><%=epmDocHash.get("eca_obj_no")%></td>
                      <input type="hidden" name="epmNo" value="<%=epmDocHash.get("eca_obj_no")%>"/>
		              <td class="tdwhiteL" title="<%=StringEscapeUtils.escapeHtml(epmDocHash.get("eca_obj_name")) %>"><div class="ellipsis" style="width:400px;"><nobr><%=epmDocHash.get("eca_obj_name")%></nobr></div></td>
		              <td class="tdwhiteM"><a href="javascript:viewEpmDocPopup('<%=EcmUtil.getEPMDocumentOid( epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_pre_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode2(epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_pre_rev")) %></a></td>
		            
		            <%
		            if( !isAddableActivity ||  StringUtil.checkNull(epmDocHash.get("complete_date")).length() > 0 ) {
		            %>
		              <td class="tdwhiteM"><a href="javascript:viewEpmDocPopup('<%=EcmUtil.getEPMDocumentOid( epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_after_rev") )%>');"><%=ketBOMQueryBean.getNewVersionCode2(epmDocHash.get("eca_obj_no"), epmDocHash.get("eca_obj_after_rev")) %>&nbsp;</td>
		            <%
		            } else { 
		            %>
		              <td class="tdwhiteM"><%=epmDocHash.get("eca_obj_after_rev") %>&nbsp;</td>
		            <%
		            } 
		            %>
		              <td class="tdwhiteM"><%=epmDocHash.get("worker_name") %>&nbsp;</td>
		            <%
		            if( StringUtil.checkNull(epmDocHash.get("complete_date")).length() > 0 )
		            {
		            %>
		              <td class="tdwhiteM"><%=epmDocHash.get("complete_date") %>&nbsp;</td>
		            <%
		            } else {
		            %>
		              <td class="tdwhiteM"><font color="#A9A9A9"><%=epmDocHash.get("plan_date") %>&nbsp;</font></td>
		            <%
		            }
		            %>
		              <!-- td class="tdwhiteL0" title="<%=StringEscapeUtils.escapeHtml(epmDocHash.get("eca_obj_act_comment")) %>"><div class="ellipsis" style="width:180px;"><nobr><%=epmDocHash.get("eca_obj_act_comment") %></nobr></div></td -->
		            </tr>
	        <%
	            }
	        }
	        else
	        {
	        %>
	          <tr>
	            <td colspan=8 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "01495") %><%--변경 도면이 없습니다--%>.</td>
	          </tr>
	        <%
	        }
	        %>
	        </table>
	      </td>
	    </tr>
	  </table>
	  <!-- /div -->

      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- /div -->
 