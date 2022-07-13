<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.KETGridUtil,
                e3ps.common.util.KETParamMapUtil,
                e3ps.project.TemplateProject" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<jsp:useBean id="screenWidth"           class="java.lang.String" scope="request"/>
<jsp:useBean id="screenHeight"          class="java.lang.String" scope="request"/>
<jsp:useBean id="tgDataHead"            class="java.lang.String" scope="request"/>
<jsp:useBean id="tgDataBody"            class="java.lang.String" scope="request"/>
<jsp:useBean id="personRoleEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="personRoleEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="milestoneTypeEnumKey"  class="java.lang.String" scope="request"/>
<jsp:useBean id="milestoneTypeEnum"     class="java.lang.String" scope="request"/>
<jsp:useBean id="optionTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="optionTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="newTypeEnumKey"        class="java.lang.String" scope="request"/>
<jsp:useBean id="newTypeEnum"           class="java.lang.String" scope="request"/>
<jsp:useBean id="modifyTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="modifyTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="commonTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="commonTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="mdrawTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="mdrawTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="hwTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="hwTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="swTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="swTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="mTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="mTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="pTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="pTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="buyTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="buyTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="systemTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="systemTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="taskTypeEnumKey"       class="java.lang.String" scope="request"/>
<jsp:useBean id="taskTypeEnum"          class="java.lang.String" scope="request"/>
<jsp:useBean id="scheduleTypeEnumKey"       class="java.lang.String" scope="request"/>
<jsp:useBean id="scheduleTypeEnum"          class="java.lang.String" scope="request"/>
<jsp:useBean id="priorityControlEnumKey"   class="java.lang.String" scope="request"/>
<jsp:useBean id="priorityControlEnum"      class="java.lang.String" scope="request"/>
<jsp:useBean id="mainScheduleCodeEnumKey"       class="java.lang.String" scope="request"/>
<jsp:useBean id="mainScheduleCodeEnum"          class="java.lang.String" scope="request"/>
<jsp:useBean id="debugSubEnumKey"       class="java.lang.String" scope="request"/>
<jsp:useBean id="debugSubEnum"          class="java.lang.String" scope="request"/>
<jsp:useBean id="debugNEnumKey"         class="java.lang.String" scope="request"/>
<jsp:useBean id="debugNEnum"            class="java.lang.String" scope="request"/>
<jsp:useBean id="debugTaskEnumKey"      class="java.lang.String" scope="request"/>
<jsp:useBean id="debugTaskEnum"         class="java.lang.String" scope="request"/>
<jsp:useBean id="divide"         class="java.lang.String" scope="request"/>

<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");
    // EJS TreeGrid End

    String oid = StringUtil.checkNull(request.getParameter("oid"));
    String title = "";
    String pjtName = "";
    Object obj = CommonUtil.getObject(oid);
    TemplateProject project = null;

    if ( obj instanceof TemplateProject ) {

        project = (TemplateProject)obj;

        if ( project != null ) {

            // WBS 일정 변경 화면 Title - WBS : PJT_NAME
            pjtName = StringUtil.checkNull( project.getPjtName() );
            title = "WBS : " + pjtName;
        }
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00559") %><%--WBS 일정 변경--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<!-- EJS TreeGrid End -->



<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp" %>
<%@include file="/jsp/project/schedule/ProjectScheduleGridJs.jsp" %>
<%@include file="/jsp/common/processing.html" %>
<script src="/plm/portal/js/common.js"></script>
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
    height : 100%;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<script type="text/javascript">
//<![CDATA[
               
    // WBS 조회
    function doSubmit() {

        document.form01.cmd.value = "wbs_search";

        document.form01.action = "/plm/servlet/e3ps/ProjectScheduleServlet";
        document.form01.target = "_self";
        document.form01.submit();
    }
    
    function search(taskUrl, wbsContentUrl){
    	openOtherName(encodeURI(wbsContentUrl+"&taskUrl="+taskUrl), "StandardWBSSearch", 1335, 768, "status=no,scrollbars=yes,resizable=no");
    }
    
    function checkCategoryType(){
        var G = Grids[0];
        var temp = "";
        var temp1 = "";
        var count = 0;
        var count1 = 0;
        var i =0;
        for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        	if(i != 0){
        		   temp = G.GetString( tRow, 'New');
        		   temp1 = G.GetString( tRow, "Modify");
        		   if(temp == ""){
        			   
        		   }else{
        			 count++;   
        		   }
        		   
        		   if(temp1 == ""){
        			   
        		   }else{
        			   count1++;
        		   }
        	}
        	i++;
        }
        if(count > 0 && count1 > 0){ return true;}
        else{ return false;}
  
    }
    
$(document).ready(function(){
        
        var agent = navigator.userAgent.toLowerCase();
        var isExplorer = false;
        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {//ms 익스플로러 인지 확인
         isExplorer = true;
        }

        var gridId = "#ChangeWBSScheduleGrid";
        if(!isExplorer){
           //크롬에서 그리드 리사이즈 하기 위한 코드 start
            var re_size = 150;
        
            $("#divId").height($("#taskContent",parent.document).height()+$(window).height()-re_size);
        
            $(window).resize(function(){
                $("#divId").height($("#taskContent",parent.document).height()+$(window).height()-re_size);
            });
            
        
            var timerId = null;
        
            var gridHeight = 0;

            window.editorResize = function(){
            
                var windowHeight = $("#taskContent",parent.document).height()+$(window).height();
                var gridHeight = $(gridId).height();
            
                window.console.log("gridHeight : "+gridHeight);
                window.console.log("windowHeight : "+windowHeight);
             
                //그리드가 로딩되어 높이가 측정되고 스크롤이 필요없을만큼 갯수가 적을 때 (그리드 높이가 낮을 때) 그리드 사이즈만큼 리사이즈 해준다    
                if(gridHeight != null && gridHeight < windowHeight){
                 
                     $("#divId").height($("#taskContent",parent.document).height()+$(window).height()-re_size);
                     $(window).resize(function(){
                           
                           $("#divId").height($("#taskContent",parent.document).height()+$(window).height()-re_size);                   
                     });     
                    
                     window.console.log($("#divId").height());
                 
                 }
                if($(gridId).height() != null){
                
                    clearInterval(timerId); 
                }
           }
        
           timerId = setInterval(editorResize, 100);
        
            //크롬에서 그리드 리사이즈 하기 위한 코드 end
       }
        
        
    });
    
//]]>
</script>

</head>

<body>

<form name="form01" method="post">

<input type="hidden" id="oid" name="oid" value="<%= oid %>" />
<input type="hidden" name="mode" value="ChangeSchedule" />
<input type="hidden" name="cmd" value="" />
<input type="hidden" name="command" value="" />


<table style="width: 100%; height: 100%;">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td>
              
            </td>
        </tr>
        </table>
        <!-- [END] title & position -->

        <!-- [START] 버튼 -->
        
        <table style="width: 100%">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] 버튼 -->

        <!-- EJS TreeGrid Start -->
        <div class="content-main" >
            <div class="container-fluid">
                <div class="row-fluid" >
                    <div style="width:100%;height:100%; min-height:200px" id="divId">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/schedule/ChangeWBSScheduleGridLayout.jsp"
                            Layout_Method="POST"
                            Layout_Param_Screenwidth="<%= screenWidth %>"
                            Layout_Param_Screenheight="<%= screenHeight %>"
                            Layout_Param_Personroleenumkey="<%= personRoleEnumKey %>"
                            Layout_Param_Personroleenum="<%= personRoleEnum %>"
                            Layout_Param_Milestonetypeenumkey="<%= milestoneTypeEnumKey %>"
                            Layout_Param_Milestonetypeenum="<%= milestoneTypeEnum %>"
                            Layout_Param_Optiontypeenumkey="<%= optionTypeEnumKey %>"
                            Layout_Param_Optiontypeenum="<%= optionTypeEnum %>"
                            Layout_Param_Newtypeenumkey="<%= newTypeEnumKey %>"
                            Layout_Param_Newtypeenum="<%= newTypeEnum %>"
                            Layout_Param_Modifytypeenumkey="<%= modifyTypeEnumKey %>"
                            Layout_Param_Modifytypeenum="<%= modifyTypeEnum %>"
                            Layout_Param_Commontypeenumkey="<%= commonTypeEnumKey %>"
                            Layout_Param_Commontypeenum="<%= commonTypeEnum %>"
                            Layout_Param_Mdrawtypeenumkey="<%= mdrawTypeEnumKey %>"
                            Layout_Param_Mdrawtypeenum="<%= mdrawTypeEnum %>"
                            Layout_Param_Hwtypeenumkey="<%= hwTypeEnumKey %>"
                            Layout_Param_Hwtypeenum="<%= hwTypeEnum %>"
                            Layout_Param_Swtypeenumkey="<%= swTypeEnumKey %>"
                            Layout_Param_Swtypeenum="<%= swTypeEnum %>"
                            Layout_Param_Mtypeenumkey="<%= mTypeEnumKey %>"
                            Layout_Param_Mtypeenum="<%= mTypeEnum %>"
                            Layout_Param_Ptypeenumkey="<%= pTypeEnumKey %>"
                            Layout_Param_Ptypeenum="<%= pTypeEnum %>"
                            Layout_Param_Buytypeenumkey="<%= buyTypeEnumKey %>"
                            Layout_Param_Buytypeenum="<%= buyTypeEnum %>"
                            Layout_Param_Systemtypeenumkey="<%= systemTypeEnumKey %>"
                            Layout_Param_Systemtypeenum="<%= systemTypeEnum %>"
                            Layout_Param_Tasktypeenumkey="<%= taskTypeEnumKey %>"
                            Layout_Param_Tasktypeenum="<%= taskTypeEnum %>"
                            Layout_Param_Mainschedulecodeenumkey="<%= mainScheduleCodeEnumKey %>"
                            Layout_Param_Mainschedulecodeenum="<%= mainScheduleCodeEnum %>"
                            Layout_Param_Prioritycontrolenumkey="<%= priorityControlEnumKey %>"
                            Layout_Param_Prioritycontrolenum="<%= priorityControlEnum %>"
                            Layout_Param_Scheduletypeenumkey="<%= scheduleTypeEnumKey %>"
                            Layout_Param_Scheduletypeenum="<%= scheduleTypeEnum %>"
                            Layout_Param_Debugsubenumkey="<%= debugSubEnumKey %>"
                            Layout_Param_Debugsubenum="<%= debugSubEnum %>"
                            Layout_Param_Debugnenumkey="<%= debugNEnumKey %>"
                            Layout_Param_Debugnenum="<%= debugNEnum %>"
                            Layout_Param_Debugtaskenumkey="<%= debugTaskEnumKey %>"
                            Layout_Param_Debugtaskenum="<%= debugTaskEnum %>"
                            Layout_Param_Oid="<%= oid %>"
                            Layout_Param_Divide="<%= divide %>"
                            Data_Url="/plm/jsp/project/schedule/ChangeWBSScheduleGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Resulthead="<%= tgDataHead %>"
                            Data_Param_Resultbody="<%= tgDataBody %>"
                            Upload_Url="/plm/servlet/e3ps/ProjectScheduleServlet?cmd=wbs_save"
                            Upload_Method="POST"
                            Upload_Format="Internal"
                            Upload_Data="TGData"
                            Upload_Flags="AllCols"
                            Upload_Type="All"
                            Upload_Param_Oid="<%= oid %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Change_WBS_Schedule"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->
    </td>
</tr>
</table>

</form>

</body>
</html>
