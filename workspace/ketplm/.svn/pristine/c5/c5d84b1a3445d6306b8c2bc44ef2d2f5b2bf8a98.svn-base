<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>
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

.headerLock5 {
  position: relative;
  top:expression(document.getElementById("div_scroll5").scrollTop);
  z-index:99;
 }
</style>
<script type="text/javascript">
$(document).ready(function(){ 
	
	// 담당자
	SuggestUtil.bind('USER', 'relEcaWorkerName', 'relEcaWorkerOid');
    
    // Calener field 설정   
    // 설변부품/도면 변경계획일
    //CalendarUtil.dateInputFormat('relEcaPlanDate'); 
	CalendarUtil.dateInputFormat('relEcaPlanDate', null, {minDate : new Date()});
})

// 비용확보
$(document).on("click", "[name=btnBudgetCheck]", function() {
    var $OBJ = $(this);
    
    var pos = -1;
    $("#relEpmTable").find("[name=btnBudgetCheck]").each(function(i) {
        if($(this).get(0) == $OBJ.get(0)) {
            pos = i;
            return false;
        }
        
    });
    //alert(pos);
    var dev_yn = $("input:radio[name=dev_yn]:checked").val();
    var div_flag = $("select[name=div_flag] option:selected").val();
    var relDieNo = $("input[name=relDieNo_eca]:eq("+ pos +")").val();
    var expectCost = $("input[name=expectCost_eca]:eq("+ pos +")").val();
    var relObjOid = $("input[name=relObjOid]:eq("+ pos +")").val();
    
    var action = "/plm/jsp/ecm/BudgetInterfaceCheck.jsp"
               + "?devYn="    + dev_yn
               + "&division=" + div_flag
               + "&dieNo="    + relDieNo
               + "&cost="     + expectCost
               + "&rowInx="   + pos
               + "&partOid="   + relObjOid
               + "&tableId=relEpmTable"
               ;
    //alert(action);
    
    showProcessing();
    $.ajax({
        type       : "GET",
        url        : action,
        dataType   : "html",
        success    : function(data){
            //alert(data);
            $("iframe[name=setCarData]").html(data);
            
        },
        error      : function(xhr, status, error){
            alert(xhr+"  "+status+"  "+error);
                     
        }
    });
    
});

<% /* Feedback - 비용확보 - 확인 */ %>
function setBudgetYnrelEpmTable( budget_yn, row_inx, msg )
{
    //alert('setBudgetYn( '+ budget_yn +', '+ row_inx +', '+ msg +' )');
    var budget = document.getElementsByName("budget_eca");
    if(budget == null || typeof budget == 'undefined') return;
    
    if( budget_yn == 'N' )
    {
        budget[row_inx].value = '미확보';
    }
    else if( budget_yn =='Y')
    {
        budget[row_inx].value = '확보';
    }
    else {
        budget_yn = 'N';
        budget[row_inx].value = '미확보';
    }

    
    var secureBudgetYn = document.getElementsByName("secureBudgetYn_eca");
    if(secureBudgetYn == null || typeof secureBudgetYn == 'undefined') return;
    secureBudgetYn[row_inx].value = budget_yn;

    
    if( msg != '')
    {
        alert(msg);
    }
    
    
    hideProcessing();
}

</script>
<script type="text/javascript">
<!--
// 자부품 추가/연계도면/BOM 검색 팝업 호출
var newwin = null;
function popupRelEpm2() {
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
    var cnt_chg_reason = 0;
    $("[name=chk_chg_reason]").each(function(i) {
    	if($(this).is(':checked')) {
    		cnt_chg_reason++;
    	    if($(this).val() == 'REASON_12') {
    	    	url += "&isChodo=Y";
    	    	return;
    	    }
    	}
    });
    
    if(cnt_chg_reason == 0){
    	alert("먼저 설계변경사유를 선택하시기 바랍니다.");
    	return false;
    }
    
    
    // ECO No
    url += "&currentEcoNo="+ $("[name=ecoId]").val();
    //alert(url);            
    
    newwin = openWindow(url,"popupRelEpm2","1000","600","status=1,scrollbars=no,resizable=no");
    //attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=725px; dialogHeight:570px; center:yes");
    
}

<% /* deprecated */ %>
function popupRelEpm() {
	alert('[deprecated] ModifyProdEcoActivityForm.jsp, popupRelEpm()');
	var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchActivityPopupForm.jsp";
    url += "&obj=prodMoldCls^1&obj=mode^multi&obj=partType^P";
    //openWindow(url,"","770","600","status=1,scrollbars=no,resizable=no");
    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=725px; dialogHeight:570px; center:yes");
    
}

<% /* deprecated */ %>
function insertRelBomLine(objArr) {
  alert('[deprecated] ModifyProdEcoActivityForm.jsp, insertRelBomLine()');
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relBomTable");

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];

    if( !isDuplicateBom(trArr[2]) )
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      newTr.height="29";
      newTr.onmouseover=function(){relBomTable.clickedRowIndex=this.rowIndex};
      var currRow = targetTable.rows.length - 2;

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
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
      str += "<input type='hidden' name='relEcaEpmDocType' value='" + trArr[7] + "'>";
      
      str += "<input type='checkbox' name='chkSelectRelBom'>";
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
      newTd.innerHTML = trArr[4];

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
      */
      
      //변경내용
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='text' name='relEcaActivityComment' class='txt_field'  style='width:95%'>";
      newTd.innerHTML = str;

      //작업
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "&nbsp;";
      newTd.innerHTML = str;
    }

    document.forms[0].isChanged.value = 'Y';
  }
}

function isDuplicateBom( item_code )
{
    var isDuplicate = false;
  var bomList = document.getElementsByName("relEcaBomNo");

  for( var inx=0; inx < bomList.length; inx++ )
  {
    if( bomList[inx].value == item_code )
    {
      isDuplicate = true;
    }
  }

  return isDuplicate;
}

function popupRelChildPart() {
    if($('[name=pNum]').length == 0){
        alert('추가된 제품정보가 없습니다.');
        return;
    }
    var param = '';
    if($('[name=pNum]').length > 0 ){
        for(var i=0;i<$('[name=pNum]').length;i++){
            param += '&pNums='+$('[name=pNum]')[i].value;
        }
    }
    var url = '/plm/jsp/project/AddChildPartPopup.jsp?1=1'+param;
    var attacheObject = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:600px; center:yes");
    if (typeof attacheObject == "undefined" || attacheObject == null || attacheObject.length == 0) {
        return;
    }
    callBackFn(attacheObject);	
}

// 도면/BOM 라인추가
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
	
	  //if( !isDuplicateLine(trArr[1]) )
	  if( !isDuplicateLine2(trArr[0], trArr[2]) )	  
	  {
	    var tableRows = targetTable.rows.length;
	    var newTr = targetTable.insertRow(tableRows);
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
	    str += "<input type='hidden' name='prodEcaOid' value=''>";
	    str += "<input type='hidden' name='relObjPreRev' value='" + trArr[4] + "'>";
	    //str += "<input type='hidden' name='relEcaWorkerOid' value='" + trArr[5] + "'>";
	    str += "<input type='hidden' name='relEcaEpmDocType' value='" + trArr[7] + "'>";

        <!-- 활동추가를 위한 정보 -->
        str += "<input type='hidden' name='addedPartNumberAtEca' value='"+ trArr[2] +"'>";

        <!-- 부품삭제시 같이 삭제하기 위한 정보 -->
        //str += "<input type='hidden' name='relPartOid' value='"+ trArr[12] +"'>";

        
	    str += "<a href=\"#\" onclick=\"javascript:lfn_deleteData(this);\"><img src=\"/plm/portal/images/b-minus.png\"></a>";
        str += "<input type='checkbox' name='chkSelectRelEpm' value='"+ uniqueTime +"' style='display:none'>";
        str += "<input type='hidden' name='ecaUniqueKey' value='"+ uniqueTime +"'>";

        newTd.innerHTML = str;
	
	    //구분
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    if(trArr[0] == "1") {
	      newTd.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%>";
	    } else {
	      newTd.innerHTML = "BOM";
	    }
	
	    //부품번호
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    str += "<font title=\""+trArr[2]+"\">";
	    str += trArr[2] +"</font>";
	    newTd.innerHTML = str;
	
	    //부품명
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteL";
	    str = "";
	    str += "<font title=\""+trArr[3]+"\"><div class='ellipsis' style='width:110px;'><nobr>";
	    str += trArr[3] +"</nobr></div></font>";
	    newTd.innerHTML = str;
	
	    //Rev
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = trArr[11];
	    newTd.innerHTML = str;
	
	    //담당자
	    /* 
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.innerHTML = trArr[6];
	    */
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    str +="<input type='hidden' name='relEcaWorkerOid' value='" + trArr[5] + "' >";
	    str +="<input type='text' name='relEcaWorkerName' value='"+ trArr[6] +"' class='txt_field' style='width:40px'>";
	    str +="&nbsp;<a href='javascript:popupUser_RelEpmLine();'><img src='/plm/portal/images/icon_user.gif' border='0'></a>";
	    str +="<a href='javascript:clearUser_RelEpmLine();'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
        newTd.innerHTML = str;
	
	    /* 
	    //일괄변경 여부
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    if(trArr[0] == "1") {
	      str += "-<input type='hidden' name='masschange_yn' value=''>";
	    } else {
	      str += "<select name='masschange_yn' class='fm_jmp' style='width:35'>";
	      str += "<option value='Y'>Y&nbsp;</option>";
	      str += "<option value='N' selected>N&nbsp;</option>";
	      str += "</select>";
	    }
	    newTd.innerHTML = str;
	
	    //관련모부품
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    if(trArr[0] == "1") {
	      str += "-<input type='hidden' name='parentPart' value=''>";
	    } else {
	      str += "<input type='text' name='parentPart' class='txt_field' style='width:70' readonly>";
	      str += "&nbsp;<a href=\"javascript:popupPart('setParentPart');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
	      str += "&nbsp;<a href=\"javascript:clearEpmCal('parentPart','relEpmTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
	    }
	    newTd.innerHTML = str;
	    */
	    
	    //변경예정일
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    str += "<input type='text' name='relEcaPlanDate' id='relEcaPlanDate"+ currRow +"' class='txt_field' style='width:70px;text-align:center;'>&nbsp;";
	    //str += "<a href=\"#\" onclick=\"javascript:popupEpmCal('relEcaPlanDate','relEpmTable');\" onfocus='this.blur();'><img src='/plm/portal/images/icon_6.png' border='0'></a>";
	    //str += "<a href=\"javascript:clearEpmCal('relEcaPlanDate','relEpmTable');\" onfocus='this.blur();'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
	    str += "&nbsp;<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"relEcaPlanDate\");' style='cursor: hand;'>"
        newTd.innerHTML = str;
	
	
	    //연계일정
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    /*
	    if(trArr[0] == "1" && isExistMoldChg() ) {
	      str += "<input type='hidden' name='dieNo' value=''>";
	      str += "<input type='text' name='scheduleId' class='txt_field' style='width:52' readonly>";
	      str += "&nbsp;<a href=\"javascript:popupRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
	      str += "&nbsp;<a href=\"javascript:clearRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
	    } else {
	      str += "-<input type='hidden' name='dieNo' value=''>";
	      str += "<input type='hidden' name='scheduleId' value=''>";
	    }*/
	    
        // 도면일 경우
        if(trArr[0] == "1") {
	
		    str += "<input type='hidden' name='dieNo' value=''>";
		    str += "<input type='text' name='scheduleId' class='txt_fieldRO' style='width:60px' readonly>";
		    str += "&nbsp;<a href=\"javascript:popupRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
		    str += "&nbsp;<a href=\"javascript:clearRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";

        } else {

        	str += "-";
            str += "<input type='hidden' name='dieNo' value=''>";
            str += "<input type='hidden' name='scheduleId' value=''>";
            
        }
	    
	    str += "<input type='hidden' name='relEcaActivityComment' value=''>";
	    newTd.innerHTML = str;

	    
        //Die No
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.title = trArr[8];
        newTd.className = "tdwhiteM";
        str = "";
        if( trArr[0] != "1" && trArr[8] != '' )
        {
            //str += getTruncStr(trArr[5], 10);
            //str += "<input type='hidden' name='relDieNo' value='" + trArr[5] + "'>";

            if( trArr[10] > 1 )
            {
                /* 
                str += "<input type='text' name='relDieNo' value='"+trArr[5]+"' class='txt_field' style='width:80px' readonly>&nbsp;";
                str +="&nbsp;<a href=\"javascript:searchDieNo('"+trArr[1]+"');\" onfocus=\"this.blur();\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
                */
                str += "<input type='hidden' name='relDieNo_eca' value='"+trArr[8]+"'>";
                str += "<a href=\"javascript:searchDieNo('"+trArr[1]+"');\" onfocus=\"this.blur();\">"+trArr[8]+"</a>";                    
            }
            else
            {
                str += "<input type='hidden' name='relDieNo_eca' value='"+trArr[8]+"'>";
                str += ""+trArr[8]+"";
            }
            newTd.innerHTML = str;
        }
        else
        {
            str +=  "&nbsp;"
            str += "<input type='hidden' name='relDieNo_eca' value=''>";
            newTd.innerHTML = str;
        }

        // 도면일 경우
        if(trArr[0] == "1") {
        	
            //예상비용(천원)
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<input type='hidden' name='expectCost_eca'>";

            //비용확보여부
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL0";
            str = "";
            str += "<div style='display:none'><a href='javascript:void(0);' name='btnBudgetCheck' class='btn_blue'>확인</a></div>";
            
            str += "<input type='hidden' name='budget_eca' value='확보'><input type='hidden' name='secureBudgetYn_eca' value='Y'>";
             
            newTd.innerHTML = str;
             
        } 
        // BOM일 경우
        else {
	        
	        var partStr = trArr[2]+"";
	        subValue =  partStr.substr(0,3);
	
	        if( (subValue == 'R40'  || subValue == 'R41' || subValue == 'R50' || subValue == 'R60' || subValue == 'R68' )
	            || (subValue == 'H42'  || subValue == 'H43' || subValue == 'H45'  || subValue == 'H46' || subValue == 'H47' 
	                || subValue == 'H52' || subValue == 'H54' || subValue == 'H57' || subValue == 'H59' || subValue == 'H64' 
	                    || subValue == 'H65' || subValue == 'H66' )
	                        || (subValue == 'K50')
	                            || (partStr.substr(0,2) == '68') 
	                                || trArr[8] == '')
	        {
	
	            //예상비용(천원)
	            newTd = newTr.insertCell(newTr.cells.length);
	            newTd.className = "tdwhiteM";
	            newTd.innerHTML = "<input type='hidden' name='expectCost_eca'>";
	
	            //비용확보여부
	            newTd = newTr.insertCell(newTr.cells.length);
	            newTd.className = "tdwhiteL0";
	            str = "";
	            
	            if(trArr[8] == '') {
	            	
	            } else {
	            	str += "확보";
	            }
	            str += "<div style='display:none'><a href='javascript:void(0);' name='btnBudgetCheck' class='btn_blue'>확인</a></div>";
                str += "<input type='hidden' name='budget_eca' value='확보'><input type='hidden' name='secureBudgetYn_eca' value='Y'>";
	             
	            newTd.innerHTML = str;
	      
	        }
	        else
	        {
	
	            //예상비용(천원)
	            newTd = newTr.insertCell(newTr.cells.length);
	            newTd.className = "tdwhiteM";
	            newTd.innerHTML = "<input type='text' name='expectCost_eca' class='txt_fieldR' style='width:40px'>";
	
	            //비용확보여부
	            newTd = newTr.insertCell(newTr.cells.length);
	            newTd.className = "tdwhiteL0";
	            str = "";
	            
	            str += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
	            str += "  <tr>";
	            str += "    <td align='middle'>";
	            str += "      <input type='hidden' name='secureBudgetYn_eca' value='N'>";
	            str += "      <input type='text' name='budget_eca' value='미확보' class='txt_field' style='width:35px' readonly>";
	            str += "    </td>";
	            str += "    <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>";
	            str += "      <tr>";
	            str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
	            str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:void(0);' name='btnBudgetCheck' class='btn_blue'><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>";
	            str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
	            str += "      </tr>";
	            str += "    </table></td>";
	            str += "  </tr>";
	            str += "</table>";
	            
	            newTd.innerHTML = str;
	
	        }

        }
        isAdded = true;
        
	    
	    // 담당자
	    SuggestUtil.bind('USER', 'relEcaWorkerName', 'relEcaWorkerOid');

	    // Calener field 재설정
	    //CalendarUtil.dateInputFormat('relEcaPlanDate');
	    CalendarUtil.dateInputFormat('relEcaPlanDate', null, {minDate : new Date()});

	  }
	}

	
	//if(newwin != null) newwin.window.close();
	if(isAdded) {
		alert('<%=messageService.getString("e3ps.message.ket_message", "04045") %><%--\'선택\'하신 Part가 추가되었습니다.--%>');
		newwin.window.focus();
	}
	
}
function lfn_getUniqueTime() {
    var time = new Date().getTime();
    while (time == new Date().getTime());
    return new Date().getTime();
}

function isDuplicateLine( oid )
{
	var isDuplicate = false;
	
	var oidList = document.getElementsByName("relObjOid");
	
	for( var i=0; i < oidList.length ; i++)
	{
	  if( oid == oidList[i].value )
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
    
    return isDuplicate;
}

function lfn_deleteData(obj) {

	<%-- 
	var relEcaActivityType = $(obj).parent().find("[name=relEcaActivityType]").val();
	if(relEcaActivityType == '2') {
		var relObjOid = $(obj).parent().find("[name=relObjOid]").val();
	    if(confirm('<%=messageService.getString("e3ps.message.ket_message", "04046") %>관련 도면도 제거하시겠습니까?')) {
			$("[name=relPartOid ]").each(function(i) {
		        if($(this).val() == relObjOid) {
		        	$(this).parent().parent().remove();
		        }
		        
		    });
			
		}
		
	}
	--%>
	
	$(obj).parent().parent().remove();
}
var userRelEmpLinePos = 0;
// 도면/BOM 담당자검색 팝업
function popupUser_RelEpmLine()
{
	var targetTable = document.getElementById('relEpmTable');
	var tableRows = targetTable.rows.length;
	userRelEmpLinePos = document.getElementById('relEpmTable').clickedRowIndex-1;
	
	SearchUtil.selectOneUser('','','setUser_RelEpmLine');
	
}

 // 도면/BOM 담당자 검색 팝업 리턴 포맷
 function setUser_RelEpmLine(objArr)
 {
   if(objArr.length == 0) {
       return;
   }
   var inx = userRelEmpLinePos;
   var trArr = objArr[0];
   var form = document.forms[0];
 
   if(typeof form.relEcaWorkerOid.length == 'undefined') {
	   alert(userRelEmpLinePos);
       form.relEcaWorkerOid.value = trArr[0];
       form.relEcaWorkerName.value = trArr[4];
       
   } else {
       form.relEcaWorkerOid[inx].value = trArr[0];
       form.relEcaWorkerName[inx].value = trArr[4];
       
   }
   
 }

// 도면/BOM 담당자 초기화
function clearUser_RelEpmLine()
{
    var form = document.forms[0];
	
	var targetTable = document.getElementById('relEpmTable');
	var tableRows = targetTable.rows.length;
	var pos = eval('relEpmTable').clickedRowIndex;
    
    pos -= 1;
   
    if(typeof form.relEcaWorkerOid.length == 'undefined') {
        form.relEcaWorkerOid.value = '';
        form.relEcaWorkerName.value = '';
       
    } else {
	    form.relEcaWorkerOid[pos].value = '';
	    form.relEcaWorkerName[pos].value = '';
    }
   	
}


//변경예정일 팝업
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

<% /* deprecated */ %>
<% /* 관련모부품 검색 */ %>
function popupPart( fncall )
{
  alert('[deprecated] ModifyProdEcoActivityForm.jsp, popupPart()');
  var url="/plm/ext/part/base/listPartPopup.do??mode=multi&pType=P&fncall="+fncall;
  openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
}
<% /* 관련모부품 추가 */ %>
function setParentPart( objArr )
{
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relEpmTable");
  var form = document.forms[0];

  var tableRows = targetTable.rows.length;
  var pos = targetTable.clickedRowIndex - 1;

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];

    if( tableRows > 2 )
    {
      form.parentPart[pos].value += trArr[1];

      if( i != objArr.length -1 )
      {
        form.parentPart[pos].value += ',';
      }
    }
    else
    {
       form.parentPart.value += trArr[1];
       if( i != objArr.length -1 )
      {
        form.parentPart.value += ',';
      }

    }
  }
}

//연계일정검색 팝업
function popupRelPlan(tableId){
	var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchMoldPlanPopupForm.jsp?oid="+document.forms[0].oid.value;
	arr = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=770px; dialogHeight:500px; center:yes");
	if(typeof arr == "undefined" || arr == null) {
	  return;
	}
	var pos = eval(tableId).clickedRowIndex - 1;
	setRelPlan(arr, pos);
}

//연계일정 검색 팝업 리턴 포맷
function setRelPlan(objArr, pos) {
	if(objArr.length == 0) {
	  return;
	}
	var trArr = objArr[0];
	var form = document.forms[0];
	var targetTable = document.getElementById("relEpmTable");
	var tableRows = targetTable.rows.length;
	if(tableRows > 2){//테이블 헤더가 포함되어 있기 때문에.
	  for(var cnt = 0; cnt < tableRows-1; cnt++){
	    form.scheduleId[cnt].value = trArr[2];
	    form.dieNo[pos].value = trArr[1];
	    form.relEcaPlanDate[cnt].value= trArr[4];
	  }
	} else {
	  form.scheduleId.value = trArr[2];
	  form.dieNo.value = trArr[1];
	  form.relEcaPlanDate.value =trArr[4];
	}
}

//연계일정 초기화
function clearRelPlan(tableId){
	var form = document.forms[0];
	var targetTable = document.getElementById(tableId);
	var tableRows = targetTable.rows.length;
	var pos = eval(tableId).clickedRowIndex - 1;
	if(tableRows > 2){
	  form.scheduleId[pos].value = '';
	  form.dieNo[pos].value = '';
	} else {
	  form.scheduleId.value = '';
	  form.dieNo.value = '';
	}
}

//-->
</script>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td valign="top">
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01261") %><%--도면/BOM--%></td>
          <td align="right">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right"><%=messageService.getString("e3ps.message.ket_message", "04440") %><%--테이블 좌측상단 '+'을 클릭하여 추가하십시오.--%></td>
              </tr>
            </table>
            <!-- table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelEpm2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "04470") %><%--자부품 추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelEpm2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "04480") %><%--연계도면 추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelEpm2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "03245") %><%--활동추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLineMinus1('forms[0]', 'relEpmTable', 'chkSelectRelEpm', 'chkAllRelEpm', 'deleteRelEpmList');" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "03234") %><%--활동 삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table -->
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      
      <div id="div_scroll4" style="width:100%;" class="table_border">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relEpmTable">
        <tr class="headerLock4">
          <td width="25" class="tdblueM"><a href="#" onclick="javascript:popupRelEpm2();"><img src="/plm/portal/images/b-plus.png"></a></td>
          <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01580") %><%--부품/도면번호--%></td>
          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01579") %><%--부품/도면명--%></td>
          <td width="35" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <%-- 
          <td width="40"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02332") %>일괄</td>
          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00928") %>관련모부품번호</td>
          --%>
          <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01504") %><%--변경계획일--%></td>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02125") %><%--연계일정--%></td>
          
          <td width="60" class="tdblueM">Die No</td>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td>
          <td width="115" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%><span class="red">*</span></td>          
        </tr>
        <%
        boolean isPlanningStep = false;
        if( ecoHash.get("status") == null || ecoHash.get("status").equals("PLANNING") || ecoHash.get("status").equals("REWORK") )
        {
            isPlanningStep = true;
        }
        
        
        Hashtable<String, String> epmDoc = null;
        String selectTxt = "";
        if( epmDocList != null && epmDocList.size() > 0 )
        {
          for( int eCnt=0; eCnt < epmDocList.size(); eCnt++ )
          {
            epmDoc = epmDocList.get(eCnt);

            //selectTxt = epmDoc.get("activity_type") + "|"+epmDoc.get("eca_obj_link_oid");
            
            String relEcaActivityType = epmDoc.get("activity_type");
        %>
        <tr onMouseOver='relEpmTable.clickedRowIndex=this.rowIndex'>
          <td class="tdwhiteM">
            
            <%
            // 도면일 경우
            if(relEcaActivityType.equalsIgnoreCase("1")) {
            %>
                <a href="#" onclick="javascript:$(this).next().attr('checked',true); deleteDataLineMinus1('forms[0]', 'relEpmTable', 'chkSelectRelEpm', 'chkAllRelEpm', 'deleteRelEpmList');"><img src="/plm/portal/images/b-minus.png"></a>
            <%
            // 부품일 경우
            } else {
            %>
                <a href="#" onclick="javascript:$(this).next().attr('checked',true); deleteDataLineMinus1('forms[0]', 'relEpmTable', 'chkSelectRelEpm', 'chkAllRelEpm', 'deleteRelBomList');"><img src="/plm/portal/images/b-minus.png"></a>
            <%
            }
            %>
            
            <input name="chkSelectRelEpm" type="checkbox"  value='<%=epmDoc.get("eca_oid") %>' style="display:none">
            <input type='hidden' name="ecaUniqueKey" value='<%=epmDoc.get("eca_oid") %>'>
            <input type="hidden" name="masschange_yn" value='N'>
            
            <input type="hidden" name="relEcaActivityType" value='<%=relEcaActivityType %>'>
            <input type="hidden" name="relEcaLinkOid" value='<%=epmDoc.get("eca_obj_link_oid") %>'>
            <input type="hidden" name="relObjOid" value='<%if(epmDoc.get("activity_type").equals("2")) {out.print(epmDoc.get("before_part_oid"));}else{out.print(epmDoc.get("eca_obj_oid"));}%>'>
            <input type="hidden" name="prodEcaOid" value='<%=epmDoc.get("eca_oid") %>'>
            <input type="hidden" name="relObjPreRev" value='<%=epmDoc.get("eca_obj_pre_rev") %>'>
            
            <input type="hidden" name="relEcaEpmDocType" value='<%=epmDoc.get("obj_type") %>'>
            
            <!-- 활동추가를 위한 정보 -->
            <input type="hidden" name="addedPartNumberAtEca" value='<%=epmDoc.get("eca_obj_no") %>'>
            
	        <!-- 부품삭제시 같이 삭제하기 위한 정보 -->
	        <!-- input type='hidden' name='relPartOid' value=<%=epmDoc.get("relPartOid") %>' -->
	            
          </td>
          <td class="tdwhiteM"><%=epmDoc.get("activity_type_name") %></td>
          <td class="tdwhiteM" title="<%=epmDoc.get("eca_obj_no") %>"><%=epmDoc.get("eca_obj_no") %></td>
          <td class="tdwhiteL" title="<%=epmDoc.get("eca_obj_name") %>"><div class="ellipsis" style="width:110px;"><nobr><%=epmDoc.get("eca_obj_name") %></nobr></div></td>
          <!-- td class="tdwhiteM"><%=epmDoc.get("eca_obj_pre_rev") %></td -->
          <%
          String viewoid = "";
          if(epmDoc.get("activity_type").equals("2")) {
              viewoid = EcmUtil.getPartOid(epmDoc.get("eca_obj_no"),epmDoc.get("eca_obj_pre_rev") );
          } else {
              viewoid = epmDoc.get("eca_obj_oid");
          }
          %>
          <td class="tdwhiteM"><a href="javascript:viewPart('<%=viewoid %>');"><%=ketBOMQueryBean.getNewVersionCode2(epmDoc.get("eca_obj_no"),epmDoc.get("eca_obj_pre_rev")) %>&nbsp;</a></td>
                 
          <%
         if(isPlanningStep) {
          %>
          <td class="tdwhiteM">
            <input type="hidden" name="relEcaWorkerOid" value='<%=epmDoc.get("worker_id") %>'>
            <input type='text' name='relEcaWorkerName' value='<%=epmDoc.get("worker_name") %>' class='txt_field' style='width:40px'>
            <a href='javascript:popupUser_RelEpmLine();'><img src='/plm/portal/images/icon_user.gif' border='0'></a>
            <a href='javascript:clearUser_RelEpmLine();'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
          </td>          
          <%
          } else {
          %>
          <td class="tdwhiteM">
            <input type="hidden" name="relEcaWorkerOid" value='<%=epmDoc.get("worker_id") %>'>
            <input type='hidden' name='relEcaWorkerName' value='<%=epmDoc.get("worker_name") %>'>
            <%=epmDoc.get("worker_name") %>
          </td>
          <%
          }
          %>
          
        <%-- 
          <td class="tdwhiteM">
        <%
            if( epmDoc.get("activity_type").equals("2") ){
        %>
              <select name='masschange_yn' class='fm_jmp' style='width:35'>
        <%
              if( epmDoc.get("masschange_yn").equals("Y") ){
        %>
                <option value='Y' selected>Y&nbsp;</option>
                <option value='N' >N&nbsp;</option>
        <%
              }else{
        %>
                <option value='Y' >Y&nbsp;</option>
                <option value='N' selected>N&nbsp;</option>
        <%
              }
        %>
              </select>
        <%
            }
            else{
        %>
              -<input type='hidden' name='masschange_yn''>
        <%
            }
        %>
          </td>
          <td class="tdwhiteM">
        <%
            if( epmDoc.get("activity_type").equals("2") ){
        %>
              <input type='text' name='parentPart'  value='<%=epmDoc.get("related_part_list") %>' class='txt_field' style='width:60;text-align:center;' readonly>&nbsp;<a href="#" onclick="javascript:popupPart('setParentPart');" onfocus='this.blur();'><img src='/plm/portal/images/icon_5.png' border='0'></a>&nbsp;<a href="javascript:clearEpmCal('parentPart','relEpmTable');" onfocus='this.blur();'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
        <%  }else{ %>
              -<input type='hidden' name='parentPart'  >
        <%  } %>
          </td>
        --%>
          <td class="tdwhiteM">
            <input type='text' name='relEcaPlanDate'  id='relEcaPlanDate<%=eCnt %>' value='<%=epmDoc.get("eca_obj_plan_date") %>' class='txt_field' style='width:70px;text-align:center;'>
            <!-- a href="#" onclick="javascript:popupEpmCal('relEcaPlanDate','relEpmTable');" onfocus='this.blur();'><img src='/plm/portal/images/icon_6.png' border='0'></a>
            <a href="javascript:clearEpmCal('relEcaPlanDate','relEpmTable');" onfocus='this.blur();'><img src='/plm/portal/images/icon_delete.gif' border='0'></a -->
            <img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue("relEcaPlanDate");' style='cursor: hand;'>
        
          </td>


          <%
          // 도면일 경우
          if( epmDoc.get("activity_type").equals("1") )
          {
          %>
          <%-- 연계일정 --%>
          <td class="tdwhiteM">
            <input type='hidden' name='dieNo' value='<%=epmDoc.get("die_no") %>'>
            <input type='text' name='scheduleId'  value='<%=epmDoc.get("schedule_id") %>' class='txt_fieldRO' style='width:60px' readonly>
            <a href="javascript:popupRelPlan('relEpmTable');"><img src='/plm/portal/images/icon_5.png' border='0'></a>
            <a href="javascript:clearRelPlan('relEpmTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
            
            <input type='hidden' name='relEcaActivityComment'  value='<%=epmDoc.get("eca_obj_act_comment") %>' >
          </td>
          <%-- Die No --%>
          <td class="tdwhiteM" title="<%=epmDoc.get("related_die_no") %>"><%=epmDoc.get("related_die_no") %>
            <input type='hidden' name='relDieNo_eca' value='<%=epmDoc.get("related_die_no") %>' >
          </td>
          <td class="tdwhiteM"><input type='hidden' name='expectCost_eca'  value='<%=epmDoc.get("expect_cost") %>'></td>
          <td class="tdwhiteL0">                  
            <table width='100%' border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align='middle'>
                  <input type='hidden' name='secureBudgetYn_eca' value='<%=epmDoc.get("secure_budget_yn") %>' >
                  <%
                  String budgetEca = (epmDoc.get("secure_budget_yn") == null || ((String) epmDoc.get("secure_budget_yn")).equalsIgnoreCase("N")) ? "미확보" : "확보"; 
                  %>
                  <input type='hidden' name='budget_eca' value='<%=budgetEca %>'>
                </td>  
                <td align='right' >
                  <div style="display:none"><a href="javascript:void(0)" name="btnBudgetCheck" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></div>
                </td>
              </tr>
            </table>
          </td>          
          <%
          }
          // 부품일 경우
          else
          {
              String budgetEca = (epmDoc.get("secure_budget_yn") == null || ((String) epmDoc.get("secure_budget_yn")).equalsIgnoreCase("N")) ? "미확보" : "확보"; 
              
          %>          
          <%-- 연계일정 --%>
          <td class="tdwhiteM">-
            <input type='hidden' name='dieNo' value=''>
            <input type='hidden' name='scheduleId' value=''>
            
            <input type='hidden' name='relEcaActivityComment'  value='<%=epmDoc.get("eca_obj_act_comment") %>' >
          </td>
          
          <%
	          String partStr = epmDoc.get("eca_obj_no");
	          String subValue =  partStr.substring(0,3);
	  
	          if( (subValue.equals("R40")  || subValue.equals("R41") || subValue.equals("R50") || subValue.equals("R60") || subValue.equals("R68") )
	              || (subValue.equals("H42")  || subValue.equals("H43") || subValue.equals("H45")  || subValue.equals("H46") || subValue.equals("H47") 
	                  || subValue.equals("H52") || subValue.equals("H54") || subValue.equals("H57") || subValue.equals("H59") || subValue.equals("H64") 
	                      || subValue.equals("H65") || subValue.equals("H66") )
	                          || (subValue.equals("K50"))
	                              || (partStr.substring(0,2).equals("68")) 
	                                  || epmDoc.get("related_die_no").equals(""))
	          {
	  
          %>
          
          <%-- Die No --%>
          <td class="tdwhiteM"><input type='hidden' name='relDieNo_eca' value='<%=epmDoc.get("related_die_no") %>' ></td>
          <td class="tdwhiteM"><input type='hidden' name='expectCost_eca'  value='<%=epmDoc.get("expect_cost") %>'></td>
          <td class="tdwhiteL0">                  
            <input type='hidden' name='budget_eca' value='<%=budgetEca %>'>
            <input type='hidden' name='secureBudgetYn_eca' value='<%=epmDoc.get("secure_budget_yn") %>' >
            <div style="display:none"><a href="javascript:void(0)" name="btnBudgetCheck" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></div>
          </td>
                    
          <%	        
	          }  
	          else {
          %>
          
          <%-- Die No --%>
          <td class="tdwhiteM" title="<%=epmDoc.get("related_die_no") %>">
            <div class="ellipsis" style="width:60px;"><nobr><%=epmDoc.get("related_die_no") %></nobr></div>
            <input type='hidden' name='relDieNo_eca' value='<%=epmDoc.get("related_die_no") %>' >
          </td>
          <td class="tdwhiteM"><input type='text' name='expectCost_eca'  value='<%=epmDoc.get("expect_cost") %>' class='txt_fieldR' style='width:40px'></td>
          <td class="tdwhiteL0">                  
            <table width='100%' border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align='middle'>
                  <input type='hidden' name='secureBudgetYn_eca' value='<%=epmDoc.get("secure_budget_yn") %>' >
                  <input type='text' name='budget_eca' value='<%=budgetEca %>' class='txt_field' style='width:35px' readonly>
                </td>  
                <td align='right' >
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:void(0)" name="btnBudgetCheck" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
          
          <%
              }
          %>
          
          <%
          }
          %>


          <%-- 디버깅 --%>
          <!-- td class="tdwhiteL0"><%=epmDoc.get("eca_oid") %>&nbsp;</td>
          <td class="tdwhiteL0"><%=((e3ps.ecm.entity.KETProdChangeActivity)e3ps.common.util.CommonUtil.getObject(epmDoc.get("eca_oid"))).getLifeCycleState().toString() %>&nbsp;</td -->
          
                              
        </tr>
        <%
          } // for( int eCnt=0; eCnt < epmDocList.size(); eCnt++ )
        }   // if( epmDocList != null && epmDocList.size() > 0 )
        %>
      </table>
      </div>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
          <td class="space15" align="right"><b>*<%=messageService.getString("e3ps.message.ket_message", "02126") %><%--연계일정은 양산단계 금형변경시 입력하세요--%></b></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
