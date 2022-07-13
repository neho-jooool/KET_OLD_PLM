<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="
                wt.workflow.work.WorkItem,
                wt.fc.*,wt.content.*,
                wt.lifecycle.*,
                e3ps.common.util.WCUtil,
                java.util.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = request.getParameter("oid");
    String command = request.getParameter("command");
    if(oid == null) {
        oid = "";
    }

    if(command == null) {
        command = "";
    }

    ReferenceFactory rf = new ReferenceFactory();

    Object object = null;
    if(oid.length() > 0) {
        object = rf.getReference(oid).getObject();
    }

    if( (object != null) && !(object instanceof wt.lifecycle.LifeCycleManaged) ) {
%>
    <script language="javascript">
        alert("<%=messageService.getString("e3ps.message.ket_message", "00567") %><%--wtlifecycleLifeCycleManaged가 아닙니다--%>");
        document.location.href = "/plm/jsp/project/WfProcessInfo.jsp";
    </script>
<%
    }


    if("stateChange".equals(command)) {
        String state = request.getParameter("state");
        String terminate = request.getParameter("terminate");

        wt.lifecycle.LifeCycleManaged lcm = (wt.lifecycle.LifeCycleManaged)object;
        try {
            /*
            if("INWORK".equals(state)) {
                Kogger.debug("********** 결재선 초기화 시작 **********");
                WFItem wfItem = WFItemHelper.manager.getWFItem((WTObject)lcm);
                if(wfItem != null) {
                    wfItem.setObjectState(state);
                    wfItem = (WFItem)PersistenceHelper.manager.modify(wfItem);
                    WFItemHelper.manager.reworkDataInit(wfItem);
                }
                Kogger.debug("********** 결재선 초기화 끝 **********");
            }
            */
            lcm = wt.lifecycle.LifeCycleHelper.service.setLifeCycleState(lcm, wt.lifecycle.State.toState(state), "true".equals(terminate.toLowerCase()));
        }
        catch(Exception e) {
            Kogger.error(e);
        }
%>
    <script language="javascript">
        alert("<%=messageService.getString("e3ps.message.ket_message", "01761") %><%--상태 완료--%>");
        document.location.href = "/plm/jsp/project/WfProcessInfo.jsp?oid=<%=oid%>";
    </script>
<%
    }
    else if("reassign".equals(command)) {
        String lifecycle = request.getParameter("lifecycle");

        try {
            LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(lifecycle,WCUtil.getWTContainerRef());
            LifeCycleHelper.service.reassign((LifeCycleManaged)object, lct.getLifeCycleTemplateReference(),WCUtil.getWTContainerRef());
        }
        catch(Exception e) {
            Kogger.error(e);
        }
%>
    <script language="javascript">
        alert("<%=messageService.getString("e3ps.message.ket_message", "00441") %><%--Reassign 완료--%>");
        document.location.href = "/plm/jsp/project/WfProcessInfo.jsp?oid=<%=oid%>";
    </script>
<%
    }
%>
<HTML>
<HEAD>
<TITLE> WfProcess </TITLE>
<script language='javascript'>
<!--
function onSubmit() {
    alert(document.forms[0].oid.value);
    document.location.href = "/plm/jsp/project/WfProcessInfo.jsp?oid=" + document.forms[0].oid.value;
}

function onBack() {
    document.location.href = "/plm/jsp/project/WfProcessInfo.jsp?oid=";
}

function processHistory(url) {
    var screenWidth = screen.availWidth/2-320;
    var screenHeight = screen.availHeight/2-300;
    var windowWin = window.open(url,"processHistory","status=no,resizable=yes, scrollbars=yes,width=800,height=600,top="+screenHeight/2+",left="+screenWidth/2);
    windowWin.focus();
}

function onStateChange() {
    form = document.forms[0];
    var param = "command=stateChange";
    param += "&oid=" + document.forms[0].oid.value;
    param += "&state=" + document.forms[0].state.value;
    param += "&terminate=" + document.forms[0].terminate.value;
    document.location.href = "/plm/jsp/project/WfProcessInfo.jsp?" + param;
    /*
    document.forms[0].command.value = 'stateChange';
    document.forms[0].method = 'post';
    document.forms[0].action = "/plm/jsp/project/WfProcessInfo.jsp";
    document.forms[0].submit();
    */
}

function onReassign() {
    form = document.forms[0];

    var param = 'command=reassign';
    param += "&oid=" + form.oid.value;
    param += "&lifecycle=" + form.lifecycle.value;
    document.location.href = "/plm/jsp/project/WfProcessInfo.jsp?" + param;
}

function resizestart() {
    alert('resizestart');
}

function resizeend() {
    alert('resizeend');
}

function resize() {
    alert('resize');
}

document.onresizestart=resizestart;
document.onresizeend=resizeend;
document.onresize=resize;

// -->
</script>
<style type="text/css" media="screen">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}

h1#header
{
    color: #036;
    font-size: 120%;
    font-weight: bold;
    text-transform: uppercase;
    text-align: center;
    letter-spacing: .5em;
    padding: .4em 0;
    border-top: 1px solid #069;
    border-bottom: 1px solid #069;
}

.label0 {
    text-align:center;
    background-color:#ADD8E6;
    font: 1em arial, helvetica, sans-serif;
    color:#191970;

}

.td0 {
    text-align:left;
    background-color:#FFFFFF;
    font: 1em arial, helvetica, sans-serif;
    color:#191970;

}

a {
    text-decoration: none;
}

// -->
</style>
</HEAD>

<body>
<form method='post'>

<h1 id="header">
    CAUTION
</h1>

<%
    if(object == null) {
%>
        <table>
            <tr>
                <td>
                    <input type='text' name='oid' value=''> <input type='button' name='submit' value='submit' onclick="javascript:onSubmit();">
                </td>
            </tr>
        </table>

<%
    }
    else {
%>
    <!-- hidden begin -->
    <input type='hidden' name='oid' value='<%=oid%>'>
    <input type='hidden' name='command' value=''>
    <!-- hidden end -->
    <%
        wt.lifecycle.LifeCycleManaged lcm = (wt.lifecycle.LifeCycleManaged)object;
        LifeCycleTemplate lct = (LifeCycleTemplate)lcm.getLifeCycleTemplate().getObject();
    %>
    <table cellspacing="1" cellpadding="0" border="0" style="background-color:#4682B4;padding: .4em 0;" width="100%">
    <col width='15%'><col width='85%'>
        <tr>
            <td class='label0'>
                OID
            </td>
            <td class='td0'>
                <%=  rf.getReferenceString(lcm)%>
            </td>
        </tr>
        <tr>
            <td class='label0'>
                LifeCycleTemplate
            </td>
            <td class='td0'>
                <%=lct.getName()%><br>
                New LifeCycleTemplate&nbsp;:&nbsp;<input type='text' name='lifecycle' value=''>
                <input type='button' name='reassign' value='reassign' onclick="javascript:onReassign();">
            </td>
        </tr>
        <tr>
            <td class='label0'>
                State
            </td>
            <td class='td0'>
                <%=lcm.getLifeCycleState().getDisplay(messageService.getLocale())%><br>
                <select name="state" style="width:110" >
                <%
                    String curState = lcm.getLifeCycleState().toString();
                    try {
                        //LifeCycleTemplate lct = (LifeCycleTemplate)lcm.getLifeCycleTemplate().getObject();
                        Vector states = LifeCycleHelper.service.getPhaseTemplates(lct);
                        PhaseTemplate pt = null;
                        wt.lifecycle.State lcState = null;
                        for(int i = 0; i < states.size(); i++) {
                            pt = (PhaseTemplate)states.get(i);
                            lcState = pt.getPhaseState();
                %>
                        <OPTION VALUE="<%=lcState.toString()%>"><%=lcState.getDisplay(messageService.getLocale())%></OPTION>
                <%
                        }
                    } catch(Exception e) {
                	Kogger.error(e);
                    }
                %>
                </select>
                &nbsp;&nbsp;
                TERMINATE : <input type='radio' name='terminate' value='true' checked>TRUE&nbsp;<input type='radio' name='terminate' value='false'>FALSE

                <input type='button' name='statechange' value='<%=messageService.getString("e3ps.message.ket_message", "01762") %><%--상태변경--%>' onclick="javascript:onStateChange();">
            </td>
        </tr>
    </table>
    <br>
    <table cellspacing="1" cellpadding="0" border="0" style="background-color:#4682B4;padding: .4em 0;" width="100%">
    <col width='60%'><col width='20%'><col width='20%'>
        <tr>
            <td class='label0'>
                Name
            </td>
            <td class='label0'>
                State
            </td>
            <td class='label0'>
                Created Time
            </td>

        </tr>
    <%
        try {
            //java.util.Enumeration enum = wt.workflow.engine.WfEngineHelper.service.getAssociatedProcesses((Persistable)object, null);
            QueryResult result = wt.workflow.engine.WfEngineHelper.service.getAssociatedProcesses((Persistable)object, null, WCUtil.getWTContainerRef());

            String wfProcessOid = "";
            wt.workflow.engine.WfProcess wfprocess = null;
            wt.workflow.engine.WfState wfState = null;
            while (result.hasMoreElements()) {
                wfprocess = (wt.workflow.engine.WfProcess)result.nextElement();
                wfProcessOid = wfprocess.getPersistInfo().getObjectIdentifier().getStringValue();
                wfState = wfprocess.getState();
    %>


        <tr>
            <td class='td0'>
            <!-- //[START] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리, WF Monitor URL 변경 2014-06-10, Jason Han -->
                <%-- <a href="#" onclick="javascript:processHistory('/plm/servlet/WindchillAuthGW/wt.enterprise.URLProcessor/URLTemplateAction?u8&oid=<%=wfProcessOid%>&action=ProcessManager');"> --%>
                <a href="#" onclick="javascript:processHistory('/plm/app/#ptc1/tcomp/infoPage?oid=OR:<%=wfProcessOid%>&u8=1');">
			<!-- //[END] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리,  WF Monitor URL 변경 2014-06-10, Jason Han -->
                <%=wfprocess.getName()%>
                </a>
            </td>
            <td class='td0'>
                <%=wfState.getDisplay(messageService.getLocale())%>
            </td>
            <td class='td0'>
                <%=wfprocess.getCreateTimestamp().toLocaleString()%>
            </td>
        </tr>

    <%
            }
        } catch(Exception e) {
            Kogger.error(e);
        }
    %>
    </table>
    <br>
<%
    }
%>


    <%
        if(object instanceof wt.content.ContentHolder) {
            ContentHolder holder = (ContentHolder)object;
            holder = wt.content.ContentHelper.service.getContents(holder);
    %>
    <table cellspacing="1" cellpadding="0" border="0" style="background-color:#4682B4;padding: .4em 0;" width="100%">
    <col width='15%'><col width='85%'>
        <tr>
            <td class='label0'>
                File
            </td>
            <td class='td0'>

    <%
            Vector cotents = wt.content.ContentHelper.getContentListAll(holder);
            if(cotents.size() > 0) {

                wt.content.ContentItem contentItem = null;
                for(int i = 0; i < cotents.size(); i++) {
                    contentItem = (wt.content.ContentItem)cotents.get(i);
                    if(contentItem instanceof wt.content.ApplicationData) {

                        if(i > 0) {
                            out.println("<br>");
                        }

                        wt.content.ApplicationData appData = (wt.content.ApplicationData)contentItem;

                        java.net.URL downloadURL = wt.content.ContentHelper.getDownloadURL ( holder , appData );
    %>
                <a target='blank' href='<%=downloadURL.toString()%>'><%=appData.getFileName()%></a>
    <%
                    }
                }
            }

    %>
            </td>
        </tr>
    </table>
    <%
        }
    %>
    <table>
        <tr>
            <td>
                <input type='button' name='submit' value='<%=messageService.getString("e3ps.message.ket_message", "01309") %><%--뒤로--%>' onclick="javascript:onBack();">
            </td>
        </tr>
    </table>
</form>

</BODY>
</HTML>
