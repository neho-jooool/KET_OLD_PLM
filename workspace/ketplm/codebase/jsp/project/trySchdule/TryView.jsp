<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.trySchdule.TrySchdule"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleData"%>

<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleAuth"%><html>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    TrySchduleData data = null;
    E3PSTask task = null;
    String oid = request.getParameter("oid");
    Object obj = CommonUtil.getObject(oid);
    if(obj instanceof E3PSTask){
        task = (E3PSTask)obj;
        data = new TrySchduleData(task);
    }else if(obj instanceof TrySchdule){
        TrySchdule trySchdule = (TrySchdule)obj;
        data = new TrySchduleData(trySchdule);
    }

    TrySchduleAuth tryAuth = new TrySchduleAuth(data);

    String dieNo = "&nbsp;";
    String partNumber = "&nbsp;";
    String tryType = "&nbsp;";
    String moldPlanerName = "&nbsp;";
    String moldMakerName = "&nbsp;";
    String projectPlanerName = "&nbsp;";
    String partName = "&nbsp;";
    String materialName = "&nbsp;";
    String outsourcingName = "&nbsp;";
    String cavSu = "&nbsp;";
    String ton = "&nbsp;";
    String quantity = "&nbsp;";
    String requestorName = "&nbsp;";
    String state = "&nbsp;";
    String tryPlace = "연구소";
    String des = "";
    String dayString = "&nbsp'";

    boolean isCompleted = false;
    boolean isTryPlan = false;
    //String oid = data.oid;

    if(data.dieNo != null && data.dieNo.length() > 0){
        dieNo = data.dieNo;
    }

    if(data.partNumber != null && data.partNumber.length() > 0){
        partNumber = data.partNumber;
    }

     if(data.tryType != null && data.tryType.length() > 0){
         tryType = data.tryType;
     }

     if(data.moldPlanerName != null && data.moldPlanerName.length() > 0){
         moldPlanerName = data.moldPlanerName;
     }

     if(data.moldMakerName != null && data.moldMakerName.length() > 0){
         moldMakerName = data.moldMakerName;
     }

     if(data.projectPlanerName != null && data.projectPlanerName.length() > 0){
         projectPlanerName = data.projectPlanerName;
     }

     if(data.partName != null && data.partName.length() > 0){
         partName = data.partName;
     }

     if(data.materialName != null && data.materialName.length() > 0){
         materialName = data.materialName;
     }

     if(data.outsourcingName != null && data.outsourcingName.length() > 0){
         outsourcingName = data.outsourcingName;
     }

     if(data.cavSu != null && data.cavSu.length() > 0){
         cavSu = data.cavSu;
     }

     if(data.ton != null && data.ton.length() > 0){
         ton = data.ton;
     }

     if(data.quantity != null && data.quantity.length() > 0){
         quantity = data.quantity;
     }

     if(data.requestorName != null && data.requestorName.length() > 0){
         requestorName = data.requestorName;
     }

     if(data.state != 0){
         if(data.state == -1){
             state = "지연";
         }else if(data.state == 1){
             state = "완료";
         }else if(data.state == 2){
             state = "확정";
         }else if(data.state == 3){
             state = "예정";
         }
     }

     if(data.tryPlace != null && data.tryPlace.length() > 0){
         tryPlace = data.tryPlace;
     }

     if(data.des != null && data.des.length() > 0){
         des = data.des;
     }

     if(data.dayString != null && data.dayString.length() > 0){
         dayString = data.dayString;
     }

     if(data.isCompleted){
         isCompleted = data.isCompleted;
     }

     if(data.isTryPlan){
         isTryPlan = data.isTryPlan;
     }

     String todayStr = DateUtil.getToDay();

     boolean todayBefore = todayStr.compareTo(data.dayString) >= 0;

     String itemType = "Cavity";
     if("Press".equals(data.project.getMoldInfo().getItemType())){
         itemType = "Line*Pcs";
     }
%>

<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01015") %><%--금형 Try 일정 상세정보--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/portal/js/common.js"></script>

<%@include file="/jsp/common/multicombo.jsp" %>
<style type="text/css">
<!--
body {
    margin-left: 5px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">

function simpleSave(oid){
    var planform = document.forms[0].planDate.value;
    //openWindow('/plm/jsp/project/trySchdule/TryChange.jsp?oid='+oid", '',520,500);
    var url = "/plm/jsp/project/trySchdule/TryChange.jsp?oid="+oid+"&planDate="+planform;
    openOtherName(url, "", 520,500, "status=yes,scrollbars=no,resizable=yes");
    //document.forms[0].mode.value='simple';
    //document.forms[0].action="/plm/jsp/project/trySchdule/TrySave.jsp";
    //document.forms[0].submit();

}

function simpleSave2(){
    document.forms[0].mode.value='simple';
    document.forms[0].action="/plm/jsp/project/trySchdule/TrySave.jsp";
    document.forms[0].submit();
}

function tryDelete(){
    if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')){
        document.forms[0].mode.value='delete';
        document.forms[0].action="/plm/jsp/project/trySchdule/TrySave.jsp";
        document.forms[0].submit();
    }

}

function viewProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1010,800);
}
function tryChange(oid){
    //openWindow('/plm/jsp/project/trySchdule/TryChangeList.jsp?oid='+oid, '',520,500);
    var url = "/plm/jsp/project/trySchdule/TryChangeList.jsp?oid="+oid;
    openOtherName(url, "", 520,500, "status=yes,scrollbars=no,resizable=yes");
}
$(document).ready(function() {
    
    CalendarUtil.dateInputFormat('planDate', null);
    
});
</script>
</head>
<body>
<form>
<input type="hidden" name="mode" value=""></input>
<input type="hidden" name="oid" value="<%=oid %>"></input>
<table width="99%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01040") %><%--금형Try 일정 상세정보--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <%if(!isCompleted && (tryAuth.isTryPlaner() || tryAuth.isTryCompleter())){ %>
                <%if(tryAuth.isTryPlaner() || tryAuth.isTryCompleter()){ %>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02356") %><%--일정변경--%></td>
                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                          <%if(obj instanceof TrySchdule && tryAuth.isTryCompleter()){ %>
                          <td width="10">&nbsp;</td>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:simpleSave2();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        <%} %>
                        <td width="10">&nbsp;</td>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:simpleSave('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02356") %><%--일정변경--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr></table>
                    </td></tr>
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
                <%}%>
                <%if(obj instanceof E3PSTask){%>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                          <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02056") %><%--실제 시작일 변경--%></td>
                        <td class="tdwhiteL0" >
                            <input type="text" name="planDate" class="txt_field"  style="width:100" id="textfield7" value="<%=dayString %>">
                        </td>
                      </tr>
                    </table>
                <%}else if(obj instanceof TrySchdule){%>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <%if(tryAuth.isTryPlaner()){ %>
                           <tr>
                              <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02356") %><%--일정변경--%></td>
                            <td class="tdwhiteL0" >
                                <input type="text" name="planDate" class="txt_field"  style="width:100" id="textfield7" value="<%=dayString %>">
                            </td>
                        </tr>
                    <%}else{ %>
                        <input type="hidden" name="planDate" value="<%=dayString %>"></input>
                    <%} %>
                    <%if(tryAuth.isTryCompleter()){ %>
                        <tr>
                              <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00531") %><%--Try 완료 여부--%></td>
                              <td class="tdwhiteL0" ><input type="checkbox" name="isCompleted"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></input></td>
                        </tr>
                    <%}else{ %>
                        <input type="hidden" name="isCompleted" value=""></input>
                    <%} %>
                    </table>
                <%}%>
            <%}%>
            
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                  <td class="space10"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03">상세정보<%--일정변경--%></td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <%if(tryAuth.isTryModify()){  %>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="/plm/jsp/project/trySchdule/TryModify.jsp?oid=<%=data.oid %>" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  <%}%>
                  <%if(tryAuth.isTryDelete()){%>
                    <td width="10">&nbsp;</td>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:tryDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  <%}%>
                    <td width="10">&nbsp;</td>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:tryChange('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02365") %><%--일정변경이력--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    <td width="10">&nbsp;</td>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:self.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>

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
                <td width="20%" class="tdblueL">Die No</td>
                <td width="30%" class="tdwhiteL"><%=dieNo %></td>
                <td width="20%" class="tdblueL">Part No</td>
                <td width="30%" class="tdwhiteL0"><%=partNumber %></td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL">Part Name</td>
                <td width="30%" class="tdwhiteL"><%=partName %></td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00528") %><%--Try 단계--%></td>
                <td width="30%" class="tdwhiteL0"><%=tryType %></td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02599") %><%--제품설계--%></td>
                <td width="30%" class="tdwhiteL"><%=projectPlanerName %></td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01021") %><%--금형 설계/조립--%></td>
                <td width="30%" class="tdwhiteL0"><%=moldPlanerName + " / " + moldMakerName %></td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td width="30%" class="tdwhiteL"><%=state %></td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02221") %><%--원재료명--%></td>
                <td width="30%" class="tdwhiteL0"><%=materialName %></td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
                <td width="30%" class="tdwhiteL"><%=outsourcingName %></td>
                <td width="20%" class="tdblueL"><%=itemType %></input></td>
                <td width="30%" class="tdwhiteL0"><%=cavSu %></td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01876") %><%--설비(TON)--%></td>
                <td width="30%" class="tdwhiteL"><%=ton%></td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
                <td width="30%" class="tdwhiteL0"><%=quantity %></td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02258") %><%--의뢰자--%></td>
                <td width="30%" class="tdwhiteL"><%=requestorName %></td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00534") %><%--Try 종류--%> </td>
                <td width="30%" class="tdwhiteL0"><%=tryPlace %></td>
              </tr>
              <%if(task != null){ %>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00485") %><%--Task 이름--%></td>
                <td colspan="3" class="tdwhiteL0"><a href="#" onclick="javascript:viewProject('<%=oid %>')"><%=task.getTaskName() %></a></td>
              </tr>
              <%} %>
              <tr>
                <td width="20%" class="tdblueL" valign="middle"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
                <td width="360" colspan="3" class="tdwhiteL0" style="height:80"><pre><%=des%></pre>&nbsp;</td>
              </tr>
            </table>
            

            </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
