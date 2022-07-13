<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<!-- <script type="text/javascript" src="/plm/jsp/wfm/viewPBO.js"></script> -->
<script src="/plm/extcore/js/wfm/workflow/listTempWorkItem.js"></script>
<script type="text/javascript">
    $(document).ready(function(e) {
        // Calener field 설정
        CalendarUtil.dateInputFormat('predate', 'postdate');
        // default 기간 설정
        $('[name=predate]').val(predate);
        $('[name=postdate]').val(postdate);
        WorkItem.createPaingGrid();
        treeGridResize("#listTempWorkItemGrid","#listTempWorkItemGridDiv");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        // Enter
        $("form[name=form01]").keypress(function(e) {
            if (e.which == 13) {
                WorkItem.search();
                return false;
            }
        });
        $(function(){
            $("#filterClass").multiselect({
                minWidth : 200,
                noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>',
                checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498")%><%--전체선택--%>',
                uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500")%><%--전체해제--%>'
            });
        });
    });
</script>
<form name="form01" method="post">
  <table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <table style="width: 100%; height: 28px;" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02375")%><%--임시저장함--%></td>
                  <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00788")%><%--결재함--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02375")%><%--임시저장함--%></td>
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
        </table>
        <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
          <tr>
            <td>
              <table border="0" align="right" cellpadding="0" cellspacing="0">
                <tr>
                  <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:WorkItem.search()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><!-- 검색 --></a></td>
                  <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
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
          <colgroup>
            <col width="15%">
            <col width="35%">
            <col width="15%">
            <col width="35%">
          </colgroup>
          <tr>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242")%><!-- 유형 --></td>
            <td class="tdwhiteL0"><select name="filterClass" id="filterClass" class="fm_jmp" multiple="multiple">
                <c:forEach items="${typeList }" var="entry">
                  <option value="<c:out value="${entry.key}"/>"><c:out value="${entry.value}" /></option>
                </c:forEach>
            </select></td>
            <td style="width: 150px;" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02428")%><!-- 작성일 --></td>
            <td class="tdwhiteL0"><input type="text" id="predate" name="predate" class="txt_field" style="width: 100px;" value=""> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('predate');" style="cursor: hand;"
            > ~ <input type="text" id="postdate" name="postdate" class="txt_field" style="width: 100px;"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('postdate');" style="cursor: hand;"
            ></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
          <tr>
            <td class="space5"></td>
          </tr>
        </table> <!-- EJS TreeGrid Start -->
        <div class="container-fluid">
          <div class="row-fluid">
            <div id="listTempWorkItemGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
          </div>
        </div> <!-- EJS TreeGrid End --></td>
    </tr>
  </table>
</form>
