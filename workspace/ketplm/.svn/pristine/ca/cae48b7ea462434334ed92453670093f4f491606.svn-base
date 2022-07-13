<%@ page language="java" contentType="application/vnd.ms-excel; name='excel', text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
<%@page import="e3ps.project.trySchdule.beans.TryPlanData"%>
<%@page import="e3ps.common.util.DateUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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

    if(dsc == null){
        dsc = "false";
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



<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td ><table height="28" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="7"></td>
                    <td width="20"></td>
                    <td><h2><%=messageService.getString("e3ps.message.ket_message", "01037") %><%--금형Try--%> List</h2></td>
                  </tr>
                </table></td>
                <td width="136" align="right"></td>
              </tr>
            </table>
        <table>

        </table>
        <table width="680" border="0" cellspacing="0" cellpadding="10" class="table_border2">
          <tr>
            <td valign="top">

              <table border="1" cellspacing="0" cellpadding="0" width="660">
                <tr>
                  <td width="30" rowspan="2" align="center"bgColor="#CAD7E5">No</td>

                  <td width="70" rowspan="2" align="center"bgColor="#CAD7E5">dieNo</td>
                  <td width="80" rowspan="2" align="center"bgColor="#CAD7E5">partNumber</td>
                  <td width="80" rowspan="2" align="center"bgColor="#CAD7E5"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                  <td width="70" align="center"bgColor="#CAD7E5"><%=messageService.getString("e3ps.message.ket_message", "00534") %><%--Try 종류--%></td>
                  <td width="75" align="center"bgColor="#CAD7E5"><%=messageService.getString("e3ps.message.ket_message", "01085") %><%--금형설계--%></td>
                  <td width="175" align="center"bgColor="#CAD7E5">Part Name</td>
                  <td width="80" align="center"bgColor="#CAD7E5"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
                  <td width="80" align="center"bgColor="#CAD7E5"><%=messageService.getString("e3ps.message.ket_message", "01876") %><%--설비(TON)--%></td>
                </tr>
                <tr>
                  <td width="70" align="center"bgColor="#CAD7E5"><%=messageService.getString("e3ps.message.ket_message", "00528") %><%--Try 단계--%></td>
                  <td width="75" align="center"bgColor="#CAD7E5"><%=messageService.getString("e3ps.message.ket_message", "01104") %><%--금형조립--%></td>
                  <td width="175" align="center"bgColor="#CAD7E5"><%=messageService.getString("e3ps.message.ket_message", "02221") %><%--원재료명--%></td>
                  <td width="80" align="center"bgColor="#CAD7E5">PCS*Pitch</td>
                  <td width="80" align="center"bgColor="#CAD7E5"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
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
                    String dieNo = "";
                    String partNumber = "";
                    String tryType = "";
                    String moldPlanerName = "";
                    String moldMakerName = "";
                    String projectPlanerName = "";
                    String partName = "";
                    String materialName = "";
                    String outsourcingName = "";
                    String cavSu = "";
                    String ton = "";
                    String quantity = "";

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

                <tr>
                  <td width="30" rowspan="2" class="tdwhiteM"><%=total--%></td>


                  <td width="70" rowspan="2" class="tdwhiteM"><%=dieNo%></td>
                  <td width="80" rowspan="2" class="tdwhiteM"><%=partNumber%></td>
                  <td width="80" rowspan="2" class="tdwhiteM"><%=excucEndDate%></td>
                  <td width="70" class="tdwhiteM"><%=isPrint?messageService.getString("e3ps.message.ket_message", "02024")/*신규개발*/:tryType%></td>
                  <td width="75" class="tdwhiteM"><%=moldPlanerName%></td>
                  <td width="175" class="tdwhiteM"><%=partName%></td>
                  <td width="80" class="tdwhiteM"><%=outsourcingName%></td>
                  <td width="80" class="tdwhiteM"><%=ton%></td>
                </tr>
                <tr>
                  <td width="70" class="tdwhiteM"><%=isPrint?tryType:"-"%></td>
                  <td width="75" class="tdwhiteM"><%=moldMakerName%></td>
                  <td width="175" class="tdwhiteM"><%=materialName%></td>
                  <td width="80" class="tdwhiteM"><%=cavSu%></td>
                  <td width="80" class="tdwhiteM"><%=quantity%></td>
                </tr>

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
