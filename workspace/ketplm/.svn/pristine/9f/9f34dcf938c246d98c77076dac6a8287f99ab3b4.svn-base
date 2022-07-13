<%@page import="ext.ket.project.program.service.ProgramHelper"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page contentType="text/html; charset=UTF-8"%>

<%@page import = "java.util.*"%>
<%@page import="
                        wt.fc.*,
                        wt.folder.*,
                        wt.lifecycle.*,
                        wt.org.*,
                        wt.part.*,
                        wt.project.Role,
                        wt.query.*,
                        wt.session.*,
                        wt.team.*,
                        wt.vc.*,
                        wt.vc.wip.*,
                        wt.workflow.engine.WfActivity,
                        wt.workflow.engine.WfProcess,
                        wt.workflow.work.WorkItem"%>
<%@page import="
                        e3ps.common.content.*,
                        e3ps.common.jdf.config.Config,
                        e3ps.common.jdf.config.ConfigImpl,
                        e3ps.common.jdf.log.Logger,
                        e3ps.common.query.*,
                        e3ps.common.util.*,
                        e3ps.ews.dao.PartnerDao,
                        e3ps.groupware.company.*,
                        e3ps.project.*,
                        e3ps.project.beans.*,
                        e3ps.project.historyprocess.HistoryHelper,
                        e3ps.project.outputtype.OEMPlan,
                        e3ps.project.outputtype.OEMProjectType,
                        e3ps.sap.*,
                        e3ps.wfm.entity.KETWfmApprovalMaster
                        " %>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
String menuType = request.getParameter("menuType");
if(StringUtil.isEmpty(menuType)) menuType = "A";
String tabImageType = "A";
String tab_1_img = "/plm/portal/images/tab_1.png";
String tab_2_img = "/plm/portal/images/tab_2.png";
String tab_3_img = "/plm/portal/images/tab_3.png";

%>
<script type="text/javascript">
<!--

//-->
</script>
                    <!-- Tab start -->
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="5"></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png"></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            if (!isCS) {
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01640")%><%--비용--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            }
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView5.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "00264")%><%--Issue--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <!-- /plm/extcore/jsp/project/gate/updateProjectAssessForm.jsp?oid= -->
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01381")%><%--목표--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                    <!-- /plm/ext/project/gate/updateProjectAssessForm.do -->
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=gateDrTabName%><%--Gate/DR--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView7.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "03034")%><%--프로그램--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView8.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">원가</a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                            </td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="#" onClick="javascript:top.close();"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>    
                        </tr>
                    </table>
