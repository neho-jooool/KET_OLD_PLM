<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script src="/plm/extcore/js/wfm/workspace/listMyTodo.js?ver=0.1"></script>
<script type="text/javascript">
    $(document).ready(function(e) {
        MyTodo.createPaingGrid();
        treeGridResize("#listMyTodoGrid","#listMyTodoGridDiv");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        MyTodo.search();
    });
</script>
<form name="form01" method="post">
<input type="hidden" name="filterClass" value="${filterClass }"> 
  <table style="width: 100%;" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top">
        <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <table style="width: 100%;" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01">My Todo</td>
                  <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02435")%><%--작업공간--%>
                    <img src="/plm/portal/images/icon_navi.gif">My Todo</td>
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
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyTodo.doDelegateWorkItem()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02233")%><!-- 위임 --></a></td>
                  <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  <%-- 
                  <td width="5">&nbsp;</td>
                  <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyTodo.doAcknowledgement()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01931")%><!-- 수신확인 --></a></td>
                  <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                   --%>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td class="space5"></td>
          </tr>
        </table> <!-- EJS TreeGrid Start -->
        <div class="container-fluid">
          <div class="row-fluid">
            <div id="listMyTodoGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
          </div>
        </div> <!-- EJS TreeGrid End -->
      </td>
    </tr>
  </table>
</form>