<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<%@page import="wt.fc.QueryResult"%>

<%@page import="e3ps.project.trySchdule.beans.TryPlanData"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="e3ps.project.trySchdule.TrySchdule"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.fc.Persistable"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
%>

<%
  Calendar today = Calendar.getInstance();
	
  String todayStr = format.format(today.getTime());

  String state = request.getParameter("state");
  String type = request.getParameter("type");
  String date = request.getParameter("date");
  
  QueryResult rs = TrySchduleHelper.getTotalDayTry(date, "", "", TrySchduleHelper.ALL);
  boolean todayBefore = todayStr.compareTo(date) > 0;
  TryPlanData tplan = new TryPlanData(todayBefore);
  
  //Kogger.debug("rs.size() = " + rs.size());
  while(rs.hasMoreElements()){	
	Object objs[] = (Object[])rs.nextElement();	
	if(objs[0] instanceof E3PSTask){
		E3PSTask task = (E3PSTask)objs[0];
		ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
		tplan.add(task);
	}else if(objs[0] instanceof TrySchdule){	
		TrySchdule trySchdule = (TrySchdule)objs[0];
		tplan.add(trySchdule);
	}
  }
  
  Vector datas = tplan.getData(Integer.parseInt(state), type);
  TryPlanComparator  tc = new TryPlanComparator();
  
  Collections.sort(datas, tc);
  
  //Kogger.debug("###size = " + datas.size());
%>

<%@page import="e3ps.project.trySchdule.beans.TryPlanComparator"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleData"%>
<stdinfo>
			<results>
				<contents>
					<data_info>
				    <%for(int i = 0; i < datas.size(); i++){
						Object obj = datas.get(i);
						String oid = "";
						String tryType = "";
						String title = "";
						String dieNo = "";
						String shortPart = "";
						oid = CommonUtil.getOIDString((Persistable)obj);
						String color = "";
						int colorType = 0;
						TrySchduleData data = null;
						if(obj instanceof E3PSTask){
							
							E3PSTask task = (E3PSTask)obj;
							
							data = new TrySchduleData(task);
							
							colorType = TryPlanData.getTryState(task);
							
							
						}else if(obj instanceof TrySchdule){
							
							TrySchdule trySchdule = (TrySchdule)obj;
							data = new TrySchduleData(trySchdule);
							
							colorType = TryPlanData.getTryState(trySchdule);
							//Kogger.debug("colorType = " + colorType);
							
						}
						String partName = "";
						if(data.partName != null){
							partName = data.partName;
						}
						
						
						//partName = "kkkkkkkkkgggggdd";
						tryType = data.tryType;
						title = data.shortType;
						dieNo = data.dieNo;
						shortPart = StringUtil.getLeft(partName, 20);
						//title += " / " + data.tryType;
						
						switch(colorType){
						    case TryPlanData.DELAY : {color = "red"; break;}
						    case TryPlanData.COMPLATED : {color = "blue"; break;}
						    case TryPlanData.TRYPLAN : {color = "black"; break;}
						    case TryPlanData.TRYNONPLAN : {color = "gray"; break;}
						    default: break;
						}
						
					%>
						<l_code><![CDATA[<%=oid%>]]></l_code>
						<l_name><![CDATA[<%=title%>]]></l_name>
						<l_tryType><![CDATA[<%=tryType%>]]></l_tryType>
						<l_dieNo><![CDATA[<%=dieNo%>]]></l_dieNo>
						<l_shortPart><![CDATA[<%=shortPart%>]]></l_shortPart>
						<l_color><![CDATA[<%=color%>]]></l_color>
					<%}%>
					</data_info>
					<message>
						<l_message><![CDATA[<%="ggggfffffmmm"%>]]></l_message>
					</message>
				</contents>
			</results>
</stdinfo>
