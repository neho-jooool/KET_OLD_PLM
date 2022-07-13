$(document).ready(function() {
	var cloneCount = $(".firstItem").length;
	//행추가
	$(".inputButton").live("click", function(){
		var clickRow = $(this).parent().parent();
		var cls = clickRow.attr("id");
		var listFrame = "<tr id='firstItem' class='firstItem'>"+
		"<td class='tdwhiteL' width='150'><img src='/plm/portal/images/iconPlus.gif' id='123' align='absmiddle' class='inputButton'/><img src='/plm/portal/images/iconMinus.gif' align='absmiddle' class='delButton'/><input id='planName' name='planName' value='' class='txt_field' style='width: 80px;' maxlength='10'/></td>"+
		"<td class='tdwhiteL' width='150'><input id='customerPlan"+cloneCount+"' name='customerPlan"+cloneCount+"' class='txt_field' style='width: 80px;' maxlength='10'/><a href='#' onclick='javascript:showCal(customerPlan"+cloneCount+");'><img src='/plm/portal/images/icon_6.png'></a>&nbsp;<a href=\"JavaScript:clearDate(\'customerPlan"+cloneCount+"\');\"><img src='/plm/portal/images/icon_delete.gif'></a></td>"+
		"</tr>";
		cloneCount++;
		$("#planTable").append(listFrame);
	});

	// 행(row) 삭제
	$(".delButton").live("click", function(){
		var clickedRow = $(this).parent().parent();
		var cls = clickedRow.attr("class");
		if($("."+cls).length > 1){
			clickedRow.remove();
		}else{
			alert("삭제 할 수 없습니다.");
			return;
		}
	});

	// input clear
	$("#planTable td input").live("click", function(){
		var customerID = $(this).attr("id");
		//alert(document.getElementById(customerID).value);
		if(customerID != "planName"){
			var tartxt = document.getElementById(customerID);
			tartxt.value = "";
		}
	});

	// input numric
	$("#planTable td input").live("keyup", function(){
		//alert($(this).attr("id"));
		check_number($(this).attr("id"))
	});

	//숫자만 입력
	 function check_number(id){
		var str = $("#"+id).val();
		var checkCode = str.charCodeAt(str);

		if(id != "planName"){
			//숫자체크
			if(str.length > 7 && str.length < 9){
				if(event.keyCode == 8){
					str = str.replace(/-/gi, "");
					document.getElementById(id).value=str;
				}else{
					str = str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8);
					document.getElementById(id).value=str;
				}
			}else{
				if(checkCode >= 33 && checkCode <= 47 || checkCode >= 58 && checkCode <= 125){
				   alert("숫자만 입력가능합니다.");
				   document.getElementById(id).value="";
				}
			}
		}
	 }
});