<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.fc.*,
                  wt.org.*,
                  wt.vc.*,
                  wt.vc.wip.*,
                  java.util.*,
                  java.text.*"%>
<%@page import="e3ps.groupware.company.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

        <%@include file="/jsp/common/context.jsp" %>
<html>
<head>

<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>

<script type="text/javascript">

    function clearDate(str) {
        var tartxt = document.getElementById(str);
        tartxt.value = "";
    }

    function setExeDateSave(){

        document.forms[0].action = "/plm/jsp/project/issueCreateDateUpdate.jsp?ExeDate=ExeDate";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

</script>
</head>
<body>
<% 	 String oid = request.getParameter("oid");

     String createDate = request.getParameter("createDate");
     Calendar tempCal = Calendar.getInstance();


     String createStr = "";
     Object obj = CommonUtil.getObject(oid);

     if(obj instanceof ProjectIssue){

        ProjectIssue issue = (ProjectIssue)obj;
        createStr = DateUtil.getDateString(issue.getCreateDate(), "D");

     }else if(obj instanceof ProjectIssueAnswer){
         ProjectIssueAnswer answer = (ProjectIssueAnswer)obj;
         createStr = DateUtil.getDateString(answer.getCreateDate(), "D");
     }


     if(createDate != null){


         if(obj instanceof ProjectIssue){

            ProjectIssue issue = (ProjectIssue)obj;
            tempCal.setTime(DateUtil.parseDateStr((String)createDate));
            issue.setCreateDate(new java.sql.Timestamp(tempCal.getTime().getTime()));

         }else if(obj instanceof ProjectIssueAnswer){
             ProjectIssueAnswer answer = (ProjectIssueAnswer)obj;
             tempCal.setTime(DateUtil.parseDateStr((String)createDate));
             answer.setCreateDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
         }
     }
%>
<form>
            <input type="hidden" name="oid" value="<%=oid%>"/>
            <table>
                    <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02061") %><%--실제 시작일자--%></td>
                    <td class="tdwhiteL">
                        <input name="createDate"  value="<%=createStr%>" size="12" value="" maxlength=15 style="border:0;">
                        <input type="button" class="buttoncalendar" value=" " onclick="javascript:openCal('taskExecStartDate');"/>
                        <a href="JavaScript:clearDate('createDate')"><img src="/plm/portal/images/img_common/x.gif" border=0></a>
                        <a href="javascript:setExeDateSave();">
                            <img src="/plm/portal/images/img_default/button/board_btn_input.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%>' width="62" height="20" border="0">
                        </a>&nbsp;&nbsp;
                    </td>
                    </tr>
            </table>
</form>
</body>
</html>
