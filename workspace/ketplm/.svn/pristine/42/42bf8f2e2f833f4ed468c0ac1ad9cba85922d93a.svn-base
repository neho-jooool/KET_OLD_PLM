<%@page import="java.text.DecimalFormat"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.control.NewCaseRequestCtl"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable"%>
<%
	String pk_cr_group     = StringUtil.checkNull(request.getParameter("pk_cr_group"));    // 요청서 그룹번호(PLM-검토PJT)
	String page_no         = StringUtil.checkNull(request.getParameter("page_no"));
	String cost_report_add = StringUtil.checkNull(request.getParameter("cost_report_add"));
	String select_group    = StringUtil.checkNull(request.getParameter("select_group"));
	String case_sele       = StringUtil.checkNull(request.getParameter("case_sele"));
	String chk_list        = StringUtil.checkNull(request.getParameter("chk_list"));
	String table_row 	   = StringUtil.checkNullZero(request.getParameter("table_row"));
	String report_pk       = StringUtil.checkNull(request.getParameter("report_pk"));
	String step_no         = StringUtil.checkNull(request.getParameter("step_no"));
	String w_name          = StringUtil.checkNull(request.getParameter("w_name"));
	String btn_type		   = StringUtil.checkNull(request.getParameter("btn_type"));

	String rev_no          = StringUtil.checkNull(request.getParameter("rev_no"));
	String user_case_count = StringUtil.checkNullZero(request.getParameter("user_case_count"));
	String page_name = "";
	if(page_no.equals("1")){
		page_name = "cost_report_1_edit.jsp";
	}else{
		page_name = "cost_report_2_edit.jsp";
	}

	BigDecimal calc_1  = new BigDecimal("0");//소수점 계산용
    BigDecimal calc_2 = new BigDecimal("0");//소수점 계산용
    BigDecimal calc_3 = new BigDecimal("0");//소수점 계산용
    DecimalFormat df = new DecimalFormat("#0.00");//%포맷
    String result_earn_rate = "";

	int case_count = 0;
	int p_count = 0;
	int table_row_count = 0;
	String team = "";

	String pk_cw = "";
	String part_name = "";
	String part_type = "";
	String pro_type = "";
	String su_year_1 = "";
	String su_year_2 = "";
	String su_year_3 = "";
	String su_year_4 = "";
	String su_year_5 = "";
	String su_year_6 = "";
	String royalty = "";
	String start_pro = "";
	String store = "";
	String dest = "";
	String client_cost = "";
	String ket_cost = "";
	String target_cost = "";
	String assy_note = "";
	String dis_cost = "";
	String re_m_cost = "";
	String usage = "";
	String meterial = "";
	String t_size = "";
	String w_size = "";
	String p_size = "";
	String plating = "";
	String m_maker = "";
	String m_mone = "";
	String unitcost = "";    //원재료단가
	String order_con = "";
	String h_weight = "";
	String h_scrap = "";
	String t_weight = "";
	String recycle = "";
	String die_no = "";
	String cavity = "";
	String make_type = "";
	String pro_1 = "";
	String ton = "";
	String spm = "";
	String pro_level = "";
	String pro_level_txt = "";
	String type_1 = "";
	String type_2 = "";
	String t_mone = "";
	String type_cost = "";
	String t_order = "";
	String pack_type = "";
	String in_pack = "";
	String out_pack = "";
	String yazaki_ro = "";
	String m_su = "";
	String mold_cost = "";
	String to_cost = "";
	String line_su = "";
	String sul_cost = "";
	String etc_cost = "";
	String part_note = "";
	String distri_cost = "";    //물류비 1Box기준가
	String meterial_cost = "";    //원재료비
	String m_total_cost = "";    //재료비
	String labor_cost = "";    //노무비
	String common_cost = "";    //제조경비
	String actual_cost = "";    //총원가
	String earn_rate = "";    //수익률
	String loss = "";    //loss비
	String scrap_cost = "";    //스크랩판매비
	String output = "";    //시간당 생산량
	String rate = "";    //임율
	String eff_value = "";    //효율
	String rnd_cost = "";    //R&D비율
	String jun_cost = "";    //전력비
	String ma_depr = "";    //기계감가
	String m_upkeep = "";    //금형유지비
	String total_expense = "";    //직접경비
	String overhead = "";    //간접경비
	String outsource = "";    //외주단가
	String etc_expense = "";    //기타
	String gita_cost = "";
	String pack_cost = "";    //포장비
	String yzk_depr = "";    //yzk감가
	String tabalu = "";    //타발유
	String start_depr = "";    //금형감가
	String pro_depr = "";    //설비감가
	String qu_cost = "";    //품질비용
	String tariff = "";    //관세
	String royalty_cost = "";    //로열티
	String jae_cost = "";    //재료관리비
	String ge_cost = "";    //일반관리비
	String depr_cost = "";
	String ad_cost = "";
	String case_type_admin  = "";
	String case_type_user = "";

	String sum_meterial = "";
	String p_m_total_cost = "";
	String p_labor_cost = "";
	String p_common_cost = "";
	String p_sum_meterial = "";
	String p_ad_cost = "";
	String p_g_depr_cost = "";
	String p_actual_cost = "";
	String sel_case = "";

	String t_chk_list ="";

	// 그룹번호 생성
	String group_no = "";

	NewCaseRequestCtl ncRequestCtl = new NewCaseRequestCtl();
	ArrayList caseRequestList = ncRequestCtl.caseRequestList(pk_cr_group, rev_no);
	Hashtable requestItem = null;

	for(int i = 0; i < Integer.parseInt(table_row); i++){
		for(int j = 0 ; j < caseRequestList.size(); j++){
			requestItem = (Hashtable)caseRequestList.get(j);
			if(case_count <= 0){
				case_count = Integer.parseInt(StringUtil.checkNullZero((String)requestItem.get("case_count")));
			}else{
				case_count = case_count + Integer.parseInt(StringUtil.checkNullZero((String)requestItem.get("case_count")));
			}

			if(Integer.parseInt(user_case_count) <= 0){
				//user_case_count = Integer.parseInt(((String)requestItem.get("user_case_count")!=""?(String)requestItem.get("user_case_count"):"0"));
				user_case_count = StringUtil.checkNullZero((String)requestItem.get("user_case_count"));
			}else{
				//Integer.parseInt(user_case_count) + Integer.parseInt(StringUtil.checkNullZero((String)requestItem.get("user_case_count")));
				user_case_count = Integer.toString(Integer.parseInt(user_case_count) + Integer.parseInt(StringUtil.checkNullZero((String)requestItem.get("user_case_count"))));
			}

			team = (String)requestItem.get("team");
			w_name = (String)requestItem.get("w_name");
		}
	}

	p_count = (Integer.parseInt(table_row) + case_count + Integer.parseInt(user_case_count))-1;
	table_row_count = Integer.parseInt(table_row);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>개발원가 결과보고</title>
<link rel='stylesheet' href='/plm/jsp/cost/css/costWork/newCaseRequest.css' type='text/css'>
</head>
<script language="javascript">
	function back_call(){
		self.close();
	}

	/**********************************************
	 Opener submit opener_call
	**********************************************/
	  function opener_call()
	 {
		/*document.part_1.action = "/plm/servlet/e3ps/CostReportServlet";
		document.part_1.method = "post";
		document.part_1.target = "report1";
		document.part_1.submit();
		self.close();*/
		var form = document.part_1;
		var all_sum = 0;
		var sum = 0;
		var chk_list_v = "";

		for ( var i = 0; i < form.chk_list.length; i++) {
			if (form.chk_list[i].checked == true) {
				sum++;
				chk_list_v += form.chk_list[i].value+",";
			}
			all_sum++;
		}
		//document.part_1.t_chk_list.value = chk_list_v;
		if(all_sum == 0 && form.chk_list.checked == true){
			chk_list_v = form.chk_list.value;
			sum = 1;
		}
		if(sum == 0){
			alert("산출정보를 선택하세요.");
			return false;
		}


		var url = "/plm/jsp/cost/costreport/<%=page_name%>?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&cost_report_add=<%=cost_report_add%>&chk_list="+chk_list_v+"&report_pk=<%=report_pk%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>&select_case=ok";
		opener.location.href = url;
		self.close();
	 }

	/**********************************************
	case 선택
 	**********************************************/
	function changeValue(obj){

	}



</script>
<body>
<Form method="post" name="part_1" action="/plm/jsp/cost/common/new_case_request.jsp" >
	<input name="rev_no"		type="hidden"	value="<%=rev_no%>">
	<input name="report_pk"		type="hidden"	value="<%=report_pk%>">
	<input name="page_no"		type="hidden"	value="<%=page_no%>">
	<input name="pk_cw"			type="hidden"	value="<%=pk_cw%>">
	<input name="case_sel"		type="hidden"	value="ok">
	<input name="user_case_count"	type="hidden"	value="<%=user_case_count%>">
	<input name="cost_report_add"	type="hidden"	value="<%=cost_report_add%>">
	<input name="t_chk_list"		type="hidden"	>
	<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td width="845" height="5">&nbsp;</td>
			<td width="115" height="5">&nbsp;</td>
		</tr>
		<tr>
			<td height="89" colspan="2" class="style10">제품별 원가계산 내역 </td>
		</tr>
		<tr>
			<td colspan="2" valign="top">
				<table width="906" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td height="22" colspan="8" valign="bottom" bgcolor="#FFFFFF" class="style3">■ 원가계산 현황 <span class="style2">(보고서에 기재하실 제품의 산출정보를 선택후 확인버튼을 눌러주세요)  </span></td>
					</tr>
				</table>
<%
	int p=0;
	for(int i=0; i < table_row_count; i++){
%>
				<table width="906" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
					<tr>
						<td width="27" class="style5">선택</td>
						<td height="31" colspan="2" class="style5">품명</td>
						<td width="58" class="style5">U/S</td>
						<td width="43" class="style5">재료비</td>
						<td width="43" class="style5">노무비</td>
						<td width="43" class="style5">경비</td>
						<td width="43" class="style5">제조<br>원가</td>
						<td width="43" class="style5">관리비</td>
						<td width="43" class="style5">감가비</td>
						<td width="60" class="style5">총원가</td>
						<td width="64" class="style5">판매목표가</td>
						<td width="55" class="style5">수익율</td>
						<td width="70" class="style5">예상판매량<br>(평균/년)</td>
						<td width="97" class="style5">비고</td>
					</tr>
<%
		if(i < 9){
			group_no = "T00"+Integer.toString(i+1);
		}else if(i < 99){
			group_no = "T0"+Integer.toString(i+1);
		}else{
			group_no = "T"+Integer.toString(i+1);
		}
		ArrayList caseEarnRateList = ncRequestCtl.caseEarnRateList(pk_cr_group, rev_no, group_no);
		Hashtable requestEarnItem = null;

		for(int j = 0 ; j < caseEarnRateList.size(); j++){
			requestEarnItem = (Hashtable)caseEarnRateList.get(j);
			pk_cw = (String)requestEarnItem.get("pk_cw");
			part_name = (String)requestEarnItem.get("part_name");
			part_type = (String)requestEarnItem.get("part_type");
			pro_type = (String)requestEarnItem.get("pro_type");
			su_year_1 = (String)requestEarnItem.get("su_year_1");
			su_year_2 = (String)requestEarnItem.get("su_year_2");
			su_year_3 = (String)requestEarnItem.get("su_year_3");
			su_year_4 = (String)requestEarnItem.get("su_year_4");
			su_year_5 = (String)requestEarnItem.get("su_year_5");
			su_year_6 = (String)requestEarnItem.get("su_year_6");
			royalty = (String)requestEarnItem.get("royalty");
			start_pro = (String)requestEarnItem.get("start_pro");
			store = (String)requestEarnItem.get("store");
			dest = (String)requestEarnItem.get("dest");
			client_cost = (String)requestEarnItem.get("client_cost");
			ket_cost = StringUtil.checkNullZero((String)requestEarnItem.get("ket_cost"));
			target_cost = (String)requestEarnItem.get("target_cost");
			assy_note = (String)requestEarnItem.get("assy_note");
			dis_cost = (String)requestEarnItem.get("dis_cost");
			re_m_cost = (String)requestEarnItem.get("re_m_cost");
			usage = (String)requestEarnItem.get("usage");
			meterial = (String)requestEarnItem.get("meterial");
			t_size = (String)requestEarnItem.get("t_size");
			w_size = (String)requestEarnItem.get("w_size");
			p_size = (String)requestEarnItem.get("p_size");
			plating = (String)requestEarnItem.get("plating");
			m_maker = (String)requestEarnItem.get("m_maker");
			m_mone = (String)requestEarnItem.get("m_mone");
			unitcost = (String)requestEarnItem.get("unitcost");
			order_con = (String)requestEarnItem.get("order_con");
			h_weight = (String)requestEarnItem.get("h_weight");
			h_scrap = (String)requestEarnItem.get("h_scrap");
			t_weight = (String)requestEarnItem.get("t_weight");
			recycle = (String)requestEarnItem.get("recycle");
			die_no = (String)requestEarnItem.get("die_no");
			cavity = (String)requestEarnItem.get("cavity");
			make_type = (String)requestEarnItem.get("make_type");
			pro_1 = (String)requestEarnItem.get("pro_1");
			ton = (String)requestEarnItem.get("ton");
			spm = (String)requestEarnItem.get("spm");
			pro_level = (String)requestEarnItem.get("pro_level");
			pro_level_txt = (String)requestEarnItem.get("pro_level_txt");
			type_1 = (String)requestEarnItem.get("type_1");
			type_2 = (String)requestEarnItem.get("type_2");
			t_mone = (String)requestEarnItem.get("t_mone");
			type_cost = (String)requestEarnItem.get("type_cost");
			t_order = (String)requestEarnItem.get("t_order");
			pack_type = (String)requestEarnItem.get("pack_type");
			in_pack = (String)requestEarnItem.get("in_pack");
			out_pack = (String)requestEarnItem.get("out_pack");
			yazaki_ro = (String)requestEarnItem.get("yazaki_ro");
			m_su = (String)requestEarnItem.get("m_su");
			mold_cost = (String)requestEarnItem.get("mold_cost");
			to_cost = (String)requestEarnItem.get("to_cost");
			line_su = (String)requestEarnItem.get("line_su");
			sul_cost = (String)requestEarnItem.get("sul_cost");
			etc_cost = (String)requestEarnItem.get("etc_cost");
			part_note = (String)requestEarnItem.get("part_note");
			distri_cost = (String)requestEarnItem.get("distri_cost");
			meterial_cost = (String)requestEarnItem.get("meterial_cost");
			m_total_cost = StringUtil.checkNullZero((String)requestEarnItem.get("m_total_cost"));
			labor_cost = StringUtil.checkNullZero((String)requestEarnItem.get("labor_cost"));
			common_cost = StringUtil.checkNullZero((String)requestEarnItem.get("common_cost"));
			actual_cost = StringUtil.checkNullZero((String)requestEarnItem.get("actual_cost"));
			earn_rate = (String)requestEarnItem.get("earn_rate");
			loss = (String)requestEarnItem.get("loss");
			scrap_cost = (String)requestEarnItem.get("scrap_cost");
			output = (String)requestEarnItem.get("output");
			rate = (String)requestEarnItem.get("rate");
			eff_value = (String)requestEarnItem.get("eff_value");
			rnd_cost = (String)requestEarnItem.get("rnd_cost");
			jun_cost = (String)requestEarnItem.get("jun_cost");
			ma_depr = (String)requestEarnItem.get("ma_depr");
			m_upkeep = (String)requestEarnItem.get("m_upkeep");
			total_expense = (String)requestEarnItem.get("total_expense");
			overhead = (String)requestEarnItem.get("overhead");
			outsource = (String)requestEarnItem.get("outsource");
			etc_expense = (String)requestEarnItem.get("etc_expense");
			gita_cost = (String)requestEarnItem.get("gita_cost");
			pack_cost = (String)requestEarnItem.get("pack_cost");
			yzk_depr = (String)requestEarnItem.get("yzk_depr");
			tabalu = (String)requestEarnItem.get("tabalu");
			start_depr = (String)requestEarnItem.get("start_depr");
			pro_depr = (String)requestEarnItem.get("pro_depr");
			qu_cost = (String)requestEarnItem.get("qu_cost");
			tariff = (String)requestEarnItem.get("tariff");
			royalty_cost = (String)requestEarnItem.get("royalty_cost");
			jae_cost = (String)requestEarnItem.get("jae_cost");
			ge_cost = (String)requestEarnItem.get("ge_cost");
			cost_report_add = (String)requestEarnItem.get("cost_report_add");
			depr_cost = (String)requestEarnItem.get("depr_cost");
			ad_cost = (String)requestEarnItem.get("ad_cost");
			case_type_admin = (String)requestEarnItem.get("case_type_admin");
			case_type_user = (String)requestEarnItem.get("case_type_user");

			calc_1  = new BigDecimal(StringUtil.Roundformat(m_total_cost,4));
			calc_2  = new BigDecimal(StringUtil.Roundformat(labor_cost,4));
			calc_3  = new BigDecimal(StringUtil.Roundformat(common_cost,4));


			sum_meterial = Double.toString(calc_1.add(calc_2).add(calc_3).doubleValue());

			calc_1  = new BigDecimal(StringUtil.Roundformat(m_total_cost,4));
			calc_2  = new BigDecimal(StringUtil.Roundformat(actual_cost,4));
			p_m_total_cost = StringUtil.Doubledivide(calc_1, calc_2);

			calc_1  = new BigDecimal(StringUtil.Roundformat(labor_cost,4));
			calc_2  = new BigDecimal(StringUtil.Roundformat(actual_cost,4));

			p_labor_cost = StringUtil.Doubledivide(calc_1, calc_2);

			calc_1  = new BigDecimal(StringUtil.Roundformat(common_cost,4));
			calc_2  = new BigDecimal(StringUtil.Roundformat(actual_cost,4));

			p_common_cost = StringUtil.Doubledivide(calc_1, calc_2);

			calc_1  = new BigDecimal(StringUtil.Roundformat(sum_meterial,4));
			calc_2  = new BigDecimal(StringUtil.Roundformat(actual_cost,4));

			p_sum_meterial = StringUtil.Doubledivide(calc_1, calc_2);

			calc_1  = new BigDecimal(StringUtil.Roundformat(ad_cost,4));
			calc_2  = new BigDecimal(StringUtil.Roundformat(actual_cost,4));

			p_ad_cost = StringUtil.Doubledivide(calc_1, calc_2);

			calc_1  = new BigDecimal(StringUtil.Roundformat(depr_cost,4));
			calc_2  = new BigDecimal(StringUtil.Roundformat(actual_cost,4));

			p_g_depr_cost = StringUtil.Doubledivide(calc_1, calc_2);

			calc_1  = new BigDecimal(StringUtil.Roundformat(actual_cost,4));
			calc_2  = new BigDecimal(StringUtil.Roundformat(actual_cost,4));

			p_actual_cost = StringUtil.Doubledivide(calc_1, calc_2);

			calc_1  = new BigDecimal(earn_rate);
			calc_2  = new BigDecimal("100");
			result_earn_rate = df.format(calc_1.multiply(calc_2).doubleValue());

			calc_1  = new BigDecimal(p_m_total_cost);
			calc_2  = new BigDecimal("100");

			p_m_total_cost = df.format(calc_1.multiply(calc_2).doubleValue());

			calc_1  = new BigDecimal(p_labor_cost);
			calc_2  = new BigDecimal("100");

			p_labor_cost = df.format(calc_1.multiply(calc_2).doubleValue());

			calc_1  = new BigDecimal(p_common_cost);
			calc_2  = new BigDecimal("100");
			p_common_cost = df.format(calc_1.multiply(calc_2).doubleValue());

			calc_1  = new BigDecimal(p_sum_meterial);
			calc_2  = new BigDecimal("100");
			p_sum_meterial = df.format(calc_1.multiply(calc_2).doubleValue());

			calc_1  = new BigDecimal(p_ad_cost);
			calc_2  = new BigDecimal("100");
			p_ad_cost = df.format(calc_1.multiply(calc_2).doubleValue());

			calc_1  = new BigDecimal(p_g_depr_cost);
			calc_2  = new BigDecimal("100");
			p_g_depr_cost = df.format(calc_1.multiply(calc_2).doubleValue());

			calc_1  = new BigDecimal(p_actual_cost);
			calc_2  = new BigDecimal("100");
			p_actual_cost = df.format(calc_1.multiply(calc_2).doubleValue());


%>
					<tr>
						<td bgcolor="#FFFFDD" class="style1"><input type="checkbox" name="chk_list" value="<%=pk_cw%>" onclick="javascript:changeValue(this);"></td>
						<td width="34" bgcolor="#FFFFDD" class="style11"><%if(!"main".equals(case_type_admin) || !"main".equals(case_type_user)){out.println("case");}%></td>
						<td width="167" height="21" bgcolor="#FFFFDD" class="style1"><%=part_name%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1"><%=usage%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1"><%=StringUtil.Roundformat(m_total_cost, 2)%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1"><%=StringUtil.Roundformat(labor_cost,2)%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1"><%=StringUtil.Roundformat(common_cost,2)%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1"><%=StringUtil.Roundformat(sum_meterial,2)%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1"><%=StringUtil.Roundformat(ad_cost,2)%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1"><%=StringUtil.Roundformat(depr_cost,2)%></td>
						<td height="21" bgcolor="#FFFFDD" class="style11"><%=StringUtil.Roundformat(actual_cost,2)%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1"><%=StringUtil.Roundformat(ket_cost,2)%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1"><%= result_earn_rate+"%"%></td>
						<td height="21" bgcolor="#FFFFDD" class="style1">&nbsp;</td>
						<td height="21" bgcolor="#FFFFDD" class="style1">&nbsp;</td>
					</tr>
					<tr>
						<td bgcolor="#FFFFFF" class="style2">&nbsp;</td>
						<td bgcolor="#FFFFFF" class="style6">&nbsp;</td>
						<td height="21" bgcolor="#FFFFFF" class="style6">총원가대비 구성비 </td>
						<td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
						<td height="21" bgcolor="#FFFFFF" class="style6"><%=p_m_total_cost+"%"%></td>
						<td height="21" bgcolor="#FFFFFF" class="style6"><%=p_labor_cost+"%"%></td>
						<td height="21" bgcolor="#FFFFFF" class="style6"><%=p_common_cost+"%"%></td>
						<td height="21" bgcolor="#FFFFFF" class="style6"><%=p_sum_meterial+"%"%></td>
						<td height="21" bgcolor="#FFFFFF" class="style6"><%=p_ad_cost+"%"%></td>
						<td height="21" bgcolor="#FFFFFF" class="style6"><%=p_g_depr_cost+"%"%></td>
						<td height="21" bgcolor="#FFFFFF" class="style6"><%=p_actual_cost+"%"%></td>
						<td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
						<td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
						<td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
						<td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
					</tr>
					<br>
<%
		}
	}
%>
				</table>
			<br>
			</td>
		</tr>
		<tr>
			<td height="50" colspan="4" class="style8">
				<table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td ><img src="/plm/jsp/cost/acc_img/btn_in.gif"border="0" onClick="opener_call();" style="cursor:pointer;"/><img src="/plm/jsp/cost/acc_img/btn_cancel.gif" border="0" onClick="back_call();" style="cursor:pointer;"/></td>
        </tr>
      </table>
			</td>
		</tr>
	</table>
</Form>
</body>
</html>
