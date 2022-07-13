<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
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
<body>
<a href="/plm/jsp/project/adminGuide.doc"><%=messageService.getString("e3ps.message.ket_message", "00948") %><%--관리자 매뉴얼--%></a><br>
<a href="/plm/jsp/project/01_KET_CofigSetup.txt">CofigSetup</a><br>
<a href="/plm/jsp/project/02_KET_PerfTuning.txt">PerfTuning</a><br>
<a href="/plm/jsp/project/03_KET_javagen.txt">javagen</a><br>
<a href="/plm/jsp/project/04_KET_ResourceBuild.txt">ResourceBuild</a><br>
<a href="/plm/jsp/project/05_KET_CustomService.txt">CustomService</a><br>
<a href="/plm/jsp/project/06_KET_DataLoad.txt">DataLoad</a><br>

</body>
</html>
