<%@ page contentType="text/html;charset=UTF-8"%>

<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="e3ps.project.outputtype.OEMPlan"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.outputtype.ModelPlan"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.beans.TaskPlanComparator"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
  String oid = request.getParameter("oid");
  Kogger.debug("oid = " + oid ) ;

  ModelPlan mp = null;

  if(oid != null){
    mp = (ModelPlan)CommonUtil.getObject(oid);
  }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02396") %><%--자동차 일정 보기--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  margin-left: 5px;
  margin-top: 0px;
  margin-right: 5px;
  margin-bottom: 0px;
}
-->
</style>
<script language="JavaScript">
<!--
function modify(oid){
//alert(oid);
  document.location.href="/plm/jsp/project/ModifyProgram.jsp?oid="+oid;
}

function delProgram(oid)
{
//alert(oid);
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03329") %><%--자동차일정을 삭제하시겠습니까?--%>")) {
      return;
    }
  document.location.href="/plm/jsp/project/ActionProgram.jsp?oid="+oid+"&cmd=Delete";
}

//-->
</script>
</head>
<body>
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td background="/plm/portal/images/logo_popupbg.png">
                                        <table height="28" border="0" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02396")%><%--자동차 일정 보기--%></td>
                                                <td width="10"></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="space10"></td>
                    </tr>
                </table>

                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01118")%><%--기본--%></td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table></td>
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
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                        <td width="240" class="tdwhiteL0" colspan=3><%=((OEMProjectType) mp.getCarType()).getMaker().getParent().getName()%></td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%>/td>
                        <td width="240" class="tdwhiteL"><%=((OEMProjectType) mp.getCarType()).getMaker().getName()%></td>
                        <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%></td>
                        <td width="240" class="tdwhiteL0"><%=mp.getCarType().getName()%></td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01744")%><%--상세 차종명--%></td>
                        <td class="tdwhiteL"><%=mp.getModelName() == null ? "&nbsp;" : mp.getModelName()%></td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03223")%><%--형태--%></td>
                        <td class="tdwhiteL0"><%=mp.getFormType() == null ? "&nbsp;" : mp.getFormType().getName()%></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="space15"></td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01782")%><%--생산량(단위:천대/년)--%></td>
                        <td align="right">&nbsp;</td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="space5"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016")%><%--{0}년차--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02830")%><%--총생산량--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 1)%><%--{0}차--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 2)%><%--{0}차--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 3)%><%--{0}차--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 4)%><%--{0}차--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 5)%><%--{0}차--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 6)%><%--{0}차--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 7)%><%--{0}차--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 8)%><%--{0}차--%></td>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 9)%><%--{0}차--%></td>
                        <td width="65" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00046", 10)%><%--{0}차--%></td>
                    </tr>
                    <tr>
                        <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01780")%><%--생산량--%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getTotal()%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getYield1() == null ? "0" : mp.getYield1()%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getYield2() == null ? "0" : mp.getYield2()%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getYield3() == null ? "0" : mp.getYield3()%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getYield4() == null ? "0" : mp.getYield4()%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getYield5() == null ? "0" : mp.getYield5()%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getYield6() == null ? "0" : mp.getYield6()%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getYield7() == null ? "0" : mp.getYield7()%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getYield8() == null ? "0" : mp.getYield8()%></td>
                        <td width="65" class="tdwhiteR"><%=mp.getYield9() == null ? "0" : mp.getYield9()%></td>
                        <td width="65" class="tdwhiteR0"><%=mp.getYield10() == null ? "0" : mp.getYield10()%></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="space15"></td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02348")%><%--일정--%></td>
                        <td align="right">&nbsp;</td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="space5"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <%
                        long mpOid = CommonUtil.getOIDLongValue(oid);

                        QuerySpec qs = new QuerySpec();
                        int idx = qs.addClassList(OEMPlan.class, true);
                        int idx2 = qs.addClassList(OEMProjectType.class, false);

                        SearchCondition sc = new SearchCondition(OEMPlan.class, "modelPlanReference.key.id", "=", mpOid);
                        qs.appendWhere(sc, new int[] { idx });

                        qs.appendAnd();

                        SearchCondition sc2 = new SearchCondition(new ClassAttribute(OEMPlan.class, "oemTypeReference.key.id"), "=", new ClassAttribute(OEMProjectType.class,
                    		    "thePersistInfo.theObjectIdentifier.id"));
                        qs.appendWhere(sc2, new int[] { idx, idx2 });

                        ClassAttribute attr1 = new ClassAttribute(OEMProjectType.class, OEMProjectType.CODE);
                        qs.appendOrderBy(new OrderBy(attr1, false), new int[] { idx2 });

                        Kogger.debug("QS--" + qs);

                        QueryResult qr = PersistenceHelper.manager.find(qs);

                        //Collections.sort(qr.getObjectVector().getVector(), new OutputTypeComparator());
                    %>
                    <tr>
                        <%
   int i = 0;
   while(qr.hasMoreElements()){
    Object[] o = (Object[])qr.nextElement();
    OEMPlan op = (OEMPlan)o[0];

    String endDate = DateUtil.getTimeFormat(op.getOemEndDate(), "yyyy-MM-dd");
    String oemName = op.getOemType().getName();


//    Kogger.debug("op.name----" + oemName);
//    Kogger.debug("op.date-----" + endDate);

    i++;
//    Kogger.debug("i === " + i );

    if(i%2==1){
      %>
                        <td width="150" class="tdblueL"><%=oemName %></td>
                        <%
          if(i==qr.size()){
          %>
                        <td width="240" colspan="3" class="tdwhiteL0"><%=endDate %>&nbsp;</td>

                        <%
          }else{
            %>
                        <td width="240" class="tdwhiteL"><%=endDate %>&nbsp;</td>
                        <%
          }

    }else{
      %>
                        <td width="150" class="tdblueL"><%=oemName %></td>
                        <td width="240" class="tdwhiteL0"><%=endDate %>&nbsp;</td>
                    </tr>
                    <tr>
                        <%

    }
    if(i==qr.size()){
        %>
                    </tr>
                    <%
    }
  }
        %>

                </table>
            </td>
        </tr>
    </table>
</body>
</html>
