<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostInvestCtl"%>
<%
	String report_pk = request.getParameter("report_pk")!=null?request.getParameter("report_pk"):"";
	String gubun = "";
	String part_name = "";
	String to_cost = "";
	String to_su = "";
	String to_type = "";
	String to_note = "";
	String item_name = "";
	String item_cost = "";
	String gubun_cnt = "";
	String invest_no = "";
	String cost_sum = "";
	String invest_order = "";
	String gubun_count = "";

	String title_rowspan = "";
	String title_colspan = "";
	int row_count = 0;

	CostInvestCtl investCtl = new CostInvestCtl();
	ArrayList costInvestList = null;
	Hashtable costInvestItem = null;
	costInvestList = investCtl.costFullList(report_pk);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>투자비 내역보고</title>
</head>
<style type="text/css">
<!--
BODY
A:link    { text-decoration:none; color:#8A8A8A}
A:visited { text-decoration:underline; color:#5E5E5E}
A:active  { text-decoration:none; color:#5E5E5E}
A:hover   { text-decoration:underline; color:#8A8A8A}

A.txt:link    { text-decoration:none; color:#3F6FA3}
A.txt:visited { text-decoration:none; color:#3F6FA3}
A.txt:active  { text-decoration:none; color:#3F6FA3}
A.txt:hover   { text-decoration:none; color:#8A8A8A}

textarea {border:2px solid #ccc;padding: 10px;vertical-align: top;width: 90%;}
.style1 {font-size: 12px;text-align:center}
.style2 {font-size: 12px; font-weight: bold;color:#FF9900; text-align:left}
.style3 {font-size: 12px; font-weight: bold;color:#4F4F4F;}
.style4 {font-size: 12px; background-color:#D2D2D2; text-align: center; color:#FFFFFF}
.style5 {font-size: 12px; background-color:#D2D2D2; text-align: center; font-weight: bold; color:#333333}
.style6 {font-size: 12px;font-weight: bold; text-align:right;color:#3F6FA3}
.style7 {font-size: 11px}
.style8 {font-size: 12px;text-align:right}
.style9 {font-size: 12px;text-align:left}
.style10 {font-size: 24px; font-weight: bold;color:#4F4F4F;}
.style11 {font-size: 12px;text-align:right; font-weight: bold;color:#FF0033}
.style12 {font-size: 12px; background-color:#FFFFFF; text-align: center; font-weight: bold; color:#333333}
.style13 {font-size: 12px;font-weight: bold; text-align:right; color:#0000FF}
.style15 {font-size: 14px;font-weight:bold;text-align:center; color:#66A014}
.style16 {font-size: 12px;text-align:center; color:#8A8A8A}
.style17 {font-size: 12px;text-align:center}

.line_name {font-size: 4px; color:#EFEFEF; text-align:center}
#bg_color {background:#E6E6E6}
#not_Data {background:#FFDFDF}
#font_co {color:#CCCCCC}
-->
</style>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	rowSum('to_cost_1');rowSum('to_su_1');rowSum('to_cost_sum_1');
	rowSum('to_cost_2');rowSum('to_su_2');rowSum('to_cost_sum_2');
	rowSum('to_cost_3');rowSum('to_su_3');rowSum('to_cost_sum_3');
	rowSum('to_cost_4');rowSum('to_su_4');rowSum('to_cost_sum_4');
	rowSum('to_cost_5');rowSum('to_su_5');rowSum('to_cost_sum_5');
	for(n=0;n<$("td[name='procost_name']").length;n++){
		rowSum("to_procost_1_"+(n+1));
		rowSum("to_procost_2_"+(n+1));
		rowSum("to_procost_3_"+(n+1));
		rowSum("to_procost_4_"+(n+1));
		rowSum("to_procost_5_"+(n+1));
		$("#to_procost_"+(n+1)+"_sum_sum").text(parseInt(($("#to_procost_1_"+(n+1)+"_sum").text() > 0) ? $("#to_procost_1_"+(n+1)+"_sum").text() : "0")
																							+parseInt(($("#to_procost_2_"+(n+1)+"_sum").text() > 0) ? $("#to_procost_2_"+(n+1)+"_sum").text() : "0")
																							+parseInt(($("#to_procost_3_"+(n+1)+"_sum").text() > 0) ? $("#to_procost_3_"+(n+1)+"_sum").text() : "0")
																							+parseInt(($("#to_procost_4_"+(n+1)+"_sum").text() > 0) ? $("#to_procost_4_"+(n+1)+"_sum").text() : "0")
																							+parseInt(($("#to_procost_5_"+(n+1)+"_sum").text() > 0) ? $("#to_procost_5_"+(n+1)+"_sum").text() : "0"));
	}

	var to_cost_1_sum = ($("#to_cost_1_sum").text() > 0) ? $("#to_cost_1_sum").text() : "0";
	var to_cost_2_sum = ($("#to_cost_2_sum").text() > 0) ? $("#to_cost_2_sum").text() : "0";
	var to_cost_3_sum = ($("#to_cost_3_sum").text() > 0) ? $("#to_cost_3_sum").text() : "0";
	var to_cost_4_sum = ($("#to_cost_4_sum").text() > 0) ? $("#to_cost_4_sum").text() : "0";
	var to_cost_5_sum = ($("#to_cost_5_sum").text() > 0) ? $("#to_cost_5_sum").text() : "0";
	var to_su_1_sum = ($("#to_su_1_sum").text() > 0) ? $("#to_su_1_sum").text() : "0";
	var to_su_2_sum = ($("#to_su_2_sum").text() > 0) ? $("#to_su_2_sum").text() : "0";
	var to_su_3_sum = ($("#to_su_3_sum").text() > 0) ? $("#to_su_3_sum").text() : "0";
	var to_su_4_sum = ($("#to_su_4_sum").text() > 0) ? $("#to_su_4_sum").text() : "0";
	var to_su_5_sum = ($("#to_su_5_sum").text() > 0) ? $("#to_su_5_sum").text() : "0";
	var to_cost_sum_1_sum = ($("#to_cost_sum_1_sum").text() > 0) ? $("#to_cost_sum_1_sum").text() : "0";
	var to_cost_sum_2_sum = ($("#to_cost_sum_2_sum").text() > 0) ? $("#to_cost_sum_2_sum").text() : "0";
	var to_cost_sum_3_sum = ($("#to_cost_sum_3_sum").text() > 0) ? $("#to_cost_sum_3_sum").text() : "0";
	var to_cost_sum_4_sum = ($("#to_cost_sum_4_sum").text() > 0) ? $("#to_cost_sum_4_sum").text() : "0";
	var to_cost_sum_5_sum = ($("#to_cost_sum_5_sum").text() > 0) ? $("#to_cost_sum_5_sum").text() : "0";
	$("#to_cost_sum").text(parseInt(to_cost_1_sum)+parseInt(to_cost_2_sum)+parseInt(to_cost_3_sum)+parseInt(to_cost_4_sum)+parseInt(to_cost_5_sum));
	$("#to_su_sum").text(parseInt(to_su_1_sum)+parseInt(to_su_2_sum)+parseInt(to_su_3_sum)+parseInt(to_su_4_sum)+parseInt(to_su_5_sum));
	$("#to_cost_sum_sum").text(parseInt(to_cost_sum_1_sum)+parseInt(to_cost_sum_2_sum)+parseInt(to_cost_sum_3_sum)+parseInt(to_cost_sum_4_sum)+parseInt(to_cost_sum_5_sum));

	function rowSum(name){
		var sum = 0;
		for(i=0;i<$("td[name='"+name+"']").length;i++){
			cv = $("td[name='"+name+"']").eq(i).text();
			if(cv == "" || cv == " "){cv = "0";}
			sum = sum + parseInt(cv);
			if(sum==0){sum=" ";}
			$("#"+name+"_sum").text(sum);
		}
	}
});

</script>
<body>
<form>
<input type="hidden" name="report_pk" value="<%=report_pk%>"/>
<p><font class="style10">투자비 내역 보고</font></p>
<table id="invest_table" class="invest_table" border="1px">
<%
	for(int i=0; i < costInvestList.size(); i++){
		costInvestItem = (Hashtable)costInvestList.get(i);
		gubun = (String)costInvestItem.get("gubun")!=""?(String)costInvestItem.get("gubun"):"&nbsp;";
		part_name = (String)costInvestItem.get("part_name")!=""?(String)costInvestItem.get("part_name"):"&nbsp;";
		to_cost = (String)costInvestItem.get("to_cost")!=""?(String)costInvestItem.get("to_cost"):"&nbsp;";
		to_su = (String)costInvestItem.get("to_su")!=""?(String)costInvestItem.get("to_su"):"&nbsp;";
		to_type = (String)costInvestItem.get("to_type")!=""?(String)costInvestItem.get("to_type"):"&nbsp;";
		to_note = (String)costInvestItem.get("to_note")!=""?(String)costInvestItem.get("to_note"):"&nbsp;";
		item_name = (String)costInvestItem.get("item_name")!=""?(String)costInvestItem.get("item_name"):"";
		item_cost = (String)costInvestItem.get("item_cost")!=""?(String)costInvestItem.get("item_cost"):"&nbsp;";
		gubun_cnt = (String)costInvestItem.get("gubun_cnt")!=""?(String)costInvestItem.get("gubun_cnt"):"&nbsp;";
		invest_no = (String)costInvestItem.get("invest_no")!=""?(String)costInvestItem.get("invest_no"):"&nbsp;";
		cost_sum = (String)costInvestItem.get("cost_sum")!=""?(String)costInvestItem.get("cost_sum"):"&nbsp;";
		invest_order = (String)costInvestItem.get("invest_order")!=""?(String)costInvestItem.get("invest_order"):"&nbsp;";
		gubun_count = (String)costInvestItem.get("cnt")!=""?(String)costInvestItem.get("cnt"):"&nbsp;";
		if(i==0){
			if(!"".equals(item_name)){
				title_rowspan = "2";
			}
%>
	<tr id="titleItem" class="titleItem">
		<td width="139" height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">구분</td>
		<td width="265" height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">품명</td>
		<td width="106" height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">단가</td>
		<td width="63"  height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">수량</td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				title_colspan = Integer.toString(itemNameArray.length);
				out.println("<td height='18' bgcolor='#FFFFFF' class='style5' colspan="+title_colspan+">제품명</td>");
			}
%>
		<td width="106" height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">투자금액</td>
		<td width="72"  height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">감가조건</td>
		<td width="259" height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">비고</td>
	</tr>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td width='93' height='18' bgcolor='#FFFFFF' class='style5' name='procost_name'>"+itemNameArray[n].toString()+"</td>");
				}
			}
		}
		if("first".equals(gubun)){
			row_count++;
%>
	<tr id="firstItem" class="firstItem">
<%			if(i !=0 && Integer.parseInt(gubun_count) > 1){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=gubun_count%>">Mold</td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style1" name="part_name_1"><%=part_name %></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_1"><%=to_cost %></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1" name="to_su_1"><%=to_su %></td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				String[] itemCostArray = item_cost.split("\\^", itemNameArray.length);
				for(int n=0;n<itemNameArray.length;n++){
					if("".equals(itemCostArray[n].toString())){
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_1_"+(n+1)+"'>&nbsp;</td>");
					}else{
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_1_"+(n+1)+"'>"+itemCostArray[n].toString()+"</td>");
					}
				}
			}
%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_sum_1"><%=cost_sum %></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style1" name="to_type_1"><%=to_type %></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style1" name="to_note_1"><%=to_note %></td>
<%
		}
		if("first".equals(gubun) && Integer.parseInt(gubun_count) == row_count){
			row_count = 0;
%>
	<tr id="MSum">
		<td width="406" height="20" colspan="2" bgcolor="FFFFCC" class="style1">소계</td>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_1_sum">&nbsp;</td>
		<td width="63" height="20" bgcolor="FFFFCC" class="style1" id="to_su_1_sum">&nbsp;</td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFCC' class='style1' id='to_procost_1_"+(n+1)+"_sum'>&nbsp;</td>");
				}
			}
%>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_sum_1_sum">&nbsp;</td>
		<td width="72" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
		<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
	</tr>
<%
		}
		if("second".equals(gubun)){
			row_count++;
%>
	<tr class="seconItem" id="seconItem">
<%			if(row_count > 1 && Integer.parseInt(gubun_count) > 1){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=gubun_count%>">Press</td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style1" name="part_name_2"><%=part_name %></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_2"><%=to_cost %></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1" name="to_su_2"><%=to_su %></td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				String[] itemCostArray = item_cost.split("\\^", itemNameArray.length);
				for(int n=0;n<itemNameArray.length;n++){
					if("".equals(itemCostArray[n].toString())){
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_2_"+(n+1)+"'>&nbsp;</td>");
					}else{
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_2_"+(n+1)+"'>"+itemCostArray[n].toString()+"</td>");
					}
				}
			}
%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_sum_2"><%=cost_sum %></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style1" name="to_type_2"><%=to_type %></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style1" name="to_note_2"><%=to_note %></td>
	</tr>
<%
		}
		if("second".equals(gubun) && Integer.parseInt(gubun_count) == row_count){
			row_count = 0;
%>
	<tr id="PSum">
		<td width="406" height="20" colspan="2" bgcolor="FFFFCC" class="style1">소계</td>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_2_sum">&nbsp;</td>
		<td width="63" height="20" bgcolor="FFFFCC" class="style1" id="to_su_2_sum">&nbsp;</td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFCC' class='style1' id='to_procost_2_"+(n+1)+"_sum'>&nbsp;</td>");
				}
			}
%>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_sum_2_sum">&nbsp;</td>
		<td width="72"  height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
		<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
	</tr>
<%
		}
		if("third".equals(gubun)){
			row_count++;
%>
	<tr class="thirdItem" id="thirdItem">
<%			if(row_count > 1 && Integer.parseInt(gubun_count) > 1){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=gubun_count%>">조립설비비<br/>(Insert설비)</td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style1" name="part_name_3"><%=part_name %></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_3"><%=to_cost %></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1" name="to_su_3"><%=to_su %></td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				String[] itemCostArray = item_cost.split("\\^", itemNameArray.length);
				for(int n=0;n<itemNameArray.length;n++){
					if("".equals(itemCostArray[n].toString())){
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_3_"+(n+1)+"'>&nbsp;</td>");
					}else{
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_3_"+(n+1)+"'>"+itemCostArray[n].toString()+"</td>");
					}
				}
			}
%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_sum_3"><%=cost_sum %></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style1" name="to_type_3"><%=to_type %></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style1" name="to_note_3"><%=to_note %></td>
	</tr>
<%
		}
		if("third".equals(gubun) && Integer.parseInt(gubun_count) == row_count){
			row_count = 0;
%>
	<tr id="ISum">
		<td width="406" height="20" colspan="2" bgcolor="FFFFCC" class="style1">소계</td>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_3_sum">&nbsp;</td>
		<td width="63" height="20" bgcolor="FFFFCC" class="style1" id="to_su_3_sum">&nbsp;</td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFCC' class='style1' id='to_procost_3_"+(n+1)+"_sum'>&nbsp;</td>");
				}
			}
%>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_sum_3_sum">&nbsp;</td>
		<td width="72"  height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
		<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
	</tr>
<%
		}
		if("four".equals(gubun)){
			row_count++;
%>
	<tr class="fourItem" id="fourItem">
<%			if(row_count > 1 && Integer.parseInt(gubun_count) > 1){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=gubun_count%>">기타투자비</td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style1" name="part_name_4"><%=part_name %></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_4"><%=to_cost %></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1" name="to_su_4"><%=to_su %></td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				String[] itemCostArray = item_cost.split("\\^", itemNameArray.length);
				for(int n=0;n<itemNameArray.length;n++){
					if("".equals(itemCostArray[n].toString())){
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_4_"+(n+1)+"'>&nbsp;</td>");
					}else{
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_4_"+(n+1)+"'>"+itemCostArray[n].toString()+"</td>");
					}
				}
			}
%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_sum_4"><%=cost_sum %></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style1" name="to_type_4"><%=to_type %></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style1" name="to_note_4"><%=to_note %></td>
	</tr>
<%
		}
		if("four".equals(gubun) && Integer.parseInt(gubun_count) == row_count){
			row_count = 0;
%>
	<tr id="ESum">
		<td width="406" height="20" colspan="2" bgcolor="FFFFCC" class="style1">소계</td>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_4_sum">&nbsp;</td>
		<td width="63" height="20" bgcolor="FFFFCC" class="style1" id="to_su_4_sum">&nbsp;</td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFCC' class='style1' id='to_procost_4_"+(n+1)+"_sum'>&nbsp;</td>");
				}
			}
%>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_sum_4_sum">&nbsp;</td>
		<td width="72"  height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
		<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
	</tr>
<%
		}
		if("five".equals(gubun)){
			row_count++;
%>
	<tr class="fiveItem" id="fiveItem">
<%			if(row_count > 1 && Integer.parseInt(gubun_count) > 1){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=gubun_count%>">포장재투자비</td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style1" name="part_name_5"><%=part_name %></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_5"><%=to_cost %></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1" name="to_su_5"><%=to_su %></td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				String[] itemCostArray = item_cost.split("\\^", itemNameArray.length);
				for(int n=0;n<itemNameArray.length;n++){
					if("".equals(itemCostArray[n].toString())){
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_5_"+(n+1)+"'>&nbsp;</td>");
					}else{
						out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name='to_procost_5_"+(n+1)+"'>"+itemCostArray[n].toString()+"</td>");
					}
				}
			}
%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1" name="to_cost_sum_5"><%=cost_sum %></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style1" name="to_type_5"><%=to_type %></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style1" name="to_note_5"><%=to_note %></td>
	</tr>
<%
		}
		if("five".equals(gubun) && Integer.parseInt(gubun_count) == row_count){
			row_count = 0;
%>
	<tr id="PKSum">
		<td width="406" height="20" colspan="2" bgcolor="FFFFCC" class="style1">소계</td>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_5_sum">&nbsp;</td>
		<td width="63" height="20" bgcolor="FFFFCC" class="style1" id="to_su_5_sum">&nbsp;</td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFCC' class='style1' id='to_procost_5_"+(n+1)+"_sum'>&nbsp;</td>");
				}
			}
%>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_sum_5_sum">&nbsp;</td>
		<td width="72"  height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
		<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
	</tr>
<%} } %>
	<tr id="ASum">
		<td width="406" height="24" colspan="2" bgcolor="#E4ECFA" class="style1">투자비 합계</td>
		<td width="106" height="24" bgcolor="#E4ECFA" class="style1" id="to_cost_sum">&nbsp;</td>
		<td width="63"  height="24" bgcolor="#E4ECFA" class="style1" id="to_su_sum">&nbsp;</td>
<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#E4ECFA' class='style1' id='to_procost_"+(n+1)+"_sum_sum'>&nbsp;</td>");
				}
			}
%>
		<td width="106" height="24" bgcolor="#E4ECFA" class="style1" id="to_cost_sum_sum">&nbsp;</td>
		<td width="72"  height="24" bgcolor="#E4ECFA" class="style1">&nbsp;</td>
		<td width="259" height="24" bgcolor="#E4ECFA" class="style1">&nbsp;</td>
	</tr>
</table>


</form>
</body>
</html>
