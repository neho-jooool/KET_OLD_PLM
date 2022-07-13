<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList,
         java.util.Hashtable,
         e3ps.bom.common.util.*,
         e3ps.bom.service.KETBOMHeaderQueryBean,
         e3ps.bom.common.iba.IBAUtil,
         e3ps.common.util.StringUtil,
         e3ps.project.beans.MoldProjectHelper,
         e3ps.ecm.beans.EcmSearchHelper,
         e3ps.common.web.PageControl"%>
<%@page import="e3ps.groupware.board.beans.MyTestOption"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  // EJS TreeGrid Start
  response.addHeader("Cache-Control","max-age=1, must-revalidate");
  // EJS TreeGrid End

  String mode = searchCondition.getString("mode"); // [multi|one](legacy), [m|s] 가능
  boolean multi = ("multi".equalsIgnoreCase(mode) || "m".equalsIgnoreCase(mode)); // default: s(ingle)
  String fncall = searchCondition.getString("fncall");      // Opener 리턴 함수 지정
  String modal = searchCondition.getString("modal");      // Modal 여부 지정
  String fromPage = searchCondition.getString("fromPage");
  String pType = searchCondition.getString("pType"); // 외부에서 interface되는 partType
  
  // 부품 유형 고정 Flag ex) : 제품으로 fix, 금형으로 fix 등에 사용
  String partTypeDisable = "N";
  if (org.apache.commons.lang.StringUtils.isEmpty(pType)) { // 1) 외부에서 부품유형을 정의 안하거나, 2) 정의한 후 재호출
      partTypeDisable = searchCondition.getString("partTypeDisable");
      if ("Y".equals(partTypeDisable)) { // 2) 정의한 후 재호출
	    
      }else{
	    partTypeDisable = "N"; // 1) 외부에서 부품유형을 정의 안함
	    pType = "A";           // 기본적으로 전체를 선택하도록 처리     
      }
  }else{ // 외부에서 부품유형을 정의해서 바로 호출 경우 - 부품유형이 고정됨
      partTypeDisable = "Y";
  }

System.out.println("@@@@@@@@@@@@@ mode: [" + mode + "](multi=" + multi + ")" + ",  fncall: [" + fncall + "], modal: [" + modal + "], fromPage: [" + fromPage + "], pType: [" + pType + "]");

  // 검색조건
  String partType = searchCondition.getString("partType"); // 내부에서 사용되는 partType
  if (StringUtil.isEmpty(partType)) {
    partType = pType;
  }
  String number = searchCondition.getString("number");
  String name = searchCondition.getString("name");
  String inputPartNos = searchCondition.getString("inputPartNos");

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<base target="_self">
<title><%=messageService.getString("e3ps.message.ket_message", "01566") %><%--부품 검색 팝업--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/jsp/bom/js/part_js.jsp"></script>

<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<!-- 자동완성 스크립트 -->
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>

<style type="text/css">
body {
    margin-left: 5px;
    margin-right: 5px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
-->
</style>

<script type="text/javascript" charset="UTF-8">

  function validation(){
    var form01 = document.forms[0];
    // 검색조건 미입력 validation
    if (form01.number.value == "" && form01.name.value == "" && form01.inputPartNos.value == ""){
      alert('<%=messageService.getString("e3ps.message.ket_message", "03135") %><%--하나 이상의 검색조건을 입력해 주십시오--%>');
      return false;
    }

    // 부품번호 검색조건 입력 시, 자릿수 체크
    if ( form01.number.value != "" ) {
      var numberOnly = form01.number.value.split("*").join("");
      if (numberOnly.length < 3) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00712") %><%--검색조건은 '*'를 제외하고 3자리 이상입니다--%>");
        return false;
      }
    }

    // 부품명 검색조건 입력 시, 자릿수 체크
    if ( form01.name.value != "" ) {
      if (form01.name.value.indexOf("*") == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00711") %><%--검색조건은 '*'로 시작할 수 없습니다--%>");
        form01.name.value = "";
        return false;
      }

      var nameOnly = form01.name.value.split("*").join("");
      if (nameOnly.length < 3) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00712") %><%--검색조건은 '*'를 제외하고 3자리 이상입니다--%>");
        return false;
      }
    }
    return true;
  }

  // 검색
  function search(){
    var form01 = document.forms[0];

    if (!validation()) {
      return;
    }

    disabledAllBtn();
    showProcessing();
    
    $("input:radio[name='partType']").attr("disabled",false);

    form01.cmd.value="searchPop";
    form01.action = "/plm/servlet/e3ps/PartServlet";
    form01.submit();
  }

  // 초기화
  function deleteValue(){
    var form01 = document.forms[0];
    form01.number.value = "";
    form01.name.value = "";
    form01.inputPartNos.value = "";
    // 결과내재검색 체크해제
    $("input:checkbox[id=searchInSearch]").attr("checked", false);

  }

  function selectPart() {
    var G = Grids[0];
    var arr = new Array();
    var subArr = new Array();
    //var idx = 0;

    if( G != null ){
        var R = G.GetSelRows();

        if( R.length == 0 ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01596") %><%--부품을 선택한 후 확인을 눌러주세요!--%>');
            return;
        }

        for ( var i=0; i<R.length; i++ ) {
             subArr = new Array();

             subArr[0] = "wt.part.WTPart:" + R[i].Oid;//oid
             subArr[1] = R[i].PartNumber;//number
             subArr[2] = R[i].PartName;//name
             subArr[3] = R[i].PartVersion;//version
             subArr[4] = R[i].PartType;//type
             subArr[5] = R[i].DieNo;//dieno
             subArr[6] = R[i].DieName;
             subArr[7] = R[i].DieCnt;
             subArr[8] = R[i].OidMaster;//wtpartmaster oid
             subArr[9] = R[i].PartIteration;//wtpart oid

             arr.push(subArr);
        }
    }

    if( document.forms[0].modal.value == 'Y' ){
        window.returnValue = arr;
        window.close();
      } else {
        <%if (fncall != null && fncall.equals("")) {  // 리턴 함수가 지정되어있지 않은 경우 기본 Opener 함수 호출%>
          opener.selectedPart(arr);
        <%}else {                    // 리턴 함수가 지정된 경우 해당 Opener 함수 호출%>
          opener.<%=fncall%>(arr);
        <%}%>
        window.close();
      }
  }

  // 전체 체크박스 선택
  function checkAll(obj)
  {
    var form01 = document.forms[0];
    if (form01.checkedRecord) {
      // 있는데 1개일 경우
      if (form01.checkedRecord.length == undefined) {
        if (obj.checked) {
          form01.checkedRecord.checked = true;
        } else {
          form01.checkedRecord.checked = false;
        }
      // 2개 이상일 경우
      } else {
        for (i=0; i<form01.checkedRecord.length; i++) {
          if (obj.checked) {
            form01.checkedRecord[i].checked = true;
          } else {
            form01.checkedRecord[i].checked = false;
          }
        }
      }
    }
  }

  //처리중 화면 전환
  function hideProcessing() {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    div1.style.display = "none";
    div2.style.display = "none";
  }

  // 부품 상세조회 화면 호출
  function viewPart(v_poid){

    var url = "/plm/jsp/bom/ViewPart.jsp?poid=wt.part.WTPart:" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
  }

  // 부품검색 팝업 호출 함수
  function serachPartPopup(){
    var url = "/plm/ext/part/base/listPartPopup.do??mode=one";
    openWindow(url,"","1024","768","scrollbars=yes,resizable=no,top=200,left=250");
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////
  // 팝업창에서 선택된 부품 목록을 받아 화면에 display 하는 함수
  //
  // arrayData[inx][jnx]
  //  :: inx :: 부품검색 팝업에서 선택한 부품의 갯수
  //  :: jnx :: 0(oid), 1(부품번호), 2(부품명), 3(부품버전), 4(부품유형: 'P'제품, 'D':DieNo, 'M':금형부품)
  //            5(부품이 관련된 PMS의 Die No)
  // , 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'P', '포장재', 'S', '스크랩', 'W', '상품','제품'('P'-DB 없음)
  //////////////////////////////////////////////////////////////////////////////////////////////////////////
  function selectedPart(arrayData){
    var form01 = document.forms[0];
    // 멀티선택 예제
    var strTemp ="";
    for( var inx=0; inx < arrayData.length ; inx++ ){
      strTemp += arrayData[inx][1] + ",";
  }
  form01.number.value = strTemp;

  // 단일선택 예제
  form01.number.value = arrayData[0][1];
  }

  window.onload = function() {
    initPartNumberTextbox();
  }
  
  function helpTest(){
	  var    text = " <<제품>> :\n H31~H33, H41~H43, H45~H49, H51~H67, H71~H79, \n"; 
	  text = text + " H88, H89,H31~H33, H41~H43, H45~H49, H51~H67, \n";
	  text = text + " H71~H79, H88, H89, HKW, HKS, HKH, HKP, HK, K, KD4, KR, \n";
	  text = text + " 31, R40~R42, R44, R50, R60, R68 \n\n";
	  text = text + " <<Die>> :\n 10,11,1A,1T,1Y,20,21,22,23,26,29,2A,2T,\n 30,40,60,LC,LI,LM,LP,YT \n\n"; 
	  text = text + " <<Mold>> :\n 07,10,11,13,16,19,1A,1R,1T,1Y,20,21,22,\n23,24,25,26,27,29,2A,2T,30,40,D0 \n";
	  text = text + " ,D1,D2,D3,D4,D5,D6,D7,D8,D9,EJ,ET,IY,L0,LI,LM,LP,\nP0,P1,P2,P3,P4,P5,P6,P7,P8 \n";
	  text = text + " ,P9,R0,S1,S2,S3,S4,S7,S8,S9,ST,U0,UP,Z4 \n";
	  //text = text + " 제품 : 610056-3, 477016-09, KL10024-00, H615390-2, S200406, R103081, 310464-AS \n";
	  //text = text + " Die No : 1T389000 22089001 10860C00 22845A00 LP020000 1Y007000 10929000 1A171000 2T220000 \n";
	  //text = text + " 금형 부품 : 11141000-P52, 11183000-STD27, 10861B00-P18-7 11133-D09-2 21906A00-019 1T389000-D09 \n";
	  alert(text);
  }
  
  $(document).ready(function(){
	  
	// Enter 검색
      $("form[name=form01]").keyup(function(e) {
          if ( e.which == 13 ) {
              search();
              return false;
          }
      });
	
	  //부품 suggest
	  SuggestUtil.bind('PARTNO', 'number');
	    
	  <%
	    if("Y".equals(partTypeDisable)){
	  %> 
	  $("input:radio[name='partType']").attr("disabled",true);
	  <%
	    }
	  %>  
	    
	});


</script>
</head>

<body class="body" >
<form name="form01" method="post">
<input type="hidden" name="cmd">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="fncall" value="<%=fncall%>">
<input type="hidden" name="modal" value="<%=modal%>">
<input type="hidden" name="fromPage" value="<%=fromPage%>">
<input type="hidden" name="partTypeDisable" value="<%=partTypeDisable%>">
<input type="hidden" name="oldPartType" value="<%=partType%>">
<input type="hidden" id="inputPartNos" name="inputPartNos" value="<%=inputPartNos %>">

        <table style="width: 100%; height: 100%;">
            <tr>
                <td height="50" valign="top">
                    <table style="width: 100%;">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table style="height: 28px;">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <table style="width: 710px; height: 100%;">
                        <tr>
                            <td width="10">&nbsp;</td>
                            <td valign="top">
                                <table style="width: 100%;">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:deleteValue();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="5">&nbsp;</td>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="5">&nbsp;</td>
                                                    <td align="center">
                                                        <table>
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:selectPart();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226")%><%--확인--%></a></td>
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
								<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
									<tr>
										<td width="150px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01595") %><%--부품유형--%></td>
										<td width="*" colspan="3" class="tdwhiteL0">
										<input name="partType" type="radio" class="Checkbox" value="A" <%=("A".equals(partType))?"checked":"" %>><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%>&nbsp;&nbsp;
										<input name="partType" type="radio" class="Checkbox" value="P" <%=("P".equals(partType))?"checked":"" %> ><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%>&nbsp;&nbsp;
										<input name="partType" type="radio" class="Checkbox" value="D" <%=("D".equals(partType))?"checked":"" %> >Die No&nbsp;&nbsp; 
										<input name="partType" type="radio" class="Checkbox" value="M" <%=("M".equals(partType))?"checked":"" %> ><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%>
										</td>
									</tr>
									<tr>
										<td width="150px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>
										&nbsp;<input style="line-height:10px" type=button value='선택Test용' onclick='javascript:helpTest();' id='innerbutton'></td>
										<td width="*" class="tdwhiteL"><input type="text" id="number" name="number" value="<%=number %>" class="txt_field" style="width: 65%" >
											<input type="text" id="numbertemp" name="numbertemp" class="txt_fieldRO" readonly style="width: 65%; display: none;"> <a href=""
											onClick="javascript:inputPartNumberPopup('number');"><img src="/plm/portal/images/icon_5.png" border="0"></a> <a href="javascript:;" onClick="javascript:clearPartNumber();"><img
												src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
										<td width="150px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
										<td width="*" class="tdwhiteL0"><input type="text" id="name" name="name" value="<%=name %>" class="txt_field" style="width: 100%" ></td>
									</tr>
								</table>
								<!-- 
								<table style="width: 500px;">
									<tr>
										<td class="space5"></td>
									</tr>
								</table> 
								 -->
								<!-- EJS TreeGrid Start -->
								<div class="content-main">
									<div class="container-fluid">
										<div class="row-fluid">
											<div style="WIDTH: 100%; HEIGHT: 100%">
												<bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/bom/SearchPartPopupGridLayout.jsp" Layout_Param_Multi="<%=multi %>" Data_Url="/plm/jsp/common/searchGridData.jsp" Data_Method="POST"
													Data_Param_Result="<%=tgData %>" Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_DocMultiApp_List"></bdo>
											</div>
										</div>
									</div>
								</div> <!-- EJS TreeGrid End -->
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 
			<tr>
				<td height="28" valign="bottom">
					<table style="width: 470px;" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td style="width: 10px;">&nbsp;</td>
							<td style="height: 28px;"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" style="width: 510px; height: 24px" frameborder="0" marginwidth="0" marginheight="0"
									scrolling="no"></iframe></td>
						</tr>
					</table>
				</td>
			</tr>
			 -->
		</table>
	</form>
</body>
</html>
