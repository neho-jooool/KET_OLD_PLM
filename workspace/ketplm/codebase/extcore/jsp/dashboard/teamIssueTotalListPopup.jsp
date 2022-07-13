<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/teamIssueTotalList.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid End -->
<body class="popup-background popup-space">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07300") %></div> 
<div align="right"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span></div>
<!-- EJS TreeGrid Start -->
<div class="content-main">
    <div class="container-fluid">
        <div class="row-fluid">
            <div id="listGrid2" style="WIDTH: 100%; HEIGHT: 100%"></div>
        </div>
    </div>
</div>
<!-- EJS TreeGrid Start -->
</body>
<script type="text/javascript">
$(document).ready(function(){
    
  //  alert("${dashBoardDTO.type}"+" "+"${dashBoardDTO.devType}"+"  "+"${dashBoardDTO.year}");
    
   // teamIssueTotalList.createPaingGrid("${dashBoardDTO.pjtType}","${dashBoardDTO.status}","${dashBoardDTO.deptCode}","${dashBoardDTO.startDate}","${dashBoardDTO.endDate}");
	teamIssueTotalList.createPaingGrid("${dashBoardDTO.pjtType}","${dashBoardDTO.status}","${dashBoardDTO.deptCode}","${dashBoardDTO.startDate}","${dashBoardDTO.endDate}","${dashBoardDTO.devType}","${dashBoardDTO.devPattern}","${dashBoardDTO.carType}","${dashBoardDTO.level2}");
	treeGridResize("#SampleSearchGrid2","#listGrid2");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});

function viewProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
}

function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"","750","700","status=no,scrollbars=yes,resizable=no");
}


function search(perPage){
    if(!perPage) perPage = Grids['SampleSearchGrid2'].Source.Layout.Data.Cfg.PageLength;
    Grids['SampleSearchGrid2'].Source.Layout.Data.Cfg.PageLength=perPage;
    Grids['SampleSearchGrid2'].Source.Data.Params = decodeURIComponent($('[name=workTimeReport]').serialize());
    Grids['SampleSearchGrid2'].Source.Data.Url = '/plm/ext/dashboard/teamIssueTotalListSetting?sortName=*Sort0';
    Grids['SampleSearchGrid2'].Source.Data.Param.formPage=perPage;
    Grids['SampleSearchGrid2'].Source.Data.Param.deptCode="${dashBoardDTO.deptCode}";
    Grids['SampleSearchGrid2'].Source.Data.Param.startDate="${dashBoardDTO.startDate}";
    Grids['SampleSearchGrid2'].Source.Data.Param.endDate="${dashBoardDTO.endDate}";
    Grids['SampleSearchGrid2'].Source.Data.Param.pjtType="${dashBoardDTO.pjtType}";
    Grids['SampleSearchGrid2'].Source.Data.Param.vDevType="${dashBoardDTO.devType}" == '' ? null : "${dashBoardDTO.devType}";
    Grids['SampleSearchGrid2'].Source.Data.Param.vDevPattern="${dashBoardDTO.devPattern}" == '' ?  null : "${dashBoardDTO.devPattern}";
    Grids['SampleSearchGrid2'].Source.Data.Param.carType="${dashBoardDTO.carType}";
    Grids['SampleSearchGrid2'].Source.Data.Param.level2="${dashBoardDTO.level2}";
    Grids['SampleSearchGrid2'].Reload();
    $('#listGrid2').loadMask('loading...'); 
}
</script>