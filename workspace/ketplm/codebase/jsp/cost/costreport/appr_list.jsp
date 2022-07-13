<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.servlet.CostReportServlet"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable, java.util.Calendar"%>
<%
    String position = StringUtil.checkNull((String)session.getAttribute("position"));
    ArrayList SearchList	= (ArrayList)request.getAttribute("SearchList");
    Hashtable searchMap	= null;

    String gubun  		 =  StringUtil.checkNull((String)request.getParameter("gubun"));
    String ProductName = StringUtil.checkNull((String)request.getParameter("ProductName"));
    String ProjectNo      = StringUtil.checkNull((String)request.getParameter("ProjectNo"));
    String kyul_date1     = StringUtil.checkNull((String)request.getParameter("kyul_date1"));
    String kyul_date2     = StringUtil.checkNull((String)request.getParameter("kyul_date2"));
    String kyul_line        = StringUtil.checkNull((String)request.getParameter("kyul_line"));

    String PjtNo = "";
    String PartName = "";
    String DEV_STEP = "";
    String REQUEST_DAY = "";
    String KYUL_NAME = "";
    String KYUL_DAY = "";
    String CASE_TYPE_USER = "";
    String TABLE_ROW = "";
    String PK_CRP = "";
    String REV_NO = "";
    String REV_PK = "";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>전자결재 목록</title>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/report/appr_list.css"/>
<link type="text/css" rel="stylesheet" href="/plm/jsp/cost/css/date_picker.css">

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
<script language="javascript" src="/plm/jsp/cost/js/date_picker.js"></script>
<script language="javascript">
 DP_InitPicker();
</script>
<script language="JavaScript">
<!--
function init(){
    var form = document.forms[0];
    var gubun  		  = '<%=gubun%>';
    if(gubun == "kyul_daesang"){
        form.rdoDevYn[0].checked = true;
    }else if(gubun == "kyul_ing"){
        form.rdoDevYn[1].checked = true;
    }else if(gubun == "kyul_finish"){
        form.rdoDevYn[2].checked = true;
    }else{
        form.rdoDevYn[0].checked = true;
    }
    rdo_select();
    if(gubun == ""){
        doSearch();
    }
}
function isNull(str) {
    if(str==null||str==""){
        return true;
    }
    return false;
}

function doSearch(){
    var form = document.forms[0];
    form.cmd.value = "ApprList";
    if(form.rdoDevYn[0].checked == true){
        form.gubun.value = 'kyul_daesang';
    }else if(form.rdoDevYn[1].checked == true){
        form.gubun.value = 'kyul_ing';
    }else{
        form.gubun.value = 'kyul_finish';
    }

    form.action =  '/plm/servlet/e3ps/CostReportServlet';
    form.submit();
}

function rdo_select(){
    var form = document.forms[0];
    deleteAllSelectBox(form.kyul_line);
    addSelectBox( form.kyul_line, "선택", "", "<%=kyul_line%>" );
    if(form.rdoDevYn[0].checked == true || form.rdoDevYn[1].checked == true){
        if(form.rdoDevYn[0].checked == true){
            <%if(position.equals("본부장")){%>
                addSelectBox( form.kyul_line, "홍종범", "홍종범", "<%=kyul_line%>");

            <%}%>
            <%if(position.equals("부문장")){%>
                addSelectBox( form.kyul_line, "김동식", "김동식", "<%=kyul_line%>");

            <%}%>
            <%if(position.equals("사장")){%>
                addSelectBox( form.kyul_line, "이성범", "이성범", "<%=kyul_line%>");
            <%}%>
        }
        if(form.rdoDevYn[1].checked == true){
            <%if(position.equals("본부장")){%>
                addSelectBox( form.kyul_line, "김동식", "김동식", "<%=kyul_line%>");
                addSelectBox( form.kyul_line, "이성범", "이성범", "<%=kyul_line%>");
            <%}%>
            <%if(position.equals("부문장")){%>
                addSelectBox( form.kyul_line, "이성범", "이성범", "<%=kyul_line%>");
            <%}%>
            <%if(position.equals("사장")){%>
                addSelectBox( form.kyul_line, "이성범", "이성범", "<%=kyul_line%>");
            <%}%>
        }
        document.getElementById('kyul_td1').innerText = "상신자";
        document.getElementById('kyul_td2').innerText = "상신일";
        document.getElementById('kyul_td3').innerText = "상신자";
        document.getElementById('kyul_td4').innerText = "상신일";
    }else{
        addSelectBox( form.kyul_line, "이원준", "이원준", "<%=kyul_line%>");

        document.getElementById('kyul_td1').innerText = "결재자";
        document.getElementById('kyul_td2').innerText = "결재일";
        document.getElementById('kyul_td3').innerText = "결재자";
        document.getElementById('kyul_td4').innerText = "결재일";
    }
}

function addSelectBox(objSelectBox, addText, addValue , selectValue)
{
    var optItem = new Option();

    optItem.text     = addText;
    optItem.value    = addValue;

    if( selectValue == addValue )
    {
        optItem.selected = true;
    }

    objSelectBox.options[objSelectBox.options.length] = optItem;
}

//select box 전체 삭제
function deleteAllSelectBox(objSelectBox)
{
    for( var i = 0; i < objSelectBox.options.length; i++ )
    {
        objSelectBox.options[i].value = null;
        objSelectBox.options[i].text  = null;
    }

    objSelectBox.options.length = 0;
}
//-->
</script>

</head>
<body onload="init();">
<form name="form01" target="_self" method="post" >
<input type=hidden name=sortKey value="">
<input type=hidden name=cmd value="">
<input type=hidden name=gubun value="">
<input type=hidden name=position value='<%=position%>'>

<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
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
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/jsp/cost/images/common/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/jsp/cost/images/common/btn_bg1.gif"><a href="javascript:doSearch();" class="btn_blue">검색</a></td>
                      <td width="10"><img src="/plm/jsp/cost/images/common/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
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
          <td class="tdblueL">구분</td>
          <td width="170" class="tdwhiteL">
                    <input name="rdoDevYn" type="radio" class="Checkbox"  id="rdoDevYn" onClick="rdo_select();">
                  대상
                  <input name="rdoDevYn" type="radio" class="Checkbox"  id="rdoDevYn" onClick="rdo_select();">
                  진행중
                  <input name="rdoDevYn" type="radio" class="Checkbox"  id="rdoDevYn" onClick="rdo_select();">
                  완료
           </td>
          <td class="tdblueL">제품명</td>
          <td width="200" class="tdwhiteL0"><input type="text" name="ProductName" class="txt_field"  style="width:160" id="textfield" value = '<%=ProductName%>'></td>
          <td class="tdblueL">Project No</td>
          <td width="90" class="tdwhiteL0"><input type="text" name="ProjectNo" class="txt_field"  style="width:160" id="textfield" value = '<%=ProjectNo%>'></td>
        </tr>
        <tr>
          <td class="tdblueL" id="kyul_td1">상신자</td>
          <td width="170" class="tdwhiteL">
          <select name="kyul_line" style="width:100">
          <option selected="selected"></option>
          </select>
          </td>
          <td class="tdblueL" id="kyul_td2">상신일</td>
          <td width="200" class="tdwhiteL0">
          <input name="kyul_date1" type="text" size="5" value='<%=kyul_date1%>' class="DateInput" style="width:80" autocomplete="off" onBlur="DP_PickerInput_blur()">-
          <input name="kyul_date2" type="text" size="5" value='<%=kyul_date2%>' class="DateInput" style="width:80" autocomplete="off" onBlur="DP_PickerInput_blur()">
          </td>
          <td class="tdwhiteL0" colspan=3>&nbsp;</td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
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
          <td width="70" class="tdblueM">Project No</td>
          <td width="300" class="tdblueM">제품명</td>
          <td width="80" class="tdblueM">개발단계</td>
          <td width="140" class="tdblueM">요청일</td>
          <td width="100" class="tdblueM" id="kyul_td3">상신자</td>
          <td width="140" class="tdblueM" id="kyul_td4">상신일</td>
        </tr>

        <%
            int Rcount = 0;
            if(SearchList != null){
                Rcount = SearchList.size();
            }

            for(int i=0; i < Rcount; i++){
                searchMap = (Hashtable)SearchList.get(i);
                PjtNo = StringUtil.checkNull((String)searchMap.get("ProjectNo"));
                PartName = StringUtil.checkNull((String)searchMap.get("ProductName"));
                DEV_STEP = StringUtil.checkNull((String)searchMap.get("DEV_STEP"));
                REQUEST_DAY = StringUtil.checkNull((String)searchMap.get("REQUEST_DAY"));
                KYUL_NAME = StringUtil.checkNull((String)searchMap.get("KYUL_NAME"));
                KYUL_DAY = StringUtil.checkNull((String)searchMap.get("KYUL_DAY"));
                TABLE_ROW = StringUtil.checkNull((String)searchMap.get("TABLE_ROW"));
                PK_CRP = StringUtil.checkNull((String)searchMap.get("PK_CRP"));
                REV_NO = StringUtil.checkNull((String)searchMap.get("REV_NO"));
                CASE_TYPE_USER = StringUtil.checkNull((String)searchMap.get("CASE_TYPE_USER"));
                REV_PK = StringUtil.checkNull((String)searchMap.get("REV_PK"));
        %>
          <tr>
          <td width="70" class="tdwhiteM">
          <a href="/plm/jsp/cost/costreport/cost_report_1.jsp?pk_cr_group=<%=PjtNo%>&table_row=<%=TABLE_ROW%>&report_pk=<%=PK_CRP%>&cost_report_add=ok&&rev_no=<%=REV_NO%>&rev_pk=<%=REV_PK%>&user_case_count=<%=CASE_TYPE_USER%>" target="_blank"><%=PjtNo %></a>&nbsp;
          </td>
          <td width="300" class="tdwhiteM"><%=PartName %>&nbsp;</td>
          <td width="80" class=tdwhiteM><%=DEV_STEP %>&nbsp;</td>
          <td width="140" class="tdwhiteM"><%=REQUEST_DAY %>&nbsp;</td>
          <td width="100" class="tdwhiteM"><%=KYUL_NAME %>&nbsp;</td>
          <td width="140" class="tdwhiteM"><%=KYUL_DAY %>&nbsp;</td>
          </tr>
        <%
            }
        %>

      </table>
  </td>
  </tr>
</table>
</form>
</body>
</html>