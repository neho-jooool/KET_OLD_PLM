<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.web.PageQueryBroker"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="java.util.HashMap"%>
<%@page import="wt.fc.PagingQueryResult"%>
<%@page import="e3ps.project.beans.SearchPagingProjectHelper"%>
<%@page import="wt.fc.PagingSessionHelper"%>
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />
<%@page import="e3ps.common.util.StringUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head><title><%=messageService.getString("e3ps.message.ket_message", "01378") %><%--목록--%></title>
<%
    String cmd = request.getParameter("cmd");
    String mode = request.getParameter("mode");
    String pjtNo = request.getParameter("pjtNo");
    String pjtName = request.getParameter("pjtName");
    String pjtType = request.getParameter("pjtType");
    String dType = request.getParameter("dType");
    String developentType = request.getParameter("developentType");

    if(cmd == null)
        cmd = "";

    if(mode == null || mode.length() == 0)
        mode = "";

    if(pjtNo == null){
        pjtNo = "";
    }
    if(pjtName == null){
        pjtName = "";
    }

    if(pjtType == null){
        pjtType = "";
    }

    if(dType == null){
        dType = "";

    }


////////////////////////////////////////////////////////////////
    // 검색
    ////////////////////////////////////////////////////////////////

    HashMap map = new HashMap();


//  if (tmpName != null && tmpName.length() > 0) {
//    map.put("pjtNo", tmpName);
//  }
    if (pjtNo != null && pjtNo.length() > 0) {
        map.put("pjtNo", pjtNo);
    }
    if (pjtName != null && pjtName.length() > 0) {
        map.put("pjtName", pjtName);
    }
//  if (pjtType != null && pjtType.length() > 0 ) {
        map.put("pjtType", pjtType);
//  }
    if (dType != null && dType.length() > 0 ) {
        map.put("dType", dType);
    }
    if (cmd != null && cmd.length() > 0 ) {
        map.put("command", cmd);
    }
    if (developentType != null && developentType.length() > 0 ) {
        map.put("DEVELOPENTTYPE", developentType);
    }
    boolean isProjectType = CommonUtil.isMember("자동차사업부");
    if(CommonUtil.isAdmin()){
        dType = "";
    }
    else if(isProjectType){
        dType = "1";
    }else{
        dType = "2";
    }
    map.put("dType", dType);


    int psize = 15;
    int cpage = 1;
    int total = 0;
    int pageCount = 10;

    //page
    String sessionidstring = request.getParameter("sessionid");
    if (sessionidstring == null){
        sessionidstring = "0";
    }
    long sessionid = Long.parseLong(sessionidstring);
    String pagestring = request.getParameter("tpage");
    if (pagestring != null && pagestring.length() > 0) {
        cpage = Integer.parseInt(pagestring);
    }

    PagingQueryResult result = null;
    if (sessionid <= 0) {
        result = SearchPagingProjectHelper.openPagingSession(map, 0, psize);
    } else {
        result = PagingSessionHelper.fetchPagingSession((cpage - 1)  * psize, psize, sessionid);
    }

    if (result != null) {
        total = result.getTotalSize();
        sessionid = result.getSessionId();
    }


%>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language="javascript">
<!--
function gotoPage(p){

            document.templateCopy.cmd.value='<%=cmd%>';

            document.templateCopy.sessionid.value='<%=sessionid%>';
            document.templateCopy.tpage.value=p;
            document.templateCopy.submit();
        }

function viewProject(oid) {
    var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid+"&popup=popup";
    openSameName(url,"1100","700","status=no,scrollbars=yes,resizable=no");
}

function tmpCopy(tmpid) {
    if(parent.tmpRegister) {
        parent.tmpRegister(tmpid);
    }
}

function onMoveEditPage(tmpoid) {
    //var url = "/plm/jsp/project/template/ViewListTemplate.jsp?oid=" + tmpoid;
    //newPopUpPage(url, 'EditPage','1220','800','yes', 'yes');
    parent.document.templateCreateForm.action="/plm/jsp/project/template/ViewListTemplate.jsp";
    parent.document.templateCreateForm.oid.value=oid;
    parent.document.templateCreateForm.submit();
}

var win = null;
function newPopUpPage(pageUrl,pageName,pageWidth,pageHeight,scroll, resize) {
    leftPosition = (screen.width) ? (screen.width - pageWidth)/2 : 0;
    topPosition = (screen.height) ? (screen.height - pageHeight)/2 : 0;
    pageOption = 'toolbar=no,menubar=no,status=no,titlebar=no';
    pageOption += ',height=' + pageHeight;
    pageOption += ',width=' + pageWidth;
    pageOption += ',top=' + topPosition;
    pageOption += ',left=' + leftPosition;
    pageOption += ',scrollbars=' + scroll;
    pageOption += ',resizable=' + resize;
    win = window.open(pageUrl,pageName,pageOption);
    win.focus();
}

function checkAll() {
    form = document.templateCopy;
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
    form = document.templateCopy;
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
    form = document.templateCopy;

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
                arr[idx++] = form.oid[i].value.split("†");
            }
        }
    } else {
        if(form.oid.checked == true) {
            arr[idx++] = form.oid.value.split("†");
        }
    }

    return arr;
}

function checkList2() {
    form = document.templateCopy;

    var arr = new Array();
    var subarr = new Array();//0:oid, 1:name, 2:no, 3:duration, 4:createDate, 5:modifyDate

    if(!isCheckedCheckBox()) {
        return arr;
    }

    len = form.oid.length;

    var idx = 0;
    if(len) {
        for(var i = 0; i < len; i++) {
            if(form.oid[i].checked == true) {

                subarr = new Array();
                subarr[0] = form.oid[i].value;//oid
                subarr[1] = form.oid[i].tempName;//name
                subarr[2] = form.oid[i].tempNo;//no
                subarr[3] = form.oid[i].duration;//duration
                subarr[4] = form.oid[i].createDate;//createDate
                subarr[5] = form.oid[i].modifyDate;
                subarr[6] = form.oid[i].pm;
                subarr[7] = form.oid[i].startDate;
                subarr[8] = form.oid[i].endDate;
                subarr[9] = form.oid[i].pjtDesc;
                subarr[10] = form.oid[i].partNo;
                subarr[11] = form.oid[i].customer;
                subarr[12] = form.oid[i].pmName;
                subarr[13] = form.oid[i].department;
                subarr[14] = form.oid[i].developmentType;
                subarr[15] = form.oid[i].planStartDate;
                subarr[16] = form.oid[i].representModel;

                arr[idx++] = subarr;
            }
        }
    } else {
        if(form.oid.checked == true) {
            subarr = new Array();
            subarr[0] = form.oid.value;//oid
            subarr[1] = form.oid.tempName;//name
            subarr[2] = form.oid.tempNo;//no
            subarr[3] = form.oid.duration;//duration
            subarr[4] = form.oid.createDate;//createDate
            subarr[5] = form.oid.modifyDate;//modifyDate
            subarr[6] = form.oid.pm;
            subarr[7] = form.oid.startDate;
            subarr[8] = form.oid.endDate;
            subarr[9] = form.oid.pjtDesc;
            subarr[10] = form.oid.partNo;
            subarr[11] = form.oid.customer;
            subarr[12] = form.oid.pmName;
            subarr[13] = form.oid.department;
            subarr[14] = form.oid.developmentType;
            subarr[15] = form.oid.planStartDate;
            subarr[16] = form.oid.representModel;
            arr[idx++] = subarr;
        }
    }


    return arr;
}
// -->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name=templateCopy method=post>
<!-- hidden begin -->
<input type='hidden' name='cmd' value="<%=cmd%>">
<input type='hidden' name='mode' value="<%=mode%>">
<input type='hidden' name='pjtNo' value="<%=pjtNo%>">
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>
<input type='hidden' name='pjtType' value="<%=pjtType %>">

<!-- hidden end -->

<!------------------------------ 본문 시작 //------------------------------>


            <table border="0" cellspacing="0" cellpadding="0" width="700">
                <tr>
                    <td class="tdblueM" width="50">
<%
    if("select".equals(cmd)) {
        if("multi".equals(mode)) {
%>
                        <input name="chkAll" type="checkbox" class="Checkbox" onclick="javascript:checkAll();">
<%
        }else {
            out.println("&nbsp;");
        }
    }else {
        out.println("NO");
    }
%>
                    </td>
                    <td class="tdblueM"  width="100"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
                    <td class="tdblueM">Project Name</td>
                    <td class="tdblueM" width="100">Project No</td>
                    <td class="tdblueM" width="60"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                    <td class="tdblueM"  width="100"><%=messageService.getString("e3ps.message.ket_message", "01200") %><%--담당--%></td>
                    <%if(mode.equals("")){ %>
                    <td class="tdblueM0" width="50">&nbsp;</td>
                    <%} %>
                </tr>
<%
    if(result.size() == 0) {
%>
        <TR bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
            <TD align=center colspan='7' class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></TD>
        </TR>

<%
    }else {
        int count= 1;
        while ( result.hasMoreElements() ) {
            Object[] obj = (Object[])result.nextElement();
            E3PSProjectData data = new E3PSProjectData((E3PSProject)obj[0]);
%>
                    <tr>
                        <td class="tdwhiteM">
    <%
        if("select".equals(cmd)) {
    %>
                                <input name="oid" type="checkbox" class="Checkbox" <%if(!"multi".equals(mode)){%>onClick="oneClick(this)"<%}%>
                                        value="<%=data.e3psPjtOID%>†<%=data.pjtName%>†<%=((E3PSProject)obj[0]).getPjtNo()%>†<%=data.pjtDuration%>†<%=DateUtil.getDateString(data.pjtCreateDate,"D")%>†<%=DateUtil.getDateString(data.pjtModifyDate,"D")%>†<%=data.pjtPm%>†<%=DateUtil.getDateString(data.pjtExecStartDate,"D")%>†<%=DateUtil.getDateString(data.pjtExecEndDate,"D")%>†<%=data.pjtDesc%>†<%=data.partNo%>†<%=data.customer%>†<%=data.pjtPmName%>†<%=data.department %>†<%=((E3PSProject)obj[0]).getDevelopentType()==null?"":StringUtil.checkNull(((E3PSProject)obj[0]).getDevelopentType().getName())%>†<%=DateUtil.getDateString(data.pjtPlanStartDate,"D")%>†<%=data.representModel%>"
                                        />
    <%
        }
        else {
            out.println(count++);
        }
    %>
                            </td>
                        <td class="tdwhiteM">&nbsp;<%=data.developenttypeName!=null ? NumberCodeUtil.getNumberCodeValue_Oid("DEVELOPENTTYPE", Long.toString(CommonUtil.getOIDLongValue(data.developenttypeOID)), messageService.getLocale().toString())  : ""%></td>
                        <td class="tdwhiteL">
                            <div style="width:300;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                                <nobr>&nbsp;<a href="javascript:viewProject('<%=data.e3psPjtOID%>')"><%=data.pjtName%></nobr>
                            </div>
                        </td>
                        <td class="tdwhiteM">&nbsp;<%=((E3PSProject)obj[0]).getPjtNo()%></td>
                        <td class="tdwhiteM">&nbsp;<%=data.pjtDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                        </td>
                        <td class="tdwhiteM">&nbsp;<%=data.pjtPmName%></td>
                        <%if(mode.equals("")){ %>
                        <td class="tdwhiteM0">
                            <a href="javascript:tmpCopy('<%=data.e3psPjtOID%>');">
                            <img src="/plm/e3pscore/portal/images/img_default/button/board_btn_copy.gif" alt="COPY" width="62" height="20" border="0">
                            </a>
                            <!--input type='button' value='Copy' onClick="javascript:tmpCopy('<%//=data.tempProjectOID%>');" class="s_font"-->
                        </td>
                        <%} %>
                    </tr>
<%
        }
    }
%>
            </table>
            <%
            int ksize = total / psize;
            int x = total % psize;
            if (x > 0)
                ksize++;
            int temp = cpage / pageCount;
            if ((cpage % pageCount) > 0)
                temp++;
            int start = (temp - 1) * pageCount + 1;

            int end = start + pageCount - 1;
            if (end > ksize) {
                end = ksize;
            }
        %>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
                <td>
                <table border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="25" align="center">
                        <%
                        if (start > 1) {
                        %><td width="25" align="center"><a href="JavaScript:gotoPage(1)" class="small"><img src="/plm/portal/images/btn_arrow4.gif" style='border:0'></a></td>
                        <%
                        }else{
                        %>
                        <td width="25" align="center">&nbsp;</td>
                        <%} %>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%
                        if (start > 1) {
                        %>
                        <td width="25" class="quick" align="center"><a
                            href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><img src="/plm/portal/images/btn_arrow3.gif" style='border:0'></a></td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%
                            }
                            for (int i = start; i <= end; i++) {
                        %>
                        <td style="padding:2 8 0 7;cursor:hand"
                            onMouseOver="this.style.background='#ECECEC'"
                            OnMouseOut="this.style.background=''" class="nav_on">
                        <%
                        if (i == cpage) {
                        %><b><%=i%>
                        <%
                        } else {
                        %><a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a>
                        <%
                        }
                        %>
                        </td>
                        <%
                            }
                            if (end < ksize) {
                        %>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="25" align="center"><a
                            href="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><img src="/plm/portal/images/btn_arrow1.gif" style='border:0'></a></td>
                        <%
                        }
                        %>
                        <td width="1" bgcolor="#dddddd"></td>

                        <%
                        if (end < ksize) {
                        %><td width="25" align="center">
                        <a href="JavaScript:gotoPage(<%=ksize%>)"
                            class="small"><img src="/plm/portal/images/btn_arrow2.gif" style='border:0'></a></td>
                        <%
                        }else{
                        %>
                        <td width="25" align="center">&nbsp;</td>
                        <%} %>
                    </tr>
                </table>
                </td>
                <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
            </tr>
        </table>
<!------------------------------ 본문 끝 //------------------------------>

</form>
</body>
</html>
