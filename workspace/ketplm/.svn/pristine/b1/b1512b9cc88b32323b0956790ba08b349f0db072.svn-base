<%@ page import="java.util.Hashtable
              ,java.util.ArrayList
              ,java.util.Vector
              ,java.util.List
              ,java.util.Map
              ,java.util.HashMap
              ,e3ps.common.util.StringUtil
              ,e3ps.common.util.DateUtil
              ,e3ps.common.util.CommonUtil
              ,e3ps.common.util.KETObjectUtil
              ,e3ps.common.code.NumberCode
              ,e3ps.common.code.NumberCodeHelper
              ,e3ps.ecm.beans.ProdEcrBeans
              ,e3ps.ecm.entity.*
              ,e3ps.common.content.ContentInfo
              ,e3ps.common.content.ContentUtil
              ,e3ps.groupware.company.Department
              ,wt.org.WTUser
              ,wt.fc.QueryResult
              ,wt.fc.PersistenceHelper
              ,wt.query.*
              ,wt.content.ContentHelper
              ,wt.content.ContentHolder
              ,wt.content.ContentItem
              ,wt.session.SessionHelper
              ,wt.content.ApplicationData" %>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){ 

    SuggestUtil.bind('USER', 'relEcaDocWorkerName', 'relEcaDocWorkerOid');
    
    // ECN 완료요청일
    //CalendarUtil.dateInputFormat('completeRequestDate');
    CalendarUtil.dateInputFormat('completeRequestDate', null, {minDate : new Date()});
    
})
</script>
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
      <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03258") %><%--후속활동계획--%></td>
      <!-- td align="right"><%=messageService.getString("e3ps.message.ket_message", "04440") %><%--테이블 좌측상단 '+'을 클릭하여 추가하십시오.--%></td -->
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

<script type="text/javascript">
<!--
//활동 라인 추가
function addRelAct() {
    var arr = new Array();
    var idx = 0;
    rArr = new Array();
    rArr[0] = "";//oid
    rArr[1] = "";//code
    rArr[2] = "";//version
    arr[idx++] = rArr;
    insertRelActLine(arr);
}

//활동 라인추가
function insertRelActLine(objArr) {
    if(objArr.length == 0) {
      return;
    }
    var targetTable = document.getElementById("relActTable");
    
    var trArr;
    var str = "";
    //if( targetTable.rows.length < 1)
    //{
      for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];
        var tableRows = targetTable.rows.length;
        var newTr = targetTable.insertRow(tableRows);
        newTr.onmouseover=function(){relActTable.clickedRowIndex=this.rowIndex};
        var currRow = targetTable.rows.length - 1;
    
        //전체선택 checkbox
        newTd = newTr.insertCell(newTr.cells.length);
        //    newTd.width = "40";
        newTd.className = "tdwhiteM";
        str = "";
        str += "<input type='hidden' name='relEcaDocActivityType' value='4'>";
        str += "<input type='hidden' name='relEcaDocLinkOid' value=''>";
        str += "<input type='hidden' name='moldEcaDocOid' value=''>";
        
        str += "<input type='hidden' name='relEcaDocOid' value=''>";
        str += "<input type='hidden' name='relEcaDocNo' value=''>";
        str += "<input type='hidden' name='relEcaDocPreRev' value=''>";
        str += "<input type='hidden' name='relEcaDocPlanDate' value=''>";
        str += "<input type='hidden' name='relEcaDocActivityComment' value=''>";
        
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
        str = "";
        str += "<select name='docActFlag'>";
        str += "<option value='ACT' selected='selected'><%=messageService.getString("e3ps.message.ket_message", "04120") %><%--활동--%></option>";
        str += "</select>";
        newTd.innerHTML = str;
    
        /* 
        //문서번호
        newTd = newTr.insertCell(newTr.cells.length);
        //    newTd.width = "140";
        newTd.className = "tdwhiteM";
        str = "";
        str += "<input type='hidden' name='relEcaDocOid' value='" + trArr[0] + "'>";
        str += "<input type='text' name='relEcaDocNo' class='txt_fieldCRO' readonly value='" + trArr[1] + "'>";
        str += "&nbsp;<a href=\"javascript:popupRelAct2('relActTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
        str += "&nbsp;<a href=\"javascript:clearRelDoc2('relActTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
        newTd.innerHTML = str;
    
        //Rev
        newTd = newTr.insertCell(newTr.cells.length);
        //    newTd.width = "50";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type='text' name='relEcaDocPreRev' class='txt_fieldCRO' style='width:38px' readonly value='" + trArr[2] + "'>";
        */
        
        // 후속업무
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type='text' name='customName' value='' style='width:100%'>";
        
        //담당자
        newTd = newTr.insertCell(newTr.cells.length);
        //    newTd.width = "130";
        newTd.className = "tdwhiteM";
        str = "";
        str += "<input type='hidden' name='relEcaDocWorkerOid'>";
        str += "<input type='text' name='relEcaDocWorkerName' class='txt_fieldRO' readonly>&nbsp;";
        str += "<a href=\"javascript:popupUser3('relActTable');\"><img src='/plm/portal/images/icon_user.gif' border='0'></a>&nbsp;";
        str += "<a href=\"javascript:clearUser3('relActTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
        newTd.innerHTML = str;
    
        /* 
        //변경예정일
        newTd = newTr.insertCell(newTr.cells.length);
        //    newTd.width = "130";
        newTd.className = "tdwhiteM";
        str = "";
        str += "<input type='text' name='relEcaDocPlanDate' class='txt_field' >";
        // str += "<a href=\"#\" onclick=\"javascript:popupCal('relEcaDocPlanDate','relActTable');\"><img src='/plm/portal/images/icon_6.png' border='0'></a>";
        // str += "<a href=\"javascript:clearCal('relEcaDocPlanDate','relActTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
        str += "<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"relEcaDocPlanDate\");' style='cursor: hand;'>";
        newTd.innerHTML = str;
        */
        
        // 완료요청일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.style.width = "140";
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input id='completeRequestDate"+ currRow +"' name='completeRequestDate' class='txt_field' style='width: 60%;'/>&nbsp;"
                        //+ "<a href=\"#\" onclick=\"javascript:showCal2('completeRequestDate"+ rowIndex +"', 'lfn_feedback_clearDate');\"><img src=\"/plm/portal/images/icon_6.png\"></a>&nbsp;"    
                        + "<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"completeRequestDate\"); lfn_feedback_clearDate("+ currRow +")' style='cursor: hand;'>"
                        ;

        //변경내용
        newTd = newTr.insertCell(newTr.cells.length);
        //    newTd.width = "";
        newTd.className = "tdwhiteL0";
        str = "";
        str += "<input type='text' name='activityTypeDesc' class='txt_field' style='width:100%'>";
        newTd.innerHTML = str;
        
        
        // 완료요청일
        //CalendarUtil.dateInputFormat('completeRequestDate');
        CalendarUtil.dateInputFormat('completeRequestDate', null, {minDate : new Date()});
        
        // 활동 변경계획일
        //CalendarUtil.dateInputFormat('relEcaDocPlanDate');
      }
    //}
    //else
    //{
    //  alert("활동리스트는 1개만 등록할 수 있습니다.")
    //}
    //document.recalc();
}

var popupUser3Pos = 0;
//활동 담당자검색 팝업
function popupUser3(tableId){
	popupUser3Pos = eval(tableId).clickedRowIndex;
	SearchUtil.selectOneUser('','','setRelActUser3');
}

//활동 담당자 검색 팝업 리턴 포맷
function setRelActUser3(objArr, pos) {
    if(objArr.length == 0) {
      return;
    }
    var trArr = objArr[0];

    $("#relActTable").find("[name=relEcaDocWorkerOid]").each(function(i) {
        if(i == (popupUser3Pos - 1)) {
            $(this).val(trArr[0]);
            $("#relActTable").find("[name=relEcaDocWorkerName]").eq(i).val(trArr[4]);
        }
        
    });
    //alert(pos);

}

//담당자 초기화
function clearUser3(tableId){
    var pos = eval(tableId).clickedRowIndex;

    $("#relActTable").find("[name=relEcaDocWorkerOid]").each(function(i) {
        if(i == (pos - 1)) {
            $(this).val('');
            $("#relActTable").find("[name=relEcaDocWorkerName]").eq(i).val('');
        }
        
    });
    
}

//-->
</script>


      <div id="div_scroll4" class="table_border">
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="relActTable">
        <col width='20'><col width='50'><col width='200'><col width='180'><col width='180'><col width='*'>
        <tr>
          <td class="tdblueM"><!-- a href="#" onclick="javascript:addRelAct();"><img src="/plm/portal/images/b-plus.png"></a --></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <!-- td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td -->
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04115") %><%--후속업무--%></td>
                    
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
          <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
        </tr>
      <!-- /table>
      <div style="height:100;width:100%;overflow-x:hidden;overflow-y:auto;" class="table_border">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relActTable">
        <col width='20'><col width='50'><col width='140'><col width='50'><col width='130'><col width='130'><col width='' -->
        
        <%
        int docCnt = -1;
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
                if(!"4".equals(ketMoldECALinkVO.getActivityType())) {
                    continue;
                }
                docCnt++;
        %>
                <tr onMouseOver='relActTable.clickedRowIndex=this.rowIndex'>
                  <td class='tdwhiteM'>
                    
                    <div style="display:none;">
                      <input type='hidden' name='relEcaDocActivityType' value='4'>
                      <input type='hidden' name='relEcaDocLinkOid' value="<%=ketMoldECALinkVO.getRelEcaObjectLinkOid()%>">
                      <input type='hidden' name='moldEcaDocOid' value="<%=ketMoldECALinkVO.getRelEcaOid()%>">
                    
                      <input type='hidden' name='relEcaDocOid' value='<%=ketMoldECALinkVO.getRelEcaObjectOid()%>'>
                      <input type='hidden' name='relEcaDocNo' value='<%=ketMoldECALinkVO.getRelEcaObjectNo()%>'>
                      <input type='hidden' name='relEcaDocPreRev' value='<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>'>
                      <input type='hidden' name='relEcaDocPlanDate' value='<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>'>
                      <input type='hidden' name='relEcaDocActivityComment' value='<%=ketMoldECALinkVO.getRelEcaObjectActivityComment()%>'>
                      
                      <input type='hidden' name='activityBackDesc' value=''>
                    </div>
                    
                    <!-- a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relActTable', 'chkSelectRelEcn', 'chkAllRelDoc', 'deleteRelDocList');"><img src="/plm/portal/images/b-minus.png"></a -->
                    <div style="display:none;"><input type='checkbox' name='chkSelectRelEcn' value='<%=ketMoldECALinkVO.getRelEcaOid() + "^" + ketMoldECALinkVO.getRelEcaObjectLinkOid()%>'></div>
                    
                  </td>
                  <td class='tdwhiteM'>
                    <input type="hidden" name="docActFlag" value="ACT">
                    <%=messageService.getString("e3ps.message.ket_message", "04120") %><%--활동--%>               
                  </td>
                  
                  <!-- td class='tdwhiteM'>
                    <input type='hidden' name='relEcaDocOid' value='<%=ketMoldECALinkVO.getRelEcaObjectOid()%>'>
                    <input type='text' name='relEcaDocNo' class='txt_fieldC' style='width:90' readonly value='<%=ketMoldECALinkVO.getRelEcaObjectNo()%>'>
                    <a href="javascript:popupRelAct2('relActTable');"><img src='/plm/portal/images/icon_5.png' border='0'></a>
                    <a href="javascript:clearRelDoc2('relActTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
                  </td>
                  <td class='tdwhiteM'>
                    <input type='text' name='relEcaDocPreRev' class='txt_fieldC' style='width:38px' readonly value='<%=ketMoldECALinkVO.getRelEcaObjectPreRev()%>'>
                  </td -->
                  <td class="tdwhiteM">
                    <input type="hidden" name="customName" value="<%=ketMoldECALinkVO.getCustomName() %>">
                    <%=ketMoldECALinkVO.getCustomName() %>
                  </td>
                      
                  <td class='tdwhiteM'>
                    <input type='hidden' name='relEcaDocWorkerOid' value='<%=ketMoldECALinkVO.getWorkerId()%>'>
                    <input type='text' name='relEcaDocWorkerName' class='txt_field' value="<%=ketMoldECALinkVO.getWorkerName()%>">
                    <a href="javascript:popupUser3('relActTable');"><img src='/plm/portal/images/icon_user.gif' border='0'></a>
                    <a href="javascript:clearUser3('relActTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
                  </td>
                  
                  <%-- 
                  <td class='tdwhiteM'>
                    <input type='text' name='relEcaDocPlanDate' class='txt_field' value='<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>'>
                    <!-- a href="#" onclick="javascript:popupCal('relEcaDocPlanDate','relActTable');"><img src='/plm/portal/images/icon_6.png' border='0'></a>
                    <a href="javascript:clearCal('relEcaDocPlanDate','relActTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a -->
                    <img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue("relEcaDocPlanDate");' style='cursor: hand;'>
                  </td>
                  --%>
                  <td style="width:110px" class="tdwhiteM">
                    <input id="completeRequestDate<%=docCnt %>" name="completeRequestDate" value="<%=ketMoldECALinkVO.getCompleteRequestDate()%>" class="txt_field" style="width: 60%;"/>
                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('completeRequestDate');" style="cursor: hand;">
                  </td> 
                      
                  <td class='tdwhiteL0'>
                    <input type='text' name='activityTypeDesc' class='txt_field'  style='width:100%' value='<%=ketMoldECALinkVO.getActivityTypeDesc()%>'>
                  </td>
                </tr>
        <%
            }   // for(int i=0; i<size; i++) {
        	
        } 
        
        if(docCnt == -1) {
		%>
		        <tr onMouseOver='relActTable.clickedRowIndex=this.rowIndex'>
		          <td class='tdwhiteM'>
		            
		            <div style="display:none;">
		              <input type='hidden' name='relEcaDocActivityType' value='4'>
		              <input type='hidden' name='relEcaDocLinkOid' value="">
		              <input type='hidden' name='moldEcaDocOid' value="">
		            
		              <input type='hidden' name='relEcaDocOid' value=''>
		              <input type='hidden' name='relEcaDocNo' value=''>
		              <input type='hidden' name='relEcaDocPreRev' value=''>
		              <input type='hidden' name='relEcaDocPlanDate' value=''>
		              <input type='hidden' name='relEcaDocActivityComment' value=''>
		              
		              <input type='hidden' name='activityBackDesc' value=''>
		            </div>
		            
		            <!-- a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relActTable', 'chkSelectRelEcn', 'chkAllRelDoc', 'deleteRelDocList');"><img src="/plm/portal/images/b-minus.png"></a -->
		            <div style="display:none;"><input type='checkbox' name='chkSelectRelEcn' value='DOC_4M_CHANGE'></div>
		            
		          </td>
		          <td class='tdwhiteM'>
		            <input type="hidden" name="docActFlag" value="ACT">
		            <%=messageService.getString("e3ps.message.ket_message", "04120") %><%--활동--%>                     
		          </td>
		          
		          <!-- td class='tdwhiteM'>
		            <input type='hidden' name='relEcaDocOid' value=''>
		            <input type='text' name='relEcaDocNo' class='txt_fieldC' style='width:90' readonly value=''>
		            <a href="javascript:popupRelAct2('relActTable');"><img src='/plm/portal/images/icon_5.png' border='0'></a>
		            <a href="javascript:clearRelDoc2('relActTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
		          </td>
		          <td class='tdwhiteM'>
		            <input type='text' name='relEcaDocPreRev' class='txt_fieldC' style='width:38px' readonly value=''>
		          </td -->
		          <td class="tdwhiteM">
		            <input type="hidden" name="customName" value="<%=messageService.getString("e3ps.message.ket_message", "04900") %><%--4M변경--%>">
		            <%=messageService.getString("e3ps.message.ket_message", "04900") %><%--4M변경--%>  
		          </td>
		              
		          <td class='tdwhiteM'>
		            <input type='hidden' name='relEcaDocWorkerOid' value=''>
		            <input type='text' name='relEcaDocWorkerName' class='txt_field' value="">
		            <a href="javascript:popupUser3('relActTable');"><img src='/plm/portal/images/icon_user.gif' border='0'></a>
		            <a href="javascript:clearUser3('relActTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>
		          </td>
		          
		          <%-- 
		          <td class='tdwhiteM'>
		            <input type='text' name='relEcaDocPlanDate' class='txt_field' value='<%=ketMoldECALinkVO.getRelEcaObjectPlanDate()%>'>
		            <!-- a href="#" onclick="javascript:popupCal('relEcaDocPlanDate','relActTable');"><img src='/plm/portal/images/icon_6.png' border='0'></a>
		            <a href="javascript:clearCal('relEcaDocPlanDate','relActTable');"><img src='/plm/portal/images/icon_delete.gif' border='0'></a -->
		            <img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue("relEcaDocPlanDate");' style='cursor: hand;'>
		          </td>
		          --%>
		          <td style="width:110px" class="tdwhiteM">
		            <input id="completeRequestDate1" name="completeRequestDate" value="" class="txt_field" style="width: 60%;"/>
		            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('completeRequestDate');" style="cursor: hand;">
		          </td> 
		              
		          <td class='tdwhiteL0'>
		            <input type='text' name='activityTypeDesc' class='txt_field'  style='width:100%' value=''>
		          </td>
		        </tr>        
		<%            
		}   
        %>
      </table>
      </div>
    
