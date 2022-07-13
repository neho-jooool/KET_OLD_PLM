package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import wt.part.WTPart;
import e3ps.common.util.CommonUtil;
import e3ps.cost.entity.PartBomInfoDTO;
import e3ps.cost.service.KetCostInfoHelper;
import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;
import e3ps.project.beans.ProductHelper;
import ext.ket.part.base.service.PartBaseHelper;

public class CostComDao {

    private Connection conn;
    String search_type = "";
    String plm_data = "";
    int Rcount = 0;

    public CostComDao(Connection conn) {
	this.conn = conn;
    }

    public CostComDao() {
	super();
    }

    /**
     * 함수명 : getCuttingList 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2011. 11. 21.
     */
    public ArrayList getCuttingList() throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT PK_CUT,     MET_TYPE,   TO_CHAR(MET_W) MET_W,      C_SIZE_T_1, C_SIZE_T_2, C_SIZE_T_3,");
	sb.append("        C_SIZE_T_4, C_SIZE_T_5, C_SIZE_T_6,    C_SIZE_T_7, C_SIZE_T_8, C_SIZE_T_9");
	sb.append("   FROM CUTTING_COST");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    // pstmt.setString(1, gubun);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("PK_CUT", StringUtil.checkNull(rs.getString("PK_CUT")));
		SearchMap.put("MET_TYPE", StringUtil.checkNull(rs.getString("MET_TYPE")));
		SearchMap.put("MET_W", StringUtil.checkNull(rs.getString("MET_W")));
		SearchMap.put("C_SIZE_T_1", StringUtil.checkNull(rs.getString("C_SIZE_T_1")));
		SearchMap.put("C_SIZE_T_2", StringUtil.checkNull(rs.getString("C_SIZE_T_2")));
		SearchMap.put("C_SIZE_T_3", StringUtil.checkNull(rs.getString("C_SIZE_T_3")));
		SearchMap.put("C_SIZE_T_4", StringUtil.checkNull(rs.getString("C_SIZE_T_4")));
		SearchMap.put("C_SIZE_T_5", StringUtil.checkNull(rs.getString("C_SIZE_T_5")));
		SearchMap.put("C_SIZE_T_6", StringUtil.checkNull(rs.getString("C_SIZE_T_6")));
		SearchMap.put("C_SIZE_T_7", StringUtil.checkNull(rs.getString("C_SIZE_T_7")));
		SearchMap.put("C_SIZE_T_8", StringUtil.checkNull(rs.getString("C_SIZE_T_8")));
		SearchMap.put("C_SIZE_T_9", StringUtil.checkNull(rs.getString("C_SIZE_T_9")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    public int CuttingCreate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String MET_TYPE = null;
	String MET_W = null;
	String C_SIZE_T_1 = null;
	String C_SIZE_T_2 = null;
	String C_SIZE_T_3 = null;
	String C_SIZE_T_4 = null;
	String C_SIZE_T_5 = null;
	String C_SIZE_T_6 = null;
	String C_SIZE_T_7 = null;
	String C_SIZE_T_8 = null;
	String C_SIZE_T_9 = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    MET_TYPE = (String) SearchMap.get("met_type");
	    MET_W = (String) SearchMap.get("met_w");
	    C_SIZE_T_1 = (String) SearchMap.get("c_size_t_1");
	    C_SIZE_T_2 = (String) SearchMap.get("c_size_t_2");
	    C_SIZE_T_3 = (String) SearchMap.get("c_size_t_3");
	    C_SIZE_T_4 = (String) SearchMap.get("c_size_t_4");
	    C_SIZE_T_5 = (String) SearchMap.get("c_size_t_5");
	    C_SIZE_T_6 = (String) SearchMap.get("c_size_t_6");
	    C_SIZE_T_7 = (String) SearchMap.get("c_size_t_7");
	    C_SIZE_T_8 = (String) SearchMap.get("c_size_t_8");
	    C_SIZE_T_9 = (String) SearchMap.get("c_size_t_9");

	    sb.append(" INSERT INTO CUTTING_COST (PK_CUT, MET_TYPE, MET_W, C_SIZE_T_1,C_SIZE_T_2,C_SIZE_T_3,C_SIZE_T_4,  ");
	    sb.append("                           C_SIZE_T_5,C_SIZE_T_6,C_SIZE_T_7,C_SIZE_T_8,C_SIZE_T_9)");
	    sb.append("   VALUES ((SELECT MAX(PK_CUT)+1 FROM CUTTING_COST),?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, MET_TYPE);
		pstmt.setString(2, MET_W);
		pstmt.setString(3, C_SIZE_T_1);
		pstmt.setString(4, C_SIZE_T_2);
		pstmt.setString(5, C_SIZE_T_3);
		pstmt.setString(6, C_SIZE_T_4);
		pstmt.setString(7, C_SIZE_T_5);
		pstmt.setString(8, C_SIZE_T_6);
		pstmt.setString(9, C_SIZE_T_7);
		pstmt.setString(10, C_SIZE_T_8);
		pstmt.setString(11, C_SIZE_T_9);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    }
	}
	return complet;
    }

    public int CuttingUpdate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String PK_CUT = null;
	String MET_TYPE = null;
	String MET_W = null;
	String C_SIZE_T_1 = null;
	String C_SIZE_T_2 = null;
	String C_SIZE_T_3 = null;
	String C_SIZE_T_4 = null;
	String C_SIZE_T_5 = null;
	String C_SIZE_T_6 = null;
	String C_SIZE_T_7 = null;
	String C_SIZE_T_8 = null;
	String C_SIZE_T_9 = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    PK_CUT = (String) SearchMap.get("pk_cut");
	    MET_TYPE = (String) SearchMap.get("met_type");
	    MET_W = (String) SearchMap.get("met_w");
	    C_SIZE_T_1 = (String) SearchMap.get("c_size_t_1");
	    C_SIZE_T_2 = (String) SearchMap.get("c_size_t_2");
	    C_SIZE_T_3 = (String) SearchMap.get("c_size_t_3");
	    C_SIZE_T_4 = (String) SearchMap.get("c_size_t_4");
	    C_SIZE_T_5 = (String) SearchMap.get("c_size_t_5");
	    C_SIZE_T_6 = (String) SearchMap.get("c_size_t_6");
	    C_SIZE_T_7 = (String) SearchMap.get("c_size_t_7");
	    C_SIZE_T_8 = (String) SearchMap.get("c_size_t_8");
	    C_SIZE_T_9 = (String) SearchMap.get("c_size_t_9");

	    sb.append(" UPDATE CUTTING_COST");
	    sb.append("    SET MET_TYPE = ?,   MET_W = ?,      C_SIZE_T_1 = ?, C_SIZE_T_2 = ?, C_SIZE_T_3 = ?, C_SIZE_T_4 = ?");
	    sb.append("       ,C_SIZE_T_5 = ?, C_SIZE_T_6 = ?, C_SIZE_T_7 = ?, C_SIZE_T_8 = ?, C_SIZE_T_9 = ?");
	    sb.append("  WHERE PK_CUT = ?");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, MET_TYPE);
		pstmt.setString(2, MET_W);
		pstmt.setString(3, C_SIZE_T_1);
		pstmt.setString(4, C_SIZE_T_2);
		pstmt.setString(5, C_SIZE_T_3);
		pstmt.setString(6, C_SIZE_T_4);
		pstmt.setString(7, C_SIZE_T_5);
		pstmt.setString(8, C_SIZE_T_6);
		pstmt.setString(9, C_SIZE_T_7);
		pstmt.setString(10, C_SIZE_T_8);
		pstmt.setString(11, C_SIZE_T_9);
		pstmt.setString(12, PK_CUT);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    }
	}
	return complet;
    }

    public int CuttingDelete(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String PK_CUT = null;
	String MET_TYPE = null;
	String MET_W = null;
	String C_SIZE_T_1 = null;
	String C_SIZE_T_2 = null;
	String C_SIZE_T_3 = null;
	String C_SIZE_T_4 = null;
	String C_SIZE_T_5 = null;
	String C_SIZE_T_6 = null;
	String C_SIZE_T_7 = null;
	String C_SIZE_T_8 = null;
	String C_SIZE_T_9 = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    PK_CUT = (String) SearchMap.get("pk_cut");

	    sb.append(" DELETE CUTTING_COST");
	    sb.append("  WHERE PK_CUT = ?");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, PK_CUT);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    }
	}
	return complet;
    }

    /**
     * 함수명 : getPlatingList 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2011. 11. 21.
     */
    public ArrayList getPlatingList() throws Exception {
	ArrayList<Hashtable<String, String>> platingList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> plating = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT PK_PLC, MET_TYPE, P_SIZE_T_1, P_SIZE_T_2, P_SIZE_T_3, P_SIZE_T_4");
	sb.append("   FROM PLATING_COST");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    // pstmt.setString(1, gubun);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		plating = new Hashtable<String, String>();
		plating.put("PK_PLC", StringUtil.checkNull(rs.getString("PK_PLC")));
		plating.put("MET_TYPE", StringUtil.checkNull(rs.getString("MET_TYPE")));
		plating.put("P_SIZE_T_1", StringUtil.checkNull(rs.getString("P_SIZE_T_1")));
		plating.put("P_SIZE_T_2", StringUtil.checkNull(rs.getString("P_SIZE_T_2")));
		plating.put("P_SIZE_T_3", StringUtil.checkNull(rs.getString("P_SIZE_T_3")));
		plating.put("P_SIZE_T_4", StringUtil.checkNull(rs.getString("P_SIZE_T_4")));

		platingList.add(plating);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return platingList;
    }

    public int platingCreate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String MET_TYPE = null;
	String P_SIZE_T_1 = null;
	String P_SIZE_T_2 = null;
	String P_SIZE_T_3 = null;
	String P_SIZE_T_4 = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    MET_TYPE = (String) SearchMap.get("met_type");
	    P_SIZE_T_1 = (String) SearchMap.get("p_size_t_1");
	    P_SIZE_T_2 = (String) SearchMap.get("p_size_t_2");
	    P_SIZE_T_3 = (String) SearchMap.get("p_size_t_3");
	    P_SIZE_T_4 = (String) SearchMap.get("p_size_t_4");

	    sb.append(" INSERT INTO PLATING_COST (PK_PLC, MET_TYPE, P_SIZE_T_1,P_SIZE_T_2,P_SIZE_T_3,P_SIZE_T_4) ");
	    sb.append("   VALUES ((SELECT MAX(PK_PLC)+1 FROM PLATING_COST),?, ?, ?, ?, ?)");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, MET_TYPE);
		pstmt.setString(2, P_SIZE_T_1);
		pstmt.setString(3, P_SIZE_T_2);
		pstmt.setString(4, P_SIZE_T_3);
		pstmt.setString(5, P_SIZE_T_4);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    }
	}
	return complet;
    }

    public int PlatingUpdate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String PK_PLC = null;
	String MET_TYPE = null;
	String P_SIZE_T_1 = null;
	String P_SIZE_T_2 = null;
	String P_SIZE_T_3 = null;
	String P_SIZE_T_4 = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    PK_PLC = (String) SearchMap.get("pk_plc");
	    MET_TYPE = (String) SearchMap.get("met_type");
	    P_SIZE_T_1 = (String) SearchMap.get("p_size_t_1");
	    P_SIZE_T_2 = (String) SearchMap.get("p_size_t_2");
	    P_SIZE_T_3 = (String) SearchMap.get("p_size_t_3");
	    P_SIZE_T_4 = (String) SearchMap.get("p_size_t_4");

	    sb.append(" UPDATE PLATING_COST");
	    sb.append("    SET MET_TYPE = ?,   P_SIZE_T_1 = ?, P_SIZE_T_2 = ?, P_SIZE_T_3 = ?, P_SIZE_T_4 = ?");
	    sb.append("  WHERE PK_CUT = ?");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, MET_TYPE);
		pstmt.setString(2, P_SIZE_T_1);
		pstmt.setString(3, P_SIZE_T_2);
		pstmt.setString(4, P_SIZE_T_3);
		pstmt.setString(5, P_SIZE_T_4);
		pstmt.setString(6, PK_PLC);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    }
	}
	return complet;
    }

    public int PlatingDelete(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String PK_PLC = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    PK_PLC = (String) SearchMap.get("pk_plc");

	    sb.append(" DELETE PLATING_COST");
	    sb.append("  WHERE PK_PLC = ?");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, PK_PLC);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    }
	}
	return complet;
    }

    /**
     * 함수명 : getPlatingList 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2011. 11. 21.
     */
    public ArrayList getProcessingList() throws Exception {
	ArrayList<Hashtable<String, String>> platingList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> plating = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT PK_PC, PC_COST_TYPE, MET_TYPE, PRO_COST");
	sb.append("   FROM PROCESSING_COST");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    // pstmt.setString(1, gubun);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		plating = new Hashtable<String, String>();
		plating.put("PK_PC", StringUtil.checkNull(rs.getString("PK_PC")));
		plating.put("PC_COST_TYPE", StringUtil.checkNull(rs.getString("PC_COST_TYPE")));
		plating.put("MET_TYPE", StringUtil.checkNull(rs.getString("MET_TYPE")));
		plating.put("PRO_COST", StringUtil.checkNull(rs.getString("PRO_COST")));

		platingList.add(plating);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return platingList;
    }

    public int processingCreate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;

	String PC_COST_TYPE = null;
	String MET_TYPE = null;
	String PRO_COST = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    PC_COST_TYPE = (String) SearchMap.get("PC_COST_TYPE");
	    MET_TYPE = (String) SearchMap.get("MET_TYPE");
	    PRO_COST = (String) SearchMap.get("PRO_COST");

	    sb.append(" INSERT INTO PROCESSING_COST (PK_PC, PC_COST_TYPE, MET_TYPE,PRO_COST) ");
	    sb.append("   VALUES ((SELECT MAX(PK_PC)+1 FROM PLATING_COST),?, ?, ?)");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, PC_COST_TYPE);
		pstmt.setString(2, MET_TYPE);
		pstmt.setString(3, PRO_COST);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    }
	}
	return complet;
    }

    public int ProcessingUpdate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String PK_PC = null;
	String PC_COST_TYPE = null;
	String MET_TYPE = null;
	String PRO_COST = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    PK_PC = (String) SearchMap.get("pk_pc");
	    PC_COST_TYPE = (String) SearchMap.get("pc_cost_type");
	    MET_TYPE = (String) SearchMap.get("met_type");
	    PRO_COST = (String) SearchMap.get("pro_cost");

	    sb.append(" UPDATE PROCESSING_COST");
	    sb.append("    SET PC_COST_TYPE = ?,   MET_TYPE = ?, PRO_COST = ?");
	    sb.append("  WHERE PK_PC = ?");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, PC_COST_TYPE);
		pstmt.setString(2, MET_TYPE);
		pstmt.setString(3, PRO_COST);
		pstmt.setString(4, PK_PC);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    }
	}
	return complet;
    }

    public int ProcessingDelete(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String PK_PC = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    PK_PC = (String) SearchMap.get("pk_pc");

	    sb.append(" DELETE PROCESSING_COST");
	    sb.append("  WHERE PK_PC = ?");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, PK_PC);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    }
	}
	return complet;
    }
    
    
    /**
     * 함수명 : costInputCheck 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2015. 05. 21.
     */
    public ArrayList costInputCheck(String projectNo) throws Exception {
	ArrayList<Hashtable<String, String>> costInputCheckList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> costInputCheckHash = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	
	
	sb.append("SELECT REPORT_PROGRESS_CNT+REQUEST_PROGRESS_CNT+REQUEST_PROGRESS_CNT_2 AS PROGRESS_CNT ,COMPLETE_CNT                                               	\n");
	sb.append("  FROM (SELECT COUNT(*) AS REPORT_PROGRESS_CNT                  							 	\n");
	sb.append("          FROM WFINFO 												 	\n");
	sb.append("         WHERE PK_WID IN (SELECT B.FK_WID FROM COST_PRODUCTINFO A, COST_REPORT B						\n");
	sb.append("                           WHERE A.PK_CR_GROUP = '"+projectNo+"'"+  "								\n");
	sb.append("                             AND A.REPORT_PK = B.CRP_GROUP 									\n");
	sb.append("                             AND INPUT_GB IS NULL										\n");
	sb.append("                             AND REV_NO IN (SELECT MAX(REV_NO) FROM COST_PRODUCTINFO WHERE PK_CR_GROUP = '"+projectNo+"'"+  ")   	\n");
	sb.append("                             AND STEP_NO < '6')) A,									 	\n");
	sb.append("       (SELECT COUNT(*) AS REQUEST_PROGRESS_CNT 									 	\n");
	sb.append("          FROM WFINFO 												 	\n");
	sb.append("         WHERE PK_WID IN (SELECT B.FK_WID FROM COST_PRODUCTINFO A,COST_REQUEST B					 	\n");
	sb.append("                           WHERE A.PK_PID = B.FK_PID									 	\n");
	sb.append("                             AND PK_CR_GROUP = '"+projectNo+"'"+  "								 	\n");
	sb.append("                             AND REV_NO IN (SELECT MAX(REV_NO) FROM COST_PRODUCTINFO WHERE PK_CR_GROUP = '"+projectNo+"'"+  ")	\n");
	sb.append("                             AND STEP_NO < '6')) B,									 	\n");
	sb.append("  (SELECT COUNT(*) AS REQUEST_PROGRESS_CNT_2 FROM COST_PRODUCTINFO A,COST_REQUEST B                         		\n");
	sb.append("         WHERE A.PK_PID = B.FK_PID             										\n");
	sb.append("           AND A.GROUP_NO = 'T001'                                    						        \n");
	sb.append("           AND PK_CR_GROUP = '"+projectNo+"'"+  "  AND FK_WID IS NULL                                 							\n");
	sb.append("           AND REV_NO IN (SELECT MAX(REV_NO) FROM COST_PRODUCTINFO WHERE PK_CR_GROUP = '"+projectNo+"'"+")) C,			\n");
        sb.append("       (SELECT COUNT(*) AS COMPLETE_CNT											\n");
        sb.append("	     FROM WFINFO 													\n");
        sb.append("	    WHERE PK_WID IN (SELECT B.FK_WID FROM COST_PRODUCTINFO A, COST_REPORT B						\n");
        sb.append("	                      WHERE A.PK_CR_GROUP = '"+projectNo+"'"+  "								\n");
        sb.append("	                        AND A.REPORT_PK = B.CRP_GROUP 									\n");
        sb.append("	                        AND INPUT_GB IS NULL										\n");
        sb.append("	                        AND STEP_NO = '6')) D										\n");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    // pstmt.setString(1, gubun);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		costInputCheckHash = new Hashtable<String, String>();
		costInputCheckHash.put("PROGRESS_CNT", StringUtil.checkNull(rs.getString("PROGRESS_CNT")));
		costInputCheckHash.put("COMPLETE_CNT", StringUtil.checkNull(rs.getString("COMPLETE_CNT")));
		costInputCheckList.add(costInputCheckHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
/*	    if (conn != null) {
		conn.close();
	    }*/
	}
	return costInputCheckList;
    }
    
    public StringBuffer getCostIQuery(){
	StringBuffer sb = new StringBuffer();
	
	sb.append(" select a.seqnum as partSqe, 																		\n");
	sb.append("       A.PNUM as partNo , 																			\n");
	sb.append("       e.NAME AS devUser, 																			\n");
	sb.append("       'e3ps.project.ProductInfo:'||a.ida2a2 as car,																\n");
	sb.append("       A.PNAME AS partName,																				\n");
	sb.append("       nvl(to_char(costsDate,'YYYYMMDD'),to_char(sysdate,'YYYYMMDD')) as devCostDate,											\n");
	sb.append("       C.PJTNAME as devPartName, 																		\n");
	sb.append("       (select name from numbercode where ida2a2 = b.idA3F9) as devLevel,													\n");
	sb.append("       nvl(f.year1,0) as ea1, 																		\n");
	sb.append("       nvl(f.year2,0) as ea2, 																		\n");
	sb.append("       nvl(f.year3,0) as ea3, 																		\n");
	sb.append("       nvl(f.year4,0) as ea4, 																		\n");
	sb.append("       nvl(f.year5,0) as ea5, 																		\n");
	sb.append("       nvl(f.year6,0) as ea6, 																		\n");
	sb.append("       nvl(f.year7,0) as ea7, 																		\n");
	sb.append("       nvl(f.year8,0) as ea8,																		\n");
	sb.append("       nvl(a.price,0) as expectValue,																	\n");
	sb.append("       nvl(a.cost,0) as targetValue,     																	\n");
	sb.append("       nvl(a.rate,0) as targetRate,																		\n");
	sb.append("       (select case when dm.name = '제품개발1팀' or dm.name = '개발1팀' or dm.name = '연구개발1팀' or dm.name = '1' or dm.name = '커넥터연구개발1팀' or dm.name = '커넥터설계1팀' then '1'  \n");
	sb.append("       when dm.name = '커넥터연구개발2팀' or dm.name = '커넥터개발팀' then '11'    																\n");
	sb.append("       when dm.name = '커넥터연구개발3팀' or dm.name = '커넥터양산개선팀' then '12'    																\n");
	sb.append("       when dm.name = '전장모듈연구개발1팀' or dm.name = '제품개발2팀' or  dm.name = '개발2팀' or dm.name = '연구개발2팀' or dm.name = '2'  then '22'    				\n");
	sb.append("       when dm.name = '전장모듈연구개발2팀' then '23'    															\n");
	sb.append("       when dm.name = '전장모듈연구개발3팀' then '24'    															\n");
	sb.append("       when dm.name = '제품개발3팀' or  dm.name = '개발3팀' or dm.name = '연구개발3팀' or dm.name = '3' then '3'    								\n");
	sb.append("       when dm.name = '제품개발4팀' or  dm.name = '개발4팀' or dm.name = '연구개발4팀' or dm.name = '4' then '4'    								\n");
	sb.append("       when dm.name = '연구개발5팀' then '5'    								\n");
	sb.append("       when dm.name = '연구개발6팀' or dm.name = '6' or dm.name = 'W／H Gr' or  dm.name = '개발5팀' or dm.name = '연구개발5팀' or  dm.name = '5' then '6'                        \n");                                   
	sb.append("       when dm.name = '시작개발팀' then '21'                                                                									\n");
	sb.append("       when dm.name = '전장부품개발팀' then '7'                                                                									\n");
	sb.append("       else '1'                                                                                                                            					\n");
	sb.append("       end code from department  dm 														\n");
	sb.append("          where dm.ida2a2 = e.ida3c4)	as devDepartment,														        \n");
	sb.append("       FN_GET_DRPROGRESS(c.pjtno) as drProgress,																\n");
	sb.append("       (select name from people where IDA3B4 = b.idA3J9) as sales,														\n");
	sb.append("       sysdate as proposalDate,																		\n");
	sb.append("       (select name from numbercode where ida2a2 = b.idA3B9) as rank ,													\n");
	sb.append("       (select listagg(nbc.name,',') within group(order by nbc.name) as name 												\n");
	sb.append("          from ProjectSubcontractorLink psl, numbercode nbc															\n");
	sb.append("         where psl.ida3a5 = nbc.ida2a2																	\n");
	sb.append("           and psl.ida3b5 = b.ida2a2) as subcontractor,															\n");
	sb.append("       (select listagg(nbc.name,',') within group(order by nbc.name) as name 												\n");
	sb.append("          from ProjectCustomereventLink pcl, numbercode nbc															\n");
	sb.append("         where pcl.ida3a5 = nbc.ida2a2																	\n");
	sb.append("           and pcl.ida3b5 = b.ida2a2) as customerevent,															\n");
	sb.append("       a.areas,																				\n");
	sb.append("       a.reviewProjectNo as reviewProjectCode,																\n");
	sb.append("       a.reviewSeqNo as reviewPartSqe,																			\n");
	sb.append("       (select linkurl from cost_i where projectcode = a.reviewProjectNo and partsqe = a.reviewSeqNo) as linkurl								\n");
	sb.append("  from productinfo a, productproject b, e3psprojectmaster c, ProjectMemberLink d , PEOPLE e, modelinfo f  										\n");
	sb.append(" where a.ida3a3 = b.ida2a2																			\n");
	sb.append("   and b.ida3b8 = c.ida2a2																			\n");
	sb.append("   and b.ida2a2 = d.ida3a5																			\n");
	sb.append("   AND d.IDA3B5 = e.IDA3B4   																		\n");
	sb.append("   and a.IDA2A2 = f.IDA3B3   																		\n");
	sb.append("   and c.pjtno = ?																			        \n");
	sb.append("   and b.checkoutstate != 'c/o'																		\n");
	sb.append("   and b.lastest = '1'																			\n");
	sb.append("   and d.PJTROLE  = 'Team_PRODUCT01'																		\n");
	sb.append("   union																					\n");
	sb.append("   select a.seqnum as partSqe, 																		\n");
	sb.append("       A.PNUM as partNo , 																			\n");
	sb.append("       e.NAME AS devUser, 																			\n");
	sb.append("       'e3ps.project.ProductInfo:'||a.ida2a2 as car,																\n");
	sb.append("       A.PNAME AS partName,																				\n");
	sb.append("       nvl(to_char(estimateDate,'YYYYMMDD'),to_char(sysdate,'YYYYMMDD')) as devCostDate,											\n");
	sb.append("       C.PJTNAME as devPartName, 																		\n");
	sb.append("       '개발검토' as devLevel,																			\n");
	sb.append("       nvl(f.year1,0) as ea1, 																		\n");
	sb.append("       nvl(f.year2,0) as ea2, 																	        \n");
	sb.append("       nvl(f.year3,0) as ea3, 																		\n");
	sb.append("       nvl(f.year4,0) as ea4, 																		\n");
	sb.append("       nvl(f.year5,0) as ea5, 																		\n");
	sb.append("       nvl(f.year6,0) as ea6, 																		\n");
	sb.append("       nvl(f.year7,0) as ea7, 																		\n");
	sb.append("       nvl(f.year8,0) as ea8,																		\n");
	sb.append("       nvl(a.price,0) as expectValue,																	\n");
	sb.append("       nvl(a.cost,0) as targetValue,     																	\n");
	sb.append("       nvl(a.rate,0) as targetRate,																		\n");
	sb.append("       (select case when dm.name = '제품개발1팀' or dm.name = '개발1팀' or dm.name = '연구개발1팀' or dm.name = '1' or dm.name = '커넥터연구개발1팀' or dm.name = '커넥터설계1팀' then '1'  \n");
	sb.append("       when dm.name = '커넥터연구개발2팀' or dm.name = '커넥터개발팀' then '11'    																\n");
	sb.append("       when dm.name = '커넥터연구개발3팀' or dm.name = '커넥터양산개선팀' then '12'    																\n");
	sb.append("       when dm.name = '전장모듈연구개발1팀' or dm.name = '제품개발2팀' or  dm.name = '개발2팀' or dm.name = '연구개발2팀' or dm.name = '2'  then '22'    				\n");
	sb.append("       when dm.name = '전장모듈연구개발2팀' then '23'    															\n");
	sb.append("       when dm.name = '전장모듈연구개발3팀' then '24'    															\n");
	sb.append("       when dm.name = '제품개발3팀' or  dm.name = '개발3팀' or dm.name = '연구개발3팀' or dm.name = '3' then '3'    								\n");
	sb.append("       when dm.name = '제품개발4팀' or  dm.name = '개발4팀' or dm.name = '연구개발4팀' or dm.name = '4' then '4'    								\n");
	sb.append("       when dm.name = '연구개발5팀' then '5'    								\n");
	sb.append("       when dm.name = '연구개발6팀' or dm.name = '6' or dm.name = 'W／H Gr' or  dm.name = '개발5팀' or dm.name = '연구개발5팀' or  dm.name = '5' then '6'                        \n");                                   
	sb.append("       when dm.name = '시작개발팀' then '21'                                                                									\n");
	sb.append("       when dm.name = '전장부품개발팀' then '7'                                                                									\n");
	sb.append("       else '1'                                                                                                                            					\n");
	sb.append("       end code from department  dm 														\n");
	sb.append("          where dm.ida2a2 = e.ida3c4)	as devDepartment,														        \n");
	sb.append("       FN_GET_DRPROGRESS(c.pjtno) as drProgress,																\n");
	sb.append("       (select name from people where IDA3B4 = b.idA3J9) as sales,														\n");
	sb.append("       proposalDate as proposalDate,																		\n");
	sb.append("       (select name from numbercode where ida2a2 = b.idA3B9) as rank ,													\n");
	sb.append("       (select listagg(nbc.name,',') within group(order by nbc.name) as name 												\n");
	sb.append("          from ProjectSubcontractorLink psl, numbercode nbc															\n");
	sb.append("         where psl.ida3a5 = nbc.ida2a2																	\n");	
	sb.append("           and psl.ida3b5 = b.ida2a2) as subcontractor,															\n");
	sb.append("       (select listagg(nbc.name,',') within group(order by nbc.name) as name 												\n");
	sb.append("          from ProjectCustomereventLink pcl, numbercode nbc															\n");
	sb.append("         where pcl.ida3a5 = nbc.ida2a2																	\n");
	sb.append("           and pcl.ida3b5 = b.ida2a2) as customerevent,															\n");
	sb.append("       a.areas,																				\n");
	sb.append("       a.reviewProjectNo as reviewProjectCode,																\n");
	sb.append("       a.reviewSeqNo as reviewPartSqe,																			\n");
	sb.append("       (select linkurl from cost_i where projectcode = a.reviewProjectNo and partsqe = a.reviewSeqNo) as linkurl								\n");
	sb.append("  from productinfo a, reviewproject b, e3psprojectmaster c, ProjectMemberLink d , PEOPLE e, modelinfo f  											\n");
	sb.append(" where a.ida3a3 = b.ida2a2																			\n");
	sb.append("   and b.ida3b8 = c.ida2a2																			\n");
	sb.append("   and b.ida2a2 = d.ida3a5																			\n");
	sb.append("   AND d.IDA3B5 = e.IDA3B4   																		\n");
	sb.append("   and a.IDA2A2 = f.IDA3B3   																		\n");
	sb.append("   and c.pjtno = ?																			        \n");
	sb.append("   and b.checkoutstate != 'c/o'																		\n");
	sb.append("   and b.lastest = '1'																			\n");
	sb.append("   and d.PJTROLE  = 'Team_REVIEW01'																		\n");
	return sb;
    }

    /**
     * 함수명 : getCostI 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2011. 11. 21.
     */
    public ArrayList getCostI(String pjt_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	Hashtable<String, String> SearchMap2 = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = getCostIQuery();
	/*
	 * sb.append(
	 * " SELECT partSqe, partNo, devUser, car, partName, nvl(to_char(devCostDate,'YYYYMMDD'),to_char(sysdate,'YYYYMMDD')) as devCostDate, devPartName, devLevel,  "
	 * ); sb.append("        ea1, ea2, ea3, ea4, ea5, ea6, ea7, ea8, expectValue, targetValue, targetRate, " );
	 * sb.append("case when devDepartment = '제품개발1팀' or  devDepartment = '개발1팀' or devDepartment = '연구개발1팀' or devDepartment = '1' then '1'"
	 * );
	 * sb.append("     when devDepartment = '제품개발2팀' or  devDepartment = '개발2팀' or devDepartment = '연구개발2팀' or devDepartment = '2' then '2'"
	 * );
	 * sb.append("     when devDepartment = '제품개발3팀' or  devDepartment = '개발3팀' or devDepartment = '연구개발3팀' or devDepartment = '3' then '3'"
	 * );
	 * sb.append("     when devDepartment = '제품개발4팀' or  devDepartment = '개발4팀' or devDepartment = '연구개발4팀' or devDepartment = '4' then '4'"
	 * ); sb.append(
	 * "     when devDepartment = 'W／H Gr' or  devDepartment = '개발5팀' or devDepartment = '연구개발5팀' or  devDepartment = '5' then '5'                                 "
	 * ); sb.append("     when devDepartment = '연구개발6팀' or devDepartment = '6' then '6'");
	 * sb.append("else '1'                                                                                             ");
	 * sb.append(" end devDepartment,                                                                                  "); sb.append(
	 * "        drProgress, sales, proposalDate, rank, subcontractor, customerevent, areas, reviewProjectCode, reviewPartSqe, linkUrl "
	 * ); sb.append("   FROM COST_I"); sb.append("  WHERE projectcode = ?");
	 * 
	 * sb.append("  ORDER BY partSqe");
	 */

	/*sb.append("  SELECT partSqe, partNo, F.NAME AS devUser, car, partName, nvl(to_char(devCostDate,'YYYYMMDD'),to_char(sysdate,'YYYYMMDD')) as devCostDate, devPartName, devLevel,  ");
	sb.append(" nvl(d.year1,0) as ea1, nvl(d.year2,0) as ea2, nvl(d.year3,0) as ea3, nvl(d.year4,0) as ea4, nvl(d.year5,0) as ea5, nvl(d.year6,0) as ea6, nvl(d.year7,0) as ea7, nvl(d.year8,0) as ea8, expectValue, targetValue, A.targetRate,");
	sb.append(" case when devDepartment = '제품개발1팀' or devDepartment = '개발1팀' or devDepartment = '연구개발1팀' or devDepartment = '1' or devDepartment = '커넥터연구개발1팀' then '1'		");
	sb.append("      when devDepartment = '커넥터연구개발2팀' then '11'	");
	sb.append("      when devDepartment = '커넥터연구개발3팀' then '12'	");
	sb.append("      when devDepartment = '커넥터연구개발센타' then '13'	");
	sb.append("      when devDepartment = '전장모듈연구개발1팀' or devDepartment = '제품개발2팀' or  devDepartment = '개발2팀' or devDepartment = '연구개발2팀' or devDepartment = '2'  then '22'	");
	sb.append("      when devDepartment = '전장모듈연구개발2팀' then '23'	");
	sb.append("      when devDepartment = '전장모듈연구개발3팀' then '24'	");
	sb.append("      when devDepartment = '제품개발3팀' or  devDepartment = '개발3팀' or devDepartment = '연구개발3팀' or devDepartment = '3' then '3'	");
	sb.append("      when devDepartment = '제품개발4팀' or  devDepartment = '개발4팀' or devDepartment = '연구개발4팀' or devDepartment = '4' then '4'	");
	sb.append("      when devDepartment = '연구개발6팀' or devDepartment = '6' or devDepartment = 'W／H Gr' or  devDepartment = '개발5팀' or devDepartment = '연구개발5팀' or  devDepartment = '5' then '6'																");
	sb.append("      when devDepartment = '시작개발1팀' then '21'																");
	sb.append(" else '1'                                                                															");
	sb.append("  end devDepartment,                                                        															");
	sb.append("         drProgress, sales, proposalDate, rank, subcontractor, customerevent, A.areas, reviewProjectCode, reviewPartSqe, linkUrl		");
	sb.append("    FROM COST_I A, ProductInfo B, (select ida2a2 from productproject where (pjtcompletion,ida3b8) in (								");
	sb.append(" select  max(pjtcompletion),m.ida2a2 from e3psprojectmaster m, productproject d where pjtno = ? and m.ida2a2 = d.ida3b8 and LASTEST = 1 and CHECKOUTSTATE <> 'c/o'		");
	sb.append(" group by m.ida2a2)) C, modelinfo D, ProjectMemberLink E , PEOPLE F	");
	sb.append("   WHERE projectcode = ?				");
	sb.append("     AND A.PARTSQE = B.SEQNUM		");
	sb.append("     AND B.ida3a3 = C.IDA2A2			");
	sb.append("     AND B.IDA2A2 = D.IDA3B3			");
	sb.append("     AND B.IDA3A3 = E.IDA3A5     	");
	sb.append("     AND PJTROLE  = 'Team_PRODUCT01' ");
	sb.append("     AND E.IDA3B5 = F.IDA3B4			");
	sb.append("union");
	sb.append("  SELECT partSqe, partNo, F.NAME AS devUser, car, partName, nvl(to_char(devCostDate,'YYYYMMDD'),to_char(sysdate,'YYYYMMDD')) as devCostDate, devPartName, devLevel,  ");
	sb.append(" nvl(d.year1,0) as ea1, nvl(d.year2,0) as ea2, nvl(d.year3,0) as ea3, nvl(d.year4,0) as ea4, nvl(d.year5,0) as ea5, nvl(d.year6,0) as ea6, nvl(d.year7,0) as ea7, nvl(d.year8,0) as ea8, expectValue, targetValue, A.targetRate,");
	sb.append(" case when devDepartment = '제품개발1팀' or devDepartment = '개발1팀' or devDepartment = '연구개발1팀' or devDepartment = '1' or devDepartment = '커넥터연구개발1팀' then '1'		");
	sb.append("      when devDepartment = '커넥터연구개발2팀' then '11'	");
	sb.append("      when devDepartment = '커넥터연구개발3팀' then '12'	");
	sb.append("      when devDepartment = '커넥터연구개발센타' then '13'	");
	sb.append("      when devDepartment = '전장모듈연구개발1팀' or devDepartment = '제품개발2팀' or  devDepartment = '개발2팀' or devDepartment = '연구개발2팀' or devDepartment = '2'  then '22'	");
	sb.append("      when devDepartment = '전장모듈연구개발2팀' then '23'	");
	sb.append("      when devDepartment = '전장모듈연구개발3팀' then '24'	");
	sb.append("      when devDepartment = '제품개발3팀' or  devDepartment = '개발3팀' or devDepartment = '연구개발3팀' or devDepartment = '3' then '3'	");
	sb.append("      when devDepartment = '제품개발4팀' or  devDepartment = '개발4팀' or devDepartment = '연구개발4팀' or devDepartment = '4' then '4'	");
	sb.append("      when devDepartment = '연구개발6팀' or devDepartment = '6' or devDepartment = 'W／H Gr' or  devDepartment = '개발5팀' or devDepartment = '연구개발5팀' or  devDepartment = '5' then '6'																");
	sb.append("      when devDepartment = '시작개발1팀' then '21'																");
	sb.append(" else '1'                                                                															");
	sb.append("  end devDepartment,                                                        															");
	sb.append("         drProgress, sales, proposalDate, rank, subcontractor, customerevent, A.areas, reviewProjectCode, reviewPartSqe, linkUrl		");
	sb.append("    FROM COST_I A, ProductInfo B, (select ida2a2 from reviewproject where (pjtcompletion,ida3b8) in (								");
	sb.append(" select  max(pjtcompletion),m.ida2a2 from e3psprojectmaster m, reviewproject d where pjtno = ? and m.ida2a2 = d.ida3b8 and LASTEST = 1 and CHECKOUTSTATE <> 'c/o'		");
	sb.append(" group by m.ida2a2)) C, modelinfo D, ProjectMemberLink E , PEOPLE F 	");
	sb.append("   WHERE projectcode = ?				");
	sb.append("     AND A.PARTSQE = B.SEQNUM		");
	sb.append("     AND B.ida3a3 = C.IDA2A2			");
	sb.append("     AND B.IDA2A2 = D.IDA3B3			");
	sb.append("     AND B.IDA3A3 = E.IDA3A5         ");
	sb.append("     AND PJTROLE  = 'Team_REVIEW01'  ");
	sb.append("     AND E.IDA3B5 = F.IDA3B4         ");
	sb.append("   ORDER BY partSqe					");*/
	

	String reviewProjectCode = "";
	String reviewPartSqe = "";
	String partSqe_1 = "";
	String linkUrl = "";
	String rev_pk = "";
	String partSqe_2 = "";
	String car_type = "";
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pjt_no);
	    pstmt.setString(2, pjt_no);
	    rs = pstmt.executeQuery();
	    // ResultSetMetaData rsmd = rs.getMetaData();
	    // int columnCnt = rsmd.getColumnCount();
	    int k = 0;
	    int i = 1;
	    int j = 0;
	    String o_group_no = "";
	    String tmp = "";
	    SearchMap = new Hashtable<String, String>();
	    
	    while (rs.next()) {// PLM에 Data가 있을때
		System.out.println("PLM에 DATA있다");
		if (j < 9) {
		    tmp = "00" + i;
		} else if (j < 99) {
		    tmp = "0" + i;
		} else if (j < 999) {
		    tmp = Integer.toString(i);
		}
		SearchMap = new Hashtable<String, String>();
		reviewProjectCode = StringUtil.checkNull(rs.getString("reviewProjectCode"));
		reviewPartSqe = StringUtil.checkNull(rs.getString("reviewPartSqe"));
		partSqe_1 = StringUtil.checkNull(rs.getString("partSqe"));
		linkUrl = StringUtil.checkNull(rs.getString("linkUrl"));
		SearchMap.put("partSqe", partSqe_1); // 품목 시퀀스 키
		SearchMap.put("part_no", StringUtil.checkNull(rs.getString("partNo"))); // 품번
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("devUser"))); // 개발담당자
		car_type = ProductHelper.getCarName(rs.getString("car"));
		SearchMap.put("car_type", StringUtil.checkNull(car_type)); // 차종
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("partName"))); // 품목명
		SearchMap.put("request_day", StringUtil.checkNull(rs.getString("devCostDate"))); // 개발 원가 제출 일정
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("devPartName"))); // 개발 검토 제품명
		// SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("devLevel"))); //개발단계
		// SearchMap.put("usage", StringUtil.checkNull(rs.getString("us"))); //u/s
		// Usage Default 값 = 1 11.11.30 이성수대리 요청
		SearchMap.put("usage", "1"); // u/s
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("ea1"))); // 예상수량(1년차)
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("ea2"))); // 예상수량(2년차)
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("ea3"))); // 예상수량(3년차)
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("ea4"))); // 예상수량(4년차)
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("ea5"))); // 예상수량(…)
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("ea6"))); // 예상수량(1년차)
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("ea7"))); // 예상수량(2년차)
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("ea8"))); // 예상수량(3년차)
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("expectValue"))); // 고객 예상가
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("targetValue"))); // 판매 목표가
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("targetRate"))); // 목표 수익율
		SearchMap.put("team", StringUtil.checkNull(rs.getString("devDepartment"))); // 개발부서
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("sales"))); // 영업담당자
		SearchMap.put("posal_day", StringUtil.checkNull(rs.getString("proposalDate"))); // 제안서제출일
		SearchMap.put("rank", StringUtil.checkNull(rs.getString("rank"))); // Rank
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("subcontractor"))); // 고객처
		SearchMap.put("customer_L", StringUtil.checkNull(rs.getString("customerevent"))); // 최종사용처
		SearchMap.put("app_part", StringUtil.checkNull(rs.getString("areas"))); // 적용부위
		SearchMap.put("reviewProjectCode", reviewProjectCode); // 기개발검토프로젝트 번호
		SearchMap.put("reviewPartSqe", reviewPartSqe); // 기개발검토프로젝트 시퀀스 키
		SearchMap.put("drProgress", StringUtil.checkNull(rs.getString("drProgress"))); // DR 단계
		SearchMap.put("linkUrl", linkUrl);
		SearchMap.put("o_group_no", o_group_no + "T" + tmp); // 그룹No
		SearchMap.put("IsPlm", "plm");
		if (!reviewProjectCode.equals("") && reviewProjectCode != null) {
		    SearchMap.put("rev_pk", reviewProjectCode);
		    SearchMap.put("partSqe_2", reviewPartSqe);
		} else {
		    SearchMap.put("rev_pk", pjt_no);
		    SearchMap.put("partSqe_2", partSqe_1);
		}

		SearchList.add(SearchMap);

		i = i + 1;
		j = j + 1;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	if (SearchList.size() < 1) {
	    search_type = "old";
	    plm_data = "no";
	    Rcount = 1;
	} else {

	    int link_count = 0;
	    for (int i = 0; i < SearchList.size(); i++) {
		SearchMap = (Hashtable) SearchList.get(i);
		linkUrl = (String) SearchMap.get("linkUrl");
		if (linkUrl != null && !linkUrl.equals("")) {
		    link_count++;
		}
	    }

	    if (link_count > 0) { // 인터페이스 DB에 원가산출정보가 들어있을경우
		search_type = "old"; // PLM에 Data가 있음
		plm_data = "ok";
	    } else {// 인터페이스 DB에 원가산출정보가 들어있지 않을경우
		SearchList = getRev(SearchList);
	    }
	}

	if (search_type.equals("old")) {// 원가Db 검색
	    SearchList = getCostList(SearchList, pjt_no);
	}

	SearchMap = null;

	int p_count = 0;
	int c_count = 0;
	int t_count = 0;
	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	}

/*	if (conn != null) {
	    conn.close();
	}*/

	SearchMap2 = new Hashtable<String, String>();
	SearchMap2.put("search_type", search_type);
	SearchMap2.put("plm_data", plm_data);
	SearchList.add(SearchMap2);
	return SearchList;
    }

    public ArrayList getRev(ArrayList SearchList) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList2 = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	Hashtable<String, String> SearchMap2 = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	SearchList2 = (ArrayList) SearchList.clone();
	int count = 0;

	for (int i = 0; i < SearchList.size(); i++) {
	    sb.append(" select b.rev_pk from cost_request a,cost_productinfo b");
	    sb.append("  WHERE b.rev_pk = ?");
	    sb.append("    and a.fk_pid = b.pk_pid and length(b.group_no) = 4 order by b.rev_no desc");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		SearchMap = (Hashtable) SearchList.get(i);
		pstmt.setString(1, SearchMap.get("rev_pk"));
		rs = pstmt.executeQuery();

		while (rs.next()) {// 원가DB에 Data가 있을경우 = 기존원가DB에 존재하는 Data 이면서 PLM Open 후 별도로 PLM에 신규생성된 Data
		    SearchMap2 = new Hashtable<String, String>();
		    SearchMap2.put("rev_pk", StringUtil.checkNull(rs.getString("rev_pk")));// 기개발검토프로젝트 번호
		    SearchList2.set(i, SearchMap);
		    search_type = "old";
		    plm_data = "ok";// PLM에 Data가 있음

		    SearchMap = (Hashtable) SearchList.get(i);
		    count++;
		}
		if (count < 1) {
		    plm_data = "ok";// PLM에 Data가 있음
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (rs != null) {
		    rs.close();
		}
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}

	return SearchList2;
    }

    public ArrayList getCostList(ArrayList SearchList, String pjt_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList2 = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	Hashtable<String, String> SearchMap2 = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	SearchList2 = (ArrayList) SearchList.clone();
	int S_list = SearchList.size();
	if (S_list < 1) {
	    S_list = 1;
	}
	for (int i = 0; i < S_list; i++) {
	    sb.append(" select partSqe,part_no,group_no,car_type,part_name,pjt_name,rev_pk,usage,su_year_1,su_year_2,su_year_3,su_year_4,");
	    sb.append("        su_year_5,su_year_6,su_year_7,su_year_8,client_cost,ket_cost,target_cost,team,f_name,a_name,customer_F,customer_L,dr_step,");
	    sb.append("        case_count_1,case_count_2,rev_no,pro_type,make,part_type,mix_group,net_1,net_2,net_3,meterial,      ");
	    sb.append("        t_size,w_size,p_size,plating,m_maker,m_mone,unitcost,unitcost_txt,grade_name,grade_color,order_con,h_weight,          ");
	    sb.append("        h_scrap,t_weight,recycle,die_no,cavity,m_su,mold_cost,mold_c_type,make_type,pro_1,ton,spm,method_type,pro_level,");
	    sb.append("        pro_level_txt,line_su,sul_cost,plating_type,type_2,plating_cost,type_1,t_mone,type_cost,t_order,pack_type,in_pack,    ");
	    sb.append("        out_pack,store,dest,dis_cost,distri_cost,royalty,yazaki_ro,etc_cost,replace(part_note , chr(13) || chr(10) , '') as part_note, part_count");
	    sb.append("   from cost_request a,cost_productinfo b");
	    if (plm_data.equals("ok")) {
		sb.append("  where a.fk_pid = b.pk_pid and b.rev_pk = ? and a.partSqe = ? and b.data_type = 'main'");
	    } else {
		sb.append("  where a.fk_pid = b.pk_pid and b.pjt_no = ? and b.data_type = 'main'");
	    }
	    sb.append("    and b.rev_no = (select max(rev_no) from cost_request a,cost_productinfo b");
	    if (plm_data.equals("ok")) {
		sb.append("                     where a.fk_pid = b.pk_pid and b.rev_pk = ? and a.partSqe = ? and b.data_type = 'main') order by group_no");
	    } else {
		sb.append("                     where a.fk_pid = b.pk_pid and b.pjt_no = ? and b.data_type = 'main') order by group_no");
	    }

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		if (SearchList != null) {
		    SearchMap = new Hashtable<String, String>();

		    if (plm_data.equals("ok")) {
			SearchMap = (Hashtable) SearchList.get(i);
			pstmt.setString(1, SearchMap.get("rev_pk"));
			pstmt.setString(2, SearchMap.get("partSqe_2"));
			pstmt.setString(3, SearchMap.get("rev_pk"));
			pstmt.setString(4, SearchMap.get("partSqe_2"));
		    } else {
			pstmt.setString(1, pjt_no);
			pstmt.setString(2, pjt_no);
		    }
		    rs = pstmt.executeQuery();
		}
		int count = 0;
		while (rs.next()) {
		    SearchMap2 = new Hashtable<String, String>();
		    if (plm_data.equals("no")) {
			SearchMap2.put("partSqe", StringUtil.checkNull(rs.getString("partSqe"))); // 품목 시퀀스 키
			SearchMap2.put("part_no", StringUtil.checkNull(rs.getString("part_no"))); // 품번
			SearchMap2.put("o_group_no", StringUtil.checkNull(rs.getString("group_no"))); // 그룹No
			SearchMap2.put("car_type", StringUtil.checkNull(rs.getString("car_type"))); // 차종
			SearchMap2.put("part_name", StringUtil.checkNull(rs.getString("part_name"))); // 품목명
			SearchMap2.put("request_day", ""); // 개발 원가 제출 일정
			SearchMap2.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name"))); // 개발 검토 제품명
			System.out.println("pjt_name : " + StringUtil.checkNull(rs.getString("pjt_name")));
			SearchMap2.put("rev_pk", StringUtil.checkNull(rs.getString("rev_pk"))); // 기개발검토프로젝트 번호
			SearchMap2.put("usage", StringUtil.checkNull(rs.getString("usage"))); // u/s
			SearchMap2.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1"))); // 예상수량(1년차)
			SearchMap2.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2"))); // 예상수량(2년차)
			SearchMap2.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3"))); // 예상수량(3년차)
			SearchMap2.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4"))); // 예상수량(4년차)
			SearchMap2.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5"))); // 예상수량(…)
			SearchMap2.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6"))); // 예상수량(1년차)
			SearchMap2.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7"))); // 예상수량(2년차)
			SearchMap2.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8"))); // 예상수량(3년차)
			SearchMap2.put("client_cost", StringUtil.checkNull(rs.getString("client_cost"))); // 고객 예상가
			SearchMap2.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost"))); // 판매 목표가
			SearchMap2.put("target_cost", StringUtil.checkNull(rs.getString("target_cost"))); // 목표 수익율
			SearchMap2.put("team", StringUtil.checkNull(rs.getString("team"))); // 개발부서
			SearchMap2.put("f_name", StringUtil.checkNull(rs.getString("f_name"))); // 개발담당자
			SearchMap2.put("a_name", StringUtil.checkNull(rs.getString("a_name"))); // 영업담당자
			SearchMap2.put("customer_F", StringUtil.checkNull(rs.getString("customer_F"))); // 고객처
			SearchMap2.put("customer_L", StringUtil.checkNull(rs.getString("customer_L"))); // 최종사용처
			SearchMap2.put("drProgress", StringUtil.checkNull(rs.getString("dr_step"))); // DR 단계
		    } else {
			SearchMap2.put("partSqe", SearchMap.get("partSqe")); // 품목 시퀀스 키
			SearchMap2.put("car_type", SearchMap.get("car_type")); // 차종
			SearchMap2.put("request_day", SearchMap.get("request_day")); // 개발 원가 제출 일정
			SearchMap2.put("pjt_name", SearchMap.get("pjt_name")); // 개발 검토 제품명
			SearchMap2.put("su_year_1", SearchMap.get("su_year_1")); // 예상수량(1년차)
			SearchMap2.put("su_year_2", SearchMap.get("su_year_2")); // 예상수량(2년차)
			SearchMap2.put("su_year_3", SearchMap.get("su_year_3")); // 예상수량(3년차)
			SearchMap2.put("su_year_4", SearchMap.get("su_year_4")); // 예상수량(4년차)
			SearchMap2.put("su_year_5", SearchMap.get("su_year_5")); // 예상수량(…)
			SearchMap2.put("su_year_6", SearchMap.get("su_year_6")); // 예상수량(1년차)
			SearchMap2.put("su_year_7", SearchMap.get("su_year_7")); // 예상수량(2년차)
			SearchMap2.put("su_year_8", SearchMap.get("su_year_8")); // 예상수량(3년차)
			SearchMap2.put("client_cost", SearchMap.get("client_cost")); // 고객 예상가
			SearchMap2.put("ket_cost", SearchMap.get("ket_cost")); // 판매 목표가
			SearchMap2.put("target_cost", SearchMap.get("target_cost")); // 목표 수익율
			SearchMap2.put("team", SearchMap.get("team")); // 개발부서
			SearchMap2.put("a_name", SearchMap.get("a_name")); // 영업담당자
			SearchMap2.put("customer_F", SearchMap.get("customer_F")); // 고객처
			SearchMap2.put("customer_L", SearchMap.get("customer_L")); // 최종사용처
			SearchMap2.put("drProgress", SearchMap.get("drProgress")); // DR 단계
			SearchMap2.put("part_no", StringUtil.checkNull(rs.getString("part_no"))); // 품번
			SearchMap2.put("part_name", StringUtil.checkNull(rs.getString("part_name"))); // 품목명 }
			SearchMap2.put("f_name", StringUtil.checkNull(rs.getString("f_name"))); // 개발담당자
			SearchMap2.put("usage", StringUtil.checkNull(rs.getString("usage"))); // u/s
			SearchMap2.put("o_group_no", StringUtil.checkNull(rs.getString("group_no"))); // 그룹No
		    }
		    SearchMap2.put("case_count_1", StringUtil.checkNull(rs.getString("case_count_1")));
		    SearchMap2.put("case_count_2", StringUtil.checkNull(rs.getString("case_count_2")));
		    SearchMap2.put("rev_no", Integer.toString(Integer.parseInt(StringUtil.checkNull(rs.getString("rev_no"))) + 1));
		    SearchMap2.put("p_rev_no", Integer.toString(Integer.parseInt(StringUtil.checkNull(rs.getString("rev_no"))) + 1));
		    SearchMap2.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		    SearchMap2.put("make", StringUtil.checkNull(rs.getString("make")));
		    SearchMap2.put("part_type", StringUtil.checkNull(rs.getString("part_type")));
		    SearchMap2.put("mix_group", StringUtil.checkNull(rs.getString("mix_group")));
		    SearchMap2.put("net_1", StringUtil.checkNull(rs.getString("net_1")));
		    SearchMap2.put("net_2", StringUtil.checkNull(rs.getString("net_2")));
		    SearchMap2.put("net_3", StringUtil.checkNull(rs.getString("net_3")));
		    SearchMap2.put("meterial", StringUtil.checkNull(rs.getString("meterial")));
		    SearchMap2.put("t_size", StringUtil.checkNull(rs.getString("t_size")));
		    SearchMap2.put("w_size", StringUtil.checkNull(rs.getString("w_size")));
		    SearchMap2.put("p_size", StringUtil.checkNull(rs.getString("p_size")));
		    SearchMap2.put("plating", StringUtil.checkNull(rs.getString("plating")));
		    SearchMap2.put("m_maker", StringUtil.checkNull(rs.getString("m_maker")));
		    SearchMap2.put("m_mone", StringUtil.checkNull(rs.getString("m_mone")));
		    SearchMap2.put("unitcost", StringUtil.checkNull(rs.getString("unitcost")));
		    SearchMap2.put("unitcost_txt", StringUtil.checkNull(rs.getString("unitcost_txt")));
		    SearchMap2.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		    SearchMap2.put("grade_color", StringUtil.checkNull(rs.getString("grade_color")));
		    SearchMap2.put("order_con", StringUtil.checkNull(rs.getString("order_con")));
		    SearchMap2.put("h_weight", StringUtil.checkNull(rs.getString("h_weight")));
		    SearchMap2.put("h_scrap", StringUtil.checkNull(rs.getString("h_scrap")));
		    SearchMap2.put("t_weight", StringUtil.checkNull(rs.getString("t_weight")));
		    SearchMap2.put("recycle", StringUtil.checkNull(rs.getString("recycle")));
		    SearchMap2.put("die_no", StringUtil.checkNull(rs.getString("die_no")));
		    SearchMap2.put("cavity", StringUtil.checkNull(rs.getString("cavity")));
		    SearchMap2.put("m_su", StringUtil.checkNull(rs.getString("m_su")));
		    SearchMap2.put("mold_cost", StringUtil.checkNull(rs.getString("mold_cost")));
		    SearchMap2.put("mold_c_type", StringUtil.checkNull(rs.getString("mold_c_type")));
		    SearchMap2.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		    SearchMap2.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		    SearchMap2.put("ton", StringUtil.checkNull(rs.getString("ton")));
		    SearchMap2.put("spm", StringUtil.checkNull(rs.getString("spm")));
		    SearchMap2.put("method_type", StringUtil.checkNull(rs.getString("method_type")));
		    SearchMap2.put("pro_level", StringUtil.checkNull(rs.getString("pro_level")));
		    SearchMap2.put("pro_level_txt", StringUtil.checkNull(rs.getString("pro_level_txt")));
		    SearchMap2.put("line_su", StringUtil.checkNull(rs.getString("line_su")));
		    SearchMap2.put("sul_cost", StringUtil.checkNull(rs.getString("sul_cost")));
		    SearchMap2.put("plating_type", StringUtil.checkNull(rs.getString("plating_type")));
		    SearchMap2.put("type_2", StringUtil.checkNull(rs.getString("type_2")));
		    SearchMap2.put("plating_cost", StringUtil.checkNull(rs.getString("plating_cost")));
		    SearchMap2.put("type_1", StringUtil.checkNull(rs.getString("type_1")));
		    SearchMap2.put("t_mone", StringUtil.checkNull(rs.getString("t_mone")));
		    SearchMap2.put("type_cost", StringUtil.checkNull(rs.getString("type_cost")));
		    SearchMap2.put("t_order", StringUtil.checkNull(rs.getString("t_order")));
		    SearchMap2.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		    SearchMap2.put("in_pack", StringUtil.checkNull(rs.getString("in_pack")));
		    SearchMap2.put("out_pack", StringUtil.checkNull(rs.getString("out_pack")));
		    SearchMap2.put("store", StringUtil.checkNull(rs.getString("store")));
		    SearchMap2.put("dest", StringUtil.checkNull(rs.getString("dest")));
		    SearchMap2.put("dis_cost", StringUtil.checkNull(rs.getString("dis_cost")));
		    SearchMap2.put("distri_cost", StringUtil.checkNull(rs.getString("distri_cost")));
		    SearchMap2.put("royalty", StringUtil.checkNull(rs.getString("royalty")));
		    SearchMap2.put("yazaki_ro", StringUtil.checkNull(rs.getString("yazaki_ro")));
		    SearchMap2.put("etc_cost", StringUtil.checkNull(rs.getString("etc_cost")));
		    SearchMap2.put("part_note", StringUtil.checkNull(rs.getString("part_note")));
		    SearchMap2.put("part_count", StringUtil.checkNull(rs.getString("part_count")));

		    SearchList2.add(SearchMap2);

		    count++;
		}
		if (count < 1 && plm_data.equals("ok")) {
		    SearchMap2 = new Hashtable<String, String>();
		    SearchMap2.put("partSqe", SearchMap.get("partSqe"));
		    SearchMap2.put("part_no", SearchMap.get("part_no"));
		    SearchMap2.put("part_name", SearchMap.get("part_name"));
		    SearchMap2.put("f_name", SearchMap.get("f_name"));
		    SearchMap2.put("usage", SearchMap.get("usage"));
		    SearchMap2.put("o_group_no", SearchMap.get("o_group_no"));

		    SearchMap2.put("case_count_1", "");
		    SearchMap2.put("case_count_2", "");
		    SearchMap2.put("pro_type", "empty");
		    SearchMap2.put("make", "");
		    SearchMap2.put("part_type", "신규");
		    SearchMap2.put("mix_group", "empty");
		    SearchMap2.put("net_1", "");
		    SearchMap2.put("net_2", "");
		    SearchMap2.put("net_3", "");
		    SearchMap2.put("meterial", "");
		    SearchMap2.put("t_size", "");
		    SearchMap2.put("w_size", "");
		    SearchMap2.put("p_size", "");
		    SearchMap2.put("plating", "");
		    SearchMap2.put("m_maker", "");
		    SearchMap2.put("m_mone", "");
		    SearchMap2.put("unitcost", "");
		    SearchMap2.put("unitcost_txt", "");
		    SearchMap2.put("grade_name", "");
		    SearchMap2.put("grade_color", "");
		    SearchMap2.put("order_con", "");
		    SearchMap2.put("h_weight", "");
		    SearchMap2.put("h_scrap", "");
		    SearchMap2.put("t_weight", "");
		    SearchMap2.put("recycle", "");
		    SearchMap2.put("die_no", "");
		    SearchMap2.put("cavity", "");
		    SearchMap2.put("m_su", "");
		    SearchMap2.put("mold_cost", "");
		    SearchMap2.put("mold_c_type", "감가");
		    SearchMap2.put("make_type", "empty");
		    SearchMap2.put("pro_1", "empty");
		    SearchMap2.put("ton", "");
		    SearchMap2.put("spm", "");
		    SearchMap2.put("method_type", "empty");
		    SearchMap2.put("pro_level", "표준");
		    SearchMap2.put("pro_level_txt", "");
		    SearchMap2.put("line_su", "");
		    SearchMap2.put("sul_cost", "");
		    SearchMap2.put("plating_type", "empty");
		    SearchMap2.put("type_2", "empty");
		    SearchMap2.put("plating_cost", "");
		    SearchMap2.put("type_1", "empty");
		    SearchMap2.put("t_mone", "empty");
		    SearchMap2.put("type_cost", "");
		    SearchMap2.put("t_order", "empty");
		    SearchMap2.put("pack_type", "empty");
		    SearchMap2.put("in_pack", "");
		    SearchMap2.put("out_pack", "");
		    SearchMap2.put("store", "");
		    SearchMap2.put("dest", "");
		    SearchMap2.put("dis_cost", "");
		    SearchMap2.put("distri_cost", "");
		    SearchMap2.put("royalty", "2");
		    SearchMap2.put("yazaki_ro", "");
		    SearchMap2.put("etc_cost", "");
		    SearchMap2.put("part_note", "");
		    SearchMap2.put("part_count", "a");
		    SearchMap2.put("IsPlm", "");

		    SearchList2.add(SearchMap2);
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (rs != null) {
		    rs.close();
		}
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}

	return SearchList2;
    }

    public int costRequestCreate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	int complet = 0;

	String pk_cr_group_1 = null;
	String rev_no = null;
	String group_no = null;
	String case_count_1 = null;
	String case_count_2 = null;
	String table_row = null;
	String pjt_no = null;
	String pjt_name = null;
	String team = null;
	String f_name = null;
	String a_name = null;
	String dev_step = null;
	String dr_step = null;
	String sub_step = null;
	String request_day = null;
	String request_txt = null;
	String pro_type = null;
	String make = null;
	String part_name = null;
	String part_type = null;
	String mix_group = null;
	String part_no = null;
	String net_1 = null;
	String net_2 = null;
	String net_3 = null;
	String usage = null;
	String meterial = null;
	String t_size = null;
	String w_size = null;
	String p_size = null;
	String plating = null;
	String m_maker = null;
	String m_mone = null;
	String unitcost = null;
	String unitcost_txt = null;
	String grade_name = null;
	String grade_color = null;
	String order_con = null;
	String h_weight = null;
	String h_scrap = null;
	String t_weight = null;
	String recycle = null;
	String die_no = null;
	String cavity = null;
	String m_su = null;
	String mold_cost = null;
	String mold_c_type = null;
	String make_type = null;
	String pro_1 = null;
	String ton = null;
	String spm = null;
	String method_type = null;
	String pro_level = null;
	String pro_level_txt = null;
	String line_su = null;
	String sul_cost = null;
	String plating_type = null;
	String type_2 = null;
	String plating_cost = null;
	String type_1 = null;
	String t_mone = null;
	String type_cost = null;
	String t_order = null;
	String pack_type = null;
	String in_pack = null;
	String out_pack = null;
	String store = null;
	String dest = null;
	String dis_cost = null;
	String distri_cost = null;
	String royalty = null;
	String yazaki_ro = null;
	String etc_cost = null;
	String part_note = null;
	String CRUD = null;
	
	int pk_pid = 0;
	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    CRUD = (String) SearchMap.get("CRUD");
	    //if (CRUD.equals("C")) {
		pk_cr_group_1 = (String) SearchMap.get("pk_cr_group_1");
		rev_no = (String) SearchMap.get("rev_no");
		group_no = (String) SearchMap.get("group_no");
		case_count_1 = (String) SearchMap.get("case_count_1");
		case_count_2 = (String) SearchMap.get("case_count_2");
		table_row = (String) SearchMap.get("table_row");
		pjt_no = (String) SearchMap.get("pjt_no");
		pjt_name = (String) SearchMap.get("pjt_name");
		team = (String) SearchMap.get("team");
		f_name = (String) SearchMap.get("f_name");
		a_name = (String) SearchMap.get("a_name");
		dev_step = (String) SearchMap.get("dev_step");
		dr_step = (String) SearchMap.get("dr_step");
		sub_step = (String) SearchMap.get("sub_step");
		request_day = (String) SearchMap.get("request_day");
		request_txt = (String) SearchMap.get("request_txt");
		pro_type = (String) SearchMap.get("pro_type");
		make = (String) SearchMap.get("make");
		part_name = (String) SearchMap.get("part_name");
		part_type = (String) SearchMap.get("part_type");
		mix_group = (String) SearchMap.get("mix_group");
		part_no = (String) SearchMap.get("part_no");
		net_1 = (String) SearchMap.get("net_1");
		net_2 = (String) SearchMap.get("net_2");
		net_3 = (String) SearchMap.get("net_3");
		usage = (String) SearchMap.get("usage");
		meterial = (String) SearchMap.get("meterial");
		t_size = (String) SearchMap.get("t_size");
		w_size = (String) SearchMap.get("w_size");
		p_size = (String) SearchMap.get("p_size");
		plating = (String) SearchMap.get("plating");
		m_maker = (String) SearchMap.get("m_maker");
		m_mone = (String) SearchMap.get("m_mone");
		unitcost = (String) SearchMap.get("unitcost");
		unitcost_txt = (String) SearchMap.get("unitcost_txt");
		grade_name = (String) SearchMap.get("grade_name");
		grade_color = (String) SearchMap.get("grade_color");
		order_con = (String) SearchMap.get("order_con");
		h_weight = (String) SearchMap.get("h_weight");
		h_scrap = (String) SearchMap.get("h_scrap");
		t_weight = (String) SearchMap.get("t_weight");
		recycle = (String) SearchMap.get("recycle");
		die_no = (String) SearchMap.get("die_no");
		cavity = (String) SearchMap.get("cavity");
		m_su = (String) SearchMap.get("m_su");
		mold_cost = (String) SearchMap.get("mold_cost");
		mold_c_type = (String) SearchMap.get("mold_c_type");
		make_type = (String) SearchMap.get("make_type");
		pro_1 = (String) SearchMap.get("pro_1");
		ton = (String) SearchMap.get("ton");
		spm = (String) SearchMap.get("spm");
		method_type = (String) SearchMap.get("method_type");
		pro_level = (String) SearchMap.get("pro_level");
		pro_level_txt = (String) SearchMap.get("pro_level_txt");
		line_su = (String) SearchMap.get("line_su");
		sul_cost = (String) SearchMap.get("sul_cost");
		plating_type = (String) SearchMap.get("plating_type");
		type_2 = (String) SearchMap.get("type_2");
		plating_cost = (String) SearchMap.get("plating_cost");
		type_1 = (String) SearchMap.get("type_1");
		t_mone = (String) SearchMap.get("t_mone");
		type_cost = (String) SearchMap.get("type_cost");
		t_order = (String) SearchMap.get("t_order");
		pack_type = (String) SearchMap.get("pack_type");
		in_pack = (String) SearchMap.get("in_pack");
		out_pack = (String) SearchMap.get("out_pack");
		store = (String) SearchMap.get("store");
		dest = (String) SearchMap.get("dest");
		dis_cost = (String) SearchMap.get("dis_cost");
		distri_cost = (String) SearchMap.get("distri_cost");
		royalty = (String) SearchMap.get("royalty");
		yazaki_ro = (String) SearchMap.get("yazaki_ro");
		etc_cost = (String) SearchMap.get("etc_cost");
		part_note = (String) SearchMap.get("part_note");
		sb.append(" SELECT productinfo_pk_pid.nextval as pk_pid");
		sb.append("   FROM DUAL");

		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    rs = pstmt.executeQuery();

		    while (rs.next()) {
			pk_pid = rs.getInt("pk_pid");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    sb.delete(0, sb.length());
		    if (rs != null) {
			rs.close();
		    }
		    if (pstmt != null) {
			pstmt.close();
		    }
		}

		pstmt = null;
		
		sb.append(" insert into COST_PRODUCTINFO (pk_pid,      pk_cr_group, rev_no,      group_no, case_count_1, case_count_2, table_row, ");
		sb.append("                               pjt_no,      pjt_name,    team,        f_name,   a_name,       dev_step,     dr_step,   ");
		sb.append("                               sub_step,    request_txt, pro_type,    make,     part_name,    part_type,    mix_group, ");
		sb.append("                               part_no,     net_1,       net_2,       net_3,    die_no,       part_note,    data_type  ");
		sb.append(" ) values (                                                                                ");
		sb.append("           ?,?,?,?,?,?,?,                                                                            ");
		sb.append("           ?,?,?,?,?,?,?,                                                                            ");
		sb.append("           ?,?,?,?,?,?,?,                                                                            ");
		sb.append("           ?,?,?,?,?,?,?  )                                                                          ");
		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setInt(1, pk_pid);
		    pstmt.setString(2, pk_cr_group_1);
		    pstmt.setString(3, rev_no);
		    pstmt.setString(4, group_no);
		    pstmt.setString(5, case_count_1);
		    pstmt.setString(6, case_count_2);
		    pstmt.setString(7, table_row);
		    pstmt.setString(8, pjt_no);
		    pstmt.setString(9, pjt_name);
		    pstmt.setString(10, team);
		    pstmt.setString(11, f_name);
		    pstmt.setString(12, a_name);
		    pstmt.setString(13, dev_step);
		    pstmt.setString(14, dr_step);
		    pstmt.setString(15, sub_step);
		    pstmt.setString(16, request_txt);
		    pstmt.setString(17, pro_type);
		    pstmt.setString(18, make);
		    pstmt.setString(19, part_name);
		    pstmt.setString(20, part_type);
		    pstmt.setString(21, mix_group);
		    pstmt.setString(22, part_no);
		    pstmt.setString(23, net_1);
		    pstmt.setString(24, net_2);
		    pstmt.setString(25, net_3);
		    pstmt.setString(26, die_no);
		    pstmt.setString(27, part_note);
		    pstmt.setString(28, "main");
		    complet = pstmt.executeUpdate() + complet;

		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    sb.delete(0, sb.length());
		    if (pstmt != null) {
			pstmt.close();
		    }
		    // if(conn!=null) {conn.close();}
		}

		pstmt = null;

		sb.append(" insert into cost_request (usage,         meterial,   t_size,      w_size,       p_size,      plating,      m_maker,     m_mone,      unitcost,  ");
		sb.append("                           unitcost_txt,  grade_name, grade_color, order_con,    h_weight,    h_scrap,      t_weight,    recycle,     cavity,    ");
		sb.append("                           m_su,          mold_cost,  mold_c_type, make_type,    pro_1,       ton,          spm,         method_type, pro_level, ");
		sb.append("                           pro_level_txt, line_su,    sul_cost,    plating_type, type_2,      plating_cost, type_1,      t_mone,      type_cost, ");
		sb.append("                           t_order,       pack_type,  in_pack,     out_pack,     store,       dest,         dis_cost,    distri_cost, royalty,   ");
		sb.append("                           yazaki_ro,     etc_cost,   fk_pid,      pk_cr,   request_day     ");
		sb.append("        ) values (                                                                                                                               ");
		sb.append(" 	   ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                        ");
		sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    ");
		sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    ");
		sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    ");
		sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    ");
		sb.append("        ?, ?, ?, request_pk_cr.nextval ,to_date(?,'YYYY-MM-DD')                                    )");

		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setString(1, usage);
		    pstmt.setString(2, meterial);
		    pstmt.setString(3, t_size);
		    pstmt.setString(4, w_size);
		    pstmt.setString(5, p_size);
		    pstmt.setString(6, plating);
		    pstmt.setString(7, m_maker);
		    pstmt.setString(8, m_mone);
		    pstmt.setString(9, unitcost);
		    pstmt.setString(10, unitcost_txt);
		    pstmt.setString(11, grade_name);
		    pstmt.setString(12, grade_color);
		    pstmt.setString(13, order_con);
		    pstmt.setString(14, h_weight);
		    pstmt.setString(15, h_scrap);
		    pstmt.setString(16, t_weight);
		    pstmt.setString(17, recycle);
		    pstmt.setString(18, cavity);
		    pstmt.setString(19, m_su);
		    pstmt.setString(20, mold_cost);
		    pstmt.setString(21, mold_c_type);
		    pstmt.setString(22, make_type);
		    pstmt.setString(23, pro_1);
		    pstmt.setString(24, ton);
		    pstmt.setString(25, spm);
		    pstmt.setString(26, method_type);
		    pstmt.setString(27, pro_level);
		    pstmt.setString(28, pro_level_txt);
		    pstmt.setString(29, line_su);
		    pstmt.setString(30, sul_cost);
		    pstmt.setString(31, plating_type);
		    pstmt.setString(32, type_2);
		    pstmt.setString(33, plating_cost);
		    pstmt.setString(34, type_1);
		    pstmt.setString(35, t_mone);
		    pstmt.setString(36, type_cost);
		    pstmt.setString(37, t_order);
		    pstmt.setString(38, pack_type);
		    pstmt.setString(39, in_pack);
		    pstmt.setString(40, out_pack);
		    pstmt.setString(41, store);
		    pstmt.setString(42, dest);
		    pstmt.setString(43, dis_cost);
		    pstmt.setString(44, distri_cost);
		    pstmt.setString(45, royalty);
		    pstmt.setString(46, yazaki_ro);
		    pstmt.setString(47, etc_cost);
		    pstmt.setInt(48, pk_pid);
		    pstmt.setString(49, request_day);

		    complet = pstmt.executeUpdate() + complet;

		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    sb.delete(0, sb.length());
		    if (pstmt != null) {
			pstmt.close();
		    }
		    // if(conn!=null) {conn.close();}
		}
//	    }
	}
	return complet;
    }

    public int costRequestCreate2(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	int complet = 0;

	String pk_cr_group_1 = null;
	String rev_pk = null;
	String rev_no = null;
	String group_no = null;
	String partSqe = null;

	String client_cost = null;
	String ket_cost = null;
	String target_cost = null;
	String customer_F = null;
	String customer_L = null;
	String su_year_1 = null;
	String su_year_2 = null;
	String su_year_3 = null;
	String su_year_4 = null;
	String su_year_5 = null;
	String su_year_6 = null;
	String su_year_7 = null;
	String su_year_8 = null;
	String car_type = null;
	int pk_pid = 0;
	String S_pk_pid = "";
	String[] parameters = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);

	    pk_cr_group_1 = (String) SearchMap.get("pk_cr_group_1");
	    partSqe = (String) SearchMap.get("partSqe");
	    rev_no = (String) SearchMap.get("rev_no");
	    rev_pk = (String) SearchMap.get("rev_pk");
	    group_no = (String) SearchMap.get("group_no");
	    client_cost = (String) SearchMap.get("client_cost");
	    ket_cost = (String) SearchMap.get("ket_cost");
	    target_cost = (String) SearchMap.get("target_cost");
	    customer_F = (String) SearchMap.get("customer_F");
	    customer_L = (String) SearchMap.get("customer_L");
	    su_year_1 = (String) SearchMap.get("su_year_1");
	    su_year_2 = (String) SearchMap.get("su_year_2");
	    su_year_3 = (String) SearchMap.get("su_year_3");
	    su_year_4 = (String) SearchMap.get("su_year_4");
	    su_year_5 = (String) SearchMap.get("su_year_5");
	    su_year_6 = (String) SearchMap.get("su_year_6");
	    su_year_7 = (String) SearchMap.get("su_year_7");
	    su_year_8 = (String) SearchMap.get("su_year_8");
	    car_type = (String) SearchMap.get("car_type");

	    sb.append(" select pk_pid from cost_productinfo  ");
	    sb.append("  where pk_cr_group          = ?      ");
	    sb.append("    and substr(group_no,1,4) = ?      ");
	    sb.append("    and rev_no               = (select max(rev_no) from cost_productinfo where pk_cr_group = ?)      ");
	    sb.append("    and data_type            = 'main' ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, pk_cr_group_1);
		pstmt.setString(2, group_no);
		pstmt.setString(3, pk_cr_group_1);
		rs = pstmt.executeQuery();

		while (rs.next()) {
		    S_pk_pid += Integer.toString(rs.getInt("pk_pid")) + "/";
		}
		parameters = S_pk_pid.split("/");
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (rs != null) {
		    rs.close();
		}
		if (pstmt != null) {
		    pstmt.close();
		}
	    }

	    pstmt = null;
	    for (int k = 0; k < parameters.length; k++) {
		S_pk_pid = parameters[k];
		pk_pid = Integer.parseInt(S_pk_pid);
		sb.append(" update cost_request ");
		sb.append("    set partSqe   = ?");
		sb.append("      , su_year_1 = ?");
		sb.append("      , su_year_2 = ?");
		sb.append("      , su_year_3 = ?");
		sb.append("      , su_year_4 = ?");
		sb.append("      , su_year_5 = ?");
		sb.append("      , su_year_6 = ?");
		sb.append("      , su_year_7 = ?");
		sb.append("      , su_year_8 = ?");
		sb.append("      , client_cost = ?");
		sb.append("      , ket_cost    = ?");
		sb.append("      , target_cost = ?");
		sb.append("  where fk_pid = ? ");

		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setString(1, partSqe);
		    pstmt.setString(2, su_year_1);
		    pstmt.setString(3, su_year_2);
		    pstmt.setString(4, su_year_3);
		    pstmt.setString(5, su_year_4);
		    pstmt.setString(6, su_year_5);
		    pstmt.setString(7, su_year_6);
		    pstmt.setString(8, su_year_7);
		    pstmt.setString(9, su_year_8);
		    pstmt.setString(10, client_cost);
		    pstmt.setString(11, ket_cost);
		    pstmt.setString(12, target_cost);
		    pstmt.setInt(13, pk_pid);

		    complet = pstmt.executeUpdate() + complet;

		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    sb.delete(0, sb.length());
		    if (pstmt != null) {
			pstmt.close();
		    }
		    // if(conn!=null) {conn.close();}
		}

		pstmt = null;

		sb.append(" update cost_productinfo ");
		sb.append("    set customer_F   = ?");
		sb.append("      , customer_L = ?");
		sb.append("      , rev_pk = ?");
		sb.append("      , car_type = ?");
		sb.append("  where pk_pid = ? ");

		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setString(1, customer_F);
		    pstmt.setString(2, customer_L);
		    pstmt.setString(3, rev_pk);
		    pstmt.setString(4, car_type);
		    pstmt.setInt(5, pk_pid);

		    complet = pstmt.executeUpdate() + complet;

		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    sb.delete(0, sb.length());
		    if (pstmt != null) {
			pstmt.close();
		    }
		    // if(conn!=null) {conn.close();}
		}
	    }

	}

	return complet;
    }
    
    /**
     * 함수명 : getMaxRevno 설명 :
     * 
     * @param
     * @return String
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2015. 10. 14.
     */
    public String getMaxRevno(String pjtno) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	String rev_no = "";
	sb.append("    SELECT TO_CHAR(NVL(MAX(REV_NO)+1,0)) AS REV_NO FROM COST_PRODUCTINFO WHERE PK_CR_GROUP = ?");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pjtno);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		rev_no = StringUtil.checkNull(rs.getString("REV_NO"));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	}
	
	return rev_no;
    }

    /**
     * 함수명 : getSearchStore 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchStore() throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("    SELECT distinct store");
	sb.append("      FROM distribution_cost");
	sb.append(" ORDER BY STORE");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("store", StringUtil.checkNull(rs.getString("store")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchStore 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchDest(String code, String child) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	code = java.net.URLDecoder.decode(code, "UTF-8");

	if (child.equals("store")) {
	    sb.append("    SELECT dest");
	    sb.append("      FROM distribution_cost");
	    sb.append("     WHERE STORE = ?");
	    sb.append(" ORDER BY dest");
	} else {
	    sb.append("    SELECT distri_cost");
	    sb.append("      FROM distribution_cost");
	    sb.append("     WHERE DEST = ?");
	}

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, code);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		if (child.equals("store")) {
		    SearchMap.put("dest", StringUtil.checkNull(rs.getString("dest")));
		} else {
		    SearchMap.put("distri_cost", StringUtil.checkNull(rs.getString("distri_cost")));
		}

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade(String m_maker, String meterial) throws Exception {
	// m_maker = java.net.URLDecoder.decode(m_maker, "utf-8");
	ArrayList SearchList = new ArrayList();

	Hashtable SearchMap = new Hashtable();
	SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	int cnt = 0;

	sb.append("    select s_gravity as s_gravity, s_gravity as s_gravity_1");
	sb.append("      from press_maker");
	sb.append("     where maker_name = ? and grade_name = ?");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, m_maker);
	    pstmt.setString(2, meterial);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		cnt++;
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("s_gravity", rs.getString("s_gravity"));
		SearchMap.put("s_gravity_1", rs.getString("s_gravity"));

		SearchList.add(SearchMap);
	    }
	    if (cnt < 1) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("s_gravity", "8.9");
		SearchMap.put("s_gravity_1", "8.55");

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade2 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade2() throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("    select Carrier_cost,Carrier_weight,Carrier_grade,Wire_cost");
	sb.append("      from bandolier_DB");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("Carrier_cost", StringUtil.checkNull(rs.getString("Carrier_cost")));
		SearchMap.put("Carrier_weight", StringUtil.checkNull(rs.getString("Carrier_weight")));
		SearchMap.put("Carrier_grade", StringUtil.checkNull(rs.getString("Carrier_grade")));
		SearchMap.put("Wire_cost", StringUtil.checkNull(rs.getString("Wire_cost")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade3 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade3() throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("    select distinct maker_name from mold_maker order by maker_name ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("maker_name", StringUtil.checkNull(rs.getString("maker_name")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade4 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade4(String maker_2) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append("select distinct material_name from mold_maker where maker_name = ? order by material_name  ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, maker_2);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("material_name", StringUtil.checkNull(rs.getString("material_name")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade5 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade5(String maker_2, String material_name) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append("select distinct grade_name from mold_maker where maker_name = ? and material_name = ? order by grade_name  ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, maker_2);
	    pstmt.setString(2, material_name);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade6 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade6(String grade_name) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append("select distinct grade_color from mold_maker where grade_name = ? order by grade_color  ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, grade_name);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("grade_color", StringUtil.checkNull(rs.getString("grade_color")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade7 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade7() throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select distinct maker_name from press_maker where maker_name is not null order by maker_name desc  ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("maker_name", StringUtil.checkNull(rs.getString("maker_name")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade8 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade8(String m_maker) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append("select grade_name from press_maker where maker_name = ? order by grade_name ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, m_maker);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade9 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade9() throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select grade_name from press_maker where maker_name = '풍산' order by grade_name");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchGrade10 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 15.
     */
    public ArrayList getSearchGrade10() throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select grade_name from press_maker where maker_name in ('풍산','-') order by grade_name");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getCostEditList 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 21.
     */
    public ArrayList getCostEditList(String pk_cr_group_1, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" select pk_cr_group,  pjt_no,    pjt_name,    team,         b.f_name,       a_name,    dev_step, ");
	sb.append("        dr_step,      sub_step,   to_char(Leader_day,'YYYY-MM-DD') as Leader_day, to_char(a.request_day,'YYYYMMDD') as request_day,  request_txt,  etc_note,   replace(file_1,'\\','/') file_1,   replace(file_2,'\\','/') file_2, replace(file_3,'\\','/') file_3");
	sb.append("   from cost_request a,cost_productinfo b,wfinfo c");
	sb.append("  where a.FK_PID = b.pk_pid and a.fk_wid = c.pk_wid(+) and pk_cr_group =? and group_no = 'T001' and data_type='main' and rev_no=?");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group_1);
	    pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("dr_step", StringUtil.checkNull(rs.getString("dr_step")));
		SearchMap.put("sub_step", StringUtil.checkNull(rs.getString("sub_step")));
		SearchMap.put("Leader_day", StringUtil.checkNull(rs.getString("Leader_day")));
		SearchMap.put("request_day", StringUtil.checkNull(rs.getString("request_day")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("etc_note", StringUtil.checkNull(rs.getString("etc_note")));
		SearchMap.put("file_1", StringUtil.checkNull(rs.getString("file_1")));
		SearchMap.put("file_2", StringUtil.checkNull(rs.getString("file_2")));
		SearchMap.put("file_3", StringUtil.checkNull(rs.getString("file_3")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : getCostEditList2 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 21.
     */
    public ArrayList getCostEditList2(String pk_cr_group_1, String data_type, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" select pk_cr,     pk_cr_group, partSqe,     group_no,  part_name,   part_no, car_type, su_year_1, su_year_2,");
	sb.append("        su_year_3, su_year_4,   su_year_5,   su_year_6, su_year_7,   su_year_8,");
	sb.append("        customer_F,customer_L,  client_cost, ket_cost,  target_cost, rev_pk, rev_no");
	sb.append("   from cost_request a,cost_productinfo b");
	sb.append("  where a.FK_PID = b.pk_pid and pk_cr_group =? and length(group_no) = 4 and data_type=? and rev_no=? order by group_no");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group_1);
	    pstmt.setString(2, data_type);
	    pstmt.setString(3, rev_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cr", StringUtil.checkNull(rs.getString("pk_cr")));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("partSqe", StringUtil.checkNull(rs.getString("partSqe")));
		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("part_no", StringUtil.checkNull(rs.getString("part_no")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("rev_pk", StringUtil.checkNull(rs.getString("rev_pk")));
		SearchMap.put("customer_L", StringUtil.checkNull(rs.getString("customer_L")));
		SearchMap.put("rev_no", StringUtil.checkNull(rs.getString("rev_no")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : getCostEditList3 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 21.
     */
    public ArrayList getCostEditList3(String pk_cr_group_1, String data_type, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" select case_count_1, case_count_2, pk_cr,       pk_cr_group,  make,         group_no,    pro_type,     part_name,   h_scrap,   t_weight,");
	sb.append("        part_type,    mix_group,    part_no,     net_1,        net_2,        net_3,       usage,        meterial,    t_size,    w_size,        p_size,");
	sb.append("        plating,      m_maker,      m_mone,      unitcost,     unitcost_txt, grade_name,  grade_color,  order_con,   h_weight,  recycle,       die_no,  cavity,");
	sb.append("        m_su,         mold_cost,    mold_c_type, make_type,    pro_1,        ton,         spm,          method_type, pro_level, pro_level_txt, line_su,");
	sb.append("        sul_cost,     plating_type, type_2,      plating_cost, type_1,       t_mone,      type_cost,    t_order,     pack_type, in_pack,       out_pack,");
	sb.append("        store,        dest,         dis_cost,    distri_cost,  royalty,      etc_cost,    yazaki_ro,    part_note,   pack_cost");
	sb.append("   from cost_request a,cost_productinfo b");
	sb.append("  where a.FK_PID = b.pk_pid and pk_cr_group =? and data_type=? and rev_no=? order by group_no");

	String g_sel1 = "";
	String g_sel2 = "";
	String g_sel3 = "";
	String group_no = "";
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group_1);
	    pstmt.setString(2, data_type);
	    pstmt.setString(3, rev_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		g_sel1 = "";
		g_sel2 = "";
		g_sel3 = "";
		group_no = StringUtil.checkNull(rs.getString("group_no"));
		if (group_no.length() < 5) {
		    g_sel1 = "/plm/jsp/cost/acc_img/add_red.jpg";
		} else if (group_no.length() < 8) {
		    g_sel2 = "/plm/jsp/cost/acc_img/add.jpg";
		} else {
		    g_sel3 = "/plm/jsp/cost/acc_img/add.jpg";
		}
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("case_count_1", StringUtil.checkNull(rs.getString("case_count_1")));
		SearchMap.put("case_count_2", StringUtil.checkNull(rs.getString("case_count_2")));
		SearchMap.put("pk_cr", StringUtil.checkNull(rs.getString("pk_cr")));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("make", StringUtil.checkNull(rs.getString("make")));
		SearchMap.put("group_no", group_no);
		SearchMap.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		SearchMap.put("g_sel1", g_sel1);
		SearchMap.put("g_sel2", g_sel2);
		SearchMap.put("g_sel3", g_sel3);
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("part_type", StringUtil.checkNull(rs.getString("part_type")));
		SearchMap.put("mix_group", StringUtil.checkNull(rs.getString("mix_group")));
		SearchMap.put("part_no", StringUtil.checkNull(rs.getString("part_no")));
		SearchMap.put("net_1", StringUtil.checkNull(rs.getString("net_1")));
		SearchMap.put("net_2", StringUtil.checkNull(rs.getString("net_2")));
		SearchMap.put("net_3", StringUtil.checkNull(rs.getString("net_3")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("meterial", StringUtil.checkNull(rs.getString("meterial")));
		SearchMap.put("t_size", StringUtil.checkNull(rs.getString("t_size")));
		SearchMap.put("w_size", StringUtil.checkNull(rs.getString("w_size")));
		SearchMap.put("p_size", StringUtil.checkNull(rs.getString("p_size")));
		SearchMap.put("plating", StringUtil.checkNull(rs.getString("plating")));
		SearchMap.put("m_maker", StringUtil.checkNull(rs.getString("m_maker")));
		SearchMap.put("m_mone", StringUtil.checkNull(rs.getString("m_mone")));
		SearchMap.put("unitcost", StringUtil.checkNull(rs.getString("unitcost")));
		SearchMap.put("unitcost_txt", StringUtil.checkNull(rs.getString("unitcost_txt")));
		SearchMap.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		SearchMap.put("grade_color", StringUtil.checkNull(rs.getString("grade_color")));
		SearchMap.put("order_con", StringUtil.checkNull(rs.getString("order_con")));
		SearchMap.put("h_weight", StringUtil.checkNull(rs.getString("h_weight")));
		SearchMap.put("h_scrap", StringUtil.checkNull(rs.getString("h_scrap")));
		SearchMap.put("t_weight", StringUtil.checkNull(rs.getString("t_weight")));
		SearchMap.put("recycle", StringUtil.checkNull(rs.getString("recycle")));
		SearchMap.put("die_no", StringUtil.checkNull(rs.getString("die_no")));
		SearchMap.put("cavity", StringUtil.checkNull(rs.getString("cavity")));
		SearchMap.put("m_su", StringUtil.checkNull(rs.getString("m_su")));
		SearchMap.put("mold_cost", StringUtil.checkNull(rs.getString("mold_cost")));
		SearchMap.put("mold_c_type", StringUtil.checkNull(rs.getString("mold_c_type")));
		SearchMap.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("ton", StringUtil.checkNull(rs.getString("ton")));
		SearchMap.put("spm", StringUtil.checkNull(rs.getString("spm")));
		SearchMap.put("method_type", StringUtil.checkNull(rs.getString("method_type")));
		SearchMap.put("pro_level", StringUtil.checkNull(rs.getString("pro_level")));
		SearchMap.put("pro_level_txt", StringUtil.checkNull(rs.getString("pro_level_txt")));
		SearchMap.put("line_su", StringUtil.checkNull(rs.getString("line_su")));
		SearchMap.put("sul_cost", StringUtil.checkNull(rs.getString("sul_cost")));
		SearchMap.put("plating_type", StringUtil.checkNull(rs.getString("plating_type")));
		SearchMap.put("type_2", StringUtil.checkNull(rs.getString("type_2")));
		SearchMap.put("plating_cost", StringUtil.checkNull(rs.getString("plating_cost")));
		SearchMap.put("type_1", StringUtil.checkNull(rs.getString("type_1")));
		SearchMap.put("t_mone", StringUtil.checkNull(rs.getString("t_mone")));
		SearchMap.put("type_cost", StringUtil.checkNull(rs.getString("type_cost")));
		SearchMap.put("t_order", StringUtil.checkNull(rs.getString("t_order")));
		SearchMap.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		SearchMap.put("pack_cost", StringUtil.checkNull(rs.getString("pack_cost")));
		SearchMap.put("in_pack", StringUtil.checkNull(rs.getString("in_pack")));
		SearchMap.put("out_pack", StringUtil.checkNull(rs.getString("out_pack")));
		SearchMap.put("store", StringUtil.checkNull(rs.getString("store")));
		SearchMap.put("dest", StringUtil.checkNull(rs.getString("dest")));
		SearchMap.put("dis_cost", StringUtil.checkNull(rs.getString("dis_cost")));
		SearchMap.put("distri_cost", StringUtil.checkNull(rs.getString("distri_cost")));
		SearchMap.put("royalty", StringUtil.checkNull(rs.getString("royalty")));
		SearchMap.put("etc_cost", StringUtil.checkNull(rs.getString("etc_cost")));
		SearchMap.put("yazaki_ro", StringUtil.checkNull(rs.getString("yazaki_ro")));
		SearchMap.put("part_note", StringUtil.checkNull(rs.getString("part_note")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    public int costRequestEditCreate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	int complet = 0;

	String pk_cr_group = null;
	String rev_no = null;
	String group_no = null;
	String case_count_1 = null;
	String case_count_2 = null;
	String table_row = null;
	String pjt_no = null;
	String pjt_name = null;
	String team = null;
	String f_name = null;
	String a_name = null;
	String dev_step = null;
	String dr_step = null;
	String sub_step = null;
	String request_day = null;
	String request_txt = null;
	String pro_type = null;
	String make = null;
	String part_name = null;
	String part_type = null;
	String mix_group = null;
	String part_no = null;
	String net_1 = null;
	String net_2 = null;
	String net_3 = null;
	String usage = null;
	String meterial = null;
	String t_size = null;
	String w_size = null;
	String p_size = null;
	String plating = null;
	String m_maker = null;
	String m_mone = null;
	String unitcost = null;
	String unitcost_txt = null;
	String grade_name = null;
	String grade_color = null;
	String order_con = null;
	String h_weight = null;
	String h_scrap = null;
	String t_weight = null;
	String recycle = null;
	String die_no = null;
	String cavity = null;
	String m_su = null;
	String mold_cost = null;
	String mold_c_type = null;
	String make_type = null;
	String pro_1 = null;
	String ton = null;
	String spm = null;
	String method_type = null;
	String pro_level = null;
	String pro_level_txt = null;
	String line_su = null;
	String sul_cost = null;
	String plating_type = null;
	String type_2 = null;
	String plating_cost = null;
	String type_1 = null;
	String t_mone = null;
	String type_cost = null;
	String t_order = null;
	String pack_type = null;
	String in_pack = null;
	String out_pack = null;
	String store = null;
	String dest = null;
	String dis_cost = null;
	String distri_cost = null;
	String royalty = null;
	String yazaki_ro = null;
	String etc_cost = null;
	String part_note = null;
	String data_type_txt = null;
	String group_case_count = null;
	String data_type = null;

	int pk_pid = 0;
	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    pk_cr_group = (String) SearchMap.get("pk_cr_group");
	    rev_no = (String) SearchMap.get("rev_no");
	    group_no = (String) SearchMap.get("group_no");
	    case_count_1 = (String) SearchMap.get("case_count_1");
	    case_count_2 = (String) SearchMap.get("case_count_2");
	    pro_type = (String) SearchMap.get("pro_type");
	    make = (String) SearchMap.get("make");
	    part_name = (String) SearchMap.get("part_name");
	    part_type = (String) SearchMap.get("part_type");
	    mix_group = (String) SearchMap.get("mix_group");
	    part_no = (String) SearchMap.get("part_no");
	    net_1 = (String) SearchMap.get("net_1");
	    net_2 = (String) SearchMap.get("net_2");
	    net_3 = (String) SearchMap.get("net_3");
	    usage = (String) SearchMap.get("usage");
	    meterial = (String) SearchMap.get("meterial");
	    t_size = (String) SearchMap.get("t_size");
	    w_size = (String) SearchMap.get("w_size");
	    p_size = (String) SearchMap.get("p_size");
	    plating = (String) SearchMap.get("plating");
	    m_maker = (String) SearchMap.get("m_maker");
	    m_mone = (String) SearchMap.get("m_mone");
	    unitcost = (String) SearchMap.get("unitcost");
	    unitcost_txt = (String) SearchMap.get("unitcost_txt");
	    grade_name = (String) SearchMap.get("grade_name");
	    grade_color = (String) SearchMap.get("grade_color");
	    order_con = (String) SearchMap.get("order_con");
	    h_weight = (String) SearchMap.get("h_weight");
	    h_scrap = (String) SearchMap.get("h_scrap");
	    t_weight = (String) SearchMap.get("t_weight");
	    recycle = (String) SearchMap.get("recycle");
	    die_no = (String) SearchMap.get("die_no");
	    cavity = (String) SearchMap.get("cavity");
	    m_su = (String) SearchMap.get("m_su");
	    mold_cost = (String) SearchMap.get("mold_cost");
	    mold_c_type = (String) SearchMap.get("mold_c_type");
	    make_type = (String) SearchMap.get("make_type");
	    pro_1 = (String) SearchMap.get("pro_1");
	    ton = (String) SearchMap.get("ton");
	    spm = (String) SearchMap.get("spm");
	    method_type = (String) SearchMap.get("method_type");
	    pro_level = (String) SearchMap.get("pro_level");
	    pro_level_txt = (String) SearchMap.get("pro_level_txt");
	    line_su = (String) SearchMap.get("line_su");
	    sul_cost = (String) SearchMap.get("sul_cost");
	    plating_type = (String) SearchMap.get("plating_type");
	    type_2 = (String) SearchMap.get("type_2");
	    plating_cost = (String) SearchMap.get("plating_cost");
	    type_1 = (String) SearchMap.get("type_1");
	    t_mone = (String) SearchMap.get("t_mone");
	    type_cost = (String) SearchMap.get("type_cost");
	    t_order = (String) SearchMap.get("t_order");
	    pack_type = (String) SearchMap.get("pack_type");
	    in_pack = (String) SearchMap.get("in_pack");
	    out_pack = (String) SearchMap.get("out_pack");
	    store = (String) SearchMap.get("store");
	    dest = (String) SearchMap.get("dest");
	    dis_cost = (String) SearchMap.get("dis_cost");
	    distri_cost = (String) SearchMap.get("distri_cost");
	    royalty = (String) SearchMap.get("royalty");
	    yazaki_ro = (String) SearchMap.get("yazaki_ro");
	    etc_cost = (String) SearchMap.get("etc_cost");
	    part_note = (String) SearchMap.get("part_note");
	    data_type_txt = (String) SearchMap.get("data_type_txt");
	    group_case_count = (String) SearchMap.get("group_case_count");
	    data_type = (String) SearchMap.get("data_type");

	    sb.append(" SELECT productinfo_pk_pid.nextval as pk_pid");
	    sb.append("   FROM DUAL");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		rs = pstmt.executeQuery();

		while (rs.next()) {
		    pk_pid = rs.getInt("pk_pid");
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (rs != null) {
		    rs.close();
		}
		if (pstmt != null) {
		    pstmt.close();
		}
	    }

	    pstmt = null;

	    sb.append("  INSERT INTO COST_PRODUCTINFO (                                   ");
	    sb.append("    PK_PID,       PK_CR_GROUP, REV_NO,         GROUP_NO,  CASE_COUNT_1,    ");
	    sb.append("    CASE_COUNT_2, DATA_TYPE,   CASE_TYPE_USER, PRO_TYPE,  MAKE,            ");
	    sb.append("    PART_TYPE,    MIX_GROUP,   PART_NO,                                    ");
	    sb.append("    PART_NAME,    PART_NOTE,   NET_1,          NET_2,     NET_3,  DIE_NO)  ");
	    sb.append(" VALUES (?,        ?, ?, ?, nvl(?,0),                                             ");
	    sb.append("         nvl(?,0), ?, ?, ?, ?,                                                    ");
	    sb.append("         ?,        ?, ?,                                                          ");
	    sb.append("         ?,        ?, ?, ?, ?, ?                                                  ");
	    sb.append("        )                                                                 ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setInt(1, pk_pid);
		pstmt.setString(2, pk_cr_group);
		pstmt.setInt(3, Integer.parseInt(rev_no));
		pstmt.setString(4, group_no);
		pstmt.setInt(5, Integer.parseInt(case_count_1));
		pstmt.setInt(6, Integer.parseInt(case_count_2));
		pstmt.setString(7, data_type_txt);
		pstmt.setInt(8, Integer.parseInt(group_case_count));
		pstmt.setString(9, pro_type);
		pstmt.setString(10, make);
		pstmt.setString(11, part_type);
		pstmt.setString(12, mix_group);
		pstmt.setString(13, part_no);
		pstmt.setString(14, part_name);
		pstmt.setString(15, part_note);
		pstmt.setString(16, net_1);
		pstmt.setString(17, net_2);
		pstmt.setString(18, net_3);
		pstmt.setString(19, die_no);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	    pstmt = null;

	    sb.append(" INSERT INTO COST_REQUEST (                                                                                                  ");
	    sb.append("    PK_CR,        FK_PID,       ROYALTY,   STORE,     USAGE,        METERIAL,    T_SIZE,      W_SIZE,      P_SIZE,        ");
	    sb.append("    PLATING,      M_MAKER,      M_MONE,    UNITCOST,  UNITCOST_TXT, GRADE_NAME,  GRADE_COLOR, ORDER_CON,   H_WEIGHT,  H_SCRAP,       ");
	    sb.append("    T_WEIGHT,     RECYCLE,      CAVITY,    MAKE_TYPE, PRO_1,        TON,         SPM,         METHOD_TYPE, PRO_LEVEL, PRO_LEVEL_TXT, ");
	    sb.append("    PLATING_TYPE, PLATING_COST, TYPE_1,    TYPE_2,    T_MONE,       TYPE_COST,   T_ORDER,     PACK_TYPE,   IN_PACK,   OUT_PACK,      ");
	    sb.append("    DIS_COST,     DISTRI_COST,  YAZAKI_RO, M_SU,      MOLD_COST,    MOLD_C_TYPE, LINE_SU,     SUL_COST,    ETC_COST,  DEST)                 ");
	    sb.append(" VALUES (request_pk_cr.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                               ");
	    sb.append("         ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                               ");
	    sb.append("         ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                               ");
	    sb.append("         ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                               ");
	    sb.append("         ?, ?, ?, ?, ?, ?, ?, ?, ?)  ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setInt(1, pk_pid);
		pstmt.setString(2, royalty);
		pstmt.setString(3, store);
		pstmt.setString(4, usage);
		pstmt.setString(5, meterial);
		pstmt.setString(6, t_size);
		pstmt.setString(7, w_size);
		pstmt.setString(8, p_size);
		pstmt.setString(9, plating);
		pstmt.setString(10, m_maker);
		pstmt.setString(11, m_mone);
		pstmt.setString(12, unitcost);
		pstmt.setString(13, unitcost_txt);
		pstmt.setString(14, grade_name);
		pstmt.setString(15, grade_color);
		pstmt.setString(16, order_con);
		pstmt.setString(17, h_weight);
		pstmt.setString(18, h_scrap);
		pstmt.setString(19, t_weight);
		pstmt.setString(20, recycle);
		pstmt.setString(21, cavity);
		pstmt.setString(22, make_type);
		pstmt.setString(23, pro_1);
		pstmt.setString(24, ton);
		pstmt.setString(25, spm);
		pstmt.setString(26, method_type);
		pstmt.setString(27, pro_level);
		pstmt.setString(28, pro_level_txt);
		pstmt.setString(29, plating_type);
		pstmt.setString(30, plating_cost);
		pstmt.setString(31, type_1);
		pstmt.setString(32, type_2);
		pstmt.setString(33, t_mone);
		pstmt.setString(34, type_cost);
		pstmt.setString(35, t_order);
		pstmt.setString(36, pack_type);
		pstmt.setString(37, in_pack);
		pstmt.setString(38, out_pack);
		pstmt.setString(39, dis_cost);
		pstmt.setString(40, distri_cost);
		pstmt.setString(41, yazaki_ro);
		pstmt.setString(42, m_su);
		pstmt.setString(43, mold_cost);
		pstmt.setString(44, mold_c_type);
		pstmt.setString(45, line_su);
		pstmt.setString(46, sul_cost);
		pstmt.setString(47, etc_cost);
		pstmt.setString(48, dest);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
	return complet;
    }

    public int costRequestUpdate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	int complet = 0;

	String pk_cr_group_1 = null;
	String rev_pk = null;
	String rev_no = null;
	String group_no = null;
	String partSqe = null;

	String client_cost = null;
	String ket_cost = null;
	String target_cost = null;
	String customer_F = null;
	String customer_L = null;
	String su_year_1 = null;
	String su_year_2 = null;
	String su_year_3 = null;
	String su_year_4 = null;
	String su_year_5 = null;
	String su_year_6 = null;
	String su_year_7 = null;
	String su_year_8 = null;
	String car_type = null;
	String rowCount = null;
	String data_type_txt = null;
	String group_case_count = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);

	    pk_cr_group_1 = (String) SearchMap.get("pk_cr_group_1");
	    partSqe = (String) SearchMap.get("partSqe");
	    rev_no = (String) SearchMap.get("rev_no");
	    rev_pk = (String) SearchMap.get("rev_pk");
	    group_no = (String) SearchMap.get("group_no");
	    client_cost = (String) SearchMap.get("client_cost");
	    ket_cost = (String) SearchMap.get("ket_cost");
	    target_cost = (String) SearchMap.get("target_cost");
	    customer_F = (String) SearchMap.get("customer_F");
	    customer_L = (String) SearchMap.get("customer_L");
	    su_year_1 = (String) SearchMap.get("su_year_1");
	    su_year_2 = (String) SearchMap.get("su_year_2");
	    su_year_3 = (String) SearchMap.get("su_year_3");
	    su_year_4 = (String) SearchMap.get("su_year_4");
	    su_year_5 = (String) SearchMap.get("su_year_5");
	    su_year_6 = (String) SearchMap.get("su_year_6");
	    su_year_7 = (String) SearchMap.get("su_year_7");
	    su_year_8 = (String) SearchMap.get("su_year_8");
	    car_type = (String) SearchMap.get("car_type");
	    rowCount = (String) SearchMap.get("rowCount");
	    data_type_txt = (String) SearchMap.get("data_type_txt");

	    pstmt = null;

	    sb.append(" update cost_request ");
	    sb.append("    set partSqe   = ?");
	    sb.append("      , su_year_1 = ?");
	    sb.append("      , su_year_2 = ?");
	    sb.append("      , su_year_3 = ?");
	    sb.append("      , su_year_4 = ?");
	    sb.append("      , su_year_5 = ?");
	    sb.append("      , su_year_6 = ?");
	    sb.append("      , su_year_7 = ?");
	    sb.append("      , su_year_8 = ?");
	    sb.append("      , client_cost = ?");
	    sb.append("      , ket_cost    = ?");
	    sb.append("      , target_cost = ?");
	    sb.append("  where fk_pid in (select pk_pid from COST_productinfo");
	    sb.append("                    where pk_cr_group = ?  ");
	    sb.append("                      and group_no    = ?  ");
	    sb.append("                      and rev_no      = ?  ");
	    sb.append("                      and data_type = ?) ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, partSqe);
		pstmt.setString(2, su_year_1);
		pstmt.setString(3, su_year_2);
		pstmt.setString(4, su_year_3);
		pstmt.setString(5, su_year_4);
		pstmt.setString(6, su_year_5);
		pstmt.setString(7, su_year_6);
		pstmt.setString(8, su_year_7);
		pstmt.setString(9, su_year_8);
		pstmt.setString(10, client_cost);
		pstmt.setString(11, ket_cost);
		pstmt.setString(12, target_cost);
		pstmt.setString(13, pk_cr_group_1);
		pstmt.setString(14, group_no);
		pstmt.setString(15, rev_no);
		pstmt.setString(16, data_type_txt);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	    pstmt = null;

	    sb.append(" update cost_productinfo ");
	    sb.append("    set customer_F   = ?");
	    sb.append("      , customer_L = ?");
	    sb.append("      , rev_pk = ?");
	    sb.append("      , car_type = ?");
	    sb.append("      , table_row = ?");
	    // sb.append("      , case_type_user = ?");
	    sb.append(" where pk_cr_group = ?  ");
	    sb.append("   and rev_no      = ?  ");
	    sb.append("   and data_type  = ? ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, customer_F);
		pstmt.setString(2, customer_L);
		pstmt.setString(3, rev_pk);
		pstmt.setString(4, car_type);
		pstmt.setString(5, rowCount);
		// pstmt.setString(6, group_case_count);
		pstmt.setString(6, pk_cr_group_1);
		pstmt.setString(7, rev_no);
		pstmt.setString(8, data_type_txt);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}

	return complet;
    }

    public int costRequestUpdate2(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	int complet = 0;

	String pjt_no = null;
	String pjt_name = null;
	String team = null;
	String f_name = null;
	String a_name = null;
	String dev_step = null;
	String dr_step = null;
	String sub_step = null;
	String request_txt = null;
	String request_day = null;
	String pk_cr_group_1 = null;
	String rev_no = null;
	String data_type_txt = null;
	String group_case_count = null;

	System.out.println("costRequestUpdate2 == 1");

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);

	    pjt_no = (String) SearchMap.get("pjt_no");
	    pjt_name = (String) SearchMap.get("pjt_name");
	    team = (String) SearchMap.get("team");
	    f_name = (String) SearchMap.get("f_name");
	    a_name = (String) SearchMap.get("a_name");
	    dev_step = (String) SearchMap.get("dev_step");
	    dr_step = (String) SearchMap.get("dr_step");
	    sub_step = (String) SearchMap.get("sub_step");
	    request_txt = (String) SearchMap.get("request_txt");
	    request_day = (String) SearchMap.get("request_day");
	    pk_cr_group_1 = (String) SearchMap.get("pk_cr_group_1");
	    rev_no = (String) SearchMap.get("rev_no");
	    data_type_txt = (String) SearchMap.get("data_type_txt");
	    group_case_count = (String) SearchMap.get("group_case_count");

	    pstmt = null;
	    System.out.println("pjt_no : " + pjt_no);
	    System.out.println("pjt_name : " + pjt_name);
	    System.out.println("team : " + team);
	    System.out.println("f_name : " + f_name);
	    System.out.println("a_name : " + a_name);
	    System.out.println("dev_step : " + dev_step);
	    System.out.println("dr_step : " + dr_step);
	    System.out.println("request_day : " + request_day);
	    System.out.println("request_txt : " + request_txt);

	    sb.append(" update cost_productinfo ");
	    sb.append("    set pjt_no   = ?");
	    sb.append("      , pjt_name = ?");
	    sb.append("      , team     = ?");
	    sb.append("      , f_name   = ?");
	    sb.append("      , a_name   = ?");
	    sb.append("      , dev_step = ?");
	    sb.append("      , dr_step  = ?");
	    sb.append("      , sub_step = ?");
	    sb.append("      , request_txt = ? ");
	    sb.append("  where pk_cr_group = ?  ");
	    sb.append("    and rev_no      = ?  ");
	    // sb.append("    and data_type   = ?  ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, pjt_no);
		pstmt.setString(2, pjt_name);
		pstmt.setString(3, team);
		pstmt.setString(4, f_name);
		pstmt.setString(5, a_name);
		pstmt.setString(6, dev_step);
		pstmt.setString(7, dr_step);
		pstmt.setString(8, sub_step);
		pstmt.setString(9, request_txt);
		pstmt.setString(10, pk_cr_group_1);
		pstmt.setString(11, rev_no);
		// pstmt.setString(12, data_type_txt );

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	    System.out.println("costRequestUpdate2 ==2");
	    pstmt = null;

	    sb.append(" update cost_productinfo ");
	    sb.append("    set case_type_user   = ?");
	    sb.append(" where pk_cr_group = ?  ");
	    sb.append("   and rev_no      = ?  ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, group_case_count);
		pstmt.setString(2, pk_cr_group_1);
		pstmt.setString(3, rev_no);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	    System.out.println("costRequestUpdate3 == 1");
	    pstmt = null;

	    sb.append(" update cost_request ");
	    sb.append("    set request_day   = to_date(?,'YYYY-MM-DD')");
	    sb.append("where fk_pid in (select pk_pid from cost_productinfo where  pk_cr_group = ?  and rev_no  = ?)");

	    System.out.println("pk_cr_group : " + pk_cr_group_1);
	    System.out.println("rev_no : " + rev_no);

	    try {
		pstmt = conn.prepareStatement(sb.toString());

		pstmt.setString(1, request_day);
		pstmt.setString(2, pk_cr_group_1);
		pstmt.setString(3, rev_no);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	    System.out.println("costRequestUpdate4 == 1");
	}

	return complet;
    }

    public int costCaseDelete(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	int complet = 0;

	String pk_cr = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);

	    pk_cr = (String) SearchMap.get("pk_cr");

	    pstmt = null;

	    sb.append(" delete cost_productinfo");
	    sb.append("  where pk_pid in (select fk_pid from cost_request where pk_cr = ?) ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, pk_cr);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}

	return complet;
    }

    public int costRequestUpdate3(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	int complet = 0;

	String group_case_count = null;
	String pk_cr_group_1 = null;
	String rev_no = null;

	int n_group_case_count = 0;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);

	    group_case_count = (String) SearchMap.get("group_case_count");
	    n_group_case_count = Integer.parseInt(group_case_count);

	    pk_cr_group_1 = (String) SearchMap.get("pk_cr_group_1");
	    rev_no = (String) SearchMap.get("rev_no");

	    pstmt = null;

	    sb.append(" update cost_productinfo ");
	    sb.append("    set case_type_user   = ?");
	    sb.append("  where pk_cr_group = ?  ");
	    sb.append("    and rev_no      = ?  ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setInt(1, n_group_case_count - 1);
		pstmt.setString(2, pk_cr_group_1);
		pstmt.setString(3, rev_no);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	    pstmt = null;

	    if (n_group_case_count > 0) {
		int data_type_txt_temp = n_group_case_count - 2;
		String data_type_txt = "case" + Integer.toString(data_type_txt_temp);

		sb.append(" update cost_productinfo ");
		sb.append("    set data_type   = ?");
		sb.append("  where pk_cr_group = ?  ");
		sb.append("    and rev_no      = ?  ");
		sb.append("    and substr(data_type,0,4) = 'case' ");

		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setString(1, data_type_txt);
		    pstmt.setString(2, pk_cr_group_1);
		    pstmt.setString(3, rev_no);

		    complet = pstmt.executeUpdate() + complet;

		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    sb.delete(0, sb.length());
		    if (pstmt != null) {
			pstmt.close();
		    }
		    // if(conn!=null) {conn.close();}
		}
	    }
	}

	return complet;
    }

    public int costReEditSave(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	int complet = 0;

	String pk_cr_group_1 = "";
	String rev_no = "";
	String group_no = "";
	String case_count_1 = "";
	String case_count_2 = "";
	String pro_type = "";
	String make = "";
	String part_name = "";
	String part_type = "";
	String mix_group = "";
	String part_no = "";
	String net_1 = "";
	String net_2 = "";
	String net_3 = "";
	String usage = "";
	String meterial = "";
	String t_size = "";
	String w_size = "";
	String p_size = "";
	String plating = "";
	String m_maker = "";
	String m_mone = "";
	String unitcost = "";
	String unitcost_txt = "";
	String grade_name = "";
	String grade_color = "";
	String order_con = "";
	String h_weight = "";
	String h_scrap = "";
	String t_weight = "";
	String recycle = "";
	String die_no = "";
	String cavity = "";
	String m_su = "";
	String mold_cost = "";
	String mold_c_type = "";
	String make_type = "";
	String pro_1 = "";
	String ton = "";
	String spm = "";
	String method_type = "";
	String pro_level = "";
	String pro_level_txt = "";
	String line_su = "";
	String sul_cost = "";
	String plating_type = "";
	String type_2 = "";
	String plating_cost = "";
	String type_1 = "";
	String t_mone = "";
	String type_cost = "";
	String t_order = "";
	String pack_type = "";
	String in_pack = "";
	String out_pack = "";
	String store = "";
	String dest = "";
	String dis_cost = "";
	String distri_cost = "";
	String royalty = "";
	String yazaki_ro = "";
	String etc_cost = "";
	String part_note = "";
	String data_type = "";
	String table_row = "";
	String pack_cost = "";

	int pk_pid = 0;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    pk_cr_group_1 = (String) SearchMap.get("pk_cr_group_1");
	    rev_no = (String) SearchMap.get("rev_no");
	    group_no = (String) SearchMap.get("group_no");
	    case_count_1 = (String) SearchMap.get("case_count_1");
	    case_count_2 = (String) SearchMap.get("case_count_2");
	    pro_type = (String) SearchMap.get("pro_type");
	    make = (String) SearchMap.get("make");
	    part_name = (String) SearchMap.get("part_name");
	    part_type = (String) SearchMap.get("part_type");
	    mix_group = (String) SearchMap.get("mix_group");
	    part_no = (String) SearchMap.get("part_no");
	    net_1 = (String) SearchMap.get("net_1");
	    net_2 = (String) SearchMap.get("net_2");
	    net_3 = (String) SearchMap.get("net_3");
	    usage = (String) SearchMap.get("usage");
	    meterial = (String) SearchMap.get("meterial");
	    t_size = (String) SearchMap.get("t_size");
	    w_size = (String) SearchMap.get("w_size");
	    p_size = (String) SearchMap.get("p_size");
	    plating = (String) SearchMap.get("plating");
	    m_maker = (String) SearchMap.get("m_maker");
	    m_mone = (String) SearchMap.get("m_mone");
	    unitcost = (String) SearchMap.get("unitcost");
	    unitcost_txt = (String) SearchMap.get("unitcost_txt");
	    grade_name = (String) SearchMap.get("grade_name");
	    grade_color = (String) SearchMap.get("grade_color");
	    order_con = (String) SearchMap.get("order_con");
	    h_weight = (String) SearchMap.get("h_weight");
	    h_scrap = (String) SearchMap.get("h_scrap");
	    t_weight = (String) SearchMap.get("t_weight");
	    recycle = (String) SearchMap.get("recycle");
	    die_no = (String) SearchMap.get("die_no");
	    cavity = (String) SearchMap.get("cavity");
	    m_su = (String) SearchMap.get("m_su");
	    mold_cost = (String) SearchMap.get("mold_cost");
	    mold_c_type = (String) SearchMap.get("mold_c_type");
	    make_type = (String) SearchMap.get("make_type");
	    pro_1 = (String) SearchMap.get("pro_1");
	    ton = (String) SearchMap.get("ton");
	    spm = (String) SearchMap.get("spm");
	    method_type = (String) SearchMap.get("method_type");
	    pro_level = (String) SearchMap.get("pro_level");
	    pro_level_txt = (String) SearchMap.get("pro_level_txt");
	    line_su = (String) SearchMap.get("line_su");
	    sul_cost = (String) SearchMap.get("sul_cost");
	    plating_type = (String) SearchMap.get("plating_type");
	    type_2 = (String) SearchMap.get("type_2");
	    plating_cost = (String) SearchMap.get("plating_cost");
	    type_1 = (String) SearchMap.get("type_1");
	    t_mone = (String) SearchMap.get("t_mone");
	    type_cost = (String) SearchMap.get("type_cost");
	    t_order = (String) SearchMap.get("t_order");
	    pack_type = (String) SearchMap.get("pack_type");
	    in_pack = (String) SearchMap.get("in_pack");
	    out_pack = (String) SearchMap.get("out_pack");
	    store = (String) SearchMap.get("store");
	    dest = (String) SearchMap.get("dest");
	    dis_cost = (String) SearchMap.get("dis_cost");
	    distri_cost = (String) SearchMap.get("distri_cost");
	    royalty = (String) SearchMap.get("royalty");
	    yazaki_ro = (String) SearchMap.get("yazaki_ro");
	    etc_cost = (String) SearchMap.get("etc_cost");
	    part_note = (String) SearchMap.get("part_note");
	    data_type = (String) SearchMap.get("data_type");
	    table_row = (String) SearchMap.get("table_row");
	    pack_cost = (String) SearchMap.get("pack_cost");

	    sb.append(" SELECT productinfo_pk_pid.nextval as pk_pid");
	    sb.append("   FROM DUAL");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		rs = pstmt.executeQuery();

		while (rs.next()) {
		    pk_pid = rs.getInt("pk_pid");
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (rs != null) {
		    rs.close();
		}
		if (pstmt != null) {
		    pstmt.close();
		}
	    }

	    pstmt = null;

	    sb.append(" INSERT INTO cost_productinfo (                                 ");
	    sb.append("  pk_cr_group  ,rev_no     ,group_no  ,case_count_1             ");
	    sb.append(" ,case_count_2 ,pro_type   ,make      ,part_name                ");
	    sb.append(" ,part_type    ,mix_group  ,part_no   ,net_1                    ");
	    sb.append(" ,net_2        ,net_3      ,die_no    ,part_note    ,data_type,  pk_pid, table_row  ");
	    sb.append(" )VALUES(                                                       ");
	    sb.append(" 	? , ? , ? , ?                                              ");
	    sb.append("    ,? , ? , ? , ?                                          ");
	    sb.append("    ,? , ? , ? , ?                                          ");
	    sb.append("    ,? , ? , ? , ?, ?, ?, ?)                                      ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, pk_cr_group_1);
		pstmt.setString(2, rev_no);
		pstmt.setString(3, group_no);
		pstmt.setString(4, case_count_1);
		pstmt.setString(5, case_count_2);
		pstmt.setString(6, pro_type);
		pstmt.setString(7, make);
		pstmt.setString(8, part_name);
		pstmt.setString(9, part_type);
		pstmt.setString(10, mix_group);
		pstmt.setString(11, part_no);
		pstmt.setString(12, net_1);
		pstmt.setString(13, net_2);
		pstmt.setString(14, net_3);
		pstmt.setString(15, die_no);
		pstmt.setString(16, part_note);
		pstmt.setString(17, data_type);
		pstmt.setInt(18, pk_pid);
		pstmt.setString(19, table_row);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	    pstmt = null;

	    sb.append(" INSERT INTO cost_request(                                                             ");
	    sb.append(" 	unitcost,    m_mone,       m_maker,     plating,    p_size,        w_size,        ");
	    sb.append(" 	t_size,      meterial,     usage,       etc_cost,   yazaki_ro,     royalty,       ");
	    sb.append(" 	distri_cost, dis_cost,     dest,        store,      out_pack,      in_pack,       ");
	    sb.append(" 	pack_type,   t_order,      type_cost,   t_mone,     type_1,        plating_cost,  ");
	    sb.append(" 	type_2,      plating_type, sul_cost,    line_su,    pro_level_txt, pro_level,     ");
	    sb.append(" 	spm,         ton,          method_type, pro_1,      make_type,     mold_c_type,   ");
	    sb.append(" 	mold_cost,   m_su,         cavity,      recycle,    t_weight,      h_scrap,       ");
	    sb.append(" 	h_weight,    order_con,    grade_color, grade_name, unitcost_txt,  pk_cr,  fk_pid , pack_cost ");
	    sb.append(" )VALUES(                                                                              ");
	    sb.append(" 	? , ? , ? , ? , ? , ?                                                             ");
	    sb.append("    ,? , ? , ? , ? , ? , ?                                                             ");
	    sb.append("    ,? , ? , ? , ? , ? , ?                                                         ");
	    sb.append("    ,? , ? , ? , ? , ? , ?                                                         ");
	    sb.append("    ,? , ? , ? , ? , ? , ?                                                         ");
	    sb.append("    ,? , ? , ? , ? , ? , ?                                                         ");
	    sb.append("    ,? , ? , ? , ? , ? , ?                                                         ");
	    sb.append("    ,? , ? , ? , ? , ? , request_pk_cr.nextval, ? ,?)                              ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, unitcost);
		pstmt.setString(2, m_mone);
		pstmt.setString(3, m_maker);
		pstmt.setString(4, plating);
		pstmt.setString(5, p_size);
		pstmt.setString(6, w_size);
		pstmt.setString(7, t_size);
		pstmt.setString(8, meterial);
		pstmt.setString(9, usage);
		pstmt.setString(10, etc_cost);
		pstmt.setString(11, yazaki_ro);
		pstmt.setString(12, royalty);
		pstmt.setString(13, distri_cost);
		pstmt.setString(14, dis_cost);
		pstmt.setString(15, dest);
		pstmt.setString(16, store);
		pstmt.setString(17, out_pack);
		pstmt.setString(18, in_pack);
		pstmt.setString(19, pack_type);
		pstmt.setString(20, t_order);
		pstmt.setString(21, type_cost);
		pstmt.setString(22, t_mone);
		pstmt.setString(23, type_1);
		pstmt.setString(24, plating_cost);
		pstmt.setString(25, type_2);
		pstmt.setString(26, plating_type);
		pstmt.setString(27, sul_cost);
		pstmt.setString(28, line_su);
		pstmt.setString(29, pro_level_txt);
		pstmt.setString(30, pro_level);
		pstmt.setString(31, spm);
		pstmt.setString(32, ton);
		pstmt.setString(33, method_type);
		pstmt.setString(34, pro_1);
		pstmt.setString(35, make_type);
		pstmt.setString(36, mold_c_type);
		pstmt.setString(37, mold_cost);
		pstmt.setString(38, m_su);
		pstmt.setString(39, cavity);
		pstmt.setString(40, recycle);
		pstmt.setString(41, t_weight);
		pstmt.setString(42, h_scrap);
		pstmt.setString(43, h_weight);
		pstmt.setString(44, order_con);
		pstmt.setString(45, grade_color);
		pstmt.setString(46, grade_name);
		pstmt.setString(47, unitcost_txt);
		pstmt.setInt(48, pk_pid);
		pstmt.setString(49, pack_cost);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
	return complet;
    }

    public int costReEditUpdate(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;

	String group_no = "";
	String case_count_1 = "";
	String case_count_2 = "";
	String pro_type = "";
	String make = "";
	String part_name = "";
	String part_type = "";
	String mix_group = "";
	String part_no = "";
	String net_1 = "";
	String net_2 = "";
	String net_3 = "";
	String die_no = "";
	String part_note = "";
	String etc_note = "";
	String pk_cr = "";

	String etc_cost = "";
	String yazaki_ro = "";
	String royalty = "";
	String distri_cost = "";
	String make_type = "";
	String mold_c_type = "";
	String mold_cost = "";
	String dis_cost = "";
	String dest = "";
	String store = "";
	String pack_type = "";
	String t_order = "";
	String type_cost = "";
	String m_su = "";
	String cavity = "";
	String recycle = "";
	String in_pack = "";
	String t_mone = "";
	String type_1 = "";
	String plating_cost = "";
	String out_pack = "";
	String sul_cost = "";
	String line_su = "";
	String pro_level = "";
	String pro_level_txt = "";
	String pro_1 = "";
	String plating_type = "";
	String type_2 = "";
	String method_type = "";
	String ton = "";
	String order_con = "";
	String h_weight = "";
	String h_scrap = "";
	String t_weight = "";
	String spm = "";
	String unitcost = "";
	String unitcost_txt = "";
	String grade_name = "";
	String grade_color = "";
	String t_size = "";
	String w_size = "";
	String p_size = "";
	String plating = "";
	String m_maker = "";
	String m_mone = "";
	String usage = "";
	String meterial = "";
	String request_day = "";
	String pack_cost = "";

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    group_no = (String) SearchMap.get("group_no");
	    case_count_1 = (String) SearchMap.get("case_count_1");
	    case_count_2 = (String) SearchMap.get("case_count_2");
	    pro_type = (String) SearchMap.get("pro_type");
	    make = (String) SearchMap.get("make");
	    part_name = (String) SearchMap.get("part_name");
	    part_type = (String) SearchMap.get("part_type");
	    mix_group = (String) SearchMap.get("mix_group");
	    part_no = (String) SearchMap.get("part_no");
	    net_1 = (String) SearchMap.get("net_1");
	    net_2 = (String) SearchMap.get("net_2");
	    net_3 = (String) SearchMap.get("net_3");
	    die_no = (String) SearchMap.get("die_no");
	    part_note = (String) SearchMap.get("part_note");
	    etc_note = (String) SearchMap.get("etc_note");
	    pk_cr = (String) SearchMap.get("pk_cr");
	    request_day = (String) SearchMap.get("request_day");
	    System.out.println("request_day : " + request_day);

	    sb.append(" update cost_productinfo      ");
	    sb.append("    set  group_no      = ?    ");
	    sb.append("        ,case_count_1  = ?    ");
	    sb.append("        ,case_count_2  = ?    ");
	    sb.append("        ,pro_type      = ?    ");
	    sb.append("        ,make          = ?    ");
	    sb.append("        ,part_name     = ?    ");
	    sb.append("        ,part_type     = ?    ");
	    sb.append("        ,mix_group     = ?    ");
	    sb.append("        ,part_no       = ?    ");
	    sb.append("        ,net_1         = ?    ");
	    sb.append("        ,net_2         = ?    ");
	    sb.append("        ,net_3         = ?    ");
	    sb.append("        ,die_no        = ?    ");
	    sb.append("        ,part_note     = ?    ");
	    sb.append("        ,etc_note      = ?    ");
	    sb.append("  where pk_pid in (select fk_pid from cost_request where pk_cr = ?) ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, group_no);
		pstmt.setString(2, case_count_1);
		pstmt.setString(3, case_count_2);
		pstmt.setString(4, pro_type);
		pstmt.setString(5, make);
		pstmt.setString(6, part_name);
		pstmt.setString(7, part_type);
		pstmt.setString(8, mix_group);
		pstmt.setString(9, part_no);
		pstmt.setString(10, net_1);
		pstmt.setString(11, net_2);
		pstmt.setString(12, net_3);
		pstmt.setString(13, die_no);
		pstmt.setString(14, part_note);
		pstmt.setString(15, etc_note);
		pstmt.setString(16, pk_cr);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	    etc_cost = (String) SearchMap.get("etc_cost");
	    yazaki_ro = (String) SearchMap.get("yazaki_ro");
	    royalty = (String) SearchMap.get("royalty");
	    distri_cost = (String) SearchMap.get("distri_cost");
	    make_type = (String) SearchMap.get("make_type");
	    mold_c_type = (String) SearchMap.get("mold_c_type");
	    mold_cost = (String) SearchMap.get("mold_cost");
	    dis_cost = (String) SearchMap.get("dis_cost");
	    dest = (String) SearchMap.get("dest");
	    store = (String) SearchMap.get("store");
	    pack_type = (String) SearchMap.get("pack_type");
	    t_order = (String) SearchMap.get("t_order");
	    type_cost = (String) SearchMap.get("type_cost");
	    m_su = (String) SearchMap.get("m_su");
	    cavity = (String) SearchMap.get("cavity");
	    recycle = (String) SearchMap.get("recycle");
	    in_pack = (String) SearchMap.get("in_pack");
	    t_mone = (String) SearchMap.get("t_mone");
	    type_1 = (String) SearchMap.get("type_1");
	    plating_cost = (String) SearchMap.get("plating_cost");
	    out_pack = (String) SearchMap.get("out_pack");
	    sul_cost = (String) SearchMap.get("sul_cost");
	    line_su = (String) SearchMap.get("line_su");
	    pro_level = (String) SearchMap.get("pro_level");
	    pro_level_txt = (String) SearchMap.get("pro_level_txt");
	    pro_1 = (String) SearchMap.get("pro_1");
	    plating_type = (String) SearchMap.get("plating_type");
	    type_2 = (String) SearchMap.get("type_2");
	    method_type = (String) SearchMap.get("method_type");
	    ton = (String) SearchMap.get("ton");
	    order_con = (String) SearchMap.get("order_con");
	    h_weight = (String) SearchMap.get("h_weight");
	    h_scrap = (String) SearchMap.get("h_scrap");
	    t_weight = (String) SearchMap.get("t_weight");
	    spm = (String) SearchMap.get("spm");
	    unitcost = (String) SearchMap.get("unitcost");
	    unitcost_txt = (String) SearchMap.get("unitcost_txt");
	    grade_name = (String) SearchMap.get("grade_name");
	    grade_color = (String) SearchMap.get("grade_color");
	    t_size = (String) SearchMap.get("t_size");
	    w_size = (String) SearchMap.get("w_size");
	    p_size = (String) SearchMap.get("p_size");
	    plating = (String) SearchMap.get("plating");
	    m_maker = (String) SearchMap.get("m_maker");
	    m_mone = (String) SearchMap.get("m_mone");
	    usage = (String) SearchMap.get("usage");
	    meterial = (String) SearchMap.get("meterial");
	    pack_cost = (String) SearchMap.get("pack_cost");

	    sb.append("update cost_request        ");
	    sb.append("   set etc_cost       = ?  ");
	    sb.append("      ,yazaki_ro      = ?  ");
	    sb.append("      ,royalty        = ?  ");
	    sb.append("      ,distri_cost    = ?  ");
	    sb.append("      ,make_type      = ?  ");
	    sb.append("      ,mold_c_type    = ?  ");
	    sb.append("      ,mold_cost      = ?  ");
	    sb.append("      ,dis_cost       = ?  ");
	    sb.append("      ,dest           = ?  ");
	    sb.append("      ,store          = ?  ");
	    sb.append("      ,pack_type      = ?  ");
	    sb.append("      ,t_order        = ?  ");
	    sb.append("      ,type_cost      = ?  ");
	    sb.append("      ,m_su           = ?  ");
	    sb.append("      ,cavity         = ?  ");
	    sb.append("      ,recycle        = ?  ");
	    sb.append("      ,in_pack        = ?  ");
	    sb.append("      ,t_mone         = ?  ");
	    sb.append("      ,type_1         = ?  ");
	    sb.append("      ,plating_cost   = ?  ");
	    sb.append("      ,out_pack       = ?  ");
	    sb.append("      ,sul_cost       = ?  ");
	    sb.append("      ,line_su        = ?  ");
	    sb.append("      ,pro_level      = ?  ");
	    sb.append("      ,pro_level_txt  = ?  ");
	    sb.append("      ,pro_1          = ?  ");
	    sb.append("      ,plating_type   = ?  ");
	    sb.append("      ,type_2         = ?  ");
	    sb.append("      ,method_type    = ?  ");
	    sb.append("      ,ton            = ?  ");
	    sb.append("      ,order_con      = ?  ");
	    sb.append("      ,h_weight       = ?  ");
	    sb.append("      ,h_scrap        = ?  ");
	    sb.append("      ,t_weight       = ?  ");
	    sb.append("      ,spm            = ?  ");
	    sb.append("      ,unitcost       = ?  ");
	    sb.append("      ,unitcost_txt   = ?  ");
	    sb.append("      ,grade_name     = ?  ");
	    sb.append("      ,grade_color    = ?  ");
	    sb.append("      ,t_size         = ?  ");
	    sb.append("      ,w_size         = ?  ");
	    sb.append("      ,p_size         = ?  ");
	    sb.append("      ,plating        = ?  ");
	    sb.append("      ,m_maker        = ?  ");
	    sb.append("      ,m_mone         = ?  ");
	    sb.append("      ,usage          = ?  ");
	    sb.append("      ,meterial       = ?  ");
	    sb.append("      ,request_day       = to_date(?,'YYYY-MM-DD')  ");
	    sb.append("      ,pack_cost       = ?  ");
	    sb.append(" where pk_cr = ?          ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, etc_cost);
		pstmt.setString(2, yazaki_ro);
		pstmt.setString(3, royalty);
		pstmt.setString(4, distri_cost);
		pstmt.setString(5, make_type);
		pstmt.setString(6, mold_c_type);
		pstmt.setString(7, mold_cost);
		pstmt.setString(8, dis_cost);
		pstmt.setString(9, dest);
		pstmt.setString(10, store);
		pstmt.setString(11, pack_type);
		pstmt.setString(12, t_order);
		pstmt.setString(13, type_cost);
		pstmt.setString(14, m_su);
		pstmt.setString(15, cavity);
		pstmt.setString(16, recycle);
		pstmt.setString(17, in_pack);
		pstmt.setString(18, t_mone);
		pstmt.setString(19, type_1);
		pstmt.setString(20, plating_cost);
		pstmt.setString(21, out_pack);
		pstmt.setString(22, sul_cost);
		pstmt.setString(23, line_su);
		pstmt.setString(24, pro_level);
		pstmt.setString(25, pro_level_txt);
		pstmt.setString(26, pro_1);
		pstmt.setString(27, plating_type);
		pstmt.setString(28, type_2);
		pstmt.setString(29, method_type);
		pstmt.setString(30, ton);
		pstmt.setString(31, order_con);
		pstmt.setString(32, h_weight);
		pstmt.setString(33, h_scrap);
		pstmt.setString(34, t_weight);
		pstmt.setString(35, spm);
		pstmt.setString(36, unitcost);
		pstmt.setString(37, unitcost_txt);
		pstmt.setString(38, grade_name);
		pstmt.setString(39, grade_color);
		pstmt.setString(40, t_size);
		pstmt.setString(41, w_size);
		pstmt.setString(42, p_size);
		pstmt.setString(43, plating);
		pstmt.setString(44, m_maker);
		pstmt.setString(45, m_mone);
		pstmt.setString(46, usage);
		pstmt.setString(47, meterial);
		pstmt.setString(48, request_day);
		pstmt.setString(49, pack_cost);
		pstmt.setString(50, pk_cr);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	}
	return complet;
    }

    public int costReEditUpdate2(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String group_no = null;
	String case_count_1 = null;
	String case_count_2 = null;
	String pk_cr = null;
	String etc_note = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    group_no = (String) SearchMap.get("group_no");
	    case_count_1 = (String) SearchMap.get("case_count_1");
	    case_count_2 = (String) SearchMap.get("case_count_2");
	    pk_cr = (String) SearchMap.get("pk_cr");
	    etc_note = (String) SearchMap.get("etc_note");

	    sb.append(" UPDATE cost_productInfo");
	    sb.append("    SET group_no     = ?");
	    sb.append("       ,case_count_1 = ?");
	    sb.append("       ,case_count_2 = ?");
	    sb.append("       ,etc_note = ?");
	    sb.append("  WHERE pk_pid in (select fk_pid from cost_request where pk_cr = ?)");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, group_no);
		pstmt.setString(2, case_count_1);
		pstmt.setString(3, case_count_2);
		pstmt.setString(4, etc_note);
		pstmt.setString(5, pk_cr);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
	return complet;
    }

    public int costReEditDelete(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String pk_cr = null;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    pk_cr = (String) SearchMap.get("pk_cr");

	    sb.append(" DELETE cost_productinfo");
	    sb.append("  WHERE pk_pid in (select fk_pid from cost_request where pk_cr = ?)");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, pk_cr);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }

	    pstmt = null;

	    sb.append(" DELETE cost_request");
	    sb.append("  WHERE pk_cr = ?");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, pk_cr);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
	return complet;
    }

    /**
     * 함수명 : getSearchFile 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 06. 27.
     */
    public ArrayList getSearchFile(String pk_cr_group, String page_name, String report_pk) throws Exception {
	ArrayList SearchList = new ArrayList();

	Hashtable SearchMap = new Hashtable();
	SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	if (page_name.equals("work")) {
	    sb.append("select replace(file_1,'\\Upload\\cost_file\\보고서\\','') file_1,");
	    sb.append("       replace(file_2,'\\Upload\\cost_file\\보고서\\','') file_2,");
	    sb.append("       replace(file_3,'\\Upload\\cost_file\\보고서\\','') file_3 from cost_work");
	    sb.append(" where fk_pid in (select pk_pid from cost_productinfo where pk_cr_group = ?) ");
	} else if (page_name.equals("report")) {
	    sb.append("select replace(file_1,'\\Upload\\cost_file\\보고서\\','') file_1,");
	    sb.append("          replace(file_2,'\\Upload\\cost_file\\보고서\\','') file_2,");
	    sb.append("          replace(file_3,'\\Upload\\cost_file\\보고서\\','') file_3,");
	    sb.append("          replace(file_4,'\\Upload\\cost_file\\보고서\\','') file_4,");
	    sb.append("          replace(file_5,'\\Upload\\cost_file\\보고서\\','') file_5,");
	    sb.append("          replace(file_6,'\\Upload\\cost_file\\보고서\\','') file_6 from cost_report");
	    sb.append(" where crp_group = ? ");
	} else {
	    sb.append("select replace(file_1,'\\Upload\\cost_file\\원가산출요청\\','') file_1,");
	    sb.append("       replace(file_2,'\\Upload\\cost_file\\원가산출요청\\','') file_2,");
	    sb.append("       replace(file_3,'\\Upload\\cost_file\\원가산출요청\\','') file_3 from cost_request");
	    sb.append(" where fk_pid in (select pk_pid from cost_productinfo where pk_cr_group = ?) ");
	}

	try {
	    pstmt = conn.prepareStatement(sb.toString());

	    if (page_name.equals("report")) {
		pstmt.setString(1, report_pk);
	    } else {
		pstmt.setString(1, pk_cr_group);
	    }
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();

		SearchMap.put("file_1", StringUtil.checkNull(rs.getString("file_1")));
		SearchMap.put("file_2", StringUtil.checkNull(rs.getString("file_2")));
		SearchMap.put("file_3", StringUtil.checkNull(rs.getString("file_3")));
		SearchMap.put("file_4", StringUtil.checkNull(rs.getString("file_4")));
		System.out.println("file_4 : " + rs.getString("file_4"));
		SearchMap.put("file_5", StringUtil.checkNull(rs.getString("file_5")));
		SearchMap.put("file_6", StringUtil.checkNull(rs.getString("file_6")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    public int costFileSave(String page_name, String file_type, String file_path, String pk_cr_group, String report_pk, String file_2_name,
	    String file_3_name, String file_4_name, String file_5_name, String file_6_name) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	int complet = 0;

	String group_case_count = null;
	String pk_cr_group_1 = null;
	String rev_no = null;

	pstmt = null;
	file_path.replaceAll("D:", "");
	if (page_name.equals("work")) {
	    sb.append(" update cost_work ");
	} else if (page_name.equals("report")) {
	    sb.append(" update cost_report ");
	} else {
	    sb.append(" update cost_request ");
	}
	// file_2_name = java.net.URLDecoder.decode(file_2_name, "utf-8");
	System.out.println("file_2_name: " + file_2_name);
	if (page_name.equals("report")) {
	    if (file_type.equals("file_1")) {
		sb.append("  set file_1 = replace(?,'D:','') ");
	    } else if (file_type.equals("file_2")) {
		sb.append(" set file_2 = replace(?,'D:','') , file_2_name = '" + file_2_name + "'");
	    } else if (file_type.equals("file_3")) {
		sb.append(" set file_3 = replace(?,'D:','') , file_3_name  = '" + file_3_name + "'");
	    } else if (file_type.equals("file_4")) {
		sb.append(" set file_4 = replace(?,'D:','') , file_4_name  = '" + file_4_name + "'");
	    } else if (file_type.equals("file_5")) {
		sb.append(" set file_5 = replace(?,'D:',''),  file_5_name  = '" + file_5_name + "'");
	    } else if (file_type.equals("file_6")) {
		sb.append(" set file_6 = replace(?,'D:','') , file_6_name  = '" + file_6_name + "'");
	    }
	    sb.append(" where crp_group = ? ");
	} else {
	    if (file_type.equals("file_1")) {
		sb.append(" set file_1 = replace(?,'D:','') ");
	    } else if (file_type.equals("file_2")) {
		sb.append(" set file_2 = replace(?,'D:','') ");
	    } else {
		sb.append(" set file_3 = replace(?,'D:','') ");
	    }
	    sb.append(" where fk_pid in (select pk_pid from cost_productinfo where pk_cr_group =?) ");
	}
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, file_path);
	    if (page_name.equals("report")) {
		pstmt.setString(2, report_pk);
	    } else {
		pstmt.setString(2, pk_cr_group);
	    }

	    complet = pstmt.executeUpdate() + complet;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}

	return complet;
    }

    /**
     * 함수명 : getSearchRe_view 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList getSearchRe_view(String pk_cr_group, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append("select b.f_name,a_name,to_char(c.f_day,'YY-MM-DD') as f_day,to_char(c.leader_day,'YY-MM-DD') as leader_day,c.leader_name,c.approval,to_char(c.gr_day,'YY-MM-DD') as gr_day,c.gr_name");
	sb.append("  from cost_request a,cost_productinfo b,wfinfo c                   ");
	sb.append(" where a.fk_pid = b.pk_pid                                          ");
	sb.append("   and a.fk_wid = c.pk_wid(+)                                       ");
	sb.append("   and pk_cr_group = ?                                              ");
	sb.append("   and rev_no      = ?                                              ");
	sb.append("   and group_no  = 'T001'                                           ");
	sb.append("   and data_type = 'main'                                           ");
	sb.append("   and nvl(cost_flag,'R') = 'R'                                     ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("f_day", StringUtil.checkNull(rs.getString("f_day")));
		SearchMap.put("leader_day", StringUtil.checkNull(rs.getString("leader_day")));
		SearchMap.put("leader_name", StringUtil.checkNull(rs.getString("leader_name")));
		SearchMap.put("gr_day", StringUtil.checkNull(rs.getString("gr_day")));
		SearchMap.put("gr_name", StringUtil.checkNull(rs.getString("gr_name")));
		SearchMap.put("approval", StringUtil.checkNull(rs.getString("approval")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	}
	return SearchList;
    }

    /**
     * 함수명 : Re_viewSearch_2 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList Re_viewSearch_1(String pk_cr_group, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select pk_cr_group, pjt_no,      pjt_name, team,     b.f_name    ");
	sb.append("      ,a_name,      dev_step,    dr_step,  sub_step, Leader_day  ");
	sb.append("      ,to_char(a.request_day,'YYYYMMDD') as request_day            ");
	sb.append("      ,request_txt, etc_note                                     ");
	sb.append("      ,replace(file_1,'\\','/') file_1                           ");
	sb.append("      ,replace(file_2,'\\','/') file_2                           ");
	sb.append("      ,replace(file_3,'\\','/') file_3                           ");
	sb.append("  from cost_request a,cost_productinfo b,wfinfo c                ");
	sb.append(" where a.fk_pid = b.pk_pid                                       ");
	sb.append("   and a.fk_wid = c.pk_wid(+)                                    ");
	sb.append("   and pk_cr_group = ?                                           ");
	sb.append("   and rev_no      = ?                                           ");
	sb.append("   and group_no  = 'T001'                                        ");
	sb.append("   and data_type = 'main'                                        ");
	sb.append("   and nvl(cost_flag,'R') = 'R'                                  ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("dr_step", StringUtil.checkNull(rs.getString("dr_step")));
		SearchMap.put("sub_step", StringUtil.checkNull(rs.getString("sub_step")));
		SearchMap.put("Leader_day", StringUtil.checkNull(rs.getString("Leader_day")));
		SearchMap.put("request_day", StringUtil.checkNull(rs.getString("request_day")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("etc_note", StringUtil.checkNull(rs.getString("etc_note")));
		SearchMap.put("file_1", StringUtil.checkNull(rs.getString("file_1")));
		SearchMap.put("file_2", StringUtil.checkNull(rs.getString("file_2")));
		SearchMap.put("file_3", StringUtil.checkNull(rs.getString("file_3")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	}
	return SearchList;
    }

    /**
     * 함수명 : Re_viewSearch_2 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList Re_viewSearch_2(String pk_cr_group, String data_type, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select pk_cr      ,pk_cr_group  ,group_no   ,part_name   ,part_no        ");
	sb.append("      ,car_type   ,su_year_1    ,su_year_2  ,su_year_3   ,su_year_4      ");
	sb.append("      ,su_year_5  ,su_year_6    ,su_year_7  ,su_year_8   ,customer_F          ");
	sb.append("      ,customer_L ,client_cost  ,ket_cost   ,target_cost ,rev_pk    ,rev_no   ");
	sb.append("   from cost_request a ,cost_productinfo b                                    ");
	sb.append(" where a.fk_pid = b.pk_pid                                                    ");
	sb.append("   and pk_cr_group = ?                                                        ");
	sb.append("   and rev_no      = ?                                                        ");
	sb.append("   and length(group_no)  = 4                                                  ");
	sb.append("   and data_type = ?                                                          ");
	sb.append("order by group_no                                                             ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    pstmt.setString(3, data_type);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cr", StringUtil.checkNull(rs.getString("pk_cr")));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("part_no", StringUtil.checkNull(rs.getString("part_no")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("customer_L", StringUtil.checkNull(rs.getString("customer_L")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("rev_pk", StringUtil.checkNull(rs.getString("rev_pk")));
		SearchMap.put("rev_no", StringUtil.checkNull(rs.getString("rev_no")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	}
	return SearchList;
    }

    /**
     * 함수명 : Re_viewSearch_2 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList Re_viewSearch_3(String pk_cr_group, String data_type, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select ");
	sb.append("case_count_1,  case_count_2, pk_cr,      pk_cr_group,   make,         group_no,      ");
	sb.append("pro_type,      part_name,    part_type,  mix_group,     part_no,      net_1,         ");
	sb.append("net_2,         net_3,        usage,      meterial,      t_size,       w_size,        ");
	sb.append("p_size,        plating,      m_maker,    m_mone,        unitcost,     unitcost_txt,  ");
	sb.append("grade_name,    grade_color,  order_con,  h_weight,      h_scrap,      t_weight,      ");
	sb.append("recycle,       die_no,       cavity,     m_su,          mold_cost,    mold_c_type,   ");
	sb.append("make_type,     pro_1,        ton,        spm,           method_type,  pro_level,     ");
	sb.append("pro_level_txt, line_su,      sul_cost,   plating_type,  type_2,       plating_cost,  ");
	sb.append("type_1,        t_mone,       type_cost,  t_order,       pack_type,    in_pack,       ");
	sb.append("out_pack,      store,        dest,       dis_cost,      distri_cost,  royalty,       ");
	sb.append("etc_cost,      yazaki_ro,    part_note                                               ");
	sb.append("  from cost_request a ,cost_productinfo b                                    ");
	sb.append(" where a.fk_pid = b.pk_pid                                                    ");
	sb.append("   and pk_cr_group = ?                                                        ");
	sb.append("   and rev_no      = ?                                                        ");
	sb.append("   and data_type = ?                                                          ");
	sb.append("order by group_no                                                             ");
	String g_sel1 = "";
	String g_sel2 = "";
	String g_sel3 = "";
	String group_no = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    pstmt.setString(3, data_type);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		g_sel1 = "";
		g_sel2 = "";
		g_sel3 = "";
		group_no = StringUtil.checkNull(rs.getString("group_no"));
		if (group_no.length() < 5) {
		    g_sel1 = "/plm/jsp/cost/acc_img/add_red.jpg";
		} else if (group_no.length() < 8) {
		    g_sel2 = "/plm/jsp/cost/acc_img/add.jpg";
		} else {
		    g_sel3 = "/plm/jsp/cost/acc_img/add.jpg";
		}

		SearchMap = new Hashtable<String, String>();
		SearchMap.put("case_count_1", StringUtil.checkNull(rs.getString("case_count_1")));
		SearchMap.put("case_count_2", StringUtil.checkNull(rs.getString("case_count_2")));
		SearchMap.put("pk_cr", StringUtil.checkNull(rs.getString("pk_cr")));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("make", StringUtil.checkNull(rs.getString("make")));
		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		SearchMap.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		SearchMap.put("g_sel1", g_sel1);
		SearchMap.put("g_sel2", g_sel2);
		SearchMap.put("g_sel3", g_sel3);
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("part_type", StringUtil.checkNull(rs.getString("part_type")));
		SearchMap.put("mix_group", StringUtil.checkNull(rs.getString("mix_group")));
		SearchMap.put("part_no", StringUtil.checkNull(rs.getString("part_no")));
		SearchMap.put("net_1", StringUtil.checkNull(rs.getString("net_1")));
		SearchMap.put("net_2", StringUtil.checkNull(rs.getString("net_2")));
		SearchMap.put("net_3", StringUtil.checkNull(rs.getString("net_3")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("meterial", StringUtil.checkNull(rs.getString("meterial")));
		SearchMap.put("t_size", StringUtil.checkNull(rs.getString("t_size")));
		SearchMap.put("w_size", StringUtil.checkNull(rs.getString("w_size")));
		SearchMap.put("p_size", StringUtil.checkNull(rs.getString("p_size")));
		SearchMap.put("plating", StringUtil.checkNull(rs.getString("plating")));
		SearchMap.put("m_maker", StringUtil.checkNull(rs.getString("m_maker")));
		SearchMap.put("m_mone", StringUtil.checkNull(rs.getString("m_mone")));
		SearchMap.put("unitcost", StringUtil.checkNull(rs.getString("unitcost")));
		SearchMap.put("unitcost_txt", StringUtil.checkNull(rs.getString("unitcost_txt")));
		SearchMap.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		SearchMap.put("grade_color", StringUtil.checkNull(rs.getString("grade_color")));
		SearchMap.put("order_con", StringUtil.checkNull(rs.getString("order_con")));
		SearchMap.put("h_weight", StringUtil.checkNull(rs.getString("h_weight")));
		SearchMap.put("h_scrap", StringUtil.checkNull(rs.getString("h_scrap")));
		SearchMap.put("t_weight", StringUtil.checkNull(rs.getString("t_weight")));
		SearchMap.put("recycle", StringUtil.checkNull(rs.getString("recycle")));
		SearchMap.put("die_no", StringUtil.checkNull(rs.getString("die_no")));
		SearchMap.put("cavity", StringUtil.checkNull(rs.getString("cavity")));
		SearchMap.put("m_su", StringUtil.checkNull(rs.getString("m_su")));
		SearchMap.put("mold_cost", StringUtil.checkNull(rs.getString("mold_cost")));
		SearchMap.put("mold_c_type", StringUtil.checkNull(rs.getString("mold_c_type")));
		SearchMap.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("ton", StringUtil.checkNull(rs.getString("ton")));
		SearchMap.put("spm", StringUtil.checkNull(rs.getString("spm")));
		SearchMap.put("method_type", StringUtil.checkNull(rs.getString("method_type")));
		SearchMap.put("pro_level", StringUtil.checkNull(rs.getString("pro_level")));
		SearchMap.put("pro_level_txt", StringUtil.checkNull(rs.getString("pro_level_txt")));
		SearchMap.put("line_su", StringUtil.checkNull(rs.getString("line_su")));
		SearchMap.put("sul_cost", StringUtil.checkNull(rs.getString("sul_cost")));
		SearchMap.put("plating_type", StringUtil.checkNull(rs.getString("plating_type")));
		SearchMap.put("type_2", StringUtil.checkNull(rs.getString("type_2")));
		SearchMap.put("plating_cost", StringUtil.checkNull(rs.getString("plating_cost")));
		SearchMap.put("type_1", StringUtil.checkNull(rs.getString("type_1")));
		SearchMap.put("t_mone", StringUtil.checkNull(rs.getString("t_mone")));
		SearchMap.put("type_cost", StringUtil.checkNull(rs.getString("type_cost")));
		SearchMap.put("t_order", StringUtil.checkNull(rs.getString("t_order")));
		SearchMap.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		SearchMap.put("in_pack", StringUtil.checkNull(rs.getString("in_pack")));
		SearchMap.put("out_pack", StringUtil.checkNull(rs.getString("out_pack")));
		SearchMap.put("store", StringUtil.checkNull(rs.getString("store")));
		SearchMap.put("dest", StringUtil.checkNull(rs.getString("dest")));
		SearchMap.put("dis_cost", StringUtil.checkNull(rs.getString("dis_cost")));
		SearchMap.put("distri_cost", StringUtil.checkNull(rs.getString("distri_cost")));
		SearchMap.put("royalty", StringUtil.checkNull(rs.getString("royalty")));
		SearchMap.put("etc_cost", StringUtil.checkNull(rs.getString("etc_cost")));
		SearchMap.put("yazaki_ro", StringUtil.checkNull(rs.getString("yazaki_ro")));
		SearchMap.put("part_note", StringUtil.checkNull(rs.getString("part_note")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	}
	return SearchList;
    }

    /**
     * 함수명 : Re_view_Delete 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */

    public int Re_view_Delete(String pk_cr_group_1, String rev_no) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;
	String pk_cr = null;

	sb.append(" delete cost_work                                                  ");
	sb.append("  where fk_cost_request                                            ");
	sb.append("     in (select pk_cr from cost_request                            ");
	sb.append(" 	     where fk_pid                                             ");
	sb.append(" 		    in (select pk_pid from cost_productinfo               ");
	sb.append(" 			     where pk_cr_group = ? and rev_no = ? and report_pk is null))  ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group_1);
	    pstmt.setString(2, rev_no);
	    complet = pstmt.executeUpdate() + complet;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	pstmt = null;

	sb.append(" DELETE cost_productinfo");
	sb.append("  where pk_cr_group = ?");
	sb.append("    and rev_no = ?");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group_1);
	    pstmt.setString(2, rev_no);
	    complet = pstmt.executeUpdate() + complet;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	pstmt = null;

	sb.append(" DELETE cost_request");
	sb.append("  WHERE fk_pid in (select pk_pid from cost_productinfo where pk_cr_group = ? and rev_no = ?)");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group_1);
	    pstmt.setString(2, rev_no);
	    complet = pstmt.executeUpdate() + complet;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	return complet;
    }

    /**
     * 함수명 : SearchRepass 설명 : 개발담당자 결재
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList SearchRepass(String pk_cr_group, String rev_no, String team) throws Exception {

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	PreparedStatement pstmt3 = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	StringBuffer sb2 = new StringBuffer();
	StringBuffer sb3 = new StringBuffer();

	sb.append("select pk_cr, f_name, pjt_name, wfinfo_pk_wid.nextval as	pk_wid  ");
	sb.append("  from cost_request a ,cost_productinfo b                  ");
	sb.append(" where a.fk_pid = b.pk_pid                                 ");
	sb.append("   and pk_cr_group = ?                                     ");
	sb.append("   and rev_no      = ?                                     ");
	sb.append("   nvl(cost_flag,'R') = 'R'                                ");

	sb2.append("insert into wfinfo (  						  			               ");
	sb2.append("       pk_wid, fk_cost, cost_flag, f_name, step_no, approval, f_day  ) ");
	sb2.append("values(?,      ?,       'R',       ?,      '0.5',     'ok',     sysdate) ");

	sb3.append("update cost_request");
	sb3.append("   set fk_wid = ?  ");
	sb3.append(" where pk_cr  = ?  ");

	String pk_cr = "";
	String f_name = "";
	String pjt_name = "";
	String pk_wid = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		pk_cr = StringUtil.checkNull(rs.getString("pk_cr"));
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		pk_wid = StringUtil.checkNull(rs.getString("pk_wid"));

		pstmt2 = conn.prepareStatement(sb2.toString());
		pstmt2.setString(1, pk_wid);
		pstmt2.setString(2, pk_cr);
		pstmt2.setString(3, f_name);

		pstmt2.executeUpdate();

		pstmt3 = conn.prepareStatement(sb3.toString());
		pstmt3.setString(1, pk_wid);
		pstmt3.setString(2, pk_cr);

		pstmt3.executeUpdate();
	    }

	    SearchMap = new Hashtable<String, String>();
	    SearchMap.put("pjt_name", pjt_name);
	    SearchMap.put("f_name", f_name);
	    SearchList.add(SearchMap);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    sb2.delete(0, sb2.length());
	    sb3.delete(0, sb3.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (pstmt2 != null) {
		pstmt2.close();
	    }
	    if (pstmt3 != null) {
		pstmt3.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : getSearchPjtinfo 설명 : 프로젝트 명,개발담당자 이름 얻기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */

    public String getSearchPjtinfo(String pk_cr_group, String rev_no) {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	String pjt_name = "";
	String f_name = "";
	String a_name = "";

	sb.append("select f_name,pjt_name,a_name from cost_request a, cost_productinfo b    ");
	sb.append(" where a.fk_pid    = b.pk_pid                            ");
	sb.append("   and pk_cr_group = ?                                   ");
	sb.append("   and rev_no      = ?                                  ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		a_name = StringUtil.checkNull(rs.getString("a_name"));
	    }

	} catch (Exception e) {

	    e.printStackTrace();
	}
	if (f_name.equals("")) {
	    f_name = " ";
	}
	if (pjt_name.equals("")) {
	    pjt_name = " ";
	}
	if (a_name.equals("")) {
	    a_name = " ";
	}
	return f_name + ";" + pjt_name + ";" + a_name;
    }

    /**
     * 함수명 : Appr_Gr 설명 : 그룹장 결재
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public void Appr_Gr(String pk_cr_group, String rev_no, String GR_NAME) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("update wfinfo                                                    ");
	sb.append("   set GR_DAY = SYSDATE                                          ");
	sb.append("      ,GR_NAME = ?                                               ");
	sb.append("      ,STEP_NO = '1'                                             ");
	sb.append(" where pk_wid                                                    ");
	sb.append("    in (select fk_wid from cost_request a, cost_productinfo b    ");
	sb.append("        where a.fk_pid    = b.pk_pid                             ");
	sb.append("          and pk_cr_group = ?                                    ");
	sb.append("          and rev_no      = ?)                                   ");
	sb.append("   and cost_flag = 'R'                                           ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, GR_NAME);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}

    }

    /**
     * 함수명 : Appr_leader 설명 : 팀장 결재
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public void Appr_leader(String pk_cr_group, String rev_no, String LEADER_NAME) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("update wfinfo                                                    ");
	sb.append("   set LEADER_DAY  = SYSDATE                                     ");
	sb.append("      ,LEADER_NAME = ?                                           ");
	sb.append("      ,STEP_NO  = '2'                                            ");
	sb.append("      ,APPROVAL = 'finish'                                       ");
	sb.append(" where pk_wid                                                    ");
	sb.append("    in (select fk_wid from cost_request a, cost_productinfo b    ");
	sb.append("        where a.fk_pid    = b.pk_pid                             ");
	sb.append("          and pk_cr_group = ?                                    ");
	sb.append("          and rev_no      = ?)                                   ");
	sb.append("   and cost_flag = 'R'                                           ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, LEADER_NAME);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}

    }

    /**
     * 함수명 : SearchGname 설명 : 그룹장 이름 가져오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String SearchGname(String team) throws Exception {
	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select a.name        ");
	sb.append("  from ghost_ket.auth_cost A, ghost_ket.CDUser B    ");
	sb.append(" where A.empno = b.Account                          ");
	sb.append("   and B.GroupCode = ?                              ");
	sb.append("   and A.group_m   = 'G'                         ");

	String G_name = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, team);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		G_name = StringUtil.checkNull(rs.getString("name"));

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return G_name;
    }

    /**
     * 함수명 : SearchEmail 설명 : 그룹장,팀장 결재시 메일정보 알아오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList SearchEmail(String f_name, String team, String step_no, String a_name) throws Exception {

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	if (step_no.equals("0")) { // 그룹장에게 결재요청
	    sb.append("select leader,le_email,M_email from                 ");
	    sb.append("(select master.dbo.xdbdec('normal',B.Email)  M_email                             ");
	    sb.append("   from ghost_ket.auth_cost A, ghost_ket.CDUser B   ");
	    sb.append(" where A.empno = b.Account                          ");
	    sb.append("   and B.GroupCode = ?                        ");
	    sb.append("   and b.Name = ?) a,                               ");
	    sb.append("(select a.name as leader, master.dbo.xdbdec('normal',B.Email)  as le_email       ");
	    sb.append("  from ghost_ket.auth_cost A, ghost_ket.CDUser B    ");
	    sb.append(" where A.empno = b.Account                          ");
	    sb.append("   and B.GroupCode = ?                              ");
	    sb.append("   and A.group_m   = 'G') b                         ");
	}

	if (step_no.equals("0.5")) { // 팀장에게 결재요청
	    sb.append(" SELECT a.M_email, b.Name as leader,master.dbo.xdbdec('normal',B.Email) as le_email ");
	    sb.append("   FROM (SELECT GroupCode,master.dbo.xdbdec('normal',Email)  as M_email              ");
	    sb.append("           FROM ghost_ket.CDUser                        ");
	    sb.append("          WHERE Name = ?) A, ghost_ket.CDUser B         ");
	    sb.append(" WHERE B.PositionCode = 'J10'                           ");
	    sb.append("   AND A.GroupCode = ?                                  ");
	    sb.append("   AND B.GroupCode = A.GroupCode                        ");
	}

	if (step_no.equals("1")) { // 팀장이 결재시
	    sb.append(" select a.M_email,b.le_email,'' as leader                   ");
	    sb.append("   from (select master.dbo.xdbdec('normal',Email)  as M_email                            ");
	    sb.append("           from ghost_ket.CDUser                            ");
	    sb.append("          where Name = ?                                    ");
	    sb.append("            and GroupCode = ?) a,                           ");
	    sb.append("        (select master.dbo.xdbdec('normal',Email)  as le_email                           ");
	    sb.append("           from ghost_ket.CDUser                            ");
	    sb.append("          where Name = ?                                    ");
	    sb.append("            and GroupCode not in ('11614','11612','11616')) b   ");
	}

	String M_email = "";
	String L_email = "";
	String Le_name = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    if (step_no.equals("0")) {
		pstmt.setString(1, team);
		pstmt.setString(2, f_name);
		pstmt.setString(3, team);
	    }
	    if (step_no.equals("0.5")) {
		pstmt.setString(1, f_name);
		pstmt.setString(2, team);
	    }
	    if (step_no.equals("1")) {
		pstmt.setString(1, f_name);
		pstmt.setString(2, team);
		pstmt.setString(3, a_name);
	    }

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		M_email = StringUtil.checkNull(rs.getString("M_email"));
		L_email = StringUtil.checkNull(rs.getString("le_email"));
		Le_name = StringUtil.checkNull(rs.getString("leader"));

	    }
	    SearchMap = new Hashtable<String, String>();
	    SearchMap.put("M_email", M_email);
	    SearchMap.put("L_email", L_email);
	    SearchMap.put("Le_name", Le_name);
	    SearchList.add(SearchMap);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}

	return SearchList;
    }

    /**
     * 함수명 : SearchPass 설명 : 그룹장 및 팀장 결재시 패스워드,이름 알아오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList SearchPass(String emp_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" select CustomField5,name,master.dbo.xdbdec('normal',Email) as email  from ghost_ket.CDUser");
	sb.append("  where Account      = ? and CustomField1 = 'Y'                  ");

	String pw = "";
	String name = "";
	String L_email = "";
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, emp_no);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		pw = StringUtil.checkNull(rs.getString("CustomField5"));
		name = StringUtil.checkNull(rs.getString("name"));
		L_email = StringUtil.checkNull(rs.getString("email"));
	    }
	    SearchMap = new Hashtable<String, String>();
	    SearchMap.put("pw", pw);
	    SearchMap.put("name", name);
	    SearchMap.put("L_email", L_email);
	    SearchList.add(SearchMap);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}

	return SearchList;
    }

    /**
     * 함수명 : AprovalReject 설명 : 팀장의 요청서 반려
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String AprovalReject(String pk_cr_group, String rev_no) throws Exception {
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	StringBuffer sb2 = new StringBuffer();

	sb.append(" select f_name,pjt_name,pk_cr from cost_request a, cost_productinfo b ");
	sb.append("  where a.fk_pid    = b.pk_pid                                        ");
	sb.append("    and pk_cr_group = ?                                               ");
	sb.append("    and rev_no      = ?                                               ");

	String f_name = "";
	String pjt_name = "";
	String pk_cr = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		pk_cr = StringUtil.checkNull(rs.getString("pk_cr"));
		sb2.append("delete wfinfo                           ");
		sb2.append(" where fk_cost   = ?                    ");
		sb2.append("   and cost_flag = 'R'                  ");

		try {

		    pstmt2 = conn.prepareStatement(sb2.toString());
		    pstmt2.setString(1, pk_cr);

		    pstmt2.executeUpdate();

		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    sb2.delete(0, sb2.length());
		}

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (pstmt2 != null) {
		pstmt2.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return f_name + ";" + pjt_name;
    }

    /**
     * 함수명 : SearchF_email 설명 : 이메일 정보 가져오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String SearchF_email(String f_name, String team) throws Exception {
	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select master.dbo.xdbdec('normal',Email) email               ");
	sb.append("  from ghost_ket.CDUser   ");
	sb.append(" where GroupCode = ?      ");
	sb.append("   and name      = ?      ");

	String M_email = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, team);
	    pstmt.setString(2, f_name);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		M_email = StringUtil.checkNull(rs.getString("email"));

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return M_email;
    }

    /**
     * 함수명 : SearchF_email2 설명 : 이메일 정보 가져오기2
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String SearchF_email2(String w_name) throws Exception {
	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select master.dbo.xdbdec('normal',Email) as email                ");
	sb.append("  from ghost_ket.CDUser    ");
	sb.append(" where GroupCode = '11651' ");
	sb.append("   and name      = ?       ");

	String M_email = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, w_name);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		M_email = StringUtil.checkNull(rs.getString("email"));

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return M_email;
    }

    /**
     * 함수명 : ChangeWname 설명 : 산출담당자 변경
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public ArrayList ChangeWname(String w_name, String pk_cr_group, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;

	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" select pjt_name,f_name,team from cost_productinfo ");
	sb.append("  where pk_cr_group = ? AND rev_no = ?          ");

	String pjt_name = "";
	String f_name = "";
	String team = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    rs = pstmt.executeQuery();
	    SearchMap = new Hashtable<String, String>();
	    while (rs.next()) {
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		team = StringUtil.checkNull(rs.getString("team"));

		SearchMap.put("pjt_name", pjt_name);
		SearchMap.put("f_name", f_name);
		SearchMap.put("team", team);
	    }

	    SearchList.add(SearchMap);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	sb.append(" update cost_productinfo ");
	sb.append("    set w_name = ?       ");
	sb.append("  where pk_cr_group = ?  ");
	sb.append("    and rev_no = ?       ");

	pstmt = null;
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, w_name);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	return SearchList;
    }

    /**
     * 함수명 : Reject_work 설명 : 요청서반려
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 02.
     */
    public String Reject_work(String pk_cr_group, String rev_no, String pps_note) throws Exception {

	PreparedStatement pstmt = null;

	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" select pjt_name,b.f_name,Leader_name from COST_REQUEST a,cost_productinfo b,wfinfo c ");
	sb.append("  where b.pk_cr_group = ?  AND b.rev_no = ?                                           ");
	sb.append("    and a.fk_pid = b.pk_pid                                                           ");
	sb.append("    and a.PK_CR = c.fk_cost(+)                                                        ");
	sb.append("    and nvl(cost_flag,'R') = 'R'                                                      ");

	String pjt_name = "";
	String f_name = "";
	String L_name = "";

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		L_name = StringUtil.checkNull(rs.getString("Leader_name"));

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	sb.append(" UPDATE cost_request                          ");
	sb.append("    SET PPS_NOTE = ?                          ");
	sb.append("  WHERE FK_PID                                ");
	sb.append("     IN (select PK_PID from cost_productinfo  ");
	sb.append("          where pk_cr_group = ?               ");
	sb.append("            and rev_no      = ?)              ");

	pstmt = null;
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pps_note);
	    pstmt.setString(2, pk_cr_group);
	    pstmt.setString(3, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	sb.append(" DELETE wfinfo                                            ");
	sb.append("  WHERE FK_COST                                           ");
	sb.append("     IN (SELECT PK_CR FROM COST_REQUEST                   ");
	sb.append("          WHERE FK_PID                                    ");
	sb.append(" 		    IN (select PK_PID from cost_productinfo      ");
	sb.append("                  where pk_cr_group = ?                   ");
	sb.append("                    and rev_no      = ?                   ");
	sb.append(" 			   )                                         ");
	sb.append(" 	   )                                                 ");
	sb.append("    AND cost_flag = 'R'                                   ");

	pstmt = null;
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    pstmt.executeUpdate();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}

	return pjt_name + ";" + f_name + ";" + L_name;
    }

    /**
     * 함수명 : CostWorkPass 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 07. 23
     */
    public void CostWorkPass(String pk_cr_group, String rev_no, String url_chk, String pjt_no, String table_row, String user_case_count)
	    throws Exception {
	PreparedStatement pstmt = null;

	ResultSet rs = null;

	StringBuffer sb = new StringBuffer();

	sb.append(" select pk_pid,file_1,pk_cw, b.pk_cr_group,dev_step,pjt_name,pjt_no,team,f_name,a_name,w_name,cost_report_pk_crp.nextval as pk_crp ");
	sb.append("   from cost_productInfo a, cost_work b                                             ");
	sb.append("  where a.pk_cr_group = ?                                                           ");
	sb.append("    and a.group_no    = 'T001'                                                      ");
	sb.append("    and a.rev_no      = ?                                                           ");
	sb.append("    and a.pk_pid      = b.fk_pid                                                    ");

	String pk_pid = "";
	String file_1 = "";
	String pk_cw = "";
	String dev_step = "";
	String pjt_name = "";
	String team = "";
	String f_name = "";
	String a_name = "";
	String w_name = "";
	String pk_crp = "";

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();

	    sb.delete(0, sb.length());

	    while (rs.next()) {
		pk_pid = StringUtil.checkNull(rs.getString("pk_pid"));
		file_1 = StringUtil.checkNull(rs.getString("file_1"));
		pk_cw = StringUtil.checkNull(rs.getString("pk_cw"));
		dev_step = StringUtil.checkNull(rs.getString("dev_step"));
		pjt_name = StringUtil.checkNull(rs.getString("pjt_name"));
		team = StringUtil.checkNull(rs.getString("team"));
		f_name = StringUtil.checkNull(rs.getString("f_name"));
		a_name = StringUtil.checkNull(rs.getString("a_name"));
		w_name = StringUtil.checkNull(rs.getString("w_name"));
		pk_crp = StringUtil.checkNull(rs.getString("pk_crp"));

		sb.append(" insert into cost_report(                              ");
		sb.append("     PK_CRP, FK_PID, CRP_GROUP, FK_COST_WORK_1, FILE_1 ");
		sb.append(" )values(                                              ");
		sb.append("     ?, ?, ?, ?, ?                               ");
		sb.append(" )                                                     ");
		pstmt = null;
		pstmt = conn.prepareStatement(sb.toString());

		pstmt.setString(1, pk_crp);
		pstmt.setString(2, pk_pid);
		pstmt.setString(3, pk_crp);
		pstmt.setString(4, pk_cw);
		pstmt.setString(5, file_1);

		pstmt.executeUpdate();

		sb.delete(0, sb.length());

		sb.append(" update cost_productinfo ");
		sb.append(" set report_pk = ? ");
		sb.append(" where pk_cr_group = ? ");
		sb.append("   and rev_no = ? ");

		pstmt = null;
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, pk_crp);
		pstmt.setString(2, pk_cr_group);
		pstmt.setString(3, rev_no);

		pstmt.executeUpdate();
	    }

	    sb.delete(0, sb.length());

	    sb.append(" update wfinfo                       ");
	    sb.append("     set r_pre_day = sysdate     ");
	    sb.append("        ,step_no = '6'                   ");
	    sb.append("        ,approval = 'complete'           ");
	    sb.append(" where fk_cost in (select pk_cr from cost_request    ");
	    sb.append("                    where fk_pid in (select pk_pid from cost_productinfo ");
	    sb.append("                                      where pk_cr_group = ? and rev_no = ?))     ");

	    pstmt = null;
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);

	    pstmt.executeUpdate();

	    sb.delete(0, sb.length());

	    sb.append(" update cost_report  ");
	    sb.append(" set fk_wid = (select pk_wid from wfinfo where fk_cost in (select pk_cr from cost_request    ");
	    sb.append("                  where fk_pid in (select pk_pid from cost_productinfo       ");
	    sb.append("                                    where pk_cr_group = ? and rev_no = ? and group_no = 'T001'))    )    ");
	    sb.append(" where crp_group = ? ");

	    pstmt = null;
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    pstmt.setString(3, pk_crp);

	    pstmt.executeUpdate();

	    sb.delete(0, sb.length());

	    sb.append(" update cost_work                                       ");
	    sb.append("    set cost_report_add = 'ok'                          ");
	    sb.append("  where fk_pid in (select pk_pid from cost_productinfo  ");
	    sb.append("                    where pk_cr_group = ?               ");
	    sb.append("                      and rev_no = ?)                   ");

	    pstmt = null;

	    pstmt = conn.prepareStatement(sb.toString());

	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    pstmt.executeUpdate();

	    if (url_chk.equals("ok")) {

		String link_url = "http://plm.ket.com/plm/jsp/cost/costreport/cost_report_1.jsp?cost_report_add=ok&visitor=4&pk_cr_group="
		        + pk_cr_group + "&table_row=" + table_row + "&report_pk=" + pk_crp + "&rev_no=" + rev_no + "&user_case_count=";
		String parameter[] = pjt_no.split(",");
		for (int i = 0; i < parameter.length; i++) {
		    sb.delete(0, sb.length());

		    sb.append(" update cost_i           ");
		    sb.append("    set linkurl = ?      ");
		    sb.append("  where projectcode = ?  ");

		    pstmt = null;
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setString(1, link_url);
		    pstmt.setString(2, parameter[i]);
		    pstmt.executeUpdate();
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
    }
    
    
    /**
     * 함수명 : Plm_view_Search_1 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2015. 05. 22.
     */
    public ArrayList Plm_view_Search_1(String pjt_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = getCostIQuery();

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pjt_no);
	    pstmt.setString(2, pjt_no);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cr_group", StringUtil.checkNull(pjt_no));
		SearchMap.put("pjt_no", StringUtil.checkNull(pjt_no));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("devUser"))); // 개발담당자
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("devPartName"))); // 개발 검토 제품명
		SearchMap.put("team", StringUtil.checkNull(rs.getString("devDepartment"))); // 개발부서
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("sales"))); // 영업담당자
		SearchMap.put("drProgress", StringUtil.checkNull(rs.getString("drProgress"))); // DR 단계
		SearchMap.put("request_day", StringUtil.checkNull(rs.getString("devCostDate"))); // 개발 원가 제출 일정
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	}
	return SearchList;
    }
    
    /**
     * 함수명 : Plm_view_Search_2 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2015. 05. 22.
     */
    public ArrayList Plm_view_Search_2(String pjt_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = getCostIQuery();
	String car_type = "";
	String reviewProjectCode = "";
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pjt_no);
	    pstmt.setString(2, pjt_no);
	    rs = pstmt.executeQuery();
	    int i = 1;
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cr", StringUtil.checkNull(""));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(pjt_no));
		SearchMap.put("partSqe", StringUtil.checkNull(rs.getString("partSqe")));
		SearchMap.put("group_no", StringUtil.checkNull("T00"+Integer.toString(i)));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("partName"))); // 품목명
		SearchMap.put("part_no", StringUtil.checkNull(rs.getString("partNo"))); // 품번
		car_type = ProductHelper.getCarName(rs.getString("car"));
		SearchMap.put("car_type", StringUtil.checkNull(car_type));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("ea1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("ea2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("ea3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("ea4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("ea5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("ea6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("ea7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("ea8")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("subcontractor"))); // 고객처
		SearchMap.put("customer_L", StringUtil.checkNull(rs.getString("customerevent"))); // 최종사용처
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("expectValue"))); // 고객 예상가
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("targetValue"))); // 판매 목표가
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("targetRate"))); // 목표 수익율
		reviewProjectCode = StringUtil.checkNull(rs.getString("reviewProjectCode"));
		
		if (!reviewProjectCode.equals("") && reviewProjectCode != null) {
		    SearchMap.put("rev_pk", reviewProjectCode);
		} else {
		    SearchMap.put("rev_pk", pjt_no);
		}
		
		SearchMap.put("rev_no", StringUtil.checkNull(this.getMaxRevno(pjt_no)));
		SearchList.add(SearchMap);
		i++;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	}
	return SearchList;
    }
    
    /**
     * 함수명 : Plm_view_Search_3 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2015. 05. 22.
     */
    public List Plm_view_Search_3(String pjt_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	 
	List<PartBomInfoDTO> BomList = null;
	
	List<PartBomInfoDTO> BomFullList = new ArrayList<PartBomInfoDTO>();
		
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = getCostIQuery();

	String g_sel1 = "";
	String g_sel2 = "";
	String g_sel3 = "";
	String group_no = "";
	String partNo = "";
	String partOid = "";
	WTPart wtpart = null;
	int i = 1;
	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pjt_no);
	    pstmt.setString(2, pjt_no);
	    rs = pstmt.executeQuery();
	    
	    
	    while (rs.next()) {

		SearchMap = new Hashtable<String, String>();
		
		partNo = StringUtil.checkNull(rs.getString("partNo")); // 품번
		wtpart = PartBaseHelper.service.getLatestPart(partNo);
		partOid = CommonUtil.getOIDLongValue2Str(wtpart);
		
		BomList = KetCostInfoHelper.service.searchPlmFullList(partOid,"T00"+Integer.toString(i));
		
		
		for(PartBomInfoDTO dto: BomList){
		    BomFullList.add(dto);
		    //System.out.println(dto.toString()); 
		}
		
		i++;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	}
	return BomFullList;
    }
    
    /**
     * 함수명 : getPackList 설명 :
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 최윤정 작성일자 : 2015. 05. 21
     */
    public ArrayList getPackList(String request_id) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT cost_pack_id,request_id,pack_item,pack_unitcost,pack_quantity,pack_amount,pack_pro_quantity,pack_inputcost_using,pack_inputcost,pack_unitcost_total,pack_recovery,in_pack,out_pack FROM cost_pack_calc");
	sb.append("  where request_id = ?  ");

	try {

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, request_id);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("cost_pack_id", rs.getString("cost_pack_id"));
		SearchMap.put("request_id", rs.getString("request_id"));
		SearchMap.put("pack_item", rs.getString("pack_item"));
		SearchMap.put("pack_unitcost", rs.getString("pack_unitcost"));
		SearchMap.put("pack_quantity", rs.getString("pack_quantity"));
		SearchMap.put("pack_recovery", rs.getString("pack_recovery"));
		SearchMap.put("in_pack", rs.getString("in_pack"));
		SearchMap.put("out_pack", rs.getString("out_pack"));
		SearchMap.put("pack_amount", rs.getString("pack_amount"));
		SearchMap.put("pack_inputcost_using", rs.getString("pack_inputcost_using"));
		SearchMap.put("pack_pro_quantity", rs.getString("pack_pro_quantity"));
		SearchMap.put("pack_inputcost", rs.getString("pack_inputcost"));
		SearchMap.put("pack_unitcost_total", rs.getString("pack_unitcost_total"));

		SearchList.add(SearchMap);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	}
	return SearchList;
    }

    public int cost_pack_save(ArrayList createDataList) throws Exception {
	// TODO Auto-generated method stub
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;

	System.out.println("Babiss ::::::::::::::::::save11");

	String cost_pack_id = null;
	String request_id = null;
	String pack_item = null;
	String pack_unitcost = null;
	String pack_quantity = null;
	String in_pack = null;
	String out_pack = null;
	String pack_recovery = null;
	String pack_amount = null;
	String pack_inputcost_using = null;
	String pack_pro_quantity = null;
	String pack_inputcost = null;
	String pack_unitcost_total = null;

	for (int i = 0; i < createDataList.size(); i++) {
	    SearchMap = (Hashtable) createDataList.get(i);

	    request_id = (String) SearchMap.get("request_id");
	    pack_item = (String) SearchMap.get("pack_item");
	    pack_unitcost = (String) SearchMap.get("pack_unitcost");
	    pack_quantity = (String) SearchMap.get("pack_quantity");
	    in_pack = (String) SearchMap.get("in_pack");
	    out_pack = (String) SearchMap.get("out_pack");
	    pack_recovery = (String) SearchMap.get("pack_recovery");
	    pack_amount = (String) SearchMap.get("pack_amount");
	    pack_inputcost_using = (String) SearchMap.get("pack_inputcost_using");
	    pack_pro_quantity = (String) SearchMap.get("pack_pro_quantity");
	    pack_inputcost = (String) SearchMap.get("pack_inputcost");
	    pack_unitcost_total = (String) SearchMap.get("pack_unitcost_total");

	    sb.append(" INSERT INTO cost_pack_calc (cost_pack_id,         request_id,        pack_item,         pack_unitcost, pack_quantity,        ");
	    sb.append("                             in_pack,              out_pack,          pack_recovery,     pack_amount,   pack_inputcost_using, ");
	    sb.append("                             pack_pro_quantity,    pack_inputcost,    pack_unitcost_total  )                ");
	    sb.append("                      VALUES ( FN_GET_COST_NUMBERING('SEQ_COST_PACK_NUM'), ");
	    sb.append("                               ?, ?, ?, ?, ");
	    sb.append("                               ?, ?, ?, ?, ?,");
	    sb.append("                               ?, ?, ? )");
	    System.out.println(sb.toString());
	    try {
		pstmt = conn.prepareStatement(sb.toString());

		pstmt.setString(1, request_id);
		pstmt.setString(2, pack_item);
		pstmt.setString(3, pack_unitcost);
		pstmt.setString(4, pack_quantity);
		pstmt.setString(5, in_pack);
		pstmt.setString(6, out_pack);
		pstmt.setString(7, pack_recovery);
		pstmt.setString(8, pack_amount);
		pstmt.setString(9, pack_inputcost_using);
		pstmt.setString(10, pack_pro_quantity);
		pstmt.setString(11, pack_inputcost);
		pstmt.setString(12, pack_unitcost_total);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		/*
	         * if (conn != null) { conn.close(); }
	         */
	    }
	}
	return complet;
    }

    public int cost_pack_update(ArrayList updateDataList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;

	System.out.println("Babiss ::::::::::::::::::updateDataList");

	String cost_pack_id = null;
	String request_id = null;
	String pack_item = null;
	String pack_unitcost = null;
	String pack_quantity = null;
	String in_pack = null;
	String out_pack = null;
	String pack_recovery = null;
	String pack_amount = null;
	String pack_inputcost_using = null;
	String pack_pro_quantity = null;
	String pack_inputcost = null;
	String pack_unitcost_total = null;

	for (int i = 0; i < updateDataList.size(); i++) {
	    SearchMap = (Hashtable) updateDataList.get(i);

	    cost_pack_id = (String) SearchMap.get("cost_pack_id");
	    request_id = (String) SearchMap.get("request_id");
	    pack_item = (String) SearchMap.get("pack_item");
	    pack_unitcost = (String) SearchMap.get("pack_unitcost");
	    pack_quantity = (String) SearchMap.get("pack_quantity");
	    in_pack = (String) SearchMap.get("in_pack");
	    out_pack = (String) SearchMap.get("out_pack");
	    pack_recovery = (String) SearchMap.get("pack_recovery");
	    pack_amount = (String) SearchMap.get("pack_amount");
	    pack_inputcost_using = (String) SearchMap.get("pack_inputcost_using");
	    pack_pro_quantity = (String) SearchMap.get("pack_pro_quantity");
	    pack_inputcost = (String) SearchMap.get("pack_inputcost");
	    pack_unitcost_total = (String) SearchMap.get("pack_unitcost_total");

	    sb.append(" UPDATE cost_pack_calc ");
	    sb.append(" 	SET  request_id = ?,     pack_item = ?,     pack_unitcost = ?,  pack_quantity = ?,        in_pack = ? ,      ");
	    sb.append("              out_pack = ?,       pack_recovery = ?, pack_amount = ?,    pack_inputcost_using = ?, pack_pro_quantity = ?,");
	    sb.append("              pack_inputcost = ?, pack_unitcost_total = ?");
	    sb.append("         WHERE  cost_pack_id = ?");

	    System.out.println(sb.toString());
	    try {
		pstmt = conn.prepareStatement(sb.toString());

		pstmt.setString(1, request_id);
		pstmt.setString(2, pack_item);
		pstmt.setString(3, pack_unitcost);
		pstmt.setString(4, pack_quantity);
		pstmt.setString(5, in_pack);
		pstmt.setString(6, out_pack);
		pstmt.setString(7, pack_recovery);
		pstmt.setString(8, pack_amount);
		pstmt.setString(9, pack_inputcost_using);
		pstmt.setString(10, pack_pro_quantity);
		pstmt.setString(11, pack_inputcost);
		pstmt.setString(12, pack_unitcost_total);
		pstmt.setString(13, cost_pack_id);

		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
	    }
	}
	return complet;
    }

    public int cost_pack_Delete(ArrayList deleteDataList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;

	System.out.println("Babiss ::::::::::::::::::deleteDataList");

	String cost_pack_id = null;

	for (int i = 0; i < deleteDataList.size(); i++) {
	    SearchMap = (Hashtable) deleteDataList.get(i);

	    cost_pack_id = (String) SearchMap.get("cost_pack_id");

	    sb.append(" DELETE cost_pack_calc ");
	    sb.append("     WHERE  cost_pack_id = ?");

	    System.out.println(sb.toString());
	    try {

		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, cost_pack_id);
		complet = pstmt.executeUpdate() + complet;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
	    }
	}
	return complet;
    }
}
