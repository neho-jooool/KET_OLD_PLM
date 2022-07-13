package e3ps.qms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class QmsDao {

	private Connection conn;

	public QmsDao(Connection conn) {
		this.conn = conn;
	}

	public QmsDao() {
		super();
	}

	/**
	 * 함수명 : setInsertPLMProject 설명 : QMS DB에 PLM Project 등록
	 * 
	 * @param ArrayList
	 *            projectList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 05.
	 */
	public int setInsertPLMProject(ArrayList projectList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable projectItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;
		sb.append(" INSERT INTO TBL_INF_PLM_PJT(");
		sb.append("ida2a2, \n");
		sb.append("pjt_no, \n");
		sb.append("pjt_name, \n");
		sb.append("pnum, \n");
		sb.append("pname, \n");
		sb.append("cartype_code, \n");
		sb.append("cartype_name, \n");
		sb.append("producttype_code, \n");
		sb.append("producttype_name, \n");
		sb.append("dept_code, \n");
		sb.append("dept_name, \n");
		sb.append("account_no, \n");
		sb.append("pm_name, \n");
		sb.append("developenttype_code, \n");
		sb.append("developenttype_name, \n");
		sb.append("statestate, \n");
		// // sb.append("statename, \n");
		sb.append("planenddate, \n");
		sb.append("process_code, \n");
		sb.append("process_name, \n");
		sb.append("mold_count, \n");
		sb.append("mold_state, \n");
		sb.append("press_count, \n");
		sb.append("press_state, \n");
		sb.append("oem1plan, \n");
		sb.append("oem2plan, \n");
		sb.append("oemSOPplan, \n");
		sb.append("gate4date, \n");
		sb.append("systemenddate, \n");
		// sb.append("productiondept_name, \n");
		sb.append("rolemember_code, \n");
		sb.append("rolemember_name, \n");
		sb.append("rank, \n");
		sb.append("subcontractor_code, \n");
		sb.append("subcontractor_name, \n");
		sb.append("designtype_code, \n");
		sb.append("designtype_name, \n");
		sb.append("developpattern_code, \n");
		sb.append("develppattern_name, \n");
		sb.append("execstartdate, \n");
		sb.append("execenddate, \n");
		sb.append("taskexecenddate \n");
		sb.append(") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			Kogger.debug("TBL_INF_PLM_PJT insert Start !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			for (int i = 0; i < projectList.size(); i++) {
				projectItem = (Hashtable) projectList.get(i);

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) projectItem.get("ida2a2"));
				pstmt.setString(2, (String) projectItem.get("pjtno"));
				pstmt.setString(3, (String) projectItem.get("pjtname"));
				pstmt.setString(4, (String) projectItem.get("pnum"));
				pstmt.setString(5, (String) projectItem.get("pname"));
				pstmt.setString(6, (String) projectItem.get("cartype_code"));
				pstmt.setString(7, (String) projectItem.get("cartype_name"));
				pstmt.setString(8, (String) projectItem.get("producttype_code"));
				pstmt.setString(9, (String) projectItem.get("producttype_name"));
				pstmt.setString(10, (String) projectItem.get("dept_code"));
				pstmt.setString(11, (String) projectItem.get("dept_name"));
				pstmt.setString(12, (String) projectItem.get("account_no"));
				pstmt.setString(13, (String) projectItem.get("pm_name"));
				pstmt.setString(14, (String) projectItem.get("developenttype_code"));
				pstmt.setString(15, (String) projectItem.get("developenttype_name"));
				pstmt.setString(16, (String) projectItem.get("statestate"));
				pstmt.setString(17, (String) projectItem.get("planenddate"));
				pstmt.setString(18, (String) projectItem.get("process_code"));
				pstmt.setString(19, (String) projectItem.get("process_name"));
				pstmt.setString(20, (String) projectItem.get("mold_count"));
				pstmt.setString(21, (String) projectItem.get("mold_state"));
				pstmt.setString(22, (String) projectItem.get("press_count"));
				pstmt.setString(23, (String) projectItem.get("press_state"));
				pstmt.setString(24, (String) projectItem.get("oem1plan"));
				pstmt.setString(25, (String) projectItem.get("oem2plan"));
				pstmt.setString(26, (String) projectItem.get("oemSOPplan"));
				pstmt.setString(27, (String) projectItem.get("gate4date"));
				pstmt.setString(28, (String) projectItem.get("systemenddate"));
				pstmt.setString(29, (String) projectItem.get("rolemember_code"));
				pstmt.setString(30, (String) projectItem.get("rolemember_name"));
				pstmt.setString(31, (String) projectItem.get("rank"));
				pstmt.setString(32, (String) projectItem.get("subcontractor_code"));
				pstmt.setString(33, (String) projectItem.get("subcontractor_name"));
				pstmt.setString(34, (String) projectItem.get("designtype_code"));
				pstmt.setString(35, (String) projectItem.get("designtype_name"));
				pstmt.setString(36, (String) projectItem.get("developpattern_code"));
				pstmt.setString(37, (String) projectItem.get("develppattern_name"));
				pstmt.setString(38, (String) projectItem.get("execstartdate"));
				pstmt.setString(39, (String) projectItem.get("execenddate"));
				pstmt.setString(40, (String) projectItem.get("taskexecenddate"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_PLM_PJT insert Completed !!");
			Kogger.debug("TBL_INF_PLM_PJT insert Completed !!!!!!!!!!!!!!!");

		} catch (Exception e) {
			e.printStackTrace();
			// conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setInsertPLMECO 설명 : QMS DB에 PLM ECO 등록
	 * 
	 * @param ArrayList
	 *            ecoList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 11.
	 */
	public int setInsertPLMEcoPjt(ArrayList ecoList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable projectItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;

		sb.append(" INSERT INTO TBL_INF_PLM_OC_DES(");
		sb.append("pjtno, \n");
		sb.append("pjt_name, \n");
		sb.append("statestate, \n");
		// sb.append("statename, \n");
		sb.append("pnum, \n");
		sb.append("pname, \n");
		sb.append("cartype_code, \n");
		sb.append("cartype_name, \n");
		sb.append("approvedate, \n");
		sb.append("producttype_code, \n");
		sb.append("producttype_name, \n");
		sb.append("eco_no, \n");
		sb.append("dept_code, \n");
		sb.append("dept_name, \n");
		sb.append("creator_code, \n");
		sb.append("creator_name, \n");
		sb.append("process_code, \n");
		sb.append("process_name, \n");
		sb.append("ecr_webeditortext, \n");
		sb.append("eco_after_webeditortext \n");
		sb.append(") VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) \n");

		try {
			Kogger.debug("TBL_INF_PLM_OC_DES insert Start !!!!!!!!!!!!!!!");
			for (int i = 0; i < ecoList.size(); i++) {
				projectItem = (Hashtable) ecoList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				Kogger.debug("by sql ecr ::: " + (String) projectItem.get("ecoid") + " ::: " + projectItem.get("ecr_webeditortext").toString().length());
				System.out.println("by sql ecr ::: " + (String) projectItem.get("ecoid") + " ::: " + projectItem.get("ecr_webeditortext").toString().length());
				Kogger.debug("by sql eco ::: " + (String) projectItem.get("ecoid") + " ::: " + projectItem.get("eco_after_webeditortext").toString().length());
				System.out.println("by sql eco ::: " + (String) projectItem.get("ecoid") + " ::: " + projectItem.get("eco_after_webeditortext").toString().length());
				if ("ECO-1506-376".equals(projectItem.get("ecoid").toString()) || "ECO-1412-150".equals(projectItem.get("ecoid").toString())) {

				} else {
					pstmt.setString(1, (String) projectItem.get("pjtno"));
					pstmt.setString(2, (String) projectItem.get("pjtname"));
					pstmt.setString(3, (String) projectItem.get("statestate"));
					pstmt.setString(4, (String) projectItem.get("pnum"));
					pstmt.setString(5, (String) projectItem.get("pname"));
					pstmt.setString(6, (String) projectItem.get("cartype_code"));
					pstmt.setString(7, (String) projectItem.get("cartype_name"));
					pstmt.setString(8, (String) projectItem.get("approvedate"));
					pstmt.setString(9, (String) projectItem.get("producttype_code"));
					pstmt.setString(10, (String) projectItem.get("producttype_name"));
					pstmt.setString(11, (String) projectItem.get("ecoid"));
					pstmt.setString(12, (String) projectItem.get("dept_code"));
					pstmt.setString(13, (String) projectItem.get("dept_name"));
					pstmt.setString(14, (String) projectItem.get("creator_perno"));
					pstmt.setString(15, (String) projectItem.get("creator_name"));
					pstmt.setString(16, (String) projectItem.get("process_code"));
					pstmt.setString(17, (String) projectItem.get("process_name"));
					pstmt.setString(18, (String) projectItem.get("ecr_webeditortext"));
					pstmt.setString(19, (String) projectItem.get("eco_after_webeditortext"));

					complet = pstmt.executeUpdate();
				}
			}
			System.out.println("TBL_INF_PLM_OC_DES insert completed !!");
			Kogger.debug("TBL_INF_PLM_OC_DES insert Completed !!!!!!!!!!!!!!!");

		} catch (Exception e) {
			e.printStackTrace();
			// conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setUpdatePLMEcoPjt 설명 : QMS DB에 PLM ECO 수정
	 * 
	 * @param ArrayList
	 *            ecoList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 07. 24.
	 */
	public int setUpdatePLMEcoPjt(ArrayList ecoList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable projectItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;

		sb.append(" UPDATE TBL_INF_PLM_OC_DES SET ");
		sb.append("pjtno = ?, \n");
		sb.append("pjt_name = ?, \n");
		sb.append("statestate = ?, \n");
		// sb.append("statename, \n");
		sb.append("pnum = ?, \n");
		sb.append("pname = ?, \n");
		sb.append("cartype_code = ?, \n");
		sb.append("cartype_name = ?, \n");
		sb.append("approvedate = ?, \n");
		sb.append("producttype_code = ?, \n");
		sb.append("producttype_name = ?, \n");
		sb.append("dept_code = ?, \n");
		sb.append("dept_name = ?, \n");
		sb.append("creator_code = ?, \n");
		sb.append("creator_name = ?, \n");
		sb.append("process_code = ?, \n");
		sb.append("process_name = ?, \n");
		sb.append("ecr_webeditortext = ?, \n");
		sb.append("eco_after_webeditortext = ? \n");
		sb.append("WHERE eco_no = ? \n");

		try {
			Kogger.debug("TBL_INF_PLM_OC_DES UPDATE Start !!!!!!!!!!!!!!!");
			for (int i = 0; i < ecoList.size(); i++) {
				projectItem = (Hashtable) ecoList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) projectItem.get("pjtno"));
				pstmt.setString(2, (String) projectItem.get("pjtname"));
				pstmt.setString(3, (String) projectItem.get("statestate"));
				pstmt.setString(4, (String) projectItem.get("pnum"));
				pstmt.setString(5, (String) projectItem.get("pname"));
				pstmt.setString(6, (String) projectItem.get("cartype_code"));
				pstmt.setString(7, (String) projectItem.get("cartype_name"));
				pstmt.setString(8, (String) projectItem.get("approvedate"));
				pstmt.setString(9, (String) projectItem.get("producttype_code"));
				pstmt.setString(10, (String) projectItem.get("producttype_name"));
				pstmt.setString(11, (String) projectItem.get("dept_code"));
				pstmt.setString(12, (String) projectItem.get("dept_name"));
				pstmt.setString(13, (String) projectItem.get("creator_perno"));
				pstmt.setString(14, (String) projectItem.get("creator_name"));
				pstmt.setString(15, (String) projectItem.get("process_code"));
				pstmt.setString(16, (String) projectItem.get("process_name"));
				pstmt.setString(17, (String) projectItem.get("ecr_webeditortext"));
				pstmt.setString(18, (String) projectItem.get("eco_after_webeditortext"));
				pstmt.setString(19, (String) projectItem.get("ecoid"));
				complet = pstmt.executeUpdate();
			}

			System.out.println("TBL_INF_PLM_OC_DES UPDATE completed !!");
			Kogger.debug("TBL_INF_PLM_OC_DES UPDATE Completed !!!!!!!!!!!!!!!");

		} catch (Exception e) {

			System.out.println("by hooni ::: qms dao ::: " + (String) projectItem.get("ecoid"));
			System.out.println("11111111111 ::: " + (String) projectItem.get("ecr_webeditortext"));
			System.out.println("22222222222 ::: " + (String) projectItem.get("eco_after_webeditortext"));
			Kogger.debug("by hooni ::: qms dao ::: " + (String) projectItem.get("ecoid"));
			Kogger.debug("11111111111 ::: " + (String) projectItem.get("ecr_webeditortext"));
			Kogger.debug("22222222222 ::: " + (String) projectItem.get("eco_after_webeditortext"));
			e.printStackTrace();
			conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setDeletePLMEcoPjt 설명 : QMS DB에 PLM ECO 수정
	 * 
	 * @param ArrayList
	 *            ecoList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 07. 24.
	 */
	public int setDeletePLMEcoPjt(ArrayList ecoList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable projectItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;

		sb.append(" DELETE FROM TBL_INF_PLM_OC_DES WHERE eco_no = ? ");

		try {
			for (int i = 0; i < ecoList.size(); i++) {
				projectItem = (Hashtable) ecoList.get(i);
				pstmt = conn.prepareStatement(sb.toString());

				pstmt.setString(1, (String) projectItem.get("ecoid"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_PLM_OC_DES DELETE completed !!");

		} catch (Exception e) {
			e.printStackTrace();
			conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setInsertPLMDqm 설명 : QMS DB에 PLM Dqm 등록
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 11.
	 */
	public int setInsertPLMDqm(ArrayList dqmList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable projectItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;
		sb.append(" INSERT INTO TBL_INF_PLM_OC_DEV(");
		sb.append("project_no, \n");
		sb.append("pjt_name, \n");
		sb.append("statestate, \n");
		// sb.append("statename, \n");
		sb.append("pnum, \n");
		sb.append("pname, \n");
		sb.append("cartype_code, \n");
		sb.append("cartype_name, \n");
		sb.append("dqm_reviewdate, \n");
		sb.append("producttype_code, \n");
		sb.append("producttype_name, \n");
		sb.append("problem_no, \n");
		sb.append("problem_editortext, \n");
		sb.append("reform_webeditortext, \n");
		sb.append("dept_code, \n");
		sb.append("dept_name, \n");
		sb.append("actionuser_code, \n");
		sb.append("actionuser_name, \n");
		sb.append("process_code, \n");
		sb.append("process_name \n");
		sb.append(") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			Kogger.debug("TBL_INF_PLM_OC_DEV insert Start !!!!!!!!!!!!!!");
			for (int i = 0; i < dqmList.size(); i++) {
				projectItem = (Hashtable) dqmList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) projectItem.get("pjtno"));
				pstmt.setString(2, (String) projectItem.get("pjtname"));
				pstmt.setString(3, (String) projectItem.get("statestate"));
				pstmt.setString(4, (String) projectItem.get("pnum"));
				pstmt.setString(5, (String) projectItem.get("pname"));
				pstmt.setString(6, (String) projectItem.get("cartype_code"));
				pstmt.setString(7, (String) projectItem.get("cartype_name"));
				pstmt.setString(8, (String) projectItem.get("dqm_reviewdate"));
				pstmt.setString(9, (String) projectItem.get("producttype_code"));
				pstmt.setString(10, (String) projectItem.get("producttype_name"));
				pstmt.setString(11, (String) projectItem.get("problem_no"));
				pstmt.setString(12, (String) projectItem.get("problem_webeditortext"));
				pstmt.setString(13, (String) projectItem.get("reform_webeditortext"));
				pstmt.setString(14, (String) projectItem.get("dept_code"));
				pstmt.setString(15, (String) projectItem.get("dept_name"));
				pstmt.setString(16, (String) projectItem.get("actionuser_perno"));
				pstmt.setString(17, (String) projectItem.get("actionuser_name"));
				pstmt.setString(18, (String) projectItem.get("process_code"));
				pstmt.setString(19, (String) projectItem.get("process_name"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_PLM_OC_DEV insert Completed !!");
			Kogger.debug("TBL_INF_PLM_OC_DEV insert Completed !!!!!!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setUpdatePLMDqm 설명 : QMS DB에 PLM Dqm Update
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 07. 27.
	 */
	public int setUpdatePLMDqm(ArrayList dqmList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable projectItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;
		sb.append(" UPDATE TBL_INF_PLM_OC_DEV SET ");
		sb.append("project_no = ?, \n");
		sb.append("pjt_name = ?, \n");
		sb.append("statestate = ?, \n");
		sb.append("pnum = ?, \n");
		sb.append("pname = ?, \n");
		sb.append("cartype_code = ?, \n");
		sb.append("cartype_name = ?, \n");
		sb.append("dqm_reviewdate = ?, \n");
		sb.append("producttype_code = ?, \n");
		sb.append("producttype_name = ?, \n");
		sb.append("problem_editortext = ?, \n");
		sb.append("reform_webeditortext = ?, \n");
		sb.append("dept_code = ?, \n");
		sb.append("dept_name = ?, \n");
		sb.append("actionuser_code = ?, \n");
		sb.append("actionuser_name = ?, \n");
		sb.append("process_code = ?, \n");
		sb.append("process_name = ? \n");
		sb.append("WHERE problem_no = ? \n");

		try {
			Kogger.debug("TBL_INF_PLM_OC_DEV Update Start !!!!!!!!!!!!!!");
			for (int i = 0; i < dqmList.size(); i++) {
				projectItem = (Hashtable) dqmList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) projectItem.get("pjtno"));
				pstmt.setString(2, (String) projectItem.get("pjtname"));
				pstmt.setString(3, (String) projectItem.get("statestate"));
				pstmt.setString(4, (String) projectItem.get("pnum"));
				pstmt.setString(5, (String) projectItem.get("pname"));
				pstmt.setString(6, (String) projectItem.get("cartype_code"));
				pstmt.setString(7, (String) projectItem.get("cartype_name"));
				pstmt.setString(8, (String) projectItem.get("dqm_reviewdate"));
				pstmt.setString(9, (String) projectItem.get("producttype_code"));
				pstmt.setString(10, (String) projectItem.get("producttype_name"));
				pstmt.setString(11, (String) projectItem.get("problem_webeditortext"));
				pstmt.setString(12, (String) projectItem.get("reform_webeditortext"));
				pstmt.setString(13, (String) projectItem.get("dept_code"));
				pstmt.setString(14, (String) projectItem.get("dept_name"));
				pstmt.setString(15, (String) projectItem.get("actionuser_perno"));
				pstmt.setString(16, (String) projectItem.get("actionuser_name"));
				pstmt.setString(17, (String) projectItem.get("process_code"));
				pstmt.setString(18, (String) projectItem.get("process_name"));
				pstmt.setString(19, (String) projectItem.get("problem_no"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_PLM_OC_DEV Update Completed !!");
			Kogger.debug("TBL_INF_PLM_OC_DEV Update Completed !!!!!!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setDeletePLMDqm 설명 : QMS DB에 PLM Dqm Delete
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 07. 27.
	 */
	public int setDeletePLMDqm(ArrayList dqmList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable projectItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;
		sb.append(" DELETE FROM TBL_INF_PLM_OC_DEV WHERE problem_no = ? \n");

		try {
			for (int i = 0; i < dqmList.size(); i++) {
				projectItem = (Hashtable) dqmList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) projectItem.get("problem_no"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_PLM_OC_DEV Delete Completed !!");
		} catch (Exception e) {
			e.printStackTrace();
			conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setInsertPLMReq 설명 : QMS DB에 PLM req 등록
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 11.
	 */
	public int setInsertPLMReq(ArrayList dqmList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable reqItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;
		sb.append(" INSERT INTO TBL_INF_TEST_REQ(");
		sb.append("ida2a2, \n");
		sb.append("pjt_no, \n");
		sb.append("pjt_name, \n");
		sb.append("statestate, \n");
		// sb.append("statename, \n");
		sb.append("task_name, \n");
		sb.append("cartype_code, \n");
		sb.append("cartype_name, \n");
		sb.append("producttype_code, \n");
		sb.append("producttype_name, \n");
		sb.append("dept_code, \n");
		sb.append("dept_name, \n");
		sb.append("account_no, \n");
		sb.append("pm_name, \n");
		sb.append("role_code, \n");
		sb.append("role_name, \n");
		sb.append("rolemember_code, \n");
		sb.append("rolemember_name, \n");
		sb.append("pnum, \n");
		sb.append("pname, \n");
		sb.append("subcontractor_code, \n");
		sb.append("subcontractor_name, \n");
		sb.append("oem1plan, \n");
		sb.append("oem2plan, \n");
		sb.append("oemSOPplan, \n");
		sb.append("gate4date, \n");
		sb.append("planstartdate, \n");
		sb.append("planenddate \n");
		sb.append(") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			Kogger.debug("TBL_INF_TEST_REQ insert Start !!!!!!!!!!");
			for (int i = 0; i < dqmList.size(); i++) {
				reqItem = (Hashtable) dqmList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) reqItem.get("ida2a2"));
				pstmt.setString(2, (String) reqItem.get("pjtno"));
				pstmt.setString(3, (String) reqItem.get("pjtname"));
				pstmt.setString(4, (String) reqItem.get("statestate"));
				pstmt.setString(5, (String) reqItem.get("task_name"));
				pstmt.setString(6, (String) reqItem.get("cartype_code"));
				pstmt.setString(7, (String) reqItem.get("cartype_name"));
				pstmt.setString(8, (String) reqItem.get("producttype_code"));
				pstmt.setString(9, (String) reqItem.get("producttype_name"));
				pstmt.setString(10, (String) reqItem.get("dept_code"));
				pstmt.setString(11, (String) reqItem.get("dept_name"));
				pstmt.setString(12, (String) reqItem.get("account_no"));
				pstmt.setString(13, (String) reqItem.get("pm_name"));
				pstmt.setString(14, (String) reqItem.get("role_code"));
				pstmt.setString(15, (String) reqItem.get("role_name"));
				pstmt.setString(16, (String) reqItem.get("rolemember_code"));
				pstmt.setString(17, (String) reqItem.get("rolemember_name"));
				pstmt.setString(18, (String) reqItem.get("pnum"));
				pstmt.setString(19, (String) reqItem.get("pname"));
				pstmt.setString(20, (String) reqItem.get("subcontractor_code"));
				pstmt.setString(21, (String) reqItem.get("subcontractor_name"));
				pstmt.setString(22, (String) reqItem.get("oem1plan"));
				pstmt.setString(23, (String) reqItem.get("oem2plan"));
				pstmt.setString(24, (String) reqItem.get("oemSOPplan"));
				pstmt.setString(25, (String) reqItem.get("gate4date"));
				pstmt.setString(26, (String) reqItem.get("planstartdate"));
				pstmt.setString(27, (String) reqItem.get("planenddate"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_TEST_REQ insert Completed !!");
			Kogger.debug("TBL_INF_TEST_REQ insert Completed !!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			// conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setInsertPLMReq 설명 : QMS DB에 PLM req 등록
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 11.
	 */
	public int setInsertPLMDocument(ArrayList documentList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable documentItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;
		sb.append(" INSERT INTO TBL_INF_INLINE_PLM_DOCUMENT(");
		sb.append("qms_no, \n");
		sb.append("document_no, \n");
		sb.append("document_name, \n");
		sb.append("version_info, \n");
		sb.append("approvedate \n");
		sb.append(") VALUES(?, ?, ?, ?, ?)");

		try {
			Kogger.debug("TBL_INF_INLINE_PLM_DOCUMENT insert Start !!!!!!!!!!");
			for (int i = 0; i < documentList.size(); i++) {
				documentItem = (Hashtable) documentList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) documentItem.get("qms_no"));
				pstmt.setString(2, (String) documentItem.get("document_no"));
				pstmt.setString(3, (String) documentItem.get("document_name"));
				pstmt.setString(4, (String) documentItem.get("version_info"));
				pstmt.setString(5, (String) documentItem.get("approvedate"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_INLINE_PLM_DOCUMENT insert Completed !!");
			Kogger.debug("TBL_INF_INLINE_PLM_DOCUMENT insert Completed !!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			// conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setInsertPLMReq 설명 : QMS DB에 PLM req 등록
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 11.
	 */
	public int setInsertPLMEco(ArrayList ecoList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable ecoItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;
		sb.append(" INSERT INTO TBL_INF_INLINE_COMPLAINT_eco(");
		sb.append("eco_no, \n");
		sb.append("eco_name, \n");
		sb.append("pjt_no, \n");
		sb.append("pnum, \n");
		sb.append("pname, \n");
		sb.append("create_date \n");
		sb.append(") VALUES(?, ?, ?, ?, ?, ?)");

		try {
			Kogger.debug("TBL_INF_INLINE_COMPLAINT_eco insert Start !!!!!!!!!!");
			for (int i = 0; i < ecoList.size(); i++) {
				ecoItem = (Hashtable) ecoList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) ecoItem.get("ecoid"));
				pstmt.setString(2, (String) ecoItem.get("econame"));
				pstmt.setString(3, (String) ecoItem.get("pjtno"));
				pstmt.setString(4, (String) ecoItem.get("pnum"));
				pstmt.setString(5, (String) ecoItem.get("pname"));
				pstmt.setString(6, (String) ecoItem.get("create_date"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_INLINE_COMPLAINT_eco insert Completed !!");
			Kogger.debug("TBL_INF_INLINE_COMPLAINT_eco insert Completed !!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			// conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setInsertPLMReq 설명 : QMS DB에 PLM req 등록
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 11.
	 */
	public int setInsertPLMEcr(ArrayList ecrList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable ecoItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;
		sb.append(" INSERT INTO TBL_INF_INLINE_COMPLAINT_ecr(");
		sb.append("ecr_no, \n");
		sb.append("ecr_name, \n");
		sb.append("pjt_no, \n");
		sb.append("pnum, \n");
		sb.append("pname, \n");
		sb.append("create_date \n");
		sb.append(") VALUES(?, ?, ?, ?, ?, ?)");

		try {
			Kogger.debug("TBL_INF_INLINE_COMPLAINT_ecr insert Start !!!!!!!!!!");
			for (int i = 0; i < ecrList.size(); i++) {
				ecoItem = (Hashtable) ecrList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) ecoItem.get("ecrid"));
				pstmt.setString(2, (String) ecoItem.get("ecrname"));
				pstmt.setString(3, (String) ecoItem.get("pjtno"));
				pstmt.setString(4, (String) ecoItem.get("pnum"));
				pstmt.setString(5, (String) ecoItem.get("pname"));
				pstmt.setString(6, (String) ecoItem.get("create_date"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_INLINE_COMPLAINT_ecr insert Completed !!");
			Kogger.debug("TBL_INF_INLINE_COMPLAINT_ecr insert Completed !!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			// conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}

	/**
	 * 함수명 : setInsertPLMReq 설명 : QMS DB에 PLM req 등록
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 11.
	 */
	public int setInsertPLMCar(ArrayList oemList) throws Exception {
		ArrayList returnItemList = new ArrayList();

		Hashtable oemItem = null;

		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		int complet = 0;
		sb.append(" INSERT INTO TBL_INF_PLM_CAR(car_code, car_name) VALUES(?, ?)");

		try {
			Kogger.debug("TBL_INF_PLM_CAR insert Start !!!!!!!!!!");
			for (int i = 0; i < oemList.size(); i++) {
				oemItem = (Hashtable) oemList.get(i);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, (String) oemItem.get("code"));
				pstmt.setString(2, (String) oemItem.get("name"));
				complet = pstmt.executeUpdate();
			}
			System.out.println("TBL_INF_PLM_CAR insert Completed !!");
			Kogger.debug("TBL_INF_PLM_CAR insert Completed !!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			// conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				// conn.close();
			}
		}
		return complet;
	}

	/**
	 * 함수명 : getPLMEcoSearch 설명 : QMS DB에 PLM eco 조회
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 11.
	 */
	public ArrayList getPLMEcoSearchList(ArrayList ecoList) throws Exception {
		ArrayList<Hashtable<String, String>> ecoSList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> ecoHash = null;
		Hashtable ecoItem = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		int complet = 0;
		sb.append(" select A.ecoOid as ecoOid from ( \n");
		for (int i = 0; i < ecoList.size(); i++) {
			ecoItem = (Hashtable) ecoList.get(i);
			if (i == (ecoList.size() - 1)) {
				sb.append(" select '" + (String) ecoItem.get("ecoid") + "' as eco_no, '" + (String) ecoItem.get("ecoOid") + "' as ecoOid \n");
			} else {
				sb.append(" select '" + (String) ecoItem.get("ecoid") + "' as eco_no, '" + (String) ecoItem.get("ecoOid") + "' as ecoOid union \n");
			}
		}
		sb.append(" ) A \n");
		sb.append(" WHERE A.eco_no not in ( SELECT B.eco_no FROM tbl_inf_plm_oc_des B group by B.eco_no) \n");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ecoHash = new Hashtable<String, String>();
				ecoHash.put("ecoOid", StringUtil.checkNull(rs.getString("ecoOid")));
			}
			System.out.println("tbl_inf_plm_oc_des Search OK !!");
		} catch (Exception e) {
			e.printStackTrace();
			// conn.close();
		} finally {
			sb.delete(0, sb.length());
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				// conn.close();
			}
		}
		return ecoSList;
	}

	/**
	 * 함수명 : setInsertPLMReq 설명 : QMS DB에 PLM req 등록
	 * 
	 * @param ArrayList
	 *            dqmList
	 * @return int
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 06. 11.
	 */
	public int setDeletePLM() throws Exception {

		PreparedStatement pstmt = null;
		StringBuffer sbPjt = new StringBuffer();
		StringBuffer sbEcoPjt = new StringBuffer();
		StringBuffer sbDqm = new StringBuffer();
		StringBuffer sbReq = new StringBuffer();
		StringBuffer sbEco = new StringBuffer();
		StringBuffer sbEcr = new StringBuffer();
		StringBuffer sbDocument = new StringBuffer();
		StringBuffer sbCarType = new StringBuffer();
		int complet = 0;

		sbPjt.append("DELETE FROM TBL_INF_PLM_PJT ");
		sbEcoPjt.append("DELETE FROM TBL_INF_PLM_OC_DES; ");
		sbDqm.append("DELETE FROM TBL_INF_PLM_OC_DEV; ");
		sbEcr.append("DELETE FROM TBL_INF_INLINE_COMPLAINT_ecr; ");
		sbEco.append("DELETE FROM TBL_INF_INLINE_COMPLAINT_eco; ");
		sbDocument.append("DELETE FROM TBL_INF_INLINE_PLM_DOCUMENT; ");
		sbReq.append("DELETE FROM TBL_INF_TEST_REQ; ");
		sbCarType.append("DELETE FROM TBL_INF_PLM_CAR; ");

		try {
			pstmt = conn.prepareStatement(sbPjt.toString());
			pstmt.executeUpdate();

			// pstmt = null;
			// pstmt = conn.prepareStatement(sbEcoPjt.toString());
			// pstmt.executeUpdate();
			//
			// pstmt = null;
			// pstmt = conn.prepareStatement(sbDqm.toString());
			// pstmt.executeUpdate();

			// pstmt = null;
			// pstmt = conn.prepareStatement(sbDqm.toString());
			// pstmt.executeUpdate();

			pstmt = null;
			pstmt = conn.prepareStatement(sbEcr.toString());
			pstmt.executeUpdate();

			pstmt = null;
			pstmt = conn.prepareStatement(sbEco.toString());
			pstmt.executeUpdate();

			pstmt = null;
			pstmt = conn.prepareStatement(sbDocument.toString());
			pstmt.executeUpdate();

			pstmt = null;
			pstmt = conn.prepareStatement(sbReq.toString());
			pstmt.executeUpdate();

			pstmt = null;
			pstmt = conn.prepareStatement(sbCarType.toString());
			pstmt.executeUpdate();

			System.out.println("Delete Completed !!");
		} catch (Exception e) {
			e.printStackTrace();
			// conn.close();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			// if (conn != null) {
			// conn.close();
			// }
		}
		return complet;
	}
}
