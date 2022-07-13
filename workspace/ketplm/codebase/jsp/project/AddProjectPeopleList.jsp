<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="/jsp/common/error.jsp"%>

<%@page import="e3ps.common.web.ParamUtil"%>

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  // EJS TreeGrid Start
  response.addHeader("Cache-Control","max-age=1, must-revalidate");
  // EJS TreeGrid End
%>

<%
    String oid = ParamUtil.getStrParameter(request.getParameter("oid"));

    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<base target="_self">

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<%@include file="/jsp/common/context.jsp"%>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 5px;
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
    function view(id) {
        document.forms[0].action = "/plm/jsp/groupware/company/selectPeopleView.jsp?oid="+id;
        document.forms[0].submit();
    }

    function search() {
        document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet?command=searchPeople";
        document.forms[0].submit();
    }

    function seletPeople() {

        var G = Grids[0];
        var arr = new Array();
        var subArr = new Array();
        var idx = 0;

        if( G != null ){

            var R = G.GetSelRows();

            if( R.length == 0 ){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01661") %><%--사람을 선택해 주십시오--%>');
                return;
            }

            for ( var i=0; i<R.length; i++ ) {
                 subArr = new Array();
                 subArr[0] = R[i].WtuserOID;  //wtuser oid
                 subArr[1] = R[i].Poid;       //people oid
                 subArr[2] = R[i].Doid;       //dept oid
                 subArr[3] = R[i].Uid;        //uid
                 subArr[4] = R[i].UserName;   //name
                 subArr[5] = R[i].DeptName;   //dept name
                 subArr[6] = R[i].Duty;       //duty
                 subArr[7] = R[i].Dutycode;   //duty code
                 subArr[8] = R[i].Email;      //email
                 arr[idx++] = subArr;
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

    function thiscolse(){
        self.close();
    }

    function viewTodo2(oid){
        var url = "/plm/jsp/project/ListMyProjectPop.jsp?command=MySearch&wtUser="+oid;
        //openOtherName(url,"ViewTemplate","850","680","status=1,scrollbars=yes,resizable=1");
        //alert("aaaaaaaaaaaaaaaaaaaaa");
        window.open(url, '', "scrollbars=yes,resizable=yes,status=1, menu=no, width=850, height=680");
        //window.open(linkUrl,'',"scrollbars=yes,resizable=yes,status=1, menu=no, width=850, height=680");
    }

    function onKeyPress() {
        if ( window.event ) {
            if (window.event.keyCode == 13) search();
        } else return;
    }
    document.onkeypress = onKeyPress;
//-->
</script>
</head>

<body onload='document.forms[0].keyvalue.focus()'>

<form method="post" name="selectPeopleList">

<!-- hidden begin -->
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="invokeMethod" value="<%=invokeMethod%>">
<!-- hidden end -->

        <table style="width: 480px;">
            <tr>
                <td valign="top">
                    <table style="width: 480px;">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table style="height: 28px;">
                                    <tr>
                                        <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01541")%><%--부서 및 사원--%>
                                            <%
                                                if (!searchCondition.getString("deptName").equals("")) {
                                             %>[<%=searchCondition.getString("deptName")%>]<%
                                                }
                                             %>
                                        </b></td>
                                        <td style="width: 10px;"></td>
                                    </tr>
                                </table>
                            </td>
                            <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                    <table style="width: 480px;">
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>

                    <table style="width: 480px;">
                        <tr>
                            <td>&nbsp;</td>
                            <td align="right">
                                <table>
                                    <tr>
                                        <td><input type="text" name="keyvalue" class="txt_field" style="width: 150px;"
                                            value="<%=searchCondition.getString("keyvalue")%>">&nbsp;&nbsp;</td>
                                        <td>
                                            <table>
                                                <tr>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
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
                                                        href="javascript:seletPeople();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a>
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
                            </td>
                        </tr>
                    </table>
                    <table style="width: 480px;">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table> <!-- EJS TreeGrid Start -->
                    <div class="content-main">
                        <div class="container-fluid">
                            <div class="row-fluid">
                                <div style="WIDTH: 100%; HEIGHT: 100%">
                                    <bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/project/AddProjectPeopleGridLayout.jsp"
                                        Layout_Param_Mode="<%=mode %>" Data_Url="/plm/jsp/common/searchGridData.jsp" Data_Method="POST"
                                        Data_Param_Result="<%=tgData %>" Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                                        Export_Data="TGData" Export_Param_File="Search_DocMultiApp_List"></bdo>
                                </div>
                            </div>
                        </div>
                    </div> <!-- EJS TreeGrid Start -->
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
