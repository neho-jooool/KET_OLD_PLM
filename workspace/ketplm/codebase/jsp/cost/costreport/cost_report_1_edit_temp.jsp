<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.net.URLDecoder,java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostReportCtl"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
    String Ename    = StringUtil.checkNull((String)session.getAttribute("Ename"));
    String Dname    = StringUtil.checkNull((String)session.getAttribute("Dname"));
    String emp_no   = StringUtil.checkNull((String)session.getAttribute("emp_no"));
    String dept_no  = StringUtil.checkNull((String)session.getAttribute("dept_no"));
    String K_pass   = StringUtil.checkNull((String)session.getAttribute("K_pass"));
    String position = StringUtil.checkNull((String)session.getAttribute("position"));
    String login_id = StringUtil.checkNull((String)session.getAttribute("login_id"));//권한 관련 id
    String cost_id  = StringUtil.checkNull((String)session.getAttribute("cost_id"));//user id
    String group_m  = StringUtil.checkNull((String)session.getAttribute("group_m"));
    if(group_m.equals("G")){
        position = "그룹장";
    }
    String visitor = StringUtil.checkNull(request.getParameter("visitor"));
    String report_pk = (String)request.getParameter("report_pk");
    String pk_cr_group      = "";
    String cost_report_add  = "";
    String table_row        = "";
    String step_no          = "";
    String rev_no           = "";
    String user_case_count  = "";
    String dev_step         = "";
    String w_name           = "";
    String f_day            = "";
    String Gr_day           = "";
    String Gr_name          = "";
    String Leader_day       = "";
    String p_leader_day     = "";
    String Leader_name      = "";
    String p_leader_name    = "";
    String r_owner_day      = "";
    String r_pre_day        = "";
    String note_1           = "";
    String note_2           = "";
    String note_3           = "";
    String note_4           = "";

    String team             = "";   //개발담당팀
    String f_name           = "";//개발담당자
    String a_name           = ""; //영업담당자
    String customer_f       = ""; //고객사
    String pjt_name         = "";    // 제품명
    String pjt_no           = "";     //프로젝트 번호
    String car_type         = "";   //적용차종
    String app_part         = "";   //적용부위
    String request_txt      = ""; //검토목적
    String report_dest      = ""; //물류흐름
    String case_to_note_1   = ""; //신규투자비1안메모
    String case_to_note_2   = "";//신규투자비2안메모
    String case_to_note_3   = "";//신규투자비3안메모
    String mold_count       = "";     //mold수량
    String press_count      = "";   //press수량
    String line_count       = "";    //조립설비 수량
    String pack_count       = "";    //기타 투자비 수량
    String repack_count     = "";  //포장용 투자비
    String mold_total_1     = "";  //mold 비 1안 (천원단위)
    String mold_total_2     = "";  //mold 비 2안 (천원단위)
    String mold_total_3     = ""; //mold 비 3안 (천원단위)
    String press_total_1    = ""; // press 비 1안(천원단위)
    String press_total_2    = ""; // press 비 2안(천원단위)
    String press_total_3    = ""; // press 비 3안(천원단위)
    String line_total_1     = ""; // 조립설비비1안 (천원단위)
    String line_total_2     = "";// 조립설비비2안 (천원단위)
    String line_total_3     = ""; // 조립설비비3안 (천원단위)
    String pack_total_1     = ""; // 기타 투자비(천원단위)
    String pack_total_2     = "";// 기타 투자비(천원단위)
    String pack_total_3     = ""; // 기타 투자비(천원단위)
    String repack_total_1   = "";// 포장용 투자비(천원단위)
    String repack_total_2   = "";// 포장용 투자비(천원단위)
    String repack_total_3   = "";// 포장용 투자비(천원단위)
    String total_1          = "";
    String total_2          = "";
    String total_3          = "";
    String m_depr_stan      = "";      // 'MOLD상각기준'
    String p_depr_stan      = "";     // 'press상각기준'
    String l_depr_stan      = "";     // '조립설비상각기준'
    String pack_depr_stan       = "";  // '기타투자비 상각기준'
    String repack_depr_stan     = "";// '포장용 투자비 상각기준'
    //개발배경 및 투자비 현황 end
    //////////////////////////////////////////////////
    //예상 손익 및 환율 원재료 가격 기준 start
    String su_year_1            = "";    // 기획수량-1년차
    String su_year_2            = "";    // 기획수량-2년차
    String su_year_3            = "";   // 기획수량-3년차
    String su_year_4            = "";   // 기획수량-4년차
    String su_year_5            = "";    // 기획수량-5년차
    String su_year_6            = "";   // 기획수량-6년차
    String su_year_7            = "";    // 기획수량-7년차
    String su_year_8            = "";    // 기획수량-8년차
    String avg_su               = ""; // 기획수량 합계
    String su_stan_day          = "";  //수지 재료단가
    String lme_cu               = "";       //비철시세
    String u_ex_rate            = "";   //달러기준가
    String total_sales_1        = "";  //매출액1년차
    String total_sales_2        = "";  //매출액2년차
    String total_sales_3        = ""; //매출액3년차
    String total_sales_4        = ""; //매출액4년차
    String total_sales_5        = ""; //매출액5년차
    String total_sales_6        = ""; //매출액6년차
    String total_sales_7        = ""; //매출액7년차
    String total_sales_8        = "";//매출액8년차
    String sum_sales            = "";
    String profit_1             = ""; //영업이익-1년차
    String profit_2             = ""; //영업이익-2년차
    String profit_3             = ""; //영업이익-3년차
    String profit_4             = ""; //영업이익-4년차
    String profit_5             = ""; //영업이익-5년차
    String profit_6             = ""; //영업이익-6년차
    String profit_7             = ""; //영업이익-7년차
    String profit_8             = ""; //영업이익-8년차
    String sum_profit           = "";
    String sum_per_profit       = "";
    String per_profit_1         = ""; //영업이익률 1년차
    String per_profit_2         = "";//영업이익률 2년차
    String per_profit_3         = "";//영업이익률 3년차
    String per_profit_4         = "";//영업이익률 4년차
    String per_profit_5         = "";//영업이익률 5년차
    String per_profit_6         = "";//영업이익률 6년차
    String per_profit_7         = "";//영업이익률 7년차
    String per_profit_8         = "";//영업이익률 8년차
    String pack_type            = ""; //포장유형
    String pro_1                = "";     //생산처 구분
    String eff_value            = "";// 생산 효율
    ////////////////////////////////////////////////-
    //예상 손익 및 환율 원재료 가격 기준 end//////-
    //////////////////////////////////////////////-
    ////////////////////////////////////////////////-
    // 원가계산 결과start //////////////////////////-
    String part_name            = "";  //제품명
    String pro_usage            = "";  //적용 u/s
    String ket_cost             = ""; //판매 목표가
    String target_cost          = "";  //목표 수익률
    String actual_cost_1        = ""; // 1안 총원가
    String earn_rate_1          = ""; // 1안수익율
    String actual_cost_sum_1    = ""; // 1안 총원가 합계
    String earn_rate_sum_1      = "";   // 1안수익율 합계
    String actual_cost_2        = ""; // 2안 총원가
    String earn_rate_2          = ""; // 2안수익율
    String actual_cost_sum_2    = ""; // 2안 총원가 합계
    String earn_rate_sum_2      = "";   // 2안수익율 합계
    String actual_cost_3        = ""; // 3안 총원가
    String earn_rate_3          = ""; // 3안수익율
    String actual_cost_sum_3    = "";// 3안 총원가 합계
    String earn_rate_sum_3      = "";   // 3안수익율 합계
    String sum_ket_cost         = "";// 판매목표가 합계
    String tocost_year           = "";// 투자비 회수기간
    String select_cost           = "";// 최종원가 안
    String case_1_note           = "";// 1안 메모
    String case_2_note           = "";// 2안 메모
    String case_3_note           = "";// 3안 메모

    String file_1 = "-";
    String file_2 = "-";
    String file_3 = "-";
    String file_4 = "-";
    String file_5 = "-";
    String file_6 = "-";

    String file_2_name = "";
    String file_3_name = "";
    String file_4_name = "";
    String file_5_name = "";
    String file_6_name = "";
    String pk_crp = "";
    String pk_pid = "";
    String dr_step = "";


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
    table_row = "1";
    if( list != null && list.size() > 0 ){
        table_row = Integer.toString(list.size());
        for(int inx = 0 ; inx < 1; inx++){//원가계산결과 외에 다른 항목들은 for문을 돌릴필요가 없으므로 1번만 돌림
            costHash = (Hashtable)list.get(inx);
            team = (String)costHash.get("team");
            f_name = (String)costHash.get("f_name");
            a_name = (String)costHash.get("a_name");
            customer_f = (String)costHash.get("customer_f");
            pjt_name = (String)costHash.get("pjt_name");
            pjt_no = (String)costHash.get("pjt_no");
            car_type = (String)costHash.get("car_type");
            app_part = (String)costHash.get("app_part");
            request_txt = (String)costHash.get("request_txt");
            report_dest = (String)costHash.get("report_dest");
            case_to_note_1 = (String)costHash.get("case_to_note_1");
            case_to_note_2 = (String)costHash.get("case_to_note_2");
            case_to_note_3 = (String)costHash.get("case_to_note_3");
            mold_count = (String)costHash.get("mold_count");
            press_count = (String)costHash.get("press_count");
            line_count = (String)costHash.get("line_count");
            pack_count = (String)costHash.get("pack_count");
            repack_count = (String)costHash.get("repack_count");
            mold_total_1 = (String)costHash.get("mold_total_1");
            mold_total_2 = (String)costHash.get("mold_total_2");
            mold_total_3 = (String)costHash.get("mold_total_3");
            press_total_1 = (String)costHash.get("press_total_1");
            press_total_2 = (String)costHash.get("press_total_2");
            press_total_3 = (String)costHash.get("press_total_3");
            line_total_1 = (String)costHash.get("line_total_1");
            line_total_2 = (String)costHash.get("line_total_2");
            line_total_3 = (String)costHash.get("line_total_3");
            pack_total_1 = (String)costHash.get("pack_total_1");
            pack_total_2 = (String)costHash.get("pack_total_2");
            pack_total_3 = (String)costHash.get("pack_total_3");
            repack_total_1 = (String)costHash.get("repack_total_1");
            repack_total_2 = (String)costHash.get("repack_total_2");
            repack_total_3 = (String)costHash.get("repack_total_3");
            total_1 = (String)costHash.get("total_1");
            if("0".equals(total_1)){
                total_1 = "";
            }
            total_2 = (String)costHash.get("total_2");
            if("0".equals(total_2)){
                total_2 = "";
            }
            total_3 = (String)costHash.get("total_3");
            if("0".equals(total_3)){
                total_3 = "";
            }
            m_depr_stan = (String)costHash.get("m_depr_stan");
            p_depr_stan = (String)costHash.get("p_depr_stan");
            l_depr_stan = (String)costHash.get("l_depr_stan");
            pack_depr_stan = (String)costHash.get("pack_depr_stan");
            repack_depr_stan = (String)costHash.get("repack_depr_stan");
            su_year_1 = (String)costHash.get("su_year_1");
            su_year_2 = (String)costHash.get("su_year_2");
            su_year_3 = (String)costHash.get("su_year_3");
            su_year_4 = (String)costHash.get("su_year_4");
            su_year_5 = (String)costHash.get("su_year_5");
            su_year_6 = (String)costHash.get("su_year_6");
            su_year_7 = (String)costHash.get("su_year_7");
            su_year_8 = (String)costHash.get("su_year_8");
            avg_su = (String)costHash.get("avg_su");
            su_stan_day = (String)costHash.get("su_stan_day");
            lme_cu = (String)costHash.get("lme_cu");
            u_ex_rate = (String)costHash.get("u_ex_rate");
            total_sales_1 = (String)costHash.get("total_sales_1");
            total_sales_2 = (String)costHash.get("total_sales_2");
            total_sales_3 = (String)costHash.get("total_sales_3");
            total_sales_4 = (String)costHash.get("total_sales_4");
            total_sales_5 = (String)costHash.get("total_sales_5");
            total_sales_6 = (String)costHash.get("total_sales_6");
            total_sales_7 = (String)costHash.get("total_sales_7");
            total_sales_8 = (String)costHash.get("total_sales_8");
            sum_sales = (String)costHash.get("sum_sales");
            profit_1 = (String)costHash.get("profit_1");
            profit_2 = (String)costHash.get("profit_2");
            profit_3 = (String)costHash.get("profit_3");
            profit_4 = (String)costHash.get("profit_4");
            profit_5 = (String)costHash.get("profit_5");
            profit_6 = (String)costHash.get("profit_6");
            profit_7 = (String)costHash.get("profit_7");
            profit_8 = (String)costHash.get("profit_8");
            sum_profit = (String)costHash.get("sum_profit");
            sum_per_profit = (String)costHash.get("sum_per_profit");
            per_profit_1 = (String)costHash.get("per_profit_1");
            per_profit_2 = (String)costHash.get("per_profit_2");
            per_profit_3 = (String)costHash.get("per_profit_3");
            per_profit_4 = (String)costHash.get("per_profit_4");
            per_profit_5 = (String)costHash.get("per_profit_5");
            per_profit_6 = (String)costHash.get("per_profit_6");
            per_profit_7 = (String)costHash.get("per_profit_7");
            per_profit_8 = (String)costHash.get("per_profit_8");
            pack_type = (String)costHash.get("pack_type");
            pro_1 = (String)costHash.get("pro_1");
            eff_value = (String)costHash.get("eff_value");
            case_1_note = (String)costHash.get("case_1_note");
            case_2_note = (String)costHash.get("case_2_note");
            case_3_note = (String)costHash.get("case_3_note");
            select_cost = (String)costHash.get("select_cost");
            tocost_year = (String)costHash.get("tocost_year");
            if("0.00".equals(tocost_year)){
                tocost_year = "";
            }
            note_1 = (String)costHash.get("note_1");
            note_2 = (String)costHash.get("note_2");
            note_3 = (String)costHash.get("note_3");
            note_4 = (String)costHash.get("note_4");
            file_1 = (String)costHash.get("file_1");
            file_2 = (String)costHash.get("file_2");
            file_3 = (String)costHash.get("file_3");
            file_4 = (String)costHash.get("file_4");
            file_5 = (String)costHash.get("file_5");
            file_6 = (String)costHash.get("file_6");

            file_2_name = (String)costHash.get("file_2_name");
            file_3_name = (String)costHash.get("file_3_name");
            file_4_name = (String)costHash.get("file_4_name");
            file_5_name = (String)costHash.get("file_5_name");
            file_6_name = (String)costHash.get("file_6_name");
            step_no = (String)costHash.get("step_no");
            dev_step = (String)costHash.get("dev_step");
            w_name   = (String)costHash.get("w_name");
            f_day   = (String)costHash.get("f_day");
            Gr_day   = (String)costHash.get("Gr_day");
            Gr_name   = (String)costHash.get("Gr_name");
            Leader_day   = (String)costHash.get("Leader_day");
            p_leader_day   = (String)costHash.get("p_leader_day");
            p_leader_name   = (String)costHash.get("p_leader_name");
            r_owner_day   = (String)costHash.get("r_owner_day");
            r_pre_day   = (String)costHash.get("r_pre_day");
            dr_step   = (String)costHash.get("dr_step");
        }
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
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
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
<script type="text/javascript">
        $(document).ready(function(){
            // 항목추가 버튼 클릭시
            $(".row_add").click(function(){
                var clickedRow = $(this).parent().parent();
                var cls = clickedRow.attr("class");
                // tr 복사해서 마지막에 추가
                var newrow = clickedRow.clone();
                newrow.find("td:eq(11)").remove();
                newrow.find("td:eq(0)").remove();
                newrow.insertAfter($("#costResult ."+cls+":last"));
                // rowspan 증가
                resizeRowspan(cls);
            });

            // 삭제버튼 클릭시
            $(".delBtn").live("click", function(){
                var clickedRow = $(this).parent().parent();

                var cur_Row = $(this).parent().parent().prevAll("tr").length;
                if(cur_Row == 2){
                    return;
                }
                var cls = clickedRow.attr("class");
                // 각 항목의 첫번째 row를 삭제한 경우 다음 row에 td 하나를 추가해 준다.

                if( clickedRow.find("td:eq(0)").attr("rowspan") ){//0번째  colspan처리하기위함
                    if( clickedRow.next().hasClass(cls) ){
                        clickedRow.next().prepend(clickedRow.find("td:eq(0)"));
                    }
                }

                if( clickedRow.find("td:eq(11)").attr("rowspan") ){//11번째  colspan처리하기위함
                    if( clickedRow.next().hasClass(cls) ){
                        clickedRow.next().prepend(clickedRow.find("td:eq(11)"));
                    }
                }

                clickedRow.remove();

                resizeRowspan(cls);
            });


            // cls : rowspan 을 조정할 class ex) item1, item2, ...
            function resizeRowspan(cls){
                var rowspan = $("."+cls).length;
                $("."+cls+":first td:eq(0)").attr("rowspan", rowspan);

                $("."+cls+":first td:eq(11)").attr("rowspan", rowspan);

                document.getElementById("note_2").rows = rowspan;
            }

        });
</script>
<script language="javascript">
function on_loads(){
    var file_2 = "<%=file_2%>";
    var file_3 = "<%=file_3%>";
    var file_4 = "<%=file_4%>";
    var file_5 = "<%=file_5%>";
    var file_6 = "<%=file_6%>";
    var src_2 = "";
    var src_3 = "";
    var src_4 = "";
    var src_5 = "";
    var src_6 = "";

    <%if(report_pk != ""){%>

        src_2 = ext_check(file_2);
        src_3 = ext_check(file_3);
        src_4 = ext_check(file_4);
        src_5 = ext_check(file_5);
        src_6 = ext_check(file_6);

        if(file_2 == "-"){
            document.getElementById("file_2_img").style.display = "none";
            document.getElementById("file_2_img_1").style.display = "block";
        }else{
            document.getElementById("file_2_img").src =src_2;
        }

        if(file_3 == "-"){
            document.getElementById("file_3_img").style.display = "none";
            document.getElementById("file_3_img_1").style.display = "block";
        }else{
            document.getElementById("file_3_img").src =src_3 ;
        }

        if(file_4 == "-"){
            document.getElementById("file_4_img").style.display = "none";
            document.getElementById("file_4_img_1").style.display = "block";
        }else{
            document.getElementById("file_4_img").src =src_4;
        }

        if(file_5 == "-"){
            document.getElementById("file_5_img").style.display = "none";
            document.getElementById("file_5_img_1").style.display = "block";
        }else{
            document.getElementById("file_5_img").src =src_5 ;
        }


        if(file_6 == "-"){
            document.getElementById("file_6_img").style.display = "none";
            document.getElementById("file_6_img_1").style.display = "block";
        }else{
            document.getElementById("file_6_img").src =src_6;
        }
    <%}else{%>
        document.getElementById("file_2_img").style.display = "none";
        document.getElementById("file_2_img_1").style.display = "none";
        document.getElementById("file_3_img").style.display = "none";
        document.getElementById("file_3_img_1").style.display = "none";
        document.getElementById("file_4_img").style.display = "none";
        document.getElementById("file_4_img_1").style.display = "none";
        document.getElementById("file_5_img").style.display = "none";
        document.getElementById("file_5_img_1").style.display = "none";
        document.getElementById("file_6_img").style.display = "none";
        document.getElementById("file_6_img_1").style.display = "none";
    <%}%>
}


function ext_check(file){
    var src = "";
    if(file.indexOf(".ppt") > -1 || file.indexOf(".PPT") > -1) {
        src = "/plm/jsp/cost/images/common/powerpoint.png";
    }

    if(file.indexOf(".xls") > -1 || file.indexOf(".XLS") > -1 || file.indexOf(".xlsx") > -1 || file.indexOf(".XLSX") > -1) {
        src = "/plm/jsp/cost/images/common/excel.png";
    }

    return src;
}

function show_file(file_path,file_type){

    var src =  "";

    src = ext_check(file_path);
    if(src != ""){
        if(file_type == "file_2"){
            document.getElementById("file_2_img").src =src;
            document.getElementById("file_2_img").style.display = "block";
            document.getElementById("file_2").value = file_path;
        }
        if(file_type == "file_3"){
            document.getElementById("file_3_img").style.display = "block";
            document.getElementById("file_3_img").src =src;
            document.getElementById("file_3").value = file_path;
        }
        if(file_type == "file_4"){
            document.getElementById("file_4_img").style.display = "block";
            document.getElementById("file_4_img").src =src;
            document.getElementById("file_4").value = file_path;
        }
        if(file_type == "file_5"){
            document.getElementById("file_5_img").style.display = "block";
            document.getElementById("file_5_img").src =src;
            document.getElementById("file_5").value = file_path;
        }
        if(file_type == "file_6"){
            document.getElementById("file_6_img").style.display = "block";
            document.getElementById("file_6_img").src =src;
            document.getElementById("file_6").value = file_path;
        }
    }else{
        if(file_type == "file_2"){
            document.getElementById("file_2_img").style.display = "none";
        }
        if(file_type == "file_3"){
            document.getElementById("file_3_img").style.display = "none";
        }
        if(file_type == "file_4"){
            document.getElementById("file_4_img").style.display = "none";
        }
        if(file_type == "file_5"){
            document.getElementById("file_5_img").style.display = "none";
        }
        if(file_type == "file_6"){
            document.getElementById("file_6_img").style.display = "none";
        }
    }
}

function file_open(file_name)
{

    var file_name = document.getElementById(file_name).value;

    file_name = file_name.replace("Upload\\cost_file\\보고서\\","");
    file_name = file_name.replace("D:Uploadcost_file보고서","");

    file_name = escape(encodeURIComponent(file_name));
    var url = "/plm/jsp/cost/common/file_download.jsp?file_name="+file_name+"&page_name=work";
    window.open(url);
}
    function print_call(){
        window.open("/plm/jsp/cost/costreport/cost_report_1_print.jsp?visitor=<%=visitor%>&pk_cr_group=<%=pk_cr_group%>&cost_report_add=<%=cost_report_add%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>", "pop_300", "width=960,height=700,scrollbars=yes,resizable=no");
    }
    function excel_call()
    {
        window.open("./cost_report_1_excel.asp?visitor=<%//=visitor%>&pk_cr_group=<%//=pk_cr_group%>&cost_report_add=<%//=cost_report_add%>&table_row=<%//=table_row%>&report_pk=<%//=report_pk%>", "pop_300", "width=960,height=700,scrollbars=yes,resizable=no");
    }
    function back_call(){
    <%if("3".equals(visitor)){%>
        this.close();
    <%}else{%>
        window.location.href ="/plm/jsp/cost/index.html?select_name="+'';
    <%}%>
    }


    /**********************************************
     요청서 보기V
    **********************************************/
    function request_call(){
        window.open("./cost_re_view_test.asp?pk_cr_group=<%//=pk_cr_group%>&rev_no=<%//=rev_no%>&data_type=main&group_case_count=<%//=user_case_count%>", "pop_999", "width=1024,height=800,scrollbars=yes,resizable=yes");
    }
    /**********************************************
    /**********************************************
    DB저장 V
    **********************************************/
    function DB_call(){

        if(!validationCheck()){
            return;
        }

        <%if(!"".equals(report_pk) && report_pk != null){%>
        document.forms[0].cmd.value = "modify_create_temp";
        <%}else{%>
        document.forms[0].cmd.value = "report_create_temp";
        <%}%>
        document.forms[0].action = "/plm/servlet/e3ps/CostReportServlet";
        document.forms[0].method = "post";
        document.forms[0].submit();
        alert("저장되었습니다.");
    }

    function validationCheck(){
        var idx = document.forms[0].department.length;
        var team = "";

        for(var i = 0; i < idx; i++){
            if(document.forms[0].department.options[i].selected == true){
                team = document.forms[0].department.options[i].value;
            }
        }

        idx = document.forms[0].select_cost.length;
        var select_val = "";
        for(var i = 0; i < idx; i++){
            if(document.forms[0].select_cost[i].checked){
                select_val = document.forms[0].select_cost[i].value;
            }
        }

        idx = document.forms[0].dev_step.length;
        var dev_step = "";
        for(var i = 0; i < idx; i++){
            if(document.forms[0].dev_step[i].checked){
                dev_step = document.forms[0].dev_step[i].value;
            }
        }


        if(team == ""){
            alert("검토부서를 선택하십시오.");
            return false;
        }

        if(document.forms[0].f_name.value == ""){
            alert("개발 담당자를 입력하세요.");
            return false;
        }

        if(document.forms[0].a_name.value == ""){
            alert("영업 담당자를 입력하세요.");
            return false;
        }

        if(dev_step == ""){
            alert("검토단계를 선택하세요.");
            return false;
        }

        if(document.forms[0].dr_step.value == ""){
            alert("DR단계를 선택하세요.");
            return false;
        }

        if(document.forms[0].pjt_no.value == ""){
            alert("Project No를 입력하세요.");
            return false;
        }

        if(document.forms[0].su_year_1.value == ""){
            alert("년차별 판매량을 입력하세요.");
            return false;
        }

        if(document.forms[0].total_sales_1.value == ""){
            alert("년차별 매출액을 입력하세요.");
            return false;
        }

        if(document.forms[0].profit_1.value == ""){
            alert("년차별 영업이익을 입력하세요.");
            return false;
        }

        if(document.forms[0].per_profit_1.value == ""){
            alert("년차별 영업이익율을 입력하세요.");
            return false;
        }

        if(select_val == ""){
            alert("선택안(1안,2안,3안)을 체크하세요.");
            return false;
        }

        var obj = document.getElementById("costResult");
        var idx_temp = 0;

        for(var i = 0; i < obj.rows.length-3; i++){

            if(document.getElementsByName("part_name")[i].value == ""){
                alert(select_val+"안 제품명을 입력하세요.");
                return false;
            }

            if(document.getElementsByName("pro_usage")[i].value == ""){
                alert(select_val+"안 적용U/S를 입력하세요.");
                return false;
            }

            if(document.getElementsByName("ket_cost")[i].value == ""){
                alert(select_val+"안 판매목표가를 입력하세요.");
                return false;
            }

            if(document.getElementsByName("target_cost")[i].value == ""){
                alert(select_val+"안 목표수익율을 입력하세요.");
                return false;
            }

            if(select_val == "1"){
                if(document.getElementsByName("actual_cost_1")[i].value == ""){
                    alert(select_val+"안 총원가를 입력하세요.");
                    return false;
                }
                if(document.getElementsByName("earn_rate_1")[i].value == ""){
                    alert(select_val+"안 수익율을 입력하세요.");
                    return false;
                }
            }
            if(select_val == "2"){
                if(document.getElementsByName("actual_cost_2")[i].value == ""){
                    alert(select_val+"안 총원가를 입력하세요.");
                    return false;
                }
                if(document.getElementsByName("earn_rate_2")[i].value == ""){
                    alert(select_val+"안 수익율을 입력하세요.");
                    return false;
                }
            }
            if(select_val == "3"){
                if(document.getElementsByName("actual_cost_3")[i].value == ""){
                    alert(select_val+"안 총원가를 입력하세요.");
                    return false;
                }
                if(document.getElementsByName("earn_rate_3")[i].value == ""){
                    alert(select_val+"안 수익율을 입력하세요.");
                    return false;
                }
            }

        }
        document.forms[0].team.value = team;
        document.forms[0].select_val.value = select_val;
        document.forms[0].dev_step_val.value = dev_step;

        return true;
    }

    function keyNumericDotPoint(){//숫자,-,. 만 입력가능
        if((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode == 45)    || (event.keyCode == 46)){
            event.returnValue = true;
        }
        else{
            event.returnValue = false;
        }
    }

    /**********************************************
    결재요청-개발자
    **********************************************/
    function pass_call_1(){
        window.open("/plm/jsp/cost/costreport/work_pass.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&step_no=<%=step_no%>&team=<%=team%>&report_pk=<%=report_pk%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>", "window_2", "width=262,height=210,scrollbars=no");
    }
    /**********************************************
     결재-팀장
    **********************************************/
    function pass_call_2(){
        window.open("/plm/jsp/cost/costreport/work_login.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&step_no=<%=step_no%>&team=<%=team%>&report_pk=<%=report_pk%>&pass_type=ok_1&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>", "window_2", "width=262,height=210,scrollbars=no");
    }
    /**********************************************
    결재-센타장
    **********************************************/
    function pass_call_3(){
        window.open("/plm/jsp/cost/costreport/work_login.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&step_no=<%=step_no%>&team=<%=team%>&report_pk=<%=report_pk%>&pass_type=ok&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>", "window_2", "width=262,height=210,scrollbars=no");
    }

    /**********************************************
    결재 - 본부장
    **********************************************/
    function pass_call_5(pass_type){
        /*var JB_day            = document.part_1.JB_day.value;
        var p_leader_day    = document.part_1.p_leader_day.value;
        var r_owner_day = document.part_1.r_owner_day.value;
        var r_pre_day       = document.part_1.r_pre_day.value;*/
        var note_4              = document.part_1.note_4.value;
        var pass_type = pass_type;
        if(pass_type == "2"){
            step_no = "5.1";
        }else if(pass_type == "3"){
            step_no = "5.2";
        }else if(pass_type == "4"){
            step_no = "6";
        }
        window.open("/plm/jsp/cost/costreport/report_approval.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&note_4="+note_4+"&step_no="+step_no+"&pass_type="+pass_type ,"window_2", "width=262,height=210,scrollbars=no");
    }

    /**********************************************
    최종배포
    **********************************************/
    function pass_call_4(){
        var JB_day          = document.part_1.JB_day.value;
        var p_leader_day    = document.part_1.p_leader_day.value;
        var r_owner_day = document.part_1.r_owner_day.value;
        var r_pre_day       = document.part_1.r_pre_day.value;
        var note_4              = document.part_1.note_4.value;
        document.part_1.action = "/plm/jsp/cost/costreport/work_finish.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&JB_day="+JB_day+"&p_leader_day="+p_leader_day+"&r_owner_day="+r_owner_day+"&r_pre_day="+r_pre_day+"&note_4="+note_4;
        document.part_1.submit();
    }

    /**********************************************
    * 등록된 파일보기
    **********************************************/
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
        var file_2_name         = document.part_1.file_2_name.value;
        var file_3_name         = document.part_1.file_3_name.value;
        var file_4_name         = document.part_1.file_4_name.value;
        var file_5_name         = document.part_1.file_5_name.value;
        var file_6_name         = document.part_1.file_6_name.value;

        file_2_name = escape(encodeURIComponent(file_2_name));
        file_3_name = escape(encodeURIComponent(file_3_name));
        file_4_name = escape(encodeURIComponent(file_4_name));
        file_5_name = escape(encodeURIComponent(file_5_name));
        file_6_name = escape(encodeURIComponent(file_6_name));

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

    function invest_call(){
        var report_pk = '<%=report_pk%>';
        if(report_pk == null || report_pk == ""){
            alert("보고서 작성 후에 투자비내역을 작성할수있습니다.");
            return;
        }
        window.open("/plm/jsp/cost/costreport/cost_invest_edit.jsp?report_pk="
                + report_pk, "pop_999",
                "width=1024,height=800,scrollbars=yes,resizable=yes");
    }

    function select_fk_cost(value){
        //document.getElementById('total_sales_1_view').innerText='200';
    }
</script>
<body onload = "javascript:on_loads();">
<Form method="post" name="part_1" >
<input type="hidden" name="cmd" value="">
<input type="hidden" name="Ename" value="<%=Ename%>">
<input type="hidden" name="report_pk" value="<%=report_pk%>">
<input type="hidden" name="team" value="<%=team%>">
<input type="hidden" name="select_val" value="">
<input type="hidden" name="dev_step_val" value="">
<input name="file_2" type="hidden" value="<%=file_2%>">
<input name="file_3" type="hidden" value="<%=file_3%>">
<input name="file_4" type="hidden" value="<%=file_4%>">
<input name="file_5" type="hidden" value="<%=file_5%>">
<input name="file_6" type="hidden" value="<%=file_6%>">
<input type="hidden" name="division" value="<%=1%>">
        <table width="1024" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <table width="1022" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
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
                <td>
                    <table width="1022" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                            <td height="50" colspan="3" class="style8">

                                <table width="1022" border="0" cellpadding="0" cellspacing="1" bgcolor="#999999">
                                    <tr>
                                        <td width="1022" bgcolor="#FFFFFF">
                                            <table width="1020" border="0" align="center" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td height="5" colspan="4" class="style1">&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td width="173" height="99">
                                                        <table width="153" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td height="21" colspan="2" class="style5">검 토 부 서</td>
                                                            </tr>
                                                            <tr>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <select name="department" style="width:80">
                                                                        <%=StringUtil.SelectDeptString(team) %>                                                                        
                                                                        <!-- option value="">선택</option>
                                                                        <option value="1" <%//if("1".equals(team)){%>selected<%//} %>>커넥터연구개발1팀</option>
                                                                        <option value="11"<%//if("11".equals(team)){%>selected<%//} %>>커넥터연구개발2팀</option>
                                                                        <option value="12"<%//if("12".equals(team)){%>selected<%//} %>>커넥터연구개발3팀</option>
                                                                        <option value="13"<%//if("13".equals(team)){%>selected<%//} %>>커넥터연구개발센타</option>
                                                                        <option value="22"<%//if("22".equals(team)){%>selected<%//} %>>전장모듈연구개발1팀</option>
                                                                        <option value="23"<%//if("23".equals(team)){%>selected<%//} %>>전장모듈연구개발2팀</option>
                                                                        <option value="24"<%//if("24".equals(team)){%>selected<%//} %>>전장모듈연구개발3팀</option>
                                                                        <option value="3"<%//if("3".equals(team)){%>selected<%//} %>>연구개발3팀</option>
                                                                        <option value="4"<%//if("4".equals(team)){%>selected<%//} %>>연구개발4팀</option>
                                                                        <option value="6"<%//if("6".equals(team)){%>selected<%//} %>>연구개발6팀</option-->
                                                                        
                                                                    </select>
                                                                </td>
                                                                <td width="76" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="f_name" class="style1" size="7" value="<%=f_name %>">
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1">자동차영업팀</td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="a_name" class="style1" size="7" value="<%=a_name %>">
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        &nbsp;
                                                        <table width="153" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td height="21" class="style5">DR단계</td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="dr_step" class="style1" size="7" value="<%=dr_step %>">
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>

                                                    <td width="446" class="style10">개발 원가 결과 보고</td>
                                                    <td width="122">
                                                        <table width="110" border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="106" height="18" class="style9">
                                                                    <input type="radio" name="dev_step" value="개발검토"  <%if("개발검토".equals(dev_step)){out.println("checked"); }%>>개발검토
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td height="18" class="style9">
                                                                    <input type="radio" name="dev_step" value="개발착수"  <%if("개발착수".equals(dev_step)){out.println("checked"); }%>>개발착수/진행중
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td height="18" class="style9">
                                                                    <input type="radio" name="dev_step" value="개발완료"  <%if("개발완료".equals(dev_step)){out.println("checked"); }%>>개발완료
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td height="18" class="style9">
                                                                    <input type="radio" name="dev_step" value="설계변경"  <%if("설계변경".equals(dev_step)){out.println("checked"); }%>>설계변경
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td height="18" class="style9">
                                                                    <input type="radio" name="dev_step" value="기타"  <%if("기타".equals(dev_step)){out.println("checked"); }%>>기  타
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>

                                                    <td width="446" valign="baseline" class="style8">
                                                        <table width="382" height="58" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td width="29" rowspan="2" class="style5">결<br>재</td>
                                                                <td width="57" height="19" bgcolor="#FFFFFF" class="style1">담당</td>
                                                                <td width="57" bgcolor="#FFFFFF" class="style1">팀장</td>
                                                                <td width="57" height="19" bgcolor="#FFFFFF" class="style1">센타장</td>
                                                                <td width="58" bgcolor="#FFFFFF" class="style1">연구원장</td>
                                                                <td width="58" height="19" bgcolor="#FFFFFF" class="style1">사장</td>
                                                                <td width="58" height="19" bgcolor="#FFFFFF" class="style1">회장</td>
                                                            </tr>
                                                            <tr>
                                                                <td height="53" bgcolor="#FFFFFF" class="style1">
                                                                    <%if(step_no.equals("2")){ %>
                                                                        <img src="/plm/jsp/cost/acc_img/btn_pass_call_1.gif" border="0" onClick="pass_call_1();" style="cursor:pointer;"/>
                                                                    <%}else{
                                                                            if(!StringUtil.checkNull(f_day).equals("")){
                                                                                out.println(w_name+"<br>"+f_day.substring(0,4)+"<br>"+f_day.substring(5,10));
                                                                            }
                                                                         }
                                                                    %>
                                                                </td>
                                                                <td bgcolor="#FFFFFF" class="style1">
                                                                    <%if(step_no.equals("3") && (position.equals("팀장") || login_id.equals("admin"))){%>
                                                                    <img src="/plm/jsp/cost/acc_img/btn_pass_call_3.gif" border="0" onClick="pass_call_2();" style="cursor:pointer;"/>
                                                                    <%}else if(step_no.equals("2")){out.println("");}else{if(!StringUtil.checkNull(Gr_day).equals("")){out.println(Gr_name+"<br>"+Gr_day.substring(0,4)+"<br>"+Gr_day.substring(5,10));}} %>
                                                                </td>

                                                                <td bgcolor="#FFFFFF" class="style1">
                                                                <%if(step_no.equals("4") && (position.equals("센타장") || login_id.equals("admin"))){%>
                                                                    <img src="/plm/jsp/cost/acc_img/btn_pass_call_3.gif" border="0" onClick="pass_call_3();" style="cursor:pointer;"/>
                                                                <%}else if(step_no.equals("2") || step_no.equals("3")){out.println("");}else{if(!StringUtil.checkNull(Leader_day).equals("")){out.println(Leader_name+"<br>"+Leader_day.substring(0,4)+"<br>"+Leader_day.substring(5,10));}} %>
                                                                </td>


                                                                <!-- 연구원장 승인 -->
                                                                <td bgcolor="#FFFFFF" class="style1">
                                                                    <%if(step_no.equals("5.1") && (position.equals("연구원장") || login_id.equals("admin"))){%>
                                                                            <!-- <input name="p_leader_day" type="text" size="5" value="<%=p_leader_day%>" class="DateInput" autocomplete="off" onBlur="DP_PickerInput_blur()">-->
                                                                            <img src="/plm/jsp/cost/acc_img/btn_pass_call_1.gif" border="0" onClick="pass_call_5(3);"style="cursor:pointer;"/>
                                                                    <%}else if(step_no.equals("2") || step_no.equals("3") || step_no.equals("4") || step_no.equals("5")){
                                                                            out.println("");
                                                                         }else if(!p_leader_day.equals("")){
                                                                            out.println(p_leader_name+"<br>"+p_leader_day.substring(0,4)+"<br>"+p_leader_day.substring(5,10));
                                                                         }
                                                                    %>
                                                                </td>

                                                                <!-- 사장님 승인 -->
                                                                <td bgcolor="#FFFFFF" class="style1">
                                                                <%if(step_no.equals("5.2") && (position.equals("사장") || login_id.equals("admin"))){%>
                                                                        <!-- <input name="r_owner_day" type="text" size="5" value="<%=r_owner_day%>" class="DateInput" autocomplete="off" onBlur="DP_PickerInput_blur()">-->
                                                                        <img src="/plm/jsp/cost/acc_img/btn_pass_call_1.gif" border="0" onClick="pass_call_5(4);"style="cursor:pointer;"/>
                                                                <%}else if( step_no.equals("2") || step_no.equals("3") || step_no.equals("4") || step_no.equals("5") || step_no.equals("5.1")){
                                                                         out.println("");
                                                                     }else if(!r_owner_day.equals("")){
                                                                        out.println("이원준<br>"+r_owner_day.substring(0,4)+"<br>"+r_owner_day.substring(5,10));
                                                                       }
                                                                  %>
                                                                  </td>

                                                                  <td <% if(r_pre_day.equals("") && step_no.equals("6") && dev_step.equals("개발검토") ){ %>background="/plm/jsp/cost/acc_img/line.jpg" <% }%> bgcolor="#FFFFFF" class="style1">
                                                                    <%if(!r_pre_day.equals("")){
                                                                            out.println("이창원<br>"+r_pre_day.substring(0,4)+"<br>"+r_pre_day.substring(5,10 ));
                                                                        }else if(step_no.equals("2") || step_no.equals("3") || step_no.equals("4") || step_no.equals("5") || step_no.equals("5.1") || step_no.equals("5.2") ){
                                                                            out.println("");
                                                                          }
                                                                      %>
                                                                  </td>
                                                            </tr>


                                                        </table>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td height="197" colspan="4">
                                                        <table width="1020" border="0" cellpadding="0" cellspacing="1">
                                                            <tr>
                                                                <td height="22" colspan="8" valign="bottom" bgcolor="#FFFFFF" class="style3">1. 개발배경 및 투자비 현황<img id="file_1_img" src="/plm/jsp/cost/images/costWork/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file('file_1')"style="cursor:pointer;"/><%if(!file_1.equals("-") && !file_1.equals("") ){ %>&nbsp;&nbsp;<font color="#0000ff" size="+2">※첨부된 보고서파일(<img src="/plm/jsp/cost/acc_img/file.png" width="16" height="16" border="0" onClick="file_call1();" style="cursor:pointer;"/>)</font><%} %></td>
                                                            </tr>
                                                        </table>

                                                        <table width="1020" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td width="111" height="21" rowspan="2" class="style5">고객사명</td>
                                                                <td width="260" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1"><input name="customer_f" class="style1" size="52" value="<%=customer_f%>"></td>
                                                                <td width="91" rowspan="9" class="style5">신규 투자비<br />검 토 현 황<br /></td>
                                                                <td width="74" height="21" rowspan="2" class="style5">구분</td>
                                                                <td width="59" height="21" rowspan="2" class="style5">수량</td>
                                                                <td width="59" height="21" class="style5">1안</td>
                                                                <td width="59" height="21" class="style5">2안</td>
                                                                <td width="59" height="21" class="style5">3안</td>
                                                                <!--  <td width="59" height="21" class="style5">4안</td>
                                                                <td width="59" height="21" class="style5">5안</td>-->
                                                                <td width="59" height="21" rowspan="2" class="style5">상각기준</td>
                                                                <td width="58" rowspan="7" class="style5">투자비<br/>회수기간</td>
                                                            </tr>
                                                            <tr>
                                                                <td width="59" height="18" class="style5"><input name="case_to_note_1" class="style1" size="9" value="<%=case_to_note_1%>"></td>
                                                                <td width="59" height="18" class="style5"><input name="case_to_note_2" class="style1" size="9" value="<%=case_to_note_2%>"></td>
                                                                <td width="59" height="18" class="style5"><input name="case_to_note_3" class="style1" size="9" value="<%=case_to_note_3%>"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="111" height="21" class="style5">제품명</td>
                                                                <td width="260" height="21" bgcolor="#FFFFFF" class="style1"><input name="pjt_name" class="style1" size="52" value="<%=pjt_name%>" ></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">Mold</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_count"    type="text" class="style1" size="9" value="<%=StringUtil.checkNull(mold_count)%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(mold_total_1)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(mold_total_2)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_total_3" class="style1" size="9" value="<%=StringUtil.checkNull(mold_total_3)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="m_depr_stan" class="style1" size="9"
                                                                value="<%if(!StringUtil.checkNull(m_depr_stan).equals("")){out.println(m_depr_stan);}else if(!StringUtil.checkNullZero(mold_total_1).equals("0") ){out.println("판매량감가");} %>">
                                                                </td>
                                                            </tr>
                                                            <tr>

                                                                <td width="111" height="21" class="style5">Project No. </td>
                                                                <td width="260" height="21" bgcolor="#FFFFFF" class="style1"><input name="pjt_no" class="style1" size="52" value="<%=pjt_no%>" ></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">Press</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_count"   type="text" class="style1" size="9" value="<%=StringUtil.checkNull(press_count)%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(press_total_1)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(press_total_2)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_3" class="style1" size="9" value="<%=StringUtil.checkNull(press_total_3)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_3" class="style1" size="9" value="<%//=press_total_4%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_4" class="style1" size="9" value="<%//=press_total_5%>"></td>-->
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="p_depr_stan" class="style1" size="9"
                                                                value="<%if(!StringUtil.checkNull(p_depr_stan).equals("")){out.println(p_depr_stan);}else if(!StringUtil.checkNullZero(press_total_1).equals("0") ){out.println("판매량감가");} %>">
                                                                </td>
                                                            </tr>
                                                            <tr>

                                                                <td width="111" height="20" class="style5">적용차종</td>
                                                                <td width="260" height="20" bgcolor="#FFFFFF" class="style1"><input name="car_type" class="style1" size="52" value="<%=car_type%>"></td>
                                                                <td width="74" height="20" bgcolor="#FFFFFF" class="style1">조립설비 </td>
                                                                <td width="59" height="20" bgcolor="#FFFFFF" class="style1"><input name="line_count"    type="text" class="style1" size="9" value="<%=StringUtil.checkNull(line_count)%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(line_total_1)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(line_total_2)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="20" bgcolor="#FFFFFF" class="style1"><input name="line_total_3" class="style1" size="9" value="<%=StringUtil.checkNull(line_total_3)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_3" class="style1" size="9" value="<%//=line_total_4%>"></td>
                                                                <td width="59" height="20" bgcolor="#FFFFFF" class="style1"><input name="line_total_4" class="style1" size="9" value="<%//=line_total_5%>"></td>-->
                                                                <td width="59" height="20" bgcolor="#FFFFFF" class="style1"><input name="l_depr_stan" class="style1" size="9" value="<%if(!StringUtil.checkNull(l_depr_stan).equals("")){out.println(l_depr_stan);}else if(!StringUtil.checkNullZero(line_total_1).equals("0") ){out.println("판매량감가");} %>"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="111" height="21" class="style5">적용부위</td>
                                                                <td width="260" height="21" bgcolor="#FFFFFF" class="style1"><input name="app_part" class="style1" size="52" value="<%=app_part%>"></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">기타 투자비 </td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_count"    type="text" class="style1" size="9" value="<%=StringUtil.checkNull(pack_count)%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(pack_total_1)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(pack_total_2)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_3" class="style1" size="9" value="<%=StringUtil.checkNull(pack_total_3)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <!-- <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_3" class="style1" size="9" value="<%//=StringUtil.checkNull(pack_total_4)%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_4" class="style1" size="9" value="<%//=StringUtil.checkNull(pack_total_5)%>"></td> -->
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_depr_stan" class="style1" size="9" value="<%if(!StringUtil.checkNull(pack_depr_stan).equals("")){out.println(pack_depr_stan);}else if(!StringUtil.checkNullZero(pack_total_1).equals("0") ){out.println("판매량감가");} %>"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="111" height="21" class="style5">물류흐름</td>
                                                                <td width="260" height="21" bgcolor="#FFFFFF" class="style1"><input name="report_dest" class="style1" size="52" value="<%=report_dest%>"></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">포장용<br>투자비</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_count" type="text" class="style1" size="9" value="<%=StringUtil.checkNull(repack_count)%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(repack_total_1)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(repack_total_2)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_3" class="style1" size="9" value="<%=StringUtil.checkNull(repack_total_3)%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_3" class="style1" size="9" value="<%//=StringUtil.checkNull(repack_total_4)%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_4" class="style1" size="9" value="<%//=StringUtil.checkNull(repack_total_5)%>"></td>-->
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_depr_stan" class="style1" size="9" value="<%if(!StringUtil.checkNull(repack_depr_stan).equals("")){out.println(repack_depr_stan);}else if(!StringUtil.checkNullZero(repack_total_1).equals("0") ){out.println("판매량감가");} %>"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="111" rowspan="2" class="style5">검토목적</td>
                                                                <td width="260" rowspan="2" bgcolor="#FFFFFF" class="style1"><input name="request_txt" class="style1" size="52" value="<%=request_txt%>"></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">합 계</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%=total_1%></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%=total_2%></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%=total_3%></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"></td>-->
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                                <td width="58" height="21" class="style12"><input name="tocost_year" class="style1" size="2" value="<%=tocost_year%>" onKeypress='keyNumericDotPoint();'>년</td>
                                                            </tr>
                                                        </table>

                                                        <table width="1020" border="0" cellpadding="0" cellspacing="1">
                                                            <tr>
                                                                <td id = "gijun_td" height="21" colspan="13" valign="bottom" class="style3">2. 예상 손익 및 환율, 원재료 가격 기준 (<%=select_cost %>안기준)</td>
                                                            </tr>
                                                        </table>

                                                        <table width="1020" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td width="121" height="21" class="style5">구분</td>
                                                                <td width="45" height="21" class="style5">1년차</td>
                                                                <td width="45" height="21" class="style5">2년차</td>
                                                                <td width="45" height="21" class="style5">3년차</td>
                                                                <td width="45" height="21" class="style5">4년차</td>
                                                                <td width="45" height="21" class="style5">5년차</td>
                                                                <td width="45" height="21" class="style5">6년차</td>
                                                                <td width="45" class="style5">7년차</td>
                                                                <td width="45" class="style5">8년차</td>
                                                                <td width="45" height="21" class="style5">합계</td>
                                                                <td width="112" height="21" class="style5">수지 재료단가 </td>
                                                                <td width="108" height="21" bgcolor="#FFFFFF" class="style1"><input name="su_stan_day" class="style1" size="19" value="<%=su_stan_day%>"></td>
                                                                <td width="260" height="21" class="style5">비고</td>
                                                            </tr>
                                                            <tr>
                                                                <td width="121" height="21" rowspan="2" class="style5">판매량(천개)</td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="su_year_1" type="text" class="style8" size="1" value="<%=su_year_1%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="su_year_2" type="text" class="style8" size="1" value="<%=su_year_2%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="su_year_3" type="text" class="style8" size="1" value="<%=su_year_3%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="su_year_4" type="text" class="style8" size="1" value="<%=su_year_4%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="su_year_5" type="text" class="style8" size="1" value="<%=su_year_5%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="su_year_6" type="text" class="style8" size="1" value="<%=su_year_6%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="su_year_7" type="text" class="style8" size="1" value="<%=su_year_7%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="su_year_8" type="text" class="style8" size="1" value="<%=su_year_8%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=avg_su%></td>
                                                                <td height="21" rowspan="2" class="style5">비철 LME시세 </td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="lme_cu" type="text" class="style8" size="1" value="<%=lme_cu%>" onKeypress='keyNumericDotPoint();'><span class="style7"> [＄/Ton]</span></td>
                                                                <td rowspan="5" bgcolor="#FFFFFF" class="style1"><textarea name="note_1" cols="40" rows="9" class="style9"><%=note_1%></textarea></td>
                                                            </tr>
                                                            <tr>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="u_ex_rate" type="text" class="style8" size="1" value="<%=u_ex_rate%>" onKeypress='keyNumericDotPoint();'><span class="style7"> [￦/$]</span></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="121" height="21" class="style5">매출액(백만원)</td>
                                                                <td id = "total_sales_1_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="total_sales_1" type="text" class="style8" size="1" value="<%=total_sales_1%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td id = "total_sales_2_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="total_sales_2" type="text" class="style8" size="1" value="<%=total_sales_2%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td id = "total_sales_3_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="total_sales_3" type="text" class="style8" size="1" value="<%=total_sales_3%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td id = "total_sales_4_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="total_sales_4" type="text" class="style8" size="1" value="<%=total_sales_4%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td id = "total_sales_5_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="total_sales_5" type="text" class="style8" size="1" value="<%=total_sales_5%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td id = "total_sales_6_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="total_sales_6" type="text" class="style8" size="1" value="<%=total_sales_6%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td id = "total_sales_7_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="total_sales_7" type="text" class="style8" size="1" value="<%=total_sales_7%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td id = "total_sales_8_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="total_sales_8" type="text" class="style8" size="1" value="<%=total_sales_8%>" onKeypress='keyNumericDotPoint();'>
                                                                </td>
                                                                <td id = "sum_sales_view"  width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=sum_sales%></td>
                                                                <td height="21" class="style5">포장유형</td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_type" class="style1" size="19" value="<%=pack_type%>"></td>
                                                            </tr>

                                                            <tr>
                                                                <td width="121" height="21" class="style5">영업이익<br>(백만원)</td>
                                                                <td id = "profit_1_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="profit_1" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(profit_1)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=profit_1%>">
                                                                </td>
                                                                <td id = "profit_2_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="profit_2" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(profit_2)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=profit_2%>">
                                                                </td>
                                                                <td id = "profit_3_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="profit_3" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(profit_3)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=profit_3%>">
                                                                </td>
                                                                <td id = "profit_4_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="profit_4" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(profit_4)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=profit_4%>">
                                                                </td>
                                                                <td id = "profit_5_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="profit_5" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(profit_5)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=profit_5%>">
                                                                </td>
                                                                <td id = "profit_6_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="profit_6" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(profit_6)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=profit_6%>">
                                                                </td>
                                                                <td id = "profit_7_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="profit_7" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(profit_7)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=profit_7%>">
                                                                </td>
                                                                <td id = "profit_8_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="profit_8" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(profit_8)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=profit_8%>">
                                                                </td>


                                                                <td id = "sum_profit_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(sum_profit)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(sum_profit, "", 0, 0)%></td>
                                                                <td height="21" class="style5">생산처 구분 </td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="pro_1" class="style1" size="19" value="<%=pro_1%>"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="121" height="21" class="style5">영업이익율</td>
                                                                <td id = "per_profit_1_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="per_profit_1" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_1)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=per_profit_1%>">
                                                                </td>
                                                                <td id = "per_profit_2_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="per_profit_2" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_2)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=per_profit_2%>">
                                                                </td>
                                                                <td id = "per_profit_3_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="per_profit_3" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_3)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=per_profit_3%>">
                                                                </td>
                                                                <td id = "per_profit_4_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="per_profit_4" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_4)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=per_profit_4%>">
                                                                </td>
                                                                <td id = "per_profit_5_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="per_profit_5" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_5)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=per_profit_5%>">
                                                                </td>
                                                                <td id = "per_profit_6_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="per_profit_6" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_6)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %>size="1" value="<%=per_profit_6%>">
                                                                </td>
                                                                <td id = "per_profit_7_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="per_profit_7" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_7)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=per_profit_7%>">
                                                                </td>
                                                                <td id = "per_profit_8_view" width="45" height="21" bgcolor="#FFFFFF" class="style1">
                                                                    <input name="per_profit_8" type="text" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_8)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="1" value="<%=per_profit_8%>">
                                                                </td>
                                                                <td id = "sum_per_p_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(sum_per_profit )) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=sum_per_profit%></td>
                                                                <td height="21" class="style5">생산 효율 </td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="eff_value" class="style1" size="17" value="<%=eff_value%>" onKeypress='keyNumericDotPoint();'>%</td>
                                                            </tr>
                                                        </table>

                                                        <table width="1020" border="0" cellpadding="0" cellspacing="1">
                                                            <tr>
                                                                <td height="21" colspan="12" valign="bottom" class="style3">3. 원가계산 결과</td>
                                                                <td width="52" height="21" valign="bottom" class="style3">단위: 원</td>
                                                            </tr>
                                                        </table>

                                                        <table id="costResult" width="1020" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td width="253" <%if("".equals(report_pk)){ %> colspan="2" <%} %> rowspan="2" class="style5">제품명</td>
                                                                <td width="36" rowspan="2" class="style5">적용<br>U/S</td>
                                                                <td width="45" rowspan="2" class="style5">판매<br/>목표가</td>
                                                                <td width="45" rowspan="2" class="style5">목표<br/>수익율</td>

                                                                <td height="40" colspan="2" valign="bottom" class="style5"><span class="change"><input type="radio" name="select_cost" value="1"  <%if("1".equals(select_cost)){ %>checked<%} %> onclick="select_fk_cost(this.value);">1안<br><input name="case_1_note" class="style1" size="16" value="<%=case_1_note%>"></span></td>
                                                                <td height="40" colspan="2" valign="bottom" class="style5"><span class="change_1"><input type="radio" name="select_cost" value="2"  <%if("2".equals(select_cost)){ %>checked<%} %> onclick="select_fk_cost(this.value);">2안<br><input name="case_2_note" class="style1" size="16" value="<%=case_2_note%>"></span></td>
                                                                <td height="40" colspan="2" valign="bottom" class="style5"><span class="change_2"><input type="radio" name="select_cost" value="3"  <%if("3".equals(select_cost)){ %>checked<%} %> onclick="select_fk_cost(this.value);">3안<br><input name="case_3_note" class="style1" size="16" value="<%=case_3_note%>"></span></td>
                                                                <td width="177" rowspan="2" class="style5">비고</td>
                                                            </tr>

                                                            <tr>
                                                                <td width="46" height="21" class="style5"><span class="change_left">총원가</span></td>
                                                                <td width="40" height="21" class="style5"><span class="change_right">수익율</span></td>
                                                                <td width="46" height="21" class="style5"><span class="change_left_1">총원가</span></td>
                                                                <td width="40" height="21" class="style5"><span class="change_right_1">수익율</span></td>
                                                                <td width="46" height="21" class="style5"><span class="change_left_2">총원가</span></td>
                                                                <td width="40" height="21" class="style5"><span class="change_right_2">수익율</span></td>
                                                            </tr>
                                                            <%
                                                            for(int inx = 0 ; inx < Integer.parseInt(table_row); inx++){
                                                                if(!"".equals(report_pk) && report_pk != null){
                                                                    costHash = (Hashtable)list.get(inx);
                                                                    part_name = (String)costHash.get("part_name");
                                                                    pro_usage = (String)costHash.get("pro_usage");
                                                                    ket_cost = (String)costHash.get("ket_cost");
                                                                    target_cost = (String)costHash.get("target_cost");
                                                                    actual_cost_1 = (String)costHash.get("actual_cost_1");
                                                                    earn_rate_1 = (String)costHash.get("earn_rate_1");
                                                                    actual_cost_sum_1 = (String)costHash.get("actual_cost_sum_1");
                                                                    earn_rate_sum_1 = (String)costHash.get("earn_rate_sum_1");
                                                                    actual_cost_2 = (String)costHash.get("actual_cost_2");
                                                                    earn_rate_2 = (String)costHash.get("earn_rate_2");
                                                                    actual_cost_sum_2 = (String)costHash.get("actual_cost_sum_2");
                                                                    earn_rate_sum_2 = (String)costHash.get("earn_rate_sum_2");
                                                                    actual_cost_3 = (String)costHash.get("actual_cost_3");
                                                                    earn_rate_3 = (String)costHash.get("earn_rate_3");
                                                                    actual_cost_sum_3 = (String)costHash.get("actual_cost_sum_3");
                                                                    earn_rate_sum_3 = (String)costHash.get("earn_rate_sum_3");
                                                                    sum_ket_cost = (String)costHash.get("sum_ket_cost");
                                                                    pk_crp = (String)costHash.get("pk_crp");
                                                                    pk_pid = (String)costHash.get("pk_pid");
                                                                }

                                                            %>
                                                            <tr class="result01">
                                                                <%if(inx == 0 && "".equals(report_pk)){ %>
                                                                <td rowspan="<%=list.size()%>" height="21" bgcolor="#FFFFFF"  class="style1"><button class="row_add">추가</button></td>
                                                                <%} %>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name ="part_name" class="style1" size="10" value="<%=part_name%>"><%if("".equals(report_pk)){ %> <button class="delBtn">삭제</button> <%} %></td>
                                                                <td width="35"  bgcolor="#FFFFFF" class="style1"><input name="pro_usage" class="style1" size="2" value="<%=pro_usage%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="ket_cost" class="style1" size="2" value="<%=ket_cost%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="target_cost" class="style1" size="2" value="<%=target_cost%>" onKeypress='keyNumericDotPoint();'></td>
                                                                <td width="55"  bgcolor="#FFFFFF" class="style1">
                                                                    <span class="change_left">
                                                                        <input name="actual_cost_1" class="style1" size="2" value="<%=actual_cost_1%>" onKeypress='keyNumericDotPoint();'>
                                                                    </span>
                                                                </td>
                                                                <td width="55"  height="21" bgcolor="#FFFFFF" class="style13"><span class="change_right"><input name="earn_rate_1" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_1)) > 0){ %>class="style13"<%}else{%>class="style11"<%} %> size="2" value="<%=StringUtil.DoubleFormat(earn_rate_1, "%", 100, 1)%>"></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_1"><input name="actual_cost_2" class="style1" size="2" value="<%=actual_cost_2%>" onKeypress='keyNumericDotPoint();'></span></td>
                                                                <td width="55"  height="21" bgcolor="#FFFFFF" class="style13"><span class="change_right_1"><input name="earn_rate_2" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_2)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="2" value="<%=StringUtil.DoubleFormat(earn_rate_2, "%", 100, 1)%>"></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_2"><input name="actual_cost_3" class="style1" size="2" value="<%=actual_cost_3%>" onKeypress='keyNumericDotPoint();'></span></td>
                                                                <td width="55"  height="21" bgcolor="#FFFFFF" class="style13"><span class="change_right_2"><input name="earn_rate_3" onKeypress='keyNumericDotPoint();' <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_3)) > 0){  %>class="style13"<%}else{%>class="style11"<%} %> size="2" value="<%=StringUtil.DoubleFormat(earn_rate_3, "%", 100, 1)%>"></span></td>
                                                                <input type="hidden" name="pk_crp" value="<%=pk_crp%>">
                                                                <input type="hidden" name="pk_pid" value="<%=pk_pid%>">
                                                                <%if(inx == 0){ %>
                                                                <td rowspan="<%=list.size()%>" bgcolor="#FFFFFF" class="style1"><textarea name="note_2" cols="32" rows="<%=list.size()+1%>" class="style9"><%=note_2%></textarea></td>
                                                            </tr>
                                                                <%} %>
                                                            <%}%>
                                                            <tr>
                                                                <td height="21" <%if("".equals(report_pk)){ %> colspan="2" <%} %> class="style5">Total</td>
                                                                <td width="35" bgcolor="#FFFFFF" class="style1"></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><%=sum_ket_cost+" 원"%></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                                <td width="65" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_bottom"><%=StringUtil.DoubleFormat(actual_cost_sum_1, "원", 0, 1)%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_sum_1)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_bottom"><%=StringUtil.DoubleFormat(earn_rate_sum_1, "%", 0, 1)%></span></td>
                                                                <td width="65" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_bottom_1"><%=StringUtil.DoubleFormat(actual_cost_sum_2, "원", 0, 1)%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_sum_2)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_bottom_1"><%=StringUtil.DoubleFormat(earn_rate_sum_2, "%", 0, 1)%></span></td>
                                                                <td width="65" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_bottom_2"><%=StringUtil.DoubleFormat(actual_cost_sum_3, "원", 0, 1)%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_sum_3)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_bottom_2"><%=StringUtil.DoubleFormat(earn_rate_sum_3, "%", 0, 1)%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                            </tr>


                                                        </table>

                                                        <table width="1020" border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td height="50">

                                                                    <table border="0" cellpadding="0" cellspacing="1">
                                                                        <tr>
                                                                            <td height="21" colspan="13" valign="bottom" class="style3">4.주요 검토기준 및 ISSUE 사항 </td>
                                                                        </tr>
                                                                    </table>
                                                                    <table border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                                        <tr>
                                                                            <td bgcolor="#FFFFFF"><textarea name="note_3" cols="70" rows="7" class="style9"><%=note_3%></textarea></td>
                                                                        </tr>
                                                                    </table>

                                                                </td>
                                                                <td height="50">

                                                                    <table border="0" cellpadding="0" cellspacing="1">
                                                                        <tr>
                                                                            <td height="21" colspan="13" valign="bottom" class="style3">&nbsp;5. 경영층 지시사항 </td>
                                                                        </tr>
                                                                    </table>

                                                                    <table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                                        <tr>
                                                                            <td bgcolor="#FFFFFF" colspan="5">
                                                                                <textarea name="note_4" cols="65" rows="3" class="style9" ><%=note_4%></textarea>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td bgcolor="#FFFFFF"><input name="file_2_name" class="style1" size="12" value="<%=file_2_name%>"></td>
                                                                            <td bgcolor="#FFFFFF"><input name="file_3_name" class="style1" size="12" value="<%=file_3_name%>"></td>
                                                                            <td bgcolor="#FFFFFF"><input name="file_4_name" class="style1" size="12" value="<%=file_4_name%>"></td>
                                                                            <td bgcolor="#FFFFFF"><input name="file_5_name" class="style1" size="12" value="<%=file_5_name%>"></td>
                                                                            <td bgcolor="#FFFFFF"><input name="file_6_name" class="style1" size="12" value="<%=file_6_name%>"></td>
                                                                        </tr>

                                                                        <tr>
                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                                <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td><img id= "file_2_img" border="0" onClick="file_open('file_2');" style="cursor:pointer;"></td>
                                                                                <td><img id="file_2_img_1" src="/plm/jsp/cost/acc_img/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file('file_2')"style='cursor:pointer'></td>
                                                                                </tr>
                                                                                </table>
                                                                        </td>

                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                            <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td><img id= "file_3_img" border="0" onClick="file_open('file_3');" style="cursor:pointer;"></td>
                                                                                <td><img id="file_3_img_1" src="/plm/jsp/cost/acc_img/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file('file_3')"style='cursor:pointer'></td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>

                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                            <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td><img id= "file_4_img" border="0" onClick="file_open('file_4');" style="cursor:pointer;"></td>
                                                                                <td><img id="file_4_img_1" src="/plm/jsp/cost/acc_img/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file('file_4')"style='cursor:pointer'></td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>

                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                            <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td><img id= "file_5_img" border="0" onClick="file_open('file_5');" style="cursor:pointer;"></td>
                                                                                <td><img id="file_5_img_1" src="/plm/jsp/cost/acc_img/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file('file_5')"style='cursor:pointer'></td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>

                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                            <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td><img id= "file_6_img" border="0" onClick="file_open('file_6');" style="cursor:pointer;"></td>
                                                                                <td><img id="file_6_img_1" src="/plm/jsp/cost/acc_img/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file('file_6')"style='cursor:pointer'></td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>

                                                                    </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>

                                                    </td>
                                                </tr>

                                            </table>
                                        </td>
                                    </tr>
                            </table>

                                <table height="25"border="0" cellpadding="0" cellspacing="1">
                                    <tr>
                                        <td valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_print.gif" border="0" onClick="print_call();"style="cursor:pointer;"/></td>
                                        <td valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_Save.gif" border="0" onClick="DB_call();" style="cursor:pointer;"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

         </table>


</Form>
</body>
</html>