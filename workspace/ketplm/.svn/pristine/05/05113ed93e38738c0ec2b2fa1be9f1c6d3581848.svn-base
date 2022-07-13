<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="/jsp/common/error.jsp"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<%
    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");

    if ( mode == null || mode.length() == 0 || !mode.equals("m") )
        mode = "s";
    if ( invokeMethod == null || invokeMethod.length() == 0 )
        invokeMethod = "";

   // String param = "mode=" + mode + "&invokeMethod=" + invokeMethod;
   // String mainURL = "/plm/servlet/e3ps/ProjectServlet?command=searchAnalysisPeople&" + param;
    
    // [START] [PLM System 1차 고도화] 조직도 검색 처음 로딩시 화면만 나오게 하여 체감 속도를 높인다, 2014-06-17, 김태현
    // mainURL += "&init=true";
    // [END] [PLM System 1차 고도화] 조직도 검색 처음 로딩시 화면만 나오게 하여 체감 속도를 높인다, 2014-06-17, 김태현

    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<base target="_self">
<TITLE><%=messageService.getString("e3ps.message.ket_message", "01541") %></TITLE>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->
<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/common/multicombo.jsp"%>
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
table th, table td {padding: 0}
img {
    vertical-align: middle;
}
input {
    vertical-align:middle;line-height:22px;
}
-->
</style>
<script type="text/javascript">
<!--
    function search() {
    	try{
            var D = Grids[0].Data;
            var formName = 'selectPeopleList';
            D.Data.Url = "/plm/servlet/e3ps/ProjectServlet";
            D.Data.Param.command = "searchAnalysisPeople";
            Grids[0].Reload(D);
            var S = document.getElementById("Status0");
            if(S) S.innerHTML="Loading";
         }catch(e){
             alert(e.message);
         }
    }

    function seletPeople() {

        var G = Grids[0];
        var arr = new Array();
        var subArr = new Array();
        var idx = 0;
        
        if(G.GetFirstVisible() == null){
        	alert('<%=messageService.getString("e3ps.message.ket_message", "01813")%>');
        	return;
        }
        
        if( G != null){

        	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        		<%if(!"m".equals(mode)){%>
        		if(tRow.Selected == true){
      			<%}%>
        			subArr = new Array();
                    subArr[0] = tRow.WtuserOID;  //wtuser oid
                    subArr[1] = tRow.Poid;       //people oid
                    subArr[2] = tRow.Doid;       //dept oid
                    subArr[3] = tRow.Uid;        //uid
                    subArr[4] = tRow.UserName;   //name
                    subArr[5] = tRow.DeptName;   //dept name
                    subArr[6] = tRow.Duty;       //duty
                    subArr[7] = tRow.Dutycode;   //duty code
                    subArr[8] = tRow.Email;      //email
                    arr[idx++] = subArr;	
               <%if(!"m".equals(mode)){%>
               }
               <%}%>
                
            }
        }

    <%  if ( invokeMethod.length() == 0 ) {  %>
        //modal dialog
        selectModalDialog(arr);
    <%  } else {  %>
        //open window
        selectOpenWindow(arr);
    <%  }  %>
    }

    function selectModalDialog(arrObj) {
        window.returnValue= arrObj;
        window.close();
    }

    <%  if(invokeMethod.length() > 0) {  %>

    function selectOpenWindow(arrObj) {
        //...이하 원하는 스크립트를 만들어서 작업...
        if(opener) {
            if(opener.<%=invokeMethod%>) {
                opener.<%=invokeMethod%>(arrObj);
            }
        }

        if(parent.opener) {
            if(parent.opener.<%=invokeMethod%>) {
                parent.opener.<%=invokeMethod%>(arrObj);
            }
        }
        thiscolse();
    }

    <%  }  %>
	function thiscolse() {
		self.close();
	}

	function onKeyPress() {
		if (window.event) {
			if (window.event.keyCode == 13)
				search();
		} else
			return;
	}
	document.onkeypress = onKeyPress;
	
	function movePeople(){
        seletPeople();
    }
	
	$(document).ready(function() {
    	//window.resizeTo(800,600);
        window.dialogWidth = 450+'px';
		window.dialogHeight = 350+'px';
	});
	
//-->
</script>
</head>
<body>
<form method="post" name="selectPeopleList">
<!-- hidden begin -->
<input type="text" name="mode" value="<%=mode%>" style="display: none;">
<input type="hidden" name="invokeMethod" value="<%=invokeMethod%>">

<!-- hidden end -->
    <table style="width: 100%;">
        <tr>
            <td valign="top">
                <table style="width: 100%;">
                    <tr>
                        <td background="/plm/portal/images/logo_popupbg.png">
                            <table style="height: 28px;">
                                <tr>
                                    <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                    <td class="font_01">담당자</td>
                                    <td style="width: 10px;"></td>
                                </tr>
                            </table>
                        </td>
                        <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                    </tr>
                </table>
                <table style="width: 100%;">
                    <tr>
                        <td class="space10"></td>
                    </tr>
                </table>
                <table style="width: 100%;">
                    <tr>
                        <td align="right" valign="top">
                            <table>
                                <tr>
                                    <td>
                                        <table>
                                            <tr>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                    href="javascript:movePeople();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a>
                                                </td>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td width="5">&nbsp;</td>
                                    <td>
                                        <table>
                                            <tr>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                    href="javascript:thiscolse();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                                                </td>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <table style="width: 100%;">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table> 
                            <table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td valign="top" height="100%">
                                        <!-- EJS TreeGrid Start -->
                                        
                                        <div style="WIDTH: 100%; HEIGHT:300px;">
                                            <bdo Debug="1" AlertError="0" Layout_Url="/plm/extcore/jsp/arm/AddAnalysisPeopleGridLayout.jsp"
                                                Layout_Param_Mode="<%//=mode %>" Data_Url="/plm/jsp/common/searchGridData.jsp" Data_Method="POST"
                                                Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData"
                                                Export_Param_File="Search_DocMultiApp_List"></bdo>
                                        </div> 
                                        <!-- EJS TreeGrid Start -->
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript">
setTimeout(function(){
	search();
}, 100);
</script>
</body>
</html>
