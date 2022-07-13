<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.web.ParamUtil, e3ps.common.query.SearchUtil,e3ps.common.util.CommonUtil"%>
<%@page import="wt.fc.*,wt.query.*,wt.util.*,wt.introspection.*, wt.org.WTUser" %>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*,e3ps.common.web.*" %>
<%@page import="wt.session.SessionHelper" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

  String aUser = StringUtil.checkNull(request.getParameter("keyvalue"));

  QueryResult result = new QueryResult();

  if(aUser!=null || !aUser.equals("")){
    QuerySpec spec = new QuerySpec();
    Class peopleClass = People.class;

    int peopleClassPos = spec.addClassList(peopleClass,true);
    if ( !aUser.equals("") ) {
      if  ( spec.getConditionCount() > 0 ) spec.appendAnd();
      // Multi 검색
      KETQueryUtil.setQuerySpecForMultiSearch(spec, peopleClass, peopleClassPos, "name", aUser, true);
    }
    if ( !CommonUtil.isAdmin() ) {
      if ( spec.getConditionCount() > 0 ) spec.appendAnd();
       spec.appendWhere(new SearchCondition(peopleClass,People.IS_DISABLE,SearchCondition.IS_FALSE),new int[]{peopleClassPos});
    }
    if(!CommonUtil.isAdmin())
    {
       if(spec.getConditionCount()>0)spec.appendAnd();
       SearchCondition sc = new SearchCondition(peopleClass, "name", SearchCondition.NOT_LIKE,"%삭제됨%");
       spec.appendWhere(sc, new int[]{peopleClassPos});
    }

    result = PersistenceHelper.manager.find(spec);
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var selMember = '';
var i;
var j;

function selectMember(memberID, obj){
  var strArray = selMember.split(',');
  var scope = false;
  for(i=0; i<strArray.length; i++){
    if(strArray[i] == memberID) {
      selMember = selMember.replace(memberID+',','');
      for(j=0; j<obj.childNodes.length; j++){
        obj.childNodes[j].className="tdwhiteL";
      }
      scope = true;
    }
  }
  if(scope==false) {
    selMember = selMember + memberID + ',';
    for(j=0; j<obj.childNodes.length; j++){
        obj.childNodes[j].className="tdgrayL";
    }
  }

  parent.addUser(selMember);
}

</script>
</head>
<form>
<table width="300" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td valign="top">
   <table border="0" cellpadding="0" cellspacing="0">
   <%
       if((!aUser.equals(""))&&(result.size()!=0)){
         while(result.hasMoreElements()){
           Object[] obj = (Object[])result.nextElement();
           PeopleData data = new PeopleData(obj[0]);
   %>
   <tr style=cursor:hand; align="left" onClick="javascript:selectMember('<%=data.id %>',this);">
     <td width="90" class="tdwhiteL" nowrap><%=data.departmentName %></td>
     <td class="tdwhiteL" nowrap><%=data.name %></td>
     <td width="70" class="tdwhiteL" nowrap><%=data.duty %></td>
   </tr>
    <%
         }
       }else{
     %>
     <tr align="center">
      <td colspan="3" width="185"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
    </tr>
     <%} %>
     </table>
  </td>
   </tr>
  </table>
</body>
</form>
</html>
