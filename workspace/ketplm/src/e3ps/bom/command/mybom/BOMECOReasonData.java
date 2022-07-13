/**
 * @author : Park Pil Keun (pkpark@lgcns.com)
 * @since  : 2006.09.26
 * @description : ECM의 reason을 가져오는 data class
 */
package e3ps.bom.command.mybom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class BOMECOReasonData {

	Registry registry;
	DBConnectionManager res;
	Connection conn;
	Statement stmt;

	Hashtable reasonHash = new Hashtable();
	Vector reasonDesc = new Vector();

	public BOMECOReasonData() {
		try {
			registry = Registry.getRegistry("e3ps.bom.bom");
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(registry.getString("plm"));
			stmt = conn.createStatement();
	
//			String reasonSql = " SELECT CODE, DESCRIPTION"
//							 + " FROM LSISECMCODE"
//							 + " WHERE CATEGORY = '01' AND ISVALID = 1";
			
			String reasonSql = " SELECT n.code CODE, n.name DESCRIPTION"
				 				 + " FROM numbercode n	"
				 				 + " WHERE n.codetype = PRODECOREASON";			// 테스트를 위해 제품쪽 변경 사유를 가져오는것으로 임시로 넣어놓음 

			ResultSet rs = stmt.executeQuery(reasonSql);

			reasonDesc.addElement("");

			while (rs.next()) {
				reasonHash.put(rs.getString("DESCRIPTION"), rs.getString("CODE"));
				reasonDesc.addElement(rs.getString("DESCRIPTION"));
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
				if (res != null) res.freeConnection(registry.getString("plm"), conn);
			} catch (SQLException sqe) {
				Kogger.error(getClass(), sqe);
			}
		}
	}

	public String getReasonCode(String desc) {

		return checkNVL(reasonHash.get(desc));
	}

	public Vector getReasonDesc() {
		return reasonDesc;
	}

	/**
	 * String 형태의 Object 들을 받아서 null 처리 해주기 
	 * @return String
	 */
	private String checkNVL(Object str) {
		if (str == null || ((String)str).equals("null")) {
			return "";
		} else {
			return ((String)str).trim();
		}
	}
}
