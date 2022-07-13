<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="/plm/portal/js/viewObject.js"></script>

<body class="popup-background popup-space">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" />프로젝트 산출물 목록</div>  
<div>
<table cellpadding="0" class="table-style-01">
    <thead>
    <tr>
        <td>번호</td>
        <td>Task명</td>
        <td>타입</td>
        <td>문서제목</td>
        <td>버전</td>
        <td>담당자</td>
        <td>등록여부</td>
    </tr>
    </thead>
    <c:forEach var="data" items="${outputData}" varStatus="status">
    <tr>
        <td align="center">${status.count}</td>
        <td><a href="javascript:void(0);" onclick="viewTask('${data.oid}');">${data.taskName}</a></td>
        <td align="center">${data.objType}</td>
        <c:if test="${data.isRegistry eq false}">
        <td>${data.name}</td>
        <td align="center">${data.version}/${data.lastVersion}</td>
        </c:if>
        <c:if test="${data.isRegistry eq true}">
            <c:if test="${data.objType ne '기타'}">
            <td align="center"><a href="javascript:void(0);" onclick="openViewProject('${data.docOid}')">${data.name}</a></td>
            </c:if>
            <c:if test="${data.objType eq '기타'}">
            <td align="center"><a href="javascript:void(0);" onclick="openCompleteReson('${data.outOid}')">${data.name}</a></td>
            </c:if>
            <td align="center"><a href="javascript:void(0);" onclick="ViewDoc('${data.docOid}')">${data.version}</a>/<a href="javascript:void(0);" onclick="ViewDoc('${data.docOid1}')">${data.lastVersion}</a></td>
        </c:if>
        <td align="center">${data.userName}</td>
        <td align="center">
            <c:if test="${data.isRegistry eq true}">등록</c:if>
            <c:if test="${data.isRegistry eq false}">등록전</c:if>
        </td>
    </tr>
    </c:forEach>
</table>
</div>
</body>

<script type="text/javascript">
function viewTask(oid){
	var url = '/plm/jsp/project/TaskView.jsp?oid=' + oid;
	openOtherName(url, "popup", 660, 530, "status=yes,scrollbars=no,resizable=yes");
}


function openViewProject(oid, width, height, auth){
    if(width == null)
    {
        width = 'full';
        height = 'full';
    }
    var url = getViewURL(oid);
    
    if(url != "") 
    {
        if(auth != null){
            url += '&key=auth&value='+auth; 
            url += '&key=projectValue&value=true';
        }else{  
            url += '&key=projectValue&value=true';
        }
        
        popup(url, 900, 600);
        
    }
}

function openCompleteReson(oid){
    var url="/plm/jsp/project/EtcOutputReasonView.jsp?oid="+oid;
    openOtherName(url,"Reson","500","360","status=no,scrollbars=no,resizable=yes");
}

function ViewDoc(oid){
    openView(oid, 870, null, null);
}
</script>