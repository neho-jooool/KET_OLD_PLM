<%@page import="e3ps.groupware.company.PeopleData"%>
<%@ page import="java.util.Hashtable
              ,java.util.ArrayList
              ,java.util.Vector
              ,java.util.List
              ,java.util.Map
              ,java.util.HashMap
              ,e3ps.common.code.*
              ,e3ps.common.util.StringUtil
              ,e3ps.common.util.DateUtil
              ,e3ps.common.util.CommonUtil
              ,e3ps.common.util.KETObjectUtil
              ,e3ps.common.util.NumberCodeUtil
              ,e3ps.common.code.NumberCode
              ,e3ps.common.code.NumberCodeHelper
              ,e3ps.ecm.beans.ProdEcrBeans
              ,e3ps.ecm.entity.*
              ,e3ps.common.content.ContentInfo
              ,e3ps.common.content.ContentUtil
              ,e3ps.groupware.company.Department
              ,wt.org.WTUser
              ,wt.fc.*
              ,wt.query.*
              ,wt.content.ContentHelper
              ,wt.content.ContentHolder
              ,wt.content.ContentItem
              ,wt.session.SessionHelper
              ,wt.content.ApplicationData" %>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEcr" class="wt.change2.WTChangeRequest2" scope="request"/>
<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>
<jsp:useBean id="meeting" class="e3ps.ecm.entity.KETMeetingMinutes" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
  if (prodEcr instanceof e3ps.ecm.entity.KETProdChangeRequest) {
    prodEcr = (e3ps.ecm.entity.KETProdChangeRequest) prodEcr;
  } else if (prodEcr instanceof e3ps.ecm.entity.KETMoldChangeRequest) {
    prodEcr = (e3ps.ecm.entity.KETMoldChangeRequest) prodEcr;
  } 

  // 공통정보
  Map<String, Object> parameter = new HashMap<String, Object>();
  parameter.put("locale", messageService.getLocale());

  ProdEcrBeans beans = new ProdEcrBeans();
  Hashtable<String, ArrayList<Hashtable<String,String>>> codeHash = beans.getInitCodeList(parameter);

  ArrayList<Hashtable<String, String>> devFlagList = codeHash.get("devYn");
  ArrayList<Hashtable<String, String>> divList = codeHash.get("division");
  ArrayList<Hashtable<String, String>> chgTypeList = codeHash.get("changeType");    // 사용하지 않는다.
  ArrayList<Hashtable<String, String>> reasonList = codeHash.get("changeReason");

  WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
  WTUser owner = (WTUser)prodEcr.getCreator().getPrincipal();

  boolean isOwner = false;
  if( owner.equals( loginUser ) )
  {
    isOwner = true;
  }


  //제품, 금형 ECR 확장팩
  KETChangeRequestExpansion expansion = null;
  // ECR 로 찾는다.
  QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
  spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(prodEcr)), new int[] { 0 });
  QueryResult result = PersistenceHelper.manager.find(spec);
  if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
    expansion = (KETChangeRequestExpansion) result.nextElement();
  }

  // 회의록
  String pboOid = "";
  String stateDisplayName = "";
  
  String meetingName = "";
  String meetingDate = "";
  String meetingTime = "";
  String meetingLocation = "";
  String attendance = "";
  
  String subjectDisplayName = "";
  String subjectOid = "";
  String subjectCode = "";
  String chargeDisplayName = "";
  String chargeOid = "";
  String chargeName = "";

  String webEditor = "";
  String webEditorText = "";
  
  // 첨부파일
  ContentHolder holder = null;
  Vector attachFileList = null;

  // ECN
  KETChangeNotice ecn = null;
    
  if (PersistenceHelper.isPersistent(meeting)) {
      
      pboOid = StringUtils.stripToEmpty(CommonUtil.getOIDString( meeting ));
      stateDisplayName = meeting.getLifeCycleState().getDisplay(messageService.getLocale());
      
      // 회의명
      meetingName = HtmlUtils.htmlEscape(StringUtils.stripToEmpty(meeting.getMeetingName()));
      // 회의일시
      meetingDate = DateUtil.getTimeFormat(meeting.getMeetingDate(), "yyyy-MM-dd"); 
      // 회의시작
      meetingTime = StringUtils.stripToEmpty(meeting.getMeetingTime());
      // 회의장소
      meetingLocation = StringUtils.stripToEmpty(meeting.getMeetingLocation());
      // 회의 참석자
      attendance = StringUtils.stripToEmpty(meeting.getAttendance());
      
      // 주관부서
      Department subject = meeting.getSubject();
      subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
      subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
      subjectCode = StringUtils.stripToEmpty(meeting.getSubjectCode());
      
      // 담당자
      WTUser charge = meeting.getCharge();
      chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
      chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
      chargeName = StringUtils.stripToEmpty(meeting.getChargeName());
      
      // 회의내용
      webEditor = (String) meeting.getWebEditor();
      webEditorText = (String) meeting.getWebEditorText();
      
      // 첨부파일
      holder = ContentHelper.service.getContents( (ContentHolder)meeting );
      attachFileList = ContentUtil.getSecondaryContents(holder); 
      
      // Link 처리
      KETEcrEcnLink ketEcrEcnLink = null;
      // ECR 로 찾는다.
      QueryResult qr = PersistenceHelper.manager.navigate(prodEcr, "ecn", KETEcrEcnLink.class, false);
      if (qr.hasMoreElements()) {    // while (qr.hasMoreElements()) {
          ketEcrEcnLink = (KETEcrEcnLink) qr.nextElement();
          ecn = ketEcrEcnLink.getEcn();

      }
            
  } else {
      meeting = null;
      
      WTUser charge = (WTUser)(prodEcr.getCreator().getPrincipal());
      PeopleData peopleData = new PeopleData(charge);
      Department subject = peopleData.department;
      // 주관부서
      //Department subject = (expansion != null) ? expansion.getSubject() : null;
      subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
      subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
      subjectCode = StringUtils.stripToEmpty(((expansion != null) ? expansion.getSubjectCode() : null));
      
      // 담당자
      //WTUser charge = (expansion != null) ? expansion.getCharge() : null;
      chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
      chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
      chargeName = StringUtils.stripToEmpty(((expansion != null) ? expansion.getChargeName() : null));
  
  }

%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 10px;
  margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }

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
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    var strHTMLCode = document.forms[0].webEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);
    

    //SuggestUtil.bind('DEPARTMENT', 'subjectDisplayName', 'subjectOid');
    SuggestUtil.multiBind('DEPTUSER', 'subjectDisplayName', 'subjectOid', 'chargeDisplayName', 'chargeOid');
    SuggestUtil.multiBind('USERDEPT', 'chargeDisplayName', 'chargeOid', 'subjectDisplayName', 'subjectOid');
    //SuggestUtil.multiBindCallBackFunc('USERDEPT', 'chargeDisplayName', 'chargeOid', deptCallBackFunc); 콜백 함수로 OBJECT 리턴
    //SuggestUtil.bind('USER', 'chargeDisplayName', 'chargeOid');
    
    
    SuggestUtil.bind('USER', 'relEcaDocWorkerName', 'relEcaDocWorkerOid');

    
    // Calener field 설정
    CalendarUtil.dateInputFormat('meetingDate',lfn_feedback_showCal);       
    CalendarUtil.dateInputFormat('completeRequestDate',lfn_feedback_showCal);       

})
</script>
<script type="text/javascript">
<!--

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
        innerCell.className = "tdwhiteM";

        if (k == 0)
        {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'fileTable', 'fileSelect', 'chkFileAll', 'deleteFileList');\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                                + "<div style=\"display:none;\"><input type=\"checkbox\" name=\"fileSelect\" value=''></div>"
                                ;

        }
        else if (k == 1)
        {
            innerCell.className = "tdwhiteL0";
            innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' size='100%'>";
        }
    }

}

function isChecked( checkboxName )
{
    var isChecked = false;
    var objCheck = document.getElementsByName(checkboxName);

    for( var i=0; i < objCheck.length; i++ )
    {
        if( objCheck[i].checked )
        {
            isChecked = true;
        }
    }

    return isChecked;
}

function deleteDataLine2(formName, tableElementId, checkboxName, allCheckName, listVarName)
{
    var body = document.getElementById(tableElementId);
    if (body.rows.length == 1) return;
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

      if (body.rows.length == 2) {
        if ((typeof objChecks != 'undefined' && objChecks.checked) || (typeof objChecks[0] != 'undefined' && objChecks[0].checked)) {
          if(typeof objChecks != 'undefined' && objChecks.checked) {

            if(listVal == "") {
              listVal = objChecks.value;
            } else {
              listVal += "*" + objChecks.value;
            }
          } else if(typeof objChecks[0] != 'undefined' && objChecks[0].checked) {
            if(listVal == "") {
              listVal = objChecks[0].value;
            } else {
              listVal += "*" + objChecks[0].value;
            }
          }
          body.deleteRow(1);
        }
      } else {
        for (var i = objChecks.length; i > 0; i--) {
          
          if (objChecks[i-1].checked) {
            if(listVal == "") {
              listVal = objChecks[i-1].value;
            } else {
              listVal += "*" + objChecks[i-1].value;
            }
            body.deleteRow(i);
          }
        }
      }
      if (body.rows.length < 2) {
        //objAllChecks.checked = false;
      }
      if(listVarName != "") {
        objList.value = listVal;
      }
    }
    else
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "01715") %><%--삭제할 항목을 선택하세요--%>");
      return;
    }
  }

    function save()
    {
      if( check() )
      {
         // 이노디터 처리
         $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
         $('[name=webEditorText]').val(fnGetEditorText(0));
        
         document.forms[0].action="/plm/servlet/e3ps/ProdEcrServlet";
         document.forms[0].target="download";
         disabledAllBtn();
         showProcessing();
         document.forms[0].submit();
       }
       else
       {
         return;
       }
    }
    
    function check()
    {
        return true;
    }
    
    function cancel()
    {
      if( confirm("<%=messageService.getString("e3ps.message.ket_message", "03331") %><%--작업한 내용이 사라집니다.\n그래도 진행하시겠습니까?--%>") )
      {
        history.back();
      }
      else
      {
        return;
      }
    }

    <%--주관부서--%>
    function lfn_addDepartment2() {
    	var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s&invokeMethod=addDepartmentCallBack";
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=430,height=600";
        
        window.open(url,'',opts);
    }
    
    function addDepartmentCallBack(attacheDept){
        var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
        var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
        callServer(url, lfn_acceptDept2);
    }
    
    function getTagText(xmlDoc){
        return xmlDoc.textContent || xmlDoc.text || '';
    }
    
    function lfn_acceptDept2(req) {
        var xmlDoc = req.responseXML;
        
        var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);
        if(msg != 'true') {
            alert('다시 시도하시기 바랍니다');
            return;
        }

        var l_oid = xmlDoc.getElementsByTagName("l_oid");
        var l_name = xmlDoc.getElementsByTagName("l_name");
        var l_code = xmlDoc.getElementsByTagName("l_code");
        var l_chiefOid = xmlDoc.getElementsByTagName("l_chiefOid");
        var l_chiefName = xmlDoc.getElementsByTagName("l_chiefName");
        document.forms[0].subjectDisplayName.value = decodeURIComponent(getTagText(l_name[0]));
        document.forms[0].subjectOid.value = decodeURIComponent(getTagText(l_oid[0]));
        document.forms[0].subjectCode.value = decodeURIComponent(getTagText(l_code[0]));

        // chiefName, chiefOid 추가
        document.forms[0].chargeDisplayName.value = decodeURIComponent(getTagText(l_chiefName[0]));
        document.forms[0].chargeOid.value = decodeURIComponent(getTagText(l_chiefOid[0]));
        
    }    
    function lfn_deleteDept(){
        document.forms[0].subjectDisplayName.value = "";
        document.forms[0].subjectOid.value = "";
        document.forms[0].subjectCode.value = "";
    }
    
    <%--담당자--%>
    function lfn_addCharge() {
    	SearchUtil.selectOneUser('','','lfn_acceptCharge');
    }

    function lfn_acceptCharge(objArr) {
        if(objArr.length == 0) {
            return;
        }

        // 담당자
        var chargeDisplayName = document.forms[0].chargeDisplayName;
        var chargeOid = document.forms[0].chargeOid;
        var chargeName = document.forms[0].chargeName;
        
        // 주관부서
        var subjectDisplayName = document.forms[0].subjectDisplayName;
        var subjectOid = document.forms[0].subjectOid;
        var subjectCode = document.forms[0].subjectCode;
        
        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            /* 
            for(var j=0; j < infoArr.length; j++) {
                alert('infoArr['+ j +'] is '+ infoArr[j]);
            }
            */
            // 담당자
            chargeDisplayName.value = infoArr[4];
            chargeOid.value = infoArr[0];
            chargeName.value = infoArr[3];
            
            // 주관부서
            subjectDisplayName.value = infoArr[5];
            subjectOid.value = infoArr[2];
            subjectCode.value = "";
        }
        
    }    
    function lfn_deleteCharge(){
        document.forms[0].chargeDisplayName.value = "";
        document.forms[0].chargeOid.value = "";
        document.forms[0].chargeName.value = "";
        
        // lfn_deleteDept();
    }    
    
    <%--회의일시--%>
    function lfn_feedback_showCal_meetingDate() {
        // Do nothing..!!   
    }    
    
// ECN 관련 스크립트 시작
// ECN 기준정보(NumberCode) 리스트 팝업    
function popupEcnNumberCode() {
    var form = document.forms[0];
    var url = "/plm/jsp/ecm/reform/EcnNumberCodeList.jsp"
            + "?codetype=PRODECODOCTYPE"
            + "&"+ convertEcnList2String()
            ;
    url = encodeURI(url);   // 한글깨짐방지
    
    	
    var selectedEcnList = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
    if(typeof selectedEcnList == "undefined" || selectedEcnList == null) {
        return;
    }


    
    //var relDocTable = document.getElementById("relDocTable");
    for (var i = relDocTable.rows.length; i > 1; i--) {
    	relDocTable.deleteRow(i-1);
          
    }
    
    insertEcnLine(selectedEcnList);    	
}

function convertEcnList2String()
{
    var chkPostAct = document.getElementsByName("chkPostAct");
    if(chkPostAct == null || typeof chkPostAct == 'undefined') return "";
    var docType = document.getElementsByName("docType");
    var codename = document.getElementsByName("codename");
    
    var refDocStr = "refDocStr=";
    for( var inx=0; inx < chkPostAct.length ; inx++ )
    {
        if(chkPostAct[ inx ].value != '')
        {
            refDocStr += chkPostAct[inx].value;
            refDocStr += "|";
            
            refDocStr += docType[inx].value;
            refDocStr += "|";
           
            refDocStr += codename[inx].value;
            refDocStr += ",";
     
        }
    }
    
    return refDocStr;
}

function insertEcnLine2()
{
    var innerRow = relDocTable.insertRow();
    innerRow.height = "27";
    var rowIndex = innerRow.rowIndex - 1;
    
    var innerCell;
	
    for( var k=0 ; k < 5 ; k++ ) {
        innerCell = innerRow.insertCell();
	        
        if (k == 0) 
        {
        	var uniqueTime = lfn_getUniqueTime();
        	
        	innerCell.style.width = "20";
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
            	                //= "<a href=\"#\" onclick=\"javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relDocTable', 'chkPostAct', 'chkEcnAll', 'deleteRelDocList');\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                                + "<input type='checkbox' name='chkPostAct' value='"+ uniqueTime +"' checked='checked' style='display:none;'>"
                                + "<input type='hidden' name='ecaUniqueKey' value='"+ uniqueTime +"'>"
                                ;
	
        }
        else if(k == 1)
        {
            innerCell.style.width = "80";
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<select name='docActFlag'><option value='DOC'>문서</option><option value='ACT'>활동</option></select>"
                                ;
        }
        else if(k == 2)
        {
        	//innerCell.style.width = "*";
            innerCell.className = "tdwhiteL";
            innerCell.innerHTML = "<input type='text' name='codename' value='' style='width:100%'>";
        }
        else if(k == 3)
	    {
        	innerCell.style.width = "120";
            innerCell.className = "tdwhiteL";
            innerCell.innerHTML = "<input type='hidden' name='docType' value=''>"
                                + "<input type='hidden' name='relEcaDocWorkerOid' >"
                                + "<input type='text' name='relEcaDocWorkerName' class='txt_field' style='width:80px'>"
                                + "<a href='javascript:popupUser("+ rowIndex +");'><img src='/plm/portal/images/icon_user.gif' border='0'></a>"
                                + "<a href='javascript:clearUser("+ rowIndex +");'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>"
                                ;

        }
        else if(k == 4)
        {
        	innerCell.style.width = "140";
            innerCell.className = "tdwhiteL0";
            innerCell.innerHTML = "<input id='completeRequestDate"+ rowIndex +"' name='completeRequestDate' class='txt_field' style='width: 80px;'/>&nbsp;"
                                + "<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"completeRequestDate\"); lfn_feedback_clearDate("+ rowIndex +")' style='cursor: hand;'>"
                                ;
        }
        

        // Calener field 재설정
        CalendarUtil.dateInputFormat('completeRequestDate',lfn_feedback_showCal);
        
        // 담당자
        SuggestUtil.bind('USER', 'relEcaDocWorkerName', 'relEcaDocWorkerOid');
    }
	
}
function lfn_getUniqueTime() {
    var time = new Date().getTime();
    while (time == new Date().getTime());
    return new Date().getTime();
}

/**
 * @deprecated
 */
function insertEcnLine(selectedEcnList)
{
    /* 
    subArr[0] = form.oid.value;
    subArr[1] = form.oid.codecode;
    subArr[2] = form.oid.codename;
    subArr[3] = form.oid.dsCode;
    subArr[4] = form.oid.codelong;
    arr[idx++] = subArr;
    
    e3ps.common.code.NumberCode:21519,DOC_03,작업기준서,,21519
    */
    var subArr = null;
    var value = null;
    var value = null;
    var value = null;
    var value = null;
    var value = null;
     
    var selectedEcnListLength = selectedEcnList.length;
    for(var i=0; i < selectedEcnListLength; i++) {
        subArr = selectedEcnList[i];
        value = subArr[0];
        codecode = subArr[1];
        codename = subArr[2];
        dsCode = subArr[3];
        codelong = subArr[4];
          
        var innerRow = relDocTable.insertRow();
        innerRow.height = "27";
        var rowIndex = innerRow.rowIndex - 1;
        
        var innerCell;
        
        for( var k=0 ; k < 4 ; k++ ) {
            innerCell = innerRow.insertCell();
                
            if (k == 0) 
            {
                innerCell.style.width = "20";
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relDocTable', 'chkPostAct', 'chkEcnAll', 'deleteRelDocList');\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                                    + "<input type='checkbox' name='chkPostAct' value='"+ codecode +"' style='display:none;'>"
                                    ;
        
            }
            else if(k == 1)
            {
                //innerCell.style.width = "*";
                innerCell.className = "tdwhiteL";
                innerCell.innerHTML = codename
                                    + "<input type='hidden' name='codename' value='"+ codename +"'>"
                                    ;
            }
            else if(k == 2)
            {
                innerCell.style.width = "150";
                innerCell.className = "tdwhiteL";
                innerCell.innerHTML = "<input type='hidden' name='docType' value='"+ dsCode +"'>"
                                    + "<input type='hidden' name='relEcaDocOid' >"
                                    + "<input type='hidden' name='relEcaDocWorkerOid' >"
                                    + "<input type='hidden' name='relEcaDocAfterRev' value='-'>"
                                    + "<input type='text' name='relEcaDocWorkerName' class='txt_field' style='width:80px'>"
                                    + "<a href='javascript:popupUser("+ rowIndex +");'><img src='/plm/portal/images/icon_user.gif' border='0'></a>"
                                    + "<a href='javascript:clearUser("+ rowIndex +");'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>"
                                    ;

            }
            else if(k == 3)
            {
                innerCell.style.width = "150";
                innerCell.className = "tdwhiteL0";
                innerCell.innerHTML = "<input id='completeRequestDate"+ rowIndex +"' name='completeRequestDate' class='txt_field' style='width: 80px;'/>"
                                    + "<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"completeRequestDate\"); lfn_feedback_clearDate("+ rowIndex +")' style='cursor: hand;'>"
                                    ;
            }
        }
        

        // Calener field 재설정
        CalendarUtil.dateInputFormat('completeRequestDate',lfn_feedback_showCal);
        

        SuggestUtil.bind('USER', 'relEcaDocWorkerName', 'relEcaDocWorkerOid');
    }
    
}
        
function chechFunc(pos){
	var form = document.forms[0];
	if(form.relEcaDocWorkerOid[pos].value == ""){
		form.chkPostAct[pos].checked = false;
    }
	else{
	    form.chkPostAct[pos].checked = true;
	}
}

var popUpUserIdx = 0;

// ECN 담당자 검색팝업
function popupUser(pos)
{
	 
  popUpUserIdx = pos;
  
  SearchUtil.selectOneUser('','','setUser');
}

// ECN 담당자 검색팝업 리턴
function setUser(objArr)
{
  if(objArr.length == 0) {
      return;
  }
  var inx = popUpUserIdx;
   	  
  var trArr = objArr[0];
  var form = document.forms[0];

  if(typeof form.relEcaDocWorkerOid.length == 'undefined') {
	  form.relEcaDocWorkerOid.value = trArr[0];
	  form.relEcaDocWorkerName.value = trArr[4];
	  
	  form.chkPostAct.checked = true;
	  
  } else {
	  form.relEcaDocWorkerOid[inx].value = trArr[0];
	  form.relEcaDocWorkerName[inx].value = trArr[4];
	  
	  form.chkPostAct[inx].checked = true;
      
  }
}

// ECN 담당자 초기화
function clearUser(pos)
{
  var form = document.forms[0];

  var targetTable = document.getElementById('relDocTable');
  var tableRows = targetTable.rows.length;
  
  form.relEcaDocWorkerOid[pos].value = '';
  form.relEcaDocWorkerName[pos].value = '';
  
  lfn_uncheck_chkPostAct(pos);
}
    
// 완료요청일 리턴    
var POS_SHOWCAL = -1;
function lfn_feedback_showCal(dateStr, obj) {
	

  var i=0;	
  $('[name=completeRequestDate]').each(function(){
      if($(this)[0] == $(obj)[0]){
    	  POS_SHOWCAL = i;
      }
      i++;
  });
  
  var form = document.forms[0];
  

  if(typeof form.chkPostAct.length == 'undefined') {
      form.chkPostAct.checked = true;
      
  } else {
      form.chkPostAct[POS_SHOWCAL].checked = true;
      
  }
  
  POS_SHOWCAL = -1;
}    

// 완료요청일 삭제 리턴
function lfn_feedback_clearDate(pos) {
	lfn_uncheck_chkPostAct(pos);
}

// 체크박스 언체크 처리
function lfn_uncheck_chkPostAct(pos) {
  var form = document.forms[0];

  if(typeof form.chkPostAct.length == 'undefined') {
    if(form.relEcaDocWorkerOid.value == '' && form.completeRequestDate.value == '') {
	  form.chkPostAct.checked = false;
	}
		  
  } else {
	if(form.relEcaDocWorkerOid[pos].value == '' && form.completeRequestDate[pos].value == '') {
	  
		if(form.chkPostAct[pos].style.display == 'none') {
			// 추가하여 생성된 ECN은 체크박스 언체크 처리에서 제외한다.
	        
		} else {
			form.chkPostAct[pos].checked = false;	
		}
      
	}
	
  }
}
-->
</script>

<!-- 이노디터 JS Include Start -->
<script type="text/javascript">
    //<![CDATA[

    // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
    // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
    // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
    var g_arrSetEditorArea = new Array();
    g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정

    //]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui.js"></script>
<script type="text/javascript">
    //<![CDATA[

    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우

    // Skin 재정의
    //g_nSkinNumber = 0;

    // 크기, 높이 재정의
    g_nEditorWidth = 790;
    g_nEditorHeight = 400;

    //]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<!-- 이노디터 JS Include End -->

</head>
<body>
<form name="ModifyEcrForm"  method="post" enctype="multipart/form-data">
<input type="hidden" name="cmd" value="Modify">
<input type="hidden" name ="oid" value="<%=CommonUtil.getOIDString( prodEcr )%>" >
<input type="hidden" name ="pboOid" value="<%=pboOid %>" >

<input type="hidden" name="tabName" value="ecrMeeting">
<input type="hidden" name="deleteFileList" value="">
<input type="hidden" name="deleteRelDocList" value="">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:cancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
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
      
      
      <div style="width:100%; height:640px; overflow-x:auto; overflow-y:auto;">
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04107") %><%--회의명--%></td>
              <td width="780" colspan="3" class="tdwhiteL"><input name="meetingName" type="text" class="txt_field" id="textfield5" style="width:99%" value="<%=meetingName %>" maxlength="100"></td>
            </tr>
            <tr>
              <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04108") %><%--회의일시--%>&nbsp;/&nbsp;<%=messageService.getString("e3ps.message.ket_message", "회의시간") %><%--회의시간--%></td>
              <td width="325" class="tdwhiteL">
                <input id="reviewRequestDate" name="meetingDate" value="<%=meetingDate %>" class="txt_field" style="width:80px;">
                <!-- a href="#" onclick="javascript:showCal2('meetingDate');"><img src="/plm/portal/images/icon_6.png"></a>&nbsp;
                <a href="JavaScript:clearDate('meetingDate');"><img src="/plm/portal/images/icon_delete.gif"></a -->
                <img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue("completeRequestDate");' style='cursor: hand;'>
              
                <input name="meetingTime" type="text" class="txt_field" id="textfield5" style="width:40%" value="<%=meetingTime %>">
              </td>
              <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "회의장소") %><%--회의장소--%></td>
              <td width="325" class="tdwhiteL0"><input name="meetingLocation" type="text" class="txt_field" id="textfield5" style="width:98%" value="<%=meetingLocation %>"></td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04109") %><%--회의참석자--%></td>
              <td colspan="3" class="tdwhiteL0"><input name="attendance" value="<%=attendance %>" type="text" class="txt_field" id="textfield5" style="width:99%"></td>
            </tr>
            <tr>
              <td class="tdblueL">ECR제기부서</td>
              <td class="tdwhiteL">
                <input type="text" name="subjectDisplayName" value="<%=subjectDisplayName %>" style="width:80%" class='txt_field'>
                <input type="hidden" name="subjectOid" value="<%=subjectOid %>" >
                <input type="hidden" name="subjectCode" value="<%=subjectCode %>" >
                <a href="javascript:lfn_addDepartment2();"><img src="/plm/portal/images/icon_5.png" border=0></a>
                <a href="javascript:lfn_deleteDept();"><img src="/plm/portal/images/icon_delete.gif" border=0></a>
              </td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%></td>
              <td class="tdwhiteL0">
                <input type="text" name="chargeDisplayName" value="<%=chargeDisplayName %>" style="width:80%" class="txt_field">
                <input type="hidden" name="chargeOid" value="<%=chargeOid %>" >
                <input type="hidden" name="chargeName" value="<%=chargeName %>" >
                <a href="javascript:lfn_addCharge();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                <a href="javascript:lfn_deleteCharge();"><img src="/plm/portal/images/icon_delete.gif" border=0></a>
              </td>
            </tr>
            <!-- tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
              <td class="tdwhiteL"><a href="javascript:viewHistory('<%=pboOid %>');"><%=stateDisplayName %></a>&nbsp;</td>
              <td colspan="2" class="tdwhiteL0">&nbsp;</td>
            </tr -->              
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04110") %><%--회의내용--%></td>
              <td colspan="3" class="tdwhiteL0">
              
              
                            <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                            <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor %></textarea> 
                            <textarea name="webEditorText" rows="0" cols="0" style="display: none"><%=webEditorText %></textarea> 
                            <!-- Editor Area Container : Start -->
                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                            <div id="EDITOR_AREA_CONTAINER"></div> 
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
                    <td>&nbsp;</td>
                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:insertFileLine();"" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                        <td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'fileTable', 'fileSelect', 'chkFileAll', 'deleteFileList');"" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                <div id="div_scroll3" style="width:100%;height:81;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
                <table width="100%" cellpadding="0" cellspacing="0" id="fileTable">
                  <tr class="">

                          <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine();"><img src="/plm/portal/images/b-plus.png"></a></td>
                          <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>

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
                  <tr height="27">
                    <td class="tdwhiteM">
                      <a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'fileTable', 'fileSelect', 'chkFileAll', 'deleteFileList');"><img src="/plm/portal/images/b-minus.png"></a>
                      <div style="display:none;"><input type="checkbox" name="fileSelect" value='<%=cntInfo.getContentOid()%>'></div>
                    </td>
                    <td class="tdwhiteL0"><%=url%></td>
                  </tr>
                  <%
                    }
                  }
                  %>
                </table>
                <!-- /div>
                <table border="0" cellspacing="0" cellpadding="0" width="645">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table -->
              </td>
            </tr>
            
            <%
            // 제품 ECR일 경우에만 ECN를 사용한다.
            if (prodEcr instanceof e3ps.ecm.entity.KETProdChangeRequest) {
            %>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04114") %><%--ECN--%></td>
              <td colspan="3" class="tdwhiteL0">
                <table width="100%" cellpadding="0" cellspacing="0" id="relDocTable">
                  <tr class="">

                          <td style="width:20px" class="tdgrayM"><a href="#" onclick="javascript:insertEcnLine2();"><img src="/plm/portal/images/b-plus.png"></a></td>
                          <td style="width:80px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--Type--%></td>
                          <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04115") %><%--후속업무--%></td>
                          <td style="width:140px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                          <td style="width:140px" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
                          
                  </tr>
                <%
                ArrayList ecaList = null;
                if(PersistenceHelper.isPersistent(ecn)) {
	                try {
	                    result = PersistenceHelper.manager.navigate(ecn, "eca", KETEcnEcaLink.class, false);
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
                
				HashMap map = new HashMap();
				map.put("type", "PRODECODOCTYPE");
				map.put("isParent", "false");
				QuerySpec qs = NumberCodeHelper.getCodeQuerySpec(map);
				result =  PersistenceHelper.manager.find(qs);
                
	        	
        	    while(result.hasMoreElements()) {
                    Object[] obj = (Object[])result.nextElement();
                
                    NumberCode numberCode = (NumberCode)obj[0];
                    boolean isDisabled = numberCode.isDisabled();
                    if(!isDisabled) {
	                    
	                    String numberCodeOid = CommonUtil.getOIDString(numberCode);
	                    
	                    
	                    String chkPostAct = numberCodeOid;
	                    String ecaUniqueKey = numberCodeOid;
	                    String docActFlag = StringUtils.stripToEmpty(numberCode.getDsCode());
	                    String codename = StringUtils.stripToEmpty(numberCode.getName());
	                    
	                    String relEcaDocWorkerOid = "";
	                    String relEcaDocWorkerName = "";
	                    String completeRequestDate = "";
	                    
	                    String checked = "";
	                    
	                    int ecaListSize = (ecaList != null) ? ecaList.size() : 0;
	                    for (int i=0; i < ecaListSize; i++) {
	                	    // ECA
                            KETChangeActivity eca = (KETChangeActivity) ecaList.get(i);
                            NumberCode activity = eca.getActivity();
                            
                            String activityOid = CommonUtil.getOIDString(activity);
                            if(activityOid != null && activityOid.equals(numberCodeOid)) {
                        	    checked = "checked=\"checked\"";

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
	            %>
			              <tr>
			                <td class="tdwhiteM">
			                  <!-- a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relDocTable', 'chkPostAct', 'chkEcnAll', 'deleteRelDocList');"><img src="/plm/portal/images/b-minus.png"></a -->
			                  <input type="checkbox" name="chkPostAct" value="<%=chkPostAct %>" <%=checked %>>
		                      <input type="hidden" name="ecaUniqueKey" value="<%=ecaUniqueKey %>"> 
			                </td>
                            <%
                            String docActFlagDisplayName = "";
                            if(docActFlag.equals("") || docActFlag.equalsIgnoreCase("DOC")) docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04119");
                            else docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04120");
                            %>
                            <td class="tdwhiteM"><%=docActFlagDisplayName %><input type="hidden" name="docActFlag" value="<%=docActFlag %>"></td>
		                    <td class="tdwhiteL" title="<%=codename %>"><%=HtmlUtils.htmlEscape(codename) %><input type="hidden" name="codename" value="<%=codename %>"></td>
			                <td class="tdwhiteL">
			                  <input type="hidden" name="docType" value="">
			                  <input type="hidden" name="relEcaDocWorkerOid" value="<%=relEcaDocWorkerOid %>" >
			                  <input type="text" name="relEcaDocWorkerName" value="<%=relEcaDocWorkerName %>" class="txt_field" style="width:80px" onfocusOut="javascript:chechFunc('<%=rowIndex %>');">
			                  <a href="javascript:popupUser('<%=rowIndex %>');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
			                  <a href="javascript:clearUser('<%=rowIndex %>');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
			                </td>
		                    <td class="tdwhiteL0">
		                      <input id="completeRequestDate<%=rowIndex %>" name="completeRequestDate" value="<%=completeRequestDate %>" class="txt_field" style="width: 80px;"/>
                              <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('completeRequestDate'); lfn_feedback_clearDate(<%=rowIndex %>)" style="cursor: hand;">
		                    </td>         
			              </tr>
	            <%
	                rowIndex++;
	            
	                }
	                    
	            }  // while(result.hasMoreElements()) {
	           

                int ecaListSize = (ecaList != null) ? ecaList.size() : 0;
                for (int i=0; i < ecaListSize; i++) {
                    // ECA
                    KETChangeActivity eca = (KETChangeActivity) ecaList.get(i);
                    
                    String chkPostAct = "";
                    String ecaUniqueKey = "";
                    String docActFlag = "";
                    String codename = "";
                    
                    // 기준정보에 있을 경우
                    NumberCode activity = eca.getActivity();
                    if(PersistenceHelper.isPersistent(activity)) {
                	    chkPostAct = activity.getCode();
                	    ecaUniqueKey = activity.getCode();
                	    
                        // 타입
                        docActFlag = StringUtils.stripToEmpty(activity.getDsCode());
                        // 후속업무
                        codename = HtmlUtils.htmlEscape(StringUtils.stripToEmpty(activity.getName()));
                    } 
                    // 사용자 입력일 경우
                    else {
                	    chkPostAct = eca.getCustomCode();
                        ecaUniqueKey = eca.getCustomCode();
                        
                        // 타입
                        docActFlag = StringUtils.stripToEmpty(eca.getCustomType());
                        // 후속업무
                        codename = HtmlUtils.htmlEscape(StringUtils.stripToEmpty(eca.getCustomName()));
                    }
                                        
                    //String checked = "checked=\"checked\"";

                    // 담당자
                    WTUser charge = eca.getCharge();
                    String relEcaDocWorkerOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
                    String relEcaDocWorkerName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
                        
                    // 완료요청일
                    String completeRequestDate = DateUtil.getTimeFormat(eca.getCompleteRequestDate(), "yyyy-MM-dd"); 
                %>
                        <tr>
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
                          <td class="tdwhiteL"><input type="text" name="codename" value="<%=codename %>" style="width:100%;"></td>
                          <td class="tdwhiteL">
                            <input type="hidden" name="docType" value="">
                            <input type="hidden" name="relEcaDocWorkerOid" value="<%=relEcaDocWorkerOid %>" >
                            <input type="text" name="relEcaDocWorkerName" value="<%=relEcaDocWorkerName %>" class="txt_field" style="width:80px">
                            <a href="javascript:popupUser('<%=rowIndex %>');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                            <a href="javascript:clearUser('<%=rowIndex %>');">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                          </td>
                          <td class="tdwhiteL0">
                            <input id="completeRequestDate<%=rowIndex %>" name="completeRequestDate" value="<%=completeRequestDate %>" class="txt_field" style="width: 80px;"/>
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('completeRequestDate'); lfn_feedback_clearDate(<%=rowIndex %>)" style="cursor: hand;">
                          </td>         
                        </tr>
                <%
                    rowIndex++;
              
                }	                
	            %>                  
                  
                </table>
              </td>
            </tr>  
            <%
	        }  // if (prodEcr instanceof e3ps.ecm.entity.KETProdChangeRequest) {
            %>
                      
          </table>
        </div>
        
        
        </td>
  </tr>
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
