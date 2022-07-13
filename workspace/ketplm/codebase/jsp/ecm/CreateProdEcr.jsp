<%@ page import="java.util.*
              ,e3ps.common.util.StringUtil
              ,e3ps.common.util.DateUtil
              ,ext.ket.dqm.entity.*
              ,e3ps.groupware.company.PeopleData
              ,e3ps.common.util.CommonUtil
              ,e3ps.common.util.KETObjectUtil
              ,e3ps.common.code.NumberCode
              ,e3ps.common.code.NumberCodeHelper
              ,e3ps.ecm.beans.ProdEcrBeans
              ,e3ps.ecm.entity.*
              ,e3ps.project.ProjectIssue
              ,e3ps.common.content.ContentInfo
              ,e3ps.common.content.ContentUtil
              ,e3ps.groupware.company.Department
              ,wt.org.WTUser
              ,wt.fc.QueryResult
              ,wt.fc.PersistenceHelper
              ,wt.query.*
              ,wt.content.ContentHolder
              ,wt.content.ContentHelper
              ,wt.session.SessionHelper
              ,wt.content.ApplicationData
              ,wt.part.WTPart" %>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>
              
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%

  Map<String, Object> parameter = new HashMap<String, Object>();
  parameter.put("locale", messageService.getLocale());

  ProdEcrBeans beans = new ProdEcrBeans();
  Hashtable<String, ArrayList<Hashtable<String,String>>> codeHash = beans.getInitCodeList(parameter);

  ArrayList<Hashtable<String, String>> devFlagList = codeHash.get("devYn");
  ArrayList<Hashtable<String, String>> divList = codeHash.get("division");
  ArrayList<Hashtable<String, String>> chgTypeList = codeHash.get("changeType");
  ArrayList<Hashtable<String, String>> reasonList = codeHash.get("changeReason");

  
  WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
  String userGroup = KETObjectUtil.getCurrentUserGroup();
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
      dqmPartOid = CommonUtil.getOIDString(wtpart);
  }
//DQM 에서 ECR 작성시 값 세팅 끝  
%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02541") %><%--제품 ECR 등록--%></title>

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
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/searchUtil.js"></script>

<script type="text/javascript">
//Jquery
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
		$('[name=ecr_title]').val("<%=dqmProblem%>");
		
		var devYnCode  = "<%=dqmOccurStepCode%>";
		$("input:radio[name='dev_yn']:radio[value='"+ devYnCode +"']").prop('checked',true);
	}
	//DQM 에서 ECR 작성시 값 세팅 끝
	
    CalendarUtil.dateInputFormat('reviewRequestDate');

    //SuggestUtil.bind('DEPARTMENT', 'subjectDisplayName', 'subjectOid');
    SuggestUtil.multiBind('DEPTUSER', 'subjectDisplayName', 'subjectOid', 'chargeDisplayName', 'chargeOid');
    SuggestUtil.multiBind('USERDEPT', 'chargeDisplayName', 'chargeOid', 'subjectDisplayName', 'subjectOid');
    //SuggestUtil.multiBindCallBackFunc('USERDEPT', 'chargeDisplayName', 'chargeOid', deptCallBackFunc); 콜백 함수로 OBJECT 리턴
    //SuggestUtil.bind('USER', 'chargeDisplayName', 'chargeOid');

    SuggestUtil.bind('CARTYPE', 'carTypeName', 'carTypeCode');
    SuggestUtil.bind('PRODUCTPROJNO', 'project_id', 'project_oid', 'project_name');
    
});

// 프로젝트 선택시 관련 정보를 물고와 정의된 엘레멘트에 set 한다.
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
                   
                        // 프로젝트 상태
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
                            var pName = arrP[2];
                            var relPartVersion = arrP[3];
                            
                            arrPartInfo13 = [relPartOid, pNum, pName, relPartVersion];
                            //alert('arrPartInfo13 is '+ arrPartInfo13);
                        }
                    
                        
                        if(arrPartInfo13 != null) {
                            if(arrParts == null) arrParts = new Array();
                            arrParts[arrParts.length] = arrPartInfo13;
                            //alert('arrParts['+ (arrParts.length - 1) +'] is '+ arrParts[ (arrParts.length - 1) ]);
                        }
                        
                    }
                    
                    // 제품정보를 위한 후처리
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
<!--

function deptCallBackFunc(obj){
	/*
	obj.USERID 
    obj.USERNAME
    obj.DEPTID 
    obj.DEPTNAME
    */
    alert(obj.USERNAME);
}


var devflag = '';


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
     newTd.className = "tdwhiteM";
     newTd.setAttribute("title", problemNo);
     str = "<div class='ellipsis' style='width:337;'><nobr>";
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
}

function init()
{
  var form = document.forms[0];

    deleteAllSelectBox( form.div_flag );

  <%
  if( userGroup.equals("지원조직") )
  {
  %>
    addSelectBox( form.div_flag, '<%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%>', '', '' );
  <%
  }

  String groupCode = "";
  Hashtable<String, String> divFlag = new Hashtable<String, String>();
    for(int jnx=0; jnx < divList.size(); jnx++)
    {
      divFlag = divList.get(jnx);
      if( divFlag.get("name").equals( userGroup ) ||  userGroup.equals("지원조직") )
      {
        if( !userGroup.equals("지원조직") )
        {
          groupCode = divFlag.get("code");
        }
        else
        {
          groupCode = "";
        }
    %>
    addSelectBox( form.div_flag, '<%=divFlag.get("name")%>', '<%=divFlag.get("code")%>', '<%=groupCode%>');
    <%
      }
     }
    %>

  document.forms[0].div_flag.value= '<%=groupCode%>';
  if("<%=dqmOccurStepCode%>" == ""){
	  document.forms[0].dev_yn[0].checked= true;
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
        innerCell.height = "23";

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
              innerCell.className = "tdwhiteL0";
              innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:100%'>";
        }
    }

  }

  function deleteFileLine()
  {
    var body = document.getElementById("fileTable");
    if (body.rows.length == 0) return;
    var file_checks = document.forms[0].fileSelect;
    if (body.rows.length == 1)
    {
      body.deleteRow(0);
    } else
    {
      for (var i = body.rows.length; i > 0; i--)
        {
        if (file_checks[i-1].checked) body.deleteRow(i - 1);
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

  <% /* @deprecated */ %>
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

    function disable( value )
    {
      if( value == 'REASON_4' )
      {
          //document.forms[0].other_reason.disabled = false;
      }
      else
      {
        //document.forms[0].other_reason.disabled = true;
      }

    }

    function save()
    {
      if( check() )
      {
    	  
          // 이노디터 처리
          $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
          $('[name=webEditorText]').val(fnGetEditorText(0));

          // 이노디터 처리
          $('[name=webEditor1]').val(fnGetEditorHTMLCode(false, 1));
          $('[name=webEditorText1]').val(fnGetEditorText(1));

        
        /*
        if( document.forms[0].other_reason.disabled == true )
        {
          document.forms[0].other_reason.value = "";
        }
        */
        
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

    function cancel()
    {
      if( confirm("<%=messageService.getString("e3ps.message.ket_message", "03331") %><%--작업한 내용이 사라집니다.\n그래도 진행하시겠습니까?--%>") )
      {
    	 
    	 /*  
    	 document.forms[0].action="/plm/jsp/ecm/SearchEcrForm.jsp";
         document.forms[0].target="_self";
         document.forms[0].submit();
         */
         
         if(opener) opener.feedbackAfterPopup("doReSearching");
         window.close();
         
       }
       else
       {
         return;
       }
    }

  function popupProject()
  {
    SearchUtil.selectOneProjectPopUp('setProject','status=P&type=P&modal=');
  }

  function delProject()
  {
    document.forms[0].project_id.value = '';
    document.forms[0].project_name.value = '';
    document.forms[0].project_oid.value='';
  }

  function setProject(objArr)
  {

    if(objArr == null || objArr.length == 0) {
      return;
    }

    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++)
    {
      trArr = objArr[i];
      
      /* 
      document.forms[0].project_oid.value = trArr[0];
      document.forms[0].project_id.value = trArr[1];
      document.forms[0].project_name.value= trArr[2];
      */
      
      // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
      lfn_setInfosRelatedProject(trArr[0]);
      
      
    }
    
    hideProcessing();
  }


  function isDuplicatePart( partOid )
  {
    var isDuplicate = false;
    var existPartList = document.getElementsByName( "relPartOid" );

    for( var i=0; i < existPartList.length ; i++ )
    {
      if( existPartList[i].value == partOid )
      {
        isDuplicate =  true;
      }
    }

    return isDuplicate;
  }
  
<% /* @deprecated */ %>  
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

  function popupPart()
  {
    var url="/plm/ext/part/base/listPartPopup.do?mode=multi&pType=P&modal=N";
    openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
  }

  function selectedPart(objArr) {
  if(objArr == null || objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relPartTable");
  
  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    
    if( !isDuplicatePart(trArr[0]) )
    {
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
      str += "<a href=\"javascript:viewRelPart('" + trArr[0] + "');\">" + getTruncStr(trArr[1], 15) + "</a>";
      str += "<input type='hidden' name='relPartOid' value='" + trArr[0] + "'>";
      str += "<input type='hidden' name='relPartLinkOid' value=''>";
      newTd.innerHTML = str;

      //Part Name
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "";
      newTd.className = "tdwhiteL";
      str = "";
      str += "<font title=\""+trArr[2]+"\"><div class='ellipsis' style='width:600px;'><nobr>"+trArr[2]+"</nobr></div></font>";
      newTd.innerHTML = str;

      //Rev
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "50";
      newTd.className = "tdwhiteM0";
      //newTd.innerHTML = trArr[3] + "&nbsp;";
      newTd.innerHTML = ((typeof trArr[12] != 'undefined') ? trArr[12] : trArr[3]) + "&nbsp;"; // Display Revision

      /* 
      //req Comment
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "200";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type='text' name='req_comment' class='txt_field' style='width:95%'>";
      */
      
    }

  }
}

function check()
{
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

  var chg_reason = document.all("chg_reason");
  var str_chg_reason = '';
  for( var inx=0; inx < chg_reason.length ; inx++)
  {
    if( chg_reason[inx].checked )
    {
      str_chg_reason  = chg_reason[inx].value;
    }
  }

  var targetTable = document.getElementById("relPartTable");

  if( form.ecr_title.value == '' )
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "02526") %><%--제목을 입력하세요--%>');
    form.ecr_title.focus();
  }
  else if( str_dev_yn == '' )
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "01191") %><%--단계 구분을 선택하세요--%>');
    form.dev_yn[0].focus();
  }
  else if( form.div_flag.value == '' && '<%=userGroup%>' == '지원조직')
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "01666") %><%--사업부를 선택하세요--%>');
  }
  else if( str_dev_yn != 'PRODUCTION' && form.project_id.value == '' )
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "04930") %><%--개발단계가 \'양산\'이 아닐 경우 프로젝트 정보를 입력하셔야 합니다.--%>');
    popupProject('project_id');
  }
  
  else if( $('input[name=reviewRequestDate]').val() == '' )
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "04780") %><%--검토요청기한을 입력하여 주십시오.--%>');
    $('input[name=reviewRequestDate]').focus();
  }
  else if( $('input[name=subjectOid]').val() == '' )
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "04790") %><%--주관부서를 선택하여 주십시오.--%>');
    $('input[name=subjectDisplayName]').focus();
    lfn_addDepartment2()
    
  }
  
  else if( str_chg_reason == '')
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "01858") %><%--설계변경요청 사유를 선택하세요--%>');
    form.chg_reason[0].focus();
  }
  /*
  else if( str_chg_reason != '' && str_chg_reason.lastIndexOf('4') > 0 && form.other_reason.value == ''  )
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "01143") %><%--기타 설계변경 사유를 입력하세요--%>');
    form.other_reason.focus();
  }
  */
  else if( targetTable.rows.length <=1 )
  {
    isValid = false;
    //<entry key="04003">관련부품을 추가하여 주십시오.</entry>
    alert('<%=messageService.getString("e3ps.message.ket_message", "04003") %>');
    popupPart();
    
  }
  <%-- 
  else if( getStringLength( form.ecr_desc.value ) > 200  )
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "01869") %>설명을 200byte 이내로 작성하여 주십시오');
  }
  else if( getStringLength( form.ecr_effect.value ) > 200 )
  {
    isValid = false;
    alert('<%=messageService.getString("e3ps.message.ket_message", "01117") %>기대효과를 200byte 이내로 작성하여 주십시오');
  }
  --%>
  return isValid;
}

//관련이슈 검색 팝업 호출
function popupRelIssue() {
  var form = document.forms[0];
  if(form.project_oid.value == "") {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00921") %><%--관련 프로젝트가 없습니다--%>");
    return;
  }
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/ProjectIssueViewListPopup.jsp";
  url += "&obj=projectOid^" + form.project_oid.value;
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }

  insertRelIssueLine(attache);
}

//관련이슈 라인추가(projectIssueCreate.jsp 참조)
function insertRelIssueLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relIssueTable");

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];

    if( !isDuplicateIssue( trArr[0] ) )
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "20";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                      + "<div style=\"display:none;\"><input name='chkSelectRelIssue' type='checkbox' value=''></div>"
                      ;

      //이슈명
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "";
      newTd.className = "tdwhiteM";
      newTd.setAttribute("title", trArr[1]);
      str = "<div class='ellipsis' style='width:337;'><nobr>";
      str += "<a href=\"javascript:viewRelIssue('" + trArr[0] + "');\">" + trArr[1] + "</a>";
      str += "<input type='hidden' name='relIssueOid' value='" + trArr[0] + "'>";
      str += "<input type='hidden' name='relIssueLinkOid' value=''></nobr></div>";
      newTd.innerHTML = str;

      //작성자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "140";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = trArr[2];

      //작성일자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "115";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = trArr[3];
    }
  }
}

function isDuplicateIssue( issueOid )
{
  var isDuplicate = false;

  var issueList = document.getElementsByName("relIssueOid");
  for( var cnt=0; cnt < issueList.length; cnt++ )
  {
    if( issueList[cnt].value == issueOid )
      isDuplicate = true;
  }

  return isDuplicate;
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
        newTd.className = "tdwhiteM";
        newTd.setAttribute("title", trArr.PROBLEMNO);
        str = "<div class='ellipsis' style='width:337;'><nobr>";
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


//부품 상세조회 팝업
function viewRelPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function viewRelIssue(oid){
  var url = '/plm/jsp/project/projectIssueView.jsp?oid='+oid;
  openWindow2(url,"","750","320","scrollbars=no,resizable=no,top=200,left=250");
}
-->
</script>

<script type="text/javascript">

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

    function lfn_feedback_after_ecr() {
    	//location.reload();
    	
    	hideProcessing();
        enabledAllBtn();
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
    g_nEditorWidth = 485;
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
    fnResizeEditor(0, 485, 600);
    fnResizeEditor(1, 485, 600);
}

function fnCustomerFunction_3(EditNumber)
{
    //alert("사용자 정의 툴바버튼 3");
    /* 
    var object = document.getElementById("EDITOR_AREA_CONTAINER"+ ((EditNumber == 0) ? "" : EditNumber));
    object.style.position = "";
	*/
	 
    fnResizeEditor(0, 485, 200);
    fnResizeEditor(1, 485, 200);
}
</script>

<!-- 이노디터 JS Include End -->

</head>
<body onload="javascript:init();">
<form name="CreateEcrForm"  method="post" enctype="multipart/form-data">
<input type="hidden" name="cmd" value="Create">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
            <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02541") %><%--제품 ECR 등록--%></td>
                <!-- td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00204") %><%--ECR 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02541") %><%--제품 ECR 등록--%>
                  </td -->
              </tr>
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        <tr>
          <td class="space5"></td>
        </tr>
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
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:cancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
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
      
      
      <div style="width:100%; height:680px; overflow-x:auto; overflow-y:auto;">
          <table border="0" cellspacing="0" cellpadding="0" width="100%">
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
	          <td colspan="3" class="tdwhiteL0"><input name="ecr_title" type="text" class="txt_field" id="textfield5" style="width:100%" value="" maxlength="100"></td>
	        </tr>
	        <tr>
	          <!-- 단계구분  -->
	          <td class="tdblueL" width="130"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--개발단계--%><span class="red">*</span></td>
	          <td class="tdwhiteL" width="330">
	          <%
	          Hashtable<String, String> devFlag = new Hashtable<String, String>();
	          for( int inx=0; inx < devFlagList.size(); inx++ )
	          {
	            devFlag = devFlagList.get(inx);
	          %>
	            <input name="dev_yn" type="radio" class="Checkbox" value='<%=devFlag.get("code") %>'  id="dev_yn" >
	            <%=devFlag.get("name") %>
	          <%
	          }
	          %>
	          </td>
	          <td class="tdblueL" width="130"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
	          <td class="tdwhiteL0" width="320">
	          <select name="div_flag" class="fm_jmp" style="width:217"></select>
	         </td>
	        </tr>
	        <tr>
	          <td class="tdblueL">Project No</td>
	          <td class="tdwhiteL">
	            <input type="text" name="project_id" id="project_id" class="txt_field" value="" style="width:80%">
	            <a href="javascript:popupProject('project_id');">
	               <img src="/plm/portal/images/icon_5.png" border="0">
	            </a>
	            <a href="#" onfocus="this.blur();" onClick="javascript:delProject();">
	               <img src="/plm/portal/images/icon_delete.gif" border="0">
	             </a>
	          </td>
	          <td class="tdblueL">Project Name</td>
              <td class="tdwhiteL0">
                <input type="text" name="project_name" class="txt_fieldRO" style="width:80%;" readonly>
	            <input type="hidden" name="project_oid">
	          </td>
	        </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04123") %><%--검토요청기한--%><span class="red">*</span></td>
              <td class="tdwhiteL">
                <input type="text" name="reviewRequestDate" id="reviewRequestDate" class="txt_field"  style="width:100px" value="">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('reviewRequestDate');" style="cursor: hand;">
              </td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04122") %><%--차종--%></td>
              <td class="tdwhiteL">
	              <input type="text" name="carTypeName" class="txt_field" style="width:80%"> 
	              <input type="hidden" name="carTypeCode">
	              <a href="javascript:SearchUtil.selectCarType('carTypeCode','carTypeName');">
	              <img src="/plm/portal/images/icon_5.png" border="0"></a> 
	              <a href="javascript:CommonUtil.deleteValue('carTypeCode','carTypeName');">
	              <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	          </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04103") %><%--주관부서--%><span class="red">*</span></td>
              <td class="tdwhiteL">
                <input type="text" name="subjectDisplayName" style="width:80%" class='txt_field'>
                <input type="hidden" name="subjectOid" >
                <input type="hidden" name="subjectCode" >
                <a href="javascript:lfn_addDepartment2();">
                    <img src="/plm/portal/images/icon_5.png" border=0>
                </a>
                <a href="javascript:lfn_deleteDept();">
                    <img src="/plm/portal/images/icon_delete.gif" border=0>
                </a>              
              </td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%></td>
              <td class="tdwhiteL0">
                <input type="text" name="chargeDisplayName" style="width:80%" class="txt_field">
                <input type="hidden" name="chargeOid" >
                <input type="hidden" name="chargeName" >
                <a href="javascript:lfn_addCharge();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                <a href="javascript:lfn_deleteCharge();"><img src="/plm/portal/images/icon_delete.gif" border=0></a>
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04124") %><%--금형변경--%></td>
              <td class="tdwhiteL">
              <%
              parameter = new HashMap<String, Object>();
              parameter.clear();
              parameter.put("locale",   messageService.getLocale());
              parameter.put("codeType", "MOLDCHANGE");
              List<Map<String, Object>> moldChangeList = NumberCodeHelper.manager.getNumberCodeList(parameter);
              for ( int i=0; i < moldChangeList.size(); i++ ) {
              %>
                <input name="moldChangeOid" type="radio" class="Checkbox" value="<%=moldChangeList.get(i).get("oid") %>" ><%=moldChangeList.get(i).get("value")%>
              <%
              }
              %>
              </td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04125") %><%--원가변동--%></td>
              <td class="tdwhiteL0">
              <%
              parameter = new HashMap<String, Object>();
              parameter.clear();
              parameter.put("locale",   messageService.getLocale());
              parameter.put("codeType", "COSTCHANGE");
              List<Map<String, Object>> costChangeList = NumberCodeHelper.manager.getNumberCodeList(parameter);
              for ( int i=0; i < costChangeList.size(); i++ ) {
              %>
                <input name="costChangeOid" type="radio" class="Checkbox" value="<%=costChangeList.get(i).get("oid") %>" ><%=costChangeList.get(i).get("value")%>
              <%
              }
              %>
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04126") %><%--긴급도--%></td>
              <td class="tdwhiteL">
              <%
              parameter = new HashMap<String, Object>();
              parameter.clear();
              parameter.put("locale",   messageService.getLocale());
              parameter.put("codeType", "EMERGENCYPOSITION");
              List<Map<String, Object>> emergencyPositionList = NumberCodeHelper.manager.getNumberCodeList(parameter);
              for ( int i=0; i < emergencyPositionList.size(); i++ ) {
              %>
                <input name="emergencyPositionOid" type="radio" class="Checkbox" value="<%=emergencyPositionList.get(i).get("oid") %>" ><%=emergencyPositionList.get(i).get("value")%>
              <%
              }
              %>
              </td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01859") %><%--설계변경요청사유--%><span class="red">*</span></td>
              <td class="tdwhiteL0" >
               <%
               Hashtable<String, String> reason = new Hashtable<String, String>();
               for(int snx=0; snx < reasonList.size(); snx++ )
               {
                 reason = reasonList.get(snx);
               %>
                 <input name="chg_reason" type="radio" class="Checkbox" value='<%=reason.get("code") %>' onclick="javascript:disable(this.value);"><%=reason.get("name") %>
               <%
               }
               %>
               <!-- td> <input type="text" name="other_reason" class="txt_field" style="width:250" id="other_reason"></td -->
              </td>
            </tr>
            
            <%-- 
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00939") %>관련이슈</td>
	          <td colspan="3" class="tdwhiteL0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <table style="width:100%" border="0" cellspacing="0" cellpadding="0" >
	              <tr>
	                <td align="right">
	                  <table border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td>
	                        <table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelIssue();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %>추가</a></td>
	                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                          </tr>
	                        </table></td>
	                      <td width="5">&nbsp;</td>
	                      <td><table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'relIssueTable', 'chkSelectRelIssue', 'chkAllIssue', '');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %>삭제</a></td>
	                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                          </tr>
	                        </table>
	                      </td>
	                    </tr>
	                  </table>
	                </td>
	              </tr>
	            </table>
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <div id="div_scroll" style="width:100%; height:71px; overflow-x:hidden; overflow-y:auto;" class="table_border"  -->
	            <table width="100%" cellpadding="0" cellspacing="0" id="relIssueTable">
	              <tr class="">

	                      <td width="20" class="tdgrayM"><a href="javascript:popupRelIssue();"><img src="/plm/portal/images/b-plus.png"></a></td>
                          <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02317") %>이슈명</td>
	                      <td width="140" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %>작성자</td>
	                      <td width="115" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %>작성일자</td>

	              </tr>
	            </table>
	            <!-- /div>
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table -->
	          </td>
	        </tr>
	        --%>
	        
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
                </table>
              </td>
            </tr>	        
	        <!-- tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
	          <td colspan="3" class="tdwhiteL0">
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <textarea name="ecr_desc" style='overflow: auto; width:100%; height:50px; padding: 2;' class="txt_field" ></textarea>
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	          </td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01116") %><%--기대효과--%><span class="red"></span></td>
	          <td colspan="3" class="tdwhiteL0">
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <textarea name="ecr_effect" style='overflow: auto; width:100%; height:50px; padding: 2;' class="txt_field" ></textarea>
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	          </td>
	        </tr -->
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%><span class="red">*</span></td>
	          <td colspan="3" class="tdwhiteL0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <table style="width:100%" border="0" cellspacing="0" cellpadding="0" >
	              <tr>
	                <td>&nbsp;</td>
	                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td><table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupPart();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
	                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                          </tr>
	                        </table></td>
	                      <td width="5">&nbsp;</td>
	                      <td><table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', '');"  class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
	                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                          </tr>
	                        </table></td>
	                    </tr>
	                  </table></td>
	              </tr>
	            </table>
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <div id="div_scroll2" style="width:100%; height:71px; overflow-x:hidden; overflow-y:auto;" class="table_border"  -->
	            <table width="100%" cellpadding="0" cellspacing="0" id="relPartTable">
	              <tr class="">

	                      <td width="20" class="tdgrayM"><a href="javascript:popupPart();"><img src="/plm/portal/images/b-plus.png"></a></td>
                          <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
	                      <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
	                      <td width="50" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
	                      <!-- td width="200" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02193") %><%--요청사항--%></td -->

	              </tr>
	              <!-- tr>
	                <td>
	                  <table width="100%" cellpadding="0" cellspacing="0"  id="relPartTable"  style=table-layout:fixed >
	                  </table>
	                </td>
	              </tr -->
	            </table>
	            <!-- /div>
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%" >
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table -->
	          </td>
	        </tr>
            <tr>
              <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04127") %><%--현상--%></td>
              <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04128") %><%--문제점 및 요구사항--%></td>
            </tr>
            <tr>
              <td colspan="2" class="tdwhiteL">
              
              
                            <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                            <textarea name="webEditor" rows="0" cols="0" style="display: none"></textarea> 
                            <textarea name="webEditorText" rows="0" cols="0" style="display: none"></textarea> 
                            <!-- Editor Area Container : Start -->
                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                            <div id="EDITOR_AREA_CONTAINER"></div> 
                            <!-- Editor Area Container : End -->
                            
                            
              </td>
              <td colspan="2" class="tdwhiteL0">
              
              
                            <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                            <textarea name="webEditor1" rows="0" cols="0" style="display: none"></textarea> 
                            <textarea name="webEditorText1" rows="0" cols="0" style="display: none"></textarea> 
                            <!-- Editor Area Container : Start -->
                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                            <div id="EDITOR_AREA_CONTAINER1"></div> 
                            <!-- Editor Area Container : End -->
                            
                            
              </td>            
            </tr>            	
 	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
	          <td colspan="3" class="tdwhiteL0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <table style="width:100%" border="0" cellspacing="0" cellpadding="0" >
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
	                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteFileLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
	                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                      </tr>
	                    </table></td>
	                  </tr>
	                </table></td>
	              </tr>
	            </table>
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <div id="div_scroll3" style="width:100%; height:71px; overflow-x:hidden; overflow-y:auto;" class="table_border"  -->
	            <table width="100%" cellpadding="0" cellspacing="0" id="fileTable">
	              <tr class="">

	                      <td width="20" class="tdwhiteM"><a href="#" onclick="javascript:insertFileLine();"><img src="/plm/portal/images/b-plus.png"></a></td>
	                      <td width="" class="tdwhiteL0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>

	              </tr>
	            </table>
	            <!-- /div>
	            <table border="0" cellspacing="0" cellpadding="0" style="width:100%">
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
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
