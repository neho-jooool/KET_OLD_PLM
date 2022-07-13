$(document).ready(function(){
	// 기본 리스트 호출
	getUserList();

	// 권한 검색 리스트
	$("#searchAuth").click(function(){
		searchAuth();
	});

	// 검색 enter
	$("#userName").keydown(function(e){
		if(e.keyCode == 13){
			searchAuth();
			return false;
		}
	});

	// 사용자 권한 수정
	$("#modifyAuth").click(function(){
		modifyAuth();
	});

	// 권한 등록 팝업
	$("#insertUser").click(function() {
		insertUser();
	});

	$("#account").numeric();
	$("#account").css("ime-mode", "disabled");
	$("#account").keyup(function(){
		if(document.forms[0].account.value.length == 8){
			searchEPuser();
		}
	});

	// 권한 등록
	$("#insertAuth").click(function() {
		insertAuth();
	});

	// 권한 삭제
	$("#deleteAuth").click(function() {
		deleteAuth();
	});
});

$(function () {
	$(".tb").fixedtableheader();
});

// 기본 리스트
function getUserList(){
	var queryUrl = "/plm/jsp/cost/auth/defaultAction.jsp";
	$.ajax({
		url:queryUrl,
		type:"POST",
		dataType:"xml",
		success:function(data){
			makeAuthList(data);
		}
	});
}

//조회 리스트
function searchAuth(){
	var userName = document.forms[0].userName.value;
	userName = escape(encodeURIComponent(userName));
	var queryUrl = "/plm/jsp/cost/auth/searchAction.jsp?user="+userName;
	$.ajax({
		url:queryUrl,
		type:"POST",
		dataType:"xml",
		success:function(data){
			makeSearchAuthList(data);
		}
	});
}

// 사용자 권한수정 및 사용자삭제 팝업
function updateAuth(account){
	$.smartPop.open({title: "권한수정및삭제",
		width: 570,
		height: 300,
		url: "/plm/jsp/cost/auth/modifyAuth.jsp?account="+account
	});
}

//권한 등록 팝업
function insertUser(){
	$.smartPop.open({title: "권한등록",
		width: 450,
		height: 200,
		url: "/plm/jsp/cost/auth/insertAuth.jsp"
	});
}

// 팝업 Close
function smartPopClose(){
	parent.$.smartPop.close();
}



//권한 수정
function modifyAuth(){
	var name = document.forms[0].name.value;
	var account = document.forms[0].empno.value;
	var auth = "";
	var authLength =document.forms[0].auth.length;
	for(i=0; i < authLength;i++){
		if(document.forms[0].auth[i].checked){
			auth = document.forms[0].auth[i].value;
		}
	}
	var group = "";
	if(document.forms[0].groupMaster.checked){
		group = "G";
	}

	var queryString = "/plm/jsp/cost/auth/modifyAuthAction.jsp?account="+account+"&auth="+auth+"&group="+group;

	$.ajax({
		url:queryString,
		type:"POST",
		dataType:"xml",
		success:function(data){
			var complet = $(data).find("isComplet").text();
			if(complet <= 0){
				alert("수정에 실패하였습니다.");
			}else{
				alert("수정 되었습니다.");
				parent.getAuthListReset(name);
				smartPopClose();
			}
		}
	});
}

//수정 및 삭제 후 페이지 Reloading
function getAuthListReset(name){
	var queryUrl = "/plm/jsp/cost/auth/searchAction.jsp?user="+name;
	$.ajax({
		url:queryUrl,
		type:"POST",
		dataType:"xml",
		success:function(data){
			makeSearchAuthList(data);
		}
	});
}

// EP사용자조회
function searchEPuser(){
	var account = document.forms[0].account.value;
	var queryUrl = "/plm/jsp/cost/auth/searchEPuser.jsp?account="+account;
	$.ajax({
		url:queryUrl,
		type:"POST",
		dataType:"xml",
		success:function(data){
			$(data).find("List").each(function(){
				var no						= $(this).attr("no")==""?"0":Number($(this).attr("no"));
				var account			= $(this).attr("account")==""?"&nbsp;":$(this).attr("account");
				var name				= $(this).text()==""?"&nbsp;":$(this).text();
				if(no > 0){
					$("#name").focus();
					document.forms[0].name.value = name;
					return;
				}else{
					alert("등록된 EP 사용자가 아닙니다.");
				}
			});
		}
	});
}

// 권한 등록
function insertAuth(){
	var account = document.forms[0].account.value;
	var name = document.forms[0].name.value;
	var group = "";
	var auth = "";
	var authLength =document.forms[0].auth.length;
	for(i=0; i < authLength;i++){
		if(document.forms[0].auth[i].checked){
			auth = document.forms[0].auth[i].value;
		}
	}
	if(document.forms[0].groupMaster.checked){
		group = "G";
	}

	if(account == ""){alert("사번을 입력하세요.");return;}
	if(name == ""){alert("이름을 입력하세요.");return;}
	if(auth == ""){alert("권한을 선택하세요.");return;}

	var queryString = "/plm/jsp/cost/auth/insertAuthAction.jsp?name="+name+"&account="+account+"&auth="+auth+"&group="+group;

	$.ajax({
		url:queryString,
		type:"POST",
		dataType:"xml",
		success:function(data){
			var complet = $(data).find("isComplet").text();
			if(complet <= 0){
				alert("등록에 실패하였습니다.");
			}else{
				if(complet == 99){
					alert("이미 등록된 사용자입니다.");
					parent.getAuthListReset(name);
					document.forms[0].account.value="";
					document.forms[0].name.value="";
					document.forms[0].groupMaster.checked = false;
					for(i=0; i < authLength;i++){
						document.forms[0].auth[i].checked = false;
					}
					return;
				}else{
					alert("등록 되었습니다.");
					parent.getAuthListReset(name);
					smartPopClose();
				}
			}
		}
	});
}

//권한 삭제
function deleteAuth(){
	var account = document.forms[0].empno.value;
	var queryString = "/plm/jsp/cost/auth/deleteAuthAction.jsp?account="+account;
	$.ajax({
		url:queryString,
		type:"POST",
		dataType:"xml",
		success:function(data){
			var complet = $(data).find("isComplet").text();
			if(complet <= 0){
				alert("삭제에 실패하였습니다.");
			}else{
				alert("삭제 되었습니다.");
				var name = "";
				smartPopClose();
				parent.getAuthListReset(name);
			}
		}
	});
}

//불러온 데이터를 이용하여 게시판 html 구조 만들기
function makeAuthList(data){
	$(data).find("List").each(function(){
		var no						= $(this).attr("no")==""?"0":Number($(this).attr("no"))+1;
		var account			= $(this).attr("account")==""?"&nbsp;":$(this).attr("account");
		var email				= $(this).attr("email")==""?"&nbsp;":$(this).attr("email");
		var groupName	= $(this).attr("groupName")==""?"&nbsp:":$(this).attr("groupName");
		var position			= $(this).attr("position")==""?"&nbsp;":$(this).attr("position");
		var auth					= $(this).attr("auth")==""?"&nbsp;":$(this).attr("auth");
		var name				= $(this).text()==""?"&nbsp;":$(this).text();
		var listFrame = "<tr>"+
									 "    <td align='center'>"+no+"</td>"+
									 "    <td align='center'><a href='javascript:updateAuth("+account+")'>"+account+"</a></td>"+
									 "    <td align='center'><a href='javascript:updateAuth("+account+")'>"+name+"</a></td>"+
									 "    <td>"+groupName+"</td>"+
									 "    <td>"+position+"</td>"+
									 "    <td>"+email+"</td>"+
									 "    <td  align='center' class='tdEnd'>"+auth+"</td>"+
									 "</tr>";

		$("#userList").append(listFrame);
	});
}

//불러온 데이터를 이용하여 조회된 게시판 html 구조 만들기
function makeSearchAuthList(data){
	var topFrame = "<tr>"+
	"    <th width='68'>NO</th>"+
	 "    <th width='71'>사번</th>"+
	 "    <th width='61'>이름</th>"+
	 "    <th width='519'>부서</th>"+
	 "    <th width='111'>직급</th>"+
	 "    <th width='109'>메일주소</th>"+
	 "    <th width='91'>권한등급</th>"+
	 "</tr>";
	$("#userList").html("");
	$("#userList").html(topFrame);
	$(data).find("List").each(function(){
		var no						= $(this).attr("no")==""?"0":Number($(this).attr("no"))+1;
		var account			= $(this).attr("account")==""?"&nbsp;":$(this).attr("account");
		var email				= $(this).attr("email")==""?"&nbsp;":$(this).attr("email");
		var groupName	= $(this).attr("groupName")==""?"&nbsp:":$(this).attr("groupName");
		var position			= $(this).attr("position")==""?"&nbsp;":$(this).attr("position");
		var auth					= $(this).attr("auth")==""?"&nbsp;":$(this).attr("auth");
		var name				= $(this).text()==""?"&nbsp;":$(this).text();

		var listFrame = "<tr>"+
									 "    <td align='center'>"+no+"</td>"+
									 "    <td align='center'><a href='javascript:updateAuth("+account+")'>"+account+"</a></td>"+
									 "    <td align='center'><a href='javascript:updateAuth("+account+")'>"+name+"</a></td>"+
									 "    <td>"+groupName+"</td>"+
									 "    <td>"+position+"</td>"+
									 "    <td>"+email+"</td>"+
									 "    <td  align='center' class='tdEnd'>"+auth+"</td>"+
									 "</tr>";
		var userName = document.forms[0].userName.value;
		$("#userList").append(listFrame);
	});
}

