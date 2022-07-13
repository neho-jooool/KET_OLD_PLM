<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<style>
<!--
body {
	margin-bottom: 0px;
}
-->
</style>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/trycondition/tryCondition.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    //------------ start suggest binding ------------//
    //사용자 suggest
    SuggestUtil.bind('USER', 'creatorTxt', 'creator');
    //Die no suggest
    SuggestUtil.bind('DIENO', 'dieNo');
    //제품 Project No suggest
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo');
    //부품 suggest
    SuggestUtil.bind('PARTNO', 'partNo');
    //------------ end suggest binding ------------//
    // default 3달 설정
    $('[name=devStartDate]').val(predate);
    $('[name=devEndDate]').val(postdate);
    // Calener field 설정
    CalendarUtil.dateInputFormat('devStartDate','devEndDate'); //기한 설정시 start와 end field를 설정한다.
    //multi select bind
    CommonUtil.singleSelect('moldType',150);
    CommonUtil.singleSelect('developType2',150);
    CommonUtil.multiSelect('division',100);
    CommonUtil.setNumberField('dieNo',true);
    CommonUtil.setNumberField('partNo',true);
    
    
    //server paging
    TryCondition.createPaingGrid();
    $("form[name=TryConditionSearchForm]").keypress(function(e) {
        //Enter key
        if ( e.which == 13 ) {
            TryCondition.search();
            return false;
        }
    });
    
    treeGridResize("#TryConditionGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});

</script>
<form name="TryConditionSearchForm">
<div class="contents-wrap" style="margin-bottom: 0px">
	<div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "09257") %><!-- 금형 Try 조건표 검색 --></div>
	<div class="current-location">
		<span><img src="/plm/portal/images/icon_navi.gif" />Home<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "00997") %><!-- 금형 --><img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "09245") %><!-- 금형Try조건표 --></span>
	</div>
	<div style="text-align:right" class="b-space10">
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.clear()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><!-- 초기화 --></a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.search()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><!-- 검색 --></a></span><span class="pro-cell b-right"></span></span></span>
	</div>
	<div>
        <!-- 검색영역 collapse/expand -->
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
        <div style="display: none">지우지 마세요</div>
		<table cellpadding="0" class="table-style-01" summary="">
			<colgroup>
				<col width="13%" />
				<col width="20%" />
				<col width="13%" />
				<col width="20%" />
				<col width="13%" />
				<col width="21%" />
			</colgroup>
			<tbody>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "00144") %><!-- Die No --></td>
					<td><input type="text" name="dieNo" style="width:96%"></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "01051") %><!-- 금형구분 --></td>
					<td>
                        <select id="moldType" name="moldType">
                            <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %></option>
                            <option value="PRESS">Press</option>
                            <option value="MOLD">Mold</option>
                        </select>
                    </td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "04640") %><!-- 작성자 --></td>
					<td><input type="text" name="creatorTxt"/>&nbsp;&nbsp;
                        <input type="hidden" name="creator"/>
                        <a href="javascript:SearchUtil.selectOneUser('fstCharge','fstChargeName');">
                        <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('creatorTxt','creator');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "00348") %><!-- Part No --></td>
					<td><input type="text" name="partNo" style="width:96%"></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "00397") %><!-- Project No --></td>
					<td><input type="text" name="projectNo" style="width:96%"></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "02532") %><!-- 제작구분 --></td>
					<td><ket:select id="developType2" name="developType2" className="fm_jmp" codeType="MAKINGTYPE" multiple="multiple"/></td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "00347") %><!-- Part Name --></td>
					<td><input type="text" name="partName"  style="width:96%"/></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "01662") %><!-- 사업부 --></td>
                    <td><ket:select id="division" name="division" className="fm_jmp" style="width: 160px;" codeType="DIVISIONNUMBER" multiple="multiple" useCodeValue="true"/></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "00590") %><!-- 개발시작일 --></td>
					<td>
                       <input type="text" name="devStartDate" class="width25" />
                       <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('devStartDate');" style="cursor: hand;">
                     ~ <input type="text" name="devEndDate" class="width25" />
                       <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('devEndDate');" style="cursor: hand;">
                     </td>
				</tr>
			</tbody>
		</table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space5"></td>
            </tr>
        </table>
        <!-- EJS TreeGrid Start -->
        <div id="listGrid"></div>
        <!-- EJS TreeGrid End -->
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space5"></td>
            </tr>
        </table>
	</div>
</div>
</form>