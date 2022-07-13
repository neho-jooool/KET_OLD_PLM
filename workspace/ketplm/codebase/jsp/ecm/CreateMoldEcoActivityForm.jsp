<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.headerLock2 {
  position: relative;
  top:expression(document.getElementById("div_scroll2").scrollTop);
  z-index:99;
 }
</style>
<script type="text/javascript">
$(document).ready(function(){ 
    
    // 담당자
    SuggestUtil.bind('USER', 'relEcaEpmWorkerName', 'relEcaEpmWorkerOid');
    
    // 표준품 List 담당자
    SuggestUtil.bind('USER', 'relEcaDocWorkerName', 'relEcaDocWorkerOid');
    
    // Calener field 설정
    // 도면/BOM 변경계획일
    //CalendarUtil.dateInputFormat('relEcaEpmPlanDate');   
    CalendarUtil.dateInputFormat('relEcaEpmPlanDate', null, {minDate : new Date()});
    
    // 표준품 변경예정일
    //CalendarUtil.dateInputFormat('relEcaDocPlanDate');
    CalendarUtil.dateInputFormat('relEcaDocPlanDate', null, {minDate : new Date()});
    
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
    var dev_yn = $("input:radio[name=rdoDevYn]:checked").val();
    var div_flag = $("select[name=divisionFlag] option:selected").val();
    var relDieNo = $("input[name=relDieNo_eca]:eq("+ pos +")").val();
    var expectCost = $("input[name=expectCost_eca]:eq("+ pos +")").val();
    
    var action = "/plm/jsp/ecm/BudgetInterfaceCheck.jsp"
               + "?devYn="    + dev_yn
               + "&division=" + div_flag
               + "&dieNo="    + relDieNo
               + "&cost="     + expectCost
               + "&rowInx="   + pos
               
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
            $("iframe[name=download]").html(data);
            
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
//자부품 추가/연계도면/BOM 검색 팝업 호출
var newwin = null;
function popupRelEpm2() {
    var url = "/plm/jsp/ecm/SearchActivityPopupForm.jsp"
	        //+ "?obj=prodMoldCls^2&obj=mode^multi&obj=partType^D";
	        + "?prodMoldCls=2&mode=multi&partType=D";
	            
    
    /* 
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchActivityPopupForm.jsp";
    url += "&obj=prodMoldCls^2&obj=mode^multi&obj=ecoEcaCls^eca&obj=epmBomCls^2&obj=partType^D";
    url += "&obj=partNumber^" + partNumber;
    */
    
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
    $("[name=chkChangeReason]").each(function(i) {
        if($(this).is(':checked')) {
            if($(this).val() == 'REASON_10') {
                url += "&isChodo=Y";
                return;
            }
        }
    });
    
    // ECO No
    url += "&currentEcoNo="+ $("[name=ecoId]").val();
    
    newwin = openWindow(url,"popupRelEpm2","1000","600","status=1,scrollbars=no,resizable=no");
    //attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=725px; dialogHeight:570px; center:yes");
    
}

<% /* deprecated */ %>
//연계도면/BOM 검색 팝업 호출
function popupRelEpm() {
	var form = document.forms[0];
	/*
	var targetTable = document.getElementById("relPartTable");
	var tableRows = targetTable.rows.length;
	var partNumber = "";
	if(tableRows > 0){
	  if(tableRows > 1){
	    partNumber = form.relPartNumber[0].value;
	  } else {
	    partNumber = form.relPartNumber.value;
	  }
	}
	*/
	
	var partNumber = "";
	var oidList = document.getElementsByName("relPartNumber");
	
	for( var i=0; i < oidList.length ; i++)
	{
	  partNumber = oidList[i].value;
	  break;
	}
	
	var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchActivityPopupForm.jsp";
	url += "&obj=prodMoldCls^2&obj=mode^multi&obj=partType^D";
	url += "&obj=partNumber^" + partNumber;
	
	//openWindow(url,"","770","600","status=1,scrollbars=no,resizable=no");
	attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=900px; dialogHeight:600px; center:yes");
	if(typeof attache == "undefined" || attache == null) {
	  return;
	}
	//var attache = getSampleRelEpmData();
	insertRelEpmLine(attache);
}
	
function isDuplicateLine( oid )
	{
	var isDuplicate = false;
	
	var oidList = document.getElementsByName("relEcaEpmOid");
	
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
    
    var numberList = document.getElementsByName("relEcaEpmNo");
    var typeList = document.getElementsByName("relEcaEpmActivityType");
    
    for( var i=0; i < numberList.length ; i++)
    {
      if( number == numberList[i].value && type == typeList[i].value )
      {
        isDuplicate = true;
      }
    }
    
    return isDuplicate;
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
	    str = "";
	    str += "<input type='hidden' name='relEcaEpmActivityType' value='" + trArr[0] + "'>";
	    str += "<input type='hidden' name='moldEcaEpmOid' value=''>";
	    str += "<input type='hidden' name='relEcaEpmLinkOid' value=''>";
	    str += "<input type='hidden' name='relEcaEpmOid' value='" + trArr[1] + "'>";
	    str += "<input type='hidden' name='beforePartOid' value='" + trArr[1] + "'>";
	    str += "<input type='hidden' name='relEcaEpmNo' value='" + trArr[2] + "'>";
	    str += "<input type='hidden' name='relEcaEpmName' value='" + trArr[3] + "'>";
	    str += "<input type='hidden' name='relEcaEpmPreRev' value='" + trArr[4] + "'>";
	    str += "<input type='hidden' name='relEcaEpmWorkerOid' value='" + trArr[5] + "'>";
	    str += "<input type='hidden' name='relEcaEpmChangeYn' value='N'>";
	    str += "<input type='hidden' name='relEcaEpmDocType' value='" + trArr[7] + "'>";
	    str += "<input type='hidden' name='relEcaEpmActivityComment'>";
	    
	    <!-- 예상비용 관련정보 -->
	    str += "<input type='hidden' name='relDieNo_eca' value=''>";
	    str += "<input type='hidden' name='expectCost_eca'>";
	    str += "<input type='hidden' name='budget_eca' value='확보'>";
	    str += "<input type='hidden' name='secureBudgetYn_eca' value='Y'>";

        <!-- 활동추가를 위한 정보 -->
        str += "<input type='hidden' name='addedPartNumberAtEca' value='"+ trArr[2] +"'>";

	    str += "<input type='checkbox' name='chkSelectRelEpm'>";
	    //newTd.innerHTML = str;
	    newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
	                    + "<div style=\"display:none;\">"+ str +"</div>"
	                    ;
	    
	    //구분
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    if(trArr[0] == "1") {
	      newTd.innerHTML = "도면";
	    } else {
	      newTd.innerHTML = "BOM";
	    }
	
	    //금형부품번호
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    str += "<font title='"+trArr[2]+"'>";
	    str += trArr[2] +"</font>";
	    newTd.innerHTML = str;
	
	    //금형부품명
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteL";
	    str = "";
	    str += "<font title=\""+trArr[3]+"\"><div class='ellipsis' style='width:340px;'><nobr>";
	    str += trArr[3] +"</nobr></div></font>";
	    newTd.innerHTML = str;
	
	    //Rev
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.innerHTML = trArr[4];
	
	    //담당자
	    /* 
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.innerHTML = trArr[6];
        */
	    newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        str = "";
        str +="<input type='hidden' name='relEcaEpmWorkerOid' value='" + trArr[5] + "' >";
        str +="<input type='text' name='relEcaEpmWorkerName' value='"+ trArr[6] +"' class='txt_field' style='width:50px'>&nbsp;";
        str +="<a href='javascript:popupUser_RelEpmLine();'><img src='/plm/portal/images/icon_user.gif' border='0'></a><a href='javascript:clearUser_RelEpmLine();'>&nbsp;<img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
        newTd.innerHTML = str;
	
	    //변경예정일
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    str += "<input type='text' name='relEcaEpmPlanDate' id='relEcaEpmPlanDate"+ currRow +"' class='txt_field' style='width:70px;'>";
	    /* 
	    str += "&nbsp;<a href=\"#\" onclick=\"javascript:popupEpmCal('relEcaEpmPlanDate','relEpmTable');\" onfocus='this.blur();'><img src='/plm/portal/images/icon_6.png' border='0'></a>";
	    str += "&nbsp;<a href=\"javascript:clearEpmCal('relEcaEpmPlanDate','relEpmTable');\" onfocus='this.blur();'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
	    */
	    str += "<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"relEcaEpmPlanDate\");' style='cursor: hand;'>";
        newTd.innerHTML = str;
	
	
	    //연계일정
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    // 도면일 경우
        if(trArr[0] == "1") {
        	
	      str += "<input type='hidden' name='oldMoldChangePlanOid' value=''>";
	      str += "<input type='hidden' name='newMoldChangePlanOid' value=''>";
	      str += "<input type='hidden' name='moldEcoPlanLinkOid' value=''>";
	      str += "<input type='hidden' name='dieNo' value=''>";
	      str += "<input type='text' name='scheduleId' class='txt_field' style='width:50px' readonly>";
	      str += "&nbsp;<a href=\"javascript:popupRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
	      str += "&nbsp;<a href=\"javascript:clearRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
	    
        } else {
        	
	      str += "-<input type='hidden' name='oldMoldChangePlanOid' value=''>";
	      str += "<input type='hidden' name='newMoldChangePlanOid' value=''>";
	      str += "<input type='hidden' name='moldEcoPlanLinkOid' value=''>";
	      str += "<input type='hidden' name='dieNo' value=''>";
	      str += "<input type='hidden' name='scheduleId' value=''>";
	    
        }
	    newTd.innerHTML = str;
	
	    //변경내용
	    /* 
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    str = "";
	    str += "<input type='text' name='relEcaEpmActivityComment' class='txt_field'  style='width:98%'>";
	    newTd.innerHTML = str;
        */
	    <%-- 
        //Die No
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "100";
        newTd.title = trArr[2];
        newTd.className = "tdwhiteM";
        str = "";
        if( trArr[0] != "1" && trArr[2] != '' )
        {
            //str += getTruncStr(trArr[5], 10);
            //str += "<input type='hidden' name='relDieNo' value='" + trArr[5] + "'>";

            if( trArr[1] > 1 )
            {
                /* 
                str += "<input type='text' name='relDieNo' value='"+trArr[5]+"' class='txt_field' style='width:80px' readonly>&nbsp;";
                str +="&nbsp;<a href=\"javascript:searchDieNo('"+trArr[1]+"');\" onfocus=\"this.blur();\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
                */
                str += "<input type='hidden' name='relDieNo_eca' value='"+trArr[2]+"'>";
                str += ""+trArr[2]+"<a href=\"javascript:searchDieNo('"+trArr[1]+"');\" onfocus=\"this.blur();\">"+trArr[2]+"</a>";                    
            }
            else
            {
                str += "<input type='hidden' name='relDieNo_eca' value='"+trArr[2]+"'>";
                str += ""+trArr[2]+"";
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
                || (partStr.substr(0,2) == '68') )
            {
    
                //예상비용(천원)
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input type='hidden' name='expectCost_eca'>";
    
                //비용확보여부
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteL0";
                str = "";
                
                str += "확보";
                str += "<div style='display:none'><a href='javascript:void(0);' name='btnBudgetCheck' class='btn_blue'>확인</a></div>";
                str += "<input type='hidden' name='budget_eca' value='확보'><input type='hidden' name='secureBudgetYn_eca' value='Y'>";
                 
                newTd.innerHTML = str;
          
            }
            else
            {
    
                //예상비용(천원)
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input type='text' name='expectCost_eca' class='txt_fieldR' style='width:80px'>";
    
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
                str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:void(0);' name='btnBudgetCheck' class='btn_blue'><%=messageService.getString("e3ps.message.ket_message", "03226") %>확인</a></td>";
                str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
                str += "      </tr>";
                str += "    </table></td>";
                str += "  </tr>";
                str += "</table>";
                
                newTd.innerHTML = str;
    
            }

        }
        --%>
        isAdded = true;
        
        
        
	  }
	}
	
 // 담당자
    SuggestUtil.bind('USER', 'relEcaEpmWorkerName', 'relEcaEpmWorkerOid');

    // 도면/BOM 변경계획일
    //CalendarUtil.dateInputFormat('relEcaEpmPlanDate'); 
    CalendarUtil.dateInputFormat('relEcaEpmPlanDate', null, {minDate : new Date()});
    
	
	//if(newwin != null) newwin.window.close();
	if(isAdded) {
	    alert('<%=messageService.getString("e3ps.message.ket_message", "04045") %><%--\'선택\'하신 Part가 추가되었습니다.--%>');
	    newwin.window.focus();
	}
}


//연계일정검색 팝업
function popupRelPlan(tableId){
	 var oid = "e3ps.ecm.entity.KETMoldChangeOrder:";
	 if( document.forms[0].oid.value != '' )
	 {
	    oid = document.forms[0].oid.value;
	 }
	var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchMoldPlanPopupForm.jsp?oid="+oid;
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
	    form.newMoldChangePlanOid[pos].value = trArr[0];
	    form.scheduleId[cnt].value = trArr[2];
	    form.dieNo[pos].value = trArr[1];
	    //form.relEcaEpmPlanDate[pos].value = trArr[4];
	    form.relEcaEpmPlanDate[cnt].value = trArr[4];
	  }
	} else {
	  form.newMoldChangePlanOid.value = trArr[0];
	  form.scheduleId.value = trArr[2];
	  form.dieNo.value = trArr[1];
	  form.relEcaEpmPlanDate.value = trArr[4];
	}
}

//연계일정 초기화
function clearRelPlan(tableId){
	var form = document.forms[0];
	var targetTable = document.getElementById(tableId);
	var tableRows = targetTable.rows.length;
	var pos = eval(tableId).clickedRowIndex - 1;
	if(tableRows > 2){
	  form.newMoldChangePlanOid[pos].value = '';
	  form.scheduleId[pos].value = '';
	  form.dieNo[pos].value = '';
	} else {
	  form.newMoldChangePlanOid.value = '';
	  form.scheduleId.value = '';
	  form.dieNo.value = '';
	}
}



//표준품 라인 추가
function addRelDoc() {
	var arr = new Array();
	var idx = 0;
	rArr = new Array();
	rArr[0] = "";//oid
	rArr[1] = "";//code
	rArr[2] = "";//version
	arr[idx++] = rArr;
	insertRelDocLine(arr);
}

//표준품 라인추가
function insertRelDocLine(objArr) {
	if(objArr.length == 0) {
	  return;
	}
	var targetTable = document.getElementById("relDocTable");
	
	var trArr;
	var str = "";
	//if( targetTable.rows.length < 1)
	//{
	  for(var i = 0; i < objArr.length; i++) {
	    trArr = objArr[i];
	    var tableRows = targetTable.rows.length;
	    var newTr = targetTable.insertRow(tableRows);
	    newTr.onmouseover=function(){relDocTable.clickedRowIndex=this.rowIndex};
	    var currRow = targetTable.rows.length - 1;
	
	    //전체선택 checkbox
	    newTd = newTr.insertCell(newTr.cells.length);
	//    newTd.width = "40";
	    newTd.className = "tdwhiteM";
	    str = "";
	    str += "<input type='hidden' name='relEcaDocActivityType' value='3'>";
	    str += "<input type='hidden' name='relEcaDocLinkOid' value=''>";
	    str += "<input type='hidden' name='moldEcaDocOid' value=''>";

        <% /* ECN에 필요한 정보로 표준품리스트에서는 필요없으나 서버로직을 맞추기 위해서 Element로써 가지고 있게한다. */ %>
        str += "<input type='hidden' name='customName' value=''>";
        str += "<input type='hidden' name='completeRequestDate' value=''>";
        str += "<input type='hidden' name='activityTypeDesc' value=''>";
        str += "<input type='hidden' name='activityBackDesc' value=''>";
        
        
	    str += "<input type='checkbox' name='chkSelectRelDoc'>";
	    //newTd.innerHTML = str;
	    newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
	                    + "<div style=\"display:none;\">"+ str +"</div>"
	                    ; 
	
	    //구분
	    newTd = newTr.insertCell(newTr.cells.length);
	//    newTd.width = "50";
	    newTd.className = "tdwhiteM";
	    newTd.innerHTML = "표준품";
	
	    //문서번호
	    newTd = newTr.insertCell(newTr.cells.length);
	//    newTd.width = "140";
	    newTd.className = "tdwhiteM";
	    str = "";
	    str += "<input type='hidden' name='relEcaDocOid' value='" + trArr[0] + "'>";
	    str += "<input type='text' name='relEcaDocNo' class='txt_fieldCRO' readonly value='" + trArr[1] + "'>";
	    str += "&nbsp;<a href=\"javascript:popupRelDoc2('relDocTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
	    str += "&nbsp;<a href=\"javascript:clearRelDoc2('relDocTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
	    newTd.innerHTML = str;
	
	    //Rev
	    newTd = newTr.insertCell(newTr.cells.length);
	//    newTd.width = "50";
	    newTd.className = "tdwhiteM";
	    newTd.innerHTML = "<input type='text' name='relEcaDocPreRev' class='txt_fieldCRO' style='width:38px' readonly value='" + trArr[2] + "'>";
	
	    //담당자
	    newTd = newTr.insertCell(newTr.cells.length);
	//    newTd.width = "130";
	    newTd.className = "tdwhiteM";
	    str = "";
	    str += "<input type='hidden' name='relEcaDocWorkerOid'>";
	    str += "<input type='text' name='relEcaDocWorkerName' class='txt_field'>&nbsp;";
	    str += "<a href=\"javascript:popupUser2('relDocTable');\"><img src='/plm/portal/images/icon_user.gif' border='0'></a>&nbsp;";
	    str += "<a href=\"javascript:clearUser2('relDocTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
	    newTd.innerHTML = str;
	
	    //변경예정일
	    newTd = newTr.insertCell(newTr.cells.length);
	//    newTd.width = "130";
	    newTd.className = "tdwhiteM";
	    str = "";
	    str += "<input type='text' name='relEcaDocPlanDate' id='relEcaDocPlanDate"+ currRow +"' class='txt_field' >";
	    /* 
	    str += "<a href=\"#\" onclick=\"javascript:popupCal('relEcaDocPlanDate','relDocTable');\"><img src='/plm/portal/images/icon_6.png' border='0'></a>";
	    str += "<a href=\"javascript:clearCal('relEcaDocPlanDate','relDocTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
	    */
	    str += "<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"relEcaDocPlanDate\");' style='cursor: hand;'>";
	    
	    newTd.innerHTML = str;
	
	    //변경내용
	    newTd = newTr.insertCell(newTr.cells.length);
	//    newTd.width = "";
	    newTd.className = "tdwhiteL0";
	    str = "";
	    str += "<input type='text' name='relEcaDocActivityComment' class='txt_field' style='width:100%'>";
	    newTd.innerHTML = str;
	    
	    
	    // 표준품 담당자
	    SuggestUtil.bind('USER', 'relEcaDocWorkerName', 'relEcaDocWorkerOid');
        // 표준품 변경계획일
        //CalendarUtil.dateInputFormat('relEcaDocPlanDate'); 
        CalendarUtil.dateInputFormat('relEcaDocPlanDate', null, {minDate : new Date()});
        
	  }
	//}
	//else
	//{
	//  alert("표준품리스트는 1개만 등록할 수 있습니다.")
	//}
	//document.recalc();
}
var relDocPos = 0;
//표준품 검색 팝업 호출
function popupRelDoc2(tableId) {
	
	relDocPos = eval(tableId).clickedRowIndex;
	
	var url = "/plm/jsp/dms/SearchDocumentPop.jsp?method=checkDocAjax2&mode=one&authorStatus=APPROVED";
	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,tableId, 800, 540, opts);
	
	
}
	
function checkDocAjax2(objArr){
	var trArr = objArr[0];
	var url = "/plm/jsp/ecm/CheckDocAjaxAction.jsp?oid="+trArr[0]+"&no="+trArr[1]+"&name="+trArr[2]+"&ver="+trArr[3]+"&inx=" + relDocPos;
	callServer(url, checkDocResult2);
}

window.getTagText = function(xmlDoc){
    return xmlDoc.textContent || xmlDoc.text || '';
}

function checkDocResult2(req) {
	var xmlDoc = req.responseXML;
	//showElements = xmlDoc.selectNodes("//data_info");
	
	var l_flag = xmlDoc.getElementsByTagName("l_flag");
	var l_oid = xmlDoc.getElementsByTagName("l_oid");
	var l_no = xmlDoc.getElementsByTagName("l_no");
	var l_name = xmlDoc.getElementsByTagName("l_name");
	var l_ver = xmlDoc.getElementsByTagName("l_ver");
	var l_inx = xmlDoc.getElementsByTagName("l_inx");
	
	var flag = decodeURIComponent(getTagText(l_flag[0]));
	var oid = decodeURIComponent(getTagText(l_oid[0]));
	var no = decodeURIComponent(getTagText(l_no[0]));
	var name = decodeURIComponent(getTagText(l_name[0]));
	var ver = decodeURIComponent(getTagText(l_ver[0]));
	var inx = decodeURIComponent(getTagText(l_inx[0]));
	
	if ( flag == 'true'){
	  alert("<%=messageService.getString("e3ps.message.ket_message", "01862") %><%--설계변경이 진행중인 문서입니다--%>");
	  return;
	}
	
	 setRelDoc2(oid, no, name, ver, inx);
}

//표준품 검색 팝업 리턴 세팅
function setRelDoc2(oid, no, name, ver, inx) {
	//alert("setRelDoc("+ oid +","+ no +","+ name +","+ ver +","+ inx +")");
	if(oid.length == 0) {
	  return;
	}
	
	/* 
	var form = document.forms[0];
	
	var targetTable = document.getElementById("relDocTable");
	var tableRows = targetTable.rows.length;
	
	if(inx > 1){
	  form.relEcaDocOid[inx].value = oid;
	  form.relEcaDocNo[inx].value = no;
	  form.relEcaDocPreRev[inx].value = ver;
	} else {
	  form.relEcaDocOid.value = oid;
	  form.relEcaDocNo.value = no;
	  form.relEcaDocPreRev.value = ver;
	}
	*/
	
    $("#relDocTable").find("[name=relEcaDocOid]").each(function(i) {
        if(i == (inx - 1)) {
            $(this).val(oid);
            $("#relDocTable").find("[name=relEcaDocNo]").eq(i).val(no);
            $("#relDocTable").find("[name=relEcaDocPreRev]").eq(i).val(ver);
        }
        
    });
	
}

//문서 초기화
function clearRelDoc2(tableId){
	var form = document.forms[0];
	var targetTable = document.getElementById(tableId);
	var tableRows = targetTable.rows.length;
	var pos = eval(tableId).clickedRowIndex;
	
	/* 
	if(tableRows > 1){
	  form.relEcaDocOid[pos].value = '';
	  form.relEcaDocNo[pos].value = '';
	  form.relEcaDocPreRev[pos].value = '';
	} else {
	  form.relEcaDocOid.value = '';
	  form.relEcaDocNo.value = '';
	  form.relEcaDocPreRev.value = '';
	}
	*/

    $("#"+tableId).find("[name=relEcaDocOid]").each(function(i) {
        if(i == (pos - 1)) {
            $(this).val('');
            $("#relDocTable").find("[name=relEcaDocNo]").eq(i).val('');
            $("#relDocTable").find("[name=relEcaDocPreRev]").eq(i).val('');
        }
        
    });
    
}
var RelDocUserPos = 0;
//표준품 담당자검색 팝업
function popupUser2(tableId){
	RelDocUserPos = eval('relDocTable').clickedRowIndex;
	SearchUtil.selectOneUser('','','setRelDocUser2');
}

//표준품 담당자 검색 팝업 리턴 포맷
function setRelDocUser2(objArr) {
	if(objArr.length == 0) {
	  return;
	}
	var pos = RelDocUserPos;
	var trArr = objArr[0];
	
    $("#relDocTable").find("[name=relEcaDocWorkerOid]").each(function(i) {
        if(i == (pos - 1)) {
            $(this).val(trArr[0]);
            $("#relDocTable").find("[name=relEcaDocWorkerName]").eq(i).val(trArr[4]);
        }
        
    });
	
}

//담당자 초기화
function clearUser2(tableId){
	var pos = eval(tableId).clickedRowIndex;

    $("#relDocTable").find("[name=relEcaDocWorkerOid]").each(function(i) {
        if(i == (pos - 1)) {
            $(this).val('');
            $("#relDocTable").find("[name=relEcaDocWorkerName]").eq(i).val('');
        }
        
    });
    
}


var userRelEmpLinePos = 0;

//도면/BOM 담당자검색 팝업
function popupUser_RelEpmLine()
{
 var targetTable = document.getElementById('relEpmTable');
 var tableRows = targetTable.rows.length;
 userRelEmpLinePos = document.getElementById('relEpmTable').clickedRowIndex;
     
 /* var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
 var attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
 if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
 } */
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
	
	/* 
	var form = document.forms[0];

	inx -= 1;
	alert(form.relEcaEpmWorkerOid.length);
	if(typeof form.relEcaEpmWorkerOid.length == 'undefined') {
	    form.relEcaEpmWorkerOid.value = trArr[0];
	    form.relEcaEpmWorkerName.value = trArr[4];
	    
	} else {
	    form.relEcaEpmWorkerOid[inx].value = trArr[0];
	    form.relEcaEpmWorkerName[inx].value = trArr[4];
	    
	}
    */
    

    $("#relEpmTable").find("[name=relEcaEpmWorkerOid]").each(function(i) {
        if(i == (inx - 1)) {
            $(this).val(trArr[0]);
            $("#relEpmTable").find("[name=relEcaEpmWorkerName]").eq(i).val(trArr[4]);
        }
        
    });
    
}

//도면/BOM 담당자 초기화
function clearUser_RelEpmLine()
{
 /* 
 var form = document.forms[0];
 
 var targetTable = document.getElementById('relEpmTable');
 var tableRows = targetTable.rows.length;
 */
 
 var pos = eval('relEpmTable').clickedRowIndex;
 
 /* 
 pos -= 1;
 
 if(typeof form.relEcaEpmWorkerOid.length == 'undefined') {
     form.relEcaEpmWorkerOid.value = '';
     form.relEcaEpmWorkerName.value = '';
    
 } else {
     form.relEcaEpmWorkerOid[pos].value = '';
     form.relEcaEpmWorkerName[pos].value = '';
 }
 */
 
 $("#relEpmTable").find("[name=relEcaEpmWorkerOid]").each(function(i) {
     if(i == (pos - 1)) {
         $(this).val('');
         $("#relEpmTable").find("[name=relEcaEpmWorkerName]").eq(i).val('');
     }
     
 });
 
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
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelEpm();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "03245") %><%--활동추가--%></a></td>
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
      
      <!-- div id="div_scroll" style="height:150;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border" -->
      <div id="div_scroll" class="table_border">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relEpmTable">
        <!-- col width='20'><col width='50'><col width='*'><col width=''><col width='35'><col width='55'><col width='135'><col width='102'><col width='140' -->
        <tr class="">
          <td width="25" class="tdblueM"><a href="#" onclick="javascript:popupRelEpm2();"><img src="/plm/portal/images/b-plus.png"></a></td>
          <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01580") %><%--부품/도면번호--%></td>
          <td width="*" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01579") %><%--부품/도면명--%></td>
          <td width="35" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01504") %><%--변경계획일--%></td>
          <td width="100" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02125") %><%--연계일정--%></td>
          <!-- td width="*" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td -->
          
          <!-- td width="100" class="tdblueM">Die No</td>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td>
          <td width="115" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%><span class="red">*</span></td -->          
          
        </tr>
        <%
        boolean isPlanningStep = false;
        if( "PLANNING".equals(ketMoldChangeOrderVO.getProgressState()) || "REWORK".equals(ketMoldChangeOrderVO.getProgressState()) ) {
            isPlanningStep = true;
        }
  
        
        int docCnt = -1;
        if(ketMoldChangeOrderVO.getTotalCount() > 0) {
            ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();
            if(ketMoldECALinkVOList == null) {
                size = 0;
            } else {
                size = ketMoldECALinkVOList.size();
            }
            KETMoldECALinkVO ketMoldECALinkVO = null;
            String type = "";
            for(int i=0; i<size; i++) {
                ketMoldECALinkVO = (KETMoldECALinkVO)ketMoldECALinkVOList.get(i);
                type = ketMoldECALinkVO.getActivityType();
                if("3".equals(type) || "4".equals(type)) {//표준품이거나 ECN 이면 다음 자료 처리
                    continue;
                }
                docCnt++;
        %>
        <tr onMouseOver='relEpmTable.clickedRowIndex=this.rowIndex'>
          <td class='tdwhiteM'>
            <input type='hidden' name='relEcaEpmActivityType' value='<%=type%>'>
            <input type='hidden' name='moldEcaEpmOid' value="<%=ketMoldECALinkVO.getRelEcaOid()%>">
            <input type='hidden' name='relEcaEpmLinkOid' value="<%=ketMoldECALinkVO.getRelEcaObjectLinkOid()%>">
            <input type='hidden' name='relEcaEpmOid' value="<%=ketMoldECALinkVO.getRelEcaObjectOid()%>">
            <input type='hidden' name='beforePartOid' value="<%=ketMoldECALinkVO.getBeforePartOid()%>">
            <input type='hidden' name='relEcaEpmNo' value="<%=ketMoldECALinkVO.getRelEcaObjectNo()%>">
            <input type='hidden' name='relEcaEpmName' value="<%=ketMoldECALinkVO.getRelEcaObjectName()%>">
            <input type='hidden' name='relEcaEpmPreRev' value="<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>">
            <input type='hidden' name='relEcaEpmChangeYn' value="<%=ketMoldECALinkVO.getRelEcaEpmChangeYn()%>">
            <input type='hidden' name='relEcaEpmDocType' value="<%=ketMoldECALinkVO.getRelEcaEpmDocType()%>">
            <input type='hidden' name='relEcaEpmActivityComment' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>'>
            
            <!-- 예상비용 관련정보 -->
            <input type='hidden' name='relDieNo_eca' value=''>
            <input type='hidden' name='expectCost_eca'>
            <input type='hidden' name='budget_eca' value='확보'>
            <input type='hidden' name='secureBudgetYn_eca' value='Y'>
            
            <!-- 활동추가를 위한 정보 -->
            <input type="hidden" name="addedPartNumberAtEca" value='<%=ketMoldECALinkVO.getRelEcaObjectNo() %>'>
            
            <a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relEpmTable', 'chkSelectRelEpm', 'chkAllRelEpm', 'deleteRelEpmList');"><img src="/plm/portal/images/b-minus.png"></a>
            <div style="display:none;"><input type='checkbox' name='chkSelectRelEpm' value="<%if(!"".equals(ketMoldECALinkVO.getRelEcaObjectLinkOid())) {out.print(type + ":" + ketMoldECALinkVO.getRelEcaObjectLinkOid());}%>"></div>
          </td>
          <td class='tdwhiteM'><%=ketMoldECALinkVO.getActivityTypeName()%></td>
          <td class='tdwhiteM' title="<%=ketMoldECALinkVO.getRelEcaObjectNo()%>"><%=ketMoldECALinkVO.getRelEcaObjectNo()%></td>
          <td class='tdwhiteL' title="<%=ketMoldECALinkVO.getRelEcaObjectName()%>"><div class="ellipsis" style="width:340px;"><nobr><%=ketMoldECALinkVO.getRelEcaObjectName()%></nobr></div></td>
          <td class='tdwhiteM'><%=ketMoldECALinkVO.getRelEcaObjectPreRev()%></td>
          
          <%
          if(isPlanningStep) {
          %>
          <td class="tdwhiteM">
            <input type="hidden" name="relEcaEpmWorkerOid" value='<%=ketMoldECALinkVO.getWorkerId() %>'>
            <input type='text' name='relEcaEpmWorkerName' value='<%=ketMoldECALinkVO.getWorkerName() %>' class='txt_field' style='width:50px'>
            <a href='javascript:popupUser_RelEpmLine();'><img src='/plm/portal/images/icon_user.gif' border='0'></a>
            <a href='javascript:clearUser_RelEpmLine();'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
          </td>
          <%
          } else {
          %>
          <td class="tdwhiteM">
            <input type="hidden" name="relEcaEpmWorkerOid" value='<%=ketMoldECALinkVO.getWorkerId() %>'>
            <input type='hidden' name='relEcaEpmWorkerName' value='<%=ketMoldECALinkVO.getWorkerName() %>'>
            <%=ketMoldECALinkVO.getWorkerName() %>
          </td>
          <%
          }
          %>
          
          <td class='tdwhiteM'>
            <input type='text' name='relEcaEpmPlanDate' id='relEcaEpmPlanDate<%=docCnt %>' class='txt_field' style='width:70px;'
                   value='<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>'>
            <!-- a href="#" onclick="javascript:popupEpmCal('relEcaEpmPlanDate','relEpmTable');"><img src='/plm/portal/images/icon_6.png' border='0'></a>
            <a href="javascript:clearEpmCal('relEcaEpmPlanDate','relEpmTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a -->
            <img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue("relEcaEpmPlanDate");' style='cursor: hand;'>                   
          </td>
          
                <%
                if("1".equals(type)) {
                %>
                  <td class='tdwhiteM'><input type='hidden' name='dieNo' value='<%=ketMoldECALinkVO.getDieNo()%>'>
                    <input type='hidden' name='oldMoldChangePlanOid' value='<%=ketMoldECALinkVO.getMoldChangePlanOid()%>'>
                    <input type='hidden' name='newMoldChangePlanOid' value='<%=ketMoldECALinkVO.getMoldChangePlanOid()%>'>
                    <input type='text' name='scheduleId' class='txt_field' style='width:50px' readonly value="<%=ketMoldECALinkVO.getScheduleId()%>">
                    <a href="javascript:popupRelPlan('relEpmTable');"><img src='/plm/portal/images/icon_5.png' border='0'></a>
                    <a href="javascript:clearRelPlan('relEpmTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
                  </td>
                <%
                } else {
                %>
                  <td class='tdwhiteM'><input type='hidden' name='dieNo' value='<%=ketMoldECALinkVO.getDieNo()%>'>
                    <input type='hidden' name='oldMoldChangePlanOid' value='<%=ketMoldECALinkVO.getMoldChangePlanOid()%>'>
                    <input type='hidden' name='newMoldChangePlanOid' value='<%=ketMoldECALinkVO.getMoldChangePlanOid()%>'>
                    <input type='hidden' name='scheduleId' value="<%=ketMoldECALinkVO.getScheduleId()%>">-
                  </td>
                <%
                }
                %>
                
          <!-- >td class='tdwhiteM'>
            <input type='text' name='relEcaEpmActivityComment' class='txt_field'  style='width:98%' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>'>
          </td -->
          
          <%-- 
          <%
          // 도면일 경우
          if("1".equals(type))
          {
          %>
          Die No
          <td class="tdwhiteM" title="<%=ketMoldECALinkVO.getRelated_die_no() %>"><%=ketMoldECALinkVO.getRelated_die_no() %>
            <input type='hidden' name='relDieNo_eca' value='<%=ketMoldECALinkVO.getRelated_die_no() %>' >
          </td>
          <td class="tdwhiteM"><input type='hidden' name='expectCost_eca'  value='<%=ketMoldECALinkVO.getExpect_cost() %>'></td>
          <td class="tdwhiteL0">                  
            <table width='100%' border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align='middle'>
                  <input type='hidden' name='secureBudgetYn_eca' value='<%=ketMoldECALinkVO.getSecure_budget_yn() %>' >
                  <%
                  String budgetEca = (ketMoldECALinkVO.getSecure_budget_yn() == null || ((String) ketMoldECALinkVO.getSecure_budget_yn()).equalsIgnoreCase("N")) ? "미확보" : "확보"; 
                  %>
                  <input type='hidden' name='budget_eca' value='<%=budgetEca %>'>
                </td>  
                <td align='right' >
                  <div style="display:none"><a href="javascript:void(0)" name="btnBudgetCheck" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %>확인</a></div>
                </td>
              </tr>
            </table>
          </td>          
          <%
          }
          // 부품일 경우
          else
          {
          %>          
          Die No
          <td class="tdwhiteM" title="<%=ketMoldECALinkVO.getRelated_die_no() %>"><%=ketMoldECALinkVO.getRelated_die_no() %>
            <input type='hidden' name='relDieNo_eca' value='<%=ketMoldECALinkVO.getRelated_die_no() %>' >
          </td>
          <td class="tdwhiteM"><input type='text' name='expectCost_eca'  value='<%=ketMoldECALinkVO.getExpect_cost() %>' class='txt_fieldR' style='width:80px'></td>
          <td class="tdwhiteL0">                  
            <table width='100%' border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align='middle'>
                  <input type='hidden' name='secureBudgetYn_eca' value='<%=ketMoldECALinkVO.getSecure_budget_yn() %>' >
                  <%
                  String budgetEca = (ketMoldECALinkVO.getSecure_budget_yn() == null || ((String) ketMoldECALinkVO.getSecure_budget_yn()).equalsIgnoreCase("N")) ? "미확보" : "확보"; 
                  %>
                  <input type='text' name='budget_eca' value='<%=budgetEca %>' class='txt_field' style='width:35px' readonly>
                </td>  
                <td align='right' >
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:void(0)" name="btnBudgetCheck" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %>확인</a></td>
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
          --%>
          
        </tr>
        <%
            }   // for(int i=0; i<size; i++) {
        }
        %>
      </table>
      </div>

      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png">
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03017") %><%--표준품 List--%></td>
          <!-- td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:addRelDoc();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01401") %><%--문서 추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'relDocTable', 'chkSelectRelDoc', 'chkAllRelDoc', 'deleteRelDocList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01396") %><%--문서 삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td -->
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <div id="div_scroll4" class="table_border">
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="relDocTable">
        <col width='20'><col width='50'><col width='180'><col width='50'><col width='180'><col width='180'><col width='*'>
        <tr>
          <td class="tdblueM"><a href="#" onclick="javascript:addRelDoc();"><img src="/plm/portal/images/b-plus.png"></a></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01515") %><%--변경예정일--%></td>
          <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td>
        </tr>
      <!-- /table>
      <div style="height:100;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relDocTable">
        <col width='20'><col width='50'><col width='140'><col width='50'><col width='130'><col width='130'><col width='' -->
        
        <%
        //int docCnt = -1;
        docCnt = -1;
        if(ketMoldChangeOrderVO.getTotalCount() > 0) {
            ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();
            if(ketMoldECALinkVOList == null) {
                size = 0;
            } else {
                size = ketMoldECALinkVOList.size();
            }
            KETMoldECALinkVO ketMoldECALinkVO = null;
            for(int i=0; i<size; i++) {
                ketMoldECALinkVO = (KETMoldECALinkVO)ketMoldECALinkVOList.get(i);
                if(!"3".equals(ketMoldECALinkVO.getActivityType())) {
                    continue;
                }
                docCnt++;
        %>
                <tr onMouseOver='relDocTable.clickedRowIndex=this.rowIndex'>
                  <td class='tdwhiteM'>
                    
                    <div style="display:none;">
                      <input type='hidden' name='relEcaDocActivityType' value='3'>
                      <input type='hidden' name='relEcaDocLinkOid' value="<%=ketMoldECALinkVO.getRelEcaObjectLinkOid()%>">
                      <input type='hidden' name='moldEcaDocOid' value="<%=ketMoldECALinkVO.getRelEcaOid()%>">
		                    
		              <% /* ECN에 필요한 정보로 표준품리스트에서는 필요없으나 서버로직을 맞추기 위해서 Element로써 가지고 있게한다. */ %>
		              <input type='hidden' name='customName' value="">
		              <input type='hidden' name='completeRequestDate' value="">
		              <input type='hidden' name='activityTypeDesc' value="">
		              <input type='hidden' name='activityBackDesc' value="">
                    </div>
		            
                    <a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relDocTable', 'chkSelectRelDoc', 'chkAllRelDoc', 'deleteRelDocList');"><img src="/plm/portal/images/b-minus.png"></a>
                    <div style="display:none;"><input type='checkbox' name='chkSelectRelDoc' value='<%=ketMoldECALinkVO.getRelEcaOid() + "^" + ketMoldECALinkVO.getRelEcaObjectLinkOid()%>'></div>
                    
                  </td>
                  <td class='tdwhiteM'><%=messageService.getString("e3ps.message.ket_message", "03016") %><%--표준품--%></td>
                  <td class='tdwhiteM'>
                    <input type='hidden' name='relEcaDocOid' value='<%=ketMoldECALinkVO.getRelEcaObjectOid()%>'>
                    <input type='text' name='relEcaDocNo' class='txt_fieldC' style='width:90' readonly value='<%=ketMoldECALinkVO.getRelEcaObjectNo()%>'>
                    <a href="javascript:popupRelDoc2('relDocTable');"><img src='/plm/portal/images/icon_5.png' border='0'></a>
                    <a href="javascript:clearRelDoc2('relDocTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
                  </td>
                  <td class='tdwhiteM'>
                    <input type='text' name='relEcaDocPreRev' class='txt_fieldC' style='width:38px' readonly value='<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>'>
                  </td>
                  
                  <%
                  if(isPlanningStep) {
                  %>
                  <td class='tdwhiteM'>
                    <input type='hidden' name='relEcaDocWorkerOid' value='<%=ketMoldECALinkVO.getWorkerId()%>'>
                    <input type='text' name='relEcaDocWorkerName' class='txt_field' value="<%=ketMoldECALinkVO.getWorkerName()%>">
                    <a href="javascript:popupUser2('relDocTable');"><img src='/plm/portal/images/icon_user.gif' border='0'></a>
                    <a href="javascript:clearUser2('relDocTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
                  </td>
                  <%
                  } else {
                  %>
                  <td class='tdwhiteM'>
                    <input type='hidden' name='relEcaDocWorkerOid' value='<%=ketMoldECALinkVO.getWorkerId()%>'>
                    <input type='hidden' name='relEcaDocWorkerName' value="<%=ketMoldECALinkVO.getWorkerName()%>">
                    <%=ketMoldECALinkVO.getWorkerName()%>
                  </td>
                  <%
                  }
                  %>
                  
                  <td class='tdwhiteM'>
                    <input type='text' name='relEcaDocPlanDate' class='txt_field' value='<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>'>
                    <!-- a href="#" onclick="javascript:popupCal('relEcaDocPlanDate','relDocTable');"><img src='/plm/portal/images/icon_6.png' border='0'></a>
                    <a href="javascript:clearCal('relEcaDocPlanDate','relDocTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a -->
                    <img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue("relEcaDocPlanDate");' style='cursor: hand;'>
                  </td>
                  <td class='tdwhiteL0'>
                    <input type='text' name='relEcaDocActivityComment' class='txt_field'  style='width:100%' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>'>
                  </td>
                </tr>
        <%
            }   // for(int i=0; i<size; i++) {
        }
        %>
        
      </table>
      </div>
    
    </td>
  </tr>
</table>
