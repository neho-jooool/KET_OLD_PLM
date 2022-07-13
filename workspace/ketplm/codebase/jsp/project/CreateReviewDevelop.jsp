<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.session.SessionHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<%
    String oid = request.getParameter("oid");
    String rdOid = request.getParameter("rdOid");
    ReviewProject project = null;
    ReviewDevelop rd = null;
    String title = messageService.getString("e3ps.message.ket_message", "01310")/*등록*/;
    if(rdOid == null || rdOid.length() == 0){
        rdOid = "";
        title = messageService.getString("e3ps.message.ket_message", "01310")/*등록*/;
    }else{
        title = messageService.getString("e3ps.message.ket_message", "01936")/*수정*/;
        rd = (ReviewDevelop)CommonUtil.getObject(rdOid);
    }
    if(oid != null){
        project = (ReviewProject)CommonUtil.getObject(oid);
    }
    PastType[] pt = PastType.getPastTypeSet();
    DevelopType[] dt = DevelopType.getDevelopTypeSet();

    String cmd = request.getParameter("cmd");
    String pastTypeValue = request.getParameter("pastType");
    String developTypeValue = request.getParameter("developType");
    String pastDesc = request.getParameter("pastDesc");
    String improveDesc = request.getParameter("improveDesc");
    String reviewDesc = request.getParameter("reviewDesc");

    if(cmd == null) cmd = "";
    if(pastDesc == null) pastDesc = "";
    if(improveDesc == null) improveDesc = "";
    if(reviewDesc == null) reviewDesc = "";

    PastType type1 = null;
    DevelopType type2 = null;
    if(pastTypeValue == null){
        pastTypeValue = "";
    }else{
        type1 = (PastType)EnumeratedTypeUtil.toEnumeratedType(pastTypeValue);

    }
    if(developTypeValue == null){
        developTypeValue = "";
    }else{
        type2 = (DevelopType)EnumeratedTypeUtil.toEnumeratedType(developTypeValue);
    }

    if(cmd.equals("create")){
            if(rdOid.length() > 0 ){
                rd = (ReviewDevelop)CommonUtil.getObject(rdOid);
            }else{
                rd = ReviewDevelop.newReviewDevelop();
            }
            rd.setProject(project);
            rd.setPastType(type1);
            rd.setDeveloptype(type2);
            rd.setPastDesc(pastDesc);
            rd.setImproveDesc(improveDesc);
            rd.setReviewDesc(reviewDesc);
            rd = (ReviewDevelop)PersistenceHelper.manager.save(rd);
    }
        if(cmd.equals("create") && rd != null){
%>
<script>
        alert("<%=messageService.getString("e3ps.message.ket_message", "00006", title) %><%--{0} 되었습니다--%>");
        <%if("등록".equals(title)){%>
        opener.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>&command=stateChange&state=COMPLETED";
        <%
        }else{
        %>
        opener.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>&radioValue=3";
        <%}%>
        self.close();
</script>

<%
        }

    String msg = "";
    if(cmd.equals("delete")){
        if(rdOid.length() > 0 ){
            rd = (ReviewDevelop)CommonUtil.getObject(rdOid);
            PersistenceHelper.manager.delete(rd);
        }

        %>
        <script>
                alert("<%=messageService.getString("e3ps.message.ket_message", "01692") %><%--삭제 되었습니다--%>");
                opener.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>";
                self.close();
        </script>

        <%


    }


%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.PastType"%>
<%@page import="e3ps.project.DevelopType"%>
<%@page import="e3ps.project.ReviewDevelop"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.EnumeratedTypeUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/script.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>


<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<style type="text/css">
<!--
body {
    margin-top: 0px;
    margin-left:10px;
    margin-right:10px;
}
-->
</style>
<title><%=messageService.getString("e3ps.message.ket_message", "00609") %><%--개발검토--%>  <%=title%></title>

</head>
<body background="/plm/portal/images/img_common/contents_bg.gif">
<!-------------------------------------- 컨텐츠 시작 //-------------------------------------->
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<form autocomplete="off">
<input type="hidden" name="cmd">
<input type="hidden" name="rdOid" value="<%=rdOid%>">

<table border="0" cellspacing="0" cellpadding="0" width="760">
    <tr>
        <td valign="top">

<!-------------------------------------- 상단 제목 버튼 시작 //-------------------------------------->
            <table border="0" cellpadding="0" cellspacing="0" width="760">
                    <tr>
                        <td>
                            <table width="760" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td background="/plm/portal/images/logo_popupbg.png">
                              <table height="28" border="0" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00610") %><%--개발검토 결과--%>  <%=title%></td>
                                    <td width="10"></td>
                                  </tr>
                              </table>
                              </td>
                              <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                            </tr>
                              </table>
                            <table border="0" cellspacing="0" cellpadding="0" width="760">
                                <tr>
                                    <td class="space10"> </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
            </table>
             <table width="760" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                          <%if(rdOid.equals("")){ %>
                          <td>&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="Javascript:doCreateJsp();" class="btn_blue"><%=title%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                        <%}else{ %>
                          <td>&nbsp;</td>
                           <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="Javascript:doCreateJsp();" class="btn_blue"><%=title%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                          <td>&nbsp;</td>
                        <%  if(CommonUtil.isAdmin()){ %>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                           <td>&nbsp;</td>
                          <%  }
                           }%>
                        <td width="5px">&nbsp;</td>   
                        <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table></td>
                        </td>
                    </tr>
                    </table>
                </td>
              </tr>
            </table>

<!-------------------------------------- 상단 제목 버튼 끝 //-------------------------------------->

            <table border="0" cellpadding="0" cellspacing="0" width="760">
                <tr>
                    <td>

            <!------------------------------ 본문 시작 //------------------------------>
                        <table border="0" cellspacing="0" cellpadding="0" >
                            <tr>
                                <td class="space10"> </td>
                            </tr>
                        </table>

                        <table border="0" cellpadding="0" cellspacing="0" width="760">
                            <tr>
                                <td class="tab_btm2"></td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="760">
                            <COL width="18%"><COL width="32%"><COL width="15%"><COL width="35%">
                            <tr>
                                <td class="tdblueM">Project No</td>
                                <td class="tdwhiteL"><%=project.getPjtNo()%></td>
                                <td class="tdblueM">Project Name</td>
                                <td class="tdwhiteL" title="<%=project.getPjtName()%>">
                                <div style="width:250;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">&nbsp;
                                    <nobr>
                                    <%=project.getPjtName()%>
                                    </nobr>
                                </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00898") %><%--과거차문제점--%><span class="red">*</span></td>
                                <td class="tdwhiteL">
                                    <select name="pastType">
                                        <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                                        <%
                                        String checkPt = "";
                                        if(rd != null){
                                            checkPt = rd.getPastType().getStringValue();
                                        }
                                        for( int i=0; i<pt.length; i++ ) {
                                            if( !pt[i].isSelectable() ) continue;
                                        %>
                                        <option value="<%=pt[i].getStringValue()%>" <%=(checkPt.equals(pt[i].getStringValue()))?"selected":"" %> ><%=pt[i].getDisplay(messageService.getLocale())%></option>

                                        <%
                                        }
                                        %>
                                    </select>
                                </td>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01440") %><%--문제점 개선안--%><span class="red">*</span></td>
                                <td class="tdwhiteL">
                                    <select name="developType">
                                        <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                                        <%
                                        String checkDt = "";
                                        if(rd != null){
                                            checkDt = rd.getDeveloptype().getStringValue();
                                        }

                                        for( int i=0; i<dt.length; i++ ) {
                                            if( !dt[i].isSelectable() ) continue;
                                        %>
                                        <option value="<%=dt[i].getStringValue()%>" <%=(checkDt.equals(dt[i].getStringValue()))?"selected":"" %>><%=dt[i].getDisplay(messageService.getLocale())%></option>
                                        <%
                                        }
                                        %>
                                    </select>
                                </td>
                            </tr>
                            <%
                            String pd = "";
                            String id = "";
                            String vd = "";
                            if(rd != null){
                                if(rd.getPastDesc() != null) { pd = rd.getPastDesc(); }
                                if(rd.getImproveDesc() != null) { id = rd.getImproveDesc(); }
                                if(rd.getReviewDesc() != null) { vd = rd.getReviewDesc(); }
                            }
                            %>
                            <tr>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00896") %><%--과거차 문제점 세부--%></td>
                                <td colspan="5" class="tdwhiteL0"><textarea name="pastDesc" style="width:100%" rows="5" class="fm_area" onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)"><%=pd%></textarea></td>
                            </tr>
                            <tr>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01441") %><%--문제점 개선안 세부--%></td>
                                <td colspan="5" class="tdwhiteL0"><textarea name="improveDesc" style="width:100%" rows="5" class="fm_area" onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)"><%=id%></textarea></td>
                            </tr>
                            <tr>
                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00720") %><%--검토 내용--%> </td>
                                <td colspan="5" class="tdwhiteL0"><textarea name="reviewDesc" style="width:100%" rows="5" class="fm_area" onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)"><%=vd%></textarea></td>
                            </tr>

                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="99%">
                            <tr>
                                <td class="space5"> </td>
                            </tr>
                        </table>

                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td height="30" valign="bottom">
            <table width="760" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10">&nbsp;</td>
                <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="760" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
              </tr>
            </table>
        </td>
      </tr>
</table>



<!-- Layer begin -->
<%@include file="/jsp/common/AutoCompleteLayer.jsp"%>
<!-- Layer end -->

</form>
</body>
</HTML>

<script>

    function doCreateJsp(){
        if(!checkValidate()){
            return;
        }
        <%if("등록".equals(title)){ %>
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03264", title) %><%--{0} 및 프로젝트를 완료  하시겠습니까?--%>")) {
            return;
        }
        <%}else{%>
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03266", title) %><%--{0} 하시겠습니까?--%>")) {
            return;
        }
        <%}%>

        document.forms[0].cmd.value = "create";
        document.forms[0].method = "post";
        document.forms[0].submit();

    }
    function checkValidate(){
        var form = document.forms[0];
        if(form.pastType.value == ""){
            alert("<%=messageService.getString("e3ps.message.ket_message", "00897") %><%--과거차 문제점을 선택 하십시오--%>");
            return false;
        }
        if(form.developType.value == ""){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01442") %><%--문제점 개선안을 선택 하십시오--%>');
            return false;
        }

        return true;
    }
    function doDelete(){

        if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')) {
            return;
        }
        document.forms[0].cmd.value = "delete";
        document.forms[0].method = "post";
        document.forms[0].submit();

    }

</script>


