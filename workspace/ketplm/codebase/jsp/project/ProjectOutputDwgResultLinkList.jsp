<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.fc.*"%>
<%@page import = "wt.org.*"%>
<%@page import = "e3ps.common.util.*,e3ps.common.code.*"%>
<%@page import = "e3ps.groupware.company.*"%>
<%@page import = "e3ps.project.*,e3ps.project.beans.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
    long beginTime = System.currentTimeMillis();

    EDMProperties edmProperties = EDMProperties.getInstance();
    EDMAttributes edmAttributes = EDMAttributes.getInstance();

    WTContainerRef wtContainerRef = EDMUtil.getWTContainerRef(edmProperties.getContainer());

    String command = request.getParameter("command");
    String latest = request.getParameter("latest");

    String businessType = request.getParameter("businessType");
    String devStage = request.getParameter("devStage");
    String category = request.getParameter("category");
    String cadAppType = request.getParameter("cadAppType");

    if(command == null) { command = "";  }
    if(latest == null) { latest = "true";  }

    if(devStage == null) { devStage = "";  }
    if(category == null) { category = "";  }
    if(cadAppType == null) { cadAppType = "";  }

    String cmd = request.getParameter("cmd");
    String mode = request.getParameter("mode");
    if(cmd == null) cmd = "search";
    if(mode == null) mode = "one";

    boolean isAdmin = CommonUtil.isAdmin();

    String number = request.getParameter("number");//도면번호
    String name = request.getParameter("name");//도면명
    String islastversion = request.getParameter("islastversion");//버젼
    String state = request.getParameter("state");//상태

    //작성일
    String create_start = request.getParameter("predate");
    String create_end = request.getParameter("postdate");
    String creator = request.getParameter("creator");

    //최종수정일
    String modify_start = request.getParameter("modify_start");
    String modify_end = request.getParameter("modify_end");
    String modifier = request.getParameter("modifier");

    String folderpath = request.getParameter("folderpath");

    String partNumber = request.getParameter("partNumber");//부품번호
    String partName = request.getParameter("partName");//부품명
    String Project_no = request.getParameter("Project_no");//Project_no

    if(number == null)
        number = "";
    if(name == null)
        name = "";
    if(islastversion == null)
        islastversion = "true";

    if(state == null)
        state = "APPROVED";

    if(create_start == null)
        create_start = "";
    if(create_end == null)
        create_end = "";
    if(creator == null)
        creator = "";

    if(modify_start == null)
        modify_start = "";
    if(modify_end == null)
        modify_end = "";
    if(modifier == null)
        modifier = "";

    if(partNumber == null)
        partNumber = "";
    if(partName == null)
        partName = "";

    if(Project_no == null)
        Project_no = "";

    int psize = 15;
    int cpage = 1;
    int total = 0;
    int pageCount = 10;
    int listStart = (cpage-1)*psize+1;

    String sessionidstring = request.getParameter("sessionid");
    if(sessionidstring==null)sessionidstring = "0";
    long sessionid = Long.parseLong(sessionidstring);
    String pagestring = request.getParameter("tpage");
    if(pagestring!=null && pagestring.length()>0)cpage = Integer.parseInt(pagestring);
    WTUser ssssionWtuser = (WTUser)SessionHelper.manager.getPrincipal();
    String sessionUser = "";



    if(creator != null){
        QuerySpec spec = new QuerySpec();
        Class peopleClass = People.class;
        int peopleClassPos = spec.addClassList(peopleClass,true);
        if  ( spec.getConditionCount() > 0 ) spec.appendAnd();
        spec.appendWhere(new SearchCondition(peopleClass,People.NAME,SearchCondition.EQUAL, creator),new int[]{peopleClassPos});
        QueryResult qr = PersistenceHelper.manager.find(spec);
        if(qr.hasMoreElements()){
            Object[] obj = (Object[])qr.nextElement();
            PeopleData data = new PeopleData(obj[0]);
            sessionUser = CommonUtil.getOIDString(data.wtuser);
        }
    }

    PagingQueryResult result = null;
    if(sessionid <= 0){
        try {
            if(sessionid > 0L) {
                result = EDMQueryHelper.fetchPagingSession(listStart-1,psize,sessionid);
            } else {
                HashMap map = new HashMap();
                map.put("number",number);
                map.put("name",name);
                map.put("state",state);
                map.put("latest",latest);
                map.put("className",wt.epm.EPMDocument.class.getName());
                map.put("creator", sessionUser);
                map.put("create_start", create_start);
                map.put("create_end", create_end);
                map.put("modify_start", modify_start);
                map.put("modify_end", modify_end);

                //IBA 값 처리
                if(devStage.length() > 0) {
                    map.put(EDMHelper.IBA_DEV_STAGE, DevStage.toDevStage(devStage).getDisplay(Locale.KOREA));
                }
                if(category.length() > 0) {
                    map.put(EDMHelper.IBA_CAD_CATEGORY, CADCategory.toCADCategory(category).getDisplay(Locale.KOREA));
                }
                if(cadAppType.length() > 0) {
                    map.put(EDMHelper.IBA_CAD_APP_TYPE, CADAppType.toCADAppType(cadAppType).getDisplay(Locale.KOREA));
                }

                result = EDMQueryHelper.openPagingSession(map,listStart-1,psize);

                sessionid = result.getSessionId();
                total = result.getTotalSize();
            }
        }catch(Exception e) { Kogger.error(e); }

    }
    else {
        result = PagingSessionHelper.fetchPagingSession((cpage-1) * psize, psize, sessionid);
    }

    if(result != null){
        total = result.getTotalSize();
        sessionid = result.getSessionId();
    }

%>
<%@page import="e3ps.common.jdf.config.ConfigEx"%>
<%@page import="wt.part.WTPart"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="e3ps.edm.beans.EDMQueryHelper"%>
<%@page import="e3ps.edm.util.EDMProperties"%>
<%@page import="e3ps.edm.util.EDMAttributes"%>
<%@page import="wt.inf.container.WTContainerRef"%>
<%@page import="e3ps.edm.beans.EDMServiceHelper"%>
<%@page import="e3ps.edm.beans.EDMHelper"%>
<%@page import="e3ps.edm.DevStage"%>
<%@page import="e3ps.edm.CADCategory"%>
<%@page import="e3ps.edm.CADAppType"%>
<%@page import="e3ps.edm.util.EDMUtil"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<html>
<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<title><%=messageService.getString("e3ps.message.ket_message", "01395") %><%--문서 보기--%></title>
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language="javascript" src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoPage(p){
    document.forms[0].sessionid.value='<%=sessionid%>';
    document.forms[0].tpage.value=p;
    document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body>
<form method="post">
<!-- hidden begin -->
<input type='hidden' name='cmd' value='<%=cmd%>'>
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>
<!-- hidden end -->
<table border="0" cellpadding="0" cellspacing="0" width="700">
    <tr>
      <td valign="top" style="padding-left:0"><!------------------------------ 본문 시작 //------------------------------>
            <table border="0" cellpadding="0" cellspacing="0" width="700">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="700">
                <tr>
                    <td  class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="700">
                <tr>



                    <td width="130"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%> </td>
                    <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                    <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>

                    <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02853") %><%--최종수정자--%></td>
                    <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>
                    <td width="80" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></td>
                </tr>
<%
    if(result.size() == 0) {
%>
                <tr>
                    <td class='tdwhiteM0' align='center' colspan='7'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                </tr>
<%
    }
    else {
        ReferenceFactory rf = new ReferenceFactory();

        Object[] obj = null;
        String epmOid = "";
        String rNumber = "";
        String rName = "";
        String rVer = "";
        String rState = "";
        String rFinalModifyDate = "";
        String rFinalModifier = "";

        EPMDocument epm = null;
        while(result.hasMoreElements()){
            obj = (Object[])result.nextElement();
            epmOid = (String)obj[0];
            epm = (EPMDocument)rf.getReference(epmOid).getObject();
            //data = new EpmData(epm);

            rNumber = epm.getNumber();
            rName = epm.getName();
            //rVer = E3psVersionHelper.getVersion(epm);
            rVer = epm.getVersionInfo().getIdentifier().getValue();
            //rVer += "." + epm.getIterationInfo().getIdentifier().getValue();
            rState = epm.getLifeCycleState().getDisplay();
            rFinalModifyDate = DateUtil.getDateString(epm.getModifyTimestamp(), "d");
            rFinalModifier = epm.getModifier().getFullName();
%>
                <tr>
                    <td class="tdwhiteM"><%=rNumber%></td>
                    <td class="tdwhiteL" title='<%=rName%>'>
                        <div style="width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr><%=rName%></nobr>
                        </div>
                    </td>
                    <td class="tdwhiteM"><%=rState%></td>
                    <td class="tdwhiteM"><%=rVer%></td>
                    <td class="tdwhiteM"><%=rFinalModifier%></td>
                    <td class="tdwhiteM"><%=rFinalModifyDate%></td>
                    <td class="tdwhiteM0">
                        <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>' onClick="javascript:parent.onSave('<%=epmOid%>');" class="s_font">
                    </td>
                </tr>
<%
        }
    }
%>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
                <tr>
                    <td class="space5"> </td>
                </tr>
            </table>

<!-- 리스트 시작 //-->
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
            <table border="0" cellspacing="0" cellpadding="0" width="700">
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
                                <td width="45"align="center"><%if(end<ksize){%><a href="JavaScript:gotoPage(<%=ksize%>)" class="small"><%=messageService.getString("e3ps.message.ket_message", "01354") %><%--마지막--%></a><%}%></td>
                            </tr>
                        </table>
                  </td>
                    <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
                </tr>
            </table>
<!-- 리스트 끝 //-->
      </td>
    </tr>
</table>


</body>
</html>
