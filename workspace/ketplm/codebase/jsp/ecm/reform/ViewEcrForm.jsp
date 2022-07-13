<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<jsp:useBean id="tabId" class="java.lang.String" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
String redirectURL = StringUtils.trim(request.getParameter("redirectURL"));
redirectURL = (!redirectURL.equals("")) ? (new org.apache.commons.codec.net.URLCodec().decode(request.getParameter("redirectURL"))) : "";

String title = messageService.getString("e3ps.message.ket_message", "02542");   // 제품 ECR 상세조회
if(redirectURL.indexOf("KETMoldChangeRequest") > -1) title = messageService.getString("e3ps.message.ket_message", "01006");   // 금형 ECR 상세조회

// 결재대상화면 페이지에서 탭을 고정하기 위한 파라미터 처리
String reqTabId = StringUtil.checkReplaceStr(request.getParameter("tabId"), "1");
boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));
%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=title %><%--제품 or 금형 ECR 상세조회--%></title>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
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

<script language="javascript">
var timerIdEcr = null;

$(document).ready(function(){

	$(window).resize(function(){
		resizeInnoditor();
	});
	
	timerIdEcr = setInterval(resizeInnoditor, 100);
	
});


function resizeInnoditor(){
	
	var innoditor_0 =$('#ecrBasicIFrame').get(0).contentWindow.document.getElementById('innoditor_0'); //이노디터 에디터 0의 오브젝트
    var innoditor_1 =$('#ecrBasicIFrame').get(0).contentWindow.document.getElementById('innoditor_1'); //이노디터 에디터 1의 오브젝트
    
    if(innoditor_0 && innoditor_1){
    	if(timerIdEcr != null){
    		clearInterval(timerIdEcr);
    		timerIdEcr = null;
    		window.moveTo(0,0);
            window.resizeTo(screen.availWidth, screen.availHeight);
    	}
    	
    	var windowHeight = window.outerHeight;
        var innoditor_0ScrollHeight = innoditor_0.contentDocument.body.scrollHeight+50; //이노디터 에디터 0의 높이
        var innoditor_1ScrollHeight = innoditor_1.contentDocument.body.scrollHeight+50; //이노디터 에디터 1의 높이
        console.log("innoditor_0ScrollHeight : " + innoditor_0ScrollHeight);
        console.log("innoditor_1ScrollHeight : " + innoditor_1ScrollHeight);
        var innoditorHeight = innoditor_0ScrollHeight > innoditor_1ScrollHeight ? innoditor_1ScrollHeight : innoditor_1ScrollHeight; //높이가 더 높은 에디터의 height을 기준으로 한다
        
        if(windowHeight < 1000){//기본사이즈일 때 원래 사이즈로 롤백
            
            $('#ecrTab').height(690);
            $('#tabBasic').height(690);
            $('#ecrBasicIFrame').height(690);
            $('#ecrBasicIFrame').get(0).contentWindow.document.getElementById('ecrDiv1').style.height = '650px';
            
            $(innoditor_0).width(470);
            $(innoditor_0).height(200);
            $(innoditor_1).width(470);
            $(innoditor_1).height(200);
            
            //$('#ecrBasicIFrame').get(0).contentWindow.g_nEditorWidth = 470;
            //$('#ecrBasicIFrame').get(0).contentWindow.g_nEditorHeight = 200;
            
            //$('#ecrBasicIFrame').get(0).contentWindow.fnResizeEditor(0, 470, 200);
            //$('#ecrBasicIFrame').get(0).contentWindow.fnResizeEditor(1, 470, 200);
            
        }else{//브라우저 리사이즈(확대) 시 에디터 크기 최적화
            const height_ = windowHeight -150;
            $('#ecrTab').height(height_);
            $('#tabBasic').height(height_);
            $('#ecrBasicIFrame').height(height_);
            
            $('#ecrBasicIFrame').get(0).contentWindow.document.getElementById('ecrDiv1').style.height = height_-50 + 'px';
             
            $(innoditor_0).width(900);
            $(innoditor_0).height(innoditorHeight);
            $(innoditor_1).width(900);
            $(innoditor_1).height(innoditorHeight);
            
            //$('#ecrBasicIFrame').get(0).contentWindow.g_nEditorWidth = 900;
            //$('#ecrBasicIFrame').get(0).contentWindow.g_nEditorHeight = innoditorHeight;
            
            //$('#ecrBasicIFrame').get(0).contentWindow.fnResizeEditor(0, 900, innoditorHeight);
            //$('#ecrBasicIFrame').get(0).contentWindow.fnResizeEditor(1, 900, innoditorHeight);
            
        }
    }
	
    
	
}

//처리중 화면 전환
function clickTabBtn(tabId) {
    var tabBasic = document.getElementById("tabBasic");
    var tabCompetent = document.getElementById("tabCompetent");
    var tabMeeting = document.getElementById("tabMeeting");
    if(tabId == 2) {
        tabBasic.style.visibility = "hidden";
        tabCompetent.style.visibility = "visible";
        tabMeeting.style.visibility = "hidden";
        
        var info1st = document.getElementById("info1st");
        info1st.style.display = "none";
        var info2nd = document.getElementById("info2nd");
        info2nd.style.display = "block";
        var info3rd = document.getElementById("info3rd");
        info3rd.style.display = "none";

        tabBasic.style.zIndex = 1;
        tabCompetent.style.zIndex = 2;
        tabMeeting.style.zIndex = 1;
        
    } else if(tabId == 3) {
        tabBasic.style.visibility = "hidden";
        tabCompetent.style.visibility = "hidden";
        tabMeeting.style.visibility = "visible";
        
        var info1st = document.getElementById("info1st");
        info1st.style.display = "none";
        var info2nd = document.getElementById("info2nd");
        info2nd.style.display = "none";
        var info3rd = document.getElementById("info3rd");
        info3rd.style.display = "block";

        tabBasic.style.zIndex = 1;
        tabCompetent.style.zIndex = 1;
        tabMeeting.style.zIndex = 2;
        
    } else {    // if(tabId == 1) {
        tabBasic.style.visibility = "visible";
        tabCompetent.style.visibility = "hidden";
        tabMeeting.style.visibility = "hidden";
        
        var info1st = document.getElementById("info1st");
        info1st.style.display = "block";
        var info2nd = document.getElementById("info2nd");
        info2nd.style.display = "none";
        var info3rd = document.getElementById("info3rd");
        info3rd.style.display = "none";

        tabBasic.style.zIndex = 2;
        tabCompetent.style.zIndex = 1;
        tabMeeting.style.zIndex = 1;
        
    }
}

function init() {
	
    clickTabBtn(<%=(StringUtil.checkString(tabId))?tabId:reqTabId %>);
    
    loadEcrData();
    
}
function loadEcrData() {
	var redirectURL = "<%=redirectURL %>";
	
	var ecrBasicIFrameObj = document.getElementById("ecrBasicIFrame");
	ecrBasicIFrameObj.src = redirectURL
					      + "&tabName=ecrBasic"
					      ;
	
    var ecrCompetentUrl = redirectURL;
    var ecrCompetentIFrameObj = document.getElementById("ecrCompetentIFrame");
    ecrCompetentIFrameObj.src = ecrCompetentUrl
                              + "&tabName=ecrCompetent"
                              ;
    
    var ecrMeetingUrl = redirectURL;
    var ecrMeetingIFrameObj = document.getElementById("ecrMeetingIFrame");
    ecrMeetingIFrameObj.src = ecrMeetingUrl
						    + "&tabName=ecrMeeting"
						    ;
	
}

function lfn_reloadWhole() {
	
    /* 
	document.getElementById('ecrBasicIFrame').contentWindow.location.reload();
    document.getElementById('ecrCompetentIFrame').contentWindow.location.reload();
	document.getElementById('ecrMeetingIFrame').contentWindow.location.reload();
	*/
	
	loadEcrData();
}

</script>

</head>
<body onload="javascript:init();">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=title %><%--제품 or 금형 ECR 상세조회--%></td>
                  <!-- td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00204") %><%--ECR 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00202") %><%--ECR 검색--%></td>
                </tr -->
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table>
      <table width="100%" height="20" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="20">&nbsp;</td>
            <td class="font_03">
                <table id="info1st" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%>
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
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09145") %><%--검토부서--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03251") %><%--회의록--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>                    
                </tr>
                </table>
                <table id="info2nd" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "09145") %><%--검토부서--%>
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
                                <a href="javascript:clickTabBtn(3);" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03251") %><%--회의록--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>                      
                </tr>
                </table>
                <table id="info3rd" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09145") %><%--검토부서--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>                      
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "03251") %><%--회의록--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>                
            </td>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe)?"display:none;":""%>">
                <tr>
                    <td id="searchMoldPlan">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onclick="javascript:self.close();" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    
                </tr>
                </table>
            </td>            
        </tr>
      </table>

      <table id="ecrTab" width="100%" height="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif"></td>
        </tr>
        <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top">
            <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top">&nbsp;</td>
              </tr>
            </table>
            <!--내용-->
            <div id="tabBasic" style="position:absolute; visibility:hidden; width:98%; height:690px; z-index:2; top: <%=(isIframe)?"30px":"80px" %>; left: 10px">
              <iframe src="" id="ecrBasicIFrame" name="ecrBasicIFrame" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </div>
            <div id="tabCompetent" style="position:absolute; visibility:visible; width:98%; height:690px; z-index:1; top: <%=(isIframe)?"30px":"80px" %>; left: 10px">
              <iframe src="" id="ecrCompetentIFrame" name="ecrCompetentIFrame" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </div>
            <div id="tabMeeting" style="position:absolute; visibility:visible; width:98%; height:690px; z-index:1; top: <%=(isIframe)?"30px":"80px" %>; left: 10px">
              <iframe src="" id="ecrMeetingIFrame" name="ecrMeetingIFrame" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </div>
          </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
        </tr>
      </table></td>
  </tr>
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
</body>
</html>
