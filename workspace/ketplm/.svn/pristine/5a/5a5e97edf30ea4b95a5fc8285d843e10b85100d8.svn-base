<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    /*-----------------------------------------------------------------------------------------------------------------
      ! Support file only, run Grid.html instead !
      This file is used as Data_Url for TreeGrid
      It generates source data for TreeGrid from database
    ------------------------------------------------------------------------------------------------------------------*/

    //------------------------------------------------------------------------------------------------------------------
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String resultHeadStr        = request.getParameter("Resulthead");
	String resultHeadStr1        = request.getParameter("Resulthead1");	      
    String resultBodyStr        = request.getParameter("Resultbody");
    String personRoleEnumKey    = request.getParameter("Personroleenumkey");
    String personRoleEnum       = request.getParameter("Personroleenum");
    String milestoneTypeEnumKey = request.getParameter("Milestonetypeenumkey");
    String milestoneTypeEnum    = request.getParameter("Milestonetypeenum");
    String optionTypeEnumKey    = request.getParameter("Optiontypeenumkey");
    String optionTypeEnum       = request.getParameter("Optiontypeenum");
    String taskTypeEnumKey      = request.getParameter("Tasktypeenumkey");
    String taskTypeEnum         = request.getParameter("Tasktypeenum");
%>

<?xml version="1.0"?>
<Grid>
    <Head>
        <!-- 자동차 일정 -->
        <%= resultHeadStr %>
        <!-- 프로그램 일정 -->
        <%= resultHeadStr1 %>
    </Head>
    <Body>
        <!-- Project Task 일정 -->
        <B><%= resultBodyStr %></B>
    </Body>
</Grid>
