<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ page import="e3ps.groupware.company.CompanyState"%>
<%@ page import="java.util.Vector"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 메세지 Start-->
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- 메세지 End -->
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/etcReport.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>

<script type="text/javascript">
$(document).ready(function(){
     
     $("[name=startDate]").val("${dashBoardDTO.startDate}");
     $("[name=endDate]").val("${dashBoardDTO.endDate}");
     
     CalendarUtil.dateInputFormat('startDate','endDate');
     CommonUtil.multiSelect('processCode',170);
     CommonUtil.multiSelect('trustCode',130);
     etcReport.createPaingGrid();
     treeGridResize("#SampleSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
     
/*   $("#review").click(function(){
         $("#workTimeReport").attr({action:"/plm/ext/dashboard/workTimeTeamReport",method:"post"}).submit();
     });
     
     $("#product").click(function(){
         $("#workTimeReport").attr({action:"/plm/ext/dashboard/workTimeTeamReport",method:"post"}).submit();
     });
     
     $("#mold").click(function(){
         $("#workTimeReport").attr({action:"/plm/ext/dashboard/workTimeTeamReport",method:"post"}).submit();
     }); */
});

</script>

<!-- EJS TreeGrid End -->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">기타 Report</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif">Dashboard 
                                <img src="/plm/portal/images/icon_navi.gif">Report
                                <img src="/plm/portal/images/icon_navi.gif">기타 Report
                                </td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <form id="workTimeReport" name="workTimeReport">
            <input type="hidden" id="deptCode" name="deptCode" value="">
            <input type="hidden" id="userCode" name="userCode" value="">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                
                <td align="left">
                
                <SELECT class=fm_jmp style="WIDTH: 180px" name=manageProduct>
                <OPTION value="">신뢰성재평가현황</OPTION>
                </SELECT>
                </td>
               
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:etcReport.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                             </td>
                        </tr>
                    </table>
                </td>
            </table>
            
            <table style="width: 100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <!-- 검색영역 collapse/expand -->
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdblueL">실제완료일<span class="red">*</span></td>
                    <td class="tdwhiteL">
                        <input type="text" name="startDate" class="txt_field" style="width: 80px;" value="">
                        <a href="javascript:CommonUtil.deleteValue('startDate');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        ~ 
                        <input type="text" name="endDate" class="txt_field" style="width: 80px;">
                        <a href="javascript:CommonUtil.deleteValue('endDate');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td class="tdblueL">평가구분</td>
                    <td class="tdwhiteL">
                        <select onBlur="fm_jmp" id="trustCode" name="trustCode" multiple="multiple" style="width:170px">
                            <option value="PV">PV</option>
                            <option value="DV">DV</option>
                            <option value="PK">PK</option>
                        </select>
                    </td>
	                  
	                <td class="tdblueL">개발단계</td>
	                <td class="tdwhiteL">
	                <ket:select id="processCode" name="processCode" className="fm_jmp" style="width: 170px;" codeType="PROCESS" multiple="multiple"/>
                    </td>
                        
                </tr>
            </table>
            </form>
            
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table> 
                        <!-- EJS TreeGrid Start -->
                        <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="listGrid"></div>
                                </div>
                            </div>
                        <!-- EJS TreeGrid Start -->
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>   
