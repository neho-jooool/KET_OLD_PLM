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
    //CalendarUtil.dateInputFormat('completeRequestDate', lfn_feedback_showCal);
    CalendarUtil.dateInputFormat('completeRequestDate', lfn_feedback_showCal, {minDate : new Date()});
    
})


$(document).on("change", "[name=docActFlag]", function() {
	var $OBJ = $(this);
    
    var pos = -1;
    $("#relDocTable").find("[name=docActFlag]").each(function(i) {
        if($(this).get(0) == $OBJ.get(0)) {
            pos = i;
            return false;
        }
        
    });
    
    if($OBJ.val() == 'ACT') {
	    $("input[name=relEcaDocNo]:eq("+ pos +")").attr('type', 'hidden');
	    $("input[name=relEcaDocName]:eq("+ pos +")").attr('type', 'hidden');
	    $("input[name=relEcaDocPreRev]:eq("+ pos +")").attr('type', 'hidden');
	    $("div[id=divImgPopupRelDoc]:eq("+ pos +")").css('display', 'none');
	    
	    $("input[name=relEcaDocNo]:eq("+ pos +")").val('');
        $("input[name=relEcaDocName]:eq("+ pos +")").val('');
        $("input[name=relEcaDocPreRev]:eq("+ pos +")").val('');
        
    } else {
    	$("input[name=relEcaDocNo]:eq("+ pos +")").attr('type', 'text');
        $("input[name=relEcaDocName]:eq("+ pos +")").attr('type', 'text');
        $("input[name=relEcaDocPreRev]:eq("+ pos +")").attr('type', 'text');
        $("div[id=divImgPopupRelDoc]:eq("+ pos +")").css('display', 'block');
    }
    
});


$(document).on("change", "[name=relEcaDocWorkerOid]", function() {
    var $OBJ = $(this);
    
    var pos = -1;
    $("#relDocTable").find("[name=relEcaDocWorkerOid]").each(function(i) {
        if($(this).get(0) == $OBJ.get(0)) {
            pos = i;
            return false;
        }
        
    });
    
    // 체크박스 처리
    lfn_check_chkPostAct(pos);
    
});

</script>
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
      <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03258") %><%--후속활동계획--%></td>
      <td align="right"><%=messageService.getString("e3ps.message.ket_message", "04440") %><%--테이블 좌측상단 '+'을 클릭하여 추가하십시오.--%></td>
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

function addOtherDoc()
{
    var targetTable = document.getElementById("relDocTable");

    var tableRows = targetTable.rows.length;
    var newTr = targetTable.insertRow(tableRows);
     
    newTr.height = "27";
    newTr.onmouseover=function(){relDocTable.clickedRowIndex=this.rowIndex};
    var currRow = targetTable.rows.length - 2;
    
    //전체선택 checkbox
    newTd = newTr.insertCell(newTr.cells.length);
    //newTd.style.width = "20px";
    newTd.className = "tdwhiteM";
    str = "";
    //str += "<input type='checkbox' name='chkPostAct' value='DOC_06'>";
    //newTd.innerHTML = str;
    var uniqueTime = lfn_getUniqueTime();
    newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                    //= "<a href=\"#\" onclick=\"javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relDocTable', 'chkPostAct', 'chkEcnAll', 'deleteRelDocList');\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                    + "<input type='checkbox' name='chkPostAct' value='"+ uniqueTime +"' checked='checked' style='display:none;'>"
                    + "<input type='hidden' name='ecaUniqueKey' value='"+ uniqueTime +"'>"
                    ;
        
    // 타입
    newTd = newTr.insertCell(newTr.cells.length);
    //newTd.style.width = "30px";
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<select name='docActFlag' style='width:50px'><option value='DOC'>문서</option><option value='ACT'>활동</option></select>"
                    ;

    //구분(후속업무)
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    //newTd.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>";
    newTd.innerHTML = "<input type='text' name='customName' value='' class='txt_field' style='width:95%' maxlength='100'>";

    //상세구분(내용)
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    str = "";
    str += "<input type='text' name='relEcaDocOtherTypeDesc' class='txt_field' style='width:95%' maxlength='100'>";
    newTd.innerHTML = str;
    
    //담당자
    newTd = newTr.insertCell(newTr.cells.length);
    //newTd.style.width = "110";
    newTd.className = "tdwhiteM";
    str = "";
    str +="<input type='hidden' name='docType'  value='"+ uniqueTime +"'>";
    str +="<input type='hidden' name='relEcaDocOid'  value=''>";
    str +="<input type='hidden' name='relEcaDocWorkerOid' value ='' >";
    str +="<input type='hidden' name='relEcaDocAfterRev' value ='-' >";
    str +="<input type='text' name='relEcaDocWorkerName' class='txt_field' style='width:60%'>";
    str +="&nbsp;<a href='javascript:popupUser();'><img src='/plm/portal/images/icon_user.gif' border='0'></a>";
    str +="&nbsp;<a href='javascript:clearUser();'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
    newTd.innerHTML = str;

    // 완료요청일
    newTd = newTr.insertCell(newTr.cells.length);
    //newTd.style.width = "140";
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input id='completeRequestDate"+ currRow +"' name='completeRequestDate' class='txt_field' style='width: 60%;'/>&nbsp;"
                    //+ "<a href=\"#\" onclick=\"javascript:showCal2('completeRequestDate"+ rowIndex +"', 'lfn_feedback_clearDate');\"><img src=\"/plm/portal/images/icon_6.png\"></a>&nbsp;"    
                    + "<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"completeRequestDate\"); lfn_feedback_clearDate("+ currRow +")' style='cursor: hand;'>"
                    ;
                        
    //문서번호
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    str = "";
    str += "<input type='text' name='relEcaDocNo' class='txt_fieldCRO'  style='width:90%' readonly >";
    newTd.innerHTML = str;

    //문서명
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    str = "";
    str += "<input type='text' name='relEcaDocName' class='txt_fieldRO'  style='width:95%' readonly>";
    newTd.innerHTML = str;

    //문서Rev
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    str = "";
    str += "<input type='text' name='relEcaDocPreRev' class='txt_fieldCRO' style='width:90%' readonly>";
    newTd.innerHTML = str;

    //선택해제
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    str = "";
    str += "<div id='divImgPopupRelDoc' style='display:block;'><a href='javascript:popupRelDoc();'><img src='/plm/portal/images/icon_5.png' border='0'></a>";
    str +="<a href='javascript:clearRelDoc();'>&nbsp;<img src='/plm/portal/images/icon_delete.gif' border='0'></a></div>";
    newTd.innerHTML = str;
    
    SuggestUtil.bind('USER', 'relEcaDocWorkerName', 'relEcaDocWorkerOid');
    
    // Calener field 재설정
    //CalendarUtil.dateInputFormat('completeRequestDate',lfn_feedback_showCal);
    CalendarUtil.dateInputFormat('completeRequestDate', lfn_feedback_showCal, {minDate : new Date()});
    
}
function lfn_getUniqueTime() {
    var time = new Date().getTime();
    while (time == new Date().getTime());
    return new Date().getTime();
}

function deleteDocLine(formName, tableElementId, checkboxName, allCheckName, listVarName)
{
        var body = document.getElementById(tableElementId);
        if (body.rows.length == 0) return;
        var formNameStr = "document." + formName + ".";
        var objChecks = eval(formNameStr + checkboxName);
        var objAllChecks = eval(formNameStr + allCheckName);

        if( isChecked(checkboxName) )
        {
            var listVal = "";
            var objList;
            if(listVarName != "") {
                objList = eval(formNameStr + listVarName);
                listVal = objList.value;
            }

            if (body.rows.length == 1) {
                if (objChecks.checked || objChecks[0].checked) {
                    if(objChecks.checked) {
                        if(listVal == "") {
                            listVal = objChecks.value;
                        } else {
                            listVal += "*" + objChecks.value;
                        }
                    } else if(objChecks[0].checked) {
                        if(listVal == "") {
                            listVal = objChecks[0].value;
                        } else {
                            listVal += "*" + objChecks[0].value;
                        }
                    }
                    body.deleteRow(0);
                }
            } else {
                for (var i = body.rows.length; i > 0; i--) {
                    if (objChecks[i-1].checked) {
                        if( objChecks[i-1].value == 'DOC_06' )
                        {
                            if(listVal == "") {
                                listVal = objChecks[i-1].value;
                            } else {
                                listVal += "*" + objChecks[i-1].value;
                            }
                            body.deleteRow(i - 1);
                        }
                    }
                }
            }
            if (body.rows.length < 1) {
                objAllChecks.checked = false;
            }
            if(listVarName != "") {
                objList.value = listVal;
            }
        }
    }

var popupUserPos = 0;
// 담당자검색 팝업
function popupUser()
{
  var targetTable = document.getElementById('relDocTable');
  var tableRows = targetTable.rows.length;
  popupUserPos = document.getElementById('relDocTable').clickedRowIndex;
  
  tableRows -= 1;
  popupUserPos -= 1;
  
  SearchUtil.selectOneUser('','','setUser');
}

    // 담당자 검색 팝업 리턴 포맷
    function setUser(objArr)
    {
      if(objArr.length == 0) {
          return;
      }
      var inx = popupUserPos;
      var trArr = objArr[0];
      var form = document.forms[0];
    
      if(typeof form.relEcaDocWorkerOid.length == 'undefined') {
          form.relEcaDocWorkerOid.value = trArr[0];
          form.relEcaDocWorkerName.value = trArr[4];
          
      } else {
          form.relEcaDocWorkerOid[inx].value = trArr[0];
          form.relEcaDocWorkerName[inx].value = trArr[4];
          
      }
      
      
      // 체크박스 처리
      lfn_check_chkPostAct(inx);
      
    }

// 담당자 초기화
function clearUser()
{
  var form = document.forms[0];

  var targetTable = document.getElementById('relDocTable');
  var tableRows = targetTable.rows.length;
  var pos = eval('relDocTable').clickedRowIndex;

  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */

  if(typeof form.relEcaDocWorkerOid.length == 'undefined') {
      form.relEcaDocWorkerOid.value = '';
      form.relEcaDocWorkerName.value = '';
      
  } else {
	  form.relEcaDocWorkerOid[pos].value = '';
	  form.relEcaDocWorkerName[pos].value = '';

  }
  
  
  lfn_uncheck_chkPostAct(pos);
}


//완료요청일 리턴    
var POS_SHOWCAL = -1;
function lfn_feedback_showCal(dateStr, obj) {
	//alert("lfn_feedback_showCal("+ dateStr +", "+ obj +")");
	
	// 체크박스 처리
	var i=0;  
	$('[name=completeRequestDate]').each(function(){
	    if($(this)[0] == $(obj)[0]){
	        POS_SHOWCAL = i;
	    }
	    i++;
	});
	
	
	// 체크박스 처리
    lfn_check_chkPostAct(POS_SHOWCAL);
    
	
	POS_SHOWCAL = -1;
}    

//완료요청일 삭제 리턴
function lfn_feedback_clearDate(pos) {
  lfn_uncheck_chkPostAct(pos);
}

//체크박스 체크 처리
function lfn_check_chkPostAct(pos) {
    var form = document.forms[0];
    
    if(typeof form.chkPostAct.length == 'undefined') {
     if(form.relEcaDocWorkerOid.value != '' || form.completeRequestDate.value != '' || (form.docActFlag.value == 'DOC' && form.relEcaDocOid.value != '')) {
       form.chkPostAct.checked = true;
     }
           
    } else {
     if(form.relEcaDocWorkerOid[pos].value != '' || form.completeRequestDate[pos].value != '' || (form.docActFlag[pos].value == 'DOC' && form.relEcaDocOid[pos].value != '')) {
       
         form.chkPostAct[pos].checked = true;   
         
     }
     
    }
}

//체크박스 언체크 처리
function lfn_uncheck_chkPostAct(pos) {
	var form = document.forms[0];
	
	if(form.chkPostAct[pos].disabled){
		return;
	}
	
	if(typeof form.chkPostAct.length == 'undefined') {
	 if(form.relEcaDocWorkerOid.value == '' && form.completeRequestDate.value == '' && (form.docActFlag.value != 'DOC' || (form.docActFlag.value == 'DOC' && form.relEcaDocOid.value == ''))) {
	   form.chkPostAct.checked = false;
	 }
	       
	} else {
	 if(form.relEcaDocWorkerOid[pos].value == '' && form.completeRequestDate[pos].value == '' && (form.docActFlag[pos].value != 'DOC' || (form.docActFlag[pos].value == 'DOC' && form.relEcaDocOid[pos].value == ''))) {
	   
	     if(form.chkPostAct[pos].style.display == 'none') {
	         // 추가하여 생성된 ECN은 체크박스 언체크 처리에서 제외한다.
	         
	     } else {
	         form.chkPostAct[pos].checked = false;   
	     }
	   
	 }
	 
	}
}

var popupRelPos = 0;
//문서 검색 팝업 호출
function popupRelDoc() {

  var targetTable = document.getElementById('relDocTable');
  var tableRows = targetTable.rows.length;
  popupRelPos = document.getElementById('relDocTable').clickedRowIndex;

  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  var url = "/plm/jsp/dms/SearchDocumentPop.jsp?method=checkDocAjax&mode=one";
  var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=800,height=600";
  
  window.open(url,'popupRelDoc',opts);
  
}
    
    function checkDocAjax(objArr){
      var trArr = objArr[0];
      var url = "/plm/jsp/ecm/CheckDocAjaxAction.jsp?oid="+trArr[0]+"&no="+trArr[1]+"&name="+encodeURIComponent(trArr[2])+"&ver="+trArr[3]+"&inx=" + popupRelPos;
      callServer(url, checkDocResult);
    }
    
    window.getTagText = function(xmlDoc){
        return xmlDoc.textContent || xmlDoc.text || '';
    }
    
    function checkDocResult(req) {
      showProcessing();
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
    
      
      hideProcessing();
      if ( flag == 'true'){
          alert("<%=messageService.getString("e3ps.message.ket_message", "01862") %><%--설계변경이 진행중인 문서입니다--%>");
          return;
      }
    
      
       setRelDoc(oid, no, name, ver, inx);
    }

//문서 검색 팝업 리턴 세팅
function setRelDoc(oid, no, name, ver, inx)
{
  if(oid.length == 0)
  {
      return;
  }

  var form = document.forms[0];

  form.relEcaDocOid[inx].value = oid;
  form.relEcaDocNo[inx].value = no;
  form.relEcaDocName[inx].value = name;
  form.relEcaDocPreRev[inx].value = ver;
  

  // 체크박스 처리
  lfn_check_chkPostAct(inx);
  
}

function clearRelDoc()
{
  var targetTable = document.getElementById('relDocTable');
  var tableRows = targetTable.rows.length;
  var inx = eval('relDocTable').clickedRowIndex;

  tableRows -= 1;
  inx -= 1;
  /*
  alert(tableRows);
  alert(inx);
  */
  
  var form = document.forms[0];

  form.relEcaDocOid[inx].value = "";
  form.relEcaDocNo[inx].value = "";
  form.relEcaDocName[inx].value = "";
  form.relEcaDocPreRev[inx].value = "";
  
  
  lfn_uncheck_chkPostAct(inx);
}


// 유효성 검사
function checkDoc()
{
    var rtn = true;
    var docTypeList = document.getElementsByName("docType");
    var docTypeListLength = docTypeList.length;
    if(docTypeListLength == 1) {
        if(document.forms[0].chkPostAct.checked) {
            <%-- 
            if( docTypeList[ inx ].value != 'DOC_06' )
            {
                if( document.forms[0].relEcaDocWorkerOid.value == '' && document.forms[0].relEcaDocOid.value != '' )
                {
                    alert( '<%=messageService.getString("e3ps.message.ket_message", "03256") %>후속활동 담당자를 입력하세요' );
                    rtn = false;
                    return rtn;
                }
                else if( document.forms[0].relEcaDocWorkerOid.value != '' && document.forms[0].relEcaDocOid.value == '' )
                {
                    alert( '<%=messageService.getString("e3ps.message.ket_message", "03257") %>후속활동 문서를 입력하세요' );
                    rtn = false;
                    return rtn;
                }
            }
            else
            {
                if( document.forms[0].relEcaDocOtherTypeDesc.value == '' &&  document.forms[0].relEcaDocWorkerOid.value != ''  )
                {
                    alert( '<%=messageService.getString("e3ps.message.ket_message", "01153") %>기타활동의 상세구분을 입력하세요' );
                    rtn = false;
                    return rtn;
                }
                else if( document.forms[0].relEcaDocOtherTypeDesc.value != '' && document.forms[0].relEcaDocWorkerOid.value == '' )
                {
                    alert( '<%=messageService.getString("e3ps.message.ket_message", "01145") %>기타 후속활동 담당자를 입력하세요' );
                    rtn = false;
                    return rtn;
                }
            }
            --%>

            if( document.forms[0].relEcaDocWorkerOid.value == '' && document.forms[0].relEcaDocOid.value != '' )
            {
                alert( '<%=messageService.getString("e3ps.message.ket_message", "03256") %><%-- 후속활동 담당자를 입력하세요 --%>' );
                rtn = false;
                return rtn;
            }
            else if( document.forms[0].docActFlag.value == 'DOC' && document.forms[0].relEcaDocWorkerOid.value != '' && document.forms[0].relEcaDocOid.value == '' )
            {
                alert( '<%=messageService.getString("e3ps.message.ket_message", "03257") %><%-- 후속활동 문서를 입력하세요 --%>' );
                rtn = false;
                return rtn;
            }
            
        }
        
    } else {
        for( var inx=0; inx < docTypeListLength ; inx++ )
        {
            if(document.forms[0].chkPostAct[inx].checked) {
                <%-- 
                if( docTypeList[ inx ].value != 'DOC_06' )
                {
                    if( document.forms[0].relEcaDocWorkerOid[inx].value == '' && document.forms[0].relEcaDocOid[inx].value != '' )
                    {
                        alert( '<%=messageService.getString("e3ps.message.ket_message", "03256") %>후속활동 담당자를 입력하세요' );
                        rtn = false;
                        return rtn;
                    }
                    else if( document.forms[0].relEcaDocWorkerOid[inx].value != '' && document.forms[0].relEcaDocOid[inx].value == '' )
                    {
                        alert( '<%=messageService.getString("e3ps.message.ket_message", "03257") %>후속활동 문서를 입력하세요' );
                        rtn = false;
                        return rtn;
                    }
                }
                else
                {
                    if( document.forms[0].relEcaDocOtherTypeDesc[inx].value == '' &&  document.forms[0].relEcaDocWorkerOid[inx].value != ''  )
                    {
                        alert( '<%=messageService.getString("e3ps.message.ket_message", "01153") %>기타활동의 상세구분을 입력하세요' );
                        rtn = false;
                        return rtn;
                    }
                    else if( document.forms[0].relEcaDocOtherTypeDesc[inx].value != '' && document.forms[0].relEcaDocWorkerOid[inx].value == '' )
                    {
                        alert( '<%=messageService.getString("e3ps.message.ket_message", "01145") %>기타 후속활동 담당자를 입력하세요' );
                        rtn = false;
                        return rtn;
                    }
                }
                --%>
                
                if( document.forms[0].relEcaDocWorkerOid[inx].value == '' && document.forms[0].relEcaDocOid[inx].value != '' )
                {
                    alert( '<%=messageService.getString("e3ps.message.ket_message", "03256") %><%-- 후속활동 담당자를 입력하세요 --%>' );
                    rtn = false;
                    return rtn;
                }
                else if( document.forms[0].docActFlag[inx].value == 'DOC' && document.forms[0].relEcaDocWorkerOid[inx].value != '' && document.forms[0].relEcaDocOid[inx].value == '' )
                {
                    alert( '<%=messageService.getString("e3ps.message.ket_message", "03257") %><%-- 후속활동 문서를 입력하세요 --%>' );
                    rtn = false;
                    return rtn;
                }
                
            }
        }

    }
    
    return rtn; 
}

//ECN 정보 컨버팅
function setDocList()
{
     
    var chkPostAct = document.getElementsByName("chkPostAct");
    if(chkPostAct == null || typeof chkPostAct == 'undefined') return "";
    
    var refDocStr = "";
    var chkPostActLength = (chkPostAct != null) ? chkPostAct.length : 0;
    if(chkPostActLength == 1) {
        if( chkPostAct.checked )
        {
            if( eval("document.forms[0].docType").value == '' )
            {
                refDocStr +="-";
            }
            else
            {
                refDocStr += eval("document.forms[0].docType").value;
            }
            refDocStr += "|";
    
            if( eval("document.forms[0].relEcaDocOid").value == '' )
            {
                refDocStr +="-";
            }
            else
            {
                refDocStr += eval("document.forms[0].relEcaDocOid").value;
            }
            refDocStr += "|";
    
            if( eval("document.forms[0].relEcaDocPreRev").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].relEcaDocPreRev").value;
            }
            refDocStr += "|";
    
            // 상세구분(내용)
            if( eval("document.forms[0].relEcaDocOtherTypeDesc").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].relEcaDocOtherTypeDesc").value;
            }
            refDocStr += "|";
    
            if( eval("document.forms[0].relEcaDocAfterRev").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].relEcaDocAfterRev").value;
            }
            refDocStr += "|";
    
            if( eval("document.forms[0].relEcaDocWorkerOid").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].relEcaDocWorkerOid").value;
            }
            refDocStr += "|";
    
            // 타입
            if( eval("document.forms[0].docActFlag").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].docActFlag").value;
            }
            refDocStr += "|";

            // 사용자 입력 후속업무
            if( eval("document.forms[0].customName").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].customName").value;
            }
            refDocStr += "|";
            
            // 완료요청일
            if( eval("document.forms[0].completeRequestDate").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].completeRequestDate").value;
            }
    
            refDocStr += "↕";
        }
        
    } else {
        for( var inx=0; inx < chkPostActLength ; inx++ )
        {
            if( chkPostAct[ inx ].checked )
            {
                if( eval("document.forms[0].docType["+inx+"]").value == '' )
                {
                    refDocStr +="-";
                }
                else
                {
                    refDocStr += eval("document.forms[0].docType["+inx+"]").value;
                }
                refDocStr += "|";
        
                if( eval("document.forms[0].relEcaDocOid["+inx+"]").value == '' )
                {
                    refDocStr +="-";
                }
                else
                {
                    refDocStr += eval("document.forms[0].relEcaDocOid["+inx+"]").value;
                }
                refDocStr += "|";
        
                if( eval("document.forms[0].relEcaDocPreRev["+inx+"]").value == '' )
                {
                    refDocStr += "-";
                }
                else
                {
                    refDocStr += eval("document.forms[0].relEcaDocPreRev["+inx+"]").value;
                }
                refDocStr += "|";
        
                // 상세구분(내용)
                if( eval("document.forms[0].relEcaDocOtherTypeDesc["+inx+"]").value == '' )
                {
                    refDocStr += "-";
                }
                else
                {
                    refDocStr += eval("document.forms[0].relEcaDocOtherTypeDesc["+inx+"]").value;
                }
                refDocStr += "|";
        
                if( eval("document.forms[0].relEcaDocAfterRev["+inx+"]").value == '' )
                {
                    refDocStr += "-";
                }
                else
                {
                    refDocStr += eval("document.forms[0].relEcaDocAfterRev["+inx+"]").value;
                }
                refDocStr += "|";
        
                if( eval("document.forms[0].relEcaDocWorkerOid["+inx+"]").value == '' )
                {
                    refDocStr += "-";
                }
                else
                {
                    refDocStr += eval("document.forms[0].relEcaDocWorkerOid["+inx+"]").value;
                }
                refDocStr += "|";
        
                // 타입
                if( eval("document.forms[0].docActFlag["+inx+"]").value == '' )
                {
                    refDocStr += "-";
                }
                else
                {
                    refDocStr += eval("document.forms[0].docActFlag["+inx+"]").value;
                }
                refDocStr += "|";
                
                // 사용자 입력 후속업무
                if( eval("document.forms[0].customName["+inx+"]").value == '' )
                {
                    refDocStr += "-";
                }
                else
                {
                    refDocStr += eval("document.forms[0].customName["+inx+"]").value;
                }
                refDocStr += "|";
                
                // 완료요청일
                if( eval("document.forms[0].completeRequestDate["+inx+"]").value == '' )
                {
                    refDocStr += "-";
                }
                else
                {
                    refDocStr += eval("document.forms[0].completeRequestDate["+inx+"]").value;
                }
        
                refDocStr += "↕";
            }
        }
    
    }
    
    return refDocStr;
}

//-->
</script>
<!-- div id="div_scroll5" style="height:189;width:100%;overflow-x:auto;overflow-y:auto;"  -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="relDocTable">
    <tr class="">

                    <td style="width:20px" class="tdblueM"><a href="#" onclick="javascript:addOtherDoc();"><img src="/plm/portal/images/b-plus.png"></a></td>
                    <td style="width:50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--Type--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04115") %><%--후속업무--%></td>
                    <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "내용") %><%--내용--%></td>
                    <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                    <td style="width:110px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                    <td width="161" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                    <td width="45"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                    <td width="45"  class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01824") %><%--선택해제--%></td>

    </tr>
    <!-- tr>
      <td>
            <table border="0" cellspacing="0" cellpadding="0" width="100%"  id="relDocTable" style="table-layout:fixed" -->
            <%
            // ECR에 저장된 ECN 템플릿 리스트
            String ecr_oid = (String) ecrHash.get("ecr_oid");
            wt.change2.WTChangeRequest2 ecr = (wt.change2.WTChangeRequest2) CommonUtil.getObject(ecr_oid);
            
            // ECN
            KETChangeNotice ecn = null;
            // Link 처리
            KETEcrEcnLink ketEcrEcnLink = null;
            // ECR 로 찾는다.
            QueryResult qr = (ecr != null) ? PersistenceHelper.manager.navigate(ecr, "ecn", KETEcrEcnLink.class, false) : null;
            if (qr != null && qr.hasMoreElements()) {    // while (qr.hasMoreElements()) {
                ketEcrEcnLink = (KETEcrEcnLink) qr.nextElement();
                ecn = ketEcrEcnLink.getEcn();

            }
            
            ArrayList ecaList = null;
            if(PersistenceHelper.isPersistent(ecn)) {
                try {
                    QueryResult result = PersistenceHelper.manager.navigate(ecn, "eca", KETEcnEcaLink.class, false);
                    while(result.hasMoreElements()) {
                        KETEcnEcaLink ketEcnEcaLink = (KETEcnEcaLink) result.nextElement();
                        // ECA
                        KETChangeActivity eca = ketEcnEcaLink.getEca();
                        
                        if(ecaList == null) ecaList = new ArrayList();
                        ecaList.add(eca);
                    
                    }
                    
                } catch(Exception e) {
                    // Do nothing..!!
                }
            }
            
            
            int rowIndex = 0;
            
            // 기준정보
            HashMap map = new HashMap();
            map.put("type", "PRODECODOCTYPE");
            map.put("isParent", "false");
            QuerySpec qs = NumberCodeHelper.getCodeQuerySpec(map);
            QueryResult result =  PersistenceHelper.manager.find(qs);
            
            
            while(result.hasMoreElements()) {
                Object[] obj = (Object[])result.nextElement();
                
                // 기준정보
                NumberCode numberCode = (NumberCode)obj[0];
                boolean isDisabled = numberCode.isDisabled();
                if(!isDisabled) {
    
                    // NumberCode
                    String numberCodeOid = CommonUtil.getOIDString(numberCode);
                    
                    String chkPostAct = numberCodeOid;
                    String ecaUniqueKey = numberCodeOid;
                    String docActFlag = StringUtils.stripToEmpty(numberCode.getDsCode());
                    if(docActFlag.equalsIgnoreCase("")) docActFlag = "DOC";
                    String codeName = StringUtils.stripToEmpty(numberCode.getName());
                    
                    String numberCodeCode = StringUtils.stripToEmpty(numberCode.getCode());
                    
                    // 담당자
                    String relEcaDocWorkerOid = "";
                    String relEcaDocWorkerName = "";
                    // 완료요청일
                    String completeRequestDate = "";
                    
                    String checked = "";
                    boolean isAdded = false;
                    
                    int ecaListSize = (ecaList != null) ? ecaList.size() : 0;
                    for (int i=0; i < ecaListSize; i++) {
                        
                        // ECR
                        KETChangeActivity eca = (KETChangeActivity) ecaList.get(i);
                        NumberCode activity = eca.getActivity();
                        
                        String activityOid = CommonUtil.getOIDString(activity);
                        String activityCode = (activity != null) ? activity.getCode() : null;
                        if(activityCode != null && activityCode.equals(numberCodeCode)) {
                            
                            checked = "checked=\"checked\"";
                            isAdded = true;
                                
                            // 담당자
                            WTUser charge = eca.getCharge();
                            relEcaDocWorkerOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
                            relEcaDocWorkerName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
                            
                            // 완료요청일
                            completeRequestDate = DateUtil.getTimeFormat(eca.getCompleteRequestDate(), "yyyy-MM-dd"); 
                            
                            
                            ecaList.remove(i);
                            break;
                            
                        }
                    }
                    
                    
                    // 저장한 ECN
                    // 상세구분(내용)
                    String activity_type_desc = "";
                    // KETProdChangeActitivy OID
                    String eca_obj_oid = "";
                    // 문서
                    String eca_obj_after_rev = "";
                    String eca_obj_no = "";
                    String eca_obj_name = "";
                    String eca_obj_pre_rev = "";
                    if(!isAdded) {
                        int docListSize = (docList != null) ? docList.size() : 0;
                        for( int docCnt=0; docCnt < docListSize; docCnt++ ) {
                            
                            Hashtable<String, String> docHash = docList.get(docCnt);
                            
                            String activity_type_name = docHash.get("activity_type_name");
                            if( codeName.equals(activity_type_name) ) {
                            
                                checked = "checked=\"checked\"";
                                isAdded = true;
                                        
                                // 담당자
                                relEcaDocWorkerOid = docHash.get("worker_id");
                                relEcaDocWorkerName = docHash.get("worker_name");
                                
                                // 완료요청일
                                completeRequestDate = docHash.get("complete_request_date");
                                         
                                // 상세구분(내용)
                                activity_type_desc = docHash.get("activity_type_desc");
                                // KETProjectDocument OID
                                eca_obj_oid = docHash.get("eca_obj_oid");
                                // 문서
                                eca_obj_after_rev = docHash.get("eca_obj_after_rev");
                                eca_obj_no = docHash.get("eca_obj_no");
                                eca_obj_name = docHash.get("eca_obj_name");
                                eca_obj_pre_rev = docHash.get("eca_obj_pre_rev");
                                
                                
                                docList.remove(docCnt);
                                break;
                                        
                            }    // if( docType.get("name").equals(docHash.get("activity_type_name")) )   
                        }   // for( int docCnt=0; docCnt < docList.size(); docCnt++ )
                    }
                    
            %>
                <tr height="27" onMouseOver='relDocTable.clickedRowIndex=this.rowIndex'>
                    <td class="tdwhiteM">
                      <!-- a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relDocTable', 'chkPostAct', 'chkEcnAll', 'deleteRelDocList');"><img src="/plm/portal/images/b-minus.png"></a -->
                      <input type="checkbox" name="chkPostAct" value="<%=chkPostAct %>" <%=checked %> <%if("설계가이드".equals(codeName) || "설계검증체크시트".equals(codeName) || "설계원가의뢰".equals(codeName) || "설계원가보고서".equals(codeName) || "사양변경 식별표기 지시서".equals(codeName)){ %> disabled=true <%} %> >
                      <input type="hidden" name="ecaUniqueKey" value="<%=ecaUniqueKey %>"> 
                    </td>
                    <%
                    String docActFlagDisplayName = "";
                    if(docActFlag.equals("") || docActFlag.equalsIgnoreCase("DOC")) docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04119");
                    else docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04120");
                    %>
                    <td class="tdwhiteM"><%=docActFlagDisplayName %><input type="hidden" name="docActFlag" value="<%=docActFlag %>"></td>
                    <td width="100" class="tdwhiteM"><%=codeName %><input type="hidden" name="customName" value="<%=codeName %>"></td>
                    
                    <td width="110" class="tdwhiteM"><input type="text" name='relEcaDocOtherTypeDesc' value="<%=activity_type_desc %>" class="txt_field"  style="width:95%" maxlength='100'></td>
                    
                    <td width="110" class="tdwhiteM">
                        <input type='hidden' name='docType'  value="<%=numberCodeCode %>">
                        <input type='hidden' name='relEcaDocOid' value="<%=eca_obj_oid %>">
                        <input type='hidden' name='relEcaDocWorkerOid' value="<%=relEcaDocWorkerOid %>">
                        <input type='hidden' name='relEcaDocAfterRev' value="<%=eca_obj_after_rev %>">
                        <input type="text" name="relEcaDocWorkerName" value="<%=relEcaDocWorkerName %>" class="txt_field"  style="width:60%">
                        <a href="javascript:popupUser();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                        <a href="javascript:clearUser();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td style="width:110px" class="tdwhiteM">
                      <input id="completeRequestDate<%=rowIndex %>" name="completeRequestDate" value="<%=completeRequestDate %>" class="txt_field" style="width: 60%;"/>
                      <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('completeRequestDate'); lfn_feedback_clearDate(<%=rowIndex %>)" style="cursor: hand;">
                    </td>   
                    
                    <%
                    if(docActFlag.equals("") || docActFlag.equalsIgnoreCase("DOC")) {
                    %>                   
                    <td width="100" class="tdwhiteM"><input type="text" name='relEcaDocNo' value="<%=eca_obj_no %>" class="txt_fieldCRO"  style="width:90%" readonly></td>
                    <td width="161" class="tdwhiteM"><input type="text" name='relEcaDocName' value="<%=eca_obj_name %>" class="txt_fieldRO"  style="width:95%" readonly></td>
                    <td width="45" class="tdwhiteM"><input type='text' name='relEcaDocPreRev' value="<%=eca_obj_pre_rev %>" class='txt_fieldCRO' style='width:90%' readonly></td>
                    <td width="45" class="tdwhiteM"><div id="divImgPopupRelDoc" style="display:block;"><a href="javascript:popupRelDoc();"><img src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:clearRelDoc();">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a></div></td>
                    <%
                    } else {
                    %>
                    <td width="100" class="tdwhiteM"><input type="hidden" name='relEcaDocNo' value="<%=eca_obj_no %>" ></td>
                    <td width="161" class="tdwhiteM"><input type="hidden" name='relEcaDocName' value="<%=eca_obj_name %>" ></td>
                    <td width="45" class="tdwhiteM"><input type='hidden' name='relEcaDocPreRev' value="<%=eca_obj_pre_rev %>" ></td>
                    <td width="45" class="tdwhiteM"><div id="divImgPopupRelDoc" style="display:none;"><a href="javascript:popupRelDoc();"><img src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:clearRelDoc();">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a></div></td>
                    <%
                    }
                    %>
                    
                </tr>
            <%
                    rowIndex++;
            
                }  // if(!isDisabled) {
                
            }   // while(result.hasMoreElements()) {
            
            
            int ecaListSize = (ecaList != null) ? ecaList.size() : 0;
            for (int i=0; i < ecaListSize; i++) {
                // ECA
                KETChangeActivity eca = (KETChangeActivity) ecaList.get(i);

                String chkPostAct = "";
                String ecaUniqueKey = "";
                String customCode = "";
                String docActFlag = "";
                String customName = "";
                
                // 기준정보에 있을 경우
                NumberCode activity = eca.getActivity();
                if(PersistenceHelper.isPersistent(activity)) {
                    chkPostAct = activity.getCode();
                    ecaUniqueKey = activity.getCode();
                    customCode = activity.getCode();
                    
                    // 타입
                    docActFlag = StringUtils.stripToEmpty(activity.getDsCode());
                    // 후속업무
                    customName = HtmlUtils.htmlEscape(StringUtils.stripToEmpty(activity.getName()));
                } 
                // 사용자 입력일 경우
                else {
                    chkPostAct = eca.getCustomCode();
                    ecaUniqueKey = eca.getCustomCode();
                    customCode = eca.getCustomCode();
                    
                    // 타입
                    docActFlag = StringUtils.stripToEmpty(eca.getCustomType());
                    // 후속업무
                    customName = HtmlUtils.htmlEscape(StringUtils.stripToEmpty(eca.getCustomName()));
                }
                                    
                //String checked = "checked=\"checked\"";
                  
                // 담당자
                WTUser charge = eca.getCharge();
                String relEcaDocWorkerOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
                String relEcaDocWorkerName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
                    
                // 완료요청일
                String completeRequestDate = DateUtil.getTimeFormat(eca.getCompleteRequestDate(), "yyyy-MM-dd"); 
            %>
                    <tr height="27" onMouseOver='relDocTable.clickedRowIndex=this.rowIndex'>
                      <td class="tdwhiteM">
                        <a href="#" onclick="javascript:$(this).parent().parent().remove();"><img src="/plm/portal/images/b-minus.png"></a>
                        <input type="checkbox" name="chkPostAct" value="<%=chkPostAct %>" checked="checked" style="display:none;">
                        <input type="hidden" name="ecaUniqueKey" value="<%=ecaUniqueKey %>">
                      </td>
                      <td class="tdwhiteM">
                          <select name="docActFlag">
                            <%
                            String docSelected = "";
                            String actSelected = "";
                            if(docActFlag.equals("") || docActFlag.equalsIgnoreCase("DOC")) docSelected = "selected=\"selected\"";
                            else actSelected = "selected=\"selected\"";
                            %>
                            <option value="DOC" <%=docSelected %>><%=messageService.getString("e3ps.message.ket_message", "04119") %><%--문서--%></option>
                            <option value="ACT" <%=actSelected %>><%=messageService.getString("e3ps.message.ket_message", "04120") %><%--활동--%></option>
                          </select>
                      </td>
                      <td class="tdwhiteM"><input type="text" name="customName" value="<%=customName %>" class='txt_field' style='width:95%' maxlength='100'></td>
                      
                      <td width="110" class="tdwhiteM">
                        <input type="text" name='relEcaDocOtherTypeDesc' class="txt_field"  style="width:95%" maxlength='100'>
                      </td>
                      
                      <td width="110" class="tdwhiteM">
                        <input type="hidden" name="docType" value="<%=customCode %>">
                        <input type='hidden' name='relEcaDocOid' >
                        <input type="hidden" name="relEcaDocWorkerOid" value="<%=relEcaDocWorkerOid %>" >
                        <input type='hidden' name='relEcaDocAfterRev' value='-'>
                        <input type="text" name="relEcaDocWorkerName" value="<%=relEcaDocWorkerName %>" class="txt_fieldRO" style="width:60%" onfocus="this.blur();" readonly>&nbsp;
                        <a href="javascript:popupUser();"><img src="/plm/portal/images/icon_user.gif" border="0"></a><a href="javascript:clearUser();">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </td>
                      <td style="width:110px" class="tdwhiteM">
                        <input id="completeRequestDate<%=rowIndex %>" name="completeRequestDate" value="<%=completeRequestDate %>" class="txt_field" style="width: 60%;"/>
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('completeRequestDate'); lfn_feedback_clearDate(<%=rowIndex %>)" style="cursor: hand;">
                      </td> 
                      
                      <%
                      if(docActFlag.equals("") || docActFlag.equalsIgnoreCase("DOC")) {
                      %>                   
                      <td width="100" class="tdwhiteM"><input type="text" name='relEcaDocNo' class="txt_fieldCRO"  style="width:90%" readonly></td>
                      <td width="161" class="tdwhiteM"><input type="text" name='relEcaDocName' class="txt_fieldRO"  style="width:95%" readonly></td>
                      <td width="45" class="tdwhiteM"><input type='text' name='relEcaDocPreRev' class='txt_fieldCRO' style='width:90%' readonly></td>
                      <td width="45" class="tdwhiteM"><div id="divImgPopupRelDoc" style="display:block;"><a href="javascript:popupRelDoc();"><img src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:clearRelDoc();">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a></div></td>
                      <%
                      } else {
                      %>
                      <td width="100" class="tdwhiteM"><input type="hidden" name='relEcaDocNo' ></td>
                      <td width="161" class="tdwhiteM"><input type="hidden" name='relEcaDocName' ></td>
                      <td width="45" class="tdwhiteM"><input type='hidden' name='relEcaDocPreRev' ></td>
                      <td width="45" class="tdwhiteM"><div id="divImgPopupRelDoc" style="display:none;"><a href="javascript:popupRelDoc();"><img src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:clearRelDoc();">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a></div></td>
                      <%
                      }
                      %>
                      
                    </tr>
            <%
                rowIndex++;
          
            }                   
            %>                  
              
            <%
            // 사용자 입력한 ECN
            int docListSize = (docList != null) ? docList.size() : 0;
            for( int docCnt=0; docCnt < docListSize; docCnt++ )
            {
                Hashtable<String, String> docHash = docList.get(docCnt);

            %>
            <tr height="27" onMouseOver='relDocTable.clickedRowIndex=this.rowIndex'>
              <td class="tdwhiteM">
                <a href="#" onclick="javascript:$(this).parent().parent().remove();"><img src="/plm/portal/images/b-minus.png"></a>
                <input type="checkbox" name="chkPostAct" value='<%=docHash.get("code") %>' checked="checked" style="display:none">
                <input type="hidden" name="ecaUniqueKey" value="<%=docHash.get("code") %>"> 
              </td>
                  
              <%
              String activity_type = StringUtils.stripToEmpty(docHash.get("activity_type"));
              String docActFlag = (activity_type.equals("") || activity_type.equalsIgnoreCase("3")) ? "DOC" : "ACT";
              String docActFlagDisplayName = "";
              if(docActFlag.equals("") || docActFlag.equalsIgnoreCase("DOC")) docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04119");
              else docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04120");
              %>
              <td class="tdwhiteM"><%=docActFlagDisplayName %><input type="hidden" name="docActFlag" value="<%=docActFlag %>"></td>
                  
              <td width="100" class="tdwhiteM"><input type="text" name="customName" value="<%=docHash.get("activity_type_name") %>" class='txt_field' style='width:95%' maxlength='100'></td>
              <td width="110" class="tdwhiteM">
                <input type="text" name='relEcaDocOtherTypeDesc' class="txt_field"  style="width:95%" value='<%=docHash.get("activity_type_desc") %>' maxlength='100'>
              </td>
              <td width="110" class="tdwhiteM">
                <input type='hidden' name='docType'  value='<%=docHash.get("obj_type") %>'>
                <input type='hidden' name='relEcaDocOid'  value='<%=docHash.get("eca_obj_oid") %>' >
                <input type='hidden' name='relEcaDocWorkerOid'  value='<%=docHash.get("worker_id") %>' >
                <input type='hidden' name='relEcaDocAfterRev' value='<%=StringUtil.checkReplaceStr(docHash.get("eca_obj_after_rev"),"-")  %>'>
                <input type="text" name="relEcaDocWorkerName"  value='<%=docHash.get("worker_name") %>'  class="txt_field"  style="width:60%">&nbsp;
                <a href="javascript:popupUser();"><img src="/plm/portal/images/icon_user.gif" border="0"></a><a href="javascript:clearUser();">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a>
              </td>
                  
              <%
              String completeRequestDate = StringUtils.stripToEmpty(docHash.get("complete_request_date"));
              %>
              <td style="width:110px" class="tdwhiteM">
                <input id="completeRequestDate<%=rowIndex %>" name="completeRequestDate" value="<%=completeRequestDate %>" class="txt_field" style="width: 60%;"/>
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('completeRequestDate'); lfn_feedback_clearDate(<%=rowIndex %>)" style="cursor: hand;">
              </td>                      
              
              <%
              if(docActFlag.equals("") || docActFlag.equalsIgnoreCase("DOC")) {
              %>    
              <td width="100" class="tdwhiteM"><input type="text" name='relEcaDocNo'   value='<%=docHash.get("eca_obj_no") %>'  class="txt_fieldCRO"  style="width:90%" readonly></td>
              <td width="161" class="tdwhiteM"><input type="text" name='relEcaDocName'  value="<%=docHash.get("eca_obj_name") %>" class="txt_fieldRO"  style="width:95%" readonly></td>
              <td width="45" class="tdwhiteM"><input type='text' name='relEcaDocPreRev'  value='<%=docHash.get("eca_obj_pre_rev") %>'  class='txt_fieldCRO' style='width:90%' readonly></td>
              <td width="45" class="tdwhiteM"><div id="divImgPopupRelDoc" style="display:block;"><a href="javascript:popupRelDoc();"><img src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:clearRelDoc();">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a></div></td>
              <%
              } else {
              %>
              <td width="100" class="tdwhiteM"><input type="hidden" name='relEcaDocNo'   value='<%=docHash.get("eca_obj_no") %>' ></td>
              <td width="161" class="tdwhiteM"><input type="hidden" name='relEcaDocName'  value="<%=docHash.get("eca_obj_name") %>" ></td>
              <td width="45" class="tdwhiteM"><input type='hidden' name='relEcaDocPreRev'  value='<%=docHash.get("eca_obj_pre_rev") %>' ></td>
              <td width="45" class="tdwhiteM"><div id="divImgPopupRelDoc" style="display:none;"><a href="javascript:popupRelDoc();"><img src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:clearRelDoc();">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a></div></td>
              <%
              }
              %>
              
            </tr>
            <%
                rowIndex++; 
          
            }   // for( int docCnt=0; docCnt < docList.size(); docCnt++ )
            %>              
              
            <!-- /table>
        </td>
    </tr -->
</table>
<!-- /div -->
