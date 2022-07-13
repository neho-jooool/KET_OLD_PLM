<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.project.ProjectOutput"%>
<%@page import="wt.fc.PagingQueryResult"%>
<%@page import="e3ps.edm.beans.EDMQueryHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="wt.fc.PagingSessionHelper"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
    String dieNo = request.getParameter("dieNo");
    String number = "";
    if(dieNo != null){
        number = dieNo + "*";
    }

    int psize = 10;
    int cpage = 1;
    int total = 0;
    int pageCount = 10;
    int listStart = (cpage-1)*psize+1;

    String sessionidstring = request.getParameter("sessionid");
    if(sessionidstring==null)sessionidstring = "0";
    long sessionid = Long.parseLong(sessionidstring);
    String pagestring = request.getParameter("tpage");
    if(pagestring!=null && pagestring.length()>0)cpage = Integer.parseInt(pagestring);

    PagingQueryResult result = null;
    if(sessionid <= 0){
        try {
            if(sessionid > 0L) {
                result = EDMQueryHelper.fetchPagingSession(listStart-1,psize,sessionid);
            } else {
                HashMap map = new HashMap();
                map.put("number",number);
                map.put("latest","true");
                map.put("className",wt.epm.EPMDocument.class.getName());



                //IBA 값 처리
                /*
                if(devStage.length() > 0) {
                    map.put(EDMHelper.IBA_DEV_STAGE, DevStage.toDevStage(devStage).getDisplay(Locale.KOREA));
                }
                if(category.length() > 0) {
                    map.put(EDMHelper.IBA_CAD_CATEGORY, CADCategory.toCADCategory(category).getDisplay(Locale.KOREA));
                }
                if(cadAppType.length() > 0) {
                    map.put(EDMHelper.IBA_CAD_APP_TYPE, CADAppType.toCADAppType(cadAppType).getDisplay(Locale.KOREA));
                }
                */

                result = EDMQueryHelper.openPagingSession(map, listStart-1, psize);

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

<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<style type="text/css">
<!--
.style1 {
        color: #FF0000
}
-->
</style>
<style type="text/css">

body {
    margin-left: 12px;
    margin-top: 15px;
    margin-right: 0px;
    margin-bottom: 5px;

    overflow-x:auto;
    overflow-y:auto;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
    scrollbar-arrow-color:#607ddb;
}

</style>

<script language='javascript'>
<!--
    function gotoPage(p){
        document.forms[0].sessionid.value='<%=sessionid%>';
        document.forms[0].tpage.value=p;
        document.forms[0].submit();
    }
    function doViewDoc(_oid){
        openView(_oid, null, null, false);
    }

//-->
</script>


<style type="text/css">
body {
    margin-top: 12px;
    margin-left:15px;
}
</style>
</head>
<body>
<form>


<!-- hidden begin -->
<input type='hidden' name='dieNo' value='<%=dieNo%>'>
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>

<!-- hidden end -->

<table border="0" cellpadding="0" cellspacing="0" width="620">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
               <table width="620" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td background="/plm/portal/images/logo_popupbg.png">
                 <table height="28" border="0" cellpadding="0" cellspacing="0">
                     <tr>
                       <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                       <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00904") %><%--관련 Die No 도면 목록--%></td>
                       <td width="10"></td>
                     </tr>
                 </table>
                 </td>
                 <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
               </tr>
                 </table>
            <table border="0" cellspacing="0" cellpadding="0" width="620">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="620">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="620">
                <tr>
                    <td class="space5"> </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="620">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="620">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="620">
                <tr>



                    <td width="160"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%> </td>
                    <td width="180" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                    <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                    <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02853") %><%--최종수정자--%></td>
                    <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>

                </tr>
<%
    if(result.size() == 0) {
%>
                <tr>
                    <td class='tdwhiteM0' align='center' colspan='6'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
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
                    <td class="tdwhiteL">
                        <a href="#" onClick="javascript:doViewDoc('<%=epmOid%>')">
                        <div style="width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr><%=rNumber%></nobr>
                        </div>
                        </a>
                    </td>
                    <td class="tdwhiteL"><%=rName%></td>
                    <td class="tdwhiteM"><%=rState%></td>
                    <td class="tdwhiteM"><%=rVer%></td>
                    <td class="tdwhiteM"><%=rFinalModifier%></td>
                    <td class="tdwhiteM"><%=rFinalModifyDate%></td>

                </tr>
<%
        }
    }
%>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="620">
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
            <table border="0" cellspacing="0" cellpadding="0" width="620">
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
        </td>
    </tr>
    <tr>
        <td height="30" valign="bottom"><table width="620" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="10">&nbsp;</td>
            <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="620" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
          </tr>
        </table></td>
    </tr>

</table>



</form>
</body>
</html>
