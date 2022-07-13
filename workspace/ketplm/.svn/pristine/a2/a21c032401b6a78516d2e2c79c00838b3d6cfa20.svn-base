<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="wt.fc.*" %>
<%@page import="wt.query.*" %>
<%@page import ="e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
    String command = request.getParameter("command");
    String mode = request.getParameter("mode");

    String ecrNo = request.getParameter("ecrNo");
    String creator = request.getParameter("creator");

    String sessionidstring = request.getParameter("sessionid");
    String pagestring = request.getParameter("tpage");

    if(command == null)
        command = "";

    if(mode == null)
        mode = "multi";

    if(ecrNo == null)
        ecrNo = "";

    if(creator == null)
        creator = "";



    if(sessionidstring == null)
        sessionidstring = "0";

    if(pagestring == null)
        pagestring = "";


    int psize = 15;
    int cpage = 1;
    int total = 0;
    int pageCount = 10;

    long sessionid = Long.parseLong(sessionidstring);
    if(pagestring.length()>0)
        cpage = Integer.parseInt(pagestring);

    PagingQueryResult result = null;

    if(sessionid <= 0){
        QuerySpec qs = new QuerySpec();
        qs.setDescendantQuery(false);

        Class target = E3PSEChangeRequest.class;

        int idx = qs.appendClassList(target, true);

        SearchCondition sc = null;
        sc = new SearchCondition(target,"state.state", "=", "ECRCOMPLETED");
        qs.appendWhere(sc, new int[]{idx});

        if(ecrNo.length() > 0) {
            if(qs.getConditionCount() > 0)
                qs.appendAnd();

            sc = new SearchCondition(target, E3PSEChangeRequest.ECNUMBER, SearchCondition.LIKE, "%" + ecrNo + "%");
            qs.appendWhere(sc, new int[]{idx});
        }

        if(creator.length() > 0) {
            if(qs.getConditionCount() > 0)
                qs.appendAnd();
            sc = new SearchCondition(target, "iterationInfo.creator.key.id","=",CommonUtil.getOIDLongValue(creator));
            qs.appendWhere(sc, new int[]{idx});
        }

        ClassAttribute ca = new ClassAttribute(target, "thePersistInfo.modifyStamp");
        ca.setColumnAlias("wtsort"+String.valueOf(0));
        qs.appendSelect(ca, new int[]{idx}, false);
        OrderBy orderby = new OrderBy(ca, true, null);
        qs.appendOrderBy(orderby, new int[]{idx});

        result = PagingSessionHelper.openPagingSession(0, psize, qs);
    }
    else {
        result = PagingSessionHelper.fetchPagingSession((cpage-1) * psize, psize, sessionid);
    }

    if(result != null) {
        total = result.getTotalSize();
        sessionid = result.getSessionId();
    }

%>
<%@page import="e3ps.change.E3PSEChangeRequest"%>
<%@page import="e3ps.change.beans.ECRData"%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language="javascript" src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="javascript">
<!--
function gotoPage(p){
    document.forms[0].sessionid.value='<%=sessionid%>';
    document.forms[0].tpage.value=p;
    document.forms[0].submit();
}

function checkAll() {
    form = document.forms[0];
    if(form.oid) {
        var chkLen = form.oid.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                form.oid[i].checked = form.chkAll.checked;
            }
        }else{
            form.oid.checked = form.chkAll.checked;
        }
    }
}

function isCheckedCheckBox() {
    form = document.forms[0];
    if(form.oid == null) {
        return false;
    }

    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                return true;
            }
        }
    }
    else {
        if(form.oid.checked == true) {
            return true;
        }
    }

    return false;

}

function checkList() {
    form = document.forms[0];

    var arr = new Array();
    var subarr = new Array();

    if(!isCheckedCheckBox()) {
        return arr;
    }

    len = form.oid.length;

    var idx = 0;
    if(len) {
        for(var i = 0; i < len; i++) {
            if(form.oid[i].checked == true) {
                subarr = new Array();
                subarr[0] = form.oid[i].value;
                subarr[1] = form.oid[i].ecrNo;
                subarr[2] = form.oid[i].ecrName;
                subarr[3] = form.oid[i].creator;
                subarr[4] = form.oid[i].createDate;

                arr[idx++] = subarr;
            }
        }
    } else {
        if(form.oid.checked == true) {
            subarr = new Array();
            subarr[0] = form.oid.value;
            subarr[1] = form.oid.ecrNo;
            subarr[2] = form.oid.ecrName;
            subarr[3] = form.oid.creator;
            subarr[4] = form.oid.createDate;
            arr[idx++] = subarr;
        }
    }


    return arr;
}
// -->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form>
<!-- hidden begin -->
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>
<input type='hidden' name='command' value="<%=command%>">
<input type='hidden' name='mode' value="<%=mode%>">
<input type='hidden' name='ecrNo' value="<%=ecrNo%>">
<input type='hidden' name='creator' value="<%=creator%>">
<!-- hidden end -->

<!------------------------------ 본문 시작 //------------------------------>
            <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
                <tr>
                    <td class="tab_btm1"></td>
                </tr>
            </table>

            <table class="tab_w01" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="tdblueM" width="50">
<%
    if("select".equals(command)) {
        if("multi".equals(mode)) {
%>
                        <input name="chkAll" type="checkbox" class="Checkbox" onclick="javascript:checkAll();">
<%
        }
        else {
            out.println("&nbsp;");
        }
    }
    else {
        out.println("NO");
    }
%>
                    </td>
                    <td class="tdblueM" width="80">ECR NO</td>
                    <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                    <td class="tdblueM" width="60"><%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%></td>
                    <td class="tdblueM" width="60"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                    <td class="tdblueM"  width="100"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                </tr>
<%
    if(result.size() == 0) {
%>
        <TR bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
            <TD align=center colspan='5' class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></TD>
        </TR>

<%
    }
    else {
        int count = (psize*(cpage-1)) + 1;
        while ( result.hasMoreElements() ) {
            Object[] obj = (Object[])result.nextElement();
            E3PSEChangeRequest ecr = (E3PSEChangeRequest)obj[0];
            ECRData data = new ECRData(ecr);
%>
                    <tr>
                        <td class="tdwhiteM">
<%
    if("select".equals(command)) {
%>
                            <input name="oid" type="checkbox" class="Checkbox" <%if(!"multi".equals(mode)){%>onClick="oneClick(this)"<%}%>
                                    value='<%=CommonUtil.getOIDString(ecr)%>'
                                    ecrNo='<%=data.number%>'
                                    ecrName='<%=data.title%>'
                                    creator='<%=ecr.getCreatorFullName()%>'
                                    createDate='<%=DateUtil.getDateString(ecr.getPersistInfo().getCreateStamp(), "d") %>'/>
<%
    }
    else {
        out.println(count++);
    }
%>
                        </td>
                        <td class="tdwhiteL" title="<%=data.number%>" >
                            <div style="width:80;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                                <nobr>&nbsp;<a href="javascript:openView('<%=CommonUtil.getOIDString(ecr)%>')"><%=data.number%></nobr>
                            </div>
                        </td>
                        <td class="tdwhiteM" title="<%=data.title%>" >
                            <div style="width:250;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                                <nobr>&nbsp;<a href="javascript:openView('<%=CommonUtil.getOIDString(ecr)%>')"><%=data.title%></nobr>
                            </div>
                        </td>
                        <td class="tdwhiteM">&nbsp;<%=data.condition%></td>
                        <td class="tdwhiteM">&nbsp;<%=ecr.getCreatorFullName()%></td>
                        <td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(ecr.getPersistInfo().getCreateStamp(), "d") %></td>
                    </tr>
<%
        }
    }
%>
            </table>
<!------------------------------ 본문 끝 //------------------------------>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td class="space10"> </td>
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
                        <td width="45"align="center"><%if(end<ksize){%><a href="JavaScript:gotoPage(<%=ksize%>)" class="small"><%=messageService.getString("e3ps.message.ket_message", "01354") %><%--마지막--%></a><%}%></td>
                    </tr>
                </table>
          </td>
            <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
        </tr>
    </table>

</form>
</body>
</html>
