<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/plm/extcore/fancytree-master/dist/skin-lion/ui.fancytree.min.css" rel="stylesheet">
<script src="/plm/extcore/fancytree-master/dist/jquery.fancytree-all-deps.min.js"></script>
<style type="text/css">
.attrBtn {
	-webkit-border-radius: 5;
	-moz-border-radius: 5;
	border-radius: 5px;
	-webkit-box-shadow: 0px 1px 3px #666666;
	-moz-box-shadow: 0px 1px 3px #666666;
	box-shadow: 0px 1px 3px #666666;
	color: #000000;
	font-size: 14px;
	background: #82BADD;
	padding: 15px;
	margin: 5px;
	border: solid #8a8a8a 2px;
	text-decoration: none;
}

.attrBtn:hover {
	background: #82BAD0;
	text-decoration: none;
	color:#FFFFFF;
	cursor:pointer;
}
.attrBtn:active {
  transform: translateY(4px);
}

ul.fancytree-container {
	background:#EDEDED;
    border: none;
}
.fancytree-container,
span.fancytree-focused span.fancytree-title
{
    outline: 0 !important;
}
</style>
<script>
var useType = "${useType}";
$(document).ready(function() {
	
	
	var tab = CommonUtil.tabs('tabs');
	$('.tabContent').hide();
	$('.tabContent :first').show();
	
	
	$(".tabref").click(function(){
		$(".tabContent").hide();
		var ref = $(this).attr("href");
		$(ref).show();
	});

	if(useType == "CALCULATOR"){
		$("#FORMULAATTRTAB").show();
	}
	
	var attrData = new Array();
	var grid = opener.Grids[0];
	if(grid != null){
		
		var gridRows = grid.Rows;
		var rowKey = Object.keys(gridRows);
		
		var parentNodes = new Object();
		var attrData = new Array();
		
		var rootSkip = false;
		
		for(var i = 0; i < rowKey.length; i++){
			var id = rowKey[i];
			var gridRow = gridRows[id];
			var name = "";
			
			if("Data" == gridRow.Kind){
				
				if(rootSkip){
					var parent = parentNodes[gridRow.parentNode.id];
					
					if(gridRow.name != null) name = gridRow.name;
					var node = { key : id, title : name, expanded: true, children : [] };
					
					if(parent == null){
						attrData.push(node);
					}else{
						parent.children.push(node);
					}
					parentNodes[id] = node;
				}
				
				rootSkip = true;
			}
		}
	}
	
	$("#FORMULAATTRTREE").fancytree({
        source: attrData,
        clickFolderMode : 1,
        icon: false,
        beforeActivate : function(){
        	return false;
        },
        click : function(e, data){
        	
        	var isParent = false;
        	var row = opener.formulaRow;
        	
        	if(data.node.key == row.id){
        		alert("자기 자신은 추가할 수 없습니다.")
        		return false;
        	}
        	
        	var cNode = $("#FORMULAATTRTREE").fancytree("getTree").getNodeByKey(row.id);
        	
        	if(cNode != null){
        		var parents = cNode.getParentList(false, false);
            	
            	for(var i = 0; i < parents.length; i++){
            		
            		var parent = parents[i];
            		
            		if(data.node.key == parent.key){
            			isParent = true;
            		}
            	}
            	
            	if(!isParent){
            		addNewFormulaAttribute("[" + data.node.title + "]", data.node.key);
            	}else{
            		alert("상위 수식은 추가할 수 없습니다.");
            	}
        	}else{
        		addNewFormulaAttribute("[" + data.node.title + "]", data.node.key);
        	}
        	
        	 return false;
        }
    });
    $(".fancytree-container").addClass("fancytree-connectors");
});
</script>
<div id="tabs">
	<ul>
		<li><a class="tabref" href="#MATERIAL_COST">재료비</a></li>
		<li><a class="tabref" href="#LABOR_COST">노무비</a></li>
		<li><a class="tabref" href="#EXPENSE">경비</a></li>
		<li><a class="tabref" href="#MAINTENENCE_COST">관리비</a></li>
		<li><a class="tabref" href="#PRODUCTION">생산량</a></li>
		<li id="FORMULAATTRTAB" style="display:none;"><a class="tabref" href="#FORMULAATTR">원가수식</a></li>
	</ul>
	<div id="MATERIAL_COST" class="tabContent" align="left">
		<c:forEach items="${attrList }" var="attr" varStatus="stat">
			<c:if test="${ fn:indexOf(attr.attrType, 'MATERIAL_COST') >= 0 }">
				<button class="attrBtn" data-code="${ attr.id}">${ attr.name}</button>
			</c:if>
		</c:forEach>
	</div>
	<div id="LABOR_COST" class="tabContent" align="left">
		<c:forEach items="${attrList }" var="attr" varStatus="stat">
			<c:if test="${ fn:indexOf(attr.attrType, 'LABOR_COST') >= 0 }">
				<button class="attrBtn" data-code="${ attr.id}">${ attr.name}</button>
			</c:if>
		</c:forEach>
	</div>
	<div id="EXPENSE" class="tabContent" align="left">
		<c:forEach items="${attrList }" var="attr" varStatus="stat">
			<c:if test="${ fn:indexOf(attr.attrType, 'EXPENSE') >= 0 }">
				<button class="attrBtn" data-code="${ attr.id}">${ attr.name}</button>
			</c:if>
		</c:forEach>
	</div>
	<div id="MAINTENENCE_COST" class="tabContent" align="left">
		<c:forEach items="${attrList }" var="attr" varStatus="stat">
			<c:if test="${ fn:indexOf(attr.attrType, 'MAINTENENCE_COST') >= 0 }">
				<button class="attrBtn" data-code="${ attr.id}">${ attr.name}</button>
			</c:if>
		</c:forEach>
	</div>
	<div id="PRODUCTION" class="tabContent" align="left">
		<c:forEach items="${attrList }" var="attr" varStatus="stat">
			<c:if test="${ fn:indexOf(attr.attrType, 'PRODUCTION') >= 0 }">
				<button class="attrBtn" data-code="${ attr.id}">${ attr.name}</button>
			</c:if>
		</c:forEach>
	</div>
	<div id="FORMULAATTR" class="tabContent" ><div id="FORMULAATTRTREE"></div></div>
</div>
