<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil,
        e3ps.common.content.E3PSContentHelper,
        e3ps.doc.PATDocument,
        e3ps.doc.PATDocumentContent,
        e3ps.doc.PATDocContentLink,
        wt.fc.PersistenceHelper,
        wt.fc.QueryResult,
        wt.content.ContentHolder,
        wt.content.ApplicationData" %>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<%@include file="/jsp/common/context.jsp"%>

<%
  String oid = request.getParameter("oid");
  String contentoid = request.getParameter("contentoid");
  Kogger.debug("deleteContents", null, oid, "deleteContents.jsp>>>oid!!!");
  Kogger.debug("deleteContents", null, contentoid, "deleteContents.jsp>>>contentoid!!!");

  ApplicationData appData = null;
  Object obj = (Object)CommonUtil.getObject(oid);
  Object obj1 = (Object)CommonUtil.getObject(contentoid);
  if(obj1 instanceof ApplicationData)
      appData = (ApplicationData)CommonUtil.getObject(contentoid);
  else
      Kogger.debug("deleteContents", null, null, "contentoid is ApplicationData OID None!!!");

  try
  {
      if(obj != null && appData != null)
      {
          if(obj instanceof PATDocumentContent)
          {
//                E3PSContentHelper.service.delete((ContentHolder)obj);
//                QueryResult qr = PersistenceHelper.manager.navigate((PATDocumentContent)obj, "patDoc", PATDocContentLink.class);
//                while(qr.hasMoreElements())
//                {
//                    PATDocument prjDoc = (PATDocument)qr.nextElement();
//                    E3PSContentHelper.service.delete((ContentHolder)prjDoc);
//                }
        PersistenceHelper.manager.delete((PATDocumentContent)obj);
        Kogger.debug("deleteContents", null, null, "Content Delete(PATDocument Object)!!!");        
          }
          else
          {
          E3PSContentHelper.service.delete((ContentHolder)obj, appData);
          Kogger.debug("deleteContents", null, null, "Content Delete(PATDocument Object None)!!!");        
          }
%>
      <script language="JavaScript">
        alert("<%=messageService.getString("e3ps.message.ket_message", "02798") %><%--첨부파일이 삭제되었습니다--%>");
      </script>
<%
      }
      else
      {
%>
      <script language="JavaScript">
        alert("<%=messageService.getString("e3ps.message.ket_message", "02799") %><%--첨부파일이 삭제되지 않았습니다--%>");
      </script>
<%
      }
  }
  catch(Exception e)
  {
      Kogger.error(e);
  }
%>

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02797") %><%--첨부파일 삭제중--%></title>
</head>

<body onLoad="javascript:goPage()">
<form method="post">
</form>
</body>
</html>
<script language="JavaScript">
<!--
function goPage() {
  opener.document.forms[0].submit();
  self.close();
}
//-->
</script>
