<%@page import="e3ps.common.treegrid.TreeGridUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  // EJS TreeGrid Start
  response.addHeader("Cache-Control","max-age=1, must-revalidate");
  // EJS TreeGrid End

  String tgData = "<I " + TreeGridUtil.replaceForI("TEXT=\"\"") + "/>"; // 붙여넣기 위해 한 라인 생성
  
  String modal = request.getParameter("modal");
  String fnCall = request.getParameter("fnCall");

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<base target="_self">
<title><%=messageService.getString("e3ps.message.ket_message", "01588") %><%--부품번호 입력--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %> <!-- MultiCombo & jQuery -->

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
table {
    border-spacing: 0;
    border: 0px;
}
table th, table td {padding: 0}
img {
    vertical-align: middle;
}
input {
    vertical-align:middle;line-height:22px;
}
</style>
<script type="text/javascript">
//<![CDATA[

  var firstRendering = false;

  // Grid Rendering 완료 후 호출
  Grids.OnRenderFinish = function(G) {

    if (firstRendering == false) {
        initGrid();
        firstRendering = true;
    }

    displayPartNoCnt(G);        // 입력된 부품 수 표시
  }

  // Grid 값 변경 후 호출
  Grids.OnAfterValueChanged = function(G, row, col, val) {
      displayPartNoCnt(G);      // 입력된 부품 수 표시
  }

  // Grid 행 복사 붙여넣기(Ctrl+V) 후 호출
  Grids.OnPasteRowFinish = function(G, row, cols, values, added) {
      displayPartNoCnt(G);      // 입력된 부품 수 표시
  }

  // Grid 행 삭제 시 호출
  Grids.OnRowDelete = function(G, row, type) {
      displayPartNoCnt(G);      // 입력된 부품 수 표시
  }

  // Grid 입력된 부품 수 표시
  function displayPartNoCnt(G) {

      var cnt = 0;
      var msg = "<%=messageService.getString("e3ps.message.ket_message", "02385") %><%--입력된 부품 수--%>";

      if ( G != null ) {
          for ( var r = G.GetFirst(); r; r = G.GetNext(r) ) {
              if ( r != null && r.PartNumber != undefined && r.PartNumber != "" && r.Deleted != "1" ) {     // 입력된 Part No가 있고 삭제된 행이 아닐 경우 Counting
                  cnt++;
              }
          }
      }

      G.SetValue(G.Toolbar, "Cnt", cnt, 1);     // 입력된 부품 수 표시
  }

  function initGrid() {
   
	var inputPartNos = "";
	if(window.dialogArguments && window.dialogArguments.document && window.dialogArguments.document.getElementById("inputPartNos") ){
	    window.dialogArguments.document.getElementById("inputPartNos").value;
	    if (inputPartNos != undefined && inputPartNos != "") {
	      var inputPartNosAry = inputPartNos.split(",");
	      var G = Grids[0];
	      for (var i = 0; i < inputPartNosAry.length; ++i) {
	        var newrow = G.AddRow(G, G.GetLast(), true);
	        G.SetValue(newrow, "PartNumber", inputPartNosAry[i], 1);
	      }
	    }
	}
  }

  function selectOk() {
    if (firstRendering == false) {
      alert('<%=messageService.getString("e3ps.message.ket_message", "02820") %><%--초기화 중입니다--%>');
      return;
    }

    var G = Grids[0];
    var retAry = new Array();
    var idx = 0;

    for(var r = G.GetFirst(); r; r = G.GetNext(r)) {
      if (r.Deleted != "1" && r.PartNumber != undefined && r.PartNumber != "") {
        retAry[idx++] = r.PartNumber;
      }
    }

    if (retAry.length == 0) {
      alert('<%=messageService.getString("e3ps.message.ket_message", "02384") %><%--입력된 데이터가 없습니다--%>');
      return;
    }

    var modal = '<%=modal%>';
    
    if(modal == 'N'){
    	try{
    		if(opener) {
                opener.<%=fnCall%>(retAry);
            }else if(parent.opener) {            
                parent.opener.<%=fnCall%>(retAry);
            }	
    	}catch(e){
    		alert(e);
    	}
        
        
    }else{
    	window.returnValue= retAry;
    }
    
    window.close();
    
    
  }

  function clearAll() {
    if (confirm('<%=messageService.getString("e3ps.message.ket_message", "01707") %><%--삭제하시겠습니까?--%>') == false) {
      return;
    }

    var G = Grids[0];
    for(var r = G.GetFirst(); r; r = G.GetNext(r)) {
      G.DeleteRow(r, 2);
    }
  }

//]]>
</script>
</head>

<body>
<form name="form01" method="post">

<table style="width:500px; height:100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="height:50px;" valign="top"><table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table style="height:28px;" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td style="width:7px;"></td>
            <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01588") %><%--부품번호 입력--%></td>
          </tr>
        </table></td>
        <td style="width:136px;" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top">
      <table style="width:450px; height:100%;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td style="width:10px;">&nbsp;</td>
          <td valign="top">
            <table style="width:450px;" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right">
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
<%--
                      <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:addRow();" class="btn_blue">행 추가</a></td>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td style="width:5px;">&nbsp;</td>
--%>
<%--
                      <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:clearAll();" class="btn_blue">초기화</a></td>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td style="width:5px;">&nbsp;</td>
--%>
<%--
                      <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:selectOk();" class="btn_blue">확인</a></td>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
--%>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:450px;">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>

            <!-- EJS TreeGrid Start -->
            <div class="content-main">
              <div class="container-fluid">
                <div class="row-fluid">
                  <div style="WIDTH: 100%; HEIGHT: 100%">
                    <bdo Debug="1" AlertError="0"
                        Layout_Url="/plm/jsp/bom/InputPartNumberPopupGridLayout.jsp"
                        Data_Url="/plm/jsp/common/searchGridData.jsp"
                        Data_Method="POST"
                        Data_Param_Result="<%=tgData %>"
                    ></bdo>
                  </div>
                </div>
              </div>
            </div>
            <!-- EJS TreeGrid End -->

            <table border="0" cellspacing="0" cellpadding="0" style="width:450px;">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table width="500" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:selectOk();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="28" valign="bottom">
      <table style="width:450px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td style="width:10px;">&nbsp;</td>
          <td style="height:28px;"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" style="width:450px; height:24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
