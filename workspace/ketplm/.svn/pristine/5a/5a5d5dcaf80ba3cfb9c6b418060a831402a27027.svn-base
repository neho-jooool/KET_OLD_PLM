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
	int mold = 0;
	int press = 0;
	int insert = 0;
	int etc = 0;
	int packing = 0;

	CostInvestCtl investCtl = new CostInvestCtl();
	ArrayList costInvestList = null;
	ArrayList firstList = null;
	ArrayList secondList = null;
	ArrayList thirdList = null;
	ArrayList fourList = null;
	ArrayList fiveList = null;
	Hashtable costInvestItem = null;
	costInvestList = investCtl.costFullList(report_pk);
	firstList = investCtl.costInvestList(report_pk, "first");
	secondList = investCtl.costInvestList(report_pk, "second");
	thirdList = investCtl.costInvestList(report_pk, "third");
	fourList = investCtl.costInvestList(report_pk, "four");
	fiveList = investCtl.costInvestList(report_pk, "five");
%>
<!-- DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"-->
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
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery.alphanumeric.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/invest.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery.autosize.js"></script>
<script type="text/javascript">
	function onSave(){
		var fRow = $(".firstItem").length;
		var sRow = $(".seconItem").length;
		var tRow = $(".thirdItem").length;
		var foRow = $(".fourItem").length;
		var fiRow = $(".fiveItem").length;
		var firstItem = "";
		var seconItem = "";
		var thirdItem = "";
		var fourItem = "";
		var fiveItem = "";
		var partName = "";
		var toCost = "";
		var toSu = "";
		var toType = "";
		var toNote = "";
		var toCostSum = "";
		var proCostName = "";
		var firstProcost = "";
		var seconProcost = "";
		var thirdProcost = "";
		var fourProcost = "";
		var fiveProcost = "";

		for(i=0;i<document.getElementsByName("procost_name").length;i++){
			if(i==0){
				proCostName = document.getElementsByName("procost_name").item(i).value;
			}else{
				proCostName = proCostName + "^"+ document.getElementsByName("procost_name").item(i).value;
			}
		}

		var proRow = document.getElementsByName("procost_name").length;
		//Mold
		for(i=0; i<fRow; i++){
			partName = (document.getElementsByName("part_name_1").item(i).value.length > 0) ? document.getElementsByName("part_name_1").item(i).value : "";
			toCost = (document.getElementsByName("to_cost_1").item(i).value.length > 0) ? document.getElementsByName("to_cost_1").item(i).value : "";
			toSu = (document.getElementsByName("to_su_1").item(i).value.length > 0) ? document.getElementsByName("to_su_1").item(i).value : "";
			toCostSum = (document.getElementsByName("to_cost_sum_1").item(i).value.length > 0) ? document.getElementsByName("to_cost_sum_1").item(i).value : "";
			toType = (document.getElementsByName("to_type_1").item(i).value.length > 0) ? document.getElementsByName("to_type_1").item(i).value : "";
			toNote = (document.getElementsByName("to_note_1").item(i).value.length > 0) ? document.getElementsByName("to_note_1").item(i).value : "";
			if(proRow > 0){
				for(p=0;p<$("#invest_table tr:eq(1) td").length;p++){
					if(p==0){
						firstProcost = document.getElementsByName("to_procost_1_"+(p+1)).item(i).value;
					}else{
						firstProcost = firstProcost + "^" + document.getElementsByName("to_procost_1_"+(p+1)).item(i).value;
					}
				}
			}
			if(i==0){
				firstItem = partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+firstProcost;
			}else{
				firstItem = firstItem+"@"+partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+firstProcost;
			}
			firstProcost = "";
		}
		//Press
		for(i=0; i<sRow; i++){
			partName = (document.getElementsByName("part_name_2").item(i).value.length > 0) ? document.getElementsByName("part_name_2").item(i).value : "";
			toCost = (document.getElementsByName("to_cost_2").item(i).value.length > 0) ? document.getElementsByName("to_cost_2").item(i).value : "";
			toSu = (document.getElementsByName("to_su_2").item(i).value.length > 0) ? document.getElementsByName("to_su_2").item(i).value : "";
			toCostSum = (document.getElementsByName("to_cost_sum_2").item(i).value.length > 0) ? document.getElementsByName("to_cost_sum_2").item(i).value : "";
			toType = (document.getElementsByName("to_type_2").item(i).value.length > 0) ? document.getElementsByName("to_type_2").item(i).value : "";
			toNote = (document.getElementsByName("to_note_2").item(i).value.length > 0) ? document.getElementsByName("to_note_2").item(i).value : "";
			if(proRow > 0){
				for(p=0;p<$("#invest_table tr:eq(1) td").length;p++){
					if(p==0){
						seconProcost = document.getElementsByName("to_procost_2_"+(p+1)).item(i).value;
					}else{
						seconProcost = seconProcost + "^" + document.getElementsByName("to_procost_2_"+(p+1)).item(i).value;
					}
				}
			}
			if(i==0){
				seconItem = partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+seconProcost;
			}else{
				seconItem = seconItem+"@"+partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+seconProcost;
			}
			seconProcost = "";
		}
		//조립설비비(Insert설비)
		for(i=0; i<tRow; i++){
			partName = (document.getElementsByName("part_name_3").item(i).value.length > 0) ? document.getElementsByName("part_name_3").item(i).value : "";
			toCost = (document.getElementsByName("to_cost_3").item(i).value.length > 0) ? document.getElementsByName("to_cost_3").item(i).value : "";
			toSu = (document.getElementsByName("to_su_3").item(i).value.length > 0) ? document.getElementsByName("to_su_3").item(i).value : "";
			toCostSum = (document.getElementsByName("to_cost_sum_3").item(i).value.length > 0) ? document.getElementsByName("to_cost_sum_3").item(i).value : "";
			toType = (document.getElementsByName("to_type_3").item(i).value.length > 0) ? document.getElementsByName("to_type_3").item(i).value : "";
			toNote = (document.getElementsByName("to_note_3").item(i).value.length > 0) ? document.getElementsByName("to_note_3").item(i).value : "";
			if(proRow > 0){
				for(p=0;p<$("#invest_table tr:eq(1) td").length;p++){
					if(p==0){
						thirdProcost = document.getElementsByName("to_procost_3_"+(p+1)).item(i).value;
					}else{
						thirdProcost = thirdProcost + "^" + document.getElementsByName("to_procost_3_"+(p+1)).item(i).value;
					}
				}
			}
			if(i==0){
				thirdItem = partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+seconProcost;
			}else{
				thirdItem = thirdItem+"@"+partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+thirdProcost;
			}
				thirdProcost = "";
		}
		//기타투자비
		for(i=0; i<foRow; i++){
			partName = (document.getElementsByName("part_name_4").item(i).value.length > 0) ? document.getElementsByName("part_name_4").item(i).value : "";
			toCost = (document.getElementsByName("to_cost_4").item(i).value.length > 0) ? document.getElementsByName("to_cost_4").item(i).value : "";
			toSu = (document.getElementsByName("to_su_4").item(i).value.length > 0) ? document.getElementsByName("to_su_4").item(i).value : "";
			toCostSum = (document.getElementsByName("to_cost_sum_4").item(i).value.length > 0) ? document.getElementsByName("to_cost_sum_4").item(i).value : "";
			toType = (document.getElementsByName("to_type_4").item(i).value.length > 0) ? document.getElementsByName("to_type_4").item(i).value : "";
			toNote = (document.getElementsByName("to_note_4").item(i).value.length > 0) ? document.getElementsByName("to_note_4").item(i).value : "";
			if(proRow > 0){
				for(p=0;p<$("#invest_table tr:eq(1) td").length;p++){
					if(p==0){
						fourProcost = document.getElementsByName("to_procost_4_"+(p+1)).item(i).value;
					}else{
						fourProcost = fourProcost + "^" + document.getElementsByName("to_procost_4_"+(p+1)).item(i).value;
					}
				}
			}
			if(i==0){
				fourItem = partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+fourProcost;
			}else{
				fourItem = fourItem+"@"+partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+fourProcost;
			}
			fourProcost = "";
		}
		//포장재투자비
		for(i=0; i<fiRow; i++){
			partName = (document.getElementsByName("part_name_5").item(i).value.length > 0) ? document.getElementsByName("part_name_5").item(i).value : "";
			toCost = (document.getElementsByName("to_cost_5").item(i).value.length > 0) ? document.getElementsByName("to_cost_5").item(i).value : "";
			toSu = (document.getElementsByName("to_su_5").item(i).value.length > 0) ? document.getElementsByName("to_su_5").item(i).value : "";
			toCostSum = (document.getElementsByName("to_cost_sum_5").item(i).value.length > 0) ? document.getElementsByName("to_cost_sum_5").item(i).value : "";
			toType = (document.getElementsByName("to_type_5").item(i).value.length > 0) ? document.getElementsByName("to_type_5").item(i).value : "";
			toNote = (document.getElementsByName("to_note_5").item(i).value.length > 0) ? document.getElementsByName("to_note_5").item(i).value : "";
			if(proRow > 0){
				for(p=0;p<$("#invest_table tr:eq(1) td").length;p++){
					if(p==0){
						fiveProcost = document.getElementsByName("to_procost_5_"+(p+1)).item(i).value;
					}else{
						fiveProcost = fiveProcost + "^" + document.getElementsByName("to_procost_5_"+(p+1)).item(i).value;
					}
				}
			}
			if(i==0){
				fiveItem = partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+fiveProcost;
			}else{
				fiveItem = fiveItem+"@"+partName+"|"+toCost+"|"+toSu+"|"+toCostSum+"|"+toType+"|"+toNote+"|"+proCostName+"|"+fiveProcost;
			}
			fiveProcost = "";
		}
		document.forms[0].frItem.value = firstItem;
		document.forms[0].seItem.value = seconItem;
		document.forms[0].thItem.value = thirdItem;
		document.forms[0].foItem.value = fourItem;
		document.forms[0].fiItem.value = fiveItem;
		document.forms[0].productItem.value = proCostName;
		document.forms[0].method="post";
		document.forms[0].action = "/plm/jsp/cost/costreport/invest_save.jsp";
		document.forms[0].submit();

	}
</script>
<body>
<form>
<input type="hidden" name="frItem" value=""/>
<input type="hidden" name="seItem" value=""/>
<input type="hidden" name="thItem" value=""/>
<input type="hidden" name="foItem" value=""/>
<input type="hidden" name="fiItem" value=""/>
<input type="hidden" name="report_pk" value="<%=report_pk%>"/>
<input type="hidden" name="productItem" value=""/>
<input type="hidden" name="modify" value="M"/>
<p><font class="style10">투자비 내역 보고</font></p>
<table id="invest_table" class="invest_table" border="1px">
<%
	if(costInvestList.size()<=0){
%>
	<tr id="titleItem" class="titleItem">
		<td width="139" height="18" bgcolor="#FFFFFF" class="style5">구분</td>
		<td width="265" height="18" bgcolor="#FFFFFF" class="style5">품명</td>
		<td width="106" height="18" bgcolor="#FFFFFF" class="style5">단가</td>
		<td width="63"  height="18" bgcolor="#FFFFFF" class="style5">수량<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="cButton"/></td>
		<td width="106" height="18" bgcolor="#FFFFFF" class="style5">투자금액</td>
		<td width="72"  height="18" bgcolor="#FFFFFF" class="style5">감가조건</td>
		<td width="259" height="18" bgcolor="#FFFFFF" class="style5">비고</td>
	</tr>
<%
	}
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
		gubun_cnt = (String)costInvestItem.get("gubun_cnt")!=""?(String)costInvestItem.get("gubun_cnt"):"0";
		invest_no = (String)costInvestItem.get("invest_no")!=""?(String)costInvestItem.get("invest_no"):"&nbsp;";
		cost_sum = (String)costInvestItem.get("cost_sum")!=""?(String)costInvestItem.get("cost_sum"):"&nbsp;";
		invest_order = (String)costInvestItem.get("invest_order")!=""?(String)costInvestItem.get("invest_order"):"&nbsp;";
		if(i==0){
			if(!"".equals(item_name)){
				title_rowspan = "2";
			}
%>
	<tr id="titleItem" class="titleItem">
		<td width="139" height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">구분</td>
		<td width="265" height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">품명</td>
		<td width="106" height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">단가</td>
		<td width="63"  height="18" bgcolor="#FFFFFF" class="style5" rowspan="<%=title_rowspan%>">수량<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="cButton"/></td>
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
					out.println("");
					out.println("<td width='93' height='18' bgcolor='#FFFFFF' class='style5'><img src='/plm/jsp/cost/images/common/iconMinus.gif' onclick='dlCol()' align='middle'/><input name='procost_name' class='style1' size='8' value='"+itemNameArray[n].toString()+"'/></td>");
				}
			}
		}
	}
	for(int i=0; i < firstList.size(); i++){
		costInvestItem = (Hashtable)firstList.get(i);
		gubun = (String)costInvestItem.get("gubun")!=""?(String)costInvestItem.get("gubun"):"&nbsp;";
		part_name = (String)costInvestItem.get("part_name")!=""?(String)costInvestItem.get("part_name"):"&nbsp;";
		to_cost = (String)costInvestItem.get("to_cost")!=""?(String)costInvestItem.get("to_cost"):"&nbsp;";
		to_su = (String)costInvestItem.get("to_su")!=""?(String)costInvestItem.get("to_su"):"&nbsp;";
		to_type = (String)costInvestItem.get("to_type")!=""?(String)costInvestItem.get("to_type"):"&nbsp;";
		to_note = (String)costInvestItem.get("to_note")!=""?(String)costInvestItem.get("to_note"):"&nbsp;";
		item_name = (String)costInvestItem.get("item_name")!=""?(String)costInvestItem.get("item_name"):"";
		item_cost = (String)costInvestItem.get("item_cost")!=""?(String)costInvestItem.get("item_cost"):"&nbsp;";
		gubun_cnt = (String)costInvestItem.get("gubun_cnt")!=""?(String)costInvestItem.get("gubun_cnt"):"0";
		invest_no = (String)costInvestItem.get("invest_no")!=""?(String)costInvestItem.get("invest_no"):"&nbsp;";
		cost_sum = (String)costInvestItem.get("cost_sum")!=""?(String)costInvestItem.get("cost_sum"):"&nbsp;";
		invest_order = (String)costInvestItem.get("invest_order")!=""?(String)costInvestItem.get("invest_order"):"&nbsp;";
		if(!"".equals(item_name)){
			title_rowspan = "2";
		}
%>
	<tr id="firstItem" class="firstItem">
<%			if(i !=0){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=firstList.size()%>">Mold<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_1" class="style17" size="40" value="<%=part_name %>"></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_1" class="style1" size="11" value="<%=to_cost %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_1" class="style1" size="4" value="<%=to_su %>" onKeyUp="javascript:rowSum(this);"></td>
<%
		if(!"".equals(item_name)){
			String[] itemNameArray = item_name.split("\\^");
			String[] itemCostArray = item_cost.split("\\^", itemNameArray.length);
			for(int n=0;n<itemNameArray.length;n++){
				if("".equals(itemCostArray[n].toString())){
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_1_"+(n+1)+"' class='style1' size='11' value='' onKeyUp='javascript:rowSum(this);'></td>");
				}else{
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_1_"+(n+1)+"' class='style1' size='11' value='"+itemCostArray[n].toString()+"' onKeyUp='javascript:rowSum(this);'></td>");
				}
			}
		}
%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_1" class="style1" size="11" value="<%=cost_sum %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_1" class="style17" size="6" value="<%=to_type %>"></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_1" cols="38" rows="1" class="style9"><%=to_note %></textarea></td>
	</tr>
<%
	}
	if(firstList.size() <= 0){
%>
	<tr id="firstItem" class="firstItem">
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5">Mold<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_1" class="style17" size="40" value=""></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_1" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_1" class="style1" size="4" value="" onKeyUp="javascript:rowSum(this);"></td>
	<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_1_"+(n+1)+"' class='style1' size='11' value='' onKeyUp='javascript:rowSum(this);'></td>");
				}
			}
	%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_1" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_1" class="style17" size="6" value=""></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_1" cols="38" rows="1" class="style9"></textarea></td>
	</tr>
<%
	}
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
		<td width="72" height="20" bgcolor="FFFFCC" class="style1" id="to_type_1_sum">&nbsp;</td>
		<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
	</tr>
<%
	for(int i=0; i < secondList.size(); i++){
		costInvestItem = (Hashtable)secondList.get(i);
		gubun = (String)costInvestItem.get("gubun")!=""?(String)costInvestItem.get("gubun"):"&nbsp;";
		part_name = (String)costInvestItem.get("part_name")!=""?(String)costInvestItem.get("part_name"):"&nbsp;";
		to_cost = (String)costInvestItem.get("to_cost")!=""?(String)costInvestItem.get("to_cost"):"&nbsp;";
		to_su = (String)costInvestItem.get("to_su")!=""?(String)costInvestItem.get("to_su"):"&nbsp;";
		to_type = (String)costInvestItem.get("to_type")!=""?(String)costInvestItem.get("to_type"):"&nbsp;";
		to_note = (String)costInvestItem.get("to_note")!=""?(String)costInvestItem.get("to_note"):"&nbsp;";
		item_name = (String)costInvestItem.get("item_name")!=""?(String)costInvestItem.get("item_name"):"";
		item_cost = (String)costInvestItem.get("item_cost")!=""?(String)costInvestItem.get("item_cost"):"&nbsp;";
		gubun_cnt = (String)costInvestItem.get("gubun_cnt")!=""?(String)costInvestItem.get("gubun_cnt"):"0";
		invest_no = (String)costInvestItem.get("invest_no")!=""?(String)costInvestItem.get("invest_no"):"&nbsp;";
		cost_sum = (String)costInvestItem.get("cost_sum")!=""?(String)costInvestItem.get("cost_sum"):"&nbsp;";
		invest_order = (String)costInvestItem.get("invest_order")!=""?(String)costInvestItem.get("invest_order"):"&nbsp;";
		if(!"".equals(item_name)){
			title_rowspan = "2";
		}
%>
	<tr class="seconItem" id="seconItem">
<%			if(i !=0){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=secondList.size()%>">Press<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_2" class="style17" size="40" value="<%=part_name %>"></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_2" class="style1" size="11" value="<%=to_cost %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_2" class="style1" size="4" value="<%=to_su %>" onKeyUp="javascript:rowSum(this);"></td>
<%
		if(!"".equals(item_name)){
			String[] itemNameArray = item_name.split("\\^");
			String[] itemCostArray = item_cost.split("\\^", itemNameArray.length);
			for(int n=0;n<itemNameArray.length;n++){
				if("".equals(itemCostArray[n].toString())){
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_2_"+(n+1)+"' class='style1' size='11' value='' onKeyUp='javascript:rowSum(this);'></td>");
				}else{
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_2_"+(n+1)+"' class='style1' size='11' value='"+itemCostArray[n].toString()+"' onKeyUp='javascript:rowSum(this);'></td>");
				}
			}
		}
%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_2" class="style1" size="11" value="<%=cost_sum %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_2" class="style17" size="6" value="<%=to_type %>"></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_2" cols="38" rows="1" class="style9"><%=to_note %></textarea></td>
	</tr>
<%
	}
	if(secondList.size() <= 0){
%>
	<tr class="seconItem" id="seconItem">
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5">Press<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_2" class="style17" size="40" value=""></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_2" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_2" class="style1" size="4" value="" onKeyUp="javascript:rowSum(this);"></td>
	<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_2_"+(n+1)+"' class='style1' size='11' value='' onKeyUp='javascript:rowSum(this);'></td>");
				}
			}
	%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_2" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_2" class="style17" size="6" value=""></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_2" cols="38" rows="1" class="style9"></textarea></td>
	</tr>
<%
	}
%>
	<tr id="PSum">
		<td width="406" height="20" colspan="2" bgcolor="FFFFCC" class="style1">소계</td>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_2_sum">&nbsp;</td>
		<td width="63"  height="20" bgcolor="FFFFCC" class="style1" id="to_su_2_sum">&nbsp;</td>
<%
		if(!"".equals(item_name)){
			String[] itemNameArray = item_name.split("\\^");
			for(int n=0;n<itemNameArray.length;n++){
				out.println("<td height='24' bgcolor='#FFFFCC' class='style1' id='to_procost_2_"+(n+1)+"_sum'>&nbsp;</td>");
			}
		}
%>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_sum_2_sum">&nbsp;</td>
		<td width="72"  height="20" bgcolor="FFFFCC" class="style1" id="to_type_2_sum">&nbsp;</td>
		<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
	</tr>
<%
	for(int i=0; i < thirdList.size(); i++){
		costInvestItem = (Hashtable)thirdList.get(i);
		gubun = (String)costInvestItem.get("gubun")!=""?(String)costInvestItem.get("gubun"):"&nbsp;";
		part_name = (String)costInvestItem.get("part_name")!=""?(String)costInvestItem.get("part_name"):"&nbsp;";
		to_cost = (String)costInvestItem.get("to_cost")!=""?(String)costInvestItem.get("to_cost"):"&nbsp;";
		to_su = (String)costInvestItem.get("to_su")!=""?(String)costInvestItem.get("to_su"):"&nbsp;";
		to_type = (String)costInvestItem.get("to_type")!=""?(String)costInvestItem.get("to_type"):"&nbsp;";
		to_note = (String)costInvestItem.get("to_note")!=""?(String)costInvestItem.get("to_note"):"&nbsp;";
		item_name = (String)costInvestItem.get("item_name")!=""?(String)costInvestItem.get("item_name"):"";
		item_cost = (String)costInvestItem.get("item_cost")!=""?(String)costInvestItem.get("item_cost"):"&nbsp;";
		gubun_cnt = (String)costInvestItem.get("gubun_cnt")!=""?(String)costInvestItem.get("gubun_cnt"):"0";
		invest_no = (String)costInvestItem.get("invest_no")!=""?(String)costInvestItem.get("invest_no"):"&nbsp;";
		cost_sum = (String)costInvestItem.get("cost_sum")!=""?(String)costInvestItem.get("cost_sum"):"&nbsp;";
		if(!"".equals(item_name)){
			title_rowspan = "2";
		}
%>
	<tr class="thirdItem" id="thirdItem">
<%			if(i !=0){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=thirdList.size()%>">조립설비비<br/>(Insert설비)<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_3" class="style17" size="40" value="<%=part_name %>"></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_3" class="style1" size="11" value="<%=to_cost %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_3" class="style1" size="4" value="<%=to_su %>" onKeyUp="javascript:rowSum(this);"></td>
<%
		if(!"".equals(item_name)){
			String[] itemNameArray = item_name.split("\\^");
			String[] itemCostArray = item_cost.split("\\^", itemNameArray.length);
			for(int n=0;n<itemNameArray.length;n++){
				if("".equals(itemCostArray[n].toString())){
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_3_"+(n+1)+"' class='style1' size='11' value='' onKeyUp='javascript:rowSum(this);'></td>");
				}else{
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_3_"+(n+1)+"' class='style1' size='11' value='"+itemCostArray[n].toString()+"' onKeyUp='javascript:rowSum(this);'></td>");
				}
			}
		}
%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_3" class="style1" size="11" value="<%=cost_sum %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_3" class="style17" size="6" value="<%=to_type %>"></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_3" cols="38" rows="1" class="style9"><%=to_note %></textarea></td>
	</tr>
<%
	}
	if(thirdList.size() <= 0){
%>
	<tr class="thirdItem" id="thirdItem">
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5">조립설비비<br/>(Insert설비)<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_3" class="style17" size="40" value=""></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_3" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_3" class="style1" size="4" value="" onKeyUp="javascript:rowSum(this);"></td>
	<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_3_"+(n+1)+"' class='style1' size='11' value='' onKeyUp='javascript:rowSum(this);'></td>");
				}
			}
	%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_3" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_3" class="style17" size="6" value=""></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_3" cols="38" rows="1" class="style9"></textarea></td>
	</tr>
<%
	}
%>
	<tr id="ISum">
		<td width="406" height="20" colspan="2" bgcolor="FFFFCC" class="style1">소계</td>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_3_sum">&nbsp;</td>
		<td width="63"  height="20" bgcolor="FFFFCC" class="style1" id="to_su_3_sum">&nbsp;</td>
<%
		if(!"".equals(item_name)){
			String[] itemNameArray = item_name.split("\\^");
			for(int n=0;n<itemNameArray.length;n++){
				out.println("<td height='24' bgcolor='#FFFFCC' class='style1' id='to_procost_3_"+(n+1)+"_sum'>&nbsp;</td>");
			}
		}
%>
	<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_sum_3_sum">&nbsp;</td>
	<td width="72"  height="20" bgcolor="FFFFCC" class="style1" id="to_type_3_sum">&nbsp;</td>
	<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
</tr>
<%
	for(int i=0; i < fourList.size(); i++){
		costInvestItem = (Hashtable)fourList.get(i);
		gubun = (String)costInvestItem.get("gubun")!=""?(String)costInvestItem.get("gubun"):"&nbsp;";
		part_name = (String)costInvestItem.get("part_name")!=""?(String)costInvestItem.get("part_name"):"&nbsp;";
		to_cost = (String)costInvestItem.get("to_cost")!=""?(String)costInvestItem.get("to_cost"):"&nbsp;";
		to_su = (String)costInvestItem.get("to_su")!=""?(String)costInvestItem.get("to_su"):"&nbsp;";
		to_type = (String)costInvestItem.get("to_type")!=""?(String)costInvestItem.get("to_type"):"&nbsp;";
		to_note = (String)costInvestItem.get("to_note")!=""?(String)costInvestItem.get("to_note"):"&nbsp;";
		item_name = (String)costInvestItem.get("item_name")!=""?(String)costInvestItem.get("item_name"):"";
		item_cost = (String)costInvestItem.get("item_cost")!=""?(String)costInvestItem.get("item_cost"):"&nbsp;";
		gubun_cnt = (String)costInvestItem.get("gubun_cnt")!=""?(String)costInvestItem.get("gubun_cnt"):"0";
		invest_no = (String)costInvestItem.get("invest_no")!=""?(String)costInvestItem.get("invest_no"):"&nbsp;";
		cost_sum = (String)costInvestItem.get("cost_sum")!=""?(String)costInvestItem.get("cost_sum"):"&nbsp;";
		invest_order = (String)costInvestItem.get("invest_order")!=""?(String)costInvestItem.get("invest_order"):"&nbsp;";
		if(!"".equals(item_name)){
			title_rowspan = "2";
		}
%>
	<tr class="fourItem" id="fourItem">
<%			if(i !=0){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=fourList.size()%>">기타투자비<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_4" class="style17" size="40" value="<%=part_name %>"></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_4" class="style1" size="11" value="<%=to_cost %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_4" class="style1" size="4" value="<%=to_su %>" onKeyUp="javascript:rowSum(this);"></td>
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
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_4" class="style1" size="11" value="<%=cost_sum %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_4" class="style17" size="6" value="<%=to_type %>"></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_4" cols="38" rows="1" class="style9"><%=to_note %></textarea></td>
<%
	}
	if(fourList.size() <= 0){
%>
	<tr class="fourItem" id="fourItem">
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5">기타투자비<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_4" class="style17" size="40" value=""></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_4" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_4" class="style1" size="4" value="" onKeyUp="javascript:rowSum(this);"></td>
	<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_4_"+(n+1)+"' class='style1' size='11' value='' onKeyUp='javascript:rowSum(this);'></td>");
				}
			}
	%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_4" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_4" class="style17" size="6" value=""></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_4" cols="38" rows="1" class="style9"></textarea></td>
	</tr>
<%
	}
%>
	<tr id="ESum">
		<td width="406" height="20" colspan="2" bgcolor="FFFFCC" class="style1">소계</td>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_4_sum">&nbsp;</td>
		<td width="63"  height="20" bgcolor="FFFFCC" class="style1" id="to_su_4_sum">&nbsp;</td>
<%
		if(!"".equals(item_name)){
			String[] itemNameArray = item_name.split("\\^");
			for(int n=0;n<itemNameArray.length;n++){
				out.println("<td height='24' bgcolor='#FFFFCC' class='style1' id='to_procost_4_"+(n+1)+"_sum'>&nbsp;</td>");
			}
		}
%>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_sum_4_sum">&nbsp;</td>
		<td width="72"  height="20" bgcolor="FFFFCC" class="style1" id="to_type_4_sum">&nbsp;</td>
		<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
	</tr>
<%
	for(int i=0; i < fiveList.size(); i++){
		costInvestItem = (Hashtable)fiveList.get(i);
		gubun = (String)costInvestItem.get("gubun")!=""?(String)costInvestItem.get("gubun"):"&nbsp;";
		part_name = (String)costInvestItem.get("part_name")!=""?(String)costInvestItem.get("part_name"):"&nbsp;";
		to_cost = (String)costInvestItem.get("to_cost")!=""?(String)costInvestItem.get("to_cost"):"&nbsp;";
		to_su = (String)costInvestItem.get("to_su")!=""?(String)costInvestItem.get("to_su"):"&nbsp;";
		to_type = (String)costInvestItem.get("to_type")!=""?(String)costInvestItem.get("to_type"):"&nbsp;";
		to_note = (String)costInvestItem.get("to_note")!=""?(String)costInvestItem.get("to_note"):"&nbsp;";
		item_name = (String)costInvestItem.get("item_name")!=""?(String)costInvestItem.get("item_name"):"";
		item_cost = (String)costInvestItem.get("item_cost")!=""?(String)costInvestItem.get("item_cost"):"&nbsp;";
		gubun_cnt = (String)costInvestItem.get("gubun_cnt")!=""?(String)costInvestItem.get("gubun_cnt"):"0";
		invest_no = (String)costInvestItem.get("invest_no")!=""?(String)costInvestItem.get("invest_no"):"&nbsp;";
		cost_sum = (String)costInvestItem.get("cost_sum")!=""?(String)costInvestItem.get("cost_sum"):"&nbsp;";
		invest_order = (String)costInvestItem.get("invest_order")!=""?(String)costInvestItem.get("invest_order"):"&nbsp;";
		if(!"".equals(item_name)){
			title_rowspan = "2";
		}
%>
	<tr class="fiveItem" id="fiveItem">
<%			if(i !=0){}else{%>
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5" rowSpan="<%=fiveList.size()%>">포장재투자비<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
<%			} %>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_5" class="style17" size="40" value="<%=part_name %>"></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_5" class="style1" size="11" value="<%=to_cost %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_5" class="style1" size="4" value="<%=to_su %>" onKeyUp="javascript:rowSum(this);"></td>
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
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_5" class="style1" size="11" value="<%=cost_sum %>" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_5" class="style17" size="6" value="<%=to_type %>"></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_5" cols="38" rows="1" class="style9"><%=to_note %></textarea></td>
	</tr>
<%
	}
	if(fiveList.size() <= 0){
%>
	<tr class="fiveItem" id="fiveItem">
		<td width="139" height="24" bgcolor="#FFFFFF" class="style5">포장재투자비<img src="/plm/jsp/cost/images/common/iconPlus.gif" align="absmiddle" class="mbutton"/></td>
		<td width="265" height="24" bgcolor="#FFFFFF" class="style17"><img src="/plm/jsp/cost/images/common/iconMinus.gif" align="absmiddle" class="delButton"/><input name="part_name_5" class="style17" size="40" value=""></td>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_5" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="63"  height="24" bgcolor="#FFFFFF" class="style1"><input name="to_su_5" class="style1" size="4" value="" onKeyUp="javascript:rowSum(this);"></td>
	<%
			if(!"".equals(item_name)){
				String[] itemNameArray = item_name.split("\\^");
				for(int n=0;n<itemNameArray.length;n++){
					out.println("<td height='24' bgcolor='#FFFFFF' class='style1' name=''><input name='to_procost_5_"+(n+1)+"' class='style1' size='11' value='' onKeyUp='javascript:rowSum(this);'></td>");
				}
			}
	%>
		<td width="106" height="24" bgcolor="#FFFFFF" class="style1"><input name="to_cost_sum_5" class="style1" size="11" value="" onKeyUp="javascript:rowSum(this);"></td>
		<td width="72"  height="24" bgcolor="#FFFFFF" class="style17"><input name="to_type_5" class="style17" size="6" value=""></td>
		<td width="259" height="24" bgcolor="#FFFFFF" class="style17"><textarea name="to_note_5" cols="38" rows="1" class="style9"></textarea></td>
	</tr>
<%
	}
%>
	<tr id="PKSum">
		<td width="406" height="20" colspan="2" bgcolor="FFFFCC" class="style1">소계</td>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_5_sum">&nbsp;</td>
		<td width="63"  height="20" bgcolor="FFFFCC" class="style1" id="to_su_5_sum">&nbsp;</td>
<%
		if(!"".equals(item_name)){
			String[] itemNameArray = item_name.split("\\^");
			for(int n=0;n<itemNameArray.length;n++){
				out.println("<td height='24' bgcolor='#FFFFCC' class='style1' id='to_procost_5_"+(n+1)+"_sum'>&nbsp;</td>");
			}
		}
%>
		<td width="106" height="20" bgcolor="FFFFCC" class="style1" id="to_cost_sum_5_sum">&nbsp;</td>
		<td width="72"  height="20" bgcolor="FFFFCC" class="style1" id="to_type_5_sum">&nbsp;</td>
		<td width="259" height="20" bgcolor="FFFFCC" class="style1">&nbsp;</td>
	</tr>
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
<table height="25"border="0" cellpadding="0" cellspacing="1">
<tr>
	<td valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_Save.gif" border="0" onClick="onSave();" style="cursor:pointer;"></td>
</tr>
</table>
</form>
</body>
</html>
