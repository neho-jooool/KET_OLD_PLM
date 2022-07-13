<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.doc.*,wt.fc.*,wt.org.*,wt.query.*"%>
<%@page import = "e3ps.access.*,e3ps.access.service.*"%>
<%@page import = "e3ps.common.util.*,e3ps.common.obj.ObjectData"%>
<%@page import = "e3ps.groupware.company.*"%>
<%@page import = "e3ps.doc.*,e3ps.doc.beans.*"%>
<%@page import = "e3ps.project.*,e3ps.project.beans.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
    String oid = request.getParameter("oid");
    String command = request.getParameter("command");
    String mode = request.getParameter("mode");
    String fromPage = request.getParameter("fromPage");

    if(command == null) {
        command = "";
    }

    if(mode == null) {
        mode = "";
    }

    if(fromPage == null) {
        fromPage = "";
    }


    TemplateProject project = null;

    HashMap map = null;

    ReferenceFactory rf = new ReferenceFactory();
    Object obj = rf.getReference(oid).getObject();
    if(obj instanceof TemplateProject) {
        project = (TemplateProject)obj;
    }
    else if(obj instanceof ProjectOutput) {
        ProjectOutput output = (ProjectOutput)obj;
        project = output.getProject();
        if("select".equals(command)) {
            QueryResult viewQr = ProjectOutputHelper.getOutputViewMember(output, false);
            Object viewObj[] = null;
            while(viewQr.hasMoreElements()) {
                viewObj = (Object[])viewQr.nextElement();
                if(map == null) {
                    map = new HashMap();
                }
                map.put(viewObj[0], viewObj[0]);
            }
        }
    }
    else if(obj instanceof TemplateTask) {
        TemplateTask task = (TemplateTask)obj;
        project = task.getProject();
    }
%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.style1 {
        color: #FF0000
}

.listDiv {
    margin: 0;
    border: 0;
    padding: 0;
    overflow-x:hidden;
    overflow-y:auto;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
    scrollbar-arrow-color:#607ddb;
}
-->
</style>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<script language='javascript'>
<!--
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
    var subarr = new Array();//0:oid

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
<%
    if("OutputCreatePage".equals(fromPage)) {
%>
                subarr[1] = form.oid[i].userName;
                subarr[2] = form.oid[i].dutyName;
                subarr[3] = form.oid[i].deptName;
                subarr[4] = form.oid[i].email;
                subarr[5] = form.oid[i].peopleOID;
                subarr[6] = form.oid[i].isView;
                subarr[7] = form.oid[i].isDept;
<%
    }
%>
                arr[idx++] = subarr;
            }
        }
    } else {
        if(form.oid.checked == true) {
            subarr = new Array();
            subarr[0] = form.oid.value;//oid
<%
    if("OutputCreatePage".equals(fromPage)) {
%>
            subarr[1] = form.oid.userName;
            subarr[2] = form.oid.dutyName;
            subarr[3] = form.oid.deptName;
            subarr[4] = form.oid.email;
            subarr[5] = form.oid.peopleOID;
            subarr[6] = form.oid.isView;
            subarr[7] = form.oid.isDept;
<%
    }
%>
            arr[idx++] = subarr;
        }
    }

    return arr;
}

function onSelect() {
    var arr = checkList();
    window.returnValue = arr;
    window.close();
}
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
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form method="post">
    <table border="0" cellpadding="0" cellspacing="0" class="popBox">
        <tr>
            <td valign="top" style="padding:0px 0px 0px 0px">
                <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
                  <tr>
                    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03061") %><%--프로젝트 구성원--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px"></td>
                    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
                  </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right">
                            <% if("select".equals(command)) { %>
                            <a href="javascript:onSelect();">
                            <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
                            </a>
                            &nbsp;&nbsp;
                            <% } %>
                            <a href="javascript:self.close();">
                            <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                            </a>
                        </td>
                    </tr>
                </table>
                <div class="listDiv" style="width:580;height:400px;">
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
<%
    if("select".equals(command)) {
%>
                        <td class="tdblueM" width="40">
<%
        if("multi".equals(mode)) {
%>
                            <input name="chkAll" type="checkbox" class="Checkbox" onclick="javascript:checkAll();">
<%
        } else {
%>
                            &nbsp;
<%
        }
%>
                        </td>
<%
    }
%>

                        <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                        <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                        <td class="tdblueM"  width="120"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                        <td class="tdblueM0">E-Mail</td>
                    </tr>
<%
    QueryResult result = null;
    try {
        QuerySpec qs = new QuerySpec();
        int idx = qs.appendClassList(ProjectMemberLink.class, true);
        SearchCondition sc = null;

        sc = new SearchCondition(ProjectMemberLink.class,
                                "roleAObjectRef.key.id",
                                SearchCondition.EQUAL,
                                project.getPersistInfo().getObjectIdentifier().getId());
        qs.appendWhere(sc, new int[]{idx});

        qs.appendAnd();
        qs.appendOpenParen();

        sc = new SearchCondition(ProjectMemberLink.class,
                                ProjectMemberLink.PJT_MEMBER_TYPE,
                                SearchCondition.EQUAL,
                                ProjectUserHelper.MEMBER);
        qs.appendWhere(sc, new int[]{idx});

        qs.appendOr();
        sc = new SearchCondition(ProjectMemberLink.class,
                                ProjectMemberLink.PJT_MEMBER_TYPE,
                                SearchCondition.EQUAL,
                                ProjectUserHelper.ONLY_VIEW_MEMBER);
        qs.appendWhere(sc, new int[]{idx});
        qs.appendCloseParen();

        ClassAttribute ca = new ClassAttribute(ProjectMemberLink.class, ProjectMemberLink.PJT_MEMBER_TYPE);
        ca.setColumnAlias("wtsort"+String.valueOf(0));
        qs.appendSelect(ca, new int[]{idx}, false);
        OrderBy orderby = new OrderBy(ca, false, null);
        qs.appendOrderBy(orderby, new int[]{idx});

        result = PersistenceHelper.manager.find(qs);



        WTUser wtuser = null;
        PeopleData peopleData = null;
        String peopleOID = null;
        String memberLinkOid = "";
        String fontColor = "";

        boolean isView = false;

        ProjectMemberLink link = null;
        Object obj1[] = null;
        while(result.hasMoreElements()) {
            obj1 = (Object[])result.nextElement();
            link = (ProjectMemberLink)obj1[0];

            memberLinkOid = link.getPersistInfo().getObjectIdentifier().getStringValue();
            wtuser = link.getMember();
            peopleData = new PeopleData(wtuser);
            peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();

            isView = false;
            if(link.getPjtMemberType() == ProjectUserHelper.ONLY_VIEW_MEMBER) {
                isView = true;
            }

            fontColor = "#black";
            if(isView) {
                fontColor = "#brown";
            }
%>
                    <tr>
<%
            if("select".equals(command)){
%>
                        <td class="tdwhiteM">
                            <input name="oid" type="checkbox" class="Checkbox" <%if(!"multi".equals(mode)){%>onClick="oneClick(this)"<%}%>
                                            <%if(map != null && map.get(memberLinkOid) != null){%>checked<%}%>
<%
                if("OutputCreatePage".equals(fromPage)) {
%>
                                            userName='<%=peopleData.name%>'
                                            dutyName='<%=peopleData.duty%>'
                                            deptName='<%=peopleData.departmentName%>'
                                            email='<%=peopleData.email%>'
                                            peopleOID='<%=peopleOID%>'
                                            isView='<%=isView%>'
                                            isDept='false'
<%
                }
%>
                                            value='<%=memberLinkOid%>' />
                        </td>
<%
            }
%>
                        <td class="tdwhiteL"><a href="JavaScript:viewPeople('<%=peopleOID%>');"><font color="<%=fontColor%>"><%=peopleData.name%></font></a></td>
                        <td class="tdwhiteM"><%=peopleData.duty%></td>
                        <td class="tdwhiteM"><%=peopleData.departmentName%></td>
                        <td class="tdwhiteL0">&nbsp;<%=peopleData.email%></td>
                    </tr>
<%
        }

    }
    catch(Exception e) {
	Kogger.error(e);
    }

    QueryResult viewDepts = null;
    try {
        QuerySpec qs = new QuerySpec();
        int idx = qs.appendClassList(ProjectViewDepartmentLink.class, true);

        SearchCondition sc = null;
        sc = new SearchCondition(ProjectViewDepartmentLink.class,
                                "roleAObjectRef.key.id",
                                SearchCondition.EQUAL,
                                project.getPersistInfo().getObjectIdentifier().getId());
        qs.appendWhere(sc, new int[]{idx});

        viewDepts = PersistenceHelper.manager.find(qs);

        Department dept = null;
        String deptName = null;
        String deptMemberOid = null;

        ProjectViewDepartmentLink link = null;
        Object obj2[] = null;
        while(viewDepts.hasMoreElements()) {
            obj2 = (Object[])viewDepts.nextElement();
            link = (ProjectViewDepartmentLink)obj2[0];
            dept = link.getDepartment();
            deptName = dept.getName();
            deptMemberOid = link.getPersistInfo().getObjectIdentifier().getStringValue();
%>
                    <tr>
<%
            if("select".equals(command)){
%>
                        <td class="tdwhiteM">
                            <input name="oid" type="checkbox" class="Checkbox" <%if(!"multi".equals(mode)){%>onClick="javascript:oneClick(this);"<%}%>
                                                <%if(map != null && map.get(deptMemberOid) != null){%>checked<%}%>
<%
                if("OutputCreatePage".equals(fromPage)) {
%>
                                            userName=''
                                            dutyName=''
                                            deptName='<%=deptName%>'
                                            email=''
                                            peopleOID=''
                                            isView='true'
                                            isDept='true'
<%
                }
%>
                                                value='<%=deptMemberOid%>' />
                        </td>
<%
            }
%>
                        <td class="tdwhiteL0" colspan='4'><font color="#brown"><%=deptName%></font></td>
                    </tr>
<%
        }
    }
    catch(Exception e) {
	Kogger.error(e);
    }

    if(result.size() == 0 && viewDepts.size() == 0) {
%>
                    <tr>
                        <td  class="tdwhiteL0" colspan='5'><%=messageService.getString("e3ps.message.ket_message", "00706") %><%--검색 결과가 없습니다.--%></td>
                    </tr>
<%
    }

%>
                </table>
                </div>
<!------------------------------------- 본문 끝 //----------------------------------------->
            </td>
        </tr>
    </table>
</form>
</body>
</html>
