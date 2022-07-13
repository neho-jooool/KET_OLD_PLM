<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.jdf.config.*"%>
<%@include file="/jsp/common/context.jsp" %>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.ProjectViewButtonAuth"%>
<%@page import = "e3ps.project.*,
					e3ps.project.beans.*,
					e3ps.common.util.*,
					wt.lifecycle.State"%>
<%
	String oid = request.getParameter("oid");
	String popup = request.getParameter("popup");

	TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);
	boolean isInWork = (project.getLifeCycleState()==State.INWORK)||(project.getLifeCycleState().toString()=="REWORK");
%>
<html>
<head>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<script language=JavaScript src="/plm/portal/js/viewObject.js"></script>

<script language=JavaScript>
<!--
function viewData(url) {
	//parent.body.document.location.href = url;
<%
	if(popup != null && popup.equals("popup")) {
%>
		parent.body.document.location.href = url;
<%
	} else {
%>
		parent.parent.document.frames["contName"].location.href = url;
<%
	}
%>
}

function reloadTree(){
	//var form = parent.tree.document.forms[0];
	//form.action = "/plm/jsp/project/template/TemplateProjectTree.jsp?oid=<%=oid%>";
	//form.submit();
	parent.document.location.reload();
}

function manageTask(oid){
	var url = "/plm/jsp/project/manage/ManageTemplateProjectTaskFrm.jsp?oid="+oid;
	openOtherName(url,"manageTask","950","750","status=yes,scrollbars=no,resizable=yes");
}

// [START] [PLM System 1차개선] WBS 일정 변경 화면 Link, 2013-07-16, BoLee
function openChangeWBSSchedule(oid) {

     var screenWidth = screen.availWidth;
     var screenHeight = screen.availHeight;
     var url = "/plm/servlet/e3ps/ProjectScheduleServlet?oid=" + oid + "&cmd=wbs_search&width=" + screenWidth + "&height=" + screenHeight;
     openOtherName(url, "ChangeProjectSchedule", screenWidth, screenHeight, "status=no,scrollbars=no,resizable=no");
}
// [END] [PLM System 1차개선] WBS 일정 변경 화면 Link, 2013-07-16, BoLee

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function excelDown(){
	document.forms[0].method = "post";
	document.forms[0].command.value = "excelDown";
	document.forms[0].action = "/plm/servlet/e3ps/TemplateProjectServlet";
	document.forms[0].submit();
}
-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>
<body  onLoad="MM_preloadImages('/plm/portal/images/btn_ref_s.png','/plm/portal/images/btn_task_s.png')">
<form>
<input type="hidden" name="command" value=""></input>
<input type="hidden" name="oid" value="<%=oid%>">
<table width="200" height="100%" border="0" cellspacing="0" cellpadding="0" >
    <tr>
	  <td height="44"><table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td ><img src="/plm/portal/images/subh_7.png"></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table></td>
			  <td width="1" bgcolor="#c6c6c6"></td>
		<td width="9" valign="top"></td>
	</tr><tr>
      <td width="190" align="center" valign="top"><table width="170" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" valign="top"><table width="170" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="7" height="7"><img src="/plm/portal/images/box_5.gif"></td>
                  <td bgcolor="#e6f0ff"></td>
                  <td width="7" height="7"><img src="/plm/portal/images/box_6.gif"></td>
                </tr>
                <tr>
                  <td width="7" bgcolor="#e6f0ff"></td>
                  <td align="center" valign="middle" bgcolor="#e6f0ff">
                      <a href="javascript:reloadTree();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image19','','/plm/portal/images/btn_ref_s.png',1)">
                      <img src="/plm/portal/images/btn_ref.png" name="Image19" border="0"></a>
                      <!-- [START] [PLM System 1차개선] WBS 일정 변경 화면 Link, 2013-07-16, BoLee -->
                      <!--
                      <a href="javascript:manageTask('<%=oid%>');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','/plm/portal/images/btn_task_s.png',1)">
                      <img src="/plm/portal/images/btn_task.png" name="Image3" border="0"></a>
                      -->
                      <a href="javascript:openChangeWBSSchedule('<%= oid %>');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','/plm/portal/images/btn_task_s.png',1)">
                      <img src="/plm/portal/images/btn_task.png" name="Image3" border="0"></a>
                      <!-- [END] [PLM System 1차개선] WBS 일정 변경 화면 Link, 2013-07-16, BoLee -->
                      <img src="/plm/portal/images/iocn_excel.png" onclick="javascript:excelDown();" style="cursor:hand" alt="excel down" name="leftbtn_02" border="0">
                  </td>
                  <td width="7" bgcolor="#e6f0ff"></td>
                </tr>
                <tr>
                  <td width="7" height="7"><img src="/plm/portal/images/box_7.gif"></td>
                  <td bgcolor="#e6f0ff"></td>
                  <td width="7" height="7"><img src="/plm/portal/images/box_8.gif"></td>
                </tr>
              </table></td>
          </tr></table>
		</td>
		<td width="1" bgcolor="#c6c6c6"></td>
		<td width="9" valign="top"><!-- <img src="/plm/portal/images/img_common/arrow.gif" width="9" height="30"> --></td>
	</tr>
	<tr>
		<td width="190" height="4"align="center" valign="top">
		<td width="1" bgcolor="#c6c6c6"></td>
		<td width="9" valign="top"></td>
	</tr>
</table>

<!--<table border="0" cellpadding="0" cellspacing="0" width="200">
	<tr>
		<td align="center">
			<!--
			<a href="javascript:viewData('/plm/servlet/e3ps.project.servlet.SearchProjectOutputServlet?oid=<%=oid%>&type=temp');">
			<img src="/plm/portal/images/img_default/left/btn_product.gif" alt="산출물 목록" border="0">
			</a>
			&nbsp;
			<a href="javascript:reloadTree();">
			<img src="/plm/portal/images/img_default/left/btn_ref.gif" alt="새로고침" border="0">
			</a>
			&nbsp;
		<% if(isInWork ) { %>
			<a href="javascript:manageTask('<%=oid%>');">
			<img src="/plm/portal/images/img_default/left/btn_task.gif" alt="TASK관리" border="0">
			</a>
		</td>
		<% } %>
	</tr>
</table>-->
</form>
</body>
</html>
