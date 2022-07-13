﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<style>
.dragRow {
    background-color: #EDFFE7;
}
</style>
<script type="text/javascript"
	src="/plm/extcore/js/sales/project/salesCSProject.js"></script>
<script type="text/javascript">
var year;
var mon;
var isCsFileSave='ALL';
<!--
	$(document).ready(function() {
		window.document.body.scroll = "auto";//메일에서 열릴때 스크롤주기위함
		$(document).attr('title', "CS회의차수 상세조회");
		CalendarUtil.dateInputFormat('csStartDate'); //csStartDate
		CalendarUtil.dateInputFormat('csEndDate'); //csEndDate
		CalendarUtil.dateInputFormat('csNextStartDate'); //csNextStartDate
		CommonUtil.singleSelect('csTargetDept',100);
		
		var adminCheckFlag = $('[name=adminCheckFlag]').val();
		
		if(adminCheckFlag == 'false'){//admin권한이면 전체부서가 다 로드됨 부서별로 순서를 정해야하므로 dnd기능 비활성
			tablednd();
		}
		approveCheck();

	});
//-->

function approveCheck(){
	
	var csClose = $('[name=csClose]').val();
	var stateName = $('[name=stateName]').val(); 
	var chiefCheckFlag = $('[name=chiefCheckFlag]').val();
	var adminCheckFlag = $('[name=adminCheckFlag]').val();
	
	if(csClose != 'Y' && (stateName == 'INWORK' || stateName == 'REWORK') && (chiefCheckFlag == 'true' || adminCheckFlag == 'true')){
		$("#requestApp").css("display","");
	}else{
		
		$("#requestApp").css("display","none");
	}
}

function tablednd(){
	
	$(function(){
        $("#salesTargetTable").tableDnD({
            //onDragClass : "dragRow",
        });
        
        $("#salesTargetTable tr").not(".nodrag").hover(function() {//마우스 오버,아웃
            for(var i=0; i<7; i++){
                $(this.cells[i]).addClass('dragRow');
            }
        }, function() {
            for(var i=0; i<7; i++){
                $(this.cells[i]).removeClass('dragRow');
            }
            
        });
        
        
        $("#salesTargetTable tr").not(".nodrag").mouseup(//마우스 버튼 땔때
                function() {
                    
                    $('#salesTargetTable').find('tr').each(function(i,val){
                        if(i > 0){
                            $(this).children("td:eq(0)").text(eval(i));
                        }
                        
                    });
                    
                }
        );

        })
}
function changeCSTargetDept(){
	
	if ( $("#csTargetDept").val() != null ) {
		
		$("#salesTargetTable tr:not(:first)").remove();
		
        var csTargetDeptCode = $("#csTargetDept").val();
        var oid = $('[name=oid]').val();
        
        
        $.ajax({
            url : "/plm/ext/sales/project/getCSDeptList.do?oid="+oid+"&deptNo="+csTargetDeptCode,
            type : "post",
            dataType : "json",
            async : false,
            cache: false,
            success: function(data) {
                $.each(data, function(i) {
                	
                	$('[name=stateName]').val(this.stateName);
                	$('[name=approveOid]').val(this.approveOid);
                	
                    i=i+1;
                    addContent = "<tr>";
                    addContent +="<td class=tdwhiteM>"+i+"</td>"; 
                    addContent +="<input type=hidden name=sortNo value="+this.mysortNo+">"; 
                    addContent +="<input type=hidden name=oids value="+this.oids+">";  
                    addContent +="<td class=tdwhiteM><a href='#' onclick=javascript:sales.openCsProject('"+this.oid+"'); return false;>"+this.projectNo+"</a></td>";
                    addContent +="<td class=tdwhiteM>"+this.projectName+"</td>"; 
                    addContent +="<td class=tdwhiteM>"+this.state+"</td>"; 
                    addContent +="<td class=tdwhiteM>"+this.rankName+"</td>"; 
                    addContent +="<td class=tdwhiteM>"+this.nationName+"</td>"; 
                    addContent +="<td class=tdwhiteM>"+this.dept+"</td>"; 
                    addContent +="</tr>";
                    
                    $('#salesTargetTable > tbody:last').append(addContent);
                    
                });
            }
        });
        
        if(csTargetDeptCode != 'ALL'){
        	tablednd();
        }
        
        isCsFileSave = csTargetDeptCode;
        
        approveCheck();
        
    }
}

//첨부파일 관련 스크립트 시작
function insertFileLine() {
	
 	var size = '${sales.csPorjectList.size()}';
	
	addContent = "<tr>";
	addContent +="<td width='4%' class=tdwhiteM><a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a></td>"; 
	addContent +="<td width='80%' class=tdwhiteL><input name='secondaryFiles' type='file' class='txt_fieldRO' size='85%'></td><input type=hidden name=secondaryFileOids value='new'>";
	addContent +="<td width='16%' class=tdwhiteM><select name=fileSort style='width:80%;text-align:center;'>";
	addContent += "<option value=>선택</option>";
	addContent += "<option value=0.5>0.5</option>";
    for ( var i = 1; i <= size; i++) {
    	addContent += "<option value="+i+">"+i+".5</option>";
    }
    addContent += "</select></td>"
    
    
/*     addContent +="<td width='8%' class=tdwhiteM><select name=fileSortOpt>";
    addContent += "<option value='front'>앞</option><option value='back'>뒤</option>";
    addContent += "</select></td>"; */
	
     addContent +="</tr>";

    $('#csFileTable > tbody:last').append(addContent); 
    
}

function goApproval(){
	var oid = $('[name=approveOid]').val();
	goPage(oid);
}
</script>
<form name="SalesCSForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="oid" value="${sales.oid}" />
<input type="hidden" name="year" value="${sales.year}" />
<input type="hidden" name="month" value="${sales.month}" />
<input type="hidden" name="degree" value="${sales.degree}" />
<input type="hidden" name="adminCheckFlag" value="${adminCheckFlag}" />
<input type="hidden" name="csClose" value="${sales.csClose}" />
<input type="hidden" name="stateName" value="${sales.stateName}" />
<input type="hidden" name="chiefCheckFlag" value="${chiefCheckFlag}" />
<input type="hidden" name="approveOid" value="${sales.approveOid}" />
	<div class="contents-wrap">
		<table style="width: 100%;">
			<tr>
				<td background="/plm/portal/images/logo_popupbg.png">
					<table style="height: 28px;">
						<tr>
							<td width="7"></td>
							<td width="20"><img src="/plm/portal/images/icon_3.png"></td>
							<td class="font_01">CS회의차수 상세조회</td>
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
            <c:if test="${sales.csClose != 'Y' && adminCheckFlag == true}" >
            <span class="in-block v-middle">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:sales.csMeetingClose();" class="btn_blue">CS회의 마감</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            </c:if>
            <c:if test="${adminCheckFlag == true}" >
            <span class="in-block v-middle">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:sales.CSdateUpdate();" class="btn_blue">기간수정</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            </c:if>
			<span class="in-block v-middle">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue">닫기</a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
		</div>
		<div id="title" class="sub-title-02 b-space15 clear">
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" />
			${sales.year} 년 ${sales.month} 월 ${sales.degree} 차수  
			</span>
		</div>
		<div class="b-space30">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td class="tab_btm2"></td>
				</tr>
			</table>
			<table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed" border="1">
				<colgroup>
					<col width="20%" />
					<col width="80%" />
				</colgroup>
				<tbody>
					<tr>
                        <td class="tdblueL">CS기간<span class="red">*</span></td>
                        <td class="tdwhiteL">
                          <input type="text" class="txt_field" name="csStartDate" style="width: 30%" esse="true" esseLabel="CS기간시작일" value='${sales.csStartDate}' />
                          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('csStartDate');" style="cursor:pointer;cursor:hand;"> ~
                          <input type="text" class="txt_field" name="csEndDate" style="width: 30%" esse="true" esseLabel="CS기간종료일" value='${sales.csEndDate}' />
                          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('csEndDate');" style="cursor:pointer;cursor:hand;">
                          </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">다음차수진행가능일<span class="red">*</span></td>
                        <td class="tdwhiteL">
                          <input type="text" class="txt_field" name="csNextStartDate" style="width: 30%" esse="true" esseLabel="다음 차수진행 가능일" value='${sales.csNextStartDate}' />
                          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('csNextStartDate');" style="cursor:pointer;cursor:hand;">
                        </td>
                    </tr>
					<tr>
                        <td class="tdblueL">마감여부</td>
                        <td class="tdwhiteL">${sales.csClose}</td>
                    </tr>
                    <c:if test="${adminCheckFlag == true}">
                    <tr>
                        <td class="tdblueL">대상부서</td>
                        <td class="tdwhiteL">
                        <select id="csTargetDept" name="csTargetDept" class="fm_jmp" multiple="multiple" style="width:200px" onChange="changeCSTargetDept(this);"/>
                        <option value='ALL'>전체</option>
                        <c:forEach items="${sales.csDeptList}" var="deptList" varStatus="status">
                        <option value=${deptList.sortNo}>${deptList.team}</option>
                        </c:forEach>
                        </select>
                        </td>
                    </tr>
                    </c:if>
                    
                    
                    
                    <!--  
                    <tr>   
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667") %><%--주 첨부파일--%></td>
                      <td class="tdwhiteL">
                      <c:choose> 
                          <c:when test="${primaryFile.contentoid != null}">
                              <input name='primaryFile' type='hidden' value='${primaryFile.contentoid}'>
                              <a target='download' href='${primaryFile.downURLStr}'>${primaryFile.iconURLStr}</a>&nbsp;
                              <a href='${primaryFile.downURLStr}' target='_blank'>${primaryFile.name}</a>&nbsp;(&nbsp;${primaryFile.fileSizeKB}&nbsp;)
                          </c:when>
                          <c:otherwise></c:otherwise>
                      </c:choose>
                      </td>
                    </tr>
                    -->
			        
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="sub-title-02 b-space15 clear">
        <div class="float-r" style="text-align: right">
                <c:if test="${sales.csClose != 'Y' && adminCheckFlag == true}" >
                <span class="in-block v-middle">
                    <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center"><a href="javascript:sales.csMeetingSortUpdate('file');" class="btn_blue">CS회의 파일 저장</a></span>
                        <span class="pro-cell b-right"></span>
                    </span>
                </span>
                </c:if>
            </div>
            <c:if test="${adminCheckFlag == true}" >
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>CS회의 파일</font>
            </c:if>
     </div>
     <c:if test="${adminCheckFlag == true}" >
     <div class="b-space30">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            
            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;" border="1">
            <tr>
            <td class="tdwhiteL">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table>
                            
                            <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                            
                                <table width="100%" cellpadding="0" cellspacing="0">
                                
                                     <tr class="headerLock3">
                                        <td>
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                                                <tr>
                                                    <td width="4%" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine(); return false;"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                                    <td width="80%" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961")%></td>
                                                    <td width="16%" class="tdgrayM">CS회의순서</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    
                                     <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed" id="csFileTable">
                                        <tbody id="fileTable">
                                        <c:forEach var="content" items="${secondaryFiles}">
                                        <tr>
                                            <td class="tdwhiteM" width="4%" ><a href="#" onclick="javascript:$(this).parent().parent().remove();return false;"><b><img src="/plm/portal/images/b-minus.png"></b></a></td>
                                            <td class="tdwhiteL" width="80%">
                                                <input name='secondaryFileOids' type='hidden' value='${content.contentoid}'>
                                                <a target='download' href='${content.downURLStr}'>${content.iconURLStr}</a>&nbsp;
                                                <a href='${content.downURLStr}' target='download'>${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)
                                            </td>
                                            <td width="16%" class="tdwhiteM">
                                            <select name=fileSort style="width:80%;text-align:center;">

                                            <option value='0.5' <c:forEach items="${sales.csPorjectList}" var="csList" varStatus="status"><c:if test="${content.contentoid  == csList.fileSortOption}" > selected </c:if></c:forEach> >0.5</option>

                                            <c:forEach items="${sales.csPorjectList}" var="csList" varStatus="status">
                                            <option value='${status.index+1}' <c:if test="${content.contentoid == csList.fileOid}" > selected </c:if> >${status.index+1}.5</option>
                                            </c:forEach>
                                            </select>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    
                           
                                    
                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                       <tr>
                                           <td class="space5"></td>
                                       </tr>
                                    </table>
                                    
                                 </table>
                             </div>
             </td>
            </tr>            
            </table>
     </div> 
     </c:if>
     
	<div class="sub-title-02 b-space15 clear">
	     <div class="float-r" style="text-align: right">
                <c:if test="${sales.csClose != 'Y' && ((chiefCheckFlag == true && (sales.stateName == 'INWORK' || sales.stateName == 'REWORK')) || approveAuthFlag == true || adminCheckFlag == true)}" >
                <span class="in-block v-middle">
                    <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center"><a href="javascript:sales.csMeetingSortUpdate();" class="btn_blue">CS회의 순서 저장</a></span>
                        <span class="pro-cell b-right"></span>
                    </span>
                </span>
                </c:if>
        </div>
		<div class="float-r" style="text-align: right">
	            <div id="requestApp" name="requestApp" style="display: none" >
	            <span class="in-block v-middle" >
                    <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center"><a href="javascript:goApproval()" class="btn_blue">결재요청</a></span>
                        <span class="pro-cell b-right"></span>
                    </span>
                </span>
	            </div>
	        </div>
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>프로젝트 카드 리스트<font color="red" size="2"> * Drag & Drop으로 순서 지정 후 저장버튼을 눌러야 반영됩니다.</font>
     </div>

     <div class="b-space30">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table id="salesTargetTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;" border="1">
                <colgroup>
                    <col width="5%" />
                    <col width="15%" />
                    <col width="20%" />
                    <col width="5%" />
                    <col width="15%" />
                    <col width="15%" />
                    <col width="25%" />
                </colgroup>
                <tbody>
                <tr class="nodrop nodrag">
                    <td class="tdblueM">순서</td>
                    <td class="tdblueM">ProjectNo</td>
                    <td class="tdblueM">프로젝트명</td>
                    <td class="tdblueM">상태</td>
                    <td class="tdblueM">중요도</td>
                    <td class="tdblueM">국가</td>
                    <td class="tdblueM">부서</td>
                </tr>
                <c:forEach items="${sales.csPorjectList}" var="csList" varStatus="status">
                    <tr>
                    <td class="tdwhiteM">${status.index+1}</td>
                    <input type="hidden" name="sortNo" value="${csList.mysortNo}">
                    <input type="hidden" name="oids" value="${csList.oids}">                    
                    <td class="tdwhiteM"><a href='#' onclick="javascript:sales.openCsProject('${csList.oid}'); return false;">${csList.projectNo}</a></td>
                    <td class="tdwhiteM">${csList.projectName}</td>
                    <td class="tdwhiteM">${csList.state}</td>
                    <td class="tdwhiteM">${csList.rankName}</td>
                    <td class="tdwhiteM">${csList.nationName}</td>
                    <td class="tdwhiteM">${csList.dept}</td>
                    </tr>
                </c:forEach>
                <c:if test="${sales.csPorjectList.size() < 1}" >
                <tr class="nodrop nodrag"><td colspan=7 style="text-align:center; font-family: NanumGothic, '나눔고딕', Nanumgo, Dotum; font-size: 12px;"><br><b>지정된 CS회의가 없습니다.</b><br><br></td></tr>
                </c:if>
                </tbody>
            </table>
        </div>   
    </div>
	<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>