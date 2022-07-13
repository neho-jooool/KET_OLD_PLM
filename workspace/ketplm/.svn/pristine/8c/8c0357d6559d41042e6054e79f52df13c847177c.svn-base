<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*,
                  wt.fc.*,
                  wt.org.*,
                  wt.team.*,
                  wt.project.Role,
                  wt.vc.*,
                  wt.part.*,
                  wt.vc.wip.*"%>
<%@page import="e3ps.groupware.company.*,
                e3ps.common.util.*,
                e3ps.common.content.*,
                e3ps.common.jdf.log.Logger,
                e3ps.auth.beans.E3PSAuthHelper,
                e3ps.project.*,
                e3ps.project.beans.*" %>
                
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%

    String oids[] = request.getParameterValues("oid");

    String oid = oids[0];
    String oldOid = oids[1];
    //Kogger.debug("oid==>"+oid);
    //Kogger.debug("oldOid==>"+oldOid);


    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProject project2 = (E3PSProject)CommonUtil.getObject(oldOid);


    E3PSProjectData projectData = new E3PSProjectData(project);
    E3PSProjectData projectData2 = new E3PSProjectData(project2);

    TemplateProject tp = ProjectHelper.getTemplate(project.getTemplateCode());;
    TemplateProjectData tpdata = new TemplateProjectData(tp);

    TemplateProject tp2 = ProjectHelper.getTemplate(project2.getTemplateCode());;
    TemplateProjectData tpdata2 = new TemplateProjectData(tp2);


%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/common.js"></script>
<script  language="JavaScript">
<!--
    function viewTemplate(oid){
        var url = "/plm/jsp/project/template/TemplateProjectView.jsp?oid="+oid;
        openOtherName(url,"ViewTemplate","800","500","status=1,scrollbars=no,resizable=1");
    }
-->
</script>

</head>
<body bgcolor="white" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form method="post">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding-left:20">
<!-------------------------------------- 상단버튼 시작 //-------------------------------------->
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right" style="padding-top: 5px" >
                        <a href="javascript:parent.self.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                        <!--table border="0" cellspacing="0" cellpadding="0" >
                            <tr>
                                <td class=fixLeft></td>
                                <td ><input type=button class="btnTras" value='닫기' onclick="javascript:parent.self.close();"></td>
                                <td class=fixRight></td>
                                <td>&nbsp;</td>
                            </tr>
                        </table-->
                    </td>
                </tr>
            </table>
<!-------------------------------------- 상단버튼 끝 //-------------------------------------->
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
<!------------------------------ 본문 시작 //------------------------------>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "03092") %><%--프로젝트 정보 비교--%></td>
                    <td align="right">
                        &nbsp;
                    </td>
                </tr>
            </table>

            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>

            <!-- 프로젝트 기본정보 (NEW Version) -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="20%"><COL width="13%"><COL width="20%"><COL width="12%"><COL width="20%">
                <tr>
                    <td class="tdblueM0" colspan=6>
                        <img src='/plm/portal/icon/project.gif'>&nbsp;<B><SPAN style="FONT-SIZE: 11pt">[<%=projectData.pjtNo%>] <%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%> (<%=projectData.pjtHistory%>) <%=messageService.getString("e3ps.message.ket_message", "02512") %><%--정보--%></SPAN></B>
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
                    <td class="tdwhiteL" colspan=3>
                        &nbsp;<%=project.getModel()==null?"":StringUtil.checkNull(project.getModel().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td class="tdwhiteL0" >
                        &nbsp;<%=project.getLifeCycleState().getDisplay(messageService.getLocale())%><font color="red"><%=(project.isCheckOut() == true)?messageService.getString("e3ps.message.ket_message", "00120")/*CheckOut됨*/ : ""%></font>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td class="tdwhiteL0" colspan=5>
                        <textarea rows="5" style="width:100%" class="fm_area" readonly><%=(projectData.pjtDesc).length()>0 ? projectData.pjtDesc:""%></textarea>
                    </td>
                </tr>
                <% if(tpdata != null) { %>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00516") %><%--Template 정보--%></td>
                    <td class="tdwhiteL0" colspan=5>
                        <!-- Open Window -->
                        <a href="#" onClick="javascript:viewTemplate('<%=tpdata.tempProjectOID %>');">
                        <%=tpdata.tempName%> </a>
                    </td>
                </tr>
                <% } %>
            </table>
            <!-- 프로젝트 기본정보 (NEW Version) -->

            <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>

            <!-- 프로젝트 기본정보 (NEW Version) -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="20%"><COL width="13%"><COL width="20%"><COL width="12%"><COL width="20%">
                <tr>
                    <td class="tdblueM0" colspan=6>
                        <img src='/plm/portal/icon/project.gif'>&nbsp;<B><SPAN style="FONT-SIZE: 11pt">[<%=projectData2.pjtNo%>] <%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%> (<%=projectData2.pjtHistory%>) <%=messageService.getString("e3ps.message.ket_message", "02512") %><%--정보--%></SPAN></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=projectData2.ViewpjtNo%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02860") %><%--최초등록일자--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=DateUtil.getDateString(projectData2.pjtCreateDate,"D")%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02846") %><%--최종 수정일자--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=DateUtil.getDateString(projectData2.pjtModifyDate,"D")%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                    <td class="tdwhiteL" colspan=3>
                        &nbsp;<%=projectData2.pjtName%>
                    </td>
                    <!--td class="tdblueL">프로젝트 종류</td>
                    <td class="tdwhiteL0">
                        &nbsp;<%//=projectData2.pjtTypeName%>
                    </td-->
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03074") %><%--프로젝트 산출물 인증방식--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=projectData2.pjtOutputType%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02828") %><%--총기간--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=projectData2.pjtDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=DateUtil.getDateString(projectData2.pjtPlanStartDate,"D")%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=DateUtil.getDateString(projectData2.pjtPlanEndDate,"D")%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=project2.getDivision()==null?"":StringUtil.checkNull(project2.getDivision().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01162") %><%--난이도--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=project2.getLevel()==null?"":StringUtil.checkNull(project2.getLevel().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02581") %><%--제품군--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=project2.getProduct()==null?"":StringUtil.checkNull(project2.getProduct().getName())%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01474") %><%--발주처--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=project2.getCustomer()==null?"":StringUtil.checkNull(project2.getCustomer().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00665") %><%--개발조직--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=project2.getDevcompany()==null?"":StringUtil.checkNull(project2.getDevcompany().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01788") %><%--생산조직--%></td>
                    <td class="tdwhiteL0">
                        &nbsp;<%=project2.getMakecompany()==null?"":StringUtil.checkNull(project2.getMakecompany().getName())%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
                    <td class="tdwhiteL" colspan=3>
                        &nbsp;<%=project2.getModel()==null?"":StringUtil.checkNull(project2.getModel().getName())%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td class="tdwhiteL0" >
                        &nbsp;<%=project2.getLifeCycleState().getDisplay(messageService.getLocale())%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td class="tdwhiteL0" colspan=5>
                        <textarea rows="5" style="width:100%" class="fm_area" readonly><%=(projectData2.pjtDesc).length()>0 ? projectData2.pjtDesc:""%></textarea>
                    </td>
                </tr>
                <% if(tpdata2 != null) { %>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00516") %><%--Template 정보--%></td>
                    <td class="tdwhiteL0" colspan=5>
                        <!-- Open Window -->
                        <a href="#" onClick="javascript:viewTemplate('<%=tpdata2.tempProjectOID %>');">
                        <%=tpdata2.tempName%> </a>
                    </td>
                </tr>
                <% } %>
            </table>
            <!-- 프로젝트 기본정보 (OLD Version) -->

            <!-- space -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space20"></td>
                </tr>
            </table>
            <!-- space -->


            <!-- 진행현황 시작 -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "02738") %><%--진행현황 비교--%></td>
                    <td align="right">
                        &nbsp;
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <COL width="15%"><COL width="20%"><COL width="65%">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00808") %><%--계획 진행율--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=projectData.pjtCompletion%> %
                    </td>
                    <td class="tdwhiteL0">
                        <!--진행률 그림-->
                        <table width=100% border=0 align=center>
                            <tr>
                                <td width=15%>(<%=DateUtil.getDateString(projectData.pjtExecStartDate,"D")%>)</td>
                                <td width=70%>
                                    <table border=0 width=100% cellpadding=0 cellspacing=0>
                                        <tr>
                                            <td align=right width=<%=projectData.pjtCompletion%>%><img src=/plm/portal/icon/bar_arrow1.gif></td>
                                            <td align=left width=<%=(100-projectData.pjtCompletion)%>%><img src=/plm/portal/icon/bar_arrow2.gif></td>
                                        </tr>
                                        <tr height=10>
                                            <td <%if(projectData.pjtCompletion!=0){%>background=/plm/portal/icon/bar_full.gif<%}%>></td>
                                            <td <%if(projectData.pjtCompletion!=100){%>background=/plm/portal/icon/bar_blank.gif<%}%>></td>
                                        </tr>
                                    </table>
                                </td>
                                <% if(projectData.pjtExecEndDate != null) { %>
                                <td width=15%>(<%=DateUtil.getDateString(projectData.pjtExecEndDate,"D")%>)</td>
                                <% } else { %>
                                <td width=15%>(<%=DateUtil.getDateString(projectData.pjtPlanEndDate,"D")%>)</td>
                                <% } %>
                            </tr>
                        </table>
                        <!--//-->
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <COL width="15%"><COL width="20%"><COL width="65%">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00808") %><%--계획 진행율--%></td>
                    <td class="tdwhiteL">
                        &nbsp;<%=projectData2.pjtCompletion%> %
                    </td>
                    <td class="tdwhiteL0">
                        <!--진행률 그림-->
                        <table width=100% border=0 align=center>
                            <tr>
                                <td width=15%>(<%=DateUtil.getDateString(projectData2.pjtExecStartDate,"D")%>)</td>
                                <td width=70%>
                                    <table border=0 width=100% cellpadding=0 cellspacing=0>
                                        <tr>
                                            <td align=right width=<%=projectData2.pjtCompletion%>%><img src=/plm/portal/icon/bar_arrow1.gif></td>
                                            <td align=left width=<%=(100-projectData2.pjtCompletion)%>%><img src=/plm/portal/icon/bar_arrow2.gif></td>
                                        </tr>
                                        <tr height=10>
                                            <td <%if(projectData2.pjtCompletion!=0){%>background=/plm/portal/icon/bar_full.gif<%}%>></td>
                                            <td <%if(projectData2.pjtCompletion!=100){%>background=/plm/portal/icon/bar_blank.gif<%}%>></td>
                                        </tr>
                                    </table>
                                </td>
                                <% if(projectData2.pjtExecEndDate != null) { %>
                                <td width=15%>(<%=DateUtil.getDateString(projectData2.pjtExecEndDate,"D")%>)</td>
                                <% } else { %>
                                <td width=15%>(<%=DateUtil.getDateString(projectData2.pjtPlanEndDate,"D")%>)</td>
                                <% } %>
                            </tr>
                        </table>
                        <!--//-->
                    </td>
                </tr>
            </table>


            <!-- 진행현황 끝 -->
            <!-- space -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space20"></td>
                </tr>
            </table>
            <!-- space -->


            <!-- 프로젝트 세부 정보 -->

            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "00975") %><%--구성원 비교--%></td>
                    <td align="right">
                        &nbsp;
                    </td>
                </tr>
            </table>

            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="85%">
                <tr>
                    <td class="tdblueL" valign="top">PM</td>
                    <td class="tdwhiteL0">
                        <div style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="pmtable">
                                <tr>
                                    <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                    <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                    <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                                    <td class="tdblueM0">E-Mail</td>
                                </tr>
<%
    PeopleData PMData = new PeopleData(projectData.pjtPm);
    String pOID = CommonUtil.getOIDString(projectData.pjtPm);
%>
                                <tr>
                                    <td class="tdwhiteM"><a href="JavaScript:viewPeople('<%=pOID%>')">&nbsp;<%=PMData.name%></td>
                                    <td class="tdwhiteM">&nbsp;<%=PMData.duty%></td>
                                    <td class="tdwhiteM">&nbsp;<%=PMData.departmentName%></td>
                                    <td class="tdwhiteL0">&nbsp;<a href="mailto:<%=PMData.email%>"><%=PMData.email%></a></td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL" valign="top">
                        <%=messageService.getString("e3ps.message.ket_message", "00451") %><%--Role별 담당자--%><br>
                    </td>
                    <td class="tdwhiteL0">
                        <div id ="roleDiv" style="width=99%;overflow-x:hidden;overflow-y:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:3px 1px;">
                                <table border="0" cellspacing="0" cellpadding="0" width="100%" id="roleMemberTable">
                                    <tr>
                                        <td class="tdblueM" width="50">Role</td>
                                        <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                        <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                        <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                                        <td class="tdblueM">E-Mail</td>

                                    </tr>
<%
    HashMap legacyMap = new HashMap();
    ProjectMemberLink roleLink = null;
    QueryResult result = ProjectUserHelper.manager.getQueryForTeamUsers(project, "ROLEMEMBER");
    while(result.hasMoreElements()) {
        roleLink = (ProjectMemberLink)result.nextElement();
        legacyMap.put(roleLink.getPjtRole(), roleLink);
    }


    Vector vecTeamStd = null;
    TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Team_Project");
    if(tempTeam != null) {
        vecTeamStd = tempTeam.getRoles();
    }

    if(vecTeamStd != null) {
        Role role = null;
        String roleName_en = null;
        String roleName_ko = null;

        PeopleData Roledata = null;
        String peopleOID = "";

        int roleIndex = 0;
        int colspan = 1;
        for (int i = vecTeamStd.size() - 1; i > -1; i--) {
            role = (Role) vecTeamStd.get(i);
            roleName_en = role.toString();
            roleName_ko = role.getDisplay(Locale.KOREA);

            roleLink = null;
            Roledata = null;
            peopleOID = "";
            if(legacyMap.get(roleName_en) != null) {
                roleLink = (ProjectMemberLink)legacyMap.get(roleName_en);
                Roledata = new PeopleData(roleLink.getMember());
                peopleOID = (Roledata.people).getPersistInfo().getObjectIdentifier().getStringValue();
            }
%>
                                    <tr>
                                        <td class="tdwhiteM"><%=roleName_ko%></td>
                                        <td class="tdwhiteM"><%if(Roledata != null){%><a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=Roledata.name%></a><%}else{%>&nbsp;<%}%></td>
                                        <td class="tdwhiteM"><%if(Roledata != null){%><%=Roledata.duty%><%}else{%>&nbsp;<%}%></td>
                                        <td class="tdwhiteM"><%if(Roledata != null){%><%=Roledata.departmentName%><%}else{%>&nbsp;<%}%></td>
                                        <td class="tdwhiteL"><%if(Roledata != null){%>&nbsp;<a href="mailto:<%=Roledata.email%>"><%=Roledata.email%></a><%}else{%>&nbsp;<%}%></td>
                                    </tr>
<%
        }
    }
%>
                                </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL" valign="top">
                        <%=messageService.getString("e3ps.message.ket_message", "00974") %><%--구성원--%><br>
                    </td>
                    <td class="tdwhiteL0">

                        <div id="memberDiv" style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="membertable">
                                <tr>
                                    <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                    <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                    <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                                    <td class="tdblueM">E-Mail</td>
                                </tr>
<%
    ProjectMemberLink link = null;
    PeopleData Memberdata = null;
    String peopleOID = "";

    QueryResult members = ProjectUserHelper.manager.getQueryForTeamUsers(project, "MEMBER");
    while(members.hasMoreElements()) {
        link = (ProjectMemberLink)members.nextElement();
        Memberdata = new PeopleData(link.getMember());
        peopleOID = (Memberdata.people).getPersistInfo().getObjectIdentifier().getStringValue();
%>
                                <tr>
                                    <td class="tdwhiteM"><a href="JavaScript:viewPeople('<%=peopleOID%>')">&nbsp;<%=Memberdata.name%></a></td>
                                    <td class="tdwhiteM">&nbsp;<%=Memberdata.duty%></td>
                                    <td class="tdwhiteM">&nbsp;<%=Memberdata.departmentName%></td>
                                    <td class="tdwhiteL">&nbsp;<a href="mailto:<%=Memberdata.email%>"><%=Memberdata.email%></a></td>
                                    <td class="tdwhiteM0">

                                    </td>
                                </tr>
<%
    }
%>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>

            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>



            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="85%">
                <tr>
                    <td class="tdblueL" valign="top">PM</td>
                    <td class="tdwhiteL0">
                        <div style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="pmtable">
                                <tr>
                                    <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                    <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                    <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                                    <td class="tdblueM0">E-Mail</td>
                                </tr>
<%
    PeopleData PMData2 = new PeopleData(projectData2.pjtPm);
    String pOID2 = CommonUtil.getOIDString(projectData2.pjtPm);
%>
                                <tr>
                                    <td class="tdwhiteM"><a href="JavaScript:viewPeople('<%=pOID2%>')">&nbsp;<%=PMData2.name%></td>
                                    <td class="tdwhiteM">&nbsp;<%=PMData2.duty%></td>
                                    <td class="tdwhiteM">&nbsp;<%=PMData2.departmentName%></td>
                                    <td class="tdwhiteL0">&nbsp;<a href="mailto:<%=PMData2.email%>"><%=PMData2.email%></a></td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL" valign="top">
                        <%=messageService.getString("e3ps.message.ket_message", "00451") %><%--Role별 담당자--%><br>
                    </td>
                    <td class="tdwhiteL0">
                        <div id ="roleDiv" style="width=99%;overflow-x:hidden;overflow-y:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:3px 1px;">
                                <table border="0" cellspacing="0" cellpadding="0" width="100%" id="roleMemberTable">
                                    <tr>
                                        <td class="tdblueM" width="50">Role</td>
                                        <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                        <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                        <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                                        <td class="tdblueM">E-Mail</td>

                                    </tr>
<%
    HashMap legacyMap2 = new HashMap();
    ProjectMemberLink roleLink2 = null;
    QueryResult result2 = ProjectUserHelper.manager.getQueryForTeamUsers(project2, "ROLEMEMBER");
    while(result2.hasMoreElements()) {
        roleLink2 = (ProjectMemberLink)result2.nextElement();
        legacyMap2.put(roleLink2.getPjtRole(), roleLink2);
    }


    Vector vecTeamStd2 = null;
    TeamTemplate tempTeam2 = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Team_Project");
    if(tempTeam2 != null) {
        vecTeamStd2 = tempTeam2.getRoles();
    }

    if(vecTeamStd != null) {
        Role role = null;
        String roleName_en = null;
        String roleName_ko = null;

        PeopleData Roledata2 = null;
        String peopleOID2 = "";

        int roleIndex = 0;
        int colspan = 1;
        for (int i = vecTeamStd.size() - 1; i > -1; i--) {
            role = (Role) vecTeamStd.get(i);
            roleName_en = role.toString();
            roleName_ko = role.getDisplay(Locale.KOREA);

            roleLink2 = null;
            Roledata2 = null;
            peopleOID2 = "";
            if(legacyMap2.get(roleName_en) != null) {
                roleLink2 = (ProjectMemberLink)legacyMap2.get(roleName_en);
                Roledata2 = new PeopleData(roleLink2.getMember());
                peopleOID2 = (Roledata2.people).getPersistInfo().getObjectIdentifier().getStringValue();
            }
%>
                                    <tr>
                                        <td class="tdwhiteM"><%=roleName_ko%></td>
                                        <td class="tdwhiteM"><%if(Roledata2 != null){%><a href="JavaScript:viewPeople('<%=peopleOID2%>')"><%=Roledata2.name%></a><%}else{%>&nbsp;<%}%></td>
                                        <td class="tdwhiteM"><%if(Roledata2 != null){%><%=Roledata2.duty%><%}else{%>&nbsp;<%}%></td>
                                        <td class="tdwhiteM"><%if(Roledata2 != null){%><%=Roledata2.departmentName%><%}else{%>&nbsp;<%}%></td>
                                        <td class="tdwhiteL"><%if(Roledata2 != null){%>&nbsp;<a href="mailto:<%=Roledata2.email%>"><%=Roledata2.email%></a><%}else{%>&nbsp;<%}%></td>
                                    </tr>
<%
        }
    }
%>
                                </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL" valign="top">
                        <%=messageService.getString("e3ps.message.ket_message", "00974") %><%--구성원--%><br>
                    </td>
                    <td class="tdwhiteL0">

                        <div id="memberDiv" style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="membertable">
                                <tr>
                                    <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                    <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                    <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                                    <td class="tdblueM">E-Mail</td>

                                </tr>
<%
    ProjectMemberLink link2 = null;
    PeopleData Memberdata2 = null;
    String peopleOID2 = "";

    QueryResult members2 = ProjectUserHelper.manager.getQueryForTeamUsers(project2, "MEMBER");
    while(members2.hasMoreElements()) {
        link2 = (ProjectMemberLink)members2.nextElement();
        Memberdata2 = new PeopleData(link2.getMember());
        peopleOID2 = (Memberdata2.people).getPersistInfo().getObjectIdentifier().getStringValue();
%>
                                <tr>
                                    <td class="tdwhiteM"><a href="JavaScript:viewPeople('<%=peopleOID2%>')">&nbsp;<%=Memberdata2.name%></a></td>
                                    <td class="tdwhiteM">&nbsp;<%=Memberdata2.duty%></td>
                                    <td class="tdwhiteM">&nbsp;<%=Memberdata2.departmentName%></td>
                                    <td class="tdwhiteL">&nbsp;<a href="mailto:<%=Memberdata2.email%>"><%=Memberdata2.email%></a></td>
                                    <td class="tdwhiteM0">

                                    </td>
                                </tr>
<%
    }
%>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>

            <!-- space -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space20"></td>
                </tr>
            </table>
            <!-- space -->


            <!-- 프로젝트 세부 정보 -->

            <!--table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="titleD">View 권한  비교</td>
                    <td align="right">
                        &nbsp;
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="85%">
                <tr>
                    <td class="tdblueL"  valign="top">
                        <%=messageService.getString("e3ps.message.ket_message", "01673") %><%--사용자--%><br>
                    </td>
                    <td class="tdwhiteL0">
                        <div id="refMemberDiv" style="width:99%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="refmembertable">
                                <tr>
                                    <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                    <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                    <td class="tdblueM" width="120"<%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                                    <td class="tdblueM">E-Mail</td>

                                </tr-->
<%
    /*PeopleData ViewUserdata = null;
    String viewPOID = "";
    QueryResult refMembers = ProjectUserHelper.manager.getQueryForTeamUsers(project, "ONLY_VIEW_MEMBER");
    while(refMembers.hasMoreElements()) {
        link = (ProjectMemberLink)refMembers.nextElement();
        ViewUserdata = new PeopleData(link.getMember());
        viewPOID = (ViewUserdata.people).getPersistInfo().getObjectIdentifier().getStringValue();*/
%>
                                <!--tr>
                                    <td class="tdwhiteM"><a href="JavaScript:viewPeople('<%//=viewPOID%>')">&nbsp;<%//=ViewUserdata.name%></a></td>
                                    <td class="tdwhiteM">&nbsp;<%//=ViewUserdata.duty%></td>
                                    <td class="tdwhiteM">&nbsp;<%//=ViewUserdata.departmentName%></td>
                                    <td class="tdwhiteL">&nbsp;<a href="mailto:<%//=ViewUserdata.email%>"><%//=ViewUserdata.email%></a></td>

                                </tr>
<%
    //}
%>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL" valign="top">
                        <%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%><br>
                    </td>
                    <td class="tdwhiteL0">
                        <div id="refDeptDiv" style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="refDeptTable">
                                <tr>
                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>

                                </tr-->
<%
    /*ProjectViewDepartmentLink deptLink = null;
    Department viewDept = null;
    QueryResult refDepts= ProjectUserHelper.manager.getViewDepartmentLink(project, null);
    Object deptObj[] = null;
    while(refDepts.hasMoreElements()) {
        deptObj = (Object[])refDepts.nextElement();
        deptLink = (ProjectViewDepartmentLink)deptObj[0];
        viewDept = deptLink.getDepartment();*/
%>
                                <!--tr>
                                    <td class="tdwhiteL"><%//=viewDept.getName()%></td>

                                </tr>
<%
    //}
%>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>

            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>

            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm5"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="85%">
                <tr>
                    <td class="tdblueL"  valign="top">
                        <%=messageService.getString("e3ps.message.ket_message", "01673") %><%--사용자--%><br>
                    </td>
                    <td class="tdwhiteL0">
                        <div id="refMemberDiv" style="width:99%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="refmembertable">
                                <tr>
                                    <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                    <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                    <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                                    <td class="tdblueM">E-Mail</td>
                                </tr-->
<%
    /*QueryResult refMembers2 = ProjectUserHelper.manager.getQueryForTeamUsers(project2, "ONLY_VIEW_MEMBER");
    String ViewMemberOID = "";
    PeopleData viewMemberData = null;
    while(refMembers2.hasMoreElements()) {
        link2 = (ProjectMemberLink)refMembers2.nextElement();
        viewMemberData = new PeopleData(link2.getMember());
        ViewMemberOID = (viewMemberData.people).getPersistInfo().getObjectIdentifier().getStringValue();*/
%>
                                <!--tr>
                                    <td class="tdwhiteM"><a href="JavaScript:viewPeople('<%//=ViewMemberOID%>')">&nbsp;<%//=viewMemberData.name%></a></td>
                                    <td class="tdwhiteM">&nbsp;<%//=viewMemberData.duty%></td>
                                    <td class="tdwhiteM">&nbsp;<%//=viewMemberData.departmentName%></td>
                                    <td class="tdwhiteL">&nbsp;<a href="mailto:<%//=viewMemberData.email%>"><%//=viewMemberData.email%></a></td>
                                </tr>
<%
    //}

%>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL" valign="top">
                        <%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%><br>
                    </td>
                    <td class="tdwhiteL0">
                        <div id="refDeptDiv" style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="refDeptTable">
                                <tr>
                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>

                                </tr-->
<%
    /*ProjectViewDepartmentLink deptLink2 = null;
    Department viewDept2 = null;
    QueryResult refDepts2= ProjectUserHelper.manager.getViewDepartmentLink(project2, null);
    Object deptObj2[] = null;
    while(refDepts2.hasMoreElements()) {
        deptObj2 = (Object[])refDepts2.nextElement();
        deptLink2 = (ProjectViewDepartmentLink)deptObj2[0];
        viewDept2 = deptLink2.getDepartment();*/
%>
                                <!--tr>
                                    <td class="tdwhiteL"><%//=viewDept2.getName()%></td>

                                </tr>
<%
    //}
%>
                            </table>
                        </div>
                    </td>
                </tr>
            </table-->



            <!-- //프로젝트 세부 정보 -->
<!------------------------------ 본문 끝 //------------------------------>
        </td>
    </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</form>

</body>
</html>
