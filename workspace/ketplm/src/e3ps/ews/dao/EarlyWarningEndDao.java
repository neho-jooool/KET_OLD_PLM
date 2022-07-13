/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EarlyWarningEndDao.java
 * 작성자 : 김경희
 * 작성일자 : 2011. 01. 03.
 */
package e3ps.ews.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.PlmDBUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : EarlyWarningEndDao
 * 설명 : 해제신청서 조회
 * 작성자 : 김경희
 * 작성일자 : 2011. 01. 03.
 */
public class EarlyWarningEndDao {
	
	/**
	 * 함수명 : ViewEndReqId
	 * 설명 : 
	 * @param masterOid
	 * @return String
	 * @throws 
	 * 작성자 : 김경희
	 * 작성일자 : 2011. 01. 03.
	 */
	public String ViewEndReqId (String masterOid){
		LoggableStatement pstmt = null;
		Connection conn = null;
		StringBuffer sb = null;
		ResultSet rs = null;
		String reqOid = null;
		
		try {
			conn = PlmDBUtil.getConnection();
			
			sb = new StringBuffer();
			sb.append(" SELECT CLASSNAMEA2A2||':'||IDA2A2     reqOid                                                                                                           \n"); 
			sb.append("   FROM KETEARLYWARNINGENDREQ                                                                                                                               \n"); 
			sb.append(" WHERE MODIFYSTAMPA2 in (SELECT max(MODIFYSTAMPA2)                                                                                          \n");  
			sb.append("                                              FROM KETEARLYWARNINGENDREQ                                                                                    \n");  
			sb.append("                                            WHERE IDA3MASTERREFERENCE in ( SELECT IDA3B5                                                           \n");
			sb.append("                                                                                                      FROM KETEARLYWARNINGENDREQLINK                     \n");
			sb.append("                                                                                                    WHERE 1=1                                                                \n");
			if (masterOid !=  null && !(masterOid.equals(""))){
				sb.append("                                                                                                     AND CLASSNAMEKEYROLEAOBJECTREF || ':' || IDA3A5 = '" + masterOid + "' ))");
			}
			
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				reqOid = rs.getString(1);
			}
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			try {
				statementRsClose(rs, pstmt);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
			PlmDBUtil.close(conn);
		}
		
		return reqOid;
	}
	
	/**
	 * 함수명 : statementRsClose
	 * 설명 : 
	 * @throws Exception
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 10. 18.
	 */
	public void statementRsClose(ResultSet rs, LoggableStatement pstmt) throws Exception
	{
		if( rs != null )
			rs.close();
		
		if( pstmt != null )
			pstmt.close();
	}

}
