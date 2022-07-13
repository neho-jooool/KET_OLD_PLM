<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
    String pk_cr_group=StringUtil.checkNull(request.getParameter("pk_cr_group"));    // 요청서 그룹번호(PLM-검토PJT)
    String rev_no=StringUtil.checkNull(request.getParameter("rev_no"));    // REVNO
    String data_type=StringUtil.checkNull(request.getParameter("data_type"));    // CASE 구분
    String group_case_count=StringUtil.checkNullZero(request.getParameter("group_case_count"));    // 요청당시추가CASE
    String table_row=StringUtil.checkNullZero(request.getParameter("table_row"));    // 구성품 갯수
    String report_pk=StringUtil.checkNull(request.getParameter("report_pk"));    // 보고서 NO
    String tab_name = "";
    String visitor = StringUtil.checkNull(request.getParameter("visitor"));
    String select_name = StringUtil.checkNull(request.getParameter("select_name"));

%>
<html>
<head>
<link rel='stylesheet' href='/plm/jsp/cost/css/wisegrid.css' type='text/css'>
<link rel='stylesheet' href='/plm/jsp/cost/css/costWork/costWork.css' type='text/css'>
<script language="javascript" src="/plm/jsp/cost/js/WiseGridUni_TAG.js"></script>
<script language="javascript" src="/plm/jsp/cost/js/WiseGrid_Property.js"></script>
<script language="javascript" src="/plm/jsp/cost/js/costWork/mmImage.js"></script>
<script language="javascript" src="/plm/jsp/cost/js/costWork/costWork.js"></script>
<script language="javascript">
/**********************************************
List Call
**********************************************/

function back_call()
{
    <%if(StringUtil.checkNull(visitor).equals("4")){%>
        this.close();
    <%}else{%>
        window.location.href ="/plm/jsp/cost/index.html?select_name="+'<%=select_name%>';

    <%}%>
}

/**********************************************
파일첨부
**********************************************/
function  filecheck_file_1()
{
    var a_pk_cr_group = "<%=pk_cr_group%>";
    popUpOpen("/plm/jsp/cost/common/file_add.jsp?file_type=file_1&pk_cr_group="+a_pk_cr_group+"&page_name=work", "file_pop", 470, 120);
}

/**********************************************
등록된 파일보기
**********************************************/
function file_call1()
{
    var file_name = GridObj3.GetCellValue("file_1",0);
        file_name = file_name.replace("/Upload/cost_file/보고서/","");
        file_name = escape(encodeURIComponent(file_name));
    var url = "/plm/jsp/cost/common/file_download.jsp?file_name="+file_name+"&page_name=work";
    //if(file_name != "" && file_name != "-"){
        window.open(url)
    //};
}

function GridCellDblClick6(strColumnKey, nRow){

    var case_type_admin = GridObj6.GetCellHiddenValue("case_type_admin", nRow);
    var group_no = GridObj6.GetCellValue("group_no", nRow);
    call_case(case_type_admin,group_no);
}

function call_case(case_type_admin,group_no) {
    gridDeleteRow();
    document.forms[0].case_type_admin_1.value = case_type_admin;
    document.forms[0].case_group_no_1.value = group_no;
    doQuery3_case(case_type_admin,group_no);
    doQuery4_case(case_type_admin,group_no);
    doQuery6_case(group_no);
    document.forms[0].case_yn.value = "Y";
}


function doQuery3_case(case_type_admin,group_no) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";
    // WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj3.SetParam("mode", "search3");
    GridObj3.SetParam("mode_detail", "work_case");
    GridObj3.SetParam("group_no", group_no);
    GridObj3.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj3.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj3.SetParam("data_type", case_type_admin);
    // WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj3.DoQuery(servlet_url);
}

/*******************************************************************************
 * 조회 4
 ******************************************************************************/
function doQuery4_case(case_type_admin,group_no) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";
    // WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj4.SetParam("mode", "search4");
    GridObj4.SetParam("mode_detail", "work_case");
    GridObj4.SetParam("group_no", group_no);
    GridObj4.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj4.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj4.SetParam("data_type", case_type_admin);

    // WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj4.DoQuery(servlet_url);
}

/*******************************************************************************
 * 조회 6
 ******************************************************************************/
function doQuery6_case(val) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";
    // WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj6.SetParam("mode", "search6");
    GridObj6.SetParam("group_no", val);
    GridObj6.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj6.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj6.SetParam("data_type", document.forms[0].data_type.value);

    // WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj6.DoQuery(servlet_url);
}
</script>
<!--
    WiseGrid 오브젝트가 생성되고 초기화된 후 발생하는
    JavaScript Event인 Initialize()를 받아 그리드의 헤더를 셋팅한다.
-->
<script language=javascript for="WiseGrid3" event="Initialize()">
    init3();
</script>
<script language=javascript for="WiseGrid4" event="Initialize()">
    init4();

</script>
<script language=javascript for="WiseGrid5" event="Initialize()">
    init5();

</script>
<script language=javascript for="WiseGrid6" event="Initialize()">
    init6();

</script>
<script language=javascript for="WiseGrid_hidden" event="Initialize()">
    init7(); //WiseGrid_hidden
</script>
<!--    서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WiseGrid3" event="EndQuery()">
    GridEndQuery3();
</script>
<script language=javascript for="WiseGrid4" event="EndQuery()">
    GridEndQuery4();
</script>
<script language=javascript for="WiseGrid6" event="EndQuery()">
    GridEndQuery6();
</script>
<script language=javascript for="WiseGrid_hidden" event="EndQuery()">
    GridEndQuery_hidden();
</script>
<!--    WiseGrid의 셀을 더블클릭했을때 발생하는 Javacript Event인 CellClick()을 받아 해당하는 작업을 진행한다.  -->
<script language=javascript for="WiseGrid6" event="CellDblClick(strColumnKey,nRow)">
    GridCellDblClick6(strColumnKey, nRow);
</script>
</head>
<body onLoad="MM_preloadImages('/plm/jsp/cost/images/costWork/work_case_btn_1.jpg','/plm/jsp/cost/images/costWork/work_case_btn_2.jpg','/plm/jsp/cost/images/costWork/work_case_btn_3.jpg')" >
<Form method="post" name="costWork">
<input name="pk_cr_group" type="hidden" size="2" value=<%=pk_cr_group%>><!-- 요청서그룹번호 -->
<input name="rev_no" type="hidden" size="2" value=<%=rev_no%>><!-- REV_NO -->
<input name="data_type" type="hidden" size="2" value=<%=data_type%>><!-- CASE 구분 -->
<input name="group_case_count" type="hidden" size="2" value=<%=group_case_count%>><!-- 요청당시추가CASE -->
<input name="report_pk" type="hidden" size="2" value=<%=report_pk%>><!-- 보고서 NO -->
<input name="table_row" type="hidden" size="2" value=<%=table_row%>><!-- 구성품 갯수 -->
<input name="cost_report_add" type="hidden" value="<%//=cost_report_add%>" ><!-- ??? -->
<input name="file_1_name" type="hidden" size="2" value=<%//=file_1_name%>><!-- ??? -->
<input name="file_2_name" type="hidden" size="2" value=<%//=file_2_name%>><!-- ??? -->
<input name="file_3_name" type="hidden" size="2" value=<%//=file_3_name%>><!-- ??? -->
<input name="case_yn" type="hidden" size="2" value=""><!-- ??? -->
<input name="case_type_admin_1" type="hidden" size="2" value=""><!-- ??? -->
<input name="case_group_no_1" type="hidden" size="2" value=""><!-- ??? -->



<script>initWiseGrid("WiseGrid_hidden", "0", "0");</script>
<table  width="1249" height="57" border="0" cellspacing="0" cellpadding="0" >
    <tr>
        <td width="1066" height="57" rowspan="2" valign="bottom"></td>
        <td width="183"height="18"align="right"valign="middle"><img src="/plm/jsp/cost/images/costWork/btn_back.gif"border="0"width="57"height="15"onClick="back_call();"style="cursor:pointer;"/></td>
    </tr>
    <tr>
        <td height="39"align="right"valign="top">&nbsp;</td>
    </tr>
</table>
<table width="1249" height="30" border="0" cellspacing="0" cellpadding="0" >
    <tr>
        <td align="right" valign="middle" ><img src="/plm/jsp/cost/images/costWork/btn_complete.gif" width="86" height="16" border="0" onClick="admin_call();" style="cursor:pointer;"/></td>
    </tr>
</table>
<table width="1249" border="0" cellspacing="0" cellpadding="0"  >
    <tr>
        <td width="47" rowspan="7" valign="top"><a href="#" onClick="user_case_call_1();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','/plm/jsp/cost/images/costWork/work_case_btn_1.jpg',1)">
<%if("main".equals(data_type)){%>
            <img src="/plm/jsp/cost/images/costWork/work_case_btn_1_2.jpg" name="Image1" width="35" height="52" border="0"><%}else{%><img src="/plm/jsp/cost/images/costWork/work_case_btn_1_1.jpg" name="Image1" width="35" height="52" border="0"><%}%></a>
<%if(Integer.parseInt(group_case_count) > 0){%>
            <a href="#" onClick="user_case_call_2();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2','','/plm/jsp/cost/images/costWork/work_case_btn_2.jpg',1)">
<%    if("case0".equals(data_type)){%>
            <img src="/plm/jsp/cost/images/costWork/work_case_btn_2_2.jpg" name="Image2" width="35" height="52" border="0">
<%    }else{%>
            <img src="/plm/jsp/cost/images/costWork/work_case_btn_2_1.jpg" name="Image2" width="35" height="52" border="0"></a>
<%    }
        }
    if(Integer.parseInt(group_case_count) > 1){%>
            <a href="#" onClick="user_case_call_3();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','/plm/jsp/cost/images/costWork/work_case_btn_3.jpg',1)">
<% if("case1".equals(data_type)){%><img src="/plm/jsp/cost/images/costWork/work_case_btn_3_2.jpg" name="Image3" width="35" height="52" border="0"><%}else{%><img src="/plm/jsp/cost/images/costWork/work_case_btn_3_1.jpg" name="Image3" width="35" height="52" border="0"><%}%></a><%}%>
        </td>
        <td width="1204" >
            <table width="1204" border="0" cellspacing="0" cellpadding="0" align="left"  >
                <tr>
                    <td width="800" >
                        <table width="800" height="43" border="0" cellspacing="1" cellpadding="0" bgcolor="#cad9df">
                            <tr>
                                <td width="800"  height="18" colspan="12" class="style3" style="padding-left:10;">■ 환율정보 (최종등록일:<%//=REER_date%>) </td>
                            </tr>
                            <tr>
                                <td width="60" height="20" class="style5">USD</td>
                                <td width="110" class="style8"><input name="USD_rate" type="text" class="style2" size="14"  id="box" onChange="javascript:changeValue(this);"></td>
                                <td width="30" bgcolor="#e2ecea">[￦/USD]</td>
                                <td width="60" class="style5">EURO</td>
                                <td width="110" class="style8"><input name="EURO_rate" type="text" class="style2" size="14"  id="box" onChange="javascript:changeValue(this);"></td>
                                <td width="30" bgcolor="#e2ecea">[￦/EUR]</td>
                                <td width="60"  class="style5">YEN</td>
                                <td width="110"class="style8"><input name="YEN_rate" type="text" class="style2" size="14"  id="box" onChange="javascript:changeValue(this);"></td>
                                <td width="30" bgcolor="#e2ecea">[￦/YEN]</td>
                                <td width="60" class="style5">CNY</td>
                                <td width="110"class="style8"><input name="CNY_rate" type="text" class="style2" size="14"  id="box" onChange="javascript:changeValue(this);"></td>
                                <td width="30" bgcolor="#e2ecea">[￦/CNY]</td>
                            </tr>
                        </table>
                    </td>
                    <td width="404" align="right" valign="top" >
                        <table width="404"align="right" valign="top" cellpadding="0" cellspacing="0" >
                            <tr>
                                <td align="right" ><img src="/plm/jsp/cost/images/costWork/btn_work_save.gif" width="132" height="17" border="0" onClick="DB_call(pageNo);"<%//권한으로표시%>style="cursor:pointer;"/><img src="/plm/jsp/cost/images/costWork/btn_work_cost_acc_call.gif" width="132" height="17" border="0" onClick="cost_acc_call();" style="cursor:pointer;"><img src="/plm/jsp/cost/images/costWork/btn_work_all_cost.gif" width="132" height="17" border="0" onClick="case_review();" style="cursor:pointer;"/></td>
                            </tr>
                                <td height="17"align="right"><img src="/plm/jsp/cost/images/costWork/btn_work_reset.gif" width="132" height="17" border="0" onClick="reset_call(pageNo);" <%//권한으로표시%> style="cursor:pointer;"/><img src="/plm/jsp/cost/images/costWork/btn_work_bogo.gif"width="132"height="17"border="0"onClick="report_add();"style="cursor:pointer;"/><img src="/plm/jsp/cost/images/costWork/btn_work_yo.gif"width="132"height="17"border="0"onClick="request_call();"style="cursor:pointer;"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td width="1204">
            <table width="1204" height="80"border="0" cellspacing="0" cellpadding="0" style="margin-top:5;" align="left" >
                <tr>
                    <td valign="top" align="left"><table width="500" border="0" cellpadding="0" cellspacing="1" bgcolor="#cad9df">
                <tr>
                    <td height="18" colspan="9" class="style3" style="padding-left:10;">■ 판매예상수량(천개/年) </td>
                </tr>
                <tr>
                    <td width="60" height="20" class="style5">구분</td>
                    <td width="55" class="style5">1년차</td>
                    <td width="55" class="style5">2년차</td>
                    <td width="55" class="style5">3년차</td>
                    <td width="55" class="style5">4년차</td>
                    <td width="55" class="style5">5년차</td>
                    <td width="55" class="style5">6년차</td>
                    <td width="55" class="style5">7년차</td>
                    <td width="55" class="style5">8년차</td>
                </tr>
                <tr>
                    <td height="20" class="style1" valign="middle">기획</td>
                    <td class="style1"><input name="su_year_1"  type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="su_year_2"  type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="su_year_3"  type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="su_year_4"  type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="su_year_5"  type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="su_year_6"  type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="su_year_7"  type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="su_year_8"  type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                </tr>
                <tr>
                    <td height="20" class="style1"><input type="checkbox"  name="p_su_chk"  value="1" onClick="javascript:changeValue(this);">공용</td>
                    <td class="style1"><input name="p_su_year_1"    type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="p_su_year_2"    type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="p_su_year_3"    type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="p_su_year_4"    type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="p_su_year_5"    type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="p_su_year_6"    type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="p_su_year_7"    type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                    <td class="style1"><input name="p_su_year_8"    type="text" class="style2"  size="7"    id="box" onChange="javascript:changeValue(this);"></td>
                </tr>
            </table>
        </td>
        <td valign="top" align="left" style="padding-left:5;">
            <table width="492" border="0" cellpadding="0" cellspacing="1" bgcolor="#cad9df">
                <tr>
                    <td height="18" colspan="8" class="style3" style="padding-left:10;">■ 기타정보</td>
                </tr>
                <tr>
                    <td width="52" height="40"rowspan="2" class="style5">차종</td>
                    <td width="39" rowspan="2" class="style5">로얄티<br />有/無</td>
                    <td height="16" colspan="2" valign="bottom" class="style5">물류흐름</td>
                    <td colspan="3" valign="bottom" class="style5">목표가</td>
                </tr>
                <tr>
                    <td width="50" class="style5" style="padding:2 0 0 0;">보관</td>
                    <td width="60" class="style5" style="padding:2 0 0 0;">납입처</td>
                    <td width="128" class="style5" style="padding:2 0 0 0;">고객사 인정예상가</td>
                    <td width="80" class="style5" style="padding:2 0 0 0;">판매목표가</td>
                    <td width="80" class="style5" style="padding:2 0 0 0;">목표수익률</td>
                </tr>
                <input name="del_list" type="hidden" class="style1" size="4">
                <input name="h_table_row" type="hidden" class="style1" size="4">
                <tr>
                    <td height="20"valign="top" class="style1"><input name="car_type"   type="text" class="style1" size="7"  id="box"></td>
                    <td valign="top" class="style1">
                        <select name="royalty" class="style1"id="box" onChange="javascript:changeValue(this);">
                            <option value="1" >有</option>
                            <option value="2" >無</option>
                        </select>
                    </td>
                    <td valign="top" class="style1"><input name="store"         type="text" class="style1" size="5"  readonly onClick="javascript:window.open('/plm/jsp/cost/costdb/distri_cost.jsp?page_name=work','pop_3','width=470,height=120,scrollbars=yes,resizable=no')" id="box" onChange="javascript:changeValue(this);"></td>
                    <td valign="top" class="style1"><input name="dest"      type="text" class="style1" size="8"  readonly id="box" onChange="javascript:changeValue(this);"></td>
                    <td valign="top" class="style1"><input name="client_cost"   type="text" class="style2" size="9"  id="box"/ onChange="javascript:changeValue(this);"></td>
                    <td valign="top" class="style1"><input name="ket_cost"  type="text" class="style2" size="8"  id="box"/ onChange="javascript:changeValue(this);"></td>
                    <td width="69" valign="top" class="style1"><input name="target_cost"    type="text" class="style2" size="8"  id="box"/ onChange="javascript:changeValue(this);">%</td>
                </tr>
            </table>
        </td>
        <td valign="top" align="left" style="padding-left:5;">
            <table width="200" border="0" cellspacing="1" cellpadding="0"  align="left" bgcolor="#cad9df">
                <tr>
                    <td height="18" colspan="3" class="style3" style="padding-left:10;">■ LME및 적용환율기준</td>
                </tr>
                <tr>
                    <td width="50" height="20" class="style5">LME</td>
                    <td width="120" height="20" class="style8"><input name="lme_ton" type="text" class="style2" size="14"  id="box" onChange="javascript:changeValue(this);"></td>
                    <td width="30" bgcolor="#e2ecea">[＄/Ton]</td>
                </tr>
                <tr>
                    <td height="40" class="style5" rowspan="2">환율</td>
                    <td height="20" class="style8"><input name="u_ex_rate" type="text" class="style2" size="14"  id="box" onChange="javascript:changeValue(this);"></td>
                    <td bgcolor="#e2ecea">[￦/USD]</td>
                </tr>
                <tr>
                    <td height="20"class="style8"><input name="y_ex_rate" type="text" class="style2" size="14"  id="box" onChange="javascript:changeValue(this);"></td>
                    <td bgcolor="#e2ecea"> [￦/YEN]</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</td>
</tr>
    <tr><td valign="bottom"><hr></td></tr>
    <tr>
        <td>
            <table id="tb_product" width="1204" height="223" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="120" height="24" valign="bottom" align="left"><img src="/plm/jsp/cost/images/costWork/tap_work_1.gif" border="0" ></td>
                    <td width="981" height="24" align="left" valign="bottom" >
<%
    for(int i=1; i <= Integer.parseInt(table_row); i++){
        if(Integer.toString(i).length() == 1){
            tab_name =  "T00"+i;
        }else if(Integer.toString(i).length()== 2){
            tab_name = "T0"+i;
        }else if(Integer.toString(i).length()== 3){
            tab_name = "T"+i;
        }
%>
                        <a href="javascript:reset_call('<%=tab_name%>')"><%=tab_name%></a>
<%
        if(i < Integer.parseInt(table_row)){
            out.println(" | ");
        }
    }
%>
                    </td>
                    <td width="103" height="24" valign="bottom" align="right"><img src="/plm/jsp/cost/images/costWork/btn_plus.jpg" border="0" onClick="seePlus('tb_product');" style="cursor:pointer;"/><img src="/plm/jsp/cost/images/costWork/btn_minus.jpg" border="0" onClick="seeMinus('tb_product');" style="cursor:pointer;"/></td>
                </tr>
                <tr>
                    <td width="1204" bgcolor="#00455d" height="3" colspan="3"></td>
                </tr>
                <tr>
                    <td width="1204" valign="top" bgcolor="#FFFFFF" colspan="3"><script>initWiseGrid("WiseGrid3", "1204", "100%");</script></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table id="tb_product_1" width="1204" height="325" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="1204" height="24" valign="bottom" align="left"><img src="/plm/jsp/cost/images/costWork/tap_work_2.gif" border="0" ></td>
                    <td width="1204" height="24" valign="bottom" align="right"><img src="/plm/jsp/cost/images/costWork/btn_plus.jpg" border="0" onClick="seePlus('tb_product_1');" style="cursor:pointer;"/><img src="/plm/jsp/cost/images/costWork/btn_minus.jpg" border="0" onClick="seeMinus('tb_product_1');" style="cursor:pointer;"/></td>
                </tr>
                <tr>
                    <td width="1204" bgcolor="#00455d" height="3" colspan="2"></td>
                </tr>
                <tr>
                    <td width="1204" valign="top" bgcolor="#FFFFFF" colspan="2"><script>initWiseGrid("WiseGrid4", "1204", "100%");</script></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td width="1204" border="0" cellspacing="0" cellpadding="0">
            <table width="1204" height="24" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                    <td width="722" height="24" valign="bottom" ><img src="/plm/jsp/cost/images/costWork/tap_work_3.gif"></td>
                    <td width="482" height="24" valign="bottom" ><img src="/plm/jsp/cost/images/costWork/tap_work_4.gif"></td>
                </tr>
            </table>
            <table width="1204" height="88" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                    <td bgcolor="#00455d" height="3"></td>
                </tr>
                <tr>
                    <td align="left" valign="top">
                        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="CCCCCC">
                            <tr>
                                <td width="720" rowspan="3" bgcolor="#FFFFFF" ><textarea name="information" cols="100" rows="5" ></textarea></td>
                                <td width="130" rowspan="3" bgcolor="F2F2F2" align="center">보고서파일첨부</td>
                                <td width="353" bgcolor="#FFFFFF" ><img src="/plm/jsp/cost/images/costWork/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file_1()"style="cursor:pointer;"/>등록된 파일보기(<img src="/plm/jsp/cost/images/costWork/file.png" border="0" onClick="file_call1();" style="cursor:pointer;"/>) </td>
                            </tr>
                            <tr>
                                <td bgcolor="#FFFFFF" ><img src="/plm/jsp/cost/images/costWork/btn_filecheck_file_1_b.gif" border="0" >등록된 파일보기(<img src="/plm/jsp/cost/images/costWork/file.png" border="0">) </td>
                            </tr>
                            <tr>
                                <td height="21" bgcolor="#FFFFFF" ><img src="/plm/jsp/cost/images/costWork/btn_filecheck_file_1_b.gif" border="0" >등록된 파일보기(<img src="/plm/jsp/cost/images/costWork/file.png" border="0">) </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <fieldset  id= 'result'><legend>간이산출 </legend>
                <table width="1193" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="1193" align="right" valign="bottom" >변경내용 : <input name="case_infor" type="text" class="style14" size="70" >&nbsp;<img src="/plm/jsp/cost/images/costWork/btn_rok_b.gif" onClick="mody_add(pageNo);" style="cursor:pointer;"></td>
                    </tr>
                    <tr>
                        <td width="1193"><script>initWiseGrid("WiseGrid5", "1193", "250");</script></td>
                    </tr>
                </table>
            </fieldset>
            <table width="802" border="0" cellspacing="0" cellpadding="0"align="left" valign="bottom">
                <tr>
                    <td width="294" height="24" align="left" valign="bottom" ><img src="/plm/jsp/cost/images/costWork/tap_work_6.gif"></td>
                    <td width="508" height="24" align="right" valign="bottom" >
                        <img src="/plm/jsp/cost/acc_img/btn_Save.gif" border="0" onClick="DB_call_case();" style="cursor:pointer;">
                        <img src="/plm/jsp/cost/images/costWork/btn_Delete.gif" border="0" onClick="doDelete3(pageNo);" style="cursor:pointer;"/>
                    </td>
                </tr>
                <tr>
                    <td bgcolor="#00455d" height="3" colspan="2"></td>
                </tr>
                <tr>
                    <td width="802" colspan="2"><script>initWiseGrid("WiseGrid6", "868", "150");</script><p></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>