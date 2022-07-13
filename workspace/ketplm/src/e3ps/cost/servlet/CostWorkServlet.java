package e3ps.cost.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;
import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;
import e3ps.cost.dao.CostStandDao;
import e3ps.cost.dao.CostWorkDao;

public class CostWorkServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        GridData gdReq = null;
        GridData gdReq6 = null;
        GridData gdReq4 = null;
        GridData gdRes = null;

        // Encode Type을 UTF-8로 변환한다.
        req.setCharacterEncoding("utf-8");
        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();
        try {
            // WISEGRID_DATA 라는 Param으로 WiseGrid의 전문이 올라온다.
            String rawData = req.getParameter("WISEGRID_DATA");
            String rawData6 = req.getParameter("WISEGRID_DATA6");
            String rawData4 = req.getParameter("WISEGRID_DATA4");
            // 올라온 전문을 Parsing하여 자료구조 형태로 반환해준다.
            gdReq = OperateGridData.parse(rawData);
            // 전달받은 파라미터값을 가져온다.
            String mode = gdReq.getParam("mode");
            if ("search3".equals(mode)){
                gdRes = Search3SelectQry(gdReq);
            }else if("search4".equals(mode)){
                gdRes = Search4SelectQry(gdReq);
            }else if("search6".equals(mode)){
                gdRes = Search6SelectQry(gdReq);
            }else if("save3".equals(mode)){
                gdRes = Save3Qry(gdReq);
            }else if("save4".equals(mode)){
                gdRes = Save4Qry(gdReq);
            }else if("modi".equals(mode)){
                gdReq6 = OperateGridData.parse(rawData6);
                gdReq4 = OperateGridData.parse(rawData4);
                gdRes = Save5Qry(gdReq, gdReq6, gdReq4);
            }else if("delete".equals(mode)){
                gdRes = deleteQry(gdReq);
            }else if("case_update".equals(mode)){
                gdRes = caseSaveQry(gdReq);
            }
        }catch (Exception e) {
            gdRes = new GridData();
            gdRes.setMessage("Error: " + e.getMessage());
            gdRes.setStatus("false");
            e.printStackTrace();
        }finally{
            try
            {
                // WiseGrid에 전달할 데이터가 담긴 GridData 객체와 미리 생성해 놓은 PrintWriter 객체를 넘겨
                // 데이터를 Parsing 한 후 전송합니다.
                OperateGridData.write(gdRes, out);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /*search3 조회*/
    public GridData Search3SelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try{
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            // 조회조건으로 사용할 Param 값들을 가져옵니다.
            String group_no = gdReq.getParam("group_no");
            String pkcr_group = gdReq.getParam("pk_cr_group");
            String rev_no = gdReq.getParam("rev_no");
            String data_type = gdReq.getParam("data_type");
            String mode_detail = gdReq.getParam("mode_detail");

            conn = DBUtil.getConnection();
            CostWorkDao workDao = new CostWorkDao(conn);
            // result
            ArrayList SearchList = workDao.getWorkSearch3List(group_no, pkcr_group, rev_no, data_type,mode_detail);
            Hashtable SearchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++){
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.
                SearchMap = (Hashtable)SearchList.get(i);
                group_no = (String)SearchMap.get("group_no");
                String m_co_chk = (String)SearchMap.get("m_co_chk");
                String case_count_1 = (String)SearchMap.get("case_count_1");
                String case_count_2 = (String)SearchMap.get("case_count_2");
                String pk_cw = (String)SearchMap.get("pk_cw");
                String pk_cr_group = (String)SearchMap.get("pk_cr_group");
                String make = (String)SearchMap.get("make");
                String part_name = (String)SearchMap.get("part_name");
                String part_no = (String)SearchMap.get("part_no");
                String net_1 = (String)SearchMap.get("net_1");
                String net_2 = (String)SearchMap.get("net_2");
                String net_3 = (String)SearchMap.get("net_3");
                String usage = (String)SearchMap.get("usage");
                String meterial = (String)SearchMap.get("meterial");
                String t_size = (String)SearchMap.get("t_size");
                String w_size = (String)SearchMap.get("w_size");
                String p_size = (String)SearchMap.get("p_size");
                String plating = (String)SearchMap.get("plating");
                String m_maker = (String)SearchMap.get("m_maker");
                String m_mone = (String)SearchMap.get("m_mone");
                String unitcost = (String)SearchMap.get("unitcost");
                String c_unitcost = (String)SearchMap.get("unitcost");
                String grade_name = (String)SearchMap.get("grade_name");
                String grade_color = (String)SearchMap.get("grade_color");
                String order_con = (String)SearchMap.get("order_con");
                String h_weight = (String)SearchMap.get("h_weight");
                String h_scrap = (String)SearchMap.get("h_scrap");
                String t_weight = (String)SearchMap.get("t_weight");
                String recycle = (String)SearchMap.get("recycle");
                String die_no = (String)SearchMap.get("die_no");
                String cavity = (String)SearchMap.get("cavity");
                String m_su = (String)SearchMap.get("m_su");
                String mold_cost = (String)SearchMap.get("mold_cost");
                String ton = (String)SearchMap.get("ton");
                String spm = (String)SearchMap.get("spm");
                String pro_level_txt = (String)SearchMap.get("pro_level_txt");
                String line_su = (String)SearchMap.get("line_su");
                String sul_cost = (String)SearchMap.get("sul_cost");
                String plating_cost = (String)SearchMap.get("plating_cost");
                String type_cost = (String)SearchMap.get("type_cost");
                String in_pack = (String)SearchMap.get("in_pack");
                String out_pack = (String)SearchMap.get("out_pack");
                String distri_cost = (String)SearchMap.get("distri_cost");
                String etc_cost = (String)SearchMap.get("etc_cost");
                String yazaki_ro = (String)SearchMap.get("yazaki_ro");
                String part_note = (String)SearchMap.get("part_note");
                String car_type = (String)SearchMap.get("car_type");
                String p_su_chk = (String)SearchMap.get("p_su_chk");
                String su_year_1 = (String)SearchMap.get("su_year_1");
                String su_year_2 = (String)SearchMap.get("su_year_2");
                String su_year_3 = (String)SearchMap.get("su_year_3");
                String su_year_4 = (String)SearchMap.get("su_year_4");
                String su_year_5 = (String)SearchMap.get("su_year_5");
                String su_year_6 = (String)SearchMap.get("su_year_6");
                String su_year_7 = (String)SearchMap.get("su_year_7");
                String su_year_8 = (String)SearchMap.get("su_year_8");
                String p_su_year_1 = (String)SearchMap.get("p_su_year_1");
                String p_su_year_2 = (String)SearchMap.get("p_su_year_2");
                String p_su_year_3 = (String)SearchMap.get("p_su_year_3");
                String p_su_year_4 = (String)SearchMap.get("p_su_year_4");
                String p_su_year_5 = (String)SearchMap.get("p_su_year_5");
                String p_su_year_6 = (String)SearchMap.get("p_su_year_6");
                String p_su_year_7 = (String)SearchMap.get("p_su_year_7");
                String p_su_year_8 = (String)SearchMap.get("p_su_year_8");
                String client_cost = (String)SearchMap.get("client_cost");
                String ket_cost = (String)SearchMap.get("ket_cost");
                String target_cost = (String)SearchMap.get("target_cost");
                String store = (String)SearchMap.get("store");
                String dest = (String)SearchMap.get("dest");
                String royalty = (String)SearchMap.get("royalty");
                String information = (String)SearchMap.get("information");
                String USD_rate = (String)SearchMap.get("USD_rate");
                String EURO_rate = (String)SearchMap.get("EURO_rate");
                String YEN_rate = (String)SearchMap.get("YEN_rate");
                String CNY_rate = (String)SearchMap.get("CNY_rate");
                String lme_ton = (String)SearchMap.get("lme_ton");
                String u_ex_rate = (String)SearchMap.get("u_ex_rate");
                String y_ex_rate = (String)SearchMap.get("y_ex_rate");
                String cost_report_add = (String)SearchMap.get("cost_report_add");
                String pro_type = (String)SearchMap.get("pro_type");
                String part_type = (String)SearchMap.get("part_type");
                String mix_group = (String)SearchMap.get("mix_group");
                String recycle_state = (String)SearchMap.get("recycle_state");
                String mold_c_type = (String)SearchMap.get("mold_c_type");
                String make_type = (String)SearchMap.get("make_type");
                String pro_1 = (String)SearchMap.get("pro_1");
                String method_type = (String)SearchMap.get("method_type");
                String pro_level = (String)SearchMap.get("pro_level");
                String plating_type = (String)SearchMap.get("plating_type");
                String type_2 = (String)SearchMap.get("type_2");
                String type_1 = (String)SearchMap.get("type_1");
                String t_mone = (String)SearchMap.get("t_mone");
                String t_order = (String)SearchMap.get("t_order");
                String pack_type = (String)SearchMap.get("pack_type");
                String file_1 = (String)SearchMap.get("file_1");
                String g_sel1 = "";
                String g_sel2 = "";
                String g_sel3 = "";
                if(group_no.length() < 5){
                    g_sel1 = "/plm/jsp/cost/images/common/add_red.jpg";
                }else if(group_no.length() < 8){
                    g_sel2 = "/plm/jsp/cost/images/common/add.jpg";
                }else{
                    g_sel3 = "/plm/jsp/cost/images/common/add.jpg";
                }

                gdRes.getHeader("SELECTED").addValue("0", "");
                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("m_co_chk").addValue(m_co_chk,"");
                gdRes.getHeader("case_count_1").addValue(case_count_1,"");
                gdRes.getHeader("case_count_2").addValue(case_count_2,"");
                gdRes.getHeader("pk_cw").addValue(pk_cw,"");
                gdRes.getHeader("pk_cr_group").addValue(pk_cr_group,"");
                gdRes.getHeader("make").addValue(make,"");
                gdRes.getHeader("group_no").addValue(group_no,"");
                gdRes.getHeader("pro_type").addSelectedHiddenValue(pro_type);
                gdRes.getHeader("g_sel1").addValue("", "", g_sel1);
                gdRes.getHeader("g_sel2").addValue("", "", g_sel2);
                gdRes.getHeader("g_sel3").addValue("", "", g_sel3);
                gdRes.getHeader("part_name").addValue(part_name,"");
                gdRes.getHeader("part_type").addSelectedHiddenValue(part_type);
                gdRes.getHeader("mix_group").addSelectedHiddenValue(mix_group);
                gdRes.getHeader("part_no").addValue(part_no,"");
                gdRes.getHeader("net_1").addValue(net_1,"");
                gdRes.getHeader("net_2").addValue(net_2,"");
                gdRes.getHeader("net_3").addValue(net_3,"");
                gdRes.getHeader("usage").addValue(usage,"");
                gdRes.getHeader("meterial").addValue(meterial,"");
                gdRes.getHeader("t_size").addValue(t_size,"");
                gdRes.getHeader("w_size").addValue(w_size,"");
                gdRes.getHeader("p_size").addValue(p_size,"");
                gdRes.getHeader("plating").addValue(plating,"");
                gdRes.getHeader("m_maker").addValue(m_maker,"");
                gdRes.getHeader("m_mone").addValue(m_mone,"");
                gdRes.getHeader("unitcost").addValue(unitcost,"");
                gdRes.getHeader("c_unitcost").addValue(c_unitcost,"");
                gdRes.getHeader("grade_name").addValue(grade_name,"");
                gdRes.getHeader("grade_color").addValue(grade_color,"");
                gdRes.getHeader("order_con").addValue(order_con,"");
                gdRes.getHeader("h_weight").addValue(h_weight,"");
                gdRes.getHeader("h_scrap").addValue(h_scrap,"");
                gdRes.getHeader("t_weight").addValue(t_weight,"");
                gdRes.getHeader("recycle_state").addSelectedHiddenValue(recycle_state);
                gdRes.getHeader("recycle").addValue(recycle,"");
                gdRes.getHeader("die_no").addValue(die_no,"");
                gdRes.getHeader("cavity").addValue(cavity,"");
                gdRes.getHeader("m_su").addValue(m_su,"");
                gdRes.getHeader("mold_cost").addValue(mold_cost,"");
                gdRes.getHeader("mold_c_type").addSelectedHiddenValue(mold_c_type);
                gdRes.getHeader("make_type").addSelectedHiddenValue(make_type);
                gdRes.getHeader("pro_1").addSelectedHiddenValue(pro_1);
                gdRes.getHeader("ton").addValue(ton,"");
                gdRes.getHeader("spm").addValue(spm,"");
                gdRes.getHeader("method_type").addSelectedHiddenValue(method_type);
                gdRes.getHeader("pro_level").addSelectedHiddenValue(pro_level);
                gdRes.getHeader("pro_level_txt").addValue(pro_level_txt,"");
                gdRes.getHeader("line_su").addValue(line_su,"");
                gdRes.getHeader("sul_cost").addValue(sul_cost,"");
                gdRes.getHeader("plating_type").addSelectedHiddenValue(plating_type);
                gdRes.getHeader("type_2").addSelectedHiddenValue(type_2);
                gdRes.getHeader("plating_cost").addValue(plating_cost,"");
                gdRes.getHeader("type_1").addSelectedHiddenValue(type_1);
                gdRes.getHeader("t_mone").addSelectedHiddenValue(t_mone);
                gdRes.getHeader("type_cost").addValue(type_cost,"");
                gdRes.getHeader("t_order").addSelectedHiddenValue(t_order);
                gdRes.getHeader("pack_type").addSelectedHiddenValue(pack_type);
                gdRes.getHeader("in_pack").addValue(in_pack,"");
                gdRes.getHeader("out_pack").addValue(out_pack,"");
                gdRes.getHeader("distri_cost").addValue(distri_cost,"");
                gdRes.getHeader("etc_cost").addValue(etc_cost,"");
                gdRes.getHeader("yazaki_ro").addValue(yazaki_ro,"");
                gdRes.getHeader("part_note").addValue(part_note,"");
                gdRes.getHeader("car_type").addValue(car_type,"");
                gdRes.getHeader("p_su_chk").addValue(p_su_chk,"");
                gdRes.getHeader("su_year_1").addValue(su_year_1, "0");
                gdRes.getHeader("su_year_2").addValue(su_year_2,"0");
                gdRes.getHeader("su_year_3").addValue(su_year_3,"0");
                gdRes.getHeader("su_year_4").addValue(su_year_4,"0");
                gdRes.getHeader("su_year_5").addValue(su_year_5,"0");
                gdRes.getHeader("su_year_6").addValue(su_year_6,"0");
                gdRes.getHeader("su_year_7").addValue(su_year_7,"0");
                gdRes.getHeader("su_year_8").addValue(su_year_8,"0");
                gdRes.getHeader("p_su_year_1").addValue(p_su_year_1,"0");
                gdRes.getHeader("p_su_year_2").addValue(p_su_year_2,"0");
                gdRes.getHeader("p_su_year_3").addValue(p_su_year_3,"0");
                gdRes.getHeader("p_su_year_4").addValue(p_su_year_4,"0");
                gdRes.getHeader("p_su_year_5").addValue(p_su_year_5,"0");
                gdRes.getHeader("p_su_year_6").addValue(p_su_year_6,"0");
                gdRes.getHeader("p_su_year_7").addValue(p_su_year_7,"0");
                gdRes.getHeader("p_su_year_8").addValue(p_su_year_8,"0");
                gdRes.getHeader("client_cost").addValue(client_cost,"");
                gdRes.getHeader("ket_cost").addValue(ket_cost,"");
                gdRes.getHeader("target_cost").addValue(target_cost,"");
                gdRes.getHeader("store").addValue(store,"");
                gdRes.getHeader("dest").addValue(dest,"");
                gdRes.getHeader("royalty").addValue(royalty,"");
                gdRes.getHeader("information").addValue(information,"");
                gdRes.getHeader("USD_rate").addValue(USD_rate,"");
                gdRes.getHeader("EURO_rate").addValue(EURO_rate,"");
                gdRes.getHeader("YEN_rate").addValue(YEN_rate,"");
                gdRes.getHeader("CNY_rate").addValue(CNY_rate,"");
                gdRes.getHeader("lme_ton").addValue(lme_ton,"");
                gdRes.getHeader("u_ex_rate").addValue(u_ex_rate,"");
                gdRes.getHeader("y_ex_rate").addValue(y_ex_rate,"");
                gdRes.getHeader("cost_report_add").addValue(cost_report_add,"");
                gdRes.getHeader("file_1").addValue(file_1,"");
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        // 메세지와 상태값을 셋팅합니다.
        gdRes.addParam("mode", "search3");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");
        return gdRes;
    }

    /*search4 조회*/
    public GridData Search4SelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try{
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            // 조회조건으로 사용할 Param 값들을 가져옵니다.
            String groupNo = gdReq.getParam("group_no");
            String pkcr_group = gdReq.getParam("pk_cr_group");
            String rev_no = gdReq.getParam("rev_no");
            String data_type = gdReq.getParam("data_type");
            String mode_detail = gdReq.getParam("mode_detail");

            conn = DBUtil.getConnection();
            CostWorkDao workDao = new CostWorkDao(conn);
            // result
            ArrayList SearchList = workDao.getWorkSearch4List(groupNo, pkcr_group, rev_no, data_type, mode_detail);
            Hashtable SearchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++){
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.
                SearchMap = (Hashtable)SearchList.get(i);
                String pk_cw=(String)SearchMap.get("pk_cw");
                String pk_cr_group=(String)SearchMap.get("pk_cr_group");
                String group_no=(String)SearchMap.get("group_no");
                String pro_type=(String)SearchMap.get("pro_type");
                String part_name=(String)SearchMap.get("part_name");
                String usage=(String)SearchMap.get("usage");
                String meterial_cost=(String)SearchMap.get("meterial_cost");
                String loss=(String)SearchMap.get("loss");
                String scrap_cost=(String)SearchMap.get("scrap_cost");
                String scrap_rate=(String)SearchMap.get("scrap_rate");
                String m_total_cost=(String)SearchMap.get("m_total_cost");
                String output=(String)SearchMap.get("output");
                String rate=(String)SearchMap.get("rate");
                String eff_value=(String)SearchMap.get("eff_value");
                String labor_cost=(String)SearchMap.get("labor_cost");
                String jun_cost=(String)SearchMap.get("jun_cost");
                String ma_depr=(String)SearchMap.get("ma_depr");
                String tabalu=(String)SearchMap.get("tabalu");
                String m_upkeep=(String)SearchMap.get("m_upkeep");
                String etc_expense=(String)SearchMap.get("etc_expense");
                String pack_cost=(String)SearchMap.get("pack_cost");
                String out_cost=(String)SearchMap.get("type_cost");
                String total_expense=(String)SearchMap.get("total_expense");
                String overhead=(String)SearchMap.get("overhead");
                String gita_cost=(String)SearchMap.get("gita_cost");
                String common_cost=(String)SearchMap.get("common_cost");
                String yzk_depr=(String)SearchMap.get("yzk_depr");
                String mold_type=(String)SearchMap.get("mold_type");
                System.out.println("DB mold_type : "+mold_type);
                String start_depr=(String)SearchMap.get("start_depr");
                String pro_depr=(String)SearchMap.get("pro_depr");
                String etc_depr=(String)SearchMap.get("etc_depr");
                String depr_cost=(String)SearchMap.get("depr_cost");
                String jae_cost=(String)SearchMap.get("jae_cost");
                String ge_cost=(String)SearchMap.get("ge_cost");
                String ad_cost=(String)SearchMap.get("ad_cost");
                String rnd_cost=(String)SearchMap.get("rnd_cost");
                String qu_cost=(String)SearchMap.get("qu_cost");
                String tariff=(String)SearchMap.get("tariff");
                String royalty_cost=(String)SearchMap.get("royalty_cost");
                String dis_cost=(String)SearchMap.get("dis_cost");
                String actual_cost=(String)SearchMap.get("actual_cost");
                String earn_rate=(String)SearchMap.get("earn_rate");
                String g_sel1 = "";
                String g_sel2 = "";
                String g_sel3 = "";
                if(group_no.length() < 5){
                    g_sel1 = "/plm/jsp/cost/images/common/add_red.jpg";
                }else if(group_no.length() < 8){
                    g_sel2 = "/plm/jsp/cost/images/common/add.jpg";
                }else{
                    g_sel3 = "/plm/jsp/cost/images/common/add.jpg";
                }

                String pl_meterial_cost=(String)SearchMap.get("pl_meterial_cost");
                String pl_loss=(String)SearchMap.get("pl_loss");
                String pl_scrap_cost=(String)SearchMap.get("pl_scrap_cost");
                String pl_m_total_cost=(String)SearchMap.get("pl_m_total_cost");
                String pl_output=(String)SearchMap.get("pl_output");
                String pl_rate=(String)SearchMap.get("pl_rate");
                String pl_labor_cost=(String)SearchMap.get("pl_labor_cost");
                String pl_jun_cost=(String)SearchMap.get("pl_jun_cost");
                String pl_ma_depr=(String)SearchMap.get("pl_ma_depr");
                String pl_tabalu=(String)SearchMap.get("pl_tabalu");
                String pl_m_upkeep=(String)SearchMap.get("pl_m_upkeep");
                String pl_total_expense=(String)SearchMap.get("pl_total_expense");
                String pl_overhead=(String)SearchMap.get("pl_overhead");
                String pl_common_cost=(String)SearchMap.get("pl_common_cost");
                String pl_yzk_depr=(String)SearchMap.get("pl_yzk_depr");
                String pl_start_depr=(String)SearchMap.get("pl_start_depr");
                String pl_pro_depr=(String)SearchMap.get("pl_pro_depr");
                String pl_etc_depr=(String)SearchMap.get("pl_etc_depr");
                String pl_jae_cost=(String)SearchMap.get("pl_jae_cost");
                String pl_ge_cost=(String)SearchMap.get("pl_ge_cost");
                String pl_rnd_cost=(String)SearchMap.get("pl_rnd_cost");
                String pl_tariff=(String)SearchMap.get("pl_tariff");
                String pl_royalty_cost=(String)SearchMap.get("pl_royalty_cost");
                String pl_actual_cost=(String)SearchMap.get("pl_actual_cost");


                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("pk_cw").addValue(pk_cw,"");
                gdRes.getHeader("pk_cr_group").addValue(pk_cr_group,"");
                gdRes.getHeader("group_no").addValue(group_no,"");
                gdRes.getHeader("pro_type").addValue(pro_type,"");
                gdRes.getHeader("g_sel1").addValue("", "", g_sel1);
                gdRes.getHeader("g_sel2").addValue("", "", g_sel2);
                gdRes.getHeader("g_sel3").addValue("", "", g_sel3);
                gdRes.getHeader("part_name").addValue(part_name,"");
                gdRes.getHeader("usage").addValue(usage,"");
                gdRes.getHeader("meterial_cost").addValue(meterial_cost,"");
                gdRes.getHeader("loss").addValue(loss,"");
                gdRes.getHeader("scrap_cost").addValue(scrap_cost,"");
                gdRes.getHeader("scrap_rate").addValue(scrap_rate,"");
                gdRes.getHeader("m_total_cost").addValue(m_total_cost,"");
                gdRes.getHeader("output").addValue(output,"");
                gdRes.getHeader("rate").addValue(rate,"");
                gdRes.getHeader("eff_value").addValue(eff_value,"");
                gdRes.getHeader("labor_cost").addValue(labor_cost,"");
                gdRes.getHeader("jun_cost").addValue(jun_cost,"");
                gdRes.getHeader("ma_depr").addValue(ma_depr,"");
                gdRes.getHeader("tabalu").addValue(tabalu,"");
                gdRes.getHeader("m_upkeep").addValue(m_upkeep,"");
                gdRes.getHeader("etc_expense").addValue(etc_expense,"");
                gdRes.getHeader("pack_cost").addValue(pack_cost,"");
                gdRes.getHeader("out_cost").addValue(out_cost,"");
                gdRes.getHeader("total_expense").addValue(total_expense,"");
                gdRes.getHeader("overhead").addValue(overhead,"");
                gdRes.getHeader("gita_cost").addValue(gita_cost,"");
                gdRes.getHeader("common_cost").addValue(common_cost,"");
                gdRes.getHeader("yzk_depr").addValue(yzk_depr,"");
                gdRes.getHeader("mold_type").addSelectedHiddenValue(mold_type);
                gdRes.getHeader("start_depr").addValue(start_depr,"");
                gdRes.getHeader("pro_depr").addValue(pro_depr,"");
                gdRes.getHeader("etc_depr").addValue(etc_depr,"");
                gdRes.getHeader("depr_cost").addValue(depr_cost,"");
                gdRes.getHeader("jae_cost").addValue(jae_cost,"");
                gdRes.getHeader("ge_cost").addValue(ge_cost,"");
                gdRes.getHeader("ad_cost").addValue(ad_cost,"");
                gdRes.getHeader("rnd_cost").addValue(rnd_cost,"");
                gdRes.getHeader("qu_cost").addValue(qu_cost,"");
                gdRes.getHeader("tariff").addValue(tariff,"");
                gdRes.getHeader("royalty_cost").addValue(royalty_cost,"");
                gdRes.getHeader("dis_cost").addValue(dis_cost,"");
                gdRes.getHeader("actual_cost").addValue(actual_cost,"");
                gdRes.getHeader("earn_rate").addValue(earn_rate,"");

                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("pk_cw").addValue (pk_cw,"");
                gdRes.getHeader("pk_cr_group").addValue("", "");
                gdRes.getHeader("group_no").addValue("", "");
                gdRes.getHeader("pro_type").addValue("", "");
                gdRes.getHeader("g_sel1").addValue("", "", "");
                gdRes.getHeader("g_sel2").addValue("", "", "");
                gdRes.getHeader("g_sel3").addValue("", "", "");
                gdRes.getHeader("part_name").addValue("산출결과값 변경", "");
                gdRes.getHeader("usage").addValue("", "");
                gdRes.getHeader("meterial_cost").addValue(pl_meterial_cost, "");
                gdRes.getHeader("loss").addValue(pl_loss, "");
                gdRes.getHeader("scrap_cost").addValue(pl_scrap_cost, "");
                gdRes.getHeader("scrap_rate").addValue("", "");
                gdRes.getHeader("m_total_cost").addValue(pl_m_total_cost, "");
                gdRes.getHeader("output").addValue(pl_output, "");
                gdRes.getHeader("rate").addValue(pl_rate, "");
                gdRes.getHeader("eff_value").addValue("", "");
                gdRes.getHeader("labor_cost").addValue(pl_labor_cost, "");
                gdRes.getHeader("jun_cost").addValue(pl_jun_cost, "");
                gdRes.getHeader("ma_depr").addValue(pl_ma_depr, "");
                gdRes.getHeader("tabalu").addValue(pl_tabalu, "");
                gdRes.getHeader("m_upkeep").addValue(pl_m_upkeep, "");
                gdRes.getHeader("etc_expense").addValue("", "");
                gdRes.getHeader("pack_cost").addValue("", "");
                gdRes.getHeader("out_cost").addValue("", "");
                gdRes.getHeader("total_expense").addValue(pl_total_expense, "");
                gdRes.getHeader("overhead").addValue(pl_overhead, "");
                gdRes.getHeader("gita_cost").addValue("", "");
                gdRes.getHeader("common_cost").addValue(pl_common_cost, "");
                gdRes.getHeader("yzk_depr").addValue(pl_yzk_depr, "");
                gdRes.getHeader("mold_type").addSelectedHiddenValue("");
                gdRes.getHeader("start_depr").addValue(pl_start_depr, "");
                gdRes.getHeader("pro_depr").addValue(pl_pro_depr, "");
                gdRes.getHeader("etc_depr").addValue(pl_etc_depr, "");
                gdRes.getHeader("depr_cost").addValue("", "");
                gdRes.getHeader("jae_cost").addValue(pl_jae_cost, "");
                gdRes.getHeader("ge_cost").addValue(pl_ge_cost, "");
                gdRes.getHeader("ad_cost").addValue("", "");
                gdRes.getHeader("rnd_cost").addValue(pl_rnd_cost, "");
                gdRes.getHeader("qu_cost").addValue("", "");
                gdRes.getHeader("tariff").addValue(pl_tariff, "");
                gdRes.getHeader("royalty_cost").addValue(pl_royalty_cost, "");
                gdRes.getHeader("dis_cost").addValue("", "");
                gdRes.getHeader("actual_cost").addValue(pl_actual_cost, "");
                gdRes.getHeader("earn_rate").addValue("", "");
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        // 메세지와 상태값을 셋팅합니다.
        gdRes.addParam("mode", "search4");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");
        return gdRes;
    }

    /*search6 조회*/
    public GridData Search6SelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try{
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            // 조회조건으로 사용할 Param 값들을 가져옵니다.
            String groupNo = gdReq.getParam("group_no");
            String pkcr_group = gdReq.getParam("pk_cr_group");
            String rev_no = gdReq.getParam("rev_no");

            String pk_cw					= "";
            String pk_cr_group			= "";
            String case_type_admin	= "";
            String part_name 			= "";
            String actual_cost 			= "";
            String earn_rate 			= "";
            String case_infor			= "";
            String group_no			= "";

            conn = DBUtil.getConnection();
            CostWorkDao workDao = new CostWorkDao(conn);
            // result
            ArrayList SearchList = workDao.getWorkSearch6List(groupNo, pkcr_group, rev_no);
            Hashtable SearchMap = null;

            String[] arrComboValue =  new String[SearchList.size()];
            String[] arrComboHiddenValue =  new String[SearchList.size()];


            if(SearchList.size() > 0){
                // case_type_admin 의 콤보 생성
                for(int i = 0; i < SearchList.size() ; i++){
                    SearchMap = (Hashtable)SearchList.get(i);
                    case_type_admin=(String)SearchMap.get("case_type_admin");
                    arrComboValue[i] = case_type_admin.substring(4);
                    arrComboHiddenValue[i] = case_type_admin;
                }
            }

            gdRes.getHeader("case_type_admin").setComboValues(arrComboValue, arrComboHiddenValue);
            if(SearchList.size() > 0){
                // GridData에 데이터를 셋팅합니다.
                for(int i = 0; i < SearchList.size() ; i++){
                    // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                    // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                    // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.
                    SearchMap = (Hashtable)SearchList.get(i);
                    pk_cw=(String)SearchMap.get("pk_cw");
                    pk_cr_group=(String)SearchMap.get("pk_cr_group");
                    case_type_admin=(String)SearchMap.get("case_type_admin");
                    part_name=(String)SearchMap.get("part_name");
                    actual_cost=(String)SearchMap.get("actual_cost");
                    earn_rate=(String)SearchMap.get("earn_rate");
                    case_infor=(String)SearchMap.get("case_infor");
                    group_no=(String)SearchMap.get("group_no");

                    gdRes.getHeader("SELECTED").addValue("0", "");
                    gdRes.getHeader("pk_cw").addValue(pk_cw,"");
                    gdRes.getHeader("pk_cr_group").addValue(pk_cr_group,"");
                    gdRes.getHeader("case_type_admin").addSelectedHiddenValue(case_type_admin);
                    gdRes.getHeader("part_name").addValue(part_name,"");
                    gdRes.getHeader("actual_cost").addValue(actual_cost,"");
                    gdRes.getHeader("earn_rate").addValue(earn_rate,"");
                    gdRes.getHeader("case_infor").addValue(case_infor,"");
                    gdRes.getHeader("group_no").addValue(group_no,"");

                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        // 메세지와 상태값을 셋팅합니다.
        gdRes.addParam("mode", "search4");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");
        return gdRes;
    }

    /* Save3 SAVE */
    public GridData Save3Qry(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("CRUD").getRowCount();

            ArrayList updateDataList = new ArrayList(rowCount);

            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            String information = gdReq.getParam("information");
            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostWorkDao workDao = new CostWorkDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                if (crud.equals("U")) {
                    updateData.put("m_co_chk",  gdReq.getHeader("m_co_chk").getValue(i));
                    updateData.put("pk_cw",  gdReq.getHeader("pk_cw").getValue(i));
                    updateData.put("pk_cr_group",  gdReq.getHeader("pk_cr_group").getValue(i));
                    updateData.put("make",  gdReq.getHeader("make").getValue(i));
                    updateData.put("group_no",  gdReq.getHeader("group_no").getValue(i));
                    if(gdReq.getHeader("pro_type").getSelectedIndex(i) >= 0){
                        updateData.put("pro_type",  gdReq.getHeader("pro_type").getComboHiddenValues()[gdReq.getHeader("pro_type").getSelectedIndex(i)]); // getComboSelectedIndex
                    }
                    updateData.put("part_name",  gdReq.getHeader("part_name").getValue(i));
                    if(gdReq.getHeader("part_type").getSelectedIndex(i) >= 0){
                        updateData.put("part_type",  gdReq.getHeader("part_type").getComboHiddenValues()[gdReq.getHeader("part_type").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    if(gdReq.getHeader("mix_group").getSelectedIndex(i) >= 0){
                        updateData.put("mix_group", gdReq.getHeader("mix_group").getComboHiddenValues()[gdReq.getHeader("mix_group").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    updateData.put("part_no", gdReq.getHeader("part_no").getValue(i));
                    updateData.put("net_1", gdReq.getHeader("net_1").getValue(i));
                    updateData.put("net_2", gdReq.getHeader("net_2").getValue(i));
                    updateData.put("net_3", gdReq.getHeader("net_3").getValue(i));
                    updateData.put("usage", gdReq.getHeader("usage").getValue(i));
                    updateData.put("meterial", gdReq.getHeader("meterial").getValue(i));
                    updateData.put("t_size", gdReq.getHeader("t_size").getValue(i));
                    updateData.put("w_size", gdReq.getHeader("w_size").getValue(i));
                    updateData.put("p_size", gdReq.getHeader("p_size").getValue(i));
                    updateData.put("plating", gdReq.getHeader("plating").getValue(i));
                    updateData.put("m_maker", gdReq.getHeader("m_maker").getValue(i));
                    updateData.put("m_mone", gdReq.getHeader("m_mone").getValue(i));
                    updateData.put("unitcost", gdReq.getHeader("unitcost").getValue(i));
                    updateData.put("c_unitcost", gdReq.getHeader("c_unitcost").getValue(i));
                    updateData.put("grade_name", gdReq.getHeader("grade_name").getValue(i));
                    updateData.put("grade_color", gdReq.getHeader("grade_color").getValue(i));
                    updateData.put("order_con", gdReq.getHeader("order_con").getValue(i));
                    updateData.put("h_weight", gdReq.getHeader("h_weight").getValue(i));
                    updateData.put("h_scrap", gdReq.getHeader("h_scrap").getValue(i));
                    updateData.put("t_weight", gdReq.getHeader("t_weight").getValue(i));
                    if(gdReq.getHeader("recycle_state").getSelectedIndex(i) >= 0){
                        updateData.put("recycle_state", gdReq.getHeader("recycle_state").getComboHiddenValues()[gdReq.getHeader("recycle_state").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    updateData.put("recycle", gdReq.getHeader("recycle").getValue(i));
                    updateData.put("die_no", gdReq.getHeader("die_no").getValue(i));
                    updateData.put("cavity", gdReq.getHeader("cavity").getValue(i));
                    updateData.put("m_su", gdReq.getHeader("m_su").getValue(i));
                    updateData.put("mold_cost", gdReq.getHeader("mold_cost").getValue(i));
                    if(gdReq.getHeader("mold_c_type").getSelectedIndex(i) >= 0){
                        updateData.put("mold_c_type", gdReq.getHeader("mold_c_type").getComboHiddenValues()[gdReq.getHeader("mold_c_type").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    if(gdReq.getHeader("make_type").getSelectedIndex(i) >= 0){
                        updateData.put("make_type", gdReq.getHeader("make_type").getComboHiddenValues()[gdReq.getHeader("make_type").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    if(gdReq.getHeader("pro_1").getSelectedIndex(i) >= 0){
                        updateData.put("pro_1", gdReq.getHeader("pro_1").getComboHiddenValues()[gdReq.getHeader("pro_1").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    updateData.put("ton", gdReq.getHeader("ton").getValue(i));
                    updateData.put("spm", gdReq.getHeader("spm").getValue(i));
                    if(gdReq.getHeader("method_type").getSelectedIndex(i) >= 0){
                        updateData.put("method_type", gdReq.getHeader("method_type").getComboHiddenValues()[gdReq.getHeader("method_type").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    if(gdReq.getHeader("pro_level").getSelectedIndex(i) >= 0){
                        updateData.put("pro_level", gdReq.getHeader("pro_level").getComboHiddenValues()[gdReq.getHeader("pro_level").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    updateData.put("pro_level_txt", gdReq.getHeader("pro_level_txt").getValue(i));
                    updateData.put("line_su", gdReq.getHeader("line_su").getValue(i));
                    updateData.put("sul_cost", gdReq.getHeader("sul_cost").getValue(i));
                    if(gdReq.getHeader("plating_type").getSelectedIndex(i) >= 0){
                        updateData.put("plating_type", gdReq.getHeader("plating_type").getComboHiddenValues()[gdReq.getHeader("plating_type").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    if(gdReq.getHeader("type_2").getSelectedIndex(i) >= 0){
                        updateData.put("type_2", gdReq.getHeader("type_2").getComboHiddenValues()[gdReq.getHeader("type_2").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    updateData.put("plating_cost", gdReq.getHeader("plating_cost").getValue(i));
                    if(gdReq.getHeader("type_1").getSelectedIndex(i) >= 0){
                        updateData.put("type_1", gdReq.getHeader("type_1").getComboHiddenValues()[gdReq.getHeader("type_1").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    if(gdReq.getHeader("t_mone").getSelectedIndex(i) >= 0){
                        updateData.put("t_mone", gdReq.getHeader("t_mone").getComboHiddenValues()[gdReq.getHeader("t_mone").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    updateData.put("type_cost", gdReq.getHeader("type_cost").getValue(i));
                    if(gdReq.getHeader("t_order").getSelectedIndex(i) >= 0){
                        updateData.put("t_order", gdReq.getHeader("t_order").getComboHiddenValues()[gdReq.getHeader("t_order").getSelectedIndex(i)]);// getComboSelectedIndex
                    }
                    if(gdReq.getHeader("pack_type").getSelectedIndex(i) >= 0){
                        updateData.put("pack_type", gdReq.getHeader("pack_type").getComboHiddenValues()[gdReq.getHeader("pack_type").getSelectedIndex(i)]);// getComboSelectedIndex
                    }

                    updateData.put("in_pack", gdReq.getHeader("in_pack").getValue(i));
                    updateData.put("out_pack", gdReq.getHeader("out_pack").getValue(i));
                    updateData.put("distri_cost", gdReq.getHeader("distri_cost").getValue(i));
                    updateData.put("etc_cost", gdReq.getHeader("etc_cost").getValue(i));
                    updateData.put("yazaki_ro", gdReq.getHeader("yazaki_ro").getValue(i));
                    updateData.put("part_note", gdReq.getHeader("part_note").getValue(i));
                    updateData.put("car_type", gdReq.getHeader("car_type").getValue(i));
                    updateData.put("p_su_chk", gdReq.getHeader("p_su_chk").getValue(i));
                    updateData.put("su_year_1", gdReq.getHeader("su_year_1").getValue(i));
                    updateData.put("su_year_2", gdReq.getHeader("su_year_2").getValue(i));
                    updateData.put("su_year_3", gdReq.getHeader("su_year_3").getValue(i));
                    updateData.put("su_year_4", gdReq.getHeader("su_year_4").getValue(i));
                    updateData.put("su_year_5", gdReq.getHeader("su_year_5").getValue(i));
                    updateData.put("su_year_6", gdReq.getHeader("su_year_6").getValue(i));
                    updateData.put("su_year_7", gdReq.getHeader("su_year_7").getValue(i));
                    updateData.put("su_year_8", gdReq.getHeader("su_year_8").getValue(i));
                    updateData.put("p_su_year_1", gdReq.getHeader("p_su_year_1").getValue(i));
                    updateData.put("p_su_year_2", gdReq.getHeader("p_su_year_2").getValue(i));
                    updateData.put("p_su_year_3", gdReq.getHeader("p_su_year_3").getValue(i));
                    updateData.put("p_su_year_4", gdReq.getHeader("p_su_year_4").getValue(i));
                    updateData.put("p_su_year_5", gdReq.getHeader("p_su_year_5").getValue(i));
                    updateData.put("p_su_year_6", gdReq.getHeader("p_su_year_6").getValue(i));
                    updateData.put("p_su_year_7", gdReq.getHeader("p_su_year_7").getValue(i));
                    updateData.put("p_su_year_8", gdReq.getHeader("p_su_year_8").getValue(i));
                    updateData.put("client_cost", gdReq.getHeader("client_cost").getValue(i));
                    updateData.put("ket_cost", gdReq.getHeader("ket_cost").getValue(i));
                    updateData.put("target_cost", gdReq.getHeader("target_cost").getValue(i));
                    updateData.put("store", gdReq.getHeader("store").getValue(i));
                    updateData.put("dest", gdReq.getHeader("dest").getValue(i));
                    updateData.put("royalty", gdReq.getHeader("royalty").getValue(i));
                    updateData.put("USD_rate", gdReq.getHeader("USD_rate").getValue(i));
                    updateData.put("EURO_rate", gdReq.getHeader("EURO_rate").getValue(i));
                    updateData.put("YEN_rate", gdReq.getHeader("YEN_rate").getValue(i));
                    updateData.put("CNY_rate", gdReq.getHeader("CNY_rate").getValue(i));
                    updateData.put("lme_ton", gdReq.getHeader("lme_ton").getValue(i));
                    updateData.put("u_ex_rate", gdReq.getHeader("u_ex_rate").getValue(i));
                    updateData.put("y_ex_rate", gdReq.getHeader("y_ex_rate").getValue(i));
                    updateData.put("information", StringUtil.checkNull(information));

                    updateDataList.add(updateData);
                    cnt = workDao.UpdateCostWork3(updateDataList);
                    updateDataList.clear();
                }
            }
            if(conn!=null) {conn.close();}
            gdRes.addParam("mode", "save");
            String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
            gdRes.setMessage(msg);
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }
        return gdRes;
    }

    /* Save4 SAVE */
    public GridData Save4Qry(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;
        try {
            ArrayList updateDataList = new ArrayList(rowCount);
            rowCount = Integer.parseInt(gdReq.getParam("table_row"));
            String information = gdReq.getParam("information");
            String case_yn = StringUtil.checkNull(gdReq.getParam("case_yn"));
            System.out.println("case_yn : "+case_yn);
            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostWorkDao workDao = new CostWorkDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                //if (crud.equals("U")) {
                    updateData.put("pk_cw", gdReq.getHeader("pk_cw").getValue(i));
                    updateData.put("meterial_cost", gdReq.getHeader("meterial_cost").getValue(i));
                    updateData.put("loss", gdReq.getHeader("loss").getValue(i));
                    updateData.put("scrap_cost", gdReq.getHeader("scrap_cost").getValue(i));
                    updateData.put("scrap_rate", gdReq.getHeader("scrap_rate").getValue(i));
                    updateData.put("m_total_cost", gdReq.getHeader("m_total_cost").getValue(i));
                    updateData.put("output", gdReq.getHeader("output").getValue(i));
                    updateData.put("rate", gdReq.getHeader("rate").getValue(i));
                    updateData.put("eff_value", gdReq.getHeader("eff_value").getValue(i));
                    updateData.put("labor_cost", gdReq.getHeader("labor_cost").getValue(i));
                    updateData.put("jun_cost", gdReq.getHeader("jun_cost").getValue(i));
                    updateData.put("ma_depr", gdReq.getHeader("ma_depr").getValue(i));
                    updateData.put("tabalu", gdReq.getHeader("tabalu").getValue(i));
                    updateData.put("m_upkeep", gdReq.getHeader("m_upkeep").getValue(i));
                    updateData.put("etc_expense", gdReq.getHeader("etc_expense").getValue(i));
                    updateData.put("pack_cost", gdReq.getHeader("pack_cost").getValue(i));
                    updateData.put("out_cost", gdReq.getHeader("out_cost").getValue(i));
                    updateData.put("total_expense", gdReq.getHeader("total_expense").getValue(i));
                    updateData.put("overhead", gdReq.getHeader("overhead").getValue(i));
                    updateData.put("gita_cost", gdReq.getHeader("gita_cost").getValue(i));
                    updateData.put("common_cost", gdReq.getHeader("common_cost").getValue(i));
                    updateData.put("yzk_depr", gdReq.getHeader("yzk_depr").getValue(i));
                    System.out.println("mold_tyep = "+i);
                    try{
                        updateData.put("mold_type"	    ,	gdReq.getHeader("mold_type").getComboHiddenValues()[gdReq.getHeader("mold_type").getSelectedIndex(i)] );
                    }catch(Exception e){
                        System.out.println("mold_type ==  null");
                    }
                    updateData.put("start_depr", gdReq.getHeader("start_depr").getValue(i));
                    updateData.put("pro_depr", gdReq.getHeader("pro_depr").getValue(i));
                    updateData.put("etc_depr", gdReq.getHeader("etc_depr").getValue(i));
                    updateData.put("depr_cost", gdReq.getHeader("depr_cost").getValue(i));
                    updateData.put("jae_cost", gdReq.getHeader("jae_cost").getValue(i));
                    updateData.put("ge_cost", gdReq.getHeader("ge_cost").getValue(i));
                    updateData.put("ad_cost", gdReq.getHeader("ad_cost").getValue(i));
                    updateData.put("rnd_cost", gdReq.getHeader("rnd_cost").getValue(i));
                    updateData.put("qu_cost", gdReq.getHeader("qu_cost").getValue(i));
                    updateData.put("tariff", gdReq.getHeader("tariff").getValue(i));
                    updateData.put("royalty_cost", gdReq.getHeader("royalty_cost").getValue(i));
                    updateData.put("dis_cost", gdReq.getHeader("dis_cost").getValue(i));
                    updateData.put("actual_cost", gdReq.getHeader("actual_cost").getValue(i));
                    updateData.put("earn_rate", gdReq.getHeader("earn_rate").getValue(i));
                    i = i+1;
                    System.out.println("mold_tyep2 = "+i);
                    updateData.put("pl_meterial_cost", gdReq.getHeader("meterial_cost").getValue(i));
                    System.out.println("mold_tye3 = "+i);
                    updateData.put("pl_loss", gdReq.getHeader("loss").getValue(i));
                    updateData.put("pl_scrap_cost", gdReq.getHeader("scrap_cost").getValue(i));
                    updateData.put("pl_m_total_cost", gdReq.getHeader("m_total_cost").getValue(i));
                    updateData.put("pl_output", gdReq.getHeader("output").getValue(i));
                    updateData.put("pl_rate", gdReq.getHeader("rate").getValue(i));
                    updateData.put("pl_labor_cost", gdReq.getHeader("labor_cost").getValue(i));
                    updateData.put("pl_jun_cost", gdReq.getHeader("jun_cost").getValue(i));
                    updateData.put("pl_ma_depr", gdReq.getHeader("ma_depr").getValue(i));
                    updateData.put("pl_tabalu", gdReq.getHeader("tabalu").getValue(i));
                    updateData.put("pl_m_upkeep", gdReq.getHeader("m_upkeep").getValue(i));
                    updateData.put("pl_total_expense", gdReq.getHeader("total_expense").getValue(i));
                    updateData.put("pl_overhead", gdReq.getHeader("overhead").getValue(i));
                    updateData.put("pl_common_cost", gdReq.getHeader("common_cost").getValue(i));
                    updateData.put("pl_yzk_depr", gdReq.getHeader("yzk_depr").getValue(i));
                    updateData.put("pl_start_depr", gdReq.getHeader("start_depr").getValue(i));
                    updateData.put("pl_pro_depr", gdReq.getHeader("pro_depr").getValue(i));
                    updateData.put("pl_etc_depr", gdReq.getHeader("etc_depr").getValue(i));
                    updateData.put("pl_jae_cost", gdReq.getHeader("jae_cost").getValue(i));
                    updateData.put("pl_ge_cost", gdReq.getHeader("ge_cost").getValue(i));
                    updateData.put("pl_rnd_cost", gdReq.getHeader("rnd_cost").getValue(i));
                    updateData.put("pl_tariff", gdReq.getHeader("tariff").getValue(i));
                    updateData.put("pl_royalty_cost", gdReq.getHeader("royalty_cost").getValue(i));
                    updateData.put("pl_actual_cost", gdReq.getHeader("actual_cost").getValue(i));

                    updateDataList.add(updateData);
                    cnt = workDao.UpdateCostWork4(updateDataList);
                    updateDataList.clear();
                //}
            }
            if(conn!=null) {conn.close();}
            if(case_yn.equals("Y")){
                gdRes.addParam("mode", "save_case");
            }else{
                gdRes.addParam("mode", "save");
            }
            String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
            gdRes.setMessage(msg);
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }
        return gdRes;
    }

    /* Save5 SAVE */
    public GridData Save5Qry(GridData gdReq, GridData gdReq6, GridData gdReq4) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;
        int rowCount4 = 0;
        String case_infor = "";
        String case_count = "";
        String case_type_admin = "";

        int j = 0;
        try {
            ArrayList insertDataList = new ArrayList(rowCount);
            rowCount = Integer.parseInt(gdReq.getParam("table_row"));
            case_count = StringUtil.checkNullZero(gdReq.getParam("case_count"));
            case_infor = gdReq.getParam("case_infor");
            System.out.println("rowCount : "+rowCount);
            System.out.println("case_count : "+case_count);
            System.out.println("case_infor : "+case_infor);

            /*if(Integer.parseInt(case_count) > 0){
                case_type_admin = gdReq6.getHeader("case_type_admin").getComboHiddenValues()[gdReq6.getHeader("case_type_admin").getSelectedIndex(Integer.parseInt(case_count)-1)];
                case_count = case_type_admin.replace("case", "");
            }*/
            case_count = "case"+case_count;
            System.out.println("case_count 444: "+case_count);
            rowCount4 = Integer.parseInt(gdReq.getParam("table_row2"));

            Hashtable<String, String> insertData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostWorkDao workDao = new CostWorkDao(conn);
            System.out.println("case_infor3 : "+case_infor);
            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {
                System.out.println("rev_no : "+gdReq.getParam("rev_no"));
                insertData.put("rev_no",gdReq.getParam("rev_no"));
                insertData.put("case_type_admin",case_count);
                insertData.put("case_infor",case_infor);
                insertData.put("m_co_chk",gdReq.getHeader("m_co_chk").getValue(i));
                insertData.put("case_count_1",gdReq.getHeader("case_count_1").getValue(i));
                insertData.put("case_count_2",gdReq.getHeader("case_count_2").getValue(i));
                insertData.put("pk_cw",gdReq.getHeader("pk_cw").getValue(i));
                insertData.put("pk_cr_group",gdReq.getHeader("pk_cr_group").getValue(i));
                insertData.put("make",gdReq.getHeader("make").getValue(i));
                insertData.put("group_no",gdReq.getHeader("group_no").getValue(i));
                System.out.println("i===> : "+i);
                try{
                    insertData.put("pro_type",gdReq.getHeader("pro_type").getComboHiddenValues()[gdReq.getHeader("pro_type").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("pro_type is null..");
                }
                insertData.put("part_name",gdReq.getHeader("part_name").getValue(i));
                System.out.println("rev_no 2: "+gdReq.getParam("rev_no"));
                try{
                    insertData.put("part_type",gdReq.getHeader("part_type").getComboHiddenValues()[gdReq.getHeader("part_type").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("part_type is null..");
                }
                System.out.println("rev_no 3: "+gdReq.getParam("rev_no"));
                try{
                    insertData.put("mix_group",gdReq.getHeader("mix_group").getComboHiddenValues()[gdReq.getHeader("mix_group").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("mix_group is null..");
                }
                System.out.println("rev_no 4: "+gdReq.getParam("rev_no"));
                insertData.put("part_no",gdReq.getHeader("part_no").getValue(i));
                insertData.put("net_1",gdReq.getHeader("net_1").getValue(i));
                insertData.put("net_2",gdReq.getHeader("net_2").getValue(i));
                insertData.put("net_3",gdReq.getHeader("net_3").getValue(i));
                insertData.put("usage",gdReq.getHeader("usage").getValue(i));
                insertData.put("meterial",gdReq.getHeader("meterial").getValue(i));
                insertData.put("t_size",gdReq.getHeader("t_size").getValue(i));
                insertData.put("w_size",gdReq.getHeader("w_size").getValue(i));
                insertData.put("p_size",gdReq.getHeader("p_size").getValue(i));
                insertData.put("plating",gdReq.getHeader("plating").getValue(i));
                insertData.put("m_maker",gdReq.getHeader("m_maker").getValue(i));
                insertData.put("m_mone",gdReq.getHeader("m_mone").getValue(i));
                insertData.put("unitcost",gdReq.getHeader("unitcost").getValue(i));
                insertData.put("c_unitcost",gdReq.getHeader("c_unitcost").getValue(i));
                insertData.put("grade_name",gdReq.getHeader("grade_name").getValue(i));
                insertData.put("grade_color",gdReq.getHeader("grade_color").getValue(i));
                insertData.put("order_con",gdReq.getHeader("order_con").getValue(i));
                insertData.put("h_weight",gdReq.getHeader("h_weight").getValue(i));
                insertData.put("h_scrap",gdReq.getHeader("h_scrap").getValue(i));
                insertData.put("t_weight",gdReq.getHeader("t_weight").getValue(i));
                System.out.println("rev_no 5: "+gdReq.getParam("rev_no"));
                try{
                    insertData.put("recycle_state",gdReq.getHeader("recycle_state").getComboHiddenValues()[gdReq.getHeader("recycle_state").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("recycle_state is null..");
                }
                insertData.put("recycle",gdReq.getHeader("recycle").getValue(i));
                insertData.put("die_no",gdReq.getHeader("die_no").getValue(i));
                insertData.put("cavity",gdReq.getHeader("cavity").getValue(i));
                insertData.put("m_su",gdReq.getHeader("m_su").getValue(i));
                insertData.put("mold_cost",gdReq.getHeader("mold_cost").getValue(i));
                System.out.println("rev_no 6: "+gdReq.getParam("rev_no"));
                try{
                    insertData.put("mold_c_type",gdReq.getHeader("mold_c_type").getComboHiddenValues()[gdReq.getHeader("mold_c_type").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("recycle_state is null..");
                }
                try{
                    insertData.put("make_type",gdReq.getHeader("make_type").getComboHiddenValues()[gdReq.getHeader("make_type").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("make_type is null..");
                }
                try{
                    insertData.put("pro_1",gdReq.getHeader("pro_1").getComboHiddenValues()[gdReq.getHeader("pro_1").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("pro_1 is null..");
                }
                System.out.println("rev_no 7: "+gdReq.getParam("rev_no"));
                insertData.put("ton",gdReq.getHeader("ton").getValue(i));
                insertData.put("spm",gdReq.getHeader("spm").getValue(i));
                try{
                    insertData.put("method_type",gdReq.getHeader("method_type").getComboHiddenValues()[gdReq.getHeader("method_type").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("method_type is null..");
                }
                try{
                    insertData.put("pro_level",gdReq.getHeader("pro_level").getComboHiddenValues()[gdReq.getHeader("pro_level").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("pro_level is null..");
                }
                insertData.put("pro_level_txt",gdReq.getHeader("pro_level_txt").getValue(i));
                insertData.put("line_su",gdReq.getHeader("line_su").getValue(i));
                insertData.put("sul_cost",gdReq.getHeader("sul_cost").getValue(i));
                try{
                    insertData.put("plating_type",gdReq.getHeader("plating_type").getComboHiddenValues()[gdReq.getHeader("plating_type").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("plating_type is null..");
                }
                System.out.println("rev_no 8: "+gdReq.getParam("rev_no"));
                try{
                    insertData.put("type_2",gdReq.getHeader("type_2").getComboHiddenValues()[gdReq.getHeader("type_2").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("type_2 is null..");
                }
                insertData.put("plating_cost",gdReq.getHeader("plating_cost").getValue(i));
                try{
                    insertData.put("type_1",gdReq.getHeader("type_1").getComboHiddenValues()[gdReq.getHeader("type_1").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("type_1 is null..");
                }
                try{
                    insertData.put("t_mone",gdReq.getHeader("t_mone").getComboHiddenValues()[gdReq.getHeader("t_mone").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("t_mone is null..");
                }
                insertData.put("type_cost",gdReq.getHeader("type_cost").getValue(i));
                try{
                    insertData.put("t_order",gdReq.getHeader("t_order").getComboHiddenValues()[gdReq.getHeader("t_order").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("t_order is null..");
                }
                try{
                    insertData.put("pack_type",gdReq.getHeader("pack_type").getComboHiddenValues()[gdReq.getHeader("pack_type").getSelectedIndex(i)]);
                }catch(Exception e){
                    System.out.println("pack_type is null..");
                }
                insertData.put("in_pack",gdReq.getHeader("in_pack").getValue(i));
                insertData.put("out_pack",gdReq.getHeader("out_pack").getValue(i));
                insertData.put("distri_cost",gdReq.getHeader("distri_cost").getValue(i));
                insertData.put("etc_cost",gdReq.getHeader("etc_cost").getValue(i));
                insertData.put("yazaki_ro",gdReq.getHeader("yazaki_ro").getValue(i));
                insertData.put("part_note",gdReq.getHeader("part_note").getValue(i));
                insertData.put("car_type",gdReq.getHeader("car_type").getValue(i));
                insertData.put("p_su_chk",gdReq.getHeader("p_su_chk").getValue(i));
                insertData.put("su_year_1",gdReq.getHeader("su_year_1").getValue(i));
                insertData.put("su_year_2",gdReq.getHeader("su_year_2").getValue(i));
                insertData.put("su_year_3",gdReq.getHeader("su_year_3").getValue(i));
                insertData.put("su_year_4",gdReq.getHeader("su_year_4").getValue(i));
                insertData.put("su_year_5",gdReq.getHeader("su_year_5").getValue(i));
                insertData.put("su_year_6",gdReq.getHeader("su_year_6").getValue(i));
                insertData.put("su_year_7",gdReq.getHeader("su_year_7").getValue(i));
                insertData.put("su_year_8",gdReq.getHeader("su_year_8").getValue(i));
                insertData.put("p_su_year_1",gdReq.getHeader("p_su_year_1").getValue(i));
                insertData.put("p_su_year_2",gdReq.getHeader("p_su_year_2").getValue(i));
                insertData.put("p_su_year_3",gdReq.getHeader("p_su_year_3").getValue(i));
                insertData.put("p_su_year_4",gdReq.getHeader("p_su_year_4").getValue(i));
                insertData.put("p_su_year_5",gdReq.getHeader("p_su_year_5").getValue(i));
                insertData.put("p_su_year_6",gdReq.getHeader("p_su_year_6").getValue(i));
                insertData.put("p_su_year_7",gdReq.getHeader("p_su_year_7").getValue(i));
                insertData.put("p_su_year_8",gdReq.getHeader("p_su_year_8").getValue(i));
                insertData.put("client_cost",gdReq.getHeader("client_cost").getValue(i));
                insertData.put("ket_cost",gdReq.getHeader("ket_cost").getValue(i));
                insertData.put("target_cost",gdReq.getHeader("target_cost").getValue(i));
                insertData.put("store",gdReq.getHeader("store").getValue(i));
                insertData.put("dest",gdReq.getHeader("dest").getValue(i));
                insertData.put("royalty",gdReq.getHeader("royalty").getValue(i));
                insertData.put("USD_rate",gdReq.getHeader("USD_rate").getValue(i));
                insertData.put("EURO_rate",gdReq.getHeader("EURO_rate").getValue(i));
                insertData.put("YEN_rate",gdReq.getHeader("YEN_rate").getValue(i));
                insertData.put("CNY_rate",gdReq.getHeader("CNY_rate").getValue(i));
                insertData.put("lme_ton",gdReq.getHeader("lme_ton").getValue(i));
                insertData.put("u_ex_rate",gdReq.getHeader("u_ex_rate").getValue(i));
                insertData.put("y_ex_rate",gdReq.getHeader("y_ex_rate").getValue(i));

                insertData.put("meterial_cost",gdReq4.getHeader("meterial_cost").getValue(j));
                insertData.put("loss",gdReq4.getHeader("loss").getValue(j));
                insertData.put("scrap_cost",gdReq4.getHeader("scrap_cost").getValue(j));
                insertData.put("scrap_rate",gdReq4.getHeader("scrap_rate").getValue(j));
                insertData.put("m_total_cost",gdReq4.getHeader("m_total_cost").getValue(j));
                insertData.put("output",gdReq4.getHeader("output").getValue(j));
                insertData.put("rate",gdReq4.getHeader("rate").getValue(j));
                insertData.put("eff_value",gdReq4.getHeader("eff_value").getValue(j));
                insertData.put("labor_cost",gdReq4.getHeader("labor_cost").getValue(j));
                insertData.put("jun_cost",gdReq4.getHeader("jun_cost").getValue(j));
                insertData.put("ma_depr",gdReq4.getHeader("ma_depr").getValue(j));
                insertData.put("tabalu",gdReq4.getHeader("tabalu").getValue(j));
                insertData.put("m_upkeep",gdReq4.getHeader("m_upkeep").getValue(j));
                insertData.put("etc_expense",gdReq4.getHeader("etc_expense").getValue(j));
                insertData.put("pack_cost",gdReq4.getHeader("pack_cost").getValue(j));
                insertData.put("out_cost",gdReq4.getHeader("out_cost").getValue(j));
                insertData.put("total_expense",gdReq4.getHeader("total_expense").getValue(j));
                insertData.put("overhead",gdReq4.getHeader("overhead").getValue(j));
                insertData.put("gita_cost",gdReq4.getHeader("gita_cost").getValue(j));
                insertData.put("common_cost",gdReq4.getHeader("common_cost").getValue(j));
                insertData.put("yzk_depr",gdReq4.getHeader("yzk_depr").getValue(j));
                System.out.println("rev_no 9: "+gdReq.getParam("rev_no"));
                try{
                    insertData.put("mold_type",gdReq4.getHeader("mold_type").getComboHiddenValues()[gdReq4.getHeader("mold_type").getSelectedIndex(j)]);
                }catch(Exception e){
                    System.out.println("mold_type is null..");
                }
                insertData.put("start_depr",gdReq4.getHeader("start_depr").getValue(j));
                insertData.put("pro_depr",gdReq4.getHeader("pro_depr").getValue(j));
                insertData.put("etc_depr",gdReq4.getHeader("etc_depr").getValue(j));
                insertData.put("depr_cost",gdReq4.getHeader("depr_cost").getValue(j));
                insertData.put("jae_cost",gdReq4.getHeader("jae_cost").getValue(j));
                insertData.put("ge_cost",gdReq4.getHeader("ge_cost").getValue(j));
                insertData.put("ad_cost",gdReq4.getHeader("ad_cost").getValue(j));
                insertData.put("rnd_cost",gdReq4.getHeader("rnd_cost").getValue(j));
                insertData.put("qu_cost",gdReq4.getHeader("qu_cost").getValue(j));
                insertData.put("tariff",gdReq4.getHeader("tariff").getValue(j));
                insertData.put("royalty_cost",gdReq4.getHeader("royalty_cost").getValue(j));
                insertData.put("dis_cost",gdReq4.getHeader("dis_cost").getValue(j));
                insertData.put("actual_cost",gdReq4.getHeader("actual_cost").getValue(j));
                insertData.put("earn_rate",gdReq4.getHeader("earn_rate").getValue(j));
                System.out.println("rev_no 10: "+gdReq.getParam("rev_no"));
                insertData.put("pl_meterial_cost",gdReq4.getHeader("meterial_cost").getValue(j+1));
                insertData.put("pl_loss",gdReq4.getHeader("loss").getValue(j+1));
                insertData.put("pl_scrap_cost",gdReq4.getHeader("scrap_cost").getValue(j+1));
                insertData.put("pl_m_total_cost",gdReq4.getHeader("m_total_cost").getValue(j+1));
                insertData.put("pl_output",gdReq4.getHeader("output").getValue(j+1));
                insertData.put("pl_rate",gdReq4.getHeader("rate").getValue(j+1));
                insertData.put("pl_labor_cost",gdReq4.getHeader("labor_cost").getValue(j+1));
                insertData.put("pl_jun_cost",gdReq4.getHeader("jun_cost").getValue(j+1));
                insertData.put("pl_ma_depr",gdReq4.getHeader("ma_depr").getValue(j+1));
                insertData.put("pl_tabalu",gdReq4.getHeader("tabalu").getValue(j+1));
                insertData.put("pl_m_upkeep",gdReq4.getHeader("m_upkeep").getValue(j+1));
                insertData.put("pl_total_expense",gdReq4.getHeader("total_expense").getValue(j+1));
                insertData.put("pl_overhead",gdReq4.getHeader("overhead").getValue(j+1));
                insertData.put("pl_common_cost",gdReq4.getHeader("common_cost").getValue(j+1));
                insertData.put("pl_yzk_depr",gdReq4.getHeader("yzk_depr").getValue(j+1));
                insertData.put("pl_start_depr",gdReq4.getHeader("start_depr").getValue(j+1));
                insertData.put("pl_pro_depr",gdReq4.getHeader("pro_depr").getValue(j+1));
                insertData.put("pl_etc_depr",gdReq4.getHeader("etc_depr").getValue(j+1));
                insertData.put("pl_jae_cost",gdReq4.getHeader("jae_cost").getValue(j+1));
                insertData.put("pl_ge_cost",gdReq4.getHeader("ge_cost").getValue(j+1));
                insertData.put("pl_rnd_cost",gdReq4.getHeader("rnd_cost").getValue(j+1));
                insertData.put("pl_tariff",gdReq4.getHeader("tariff").getValue(j+1));
                insertData.put("pl_royalty_cost",gdReq4.getHeader("royalty_cost").getValue(j+1));
                insertData.put("pl_actual_cost",gdReq4.getHeader("actual_cost").getValue(j+1));
                insertData.put("case_count",case_count);

                insertDataList.add(insertData);
                cnt = workDao.insertCostWork(insertDataList);
                insertDataList.clear();

                j = j+2;
            }
            if(conn!=null) {conn.close();}
            gdRes.addParam("mode", "modi");
            String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
            gdRes.setMessage(msg);
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }
        return gdRes;
    }

    /* Save3 SAVE */
    public GridData deleteQry(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("SELECTED").getRowCount();
            ArrayList deleteDataList = new ArrayList(rowCount);

            Hashtable<String, String> deleteData = null;
            deleteData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostWorkDao workDao = new CostWorkDao(conn);

            String pk_cw = "";
            String pk_cr_group = "";
            String case_type_admin = "";

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                deleteData.put("pk_cw",  gdReq.getHeader("pk_cw").getValue(i));
                deleteData.put("pk_cr_group",  gdReq.getHeader("pk_cr_group").getValue(i));
                deleteData.put("case_type_admin",  gdReq.getHeader("case_type_admin").getComboHiddenValues()[gdReq.getHeader("case_type_admin").getSelectedIndex(i)]);
                deleteDataList.add(deleteData);
                cnt = workDao.DeleteCostWork(deleteDataList);
                deleteDataList.clear();
            }
            if(conn!=null) {conn.close();}
            gdRes.addParam("mode", "delete");
            String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
            gdRes.setMessage(msg);
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }
        return gdRes;
    }

    public GridData caseSaveQry(GridData gdReq) throws Exception {
        GridData gdRes = new GridData();

        Connection conn = null;
        conn = DBUtil.getConnection();
        CostWorkDao workDao = new CostWorkDao(conn);

        int rowcount = gdReq.getHeader("pk_cw").getRowCount();

        ArrayList updateDataList = new ArrayList(0);
        Hashtable<String, String> updateData = null;
        updateData = new Hashtable<String, String>();

        for (int i = 0; i < rowcount; i++) {
            updateData.put("pk_cw", gdReq.getHeader("pk_cw").getValue(i));
            updateData.put("case_type_admin",gdReq.getHeader("case_type_admin").getComboHiddenValues()[gdReq.getHeader("case_type_admin").getSelectedIndex(i)]);
            updateData.put("case_infor", gdReq.getHeader("case_infor").getValue(i));
            updateDataList.add(updateData);
            workDao.UpdateCostWork5(updateDataList);
            updateDataList.clear();
        }


        gdRes.addParam("mode", "case_update");
        String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
        gdRes.setMessage(msg);
        gdRes.setStatus("true");

        return gdRes;
    }
}