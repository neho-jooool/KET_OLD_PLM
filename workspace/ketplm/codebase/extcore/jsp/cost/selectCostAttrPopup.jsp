<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<style>
html{
	height:100%;
}
.contents-wrap {
	height:100%;
}
</style>
<script> 
$(document).ready(function(){
	
	$("#attribute").load("/plm/ext/cost/selectCostAttribute",function(){
		$("button[data-code]").click(function(){
			var isComplete = opener.addPopupValue($(this).text(), $(this).data("code"), "mapping");
			if(isComplete)	self.close();
		});
	});
	
	//드래그 & 드롭 편집 막기
	$('#formula').on('drop', function(e) {
    	e.preventDefault();
	});
	
	$(window).resize(function(){
		$('body').height($('html').height() - 50);
		$('#attribute').height($('body').height() - 130);
	});
	
	$('body').height($('html').height() - 50);
	$('#attribute').height($('body').height() - 130);
});


</script>
<body class="popup-background popup-space">
	<div class="contents-wrap">
		<div class="sub-title b-space5">
			<img src="/plm/portal/images/icon_3.png" />수식 컬럼 매핑
		</div>
		<div class="b-space5 float-r" style="text-align: right">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
		</div>
		<div class="sub-title-02 float-l b-space5">
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>부품속성
		</div>
		<div class="b-space5" >
			<table summary="" class="table-style-01">
				<tbody>
					<tr>
						<td class="center"><div id="attribute"></div></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
