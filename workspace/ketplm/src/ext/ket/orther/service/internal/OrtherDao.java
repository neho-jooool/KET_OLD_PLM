package ext.ket.orther.service.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.PlmDBUtil;
import ext.ket.orther.entity.dto.OrtherDto;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.shared.log.Kogger;

public class OrtherDao {
    final Connection conn;

    public OrtherDao(Connection conn) {
	this.conn = conn;
    }

    public OrtherDto getDividendInfo(OrtherDto paramObject) {
	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	try {
	    sb = new StringBuffer();
	    sb.append("SELECT B24.DIVI_YEAR,                                    ");
	    sb.append("       L21.SITE,                                         ");
	    // sb.append("	      L21.DEPT_CODE,                                    ");
	    sb.append("       B21.EMP_NO,                                       ");
	    sb.append("       B24.SAEMAL_ACCT_NO,                                       ");
	    sb.append("       B24.PAYM_SUM as PAYM_SUM,          ");
	    sb.append("       L21.KOR_NAME,                                                      ");
	    sb.append("       B24.YEAR_SHARE_RATE,                                               ");
	    sb.append("       DIVI_AMT AS DIVI_AMT,                   ");
	    sb.append("       B21.MM_PAYM_AMT AS MM_PAYM_AMT,             ");
	    sb.append("       B24.DIVI_YN,                                                       ");
	    sb.append("       B24.PRIN_YN,                                      		 ");
	    sb.append("       B24.AFR_MM_PAYM_AMT AS  AFR_MM_PAYM_AMT,     ");
	    sb.append("       B24.PRIN_AMT AS  PRIN_AMT     ");
	    sb.append("  FROM HCLUB24@legacy B24,                                      	         ");
	    sb.append("       HPSNL21@legacy L21,                                      ");
	    sb.append("       HCLUB21@legacy B21                                       ");
	    sb.append(" WHERE B24.INVEST_GUBN = B21.INVEST_GUBN                 ");
	    sb.append("   AND B24.SAEMAL_ACCT_NO = B21.SAEMAL_ACCT_NO           ");
	    sb.append("   AND B21.EMP_NO = L21.EMP_NO                           ");
	    sb.append("   AND B24.INVEST_GUBN = '1'                             ");
	    sb.append("   AND B24.DIVI_YEAR = TO_CHAR(SYSDATE,'YYYY') -1        ");
	    sb.append("   AND L21.SITE LIKE '1%'                                ");
	    sb.append("   AND L21.RETIRE_GUBN IN ('0')                          ");
	    sb.append("   AND B21.EMP_NO = '" + paramObject.getEmp_no() + "'                         ");

	    pstmt = new LoggableStatement(conn, sb.toString());

	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		paramObject.setDivi_year(rs.getString("DIVI_YEAR"));
		paramObject.setSite(rs.getString("SITE"));
		paramObject.setSaemal_acct_no(rs.getString("SAEMAL_ACCT_NO"));
		paramObject.setPaym_sum(rs.getString("PAYM_SUM"));
		paramObject.setKor_name(rs.getString("KOR_NAME"));
		paramObject.setYear_share_rate(rs.getString("YEAR_SHARE_RATE"));
		paramObject.setDivi_amt(rs.getString("DIVI_AMT"));
		paramObject.setMm_paym_amt(rs.getString("MM_PAYM_AMT"));
		paramObject.setDivi_yn(rs.getString("DIVI_YN"));
		paramObject.setPrin_yn(rs.getString("PRIN_YN"));
		paramObject.setAfr_mm_paym_amt(rs.getString("AFR_MM_PAYM_AMT"));
		paramObject.setPrin_amt(rs.getString("PRIN_AMT"));
	    }
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	    PlmDBUtil.close(conn);
	}

	return paramObject;
    }

    
    public void dividendSave(OrtherDto paramObject) {

	PreparedStatement pstmt = null;

	try {

	    StringBuffer buffer = new StringBuffer();

	    buffer.append("UPDATE HCLUB24@legacy ");
	    buffer.append("   SET DIVI_YN = '" + paramObject.getCheck_divi_yn() + "'      ");
	    buffer.append("      ,PRIN_YN = '" + paramObject.getCheck_prin_yn() + "'      ");
	    buffer.append("      ,AFR_MM_PAYM_AMT = '" + paramObject.getAfr_mm_paym_amt() + "'      ");
	    buffer.append("      ,PRIN_AMT = '" + paramObject.getPrin_amt() + "'      ");
	    buffer.append(" WHERE DIVI_YEAR = TO_CHAR(SYSDATE,'YYYY')-1 AND SAEMAL_ACCT_NO = '" + paramObject.getSaemal_acct_no()+ "' AND INVEST_GUBN = '1' ");
	    
	    pstmt = conn.prepareStatement(buffer.toString());
	    pstmt.executeQuery();
		
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(pstmt);
	    PlmDBUtil.close(conn);
	}
    }
}
