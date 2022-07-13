	$(document).ready(function() {
		$('.style9').autosize();
		$(".style1").numeric(); //영문과 숫자중 숫자만 받는다.
		$(".style1").css("ime-mode", "disabled"); //한글입력을 받지 않는다.

		rowSum1('to_cost_1');rowSum1('to_su_1');rowSum1('to_cost_sum_1');
		rowSum1('to_cost_2');rowSum1('to_su_2');rowSum1('to_cost_sum_2');
		rowSum1('to_cost_3');rowSum1('to_su_3');rowSum1('to_cost_sum_3');
		rowSum1('to_cost_4');rowSum1('to_su_4');rowSum1('to_cost_sum_4');
		rowSum1('to_cost_5');rowSum1('to_su_5');rowSum1('to_cost_sum_5');
		for(n=0;n<$("input[name='procost_name']").length;n++){
			rowSum1("to_procost_1_"+(n+1));
			rowSum1("to_procost_2_"+(n+1));
			rowSum1("to_procost_3_"+(n+1));
			rowSum1("to_procost_4_"+(n+1));
			rowSum1("to_procost_5_"+(n+1));
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

		function rowSum1(name){
			var sum = 0;
			for(i=0;i<$("input[name='"+name+"']").length;i++){
				cv = $("input[name='"+name+"']").eq(i).val();
				if(cv == "" || cv == " "){cv = "0";}
				sum = sum + parseInt(cv);
				if(sum==0){sum=" ";}
				$("#"+name+"_sum").text(sum);
			}
		}

		//행추가
		$(".mbutton").click(function(){
			var clickRow = $(this).parent().parent();
			var cls = clickRow.attr("id");
			var newRow = clickRow.clone();
			newRow.find(".style1").numeric();
			newRow.find("td:eq(0)").remove();
			//newRow.find("td:eq("+($("#invest_table tr:last td").length-1)+")").remove();
			newRow.insertAfter($("#invest_table #"+cls+":last"));
			$("#invest_table #"+cls+":last").find("input:text").val("").end();
			$("#invest_table #"+cls+":last").find("textarea").val("").end();
			//rowSpan
			resizeRowspan(cls);
		});

		$(".cButton").click(function(){
			//alert($("#invest_table tr:first td").length);
			if($("#invest_table tr:first td").length < 8){
				var clickRow = $(this).parent().parent();
				var cls = clickRow.attr("id");
				var newRow = clickRow.clone();
				newRow.insertAfter($("#invest_table #"+cls+":last"));
			}
			var otable = document.getElementById("invest_table");
			var rowIdx = otable.rows.length;
			var TR = "";
			var TD = "";
			var cellIdx = 0;
			var input_name = "";
			for(i=0;i<rowIdx;i++){
				TR = otable.rows[i];
				cellIdx = TR.cells.length;
				//alert(i +" ::: "+ $("#MSum").index() +" ::: "+ $("#PSum").index() +" ::: "+ $("#ISum").index() +" ::: "+ $("#ESum").index() +" ::: "+ $("#PKSum").index() +" ::: "+ $("#ASum").index());
				if(i == 1){
					if($("#invest_table tr:first td").length > 8){
						TD = TR.insertCell(cellIdx);
					}else{
						TD = TR.insertCell(cellIdx-3);
					}
				}else{
					if(i > 1 && $("#invest_table tr").length > 13){
						//alert(i+" ::: "+cellIdx +" ::: "+ (otable.rows[(i-1)].cells.length-3) +" ::: "+ (otable.rows[(i-1)].cells.length));
						if(i == 2){
							TD = TR.insertCell(cellIdx-3);
						}else if(i == $("#MSum").index() || i == $("#PSum").index() || i == $("#ISum").index() || i == $("#ESum").index() || i == $("#PKSum").index() || i == $("#ASum").index()){
							TD = TR.insertCell(cellIdx-3);
						}else if(cellIdx<(otable.rows[(i-1)].cells.length)){
							TD = TR.insertCell(cellIdx-2);
						}else{
							TD = TR.insertCell(cellIdx-3);
						}
					}else{
						TD = TR.insertCell(cellIdx-3);
					}
				}
				if (i == 0){
					TD.innerHTML = "제품명";
					TD.className = "style5";
				}else if(i == 1){
					TD.innerHTML = "<img src='/plm/jsp/cost/images/common/iconMinus.gif' onclick='dlCol()' align='middle'/><input name='procost_name' class='style1' size='8'/>";
					TD.className = "style5";
				}else if(i == $("#MSum").index()){
					TD.innerHTML = "&nbsp;";
					TD.style.background = "#FFFFCC";
					TD.className = "style1";
					if($("#invest_table tr:first td").length == 8){
						TD.id = "to_procost_1_1_sum";
					}else{
						TD.id = "to_procost_1_"+$("#invest_table tr:eq(1) td").length+"_sum";
					}
				}else if(i == $("#PSum").index()){
					TD.innerHTML = "&nbsp;";
					TD.style.background = "#FFFFCC";
					TD.className = "style1";
					if($("#invest_table tr:first td").length == 8){
						TD.id = "to_procost_2_1_sum";
					}else{
						TD.id = "to_procost_2_"+$("#invest_table tr:eq(1) td").length+"_sum";
					}
				}else if(i == $("#ISum").index()){
					TD.innerHTML = "&nbsp;";
					TD.style.background = "#FFFFCC";
					TD.className = "style1";
					if($("#invest_table tr:first td").length == 8){
						TD.id = "to_procost_3_1_sum";
					}else{
						TD.id = "to_procost_3_"+$("#invest_table tr:eq(1) td").length+"_sum";
					}
				}else if(i == $("#ESum").index()){
					TD.innerHTML = "&nbsp;";
					TD.style.background = "#FFFFCC";
					TD.className = "style1";
					if($("#invest_table tr:first td").length == 8){
						TD.id = "to_procost_4_1_sum";
					}else{
						TD.id = "to_procost_4_"+$("#invest_table tr:eq(1) td").length+"_sum";
					}
				}else if(i == $("#PKSum").index()){
					TD.innerHTML = "&nbsp;";
					TD.style.background = "#FFFFCC";
					TD.className = "style1";
					if($("#invest_table tr:first td").length == 8){
						TD.id = "to_procost_5_1_sum";
					}else{
						TD.id = "to_procost_5_"+$("#invest_table tr:eq(1) td").length+"_sum";
					}
				}else if(i == $("#ASum").index()){
					TD.innerHTML = "&nbsp;";
					TD.style.background = "#E4ECFA";
					TD.className = "style1";
					if($("#invest_table tr:first td").length == 8){
						TD.id = "to_procost_1_sum_sum";
					}else{
						TD.id = "to_procost_"+$("#invest_table tr:eq(1) td").length+"_sum_sum";
					}
				}else{
					if(i < $("#MSum").index()){
						if($("#invest_table tr:first td").length == 8){
							input_name = "to_procost_1_1";
						}else{
							input_name = "to_procost_1_"+$("#invest_table tr:eq(1) td").length;
						}
					}else if(i < $("#PSum").index()){
						if($("#invest_table tr:first td").length == 8){
							input_name = "to_procost_2_1";
						}else{
							input_name = "to_procost_2_"+$("#invest_table tr:eq(1) td").length;
						}
					}else if(i < $("#ISum").index()){
						if($("#invest_table tr:first td").length == 8){
							input_name = "to_procost_3_1";
						}else{
							input_name = "to_procost_3_"+$("#invest_table tr:eq(1) td").length;
						}
					}else if(i < $("#ESum").index()){
						if($("#invest_table tr:first td").length == 8){
							input_name = "to_procost_4_1";
						}else{
							input_name = "to_procost_4_"+$("#invest_table tr:eq(1) td").length;
						}
					}else if(i < $("#PKSum").index()){
						if($("#invest_table tr:first td").length == 8){
							input_name = "to_procost_5_1";
						}else{
							input_name = "to_procost_5_"+$("#invest_table tr:eq(1) td").length;
						}
					}
					TD.innerHTML = "<input name='"+input_name+"' class='style1' size='11' onKeyDown='javascript:numCheck(this);' style='ime-mode:disabled'/>";
					TD.style.background = "#FFFFFF";
					TD.className = "style1";
				}
			}

			//Title rowSpan
			if($("#invest_table tr:first td").length < 9){
				var o_tr = otable.rows(1);
				var o_tr2 = otable.rows(0);
				var o_td = "";
				var o_td2 = "";
				var cellCnt = $("#invest_table tr:first td").length;
				for(i=0;i<cellCnt;i++){
					if(i == cellCnt-3 || i == cellCnt-2 || i == cellCnt-1){
						o_td=o_tr.cells(1);
					}else{
						o_td=o_tr.cells(0);
					}
					o_td2 = o_tr2.cells(i);
					if(o_td.innerText == o_td2.innerText){
						o_td2.rowSpan +=o_td.rowSpan;
						o_td.removeNode(true);
					}
				}
			}

			//Title colSpan
			if($("#invest_table tr:first td").length > 8){
				var cellCnt = $("#invest_table tr:first td").length;
				var o_tr = otable.rows(0);
				for(i=4;i<cellCnt-3;i++){
					var o_td1 = o_tr.cells(i)
					var o_td2 = o_tr.cells(i-1)
					if(o_td1.innerText == o_td2.innerText){
						o_td2.colSpan += o_td1.colSpan;
						o_td1.removeNode(true);
					}
				}
			}
		});

	// 행(row) 삭제
	$(".delButton").live("click", function(){
		var clickedRow = $(this).parent().parent();
		var cls = clickedRow.attr("class");
		//alert($("#invest_table tr:eq("+$(".delButton").index(this)+") td").length);
		if($("."+cls).length > 1){
			// 각 항목의 첫번째 row를 삭제한 경우 다음 row에 td 하나를 추가
			if( clickedRow.find("td:eq(0)").attr("rowspan") ){
				if( clickedRow.next().hasClass(cls) ){
					clickedRow.next().prepend(clickedRow.find("td:eq(0)"));
					clickedRow.next().append(clickedRow.find("td:eq("+($("#invest_table tr:last td").length-1)+")"));
				}
			}
			clickedRow.remove();
			resizeRowspan(cls);
			if(cls == 'firstItem'){
				delRowSum('to_cost_1');delRowSum('to_su_1');delRowSum('to_cost_sum_1');
				if($("#invest_table tr:first td").length == 8){
					delRowSum("to_procost_1_1");
				}else{
					delRowSum("to_procost_1_"+$("#invest_table tr:eq(1) td").length);
				}
			}else if(cls == 'seconItem'){
				delRowSum('to_cost_2');delRowSum('to_su_2');delRowSum('to_cost_sum_2');
				if($("#invest_table tr:first td").length == 8){
					delRowSum("to_procost_2_1");
				}else{
					delRowSum("to_procost_2_"+$("#invest_table tr:eq(1) td").length);
				}
			}else if(cls == 'thirdItem'){
				delRowSum('to_cost_3');delRowSum('to_su_3');delRowSum('to_cost_sum_3');
				if($("#invest_table tr:first td").length == 8){
					delRowSum("to_procost_3_1");
				}else{
					delRowSum("to_procost_3_"+$("#invest_table tr:eq(1) td").length);
				}
			}else if(cls == 'fourItem'){
				delRowSum('to_cost_4');delRowSum('to_su_4');delRowSum('to_cost_sum_4');
				if($("#invest_table tr:first td").length == 8){
					delRowSum("to_procost_4_1");
				}else{
					delRowSum("to_procost_4_"+$("#invest_table tr:eq(1) td").length);
				}
			}else if(cls == 'fiveItem'){
				delRowSum('to_cost_5');delRowSum('to_su_5');delRowSum('to_cost_sum_5');
				if($("#invest_table tr:first td").length == 8){
					delRowSum("to_procost_5_1");
				}else{
					delRowSum("to_procost_5_"+$("#invest_table tr:eq(1) td").length);
				}
			}
		}else{
			alert("삭제 할 수 없습니다.");
			return;
		}
	});

		//rowSpan
		function resizeRowspan(cls){
			var rowspan = $("."+cls).length;
			$("#"+cls+":first td:eq(0)").attr("rowspan", rowspan);
			//$("#"+cls+":first td:eq("+($("#invest_table tr:last td").length)+")").attr("rowspan", rowspan);
			//$("#invest_table #"+cls+":first").find("textarea").attr("rows", rowspan);
		}
	});

	function delRowSum(name){
		var cv = "";
		var sum = 0;
		for(i=0;i<$("input[name='"+name+"']").length;i++){
			cv = $("input[name='"+name+"']").eq(i).val();
			if(cv == ""){cv = "0";}
			sum = sum + parseInt(cv);
			if(sum==0){sum=" ";}
			$("#"+name+"_sum").text(sum);
		}
		for(n=0;n<$("input[name='procost_name']").length;n++){
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
		if($("#to_cost_sum").text() == 0){$("#to_cost_sum").text(" ");}
		if($("#to_su_sum").text() == 0){$("#to_su_sum").text(" ");}
		if($("#to_cost_sum_sum").text() == 0){$("#to_cost_sum_sum").text(" ");}
	}

	//선택셀 Index 반환
	function getObj(){
		var obj = event.srcElement;
		while(obj.tagName != 'TD'){
			obj = obj.parentElement;
		}
		return obj;
	}
	//선택 셀 삭제
	function dlCol() {
		var idx = getObj().cellIndex;
		var colCnt = $("#invest_table tr:eq(1) td").length;
		for (var i = 0; i < invest_table.rows.length; i++) {
			if(i == 0){
				if(colCnt == 1){
					invest_table.rows(i).deleteCell(idx+4);
					for(var j=0;j<7;j++){
						document.getElementById("invest_table").rows(0).cells(j).rowSpan = 1;
					}
				}else{
					document.getElementById("invest_table").rows(0).cells(4).colSpan = (colCnt-1);
				}
			}else if(i == 1){
				invest_table.rows(i).deleteCell(idx);
			}else{
				//alert(i +" ::: "+$("#invest_table tr:eq("+(i)+") td").length +" ::: "+ ($("#invest_table tr:eq("+(i-1)+") td").length-1)+" ::: "+ ($("#MSum").index() + 1)+" ::: "+ ($("#PSum").index() + 1)+" ::: "+ ($("#ISum").index() + 1)+" ::: "+ ($("#ESum").index() + 1))
				if(i==2 || i == ($("#MSum").index() + 1) || i == ($("#PSum").index() + 1) || i == ($("#ISum").index() + 1) || i == ($("#ESum").index() + 1)){
					//alert(i + "                in if");
					invest_table.rows(i).deleteCell(idx+4);
				}else if($("#invest_table tr:eq("+(i)+") td").length >= ($("#invest_table tr:eq("+(i-1)+") td").length-1)){
					//alert(i+"            in else if");
					//if(i == $("#MSum").index() || i == $("#PSum").index() || i == $("#ISum").index() || i == $("#ESum").index() || i == $("#PKSum").index() || i == $("#ASum").index()){
						//invest_table.rows(i).deleteCell(idx+4);
						invest_table.rows(i).deleteCell(idx+3);
					//}else{
						//invest_table.rows(i).deleteCell(idx+3);
					//}
				}else{
					//alert(i+"            in else");
					invest_table.rows(i).deleteCell(idx+4);
				}
			}
		}
		if( colCnt == 1){
			invest_table.deleteRow(1);
		}
	}

	function numCheck(obj){

		var code = window.event.keyCode;
		if((code > 34 && code < 41) || (code > 47 && code < 58) || (code > 95 && code < 106) || code == 8 || code == 9 || code == 13 || code == 46){
			window.event.returnValue = true;
			rowSum(obj)
			return;
		}
		window.event.returnValue = false;
	}

	function rowSum(obj){
		var cv = "";
		var sum = 0;
		$("input[name='"+obj.name+"']").live("keyup", function(){
			var cv = "";
			var sum = 0;
			for(i=0;i<$("input[name='"+obj.name+"']").length;i++){
				cv = $("input[name='"+obj.name+"']").eq(i).val();
				if(cv == "" || cv == " "){cv = "0";}
				sum = sum + parseInt(cv);
				if(sum==0){sum=" ";}
				$("#"+obj.name+"_sum").text(sum);
			}
			for(n=0;n<$("input[name='procost_name']").length;n++){
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
			if($("#to_cost_sum").text() == 0){$("#to_cost_sum").text(" ");}
			if($("#to_su_sum").text() == 0){$("#to_su_sum").text(" ");}
			if($("#to_cost_sum_sum").text() == 0){$("#to_cost_sum_sum").text(" ");}
		});
	}
