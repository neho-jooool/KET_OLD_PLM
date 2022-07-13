<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.issue.Issue"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.beans.ProjectUserData"%>
<%@page import="e3ps.project.ProjectMemberLink"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="e3ps.groupware.company.People"%>
<%@page import="e3ps.groupware.company.PeopleData"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%

// 0: PM 외 ALL
// 1: PL 외 ALL
// 2: Role Member 외 ALL
// 3: Etc Member 외 ALL
// 4: Only View 외 ALL
// 5: All

    //selectType    =>   multi : one
    String memberType = request.getParameter("memberType");
    String project = request.getParameter("projectOid");

    int type = 0;

    if(memberType == null){
        type = 5;
    }else{
        type = Integer.parseInt(memberType);
    }
    //out.println("type ==> " + type );

    //String project ="e3ps.project.E3PSProject:156782";


    String oid = request.getParameter("oid");
    Issue issue = (Issue)CommonUtil.getObject(oid);
    //TemplateProject project = null;

    QuerySpec qs = null;
    QueryResult qr = null;

    try {
        qs = new QuerySpec();
        Class peopleProjectLinkClass = ProjectMemberLink.class;
        int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);
        qs.appendWhere(new SearchCondition(peopleProjectLinkClass,ProjectMemberLink.PJT_MEMBER_TYPE,
                SearchCondition.NOT_EQUAL,type), new int[] {peopleProjectLinkClassPos} );
        qs.appendAnd();
        long oidValue = CommonUtil.getOIDLongValue(project);
        qs.appendWhere(new SearchCondition(peopleProjectLinkClass,"roleAObjectRef.key.id",
                SearchCondition.EQUAL,oidValue), new int[] {peopleProjectLinkClassPos});

        SearchUtil.setOrderBy(qs,peopleProjectLinkClass,peopleProjectLinkClassPos,ProjectMemberLink.PJT_MEMBER_TYPE,"pjtMemberType",false);

        // out.println(qs);
        qr = PersistenceHelper.manager.find(qs);



    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");

    if(mode == null) {
        mode = "one";
    }

    if(invokeMethod == null) {
        invokeMethod = "";
    }


%>

<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>

<Script>

        function viewPeople(v){
            var str="/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+v;
            newWin = window.open(str,"viewPeople", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=550,height=400,resizable=yes,mebar=no,left=40,top=65");
            newWin.focus();
        }

        function seletPeople() {
            if(!fcheck()) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "01813") %><%--선택된 항목이 없습니다--%>");
                return;
            }
            //alert("메롱.....");

            var arr = new Array();
            var subArr;
            var index = 0;
            chkobj = document.forms[0].check;

            var len = chkobj.length;
            if (len) {
                for( var i=0;i<len ;i++) {
                    if( chkobj[i].checked == true) {
                        subArr = new Array();
                        subArr[0] = chkobj[i].value;//wtuser oid
                        subArr[1] = chkobj[i].poid;//people oid
                        subArr[2] = chkobj[i].doid;//dept oid
                        subArr[3] = chkobj[i].uid;//uid
                        subArr[4] = chkobj[i].sname;//name
                        subArr[5] = chkobj[i].dname;//dept name
                        subArr[6] = chkobj[i].duty;//duty
                        subArr[7] = chkobj[i].dutycode;//duty code
                        subArr[8] = chkobj[i].email;//email

                        arr[index++] = subArr;
                    }
                }
            } else {
                if( chkobj.checked == true) {
                    subArr = new Array();
                    subArr[0] = chkobj.value;//wtuser oid
                    subArr[1] = chkobj.poid;//people oid
                    subArr[2] = chkobj.doid;//dept oid
                    subArr[3] = chkobj.uid;//uid
                    subArr[4] = chkobj.sname;//name
                    subArr[5] = chkobj.dname;//dept name
                    subArr[6] = chkobj.duty;//duty
                    subArr[7] = chkobj.dutycode;//duty code
                    subArr[8] = chkobj.email;//email

                    arr[index++] = subArr;
                }
            }
            subArr = new Array();

        <%  if(invokeMethod.length() == 0) {  %>
            //modal dialog
            selectModalDialog(arr);
        <%  } else {  %>
            //open window
            selectOpenWindow(arr);
        <%  }  %>
        }

        function selectModalDialog(arrObj) {
            window.returnValue= arrObj;
            window.close();
        }

        <%  if(invokeMethod.length() > 0) {  %>

        function selectOpenWindow(arrObj) {
            //...이하 원하는 스크립트를 만들어서 작업...
            if(opener) {
                if(opener.<%=invokeMethod%>) {
                    opener.<%=invokeMethod%>(arrObj);
                }
            }

            if(parent.opener) {
                if(parent.opener.<%=invokeMethod%>) {
                    parent.opener.<%=invokeMethod%>(arrObj);
                }
            }
            thiscolse();
        }

        <%  }  %>

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


        function clickThis(param) {
            if ( !param.checked ) {
                return;
            }

            var len = document.forms[0].check.length;
            var checkStr = param.value;

            var objArr = document.forms[0].check;
            if (len) {
                for ( var i = 0 ; i < objArr.length ; i++ ) {
                    if ( objArr[i].checked == true
                        && checkStr != objArr[i].value) {
                        objArr[i].checked = false;
                    }
                }
            }
        }

        function fcheck() {
            chkobj = document.forms[0].check;
            if(chkobj == null) {
                return false;
            }

            var len = chkobj.length;
            if (len) {
                for( var i=0;i<len ;i++) {
                    if( chkobj[i].checked == true) {
                        return true;
                    }
                }
            } else {
                if( chkobj.checked == true) {
                    return true;
                }
            }

            return false;
        }



</Script>


<title><%=messageService.getString("e3ps.message.ket_message", "02307") %><%--이슈 보기--%></title>
</head>
<body>

<form>

    <table border="0" cellpadding="0" cellspacing="0" class="popBox">
        <tr>
            <td class="boxTLeft"></td>
          <td class="boxTCenter"></td>
            <td class="boxTRight"></td>
        </tr>
        <tr>
            <td class="boxLeft"></td>
            <td>

<!-------------------------------------- 상단버튼 시작 //-------------------------------------->
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:seletPeople();">
                        <img src="/plm/portal/images/img_default/button/board_btn_choice.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" width="62" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a href="javascript:self.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                        <!--table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class=fixLeft></td>
                                <td ><input type=button class="btnTras" value=선택" onClick="javascript:seletPeople()"></td>
                                <td class=fixRight></td>
                                <td>&nbsp;</td>
                                <td class=fixLeft></td>
                                <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onClick="javascript:self.close()"></td>
                                <td class=fixRight></td>
                            </tr>
                        </table-->
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
                <tr>
                    <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "00318") %><%--Member 보기--%> </td>
                    <td align="right">&nbsp;
                    </td>
                </tr>
            </table>
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
            <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
                <tr>
                    <td class="tdblueM">&nbsp;
                    <%if(mode.equals("multi")) {%>
                    <input type="checkbox" name="checkboxAll" value="all"  onClick="javascript:selectAll();">
                    <%}%>
                    </td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02723") %><%--직책--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00412") %><%--Project권한--%></td>
                    <td class="tdblueM">e-mail</td>
                </tr>
<%
    if(qr != null){
        while(qr.hasMoreElements()){
            Object[] o = (Object[])qr.nextElement();
            ProjectUserData data1 = null;
            data1 = new ProjectUserData((ProjectMemberLink)o[0]);
            People p =  (People)CommonUtil.getObject(data1.peopleOID);

            PeopleData data = new PeopleData(p);
            String wtuserOID = CommonUtil.getOIDString(data.wtuser);
            String peopleOID = CommonUtil.getOIDString(data.people);
            String deptOID = CommonUtil.getOIDString(data.department);
%>

                <tr>
                    <td class="tdwhiteM" width="20"><input type="checkbox" name="check"
                    value="<%=wtuserOID%>"
                    poid="<%=peopleOID%>"
                    doid="<%=deptOID%>"
                    email="<%=data.email%>"
                    sname="<%=data.name%>"
                    dname="<%=data.departmentName%>"
                    duty="<%=data.duty%>"
                    uid="<%=data.id%>"
                    dutycode="<%=data.dutycode %>"
                    <%if(!"multi".equals(mode)) out.print("onclick='javascript:oneClick(this)'");%>></td>

                    <!--  <td class="tdwhiteM" >&nbsp;
                    <input name="oid" type="checkbox" class="check"
                    value="<%=data.wtuserOID%>"
                    poid="<%=data.peopleOID%>"
                    email="<%=data.email%>"
                    sname="<%=data.name%>"
                    <%if(!"multi".equals(mode)){%> onClick="clickThis(this)"<%}%>  >  </td>  -->

                    <td class="tdwhiteM" >&nbsp;<a href="JavaScript:viewPeople('<%=data.peopleOID%>')"><%=data.name%></a></td>
                    <td class="tdwhiteM">&nbsp;<%=data.departmentName%></td>
                    <td class="tdwhiteM">&nbsp;<%=data.duty%>&nbsp;</td>
                    <td class="tdwhiteM">&nbsp;<%=data1.projectDuty%></td>
                    <td class="tdwhiteM">&nbsp;<%=data.email%></td>
                </tr>
<%
        }
    }
    } catch (Exception e) {
	Kogger.error(e);
    }

%>

            </table>


            <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>

<!------------------------------------- 본문 끝 //----------------------------------------->
            </td>
            <td class="boxRight"></td>
        </tr>
        <tr>
            <td class="boxBLeft"></td>
            <td valign="bottom" class="boxBCenter"></td>
            <td class="boxBRight"></td>
        </tr>
    </table>
<!-- 본문외관테두리 끝 //-->

</form>
</body>
</html>
