<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript"
    src="/plm/extcore/js/sales/project/salesProject.js"></script>
<script type="text/javascript">


<!--
    $(document).ready(function() {
        $(document).attr('title', "업무프로젝트 등록");
        CalendarUtil.dateInputFormat('planStartDate'); //sop
        CommonUtil.singleSelect('rank', 140); //Rank
        CommonUtil.singleSelect('workType', 140); //workType
        SuggestUtil.bind('USER', 'tempdevUser', 'devUser');
    });
//-->

</script>

<script>

//Project Template 가져오기 시작
function onProjectTemplate() {
    SearchUtil.selectOneWBSTemplate('work','acceptProjectTemplate');
}

function acceptProjectTemplate(objArr) {
    if(objArr.length == 0) {
        return;
    }


    var targetTable = document.getElementById("templatetable");

    if(targetTable.rows.length > 1) {
        targetTable.deleteRow(1);
    }

    var trArr;
    var trArr;
    var i = 0;
    //for(var i = 0; i < objArr[objArr.length-1].length; i++) {
      tableRows = targetTable.rows.length;

      trArr = objArr[objArr.length-1];
      newTr = targetTable.insertRow(tableRows);
      //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<center><a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('templatetable','templateOid','" + trArr.ProjectOid + "');\"><img src=\"/plm/portal/images/b-minus.png\"></a><input type='hidden' name='templateOid' value='" + trArr.ProjectOid + "'>";
      for ( var j=0; j<objArr.length; j++) {
          if(objArr[j]['name'] == 'productType'){
             newTd.innerHTML += '<input type=hidden name=wbsType value='+objArr[j].value+'>';
          }
          if(objArr[j]['name'] == 'category'){
              newTd.innerHTML += '<input type=hidden name=category value='+objArr[j].value+'>';
          }
      }
      
      //Template 명
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerText = trArr.TempName;

      //기간
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL text-center";
      newTd.innerText = trArr.TempDu;

      //등록일
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL text-center";
      newTd.innerText =trArr.TempCreateDate;

      //최종수정일
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL0 text-center";
      newTd.innerText = trArr.TempModifyDate;
    //}

}

function onDeleteTableRow(tableid, chk_name, chk_value) {
    targetTable = document.getElementById(tableid);
    var chkTag = document.all.item(chk_name);

    var chkLen = chkTag.length;
    if(chkLen) {
        for(var i = 0; i < chkLen; i++) {
            if(chkTag[i].value == chk_value) {
                targetTable.deleteRow(i+1);
                return;
            }
        }
    }else {
        if(chkTag.value == chk_value) {
            targetTable.deleteRow(1);
            return;
        }
    }
}


function addRoleMember(name) {
	
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod=acceptRoleMember";
    getOpenWindow2(url,'searchDqmPopup', 820, 710);
    
}

function acceptRoleMember(objArr) {
	
    if(objArr.length == 0) {
        return;
    }

    var displayName = document.getElementById("tempdevUser");
    var keyName = document.getElementById("devUser");
    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
        infoArr = objArr[i];
        displayName.value = infoArr[4];
        keyName.value = infoArr[0];
    }
}
function deleteRoleMember(name) {
    document.getElementById("tempdevUser").value = "";
    document.getElementById("devUser").value = "";
}

window.saveProject = function(){
    var isModify = false;
    
    if('${oid}' != '' ){
        isModify = true;
    }
	
	if(!CommonUtil.checkEsseOk()){ 
        return;
    }
	
	if(!isModify && document.getElementById("templatetable").rows.length < 2){
		alert("WBS를 선택하세요.");
		return;
	}
	
	var category = "";
	
	$("[name='category']").each(function(i){
		category = $(this).val();
	});
	
	if(!isModify && category == ''){
		alert('표쥰 WBS의 category가 선택되지 않았습니다.');
		return;
	}
	
    var confirmMsg = "저장하시겠습니까?";
    var completeMsg = "저장되었습니다.";
    
    if(confirm(confirmMsg)){
        
        var param = $("[name=uploadForm]").serializefiles();
        
        ajaxCallServer("/plm/ext/project/work/saveWorkProject", param, function(data){
            
            alert(completeMsg);
            var oid = data.oid;
            if(isModify){
            	opener.location.reload();	
            }else{
            	openView(oid.replace(/(^\s*)|(\s*$)/gi, ''));	
            }
            self.close();

        });
    }
}

</script>
<form name="uploadForm" method="post" enctype="multipart/form-data">
    <input type="hidden" id="oid" name="oid" value="${oid}">
    <div class="contents-wrap">
        <table style="width: 100%;">
            <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                    <table style="height: 28px;">
                        <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">업무프로젝트 등록</td>
                        </tr>
                    </table>
                </td>
                <td width="136" align="right"><img
                    src="/plm/portal/images/logo_popup.png"></td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space5"></td>
            </tr>
        </table>
        <div class="float-r" style="text-align: right">
            <span class="in-block v-middle">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:saveProject();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><!-- 저장 --></a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="in-block v-middle">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><!-- 취소 --></a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="sub-title-02 b-space15 clear">
            <span class="r-space9"><img
                src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01120")%><!-- 기본정보 -->
        </div>
        <div class="b-space30">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed" border="1">
                <colgroup>
                    <col width="15%" />
                    <col width="35%" />
                    <col width="15%" />
                    <col width="35%" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="tdblueL">구분<span class="red">*</span></td>
                        <td class="tdwhiteL">
                          <ket:select id="workType" name="workType" className="fm_jmp" value="${workType}" codeType="WORKPROJECTTYPE" useOidValue="true" multiple='multiple' esse='true' esseLabel='구분' />
                        </td>
                        <td class="tdblueL">프로젝트 명<span class="red">*</span></td>
                        <td class="tdwhiteL"><input type="text" class="txt_field" style="width: 100%" name="pjtName" id="pjtName" esse='true' value="${pjtName}" esseLabel='프로젝트 명'/></td>
                    </tr>
                    <tr>
                        <td width="150" class="tdblueL">PM<span class="red">*</span></td>
                        <td width="240" class="tdwhiteL">
                            <input type="text" name="tempdevUser" id="tempdevUser" class="txt_field" style="width: 75%" value="${tempdevUser}"> 
                            <input type='hidden' name='devUser' id="devUser" value="${devUser}" esse='true' esseLabel='PM'>
                            <a href="javascript:;" onClick="javascript:addRoleMember('tempdevUser');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                            <a href="javascript:CommonUtil.deleteValue('tempdevUser','devUser');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817","")%><!-- 계획시작일 --><span class="red">*</span></td>
                        <td class="tdwhiteL">
	                      <c:choose>
	                      <c:when test="${oid == null}">
	                      <input type="text" class="txt_field" name="planStartDate" style="width: 30%" value="${planStartDate}" esse='true' esseLabel='계획시작일'/>
                          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('planStartDate');" style="cursor:pointer;cursor:hand;">
	                      </c:when>
	                      <c:otherwise>${planStartDate}</c:otherwise>
	                      </c:choose>
                          
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">Rank<span class="red">*</span></td>
                        <td class="tdwhiteL" colspan='3'>
                          <ket:select id="rank" name="rank" className="fm_jmp" codeType="RANK" useOidValue="true" depthLevel="child" code="RAN1000" multiple='multiple' value="${rank}" esse='true' esseLabel='rank' />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <c:if test="${oid == null}">
        <div class="b-space30">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
	                <td class="font_03">WBS<span class="red">*</span></td>
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
	        
	        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="templatetable">
	            <tr>
	                <td class="tdblueM" width="30"><a href="javascript:onProjectTemplate()"><img src="/plm/portal/images/b-plus.png"></a></td>
	                <td class="tdblueM">WBS</td>
	                <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01111")%><%--기간--%></td>
	                <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "01335")%><%--등록일--%></td>
	                <td class="tdblueM0" width="100"><%=messageService.getString("e3ps.message.ket_message", "02852")%><%--최종수정일--%></td>
	            </tr>
	        </table>    
        </div>
        </c:if>
    </div>
    <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>