package e3ps.cost.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import wt.fc.QueryResult;
import wt.part.WTPart;
import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;
import e3ps.common.util.CommonUtil;
import e3ps.cost.dao.CostComDao;
import e3ps.cost.entity.PartBomInfoDTO;
import e3ps.cost.service.StandardKetCostInfoService;
import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.MoldItemInfo;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.beans.ProductHelper;
import e3ps.project.beans.ProductProjectHelper;
import e3ps.project.beans.ProjectHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.code.service.CodeHelper;

public class CostComServlet extends HttpServlet {

	String data_type_txt = "";

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String wise_gb = StringUtil.getParameter(req, "wise_gb");
		System.out.println("BABISS::::::::::::::::::::::::111 ");

		if (wise_gb.equals("No")) {// 와이즈 그리드 모듈 여부
			String cmd = StringUtil.getParameter(req, "cmd");

			if (cmd.equals("plmSearch")) {
				plmSearch(req, res);
			}
			if (cmd.equals("popStore")) {
				popStoreSearch(req, res);
			}

			if (cmd.equals("popGrade")) {
				popGradeSearch(req, res);
			}

			if (cmd.equals("popFile")) {
				popFile(req, res);
			}

		} else {

			System.out.println("BABISS::::::::::::::::::::::::222 ");
			GridData gdReq = null;
			GridData gdRes = null;

			// Encode Type을 UTF-8로 변환한다.
			req.setCharacterEncoding("utf-8");
			res.setContentType("text/html; charset=utf-8");

			PrintWriter out = res.getWriter();

			try {

				// WISEGRID_DATA 라는 Param으로 WiseGrid의 전문이 올라온다.
				String rawData = req.getParameter("WISEGRID_DATA");

				// 올라온 전문을 Parsing하여 자료구조 형태로 반환해준다.
				gdReq = OperateGridData.parse(rawData);

				// 전달받은 파라미터값을 가져온다.
				String mode = gdReq.getParam("mode");

				System.out.println("BABISS::::::::::::::::::::::::222 " + mode);

				if (mode.equals("cuttingSearch")) {
					gdRes = CuttingSelectQry(gdReq);
				} else if (mode.equals("SaveCutting")) {
					gdRes = SaveCutting(gdReq);
				} else if (mode.equals("platingSearch")) {
					gdRes = PlatingSelectQry(gdReq);
				} else if (mode.equals("SavePlating")) {
					gdRes = SavePlating(gdReq);
				} else if (mode.equals("processingSearch")) {
					gdRes = ProcessingSelectQry(gdReq);
				} else if (mode.equals("SaveProcessing")) {
					gdRes = SaveProcessing(gdReq);
				} else if (mode.equals("cost_re_add_save")) {
					SaveCostAdd(gdReq);
					rawData = req.getParameter("COST_RE_ADD_GRID");
					gdReq = OperateGridData.parse(rawData);
					gdRes = SaveCostAdd2(gdReq);
				} else if (mode.equals("cost_re_edit")) {
					gdRes = costeditSearch(gdReq);
				} else if (mode.equals("cost_re_edit2")) {
					gdRes = costeditSearch2(gdReq);
				} else if (mode.equals("cost_re_edit3")) {
					gdRes = costeditSearch3(gdReq);
				} else if (mode.equals("cost_re_edit_case1")) {
					gdRes = SaveCostCase1(gdReq);

					rawData = req.getParameter("cost_re_edit_case2");
					gdReq = OperateGridData.parse(rawData);
					gdRes = SaveCostCase2(gdReq);

					rawData = req.getParameter("cost_re_edit_case3");
					gdReq = OperateGridData.parse(rawData);
					gdRes = SaveCostCase3(gdReq);

				} else if (mode.equals("case_del")) {
					gdRes = deleteCase(gdReq);
				} else if (mode.equals("cost_re_edit_save")) {
					gdRes = SaveCostRe_edit(gdReq);

					rawData = req.getParameter("cost_re_edit_save2");
					gdReq = OperateGridData.parse(rawData);
					gdRes = SaveCostCase2(gdReq);
				} else if (mode.equals("cost_re_viewSearch1")) {
					gdRes = Re_view_Search1(gdReq);
				} else if (mode.equals("cost_re_viewSearch2")) {
					gdRes = Re_view_Search2(gdReq);
				} else if (mode.equals("cost_re_viewSearch3")) {
					gdRes = Re_view_Search3(gdReq);
				} else if (mode.equals("cost_re_view_delete")) {
					gdRes = Re_view_delete(gdReq);
				} else if (mode.equals("getPlm_cost_viewSearch1")) {
					gdRes = Plm_view_Search_1(gdReq);
				} else if (mode.equals("getPlm_cost_viewSearch2")) {
					gdRes = Plm_view_Search_2(gdReq);
				} else if (mode.equals("getPlm_cost_viewSearch3")) {
					gdRes = Plm_view_Search_3(gdReq);
				} else if (mode.equals("cost_packSearch")) {
					gdRes = cost_packSearch(gdReq);
				} else if (mode.equals("cost_pack_save")) {
					gdRes = cost_pack_save(gdReq);
				}

			} catch (Exception e) {
				gdRes = new GridData();
				gdRes.setMessage("Error: " + e.getMessage());
				gdRes.setStatus("false");
				e.printStackTrace();
			}

			finally {
				try {
					// WiseGrid에 전달할 데이터가 담긴 GridData 객체와 미리 생성해 놓은 PrintWriter 객체를 넘겨
					// 데이터를 Parsing 한 후 전송합니다.
					OperateGridData.write(gdRes, out);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public GridData cost_pack_save(GridData gdReq) throws Exception {

		System.out.println("Babiss ::::::::::::::::::save22");

		// TODO Auto-generated method stub
		GridData gdRes = new GridData();
		String request_id = gdReq.getParam("request_id");
		int rowCount = 0;

		try {
			// 화면에서 전달받은 "CRUD"의 row 수를 가져온다.
			rowCount = Integer.parseInt(gdReq.getParam("table_row"));
			ArrayList createDataList = new ArrayList(rowCount);
			ArrayList updateDataList = new ArrayList(rowCount);
			ArrayList deleteDataList = new ArrayList(rowCount);

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			Hashtable<String, String> updateData = null;
			updateData = new Hashtable<String, String>();

			Hashtable<String, String> deleteData = null;
			deleteData = new Hashtable<String, String>();

			int cnt = 0;

			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			// 데이터 셋팅
			for (int i = 0; i < rowCount; i++) {

				String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

				if (crud.equals("C")) {

					createData.put("request_id", request_id);
					createData.put("pack_item", gdReq.getHeader("pack_item").getComboHiddenValues()[gdReq.getHeader("pack_item").getSelectedIndex(i)]);
					createData.put("pack_unitcost", gdReq.getHeader("pack_unitcost").getValue(i));
					createData.put("pack_quantity", gdReq.getHeader("pack_quantity").getValue(i));
					createData.put("in_pack", gdReq.getHeader("in_pack").getValue(i));
					createData.put("out_pack", gdReq.getHeader("out_pack").getValue(i));
					createData.put("pack_recovery", gdReq.getHeader("pack_recovery").getComboHiddenValues()[gdReq.getHeader("pack_recovery").getSelectedIndex(i)]);
					createData.put("pack_amount", gdReq.getHeader("pack_amount").getValue(i));
					createData.put("pack_inputcost_using",
							gdReq.getHeader("pack_inputcost_using").getComboHiddenValues()[gdReq.getHeader("pack_inputcost_using").getSelectedIndex(i)]);
					createData.put("pack_pro_quantity", gdReq.getHeader("pack_pro_quantity").getValue(i));
					createData.put("pack_inputcost", gdReq.getHeader("pack_inputcost").getValue(i));
					createData.put("pack_unitcost_total", gdReq.getHeader("pack_unitcost_total").getValue(i));

					createDataList.add(createData);
					cnt = codeDao.cost_pack_save(createDataList);
					createDataList.clear();

				} else if (crud.equals("U")) {

					System.out.println("BABISS::::::::::::::::::::::::7777 ");

					updateData.put("cost_pack_id", gdReq.getHeader("cost_pack_id").getValue(i));
					updateData.put("request_id", request_id);
					updateData.put("pack_item", gdReq.getHeader("pack_item").getComboHiddenValues()[gdReq.getHeader("pack_item").getSelectedIndex(i)]);
					updateData.put("pack_unitcost", gdReq.getHeader("pack_unitcost").getValue(i));
					updateData.put("pack_quantity", gdReq.getHeader("pack_quantity").getValue(i));
					updateData.put("in_pack", gdReq.getHeader("in_pack").getValue(i));
					updateData.put("out_pack", gdReq.getHeader("out_pack").getValue(i));
					updateData.put("pack_recovery", gdReq.getHeader("pack_recovery").getComboHiddenValues()[gdReq.getHeader("pack_recovery").getSelectedIndex(i)]);
					updateData.put("pack_amount", gdReq.getHeader("pack_amount").getValue(i));
					updateData.put("pack_inputcost_using",
							gdReq.getHeader("pack_inputcost_using").getComboHiddenValues()[gdReq.getHeader("pack_inputcost_using").getSelectedIndex(i)]);
					updateData.put("pack_pro_quantity", gdReq.getHeader("pack_pro_quantity").getValue(i));
					updateData.put("pack_inputcost", gdReq.getHeader("pack_inputcost").getValue(i));
					updateData.put("pack_unitcost_total", gdReq.getHeader("pack_unitcost_total").getValue(i));

					updateDataList.add(updateData);
					cnt = codeDao.cost_pack_update(updateDataList);
					updateDataList.clear();

				} else if (crud.equals("D")) {

					deleteData.put("cost_pack_id", gdReq.getHeader("cost_pack_id").getValue(i));
					deleteDataList.add(deleteData);
					cnt = codeDao.cost_pack_Delete(deleteDataList);

				}

			}
			if (conn != null) {
				conn.close();
			}

			gdRes.addParam("mode", "cost_pack_save");
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("저장.");
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public GridData cost_packSearch(GridData gdReq) throws Exception {
		// 포장비
		System.out.println("BABISS::::::::::::::::::::::::333 ");

		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String request_id = gdReq.getParam("request_id");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.getPackList(request_id);
			Hashtable SearchMap = null;
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				SearchMap = (Hashtable) SearchList.get(i);

				// String request_id = StringUtil.checkNull((String) SearchMap.get("request_id"));
				// String g_sel1 = StringUtil.checkNull((String) SearchMap.get("g_sel1"));
				// String g_sel2 = StringUtil.checkNull((String) SearchMap.get("g_sel2"));
				String cost_pack_id = StringUtil.checkNull((String) SearchMap.get("cost_pack_id"));
				String pack_item = StringUtil.checkNull((String) SearchMap.get("pack_item"));
				String pack_unitcost = StringUtil.checkNull((String) SearchMap.get("pack_unitcost"));
				String pack_quantity = StringUtil.checkNull((String) SearchMap.get("pack_quantity"));
				String pack_recovery = StringUtil.checkNull((String) SearchMap.get("pack_recovery"));
				String in_pack = StringUtil.checkNull((String) SearchMap.get("in_pack"));
				String out_pack = StringUtil.checkNull((String) SearchMap.get("out_pack"));
				String pack_amount = StringUtil.checkNull((String) SearchMap.get("pack_amount"));
				String pack_inputcost_using = StringUtil.checkNull((String) SearchMap.get("pack_inputcost_using"));
				String pack_pro_quantity = StringUtil.checkNull((String) SearchMap.get("pack_pro_quantity"));
				String pack_inputcost = StringUtil.checkNull((String) SearchMap.get("pack_inputcost"));
				String pack_unitcost_total = StringUtil.checkNull((String) SearchMap.get("pack_unitcost_total"));

				// gdRes.getHeader("g_sel1").addValue("", "", g_sel1);
				// gdRes.getHeader("g_sel2").addValue("", "", g_sel2);
				gdRes.getHeader("SELECTED").addValue("0", "");
				gdRes.getHeader("CRUD").addValue("", "");
				gdRes.getHeader("cost_pack_id").addValue(cost_pack_id, "");
				gdRes.getHeader("pack_item").addSelectedHiddenValue(pack_item);
				gdRes.getHeader("pack_unitcost").addValue(pack_unitcost, "");
				gdRes.getHeader("pack_quantity").addValue(pack_quantity, "");
				gdRes.getHeader("in_pack").addValue(in_pack, "");
				gdRes.getHeader("out_pack").addValue(out_pack, "");
				gdRes.getHeader("pack_recovery").addSelectedHiddenValue(pack_recovery);
				gdRes.getHeader("pack_amount").addValue(pack_amount, "");
				gdRes.getHeader("pack_inputcost_using").addSelectedHiddenValue(pack_inputcost_using);
				gdRes.getHeader("pack_pro_quantity").addValue(pack_pro_quantity, "");
				gdRes.getHeader("pack_inputcost").addValue(pack_inputcost, "");
				gdRes.getHeader("pack_unitcost_total").addValue(pack_unitcost_total, "");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		// 메세지와 상태값을 셋팅합니다.
		System.out.println("BABISS::::::::::::::::::::::::444 ");
		gdRes.addParam("mode", "cost_packSearch");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	public GridData CuttingSelectQry(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String cmd = gdReq.getParam("cmd");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.getCuttingList();
			Hashtable SearchMap = null;
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				SearchMap = (Hashtable) SearchList.get(i);
				String PK_CUT = (String) SearchMap.get("PK_CUT");

				String MET_TYPE = (String) SearchMap.get("MET_TYPE");
				String MET_W = (String) SearchMap.get("MET_W");
				String C_SIZE_T_1 = (String) SearchMap.get("C_SIZE_T_1");
				String C_SIZE_T_2 = (String) SearchMap.get("C_SIZE_T_2");
				String C_SIZE_T_3 = (String) SearchMap.get("C_SIZE_T_3");
				String C_SIZE_T_4 = (String) SearchMap.get("C_SIZE_T_4");
				String C_SIZE_T_5 = (String) SearchMap.get("C_SIZE_T_5");
				String C_SIZE_T_6 = (String) SearchMap.get("C_SIZE_T_6");
				String C_SIZE_T_7 = (String) SearchMap.get("C_SIZE_T_7");
				String C_SIZE_T_8 = (String) SearchMap.get("C_SIZE_T_8");
				String C_SIZE_T_9 = (String) SearchMap.get("C_SIZE_T_9");
				// gdRes.getHeader("CHECK").addValue("0", "");
				gdRes.getHeader("CRUD").addValue("", "");
				gdRes.getHeader("pk_cut").addValue(PK_CUT, "");
				gdRes.getHeader("met_type").addValue(MET_TYPE, " ");
				gdRes.getHeader("met_w").addValue(MET_W, " ");
				gdRes.getHeader("c_size_t_1").addValue(C_SIZE_T_1, " ");
				gdRes.getHeader("c_size_t_2").addValue(C_SIZE_T_2, " ");
				gdRes.getHeader("c_size_t_3").addValue(C_SIZE_T_3, " ");
				gdRes.getHeader("c_size_t_4").addValue(C_SIZE_T_4, " ");
				gdRes.getHeader("c_size_t_5").addValue(C_SIZE_T_5, " ");
				gdRes.getHeader("c_size_t_6").addValue(C_SIZE_T_6, " ");
				gdRes.getHeader("c_size_t_7").addValue(C_SIZE_T_7, " ");
				gdRes.getHeader("c_size_t_8").addValue(C_SIZE_T_8, " ");
				gdRes.getHeader("c_size_t_9").addValue(C_SIZE_T_9, " ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "CuttingSearch");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	/* 저장 */
	public GridData SaveCutting(GridData gdReq) throws Exception {

		GridData gdRes = new GridData();

		int rowCount = 0;

		try {
			// 화면에서 전달받은 "CRUD"의 row 수를 가져온다.
			rowCount = gdReq.getHeader("CRUD").getRowCount();
			ArrayList createDataList = new ArrayList(rowCount);
			ArrayList updateDataList = new ArrayList(rowCount);
			ArrayList deleteDataList = new ArrayList(rowCount);

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			Hashtable<String, String> updateData = null;
			updateData = new Hashtable<String, String>();

			Hashtable<String, String> deleteData = null;
			deleteData = new Hashtable<String, String>();

			int cnt = 0;
			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			// 데이터 셋팅
			for (int i = 0; i < rowCount; i++) {

				// 화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
				String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

				if (crud.equals("C")) {
					createData.put("met_type", gdReq.getHeader("met_type").getValue(i));
					createData.put("met_w", gdReq.getHeader("met_w").getValue(i));
					createData.put("c_size_t_1", gdReq.getHeader("c_size_t_1").getValue(i));
					createData.put("c_size_t_2", gdReq.getHeader("c_size_t_2").getValue(i));
					createData.put("c_size_t_3", gdReq.getHeader("c_size_t_3").getValue(i));
					createData.put("c_size_t_4", gdReq.getHeader("c_size_t_4").getValue(i));
					createData.put("c_size_t_5", gdReq.getHeader("c_size_t_5").getValue(i));
					createData.put("c_size_t_6", gdReq.getHeader("c_size_t_6").getValue(i));
					createData.put("c_size_t_7", gdReq.getHeader("c_size_t_7").getValue(i));
					createData.put("c_size_t_8", gdReq.getHeader("c_size_t_8").getValue(i));
					createData.put("c_size_t_9", gdReq.getHeader("c_size_t_9").getValue(i));
					createDataList.add(createData);
					cnt = codeDao.CuttingCreate(createDataList);
				} else if (crud.equals("U")) {
					updateData.put("pk_cut", gdReq.getHeader("pk_cut").getValue(i));
					updateData.put("met_type", gdReq.getHeader("met_type").getValue(i));
					updateData.put("met_w", gdReq.getHeader("met_w").getValue(i));
					updateData.put("c_size_t_1", gdReq.getHeader("c_size_t_1").getValue(i));
					updateData.put("c_size_t_2", gdReq.getHeader("c_size_t_2").getValue(i));
					updateData.put("c_size_t_3", gdReq.getHeader("c_size_t_3").getValue(i));
					updateData.put("c_size_t_4", gdReq.getHeader("c_size_t_4").getValue(i));
					updateData.put("c_size_t_5", gdReq.getHeader("c_size_t_5").getValue(i));
					updateData.put("c_size_t_6", gdReq.getHeader("c_size_t_6").getValue(i));
					updateData.put("c_size_t_7", gdReq.getHeader("c_size_t_7").getValue(i));
					updateData.put("c_size_t_8", gdReq.getHeader("c_size_t_8").getValue(i));
					updateData.put("c_size_t_9", gdReq.getHeader("c_size_t_9").getValue(i));
					updateDataList.add(updateData);
					cnt = codeDao.CuttingUpdate(updateDataList);

				} else if (crud.equals("D")) {
					deleteData.put("pk_cut", gdReq.getHeader("pk_cut").getValue(i));
					deleteDataList.add(deleteData);
					cnt = codeDao.CuttingDelete(deleteDataList);
				}
			}

			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");
			// returnData += getSendData(updateDataList, "U");
			// returnData += getSendData(deleteDataList, "D");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "SaveCutting");
			gdRes.addParam("saveData", "aa");
			String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
			gdRes.setMessage(msg);
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public GridData PlatingSelectQry(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.getPlatingList();
			Hashtable searchMap = null;
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				searchMap = (Hashtable) SearchList.get(i);
				String PK_PLC = (String) searchMap.get("PK_PLC");

				String MET_TYPE = (String) searchMap.get("MET_TYPE");

				String P_SIZE_T_1 = (String) searchMap.get("P_SIZE_T_1");
				String P_SIZE_T_2 = (String) searchMap.get("P_SIZE_T_2");
				String P_SIZE_T_3 = (String) searchMap.get("P_SIZE_T_3");
				String P_SIZE_T_4 = (String) searchMap.get("P_SIZE_T_4");
				// gdRes.getHeader("CHECK").addValue("0", "");
				gdRes.getHeader("CRUD").addValue("", "");
				gdRes.getHeader("pk_plc").addValue(PK_PLC, "");
				gdRes.getHeader("met_type").addValue(MET_TYPE, " ");
				gdRes.getHeader("p_size_t_1").addValue(P_SIZE_T_1, " ");
				gdRes.getHeader("p_size_t_2").addValue(P_SIZE_T_2, " ");
				gdRes.getHeader("p_size_t_3").addValue(P_SIZE_T_3, " ");
				gdRes.getHeader("p_size_t_4").addValue(P_SIZE_T_4, " ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		// 메세지와 상태값을 셋팅합니다.
		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	/* 저장 */
	public GridData SavePlating(GridData gdReq) throws Exception {

		GridData gdRes = new GridData();

		int rowCount = 0;

		try {
			// 화면에서 전달받은 "CRUD"의 row 수를 가져온다.
			rowCount = gdReq.getHeader("CRUD").getRowCount();
			ArrayList createDataList = new ArrayList(rowCount);
			ArrayList updateDataList = new ArrayList(rowCount);
			ArrayList deleteDataList = new ArrayList(rowCount);

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			Hashtable<String, String> updateData = null;
			updateData = new Hashtable<String, String>();

			Hashtable<String, String> deleteData = null;
			deleteData = new Hashtable<String, String>();

			int cnt = 0;
			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			// 데이터 셋팅
			for (int i = 0; i < rowCount; i++) {

				// 화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
				String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

				if (crud.equals("C")) {
					createData.put("pk_plc", gdReq.getHeader("pk_plc").getValue(i));
					createData.put("met_type", gdReq.getHeader("met_type").getValue(i));
					createData.put("p_size_t_1", gdReq.getHeader("p_size_t_1").getValue(i));
					createData.put("p_size_t_2", gdReq.getHeader("p_size_t_2").getValue(i));
					createData.put("p_size_t_3", gdReq.getHeader("p_size_t_3").getValue(i));
					createData.put("p_size_t_4", gdReq.getHeader("p_size_t_4").getValue(i));
					createDataList.add(createData);
					cnt = codeDao.platingCreate(createDataList);
				} else if (crud.equals("U")) {
					createData.put("pk_plc", gdReq.getHeader("pk_plc").getValue(i));
					createData.put("met_type", gdReq.getHeader("met_type").getValue(i));
					createData.put("p_size_t_1", gdReq.getHeader("p_size_t_1").getValue(i));
					createData.put("p_size_t_2", gdReq.getHeader("p_size_t_2").getValue(i));
					createData.put("p_size_t_3", gdReq.getHeader("p_size_t_3").getValue(i));
					createData.put("p_size_t_4", gdReq.getHeader("p_size_t_4").getValue(i));
					updateDataList.add(updateData);
					cnt = codeDao.PlatingUpdate(updateDataList);

				} else if (crud.equals("D")) {
					deleteData.put("pk_plc", gdReq.getHeader("pk_plc").getValue(i));
					deleteDataList.add(deleteData);
					cnt = codeDao.PlatingDelete(deleteDataList);
				}
			}

			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");
			// returnData += getSendData(updateDataList, "U");
			// returnData += getSendData(deleteDataList, "D");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "save");
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public GridData ProcessingSelectQry(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.getProcessingList();
			Hashtable searchMap = null;
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				searchMap = (Hashtable) SearchList.get(i);
				String PK_PC = (String) searchMap.get("PK_PC");
				String PC_COST_TYPE = (String) searchMap.get("PC_COST_TYPE");
				String MET_TYPE = (String) searchMap.get("MET_TYPE");
				PC_COST_TYPE = new String(PC_COST_TYPE.getBytes("utf-8"), "8859_1");
				String PRO_COST = (String) searchMap.get("PRO_COST");
				// gdRes.getHeader("CHECK").addValue("0", "");
				gdRes.getHeader("CRUD").addValue("", "");
				gdRes.getHeader("pk_pc").addValue(PK_PC, "");
				gdRes.getHeader("pc_cost_type").addValue(PC_COST_TYPE, "");
				gdRes.getHeader("met_type").addValue(MET_TYPE, "");
				gdRes.getHeader("pro_cost").addValue(PRO_COST, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		// 메세지와 상태값을 셋팅합니다.
		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	/* 저장 */
	public GridData SaveProcessing(GridData gdReq) throws Exception {

		GridData gdRes = new GridData();

		int rowCount = 0;

		try {
			// 화면에서 전달받은 "CRUD"의 row 수를 가져온다.
			rowCount = gdReq.getHeader("CRUD").getRowCount();
			ArrayList createDataList = new ArrayList(rowCount);
			ArrayList updateDataList = new ArrayList(rowCount);
			ArrayList deleteDataList = new ArrayList(rowCount);

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			Hashtable<String, String> updateData = null;
			updateData = new Hashtable<String, String>();

			Hashtable<String, String> deleteData = null;
			deleteData = new Hashtable<String, String>();

			int cnt = 0;
			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			// 데이터 셋팅
			for (int i = 0; i < rowCount; i++) {

				// 화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
				String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

				if (crud.equals("C")) {
					createData.put("pk_plc", gdReq.getHeader("pk_pc").getValue(i));
					createData.put("pc_cost_type", gdReq.getHeader("pc_cost_type").getValue(i));
					createData.put("met_type", gdReq.getHeader("met_type").getValue(i));
					createData.put("pro_cost", gdReq.getHeader("pro_cost").getValue(i));
					createDataList.add(createData);
					cnt = codeDao.processingCreate(createDataList);
				} else if (crud.equals("U")) {
					createData.put("pk_plc", gdReq.getHeader("pk_pc").getValue(i));
					createData.put("pc_cost_type", gdReq.getHeader("pc_cost_type").getValue(i));
					createData.put("met_type", gdReq.getHeader("met_type").getValue(i));
					createData.put("pro_cost", gdReq.getHeader("pro_cost").getValue(i));
					updateDataList.add(updateData);
					cnt = codeDao.ProcessingUpdate(updateDataList);

				} else if (crud.equals("D")) {
					deleteData.put("pk_pc", gdReq.getHeader("pk_pc").getValue(i));
					deleteDataList.add(deleteData);
					cnt = codeDao.ProcessingDelete(deleteDataList);
				}
			}

			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");
			// returnData += getSendData(updateDataList, "U");
			// returnData += getSendData(deleteDataList, "D");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "save");
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	private String getSendData(ArrayList sendData, String CRUDFlag) {

		StringBuffer sbData = new StringBuffer();

		for (int i = 0; i < sendData.size(); i++) {
			String[] rowData = (String[]) sendData.get(i);

			for (int k = 0; k < rowData.length; k++) {
				sbData.append("[" + rowData[k] + "]");
			}
			sbData.append("\n");

		}

		if (CRUDFlag.equals("C"))
			sbData.append(sendData.size() + " 건의 데이터가 등록되었습니다.\n\n");
		else if (CRUDFlag.equals("U"))
			sbData.append(sendData.size() + " 건의 데이터가 수정되었습니다.\n\n");
		else if (CRUDFlag.equals("D"))
			sbData.append(sendData.size() + " 건의 데이터가 삭제되었습니다.\n\n");

		return sbData.toString();
	}

	public void plmSearch(HttpServletRequest req, HttpServletResponse res) {
		// connection
		Connection conn = null;

		String pjt_no = req.getParameter("pjt_no");
		try {
			// connection creation
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			ArrayList CheckList = codeDao.costInputCheck(pjt_no);
			Hashtable CheckHash = null;
			CheckHash = (Hashtable) CheckList.get(0);

			boolean IsOldimpossible = true;
			boolean IsNotPlmInterface = true;

			String NotProgerss = "";
			String GetPlm = "";
			String progrssCnt = (String) CheckHash.get("PROGRESS_CNT"); // 진행중인 원가 데이터가 있으면 신규요청 불가
			String completeCnt = (String) CheckHash.get("COMPLETE_CNT"); // 결재완료된 기존 원가 데이터가 있는지

			if (Integer.parseInt(progrssCnt) > 0) {
				IsOldimpossible = false;
				NotProgerss = "ok";
			}

			if (Integer.parseInt(completeCnt) < 1) {
				IsNotPlmInterface = false;
			}

			// result
			ArrayList SearchList = null;
			String search_type = null;
			String plm_data = null;

			if (IsOldimpossible && !IsNotPlmInterface) {// 진행중인 원가요청이 없고 기결재완료된 원가요청도 없을때
				E3PSProject e3psproject = ProjectHelper.getProject(pjt_no); // 해당 제품프로젝트가 plm에 있다면 bom 구조를 가져올수있다..
				if (e3psproject != null && e3psproject instanceof ProductProject) {
					ProductProject pjt = (ProductProject) e3psproject;
					String pjtOid = CommonUtil.getOIDLongValue2Str(pjt);
					QueryResult qr = ProjectHelper.getProductInfo(pjtOid);
					ProductInfo pi = null;

					if (qr.size() > 0) {
						GetPlm = "ok";
					} else {
						GetPlm = "notOk";
					}
					String yield = "0";
					while (qr.hasMoreElements()) {
						Object o[] = (Object[]) qr.nextElement();
						pi = (ProductInfo) o[0];
						yield = ProductHelper.getYield(CommonUtil.getOIDLongValue2Str(pi));
						if (Integer.parseInt(StringUtil.checkNullZero(yield)) < 1) {
							GetPlm = "notOk";
						}
					}
				}
			}

			SearchList = codeDao.getCostI(pjt_no);
			Hashtable searchMap = null;
			searchMap = (Hashtable) SearchList.get(SearchList.size() - 1);
			search_type = (String) searchMap.get("search_type");
			plm_data = (String) searchMap.get("plm_data");
			SearchList.remove(SearchList.size() - 1);

			for (int i = 0; i < SearchList.size(); i++) {
				searchMap = (Hashtable) SearchList.get(i);
			}
			// result setting
			req.setAttribute("SearchList", SearchList);

			// return url
			gotoResult(req, res, "/jsp/cost/costdb/plmSearch.jsp?search_type=" + search_type + "&plm_data=" + plm_data + "&pjt_no=" + pjt_no + "&NotProgerss=" + NotProgerss
					+ "&GetPlm=" + GetPlm);

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		} finally {
			if (conn != null) {
				DBUtil.close(conn);
			}
		}
	}

	public void popStoreSearch(HttpServletRequest req, HttpServletResponse res) {
		// connection
		Connection conn = null;
		String page_name = StringUtil.checkNull(req.getParameter("page_name"));

		try {
			// connection creation
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			// result
			ArrayList SearchList = codeDao.getSearchStore();
			Hashtable searchMap = null;

			for (int i = 0; i < SearchList.size(); i++) {
				searchMap = (Hashtable) SearchList.get(i);

			}
			// result setting
			req.setAttribute("SearchStore", SearchList);

			// return url

			gotoResult(req, res, "/jsp/cost/costdb/distri_cost.jsp?Search_ok=no&page_name=" + page_name);

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}
	}

	public GridData SaveCostAdd(GridData gdReq) throws Exception {

		GridData gdRes = new GridData();

		int rowCount = 0;
		Connection conn = null;
		conn = DBUtil.getConnection(false);
		try {
			// 화면에서 전달받은 "CRUD"의 row 수를 가져온다.
			rowCount = gdReq.getHeader("CRUD").getRowCount();

			ArrayList createDataList = new ArrayList(rowCount);
			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			int cnt = 0;
			String pjt_name = null;
			String part_name = null;
			String part_note = null;
			
			CostComDao codeDao = new CostComDao(conn);
			String rev_no = codeDao.getMaxRevno(gdReq.getParam("pk_cr_group"));

			pjt_name = gdReq.getParam("pjt_name");
			part_note = gdReq.getParam("part_note");

			String case_count_1 = null;
			String case_count_2 = null;

			// 데이터 셋팅
			for (int i = 0; i < rowCount; i++) {

				// 화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
				// String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

				createData.put("pk_cr_group_1", gdReq.getParam("pk_cr_group"));
				createData.put("pjt_no", gdReq.getParam("pjt_no"));
				createData.put("rev_no", rev_no);

				createData.put("team", gdReq.getParam("team"));
				createData.put("f_name", gdReq.getParam("f_name"));
				createData.put("a_name", gdReq.getParam("a_name"));
				createData.put("dev_step", gdReq.getParam("dev_step"));
				createData.put("dr_step", gdReq.getParam("dr_step"));
				createData.put("sub_step", gdReq.getParam("sub_step"));
				createData.put("request_day", gdReq.getParam("request_day"));
				createData.put("request_txt", gdReq.getParam("request_txt"));
				createData.put("etc_note", gdReq.getParam("etc_note"));
				createData.put("table_row", gdReq.getParam("table_row"));

				createData.put("pk_cr", gdReq.getHeader("pk_cr").getValue(i));
				createData.put("make", gdReq.getHeader("make").getValue(i));

				case_count_1 = gdReq.getHeader("case_count_1").getValue(i);
				if (case_count_1.equals("")) {
					case_count_1 = "0";
				}
				case_count_2 = gdReq.getHeader("case_count_2").getValue(i);
				if (case_count_2.equals("")) {
					case_count_2 = "0";
				}
				createData.put("case_count_1", case_count_1);
				createData.put("case_count_2", case_count_2);
				createData.put("pk_cr_group", gdReq.getHeader("pk_cr_group").getValue(i));
				createData.put("group_no", gdReq.getHeader("group_no").getValue(i));

				if (gdReq.getHeader("pro_type").getSelectedIndex(i) >= 0) {
					createData.put("pro_type", gdReq.getHeader("pro_type").getComboHiddenValues()[gdReq.getHeader("pro_type").getSelectedIndex(i)]);
				}

				part_name = gdReq.getHeader("part_name").getValue(i);
				if (gdReq.getHeader("part_type").getSelectedIndex(i) >= 0) {
					createData.put("part_type", gdReq.getHeader("part_type").getComboHiddenValues()[gdReq.getHeader("part_type").getSelectedIndex(i)]);
				}
				if (gdReq.getHeader("mix_group").getSelectedIndex(i) >= 0) {
					createData.put("mix_group", gdReq.getHeader("mix_group").getComboHiddenValues()[gdReq.getHeader("mix_group").getSelectedIndex(i)]);
				}
				createData.put("part_no", gdReq.getHeader("part_no").getValue(i));
				createData.put("net_1", gdReq.getHeader("net_1").getValue(i));
				createData.put("net_2", gdReq.getHeader("net_2").getValue(i));
				createData.put("net_3", gdReq.getHeader("net_3").getValue(i));
				createData.put("usage", gdReq.getHeader("usage").getValue(i));
				createData.put("meterial", gdReq.getHeader("meterial").getValue(i));
				createData.put("t_size", gdReq.getHeader("t_size").getValue(i));
				createData.put("w_size", gdReq.getHeader("w_size").getValue(i));
				createData.put("p_size", gdReq.getHeader("p_size").getValue(i));
				createData.put("plating", gdReq.getHeader("plating").getValue(i));
				createData.put("m_maker", gdReq.getHeader("m_maker").getValue(i));
				createData.put("m_mone", gdReq.getHeader("m_mone").getValue(i));
				createData.put("unitcost", gdReq.getHeader("unitcost").getValue(i));
				createData.put("unitcost_txt", gdReq.getHeader("unitcost_txt").getValue(i));
				createData.put("grade_name", gdReq.getHeader("grade_name").getValue(i));
				createData.put("grade_color", gdReq.getHeader("grade_color").getValue(i));
				createData.put("order_con", gdReq.getHeader("order_con").getValue(i));
				createData.put("h_weight", gdReq.getHeader("h_weight").getValue(i));
				createData.put("h_scrap", gdReq.getHeader("h_scrap").getValue(i));
				createData.put("t_weight", gdReq.getHeader("t_weight").getValue(i));
				createData.put("recycle", gdReq.getHeader("recycle").getValue(i));
				createData.put("die_no", gdReq.getHeader("die_no").getValue(i));
				createData.put("cavity", gdReq.getHeader("cavity").getValue(i));
				createData.put("m_su", gdReq.getHeader("m_su").getValue(i));
				createData.put("mold_cost", gdReq.getHeader("mold_cost").getValue(i));
				if (gdReq.getHeader("mold_c_type").getSelectedIndex(i) >= 0) {
					createData.put("mold_c_type", gdReq.getHeader("mold_c_type").getComboHiddenValues()[gdReq.getHeader("mold_c_type").getSelectedIndex(i)]);
				}
				if (gdReq.getHeader("make_type").getSelectedIndex(i) >= 0) {
					createData.put("make_type", gdReq.getHeader("make_type").getComboHiddenValues()[gdReq.getHeader("make_type").getSelectedIndex(i)]);
				}
				if (gdReq.getHeader("pro_1").getSelectedIndex(i) >= 0) {
					createData.put("pro_1", gdReq.getHeader("pro_1").getComboHiddenValues()[gdReq.getHeader("pro_1").getSelectedIndex(i)]);
				}
				createData.put("ton", gdReq.getHeader("ton").getValue(i));
				createData.put("spm", gdReq.getHeader("spm").getValue(i));
				if (gdReq.getHeader("method_type").getSelectedIndex(i) >= 0) {
					createData.put("method_type", gdReq.getHeader("method_type").getComboHiddenValues()[gdReq.getHeader("method_type").getSelectedIndex(i)]);
				}
				if (gdReq.getHeader("pro_level").getSelectedIndex(i) >= 0) {
					createData.put("pro_level", gdReq.getHeader("pro_level").getComboHiddenValues()[gdReq.getHeader("pro_level").getSelectedIndex(i)]);
				}
				createData.put("pro_level_txt", gdReq.getHeader("pro_level_txt").getValue(i));
				createData.put("line_su", gdReq.getHeader("line_su").getValue(i));
				createData.put("sul_cost", gdReq.getHeader("sul_cost").getValue(i));
				if (gdReq.getHeader("plating_type").getSelectedIndex(i) >= 0) {
					createData.put("plating_type", gdReq.getHeader("plating_type").getComboHiddenValues()[gdReq.getHeader("plating_type").getSelectedIndex(i)]);
				}
				if (gdReq.getHeader("type_2").getSelectedIndex(i) >= 0) {
					createData.put("type_2", gdReq.getHeader("type_2").getComboHiddenValues()[gdReq.getHeader("type_2").getSelectedIndex(i)]);
				}
				createData.put("plating_cost", gdReq.getHeader("plating_cost").getValue(i));
				if (gdReq.getHeader("type_1").getSelectedIndex(i) >= 0) {
					createData.put("type_1", gdReq.getHeader("type_1").getComboHiddenValues()[gdReq.getHeader("type_1").getSelectedIndex(i)]);
				}
				if (gdReq.getHeader("t_mone").getSelectedIndex(i) >= 0) {
					createData.put("t_mone", gdReq.getHeader("t_mone").getComboHiddenValues()[gdReq.getHeader("t_mone").getSelectedIndex(i)]);
				}
				createData.put("type_cost", gdReq.getHeader("type_cost").getValue(i));
				if (gdReq.getHeader("t_order").getSelectedIndex(i) >= 0) {
					createData.put("t_order", gdReq.getHeader("t_order").getComboHiddenValues()[gdReq.getHeader("t_order").getSelectedIndex(i)]);
				}
				createData.put("pack_type", gdReq.getHeader("pack_type").getValue(i));
				createData.put("in_pack", gdReq.getHeader("in_pack").getValue(i));
				createData.put("out_pack", gdReq.getHeader("out_pack").getValue(i));
				createData.put("store", gdReq.getHeader("store").getValue(i));
				createData.put("dest", gdReq.getHeader("dest").getValue(i));
				createData.put("dis_cost", gdReq.getHeader("dis_cost").getValue(i));
				createData.put("distri_cost", gdReq.getHeader("distri_cost").getValue(i));
				if (gdReq.getHeader("royalty").getSelectedIndex(i) >= 0) {
					createData.put("royalty", gdReq.getHeader("royalty").getComboHiddenValues()[gdReq.getHeader("royalty").getSelectedIndex(i)]);
				}
				createData.put("yazaki_ro", gdReq.getHeader("yazaki_ro").getValue(i));
				createData.put("etc_cost", gdReq.getHeader("etc_cost").getValue(i));
				createData.put("part_note", gdReq.getHeader("part_note").getValue(i));
				createData.put("pjt_name", StringUtil.checkNull(pjt_name).replaceAll("'", ""));
				createData.put("part_name", StringUtil.checkNull(part_name).replaceAll("'", ""));
				createData.put("part_note", StringUtil.checkNull(part_note).replaceAll("'", ""));
				createData.put("CRUD", gdReq.getHeader("CRUD").getHiddenValue(i));

				createDataList.add(createData);
				cnt = codeDao.costRequestCreate(createDataList);
				createDataList.clear();

			}
			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "save3");
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");
			conn.commit();

		} catch (Exception e) {
		    conn.rollback();
		    throw e;
		}finally{
		    DBUtil.close(conn);
		}

		return gdRes;
	}

	public GridData SaveCostAdd2(GridData gdReq) throws Exception {
		GridData gdRes = new GridData();

		int rowCount = 0;

		try {
			// 화면에서 전달받은 "CRUD"의 row 수를 가져온다.
			rowCount = Integer.parseInt(gdReq.getParam("table_row"));
			ArrayList createDataList = new ArrayList(rowCount);

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			int cnt = 0;

			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			// 데이터 셋팅
			for (int i = 0; i < rowCount; i++) {
				createData.put("pk_cr_group_1", gdReq.getParam("pk_cr_group"));
				createData.put("pjt_no", gdReq.getParam("pjt_no"));
				createData.put("rev_no", gdReq.getHeader("rev_no").getValue(i));
				createData.put("group_no", gdReq.getHeader("group_no").getValue(i));
				createData.put("su_year_1", gdReq.getHeader("su_year_1").getValue(i));
				createData.put("su_year_2", gdReq.getHeader("su_year_2").getValue(i));
				createData.put("su_year_3", gdReq.getHeader("su_year_3").getValue(i));
				createData.put("su_year_4", gdReq.getHeader("su_year_4").getValue(i));
				createData.put("su_year_5", gdReq.getHeader("su_year_5").getValue(i));
				createData.put("su_year_6", gdReq.getHeader("su_year_6").getValue(i));
				createData.put("su_year_7", gdReq.getHeader("su_year_7").getValue(i));
				createData.put("su_year_8", gdReq.getHeader("su_year_8").getValue(i));
				createData.put("client_cost", gdReq.getHeader("client_cost").getValue(i));
				createData.put("ket_cost", gdReq.getHeader("ket_cost").getValue(i));
				createData.put("customer_F", gdReq.getHeader("customer_F").getValue(i));
				createData.put("customer_L", gdReq.getHeader("customer_L").getValue(i));
				createData.put("car_type", gdReq.getHeader("car_type").getValue(i));
				createData.put("partSqe", gdReq.getHeader("partSqe").getValue(i));
				createData.put("rev_pk", gdReq.getHeader("rev_pk").getValue(i));
				createData.put("target_cost", gdReq.getHeader("target_cost").getValue(i));

				createDataList.add(createData);
				cnt = codeDao.costRequestCreate2(createDataList);
				createDataList.clear();
			}
			if (conn != null) {
				conn.close();
			}
			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "save3");
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public void popFile(HttpServletRequest req, HttpServletResponse res) {
		Connection conn = null;

		String page_name = StringUtil.getParameter(req, "page_name");
		String pk_cr_group = StringUtil.getParameter(req, "pk_cr_group");
		String file_type = StringUtil.getParameter(req, "file_type");
		String report_pk = StringUtil.getParameter(req, "report_pk");
		String file_name_2 = StringUtil.getParameter(req, "file_name_2");
		/*
		 * try { file_name_2 = java.net.URLDecoder.decode(req.getParameter("file_2_name"), "utf-8"); } catch (UnsupportedEncodingException e1) { e1.printStackTrace(); }
		 */
		System.out.println("file_name_2 : " + file_name_2);
		String file_name_3 = StringUtil.getParameter(req, "file_name_3");
		String file_name_4 = StringUtil.getParameter(req, "file_name_4");
		String file_name_5 = StringUtil.getParameter(req, "file_name_5");
		String file_name_6 = StringUtil.getParameter(req, "file_name_6");

		try {
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			ArrayList SearchList = codeDao.getSearchFile(pk_cr_group, page_name, report_pk);
			req.setAttribute("SearchFile", SearchList);
			gotoResult(req, res, "/jsp/cost/common/file_add.jsp?page_name=" + page_name + "&wise_gb=No" + "&pk_cr_group=" + pk_cr_group + "&search_gb=no" + "&file_type="
					+ file_type + "&report_pk=" + report_pk + "&file_name_2=" + file_name_2 + "&file_name_3=" + file_name_3 + "&file_name_4=" + file_name_4 + "&file_name_5="
					+ file_name_5 + "&file_name_6=" + file_name_6);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void popGradeSearch(HttpServletRequest req, HttpServletResponse res) {
		// connection
		Connection conn = null;

		String page_name = StringUtil.getParameter(req, "param_page_name");
		String search_first = StringUtil.getParameter(req, "param_search_first");

		String param_m_maker = StringUtil.getParameter(req, "param_m_maker");
		String param_meterial = StringUtil.getParameter(req, "param_meterial");
		String make = StringUtil.getParameter(req, "make");
		String plating = StringUtil.getParameter(req, "param_plating");
		String t_size = StringUtil.getParameter(req, "param_t_size");
		String w_size = StringUtil.getParameter(req, "param_w_size");
		String p_size = StringUtil.getParameter(req, "param_p_size");
		String m_mone = StringUtil.getParameter(req, "param_m_mone");
		String unitcost_txt = StringUtil.getParameter(req, "param_unitcost_txt");
		String order_con = StringUtil.getParameter(req, "param_order_con");
		String h_weight = StringUtil.getParameter(req, "param_h_weight");
		String h_scrap = StringUtil.getParameter(req, "param_h_scrap");
		String t_weight = StringUtil.getParameter(req, "param_t_weight");
		String recycle = StringUtil.getParameter(req, "param_recycle");
		String grade_color = StringUtil.getParameter(req, "param_grade_color");
		String p_pro_type = StringUtil.getParameter(req, "param_p_pro_type");
		String sul_cost = StringUtil.getParameter(req, "param_sul_cost");
		String type_2 = StringUtil.getParameter(req, "param_type_2");
		String plating_cost = StringUtil.getParameter(req, "param_plating_cost");
		String k = StringUtil.getParameter(req, "param_k");
		String param_grade_name = StringUtil.getParameter(req, "param_grade_name");

		String m_maker = StringUtil.getParameter(req, "m_maker");
		String meterial = StringUtil.getParameter(req, "meterial");

		String maker_2 = StringUtil.getParameter(req, "maker_2");

		if (StringUtil.checkNull(m_maker).equals("")) {
			m_maker = param_m_maker;
		}

		if (StringUtil.checkNull(maker_2).equals("")) {
			maker_2 = param_m_maker;
		}

		String material_name = StringUtil.getParameter(req, "material_name");
		String param_material_name = StringUtil.getParameter(req, "param_material_name");
		if (StringUtil.checkNull(material_name).equals("")) {
			material_name = param_material_name;
		}

		String grade_name = StringUtil.getParameter(req, "grade_name");

		if (StringUtil.checkNull(grade_name).equals("")) {
			grade_name = param_grade_name;
		}

		try {
			// connection creation
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			// result
			ArrayList SearchList = codeDao.getSearchGrade(m_maker, meterial);// 비철

			// result setting
			req.setAttribute("SearchPress", SearchList);

			ArrayList SearchList2 = codeDao.getSearchGrade2(); // 벤도리아핀

			req.setAttribute("SearchBandolier", SearchList2);

			ArrayList SearchList3 = codeDao.getSearchGrade3();// maker_name

			req.setAttribute("SearchMaker_2", SearchList3);

			ArrayList SearchList4 = codeDao.getSearchGrade4(maker_2);// material_name

			req.setAttribute("SearchMaterial_name", SearchList4);

			ArrayList SearchList5 = codeDao.getSearchGrade5(maker_2, material_name);// grade_name

			req.setAttribute("SearchGrade_name", SearchList5);

			ArrayList SearchList6 = codeDao.getSearchGrade6(grade_name);// grade_name

			req.setAttribute("SearchGrade_color", SearchList6);

			ArrayList SearchList7 = codeDao.getSearchGrade7();// grade_name1

			req.setAttribute("SearchMaker_name", SearchList7);

			ArrayList SearchList8 = codeDao.getSearchGrade8(m_maker);// grade_name2

			req.setAttribute("SearchGrade_name2", SearchList8);

			ArrayList SearchList9 = codeDao.getSearchGrade9();// grade_name3

			req.setAttribute("SearchGrade_name3", SearchList9);

			ArrayList SearchList10 = codeDao.getSearchGrade10();// grade_name4

			req.setAttribute("SearchGrade_name4", SearchList10);

			if (conn != null) {
				conn.close();
			}

			if (search_first.equals("first")) {
				String url = "/jsp/cost/common/cost_grade.jsp?Search_ok=no&k=" + k + "&page_name=" + page_name + "&meterial=" + param_meterial + "&make=" + make + "&m_maker="
						+ param_m_maker + "&plating=" + plating + "&t_size=" + t_size + "&w_size=" + w_size + "&p_size=" + p_size + "&m_mone=" + m_mone + "&unitcost_txt="
						+ unitcost_txt + "&order_con=" + order_con + "&h_weight=" + h_weight + "&h_scrap=" + h_scrap + "&t_weight=" + t_weight + "&recycle=" + recycle
						+ "&grade_color=" + grade_color + "&p_pro_type=" + p_pro_type + "&sul_cost=" + sul_cost + "&type_2=" + type_2 + "&plating_cost=" + plating_cost
						+ "&search_first=" + search_first + "&grade_name=" + param_grade_name;
				gotoResult(req, res, url);
			} else {
				gotoResult(req, res, "/jsp/cost/common/cost_grade.jsp?Search_ok=no&k=" + k + "&page_name=" + page_name);
			}

			// gotoResult(req, res, "/jsp/cost/common/cost_grade.jsp?Search_ok=no&k="+k);

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}
	}

	public GridData costeditSearch(GridData gdReq) throws Exception {

		System.out.println("YOON111111111111111111111111 ");

		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
			// String etc_note = gdReq.getParam("etc_note");
			String rev_no = gdReq.getParam("rev_no");
			String data_type = gdReq.getParam("data_type");
			String group_case_count = gdReq.getParam("group_case_count");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.getCostEditList(pk_cr_group_1, rev_no);
			Hashtable SearchMap = null;
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				SearchMap = (Hashtable) SearchList.get(i);
				String pk_cr_group = (String) SearchMap.get("pk_cr_group");

				String pjt_no = StringUtil.checkNull((String) SearchMap.get("pjt_no"));
				String pjt_name = StringUtil.checkNull((String) SearchMap.get("pjt_name"));
				String team = StringUtil.checkNull((String) SearchMap.get("team"));
				String f_name = StringUtil.checkNull((String) SearchMap.get("f_name"));
				String a_name = StringUtil.checkNull((String) SearchMap.get("a_name"));
				String dev_step = StringUtil.checkNull((String) SearchMap.get("dev_step"));
				String dr_step = StringUtil.checkNull((String) SearchMap.get("dr_step"));
				String sub_step = StringUtil.checkNull((String) SearchMap.get("sub_step"));
				String Leader_day = StringUtil.checkNull((String) SearchMap.get("Leader_day"));
				String request_day = StringUtil.checkNull((String) SearchMap.get("request_day"));
				String request_txt = StringUtil.checkNull((String) SearchMap.get("request_txt"));
				String etc_note = StringUtil.checkNull((String) SearchMap.get("etc_note"));
				String file_1 = StringUtil.checkNull((String) SearchMap.get("file_1"));
				String file_2 = StringUtil.checkNull((String) SearchMap.get("file_2"));
				String file_3 = StringUtil.checkNull((String) SearchMap.get("file_3"));

				System.out.println("YOON : " + rev_no);
				gdRes.getHeader("pk_cr_group").addValue(pk_cr_group, "");
				gdRes.getHeader("pjt_no").addValue(pjt_no, "");
				gdRes.getHeader("pjt_name").addValue(pjt_name, "");
				gdRes.getHeader("team").addSelectedHiddenValue(team);
				gdRes.getHeader("f_name").addValue(f_name, "");
				gdRes.getHeader("a_name").addValue(a_name, "");
				gdRes.getHeader("dev_step").addSelectedHiddenValue(dev_step);
				gdRes.getHeader("dr_step").addValue(dr_step, "");
				gdRes.getHeader("sub_step").addSelectedHiddenValue(sub_step);
				gdRes.getHeader("rev_no").addValue(rev_no, "");
				gdRes.getHeader("Leader_day").addValue(Leader_day, "");
				gdRes.getHeader("request_day").addValue(request_day, "");
				gdRes.getHeader("request_txt").addValue(request_txt, "");
				gdRes.getHeader("etc_note").addValue(etc_note, "");
				gdRes.getHeader("file_1").addValue(file_1, "");
				gdRes.getHeader("file_2").addValue(file_2, "");
				gdRes.getHeader("file_3").addValue(file_3, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// if (conn != null) {
		// conn.close();
		// }
		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	public GridData costeditSearch2(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
			// String etc_note = gdReq.getParam("etc_note");
			String rev_no = gdReq.getParam("rev_no");
			String data_type = gdReq.getParam("data_type");
			String group_case_count = gdReq.getParam("group_case_count");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.getCostEditList2(pk_cr_group_1, data_type, rev_no);
			Hashtable SearchMap = null;
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				SearchMap = (Hashtable) SearchList.get(i);
				String pk_cr = StringUtil.checkNull((String) SearchMap.get("pk_cr"));
				String pk_cr_group = StringUtil.checkNull((String) SearchMap.get("pk_cr_group"));
				String partSqe = StringUtil.checkNull((String) SearchMap.get("partSqe"));
				String group_no = StringUtil.checkNull((String) SearchMap.get("group_no"));
				String part_name = StringUtil.checkNull((String) SearchMap.get("part_name"));
				String part_no = StringUtil.checkNull((String) SearchMap.get("part_no"));
				String car_type = StringUtil.checkNull((String) SearchMap.get("car_type"));
				String su_year_1 = StringUtil.checkNull((String) SearchMap.get("su_year_1"));
				String su_year_2 = StringUtil.checkNull((String) SearchMap.get("su_year_2"));
				String su_year_3 = StringUtil.checkNull((String) SearchMap.get("su_year_3"));
				String su_year_4 = StringUtil.checkNull((String) SearchMap.get("su_year_4"));
				String su_year_5 = StringUtil.checkNull((String) SearchMap.get("su_year_5"));
				String su_year_6 = StringUtil.checkNull((String) SearchMap.get("su_year_6"));
				String su_year_7 = StringUtil.checkNull((String) SearchMap.get("su_year_7"));
				String su_year_8 = StringUtil.checkNull((String) SearchMap.get("su_year_8"));
				String customer_F = StringUtil.checkNull((String) SearchMap.get("customer_F"));
				String customer_L = StringUtil.checkNull((String) SearchMap.get("customer_L"));
				String client_cost = StringUtil.checkNull((String) SearchMap.get("client_cost"));
				String ket_cost = StringUtil.checkNull((String) SearchMap.get("ket_cost"));
				String target_cost = StringUtil.checkNull((String) SearchMap.get("target_cost"));
				String rev_pk = StringUtil.checkNull((String) SearchMap.get("rev_pk"));
				String p_rev_no = StringUtil.checkNull((String) SearchMap.get("rev_no"));

				gdRes.getHeader("SEQ_NO").addValue(Integer.toString(i), "");
				gdRes.getHeader("pk_cr").addValue(pk_cr, "");
				gdRes.getHeader("pk_cr_group").addValue(pk_cr_group, "");
				gdRes.getHeader("partSqe").addValue(partSqe, "");
				gdRes.getHeader("group_no").addValue(group_no, "");
				gdRes.getHeader("part_name").addValue(part_name, "");
				gdRes.getHeader("part_no").addValue(part_no, "");
				gdRes.getHeader("car_type").addValue(car_type, "");
				gdRes.getHeader("su_year_1").addValue(su_year_1, "");
				gdRes.getHeader("su_year_2").addValue(su_year_2, "");
				gdRes.getHeader("su_year_3").addValue(su_year_3, "");
				gdRes.getHeader("su_year_4").addValue(su_year_4, "");
				gdRes.getHeader("su_year_5").addValue(su_year_5, "");
				gdRes.getHeader("su_year_6").addValue(su_year_6, "");
				gdRes.getHeader("su_year_7").addValue(su_year_7, "");
				gdRes.getHeader("su_year_8").addValue(su_year_8, "");
				gdRes.getHeader("customer_F").addValue(customer_F, "");
				gdRes.getHeader("customer_L").addValue(customer_L, "");
				gdRes.getHeader("client_cost").addValue(client_cost, "");
				gdRes.getHeader("ket_cost").addValue(ket_cost, "");
				gdRes.getHeader("target_cost").addValue(target_cost, "");
				gdRes.getHeader("rev_pk").addValue(rev_pk, "");
				gdRes.getHeader("rev_no").addValue(p_rev_no, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// if(conn!=null) {conn.close();}
		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	public GridData costeditSearch3(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
			// String etc_note = gdReq.getParam("etc_note");
			String rev_no = gdReq.getParam("rev_no");
			String data_type = gdReq.getParam("data_type");
			String group_case_count = gdReq.getParam("group_case_count");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.getCostEditList3(pk_cr_group_1, data_type, rev_no);
			Hashtable SearchMap = null;
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				SearchMap = (Hashtable) SearchList.get(i);
				String case_count_1 = StringUtil.checkNull((String) SearchMap.get("case_count_1"));
				String case_count_2 = StringUtil.checkNull((String) SearchMap.get("case_count_2"));
				String pk_cr = StringUtil.checkNull((String) SearchMap.get("pk_cr"));
				String pk_cr_group = StringUtil.checkNull((String) SearchMap.get("pk_cr_group"));
				String make = StringUtil.checkNull((String) SearchMap.get("make"));
				String group_no = StringUtil.checkNull((String) SearchMap.get("group_no"));
				String pro_type = StringUtil.checkNull((String) SearchMap.get("pro_type"));
				String g_sel1 = StringUtil.checkNull((String) SearchMap.get("g_sel1"));
				String g_sel2 = StringUtil.checkNull((String) SearchMap.get("g_sel2"));
				String g_sel3 = StringUtil.checkNull((String) SearchMap.get("g_sel3"));
				String part_name = StringUtil.checkNull((String) SearchMap.get("part_name"));
				String part_type = StringUtil.checkNull((String) SearchMap.get("part_type"));
				String mix_group = StringUtil.checkNull((String) SearchMap.get("mix_group"));
				String part_no = StringUtil.checkNull((String) SearchMap.get("part_no"));
				String net_1 = StringUtil.checkNull((String) SearchMap.get("net_1"));
				String net_2 = StringUtil.checkNull((String) SearchMap.get("net_2"));
				String net_3 = StringUtil.checkNull((String) SearchMap.get("net_3"));
				String usage = StringUtil.checkNull((String) SearchMap.get("usage"));
				String meterial = StringUtil.checkNull((String) SearchMap.get("meterial"));
				String t_size = StringUtil.checkNull((String) SearchMap.get("t_size"));
				String w_size = StringUtil.checkNull((String) SearchMap.get("w_size"));
				String p_size = StringUtil.checkNull((String) SearchMap.get("p_size"));
				String plating = StringUtil.checkNull((String) SearchMap.get("plating"));
				String m_maker = StringUtil.checkNull((String) SearchMap.get("m_maker"));
				String m_mone = StringUtil.checkNull((String) SearchMap.get("m_mone"));
				String unitcost = StringUtil.checkNull((String) SearchMap.get("unitcost"));
				String unitcost_txt = StringUtil.checkNull((String) SearchMap.get("unitcost_txt"));
				String grade_name = StringUtil.checkNull((String) SearchMap.get("grade_name"));
				String grade_color = StringUtil.checkNull((String) SearchMap.get("grade_color"));
				String order_con = StringUtil.checkNull((String) SearchMap.get("order_con"));
				String h_weight = StringUtil.checkNull((String) SearchMap.get("h_weight"));
				String h_scrap = StringUtil.checkNull((String) SearchMap.get("h_scrap"));
				String t_weight = StringUtil.checkNull((String) SearchMap.get("t_weight"));
				String recycle = StringUtil.checkNull((String) SearchMap.get("recycle"));
				String die_no = StringUtil.checkNull((String) SearchMap.get("die_no"));
				String cavity = StringUtil.checkNull((String) SearchMap.get("cavity"));
				String m_su = StringUtil.checkNull((String) SearchMap.get("m_su"));
				String mold_cost = StringUtil.checkNull((String) SearchMap.get("mold_cost"));
				String mold_c_type = StringUtil.checkNull((String) SearchMap.get("mold_c_type"));
				String make_type = StringUtil.checkNull((String) SearchMap.get("make_type"));
				String pro_1 = StringUtil.checkNull((String) SearchMap.get("pro_1"));
				String ton = StringUtil.checkNull((String) SearchMap.get("ton"));
				String spm = StringUtil.checkNull((String) SearchMap.get("spm"));
				String method_type = StringUtil.checkNull((String) SearchMap.get("method_type"));
				String pro_level = StringUtil.checkNull((String) SearchMap.get("pro_level"));
				String pro_level_txt = StringUtil.checkNull((String) SearchMap.get("pro_level_txt"));
				String line_su = StringUtil.checkNull((String) SearchMap.get("line_su"));
				String sul_cost = StringUtil.checkNull((String) SearchMap.get("sul_cost"));
				String plating_type = StringUtil.checkNull((String) SearchMap.get("plating_type"));
				String type_2 = StringUtil.checkNull((String) SearchMap.get("type_2"));
				String plating_cost = StringUtil.checkNull((String) SearchMap.get("plating_cost"));
				String type_1 = StringUtil.checkNull((String) SearchMap.get("type_1"));
				String t_mone = StringUtil.checkNull((String) SearchMap.get("t_mone"));
				String type_cost = StringUtil.checkNull((String) SearchMap.get("type_cost"));
				String t_order = StringUtil.checkNull((String) SearchMap.get("t_order"));
				String pack_type = StringUtil.checkNull((String) SearchMap.get("pack_type"));
				String pack_cost = StringUtil.checkNull((String) SearchMap.get("pack_cost"));
				String in_pack = StringUtil.checkNull((String) SearchMap.get("in_pack"));
				String out_pack = StringUtil.checkNull((String) SearchMap.get("out_pack"));
				String store = StringUtil.checkNull((String) SearchMap.get("store"));
				String dest = StringUtil.checkNull((String) SearchMap.get("dest"));
				String dis_cost = StringUtil.checkNull((String) SearchMap.get("dis_cost"));
				String distri_cost = StringUtil.checkNull((String) SearchMap.get("distri_cost"));
				String royalty = StringUtil.checkNull((String) SearchMap.get("royalty"));
				String etc_cost = StringUtil.checkNull((String) SearchMap.get("etc_cost"));
				String yazaki_ro = StringUtil.checkNull((String) SearchMap.get("yazaki_ro"));
				String part_note = StringUtil.checkNull((String) SearchMap.get("part_note"));

				gdRes.getHeader("SELECTED").addValue("0", "");
				gdRes.getHeader("CRUD").addValue("", "");
				gdRes.getHeader("SEQ_NO").addValue(Integer.toString(i), "");
				gdRes.getHeader("case_count_1").addValue(case_count_1, "");
				gdRes.getHeader("case_count_2").addValue(case_count_2, "");
				gdRes.getHeader("pk_cr").addValue(pk_cr, "");
				gdRes.getHeader("pk_cr_group").addValue(pk_cr_group, "");
				gdRes.getHeader("make").addValue(make, "");
				gdRes.getHeader("group_no").addValue(group_no, "");
				gdRes.getHeader("pro_type").addSelectedHiddenValue(pro_type);
				/*
				 * String[] imagelist = {g_sel1}; String[] imagelist2 = {g_sel2}; String[] imagelist3 = {g_sel3}; gdRes.getHeader("g_sel1").setImageURLs(imagelist);
				 * gdRes.getHeader("g_sel2").setImageURLs(imagelist2); gdRes.getHeader("g_sel3").setImageURLs(imagelist3);
				 */

				gdRes.getHeader("g_sel1").addValue("", "", g_sel1);
				gdRes.getHeader("g_sel2").addValue("", "", g_sel2);
				gdRes.getHeader("g_sel3").addValue("", "", g_sel3);

				gdRes.getHeader("part_name").addValue(part_name, "");
				gdRes.getHeader("part_type").addSelectedHiddenValue(part_type);
				gdRes.getHeader("mix_group").addSelectedHiddenValue(mix_group);
				gdRes.getHeader("part_no").addValue(part_no, "");
				gdRes.getHeader("net_1").addValue(net_1, "");
				gdRes.getHeader("net_2").addValue(net_2, "");
				gdRes.getHeader("net_3").addValue(net_3, "");
				gdRes.getHeader("usage").addValue(usage, "");
				gdRes.getHeader("meterial").addValue(meterial, "");
				gdRes.getHeader("t_size").addValue(t_size, "");
				gdRes.getHeader("w_size").addValue(w_size, "");
				gdRes.getHeader("p_size").addValue(p_size, "");
				gdRes.getHeader("plating").addValue(plating, "");
				gdRes.getHeader("m_maker").addValue(m_maker, "");
				gdRes.getHeader("m_mone").addValue(m_mone, "");
				gdRes.getHeader("unitcost").addValue(unitcost, "");
				gdRes.getHeader("unitcost_txt").addValue(unitcost_txt, "");
				gdRes.getHeader("grade_name").addValue(grade_name, "");
				gdRes.getHeader("grade_color").addValue(grade_color, "");
				gdRes.getHeader("order_con").addValue(order_con, "");
				gdRes.getHeader("h_weight").addValue(h_weight, "");
				gdRes.getHeader("h_scrap").addValue(h_scrap, "");
				gdRes.getHeader("t_weight").addValue(t_weight, "");
				gdRes.getHeader("recycle").addValue(recycle, "");
				gdRes.getHeader("die_no").addValue(die_no, "");
				gdRes.getHeader("cavity").addValue(cavity, "");
				gdRes.getHeader("m_su").addValue(m_su, "");
				gdRes.getHeader("mold_cost").addValue(mold_cost, "");
				gdRes.getHeader("mold_c_type").addSelectedHiddenValue(mold_c_type);
				gdRes.getHeader("make_type").addSelectedHiddenValue(make_type);
				gdRes.getHeader("pro_1").addSelectedHiddenValue(pro_1);
				gdRes.getHeader("ton").addValue(ton, "");
				gdRes.getHeader("spm").addValue(spm, "");
				gdRes.getHeader("method_type").addSelectedHiddenValue(method_type);
				gdRes.getHeader("pro_level").addSelectedHiddenValue(pro_level);
				gdRes.getHeader("pro_level_txt").addValue(pro_level_txt, "");
				gdRes.getHeader("line_su").addValue(line_su, "");
				gdRes.getHeader("sul_cost").addValue(sul_cost, "");
				gdRes.getHeader("plating_type").addSelectedHiddenValue(plating_type);
				gdRes.getHeader("type_2").addSelectedHiddenValue(type_2);
				gdRes.getHeader("plating_cost").addValue(plating_cost, "");
				gdRes.getHeader("type_1").addSelectedHiddenValue(type_1);
				gdRes.getHeader("t_mone").addSelectedHiddenValue(t_mone);
				gdRes.getHeader("type_cost").addValue(type_cost, "");
				gdRes.getHeader("t_order").addSelectedHiddenValue(t_order);
				gdRes.getHeader("pack_type").addValue(pack_type, "");
				gdRes.getHeader("pack_cost").addValue(pack_cost, "");
				gdRes.getHeader("in_pack").addValue(in_pack, "");
				gdRes.getHeader("out_pack").addValue(out_pack, "");
				gdRes.getHeader("store").addValue(store, "");
				gdRes.getHeader("dest").addValue(dest, "");
				gdRes.getHeader("dis_cost").addValue(dis_cost, "");
				gdRes.getHeader("distri_cost").addValue(distri_cost, "");
				gdRes.getHeader("royalty").addSelectedHiddenValue(royalty);
				gdRes.getHeader("etc_cost").addValue(etc_cost, "");
				gdRes.getHeader("yazaki_ro").addValue(yazaki_ro, "");
				gdRes.getHeader("part_note").addValue(part_note, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// if(conn!=null) {conn.close();}
		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	public GridData SaveCostCase1(GridData gdReq) throws Exception {

		GridData gdRes = new GridData();

		int rowCount = 0;
		String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
		String etc_note = gdReq.getParam("etc_note");
		String rev_no = gdReq.getParam("rev_no");
		String data_type = gdReq.getParam("data_type");
		String group_case_count = gdReq.getParam("group_case_count");

		try {
			rowCount = Integer.parseInt(gdReq.getParam("table_row2"));
			ArrayList createDataList = new ArrayList(rowCount);

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();
			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			int data_type_txt_temp = Integer.parseInt(group_case_count) - 1;
			data_type_txt = "case" + Integer.toString(data_type_txt_temp);

			// 데이터 셋팅
			for (int i = 0; i < rowCount; i++) {

				createData.put("case_count_1", gdReq.getHeader("case_count_1").getValue(i));
				createData.put("case_count_2", gdReq.getHeader("case_count_2").getValue(i));
				createData.put("pk_cr", gdReq.getHeader("pk_cr").getValue(i));
				createData.put("pk_cr_group", pk_cr_group_1);
				createData.put("make", gdReq.getHeader("make").getValue(i));
				createData.put("group_no", gdReq.getHeader("group_no").getValue(i));
				createData.put("pro_type", gdReq.getHeader("pro_type").getComboHiddenValues()[gdReq.getHeader("pro_type").getSelectedIndex(i)]);
				createData.put("part_name", gdReq.getHeader("part_name").getValue(i));
				createData.put("part_type", gdReq.getHeader("part_type").getComboHiddenValues()[gdReq.getHeader("part_type").getSelectedIndex(i)]);
				createData.put("mix_group", gdReq.getHeader("mix_group").getComboHiddenValues()[gdReq.getHeader("mix_group").getSelectedIndex(i)]);
				createData.put("part_no", gdReq.getHeader("part_no").getValue(i));
				createData.put("net_1", gdReq.getHeader("net_1").getValue(i));
				createData.put("net_2", gdReq.getHeader("net_2").getValue(i));
				createData.put("net_3", gdReq.getHeader("net_3").getValue(i));
				createData.put("usage", gdReq.getHeader("usage").getValue(i));
				createData.put("meterial", gdReq.getHeader("meterial").getValue(i));
				createData.put("t_size", gdReq.getHeader("t_size").getValue(i));
				createData.put("w_size", gdReq.getHeader("w_size").getValue(i));
				createData.put("p_size", gdReq.getHeader("p_size").getValue(i));
				createData.put("plating", gdReq.getHeader("plating").getValue(i));
				createData.put("m_maker", gdReq.getHeader("m_maker").getValue(i));
				createData.put("m_mone", gdReq.getHeader("m_mone").getValue(i));
				createData.put("unitcost", gdReq.getHeader("unitcost").getValue(i));
				createData.put("unitcost_txt", gdReq.getHeader("unitcost_txt").getValue(i));
				createData.put("grade_name", gdReq.getHeader("grade_name").getValue(i));
				createData.put("grade_color", gdReq.getHeader("grade_color").getValue(i));
				createData.put("order_con", gdReq.getHeader("order_con").getValue(i));
				createData.put("h_weight", gdReq.getHeader("h_weight").getValue(i));
				createData.put("h_scrap", gdReq.getHeader("h_scrap").getValue(i));
				createData.put("t_weight", gdReq.getHeader("t_weight").getValue(i));
				createData.put("recycle", gdReq.getHeader("recycle").getValue(i));
				createData.put("die_no", gdReq.getHeader("die_no").getValue(i));
				createData.put("cavity", gdReq.getHeader("cavity").getValue(i));
				createData.put("m_su", gdReq.getHeader("m_su").getValue(i));
				createData.put("mold_cost", gdReq.getHeader("mold_cost").getValue(i));
				createData.put("mold_c_type", gdReq.getHeader("mold_c_type").getComboHiddenValues()[gdReq.getHeader("mold_c_type").getSelectedIndex(i)]);
				createData.put("make_type", gdReq.getHeader("make_type").getComboHiddenValues()[gdReq.getHeader("make_type").getSelectedIndex(i)]);
				createData.put("pro_1", gdReq.getHeader("pro_1").getComboHiddenValues()[gdReq.getHeader("pro_1").getSelectedIndex(i)]);
				createData.put("ton", gdReq.getHeader("ton").getValue(i));
				createData.put("spm", gdReq.getHeader("spm").getValue(i));
				createData.put("method_type", gdReq.getHeader("method_type").getComboHiddenValues()[gdReq.getHeader("method_type").getSelectedIndex(i)]);
				createData.put("pro_level", gdReq.getHeader("pro_level").getComboHiddenValues()[gdReq.getHeader("pro_level").getSelectedIndex(i)]);
				createData.put("pro_level_txt", gdReq.getHeader("pro_level_txt").getValue(i));
				createData.put("line_su", gdReq.getHeader("line_su").getValue(i));
				createData.put("sul_cost", gdReq.getHeader("sul_cost").getValue(i));
				createData.put("plating_type", gdReq.getHeader("plating_type").getComboHiddenValues()[gdReq.getHeader("plating_type").getSelectedIndex(i)]);
				createData.put("type_2", gdReq.getHeader("type_2").getComboHiddenValues()[gdReq.getHeader("type_2").getSelectedIndex(i)]);
				createData.put("plating_cost", gdReq.getHeader("plating_cost").getValue(i));
				createData.put("type_1", gdReq.getHeader("type_1").getComboHiddenValues()[gdReq.getHeader("type_1").getSelectedIndex(i)]);
				createData.put("t_mone", gdReq.getHeader("t_mone").getComboHiddenValues()[gdReq.getHeader("t_mone").getSelectedIndex(i)]);
				createData.put("type_cost", gdReq.getHeader("type_cost").getValue(i));
				createData.put("t_order", gdReq.getHeader("t_order").getComboHiddenValues()[gdReq.getHeader("t_order").getSelectedIndex(i)]);
				createData.put("pack_type", gdReq.getHeader("pack_type").getValue(i));
				createData.put("in_pack", gdReq.getHeader("in_pack").getValue(i));
				createData.put("out_pack", gdReq.getHeader("out_pack").getValue(i));
				createData.put("store", gdReq.getHeader("store").getValue(i));
				createData.put("dest", gdReq.getHeader("dest").getValue(i));
				createData.put("dis_cost", gdReq.getHeader("dis_cost").getValue(i));
				createData.put("distri_cost", gdReq.getHeader("distri_cost").getValue(i));
				createData.put("royalty", gdReq.getHeader("royalty").getComboHiddenValues()[gdReq.getHeader("royalty").getSelectedIndex(i)]);
				createData.put("yazaki_ro", gdReq.getHeader("yazaki_ro").getValue(i));
				createData.put("etc_cost", gdReq.getHeader("etc_cost").getValue(i));
				createData.put("part_note", gdReq.getHeader("part_note").getValue(i).replaceAll("'", ""));
				createData.put("part_name", gdReq.getHeader("part_name").getValue(i).replaceAll("'", ""));
				createData.put("rev_no", rev_no);
				createData.put("data_type_txt", data_type_txt);
				createData.put("group_case_count", group_case_count);

				createDataList.add(createData);
				codeDao.costRequestEditCreate(createDataList);
				createDataList.clear();

			}
			if (conn != null) {
				conn.close();
			}

			gdRes.addParam("mode", "save3");
			gdRes.addParam("data_type", data_type_txt);
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public GridData SaveCostCase2(GridData gdReq) throws Exception {
		GridData gdRes = new GridData();

		int rowCount = 0;
		String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
		String etc_note = gdReq.getParam("etc_note");
		String rev_no = gdReq.getParam("rev_no");
		String data_type = gdReq.getParam("data_type");
		String group_case_count = gdReq.getParam("group_case_count");

		String pjt_no = gdReq.getParam("pjt_no");
		String pjt_name = gdReq.getParam("pjt_name");
		String team = gdReq.getParam("team");
		String f_name = gdReq.getParam("f_name");
		String a_name = gdReq.getParam("a_name");
		String dev_step = gdReq.getParam("dev_step");
		String dr_step = gdReq.getParam("dr_step");
		String sub_step = gdReq.getParam("sub_step");
		String request_day = gdReq.getParam("request_day");
		String request_txt = gdReq.getParam("request_txt");

		System.out.println("pk_cr_group : " + pk_cr_group_1);
		System.out.println("pjt_no : " + pjt_no);
		System.out.println("pjt_name : " + pjt_name);
		System.out.println("team : " + team);
		System.out.println("f_name : " + f_name);
		System.out.println("a_name : " + a_name);
		System.out.println("dev_step : " + dev_step);
		System.out.println("dr_step : " + dr_step);
		System.out.println("request_day : " + request_day);
		System.out.println("request_txt : " + request_txt);

		try {
			// 화면에서 전달받은 "CRUD"의 row 수를 가져온다.
			if (!StringUtil.checkNull(gdReq.getParam("table_row")).equals("")) {
				rowCount = Integer.parseInt(gdReq.getParam("table_row"));
			}
			ArrayList createDataList = new ArrayList(rowCount);

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			int cnt = 0;

			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			// 데이터 셋팅
			for (int i = 0; i < rowCount; i++) {

				System.out.println("su_year_1 : " + gdReq.getHeader("su_year_1").getValue(i));
				createData.put("pk_cr", gdReq.getHeader("pk_cr").getValue(i));
				System.out.println("su_year_2 : " + gdReq.getHeader("su_year_1").getValue(i));
				createData.put("partSqe", gdReq.getHeader("partSqe").getValue(i));
				createData.put("group_no", gdReq.getHeader("group_no").getValue(i));
				createData.put("part_name", gdReq.getHeader("part_name").getValue(i));
				createData.put("part_no", gdReq.getHeader("part_no").getValue(i));
				createData.put("car_type", gdReq.getHeader("car_type").getValue(i));
				System.out.println("su_year_3 : " + gdReq.getHeader("su_year_1").getValue(i));
				createData.put("su_year_1", gdReq.getHeader("su_year_1").getValue(i));
				createData.put("su_year_2", gdReq.getHeader("su_year_2").getValue(i));
				createData.put("su_year_3", gdReq.getHeader("su_year_3").getValue(i));
				createData.put("su_year_4", gdReq.getHeader("su_year_4").getValue(i));
				createData.put("su_year_5", gdReq.getHeader("su_year_5").getValue(i));
				System.out.println("su_year_5 : " + gdReq.getHeader("su_year_1").getValue(i));
				createData.put("su_year_6", gdReq.getHeader("su_year_6").getValue(i));
				createData.put("su_year_7", gdReq.getHeader("su_year_7").getValue(i));
				createData.put("su_year_8", gdReq.getHeader("su_year_8").getValue(i));
				System.out.println("su_year_6: " + gdReq.getHeader("su_year_1").getValue(i));
				createData.put("customer_F", gdReq.getHeader("customer_F").getValue(i));
				createData.put("customer_L", gdReq.getHeader("customer_L").getValue(i));
				createData.put("client_cost", gdReq.getHeader("client_cost").getValue(i));
				createData.put("ket_cost", gdReq.getHeader("ket_cost").getValue(i));
				createData.put("target_cost", gdReq.getHeader("target_cost").getValue(i));
				createData.put("rev_pk", gdReq.getHeader("rev_pk").getValue(i));
				System.out.println("su_year_7 : " + gdReq.getHeader("su_year_1").getValue(i));
				createData.put("rev_no", rev_no);
				createData.put("rowCount", Integer.toString(rowCount));
				System.out.println("su_year_8 : " + rowCount);
				createData.put("data_type_txt", data_type_txt);
				createData.put("group_case_count", group_case_count);
				System.out.println("su_year_9 : " + rowCount);
				createData.put("pk_cr_group_1", pk_cr_group_1);
				System.out.println("su_year_10 : " + rowCount);
				createDataList.add(createData);
				cnt = codeDao.costRequestUpdate(createDataList);
				createDataList.clear();
			}
			createData.clear();
			createData = new Hashtable<String, String>();
			System.out.println("su_year_11 : " + rowCount);
			createData.put("rev_no", rev_no);
			createData.put("data_type", data_type);
			createData.put("pk_cr_group_1", pk_cr_group_1);
			System.out.println("su_year_12 : " + rowCount);
			createData.put("pjt_no", pjt_no);
			createData.put("pjt_name", pjt_name);
			createData.put("team", team);
			createData.put("f_name", f_name);
			createData.put("a_name", a_name);
			createData.put("dev_step", dev_step);
			createData.put("data_type_txt", data_type_txt);
			createData.put("group_case_count", group_case_count);
			System.out.println("su_year_13 : " + rowCount);
			if (!StringUtil.checkNull(dr_step).equals("")) {
				createData.put("dr_step", dr_step);
			}
			if (!StringUtil.checkNull(sub_step).equals("")) {
				createData.put("sub_step", sub_step);
			}
			if (!StringUtil.checkNull(request_day).equals("")) {
				createData.put("request_day", request_day);
			}
			if (!StringUtil.checkNull(request_txt).equals("")) {
				createData.put("request_txt", request_txt);
			}

			System.out.println("su_year_14: " + rowCount);
			createDataList.add(createData);
			cnt = codeDao.costRequestUpdate2(createDataList);

			if (conn != null) {
				conn.close();
			}
			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "save3");
			gdRes.addParam("data_type", data_type_txt);
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public GridData SaveCostCase3(GridData gdReq) throws Exception {
		GridData gdRes = new GridData();
		System.out.println("SaveCostCase3  1");
		String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
		String etc_note = gdReq.getParam("etc_note");
		String rev_no = gdReq.getParam("rev_no");
		String data_type = gdReq.getParam("data_type");
		String group_case_count = gdReq.getParam("group_case_count");

		String pjt_name = gdReq.getHeader("pjt_name").getValue(0);
		pjt_name = pjt_name.replaceAll("'", "");
		System.out.println("SaveCostCase3  2의 group_case_count = " + group_case_count);
		try {
			ArrayList createDataList = new ArrayList();

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			int cnt = 0;

			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			System.out.println("SaveCostCase3  3");
			// 데이터 셋팅
			createData.put("pjt_no", gdReq.getHeader("pjt_no").getValue(0));
			createData.put("pjt_name", pjt_name);
			try {
				createData.put("team", gdReq.getHeader("team").getComboHiddenValues()[gdReq.getHeader("team").getSelectedIndex(0)]);
			} catch (Exception e) {

			}
			createData.put("f_name", gdReq.getHeader("f_name").getValue(0));
			createData.put("a_name", gdReq.getHeader("a_name").getValue(0));
			try {
				createData.put("dev_step", gdReq.getHeader("dev_step").getComboHiddenValues()[gdReq.getHeader("dev_step").getSelectedIndex(0)]);
			} catch (Exception e) {

			}
			createData.put("dr_step", gdReq.getHeader("dr_step").getValue(0));
			try {
				createData.put("sub_step", gdReq.getHeader("sub_step").getComboHiddenValues()[gdReq.getHeader("sub_step").getSelectedIndex(0)]);
			} catch (Exception e) {

			}
			createData.put("request_txt", gdReq.getHeader("request_txt").getValue(0));
			createData.put("rev_no", rev_no);
			createData.put("data_type", data_type_txt);
			createData.put("group_case_count", group_case_count);
			createData.put("pk_cr_group_1", pk_cr_group_1);

			System.out.println("SaveCostCase3  4");
			createDataList.add(createData);
			cnt = codeDao.costRequestUpdate2(createDataList);
			createDataList.clear();
			System.out.println("SaveCostCase3  5");
			if (conn != null) {
				conn.close();
			}
			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "save3");
			gdRes.addParam("data_type", data_type_txt);
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");
			System.out.println("SaveCostCase3  6");
		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public GridData deleteCase(GridData gdReq) throws Exception {
		GridData gdRes = new GridData();
		int rowCount = 0;
		String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
		String etc_note = gdReq.getParam("etc_note");
		String rev_no = gdReq.getParam("rev_no");
		String data_type = gdReq.getParam("data_type");
		String group_case_count = gdReq.getParam("group_case_count");
		rowCount = Integer.parseInt(gdReq.getParam("table_row2"));

		int n_group_case_count = Integer.parseInt(group_case_count);
		int data_type_txt_temp = n_group_case_count - 2;
		data_type_txt = "case" + Integer.toString(data_type_txt_temp);

		if (n_group_case_count - 1 == 0) {
			data_type_txt = "main";
		}

		try {
			ArrayList createDataList = new ArrayList(rowCount);

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			int cnt = 0;

			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			for (int i = 0; i < rowCount; i++) {
				// 데이터 셋팅
				createData.put("pk_cr", gdReq.getHeader("pk_cr").getValue(i));

				createDataList.add(createData);
				cnt = codeDao.costCaseDelete(createDataList);
				createDataList.clear();
			}
			createData.put("group_case_count", group_case_count);
			createData.put("pk_cr_group_1", pk_cr_group_1);
			createData.put("rev_no", rev_no);
			createDataList.add(createData);

			codeDao.costRequestUpdate3(createDataList);

			if (conn != null) {
				conn.close();
			}
			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "del");
			gdRes.addParam("data_type", data_type_txt);
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public GridData SaveCostRe_edit(GridData gdReq) throws Exception {

		GridData gdRes = new GridData();

		int rowCount = 0;
		String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
		String etc_note = gdReq.getParam("etc_note");
		String rev_no = gdReq.getParam("rev_no");
		String data_type = gdReq.getParam("data_type");
		String group_case_count = gdReq.getParam("group_case_count");
		String request_day = gdReq.getParam("request_day");
		String table_row = gdReq.getParam("table_row");
		System.out.println("table_row ==> " + table_row);

		String group_no_case = "";
		String group_no = "";
		String group_no_case2 = "";

		int case_cnt1 = 0;
		int case_cnt2 = 0;
		int case_count_1 = 0;
		int case_count_2 = 0;

		try {
			// 화면에서 전달받은 "CRUD"의 row 수를 가져온다.
			rowCount = gdReq.getHeader("CRUD").getRowCount();
			System.out.println("rowCount : " + rowCount);

			ArrayList createDataList = new ArrayList(rowCount);
			ArrayList updateDataList = new ArrayList(rowCount);
			ArrayList deleteDataList = new ArrayList(rowCount);

			Hashtable<String, String> createData = null;
			createData = new Hashtable<String, String>();

			Hashtable<String, String> updateData = null;
			updateData = new Hashtable<String, String>();

			Hashtable<String, String> deleteData = null;
			deleteData = new Hashtable<String, String>();

			int cnt = 0;
			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			// 데이터 셋팅
			for (int i = 0; i < rowCount; i++) {

				// 화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
				String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

				String case_count_1_temp = gdReq.getHeader("case_count_1").getValue(i);
				String case_count_2_temp = gdReq.getHeader("case_count_2").getValue(i);
				String pk_cr = gdReq.getHeader("pk_cr").getValue(i);
				String pk_cr_group = gdReq.getHeader("pk_cr_group").getValue(i);
				String make = gdReq.getHeader("make").getValue(i);
				String temp_group_no = gdReq.getHeader("group_no").getValue(i);
				String pro_type = gdReq.getHeader("pro_type").getComboHiddenValues()[gdReq.getHeader("pro_type").getSelectedIndex(i)];
				String part_name = gdReq.getHeader("part_name").getValue(i);
				part_name = part_name.replaceAll("'", "");
				String part_type = gdReq.getHeader("part_type").getComboHiddenValues()[gdReq.getHeader("part_type").getSelectedIndex(i)];
				String mix_group = gdReq.getHeader("mix_group").getComboHiddenValues()[gdReq.getHeader("mix_group").getSelectedIndex(i)];
				String part_no = gdReq.getHeader("part_no").getValue(i);
				String net_1 = gdReq.getHeader("net_1").getValue(i);
				String net_2 = gdReq.getHeader("net_2").getValue(i);
				String net_3 = gdReq.getHeader("net_3").getValue(i);
				String usage = gdReq.getHeader("usage").getValue(i);
				String meterial = gdReq.getHeader("meterial").getValue(i);
				String t_size = gdReq.getHeader("t_size").getValue(i);
				String w_size = gdReq.getHeader("w_size").getValue(i);
				String p_size = gdReq.getHeader("p_size").getValue(i);
				String plating = gdReq.getHeader("plating").getValue(i);
				String m_maker = gdReq.getHeader("m_maker").getValue(i);
				String m_mone = gdReq.getHeader("m_mone").getValue(i);
				String unitcost = gdReq.getHeader("unitcost").getValue(i);
				String unitcost_txt = gdReq.getHeader("unitcost_txt").getValue(i);
				String grade_name = gdReq.getHeader("grade_name").getValue(i);
				String grade_color = gdReq.getHeader("grade_color").getValue(i);
				String order_con = gdReq.getHeader("order_con").getValue(i);
				String h_weight = gdReq.getHeader("h_weight").getValue(i);
				String h_scrap = gdReq.getHeader("h_scrap").getValue(i);
				String t_weight = gdReq.getHeader("t_weight").getValue(i);
				String recycle = gdReq.getHeader("recycle").getValue(i);
				String die_no = gdReq.getHeader("die_no").getValue(i);
				String cavity = gdReq.getHeader("cavity").getValue(i);
				String m_su = gdReq.getHeader("m_su").getValue(i);
				String mold_cost = gdReq.getHeader("mold_cost").getValue(i);
				String mold_c_type = gdReq.getHeader("mold_c_type").getComboHiddenValues()[gdReq.getHeader("mold_c_type").getSelectedIndex(i)];
				String make_type = gdReq.getHeader("make_type").getComboHiddenValues()[gdReq.getHeader("make_type").getSelectedIndex(i)];
				String pro_1 = gdReq.getHeader("pro_1").getComboHiddenValues()[gdReq.getHeader("pro_1").getSelectedIndex(i)];
				String ton = gdReq.getHeader("ton").getValue(i);
				String spm = gdReq.getHeader("spm").getValue(i);
				String method_type = gdReq.getHeader("method_type").getComboHiddenValues()[gdReq.getHeader("method_type").getSelectedIndex(i)];
				String pro_level = gdReq.getHeader("pro_level").getComboHiddenValues()[gdReq.getHeader("pro_level").getSelectedIndex(i)];
				String pro_level_txt = gdReq.getHeader("pro_level_txt").getValue(i);
				String line_su = gdReq.getHeader("line_su").getValue(i);
				String sul_cost = gdReq.getHeader("sul_cost").getValue(i);
				String plating_type = gdReq.getHeader("plating_type").getComboHiddenValues()[gdReq.getHeader("plating_type").getSelectedIndex(i)];
				String type_2 = gdReq.getHeader("type_2").getComboHiddenValues()[gdReq.getHeader("type_2").getSelectedIndex(i)];
				String plating_cost = gdReq.getHeader("plating_cost").getValue(i);
				String type_1 = gdReq.getHeader("type_1").getComboHiddenValues()[gdReq.getHeader("type_1").getSelectedIndex(i)];
				String t_mone = gdReq.getHeader("t_mone").getComboHiddenValues()[gdReq.getHeader("t_mone").getSelectedIndex(i)];
				String type_cost = gdReq.getHeader("type_cost").getValue(i);
				String t_order = gdReq.getHeader("t_order").getComboHiddenValues()[gdReq.getHeader("t_order").getSelectedIndex(i)];
				String pack_type = gdReq.getHeader("pack_type").getValue(i);
				String pack_cost = gdReq.getHeader("pack_cost").getValue(i);
				String in_pack = gdReq.getHeader("in_pack").getValue(i);
				String out_pack = gdReq.getHeader("out_pack").getValue(i);
				String store = gdReq.getHeader("store").getValue(i);
				String dest = gdReq.getHeader("dest").getValue(i);
				String dis_cost = gdReq.getHeader("dis_cost").getValue(i);
				String distri_cost = gdReq.getHeader("distri_cost").getValue(i);
				String royalty = gdReq.getHeader("royalty").getComboHiddenValues()[gdReq.getHeader("royalty").getSelectedIndex(i)];
				String yazaki_ro = gdReq.getHeader("yazaki_ro").getValue(i);
				String etc_cost = gdReq.getHeader("etc_cost").getValue(i);
				String part_note = gdReq.getHeader("part_note").getValue(i);
				part_name = part_name.replaceAll("'", "");
				part_note = part_note.replaceAll("'", "");
				if (case_count_1_temp.equals("")) {
					case_count_1_temp = "0";
				}
				if (case_count_2_temp.equals("")) {
					case_count_2_temp = "0";
				}

				if (i == 0) {
					case_count_1 = Integer.parseInt(case_count_1_temp); // 케이스 카운트 1번째
					case_count_2 = Integer.parseInt(case_count_2_temp); // 케이스 카운트 2번째
				}

				if (!crud.equals("D")) {
					if (temp_group_no.length() < 5) { // T001
						group_no_case = temp_group_no;
						case_count_1 = Integer.parseInt(case_count_1_temp); // 케이스 카운트 1번째
						case_count_2 = Integer.parseInt(case_count_2_temp); // 케이스 카운트 2번째
						case_cnt1 = 0;
						group_no = group_no_case;
					} else if (temp_group_no.length() < 8) { // T001-01
						case_cnt1 = case_cnt1 + 1;
						group_no_case2 = group_no_case.substring(0, 4) + "-" + format(case_cnt1, "00");
						case_count_2 = Integer.parseInt(case_count_2_temp); // 케이스 카운트 2번째
						case_cnt2 = 0;
						group_no = group_no_case2;
					} else {// T001-01-01
						case_cnt2 = case_cnt2 + 1;
						group_no = group_no_case2.substring(0, 7) + "-" + format(case_cnt2, "00");
					}
				}

				if (crud.equals("C")) {

					createData.put("pk_cr_group_1", pk_cr_group_1);
					createData.put("rev_no", rev_no);
					createData.put("group_no", group_no);
					createData.put("case_count_1", Integer.toString(case_count_1));
					createData.put("case_count_2", Integer.toString(case_count_2));
					createData.put("pro_type", pro_type);
					createData.put("make", make);
					createData.put("part_name", part_name);
					createData.put("part_type", part_type);
					createData.put("mix_group", mix_group);
					createData.put("part_no", part_no);
					createData.put("net_1", net_1);
					createData.put("net_2", net_2);
					createData.put("net_3", net_3);
					createData.put("usage", usage);
					createData.put("meterial", meterial);
					createData.put("t_size", t_size);
					createData.put("w_size", w_size);
					createData.put("p_size", p_size);
					createData.put("plating", plating);
					createData.put("m_maker", m_maker);
					createData.put("m_mone", m_mone);
					createData.put("unitcost", unitcost);
					createData.put("unitcost_txt", unitcost_txt);
					createData.put("grade_name", grade_name);
					createData.put("grade_color", grade_color);
					createData.put("order_con", order_con);
					createData.put("h_weight", h_weight);
					createData.put("h_scrap", h_scrap);
					createData.put("t_weight", t_weight);
					createData.put("recycle", recycle);
					createData.put("die_no", die_no);
					createData.put("cavity", cavity);
					createData.put("m_su", m_su);
					createData.put("mold_cost", mold_cost);
					createData.put("mold_c_type", mold_c_type);
					createData.put("make_type", make_type);
					createData.put("pro_1", pro_1);
					createData.put("ton", ton);
					createData.put("spm", spm);
					createData.put("method_type", method_type);
					createData.put("pro_level", pro_level);
					createData.put("pro_level_txt", pro_level_txt);
					createData.put("line_su", line_su);
					createData.put("sul_cost", sul_cost);
					createData.put("plating_type", plating_type);
					createData.put("type_2", type_2);
					createData.put("plating_cost", plating_cost);
					createData.put("type_1", type_1);
					createData.put("t_mone", t_mone);
					createData.put("type_cost", type_cost);
					createData.put("t_order", t_order);
					createData.put("pack_type", pack_type);
					createData.put("pack_cost", pack_cost);
					createData.put("in_pack", in_pack);
					createData.put("out_pack", out_pack);
					createData.put("store", store);
					createData.put("dest", dest);
					createData.put("dis_cost", dis_cost);
					createData.put("distri_cost", distri_cost);
					createData.put("royalty", royalty);
					createData.put("yazaki_ro", yazaki_ro);
					createData.put("etc_cost", etc_cost);
					createData.put("part_note", part_note);
					createData.put("data_type", data_type);
					createData.put("table_row", table_row);
					createDataList.add(createData);
					cnt = codeDao.costReEditSave(createDataList);
					createDataList.clear();

				} else if (crud.equals("U")) {
					updateData.put("pk_cr_group_1", pk_cr_group_1);
					updateData.put("rev_no", rev_no);
					updateData.put("group_no", group_no);
					updateData.put("case_count_1", Integer.toString(case_count_1));
					updateData.put("case_count_2", Integer.toString(case_count_2));
					updateData.put("pro_type", pro_type);
					updateData.put("make", make);
					updateData.put("part_name", part_name);
					updateData.put("part_type", part_type);
					updateData.put("mix_group", mix_group);
					updateData.put("part_no", part_no);
					updateData.put("net_1", net_1);
					updateData.put("net_2", net_2);
					updateData.put("net_3", net_3);
					updateData.put("usage", usage);
					updateData.put("meterial", meterial);
					updateData.put("t_size", t_size);
					updateData.put("w_size", w_size);
					updateData.put("p_size", p_size);
					updateData.put("plating", plating);
					updateData.put("m_maker", m_maker);
					updateData.put("m_mone", m_mone);
					updateData.put("unitcost", unitcost);
					updateData.put("unitcost_txt", unitcost_txt);
					updateData.put("grade_name", grade_name);
					updateData.put("grade_color", grade_color);
					updateData.put("order_con", order_con);
					updateData.put("h_weight", h_weight);
					updateData.put("h_scrap", h_scrap);
					updateData.put("t_weight", t_weight);
					updateData.put("recycle", recycle);
					updateData.put("die_no", die_no);
					updateData.put("cavity", cavity);
					updateData.put("m_su", m_su);
					updateData.put("mold_cost", mold_cost);
					updateData.put("mold_c_type", mold_c_type);
					updateData.put("make_type", make_type);
					updateData.put("pro_1", pro_1);
					updateData.put("ton", ton);
					updateData.put("spm", spm);
					updateData.put("method_type", method_type);
					updateData.put("pro_level", pro_level);
					updateData.put("pro_level_txt", pro_level_txt);
					updateData.put("line_su", line_su);
					updateData.put("sul_cost", sul_cost);
					updateData.put("plating_type", plating_type);
					updateData.put("type_2", type_2);
					updateData.put("plating_cost", plating_cost);
					updateData.put("type_1", type_1);
					updateData.put("t_mone", t_mone);
					updateData.put("type_cost", type_cost);
					updateData.put("t_order", t_order);
					updateData.put("pack_type", pack_type);
					updateData.put("pack_cost", pack_cost);
					updateData.put("in_pack", in_pack);
					updateData.put("out_pack", out_pack);
					updateData.put("store", store);
					updateData.put("dest", dest);
					updateData.put("dis_cost", dis_cost);
					updateData.put("distri_cost", distri_cost);
					updateData.put("royalty", royalty);
					updateData.put("yazaki_ro", yazaki_ro);
					updateData.put("etc_cost", etc_cost);
					updateData.put("part_note", part_note);
					updateData.put("data_type", data_type);
					updateData.put("pk_cr", pk_cr);
					updateData.put("etc_note", etc_note);

					updateDataList.add(updateData);
					cnt = codeDao.costReEditUpdate(updateDataList);
					updateDataList.clear();

				} else if (crud.equals("D")) {
					deleteData.put("pk_cr", pk_cr);
					deleteDataList.add(deleteData);
					cnt = codeDao.costReEditDelete(deleteDataList);
					deleteDataList.clear();
				} else {
					updateData.put("group_no", group_no);
					updateData.put("case_count_1", Integer.toString(case_count_1));
					updateData.put("case_count_2", Integer.toString(case_count_2));
					updateData.put("pk_cr", pk_cr);
					updateData.put("etc_note", etc_note);

					updateDataList.add(updateData);
					cnt = codeDao.costReEditUpdate2(updateDataList);
					updateDataList.clear();
				}
			}
			if (conn != null) {
				conn.close();
			}
			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");
			// returnData += getSendData(updateDataList, "U");
			// returnData += getSendData(deleteDataList, "D");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "save");
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public GridData Re_view_Search1(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
			String rev_no = gdReq.getParam("rev_no");
			String data_type = gdReq.getParam("data_type");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.Re_viewSearch_1(pk_cr_group_1, rev_no);
			Hashtable SearchMap = null;
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				SearchMap = (Hashtable) SearchList.get(i);
				String pk_cr_group = StringUtil.checkNull((String) SearchMap.get("pk_cr_group"));
				String pjt_no = StringUtil.checkNull((String) SearchMap.get("pjt_no"));
				String pjt_name = StringUtil.checkNull((String) SearchMap.get("pjt_name"));
				String team = StringUtil.checkNull((String) SearchMap.get("team"));
				String f_name = StringUtil.checkNull((String) SearchMap.get("f_name"));
				String a_name = StringUtil.checkNull((String) SearchMap.get("a_name"));
				String dev_step = StringUtil.checkNull((String) SearchMap.get("dev_step"));
				String dr_step = StringUtil.checkNull((String) SearchMap.get("dr_step"));
				String sub_step = StringUtil.checkNull((String) SearchMap.get("sub_step"));
				String Leader_day = StringUtil.checkNull((String) SearchMap.get("Leader_day"));
				String request_day = StringUtil.checkNull((String) SearchMap.get("request_day"));
				String request_txt = StringUtil.checkNull((String) SearchMap.get("request_txt"));
				String etc_note = StringUtil.checkNull((String) SearchMap.get("etc_note"));
				String file_1 = StringUtil.checkNull((String) SearchMap.get("file_1"));
				String file_2 = StringUtil.checkNull((String) SearchMap.get("file_2"));
				String file_3 = StringUtil.checkNull((String) SearchMap.get("file_3"));

				gdRes.getHeader("pk_cr_group").addValue(pk_cr_group, "");
				gdRes.getHeader("pjt_no").addValue(pjt_no, "");
				gdRes.getHeader("pjt_name").addValue(pjt_name, "");
				gdRes.getHeader("team").addSelectedHiddenValue(team);
				gdRes.getHeader("f_name").addValue(f_name, "");
				gdRes.getHeader("a_name").addValue(a_name, "");
				gdRes.getHeader("dev_step").addSelectedHiddenValue(dev_step);
				gdRes.getHeader("dr_step").addValue(dr_step, "");
				gdRes.getHeader("sub_step").addSelectedHiddenValue(sub_step);
				gdRes.getHeader("rev_no").addValue(rev_no, "");
				gdRes.getHeader("Leader_day").addValue(Leader_day, "");
				gdRes.getHeader("request_day").addValue(request_day, "");
				gdRes.getHeader("request_txt").addValue(request_txt, "");
				gdRes.getHeader("etc_note").addValue(etc_note, "");
				gdRes.getHeader("file_1").addValue(file_1, "");
				gdRes.getHeader("file_2").addValue(file_2, "");
				gdRes.getHeader("file_3").addValue(file_3, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (conn != null) {
			conn.close();
		}
		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	public GridData Re_view_Search2(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
			String rev_no = gdReq.getParam("rev_no");
			String data_type = gdReq.getParam("data_type");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.Re_viewSearch_2(pk_cr_group_1, data_type, rev_no);
			Hashtable SearchMap = null;

			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				SearchMap = (Hashtable) SearchList.get(i);
				String pk_cr = StringUtil.checkNull((String) SearchMap.get("pk_cr"));
				String pk_cr_group = StringUtil.checkNull((String) SearchMap.get("pk_cr_group"));
				String group_no = StringUtil.checkNull((String) SearchMap.get("group_no"));
				String part_name = StringUtil.checkNull((String) SearchMap.get("part_name"));
				String part_no = StringUtil.checkNull((String) SearchMap.get("part_no"));
				String car_type = StringUtil.checkNull((String) SearchMap.get("car_type"));
				String su_year_1 = StringUtil.checkNull((String) SearchMap.get("su_year_1"));
				String su_year_2 = StringUtil.checkNull((String) SearchMap.get("su_year_2"));
				String su_year_3 = StringUtil.checkNull((String) SearchMap.get("su_year_3"));
				String su_year_4 = StringUtil.checkNull((String) SearchMap.get("su_year_4"));
				String su_year_5 = StringUtil.checkNull((String) SearchMap.get("su_year_5"));
				String su_year_6 = StringUtil.checkNull((String) SearchMap.get("su_year_6"));
				String su_year_7 = StringUtil.checkNull((String) SearchMap.get("su_year_7"));
				String su_year_8 = StringUtil.checkNull((String) SearchMap.get("su_year_8"));
				String customer_F = StringUtil.checkNull((String) SearchMap.get("customer_F"));
				String customer_L = StringUtil.checkNull((String) SearchMap.get("customer_L"));
				String client_cost = StringUtil.checkNull((String) SearchMap.get("client_cost"));
				String ket_cost = StringUtil.checkNull((String) SearchMap.get("ket_cost"));
				String target_cost = StringUtil.checkNull((String) SearchMap.get("target_cost"));
				String rev_pk = StringUtil.checkNull((String) SearchMap.get("rev_pk"));
				String param_rev_no = StringUtil.checkNull((String) SearchMap.get("rev_no"));

				gdRes.getHeader("SEQ_NO").addValue(Integer.toString(i), "");
				gdRes.getHeader("pk_cr").addValue(pk_cr, "");
				gdRes.getHeader("pk_cr_group").addValue(pk_cr_group, "");
				gdRes.getHeader("group_no").addValue(group_no, "");
				gdRes.getHeader("part_name").addValue(part_name, "");
				gdRes.getHeader("part_no").addValue(part_no, "");
				gdRes.getHeader("car_type").addValue(car_type, "");
				gdRes.getHeader("su_year_1").addValue(su_year_1, "");
				gdRes.getHeader("su_year_2").addValue(su_year_2, "");
				gdRes.getHeader("su_year_3").addValue(su_year_3, "");
				gdRes.getHeader("su_year_4").addValue(su_year_4, "");
				gdRes.getHeader("su_year_5").addValue(su_year_5, "");
				gdRes.getHeader("su_year_6").addValue(su_year_6, "");
				gdRes.getHeader("su_year_7").addValue(su_year_7, "");
				gdRes.getHeader("su_year_8").addValue(su_year_8, "");
				gdRes.getHeader("customer_F").addValue(customer_F, "");
				gdRes.getHeader("customer_L").addValue(customer_L, "");
				gdRes.getHeader("client_cost").addValue(client_cost, "");
				gdRes.getHeader("ket_cost").addValue(ket_cost, "");
				gdRes.getHeader("target_cost").addValue(target_cost, "");
				gdRes.getHeader("rev_pk").addValue(rev_pk, "");
				gdRes.getHeader("rev_no").addValue(param_rev_no, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (conn != null) {
			conn.close();
		}
		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	public GridData Re_view_Search3(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
			String rev_no = gdReq.getParam("rev_no");
			String data_type = gdReq.getParam("data_type");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.Re_viewSearch_3(pk_cr_group_1, data_type, rev_no);
			Hashtable SearchMap = null;

			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				SearchMap = (Hashtable) SearchList.get(i);
				String case_count_1 = StringUtil.checkNull((String) SearchMap.get("case_count_1"));
				String case_count_2 = StringUtil.checkNull((String) SearchMap.get("case_count_2"));
				String pk_cr = StringUtil.checkNull((String) SearchMap.get("pk_cr"));
				String pk_cr_group = StringUtil.checkNull((String) SearchMap.get("pk_cr_group"));
				String make = StringUtil.checkNull((String) SearchMap.get("make"));
				String group_no = StringUtil.checkNull((String) SearchMap.get("group_no"));
				String pro_type = StringUtil.checkNull((String) SearchMap.get("pro_type"));
				String g_sel1 = StringUtil.checkNull((String) SearchMap.get("g_sel1"));
				String g_sel2 = StringUtil.checkNull((String) SearchMap.get("g_sel2"));
				String g_sel3 = StringUtil.checkNull((String) SearchMap.get("g_sel3"));
				String part_name = StringUtil.checkNull((String) SearchMap.get("part_name"));
				String part_type = StringUtil.checkNull((String) SearchMap.get("part_type"));
				String mix_group = StringUtil.checkNull((String) SearchMap.get("mix_group"));
				String part_no = StringUtil.checkNull((String) SearchMap.get("part_no"));
				String net_1 = StringUtil.checkNull((String) SearchMap.get("net_1"));
				String net_2 = StringUtil.checkNull((String) SearchMap.get("net_2"));
				String net_3 = StringUtil.checkNull((String) SearchMap.get("net_3"));
				String usage = StringUtil.checkNull((String) SearchMap.get("usage"));
				String meterial = StringUtil.checkNull((String) SearchMap.get("meterial"));
				String t_size = StringUtil.checkNull((String) SearchMap.get("t_size"));
				String w_size = StringUtil.checkNull((String) SearchMap.get("w_size"));
				String p_size = StringUtil.checkNull((String) SearchMap.get("p_size"));
				String plating = StringUtil.checkNull((String) SearchMap.get("plating"));
				String m_maker = StringUtil.checkNull((String) SearchMap.get("m_maker"));
				String m_mone = StringUtil.checkNull((String) SearchMap.get("m_mone"));
				String unitcost = StringUtil.checkNull((String) SearchMap.get("unitcost"));
				String unitcost_txt = StringUtil.checkNull((String) SearchMap.get("unitcost_txt"));
				String grade_name = StringUtil.checkNull((String) SearchMap.get("grade_name"));
				String grade_color = StringUtil.checkNull((String) SearchMap.get("grade_color"));
				String order_con = StringUtil.checkNull((String) SearchMap.get("order_con"));
				String h_weight = StringUtil.checkNull((String) SearchMap.get("h_weight"));
				String h_scrap = StringUtil.checkNull((String) SearchMap.get("h_scrap"));
				String t_weight = StringUtil.checkNull((String) SearchMap.get("t_weight"));
				String recycle = StringUtil.checkNull((String) SearchMap.get("recycle"));
				String die_no = StringUtil.checkNull((String) SearchMap.get("die_no"));
				String cavity = StringUtil.checkNull((String) SearchMap.get("cavity"));
				String m_su = StringUtil.checkNull((String) SearchMap.get("m_su"));
				String mold_cost = StringUtil.checkNull((String) SearchMap.get("mold_cost"));
				String mold_c_type = StringUtil.checkNull((String) SearchMap.get("mold_c_type"));
				String make_type = StringUtil.checkNull((String) SearchMap.get("make_type"));
				String pro_1 = StringUtil.checkNull((String) SearchMap.get("pro_1"));
				String ton = StringUtil.checkNull((String) SearchMap.get("ton"));
				String spm = StringUtil.checkNull((String) SearchMap.get("spm"));
				String method_type = StringUtil.checkNull((String) SearchMap.get("method_type"));
				String pro_level = StringUtil.checkNull((String) SearchMap.get("pro_level"));
				String pro_level_txt = StringUtil.checkNull((String) SearchMap.get("pro_level_txt"));
				String line_su = StringUtil.checkNull((String) SearchMap.get("line_su"));
				String sul_cost = StringUtil.checkNull((String) SearchMap.get("sul_cost"));
				String plating_type = StringUtil.checkNull((String) SearchMap.get("plating_type"));
				String type_2 = StringUtil.checkNull((String) SearchMap.get("type_2"));
				String plating_cost = StringUtil.checkNull((String) SearchMap.get("plating_cost"));
				String type_1 = StringUtil.checkNull((String) SearchMap.get("type_1"));
				String t_mone = StringUtil.checkNull((String) SearchMap.get("t_mone"));
				String type_cost = StringUtil.checkNull((String) SearchMap.get("type_cost"));
				String t_order = StringUtil.checkNull((String) SearchMap.get("t_order"));
				String pack_type = StringUtil.checkNull((String) SearchMap.get("pack_type"));
				String in_pack = StringUtil.checkNull((String) SearchMap.get("in_pack"));
				String out_pack = StringUtil.checkNull((String) SearchMap.get("out_pack"));
				String store = StringUtil.checkNull((String) SearchMap.get("store"));
				String dest = StringUtil.checkNull((String) SearchMap.get("dest"));
				String dis_cost = StringUtil.checkNull((String) SearchMap.get("dis_cost"));
				String distri_cost = StringUtil.checkNull((String) SearchMap.get("distri_cost"));
				String royalty = StringUtil.checkNull((String) SearchMap.get("royalty"));
				String etc_cost = StringUtil.checkNull((String) SearchMap.get("etc_cost"));
				String yazaki_ro = StringUtil.checkNull((String) SearchMap.get("yazaki_ro"));
				String part_note = StringUtil.checkNull((String) SearchMap.get("part_note"));

				gdRes.getHeader("SELECTED").addValue("0", "");
				gdRes.getHeader("CRUD").addValue("", "");
				gdRes.getHeader("SEQ_NO").addValue(Integer.toString(i), "");

				gdRes.getHeader("case_count_1").addValue(case_count_1, "");
				gdRes.getHeader("case_count_2").addValue(case_count_2, "");
				gdRes.getHeader("pk_cr").addValue(pk_cr, "");
				gdRes.getHeader("pk_cr_group").addValue(pk_cr_group, "");
				gdRes.getHeader("make").addValue(make, "");
				gdRes.getHeader("group_no").addValue(group_no, "");
				gdRes.getHeader("pro_type").addSelectedHiddenValue(pro_type);
				gdRes.getHeader("g_sel1").addValue("", "", g_sel1);
				gdRes.getHeader("g_sel2").addValue("", "", g_sel2);
				gdRes.getHeader("g_sel3").addValue("", "", g_sel3);
				gdRes.getHeader("part_name").addValue(part_name, "");
				gdRes.getHeader("part_type").addSelectedHiddenValue(part_type);
				gdRes.getHeader("mix_group").addSelectedHiddenValue(mix_group);
				gdRes.getHeader("part_no").addValue(part_no, "");
				gdRes.getHeader("net_1").addValue(net_1, "");
				gdRes.getHeader("net_2").addValue(net_2, "");
				gdRes.getHeader("net_3").addValue(net_3, "");
				gdRes.getHeader("usage").addValue(usage, "");
				gdRes.getHeader("meterial").addValue(meterial, "");
				gdRes.getHeader("t_size").addValue(t_size, "");
				gdRes.getHeader("w_size").addValue(w_size, "");
				gdRes.getHeader("p_size").addValue(p_size, "");
				gdRes.getHeader("plating").addValue(plating, "");
				gdRes.getHeader("m_maker").addValue(m_maker, "");
				gdRes.getHeader("m_mone").addValue(m_mone, "");
				gdRes.getHeader("unitcost").addValue(unitcost, "");
				gdRes.getHeader("unitcost_txt").addValue(unitcost_txt, "");
				gdRes.getHeader("grade_name").addValue(grade_name, "");
				gdRes.getHeader("grade_color").addValue(grade_color, "");
				gdRes.getHeader("order_con").addValue(order_con, "");
				gdRes.getHeader("h_weight").addValue(h_weight, "");
				gdRes.getHeader("h_scrap").addValue(h_scrap, "");
				gdRes.getHeader("t_weight").addValue(t_weight, "");
				gdRes.getHeader("recycle").addValue(recycle, "");
				gdRes.getHeader("die_no").addValue(die_no, "");
				gdRes.getHeader("cavity").addValue(cavity, "");
				gdRes.getHeader("m_su").addValue(m_su, "");
				gdRes.getHeader("mold_cost").addValue(mold_cost, "");
				gdRes.getHeader("mold_c_type").addSelectedHiddenValue(mold_c_type);
				gdRes.getHeader("make_type").addSelectedHiddenValue(make_type);
				gdRes.getHeader("pro_1").addSelectedHiddenValue(pro_1);
				gdRes.getHeader("ton").addValue(ton, "");
				gdRes.getHeader("spm").addValue(spm, "");
				gdRes.getHeader("method_type").addSelectedHiddenValue(method_type);
				gdRes.getHeader("pro_level").addSelectedHiddenValue(pro_level);
				gdRes.getHeader("pro_level_txt").addValue(pro_level_txt, "");
				gdRes.getHeader("line_su").addValue(line_su, "");
				gdRes.getHeader("sul_cost").addValue(sul_cost, "");
				gdRes.getHeader("plating_type").addSelectedHiddenValue(plating_type);
				gdRes.getHeader("type_2").addSelectedHiddenValue(type_2);
				gdRes.getHeader("plating_cost").addValue(plating_cost, "");
				gdRes.getHeader("type_1").addSelectedHiddenValue(type_1);
				gdRes.getHeader("t_mone").addSelectedHiddenValue(t_mone);
				gdRes.getHeader("type_cost").addValue(type_cost, "");
				gdRes.getHeader("t_order").addSelectedHiddenValue(t_order);
				gdRes.getHeader("pack_type").addValue(pack_type, "");
				gdRes.getHeader("in_pack").addValue(in_pack, "");
				gdRes.getHeader("out_pack").addValue(out_pack, "");
				gdRes.getHeader("store").addValue(store, "");
				gdRes.getHeader("dest").addValue(dest, "");
				gdRes.getHeader("dis_cost").addValue(dis_cost, "");
				gdRes.getHeader("distri_cost").addValue(distri_cost, "");
				gdRes.getHeader("royalty").addSelectedHiddenValue(royalty);
				gdRes.getHeader("etc_cost").addValue(etc_cost, "");
				gdRes.getHeader("yazaki_ro").addValue(yazaki_ro, "");
				gdRes.getHeader("part_note").addValue(part_note, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (conn != null) {
			conn.close();
		}
		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	public GridData Re_view_delete(GridData gdReq) throws Exception {
		GridData gdRes = new GridData();
		int rowCount = 0;
		String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
		String rev_no = gdReq.getParam("rev_no");
		String data_type = gdReq.getParam("data_type");

		try {

			Connection conn = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);

			codeDao.Re_view_Delete(pk_cr_group_1, rev_no);

			if (conn != null) {
				conn.close();
			}
			/*
			 * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
			 */

			// 이 예제는 통합통신의 동작을 확인하기 위한 샘플이므로
			// 만들어진 데이터를 화면의 fieldset으로 보내 정상적으로 통신이 이루어졌는지 확인한다.
			// String returnData = getSendData(createDataList, "C");

			/*
			 * 화면에 전달할 파라미터를 설정한다. 메세지를 셋팅한다. Status를 설정한다
			 */

			gdRes.addParam("mode", "del");
			// gdRes.addParam("saveData", "aa" );
			gdRes.setMessage("성공적으로 작업하였습니다.");
			gdRes.setStatus("true");

		} catch (Exception e) {
			throw e;
		}

		return gdRes;
	}

	public GridData Plm_view_Search_1(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String project_no = gdReq.getParam("pjt_no");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.Plm_view_Search_1(project_no);
			Hashtable SearchMap = null;
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.
				if (i == 0) {
					SearchMap = (Hashtable) SearchList.get(i);
					String pk_cr_group = StringUtil.checkNull((String) SearchMap.get("pjt_no"));
					String pjt_no = StringUtil.checkNull((String) SearchMap.get("pjt_no"));
					String pjt_name = StringUtil.checkNull((String) SearchMap.get("pjt_name"));
					String team = StringUtil.checkNull((String) SearchMap.get("team"));
					String f_name = StringUtil.checkNull((String) SearchMap.get("f_name"));
					String a_name = StringUtil.checkNull((String) SearchMap.get("a_name"));
					String dr_step = StringUtil.checkNull((String) SearchMap.get("drProgress"));
					String request_day = StringUtil.checkNull((String) SearchMap.get("request_day"));

					gdRes.getHeader("pk_cr_group").addValue(pk_cr_group, "");
					gdRes.getHeader("pjt_no").addValue(pjt_no, "");
					gdRes.getHeader("pjt_name").addValue(pjt_name, "");
					gdRes.getHeader("team").addSelectedHiddenValue(team);
					gdRes.getHeader("f_name").addValue(f_name, "");
					gdRes.getHeader("a_name").addValue(a_name, "");
					gdRes.getHeader("dr_step").addValue(dr_step, "");
					gdRes.getHeader("request_day").addValue(request_day, "");

					gdRes.getHeader("dev_step").addSelectedHiddenValue("empty");
					gdRes.getHeader("sub_step").addSelectedHiddenValue("empty");
					gdRes.getHeader("Leader_day").addValue("", "");

					gdRes.getHeader("request_txt").addValue("", "");
					gdRes.getHeader("etc_note").addValue("", "");
					gdRes.getHeader("file_1").addValue("", "");
					gdRes.getHeader("file_2").addValue("", "");
					gdRes.getHeader("file_3").addValue("", "");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (conn != null) {
			conn.close();
		}
		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	public GridData Plm_view_Search_2(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String project_no = gdReq.getParam("pjt_no");

			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			ArrayList SearchList = codeDao.Plm_view_Search_2(project_no);
			Hashtable SearchMap = null;

			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < SearchList.size(); i++) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				SearchMap = (Hashtable) SearchList.get(i);
				String pk_cr = StringUtil.checkNull((String) SearchMap.get("pk_cr"));
				String partSqe = StringUtil.checkNull((String) SearchMap.get("partSqe"));
				String pk_cr_group = StringUtil.checkNull((String) SearchMap.get("pk_cr_group"));
				String group_no = StringUtil.checkNull((String) SearchMap.get("group_no"));
				String part_name = StringUtil.checkNull((String) SearchMap.get("part_name"));
				String part_no = StringUtil.checkNull((String) SearchMap.get("part_no"));
				String car_type = StringUtil.checkNull((String) SearchMap.get("car_type"));
				String su_year_1 = StringUtil.checkNull((String) SearchMap.get("su_year_1"));
				String su_year_2 = StringUtil.checkNull((String) SearchMap.get("su_year_2"));
				String su_year_3 = StringUtil.checkNull((String) SearchMap.get("su_year_3"));
				String su_year_4 = StringUtil.checkNull((String) SearchMap.get("su_year_4"));
				String su_year_5 = StringUtil.checkNull((String) SearchMap.get("su_year_5"));
				String su_year_6 = StringUtil.checkNull((String) SearchMap.get("su_year_6"));
				String su_year_7 = StringUtil.checkNull((String) SearchMap.get("su_year_7"));
				String su_year_8 = StringUtil.checkNull((String) SearchMap.get("su_year_8"));
				String customer_F = StringUtil.checkNull((String) SearchMap.get("customer_F"));
				String customer_L = StringUtil.checkNull((String) SearchMap.get("customer_L"));
				String client_cost = StringUtil.checkNull((String) SearchMap.get("client_cost"));
				String ket_cost = StringUtil.checkNull((String) SearchMap.get("ket_cost"));
				String target_cost = StringUtil.checkNull((String) SearchMap.get("target_cost"));
				String rev_pk = StringUtil.checkNull((String) SearchMap.get("rev_pk"));
				String param_rev_no = StringUtil.checkNull((String) SearchMap.get("rev_no"));

				// gdRes.getHeader("SEQ_NO").addValue(Integer.toString(i), "");
				gdRes.getHeader("pk_cr").addValue(pk_cr, "");
				gdRes.getHeader("pk_cr_group").addValue(pk_cr_group, "");
				gdRes.getHeader("partSqe").addValue(partSqe, "");
				gdRes.getHeader("group_no").addValue(group_no, "");
				gdRes.getHeader("part_name").addValue(part_name, "");
				gdRes.getHeader("part_no").addValue(part_no, "");
				gdRes.getHeader("car_type").addValue(car_type, "");
				gdRes.getHeader("su_year_1").addValue(su_year_1, "");
				gdRes.getHeader("su_year_2").addValue(su_year_2, "");
				gdRes.getHeader("su_year_3").addValue(su_year_3, "");
				gdRes.getHeader("su_year_4").addValue(su_year_4, "");
				gdRes.getHeader("su_year_5").addValue(su_year_5, "");
				gdRes.getHeader("su_year_6").addValue(su_year_6, "");
				gdRes.getHeader("su_year_7").addValue(su_year_7, "");
				gdRes.getHeader("su_year_8").addValue(su_year_8, "");
				gdRes.getHeader("customer_F").addValue(customer_F, "");
				gdRes.getHeader("customer_L").addValue(customer_L, "");
				gdRes.getHeader("client_cost").addValue(client_cost, "");
				gdRes.getHeader("ket_cost").addValue(ket_cost, "");
				gdRes.getHeader("target_cost").addValue(target_cost, "");
				gdRes.getHeader("rev_pk").addValue(rev_pk, "");
				gdRes.getHeader("rev_no").addValue(param_rev_no, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (conn != null) {
			conn.close();
		}
		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	public GridData Plm_view_Search_3(GridData gdReq) throws Exception {
		Connection conn = null;
		// 전송할 데이터를 담을 빈 GridData를 생성합니다.
		GridData gdRes = null;
		int rowCount = 0;

		try {
			// WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
			// cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// 조회조건으로 사용할 Param 값들을 가져옵니다.
			String project_no = gdReq.getParam("pjt_no");
			String rev_no = gdReq.getParam("rev_no");
			String data_type = gdReq.getParam("data_type");
			String g_sel1 = "";
			String g_sel2 = "";
			String g_sel3 = "";
			WTPart wtpart = null;
			WTPart wtdie = null;
			conn = DBUtil.getConnection();
			CostComDao codeDao = new CostComDao(conn);
			// result
			List<PartBomInfoDTO> SearchList = codeDao.Plm_view_Search_3(project_no);

			Hashtable SearchMap = null;
			int i = 0;
			// GridData에 데이터를 셋팅합니다.
			for (PartBomInfoDTO dto : SearchList) {
				// t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
				// t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
				// t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

				String case_count_1 = StringUtil.checkNull(dto.getCase_count_1());
				String case_count_2 = StringUtil.checkNull(dto.getCase_count_2());
				String pk_cr = StringUtil.checkNull("");
				String pk_cr_group = StringUtil.checkNull(project_no);
				String make = StringUtil.checkNull("");
				String group_no = StringUtil.checkNull(dto.getGroupNo());
				String pro_type = "empty";

				if (group_no.length() < 5) {
					g_sel1 = "/plm/jsp/cost/acc_img/add_red.jpg";
					g_sel2 = "";
					g_sel3 = "";
				} else if (group_no.length() < 8) {
					g_sel1 = "";
					g_sel2 = "/plm/jsp/cost/acc_img/add.jpg";
					g_sel3 = "";
				} else {
					g_sel1 = "";
					g_sel2 = "";
					g_sel3 = "/plm/jsp/cost/acc_img/add.jpg";
				}

				String mix_group = "empty";
				String part_no = StringUtil.checkNull(dto.getChildItemCode());
				String part_type = StringUtil.checkNull("");
				E3PSProject project = ProjectHelper.getProject(project_no);

				if (project instanceof ProductProject) {
					ProductProject pjt = (ProductProject) project;
					QueryResult qr = ProductProjectHelper.manager.getMoldItemInfo(pjt, part_no);
					while (qr.hasMoreElements()) {
						Object obj[] = (Object[]) qr.nextElement();
						MoldItemInfo moldItemInfo = (MoldItemInfo) obj[0];
						part_type = moldItemInfo.getShrinkage();
					}
				}
				if (StringUtils.isEmpty(part_type)) {
					part_type = "신규";
				}

				wtpart = PartBaseHelper.service.getLatestPart(part_no);

				String part_name = StringUtil.checkNull(wtpart.getName());
				String net_1 = StringUtil.checkNull("");
				String net_2 = StringUtil.checkNull("");
				String net_3 = StringUtil.checkNull("");
				String usage = StringUtil.checkNull(dto.getQty());
				String meterial = StringUtil.checkNull("");

				String grade_name = StringUtil.checkNull("");
				String grade_color = StringUtil.checkNull("");
				String m_mone = StringUtil.checkNull("");
				String unitcost = StringUtil.checkNull("");
				String unitcost_txt = StringUtil.checkNull("");
				String plating = StringUtil.checkNull("");
				String order_con = StringUtil.checkNull("");
				String m_maker = "";
				// Maker 원재료
				String spMaterMaker = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMaterMaker);
				if (StringUtils.isNotEmpty(spMaterMaker)) {
					m_maker = CodeHelper.manager.getCodeValue(PartSpecEnum.SpMaterMaker.getAttrCodeType(), spMaterMaker);
				}

				// Material Grade 재질(수지) 또는 재질(비철)
				String spMaterialInfo = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMaterialInfo); // 재질(수지)
				String spMaterNotFe = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMaterNotFe); // 재질(비철)
				String materialInfoTemp = null;
				String delimiter = ""; // 원가시스템 기준 데이터가 있는지 확인하기 위한 구분자

				if (StringUtils.isEmpty(spMaterialInfo)) {
					materialInfoTemp = spMaterNotFe;
					make = "make_1"; // 비철
					meterial = PartBaseHelper.service.getMaterialInfo(materialInfoTemp);
					delimiter = m_maker + meterial;
				} else {
					materialInfoTemp = spMaterialInfo;
					make = "make_2"; // 수지
					meterial = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMaterType);
					if (StringUtils.isNotEmpty(meterial)) {
						meterial = CodeHelper.manager.getCodeValue(PartSpecEnum.SpMaterType.getAttrCodeType(), meterial);
					}
					grade_name = PartBaseHelper.service.getMaterialInfo(materialInfoTemp);
					delimiter = m_maker + meterial + grade_name;
				}

				if (StandardKetCostInfoService.isCostStandard(make, delimiter)) {// plm기준정보와 원가시스템기준정보를 비교한다
					if ("make_1".equals(make)) {// 비철
						plating = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpPlating);
						plating = CodeHelper.manager.getCodeValue(PartSpecEnum.SpPlating.getAttrCodeType(), plating);
						if (!"Pre-Tin".equals(plating)) {
							plating = "Unplate";
						}
					} else {// 수지
						if (StringUtils.isNotEmpty(meterial)) {
							if (StringUtils.isNotEmpty(grade_name)) {
								meterial = meterial + " " + grade_name;
							}
							grade_color = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpColor);
							if (StringUtils.isNotEmpty(grade_color)) {
								if ("BLACK".equals(grade_color.toUpperCase())) {
									grade_color = "Black";
								} else if ("NATURAL".equals(grade_color.toUpperCase())) {
									grade_color = "Natural";
								} else {
									grade_color = "Color";
								}
							}
							plating = grade_color;
						}
					}
					if (StringUtils.isNotEmpty(meterial)) {
						m_mone = "KRW";
						unitcost = "표준단가";
						unitcost_txt = "표준단가";
						order_con = "내자";
					}

				} else {
					meterial = "";
					m_maker = "";
					grade_name = "";
				}

				String t_size = StringUtil.checkNull("");
				String w_size = StringUtil.checkNull("");
				String p_size = StringUtil.checkNull("");

				String h_weight = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpNetWeight); // 부품중량
				String h_scrap = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpScrabWeight); // 스크랩 중량
				String t_weight = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpTotalWeight); // 총중량
				
				/*
				 * 원가팀 이경무 요청에 의해 중량정보는 PLM에서 연동하지 않고 개발자로부터 수기입력받기로 함(두께,폭,피치를 가져올수없기때문에..) 2015.07.02 by 황정태
				 */
				h_weight = "";
				h_scrap = "";
				t_weight = "";

				String recycle = StringUtil.checkNull("");
				String die_no = "";
				KETPartClassification claz = PartClazHelper.service.getPartClassification(wtpart);
				if (claz != null) {
					if ("C".equals(claz.getPartClassificType())) {

					} else {
						die_no = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpRepresentM); // 대표금형 die_no
					}
				}

				String cavity = StringUtil.checkNull("");
				String spm = StringUtil.checkNull("");

				if (PartBaseHelper.service.existWTPartNumber(die_no, "D") && StringUtils.isNotEmpty(die_no) && !"구매품".equals(die_no)) {
					wtdie = PartBaseHelper.service.getLatestPart(die_no);
					cavity = PartSpecGetter.getPartSpec(wtdie, PartSpecEnum.SpCavityStd);
					if (StringUtils.isNotEmpty(cavity)) {
						cavity = Integer.toString(Integer.parseInt(cavity));
					}
					spm = PartSpecGetter.getPartSpec(wtdie, PartSpecEnum.SpObjectiveCT);
				}
				String m_su = StringUtil.checkNull("");
				String mold_cost = StringUtil.checkNull("");
				String mold_c_type = "감가";
				String make_type = StringUtil.checkNull("");
				String pro_1 = StringUtil.checkNull("");
				make_type = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpManufAt); // 생산처구분

				if (StringUtils.isNotEmpty(make_type)) {
					if ("1".equals(make_type)) {
						make_type = "사내";

						String spManufacPlace = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpManufacPlace);
						String spManufacPlaceName = PartBaseHelper.service.getManufacPlaceName(spManufacPlace);

						if (StringUtils.isNotEmpty(spManufacPlaceName)) {
							if (spManufacPlaceName.startsWith("논현")) {
								pro_1 = "생산1";
							} else if (spManufacPlaceName.startsWith("남동")) {
								pro_1 = "생산2";
							} else if (spManufacPlaceName.startsWith("평택")) {
								pro_1 = "생산3";
							} else if (spManufacPlaceName.startsWith("광주")) {
								pro_1 = "생산4";
							} else if (spManufacPlaceName.startsWith("중국")) {
								pro_1 = "중국";
							}
						}

					} else if ("2".equals(make_type)) {
						make_type = "외주";
						pro_1 = "외주";
					}
				} else {
					make_type = "empty";
					pro_1 = "empty";
				}

				String ton = StringUtil.checkNull("");

				String method_type = "empty";
				String pro_level = "표준";
				String pro_level_txt = StringUtil.checkNull("");
				String line_su = StringUtil.checkNull("");
				String sul_cost = StringUtil.checkNull("");
				String plating_type = "empty";
				String type_2 = "empty";
				String plating_cost = StringUtil.checkNull("");
				String type_1 = "empty";
				String t_mone = "empty";
				String type_cost = StringUtil.checkNull("");
				String t_order = "empty";
				String pack_type = "empty";
				String in_pack = StringUtil.checkNull("");
				String out_pack = StringUtil.checkNull("");
				String store = StringUtil.checkNull("");
				String dest = StringUtil.checkNull("");
				String dis_cost = StringUtil.checkNull("");
				String distri_cost = StringUtil.checkNull("");
				String royalty = "2";
				String etc_cost = StringUtil.checkNull("");
				String yazaki_ro = StringUtil.checkNull("");
				String part_note = StringUtil.checkNull("");

				gdRes.getHeader("SELECTED").addValue("0", "");
				gdRes.getHeader("CRUD").addValue("C", "");
				gdRes.getHeader("SEQ_NO").addValue(Integer.toString(i), "");

				gdRes.getHeader("case_count_1").addValue(case_count_1, "");
				gdRes.getHeader("case_count_2").addValue(case_count_2, "");
				gdRes.getHeader("pk_cr").addValue(pk_cr, "");
				gdRes.getHeader("pk_cr_group").addValue(pk_cr_group, "");
				gdRes.getHeader("make").addValue(make, "");
				gdRes.getHeader("group_no").addValue(group_no, "");
				gdRes.getHeader("pro_type").addSelectedHiddenValue(pro_type);
				gdRes.getHeader("g_sel1").addValue("", "", g_sel1);
				gdRes.getHeader("g_sel2").addValue("", "", g_sel2);
				gdRes.getHeader("g_sel3").addValue("", "", g_sel3);
				gdRes.getHeader("part_name").addValue(part_name, "");
				gdRes.getHeader("part_type").addSelectedHiddenValue(part_type);
				gdRes.getHeader("mix_group").addSelectedHiddenValue(mix_group);
				gdRes.getHeader("part_no").addValue(part_no, "");
				gdRes.getHeader("net_1").addValue(net_1, "");
				gdRes.getHeader("net_2").addValue(net_2, "");
				gdRes.getHeader("net_3").addValue(net_3, "");
				gdRes.getHeader("usage").addValue(usage, "");
				gdRes.getHeader("meterial").addValue(meterial, "");
				gdRes.getHeader("t_size").addValue(t_size, "");
				gdRes.getHeader("w_size").addValue(w_size, "");
				gdRes.getHeader("p_size").addValue(p_size, "");
				gdRes.getHeader("plating").addValue(plating, "");
				gdRes.getHeader("m_maker").addValue(m_maker, "");
				gdRes.getHeader("m_mone").addValue(m_mone, "");
				gdRes.getHeader("unitcost").addValue(unitcost, "");
				gdRes.getHeader("unitcost_txt").addValue(unitcost_txt, "");
				gdRes.getHeader("grade_name").addValue(grade_name, "");
				gdRes.getHeader("grade_color").addValue(grade_color, "");
				gdRes.getHeader("order_con").addValue(order_con, "");
				gdRes.getHeader("h_weight").addValue(h_weight, "");
				gdRes.getHeader("h_scrap").addValue(h_scrap, "");
				gdRes.getHeader("t_weight").addValue(t_weight, "");
				gdRes.getHeader("recycle").addValue(recycle, "");
				gdRes.getHeader("die_no").addValue(die_no, "");
				gdRes.getHeader("cavity").addValue(cavity, "");
				gdRes.getHeader("m_su").addValue(m_su, "");
				gdRes.getHeader("mold_cost").addValue(mold_cost, "");
				gdRes.getHeader("mold_c_type").addSelectedHiddenValue(mold_c_type);
				gdRes.getHeader("make_type").addSelectedHiddenValue(make_type);
				gdRes.getHeader("pro_1").addSelectedHiddenValue(pro_1);
				gdRes.getHeader("ton").addValue(ton, "");
				gdRes.getHeader("spm").addValue(spm, "");
				gdRes.getHeader("method_type").addSelectedHiddenValue(method_type);
				gdRes.getHeader("pro_level").addSelectedHiddenValue(pro_level);
				gdRes.getHeader("pro_level_txt").addValue(pro_level_txt, "");
				gdRes.getHeader("line_su").addValue(line_su, "");
				gdRes.getHeader("sul_cost").addValue(sul_cost, "");
				gdRes.getHeader("plating_type").addSelectedHiddenValue(plating_type);
				gdRes.getHeader("type_2").addSelectedHiddenValue(type_2);
				gdRes.getHeader("plating_cost").addValue(plating_cost, "");
				gdRes.getHeader("type_1").addSelectedHiddenValue(type_1);
				gdRes.getHeader("t_mone").addSelectedHiddenValue(t_mone);
				gdRes.getHeader("type_cost").addValue(type_cost, "");
				gdRes.getHeader("t_order").addSelectedHiddenValue(t_order);
				gdRes.getHeader("pack_type").addValue(pack_type, "");
				gdRes.getHeader("in_pack").addValue(in_pack, "");
				gdRes.getHeader("out_pack").addValue(out_pack, "");
				gdRes.getHeader("store").addValue(store, "");
				gdRes.getHeader("dest").addValue(dest, "");
				gdRes.getHeader("dis_cost").addValue(dis_cost, "");
				gdRes.getHeader("distri_cost").addValue(distri_cost, "");
				gdRes.getHeader("royalty").addSelectedHiddenValue(royalty);
				gdRes.getHeader("etc_cost").addValue(etc_cost, "");
				gdRes.getHeader("yazaki_ro").addValue(yazaki_ro, "");
				gdRes.getHeader("part_note").addValue(part_note, "");
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (conn != null) {
			conn.close();
		}
		// 메세지와 상태값을 셋팅합니다.

		gdRes.addParam("mode", "search");
		gdRes.setMessage("성공적으로 작업하였습니다.");
		gdRes.setStatus("true");

		return gdRes;
	}

	/**
	 * 서블릿 호출후 호출결과를 Redirect 사용하는 Method
	 */
	protected void gotoResult(HttpServletRequest req, HttpServletResponse res, String address) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(req, res);
		return;
	}

	protected String format(int szString, String expression) {
		String format = "";
		if (Integer.toString(szString).length() < expression.length()) {
			format = expression.substring(0, expression.length() - Integer.toString(szString).length()) + Integer.toString(szString);
		} else {
			format = Integer.toString(szString);
		}
		return format;
	}

	public void info() {
		String[] temp;
		String[] result;
		String ip, subNet, gateWay; // 아이피, 서브넷 마스크, 게이트 웨이

		int i = 0;

		String cmd[] = { "cmd", "/c", "ipconfig" }; // 도스창에서 ipconfig를 치는것과 같은 효과
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		} // 위에 선언된 명령(cmd)을 프로세스를 통하여 실행시킴

		InputStream is = process.getInputStream(); // 프로세서에 입력된 스트림을 읽어들인다.
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;

		temp = new String[100];
		try {
			while ((line = br.readLine()) != null) {
				temp[i] = line;
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		result = new String[2]; // 만약 문제 발생시 temp[ ] 안의 값을 약간식 수정
		result = temp[14].split(":"); // : 기준으로 문장을 나눠서 ip등이 담긴 곳의 값만 추출
		ip = result[1].trim();
		result = temp[16].split(":");
		subNet = result[1].trim();
		result = temp[18].split(":");
		gateWay = result[1].trim();

		System.out.println("ip - " + ip); // 값 출력
		System.out.println("subNet - " + subNet);
		System.out.println("gateWay - " + gateWay);

	}

}
