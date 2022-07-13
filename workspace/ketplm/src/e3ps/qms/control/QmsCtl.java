package e3ps.qms.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.util.DBUtil;
import e3ps.qms.dao.PlmDao;
import e3ps.qms.dao.QmsDao;
import e3ps.qms.util.QMSDBUtil;
import ext.ket.shared.log.Kogger;

public class QmsCtl {
	/**
	 * 함수명 : PlmProjectList 설명 : PLM 프로젝트 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 05.
	 */

	public ArrayList PlmProjectList() {
		// connection
		Connection conn = null;

		ArrayList projectList = new ArrayList();
		try {
			// connection creation
			conn = DBUtil.getConnection();
			PlmDao plmDao = new PlmDao(conn);

			// result
			projectList = plmDao.getPlmProjectList();

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}

		return projectList;
	}

	/**
	 * 함수명 : ECOList 설명 : PLM ECO 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 11.
	 */

	public ArrayList PlmECOPjtList(String attrEcoOid) {
		// connection
		Connection conn = null;

		ArrayList ecoPjtList = new ArrayList();
		try {
			// connection creation
			conn = DBUtil.getConnection();
			PlmDao plmDao = new PlmDao(conn);

			// result
			ecoPjtList = plmDao.getPlmEcoPjtList(attrEcoOid);

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}

		return ecoPjtList;
	}

	/**
	 * 함수명 : dqmList 설명 : PLM 품질관리 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 11.
	 */

	public ArrayList PlmDqmList(String attrDqmOid) {
		// connection
		Connection conn = null;

		ArrayList dqmList = new ArrayList();
		try {
			// connection creation
			conn = DBUtil.getConnection();
			PlmDao plmDao = new PlmDao(conn);

			// result
			dqmList = plmDao.getPlmDqmList(attrDqmOid);

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}

		return dqmList;
	}

	/**
	 * 함수명 : PlmReqList 설명 : PLM 시험의뢰 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 16.
	 */

	public ArrayList PlmReqList() {
		// connection
		Connection conn = null;

		ArrayList reqList = new ArrayList();
		try {
			// connection creation
			conn = DBUtil.getConnection();
			PlmDao plmDao = new PlmDao(conn);

			// result
			reqList = plmDao.getPlmReqList();

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}

		return reqList;
	}

	/**
	 * 함수명 : PlmDocumentList 설명 : PLM 품질문서 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 19.
	 */

	public ArrayList PlmDocumentList() {
		// connection
		Connection conn = null;

		ArrayList documentList = new ArrayList();
		try {
			// connection creation
			conn = DBUtil.getConnection();
			PlmDao plmDao = new PlmDao(conn);

			// result
			documentList = plmDao.getPlmDocumentList();

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}

		return documentList;
	}

	/**
	 * 함수명 : PlmEcrList 설명 : PLM 품질문서 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 19.
	 */

	public ArrayList PlmEcrList() {
		// connection
		Connection conn = null;

		ArrayList ecrList = new ArrayList();
		try {
			// connection creation
			conn = DBUtil.getConnection();
			PlmDao plmDao = new PlmDao(conn);

			// result
			ecrList = plmDao.getPlmEcrList();

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}

		return ecrList;
	}

	/**
	 * 함수명 : PlmEcrList 설명 : PLM 품질문서 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 19.
	 */

	public ArrayList PlmEcoList() {
		// connection
		Connection conn = null;

		ArrayList ecoList = new ArrayList();
		try {
			// connection creation
			conn = DBUtil.getConnection();
			PlmDao plmDao = new PlmDao(conn);

			// result
			ecoList = plmDao.getPlmEcoList();

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}

		return ecoList;
	}

	/**
	 * 함수명 : PlmOEMList 설명 : PLM 품질문서 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 19.
	 */

	public ArrayList PlmOEMList() {
		// connection
		Connection conn = null;

		ArrayList ecoList = new ArrayList();
		try {
			// connection creation
			conn = DBUtil.getConnection();
			PlmDao plmDao = new PlmDao(conn);

			// result
			ecoList = plmDao.getPlmOEMCarTypeList();

		} catch (Exception e) {
			e.printStackTrace();
			// }finally{
			// DBUtil.close(conn);
		}

		return ecoList;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB TBL_INF_PLM_OC_DES Table에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 07. 24.
	 */
	public int InsertPLMEcoPjt(String attrOid, String gubun) throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;

		String oid = attrOid.substring(attrOid.indexOf(":") + 1, attrOid.length());

		Kogger.info(getClass(), "[completeActivity][PRECOMPLETE Action] Qms Interface oid ::: " + oid);

		ArrayList ecoPjtList = new ArrayList();
		ArrayList dqmList = new ArrayList();
		ecoPjtList = PlmECOPjtList(oid);
		dqmList = PlmDqmList(oid);

		Kogger.info(getClass(), "[completeActivity][PRECOMPLETE Action] Qms Interface ecoPjtList.size ::: " + ecoPjtList.size());
		Kogger.info(getClass(), "[completeActivity][PRECOMPLETE Action] Qms Interface dqmList.size ::: " + dqmList.size());

		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);
			if (ecoPjtList.size() > 0) {
				// result
				if ("C".equals(gubun)) {
					complete = qmsDao.setInsertPLMEcoPjt(ecoPjtList);
				} else if ("U".equals(gubun)) {
					complete = qmsDao.setUpdatePLMEcoPjt(ecoPjtList);
				} else {
					complete = qmsDao.setDeletePLMEcoPjt(ecoPjtList);
				}
			} else if (dqmList.size() > 0) {
				if ("C".equals(gubun)) {
					complete = qmsDao.setInsertPLMDqm(dqmList);
				} else if ("U".equals(gubun)) {
					complete = qmsDao.setUpdatePLMDqm(dqmList);
				} else {
					complete = qmsDao.setDeletePLMDqm(dqmList);
				}
			} else {
				complete = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			DBUtil.close(conn);
		} finally {
			conn.close();
		}

		return complete;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB TBL_INF_PLM_OC_DES Table에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 07. 24.
	 */
	public int InsertPLMEcoPjt1(String attrOid, String gubun) throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;

		String oid = attrOid.substring(attrOid.indexOf(":") + 1, attrOid.length());

		ArrayList ecoPjtList = new ArrayList();
		ecoPjtList = PlmECOPjtList(oid);

		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);
			// result
			if ("C".equals(gubun)) {
				complete = qmsDao.setInsertPLMEcoPjt(ecoPjtList);
				// } else if ("U".equals(gubun)) {
				// complete = qmsDao.setUpdatePLMEcoPjt(ecoPjtList);
				// } else {
				// complete = qmsDao.setDeletePLMEcoPjt(ecoPjtList);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// DBUtil.close(conn);
		} finally {
			DBUtil.close(conn);
		}

		return complete;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB TBL_INF_PLM_OC_DES Table에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 07. 24.
	 */
	public int InsertPLMDqmPjt(String attrDqmOid, String gubun) throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;
		ArrayList dqmList = new ArrayList();
		dqmList = PlmDqmList(attrDqmOid);
		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);

			// result
			complete = 0;
			if ("C".equals(gubun)) {
				complete = qmsDao.setInsertPLMDqm(dqmList);
			} else if ("U".equals(gubun)) {
				complete = qmsDao.setUpdatePLMDqm(dqmList);
			} else {
				complete = qmsDao.setDeletePLMDqm(dqmList);
			}

		} catch (Exception e) {
			e.printStackTrace();
			DBUtil.close(conn);
		} finally {
			conn.close();
		}

		return complete;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 03.
	 */
	public int InsertPLMPjt() throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;
		ArrayList projectList = new ArrayList();
		projectList = PlmProjectList();
		System.out.println("by hooni :::                     join ?????????????????????");

		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);

			// result
			// qmsDao.setDeletePLM();
			complete = qmsDao.setInsertPLMProject(projectList);

		} catch (Exception e) {
			e.printStackTrace();
			DBUtil.close(conn);
		} finally {
			conn.close();
		}

		return complete;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 03.
	 */
	public int InsertPLMReq() throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;

		ArrayList reqList = new ArrayList();
		reqList = PlmReqList();

		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);

			// result
			complete = qmsDao.setInsertPLMReq(reqList);

			System.out.println("TBL_INF Completed!!!");
		} catch (Exception e) {
			e.printStackTrace();
			DBUtil.close(conn);
		} finally {
			conn.close();
		}

		return complete;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 03.
	 */
	public int InsertPLMDoc() throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;

		ArrayList documentList = new ArrayList();
		documentList = PlmDocumentList();

		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);

			// result

			complete = qmsDao.setInsertPLMDocument(documentList);

		} catch (Exception e) {
			e.printStackTrace();
			DBUtil.close(conn);
		} finally {
			conn.close();
		}

		return complete;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 03.
	 */
	public int InsertPLMEco1() throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;

		ArrayList ecoList = new ArrayList();
		ecoList = PlmEcoList();

		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);

			// result

			complete = qmsDao.setInsertPLMEco(ecoList);

		} catch (Exception e) {
			e.printStackTrace();
			DBUtil.close(conn);
		} finally {
			conn.close();
		}

		return complete;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 03.
	 */
	public int InsertPLMEcr1() throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;
		ArrayList ecrList = new ArrayList();
		ecrList = PlmEcrList();

		ArrayList OEMList = new ArrayList();
		OEMList = PlmOEMList();

		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);

			// result

			complete = qmsDao.setInsertPLMEcr(ecrList);

		} catch (Exception e) {
			e.printStackTrace();
			DBUtil.close(conn);
		} finally {
			conn.close();
		}

		return complete;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 03.
	 */
	public int InsertPLMOEM() throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;

		ArrayList OEMList = new ArrayList();
		OEMList = PlmOEMList();

		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);

			// result
			qmsDao.setDeletePLM();

			complete = qmsDao.setInsertPLMCar(OEMList);
			System.out.println("TBL_INF Completed!!!");

		} catch (Exception e) {
			e.printStackTrace();
			DBUtil.close(conn);
		} finally {
			conn.close();
		}

		return complete;
	}

	/**
	 * 함수명 : InsertPLMProject 설명 : qms DB에 Insert
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 03.
	 */
	public int InsertPLM() throws SQLException {
		// connection
		Connection conn = null;
		int complete = 0;
		ArrayList projectList = new ArrayList();
		projectList = PlmProjectList();

		// ArrayList ecoPjtList = new ArrayList();
		// ecoPjtList = PlmECOPjtList("");
		//
		// ArrayList dqmList = new ArrayList();
		// dqmList = PlmDqmList("");

		ArrayList reqList = new ArrayList();
		reqList = PlmReqList();

		ArrayList documentList = new ArrayList();
		documentList = PlmDocumentList();

		ArrayList ecoList = new ArrayList();
		ecoList = PlmEcoList();

		ArrayList ecrList = new ArrayList();
		ecrList = PlmEcrList();

		ArrayList OEMList = new ArrayList();
		OEMList = PlmOEMList();

		try {
			// connection creation
			conn = QMSDBUtil.getConnection(false);
			QmsDao qmsDao = new QmsDao(conn);

			// result
			qmsDao.setDeletePLM();
			complete = qmsDao.setInsertPLMProject(projectList);

			if (complete > 0) {
				// complete = 0;
				// complete = qmsDao.setInsertPLMEcoPjt(ecoPjtList);
				// if (complete == 1) {
				// complete = 0;
				// complete = qmsDao.setInsertPLMDqm(dqmList);
				// if (complete == 1) {
				complete = 0;
				complete = qmsDao.setInsertPLMEco(ecoList);
				System.out.println("PLM ECO complet ::: " + complete);
				Kogger.debug("PLM ECO complet ::: " + complete);
				if (complete > 0) {
					complete = 0;
					complete = qmsDao.setInsertPLMEcr(ecrList);
					System.out.println("PLM ECR complet ::: " + complete);
					Kogger.debug("PLM ECR complet ::: " + complete);
					if (complete > 0) {
						complete = 0;
						complete = qmsDao.setInsertPLMDocument(documentList);
						System.out.println("PLM DOC complet ::: " + complete);
						Kogger.debug("PLM DOC complet ::: " + complete);
						if (complete > 0) {
							complete = 0;
							complete = qmsDao.setInsertPLMReq(reqList);
							System.out.println("PLM REQ complet ::: " + complete);
							Kogger.debug("PLM REQ complet ::: " + complete);
							if (complete > 0) {
								complete = 0;
								complete = qmsDao.setInsertPLMCar(OEMList);
								System.out.println("PLM CAR complet ::: " + complete);
								Kogger.debug("PLM CAR complet ::: " + complete);
								System.out.println("TBL_INF Completed!!!");
								Kogger.debug("TBL_INF Completed!!!");
								// }
								// }

							}
						}
					}
				}
			}

			if (complete > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			qmsEcoInsertCheck();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			QMSDBUtil.close(conn);
		}

		return complete;
	}

	/**
	 * 함수명 : DeletePLM 설명 : qms DB에 Insert
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2012. 06. 03.
	 */
	public int DeletePLM() {
		// connection
		Connection conn = null;
		int complete = 0;
		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);

			// result
			complete = qmsDao.setDeletePLM();
		} catch (Exception e) {
			e.printStackTrace();
			DBUtil.close(conn);
			// } finally {
			// DBUtil.close(conn);
		}

		return complete;
	}

	/**
	 * 함수명 : qmsEcoSearchList 설명 : PLM 프로젝트 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 10. 21.
	 */

	public int qmsEcoInsertCheck() {
		ArrayList plmEcoList = plmEcoSearchList();
		ArrayList qmsEcoList = qmsEcoSearchList(plmEcoList);
		Hashtable ecoItem = null;
		int completed = 0;
		try {
			for (int i = 0; i < qmsEcoList.size(); i++) {
				ecoItem = (Hashtable) qmsEcoList.get(i);
				completed = InsertPLMEcoPjt((String) ecoItem.get("ecoOid"), "C");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return completed;
	}

	/**
	 * 함수명 : qmsEcoSearchList 설명 : PLM 프로젝트 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 10. 21.
	 */

	public ArrayList qmsEcoSearchList(ArrayList ecoList) {
		// connection
		Connection conn = null;

		ArrayList returnEcoList = new ArrayList();
		try {
			// connection creation
			conn = QMSDBUtil.getConnection();
			QmsDao qmsDao = new QmsDao(conn);

			// result
			returnEcoList = qmsDao.getPLMEcoSearchList(ecoList);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}

		return returnEcoList;
	}

	/**
	 * 함수명 : plmEcoSearchList 설명 : PLM 프로젝트 관련 정보
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @throws 작성자
	 *             : 엄태훈 작성일자 : 2015. 10. 21.
	 */

	public ArrayList plmEcoSearchList() {
		// connection
		Connection conn = null;

		ArrayList ecoList = new ArrayList();
		try {
			// connection creation
			conn = DBUtil.getConnection();
			PlmDao plmDao = new PlmDao(conn);

			// result
			ecoList = plmDao.getThisDayPlmEcoList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}

		return ecoList;
	}
}