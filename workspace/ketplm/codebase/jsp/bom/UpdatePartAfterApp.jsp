<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*,wt.epm.EPMDocument"%>
<%@ page import="e3ps.common.query.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.dms.entity.*,
                 e3ps.ecm.entity.*,
                 e3ps.edm.beans.*,
                 e3ps.bom.service.*,
                 e3ps.bom.dao.*,
                 e3ps.groupware.company.PeopleData,
                 e3ps.bom.common.iba.IBAUtil"%>
<%@ page import="ext.ket.part.util.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("poid");      // oid
    String tempClazOid = request.getParameter("tempClazOid");
    if(poid == null) poid = request.getParameter("oid");      // oid
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
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
.tabIframeWrapper {width:100%;height:650px;border:0px; margin:0px;position:relative;top:0px;}
</style>

</head>
<body>
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
    CommonUtil.tabs('tabs');
})
</script>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
	    <td valign="top" style="padding: 0px 0px 0px 0px">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	                <td>
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                        <tr>
	                            <td background="/plm/portal/images/logo_popupbg.png">
	                                <table height="28" border="0" cellpadding="0" cellspacing="0">
	                                    <tr>
	                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
	                                        <td class="font_01">부품 수정<%--부품 상세조회--%></td>
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
			                    <li><a class="tabref" href="#tabs-1" rel="/plm/ext/part/base/modifyPartAfterAppForm.do?partOid=<%=poid%>">PART</a></li>
			                    <li><a class="tabref" href="#tabs-2" rel="/plm/extcore/jsp/bom/KETBomEditor.jsp?oid=<%=poid%>">BOM</a></li>
	                        </ul>
                        </td>
				        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
	                                        <td>
	                                            <table border="0" cellspacing="0" cellpadding="0">
	                                                <tr>
	                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
	                                                        href="javascript:self.close();" class="btn_blue">닫기</a></td>
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
                        <iframe class="iframetab" src="/plm/ext/part/base/modifyPartAfterAppForm.do?partOid=<%=poid%>&tempClazOid=<%=tempClazOid%>">Load Failed?</iframe>
				    </div>
				</div>
				<!-- 2번째 tab 화면부터는 rel속성에 정의된 url을 호출합니다. -->   
				<div id="tabs-2">
				</div>
		    </div>
		</td>
    </tr>
</table>
</body>
</html>
