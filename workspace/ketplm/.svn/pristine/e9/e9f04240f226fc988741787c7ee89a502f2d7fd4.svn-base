<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.Hashtable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>

<%@page import="wt.org.WTUser"%>
<%@page import="wt.fc.ReferenceFactory"%>

<%@page import="e3ps.project.dao.TaskReportDao"%>
<%@page import="e3ps.common.util.PlmDBUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    // 커넥션 생성
    Connection conn = PlmDBUtil.getConnection();
    ArrayList deptList1 = new ArrayList();
    try{
        TaskReportDao taskReportDao = new TaskReportDao(conn);
        deptList1 = taskReportDao.getDeptLevel1("10190");
    }catch(Exception ex){
	Kogger.error(ex);
    }finally{
        conn.close();
    }

    Hashtable taskDepartment = null;

    String creator = StringUtil.trimToEmpty(request.getParameter("creator"));
    ReferenceFactory rf = new ReferenceFactory();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>

<script language='javascript'>
<!--
  // 주관부서 셋팅
  function setDept(parentValue, child) {
        var url = "/plm/jsp/project/taskChangeCodeAjaxAction.jsp?deptCodeValue=" + parentValue +"&child="+ child;

        callServer(url, setNextDept);
    }
  // NEXT 주관부서 셋팅
  function setNextDept(req) {
        var xmlDoc = req.responseXML;

        var showElements = xmlDoc.selectNodes("//message");
        var child = showElements[0].getElementsByTagName("l_child")[0].text;

        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");
        var l_name = showElements[0].getElementsByTagName("l_name");

        if(child == "dept2" && l_oid.length > 0){
            //document.all.dept21.style.display="block";
            document.all.dept22.style.display="block";
        }else if(child == "dept2" && l_oid.length == 0){
            //document.all.dept21.style.display="none";
            document.all.dept22.style.display="none";
        }

        if(child == "dept3" && l_oid.length > 0){
            document.all.dept23.style.display="block";
        }else if(child == "dept3" && l_oid.length == 0){
            document.all.dept23.style.display="none";
        }

        var arr = new Array();
        for(var i = 0; i < l_oid.length; i++) {
            subArr = new Array();
            subArr[0] = decodeURIComponent(l_oid[i].text);
            subArr[1] = decodeURIComponent(l_name[i].text);
            arr[i] = subArr;
        }

        var fTD = document.all.item(child);
        var childList = document.getElementById(child);
        var len = childList.length;
        for(var j = len ; j > 0 ; j--){
            childList.remove(j);
        }

        if( child == 'dept1' ){
            var childList2 = document.getElementById('dept2');
            var len2 = childList2.length;
            for(var j = len2 ; j > 0 ; j--){
                childList2.remove(j);
            }
        }

        if( child == 'dept2' ){
            var childList3 = document.getElementById('dept3');
            var len3 = childList3.length;
            for(var j = len3 ; j > 0 ; j--){
                childList3.remove(j);
            }
        }

        var newSpan;
        for(var i = 0; i < arr.length; i++) {
            newSpan = document.createElement("option");
            newSpan.innerHTML = arr[i][1];
            newSpan.value= arr[i][0];
            fTD.appendChild(newSpan);
        }
    }

    //사용자 검색 팝업  Open
    function selectUser(rname) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
        attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:510px; center:yes");
        if(typeof attacheMembers == "undefined" || attacheMembers == null) {
            return;
        }
        acceptRoleMember(rname,attacheMembers);
    }

    function acceptRoleMember(rname, objArr) {
        if(objArr.length == 0) {
            return;
        }

        var displayName = document.getElementById(rname+'name');
        var keyName = document.getElementById(rname);

        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            displayName.value = infoArr[4];
            keyName.value = infoArr[0];
        }
    }

    function doDeleteUser() {
        document.forms[0].creatorName.value = "";
        document.forms[0].creator.value = "";
    }

    //초기화
    function deleteValueAll(){
        document.forms[0].dept.options[0].selected = true;
        document.forms[0].dept1.options[0].selected = true;
        document.forms[0].dept2.options[0].selected = true;
        document.forms[0].dept3.options[0].selected = true;
        document.forms[0].checkDept[0].checked = true;
        var deptList1 = document.getElementById('dept1');
        var deptList2 = document.getElementById('dept2');
        var deptList3 = document.getElementById('dept3');

        var len1 = deptList1.length;
        for(var i = len1 ; i > 0 ; i--){
            deptList1.remove(i);
        }
        var len2 = deptList2.length;
        for(var i = len2 ; i > 0 ; i--){
            deptList2.remove(i);
        }
        var len3 = deptList3.length;
        for(var i = len2 ; i > 0 ; i--){
            deptList2.remove(i);
        }
        document.forms[0].creatorName.value = "";
        document.forms[0].creator.value = "";
    }

    //제품 Project 진행현황 검색
    function searchProduct(){
        document.forms[0].cmd.value = "productSearch";
        document.forms[0].target = "productList";
        document.forms[0].action = "/plm/servlet/e3ps/TaskReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    //제품 Project 진행현황 Excel
    function searchProductExcel(){
        document.forms[0].cmd.value = "productExcel";
        document.forms[0].target = "download";
        document.forms[0].action = "/plm/servlet/e3ps/TaskReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    //금형 Project 진행현황 검색
    function searchMold(){
        document.forms[0].cmd.value = "moldSearch";
        document.forms[0].target = "moldList";
        document.forms[0].action = "/plm/servlet/e3ps/TaskReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    //금형 Project 진행현황 Excel
    function searchMoldExcel(){
        document.forms[0].cmd.value = "moldExcel";
        document.forms[0].target = "download2";
        document.forms[0].action = "/plm/servlet/e3ps/TaskReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    //진행현황 검색
    function search(){
        if ( document.forms[0].checkDept[2].checked ){
            document.getElementById('mold').style.display='none';
            searchProduct();
        }else{
            document.getElementById('mold').style.display='block';
            searchProduct();
            searchMold();
        }
    }

//-->
</script>
</head>
<body onload="search();">
<form method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" value="search">
<!-- hidden end -->

<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01">TASK 종합현황</td>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif">프로젝트관리<img src="../../portal/images/icon_navi.gif">종합현황</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteValueAll();" class="btn_blue">초기화</a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                </td>
                  <td width="5">&nbsp;</td>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue">검색</a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
           <%--<td width="30" class="tdblueL">연도</td> --%>
          <td width="50" class="tdwhiteL">
            <select name="year" class="fm_jmp" style="width:55px;margin-bottom: 2px;">
              <option value="">전체</option>
            <% int currentYear = Integer.parseInt(DateUtil.getThisYear());
               for (int inx = currentYear; inx > 2008; inx--) {
            %>
              <option value="<%=inx%>"><%=inx%></option>
            <%
               }
            %>
            </select>
          </td>
          <%--<td width="50" class="tdblueL">본부</td> --%>
          <td width="130" class="tdwhiteL">
            <select name="dept" class="fm_jmp" style="width:125;margin-bottom: 2px;" onchange="javascript:setDept(this.value, 'dept1');">
              <option value="">전체</option>
              <%
                    for(int i = 0; i < deptList1.size(); i++) {
                        taskDepartment = (Hashtable)deptList1.get(i);
                        if(!taskDepartment.get("name").equals("경영기획본부") && !taskDepartment.get("name").equals("경영혁신실")){

              %>
                            <option value="<%=taskDepartment.get("code") %>" ><%=taskDepartment.get("name") %></option>
              <%
                        }
                    }
              %>
            </select>
          </td>
          <td width="130" class="tdwhiteL">
            <select name="dept1" class="fm_jmp" style="width:125;margin-bottom: 2px;" onchange="javascript:setDept(this.value, 'dept2');">
              <option value="">전체</option>
            </select>
          </td>
          <%-- <td width="70"   class="tdblueL" id="dept21" style="display:none;">주관부서</td> --%>
          <td width="120" class="tdwhiteL" id="dept22" style="display:none;" >
            <select name="dept2" class="fm_jmp" style="width:115;margin-bottom: 2px;" onchange="javascript:setDept(this.value, 'dept3');">
              <option value="">전체</option>
            </select>
          </td>
          <td width="120" class="tdwhiteL"  id="dept23" style="display:none;">
            <select name="dept3" class="fm_jmp" style="width:115;margin-bottom: 2px;">
              <option value="">전체</option>
            </select>
          </td>
          <%
                WTUser wtuserbycreator = null;
                if(creator.length() > 0) {
                    wtuserbycreator = (WTUser)rf.getReference(creator).getObject();
                }
          %>
          <td width="50" class="tdblueL">담당자</td>
          <td width="150" class="tdwhiteL0">
            <input type="hidden" name="creator" value="<%=(wtuserbycreator==null)? "":wtuserbycreator.getPersistInfo().getObjectIdentifier().getStringValue()%>">
            <input type="text" name="creatorName"  readonly class="txt_fieldRO"  style="width:55" id="textfield4" value="<%=(wtuserbycreator==null)? "":wtuserbycreator.getFullName()%>" readonly>&nbsp;
            <a href="javascript:;" onClick="javascript:selectUser('creator');"><img src="../../portal/images/icon_user.gif" border="0"></a>&nbsp;&nbsp;
            <a href="javascript:;" onClick="javascript:doDeleteUser('creator','creatorName');"><img src="../../portal/images/icon_delete.gif" border="0"></a>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="780" height="20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03">제품프로젝트 TASK 진행현황(단위:건)</td>
          <td align="left"><input type="radio" id="checkDept"  name="checkDept" value="A" checked>전체<input type="radio"  id="checkDept"  name="checkDept" value="C">자동차사업부<input type="radio" id="checkDept"  name="checkDept" value="E">전자사업부</td>
          <td align="left">TASK</td>
          <td align="left">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><input type="text" name=pTaskName style="width:100"></td>
                </tr>
            </table>
          </td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><a href="javascript:searchProductExcel();"><img src="../../portal/images/iocn_excel.png" border="0"></a></td>
                </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <iframe name="productList" width="780" height="200" src="" border="0" cellspacing="0" cellpadding="0" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
      <div id='mold' style="display:block">
      <table width="780" height="20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03">금형프로젝트 TASK 현황(단위:Set/종)</td>
          <td align="left" width="250"></td>
          <td align="left">TASK</td>
          <td align="left">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><input type="text" name="mTaskName" style="width:100"></td>
                </tr>
            </table>
          </td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><a href="javascript:searchMoldExcel();"><img src="../../portal/images/iocn_excel.png" border="0"></a></td>
                </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <iframe name="moldList" width="780" height="430" border="0" cellspacing="0" cellpadding="0" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
      </div>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
<iframe name="download2" align="center" width="0" height="0" border="0"></iframe>
</form>
</body>
</html>
