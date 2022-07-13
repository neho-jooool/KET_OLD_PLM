<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<!-- EJS TreeGrid Start -->
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<!-- <script src="/plm/extcore/js/wfm/workspace/listMyTask.js"></script> -->
<script src="/plm/extcore/js/wfm/workspace/listMyTask2.js?ver=0.1"></script>
<script type="text/javascript">
    $(document).ready(function() {

        // 프로젝트 상태
        CommonUtil.multiSelect('searchPjtState', 100);
        // 완료여부
        CommonUtil.multiSelect('searchState', 100);
        CommonUtil.singleSelect('searchType', 100);

        // Calener field 설정
        CalendarUtil.dateInputFormat('planStartStartDate', 'planStartEndDate');
        CalendarUtil.dateInputFormat('planEndStartDate', 'planEndEndDate');

        // default 한달 설정
        //$('[name=planStartStartDate]').val(predate);
        //$('[name=planStartEndDate]').val(postdate);
        //$('[name=planEndStartDate]').val(predate);
        //$('[name=planEndEndDate]').val(postdate);

        // grid rendering
        MyTask.createGrid();

        if($("[name=paramPjtType]").val() == "제품"){
            $("[name=searchPjtType]")[1].checked = true;
            MyTask.search();
        } else if($("[name=paramPjtType]").val() == "금형"){
            $("[name=searchPjtType]")[2].checked = true;
            MyTask.search();
        } else if($("[name=paramPjtType]").val() == "검토"){
            $("[name=searchPjtType]")[3].checked = true;
            MyTask.search();
        } else if($("[name=paramPjtType]").val() == "업무"){
            $("[name=searchPjtType]")[4].checked = true;
            MyTask.search();
        }else {
            $("[name=searchPjtType]")[0].check = true;
        }
        

        // Enter 검색
        $("form[name=myTask]").keypress(function(e) {
            if (e.which == 13) {
                MyTask.search();
                return false;
            }
        });
        // Project 구분 radio click event
        $("[name=searchPjtType]").click(function() {
            CommonUtil.deleteValue('pjtNo','pjtNo');
            if("" == $(this).val()){
                $("[name=pjtNo]").unbind();
            } else if("제품" == $(this).val()){
                SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo');
            } else if("검토" == $(this).val()){
                SuggestUtil.bind('REVIEWPROJNO', 'pjtNo');
            } else if("금형" == $(this).val()){
                SuggestUtil.bind('DIENO', 'pjtNo');
            } else if("업무" == $(this).val()){
                SuggestUtil.bind('WORKPROJNO', 'pjtNo');
            }
        });

        MyTask.search();

        if($("[name=searchType]").val() == "plan" || $("[name=searchType]").val() == "progress" || $("[name=searchType]").val() == "delay"){
            MyTask.search();
        }
        treeGridResize("#myTaskGrid","#myTaskGridDiv");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        <%-- 
        $(function(){
            $("#searchType").multiselect({
                minWidth : 200,
                noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802")%>',
                checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498")%>',
                uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500")%>'
            });
        });
         --%>
    });
</script>
</head>
<form name="myTask" method="POST">
  <!-- hidden begin -->
  <input type="hidden" name="searchYN" value="Y" />
  <input type="hidden" name="paramPjtType" value="${searchPjtType }">
  <%-- <input type="hidden" name="searchType" value="${searchType }" /> --%>
  <!-- hidden end -->
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
                  <td class="font_01">My Task</td>
                  <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02435")%><%--작업공간--%>
                    <img src="/plm/portal/images/icon_navi.gif">My Task</td>
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
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyTask.registerProjectOutput();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "05122")%><%--동일 산출물 등록--%></a></td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <td style="width: 5px;">&nbsp;</td>
                  <td>
                    <table>
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyTask.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a>
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
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyTask.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
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
        <!-- 검색영역 collapse/expand -->
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
        <!-- [START] search condition -->
        <table style="width: 100%;">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <table style="width: 100%;">
        <colgroup>
          <col width="12%">
          <col width="38%">
          <col width="12%">
          <col width="38%">
        </colgroup>
          <tr>
            <td class="tdblueL"">Project No</td>
            <td class="tdwhiteL">
              <input type="radio" name="searchPjtType" value="" checked="checked"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%>
              <input type="radio" name="searchPjtType" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
              <input type="radio" name="searchPjtType" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%> 
              <input type="radio" name="searchPjtType" value="검토"><%=messageService.getString("e3ps.message.ket_message", "00716")%><%--검토--%>
              <input type="radio" name="searchPjtType" value="업무"><%=messageService.getString("e3ps.message.ket_message", "09533")%><%--업무--%>
              <input type="text" id="pjtNo" name="pjtNo" class="txt_field" style="width: 100px;" value="">
              <a href="javascript:MyTask.selProjectNo()"><img src="/plm/portal/images/icon_5.png" border="0"></a>
              <a href="javascript:CommonUtil.deleteValue('pjtNo','pjtNo');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
            <td class="tdblueL">Task Name</td>
            <td class="tdwhiteL0"><input type="text" id="taskName" name="taskName" class="txt_field" style="width: 98%;" value=""></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07165", "")%><%--계획시작일--%></td>
            <td class="tdwhiteL"><input type="text" name="planStartStartDate" class="txt_field" style="width: 70px"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('planStartStartDate');" style="cursor: hand;"
            > &nbsp;~&nbsp; <input type="text" name="planStartEndDate" class="txt_field" style="width: 70px"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('planStartEndDate');" style="cursor: hand;"
            ></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07166", "")%><%--계획종료일--%></td>
            <td class="tdwhiteL0"><input type="text" name="planEndStartDate" class="txt_field" style="width: 70px"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('planEndStartDate');" style="cursor: hand;"
            > &nbsp;~&nbsp; <input type="text" name="planEndEndDate" class="txt_field" style="width: 70px"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteValue('planEndEndDate');" style="cursor: hand;"
            ></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03076")%><%--프로젝트 상태--%></td>
            <td class="tdwhiteL"><ket:select id="searchPjtState" name="searchPjtState" className="fm_jmp" style="width: 270px;" multiple="multiple" codeType="MYTASKPJTSTATE" value="${searchPjtState }" /></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02176")%><%--완료여부--%></td>
            <td class="tdwhiteL0" nowrap="nowrap">
              <ket:select id="searchState" name="searchState" className="fm_jmp" style="width: 200px;" multiple="multiple" codeType="MYTASKCOMPLETE" value="${searchState }" />
              <select name="searchType" id="searchType" class="fm_jmp" multiple="multiple" style="width: 100px;">
                <option value="plan" <c:if test="${searchType == 'plan' }" >selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "00798")%></option>
                <option value="progress" <c:if test="${searchType == 'progress' }" >selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "02726")%></option>
                <option value="delay" <c:if test="${searchType == 'delay' }" >selected</c:if>><%=messageService.getString("e3ps.message.ket_message", "02703")%></option>
              </select>
            </td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="space5"></td>
          </tr>
        </table> <!-- [END] search condition --> <!-- EJS TreeGrid Start -->
        <div class="container-fluid">
          <div class="row-fluid">
            <div id="myTaskGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
          </div>
        </div> <!-- EJS TreeGrid End -->
      </td>
    </tr>
  </table>
</form>
