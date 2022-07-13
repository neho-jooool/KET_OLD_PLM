<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<!-- EJS TreeGrid Start -->
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<!-- <script src="/plm/extcore/js/wfm/workspace/listMyProject.js"></script> -->
<script src="/plm/extcore/js/wfm/workspace/listMyProject2.js?ver=0.3"></script>
<script type="text/javascript">
    $(document).ready(function() {

        // 프로젝트 상태
        CommonUtil.multiSelect('searchPjtState', 100);

        // Calener field 설정
        CalendarUtil.dateInputFormat('planStartStartDate', 'planStartEndDate');
        CalendarUtil.dateInputFormat('planEndStartDate', 'planEndEndDate');

        // default 한달 설정
        /* $('[name=planStartStartDate]').val(predate);
        $('[name=planStartEndDate]').val(postdate);
        $('[name=planEndStartDate]').val(predate);
        $('[name=planEndEndDate]').val(postdate); */

        // grid rendering
        //MyProject.createPaingGrid();
        MyProject.createGrid();
        treeGridResize("#myProjectGrid","#myProjectGridDiv");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        
        if ($("[name=paramPjtType]").val() == "제품") {
            $("[name=searchPjtType]")[1].checked = true;
            MyProject.search();
        } else if ($("[name=paramPjtType]").val() == "금형") {
            $("[name=searchPjtType]")[2].checked = true;
            MyProject.search();
        } else if ($("[name=paramPjtType]").val() == "검토") {
            $("[name=searchPjtType]")[3].checked = true;
            MyProject.search();
        } else if ($("[name=paramPjtType]").val() == "업무") {
            $("[name=searchPjtType]")[4].checked = true;
            MyProject.search();
        } else {
            $("[name=searchPjtType]")[0].check = true;
        }

        // Enter 검색
        $("form[name=myProject]").keypress(function(e) {
            if (e.which == 13) {
                MyProject.search();
                return false;
            }
        });
        // Project 구분 radio click event
        $("[name=searchPjtType]").click(function() {
            CommonUtil.deleteValue('searchPjtNoTxt', 'searchPjtNo');
            if ("" == $(this).val()) {
                $("[name=searchPjtNo]").unbind();
            } else if ("제품" == $(this).val()) {
                SuggestUtil.bind('PRODUCTPROJNO', 'searchPjtNo');
            } else if ("검토" == $(this).val()) {
                SuggestUtil.bind('REVIEWPROJNO', 'searchPjtNo');
            } else if ("금형" == $(this).val()) {
                SuggestUtil.bind('DIENO', 'searchPjtNo');
            } else if ("업무" == $(this).val()) {
                SuggestUtil.bind('WORKPROJNO', 'searchPjtNo');
            }
        });

        MyProject.search();
    });
</script>
<form name="myProject" method="POST">
  <input type="hidden" name="paramPjtType" value="${searchPjtType }">
  <table style="width: 100%; height: 100%;">
    <tr>
      <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 100%;">
          <tr>
            <td>
              <table style="width: 100%; height: 28px;">
                <tr>
                  <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01">My Project</td>
                  <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02435")%><%--작업공간--%>
                    <img src="/plm/portal/images/icon_navi.gif">My Project</td>
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
        </table> <!-- [END] title & position --> <!-- [START] button -->
        <table style="width: 100%;">
          <tr>
            <td>&nbsp;</td>
            <td align="right">
              <table>
                <tr>
                  <td>
                    <table>
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyProject.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a>
                        </td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <td style="width: 5px;">&nbsp;</td>
                  <td>
                    <table>
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyProject.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
                        </td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="space5"></td>
          </tr>
        </table> <!-- [END] button --> 
        <!-- [START] search condition -->
        <!-- 검색영역 collapse/expand -->
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
        <table style="width: 100%;">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="tdblueL" style="width: 80px;"><%=messageService.getString("e3ps.message.ket_message", "00397")%><%--Project No--%></td>
            <td class="tdwhiteL" style="width: 320px;"><input type="radio" name="searchPjtType" value="" checked="checked"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%>
              <input type="radio" name="searchPjtType" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
              <input type="radio" name="searchPjtType" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%>
              <input type="radio" name="searchPjtType" value="검토"><%=messageService.getString("e3ps.message.ket_message", "00716")%><%--검토--%>
              <input type="radio" name="searchPjtType" value="업무"><%=messageService.getString("e3ps.message.ket_message", "09533")%><%--업무--%>
              <input type="text" id="searchPjtNo" name="searchPjtNo" class="txt_field" style="width: 220px;" value="">
                <a href="javascript:MyProject.selProjectNo()"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                <a href="javascript:CommonUtil.deleteValue('searchPjtNo','searchPjtNo');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL" style="width: 80px;">PM</td>
            <td class="tdwhiteL0" style="width: 450px;"><input type="checkbox" id="isPm" name="isPm" value='Y'></td>
          </tr>
          <tr>
            <td class="tdblueL">Project Name</td>
            <td class="tdwhiteL"><input type="text" id="searchPjtName" name="searchPjtName" class="txt_field"  value=""></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760")%><%--상태--%></td>
            <td class="tdwhiteL0"><ket:select id="searchPjtState" name="searchPjtState" className="fm_jmp" multiple="multiple" codeType="MYTASKPJTSTATE"
                value="${searchPjtState }"
              /></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07165")%><%--계획시작일--%></td>
            <td class="tdwhiteL"><input type="text" name="planStartStartDate" class="txt_field" style="width: 70px"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('planStartStartDate');" style="cursor: hand;"
            > &nbsp;~&nbsp; <input type="text" name="planStartEndDate" class="txt_field" style="width: 70px"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('planStartEndDate');" style="cursor: hand;"
            ></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07166")%><%--개발종료일--%></td>
            <td class="tdwhiteL0"><input type="text" name="planEndStartDate" class="txt_field" style="width: 70px"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('planEndStartDate');" style="cursor: hand;"
            > &nbsp;~&nbsp; <input type="text" name="planEndEndDate" class="txt_field" style="width: 70px"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('planEndEndDate');" style="cursor: hand;"
            ></td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="space5"></td>
          </tr>
        </table> <!-- [END] search condition --> <!-- EJS TreeGrid Start -->
        <div class="container-fluid">
          <div class="row-fluid">
            <div id="myProjectGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
          </div>
        </div> <!-- EJS TreeGrid End -->
      </td>
    </tr>
  </table>
</form>
