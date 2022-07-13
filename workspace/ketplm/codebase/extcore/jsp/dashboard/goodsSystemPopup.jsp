<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/goodsSystem.js"></script>
<!-- EJS TreeGrid End -->

<!-- EJS TreeGrid Start -->
<div class="content-main">
    <div class="container-fluid">
        <div class="row-fluid">
            <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
        </div>
    </div>
</div>
<!-- EJS TreeGrid Start -->

<script type="text/javascript">
$(document).ready(function(){
    
  //  alert("${dashBoardDTO.customer}"+" "+"${dashBoardDTO.carType}"+"  "+"${dashBoardDTO.year}");
    
    goodsSystem.createPaingGrid("${dashBoardDTO.programNo}");
});

function viewProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
}

function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"","750","700","status=no,scrollbars=yes,resizable=no");
}
</script>