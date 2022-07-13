<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.project.trySchdule.beans.TryResultCount"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.trySchdule.beans.TryPlanComparator"%>

<%@page import="e3ps.project.trySchdule.beans.TrySchduleData"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.trySchdule.TrySchdule"%>
<%@page import="e3ps.project.trySchdule.beans.TryPlanData"%><%@page import="e3ps.common.util.DateUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<title>Insert title here</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
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

function view(oid){
    var url="/plm/jsp/project/trySchdule/TryView.jsp?oid=" + oid;
    openOtherName(url, "", 700, 600, "status=yes,scrollbars=no,resizable=yes");
}

function sorting(key){

    document.forms[0].action = "/plm/jsp/project/trySchdule/TryResultList.jsp";
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

function showResultExcel2(count, startDate, endDate){
    if(count == 0){
        return;
    }
    var url = "/plm/jsp/project/trySchdule/TryResultListExcel.jsp?startDate=" + startDate + "&endDate=" + endDate + "&type=0";

    window.open(url,'',"status=1, menu=no, width=730, height=570");
    //openSameName(url, 730, 570, "status=yes,scrollbars=yes,resizable=no");
}

//-->
</script>
</head>
<body>
<form>
<%
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String type = request.getParameter("type");

    String sortKey = request.getParameter("sortKey");
    String dsc = request.getParameter("dsc");

    if(sortKey == null){
        sortKey = "dieNo";
    }

    //out.println("type === " + type);
    if(dsc == null){
        dsc = "false";
    }

    String imgDsc = "(▲)";
    if(dsc.equals("false")){
        imgDsc = "(▼)";
    }

    TryPlanComparator  tc = new TryPlanComparator(sortKey, dsc);

    QueryResult rs = TrySchduleHelper.getTaskOrTrySchduleQuerySpec(startDate, endDate, "", "");

    TryResultCount rcount = new TryResultCount();
    while (rs.hasMoreElements()) {

        Object objs[] = (Object[]) rs.nextElement();
        rcount.add(objs[0]);
    }

    Vector v = rcount.getData(Integer.parseInt(type));
    Collections.sort(v, tc);

%>
<input type=hidden name=startDate value="<%=startDate%>">
<input type=hidden name=endDate value="<%=endDate%>">
<input type=hidden name=type value="<%=type%>">

<input type=hidden name=sortKey value="<%=sortKey%>">
<input type=hidden name=dsc value="<%=dsc%>">


<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="7"></td>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01037") %><%--금형Try--%> List
                    <a href="#" onclick="javaScript:showResultExcel2('<%=rs.size()%>', '<%=startDate%>', '<%=endDate%>');">
                    <img src="/plm/portal/images/iocn_excel.png" border="0"></a>
                    </td>
                  </tr>
                </table></td>
                <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
              </tr>
            </table>
        <table>
            <tr>
                <td class="space10"></td>
            </tr>
        </table>
        <table width="680" border="0" cellspacing="0" cellpadding="10" class="table_border2">
          <tr>
            <td valign="top">
              <table border="0" cellspacing="0" cellpadding="0" width="660">
                <tr>
                  <td  class="tab_btm2"></td>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="660">
                <tr>
                  <td width="30" rowspan="2" class="tdblueM">No</td>

                  <td width="70" rowspan="2" class="tdblueM"> <a href = "javaScript:sorting('dieNo');">Die No<%="dieNo".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="80" rowspan="2" class="tdblueM"><a href = "javaScript:sorting('partNumber');">Part No<%="partNumber".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="80" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                  <td width="70" class="tdblueM"><a href = "javaScript:sorting('tryType');"><%=messageService.getString("e3ps.message.ket_message", "00534") %><%--Try 종류--%><%="tryType".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="75" class="tdblueM"><a href = "javaScript:sorting('moldPlanerName');"><%=messageService.getString("e3ps.message.ket_message", "01085") %><%--금형설계--%><%="moldPlanerName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="175" class="tdblueM"><a href = "javaScript:sorting('partName');">Part Name<%="partName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="80" class="tdblueM"><a href = "javaScript:sorting('outsourcingName');"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%><%="outsourcingName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="80" class="tdblueM"><a href = "javaScript:sorting('ton');"><%=messageService.getString("e3ps.message.ket_message", "01876") %><%--설비(TON)--%><%="ton".equals(sortKey) ? imgDsc : "" %></a></td>
                </tr>
                <tr>
                  <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00528") %><%--Try 단계--%></td>
                  <td width="75" class="tdblueM"><a href = "javaScript:sorting('moldMakerName');"><%=messageService.getString("e3ps.message.ket_message", "01104") %><%--금형조립--%><%="moldMakerName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="175" class="tdblueM"><a href = "javaScript:sorting('materialName');"><%=messageService.getString("e3ps.message.ket_message", "02221") %><%--원재료명--%><%="materialName".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="80" class="tdblueM"><a href = "javaScript:sorting('cavSu');">PCS*Pitch<%="cavSu".equals(sortKey) ? imgDsc : "" %></a></td>
                  <td width="80" class="tdblueM"><a href = "javaScript:sorting('quantity');"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%><%="quantity".equals(sortKey) ? imgDsc : "" %></a></td>
                </tr>


                <%


                Collections.sort(rs.getObjectVectorIfc().getVector(), tc);

                int total = v.size();
                for(int i = 0; i < v.size(); i++){
                    TrySchduleData data = null;
                    Object obj = (Object)v.get(i);
                    boolean isPrint = false;
                    String excucEndDate = "&nbsp;";
                    if(obj instanceof E3PSTask){
                        isPrint = true;

                        E3PSTask task = (E3PSTask)obj;
                        data = new TrySchduleData(task);
                        excucEndDate = DateUtil.getDateString(data.getTryPlanDate(task), "d") ;

                    }else if(obj instanceof TrySchdule){
                        isPrint = false;

                        TrySchdule trySchdule = (TrySchdule)obj;
                        data = new TrySchduleData(trySchdule);
                        excucEndDate = DateUtil.getDateString(trySchdule.getPlanDate(), "d") ;;
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

                     if(data.materialName != null){
                         materialName = data.materialName;
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
                <input type=hidden name=oid value='<%//=oid%>'>

                <tr>
                  <td width="30" rowspan="2" class="tdwhiteM"><%=total--%></td>


                  <td width="70" rowspan="2" class="tdwhiteM"><a href="javascript:view('<%=oid %>');"><%=dieNo%></a></td>
                  <td width="80" rowspan="2" class="tdwhiteM"><a href="javascript:view('<%=oid %>');"><%=partNumber%></a></td>
                  <td width="80" rowspan="2" class="tdwhiteM"><%=excucEndDate%></td>

                  <td width="70" class="tdwhiteM"><%=isPrint?messageService.getString("e3ps.message.ket_message", "02024")/*신규개발*/:tryType%></td>
                  <td width="75" class="tdwhiteM"><%=moldPlanerName%></td>
                  <td width="175" class="tdwhiteM"><%=partName%></td>
                  <td width="80" class="tdwhiteM" title="<%=outsourcingName%>"><div style="width:73;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=outsourcingName%></nobr></div></td>
                  <td width="80" class="tdwhiteM"><%=ton%></td>
                </tr>
                <tr>
                  <td width="70" class="tdwhiteM"><%=isPrint?tryType:"-"%></td>
                  <td width="75" class="tdwhiteM"><%=moldMakerName%></td>
                  <td width="175" class="tdwhiteM"><%=materialName%></td>
                  <td width="80" class="tdwhiteM"><%=cavSu%></td>
                  <td width="80" class="tdwhiteM"><%=quantity%></td>
                </tr>
                <tr>
              <%}%>
              </table></td>
          </tr>
        </table>
        </td>
    </tr>
    <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
