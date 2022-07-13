<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript"
	src="/plm/extcore/js/project/atft/atft.js"></script>
<script type="text/javascript">

window.openATFTAutoCheck = function(sheetdivision){
    getOpenWindow2('/plm/ext/project/atft/atftAutoCheck.do?projectOid=${projectOid}&sheetdivision='+sheetdivision, 'ATFT자동점검', 1280, 720);
}
</script>
<script type="text/javascript">

<!--
	$(document).ready(function() {
		$(document).attr('title', "ATFT 수정");
		var target_class;
		var target_division;
		var target_type;
		var targetObj;
		var p1_state = "";		
		var pjtNo = $("input[name=pjtNo]").val();
		var passOption = "NO";
		try{
            $.ajax({
                url : "/plm/ext/project/atft/getAtftIfno.do?pjtNo="+pjtNo,
                type : "POST",
                dataType : 'json',
                async : false,
                cache : false,
                success: function(data) {
                    
                    
                	$('select[name=category]').each(function(i) {
                		target_class = $(this).data("classification");
                        target_division = $(this).data("sheetdivision");
                        target_type = $(this).data("type");
                		targetObj = $(this);
                		
                		$.each(data, function(i) {
                            
                            if (target_class == this.CLASSIFICATION) {
                                
                                if(target_division == 'P1'){
                                	$(targetObj).attr("data-oid",this.P1_CLASSKEY);
                                	$(targetObj).val(this.P1_DESISION).prop("selected", true);
                                	if(this.P1_DESISION == 'PASS'){
                                		passOption = "OK";
                                	}
                                	p1_state = this.P1_STATE;
                                	if(this.P1_STATE == 'APPROVED'){
                                		$(targetObj).attr('disabled', 'true');
                                	}
                                	
                                }
                                
                                 if(target_division == 'P2'){
                                	 $(targetObj).attr("data-oid",this.P2_CLASSKEY);
                                	 $(targetObj).val(this.P2_DESISION).prop("selected", true);
                                	 
                                	 if(this.P2_STATE == 'APPROVED'){
                                         $(targetObj).attr('disabled', 'true');
                                     }
                                }
                                
                                
                            }else{
                                return true; //continue
                            }
                            
                        });
                		
                    });
                	
                	if(passOption == 'OK'){
                		$('select[name=category]').each(function(i) {
                            
                            target_division = $(this).data("sheetdivision");
                            
                            
                            if(target_division == 'P1'){
                                
                                $(this).find('option').remove();
                                
                                $(this).append("<option value='PASS'>PASS</option>");
                            }
                            
                        });
                	}
                	
                	
                	$('textarea[name=category]').each(function(i) {
                		target_class = $(this).data("classification");
                        target_division = $(this).data("sheetdivision");
                        target_type = $(this).data("type");
                		targetObj = $(this);
                        $.each(data, function(i) {
                            
                            if (target_class == this.CLASSIFICATION) {
                                
                                if(target_division == 'P1'){
                                	$(targetObj).attr("data-oid",this.P1_CLASSKEY);
                                	$(targetObj).val(this.P1_BASIS);
                                	
                                	if(this.P1_STATE == 'APPROVED'){
                                        $(targetObj).attr('disabled', 'true');
                                    }
                                }
                                
                                 if(target_division == 'P2'){
                                	 $(targetObj).attr("data-oid",this.P2_CLASSKEY);
                                	 $(targetObj).val(this.P2_BASIS);
                                	 
                                	 if(this.P2_STATE == 'APPROVED'){
                                         $(targetObj).attr('disabled', 'true');
                                     }
                                }
                                
                                
                            }else{
                                return true; //continue
                            }
                            
                        });
                        
                    });
                	
                    hideProcessing();
                },
                
                error    : function(xhr, status, error){
                             hideProcessing();
                             alert(xhr+"  "+status+"  "+error);
                             
                }
            });
        }catch(e){
            alert(e);
        }
        
        if(p1_state != 'APPROVED' && passOption == 'OK'){
        	$('#P1_PASS').css("display", "none");   
        	$('#P1_RESET').css("display", "block");
        }
        if(p1_state != 'APPROVED' && passOption != 'OK'){
        	$('#P1_PASS').css("display", "block");   
            $('#P1_RESET').css("display", "none");   
        }
        
        if(p1_state == 'APPROVED'){
        	$('#AUTO_P1').css("display", "none");
        }else{
        	$('#AUTO_P1').css("display", "block");
        }
        
        if(p2_state == 'APPROVED'){
            $('#AUTO_P2').css("display", "none");
        }else{
            $('#AUTO_P2').css("display", "block");
        }
	});
-->
</script>
<script>
function selfclose(){
	self.close();
}
</script>

<form name="atftForm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="pjtNo" value="${pjtNo}">
	<input type="hidden" name="projectOid" value="${projectOid}">
	<input type="hidden" name="aftfInfo" value="">
	<div class="contents-wrap">
		<table style="width: 100%;">
			<tr>
				<td background="/plm/portal/images/logo_popupbg.png">
					<table style="height: 28px;">
						<tr>
							<td width="7"></td>
							<td width="20"><img src="/plm/portal/images/icon_3.png"></td>
							<td class="font_01">All-Tool,Full-Tool 수정</td>
						</tr>
					</table>
				</td>
				<td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
			</tr>
		</table>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td class="space5"></td>
			</tr>
		</table>
		<div class="float-r" style="text-align: right">
		        <span class="in-block v-middle">
                <span class="" style="display:block;" id="AUTO_P1" name="AUTO_P1">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:openATFTAutoCheck('P1');" class="btn_blue">자동점검(P1)</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="in-block v-middle">
                <span class="" style="display:block;" id="AUTO_P2" name="AUTO_P2">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:openATFTAutoCheck('P2');" class="btn_blue">자동점검(P2)</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
			    <span class="in-block v-middle">
	                <span class="" style="display:none;" id="P1_PASS" name="P1_PASS">
	                    <span class="pro-cell b-left"></span>
	                    <span class="pro-cell b-center"><a href="javascript:Atft.p1Pass('P');" class="btn_blue">PASS(P1)</a></span>
	                    <span class="pro-cell b-right"></span>
	                </span>
	            </span>
            
            
            
	            <span class="in-block v-middle">
	                <span class="" style="display:none;" id="P1_RESET" name="P1_RESET">
	                    <span class="pro-cell b-left"></span>
	                    <span class="pro-cell b-center"><a href="javascript:Atft.p1Pass('C');" class="btn_blue">RESET(P1)</a></span>
	                    <span class="pro-cell b-right"></span>
	                </span>
	            </span>
            
            
			<span class="in-block v-middle">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"><a href="javascript:Atft.atftSave('U');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><!-- 저장 --></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			<span class="in-block v-middle">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"><a href="javascript:selfclose();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><!-- 취소 --></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
		</div>
		<div class="sub-title-02 b-space15 clear">
			<span class="r-space9"><img
				src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01120")%><!-- 기본정보 -->
		</div>
		
		<div class="b-space30">
			
			<table id="createTable" class="table-style-01" summary="">
				<colgroup>
					<col width="15%" />
					<col width="15%" />
					<col width="10%" />
					<col width="25%" />
					<col width="10%" />
					<col width="25%" />
				</colgroup>
				
				<tbody>
			        <tr>
			            <td class="title02" rowspan="2" colspan="2">구분</td>
			            <td class="title02" colspan="2">P1/PPV</td>
			            <td class="title02" colspan="2">P2/MVBs</td>
			            
			        </tr>
			        <tr>
			            <td class="title02" >상태</td>
                        <td class="title02" >판정근거</td>
                        <td class="title02" >상태</td>
                        <td class="title02" >판정근거</td>
			        </tr>
			        
			        <tr>
			            <td align="center" rowspan="4" colspan="1"><font color="#000000"><b>제품</b></font></td>
			            <td align="center">치수</td>
			            <td>
			            <select id="category" name="category"  style="width:80px" data-classification="치수" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
			            </td>
			            <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="치수" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
			            <td>
			            <select id="category" name="category" style="width:80px" data-classification="치수" data-sheetdivision="P2" data-oid="">
			                <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
			            </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="치수" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
			        </tr>
                    
			        <tr>
			            <td align="center">외관</td>
			            <td>
                        <select id="category" name="category" style="width:80px" data-classification="외관" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="외관" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category" style="width:80px" data-classification="외관" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="외관" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
			        </tr>
			        
			        <tr>
			            <td align="center">기능/성능</td>
                        <td>
                        <select id="category" name="category" style="width:80px" data-classification="기능/성능" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="기능/성능" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="기능/성능" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="기능/성능" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
			        </tr>
			        <tr>
			            <td align="center">신뢰성</td>
			            <td>
                        <select id="category" name="category" style="width:80px" data-classification="신뢰성" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="신뢰성" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="신뢰성" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="신뢰성" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
			        </tr>
			        
			        <tr>
                        <td align="center" rowspan="3" colspan="1"><font color="#000000"><b>공정조건 (CAPA)</b></font></td>
                        <td align="center">사출(C/T)</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="사출(C/T)" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="사출(C/T)" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="사출(C/T)" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="사출(C/T)" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">프레스(SPM)</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="프레스(SPM)" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="프레스(SPM)" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="프레스(SPM)" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="프레스(SPM)" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">조립(T/T)</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="조립(T/T)" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="조립(T/T)" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="조립(T/T)" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="조립(T/T)" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    
                    <tr>
                        <td align="center" rowspan="5" colspan="1"><font color="#000000"><b>품질확보</b></font></td>
                        <td align="center">Error-Proof</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="Error-Proof" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="Error-Proof" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="Error-Proof" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="Error-Proof" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">공정불량</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="공정불량" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="공정불량" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="공정불량" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="공정불량" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">협력사</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="협력사" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="협력사" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="협력사" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="협력사" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">작업자/검사자</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="작업자/검사자" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="작업자/검사자" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="작업자/검사자" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="작업자/검사자" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">JIG준비<br>(취출/조립/검사)</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="JIG준비(취출/조립/검사)" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="JIG준비(취출/조립/검사)" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="JIG준비(취출/조립/검사)" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="JIG준비(취출/조립/검사)" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    
                    <tr>
                        <td align="center" rowspan="8" colspan="1"><font color="#000000"><b>양산준비<br>(System 및 기타)</b></font></td>
                        <td align="center">개발BOM</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="개발BOM" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="개발BOM" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="개발BOM" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="개발BOM" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">양산BOM</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="양산BOM" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="양산BOM" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="양산BOM" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="양산BOM" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">판매가</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="판매가" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="판매가" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="판매가" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="판매가" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">구매가</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="구매가" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="구매가" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="구매가" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="구매가" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">임가공가</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="임가공가" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="임가공가" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="임가공가" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="임가공가" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">포장사양</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="포장사양" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="포장사양" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="포장사양" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="포장사양" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">생산처</td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="생산처" data-sheetdivision="P1" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="생산처" data-sheetdivision="P1" data-oid=""></textarea>
                        </td>
                        <td>
                        <select id="category" name="category"  style="width:80px" data-classification="생산처" data-sheetdivision="P2" data-oid="">
                            <option value="">해당없음</option>
                            <option value="OK">OK</option>
                            <option value="NG">NG</option>
                        </select>
                        </td>
                        <td>
                          <textarea name="category" rows="2" cols="0" style="width: 90%; height:40%" data-classification="생산처" data-sheetdivision="P2" data-oid=""></textarea>
                        </td>
                    </tr>
                    
		        </tbody>
		        
		    </table>
        </div>
	</div>
	<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>