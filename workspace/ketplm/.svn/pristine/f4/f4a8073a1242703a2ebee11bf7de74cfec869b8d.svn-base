<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%@ page import = "wt.vc.Versioned"%>
<%@ page import = "wt.vc.VersionControlHelper"%>
<%@ page import="java.util.*" %>
<%@ page import = "wt.org.*,wt.session.*"%>
<%@ page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 ext.ket.project.gate.entity.*,
                 e3ps.groupware.company.*"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<%
String pjtOid = request.getParameter("pjtOid");
AssessSheetDTO assessSheetDto = (AssessSheetDTO)request.getAttribute("assessSheet");
AssessSheet assessSheet = (AssessSheet) CommonUtil.getObject(assessSheetDto.getOid()); 
//Versioned vs = (Versioned)assessSheet;
String note = StringUtil.checkNull(VersionControlHelper.getNote(assessSheet));
%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<style type="text/css">
table {
    border-spacing: 0;
    border: 0px;
}
table th, table td {padding: 0}
img {
    vertical-align: middle;
}
input {
    vertical-align:middle;line-height:0px;
}
</style>

<script type="text/javascript">
function reviseAssessSheet() 
{
    
	if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "03287")%><%--개정하시겠습니까?--%>')){ //
		return;
    }
	$('#revisediv').css('height','500px');
	$('#revisediv').loadMask('loading...');
	
// 	showProcessing(); //Progressbar 보이기
	
//     $.ajax({
//         type       : "POST",
//         url        : "/plm/ext/project/gate/reviseAssessSheetInfos.do",
//         data       : $("#reviseAssessSheetUpdateForm").serialize(),
//         dataType   : "json",
//         async : false,
//         success    : function(data){
//         },
//         error    : function(xhr, status, error){
//                      alert(xhr+"  "+status+"  "+error);
//         }
//     });
	
//     hideProcessing(); //Progressbar 감추기

	$('[name=reviseAssessSheetUpdateForm]').attr('action', '/plm/ext/project/gate/reviseAssessSheetInfos.do');
    $('[name=reviseAssessSheetUpdateForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
    $('[name=reviseAssessSheetUpdateForm]').submit();

    $('#revisediv').css('height','0px');
	$('#revisediv').unLoadMask();
}


$(document).ready(function(){

 // multiselect
    CommonUtil.singleSelect('devType',100);
    CommonUtil.singleSelect('division',100);
    CommonUtil.singleSelect('devDiv',100);
    CommonUtil.singleSelect('prodCategory',100);
    CommonUtil.singleSelect('active',100);
//     $("#active").multiselect("uncheckAll");

})

function insertFileLine() {
    var innerRow = fileTable.insertRow();
    innerRow.height = "27";
    var innerCell;

    var filePath = "filePath";
    var filehtml = "";

    for ( var k = 0; k < 2; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";

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

/**
 * 파일 삭제
 */
 function deleteFileLine() {
    $('[name=iFileChk]').each(function(){
        if($(this).is(':checked')){
            $(this).parent().remove();
        }
    });   
}

</script>
<form name="reviseAssessSheetUpdateForm" method="post" enctype="multipart/form-data">
<table border="0" cellpadding="0" cellspacing="0" width="616px">
    <tr>
        <td valign="top" style="padding: 0px 0px 0px 0px">
            <table width="616px" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table width="616px" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td background="/plm/portal/images/logo_popupbg.png">
                                    <table height="28" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07179")%><%--개정사유--%></td>
                                            <td width="10"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table width="616px" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:reviseAssessSheet();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684")%><%--00684--%></a></td>
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
                                                <!-- 목록  --> <a href="javascript:this.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%></a>
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
            <table width="616px" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>

                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="616px">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="616px">
                <tr>

                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <div id="revisediv"></div>

                <input type="hidden" name="oid" value="${assessSheet.oid}"> <input type="hidden" name="pjtOid" value="<%=pjtOid%>">
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="tdblueL" width="80"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                        <td class="tdwhiteL">
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
                                                    <td width="20" class="tdgrayM"><a href="#"
                                                        onclick="javascript:insertFileLine(); return false;""><b><img
                                                                src="/plm/portal/images/b-plus.png"></b></a></td>
                                                    <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                                    <col width="20">
                                    <col width="">
                                    <tbody id="fileTable">
                                    </tbody>
                                </table>
                            </div>
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table>
                       </td>
                   </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07179")%><%--개정사유--%></td>
                        <td class="tdwhiteL"><input type="text" name="reviseCause" style="width: 100%" class="txt_field" value="<%=note%>"></td>
                    </tr>
                </table>
        </td>
    </tr>
</table>
</form>
<tr>
    <td style="height: 30px;" valign="bottom">
        <table style="width: 560px;">
            <tr>
                <td style="width: 10px;">&nbsp;</td>
                <td style="height: 30px;"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright"
                        style="width: 560px; height: 24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
            </tr>
        </table>
    </td>
</tr>
</table>
<script type="text/javascript">
<%
String isSuccess = request.getParameter("isSuccess");
if ("Y".equals(isSuccess)) {
%>
alert("<%=messageService.getString("e3ps.message.ket_message", "01893")%><%--성공하였습니다--%>");
//opener.reviseSuccess();
opener.document.location.reload();
this.close();
<%
}
%>
//-->
</script>
