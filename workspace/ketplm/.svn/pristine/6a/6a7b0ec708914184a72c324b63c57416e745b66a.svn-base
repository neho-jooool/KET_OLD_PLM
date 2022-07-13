<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
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
<li><%=messageService.getString("e3ps.message.ket_message", "00382") %><%--PMS 권한정보--%>
<ul >
<%=messageService.getString("e3ps.message.ket_message", "02395") %><%--자동차 일정--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01321") %><%--등록, 수정, 삭제 : 자동차PMO, 자동차일정--%></br>
</br>
WBS</br>
- <%=messageService.getString("e3ps.message.ket_message", "01319") %><%--등록, 수정 : 자동차PMO--%></br>
</br>
<%=messageService.getString("e3ps.message.ket_message", "00725") %><%--검토, 제품 프로젝트--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01313") %><%--등록 :자동차PMO--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01939") %><%--수정 :자동차PMO,PM--%></br>
</br>
<%=messageService.getString("e3ps.message.ket_message", "01024") %><%--금형 프로젝트--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01312") %><%--등록 :금형(P/M)담당자, 제품 PM--%></br>
</br>
<%=messageService.getString("e3ps.message.ket_message", "01012") %><%--금형 Try--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01311") %><%--등록 : 제한없음(PLM 로그인)--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01938") %><%--수정 : 작성자, Try제작관리 멤버, Try담당 멤버--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01691") %><%--삭제 : 직접등록 된 Try, 작성자(Try확정 이전), Try제작관리 멤버--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "00536") %><%--Try 확정 : Try제작관리 멤버--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "00530") %><%--Try 완료 : Try담당 멤버--%></br>
</br>
<%=messageService.getString("e3ps.message.ket_message", "01068") %><%--금형부품관리--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01320") %><%--등록, 수정, 삭제 : 자동차_금형부품 멤버--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01937") %><%--수정 : 자동차_금형부품측정 멤버--%></br>
</br>
<%=messageService.getString("e3ps.message.ket_message", "01901") %><%--성과관리 평가기준--%></br>
- <%=messageService.getString("e3ps.message.ket_message", "01319") %><%--등록, 수정 : 자동차PMO--%></br>
</br>

</ul>
</li>

<li><%=messageService.getString("e3ps.message.ket_message", "01362") %><%--메일 공지 정보--%>
<ul>
<%=messageService.getString("e3ps.message.ket_message", "00757") %><%--결재 승인 시 공통 메일 공지 : 프로젝트 멤버--%></br>
<%=messageService.getString("e3ps.message.ket_message", "03066") %><%--프로젝트 등록(제품,금형) : 담당자--%></br>
<%=messageService.getString("e3ps.message.ket_message", "02305") %><%--이슈 등록 : 긴급도 및 중요도에 따른 메일 공지--%></br>
<%=messageService.getString("e3ps.message.ket_message", "01013") %><%--금형 Try 완료 : 의뢰자 메일 공지--%></br>



</ul>
</li>



</body>
</html>
