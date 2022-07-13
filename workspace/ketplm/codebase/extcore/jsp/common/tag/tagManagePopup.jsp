<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<style>
.desc{font-weight:bold;color:#FF0000;font-family: NanumGothic, '나눔고딕', Nanumgo, Dotum;font-family: NanumBold !important; font-size:14px;}
.tagList{width:100%;height:100%;}
.tagList li{padding:3px;}
.tagList li:hover{background-color:#EDEDED;cursor:pointer;}
</style>
<script>
$(document).ready(function(){

	//드래그&드롭
	$(".tagList").sortable({
		connectWith : ".tagList",
		start : function(event, ui) {
			//ui.item.toggleClass("highlight");
		},
		stop : function(event, ui) {
			//ui.item.toggleClass("highlight");
		},
		update : function(a){
			//return false;
		}
	});
	$(".tagList").disableSelection();
	
	//더블클릭
	$(".tagList li").dblclick(function(e){
		
		var isdisableTag = $(this).parents("ul").hasClass("disableTag");
		
		if(isdisableTag){
			$(".enableTag").append(this);
		}else{
			$(".disableTag").append(this);
		}
	});
	
	//태그 검색
	$("input[name=searchInput]").keyup(function(e){
		var value = $(this).val();
		
		window.console.log(value);
		
		if(value == null || value == ""){
			$(".disableTag li").each(function(){
	            $(this).show();
	        });
		}else{
			$(".disableTag li").each(function(){
				
				var text = $(this).text();
				
				if(text.indexOf(value) >= 0){
					$(this).show();
				}else{
					$(this).hide();
				}
	        });
		}
	});
	
	//선택된 태그 이동
	if(opener){
		$(opener.document).find(".REFTAG input[name=refTag]").each(function(){
			var id = $(this).val();
			$(".disableTag li[data-id='" + id + "']").dblclick();
		});
	}
});

//태그저장
window.saveProjectTag = function(){
	
	var enableTagList = new Array();
    var tagHtml = "";
    $(".enableTag li").each(function(idx){
        
        var id = $(this).data("id");
        var name = $(this).data("name");
        
        if(idx == 0){
            tagHtml = "#" + name + "<input type='hidden' name='refTag' value='" + id + "'/>";
        }else{
            tagHtml += ", #" + name + "<input type='hidden' name='refTag' value='" + id + "'/>";
        }
    });
    
    if(opener){
    	$(opener.document).find(".REFTAG").html(tagHtml);
    }
    
    self.close();
    
}

//태그 마스터 추가
window.saveTagMaster = function(){
	
	var param = new Object();
	param.oid = "${oid}";
	param.value = $("input[name=tagAddInput]").val();
	
	ajaxCallServer("/plm/ext/common/tag/checkDuplicateTagMaster", param, function(data){
		
		if(confirm("Tag 마스터를 추가 하시겠습니까?")){
			
			ajaxCallServer("/plm/ext/common/tag/saveTagMaster", param, function(data){
				$(".disableTag").append("<li data-id=" + data.id + " data-name=" + data.name + ">" + data.name + "</li>");
				
				$(".disableTag li:last").dblclick(function(e){
	                
	                var isdisableTag = $(this).parents("ul").hasClass("disableTag");
	                
	                if(isdisableTag){
	                    $(".enableTag").append(this);
	                }else{
	                    $(".disableTag").append(this);
	                }
	            });
				
				alert("추가되었습니다.");
			});
		}
    });
}
</script>
<body class="popup-background popup-space">
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />프로젝트 TAG 관리
        </div>
        <div class="b-space5 float-r" style="text-align: right;">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"> <a href="javascript:saveProjectTag();" class="btn_blue">저장</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%></a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="b-space5" >
        <span class="desc">※ 추가/삭제할 Tag명을 드래그 또는 더블클릭하세요.</span>
        </div>
        <div class="b-space5">
			<table summary="" class="table-info">
				<colgroup>
					<col width="50%" />
					<col width="50%" />
				</colgroup>
				<thead>
					<tr>
						<td style="border-bottom: 0;">Tag 목록</td>
						<td class="top" style="border-bottom: 0;">적용 Tag 목록</td>
					</tr>
					<tr>
						<td class="left" style="padding-left: 5px; padding-right: 10px;"><input
							type="text" name="searchInput" class="txt_field WP100" /></td>
						<td></td>
					</tr>
				</thead>
				<tbody id="issueTaskList">
					<tr>
						<td class="td top">
							<div style="overflow-x: hidden; overflow-y: auto; height: 480px;">
								<ul class="disableTag tagList">
                                <c:forEach items="${tagList }" var="tag">
                                    <li data-id="${tag.id }" data-name="${tag.name }">${tag.name }</li>
                                </c:forEach>
								</ul>
							</div>
						</td>
						<td class="td top">
							<div style="overflow-x: hidden; overflow-y: auto; height: 480px;">
								<ul class="enableTag tagList">
								</ul>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="b-space5" style="margin-top:10px;">
	        <span class="desc">※</span> 
	        <font style="font-size:14px;font-weight:bold;">
	         사용하고자 하시는 Tag가 없으신가요?<br/>&nbsp;&nbsp;&nbsp;&nbsp;Tag 마스터는 전체가 사용하는 정보로 신중한 추가를 부탁 드립니다.
	        </font>
        </div>
        <div class="b-space5" style="margin-top:10px;">
            <input type="text" class="txt_field W300" name="tagAddInput" /> 
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"> <a href="javascript:saveTagMaster();" class="btn_blue">Tag 마스터 추가</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
    </div>
</body>