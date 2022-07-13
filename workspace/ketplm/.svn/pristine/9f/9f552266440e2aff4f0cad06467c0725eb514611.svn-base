<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />

<script type="text/javascript">


	function save(){
		
        if(confirm("교체할 파일만 변경됩니다.\n즉 신규로 추가할 파일은 반영이 되지 않습니다.\n진행하시겠습니까?")){
            if(check()){
            var d = document.fileForm;
            d.encoding = 'multipart/form-data';
            d.action = "/plm/ext/edm/saveHpIfFile.do";
            d.target = "hiddenFrame";
            d.submit();
          }
        }
	}
	
	function reload(){
    	opener.closeWin();
		opener.reload();
	}
	    
	function formclose(){
		opener.closeWin();
	}
	    
	
    function check(){
        var check = true;
        try{
            if($("input[name=iFileImgOrg]").val() == "" && $("input[name=iFileImg]").val() != ""){
	                alert("이미지파일을 신규로 추가할수 없습니다.");
	                $("input[name=iFileImg]").val('');
	                check = false;
            }

            if($("input[name=iFile2DOrg]").val() == "" && $("input[name=iFile2D]").val() != ""){
            	   alert("2D PDF파일을 신규로 추가할수 없습니다.");
	                $("input[name=iFile2D]").val('');
	                check = false;
            }

            if($("input[name=iFile3DOrg]").val() == "" && $("input[name=iFile3D]").val() != ""){
            	   alert("3D PDF파일을 신규로 추가할수 없습니다.");
	                $("input[name=iFile3D]").val('');
	                check = false;
            }
            
            if($("input[name=iFileStepOrg]").val() == "" && $("input[name=iFileStep]").val() != ""){
            	    alert("STEP 파일을 신규로 추가할수 없습니다.");
	                $("input[name=iFileStep]").val('');
	                check = false;
            }

            if($("input[name=iFileIgsOrg]").val() == "" && $("input[name=iFileIgs]").val() != ""){
            	    alert("IGS 파일을 신규로 추가할수 없습니다.");
	                $("input[name=iFileIgs]").val('');
	                check = false;
            }
            
            if($("input[name=iFileImg]").val() == "" && $("input[name=iFile2D]").val() == "" && $("input[name=iFile3D]").val() == "" && $("input[name=iFileStep]").val() == "" && $("input[name=iFileIgs]").val() == ""){
            	alert("변경할 파일정보가 없습니다.");
            	check = false;
            }
            
            if($("input[name=iFile2D]").val() != ""){
                var n1 = $("input[name=iFile2D]").val().indexOf("2D");
                if(n1<0){
	            	alert("파일명확장자 앞에 대문자 2D 를 RENAME 하여야 합니다.");
	                check = false;
                }
            }                 
            
            if($("input[name=iFile3D]").val() != ""){
                var n2 = $("input[name=iFile3D]").val().indexOf("3D");
                if(n2<0){
                	alert("파일명확장자 앞에 대문자 3D 를 RENAME 하여야 합니다.");
	            	check = false;
                }
            }                 
            
            
        }catch(e){
            
            return false;
        }
        return check;
    }
    
    
    
</script>

<form name="fileForm" method="post">
<input type="hidden" name="oid" value="${oid}">
<input type="hidden" name="partOid" value="${partOid}">

<table style="width: 100%; height: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">홈페이지 산출물 재등록</td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table> 
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">    
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="space5"></td>
                </tr>
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03">산출물 정보</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue"
                                                background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:save();"
                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="5">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue"
                                                background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:formclose();"
                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td  class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
               <colgroup><col width="15%"><col width="35%"><col width="15%"><col width="35%"></colgroup>

				<c:forEach var="data" items="${resultMapList}" varStatus="state">
		       <tr>
		          <td class="tdblueL">ECO No</td>
		          <td colspan="3" class="tdwhiteL0">${data.ECOID}</td>
		       </tr>
		       <tr>
		          <td class="tdblueL">PartNo</td>
		          <td colspan="3" class="tdwhiteL0">${data.WTPARTNUMBER} / ${data.PARTNAME}</td>
		       </tr>
		        <tr>
		          <td class="tdblueL">이미지</td>
		          <td colspan="3" class="tdwhiteL0">
		            ${data.IMGICONURLSTR}&nbsp;${data.IMGNAME}<br> 
		            <input type="hidden" name="imgcontentoid" value="${data.IMGCONTENTOID}">
		            <input type="hidden" name="iFileImgOrg" value="${data.IMGNAME}">
		            <input name="iFileImg" type="file" class="txt_fieldRO" style="width:95%">
		          </td>
		        </tr>
		        <tr>
		          <td class="tdblueL">2D</td>
		          <td colspan="3" class="tdwhiteL0">
		            ${data.PLANEICONURLSTR}&nbsp;${data.PLANENAME}<br>
		            <input type="hidden" name="planecontentoid" value="${data.PLANECONTENTOID}">
		            <input type="hidden" name="iFile2DOrg" value="${data.PLANENAME}">
		            <input  name="iFile2D" type="file" class="txt_fieldRO" style="width:95%">
		          </td>
		        </tr>
		        <tr>
		          <td class="tdblueL">3D</td>
		          <td colspan="3" class="tdwhiteL0">
		            ${data.PLANE3DICONURLSTR}&nbsp;${data.PLANE3DNAME}<br> 
		            <input type="hidden" name="plane3dcontentoid" value="${data.PLANE3DCONTENTOID}">
		            <input type="hidden" name="iFile3DOrg" value="${data.PLANE3DNAME}">
		            <input   name="iFile3D" type="file" class="txt_fieldRO" style="width:95%">
		          </td>
		        </tr>
		        <tr>
		          <td class="tdblueL">STEP</td>
		          <td colspan="3" class="tdwhiteL0">
		            ${data.STEPICONURLSTR}&nbsp;${data.STEPNAME}<br> 
		            <input type="hidden" name="stepcontentoid" value="${data.STEPCONTENTOID}">
		            <input type="hidden" name="iFileStepOrg" value="${data.STEPNAME}">
		            <input  name="iFileStep" type="file" class="txt_fieldRO" style="width:95%">
		          </td>
		        </tr>
		        <tr>
		          <td class="tdblueL">IGS</td>
		          <td colspan="3" class="tdwhiteL0">
		            ${data.IGSICONURLSTR}&nbsp;${data.IGSNAME}<br> 
		            <input type="hidden" name="igscontentoid" value="${data.IGSCONTENTOID}">
		            <input type="hidden" name="iFileIgsOrg" value="${data.IGSNAME}">
		            <input   name="iFileIgs" type="file" class="txt_fieldRO" style="width:95%">
		          </td>
		        </tr>
				</c:forEach>
            </table>
        </td>
    </tr>
</table> 
  </form>
 
 <iframe id="hiddenFrame" name="hiddenFrame" style="display:none;"></iframe>
 