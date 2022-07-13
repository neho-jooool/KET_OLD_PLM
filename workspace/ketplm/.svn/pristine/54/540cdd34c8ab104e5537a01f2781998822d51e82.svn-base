$(document).ready(function(){
    $("input[name=select_cost]").click(function() {
        var select_val = $(this).val();
        var report_pk = document.forms[0].report_pk.value;
        var select_case = document.forms[0].select_case.value;
        var td_text = "2. 예상 손익 및 환율, 원재료 가격 기준 ("+select_val+"안기준)";
        $("#gijun_td").text(td_text);
        if(report_pk != "" && select_case != "ok"){
        	searchCostWork(select_val,report_pk);
        	select_cost_gijun(select_val);
        }
    });
});

function select_cost_gijun(select_cost){

	$(".change").parent().removeClass("redBorder");
	$(".change_left").parent().removeClass('redBorder_left');
    $(".change_right").parent().removeClass('redBorder_right');
    $(".change_left_bottom").parent().removeClass('redBorder_left_bottom');
    $(".change_right_bottom").parent().removeClass('redBorder_right_bottom');

    $(".change_1").parent().removeClass("redBorder");
	$(".change_left_1").parent().removeClass('redBorder_left');
    $(".change_right_1").parent().removeClass('redBorder_right');
    $(".change_left_bottom_1").parent().removeClass('redBorder_left_bottom');
    $(".change_right_bottom_1").parent().removeClass('redBorder_right_bottom');

    $(".change_2").parent().removeClass("redBorder");
	$(".change_left_2").parent().removeClass('redBorder_left');
    $(".change_right_2").parent().removeClass('redBorder_right');
    $(".change_left_bottom_2").parent().removeClass('redBorder_left_bottom');
    $(".change_right_bottom_2").parent().removeClass('redBorder_right_bottom');

	if(select_cost == "1"){
	    $(".change").parent().toggleClass('redBorder');
	    $(".change_left").parent().toggleClass('redBorder_left');
	    $(".change_right").parent().toggleClass('redBorder_right');
	    $(".change_left_bottom").parent().toggleClass('redBorder_left_bottom');
	    $(".change_right_bottom").parent().toggleClass('redBorder_right_bottom');
	}

    if(select_cost == "2"){
	    $(".change_1").parent().toggleClass('redBorder');
	    $(".change_left_1").parent().toggleClass('redBorder_left');
	    $(".change_right_1").parent().toggleClass('redBorder_right');
	    $(".change_left_bottom_1").parent().toggleClass('redBorder_left_bottom');
	    $(".change_right_bottom_1").parent().toggleClass('redBorder_right_bottom');
    }

    if(select_cost == "3"){
	    $(".change_2").parent().toggleClass('redBorder');
	    $(".change_left_2").parent().toggleClass('redBorder_left');
	    $(".change_right_2").parent().toggleClass('redBorder_right');
	    $(".change_left_bottom_2").parent().toggleClass('redBorder_left_bottom');
	    $(".change_right_bottom_2").parent().toggleClass('redBorder_right_bottom');
    }
}

function searchCostWork(select_val,report_pk){
	var queryUrl = "/plm/jsp/cost/costreport/selectCostWork.jsp?select_val="+select_val+"&report_pk="+report_pk;
	$.ajax({
		url:queryUrl,
		type:"POST",
		dataType:"xml",
		success:function(data){
			var total_sales_1 = "";
			var profit_1 = "";
			var per_profit_1 = "";
			var total_sales_2 = "";
			var profit_2 = "";
			var per_profit_2 = "";
			var total_sales_3 = "";
			var profit_3 = "";
			var per_profit_3 = "";
			var total_sales_4 = "";
			var profit_4 = "";
			var per_profit_4 = "";
			var total_sales_5 = "";
			var profit_5 = "";
			var per_profit_5 = "";
			var total_sales_6 = "";
			var profit_6 = "";
			var per_profit_6 = "";
			var total_sales_7 = "";
			var profit_7 = "";
			var per_profit_7 = "";
			var total_sales_8 = "";
			var profit_8 = "";
			var per_profit_8 = "";

			var sum_sales = "";
			var sum_profit = "";
			var sum_per_p = "";



			$(data).find("List1").each(function(){
				total_sales_1		= $(this).attr("total_sales_1");
				profit_1			= $(this).attr("profit_1");
				per_profit_1		= $(this).attr("per_profit_1");
			});


			$(data).find("List2").each(function(){
				total_sales_2		= $(this).attr("total_sales_2");
				profit_2			= $(this).attr("profit_2");
				per_profit_2		= $(this).attr("per_profit_2");
			});
			$(data).find("List3").each(function(){
				total_sales_3		= $(this).attr("total_sales_3");
				profit_3			= $(this).attr("profit_3");
				per_profit_3		= $(this).attr("per_profit_3");
			});
			$(data).find("List4").each(function(){
				total_sales_4		= $(this).attr("total_sales_4");
				profit_4			= $(this).attr("profit_4");
				per_profit_4		= $(this).attr("per_profit_4");
			});
			$(data).find("List5").each(function(){
				total_sales_5		= $(this).attr("total_sales_5");
				profit_5			= $(this).attr("profit_5");
				per_profit_5		= $(this).attr("per_profit_5");
			});
			$(data).find("List6").each(function(){
				total_sales_6		= $(this).attr("total_sales_6");
				profit_6			= $(this).attr("profit_6");
				per_profit_6		= $(this).attr("per_profit_6");
			});
			$(data).find("List7").each(function(){
				total_sales_7		= $(this).attr("total_sales_7");
				profit_7			= $(this).attr("profit_7");
				per_profit_7		= $(this).attr("per_profit_7");
			});
			$(data).find("List8").each(function(){
				total_sales_8		= $(this).attr("total_sales_8");
				profit_8			= $(this).attr("profit_8");
				per_profit_8		= $(this).attr("per_profit_8");
			});

			$(data).find("List_sum").each(function(){
				sum_sales		= $(this).attr("sum_sales");
				sum_profit			= $(this).attr("sum_profit");
				sum_per_p		= $(this).attr("sum_per_p");
			});

			for(i=0; i<document.forms[0].table_row_count.value; i++){
				document.getElementById("total_sales_1_"+eval(i)).value = total_sales_1;
				document.getElementById("total_sales_2_"+eval(i)).value = total_sales_2;
				document.getElementById("total_sales_3_"+eval(i)).value = total_sales_3;
				document.getElementById("total_sales_4_"+eval(i)).value = total_sales_4;
				document.getElementById("total_sales_5_"+eval(i)).value = total_sales_5;
				document.getElementById("total_sales_6_"+eval(i)).value = total_sales_6;
				document.getElementById("total_sales_7_"+eval(i)).value = total_sales_7;
				document.getElementById("total_sales_8_"+eval(i)).value = total_sales_8;
				document.getElementById("profit_1_"+eval(i)).value = profit_1;
				document.getElementById("profit_2_"+eval(i)).value = profit_2;
				document.getElementById("profit_3_"+eval(i)).value = profit_3;
				document.getElementById("profit_4_"+eval(i)).value = profit_4;
				document.getElementById("profit_5_"+eval(i)).value = profit_5;
				document.getElementById("profit_6_"+eval(i)).value = profit_6;
				document.getElementById("profit_7_"+eval(i)).value = profit_7;
				document.getElementById("profit_8_"+eval(i)).value = profit_8;
				document.getElementById("per_profit_1_"+eval(i)).value = per_profit_1;
				document.getElementById("per_profit_2_"+eval(i)).value = per_profit_2;
				document.getElementById("per_profit_3_"+eval(i)).value = per_profit_3;
				document.getElementById("per_profit_4_"+eval(i)).value = per_profit_4;
				document.getElementById("per_profit_5_"+eval(i)).value = per_profit_5;
				document.getElementById("per_profit_6_"+eval(i)).value = per_profit_6;
				document.getElementById("per_profit_7_"+eval(i)).value = per_profit_7;
				document.getElementById("per_profit_8_"+eval(i)).value = per_profit_8;
				/*if(document.getElementById("total_sales_1_"+i) == null){

					//eval("document.forms[0].total_sales_1_")+eval(i).value = total_sales_1;
					var total_sales_1_temp = document.createElement("input");
					total_sales_1_temp.type = "text";
					total_sales_1_temp.name = "total_sales_1_"+i;
					total_sales_1_temp.id = "total_sales_1_"+i;
					total_sales_1_temp.value = total_sales_1;
					document.part_1.appendChild(total_sales_1_temp);
					alert(document.getElementById(total_sales_1_temp.id).value);
				}*/

			}
			if(total_sales_1 == 0){
				$("#total_sales_1_view").text("");
			}else{
				$("#total_sales_1_view").text(total_sales_1);
			}

			if(total_sales_2 == 0){
				$("#total_sales_2_view").text("");
			}else{
				$("#total_sales_2_view").text(total_sales_2);
			}

			if(total_sales_3 == 0){
				$("#total_sales_3_view").text("");
			}else{
				$("#total_sales_3_view").text(total_sales_3);
			}


			if(total_sales_4 == 0){
				$("#total_sales_4_view").text("");
			}else{
				$("#total_sales_4_view").text(total_sales_4);
			}

			if(total_sales_5 == 0){
				$("#total_sales_5_view").text("");
			}else{
				$("#total_sales_5_view").text(total_sales_5);
			}

			if(total_sales_6 == 0){
				$("#total_sales_6_view").text("");
			}else{
				$("#total_sales_6_view").text(total_sales_6);
			}

			if(total_sales_7 == 0){
				$("#total_sales_7_view").text("");
			}else{
				$("#total_sales_7_view").text(total_sales_7);
			}

			if(total_sales_8 == 0){
				$("#total_sales_8_view").text("");
			}else{
				$("#total_sales_8_view").text(total_sales_8);
			}



			if(profit_1 == 0){
				$("#profit_1_view").text("");
			}else{
				$("#profit_1_view").text(profit_1);

				if(profit_1 >= 0){
					$("#profit_1_view").attr("class","style13");
				}else{
					$("#profit_1_view").attr("class","style11");
				}
			}

			if(profit_2 == 0){
				$("#profit_2_view").text("");
			}else{
				$("#profit_2_view").text(profit_2);

				if(profit_2 >= 0){
					$("#profit_2_view").attr("class","style13");
				}else{
					$("#profit_2_view").attr("class","style11");
				}
			}

			if(profit_3 == 0){
				$("#profit_3_view").text("");
			}else{
				$("#profit_3_view").text(profit_3);

				if(profit_1 >= 0){
					$("#profit_3_view").attr("class","style13");
				}else{
					$("#profit_3_view").attr("class","style11");
				}
			}

			if(profit_4 == 0){
				$("#profit_4_view").text("");
			}else{
				$("#profit_4_view").text(profit_4);

				if(profit_4 >= 0){
					$("#profit_4_view").attr("class","style13");
				}else{
					$("#profit_4_view").attr("class","style11");
				}
			}

			if(profit_5 == 0){
				$("#profit_5_view").text("");
			}else{
				$("#profit_5_view").text(profit_5);

				if(profit_5 >= 0){
					$("#profit_5_view").attr("class","style13");
				}else{
					$("#profit_5_view").attr("class","style11");
				}
			}

			if(profit_6 == 0){
				$("#profit_6_view").text("");
			}else{
				$("#profit_6_view").text(profit_6);

				if(profit_6 >= 0){
					$("#profit_6_view").attr("class","style13");
				}else{
					$("#profit_6_view").attr("class","style11");
				}
			}

			if(profit_7 == 0){
				$("#profit_7_view").text("");
			}else{
				$("#profit_7_view").text(profit_7);

				if(profit_7 >= 0){
					$("#profit_7_view").attr("class","style13");
				}else{
					$("#profit_7_view").attr("class","style11");
				}
			}

			if(profit_8 == 0){
				$("#profit_8_view").text("");
			}else{
				$("#profit_8_view").text(profit_8);

				if(profit_8 >= 0){
					$("#profit_8_view").attr("class","style13");
				}else{
					$("#profit_8_view").attr("class","style11");
				}
			}

			var view_per_1 = per_profit_1*100;
			var view_per_2 = per_profit_2*100;
			var view_per_3 = per_profit_3*100;
			var view_per_4 = per_profit_4*100;
			var view_per_5 = per_profit_5*100;
			var view_per_6 = per_profit_6*100;
			var view_per_7 = per_profit_7*100;
			var view_per_8 = per_profit_8*100;

			if(view_per_1 == 0){
				$("#per_profit_1_view").text("");
			}else{
				$("#per_profit_1_view").text(view_per_1.toFixed(1)+"%");

				if(view_per_1 >= 0){
					$("#per_profit_1_view").attr("class","style13");
				}else{
					$("#per_profit_1_view").attr("class","style11");
				}
			}

			if(view_per_2 == 0){
				$("#per_profit_2_view").text("");
			}else{
				$("#per_profit_2_view").text(view_per_2.toFixed(1)+"%");

				if(view_per_2 >= 0){
					$("#per_profit_2_view").attr("class","style13");
				}else{
					$("#per_profit_2_view").attr("class","style11");
				}
			}

			if(view_per_3 == 0){
				$("#per_profit_3_view").text("");
			}else{
				$("#per_profit_3_view").text(view_per_4.toFixed(1)+"%");

				if(view_per_3 >= 0){
					$("#per_profit_3_view").attr("class","style13");
				}else{
					$("#per_profit_3_view").attr("class","style11");
				}
			}

			if(view_per_4 == 0){
				$("#per_profit_4_view").text("");
			}else{
				$("#per_profit_4_view").text(view_per_4.toFixed(1)+"%");

				if(view_per_4 >= 0){
					$("#per_profit_4_view").attr("class","style13");
				}else{
					$("#per_profit_4_view").attr("class","style11");
				}
			}

			if(view_per_5 == 0){
				$("#per_profit_5_view").text("");
			}else{
				$("#per_profit_5_view").text(view_per_5.toFixed(1)+"%");

				if(view_per_5 >= 0){
					$("#per_profit_5_view").attr("class","style13");
				}else{
					$("#per_profit_5_view").attr("class","style11");
				}
			}

			if(view_per_6 == 0){
				$("#per_profit_6_view").text("");
			}else{
				$("#per_profit_6_view").text(view_per_6.toFixed(1)+"%");

				if(view_per_1 >= 0){
					$("#per_profit_6_view").attr("class","style13");
				}else{
					$("#per_profit_6_view").attr("class","style11");
				}
			}

			if(view_per_7 == 0){
				$("#per_profit_7_view").text("");
			}else{
				$("#per_profit_7_view").text(view_per_7.toFixed(1)+"%");

				if(view_per_7 >= 0){
					$("#per_profit_7_view").attr("class","style13");
				}else{
					$("#per_profit_7_view").attr("class","style11");
				}
			}

			if(view_per_8 == 0){
				$("#per_profit_8_view").text("");
			}else{
				$("#per_profit_8_view").text(view_per_8.toFixed(1)+"%");

				if(view_per_8 >= 0){
					$("#per_profit_8_view").attr("class","style13");
				}else{
					$("#per_profit_8_view").attr("class","style11");
				}
			}


			$("#sum_sales_view").text(sum_sales);
			$("#sum_profit_view").text(sum_profit);
			$("#sum_per_p_view").text(sum_per_p+"%");

			if(sum_profit >= 0){
				$("#sum_profit_view").attr("class","style13");
			}else{
				$("#sum_profit_view").attr("class","style11");
			}

			if(sum_per_p >= 0){
				$("#sum_per_p_view").attr("class","style13");
			}else{
				$("#sum_per_p_view").attr("class","style11");
			}
		}
	});
}