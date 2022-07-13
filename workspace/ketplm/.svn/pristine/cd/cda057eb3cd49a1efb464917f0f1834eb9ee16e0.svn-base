<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*,
                  wt.fc.*,
                  wt.org.*,
                  wt.vc.*,
                  wt.part.*,
                  wt.team.*,
                  wt.lifecycle.*,
                  wt.project.*,
                  wt.vc.wip.*"%>
<%@page import="e3ps.groupware.company.*,
                e3ps.common.util.*,
                e3ps.common.content.*,
                e3ps.common.jdf.log.Logger,
                e3ps.auth.beans.E3PSAuthHelper,
                e3ps.project.*,
                e3ps.project.beans.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
    String oid = request.getParameter("oid");
    TaskDepartmentProjectAssign ta = null;
    E3PSProject project = null;
    Object oo = CommonUtil.getObject(oid);
    if( oo instanceof TaskDepartmentProjectAssign){
        ta = (TaskDepartmentProjectAssign)oo;
        project = ta.getProject();
    }else{
        project = (E3PSProject)oo;
    }



    E3PSProjectData projectData = new E3PSProjectData(project);
    project.getLifeCycleState();

    boolean isAdmin = CommonUtil.isAdmin();                        //PLM Admin
    boolean isAuth = E3PSAuthHelper.manager.isAuth("PRJ", "Project");          //isAuth(권한관리)
    boolean isPM = ProjectUserHelper.manager.isPM(project);                //isPM
    boolean isPL = ProjectUserHelper.manager.isPL(project);                //isPL
    boolean canManage = ProjectUserHelper.manager.isPM(project) || isAdmin || isAuth;  //Manager(isPM || isAdmin || isAuth)
    boolean canDelete = ProjectUserHelper.manager.isPM(project) || isAdmin;        //Delete(isPM || isAdmin)
    boolean isContinue = projectData.getAccessState().isContinue();              //프로젝트 진행 여부
    boolean isStop = projectData.getAccessState().isStop();                     //프로젝트 중지 여부
    boolean isComplete = projectData.getAccessState().isComplete();              //프로젝트 완료 여부(=isCompleting)
    boolean isCompleting = projectData.getAccessState().isCompleting();          //프로젝트 완료진행중  여부 (=isComplete)
    boolean isInWork = (project.getLifeCycleState()==State.INWORK);
    e3ps.common.jdf.message.Message message = e3ps.common.jdf.message.MessageBox.getInstance("message");

    String popup = request.getParameter("popup");
    String tmpPopUp = "";
    if(popup != null && !popup.equals("")) {
        tmpPopUp = "&popup=popup";
    }

%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript">
<!--
    function allProjectView(oid){
        var screenWidth = 0 ;
        var screenHeight = 0 ;
        var url =  "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid+"<%=tmpPopUp%>";
        newWin = window.open(url,"window","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=1200,height=800,resizable=no,top="+screenHeight+",left="+screenWidth);
        newWin.focus();

    }

    function projectDel(oid) {
        if(!confirm("<%=message.getMessage("confirm.delete")%>")) return;
            document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
            document.forms[0].method = "post";
            document.forms[0].command.value = "delete";
            disabledAllBtn();
            showProcessing();
            document.forms[0].submit();

    }

    function viewPeople(oid) {
        var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
        openSameName(url,"550","400","status=no,scrollbars=no,resizable=no");

    }

    function viewCurrentChart(oid){
        width = 0;
        height = 0;
        var screenWidth = 0 ;
        var screenHeight = 0 ;
        var maxWidth = screen.availWidth;
        var maxHeight = screen.availHeight;
        var windowWin = window.open("/plm/jnlp/ProjectChart.jsp?oid="+oid,"chart","status=no,scrollbars=yes,resizable,width="+maxWidth+",height="+maxHeight+",top="+screenHeight+",left="+screenWidth);
        //windowWin.focus();
    }

    function openView( url ) {
        newWin = window.open(url,"window","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=700,resizable=no,top="+screenHeight+",left="+screenWidth);
        //newWin.resizeTo(800,700);
        newWin.focus();
    }

    function viewECO(projectno) {
        var screenWidth = screen.availWidth/2-900;
        var screenHeight = screen.availHeight/2-400;
        var url = "/plm/servlet/e3ps.change.servlet.ManageECOServlet?projectno="+projectno+"&mode=popup";
        newWin = window.open(url,"window", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=350,height=150,resizable=no,top="+screenHeight+",left="+screenWidth);
        newWin.resizeTo(900,400);
        newWin.focus();
    }

-->
</script>
<script src="/plm/portal/js/script.js"></script>
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
<body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<%@include file="/jsp/common/processing.html"%>
<form method="post">
<!-- Hidden Value -->
<input type=hidden name=command>
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=pmoid value='<%=request.getParameter("pmoid")%>'>
<!-- //Hidden Value -->

<table border="0" cellpadding="1" cellspacing="1" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03090") %><%--프로젝트 정보--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03090") %><%--프로젝트 정보--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:allProjectView('<%=CommonUtil.getOIDString(project)%>');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('btn2_14','','/plm/portal/images/img_default/btn/btn2_14u.gif',1)">
                        <img src="/plm/portal/images/img_default/btn/btn2_14.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "02497") %><%--전체보기--%>' name="btn2_14" width="37" height="43" border="0">
                        </a>
                        <% if(isInWork) { %>
                        <a href="javascript:projectDel('<%=oid%>');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('btn2_05','','/plm/portal/images/img_default/btn/btn2_05u.gif',1)">
                        <img src="/plm/portal/images/img_default/btn/btn2_05.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "02708") %><%--지우기--%>" name="btn2_05" width="37" height="43" border="0">
                        </a>
                        <% } %>
                        <!--a href="javascript:viewECO('<%//=projectData.pjtNo%>');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('btn2_03','','/plm/portal/images/img_default/btn/btn2_03u.gif',1)">
                        <img src="/plm/portal/images/img_default/btn/btn2_03.gif" alt="설계변경" name="btn2_03" width="37" height="43" border="0">
                        </a-->
                        <a href="javascript:self.close();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('btn2_10','','/plm/portal/images/img_default/btn/btn2_10u.gif',1)">
                        <img src="/plm/portal/images/img_default/btn/btn2_10.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" name="btn2_10" width="37" height="43" border="0">
                        </a>
                    </td>
                </tr>
            </table>
<!-------------------------------------- 상단버튼 끝 //-------------------------------------->
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
<!------------------------------ 본문 시작 //------------------------------>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false">
                <jsp:param name="tb_class" value="tab_w01"/>
                <jsp:param name="td_class" value="tab_btm2"/>
            </jsp:include>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false">
                <jsp:param name="tb_class" value="tab_w01"/>
                <jsp:param name="td_class" value="tab_btm1"/>
            </jsp:include>
            <!-- 프로젝트 기본정보 -->
            <table class="tab_w01" border="0" cellspacing="0" cellpadding="0" bgcolor=AABDC6>
            <COL width="15%"><COL width="20%"><COL width="10%"><COL width="20%"><COL width="10%"><COL width="20%">
                <tr>
                    <td class="tdblueM" colspan=6>
                        <img src='/plm/portal/icon/project.gif'>&nbsp;<B><SPAN style="FONT-SIZE: 11pt">[<%=projectData.pjtName%>]</SPAN></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=projectData.ViewpjtNo%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02860") %><%--최초등록일자--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=DateUtil.getDateString(projectData.pjtCreateDate,"D")%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02846") %><%--최종 수정일자--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=DateUtil.getDateString(projectData.pjtModifyDate,"D")%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                    <td class="tdwhiteL" colspan=3>
                        &nbsp;<%=projectData.pjtName%>
                    </td>
                    <!--td class="tdblueL">프로젝트 종류</td>
                    <td class="tdwhiteL0">
                        &nbsp;<%//=projectData.pjtTypeName%>
                    </td-->
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03074") %><%--프로젝트 산출물 인증방식--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=projectData.pjtOutputType%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02828") %><%--총기간--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=projectData.pjtDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=DateUtil.getDateString(projectData.pjtPlanStartDate,"D")%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=DateUtil.getDateString(projectData.pjtPlanEndDate,"D")%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=project.getDivision()==null?"":StringUtil.checkNull(project.getDivision().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01162") %><%--난이도--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=project.getLevel()==null?"":StringUtil.checkNull(project.getLevel().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02581") %><%--제품군--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=project.getProduct()==null?"":StringUtil.checkNull(project.getProduct().getName())%>
                    </td>
                </tr>

                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01474") %><%--발주처--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=project.getCustomer()==null?"":StringUtil.checkNull(project.getCustomer().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00665") %><%--개발조직--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=project.getDevcompany()==null?"":StringUtil.checkNull(project.getDevcompany().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01788") %><%--생산조직--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=project.getMakecompany()==null?"":StringUtil.checkNull(project.getMakecompany().getName())%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=project.getModel()==null?"":StringUtil.checkNull(project.getModel().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td class="tdwhiteL0" >
                        &nbsp;<%=project.getLifeCycleState().getDisplay(messageService.getLocale())%>
                    </td>
                    <td class="tdwhiteL" colspan=2>
                        <% if ( isComplete ) {  %>  <!-- //종료된 경우 -->
                            <B><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00044", DateUtil.getDateString(projectData.pjtExecEndDate, "D")) %><%--{0}일 종료됨--%></font></B>
                        <% } else { %>
                        &nbsp;
                        <% } %>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td class="tdwhiteL" colspan=5>
                        &nbsp;<%=projectData.pjtDesc%>
                    </td>
                </tr>
            </table>
            <!-- //프로젝트 기본정보 -->
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false">
                <jsp:param name="tb_class" value="tab_w01"/>
                <jsp:param name="td_class" value="tab_btm1"/>
            </jsp:include>

            <!-- 프로젝트 세부 정보 -->
            <table class="tab_w01" border="0" cellspacing="0" cellpadding="0" bgcolor=AABDC6>
            <COL width="15%"><COL width="85%">
                <tr>
                    <td class="tdblueL">PM</td>
                    <td class="tdwhiteL">
                        <br>
                        <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor=AABDC6>
                            <tr bgcolor="#D6DBE7" align=center>
                                <td width=35% id=tb_blue align=center><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                <td width=15% id=tb_blue align=center><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                                <td width=15% id=tb_blue align=center><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                <td width=30% id=tb_blue align=center>e-mail</td>
                            </tr>
            <%
                PeopleData pData = new PeopleData(projectData.pjtPm);
                String pOID = CommonUtil.getOIDString(projectData.pjtPm);
            %>
                            <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                                <td id=tb_gray align=center>&nbsp;<a href="JavaScript:viewPeople('<%=pOID%>')"><%=pData.name%></td>
                                <td id=tb_gray align=center>&nbsp;<%=pData.departmentName%></td>
                                <td id=tb_gray align=center>&nbsp;<%=pData.duty%></td>
                                <td id=tb_gray align=left>&nbsp;<a href="mailto:<%=pData.email%>"><%=pData.email%></a></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00974") %><%--구성원--%></td>
                    <td class="tdwhiteL">

                        <!-- MEMBER 구성원 -->

                        <table border="0" width="100%" cellpadding="1" cellspacing="0" align="center">
                            <tr>
                                <td width="100%">
                                    <table width="100%" cellspacing="1" cellpadding="1" border="0" id="userMemberTable" bgcolor=AABDC6>
                                        <tr bgcolor="#D6DBE7"  align=center>
                                            <td width="20%" id=tb_blue ><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                            <td width="20%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                            <td width="30%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                                            <td width="30%" id=tb_blue>e-mail</td>
                                        </tr>
                    <%
                            QueryResult members = ProjectUserHelper.manager.getQueryForTeamUsers(project, "MEMBER");
                            while ( members.hasMoreElements() ) {
                                ProjectMemberLink link = (ProjectMemberLink)members.nextElement();
                                PeopleData data = new PeopleData(link.getMember());
                                String wtuserOID = CommonUtil.getOIDString(data.wtuser);
                                String peopleOID = CommonUtil.getOIDString(data.people);
                    %>
                                        <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                                            <td id=tb_gray width="20%" align=center>&nbsp;<a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=data.name%></td>
                                            <td id=tb_gray width="20%" align=center>&nbsp;<%=data.duty%></td>
                                            <td id=tb_gray width="30%" align=center>&nbsp;<%=data.departmentName%></td>
                                            <td id=tb_gray width="30%" align=center>&nbsp;<a href="mailto:<%=data.email%>"><%=data.email%></a></td>
                                        </tr>
                    <%    }  %>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <!-- //MEMBER 구성원 -->
                    </td>
                </tr>
            <!-- //프로젝트 세부 정보 -->
<!------------------------------ 본문 끝 //------------------------------>
        </td>
    </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</form>
<iframe src="" name="hiddenFrame" scrolling=no frameborder=no marginwidth=0 marginheight=0 style="display:none"></iframe>
</body>
</html>
