<%@page import="e3ps.common.util.KETParamMapUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="e3ps.groupware.board.BoardComment"%>
<%@page import="e3ps.groupware.board.Board"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="e3ps.groupware.board.beans.BoardHelper"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.groupware.board.beans.HelpBoardUtil"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
  FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
  KETParamMapUtil params = KETParamMapUtil.getMap(uploader);
  
  Kogger.debug("ActionBoard", null, null, "params=" + params);

  String cmd = (params.getString("cmd")!=null)?params.getString("cmd"):request.getParameter("cmd");
  String oid = (params.getString("oid")!=null)?params.getString("oid"):request.getParameter("oid");
  String from = (params.getString("from")!=null)?params.getString("from"):request.getParameter("from");

  Kogger.debug("=====> ActionBoard: cmd=" + cmd + ", from=" + from);

  try{
    String redirPath = null;
    if(cmd != null && cmd.equals("save")){
        oid = BoardHelper.manager.create(params, uploader.getFiles());

        redirPath = "/plm/jsp/groupware/help/faq/ViewBoard.jsp?oid=" + oid/*+ "&from=" + from*/; // from 끊어야.
    }
    else if(cmd != null && cmd.equals("delete")){
        BoardHelper.manager.delete(oid);
        redirPath ="";
    }
    else if(cmd != null && cmd.equals("modify")){
        params.put("oid" , oid);

        oid = BoardHelper.manager.modify(params , uploader.getFiles());

        redirPath = "/plm/jsp/groupware/help/faq/ViewBoard.jsp?oid=" + oid/*+ "&from=" + from*/; // from 끊어야.

    }else if(cmd != null && cmd.trim().length() > 0 && cmd.equals("commentCreate")){
        ReferenceFactory f = new ReferenceFactory();
        Board b = (Board) f.getReference(oid).getObject();
        BoardComment com = BoardComment.newBoardComment();
        String comment = request.getParameter("comment");
        if(comment != null && comment.length() > 0 ){
            com.setContents(comment);
            com.setBoard(b);
            com.setOwner(SessionHelper.manager.getPrincipalReference());
            PersistenceHelper.manager.save(com);
//            msg = "성공적으로 등록되었습니다.";
        }

    }else if(cmd != null && cmd.trim().length() > 0 && cmd.equals("commentDelete")){
        String coid = request.getParameter("coid");

        ReferenceFactory f = new ReferenceFactory();
        BoardComment com = (BoardComment) f.getReference(coid).getObject();
        PersistenceHelper.manager.delete(com);
//        msg = "성공적으로 삭제되었습니다.";
    }

    if (redirPath == null) redirPath = "/plm/jsp/groupware/help/faq/SearchBoard.jsp?from=" + from;
%>
<%
    if ("main".equals(from)) {
      try {
        response.setContentType("text/html;charset=UTF-8");
        String rtn_msg = "";
        rtn_msg = "\n <script language=\"javascript\">"
                + "\n   opener.location.reload();"
                + "\n   window.close();"
                + "\n </script>";
        out.println(rtn_msg);
      } catch (Exception e) {
	  Kogger.error(e);
      }
    }
    else {
%>
<body onLoad="javascript:gotoMenu('<%=redirPath %>','자유게시판')">
<form name=utilForm method="post">
<input type=hidden name=menu>
<input type=hidden name=module value="workspace">
<input type=hidden name=subMenu value="menu">
<input type=hidden name=cmd value='<%=cmd%>'>
<input type=hidden name=oid value='<%=oid%>'>
</form>
</body>
</html>
<script language="JavaScript">
<!--
    function gotoMenu(a,m) {
        var form01 = document.forms[0];
        if(form01.cmd.value == 'commentCreate' || form01.cmd.value == 'commentDelete'){
            form01.action = '/plm/jsp/groupware/help/faq/ViewListBoard.jsp?from=<%=from %>';
            form01.menu.value = m;
            form01.submit();
        }else {
            form01.action = a;
            form01.menu.value = m;
            form01.submit();
        }
    }
//-->
</script>
</form>
<%
  }
%>

<%
}
  catch(Exception ex){
    throw ex;
}
  finally{
//    if(req!=null)req.removeTempFiles();
}
%>