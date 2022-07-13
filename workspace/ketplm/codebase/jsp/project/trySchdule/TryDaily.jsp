<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.trySchdule.TrySchdule"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleData"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<%@page import="e3ps.project.trySchdule.beans.TryPlanData"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.trySchdule.beans.TryPlanComparator"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleAuth"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.project.MoldItemInfo"%><%@page import="e3ps.common.web.WebUtil"%>
<%@page import="e3ps.project.material.MoldMaterial"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<html>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%


    String displ = request.getParameter("displ");
    if(displ != null &&  displ.length() > 0){
        displ = request.getParameter("displ");
        WebUtil.setCookie(response, "tryType", displ);
    }else{
        displ = WebUtil.getCookie(request, "tryType");
        //Kogger.debug("displ = " + displ);
        if(displ == null){
            displ = "Press";
        }
    }

    String popup = request.getParameter("popup");
    String tmpPopUp = "";
    if(popup != null && !popup.equals("")) {
        tmpPopUp = "&popup=popup";
    }

    String date = request.getParameter("tagetDay");
    if(date.length() < "yyyy-MM-dd".length()){
        StringBuffer sp = new StringBuffer(date);
        sp.insert("yyyy-MM-dd".length() - 2, '0');
        date = sp.toString();
    }

    String sortKey = request.getParameter("sortKey");

    String dsc = request.getParameter("dsc");



    if(sortKey == null){
        sortKey = "dieNo";
    }

    //Kogger.debug("dsc === " + dsc);
    if(dsc == null){
        dsc = "false";
    }

    String imgDsc = "(▲)";
    if(dsc.equals("false")){
        imgDsc = "(▼)";
    }

    TryPlanComparator  tc = new TryPlanComparator(sortKey, dsc);


    QueryResult rs = null;
    if(displ.equals("Mold")){
        rs = TrySchduleHelper.getTotalDayTry(date,"", "", TrySchduleHelper.MOLD);
    }else if(displ.equals("Press")){
        rs = TrySchduleHelper.getTotalDayTry(date, "", "", TrySchduleHelper.PRESS);
    }

    String dayString = date;
    StringTokenizer st = new StringTokenizer(date, "-");

    SimpleDateFormat tranSimpleFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date d = tranSimpleFormat.parse(date);
    if(st.countTokens() == 3){
        dayString = "";
        dayString = messageService.getString("e3ps.message.ket_message", "00015", st.nextToken())/*{0}년*/;
        dayString += " ";
        dayString += messageService.getString("e3ps.message.ket_message", "00039", d.getMonth()+1)/*{0}월*/;
        dayString += " ";
        dayString += messageService.getString("e3ps.message.ket_message", "00138", d.getDate())/*{0}일*/;
    }


%>
<head>
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 10px;
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

function create(day){
    var url="/plm/jsp/project/trySchdule/CreateTry.jsp?tagetDay=" + day + "<%=tmpPopUp %>";
    openOtherName(url, "popup", 700, 580, "status=yes,scrollbars=no,resizable=yes");
}

function view(oid){
    var url="/plm/jsp/project/trySchdule/TryView.jsp?oid=" + oid;
    openOtherName(url, "", 700, 600, "status=yes,scrollbars=no,resizable=yes");
}

function tryPlanAction(){
    if(confirm('<%=messageService.getString("e3ps.message.ket_message", "00537") %><%--Try 확정 하시겠습니까?--%>')){
        document.forms[0].submit();
    }
}

function moveDay(){
    //var date = parent.bottomLeftFrame.tagetDay.value;
    var date = parent.bottomLeftFrame.document.getElementsByName("tagetDay")[0].value;
    location.href = "/plm/jsp/project/trySchdule/TryDaily.jsp?tagetDay=" + date + "<%=tmpPopUp %>";
}

function moveWeek(){
    //var date = parent.bottomLeftFrame.tagetDay.value;
    var date = parent.bottomLeftFrame.document.getElementsByName("tagetDay")[0].value;
    location.href = "/plm/jsp/project/trySchdule/TryWeek.jsp?date=" + date + "<%=tmpPopUp %>";
}

function moveMonth(){
    //var date = parent.bottomLeftFrame.tagetDay.value;
    var date = parent.bottomLeftFrame.document.getElementsByName("tagetDay")[0].value;
    location.href = "/plm/jsp/project/trySchdule/TryCalendar.jsp?date=" + date + "<%=tmpPopUp %>";
}

function sorting(key){

    document.forms[0].action = "/plm/jsp/project/trySchdule/TryDaily.jsp";
    if(key == document.forms[0].sortKey.value){
        if(document.forms[0].dsc.value == "false"){

            document.forms[0].dsc.value = "true";

        }else{
            document.forms[0].dsc.value = "false";
        }
    }else{
        document.forms[0].dsc.value = "false";
    }
    document.forms[0].sortKey.value = key;
    document.forms[0].submit();
}

function display(){

    document.forms[0].action = "/plm/jsp/project/trySchdule/TryDaily.jsp";
    document.forms[0].submit();
}

function taskView(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1010,800);
}
//-->
</script>
</head>
<body>

<form method=post action="/plm/jsp/project/trySchdule/TryPlanAction.jsp">

<input type=hidden name=tagetDay value="<%=date%>">
<input type=hidden name=sortKey value="<%=sortKey%>">
<input type=hidden name=dsc value="<%=dsc%>">
<input type=hidden name=popup value='<%=popup%>'>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top"><table width="95%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01017") %><%--금형 Try관리--%></td>
                        <%if(!("popup".equals(popup))){ %>
                        <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01017") %><%--금형 Try관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02331") %><%--일간--%></td>
                        <%} %>
                    </tr>
                </table>
                </td>
            </tr>
            <tr>
                <td class="head_line"></td>
            </tr>
            <tr>
                <td class="space10"></td>
            </tr>
        </table>
        <table border="0" width="95%" cellspacing="0" cellpadding="0">
          <tr>
            <td width="80%">
              <table>
                <tr>
					<td width="5"></td>
					<td><table border="0" cellspacing="0" cellpadding="0">
					    <tr>
					      <td><img src="/plm/portal/images/tab_1.png"></td>
					      <td background="/plm/portal/images/tab_3.png"><a href="#" onclick="javascript:moveDay();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02331") %><%--일간--%></a></td>
					      <td><img src="/plm/portal/images/tab_2.png"></td>
					    </tr>
					  </table></td>
					<td><table border="0" cellspacing="0" cellpadding="0">
					    <tr>
					      <td><img src="/plm/portal/images/tab_4.png"></td>
					      <td background="/plm/portal/images/tab_6.png"><a href="#" onclick="javascript:moveWeek();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02670") %><%--주간--%></a></td>
					      <td><img src="/plm/portal/images/tab_5.png""></td>
					    </tr>
					  </table></td>
					<td><table border="0" cellspacing="0" cellpadding="0">
					    <tr>
					      <td><img src="/plm/portal/images/tab_4.png"></td>
					      <td background="/plm/portal/images/tab_6.png"><a href="#" onclick="javascript:moveMonth();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02225") %><%--월간--%></a></td>
					      <td><img src="/plm/portal/images/tab_5.png""></td>
					    </tr>
					  </table></td>
					<td width="60">&nbsp;</td>
					<td><table border="0" cellspacing="0" cellpadding="0">
					    <tr>
					      <td width="25" align="center">&nbsp;</td>
					      <td width="130" align="center"><b><%=dayString%></b></td>
					      <td width="25" align="right">&nbsp;</td>
					    </tr>
					  </table></td>
					<td width="20">&nbsp;</td>
					
                </tr>
              </table>
            </td>
            <td width="20%"><table border="0" align="right" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="5">&nbsp;</td>
                  <%if(TrySchduleAuth.isTryCommit(date)){ %>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:tryPlanAction()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00535") %><%--Try 확정--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table></td>
                  <td width="5">&nbsp;</td>
                  <%}
                    if(TrySchduleAuth.isCreateAuth()){
                        boolean try1 = CommonUtil.isMember("Try담당");
                        boolean try2 = CommonUtil.isMember("Try제작관리");
                        if(try1 || try2 || CommonUtil.isAdmin()){

                  %>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:create('<%=date %>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table></td>
                  <%  }

                    } %>
                </tr>
              </table></td>
          </tr>
        </table>
        <table width="95%" border="0" cellspacing="0" cellpadding="10" class="table_border2">
          <tr>
            <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="5">
                <tr>
                  <td>
                      <select name="displ" class="fm_jmp" style="width:80" onchange="javascript:display()">
                          <option value="Mold" <%if(displ.equals("Mold")){ %>selected<%} %>>Mold</option>
                          <option value="Press" <%if(displ.equals("Press")){ %>selected<%} %>>Press</option>
                      </select>
                  </td>
                  <td>&nbsp;</td>
                  <td align="right" valign="middle"><img src="/plm/portal/images/icon_try1.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02159") %><%--예정--%>&nbsp;&nbsp;<img src="/plm/portal/images/icon_try2.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%>&nbsp;&nbsp;<img src="/plm/portal/images/icon_try4.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "03228") %><%--확정--%>&nbsp;&nbsp;<img src="/plm/portal/images/icon_try3.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                  <td  class="tab_btm2"></td>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                  <td width="30" rowspan="2" class="tdblueM">No</td>
                  <%if(TrySchduleAuth.isTryCommit(date)){ %>
                  <td width="40" rowspan="2" class="tdblueM">
                  <a href = "javaScript:sorting('isTryPlan');">
                  <%=messageService.getString("e3ps.message.ket_message", "00529", "<br>") %><%--Try{0} 여부--%><%="isTryPlan".equals(sortKey) ? imgDsc : "" %></a></td>
                  <%} %>
                  <td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01100%") %><%--상태--%></td>
                  <td width="60" rowspan="2" class="tdblueM"> <a href = "javaScript:sorting('dieNo');">Die No<br><%="dieNo".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="80" rowspan="2" class="tdblueM"><a href = "javaScript:sorting('partNumber');">Part No<br><%="partNumber".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="70" class="tdblueM"><a href = "javaScript:sorting('tryType');"><%=messageService.getString("e3ps.message.ket_message", "00534") %><%--Try 종류--%><%="tryType".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="75" class="tdblueM"><a href = "javaScript:sorting('moldPlanerName');"><%=messageService.getString("e3ps.message.ket_message", "01085") %><%--금형설계--%><%="moldPlanerName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="185" class="tdblueM"><a href = "javaScript:sorting('partName');">Part Name<%="partName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="100" class="tdblueM"><a href = "javaScript:sorting('outsourcingName');"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%><%="outsourcingName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="80" class="tdblueM"><a href = "javaScript:sorting('ton');"><%=messageService.getString("e3ps.message.ket_message", "01876") %><%--설비(TON)--%><%="ton".equals(sortKey) ? imgDsc : "" %></a></td>
                </tr>
                <tr>
                  <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00528") %><%--Try 단계--%></td>
                  <td width="75" class="tdblueM"><a href = "javaScript:sorting('moldMakerName');"><%=messageService.getString("e3ps.message.ket_message", "01104") %><%--금형조립--%><%="moldMakerName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="185" class="tdblueM"><a href = "javaScript:sorting('materialName');"><%=messageService.getString("e3ps.message.ket_message", "02221") %><%--원재료명--%><%="materialName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="100" class="tdblueM"><a href = "javaScript:sorting('cavSu');"><%if(displ.equals("Mold")){%>Cavity<%}else{%>Line*Pcs<%}%><%="cavSu".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="80" class="tdblueM"><a href = "javaScript:sorting('quantity');"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%><%="quantity".equals(sortKey) ? imgDsc : "" %></a></td>
                </tr>


                <%


                Collections.sort(rs.getObjectVectorIfc().getVector(), tc);

                int total = rs.size();
                while(rs.hasMoreElements()){
                    TrySchduleData data = null;
                    Object objs[] = (Object[])rs.nextElement();
                    boolean isPrint = false;
                    if(objs[0] instanceof E3PSTask){
                        isPrint = true;

                        E3PSTask task = (E3PSTask)objs[0];
                        data = new TrySchduleData(task);

                    }else if(objs[0] instanceof TrySchdule){
                        isPrint = false;

                        TrySchdule trySchdule = (TrySchdule)objs[0];
                        data = new TrySchduleData(trySchdule);
                    }

                    String checked = "";
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

                    String oid = data.oid;
                    String color = "black";
                    String state = "";
                    if(data.isTryPlan){
                        checked = "checked";
                    }

                    if(data.dieNo != null){
                        dieNo = data.dieNo;
                    }

                    if(data.partNumber != null){
                        partNumber = data.partNumber;
                    }

                     if(data.tryType != null && data.tryType.length() > 0){
                         tryType = data.tryType;
                     }

                     if(data.moldPlanerName != null){
                         moldPlanerName = data.moldPlanerName;
                     }

                     if(data.moldMakerName != null){
                         moldMakerName = data.moldMakerName;
                     }

                     if(data.projectPlanerName != null){
                         projectPlanerName = data.projectPlanerName;
                     }

                     if(data.partName != null){
                         partName = data.partName;
                     }

                     MoldMaterial mCode = data.material;
                     if(mCode != null){
                         //materialName = data.materialName;
                         String makerName = "";
                         if(mCode.getMaterialMaker() != null){
                             makerName = mCode.getMaterialMaker().getName();
                         }

                         String typeName = "";
                         if(mCode.getMaterialType() != null){
                             typeName = mCode.getMaterialType().getName();
                         }

                         String propertyName = "";
                         if(data.property != null){
                             propertyName = data.property.getName();
                         }

                         materialName = makerName + "_" + typeName + "_" + mCode.getGrade() + "_" + propertyName;
                     }

                     if(data.outsourcingName != null){
                         outsourcingName = data.outsourcingName;
                     }

                     if(data.cavSu != null){
                         cavSu = data.cavSu;
                     }

                     if(data.ton != null){
                         ton = data.ton;
                     }

                     if(data.quantity != null){
                         quantity = data.quantity;
                     }
                    switch(data.state){

                        case TryPlanData.DELAY : {color = "red"; state="지연"; break;}
                        case TryPlanData.COMPLATED : {color = "blue"; state="완료"; break;}
                        case TryPlanData.TRYPLAN : {color = "black"; state="확정"; break;}
                        case TryPlanData.TRYNONPLAN : {color = "gray"; state="예정"; break;}
                        default: break;

                    }
                %>
                <input type=hidden name=oid value='<%=oid%>'>

                <tr>
                  <td width="30" rowspan="2" class="tdwhiteM"><%=total--%></td>
                  <%if(TrySchduleAuth.isTryCommit(date)){ %>
                  <td width="40" rowspan="2" class="tdwhiteM"><input type="checkbox" name="tryPlan" id="checkbox" value="<%=oid%>" <%=checked%>></td>
                  <%} %>
                  <td width="40" rowspan="2" class="tdwhiteM"><span class="<%=color%>"><%=state %></span></td>
                  <td width="60" rowspan="2" class="tdwhiteM"><a href="#" onclick="javascript:view('<%=oid %>');"><%=dieNo%></a></td>
                  <td width="80" rowspan="2" class="tdwhiteM"><a href="#" onclick="javascript:view('<%=oid %>');"><%=partNumber%></a></td>

                  <td width="70" class="tdwhiteM"><%=isPrint?messageService.getString("e3ps.message.ket_message", "02024")/*신규개발*/:tryType%></td>
                  <td width="75" class="tdwhiteM"><%=moldPlanerName%></td>
                  <td width="185" class="tdwhiteM" title="<%=partName%>"><div style="width:180;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=partName%></nobr></div></td>
                  <td width="100" class="tdwhiteM"><%=outsourcingName%></td>
                  <td width="80" class="tdwhiteM"><%=ton%></td>
                </tr>
                <tr>
                  <td width="70" class="tdwhiteM"><%if(isPrint){ %><a href="#" onclick="javascript:taskView('<%=oid%>');"><%=tryType %></a><%}else{ %>-<%}%></td>
                  <td width="75" class="tdwhiteM"><%=moldMakerName%></td>
                  <td width="185" class="tdwhiteM" title="<%=materialName%>"><div style="width:180;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=materialName%></nobr></div></td>
                  <td width="100" class="tdwhiteM"><%=cavSu%></td>
                  <td width="80" class="tdwhiteM"><%=quantity%></td>
                </tr>
                <tr>
              <%}%>
              </table></td>
          </tr>
        </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
