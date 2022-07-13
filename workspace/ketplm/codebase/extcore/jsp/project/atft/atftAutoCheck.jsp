<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<style>
.bold{font-weight:bold;}
.statTable td{padding-left:0px;}
</style>
<script>

var atftData = {};

$(document).ready(function(){
	var sheetdivision = '${sheetdivision}';
	
	if(sheetdivision == 'P1'){
		$('#autoTitle').text("All-Tool 자동점검");
	}
    
	if(sheetdivision == 'P2'){
		$('#autoTitle').text("Full-Tool 자동점검");
    }
	var param = {
			oid : '${projectOid}',
			sheetdivision : '${sheetdivision}'
	}
	ajaxCallServer('/plm/ext/project/atft/getAtftStats', param, function(data){
		setAtftStatTable(data.dataList);
		atftData = data.dataList;
	});
});
window.setAtftStatTable = function(stats){
	
	for(var i = 0; i < stats.length; i++){
		
		var stat = stats[i];
		
		var trHtml = '';
		
		var totalStatTag = stat.totalState;
        var statTag = stat.state;
        
        if(statTag == 'NG'){
            statTag = '<strong><font color=red>NG</font></strong>';
        }
        
        
        if(totalStatTag == 'NG'){
            totalStatTag = '<strong><font color=red>NG</font></strong>';
        }
		 
		if($("[data-classification='" + stat.classification + "'] tbody tr").length == 0){
			
			trHtml = '<tr><td class="spanTD">' + totalStatTag + '</td><td style="padding:0px;">' + statTag + '</td>' + 
	           '<td class="left" style="padding:0px;"><a href=javascript:; onClick=javascript:viewProject('+"'"+stat.projectOid+"'"+')>' + stat.projectNo + '</a></td>' +
	           '<td class="left" style="padding:0px;"><a href=javascript:; onClick=javascript:viewProject('+"'"+stat.taskOid+"'"+')>' + stat.taskName + '</a></td>' +
			'<td class="left" style="padding:0px;">' + stat.decision + '</td></tr>';
		}else{
			trHtml = '<tr><td style="padding:0px;" class="stateTD">' + statTag + '</td>' + 
			'<td class="left" style="padding:0px;"><a href=javascript:; onClick=javascript:viewProject('+"'"+stat.projectOid+"'"+')>' + stat.projectNo + '</a></td>' +
            '<td class="left" style="padding:0px;"><a href=javascript:; onClick=javascript:viewProject('+"'"+stat.taskOid+"'"+')>' + stat.taskName + '</a></td>' +
			'<td class="left" style="padding:0px;">' + stat.decision + '</td></tr>';
		}
		
		$("[data-classification='" + stat.classification + "'] tbody").append(trHtml);
		$("[data-classification='" + stat.classification + "'] tbody .spanTD").attr("rowspan", $("[data-classification='" + stat.classification + "'] tbody tr").length);
	}
	
	
	$(".statTable tbody").each(function(){
		
		if($(this).find("tr").length == 0){
			
			trHtml = '<tr><td style="padding:0px;border-top:0;border-bottom:0;"></td>' +
			'<td style="padding:0px;border-top:0;border-bottom:0;"></td>' + 
            '<td style="padding:0px;border-top:0;border-bottom:0;"></td>' +
            '<td style="padding:0px;border-top:0;border-bottom:0;"></td>' +
            '<td style="padding:0px;border-top:0;border-bottom:0;"></td></tr>';
            
            $(this).append(trHtml);
            
		}else{
			var trFEl = $(this).find("tr:first");
			var trLEl = $(this).find("tr:last");
			
			$(trFEl).find("td").each(function(){
			    $(this).css("border-top", "0");
			});
			
			$(trLEl).find("td").each(function(){
			    $(this).css("border-bottom", "0");
			});
			
			$(trFEl).find("td:first").css("border-bottom", "0");
			
		}
	});
}
window.setAutoATFT = function(){
	
	var dataList = atftData;
	//초기화
	//$(opener.document).find("select[data-sheetdivision=${sheetdivision}]").val("");
	//$(opener.document).find("textarea[data-sheetdivision=${sheetdivision}]").html("");
	for(var i = 0; i < dataList.length; i++){
		
		var data = dataList[i];
		if(data.totalState == null || data.totalState == ''){
            continue;
        }
		var classfication = data.classification;
		
		var selectEl = $(opener.document).find("select[data-sheetdivision=${sheetdivision}][data-classification='" + classfication + "']");
		var textEl = $(opener.document).find("textarea[data-sheetdivision=${sheetdivision}][data-classification='" + classfication + "']");
		$(selectEl).val(data.totalState);
		
		if($(textEl).val() == ''){
			$(textEl).val(data.totaldecision);	
		}
		
	};
	
	if(confirm("[종합상태]에 값이 있는 항목에 대해서만 점검결과가 적용되었습니다.\r\n\r\n저장버튼을 눌러야 적용내용이 저장됩니다.\r\n\r\n지금 열려있는 창을 닫으시겠습니까?")){
		this.close();	
	}
	
	
}

window.viewMoldProject = function(oid){
	openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
}

window.viewProject = function(oid){
    
	openView(oid);
}

</script>
<form name="atftForm" method="post">
    <div class="contents-wrap">
        <table style="width: 100%;">
            <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                    <table style="height: 28px;">
                        <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01" id="autoTitle" name="autoTitle">All-Tool,Full-Tool 자동점검</td>
                        </tr>
                    </table>
                </td>
                <td width="136" align="right"><img
                    src="/plm/portal/images/logo_popup.png"></td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space5"></td>
            </tr>
        </table>
        <div class="float-r" style="text-align: right">
            <c:if test="${empty readOnly }">
            <span class="in-block v-middle">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:setAutoATFT();" class="btn_blue">적용</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            </c:if>
            <span class="in-block v-middle">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="sub-title-02 b-space15 clear">
            <span class="r-space9"><img
                src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01120")%><!-- 기본정보 -->
        </div>
        <div class="b-space30">
            
            <table id="createTable" class="table-style-01 infoTable" summary="">
                <colgroup>
                    <col width="150" />
                    <col width="150" />
                    <col width="80" />
                    <col width="80" />
                    <col width="150" />
                    <col width="150" />
                    <col width="*" />
                </colgroup>
                <thead>
                    <tr>
                        <td colspan="2">
                            구분
                        </td>
                        <td>
                            종합<br>상태
                        </td>
                        <td>
                            개별상태
                        </td>
                        <td>
                            프로젝트
                        </td>
                        <td>
                            Task
                        </td>
                        <td>판정근거
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <!-- ######################### 제품 ############################# -->
                    <tr>
                        <td rowspan="4" class="bold center">
	                        제품
	                    </td>
	                    <td class="center">
	                        치수
	                    </td>
	                    <td class="center" colspan="5">
	                        <table data-classification="치수" class="statTable" style="width:100%;">
                                <colgroup>
								<col width="80" />
								<col width="80" />
								<col width="150" />
								<col width="150" />
								<col width="*" />
								</colgroup>
								<tbody>
								</tbody>
                            </table>
	                    </td>
                       
                    </tr>
                    <tr>
                        <td class="center">
                            외관
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="외관" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            기능/성능
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="기능/성능" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            신뢰성
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="신뢰성" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <!-- ######################### 공정조건 (CAPA) ############################# -->
                    <tr>
                        <td rowspan="3" class="bold center">
                            공정조건 (CAPA)
                        </td>
                        <td class="center">
                            사출(C/T)
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="사출(C/T)" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            프레스(SPM)
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="프레스(SPM)" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            조립(T/T)
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="조립(T/T)" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <!-- ######################### 품질확보 ############################# -->
                    <tr>
                        <td rowspan="5" class="bold center">
                            품질확보
                        </td>
                        <td class="center">
                            Error-Proof
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="Error-Proof" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            공정불량
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="공정불량" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            협력사
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="협력사" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            작업자/검사자
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="작업자/검사자" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            JIG준비<br>(취출/조립/검사)
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="JIG준비(취출/조립/검사)" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <!-- ######################### 양산준비 (System 및 기타) ############################# -->
                    <tr>
                        <td rowspan="7" class="bold center">
                            양산준비<br>(System 및 기타)
                        </td>
                        <td class="center">
                            개발BOM
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="개발BOM" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            양산BOM
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="양산BOM" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            판매가
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="판매가" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            구매가
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="구매가" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            임가공가
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="임가공가" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            포장사양
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="포장사양" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="center">
                            생산처
                        </td>
                        <td class="center" colspan="5">
                            <table data-classification="생산처" class="statTable" style="width:100%;">
                                <colgroup>
                                <col width="80" />
                                <col width="80" />
                                <col width="150" />
                                <col width="150" />
                                <col width="*" />
                                </colgroup>
                                <tbody>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</form>