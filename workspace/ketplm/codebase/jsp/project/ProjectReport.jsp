<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "wt.fc.QueryResult,
                  java.util.ArrayList,
                  java.util.Hashtable,
                  java.util.Vector,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.common.code.NumberCode,
                                    e3ps.common.code.NumberCodeHelper,
                  e3ps.common.util.StringUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.web.PageControl"%>
<%
    Vector vecCarDept = NumberCodeHelper.manager.getNumberCodeLevel("CARDEVDEPT", "top");
    Vector vecElecDept = NumberCodeHelper.manager.getNumberCodeLevel("ELECDEVDEPT", "top");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>

<script language='javascript'>
<!--

  // 주관부서 셋팅
  function setDept(param) {
        var fTD = document.all.item('dept');
        var deptList = document.getElementById('dept');
        var len = deptList.length;
        var code = '';
        for(var i = len ; i > 0 ; i--){
            deptList.remove(i);
        }
        var newSpan;
        if ( param == '자동차') {
        <% for(int inx = 0; inx < vecCarDept.size(); inx++) { %>
                newSpan = document.createElement("option");
                code =  '<%=((NumberCode)vecCarDept.get(inx)).getCode()%>';
                //조직개편으로 인한 department ida2a2값과 numbercode의 부서코드값이 상충됨

                if(code == '29846'){//제품개발 4팀
                    code = '226030535';
                }

                if(code == '29847'){//제품개발 5팀
                    code = '404905079';
                }

                if(code == '29848'){//제품개발 6팀
                    code = '699088774';
                }

                if(code == '29887'){//초류관리팀
                    code = '226030717';
                }
                newSpan.innerHTML = '<%=((NumberCode)vecCarDept.get(inx)).getName()%>';
                newSpan.value=code;
                fTD.appendChild(newSpan);
        <% } %>
      }else if ( param == '전자') {
        <% for(int inx = 0; inx < vecElecDept.size(); inx++) { %>
                newSpan = document.createElement("option");
                newSpan.innerHTML = '<%=((NumberCode)vecElecDept.get(inx)).getName()%>';
                newSpan.value= '<%=((NumberCode)vecElecDept.get(inx)).getCode()%>';
                fTD.appendChild(newSpan);
            <% } %>
      }
      /*else if ( param == '자동차사업부_KETS') {
                newSpan = document.createElement("option");
                fTD.appendChild(newSpan);
      }
      */
    }

    // 고객사 검색
    function selectCustomer() {
        var fm = document.forms[0];
        var arrObj0;
        var arrObj;

        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SUBCONTRACTOR&command=select&mode=one";

        arrObj0 = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:500px; center:yes");
        if(typeof arrObj0 == "undefined" || arrObj0 == null) {
            return;
        }
        arrObj = arrObj0[0];
        fm.customerNo.value= arrObj[4];
        fm.customerName.value= arrObj[2];
    }

    //필드 값 삭제
    function deleteValue(param){
        document.getElementById(param).value = "";
    }

    //초기화
    function deleteValueAll(){
        document.forms[0].year.options[0].selected = true;
        document.forms[0].division.options[0].selected = true;
        document.forms[0].dept.options[0].selected = true;
        var deptList = document.getElementById('dept');
        var len = deptList.length;
        for(var i = len ; i > 0 ; i--){
            deptList.remove(i);
        }
        document.forms[0].customerName.value = "";
        document.forms[0].customerNo.value = "";
    }

    //제품 Project 진행현황 검색
    function searchProduct(){
        document.forms[0].cmd.value = "productSearch";
        document.forms[0].target = "productList";
        document.forms[0].action = "/plm/servlet/e3ps/ProjectReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    //제품 Project 진행현황 Excel
    function searchProductExcel(){
        document.forms[0].cmd.value = "productExcel";
        document.forms[0].target = "download";
        document.forms[0].action = "/plm/servlet/e3ps/ProjectReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    //금형 Project 진행현황 검색
    function searchMold(){
        document.forms[0].cmd.value = "moldSearch";
        document.forms[0].target = "moldList";
        document.forms[0].action = "/plm/servlet/e3ps/ProjectReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    //금형 Project 진행현황 Excel
    function searchMoldExcel(){
        document.forms[0].cmd.value = "moldExcel";
        document.forms[0].target = "download2";
        document.forms[0].action = "/plm/servlet/e3ps/ProjectReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    //진행현황 검색
    function search(){
        if ( document.forms[0].division.value == '전자' ){
            document.getElementById('mold').style.display='none';
            searchProduct();
        }else{
            document.getElementById('mold').style.display='block';
            searchProduct();
            searchMold();
        }
    }

//-->
</script>
</head>
<body onload="search();">
<form method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" value="search">
<!-- hidden end -->

<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01">프로젝트 종합현황</td>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif">프로젝트관리<img src="../../portal/images/icon_navi.gif">종합현황</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteValueAll();" class="btn_blue">초기화</a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                </td>
                  <td width="5">&nbsp;</td>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue">검색</a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
            <td width="70" class="tdblueL">연도</td>
          <td width="125" class="tdwhiteL">
            <select name="year" class="fm_jmp" style="width:95%;margin-bottom: 2px;">
            <% int currentYear = Integer.parseInt(DateUtil.getThisYear());
               for (int inx = currentYear; inx > 2008; inx--) {
            %>
              <option value="<%=inx%>"><%=inx%></option>
            <%
               }
            %>
            </select>
          </td>
          <td width="70" class="tdblueL">사업부</td>
          <td width="125" class="tdwhiteL">
            <select name="division" class="fm_jmp" style="width:95%;margin-bottom: 2px;" onchange="javascript:setDept(this.value);">
              <option value="">전체</option>
              <option value="자동차">자동차사업부</option>
              <!--<option value="자동차사업부_KETS">KETS</option>-->
              <option value="전자">전자사업부</option>
            </select>
          </td>
          <td width="70" class="tdblueL">주관부서</td>
          <td width="125" class="tdwhiteL">
            <select name="dept" class="fm_jmp" style="width:120;margin-bottom: 2px;">
              <option value="">전체</option>
            </select>
          </td>
          <td width="70" class="tdblueL">고객</td>
          <td width="125" class="tdwhiteL0">
            <input type="text" name="customerName" id="customerName" class="txt_fieldRO"  style="width:70;" readonly>
            <input type="hidden" name="customerNo" id="proteamNo" value="">
            <a href="javascript:selectCustomer();"><img src="../../portal/images/icon_5.png" border="0" ></a>
            <a href="javascript:deleteValue('customerName');deleteValue('customerNo');"><img src="../../portal/images/icon_delete.gif" border="0" ></a>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03">Project 진행현황(단위:건)</td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><a href="javascript:searchProductExcel();"><img src="../../portal/images/iocn_excel.png" border="0"></a></td>
                </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <iframe name="productList" width="780" height="200" src="" border="0" cellspacing="0" cellpadding="0" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
      <div id='mold' style="display:block">
      <table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03">금형제작 현황(단위:Set/종)</td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><a href="javascript:searchMoldExcel();"><img src="../../portal/images/iocn_excel.png" border="0"></a></td>
                </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <iframe name="moldList" width="780" height="430" border="0" cellspacing="0" cellpadding="0" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
      </div>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
<iframe name="download2" align="center" width="0" height="0" border="0"></iframe>
</form>
</body>
</html>
