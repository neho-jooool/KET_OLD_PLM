<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.part.WTPart 
               ,ext.ket.dqm.entity.*"%>
<%@page import="ext.ket.part.util.*"%>               
<%@page import="ext.ket.part.base.service.PartBaseHelper"%>      
                            
<%
//DQM 에서 ECR 작성시 값 세팅
String dqmOid = request.getParameter("dqmOid");
String dqmProblemNo = "";
String dqmProblem = "";
String dqmCreatorName = "";
String dqmCreateStamp = "";
String dqmCartypeCode = "";
String dqmCartypeName = "";
String dqmPjtName = "";
String dqmPjtNo = "";
String dqmPjtOid = "";
String dqmOccurStepCode = "";
String dqmPartNumber = "";
String dqmPartName = "";
String dqmPartRev = "";
String dqmPartOid = "";
if(!StringUtil.checkNull(dqmOid).equals("")){
    KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(dqmOid);
    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
    dqmProblemNo = ketDqmHeader.getProblemNo();
    dqmProblem = ketDqmHeader.getProblem();
    WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
    PeopleData peopleData = new PeopleData(createUser);
    dqmCreatorName = peopleData.name;
    dqmCreateStamp = DateUtil.getDateString(ketDqmRaise.getCreateTimestamp(), "date");
    dqmCartypeCode = ketDqmRaise.getCartypeCode();
    dqmCartypeName = ketDqmRaise.getCartypeName();
    dqmPjtName = ketDqmRaise.getProductProject().getPjtName();
    dqmPjtNo = ketDqmRaise.getProductProject().getPjtNo();
    dqmPjtOid = CommonUtil.getOIDString(ketDqmRaise.getProductProject());
    dqmOccurStepCode = ketDqmRaise.getOccurStepCode();
    
    WTPart wtpart = ketDqmRaise.getPart();
    dqmPartNumber = wtpart.getNumber();
    dqmPartName = wtpart.getName();
    dqmPartRev = wtpart.getVersionInfo().getIdentifier().getValue();
    
    // Revision Display
    dqmPartRev = StringUtils.stripToEmpty(PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpPartRevision));
    
    dqmPartOid = CommonUtil.getOIDString(wtpart);
}
//DQM 에서 ECR 작성시 값 세팅 끝  


// 프로젝트에서 산출물로 ECO 직접작성
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

<script type="text/javascript">
<!--
function initDqmAdd(oid, problemNo, problem, creatorName, createStamp){
     var targetTable = document.getElementById("relDqmTable");
     
     var tableRows = targetTable.rows.length;
     var newTr = targetTable.insertRow(tableRows);

     //전체선택 checkbox
     newTd = newTr.insertCell(newTr.cells.length);
     newTd.width = "20";
     newTd.className = "tdwhiteM";
     newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                     + "<div style=\"display:none;\"><input name='chkSelectRelDqm' type='checkbox' value=''></div>"
                     ;

     //문제점
     newTd = newTr.insertCell(newTr.cells.length);
     newTd.width = "";
     newTd.className = "tdwhiteL";
     newTd.setAttribute("title", problemNo);
     str = "<div class='ellipsis' style='width:510px;'><nobr>";
     str += "<a href=\"javascript:viewRelDqm('" + oid + "');\">" + problem + "</a>";
     str += "<input type='hidden' name='relDqmOid' value='" + oid + "'>";
     str += "<input type='hidden' name='relDqmLinkOid' value=''></nobr></div>";
     newTd.innerHTML = str;

     //작성자
     newTd = newTr.insertCell(newTr.cells.length);
     newTd.width = "140";
     newTd.className = "tdwhiteM";
     newTd.innerHTML = creatorName;

     //작성일자
     newTd = newTr.insertCell(newTr.cells.length);
     newTd.width = "115";
     newTd.className = "tdwhiteM";
     newTd.innerHTML = createStamp;
}

function initPartAdd(oid, partNumber, partName, ver){
	/*
    var targetTable = document.getElementById("relPartTable");
    var tableRows = targetTable.rows.length; 
    var newTr = targetTable.insertRow(tableRows);
    newTr.height="27";

    //전체선택 checkbox
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "20";
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<div style=\"display:none;\"><input type='text' name='req_comment' value=''></div>"
    
                    + "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                    + "<div style=\"display:none;\"><input name='chkSelectRelPart' type='checkbox' value=''></div>"
                    ;

    //Part No
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "120";
    newTd.className = "tdwhiteM";
    str = "";
    str += "<a href=\"javascript:viewRelPart('" + oid + "');\">" + partNumber + "</a>";
    str += "<input type='hidden' name='relPartOid' value='" + oid + "'>";
    str += "<input type='hidden' name='relPartLinkOid' value=''>";
    newTd.innerHTML = str;

    //Part Name
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "";
    newTd.className = "tdwhiteL";
    str = "";
    str += "<font title=\""+partName+"\"><div class='ellipsis' style='width:600px;'><nobr>"+partName+"</nobr></div></font>";
    newTd.innerHTML = str;

    //Rev
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "50";
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = ver + "&nbsp;";
    */
    var targetTable = document.getElementById("relPartTable");

    var str = "";
    if( !partDuplicateCheck2(oid))
    {
        var tableRows = targetTable.rows.length;
        var newTr = targetTable.insertRow(tableRows);
        newTr.onmouseover=function(){relPartTable.clickedRowIndex=this.rowIndex};

        //전체선택 checkbox
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "20";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                        + "<div style=\"display:none;\"><input name='chkSelectRelPart' type='checkbox' value=''></div>"
                        
                        <!-- 아래는 사용하지 않으나 기존 서버로직에서 사용하고 있으므로 넘겨주어야 Null Exception 이 발생하지 않는다. -->          
                        + "<input type='hidden' name='relDieNo' value=''>"
                        + "<input type='hidden' name='secureBudgetYn' value='Y' >"
                        + "<input type='hidden' name='budget' value='확보' >"
                                                                
                        ;

        //Part No
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "200";
        newTd.title = partNumber;
        newTd.className = "tdwhiteM";
        str = "";
        str += "<a href=\"javascript:viewRelPart('" + oid + "');\">" + partNumber + "</a>";
        str += "<input type='hidden' name='relPartOid' value='" + oid + "'>";
        str += "<input type='hidden' name='relPartLinkOid' value=''>";
        str += "<input type='hidden' name='relPartNumber' value='" + partNumber + "'>";
        newTd.innerHTML = str;

        //Part Name
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "";
        newTd.title = partName;
        newTd.className = "tdwhiteL";
        newTd.Title= partName;
        str = "";
        str +="<font title=\""+partName+"\">";
        str += "<div class='ellipsis' style='width:300px;'><nobr>";
        str += partName +"</nobr></div></font>";
        newTd.innerHTML = str;
        //Rev
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "45";
        newTd.className = "tdwhiteM0";
        newTd.innerHTML = ver;
    }
}

function SetNum(obj){
	//방향키 인경우는 제외 처리(글쓰다가 앞으로 다시 안가지는 경우때문)
	if (event.keyCode >= 37 && event.keyCode <= 40) return;

	val=obj.value;
	re=/[^0-9.]/gi;   //가능 문자에 . 추가
	val = val.replace(re,"");
	  

	//최초 . 사용하지 못하게 처리함.
	if( val.indexOf(".") == 0){
		val = "";
	}

	// 아래내용은 소수점이 2개이상인지 확인 후 재귀호출해서 여러번 kye up 없이 누르더라도

	// 소숫점이 하나만 남도록 처리

	v_cnt = val.indexOf(".",val.indexOf(".")+1);
	  
	if( v_cnt > -1 ){
		val = val.substring(0,v_cnt) + val.substring(v_cnt + 1,val.length);
	    obj.value = val;
	    SetNum(obj);
	}
	
	obj.value = val;
}


$(document).ready(function(){
	//DQM 에서 ECR 작성시 값 세팅
    var dqmOid = "<%=dqmOid%>";

    if(dqmOid != "" && dqmOid != "null"){
        initDqmAdd(dqmOid, "<%=dqmProblemNo%>", "<%=dqmProblem%>", "<%=dqmCreatorName%>", "<%=dqmCreateStamp%>");
        initPartAdd("<%=dqmPartOid%>", "<%=dqmPartNumber%>", "<%=dqmPartName%>", "<%=dqmPartRev%>");
        if("<%=dqmCartypeName%>" != "" && "<%=dqmCartypeName%>" != "null"){
        $('[name=carTypeName]').val("<%=dqmCartypeName%>");
        $('[name=carTypeCode]').val("<%=dqmCartypeCode%>");
        }
        $('[name=project_name]').val("<%=dqmPjtName%>");
        $('[name=project_id]').val("<%=dqmPjtNo%>");
        $('[name=project_oid]').val("<%=dqmPjtOid%>");
        $('[name=eco_name]').val("<%=dqmProblem%>");
        
        lfn_setInfosRelatedProject("<%=dqmPjtOid%>");
        
        var devYnCode  = "<%=dqmOccurStepCode%>";
        $("input:radio[name='dev_yn']:radio[value='"+ devYnCode +"']").prop('checked',true);
        
        
        $('input[name=chk_chg_reason]:input[value=REASON_3]').attr('checked', true);
        
        var prodEcoReason = "chgRsTable^REASON_3";
        var chgRsTable = document.getElementById(prodEcoReason);
        chgRsTable.style.display = "block";
            
    }
    //DQM 에서 ECR 작성시 값 세팅 끝
	
    
    // 프로젝트에서 산출물로 ECO 직접작성
    var projectOutputOid = "<%=projectOutputOid%>";
    if(projectOutputOid != null && projectOutputOid != 'null' && projectOutputOid != '') {
    	$('input[name=projectOutputOid]').val(projectOutputOid);
    	
    	lfn_setInfosRelatedProject('<%=projectOid%>');
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
    	
    SuggestUtil.bind('CARTYPE', 'carTypeName', 'carTypeCode');
    SuggestUtil.bind('PRODUCTPROJNO', 'project_id', 'project_oid', 'project_name');
    
    
    $(document).click(function(e) { 
    	var isInfoArea = $(e.target).hasClass("detailInfo") || $(e.target).parents("#detailInfo").length > 0;
    	
    	if(!isInfoArea){
    		$("#detailInfo").hide();
    	}
   	}); 
    
});

//프로젝트 선택시 관련 정보를 물고와 정의된 엘레멘트에 set 한다.
$(document).on("change", "[name=project_oid]", function() {
 var oid = $(this).val();
 if(oid == null || oid == '') return;
 
 // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
 lfn_setInfosRelatedProject(oid);
});

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
         
         $("input[name='project_oid']").val(data.projectOid);
         $("input[name='project_id']").val(data.projectNo);
         $("input[name='project_name']").val(data.projectName);
         
         // 고객사
         if(data.maker != "")  // if( trArr[7] != "" &&  ('<%=userGroupStr%>' == '자동차사업부' || '<%=userGroupStr%>' == 'KETS') )
         {
             document.forms[0].domestic_yn.value = data.domain;

             searchCarData('maker', data.domain, data.maker);
             searchCarData('car', data.maker, data.category);
         }
         
         
         // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
         if(data.projectInfos4ECM != null) {
             
             try {
            	 var isNotFert = false;
            	 
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
                             checkDevYnCode = "PROTO";
                         } else if(devYnCode == 'PC002') {
                             checkDevYnCode = "PILOT";
                         } else if(devYnCode == 'PC003') {
                             checkDevYnCode = "TCAR";
                         } else {
                             
                         }
                         
                         if(projectState == 'COMPLETED') {
                             checkDevYnCode = "PRODUCTION";
                         }
                         $("input:radio[name='dev_yn']:radio[value='"+ checkDevYnCode +"']").prop('checked',true);
                     } 
                     // 대표 차종
                     else if(l == 2 && arrPLength == 3) {
                         var carTypeOid = arrP[0];
                         var carTypeName = arrP[1];
                         var carTypeCode = arrP[2];
                         
                         $("input[name='carTypeName']").val(carTypeName);
                         $("input[name='carTypeCode']").val(carTypeOid);
                     } 
                     // 제품정보 
                     else if(l >= 3 && arrPLength == 4) {
                         var relPartOid = arrP[0];
                         var pNum = arrP[1];
                         var hPosition = pNum.lastIndexOf('H');
                         if(hPosition == 0) {
                        	 isNotFert = true;
                         }
                         if(relPartOid != null && relPartOid != 'null' && relPartOid != '' && hPosition != 0) {
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
                 
                 if(isNotFert) {
                	 alert('<%=messageService.getString("e3ps.message.ket_message", "04100") %><%--제품이 아닌 Part는 제품 추가에서 제외됩니다.--%>');
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

function ecnCheckByReason(){
	$('input[name=chk_chg_reason]').each(function(i) {
	    if ($(this).is(":checked")) {
	        lfn_setEcnDisabledCheck($(this));
	    }
	});
}


function lfn_setEcnDisabledCheck(obj){
    var chgDetailReason = $(obj).val();
    
    $("[name=customName]").each(function(index){
        var value = $(this).attr("value");  
        if(chgDetailReason == "REASON_1"){  
            if($(obj).is(":checked")){  
               if(value == "설변적용시점") {
                   $("[name=chkPostAct]").eq(index).prop("checked", true);
                   $("[name=chkPostAct]").eq(index).attr("disabled", true);
               }
            }
        }else if(chgDetailReason == "REASON_3"){
            if($(obj).is(":checked")){  
               if(value == "설변적용시점") {
                   $("[name=chkPostAct]").eq(index).prop("checked", true);
                   $("[name=chkPostAct]").eq(index).attr("disabled", true);
               }
               if(value == "유효성검증") {
                   $("[name=chkPostAct]").eq(index).prop("checked", true);
                   $("[name=chkPostAct]").eq(index).attr("disabled", true);
               }
            }
        }else if(chgDetailReason == "REASON_7"){
            if($(obj).is(":checked")){  
                if(value == "MBOM활동") {
                    $("[name=chkPostAct]").eq(index).prop("checked", true);
                    $("[name=chkPostAct]").eq(index).attr("disabled", true);
                }
             }
        }else if(chgDetailReason == "REASON_9"){
            if($(obj).is(":checked")){  
               if(value == "유효성검증") {
                   $("[name=chkPostAct]").eq(index).prop("checked", true);
                   $("[name=chkPostAct]").eq(index).attr("disabled", true);
               }
            }
        }else if(chgDetailReason == "REASON_11"){
            if($(obj).is(":checked")){  
               if(value == "원가산출") {
                   $("[name=chkPostAct]").eq(index).prop("checked", true);
                   $("[name=chkPostAct]").eq(index).attr("disabled", true);
               }
            }
        }
    });
}

function lfn_removeRelPartTable() {
	
	$("#relPartTable").find("[name=chkSelectRelPart]").each(function(i) {
        $(this).prop('checked',true);
        
        var chkSelectRelPartVal = $(this).val();
        if(chkSelectRelPartVal == '') {
        	$(this).parent().parent().parent().remove();
        } else {
        	deleteDataLineMinus1('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');
            
        }
        
    });
	
}

// 비용확보 2015.09.17 주석처리함
/* $(document).on("click", "[name=btnBudgetCheck]", function() {
    var $OBJ = $(this);
    
    var pos = -1;
    $("#relPartTable").find("[name=btnBudgetCheck]").each(function(i) {
        if($(this).get(0) == $OBJ.get(0)) {
            pos = i;
            return false;
        }
        
    });
    //alert(pos);
    var dev_yn = $("input:radio[name=dev_yn]:checked").val();
    var div_flag = $("select[name=div_flag] option:selected").val();
    var relDieNo = $("input[name=relDieNo]:eq("+ pos +")").val();
    var expectCost = $("input[name=expectCost]:eq("+ pos +")").val();
    
    var action = "/plm/jsp/ecm/BudgetInterfaceCheck.jsp"
               + "?devYn="    + dev_yn
               + "&division=" + div_flag
               + "&dieNo="    + relDieNo
               + "&cost="     + expectCost
               + "&rowInx="   + pos
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
    
}); */

<% /* Feedback - 비용확보 - 확인 */ %>
function setBudgetYn( budget_yn, row_inx, msg )
{
    //alert('setBudgetYn( '+ budget_yn +', '+ row_inx +', '+ msg +' )');
    var budget = document.getElementsByName("budget");
    if(budget == null || typeof budget == 'undefined') return;
    
    if( budget_yn == 'N' )
    {
    	try{
        	budget[row_inx].value = '미확보';
    	}catch(e){
    		
    	}
    }
    else if( budget_yn =='Y')
    {
    	try{
        	budget[row_inx].value = '확보';
    	}catch(e){
    		
    	}
    }
    else {
    	try{
        	budget_yn = 'N';
        	budget[row_inx].value = '미확보';
    	}catch(e){
    		
    	}
    }

    
    var secureBudgetYn = document.getElementsByName("secureBudgetYn");
    if(secureBudgetYn == null || typeof secureBudgetYn == 'undefined') return;
    try{
    	secureBudgetYn[row_inx].value = budget_yn;
    }catch(e){
    	
    }

    
    if( msg != '')
    {
        alert(msg);
    }
    
    
    hideProcessing();
}


function popupProject()
{
    var url="/plm/jsp/project/SearchPjtPop.jsp?status=P&type=P";
    openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
}

function setProject(objArr) {

    if(objArr == null || objArr.length == 0) {
        return;
    }

    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++)
    {
        trArr = objArr[i];

        document.forms[0].project_oid.value= trArr[0];
        document.forms[0].project_id.value = trArr[1];
        document.forms[0].project_name.value= trArr[2];

        // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
        lfn_setInfosRelatedProject(trArr[0]);
        
        
    }
}

//연계ECR 검색 팝업 호출
function popupRelEcr(targetId) {
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcrPopupForm.jsp?mode=m&statusAll=Y";
    url += "&obj=prodMoldCls^1&obj=mode^multi";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'', 1000, 700, opts);
}

function setRelEcr(arrObj) {
	insertRelEcrLine(arrObj);
}


//연계ECR 라인추가(projectIssueCreate.jsp 참조)
function insertRelEcrLine(objArr) {
  if(objArr.length == 0) {
	  hideProcessing();
      return;
  }
  var targetTable = document.getElementById("relEcrTable");

  var str ="";
  var trArr;
  for(var i = 0; i < objArr.length; i++) {
      trArr = objArr[i];
      if( !isDuplicateEcr2( trArr[1] ) )
      {
          var tableRows = targetTable.rows.length;
          var newTr = targetTable.insertRow(tableRows);

          //전체선택 checkbox
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.width = "20";
          newTd.className = "tdwhiteM";
          newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                          + "<div style=\"display:none;\"><input name='chkSelectRelEcr' type='checkbox' value=''></div>"
                          ;    

          //ECR번호
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.width = "110";
          newTd.className = "tdwhiteM";
          newTd.innerHTML = "<a href=\"javascript:viewRelEcr('" + trArr[0] + "');\">" + trArr[1] + "</a>";
          newTd.innerHTML += "<input type='hidden' name='relEcrOid' value='" + trArr[0] + "'>";
          newTd.innerHTML += "<input type='hidden' name='relEcrId' value='" + trArr[1] + "'>";
          
          var chg_reason = trArr[6];
          if(chg_reason == 'REASON_5'){//ECR 설변사유의 원가절감 코드가 ECO의 코드가 다르기 때문에.
              chg_reason = 'REASON_11';
          }
          
          newTd.innerHTML += "<input type='hidden' name='ecrChangeReason' value='" + chg_reason + "'>";
          
          var reasonChk = $("input[name=chk_chg_reason][value='" + chg_reason + "']");
          if(!reasonChk.is(':checked')) reasonChk.click();
          
          //ECR 제목
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.width = "";    // "*"일 경우 이상동작한다.
          newTd.className = "tdwhiteL";
          str ="";
          str +="<font title=\""+trArr[5]+"\">";
          str += "<div class='ellipsis' style='width:350px;'><nobr>"+ trArr[5] +"</nobr></div></font>";
          newTd.innerHTML = str;

          //작성부서
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.width = "200";
          newTd.className = "tdwhiteM";
          newTd.innerHTML = "&nbsp;" + trArr[2] + "&nbsp;";

          //작성자
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.width = "100";
          newTd.className = "tdwhiteM";
          newTd.innerHTML = "&nbsp;" + trArr[3] + "&nbsp;";

          //승인일자
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.width = "100";
          newTd.className = "tdwhiteM0";
          newTd.innerHTML = "&nbsp;" + trArr[4] + "&nbsp;";
      }
  }
  
  hideProcessing();
}

function checkECRReason(obj){
	
	var value = $(obj).val();
	var isEcrReason = false;
	
	if(!$(obj).is(':checked')){
		$("input[name='ecrChangeReason']").each(function(){
	        if(value == $(this).val()) {
	            isEcrReason = true;
	            return false;
	        }
	    });
	}
	
	if(isEcrReason){
		alert("연계 ECR에 정의된 설계변경사유입니다.")
		return false;
	}
	
	lfn_setChangeDetailReasonList(obj);
    lfn_setChgDetailRelChk(obj);
}

<% /* deprecated */ %>
function isDuplicateEcr( ecr_id ) {
//부품추가시 선택된 부품정보를 중복체크한다.
  var tBody = document.getElementById("relEcrTable");
  var rowsLen = tBody.rows.length;
  if(rowsLen > 0)
  {
      var primarEcr = document.forms[0].relEcrId;
      var ecrLength = primarEcr.length;
      if( ecrLength > 0 )
      {
          for(var i = 0; i < ecrLength; i++)
          {
              if( primarEcr[i].value == ecr_id )
              {
                  return true;
              }
          }
      }
      else
      {
          if( primarEcr.value == ecr_id )
          {
              return true;
          }
      }
  }
  return false;
}

function isDuplicateEcr2( ecr_id )
{
	var isDuplicate = false;
	
	var list = document.getElementsByName("relEcrId");
	for( var cnt=0; cnt < list.length; cnt++ )
	{
	  if( list[cnt].value == ecr_id )
	    isDuplicate = true;
	}
	
	return isDuplicate;
}

//ECR 상세조회 팝업
function viewRelEcr(oid){
  var url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid=" + oid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

// 제품 검색 팝업 호출
function poupProduct() {
    showProcessing();
    SearchUtil.selectPart('selectedPart','pType=FWR');
}
// 제품 라인 추가
function selectedPart(objArr) {
    if(objArr == null || objArr.length == 0) {
    	hideProcessing();
    	return;
    }
    var targetTable = document.getElementById("relPartTable");

    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];
        if( !partDuplicateCheck2(trArr[0]))
        {
            var tableRows = targetTable.rows.length;
            var newTr = targetTable.insertRow(tableRows);
            newTr.onmouseover=function(){relPartTable.clickedRowIndex=this.rowIndex};

            //전체선택 checkbox
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "20";
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                            + "<div style=\"display:none;\"><input name='chkSelectRelPart' type='checkbox' value=''></div>"
                            
				            <!-- 아래는 사용하지 않으나 기존 서버로직에서 사용하고 있으므로 넘겨주어야 Null Exception 이 발생하지 않는다. -->          
                            + "<input type='hidden' name='relDieNo' value=''>"
                            + "<input type='hidden' name='secureBudgetYn' value='Y' >"
                            + "<input type='hidden' name='budget' value='확보' >"
				                                                    
                            ;

            //Part No
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "150";
            newTd.title = trArr[1];
            newTd.className = "tdwhiteM";
            str = "";
            str += "<a href=\"javascript:viewRelPart('" + trArr[0] + "');\">" + trArr[1] + "</a>";
            str += "<input type='hidden' name='relPartOid' value='" + trArr[0] + "'>";
            str += "<input type='hidden' name='relPartLinkOid' value=''>";
            str += "<input type='hidden' name='relPartNumber' value='" + trArr[1] + "'>";
/*
            str += "<input type='hidden' name='homepage2DIF' value='" + trArr[17] + "'>";
            str += "<input type='hidden' name='hompage3DIF' value='" + trArr[18] + "'>";
            str += "<input type='hidden' name='homepageIF' value='" + trArr[19] + "'>";
            str += "<input type='hidden' name='spPartDevLevel' value='" + trArr[20] + "'>";
*/            
            
            newTd.innerHTML = str;

            //Part Name
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "";
            newTd.title = trArr[2];
            newTd.className = "tdwhiteL";
            newTd.Title= trArr[2];
            str = "";
            str +="<font title=\""+trArr[2]+"\">";
            str += "<div class='ellipsis' style='width:300px;'><nobr>";
            str += trArr[2] +"</nobr></div></font>";
            newTd.innerHTML = str;

            <%-- 
            //Die No
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "100";
            newTd.title = trArr[5];
            newTd.className = "tdwhiteM";
            str = "";
            if( trArr[5] != '' )
            {
                //str += getTruncStr(trArr[5], 10);
                //str += "<input type='hidden' name='relDieNo' value='" + trArr[5] + "'>";

                if( trArr[7] > 1 )
                {
                	//str += "<input type='text' name='relDieNo' value='"+trArr[5]+"' class='txt_field' style='width:80px' readonly>&nbsp;";
                    //str +="&nbsp;<a href=\"javascript:searchDieNo('"+trArr[1]+"');\" onfocus=\"this.blur();\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
                    str += "<input type='hidden' name='relDieNo' value='"+trArr[5]+"'>";
                    str += ""+trArr[5]+"<a href=\"javascript:searchDieNo('"+trArr[1]+"');\" onfocus=\"this.blur();\">"+trArr[7]+"</a>";                    
                }
                else
                {
                    str += "<input type='hidden' name='relDieNo' value='"+trArr[5]+"'>";
                    str += ""+trArr[5]+"";
                }
                newTd.innerHTML = str;
            }
            else
            {
                str +=  "&nbsp;"
                str += "<input type='hidden' name='relDieNo' value=''>";
                newTd.innerHTML = str;
            }
            --%>
            
            //Rev
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "45";
            newTd.className = "tdwhiteM";
            //newTd.innerHTML = trArr[3] + "&nbsp;";
            newTd.innerHTML = ((typeof trArr[12] != 'undefined') ? trArr[12] : trArr[3]) + "&nbsp;"; // Display Revision

            //매칭부품 개수 가져오기
            var mount = getMatchingPartCount(trArr[0]);
            
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "60";
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"javascript:openViewMatchingPart('"+ trArr[0] +"');\" >"+ mount +"</a>";

            <%-- 
            var partStr = trArr[1]+"";
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
                newTd.width = "100";
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input type='hidden' name='expectCost'>";

                //비용확보여부
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "115";
                newTd.className = "tdwhiteL0";
                str = "";
                /* 
                str += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
                str += "  <tr>";
                str += "    <td align='middle'><input type='hidden' name='budget' value='확보'><input type='hidden' name='secureBudgetYn' value='Y'></td>";
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
                 */
                
                 str += "확보";
                 str += "<input type='hidden' name='budget' value='확보'><input type='hidden' name='secureBudgetYn' value='Y'>";
                                
            }
            else
            {

                //예상비용(천원)
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "100";
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input type='text' name='expectCost' class='txt_fieldR' style='width:80px'>";

                //비용확보여부
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "115";
                newTd.className = "tdwhiteL0";
                str = "";
            	
                str += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
                str += "  <tr>";
                str += "    <td align='middle'>";
                str += "      <input type='hidden' name='secureBudgetYn' value='N'>";
                str += "      <input type='text' name='budget' value='미확보' class='txt_field' style='width:35px' readonly>";
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
            }

            newTd.innerHTML = str;
            --%>
        }
    }
    
    hideProcessing();
}

// 매칭 부품 개수 가져오기
function getMatchingPartCount(partOid){
    var ret = "E";
    $.ajax({
        type: 'get',
        async: false,
        url: '/plm/ext/part/base/matchingPartCount.do?partOid='+partOid,
        success: function (data) {
            if(data != 'Fail'){
                try{
                    ret = data;
                }catch(e){alert(e.message); ret = "E"; }
            }else{
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
                ret = "E";
            }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
    });
    
    return ret;
}

<% /* 비용확보 - 확인 */ %>
<% /* deprecated */ %>
function checkBudget()
{
    var form = document.forms[0];
    var pos = relPartTable.clickedRowIndex;
    var targetTable = document.getElementById("relPartTable");
    var tableRows = targetTable.rows.length;

    
    pos -= 1;
    tableRows -= 1;
    
    
    var division  = "";
    var dev_yn = "";
    var dieno = "";
    var expectCost = "";

    // HEENEETODO : KETS 사업부일 경우 ERP에 던져줘야 하는 division은 무엇인가?
    if( '<%=userGroupStr%>' == '자동차사업부' )
    {
        division = "1";
    }
    else if( '<%=userGroupStr%>' == 'KETS' )
    {
        division = "3";
    }
    else 
    {
        division = "2";
    }

    var dev_yn = document.all("dev_yn");
    var str_dev_yn = '';
    for( var inx=0; inx < dev_yn.length ; inx++)
    {
        if( dev_yn[inx].checked )
        {
            str_dev_yn  = dev_yn[inx].value;
        }
    }

    // 개발
    if( str_dev_yn == "D" )
    {
        dev_yn = "1";
    }
    // 양산
    else
    {
        dev_yn = "2";
    }

    if( tableRows > 1 )
    {
        dieno = form.relDieNo[pos].value;
        expectCost = form.expectCost[pos].value;
    }
    else
    {
        dieno = form.relDieNo.value;
        expectCost = form.expectCost.value;
    }

    if( dev_yn == "2" && isNumber(expectCost) ||  dev_yn == "1" && isNumber(expectCost) && dieno != ''  )
    {
        
        showProcessing();
        document.forms[0].target="setCarData";
        document.forms[0].action="/plm/jsp/ecm/BudgetInterfaceCheck.jsp?devYn="+dev_yn+"&division="+division+"&dieNo="+dieno+"&cost="+expectCost+"&rowInx="+pos;
        document.forms[0].submit();
        
    }
    else
    {
        if( dev_yn == "1"  && dieno == ''  )
        {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00906") %><%--관련 Die No가 존재하지 않아서 예산을 확인할 수 없습니다--%>");
        }
        else if( !isNumber(expectCost)  )
        {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01648") %><%--예상비용(천원)을 입력후 확인하시기 바랍니다.--%>");
            if( tableRows > 1 )
            {
                form.expectCost[pos].focus();
            }
            else
            {
                form.expectCost.focus();
            }
        }
        return;
    }

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

function partDuplicateCheck2( poid )
{
  var isDuplicate = false;

  var list = document.getElementsByName("relPartOid");
  for( var cnt=0; cnt < list.length; cnt++ )
  {
    if( list[cnt].value == poid )
      isDuplicate = true;
  }

  return isDuplicate;
}

<% /* deprecated */ %>
function deletePartLine() {
//부품삭제버튼 클릭시 선택된 부품정보를 삭제한다.
    var body = document.getElementById("iPartTable");
    if (body.rows.length == 0) return;
    var part_checks = document.forms[0].iPartChk;

    if (body.rows.length == 1) {
        if (part_checks[0]=="[object]"){
            if (part_checks[0].checked){
                body.deleteRow(0);
            }
        }else{
            if (part_checks.checked){
                body.deleteRow(0);
            }
        }
    } else {
        for (var i = body.rows.length; i > 0; i--) {
            if (part_checks[i-1].checked) body.deleteRow(i - 1);
        }
    }
}

function trim(val){
	val = val.replace(/^\s*/,'').replace(/\s*$/, '');
	val = val.replace(/\s/gi,'').replace(/ /gi,'');
	return val;
}

function fnGetContentImageWidth()
{
    // 이노디터 Document 객체 가져오기(1번째 이노디터)
    var objDocument = fnGetEditorDocument(0);

    // 이노디터 Document 상의 이미지 리스트 가져오기
    var arrImageList = objDocument.getElementsByTagName("img");

    for(var i=0; i<arrImageList.length; i++)
    {
        var nImageWidth = arrImageList[i].width;
        return nImageWidth;
    }
}


function check()
{
  var ischk_cu = false;
  var isEcoApplyPoint = false;
  
  $("[name=chk_cu]").each(function(index){
      if($(this).prop("checked")){
    	  ischk_cu = true;
      } 
  });
  
  $("[name=ecoApplyPoint]").each(function(index){
      if($(this).prop("checked")){
    	  isEcoApplyPoint = true;
      } 
  });
  	
  var isdesign_guide = false;
  $("[name=chk_design_guide]").each(function(index){
	  if($(this).prop("checked")){
		  isdesign_guide = true;
	  } 
  });
  
  var isdesign_sheet = false;
  $("[name=chk_design_sheet]").each(function(index){
      if($(this).prop("checked")){
    	  isdesign_sheet = true;
      }
  });
  
  var ischk_point = false;
  $("[name=chk_point]").each(function(index){
      if($(this).prop("checked")){
          ischk_point = true;
      } 
  });
  
  var ischk_spec_change = false;
  $("[name=chk_spec_change]").each(function(index){
      if($(this).prop("checked")){
    	  ischk_spec_change = true;
      } 
  });
  //불량구분, 불량유형 체크 검증 start
  var defectshow = 'no';
  
  $('input[name=chk_chg_reason]').each(function(i) {
      
      if ($(this).is(":checked")) {
          var value = $(this).attr("value");
          
          
          if( '<%=userGroupStr%>' == '전자사업부' ){//품질문제 일때만 전자사업부는 불량 관련 항목 필수 2021.10.01 적용 (전자사업부 이민호 요청)
        	  if(value == 'REASON_3' ){
                  defectshow = 'ok';
              }
          }else{
        	  if(value == 'REASON_3' || value == 'REASON_9' || value == 'REASON_2' ){
                  defectshow = 'ok';
              }  
          }
      }
  });
  var defectDiv  = $("#defectDiv").val();
  var defectType  = $("#defectType").val();
  
//불량구분, 불량유형 체크 검증 end
  
  var costChangecode = $('[name=costChange]').val();
  var costVariationcode = $('[name=costVariation]').val();
  
  var form = document.forms[0];
  var isValid = true;

  var dev_yn = document.all("dev_yn");
  var str_dev_yn = '';
  for( var inx=0; inx < dev_yn.length ; inx++)
  {
    if( dev_yn[inx].checked )
    {
      str_dev_yn  = dev_yn[inx].value;
    }
  }

  var chk_chg_reason = document.all("chk_chg_reason");
  var str_chk_chg_reason = '';
    for( var inx=0; inx < chk_chg_reason.length ; inx++)
  {
    if( chk_chg_reason[inx].checked )
    {
      str_chk_chg_reason  += chk_chg_reason[inx].value;
    }
  }

  var targetTable = document.getElementById("relPartTable");

  var chk_cust = document.all("chk_cust");
  var str_chk_cust = '';
    for( var inx=0; inx < chk_cust.length ; inx++)
  {
    if( chk_cust[inx].checked )
    {
      str_chk_cust  += chk_cust[inx].value;
    }
  }

  var chk_chg_fact = document.all("chk_chg_fact");
  var str_chk_chg_fact = '';
    for( var inx=0; inx < chk_chg_fact.length ; inx++)
  {
    if( chk_chg_fact[inx].checked )
    {
      str_chk_chg_fact  += chk_chg_fact[inx].value;
    }
  }

  if( form.eco_name.value == '' )
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "02526") %><%--제목을 입력하세요--%>');
    
    var tabBasic = document.getElementById("tabBasic");
    if(tabBasic.style.display == 'none') {
        clickTabBtn(1);
    }
    
    form.eco_name.focus();
  }
  if( str_dev_yn == '' && isValid)
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "01191") %><%--단계 구분을 선택하세요--%>');

    var tabBasic = document.getElementById("tabBasic");
    if(tabBasic.style.display == 'none') {
        clickTabBtn(1);
    }
    
    form.dev_yn[0].focus();    
  }
  
  if( ecr_check == 'ok' && isValid )
  {
	var ecrTable = document.getElementById("relEcrTable");
	if(ecrTable.rows.length <=1){
		isValid = false;
	    alert('<%=messageService.getString("e3ps.message.ket_message", "09481") %><%--ECR정보를 선택하시기 바랍니다.--%>');
	    

	    var tabBasic = document.getElementById("tabBasic");
	    if(tabBasic.style.display == 'none') {
	        clickTabBtn(1);
	    }    
	    
	    popupRelEcr();
	}
	
    
  }
  if(defectDiv == '' & defectshow == 'ok' && isValid){
	  isValid = false;
	  alert('<%=messageService.getString("e3ps.message.ket_message", "09011") %><%--불량구분을 입력하세요.--%>');
  }
  if(defectType == '' & defectshow == 'ok' && isValid){
      isValid = false;
      alert('<%=messageService.getString("e3ps.message.ket_message", "09012") %><%--불량유형을 입력하세요.--%>');
  }
  if( (str_dev_yn == 'TCAR' || str_dev_yn == 'PROTO' || str_dev_yn == 'PILOT' || str_dev_yn == 'D') && form.project_id.value == '' && isValid)
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "04930") %><%--개발단계가 \'양산\'이 아닐 경우 프로젝트 정보를 입력하셔야 합니다.--%>');

    var tabBasic = document.getElementById("tabBasic");
    if(tabBasic.style.display == 'none') {
        clickTabBtn(1);
    }        
    
    popupProject();
  }
 
  if( targetTable.rows.length <=1 && isValid)
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "00914") %><%--제품을 추가하세요--%>');

    var tabBasic = document.getElementById("tabBasic");
    if(tabBasic.style.display == 'none') {
        clickTabBtn(1);
    }    
    
    poupProduct();
  }
  if( (str_chk_chg_reason == '' || str_chk_chg_reason == 'REASON_1') && isValid )
  {
    isValid = false;
    var msg = "<%=messageService.getString("e3ps.message.ket_message", "01855") %>";
    if(str_chk_chg_reason == 'REASON_1'){
    	msg += "\n\n(고객요청시 추가로 다른 설변사유를 선택하셔야합니다.)";
    }
    alert(msg);

    var tabBasic = document.getElementById("tabBasic");
    if(tabBasic.style.display == 'none') {
        clickTabBtn(1);
    }        
    
    form.chk_chg_reason[0].focus();    
  }
  if(str_chk_cust == '' && isValid){
	  isValid = false;
	  alert("고객확인구분을 선택하여 주십시오.");
  }
  if(!ischk_cu && isValid){
	  isValid = false;
	  alert("CU도면 변경여부 선택하여 주십시오.");
  }
  if(!isEcoApplyPoint && isValid){
      isValid = false;
      alert("ECO적용시점을 선택하여 주십시오.");
  }
  
  if(costChangecode == "" && isValid){
      alert("원가변동을 선택하시기 바랍니다.");
      isValid = false;
      return false;
  }
  <%-- 
  else if( str_chk_chg_reason != '' && str_chk_chg_reason.indexOf('REASON_6') > -1 && form.other_reason.value == '' )
  {
    isValid = false;
    alert("<%=messageService.getString("e3ps.message.ket_message", "01846") %>설계변경 기타사유를 입력하여 주십시오");
    
    var tabBasic = document.getElementById("tabBasic");
    if(tabBasic.style.display == 'none') {
        clickTabBtn(1);
    }        
    
    form.other_reason.focus();    
  }
  --%>
  if( str_chk_cust != '' && str_chk_cust.indexOf('CHECK_4')> -1 && form.other_cust_flag.value == '' && isValid )
  {
    isValid = false;
    alert("<%=messageService.getString("e3ps.message.ket_message", "00864") %><%--고객확인 기타 내용을 입력하여 주십시오--%>");
    
    var tabBasic = document.getElementById("tabBasic");
    if(tabBasic.style.display == 'none') {
        clickTabBtn(1);
    }        
    
    form.other_cust_flag.focus();     
  }
  if(!isdesign_guide && isValid){
	  isValid = false;
	  alert("<%=messageService.getString("e3ps.message.ket_message", "09473") %><%--설계가이드 반영을 선택하여 주십시오.--%>");
  }
  if(!isdesign_sheet && isValid){
	  isValid = false;
	  alert("<%=messageService.getString("e3ps.message.ket_message", "09474") %><%--설계검증체크시트 반영을 선택하여 주십시오--%>");
  }
  //else if( '<%=userGroupStr%>'=='자동차사업부' && str_dev_yn == "P" && (str_chk_chg_fact.indexOf('FACT_3') > 0 || str_chk_chg_fact.indexOf('FACT_4') > 0) )
  //{
  //  //개발/양산구분이 P:양산인 경우 도면의 연계일정은 필수입력항목이 된다.
  //    //도면/BOM 입력항목 체크
  //  //양산단계
  //  var relEpmTable = document.getElementById("relEpmTable");
  //  var relEpmTableRows = relEpmTable.rows.length;
  //  var epmCnt = 0;
  //  var planCnt = 0;
  //  var curRow = 0;
  //  if(relEpmTableRows > 1) {
  //    if(relEpmTableRows > 2) {
  //      for(var i = 1; i < relEpmTableRows; i++) {
  //        curRow = i - 1;
  //        if(form.scheduleId[curRow].value != "") {
  //          planCnt++;
  //        }
  //        if(form.relEcaActivityType[curRow].value == "1") {//도면
  //          epmCnt++;
  //        }
  //      }
  //    }else{
  //      if(form.scheduleId.value != "") {
  //        planCnt++;
  //      }
  //      if(form.relEcaActivityType.value == "1") {//도면
  //        epmCnt++;
  //      }
  //    }
  //    if(planCnt != epmCnt)
  //    {
  //      isValid = false;
  //      alert("연계일정을 입력하세요.");
  //    }
  //  }
  //}
  <%-- 
  else
  {
     if( '<%=status%>' != 'ACTIVITYCOMPLETED' )
     {
      var relEpmTable = document.getElementById("relEpmTable");
      var relEpmTableRows = relEpmTable.rows.length;
      var curRow = 0;

      if( relEpmTableRows > 1 )
      {
        if( relEpmTableRows > 2 )
        {
          for( var i = 1; i < relEpmTableRows; i++)
          {
            curRow = i - 1;
            if(form.relEcaActivityType[curRow].value == "2")
            {
              if( form.masschange_yn[curRow].value == 'Y' )
              {
                if( form.parentPart[curRow].value == '' )
                {
                  isValid = false;
                  alert("<%=messageService.getString("e3ps.message.ket_message", "00912") %>관련 모부품을 입력하세요");
                  return;
                }
              }
            }
          }
        }
        else
        {
          if(form.relEcaActivityType.value == "2")
          {
            if( form.masschange_yn.value == 'Y' )
            {
              if( form.parentPart.value == '' )
              {
                isValid = false;
                alert("<%=messageService.getString("e3ps.message.ket_message", "00912") %>관련 모부품을 입력하세요");
                return;
              }
            }
          }
         }
      }
    }

  }
  --%>
  
  if(isValid && (costChangecode == "DECREASE" || costChangecode == "INCREASE")){
      //DECREASE : 감소, INCREASE : 증가
      if(costVariationcode == ""){
          alert("원가변동이 감소 또는 증가 일때 원가증감비율(수주대비)를 입력하여야합니다.");
          isValid = false;
          return false;
      }
      
  }

  
  if(isValid) {
      
	  // 설계변경사유
	  $("table[id*='chgRsTable^']").each(function(i) {
          if($(this).css('display') == 'block') {
        	  
        	  var hasChecked = false;
        	  $(this).find('input[name=chk_chg_type]').each(function(i) {
        		  if($(this).prop('checked') == true) {
        			  hasChecked = true;
        			  return false;
        		  }
        		  
              });
              
        	  if(!hasChecked) {
        		  isValid = false;
        		  alert("설계변경 상세사유를 하나 이상 선택하시기 바랍니다.");
                  return false;	  
        	  }
        	  
          }
      });
        
  }
  
  
  if(isValid) {
	  
	  // 변경계획일
	  var now = new Date();
	  var year= now.getFullYear();
	  var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
	  var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
	  $("#relEpmTable").find("[name=relEcaPlanDate]").each(function(i) {
	      var relEcaEpmPlanDate = $(this).val();
	      if(relEcaEpmPlanDate != '') {
		      var toDate = year +"-"+ mon +"-"+ day;
		        
		      if(relEcaEpmPlanDate < toDate) {
		          alert('<%=messageService.getString("e3ps.message.ket_message", "04060") %><%--변경계획일은 오늘 이전날짜를 기입할 수 없습니다.--%>');
		          isValid = false;
		          return false;
		      }
	      }
	  });

  }
  
  if(isValid) {
	  if(!ischk_point){
	      isValid = false;
	      alert("<%=messageService.getString("e3ps.message.ket_message", "09479") %><%--중요포인트 반영/변경 선택하여 주십시오--%>");
	  }
  }
  if(isValid) {
	  if(!ischk_spec_change){
	      isValid = false;
	      alert("<%=messageService.getString("e3ps.message.ket_message", "09480") %><%--사양변경 식별표기를 선택하여 주십시오--%>");
	  }
  }
//var nImageWidth = fnGetContentImageWidth();
  if(isValid) {
	  var editor0 = fnGetEditorText(0);
	  editor0 = trim(editor0);
	  if(editor0 == ''){
		  alert("변경 전 을 입력하십시오");
		  isValid = false;
	  }
  }
  
  if(isValid) {
	  var editor1 = fnGetEditorText(1);
	  editor1 = trim(editor1);
	  if(editor1 == ''){
          alert("변경 후 를 입력하십시오");
          isValid = false;
      }
  }
  if(defectshow == 'ok'){
	  var defectDivName = $("#defectDiv option:selected").text();
	  $("#defectDivName").val(defectDivName);
	  var defectTypeName = $("#defectType option:selected").text();
	  $("#defectTypeName").val(defectTypeName);
  }
  
  return isValid;
}

//첨부파일 관련 스크립트 시작
function insertFileLine()
{
    var innerRow = fileTable.insertRow();
  innerRow.height = "27";
    var innerCell;

    var filePath = "filePath";
    var filehtml = "";

    for( var k=0 ; k < 2 ; k++ )
    {
        innerCell = innerRow.insertCell();

        if (k == 0)
        {
            innerCell.width = "20";
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                                + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>"
                                ;            
        
         }
        else if (k == 1)
        {
            innerCell.width = "";
            innerCell.className = "tdwhiteL0";
            innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:99%'>";
        }
    }

}


<%--관련품질문제--%>
function selectDqmAfterFunc(objArr){
    
    /* 
    var code = "";
    var name = "";
    for(var i= 0 ; i < objArr.length; i++){
        if(i != 0){
            code += ",";
            name += ",";
        }
        code += objArr[i].OID;
        name += objArr[i].PROBLEMNO;
    }
    $('[name=duplicateReportCode]').val(code);
    $('[name=duplicateReportName]').val(name);
    */
    
    if(objArr.length == 0) {
        return;
    }
    var targetTable = document.getElementById("relDqmTable");

    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++) {
      trArr = objArr[i];

      if( !isDuplicateDqm( trArr.OID ) )
      {
        var tableRows = targetTable.rows.length;
        var newTr = targetTable.insertRow(tableRows);

        //전체선택 checkbox
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "20";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                        + "<div style=\"display:none;\"><input name='chkSelectRelDqm' type='checkbox' value=''></div>"
                        ;

        //문제점
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "";
        newTd.className = "tdwhiteL";
        newTd.setAttribute("title", trArr.PROBLEMNO);
        str = "<div class='ellipsis' style='width:510px;'><nobr>";
        str += "<a href=\"javascript:viewRelDqm('" + trArr.OID + "');\">" + trArr.PROBLEM + "</a>";
        str += "<input type='hidden' name='relDqmOid' value='" + trArr.OID + "'>";
        str += "<input type='hidden' name='relDqmLinkOid' value=''></nobr></div>";
        newTd.innerHTML = str;

        //작성자
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "140";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr.CREATENAME;

        //작성일자
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.width = "115";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = trArr.CREATESTAMP;
      }
    }    
}

function isDuplicateDqm( dqmOid )
{
  var isDuplicate = false;

  var dqmList = document.getElementsByName("relDqmOid");
  for( var cnt=0; cnt < dqmList.length; cnt++ )
  {
    if( dqmList[cnt].value == dqmOid )
      isDuplicate = true;
  }

  return isDuplicate;
}

//품질문제 상세조회 팝업
function viewRelDqm(v_poid){
    var url = '/plm/ext/dqm/dqmMainForm.do?type=view&oid='+ v_poid;
    getOpenWindow2(url, 'dqmMainFormPopup', 1100, 768);
}


function lfn_setChgCostChangeCheck(obj){
	//CHECK : 검토, DECREASE : 감소, INCREASE : 증가,  NONE : 없음
	var code = $('[name=costChange]').val();
	
	$("[name=customName]").each(function(index){
        var value = $(this).attr("value");
        
        if(code == "CHECK"){
            if(value == "설계원가의뢰") { 
                $("[name=chkPostAct]").eq(index).prop("checked", true);
            }
            if(value == "설계원가보고서") { 
                $("[name=chkPostAct]").eq(index).prop("checked", true);
            }
        }else if(code == "DECREASE"){
        	
            if(value == "설계원가의뢰") { 
                $("[name=chkPostAct]").eq(index).prop("checked", false);
            }
            if(value == "설계원가보고서") { 
                $("[name=chkPostAct]").eq(index).prop("checked", true);
            }
        }else if(code == "INCREASE"){
            
            if(value == "설계원가의뢰") { 
                $("[name=chkPostAct]").eq(index).prop("checked", false);
            }
            if(value == "설계원가보고서") { 
                $("[name=chkPostAct]").eq(index).prop("checked", true);
            }
        }else if(code == "NONE" || code == ""){
            
            if(value == "설계원가의뢰") { 
                $("[name=chkPostAct]").eq(index).prop("checked", false);
            }
            if(value == "설계원가보고서") { 
                $("[name=chkPostAct]").eq(index).prop("checked", false);
            }
        }
	});
}

function lfn_setChgDesignCheck(obj){
    var name = $(obj).attr("name");
    
    $("[name=customName]").each(function(index){
        var value = $(this).attr("value");  
        if(name == "chk_design_guide"){  
            if($(obj).attr("value") == "Y"){
               if(value == "설계가이드") { $("[name=chkPostAct]").eq(index).prop("checked", true);}
            }else{
               if(value == "설계가이드") { $("[name=chkPostAct]").eq(index).prop("checked", false);}
            }
        }else if(name == "chk_design_sheet"){
             if($(obj).attr("value") == "Y"){ 
               if(value == "설계검증체크시트") { $("[name=chkPostAct]").eq(index).prop("checked", true);}
            }else{
               if(value == "설계검증체크시트") { $("[name=chkPostAct]").eq(index).prop("checked", false);}
            }
        }
    });
    
}

function lfn_setSpecChangeCheck(obj){
    var name = $(obj).attr("name");
    
    $("[name=customName]").each(function(index){
        var value = $(this).attr("value");  
        if(name == "chk_spec_change"){  
            if($(obj).attr("value") == "Y"){
               if(value == "사양변경 식별표기 지시서") { $("[name=chkPostAct]").eq(index).prop("checked", true);}
            }else{
               if(value == "사양변경 식별표기 지시서") { $("[name=chkPostAct]").eq(index).prop("checked", false);}
            }
        }
    });
    
}

function changeDefectDivCode(){
    var choiceCode  = $("#defectDiv").val();
    
    $("#defectType").empty().data('options');
    $.ajax({
    	url : "/plm/ext/code/getChildCodeList.do?codeType=PROBLEMDIVTYPE&parentCode="+choiceCode,
        type : "POST",
        dataType : 'json',
        async : false,
        success: function(data) {
        	$("#defectType").append("<option value=''><%=messageService.getString("e3ps.message.ket_message", "09434") %><%--선택--%></option>");
        	$.each(data, function() {
        		$("#defectType").append("<option value='"+this.code+"'>"+ this.value +"</option>");
        	});
        }
    });
}

//-->
</script>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <!-- col width="110"><col width="248"><col width="90"><col width="220" -->
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
    <td colspan="3" class="tdwhiteL0"><input type="text" name="eco_name" class="txt_field"  style="width:100%" value="<%=eco_name %>" maxlength="100"></td>
  </tr>
  <tr>
    <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%><span class="red">*</span></td>
    <td class="tdwhiteL">
        <%
        Hashtable<String, String> devFlag = new Hashtable<String, String>();
        for( int devCnt=0; devCnt < devFlagList.size(); devCnt++ )
        {
            devFlag = devFlagList.get(devCnt);
        %>
            <input name="dev_yn" type="radio" class="Checkbox" value='<%=devFlag.get("code") %>'  id ="dev_yn"  <%if( dev_yn.equals(devFlag.get("code"))){ %>checked<%} %>><%=devFlag.get("name") %>&nbsp;
        <%
        }
        %>
    </td>
    <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%><span class="red">*</span></td>
    <td class="tdwhiteL0">
      <select name="div_flag" class="fm_jmp" style="width:100%"></select>
    </td>
  </tr>
  <tr>
    <td class="tdblueL">Project No</td>
    <td class="tdwhiteL">
      <input type='hidden' name='project_oid' value='<%=project_oid %>'>
      <input type="text" name="project_id" class="txt_field" style="width:80%" value='<%=project_no %>'>
      <a href="javascript:popupProject();" onfocus="this.blur();">
        <img src="/plm/portal/images/icon_5.png" border="0"></a>
      <a href="javascript:delProject();" onfocus="this.blur();">
        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
    </td>
    <td class="tdblueL">Project Name</td>
    <td class="tdwhiteL0">
      <input type="text" name="project_name" class="txt_fieldRO" style="width:100%" value="<%=project_name %>"  readOnly>
    </td>
  </tr>
  <%
  if( userGroupStr.equals("자동차사업부") || userGroupStr.equals("KETS") )
  {
  %>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04004") %><%--고객사/차종--%></td>
    <td class="tdwhiteL" colspan="3">
      <select name="domestic_yn" id='domestic_yn' class="fm_jmp" style="width:33%" onchange="javascript:searchCarData('maker', this.value, '');"></select>
      <select name="car_maker"  id='car_maker' class="fm_jmp" style="width:33%" onchange="javascript:searchCarData('car', this.value, '' );" >
        <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
      </select>
      <select name="car_category" id='car_category' class="fm_jmp" style="width:33%" >
        <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
      </select>      
    <!-- /td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
    <td class="tdwhiteL">
       <input type="text" name="carTypeName" class="txt_field" style="width: 70%"> 
       <input type="hidden" name="carTypeCode">
       <a href="javascript:SearchUtil.selectCarType('carTypeCode','carTypeName');">
       <img src="/plm/portal/images/icon_5.png" border="0"></a> 
       <a href="javascript:CommonUtil.deleteValue('carTypeCode','carTypeName');">
       <img src="/plm/portal/images/icon_delete.gif" border="0"></a -->
   </td>
  </tr>
  <%
  } else { 
  %>
    <input type="hidden" name="domestic_yn">
    <input type="hidden" name="car_maker">
    <input type="hidden" name="car_category">
  <%
  }
  %>
   <tr>
    <td class="tdblueL" id="ecr_td"><%=messageService.getString("e3ps.message.ket_message", "02121") %><%--연계 ECR 정보--%></td>
    <td colspan="3" class="tdwhiteL0">
 
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
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                        <a href="javascript:deleteDataLine('forms[0]', 'relEcrTable', 'chkSelectRelEcr', 'chkAllRelEcr', 'chkAllRelEcr');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a>
                      </td>
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
      
      <div id="div_scroll" style="width:100%;height:71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
      <table width="100%" cellpadding="0" cellspacing="0" id="relEcrTable">
        <tr class="">
          
                <td width="20" class="tdgrayM"><a href="javascript:popupRelEcr();"><img src="/plm/portal/images/b-plus.png"></a></td>
                <td width="110" class="tdgrayM">ECR No</td>
                <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00209") %><%--ECR 제목--%></td>
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="100" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
              
        </tr>
        <!-- tr>
          <td>
            <table width="100%" cellpadding="0" cellspacing="0" id="relEcrTable" style=table-layout:fixed >
              <col width=40><col width=110><col width=170><col width=100><col width=''><col width=100 -->
              <%
              ArrayList<Hashtable<String, String>> ecrList = (ecoHash != null && ecoHash.size() != 0) ? (ArrayList)ecoHash.get("ecrList") : null;
              Hashtable<String, String> relationEcrHash = null;
              
              int ecrListSize = (ecrList != null) ? ecrList.size() : 0;
              if( ecrListSize > 0 )
              {
                for( int ecrCnt=0; ecrCnt < ecrListSize; ecrCnt++ )
                {
                  relationEcrHash = ecrList.get(ecrCnt);
              %>
                  <tr>
                    <td class="tdwhiteM">
                      <a href="#" onclick="javascript:$(this).next().attr('checked',true); deleteDataLineMinus1('forms[0]', 'relEcrTable', 'chkSelectRelEcr', 'chkAllRelEcr', 'deleteRelEcrList');"><img src="/plm/portal/images/b-minus.png"></a>
                      <input name="chkSelectRelEcr" type="checkbox" style="display:none">
                    </td>
                    <td class="tdwhiteM"><a href="javascript:viewRelEcr('<%=relationEcrHash.get("oid") %>');"><%=relationEcrHash.get("ecr_id") %></a>
                      <input type="hidden" name="relEcrOid" value='<%=relationEcrHash.get("oid") %>' >
                      <input type="hidden" name="relEcrLinkOid" value='<%=relationEcrHash.get("link_oid") %>' >
                      <input type="hidden" name="relEcrId" value='<%=relationEcrHash.get("ecr_id") %>'>
                    </td>
                    <td class="tdwhiteL" title="<%=relationEcrHash.get("ecr_name") %>"><div class='ellipsis' style='width:350px;'><nobr><%=relationEcrHash.get("ecr_name") %></nobr></div></td>
                    <td class="tdwhiteM"><%=relationEcrHash.get("dept_name") %></td>
                    <td class="tdwhiteM"><%=relationEcrHash.get("creator_name") %></td>
                    <td class="tdwhiteM0"><%=EcmUtil.getLastApproveDate( (KETProdChangeRequest)KETObjectUtil.getObject(relationEcrHash.get("oid")) )%>&nbsp;</td>
                  </tr>
              <%
                }
              }
              %>
            <!-- /table>
          </td>
        </tr -->
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
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04620") %><%--관련품질문제--%></td>
    <td colspan="3" class="tdwhiteL0">
      <table width="100%" cellpadding="0" cellspacing="0" id="relDqmTable">
        <tr class="">

                <td width="20" class="tdgrayM"><a href="javascript:SearchUtil.selectMultiDqmAction('selectDqmAfterFunc');"><img src="/plm/portal/images/b-plus.png"></a></td>
                <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04630") %><%--문제점--%></td>
                <td width="140" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04640") %><%--작성자--%></td>
                <td width="115" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "04650") %><%--작성일자--%></td>

        </tr>
        <%
        // 관련품질문제
        if(PersistenceHelper.isPersistent(prodEco)) {
	        QueryResult dqmQr = PersistenceHelper.manager.navigate( prodEco, "dqm", KETEcoDqmLink.class );
	        if( dqmQr != null && dqmQr.size() > 0 )
	        {
	            while( dqmQr.hasMoreElements() )
	            {
	                KETDqmAction ketDqmAction = (KETDqmAction) dqmQr.nextElement();
	                
	                QuerySpec query = new QuerySpec();
	                int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
	                SearchCondition sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(ketDqmAction));
	                query.appendWhere(sc, new int[] { idxHeaer });
	
	                QueryResult dqmHeaderQr = PersistenceHelper.manager.find(query);
	                while (dqmHeaderQr.hasMoreElements()) {
	                    Object[] tempObj = (Object[]) dqmHeaderQr.nextElement();
	                    KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];
	                    
	                    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	                    WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
	                    PeopleData peopleData = new PeopleData(createUser);
	               
        %>
           <tr>
             <td class="tdwhiteM">
               <a href="#" onclick="javascript:$(this).parent().parent().remove();"><img src="/plm/portal/images/b-minus.png"></a>
               <div style="display:none;"><input name='chkSelectRelDqm' type='checkbox' value=''></div>
               
               <input type='hidden' name='relDqmOid' value='<%=CommonUtil.getOIDString(ketDqmHeader) %>'>
               <input type='hidden' name='relDqmLinkOid' value=''>
 
             </td>
             <td class="tdwhiteL" title='<%=ketDqmHeader.getProblem()%>'><a href="javascript:viewRelDqm('<%=CommonUtil.getOIDString(ketDqmHeader) %>');"><div class="ellipsis" style="width:510px;"><nobr><%=ketDqmHeader.getProblem() %></nobr></div></a></td>
             <td class="tdwhiteM"><%=peopleData.name %></td>
             <td class="tdwhiteM0"><%=DateUtil.getDateString(ketDqmRaise.getCreateTimestamp(), "date") %></td>
           </tr>
        <%
	                }    // while (dqmHeaderQr.hasMoreElements()) {
	            }    // while( dqmQr.hasMoreElements() )
	        }    // if( dqmQr != null && dqmQr.size() > 0 )    	
        }
        %>    
      </table>
    </td>
  </tr>
  <% if("".equals(ecoHash.get("defectDivName"))){ %>
  <tr id="defectTr" style=display:none;>
  <%}else{%>
  <tr id="defectTr">
  <%}%>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09003") %><span class="red">*</span><%--불량구분--%></td>
    <td class="tdwhiteL">
      <select name="defectDiv" id='defectDiv' class="fm_jmp" style="width:100%" onchange="javascript:changeDefectDivCode();"></select>
      <input type='hidden' id='defectDivName' name='defectDivName' value=''>
   </td>
   <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09004") %><span class="red">*</span><%--불량유형--%></td>
    <td class="tdwhiteL">
      <select name="defectType"  id='defectType' class="fm_jmp" style="width:100%" >
        <option value=""><%=messageService.getString("e3ps.message.ket_message", "09434") %><%--선택--%></option>
      </select>
      <input type='hidden' id='defectTypeName' name='defectTypeName' value=''>
   </td>
  </tr>           
    
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%><span class="red">*</span></td>
    <td colspan="3" class="tdwhiteL0">
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
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:poupProduct();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
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
        <tr class="">
          
                <td width="20" class="tdgrayM"><a href="javascript:poupProduct();"><img src="/plm/portal/images/b-plus.png"></a></td>
                <td width="200" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <!-- td width="100" class="tdgrayM">Die No</td -->
                <td width="45" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="60" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "04080") %><%--매칭부품--%></td>
                <!-- td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td -->
                <!-- td width="115" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%><span class="red">*</span></td -->
                
        </tr>
        <!-- tr>
          <td>
            <table width="100%" cellpadding="0" cellspacing="0" id="relPartTable" style=table-layout:fixed >
              <col width=40><col width=85><col width=''><col width='90'><col width=45><col width=100><col width=112 -->
              <%
              ArrayList<Hashtable<String, String>> partList =  (ecoHash != null && ecoHash.size() != 0) ? (ArrayList)ecoHash.get("partList") : null;
              Hashtable<String, String> partHash = null;
              
              int partListSize = (partList != null) ? partList.size() : 0;
              if( partListSize > 0 )
              {
                for( int partCnt=0; partCnt < partListSize; partCnt++ )
                {
                  partHash = partList.get(partCnt);
              %>
                  <tr height="27" onMouseOver='relPartTable.clickedRowIndex=this.rowIndex'>
                    <td class="tdwhiteM">
                      <a href="#" onclick="javascript:$(this).next().attr('checked',true); deleteDataLineMinus1('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');"><img src="/plm/portal/images/b-minus.png"></a>
                      <input name="chkSelectRelPart" type="checkbox" value='<%=partHash.get("part_oid") %>' style="display:none">
                      <input type="hidden" name="relPartOid" value='<%=partHash.get("part_oid") %>' >
                      <input type='hidden' name='relPartNumber' value='<%=partHash.get("part_no") %>'>
            
                      <!-- 아래는 사용하지 않으나 기존 서버로직에서 사용하고 있으므로 넘겨주어야 Null Exception 이 발생하지 않는다. -->          
                      <input type='hidden' name='relDieNo' value='<%=partHash.get("die_no") %>'>
                      <input type='hidden' name='secureBudgetYn' value='<%=partHash.get("secure_budget_yn") %>' >
                      <input type='hidden' name='budget' value='<%=partHash.get("budget_yn") %>' >
<!-- 
                      <input type='hidden' name='2d' value='<%=partHash.get("homepage2DIF") %>' >
                      <input type='hidden' name='3d' value='<%=partHash.get("hompage3DIF") %>' >
                      <input type='hidden' name='hp' value='<%=partHash.get("homepageIF") %>' >
     -->                 
                    </td>
                    <td class="tdwhiteM" title="<%=partHash.get("part_no") %>"><a href="javascript:viewRelPart('<%=partHash.get("part_oid") %>');"><%=partHash.get("part_no") %></a></td>
                    <td class="tdwhiteL" title="<%=partHash.get("part_name") %>"><div class="ellipsis" style="width:300px;"><nobr><%=partHash.get("part_name") %></nobr></div></td>
                    
                    <%-- 
                    <td class="tdwhiteM" title="<%=partHash.get("die_no") %>">
	                <%
	                if( Integer.parseInt(MoldProjectHelper.getDieNoCnt(partHash.get("part_no"))) > 1)
	                {
	                %>  
                      <a href="javascript:searchDieNo('<%=partHash.get("part_no") %>');"><div class="ellipsis" style="width:100px;"><nobr><%=partHash.get("die_no") %></nobr></div></a>
                      <input type='hidden' name='relDieNo' value='<%=partHash.get("die_no") %>'>
	                <%
	                }
	                else
	                {
	                %>
                      <div class="ellipsis" style="width:90px;"><nobr><%=partHash.get("die_no") %></nobr></div>
                      <input type='hidden' name='relDieNo' value='<%=partHash.get("die_no") %>'>
	                <%
	                }
	                %>
                    </td>
                    --%>
                    
                    <td class="tdwhiteM"><%=partHash.get("revDis") %></td>
                    <!-- td class="tdwhiteM0"><%=partHash.get("part_ver") %></td -->
                    <td class="tdwhiteM0"><a href="javascript:openViewMatchingPart('<%=partHash.get("part_oid") %>');" ><%=PartBaseHelper.service.getMatchingPartCount(partHash.get("part_oid")) %></a></td>
                    
                    <%-- 
	                <%
	                /* 
	                if( partHash.get("part_no").charAt(0) == 'R'  || (partHash.get("part_no")).substring(0,3).equals("H42") ||  (partHash.get("part_no")).substring(0,3).equals("H43")){ 
	                */    
	                  
	                // JavaScript로 동적 추가할 때 로직을 그대로 옮긴다.
	                String subValue = (partHash.get("part_no")).substring(0,3);
	                if( (subValue.equalsIgnoreCase("R40") || subValue.equalsIgnoreCase("R41") || subValue.equalsIgnoreCase("R50") || subValue.equalsIgnoreCase("R60") || subValue.equalsIgnoreCase("R68"))
	                    || (subValue.equalsIgnoreCase("H42") || subValue.equalsIgnoreCase("H43") || subValue.equalsIgnoreCase("H45") || subValue.equalsIgnoreCase("H46") || subValue.equalsIgnoreCase("H47") 
	                        || subValue.equalsIgnoreCase("H52") || subValue.equalsIgnoreCase("H54") || subValue.equalsIgnoreCase("H57") || subValue.equalsIgnoreCase("H59") || subValue.equalsIgnoreCase("H64") 
	                            || subValue.equalsIgnoreCase("H65") || subValue.equalsIgnoreCase("H66"))
	                                || (subValue.equalsIgnoreCase("K50"))
	                                    || ((partHash.get("part_no")).equalsIgnoreCase("68")))
	                { 
	                %>
                    <td class="tdwhiteM"><input type='hidden' name='expectCost'  value='<%=partHash.get("expect_cost") %>'></td>
                    <td class="tdwhiteL0">
                      <table width='100%' border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td align='middle'><%=partHash.get("budget_yn") %>
                            <input type='hidden' name='secureBudgetYn' value='<%=partHash.get("secure_budget_yn") %>' >
                            <input type='hidden' name='budget' value='<%=partHash.get("budget_yn") %>' >
                          </td>  
                          <td align='right' >
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="30"></td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </table>
                    </td>
                    <%
                    } else { 
                    %>
                    <td class="tdwhiteM"><input type='text' name='expectCost'  value='<%=partHash.get("expect_cost") %>' class='txt_fieldR' style='width:80px'></td>
                    <td class="tdwhiteL0">                  
                      <table width='100%' border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td align='middle'>
                            <input type='hidden' name='secureBudgetYn' value='<%=partHash.get("secure_budget_yn") %>' >
                            <input type='text' name='budget' value='<%=partHash.get("budget_yn") %>' class='txt_field' style='width:35px' readonly>
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
                }   // for( int partCnt=0; partCnt < partList.size(); partCnt++ )
              } // if( partList != null && partList.size() > 0 )
              %>
            <!-- /table>
          </td>
        </tr -->
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
    <td class="tdblueL">
        <%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%><span class="red">*</span>
        <!-- 설계변경사유 상세 설명 START -->
        <img src="/plm/portal/images/exclamation.png" class="detailInfo" style="cursor:pointer;" title="정보를 보려면 클릭하세요."
        onclick="javascript:$('#detailInfo').css('top', $(this).offset().top + 12);$('#detailInfo').css('left', $(this).offset().left + 12);$('#detailInfo').toggle();" />
        <div id="detailInfo" class="detailInfo" style="display:none;position:absolute;left:140px;border:1px solid #B7D1E2;z-index:9999">
            <table style="border-collapse:collapse;width:100%;">
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        설계변경사유
                    </td>
                    <td class="tdblueM" style="">
                        상세 내용
                    </td>
                    <td style="width:25px;text-align:center;background:#34738d;color:#FFF;cursor:pointer;font-weight:bold;" onclick="$('#detailInfo').hide();">
                    X
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        고객요청
                    </td>
                    <td class="tdwhiteL0" colspan="2" style="padding:5px;">
                        고객 요청에 따른 설계변경 진행 <br/>
                        (중복으로 다른 설계변경 사유를 반드시 선택 해야 함)
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        생산성 향상
                    </td>
                    <td class="tdwhiteL0" colspan="2" style="padding:5px;">
                        생산성 향상 목적의 설계변경 진행
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        품질문제
                    </td>
                    <td class="tdwhiteL0" colspan="2" style="padding:5px;">
                        사내공정 또는 고객공정 시 품질 문제가 발생 및 제품 Spec을 만족하지 못한 경우
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        개발단계이관
                    </td>
                    <td class="tdwhiteL0" colspan="2" style="padding:5px;">
                        제품 및 부품의 개발 단계를 양산으로 이관하는 경우
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        BOM 변경
                    </td>
                    <td class="tdwhiteL0" colspan="2" style="padding:5px;">
                        신규 부품으로 변경 또는 부품이 삭제되는 경우, 위치 이동, 수량 변경
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        치수 및 공차 변경
                    </td>
                    <td class="tdwhiteL0" colspan="2" style="padding:5px;">
                        도면 상의 단순 치수 및 공차의 변경이 발생한 경우
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        성능개선
                    </td>
                    <td class="tdwhiteL0" colspan="2" style="padding:5px;">
                        개발팀 자체 검토 시 발생한 문제 해결 및 제품 Spec은 만족하나,<br/>
                        추가적인 성능의 개선을 진행하는 경우
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        원가절감
                    </td>
                    <td class="tdwhiteL0" colspan="2" style="padding:5px;">
                        원가절감 목적으로 설계변경을 진행하는 경우
                    </td>
                </tr>
                <tr>
                    <td class="tdblueM" style="padding:5px;">
                        초도배포
                    </td>
                    <td class="tdwhiteL0" colspan="2" style="padding:5px;">
                        최초 등록된 제품 및 부품을 승인 요청하는 경우
                    </td>
                </tr>
            </table>
        </div>
        <!-- 설계변경사유 상세 설명 END -->
    </td>
    <td colspan="3" class="tdwhiteL0">
      <table width="610" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <%
        Hashtable<String, String> chgRs = new Hashtable<String, String>();
        int chgR = 0;
        for( int chgRCnt=0; chgRCnt < chgReason.size(); chgRCnt++) {
          chgR++;
          chgRs = chgReason.get(chgRCnt);
          if ( !chgRs.get("code").equals("REASON_6") ) {
        %>
            <td width="150"><input type="checkbox" name="chk_chg_reason" value='<%=chgRs.get("code") %>' onclick="javascript:return checkECRReason(this);"><%=chgRs.get("name") %></td>
        <%
          }
          else {
            chgR = chgR -1;
          }
          
          if ( chgRCnt > 0 && (chgR % 4) == 0 ) {
        %>
        </tr>
        <tr>
        <%
          } // if ( chgRCnt > 0 && (chgR % 4) == 0 ) {
        }   // for( int chgRCnt=0; chgRCnt < chgReason.size(); chgRCnt++) {
        %>
        </tr>
        
        <%-- 
        <tr>
        <%
        chgRs = chgReason.get(chgReason.size()-1);
        %>
          <td colspan="4">
            <input type="checkbox" name="chk_chg_reason" value='<%=chgRs.get("code") %>' onclick="javascript:disable( 'other_reason', this.checked );"><%=chgRs.get("name") %>&nbsp;&nbsp;
            <input type="text" name="other_reason" class="txt_field" style="width:500" value="<%=other_chg_reason_desc %>">
          </td>
        </tr>
        --%>
         
      </table>
    </td>
  </tr>
  
  <script type="text/javascript">
    
    /* 
    var changeDetailReasonList = [
                                     ["REASON_2",    [
				                                         ["CDR_100",   "조립성개선"],
				                                         ["CDR_101",   "공정개선"]
			                                         ]
                                     ],
                                     ["REASON_3",    [
                                                         ["CDR_102",   "구조"],
                                                         ["CDR_103",   "기능성"],
                                                         ["CDR_104",   "신뢰성"],
                                                         ["CDR_105",   "조립성"],
                                                         ["CDR_106",   "파손"]
                                                     ]
                                     ],
                                     ["REASON_8",    [
                                                         ["CDR_107",   "구조"],
                                                         ["CDR_108",   "기능성"],
                                                         ["CDR_109",   "조립성"],
                                                         ["CDR_110",   "사양변경"]
                                                     ]
                                     ],
                                     ["REASON_10",   [
                                                         ["CDR_111",   "Proto이관"],
                                                         ["CDR_112",   "Pilot이관"],
                                                         ["CDR_113",   "양산이관"]
                                                     ]
                                     ],
                                     ["REASON_11",   [
                                                         ["CDR_114",   "원재료종류"],
                                                         ["CDR_115",   "중량감소"],
                                                         ["CDR_116",   "공정개선"],
                                                         ["CDR_117",   "공용화"]
                                                     ]
                                     ]                                     
			                     ];
    */
    
    var checkMap = new Object();
    checkMap["REASON_1"] = new Object();
    checkMap["REASON_2"] = new Object();
    checkMap["REASON_3"] = new Object();
    checkMap["REASON_7"] = new Object();
    checkMap["REASON_8"] = new Object();
    checkMap["REASON_9"] = new Object();
    checkMap["REASON_10"] = new Object();
    checkMap["REASON_11"] = new Object();
    checkMap["CHECK_1"] = new Object();
    checkMap["CHECK_2"] = new Object();
    
    //고객요청
    checkMap["REASON_1"].checkList = ["DFMEA", "관리계획서", "검사기준서", "제조공정도", "IMDS", "설변적용시점", 
                                 "고객승인", "투자비(증액)", "폐기", "파생차종고객통보", "유효성검증"];
    checkMap["REASON_1"].needList = ["설변적용시점", "고객승인", "폐기", "파생차종고객통보", "유효성검증"];
    
    //품질문제
    checkMap["REASON_2"].checkList = ["작업기준서", "IMDS", "설계적용시점", "유효성검증"];
    checkMap["REASON_2"].needList = [];
    
    //제품설게변경
    checkMap["REASON_3"].checkList = ["DFMEA", "관리계획서", "검사기준서", "제조공정도", "IMDS", "설변적용시점",
    	                              "투자비(증액)", "폐기", "유효성검증", "고객승인", "파생차종고객통보"];
    checkMap["REASON_3"].needList = ["설변적용시점", "고객승인", "폐기", "파생차종고객통보", "유효성검증"];
    
    //기타
    checkMap["REASON_7"].checkList = ["IMDS", "실중량적용", "MBOM활동"];
    checkMap["REASON_7"].needList = ["MBOM활동"];
    
    //BOM 변경
    checkMap["REASON_8"].checkList = ["IMDS", "유효성검증"];
    checkMap["REASON_8"].needList = [];
    
    //차수 및 공차 변경
    checkMap["REASON_9"].checkList = ["DFMEA", "관리계획서", "제조공정도", "IMDS", "설변적용시점", "실중량적용", "폐기", "유효성검증"];
    checkMap["REASON_9"].needList = ["유효성검증"];
    
    //초도배포
    checkMap["REASON_10"].checkList = ["IMDS", "실중량적용", "원가산출"];
    checkMap["REASON_10"].needList = [];
    
    //원가절감
    checkMap["REASON_11"].checkList = ["IMDS", "실중량적용", "원가산출"];
    checkMap["REASON_11"].needList = ["원가산출"];
    
    //고객확인구분 승인의뢰
    checkMap["CHECK_1"].checkList = ["설변적용시점", "고객승인", "유효성검증"];
    checkMap["CHECK_1"].needList = [];
    
     //고객확인구분 불필요
    checkMap["CHECK_2"].checkList = ["설변적용시점", "고객승인", "유효성검증"];
    checkMap["CHECK_2"].needList = [];
    
    function lfn_setChgDetailRelChk(obj){
    	
        var chgDetailReason = $(obj).val();
        
        if(checkMap[chgDetailReason] != null){
        	
        	var checkList = checkMap[chgDetailReason].checkList;
            var needList = checkMap[chgDetailReason].needList;

            $("[name=customName]").each(function(index){
                var value = $(this).attr("value");
                
                if(checkList.contains(value)){
                    $("[name=chkPostAct]").eq(index).prop("checked", $(obj).is(":checked"));
                    if(needList.contains(value)){
                        if($(obj).is(":checked")) $("[name=chkPostAct]").eq(index).attr("disabled", true);
                        else                      $("[name=chkPostAct]").eq(index).removeAttr("disabled");
                    }
                }
            });
        }
    	
    	//설계변경사유, 고객확인구분 체크된것 중에 중복된것은 체크해제 되지 말아야 하기 때문에 다시 체크하는 작업
    	$("[name=chk_chg_reason], [name=chk_cust]").each(function(){
    		
    		var valueReson = $(this).val();
    	    var chkResult = $(this).is(":checked");
    	    
    	    if(checkMap[valueReson] != null){
    	    	
    	    	var checkList = checkMap[valueReson].checkList;
                var needList = checkMap[valueReson].needList;
                
                $("[name=customName]").each(function(index){
                    var value = $(this).attr("value");
                    
                    if(checkList.contains(value) && chkResult){
                        $("[name=chkPostAct]").eq(index).prop("checked", true);
                        if(needList.contains(value)) $("[name=chkPostAct]").eq(index).attr("disabled", true);
                    }
                });
    	    }
    	});
    }
    
    var ecr_check = '';
    function lfn_setChangeDetailReasonList(obj) {

        // 초도배포일 경우
        if($(obj).val() == 'REASON_12') {
            
        	if($(obj).is(':checked')) {

                if(!confirm('초도배포 선택시 저장후 변경하실 수 없습니다. 계속 하시겠습니까?')) {
                	$(obj).attr('checked', false);
                    return false;
                }
                
	        	// 설계변경 상세사유를 전부 unchecked 처리 한다.
	        	$('#chgRsTableSet').find('input[name=chk_chg_type]').attr('checked', false);
	            
	        	// 초도를 제외한 모든 설계변경사유를 unchecked 처리 한다.
	        	$('input[name=chk_chg_reason]').each(function(i) {
	                if($(this).get(0) != $(obj).get(0)) {
	                	$(this).attr('checked', false);
	                	$(this).attr('disabled', 'disabled');
	                    
	                }
	                
	            });

                // 초도를 제외한 설계변경 상세사유를 display = 'none' 처리한다.
                $("table[id*='chgRsTable^']").each(function(i) {
                	if($(obj) != $(this)) {
                	    $(this).hide();
                	}
                });
                
        	} else {

                // 초도를 제외한 모든 설계변경사유를 checked 처리 한다.
                $('input[name=chk_chg_reason]').each(function(i) {
                    $(this).attr('disabled', false);
                    
                });
                                
        	}
        	
        }
        
        // 설계변경 상세사유를 처리한다.
    	var prodEcoReason = "chgRsTable^"+ $(obj).val();
        
    	var chgRsTable = document.getElementById(prodEcoReason);
        if($(obj).is(':checked')) {
        	
        	var hasChkChgType = 0;
        	$(chgRsTable).find('input[name=chk_chg_type]').each(function(i) {
        	
        	var temp = $(this).attr("value").split("^");
        	    if($(obj).val() == temp[1]){
        	    	hasChkChgType++;
        	    }
            });
             
        	if(hasChkChgType > 0) {
        	    chgRsTable.style.display = "block";
    	        
        	}
        	
	    	/* 	
	    	var targetTable = document.getElementById("changeDetailReasonTable");
	        
	    	var cdrLength = (changeDetailReasonList != null) ? changeDetailReasonList.length : 0;
	    	for(var i=0; i < cdrLength; i++) {
	    		if(changeDetailReasonList[i][0] == prodEcoReason) {

                    var tableRows = targetTable.rows.length;
                    var newTr = targetTable.insertRow(tableRows);
                    
	    			var cdr2Length = (changeDetailReasonList[i][1] != null) ? changeDetailReasonList[i][1].length : 0;
	    	        for(var j=0; j < cdr2Length; j++) {
	    	        	var cdr2 = changeDetailReasonList[i][1][j];
	    	        	
	    	        	var code = cdr2[0];
	    	        	var name = cdr2[1];
	    	        	
	    	            var newTd = newTr.insertCell(newTr.cells.length);
	    	            newTd.width = "10";
	    	            newTd.innerHTML = "<input type='checkbox' name='chk_chg_type' id='checkbox'  value='"+ code +"'>"+ name +"";
	    	               
	    	            
	    	        }  // for(var j=0; j < cdr2Length; j++) {
	    			
	    		} // if(changeDetailReasonList[i][0] == prodEcoReason) {
	    		
	    	}  // for(var i=0; i < cdrLength; i++) {
	    	*/
	    		
	    }  // if($(obj).is(':checked') {
	    else {
	    	chgRsTable.style.display = "none";
	    	$(chgRsTable).find('input[name=chk_chg_type]').attr('checked', false);
            	
	    }
	    
	    var check_cnt = 0;
        $('input[name=chk_chg_reason]').each(function(i) {
            
            if ($(this).is(":checked")) {
                var value = $(this).attr("value");
                
                if(value == 'REASON_1' || value == 'REASON_2' || value == 'REASON_3' || value == 'REASON_11' ){
                    check_cnt++;
                    $('#ecr_td').html("연계 ECR 정보 <span class='red'>*</span>");
                    ecr_check = 'ok';
                   
                }
            }else{
                if(check_cnt == 0){
                	ecr_check = '';
                
                    $('#ecr_td').html("연계 ECR 정보"); 
                }
                
            }
        });
	    
	    //설계변경 사유가 고객요청,생산성 향상,품질문제,성능개선 중 하나를 선택할 경우 [불량구분], [불량유형] 을 보여준다
	    var defectshow = 'no';
        
        $('input[name=chk_chg_reason]').each(function(i) {
            
            if ($(this).is(":checked")) {
                var value = $(this).attr("value");
                
                if(value == 'REASON_1' || value == 'REASON_3' || value == 'REASON_9' || value == 'REASON_2' ){
                    defectshow = 'ok';
                }
            }
        });
        

        
        if(defectshow == 'ok'){
            $("#defectTr").show();
        }else{
            $("#defectTr").hide();
            $("#defectDiv").val("");
            $("#defectType").empty().data('options');
            $("#defectType").append("<option value=''><%=messageService.getString("e3ps.message.ket_message", "09434") %><%--선택--%></option>");
        }
	    
	  
    }
    
    
  </script>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04740") %><%--설계변경 상세사유--%></td>
    <td colspan="3" class="tdwhiteL0" id="chgRsTableSet">
    
      <%
      //Hashtable<String, String> chgRs = new Hashtable<String, String>();
      for( int i=0; i < chgReason.size(); i++) {
	      chgRs = chgReason.get(i);
	      String chgRsCode = chgRs.get("code");
	      String chgRsName = chgRs.get("name");
      %>  
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;display:none;" id="chgRsTable^<%=chgRsCode %>">

        <tr>
          <td width="100"><font color="#34738d">- <%=chgRsName %></font></td>
        <%
        Hashtable<String, String> changeDetailReason = new Hashtable<String, String>();
        for( int j=0; j < changeDetailReasonList.size() ; j++)
        {
            changeDetailReason = changeDetailReasonList.get(j);
            
            String code = changeDetailReason.get("code");
            String name = changeDetailReason.get("name");
            
            if(code.lastIndexOf(chgRsCode) > -1) {
        	    //개발단계이관 - Proto이관, Pilot이관 안보이게 처리
        	    if("REASON_10".equals(chgRsCode)){
        		    if(!"CDR_111^REASON_10".equals(code) && !"CDR_112^REASON_10".equals(code)) {
        %>
                         <td width="100"><input type="checkbox" name="chk_chg_type" id="checkbox"  value='<%=code %>'><%=name %></td>
        <%
        		    }
        	    }else{
        %>
                         <td width="100"><input type="checkbox" name="chk_chg_type" id="checkbox"  value='<%=code %>'><%=name %></td>
        <%		
        	    }
            }
        }
        %>
        </tr>
        
      </table>
      <%
      }
      %>
    </td>
  </tr>
  
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00865") %><%--고객확인구분--%><span class="red">*</span></td>
    <td colspan="3" class="tdwhiteL0">
      <table width="610" border="0" cellspacing="0" cellpadding="0" style=table-layout:fixed >
        <tr>
        <%
        Hashtable<String, String> custFlag = new Hashtable<String, String>();
        for( int custCnt=0; custCnt < custChkList.size(); custCnt++ )
        {
          custFlag = custChkList.get(custCnt);
          if( custCnt == custChkList.size() -1 )
          {
        %>
            <td colspan="3">
              <input type="checkbox" name="chk_cust" id="checkbox15" value='<%=custFlag.get("code") %>'  onclick="javascript:disable( 'other_cust_flag', this.checked );"><%=custFlag.get("name") %>&nbsp;&nbsp;
              <input type="text" name="other_cust_flag" value='<%=other_cust_chk_desc %>' class="txt_field" style="width:200" id="textfield3" maxlength="50">
            </td>
        <%
          }
          else
          {
        %>
            <td><input type="checkbox" name="chk_cust" id="checkbox15" value='<%=custFlag.get("code") %>' onclick="javascript:lfn_setChgDetailRelChk(this);"><%=custFlag.get("name") %></td>
        <%
          }
        %>
        <%
        }
        %>
        </tr>
        <tr>
          <td width="100"></td><td width="100"></td><td width="100"></td><td width="100"></td><td width="100"></td><td width="100"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02647") %><%--조정 및 변경사항--%></td>
    <td colspan="3" class="tdwhiteL0">
      <table width="610" border="0" cellspacing="0" cellpadding="0" style=table-layout:fixed >
        <tr>
        <%
        Hashtable<String, String> chgFact = new Hashtable<String, String>();
        for( int chgFCnt=0; chgFCnt < chgFactList.size() ; chgFCnt++)
        {
          chgFact = chgFactList.get(chgFCnt);
        %>
          <td width="100"><input type="checkbox" name="chk_chg_fact" id="checkbox"  value='<%=chgFact.get("code") %>'><%=chgFact.get("name") %></td>
        <%
        }
        %>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00134") %><span class="red">*</span><%--CU도면{0} 변경여부--%></td>
    <td class="tdwhiteL0">
        <input name="chk_cu" type="radio" class="Checkbox" value="Y">Yes&nbsp;<input name="chk_cu" type="radio" class="Checkbox" value="N"> No
    </td>
    <td class="tdblueL">ECO적용시점<span class="red">*</span><%--ECO적용시점--%></td>
    <td class="tdwhiteL0">
        <input name="ecoApplyPoint" type="radio" class="Checkbox" value="T-CAR">T-CAR&nbsp;
        <input name="ecoApplyPoint" type="radio" class="Checkbox" value="PROTO">PROTO&nbsp;
        <input name="ecoApplyPoint" type="radio" class="Checkbox" value="P1">P1&nbsp;
        <input name="ecoApplyPoint" type="radio" class="Checkbox" value="P2">P2&nbsp;
        <input name="ecoApplyPoint" type="radio" class="Checkbox" value="양산">양산&nbsp;
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04125") %><span class="red">*</span><%--원가변동--%></td>
    <td class="tdwhiteL">
      <select name="costChange" class="fm_jmp" style="width:100%" onchange="lfn_setChgCostChangeCheck(this);"></select>
    </td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04145") %><%--원가증감비율(수주대비)--%></td>
    <td class="tdwhiteL0">
      <input type="text" name="costVariation" class="txt_field" value='<%if(ecoHash.get("costVariationValue") == null){out.println("");}else{out.println(ecoHash.get("costVariationValue"));} %>' style="width:100%, IME-MODE: disabled" onKeyUp='SetNum(this)'>
    </td>
  </tr>
  
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02469") %><%--적용시기--%></td>
    <td class="tdwhiteL">
      <select name="effective_date" class="fm_jmp" style="width:100%"></select>
    </td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02445") %><%--재고처리--%></td>
    <td class="tdwhiteL0">
      <select name="inv_process" class="fm_jmp" style="width:100%"></select>
    </td>
  </tr>
  
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09471") %><span class="red">*<%--설계가이드 반영--%></td>
    <td class="tdwhiteL">
      <input name="chk_design_guide" type="radio" class="Checkbox" value="Y" onclick="lfn_setChgDesignCheck(this);">Yes&nbsp;<input name="chk_design_guide" type="radio" class="Checkbox" value="N" onclick="lfn_setChgDesignCheck(this);"> No
    </td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09472") %><%--설계검증체크시트 반영--%><span class="red">*</td>
    <td class="tdwhiteL0">
      <input name="chk_design_sheet" type="radio" class="Checkbox" value="Y" onclick="lfn_setChgDesignCheck(this);">Yes&nbsp;<input name="chk_design_sheet" type="radio" class="Checkbox" value="N" onclick="lfn_setChgDesignCheck(this);"> No
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09477") %><span class="red">*<%--중요포인트 반영/변경--%></td>
    <td class="tdwhiteL">
      <input name="chk_point" type="radio" class="Checkbox" value="Y">Yes&nbsp;<input name="chk_point" type="radio" class="Checkbox" value="N" onclick="lfn_setChgDesignCheck(this);"> No
    </td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09478") %><%--사양변경 식별표기--%><span class="red">*</td>
    <td class="tdwhiteL0">
      <input name="chk_spec_change" type="radio" class="Checkbox" value="Y" onclick="lfn_setSpecChangeCheck(this);">Yes&nbsp;<input name="chk_spec_change" type="radio" class="Checkbox" value="N" onclick="lfn_setSpecChangeCheck(this);"> No
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
    <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui_half_eco.js"></script>
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
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04106") %><%--검토결과--%></td>
    <td colspan="3" class="tdwhiteL0"><textarea name="reviewResult" rows="2" cols="5" style="width:99%" maxlength="100"><%=reviewResult %></textarea></td>
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
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'fileTable', 'fileSelect', 'chkFileAll', 'deleteFileList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
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
      
      <div id="div_scroll3" style="width:100%;height:81;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
      <table width="100%" cellpadding="0" cellspacing="0" id="fileTable">
        <tr class="headerLock3">

                <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine();"><img src="/plm/portal/images/b-plus.png"></a></td>
                <td width="*" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
              
        </tr>

              <%
              if( attachFileList != null && attachFileList.size() > 0 )
              {
                int attachCnt = attachFileList.size();
                ContentInfo cntInfo = null;
                ContentItem ctf = null;
                String appDataOid = "";
                String url = "";

                for( int fileCnt=0; fileCnt < attachFileList.size() ; fileCnt++ )
                {
                  cntInfo = (ContentInfo)attachFileList.elementAt(fileCnt);
                  ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
                  appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                  //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                  url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                  url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
              %>
                  <tr>
                    <td width="20" class="tdwhiteM">
                      <a href="#" onclick="javascript:$(this).next().attr('checked',true); deleteDataLineMinus1('forms[0]', 'fileTable', 'fileSelect', 'chkFileAll', 'deleteFileList');"><img src="/plm/portal/images/b-minus.png"></a>
                      <input name='fileSelect' type='checkbox' class='chkbox'  value='<%=cntInfo.getContentOid()%>' style="display:none">
                    </td>
                    <td width="*" class="tdwhiteL0"><%=url%></td>
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
</table>
