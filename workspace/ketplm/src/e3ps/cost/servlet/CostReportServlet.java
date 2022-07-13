package e3ps.cost.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PersistenceHelper;

import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.web.CommonServlet;
import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;
import e3ps.cost.dao.CostReportDao;

public class CostReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
		RequestDispatcher rd = null;
        //cost_report_add - 보고서 DB에 자료 유무상태 (OK=있을때)
        //chk_list -  산출결과선택Pk_no List
        //System.out.println("AAA");
        String cost_report_add = StringUtil.getParameter(req, "cost_report_add");
        String t_chk_list = StringUtil.getParameter(req, "t_chk_list");
        String us_change = StringUtil.getParameter(req, "us_change");
        String case_sel = StringUtil.getParameter(req, "case_sel");
        String cmd = StringUtil.getParameter(req, "cmd");

//		System.out.println(pro_usage[0]);
//		System.out.println(pro_usage[1]);
//		System.out.println(pro_usage[2]);
//		System.out.println(pro_usage.length);
        String report_pk = "";

        System.out.println("========= cmd ======> "+cmd);
        if(cmd.equals("ApprList")){
            ReportApprListSearch(req,res);
        }else if("report_create_temp".equals(cmd) || "modify_create_temp".equals(cmd) || "report_create_temp_electric".equals(cmd) || "modify_create_temp_electric".equals(cmd)){
        	report_pk = ReportCreate(req, res, cmd);
        	System.out.println("report_pk : "+report_pk);

        	//alertGo(res,"/plm/jsp/cost/costreport/cost_report_1_edit_temp.jsp?report_pk="+report_pk,"저장성공..");
        	if("report_create_temp_electric".equals(cmd) || "modify_create_temp_electric".equals(cmd)){
        		res.sendRedirect("/plm/jsp/cost/costreport/cost_report_add_temp.jsp?report_pk="+report_pk); // 생성 후 상세조회 페이지로 이동하도록 수정
        	}else{
        		res.sendRedirect("/plm/jsp/cost/costreport/cost_report_1_edit_temp.jsp?report_pk="+report_pk); // 생성 후 상세조회 페이지로 이동하도록 수정
        	}
        }else{
        cost_report_add ="ok";
            if("ok".equals(case_sel)){
                WorkSearch(req,res,t_chk_list);
            }else if("ok".equals(cost_report_add) && "ok".equals(us_change)){
                UsChange(req, res);
            }else if("ok".equals(cost_report_add)&& !"ok".equals(us_change)){
                ReportSearch(req, res);
            }
        }
    }

    private String ReportCreate(HttpServletRequest req, HttpServletResponse res, String cmd) {

        FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
        KETParamMapUtil map = KETParamMapUtil.getMap(uploader.getFormParameters());
System.out.println("여기 들어왔나...");

        // 1. 개발배경 및 투자비 현황 항목 파라미터 Start
        String team 				= map.getString("team");					//원가산출요청팀
        String f_name 				= map.getString("f_name");					//개발팀 담당자
        String a_name 				= map.getString("a_name");					//영업팀 담당자
        String customer_f 			= map.getString("customer_f");				//고객사명
        String pjt_name 			= map.getString("pjt_name");				//제품명
        String pjt_no 				= map.getString("pjt_no").trim().toUpperCase();	//pjt_no
        String car_type 			= map.getString("car_type");				//적용차종
        String app_part 			= map.getString("app_part");				//적용부위
        String request_txt 			= map.getString("request_txt");				//검토목적
        String report_dest 			= map.getString("report_dest");				//물류흐름

        String case_to_note_1 		= map.getString("case_to_note_1");	//신규투자비 검토현황 1안
        String case_to_note_2 		= map.getString("case_to_note_2");	//신규투자비 검토현황 2안
        String case_to_note_3 		= map.getString("case_to_note_3");	//신규투자비 검토현황 3안

        String mold_count 			= map.getString("mold_count");		//Mold 수량
        String press_count 			= map.getString("press_count");		//Press 수량
        String line_count 			= map.getString("line_count");		//조립설비 수량
        String pack_count 			= map.getString("pack_count");		//기타투자비 수량
        String repack_count 		= map.getString("repack_count");	//포장용 투자비 수량

        String mold_total_1 		= map.getString("mold_total_1");	//Mold 1안(천원 단위)
        mold_total_1 				= StringUtil.checkNull(mold_total_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String mold_total_2 		= map.getString("mold_total_2");	//Mold 2안(천원 단위)
        mold_total_2 				= StringUtil.checkNull(mold_total_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String mold_total_3 		= map.getString("mold_total_3");	//Mold 3안(천원 단위)
        mold_total_3 				= StringUtil.checkNull(mold_total_3).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String press_total_1 		= map.getString("press_total_1");	//Press 1안(천원 단위)
        press_total_1 				= StringUtil.checkNull(press_total_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String press_total_2 		= map.getString("press_total_2");	//Press 2안(천원 단위)
        press_total_2 				= StringUtil.checkNull(press_total_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String press_total_3 		= map.getString("press_total_3");	//Press 3안(천원 단위)
        press_total_3 				= StringUtil.checkNull(press_total_3).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String line_total_1 		= map.getString("line_total_1");	//조립설비 1안(천원 단위)
        line_total_1 				= StringUtil.checkNull(line_total_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String line_total_2 		= map.getString("line_total_2");	//조립설비 2안(천원 단위)
        line_total_2 				= StringUtil.checkNull(line_total_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String line_total_3 		= map.getString("line_total_3");	//조립설비 3안(천원 단위)
        line_total_3 				= StringUtil.checkNull(line_total_3).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String pack_total_1 		= map.getString("pack_total_1");	//기타투자비 1안
        pack_total_1 				= StringUtil.checkNull(pack_total_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String pack_total_2 		= map.getString("pack_total_2");	//기타투자비 2안
        pack_total_2 				= StringUtil.checkNull(pack_total_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String pack_total_3 		= map.getString("pack_total_3");	//기타투자비 3안
        pack_total_3 				= StringUtil.checkNull(pack_total_3).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String repack_total_1 		= map.getString("repack_total_1");		//포장용 투자비 1안(천원 단위)
        repack_total_1 				= StringUtil.checkNull(repack_total_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String repack_total_2 		= map.getString("repack_total_2");		//포장용 투자비 2안(천원 단위)
        repack_total_2 				= StringUtil.checkNull(repack_total_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String repack_total_3 		= map.getString("repack_total_3");		//포장용 투자비 3안(천원 단위)
        repack_total_3 				= StringUtil.checkNull(repack_total_3).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String m_depr_stan 			= map.getString("m_depr_stan");		//Mold 상각기준
        String p_depr_stan 			= map.getString("p_depr_stan");		//Press 상각기준
        String l_depr_stan 			= map.getString("l_depr_stan");		//조립설비 상각기준
        String pack_depr_stan 		= map.getString("pack_depr_stan");	//기타투자비 상각기준
        String repack_depr_stan 	= map.getString("repack_depr_stan");	//포장용 투자비  상각기준

        String tocost_year 			= map.getString("tocost_year");			//투자비 회수기간



        //1. 개발배경 및 투자비 현황 란의 항목 파라미터 End
        //----------------------------------
        //----------------------------------

        //2. 예상 손익 및 환율, 원재료 가격 기준 파라미터 Start


        String su_year_1 			= map.getString("su_year_1");			//1년차 판매량(천개)
        su_year_1 					= StringUtil.checkNull(su_year_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String su_year_2 			= map.getString("su_year_2");			//2년차 판매량(천개)
        su_year_2 					= StringUtil.checkNull(su_year_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String su_year_3 			= map.getString("su_year_3");			//3년차 판매량(천개)
        su_year_3 					= StringUtil.checkNull(su_year_3).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String su_year_4 			= map.getString("su_year_4");			//4년차 판매량(천개)
        su_year_4 					= StringUtil.checkNull(su_year_4).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String su_year_5 			= map.getString("su_year_5");			//5년차 판매량(천개)
        su_year_5 					= StringUtil.checkNull(su_year_5).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String su_year_6 			= map.getString("su_year_6");			//6년차 판매량(천개)
        su_year_6 					= StringUtil.checkNull(su_year_6).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String su_year_7 			= map.getString("su_year_7");			//7년차 판매량(천개)
        su_year_7 					= StringUtil.checkNull(su_year_7).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String su_year_8 			= map.getString("su_year_8");			//8년차 판매량(천개)
        su_year_8 					= StringUtil.checkNull(su_year_8).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String su_stan_day 			= map.getString("su_stan_day");			//수지 재료단가

        String total_sales_1        = map.getString("total_sales_1");			//매출액1년차
        total_sales_1 				= StringUtil.checkNull(total_sales_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String total_sales_2        = map.getString("total_sales_2");			//매출액2년차
        total_sales_2 				= StringUtil.checkNull(total_sales_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String total_sales_3        = map.getString("total_sales_3");			//매출액3년차
        total_sales_3 				= StringUtil.checkNull(total_sales_3).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String total_sales_4        = map.getString("total_sales_4");			//매출액4년차
        total_sales_4 				= StringUtil.checkNull(total_sales_4).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String total_sales_5        = map.getString("total_sales_5");			//매출액5년차
        total_sales_5 				= StringUtil.checkNull(total_sales_5).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String total_sales_6        = map.getString("total_sales_6");			//매출액6년차
        total_sales_6 				= StringUtil.checkNull(total_sales_6).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String total_sales_7        = map.getString("total_sales_7");			//매출액7년차
        total_sales_7 				= StringUtil.checkNull(total_sales_7).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String total_sales_8        = map.getString("total_sales_8");			//매출액8년차
        total_sales_8 				= StringUtil.checkNull(total_sales_8).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String profit_1             = map.getString("profit_1"); //영업이익-1년차
        profit_1 					= StringUtil.checkNull(profit_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String profit_2             = map.getString("profit_2"); //영업이익-2년차
        profit_2 					= StringUtil.checkNull(profit_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String profit_3             = map.getString("profit_3"); //영업이익-3년차
        profit_3 					= StringUtil.checkNull(profit_3).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String profit_4             = map.getString("profit_4"); //영업이익-4년차
        profit_4 					= StringUtil.checkNull(profit_4).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String profit_5             = map.getString("profit_5"); //영업이익-5년차
        profit_5 					= StringUtil.checkNull(profit_5).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String profit_6             = map.getString("profit_6"); //영업이익-6년차
        profit_6 					= StringUtil.checkNull(profit_6).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String profit_7             = map.getString("profit_7"); //영업이익-7년차
        profit_7 					= StringUtil.checkNull(profit_7).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String profit_8             = map.getString("profit_8"); //영업이익-8년차
        profit_8 					= StringUtil.checkNull(profit_8).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String per_profit_1         = map.getString("per_profit_1"); //영업이익률 1년차
        per_profit_1 				= StringUtil.checkNull(per_profit_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String per_profit_2         = map.getString("per_profit_2"); //영업이익률 2년차
        per_profit_2 				= StringUtil.checkNull(per_profit_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String per_profit_3         = map.getString("per_profit_3"); //영업이익률 3년차
        per_profit_3 				= StringUtil.checkNull(per_profit_3).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String per_profit_4         = map.getString("per_profit_4"); //영업이익률 4년차
        per_profit_4 				= StringUtil.checkNull(per_profit_4).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String per_profit_5         = map.getString("per_profit_5"); //영업이익률 5년차
        per_profit_5 				= StringUtil.checkNull(per_profit_5).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String per_profit_6         = map.getString("per_profit_6"); //영업이익률 6년차
        per_profit_6 				= StringUtil.checkNull(per_profit_6).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String per_profit_7         = map.getString("per_profit_7"); //영업이익률 7년차
        per_profit_7 				= StringUtil.checkNull(per_profit_7).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String per_profit_8         = map.getString("per_profit_8"); //영업이익률 8년차
        per_profit_8 				= StringUtil.checkNull(per_profit_8).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String pack_type            = map.getString("pack_type"); 	//포장유형
        String pro_1                = map.getString("pro_1"); 		//생산처 구분
        String eff_value            = map.getString("eff_value"); 	//생산 효율
        String dev_step             = map.getString("dev_step_val"); 	//개발단계
        //예상 손익 및 환율 원재료 가격 기준 end//////

        //3.원가계산 결과 파라미터 Start
        String group_no = ""; //GROUP_NO
        String select_cost = map.getString("select_val"); //선택 안
        String case_1_note = map.getString("case_1_note"); //1안 note
        String case_2_note = map.getString("case_2_note"); //2안 note
        String case_3_note = map.getString("case_3_note"); //3안 note

        String[] part_name_temp = map.getStringArray("part_name"); //제품명
        String part_name = "";

        String[] pro_usage_temp = map.getStringArray("pro_usage"); //적용 u/s
        String pro_usage = "";

        String[] ket_cost_temp = map.getStringArray("ket_cost"); //판매 목표가
        String ket_cost = "";

        String[] target_cost_temp = map.getStringArray("target_cost"); //목표 수익률
        String target_cost = "";

        String[] actual_cost_1_temp = map.getStringArray("actual_cost_1"); //1안 총원가
        String actual_cost_1 = "";

        String[] earn_rate_1_temp = map.getStringArray("earn_rate_1"); //1안 수익률
        String earn_rate_1 = "";

        String[] actual_cost_2_temp = map.getStringArray("actual_cost_2"); //2안 총원가
        String actual_cost_2 = "";

        String[] earn_rate_2_temp = map.getStringArray("earn_rate_2"); //2안 수익률
        String earn_rate_2 = "";

        String[] actual_cost_3_temp = map.getStringArray("actual_cost_3"); //3안 총원가
        String actual_cost_3 = "";

        String[] earn_rate_3_temp = map.getStringArray("earn_rate_3"); //3안 수익률
        String earn_rate_3 = "";

        String[] modify_report_pk_temp = map.getStringArray("pk_crp"); //보고서 pk_crp
        String modify_report_pk = "";

        String[] pk_pid_temp = map.getStringArray("pk_pid"); //cost_productinfo pk
        String pk_pid = "";

        String actual_cost_sum_1 = map.getString("actual_cost_sum_1"); //1안 총원가 합계
        actual_cost_sum_1 		 = StringUtil.checkNull(actual_cost_sum_1).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String actual_cost_sum_2 = map.getString("actual_cost_sum_2"); //2안 총원가 합계
        actual_cost_sum_2 		 = StringUtil.checkNull(actual_cost_sum_2).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String actual_cost_sum_3 = map.getString("actual_cost_sum_3"); //3안 총원가 합계
        actual_cost_sum_3 		 = StringUtil.checkNull(actual_cost_sum_3).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String earn_rate_sum_1 = map.getString("earn_rate_sum_1");//1안 수익률 합계
        earn_rate_sum_1 		 = StringUtil.checkNull(earn_rate_sum_1).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String earn_rate_sum_2 = map.getString("earn_rate_sum_2");//2안 수익률 합계
        earn_rate_sum_2 		 = StringUtil.checkNull(earn_rate_sum_2).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String earn_rate_sum_3 = map.getString("earn_rate_sum_3");//3안 수익률 합계
        earn_rate_sum_3 		 = StringUtil.checkNull(earn_rate_sum_3).replace(",", "").replace("%", ""); //콤마 및 % 제거

        String note_1 = map.getString("note_1");
        String note_2 = map.getString("note_2");
        String note_3 = map.getString("note_3");
        String note_4 = map.getString("note_4");
        String u_ex_rate = map.getString("u_ex_rate");
        u_ex_rate 		 = StringUtil.checkNull(u_ex_rate).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String lme_cu    = map.getString("lme_cu");
        lme_cu 		 	 = StringUtil.checkNull(lme_cu).replace(",", "").replace("%", ""); //콤마 및 % 제거
        String w_name	 = map.getString("Ename");
        String dr_step	 = map.getString("dr_step");
        String division  = map.getString("division");
        System.out.println("part_name_temp.length : "   	 + part_name_temp.length);

        Connection conn = null;

        ArrayList createDataList = new ArrayList();
        Hashtable<String, String> createData = new Hashtable<String, String>();
        String pk_crp = ""; //cost_report pk 보고서
        String rev_no = ""; //max rev_no  product_info
        String pk_wid = ""; //wf_info pk  결재
        String table_row = "1";

        try{
	        conn = DBUtil.getConnection(false);
	        CostReportDao codeDao = new CostReportDao(conn);
	        if("report_create_temp".equals(cmd) || "report_create_temp_electric".equals(cmd)){ //신규 생성일 때
	        	pk_crp = codeDao.getPkCrt(); 		//cost_report pk 보고서
	        	rev_no = codeDao.GetMaxRev(pjt_no);	//max rev_no  product_info
	        	pk_wid = codeDao.GetwfPk();			//wf_info pk  결재
	        }

	        if( part_name_temp != null){
	 		   for( int cnt=0; cnt < part_name_temp.length; cnt++ ){
	 			   table_row =  Integer.toString(part_name_temp.length);
	 			   if(part_name_temp.length > 1){//lengtg가 1일때 array 에러 발생하므로 분기처리함 1보다 클때만 배열처리하도록.
		 			   part_name = part_name_temp[cnt]; 	 //제품명
		 			   if(pro_usage_temp != null){
		 				   pro_usage     = pro_usage_temp[cnt]; 	 //적용 u/s
		 			   }
		 			   if(ket_cost_temp != null){
		 				   ket_cost      = ket_cost_temp[cnt];  	 //판매 목표가
		 				   ket_cost 	 = StringUtil.checkNull(ket_cost).replace(",", "").replace("%", ""); //콤마 및 % 제거
		 			   }
		 			   if(target_cost_temp != null){
		 				   target_cost   = target_cost_temp[cnt];    //목표 수익률
		 				   target_cost 	 = StringUtil.checkNull(target_cost).replace(",", "").replace("%", ""); //콤마 및 % 제거
		 			   }
		 			   if(actual_cost_1_temp != null){
		 				   actual_cost_1 = actual_cost_1_temp[cnt];  //1안 총원가
		 				   actual_cost_1 = StringUtil.checkNull(actual_cost_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
		 			   }
		 			   if(earn_rate_1_temp != null){
		 				   earn_rate_1   = earn_rate_1_temp[cnt];    //1안 수익률
		 				   earn_rate_1 = StringUtil.checkNull(earn_rate_1).replace(",", "").replace("%", ""); //콤마 및 % 제거
		 			   }
		 			   if(actual_cost_2_temp != null){
		 				   actual_cost_2 = actual_cost_2_temp[cnt];  //2안 총원가
		 				   actual_cost_2 = StringUtil.checkNull(actual_cost_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
		 			   }
		 			   if(earn_rate_2_temp != null){
		 				   earn_rate_2   = earn_rate_2_temp[cnt];    //2안 수익률
		 				   earn_rate_2 = StringUtil.checkNull(earn_rate_2).replace(",", "").replace("%", ""); //콤마 및 % 제거
		 			   }
		 			   if(actual_cost_3_temp != null){
		 				   actual_cost_3 = actual_cost_3_temp[cnt];  //3안 총원가
		 				   actual_cost_3 = StringUtil.checkNull(actual_cost_3).replace(",", "").replace("%", ""); //콤마 및 % 제거
		 			   }
		 			   if(earn_rate_3_temp != null){
		 				   earn_rate_3   = earn_rate_3_temp[cnt];    //3안 수익률
		 				   earn_rate_3 = StringUtil.checkNull(earn_rate_3).replace(",", "").replace("%", ""); //콤마 및 % 제거
		 			   }
		 			   if(modify_report_pk_temp != null){// 보고서 pk .. 기 생성된 보고서 데이터 수정시 사용
		 				  modify_report_pk   = modify_report_pk_temp[cnt];
		 			   }

		 			   if(pk_pid_temp != null){// CostProductinfo 수정시 사용
		 				  pk_pid = pk_pid_temp[cnt];
		 				  System.out.println("::::::::=====>    pk_pid : " + pk_pid);
		 			   }
		 			   group_no = "T00"+Integer.toString(cnt+1); //GROUP_NO
	 			   }else{
	 				   group_no = "T001";
	 		 	       part_name = map.getString("part_name"); //제품명
	 		 	       pro_usage = map.getString("pro_usage"); //적용 u/s

	 		 	       ket_cost = map.getString("ket_cost"); //판매 목표가
	 		 	       ket_cost = StringUtil.checkNull(ket_cost).replace(",", "").replace("%", ""); //콤마 및 % 제거

	 		 	       target_cost = map.getString("target_cost"); //목표 수익률
	 		 	       target_cost = StringUtil.checkNull(target_cost).replace(",", "").replace("%", ""); //콤마 및 % 제거

	 		 	       actual_cost_1 = map.getString("actual_cost_1"); //1안 총원가
	 		 	       actual_cost_1 = StringUtil.checkNull(actual_cost_1).replace(",", "").replace("%", ""); //콤마 및 % 제거

	 		 	       earn_rate_1 = map.getString("earn_rate_1"); //1안 수익률
	 		 	       earn_rate_1 = StringUtil.checkNull(earn_rate_1).replace(",", "").replace("%", ""); //콤마 및 % 제거

	 		 	       actual_cost_2 = map.getString("actual_cost_2"); //2안 총원가
	 		 	       actual_cost_2 = StringUtil.checkNull(actual_cost_2).replace(",", "").replace("%", ""); //콤마 및 % 제거

	 		 	       earn_rate_2 = map.getString("earn_rate_2"); //2안 수익률
	 		 	       earn_rate_2 = StringUtil.checkNull(earn_rate_2).replace(",", "").replace("%", ""); //콤마 및 % 제거

	 		 	       actual_cost_3 = map.getString("actual_cost_3"); //3안 총원가;
	 		 	       actual_cost_3 = StringUtil.checkNull(actual_cost_3).replace(",", "").replace("%", ""); //콤마 및 % 제거

	 		 	       earn_rate_3 = map.getString("earn_rate_3"); //1안 수익률
	 		 	       earn_rate_3 = StringUtil.checkNull(earn_rate_3).replace(",", "").replace("%", ""); //콤마 및 % 제거

	 		 	       modify_report_pk = map.getString("pk_crp"); //보고서 pk
	 		 	       pk_pid = map.getString("pk_pid"); //Costproductinfo pk
	 			   }
	 			   /*System.out.println("pjt_no    : " + pjt_no);
	 			   System.out.println("group_no  : " + group_no);
	 			   System.out.println("part_name : " + part_name);
				   System.out.println("pro_usage : " + pro_usage);
				   System.out.println("ket_cost : "  + ket_cost);
				   System.out.println("target_cost : "   + target_cost);
				   System.out.println("actual_cost_1 : " + actual_cost_1);
				   System.out.println("earn_rate_1 : "   + earn_rate_1);
				   System.out.println("actual_cost_2 : " + actual_cost_2);
				   System.out.println("earn_rate_2 : "   + earn_rate_2);
				   System.out.println("actual_cost_3 : " + actual_cost_3);
				   System.out.println("earn_rate_3 : "   + earn_rate_3);*/

				   createData.put("group_no", group_no );
				   if("report_create_temp".equals(cmd) || "report_create_temp_electric".equals(cmd)){
					   createData.put("pk_crp", pk_crp );
				   }else if("modify_create_temp".equals(cmd) || "modify_create_temp_electric".equals(cmd)){
					   createData.put("pk_crp", modify_report_pk );
				   }
				   createData.put("rev_no", rev_no );
				   createData.put("pk_wid", pk_wid );

			       createData.put("team", team);
			       createData.put("f_name", f_name);
			       createData.put("a_name", a_name);
			       createData.put("customer_f", customer_f);
			       createData.put("pjt_name", pjt_name);
			       createData.put("pjt_no", pjt_no);
			       createData.put("car_type", car_type);
			       createData.put("app_part", app_part);
			       createData.put("request_txt", request_txt);
			       createData.put("report_dest", report_dest);

			       createData.put("case_to_note_1", case_to_note_1);
			       createData.put("case_to_note_2", case_to_note_2);
			       createData.put("case_to_note_3", case_to_note_3);

			       createData.put("mold_count", mold_count);
			       createData.put("press_count", press_count);
			       createData.put("line_count", line_count);
			       createData.put("pack_count", pack_count);
			       createData.put("repack_count", repack_count);

			       createData.put("mold_total_1", mold_total_1);
			       createData.put("mold_total_2", mold_total_2);
			       createData.put("mold_total_3", mold_total_3);

			       createData.put("press_total_1", press_total_1);
			       createData.put("press_total_2", press_total_2);
			       createData.put("press_total_3", press_total_3);

			       createData.put("line_total_1", line_total_1);
			       createData.put("line_total_2", line_total_2);
			       createData.put("line_total_3", line_total_3);

			       createData.put("pack_total_1", pack_total_1);
			       createData.put("pack_total_2", pack_total_2);
			       createData.put("pack_total_3", pack_total_3);

			       createData.put("repack_total_1", repack_total_1);
			       createData.put("repack_total_2", repack_total_2);
			       createData.put("repack_total_3", repack_total_3);

			       createData.put("m_depr_stan", m_depr_stan);
			       createData.put("p_depr_stan", p_depr_stan);
			       createData.put("l_depr_stan", l_depr_stan);
			       createData.put("pack_depr_stan", pack_depr_stan);
			       createData.put("repack_depr_stan", repack_depr_stan);

			       createData.put("tocost_year", tocost_year);

			       createData.put("su_year_1", su_year_1);
			       createData.put("su_year_2", su_year_2);
			       createData.put("su_year_3", su_year_3);
			       createData.put("su_year_4", su_year_4);
			       createData.put("su_year_5", su_year_5);
			       createData.put("su_year_6", su_year_6);
			       createData.put("su_year_7", su_year_7);
			       createData.put("su_year_8", su_year_8);
			       createData.put("su_stan_day", su_stan_day);

			       createData.put("total_sales_1", total_sales_1);
			       createData.put("total_sales_2", total_sales_2);
			       createData.put("total_sales_3", total_sales_3);
			       createData.put("total_sales_4", total_sales_4);
			       createData.put("total_sales_5", total_sales_5);
			       createData.put("total_sales_6", total_sales_6);
			       createData.put("total_sales_7", total_sales_7);
			       createData.put("total_sales_8", total_sales_8);

			       createData.put("profit_1", profit_1);
			       createData.put("profit_2", profit_2);
			       createData.put("profit_3", profit_3);
			       createData.put("profit_4", profit_4);
			       createData.put("profit_5", profit_5);
			       createData.put("profit_6", profit_6);
			       createData.put("profit_7", profit_7);
			       createData.put("profit_8", profit_8);

			       createData.put("per_profit_1", per_profit_1);
			       createData.put("per_profit_2", per_profit_2);
			       createData.put("per_profit_3", per_profit_3);
			       createData.put("per_profit_4", per_profit_4);
			       createData.put("per_profit_5", per_profit_5);
			       createData.put("per_profit_6", per_profit_6);
			       createData.put("per_profit_7", per_profit_7);
			       createData.put("per_profit_8", per_profit_8);
			       createData.put("pack_type", pack_type);
			       createData.put("pro_1", pro_1);
			       createData.put("eff_value", eff_value);
			       createData.put("group_no", group_no);
			       createData.put("part_name", part_name);
			       createData.put("pro_usage", pro_usage);
			       createData.put("ket_cost", ket_cost);
			       createData.put("target_cost", target_cost);
			       createData.put("actual_cost_1", actual_cost_1);
			       createData.put("earn_rate_1", earn_rate_1);
			       createData.put("actual_cost_2", actual_cost_2);
			       createData.put("earn_rate_2", earn_rate_2);
			       createData.put("actual_cost_3", actual_cost_3);
			       createData.put("earn_rate_3", earn_rate_3);
			       createData.put("earn_rate_sum_1", earn_rate_sum_1);
			       createData.put("earn_rate_sum_2", earn_rate_sum_2);
			       createData.put("earn_rate_sum_3", earn_rate_sum_3);
			       createData.put("actual_cost_sum_1", actual_cost_sum_1);
			       createData.put("actual_cost_sum_2", actual_cost_sum_2);
			       createData.put("actual_cost_sum_3", actual_cost_sum_3);
			       createData.put("note_1", note_1);
			       createData.put("note_2", note_2);
			       createData.put("note_3", note_3);
			       createData.put("note_4", note_4);
			       createData.put("select_cost", select_cost);
			       createData.put("case_1_note", case_1_note);
			       createData.put("case_2_note", case_2_note);
			       createData.put("case_3_note", case_3_note);
			       createData.put("lme_cu", lme_cu);
			       createData.put("u_ex_rate", u_ex_rate);
			       createData.put("pk_pid", pk_pid);
			       createData.put("w_name", w_name);
			       createData.put("dev_step", dev_step);
			       createData.put("table_row", table_row);
			       createData.put("dr_step", dr_step);
			       createData.put("division", division);
			       createData.put("cmd", cmd);


				   createDataList.add(createData);
				   if("modify_create_temp".equals(cmd) || "modify_create_temp_electric".equals(cmd)){
					   codeDao.ModifyRerpotTemp(createDataList);
				   }else if("report_create_temp".equals(cmd) || "report_create_temp_electric".equals(cmd)){
					   codeDao.InsertRerpotTemp(createDataList);
				   }
				   createDataList.clear();
	 		   }

	 		   if("modify_create_temp".equals(cmd) || "modify_create_temp_electric".equals(cmd)){
	 			  pk_crp = map.getString("report_pk");
	 		   }
	 		   if(!"report_create_temp_electric".equals(cmd) && !"modify_create_temp_electric".equals(cmd)){
	 			   codeDao.UpdateReportSum(pk_crp); //총원가, 수익율 sum 하여 update
	 		   }
	 	       conn.commit();
	 	    }
        }catch(Exception e){
        	try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	e.printStackTrace();
        }finally{
        	DBUtil.close(conn);
        }
        return pk_crp;
	}

	/* 산출작업 DB 조회 */

    private void WorkSearch(HttpServletRequest req, HttpServletResponse res, String t_chk_list){

        //connection
        Connection conn = null;

        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao codeDao = new CostReportDao(conn);

            // result
            ArrayList SearchList = codeDao.getWorkList(t_chk_list);
            Hashtable SearchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                SearchMap = (Hashtable)SearchList.get(i);

            }
            // result setting
            req.setAttribute("SearchList", SearchList);

            //return url
            gotoResult(req, res, "/codebase/costreport/cost_report_1.jsp");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //throw e;
        }
    }

    /* 보고서 DB 조회 */

    private void ReportSearch(HttpServletRequest req, HttpServletResponse res){

        //connection
        Connection conn = null;

        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao codeDao = new CostReportDao(conn);

            // result
            ArrayList SearchList = codeDao.getReportList();
            Hashtable SearchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                SearchMap = (Hashtable)SearchList.get(i);

            }
            // result setting
            req.setAttribute("SearchList", SearchList);

            //return url

            gotoResult(req, res, "/codebase/costreport/cost_report_1.jsp");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //throw e;
        }
    }
    /* 보고서 DB 조회-usage 변경버튼 클릭시(기존 보고서 DB有) */

    private void UsChange(HttpServletRequest req, HttpServletResponse res){
        String[] pro_usage = req.getParameterValues("pro_usage");
        //connection
        Connection conn = null;

        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao codeDao = new CostReportDao(conn);

            // result
            ArrayList SearchList = codeDao.getUsChange(pro_usage);
            Hashtable SearchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                SearchMap = (Hashtable)SearchList.get(i);

            }
            // result setting
            req.setAttribute("SearchList", SearchList);

            //return url

            gotoResult(req, res, "/codebase/costreport/cost_report_1.jsp");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //throw e;
        }
    }

    private void ReportApprListSearch(HttpServletRequest req, HttpServletResponse res){

        //connection
        Connection conn = null;
        String gubun  		 = StringUtil.checkNull(req.getParameter("gubun"));
        String position 		 = StringUtil.checkNull(req.getParameter("position"));
        String ProductName = StringUtil.checkNull(req.getParameter("ProductName"));
        String ProjectNo      = StringUtil.checkNull(req.getParameter("ProjectNo"));
        String kyul_person   = StringUtil.checkNull(req.getParameter("kyul_person"));
        String kyul_date1     = StringUtil.checkNull(req.getParameter("kyul_date1"));
        String kyul_date2     = StringUtil.checkNull(req.getParameter("kyul_date2"));
        String kyul_line        = StringUtil.checkNull(req.getParameter("kyul_line"));

        System.out.println("gubun : "+gubun);
        System.out.println("position : "+position);
        System.out.println("ProductName : "+ProductName);
        System.out.println("ProjectNo : "+ProjectNo);
        System.out.println("kyul_person : "+kyul_person);
        System.out.println("kyul_date1 : "+kyul_date1);
        System.out.println("kyul_date2 : "+kyul_date2);
        System.out.println("kyul_line : "+kyul_line);
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao codeDao = new CostReportDao(conn);

            // result
            ArrayList SearchList = codeDao.getReportApprList(gubun,position,ProductName,ProjectNo,kyul_date1,kyul_date2,kyul_line);
            Hashtable SearchMap = null;
            // GridData에 데이터를 셋팅합니다.
           /* for(int i = 0; i < SearchList.size() ; i++)
            {
                SearchMap = (Hashtable)SearchList.get(i);

            }*/
            // result setting
            req.setAttribute("SearchList", SearchList);

            //return url
            gotoResult(req, res, "/jsp/cost/costreport/appr_list.jsp?gubun="+gubun+"&position="+position+"&ProductName="+ProductName+"&ProjectNo="+ProjectNo+"&kyul_person="+kyul_person+"&kyul_date1="+kyul_date1+"&kyul_date2="+kyul_date2+"&kyul_line="+kyul_line);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //throw e;
        }
    }

    /**
     *  서블릿 호출후 호출결과를 Redirect 사용하는 Method
     */
    protected void gotoResult(HttpServletRequest req, HttpServletResponse res, String address)throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
        dispatcher.forward(req,res);
        return;
    }

    /*protected void alertNgo(HttpServletResponse res, String msg, String url, String oid, String prePage) {
        try {
            res.setContentType("text/html;charset=KSC5601");
            PrintWriter out = res.getWriter();
              String rtn_msg = "";
              rtn_msg = "\n <form name='frmProc' method='post' target='contName' action='" + url + "'>"
                + "\n <input type='hidden' name='cmd' value='View'>"
                + "\n <input type='hidden' name='oid' value='" + oid + "'>"
                + "\n <input type='hidden' name='prePage' value='" + prePage + "'>"
                + "\n </form>"
                + "\n <script language=\"javascript\">"
            //	+ "\n   parent.hideProcessing();"
            //	+ "\n   parent.enabledAllBtn();"
                + "\n   alert(\"" + msg + "\");"
                + "\n   document.frmProc.submit();"
                + "\n </script>";
              out.println(rtn_msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    protected void alertNgo(HttpServletResponse res, String msg, String url, String oid) {
        try {
            res.setContentType("text/html;charset=KSC5601");
            PrintWriter out = res.getWriter();
              String rtn_msg = "";
              rtn_msg = "\n <form name='frmProc' method='post' target='contName' action='" + url + "'>"
                + "\n <input type='hidden' name='cmd' value='View'>"
                + "\n <input type='hidden' name='oid' value='" + oid + "'>"
                + "\n </form>"
                + "\n <script language=\"javascript\">"
            //    + "\n   parent.hideProcessing();"
            //    + "\n   parent.enabledAllBtn();"
                + "\n   alert(\"" + msg + "\");"
                + "\n   document.frmProc.submit();"
                + "\n </script>";
              out.println(rtn_msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}