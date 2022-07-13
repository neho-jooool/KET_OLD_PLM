<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<!-- EJS TreeGrid Start -->
<script type="text/javascript" src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script src="/plm/extcore/js/dms/listTotalDocument.js"></script>
<script type="text/javascript">
    $(document).ready(function() {

        // Combo Box
        
        CommonUtil.multiSelect('state', 100);
        CommonUtil.singleSelect('version', 100);

        $("#state").val("APPROVED");
        $("#state").multiselect("refresh");
        
        $("#state").multiselect('disable');
        
        // suggest
        SuggestUtil.bind('PROJECTDOCTYPE', 'documentCategoryTxt', 'documentCategory');
        SuggestUtil.bind('PARTNO', 'partNo');

        // Calener field 설정
        CalendarUtil.dateInputFormat('createDateFrom', 'createDateTo');

        // default 한달 설정
        $('[name=createDateFrom]').val(predate);
        $('[name=createDateTo]').val(postdate);

        // Project 구분 radio click event
        $("[name=pjtType]").click(function() {
            CommonUtil.deleteValue('pjtNoTxt', 'pjtNo');

            if ("" == $(this).val()) {
                $("[name=pjtType]").unbind();
            } else if ("제품" == $(this).val()) {
                SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo');
            } else if ("검토" == $(this).val()) {
                SuggestUtil.bind('REVIEWPROJNO', 'pjtNo');
            } else if ("금형" == $(this).val()) {
                SuggestUtil.bind('DIENO', 'pjtNo');
            }
        });

        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if (e.which == 13) {
                MyDocument.search();
                return false;
            }
        });
        
        // grid rendering
        MyDocument.createPaingGrid(<%=request.getParameter("isSingle")%>);
        treeGridResize("#myDocumentGrid","#myDocumentGridDiv");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    });
    
    function confirm(){
    	var G = Grids[0];
        var rsltArrayObject = new Array();
        var rsltObject;
        var idx = 0;

        if( G != null ){

            var R = G.GetSelRows();

            if( R.length == 0 ){
                alert("문서를 선택해 주십시오.");
                //alert('<%= messageService.getString("e3ps.message.ket_message", "03111") %>');<%--프로젝트를 선택해 주십시오--%>
                return;
            }
            for ( var i = 0; i < R.length; i++) {
                rsltObject = new Object();
                rsltObject.INDEX = 0;
                rsltObject.oid = R[i].oid;
                rsltObject.createDate = R[i].createDate;
                rsltObject.creator = R[i].creator; 
                rsltObject.state = R[i].state;
                rsltObject.version = R[i].version;
                rsltObject.documentNo = R[i].documentNo;
                rsltObject.documentCategory = R[i].documentCategory;
                rsltObject.documentCategoryTxt = R[i].documentCategoryTxt;
                rsltObject.documentName = R[i].documentName;
                rsltObject.primaryConentDownUrl = R[i].primaryConentDownUrl;
                rsltObject.buyerSummit = R[i].buyerSummit;
                rsltObject.pjtNo = R[i].pjtNo;
                rsltObject.pjtType = R[i].pjtType;
                rsltObject.security = R[i].security;
                
                rsltArrayObject.push(rsltObject);
            }
            opener.<%=request.getParameter("callBackFn")%>(rsltArrayObject);
            window.close();
        }
    }
    
    function setOneProject(objArr){
    	MyDocument.setProjectNo(objArr);
    }
    
    function setOneCategory(checkedNode){
    	MyDocument.selectDocCategory(checkedNode);
    }
</script>
<form name="frm" method="POST">
  <table style="width: 100%; height: 100%;">
    <tr>
      <td valign="top">
        <table style="width: 100%;">
          <tr>
            <td>
              <table style="width: 100%; height: 28px;">
                 <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">문서 검색</td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td class="space5"></td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td>&nbsp;</td>
            <td align="right">
              <table>
                <tr>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyDocument.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a></td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="5">&nbsp;</td>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:MyDocument.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
                        </td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="5">&nbsp;</td>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:confirm();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226")%><%--확인--%></a>
                        </td>
                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="5">&nbsp;</td>
                  <td>
                       <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                  href="javascript:self.close();" class="btn_blue">취소</a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="tdblueL""><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%></td>
            <td colspan="3" class="tdwhiteL0">
              <input type="text" name="documentCategoryTxt" class="txt_field" style="width: 220px"> 
              <input type="hidden" name="documentCategory" value=""><a href="javascript:SearchUtil.selectDocCategory('setOneCategory');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
              <a href="javascript:CommonUtil.deleteValue('documentCategoryTxt','documentCategory');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
          </tr>
          <tr>
            <td class="tdblueL""><%=messageService.getString("e3ps.message.ket_message", "01420")%><%--문서번호--%></td>
            <td class="tdwhiteL"><input type="text" id="documentNo" name="documentNo" class="txt_field" style="width: 220px;" value=""></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415")%><%--문서명--%></td>
            <td class="tdwhiteL0"><input type="text" id="documentName" name="documentName" class="txt_field" style="width: 220px" value=""></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771")%><%--결재상태--%></td>
            <td class="tdwhiteL"><ket:select id="state" name="state" className="fm_jmp" style="width: 220px" multiple="multiple" lifeCycleState="KET_COMMON_LC" value="INWORK" /></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
            <td class="tdwhiteL0">
              <input type="text" name="createDateFrom" class="txt_field" style="width: 80px;" value=""> 
              <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateFrom');" style="cursor: hand;" > ~ 
              <input type="text" name="createDateTo" class="txt_field" style="width: 80px;">
              <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateTo');" style="cursor: hand;"></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00397")%><%--Project No--%></td>
            <td colspan="3" class="tdwhiteL0">
              <input type="radio" name="pjtType" value="" checked="checked"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%> 
              <input type="radio" name="pjtType" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
              <input type="radio" name="pjtType" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%>
              <input type="radio" name="pjtType" value="검토"><%=messageService.getString("e3ps.message.ket_message", "00716")%><%--검토--%> 
              <input type="text" id="pjtNo" name="pjtNo" class="txt_field" style="width: 220px;" value="" > 
              <a href="javascript:SearchUtil.selectOneProject('setOneProject')"><img src="/plm/portal/images/icon_5.png" border="0"></a>
              <a href="javascript:CommonUtil.deleteValue('pjtNo','pjtNo');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
            <td class="tdwhiteL"><ket:select id="version" name="version" className="fm_jmp" style="width: 220px;" multiple="multiple" codeType="VERSION" value="671979013" /></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569")%><%--부품번호--%></td>
            <td class="tdwhiteL0"><input type="text" id="partNo" name="partNo" class="txt_field" style="width: 220px;" value=""></td>
          </tr>
        </table>
        <table style="width: 100%;">
          <tr>
            <td class="space5"></td>
          </tr>
        </table> <!-- EJS TreeGrid Start -->
        <div class="container-fluid">
          <div class="row-fluid">
            <div id="myDocumentGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
          </div>
        </div> <!-- EJS TreeGrid End -->
      </td>
    </tr>
  </table>
</form>
