<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script src="/plm/extcore/js/wfm/workflow/listWorkItem.js?ver=3"></script>
<script type="text/javascript">
    $(document).ready(function(e) {
        WorkItem.createPaingGrid();
        treeGridResize("#listWorkItemGrid","#listWorkItemGridDiv");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        $(function(){
            $("#filterClass").multiselect({
                minWidth : 200,
                noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
                checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
                uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
            });
        });
    });
</script>
<form name="form01" method="post">
  <table style="width: 100%;" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top">
        <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <table style="width: 100%;" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00760")%><%--결재대기함--%></td>
                  <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00788")%><%--결재함--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00760")%><%--결재대기함--%></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td class="head_line"></td>
          </tr>
          <tr>
            <td class="space10"></td>
          </tr>
          <tr>
            <td align="right">
              <table>
                <tr>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:WorkItem.doBatchCompleteWorkItem();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "05105")%><%--일괄승인--%></a></td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="5">&nbsp;</td>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:WorkItem.search()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><!-- 검색 --></a></td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td class="space5"></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
          <tr>
            <td style="width: 150px;" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242")%><!-- 유형 --></td>
            <td class="tdwhiteL0"><select name="filterClass" id="filterClass" class="fm_jmp" multiple="multiple">
                <c:forEach items="${typeList }" var="entry">
                  <option value="<c:out value="${entry.key}"/>"><c:out value="${entry.value}" /></option>
                </c:forEach>
            </select></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
          <tr>
            <td class="space5"></td>
          </tr>
        </table> <!-- EJS TreeGrid Start -->
        <div class="container-fluid">
          <div class="row-fluid">
            <div id="listWorkItemGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
          </div>
        </div> <!-- EJS TreeGrid End -->
      </td>
    </tr>
  </table>
</form>
