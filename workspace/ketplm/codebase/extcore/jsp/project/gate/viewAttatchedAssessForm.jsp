<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 e3ps.groupware.company.*"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />

<%
	String oid = request.getParameter("oid");
	String versionNo = request.getParameter("versionNo");
    Map<String, Object> parameter = new HashMap<String, Object>();

%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript">


$(document).ready(function(){
    
});
</script>
<table border="0" cellpadding="0" cellspacing="0" width="580px">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="580px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="580px" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07149") %><%--평가항목/목표 첨부파일--%></td>
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
      <table width="580px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
                

            <form name="ViewAssessSheetSearchForm" method="post">
            <input type="hidden" name="oid" value="<%=oid %>"/>
            <input type="hidden" name="versionNo" value="<%=versionNo %>"/>
            <input type="hidden" name="delOids" value=""/>
                <table border="0" cellspacing="0" cellpadding="0" width="580">
                    <tr>
                        <td height="30" align="left">
                          Rev. : ${ASS_VERSION} , &nbsp;&nbsp;
                          <%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일 --%> : ${CREATE_DATE} , &nbsp;&nbsp;
                          <%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태 --%> : ${ASS_STATE_NAME}  
                        </td>
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                    <a href="javascript:this.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a>
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
                
          </td>
        </tr>
      </table>

            </form>
            <table border="0" cellspacing="0" cellpadding="0" width="580">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="580">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="580">
                    
                <!--  tr>
                    <td class="tdblueL">주 첨부파일<span class="red">*</span></td>
                    <td colspan="3" class="tdwhiteL0">
                        <a target='download' href='${primaryFile.downURLStr}'>${primaryFile.iconURLStr}</a>&nbsp;
                        <a href='${primaryFile.downURLStr}' target='_blank'>${primaryFile.name}</a>&nbsp; ( ${primaryFile.fileSizeKB} )
                        <input name="primaryFile" type="file" class="txt_fieldRO" style="width:100%">
                    </td>
                </tr-->
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                    <td colspan="3" class="tdwhiteL0">
                        <table width="380" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="380">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="380">
                            <tbody id="iFileTableOld" />
                            <c:forEach var="content" items="${secondaryFiles}">
                                <tr>
                                    <td>
                                        <input type='checkbox' name='iFileChk' id='checkbox'>
                                        <input name='secondaryFileOids' type='hidden' value='${content.contentoid}'>
                                        <a target='download' href='${content.downURLStr}'>${content.iconURLStr}</a>&nbsp;
                                        <a href='${content.downURLStr}' target='_blank'>${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)
                                        </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="380">
                            <tbody id="iFileTable" />
                        </table>
                        <table width="380" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
