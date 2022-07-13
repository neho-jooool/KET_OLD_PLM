package legacy.qmsInterface;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import legacy.qmsInterface.dao.DBUtil;
import legacy.qmsInterface.dao.DaoFactory;
import legacy.qmsInterface.dao.DaoManager;
import legacy.qmsInterface.dao.RowSetStrategy;

public class legacy2qms {

    public static String strDirPath = "D:\\QmsInterface\\BatchScript\\logs";
    public static FileWriter fw = null;
    public static BufferedWriter bw = null;
    public static String currentDate = getCurrentDateString(new SimpleDateFormat("yyyy-MM-dd"));
    public static String currentTime = getCurrentDateString(new SimpleDateFormat("HH:mm:ss"));

    public static String strFileName = "QmsInterface_" + currentDate + "_" + currentTime.replace(":", "") + ".txt";
    public static String ErrMsg = "";

    public static void main(String[] args) throws Exception {

	String point = null;

	int deptSize = 0;
	int positionSize = 0;
	int userSize = 0;

	try {
	    File dir = new File(strDirPath);

	    if (!dir.exists()) { // 로그 저장 폴더가 존재하지 않는 경우 생성함
		dir.mkdir();
	    }

	    File file = new File(strDirPath + "\\" + strFileName);
	    fw = new FileWriter(file, true);
	    bw = new BufferedWriter(fw);
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::: " + getLogDate() + " legacy to qms INTERFACE  start :::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write(getLogDate() + " 부서정보 조회 getLegacyDeptInfo() 호출 start :::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();

	    List<HashMap<String, String>> deptList = null;
	    List<HashMap<String, String>> positionList = null;
	    List<HashMap<String, String>> userList = null;

	    List<HashMap<String, String>> itms3LevleDeptList = null;

	    legacy2qms ly2 = new legacy2qms();

	    point = "부서정보 조회 [ getLegacyDeptInfo() 메써드 호출 중] ";
	    try {
		deptList = ly2.getLegacyDeptInfo();
		deptSize = deptList.size();
		try {
		    if (deptSize < 1) {

			throw new Exception("부서 정보 조회 결과가 없습니다!\r\n\r\n");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    ErrorWrite(e, point);
		    return;
		}
	    } catch (Exception e) {
		e.printStackTrace();
		ErrorWrite(e, point);
		return;
	    }
	    bw.newLine();
	    bw.write(getLogDate() + " 부서정보  " + deptSize + " 건 Fetch 완료 ");
	    bw.newLine();
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write(getLogDate() + " 부서정보 조회 getLegacyDeptInfo() 호출 End ::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write(getLogDate() + " 직급/직책 조회 getLegacyPositionInfo() 호출 start ::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();

	    point = "직급/직책 조회 [ getLegacyPositionInfo() 메써드 호출 중] ";
	    try {
		positionList = ly2.getLegacyPositionInfo();
		positionSize = positionList.size();
		try {
		    if (positionSize < 1) {

			throw new Exception("직급/직책 정보 조회 결과가 없습니다!\r\n\r\n");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    ErrorWrite(e, point);
		    return;
		}
	    } catch (Exception e) {
		e.printStackTrace();
		ErrorWrite(e, point);
		return;
	    }

	    bw.newLine();
	    bw.write(getLogDate() + " 직급/직책 정보  " + positionSize + " 건 Fetch 완료 ");
	    bw.newLine();
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write(getLogDate() + " 직급/직책 조회 getLegacyPositionInfo() 호출 End ::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.newLine();

	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write(getLogDate() + " 사용자 정보 조회 getLegacyUserInfo() 호출 start ::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();

	    point = "사용자 정보 조회 [ getLegacyUserInfo() 메써드 호출 중] ";
	    try {
		userList = ly2.getLegacyUserInfo();
		userSize = userList.size();
		try {
		    if (userSize < 1) {

			throw new Exception("사용자 정보 조회 정보 조회 결과가 없습니다!\r\n\r\n");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    ErrorWrite(e, point);
		    return;
		}
	    } catch (Exception e) {
		e.printStackTrace();
		ErrorWrite(e, point);
		return;
	    }

	    bw.newLine();
	    bw.write(getLogDate() + " 사용자 정보  " + userSize + " 건 Fetch 완료 ");
	    bw.newLine();
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write(getLogDate() + " 사용자 정보 조회 getLegacyUserInfo() 호출 End ::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.newLine();
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::: " + getLogDate() + " legacy 정보 추출 완료.. qms로 이관을 시작합니다 :::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();

	    point = " qms로 데이터 이관  [ updateQms() 메써드 호출 중] ";

	    try {
		ly2.updateQms(deptList, positionList, userList);
	    } catch (Exception e) {
		e.printStackTrace();
		ErrorWrite(e, point);
		return;
	    }

	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();

	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::: " + getLogDate() + " legacy to qms INTERFACE  End ::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.newLine();
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::: "+ getLogDate()+" legacy to itms INTERFACE  start :::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write(getLogDate()+" 부서정보 조회 getLegacyItmsDept3LevelInfo() 호출 start :::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    //
	    // point = "부서정보 조회 [ getLegacyItmsDept3LevelInfo() 메써드 호출 중] ";
	    // try{
	    // itms3LevleDeptList = ly2.getLegacyItmsDept3LevelInfo();
	    // deptSize = itms3LevleDeptList.size();
	    // try{
	    // if(deptSize < 1){
	    //
	    // throw new Exception("부서 정보 조회 결과가 없습니다!\r\n\r\n" );
	    // }
	    // }catch(Exception e){
	    // e.printStackTrace();
	    // ErrorWrite(e, point);
	    // return;
	    // }
	    // }catch(Exception e){
	    // e.printStackTrace();
	    // ErrorWrite(e, point);
	    // return;
	    // }
	    // bw.newLine();
	    // bw.write(getLogDate()+" 부서정보  "+ deptSize +" 건 Fetch 완료 ");
	    // bw.newLine();
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write(getLogDate()+" 부서정보 조회 getLegacyItmsDept3LevelInfo() 호출 End ::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    //
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::: "+ getLogDate()+" legacy 정보 추출 완료.. itms로 이관을 시작합니다 :::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    //
	    // point = " itms로 데이터 이관  [ updateItms() 메써드 호출 중] ";
	    //
	    // try{
	    // ly2.updateItms(itms3LevleDeptList);
	    // }catch(Exception e){
	    // e.printStackTrace();
	    // ErrorWrite(e, point);
	    // return;
	    // }
	    //
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    //
	    //
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::: "+ getLogDate()+" legacy to itms INTERFACE  End ::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.newLine();
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    // bw.write("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    bw.close();
	}

    }

    /**
     * 
     * 
     * @return
     * @메소드명 : getLegacyDeptInfo
     * @작성자 : 황정태
     * @작성일 : 2015. 9. 10.
     * @설명 : Legacy 부서정보 조회
     * @수정이력
     * 
     */

    private List<HashMap<String, String>> getLegacyDeptInfo() throws Exception {

	/*
	 * 1 : 한국단자공업(주) 3 : 공영산업(주) 4 : 케이이티솔루션(주) 5 : (주)케.이.티.인터내쇼날 6 : 중국법인 7 : 경원산업 (사용 안함) 8 : 경원전자 9 : 협력업체 (사용 안함) E : 케이이티엔지니어링(주)
	 * H : (주)한빛누리 N : 케이티네트워크(주) P : 폴란드법인 V : 베트남법인 M : 멕시코법인 A : 미국법인 J : 일본법인
	 */
	List<HashMap<String, String>> deptList = new ArrayList<HashMap<String, String>>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT DEPTCODE, PARENTDEPT, SUBSTR(DEPTCODE,1,1) AS COMPANYCODE, DEPTNAME, TRIM(DEPTORDER) AS DEPTORDER,'1' AS USE_YN FROM VW_DP_DEPT WHERE SUBSTR(TRIM(DEPTCODE),1,1 ) IN ('1', '4', '6', 'N','V','P','M','A','J') \n");
	    sb.append("    AND TRIM(DEPTCODE) IN (SELECT TRIM(LEGACYDEPTCODE) FROM V_GWIF_LEGACYDEPT WHERE USEYN = 'Y')  \n");
	    sb.append("  ORDER BY SUBSTR(DEPTCODE,1,1) \n");
	    String query = sb.toString();

	    deptList = oDaoManager.queryForList(query, new RowSetStrategy<HashMap<String, String>>() {

		@Override
		public HashMap<String, String> mapRow(ResultSet rs) throws SQLException {
		    HashMap<String, String> map = new HashMap<String, String>();
		    map.put("deptcode", rs.getString("DEPTCODE"));
		    map.put("parentdept", rs.getString("PARENTDEPT"));
		    map.put("companycode", rs.getString("COMPANYCODE"));
		    map.put("deptname", rs.getString("DEPTNAME"));
		    map.put("deptorder", rs.getString("DEPTORDER"));
		    map.put("use_yn", rs.getString("USE_YN"));
		    return map;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return deptList;
    }

    /**
     * 
     * 
     * @return
     * @메소드명 : getLegacyPositionInfo
     * @작성자 : 황정태
     * @작성일 : 2015. 9. 10.
     * @설명 : Legacy 직급/직책정보 조회
     * @수정이력
     * 
     */

    private List<HashMap<String, String>> getLegacyPositionInfo() throws Exception {

	List<HashMap<String, String>> positonList = new ArrayList<HashMap<String, String>>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append(" select v_code, v_gubn, v_name from V_POSITION  \n");
	    sb.append("  order by v_gubn, v_code                       \n");
	    String query = sb.toString();

	    positonList = oDaoManager.queryForList(query, new RowSetStrategy<HashMap<String, String>>() {

		@Override
		public HashMap<String, String> mapRow(ResultSet rs) throws SQLException {
		    HashMap<String, String> map = new HashMap<String, String>();
		    map.put("v_code", rs.getString("v_code"));
		    map.put("v_gubn", rs.getString("v_gubn"));
		    map.put("v_name", rs.getString("v_name"));
		    return map;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return positonList;
    }

    /**
     * 
     * 
     * @return
     * @메소드명 : getLegacyPositionInfo
     * @작성자 : 황정태
     * @작성일 : 2015. 9. 10.
     * @설명 : Legacy 사용자정보 조회
     * @수정이력
     * 
     */

    private List<HashMap<String, String>> getLegacyUserInfo() throws Exception {

	List<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer sb = new StringBuffer();

	    // sb.append( " select vd.empcode,                              	\n" );
	    // sb.append( " vd.empname,                                       	\n" );
	    // sb.append( " vd.companycode,                                  	\n" );
	    // sb.append( " xdbutl_acct.xdb_dec('normal',email) as email,      	\n" );
	    // sb.append( " vd.deptcode,                                        	\n" );
	    // sb.append( " vd.jobduty,      					\n" );
	    // sb.append( " vd.jobposition,  					\n" );
	    // sb.append( " vd.primaryyn,  				        \n" );
	    // sb.append( "'1' as COMPUTE_0009,  					\n" );
	    // sb.append( " decode(h21.site,'110','1000', 				\n" );
	    // sb.append( "                '120','4000', 				\n" );
	    // sb.append( "                '121','4000', 				\n" );
	    // sb.append( "                '130','2000', 				\n" );
	    // sb.append( "                '150','3000', 				\n" );
	    // sb.append( "                '170','8000', 				\n" );
	    // sb.append( "                '310','G100', 				\n" );
	    // sb.append( "                '320','G200', 				\n" );
	    // sb.append( "                '410','S100', 				\n" );
	    // sb.append( "                '420','S200', 				\n" );
	    // sb.append( "                '510','I100', 				\n" );
	    // sb.append( "                '610','C100', 				\n" );
	    // sb.append( "                '710','W100', 				\n" );
	    // sb.append( "                '810','K100', 				\n" );
	    // sb.append( "                '910','H100', 				\n" );
	    // sb.append( "                'N01','N100','9999'  			\n" );
	    // sb.append( " ) as site,   						\n" );
	    // sb.append( " substr(h21.res_no,1,6)  as passwd, 			\n" );
	    // sb.append( " case when vd.jobposition <= 'D20' then 'ST'            \n" );
	    // sb.append( " else nvl(h11.acc_group,'S3') end as accgroup           \n" );
	    // sb.append( " from VW_DP_USER_ALL VD, HPSNL21 H21,  HBASC11 H11											   \n" );
	    // sb.append( "where vd.empcode = h21.emp_no  														   \n" );
	    // sb.append( "  and vd.companycode = '1'  														   \n" );
	    // sb.append( " and     substr(vd.deptcode,1,1)    = h11.company_code 											   \n" );
	    // sb.append( " and     substr(vd.deptcode,2,4)    = substr(h11.dept_code,1,4)										   \n" );
	    // sb.append(
	    // " and	 vd.empcode not in ('20149026','20156019','20156020','20109998','20109997','20156018','20109995','20109996','20125010','20109999') \n"
	    // );
	    // sb.append( "order by vd.companycode, vd.deptcode, vd.jobduty, vd.jobposition, vd.empcode  								   \n" );

	    /*
	     * COMPANYCODE
	     * 
	     * 1 : 한국단자공업(주) 3 : 공영산업(주) 4 : 케이이티솔루션(주) 5 : (주)케.이.티.인터내쇼날 6 : 중국법인 7 : 경원산업 (사용 안함) 8 : 경원전자 9 : 협력업체 (사용 안함) E :
	     * 케이이티엔지니어링(주) H : (주)한빛누리 N : 케이티네트워크(주) P : 폴란드법인 V : 베트남법인 M : 멕시코법인 A : 미국법인 J : 일본법인
	     */

	    sb.append(" SELECT VD.EMPCODE,                                                                                                                       \n");
	    sb.append(" 	   VD.EMPNAME,                                                                                                                   \n");
	    sb.append(" 	   VD.COMPANYCODE,                                                                                                               \n");
	    sb.append(" 	   MAILID AS EMAIL,                                                                                                              \n");
	    sb.append(" 	   VD.DEPTCODE,                                                                                                                  \n");
	    sb.append(" 	   VD.JOBDUTY,                                                                                                                   \n");
	    sb.append(" 	   VD.JOBPOSITION,                                                                                                               \n");
	    sb.append(" 	   VD.PRIMARYYN,                                                                                                                     \n");
	    sb.append(" 	   '1' AS COMPUTE_0009,                                                                                                              \n");
	    sb.append(" 	   DECODE(SITE,'110','1000',                                                                                                         \n");
	    sb.append(" 		       '120','4000',                                                                                                         \n");
	    sb.append(" 		       '121','4000',                                                                                                         \n");
	    sb.append("                        '130','2000',                                                                                                         \n");
	    sb.append("                        '150','3000',                                                                                                         \n");
	    sb.append("                        '170','8000',                                                                                                         \n");
	    sb.append("                        '310','G100',                                                                                                         \n");
	    sb.append("                        '320','G200',                                                                                                         \n");
	    sb.append("                        '410','S100',                                                                                                         \n");
	    sb.append("                        '420','S200',                                                                                                         \n");
	    sb.append("                        '510','I100',                                                                                                         \n");
	    sb.append("                        '610','C100',                                                                                                         \n");
	    sb.append("                        '710','W100',                                                                                                         \n");
	    sb.append("                        '810','K100',                                                                                                         \n");
	    sb.append("                        '910','H100',                                                                                                         \n");
	    sb.append("                        'V01','V100',                                                                                                         \n");
	    sb.append("                        'N01','N100',                                                                                         \n");
	    sb.append("                        'P01','P100',                                                                                                         \n");
	    sb.append("                        'M01','M100',                                                                                                         \n");
	    sb.append("                        'A01','A100',                                                                                                         \n");
	    sb.append("                        'J01','J100',                                                                                                         \n");
	    sb.append("                        '9999') AS SITE,                                                                                         \n");
	    sb.append("            PASSWD,                                                                                                                          \n");
	    sb.append("            CASE WHEN VD.JOBPOSITION <= 'D20' THEN 'ST'                                                                                      \n");
	    sb.append("            ELSE NVL(ACCGROUP,'S3') END AS ACCGROUP, OFCLEVELCODE \n");
	    sb.append("  FROM VW_DP_USER_ALL VD                                                                                                                  \n");
	    sb.append(" WHERE VD.COMPANYCODE IN ('1', '4', '6', 'N','V','P','M','A','J')                                                                                                              \n");
	    sb.append("   AND VD.EMPCODE NOT IN ('20149026','20156019','20156020','20109998','20109997','20156018','20109995','20109996','20125010','20109999')  \n");
	    sb.append(" ORDER BY VD.COMPANYCODE, VD.DEPTCODE, VD.JOBDUTY, VD.JOBPOSITION, VD.EMPCODE                                                             \n");

	    String query = sb.toString();

	    userList = oDaoManager.queryForList(query, new RowSetStrategy<HashMap<String, String>>() {

		@Override
		public HashMap<String, String> mapRow(ResultSet rs) throws SQLException {
		    HashMap<String, String> map = new HashMap<String, String>();
		    map.put("empcode", rs.getString("empcode"));
		    map.put("empname", rs.getString("empname"));
		    map.put("companycode", rs.getString("companycode"));
		    map.put("email", rs.getString("email"));
		    map.put("deptcode", rs.getString("deptcode"));
		    map.put("jobduty", rs.getString("jobduty"));
		    map.put("jobposition", rs.getString("jobposition"));
		    map.put("primaryyn", rs.getString("primaryyn"));
		    map.put("COMPUTE_0009", rs.getString("COMPUTE_0009"));
		    map.put("site", rs.getString("site"));
		    map.put("passwd", rs.getString("passwd"));
		    map.put("accgroup", rs.getString("accgroup"));
		    map.put("ofclevelcode", rs.getString("ofclevelcode"));

		    return map;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return userList;
    }

    static public String getString(String str) {
	if (str == null) {
	    return "";
	} else if (str.equals("null")) {
	    return "";
	} else {
	    return str;
	}
    }

    private void qmsDeptUpdate(Connection con, PreparedStatement pst, DaoManager oDaoManager, List<HashMap<String, String>> deptList) throws Exception {
	pst = null;
	List aBind = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	String sSql = "";

	// qms 부서정보 Delete Start
	buffer.append("delete from TBL_INF_COM_DEPT");

	sSql = buffer.toString();
	oDaoManager.update2(sSql, aBind, con, pst);
	// qms 부서정보 Delete End

	buffer = null;
	buffer = new StringBuffer();

	// qms 부서정보 Insert Start
	buffer.append(" insert into TBL_INF_COM_DEPT ( \n");
	buffer.append(" DEPTCODE, PARENTDEPT, COMPANYCODE, DEPTNAME, DEPTORDER, USE_YN )   \n");
	buffer.append(" values(?, ?, ?, ?, ?, ?) \n");

	sSql = buffer.toString();
	int legacySize = deptList.size();
	int qmsSize = 0;
	int cnt = 0;

	bw.newLine();
	bw.write("부서정보 Insert Start..");
	bw.newLine();

	for (HashMap<String, String> Map : deptList) {
	    cnt++;

	    bw.write("부서정보 deptcode " + cnt + "번째 : " + Map.get("deptcode"));
	    bw.newLine();
	    bw.write("부서정보 parentdept " + cnt + "번째 : " + Map.get("parentdept"));
	    bw.newLine();
	    bw.write("부서정보 companycode " + cnt + "번째 : " + Map.get("companycode"));
	    bw.newLine();
	    bw.write("부서정보 deptname " + cnt + "번째 : " + Map.get("deptname"));
	    bw.newLine();
	    bw.write("부서정보 deptorder " + cnt + "번째 : " + Map.get("deptorder"));
	    bw.newLine();
	    bw.write("부서정보 use_yn " + cnt + "번째 : " + Map.get("use_yn"));
	    bw.newLine();
	    bw.newLine();
	    aBind.add(Map.get("deptcode"));
	    aBind.add(Map.get("parentdept"));
	    aBind.add(Map.get("companycode"));
	    aBind.add(Map.get("deptname"));
	    aBind.add(Map.get("deptorder"));
	    aBind.add(Map.get("use_yn"));

	    qmsSize += oDaoManager.update2(sSql, aBind, con, pst);
	    aBind.clear();
	}

	if (legacySize != qmsSize) {
	    throw new Exception("[부서정보 숫자 불일치]legacy 데이터 fetch 숫자와 실제 qms에 insert 된 숫자가 맞지 않습니다. legacy : " + legacySize + " 건, qms : " + qmsSize + " 건 \r\n\r\n");
	}

	bw.newLine();
	bw.write("::::::::::::::::::: " + getLogDate() + " 부서정보 " + qmsSize + " 건 데이터 이관 완료 :::::::::::::::::::::::::::");
	bw.newLine();

	// qms 부서정보 Insert End
    }

    private void qmsPositionUpdate(Connection con, PreparedStatement pst, DaoManager oDaoManager, List<HashMap<String, String>> positonList) throws Exception {
	pst = null;
	List aBind = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	String sSql = "";

	// qms 직급/직책 정보 Delete Start
	buffer.append("delete from TBL_INF_COM_DEPT_JOB");

	sSql = buffer.toString();
	oDaoManager.update2(sSql, aBind, con, pst);
	// qms 직급/직책 Delete End

	buffer = null;
	buffer = new StringBuffer();

	// qms 직급/직책 Insert Start
	buffer.append(" insert into TBL_INF_COM_DEPT_JOB ( \n");
	buffer.append(" V_CODE, V_GUBUN, V_NAME )   \n");
	buffer.append(" values(?, ?, ?) \n");

	sSql = buffer.toString();

	int legacySize = positonList.size();
	int qmsSize = 0;
	int cnt = 0;

	bw.newLine();
	bw.write("직급/직책 Insert Start..");
	bw.newLine();

	for (HashMap<String, String> Map : positonList) {
	    cnt++;

	    bw.write("직급/직책 v_code " + cnt + "번째 : " + Map.get("v_code"));
	    bw.newLine();
	    bw.write("직급/직책 v_gubn " + cnt + "번째 : " + Map.get("v_gubn"));
	    bw.newLine();
	    bw.write("직급/직책 v_name " + cnt + "번째 : " + Map.get("v_name"));
	    bw.newLine();
	    bw.newLine();
	    aBind.add(Map.get("v_code"));
	    aBind.add(Map.get("v_gubn"));
	    aBind.add(Map.get("v_name"));

	    qmsSize += oDaoManager.update2(sSql, aBind, con, pst);
	    aBind.clear();
	}

	if (legacySize != qmsSize) {
	    throw new Exception("[직급/직책 숫자 불일치]legacy 데이터 fetch 숫자와 실제 qms에 insert 된 숫자가 맞지 않습니다. legacy : " + legacySize + " 건, qms : " + qmsSize + " 건 \r\n\r\n");
	}

	bw.newLine();
	bw.write("::::::::::::::::::: " + getLogDate() + " 직급/직책 정보 " + qmsSize + " 건 데이터 이관 완료 :::::::::::::::::::::::::::");
	bw.newLine();
	// qms 직급/직책 Insert End
    }

    private void qmsUserUpdate(Connection con, PreparedStatement pst, DaoManager oDaoManager, List<HashMap<String, String>> userList) throws Exception {
	pst = null;
	List aBind = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	String sSql = "";

	// qms 사용자정보 정보 Delete Start
	buffer.append("delete from TBL_INF_COM_MEMBERS");

	sSql = buffer.toString();
	oDaoManager.update2(sSql, aBind, con, pst);
	// qms 사용자정보 Delete End

	buffer = null;
	buffer = new StringBuffer();

	// qms 사용자정보 Insert Start
	buffer.append(" insert into TBL_INF_COM_MEMBERS ( \n");
	buffer.append(" EMPCODE, EMPNAME, COMPANYCODE, EMAIL, DEPTCODE, JOBDUTY, JOBPOSITION, PRIMARYYN, COMPUTE_0009, COMPUTE_0010, PASSWD, ACCGROUP, JOBGRADE )   \n");
	buffer.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) \n");

	sSql = buffer.toString();
	int legacySize = userList.size();
	int qmsSize = 0;
	int cnt = 0;

	bw.newLine();
	bw.write("사용자정보 Insert Start..");
	bw.newLine();

	for (HashMap<String, String> Map : userList) {
	    cnt++;
	    bw.write("사용자정보 empcode " + cnt + "번째 : " + Map.get("empcode"));
	    bw.newLine();
	    bw.write("사용자정보 empname " + cnt + "번째 : " + Map.get("empname"));
	    bw.newLine();
	    bw.write("사용자정보 companycode " + cnt + "번째 : " + Map.get("companycode"));
	    bw.newLine();
	    bw.write("사용자정보 email " + cnt + "번째 : " + Map.get("email"));
	    bw.newLine();
	    bw.write("사용자정보 deptcode " + cnt + "번째 : " + Map.get("deptcode"));
	    bw.newLine();
	    bw.write("사용자정보 jobduty " + cnt + "번째 : " + Map.get("jobduty"));
	    bw.newLine();
	    bw.write("사용자정보 jobposition " + cnt + "번째 : " + Map.get("jobposition"));
	    bw.newLine();
	    bw.write("사용자정보 primaryyn " + cnt + "번째 : " + Map.get("primaryyn"));
	    bw.newLine();
	    bw.write("사용자정보 COMPUTE_0009 " + cnt + "번째 : " + Map.get("COMPUTE_0009"));
	    bw.newLine();
	    bw.write("사용자정보 site " + cnt + "번째 : " + Map.get("site"));
	    bw.newLine();
	    bw.write("사용자정보 passwd " + cnt + "번째 : " + Map.get("passwd"));
	    bw.newLine();
	    bw.write("사용자정보 accgroup " + cnt + "번째 : " + Map.get("accgroup"));
	    bw.newLine();
	    bw.write("사용자정보 ofclevelcode " + cnt + "번째 : " + Map.get("ofclevelcode"));
	    bw.newLine();
	    bw.newLine();

	    aBind.add(Map.get("empcode"));
	    aBind.add(Map.get("empname"));
	    aBind.add(Map.get("companycode"));

	    aBind.add(Map.get("email"));
	    aBind.add(Map.get("deptcode"));
	    aBind.add(Map.get("jobduty"));
	    aBind.add(Map.get("jobposition"));
	    aBind.add(Map.get("primaryyn"));
	    aBind.add(Map.get("COMPUTE_0009"));
	    aBind.add(Map.get("site"));
	    aBind.add(Map.get("passwd"));
	    aBind.add(Map.get("accgroup"));
	    aBind.add(Map.get("ofclevelcode"));

	    qmsSize += oDaoManager.update2(sSql, aBind, con, pst);
	    aBind.clear();
	}

	if (legacySize != qmsSize) {
	    throw new Exception("[사용자정보 숫자 불일치]legacy 데이터 fetch 숫자와 실제 qms에 insert 된 숫자가 맞지 않습니다. legacy : " + legacySize + " 건, qms : " + qmsSize + " 건 \r\n\r\n");
	}

	bw.newLine();
	bw.write("::::::::::::::::::: " + getLogDate() + " 사용자 정보 " + qmsSize + " 건 데이터 이관 완료 :::::::::::::::::::::::::::");
	bw.newLine();
	// qms 사용자정보 Insert End
    }

    private void updateQms(List<HashMap<String, String>> deptList, List<HashMap<String, String>> positonList, List<HashMap<String, String>> userList) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	Connection con = null;
	PreparedStatement pst = null;
	try {
	    con = DBUtil.getConnection(false, "qms");

	    qmsDeptUpdate(con, pst, oDaoManager, deptList);
	    qmsPositionUpdate(con, pst, oDaoManager, positonList);
	    qmsUserUpdate(con, pst, oDaoManager, userList);

	    con.commit();
	} catch (Exception e) {
	    con.rollback();
	    e.printStackTrace();
	    throw e;
	} finally {
	    DBUtil.close(con);
	    DBUtil.close(pst);
	    System.out.println("Connection Close Complete..");
	}
    }

    private void updateItms(List<HashMap<String, String>> deptList) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	Connection con = null;
	PreparedStatement pst = null;
	try {
	    con = DBUtil.getConnection(false, "qms");

	    itms3LevelDeptUpdate(con, pst, oDaoManager, deptList);

	    con.commit();
	} catch (Exception e) {
	    con.rollback();
	    e.printStackTrace();
	    throw e;
	} finally {
	    DBUtil.close(con);
	    DBUtil.close(pst);
	    System.out.println("Connection Close Complete..");
	}
    }

    /**
     * 
     * 
     * @return
     * @메소드명 : getLegacyItmsDept3LevelInfo
     * @작성자 : 황정태
     * @작성일 : 2017. 01. 26.
     * @설명 : Legacy 3레벨 조직 정보 조회 (ITMS용)
     * @수정이력
     * 
     */

    private List<HashMap<String, String>> getLegacyItmsDept3LevelInfo() throws Exception {

	List<HashMap<String, String>> deptList = new ArrayList<HashMap<String, String>>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append(" select ONE_LEVEL_DEPTCODE,  \n");
	    sb.append("        ONE_LEVEL_DEPTNAME, ");
	    sb.append("        TWO_LEVEL_DEPTCODE, ");
	    sb.append("        TWO_LEVEL_DEPTNAME, ");
	    sb.append("        THREE_LEVEL_DEPTCODE, ");
	    sb.append("        THREE_LEVEL_DEPTNAME ");
	    sb.append("   from VW_ITMS_DEPT ");

	    String query = sb.toString();

	    deptList = oDaoManager.queryForList(query, new RowSetStrategy<HashMap<String, String>>() {

		@Override
		public HashMap<String, String> mapRow(ResultSet rs) throws SQLException {
		    HashMap<String, String> map = new HashMap<String, String>();
		    map.put("ONE_LEVEL_DEPTCODE", rs.getString("ONE_LEVEL_DEPTCODE"));
		    map.put("ONE_LEVEL_DEPTNAME", rs.getString("ONE_LEVEL_DEPTNAME"));
		    map.put("TWO_LEVEL_DEPTCODE", rs.getString("TWO_LEVEL_DEPTCODE"));
		    map.put("TWO_LEVEL_DEPTNAME", rs.getString("TWO_LEVEL_DEPTNAME"));
		    map.put("THREE_LEVEL_DEPTCODE", rs.getString("THREE_LEVEL_DEPTCODE"));
		    map.put("THREE_LEVEL_DEPTNAME", rs.getString("THREE_LEVEL_DEPTNAME"));
		    return map;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return deptList;
    }

    private void itms3LevelDeptUpdate(Connection con, PreparedStatement pst, DaoManager oDaoManager, List<HashMap<String, String>> deptList) throws Exception {
	pst = null;
	List aBind = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	String sSql = "";

	// qms 부서정보 Delete Start
	buffer.append("delete from TBL_INF_COM_DEPT_LEVEL");

	sSql = buffer.toString();
	oDaoManager.update2(sSql, aBind, con, pst);
	// qms 부서정보 Delete End

	buffer = null;
	buffer = new StringBuffer();

	// qms 부서정보 Insert Start
	buffer.append(" insert into TBL_INF_COM_DEPT_LEVEL ( \n");
	buffer.append(" ONE_LEVEL_DEPTCODE, ONE_LEVEL_DEPTNAME, TWO_LEVEL_DEPTCODE, TWO_LEVEL_DEPTNAME, THREE_LEVEL_DEPTCODE, THREE_LEVEL_DEPTNAME )   \n");
	buffer.append(" values(?, ?, ?, ?, ?, ?) \n");

	sSql = buffer.toString();
	int legacySize = deptList.size();
	int qmsSize = 0;
	int cnt = 0;

	bw.newLine();
	bw.write("부서정보 Insert Start..");
	bw.newLine();

	for (HashMap<String, String> Map : deptList) {
	    cnt++;

	    bw.write("부서정보 ONE_LEVEL_DEPTCODE " + cnt + "번째 : " + Map.get("ONE_LEVEL_DEPTCODE"));
	    bw.newLine();
	    bw.write("부서정보 ONE_LEVEL_DEPTNAME " + cnt + "번째 : " + Map.get("ONE_LEVEL_DEPTNAME"));
	    bw.newLine();
	    bw.write("부서정보 TWO_LEVEL_DEPTCODE " + cnt + "번째 : " + Map.get("TWO_LEVEL_DEPTCODE"));
	    bw.newLine();
	    bw.write("부서정보 TWO_LEVEL_DEPTNAME " + cnt + "번째 : " + Map.get("TWO_LEVEL_DEPTNAME"));
	    bw.newLine();
	    bw.write("부서정보 THREE_LEVEL_DEPTCODE " + cnt + "번째 : " + Map.get("THREE_LEVEL_DEPTCODE"));
	    bw.newLine();
	    bw.write("부서정보 THREE_LEVEL_DEPTNAME " + cnt + "번째 : " + Map.get("THREE_LEVEL_DEPTNAME"));
	    bw.newLine();
	    bw.newLine();
	    aBind.add(Map.get("ONE_LEVEL_DEPTCODE"));
	    aBind.add(Map.get("ONE_LEVEL_DEPTNAME"));
	    aBind.add(Map.get("TWO_LEVEL_DEPTCODE"));
	    aBind.add(Map.get("TWO_LEVEL_DEPTNAME"));
	    aBind.add(Map.get("THREE_LEVEL_DEPTCODE"));
	    aBind.add(Map.get("THREE_LEVEL_DEPTNAME"));

	    qmsSize += oDaoManager.update2(sSql, aBind, con, pst);
	    aBind.clear();
	}

	if (legacySize != qmsSize) {
	    throw new Exception("[부서정보 숫자 불일치]legacy 데이터 fetch 숫자와 실제 itms에 insert 된 숫자가 맞지 않습니다. legacy : " + legacySize + " 건, itms : " + qmsSize + " 건 \r\n\r\n");
	}

	bw.newLine();
	bw.write("::::::::::::::::::: " + getLogDate() + " 부서정보 " + qmsSize + " 건 데이터 이관 완료 :::::::::::::::::::::::::::");
	bw.newLine();

	// qms 부서정보 Insert End
    }

    public static String getLogDate() {
	String logDate = getCurrentDateString(new SimpleDateFormat("yyyy-MM-dd")) + " " + getCurrentDateString(new SimpleDateFormat("HH:mm:ss"));
	return logDate;
    }

    public static String getCurrentDateString(SimpleDateFormat dateFormat) {
	Date currentDate = new Date();
	return dateFormat.format(currentDate);
    }

    public static String getStackTrace(Throwable t) {
	if (t == null)
	    return "";
	try {
	    ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    t.printStackTrace(new PrintStream(bout));
	    bout.flush();
	    String error = new String(bout.toByteArray());
	    return error;
	} catch (Exception ex) {
	    return "";
	}
    }

    private static void ErrorWrite(Throwable t, String point) throws Exception {
	ErrMsg += getStackTrace(t);
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORR");
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORR");
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERROR                                                                            ERRORERRORERROERRORERRORERRORR");
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERROR                                                                            ERRORERRORERROERRORERRORERRORR");
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERROR                                                                            ERRORERRORERROERRORERRORERRORR");
	bw.newLine();
	bw.newLine();
	bw.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ " + point + " 오류가 발생했습니다!  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
	bw.newLine();
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERROR                                                                            ERRORERRORERROERRORERRORERRORR");
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERROR                                                                            ERRORERRORERROERRORERRORERRORR");
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERROR                                                                            ERRORERRORERROERRORERRORERRORR");
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORR");
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORR");
	bw.newLine();
	bw.write("ERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORR");
	bw.newLine();
	bw.newLine();
	bw.write(ErrMsg);

	bw.newLine();
	mailSend();
    }

    private static void mailSend() throws UnsupportedEncodingException {
	String html = "";
	String fromEmail = "admin@ket.com";
	String toEmail = "musicvirus;shingyuho";
	String subject = "QMS 인사정보 인터페이스 실패 알림";

	html = "<html> ";
	html = html + "<head>";
	html = html + "<title></title>";
	html = html + "</head>";
	html = html + "<body bgcolor=white>";
	html = html + "<table width=610 border=0 cellpadding=0 cellspacing=1><tr>";
	html = html + "<tr><td height=23><font size=2 color=red>QMS 인터페이스 중 오류가 발생했습니다.</font></td></tr>";
	html = html + "<tr><td height=10>&nbsp;</td></tr>";
	html = html + "<tr><td height=23><font size=2 color=blue>보다 정확한 내용을 확인하려면 Legacy 서버에서 로그파일을 열람하십시오. </font></td></tr>";
	html = html + "<tr><td height=10>&nbsp;</td></tr>";
	html = html + "<tr><td height=23><font size=2 color=blue>인터페이스 재시도를 하려면 Legacy 서버에서 배치파일 실행 또는 스케줄러를 구동하십시오. </font></td></tr>";
	html = html + "<tr><td height=10>&nbsp;</td></tr>";
	html = html + "<tr><td height=23><font size=2> 로그 파일 위치 : " + strDirPath + "\\" + strFileName + "</font></td></tr>";
	html = html + "<tr><td height=10>&nbsp;</td></tr>";
	html = html + "<tr><td height=10>&nbsp;</td></tr>";
	html = html + "<tr><td height=23><font size=2 color=red><strong>■ Exception capture </strong></font><br>";
	html = html + "<tr><td height=10>&nbsp;</td></tr>";
	html = html + "<tr><td>" + ErrMsg + "</td></tr>";
	html = html + "</table></body></html> ";

	String[] parmeter = toEmail.split(";");

	for (int i = 0; i < parmeter.length; i++) {
	    send(fromEmail, parmeter[i], subject, html);
	}

    }

    private static void send(String from, String email_to, String subject, String msg) throws UnsupportedEncodingException {
	Properties properties = new Properties();
	properties.put("mail.smtp.host", "mail.ket.com");
	Session sess = Session.getDefaultInstance(properties, null);
	Message message = new MimeMessage(sess);
	try {

	    InternetAddress sender = new InternetAddress(from, "관리자", "utf-8");
	    message.setFrom(sender);

	    /* 여러명에게 보내기 */
	    String[] parmeter = email_to.split(";");

	    InternetAddress[] address = new InternetAddress[parmeter.length];
	    for (int i = 0; i < parmeter.length; i++) {
		address[i] = new InternetAddress(parmeter[i] + "@ket.com");
		System.out.println("to===>" + parmeter[i]);
	    }
	    message.setRecipients(Message.RecipientType.TO, address); // 수령인

	    // 한명에게 보내기
	    // message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email_to, false));

	    message.setSubject(subject); // 제목
	    message.setSentDate(new Date()); // 보낸 날짜
	    // message.setText(msg); // 글을 문자만 보낼 경우
	    // 글을 HTML 형식으로 보낼 경우
	    message.setContent(msg, "text/html; charset=EUC-KR");
	    message.setHeader("Content-Transfer-Encoding", "quoted-printable");

	    Transport.send(message);
	    System.out.println("E-mail successfully sent!");
	} catch (AddressException e) {
	    e.printStackTrace();
	    System.out.println("AddressException : " + e);
	} catch (MessagingException e) {
	    e.printStackTrace();
	    System.out.println("MessagingException : " + e);
	}

    }
}
