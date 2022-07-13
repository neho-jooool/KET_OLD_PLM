<%@page contentType="text/html; charset=UTF-8"%>
<%@ page
	import="e3ps.common.code.*,
                 e3ps.common.jdf.config.*,
                 e3ps.common.util.WCUtil,
                 e3ps.common.web.HtmlTagUtil,
                 e3ps.part.entity.KETNewPartList,
                 e3ps.common.web.PageControl"%>
<%@page
	import="wt.lifecycle.LifeCycleTemplate,
                wt.fc.*,
                wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.PhaseTemplate,
                java.util.Hashtable"%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script language="JavaScript">
<!--
// 
$(document).ready(function() {
});

function mailSend(){
	showProcessing();
	
	if(!confirm('메일 수신자로 지정된 인원에게 메일이 발송됩니다.\n정말로 발송하시겠습니까?')){ // 
        hideProcessing();
        return;
    }
	$.ajax({
        type: 'post',
        async: false,
        url: '/plm/ext/common/dashboard/mailSend.do',
        success: function (data) {
            if(data == 'S'){
            	alert("메일 발송 되었습니다.");
            }else{
                alert("메일 발송 중 오류가 발생했습니다.");
            }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
    });
	hideProcessing();
}
	

function save(){
    if(check()){
        /* $.ajax({
            type: 'post',
            async: false,
            url: '/plm/ext/common/dashboard/save.do',
            data: $('[name=receiveForm]').serialize(),
            success    : function(data){
                if(data == 'OK') alert('저장하였습니다.');
            },
            error    : function(xhr, status, error){
                         alert(xhr+"  "+status+"  "+error);
                         
            }
        });  */
    	
        if( confirm(LocaleUtil.getMessage('02463'))){//"저장하시겠습니까?") ){
            $('[name=receiveForm]').attr('action', '/plm/ext/common/dashboard/save.do');
            $('[name=receiveForm]').attr('target', 'download');
            $('[name=receiveForm]').submit();
            showProcessing();
        }
        
    }else{
           alert('입력되지 않은 정보가 있습니다.')
    }
    
    
}

function check(){
    var check = true;
    try{
        
        $("input[name=targetDeptOidAttr]").each(function() {
              if(this.value == "") check = false;
         });
        $("input[name=targetPeopleOidAttr]").each(function() {
              if(this.value == "") check = false;
         });
          
    }catch(e){
        return false;
    }
    return check;
}

function addRow(){
    var addContent;
        addContent = "<tr>";
        addContent +="<td class=\"center\"><input type=\"BUTTON\" onclick=\"deleteRow(this);\" value=\"삭제\"><input type=\"hidden\" name=\"receiveOidAttr\"></td>"; 
        addContent +="<td class=\"center\"></td>"
        addContent +="<td class=\"tdwhiteL\"><input type=\"text\" name=\"targetPeopleName\" class=\"txt_fieldRO\" style=\"width: 70%\" readonly>"
        addContent +="<input type=\"hidden\" name=\"targetPeopleOidAttr\"><a href=\"javascript:;\">"
        addContent +=" <img id=\"img_TargetPeople\" src=\"/plm/portal/images/icon_user.gif\" border=\"0\" onclick=\"addUser(this); return false;\"></a> <a href=\"javascript:;\">"
        addContent +="<img id=\"img_TargetPeopleDel\" src=\"/plm/portal/images/icon_delete.gif\" border=\"0\" onclick=\"delUser(this); return false;\"></a></td>"
        addContent +="<td class=\"center\"><input type=\"hidden\" name=\"duty\"></td>"
        addContent +="<td class=\"tdwhiteL\"><input type=\"text\" name=\"targetDeptName\" class=\"txt_fieldRO\" style=\"width: 95%\" readonly>"
        addContent +="<input type=\"hidden\" name=\"targetDeptOidAttr\"><a href=\"javascript:;\">"
        addContent +=" <img id=\"img_TargetDept\" src=\"/plm/portal/images/icon_5.png\" border=\"0\" onclick=\"addDepartment(this); return false;\"></a> <a href=\"javascript:;\">"
        addContent +="<img id=\"img_TargetDeptDel\" src=\"/plm/portal/images/icon_delete.gif\" border=\"0\" onclick=\"delDepartment(this); return false;\"></a></td>"
        addContent +="</tr>";
        $('#config > tbody:last').append(addContent);    
        
        $("#content").scrollTop($("#content")[0].scrollHeight);
}

function delUser(obj){
	var index = $(obj).index("img#img_TargetPeopleDel");
    
    $('input[name=targetPeopleName]').each(function(i) {
        if(index == i){
            $(this).attr("value",'');
        }
    });
    
    $('input[name=targetPeopleOidAttr]').each(function(i) {
        if(index == i){
            $(this).attr("value",'');
        }
    });
    
    $('#config tr').each(function(i) {
        if(index+1 == i){
            $(this).closest('tr').children('td:eq(1)').text('');
            $(this).closest('tr').children('td:eq(3)').text('');
        }
    });
}

function delDepartment(obj){
	
	var index = $(obj).index("img#img_TargetDeptDel");
	
	$('input[name=targetDeptName]').each(function(i) {
        if(index == i){
            $(this).attr("value",'');
        }
    });
    
    $('input[name=targetDeptOidAttr]').each(function(i) {
        if(index == i){
            $(this).attr("value",'');
        }
    });
}

function deleteRow(obj){
	var idx = $(obj).parent().parent().index()-1;
	//var deloid = $("#delOid").val();
	
	$('input[name=receiveOidAttr]').each(function(i) {
        if(idx == i){
            if($(this).val() != ""){
            	//deloid += $(this).val()+",";
            	//$("#delOid").val(deloid);
            	if( confirm("DB에서 삭제됩니다. 정말로 삭제하시겠습니까?")){
                    $('[name=receiveForm]').attr('action', '/plm/ext/common/dashboard/delete.do?oid='+$(this).val());
                    $('[name=receiveForm]').attr('target', 'download');
                    $('[name=receiveForm]').submit();
                    showProcessing();
                }
            	
            }else{
            	var tr = $(obj).parent().parent();
                tr.remove();
            }
        }
    });
	
    
}

function viewDetail(oid){
	//parent.document.all.iframe.src = "/plm/ext/part/naming/viewPartNamingForm.do?partNamingOid="+oid;
	getOpenWindow2('/plm/ext/sample/sampleRequstMainForm.do?oid=ext.ket.sample.entity.KETSampleRequest:991945072','sampleRequstMainForm',1024,670);
}

var deptImgIdx;

function addDepartmentAfterFunc(rsltArrayObject){
	
	var index = deptImgIdx;
    
    var departOid = new Array();
    var departName = new Array();
    
    for(var i= 0 ; i < rsltArrayObject.length; i++){
        departOid[i] = rsltArrayObject[i].OID;
        departName[i] = rsltArrayObject[i].NAME;
    }
    
    
    var tmpCode = new Array();
    var tmpName = new Array();
    
    $('input[name=targetDeptName]').each(function(i) {
        if(index == i){
            
            if($(this).val() != ""){
                // ID 중복 제거
                tmpName = $.merge($(this).val().split(", "), departName);
                tmpName = $.unique(tmpName.sort());
            }else{
                tmpName = departName.sort();
            }
            
            $(this).attr("value",tmpName.join(", "));
            
        }
    });
    
    $('input[name=targetDeptOidAttr]').each(function(i) {
        if(index == i){
            
            if($(this).val() != ""){
                // ID 중복 제거
                tmpCode = $.merge($(this).val().split(", "), departOid);
                tmpCode = $.unique(tmpCode.sort());
            }else{
                tmpCode = departOid.sort();
            }
            
            $(this).attr("value",tmpCode.join(", "));
            
        }
    });
}



function addDepartment(obj){
	
	deptImgIdx = $(obj).index("img#img_TargetDept");
	
	var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?type=all&mode=m&invokeMethod=addDepartmentAfterFunc";
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'viewMailReceive', 430, 465, opts);

/*     $('input[name=targetDeptName]').each(function(i) {
        if(index == i){
            $(this).attr("value",name);
        }
    });
    
    $('input[name=targetDeptOid]').each(function(i) {
        if(index == i){
            $(this).attr("value",code);
        }
    }); */
}

function addUser(obj) {
	
	var index = $(obj).index("img#img_TargetPeople");
	
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
    var arrObj = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if ( typeof arrObj == "undefined" || arrObj == null ) {
        return;
    }
    
    var userId = "";     // Id
    var userName = "";   
    var userDept = "";
    var userDuty = "";
    
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] - wtuser oid // [1] - people oid // [2] - dept oid
        // [3] - user id    // [4] - name       // [5] - dept name
        // [6] - duty       // [7] - duty code  // [8] - email

        var infoArr = arrObj[i];
        userId = infoArr[1];
        userName = infoArr[4];
        userDept = infoArr[5];
        userDuty = infoArr[6];
    }
    
    var goFlag = true;
    
    $('input[name=targetPeopleOidAttr]').each(function(i) {
    	if($(this).val() == userId){
    		alert("중복된 데이터가 있습니다.("+userName+")");
    		goFlag = false;
        }
    });
    
    if(goFlag){
	    $('input[name=targetPeopleName]').each(function(i) {
	        if(index == i){
	            $(this).attr("value",userName);
	        }
	    });
	    
	    $('input[name=targetPeopleOidAttr]').each(function(i) {
	        if(index == i){
	            $(this).attr("value",userId);
	        }
	    });
	    
	    
	    $('#config tr').each(function(i) {
	    	if(index+1 == i){
	    		$(this).closest('tr').children('td:eq(1)').text(userDept);
	    		$(this).closest('tr').children('td:eq(3)').text(userDuty);
	        }
	    });
    }
}

-->
</script>
<input type="hidden" name="delOid" id="delOid">
<form id="receiveForm" name="receiveForm" method="post" autocomplete="off">
<div class="contents-wrap">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" />종합현황 메일 수신 설정</div>
    <div class="current-location">
        <span><img src="/plm/portal/images/icon_navi.gif" />Admin<img src="/plm/portal/images/icon_navi.gif" />E-MAIL수신설정<img src="/plm/portal/images/icon_navi.gif" />종합현황 메일 수신 설정</span>
    </div>
    <div class="b-space10" style="text-align:right">
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:addRow();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%-- 추가 --%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%-- 저장 --%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:mailSend();" class="btn_blue">메일발송</a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    <div style="width:100%; height:700px; overflow:auto" id="content">
        <table class="table-style-01" cellpadding="0" summary="" id="config">
            <colgroup>
                <col width="5%" />
                <col width="10%" />
                <col width="10%" />
                <col width="5%" />
                <col width="70%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="center bgcol fontb"></td>
                    <td class="center bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                    <td class="center bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "06147") %><%--이름--%></td>
                    <td class="center bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "02714") %><%--직급--%></td>
                    <td class="center bgcol fontb">분석대상</td>
                </tr>
                <c:forEach var="result" items="${resultList}" varStatus="status">
                    <tr>
                        <td class="center"><input type="BUTTON" onclick="deleteRow(this);" value="삭제"><input type="hidden" name=receiveOidAttr value="${result.receiveOid}"></td>
                        <td class="center">${result.dept}<input type="hidden" name=dept value='${result.dept}'></td>
                        <td class="center">${result.targetPeopleName}<input type="hidden" name=targetPeopleName><input type="hidden" name=targetPeopleOidAttr value="${result.targetPeopleOid}">
                        <img style="display:none;" id="img_TargetPeople" src="/plm/portal/images/icon_user.gif" border=0">
                        <img style="display:none;" id="img_TargetPeopleDel" src="/plm/portal/images/icon_delete.gif" border=0">
                        </td>
                        <td class="center">${result.duty}<input type="hidden" name=duty value='${result.duty}'></td>
                        <td class="tdwhiteL">
                            <input type="text" name="targetDeptName" class="txt_fieldRO" style="width: 95%" value='${result.targetDeptName}' readonly> 
                            <input type="hidden" name="targetDeptOidAttr" value='${result.targetDeptOid}'>
                            <a href="javascript:;"><img id="img_TargetDept" src="/plm/portal/images/icon_5.png" border="0" onclick="addDepartment(this); return false;"></a>                           
                            <a href="javascript:;"><img id="img_TargetDeptDel" src="/plm/portal/images/icon_delete.gif" border="0" onclick="delDepartment(this); return false;"></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>