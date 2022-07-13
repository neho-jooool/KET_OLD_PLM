<%@page contentType="text/html;charset=UTF-8" errorPage="ErrorPage.jsp" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<% request.setCharacterEncoding("Euc-kr"); %>
<%

//  String savePath="c:/ptc/plm/codebase/extcore/dsec/upload/dms/img";   // 저장할 디렉토리 (절대경로)
  String savePath="/usr/plm/plm/codebase/extcore/dsec/upload/dms/img";   // 저장할 디렉토리 (절대경로)
  String fileName = "";
  String border = "";
  String i_w1 = "";
  String i_h1 = "";

  int sizeLimit = 10 * 1024 * 1024 ;      // 파일업로드 용량 제한.. 10Mb
  try{

    MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit,"euc-kr",new DefaultFileRenamePolicy());
    fileName= multi.getFilesystemName("userFile");
    String originFileName = multi.getOriginalFileName("userFile");
    border = multi.getParameter("border");
    i_w1 = multi.getParameter("width");
    i_h1 = multi.getParameter("height");

    if(fileName == null) {
      out.print(messageService.getString("e3ps.message.ket_message", "02963")/*파일이 업로드 되지 않았습니다*/);
    } else {
//      fileName=new String(fileName.getBytes("ISO-8859-1"),"euc-kr");
      fileName = java.net.URLEncoder.encode(fileName);
    }
  } catch(Exception e) {
         out.print(e.getMessage());
  }

%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE><%=messageService.getString("e3ps.message.ket_message", "02293") %><%--이미지 저장 중--%></TITLE>
<LINK href="/plm/extcore/dsec/css/style.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript">
<!--
  var query = "<img src='http://dsplm.dsec.co.kr/plm/extcore/dsec/upload/dms/img/<%=fileName%>' border='";
  query = query + "<%=border%>' width='<%=i_w1%>' height='<%=i_h1%>'>";
  opener.easyUtil._editor.innerHTML(query);
  self.close();

// -->
</SCRIPT>
</HEAD>

<BODY>
</BODY>

</HTML>


