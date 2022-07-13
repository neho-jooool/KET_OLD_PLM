<%@page contentType="text/html; charset=UTF-8"%>

<%@page import = "java.util.*"%>
<%@page import="
            wt.fc.*,
            wt.folder.*,
            wt.lifecycle.*,
            wt.org.*,
            wt.part.*,
            wt.project.Role,
            wt.query.*,
            wt.session.*,
            wt.team.*,
            wt.vc.*,
            wt.vc.wip.*,
            wt.workflow.engine.WfActivity,
            wt.workflow.engine.WfProcess,
            wt.workflow.work.WorkItem"%>
<%@page import="
            e3ps.common.content.*,
            e3ps.common.jdf.config.Config,
            e3ps.common.jdf.config.ConfigImpl,
            e3ps.common.jdf.log.Logger,
            e3ps.common.query.*,
            e3ps.common.util.*,
            e3ps.groupware.company.*,
            e3ps.project.*,
            e3ps.project.beans.*,
            e3ps.project.historyprocess.HistoryHelper,
            e3ps.project.outputtype.OEMPlan,
            e3ps.project.outputtype.OEMProjectType,
            e3ps.sap.*,
            e3ps.wfm.entity.KETWfmApprovalMaster
            " %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<%@page import="e3ps.project.sap.ProductPrice"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>

<%
  String oid = request.getParameter("oid");
  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  String popup = StringUtil.checkNull( request.getParameter("popup") );

%>

<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
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
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

  function updateCostInfo(){
    width = 890;
    height = 400;
    var screenWidth = (screen.availWidth-width)/2;
    var screenHeight = (screen.availHeight-height)/2;
    var url = "/plm/jsp/project/UpdateCostInfo.jsp?oid=<%=oid%>";
    newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width="+width+"height="+height+",resizable=no,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(890,400);
    newWin.focus();
  }
//-->
</script>
</head>
<body>
<input type='hidden' name='popup' value="<%=popup %>">
<table width="820" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="820" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="820" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551") %><%--제품 프로젝트 상세정보--%></td>
<%if(popup == null){ %>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%><img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02401") %><%--자동차사업부--%><img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03117") %><%--프로젝트상세정보--%></td>
                <%} %>
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
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="5"></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_4.png"></td>
              <td background="../../portal/images/tab_6.png" class="btn_tab"><a href="ProjectView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></a></td>
              <td><img src="../../portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_4.png"></td>
              <td background="../../portal/images/tab_6.png" class="btn_tab"><a href="ProjectView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02328") %><%--인원 / Issue--%></a></td>
              <td><img src="../../portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_1.png"></td>
              <td background="../../portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01640") %><%--비용--%></td>
              <td><img src="../../portal/images/tab_2.png"></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_4.png"></td>
              <td background="../../portal/images/tab_6.png" class="btn_tab"><a href="ProjectView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02348") %><%--일정--%></a></td>
              <td><img src="../../portal/images/tab_5.png""></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table width="820" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_9.gif"></td>
          <td height="10" background="../../portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="../../portal/images/box_13.gif">&nbsp;</td>
          <td valign="top"><!--내용-->
      <%
        int pressCount = 0;
        int moldCount = 0;
        int handmadeCount = 0;
        int qdmCount = 0;
        int gCount = 0;

        QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject)project);
        while(rt.hasMoreElements()){
           Object[] obj = (Object[])rt.nextElement();
          MoldItemInfo miInfo = (MoldItemInfo)obj[0];

          if("Press".equals(miInfo.getItemType())) pressCount++;
          else if("Mold".equals(miInfo.getItemType())) moldCount++;
          else if("Handmade".equals(miInfo.getItemType())) handmadeCount++;
          else if("QDM".equals(miInfo.getItemType())) qdmCount++;
          else if("구매품".equals(miInfo.getItemType())) gCount++;
        }
      %>
            <table width="800" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="20"><img src="../../portal/images/icon_4.png"></td>
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00283") %><%--Item 현황--%></td>
                      <td align="right">&nbsp;</td>
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
                <td width="195" class="tdblueM">Press</td>
                <td width="195" class="tdblueM">Mold</td>
                <td width="195" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00966") %><%--구매품--%></td>
                <td width="195" class="tdblueM0">Total</td>
                    </tr>
                    <tr>
                <td width="195" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00581", pressCount) %><%--{0}개--%></td>
                <td width="195" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00581", moldCount) %><%--{0}개--%></td>
                <td width="195" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00581", gCount) %><%--{0}개--%></td>
                <td width="195" class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00581", (pressCount+moldCount+handmadeCount+qdmCount+gCount)) %><%--{0}개--%></td>
                   </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                      <td class="space15"></td>
                    </tr>
                  </table>
                  <table width="780" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="20"><img src="../../portal/images/icon_4.png"></td>
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01644") %><%--비용관리(세부내용)--%></td>
                      <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0" >
                          <tr>
                            <td align="right" valign="bottom"><%=messageService.getString("e3ps.message.ket_message", "01196") %><%--단위 : 천원--%></td>

                          <%
              ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
                          if(auth.isLatest && (auth.isPM || (auth.isCarBuse && auth.isPMO))){ %>
                            <td width="10">&nbsp;</td>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td width="80" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:updateCostInfo();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01650") %><%--비용정보 수정--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          <%}%>

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
                <td width="30" class="tdblueM" rowspan=2>No</td>
                <td width="43" class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                <td width="60" class="tdblueM" rowspan=2>Part No</td>
                <td width="60" class="tdblueM" rowspan=2>Die No</td>
                <td width="117" class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02942") %><%--투자명--%></td>
                <td width="50" class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02944") %><%--투자오더--%></td>
                <td class="tdblueM" colspan=2><%=messageService.getString("e3ps.message.ket_message", "02143") %><%--예산--%></td>
                <td class="tdblueM" colspan=4><%=messageService.getString("e3ps.message.ket_message", "02742") %><%--집행--%></td>
                <td width="60" class="tdblueM0" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02443") %><%--잔액--%></td>
                    </tr>
                    <tr>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01387") %><%--목표가--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02143") %><%--예산--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03229") %><%--확정가--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02806") %><%--초기비용--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02865") %><%--추가금형--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02834") %><%--총합--%></td>
                    </tr>
            <%
          long targetCost1 = 0;
          long targetCost2 = 0;
          long targetCost3 = 0;
          long targetCost4 = 0;
          long budget1 = 0;
          long budget2 = 0;
          long budget3 = 0;
          long budget4 = 0;
          long resultsCost1 = 0;
          long resultsCost2 = 0;
          long resultsCost3 = 0;
          long resultsCost4 = 0;
          long balanceCost1 = 0;
          long balanceCost2 = 0;
          long balanceCost3 = 0;
          long balanceCost4 = 0;

          java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
          int count =0;
          QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject)project);
          while(rtCost.hasMoreElements()){
             Object[] obj = (Object[])rtCost.nextElement();
            CostInfo costInfo = (CostInfo)obj[0];

            int budget = 0;       //예산
            int executionCost = 0;  //초기집행가
            int editCost = 0;      //추가집행가
            int totalExpense = 0;  //총집행가
            int balanceCost = 0;  //잔액


            if(costInfo.getOrderInvest() != null){
              Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
              Integer totalPriceObj = (Integer)datas.get(ProductPrice.TOTALPRICE);
              Integer initExpenseObj = (Integer)datas.get(ProductPrice.INITEXPENSE);
              Integer addExpenseObj = (Integer)datas.get(ProductPrice.ADDEXPENSE);
              Integer totalExpenseObj = (Integer)datas.get(ProductPrice.TOTALEXPENSE);

              if(totalPriceObj != null) budget = totalPriceObj.intValue();            //예산
              if(initExpenseObj != null) executionCost = initExpenseObj.intValue();    //초기집행가
              if(addExpenseObj != null) editCost = addExpenseObj.intValue();      //추가집행가
              if(totalExpenseObj != null) totalExpense = totalExpenseObj.intValue();    //총집행가
              balanceCost = budget - totalExpense;                      //잔액
            }else {
              if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));        //예산

              if(costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
                executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", ""));  //초기집행가
              else if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));    //초기집행가

              if(costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
                editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", ""));          //추가집행가

              totalExpense = executionCost + editCost;                          //총집행가
              balanceCost = budget - totalExpense;                            //잔액
            }

            if("금형".equals(costInfo.getCostType())) {
              if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                targetCost1 = targetCost1 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
              budget1 = budget1 + budget;
              resultsCost1 = resultsCost1 + executionCost + editCost;
              balanceCost1 = balanceCost1 + balanceCost;
            }else if("설비".equals(costInfo.getCostType())) {
              if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                targetCost2 = targetCost2 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
              budget2 = budget2 +budget;
              resultsCost2 = resultsCost2 + executionCost + editCost;
              balanceCost2 = balanceCost2 + balanceCost;
            }else if("JIG".equals(costInfo.getCostType())) {
              if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                targetCost4 = targetCost4 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
              budget4 = budget4 +budget;
              resultsCost4 = resultsCost4 + executionCost + editCost;
              balanceCost4 = balanceCost4 + balanceCost;
            }else {
              if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
                targetCost3 = targetCost3 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
              budget3 = budget3 +budget;
              resultsCost3 = resultsCost3 + executionCost + editCost;
              balanceCost3 = balanceCost3 + balanceCost;
            }

        %>
              <tr>
                <td class="tdwhiteM"><%=++count%></td>
                <td class="tdwhiteM"><%=costInfo.getCostType()%></td>
                <td class="tdwhiteM" title="<%=costInfo.getPartNo() != null ? costInfo.getPartNo() : "&nbsp;"%>"><div style="width:60;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=costInfo.getPartNo() != null ? costInfo.getPartNo() : "&nbsp;"%></nobr></div></td>
                <td class="tdwhiteM" title="<%=costInfo.getDieNo() != null ? costInfo.getDieNo() : "&nbsp;"%>"><div style="width:60;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=costInfo.getDieNo() != null ? costInfo.getDieNo() : "&nbsp;"%></nobr></div></td>
                <td class="tdwhiteL" title="<%=costInfo.getCostName() != null ? costInfo.getCostName() : "&nbsp;"%>">
                <div style="width:112;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr>
                <%=costInfo.getCostName() != null ? costInfo.getCostName() : "&nbsp;"%>
                </nobr></div>
                </td>
                <td class="tdwhiteR"><%=costInfo.getOrderInvest() != null ? costInfo.getOrderInvest() : "-"%></td>
                <td class="tdwhiteR"><%=costInfo.getTargetCost() != null ? nf.format( Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")) / 1000 ) : "&nbsp;"%></td>
                <td class="tdwhiteR"><%=nf.format(budget / 1000)%></td>
                <td class="tdwhiteR"><span class=green><%=costInfo.getDecideCost() != null ? nf.format( Integer.parseInt(costInfo.getDecideCost().replaceAll(",", "")) / 1000 ) : "&nbsp;"%></span></td>
                <td class="tdwhiteR"><%=nf.format(executionCost / 1000)%></td>
                <td class="tdwhiteR"><%=editCost == 0 ? "-" : nf.format(editCost / 1000)%></td>
                <td class="tdwhiteR"><%=nf.format(totalExpense / 1000)%></td>
                <td class="tdwhiteR0"><%if(balanceCost < 0) {%><span class="red"><%}%><%=nf.format(balanceCost / 1000)%><%if(balanceCost < 0) {%></span><%}%></td>
              </tr>
           <%
          }
           %>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
                  <table width="780" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="20"><img src="../../portal/images/icon_4.png"></td>
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01645") %><%--비용관리(종합)--%></td>
                      <td align="right"><%=messageService.getString("e3ps.message.ket_message", "01195") %><%--단위 : 원--%></td>
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
          <td class="space5"><table border="0" cellspacing="0" cellpadding="0" width="780">
            <tr>
              <td width="156" class="tdblueM">&nbsp;</td>
              <td width="156" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01387") %><%--목표가--%></td>
              <td width="156" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02143") %><%--예산--%></td>
              <td width="156" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
              <td width="156" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02443") %><%--잔액--%></td>
            </tr>
            <tr>
              <td width="156" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(targetCost1)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(budget1)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(resultsCost1)%></td>
              <td width="156" class="tdwhiteR0">
              <%if(balanceCost1 < 0) {%><span class="red"><%}%>
              <%=nf.format(balanceCost1)%>
              <%if(balanceCost1 < 0) {%></span><%}%>
              </td>
            </tr>
            <tr>
              <td width="156" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01874") %><%--설비--%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(targetCost2)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(budget2)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(resultsCost2)%></td>
              <td width="156" class="tdwhiteR0">
              <%if(balanceCost2 < 0) {%><span class="red"><%}%>
              <%=nf.format(balanceCost2)%>
              <%if(balanceCost2 < 0) {%></span><%}%>
              </td>
            </tr>
            <tr>
              <td width="156" class="tdblueM">JIG</td>
              <td width="156" class="tdwhiteR"><%=nf.format(targetCost4)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(budget4)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(resultsCost4)%></td>
              <td width="156" class="tdwhiteR0">
              <%if(balanceCost4 < 0) {%><span class="red"><%}%>
              <%=nf.format(balanceCost4)%>
              <%if(balanceCost4 < 0) {%></span><%}%>
              </td>
            </tr>
            <tr>
              <td width="156" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00796") %><%--경비--%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(targetCost3)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(budget3)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(resultsCost3)%></td>
              <td width="156" class="tdwhiteR0">
              <%if(balanceCost3 < 0) {%><span class="red"><%}%>
              <%=nf.format(balanceCost3)%>
              <%if(balanceCost3 < 0) {%></span><%}%>
              </td>
            </tr>
            <tr>
              <td width="156" class="tdblueM">Total</td>
              <td width="156" class="tdwhiteR"><%=nf.format(targetCost1+targetCost2+targetCost3+targetCost4)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(budget1+budget2+budget3+budget4)%></td>
              <td width="156" class="tdwhiteR"><%=nf.format(resultsCost1+resultsCost2+resultsCost3+resultsCost4)%></td>
              <td width="156" class="tdwhiteR0">
              <%if(balanceCost1+balanceCost2+balanceCost3+balanceCost4 < 0) {%><span class="red"><%}%>
              <%=nf.format(balanceCost1+balanceCost2+balanceCost3+balanceCost4)%>
              <%if(balanceCost1+balanceCost2+balanceCost3+balanceCost4 < 0) {%></span><%}%>
              </td>
            </tr>
          </table></td>
        </tr>
      </table></td>
              </tr>
            </table></td>
          <td width="10" background="../../portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_11.gif"></td>
          <td height="10" background="../../portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_12.gif"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright<%if("popup".equals(popup)){ %>_t<%} %>.html" name="copyright" width="820" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</body>
</html>
