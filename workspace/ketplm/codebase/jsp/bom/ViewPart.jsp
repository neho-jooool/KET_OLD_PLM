<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*,wt.epm.EPMDocument"%>
<%@ page
  import="e3ps.common.query.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.dms.entity.*,
                 e3ps.ecm.entity.*,
                 e3ps.edm.beans.*,
                 e3ps.bom.service.*,
                 e3ps.bom.dao.*,
                 e3ps.groupware.company.PeopleData,
                 e3ps.bom.common.iba.IBAUtil"
%>
<%@ page import="ext.ket.part.util.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("poid");      // oid
    if (poid == null)
		poid = request.getParameter("oid");      // oid
    boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/multicombo/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<style type="text/css">
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
	line-height: 22px;
}

.tabIframeWrapper {
	width: 100%;
	height: 670px;
	border: 0px;
	margin: 0px;
	position: relative;
	top: 0px;
}
</style>
<script type="text/javascript">
function lfn_onload() {
    try {
    	var isDo = false;
        if(typeof opener != 'undefined') {
        	var opener_url = opener.location.href;
        	if(opener_url != null && typeof opener_url != 'undefined') {
        		if(opener_url.indexOf('listPart') < 0 && opener_url.indexOf('bomCheckList') < 0 && opener_url.indexOf('partMassPlanList') < 0 && opener_url.indexOf('purchase') < 0 && opener_url.indexOf('wareHousingList') < 0) {
        			isDo = true;
        		} 
        	}
        }
        
        if(isDo) {
            lfn_feedbackBeforePopupViewPart();
        }
    } catch(e) {}
}

function lfn_onunload() {
	try {
	
		lfn_feedbackAfterPopupViewPart();
		
	} catch(e) {}
}

//부품상세조회 팝업에서 그 창이 열릴때 호출되는 Function
function lfn_feedbackBeforePopupViewPart() {
  try {
       opener.showProcessing();
       opener.disabledAllBtn();
  } catch(e) {}
}

//부품상세조회 팝업에서 그 창이 닫힐때 호출되는 Function
function lfn_feedbackAfterPopupViewPart() {
 try {
	 opener.hideProcessing();
	 opener.enabledAllBtn();
 } catch(e) {}
}

function lfn_goPage(partOid) {
	
	var iframetab = document.getElementById("iframeDetailPart");
	iframetab.src = "/plm/ext/part/base/modifyPartForm.do?partOid="+ partOid;
	
}

//BOM Editor에서 저장시 로딩바를 생성한다.
function lfn_feedbackBeforeSaveBomEditor() {
try {
    showProcessing();
    disabledAllBtn();
} catch(e) {}
}
function lfn_feedbackAfterSaveBomEditor() {
try {
    hideProcessing();
    enabledAllBtn();
} catch(e) {}
}
</script>
</head>
<body onload="javascript:lfn_onload();" onunload="javascript:lfn_onunload();">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
  
  <script type="text/javascript">
  
            $(document).ready(function() {
            	var tabs = CommonUtil.tabs('tabs');
            	
                $('#tabs').tabs({
                    activate: function(event ,ui){
                       if(ui.newTab.index() == 1){
                    	   
                    	   try{
                    		   $(".iframetab")[1].contentWindow.storeLoad();
                    		   //ext js 로드 함수 부름 탭전환시에 데이타 안보이는 현상때문에
                    	   }
                           catch (e) {
							// TODO: handle exception
                           }
                           
                           window.resizeTo(screen.availWidth, screen.availHeight);//bom을 최대로 펴주기 위함
                       }
                    }
                });
                
                if(typeof opener != 'undefined') {
                    var opener_url = opener.location.href;
                    if(opener_url != null && typeof opener_url != 'undefined') {
                        if(opener_url.indexOf('bomCheckList') > 0 || opener_url.indexOf('wareHousingList') > 0) {
                        	$("#tabs").tabs({active : 1 });
                        } 
                    }
                }

                $(window).resize(function(){
                    //window.console.log("window height : "+$(window).height());
                    
                    $('#tabs').find('.tabIframeWrapper').each(function() {//윈도우 크기에 맞춰서 div 리사이징
                    	$(this).height($(window).height()-100);
                    });
                    
                    
                });
            })
        </script>
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <td valign="top" style="padding: 0px 0px 0px 0px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe) ? "display:none" : ""%>">
          <tr>
            <td>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td background="/plm/portal/images/logo_popupbg.png">
                    <table height="28" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01572")%><%--부품 상세조회--%></td>
                        <td width="10"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <div id="tabs">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="left">
                <ul>
                  <li><a class="tabref" href="#tabs-1" rel="/plm/ext/part/base/viewDetailPartForm.do?isIframe=<%=isIframe %>&partOid=<%=poid%>">PART</a></li>
                  <li><a class="tabref" href="#tabs-2" rel="/plm/extcore/jsp/bom/KETBomEditor.jsp?oid=<%=poid%>">BOM</a></li>
                </ul>
              </td>
              <td align="right">
                <table border="0" cellspacing="0" cellpadding="0" style="<%=(isIframe) ? "display:none" : ""%>">
                  <tr>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <td>
                          <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue">닫기</a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table>
                        </td>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
          <!-- 첫번째 tab 화면 -->
          <div id="tabs-1" class="tabMain">
            <div class="tabIframeWrapper">
              <iframe id="iframeDetailPart" class="iframetab" src="/plm/ext/part/base/viewDetailPartForm.do?isIframe=<%=isIframe %>&partOid=<%=poid%>">Load Failed?</iframe>
            </div>
          </div>
          <!-- 2번째 tab 화면부터는 rel속성에 정의된 url을 호출합니다. -->
          <div id="tabs-2"></div>
        </div>
      </td>
    </tr>
  </table>
</body>
</html>
