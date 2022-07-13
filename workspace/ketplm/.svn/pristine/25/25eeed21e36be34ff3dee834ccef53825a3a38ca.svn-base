<%@page contentType="text/html; charset=UTF-8"%>
<!--%@page import="com.ptc.windchill.uwgm.soap.uwgmdb.Folder"%-->
<%@page import="wt.folder.FolderHelper,
        wt.folder.FolderEntry,
        wt.fc.*,
        wt.lifecycle.State,
        wt.org.*"%>
<%@page import="java.util.*" %>
<%@page import="e3ps.groupware.company.*,
        e3ps.project.*,
        e3ps.project.historyprocess.*,
        e3ps.project.beans.*,
        e3ps.common.util.*,
        org.apache.commons.lang.StringUtils" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%
  String oid = request.getParameter("oid");
  String cmd = request.getParameter("cmd");
  String auth = request.getParameter("auth");
  TemplateProject template = (TemplateProject)CommonUtil.getObject(oid);
  String[] projectType;
  projectType = template.toString().split("\\:");
  
//  e3ps.project.ProductTemplateProject template = (e3ps.project.ProductTemplateProject)CommonUtil.getObject(oid);
  String modifier= "";
  if(template != null){
    if(template.getModifier().getFullName() != null){
      modifier = template.getModifier().getFullName().toString();
    }
  }

  String state = template.getLifeCycleState().toString();

  boolean isApproved = template.getLifeCycleState() == State.toState("APPROVED");
  boolean isCancled = template.getLifeCycleState() == State.toState("CANCELLED");
  boolean isLastTest = template.isLastest();
  boolean isInWork = template.getLifeCycleState() == State.INWORK;
  boolean isReWork =  template.getLifeCycleState() == State.toState("REWORK");
  boolean isDelete = ((state.equals("INWORK") || state.equals("REWORK") || state.equals("ERPSENDERROR")) && isLastTest);
  boolean isCheckOut = template.isCheckOut();
  boolean isWorkingCopy = template.isWorkingCopy();
  boolean isModify = ((isWorkingCopy || !isCheckOut) && (isInWork || isReWork));
  boolean isAuth = false;

  if(auth != null){
    isAuth = true;
  }

  TemplateProjectData projectData = new TemplateProjectData(template);

  boolean isAdmin = CommonUtil.isAdmin();
  boolean canManage = isAdmin;

  boolean isDisabled = isApproved && isLastTest && !isCheckOut && isAdmin;
  boolean isEnabled = isCancled &&  isLastTest && !isCheckOut && isAdmin;

  if(cmd == null)
    cmd= "";

  TemplateProject original = null;
  Object oo = CommonUtil.getObject(oid);
  original = (TemplateProject)oo;

  String popup = request.getParameter("popup");
  Kogger.debug(popup);
  String taskUrl = "";
  String type = request.getParameter("type");
  String mode = request.getParameter("mode");
  String pjtTypeGB = Integer.toString(template.getPjtType());
  
  if(mode == null){
      taskUrl = request.getParameter("taskUrl")+"&cmd=wbs_search";
  }else{
      taskUrl = request.getParameter("taskUrl")+"divide=modify&cmd=wbs_search&oid="+oid;
  }
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "00518") %><%--Template 프로젝트 정보--%></title>
<script type="text/javascript">
var locale = '<%=messageService.getLocale()%>';
</script>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/common/top_include.jsp" %>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/viewObject.js"></script>
<!-- jQuery -->
<!-- <script src="/plm/portal/js/multicombo/jquery.min.js"></script>
<script src="/plm/portal/js/multicombo/jquery-ui.min.js"></script> -->
<script src="/plm/extcore/js/dashboard/jquery.form.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>
<SCRIPT language=JavaScript src="/plm/extcore/js/shared/commonUtil.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/extcore/js/shared/localeUtil.js"></SCRIPT>

<style type="text/css">

/* jquery autocomplete ui */
.ui-autocomplete { height: 200px;width:160px; overflow-y: scroll; overflow-x: hidden; font-size: 11px;}

</style>

<script language="JavaScript">
<!--

  function CheckOutOid(oid){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03272") %><%--Check Out 하시겠습니까??--%>")) {
      return;
    }
    disabledAllBtn();
    showProcessing();
    document.forms[0].cmd.value = "out";
    document.forms[0].oid.value = oid;
    document.forms[0].submit();
  }
  function settingSeq(){
    var url="/plm/jsp/project/template/UpdateSeq.jsp?oid=<%=oid%>";
    openOtherName(url,"process","900","800","status=no,scrollbars=no,resizable=no");
  }

  function HistoryList(){
    var url="/plm/jsp/project/template/TemplateHistoryList.jsp?oid=<%=oid%>";
    openOtherName(url,"process","900","800","status=no,scrollbars=no,resizable=no");
  }

  function viewPeople(oid){
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openOtherName(url,"process","550","400","status=no,scrollbars=no,resizable=no");
  }

  function updataProject(){
    var url="/plm/jsp/project/template/TemplateProjectUpdate.jsp?oid=<%=oid%>";
    openOtherName(url,"process","500","500","status=no,scrollbars=no,resizable=no");
  }

  function deleteProject(){
    if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "03290") %><%--경고!!\n삭제하시겠습니까?--%>') ) {
      if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "03289") %><%--경고!!\n삭제된 프로젝트는 복구될수 없습니다.--%>') ) {
    	  $("#command").val("delete");
    	  showProcessing();
    	  $.ajax({
              type       : "POST",
              url        : "/plm/servlet/e3ps/ProjectServlet",
              data       : $("#projectView").serialize(),
              success    : function(data){
			            	  window.close();
			                  window.opener.search();
              },
              error    : function(xhr, status, error){
                           alert(xhr+"  "+status+"  "+error);
                           
              }
          });
    	 
    	 
    	//document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
        //document.forms[0].method = "post";
        //document.forms[0].command.value = "delete";
        //disabledAllBtn();
        //showProcessing();
       // document.forms[0].submit();
      }
    }
  }

  function disabledProject(){
    if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "03312") %><%--사용 불가로 바꾸시겠습니까?--%>') ) {
        document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
        document.forms[0].method = "post";
        document.forms[0].command.value = "disabled";
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
  }

  function enabledProject(){
    if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "03311") %><%--사용 가능으로 바꾸시겠습니까?--%>') ) {
        document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
        document.forms[0].method = "post";
        document.forms[0].command.value = "enabled";
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
  }
  /* 2014.07.16 수정모드 팝업 */
  function modifyProject(){
	  var taskUrl = "/plm/servlet/e3ps/ProjectScheduleServlet?";
	  var wbsContentUrl = "/plm/jsp/project/template/TemplateProjectView.jsp?oid=<%=oid%>&mode=modify";
	  openOtherName(encodeURI(wbsContentUrl+"&taskUrl="+taskUrl+"&type=<%=type%>"), "StandardWBSSearch", 1335, 768, "status=no,scrollbars=yes,resizable=no");
  }
  
  $(document).ready(function(){
	  
	  SuggestUtil.bind('CUSTOMEREVENT', 'clientCompany', '');
	  
	   $("#devType").val("<%=template.getDevType() %>").attr("selected","selected");
	   $("#division").val("<%=template.getDivision() %>").attr("selected","selected");
	   $("#devStep").val("<%=template.getDevStep() %>").attr("selected","selected");
	   $("#moldType").val("<%=template.getMoldType()%>").attr("selected","selected");
	   $("#making").val("<%=template.getMaking()%>").attr("selected","selected");	  
  });
  
  <!-- 2014.07.14 휴지통 함수 추가 -->
  function delValue(id){
      $("#"+id).val("");
  }

  <!-- 2014.07.15 기존 최종사용처(고객처) 추가-->
   function addValue(type, depth, viewType) {
      var form = document.forms[0];
      var mode = "multi";
      var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=1&codetype=CUSTOMEREVENT&command=select&mode="+mode+"&viewType="+viewType;

      if(viewType == 'noTree')
          returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");
      else
          returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=860px; dialogHeight:480px; center:yes");
      if(typeof returnValue == "undefined" || returnValue == null) {
          return;
      }
      if(type == "SUBCONTRACTOR"){
          acceptProcessM2(returnValue, type);
      }else{
          acceptProcessM(returnValue, type);
      }
      
      
  }
   
   function acceptProcessM(arrObj, type){
	    var clientCompany = arrObj[0];
	    $("#clientCompany").val(clientCompany[2]);
	}
  
 <!-- 2014.07.16 상태변경 함수 추가-->
  function activeAction(type){
	 $("#activeType").val(type);	 $.ajax({
	        type       : "POST",
	        url        : "/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=updateAct",
	        data       : $("#projectView").serialize(),
	        success    : function(data){
	        	        alert(data);
	        	        location.reload(true);
	        },
	        error    : function(xhr, status, error){
	                     alert(xhr+"  "+status+"  "+error);
	                     
	        }
	    });
  }
  
  function modifySave(){
	 if("e3ps.project.ProductTemplateProject" == "<%=projectType[0].toString()%>"){
		
			 if($("#wbsUpFile").val() != ""){
                 showProcessing();
                 
                 $.ajax({
                     type : 'post',
                     url : '/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=updateInfo',
                     data : $("[name=projectView]").serializefiles(),
                     processData : false,
                     contentType : false,
                     success : function(data) {
                    	 document.location.reload();
                        <%--  document.taskContent.saveGrid("<%= oid %>", "<%= type %>"); --%>
                     },
                     error : function() {
                         alert('등록에 실패하였습니다.');
                         // 프로그레스바 숨기기
                         hideProcessing();
                     }
                 });
                 
                 /* form.method = "post";
                 form.action = "/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=updateInfo";
                 form.submit();   */
             } else{ 
            	/*  if(document.taskContent.checkCategoryType()){ */
		              $.ajax({
		                 type       : "POST",
		                 url        : "/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=updateInfo",
		                 data       : $("#projectView").serialize(),
		                 success    : function(data){
		                             /* alert(data);
		                             location.reload(true); */
		                            /*  document.location.reload(); */
		                              document.taskContent.saveGrid("<%= oid %>", "<%= pjtTypeGB %>"); 
		                 },
		                 error    : function(xhr, status, error){
		                              alert(xhr+"  "+status+"  "+error);
		                              
		                 }
		             }); 
             /* }else{
                 alert("신규,Modify 값을 하나 이상 선택하세요");
             } */
            	 
		 }
	 }else{
			  if($("#wbsUpFile").val() != ""){
		          showProcessing();
		          $.ajax({
                     type : 'post',
                     url : '/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=updateInfo',
                     data : $("[name=projectView]").serializefiles(),
                     processData : false,
                     contentType : false,
                     success : function(data) {
                         document.location.reload();
                        <%--  document.taskContent.saveGrid("<%= oid %>", "<%= type %>"); --%>
                     },
                     error : function() {
                         alert('등록에 실패하였습니다.');
                         // 프로그레스바 숨기기
                         hideProcessing();
                     }
                 });
		          
		          
		          form.method = "post";
		          form.action = "/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=updateInfo";
		          form.submit();  
		      } else{ 
		       $.ajax({
		          type       : "POST",
		          url        : "/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=updateInfo",
		          data       : $("#projectView").serialize(),
		          success    : function(data){
		                      /* alert(data);
		                      location.reload(true); */
		                     /*  document.location.reload(); */
		                       document.taskContent.saveGrid("<%= oid %>", "<%= pjtTypeGB %>"); 
		          },
		          error    : function(xhr, status, error){
		                       alert(xhr+"  "+status+"  "+error);
		                       
		          }
		      }); 
		      }
		 
	 }
	  
	  
	 
	  
	 
  }
  
  <!-- 2014.07.21 협력업체 추가 -->
  function selectPartner(){
          var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
          openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
  }
  
  
//협력사 검색결과 셋팅
  function linkPartner(arr){
      if(arr.length == 0) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
              return;
          }

          //document.getElementById("proteamNo").value = arr[0][0];
          $("#makeOffice").val(arr[0][1]);
  }
  
  <!-- 2014.07.14 WBS COPY 검색팝업 추가-->
  function wbsCopySearchPopup(frm){
      var divide = $("#divide").val();
      
      
      if($("#division").val() == ''){
          alert("<%=messageService.getString("e3ps.message.ket_message", "07110") %>");
          return;
      }
      
      if($("#clientCompany").val() == ''){
          alert("<%=messageService.getString("e3ps.message.ket_message", "07112") %>");
          return;
       }
      
      if($("#devType").val() == ''){
          alert("<%=messageService.getString("e3ps.message.ket_message", "07113") %>");
          return;
      }
      
      if($("#devStep").val() == ''){
          alert("<%=messageService.getString("e3ps.message.ket_message", "07114") %>");
          return;
      }
      
      if( divide == "mold"){
          /* if($("#makeOffice").val() == ''){
              alert("개발구분을 선택하세요");
              return;
          } */
          if($("#moldType").val() == ''){
              alert("<%=messageService.getString("e3ps.message.ket_message", "07115") %>");
              return;
          } 
          
          if($("#making").val() == ''){
              alert("<%=messageService.getString("e3ps.message.ket_message", "07116") %>");
              return;
          }
      }
     
      if($("#name").val() == ''){
          alert("<%=messageService.getString("e3ps.message.ket_message", "07118") %>");
          return;
      }
      
      
      var url = "/plm/servlet/e3ps/SearchProjectTemplateServlet?divide="+divide+"&popup=yes";
      var title = "wbsCopySearchPopup";
      openWindow2(url, title, 800, 600);
      
  }
  
  
  function tmpRegister(tid) {
	    
	 
	    if(tid != null && tid.length > 0) {
	        $("#tempid").val(tid);
	      }
	  
	    $.ajax({
	          type       : "POST",
	          url        : "/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=updateInfo",
	          data       : $("#projectView").serialize(),
	          success    : function(data){
	        	              document.location.reload();
	        	            /*  document.taskContent.reload(); */
	                      <%--   document.taskContent.saveGrid("<%= oid %>", "<%= type %>"); --%>
	          },
	          error    : function(xhr, status, error){
	                       alert(xhr+"  "+status+"  "+error);
	                       
	          }
	      }); 
	}
  
  function test(){
	  modifySave();
  }
-->
</script>
<script src="/plm/portal/js/script.js"></script>
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
}
-->
</style>
</head>
<body class="popup-background02 popup-space">
<%@include file="/jsp/common/processing.html"%>
<form id="projectView"  enctype="multipart/form-data" name="projectView">
<!-- Hidden Value -->
<input type=hidden id="command" name=command>
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="auth" value="<%=isAuth%>">
<input type="hidden" name="cmd">
<input type="hidden" id="activeType" name="activeType">
<input type="hidden" id="tempid" name="tempid" value="">
<!-- //Hidden Value -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%= !"modify".equals(mode) ? messageService.getString("e3ps.message.ket_message", "07320") : messageService.getString("e3ps.message.ket_message", "07321")%> </td>
                <%-- <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %>프로젝트관리<img src="/plm/portal/images/icon_navi.gif">WBS</td> --%>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
        <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <!-- Template 정보수정 -->

          <% if(isAdmin){ %>
          <!--
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
            <td width="5">&nbsp;</td>
            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:settingSeq();" class="btn_blue">SEQ SETTING</a></td>
            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
          </tr>
          </table></td>
           -->
          <% } %>


          <% if(isModify || isAdmin){ %>
          <% if(!"modify".equals(mode)) {
               if(!"yes".equals(popup)){
            %>
          <td>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                   <td width="5">&nbsp;</td>
                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                   <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:activeAction('활성');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07106") %><%--활성--%></a></td>
                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
             </table>
          </td>
          <td width="5">&nbsp;</td>
          <td>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                   <td width="5">&nbsp;</td>
                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                   <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:activeAction('비활성');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07107") %><%--비활성--%></a></td>
                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
             </table>
          </td>
          <td width="5">&nbsp;</td>
          <td>
	         <table border="0" cellspacing="0" cellpadding="0">
		        <tr>
		           <td width="5">&nbsp;</td>
		           <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		           <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:modifyProject();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
		           <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
		        </tr>
	         </table>
          </td>
          <td width="5">&nbsp;</td>
          <td>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                   <td width="5">&nbsp;</td>
                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                   <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteProject();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
             </table>
          </td>
          <%} else{%>
               
           <%}}else{ %>
          <td>
            <table border="0" cellspacing="0" cellpadding="0">
                 <tr>
                     <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                     <td class="btn_blue"
                         background="/plm/portal/images/btn_bg1.gif"><a
                         href="javascript:wbsCopySearchPopup(this.form);" class="btn_blue">기존 WBS Copy</a></td>
                     <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                 </tr>
             </table>
           </td>
           <td>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                   <td width="5">&nbsp;</td>
                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                   <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:modifySave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
             </table>
          </td>
          <% } %>
          <td width="5">&nbsp;</td>
          <% if(!"yes".equals(popup)){%>
          <td>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                   <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:opener.search();window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
             </table>
          </td>
          <%}else{%>
          <td>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                   <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
             </table>
          </td>
          <%} %>
          <%-- <td>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                   <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteProject();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %>삭제</a></td>
                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
             </table>
          </td> --%>
         
          <% } %>
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
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <% if(!"modify".equals(mode)) {%>
        <tr>
          <!-- 2014.07.11 -->
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=template.getActiveType() != null ? template.getActiveType() : "" %></td>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=projectData.wbsType != null ? projectData.wbsType : ""  %></td>
          <td width="150" class="tdblueL non-back"></td>
          <td width="240" class="tdwhiteL">&nbsp;</td>
        </tr>
         <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=template.getDivision() != null ? template.getDivision() : ""%></td>
          <% if("mold".equals(type)) {%>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=template.getMakeOffice() != null ? template.getMakeOffice() : ""%></td>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01051") %><%--금형구분--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=template.getMoldType() != null ? StringUtils.capitalize(template.getMoldType()) : "" %></td>
          <%} else  {%>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00583") %><%--개발구분--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=template.getDevType() != null ? template.getDevType() : ""%></td>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
          <td width="240" class="tdwhiteL0">&nbsp;<%=template.getClientCompany() != null ? template.getClientCompany() : "" %></td>
          <%} %>
          <%-- <td width="150" class="tdblueL">WBS  <%=messageService.getString("e3ps.message.ket_message", "01111") %>기간</td>
          <td width="240" class="tdwhiteL">&nbsp;<%=projectData.tempDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %>{0}일</td> --%>
        </tr>
        <tr>
          <% if ("product".equals(type)){ %>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=template.getDevStep() != null ? StringUtils.capitalize(template.getDevStep()) : ""%></td>
          <% } %>
          <% if ("mold".equals(type) || "purchase".equals(type)){ %>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02532") %><%--제작구분--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=template.getMaking() != null ? template.getMaking() : ""%></td>
          <% } %>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=template.getCreatorFullName() != null ? template.getCreatorFullName() : ""%></td>
          <%-- <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02859") %>최초등록일</td>
          <td width="240" class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(projectData.tempCreateDate, "D")%></td> --%>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(projectData.tempModifyDate, "D") != null ? DateUtil.getDateString(projectData.tempModifyDate, "D") : ""%></td>
          <% if ("review".equals(type) || "work".equals(type)){ %>
          <td width="150" class="tdblueL non-back">&nbsp;</td>
          <td width="240" class="tdwhiteL0">&nbsp;</td>
           <%} %>
        </tr>
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07108") %><%--표쥰 WBS Name--%></td>
          <td colspan="5" class="tdwhiteL0"><%=projectData.tempName != null ? projectData.tempName : ""%></td>
        </tr> 
        <tr>
          <td width="150" class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td colspan="5" class="tdwhiteL0" ><pre><%=projectData.tempDesc==""?"&nbsp;":projectData.tempDesc%><pre></td>
        </tr>
        <% }else { %>
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
          <td width="240" class="tdwhiteL">&nbsp;<%=projectData.wbsType != null ? projectData.wbsType : ""%><input type="hidden" name="wbsType" value="<%=projectData.wbsType%>" /></td>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
          <td width="240" class="tdwhiteL">&nbsp;
            <select id="division" name="division" class="fm_jmp" style="width: 160px">
                    <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>]</option>
                   <%if(!CommonUtil.isKETSUser()){ %>
                    <option value="자동차사업부"><%=messageService.getString("e3ps.message.ket_message", "02401")%><%--자동차사업부--%></option>
                    <option value="전자사업부"><%=messageService.getString("e3ps.message.ket_message", "02483")%><%--전자사업부--%></option>
                   <%} else{ %>
                   <option value="KETS">KETS<%--KETS--%></option>
                   <%} %> 
            </select>
          </td>
          <% if ("review".equals(type)){ %>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
          <td width="240" class="tdwhiteL0">&nbsp;
            <input type="text" id="clientCompany" class="txt_field" name="clientCompany" value="<%=template.getClientCompany() != null ? template.getClientCompany() : "" %>"/>
            <a href="javascript:addValue();"><img src="/plm/portal/images/icon_5.png"></a> 
            <a href="javascript:delValue('clientCompany');"><img src="/plm/portal/images/icon_delete.gif"></a>
          </td>
          <%} %>
          <% if ("product".equals(type)){ %>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00583") %><%--개발구분--%></td>
          <td width="240" class="tdwhiteL">&nbsp;
            <select name="devType" id="devType" class="fm_jmp" style="width: 160">
                   <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>]</option>
                   <option value="전략개발"><%=messageService.getString("e3ps.message.ket_message", "02476")%><%--전략개발--%></option>
                   <option value="수주개발"><%=messageService.getString("e3ps.message.ket_message", "01963")%><%--수주개발--%></option>
                   <option value="연구개발"><%=messageService.getString("e3ps.message.ket_message", "02128")%><%--연구개발--%></option>
                   <option value="추가금형"><%=messageService.getString("e3ps.message.ket_message", "02865")%><%--추가금형--%></option>
           </select>
          </td>
          <%} %>
          <% if ("mold".equals(type) || "purchase".equals(type)){ %>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
          <td width="240" class="tdwhiteL">&nbsp;
            <input type="text" class="txt_field" id="makeOffice" name="makeOffice" value="<%=template.getMakeOffice() != null ? template.getMakeOffice() :"" %>"/>
            <a href="javascript:selectPartner();"><img src="/plm/portal/images/icon_5.png"></a> 
            <a href="javascript:delValue('makeOffice');"><img src="/plm/portal/images/icon_delete.gif"></a>
          </td>
          <%} %>
          <%-- <td width="150" class="tdblueL">WBS  <%=messageService.getString("e3ps.message.ket_message", "01111") %>기간</td>
          <td width="240" class="tdwhiteL">&nbsp;<%=projectData.tempDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %>{0}일</td> --%>
        </tr>
        <% if ("product".equals(type)){ %>
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
          <td width="240" class="tdwhiteL">&nbsp;
            <input type="text" class="txt_field" id="clientCompany" name="clientCompany" value="<%=template.getClientCompany() != null ? template.getClientCompany() : "" %>"/>
            <a href="javascript:addValue();"><img src="/plm/portal/images/icon_5.png"></a> 
            <a href="javascript:delValue('clientCompany');"><img src="/plm/portal/images/icon_delete.gif"></a>
          </td>
           <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
           <td width="240" class="tdwhiteL">&nbsp;
            <select name="devStep" id="devStep" class="fm_jmp" style="width: 160">
                   <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>]</option>
                   <option value="pilot">Pilot<%--Pilot--%></option>
                   <option value="proto">Proto<%--Proto--%></option>
           </select>
          </td>
          <td width="150" class="tdblueL"></td>
          <td width="240" class="tdwhiteL"></td>
        </tr> 
        <%} %>
        <% if ("mold".equals(type) || "purchase".equals(type)){ %>
        <tr>
          <%if(!"purchase".equals(type)) {%>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01051") %><%--금형구분--%></td>
          <td width="240" class="tdwhiteL">&nbsp;
            <select name="moldType" id="moldType" class="fm_jmp" style="width: 160">
                   <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>]</option>
                   <option value="mold">Mold<%--Pilot--%></option>
                   <option value="press">Press<%--Proto--%></option>
           </select>
          </td>
          <%} %>
          
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02532") %><%--제작구분--%></td>
          <%if("purchase".equals(type)){ %>
          <td class="tdwhiteL" colspan="5">
          <%}else{ %><td class="tdwhiteL" colspan="3"><%} %>
            <select name="making" id="making" class="fm_jmp" style="width: 160;margin-left:3px;">
                   <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>]</option>
                   <option value="사내"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option>
                   <option value="외주"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option>
           </select>
          </td>
        </tr>
        <%} %>
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07108") %><%--표쥰 WBS Name--%></td>
          <% if ("mold".equals(type) || "product".equals(type) || "purchase".equals(type) || "work".equals(type)){ %>
          <td colspan="5" class="tdwhiteL0">&nbsp;<input type="text" class="txt_field" id="name" name="name" value="<%=projectData.tempName != null ? projectData.tempName : ""%>" style="width:98%;"/></td> 
           <%} %>
          <% if ("review".equals(type)){ %>
          <td colspan="3" class="tdwhiteL"><input type="text" id="name" class="txt_field" name="name" value="<%=projectData.tempName != null ? projectData.tempName : ""%>" style="width:98%;"/></td>
          <%} %>
          <% if ("review".equals(type)){ %>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00583") %><%--개발구분--%></td>
          <td width="240" class="tdwhiteL0">&nbsp;
            <select name="devType" id="devType" class="fm_jmp" style="width: 160">
                   <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>]</option>
                   <option value="전략개발"><%=messageService.getString("e3ps.message.ket_message", "02476")%><%--전략개발--%></option>
                   <option value="수주개발"><%=messageService.getString("e3ps.message.ket_message", "01963")%><%--수주개발--%></option>
                   <option value="연구개발"><%=messageService.getString("e3ps.message.ket_message", "02128")%><%--연구개발--%></option>
                   <option value="추가금형"><%=messageService.getString("e3ps.message.ket_message", "02865")%><%--추가금형--%></option>
           </select>
          </td>
          <%} %>
        </tr> 
        <tr>
          <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td  class="tdwhiteL0" colspan="5">&nbsp;<input type="text" class="txt_field" name="description" value="<%=projectData.tempDesc==""?"&nbsp;":projectData.tempDesc%>" style="width:98%;" /></td>
        </tr>
        <tr>
          <td width="20" class="tdblueL">Excel</td>
          <td width="653" class="tdwhiteL0" colspan="5"><input type="file" id="wbsUpFile" name="wbsUpFile"  onchange="test();"></input>
          <a href="/plm/jsp/project/template/WBS_Form.xls"><img src="/plm/portal/images/iocn_excel.png" alt="excel down" name="leftbtn_02" border="0"></a></td>
        </tr>
        <% } %>
        <%-- 
        <%
        if(projectData.tempProject instanceof ProductTemplateProject) {
        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01843") %>설계구분</td>
          <td class="tdwhiteL">
            &nbsp;<%if( ((ProductTemplateProject)projectData.tempProject).isPlanType() ) {%>Y<%}else {%>N<%}%>
          </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02640") %>조립구분</td>
          <td class="tdwhiteL0">
            &nbsp;<%if( ((ProductTemplateProject)projectData.tempProject).isAssembling() ) {%>Y<%}else {%>N<%}%>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625") %>개발구분</td>
          <td class="tdwhiteL0" colspan="3">
            <%
            int developtype = ((ProductTemplateProject)projectData.tempProject).getDevelopType();
            if(developtype == 1) {
                        %><%=messageService.getString("e3ps.message.ket_message", "02476") %>전략개발
            <%
            }else if(developtype == 2) {
            %><%=messageService.getString("e3ps.message.ket_message", "01963") %>수주개발
            <%
            }else if(developtype == 3) {
                        %><%=messageService.getString("e3ps.message.ket_message", "02128") %>연구개발
            <%
            }else if(developtype == 4) {
                        %><%=messageService.getString("e3ps.message.ket_message", "02865") %>추가금형
            <%
            }%>
            &nbsp;
          </td>
        </tr>
        <%
        }else if(projectData.tempProject instanceof MoldTemplateProject) {
        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01099") %>금형제작</td>
          <td class="tdwhiteL0" colspan=3>
            <%
            int makeType = ((MoldTemplateProject)projectData.tempProject).getMakeType();
            if(makeType == 1) {
                        %><%=messageService.getString("e3ps.message.ket_message", "01655") %>사내
            <%
            }else if(makeType == 2) {
                        %><%=messageService.getString("e3ps.message.ket_message", "02184") %>외주
            <%
            }else if(makeType == 3) {
            %>OEM
            <%
            }
            %>
            &nbsp;
          </td>
        </tr>
        <%
        }else if(projectData.tempProject instanceof ElectronTemplateProject) {
        %>
        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02629") %>제품타입</td>
          <td class="tdwhiteL0" colspan=3>
            <%int productTyep = ((ElectronTemplateProject)projectData.tempProject).getProductType();
            if(productTyep == 1) {
            %><%=messageService.getString("e3ps.message.ket_message", "01801") %>생활가전
            <%
            }else if(productTyep == 2) {
                        %><%=messageService.getString("e3ps.message.ket_message", "03220") %>협피치
            <%
            }
            %>&nbsp;
          </td>
        </tr>
        <%
        }
        %> --%>
    </table>
    <table width="100%" height="100%">
         <%-- <iframe frameBorder="0" id="taskContent" src="<%=taskUrl%>" width="100%" height="500px" name="taskContent"> --%>
         <iframe frameBorder="0" id="taskContent" src="<%=taskUrl%>" name="taskContent" style="display:block; width:100%; height: 100vh">
                <p>Your browser does not support iframes.</p>
        </iframe>
    </table>
    </td>
  </tr>
</table>
</body>
</html>
