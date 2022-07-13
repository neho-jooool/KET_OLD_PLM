<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="wt.vc.Iterated"%>
<%@page import="wt.vc.VersionControlHelper"%>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.part.WTPart"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%!

public QueryResult getMethod(LifeCycleManaged lcm, String methodName)
{
    try
    {
        QuerySpec query = new QuerySpec();
        int index1 = query.addClassList(WfProcess.class, false);
        int index2 = query.addClassList(WfInternalMethod.class, true);
        query.appendWhere(new SearchCondition(WfProcess.class, "businessObjReference", "=", CommonUtil.getFullOIDString(lcm)), new int[]{0});
        query.appendAnd();
        //query.appendWhere(new SearchCondition(WfProcess.class, WfProcess.NAME, SearchCondition.LIKE, "KET_COMMON_WF%"), new int[]{0});
        query.appendWhere(new SearchCondition(WfProcess.class, WfProcess.NAME, SearchCondition.LIKE, "KET_COMMON_WF_NEW"), new int[]{0});
        SearchCondition sc0 = null;
        ClassAttribute ca0 = null;
        ClassAttribute ca1 = null;
        ca0 = new ClassAttribute(WfProcess.class, "thePersistInfo.theObjectIdentifier.id");
        ca1 = new ClassAttribute(WfInternalMethod.class, "parentProcessRef.key.id");
        sc0 = new SearchCondition(ca0, "=", ca1);
        sc0.setFromIndicies(new int[]{index1, index2}, 0);
        sc0.setOuterJoin(0);
        query.appendAnd();
        query.appendWhere(sc0, new int[]{index1, index2});
        query.appendAnd();
        query.appendWhere(new SearchCondition(WfInternalMethod.class, WfInternalMethod.NAME, "=", methodName), new int[]{index2});
        return PersistenceHelper.manager.find(query);
    }
    catch (Exception e)
    {
	Kogger.error(e);
    }
    return null;
}
//"승인완료 상태 설정"
%>
<%
    String partNumber = request.getParameter("partNumber");
    String ecoOid = request.getParameter("ecoOid");

    if(ecoOid == null){
        ecoOid = "";
    }
    QueryResult rs = MoldPartHelper.descentPart(partNumber, null, KETPartUsageLink.class, ecoOid);
    QueryResult rr = MoldPartHelper.getMoldProject(partNumber);
    QueryResult rp = null;

    if( ecoOid.length() > 0){
        rp = MoldPartHelper.descentPartECOState(ecoOid, partNumber);
    }
    Kogger.debug("rs ==>"+rs.size());


    String dieNo = "";
    String projectOid = "";
    String partProcesser = "";
    String partProcesserOid = "";
    String exDate = "";
    String planDate = "";

    if(ecoOid != null && ecoOid.length() > 0){
        KETMoldChangeOrder activity = (KETMoldChangeOrder)CommonUtil.getObject(ecoOid);

        exDate = EcmUtil.getLastApproveDate(activity);
    }

    if(rr.hasMoreElements()){
        Object[] o =(Object[])rr.nextElement();
        MoldProject project = (MoldProject)o[0];
        dieNo = project.getMoldInfo().getDieNo();
        Hashtable userH = TrySchduleHelper.getProjectRoleMember((E3PSProject)project);
        projectOid = CommonUtil.getOIDString(project);
        WTUser user = (WTUser)userH.get("Team_MOLD02");
        if(user != null){
            partProcesser = user.getFullName();
            partProcesserOid = CommonUtil.getOIDString(user);
        }

        int cha = ProjectTaskHelper.getDeBugChaSh(project);
        if(cha > 1){
            if(ecoOid != null && ecoOid.length() == 0){
                E3PSTask task = MoldProjectHelper.getDebugintTask(project, cha - 1,"제품도");
                if(task != null){
                    ExtendScheduleData sdata = (ExtendScheduleData)task.getTaskSchedule().getObject();
                    if(sdata.getExecEndDate() != null){
                        exDate = DateUtil.getDateString(sdata.getExecEndDate(), "d");
                    }
                }
            }

            E3PSTask dTask = MoldProjectHelper.getDebugintTask(project, cha - 1, "금형부품");
            if(dTask != null){
                ExtendScheduleData sdata = (ExtendScheduleData)dTask.getTaskSchedule().getObject();
                planDate = DateUtil.getDateString(sdata.getPlanEndDate(), "d");
            }

        }else{
            if(ecoOid != null && ecoOid.length() == 0){
                E3PSTask task = MoldProjectHelper.getTask(project, "제품도");
                if(task != null){
                    ExtendScheduleData sdata = (ExtendScheduleData)task.getTaskSchedule().getObject();
                    if(sdata.getExecEndDate() != null){
                        exDate = DateUtil.getDateString(sdata.getExecEndDate(), "d");
                    }
                }
            }
            E3PSTask mtask = MoldProjectHelper.getTask(project, "금형부품");
            if(mtask != null){
                ExtendScheduleData sdata = (ExtendScheduleData)mtask.getTaskSchedule().getObject();
                planDate = DateUtil.getDateString(sdata.getPlanEndDate(), "d");
            }
        }

    }
%>

<%@page import="wt.part.WTPartUsageLink"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.bom.entity.KETPartUsageLink"%>
<%@page import="wt.fc.Persistable"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<%@page import="java.util.Hashtable"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.ecm.entity.KETMoldECAEpmDocLink"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.MoldProjectHelper"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="wt.lifecycle.LifeCycleManaged"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.workflow.engine.WfProcess"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.workflow.robots.WfInternalMethod"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrder"%>
<%@page import="e3ps.ecm.beans.EcmUtil"%><%@page import="wt.epm.EPMDocument"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.edm.beans.EDMHelper"%>

<%@page import="e3ps.ecm.entity.KETMoldStdPartLine"%>
<stdinfo>
            <results>
                <contents>
                    <data_info>
                    <%
                    //if(rs.size() > 0 ){
                        while(rs != null && rs.hasMoreElements()){
                            Object o[] = (Object[])rs.nextElement();
                            WTPart part = (WTPart)o[0];
                            Persistable link = (Persistable)o[1];
                            String usageOid = "";
                            if(link != null){
                                usageOid = CommonUtil.getOIDString(link);
                            }
                            String number = part.getNumber();
                            if(number.lastIndexOf("A") != -1)
                                continue;
                            String name = part.getName();
                            String quantity = "";
                            String material = "";
                            if(link instanceof KETPartUsageLink){
                                KETPartUsageLink ketLink = (KETPartUsageLink)link;
                                material = ketLink.getMaterial();
                                quantity = ketLink.getQuantity();
                            }else if(link instanceof WTPartUsageLink){
                                WTPartUsageLink usageLink = (WTPartUsageLink)link;
                                int q = (int)usageLink.getQuantity().getAmount();
                                quantity = String.valueOf(q);
                            }
                            if(material == null){
                                material = "";
                            }
                            if(quantity == null){
                                quantity = "";
                            }
                            String partClass = "가공";
                            if(ecoOid.length() > 0){
                                EPMDocument epm = null;
                                try{
                                    QueryResult qr_link = null;
                                    Class target_link = KETMoldECAEpmDocLink.class;
                                    QuerySpec qs_link = new QuerySpec(target_link);
                                    qs_link.appendWhere(new SearchCondition(target_link,
                                            "dieNo", "=", ecoOid), new int[] {0 });
                                    qr_link = PersistenceHelper.manager.find(qs_link);
                                    KETMoldECAEpmDocLink klink = null;
                                    while(qr_link.hasMoreElements()){
                                        klink = (KETMoldECAEpmDocLink)qr_link.nextElement();
                                        epm = klink.getEpmDoc();
                                        ArrayList refsParts = EDMHelper.getReferencedByParts(epm,null,-1);
                                        WTPart refsPart = null;
                                        for(int i = 0; i < refsParts.size(); i++) {
                                            refsPart = (WTPart)((Object[])refsParts.get(i))[1];
                                            if(refsPart.equals(part)){
                                                if(klink.getChangeType() != null){
                                                    partClass = klink.getChangeType();
                                                }
                                            }
                                        }
                                    }
                                }catch(Exception e){
                                    Kogger.error(e);
                                }

                                Kogger.debug("dfasdf" + number.lastIndexOf("A"));
                            }
                        %>
                                <l_usageOid><![CDATA[<%=usageOid%>]]></l_usageOid>
                                <l_number><![CDATA[<%=number%>]]></l_number>
                                <l_name><![CDATA[<%=name%>]]></l_name>
                                <l_quantity><![CDATA[<%=quantity%>]]></l_quantity>
                                <l_material><![CDATA[<%=material%>]]></l_material>
                                <l_partClass><![CDATA[<%=partClass%>]]></l_partClass>
                    <%	}

                    //}
                    //if(rp.size() > 0){
                        while( rp != null && rp.hasMoreElements()){
                            Object o[] = (Object[])rp.nextElement();
                            KETMoldStdPartLine line = (KETMoldStdPartLine)o[0];
                            WTPart part = null;
                            Persistable link = null;
                            WTPart partRoot = MoldPartHelper.getWTPart(partNumber, null);
                            if(partRoot == null){
                            %>
                            <script>
                                alert('<%=messageService.getString("e3ps.message.ket_message", "01571") %><%--부품번호가 없습니다--%>');
                            </script>
                            <%
                                return;
                            }

                            if(line.getPartOid() != null){
                                part = (WTPart)CommonUtil.getObject( line.getPartOid() );
                                part = (WTPart)VersionControlHelper.service.getLatestIteration((Iterated)part, true);
                                QueryResult re = new QueryResult();
                                QuerySpec qs = new QuerySpec();
                                int index1 = qs.addClassList(KETPartUsageLink.class, true);
                                int index2 = qs.addClassList(WTPart.class, false);
                                SearchCondition sc = null;
                                qs.appendWhere(new SearchCondition(KETPartUsageLink.class, "roleAObjectRef.key.id", "=",
                                        partRoot.getPersistInfo().getObjectIdentifier().getId()), new int[] { index1 });
                                sc = new SearchCondition(
                                        new ClassAttribute(KETPartUsageLink.class, "roleBObjectRef.key.id"), "=", new ClassAttribute(
                                                WTPart.class, "masterReference.key.id"));

                                sc.setOuterJoin(0);
                                qs.appendAnd();
                                qs.appendWhere(sc, new int[] { index1, index2 });
                                qs.appendAnd();
                                qs.appendWhere(new SearchCondition(WTPart.class, "iterationInfo.latest",
                                        SearchCondition.IS_TRUE, true), new int[] { index2 });
                                qs.appendAnd();
                                qs.appendWhere(new SearchCondition(WTPart.class, "checkoutInfo.state", "<>", "c/o"),
                                        new int[] { index2 });

                                if(qs.getConditionCount() > 0 ){
                                    qs.appendAnd();
                                }
                                qs.appendWhere(new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", "=",
                                        CommonUtil.getOIDLongValue(part)), new int[] { index2 });

                                re = PersistenceHelper.manager.find(qs);
                                Object[] oo = null;
                                if(re.hasMoreElements() ){
                                    oo = (Object[])re.nextElement();
                                    link = (Persistable)oo[0];
                                }
                            }
                            String usageOid = "";
                            if(link != null){
                                usageOid = CommonUtil.getOIDString(link);
                            }
                            String number = part.getNumber();
                            String name = part.getName();
                            String quantity = "";
                            String material = "";

                            Kogger.debug("===" + link);

                            if(link instanceof KETPartUsageLink){
                                KETPartUsageLink ketLink = (KETPartUsageLink)link;
                                material = ketLink.getMaterial();
                                quantity = ketLink.getQuantity();
                                Kogger.debug(quantity);
                            }else if(link instanceof WTPartUsageLink){
                                WTPartUsageLink usageLink = (WTPartUsageLink)link;
                                int q = (int)usageLink.getQuantity().getAmount();
                                quantity = String.valueOf(q);
                                Kogger.debug(quantity + " WTPART");
                            }
                            if(material == null){
                                material = "";
                            }
                            if(quantity == null){
                                quantity = "";
                            }
                            String partClass = "가공";
                            if(ecoOid.length() > 0){
                                if(line != null){
                                    if(line.getChangeType() != null){
                                        partClass = line.getChangeType();
                                    }
                                }
                            }
                        %>

                            <l_usageOid><![CDATA[<%=usageOid%>]]></l_usageOid>
                            <l_number><![CDATA[<%=number%>]]></l_number>
                            <l_name><![CDATA[<%=name%>]]></l_name>
                            <l_quantity><![CDATA[<%=quantity%>]]></l_quantity>
                            <l_material><![CDATA[<%=material%>]]></l_material>
                            <l_partClass><![CDATA[<%=partClass%>]]></l_partClass>
                    <% }
                    //}
                    %>
                    </data_info>
                    <projectInfo>
                        <dieNo><![CDATA[<%=dieNo%>]]></dieNo>
                        <ecoOid><![CDATA[<%=ecoOid%>]]></ecoOid>
                        <projectOid><![CDATA[<%=projectOid%>]]></projectOid>
                        <partProcesser><![CDATA[<%=partProcesser%>]]></partProcesser>
                        <partProcesserOid><![CDATA[<%=partProcesserOid%>]]></partProcesserOid>
                        <exDate><![CDATA[<%=exDate%>]]></exDate>
                        <planDate><![CDATA[<%=planDate%>]]></planDate>
                    </projectInfo>
                    <message>
                        <l_message><![CDATA[<%="ggggfffffmmm"%>]]></l_message>
                    </message>
                </contents>
            </results>
</stdinfo>
