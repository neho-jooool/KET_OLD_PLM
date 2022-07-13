<?xml version="1.0"?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.dao.CostReportDao , e3ps.cost.control.CostReportCtl"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable"%>
<SearchList>
<%
	String select_val = request.getParameter("select_val");
	String report_pk = request.getParameter("report_pk");

	CostReportCtl CostReportCtl = new CostReportCtl();
	ArrayList selectWork = CostReportCtl.searchCostWork(select_val,report_pk);

	Hashtable hashWork = null;

	String total_sales_1 = "";
	String profit_1 = "";
	String per_profit_1 = "";
	String total_sales_2 = "";
	String profit_2 = "";
	String per_profit_2 = "";
	String total_sales_3 = "";
	String profit_3 = "";
	String per_profit_3 = "";
	String total_sales_4 = "";
	String profit_4 = "";
	String per_profit_4 = "";
	String total_sales_5 = "";
	String profit_5 = "";
	String per_profit_5 = "";
	String total_sales_6 = "";
	String profit_6 = "";
	String per_profit_6 = "";
	String total_sales_7 = "";
	String profit_7 = "";
	String per_profit_7 = "";
	String total_sales_8 = "";
	String profit_8 = "";
	String per_profit_8 = "";
	String sum_sales = "";
	String sum_profit = "";
	String sum_per_p = "";

		for(int i = 0 ; i < selectWork.size(); i++){
			hashWork = (Hashtable)selectWork.get(i);
			total_sales_1 	= (String)hashWork.get("total_sales_1");
			profit_1 		= (String)hashWork.get("profit_1");
			per_profit_1 	= (String)hashWork.get("per_profit_1");
			total_sales_2 	= (String)hashWork.get("total_sales_2");
			profit_2 		= (String)hashWork.get("profit_2");
			per_profit_2 	= (String)hashWork.get("per_profit_2");
			total_sales_3 	= (String)hashWork.get("total_sales_3");
			profit_3 		= (String)hashWork.get("profit_3");
			per_profit_3 	= (String)hashWork.get("per_profit_3");
			total_sales_4 	= (String)hashWork.get("total_sales_4");
			profit_4 		= (String)hashWork.get("profit_4");
			per_profit_4 	= (String)hashWork.get("per_profit_4");
			total_sales_5 	= (String)hashWork.get("total_sales_5");
			profit_5 		= (String)hashWork.get("profit_5");
			per_profit_5 	= (String)hashWork.get("per_profit_5");
			total_sales_6 	= (String)hashWork.get("total_sales_6");
			profit_6 		= (String)hashWork.get("profit_6");
			per_profit_6 	= (String)hashWork.get("per_profit_6");
			total_sales_7 	= (String)hashWork.get("total_sales_7");
			profit_7 		= (String)hashWork.get("profit_7");
			per_profit_7 	= (String)hashWork.get("per_profit_7");
			total_sales_8 	= (String)hashWork.get("total_sales_8");
			profit_8 		= (String)hashWork.get("profit_8");
			per_profit_8 	= (String)hashWork.get("per_profit_8");
			sum_sales 		= (String)hashWork.get("sum_sales");
			sum_profit 		= (String)hashWork.get("sum_profit");
			sum_per_p 		= (String)hashWork.get("sum_per_p");

			if(!"".equals(per_profit_1)){
				per_profit_1 = Double.toString(Double.parseDouble(per_profit_1));
			}
			if(!"".equals(per_profit_2)){
				per_profit_2 = Double.toString(Double.parseDouble(per_profit_2));
			}
			if(!"".equals(per_profit_3)){
				per_profit_3 = Double.toString(Double.parseDouble(per_profit_3));
			}
			if(!"".equals(per_profit_4)){
				per_profit_4 = Double.toString(Double.parseDouble(per_profit_4));
			}
			if(!"".equals(per_profit_5)){
				per_profit_5 = Double.toString(Double.parseDouble(per_profit_5));
			}
			if(!"".equals(per_profit_6)){
				per_profit_6 = Double.toString(Double.parseDouble(per_profit_6));
			}
			if(!"".equals(per_profit_7)){
				per_profit_7 = Double.toString(Double.parseDouble(per_profit_7));
			}
			if(!"".equals(per_profit_8)){
				per_profit_8 = Double.toString(Double.parseDouble(per_profit_8));
			}
%>
		<List1 total_sales_1="<%=total_sales_1%>" profit_1="<%=profit_1%>" per_profit_1="<%=per_profit_1%>">
		</List1>
		<List2 total_sales_2="<%=total_sales_2%>" profit_2="<%=profit_2%>" per_profit_2="<%=per_profit_2%>">
		</List2>
		<List3 total_sales_3="<%=total_sales_3%>" profit_3="<%=profit_3%>" per_profit_3="<%=per_profit_3%>">
		</List3>
		<List4 total_sales_4="<%=total_sales_4%>" profit_4="<%=profit_4%>" per_profit_4="<%=per_profit_4%>">
		</List4>
		<List5 total_sales_5="<%=total_sales_5%>" profit_5="<%=profit_5%>" per_profit_5="<%=per_profit_5%>">
		</List5>
		<List6 total_sales_6="<%=total_sales_6%>" profit_6="<%=profit_6%>" per_profit_6="<%=per_profit_6%>">
		</List6>
		<List7 total_sales_7="<%=total_sales_7%>" profit_7="<%=profit_7%>" per_profit_7="<%=per_profit_7%>">
		</List7>
		<List8 total_sales_8="<%=total_sales_8%>" profit_8="<%=profit_8%>" per_profit_8="<%=per_profit_8%>">
		</List8>
		<List_sum sum_sales="<%=sum_sales%>" sum_profit="<%=sum_profit%>" sum_per_p="<%=Double.parseDouble(sum_per_p)%>">
		</List_sum>
<%
		}
%>
</SearchList>
