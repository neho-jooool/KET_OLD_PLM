package ext.ket.part.code;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import ext.ket.shared.log.Kogger;

public class NumberCodeUtil {
   /*
  CREATE SEQUENCE TKLEE10.KET_PART_NAMING_SEQ
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
     */
    public static String getNextNamingCode(String seqName) throws Exception {
	java.text.DecimalFormat df = new java.text.DecimalFormat("0000");
	Long serialL = Long.valueOf(wt.fc.PersistenceHelper.manager.getNextSequence(seqName));
	return df.format(serialL.longValue());
    }

    public static String getMaxNamingCode() throws Exception {

	String code = null;

	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    sb = new StringBuffer();
	    sb.append(" SELECT MAX(NAMINGCODE) NAMINGCODE \n");
	    sb.append(" FROM KETPartNaming \n");

	    String sSql = sb.toString();
	    code = oDaoManager.queryForObject(sSql, new RowSetStrategy<String>() {

		@Override
		public String mapRow(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("NAMINGCODE");
		    return ret;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(NumberCodeUtil.class, se);
	    throw se;
	} catch (Exception e) {
	    Kogger.error(NumberCodeUtil.class, e);
	    throw e;
	}

	return code == null ? "1001" : code;
    }
    
    public static String getPartMaxSerial(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength
	    , String classPartType, boolean serialOnlyNumber)throws Exception {
	
	if(classPartType != null && classPartType.indexOf("F") != -1){
	    return getPartMaxSerialCodeFert(prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, classPartType, serialOnlyNumber);
	}else if(classPartType != null && classPartType.indexOf("68") != -1){
	    return getPartMaxSerialCode68(prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, classPartType, serialOnlyNumber);
	}else{
	    return getPartMaxSerialCode(prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, classPartType, serialOnlyNumber);
	}
    }
    
    private static String getPartMaxSerialCode68(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength
	    , String classPartType, boolean serialOnlyNumber)throws Exception {

	String code = null;

	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    if(prefix == null) prefix = "";
	    
	    sb = new StringBuffer();
	    sb.append(" SELECT MAX(SERIAL) SERIAL \n");
	    sb.append(" FROM ( \n");
	    
	    if(prefix != null && prefix.startsWith("R68")){
		
		sb.append(getMaxSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber ));
		prefix = prefix.substring(1, prefix.length());
		sb.append(" UNION \n");
		sb.append(getMaxSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber ));
		
	    }else{
		
		sb.append(getMaxSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber ));
		prefix = "R" + prefix;
		sb.append(" UNION \n");
		sb.append(getMaxSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber ));
	    }
	    sb.append(" ) \n");

	    String sSql = sb.toString();
	    code = oDaoManager.queryForObject(sSql, new RowSetStrategy<String>() {

		@Override
		public String mapRow(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("SERIAL");
		    return ret;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(NumberCodeUtil.class, se);
	    throw se;
	} catch (Exception e) {
	    Kogger.error(NumberCodeUtil.class, e);
	    throw e;
	}

	return code;
    }
    
    private static String getPartMaxSerialCodeFert(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength
	    , String classPartType, boolean serialOnlyNumber)throws Exception {
	
	String code = null;
	
	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    if(prefix == null) prefix = "";
	    
	    sb = new StringBuffer();
	    sb.append(" SELECT MAX(SERIAL) SERIAL \n");
	    sb.append(" FROM ( \n");
	    
	    if(prefix != null && prefix.startsWith("H")){
		
		sb.append(getMaxSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber ));
		prefix = prefix.substring(1, prefix.length());
		sb.append(" UNION \n");
		sb.append(getMaxSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber ));
		
	    }else{
		
		sb.append(getMaxSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber ));
		prefix = "H" + prefix;
		sb.append(" UNION \n");
		sb.append(getMaxSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber ));
	    }
	    sb.append(" ) \n");
	    
	    String sSql = sb.toString();
	    code = oDaoManager.queryForObject(sSql, new RowSetStrategy<String>() {
		
		@Override
		public String mapRow(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("SERIAL");
		    return ret;
		}
	    });
	    
	} catch (SQLException se) {
	    Kogger.error(NumberCodeUtil.class, se);
	    throw se;
	} catch (Exception e) {
	    Kogger.error(NumberCodeUtil.class, e);
	    throw e;
	}
	
	return code;
    }
    
    private static String getMaxSerailSql(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength, boolean serialOnlyNumber ) throws Exception{
	
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT MAX(SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + ")) SERIAL \n");
	sb.append(" FROM WTPARTMASTER \n");
	sb.append(" WHERE WTPARTNUMBER LIKE '" + prefix + "%" + suffix + "' \n");
	
	if(serialOnlyNumber){
	    sb.append(" AND REGEXP_LIKE(SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + "),  '^[[:digit:]]+$' ) \n");
	}
	
	if (rightWTpartNoLength > 0) {
	    sb.append(" AND LENGTH(WTPARTNUMBER) = " + rightWTpartNoLength + " \n");
	}
	
	if (StringUtils.isNotEmpty(escapeStr)) {
	    sb.append(" AND SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + ") NOT LIKE '%" + escapeStr + "%' \n");
	}

	// 제일 마지막 숫자는 생산관리의 요청으로 사용중이므로 해당 시리얼은 제외한다.
	if(serialOnlyNumber){
	    sb.append(" AND SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + ") != '" + getMakeMaxNumber(serialLength) + "' \n");
	}

	// 67번 고갈 방지
	if ("H67".equals(prefix) && serialLength == 4) {
	    sb.append("  AND WTPARTNUMBER NOT LIKE 'H679%' ");
	}
	
	if("2".equals(prefix)){
	    sb.append("  AND WTPARTNUMBER NOT LIKE '29%' ");
	}
	
	return sb.toString();
    }
    
    private static String getPartMaxSerialCode(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength
	    , String classPartType, boolean serialOnlyNumber)throws Exception {
	
	String code = null;
	
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    if(prefix == null) prefix = "";
	    
	    String sSql = getMaxSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber );
	    code = oDaoManager.queryForObject(sSql, new RowSetStrategy<String>() {
		
		@Override
		public String mapRow(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("SERIAL");
		    return ret;
		}
	    });
	    
	} catch (SQLException se) {
	    Kogger.error(NumberCodeUtil.class, se);
	    throw se;
	} catch (Exception e) {
	    Kogger.error(NumberCodeUtil.class, e);
	    throw e;
	}
	
	return code;
    }
   
    public static String getSerailNumberFormat(String no, int length) throws Exception {

	if (no == null)
	    return null;

	int index = Integer.parseInt(no);

	String indexStr = String.valueOf(index);
	if (indexStr.length() > length)
	    throw new Exception("# 숫자 시리얼 생성중에 채번할 코드가 고갈되었습니다. ");

	while (indexStr.length() != length) {
	    indexStr = "0" + indexStr;
	}

	return indexStr;
    }
    
    public static String getMakeMaxNumber(int length) throws Exception {

	String indexStr = "";
	while (indexStr.length() != length) {
	    indexStr = "9" + indexStr;
	}

	return indexStr;
    }
    
    public static String getBeforeMaxNumber(String maxNoStr) throws Exception {
	
	return String.valueOf(Integer.parseInt(maxNoStr) - 1);
    }
    
    public static boolean isMaxOrOver(String no, int length) throws Exception{
	
	if(no == null) return false;
	
	String maxNumberStr = getMakeMaxNumber(length);
	
	// KET는  999 같은 Max값을 생관의 요청으로 미리 사용 중임...
	if( no.length() > length || no.equals(maxNumberStr) ||  no.equals(getBeforeMaxNumber(maxNumberStr))  ){
	    return true;
	}else{
	    return false;
	}
    }
    
    public static void main(String[] args){
	
	String indexStr = "";
	int length = 3;
	Kogger.debug(NumberCodeUtil.class, indexStr.length()); 
	while (indexStr.length() != length) {
	    indexStr = "9" + indexStr;
	}

	Kogger.debug(NumberCodeUtil.class, indexStr); 
	
    }
    
}
