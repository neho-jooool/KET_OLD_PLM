<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>Untitled Document</title>
<%@ page import="wt.org.WTUser
                            ,wt.fc.QueryResult
                            ,wt.content.ContentHolder
                            ,wt.content.ContentHelper
                            ,wt.content.ContentRoleType
                            ,wt.content.ApplicationData
                            ,wt.content.ContentItem
                            ,wt.session.SessionHelper
                            ,wt.fc.PersistenceHelper" %>
<%@ page import= "java.util.Vector
                             ,java.net.URL
                             ,java.util.Hashtable" %>
<%@ page import= "e3ps.dms.entity.KETProjectDocument
                             ,e3ps.common.util.DateUtil
                             ,e3ps.common.util.StringUtil
                             ,e3ps.common.util.KETObjectUtil
                             ,e3ps.ecm.entity.KETMoldPlanRefDocLink
                             ,e3ps.common.util.CommonUtil
                             ,e3ps.common.content.ContentInfo
                             ,e3ps.common.content.ContentUtil
                             ,e3ps.ecm.beans.EcmSearchHelper
                             ,e3ps.ecm.beans.EcmUtil
                             ,e3ps.common.code.NumberCodeHelper"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="plan" class="e3ps.ecm.entity.KETMoldChangePlan" scope="request"/>
<jsp:useBean id="commentList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    WTUser owner = (WTUser)plan.getOwner().getPrincipal();
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                      //PLM Admin

    QueryResult linkQr = null;

    boolean isOwner = false;
    if( owner.equals( loginUser ) )
    {
        isOwner = true;
    }

    linkQr = PersistenceHelper.manager.navigate( plan, "refDoc", KETMoldPlanRefDocLink.class );

    ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)plan );
    Vector attachFileList = ContentUtil.getSecondaryContents(holder);

%>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
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

<script type="text/javascript">
<!--

function goList()
{
    if( document.forms[0].prePage.value == "S" )
    {
        history.back();
    }
    else
    {
        document.forms[0].action='/plm/jsp/ecm/SearchMoldPlan.jsp'
        document.forms[0].submit();
    }
}
function viewRelDoc(oid)
{
    var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid="+oid+"&isRefresh=N";
    openWindow(url,"","800","640","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//부품 상세조회 팝업
function viewPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

//금형ECO상세조회 팝업
function viewMoldEco(oid){
    var url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid="+oid;
    openWindow2(url,"","800","600","scrollbars=no,resizable=no,top=200,left=250");
}

//제품ECO상세조회 팝업
function viewProdEco(oid){
    var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid="+oid;
    openWindow2(url,"","800","600","scrollbars=no,resizable=no,top=200,left=250");
}

-->
</script>
</head>
<body>
<form name="ViewPlanForm"  method="post">
<input type="hidden"  name="cmd"  value="View">
<input type= "hidden" name ="oid"  value="<%=CommonUtil.getOIDString( plan )%>" >
<input type= "hidden" name ="scheduleId"  value="<%=plan.getScheduleId()%>" >
<input type="hidden"  name="prePage"  value="<%=prePage %>">
<input type="hidden"  name="currentUserId" >
<input type="hidden"  name="currentUserName" >
<input type="hidden"  name="currentUserComment" >
<input type="hidden"  name="currentUserLineOrder" >
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02087") %><%--양산금형 일정 상세조회--%></td>
                <td align="right">
                  <img src="/plm/portal/images/icon_navi.gif">Home
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02089") %><%--양산금형 일정관리--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02091") %><%--양산금형 일정상세--%>
                </td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
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
          <td width="110" class="tdblueL">Die No</td>
          <td width="155" class="tdwhiteL"><a href="javascript:viewPart('<%=EcmUtil.getLastestPartOid(plan.getDieNo())%>');"><%=plan.getDieNo() %></a></td>
          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01337") %><%--등록자--%></td>
          <td width="140" class="tdwhiteL"><%=plan.getOwner().getFullName() %></td>
          <td width="85" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01336") %><%--등록일자--%></td>
          <td width="200" class="tdwhiteL0"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( PersistenceHelper.getCreateStamp(plan), "yyyy-MM-dd"),"-") %></td>
        </tr>
        <tr>
            <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="155" class="tdwhiteL"><a href="javascript:viewPart('<%=EcmUtil.getLastestPartOid(plan.getPartNo()) %>');"><%=StringUtil.checkReplaceStr(plan.getPartNo(),"-") %></a></td>
          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="315" colspan="3" class="tdwhiteL0"><%=StringUtil.checkReplaceStr(plan.getPartName(),"-")%></td>
        </tr>
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="155" class="tdwhiteL"><%=StringUtil.checkReplaceStr(plan.getPlanType(), "-")%></td>
           <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
          <td width="140" class="tdwhiteL"><%=StringUtil.checkReplaceStr( plan.getProdDeptName(), "-")%></td>
          <td width="85" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
          <%
           if( StringUtil.checkNull(plan.getVendorFlag()).equals("i") && StringUtil.checkNull(plan.getVendorCode()).length() > 0 ) {
           %>
          <td width="200" class="tdwhiteL0"><%= StringUtil.checkNull(NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", plan.getVendorCode() ).getName() ) %></td>
          <%
           }
           else if( StringUtil.checkNull(plan.getVendorFlag()).equals("o") &&  StringUtil.checkNull(plan.getVendorCode()).length() > 0 )
           {
           %>
          <td width="200" class="tdwhiteL0"><%= StringUtil.checkNull(EcmSearchHelper.manager.getPartnersName(plan.getVendorCode())) %></td>
          <%
            }
           else
           {
          %>
           <td width="200" class="tdwhiteL0">-</td>
          <%
           }
           %>
          </td>
        </tr>
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01322") %><%--등록근거--%></td>
          <td width="155" class="tdwhiteL"><%=StringUtil.checkReplaceStr(plan.getRegBasis(),"-") %></td>
          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02562") %><%--제품ECO담당--%></td>
          <td width="140" class="tdwhiteL"><%if(plan.getProdEcoOwner() !=null){ %><%=plan.getProdEcoOwner().getFullName()%><%}else{ %>-<%} %></td>
          <td width="85" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01033") %><%--금형ECO담당--%></td>
          <td width="200" class="tdwhiteL0"><%if(plan.getMoldEcoOwner() !=null){ %><%=plan.getMoldEcoOwner().getFullName()%><%}else{ %>-<%} %></td>
        </tr>
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00994") %><%--근거일자--%></td>
          <td width="155" class="tdwhiteL"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getBasisDate(), "yyyy-MM-dd"),"-") %></td>
          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02560") %><%--제품ECO No--%></td>
          <td width="140" class="tdwhiteL"><%if( plan.getProdECO()  != null ) {%><a href="javascript:viewProdEco('<%=CommonUtil.getOIDString(plan.getProdECO()) %>');"><%=StringUtil.checkNull(plan.getProdECO().getEcoId()) %></a><%} %>&nbsp;</td>
          <td width="85" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01029") %><%--금형ECO No--%></td>
          <td width="200" class="tdwhiteL0"><%if( plan.getMoldECO() != null ) {%><a href="javascript:viewMoldEco('<%=CommonUtil.getOIDString(plan.getMoldECO()) %>');"><%=StringUtil.checkNull(plan.getMoldECO().getEcoId()) %></a><%} %>&nbsp;</td>
        </tr>
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01946") %><%--수정내용--%></td>
          <td class="tdwhiteL0" colspan=5>
          <textarea name="modify_desc" style='overflow: auto; width:697; height:50px; padding: 10;' class="fm_area" readonly><%=StringUtil.checkNull(plan.getModifyDesc())%></textarea>
          </td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02370") %><%--일정정보--%></td>
          <td colspan="5" class="tdwhiteL0"><table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="695" cellpadding="0" cellspacing="0" class="table_border">
                <tr>
                 <td width="30" class="tdgrayM">&nbsp;</td>
                <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02585") %><%--제품도--%></td>
                <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01085") %><%--금형설계--%></td>
                <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01097") %><%--금형입고--%></td>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%></td>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01104") %><%--금형조립--%></td>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01037") %><%--금형Try--%></td>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02624") %><%--제품측정--%></td>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00737") %><%--검토협의--%></td>
              </tr>
               <tr>
               <td width="30" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
                <td width="83" class="tdwhiteM" ><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getProdDwgPlanDate(), "yyyy-MM-dd"),"-") %></td>
                <input type="hidden" name="prod_plan_date"  value="<%=DateUtil.getTimeFormat( plan.getProdDwgPlanDate(), "yyyy-MM-dd") %>" >
                <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getMoldChangePlanDate(),  "yyyy-MM-dd"),"-") %></td>
                <input type="hidden" name="mold_eco_plan_date"  value="<%=DateUtil.getTimeFormat( plan.getMoldChangePlanDate(), "yyyy-MM-dd") %>" >
                <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getStorePlanDate(), "yyyy-MM-dd" ),"-") %></td>
                <input type="hidden" name="store_plan_date"  value="<%=DateUtil.getTimeFormat( plan.getStorePlanDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getWorkPlanDate(), "yyyy-MM-dd" ),"-") %></td>
                 <input type="hidden" name="work_plan_date"  value="<%=DateUtil.getTimeFormat( plan.getWorkPlanDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getAssemblePlanDate(), "yyyy-MM-dd" ),"-") %></td>
                 <input type="hidden" name="ass_plan_date"  value="<%=DateUtil.getTimeFormat( plan.getAssemblePlanDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getTryPlanDate(), "yyyy-MM-dd" ),"-") %></td>
                 <input type="hidden" name="try_plan_date"  value="<%=DateUtil.getTimeFormat( plan.getTryPlanDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getTestPlanDate(), "yyyy-MM-dd" ),"-") %></td>
                 <input type="hidden" name="test_plan_date"  value="<%=DateUtil.getTimeFormat( plan.getTestPlanDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getApprovePlanDate(), "yyyy-MM-dd"),"-") %></td>
                 <input type="hidden" name="approve_plan_date"  value="<%=DateUtil.getTimeFormat( plan.getApprovePlanDate(), "yyyy-MM-dd") %>" >
              </tr>
              <tr>
               <td width="30" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%>
                <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getProdDwgActualDate(), "yyyy-MM-dd"),"-") %></td>
                <input type="hidden" name="prod_actual_date"  value="<%=DateUtil.getTimeFormat( plan.getProdDwgActualDate(), "yyyy-MM-dd") %>" >
                <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getMoldChangeActualDate(), "yyyy-MM-dd"),"-") %></td>
                <input type="hidden" name="mold_eco_actual_date"  value="<%=DateUtil.getTimeFormat( plan.getMoldChangeActualDate(), "yyyy-MM-dd") %>" >
                <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getStoreActualDate(), "yyyy-MM-dd" ),"-") %></td>
                <input type="hidden" name="store_actual_date"  value="<%=DateUtil.getTimeFormat( plan.getStoreActualDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getWorkActualDate(), "yyyy-MM-dd" ),"-") %></td>
                 <input type="hidden" name="work_actual_date"  value="<%=DateUtil.getTimeFormat( plan.getWorkActualDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getAssembleActualDate(), "yyyy-MM-dd" ),"-") %></td>
                 <input type="hidden" name="ass_actual_date"  value="<%=DateUtil.getTimeFormat( plan.getAssembleActualDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getTryActualDate(), "yyyy-MM-dd" ),"-") %></td>
                 <input type="hidden" name="try_actual_date"  value="<%=DateUtil.getTimeFormat( plan.getTryActualDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getTestActualDate(), "yyyy-MM-dd" ),"-") %></td>
                 <input type="hidden" name="test_actual_date"  value="<%=DateUtil.getTimeFormat( plan.getTestActualDate(), "yyyy-MM-dd") %>" >
                 <td width="83" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getApproveActualDate(), "yyyy-MM-dd"),"-") %></td>
                 <input type="hidden" name="approve_actual_date"  value="<%=DateUtil.getTimeFormat( plan.getApproveActualDate(), "yyyy-MM-dd") %>" >
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
         <tr>
            <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02728") %><%--진행내용--%></td>
            <td colspan="5" class="tdwhiteL0">
                    <table border="0" cellspacing="0" cellpadding="0" width="670">
                        <tr>
                            <td class="space5"></td>
                          </tr>
                    </table>
                    <table width="695" cellpadding="0" cellspacing="0" class="table_border">
                        <tr>
                            <td width="80" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "02899") %><%--측정구분--%></td>
                            <%if(StringUtil.checkReplaceStr(plan.getMeasureType(),"-").equalsIgnoreCase("불합격")) {  %>
                            <td width="93" class="tdwhiteL"><font color=red><%=StringUtil.checkReplaceStr(plan.getMeasureType(),"-") %></font></td>
                            <%}else if(StringUtil.checkReplaceStr(plan.getMeasureType(),"-").equalsIgnoreCase("합격")) { %>
                            <td width="93" class="tdwhiteL"><font color=blue><%=StringUtil.checkReplaceStr(plan.getMeasureType(),"-") %></font></td>
                            <%}else{ %>
                            <td width="93" class="tdwhiteL"><%=StringUtil.checkReplaceStr(plan.getMeasureType(),"-") %></font></td>
                            <%} %>
                              <td width="80" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "01628") %><%--불합격 조치--%></td>
                            <td width="93" class="tdwhiteL"><%=StringUtil.checkReplaceStr(plan.getFailAction(),"-") %></td>
                              <td width="80" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "00747") %><%--결과--%></td>
                            <td width="93" class="tdwhiteL"><%=StringUtil.checkReplaceStr(plan.getResult(),"-") %></td>
                              <td width="80" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "02625") %><%--제품측정일--%></td>
                            <td width="94" class="tdwhiteL"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getMeasureDate(), "yyyy-MM-dd" ),"-") %>&nbsp;</td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="670">
                        <tr>
                            <td class="space5"></td>
                          </tr>
                      </table>
            </td>
        </tr>
        <tr>
            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
            <td colspan="5" class="tdwhiteL0"><textarea name="plan_desc" style='overflow: auto; width:695; height:50px; padding: 10;' class="fm_area"  readonly><%=StringUtil.checkNull(plan.getPlanDesc()) %></textarea></td>
        </tr>
        <tr>
            <td width="100" class="tdblueL">Comment</td>
             <td colspan="5" class="tdwhiteL0">
            <table border="0" cellspacing="0" cellpadding="0" width="695">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
             <table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="695" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                  <td width="91" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                <td width="496" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
                <td width="77" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
              </tr>
            </table>
            <div style="height=50;width=695;overflow-x:hidden;overflow-y:auto;" class="table_border">
            <table width="695" cellpadding="0" cellspacing="0" id="commentTable">
            <col width='78'><col width='440'><col width='70'><col width='93'>
              <%
              if( commentList != null && commentList.size() > 0 )
              {
                  Hashtable<String, String> commentData = null;
                  for( int cnt=0; cnt < commentList.size(); cnt++)
                  {
                      commentData = (Hashtable)commentList.get(cnt);
             %>
                     <tr height="23" onMouseOver='commentTable.clickedRowIndex=this.rowIndex' id='commentTR'>
                         <%if( loginUser.getName().equals( commentData.get("user_id") ) ){ %>
                         <td class="tdwhiteL" width="78">
                         <%=StringUtil.checkReplaceStr( commentData.get("createdate"),"-")%>
                         </td>
                         <td class="tdwhiteL" width="440">
                         <input name='planComment' type='text'  value="<%=commentData.get("plan_comment") %>"class='txt_field'  style='width:100%'>
                         </td>
                         <%}else{ %>
                         <td class="tdwhiteL"  width="78">
                         <%=StringUtil.checkReplaceStr( commentData.get("createdate"),"-") %>
                         </td>
                         <td class="tdwhiteL" width="440">
                         <input name='planComment' type='hidden'  value="<%=commentData.get("plan_comment") %>"><%=commentData.get("plan_comment")%>
                         </td>
                         <%} %>
                         <td class="tdwhiteM" width="70">
                             <input name='commentUserId' type='hidden' value='<%=commentData.get("user_id")%>'>
                             <input name='commentUserName' type='hidden' value='<%=commentData.get("user_name")%>'>
                             <input name='commentLineOrder' type='hidden' value='<%=commentData.get("line_order")%>'>
                             <input name='commentScheduleID' type='hidden' value='<%=StringUtil.checkReplaceStr( commentData.get("schedule_id"), "-") %>'>
                             <%=commentData.get("user_name")%>
                         </td>
                     </tr>
             <%
                  }
              }
              %>
            </table>
            </div>
            <table border="0" cellspacing="0" cellpadding="0" width="695">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            </td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02124") %><%--연계산출물--%></td>
          <td colspan="5" class="tdwhiteL0">
            <table border="0" cellspacing="0" cellpadding="0" width="695">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="695" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                <td width="350" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
              </tr>
            </table>
            <div style="height=54;width=695;overflow-x:hidden;overflow-y:auto;" class="table_border">
            <table width="695" cellpadding="0" cellspacing="0">
             <%
                 KETProjectDocument pDoc = null;
                ContentItem item = null;
                ApplicationData pAppData = null;
                String pUrl = null;
                int docCount = linkQr.size();
                while( linkQr.hasMoreElements() )
                {
                    pDoc =   (KETProjectDocument)linkQr.nextElement();
              %>
              <tr>
                      <td width="40" class="tdwhiteM"><%=docCount-- %></td>
                      <td width="343" class="tdwhiteL"><a href="javascript:viewRelDoc('<%=CommonUtil.getOIDString(pDoc) %>');"><%=pDoc.getTitle() %></a></td>
                      <td width="149" class="tdwhiteM"><%=pDoc.getDeptName() %></td>
                      <td width="130" class="tdwhiteM"><%=pDoc.getCreatorFullName() %></td>
              </tr>
              <%
                }
             %>

            </table>
            </div>
            <table border="0" cellspacing="0" cellpadding="0" width="695">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
          <td colspan="5" class="tdwhiteL0">
            <table border="0" cellspacing="0" cellpadding="0" width="695">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="695" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="40" class="tdgrayM">No</td>
                <td width="630" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
              </tr>
            </table>
            <div style="height=54;width=695;overflow-x:hidden;overflow-y:auto;" class="table_border">
            <table width="695" cellpadding="0" cellspacing="1">
             <%
             if( attachFileList.size() > 0 )
             {
               int attachCnt = attachFileList.size();
               ContentInfo cntInfo = null;
               ContentItem ctf = null;
               String appDataOid = "";
               String url = "";
                 for( int fileCnt=0; fileCnt < attachFileList.size() ; fileCnt++ )
                 {
                     cntInfo = (ContentInfo)attachFileList.elementAt(fileCnt);
                     ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
                    appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                    //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                    url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                    url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
             %>
                      <tr>
                           <td width="40" class="tdwhiteM"><%=attachCnt-- %></td>
                           <td width="630" class="tdwhiteL0"><%=url %></td>
                      </tr>
             <%
                  }
             }
             %>
            </table>
            </div>
            <table border="0" cellspacing="0" cellpadding="0" width="695">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>

</form>
</body>
</html>
