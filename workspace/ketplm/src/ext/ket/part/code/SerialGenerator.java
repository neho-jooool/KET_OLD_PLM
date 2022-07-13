package ext.ket.part.code;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategyMap;
import ext.ket.shared.log.Kogger;

public class SerialGenerator {
    
    private Map<String,String> serialMap = new HashMap<String, String>();
    private int idx = 0;
    
    private String prefix;
    private String suffix; 
    private int serialLength; 
    private String escapeStr; 
    private int rightWTpartNoLength;
    private String classPartType;
    private boolean serialOnlyNumber;
    private String minNo;
    
    public SerialGenerator(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength
	    , String classPartType, boolean serialOnlyNumber, String minNo) {
	
	this.prefix = prefix;
	this.suffix = suffix;
	this.serialLength = serialLength;
	this.escapeStr = escapeStr;
	this.rightWTpartNoLength = rightWTpartNoLength;
	this.classPartType = classPartType;
	this.serialOnlyNumber = serialOnlyNumber;
	this.minNo = minNo;
    }
    
    public String getNextSerial() throws Exception{
	
	String serial = null;
	
	if(serialOnlyNumber){
	    
	    if(idx == 0){
		if(StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)){
		    idx = Integer.parseInt(minNo);
		}
	    }else{
		idx++;
	    }
	    
	    int maxNo = getMakeMaxNumberInt(serialLength);
	    for(;idx <= maxNo; idx++){
		String tempSerial = getSerailNumberFormat(idx, serialLength);
		if(serialMap.containsKey(tempSerial)){
		    continue;
		}else{
		    serial = tempSerial;
		    break;
		}
	    }
	    
	    if(serial == null){
		throw new Exception("사용할 수 있는 시리얼이 없습니다.");
	    }
	    
	    return serial;
	    
	}else{
	    throw new Exception("사용하지 않는 로직 : 프로그램 오류.");
	}
	
    }
    
    // serial을 미리 따놓자
    public void makeReady(){
	
    }
    
    // //////////////////////////////////////////////////////////////////////////////////
    //
    // Max 값을 가져오는 것을 Min에서 100개 Serial 가져오는 것으로 변경함.
    //
    // //////////////////////////////////////////////////////////////////////////////////
    public void searchSerial()throws Exception {
	
	if(classPartType != null && classPartType.indexOf("F") != -1){
	    getPartMinSerialCodeFert(prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, classPartType, serialOnlyNumber, minNo);
	}else if(classPartType != null && classPartType.indexOf("68") != -1){
	    getPartMinSerialCode68(prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, classPartType, serialOnlyNumber, minNo);
	}else if(classPartType != null && classPartType.indexOf("50") != -1){
	    getPartMinSerialCode50(prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, classPartType, serialOnlyNumber, minNo);
	}else{
	    getPartMinSerialCode(prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, classPartType, serialOnlyNumber, minNo);
	}
    }
    
    private void getPartMinSerialCode68(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength
	    , String classPartType, boolean serialOnlyNumber, String minNo)throws Exception {

	Map<String,String> code = null;

	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    if(prefix == null) prefix = "";
	    
	    sb = new StringBuffer();
	    sb.append(" SELECT DISTINCT SERIAL AS SERIAL \n");
	    sb.append(" FROM ( \n");
	    
	    if(prefix != null && prefix.startsWith("R68")){
		
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
		prefix = prefix.substring(1, prefix.length());
		sb.append(" UNION \n");
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
		
	    }else{
		
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
		prefix = "R" + prefix;
		sb.append(" UNION \n");
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
	    }
	    sb.append(" ) \n");
	    sb.append(" ORDER BY SERIAL \n");

	    String sSql = sb.toString();
	    code = oDaoManager.queryForMap(sSql, new RowSetStrategyMap<String>() {

		@Override
                public String getKey(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("SERIAL");
		    return ret;
                }

		@Override
                public String mapRow(ResultSet oResultSet) throws SQLException {
	            return null;
                }

	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	    throw se;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	if(code != null){
	    serialMap = code;
	}
    }
    
    private void getPartMinSerialCode50(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength
	    , String classPartType, boolean serialOnlyNumber, String minNo)throws Exception {

	Map<String,String> code = null;

	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    if(prefix == null) prefix = "";
	    
	    sb = new StringBuffer();
	    sb.append(" SELECT DISTINCT SERIAL AS SERIAL \n");
	    sb.append(" FROM ( \n");
	    
	    if(prefix != null && prefix.startsWith("R50")){
		
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
		prefix = prefix.substring(1, prefix.length());
		sb.append(" UNION \n");
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
		
	    }else{
		
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
		prefix = "R" + prefix;
		sb.append(" UNION \n");
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
	    }
	    sb.append(" ) \n");
	    sb.append(" ORDER BY SERIAL \n");

	    String sSql = sb.toString();
	    code = oDaoManager.queryForMap(sSql, new RowSetStrategyMap<String>() {

		@Override
                public String getKey(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("SERIAL");
		    return ret;
                }

		@Override
                public String mapRow(ResultSet oResultSet) throws SQLException {
	            return null;
                }

	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	    throw se;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	if(code != null){
	    serialMap = code;
	}
    }
    
    private void getPartMinSerialCodeFert(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength
	    , String classPartType, boolean serialOnlyNumber, String minNo)throws Exception {
	
	Map<String,String> code = null;
	
	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    if(prefix == null) prefix = "";
	    
	    sb = new StringBuffer();
	    sb.append(" SELECT DISTINCT SERIAL AS SERIAL \n");
	    sb.append(" FROM ( \n");
	    
	    if(prefix != null && prefix.startsWith("H")){
		
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
		prefix = prefix.substring(1, prefix.length());
		sb.append(" UNION \n");
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
		
	    }else{
		
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
		prefix = "H" + prefix;
		sb.append(" UNION \n");
		sb.append(getMinSerailSql( prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, false, minNo ));
	    }
	    sb.append(" ) \n");
	    sb.append(" ORDER BY SERIAL \n");
	    
	    String sSql = sb.toString();
	    code = oDaoManager.queryForMap(sSql, new RowSetStrategyMap<String>() {
		
		@Override
                public String getKey(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("SERIAL");
		    return ret;
                }

		@Override
                public String mapRow(ResultSet oResultSet) throws SQLException {
	            return null;
                }
	    });
	    
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	    throw se;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	if(code != null){
	    serialMap = code;
	}
    }
    
    private String getMinSerailSql(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength
	    , boolean serialOnlyNumber, boolean needOrderBy, String minNo) throws Exception {

	StringBuffer sb = new StringBuffer();
	
	if(needOrderBy){
	    sb.append(" SELECT DISTINCT SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + ") AS SERIAL \n");
	}else{
	    sb.append(" SELECT SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + ") AS SERIAL \n");
	}
	sb.append(" FROM WTPARTMASTER \n");
	sb.append(" WHERE WTPARTNUMBER LIKE '" + prefix + "%" + suffix + "' \n");

	if (serialOnlyNumber) {
	    sb.append(" AND REGEXP_LIKE(SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + "),  '^[[:digit:]]+$' ) \n");
	}

	if (rightWTpartNoLength > 0) {
	    sb.append(" AND LENGTH(WTPARTNUMBER) = " + rightWTpartNoLength + " \n");
	}else{
	    sb.append(" AND LENGTH(WTPARTNUMBER) >= " + ( prefix.length() + serialLength ) + " \n");
	}

	if (StringUtils.isNotEmpty(escapeStr)) {
	    sb.append(" AND SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + ") NOT LIKE '%" + escapeStr + "%' \n");
	}
	
	// TODO TKLEE 체크 필요
	// 최소값 적용
	if(StringUtils.isNotEmpty(minNo) && !"-".equals(minNo) && serialOnlyNumber && serialLength == minNo.length()){
	    sb.append(" AND SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + ") >= '" + minNo + "' \n");
	}

	// 제일 마지막 숫자는 생산관리의 요청으로 사용중이므로 해당 시리얼은 제외한다.
//	if (serialOnlyNumber) {
//	    sb.append(" AND SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + ") != '" + getMakeMaxNumber(serialLength) + "' \n");
//	}

//	// 67번 고갈 방지
//	if ("H67".equals(prefix) && serialLength == 4) {
//	    sb.append("  AND WTPARTNUMBER NOT LIKE 'H679%' \n");
//	}

//	if ("2".equals(prefix)) {
//	    sb.append("  AND WTPARTNUMBER NOT LIKE '29%' \n");
//	}
	
	if(needOrderBy){
	    sb.append(" ORDER BY SUBSTR(WTPARTNUMBER, " + (prefix.length() + 1) + ", " + serialLength + ") \n");
	}

	return sb.toString();
    }

    private void getPartMinSerialCode(String prefix, String suffix, int serialLength, String escapeStr
	    , int rightWTpartNoLength, String classPartType, boolean serialOnlyNumber, String minNo) throws Exception {

	Map<String,String> code = null;

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    if (prefix == null)
		prefix = "";

	    String sSql = getMinSerailSql(prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, serialOnlyNumber, true, minNo);
	    code = oDaoManager.queryForMap(sSql, new RowSetStrategyMap<String>() {

		@Override
                public String getKey(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("SERIAL");
		    return ret;
                }

		@Override
                public String mapRow(ResultSet oResultSet) throws SQLException {
	            return null;
                }
		
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	    throw se;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	if(code != null){
	    serialMap = code;
	}
	
    }
    
    private String getMakeMaxNumber(int length) throws Exception {

	String indexStr = "";
	while (indexStr.length() != length) {
	    indexStr = "9" + indexStr;
	}

	return indexStr;
    }
    
    private int getMakeMaxNumberInt(int length) throws Exception {
	
	String indexStr = "";
	while (indexStr.length() != length) {
	    indexStr = "9" + indexStr;
	}
	
	return Integer.parseInt(indexStr);
    }
    
    private String getSerailNumberFormat(int intNo, int length) throws Exception {

//	if (no == null)
//	    return null;

//	int index = Integer.parseInt(no);

	String indexStr = String.valueOf(intNo);
	
//	if (indexStr.length() > length)
//	    throw new Exception("# 숫자 시리얼 생성중에 채번할 코드가 고갈되었습니다. ");

	while (indexStr.length() != length) {
	    indexStr = "0" + indexStr;
	}

	return indexStr;
    }

}
