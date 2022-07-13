<%@ page language="java" contentType="text/html; charset=utf-8"%>

<%@ page import="e3ps.cost.servlet.CostComServlet"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable, java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String login_id = StringUtil.checkNull((String)session.getAttribute("login_id"));//권한 관련 id
    String page_type    = StringUtil.checkNull(request.getParameter("page_type"));
    String pjt_no           = StringUtil.checkNull(request.getParameter("pjt_no"));
    String brows            = "";
    String s_pjt_name   = "";
    String a_pjt_name   = "";
    String search_type  = StringUtil.checkNull(request.getParameter("search_type"));
    String plm_data     = StringUtil.checkNull(request.getParameter("plm_data"));
    String NotProgerss        = StringUtil.checkNull(request.getParameter("NotProgerss"));
    String GetPlm        = StringUtil.checkNull(request.getParameter("GetPlm"));
    ArrayList SearchList    = (ArrayList)request.getAttribute("SearchList");
    Hashtable searchMap = null;
    String o_group_no       = "";
    String group_no         = "";
    String part_no          = "";
    String part_name        = "";
    String f_name           = "";
    String partSqe          = "";
    String team             = "";
    String part_note            = "";
    String target_cost      = "";
    String rev_no               = "";
    String p_rev_no         = "";
    String client_cost      = "";
    String ket_cost         = "";

    String car_type         = "";
    String su_year_1        = "";
    String su_year_2        = "";
    String su_year_3        = "";
    String su_year_4        = "";
    String su_year_5        = "";
    String su_year_6        = "";
    String su_year_7        = "";
    String su_year_8        = "";
    String customer_F       = "";
    String customer_L       = "";
    String rev_pk               = "";
    String g_sel1               = "";
    String a_name           = "";
    String drProgress       = "";
    String request_day      = "";
    String usage                = "";

    String make             = "";
    String case_count_1 = "";
    String case_count_2 = "";
    String pro_type         = "";
    String part_type        = "";
    String mix_group        = "";
    String net_1            = "";
    String net_2                = "";
    String net_3            = "";
    String meterial         = "";
    String t_size           = "";
    String w_size           = "";
    String p_size           = "";
    String plating          = "";
    String m_maker         = "";
    String m_mone          = "";
    String unitcost         = "";
    String unitcost_txt     = "";
    String grade_name     = "";
    String grade_color      = "";
    String order_con        = "";
    String h_weight         = "";
    String h_scrap          = "";
    String t_weight         = "";
    String recycle          = "";
    String die_no           = "";
    String cavity               = "";
    String m_su             = "";
    String mold_cost        = "";
    String mold_c_type    = "";
    String make_type        = "";
    String pro_1            = "";
    String ton                  = "";
    String spm              = "";
    String method_type      = "";
    String pro_level        = "";
    String pro_level_txt    = "";
    String line_su          = "";
    String sul_cost         = "";
    String plating_type     = "";
    String type_2           = "";
    String t_mone           = "";
    String type_1           = "";
    String type_cost        = "";
    String t_order          = "";
    String pack_type        = "";
    String in_pack          = "";
    String out_pack         = "";
    String store            = "";
    String dest             = "";
    String dis_cost         = "";
    String distri_cost      = "";
    String royalty          = "";
    String yazaki_ro        = "";
    String etc_cost         = "";
    String plating_cost     = "";
    String IsPlm            = "";
    String pjt_name         = "";
    int Rcount = 0;
    int plmcnt = 0;
    if(SearchList != null){
        Rcount = SearchList.size();
    }
    boolean plmCheck = true;
    if(plm_data.equals("no") ){
        if(Rcount < 1){
            s_pjt_name = "PLM System 및 기존원가DB 에서 검색된 내용이 없습니다.<br>관리자에게 문의바랍니다.(1155)";
        }else{
            plmCheck = false;
            s_pjt_name = "PLM System 에서 검색된 내용이 없어,<br>기존 원가DB에서 검색하신 Project no와 일치한 Data를 출력합니다.";
        }
    }
    for(int z=0; z < Rcount; z++){
        searchMap = (Hashtable)SearchList.get(z);
        IsPlm = StringUtil.checkNull((String)searchMap.get("IsPlm"));
        if(!IsPlm.equals("plm")){
            plmcnt++;
        }
        if(!StringUtil.checkNull((String)searchMap.get("p_rev_no")).equals("")){
            rev_no = p_rev_no;
        }
        p_rev_no = StringUtil.checkNull((String)searchMap.get("p_rev_no"));
    }

    if(plm_data.equals("ok") ){
        searchMap = (Hashtable)SearchList.get(0);
        a_pjt_name = (String)searchMap.get("pjt_name");
        s_pjt_name = "("+a_pjt_name+"의 구성품 "+plmcnt+"건이 검색되었습니다.)";
    }
    



%>
<html>
<head>
<title>한국단자 종합 개발 시스템 - 상세검색</title>
<style type="text/css">
<!--
.style1 {font-size: 12px; text-align:center;}
.style2 {font-size: 12px; text-align:left;font-weight: bold; color:#3D6A98;}
.style3 {font-size: 12px; text-align:right;}
.style4 {font-size: 12px; text-align:left; color:#4F4F4F;}
.style5 {font-size: 12px; text-align:center; background-color:#CCCCCC}
.style6 {font-size: 12px; text-align:center; color:#FF6633}

-->
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    monseAction();
});

function monseAction(){
       $("#tdpart_no").mouseover(function(){
            $(this).css("cursor", "pointer");
        });
        $("#tdpart_no").on("click", function(){
            result_call();
        });
        $("#tdpart_name").mouseover(function(){
            $(this).css("cursor", "pointer");
        });
        $("#tdpart_name").on("click", function(){
            result_call();
        });
        $("#tdf_name").mouseover(function(){
            $(this).css("cursor", "pointer");
        });
        $("#tdf_name").on("click", function(){
            result_call();
        });
}

function Search()
{
    var form = document.forms[0];
    
    form.cmd.value = "plmSearch";
    form.action =  '/plm/servlet/e3ps/costComServlet';
    form.submit();
}

function result_call(){
    
    <%
    if("ok".equals(NotProgerss)){
    %>
    alert("이미 원가요청이 진행중입니다.");
    return;
    <%
    }
    %>
    
    <%
    if("ok".equals(GetPlm)){
    %>
    plmSearch();
    return;
    <%
    }
    %>
       <%
        if(Rcount > 0){
            int k = 0;
            int j = 0;
            int q = 1;
            out.println("opener.document.WiseGrid1.AddRow();");

            for(int i=0; i<Rcount; i++){
                searchMap = (Hashtable)SearchList.get(i);
                if(search_type.equals("old")){
                    group_no = StringUtil.checkNull((String)searchMap.get("o_group_no"));
                }
                team = StringUtil.checkNull((String)searchMap.get("team"));
                part_note = StringUtil.checkNull((String)searchMap.get("part_note"));
                IsPlm = StringUtil.checkNull((String)searchMap.get("IsPlm"));
                f_name = StringUtil.checkNull((String)searchMap.get("f_name"));

                if(IsPlm.equals("plm")){
                    partSqe = StringUtil.checkNull((String)searchMap.get("partSqe"));
                }
                part_name = StringUtil.checkNull((String)searchMap.get("part_name"));

                part_no = StringUtil.checkNull((String)searchMap.get("part_no"));
                pjt_name = StringUtil.checkNull((String)searchMap.get("pjt_name"));
                car_type = StringUtil.checkNull((String)searchMap.get("car_type"));
                su_year_1 = StringUtil.checkNull((String)searchMap.get("su_year_1"));
                su_year_2 = StringUtil.checkNull((String)searchMap.get("su_year_2"));
                su_year_3 = StringUtil.checkNull((String)searchMap.get("su_year_3"));
                su_year_4 = StringUtil.checkNull((String)searchMap.get("su_year_4"));
                su_year_5 = StringUtil.checkNull((String)searchMap.get("su_year_5"));
                su_year_6 = StringUtil.checkNull((String)searchMap.get("su_year_6"));
                su_year_7 = StringUtil.checkNull((String)searchMap.get("su_year_7"));
                su_year_8 = StringUtil.checkNull((String)searchMap.get("su_year_8"));
                customer_F = StringUtil.checkNull((String)searchMap.get("customer_F"));
                customer_L = StringUtil.checkNull((String)searchMap.get("customer_L"));
                client_cost = StringUtil.checkNull((String)searchMap.get("client_cost"));
                rev_pk = StringUtil.checkNull((String)searchMap.get("rev_pk"));
                ket_cost = StringUtil.checkNull((String)searchMap.get("ket_cost"));
                target_cost = StringUtil.checkNull((String)searchMap.get("target_cost"));

                a_name = StringUtil.checkNull((String)searchMap.get("a_name"));
                drProgress = StringUtil.checkNull((String)searchMap.get("drProgress"));
                request_day = StringUtil.checkNull((String)searchMap.get("request_day"));
                usage = StringUtil.checkNull((String)searchMap.get("usage"));

                make          = StringUtil.checkNull((String)searchMap.get("make"));
                case_count_1  = StringUtil.checkNull((String)searchMap.get("case_count_1"));
                case_count_2  = StringUtil.checkNull((String)searchMap.get("case_count_2"));
                pro_type      = StringUtil.checkNull((String)searchMap.get("pro_type"));
                part_type     = StringUtil.checkNull((String)searchMap.get("part_type"));
                mix_group     = StringUtil.checkNull((String)searchMap.get("mix_group"));
                net_1         = StringUtil.checkNull((String)searchMap.get("net_1"));
                net_2         = StringUtil.checkNull((String)searchMap.get("net_2"));
                net_3         = StringUtil.checkNull((String)searchMap.get("net_3"));
                meterial      = StringUtil.checkNull((String)searchMap.get("meterial"));
                t_size        = StringUtil.checkNull((String)searchMap.get("t_size"));
                w_size        = StringUtil.checkNull((String)searchMap.get("w_size"));
                p_size        = StringUtil.checkNull((String)searchMap.get("p_size"));
                plating       = StringUtil.checkNull((String)searchMap.get("plating"));
                m_maker       = StringUtil.checkNull((String)searchMap.get("m_maker"));
                m_mone        = StringUtil.checkNull((String)searchMap.get("m_mone"));
                unitcost      = StringUtil.checkNull((String)searchMap.get("unitcost"));
                unitcost_txt  = StringUtil.checkNull((String)searchMap.get("unitcost_txt"));
                grade_name    = StringUtil.checkNull((String)searchMap.get("grade_name"));
                grade_color   = StringUtil.checkNull((String)searchMap.get("grade_color"));
                order_con     = StringUtil.checkNull((String)searchMap.get("order_con"));
                h_weight      = StringUtil.checkNull((String)searchMap.get("h_weight"));
                h_scrap       = StringUtil.checkNull((String)searchMap.get("h_scrap"));
                t_weight      = StringUtil.checkNull((String)searchMap.get("t_weight"));
                recycle       = StringUtil.checkNull((String)searchMap.get("recycle"));
                die_no        = StringUtil.checkNull((String)searchMap.get("die_no"));
                cavity        = StringUtil.checkNull((String)searchMap.get("cavity"));
                m_su          = StringUtil.checkNull((String)searchMap.get("m_su"));
                mold_cost     = StringUtil.checkNull((String)searchMap.get("mold_cost"));
                mold_c_type   = StringUtil.checkNull((String)searchMap.get("mold_c_type"));
                make_type     = StringUtil.checkNull((String)searchMap.get("make_type"));
                pro_1         = StringUtil.checkNull((String)searchMap.get("pro_1"));
                ton           = StringUtil.checkNull((String)searchMap.get("ton"));
                spm           = StringUtil.checkNull((String)searchMap.get("spm"));
                method_type   = StringUtil.checkNull((String)searchMap.get("method_type"));
                pro_level     = StringUtil.checkNull((String)searchMap.get("pro_level"));
                pro_level_txt = StringUtil.checkNull((String)searchMap.get("pro_level_txt"));
                line_su       = StringUtil.checkNull((String)searchMap.get("line_su"));
                sul_cost      = StringUtil.checkNull((String)searchMap.get("sul_cost"));
                plating_type  = StringUtil.checkNull((String)searchMap.get("plating_type"));
                type_2        = StringUtil.checkNull((String)searchMap.get("type_2"));
                t_mone        = StringUtil.checkNull((String)searchMap.get("t_mone"));
                type_1        = StringUtil.checkNull((String)searchMap.get("type_1"));
                type_cost     = StringUtil.checkNull((String)searchMap.get("type_cost"));
                t_order       = StringUtil.checkNull((String)searchMap.get("t_order"));
                pack_type     = StringUtil.checkNull((String)searchMap.get("pack_type"));
                in_pack       = StringUtil.checkNull((String)searchMap.get("in_pack"));
                out_pack      = StringUtil.checkNull((String)searchMap.get("out_pack"));
                store         = StringUtil.checkNull((String)searchMap.get("store"));
                dest          = StringUtil.checkNull((String)searchMap.get("dest"));
                dis_cost      = StringUtil.checkNull((String)searchMap.get("dis_cost"));
                distri_cost   = StringUtil.checkNull((String)searchMap.get("distri_cost"));
                royalty       = StringUtil.checkNull((String)searchMap.get("royalty"));
                yazaki_ro     = StringUtil.checkNull((String)searchMap.get("yazaki_ro"));
                etc_cost      = StringUtil.checkNull((String)searchMap.get("etc_cost"));
                plating_cost      = StringUtil.checkNull((String)searchMap.get("plating_cost"));

                if(search_type.equals("old")){
                    if(group_no != null && group_no.length() == 4){
                        if(IsPlm.equals("plm")){
                            out.println("opener.document.WiseGrid2.AddRow();");
                        }else{
                            if(i == 0 && !plmCheck){
                                out.println("opener.document.WiseGrid2.AddRow();");
                            }
                        }
                    }
                    if(!part_note.equals("")){
                        part_note = part_note.replaceAll("chr(13)&chr(10)", " ");
                    }

                }else{

                    out.println("opener.document.WiseGrid2.AddRow();");

                    /*if(Integer.toString(i).length() == 1){
                        group_no = "T00"+(i+1);
                    }else if(Integer.toString(i).length() == 2){
                        group_no = "T0"+(i+1);
                    }else if(Integer.toString(i).length() == 3){
                        group_no = "T"+(i+1);
                    }*/
                    if(i < 9){
                        group_no = "T00"+(i+1);
                    }else if(i > 8){
                        group_no = "T0"+(i+1);
                    }else if(i > 98){
                        group_no = "T"+(i+1);
                    }
                    //out.println("alert('"+group_no+".."+Integer.toString(i).length()+"');");
                }
                if(target_cost.equals("")){
                    target_cost = "0";
                }
                if(rev_no.equals("")){
                    rev_no = "0";
                }
                if(search_type.equals("")){
                    out.println("opener.document.WiseGrid3.AddRow();");
                }else{
                    if(!IsPlm.equals("plm")){
                        out.println("opener.document.WiseGrid3.AddRow();");
                    }
                }

        %>

        <%if(i==0){%>
        opener.document.WiseGrid1.SetCellValue("pk_cr_group",<%=k%>, "<%=pjt_no%>");
        opener.document.WiseGrid1.SetCellValue("pjt_no", <%=0%>, "<%=pjt_no%>");
        opener.document.WiseGrid1.SetCellValue("f_name",<%=k%>,"<%=f_name%>");
        opener.document.WiseGrid1.SetCellValue("pjt_name",<%=k%>,"<%=pjt_name%>");
        opener.document.WiseGrid1.SetComboSelectedHiddenValue("team",<%=k%>,"<%=team%>");
        opener.document.WiseGrid1.SetCellValue("a_name",<%=k%>,"<%=a_name%>");
        opener.document.WiseGrid1.SetCellValue("dr_step",<%=k%>,"<%=drProgress%>");
        opener.document.WiseGrid1.SetCellValue("request_day",<%=k%>,"<%=request_day%>");


        <%

        }
        boolean IsSet2 = false;
        boolean IsSet3 = false;
        if(search_type.equals("")){
            IsSet2 = true;
            IsSet3 = true;
        }else{
            if(IsPlm.equals("plm")){
                IsSet2 = true;
            }else{
                if((i == 0 && !plmCheck)){
                    IsSet2 = true;
                }
                IsSet3 = true;
            }
        }

        if(group_no.length() == 4){
            if(client_cost.equals("0")){
                client_cost = "";
            }
            if(client_cost.equals("0")){
                client_cost = "";
            }

        %>
        <%if(IsSet2){%>
        opener.document.WiseGrid2.SetCellValue("pk_cr_group", <%=j%>, "<%=pjt_no%>");
        opener.document.WiseGrid2.SetCellValue("partSqe", <%=j%>, "<%=partSqe%>");
        opener.document.WiseGrid2.SetCellValue("group_no",<%=j%>,"<%=group_no%>");
        opener.document.WiseGrid2.SetCellValue("part_name",<%=j%>,"<%=part_name%>");
        opener.document.WiseGrid2.SetCellValue("part_no",<%=j%>,"<%=part_no%>");
        opener.document.WiseGrid2.SetCellValue("car_type",<%=j%>,"<%=car_type%>");
        opener.document.WiseGrid2.SetCellValue("su_year_1",<%=j%>,"<%=su_year_1%>");
        opener.document.WiseGrid2.SetCellValue("su_year_2",<%=j%>,"<%=su_year_2%>");
        opener.document.WiseGrid2.SetCellValue("su_year_3",<%=j%>,"<%=su_year_3%>");
        opener.document.WiseGrid2.SetCellValue("su_year_4",<%=j%>,"<%=su_year_4%>");
        opener.document.WiseGrid2.SetCellValue("su_year_5",<%=j%>,"<%=su_year_5%>");
        opener.document.WiseGrid2.SetCellValue("su_year_6",<%=j%>,"<%=su_year_6%>");
        opener.document.WiseGrid2.SetCellValue("su_year_7",<%=j%>,"<%=su_year_7%>");
        opener.document.WiseGrid2.SetCellValue("su_year_8",<%=j%>,"<%=su_year_8%>");
        opener.document.WiseGrid2.SetCellValue("customer_F",<%=j%>,"<%=customer_F%>");
        opener.document.WiseGrid2.SetCellValue("customer_L",<%=j%>,"<%=customer_L%>");
        opener.document.WiseGrid2.SetCellValue("client_cost",<%=j%>,"<%=client_cost%>");
        opener.document.WiseGrid2.SetCellValue("ket_cost",<%=j%>,"<%=ket_cost%>");
        opener.document.WiseGrid2.SetCellValue("target_cost",<%=j%>,"<%=target_cost%>");
        opener.document.WiseGrid2.SetCellValue("rev_pk",<%=j%>,"<%=rev_pk%>");
        opener.document.WiseGrid2.SetCellValue("rev_no",<%=j%>,"<%=rev_no%>");
        <%}%>
        <%if(IsSet3){%>
        opener.document.WiseGrid3.SetCellImage("g_sel1",<%=k%>,'0');
        <%}%>

        <%
            j = j + 1;
            q = q + 1;
            }else if(group_no.length() == 7 && IsSet3){%>
            opener.document.WiseGrid3.SetCellImage("g_sel2",<%=k%>,'0');
            <%
            }else if(group_no.length() == 10 && IsSet3){%>
            opener.document.WiseGrid3.SetCellImage("g_sel3",<%=k%>,'0');
            <%
            }

            if(IsSet3){
            %>
            opener.document.WiseGrid3.SetCellValue("pk_cr_group", <%=k%>, "<%=pjt_no%>");
            opener.document.WiseGrid3.SetCellValue("group_no",<%=k%>,"<%=group_no%>");
            opener.document.WiseGrid3.SetCellValue("part_name",<%=k%>,"<%=part_name%>");
            opener.document.WiseGrid3.SetCellValue("part_no",<%=k%>,"<%=part_no%>");
            opener.document.WiseGrid3.SetCellValue("usage",<%=k%>,"<%=usage%>");
            <%if(search_type.equals("old")){ %>
                opener.document.WiseGrid3.SetCellValue("make", <%=k%>, "<%=make%>");
                opener.document.WiseGrid3.SetCellValue("case_count_1", <%=k%>, "<%=case_count_1%>");
                opener.document.WiseGrid3.SetCellValue("case_count_2", <%=k%>, "<%=case_count_2%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("pro_type", <%=k%>, "<%=pro_type%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("part_type", <%=k%>, "<%=part_type%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("mix_group", <%=k%>, "<%=mix_group%>");
                opener.document.WiseGrid3.SetCellValue("net_1", <%=k%>, "<%=net_1%>");
                opener.document.WiseGrid3.SetCellValue("net_2", <%=k%>, "<%=net_2%>");
                opener.document.WiseGrid3.SetCellValue("net_3", <%=k%>, "<%=net_3%>");
                opener.document.WiseGrid3.SetCellValue("meterial", <%=k%>, "<%=meterial%>");
                opener.document.WiseGrid3.SetCellValue("t_size", <%=k%>, "<%=t_size%>");
                opener.document.WiseGrid3.SetCellValue("w_size", <%=k%>, "<%=w_size%>");
                opener.document.WiseGrid3.SetCellValue("p_size", <%=k%>, "<%=p_size%>");
                opener.document.WiseGrid3.SetCellValue("plating", <%=k%>, "<%=plating%>");
                opener.document.WiseGrid3.SetCellValue("m_maker", <%=k%>, "<%=m_maker%>");
                opener.document.WiseGrid3.SetCellValue("m_mone", <%=k%>, "<%=m_mone%>");
                opener.document.WiseGrid3.SetCellValue("unitcost", <%=k%>, "<%=unitcost%>");
                opener.document.WiseGrid3.SetCellValue("unitcost_txt", <%=k%>, "<%=unitcost_txt%>");
                opener.document.WiseGrid3.SetCellValue("grade_name", <%=k%>, "<%=grade_name%>");
                opener.document.WiseGrid3.SetCellValue("grade_color", <%=k%>, "<%=grade_color%>");
                opener.document.WiseGrid3.SetCellValue("order_con", <%=k%>, "<%=order_con%>");
                opener.document.WiseGrid3.SetCellValue("h_weight", <%=k%>, "<%=h_weight%>");
                opener.document.WiseGrid3.SetCellValue("h_scrap", <%=k%>, "<%=h_scrap%>");
                opener.document.WiseGrid3.SetCellValue("t_weight", <%=k%>, "<%=t_weight%>");
                opener.document.WiseGrid3.SetCellValue("recycle", <%=k%>, "<%=recycle%>");
                opener.document.WiseGrid3.SetCellValue("die_no", <%=k%>, "<%=die_no%>");
                opener.document.WiseGrid3.SetCellValue("cavity", <%=k%>, "<%=cavity%>");
                opener.document.WiseGrid3.SetCellValue("m_su", <%=k%>, "<%=m_su%>");
                opener.document.WiseGrid3.SetCellValue("mold_cost", <%=k%>, "<%=mold_cost%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("mold_c_type", <%=k%>, "<%=mold_c_type%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("make_type", <%=k%>, "<%=make_type%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("pro_1", <%=k%>, "<%=pro_1%>");
                opener.document.WiseGrid3.SetCellValue("ton", <%=k%>, "<%=ton%>");
                opener.document.WiseGrid3.SetCellValue("spm", <%=k%>, "<%=spm%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("method_type", <%=k%>, "<%=method_type%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("pro_level", <%=k%>, "<%=pro_level%>");
                opener.document.WiseGrid3.SetCellValue("pro_level_txt", <%=k%>, "<%=pro_level_txt%>");
                opener.document.WiseGrid3.SetCellValue("line_su", <%=k%>, "<%=line_su%>");
                opener.document.WiseGrid3.SetCellValue("sul_cost", <%=k%>, "<%=sul_cost%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("plating_type", <%=k%>, "<%=plating_type%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("type_2", <%=k%>, "<%=type_2%>");
                opener.document.WiseGrid3.SetCellValue("plating_cost", <%=k%>, "<%=plating_cost%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("type_1", <%=k%>, "<%=type_1%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("t_mone", <%=k%>, "<%=t_mone%>");
                opener.document.WiseGrid3.SetCellValue("type_cost", <%=k%>, "<%=type_cost%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("t_order", <%=k%>, "<%=t_order%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("pack_type", <%=k%>, "<%=pack_type%>");
                opener.document.WiseGrid3.SetCellValue("in_pack", <%=k%>, "<%=in_pack%>");
                opener.document.WiseGrid3.SetCellValue("out_pack", <%=k%>, "<%=out_pack%>");
                opener.document.WiseGrid3.SetCellValue("store", <%=k%>, "<%=store%>");
                opener.document.WiseGrid3.SetCellValue("dest", <%=k%>, "<%=dest%>");
                opener.document.WiseGrid3.SetCellValue("dis_cost", <%=k%>, "<%=dis_cost%>");
                opener.document.WiseGrid3.SetCellValue("distri_cost", <%=k%>, "<%=distri_cost%>");
                opener.document.WiseGrid3.SetComboSelectedHiddenValue("royalty", <%=k%>, "<%=royalty%>");
                opener.document.WiseGrid3.SetCellValue("yazaki_ro", <%=k%>, "<%=yazaki_ro%>");
                opener.document.WiseGrid3.SetCellValue("etc_cost", <%=k%>, "<%=etc_cost%>");
                opener.document.WiseGrid3.SetCellValue("part_note", <%=k%>, "<%=part_note%>");
            <%
            }
            k=k+1;
            }
            %>

        <%}

        }
        %>
        opener.part_1.pk_cr_group.value="<%=pjt_no%>";
        self.close();    
}

function plmSearch(){
    
    <%
    if("ok".equals(GetPlm)){
    %>
        opener.part_1.pk_cr_group.value="<%=pjt_no%>";
        opener.doQuery1();
        opener.doQuery2();
        opener.doQuery3();
        self.close();
    <%
    }
    %>
}


function onKeyPress() {
    if (window.event) {
        if (window.event.keyCode == 13) Search();
    } else return;
}
document.onkeypress = onKeyPress;
</script>
</head>
<body topmargin="0" leftmargin="0"><form name="searchpage"  target="_self" method="post" >
<input type="hidden" name="cmd" value="plmSearch">
<input type="hidden" name="wise_gb" value="No">
<table width="493" height="223" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td width="493" height="153" background="/plm/jsp/cost/acc_img/plm_search.jpg" bgcolor="#FFFFFF"><table width="450" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="90" height="70" valign="bottom" class="style2">&nbsp;</td>
        <td width="210" height="70" valign="bottom" class="style2">&nbsp;</td>
        <td width="150" valign="bottom" class="style2">&nbsp;</td>
        </tr>
      <tr>
        <td height="26" class="style1">Project No. </td>
        <td  class="style1"><input type="text" name="pjt_no" size="30" class="style4" value="<%=pjt_no%>"></td>
        <td  class="style1" valign="bottom"><span class="style3">
            <%  if(page_type.equals("edit")){
                    brows = "/plm/jsp/cost/acc_img/btn_brow_b.gif";
                }else{
                    brows = "/plm/jsp/cost/acc_img/btn_brow.gif";
                }
            %><img src="<%=brows%>" width="51" height="17" border="0" onClick="Search();"
            <%if(page_type.equals("edit")){%>
                "disabled";
            <% }else{%>
                style="cursor:pointer;"
            <%} %>/>
            <img src="/plm/jsp/cost/acc_img/btn_rok.gif" width="51" height="17" border="0" onClick="result_call();" style="cursor:pointer;"/></span></td>
        </tr>
      <tr>
        <td height="31" colspan="3" valign="bottom" class="style6">&nbsp;
        <%if(Rcount > 0){%>
            ※ 등록버튼을 누르시면 Project No →  <%=pjt_no%>  의 원가요청서가 생성됩니다
        <%}%></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="71" valign="top" bgcolor="#FFFFFF" align="center"><table width="469" border="0" cellpadding="0" cellspacing="1">
      <tr>
        <td width="10" bgcolor="#FFFFFF"></td>
        <td height="21" colspan="3" bgcolor="#FFFFFF" class="style2">* 검색결과 <%=s_pjt_name%> </td>
        <td width="12" bgcolor="#FFFFFF"></td>
      </tr>
      <tr>
        <td width="10" bgcolor="#FFFFFF"></td>
        <td height="1" colspan="3" bgcolor="#00455d"></td>
        <td width="12" bgcolor="#FFFFFF"></td>
      </tr>
      <tr>
        <td width="10" bgcolor="#FFFFFF"></td>
        <td width="93" height="21" bgcolor="#EAEAEA" class="style1">Part No </td>
        <td width="268" bgcolor="#EAEAEA" class="style1">Part Name </td>
        <td width="76" bgcolor="#EAEAEA" class="style1">담당자</td>
        <td width="12" bgcolor="#FFFFFF"></td>
      </tr>
     <%if(SearchList != null && SearchList.size() > 0){

            for(int i=0; i < SearchList.size(); i++){
                searchMap = (Hashtable)SearchList.get(i);
                o_group_no = StringUtil.checkNull((String)searchMap.get("o_group_no"));
                part_name = StringUtil.checkNull((String)searchMap.get("part_name"));
                part_no = StringUtil.checkNull((String)searchMap.get("part_no"));
                f_name = StringUtil.checkNull((String)searchMap.get("f_name"));
                partSqe = StringUtil.checkNull((String)searchMap.get("partSqe"));
                IsPlm = StringUtil.checkNull((String)searchMap.get("IsPlm"));
                if(o_group_no.length() ==  4 && (!plmCheck || IsPlm.equals("plm"))){%>
        <tr>
            <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
            <td height="20" class="style1" id="tdpart_no">&nbsp;<%=part_no%></td>
            <td height="20" class="style4" id="tdpart_name">&nbsp;<%=part_name%></td>
            <td height="20" class="style1" id="tdf_name">&nbsp;<%=f_name%><%if(login_id.equals("admin")){out.println("("+partSqe+")");} %></td>
            <td width="12" bgcolor="#FFFFFF">&nbsp;</td>
        </tr>
        <tr>
            <td width="10" bgcolor="#FFFFFF"></td>
            <td height="1" colspan="3" bgcolor="#CCCCCC"></td>
            <td width="12" bgcolor="#FFFFFF"></td>
        </tr>

                <%}
            }
        }else{%>
     <tr>
        <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
        <td height="20" class="style1">&nbsp;</td>
        <td height="20" class="style4">&nbsp;</td>
        <td height="20" class="style1">&nbsp;</td>
        <td width="12" bgcolor="#FFFFFF">&nbsp;</td>
      </tr>
      <tr>
        <td width="10" bgcolor="#FFFFFF"></td>
        <td height="1" colspan="3" bgcolor="#CCCCCC"></td>
        <td width="12" bgcolor="#FFFFFF"></td>
      </tr>
    </table></td>
        <%} %>
  </tr>
</table>
</form>
</body>
</html>