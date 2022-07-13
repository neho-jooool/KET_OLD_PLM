package e3ps.cost.control;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;
public class CostResultAcc extends CostAccVarialbe {
    Connection conn = null;

    public String[] resultAcc(String p_pk_cr_group,String rev_no,String page_name) throws Exception{
        System.out.println("result acc 에 오는지????");
        try {
            conn = DBUtil.getConnection();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        PreparedStatement pstmt = null;

        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" select pk_cr ,table_row,      pjt_name 					    	");
        sb.append("       ,a_name   ,pjt_no      ,rev_pk      ,rev_no   ,dr_step    ");
        sb.append("       ,sub_step ,a.request_day ,request_txt ,team     ,step_no    ");
        sb.append("       ,f_day    ,Leader_day  ,Leader_name ,approval ,dev_step   ");
        sb.append("       ,pps      ,app_part    ,etc_note    ,file_1   ,file_2     ");
        sb.append("       ,file_3   ,b.f_name    ,pk_pid                            ");
        sb.append("  from cost_request a, cost_productinfo b,wfinfo c               ");
        sb.append(" where b.pk_cr_group = '" + p_pk_cr_group + "'");
        sb.append("   and b.rev_no      = '" + rev_no + "'");
        sb.append("   and a.fk_pid      =  b.pk_pid                                 ");
        sb.append("   and a.pk_cr       =  c.fk_cost(+)                             ");
        sb.append("   and nvl(c.cost_flag,'R') = 'R'                                ");

        String pk_cr			= "";
        table_row       	    = "";
        String pjt_name         = "";
        String a_name           = "";
        String pjt_no           = "";
        String rev_pk           = "";
        String rs_rev_no        = "";
        String dr_step          = "";
        String sub_step         = "";
        String request_day      = "";
        String request_txt      = "";
        String rs_team          = "";
        String rs_step_no       = "";
        String f_day            = "";
        String Leader_day       = "";
        String Leader_name      = "";
        String approval         = "";
        String dev_step         = "";
        String pps              = "";
        String app_part         = "";
        String etc_note         = "";
        String file_1           = "";
        String file_2           = "";
        String file_3           = "";
        String f_name           = "";
        String pk_pid			= "";

        try{

            pstmt = conn.prepareStatement(sb.toString());

            rs = pstmt.executeQuery();

            while(rs.next()){
                pk_cr		 	 = StringUtil.checkNull(rs.getString("pk_cr"       ));
                table_row        = StringUtil.checkNull(rs.getString("table_row"));
                pjt_name         = StringUtil.checkNull(rs.getString("pjt_name"    ));
                a_name           = StringUtil.checkNull(rs.getString("a_name"      ));
                pjt_no           = StringUtil.checkNull(rs.getString("pjt_no"      ));
                rev_pk           = StringUtil.checkNull(rs.getString("rev_pk"      ));
                rs_rev_no        = StringUtil.checkNull(rs.getString("rev_no"   ));
                dr_step          = StringUtil.checkNull(rs.getString("dr_step"     ));
                sub_step         = StringUtil.checkNull(rs.getString("sub_step"    ));
                request_day      = StringUtil.checkNull(rs.getString("request_day" ));
                request_txt      = StringUtil.checkNull(rs.getString("request_txt" ));
                rs_team          = StringUtil.checkNull(rs.getString("team"     ));
                rs_step_no       = StringUtil.checkNull(rs.getString("step_no"  ));
                f_day            = StringUtil.checkNull(rs.getString("f_day"       ));
                Leader_day       = StringUtil.checkNull(rs.getString("Leader_day"  ));
                Leader_name      = StringUtil.checkNull(rs.getString("Leader_name" ));
                approval         = StringUtil.checkNull(rs.getString("approval"    ));
                dev_step         = StringUtil.checkNull(rs.getString("dev_step"    ));
                pps              = StringUtil.checkNull(rs.getString("pps"         ));
                app_part         = StringUtil.checkNull(rs.getString("app_part"    ));
                etc_note         = StringUtil.checkNull(rs.getString("etc_note"    ));
                file_1           = StringUtil.checkNull(rs.getString("file_1"      ));
                file_2           = StringUtil.checkNull(rs.getString("file_2"      ));
                file_3           = StringUtil.checkNull(rs.getString("file_3"      ));
                f_name           = StringUtil.checkNull(rs.getString("f_name"      ));
                pk_pid           = StringUtil.checkNull(rs.getString("pk_pid"      ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        System.out.println("pk_cr==>"+pk_cr);
        int AA 	= Integer.parseInt(table_row);
        int c_count_1 = 200;
        int c_count_2 = 200;
        int BB 	= c_count_1;
        int CC	= c_count_2;

        int DD = AA + BB + CC;

        //1.판매단위 제품정보

        list_pk_	= new String[DD];
        assy_in		= new String[AA];
        car_type_	= new String[AA];

        a_su_year_1 = new String[AA];
        a_su_year_2 = new String[AA];
        a_su_year_3 = new String[AA];
        a_su_year_4 = new String[AA];
        a_su_year_5 = new String[AA];
        a_su_year_6 = new String[AA];
        a_su_year_7 = new String[AA];
        a_su_year_8 = new String[AA];
        royalty  	= new String[AA];
        start_pro 	= new String[AA];
        store		= new String[AA];
        dest		= new String[AA];
        ket_cost	= new String[AA];
        target_cost = new String[AA];
        distri_cost = new String[AA];
        case_count_1= new String[AA];

        pk_cr_		= new String[AA][BB][CC];
        pk_cr_group = new String[AA][BB][CC];
        customer_F  = new String[AA];
        customer_L	= new String[AA];
        client_cost_= new String[AA];
        assy_note_	= new String[AA];
        part_note_	= new String[AA][BB][CC];

        //2.제품 생산내역

        group_no	= new String[AA][BB][CC];
        part_no_1	= new String[AA][BB][CC];
        die_no		= new String[AA][BB][CC];
        part_name	= new String[AA][BB][CC];
        case_count_2= new String[AA][BB];
        scrap_rate	= new String[AA][BB][CC];
        etc_depr	= new String[AA][BB][CC];		//기타감가
        mold_c_type = new String[AA][BB][CC];
        grade_name	= new String[AA][BB][CC];
        grade_color = new String[AA][BB][CC];
        mold_type	= new String[AA][BB][CC];
        mix_group_1 = new String[AA][BB][CC];
        make		= new String[AA][BB][CC];
        re_m_cost	= new String[AA][BB][CC];
        part_type_1 = new String[AA][BB][CC];
        pro_type	= new String[AA][BB][CC];
        usage		= new String[AA][BB][CC];
        meterial	= new String[AA][BB][CC];
        t_size		= new String[AA][BB][CC];
        w_size		= new String[AA][BB][CC];
        p_size		= new String[AA][BB][CC];
        plating	    = new String[AA][BB][CC];
        m_maker 	= new String[AA][BB][CC];
        m_mone 		= new String[AA][BB][CC];
        unitcost	= new String[AA][BB][CC];
        c_unitcost	= new String[AA][BB][CC];
        unitcost_txt	= new String[AA][BB][CC];
        order_con		= new String[AA][BB][CC];
        h_weight		= new String[AA][BB][CC];
        h_scrap = new String[AA][BB][CC];
        t_weight = new String[AA][BB][CC];
        recycle_state = new String[AA][BB][CC];
        recycle = new String[AA][BB][CC];
        cavity = new String[AA][BB][CC];
        make_type = new String[AA][BB][CC];
        pro_1 = new String[AA][BB][CC];
        ton = new String[AA][BB][CC];
        spm = new String[AA][BB][CC];
        method_type = new String[AA][BB][CC];
        pro_level = new String[AA][BB][CC];
        pro_level_txt = new String[AA][BB][CC];
        type_1 = new String[AA][BB][CC];
        type_2 = new String[AA][BB][CC];
        t_mone = new String[AA][BB][CC];
        type_cost = new String[AA][BB][CC];
        t_order = new String[AA][BB][CC];
        pack_type = new String[AA][BB][CC];
        in_pack = new String[AA][BB][CC];
        out_pack = new String[AA][BB][CC];
        dis_cost = new String[AA][BB][CC];
        yazaki_ro = new String[AA][BB][CC];
        yzk_mone = new String[AA][BB][CC];
        m_su = new String[AA][BB][CC];
        mold_cost = new String[AA][BB][CC];
        line_su = new String[AA][BB][CC];
        sul_cost = new String[AA][BB][CC];
        etc_cost = new String[AA][BB][CC];
        plating_type= new String[AA][BB][CC];
        plating_cost = new String[AA][BB][CC];

        //간이버튼 Click

        st_person = new String[AA][BB][CC];			//표준작업인원
        st_eff_value = new String[AA][BB][CC];			//효율
        eff_value = new String[AA][BB][CC];				//효율
        unitcost_si= new String[AA][BB][CC];				//원재료단가
        meterial_cost= new String[AA][BB][CC];			//원재료비
        m_total_cost= new String[AA][BB][CC]; 			//재료비
        labor_cost= new String[AA][BB][CC];  			//노무비
        common_cost= new String[AA][BB][CC]; 			//제조경비
        s_depr_cost= new String[AA][BB][CC];   			//일반감가비
        g_depr_cost= new String[AA][BB][CC];   			//기획수량감가비
        yzk_depr= new String[AA][BB][CC];				//yzk감가
        ad_cost= new String[AA][BB][CC];    				//관리비
        actual_cost= new String[AA][BB][CC];			//총원가
        earn_rate= new String[AA][BB][CC];     			//수익률
        loss= new String[AA][BB][CC];					//loss비
        scrap_cost= new String[AA][BB][CC];			//스크랩판매비
        output= new String[AA][BB][CC];				//시간당 생산량
        rate= new String[AA][BB][CC];					//임율
        qu_cost = new String[AA][BB][CC];				//품질비용
        gita_cost= new String[AA][BB][CC];				//기타감가
        tariff = new String[AA][BB][CC];					//관세
        jun_cost= new String[AA][BB][CC];				//전력비
        ma_depr= new String[AA][BB][CC];				//기계감가
        tabalu= new String[AA][BB][CC];				//타발유
        m_upkeep = new String[AA][BB][CC];			//금형유지비
        total_expense= new String[AA][BB][CC];			//직접경비
        overhead= new String[AA][BB][CC];				//간접경비
        pack_cost= new String[AA][BB][CC];				//포장비
        s_avg_su= new String[AA][BB][CC];				//일반감가기준수량
        avg_su= new String[AA][BB][CC];				//총 판매기획수량
        process_cost= new String[AA][BB][CC];  			//가공비
        jae_cost= new String[AA][BB][CC];				//재료관리비
        ge_cost= new String[AA][BB][CC];				//일반관리비
        s_rnd_cost= new String[AA][BB][CC];    			//일반감가기준 R&D비
        g_rnd_cost= new String[AA][BB][CC];    			//판매기준 R&D비
        s_roy_cost= new String[AA][BB][CC];				//일반감가기준  로열티
        s_actual_cost= new String[AA][BB][CC];			//일반감가기준  총원가
        g_roy_cost= new String[AA][BB][CC];				//판매기준 로열티
        g_actual_cost= new String[AA][BB][CC];			//판매기준 총원가
        s_earn_rate= new String[AA][BB][CC];			//일반감가기준 수익률
        g_earn_rate= new String[AA][BB][CC];			//판매기준 수익률

        //조립
        s_assy_m_total_cost= new String[AA]; 			//단품원가총합= new String재료비산출에사용]; -일반감가
        g_assy_m_total_cost= new String[AA]; 			//단품원가총합= new String재료비산출에사용]; - 판매수량
        s_assy_meterial_cost= new String[AA];			//조립 재료비
        g_assy_meterial_cost= new String[AA];			//조립 재료비
        s_assy_loss= new String[AA];					//loss비-일반
        g_assy_loss= new String[AA];					//loss비-판매
        assy_scrap_cost= new String[AA];				//스크랩판매비
        assy_labor_cost= new String[AA];  				//노무비
        assy_common_cost= new String[AA]; 			//제조경비
        assy_s_depr_cost= new String[AA];   			//일반감가비
        assy_g_depr_cost= new String[AA];   			//기획수량감가비
        assy_ad_cost= new String[AA];    				//관리비
        assy_output= new String[AA];					//시간당 생산량
        assy_rate= new String[AA];					//임율
        assy_rnd_cost= new String[AA];				//R&D비율
        assy_jun_cost= new String[AA];				//전력비
        assy_ma_depr= new String[AA];				//기계감가
        assy_m_upkeep = new String[AA];				//금형유지비
        assy_total_expense= new String[AA];			//직접경비
        assy_overhead= new String[AA];				//간접경비
        assy_pack_cost= new String[AA];				//포장비
        assy_s_avg_su= new String[AA];				//일반감가기준수량
        assy_avg_su= new String[AA];					//총 판매기획수량
        assy_process_cost= new String[AA];  			//가공비
        assy_jae_cost= new String[AA];				//재료관리비
        assy_ge_cost= new String[AA];				//일반관리비
        assy_s_rnd_cost= new String[AA];    			//일반감가기준 R&D비
        assy_g_rnd_cost= new String[AA];    			//판매기준 R&D비
        assy_s_roy_cost= new String[AA];				//일반감가기준  로열티
        assy_s_actual_cost= new String[AA];			//일반감가기준  총원가
        assy_g_roy_cost= new String[AA];				//판매기준 로열티
        assy_g_actual_cost= new String[AA];			//판매기준 총원가
        assy_s_earn_rate= new String[AA];				//일반감가기준 수익률
        assy_g_earn_rate= new String[AA];				//판매기준 수익률

        //sub 조립
        s_sub_m_total_cost= new String[AA][BB]; 			//단품원가총합= new String재료비산출에사용]; -일반감가
        g_sub_m_total_cost= new String[AA][BB]; 			//단품원가총합= new String재료비산출에사용]; - 판매수량
        s_sub_meterial_cost= new String[BB];			//조립 재료비
        g_sub_meterial_cost= new String[BB];			//조립 재료비
        s_sub_loss= new String[BB];					//loss비-일반
        g_sub_loss= new String[BB];					//loss비-판매
        sub_scrap_cost= new String[BB];				//스크랩판매비
        sub_labor_cost= new String[BB];  				//노무비
        sub_common_cost= new String[BB]; 			//제조경비
        sub_s_depr_cost= new String[BB];   			//일반감가비
        sub_g_depr_cost= new String[BB];   			//기획수량감가비
        sub_ad_cost= new String[BB];    				//관리비
        sub_output= new String[BB];					//시간당 생산량
        sub_rate= new String[BB];					//임율
        sub_rnd_cost= new String[BB];				//R&D비율
        sub_jun_cost= new String[BB];					//전력비
        sub_ma_depr= new String[BB];					//기계감가
        sub_m_upkeep = new String[BB];				//금형유지비
        sub_total_expense= new String[BB];			//직접경비
        sub_overhead= new String[BB];				//간접경비
        sub_pack_cost= new String[BB];				//포장비
        sub_s_avg_su= new String[BB];				//일반감가기준수량
        sub_avg_su= new String[BB];					//총 판매기획수량
        sub_process_cost= new String[BB];  			//가공비
        sub_jae_cost= new String[BB];					//재료관리비
        sub_ge_cost= new String[BB];					//일반관리비
        sub_s_rnd_cost= new String[BB];    			//일반감가기준 R&D비
        sub_g_rnd_cost= new String[BB];    			//판매기준 R&D비
        sub_s_roy_cost= new String[BB];				//일반감가기준  로열티
        sub_s_actual_cost= new String[BB];			//일반감가기준  총원가
        sub_g_roy_cost= new String[BB];				//판매기준 로열티
        sub_g_actual_cost= new String[BB];			//판매기준 총원가
        sub_s_earn_rate= new String[BB];				//일반감가기준 수익률
        sub_g_earn_rate= new String[BB];				//판매기준 수익률

        //최종 SUM
        sum_meterial = new String[AA][BB][CC]; 			//제조원가
        sum_m_total_cost = new String[AA][BB][CC];
        sum_labor_cost = new String[AA][BB][CC];
        sum_common_cost = new String[AA][BB][CC];
        sum_sum_meterial = new String[AA][BB][CC];
        sum_ad_cost = new String[AA][BB][CC];
        sum_g_depr_cost = new String[AA][BB][CC];
        sum_s_actual_cost = new String[AA][BB][CC];
        sum_g_actual_cost = new String[AA][BB][CC];

        pl_meterial_cost = new String[AA][BB][CC];
        pl_loss = new String[AA][BB][CC];
        pl_scrap_cost = new String[AA][BB][CC];
        pl_m_total_cost = new String[AA][BB][CC];
        pl_output = new String[AA][BB][CC];
        pl_rate = new String[AA][BB][CC];
        pl_labor_cost = new String[AA][BB][CC];
        pl_jun_cost = new String[AA][BB][CC];
        pl_ma_depr = new String[AA][BB][CC];
        pl_tabalu = new String[AA][BB][CC];
        pl_m_upkeep = new String[AA][BB][CC];
        pl_total_expense = new String[AA][BB][CC];
        pl_overhead = new String[AA][BB][CC];
        pl_common_cost = new String[AA][BB][CC];
        pl_yzk_depr = new String[AA][BB][CC];
        pl_start_depr = new String[AA][BB][CC];
        pl_pro_depr = new String[AA][BB][CC];
        pl_etc_depr = new String[AA][BB][CC];
        pl_jae_cost = new String[AA][BB][CC];
        pl_ge_cost = new String[AA][BB][CC];
        pl_rnd_cost = new String[AA][BB][CC];
        pl_tariff = new String[AA][BB][CC];
        pl_royalty_cost = new String[AA][BB][CC];
        pl_actual_cost = new String[AA][BB][CC];

        if(conn.isClosed()){
	        try {
	            conn = DBUtil.getConnection();
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
        }

        sb.append("  select pk_cr  					    				            ");
        sb.append("    from cost_request a, cost_productinfo b                        ");
        sb.append("   where b.pk_cr_group = '" + p_pk_cr_group + "'");
        sb.append("     and b.rev_no      = '" + rev_no + "'");
        sb.append("     and a.fk_pid      =  b.pk_pid                                 ");
        sb.append("     and b.data_type   = 'main'                                    ");
        sb.append("order by group_no                                             ");

        String temp_pk_cr = "";

        try{

            pstmt = conn.prepareStatement(sb.toString());

            rs = pstmt.executeQuery();

            while(rs.next()){
                temp_pk_cr	= temp_pk_cr+StringUtil.checkNull(rs.getString("pk_cr"))+",";
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }

        String pk_cr_aa[] = temp_pk_cr.split(",");
        int open_count = pk_cr_aa.length;

        for(int t=0; t<open_count; t++){
            list_pk_[t] = pk_cr_aa[t];
        }

        String text_type 	  = "<font color=#452198 size=3 ><b>";
        String case_text_type = "<font color=#666666>";

        String k = "";
        int i = 1;


        for(int j=0; j<Integer.parseInt(table_row); j++){
        	if(conn.isClosed()){
    	        try {
    	            conn = DBUtil.getConnection();
    	        } catch (Exception e1) {
    	            e1.printStackTrace();
    	        }
            }
            System.out.println("J는 ~~~~ "+j);
            if(j < 9){
                k = "00"+ Integer.toString(i);
                System.out.println("111 T" + k);
            }else if(j < 99){
                k = "0"+ Integer.toString(i);
                System.out.println("222 T" + k);
            }else if(j < 999){
                k = Integer.toString(i);
                System.out.println("333 T" + k);
            }

            sb.append(" select pk_cr,       group_no,  case_type_user, case_count_1, part_name,   part_type,  ");
            sb.append("        mix_group,   car_type,  su_year_1,      su_year_2,    su_year_3,   su_year_4,  ");
            sb.append("        su_year_5,   su_year_6, su_year_7,      su_year_8,    customer_F,  customer_L, ");
            sb.append("        royalty,     start_pro, store,          dest,         client_cost, ket_cost,   ");
            sb.append("        target_cost, assy_note                                                         ");
            sb.append("   from cost_request a, cost_productinfo b                                             ");
            sb.append("  where b.pk_cr_group = '" + p_pk_cr_group + "'");
            sb.append("    and b.rev_no      = '" + rev_no + "'");
            sb.append("    and a.fk_pid      =  b.pk_pid                                                      ");
            sb.append("    and b.group_no    = 'T" + k + "'");
            sb.append("    and b.data_type   = 'main'                                                         ");
            try{

                pstmt = conn.prepareStatement(sb.toString());

                rs = pstmt.executeQuery();

                while(rs.next()){
                    pk_cr_[j][0][0]		  = StringUtil.checkNull(rs.getString("pk_cr"));
                    group_no[j][0][0]	  = StringUtil.checkNull(rs.getString("group_no"));
                    group_case_count	  = StringUtil.checkNull( Integer.toString(rs.getInt("case_type_user")) );
                    if(Integer.parseInt(group_case_count) > 0){
                        case_data_value = "ok";
                    }
                    case_count_1[j]		  = StringUtil.checkNull(rs.getString("case_count_1"));
                    System.out.println("first case_count_1 ==!! "+case_count_1[j] + "  j==>"+j);
                    part_name[j][0][0]    = StringUtil.checkNull(rs.getString("part_name"));
                    part_type_1[j][0][0]  = StringUtil.checkNull(rs.getString("part_type"));
                    mix_group_1[j][0][0]  = StringUtil.checkNull(rs.getString("mix_group"));
                    car_type_[j]    	  = StringUtil.checkNull(rs.getString("car_type"));
                    a_su_year_1[j]   	  = StringUtil.Roundformat(rs.getString("su_year_1"),0);
                    a_su_year_2[j]   	  = StringUtil.Roundformat(rs.getString("su_year_2"),0);
                    a_su_year_3[j]   	  = StringUtil.Roundformat(rs.getString("su_year_3"),0);
                    a_su_year_4[j]   	  = StringUtil.Roundformat(rs.getString("su_year_4"),0);
                    a_su_year_5[j]   	  = StringUtil.Roundformat(rs.getString("su_year_5"),0);
                    a_su_year_6[j]   	  = StringUtil.Roundformat(rs.getString("su_year_6"),0);
                    a_su_year_7[j]   	  = StringUtil.Roundformat(rs.getString("su_year_7"),0);
                    a_su_year_8[j]   	  = StringUtil.Roundformat(rs.getString("su_year_8"),0);
                    customer_F[j]  	  = StringUtil.checkNull(rs.getString("customer_F"));
                    customer_L[j]  	  = StringUtil.checkNull(rs.getString("customer_L"));
                    royalty[j]     	  = StringUtil.checkNull(rs.getString("royalty"));
                    start_pro[j]   	  = StringUtil.checkNull(rs.getString("start_pro"));
                    store[j]       	  = StringUtil.checkNull(rs.getString("store"));
                    dest[j]		      = StringUtil.checkNull(rs.getString("dest"));
                    client_cost_[j]   = StringUtil.checkNull(rs.getString("client_cost"));
                    ket_cost[j]    	  = StringUtil.checkNull(rs.getString("ket_cost"));
                    target_cost[j] 	  = StringUtil.checkNull(rs.getString("target_cost"));
                    assy_note_[j]     = StringUtil.checkNull(rs.getString("assy_note"));

                }

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(rs!=null) {rs.close();}
                if(pstmt!=null) {pstmt.close();}
                if(conn!=null) {conn.close();}
            }
            i=i+1;
        }
        String[] imsi_case_count_2 = {};
        imsi_case_count_2 = new String[open_count];

        for(int t=0; t<open_count; t++ ){
        	if(conn.isClosed()){
    	        try {
    	            conn = DBUtil.getConnection();
    	        } catch (Exception e1) {
    	            e1.printStackTrace();
    	        }
            }
            try{
                sb.append(" select case_count_2		             ");
                sb.append("   from cost_request a,cost_productinfo b ");
                sb.append("  where a.fk_pid =  b.pk_pid              ");
                sb.append("    and a.pk_cr = '" + list_pk_[t] +"'" );

                pstmt = conn.prepareStatement(sb.toString());

                rs = pstmt.executeQuery();

                while(rs.next()){
                    imsi_case_count_2[t] = StringUtil.checkNull(rs.getString("case_count_2"));
                }

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(rs!=null) {rs.close();}
                if(pstmt!=null) {pstmt.close();}
                if(conn!=null) {conn.close();}
            }
        }
        int a=0;
        int p=0;
        int q=0;
        int j=0;
        System.out.println("open_count !! "+open_count);

        for(int t=0; t<open_count; t++ ){
            System.out.println("t ==!! "+t);
            if(t == 0){
                j = 0;
                p = 0;
                q = 0;
            }else{

                System.out.println("case_count_1 ==!! "+case_count_1[j] + "  j==>"+j);
                if(p < Integer.parseInt(case_count_1[j])){
                    j = j;
                    if(Integer.parseInt(imsi_case_count_2[t]) > 0){
                        if(a==0){
                            p = p+1;
                            a = a+1;
                            q = 0;
                        }else{
                            if(q < Integer.parseInt(imsi_case_count_2[t])){
                                p = p;
                                q = q+1;
                                if(q == Integer.parseInt(imsi_case_count_2[t])){
                                    a = 0;
                                }
                            }else{
                                p = p + 1;
                                q = 0;
                            }
                        }
                    }else{
                        p = p + 1;
                        q = 0;
                    }
                }else if(p == Integer.parseInt(case_count_1[j])){
                    if(Integer.parseInt(imsi_case_count_2[t]) > 0){
                        j = j;
                        if(q < Integer.parseInt(imsi_case_count_2[t])){
                            p = p;
                            q = q + 1;
                        }else{
                            p = p + 1;
                            q = 0;
                        }
                    }else{
                        p = 0;
                        q = 0;
                        a = 0;
                        j = j+1;
                    }
                }else{
                    p = 0;
                    q = 0;
                    j = j+1;
                }
            }
            if(conn.isClosed()){
    	        try {
    	            conn = DBUtil.getConnection();
    	        } catch (Exception e1) {
    	            e1.printStackTrace();
    	        }
            }
            sb.append(" select pk_cr,         group_no,      case_count_2, re_m_cost, part_name, part_no,      part_type,  ");
            sb.append("        pro_type,      make,          mix_group,    usage,     meterial,  t_size,       w_size,     ");
            sb.append("        p_size,        plating,       m_maker,      m_mone,    unitcost,  unitcost_txt, grade_name, ");
            sb.append("        grade_color,   order_con,     h_weight,     h_scrap,   t_weight,  recycle,      die_no,     ");
            sb.append("        cavity,        make_type,     pro_1,        ton,       spm,       method_type,  pro_level,  ");
            sb.append("        pro_level_txt, plating_type,  plating_cost, type_1,    type_2,    t_mone,       type_cost,  ");
            sb.append("        t_order,       pack_type,     in_pack,      out_pack,  dis_cost,  yazaki_ro,    yzk_mone,   ");
            sb.append("        m_su,          mold_cost,     mold_c_type,  line_su,   sul_cost,  etc_cost,     part_note   ");
            sb.append("   from cost_request a,cost_productinfo b                                                           ");
            sb.append("  where a.fk_pid =  b.pk_pid                                                                        ");
            sb.append("    and a.pk_cr = '" + list_pk_[t] + "'");
            System.out.println("result 의list_pk_ : "+list_pk_[t]);
            System.out.println("result 의 j : : "+j);
            System.out.println("result 의 p : : "+p);
            System.out.println("result 의 q : : "+q);
            try{

                pstmt = conn.prepareStatement(sb.toString());

                rs = pstmt.executeQuery();

                while(rs.next()){
                    pk_cr_[j][p][q]			= StringUtil.checkNull(rs.getString("pk_cr"));
                    group_no[j][p][q]		= StringUtil.checkNull(rs.getString("group_no"));
                    case_count_2[j][p]		= StringUtil.checkNull(rs.getString("case_count_2"));
                    re_m_cost[j][p][q]		= StringUtil.checkNull(rs.getString("re_m_cost"));
                    part_name[j][p][q]		= StringUtil.checkNull(rs.getString("part_name"));
                    part_no_1[j][p][q]		= StringUtil.checkNull(rs.getString("part_no"));
                    part_type_1[j][p][q]	= StringUtil.checkNull(rs.getString("part_type"));
                    pro_type[j][p][q]    	= StringUtil.checkNull(rs.getString("pro_type"));
                    make[j][p][q]			= StringUtil.checkNull(rs.getString("make"));
                    mix_group_1[j][p][q]	= StringUtil.checkNull(rs.getString("mix_group"));
                    usage[j][p][q]       	= StringUtil.checkNull(rs.getString("usage"));
                    meterial[j][p][q] 		= StringUtil.checkNull(rs.getString("meterial"));
                    t_size[j][p][q]		= StringUtil.checkNull(rs.getString("t_size"));
                    w_size[j][p][q]		= StringUtil.checkNull(rs.getString("w_size"));
                    p_size[j][p][q] 		= StringUtil.checkNull(rs.getString("p_size"));
                    plating[j][p][q] 		= StringUtil.checkNull(rs.getString("plating"));
                    m_maker[j][p][q]		= StringUtil.checkNull(rs.getString("m_maker"));
                    m_mone[j][p][q]		= StringUtil.checkNull(rs.getString("m_mone"));
                    unitcost[j][p][q]		= StringUtil.checkNull(rs.getString("unitcost"));
                    unitcost_txt[j][p][q]	= StringUtil.checkNull(rs.getString("unitcost_txt"));
                    grade_name[j][p][q]	= StringUtil.checkNull(rs.getString("grade_name"));
                    grade_color[j][p][q]	= StringUtil.checkNull(rs.getString("grade_color"));
                    order_con[j][p][q]		= StringUtil.checkNull(rs.getString("order_con"));
                    h_weight[j][p][q]		= StringUtil.checkNull(rs.getString("h_weight"));
                    h_scrap[j][p][q]		= StringUtil.checkNull(rs.getString("h_scrap"));
                    t_weight[j][p][q]		= StringUtil.checkNull(rs.getString("t_weight"));
                    recycle[j][p][q]		= StringUtil.checkNull(rs.getString("recycle"));
                    die_no[j][p][q]      	= StringUtil.checkNull(rs.getString("die_no"));
                    cavity[j][p][q]      	= StringUtil.checkNull(rs.getString("cavity"));
                    make_type[j][p][q]   	= StringUtil.checkNull(rs.getString("make_type"));
                    pro_1[j][p][q]       	= StringUtil.checkNull(rs.getString("pro_1"));
                    ton[j][p][q]         	= StringUtil.checkNull(rs.getString("ton"));
                    spm[j][p][q]         	= StringUtil.checkNull(rs.getString("spm"));
                    method_type[j][p][q]	= StringUtil.checkNull(rs.getString("method_type"));
                    pro_level[j][p][q]   	= StringUtil.checkNull(rs.getString("pro_level"));
                    pro_level_txt[j][p][q] = StringUtil.checkNull(rs.getString("pro_level_txt"));
                    plating_type[j][p][q]	= StringUtil.checkNull(rs.getString("plating_type"));
                    plating_cost[j][p][q]	= StringUtil.checkNull(rs.getString("plating_cost"));
                    type_1[j][p][q]      	= StringUtil.checkNull(rs.getString("type_1"));
                    type_2[j][p][q]      	= StringUtil.checkNull(rs.getString("type_2"));
                    t_mone[j][p][q]      	= StringUtil.checkNull(rs.getString("t_mone"));
                    type_cost[j][p][q]   	= StringUtil.checkNull(rs.getString("type_cost"));
                    t_order[j][p][q]     	= StringUtil.checkNull(rs.getString("t_order"));
                    pack_type[j][p][q]   	= StringUtil.checkNull(rs.getString("pack_type"));
                    in_pack[j][p][q]     	= StringUtil.checkNull(rs.getString("in_pack"));
                    out_pack[j][p][q]    	= StringUtil.checkNull(rs.getString("out_pack"));
                    dis_cost[j][p][q]    	= StringUtil.checkNull(rs.getString("dis_cost"));
                    yazaki_ro[j][p][q]  	= StringUtil.checkNull(rs.getString("yazaki_ro"));
                    yzk_mone[j][p][q]		= StringUtil.checkNull(rs.getString("yzk_mone"));
                    m_su[j][p][q]        	= StringUtil.checkNull(rs.getString("m_su"));
                    mold_cost[j][p][q]   	= StringUtil.checkNull(rs.getString("mold_cost"));
                    System.out.println("ResultAcc의 mold_cost >>>> "+j+" "+p+" "+q+" "+mold_cost[j][p][q] );
                    mold_c_type[j][p][q]	= StringUtil.checkNull(rs.getString("mold_c_type"));
                    System.out.println("에러확인 1111");
                    line_su[j][p][q]    	= StringUtil.checkNull(rs.getString("line_su"));
                    System.out.println("에러확인 2222");
                    sul_cost[j][p][q]   	= StringUtil.checkNull(rs.getString("sul_cost"));
                    System.out.println("에러확인 3333");
                    etc_cost[j][p][q]   	= StringUtil.checkNull(rs.getString("etc_cost"));
                    System.out.println("에러확인 4444");
                    part_note_[j][p][q]  	= StringUtil.checkNull(rs.getString("part_note"));
                    System.out.println("에러확인 5555");
                }

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(rs!=null) {rs.close();}
                if(pstmt!=null) {pstmt.close();}
                if(conn!=null) {conn.close();}
            }
        }
        System.out.println("원가산출탔슴 11 ???");
        for(j=0; j < Integer.parseInt(table_row); j++){
            for(p=0; p <= Integer.parseInt(StringUtil.checkNullZero(case_count_1[j])); p++){
                for(q=0; q <= Integer.parseInt(StringUtil.checkNullZero(case_count_2[j][q])); q++){
                    if(!StringUtil.checkNull(pro_type[j][p][q]).equals("")){
                        if(pro_type[j][p][q].equals("assay") || pro_type[j][p][q].equals("Insert")){
                            assy_in[j] = "ok";
                        }
                    }
                }
            }
        }
        System.out.println("원가산출탔슴 22???");
        //원가산출시작//
        cost_acc(page_name);
        //원가산출 끝//
System.out.println("원가산출탔슴 33???");

        boolean IsNew = true;
        int check_cnt = 0;

        String p_loss          = "";
        String p_scrap_cost	   = "";
        String p_m_total_cost  = "";
        String p_output	     = "";
        String p_st_person	 = "";
        String p_eff_value	 = "";
        String p_rate 	 	 = "";
        String p_labor_cost	 = "";
        String p_jun_cost	     = "";
        String p_ma_depr	     = "";
        String p_total_expense = "";
        String p_overhead	     = "";
        String p_common_cost	 = "";
        String pro_depr	     = "";
        String earn_rate	 = "";
        String rnd_cost	     = "";
        String p_jae_cost	     = "";
        String p_ge_cost	     = "";
        String p_pack_cost	 = "";
        String p_ad_cost	     = "";
        String depr_cost	 = "";
        String royalty_cost	 = "";
        String actual_cost	 = "";
        String p_meterial_cost = "";
        String p_m_upkeep	     = "";
        String start_depr	 = "";
        String p_tabalu		 = "";
        String type_cost_     = "";

        BigDecimal plus  = new BigDecimal("0");//소수점 계산용
        BigDecimal minus = new BigDecimal("0");//소수점 계산용

        String seq_pk_cw  = "";  //cost_work pk 시퀀스용 변수

        for(int kk=0; kk<Integer.parseInt(table_row); kk++){
            for(int e=0; e<=Integer.parseInt(StringUtil.checkNullZero(case_count_1[kk])); e++){
                for(int t=0; t<=Integer.parseInt(case_count_2[kk][e]); t++){
                    if(!StringUtil.checkNull(type_cost[kk][e][t]).equals("")){
                        type_cost_ = StringUtil.Roundformat(type_cost[kk][e][t],3);
                    }
                    if(StringUtil.checkNull(pro_type[kk][e][t]).equals("assy")){								//조립
                        p_loss		 	= StringUtil.Roundformat(g_assy_loss[kk],4);			//loss비
                        p_scrap_cost	= StringUtil.Roundformat(assy_scrap_cost[kk],4);			//스크랩판매비
                        p_m_total_cost 	= StringUtil.Roundformat(g_assy_meterial_cost[kk],4); 		//재료비

                        p_output		= StringUtil.Roundformat(assy_output[kk],4);			//시간당 생산량
                        p_st_person		= StringUtil.Roundformat(st_person[kk][e][t],4);			//표준작업인원
                        p_eff_value		= StringUtil.Roundformat(st_eff_value[kk][e][t],4); 		//효율
                        p_rate			= StringUtil.Roundformat(assy_rate[kk],4);				//임율
                        p_labor_cost	= StringUtil.Roundformat(assy_labor_cost[kk],4);  			//노무비

                        p_jun_cost		= StringUtil.Roundformat(assy_jun_cost[kk],4);			//전력비
                        p_ma_depr		= StringUtil.Roundformat(assy_ma_depr[kk],4);			//기계감가
                        p_total_expense	= StringUtil.Roundformat(assy_total_expense[kk],4);		//직접경비
                        p_overhead		= StringUtil.Roundformat(assy_overhead[kk],4);			//간접경비
                        p_common_cost	= StringUtil.Roundformat(assy_common_cost[kk],4); 			//제조경비

                        pro_depr		= StringUtil.Roundformat(assy_g_depr_cost[kk],4);   		//설비감가비

                        earn_rate		= StringUtil.Roundformat(assy_g_earn_rate[kk],4);     		//수익률
                        rnd_cost		= StringUtil.Roundformat(assy_g_rnd_cost[kk],4);			//R&D비율

                        p_jae_cost		= StringUtil.Roundformat(assy_jae_cost[kk],4);			//재료관리비
                        p_ge_cost		= StringUtil.Roundformat(assy_ge_cost[kk],4);			//일반관리비

                        p_pack_cost	 	= StringUtil.Roundformat(assy_pack_cost[kk],4);			//포장비
                        p_ad_cost		= StringUtil.Roundformat(assy_ad_cost[kk],4);			//관리비
                        depr_cost	 	= StringUtil.Roundformat(assy_g_depr_cost[kk],4);			//감가비
                        rnd_cost	 	= StringUtil.Roundformat(assy_g_rnd_cost[kk],4);    		//판매기준 R&D비
                        royalty_cost 	= StringUtil.Roundformat(assy_g_roy_cost[kk],4);			//판매기준 로열티
                        actual_cost	 	= StringUtil.Roundformat(assy_g_actual_cost[kk],4);		//판매기준 총원가
                    }else if(StringUtil.checkNull(pro_type[kk][e][t]).equals("Insert")){
                        p_meterial_cost	= StringUtil.Roundformat(meterial_cost[kk][e][t],4);		//원재료비
                        p_loss			= StringUtil.Roundformat(loss[kk][e][t],4);				//loss비
                        p_scrap_cost	= StringUtil.Roundformat(assy_scrap_cost[kk],4);			//스크랩판매비
                        p_m_total_cost	= StringUtil.Roundformat(m_total_cost[kk][e][t],4); 		//재료비

                        p_output	 = StringUtil.Roundformat(assy_output[kk],4);			//시간당 생산량
                        p_st_person	 = StringUtil.Roundformat(st_person[kk][e][t],4);			//표준작업인원
                        p_eff_value	 = StringUtil.Roundformat(st_eff_value[kk][e][t],4); 		//효율
                        p_rate		 = StringUtil.Roundformat(assy_rate[kk],4);				//임율
                        p_labor_cost = StringUtil.Roundformat(assy_labor_cost[kk],4);  			//노무비

                        p_jun_cost		= StringUtil.Roundformat(assy_jun_cost[kk],4);			//전력비
                        p_ma_depr		= StringUtil.Roundformat(assy_ma_depr[kk],4);			//기계감가
                        p_total_expense	= StringUtil.Roundformat(assy_total_expense[kk],4);			//직접경비
                        p_overhead		= StringUtil.Roundformat(assy_overhead[kk],4);			//간접경비
                        p_common_cost	= StringUtil.Roundformat(assy_common_cost[kk],4); 			//제조경비



                        p_m_upkeep	= StringUtil.Roundformat(m_upkeep[kk][e][t],4);			//금형유지비
                        start_depr	= StringUtil.Roundformat(g_depr_cost[kk][e][t],4);   		//금형감가비

                        plus  = new BigDecimal(StringUtil.Roundformat(assy_g_depr_cost[kk],0));
                        minus = new BigDecimal(StringUtil.Roundformat(g_depr_cost[kk][e][t],4));

                        pro_depr = Double.toString(plus.subtract(minus).doubleValue()) ;   //설비감가비

                        earn_rate	= StringUtil.Roundformat(assy_g_earn_rate[kk],4);     		//수익률
                        rnd_cost	= StringUtil.Roundformat(assy_g_rnd_cost[kk],4);			//R&D비율

                        p_jae_cost	= StringUtil.Roundformat(assy_jae_cost[kk],4);			//재료관리비
                        p_ge_cost		= StringUtil.Roundformat(assy_ge_cost[kk],4);			//일반관리비

                        p_pack_cost	= StringUtil.Roundformat(assy_pack_cost[kk],4);			//포장비
                        p_ad_cost	= StringUtil.Roundformat(assy_ad_cost[kk],4);			//관리비
                        depr_cost	= StringUtil.Roundformat(assy_g_depr_cost[kk],4);			//감가비
                        royalty_cost	= StringUtil.Roundformat(assy_g_roy_cost[kk],4);			//판매기준 로열티
                        actual_cost		= StringUtil.Roundformat(assy_g_actual_cost[kk],4);			//판매기준 총원가
                    }else if(StringUtil.checkNull(pro_type[kk][e][t]).equals("sub_Insert")){
                        p_meterial_cost	= StringUtil.Roundformat(meterial_cost[kk][e][t],4);				//원재료비
                        p_loss			= StringUtil.Roundformat(loss[kk][e][t],4);				//loss비
                        p_scrap_cost	= StringUtil.Roundformat(sub_scrap_cost[e],4);			//스크랩판매비
                        p_m_total_cost	= StringUtil.Roundformat(m_total_cost[kk][e][t],4); 			//재료비

                        p_output	 = StringUtil.Roundformat(sub_output[e],4);				//시간당 생산량
                        p_st_person	 = StringUtil.Roundformat(st_person[kk][e][t],4);				//표준작업인원
                        p_eff_value	 = StringUtil.Roundformat(st_eff_value[kk][e][t],4); 				//효율
                        p_rate		 = StringUtil.Roundformat(sub_rate[e],4);				//임율
                        p_labor_cost = StringUtil.Roundformat(sub_labor_cost[e],4);  			//노무비

                        p_jun_cost	= StringUtil.Roundformat(sub_jun_cost[e],4);				//전력비
                        p_m_upkeep	= StringUtil.Roundformat(sub_m_upkeep[e],4);				//금형유지비
                        p_total_expense	= StringUtil.Roundformat(sub_total_expense[e],4);				//직접경비
                        p_overhead	= StringUtil.Roundformat(sub_overhead[e],4);				//간접경비
                        p_common_cost	= StringUtil.Roundformat(sub_common_cost[e],4); 				//제조경비

                        p_m_upkeep	= StringUtil.Roundformat(m_upkeep[kk][e][t],4);			//금형유지비
                        start_depr	= StringUtil.Roundformat(g_depr_cost[kk][e][t],4);   				//금형감가비

                        plus  = new BigDecimal(StringUtil.Roundformat(sub_g_depr_cost[e],0));
                        minus = new BigDecimal(StringUtil.Roundformat(g_depr_cost[kk][e][t],4));

                        pro_depr = Double.toString(plus.subtract(minus).doubleValue()) ;  //설비감가비

                        earn_rate	= StringUtil.Roundformat(sub_g_earn_rate[e],4);     		//수익률
                        rnd_cost	= StringUtil.Roundformat(sub_g_rnd_cost[e],4);			//R&D비율

                        p_jae_cost	= StringUtil.Roundformat(sub_jae_cost[e],4);				//재료관리비
                        p_ge_cost		= StringUtil.Roundformat(sub_ge_cost[e],4);				//일반관리비

                        p_pack_cost	= StringUtil.Roundformat(sub_pack_cost[e],4);				//포장비
                        p_ad_cost	= StringUtil.Roundformat(sub_ad_cost[e],4); 				//관리비
                        depr_cost	= StringUtil.Roundformat(sub_g_depr_cost[e],4);				//감가비
                        actual_cost	= StringUtil.Roundformat(sub_g_actual_cost[e],4);				//판매기준 총원가
                    }else if(StringUtil.checkNull(pro_type[kk][e][t]).equals("sub_assy")){
                        p_meterial_cost	= StringUtil.Roundformat(g_sub_m_total_cost[kk][e],4);		//원재료비
                        p_m_total_cost	= StringUtil.Roundformat(g_sub_meterial_cost[e],4);		//조립 재료비
                        p_loss			= StringUtil.Roundformat(g_sub_loss[e],4);		        //loss비-판매
                        p_scrap_cost	= StringUtil.Roundformat(sub_scrap_cost[e],4);			//스크랩판매비

                        p_labor_cost	= StringUtil.Roundformat(sub_labor_cost[e],4);  			//노무비
                        p_common_cost	= StringUtil.Roundformat(sub_common_cost[e],4); 			//제조경비
                        p_ad_cost		= StringUtil.Roundformat(sub_ad_cost[e],4);    			//관리비
                        p_output		= StringUtil.Roundformat(sub_output[e],4);		        //시간당 생산량
                        p_eff_value		= StringUtil.Roundformat(sub_rate[e],4);				//임율

                        p_jun_cost		= StringUtil.Roundformat(sub_jun_cost[e],4);			//전력비
                        p_m_upkeep		= StringUtil.Roundformat(sub_m_upkeep[e],4);			//금형유지비
                        p_total_expense	= StringUtil.Roundformat(sub_total_expense[e],4);			//직접경비
                        p_overhead		= StringUtil.Roundformat(sub_overhead[e],4);			//간접경비

                        p_pack_cost		= StringUtil.Roundformat(sub_pack_cost[e],4);			//포장비
                        p_jae_cost		= StringUtil.Roundformat(sub_jae_cost[e],4);			//재료관리비
                        p_ge_cost		= StringUtil.Roundformat(sub_ge_cost[e],4);			//일반관리비
                        rnd_cost		= StringUtil.Roundformat(sub_g_rnd_cost[e],4);    	        //판매기준 R&D비
                        actual_cost		= StringUtil.Roundformat(sub_g_actual_cost[e],4);			//판매기준 총원가
                        earn_rate		= StringUtil.Roundformat(sub_g_earn_rate[e],4);			//판매기준 수익률
                        depr_cost		= StringUtil.Roundformat(sub_g_depr_cost[e],4);			//판매량기준감가비
                    }else{	//단품
                        p_meterial_cost	= StringUtil.Roundformat(meterial_cost[kk][e][t],4);			//원재료비
                        p_loss			= StringUtil.Roundformat(loss[kk][e][t],4);				//loss비


                        p_scrap_cost 	= StringUtil.Roundformat(scrap_cost[kk][e][t],4);									//스크랩판매비
                        p_m_total_cost	= StringUtil.Roundformat(m_total_cost[kk][e][t],4); 		//재료비

                        p_output	= StringUtil.Roundformat(output[kk][e][t],4);				//시간당 생산량
                        p_st_person	= StringUtil.Roundformat(st_person[kk][e][t],4);				//표준작업인원
                        p_eff_value	= StringUtil.Roundformat(st_eff_value[kk][e][t],4); 				//효율
                        p_rate		= StringUtil.Roundformat(rate[kk][e][t],4);				//임율

                        p_labor_cost = StringUtil.Roundformat(labor_cost[kk][e][t],4);  			//노무비

                        p_jun_cost	= StringUtil.Roundformat(jun_cost[kk][e][t],4);			//전력비
                        p_ma_depr	= StringUtil.Roundformat(ma_depr[kk][e][t],4);			//기계감가
                        p_tabalu	= StringUtil.Roundformat(tabalu[kk][e][t],4);				//타발유
                        p_m_upkeep	= StringUtil.Roundformat(m_upkeep[kk][e][t],4);			//금형유지비
                        p_total_expense	= StringUtil.Roundformat(total_expense[kk][e][t],4);			//직접경비
                        p_overhead	= StringUtil.Roundformat(overhead[kk][e][t],4);			//간접경비
                        p_common_cost	= StringUtil.Roundformat(common_cost[kk][e][t],4); 			//제조경비

                        start_depr	= StringUtil.Roundformat(g_depr_cost[kk][e][t],4);   		//금형감가비

                        earn_rate	= StringUtil.Roundformat(g_earn_rate[kk][e][t],4);     		//수익률
                        rnd_cost	= StringUtil.Roundformat(g_rnd_cost[kk][e][t],4);			//R&D비율

                        p_jae_cost	= StringUtil.Roundformat(jae_cost[kk][e][t],4);			//재료관리비
                        p_ge_cost		= StringUtil.Roundformat(ge_cost[kk][e][t],4);			//일반관리비

                        rnd_cost	= StringUtil.Roundformat(g_rnd_cost[kk][e][t],4);    			//판매기준 R&D비
                        royalty_cost	= StringUtil.Roundformat(g_roy_cost[kk][e][t],4);			//판매기준 로열티

                        p_pack_cost	= StringUtil.Roundformat(pack_cost[kk][e][t],4);			//포장비
                        p_ad_cost	= StringUtil.Roundformat(ad_cost[kk][e][t],4);			//관리비
                        depr_cost	= StringUtil.Roundformat(g_depr_cost[kk][e][t],4);			//감가비

                        actual_cost	= StringUtil.Roundformat(g_actual_cost[kk][e][t],4);			//판매기준 총원가
                        System.out.println("<><><><>actual_cost : "+actual_cost);
                    }
                    if(!StringUtil.checkNull(pk_cr_[kk][e][t]).equals("")){
                    	 if(conn.isClosed()){
                 	        try {
                 	            conn = DBUtil.getConnection();
                 	        } catch (Exception e1) {
                 	            e1.printStackTrace();
                 	        }
                         }
                        try{
                            sb.append("select count(*) as check_cnt from cost_work where fk_cost_request = '" + pk_cr_[kk][e][t] + "'");
                            pstmt = conn.prepareStatement(sb.toString());

                            rs = pstmt.executeQuery();

                            while(rs.next()){
                                check_cnt = Integer.parseInt(StringUtil.checkNull(rs.getString("check_cnt")));
                            }
                        }catch(Exception e1){
                            e1.printStackTrace();
                        }finally{
                            sb.delete( 0 , sb.length() );
                            if(rs!=null) {rs.close();}
                            if(pstmt!=null) {pstmt.close();}
                            if(conn!=null) {conn.close();}
                        }
                        if(check_cnt > 0){
                            IsNew = false;
                        }
                    }
                    System.out.println("IsNew==>"+IsNew);
                    to_cost = "0";

                    if(!StringUtil.checkNull(sul_cost[kk][e][t]).equals("")){
                        to_cost = sul_cost[kk][e][t];
                    }else{
                        to_cost = "0";
                    }
                    System.out.println("mold_cost==>"+mold_cost);
                    System.out.println("etc_cost==>"+etc_cost);
                    if(!StringUtil.checkNull(mold_cost[kk][e][t]).equals("")){
                        to_cost = Integer.toString((int)Math.round(StringUtil.nullDouble(StringUtil.checkNullZero(to_cost))) + (int)Math.round(StringUtil.nullDouble(StringUtil.checkNullZero(mold_cost[kk][e][t]))));
                        //to_cost = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(to_cost)) + Integer.parseInt(StringUtil.checkNullZero(mold_cost[kk][e][t])));
                        System.out.println("to_cost = "+to_cost);
                    }else{
                        to_cost = "0";
                    }

                    if(!StringUtil.checkNull(etc_cost[kk][e][t]).equals("")){
                        to_cost = Integer.toString(Integer.parseInt(StringUtil.checkNullZero(to_cost)) + Integer.parseInt(StringUtil.checkNullZero(etc_cost[kk][e][t])));
                    }else{
                        to_cost = "0";
                    }

                    if(IsNew){
                    	 if(conn.isClosed()){
                 	        try {
                 	            conn = DBUtil.getConnection();
                 	        } catch (Exception e1) {
                 	            e1.printStackTrace();
                 	        }
                         }
                        try{
                            sb.append("select cost_work_pk_cw.nextval as seq_pk_cw from dual");
                            pstmt = conn.prepareStatement(sb.toString());

                            rs = pstmt.executeQuery();

                            while(rs.next()){
                                seq_pk_cw = StringUtil.checkNull(rs.getString("seq_pk_cw"));
                            }
                        }catch(Exception e1){
                            e1.printStackTrace();
                        }finally{
                            sb.delete( 0 , sb.length() );
                            if(rs!=null) {rs.close();}
                            if(pstmt!=null) {pstmt.close();}
                            if(conn!=null) {conn.close();}
                        }
                    }
                    System.out.println("2 ResultAcc mold_cost >>>> "+kk+" "+e+" "+t+" "+mold_cost[kk][e][t] );
                    if(IsNew){
                    	 if(conn.isClosed()){
                 	        try {
                 	            conn = DBUtil.getConnection();
                 	        } catch (Exception e1) {
                 	            e1.printStackTrace();
                 	        }
                         }
                        try{
                            sb.append(" insert into cost_work(                                                                                                                          ");
                            sb.append(" 	etc_depr,     y_ex_rate,    u_ex_rate, lme_ton,    to_cost,   etc_cost,        sul_cost,        line_su,      mold_c_type, mold_cost,      ");
                            sb.append(" 	m_su,         yzk_mone,     yazaki_ro, type_1,     type_2,    t_mone,          type_cost,       t_order,      pack_type,   in_pack,        ");
                            sb.append(" 	out_pack,     dis_cost,     cavity,    make_type,  pro_1,     ton,             spm,             method_type,  pro_level,   pro_level_txt,  ");
                            sb.append(" 	plating_type, plating_cost, recycle,   h_weight,   h_scrap,   t_weight,        order_con,       grade_name,   grade_color, unitcost,       ");
                            sb.append(" 	t_size,       w_size,       p_size,    plating,    m_maker,   m_mone,          client_cost,     ket_cost,     target_cost, distri_cost,    ");
                            sb.append(" 	re_m_cost,    usage,        meterial,  start_pro,  store,     dest,            royalty,         su_year_1,    su_year_2,   su_year_3,      ");
                            sb.append(" 	su_year_4,    su_year_5,    su_year_6, su_year_7,  su_year_8, user_case_count, case_type_admin,case_type_user, loss,         scrap_cost,  m_total_cost,   ");
                            sb.append(" 	output,       eff_value,    rate,      labor_cost, jun_cost,  ma_depr,         total_expense,   overhead,     common_cost, pro_depr,       ");
                            sb.append(" 	earn_rate,    rnd_cost,     jae_cost,  ge_cost,    pack_cost, ad_cost,         depr_cost,       royalty_cost, actual_cost, meterial_cost,  ");
                            sb.append(" 	m_upkeep,     start_depr,   tabalu,    pk_cw,      fk_cost_request,  fk_pid, pk_cr_group, group_no, rev_no, case_count_1, case_count_2, pro_type, part_type, part_name ) ");
                            sb.append("values (                                                                                                                                        ");
                            sb.append(" '"+ StringUtil.Roundformat(etc_depr[kk][e][t],4)+ "',");
                            sb.append(" ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ");
                            sb.append(" ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ");
                            sb.append(" ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ");
                            sb.append(" ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,  ?,");
                            sb.append(" ?,?,?,?,?,?,?,?,?,?,?,?,?,?,(select fk_pid from cost_request where pk_cr = ?),?,?,?,?,?,?,?,?   )");

                            pstmt = conn.prepareStatement(sb.toString());
                            pstmt.setString(1, yen_rate                        );
                            pstmt.setString(2, usd_rate                     );
                            pstmt.setString(3, lme_cu                          );
                            pstmt.setString(4, to_cost       );
                            pstmt.setString(5, StringUtil.checkNull(etc_cost[kk][e][t])     );
                            pstmt.setString(6, StringUtil.checkNull(sul_cost[kk][e][t])      );
                            pstmt.setString(7, StringUtil.checkNull(line_su[kk][e][t])       );
                            pstmt.setString(8, StringUtil.checkNull(mold_c_type[kk][e][t])  );
                            pstmt.setString(9, StringUtil.checkNull(mold_cost[kk][e][t])     );
                            pstmt.setString(10, StringUtil.checkNull(m_su[kk][e][t])         );
                            pstmt.setString(11, StringUtil.checkNull(yzk_mone[kk][e][t])     );
                            pstmt.setString(12, StringUtil.checkNull(yazaki_ro[kk][e][t])     );
                            pstmt.setString(13, StringUtil.checkNull(type_1[kk][e][t])        );
                            pstmt.setString(14, StringUtil.checkNull(type_2[kk][e][t])        );
                            pstmt.setString(15, StringUtil.checkNull(t_mone[kk][e][t])        );
                            pstmt.setString(16, StringUtil.Roundformat(StringUtil.checkNull(type_cost[kk][e][t]),3));
                            pstmt.setString(17, StringUtil.checkNull(t_order[kk][e][t])       );
                            pstmt.setString(18, StringUtil.checkNull(pack_type[kk][e][t])     );
                            pstmt.setString(19, StringUtil.checkNull(in_pack[kk][e][t])       );
                            pstmt.setString(20, StringUtil.checkNull(out_pack[kk][e][t])      );
                            pstmt.setString(21, StringUtil.Roundformat(StringUtil.checkNull(dis_cost[kk][e][t]),4));
                            pstmt.setString(22, StringUtil.checkNull(cavity[kk][e][t])        );
                            pstmt.setString(23, StringUtil.checkNull(make_type[kk][e][t])     );
                            pstmt.setString(24, StringUtil.checkNull(pro_1[kk][e][t])         );
                            pstmt.setString(25, StringUtil.checkNull(ton[kk][e][t])           );
                            pstmt.setString(26, StringUtil.checkNull(spm[kk][e][t])           );
                            pstmt.setString(27, StringUtil.checkNull(method_type[kk][e][t])   );
                            pstmt.setString(28, StringUtil.checkNull(pro_level[kk][e][t])     );
                            pstmt.setString(29, StringUtil.checkNull(pro_level_txt[kk][e][t]) );
                            pstmt.setString(30, StringUtil.checkNull(plating_type[kk][e][t])   );
                            pstmt.setString(31, StringUtil.checkNull(plating_cost[kk][e][t])  );
                            pstmt.setString(32, StringUtil.checkNull(recycle[kk][e][t])       );
                            pstmt.setString(33, StringUtil.checkNull(h_weight[kk][e][t])      );
                            pstmt.setString(34, StringUtil.checkNull(h_scrap[kk][e][t])       );
                            pstmt.setString(35, StringUtil.checkNull(t_weight[kk][e][t])      );
                            pstmt.setString(36, StringUtil.checkNull(order_con[kk][e][t])     );
                            pstmt.setString(37, StringUtil.checkNull(grade_name[kk][e][t])    );
                            pstmt.setString(38, StringUtil.checkNull(grade_color[kk][e][t])   );
                            pstmt.setString(39, StringUtil.checkNull(unitcost[kk][e][t])      );
                            pstmt.setString(40, StringUtil.checkNull(t_size[kk][e][t])        );
                            pstmt.setString(41, StringUtil.checkNull(w_size[kk][e][t])        );
                            pstmt.setString(42, StringUtil.checkNull(p_size[kk][e][t])        );
                            pstmt.setString(43, StringUtil.checkNull(plating[kk][e][t])       );
                            pstmt.setString(44, StringUtil.checkNull(m_maker[kk][e][t])       );
                            pstmt.setString(45, StringUtil.checkNull(m_mone[kk][e][t])        );
                            pstmt.setString(46, StringUtil.checkNull(client_cost_[kk])         );
                            pstmt.setString(47, StringUtil.checkNull(ket_cost[kk])            );
                            pstmt.setString(48, StringUtil.checkNull(target_cost[kk])         );
                            pstmt.setString(49, StringUtil.checkNull(distri_cost[kk])         );
                            pstmt.setString(50, StringUtil.checkNull(re_m_cost[kk][e][t])     );
                            pstmt.setString(51, StringUtil.checkNull(usage[kk][e][t])         );
                            pstmt.setString(52, StringUtil.checkNull(meterial[kk][e][t])      );
                            pstmt.setString(53, StringUtil.checkNull(start_pro[kk])           );
                            pstmt.setString(54, StringUtil.checkNull(store[kk])               );
                            pstmt.setString(55, StringUtil.checkNull(dest[kk])                );
                            pstmt.setString(56, StringUtil.checkNull(royalty[kk])             );
                            pstmt.setString(57, StringUtil.checkNull(a_su_year_1[kk])         );
                            pstmt.setString(58, StringUtil.checkNull(a_su_year_2[kk])         );
                            pstmt.setString(59, StringUtil.checkNull(a_su_year_3[kk])         );
                            pstmt.setString(60, StringUtil.checkNull(a_su_year_4[kk])         );
                            pstmt.setString(61, StringUtil.checkNull(a_su_year_5[kk])         );
                            pstmt.setString(62, StringUtil.checkNull(a_su_year_6[kk])        );
                            pstmt.setString(63, StringUtil.checkNull(a_su_year_7[kk])         );
                            pstmt.setString(64, StringUtil.checkNull(a_su_year_8[kk])         );
                            pstmt.setString(65, group_case_count                );
                            pstmt.setString(66, "main"                          );
                            pstmt.setString(67, "main"                          );
                            pstmt.setString(68, p_loss                          );
                            pstmt.setString(69, p_scrap_cost                    );
                            pstmt.setString(70, p_m_total_cost                  );
                            pstmt.setString(71, p_output                        );
                            pstmt.setString(72, p_eff_value                       );
                            pstmt.setString(73, p_rate                          );
                            pstmt.setString(74, p_labor_cost                    );
                            pstmt.setString(75, p_jun_cost                      );
                            pstmt.setString(76, p_ma_depr                       );
                            pstmt.setString(77, p_total_expense                 );
                            pstmt.setString(78, p_overhead                      );
                            pstmt.setString(79, p_common_cost                   );
                            pstmt.setString(80, pro_depr                        );
                            pstmt.setString(81, earn_rate                       );
                            pstmt.setString(82, rnd_cost                        );
                            pstmt.setString(83, p_jae_cost                      );
                            pstmt.setString(84, p_ge_cost                       );
                            pstmt.setString(85, p_pack_cost                     );
                            pstmt.setString(86, p_ad_cost                       );
                            pstmt.setString(87, depr_cost                       );
                            pstmt.setString(88, royalty_cost                    );
                            pstmt.setString(89, actual_cost                     );
                            pstmt.setString(90, p_meterial_cost                 );
                            pstmt.setString(91, p_m_upkeep                      );
                            pstmt.setString(92, start_depr                      );
                            pstmt.setString(93, p_tabalu                        );
                            pstmt.setString(94, seq_pk_cw                       );
                            pstmt.setString(95, pk_cr_[kk][e][t]                );
                            pstmt.setString(96, pk_cr_[kk][e][t]                 );

                            pstmt.setString(97, p_pk_cr_group               );
                            pstmt.setString(98, group_no[kk][e][t]               );
                            pstmt.setString(99, rev_no                );
                            pstmt.setString(100, case_count_1[kk]                );
                            pstmt.setString(101, case_count_2[kk][e]           );
                            pstmt.setString(102, pro_type[kk][e][t]                );
                            pstmt.setString(103, part_type_1[kk][e][t]                );
                            pstmt.setString(104, part_name[kk][e][t]               );

                            pstmt.executeUpdate();
                        }catch(Exception e1){
                            e1.printStackTrace();
                        }finally{
                            sb.delete( 0 , sb.length() );
                            if(rs!=null) {rs.close();}
                            if(pstmt!=null) {pstmt.close();}
                            if(conn!=null) {conn.close();}
                        }
                    }else{
                    	 if(conn.isClosed()){
                 	        try {
                 	            conn = DBUtil.getConnection();
                 	        } catch (Exception e1) {
                 	            e1.printStackTrace();
                 	        }
                         }
                        try{
                            sb.append("update cost_work                 ");
                            sb.append("   set etc_depr             = '" + StringUtil.Roundformat(etc_depr[kk][e][t],4) +"'"); //기타감가
                            sb.append("      ,y_ex_rate            = '" + yen_rate  + "'"); //YEN기준환율
                            sb.append("      ,u_ex_rate            = '" + usd_rate + "'"); //USD_rate
                            sb.append("      ,lme_ton              = '" + lme_cu   + "'");
                            sb.append("      ,to_cost              = '" + to_cost + "'");
                            sb.append("      ,etc_cost             = '" + etc_cost[kk][e][t] + "'");
                            sb.append("      ,sul_cost             = '" + sul_cost[kk][e][t] + "'");
                            sb.append("      ,line_su              = '" + line_su[kk][e][t] + "'");
                            sb.append("      ,mold_c_type          = '" + mold_c_type[kk][e][t] + "'");
                            sb.append("      ,mold_cost            = '" + mold_cost[kk][e][t] + "'");
                            sb.append("      ,m_su                 = '" + m_su[kk][e][t] + "'");
                            sb.append("      ,yzk_mone             = '" + yzk_mone[kk][e][t] + "'");
                            sb.append("      ,yazaki_ro            = '" + yazaki_ro[kk][e][t] + "'");
                            sb.append("      ,type_1               = '" + type_1[kk][e][t] + "'");
                            sb.append("      ,type_2               = '" + type_2[kk][e][t] + "'");
                            sb.append("      ,t_mone               = '" + t_mone[kk][e][t] + "'");
                            sb.append("      ,type_cost            = '" + StringUtil.Roundformat(StringUtil.checkNull(type_cost[kk][e][t]),3) + "'");
                            sb.append("      ,t_order              = '" + t_order[kk][e][t] + "'");
                            sb.append("      ,pack_type            = '" + pack_type[kk][e][t] + "'");
                            sb.append("      ,in_pack              = '" + in_pack[kk][e][t]        +"'");
                            sb.append("      ,out_pack             = '" + out_pack[kk][e][t]       +"'");
                            sb.append("      ,dis_cost             = '" + StringUtil.Roundformat(StringUtil.checkNull(dis_cost[kk][e][t]),4)       +"'");
                            sb.append("      ,cavity               = '" + cavity[kk][e][t]         +"'");
                            sb.append("      ,make_type            = '" + make_type[kk][e][t]      +"'");
                            sb.append("      ,pro_1                = '" + pro_1[kk][e][t]          +"'");
                            sb.append("      ,ton                  = '" + ton[kk][e][t]            +"'");
                            sb.append("      ,spm                  = '" + spm[kk][e][t]            +"'");
                            sb.append("      ,method_type          = '" + method_type[kk][e][t]    +"'");
                            sb.append("      ,pro_level            = '" + pro_level[kk][e][t]      +"'");
                            sb.append("      ,pro_level_txt        = '" + pro_level_txt[kk][e][t]  +"'");
                            sb.append("      ,plating_type         = '" + plating_type[kk][e][t]   +"'");
                            sb.append("      ,plating_cost         = '" + plating_cost[kk][e][t]   +"'");
                            sb.append("      ,recycle              = '" + recycle[kk][e][t]        +"'");
                            sb.append("      ,h_weight             = '" + h_weight[kk][e][t]       +"'");
                            sb.append("      ,h_scrap              = '" + h_scrap[kk][e][t]        +"'");
                            sb.append("      ,t_weight             = '" + t_weight[kk][e][t]       +"'");
                            sb.append("      ,order_con            = '" + order_con[kk][e][t]      +"'");
                            sb.append("      ,grade_name           = '" + grade_name[kk][e][t]     +"'");
                            sb.append("      ,grade_color          = '" + grade_color[kk][e][t]    +"'");
                            sb.append("      ,unitcost             = '" + unitcost[kk][e][t]       +"'");
                            sb.append("      ,t_size               = '" + t_size[kk][e][t]         +"'");
                            sb.append("      ,w_size               = '" + w_size[kk][e][t]         +"'");
                            sb.append("      ,p_size               = '" + p_size[kk][e][t]         +"'");
                            sb.append("      ,plating              = '" + plating[kk][e][t]        +"'");
                            sb.append("      ,m_maker              = '" + m_maker[kk][e][t]        +"'");
                            sb.append("      ,m_mone               = '" + m_mone[kk][e][t]         +"'");
                            sb.append("      ,client_cost          = '" + client_cost_[kk]    +"'");
                            sb.append("      ,ket_cost             = '" + ket_cost[kk]       +"'");
                            sb.append("      ,target_cost          = '" + target_cost[kk]    +"'");
                            sb.append("      ,distri_cost          = '" + distri_cost[kk]    +"'");
                            sb.append("      ,re_m_cost            = '" + re_m_cost[kk][e][t]      +"'");
                            sb.append("      ,usage                = '" + usage[kk][e][t]          +"'");
                            sb.append("      ,meterial             = '" + meterial[kk][e][t]       +"'");
                            sb.append("      ,start_pro            = '" + start_pro[kk]      +"'");
                            sb.append("      ,store                = '" + store[kk]          +"'");
                            sb.append("      ,dest                 = '" + dest[kk]           +"'");
                            sb.append("      ,royalty              = '" + royalty[kk]        +"'");
                            sb.append("      ,su_year_1            = '" + a_su_year_1[kk]      +"'");
                            sb.append("      ,su_year_2            = '" + a_su_year_2[kk]      +"'");
                            sb.append("      ,su_year_3            = '" + a_su_year_3[kk]      +"'");
                            sb.append("      ,su_year_4            = '" + a_su_year_4[kk]      +"'");
                            sb.append("      ,su_year_5            = '" + a_su_year_5[kk]      +"'");
                            sb.append("      ,su_year_6            = '" + a_su_year_6[kk]      +"'");
                            sb.append("      ,su_year_7            = '" + a_su_year_7[kk]      +"'");
                            sb.append("      ,su_year_8            = '" + a_su_year_8[kk]      +"'");
                            sb.append("      ,user_case_count      = '" + group_case_count +"'");
                            sb.append("      ,case_type_admin      = 'main' ");
                            sb.append("      ,case_type_user        = 'main' ");
                            sb.append("      ,loss                 = '" + p_loss +"'");
                            sb.append("      ,scrap_cost           = '" + p_scrap_cost     +"'");
                            sb.append("      ,m_total_cost         = '" + p_m_total_cost   +"'");
                            sb.append("      ,output               = '" + p_output         +"'");
                            sb.append("      ,eff_value            = '" + p_eff_value        +"'");
                            sb.append("      ,rate                 = '" + p_rate           +"'");
                            sb.append("      ,labor_cost           = '" + p_labor_cost     +"'");
                            sb.append("      ,jun_cost             = '" + p_jun_cost       +"'");
                            sb.append("      ,ma_depr              = '" + p_ma_depr        +"'");
                            sb.append("      ,total_expense        = '" + p_total_expense  +"'");
                            sb.append("      ,overhead             = '" + p_overhead       +"'");
                            sb.append("      ,common_cost          = '" + p_common_cost    +"'");
                            sb.append("      ,pro_depr             = '" + pro_depr         +"'");
                            sb.append("      ,earn_rate            = '" + earn_rate        +"'");
                            sb.append("      ,rnd_cost             = '" + rnd_cost         +"'");
                            sb.append("      ,jae_cost             = '" + p_jae_cost       +"'");
                            sb.append("      ,ge_cost              = '" + p_ge_cost        +"'");
                            sb.append("      ,pack_cost            = '" + p_pack_cost      +"'");
                            sb.append("      ,ad_cost              = '" + p_ad_cost        +"'");
                            sb.append("      ,depr_cost            = '" + depr_cost        +"'");
                            sb.append("      ,royalty_cost         = '" + royalty_cost     +"'");
                            sb.append("      ,actual_cost          = '" + actual_cost      +"'");
                            sb.append("      ,meterial_cost        = '" + p_meterial_cost  +"'");
                            sb.append("      ,m_upkeep             = '" + p_m_upkeep         +"'");
                            sb.append("      ,start_depr           = '" + start_depr       +"'");
                            sb.append("      ,tabalu               = '" + p_tabalu         +"'");
                            sb.append("      ,pk_cr_group           = '" + p_pk_cr_group         +"'");
                            sb.append("      ,group_no               = '" + group_no[kk][e][t]          +"'");
                            sb.append("      ,rev_no              		= '" + rev_no         +"'");
                            sb.append("      ,case_count_1        = '" + case_count_1[kk]        +"'");
                            sb.append("      ,case_count_2        = '" + case_count_2[kk][e]         +"'");
                            sb.append("      ,pro_type               = '" + pro_type[kk][e][t]         +"'");
                            sb.append("      ,part_type              = '" + part_type_1[kk][e][t]        +"'");
                            sb.append("      ,part_name            = '" + part_name[kk][e][t]         +"'");
                            sb.append(" where fk_cost_request = '" + pk_cr_[kk][e][t] + "'");
                            pstmt = conn.prepareStatement(sb.toString());

                            pstmt.executeUpdate();

                        }catch(Exception e1){
                            e1.printStackTrace();
                        }finally{
                            sb.delete( 0 , sb.length() );
                            if(rs!=null) {rs.close();}
                            if(pstmt!=null) {pstmt.close();}
                            if(conn!=null) {conn.close();}
                        }
                    }
                }
            }
        }
        String[] acc_case_value = new String[3];
        if(case_data_value.equals("")){
            case_data_value = "no";
        }
        if(group_case_count.equals("")){
            group_case_count = "0";
        }
        acc_case_value[0] = case_data_value;
        acc_case_value[1] = group_case_count;
        acc_case_value[2] = table_row;

        return acc_case_value;
    }
}
