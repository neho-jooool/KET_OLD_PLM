<%--
  /**
   * @(#)  selectPeopleList.jsp
   * Copyright (c) e3ps. All rights reserverd
   *
   * @version 1.00
   * @since jdk 1.4.02
   * @author Cho Sung Ok
   */
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.web.ParamUtil, e3ps.common.query.SearchUtil,e3ps.common.util.CommonUtil"%>
<%@page import="wt.fc.*,wt.query.*,wt.util.*,wt.introspection.*" %>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*,e3ps.common.web.*" %>
<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<%@include file="/jsp/common/context.jsp"%>
<%
  String target = request.getParameter("target");
  String oid = ParamUtil.getStrParameter(request.getParameter("oid"));
  String sortType = ParamUtil.getStrParameter(request.getParameter("sortType"),"false");
  int pageNo = ParamUtil.getIntParameter(request.getParameter("page"),0);
  String sessionid = ParamUtil.getStrParameter(request.getParameter("sessionid"),"");
  PeopleData pdata = null;
  String currentDutyCode = (pdata = new PeopleData((WTUser)SessionHelper.manager.getPrincipal())).dutycode;
  if(currentDutyCode==null)
      currentDutyCode = "0";

  Department dept = null;
  String departmentStr = "";
  if ( oid.equals("root") || oid.trim().equals("") ) {
    departmentStr = "";
  }
  else {
    dept = (Department)CommonUtil.getObject(oid);
    departmentStr = dept.getName();
  }

  PagingQueryResult result = null;
  try {
    if ( !sessionid.equals("") ) {
      result = PagingSessionHelper.fetchPagingSession(e3ps.common.web.PageControl.PERPAGE*(pageNo-1),e3ps.common.web.PageControl.PERPAGE,Long.parseLong(sessionid));
    } else {
      String key = ParamUtil.getStrParameter(request.getParameter("key"),"");
      String keyvalue = request.getParameter("keyvalue");
      if(keyvalue == null) keyvalue = "";

      String sortKey = ParamUtil.getStrParameter(request.getParameter("sortKey"),"");

      QuerySpec spec = new QuerySpec();
      Class peopleClass = People.class;
      int peopleClassPos = spec.addClassList(peopleClass,true);

      if ( !keyvalue.equals("") ) {
        if  ( spec.getConditionCount() > 0 ) spec.appendAnd();
        spec.appendWhere(new SearchCondition(peopleClass,key,SearchCondition.LIKE,"%"+keyvalue+"%"),new int[]{peopleClassPos});
      } else {

        if(oid.equals("") && pdata.department!=null ){
          oid = pdata.department.getPersistInfo().getObjectIdentifier().toString();
        }

        if ( !oid.equals("") && !oid.equals("root") ) {
          if  ( spec.getConditionCount() > 0 ) spec.appendAnd();
//           하위 부서도 모두 출력되게 수정 ( 2006.04.04 Choi Seunghwan )
                    java.util.ArrayList list = e3ps.common.impl.TreeHelper.manager.getAllChildList(Department.class, (e3ps.common.impl.Tree) CommonUtil.getObject(oid), new java.util.ArrayList());
                    spec.appendOpenParen();
                    for (int i = list.size() - 1; i > -1; i--)
                    {
                        Department temp = (Department) list.get(i);
                        spec.appendWhere(new SearchCondition(peopleClass, "departmentReference.key.id", "=", CommonUtil.getOIDLongValue(temp)), new int[] { peopleClassPos });
                        spec.appendOr();
                    }
                    spec.appendWhere(new SearchCondition(peopleClass, "departmentReference.key.id", "=", CommonUtil.getOIDLongValue(oid)), new int[] { peopleClassPos });
                    spec.appendCloseParen();
        }
      }

      if ( !CommonUtil.isAdmin() ) {
        if ( spec.getConditionCount() > 0 ) spec.appendAnd();
         spec.appendWhere(new SearchCondition(peopleClass,People.IS_DISABLE,SearchCondition.IS_FALSE),new int[]{peopleClassPos});
      }

      if ( !sortKey.equals("") ) {
        SearchUtil.setOrderBy(spec, peopleClass, peopleClassPos, sortKey, "sort", Boolean.getBoolean(sortType));
      } else {
        SearchUtil.setOrderBy(spec, peopleClass, peopleClassPos, People.DUTY_CODE, "sort", false);
      }
      if(!CommonUtil.isAdmin())
      {
         if(spec.getConditionCount()>0)spec.appendAnd();
         SearchCondition sc = new SearchCondition(peopleClass, "name", SearchCondition.NOT_LIKE,"%삭제됨%");
         spec.appendWhere(sc, new int[]{peopleClassPos});

      }
      SearchUtil.setOrderBy(spec, peopleClass, peopleClassPos, People.PRIORITY, "priority", true);
       result = PagingSessionHelper.openPagingSession ( 0 , e3ps.common.web.PageControl.PERPAGE, spec);
    }
  } catch ( QueryException e ) {
      Kogger.error(e);
  } catch ( WTIntrospectionException e ) {
      Kogger.error(e);
  } catch ( WTPropertyVetoException e ) {
      Kogger.error(e);
  } catch ( WTException e ) {
      Kogger.error(e);
  }

  String mode =  ParamUtil.getStrParameter(request.getParameter("mode"),"s");
  boolean isMultiSelect = false;
  if ( mode.equalsIgnoreCase("m") ) isMultiSelect = true;
  String function = ParamUtil.getStrParameter(request.getParameter("function"),"thiscolse");
  String other = ParamUtil.getStrParameter(request.getParameter("other"));
  String paramStr = "mode="+mode;
  if ( target != null && target.length() > 0 ) paramStr = paramStr +"&target="+target;
  if ( !function.trim().equals("") ) paramStr = paramStr + "&function="+function;
  if ( !other.trim().equals("") ) paramStr = paramStr + "&other="+other;

  PageControl control = new PageControl(result,pageNo,3);
  control.setHref("/plm/jsp/groupware/company/selectPeopleList.jsp");
  control.setParam(paramStr);
%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<html>
<head>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<script language="javascript">
<!--
function view(id) {
  document.selectPeopleList.action = "/plm/jsp/groupware/company/selectPeopleView.jsp?oid="+id;
  document.selectPeopleList.submit();
}

function sorting(value) {
  document.selectPeopleList.sortKey.value=value;
  document.selectPeopleList.action = "selectPeopleList.jsp?<%=paramStr%>";
  document.selectPeopleList.submit();
}


function search() {
  document.selectPeopleList.action = "selectPeopleList.jsp?<%=paramStr%>";
  document.selectPeopleList.submit();
}

function clickThis(param) {
  if ( !param.checked ) return;

  var len = <%=control.getResult().size()%>;
  var checkStr = param.value;

  var objArr = document.forms[0];
  if (len > 1) {
    for ( var i = 0 ; i < objArr.length ; i++ ) {
      if ( objArr[i].type == "checkbox" ) {
        if ( checkStr != objArr[i].value ) {
          objArr[i].checked = false;
        }
      }
    }
  }
}

function fcheck() {
  var count = 0;
  var len = <%=control.getResult().size()%>;
  if (len > 1) {
    for( i=0;i<len ;i++) {
      if( document.selectPeopleList.check[i].checked == true) count++;
    }
  } else if ( len == 1 ) {
    if( document.selectPeopleList.check.checked == true) count++;
  }

  if(count==0) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01813") %><%--선택된 항목이 없습니다--%>");
    return false;
  }
  return true;
}

function thiscolse(){
  parent.close();
}

// Modify - skyprda 2005/02/03
<%
//  String target = request.getParameter("target");
  if(target != null)
  {
%>
// opener의 target 필드에 페이지 Refresh없이 사용자를 세팅한다.
function addUserToTarget()
{
  if ( fcheck() )
  {
    var paramStr = "";
    var userOid = "";
    var userName = "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
      {
        if( document.selectPeopleList.check[i].checked == true)
        {
          userOid += document.selectPeopleList.check[i].value+"#";
          userName += document.selectPeopleList.check[i].sname+",";
        }
      }
      userOid = userOid.substring(0,userOid.length-1);
      userName = userName.substring(0,userName.length-1);
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true)
      {
        userOid = document.selectPeopleList.check.value;
        userName = document.selectPeopleList.check.sname;
      }
    }
    <%if(target!=null){%>
    parent.opener.document.forms[0].temp<%=target%>.value = userName;
    parent.opener.document.forms[0].<%=target%>.value = userOid;
    <%}%>
    thiscolse();
  }

}
// opener의 특정 table에 row를 추가하고 사용자를 세팅한다.
function addUserByNewLine(processType)
{
  if ( fcheck() )
  {
    var paramStr = "";
    var userOid = "";
    var userName = "";
    var duty = "";
    var dutycode = "";
    var dname= "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
        if( document.selectPeopleList.check[i].checked == true)
        {
          userOid = document.selectPeopleList.check[i].value;
          userName = document.selectPeopleList.check[i].sname;
          duty = document.selectPeopleList.check[i].duty;
          dutycode = document.selectPeopleList.check[i].dutycode;
          dname = document.selectPeopleList.check[i].dname;
        }
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true)
      {
        userOid = document.selectPeopleList.check.value;
        userName = document.selectPeopleList.check.sname;
        duty = document.selectPeopleList.check.duty;
        dutycode = document.selectPeopleList.check.dutycode;
        dname = document.selectPeopleList.check.dname;
      }
    }

    if(parent.opener.document.forms[0].gianUser.value == userOid)
    {
      alert('<%=messageService.getString("e3ps.message.ket_message", "02432") %><%--작성자를 추가할 수 없습니다--%>');
      return;
    }
    if(parent.opener.document.forms[0].currentUser.value == userOid)
    {
      alert('<%=messageService.getString("e3ps.message.ket_message", "02415") %><%--자신을 추가할 수 없습니다--%>');
      return;
    }
    /*
    var cdutycode = <%=(currentDutyCode!=null && currentDutyCode.length()>0)?currentDutyCode:"0"%>;
    if(cdutycode!=null && cdutycode>0)
    {
      if(dutycode.length==0 && cdutycode.length!=0)
      {
        alert('하급자를 추가할 수 없습니다.');
        return
      }
      else if(cdutycode < dutycode)
      {
        alert('하급자를 추가할 수 없습니다.');
        return;
      }
    }*/


    var userlistLen = parent.opener.document.forms[0].<%=target%>.length;
    for (var idx=0 ; idx<userlistLen ; idx++)
    {
      userlist = parent.opener.document.forms[0].<%=target%>[idx].value;
      if(userlist == userOid)
      {
        alert("<%=messageService.getString("e3ps.message.ket_message", "02282") %><%--이미 결재선에 등록되었습니다--%>");
        return;
      }
    }

    parent.opener.insertLine('<%=target%>');
    var index = parent.opener.<%=target%>Table.rows.length-2;
    parent.opener.document.forms[0].processType[index].value = processType;
    parent.opener.document.forms[0].duty[index].value = duty;
    parent.opener.document.forms[0].dname[index].value = dname;
    parent.opener.document.forms[0].temp<%=target%>[index].value = userName;
    parent.opener.document.forms[0].<%=target%>[index].value = userOid;
  }
}

// modified by SJ, Han
// use JSENG PLM EChange
// 정해진 위치에 선택된 사용자의 oid / id / user name / departname을 셋팅한다.
function addUserToTargetForEC(){
  if ( fcheck() )
  {
    var paramStr = "";
    var userOid = "";
    var userName = "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
      {
        if( document.selectPeopleList.check[i].checked == true)
        {
          userOid = document.selectPeopleList.check[i].value;
          userId = document.selectPeopleList.check[i].uid;
          userName = document.selectPeopleList.check[i].sname;
          userDname = document.selectPeopleList.check[i].dname;
        }
      }
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true)
      {
        userOid = document.selectPeopleList.check.value;
        userId = document.selectPeopleList.check.uid;
        userName = document.selectPeopleList.check.sname;
        userDname = document.selectPeopleList.check.dname;
      }
    }
    <%if(target!=null){%>
    parent.opener.document.forms[0].<%=target%>_oid.value = userOid;
    parent.opener.document.forms[0].<%=target%>_id.value = userId;
    parent.opener.document.forms[0].<%=target%>_name.value = userName;
    if(parent.opener.document.forms[0].<%=target%>_team) parent.opener.document.forms[0].<%=target%>_team.value = userDname;
    <%}%>
    thiscolse();
  }
}
<%
  }
%>
// end - skyprda
function addUserUseCreate() {
  if ( fcheck() ) {
    var paramStr = "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
        if( document.selectPeopleList.check[i].checked == true) paramStr = paramStr + document.selectPeopleList.check[i].value + "&" + document.selectPeopleList.check[i].sname + "&" + document.selectPeopleList.check[i].duty + "&" + document.selectPeopleList.check[i].dname + "&" + document.selectPeopleList.check[i].email + "/";
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true) paramStr = document.selectPeopleList.check.value + "&" + document.selectPeopleList.check.sname + "&" + document.selectPeopleList.check.duty + "&" + document.selectPeopleList.check.dname + "&" + document.selectPeopleList.check.email + "/";
    }
<%  if ( !other.equals("") ) {  %>parent.opener.<%=other%>(paramStr);<%}%>
  }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addUserCreateForProject() {
  if ( fcheck() ) {
    var paramStr = "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
        if( document.selectPeopleList.check[i].checked == true) paramStr = paramStr + document.selectPeopleList.check[i].value + "&" + document.selectPeopleList.check[i].sname + "&" + document.selectPeopleList.check[i].duty + "&" + document.selectPeopleList.check[i].dname + "&" + document.selectPeopleList.check[i].email + "//";
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true) paramStr = document.selectPeopleList.check.value + "&" + document.selectPeopleList.check.sname + "&" + document.selectPeopleList.check.duty + "&" + document.selectPeopleList.check.dname + "&" + document.selectPeopleList.check.email + "//";
    }
<%  if ( !other.equals("") ) {  %>parent.opener.<%=other%>(paramStr);<%}%>
  }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

<%if(target!=null){%>
function addUserName(){
  if(fcheck())
  {
    var paramStr = parent.opener.document.forms[0].<%=target%>.value;
    var len=<%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
        if( document.selectPeopleList.check[i].checked == true)
        {
          if(paramStr.indexOf(document.selectPeopleList.check[i].sname)<0)
            paramStr = paramStr + document.selectPeopleList.check[i].sname + ",";
        }
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true)
      {
        if(paramStr.indexOf(document.selectPeopleList.check[i].sname)<0)
          paramStr = document.selectPeopleList.check.sname + ",";
      }
    }
    parent.opener.document.forms[0].<%=target%>.value = paramStr.substring(0,paramStr.length-1);
  }//end if(fcheck())
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}
<%}%>
//사용자 추가
function addUserUseEducation() {
  if ( fcheck() ) {
    var paramStr = "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
        if( document.selectPeopleList.check[i].checked == true) paramStr = paramStr + "addUser=" + document.selectPeopleList.check[i].value + "&";
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true) paramStr = "addUser=" + document.selectPeopleList.check.value;
    }
    parent.opener.document.forms[0].method="post";
    parent.opener.document.forms[0].action = "/plm/jsp/manage/edu/companyeducationupdate.jsp?oid="+parent.opener.document.forms[0].oid.value+"&" +paramStr;
    parent.opener.document.forms[0].submit();
  }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addUserUseManager() {
  if ( fcheck() ) {
    var paramStr = "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
        if( document.selectPeopleList.check[i].checked == true) paramStr = paramStr + "<%=other%>=" + document.selectPeopleList.check[i].value + "&";
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true) paramStr = "<%=other%>=" + document.selectPeopleList.check.value;
    }

    parent.opener.document.forms[0].method="post";
    parent.opener.document.forms[0].action = "/plm/jsp/project/projectManagerView.jsp?"+paramStr;
    parent.opener.document.forms[0].submit();
  }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addMemberUseProject() {
  if ( fcheck() ) {
    var paramStr = "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
        if( document.selectPeopleList.check[i].checked == true) paramStr = paramStr + "addMember=" + document.selectPeopleList.check[i].value + "&";
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true) paramStr = "addMember=" + document.selectPeopleList.check.value;
    }

    parent.opener.document.forms[0].method="post";
    parent.opener.document.forms[0].action = "/plm/jsp/project/projectView.jsp?"+paramStr;
    parent.opener.document.forms[0].submit();
  }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function modifyPM()
{
  if(fcheck())
  {
    var paramStr ="";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
        if( document.selectPeopleList.check[i].checked == true) paramStr = document.selectPeopleList.check[i].value;
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true) paramStr = document.selectPeopleList.check.value;
    }
    parent.opener.document.forms[0].command.value="changePM";
    parent.opener.document.forms[0].pmoid.value = paramStr;
    parent.opener.document.forms[0].action = "/plm/servlet/e3ps.project.servlet.ManageProjectServlet";
    parent.opener.document.forms[0].submit();
  }
<%
  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addMemberUseTemplateProject() {
  if ( fcheck() ) {
    var paramStr = "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
        if( document.selectPeopleList.check[i].checked == true) paramStr = paramStr + "addMember=" + document.selectPeopleList.check[i].value + "&";
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true) paramStr = "addMember=" + document.selectPeopleList.check.value;
    }

    parent.opener.document.forms[0].method="post";
    parent.opener.document.forms[0].action = "/plm/jsp/project/template/templateProjectView.jsp?"+paramStr;
    parent.opener.document.forms[0].submit();
  }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addUserDept(){
  if(fcheck()) {

    var paramStr = "";
    var userOid = "";
    var userName = "";
    var userDoid = "";
    var userDname = "";
    var len = <%=control.getResult().size()%>;
    if (len > 1) {
      for( i=0;i<len ;i++)
      {
        if( document.selectPeopleList.check[i].checked == true)
        {
          userOid = document.selectPeopleList.check[i].value;
          userId = document.selectPeopleList.check[i].uid;
          userName = document.selectPeopleList.check[i].sname;
          userDoid = document.selectPeopleList.check[i].doid;
          userDname = document.selectPeopleList.check[i].dname;
        }
      }
    } else if ( len == 1 ) {
      if( document.selectPeopleList.check.checked == true)
      {
        userOid = document.selectPeopleList.check.value;
        userId = document.selectPeopleList.check.uid;
        userName = document.selectPeopleList.check.sname;
        userDoid = document.selectPeopleList.check.doid;
        userDname = document.selectPeopleList.check.dname;
      }
    }
    <%if(target!=null){%>
    //parent.opener.document.forms[0].creator.value = userOid;
    //parent.opener.document.forms[0].tempcreator.value = userName;
    parent.opener.document.forms[0].dept.value = userDoid;
    parent.opener.document.forms[0].tempdept.value = userDname;
    <%}%>
    thiscolse();
  }
}


  function viewTodo(oid){
    var url = "/plm/jsp/project/ListMyTask.jsp?wtUser="+oid;
    openOtherName(url,"ViewTemplate","800","600","status=1,scrollbars=yes,resizable=1");
  }


//-->
</script>
<SCRIPT language="JavaScript" src="/plm/portal/js/checkbox2.js"></SCRIPT>
<SCRIPT language="JavaScript" src="/plm/portal/js/tableScriptChild.js"></SCRIPT>
</head>
<body topmargin="0" leftmargin="0" onload='document.forms[0].keyvalue.focus()'>
<form method="post" name="selectPeopleList">
<input type="hidden" name="sortKey">
<input type="hidden" name="sortType" value="<%=sortType%>">
<table border="0" cellspacing="0" cellpadding="0" class="tab_popup05">
  <tr>
    <td>
      <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="popBox">
        <tr>
          <td class="boxTLeft"></td>
          <td class="boxTCenter">

          </td>
          <td class="boxTRight"></td>
        </tr>
        <tr>
          <td class="boxLeft"></td>
          <td>
            <!------------------------------ 본문 시작 //------------------------------>
            <table width=100% align=center border=0>
              <tr>
                <td>
                  <table border=0 cellpadding=0 cellspacing=0 >
                    <tr>
                      <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "01541") %><%--부서 및 사원--%><b>[<%= departmentStr %>]</b></td>
                    </tr>
                  </table>
                </td>
                <td align="right">
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class=fixLeft></td>
                      <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onClick="Javascript:thiscolse()"></td>
                      <td class=fixRight></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
                <!--button//-->
            <table align="center" border="0" width="100%">
              <tr>
                <input type="hidden" name="key" value="<%=People.NAME%>">
                <td align="left">
                  <TABLE border="0" cellpadding="0" cellspacing="0">
                    <TR>
                      <TD><input type="text" name="keyvalue" size="8" class="txt_field" style="ime-mode:active;"></TD>
                      <TD>&nbsp;</TD>
                      <TD><input type=button value="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" onClick="Javascript:search()" class="s_font"></TD>
                    </TR>
                  </TABLE>
                </td>
                <td align="right">
            <%
                if( "addUserByNewLine".equals(function) )
                {
            %>  <input type=button onclick="javascript:<%=function%>('결재');" value='<%=messageService.getString("e3ps.message.ket_message", "00755") %><%--결재--%>' class="s_font">
            <%--  <input type=button onclick="javascript:<%=function%>('협조');" value='협조' id=button2> --%>
              <input type=button onclick="javascript:<%=function%>('합의');" value='<%=messageService.getString("e3ps.message.ket_message", "03156") %><%--합의--%>' class="s_font">
            <%
                }
                else if("addSelectedItem".equals(function))
                {
            %>  <input type=button onclick="addSelectedItem(document.forms[0].check, 'userTable')" value="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" class="s_font">
            <%  }
                else
                {
            %>      <input type=button onclick="javascript:<%=function%>();" value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>' class="s_font">
            <%  }%>
                </td>
              </tr>
            </table>
                <!--button//-->
            <table border="0" cellpadding="0" cellspacing="0" class="tab_popup05">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" class="tab_popup05">
              <tr>
                <td  class="tab_btm1"></td>
              </tr>
            </table>
<!---리스트------->
            <table width="100%" cellspacing="0" cellpadding="0" border="0" align=center>
              <tr>
                <td class="tdblueM" width="20">
                  <%if(isMultiSelect){%>
                  <input type="checkbox" name="checkboxAll" onClick="javascript:selectAll(this, document.forms[0].check)">
                  <%}else{%>&nbsp;<%}%>
                </td>
                <td class="tdblueM"><a href="javascript:sorting('<%=People.NAME%>');"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></A></td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                <td class="tdblueM"><a href="javascript:sorting('<%=People.DUTY_CODE%>');"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></A></td>
                <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "03122") %><%--프로젝트이력--%></td>

              </tr>
            <%
              while ( control.getResult().hasMoreElements() )
              {
                Object[] obj = (Object[])control.getResult().nextElement();
                PeopleData data = new PeopleData(obj[0]);
                String wtuserOID = CommonUtil.getOIDString(data.wtuser);
                String peopleOID = CommonUtil.getOIDString(data.people);
                String deptOID = CommonUtil.getOIDString(data.department);
            %>
              <SCRIPT LANGUAGE="JavaScript">
                addOID('<%=wtuserOID%>');
                addTableValue('<%=data.name%>');
                addTableValue('<%=data.departmentName%>');
                addTableValue('<%=data.duty%>');
              </SCRIPT>
              <tr onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                <td class="tdwhiteM" width="20"><input type="checkbox" name="check" value="<%=wtuserOID%>" poid="<%=peopleOID%>" doid="<%=deptOID%>" email="<%=data.email%>" sname="<%=data.name%>" dname="<%=data.departmentName%>" duty="<%=data.duty%>" uid="<%=data.id%>" dutycode="<%=data.dutycode %>" <%if(!isMultiSelect)out.print("onclick='javascript:clickThis(this)'");%>></td>
                <td class="tdwhiteM"><%=data.name%></td>
                <td class="tdwhiteM"><%= data.departmentName %></td>
                <td class="tdwhiteM">&nbsp;<%= data.duty %></td>
                <td class="tdwhiteM">&nbsp;
                  <a href="javascript:viewTodo('<%=wtuserOID %>');">
                  <img src="/plm/portal/images/img_default/button/but2_list.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "03122") %><%--프로젝트이력--%>" width="21" height="20" border="0">
                  </a>
                </td>
              </tr>
            <%  }  %>
            <%  if(control.getTotalCount() == 0) {  %>
              <tr>
              <td colspan="5" height="25" class="tdwhiteL"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
                </tr>
            <%  }  %>
            </table>
            <!-- 페이지 설정 시작 //-->
            <jsp:include page="/jsp/common/pageControl.jsp" flush="false">
              <jsp:param name="totalCount" value="<%=control.getTotalCount()%>"/>
              <jsp:param name="totalPage" value="<%=control.getTotalPage()%>"/>
              <jsp:param name="currentPage" value="<%=control.getCurrentPage()%>"/>
              <jsp:param name="startPage" value="<%=control.getStartPage()%>"/>
              <jsp:param name="endPage" value="<%=control.getEndPage()%>"/>

              <jsp:param name="href" value="<%=control.getHref()%>"/>
              <jsp:param name="param" value="<%=control.getParam()%>"/>
              <jsp:param name="sessionid" value="<%=control.getSessionId()%>"/>
              <jsp:param name="isPost" value="<%=control.isPostMethod()%>"/>
              <jsp:param name="type" value="simple"/>
            </jsp:include>
            <!-- 페이지 설정 끝 //-->

          </td>
          <td class="boxRight"></td>
        </tr>
        <tr>
          <td class="boxBLeft"></td>
          <td valign="bottom" class="boxBCenter"></td>
          <td class="boxBRight"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
