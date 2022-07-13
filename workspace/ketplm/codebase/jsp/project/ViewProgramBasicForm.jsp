<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
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
<form name="basicPrg" id="">
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="space5"></td>
    </tr>
</table>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
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
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="240" class="tdwhiteL0" colspan="3"><%=((OEMProjectType)mp.getCarType()).getMaker().getParent().getName()%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
          <td width="240" class="tdwhiteL"><%=((OEMProjectType)mp.getCarType()).getMaker().getName()%></td>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
          <td width="240" class="tdwhiteL0"><%=mp.getCarType().getName() %></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01744") %><%--상세 차종명--%></td>
          <td class="tdwhiteL"><%=mp.getModelName()==null?"&nbsp;":mp.getModelName() %></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03223") %><%--형태--%></td>
          <td class="tdwhiteL0"><%=mp.getFormType()==null?"&nbsp;":mp.getFormType().getName() %></td>
        </tr>
        <%
        QuerySpec qs1 = new QuerySpec();
        int idx1 = qs1.appendClassList(CustomerTheModelPlan.class, true);

        long longValue = CommonUtil.getOIDLongValue(oid);

        SearchCondition sc1 =  new SearchCondition(CustomerTheModelPlan.class, "roleBObjectRef.key.id", "=", longValue);
        qs1.appendWhere(sc1, new int[]{idx1});
        QueryResult qr1 = PersistenceHelper.manager.find(qs1);
        CustomerTheModelPlan link = null;
        String SUBCONTRACTOR = "";
    int qsize = qr1.size();
        while(qr1.hasMoreElements())
        {
          Object[] objs = (Object[])qr1.nextElement();
          link = (CustomerTheModelPlan)objs[0];

          if(qsize > 1){
            SUBCONTRACTOR += link.getCustomer().getName()+",";
            //out.println(link.getCustomer().getName()+",");
          }else{
            SUBCONTRACTOR += link.getCustomer().getName();
          }
          qsize--;
        }

        %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00550") %><%--W/H 업체(엔진룸/실내)--%></td>
          <td class="tdwhiteL"><%=SUBCONTRACTOR==""?"&nbsp;":SUBCONTRACTOR%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02400") %><%--자동차사 설계담당--%></td>
          <td class="tdwhiteL0"><%=mp.getPerson()==null?"&nbsp;":mp.getPerson() %></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01781") %><%--생산량(단위:천대)--%></td>
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
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016") %><%--{0}년차--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03151") %><%--합계--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 1) %><%--{0}차--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 2) %><%--{0}차--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 3) %><%--{0}차--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 4) %><%--{0}차--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 5) %><%--{0}차--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 6) %><%--{0}차--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 7) %><%--{0}차--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 8) %><%--{0}차--%></td>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 9) %><%--{0}차--%></td>
          <td width="65" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00046", 10) %><%--{0}차--%></td>
        </tr>
        <tr>
          <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01779") %><%--생산대수--%></td>
          <td width="65" class="tdwhiteR"><%=mp.getTotal()%></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield1()==null?"0":mp.getYield1() %></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield2()==null?"0":mp.getYield2() %></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield3()==null?"0":mp.getYield3() %></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield4()==null?"0":mp.getYield4() %></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield5()==null?"0":mp.getYield5() %></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield6()==null?"0":mp.getYield6() %></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield7()==null?"0":mp.getYield7() %></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield8()==null?"0":mp.getYield8() %></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield9()==null?"0":mp.getYield9() %></td>
          <td width="65" class="tdwhiteR"><%=mp.getYield10()==null?"0":mp.getYield10() %></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02348") %><%--일정--%></td>
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
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <%
      long mpOid = CommonUtil.getOIDLongValue(oid);

      QuerySpec qs = new QuerySpec();
      int idx = qs.addClassList(OEMPlan.class, true);
      int idx2 = qs.addClassList(OEMProjectType.class, false);

      SearchCondition sc = new SearchCondition(OEMPlan.class, "modelPlanReference.key.id", "=", mpOid);
      qs.appendWhere(sc, new int[] {idx});

      qs.appendAnd();

      SearchCondition sc2 = new SearchCondition(new ClassAttribute(OEMPlan.class, "oemTypeReference.key.id"), "=", new ClassAttribute(OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id"));
      qs.appendWhere(sc2, new int[] {idx, idx2});

      ClassAttribute attr1 = new ClassAttribute(OEMProjectType.class, OEMProjectType.CODE);
      qs.appendOrderBy(new OrderBy(attr1, false), new int[] {idx2});

    QueryResult qr = PersistenceHelper.manager.find(qs);

   %>
  <tr>
   <%
   int i = 0;
   while(qr.hasMoreElements()){
    Object[] o = (Object[])qr.nextElement();
    OEMPlan op = (OEMPlan)o[0];

    String endDate = DateUtil.getTimeFormat(op.getOemEndDate(), "yyyy-MM-dd");
    String oemName = op.getOemType()==null?messageService.getString("e3ps.message.ket_message", "01700")/*삭제된 이벤트*/:op.getOemType().getName();
    i++;
    if(i%2==1){
      %>
          <td width="150" class="tdblueL"><%=oemName %></td>
          <%
          if(i==qr.size()){
          %>
          <td width="240"colspan="3" class="tdwhiteL0"><%=endDate %>&nbsp;</td>

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

      </table></td>
  </tr>
 <!--  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr> -->
</table>
</form>
</body>
</html>
