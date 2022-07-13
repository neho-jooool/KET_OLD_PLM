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
<script type="text/javascript" src="/plm/extcore/js/dashboard/workTimeTeamReport.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>

<!-- EJS TreeGrid End -->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">팀별 계획공수현황(Report)</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif">Dashboard 
                                <img src="/plm/portal/images/icon_navi.gif">팀별 계획공수현황
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
                <input type="radio" id="product" name="pjtType" value="PRODUCTPROJECT" checked="checked"><%=messageService.getString("e3ps.message.ket_message","02536")%><%--제품--%></input>
                <input type="radio" id="review" name="pjtType" value="REVIEWPROJECT"><%=messageService.getString("e3ps.message.ket_message","00716")%><%--검토--%></input> 
                <input type="radio" id="mold" name="pjtType" value="MOLDPROJECT"><%=messageService.getString("e3ps.message.ket_message","00997")%><%--금형--%></input> 
                </td>
               
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>산출식&nbsp;&nbsp;<a href="/plm/extcore/jsp/bom/KETExcelTemplateDownload.jsp?filepath=TeamWorkCalculation.xlsx"><img src="/plm/portal/images/iocn_excel.png" alt="excel down" name="leftbtn_02" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:WorkTime.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                             </td>
                                <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:WorkTime.viewStandard();" class="btn_blue">기준정보</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table></td>
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
                    <td class="tdblueL"  width="80">인원</td>
                    <td class="tdwhiteL" width="*" >
                        <input type="text" id="userName" name="userName" class="txt_field" style="width: 30%;">
                        <a href="javascript:SearchUtil.selectOneUser('userCode','userName');">
                        <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('userCode','userName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td class="tdblueL"  width="80" title="<%=messageService.getString("e3ps.message.ket_message", "01201") %>"><%=messageService.getString("e3ps.message.ket_message", "01201") %></div></td>
                    <td class="tdwhiteL" width="*" >
                        <input type="text" id="departName" name="departName" class="txt_field" style="width:30%">
                        <a href="javascript:SearchUtil.addDepartmentAfterFunc(false, 'selectDeptRsltFunc')">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                        <a href="javascript:deleteDept();">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <!-- <td class="tdblueL"  width="70"><%=messageService.getString("e3ps.message.ket_message", "01662") %></td>
                    <td width="*" class="tdwhiteL">
                        <select id="vdivision" name="vdivision" style="width: 170px;" multiple="multiple">
                        <option value="C">자동차사업부</option>
                        <option value="E">전자사업부</option>
                        </select>
                    </td>  -->
                        
                </tr>
                <tr>
                <td class="tdblueL">직급</td>
                <td class="tdwhiteL">
                <select id="duty" name="duty" style="width: 170px;" multiple="multiple">
                  <%
                        Vector dutyNameList = CompanyState.dutyNameList;
                        Vector dutyCodeList = CompanyState.dutyCodeList;
                      for (int i = 0; i < dutyCodeList.size(); i++) {
                          if(!"고문".equals(dutyNameList.get(i)) && !"감사".equals(dutyNameList.get(i)) && !"회장".equals(dutyNameList.get(i)) && !"사장".equals(dutyNameList.get(i)) && !"부사장".equals(dutyNameList.get(i))
                        	  && !"전무".equals(dutyNameList.get(i)) && !"상무".equals(dutyNameList.get(i)) && !"상무보".equals(dutyNameList.get(i)) && !"이사대우".equals(dutyNameList.get(i)) && !"기장".equals(dutyNameList.get(i))
                        	  && !"선임반장".equals(dutyNameList.get(i)) && !"반장".equals(dutyNameList.get(i))){
                  %>
                          <option value="<%=dutyCodeList.get(i)%>"><%=dutyNameList.get(i)%></option>
                  <%
                          }
                      }
                  %>
                  </select></td>
                    <td class="tdblueL">기간</td>
                    <td width="*" class="tdwhiteL" colspan="4">
                        <input type="text" name="startDate" class="txt_field" style="width: 80px;">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('startDate');" style="cursor: hand;"> 
                        ~ 
                        <input type="text" name="endDate" class="txt_field" style="width: 80px;">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('endDate');" style="cursor: hand;">
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


<script type="text/javascript">
$(document).ready(function(){
	
	 //$("[name=startDate]").val("${dashBoardDTO.startDate}");
     //$("[name=endDate]").val("${dashBoardDTO.endDate}");
     
     SuggestUtil.bind('USER', 'userName', 'userCode');
     SuggestUtil.bind('DEPARTMENT', 'departName', 'deptCode');
	
	 //CommonUtil.singleSelect('division',100);
	 CommonUtil.multiSelect('devType',100);
	 CommonUtil.singleSelect('dateType',100);
	 
	 
	 $("[name=startDate]").val("${dashBoardDTO.startDate}");
	 $("[name=endDate]").val("${dashBoardDTO.endDate}");
	 
	 CalendarUtil.dateInputFormat('startDate','endDate');
	
     // 직급
     $("#duty").multiselect({
    	 duty: 80,
         noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
         checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
         uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
     });
     
<%--      // 사업부
     $("#vdivision").multiselect({
         duty: 80,
         noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %>선택',
         checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %>전체선택',
         uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %>전체해제'
     });
	  --%>
	
	 if("${pjtType}" == "PRODUCTPROJECT"){
         $("#product").attr("PRODUCTPROJECT",true);
     }else if("${pjtType}" == "REVIEWPROJECT"){
    	 $("#review").attr("checked",true);
     }else if("${pjtType}" == "MOLDPROJECT"){
    	 $("#mold").attr("checked",true);
     }
	 
	 WorkTime.createPaingGrid();
	 treeGridResize("#SampleSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	 
	 $("#review").click(function(){
		 $("#workTimeReport").attr({action:"/plm/ext/dashboard/workTimeTeamReport",method:"post"}).submit();
	 });
	 
	 $("#product").click(function(){
         $("#workTimeReport").attr({action:"/plm/ext/dashboard/workTimeTeamReport",method:"post"}).submit();
     });
	 
	 $("#mold").click(function(){
		 $("#workTimeReport").attr({action:"/plm/ext/dashboard/workTimeTeamReport",method:"post"}).submit();
     });
});

function selectDeptRsltFunc(rsltArray){
    var name = "";
    var oid = "";
    var code = "";
    
    for(var i = 0; i < rsltArray.length ; i++){
         if(i != 0){
             oid += ",";
             name += ",";
             code += ",";
         }
        name += rsltArray[i].NAME;
        oid += rsltArray[i].OID;
        code += rsltArray[i].CODE;
    }
    
    $('[name=deptCode]').val(oid);
    $('[name=departName]').val(name);
}

function deleteDept(){
	$('[name=deptCode]').val('');
	$('[name=departName]').val('');
}

</script>