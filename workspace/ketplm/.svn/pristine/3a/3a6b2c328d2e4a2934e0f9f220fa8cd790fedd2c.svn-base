<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import ="e3ps.project.*,
                e3ps.common.web.ParamUtil,
                e3ps.common.util.CommonUtil,
                e3ps.project.beans.*,java.util.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    int psize = 15;
    int cpage = 1;
    int total = 0;
    int pageCount = 10;

    String initType = ParamUtil.getStrParameter(request.getParameter("initType"));
    String command = request.getParameter("command");

    String sessionidstring = request.getParameter("sessionid");

    if(sessionidstring==null || sessionidstring.length() == 0) sessionidstring = "0";
    long sessionid = Long.parseLong(sessionidstring.trim());
    String pagestring = request.getParameter("tpage");

    if(pagestring!=null && pagestring.length()>0) cpage = Integer.parseInt(pagestring);

    PagingQueryResult result = null;
    try{
    if("search".equals(command)){
        if(sessionid > 0 && cpage > 0){
            result = PagingSessionHelper.fetchPagingSession((cpage-1) * psize, psize, sessionid);
        } else{
            String type  = ParamUtil.getStrParameter(request.getParameter("type"));
            String category = ParamUtil.getStrParameter(request.getParameter("category"));
            String oid  = ParamUtil.getStrParameter(request.getParameter("oid"));
            String searchPjtNo= ParamUtil.getStrParameter(request.getParameter("searchPjtNo"));
            String searchDocTitle = ParamUtil.getStrParameter(request.getParameter("searchDocTitle"));
            HashMap param = new HashMap();
            param.put("initType",initType);
            param.put("type",type);
            param.put("category",category);
            param.put("oid",oid);
            param.put("searchPjtNo",searchPjtNo);
            param.put("searchDocTitle",searchDocTitle);
            result = ProjectOutputHelper.manager.searchProjectOutput(param, psize);
        }

        if(result!=null){
            total = result.getTotalSize();
            sessionid = result.getSessionId();
        }
    }

    String tmpTitle = "";
    if(initType.equals("produceproject")) {  //고객공급 프로젝트
        tmpTitle = "<%=messageService.getString("e3ps.message.ket_message", "00852") %><%--고객제공--%>";
    } else if(initType.equals("devproject")) {//연구개발 프로젝트
        tmpTitle = "연구개발";
    } else if(initType.equals("template")) {//Template
        tmpTitle = "Template";
    } else if(initType.equals("wbs")) {//WBS 관리
        tmpTitle = "WBS관리";
    }
%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<script language=JavaScript>
<!--

function gotoPage(p){
    document.forms[0].command.value='search';
    document.forms[0].sessionid.value='<%=sessionid%>';
    document.forms[0].tpage.value=p;
    document.forms[0].submit();
}

function search() {
    disabledAllBtn();
    showProcessing();
    document.forms[0].sessionid.value = "";
    document.forms[0].tpage.value = "";
    document.forms[0].action = "/plm/jsp/project/WorkOutputList.jsp?initType="+document.forms[0].initType.value+"&command=search";
    document.forms[0].method = "post";
    document.forms[0].submit();
}

//function viewOutput(oid) {
//  parent.document.location.href = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid;
//}

//-->
</script>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form>
<!-- Hidden Value -->
<input type="hidden" name="initType" value="<%= initType %>">
<input type=hidden name=command>
<input type=hidden name=sessionid>
<input type=hidden name=tpage>
<!-- //Hidden Value -->
<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01">MY 산출물 목록</td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=tmpTitle%> &gt; MY 산출물 목록 </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width=""><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%> :&nbsp;</td>
                                <td width="" ><input name="searchPjtNo" size=15 class="txt_field"/></td>
                                <td>&nbsp;</td>
                                <td width=""><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%> :&nbsp;</td>
                                <td width="" ><input name="searchDocTitle" size=15 class="txt_field"/></td>
                                <td>&nbsp;</td>
                                <td width=""><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%> :&nbsp;</td>
                                <td width="">
                                    <select name="category" size="1" style="width:70">
                                        <option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                        <option value='pre'><%=messageService.getString("e3ps.message.ket_message", "01338") %><%--등록전--%></option>
                                        <option value='post'><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></option>
                                    </select>
                                </td>
                                <td>
                                    &nbsp;
                                    <a href="javascript:search();">
                                    <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdblueM">NO</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%><br>NO</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                    <td class="tdblueM">TASK<br><%=messageService.getString("e3ps.message.ket_message", "02019") %><%--시작일자--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                </tr>
<%
    int listCount = total/psize;
    int count = 1;
    if(e3ps.common.util.StringUtil.checkString(pagestring) && Integer.parseInt(pagestring) > 0) {
        if(Integer.parseInt(pagestring) > 1) {
            count = (Integer.parseInt(pagestring) - 1) * psize + 1;
        } else {
            count = count;
        }
    }

    if(total > 0) {
        while ( result.hasMoreElements() ) {
            Object[] obj = (Object[])result.nextElement();
            ProjectOutputData data = new ProjectOutputData((ProjectOutput)obj[0]);
            JELProjectData pjtData = new JELProjectData((JELProject)data.project);
            String stateStr = "";
            if ( data.isRegistry && (data.docStateStr).equals("승인됨") ) stateStr = "등록";
            else stateStr = "등록전";

            String parentName =  "";
            if(data.task!=null){
                JELTask pTask = (JELTask)data.task.getParent();
                if(pTask!=null) {
                    parentName = pTask.getTaskName();
                } else {
                    parentName = data.task.getTaskName();
                }
            }
%>
                <tr>
                    <td class="tdwhiteM">&nbsp;<%=count++%></td>
                    <td class="tdwhiteL">&nbsp;
                        <a href="javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=CommonUtil.getOIDString(pjtData.jelProject)%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=CommonUtil.getOIDString(pjtData.jelProject)%>');"><%=pjtData.pjtNo%>
                    </td>
                    <% if(data.task!=null) { %>
                        <% JELTaskData taskData = new JELTaskData((JELTask)data.task); %>
                    <td class="tdwhiteL">&nbsp;<%=data.task.getTaskName()%></td>
                    <td class="tdwhiteL">
                        <!--a href="javascript:viewOutput('<%//=CommonUtil.getOIDString(data.task)%>')" title='<%//=parentName%>'-->&nbsp;<%//=data.name%>
                        <a href="javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=CommonUtil.getOIDString(data.task)%>', '/plm/jsp/project/TaskView.jsp?oid=<%=CommonUtil.getOIDString(data.task)%>');"><%=data.name%>
                    </td>
                    <td class="tdwhiteL">&nbsp;<%=e3ps.common.util.DateUtil.getDateString(taskData.taskPlanStartDate, "D")%></td>
                    <% } %>
                    <td class="tdwhiteL">&nbsp;<%=data.locationStr%></td>
                    <td class="tdwhiteL"><%=stateStr%></td>
                </tr>
<%
        }
    }

    if(total == 0) {
%>
                <tr>
                    <td class='tdwhiteM0' align='center' colspan='10'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                </tr>
<%  }  %>
            </table>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>
    <%
        int ksize = total/psize;
        int x = total%psize;
        if(x>0) ksize++;
        int temp = cpage/pageCount;
        if( (cpage%pageCount)>0)temp++;
        int start = (temp-1)*pageCount+1;

        int end = start + pageCount-1;
        if(end> ksize){
            end = ksize;
        }
    %>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
      <td>
            <table border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="40" align="center"><%if(start>1){%><a href="JavaScript:gotoPage(1)" class="small"><%=messageService.getString("e3ps.message.ket_message", "02792") %><%--처음--%></a><%}%></td>
                    <td width="1" bgcolor="#dddddd"></td>
                    <%if(start>1){%>
                    <td width="60" class="quick" align="center"><a href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "02322") %><%--이전--%></a></td>
                    <td width="1" bgcolor="#dddddd"></td>
                    <%}
                    for(int i=start; i<= end; i++){
                    %>
                    <td style="padding:2 8 0 7;cursor:hand" onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on">
                        <%if(i==cpage){%><b><%=i%><%}else{%><a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a><%}%></td>
                    <%}
                    if(end < ksize){
                    %>
                    <td width="1" bgcolor="#dddddd"></td>
                    <td width="60" align="center"><a href="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "01186") %><%--다음--%></a></td>
                    <%}%>
                    <td width="1" bgcolor="#dddddd"></td>
                    <td width="45" align="center"><%if(end<ksize){%><a href="JavaScript:gotoPage(<%=ksize%>)" class="small"><%=messageService.getString("e3ps.message.ket_message", "01354") %><%--마지막--%></a><%}%></td>
                </tr>
            </table>
      </td>
        <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
    </tr>
</table>
        </td>
    </tr>
</table>
</form>

</body>
</html>
<%}catch(Exception e){
    Kogger.error(e);
}

%>
