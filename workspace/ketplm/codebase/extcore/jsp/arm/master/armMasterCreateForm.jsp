<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>Document > 해석의뢰 시스템</title>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<script type="text/javascript" src="/plm/extcore/js/arm/arm.js"></script>
<script type="text/javascript" src="/plm/extcore/js/arm/armCreate.js?ver=0.1"></script>
<script type="text/javascript">

   
    //첨부파일 관련 스크립트 시작
	function insertFileLine() {
	    var innerRow = fileTable.insertRow();
	    innerRow.height = "27";
	    var innerCell;
	
	    var filePath = "filePath";
	    var filehtml = "";
	
	    for ( var k = 0; k < 2; k++) {
	        innerCell = innerRow.insertCell();
	        innerCell.height = "23";
	        innerCell.width = "265";
	
	        if (k == 0) {
	            innerCell.className = "tdwhiteM";
	            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>"
	                  + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
	        } else if (k == 1) {
	            innerCell.className = "tdwhiteL0";
	            innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' style='width: 100%;'>";
	        }
	    }
	}
    
    function setSubContractor(arrObj){
    	var fm = document.forms[0];
    	  var pos = fm.customerDepartment.length;
    	  var arrObj;
    	  var subArr;


    	  for(var i = 0; i < arrObj.length; i++) {
    	    subArr = arrObj[i];

    	    for(var j = 0; j < pos; j++) {
    	      if(fm.customerDepartment.options[j].value==subArr[0]){
    	        alert(subArr[2]+"는 이미 존재하는 의뢰처입니다");
    	        return;
    	      }
    	    }

    	    fm.customerDepartment.length = pos+i+1;
    	    fm.customerDepartment.options[pos+i].value= subArr[0];
    	    fm.customerDepartment.options[pos+i].text = subArr[2];
    	    fm.customerDepartment.options[pos+i].selected = true;
    	  }
    }
</script>
</head>
<body>
	<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	        <td valign="top">
	            <table width="780" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                    <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
	                            <tr>
	                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
	                                <td class="font_01">해석의뢰 요청서 등록</td>
	                                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home <img src="/plm/portal/images/icon_navi.gif">해석의뢰 <img src="/plm/portal/images/icon_navi.gif">해석의뢰 요청서 등록</td>
	                            </tr>
	                        </table></td>
	                </tr>
	                <tr>
	                    <td class="head_line"></td>
	                </tr>
	                <tr>
	                    <td class="space10"></td>
	                </tr>
	            </table>
	            <table width="780" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                    <td>&nbsp;</td>
	                    <td align="right">
	                        <table border="0" cellspacing="0" cellpadding="0">
	                            <tr>
	                                <td>
	                                    <table border="0" cellspacing="0" cellpadding="0">
	                                        <tr>
	                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
	                                                href="javascript:Arm.create();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><!-- 저장 --></a></td>
	                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                        </tr>
	                                    </table>
	                                </td>
	                                <td width="5">&nbsp;</td>
	                                <td>
	                                    <table border="0" cellspacing="0" cellpadding="0">
	                                        <tr>
	                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
	                                                <a href="javascript:window.close();" class="btn_blue">취소</a>
	                                            </td>
	                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                        </tr>
	                                    </table>
	                                </td>
	                            </tr>
	                        </table>
	                    </td>
	                </tr>
	            </table>
	            <table border="0" cellspacing="0" cellpadding="0" width="780">
	                <tr>
	                    <td class="space5"></td>
	                </tr>
	            </table>
	            <table border="0" cellspacing="0" cellpadding="0" width="780">
	                <tr>
	                    <td class="tab_btm2"></td>
	                </tr>
	            </table>
	            <form name="ArmCreateForm" method="post">
                    <input type="hidden" name="partNo" id="partNo"/>
	                <table border="0" cellspacing="0" cellpadding="0" width="780">
	                    <tr>
	                        <td class="tdblueL" width="125">용도<span class="red">*</span></td>
	                        <td width="265" class="tdwhiteL"><ket:select id="analysisUse" name="analysisUse" className="fm_jmp" style="width: 170px;" codeType="ANALYSISUSE" multiple="multiple" useOidValue="true" /></td>
	                        <td class="tdblueL" width="125"><%=messageService.getString("e3ps.message.ket_message", "03104") %><%-- 프로젝트NO --%></td>
	                        <td width="265" class="tdwhiteL"><input type="text" name="projectNo" class="txt_field" style="width: 100%"><input type="hidden" name="projectOid"></td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">의뢰명<span class="red">*</span></td>
                            <td width="*" class="tdwhiteL"><input type="text" name="analysisTitle" class="txt_field" style="width: 100%"></td>
	                        <td class="tdblueL" style="width: 110px;">프로젝트명</td>
				            <td class="tdwhiteL"><input type="text" id="projectName" name="projectName" class="txt_field" style="width: 98%;"></td>
	                    </tr>
	                    <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
                            <td class="tdwhiteL"><select name="customerDepartment" style="width: 76%;" size=2 multiple></select>  
                                <a href="javascript:SearchUtil.selectMultiSubContractor('setSubContractor')"><img src="../../../portal/images/icon_5.png" border="0" align="top"></a>
                                <a href="javascript:deleteRequestBuyer()"><img src="../../../portal/images/icon_delete.gif" border="0" align="top"></a>
                            </td>
                            <td class="tdblueL"  ><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%></td>
                            <td class="tdwhiteL" ><input type="text" id="carTypeName" name="carTypeName" class="txt_field" style="width: 98%;"><input type="hidden" id="carType" name="carType"></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                            <td class="tdwhiteL" colspan="3"><ket:select id="process" name="process" className="fm_jmp" style="width: 170px;" codeType="PROCESS" multiple="multiple" useOidValue="true"/></td>
                        </tr>
                        <tr>
                            <td class="tdblueL">구분<span class="red">*</span></td>
                            <td class="tdwhiteL"><ket:select id="analysisDivision" name="analysisDivision" className="fm_jmp" style="width: 170px;" codeType="ANALYSISDIVISION" multiple="multiple"/></td>
                            <td class="tdblueL">제품구분</td>
                            <td class="tdwhiteL" id="projectClassName"></td>
                        </tr>
                        <tr id ="analysisCommentTR">
                            <td class="tdblueL">해석의견<span class="red">*</span></td>
                            <td class="tdwhiteL" colspan="3"></td>
                        </tr>
                        <tr>
                            <td class="tdblueL">해석의뢰 목적</td>
                            <td colspan="3" class="tdwhiteL"><input id="analysisObjectComment" name="analysisObjectComment" class="txt_field" style="width: 100%"></td>
                        </tr>
                        <tr>
                            <td class="tdblueL">설계목표&설계기준</td>
                            <td colspan="3" class="tdwhiteL"><input id="analysisTargetComment" name="analysisTargetComment" class="txt_field" style="width: 100%"></td>
                        </tr>
                        <tr>
                            <td class="tdblueL">의뢰부품<span class="red">*</span></td>
                            <td colspan="3">
	                           <div style="width: 100%; height: 230px; overflow: auto">
	                               <table border="0" cellspacing="0" cellpadding="0" width="100%" id="productInfo">
		                           <tr>
		                               <td width="30" class="tdgrayM"><a href="javascript:SearchUtil.selectPart('createProduct','pType=P');"><img src="/plm/portal/images/b-plus.png"></a></td>
		                               <td width="80" class="tdgrayM">Part No</td>
		                               <td width="150" class="tdgrayM">Part Name</td>
		                               <td width="30" class="tdgrayM0">Rev</td>
		                            </tr>
	                               </table>
	                               </br>
	                               <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                            <td class="tdwhiteL" colspan="4">*비고*<input type="text" id="analysisComment"  name="analysisComment" class="txt_field" style="width: 100%"></td>
                                        </tr>
                                   </table>
	                           </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdblueL">첨부파일</td>
                            <td colspan="3" class="tdwhiteL">
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
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
                                                        <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine(); return false;""><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                                        <td width="265" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                    <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                                        <col width="20"><col width="">
                                        <tbody id="fileTable"></tbody>
                                    </table>
                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                            <td class="space5"></td>
                                        </tr>
                                    </table>
                                </div>
                            </td>
                        </tr>
	                 </table>
	            </form>
	        </td>
	    </tr>
	</table>
</body>
</html>
