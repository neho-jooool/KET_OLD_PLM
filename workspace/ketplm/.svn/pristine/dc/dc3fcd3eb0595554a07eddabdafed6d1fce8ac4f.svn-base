<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
    String pk_cr_group				=	StringUtil.checkNull(request.getParameter("pk_cr_group"));    // 요청서 그룹번호(PLM-검토PJT)
    String rev_no						=	StringUtil.checkNull(request.getParameter("rev_no"));    // REVNO
    String data_type					=	StringUtil.checkNull(request.getParameter("data_type"));    // CASE 구분
    String group_case_count	=	StringUtil.checkNullZero(request.getParameter("group_case_count"));    // 요청당시추가CASE
    String table_row 				=	StringUtil.checkNullZero(request.getParameter("table_row"));    // 구성품 갯수
    String report_pk 				= 	StringUtil.checkNull(request.getParameter("report_pk"));    // 보고서 NO
    String url_chk 					=	StringUtil.checkNull(request.getParameter("url_chk"));
    String call_page 				= 	StringUtil.checkNull(request.getParameter("call_page"));
    String pjt_no 						= 	StringUtil.checkNull(request.getParameter("pjt_no"));

/*	if("cost_work_pass".equals(call_page)){
        call_page = "/plm/jsp/cost/common/cost_work_pass.jsp";
    }*/
%>
<html>
<head>
<title>한국단자 종합 개발 시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
.style1 {font-size: 12px;font-weight: bold; color:#3D505A; text-align:center}
.style2 {font-size: 12px;text-align:left;}
.style4 {font-size: 12px;text-align:center}
#box {border:1 solid #ffffff ;padding:3 1 0 2; border-color:#CCCCCC}
.style5 {font-size: 12px; text-align: left; color: #006699; }
.style6 {color:#FF3366;}
-->
</style>
</head>
<script language = "javascript">
 /**********************************************
 URL송부여부
 **********************************************/
function consent_change(a){
    var group_case_count = '<%=group_case_count%>';

    if (document.u_login.consent[0].checked == true ){
        document.u_login.pjt_no.disabled = false ;
        document.u_login.pjt_no.style.background = "#FFFFFF";
        document.u_login.action = '<%=call_page%>'+".jsp?url_chk=ok&pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&data_type=main&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>'+"&report_pk="+'<%=report_pk%>';
    }else{
        document.u_login.pjt_no.disabled = true ;
        document.u_login.pjt_no.style.background = "#E6E6E6";
        document.u_login.action = '<%=call_page%>'+".jsp?url_chk=no&pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&data_type=main&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>'+"&report_pk="+'<%=report_pk%>';
    }
}

</script>
<body onLoad="consent_change();">
<form  method=post name="u_login" >
    <table width="240" border="0" align="center" cellspacing="0" bgcolor="#FFFFFF">
        <tr bgcolor="#CCCCCC">
            <td height="20" colspan="2" bgcolor="#E6E6E6" class="style2"><input type="radio" name="consent" value="yes" onclick= "consent_change(this.a)" checked >URL송부<input type="radio" name="consent" value="no" onclick= "consent_change(this.a)" >URL미송부</td>
        </tr>
        <tr bgcolor="#CCCCCC">
            <td width="96" height="44" bgcolor="#F3F3F3" class="style1">Project No </td>
            <td width="137" bgcolor="#F3F3F3" class="style4"><input name="pjt_no" type="text" class="style2" size="20" id="box" value="<%=pjt_no%>"></td>
        </tr>
        <tr bgcolor="#CCCCCC">
            <td height="23" colspan="2" bgcolor="#E6E6E6" class="style4"><input type="submit" name="enter" value="확인" ></td>
        </tr>
        <tr bgcolor="#CCCCCC">
            <td height="23" colspan="2" bgcolor="#FFFFFF" class="style5">*여러제품 등록시 &quot;,&quot;로 구분하여 표기<br><span class="style6">&nbsp;&nbsp;ex) 10C038,10C012,10C040</span></td>
        </tr>
    </table>
</form>
</body>
</html>