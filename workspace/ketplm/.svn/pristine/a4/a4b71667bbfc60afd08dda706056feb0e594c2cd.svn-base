<%@page import="e3ps.groupware.company.People"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
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
                             ,e3ps.groupware.company.*
                             ,e3ps.common.code.NumberCodeHelper"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="plan" class="e3ps.ecm.entity.KETMoldChangePlan" scope="request"/>
<jsp:useBean id="commentList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
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
    People people = PeopleHelper.manager.getPeople(loginUser.getName());
    String DeptName = "";
    if ( people.getDepartment() != null ) {
        DeptName = people.getDepartment().getName();
    }

    boolean isDrFlag = true;

    if(StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getProdDwgPlanDate(), "yyyy-MM-dd"),"-").equals("-")      &&
       StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getMoldChangePlanDate(),  "yyyy-MM-dd"),"-").equals("-") &&
       StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getStorePlanDate(), "yyyy-MM-dd" ),"-").equals("-")           &&
       StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getWorkPlanDate(), "yyyy-MM-dd" ),"-").equals("-")           &&
       StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getAssemblePlanDate(), "yyyy-MM-dd" ),"-").equals("-")    &&
       StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getTryPlanDate(), "yyyy-MM-dd" ),"-").equals("-")              &&
       StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getTestPlanDate(), "yyyy-MM-dd" ),"-").equals("-")            &&
       StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getApprovePlanDate(), "yyyy-MM-dd"),"-").equals("-"))
    {
        isDrFlag = false;
    }

    boolean ismodifyFlag = false;
    boolean isAuthFlag = false;
    if(StringUtil.checkNull(plan.getScheduleStatus()).equals("C")){
        if(StringUtil.checkReplaceStr(plan.getAttribute1() ,"-").equals("Yes") && StringUtil.checkReplaceStr(plan.getAttribute2() ,"-").equals("-")){
            ismodifyFlag = true;
        }
    }else{
        ismodifyFlag = true;
        if(StringUtil.checkReplaceStr(plan.getAttribute1() ,"-").equals("No") && ( "합격".equals(StringUtil.checkNull(plan.getMeasureType())) ||   "합격".equals(StringUtil.checkNull(plan.getResult()))) ){
            ismodifyFlag = false;
        }

    }

    if(DeptName.equals("제품개발1팀") || DeptName.equals("제품개발2팀") || DeptName.equals("제품개발3팀") || DeptName.equals("제품개발4팀") ||
       DeptName.equals("사출금형개발팀") || DeptName.equals("프레스금형개발팀") || DeptName.equals("금형수리팀") || DeptName.equals("생산관리팀") ||
       DeptName.equals("금형부품팀") || DeptName.equals("구매2팀") || DeptName.equals("생산1팀") || DeptName.equals("생산2팀") || DeptName.equals("생산3팀") ||
       DeptName.equals("초류관리팀")  || DeptName.equals("나노정밀") ){
        isAuthFlag = true;
    }


%>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<style type="text/css">
<!--
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
-->
</style>

<script type="text/javascript">
<!--
function goModify()
{
    var form = document.ViewPlanForm;

    form.cmd.value = "ModifyView";

    form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
    form.submit();
}

function goDelete()
{
     var form = document.ViewPlanForm;

     if( confirm("<%=messageService.getString("e3ps.message.ket_message", "01707") %><%--삭제하시겠습니까?--%>") )
     {
         form.cmd.value = "Delete";

         form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
        form.submit();
    }
}

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

function duplicateCheck(userId) {

    var tBody = document.getElementById("commentTable");
    var rowsLen = tBody.rows.length;
    if(rowsLen > 0) {
        var userIdList= document.forms[0].commentUserId;
        if( userIdList != null )
        {
            var leng = userIdList.length;
            if(leng > 0 ) {
                for(var i = 0; i < leng; i++) {
                    if(userIdList[i].value == userId) {
                        return true;
                    }
                }
            } else {
                if(userIdList.value == userId) {
                    return true;
                }
            }
        }
    }
    return false;
}

function insertCommentLine()
{
        var innerCell;

        var filePath = "filePath";
        var filehtml = "";
        var str = "";

        var tableRows = commentTable.rows.length;
        var newTr = commentTable.insertRow(tableRows);
        newTr.height="23";
        newTr.onmouseover=function(){commentTable.clickedRowIndex=this.rowIndex};

//    if( !duplicateCheck('<//%=loginUser.getName()%>') )
//      {
            for( var k=0 ; k < 4 ; k++ )
            {
                if(k == 0)
                {
                    newTd = newTr.insertCell(newTr.cells.length);
                    newTd.className = "tdwhiteL";
                    newTd.innerHTML = '<%=DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"yyyy-MM-dd")%>';
                }
                else if(k == 1)
                {
                    newTd = newTr.insertCell(newTr.cells.length);
                    newTd.className = "tdwhiteM";
                    newTd.innerHTML  = "<input name='planComment' type='text' class='txt_field'  style='width:99%'>";
                }
                else if(k == 2)
                {
                    newTd = newTr.insertCell(newTr.cells.length);
                    newTd.className = "tdwhiteM";
                    str = "<input name='commentUserId' type='hidden' value='<%=loginUser.getName()%>'>";
                    str += "<input name='commentUserName' type='hidden' value='<%=loginUser.getFullName()%>'>";
                    str += "<input name='commentLineOrder' type='hidden' value=''>";
                    str += "<input name='commentScheduleID' type='hidden' value=''>";
                    str += "<%=loginUser.getFullName()%>";
                    newTd.innerHTML = str;
                }
                else if(k ==3)
                {
                    newTd = newTr.insertCell(newTr.cells.length);
                    newTd.className = "tdwhiteM0";
                    str = "<a href=\"javascript:commentSave();\"><img name='img_prod_plan_date' src='/plm/portal/images/icon_file.gif' border='0'></a>&nbsp;&nbsp;";
                    str += "<a href=\"javascript:commentDelete();\"><img name='img_prod_plan_date' src='/plm/portal/images/icon_delete2.gif' border='0'></a>";
                    newTd.innerHTML = str;
                }
            }
//    }
}
function commentSave()
{
    var targetTable = document.getElementById("commentTable");
    var form = document.forms[0];

    var tableRows = targetTable.rows.length;
    var pos = targetTable.clickedRowIndex;

    if( tableRows > 1 )
    {

        if( form.planComment[pos].value != '' )
        {
             form.currentUserId.value =  form.commentUserId[pos].value;
             form.currentUserName.value =  form.commentUserName[pos].value;
             form.currentUserComment.value =  form.planComment[pos].value;
             form.currentUserLineOrder.value =  form.commentLineOrder[pos].value;
             form.cmd.value = "CommentSave";
            form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
            form.submit();
        }
        else
        {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00125") %><%--Comment를 작성하세요--%>");
            return;
        }
    }
    else
    {
        if( form.planComment.value != '' )
        {
            form.currentUserId.value =  form.commentUserId.value;
             form.currentUserName.value =  form.commentUserName.value;
             form.currentUserComment.value =  form.planComment.value;
             form.currentUserLineOrder.value =  form.commentLineOrder.value;
            form.cmd.value = "CommentSave";
            form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
            form.submit();
        }
        else
        {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00125") %><%--Comment를 작성하세요--%>");
            return;
        }
    }
}

function commentDelete()
{
    if(confirm("<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>") ){
        var targetTable = document.getElementById("commentTable");
        var form = document.forms[0];

        var tableRows = targetTable.rows.length;
        var pos = targetTable.clickedRowIndex;

        if( tableRows > 1 )
        {
             if(  form.commentScheduleID[pos].value != '' ){
                 form.currentUserId.value =  form.commentUserId[pos].value;
                 form.currentUserLineOrder.value =  form.commentLineOrder[pos].value;
                form.cmd.value = "CommentDelete";
                form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
                form.submit();
             }else{
                 document.getElementById("commentTable").deleteRow(pos);
                 alert('<%=messageService.getString("e3ps.message.ket_message", "02518") %><%--정상적으로 처리되었습니다--%>');
             }
        }
        else
        {
             if(  form.commentScheduleID.value != '' ){
                form.currentUserId.value =  form.commentUserId.value;
                form.currentUserLineOrder.value =  form.commentLineOrder.value;

                form.cmd.value = "CommentDelete";
                form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
                form.submit();
             }else{
                document.getElementById("commentTable").deleteRow(0);
                alert('<%=messageService.getString("e3ps.message.ket_message", "02518") %><%--정상적으로 처리되었습니다--%>');
             }
        }
    }
}

function goCompleted()
{
    <%if(isDrFlag){%>
        if(confirm("<%=messageService.getString("e3ps.message.ket_message", "03302") %><%--등록 하시겠습니까?--%>") ){
            var form = document.forms[0];
            form.cmd.value = "Completed";
            form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
            form.submit();
            document.forms[0].prePage.value = "CN";
            alert('<%=messageService.getString("e3ps.message.ket_message", "02518") %><%--정상적으로 처리되었습니다--%>');
        }
    <%}else{%>
    alert('<%=messageService.getString("e3ps.message.ket_message", "01357") %><%--먼저 일정정보의 계획일자를 1개 이상 입력하세요--%>');
    <%}%>
}


-->
</script>
</head>
<body class="popup-background popup-space">
<form name="ViewPlanForm"  method="post">
<input type="hidden"  name="cmd"  value="View">
<input type= "hidden" name ="oid"  value="<%=CommonUtil.getOIDString( plan )%>" >
<input type= "hidden" name ="scheduleId"  value="<%=plan.getScheduleId()%>" >
<input type="hidden"  name="prePage"  value="<%=prePage %>">
<input type="hidden"  name="currentUserId" >
<input type="hidden"  name="currentUserName" >
<input type="hidden"  name="currentUserComment" >
<input type="hidden"  name="currentUserLineOrder" >
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02087") %><%--양산금형 일정 상세조회--%>
                
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
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                 <%
                 if( isOwner || isAdmin )
                 {
                    if( "".equals(StringUtil.checkNull(plan.getScheduleStatus())) ) {
                 %>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goCompleted();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01336") %><%--등록일자--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td><td width="5">&nbsp;</td>
                  <%
                      }
                    /*if( !isAdmin && ismodifyFlag && ("".equals(StringUtil.checkNull(StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getTestActualDate(), "yyyy-MM-dd" ),"")))
                      || ( !"합격".equals(StringUtil.checkNull(plan.getMeasureType())) && "".equals(StringUtil.checkNull(plan.getFailAction())))
                      || ( !"".equals(StringUtil.checkNull(plan.getFailAction())) && "".equals(StringUtil.checkNull(plan.getResult())))) ){*/
                    if(!isAdmin && ismodifyFlag){
                  %>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goModify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <%
                    if( "".equals(StringUtil.checkNull(plan.getScheduleStatus())) ) {
                %>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <%} %>
                <%
                     }else if(isAdmin){
                 %>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goModify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                 <%
                     }
                 }else if(isAuthFlag && ismodifyFlag && ! "".equals(StringUtil.checkNull(plan.getScheduleStatus()))){
                %>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goModify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <%
                }
                %>
                <%-- <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goList();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378") %>목록</a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td> --%>
              <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
         <tr>
          <td width="110" class="tdblueL">Die No</td>
          <td width="155" class="tdwhiteL"><a href="javascript:viewPart('<%=EcmUtil.getLastestPartOid(plan.getDieNo())%>');"><%=plan.getDieNo() %></a></td>
          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01337") %><%--등록자--%></td>
          <td width="140" class="tdwhiteL"><%=plan.getOwner().getFullName()+" ("+plan.getDeptName()+")" %></td>
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
          <td width="85" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01792") %><%--생산처 구분--%></td>
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
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00840") %><%--고객사4M--%></td>
          <td width="155" class="tdwhiteL"><%=StringUtil.checkReplaceStr(plan.getAttribute1() ,"-") %></td>
          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00056") %><%--4M완료일--%></td>
          <td colspan="3" width="140" class="tdwhiteL0"><%=StringUtil.checkReplaceStr(plan.getAttribute2() ,"-")%></td>
        </tr>
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01946") %><%----%></td>
          <td class="tdwhiteL0" colspan=5>
          <textarea name="modify_desc" style='overflow: auto; width:98%; height:50px; padding: 10;' class="fm_area" readonly><%=StringUtil.checkNull(plan.getModifyDesc())%></textarea>
          </td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02370") %><%--일정정보--%></td>
          <td colspan="5" class="tdwhiteL0"><table border="0" cellspacing="0" cellpadding="0" width="98%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
                <tr>
                 <td width="30" class="tdgrayM">&nbsp;</td>
                <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02585") %><%--제품도--%></td>
                <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01085") %><%--금형설계--%></td>
                <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01097") %><%--금형입고--%>
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
               <td width="30" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
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
                    <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
                        <tr>
                            <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02899") %><%--측정구분--%></td>
                            <%if(StringUtil.checkReplaceStr(plan.getMeasureType(),"-").equalsIgnoreCase("불합격")) {  %>
                            <td width="93" class="tdwhiteM"><font color=red><%=StringUtil.checkReplaceStr(plan.getMeasureType(),"-") %></font></td>
                            <%}else if(StringUtil.checkReplaceStr(plan.getMeasureType(),"-").equalsIgnoreCase("합격")) { %>
                            <td width="93" class="tdwhiteM"><font color=blue><%=StringUtil.checkReplaceStr(plan.getMeasureType(),"-") %></font></td>
                            <%}else{ %>
                            <td width="93" class="tdwhiteM"><%=StringUtil.checkReplaceStr(plan.getMeasureType(),"-") %></font></td>
                            <%} %>
                              <td width="80" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "01628") %><%--불합격 조치--%></td>
                            <td width="93" class="tdwhiteM"><%=StringUtil.checkReplaceStr(plan.getFailAction(),"-") %></td>
                              <td width="80" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "00747") %><%--결과--%></td>
                              <%if(StringUtil.checkReplaceStr(plan.getResult(),"-").equalsIgnoreCase("합격")) {  %>
                            <td width="93" class="tdwhiteM"><font color=blue><%=StringUtil.checkReplaceStr(plan.getResult(),"-") %></font></td>
                            <%}else{ %>
                            <td width="93" class="tdwhiteM"><%=StringUtil.checkReplaceStr(plan.getResult(),"-") %></td>
                            <%} %>
                              <td width="80" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "02625") %><%--제품측정일--%></td>
                            <td width="94" class="tdwhiteM"><%=StringUtil.checkReplaceStr(DateUtil.getTimeFormat( plan.getMeasureDate(), "yyyy-MM-dd" ),"-") %>&nbsp;</td>
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
            <td colspan="5" class="tdwhiteL0"><textarea name="plan_desc" style='overflow: auto; width:98%; height:50px; padding: 10;' class="fm_area"  readonly><%=StringUtil.checkNull(plan.getPlanDesc()) %></textarea></td>
        </tr>
        <tr>
            <td width="100" class="tdblueL">Comment</td>
             <td colspan="5" class="tdwhiteL0">
            <table border="0" cellspacing="0" cellpadding="0" width="695">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:insertCommentLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
             <table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                  <td width="91" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                <td width="496" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
                <td width="77" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01945") %><%--수정/삭제--%></td>
              </tr>
            </table>
            <div style="height:50;width:98%;overflow-x:hidden;overflow-y:auto;" class="table_border">
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
                         <td class="tdwhiteM0" width="93">
                         <%if( loginUser.getName().equals( commentData.get("user_id") ) ){ %>
                         <a href="javascript:commentSave();"><img name='img_prod_plan_date' src='/plm/portal/images/icon_file.gif' border='0'></a>&nbsp;&nbsp;
                         <a href="javascript:commentDelete();"><img name='img_prod_plan_date' src='/plm/portal/images/icon_delete2.gif' border='0'></a>
                         <%}else{ %>
                         &nbsp;
                         <%} %>
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
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                <td width="350" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
              </tr>
            </table>
            <div style="height:54;width:98%;overflow-x:hidden;overflow-y:auto;" class="table_border">
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
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="40" class="tdgrayM">No</td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
              </tr>
            </table>
            <div style="height:54;width:98%;overflow-x:hidden;overflow-y:auto;" class="table_border">
            <table width="100%" cellpadding="0" cellspacing="1">
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
                           <td class="tdwhiteM"><%=url %></td>
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
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
