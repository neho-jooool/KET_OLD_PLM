<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<html>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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

            /* text-align: center;*/
            margin: 24px 10px 0 15px;
            padding: 0 0 0 0.2em;
            border: 0;
            font-family: Arial, Helvetica, sans-serif;
            color: #000;
            /*background-color: #F5F5DC;*/

            overflow-x:hidden;
            overflow-y:yes;
            scrollbar-highlight-color:#f4f6fb;
            scrollbar-3dlight-color:#c7d0e6;
            scrollbar-face-color:#f4f6fb;
            scrollbar-shadow-color:#f4f6fb;
            scrollbar-darkshadow-color:#c7d0e6;
            scrollbar-track-color:#f4f6fb;
        }


    </style>
</head>
<body>
<li>검토 프로젝트 채번 규칙</li><br>
<%=messageService.getString("e3ps.message.ket_message", "00895") %><%--공통--%>( <%=messageService.getString("e3ps.message.ket_message", "03327") %><%--자동차 A  /  전자 E--%> )<br>
년도 : 2자리 <br>
<%=messageService.getString("e3ps.message.ket_message", "00970") %><%--구분 : A 검토--%><br>
Seq 번호 :3자리<br>
프로젝트 번호 : A11A001<br>
* 기타 사항 : 2012년부터 채번 관련  소스 수정 필요<br>
-jsp/project/ActionProject.jsp<br>
-라인 : 114~ <br>
    DecimalFormat decimalformat = new DecimalFormat("000"); <br>
    projectNo = decimalformat.format(Long.parseLong(projectNo)+50);<br>
-2줄 삭제 <br>

<br><br>
<li>제품 프로젝트 채번 규칙</li><br>
<%=messageService.getString("e3ps.message.ket_message", "00895") %><%--공통--%>( <%=messageService.getString("e3ps.message.ket_message", "03327") %><%--자동차 A  /  전자 E--%> )<br>
년도 : 2자리 <br>
구분 : P(Proto), B(Pilot), D(연구개발), C(추가금형)<br>
Seq 번호 :3자리<br>
제품 프로젝트 번호 : A11P001, A11B001, A11D001, A11C001 <br>

<li><%=messageService.getString("e3ps.message.ket_message", "00320") %><%--Migration 관련--%></li><br>
완료된 프로젝트 Migration 할때 이벤트 조건처리<br>
경로 : e3ps.project.historyprocess.ProjectModuleEventListener line 190 <br>

if(project.getState().toString().equals("COMPLETED") && !isCheckReview){<br>
                try {<br>

                    String msg = "S";<br>
                    if(false){ // 이벤트 조건   true(실행)   false(중지)<br>
                        msg = PerformanceHelper.manager.eventPerformance(project);<br>
                    }<br>
                    Kogger.debug(" 성과관리 완료 확인 메세지 :" + msg);<br>
                    String msgCheck = msg.substring(0,1);<br>
                    if("F".equals(msgCheck)){<br>
                        throw new Exception(" 성과관리 이벤트 처리중 오류 발생 : " +msg);<br>
                    }<br>

                }catch (Exception e) {<br>
        Kogger.error(e);<br>
        throw new WTException(e);<br>
    }<br>
}<br>


</body>
</html>
