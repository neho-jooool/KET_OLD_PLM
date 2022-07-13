<%@ page contentType="text/html;charset=UTF-8"%>
<%
String leftSrc = request.getParameter("leftSrc");
String contSrc = request.getParameter("contSrc");
String oid = request.getParameter("oid");

if(leftSrc == null) {
    leftSrc = "/plm/portal/common/project_submenu.jsp";
}

if(contSrc == null) {
    contSrc = "main.jsp";
}

if(oid != null){

    Object project = CommonUtil.getObject(oid);

    leftSrc = "/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=" + oid;

    if(project instanceof ReviewProject){
        contSrc = "/plm/jsp/project/ReviewProjectView.jsp?oid=" + oid;
    }else if(project instanceof ProductProject){
        contSrc = "/plm/jsp/project/ProjectView.jsp?oid=" + oid;
    }else if(project instanceof MoldProject){
        contSrc = "/plm/jsp/project/MoldProjectView.jsp?oid=" + oid;
    }else if(project instanceof E3PSTask){
        contSrc = "/plm/jsp/project/TaskView.jsp?oid=" + oid;
    }else{
        contSrc = "main.jsp";
    }

}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>:: PLM Portal ::</title>

<!-- Fasoo DRM -->
<!-- script language="javascript">
 var Agent = navigator.userAgent;
 Agent = Agent.toLowerCase();

 if (Agent.indexOf("nt 6.") == -1)
 {
    //alert("ok");
  //Non-Vista
  document.writeln("<object id= cmhver classid='clsid:43C01137-78B1-4C7D-8522-44B4D0A96E4B' style='display:none;' ");
  document.writeln("codebase='./Resources/xp/fclient(v1.6.34.25)_20090114_blue3725_In.cab#version=1,6,34,25'></object>");
 } else {//Vista
  document.writeln("<object id= cmhver classid='clsid:43C01137-78B1-4C7D-8522-44B4D0A96E4B' style='display:none;' ");
  document.writeln("codebase='./Resources/vista/fclient(v1.6.34.25)_20090114_blue3725_In_Vista.cab#version=1,6,34,25'></object>");
 }
</script-->
<script>

function movePaage(leftSrc, contSrc) {
    var cont = document.getElementById("contentFrame");
    cont.src = "/plm/portal/content.jsp?leftSrc=" + leftSrc + "&contSrc=" + contSrc;

    //var menuObj = document.getElementById("bottomLeftFrame");
    //menuObj.src = leftSrc;
    //var mainObj = document.getElementById("contName");
    //mainObj.src = contSrc;
}

function gotoMainPage() {
    var cont = document.getElementById("contentFrame");
    cont.src = "content.jsp?leftSrc=/plm/portal/common/main_submenu.jsp&contSrc=main.jsp";

    //var menuObj = document.getElementById("bottomLeftFrame");
    //menuObj.src = '/plm/portal/common/main_submenu.jsp';
    //var mainObj = document.getElementById("bottomRightFrame");
    //mainObj.src = 'main.jsp';
}

function viewContentPage(leftSrc, contSrc) {
    var cont = document.getElementById("contentFrame");
    cont.src = "/plm/portal/viewContent.jsp?leftSrc=" + leftSrc + "&contSrc=" + contSrc;
}

function viewContentPage2(leftSrc, contSrc) {
    var cont = document.getElementById("contentFrame");
    cont.src = "/plm/portal/viewContent2.jsp?leftSrc=" + leftSrc + "&contSrc=" + contSrc;
}



</script>
</head>
<form method=post target=cen >
<input type=hidden name="menuPage">
<input type=hidden name="centerPage">
</form>
<!--frameset rows="77,*" framespacing="0" frameborder="no" border="0"-->
<!-- OLD Start -->
<!--frameset rows="100,*" framespacing="0" frameborder="no" border="0">
  <frame src="/plm/portal/common/top.jsp" frameborder="no" scrolling="no" noresize="noresize" marginwidth="0" marginheight="0" />
  <frame name="cen" src="main.jsp" frameborder="no" scrolling="yes" noresize="noresize" marginwidth="0" marginheight="0" id="cen" />
  <frame src="/plm/portal/common/footer.jsp" frameborder="no" scrolling="no" noresize="noresize" marginwidth="0" marginheight="0" id="footer" />
</frameset-->
<!-- OLD End -->

<!-- NEW Start -->
<frameset rows="90,*" framespacing=0 frameborder=no border=0 id=topFrame>
  <frame name=topMenuFrame noresize frameborder=no scrolling=no id=topMenuFrame src="/plm/portal/common/top.jsp">
  <frame name=contentFrame noresize frameborder=no scrolling=no id=contentFrame src="/plm/portal/content.jsp?leftSrc=<%=leftSrc %>&contSrc=<%=contSrc %>">
</frameset>
<!-- NEW End -->


</frameset>
<noframes><body>
</body>
</noframes>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.E3PSTask"%></html>
