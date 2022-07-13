/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : PartnerDao.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 9. 7.
 */
package e3ps.ews.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import wt.pom.WTConnection;
import e3ps.common.query.LoggableStatement;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : PartnerDao
 * 설명 : 협력사 정보 조회
 * 작성자 : 김경희
 * 작성일자 : 2010. 9. 7.
 */
public class PartnerDao {

	private Connection conn;

	public PartnerDao(Connection conn){
		this.conn = conn;
	}

	public PartnerDao(){
		super();
	}

	/**
	 * 함수명 : ViewPartnerList
	 * 설명 :
	 * @param hash
	 * @return ArrayList
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 9. 7.
	 */
	public ArrayList ViewPartnerList ( Hashtable hash) throws Exception{
		LoggableStatement pstmt = null;
		int pstmtCnt = 1;
		StringBuffer sb = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		Hashtable<String, String> partner = null;

		try {

			Kogger.debug(getClass(), "----------------------쿼리실행시작");

			sb = new StringBuffer();
			sb.append( "SELECT PARTNERNO, PARTNERNAME, PARTNERTYPE, ADDRESS, REPRESENTATIVE, row_id  	            	 \n" );
			sb.append( "  FROM (																																         	 \n" );
			sb.append( "SELECT PARTNERNO, PARTNERNAME, PARTNERTYPE, ADDRESS, REPRESENTATIVE, ROWNUM row_id     \n" );
			sb.append( "  FROM (																																	          \n" );
			sb.append(" SELECT PARTNERNO ");
			sb.append("           , PARTNERNAME ");
			sb.append("           , PARTNERTYPE ");
			sb.append("           , ADDRESS ");
			sb.append("           , REPRESENTATIVE");
			sb.append("   FROM KETPARTNER ");
			sb.append(" WHERE 1=1");

			// PLM 시스템 1차 개선
            // 2013.06-18  남현승
			if (((String)hash.get("partnerName")).length() > 0){
				sb.append("     AND upper(PARTNERNAME) like upper('" +  StringUtil.getLikeQueryString((String) hash.get("partnerName")) + "')           \n");
			}

		    String partnerType = (String)hash.get("HpartnerType");
            if ( partnerType != null && partnerType.trim().length() > 0 && !partnerType.equalsIgnoreCase("null") ) {

                ArrayList<String> stateList = KETStringUtil.getToken(partnerType, ",");           // 종목
                if ( stateList.size() == 1 ) {
                    sb.append(" AND PARTNERTYPE = '" + stateList.get(0) + "'                             \n");
                } else {
                    sb.append(" AND PARTNERTYPE IN (" + KETStringUtil.getMultiSearchCondition(stateList) + ")                             \n");
                }
            }

			sb.append(" ORDER BY PARTNERNAME     ) ) ");

			Kogger.debug(getClass(), "  ---------------- 쿼리 ----- \n");
			Kogger.debug(getClass(),  sb.toString() );

			pstmt = new LoggableStatement( conn, sb.toString() );

			rs = pstmt.executeQuery();

			while (rs.next()){
				partner = new Hashtable<String, String>();
				partner.put("partnerno", StringUtil.checkNull(rs.getString("partnerno"))); // 협력사 번호
				partner.put("partnername", StringUtil.checkNull(rs.getString("partnername"))); // 협력사명
				partner.put("partnertype",StringUtil.checkNull(rs.getString("partnertype"))); // 협력사종목
				partner.put("address", StringUtil.checkNull(rs.getString("address"))); // 주소
				partner.put("representative", StringUtil.checkNull(rs.getString("representative"))); // 대표자성명
				list.add(partner);
			}

			Kogger.debug(getClass(), "----------------------쿼리실행종료");

		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}

		return list;
	}

	/**
	 * 함수명 : ViewPartnerListCnt
	 * 설명 :
	 * @param hash
	 * @return ArrayList
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 12. 27.
	 */
	public int ViewPartnerListCnt ( Hashtable hash) throws Exception{
		LoggableStatement pstmt = null;
		int pstmtCnt = 1;
		StringBuffer sb = null;
		ResultSet rs = null;
		int listCnt = 0;

		try {

			Kogger.debug(getClass(), "----------------------쿼리실행시작");

			sb = new StringBuffer();
			sb.append( "SELECT count(*)																												         \n" );
			sb.append("   FROM KETPARTNER ");
			sb.append(" WHERE 1=1");
			if (((String)hash.get("partnerName")).length() > 0){
				sb.append("     AND upper(PARTNERNAME) like ? ");
			}
			if (((String)hash.get("partnerType")).length() > 0){
				sb.append("     AND PARTNERTYPE  = ? ");
			}

			pstmt = new LoggableStatement( conn, sb.toString() );

			if (((String)hash.get("partnerName")).length() > 0){
				pstmt.setString( pstmtCnt++, StringUtil.getLikeQueryString(((String)hash.get("partnerName")).toUpperCase()));
			}
			if (((String)hash.get("partnerType")).length() > 0){
				pstmt.setString( pstmtCnt++,  (String)hash.get("partnerType"));
			}

			rs = pstmt.executeQuery();

			while (rs.next()){
				listCnt = rs.getInt(1);
			}

			Kogger.debug(getClass(), "----------------------쿼리실행종료");

		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}

		return listCnt;
	}

	/**
	 * 함수명 : ViewPartnerType
	 * 설명 :
	 * @return ArrayList
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 9. 7.
	 */
	public ArrayList ViewPartnerType (){
		LoggableStatement pstmt = null;
		Connection conn = null;
		StringBuffer sb = null;
		ResultSet rs = null;
		ArrayList list = null;

		try {
			conn = PlmDBUtil.getConnection();

			sb = new StringBuffer();
			sb.append("     SELECT DISTINCT PARTNERTYPE ");
			sb.append("       FROM KETPARTNER ");
			sb.append(" ORDER BY PARTNERTYPE ");

			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();

			list = new ArrayList();

			while (rs.next()){
				list.add(rs.getString(1));
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

		return list;
	}

	/**
	 * 함수명 : ViewPartnerName
	 * 설명 :
	 * @param partnerCode
	 * @return String
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 10. 25.
	 */
	public String ViewPartnerName (String partnerNo){
		LoggableStatement pstmt = null;
		Connection conn = null;
		StringBuffer sb = null;
		ResultSet rs = null;
		String partnerName = null;

		try {
			conn = PlmDBUtil.getConnection();

			sb = new StringBuffer();
			sb.append(" SELECT PARTNERNAME ");
			sb.append("   FROM KETPARTNER ");
			sb.append(" WHERE 1=1");
			if (partnerNo !=  null && !(partnerNo.equals(""))){
				sb.append("     AND PARTNERNO = '" + partnerNo + "' ");
			}

			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();

			while (rs.next()){
				partnerName = rs.getString(1);
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

		return partnerName;
	}

	/**
	 * 함수명 : ViewPartnerNo
	 * 설명 :
	 * @param partnerName
	 * @return String
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 12. 07.
	 */
	public String ViewPartnerNo (String partnerName){
		LoggableStatement pstmt = null;
		Connection conn = null;
		StringBuffer sb = null;
		ResultSet rs = null;
		String partnerNo = null;

		try {
			conn = PlmDBUtil.getConnection();

			sb = new StringBuffer();
			sb.append(" SELECT PARTNERNO ");
			sb.append("   FROM KETPARTNER ");
			sb.append(" WHERE 1=1");
			if (partnerName !=  null && !(partnerName.equals(""))){
				sb.append("     AND PARTNERNAME = '" + partnerName + "' ");
			}

			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();

			while (rs.next()){
				partnerNo = rs.getString(1);
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

		return partnerNo;
	}

	/**
	 * 함수명 : InsertPartner
	 * 설명 :
	 * @param vendorList
	 * @return boolean
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 11. 24.
	 */
	public boolean InsertPartner (ArrayList vendorList, WTConnection wtconnection){
		LoggableStatement pstmt = null;
		int pstmtCnt = 1;
		Connection conn = null;
		StringBuffer sb = null;
		int rs = 0;
		boolean flag = false;
		Hashtable<String, String> vendor = null;

		try {
			conn = wtconnection.getConnection();
			conn.setAutoCommit(false);

			sb = new StringBuffer();
			sb.append(" MERGE INTO KETPARTNER ");
			sb.append("          USING DUAL ");
			sb.append("               ON ( PARTNERNO = ? ) ");
			sb.append(" WHEN MATCHED THEN ");
			sb.append(" UPDATE SET  ");
			sb.append("              PARTNERNAME = ? " );
			sb.append("             ,PARTNERTYPE = ? "  );
			sb.append("             ,ADDRESS = ? " );
			sb.append("             ,REPRESENTATIVE = ? " );
			sb.append(" WHEN NOT MATCHED THEN ");
			sb.append(" INSERT  ");
			sb.append("           ( PARTNERNO  ");
			sb.append("           , PARTNERNAME  ");
			sb.append("           , PARTNERTYPE  ");
			sb.append("           , ADDRESS  ");
			sb.append("           , REPRESENTATIVE)  ");
			sb.append(" VALUES  ");
			sb.append("           ( ? " );
			sb.append("           , ? " );
			sb.append("           , ? " );
			sb.append("           , ? " );
			sb.append("           , ? ) " );

			pstmt = new LoggableStatement( conn, sb.toString() );

			for ( int inx = 0 ; inx < vendorList.size() ; inx++){
				pstmtCnt = 1;
				vendor = (Hashtable)vendorList.get(inx);

				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("code")) );  // 협력사코드
				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("name")) );  // 협력사명
				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("type")) );  // 종목
				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("address")) );  // 주소
				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("representative")) );  // 대표자성명
				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("code")) );  // 협력사코드
				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("name")) );  // 협력사명
				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("type")) );  // 종목
				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("address")) );  // 주소
				pstmt.setString( pstmtCnt++, StringUtil.checkNull((String) vendor.get("representative")) );  // 대표자성명

				rs = pstmt.executeUpdate();

			}

			conn.commit();
			flag = true;

		} catch (SQLException se) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				Kogger.error(getClass(), e);
			}
			flag = false;
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Kogger.error(getClass(), e1);
			}
			flag = false;
			Kogger.error(getClass(), e);
		} finally {
			try {
				if( pstmt != null )
					pstmt.close();
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
			PlmDBUtil.close(conn);
		}

		return flag;
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
