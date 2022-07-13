<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%@ page import = "wt.vc.Versioned"%>
<%@ page import = "wt.vc.VersionControlHelper"%>
<%@ page import="java.util.*" %>
<%@ page import = "wt.org.*,wt.session.*,wt.content.*"%>
<%@ page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 ext.ket.shared.content.entity.*,
                 ext.ket.project.gate.entity.*,
                 e3ps.groupware.company.*"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<%
String oid = request.getParameter("oid");

ext.ket.project.gate.entity.GateAssessResult gateAssResult = ext.ket.project.task.service.ProjectTaskCompHelper.service.getGateAssessResultObj(oid);
String resultState = gateAssResult.getState().toString();
String passComment = StringUtil.checkNull(gateAssResult.getPassComment());

java.util.ArrayList<ContentDTO> secondaryFilesArr = (java.util.ArrayList<ContentDTO>)request.getAttribute("secondaryFiles");
%>

<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript">
function updateGateResultAttatch() 
{
    showProcessing(); //Progressbar 보이기
    $('[name=reviseAssessSheetUpdateForm]').attr('action', '/plm/ext/project/task/updateGateResultAttatch.do');
    $('[name=reviseAssessSheetUpdateForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
    $('[name=reviseAssessSheetUpdateForm]').submit();      
    hideProcessing(); //Progressbar 감추기
}


$(document).ready(function(){

//     $("#active").multiselect("uncheckAll");
})


 /**
  * 파일 추가
  */
 function insertFileLine() {
     //첨부파일 라인을 추가한다.
     var tBody = document.getElementById("iFileTable");
     var innerRow = tBody.insertRow();
     var innerCell = innerRow.insertCell();
     var filehtml = "";
     filehtml += "  <input type='checkbox' name='iFileChk' id='checkbox' >";
     filehtml += "  <input name='secondaryFiles' type='file' class='txt_fieldRO' style='width:90%'>";
     innerCell.innerHTML = filehtml;
     
     
//      var innerRow = iFileTableOld.insertRow();
//      innerRow.height = "27";
//      var tBody = document.getElementById("iFileTableOld");
//      //var innerRow = tBody.insertRow();
//      //var innerCell = innerRow.insertCell();
//      var filePath = "filePath"+tBody.rows.length;

//      var filehtml = "";

//      for ( var k = 0; k < 2; k++) {
//          innerCell = innerRow.insertCell();
//          innerCell.height = "23";

//          if (k == 0) {
//              innerCell.className = "tdwhiteM";
//              innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
//                    + "<div style=\"display:none;\"><input name='iFileChk' type='checkbox' class='chkbox'></div>";
//          } else if (k == 1) {
//              innerCell.className = "tdwhiteL0";
//              innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' style='width: 100%;'>";
//          }
//      }
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
 
  function insertFileLine() {

      var innerRow = iFileTableOld.insertRow();
        innerRow.height = "27";
        var tBody = document.getElementById("iFileTableOld");
        //var innerRow = tBody.insertRow();
        //var innerCell = innerRow.insertCell();
        var filePath = "filePath"+tBody.rows.length;
        filePath = "secondaryFiles";

        var filehtml = "";

        for ( var k = 0; k < 2; k++) {
            innerCell = innerRow.insertCell();
            innerCell.height = "23";

            if (k == 0) {
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                      + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
            } else if (k == 1) {
                innerCell.className = "tdwhiteL0";
                innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_fieldRO' style='width: 100%;'>";
            }
        }
  }
  
  function deleteFileLine() {

    var body = document.getElementById("iFileTable");

    var file_checks = document.forms[0].iFileChk;
    if (body.rows.length == 0){

    }else if (body.rows.length == 1) {
      if (file_checks[0]=="[object]"){
        if (file_checks[0].checked){
          body.deleteRow(0);
        }
      }else{
        if (file_checks.checked){
          body.deleteRow(0);
        }
      }
    } else {
      for (var i = body.rows.length; i > 0; i--) {
        if (file_checks[i-1].checked) body.deleteRow(i - 1);
      }
    }

    body = document.getElementById("iFileTableOld");
    var ContentOid1 = document.getElementById("ContentOid");

    file_checks = document.forms[0].iFileChkOld;
    if (body.rows.length == 0){

    }else if (body.rows.length == 1) {
      if (file_checks[0]=="[object]"){
        if (file_checks[0].checked){

          document.form01.isFileDel.value = document.form01.isFileDel.value + "/" + document.form01.ContentOid[0].value;
          body.deleteRow(0);
        }
      }else{
        if (file_checks.checked){

          document.form01.isFileDel.value = document.form01.isFileDel.value + "/" + document.form01.ContentOid.value;
          body.deleteRow(0);
        }
      }
    } else {
      for (var i = body.rows.length; i > 0; i--) {
        if (file_checks[i-1].checked){

          document.form01.isFileDel.value = document.form01.isFileDel.value + "/" + document.form01.ContentOid[i-1].value;
          body.deleteRow(i-1);
        }
      }
    }
  }

</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07241")%><%--첨부파일--%></td>
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

      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
<%
    if("INWORK".equals(resultState) || "REWORK".equals(resultState)) {
%>
                  <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:updateGateResultAttatch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></span><span class="pro-cell b-right"></span></span></span>

<%
    }
%>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:this.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
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
            <table border="0" cellspacing="0" cellpadding="0" width="99.5%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <form name="reviseAssessSheetUpdateForm"  method="post" enctype="multipart/form-data">
            <input type="hidden" name="oid" value="<%=oid%>">
            <input type=hidden name=isFileDel value="0">

                            
      <table border="0" cellspacing="0" cellpadding="0" width="100%">

       <tr>
                        <td width="65"  class="tdblueL">
                            조건부합격<br>&nbsp;&nbsp;&nbsp;&nbsp;근거
                        </td>
                        <td class="tdwhiteM0">
                            <textarea name="passComment" id="passComment" class='txt_field' style="height:70px;width:98%;"><%=passComment%></textarea>
                        </td>
                    </tr>
        <tr>

                <tr>
                    <td class="space5"></td>
                </tr>
          <td width="65" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
          <td class="tdwhiteM0">
             <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                <table width="100%" cellpadding="0" cellspacing="0">
<%
    if("INWORK".equals(resultState) || "REWORK".equals(resultState)) {
%>
                    <tr class="headerLock3">
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                                <tr>
                                    <td width="20" class="tdgrayM">
                                        <a href="#" onclick="javascript:insertFileLine();return false;"><img src="/plm/portal/images/b-plus.png"></a></td>
                                    <td width="" class="tdgrayM0">파일명(※ <%=messageService.getString("e3ps.message.ket_message", "03252")%><%--회의록, 주변Data, Concept view 관련 파일 유첨바랍니다.--%>)</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
<%
    }
%>

                    <tr>
                        <td>
                            <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                                <col width="20">
                                <col width="">
                                <tbody id="iFileTableOld">
                                <%
                                       String appDataOid;
                                       String urlpath;
                                       String iconpath;
                                
                                      
                                        if(secondaryFilesArr!=null && secondaryFilesArr.size()>0){
                                
                                          for(int i=0; i<secondaryFilesArr.size(); i++) {
                                              ContentDTO dto = (ContentDTO)secondaryFilesArr.get(i);
                                            if( dto == null) {
                                              iconpath = "";
                                              urlpath = "";
                                            } else {
                                              ContentItem ctf=(ContentItem)CommonUtil.getObject(dto.getContentoid());
                                              appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                                              //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(gateAssResult)+"&adOid="+appDataOid;
                                              urlpath = dto.getDownURLStr();
                                               urlpath = "<a href=" + urlpath + " target='_blank'>" + dto.getName() + "</a>&nbsp;(&nbsp;" + dto.getFileSize() + "&nbsp;)";
                                              iconpath = dto.getIconURLStr();
                                
                                            }
                                %>
                                <tr>
                                    <td class="tdwhiteM">&nbsp;
<%
    if("INWORK".equals(resultState) || "REWORK".equals(resultState)) {
%>

                                        <a href="#" onclick="javascript:$(this).parent().parent().remove();return false;"><img src="/plm/portal/images/b-minus.png"></a>
<%
    }
%>

                                        <input name='secondaryFileDel' type='hidden' value='${content.contentoid}'>

                                    </td>
                                    <td class="tdwhiteL0"><input name='secondaryFileOids' class='txt_fieldRO' id='secondaryFileOids' type='hidden' value='<%=dto.getContentoid()%>'><%=iconpath%>&nbsp;<%=urlpath%>
                                    </td>
                                </tr>
                                <%
                                          }
                                        }else {
                                %>
                                <tr>
                                    <td class="tdwhiteM">&nbsp;
                                    </td>
                                </tr>
                                <%
                                        }
                                %>
			                </tbody>
			                </table>
                        </td>
                    </tr>
	            </table>
	            <table width="100%" border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	          </td>
	        </tr>
	      </table>
                 
            </form>
            
        </td>
    </tr>
    <tr>
        <td style="height: 30px;" valign="bottom">
            <table style="width: 560px;">
            <tr>
                <td style="width: 10px;">&nbsp;</td>
                <td style="height: 30px;"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" style="width: 560px; height: 24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
            </tr>
            </table>
        </td>
  </tr>
</table>
<script type="text/javascript">
<!--
<%
String isSuccess = request.getParameter("isSuccess");
if("Y".equals(isSuccess)) {
%>
//opener.reviseSuccess();
//opener.document.location.reload();
<%-- opener.parent.document.location.href="/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=oid%>&popup=popup"; --%>
opener.goReloadPage();
this.close();
<%
}
%>
//-->
</script>
