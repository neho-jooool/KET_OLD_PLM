<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.net.URLDecoder,java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostReportCtl"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
	String Ename = StringUtil.checkNull((String)session.getAttribute("Ename"));
	String file_1 = "-";
	String report_pk 			= (String)request.getParameter("report_pk");
	String pjt_no    			= "";
	String part_name    		= "";
	String ket_cost  			= "";
	String actual_cost_sum_1  	= "";
	String earn_rate_sum_1  	= "";
	String note_3  	= "";
	String dr_step  = "";
	String pk_crp  	= "";
	String pro_1  	= "";
	String case_1_note = "";
	Hashtable condition = null;
    if(!"".equals(report_pk) && report_pk != null){
    	condition = new Hashtable();
    	condition.put("report_pk", report_pk);
    }else{
    	report_pk = "";
    }

    CostReportCtl reportCtl = new CostReportCtl();

    ArrayList list = new ArrayList();
	if(condition != null){
    	list = reportCtl.CostReportTempSearch(condition);
	}
    Hashtable costHash = null;
    if( list != null && list.size() > 0 ){
    	costHash = (Hashtable)list.get(0);
    	pjt_no 				= (String)costHash.get("pjt_no");
    	part_name 			= (String)costHash.get("part_name");
    	ket_cost 			= (String)costHash.get("ket_cost");
    	actual_cost_sum_1 	= (String)costHash.get("actual_cost_sum_1");
    	earn_rate_sum_1 	= (String)costHash.get("earn_rate_sum_1");
    	note_3 				= (String)costHash.get("note_3");
    	dr_step 		    = (String)costHash.get("dr_step");
    	pk_crp 		        = (String)costHash.get("pk_crp");
    	file_1              = (String)costHash.get("file_1");
    	pro_1               = (String)costHash.get("pro_1");
    	case_1_note         = (String)costHash.get("case_1_note");
    }
%>
<html>
<head>

<title>개발원가 결과보고</title>
<style type="text/css">
<!--
BODY{
scrollbar-face-color: #CBDADF;
    scrollbar-highlight-color: #FFFFFF;
    scrollbar-3dlight-color: #E6E6E6;
    scrollbar-shadow-color: #E6E6E6;
    scrollbar-darkshadow-color: #F8F8F8;
    scrollbar-track-color: #F8F8F8;
    scrollbar-arrow-color: #E7EEF7;

    margin-top: 0px;
    margin-left: 0px;
}
-->
</style></head>
<link type="text/css" rel="stylesheet" href="/plm/jsp/cost/css/date_picker.css">
<script language="javascript" src="/plm/jsp/cost/js/date_picker.js"></script>
<script language="javascript">
 DP_InitPicker();
</script>
<style type="text/css">
BODY
A:link { text-decoration:none; color:#8A8A8A}
A:visited { text-decoration:underline; color:#5E5E5E}
A:active { text-decoration:none; color:#5E5E5E}
A:hover { text-decoration:underline; color:#8A8A8A}

A.txt:link { text-decoration:none; color:#3F6FA3}
A.txt:visited { text-decoration:none; color:#3F6FA3}
A.txt:active { text-decoration:none; color:#3F6FA3}
A.txt:hover { text-decoration:none; color:#8A8A8A}

.redBorder {
border-top: 2px solid red;
border-left: 2px solid red;
border-right: 2px solid red;
}

.redBorder_left {
border-left: 2px solid red;
}

.redBorder_right {
border-right: 2px solid red;
}

.redBorder_left_bottom {
border-left: 2px solid red;
border-bottom: 2px solid red;
}

.redBorder_right_bottom {
border-right: 2px solid red;
border-bottom: 2px solid red;
}
.style1 {font-size: 12px;text-align:center}
.style2 {font-size: 12px; font-weight: bold;color:#FF9900; text-align:left}
.style3 {font-size: 12px; font-weight: bold;color:#4F4F4F;}
.style4 {font-size: 12px; background-color:#D2D2D2; text-align: center; color:#FFFFFF}
.style5 {font-size: 12px; background-color:#D2D2D2; text-align: center; font-weight: bold; color:#333333}
.style6 {font-size: 12px;font-weight: bold; text-align:right;color:#3F6FA3}
.style7 {font-size: 11px}
.style8 {font-size: 12px;text-align:right}
.style9 {font-size: 12px;text-align:left}
.style10 {font-size: 24px; font-weight: bold;color:#4F4F4F;}
.style11 {font-size: 12px;text-align:right; font-weight: bold;color:#FF0033}
.style12 {font-size: 12px; background-color:#FFFFFF; text-align: center; font-weight: bold; color:#333333}
.style13 {font-size: 12px;font-weight: bold; text-align:right; color:#0000FF}
.style15 {font-size: 14px;font-weight:bold;text-align:center; color:#66A014}
.style16 {font-size: 12px;text-align:center; color:#8A8A8A}
.style19 {font-size: 11px; background-color:#ffff80; text-align:center; font-weight: bold; color:#333333}
.line_name {font-size: 4px; color:#EFEFEF; text-align:center}
#bg_color {background:#E6E6E6}
#not_Data {background:#FFDFDF}
#font_co {color:#CCCCCC}
-->
</style>
<script language="javascript">
function keyNumericDotPoint(){//숫자,-,. 만 입력가능
    if((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode == 45)    || (event.keyCode == 46)){
        event.returnValue = true;
    }
    else{
        event.returnValue = false;
    }
}

function DB_call(){

    <%if(!"".equals(report_pk) && report_pk != null){%>
    document.forms[0].cmd.value = "modify_create_temp_electric";
    <%}else{%>
    document.forms[0].cmd.value = "report_create_temp_electric";
    <%}%>
    document.forms[0].action = "/plm/servlet/e3ps/CostReportServlet";
    document.forms[0].method = "post";
    document.forms[0].submit();
    alert("저장되었습니다.");
}

function file_call1(){
	 var file_name = '<%=file_1%>';
	     file_name = file_name.replace("Uploadcost_file보고서","");
	     file_name = escape(encodeURIComponent(file_name));
	     var url = "/plm/jsp/cost/common/file_download.jsp?file_name="+file_name+"&page_name=work";
	     window.open(url);
}

function filecheck_file(file){
    var report_pk ='<%=report_pk%>';
    if(report_pk == 'null' || report_pk == null || report_pk == ""){
    	alert("보고서를 등록 한 후 파일첨부 가능합니다.");
    	return;
    }
    var file_2_name			= "";
    var file_3_name			= "";
    var file_4_name			= "";
    var file_5_name			= "";
    var file_6_name			= "";

    popUpOpen("/plm/jsp/cost/common/file_add.jsp?file_type="+file+"&report_pk="+report_pk+"&file_2_name="+file_2_name+"&file_3_name="+file_3_name+"&file_4_name="+file_4_name+"&file_5_name="+file_5_name+"&file_6_name="+file_6_name+"&page_name=report", "file_pop", 470, 120);

}

/* 일반팝업을 중앙에 위치도록 할때  */
function popUpOpen(url, title, width, height)
{
    if (title == '') title = 'Popup_Open';
        if (width == '') width = 540;
        if (height == '') height = 500;
        var left = "";
        var top = "";

    //화면 가운데로 배치
            var dim = new Array(2);

    dim = CenterWindow(height,width);
    top = dim[0];
    left = dim[1];

    var toolbar = 'no';
    var menubar = 'no';
    var status = 'no';
    var scrollbars = 'no';
    var resizable = 'no';
    var code_search = window.open(url, title, 'left='+left+', top='+top+',width='+width+',height='+height+', toolbar='+toolbar+', menubar='+menubar+', status='+status+', scrollbars='+scrollbars+', resizable='+resizable);
    code_search.focus();
    return code_search;
}

function CenterWindow(height,width)
{
    var outx = screen.height;
    var outy = screen.width;
    var x = (outx - height)/2;
    var y = (outy - width)/2;
    dim = new Array(2);
    dim[0] = x;
    dim[1] = y;

    return  dim;
}

function show_file(file_path,file_type){
	window.location.reload();
}

function back_call(){
	window.location.href ="/plm/jsp/cost/index.html?select_name="+'';
}
</script>
<body>
<Form method="post" name="part_1" >
<input type="hidden" name="select_val" value="1">
<input type="hidden" name="cmd" value="">
<input type="hidden" name="dev_step_val" value="개발검토">
<input type="hidden" name="report_pk" value="<%=report_pk%>">
<input type="hidden" name="pk_crp" value="<%=pk_crp%>">
<input type="hidden" name="Ename" value="<%=Ename%>">
<input type="hidden" name="f_name" value="<%=Ename%>">
<input type="hidden" name="division" value="<%=2%>">
<input type="hidden" name="team" value="<%=90%>">
	<table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
	        <td>
	            <table width="600" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
	                <tr>
	                    <td align="left" valign="bottom">
	                    <img src="/plm/jsp/cost/acc_img/tap_report_1_big.gif" border="0"></td>
	                    <td align="right" valign="middle"><img src="/plm/jsp/cost/acc_img/btn_back.gif" border="0" onClick="back_call();" style="cursor:pointer;"/></td>
	                </tr>
	                <tr>
	                    <td bgcolor="#00455d" height="3"></td>
	                    <td bgcolor="#00455d" height="3"></td>
	                </tr>
	            </table>
	        </td>
	    </tr>
	    <tr>
		    <td height="30">
			    <table width="600" border="0" cellpadding="0" align="center" cellspacing="1" bgcolor="#333333">
	             	<tr>
						<td class=style5 height=21 width=361>ProjectNo</td>
						<td class=style1 bgColor=#ffffff height=21 width=5><input class=style1 size=10 name=pjt_no value="<%=pjt_no%>"></td>
						<td class=style5 height=21 width=70>검토단계</td>
						<td class=style1 bgColor=#ffffff height=21 width=13 colSpan=3>
							<p align=left>
								<input class=style1 style="HEIGHT: 21px; WIDTH: 83px" size=83 name=dr_step value="<%=dr_step%>"> </p>
						</td>
					</tr>
					<tr>
						<td class=style5 height=21 width=361>구분</td>
						<td class=style1 bgColor=#ffffff height=21 width=5><input class=style1 size=10 name=case_1_note value="<%=case_1_note%>"></td>
						<td class=style5 height=21 width=70>생산처 구분</td>
						<td class=style1 bgColor=#ffffff height=21 width=13 colSpan=3>
							<p align=left>
								<input class=style1 style="HEIGHT: 21px; WIDTH: 83px" size=83 name=pro_1 value="<%=pro_1%>"> </p>
						</td>
					</tr>
					<tr>
						<td class=style5 height=21 width=306>PartName</td>
						<td class=style1 bgColor=#ffffff height=21 width=287 colSpan=5>
							<input class=style1 style="HEIGHT: 21px; WIDTH: 487px" size=1 align=left name=part_name value="<%=part_name%>">
						</td>
					</tr>
					<tr>
					<td class=style5 height=21 width=230>판매목표가(원)</td>
					<td class=style1 bgColor=#ffffff height=21 width=23>
						<input class=style1 size=10 name=ket_cost value="<%=ket_cost%>" onKeypress='keyNumericDotPoint();'>
					</td>
					<td class=style5 height=21 width=91>총원가(원)</td>
					<td class=style1 bgColor=#ffffff height=21 width=12>
						<input class=style1 size=10 name=actual_cost_sum_1 value="<%=actual_cost_sum_1%>" onKeypress='keyNumericDotPoint();'>
					</td>
					<td class=style5 height=21 width=108>수익률(%)</td>
					<td class=style1 bgColor=#ffffff height=21 width=129>
						<p align=left>
							<input class=style1 style="HEIGHT: 21px; WIDTH: 80px" size=1 name=earn_rate_sum_1 value="<%=earn_rate_sum_1%>"  onKeypress='keyNumericDotPoint();'> </p>
					</td>
					</tr>
					<tr>
					<td class=style5 height=21 width=230>주요 Issue<br>사항
					</td>
					<td bgColor=#ffffff width=324 colSpan=5>
						<textarea class=style9 style="HEIGHT: 109px; WIDTH: 482px" rows=7 cols=1 name=note_3 ><%=note_3%></textarea>
					</td>
					</tr>
					<tr>
					<td class=style5 height=21 width=230>첨부파일</td>
					<td bgColor=#ffffff  align="left" valign="bottom" height=21 width=324 colSpan=5>
						<img id="file_1_img" src="/plm/jsp/cost/images/costWork/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file('file_1')"style="cursor:pointer;"/>
						<%if(!file_1.equals("-") && !file_1.equals("") ){ %>&nbsp;&nbsp;<font color="#0000ff" size="+2">※첨부된 보고서파일(<img src="/plm/jsp/cost/acc_img/file.png" width="16" height="16" border="0" onClick="file_call1();" style="cursor:pointer;"/>)</font><%} %>
					</td>
					</tr>
	            </table>
	        </td>
        </tr>

        <tr>
	        <td>
	            <table width="600" height="20" border="0" align="center" cellpadding="0" cellspacing="0">
	                <tr>
	                    <td align="right" valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_Save.gif" border="0" onClick="DB_call();" style="cursor:pointer;"></td>
	                </tr>
	            </table>
	        </td>
	    </tr>
	</table>
	</Form>
</body>
</html>