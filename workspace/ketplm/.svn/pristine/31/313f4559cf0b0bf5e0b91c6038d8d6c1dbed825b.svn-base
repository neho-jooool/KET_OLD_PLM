<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
        e3ps.dms.entity.KETDocumentCategory,
        java.util.ArrayList"%>


<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<html>
<head>
<link rel="stylesheet" href="../../portal/css/drtree.css" type="text/css">
<script type="text/javascript" src="/plm/portal/js/drtree.js"></script>
<script language="JavaScript">
<!--
function cleanSt( obname ) {
//트리에 입력될 값에서 특수분자를 체크하여 공백처리 한다.
    var specialChars='~`!@#$%%^&*-=+\|[{]};:\',<.>/?';
    var str=obname;
    var i, j;
    if (str == '') {
      alert('No Input');
    return false;
    }
    for (i = 0; i < str.length; i++) {
      for (j = 0; j < specialChars.length; j++) {
        if (str.charAt(i) == specialChars.charAt(j))
        str = str.replace(str.charAt(i), " ");
      }
    }
    return str;
  }

// -->
</script>

<BODY>
<form name=tree method=post>

<DIV id="scrollbox">
<table border="0" cellspacing="0" cellpadding="0" width="10%" height="5%">
<tr>
    <td valign="top" align="right">
    <input type=button value='<%=messageService.getString("e3ps.message.ket_message", "01768") %><%--새로고침--%>' onclick='location.reload()' id='innerbutton'>
    </td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%" height="95%">
  <tr>
    <td valign="top" align="left">
      <table border=0>
        <tr>
          <td>
            <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0>
              <script type="text/javascript">

              var obname;
              d = new dTree("d");

              <%
                  ArrayList nodeList = KETDmsHelper.service.selectDocCategoryForTree();
                  Object[] obj = (Object[])nodeList.get(0);

                String parent = "";
                String my = (String)obj[1];
                String myname = (String)obj[2];

              %>
                d.add("<%=my%>",-1,"<%=my%>","/plm/jsp/dms/ViewDocuCate.jsp?oid=<%=my%>","<%=my%>","main");

              <%
                for(int i = 1; i < nodeList.size(); i++)
                {
                  obj = (Object[])nodeList.get(i);

                  parent = (String)obj[0];
                  my = (String)obj[1];
                  myname = (String)obj[2];
              %>
                  obname = "<%=myname%>";
                  obname = obname;

                  d.add("<%=my%>","<%=parent%>",obname,"/plm/jsp/dms/ViewDocuCate.jsp?oid=<%=my%>",obname,"main");

              <%
                  }
              %>

              document.write(d);

            </script>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</DIV>


</form>
</body>
</html>
