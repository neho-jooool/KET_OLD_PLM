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

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEcr" class="wt.change2.WTChangeRequest2" scope="request"/>
<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>
<jsp:useBean id="competent" class="e3ps.ecm.entity.KETCompetentDepartment" scope="request"/>
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
  ArrayList<Hashtable<String, String>> chgTypeList = codeHash.get("changeType");
  ArrayList<Hashtable<String, String>> reasonList = codeHash.get("changeReason");

  WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();


  //제품, 금형 ECR 확장팩
  KETChangeRequestExpansion expansion = null;
  // ECR 로 찾는다.
  QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
  spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(prodEcr)), new int[] { 0 });
  QueryResult result = PersistenceHelper.manager.find(spec);
  if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
    expansion = (KETChangeRequestExpansion) result.nextElement();
  }

  
  // 주간보고
  String pboOid = "";
  String stateDisplayName = "";
  String stateCode = "";
  
  String reviewRequestDate = "";
  
  String subjectDisplayName = "";
  String subjectOid = "";
  String subjectCode = "";
  String chargeDisplayName = "";
  String chargeOid = "";
  String chargeName = "";
  
  String webEditor = "";
  String webEditorText = "";
  
  String reviewDisplayName = "";
  String reviewOid = "";
  String reviewCode = "";
  String meetingCode = "";
  
  // 첨부파일
  ContentHolder holder = null;
  Vector attachFileList = null;
  
  //권한
  boolean isOwner = false;  // 작성자
  boolean isChief = false;  // 부서장
  boolean isCharge = false; // 담당자
 
  if (PersistenceHelper.isPersistent(competent)) {
      
      pboOid = StringUtils.stripToEmpty(CommonUtil.getOIDString( competent ));
      stateDisplayName = competent.getLifeCycleState().getDisplay(messageService.getLocale());
      stateCode = competent.getLifeCycleState().toString();
      
      // 회신기한
      reviewRequestDate = DateUtil.getTimeFormat(competent.getReplyDeadline(), "yyyy-MM-dd"); 
      
      // 주관부서
      Department subject = competent.getSubject();
      subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
      subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
      subjectCode = StringUtils.stripToEmpty(competent.getSubjectCode());
      
      // 담당자
      WTUser charge = competent.getCharge();
      chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
      chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
      chargeName = StringUtils.stripToEmpty(competent.getChargeName());
      
      // 검토내용
      webEditor = (String) competent.getWebEditor();
      webEditorText = (String) competent.getWebEditorText();

      // 검토결과
      NumberCode review = competent.getReview();
      reviewDisplayName = (review != null) ? StringUtils.stripToEmpty(review.getName()) : "";
      reviewOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(review));
      reviewCode = StringUtils.stripToEmpty(competent.getReviewCode());
      
      // 회의 필요여부
      meetingCode = StringUtils.stripToEmpty(competent.getMeetingCode()); 

      // 첨부파일
      holder = ContentHelper.service.getContents( (ContentHolder)competent );
      attachFileList = ContentUtil.getSecondaryContents(holder);      
      
      // 권한
      // 작성자
      WTUser owner = (WTUser)competent.getCreator().getPrincipal();
      if(loginUser.equals(owner)) {
          isOwner = true;
      }
      // 부서장
      e3ps.groupware.company.PeopleData peoData = new e3ps.groupware.company.PeopleData(loginUser);
      String chief = peoData.chief; // 로그인한 사용자가 팀장이면 chief는 null도 아니고 ""도 아니다.
      if(!chief.equals("") && chief.equals( subjectDisplayName ) ) {
          isChief = true;
      }
      // 담당자
      if(loginUser.equals(charge)) {
          isCharge = true;
      }
      
  } else {
      competent = null;
      
      
      // 주관부서
      Department subject = (expansion != null) ? expansion.getSubject() : null;
      subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
      subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
      subjectCode = StringUtils.stripToEmpty(((expansion != null) ? expansion.getSubjectCode() : null));
      
      // 담당자
      WTUser charge = (expansion != null) ? expansion.getCharge() : null;
      chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
      chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
      chargeName = StringUtils.stripToEmpty(((expansion != null) ? expansion.getChargeName() : null));
      
      // 검토요청기한
      reviewRequestDate = (expansion != null) ? DateUtil.getTimeFormat(expansion.getReviewRequestDate(), "yyyy-MM-dd") : ""; 

      // 권한
      // 부서장
      e3ps.groupware.company.PeopleData peoData = new e3ps.groupware.company.PeopleData(loginUser);
      String chief = peoData.chief; // 로그인한 사용자가 팀장이면 chief는 null도 아니고 ""도 아니다.
      if(!chief.equals("") && chief.equals(subjectDisplayName)) {
          isChief = true;
      }
      // 담당자
      if(loginUser.equals(charge)) {
          isCharge = true;
      }
      
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
	
	<%--검토결과--%>
    $("#reviewResultOid").multiselect({
        minWidth: 200,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    

    //SuggestUtil.bind('DEPARTMENT', 'subjectDisplayName', 'subjectOid');
    SuggestUtil.multiBind('DEPTUSER', 'subjectDisplayName', 'subjectOid', 'chargeDisplayName', 'chargeOid');
    SuggestUtil.multiBind('USERDEPT', 'chargeDisplayName', 'chargeOid', 'subjectDisplayName', 'subjectOid');
    //SuggestUtil.multiBindCallBackFunc('USERDEPT', 'chargeDisplayName', 'chargeOid', deptCallBackFunc); 콜백 함수로 OBJECT 리턴
    //SuggestUtil.bind('USER', 'chargeDisplayName', 'chargeOid');


    // Calener field 설정
    CalendarUtil.dateInputFormat('reviewRequestDate');    
});
</script>        
<script type="text/javascript">
<!--
var arrPartLinkOid;
function init()
{
    var strHTMLCode = document.forms[0].webEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);

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
  
  function deleteDataLine(formName, tableElementId, checkboxName, allCheckName, listVarName)
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
            if(listVal == "") {
              listVal = objChecks[i-1].value;
            } else {
              listVal += "*" + objChecks[i-1].value;
            }
            body.deleteRow(i - 1);
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
    else
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "01715") %><%--삭제할 항목을 선택하세요--%>");
      return;
    }
  }

    function allCheck( checkboxName, isChecked )
    {
        var checkedList = document.getElementsByName( checkboxName );

        for( var i=0; i < checkedList.length ; i++ )
	    {
	      checkedList[i].checked = isChecked;
	    }
    }

    function save()
    {
      if( check() )
      {
    	 // 이노디터 처리 check()에서 처리함
    	 //$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
         //$('[name=webEditorText]').val(fnGetEditorText(0));
        
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
		// 검토결과
		var isCheckedReviewResultOid = $('input:radio[name=reviewResultOid]:checked').is(':checked');
		if(!isCheckedReviewResultOid) {
        	alert('<%=messageService.getString("e3ps.message.ket_message", "04960") %><%--검토결과를 선택하여 주십시오.--%>');
        	document.forms[0].reviewResultOid[0].focus();
            return false;
        }
		
		var isCheckedMReqOid = $('input:radio[name=mReqOid]:checked').is(':checked');
        if(!isCheckedMReqOid) {
            alert('회의 필요여부를 선택하여 주십시오.');
            document.forms[0].mReqOid[0].focus();
            return false;
        }
		
		// 이노디터 처리
        $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
        $('[name=webEditorText]').val(fnGetEditorText(0));
        
		if($('[name=webEditor]').val() == "<br />\n" ) {
        	alert('<%=messageService.getString("e3ps.message.ket_message", "04970") %><%--검토내용을 입력하여 주십시오.--%>');
        	//$('[name=webEditor]').focus();
            return false;
        }
        
        
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

	<%--기각--%>
    function goReject()
	{
	    if(confirm("<%=messageService.getString("e3ps.message.ket_message", "04112") %><%--기각하시겠습니까?--%>")){
	        
	    	if(check()) {
		    	// 이노디터 처리
		        $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		        $('[name=webEditorText]').val(fnGetEditorText(0));
	
		        document.forms[0].cmd.value='Reject';
		        document.forms[0].target = 'download';
		        document.forms[0].action = '/plm/servlet/e3ps/ProdEcrServlet';
		        disabledAllBtn();
		        showProcessing();
		        document.forms[0].submit();
		        
	    	}
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
    
    <%--회신기한--%>
	function lfn_feedback_showCal_reviewRequestDate() {
		// Do nothing..!!   
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
<body onload="init();">
<form name="ModifyEcrForm"  method="post" enctype="multipart/form-data">
<input type="hidden" name="cmd" value="Modify">
<input type="hidden" name ="oid" value="<%=CommonUtil.getOIDString( prodEcr )%>" >
<input type="hidden" name ="pboOid" value="<%=pboOid %>" >

<input type="hidden" name="tabName" value="ecrCompetent">
<input type="hidden" name="deleteFileList" value="">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
              
                <%
                if( (KETObjectUtil.isAdmin() || isChief) && (stateCode.equals("") || stateCode.equals("INWORK") || stateCode.equals("REWORK")) )
                {
                %>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goReject();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04101") %><%--기각--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <%   
                }
                %>
                              
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
              <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04103") %><%--주관부서--%></td>
              <td width="360" class="tdwhiteL">
                <input type="text" name="subjectDisplayName" value="<%=subjectDisplayName %>" style="width:80%" class='txt_field'>
                <input type="hidden" name="subjectOid" value="<%=subjectOid %>" >
                <input type="hidden" name="subjectCode" value="<%=subjectCode %>" >
                <a href="javascript:lfn_addDepartment2();"><img src="/plm/portal/images/icon_5.png" border=0></a>
                <a href="javascript:lfn_deleteDept();"><img src="/plm/portal/images/icon_delete.gif" border=0></a>
              </td>
              <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%></td>
              <td width="360" class="tdwhiteL0">
                <input type="text" name="chargeDisplayName" value="<%=chargeDisplayName %>" style="width:80%" class="txt_field">
                <input type="hidden" name="chargeOid" value="<%=chargeOid %>" >
                <input type="hidden" name="chargeName" value="<%=chargeName %>" >
                <a href="javascript:lfn_addCharge();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                <a href="javascript:lfn_deleteCharge();"><img src="/plm/portal/images/icon_delete.gif" border=0></a>
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04102") %><%--회신기한--%></td>
              <td class="tdwhiteL">
                <input id="reviewRequestDate" name="reviewRequestDate" value="<%=reviewRequestDate %>" class="txt_field" style="width:80px;"/>
                <a href="javascript:CommonUtil.deleteDateValue('reviewRequestDate');"><img src="/plm/portal/images/icon_delete.gif"></a>
              </td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04106") %><%--검토결과--%><span class="red">*</span></td>
              <td class="tdwhiteL0">
                 <%
                 parameter = new HashMap<String, Object>();
                 // 사업부
                 parameter.clear();
                 parameter.put("locale",   messageService.getLocale());
                 parameter.put("codeType", "REVIEWRESULT");
                 List<Map<String, Object>> reviewResultList = NumberCodeHelper.manager.getNumberCodeList(parameter);
                 for ( int i=0; i < reviewResultList.size(); i++ ) {
             	    String checked = (reviewCode.equals((String) reviewResultList.get(i).get("code"))) ? "checked=\"checked\"" : "";
             	    
                 %>
                 <input name="reviewResultOid" type="radio" class="Checkbox" value="<%=reviewResultList.get(i).get("oid") %>" <%=checked %>><%=reviewResultList.get(i).get("value")%>
                 <%
                 }
                 %>&nbsp;
              </td>
            </tr>
            <tr>
              <td class="tdblueL">회의 필요여부<span class="red">*</span></td>
              <td class="tdwhiteL0" colspan="3">
                 <%
                 parameter = new HashMap<String, Object>();
                 // 사업부
                 parameter.clear();
                 parameter.put("locale",   messageService.getLocale());
                 parameter.put("codeType", "MEETINGREQ");
                 List<Map<String, Object>> mReqList = NumberCodeHelper.manager.getNumberCodeList(parameter);
                 for ( int i=0; i < mReqList.size(); i++ ) {
                    String checked = (meetingCode.equals((String) mReqList.get(i).get("code"))) ? "checked=\"checked\"" : "";
                    
                 %>
                 <input name="mReqOid" type="radio" class="Checkbox" value="<%=mReqList.get(i).get("oid") %>" <%=checked %>><%=mReqList.get(i).get("value")%>
                 <%
                 }
                 %>&nbsp;
              </td>
            </tr>
            <!-- tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
              <td class="tdwhiteL"><a href="javascript:viewHistory('<%=pboOid %>');"><%=stateDisplayName %></a>&nbsp;</td>
              <td colspan="2" class="tdwhiteL0">&nbsp;</td>
            </tr -->
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04105") %><%--검토내용--%><span class="red">*</span></td>
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
          </table>
        </div>
        
        
        </td>
  </tr>
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
