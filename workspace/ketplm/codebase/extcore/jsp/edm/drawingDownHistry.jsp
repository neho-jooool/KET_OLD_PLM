<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript"
    src="/plm/extcore/js/edm/drawingDistRequest.js"></script>
    
<table width="1280" height="100%" border="0" cellspacing="0"
    cellpadding="0">
    <tr>
        <td valign="top">
            <table width="1280" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="1280" height="28" border="0" cellspacing="0"
                            cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">배포요청서</td>
                                <td align="right"><img
                                    src="/plm/portal/images/icon_navi.gif">조회</td>
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
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="5"></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                        <a href="/plm/ext/edm/drawingDistRequestViewForm.do?oid=${drawingDistRequestOid}">기본정보</a>
                      </td>
                      <td><img src="/plm/portal/images/tab_5.png"></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_1.png"></td>
                      <td background="/plm/portal/images/tab_3.png" class="btn_tab">다운로드이력</td>
                      <td><img src="/plm/portal/images/tab_2.png""></td>
                    </tr>
                  </table></td>
                </tr>
              </table>
            <table width="1280" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue"
                                                background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:drawingDistRequest.goModify('${drawingDistRequestOid}');"
                                                class="btn_blue">수정</a></td>
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
                                                background="/plm/portal/images/btn_bg1.gif">
                                                <!-- 목록  --> <a
                                                href="javascript:drawingDistRequest.goList();"
                                                class="btn_blue">목록</a>
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
            <table border="0" cellspacing="0" cellpadding="0" width="1280">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="1280">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table id="drawingDownHisTable" border="0" cellspacing="0" cellpadding="0" width="100%">
	            <tr>
	                <td width="30" class="tdblueM">NO</td>
	                <td class="tdblueM">사용자</td>
	                <td class="tdblueM">일시</td>
	                <td class="tdblueM">파일명</td>
	                <td class="tdblueM">배포처</td>
	                <td class="tdblueM">다운로드 사유</td>
	            </tr>
	            <c:forEach items="${drawingDownHisList}" var="drawingDownHis" varStatus="i">
	                <tr>
	                   <td align="center">${i.index+1}</td>
	                   <td align="center">${drawingDownHis.creator}</td>
	                   <td align="center">${drawingDownHis.downloadDate}</td>
	                   <td align="center">${drawingDownHis.downloadFileName}</td>
	                   <td align="center">${drawingDownHis.distSubContractor}</td>
	                   <td align="center">${drawingDownHis.downloadReason}</td>
	                </tr>
	            </c:forEach>
	        </table>
        </td>
    </tr>
</table>