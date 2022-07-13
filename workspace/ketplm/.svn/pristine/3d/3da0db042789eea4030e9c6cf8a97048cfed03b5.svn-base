<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.web.ParamUtil,
                e3ps.common.util.CommonUtil" %>
<%@page import="wt.fc.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = request.getParameter("oid");
    TemplateProject project =(TemplateProject)CommonUtil.getObject(oid);

    String listType = request.getParameter("listType");


    QueryResult list = null;
    
    String title = messageService.getString("e3ps.message.ket_message", "02327")/*인원*/;
//     String title = messageService.getString("e3ps.message.ket_message", "02759")/*참여자 인원 목록*/;
    if("all".equals(listType)){
//         title = messageService.getString("e3ps.message.ket_message", "02492") /*전체 인원 목록*/;
        list = ProjectUserHelper.manager.getProjectUser(project);
    }else{
//         title = messageService.getString("e3ps.message.ket_message", "02756") /*참여 인원 목록*/;
        list = ProjectUserHelper.manager.getMember(project);
    }
    //QueryResult list = ProjectUserHelper.manager.getQueryForTeamUsers(project, "MEMBER");
    String mode =  ParamUtil.getStrParameter(request.getParameter("mode"),"s");


    boolean isMultiSelect = false;

    if ( mode.equalsIgnoreCase("m") ) {
        isMultiSelect = true;
    }
    String function = ParamUtil.getStrParameter(request.getParameter("function"),"thiscolse");

    // [PLM Sytsem 1차개선] function parameter 설정, 2013-08-12, BoLee
    String param = ParamUtil.getStrParameter(request.getParameter("param"), "");


    Hashtable ht = new Hashtable();

    while(list.hasMoreElements()){
        Object[] o = (Object[])list.nextElement();
        ProjectUserData data = new ProjectUserData((ProjectMemberLink)o[0]);
        //if(!data.projectDuty.equals("구성원")){
        ht.put(data.peopleOID, data);
        //}
    }
/*
    list.reset();

    while(list.hasMoreElements()){
        Object[] o = (Object[])list.nextElement();
        ProjectUserData data = new ProjectUserData((ProjectMemberLink)o[0]);
        if(data.projectDuty.equals("구성원")){
            ht.put(data.peopleOID, data);
        }
    }
    */

    Enumeration e = ht.keys();
    Vector vt = new Vector();
    ComparatorUtil comparator = new ComparatorUtil();

    try{
        while(e.hasMoreElements()){
            Object oo = (Object)e.nextElement();
            String key = (String)oo;
            ProjectUserData dataSet = (ProjectUserData)ht.get(key);
            vt.add(dataSet);
        }
    }catch(Exception e1){
	Kogger.error(e1);
    }
    Collections.sort(vt, comparator);

%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Collections"%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "03100") %><%--프로젝트 참여자 목록--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript">
function viewPeople(oid){
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openOtherName(url,"process","550","400","status=no,scrollbars=no,resizable=no");
}

function selectAll() {
    var len = <%=vt.size()%>;
    if (len > 1) {
        for( var i = 0 ; i < len ; i++ ) {
            if ( document.selectProjectPeopleList.checkboxAll.checked == true ) document.selectProjectPeopleList.check[i].checked=true;
            else document.selectProjectPeopleList.check[i].checked=false;
        }
    } else if ( len == 1 ) {
        if ( document.selectProjectPeopleList.checkboxAll.checked == true ) document.selectProjectPeopleList.check.checked=true;
        else document.selectProjectPeopleList.check.checked=false;
    }
}

function clickThis(param) {
    if ( !param.checked ) return;

    var len = <%=vt.size()%>;
    var checkStr = param.value;

    var objArr = document.forms[0];
    if (len > 1) {
        for ( var i = 0 ; i < objArr.length ; i++ ) {
            if ( objArr[i].type == "checkbox" ) {
                if ( checkStr != objArr[i].value ) {
                    objArr[i].checked = false;
                }
            }
        }
    }
}

function fcheck() {
    var count = 0;
    var len = <%=vt.size()%>;

    if (len > 1) {
        for( i=0;i<len ;i++) {
            if( document.selectProjectPeopleList.check[i].checked == true) count++;
        }
    } else if ( len == 1 ) {
        if( document.selectProjectPeopleList.check.checked == true) count++;
    }

    if(count==0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01813") %><%--선택된 항목이 없습니다--%>");
        return false;
    }
    return true;
}

function thiscolse(){
    self.close();
}


function registryUserProjectOutput() {
    if ( fcheck() ) {
        var selectObj;
        var len = <%=vt.size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectPeopleList.check[i].checked == true) selectObj = document.selectProjectPeopleList.check[i];
        } else if ( len == 1 ) {
            if( document.selectProjectPeopleList.check.checked == true) selectObj = document.selectProjectPeopleList.check;
        }

        opener.document.forms[0].registryUserName.value = selectObj.sname;
        opener.document.forms[0].registryUserOID.value = selectObj.value;
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function modifyPM(){
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=vt.size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectPeopleList.check[i].checked == true) paramStr = paramStr + "userOid=" + document.selectProjectPeopleList.check[i].value + "&";
        } else if ( len == 1 ) {
            if( document.selectProjectPeopleList.check.checked == true) paramStr = "userOid=" + document.selectProjectPeopleList.check.value;
        }


        opener.document.forms[0].action = "/plm/jsp/project/projectModifyPM.jsp?" + paramStr;
        opener.document.forms[0].command.value = "targetCmdStr";
        opener.document.forms[0].method = "post";
        opener.document.forms[0].submit();
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addMemberUseTask() {
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=vt.size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectPeopleList.check[i].checked == true) paramStr = paramStr + "addMember=" + document.selectProjectPeopleList.check[i].value + "&";
        } else if ( len == 1 ) {
            if( document.selectProjectPeopleList.check.checked == true) paramStr = "addMember=" + document.selectProjectPeopleList.check.value;
        }

        opener.document.forms[0].method="post";
        opener.document.forms[0].action = "/plm/jsp/project/TaskView.jsp?"+paramStr;
        opener.document.forms[0].submit();
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addMasterUseTask() {
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=vt.size()%>;

        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectPeopleList.check[i].checked == true) paramStr = paramStr + "addMaster=" + document.selectProjectPeopleList.check[i].value + "&";
        } else if ( len == 1 ) {
            if( document.selectProjectPeopleList.check.checked == true) paramStr = "addMaster=" + document.selectProjectPeopleList.check.value;
        }

        opener.document.forms[0].method="post";
        opener.document.forms[0].action = "/plm/jsp/project/TaskView.jsp?"+paramStr;
        opener.document.forms[0].submit();
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addMemberUseTemplateTask() {
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=vt.size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectPeopleList.check[i].checked == true) paramStr = paramStr + "addMember=" + document.selectProjectPeopleList.check[i].value + "&";
        } else if ( len == 1 ) {
            if( document.selectProjectPeopleList.check.checked == true) paramStr = "addMember=" + document.selectProjectPeopleList.check.value;
        }

        opener.document.forms[0].method="post";
        opener.document.forms[0].action = "/plm/jsp/project/TemplateTaskView.jsp?"+paramStr;
        opener.document.forms[0].submit();
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addMasterUseTemplateTask() {
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=vt.size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectPeopleList.check[i].checked == true) paramStr = paramStr + "addMaster=" + document.selectProjectPeopleList.check[i].value + "&";
        } else if ( len == 1 ) {
            if( document.selectProjectPeopleList.check.checked == true) paramStr = "addMaster=" + document.selectProjectPeopleList.check.value;
        }

        opener.document.forms[0].method="post";
        opener.document.forms[0].action = "/plm/jsp/project/TemplateTaskView.jsp?"+paramStr;
        opener.document.forms[0].submit();
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

// [START] [PLM System 1차개선] Project 일정 변경 화면에서 Task 책임자, 참여자 추가 처리, 2013-07-11, BoLee
function addMemberToTaskOnScheduling(taskMaster) {

    if ( fcheck() ) {

        var member = "";
        var memberId = "";
        var len = <%= vt.size() %>;

        if ( len > 1 ) {
            for ( i = 0; i < len; i++ ) {

                // Task 참여자 설정 시에는 책임자 제외
                if ( document.selectProjectPeopleList.check[i].checked == true && document.selectProjectPeopleList.check[i].value != taskMaster ) {

                    if ( memberId == "" ) {
                        member      = document.selectProjectPeopleList.check[i].getAttribute('sname');
                        memberId    = document.selectProjectPeopleList.check[i].value;
                    }
                    else {
                        member      = member + "," + document.selectProjectPeopleList.check[i].getAttribute('sname');
                        memberId    = memberId + "," + document.selectProjectPeopleList.check[i].value;
                    }
                }
            }
        }
        else if ( len == 1 ) {

            // Task 참여자 설정 시에는 책임자 제외
            if ( document.selectProjectPeopleList.check.checked == true && document.selectProjectPeopleList.check.value != taskMaster ) {
                member      = document.selectProjectPeopleList.check.getAttribute('sname');
                memberId    = document.selectProjectPeopleList.check.value;
            }
        }

        opener.addMemberToTask(member, memberId);
    }
<%
    if ( mode.equals("s") ) {
        out.print("thiscolse();");
    }
%>
}
// [END] [PLM System 1차개선] Project 일정 변경 화면에서 Task 책임자, 참여자 추가 처리, 2013-07-11, BoLee


</script>
<style type="text/css">
<!--
body {
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-left:5px;
}
-->
</style>
</head>
<body>
<form name="selectProjectPeopleList">
<input type=hidden name=oid value="<%=oid%>">
<input type=hidden name=mode value="<%=mode%>">
<input type=hidden name=function value="<%=function%>">
<input type="hidden" name="param" value="<%= param %>">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 5px 0px">
              <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%> <%=title%></td>
                          </tr>
                        </table></td>
                      <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                    </tr>
                  </table></td>
              </tr>
            </table><table border="0" cellspacing="0" cellpadding="0" width="100%" class="b-space10">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:<%=function%>('<%= param %>');"  class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                      </table></td>

                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="tdblueM" width="20"><%if(isMultiSelect){%><input type="checkbox" name="checkboxAll" value="all"  onClick="javascript:selectAll();"><%}%>&nbsp;</td>
                    <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                    <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "02723") %><%--직책--%></td>
                    <!--
                    <td class="tdblueM">Role</td>
                    <td class="tdblueM">프로젝트권한</td>
                    -->
                    <td class="tdblueM" width="150">e-mail</td>
                </tr>
        <%
        for(int x = 0 ; x < vt.size(); x++){
            ProjectUserData data = (ProjectUserData)vt.get(x);
        %>
                    <tr>
                        <td class="tdwhiteM" width="1%">
                            <input type="checkbox" name="check"
                            value="<%=data.wtuserOID%>"
                            poid="<%=data.peopleOID%>"
                            email="<%=data.email%>"
                            sname="<%=data.name%>"
                            memberType="<%=data.memberType%>"
                            <%if(!isMultiSelect)out.print("onclick='javascript:clickThis(this)'");%>>

                        </td>
                        <td class="tdwhiteM">&nbsp;<a href="JavaScript:viewPeople('<%=data.peopleOID%>')"><%=data.name%></a></td>
                        <td class="tdwhiteM">&nbsp;<%=data.departmentName%></td>
                        <td class="tdwhiteM">&nbsp;<%=data.duty%></td>
                        <!--  <td class="tdwhiteM">&nbsp;<%//=data.role_ko%></td>
                        <td class="tdwhiteM">&nbsp;<%//=data.projectDuty%></td>-->
                        <td class="tdwhiteM">&nbsp;<%=data.email%></td>
                    </tr>
        <%} if ( vt.size() == 0 ) {%>
                    <tr>
                        <td class='tdwhiteM0' align='center' colspan='7'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                    </tr>
        <%  }
        %>
            </table>
        </td>
    </tr>
      <tr>
    <td height="30" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>

</form>
</body>
</html>
