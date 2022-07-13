<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.common.web.ParamUtil" %>
<%@page import="wt.org.WTUser" %>
<%@page import="wt.session.SessionHelper" %>
<%@page import="wt.lifecycle.*" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
// 로그인 사용자
WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
String loginUserOid = (String)CommonUtil.getOIDString(user);
String loginUserName = user.getFullName();

// ECO의 기본사항탭의 제품 정보

// ex) [615602-5]
String[] pNums = request.getParameterValues("pNums");
// ex) 615602-5
String pNumsTemp = org.springframework.util.StringUtils.arrayToCommaDelimitedString(pNums);

// click button : ex) 1  ==>> 1:child 2:bom
String epmBomCls = ParamUtil.getParameter(request, "epmBomCls");
if("".equals(epmBomCls)) {
    epmBomCls = "1";//1:child 2:bom
}

// ex) ""
String isChodo = ParamUtil.getParameter(request, "isChodo");
// ex) 1
String prodMoldCls = ParamUtil.getParameter(request, "prodMoldCls");
// ex) ECO-1412-102
String currentEcoNo = ParamUtil.getParameter(request, "currentEcoNo");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "활동추가") %><%--활동추가--%></title>

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
-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	// 초기화면에서 특정 탭을 활성화한다.
	clickTabBtn(<%=epmBomCls %>);
	
	// 각 탭별로 URL 가져오기
	loadData('N');
	
})

</script>
<!-- script language=JavaScript src="/plm/jsp/ecm/SearchActivityPopup.js"></script -->
<script type="text/javascript">

//처리중 화면 전환
function clickTabBtn(tabId) {
  if(tabId == 1) { // 자부품 탭 클릭시에 부품검색 탭은 숨김
      
	  $("#tabBomChild").show();
      $("#tabBom").hide();
      
	  $("#infoHideChild").show();
	  $("#infoHideBom").hide();
      
	  $("#tabBomChild").zIndex(2);
	  $("#tabBom").zIndex(1);
      
  }
  else if(tabId == 2) { // 부품검색 탭 클릭시에 자부품검색 탭은 숨김
      
      $("#tabBomChild").hide();
      $("#tabBom").show();
      
      $("#infoHideChild").hide();
      $("#infoHideBom").show();
      
      $("#tabBomChild").zIndex(1);
      $("#tabBom").zIndex(2);
      
  }
  
  $("input[name='epmBomCls']").val(tabId);
}

function loadData(searchFlag) {
	showProcessing();
    
	// 자부품 검색화면 로딩
	$("#searchActivityForm").attr("target", "bomChildIFrame");
    $("#searchActivityForm").attr("action", "/plm/jsp/ecm/reform/SearchActivityBomChildForm.jsp?searchFlag="+searchFlag);
    $("#searchActivityForm").submit(function(){
    	
         $("#bomChildIFrame").load(function() {
           //alert(this);
           hideProcessing();
        });
        
    }).submit();
    
    // 부품 검색화면 로딩
    $("#searchActivityForm").attr("target", "bomIFrame");
    $("#searchActivityForm").attr("action", "/plm/jsp/ecm/reform/SearchActivityBomSearchForm.jsp");
    $("#searchActivityForm").submit(function(){
        
         
         $("#bomIFrame").load(function() {
           //alert(this);
        });
        
    }).submit();
    
}

// 확인
function onSelect(arr) {
    if(arr.length > 0) {
    	
    	if(typeof opener != 'undefined') {
    		opener.insertRelEpmLine(arr);
            
        } else if(typeof parent != 'undefined') {
            parent.selectModalDialog(arr);
            
        }
        
    } else {
    	alert("코드를 선택하십시오.");
        return;
    }
}

//부품 상세조회 팝업
function viewPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

</script>
</head>
<body>
<form name="searchActivityForm" id="searchActivityForm" method="post" action="">
<input type="hidden" name="cmd" value="search">
<input type="hidden" name="fromPage" value="">
<input type="hidden" name="page" value="">
<input type="hidden" name="totalCount" value="0">
<input type="hidden" name="sortColumn" value="2 ASC">
<input type="hidden" name="param" value="parent.">
<input type="hidden" name="oid" value="">

<input type="hidden" name="pNums" value="<%=pNumsTemp %>">
<input type="hidden" name="epmBomCls" value="<%=epmBomCls %>">
<input type="hidden" name="workerId" value="<%=loginUserOid %>">
<input type="hidden" name="workerName" value="<%=loginUserName %>">
<input type="hidden" name="isChodo" value="<%=isChodo %>">
<input type="hidden" name="prodMoldCls" value="<%=prodMoldCls %>">
<input type="hidden" name="currentEcoNo" value="<%=currentEcoNo %>">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
            <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03245") %><%--활동추가--%></td>
              </tr>
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table>
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20">&nbsp;</td>
          <td class="font_03">
          
            <!-- 자부품 -->
            <table id="infoHideChild" border="0" cellspacing="0" cellpadding="0" style="display: none;">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_1.png"></td>
                      <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04430") %><%--자부품--%></td>
                      <td><img src="/plm/portal/images/tab_2.png"></td>
                    </tr>
                  </table>
                </td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                        <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></a>
                      </td>
                      <td><img src="/plm/portal/images/tab_5.png"></td>
                    </tr>
                  </table>
                </td>                
              </tr>
            </table>
           
            <!-- 부품검색 -->
            <table id="infoHideBom" border="0" cellspacing="0" cellpadding="0" style="display: none;">
              <tr>               
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                        <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04430") %><%--자부품--%></a>
                      </td>
                      <td><img src="/plm/portal/images/tab_5.png"></td>
                    </tr>
                  </table>
                </td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_1.png"></td>
                      <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></td>
                      <td><img src="/plm/portal/images/tab_2.png"></td>
                    </tr>
                  </table>
                </td> 
              </tr>
            </table>            
            
          </td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" onfocus="this.blur();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>                
              </tr>
            </table>
          </td>
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
            <div id="tabBomChild" style="position:; display:none; z-index:1; ">

                <iframe src="" id="bomChildIFrame" name="bomChildIFrame" width="100%" height="500px" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
                
            </div>
            <div id="tabBom" style="position:; display:none; z-index:1; ">

                <iframe src="" id="bomIFrame" name="bomIFrame" width="100%" height="500px" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
                
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
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
</body>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</html>
