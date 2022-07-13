<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.net.URLDecoder,java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostReportCtl"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
    String Ename 	= StringUtil.checkNull((String)session.getAttribute("Ename"));
    String Dname	    = StringUtil.checkNull((String)session.getAttribute("Dname"));
    String emp_no	= StringUtil.checkNull((String)session.getAttribute("emp_no"));
    String dept_no	= StringUtil.checkNull((String)session.getAttribute("dept_no"));
    String K_pass	= StringUtil.checkNull((String)session.getAttribute("K_pass"));
    String position	= StringUtil.checkNull((String)session.getAttribute("position"));
    String login_id	= StringUtil.checkNull((String)session.getAttribute("login_id"));//권한 관련 id
    String cost_id	= StringUtil.checkNull((String)session.getAttribute("cost_id"));//user id
    String group_m	= StringUtil.checkNull((String)session.getAttribute("group_m"));
    if(group_m.equals("G")){
        position = "그룹장";
    }
    String visitor = StringUtil.checkNull(request.getParameter("visitor"));
    String rev_no = StringUtil.checkNull(request.getParameter("rev_no"));
    String user_case_count = StringUtil.checkNull(request.getParameter("user_case_count"));
    String pk_cr_group = StringUtil.checkNull(request.getParameter("pk_cr_group"));;
    String cost_report_add = StringUtil.checkNull(request.getParameter("cost_report_add"));

    String table_row = StringUtil.checkNull(request.getParameter("table_row"));
    String report_pk = StringUtil.checkNull(request.getParameter("report_pk"));
    String page_no  = StringUtil.checkNull(request.getParameter("page_no"));
    String select_case = StringUtil.checkNull(request.getParameter("select_case"));
    String select_name = StringUtil.checkNull(request.getParameter("select_name"));

    if(("1".equals(visitor) || "2".equals(visitor)) && !"".equals(Ename)){
        login_id = "pro_man";
        session.setAttribute("login_id", login_id);
    }else if("3".equals(visitor) && !"".equals(Ename)){
        login_id = "business";
        session.setAttribute("id", login_id);
    }else if(("1".equals(visitor) || "2".equals(visitor) || "3".equals(visitor)) && Ename.equals("")){
        response.sendRedirect("http://plm.ket.com/cost_main.jsp");
    }

    String pk_cw   		= StringUtil.checkNull(request.getParameter("pk_cw")         );
    String chk_list		= StringUtil.checkNull(request.getParameter("chk_list")      );
    String note_1  		= StringUtil.checkNull(request.getParameter("note_1")        );
    String note_2  		= StringUtil.checkNull(request.getParameter("note_2")        );
    String note_3  		= StringUtil.checkNull(request.getParameter("note_3")        );
    String note_4 		= StringUtil.checkNull(request.getParameter("note_4")        );
    String note_5 		= StringUtil.checkNull(request.getParameter("note_5")        );
    String note_5_1 	= StringUtil.checkNull(request.getParameter("note_5_1")      );
    String case_1_note	= StringUtil.checkNull(request.getParameter("case_1_note")   );
    String case_2_note 	= StringUtil.checkNull(request.getParameter("case_2_note")   );
    String case_3_note 	= StringUtil.checkNull(request.getParameter("case_3_note")   );
    String case_to_note_1	= StringUtil.checkNull(request.getParameter("case_to_note_1"));
    String case_to_note_2	= StringUtil.checkNull(request.getParameter("case_to_note_2"));
    String case_to_note_3 	= StringUtil.checkNull(request.getParameter("case_to_note_3"));
    String us_change 	= StringUtil.checkNull(request.getParameter("us_change")     );

    String[] start_pro = new String[70];
    String[] store = new String[70];
    String[] dest = new String[70];
    String[] to_cost = new String[70];
    String[] mold_count = new String[10];
    String[] press_count = new String[10];
    String[] line_count = new String[10];
    String[] pack_count = new String[10];
    String[] repack_count = new String[10];
    String[] mold_total = new String[5];
    String[] press_total = new String[5];
    String[] line_total = new String[5];
    String[] pack_total = new String[5];
    String[] repack_total = new String[5];
    String[][] group_no = new String[70][20];
    String[][] a_pk_cw = new String[70][20];
    String[][] t_pk_cw = new String[70][20];
    String[] pk_cw_array = new String[70];
    String[] part_name = new String[20];
    String[] part_type = new String[20];
    String[] part_no = new String[20];
    String[] su_year_1 = new String[70];
    String[] su_year_2 = new String[70];
    String[] su_year_3 = new String[70];
    String[] su_year_4 = new String[70];
    String[] su_year_5 = new String[70];
    String[] su_year_6 = new String[70];
    String[] su_year_7 = new String[70];
    String[] su_year_8 = new String[70];
    String[][] a_su_count = new String[70][20];
    String[][] a_su_year_1 = new String[70][20];
    String[][] a_su_year_2 = new String[70][20];
    String[][] a_su_year_3 = new String[70][20];
    String[][] a_su_year_4 = new String[70][20];
    String[][] a_su_year_5 = new String[70][20];
    String[][] a_su_year_6 = new String[70][20];
    String[][] a_su_year_7 = new String[70][20];
    String[][] a_su_year_8 = new String[70][20];
    String[][] t_su_year_1 = new String[70][40];
    String[][] t_su_year_2 = new String[70][40];
    String[][] t_su_year_3 = new String[70][40];
    String[][] t_su_year_4 = new String[70][40];
    String[][] t_su_year_5 = new String[70][40];
    String[][] t_su_year_6 = new String[70][40];
    String[][] t_su_year_7 = new String[70][40];
    String[][] t_su_year_8 = new String[70][40];
    String[] t_total_sales_1 = new String[70];    //매출액
    String[] t_total_sales_2 = new String[70];
    String[] t_total_sales_3 = new String[70];
    String[] t_total_sales_4 = new String[70];
    String[] t_total_sales_5 = new String[70];
    String[] t_total_sales_6 = new String[70];
    String[] t_total_sales_7 = new String[70];
    String[] t_total_sales_8 = new String[70];
    String[] t_profit_1 = new String[70];    //영업이익
    String[] t_profit_2 = new String[70];
    String[] t_profit_3 = new String[70];
    String[] t_profit_4 = new String[70];
    String[] t_profit_5 = new String[70];
    String[] t_profit_6 = new String[70];
    String[] t_profit_7 = new String[70];
    String[] t_profit_8 = new String[70];
    String[] t_per_profit_1 = new String[70];    //영업이익율
    String[] t_per_profit_2 = new String[70];
    String[] t_per_profit_3 = new String[70];
    String[] t_per_profit_4 = new String[70];
    String[] t_per_profit_5 = new String[70];
    String[] t_per_profit_6 = new String[70];
    String[] t_per_profit_7 = new String[70];
    String[] t_per_profit_8 = new String[70];
    String[] make_type = new String[20];
    String[] client_cost = new String[20];
    String[] pro_usage = new String[20];
    String[][] ket_cost = new String[70][20];
    String[] room_earn = new String[20];
    String[] target_cost = new String[20];
    String[] usage = new String[70];
    String[] pro_type = new String[50];
    String[][] actual_cost = new String[70][20];    //판매기준총원가
    String[][] earn_rate = new String[70][20];    //수익률
    String[][] t_actual_cost = new String[70][20];    //판매기준총원가
    String[][] t_earn_rate = new String[70][20];    //수익률
    String[][] FK_cost_request = new String[70][20];
    String[] avg_su = new String[70];    //총판매기획수량
    String[][] mold_c_type = new String[70][20];
    String[][][] A_cw = new String[50][80][50];

    String[] chk_cw = {};
    int C_count = 0;
    int case_s_count = 0;
    int p=0;
    String A = "";
    String m_co_chk = "";
    String depr_cost = "";
    String m_su = "";
    String m_maker = "";
    String grade_name = "";
    String grade_color = "";
    String mold_cost = "";
    String etc_cost = "";
    String yazaki_ro = "";
    String sul_cost = "";
    String line_su = "";
    String suji_value = "";
    String su_stan_day = "";

    String case_count_1 = "";
    String case_count_2 = "";
    String pjt_name = "";
    String f_name = "";
    String w_name = "";
    String a_name = "";
    String pjt_no = "";
    String car_type = "";
    String customer_F = "";
    String team = "";
    String app_part = "";
    String report_dest = "";
    String request_txt = "";
    String dev_step = "";
    String lme_cu = "";
    String u_ex_rate = "";
    String pro_1 = "";
    String eff_value = "";

    String pass_state = "";
    String FK_cost_request_s = "";
    String pack_type = "";
    String m_depr_stan = "";
    String p_depr_stan = "";
    String L_depr_stan = "";
    String pack_depr_stan = "";
    String repack_depr_stan = "";
    String tocost_year = "";
    String select_cost = "";

    String actual_cost_sum_1 = "";
    String earn_rate_sum_1 = "";
    String actual_cost_sum_2 = "";
    String earn_rate_sum_2 = "";
    String actual_cost_sum_3 = "";
    String earn_rate_sum_3 = "";
    String total_sales_1 = "";
    String total_sales_2 = "";
    String total_sales_3 = "";
    String total_sales_4 = "";
    String total_sales_5 = "";
    String total_sales_6 = "";
    String total_sales_7 = "";
    String total_sales_8 = "";

    String profit_1 = "";
    String profit_2 = "";
    String profit_3 = "";
    String profit_4 = "";
    String profit_5 = "";
    String profit_6 = "";
    String profit_7 = "";
    String profit_8 = "";
    String per_profit_1 = "";
    String per_profit_2 = "";
    String per_profit_3 = "";
    String per_profit_4 = "";
    String per_profit_5 = "";
    String per_profit_6 = "";
    String per_profit_7 = "";
    String per_profit_8 = "";
    String file_1 = "";

    String sum_sales = "";
    String sum_profit = "";
    String sum_per_p = "";
    int P_count = 0;
    String ket_cost_value = "";
    String su_year_state = "";
    String table_row_count = "0";
    String client_cost_sum = "";
    String ket_cost_sum = "";
    String file_2 = "";
    String file_3 = "";
    String file_4 = "";
    String file_5 = "";
    String file_6 = "";
    String file_2_name = "";
    String file_3_name = "";
    String file_4_name = "";
    String file_5_name = "";
    String file_6_name = "";

    String AA = "";
    String AB = "";
    String AC = "";
    String file_1_history = "";
    String file_1WholePath = "";

    String f_day 				= "";
    String Leader_day 		= "";
    String Leader_name 	= "";
    String approval 			= "";
    String step_no 			= "";
    String JB_day			= "";
    String JB_name			= "";
    String r_owner_day		= "";
    String r_pre_day			= "";
    String p_leader_day	= "";
    String p_leader_name	= "";
    String Gr_day			= "";
    String Gr_name			= "";

    CostReportCtl reportCtl = new CostReportCtl();
    ArrayList caseCountList = null;
    Hashtable caseCountItem = null;
    ArrayList costWorkMoreList = null;
    Hashtable costWorkMoreItem = null;
    ArrayList costWorkAllList = null;
    Hashtable costWorkAllItem = null;
    ArrayList costReportAllList = null;
    Hashtable costReportAllItem = null;
    ArrayList FKCostWorkList = null;
    Hashtable FKCostWorkItem = null;
    ArrayList costWorkGroupNoList = null;
    Hashtable costWorkGroupNoItem = null;


    /*if(!"3".equals(page_no) && !"ok".equals(cost_report_add)){
        chk_list = reportCtl.getreportCostWorkPk(pk_cr_group);
    }*/

    if(!"3".equals(page_no) && (!"ok".equals(cost_report_add) && !"".equals(chk_list))){    //처음 작성 시
        if(!"".equals(chk_list)){
            chk_cw = chk_list.split(",");

            C_count = chk_cw.length;

            String[] chk_count_1 = new String[50];
            String[] chk_count_2 = new String[50];

            for(int j=0; j<Integer.parseInt(table_row);j++){
                caseCountList = reportCtl.reportCostWorkCaseCountList(pk_cr_group, rev_no);
                for(int i=0; i < caseCountList.size(); i++){
                    caseCountItem = (Hashtable)caseCountList.get(i);
                    chk_count_1[j] = StringUtil.checkNull((String)caseCountItem.get("case_count_1"));
                    chk_count_2[j] = StringUtil.checkNull((String)caseCountItem.get("case_count_2"));
                }
            }

            for(int j=0; j<C_count;j++){
                A_cw[j][0][0] = chk_cw[j];
                for(int i=1; i<=Integer.parseInt(StringUtil.checkNullZero(chk_count_1[j]));i++){
                    for(int k=0; k<=Integer.parseInt(StringUtil.checkNullZero(chk_count_2[j]));k++){
                        A_cw[j][i][k] = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(chk_cw[j])) + 1);
                        chk_cw[j] = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(chk_cw[j])) + 1);
                    }
                }
            }

            if(Integer.parseInt(table_row) == C_count){
                case_s_count = 0;
                A = "OK";
            }else{
                case_s_count = C_count / Integer.parseInt(table_row);
                A = "NO";
            }

            for(int t=0;t<C_count;t++){
                for(int i=0;i<=Integer.parseInt(StringUtil.checkNullZero(chk_count_1[t]));i++){
                    for(int k=0;k<=Integer.parseInt(StringUtil.checkNullZero(chk_count_2[t]));k++){
                        for(int j=0;j<=case_s_count;j++){
                            costWorkMoreList = reportCtl.reportCostWorkMoreList(A_cw[t][i][k]);
                            for(int m=0; m < costWorkMoreList.size(); m++){
                                costWorkMoreItem = (Hashtable)costWorkMoreList.get(m);
                                m_co_chk = StringUtil.checkNull((String)costWorkMoreItem.get("m_co_chk"));
                                pro_type[t] = StringUtil.checkNull((String)costWorkMoreItem.get("pro_type"));
                                m_su = StringUtil.checkNull((String)costWorkMoreItem.get("m_su"));
                                mold_cost = StringUtil.checkNull((String)costWorkMoreItem.get("mold_cost"));

                                etc_cost = StringUtil.checkNull((String)costWorkMoreItem.get("etc_cost"));
                                yazaki_ro = StringUtil.checkNull((String)costWorkMoreItem.get("yazaki_ro"));
                                sul_cost = StringUtil.checkNull((String)costWorkMoreItem.get("sul_cost"));
                                line_su = StringUtil.checkNull((String)costWorkMoreItem.get("line_su"));

                                if(!"".equals((String)costWorkMoreItem.get("depr_cost"))){
                                    if(!"".equals(depr_cost)){
                                        depr_cost = Double.toString( StringUtil.nullDouble(depr_cost) + Double.parseDouble(StringUtil.checkNullZero((String)costWorkMoreItem.get("depr_cost")) ));
                                    }else{
                                        depr_cost = StringUtil.checkNull((String)costWorkMoreItem.get("depr_cost"));
                                    }
                                }else{
                                    if(!"".equals(depr_cost)){
                                        depr_cost = depr_cost;
                                    }else{
                                        depr_cost = "0";
                                    }
                                }

                                if(!"HSG".equals(pro_type[t]) || "Insert".equals(pro_type[t])){
                                    if(!"".equals((String)costWorkMoreItem.get("m_su"))){
                                        if(!"".equals(mold_count[j])){ // Mold 수랑

                                        	try{
                                            	mold_count[j] = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(mold_count[j])) + Integer.parseInt(StringUtil.checkNullZero(m_su).trim()));
                                        	}catch(Exception e){
                                        		e.printStackTrace();
												if(!"".equals(m_su) && m_su != null){
													if(m_su.trim().length() > 0){
														mold_count[j] = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(mold_count[j])) + StringUtil.nullDouble(m_su).intValue());
													}
												}
                                        	}
                                        }else{ // Mold 수량
                                            mold_count[j] = m_su;
                                        }
                                    }
                                }else if("TML".equals(pro_type[j])){
                                    if(!"".equals(StringUtil.checkNull(m_su))){
                                        if(!"".equals(StringUtil.checkNull(press_count[j]))){ // Press 수량
                                            press_count[j] = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(press_count[j])) + Integer.parseInt(StringUtil.checkNullZero(m_su)));
                                        }else{ // Press 수량
                                            press_count[j] = m_su;
                                        }
                                    }
                                }

                                if(!"yes".equals(m_co_chk)){
                                    if("HSG".equals(pro_type[t]) || "Insert".equals(pro_type[t])){
                                        if(!"".equals(StringUtil.checkNull(mold_cost))){
                                            if(!"".equals(StringUtil.checkNull(mold_total[j]))){ // Mold비
                                                mold_total[j] = Double.toString(StringUtil.nullDouble(mold_total[j]) + (StringUtil.nullDouble(mold_cost) * StringUtil.nullDouble(m_su)) );
                                                //mold_total[j] = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(mold_total[j])) + (Integer.parseInt(StringUtil.checkNullZero(mold_cost)) * Integer.parseInt(StringUtil.checkNullZero(m_su))));
                                            }else{ // Mold비
                                                mold_total[j] = Double.toString(StringUtil.nullDouble(mold_cost) * StringUtil.nullDouble(m_su));
                                            }

                                            if(!"".equals(StringUtil.checkNull(mold_count[j]))){
                                                mold_count[j] = mold_count[j];
                                            }

                                            suji_value = "ok";
                                            m_maker = StringUtil.checkNull((String)costWorkMoreItem.get("m_maker"));
                                            grade_name = StringUtil.checkNull((String)costWorkMoreItem.get("grade_name"));
                                            grade_color = StringUtil.checkNull((String)costWorkMoreItem.get("grade_color"));
                                        }
                                    }else if("TML".equals(pro_type[t])){
                                        if(!"".equals(StringUtil.checkNull(mold_cost))){
                                            if(!"".equals(StringUtil.checkNull(press_total[j]))){ // Press비
                                                press_total[j] = Double.toString(StringUtil.nullDouble(press_total[j]) + (StringUtil.nullDouble(mold_cost) * StringUtil.nullDouble(m_su)));
                                                //press_total[j] = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(press_total[j])) + (Integer.parseInt(StringUtil.checkNullZero(mold_cost)) * Integer.parseInt(StringUtil.checkNullZero(m_su))));
                                            }else{
                                                //press_total[j] = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(mold_cost)) * Integer.parseInt(StringUtil.checkNullZero(m_su)));
                                                press_total[j] = Double.toString(StringUtil.nullDouble(mold_cost) * StringUtil.nullDouble(m_su));
                                            }

                                            if(!"".equals(StringUtil.checkNull(press_count[j]))){
                                                press_count[j] = press_count[j];
                                            }
                                        }
                                    }
                                }

                                if(!"".equals(etc_cost)){
                                    if(!"".equals(pack_total[j])){
                                        //pack_total[j] =Integer.toString(Integer.parseInt(StringUtil.checkNullZero(pack_total[j])) + Integer.parseInt(StringUtil.checkNullZero(etc_cost)));
                                        pack_total[j] =Double.toString(Double.parseDouble(StringUtil.checkNullZero(pack_total[j])) + Double.parseDouble(StringUtil.checkNullZero(etc_cost)));
                                    }else{
                                        pack_total[j] = etc_cost;
                                    }
                                }

                                if(!"".equals(yazaki_ro)){
                                    if(!"".equals(StringUtil.checkNull(repack_total[j]))){
                                        //repack_total[j] =Integer.toString(Integer.parseInt(StringUtil.checkNullZero(repack_total[j])) + Integer.parseInt(StringUtil.checkNullZero(yazaki_ro)));
                                        repack_total[j] =Double.toString(Double.parseDouble(StringUtil.checkNullZero(repack_total[j])) + Double.parseDouble(StringUtil.checkNullZero(yazaki_ro)));
                                    }else{
                                        repack_total[j] = yazaki_ro;
                                    }
                                }

                                //299
                                if("assy".equals(pro_type[t]) || "Insert".equals(pro_type[t])){
                                    if(!"".equals(StringUtil.checkNull(sul_cost))){
                                        if(!"".equals(StringUtil.checkNull(line_total[j]))){
                                            line_total[j] =Double.toString(Double.parseDouble(StringUtil.checkNullZero(line_total[j])) + Double.parseDouble(StringUtil.checkNullZero(sul_cost)));
                                        }else{
                                            line_total[j] = sul_cost;
                                        }
                                    }

                                    line_count[j] = line_su; // 수동 조립설비
                                    if(!"".equals(line_count[j])){
                                        line_count[j] = line_count[j]+" Line";
                                    }
                                } // pro_type : assy, insert if end
                            } // costWorkMoreList for end
                        } // case_s_count for end
                    } // chk_count_2 for end
                } // chk_count_1 for end
            } // C_count for end

            if("ok".equals(suji_value)){
                su_stan_day = reportCtl.suStanDay(m_maker, grade_name, grade_color);
                su_stan_day = su_stan_day+" 기준";
            }
        }
        p = 0;
        String k = "0";
        int i = 1;

        for(int j=0; j<Integer.parseInt(table_row); j++){
            if(j < 9){
                k = "00"+Integer.toString(i);
            }else if(j < 99){
                k = "0" + Integer.toString(i);
            }else if(j < 999){
                k = Integer.toString(i);
            }
            // 368
            costWorkAllList = reportCtl.reportCostWorkAllList(pk_cr_group, k, rev_no);
            String temp = "→";
            String temp2 = "→";
            for(int m=0; m < costWorkAllList.size(); m++){
                costWorkAllItem = (Hashtable)costWorkAllList.get(m);
                case_count_1 = StringUtil.checkNull((String)costWorkAllItem.get("case_count_1"));
                case_count_2 = StringUtil.checkNull((String)costWorkAllItem.get("case_count_2"));
                pjt_name = StringUtil.checkNull((String)costWorkAllItem.get("pjt_name"));
                f_name = StringUtil.checkNull((String)costWorkAllItem.get("f_name"));
                w_name = StringUtil.checkNull((String)costWorkAllItem.get("w_name"));
                a_name = StringUtil.checkNull((String)costWorkAllItem.get("a_name"));
                pjt_no = StringUtil.checkNull((String)costWorkAllItem.get("pjt_no"));
                car_type = StringUtil.checkNull((String)costWorkAllItem.get("car_type"));
                customer_F = StringUtil.checkNull((String)costWorkAllItem.get("customer_F"));
                team = StringUtil.checkNull((String)costWorkAllItem.get("team"));
                step_no = StringUtil.checkNull((String)costWorkAllItem.get("step_no"));
                app_part = StringUtil.checkNull((String)costWorkAllItem.get("app_part"));
                target_cost[j] = StringUtil.checkNull((String)costWorkAllItem.get("target_cost"));
                start_pro[j] = StringUtil.checkNull((String)costWorkAllItem.get("start_pro"));
                store[j] = StringUtil.checkNull((String)costWorkAllItem.get("store"));
                dest[j] = StringUtil.checkNull((String)costWorkAllItem.get("dest"));
                // 판매기준 총원가
                actual_cost[j][p] = StringUtil.checkNull((String)costWorkAllItem.get("actual_cost"));

                if(StringUtil.checkNull(start_pro[i]).equals("")){
                    start_pro[i] = "";
                    temp = "";
                }else{
                    temp = "→";
                }
                if(StringUtil.checkNull(store[i]).equals("")){
                    store[i] = "";
                    temp2 = "";
                }else{
                    temp2 = "→";
                }
                if(StringUtil.checkNull(dest[i]).equals("")){
                    dest[i] = "";
                }
                report_dest = start_pro[i]+temp+store[i]+temp2+dest[i];

                request_txt = StringUtil.checkNull((String)costWorkAllItem.get("request_txt"));
                f_day = StringUtil.checkNull((String)costWorkAllItem.get("f_day"));
                Leader_day = StringUtil.checkNull((String)costWorkAllItem.get("Leader_day"));
                Leader_name = StringUtil.checkNull((String)costWorkAllItem.get("Leader_nam"));
                approval = StringUtil.checkNull((String)costWorkAllItem.get("approval"));
                dev_step = StringUtil.checkNull((String)costWorkAllItem.get("dev_step"));
                lme_cu = StringUtil.checkNull((String)costWorkAllItem.get("lme_ton"));
                u_ex_rate = StringUtil.checkNull((String)costWorkAllItem.get("u_ex_rate"));

                pk_cw_array[j] = StringUtil.checkNull((String)costWorkAllItem.get("pk_cw"));
                part_name[j] = StringUtil.checkNull((String)costWorkAllItem.get("part_name"));
                part_type[j] = StringUtil.checkNull((String)costWorkAllItem.get("part_type"));
                part_no[j] = StringUtil.checkNull((String)costWorkAllItem.get("part_no"));
                dev_step = StringUtil.checkNull((String)costWorkAllItem.get("dev_step"));
                su_year_1[j] = StringUtil.checkNull((String)costWorkAllItem.get("su_year_1"));
                su_year_2[j] = StringUtil.checkNull((String)costWorkAllItem.get("su_year_2"));
                su_year_3[j] = StringUtil.checkNull((String)costWorkAllItem.get("su_year_3"));
                su_year_4[j] = StringUtil.checkNull((String)costWorkAllItem.get("su_year_4"));
                su_year_5[j] = StringUtil.checkNull((String)costWorkAllItem.get("su_year_5"));
                su_year_6[j] = StringUtil.checkNull((String)costWorkAllItem.get("su_year_6"));
                su_year_7[j] = StringUtil.checkNull((String)costWorkAllItem.get("su_year_7"));
                su_year_8[j] = StringUtil.checkNull((String)costWorkAllItem.get("su_year_8"));
                client_cost[j] = StringUtil.checkNull((String)costWorkAllItem.get("client_cost"));
                ket_cost[j][p] = StringUtil.checkNull((String)costWorkAllItem.get("ket_cost"));
                target_cost[j] = StringUtil.checkNull((String)costWorkAllItem.get("target_cost"));
                usage[j] = StringUtil.checkNull((String)costWorkAllItem.get("usage"));
                pro_type[j] = StringUtil.checkNull((String)costWorkAllItem.get("pro_type"));
                pro_1 = StringUtil.checkNull((String)costWorkAllItem.get("pro_1"));
                file_2 = "-";
                file_3 = "-";
                file_4 = "-";
                file_5 = "-";
                file_6 = "-";

                if(!"".equals(chk_list)){
                    //효율
                    eff_value =  StringUtil.checkNull((String)costWorkAllItem.get("eff_value"));
                }
                if(!"".equals(su_year_1[j])){
                    su_year_1[j] = Double.toString(StringUtil.nullDouble(su_year_1[j]) * StringUtil.nullDouble(usage[j]));
                }else{
                    su_year_1[j] = "0";
                }
                if(!"".equals(su_year_2[j])){
                    su_year_2[j] = Double.toString(StringUtil.nullDouble(su_year_2[j]) * StringUtil.nullDouble(usage[j]));
                }else{
                    su_year_2[j] = "0";
                }
                if(!"".equals(su_year_3[j])){
                    su_year_3[j] = Double.toString(StringUtil.nullDouble(su_year_3[j]) * StringUtil.nullDouble(usage[j]));
                }else{
                    su_year_3[j] = "0";
                }
                if(!"".equals(su_year_4[j])){
                    su_year_4[j] = Double.toString(StringUtil.nullDouble(su_year_4[j]) * StringUtil.nullDouble(usage[j]));
                }else{
                    su_year_4[j] = "0";
                }
                if(!"".equals(su_year_5[j])){
                    su_year_5[j] = Double.toString(StringUtil.nullDouble(su_year_5[j]) * StringUtil.nullDouble(usage[j]));
                }else{
                    su_year_5[j] = "0";
                }
                if(!"".equals(su_year_6[j])){
                    su_year_6[j] = Double.toString(StringUtil.nullDouble(su_year_6[j]) * StringUtil.nullDouble(usage[j]));
                }else{
                    su_year_6[j] = "0";
                }
                if(!"".equals(su_year_7[j])){
                    su_year_7[j] = Double.toString(StringUtil.nullDouble(su_year_7[j]) * StringUtil.nullDouble(usage[j]));
                }else{
                    su_year_7[j] = "0";
                }
                if(!"".equals(su_year_8[j])){
                    su_year_8[j] = Double.toString(StringUtil.nullDouble(su_year_8[j]) * StringUtil.nullDouble(usage[j]));
                }else{
                    su_year_8[j] = "0";
                }
                avg_su[j] = Double.toString(StringUtil.nullDouble(su_year_1[j])+StringUtil.nullDouble(su_year_2[j])+StringUtil.nullDouble(su_year_3[j])+StringUtil.nullDouble(su_year_4[j])+StringUtil.nullDouble(su_year_5[j])+StringUtil.nullDouble(su_year_6[j])+StringUtil.nullDouble(su_year_7[j])+StringUtil.nullDouble(su_year_8[j]));
            } // costWorkAllList For end
            i=i+1;
        } // table_row if end
    }else{    // 작성중일때
        //보고서 DB에 자료가 있는 경우

        if("ok".equals(select_case)){
            int j=0;

            costReportAllList = reportCtl.reportCostReportAllList(report_pk);
            for(int m=0; m < costReportAllList.size(); m++){
                costReportAllItem = (Hashtable)costReportAllList.get(m);
                pass_state = StringUtil.checkNull((String)costReportAllItem.get("pass_type"));

                f_day					= StringUtil.checkNull((String)costReportAllItem.get("f_day"));
                Leader_day		= StringUtil.checkNull((String)costReportAllItem.get("Leader_day"));
                Leader_name		= StringUtil.checkNull((String)costReportAllItem.get("Leader_name"));
                JB_day				= StringUtil.checkNull((String)costReportAllItem.get("JB_day"));
                JB_name			= StringUtil.checkNull((String)costReportAllItem.get("JB_name"));
                p_leader_day		= StringUtil.checkNull((String)costReportAllItem.get("p_leader_day"));
                p_leader_name		= StringUtil.checkNull((String)costReportAllItem.get("p_leader_name"));
                r_owner_day		= StringUtil.checkNull((String)costReportAllItem.get("r_owner_day"));
                r_pre_day			= StringUtil.checkNull((String)costReportAllItem.get("r_pre_day"));
                Gr_day				= StringUtil.checkNull((String)costReportAllItem.get("Gr_day"));
                Gr_name			= StringUtil.checkNull((String)costReportAllItem.get("Gr_name"));

                approval				= StringUtil.checkNull((String)costReportAllItem.get("approval"));
                w_name      		= StringUtil.checkNull((String)costReportAllItem.get("w_name"));
                step_no				= StringUtil.checkNull((String)costReportAllItem.get("step_no"));


                w_name = StringUtil.checkNull((String)costReportAllItem.get("w_name"));
                app_part = StringUtil.checkNull((String)costReportAllItem.get("app_part")); // 적용부위
                case_1_note = StringUtil.checkNull((String)costReportAllItem.get("case_1_note"));
                case_2_note = StringUtil.checkNull((String)costReportAllItem.get("case_2_note"));
                case_3_note = StringUtil.checkNull((String)costReportAllItem.get("case_3_note"));
                case_to_note_1 = StringUtil.checkNull((String)costReportAllItem.get("case_to_note_1"));
                case_to_note_2 = StringUtil.checkNull((String)costReportAllItem.get("case_to_note_2"));
                case_to_note_3 = StringUtil.checkNull((String)costReportAllItem.get("case_to_note_3"));
                pk_cr_group = StringUtil.checkNull((String)costReportAllItem.get("pk_cr_group")); // 요청서 그룹번호
                FK_cost_request_s = StringUtil.checkNull((String)costReportAllItem.get("FK_cost_request")); // 요청서 리스트
                dev_step = StringUtil.checkNull((String)costReportAllItem.get("dev_step")); // 개발단계
                pjt_name = StringUtil.checkNull((String)costReportAllItem.get("pjt_name")); // Project Name
                pjt_no = StringUtil.checkNull((String)costReportAllItem.get("pjt_no")); // Project No
                part_name[j] = StringUtil.checkNull((String)costReportAllItem.get("part_name")); // Part Name
                target_cost[j] = StringUtil.checkNull((String)costReportAllItem.get("target_cost"));
                team = StringUtil.checkNull((String)costReportAllItem.get("team")); // 개발담당팀
                f_name = StringUtil.checkNull((String)costReportAllItem.get("f_name")); // 개발담당자
                a_name = StringUtil.checkNull((String)costReportAllItem.get("a_name"));// 영업담당자
                request_txt = StringUtil.checkNull((String)costReportAllItem.get("request_txt")); // 요청목적
                car_type = StringUtil.checkNull((String)costReportAllItem.get("car_type")); // 적용차종
                customer_F = StringUtil.checkNull((String)costReportAllItem.get("customer_F")); // 1차고객사
                report_dest = StringUtil.checkNull((String)costReportAllItem.get("report_dest"));
                lme_cu = StringUtil.checkNull((String)costReportAllItem.get("lme_cu"));
                u_ex_rate = StringUtil.checkNull((String)costReportAllItem.get("u_ex_rate"));
                pack_type = StringUtil.checkNull((String)costReportAllItem.get("pack_type")); // 포장유형
                usage[j] = StringUtil.checkNull((String)costReportAllItem.get("usage")); // usage
                make_type[j] = StringUtil.checkNull((String)costReportAllItem.get("make_type")); // 생산처
                pro_1 = StringUtil.checkNull((String)costReportAllItem.get("pro_1")); // 사내세부생산처
                eff_value = StringUtil.checkNull((String)costReportAllItem.get("eff_value")); // 효율
                mold_count[0] = StringUtil.checkNull((String)costReportAllItem.get("mold_count")); // Mold수량
                press_count[0] = StringUtil.checkNull((String)costReportAllItem.get("press_count")); // Press수량
                line_count[0] = StringUtil.checkNull((String)costReportAllItem.get("line_count")); //수동조립설비
                pack_count[0] = StringUtil.checkNull((String)costReportAllItem.get("pack_count"));// 포장금형
                repack_count[0] = StringUtil.checkNull((String)costReportAllItem.get("repack_count")); // 회수용박스
                mold_total[0] = StringUtil.checkNull((String)costReportAllItem.get("mold_total_1")); // Mold비
                press_total[0] = StringUtil.checkNull((String)costReportAllItem.get("press_total_1")); // Press비
                line_total[0] = StringUtil.checkNull((String)costReportAllItem.get("line_total_1")); // 수동조립설비비
                pack_total[0] = StringUtil.checkNull((String)costReportAllItem.get("pack_total_1")); //포장금형비
                mold_total[2] = StringUtil.checkNull((String)costReportAllItem.get("mold_total_2")); // Mold비
                press_total[1] = StringUtil.checkNull((String)costReportAllItem.get("press_total_2")); // Press비
                line_total[1] = StringUtil.checkNull((String)costReportAllItem.get("line_total_2")); // 수동조립설비비
                pack_total[1] = StringUtil.checkNull((String)costReportAllItem.get("pack_total_2")); // 포장금형비
                mold_total[2] = StringUtil.checkNull((String)costReportAllItem.get("mold_total_3")); // Mold비
                press_total[2] = StringUtil.checkNull((String)costReportAllItem.get("press_total_3")); //Press비
                line_total[2] = StringUtil.checkNull((String)costReportAllItem.get("line_total_3")); // 수동조립설비비
                pack_total[2] = StringUtil.checkNull((String)costReportAllItem.get("pack_total_3")); // 포장금형비
                repack_total[0] = StringUtil.checkNull((String)costReportAllItem.get("repack_total_1")); // 회수용박스비
                repack_total[1] = StringUtil.checkNull((String)costReportAllItem.get("repack_total_2")); // 회수용박스비
                repack_total[2] = StringUtil.checkNull((String)costReportAllItem.get("repack_total_3")); // 회수용박스비
                m_depr_stan = StringUtil.checkNull((String)costReportAllItem.get("m_depr_stan")); // Mold삼각기준
                p_depr_stan = StringUtil.checkNull((String)costReportAllItem.get("p_depr_stan")); // Press삼각기준
                L_depr_stan = StringUtil.checkNull((String)costReportAllItem.get("L_depr_stan")); // 수동조립설비 상각기준
                pack_depr_stan = StringUtil.checkNull((String)costReportAllItem.get("pack_depr_stan")); // 포장금형 상각기준
                repack_depr_stan = StringUtil.checkNull((String)costReportAllItem.get("repack_depr_stan")); // 회수용박스 상각기준
                su_stan_day = StringUtil.checkNull((String)costReportAllItem.get("su_stan_day")); // 수지 재료단가
                note_1 = StringUtil.checkNull((String)costReportAllItem.get("note_1")); // 2 비고
                note_2 = StringUtil.checkNull((String)costReportAllItem.get("note_2")); // 3 비고
                note_3 = StringUtil.checkNull((String)costReportAllItem.get("note_3")); // 4 비고
                note_4 = StringUtil.checkNull((String)costReportAllItem.get("note_4")); // 5 비고
                note_5 = StringUtil.checkNull((String)costReportAllItem.get("note_5")); // 보고서2번 Page 검토조건및 의견란
                note_5_1 = StringUtil.checkNull((String)costReportAllItem.get("note_5_1")); // 보고서2번 Page 검토조건및 의견란
                tocost_year = StringUtil.checkNull((String)costReportAllItem.get("tocost_year"));
                select_cost = StringUtil.checkNull((String)costReportAllItem.get("select_cost"));
                file_1 = StringUtil.checkNull((String)costReportAllItem.get("file_1"));
                file_2 = StringUtil.checkNull((String)costReportAllItem.get("file_2"));
                file_3 = StringUtil.checkNull((String)costReportAllItem.get("file_3"));
                file_4 = StringUtil.checkNull((String)costReportAllItem.get("file_4"));
                file_5 = StringUtil.checkNull((String)costReportAllItem.get("file_5"));
                file_6 = StringUtil.checkNull((String)costReportAllItem.get("file_6"));

                file_2_name = StringUtil.checkNull((String)costReportAllItem.get("file_2_name"));
                file_3_name = StringUtil.checkNull((String)costReportAllItem.get("file_3_name"));
                file_4_name = StringUtil.checkNull((String)costReportAllItem.get("file_4_name"));
                file_5_name = StringUtil.checkNull((String)costReportAllItem.get("file_5_name"));
                file_6_name = StringUtil.checkNull((String)costReportAllItem.get("file_6_name"));

                if(StringUtil.checkNull(file_2).equals("")){
                    file_2 = "-";
                }
                if(StringUtil.checkNull(file_3).equals("")){
                    file_3 = "-";
                }
                if(StringUtil.checkNull(file_4).equals("")){
                    file_4 = "-";
                }
                if(StringUtil.checkNull(file_5).equals("")){
                    file_5 = "-";
                }
                if(StringUtil.checkNull(file_6).equals("")){
                    file_6 = "-";
                }

                j = j+1;
            } // costReportAllList For end
        }else{
            chk_list = "";
            int j = 0;
            p=0;
            a_su_count[j][p] = "0";

            FKCostWorkList = reportCtl.reportFKCostWorkList(report_pk);
            for(int m=0; m < FKCostWorkList.size(); m++){
                FKCostWorkItem = (Hashtable)FKCostWorkList.get(m);
                t_pk_cw[j][0] = StringUtil.checkNull((String)FKCostWorkItem.get("FK_cost_work_1")); // 산출작업DB_PK(1안)
                t_pk_cw[j][1] = StringUtil.checkNull((String)FKCostWorkItem.get("FK_cost_work_2")); // 산출작업DB_PK(2안)
                t_pk_cw[j][2] = StringUtil.checkNull((String)FKCostWorkItem.get("FK_cost_work_3")); // 산출작업DB_PK(3안)

                pass_state = StringUtil.checkNull((String)FKCostWorkItem.get("pass_type"));
                //622
                if(!"".equals(t_pk_cw[j][0])){
                    if(!"".equals(chk_list)){
                        chk_list = chk_list +","+t_pk_cw[j][0];
                    }else{
                        chk_list = t_pk_cw[j][0];
                    }
                }
                if(!"".equals(t_pk_cw[j][1])){
                    if(!"".equals(chk_list)){
                        chk_list = chk_list +","+t_pk_cw[j][1];
                    }else{
                        chk_list = t_pk_cw[j][1];
                    }
                }
                if(!"".equals(t_pk_cw[j][2])){
                    if(!"".equals(chk_list)){
                        chk_list = chk_list +","+t_pk_cw[j][2];
                    }else{
                        chk_list = t_pk_cw[j][2];
                    }
                }

                f_day = StringUtil.checkNull((String)FKCostWorkItem.get("f_day"));
                Leader_day = StringUtil.checkNull((String)FKCostWorkItem.get("Leader_day"));
                Leader_name = StringUtil.checkNull((String)FKCostWorkItem.get("Leader_name"));
                JB_day =StringUtil.checkNull((String)FKCostWorkItem.get("JB_day"));
                JB_name = StringUtil.checkNull((String)FKCostWorkItem.get("JB_name"));
                r_owner_day = StringUtil.checkNull((String)FKCostWorkItem.get("r_owner_day"));
                r_pre_day = StringUtil.checkNull((String)FKCostWorkItem.get("r_pre_day"));
                p_leader_day = StringUtil.checkNull((String)FKCostWorkItem.get("p_leader_day"));
                p_leader_name = StringUtil.checkNull((String)FKCostWorkItem.get("p_leader_name"));
                Gr_day = StringUtil.checkNull((String)FKCostWorkItem.get("Gr_day"));
                Gr_name = StringUtil.checkNull((String)FKCostWorkItem.get("Gr_name"));
                approval = StringUtil.checkNull((String)FKCostWorkItem.get("approval"));
                w_name = StringUtil.checkNull((String)FKCostWorkItem.get("w_name"));
                app_part = StringUtil.checkNull((String)FKCostWorkItem.get("app_part")); //적용부위
                case_1_note = StringUtil.checkNull((String)FKCostWorkItem.get("case_1_note"));
                case_2_note = StringUtil.checkNull((String)FKCostWorkItem.get("case_2_note"));
                case_3_note = StringUtil.checkNull((String)FKCostWorkItem.get("case_3_note"));
                case_to_note_1 = StringUtil.checkNull((String)FKCostWorkItem.get("case_to_note_1"));
                case_to_note_2 = StringUtil.checkNull((String)FKCostWorkItem.get("case_to_note_2"));
                case_to_note_3 = StringUtil.checkNull((String)FKCostWorkItem.get("case_to_note_3"));
                step_no = StringUtil.checkNull((String)FKCostWorkItem.get("step_no"));
                pk_cr_group = StringUtil.checkNull((String)FKCostWorkItem.get("pk_cr_group"));
                FK_cost_request_s = StringUtil.checkNull((String)FKCostWorkItem.get("FK_cost_request"));  //요청서리스트
                dev_step = StringUtil.checkNull((String)FKCostWorkItem.get("dev_step")); //개발단계
                pjt_name = StringUtil.checkNull((String)FKCostWorkItem.get("pjt_name"));   //ProjectName
                pjt_no = StringUtil.checkNull((String)FKCostWorkItem.get("pjt_no"));   //ProjectNo
                part_name[j] = StringUtil.checkNull((String)FKCostWorkItem.get("part_name"));   //PartName
                team = StringUtil.checkNull((String)FKCostWorkItem.get("team"));    //개발담당팀
                f_name = StringUtil.checkNull((String)FKCostWorkItem.get("f_name"));    //개발담당자
                a_name = StringUtil.checkNull((String)FKCostWorkItem.get("a_name"));  //영업담당자
                request_txt = StringUtil.checkNull((String)FKCostWorkItem.get("request_txt"));    //요청목적
                car_type = StringUtil.checkNull((String)FKCostWorkItem.get("car_type"));    //적용차종
                customer_F = StringUtil.checkNull((String)FKCostWorkItem.get("customer_F"));   //1차고객사
                su_year_1[j] = StringUtil.checkNull((String)FKCostWorkItem.get("su_year_1"));    //기획수량-1년차
                su_year_2[j] = StringUtil.checkNull((String)FKCostWorkItem.get("su_year_2"));    //기획수량-2년차
                su_year_3[j] = StringUtil.checkNull((String)FKCostWorkItem.get("su_year_3"));    //기획수량-3년차
                su_year_4[j] = StringUtil.checkNull((String)FKCostWorkItem.get("su_year_4"));    //기획수량-4년차
                su_year_5[j] = StringUtil.checkNull((String)FKCostWorkItem.get("su_year_5"));    //기획수량-5년차
                su_year_6[j] = StringUtil.checkNull((String)FKCostWorkItem.get("su_year_6"));   //기획수량-6년차
                su_year_7[j] = StringUtil.checkNull((String)FKCostWorkItem.get("su_year_7"));    //기획수량-7년차
                su_year_8[j] = StringUtil.checkNull((String)FKCostWorkItem.get("su_year_8"));   //기획수량-8년차
                report_dest = StringUtil.checkNull((String)FKCostWorkItem.get("report_dest"));
                client_cost[j] = StringUtil.checkNull((String)FKCostWorkItem.get("client_cost"));   //고객사인정예상가
                room_earn[j] = StringUtil.checkNull((String)FKCostWorkItem.get("room_earn"));    //룸폭
                pro_usage[j] = StringUtil.checkNull((String)FKCostWorkItem.get("pro_usage"));  //납입U/S
                ket_cost[j][p] = StringUtil.checkNull((String)FKCostWorkItem.get("ket_cost"));   //판매목표가
                target_cost[j] = StringUtil.checkNull((String)FKCostWorkItem.get("target_cost"));  //목표수익율
                lme_cu = StringUtil.checkNull((String)FKCostWorkItem.get("lme_cu"));
                u_ex_rate = StringUtil.checkNull((String)FKCostWorkItem.get("u_ex_rate"));
                pack_type = StringUtil.checkNull((String)FKCostWorkItem.get("pack_type"));   //포장유형
                usage[j] = StringUtil.checkNull((String)FKCostWorkItem.get("usage"));  //usage
                make_type[j] = StringUtil.checkNull((String)FKCostWorkItem.get("make_type"));  //생산처
                pro_1 = StringUtil.checkNull((String)FKCostWorkItem.get("pro_1"));   //사내세부생산처
                eff_value = StringUtil.checkNull((String)FKCostWorkItem.get("eff_value"));   //효율
                t_actual_cost[j][0] = StringUtil.checkNullZero((String)FKCostWorkItem.get("actual_cost_1"));    //총원가
                t_earn_rate[j][0] = StringUtil.checkNull((String)FKCostWorkItem.get("earn_rate_1"));  //수익율
                t_actual_cost[j][1] = StringUtil.checkNullZero((String)FKCostWorkItem.get("actual_cost_2"));    //총원가;    //총원가
                t_earn_rate[j][1] = StringUtil.checkNull((String)FKCostWorkItem.get("earn_rate_2"));  //수익율
                t_actual_cost[j][2] = StringUtil.checkNullZero((String)FKCostWorkItem.get("actual_cost_3"));   //총원가
                t_earn_rate[j][2] = StringUtil.checkNull((String)FKCostWorkItem.get("earn_rate_3"));  //수익율
                actual_cost_sum_1 = StringUtil.checkNull((String)FKCostWorkItem.get("actual_cost_sum_1"));
                earn_rate_sum_1 = StringUtil.checkNull((String)FKCostWorkItem.get("earn_rate_sum_1"));
                actual_cost_sum_2 = StringUtil.checkNull((String)FKCostWorkItem.get("actual_cost_sum_2"));
                earn_rate_sum_2 = StringUtil.checkNull((String)FKCostWorkItem.get("earn_rate_sum_2"));
                actual_cost_sum_3 = StringUtil.checkNull((String)FKCostWorkItem.get("actual_cost_sum_3"));
                earn_rate_sum_3 = StringUtil.checkNull((String)FKCostWorkItem.get("earn_rate_sum_3"));
                mold_count[0] = StringUtil.checkNull((String)FKCostWorkItem.get("mold_count"));   //Mold수량
                press_count[0] = StringUtil.checkNull((String)FKCostWorkItem.get("press_count"));   //Press수량
                line_count[0] = StringUtil.checkNull((String)FKCostWorkItem.get("line_count"));  //수동조립설비
                pack_count[0] = StringUtil.checkNull((String)FKCostWorkItem.get("pack_count"));    //포장금형
                repack_count[0] = StringUtil.checkNull((String)FKCostWorkItem.get("repack_count"));  //회수용박스
                mold_total[0] = StringUtil.checkNull((String)FKCostWorkItem.get("mold_total_1"));  //Mold비
                press_total[0] = StringUtil.checkNull((String)FKCostWorkItem.get("press_total_1"));   //Press비
                line_total[0] = StringUtil.checkNull((String)FKCostWorkItem.get("line_total_1"));  //수동조립설비비
                pack_total[0] = StringUtil.checkNull((String)FKCostWorkItem.get("pack_total_1"));   //포장금형비
                mold_total[1] = StringUtil.checkNull((String)FKCostWorkItem.get("mold_total_2"));  //Mold비
                press_total[1] = StringUtil.checkNull((String)FKCostWorkItem.get("press_total_2"));   //Press비
                line_total[1] = StringUtil.checkNull((String)FKCostWorkItem.get("line_total_2"));  //수동조립설비비
                pack_total[1] = StringUtil.checkNull((String)FKCostWorkItem.get("pack_total_2"));   //포장금형비
                mold_total[2] = StringUtil.checkNull((String)FKCostWorkItem.get("mold_total_3")); //Mold비
                press_total[2] = StringUtil.checkNull((String)FKCostWorkItem.get("press_total_3"));  //Press비
                line_total[2] = StringUtil.checkNull((String)FKCostWorkItem.get("line_total_3"));  //수동조립설비비
                pack_total[2] = StringUtil.checkNull((String)FKCostWorkItem.get("pack_total_3")); //포장금형비
                repack_total[0] = StringUtil.checkNull((String)FKCostWorkItem.get("repack_total_1"));    //회수용박스비
                repack_total[1] = StringUtil.checkNull((String)FKCostWorkItem.get("repack_total_2"));  //회수용박스비
                repack_total[2] = StringUtil.checkNull((String)FKCostWorkItem.get("repack_total_3"));  //회수용박스비
                m_depr_stan = StringUtil.checkNull((String)FKCostWorkItem.get("m_depr_stan"));  //Mold상각기준
                p_depr_stan = StringUtil.checkNull((String)FKCostWorkItem.get("p_depr_stan"));  //Press상각기준
                L_depr_stan = StringUtil.checkNull((String)FKCostWorkItem.get("L_depr_stan"));   //수동조립설비상각기준
                pack_depr_stan = StringUtil.checkNull((String)FKCostWorkItem.get("pack_depr_stan"));  //포장금형상각기준
                repack_depr_stan = StringUtil.checkNull((String)FKCostWorkItem.get("repack_depr_stan"));    //회수용박스상각기준
                total_sales_1 = StringUtil.checkNullZero((String)FKCostWorkItem.get("total_sales_1")) ;    //매출액-1년차
                total_sales_2 = StringUtil.checkNull((String)FKCostWorkItem.get("total_sales_2"));    //매출액-2년차
                total_sales_3 = StringUtil.checkNull((String)FKCostWorkItem.get("total_sales_3"));    //매출액-3년차
                total_sales_4 = StringUtil.checkNull((String)FKCostWorkItem.get("total_sales_4"));    //매출액-4년차
                total_sales_5 = StringUtil.checkNull((String)FKCostWorkItem.get("total_sales_5"));    //매출액-5년차
                total_sales_6 = StringUtil.checkNull((String)FKCostWorkItem.get("total_sales_6"));   //매출액-6년차
                total_sales_7 = StringUtil.checkNull((String)FKCostWorkItem.get("total_sales_7"));    //매출액-5년차
                total_sales_8 = StringUtil.checkNull((String)FKCostWorkItem.get("total_sales_8"));   //매출액-6년차
                profit_1 = StringUtil.checkNull((String)FKCostWorkItem.get("profit_1"));   //영업이익-1년차
                profit_2 = StringUtil.checkNull((String)FKCostWorkItem.get("profit_2"));    //영업이익-2년차
                profit_3 = StringUtil.checkNull((String)FKCostWorkItem.get("profit_3"));   //영업이익-3년차
                profit_4 = StringUtil.checkNull((String)FKCostWorkItem.get("profit_4"));    //영업이익-4년차
                profit_5 = StringUtil.checkNull((String)FKCostWorkItem.get("profit_5"));    //영업이익-5년차
                profit_6 = StringUtil.checkNull((String)FKCostWorkItem.get("profit_6"));   //영업이익-6년차
                profit_7 = StringUtil.checkNull((String)FKCostWorkItem.get("profit_7"));    //영업이익-5년차
                profit_8 = StringUtil.checkNull((String)FKCostWorkItem.get("profit_8"));   //영업이익-6년차
                per_profit_1 = StringUtil.checkNull((String)FKCostWorkItem.get("per_profit_1"));   //영업이익율-1년차
                per_profit_2 = StringUtil.checkNull((String)FKCostWorkItem.get("per_profit_2"));   //영업이익율-2년차
                per_profit_3 = StringUtil.checkNull((String)FKCostWorkItem.get("per_profit_3"));   //영업이익율-3년차
                per_profit_4 = StringUtil.checkNull((String)FKCostWorkItem.get("per_profit_4"));   //영업이익율-4년차
                per_profit_5 = StringUtil.checkNull((String)FKCostWorkItem.get("per_profit_5"));    //영업이익율-5년차
                per_profit_6 = StringUtil.checkNull((String)FKCostWorkItem.get("per_profit_6"));   //영업이익율-6년차
                per_profit_7 = StringUtil.checkNull((String)FKCostWorkItem.get("per_profit_7"));    //영업이익율-5년차
                per_profit_8 = StringUtil.checkNull((String)FKCostWorkItem.get("per_profit_8"));    //영업이익율-6년차
                su_stan_day = StringUtil.checkNull((String)FKCostWorkItem.get("su_stan_day"));   //수지재료단가
                note_1 = StringUtil.checkNull((String)FKCostWorkItem.get("note_1"));   //2.비고
                note_2 = StringUtil.checkNull((String)FKCostWorkItem.get("note_2"));   //3.비고
                note_3 = StringUtil.checkNull((String)FKCostWorkItem.get("note_3"));  //4.비고
                note_4 = StringUtil.checkNull((String)FKCostWorkItem.get("note_4"));   //5.비고
                note_5 = StringUtil.checkNull((String)FKCostWorkItem.get("note_5"));    //보고서2번page검토조건및의견란
                note_5_1 = StringUtil.checkNull((String)FKCostWorkItem.get("note_5_1"));  //보고서2번page검토조건및의견란
                tocost_year = StringUtil.checkNull((String)FKCostWorkItem.get("tocost_year"));
                select_cost = StringUtil.checkNull((String)FKCostWorkItem.get("select_cost"));

                file_1 = StringUtil.checkNull((String)FKCostWorkItem.get("file_1"));
                file_2 = StringUtil.checkNull((String)FKCostWorkItem.get("file_2"));
                file_3 = StringUtil.checkNull((String)FKCostWorkItem.get("file_3"));
                file_4 = StringUtil.checkNull((String)FKCostWorkItem.get("file_4"));
                file_5 = StringUtil.checkNull((String)FKCostWorkItem.get("file_5"));
                file_6 = StringUtil.checkNull((String)FKCostWorkItem.get("file_6"));

                file_2_name = StringUtil.checkNull((String)FKCostWorkItem.get("file_2_name"));
                file_3_name = StringUtil.checkNull((String)FKCostWorkItem.get("file_3_name"));
                file_4_name = StringUtil.checkNull((String)FKCostWorkItem.get("file_4_name"));
                file_5_name = StringUtil.checkNull((String)FKCostWorkItem.get("file_5_name"));
                file_6_name = StringUtil.checkNull((String)FKCostWorkItem.get("file_6_name"));

                if(StringUtil.checkNull(file_2).equals("")){
                    file_2 = "-";
                }
                if(StringUtil.checkNull(file_3).equals("")){
                    file_3 = "-";
                }
                if(StringUtil.checkNull(file_4).equals("")){
                    file_4 = "-";
                }
                if(StringUtil.checkNull(file_5).equals("")){
                    file_5 = "-";
                }
                if(StringUtil.checkNull(file_6).equals("")){
                    file_6 = "-";
                }
                if(StringUtil.nullDouble(su_year_1[j]) > 0){

                    if(!StringUtil.checkNullZero(usage[j]).equals("0")){
                        su_year_1[j] = Double.toString(StringUtil.nullDouble(su_year_1[j]) * StringUtil.nullDouble(usage[j]));
                    }
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    su_year_1[j] = "0";
                }
                if(StringUtil.nullDouble(su_year_2[j]) > 0){
                    if(!StringUtil.checkNullZero(usage[j]).equals("0")){
                        su_year_2[j] = Double.toString(StringUtil.nullDouble(su_year_2[j]) * StringUtil.nullDouble(usage[j]));
                    }
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    su_year_2[j] = "0";
                }
                if(StringUtil.nullDouble(su_year_3[j]) > 0){
                    if(!StringUtil.checkNullZero(usage[j]).equals("0")){
                        su_year_3[j] = Double.toString(StringUtil.nullDouble(su_year_3[j]) * StringUtil.nullDouble(usage[j]));
                    }
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    su_year_3[j] = "0";
                }
                if(StringUtil.nullDouble(su_year_4[j]) > 0){
                    if(!StringUtil.checkNullZero(usage[j]).equals("0")){
                        su_year_4[j] = Double.toString(StringUtil.nullDouble(su_year_4[j]) * StringUtil.nullDouble(usage[j]));
                    }
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    su_year_4[j] = "0";
                }
                if(StringUtil.nullDouble(su_year_5[j]) > 0){
                    if(!StringUtil.checkNullZero(usage[j]).equals("0")){
                        su_year_5[j] = Double.toString(StringUtil.nullDouble(su_year_5[j]) * StringUtil.nullDouble(usage[j]));
                    }
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    su_year_5[j] = "0";
                }
                if(StringUtil.nullDouble(su_year_6[j]) > 0){
                    if(!StringUtil.checkNullZero(usage[j]).equals("0")){
                        su_year_6[j] = Double.toString(StringUtil.nullDouble(su_year_6[j]) * StringUtil.nullDouble(usage[j]));
                    }
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    su_year_6[j] = "0";
                }
                if(StringUtil.nullDouble(su_year_7[j]) > 0){
                    if(!StringUtil.checkNullZero(usage[j]).equals("0")){
                        su_year_7[j] = Double.toString(StringUtil.nullDouble(su_year_7[j]) * StringUtil.nullDouble(usage[j]));
                    }
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    su_year_7[j] = "0";
                }
                if(StringUtil.nullDouble(su_year_8[j]) > 0){
                    if(!StringUtil.checkNullZero(usage[j]).equals("0")){
                        su_year_8[j] = Double.toString(StringUtil.nullDouble(su_year_8[j]) * StringUtil.nullDouble(usage[j]));
                    }
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    su_year_8[j] = "0";
                }

                avg_su[j] = Double.toString(StringUtil.nullDouble(su_year_1[j]) + StringUtil.nullDouble(su_year_2[j]) + StringUtil.nullDouble(su_year_3[j]) + StringUtil.nullDouble(su_year_4[j]) + StringUtil.nullDouble(su_year_5[j]) + StringUtil.nullDouble(su_year_6[j]) + StringUtil.nullDouble(su_year_7[j]) + StringUtil.nullDouble(su_year_8[j]));
                if(Double.parseDouble(StringUtil.checkNullZero(avg_su[j])) == 0){
                    avg_su[j] = "";
                }
                j = j+1;
            } // FKCostWorkList for end
            sum_sales = Double.toString(StringUtil.nullDouble(total_sales_1) + StringUtil.nullDouble(total_sales_2) + StringUtil.nullDouble(total_sales_3) + StringUtil.nullDouble(total_sales_4) + StringUtil.nullDouble(total_sales_5) + StringUtil.nullDouble(total_sales_6) + StringUtil.nullDouble(total_sales_7) + StringUtil.nullDouble(total_sales_8));
            sum_profit = Double.toString(StringUtil.nullDouble(profit_1) + StringUtil.nullDouble(profit_2) + StringUtil.nullDouble(profit_3) + StringUtil.nullDouble(profit_4) + StringUtil.nullDouble(profit_5) + StringUtil.nullDouble(profit_6) + StringUtil.nullDouble(profit_7) + StringUtil.nullDouble(profit_8));
            if(StringUtil.nullDouble(a_su_count[0][0]) > 0){
                //sum_per_p = Double.toString((StringUtil.nullDouble(per_profit_1) + StringUtil.nullDouble(per_profit_2) + StringUtil.nullDouble(per_profit_3) + StringUtil.nullDouble(per_profit_4) + StringUtil.nullDouble(per_profit_5) + StringUtil.nullDouble(per_profit_6) + StringUtil.nullDouble(per_profit_7) + StringUtil.nullDouble(per_profit_8)) / StringUtil.nullDouble(a_su_count[0][0]));
                //영업이익율 = (영업이익(백만원)합계 / 매출액(백만원)합계) * 100    2013.09/04 오인석 과장 계산식 변경 요청 by 황정태
                sum_per_p = Double.toString(StringUtil.nullDouble(sum_profit)/StringUtil.nullDouble(sum_sales));
            }
        }
    }
    if(!StringUtil.checkNull(chk_list).equals("")){
        if(",".equals(chk_list.substring(0,1))){
            chk_list = chk_list.substring(1);
        }

        pk_cw_array = chk_list.split(",");
        P_count = pk_cw_array.length;
    }
    if(!"".equals(chk_list) && !chk_list.equals("null") && chk_list != null){
        p = 0;
        int i = 0;
        for(int j=0; j<P_count;j++){
            a_su_count[j][p] = "0";
            if(!StringUtil.checkNull(pk_cw_array[j]).equals("") && (!StringUtil.checkNull(pk_cw_array[j]).equals("null"))){
                costWorkGroupNoList = reportCtl.reportCostWorkGroupNoList(pk_cw_array[j],rev_no);
            }
            for(int m=0; m < costWorkGroupNoList.size(); m++){
                costWorkGroupNoItem = (Hashtable)costWorkGroupNoList.get(m);
                a_pk_cw[j][p] = pk_cw_array[j];
                group_no[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("group_no"));
                FK_cost_request[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("FK_cost_request"));
                actual_cost[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("actual_cost"));
                earn_rate[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("earn_rate"));
                usage[j] = StringUtil.checkNull((String)costWorkGroupNoItem.get("usage"));
                f_day = StringUtil.checkNull((String)costWorkGroupNoItem.get("f_day"));
                Leader_day = StringUtil.checkNull((String)costWorkGroupNoItem.get("Leader_day"));
                Leader_name = StringUtil.checkNull((String)costWorkGroupNoItem.get("Leader_name"));
                approval = StringUtil.checkNull((String)costWorkGroupNoItem.get("approval"));
                step_no = StringUtil.checkNull((String)costWorkGroupNoItem.get("step_no"));
                w_name = StringUtil.checkNull((String)costWorkGroupNoItem.get("w_name"));
                ket_cost[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("ket_cost"));
                a_su_year_1[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("su_year_1"));
                a_su_year_2[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("su_year_2"));
                a_su_year_3[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("su_year_3"));
                a_su_year_4[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("su_year_4"));
                a_su_year_5[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("su_year_5"));
                a_su_year_6[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("su_year_6"));
                a_su_year_7[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("su_year_7"));
                a_su_year_8[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("su_year_8"));
                if( !"".equals(StringUtil.checkNull( (String)costWorkGroupNoItem.get("ket_cost")) )){
                    ket_cost_value = "ok";
                }else{
                    ket_cost_value = "no";
                }
                mold_c_type[j][p] = StringUtil.checkNull((String)costWorkGroupNoItem.get("mold_c_type"));

                if(StringUtil.nullDouble(a_su_year_1[j][p]) > 0){
                    a_su_year_1[j][p] = Double.toString(StringUtil.nullDouble(a_su_year_1[j][p]) * StringUtil.nullDouble(usage[j]));
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    a_su_year_1[j][p] = "0";
                    su_year_state = "No";
                }
                if(StringUtil.nullDouble(a_su_year_2[j][p]) > 0){
                    a_su_year_2[j][p] = Double.toString(StringUtil.nullDouble(a_su_year_2[j][p]) * StringUtil.nullDouble(usage[j]));
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    a_su_year_2[j][p] = "0";
                }if(StringUtil.nullDouble(a_su_year_3[j][p]) > 0){
                    a_su_year_3[j][p] = Double.toString(StringUtil.nullDouble(a_su_year_3[j][p]) * StringUtil.nullDouble(usage[j]));
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    a_su_year_3[j][p] = "0";
                }if(StringUtil.nullDouble(a_su_year_4[j][p]) > 0){
                    a_su_year_4[j][p] = Double.toString(StringUtil.nullDouble(a_su_year_4[j][p]) * StringUtil.nullDouble(usage[j]));
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    a_su_year_4[j][p] = "0";
                }if(StringUtil.nullDouble(a_su_year_5[j][p]) > 0){
                    a_su_year_5[j][p] = Double.toString(StringUtil.nullDouble(a_su_year_5[j][p]) * StringUtil.nullDouble(usage[j]));
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    a_su_year_5[j][p] = "0";
                }if(StringUtil.nullDouble(a_su_year_6[j][p]) > 0){
                    a_su_year_6[j][p] = Double.toString(StringUtil.nullDouble(a_su_year_6[j][p]) * StringUtil.nullDouble(usage[j]));
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    a_su_year_6[j][p] = "0";
                }if(StringUtil.nullDouble(a_su_year_7[j][p]) > 0){
                    a_su_year_7[j][p] = Double.toString(StringUtil.nullDouble(a_su_year_7[j][p]) * StringUtil.nullDouble(usage[j]));
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    a_su_year_7[j][p] = "0";
                }if(StringUtil.nullDouble(a_su_year_8[j][p]) > 0){
                    a_su_year_8[j][p] = Double.toString(StringUtil.nullDouble(a_su_year_8[j][p]) * StringUtil.nullDouble(usage[j]));
                    a_su_count[j][p] = Double.toString(StringUtil.nullDouble(a_su_count[j][p]) + 1);
                }else{
                    a_su_year_8[j][p] = "0";
                }

                if("ok".equals(select_case)){
                    su_year_1[j] = a_su_year_1[j][p];
                    su_year_2[j] = a_su_year_2[j][p];
                    su_year_3[j] = a_su_year_3[j][p];
                    su_year_4[j] = a_su_year_4[j][p];
                    su_year_5[j] = a_su_year_5[j][p];
                    su_year_6[j] = a_su_year_6[j][p];
                    su_year_7[j] = a_su_year_7[j][p];
                    su_year_8[j] = a_su_year_8[j][p];
                    avg_su[j] = Double.toString(StringUtil.nullDouble(su_year_1[j]) + StringUtil.nullDouble(su_year_2[j]) + StringUtil.nullDouble(su_year_3[j]) + StringUtil.nullDouble(su_year_4[j]) + StringUtil.nullDouble(su_year_5[j]) + StringUtil.nullDouble(su_year_6[j]) + StringUtil.nullDouble(su_year_7[j]) + StringUtil.nullDouble(su_year_8[j]));
                    if(Double.parseDouble(StringUtil.checkNullZero(avg_su[j])) == 0){
                        avg_su[j] = "";
                    }
                }
            } // costWorkGroupNoList For end
        }
        String test = "0";
        p = 0;
        int k = 0;
        i = 0;

        for(int j=0;j<P_count;j++){
            if("0".equals(test)){
                test = group_no[j][p].substring(0, 4);
                t_pk_cw[i][k] = a_pk_cw[j][p];
                t_actual_cost[i][k] = actual_cost[j][p];    // 판매기준 총원가
                t_earn_rate[i][k] = earn_rate[j][p];    // 수익률
                ket_cost[i][k] = ket_cost[j][p];
            }else{
                if(!StringUtil.checkNull(group_no[j][p]).equals("")){
                    if(test.equals(group_no[j][p].substring(0, 4))){
                        t_pk_cw[i][k] = a_pk_cw[j][p];
                        t_actual_cost[i][k] = actual_cost[j][p];    // 판매기준 총원가
                        t_earn_rate[i][k] = earn_rate[j][p];    // 수익률
                        ket_cost[i][k] = ket_cost[j][p];
                    }else if(!test.equals(group_no[j][p].substring(0, 4))){
                        i = i+1;
                        k = 0;
                        test = group_no[j][p].substring(0, 4);
                        t_pk_cw[i][k] = a_pk_cw[j][p];
                        t_actual_cost[i][k] = actual_cost[j][p];    // 판매기준 총원가
                        t_earn_rate[i][k] = earn_rate[j][p];    // 수익률
                        ket_cost[i][k] = ket_cost[j][p];
                    }
                }
            }

            k = k + 1;

        }
        table_row_count = table_row;
        if("ok".equals(us_change)){
            actual_cost_sum_1 = "";
            actual_cost_sum_2 = "";
            actual_cost_sum_3 = "";
            for(int j=0; j < Integer.parseInt(StringUtil.checkNullZero(table_row_count)); j++){
                pro_usage[j] = StringUtil.checkNull(request.getParameter("pro_usage"+j));
            }
        }

        k=0;
        for(i=0;i<Integer.parseInt(StringUtil.checkNullZero(table_row_count)); i++){
            if(!"".equals(ket_cost[i][k])){
                ket_cost[i][k] = ket_cost[i][k];
            }else{
                if(!"".equals(target_cost[i])){
                    ket_cost[i][k] = Double.toString( StringUtil.nullDouble(t_actual_cost[i][k]) / (((100 - StringUtil.nullDouble(target_cost[i])) / 100))   );
                }else{
                    ket_cost[i][k] = Double.toString(StringUtil.nullDouble(t_actual_cost[i][k]) / 0.7);
                }
                ket_cost[i][k] = String.format("%.2f", StringUtil.nullDouble(ket_cost[i][k]));
            }
            if(!"".equals(client_cost[i])){
                room_earn[i] = Double.toString(1 - (StringUtil.nullDouble(ket_cost[i][k]) / StringUtil.nullDouble(client_cost[i])));
                if(!"".equals(StringUtil.checkNull(client_cost_sum))){
                    client_cost_sum = Double.toString(StringUtil.nullDouble(client_cost_sum) + StringUtil.nullDouble(client_cost[i]));
                }else{
                    client_cost_sum = client_cost[i];
                }
            }
            if((!"".equals(pro_usage[i])) && (pro_usage[i] != null)){
                pro_usage[i] = pro_usage[i];
            }else{
                pro_usage[i] = "1";
            }

            if(!"".equals(ket_cost_sum)){
                ket_cost_sum = Double.toString(StringUtil.nullDouble(ket_cost_sum) + (StringUtil.nullDouble(ket_cost[i][k]) * StringUtil.nullDouble(pro_usage[i])));
            }else{
                ket_cost_sum = Double.toString(StringUtil.nullDouble(ket_cost[i][k]) * StringUtil.nullDouble(pro_usage[i]));
            }

            if("ok".equals(us_change) || !"ok".equals(cost_report_add)){
                if(!"".equals(actual_cost_sum_1)){
                    actual_cost_sum_1 = Double.toString(StringUtil.nullDouble(actual_cost_sum_1) + (StringUtil.nullDouble(t_actual_cost[i][0]) * StringUtil.nullDouble(pro_usage[i])));
                }else{
                    if(!"".equals(t_actual_cost[i][0])){
                        actual_cost_sum_1 = Double.toString(StringUtil.nullDouble(t_actual_cost[i][0]) * StringUtil.nullDouble(pro_usage[i]));
                    }
                }
                if(!"".equals(actual_cost_sum_2)){
                    actual_cost_sum_2 = Double.toString(StringUtil.nullDouble(actual_cost_sum_2) + (StringUtil.nullDouble(t_actual_cost[i][1]) * StringUtil.nullDouble(pro_usage[i])));
                }else{
                    if(!"".equals(t_actual_cost[i][0])){
                        actual_cost_sum_2 = Double.toString(StringUtil.nullDouble(t_actual_cost[i][1]) * StringUtil.nullDouble(pro_usage[i]));
                    }
                }
                if(!"".equals(actual_cost_sum_3)){
                    actual_cost_sum_3 = Double.toString(StringUtil.nullDouble(actual_cost_sum_3) + (StringUtil.nullDouble(t_actual_cost[i][2]) * StringUtil.nullDouble(pro_usage[i])));
                }else{
                    if(!"".equals(t_actual_cost[i][2])){
                        actual_cost_sum_3 = Double.toString(StringUtil.nullDouble(t_actual_cost[i][2]) * StringUtil.nullDouble(pro_usage[i]));
                    }
                }
            } // us_change, cost_report_add if end
        } // table_row_count for end

        if((!"No".equals(su_year_state) && !"ok".equals(cost_report_add)) || "ok".equals(us_change) || "ok".equals(select_case)){
            test = "0";
            p = 0;
            k = 0;
            i = 0;
            for(int j=0;j < P_count; j++){
                if("0".equals(test)){
                    test = group_no[j][p].substring(0, 4);
                    t_su_year_1[i][k] = a_su_year_1[j][p];
                    t_su_year_2[i][k] = a_su_year_2[j][p];
                    t_su_year_3[i][k] = a_su_year_3[j][p];
                    t_su_year_4[i][k] = a_su_year_4[j][p];
                    t_su_year_5[i][k] = a_su_year_5[j][p];
                    t_su_year_6[i][k] = a_su_year_6[j][p];
                    t_su_year_7[i][k] = a_su_year_7[j][p];
                    t_su_year_8[i][k] = a_su_year_8[j][p];
                }else if( test.equals(group_no[j][p].substring(0, 4)) ){
                    t_su_year_1[i][k] = a_su_year_1[j][p];
                    t_su_year_2[i][k] = a_su_year_2[j][p];
                    t_su_year_3[i][k] = a_su_year_3[j][p];
                    t_su_year_4[i][k] = a_su_year_4[j][p];
                    t_su_year_5[i][k] = a_su_year_5[j][p];
                    t_su_year_6[i][k] = a_su_year_6[j][p];
                    t_su_year_7[i][k] = a_su_year_7[j][p];
                    t_su_year_8[i][k] = a_su_year_8[j][p];
                }else if( !test.equals(group_no[j][p].substring(0, 4)) ){
                    i=i+1;
                    k=0;
                    test = group_no[j][p].substring(0, 4);
                    t_su_year_1[i][k] = a_su_year_1[j][p];
                    t_su_year_2[i][k] = a_su_year_2[j][p];
                    t_su_year_3[i][k] = a_su_year_3[j][p];
                    t_su_year_4[i][k] = a_su_year_4[j][p];
                    t_su_year_5[i][k] = a_su_year_5[j][p];
                    t_su_year_6[i][k] = a_su_year_6[j][p];
                    t_su_year_7[i][k] = a_su_year_7[j][p];
                    t_su_year_8[i][k] = a_su_year_8[j][p];
                }
                k = k+1;

            } // P_count for end
            // 매출액 total_sales_1 ~ 6   = ((제품1 판매목표가 * 1안 판매수량)) + ((제품2 판매목표가 * 1안 판매수량)...)
            // 영업이익 profit_1 ~ 6  =  ((제품1 판매목표가 - 1안 원가) * 1안 판매수량) + ((제품2 판매목표가 - 1안 원가) * 1안 판매수량)...)
            // 영업이익율 per_profit_1 ~ 6 = 영업이익 / 매출액
            total_sales_1 = "0";
            total_sales_2 = "0";
            total_sales_3 = "0";
            total_sales_4 = "0";
            total_sales_5 = "0";
            total_sales_6 = "0";
            total_sales_7 = "0";
            total_sales_8 = "0";
            k=0;
            for(i=0;i<Integer.parseInt(table_row_count);i++){
                if(Integer.parseInt(pro_usage[i]) == 0){
                    t_total_sales_1[i] = "0";    // 1년차 매출액
                    t_total_sales_2[i] = "0";    // 2년차 매출액
                    t_total_sales_3[i] = "0";    // 3년차 매출액
                    t_total_sales_4[i] = "0";    // 4년차 매출액
                    t_total_sales_5[i] = "0";    // 5년차 매출액
                    t_total_sales_6[i] = "0";    // 6년차 매출액
                    t_total_sales_7[i] = "0";    // 7년차 매출액
                    t_total_sales_8[i] = "0";    // 8년차 매출액
                }else{
                    t_total_sales_1[i] = String.format("%.0f",StringUtil.nullDouble(ket_cost[i][k]) * (StringUtil.nullDouble(t_su_year_1[i][k]) * 1000) * StringUtil.nullDouble(pro_usage[i]));    // 1년차 매출액
                    t_total_sales_2[i] = String.format("%.0f",StringUtil.nullDouble(ket_cost[i][k]) * (StringUtil.nullDouble(t_su_year_2[i][k]) * 1000) * StringUtil.nullDouble(pro_usage[i]));    // 2년차 매출액
                    t_total_sales_3[i] = String.format("%.0f",StringUtil.nullDouble(ket_cost[i][k]) * (StringUtil.nullDouble(t_su_year_3[i][k]) * 1000) * StringUtil.nullDouble(pro_usage[i]));    // 3년차 매출액
                    t_total_sales_4[i] = String.format("%.0f",StringUtil.nullDouble(ket_cost[i][k]) * (StringUtil.nullDouble(t_su_year_4[i][k]) * 1000) * StringUtil.nullDouble(pro_usage[i]));    // 4년차 매출액
                    t_total_sales_5[i] = String.format("%.0f",StringUtil.nullDouble(ket_cost[i][k]) * (StringUtil.nullDouble(t_su_year_5[i][k]) * 1000) * StringUtil.nullDouble(pro_usage[i]));    // 5년차 매출액
                    t_total_sales_6[i] = String.format("%.0f",StringUtil.nullDouble(ket_cost[i][k]) * (StringUtil.nullDouble(t_su_year_6[i][k]) * 1000) * StringUtil.nullDouble(pro_usage[i]));    // 6년차 매출액
                    t_total_sales_7[i] = String.format("%.0f",StringUtil.nullDouble(ket_cost[i][k]) * (StringUtil.nullDouble(t_su_year_7[i][k]) * 1000) * StringUtil.nullDouble(pro_usage[i]));    // 7년차 매출액
                    t_total_sales_8[i] = String.format("%.0f",StringUtil.nullDouble(ket_cost[i][k]) * (StringUtil.nullDouble(t_su_year_8[i][k]) * 1000) * StringUtil.nullDouble(pro_usage[i]));    // 8년차 매출액
                }

                total_sales_1 = String.format("%.0f",StringUtil.nullDouble(total_sales_1) + StringUtil.nullDouble(t_total_sales_1[i]));    // 1년차 매출액합
                total_sales_2 = String.format("%.0f",StringUtil.nullDouble(total_sales_2) + StringUtil.nullDouble(t_total_sales_2[i]));    // 2년차 매출액합
                total_sales_3 = String.format("%.0f",StringUtil.nullDouble(total_sales_3) + StringUtil.nullDouble(t_total_sales_3[i]));    // 3년차 매출액합
                total_sales_4 = String.format("%.0f",StringUtil.nullDouble(total_sales_4) + StringUtil.nullDouble(t_total_sales_4[i]));    // 4년차 매출액합
                total_sales_5 = String.format("%.0f",StringUtil.nullDouble(total_sales_5) + StringUtil.nullDouble(t_total_sales_5[i]));    // 5년차 매출액합
                total_sales_6 = String.format("%.0f",StringUtil.nullDouble(total_sales_6) + StringUtil.nullDouble(t_total_sales_6[i]));    // 6년차 매출액합
                total_sales_7 = String.format("%.0f",StringUtil.nullDouble(total_sales_7) + StringUtil.nullDouble(t_total_sales_7[i]));    // 7년차 매출액합
                total_sales_8 = String.format("%.0f",StringUtil.nullDouble(total_sales_8) + StringUtil.nullDouble(t_total_sales_8[i]));    // 8년차 매출액합
            }
            //1235
            profit_1 = "0";
            profit_2 = "0";
            profit_3 = "0";
            profit_4 = "0";
            profit_5 = "0";
            profit_6 = "0";
            profit_7 = "0";
            profit_8 = "0";
            int y = 0;
            for(int x=0;x<Integer.parseInt(table_row_count);x++){
                if(Integer.parseInt(pro_usage[x]) == 0){
                    t_profit_1[x] = "0";    //1년차 영업이익액
                    t_profit_2[x] = "0";    //2년차 영업이익액
                    t_profit_3[x] = "0";    //3년차 영업이익액
                    t_profit_4[x] = "0";    //4년차 영업이익액
                    t_profit_5[x] = "0";    //5년차 영업이익액
                    t_profit_6[x] = "0";    //6년차 영업이익액
                    t_profit_7[x] = "0";    //7년차 영업이익액
                    t_profit_8[x] = "0";    //8년차 영업이익액
                }else{
                    //1258
                    //out.println("ket_cost: "+ket_cost[x][y]+"t_actual_cost: "+t_actual_cost[x][y]+"t_su_year_1: "+t_su_year_1[x][y]+"pro_usage : "+pro_usage[x]);
                    t_profit_1[x] = String.format("%.0f",(StringUtil.nullDouble(ket_cost[x][y]) - StringUtil.nullDouble(String.format("%.5f", StringUtil.nullDouble(t_actual_cost[x][y])))) * (StringUtil.nullDouble(t_su_year_1[x][y]) * 1000) * StringUtil.nullDouble(pro_usage[x]));    // 1년차 영업이익액
                    t_profit_2[x] = Double.toString((StringUtil.nullDouble(ket_cost[x][y]) - StringUtil.nullDouble(String.format("%.5f", StringUtil.nullDouble(t_actual_cost[x][y])))) * (StringUtil.nullDouble(t_su_year_2[x][y]) * 1000) * StringUtil.nullDouble(pro_usage[x]));    // 2년차 영업이익액
                    t_profit_3[x] = Double.toString((StringUtil.nullDouble(ket_cost[x][y]) - StringUtil.nullDouble(String.format("%.5f", StringUtil.nullDouble(t_actual_cost[x][y])))) * (StringUtil.nullDouble(t_su_year_3[x][y]) * 1000) * StringUtil.nullDouble(pro_usage[x]));    // 3년차 영업이익액
                    t_profit_4[x] = Double.toString((StringUtil.nullDouble(ket_cost[x][y]) - StringUtil.nullDouble(String.format("%.5f", StringUtil.nullDouble(t_actual_cost[x][y])))) * (StringUtil.nullDouble(t_su_year_4[x][y]) * 1000) * StringUtil.nullDouble(pro_usage[x]));    // 4년차 영업이익액
                    t_profit_5[x] = Double.toString((StringUtil.nullDouble(ket_cost[x][y]) - StringUtil.nullDouble(String.format("%.5f", StringUtil.nullDouble(t_actual_cost[x][y])))) * (StringUtil.nullDouble(t_su_year_5[x][y]) * 1000) * StringUtil.nullDouble(pro_usage[x]));    // 5년차 영업이익액
                    t_profit_6[x] = Double.toString((StringUtil.nullDouble(ket_cost[x][y]) - StringUtil.nullDouble(String.format("%.5f", StringUtil.nullDouble(t_actual_cost[x][y])))) * (StringUtil.nullDouble(t_su_year_6[x][y]) * 1000) * StringUtil.nullDouble(pro_usage[x]));    // 6년차 영업이익액
                    t_profit_7[x] = Double.toString((StringUtil.nullDouble(ket_cost[x][y]) - StringUtil.nullDouble(String.format("%.5f", StringUtil.nullDouble(t_actual_cost[x][y])))) * (StringUtil.nullDouble(t_su_year_7[x][y]) * 1000) * StringUtil.nullDouble(pro_usage[x]));    // 7년차 영업이익액
                    t_profit_8[x] = Double.toString((StringUtil.nullDouble(ket_cost[x][y]) - StringUtil.nullDouble(String.format("%.5f", StringUtil.nullDouble(t_actual_cost[x][y])))) * (StringUtil.nullDouble(t_su_year_8[x][y]) * 1000) * StringUtil.nullDouble(pro_usage[x]));    // 8년차 영업이익액
                }
                //1268

                profit_1 =  String.format("%.0f",StringUtil.nullDouble(profit_1) + StringUtil.nullDouble(t_profit_1[x]));    // 1년차 영업이익액 합

                if(StringUtil.nullDouble(profit_1) < 0 ){
                //    profit_1 = "0";
                }
                profit_2 = String.format("%.0f",StringUtil.nullDouble(profit_2) + StringUtil.nullDouble(t_profit_2[x]));    // 2년차 영업이익액 합
                if(StringUtil.nullDouble(profit_2) < 0 ){
                //    profit_2 = "0";
                }
                profit_3 = String.format("%.0f",StringUtil.nullDouble(profit_3) + StringUtil.nullDouble(t_profit_3[x]));    // 3년차 영업이익액 합
                if(StringUtil.nullDouble(profit_3) < 0 ){
                //    profit_3 = "0";
                }
                profit_4 = String.format("%.0f",StringUtil.nullDouble(profit_4) + StringUtil.nullDouble(t_profit_4[x]));    // 4년차 영업이익액 합
                if(StringUtil.nullDouble(profit_4) < 0 ){
                //    profit_4 = "0";
                }
                profit_5 = String.format("%.0f",StringUtil.nullDouble(profit_5) + StringUtil.nullDouble(t_profit_5[x]));    // 5년차 영업이익액 합
                if(StringUtil.nullDouble(profit_5) < 0 ){
                //    profit_5 = "0";
                }
                profit_6 = String.format("%.0f",StringUtil.nullDouble(profit_6) + StringUtil.nullDouble(t_profit_6[x]));    // 6년차 영업이익액 합
                if(StringUtil.nullDouble(profit_6) < 0 ){
                //    profit_6 = "0";
                }
                profit_7 = String.format("%.0f",StringUtil.nullDouble(profit_7) + StringUtil.nullDouble(t_profit_7[x]));    // 7년차 영업이익액 합
                if(StringUtil.nullDouble(profit_7) < 0 ){
                //    profit_7 = "0";
                }
                profit_8 = String.format("%.0f",StringUtil.nullDouble(profit_8) + StringUtil.nullDouble(t_profit_8[x]));    // 8년차 영업이익액 합
                if(StringUtil.nullDouble(profit_8) < 0 ){
                //    profit_8 = "0";
                }
            }

            if(!"".equals(total_sales_1) && !"0".equals(total_sales_1)){
                per_profit_1 = Double.toString(StringUtil.nullDouble(profit_1) / StringUtil.nullDouble(total_sales_1));    // 1년차 영업이익률 합
            }

            if(!"".equals(total_sales_2) && !"0".equals(total_sales_2)){
                per_profit_2 = Double.toString(StringUtil.nullDouble(profit_2) / StringUtil.nullDouble(total_sales_2));    // 2년차 영업이익률 합
            }
            if(!"".equals(total_sales_3) && !"0".equals(total_sales_3)){
                per_profit_3 = Double.toString(StringUtil.nullDouble(profit_3) / StringUtil.nullDouble(total_sales_3));    // 3년차 영업이익률 합
            }
            if(!"".equals(total_sales_4) && !"0".equals(total_sales_4)){
                per_profit_4 = Double.toString(StringUtil.nullDouble(profit_4) / StringUtil.nullDouble(total_sales_4));    // 4년차 영업이익률 합
            }
            if(!"".equals(total_sales_5) && !"0".equals(total_sales_5)){
                per_profit_5 = Double.toString(StringUtil.nullDouble(profit_5) / StringUtil.nullDouble(total_sales_5));    // 5년차 영업이익률 합
            }
            if(!"".equals(total_sales_6) && !"0".equals(total_sales_6)){
                per_profit_6 = Double.toString(StringUtil.nullDouble(profit_6) / StringUtil.nullDouble(total_sales_6));    // 6년차 영업이익률 합
            }
            if(!"".equals(total_sales_7) && !"0".equals(total_sales_7)){
                per_profit_7 = Double.toString(StringUtil.nullDouble(profit_7) / StringUtil.nullDouble(total_sales_7));    // 7년차 영업이익률 합
            }
            if(!"".equals(total_sales_8) && !"0".equals(total_sales_8)){
                per_profit_8 = Double.toString(StringUtil.nullDouble(profit_8) / StringUtil.nullDouble(total_sales_8));    // 8년차 영업이익률 합
            }


            if(StringUtil.nullDouble(total_sales_1) > 0){
                //total_sales_1 = String.format("%.0f",Math.round(Double.parseDouble(total_sales_1)/1000000));
                total_sales_1 = String.format("%.2f",Double.parseDouble(total_sales_1) / 1000000);
            }

            if(StringUtil.nullDouble(total_sales_2) > 0){
                //total_sales_2 = String.format("%.0f",Math.round(Double.parseDouble(total_sales_2)/1000000));
                total_sales_2 = String.format("%.2f",Double.parseDouble(total_sales_2) / 1000000);
            }

            if(StringUtil.nullDouble(total_sales_3) > 0){
                //total_sales_3 = String.format("%.0f",Math.round(Double.parseDouble(total_sales_3)/1000000));
                total_sales_3 = String.format("%.2f",Double.parseDouble(total_sales_3) / 1000000);
            }


            if(StringUtil.nullDouble(total_sales_4) > 0){
                //total_sales_4 = String.format("%.0f",Math.round(Double.parseDouble(total_sales_4)/1000000));
                total_sales_4 = String.format("%.2f",Double.parseDouble(total_sales_4) / 1000000);
            }


            if(StringUtil.nullDouble(total_sales_5) > 0){
                //total_sales_5 = String.format("%.0f",Math.round(Double.parseDouble(total_sales_5)/1000000));
                total_sales_5 = String.format("%.2f",Double.parseDouble(total_sales_5) / 1000000);
            }


            if(StringUtil.nullDouble(total_sales_6) > 0){
                //total_sales_6 = String.format("%.0f",Math.round(Double.parseDouble(total_sales_6)/1000000));
                total_sales_6 = String.format("%.2f",Double.parseDouble(total_sales_6) / 1000000);
            }


            if(StringUtil.nullDouble(total_sales_7) > 0){
                //total_sales_7 = String.format("%.0f",Math.round(Double.parseDouble(total_sales_7)/1000000));
                total_sales_7 = String.format("%.2f",Double.parseDouble(total_sales_7) / 1000000);
            }


            if(StringUtil.nullDouble(total_sales_8) > 0){
                //total_sales_8 = String.format("%.0f",Math.round(Double.parseDouble(total_sales_8)/1000000));
                total_sales_8 = String.format("%.2f",Double.parseDouble(total_sales_8) / 1000000);
            }

            //if(StringUtil.nullDouble(profit_1) > 0){
                //profit_1 = String.format("%.0f",Math.round(Double.parseDouble(profit_1)/1000000));
                profit_1 = String.format("%.2f",Double.parseDouble(profit_1)/1000000);
            //}

            //if(StringUtil.nullDouble(profit_2) > 0){
                //profit_2 = String.format("%.0f",Math.round(Double.parseDouble(profit_2)/1000000));
                profit_2 = String.format("%.2f",Double.parseDouble(profit_2)/1000000);
            //}

            //if(StringUtil.nullDouble(profit_3) > 0){
                //profit_3 = String.format("%.0f",Math.round(Double.parseDouble(profit_3)/1000000));
                profit_3 = String.format("%.2f",Double.parseDouble(profit_3)/1000000);
            //}


            //if(StringUtil.nullDouble(profit_4) > 0){
                //profit_4 = String.format("%.0f",Math.round(Double.parseDouble(profit_4)/1000000));
                profit_4 = String.format("%.2f",Double.parseDouble(profit_4)/1000000);
            //}


            //if(StringUtil.nullDouble(profit_5) > 0){
                //profit_5 = String.format("%.0f",Math.round(Double.parseDouble(profit_5)/1000000));
                profit_5 = String.format("%.2f",Double.parseDouble(profit_5)/1000000);
            //}


            //if(StringUtil.nullDouble(profit_6) > 0){
                //profit_6 = String.format("%.0f",Math.round(Double.parseDouble(profit_6)/1000000));
                profit_6 = String.format("%.2f",Double.parseDouble(profit_6)/1000000);
            //}


            //if(StringUtil.nullDouble(profit_7) > 0){
                //profit_7 = String.format("%.0f",Math.round(Double.parseDouble(profit_7)/1000000));
                profit_7 = String.format("%.2f",Double.parseDouble(profit_7)/1000000);
            //}


            //if(StringUtil.nullDouble(profit_8) > 0){
                //profit_8 = String.format("%.0f",Math.round(Double.parseDouble(profit_8)/1000000));
                profit_8 = String.format("%.2f",Double.parseDouble(profit_8)/1000000);
            //}

        }
    }

    if(!"No".equals(su_year_state)){
        sum_sales = Double.toString(StringUtil.nullDouble(total_sales_1) + StringUtil.nullDouble(total_sales_2) + StringUtil.nullDouble(total_sales_3) + StringUtil.nullDouble(total_sales_4) + StringUtil.nullDouble(total_sales_5) + StringUtil.nullDouble(total_sales_6) + StringUtil.nullDouble(total_sales_7) + StringUtil.nullDouble(total_sales_8));
        sum_profit = Double.toString(StringUtil.nullDouble(profit_1) + StringUtil.nullDouble(profit_2) + StringUtil.nullDouble(profit_3) + StringUtil.nullDouble(profit_4) + StringUtil.nullDouble(profit_5) + StringUtil.nullDouble(profit_6) + StringUtil.nullDouble(profit_7) + StringUtil.nullDouble(profit_8));

        if(StringUtil.nullDouble(a_su_count[0][0]) > 0){
            //sum_per_p = Double.toString((StringUtil.nullDouble(per_profit_1) + StringUtil.nullDouble(per_profit_2) + StringUtil.nullDouble(per_profit_3) + StringUtil.nullDouble(per_profit_4) + StringUtil.nullDouble(per_profit_5) + StringUtil.nullDouble(per_profit_6) + StringUtil.nullDouble(per_profit_7) + StringUtil.nullDouble(per_profit_8)) / StringUtil.nullDouble(a_su_count[0][0]));
            //영업이익율 = (영업이익(백만원)합계 / 매출액(백만원)합계) * 100    2013.09/04 오인석 과장 계산식 변경 요청 by 황정태
            sum_per_p = Double.toString(StringUtil.nullDouble(sum_profit)/StringUtil.nullDouble(sum_sales));
        }
    }

    // 투자비합계 1안
    if(!"".equals(mold_total[0])){
        mold_total[0] = mold_total[0];
    }else{
        mold_total[0] = "0";
    }
    if(!"".equals(press_total[0])){
        press_total[0] = press_total[0];
    }else{
        press_total[0] = "0";
    }
    if(!"".equals(line_total[0])){
        line_total[0] = line_total[0];
    }else{
        line_total[0] = "0";
    }
    if(!"".equals(pack_total[0])){
        pack_total[0] = pack_total[0];
    }else{
        pack_total[0] = "0";
    }
    if(!"".equals(repack_total[0])){
        repack_total[0] = repack_total[0];
    }else{
        repack_total[0] = "0";
    }

    AA = Double.toString((StringUtil.nullDouble(mold_total[0]) * 1000) + (StringUtil.nullDouble(press_total[0]) * 1000) + (StringUtil.nullDouble(line_total[0]) * 1000) + (StringUtil.nullDouble(pack_total[0]) * 1000) + (StringUtil.nullDouble(repack_total[0]) * 1000));
    AA = Double.toString(StringUtil.nullDouble(AA) / 1000);

    //투자비합계 2안
    if(!"".equals(mold_total[1])){
        mold_total[1] = mold_total[1];
    }else{
        mold_total[1] = "0";
    }
    if(!"".equals(press_total[1])){
        press_total[1] = press_total[1];
    }else{
        press_total[1] = "0";
    }
    if(!"".equals(line_total[1])){
        line_total[1] = line_total[1];
    }else{
        line_total[1] = "0";
    }
    if(!"".equals(pack_total[1])){
        pack_total[1] = pack_total[1];
    }else{
        pack_total[1] = "0";
    }
    if(!"".equals(repack_total[1])){
        repack_total[1] = repack_total[1];
    }else{
        repack_total[1] = "0";
    }
    AB = Double.toString((StringUtil.nullDouble(mold_total[1]) * 1000) + (StringUtil.nullDouble(press_total[1]) * 1000) + (StringUtil.nullDouble(line_total[1]) * 1000) + (StringUtil.nullDouble(pack_total[1]) * 1000) + (StringUtil.nullDouble(repack_total[1]) * 1000));
    AB = Double.toString(StringUtil.nullDouble(AB) / 1000);

    //투자비합계 3안
    if(!"".equals(mold_total[2])){
        mold_total[2] = mold_total[2];
    }else{
        mold_total[2] = "0";
    }
    if(!"".equals(press_total[2])){
        press_total[2] = press_total[2];
    }else{
        press_total[2] = "0";
    }
    if(!"".equals(line_total[2])){
        line_total[2] = line_total[2];
    }else{
        line_total[2] = "0";
    }
    if(!"".equals(pack_total[2])){
        pack_total[2] = pack_total[2];
    }else{
        pack_total[2] = "0";
    }
    if(!"".equals(repack_total[2])){
        repack_total[2] = repack_total[2];
    }else{
        repack_total[2] = "0";
    }
    AC = Double.toString((StringUtil.nullDouble(mold_total[2]) * 1000) + (StringUtil.nullDouble(press_total[2]) * 1000) + (StringUtil.nullDouble(line_total[2]) * 1000) + (StringUtil.nullDouble(pack_total[2]) * 1000) + (StringUtil.nullDouble(repack_total[2]) * 1000));
    AC = Double.toString(StringUtil.nullDouble(AC) / 1000);

    if(!"No".equals(su_year_state) && (!"".equals(file_1) || !"-".equals(file_1))){
        if(!"".equals(tocost_year)){
            tocost_year = tocost_year;
        }else{
            //투자비회수기간
            for(int j=0;j<P_count;j++){
                if((!"".equals(ket_cost[0][0])) && "감가".equals(mold_c_type[j][p])){
                    if(!"".equals(depr_cost)){
                        A = Double.toString(((StringUtil.nullDouble(ket_cost[0][0]) - StringUtil.nullDouble(actual_cost[0][0])) + StringUtil.nullDouble(depr_cost)) * (StringUtil.nullDouble(avg_su[0]) / StringUtil.nullDouble(a_su_count[0][0])));
                    }else{
                        depr_cost = "0";
                        A = Double.toString(((StringUtil.nullDouble(ket_cost[0][0]) - StringUtil.nullDouble(actual_cost[0][0])) + StringUtil.nullDouble(depr_cost)) * (StringUtil.nullDouble(avg_su[0]) / StringUtil.nullDouble(a_su_count[0][0])));
                    }

                    tocost_year = Double.toString(StringUtil.nullDouble(AA) / StringUtil.nullDouble(A));

                }else if(!"".equals(ket_cost[0][0])){
                    if(!"".equals(depr_cost)){
                        A = Double.toString(((StringUtil.nullDouble(ket_cost[0][0]) - StringUtil.nullDouble(actual_cost[0][0])) + StringUtil.nullDouble(depr_cost)) * (StringUtil.nullDouble(avg_su[0]) / StringUtil.nullDouble(a_su_count[0][0])));
                    }else{
                        depr_cost = "0";
                        A = Double.toString(((StringUtil.nullDouble(ket_cost[0][0]) - StringUtil.nullDouble(actual_cost[0][0])) + StringUtil.nullDouble(depr_cost)) * (StringUtil.nullDouble(avg_su[0]) / StringUtil.nullDouble(a_su_count[0][0])));
                    }
                    tocost_year = Double.toString((StringUtil.nullDouble(AA) - (StringUtil.nullDouble(mold_total[0]) + StringUtil.nullDouble(press_total[0]))) / StringUtil.nullDouble(A));

                }
            }
        }
    }

    file_1_history = file_1;
    if(!"".equals(file_1)){
        file_1WholePath = file_1;
    }
    if(!"".equals(select_cost)){
        select_cost = select_cost;
    }else{
        select_cost = "1";
    }

    Double eff_value_ = null;
    if(!StringUtil.checkNull(eff_value).equals("")){
        eff_value_ = Double.parseDouble(eff_value);
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
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/report_work.js"></script>
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

    var select_cost = "<%=select_cost%>";
    var report_pk = "<%=report_pk%>";
    if(report_pk != ""){
    	select_cost_gijun(select_cost);
    }
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
    /**********************************************
    USAGE 변경 적용
     **********************************************/
    function usage_call(){
        document.part_1.action = "/plm/jsp/cost/costreport/cost_report_1_edit.jsp?visitor=<%=visitor%>&pk_cr_group=<%=pk_cr_group%>&cost_report_add=<%=cost_report_add%>&chk_list=<%=chk_list%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>&us_change=ok&select_case=<%=select_case%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>"
        document.part_1.submit();
    }
    function back_call(){
    <%if("3".equals(visitor)){%>
        this.close();
    <%}else{%>
        window.location.href ="/plm/jsp/cost/index.html?select_name="+'<%=select_name%>';
    <%}%>
    }

    /**********************************************
    보고서2 V
     **********************************************/
    function report_call(){
        document.part_1.action = "/plm/jsp/cost/costreport/cost_report_2_edit.jsp?visitor=<%=visitor%>&pk_cr_group=<%=pk_cr_group%>&cost_report_add=<%=cost_report_add%>&chk_list=<%=chk_list%>&report_pk=<%=report_pk%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>&select_case=<%=select_case%>"
        document.part_1.submit();
    }
    /**********************************************
    산출case 검색
     **********************************************/
    function case_call(){
        window.name = "report1";

        window.open("/plm/jsp/cost/common/new_case_request.jsp?pk_cr_group=<%=pk_cr_group%>&cost_report_add=<%=cost_report_add%>&page_no=1&table_row=<%=table_row%>&report_pk=<%=report_pk%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>", "pop_100", "width=950,height=600,scrollbars=yes,resizable=yes");
    }

    /**********************************************
     요청서 보기V
    **********************************************/
    function request_call(){
        window.open("./cost_re_view_test.asp?pk_cr_group=<%//=pk_cr_group%>&rev_no=<%//=rev_no%>&data_type=main&group_case_count=<%//=user_case_count%>", "pop_999", "width=1024,height=800,scrollbars=yes,resizable=yes");
    }
    /**********************************************
    산출창 보기  - 작업내역보기
    **********************************************/
    function request_call_work(){
        window.open("/plm/jsp/cost/costWork/costWork.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&data_type=main&rev_no=<%=rev_no%>&group_case_count=<%=user_case_count%>", "pop_888", "width=1024,height=800,scrollbars=yes,resizable=yes");
    }
    /**********************************************
    DB저장 V
    **********************************************/
    function DB_call(){
        document.part_1.action = "/plm/jsp/cost/costreport/cost_report_db.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&cost_report_add="+'<%=cost_report_add%>'+"&table_row="+'<%=table_row%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&page_no=1&chk_list="+'<%=chk_list%>';
        //alert("/plm/jsp/cost/costreport/cost_report_db.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&cost_report_add="+'<%=cost_report_add%>'+"&table_row="+'<%=table_row%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&page_no=1&chk_list="+'<%=chk_list%>');
        document.part_1.submit();
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
        /*var JB_day			= document.part_1.JB_day.value;
        var p_leader_day	= document.part_1.p_leader_day.value;
        var r_owner_day	= document.part_1.r_owner_day.value;
        var r_pre_day		= document.part_1.r_pre_day.value;*/
        var note_4				= document.part_1.note_4.value;
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
        var JB_day			= document.part_1.JB_day.value;
        var p_leader_day	= document.part_1.p_leader_day.value;
        var r_owner_day	= document.part_1.r_owner_day.value;
        var r_pre_day		= document.part_1.r_pre_day.value;
        var note_4				= document.part_1.note_4.value;
        document.part_1.action = "/plm/jsp/cost/costreport/work_finish.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&JB_day="+JB_day+"&p_leader_day="+p_leader_day+"&r_owner_day="+r_owner_day+"&r_pre_day="+r_pre_day+"&note_4="+note_4;
        document.part_1.submit();
    }

    /**********************************************
    * 등록된 파일보기
    **********************************************/
    function file_call1(){
        //window.open("<%//=file_1WholePath%>");
    }

    function filecheck_file(file){
        var report_pk ='<%=report_pk%>';
        if(report_pk == 'null' || report_pk == null || report_pk == ""){
        	alert("보고서를 등록 한 후 파일첨부 가능합니다.");
        	return;
        }
        var file_2_name			= document.part_1.file_2_name.value;
        var file_3_name			= document.part_1.file_3_name.value;
        var file_4_name			= document.part_1.file_4_name.value;
        var file_5_name			= document.part_1.file_5_name.value;
        var file_6_name			= document.part_1.file_6_name.value;

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
    <input name="approval" type="hidden" value="<%=approval%>">
    <input name="step_no" type="hidden" value="<%=step_no%>">
    <input name="note_5" type="hidden" value="<%=note_5%>">
    <input name="note_5_1" type="hidden" value="<%=note_5_1%>">
    <input name="t_note_1" type="hidden" value="<%//=t_note_1%>">
    <input name="t_note_2" type="hidden" value="<%//=t_note_2%>">
    <input name="t_note_3" type="hidden" value="<%//=t_note_3%>">
    <input name="t_note_4" type="hidden" value="<%//=t_note_4%>">
    <input name="DB_case_2" type="hidden" value="<%//=DB_case_2%>">
    <input name="w_name" type="hidden" value="<%=w_name%>">
    <input name="report_pk" type="hidden" value="<%=report_pk%>">
    <input name="table_row_count" type="hidden" value="<%=table_row_count%>">
    <input name="select_case" type="hidden" value="<%=select_case%>">

<%

    for(int j=0; j < Integer.parseInt(StringUtil.checkNullZero(table_row_count)); j++){
%>
    <input name="total_sales_1_<%=j%>" type="hidden" value="<%=total_sales_1%>">
    <input name="total_sales_2_<%=j%>" type="hidden" value="<%=total_sales_2%>">
    <input name="total_sales_3_<%=j%>" type="hidden" value="<%=total_sales_3%>">
    <input name="total_sales_4_<%=j%>" type="hidden" value="<%=total_sales_4%>">
    <input name="total_sales_5_<%=j%>" type="hidden" value="<%=total_sales_5%>">
    <input name="total_sales_6_<%=j%>" type="hidden" value="<%=total_sales_6%>">
    <input name="total_sales_7_<%=j%>" type="hidden" value="<%=total_sales_7%>">
    <input name="total_sales_8_<%=j%>" type="hidden" value="<%=total_sales_8%>">
    <input name="profit_1_<%=j%>" type="hidden" value="<%=profit_1%>">
    <input name="profit_2_<%=j%>" type="hidden" value="<%=profit_2%>">
    <input name="profit_3_<%=j%>" type="hidden" value="<%=profit_3%>">
    <input name="profit_4_<%=j%>" type="hidden" value="<%=profit_4%>">
    <input name="profit_5_<%=j%>" type="hidden" value="<%=profit_5%>">
    <input name="profit_6_<%=j%>" type="hidden" value="<%=profit_6%>">
    <input name="profit_7_<%=j%>" type="hidden" value="<%=profit_7%>">
    <input name="profit_8_<%=j%>" type="hidden" value="<%=profit_8%>">
    <input name="file_2" type="hidden" value="<%=file_2 %>">
    <input name="file_3" type="hidden" value="<%=file_3 %>">
    <input name="file_4" type="hidden" value="<%=file_4 %>">
    <input name="file_5" type="hidden" value="<%=file_5 %>">
    <input name="file_6" type="hidden" value="<%=file_6 %>">
    <input name="per_profit_1_<%=j%>" type="hidden" value="<%if(!"".equals(per_profit_1)){out.println(String.format("%.4f", StringUtil.nullDouble(per_profit_1)));}%>">
    <input name="per_profit_2_<%=j%>" type="hidden" value="<%if(!"".equals(per_profit_2)){out.println(String.format("%.4f", StringUtil.nullDouble(per_profit_2)));}%>">
    <input name="per_profit_3_<%=j%>" type="hidden" value="<%if(!"".equals(per_profit_3)){out.println(String.format("%.4f", StringUtil.nullDouble(per_profit_3)));}%>">
    <input name="per_profit_4_<%=j%>" type="hidden" value="<%if(!"".equals(per_profit_4)){out.println(String.format("%.4f", StringUtil.nullDouble(per_profit_4)));}%>">
    <input name="per_profit_5_<%=j%>" type="hidden" value="<%if(!"".equals(per_profit_5)){out.println(String.format("%.4f", StringUtil.nullDouble(per_profit_5)));}%>">
    <input name="per_profit_6_<%=j%>" type="hidden" value="<%if(!"".equals(per_profit_6)){out.println(String.format("%.4f", StringUtil.nullDouble(per_profit_6)));}%>">
    <input name="per_profit_7_<%=j%>" type="hidden" value="<%if(!"".equals(per_profit_7)){out.println(String.format("%.4f", StringUtil.nullDouble(per_profit_7)));}%>">
    <input name="per_profit_8_<%=j%>" type="hidden" value="<%if(!"".equals(per_profit_8)){out.println(String.format("%.4f", StringUtil.nullDouble(per_profit_8)));}%>">
<%
        for(p=0;p <= 2; p++){
            /*ket_cost[j][p] = StringUtil.checkNull(ket_cost[j][p]);
            t_pk_cw[j][p] = StringUtil.checkNull(t_pk_cw[j][p]);
            t_actual_cost[j][p] = StringUtil.checkNull(t_actual_cost[j][p]);
            t_earn_rate[j][p] = StringUtil.checkNull(t_earn_rate[j][p]);*/
%>
    <input name="ket_cost_<%=Integer.toString(j)+Integer.toString(p)%>" 	type="hidden" value="<%= ket_cost[j][p]%>">
    <input name="S_pk_cw<%=Integer.toString(j)+Integer.toString(p)%>" 		type="hidden" value="<%=t_pk_cw[j][p]%>">
    <input name="S_actual_cost_<%=Integer.toString(j)+Integer.toString(p)%>" 	type="hidden" value="<%=t_actual_cost[j][p]%>">
    <input name="S_earn_rate_<%=Integer.toString(j)+Integer.toString(p)%>" 	type="hidden" value="<%=t_earn_rate[j][p]%>">
<%
        }
    }
%>
        <table width="1024" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <table width="1022" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="left" valign="bottom"><img src="/plm/jsp/cost/acc_img/tap_report_1_big.gif" border="0"><img src="/plm/jsp/cost/acc_img/tap_report_2_small.gif" width="102" height="16" border="0" onClick="report_call();" style="cursor:pointer;"/></td>
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
																<%	
																out.println(StringUtil.ChangeDeptNoCPString(team, ""));
																/*if("1".equals(team)){
																		out.println("커넥터연구개발1팀");
																	}else if("11".equals(team)){
																		out.println("커넥터연구개발2팀");
																	}else if("12".equals(team)){
																		out.println("커넥터연구개발3팀");
																	}else if("13".equals(team)){
																		out.println("커넥터연구개발센타");
																	}else if("22".equals(team)){
																		out.println("전장모듈연구개발1팀");
																	}else if("23".equals(team)){
																		out.println("전장모듈연구개발2팀");
																	}else if("24".equals(team)){
																		out.println("전장모듈연구개발3팀");
																	}else if("3".equals(team)){
																		out.println("연구개발3팀");
																	}else if("4".equals(team)){
																		out.println("연구개발4팀");
																	}else if("6".equals(team)){
																		out.println("연구개발6팀");
																	}else if("21".equals(team)){
																		out.println("시작개발1팀");
																	}*/
																%>
																<td width="76" height="21" bgcolor="#FFFFFF" class="style1"><%=f_name %></td>
															</tr>
															<tr>
																<td height="21" bgcolor="#FFFFFF" class="style1">자동차영업팀</td>
																<td height="21" bgcolor="#FFFFFF" class="style1"><%=a_name %></td>
															</tr>
														</table>
													</td>

													<td width="446" class="style10">개발 원가 결과 보고 </td>
                                                    <td width="122">
                                                        <table width="106" border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="106" height="18" class="style9">
                                                                <%if(dev_step.equals("개발검토")){out.println("■ 개발검토"); }else{out.println("□ 개발검토");}%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td height="18" class="style9">
                                                                <%if(dev_step.equals("개발착수")){out.println("■ 개발착수/진행중"); }else{out.println("□ 개발착수/진행중");}%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td height="18" class="style9">
                                                                <%if(dev_step.equals("개발완료")){out.println("■ 개발완료"); }else{out.println("□ 개발완료");}%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td height="18" class="style9">
                                                                <%if(dev_step.equals("설계변경")){out.println("■ 설계변경"); }else{out.println("□ 설계변경");}%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td height="18" class="style9">
                                                                <%if(dev_step.equals("기타")){out.println("■ 기  타"); }else{out.println("□ 기  타");}%>
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
                                                                <!--  <td width="57" height="19" bgcolor="#FFFFFF" class="style1">센타장</td>-->
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

                                                                <!--  <td bgcolor="#FFFFFF" class="style1">
                                                                <%if(step_no.equals("4") && (position.equals("센타장") || login_id.equals("admin"))){%>
                                                                    <img src="/plm/jsp/cost/acc_img/btn_pass_call_3.gif" border="0" onClick="pass_call_3();" style="cursor:pointer;"/>
                                                                <%}else if(step_no.equals("2") || step_no.equals("3")){out.println("");}else{if(!StringUtil.checkNull(Leader_day).equals("")){out.println(Leader_name+"<br>"+Leader_day.substring(0,4)+"<br>"+Leader_day.substring(5,10));}} %>
                                                                </td>-->


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
                                                                <td height="22" colspan="8" valign="bottom" bgcolor="#FFFFFF" class="style3">1. 개발배경 및 투자비 현황<%if(!file_1.equals("-") && !file_1.equals("") ){ %>&nbsp;&nbsp;<font color="#0000ff" size="+2">※첨부된 보고서파일(<img src="/plm/jsp/cost/acc_img/file.png" width="16" height="16" border="0" onClick="file_call1();" style="cursor:pointer;"/>)</font><%} %></td>
                                                            </tr>
                                                        </table>

                                                        <table width="1020" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td width="111" height="21" rowspan="2" class="style5">고객사명</td>
                                                                <td width="260" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1"><input name="customer_F" class="style1" size="52" value="<%=customer_F%>"></td>
                                                                <td width="91" rowspan="9" class="style5">신규 투자비<br />검 토 현 황<br /></td>
                                                                <td width="74" height="21" rowspan="2" class="style5">구분</td>
                                                                <td width="59" height="21" rowspan="2" class="style5">수량</td>
                                                                <td width="59" height="21" class="style5">1안</td>
                                                                <td width="59" height="21" class="style5">2안</td>
                                                                <td width="59" height="21" class="style5">3안</td>
                                                                <!--  <td width="59" height="21" class="style5">4안</td>
                                                                <td width="59" height="21" class="style5">5안</td>-->
                                                                <td width="59" height="21" rowspan="2" class="style5">상각기준</td>
                                                                <td width="58" rowspan="8" class="style5">투자비<br/>회수기간</td>
                                                            </tr>
                                                            <tr>
                                                                <td width="59" height="18" class="style5"><input name="case_to_note_1" class="style1" size="9" value="<%=case_to_note_1%>"></td>
                                                                <td width="59" height="18" class="style5"><input name="case_to_note_2" class="style1" size="9" value="<%=case_to_note_2%>"></td>
                                                                <td width="59" height="18" class="style5"><input name="case_to_note_3" class="style1" size="9" value="<%=case_to_note_3%>"></td>
                                                                <!--  <td width="59" height="18" class="style5"><input name="case_to_note_4" class="style1" size="9" value="<%//=case_to_note_4%>"></td>
                                                                <td width="59" height="18" class="style5"><input name="case_to_note_5" class="style1" size="9" value="<%//=case_to_note_5%>"></td>-->
                                                            </tr>
                                                            <tr>
                                                                <td width="111" height="21" class="style5">제품명</td>
                                                                <td width="260" height="21" bgcolor="#FFFFFF" class="style1"><input name="pjt_name" class="style1" size="52" value="<%=pjt_name%>" ></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">Mold</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_count" 	type="text" class="style1" size="9" value="<%=StringUtil.checkNull(mold_count[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_total_0" class="style1" size="9" value="<%=StringUtil.checkNull(mold_total[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(mold_total[1])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(mold_total[2])%>"></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_total_3" class="style1" size="9" value="<%//=mold_total_4%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="mold_total_4" class="style1" size="9" value="<%//=mold_total_5%>"></td>-->

                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="m_depr_stan" class="style1" size="9"
                                                                value="<%if(!StringUtil.checkNull(m_depr_stan).equals("")){out.println(m_depr_stan);}else if(!StringUtil.checkNullZero(mold_total[0]).equals("0") ){out.println("판매량감가");} %>">
                                                                </td>
                                                            </tr>
                                                            <tr>

                                                                <td width="111" height="21" class="style5">Project No. </td>
                                                                <td width="260" height="21" bgcolor="#FFFFFF" class="style1"><input name="pjt_no" class="style1" size="52" value="<%=pjt_no%>" readonly ></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">Press</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_count" 	type="text" class="style1" size="9" value="<%=StringUtil.checkNull(press_count[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_0" class="style1" size="9" value="<%=StringUtil.checkNull(press_total[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(press_total[1])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(press_total[2])%>"></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_3" class="style1" size="9" value="<%//=press_total_4%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="press_total_4" class="style1" size="9" value="<%//=press_total_5%>"></td>-->
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="p_depr_stan" class="style1" size="9"
                                                                value="<%if(!StringUtil.checkNull(p_depr_stan).equals("")){out.println(p_depr_stan);}else if(!StringUtil.checkNullZero(press_total[0]).equals("0") ){out.println("판매량감가");} %>">
                                                                </td>
                                                            </tr>
                                                            <tr>

                                                                <td width="111" height="20" class="style5">적용차종</td>
                                                                <td width="260" height="20" bgcolor="#FFFFFF" class="style1"><input name="car_type" class="style1" size="52" value="<%=car_type%>"></td>
                                                                <td width="74" height="20" bgcolor="#FFFFFF" class="style1">조립설비 </td>
                                                                <td width="59" height="20" bgcolor="#FFFFFF" class="style1"><input name="line_count" 	type="text" class="style1" size="9" value="<%=StringUtil.checkNull(line_count[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_0" class="style1" size="9" value="<%=StringUtil.checkNull(line_total[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(line_total[1])%>"></td>
                                                                <td width="59" height="20" bgcolor="#FFFFFF" class="style1"><input name="line_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(line_total[2])%>"></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_3" class="style1" size="9" value="<%//=line_total_4%>"></td>
                                                                <td width="59" height="20" bgcolor="#FFFFFF" class="style1"><input name="line_total_4" class="style1" size="9" value="<%//=line_total_5%>"></td>-->
                                                                <td width="59" height="20" bgcolor="#FFFFFF" class="style1"><input name="L_depr_stan" class="style1" size="9" value="<%if(!StringUtil.checkNull(L_depr_stan).equals("")){out.println(L_depr_stan);}else if(!StringUtil.checkNullZero(line_total[0]).equals("0") ){out.println("판매량감가");} %>"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="111" height="21" class="style5">적용부위</td>
                                                                <td width="260" height="21" bgcolor="#FFFFFF" class="style1"><input name="app_part" class="style1" size="52" value="<%=app_part%>"></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">검사설비</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_count_01" type="text" class="style1" size="9" value=""></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_01" class="style1" size="9" value=""></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_11" class="style1" size="9" value=""></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_21" class="style1" size="9" value=""></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_31" class="style1" size="9" value=""></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="line_total_41" class="style1" size="9" value=""></td>-->
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="L_depr_stan1" class="style1" size="9" value=""></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="111" height="21" class="style5">물류흐름</td>
                                                                <td width="260" height="21" bgcolor="#FFFFFF" class="style1"><input name="report_dest" class="style1" size="52" value="<%=report_dest%>"></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">기타 투자비 </td>

                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_count" 	type="text" class="style1" size="9" value="<%=StringUtil.checkNull(pack_count[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_0" class="style1" size="9" value="<%=StringUtil.checkNull(pack_total[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(pack_total[1])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(pack_total[2])%>"></td>
                                                                <!-- <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_3" class="style1" size="9" value="<%//=StringUtil.checkNull(pack_total_4)%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_total_4" class="style1" size="9" value="<%//=StringUtil.checkNull(pack_total_5)%>"></td> -->
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_depr_stan" class="style1" size="9" value="<%if(!StringUtil.checkNull(pack_depr_stan).equals("")){out.println(pack_depr_stan);}else if(!StringUtil.checkNullZero(pack_total[0]).equals("0") ){out.println("판매량감가");} %>"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="111" rowspan="2" class="style5">검토목적</td>
                                                                <td width="260" rowspan="2" bgcolor="#FFFFFF" class="style1"><input name="request_txt" class="style1" size="52" value="<%=request_txt%>"></td>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">포장용 투자비</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_count" type="text" class="style1" size="9" value="<%=StringUtil.checkNull(repack_count[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_0" class="style1" size="9" value="<%=StringUtil.checkNull(repack_total[0])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_1" class="style1" size="9" value="<%=StringUtil.checkNull(repack_total[1])%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_2" class="style1" size="9" value="<%=StringUtil.checkNull(repack_total[2])%>"></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_3" class="style1" size="9" value="<%//=StringUtil.checkNull(repack_total_4)%>"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_total_4" class="style1" size="9" value="<%//=StringUtil.checkNull(repack_total_5)%>"></td>-->
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><input name="repack_depr_stan" class="style1" size="9" value="<%if(!StringUtil.checkNull(repack_depr_stan).equals("")){out.println(repack_depr_stan);}else if(!StringUtil.checkNullZero(repack_total[0]).equals("0") ){out.println("판매량감가");} %>"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="74" height="21" bgcolor="#FFFFFF" class="style1">합 계</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(AA, "천원", 0, 0)%></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(AB, "천원", 0, 0)%></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(AC, "천원", 0, 0)%></td>
                                                                <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"></td>
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1"></td>-->
                                                                <td width="59" height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                                <td width="58" height="21" class="style12"><input name="tocost_year" class="style1" size="2" value="<%=String.format("%.2f",StringUtil.nullDouble(tocost_year)) %>">년</td>
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
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_1[0], "", 0, 0)%></td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_2[0], "", 0, 0)%></td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_3[0], "", 0, 0)%></td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_4[0], "", 0, 0)%></td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_5[0], "", 0, 0)%></td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_6[0], "", 0, 0)%></td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_7[0], "", 0, 0)%></td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_8[0], "", 0, 0)%></td>
                                                                <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(avg_su[0], "", 0, 0)%></td>
                                                                <td height="21" rowspan="2" class="style5">비철 LME시세 </td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><%=lme_cu%><span class="style7"> [＄/Ton]</span></td>
                                                                <td rowspan="5" bgcolor="#FFFFFF" class="style1"><textarea name="note_1" cols="40" rows="9" class="style9"><%=note_1%></textarea></td>
                                                            </tr>
                                                            <tr>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><%if(!"".equals(u_ex_rate)){out.println(u_ex_rate+"<span class='style7'>[￦/$]</span>");} %></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="121" height="21" class="style5">매출액(백만원)</td>
                                                                <td id = "total_sales_1_view" width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_1, "", 0, 0)%></td>
                                                                <td id = "total_sales_2_view" width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_2, "", 0, 0)%></td>
                                                                <td id = "total_sales_3_view" width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_3, "", 0, 0)%></td>
                                                                <td id = "total_sales_4_view" width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_4, "", 0, 0)%></td>
                                                                <td id = "total_sales_5_view" width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_5, "", 0, 0)%></td>
                                                                <td id = "total_sales_6_view" width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_6, "", 0, 0)%></td>
                                                                <td id = "total_sales_7_view" width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_7, "", 0, 0)%></td>
                                                                <td id = "total_sales_8_view" width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_8, "", 0, 0)%></td>
                                                                <td id = "sum_sales_view"  width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(sum_sales, "", 0, 0)%></td>
                                                                <td height="21" class="style5">포장유형</td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="pack_type" class="style1" size="19" value="<%=pack_type%>"></td>
                                                            </tr>

                                                            <tr>
                                                                <td width="121" height="21" class="style5">영업이익(백만원)</td>
                                                                <td id = "profit_1_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(profit_1)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%if(!profit_1.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_1, 0)));}%></td>
                                                                <td id = "profit_2_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(profit_2)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%if(!profit_2.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_2, 0)));}%></td>
                                                                <td id = "profit_3_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(profit_3)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%if(!profit_3.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_3, 0)));}%></td>
                                                                <td id = "profit_4_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(profit_4)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%if(!profit_4.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_4, 0)));}%></td>
                                                                <td id = "profit_5_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(profit_5)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%if(!profit_5.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_5, 0)));}%></td>
                                                                <td id = "profit_6_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(profit_6)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%if(!profit_6.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_6, 0)));}%></td>
                                                                <td id = "profit_7_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(profit_7)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%if(!profit_7.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_7, 0)));}%></td>
                                                                <td id = "profit_8_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(profit_8)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%if(!profit_8.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_8, 0)));}%></td>
                                                                <td id = "sum_profit_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(sum_profit)) >= 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(sum_profit, "", 0, 0)%></td>
                                                                <td height="21" class="style5">생산처 구분 </td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="pro_1" class="style1" size="19" value="<%=pro_1%>"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="121" height="21" class="style5">영업이익율</td>
                                                                <td id = "per_profit_1_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_1)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(per_profit_1, "%", 100, 1)%></td>
                                                                <td id = "per_profit_2_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_2)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(per_profit_2, "%", 100, 1)%></td>
                                                                <td id = "per_profit_3_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_3)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(per_profit_3, "%", 100, 1)%></td>
                                                                <td id = "per_profit_4_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_4)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(per_profit_4, "%", 100, 1)%></td>
                                                                <td id = "per_profit_5_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_5)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(per_profit_5, "%", 100, 1)%></td>
                                                                <td id = "per_profit_6_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_6)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(per_profit_6, "%", 100, 1)%></td>
                                                                <td id = "per_profit_7_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_7)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(per_profit_7, "%", 100, 1)%></td>
                                                                <td id = "per_profit_8_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_8)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(per_profit_8, "%", 100, 1)%></td>
                                                                <%if(report_pk.equals("1780")){ %>
                                                                <td width="45" height="21" bgcolor="#FFFFFF" class="style13">20.7 %</td>
                                                                <%}else{ %>
                                                                <td id = "sum_per_p_view" width="45" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(sum_per_p)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <%=StringUtil.DoubleFormat(sum_per_p, "%", 100, 1)%></td>
                                                                <%} %>
                                                                <td height="21" class="style5">생산 효율 </td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="eff_value" class="style1" size="17" value="<%=eff_value_%>">%</td>
                                                            </tr>
                                                        </table>

                                                        <table width="1020" border="0" cellpadding="0" cellspacing="1">
                                                            <tr>
                                                                <td height="21" colspan="12" valign="bottom" class="style3">3. 원가계산 결과 </td>
                                                                <td width="52" height="21" valign="bottom" class="style3">단위: 원</td>
                                                            </tr>
                                                        </table>

                                                        <table width="1020" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td width="17" rowspan="<%=Integer.parseInt(StringUtil.checkNullZero(table_row)) + 4%>" class="style5">가<br/>격<br/>정<br/>보</td>
                                                                <td width="253" rowspan="2" class="style5">제품명</td>
                                                                <td width="36" rowspan="2" class="style5">적용<br>U/S</td>
                                                                <td width="45" rowspan="2" class="style5">판매<br/>목표가</td>
                                                                <td width="45" rowspan="2" class="style5">목표<br/>수익율</td>

                                                                <td height="40" colspan="2" valign="bottom" class="style5"><span class="change"><input type="radio" name="select_cost" value="1"  <%if(select_cost.equals("1")){ %>checked<%} %> onclick="select_fk_cost(this.value);">1안<br><input name="case_1_note" class="style1" size="16" value="<%=case_1_note%>"></span></td>
                                                                <td height="40" colspan="2" valign="bottom" class="style5"><span class="change_1"><input type="radio" name="select_cost" value="2"  <%if(select_cost.equals("2")){ %>checked<%} %> onclick="select_fk_cost(this.value);">2안<br><input name="case_2_note" class="style1" size="16" value="<%=case_2_note%>"></span></td>
                                                                <td height="40" colspan="2" valign="bottom" class="style5"><span class="change_2"><input type="radio" name="select_cost" value="3"  <%if(select_cost.equals("3")){ %>checked<%} %> onclick="select_fk_cost(this.value);">3안<br><input name="case_3_note" class="style1" size="16" value="<%=case_3_note%>"></span></td>
                                                                <!-- <td height="40" colspan="2" valign="bottom" class="style5"><input type="radio" name="select_cost" value="4" >4안<br><input name="case_4_note" class="style1" size="16" value="<%//=case_4_note%>"></td>
                                                                <td height="40" colspan="2" valign="bottom" class="style5"><input type="radio" name="select_cost" value="5" >5안<br><input name="case_5_note" class="style1" size="16" value="<%//=case_5_note%>"></td> -->
                                                                <td width="177" rowspan="2" class="style5">비고</td>
                                                            </tr>

                                                            <tr>
                                                                <td width="46" height="21" class="style5"><span class="change_left">총원가</span></td>
                                                                <td width="40" height="21" class="style5"><span class="change_right">수익율</span></td>
                                                                <td width="46" height="21" class="style5"><span class="change_left_1">총원가</span></td>
                                                                <td width="40" height="21" class="style5"><span class="change_right_1">수익율</span></td>
                                                                <td width="46" height="21" class="style5"><span class="change_left_2">총원가</span></td>
                                                                <td width="40" height="21" class="style5"><span class="change_right_2">수익율</span></td>
                                                                <!--  <td width="46" height="21" class="style5">총원가</td>
                                                                <td width="40" height="21" class="style5">수익율</td>
                                                                <td width="46" height="21" class="style5">총원가</td>
                                                                <td width="40" height="21" class="style5">수익율</td>-->
                                                            </tr>
															<%
															    for(int j=0; j < Integer.parseInt(StringUtil.checkNullZero(table_row_count)); j++){
															        if(!"0".equals(t_earn_rate[j][0]) || !"".equals(t_earn_rate[j][0])){
															            t_earn_rate[j][0] = t_earn_rate[j][0];
															        }else{
															            t_earn_rate[j][0] = "0";
															        }
															        if("no".equals(ket_cost_value)){
															            t_earn_rate[j][1] = "0";
															            t_earn_rate[j][2] = "0";
															        }
															        if(!"".equals(t_earn_rate[j][1])){
															        }else{
															            t_earn_rate[j][1] = "0";
															        }
															        if(!"".equals(t_earn_rate[j][2])){
															        }else{
															            t_earn_rate[j][2] = "0";
															        }

															        if(!"".equals(StringUtil.checkNull(t_actual_cost[j][0]))){
															            if(StringUtil.nullDouble(t_actual_cost[j][0]) > 0){
															                t_actual_cost[j][0] = String.format("%,.2f", StringUtil.nullDouble(t_actual_cost[j][0]));
															            }else{
															                t_actual_cost[j][0] = "";
															            }
															        }

															        if(!"".equals(StringUtil.checkNull(t_earn_rate[j][0]))){
															            t_earn_rate[j][0] = String.format("%,.2f", StringUtil.nullDouble(t_earn_rate[j][0])*100);
															        }else{
															            t_earn_rate[j][0] = "";
															        }
															        if(!"".equals(StringUtil.checkNull(t_actual_cost[j][1]))){

															            if(StringUtil.nullDouble(t_actual_cost[j][1]) > 0){
															                t_actual_cost[j][1] = String.format("%,.2f", StringUtil.nullDouble(t_actual_cost[j][1]));
															            }else{
															                t_actual_cost[j][1] = "";
															            }
															        }
															        if(!"".equals(StringUtil.checkNull(t_earn_rate[j][1]))){
															            t_earn_rate[j][1] = String.format("%,.1f", StringUtil.nullDouble(t_earn_rate[j][1])*100);
															        }else{
															            t_earn_rate[j][1] = "";
															        }
															        if(!"".equals(StringUtil.checkNull(t_actual_cost[j][2]))){
															            if(StringUtil.nullDouble(t_actual_cost[j][2]) > 0){
															                t_actual_cost[j][2] = String.format("%,.1f", StringUtil.nullDouble(t_actual_cost[j][2]));
															            }else{
															                t_actual_cost[j][2] = "";
															            }
															        }
															        if(!"".equals(StringUtil.checkNull(t_earn_rate[j][2]))){
															            t_earn_rate[j][2] = String.format("%,.1f", StringUtil.nullDouble(t_earn_rate[j][2])*100);
															        }else{
															            t_earn_rate[j][2] = "";
															        }
															        if(t_earn_rate[j][0].equals("0.0")){
															            t_earn_rate[j][0] = "";
															        }
															        if(t_earn_rate[j][1].equals("0.0")){
															            t_earn_rate[j][1] = "";
															        }
															        if(t_earn_rate[j][2].equals("0.0")){
															            t_earn_rate[j][2] = "";
															        }


															%>
                                                            <tr>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.checkNull(part_name[j])%></td>
                                                                <td width="35"  bgcolor="#FFFFFF" class="style1"><input name="pro_usage<%=j%>" class="style1" size="2" value="<%=StringUtil.checkNull(pro_usage[j])%>"></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(ket_cost[j][0], "원", 0, 2)%></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.checkNull(target_cost[j])%></td>
                                                                <td width="55"  bgcolor="#FFFFFF" class="style1"><span class="change_left"><%=StringUtil.DoubleFormat(t_actual_cost[j][0], "원", 0, 2)%></span></td>
                                                                <td width="55" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(t_earn_rate[j][0])) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right"><%if(!t_earn_rate[j][0].equals("")){out.println(t_earn_rate[j][0]+"%");}%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_1"><%=StringUtil.DoubleFormat(t_actual_cost[j][1], "원", 0, 2)%></span></td>
                                                                <td width="55" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(t_earn_rate[j][1])) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_1"><%if(!t_earn_rate[j][1].equals("")){out.println(t_earn_rate[j][1]+"%");}%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_2"><%=StringUtil.DoubleFormat(t_actual_cost[j][2], "원", 0, 2)%></span></td>
                                                                <td width="55" height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(t_earn_rate[j][2])) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_2"><%if(!t_earn_rate[j][2].equals("")){out.println(t_earn_rate[j][2]+"%");}%></span></td>
                                                                <!--  <td width="46" height="21" bgcolor="#FFFFFF" class="style1"><%//=actual_cost_4%></td>
                                                                <td width="40" height="21" bgcolor="#FFFFFF" class="style13"><%//=earn_rate_4%></td>
                                                                <td width="46" height="21" bgcolor="#FFFFFF" class="style1"><%//=actual_cost_5%></td>
                                                                <td width="40" height="21" bgcolor="#FFFFFF" class="style13"><%//=earn_rate_5%></td>-->
															<%
																if(j ==0){
															%>
                                                                <td rowspan="<%=table_row%>" bgcolor="#FFFFFF" class="style1"><textarea name="note_2" cols="32" rows="<%=Integer.parseInt(table_row)+1%>" class="style9"><%=note_2%></textarea></td>
															<%
        														}
    														}

															    if(!"ok".equals(cost_report_add) || "ok".equals(us_change)){
															        if(!"".equals(actual_cost_sum_1)){
															            earn_rate_sum_1 = Double.toString(1 - (StringUtil.nullDouble(actual_cost_sum_1) / StringUtil.nullDouble(ket_cost_sum)));
															            earn_rate_sum_1 = StringUtil.DoubleFormat(earn_rate_sum_1, "%", 100, 1).replaceAll("%","");
															        }

															        if("ok".equals(ket_cost_value)){
															            if(!"".equals(actual_cost_sum_2)){
															                earn_rate_sum_2 = Double.toString(1 - (StringUtil.nullDouble(actual_cost_sum_2) / StringUtil.nullDouble(ket_cost_sum)));
															                earn_rate_sum_2 = StringUtil.DoubleFormat(earn_rate_sum_2, "%", 100, 1).replaceAll("%","");
															            }
															            if(!"".equals(actual_cost_sum_3)){
															                earn_rate_sum_3 = Double.toString(1 - (StringUtil.nullDouble(actual_cost_sum_3) / StringUtil.nullDouble(ket_cost_sum)));
															                earn_rate_sum_3 = StringUtil.DoubleFormat(earn_rate_sum_3, "%", 100, 1).replaceAll("%","");
															            }
															        }
															    }
																%>

                                                            </tr>
                                                            <tr>
                                                                <td height="21" class="style5">Total</td>
                                                                <td width="35" bgcolor="#FFFFFF" class="style1"><input name="usage_btn" type="button" class="style1" value="적용" onClick="usage_call();"></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(ket_cost_sum, "원", 0, 2) %></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                                <td width="55" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_bottom"><input name="actual_cost_sum_1" class="style1" size="5" value="<%=StringUtil.DoubleFormat(actual_cost_sum_1, "", 0, 2)%>"></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><span class="change_right_bottom"><input name="earn_rate_sum_1" class="style1" size="3" value="<%if(!earn_rate_sum_1.equals("")){out.println(earn_rate_sum_1+"%");} %>"></span></td>
                                                                <td width="55" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_bottom_1"><input name="actual_cost_sum_2" class="style1" size="5" value="<%=StringUtil.DoubleFormat(actual_cost_sum_2, "", 0, 2)%>"></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><span class="change_right_bottom_1"><input name="earn_rate_sum_2" class="style1" size="3" value="<%if(!earn_rate_sum_2.equals("")){out.println(earn_rate_sum_2+"%");} %>"></span></td>
                                                                <td width="55" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_bottom_2"><input name="actual_cost_sum_3" class="style1" size="5" value="<%=StringUtil.DoubleFormat(actual_cost_sum_3, "", 0, 2)%>"></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><span class="change_right_bottom_2"><input name="earn_rate_sum_3" class="style1" size="3" value="<%if(!earn_rate_sum_3.equals("")){out.println(earn_rate_sum_3+"%");} %>"></span></td>
                                                                <!--  <td width="46" height="21" bgcolor="#FFFFFF" class="style1"><input name="actual_cost_sum_3" class="style1" size="5" value="<%//=actual_cost_sum_4%>"></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="earn_rate_sum_3" class="style1" size="2" value="<%//=earn_rate_sum_4%>">%</td>
                                                                <td width="46" height="21" bgcolor="#FFFFFF" class="style1"><input name="actual_cost_sum_3" class="style1" size="5" value="<%//=actual_cost_sum_5%>"></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1"><input name="earn_rate_sum_3" class="style1" size="2" value="<%//=earn_rate_sum_5%>">%</td>-->
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
				                    	<td valign="bottom"><img src="/plm/jsp/cost/acc_img/invest_btn.gif" border="0" onClick="invest_call();"style="cursor:pointer;"/></td>
				                    	<td valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_print.gif" border="0" onClick="print_call();"style="cursor:pointer;"/></td>
				                    	<td valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_request_call.gif" border="0" onClick="request_call_work();" style="cursor:pointer;"/></td>
				                    	<td valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_case_call.gif" border="0" onClick="case_call();" style="cursor:pointer;"/ ></td>
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