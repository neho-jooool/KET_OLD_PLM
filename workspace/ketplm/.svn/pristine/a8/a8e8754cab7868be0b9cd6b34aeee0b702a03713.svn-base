<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
    <title>Floatutorial: Tutorial 8 - all steps combined</title>
     <link rel="stylesheet" href="/plm/portal/css/e3ps.css">
    <style type="text/css">
        #container
        {
            width: 100%;
            margin: 0;
            /* border-top: 3px solid #191970; */
            background-color: #fff;

        }
        #leftnav
        {
            float: left;
            margin: 0;
            padding: 0.1em 0 0 0;
        }
        #content
        {
            top:0;
            margin-left: 170px;
            border: 0;
            /* border-left: 1px solid gray; */
            /* padding: 1em; */
            /* max-width: 36em; */

            overflow-x:auto;
            overflow-y:auto;
            scrollbar-highlight-color:#f4f6fb;
            scrollbar-3dlight-color:#c7d0e6;
            scrollbar-face-color:#f4f6fb;
            scrollbar-shadow-color:#f4f6fb;
            scrollbar-darkshadow-color:#c7d0e6;
            scrollbar-track-color:#f4f6fb;
        }

        #footer
        {
            clear: both;
            margin: 0;
            padding: .5em;
            color: #333;
            background-color: #ddd;
            border-top: 1px solid gray;
        }

        /* 왼쪽 메뉴 */
        #navcontainer { margin-left: 0; }

        #navcontainer ul
        {
            margin: 0;
            padding: 0;
            list-style-type: none;
            font-family: verdana, arial, Helvetica, sans-serif;
        }

        #navcontainer li { margin: 0; }

        #navcontainer a
        {
            display: block;
            padding: 5px 10px;
            width: 140px;
            color: #000;
            background-color: #ADC1AD;
            text-decoration: none;
            border-top: 1px solid #5F9EA0;
            border-left: 1px solid #5F9EA0;
            border-bottom: 1px solid #333;
            border-right: 1px solid #333;
            font-weight: bold;
            font-size: .8em;
            background-image: url(images/vertical06.jpg);
            background-repeat: no-repeat;
            background-position: 0 0;
        }

        #navcontainer a:hover
        {
            color: #000;
            background-color: #889E88;
            text-decoration: none;
            border-top: 2px solid #4682B4;
            border-left: 2px solid #4682B4;
            border-bottom: 2px solid #4682B4;
            border-right: 2px solid #4682B4;
            /*
            border-top: 1px solid #333;
            border-left: 1px solid #333;
            border-bottom: 1px solid #5F9EA0;
            border-right: 1px solid #5F9EA0;
            */
            background-image: url(images/vertical06a.jpg);
            background-repeat: no-repeat;
            background-position: 0 0;
        }

        #navcontainer ul ul li { margin: 0; }

        #navcontainer ul ul a
        {
            display: block;
            padding: 5px 5px 5px 30px;
            width: 125px;
            color: #000;
            background-color: #C5D8C5;
            text-decoration: none;
            font-weight: normal;
        }

        #navcontainer ul ul a:hover
        {
            color: #000;
            background-color: #889E88;
            text-decoration: none;
        }

        body {
            background-image: url(/plm/portal/images/img_default/background2.gif);
            background-repeat:repeat-x;
            background-color: #ffffff;

            /* text-align: center;*/
            margin: 24px 10px 0 15px;
            padding: 0 0 0 0.2em;
            border: 0;
            font-family: Arial, Helvetica, sans-serif;
            color: #000;
            /*background-color: #F5F5DC;*/

            overflow-x:hidden;
            overflow-y:hidden;
            scrollbar-highlight-color:#f4f6fb;
            scrollbar-3dlight-color:#c7d0e6;
            scrollbar-face-color:#f4f6fb;
            scrollbar-shadow-color:#f4f6fb;
            scrollbar-darkshadow-color:#c7d0e6;
            scrollbar-track-color:#f4f6fb;
        }


    </style>
</head>
<SCRIPT LANGUAGE="JavaScript">
<!--
function openProgLayer(flag) {
    doOnOffLayer();
}

function closeProgLayer() {
    doOnOffLayer(false);
}
// -->
</SCRIPT>
<body  onResize="onLayoutForLayer();">
<!--
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
  <tr>
    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
    <td class="font_01">관리자 Menu</td>
    <td align="right" style="padding:8px 0px 0px 0px">&nbsp;</td>
    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
  </tr>
</table>
 -->
<div id="container">
    <div id="leftnav">
        <div id="navcontainer">
            <ul id="navlist">
                <%  if( CommonUtil.isAdmin() ) { %>
                <li><a href="#">PLM</a>
                    <ul >
                        <li><a href="/plm/jsp/project/WfProcessInfo.jsp" target='list'><%=messageService.getString("e3ps.message.ket_message", "00695") %><%--객체정보--%></a></li>
                        <!--
                        <li><a href="#">Subitem three</a></li>
                        <li><a href="#">Subitem four</a></li>
                         -->
                        <li><a href="/plm/jsp/project/authInfo.jsp" target='list'><%=messageService.getString("e3ps.message.ket_message", "00382") %><%--PMS 권한정보--%></a></li>
                        <li><a href="/plm/jsp/project/classinfo.jsp" target='list'>ClassInfo</a></li>
                        <li><a href="/plm/jsp/project/etcInfo.jsp" target='list'><%=messageService.getString("e3ps.message.ket_message", "01144") %><%--기타 정보--%></a></li>
                        <li><a href="/plm/jsp/project/manuFile.jsp" target='list'><%=messageService.getString("e3ps.message.ket_message", "00314") %><%--Manual--%> </a></li>
                        <li><a href="/plm/jsp/groupware/company/adminCompanyfrm.jsp" target='list'><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></a></li>
                    </ul>
                </li>
                <%  } %>
                <!--
                <li><a href="#">Item two</a></li>
                <li><a href="#">Item three</a></li>
                <li><a href="#">Item four</a></li>
                 -->
            </ul>
        </div>
    </div>
    <div id="content">
        <iframe src="" id="list" name="list" frameborder="0" width="100%" height="600" leftmargin="0" topmargin="0" scrolling="auto"></iframe>
    </div>
    <!--
    <div id="footer">
        Footer
    </div>
     -->
 </div>

<jsp:include page="/jsp/common/inc_DownLoadProcessing.jsp" flush="true"/>
</body>
</html>

<script>
    var listDiv = document.getElementById("list");
    //alert(listDiv.offsetHeight +'\n' + document.body.clientHeight + '\n' + parent.document.body.clientHeight);
    h1 = parent.document.body.clientHeight - 24;
    listDiv.height = h1;
</script>
