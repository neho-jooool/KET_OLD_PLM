<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "java.util.ArrayList,
                  java.util.Hashtable,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.PageControl"%>

<%
    Hashtable condition = (Hashtable)request.getAttribute("condition");
    ArrayList list = (ArrayList)request.getAttribute("list");
    PageControl control = (PageControl)request.getAttribute("control");

    String sortCol1 = messageService.getString("e3ps.message.ket_message", "00946")/*관리번호*/;
  String sortSQL = "";
    if( condition != null && !condition.isEmpty() )
    {
        sortSQL = condition.get("sort").toString().replace(" ", "");
    }

    if(sortSQL.equals("1ASC") || sortSQL.equals("1DESC")) {
        sortCol1 = "<u>"+sortCol1+"</u>";
    }

    String sortCol2 = messageService.getString("e3ps.message.ket_message", "03114")/*프로젝트번호*/;
    if(sortSQL.equals("2ASC") || sortSQL.equals("2DESC")) {
        sortCol2 = "<u>"+sortCol2+"</u>";
    }

    String sortCol3 = messageService.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/;
    if(sortSQL.equals("3ASC") || sortSQL.equals("3DESC")) {
        sortCol3 = "<u>"+sortCol3+"</u>";
    }

    String sortCol5 = messageService.getString("e3ps.message.ket_message", "01791")/*생산처*/;
    if(sortSQL.equals("5ASC") || sortSQL.equals("5DESC")) {
        sortCol5 = "<u>"+sortCol5+"</u>";
    }

    String sortCol6 = messageService.getString("e3ps.message.ket_message", "03024")/*품질*/;
    if(sortSQL.equals("6ASC") || sortSQL.equals("6DESC")) {
        sortCol6 = "<u>"+sortCol6+"</u>";
    }

    String sortCol7 = messageService.getString("e3ps.message.ket_message", "01972")/*수행담당(정)*/;
    if(sortSQL.equals("7ASC") || sortSQL.equals("7DESC")) {
        sortCol7 = "<u>"+sortCol7+"</u>";
    }

    String sortCol8 = messageService.getString("e3ps.message.ket_message", "01971")/*수행담당(부)*/;
    if(sortSQL.equals("8ASC") || sortSQL.equals("8DESC")) {
        sortCol8 = "<u>"+sortCol8+"</u>";
    }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>

<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}

.inoutCut{
width:80px;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}

.pjtCut{
width:75px;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}

.partCut{
width:110px;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}

.memberCut{
width:65px;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}

img {   
    vertical-align:baseline;
}
-->
</style>

<script language='javascript'>
<!--
    function hideProcessing() {}

    //사용자 셋팅
    function acceptMember(rname, objArr) {
        if(objArr.length == 0) {
            return;
        }

        var displayName = document.getElementById(rname+'name');
        var keyName = document.getElementById(rname);

        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            displayName.value = infoArr[4];
            keyName.value = infoArr[0];
        }
    }

  //필드 값 초기화
  function deleteValueAll(){
      document.forms[0].ewsno.value = "";
      document.forms[0].pjtno.value = "";
      document.forms[0].pjtname.value = "";
      document.forms[0].fstcharge.value = "";
      document.forms[0].fstchargename.value = "";
      document.forms[0].sndcharge.value = "";
      document.forms[0].sndchargename.value = "";
      document.forms[0].creator.value = "";
      document.forms[0].creatorname.value = "";
      document.forms[0].member.value = "";
      document.forms[0].membername.value = "";
  }

    //페이지 조회
    function gotoPage(pageno){
        document.forms[0].cmd.value = "search";
        document.forms[0].page.value = pageno;
        search();
    }

    //sort 조회
    function doSortCall(sortSQL){
        document.forms[0].cmd.value = "search";
        document.forms[0].sort.value = sortSQL;
        document.forms[0].page.value = "";
        document.forms[0].totalCount.value = "0";
        search();
    }

    //검색결과 정렬
    function doSort(sortColId, sortColName){
        var sortSQL = "";
        var sqlColNumber = '<%=StringUtil.getNumber(sortSQL)%>';
        var sqlColName = '<%=sortSQL%>';
        var sortA = sortColName+"ASC";
        var sortD = sortColName+"DESC";


        if(sqlColNumber == sortColName) {
            if(sqlColName == sortA){
                sortSQL = sortColName + " DESC ";
            }else if(sqlColName == sortD){
                sortSQL = sortColName + " ASC ";
            }
        } else {
            sortSQL = sortColName + " ASC ";
        }

        doSortCall(sortSQL);
    }

    // 검색
    function search(){
        disabledAllBtn();
        showProcessing();

        document.forms[0].cmd.value = "popup";
        document.forms[0].action =  '/plm/servlet/e3ps/EarlyWarningServlet';
        document.forms[0].submit();
    }

    // 초기값 셋팅
    function initPage(){
        <%
            if( condition != null && !condition.isEmpty() ){
        %>
                document.forms[0].page.value = '<%=condition.get("page")%>';
                document.forms[0].sort.value = '<%=condition.get("sort")%>';
                document.forms[0].ewsno.value = '<%=condition.get("ewsno")%>';
                document.forms[0].pjtno.value = '<%=condition.get("pjtno")%>';
                document.forms[0].pjtname.value = '<%=condition.get("pjtname")%>';
                document.forms[0].fstcharge.value = '<%=condition.get("fstcharge")%>';
                document.forms[0].fstchargename.value = '<%=condition.get("fstchargename")%>';
                document.forms[0].sndcharge.value = '<%=condition.get("sndcharge")%>';
                document.forms[0].sndchargename.value = '<%=condition.get("sndchargename")%>';
                document.forms[0].creator.value = '<%=condition.get("creator")%>';
                document.forms[0].creatorname.value = '<%=condition.get("creatorname")%>';
                document.forms[0].member.value = '<%=condition.get("creator")%>';
                document.forms[0].membername.value = '<%=condition.get("creatorname")%>';
                document.forms[0].useroid.value = '<%=condition.get("useroid")%>';

                for(var inx=0; inx< document.forms[0].perPage.options.length ; inx++){
                    if( document.forms[0].perPage.options[inx].value =='<%=condition.get("perPage")%>'){
                        document.forms[0].perPage.options[inx].selected = true;
                    }
                }
        <%
            }
            if( control != null ){
        %>
                document.forms[0].totalCount.value = '<%=control.getTotalCount()%>';
        <%
            }
        %>
    }

    function goProject(){
        var useroid = document.forms[0].useroid.value;
        window.location = "/plm/jsp/project/ListMyProjectPop.jsp?command=MySearch&wtUser="+useroid;
    }

//-->
</script>

</head>
<body onload="initPage();">
<form method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" value="search">
<input type="hidden" name="page" value="">
<input type="hidden" name="totalCount" value="0">
<input type="hidden" name="sort" value="1 DESC">
<input type="hidden" name="useroid" value="">
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png">
              <table height="28" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="7"></td>
                  <td width="20"><img src="../../portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03084") %><%--프로젝트 이력 목록--%></td>
                </tr>
              </table>
            </td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table>
    </td>
  </tr>
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="5"></td>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="../../portal/images/tab_4.png"></td>
                  <td background="../../portal/images/tab_6.png" class="btn_tab"><a href="javascript:goProject();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%></a></td>
                  <td><img src="../../portal/images/tab_5.png"></td>
                </tr>
              </table>
            </td>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="../../portal/images/tab_1.png"></td>
                  <td background="../../portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02808") %><%--초기유동--%></td>
                  <td><img src="../../portal/images/tab_2.png""></td>
                </tr>
              </table>
            </td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_9.gif"></td>
          <td height="10" background="../../portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_10.gif"></td>
        </tr>
        <tr>
          <td width="10" background="../../portal/images/box_13.gif">&nbsp;</td>
          <td valign="top">

          <!---------------------------------     본문    Start    -------------------------------------------->

                    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td valign="top">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                            <tr>
                              <td>&nbsp;</td>
                              <td align="right">
                                  <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                          <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                              <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteValueAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                              <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                          </table>
                                      </td>
                                      <td width="5">&nbsp;</td>
                                      <td>
                                          <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                              <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                              <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                          </table>
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
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                              <td  class="tab_btm2"></td>
                            </tr>
                          </table>
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
                              <td colspan="3" class="tdwhiteL0"><input type="text" name="ewsno" class="txt_field"  style="width:641" ></td>
                            </tr>
                            <tr>
                              <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                              <td  class="tdwhiteL"><input type="text" name="pjtno" class="txt_field"  style="width:235" ></td>
                              <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                              <td  class="tdwhiteL0"><input type="text" name="pjtname" class="txt_field"  style="width:235" ></td>
                            </tr>
                            <tr>
                              <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01975") %><%--수행담당자(정)--%></td>
                              <td  class="tdwhiteL">
                                  <input type='hidden' name='fstcharge' value=''>
                                  <input type="text" name="fstchargename" class="txt_fieldRO" style="width:187;cursor:hand;" onclick="javascript:selectUser('fstcharge');" readonly>
                                  &nbsp;<a href="javascript:selectUser('fstcharge');"><img src="../../portal/images/icon_user.gif" border="0"></a>
                                  &nbsp;<a href="javascript:deleteValue('fstcharge');deleteValue('fstchargename');"><img src="../../portal/images/icon_delete.gif" border="0"></a>
                              </td>
                              <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01974") %><%--수행담당자(부)--%></td>
                              <td  class="tdwhiteL0">
                                  <input type='hidden' name='sndcharge' value=''>
                                  <input type="text" name="sndchargename" class="txt_fieldRO" style="width:187;cursor:hand;" onclick="javascript:selectUser('sndcharge');" readonly>
                                  &nbsp;<a href="javascript:selectUser('sndcharge');"><img src="../../portal/images/icon_user.gif" border="0"></a>
                                  &nbsp;<a href="javascript:deleteValue('sndcharge');javascript:deleteValue('sndchargename');"><img src="../../portal/images/icon_delete.gif" border="0"></a>
                              </td>
                            </tr>
                            <tr>
                              <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03024") %><%--품질--%></td>
                              <td  class="tdwhiteL">
                                  <input type='hidden' name='creator' value=''>
                                  <input type="text" name="creatorname" class="txt_fieldRO" style="width:187;cursor:hand;" onclick="javascript:selectUser('creator');" readonly>
                                  &nbsp;<a href="javascript:selectUser('creator');"><img src="../../portal/images/icon_user.gif" border="0"></a>
                                  &nbsp;<a href="javascript:deleteValue('creator');javascript:deleteValue('creatorname');"><img src="../../portal/images/icon_delete.gif" border="0"></a>
                              </td>
                              <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03242") %><%--활동멤버--%></td>
                              <td  class="tdwhiteL0">
                                  <input type='hidden' name='member' value=''>
                                  <input type="text" name="membername" class="txt_fieldRO" style="width:187;cursor:hand;" onclick="javascript:selectUser('member');" readonly>
                                  &nbsp;<a href="javascript:selectUser('member');"><img src="../../portal/images/icon_user.gif" border="0"></a>
                                  &nbsp;<a href="javascript:deleteValue('member');javascript:deleteValue('membername');"><img src="../../portal/images/icon_delete.gif" border="0"></a>
                              </td>
                            </tr>
                          </table>
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                              <td class="space15"></td>
                            </tr>
                          </table>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                            <tr>
                              <td>&nbsp;</td>
                              <td align="right">
                                  <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                      <td align="right">
                                          <select name="perPage" class="fm_jmp" style="width:50">
                                              <option value="10">10</option>
                                            <option value="30">30</option>
                                            <option value="50">50</option>
                                            <option value="100">100</option>
                                            </select>
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
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                              <td  class="tab_btm2"></td>
                            </tr>
                          </table>
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                              <td  class="tdblueM">NO</td>
                              <td  class="tdblueM"><a href="javascript:doSort('sortCol1', '1');"><span id="sortCol1"><%=sortCol1%></span><a></td>
                              <td  class="tdblueM"><a href="javascript:doSort('sortCol2', '2');"><span id="sortCol2"><%=sortCol2%></span><a></td>
                              <td  class="tdblueM"><a href="javascript:doSort('sortCol3', '3');"><span id="sortCol3"><%=sortCol3%></span><a></td>
                              <td  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01564") %><%--부품--%></td>
                              <td  class="tdblueM"><a href="javascript:doSort('sortCol5', '5');"><span id="sortCol5"><%=sortCol5%></span><a></td>
                              <td  class="tdblueM"><a href="javascript:doSort('sortCol6', '6');"><span id="sortCol6"><%=sortCol6%></span><a></td>
                              <td  class="tdblueM"><a href="javascript:doSort('sortCol7', '7');"><span id="sortCol7"><%=sortCol7%></span><a></td>
                              <td  class="tdblueM"><a href="javascript:doSort('sortCol8', '8');"><span id="sortCol8"><%=sortCol8%></span><a></td>
                              <td  class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "03242") %><%--활동멤버--%></td>
                            </tr>
                              <%
                                Hashtable earlyWarning = null;
                                int lastInx = 0;
                                if( control != null){
                                  lastInx = control.getTotalCount() - ((control.getCurrentPage() - 1) * control.getInitPerPage());
                              }
                              if( list != null && list.size() > 0 ){
                                    for(int inx = 0 ; inx < list.size(); inx++){
                                              earlyWarning = (Hashtable)list.get(inx);
                            %>
                            <tr>
                              <td class="tdwhiteM"><%=lastInx--%></td>
                              <td class="tdwhiteM"><%=earlyWarning.get("ewsno")%></td>
                              <td class="tdwhiteM"><span class="pjtCut" title="<%=earlyWarning.get("pjtno") %>"><%=earlyWarning.get("pjtno")%></span></td>
                              <td class="tdwhiteM"><span class="pjtCut" title="<%=StringUtil.checkNull((String)earlyWarning.get("pjtname"))%>"><%=StringUtil.checkNull((String)earlyWarning.get("pjtname"))%>&nbsp;</span></td>
                              <td class="tdwhiteL"><span class="partCut" title="<%=earlyWarning.get("partno")%>"><%=earlyWarning.get("partno")%>&nbsp;</span></td>
                              <td class="tdwhiteM"><span class="inoutCut" title="<%=earlyWarning.get("inout")%>"><%=earlyWarning.get("inout")%>&nbsp;</span></td>
                              <td class="tdwhiteM"><%=earlyWarning.get("creator") %></td>
                              <td class="tdwhiteM"><%=earlyWarning.get("fstcharge") %>&nbsp;</td>
                              <td class="tdwhiteM"><%=StringUtil.checkNull((String)earlyWarning.get("sndcharge"))%>&nbsp;</td>
                              <td class="tdwhiteM0"><%=StringUtil.checkNull((String)earlyWarning.get("member"))%>&nbsp;</td>
                            </tr>
                            <%
                                  }
                              }else{
                            %>
                             <tr>
                                         <td colspan="10"  class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
                                     </tr>
                            <%}%>
                          </table>
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                              <td class="space10"></td>
                            </tr>
                          </table>
                          <table border="0" cellspacing="0" cellpadding="0" width="100%" id="pageControlTable">
                              <%if(list != null) {%>
                                  <%@include file="/jsp/ecm/PageEcmInclude.jsp" %>
                              <%} %>
                          </table>
                        </td>
                      </tr>
                    </table>

                    <!---------------------------------     본문    End    -------------------------------------------->

                    </td>
          <td width="10" background="../../portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_11.gif"></td>
          <td height="10" background="../../portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_12.gif"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="740" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
</form>
</body>
</html>
