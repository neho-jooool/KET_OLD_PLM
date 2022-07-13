<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.project.outputtype.CustomerTheModelPlan"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="e3ps.project.outputtype.OEMPlan"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.outputtype.ModelPlan"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.beans.TaskPlanComparator"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.beans.ProgramManagerHelper"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@page import="java.util.ArrayList,
                java.util.Map,
                java.util.List,
                java.util.HashMap,
                java.util.Vector,
                java.text.SimpleDateFormat,
                java.util.Calendar"%>

<%@page import="wt.lifecycle.State"%>

<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.web.WebUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.KETParamMapUtil,
                e3ps.project.beans.ProjectHelper"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="searchList" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>
<jsp:useBean id="resultStr" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />


<%@include file="/jsp/common/context.jsp"%>

<%
  String oid = request.getParameter("oid");
  Kogger.debug("oid = " + oid ) ;

  ModelPlan mp = null;

  if(oid != null){
    mp = (ModelPlan)CommonUtil.getObject(oid);
    HashMap map = new HashMap();
    map.put("oid", oid);
    ProgramManagerHelper.saveNewEvent(map);
    
  }
  
//EJS TreeGrid Start
  response.addHeader("Cache-Control","max-age=1, must-revalidate");

  String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
  String pagingSize = null;


  if ( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

      pagingSize = searchCondition.get("perPage").toString();
  }

  if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

      pagingSize = pagingSizeDefault;
  }
  // EJS TreeGrid End
  String sapAuth = "0";
  String isAEGIS = request.getParameter("isAEGIS");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<title>차종별 상세정보</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
<!-- EJS TreeGrid End -->

<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
}
-->
</style>
<script language="JavaScript">
<!--
function modify(oid){
//alert(oid);
  document.location.href="/plm/jsp/project/ModifyProgram.jsp?oid="+oid;
}

function delProgram(oid)
{
//alert(oid);
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03329") %><%--자동차일정을 삭제하시겠습니까?--%>")) {
      return;
    }
  document.location.href="/plm/jsp/project/ActionProgram.jsp?oid="+oid+"&cmd=Delete";
}

//초기화면세팅
function init() {
	var isAEGIS = '<%=isAEGIS%>';
	if(isAEGIS == 'Y'){
		clickTabBtn(2);
	}else{
		clickTabBtn(1);
	}
}
var initFlag = 0;
function clickTabBtn(tabId) {
    var tabBasic = document.getElementById("tabBasic");
    var tabList = document.getElementById("tabList");
    
    if(tabId == 1) {
        tabBasic.style.display = "block";
        tabList.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "block";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        
        tabBasic.style.zIndex = 2;
        tabList.style.zIndex = 1;
        
        document.forms[0].tabName.value = 'tabBasic';
        
    } else if(tabId == 2) {
        tabBasic.style.display = "none";
        tabList.style.display = "block";
        
        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "block";
        
        tabBasic.style.zIndex = 1;
        tabList.style.zIndex = 2;

        document.forms[0].tabName.value = 'tabList';
        
        if(initFlag == 0){
        	document.ProjectSearch.carTypeInfo2.value = '<%=mp.getCarType().getName() %>';
            loadEjsGrid();	
        }
        
        initFlag++;
        
    }
}

function perPage(v) {
    document.forms[0].perPage.value = v;
    //search();
    loadEjsGrid();
}

function search(){
    //showProcessing();     // 진행중 Bar 표시
    //disabledAllBtn();     // 버튼 비활성화
    //document.ProjectSearch.command.value = "search";
    //document.ProjectSearch.action =  "/plm/servlet/e3ps/ProjectServlet";
    //document.ProjectSearch.submit();
    
    loadEjsGrid();
}

function loadEjsGrid(){
    try{
       var idx = 0;
       var D = Grids[idx].Data;
       var formName = "ProjectSearch";
       
       D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
       
       D.Data.Url = "/plm/servlet/e3ps/ProjectServlet";
       
       D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
       D.Data.Param.command = "searchGridData";
       
       D.Page.Url = "/plm/servlet/e3ps/ProjectServlet";
       D.Page.Params =  D.Data.Params;
       D.Page.Param.command = "searchGridPage";
       
       Grids[idx].Reload(D);
       
       var S = document.getElementById("Status"+idx);
       if(S) S.innerHTML="Loading";
    
    }catch(e){
        alert(e.message);
    }
}




function excelDown() {
    if(Grids[0].LoadedCount == 0){
        return;
    }
    showProcessing();
    document.ProjectSearch.command.value = "excelDownProject";
    $.fileDownload('/plm/servlet/e3ps/ProjectServlet?'+$('[name=ProjectSearch]').serialize(),{
         successCallback : function(){
             hideProcessing();   
         },
         failCallback: function(responseHtml, url) {
             hideProcessing();
         }
    });
    /* document.ProjectSearch.command.value = "excelDownProject";
    document.ProjectSearch.action =  "/plm/servlet/e3ps/ProjectServlet";
    document.ProjectSearch.submit(); */
    
    $.ajax({
        type       : "POST",
        url        : "/plm/servlet/e3ps/ProjectServlet",
        data       : $('[name=ProjectSearch]').serialize(),
        dataType   : "text",
        success    : function(data){
                     hideProcessing();
        },
        error    : function(xhr, status, error){
                     hideProcessing();
                     
        }
    });    
}


// Jquery
$(document).ready(function(){
    SuggestUtil.bind('CARTYPE', 'carTypeInfo2');
    
    $("form[name=ProjectSearch]").keypress(function(e) {
        //Enter key
        if ( e.which == 13 ) {
            search();
            return false;
        }
    });
});

//-->
</script>
</head>
<body onload="javascript:init();">
<form name="viewprg" id="">
<input type="hidden" name="tabName" value="">
<table style="width: 100%; height: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">차종 별 상세정보</td>
                          </tr>
                        </table>
                    </td>
                    <td width="176" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
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
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"></td>
          <td class="font_03">
          
          <table id="infoShow" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                일정정보
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09205") %><!-- 관련프로젝트 --></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>                  
                </tr>
                </table>
                <table id="infoHide" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab">일정정보</a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "09205") %><!-- 관련프로젝트 -->
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>                    
                </tr>
                </table>
          
          </td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
              <%
              boolean isPmo = CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("KETS_PMO");
              boolean isAdmin = CommonUtil.isAdmin();
              boolean isPlan = CommonUtil.isMember("자동차일정");
              if(isPmo || isAdmin || isPlan){ %>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:modify('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:delProgram('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
              <%} %>
                <td><table border="0" cellspacing="0" cellpadding="0">
                        <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table></td>
                    </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top">
            <!-- table width="100%" height="100%" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top">&nbsp;</td>
              </tr>
            </table -->

            <!--내용-->
            <div style="position:; width:100%; height:656px; overflow-x:auto; overflow-y:auto;">
            <div id="tabBasic" style="position:; display:none; z-index:2; ">

                <%@include file="/jsp/project/ViewProgramBasicForm.jsp" %>

            </div>
            
            <div id="tabList" style="position:; display:none; z-index:1; ">

                <%@include file="/jsp/project/ViewProgramListForm.jsp" %>

            </div>

            
            </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
        </tr>
      </table>
      </td>
  </tr>
 <!--  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr> -->
</table>
</form>
</body>
</html>
