<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ page import="e3ps.groupware.company.Department"%>
<%@ page import="e3ps.groupware.company.DepartmentHelper"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%String leaf = ""; %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/jsp/dashboard/standard/js/updateStandard.js"></script>
<script type="text/javascript" src="/plm/extcore/jsp/dashboard/standard/js/Standard2.js"></script>
<script type="text/javascript">
    $(document).ready(function() {

        setEnumYear();

        //tab binding
        var tab = CommonUtil.tabs('tabs');
        tab.tabs({
            heightStyle : "fill"
        });

        var tabIndex = "${selectTab}";

        changeActiveTab(tabIndex);

        //공통기준 tab grid
        updateStandard.createStandardGrid();

        //팀별 공통업무(H/월) tab grid
        updateStandard.createStandardGrid2();
        
        //Role tab grid
        updateStandard.createStandardGrid3();
        
        //Rank tab grid
        updateStandard.createStandardGrid4();
        
        //직급 tab grid
        updateStandard.createStandardGrid5();

        $(document).attr('title', '공수관리 기준정보');

    });

    function setEnumYear() {

        var now = new Date();

        var standardYear = Number(now.getFullYear());
        var yearEnumKey = "";
        for ( var i = 0; i <= 10; i++) {
            yearEnumKey += "|" + standardYear;
            standardYear--;
        }

        $('[name=yearEnumKey]').attr('value', yearEnumKey);
        $('[name=yearTypeEnum]').attr('value', yearEnumKey);
    }

    //탭 활성화 및 selected
    function changeActiveTab(index) {
        $("#tabs").tabs({
            active : index
        });
    }

    function search(oid) {
    	
    	$.ajax({
            type: 'post',
            url: '/plm/ext/dashboard/standard/isLeaf.do?department='+oid,
            success: function (data) {
                if(data.success == 'OK'){
                	$('[name=leaf]').attr('value', data.value);
                }else{
                    alert(data.value);
                }
                //프로그레스바 숨기기
                hideProcessing();
            },
            error : function(){
                alert("알수없는 오류가 발생했습니다.");
                //프로그레스바 숨기기
                hideProcessing();
            }
        });
    	updateStandard.gridReload(oid);
    }
</script>
<form name="StandardViewForm">
<input type="hidden" name="departmentOid" value="" />
<input type="hidden" name="yearEnumKey" value="" />
<input type="hidden" name="yearTypeEnum" value="" />
<input type="hidden" name="delOids" value="" />
<input type="hidden" name="tempColumn" value="" />
<input type="hidden" name="leaf" value="" />
</form>
<table style="width: 100%; height: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                            <tr>
                                <td width="7"></td>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">공수관리 기준정보</td>
                            </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<div id="tabs">
    <ul>
        <li><a class="tabref" href="#tabs-1">공통기준</a></li>
        <li><a class="tabref" href="#tabs-2">팀별 공통업무(H/월)</a></li>
        <li><a class="tabref" href="#tabs-3">Role</a></li>
        <li><a class="tabref" href="#tabs-4">Rank</a></li>
        <li><a class="tabref" href="#tabs-5">직급</a></li>
        <table border="0" cellspacing="0" cellpadding="0" align="right">
            <tr>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a>
                            </td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </ul>
    <div id="tabs-1" style="height: 520px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03">기준시간</td>
                            <td align="right">
                                <%
                                    if (CommonUtil.isAdmin()) {
                                %>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.updateData(0);"
                                            class="btn_blue"> <%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.addRow(0);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07152")%><!-- 하위추가 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.removeRowOnly(0);"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table> <%
     }
 %>
                            </td>

                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
        </table>

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->
    </div>
    
    <div id="tabs-2" style="height: 520px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="97%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03">기준시간</td>
                            <td align="right">
                                <%
                                    if (CommonUtil.isAdmin()) {
                                %>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.updateData(1);"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.addRow(1);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07152")%><!-- 하위추가 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.removeRowOnly(1);"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table> <%
     }
 %>
                            </td>

                        </tr>
                    </table>
                </td>
            </tr>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table style="width: 100%;" border="0">
                <tr>
                    <td style="width: 20%;" valign="top">
                        <div>
                            <div class="tabMain">
                                <iframe src="/plm/jsp/project/AddProjectDeptTree.jsp?mode=s" name="deptTree" id="deptTree" frameborder="0" width="200px" height="470px;" scrolling="auto" style="overflow-x: hidden; overflow-y: auto;"></iframe>
                            </div>
                        </div>
                    </td>
                    <td align="left" valign="top">

                        <!-- EJS TreeGrid Start -->
                        <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="listGrid2" style="WIDTH: 100%; HEIGHT: 470px"></div>
                                </div>
                            </div>
                        <!-- EJS TreeGrid End -->

                    </td>
                </tr>
            </table>
        </table>
        </div>
        
        <div id="tabs-3" style="height: 520px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03">가중치</td>
                            <td align="right">
                                <%
                                    if (CommonUtil.isAdmin()) {
                                %>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.updateData(2);"
                                            class="btn_blue"> <%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.addRow(2);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07152")%><!-- 하위추가 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.removeRowOnly(2);"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table> <%
     }
 %>
                            </td>

                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
        </table>

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid3" style="WIDTH: 100%; HEIGHT: 470px"></div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->
        
    </div>
    
    <div id="tabs-4" style="height: 520px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03">가중치</td>
                            <td align="right">
                                <%
                                    if (CommonUtil.isAdmin()) {
                                %>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.updateData(3);"
                                            class="btn_blue"> <%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.addRow(3);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07152")%><!-- 하위추가 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.removeRowOnly(3);"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table> <%
     }
 %>
                            </td>

                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
        </table>

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid4" style="WIDTH: 100%; HEIGHT:  470px"></div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->
    </div>
    
    <div id="tabs-5" style="height: 520px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03">가중치</td>
                            <td align="right">
                                <%
                                    if (CommonUtil.isAdmin()) {
                                %>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.updateData(4);"
                                            class="btn_blue"> <%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.addRow(4);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07152")%><!-- 하위추가 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue"
                                            background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:updateStandard.removeRowOnly(4);"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table> <%
     }
 %>
                            </td>

                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
        </table>

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid5" style="WIDTH: 100%; HEIGHT: 100%"></div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->
    </div>
    
    </div>
    
    
</div>
