/**
 * @(#) CommonUtil.java Copyright (c) jerred. All rights reserverd
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2004. 3. 3.
 * @author Cho Sung Ok, jerred@bcline.com
 * @desc
 */

package e3ps.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletRequest;

import org.codehaus.jackson.map.ObjectMapper;

import com.symantec.itools.util.Properties;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.LoggableStatement;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.WTUserPeopleLink;
import ext.ket.shared.log.Kogger;
import wt.fc.IconDelegate;
import wt.fc.IconDelegateFactory;
import wt.fc.ObjectIdentifier;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.fc.WTReference;
import wt.inf.container.WTContainerHelper;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.IconSelector;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.vc.VersionForeignKey;
import wt.vc.VersionReference;
import wt.vc.Versioned;

public class CommonUtil {
	private static ReferenceFactory rf = null;

	/**
	 * 객체 생성을 방지하기 위해서 디폴트 생성자를 Private로 선언
	 */
	private CommonUtil() {
	}

	/**
	 * 파라미터로 들어온 Persistable 객체의 OID 를 리턴하는 Method <br>
	 */
	public static String getOIDString(Persistable per) {
		if (per == null)
			return null;
		return PersistenceHelper.getObjectIdentifier(per).getStringValue();
	}

	public static String getFullOIDString(Persistable persistable) {
		try {
			if (rf == null)
				rf = new ReferenceFactory();
			return rf.getReferenceString(rf.getReference(persistable));
		} catch (Exception e) {
			return null;
		}
	}

	public static long getOIDLongValue(String oid) {
		String tempoid = oid;
		tempoid = tempoid.substring(tempoid.lastIndexOf(":") + 1);
		return Long.parseLong(tempoid);
	}

	public static long getOIDLongValue(Persistable per) {
		String tempoid = getOIDString(per);
		tempoid = tempoid.substring(tempoid.lastIndexOf(":") + 1);
		return Long.parseLong(tempoid);
	}

	public static String getOIDLongValue2Str(String oid) {
		String tempoid = oid;
		tempoid = tempoid.substring(tempoid.lastIndexOf(":") + 1);
		return tempoid;
	}

	public static String getOIDLongValue2Str(Persistable per) {
		String tempoid = getOIDString(per);
		tempoid = tempoid.substring(tempoid.lastIndexOf(":") + 1);
		return tempoid;
	}

	/**
	 * VR oid를 리턴한다.
	 * 
	 * @param oid
	 * @return
	 */
	public static String getVROID(String oid) {
		Object obj = getObject(oid);
		if (obj == null)
			return null;
		return getVROID((Persistable) getObject(oid));
	}

	private static String getVRString(WTReference wtRef) throws WTException {
		VersionReference verRef = (VersionReference) wtRef;
		VersionForeignKey verForeignKey = (VersionForeignKey) verRef.getKey();
		return "VR:" + verRef.getKey().getClassname() + ":" + verForeignKey.getBranchId();
	}

	/**
	 * @param per
	 * @return
	 * @메소드명 : getRefOID
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 3.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	public static String getRefOID(Persistable per) {
		if (per instanceof Versioned) {
			return getVROID(per);
		} else {
			return getFullOIDString(per);
		}
	}

	/**
	 * VR oid를 리턴한다.
	 * 
	 * @param persistable
	 * @return
	 */
	public static String getVROID(Persistable persistable) {

		if (persistable == null)
			return null;
		try {
			if (rf == null)
				rf = new ReferenceFactory();
			return getVRString(rf.getReference(persistable));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * OID로 객체를 찾아 리턴 한다 <br>
	 */
	public static Persistable getObject(String oid) {
		if (oid == null || oid.equals(""))
			return null;
		try {
			if (rf == null)
				rf = new ReferenceFactory();
			return rf.getReference(oid).getObject();
		} catch (Exception e) {
			Kogger.error(CommonUtil.class, e);
			return null;
		}
	}

	/**
	 * 접속한 계정이 Admin 그룹에 포함 되어 있는지를 알아낸다 <br>
	 */
	public static boolean isAdmin() throws Exception {
		return isMember("Administrators");
	}

	/**
	 * <br>
	 * 현재 로그인한 사용자가 Biz Admin인지 체크하는 Method <br>
	 * 
	 * @return boolean
	 * @exception wt.util.WTException
	 **/
	public static boolean isBizAdmin() throws Exception {
		return isMember("Business Administrators");
	}

	/**
	 * 접속한 계정이 Parameter로 넘어온 group 명의 그룹에 포함 되어 있는지를 알아낸다 <br>
	 */
	public static boolean isMember(String group) throws Exception {
		WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
		return isMember(group, user);
	}

	public static boolean isMember(String group, WTUser user) throws Exception {
		Enumeration en = user.parentGroupNames();
		while (en.hasMoreElements()) {
			String st = (String) en.nextElement();
			if (st.equals(group))
				return true;
		}
		return false;
	}

	/**
	 * 세션 사용자의 사업부 구분을 NumberCode로 리턴
	 * 
	 * @return
	 * @throws Exception
	 * @메소드명 : getDivisionNumberCodeFromSessionUser
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 10. 7.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	public static NumberCode getDivisionNumberCodeFromSessionUser() throws Exception {

		NumberCode divisionFlagCode = null;
		if (isMember("자동차사업부")) {
			divisionFlagCode = NumberCodeHelper.manager.getNumberCode("DIVISIONFLAG", "C");
		} else if (isMember("전자사업부")) {
			divisionFlagCode = NumberCodeHelper.manager.getNumberCode("DIVISIONFLAG", "E");
		} else {
			// 설마 else는 없겠지.....
		}
		return divisionFlagCode;
	}

	/**
	 * 세션 사용자의 사업부 구분 NumberCode를 OID로 리턴
	 * 
	 * @return
	 * @throws Exception
	 * @메소드명 : getDivisionNumberCodeOidFromSessionUser
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 10. 7.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	public static String getDivisionNumberCodeOidFromSessionUser() throws Exception {
		return getOIDString(getDivisionNumberCodeFromSessionUser());
	}

	/**
	 * 세션 사용자의 사업부 구분 NumberCode를 code로 리턴
	 * 
	 * @return
	 * @throws Exception
	 * @메소드명 : getDivisionNumberCodeValueFromSessionUser
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 10. 7.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	public static String getDivisionNumberCodeValueFromSessionUser() throws Exception {
		return getDivisionNumberCodeFromSessionUser().getCode();
	}

	/**
	 * 시스템이 사용하는 임시 디렉토리의 절대 경로를 리턴하는 Method <br>
	 * 
	 * @return <code>java.lang.String</code> 시스템 임시 디렉토리
	 */
	public static String getTempDir() {
		Properties getpropertyaction = new Properties();
		// String tmpdir = (String) AccessController.doPrivileged ( getpropertyaction );
		return getpropertyaction.getProperty("java.io.tmpdir");
	}

	public static String getWCTempDir() {
		String tmpdir = "";
		try {
			WTProperties properties = WTProperties.getLocalProperties();
			tmpdir = properties.getProperty("wt.temp");
		} catch (IOException e) {
			Kogger.error(CommonUtil.class, e);
		}
		return tmpdir;
	}

	/**
	 * @param : String
	 * @return : String
	 * @author : PTC KOREA Yang Kyu
	 * @since : 2004.01
	 */
	public static WTUser findUserID(String userID) throws WTException {
		WTUser wtuser = (WTUser) OrganizationServicesHelper.manager.getAuthenticatedUser(userID);
		return wtuser;
	}

	public static String getUserIDFromSession() throws WTException {
		return ((WTUser) SessionHelper.manager.getPrincipal()).getName();
	}

	public static String getUserNameFromOid(String ida2a2) throws WTException {
		String oid = "wt.org.WTUser:" + ida2a2;
		WTUser wtuser = (WTUser) getObject(oid);

		if (wtuser != null) {
			return findUserName(wtuser.getName());
		} else {

			return "";
		}

	}

	public static String getUsernameFromSession() throws WTException {
		return ((WTUser) SessionHelper.manager.getPrincipal()).getFullName();
	}

	public static String findUserName(String userID) throws WTException {
		String userName = "";
		WTUser wtuser = (WTUser) OrganizationServicesHelper.manager.getAuthenticatedUser(userID);
		userName = wtuser.getFullName();
		return userName;
	}

	public static TreeMap getUsers() throws WTException {
		QuerySpec query = new QuerySpec(WTUser.class);
		query.appendWhere(new SearchCondition(WTUser.class, "disabled", "FALSE"));

		QueryResult result = PersistenceHelper.manager.find(query);

		TreeMap userTree = new TreeMap();
		while (result.hasMoreElements()) {
			wt.org.WTUser wtuser = (wt.org.WTUser) result.nextElement();
			userTree.put(wtuser.getFullName(), wtuser.getName());
		}
		return userTree;
	}

	public static Vector removeDuplicate(Vector duplicateVector) throws Exception {
		HashSet hashset = new HashSet();
		Vector vec1 = new Vector();

		for (int i = 0; i < duplicateVector.size(); i++) {
			Persistable persistable = (Persistable) duplicateVector.get(i);
			ObjectIdentifier objectidentifier = PersistenceHelper.getObjectIdentifier(persistable);

			if (!hashset.contains(objectidentifier)) {
				hashset.add(objectidentifier);
				vec1.addElement(persistable);
			}
		}
		return vec1;
	}

	public static String getIconResource(WTObject wtobject) throws Exception {
		String s = null;
		IconDelegateFactory icondelegatefactory = new IconDelegateFactory();
		IconDelegate icondelegate = icondelegatefactory.getIconDelegate(wtobject);
		IconSelector iconselector;
		for (iconselector = icondelegate.getStandardIconSelector(); !iconselector.isResourceKey(); iconselector = icondelegate.getStandardIconSelector())
			icondelegate = icondelegate.resolveSelector(iconselector);

		s = "/" + iconselector.getIconKey();
		return s;
	}

	public static void printlnParamValues(ServletRequest request) {

		Enumeration en = request.getParameterNames();
		Vector enVec = new Vector();
		while (en.hasMoreElements()) {
			enVec.addElement(en.nextElement());
		}
		Collections.sort(enVec, ORDER);
		en = enVec.elements();
		while (en.hasMoreElements()) {
			Object obj = en.nextElement();
			Kogger.debug(CommonUtil.class, obj.toString() + "    -    " + request.getParameter(obj.toString()));
		}
	}

	public static WTUser getWTUserFromID(String userID) {
		if (userID == null) {
			return null;
		}
		try {
			return (WTUser) OrganizationServicesHelper.manager.getPrincipal(userID, OrganizationServicesHelper.manager.getDefaultDirectoryService());
		} catch (WTException e) {
			Kogger.error(CommonUtil.class, e);
			return null;
		}
	}

	public static final Comparator ORDER = new Comparator() {
		public int compare(Object obj1, Object obj2) {
			int ret = (obj1.toString()).compareTo(obj2.toString());
			return ret;
		}
	};

	// 기존꺼 주석처리함
	// public static String ViewState(String state) {
	// if( "INWORK".equals(state) || "APPROVING".equals(state) || "승인중".equals(state) || "CHANGE".equals(state) ) {
	// return "작업중";
	// }else if( "DEVRELEASED".equals(state) ) {
	// return "견적승인";
	// }else if( "APPROVED".equals(state) || "승인됨".equals(state) || "RELEASED".equals(state) ) {
	// return "제작승인";
	// }else {
	// return state;
	// }
	// }

	public static String ViewState(String state) {
		if ("INWORK".equals(state)) {
			return "작업중";
		} else if ("UNDERREVIEW".equals(state)) {
			return "검토중";
		} else if ("APPROVED".equals(state)) {
			return "승인완료";
		} else if ("REJECTED".equals(state)) {
			return "반려됨";
		} else if ("REWORK".equals(state)) {
			return "재작업";
		} else {
			return state;
		}
	}

	public static String zeroFill(int value, int size) throws WTException {
		String convert = "";
		int maxSize = (int) Math.pow(10, size); // size=5 ??maxSize=100,000
		if (value >= maxSize) {
			convert = Integer.toString(maxSize - 1);
		} else {
			int seqnoSize = (Integer.toString(value)).length();
			for (int i = 0; i < (size - seqnoSize); i++) {
				convert += "0";
			}
			convert = convert + value;
		}// end if
		return convert;
	}

	public static String getWebAppName() {
		String webAppName = "";
		try {
			WTProperties wtproperties = WTProperties.getLocalProperties();
			webAppName = wtproperties.getProperty("wt.webapp.name");
		} catch (IOException e) {
			Kogger.error(CommonUtil.class, e);
			webAppName = "Windchill";
		}
		return webAppName;
	}

	/*
	 * [PLM System 1차개선] 수정내용 : 메일 다국어 적용(메일 수신자 NationalCode값을 이용해서 메일 템플릿 파일명 생성) 수정일자 : 2013. 7. 21 수정자 : 오명재
	 */
	public static String getMailTemplateName(String userId, String templateName) {

		String returnStr = "";
		String langCode = "";

		People people = null;
		WTUser wTUser = null;
		QueryResult qr = null;

		try {

			wTUser = OrganizationServicesHelper.manager.getAuthenticatedUser(userId);
			qr = PersistenceHelper.manager.navigate(wTUser, WTUserPeopleLink.PEOPLE_ROLE, WTUserPeopleLink.class);

			if (qr.hasMoreElements()) {

				people = (People) qr.nextElement();
				langCode = people != null ? KETStringUtil.nvl(people.getNationalCode(), "ko") : "ko";
				returnStr = templateName + "_" + langCode + ".html";
			} else {

				returnStr = templateName + "_ko.html";
			}
		} catch (QueryException e) {

			Kogger.error(CommonUtil.class, e);
		} catch (WTException e) {

			Kogger.error(CommonUtil.class, e);
		}

		return returnStr;
	}

	/**
	 * 세션 사용자의 oid를 long 타입으로 리턴한다.
	 * 
	 * @return
	 * @throws Exception
	 * @메소드명 : getOIDLongFromSessionUser
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 8. 14.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	public static long getOIDLongFromSessionUser() throws Exception {
		return CommonUtil.getOIDLongValue(SessionHelper.manager.getPrincipal());
	}

	/**
	 * 객체를 JSON으로 변환 ※ jackson.map.ObjectMapper 객체를 사용하므로 JsonProperty Annotation을 사용할 수 있는 장점이 있습니다.
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 * @메소드명 : toJsonString
	 * @작성자 : sw775.park
	 * @작성일 : 2014. 8. 8.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	public static String toJsonString(Object object) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Writer strWriter = new StringWriter();
		mapper.writeValue(strWriter, object);
		return strWriter.toString();
	}

	/**
	 * <p>
	 * 저장 로직 성공 여부에 따른 value 반환
	 * </p>
	 * ※ Windchill Spring에서 모든(?) exception을 어디선가 먹어버린다. 따라서, 에러 처리를 return해서 판단해야함.
	 * 
	 * @param isSuccess
	 * @param returnValue
	 * @return
	 * @메소드명 : returnData
	 * @작성자 : sw775.park
	 * @작성일 : 2014. 9. 5.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	public static Map<String, Object> returnData(boolean isSuccess) {
		return returnData(isSuccess, null);
	}

	/**
	 * <p>
	 * 저장 로직 성공 여부에 따른 value 반환
	 * </p>
	 * ※ Windchill Spring에서 모든(?) exception을 어디선가 먹어버린다. 따라서, 에러 처리를 return해서 판단해야함.
	 * 
	 * @param isSuccess
	 * @param returnValue
	 * @return
	 * @메소드명 : returnData
	 * @작성자 : sw775.park
	 * @작성일 : 2014. 9. 5.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	public static Map<String, Object> returnData(boolean isSuccess, Object returnObject) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("success", isSuccess ? "OK" : "FAIL");
		returnMap.put("value", returnObject);
		return returnMap;
	}

	/**
	 * 로그인 유저의 사업부명
	 * 
	 * @return
	 * @메소드명 : getUserDeptNameFromSession
	 * @작성자 : sw775.park
	 * @작성일 : 2014. 11. 14.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public static String getUserDeptNameFromSession() {
		String userDeptName = "";
		try {
			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
			PeopleData peoData = new PeopleData(user);
			userDeptName = peoData.departmentName;
		} catch (Exception e) {
			Kogger.error(CommonUtil.class, e);
		}
		return userDeptName;
	}
	
	/**
	 * <pre>
	 * @description 로그인 유저의 사업부 코드
	 * @author dhkim
	 * @date 2018. 5. 25. 오전 11:31:47
	 * @method getUserDeptCodeFromSession
	 * @return String
	 * </pre>
	 */
	public static String getUserDeptCodeFromSession() {
        String userDeptCode = "";
        try {
            WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
            PeopleData peoData = new PeopleData(user);
            userDeptCode = peoData.department.getCode();
        } catch (Exception e) {
            Kogger.error(CommonUtil.class, e);
        }
        return userDeptCode;
    }

	/**
	 * 로그인 유저 KETS 솔류션 사업부 유무
	 * 
	 * @return
	 * @throws Exception
	 * @메소드명 : isKETSUser
	 * @작성자 : sw775.park
	 * @작성일 : 2014. 12. 4.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public static boolean isKETSUser() throws Exception {
		return isMember("KETS") || isMember("KETS_PMO") || CommonUtil.isMember("KETS_금형설계") || CommonUtil.isMember("KETS_제품설계");
	}

	/**
	 * KETS 솔류션 사업부 유저 리스트 WHERE 조건절 리턴
	 * 
	 * @param compareStr
	 *            ex) D.IDA2A2
	 * @return
	 * @throws Exception
	 * @메소드명 : ketsUserListWhereStr
	 * @작성자 : KKW
	 * @작성일 : 2014. 12. 4.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public static String ketsUserListWhereStr(String compareStr) throws Exception {
		String rtnStr = "";
		if (isKETSUser()) {
			// 40490 케이이티솔루션㈜
			Department ketSDepartment = DepartmentHelper.manager.getDepartment("40490", true); // 케이이티솔루션㈜ department 가져옴

			ArrayList<Object> rtnArrList = new ArrayList<Object>();

			DepartmentHelper.manager.getAllChildList(ketSDepartment, rtnArrList);

			ArrayList<Object> departArrList = new ArrayList<Object>();

			for (int i = rtnArrList.size() - 1; i > -1; i--) {
				Department temp = (Department) rtnArrList.get(i);
				departArrList.add(CommonUtil.getOIDLongValue(temp));

			}

			KETParamMapUtil map = new KETParamMapUtil();

			map.put("deptOid", KETStringUtil.join(departArrList, ","));

			map.put("disable", "0");

			// 커넥션 생성
			Connection conn = null;
			conn = PlmDBUtil.getConnection();

			LoggableStatement pstmt = null;
			StringBuffer sb = null;
			ResultSet rs = null;

			// Map<String, Object> returnObj = new HashMap<String, Object>();
			// List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();

			try {

				sb = new StringBuffer();
				sb.append(" SELECT P.CLASSNAMEA2A2 || ':' || P.IDA2A2   AS PEOPLECLASSKEYOID,               \n");
				sb.append("        P.CLASSNAMEKEYB4 || ':' || P.IDA3B4  AS USERCLASSKEYOID,                 \n");
				sb.append("        P.IDA2A2                             AS PEOPLEOID,                       \n");
				sb.append("        P.IDA3B4                             AS USEROID                          \n");
				sb.append("   FROM PEOPLE     P                                                             \n");
				sb.append("       ,DEPARTMENT D                                                             \n");
				sb.append("  WHERE 1=1                                                                      \n");
				sb.append("    AND P.IDA3C4 = D.IDA2A2(+)                                                   \n");
				sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("D.IDA2A2", map.getString("deptOid"), false)).append("     \n");
				sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("P.ISDISABLE", map.getString("disable"), false)).append("     \n");

				pstmt = new LoggableStatement(conn, sb.toString());
				rs = pstmt.executeQuery();

				ArrayList<Object> userArrList = new ArrayList<Object>();

				while (rs.next()) {
					// 하위 부서 매핑 user set
					userArrList.add(rs.getString("USEROID"));
					// returnObj = new HashMap<String, Object>();
					// returnObj.put("peopleOid", rs.getString("PEOPLEOID"));
					// returnObj.put("userOid", rs.getString("USEROID"));
					// returnObjList.add(returnObj);
				}

				KETParamMapUtil userOidMap = new KETParamMapUtil();
				userOidMap.put("userOid", KETStringUtil.join(userArrList, ","));

				rtnStr = "    AND " + KETQueryUtil.getSqlQueryForMultiSearch(compareStr, userOidMap.getString("userOid"), false);

			} catch (SQLException se) {
				Kogger.error(CommonUtil.class, se);
			} catch (Exception e) {
				Kogger.error(CommonUtil.class, e);
			} finally {
				sb.delete(0, sb.length());
				PlmDBUtil.close(rs);
				PlmDBUtil.close(pstmt);
			}
		}

		return rtnStr;

	}

	/**
	 * KETS 솔류션 사업부 유저 리스트 WHERE 조건절 리턴 QuerySpec
	 * 
	 * @param query
	 * @param targetClass
	 * @param targetIndex
	 * @param attributeName
	 * @param ignoreCase
	 * @throws Exception
	 * @메소드명 : ketsUserListWhereStrQs
	 * @작성자 : KKW
	 * @작성일 : 2014. 12. 4.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public static void ketsUserListWhereStrQs(QuerySpec query, Class<?> targetClass, int targetIndex, String attributeName, boolean ignoreCase) throws Exception {
		if (isKETSUser()) {
			// 40490 케이이티솔루션㈜
			Department ketSDepartment = DepartmentHelper.manager.getDepartment("40490", true); // 케이이티솔루션㈜ department 가져옴

			ArrayList<Object> rtnArrList = new ArrayList<Object>();

			DepartmentHelper.manager.getAllChildList(ketSDepartment, rtnArrList);

			ArrayList<Object> departArrList = new ArrayList<Object>();

			for (int i = rtnArrList.size() - 1; i > -1; i--) {
				Department temp = (Department) rtnArrList.get(i);
				departArrList.add(CommonUtil.getOIDLongValue(temp));

			}

			KETParamMapUtil map = new KETParamMapUtil();

			map.put("deptOid", KETStringUtil.join(departArrList, ","));

			map.put("disable", "0");

			// 커넥션 생성
			Connection conn = null;
			conn = PlmDBUtil.getConnection();

			LoggableStatement pstmt = null;
			StringBuffer sb = null;
			ResultSet rs = null;

			// Map<String, Object> returnObj = new HashMap<String, Object>();
			// List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();

			try {

				sb = new StringBuffer();
				sb.append(" SELECT P.CLASSNAMEA2A2 || ':' || P.IDA2A2   AS PEOPLECLASSKEYOID,               \n");
				sb.append("        P.CLASSNAMEKEYB4 || ':' || P.IDA3B4  AS USERCLASSKEYOID,                 \n");
				sb.append("        P.IDA2A2                             AS PEOPLEOID,                       \n");
				sb.append("        P.IDA3B4                             AS USEROID                          \n");
				sb.append("   FROM PEOPLE     P                                                             \n");
				sb.append("       ,DEPARTMENT D                                                             \n");
				sb.append("  WHERE 1=1                                                                      \n");
				sb.append("    AND P.IDA3C4 = D.IDA2A2(+)                                                   \n");
				sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("D.IDA2A2", map.getString("deptOid"), false)).append("     \n");
				sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("P.ISDISABLE", map.getString("disable"), false)).append("     \n");

				pstmt = new LoggableStatement(conn, sb.toString());
				rs = pstmt.executeQuery();

				ArrayList<Object> userArrList = new ArrayList<Object>();

				while (rs.next()) {
					// 하위 부서 매핑 user set
					userArrList.add(rs.getString("USEROID"));
					// returnObj = new HashMap<String, Object>();
					// returnObj.put("peopleOid", rs.getString("PEOPLEOID"));
					// returnObj.put("userOid", rs.getString("USEROID"));
					// returnObjList.add(returnObj);
				}

				KETParamMapUtil userOidMap = new KETParamMapUtil();
				userOidMap.put("userOid", KETStringUtil.join(userArrList, ","));

				query.appendAnd();
				KETQueryUtil.setQuerySpecForMultiSearch(query, targetClass, targetIndex, attributeName, userOidMap.getString("userOid"), ignoreCase);

			} catch (SQLException se) {
				Kogger.error(CommonUtil.class, se);
			} catch (Exception e) {
				Kogger.error(CommonUtil.class, e);
			} finally {
				sb.delete(0, sb.length());
				PlmDBUtil.close(rs);
				PlmDBUtil.close(pstmt);
			}
		}

	}

	/**
	 * <br>
	 * 그룹명으로 사용자를 검색하는 Method<br>
	 * 
	 * @param String
	 * @param String
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public static ArrayList<Hashtable> findUser(String GroupName, String Key) throws WTException {
		Hashtable<String, String> multiData = null;
		ArrayList<Hashtable> list = new ArrayList<Hashtable>();

		if (GroupName != null && !"".equals(GroupName)) {
			WTGroup wtgroup = OrganizationServicesHelper.manager.getGroup(GroupName, WTContainerHelper.service.getExchangeContainer().getContextProvider());
			
			if(wtgroup != null) {
			    Enumeration members = OrganizationServicesHelper.manager.members(wtgroup);
	            int i = 0;
	            while (members.hasMoreElements()) {
	                WTPrincipal wtprincipal = (WTPrincipal) members.nextElement();
	                WTUser memberUser = (WTUser) wtprincipal;
	                multiData = new Hashtable<String, String>();
	                multiData.put(Key, memberUser.getName());
	                list.add(i, multiData);
	                i++;
	            }
			}
		}
		return list;
	}
	
	/**
	 * 접속한 계정이 원가관리 그룹에 포함 되어 있는지를 알아낸다 <br>
	 */
	public static boolean isCostAdmin() throws Exception {
		return isMember("원가관리");
	}
}
