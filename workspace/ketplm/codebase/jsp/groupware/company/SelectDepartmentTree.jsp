<%--
 /**
 * @(#)	SelectDepartmentTree.jsp
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.2
 * @author Seong Jin, Han sjhan@e3ps.com
 * @desc 부서 선택 화면
 */
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*" %>
<%@include file="/jsp/common/context.jsp"%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<script src="/plm/portal/js/newtree.js"></script>
<SCRIPT type="text/javascript">
<!--
    function doSubmit(){
    }
//-->
</SCRIPT>
<%
    String mode = request.getParameter("mode");
    String function = request.getParameter("function");
    String target = request.getParameter("target");
%>
<BODY>
<form method=post>
<input type="hidden" name="soid">
<input type="hidden" name="sname">
<input type="hidden" name="scode">
<input type="hidden" name="sdept">
<!--테이블1//-->
<table border="0" cellspacing="0" cellpadding="0" width="330">
    <tr>
        <td>
            <table width="330" border="0" cellpadding="0" cellspacing="0" class="popBox">
                <tr>
                    <td class="boxTLeft"></td>
                    <td class="boxTCenter"></td>
                    <td class="boxTRight"></td>
                </tr>
                <tr>
                    <td class="boxLeft"></td>
                    <td>
<!------------------------------------- 본문 시작 //----------------------------------------->
                        <table border="0" cellspacing="0" cellpadding="0" width="300">
                        <tr>
                            <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "01544") %><%--부서 선택--%></td>
                        </tr>
                        </table>
                        <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
                        <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
                        <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
                        <DIV id="scrollbox">
                        <table border="0" cellspacing="0" cellpadding="0" width="300">
                            <tr>
                                <td valign=top>
                                    <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
                                </td>
                            </tr>
                        </table>
                        </DIV>
                        <SCRIPT>
                            var target = eval("parent.main");
                            var tree = new Tree("treeTable","부서","<%=iconUrl%>/tree/company");
                            tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
                            tree.treeSelectionListener = function(node, event){
                                this.selectNode(node, event);
                                document.forms[0].soid.value = node.get("oid");
                                document.forms[0].sname.value = node.name;
                                document.forms[0].scode.value = node.get("code");
                                //document.companytree.ssort.value = node.get("sort");
                                document.forms[0].sdept.value = node.get("dept");
                                var arrObj = new Array();
                                arrObj[0] = node.get("oid");
                                arrObj[1] = node.get("name");
                                arrObj[2] = node.get("code");
                                arrObj[3] = node.get("dept");
                                opener.<%=function%>(arrObj, '<%=target%>');
                                self.close();

                                //target.document.location.href = "/plm/servlet/e3ps/ManagePeopleServlet?oid="+node.get("oid");

                            }
                            tree.root.put("oid","root");
                            tree.root.put("code","root");
                            tree.root.put("sort","0");
                            tree.root.put("dept","root");
                        <%
                            ArrayList topList = DepartmentHelper.manager.getTopList();
                            for ( int i = 0 ; i < topList.size() ; i++ ) {
                                Department topDepartment = (Department)topList.get(i);
                                long topnodeid = CommonUtil.getOIDLongValue(topDepartment);
                                String tempCode = null;
                                if((Department)topDepartment.getParent() != null)
                                    tempCode = ((Department)topDepartment.getParent()).getCode();
                                else
                                    tempCode = "root";
                        %>
                            var node<%=topnodeid%> = new TreeNode("<%=topDepartment.getName()%> [<%=topDepartment.getCode()%>]");
                            node<%=topnodeid%>.put("oid","<%=CommonUtil.getOIDString(topDepartment)%>");
                            node<%=topnodeid%>.put("code","<%=topDepartment.getCode()%>");
                            node<%=topnodeid%>.put("sort","<%=topDepartment.getSort()%>");
                            node<%=topnodeid%>.put("dept","<%=tempCode%>");
                            node<%=topnodeid%>.put("name","<%=topDepartment.getName()%>");
                            tree.root.add(node<%=topnodeid%>);
                        <%
                                ArrayList underList = DepartmentHelper.manager.getAllChildList(topDepartment,new ArrayList());
                                for ( int j = 0 ; j < underList.size() ; j++ ) {
                                    Department underDepartment = (Department)underList.get(j);
                                    Department parentDepartment = (Department)underDepartment.getParent();
                                    long undernodeid = CommonUtil.getOIDLongValue(underDepartment);
                                    long parentnodeid = CommonUtil.getOIDLongValue(parentDepartment);
                        %>
                            var node<%=undernodeid%> = new TreeNode("<%=underDepartment.getName()%> [<%=underDepartment.getCode()%>]");
                            node<%=undernodeid%>.put("oid","<%=CommonUtil.getOIDString(underDepartment)%>");
                            node<%=undernodeid%>.put("code","<%=underDepartment.getCode()%>");
                            node<%=undernodeid%>.put("sort","<%=underDepartment.getSort()%>");
                            node<%=undernodeid%>.put("dept","<%=parentDepartment.getCode()%>");
                            node<%=undernodeid%>.put("name","<%=underDepartment.getName()%>");
                            node<%=parentnodeid%>.add(node<%=undernodeid%>);
                        <%
                                }
                            }
                        %>
                            tree.loadIcons();
                            tree.expandAll();
                            tree.repaint();
                        </SCRIPT>
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
        </td>
    </tr>
</table>
</form>
</body>
