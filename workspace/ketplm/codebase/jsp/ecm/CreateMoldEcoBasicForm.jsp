<%
//프로젝트에서 산출물로 ECO 직접작성
String projectOid = request.getParameter("projectOid");
String projectOutputOid = request.getParameter("projectOutputOid");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
<!--
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

 .headerLock3 {
  position: relative;
  top:expression(document.getElementById("div_scroll3").scrollTop);
  z-index:99;
 }
-->
</style>
<script language="javascript">
$(document).ready(function(){
	      
    // 프로젝트에서 산출물로 ECO 직접작성
    var projectOid = "<%=projectOid%>";
    var projectOutputOid = "<%=projectOutputOid%>";
    if(projectOid != null && projectOid != 'null' && projectOid != '') {
        $('input[name=projectOid]').val(projectOid);
        $('input[name=projectOutputOid]').val(projectOutputOid);
        
        lfn_setInfosRelatedProject(projectOid);
    }
    
	
	  var strHTMLCode = document.forms[0].webEditor.value;
	  // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
	  // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
	  // 세번째 매개변수 => 이노디터 번호
	  fnSetEditorHTMLCode(strHTMLCode, false, 0);

	  var strHTMLCode1 = document.forms[0].webEditor1.value;
	  // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
	  // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
	  // 세번째 매개변수 => 이노디터 번호
	  fnSetEditorHTMLCode(strHTMLCode1, false, 1);
	  
	  
    SuggestUtil.bind('USER', 'ecoWorkerName', 'ecoWorkerId');
    SuggestUtil.bind('DIENO', 'projectNo', 'projectOid', 'projectName');
    
    $('[name=ecoWorkerId]').change( function() {  
    	$('[name=oldEcoWorkerId]').val($('[name=ecoWorkerId]').val());
    }); 
    
    
    // 초도배포가 선택되어 있을 경우
    // 초도를 제외한 모든 설계변경사유를 unchecked 처리 한다.
    $('input[name=chkChangeReason]').each(function(i) {
        if($(this).val() == 'REASON_10' && $(this).is(':checked')) {
            
            $('input[name=chkChangeReason]').each(function(j) {
                if($(this).val() != 'REASON_10') {
                    $(this).attr('disabled', 'disabled');
                } 
                else {
                	$(this).attr('disabled', 'disabled');
                }
            });
            
            // HEENEETODO : 초도배포시 반드시 제품ECO 필수
            //$('#spanRelEco').html($('#spanRelEco').html()+'<span class="red">*</span>');
            
            return true;
        } 
        
    });
    
    
});

//프로젝트 선택시 관련 정보를 물고와 정의된 엘레멘트에 set 한다.
$(document).on("change", "[name=projectOid]", function() {
	var oid = $(this).val();
	if(oid == null || oid == '') return;
	
	// ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
	lfn_setInfosRelatedProject(oid);
});

//프로젝트검색 팝업
function popupProject() {
	var form = document.forms[0];
	var devflag;
	//개발/양산구분
	if(form.rdoDevYn[0].checked) {
	  devflag = form.rdoDevYn[0].value;
	} else {
	  devflag = form.rdoDevYn[1].value;
	}
	//var url="/plm/jsp/project/SearchPjtPop.jsp?mode=one&dev_flag="+devflag+"&status=P&type=P";
	var url="/plm/jsp/project/SearchPjtPop.jsp?status=P&type=M";
	openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
}

function setProject(objArr) {
	if(objArr == null || objArr.length == 0) {
	  return;
	}
	
	/* 
	var trArr;
	var str = "";
	var form = document.forms[0];
	for(var i = 0; i < objArr.length; i++) {
	  trArr = objArr[i];
	  form.projectOid.value = trArr[0];
	  form.projectNo.value = trArr[1];
	  form.projectName.value= trArr[2];
	}
	//if(form.rdoDevYn[0].checked) {
	  searchProjectVendor();
	//}
	*/
	
    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++)
    {
        trArr = objArr[i];

        document.forms[0].projectOid.value= trArr[0];
        document.forms[0].projectNo.value = trArr[1];
        document.forms[0].projectName.value= trArr[2];

        // 생산처
        searchProjectVendor();
        
        // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
        lfn_setInfosRelatedProject(trArr[0]);
        
        
    }
    
}

//개발단계인 경우 관련 프로젝트가 선택되면 생산처를 조회한다.
function searchProjectVendor() {
	var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
	var form = document.forms[0];
	form.cmd.value = "searchProjectVendor";
	form.target = "download";
	form.action = url;
	form.method = "post";
	disabledAllBtn();
	showProcessing();
	form.submit();
}

//ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
function lfn_setInfosRelatedProject(oid) {
 if(oid == null || oid == '') return;
 
 showProcessing();
 $.ajax({
     type: 'get',
     url: '/plm/ext/project/program/getProject.do',
     data: {
         projectOid : oid
     },
     success: function (data) {
         
         $("input[name='projectOid']").val(data.projectOid);
         $("input[name='projectNo']").val(data.projectNo);
         $("input[name='projectName']").val(data.projectName);
         
         
         // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
         if(data.projectInfos4ECM != null) {
             
             try {
                 // 제품정보를 위한 선처리
                 var arrParts = null;
                 // 프로젝트 상태
                 var projectState = "";
                 
                 var projectInfos4ECM = data.projectInfos4ECM;
                 var arrPInfos = projectInfos4ECM.split("↕");
                 var arrPInfosLength = (arrPInfos != null) ? arrPInfos.length : 0;
                 for(var l=0; l < arrPInfosLength; l++) {
                     
                     var arrPartInfo13 = null;
                     
                     var arrP = arrPInfos[l].split("↔");
                     var arrPLength = (arrP != null) ? arrP.length : 0;
                
                     if(l == 0) { 
                    	 projectState = arrP[0];
                    	 
                     }
                     // 개발단계 = 단계구분
                     else if(l == 1 && arrPLength == 3) { 
                         var devYnOid = arrP[0];
                         var devYnName = arrP[1];
                         var devYnCode = arrP[2];
                         
                         /* 
                         Process
                         PC001 Proto 
                         PC002 Pilot 
                         PC003 T-CAR 
                         
                              개발/양산구분
                         PROTO PROTO 
                         PILOT PILOT 
                         TCAR T-CAR 
                         PRODUCTION 양산 
                         */
                         var checkDevYnCode = "";
                         if(devYnCode == 'PC001') {
                             checkDevYnCode = "D";
                         } else if(devYnCode == 'PC002') {
                             checkDevYnCode = "D";
                         } else if(devYnCode == 'PC003') {
                             checkDevYnCode = "D";
                         } else {
                        	 checkDevYnCode = "D";
                         } 
                        
                         if(projectState == 'COMPLETED') {
                             checkDevYnCode = "P";
                         }
                         $("input:radio[name='rdoDevYn']:radio[value='"+ checkDevYnCode +"']").prop('checked',true);
                     } 
                     // 대표 차종
                     else if(l == 2 && arrPLength == 3) {
                         var carTypeOid = arrP[0];
                         var carTypeName = arrP[1];
                         var carTypeCode = arrP[2];
                         
                     } 
                     // 제품정보 
                     else if(l >= 3 && arrPLength == 4) {
                         var relPartOid = arrP[0];
                         if(relPartOid != null && relPartOid != 'null' && relPartOid != '') {
                             var pNum = arrP[1];
                             var pName = arrP[2];
                             var relPartVersion = arrP[3];
                             
                             arrPartInfo13 = [relPartOid, pNum, pName, relPartVersion];
                             //alert('arrPartInfo13 is '+ arrPartInfo13);
                         }
                     }
                 
                     
                     if(arrPartInfo13 != null) {
                         if(arrParts == null) arrParts = new Array();
                         arrParts[arrParts.length] = arrPartInfo13;
                         //alert('arrParts['+ (arrParts.length - 1) +'] is '+ arrParts[ (arrParts.length - 1) ]);
                     }
                     
                 }
                 
                 // 제품정보를 위한 후처리
                 // 먼저 기존의 제품을 모두 삭제한다.
                 lfn_removeRelPartTable();
                 // 제품에 Part를 추가한다.
                 selectedPart(arrParts);
                 
                 
             } catch(e) { alert(e); }
             hideProcessing();
         }
                         
     },
     fail : function(){
         hideProcessing();
     }
             
 });
 hideProcessing();
 
}

function lfn_removeRelPartTable() {
    
    $("#relPartTable").find("[name=chkSelectRelPart]").each(function(i) {
        $(this).prop('checked',true);
        
        var chkSelectRelPartVal = $(this).val();
        if(chkSelectRelPartVal == '') {
            $(this).parent().parent().parent().remove();
        } else {
            deleteDataLine2('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');
        }
        
    });
    
}


//연계ECR 검색 팝업 호출
function popupRelEcr(targetId) {
	var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcrPopupForm.jsp";
    url += "&obj=prodMoldCls^2&obj=mode^multi";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'', 1000, 700, opts);
}

function setRelEcr(arrObj) {
    insertRelEcrLine(arrObj);
}
//연계ECR 라인추가
function insertRelEcrLine(objArr) {

	if(objArr.length == 0) {
	  hideProcessing();
	  return;
	}
	var targetTable = document.getElementById("relEcrTable");
	  var str = "";
	var trArr;
	for(var i = 0; i < objArr.length; i++) {
	  trArr = objArr[i];
	  if( !isDuplicateEcr2(trArr[0]) )
	  {
	    var tableRows = targetTable.rows.length;
	    var newTr = targetTable.insertRow(tableRows);
	    //전체선택 checkbox
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width='20';
	    //newTd.innerHTML = "<input name='chkSelectRelEcr' type='checkbox' value=''>";
	    newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
	                    + "<div style=\"display:none;\"><input name='chkSelectRelEcr' type='checkbox' value=''></div>"
	                    ; 
	                
	    //ECR번호
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width='110';
	    newTd.innerHTML = "<a href=\"javascript:viewRelEcr('" + trArr[0] + "');\">" + trArr[1] + "</a>";
	    newTd.innerHTML += "<input type='hidden' name='relEcrOid' value='" + trArr[0] + "'>";
	    newTd.innerHTML += "<input type='hidden' name='relEcrLinkOid' value=''>";
	
	    //ECR 제목
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.width='';
	    newTd.className = "tdwhiteL";
	    str ="";
	    str +="<font title=\""+trArr[5]+"\">";
	    str += "<div class='ellipsis' style='width:350px;'><nobr>";
	    str += trArr[5] +"</nobr></div></font>";
	    newTd.innerHTML = str;
	
	    //작성부서
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width='150';
	    newTd.innerHTML = "&nbsp;" + trArr[2] + "&nbsp;";
	
	    //작성자
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width='100';
	    newTd.innerHTML = "&nbsp;" + trArr[3] + "&nbsp;";
	
	    //승인일자
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width='100';
	    newTd.innerHTML = "&nbsp;" + trArr[4] + "&nbsp;";
	  }
	}
	
	hideProcessing();
}

function isDuplicateEcr2( ecr_id )
{
    var isDuplicate = false;
    
    var list = document.getElementsByName("relEcrOid");
    for( var cnt=0; cnt < list.length; cnt++ )
    {
      if( list[cnt].value == ecr_id )
        isDuplicate = true;
    }
    
    return isDuplicate;
}


//ECR 상세조회 팝업
function viewRelEcr(oid){
	var url = "/plm/servlet/e3ps/MoldEcrServlet?cmd=popupview&oid=" + oid;
	openWindow(url,"","1024","760","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

function selectModalDialog(attache){
	if(typeof attache == "undefined" || attache == null) {
        hideProcessing();
        return;
    }
    insertRelProdEcoLine(attache);
}
//연계ECO 검색 팝업 호출
function popupRelProdEco() {
	showProcessing();
	var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcoPopupForm.jsp";
	url += "&obj=prodMoldCls^1&obj=mode^multi";
	
    
	// 기본사항에 추가된 Die Nos
	// 처음 한번만 돌게 한다.
    url += "&obj=from^MoldEco";
    
    // 기본사항에 추가된 Die Nos
    var pNums = "";
    var relPartNumber = document.getElementsByName("relPartNumber");
    var relPartNumberLength = (relPartNumber != null) ? relPartNumber.length : 0;
    for(var i=0; i < relPartNumberLength; i++) {
        pNums += "&obj=pNums^"+  relPartNumber[i].value;
        
    }
    url += pNums;
    //alert(url);
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'', 740, 550, opts);
    
	
}

//연계ECO 라인추가
function insertRelProdEcoLine(objArr) {
	if(objArr.length == 0) {
		hideProcessing();
		return;
	}
	var targetTable = document.getElementById("RelProdEcoTable");
	
	var trArr;
	var str = "";
	for(var i = 0; i < objArr.length; i++) {
	  trArr = objArr[i];
	
	  if( !isDuplicateEco(trArr[0]) )
	  {
	    var tableRows = targetTable.rows.length;
	    var newTr = targetTable.insertRow(tableRows);
	
	    //전체선택 checkbox
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width = "20";
	    //newTd.innerHTML = "<input name='chkSelectRelProdEco' type='checkbox' value=''>";
	    newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
	                    + "<div style=\"display:none;\"><input name='chkSelectRelProdEco' type='checkbox' value=''></div>"
	                    ; 
	                
	    //ECO번호
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width = "110";
        newTd.innerHTML = "<a href=\"javascript:viewRelProdEco('" + trArr[0] + "');\">" + trArr[1] + "</a>";
	    newTd.innerHTML += "<input type='hidden' name='relProdEcoOid' value='" + trArr[0] + "'>";
	    newTd.innerHTML += "<input type='hidden' name='relProdEcoLinkOid' value=''>";
	
	    //ECO제목
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteL";
	    newTd.width = "";
        str ="";
	    str +="<font title=\""+trArr[6]+"\">";
	    str += "<div class='ellipsis' style='width:250px;'><nobr>";
	    str += trArr[6] +"</nobr></div></font>";
	    newTd.innerHTML = str;
	
	    //작성부서
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.width = "150";
        newTd.className = "tdwhiteM";
	    newTd.innerHTML = "&nbsp;" + trArr[2] + "&nbsp;";
	
	    //작성자
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width = "100";
        newTd.innerHTML = "&nbsp;" + trArr[3] + "&nbsp;";
	
	    //승인일자
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width = "100";
        newTd.innerHTML = "&nbsp;" + trArr[4] + "&nbsp;";
	
        // 비용확보
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width = "100";
        newTd.innerHTML = "&nbsp;" + trArr[5] + "&nbsp;";
	  }
	}
	
	hideProcessing();
}

function isDuplicateEco( oid )
{
	var isDuplicate = false;
	var oidList = document.getElementsByName("relProdEcoOid");
	
	for( var i=0; i < oidList.length; i++ )
	{
	  if( oid == oidList[i].value )
	  {
	    isDuplicate = true;
	  }
	}
	
	return isDuplicate;

}


//제품ECO 상세조회 팝업
function viewRelProdEco(oid){
	if(oid.indexOf('KETProdChangeOrder') > -1) {
		
		//var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + oid;
		var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=View&oid=" + oid;
	    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
	    
	} else {
		viewRelMoldEco(oid);
	}
}

//금형ECO 상세조회 팝업
function viewRelMoldEco(oid){
	if(oid.indexOf('KETMoldChangeOrder') > -1) {
        
	    var url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid=" + oid;
	    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
	    
	} else {
		viewRelProdEco(oid);		
	}
}

//연계부품 검색 팝업 호출
function popupRelPart() {
	//var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcmPartPopup.jsp";
	//attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=780px; dialogHeight:500px; center:yes");
	//if(typeof attache == "undefined" || attache == null) {
	//  return;
	//}
	//selectedPart(attache);
	
	var chk_chg_reason = document.getElementsByName("chkChangeReason");
	var str_chk_chg_reason = '';
	  for( var inx=0; inx < chk_chg_reason.length ; inx++)
	{
	  if( chk_chg_reason[inx].checked )
	  {
	    str_chk_chg_reason  += chk_chg_reason[inx].value;
	  }
	}
	if( str_chk_chg_reason != '' )
	{

	  showProcessing(); 
      SearchUtil.selectOnePart('selectedPart','pType=D');
      
	}
	else
	{
	  alert("<%=messageService.getString("e3ps.message.ket_message", "01855") %><%--설계변경사유를 선택하여 주십시오--%>");
	  chk_chg_reason[0].focus();
	  return;
	}
}

//연계부품 라인추가
function selectedPart(objArr) {
	if(objArr.length == 0) {
	  hideProcessing();
	  return;
	}
	var targetTable = document.getElementById("relPartTable");
	
	var reasonList = document.getElementsByName("chkChangeReason");
	
	var moldDwgChk = false;
	for( var cnt=0; cnt<reasonList.length; cnt++)
	{
	  if( reasonList[cnt].value == 'REASON_6' &&  reasonList[cnt].checked == true )
	  {
	    moldDwgChk = true;
	  }
	}
	
	var trArr;
	var str = "";
	for(var i = 0; i < objArr.length; i++) {
	  trArr = objArr[i];
	
	  if( !partDuplicateCheck2(trArr[0]) )
	  {
	    var tableRows = targetTable.rows.length;
	    var newTr = targetTable.insertRow(tableRows);
	    newTr.onmouseover=function(){relPartTable.clickedRowIndex=this.rowIndex};
	    newTr.height = 30;
	
	    //전체선택 checkbox
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
	    newTd.width = "20";
	    //newTd.innerHTML = "<input name='chkSelectRelPart' type='checkbox' value=''>";
	    newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
	                    + "<div style=\"display:none;\"><input name='chkSelectRelPart' type='checkbox' value=''></div>"
	                    ; 
	                    
	    //Die No
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
        newTd.width = "100";
	    //newTd.setAttribute("title", trArr[1]);
	    newTd.title = trArr[1];
	    str = "";
	    str += "<a href=\"javascript:viewRelPart('" + trArr[0] + "');\"><div class='ellipsis' style='width:100px;'><nobr>" + getTruncStr(trArr[1], 18) + "</nobr></div></a>";
	    str += "<input type='hidden' name='relPartOid' value='" + trArr[0] + "'>";
	    str += "<input type='hidden' name='relPartNumber' value='" + trArr[1] + "'>";
	    str += "<input type='hidden' name='relPartLinkOid' value=''>";
	    newTd.innerHTML = str;
	
	    //Part Name
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteL";
        newTd.width = "";
	    //newTd.setAttribute("title", trArr[2]);
	    newTd.title = trArr[2];
	    str = "";
	    str += "<div class='ellipsis' style='width:300px;'><nobr>";
	    str += trArr[2] +"</nobr></div>";
	    newTd.innerHTML = str;
	
	    //Part No
	    /* 
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
        newTd.width = "100";
	    //newTd.setAttribute("title", trArr[5]);
	    newTd.title = trArr[5];
	    str = "";
	    str += "<div class='ellipsis' style='width:100px;'><nobr>";
	    str += trArr[5] +"</nobr></div>";
	    newTd.innerHTML = str;
	    */
	    
	    //Rev
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
        newTd.width = "45";
	    newTd.innerHTML = ((typeof trArr[12] != 'undefined') ? trArr[12] : trArr[3]) + "&nbsp;"; // Display Revision
	
	    //예상비용(천원)
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteM";
        newTd.width = "100";
	    newTd.innerHTML = "<input type='text' name='expectCost' class='txt_fieldR' style='width:80px'>";
	
	    //비용확보여부
	    newTd = newTr.insertCell(newTr.cells.length);
	    newTd.className = "tdwhiteL0";
        newTd.width = "115";
	    str = "";
	
	    var partStr = trArr[1]+"";
	
	    if( partStr.charAt(1) == 'T' || moldDwgChk )
	    {
	      str += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
	      str += "  <tr>";
	      str += "    <td align='middle'><input type='hidden' name='budgetYnName' value='확보'><input type='hidden' name='secureBudgetYn' value='Y'></td>";
	      str += "    <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>";
	      str += "      <tr>";
	      str += "      <td width='30'>&nbsp;</td>";
	      //str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
	      //str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:checkBudget();' class='btn_blue'>확인</a></td>";
	      //str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
	      str += "      </tr>";
	      str += "    </table></td>";
	      str += "  </tr>";
	      str += "</table>";
	    }
	    else
	    {
	      str += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
	      str += "  <tr>";
	      str += "    <td align='middle'><input type='text' name='budgetYnName' value='미확보' class='txt_field' style='width:35px' readonly><input type='hidden' name='secureBudgetYn' value='N'></td>";
	      str += "    <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>";
	      str += "      <tr>";
	      str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
	      str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:checkBudget();' class='btn_blue'>확인</a></td>";
	      str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
	      str += "      </tr>";
	      str += "    </table></td>";
	      str += "  </tr>";
	      str += "</table>";
	    }
	
	
	    newTd.innerHTML = str;
	  }
	}
	
	hideProcessing();
}

function partDuplicateCheck2( oid )
{
    var isDuplicate = false;
    var oidList = document.getElementsByName("relPartOid");
    
    for( var i=0; i < oidList.length; i++ )
    {
      if( oid == oidList[i].value )
      {
        isDuplicate = true;
      }
    }
    
    return isDuplicate;

}

<% /* deprecated */ %>
function partDuplicateCheck(poid) {
    //부품추가시 선택된 부품정보를 중복체크한다.
    var tBody = document.getElementById("relPartTable");
    var rowsLen = tBody.rows.length;
    if(rowsLen > 0) {
      var primaryPart = document.forms[0].relPartOid;
      var partLen = primaryPart.length;
      if(partLen > 0 ) {
        for(var i = 0; i < partLen; i++) {
          if(primaryPart[i].value == poid) {
            return true;
          }
        }
      } else {
        if(primaryPart.value == poid) {
          return true;
        }
      }
    }
    return false;
}

//첨부파일 라인추가
function insertFileLine() {
	var targetTable = document.getElementById("filetable");
	var tableRows = targetTable.rows.length;
	var newTr = targetTable.insertRow(tableRows);
	
	//전체선택 checkbox
	newTd = newTr.insertCell(newTr.cells.length);
	newTd.width = "20";
	newTd.className = "tdwhiteM";
	//newTd.innerHTML = "<input name='addFileSelect' type='checkbox' value=''>";
	newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
	                + "<div style=\"display:none;\"><input name='addFileSelect' type='checkbox' value=''></div>"
	                ; 
	
	//첨부파일
	var filePath = "filePath" + tableRows;
	newTd = newTr.insertCell(newTr.cells.length);
	newTd.width = "";
	newTd.className = "tdwhiteL";
	newTd.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' size='100%'>";
}

</script>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <!-- col width="15%"><col width="35%"><col width="15%"><col width="35%" -->
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
    <td colspan="3" class="tdwhiteL0">
      <input type="text" name="ecoName" class="txt_field"  style="width:100%" id="textfield"
             value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoName()));}%>" >
    </td>
  </tr>
  <%
  String rdoDevYn[] = {"checked", ""};
  String rdoDivisionFlag[] = {"checked", ""};
  if(ketMoldChangeOrderVO.getTotalCount() > 0) {
      if("P".equals(ketMoldChangeOrderVO.getKetMoldChangeOrder().getDevYn())) {//양산
	      rdoDevYn[0] = "";
          rdoDevYn[1] = "checked";
      }
      if("E".equals(ketMoldChangeOrderVO.getKetMoldChangeOrder().getDivisionFlag())) {
	      rdoDivisionFlag[0] = "";
          rdoDivisionFlag[1] = "selected";
      }
  }
  String cmbVendorFlag[] = {"selected", ""};
  if(ketMoldChangeOrderVO.getTotalCount() > 0) {
      if("o".equals(ketMoldChangeOrderVO.getKetMoldChangeOrder().getVendorFlag())) {
          cmbVendorFlag[0] = "";
          cmbVendorFlag[1] = "selected";
      }
  }
  %>
  <tr>
    <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%><span class="red">*</span></td>
    <td class="tdwhiteL">
      <input name="rdoDevYn" type="radio" class="Checkbox"  id="rdoDevYn" value="D" <%=rdoDevYn[0]%>><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%>
      <input name="rdoDevYn" type="radio" class="Checkbox"  id="rdoDevYn" value="P" <%=rdoDevYn[1]%>><%=messageService.getString("e3ps.message.ket_message", "02094") %><%--양산단계--%>
    </td>
    <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%><span class="red">*</span></td>
    <td class="tdwhiteL0">
      <select name="divisionFlag" class="fm_jmp" style="width:100%"></select>
      <%
      Hashtable<String, String> divHash = new Hashtable<String, String>();
      String userGroupStr = KETObjectUtil.getCurrentUserGroup();
      String userGroupCode = "";

      for(int divCnt = 0 ; divCnt < divList.size(); divCnt++ )
      {
          divHash = divList.get(divCnt);
          if( divHash.get("name").equals(userGroupStr) )
          {
              userGroupCode = divHash.get("code");
      %>
      <script>addSelectBox( document.forms[0].divisionFlag, '<%=divHash.get("name")%>', '<%=divHash.get("code")%>', '<%=userGroupCode%>');</script>
      <%
          }
      }
      %>
    </td>
  </tr>
  <tr>
     <td class="tdblueL">Project No</td>
     <td class="tdwhiteL">
       <input type="hidden" name="projectOid" 
              value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getProjectOid()));}%>" >
       <input type="text" name="projectNo" class="txt_field" style="width:80%" 
              value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getProjectNo()));}%>" >
       <a href="javascript:popupProject();" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
       <a href="javascript:clearProject();" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
    </td>
    <td class="tdblueL">Project Name</td>
    <td class="tdwhiteL0">
      <input type="text" name="projectName" value="<%=StringUtil.checkNull(ketMoldChangeOrderVO.getProjectName())%>" class="txt_fieldRO" style="width:100%" readOnly>    
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02121") %><%--연계 ECR 정보--%></td>
    <td colspan="3" class="tdwhiteM0">
      <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
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
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelEcr();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'relEcrTable', 'chkSelectRelEcr', 'chkAllRelEcr', 'deleteRelEcrList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
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
      <div style="height:50;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border" -->
      <table width="100%" cellpadding="0" cellspacing="0" id="relEcrTable">
        <!-- <col width=40><col width=100><col width=168><col width=100><col width=90><col width=100>-->
        <tr>
          <td width="20" class="tdgrayM"><a href="javascript:popupRelEcr();"><img src="/plm/portal/images/b-plus.png"></a></td>
          <td width="110" class="tdgrayM">ECR No</td>
          <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00209") %><%--ECR 제목--%></td>
          <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
          <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
        </tr>
      <%
      if(ketMoldChangeOrderVO.getTotalCount() > 0) {
          ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoEcrLinkVOList = ketMoldChangeOrderVO.getKetMoldEcoEcrLinkVOList();
          if(ketMoldEcoEcrLinkVOList == null) {
              size = 0;
          } else {
              size = ketMoldEcoEcrLinkVOList.size();
          }
          KETMoldEcoEcrLinkVO ketMoldEcoEcrLinkVO = null;
          for ( int i = 0 ; i<size; i++ ) {
              ketMoldEcoEcrLinkVO = (KETMoldEcoEcrLinkVO)ketMoldEcoEcrLinkVOList.get(i);
      %>
        <tr>
          <td class="tdwhiteM">
            <a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relEcrTable', 'chkSelectRelEcr', 'chkAllRelEcr', 'deleteRelEcrList');"><img src="/plm/portal/images/b-minus.png"></a>
            <div style="display:none;"><input name="chkSelectRelEcr" type="checkbox" value="<%=ketMoldEcoEcrLinkVO.getRelEcrLinkOid()%>"></div>
          </td>
          <td class="tdwhiteM">
            <a href="javascript:viewRelEcr('<%=ketMoldEcoEcrLinkVO.getRelEcrOid()%>');" onfocus="this.blur();"><%=ketMoldEcoEcrLinkVO.getRelEcrId()%></a>
            <input type='hidden' name='relEcrOid' value='<%=ketMoldEcoEcrLinkVO.getRelEcrOid()%>'>
            <input type='hidden' name='relEcrLinkOid' value='<%=ketMoldEcoEcrLinkVO.getRelEcrLinkOid()%>'>
          </td>
          <td class="tdwhiteL" title="<%=ketMoldEcoEcrLinkVO.getRelEcrName()%>"><div class="ellipsis" style="width:300px;"><nobr><%=ketMoldEcoEcrLinkVO.getRelEcrName()%></nobr></div></td>
          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoEcrLinkVO.getCreateDeptName()%>&nbsp;</td>
          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoEcrLinkVO.getCreatorName()%>&nbsp;</td>
          <td class="tdwhiteM0">&nbsp;<%=ketMoldEcoEcrLinkVO.getApproveDate()%>&nbsp;</td>
        </tr>
      <%
          }
      }
      %>
      </table>
      <!-- /div>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table -->
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><div id="spanRelEco"><%=messageService.getString("e3ps.message.ket_message", "02122") %><%--연계 제품ECO 정보--%></div></td>
    <td colspan="3" class="tdwhiteM0">
      <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
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
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelProdEco();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'relProdEcoTable', 'chkSelectRelProdEco', 'chkAllRelProdEco', 'deleteRelProdEcoList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
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
      <div style="height:50;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border" -->
      <table width="100%" cellpadding="0" cellspacing="0" id="RelProdEcoTable">
        <!-- col width='39'><col width='100'><col width='136'><col width='98'><col width='74'><col width='79'><col width='75' -->
        <tr>
          <td width="20" class="tdgrayM"><a href="javascript:popupRelProdEco();"><img src="/plm/portal/images/b-plus.png"></a></td>
          <td width="110" class="tdgrayM">ECO No</td>
          <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
          <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
          <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
          <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%></td>
        </tr>
        <%
        if(ketMoldChangeOrderVO.getTotalCount() > 0) {
            ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoProdEcoLinkVOList = ketMoldChangeOrderVO.getKetMoldEcoProdEcoLinkVOList();
            if(ketMoldEcoProdEcoLinkVOList == null) {
                size = 0;
            } else {
                size = ketMoldEcoProdEcoLinkVOList.size();
            }
            KETMoldEcoEcrLinkVO ketMoldEcoProdEcoLinkVO = null;
            for ( int i = 0 ; i<size; i++ ) {
                ketMoldEcoProdEcoLinkVO = (KETMoldEcoEcrLinkVO)ketMoldEcoProdEcoLinkVOList.get(i);
        %>
        <tr>
          <td class="tdwhiteM">
            <a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'RelProdEcoTable', 'chkSelectRelProdEco', 'chkAllRelProdEco', 'deleteRelProdEcoList');"><img src="/plm/portal/images/b-minus.png"></a>
            <div style="display:none;"><input name="chkSelectRelProdEco" type="checkbox" value="<%=ketMoldEcoProdEcoLinkVO.getRelEcrLinkOid()%>"></div>
          </td>
          <td class="tdwhiteM">
            <a href="javascript:viewRelProdEco('<%=ketMoldEcoProdEcoLinkVO.getRelEcrOid()%>');"><%=ketMoldEcoProdEcoLinkVO.getRelEcrId()%></a>
            <input type='hidden' name='relProdEcoOid' value='<%=ketMoldEcoProdEcoLinkVO.getRelEcrOid()%>'>
            <input type='hidden' name='relProdEcoLinkOid' value='<%=ketMoldEcoProdEcoLinkVO.getRelEcrLinkOid()%>'>
          </td>
          <td class="tdwhiteL" title="<%=ketMoldEcoProdEcoLinkVO.getRelEcrName() %>"><div class="ellipsis" style="width:200px;"><nobr><%=ketMoldEcoProdEcoLinkVO.getRelEcrName()%></nobr></div></td>
          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoProdEcoLinkVO.getCreateDeptName()%>&nbsp;</td>
          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoProdEcoLinkVO.getCreatorName()%>&nbsp;</td>
          <td class="tdwhiteM">&nbsp;<%=ketMoldEcoProdEcoLinkVO.getApproveDate()%>&nbsp;</td>
          <td class="tdwhiteM0">&nbsp;<%=ketMoldEcoProdEcoLinkVO.getSecureBudgetYn()%>&nbsp;</td>
        </tr>
        <%
            }
        }
        %>
      </table>
      <!-- /div>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table -->
    </td>
  </tr>
  
  <script type="text/javascript">
  // 금형 ECO설계변경사유에서 고객요청 취소시 ECN 담당자 삭제 
    function lfn_setChkDetailValidation(obj){
    	if($(obj).val() == 'REASON_1'){
    		if(!$(obj).is(":checked")){
    			$("[name=relEcaDocWorkerName]").val("");
    			$("[name=relEcaDocWorkerOid]").val("");
    		}
    	}
    }
  
    function lfn_setChangeDetailReasonList(obj) {
        
        // 초도배포일 경우
        if($(obj).val() == 'REASON_10') {
            
            if($(obj).is(':checked')) {

                if(!confirm('초도배포 선택시 저장후 변경하실 수 없습니다. 계속 하시겠습니까?')) {
                    $(obj).attr('checked', false);
                    return false;
                }
                
                // 초도를 제외한 모든 설계변경사유를 unchecked 처리 한다.
                $('input[name=chkChangeReason]').each(function(i) {
                    if($(this).get(0) != $(obj).get(0)) {
                        $(this).attr('checked', false);
                        $(this).attr('disabled', 'disabled');
                        
                    }
                    
                });
                
                // 기타
                $('input[name=otherReasonDesc]').val('');
                
                
                //$('#spanRelEco').html($('#spanRelEco').html()+'<span class="red">*</span>');
                
                
                // 연계 제품ECO 정보 필수 추가
                var hasRelProdEco = 0;
                $("#RelProdEcoTable").find("input[name=relProdEcoOid]").each(function(k) {
                    hasRelProdEco++;
                    
                });
                if(hasRelProdEco == 0) {
                	
                	// HEENEETODO : 초도배포시 반드시 제품ECO 필수
                    //alert('<%=messageService.getString("e3ps.message.ket_message", "04023") %><%--초도배포일경우 연계 제품ECO 정보를 반드시 추가하십시오.--%>');
                    if(confirm('연계 제품ECO 정보를 추가하시겠습니까?')) {

                        $('#spanRelEco').html($('#spanRelEco').html()+'<span class="red">*</span>');
                        
                        popupRelProdEco();
                    }
                    
                }
                
                
            } else {

                // 초도를 제외한 모든 설계변경사유를 checked 처리 한다.
                $('input[name=chkChangeReason]').each(function(i) {
                    $(this).attr('disabled', false);
                    
                });
                
                
                $('#spanRelEco').html('<%=messageService.getString("e3ps.message.ket_message", "02122") %><%--연계 제품ECO 정보--%>');
                
            }
            
        } 
        
    }
    
    
  </script>  
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%><span class="red">*</span></td>
    <td colspan="3" class="tdwhiteL0">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <%
      int arrChangeReasonSize = (arrChangeReason != null) ? arrChangeReason.size() : 0;
      Hashtable<String, String> chgReason = null;
      for( int i=0; i<chgReasonList.size(); i++) {
          chgReason = chgReasonList.get(i);
          
          String checked = "";
          for(int j=0; j < arrChangeReasonSize; j++) {
              if(chgReason.get("code").equals(arrChangeReason.get(j))) {
        	      checked = "checked='checked'";
        	      break;
              }
          }
          
          if((i % 4) ==0) {
              out.println("<tr>");
          }
          if( (i/9) != 1 )
          {
              out.println("<td width='152'><input type='checkbox' name='chkChangeReason' value='" + chgReason.get("code") + "' " + checked + " onclick=\"javascript:lfn_setChangeDetailReasonList(this);lfn_setChkDetailValidation(this);\">"+chgReason.get("name")+ "</td>");
          }
          else
          {
              out.println("<td colspan='2'><input type='checkbox' name='chkChangeReason' value='" + chgReason.get("code") + "' " + checked + " onclick=\"javascript:setEtcValueStatus('otherReasonDesc', this.checked);\">"+chgReason.get("name")+"&nbsp;");
          }
          if( (i / 9) == 1 ){
              if( ketMoldChangeOrderVO.getTotalCount() > 0 )
              {
                  out.println("<input type='text' name='otherReasonDesc' class='txt_field' style='width:252' value='"+StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getOtherReasonDesc())+"'></td>");
              }
              else
              {
                  out.println("<input type='text' name='otherReasonDesc' class='txt_field' style='width:252' value=''></td>");
              }
          }
          if((i % 4) == 3 ) {
              out.println("</tr>");
          }
      }
      %>
      </table>
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01786") %><%--생산성향상유형--%></td>
    <td colspan="3" class="tdwhiteL0">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <%
      Hashtable<String, String> prodType = null;
      for( int i=0; i<incProdType.size(); i++) {
          prodType = incProdType.get(i);
          if((i % 4) ==0) {
              out.println("<tr>");
          }
          if( (i/11) != 1 )
          {
              out.println("<td width='152'><input type='checkbox' name='chkIncreaseProdType' value='" + prodType.get("code") + "' " + chkIncreaseProdType[i] + ">"+prodType.get("name")+"</td>");
          }
          else
          {
              out.println("<td width='152'><input type='checkbox' name='chkIncreaseProdType' value='" + prodType.get("code") + "' " + chkIncreaseProdType[i] + " onclick=\"javascript:setEtcValueStatus('otherIncreaseProdType', this.checked);\">"+prodType.get("name")+"&nbsp;");
          }
          if( (i / 11) == 1 ){
              if( ketMoldChangeOrderVO.getTotalCount() > 0 )
              {
                  out.println("<input type='text' name='otherIncreaseProdType' class='txt_field' style='width:100' value='"+StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getOtherIncreaseProdType())+"'></td>");
              }
              else
              {
                  out.println("<input type='text' name='otherIncreaseProdType' class='txt_field' style='width:100' value=''></td>");
              }
          }
          if((i % 4) == 3 ) {
              out.println("</tr>");
          }
      }
      %>
      </table>
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%><span class="red">*</span></td>
    <td colspan="3" class="tdwhiteM0">
      <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
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
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelPart();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
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
      <div id="div_scroll2" style="width:100%;height:81;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
      <table width="100%" cellpadding="0" cellspacing="0" id="relPartTable">
        <!-- col width=20><col width=85><col width=''><col width=90><col width=45><col width=100><col width=112 -->
        <tr class="">

                <td width="20" class="tdgrayM"><a href="javascript:popupRelPart();"><img src="/plm/portal/images/b-plus.png"></a></td>
                <td width="100" class="tdgrayM">Die No</td>
                <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <!-- td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td -->
                <td width="45" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td>
                <td width="112" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%></td>

        </tr>

        <%
        if(ketMoldChangeOrderVO.getTotalCount() > 0) {
            ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = ketMoldChangeOrderVO.getKetMoldECOPartLinkVOList();
            if( ketMoldECOPartLinkVOList != null )
            {
                size = ketMoldECOPartLinkVOList.size();
                KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
                for ( int i = 0 ; i<size; i++ ) {
                    ketMoldECOPartLinkVO = (KETMoldECOPartLinkVO)ketMoldECOPartLinkVOList.get(i);
        %>
        <tr height="30" onMouseOver='relPartTable.clickedRowIndex=this.rowIndex'>
          <td class="tdwhiteM">
            <a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');"><img src="/plm/portal/images/b-minus.png"></a>
            <div style="display:none;"><input name="chkSelectRelPart" type="checkbox" value="<%=ketMoldECOPartLinkVO.getRelPartLinkOid()%>"></div>
          </td>
          <td class="tdwhiteM" title="<%=ketMoldECOPartLinkVO.getRelPartNumber()%>">
            <a href="javascript:viewRelPart('<%=ketMoldECOPartLinkVO.getRelPartOid()%>');" onfocus="this.blur();"><div class="ellipsis" style="width:100px;"><nobr><%=ketMoldECOPartLinkVO.getRelPartNumber()%></nobr></div></a>
            <input type='hidden' name='relPartOid' value='<%=ketMoldECOPartLinkVO.getRelPartOid()%>'>
            <input type='hidden' name='relPartNumber' value='<%=ketMoldECOPartLinkVO.getRelPartNumber()%>'>
            <input type='hidden' name='relPartLinkOid' value='<%=ketMoldECOPartLinkVO.getRelPartLinkOid()%>'>
            <input type='hidden' name='secureBudgetYn' value='<%=ketMoldECOPartLinkVO.getSecureBudgetYn()%>'>
          </td>
          <td class="tdwhiteL" title="<%=ketMoldECOPartLinkVO.getRelPartName()%>"><div class="ellipsis" style="width:300px;"><nobr><%=ketMoldECOPartLinkVO.getRelPartName()%>&nbsp;</nobr></div></td>
          <!-- td class="tdwhiteM" title="<%=EcmSearchHelper.manager.getRelatedPartNo(ketMoldECOPartLinkVO.getRelPartNumber())%>"><div class="ellipsis" style="width:100px;"><nobr><%=EcmSearchHelper.manager.getRelatedPartNo(ketMoldECOPartLinkVO.getRelPartNumber())%>&nbsp;</nobr></div></td -->
          <td class="tdwhiteM"><%=ketMoldECOPartLinkVO.getRelPartRev()%>&nbsp;</td>
          <td class="tdwhiteM"><input type='text' name='expectCost' class='txt_fieldR' style='width:80px' value="<%=ketMoldECOPartLinkVO.getExpectCost()%>" onkeyup='javascript:SetNum(this)' style='ime-mode:disabled' onfocus='javascript:SetNumberFormat(this)' onblur='javascript:SetMoneyFormat(this)'></td>
          <td class="tdwhiteL0">
          
             <%if(ketMoldECOPartLinkVO.getRelPartNumber().charAt(1) == 'T' ){ %>
               <table width='100%' border='0' cellspacing='0' cellpadding='0'>
                 <tr>
                   <td align='middle'><input type='hidden' name='budgetYnName' value='<%=ketMoldECOPartLinkVO.getSecureBudgetYnName()%>' ></td>
                   <td width='1'></td>
                   <td align='right' width=''>
                     <table border='0' cellspacing='0' cellpadding='0'>
                       <tr>
                         <td width="30"></td>
                       </tr>
                     </table>
                   </td>
                 </tr>
               </table>
             <%}else{ %>
               <table width='100%' border='0' cellspacing='0' cellpadding='0'>
                 <tr>
                   <td align='middle'><input type='text' name='budgetYnName' value='<%=ketMoldECOPartLinkVO.getSecureBudgetYnName()%>' class='txt_field' style='width:35px' readonly></td>
                   <td width='1'></td>
                   <td align='right' width=''>
                     <table border='0' cellspacing='0' cellpadding='0'>
                       <tr>
                         <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>
                         <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:checkBudget();' class='btn_blue'><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                         <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>
                       </tr>
                     </table>
                   </td>
                 </tr>
               </table>
             <%} %>
          </td>
        </tr>
        <%
                }
            }
        }
        %>

      </table>
      <!-- /div>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table -->
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
    <td class="tdwhiteL">
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <select name="vendorFlag" class="Checkbox" style="width:50" style="text-align:center;" onChange="clearVendor();">
              <option value="i" <%=cmbVendorFlag[0]%>><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option>
              <option value="o" <%=cmbVendorFlag[1]%>><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option>
            </select>&nbsp;
          </td>
          <td>
            <input type="hidden" name="prodVendor"
                   value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getProdVendor()));}%>" >
            <input type="text" name="prodVendorName" class="txt_fieldRO" readonly
                   value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeOrderVO.getProdVendorName()));}%>" >
          </td>
          <td align="right">&nbsp;<a href="javascript:popupVendor();" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a href="javascript:clearVendor();" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
        </tr>
      </table>
    </td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00187") %><%--ECO 담당자--%><span class="red">*</span></td>
    <td class="tdwhiteL0">
		  <%
		  if( isCreate )
		  {
		  %>
		  <table border="0" cellspacing="0" cellpadding="0">
           <tr>
            <td>
		    <input type="hidden" name="oldEcoWorkerId" value='<%=loginUserOid%>'>
		    <input type="hidden" name="ecoWorkerId" value="<%=loginUserOid%>">
		    <input type="text" name="ecoWorkerName" class="txt_field" value='<%=KETObjectUtil.getLoginUserName() %>'>
		    </td>
            <td>
            &nbsp;<a href="javascript:popupEcoUser();" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>&nbsp;<a href="javascript:clearEcoUser();" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
		    </td>
		    </tr>
		  </table>
		  <%
		  }
		  else if(ketMoldChangeOrderVO.getTotalCount() > 0)
		  {
		  %>
		  <table border="0" cellspacing="0" cellpadding="0">
           <tr>
            <td>
		    <input type="hidden" name="oldEcoWorkerId" value='<%=StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoWorkerId())%>'>
		    <input type="hidden" name="ecoWorkerId" value="<%=StringUtil.checkNull(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoWorkerId())%>">
		    <input type="text" name="ecoWorkerName" class="txt_field" value='<%=StringUtil.checkNull(ketMoldChangeOrderVO.getEcoWorkerName())%>'>
		    </td>
		    <td>
		    &nbsp;<a href="javascript:popupEcoUser();" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>&nbsp;<a href="javascript:clearEcoUser();" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
		    </td>
            </tr>
          </table>
		  <%
		  }
		  %>  
    </td>
  </tr>
  <tr>
    <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "05153") %><%--변경 전--%></td>
    <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "05154") %><%--변경 후--%></td>
  </tr>    
  <tr>
    
    <!-- 이노디터 JS Include Start -->
    <script type="text/javascript">
        //<![CDATA[
    
        // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
        // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
        // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
        var g_arrSetEditorArea = new Array();
        g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
        g_arrSetEditorArea[1] = "EDITOR_AREA_CONTAINER1";// 이노디터를 위치시킬 영역의 ID값 설정
    
        //]]>
    </script>
    <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
    <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui_half.js"></script>
    <script type="text/javascript">
        //<![CDATA[
    
        // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
    
        // Skin 재정의
        //g_nSkinNumber = 0;
    
        // 크기, 높이 재정의
        g_nEditorWidth = 460;
        g_nEditorHeight = 200;
    
        //g_bCustomEditorWidthPercentageYN = true;
    
    //]]>
    </script>
    <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
    
    <script language=javascript>
    <% /* @deprecated */ %>
    /* 
    function fnCustomerFunction_1(EditNumber)
    {
        //alert("사용자 정의 툴바버튼 1");
        var object = document.getElementById("EDITOR_AREA_CONTAINER"+ ((EditNumber == 0) ? "" : EditNumber));
        object.style.position = "absolute";
        object.style.top = "100px";
        object.style.left = "60px";
        object.style.zIndex = 9999;
        //object.style.width = 1000;
        //object.style.height = 660;
        
        fnResizeEditor(EditNumber, 900, 600);
        
    }
    */
     
    function fnCustomerFunction_2(EditNumber)
    {
        //alert("사용자 정의 툴바버튼 2");
        fnResizeEditor(0, 460, 600);
        fnResizeEditor(1, 460, 600);
    }
    
    function fnCustomerFunction_3(EditNumber)
    {
        //alert("사용자 정의 툴바버튼 3");
        /* 
        var object = document.getElementById("EDITOR_AREA_CONTAINER"+ ((EditNumber == 0) ? "" : EditNumber));
        object.style.position = "";
        */
         
        fnResizeEditor(0, 460, 200);
        fnResizeEditor(1, 460, 200);
    }
    </script>
    
    <!-- 이노디터 JS Include End -->
        
    <td colspan="2" class="tdwhiteL">
    
    
                  <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                  <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor %></textarea> 
                  <textarea name="webEditorText" rows="0" cols="0" style="display: none"><%=webEditorText %></textarea> 
                  <!-- Editor Area Container : Start -->
                  <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                  <div id="EDITOR_AREA_CONTAINER"></div> 
                  <!-- Editor Area Container : End -->
                  
                  
    </td>
    <td colspan="2" class="tdwhiteL0">
    
    
                  <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                  <textarea name="webEditor1" rows="0" cols="0" style="display: none"><%=webEditor1 %></textarea> 
                  <textarea name="webEditorText1" rows="0" cols="0" style="display: none"><%=webEditorText1 %></textarea> 
                  <!-- Editor Area Container : Start -->
                  <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                  <div id="EDITOR_AREA_CONTAINER1"></div> 
                  <!-- Editor Area Container : End -->
                  
                  
    </td>            
  </tr>    
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
    <td colspan="3" class="tdwhiteL0">
      <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="5">&nbsp;</td>
          <td align="right">
            <table width="100%" cellpadding="0" cellspacing="0">
              <tr>
                <td>&nbsp;</td>
                <td align="right">
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:insertFileLine();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="5">&nbsp;</td>
                      <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'filetable', 'addFileSelect', 'chkFileAll', 'deleteFileList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
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
            <div style="height:48;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
            <table width="100%" cellpadding="0" cellspacing="0" id="filetable">
              <tr>
                <td width="40" class="tdgrayM"><a href="javascript:insertFileLine();"><img src="/plm/portal/images/b-plus.png"></a></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
              </tr>
              
              <%
			  Vector moldEcoAttacheVec = new Vector();
			  //moldEcoAttacheVec = ContentUtil.getSecondaryContents(ketMoldChangeOrderVO.getKetMoldChangeOrder());
			  if ( holder != null ) {
			      moldEcoAttacheVec = ContentUtil.getSecondaryContents(holder);
			      size = moldEcoAttacheVec.size();
			
			      ContentInfo cntInfo = null;
			      ContentItem ctf = null;
			      String appDataOid = "";
			      String url = "";
			
			      for ( int j = 0 ; j<size; j++ ) {
			          cntInfo = (ContentInfo)moldEcoAttacheVec.elementAt(j);
			          ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
			          appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
			
			          //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                      url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
			          url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
			  %>
			  <tr>
			    <td width="20" class="tdwhiteM">
                  <a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'filetable', 'addFileSelect', 'chkFileAll', 'deleteFileList');"><img src="/plm/portal/images/b-minus.png"></a>
                  <div style="display:none;"><input type="checkbox" name="addFileSelect" value="<%=cntInfo.getContentOid()%>"></div>
			    </td>
			    <td class="tdwhiteL0"><%=url%></td>
			  </tr>
			  <%
			      }
			  }
			  %>
			  
            </table>
            <!-- /div>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table -->
    </td>
  </tr>
</table>
