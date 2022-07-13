/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : ProdEcoDao.java
 * 작성자 : 오승연
 * 작성일자 : 2010. 11. 29.
 */
package e3ps.ecm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import wt.epm.EPMDocument;
import wt.fc.Persistable;
import e3ps.bom.common.iba.IBAUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.edm.beans.EDMHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : ProdEcoDao 설명 : 작성자 : 오승연 작성일자 : 2010. 11. 29.
 */
public class ProdEcoDao {

    private Connection conn;
    private PreparedStatement pstmt;
    // private LoggableStatement pstmt;
    private ResultSet rs;

    public ProdEcoDao() {
	try {
	    this.conn = PlmDBUtil.getConnection();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public ProdEcoDao(Connection conn) {
	this.conn = conn;
    }

    public String getProdEcoId() throws Exception {
	String ecoId = "";

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT fn_get_ecm_numbering('ECO') FROM dual");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		ecoId = rs.getString(1);
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return ecoId;
    }

    public Hashtable<String, Object> getProdEcoInfo(String ida2a2) throws Exception {
	Hashtable<String, Object> prodEco = null;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT																																																	\n");
	sql.append("			 o.ecoid                                   																						eco_id														\n");
	sql.append("		   , o.classnamea2a2 || ':' || o.ida2a2   																						eco_oid														\n");
	sql.append("		   , o.divisionflag                           																						division_flag												\n");
	sql.append("	       , ( SELECT n.name 																																											\n");
	sql.append("	        	FROM numbercode n 																																									\n");
	sql.append("	          WHERE n.codetype = 'DIVISIONFLAG' 																																				\n");
	sql.append("	              AND n.code = o.divisionflag )    																					division_flag_name 										\n");
	sql.append("		   , o.devyn                                  																						dev_yn														\n");
	sql.append("	       , ( SELECT n.name 																																											\n");
	sql.append("	       		FROM numbercode n 																																									\n");
	sql.append("	       	   WHERE n.codetype = 'DEVYN' 																																						\n");
	sql.append("	       	   	   AND n.code = o.devyn )         																					dev_yn_name 												\n");
	sql.append("	       , o.econame                              																						eco_name 													\n");
	sql.append("	       , o.projectoid                             																						project_oid 												\n");
	sql.append("	       , ( SELECT e.pjtno																																												\n");
	sql.append("	       		FROM e3psprojectmaster e																																							\n");
	sql.append("	       		        , productProject p																																								\n");
	sql.append("	          WHERE p.ida2a2 = substr(o.projectoid, instr( o.projectoid, ':')+1, length(o.projectoid) )   																			\n");
	sql.append("	             AND p.ida3b8 = e.ida2a2  ) 																							project_no													\n");
	sql.append("	       , ( SELECT e.pjtname																																											\n");
	sql.append("	       		FROM e3psprojectmaster e																																							\n");
	sql.append("	       		        , productProject p																																								\n");
	sql.append("	          WHERE p.ida2a2 = substr(o.projectoid, instr( o.projectoid, ':')+1, length(o.projectoid) )   																			\n");
	sql.append("	             AND p.ida3b8 = e.ida2a2  ) 																							project_name												\n");
	sql.append("	       , o.deptname                             																						dept_name 												\n");
	sql.append("	       , ( SELECT p.name																																												\n");
	sql.append("	       		FROM wtuser u																																											\n");
	sql.append("	       				, people p																																											\n");
	sql.append("	       	  WHERE u.ida2a2 = o.ida3b7																																							\n");
	sql.append("	       	     AND p.ida3b4 = u.ida2a2 ) 																							creator_name												\n");
	sql.append("	       , to_char( o.createstampa2, 'YYYY-MM-DD' )  																		create_date													\n");
	sql.append("	       , to_char( o.updatestampa2, 'YYYY-MM-DD' )  																		update_date												\n");
	sql.append("	       , o.statestate         																											status														\n");
	sql.append("	       , ( SELECT max(pl.name)																																										\n");
	sql.append("	            FROM phasetemplate ph																																								\n");
	sql.append("	                    , phase pl																																											\n");
	sql.append("	           WHERE pl.ida3a4 =ph.ida2a2																																							\n");
	sql.append("	               AND ph.phaseState = o.stateState )  																			status_name												\n");
	sql.append("	        , decode( o.domesticyn, '1000', '국내', '2000', '국외', '')  															domestic_yn												\n");
	sql.append("	        , o.domesticyn  																												domestic_yn_code										\n");
	sql.append("	        , o.carmaker  																													car_maker													\n");
	sql.append("	        , ( SELECT n.name  																																											\n");
	sql.append("	       		 FROM numbercode n 																																									\n");
	sql.append("	       	    WHERE n.codetype = 'CUSTOMEREVENT' 																																		\n");
	sql.append("	       	       AND n.code = o.carmaker )    																					car_maker_name											\n");
	sql.append("	        , o.carcategory																													car_category												\n");
	sql.append("	        , ( SELECT ot.name																																											\n");
	sql.append("	        	 FROM oemprojecttype ot																																								\n");
	sql.append("	        	        ,  numbercode n																																									\n");
	sql.append("	           WHERE ot.oemtype = 'CARTYPE'																																						\n");
	sql.append("	               AND ot.ida3a4 = n.ida2a2																																							\n");
	sql.append("	               AND n.code = o.carmaker																																							\n");
	sql.append("	               AND ot.code = o.carcategory )    																				car_category_name										\n");
	sql.append("	        , o.changereason                     	 																					chg_reason													\n");
	sql.append("	        , o.otherchangereason               																						other_chg_reason_desc									\n");
	sql.append("	        , o.custormerflag               																								cust_chk														\n");
	sql.append("	        , o.othercustflagdesc                   																						other_cust_chk_desc										\n");
	sql.append("	        , o.changeflag                   																								chg_fact														\n");
	sql.append("	        , o.cudrawingchangeyn                   																					cu_chg_yn													\n");
	sql.append("	        , o.ecoApplyPoint                   																					ecoApplyPoint													\n");
	sql.append("	        , o.effectivedateflag                   																						effective_date_flag										\n");
	sql.append("	        , o.designguideyn                   																						design_guide_yn										\n");
	sql.append("	        , o.designchecksheetyn                   																						design_check_sheet_yn										\n");
	sql.append("	        , o.defectDivCode                   																						defectDivCode										\n");
	sql.append("	        , o.defectDivName                   																						defectDivName										\n");
	sql.append("	        , o.defectTypeCode                   																						defectTypeCode										\n");
	sql.append("	        , o.defectTypeName                   																						defectTypeName										\n");
	sql.append("	        , decode(o.costchangecode, 'DECREASE', '-', 'INCREASE', '+') || o.costvariationrate || decode(o.costvariationrate,'','','%') as costVariation                  																								\n");
	sql.append("	        , o.costchangecode   as costChangeCode                																																\n");
	sql.append("	        , o.costvariationrate   as costVariationValue                																																\n");
	sql.append("	        , o.pointYn                   																						pointYn										\n");
	sql.append("	        , o.specChangeYn                   																						specChangeYn										\n");
	sql.append(" , ( SELECT n.name                                                                                                                                                                           \n");
	sql.append("       FROM numbercode n                                                                                                                                                                   \n");
	sql.append("      WHERE n.codetype = 'COSTCHANGE'                                                                                                                                                \n");
	sql.append("        AND n.code = O.COSTCHANGECODE )                                                                                costChange  \n");
	sql.append("			, ( SELECT n.name																																											\n");
	sql.append("				 FROM numbercode n																																									\n");
	sql.append("				WHERE n.codetype = 'EFFECTIVEDATE' 																																			\n");
	sql.append("					AND n.code = o.effectivedateflag )																				effective_date_flag_name								\n");
	sql.append("	        , o.inventoryclear                   																							inv_clear														\n");
	sql.append("			, ( SELECT n.name																																											\n");
	sql.append("				 FROM numbercode n																																									\n");
	sql.append("				WHERE n.codetype = 'INVPROCESS' 																																				\n");
	sql.append("					AND n.code = o.inventoryclear )																				inv_clear_name											\n");
	sql.append("			, ( SELECT n.dieno																																											\n");
	sql.append("				 FROM KETMoldChangePlan n																																									\n");
	sql.append("				WHERE n.ida3b4 =o.ida2a2)                                                                              dieno 																				\n");
	sql.append("			, ( SELECT classnamea2a2||':'||ida2a2 as mold_oid																																											\n");
	sql.append("				 FROM KETMoldChangePlan n																																									\n");
	sql.append("				WHERE n.ida3b4 =o.ida2a2) 					                                                            mold_oid																				\n");

	sql.append("	FROM ketprodchangeorder o																																									\n");
	sql.append("WHERE o.ida2a2 = ?																																													\n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ida2a2);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		prodEco = new Hashtable<String, Object>();
		prodEco.put("eco_id", StringUtil.checkNull(rs.getString("eco_id")));
		prodEco.put("eco_oid", StringUtil.checkNull(rs.getString("eco_oid")));
		prodEco.put("division_flag", StringUtil.checkNull(rs.getString("division_flag")));
		prodEco.put("division_flag_name", StringUtil.checkNull(rs.getString("division_flag_name")));
		prodEco.put("dev_yn", StringUtil.checkNull(rs.getString("dev_yn")));
		prodEco.put("dev_yn_name", StringUtil.checkNull(rs.getString("dev_yn_name")));
		prodEco.put("eco_name", StringUtil.checkNull(rs.getString("eco_name")));
		prodEco.put("project_oid", StringUtil.checkNull(rs.getString("project_oid")));
		prodEco.put("project_no", StringUtil.checkNull(rs.getString("project_no")));
		prodEco.put("project_name", StringUtil.checkNull(rs.getString("project_name")));
		prodEco.put("dept_name", StringUtil.checkNull(rs.getString("dept_name")));
		prodEco.put("creator_name", StringUtil.checkNull(rs.getString("creator_name")));
		prodEco.put("create_date", StringUtil.checkNull(rs.getString("create_date")));
		prodEco.put("update_date", StringUtil.checkNull(rs.getString("update_date")));
		prodEco.put("status", StringUtil.checkNull(rs.getString("status")));
		prodEco.put("status_name", StringUtil.checkNull(rs.getString("status_name")));
		prodEco.put("domestic_yn", StringUtil.checkNull(rs.getString("domestic_yn")));
		prodEco.put("domestic_yn_code", StringUtil.checkNull(rs.getString("domestic_yn_code")));
		prodEco.put("car_maker", StringUtil.checkNull(rs.getString("car_maker")));
		prodEco.put("car_maker_name", StringUtil.checkNull(rs.getString("car_maker_name")));
		prodEco.put("car_category", StringUtil.checkNull(rs.getString("car_category")));
		prodEco.put("car_category_name", StringUtil.checkNull(rs.getString("car_category_name")));
		prodEco.put("chg_reason", StringUtil.checkNull(rs.getString("chg_reason")));
		prodEco.put("other_chg_reason_desc", StringUtil.checkNull(rs.getString("other_chg_reason_desc")));
		prodEco.put("cust_chk", StringUtil.checkNull(rs.getString("cust_chk")));
		prodEco.put("other_cust_chk_desc", StringUtil.checkNull(rs.getString("other_cust_chk_desc")));
		prodEco.put("chg_fact", StringUtil.checkNull(rs.getString("chg_fact")));
		prodEco.put("cu_chg_yn", StringUtil.checkNull(rs.getString("cu_chg_yn")));
		prodEco.put("ecoApplyPoint", StringUtil.checkNull(rs.getString("ecoApplyPoint")));
		prodEco.put("design_guide_yn", StringUtil.checkNull(rs.getString("design_guide_yn")));
		prodEco.put("design_check_sheet_yn", StringUtil.checkNull(rs.getString("design_check_sheet_yn")));
		prodEco.put("defectDivCode", StringUtil.checkNull(rs.getString("defectDivCode")));
		prodEco.put("defectDivName", StringUtil.checkNull(rs.getString("defectDivName")));
		prodEco.put("defectTypeCode", StringUtil.checkNull(rs.getString("defectTypeCode")));
		prodEco.put("defectTypeName", StringUtil.checkNull(rs.getString("defectTypeName")));
		prodEco.put("costVariation", StringUtil.checkNull(rs.getString("costVariation")));
		prodEco.put("costChange", StringUtil.checkNull(rs.getString("costChange")));
		prodEco.put("costChangeCode", StringUtil.checkNull(rs.getString("costChangeCode")));
		prodEco.put("costVariationValue", StringUtil.checkNull(rs.getString("costVariationValue")));
		prodEco.put("pointYn", StringUtil.checkNull(rs.getString("pointYn")));
		prodEco.put("specChangeYn", StringUtil.checkNull(rs.getString("specChangeYn")));
		prodEco.put("effective_date_flag", StringUtil.checkNull(rs.getString("effective_date_flag")));
		prodEco.put("effective_date_flag_name", StringUtil.checkNull(rs.getString("effective_date_flag_name")));
		prodEco.put("inv_clear", StringUtil.checkNull(rs.getString("inv_clear")));
		prodEco.put("inv_clear_name", StringUtil.checkNull(rs.getString("inv_clear_name")));
		prodEco.put("dieno", StringUtil.checkNull(rs.getString("dieno")));
		prodEco.put("mold_oid", StringUtil.checkNull(rs.getString("mold_oid")));
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return prodEco;
    }

    public ArrayList<Hashtable<String, String>> getRefEcrList(String ida2a2) throws Exception {
	ArrayList<Hashtable<String, String>> ecrList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> prodEcr = null;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT l.classnamea2a2||':'|| l.ida2a2 			link_oid  		\n");
	sql.append("		   , r.classnamea2a2||':'|| r.ida2a2 			oid  				\n");
	sql.append("		   , r.ecrid  											ecr_id				\n");
	sql.append("		   , r.ecrname  										ecr_name		\n");
	sql.append("		   , r.deptname 									dept_name    	\n");
	sql.append("		   , (SELECT p.name     												\n");
	sql.append("		       FROM wtuser u     											\n");
	sql.append("		       		   , people p        										\n");
	sql.append("		      WHERE u.ida2a2 = r.ida3b7        							\n");
	sql.append("		          AND p.ida3b4 = u.ida2a2) 			creator_name	\n");
	sql.append(" FROM ketprodchangelink l       										\n");
	sql.append("         , ketprodchangerequest r        								\n");
	sql.append("WHERE r.ida2a2 = l.ida3b5         										\n");
	sql.append("    AND l.ida3a5 = ?        												\n");
	sql.append("ORDER BY 3         														\n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ida2a2);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		prodEcr = new Hashtable<String, String>();

		prodEcr.put("link_oid", rs.getString("link_oid"));
		prodEcr.put("oid", rs.getString("oid"));
		prodEcr.put("ecr_id", rs.getString("ecr_id"));
		prodEcr.put("ecr_name", rs.getString("ecr_name"));
		prodEcr.put("dept_name", rs.getString("dept_name"));
		prodEcr.put("creator_name", rs.getString("creator_name"));

		ecrList.add(prodEcr);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return ecrList;
    }

    public ArrayList<Hashtable<String, String>> getProdEcaList(String ida2a2, String ecaType, String workerOid) throws Exception {
	ArrayList<Hashtable<String, String>> ecaList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> ecaHash = null;

	StringBuffer sql = new StringBuffer();

	if (ecaType.equals("도면") || ecaType.equals("도면/BOM")) {
	    sql.append("SELECT t1.activitytype activity_type				\n");
	    sql.append("		   , '' doc_type_desc				\n");
	    sql.append("		   , TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' ) create_date					\n");
	    sql.append("		   , '도면' activity_type_name		\n");
	    sql.append("		   , t2.epmdoctype obj_type						\n");

	    // 상세구분(내용)
	    sql.append("	        , t1.activitytypedesc AS activity_type_desc \n");

	    // 완료요청일
	    sql.append("	        , TO_CHAR( t1.completerequestdate, 'YYYY-MM-DD' ) AS complete_request_date \n");
	    // 수신확인일
	    sql.append("	        , TO_CHAR( t1.receiveconfirmeddate, 'YYYY-MM-DD' ) AS receive_confirmed_date \n");
	    // 내용(ToDo 에서 사용자 입력 정보)
	    sql.append("	        , t1.activitybackdesc AS activity_back_desc \n");

	    sql.append("		   , t1.classnamea2a2 ||':'|| t1.ida2a2 eca_oid    					\n");
	    sql.append("		   , t1.activitystatus activity_status        		\n");
	    sql.append("		   , t1.workerid worker_id                   \n");
	    sql.append("		   , ( SELECT st2.name \n");
	    sql.append("		   	    FROM wtuser st1 \n");
	    sql.append("		   	            , people st2 \n");
	    sql.append("		   	   WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	    sql.append("		   	  	   AND st2.ida3b4 = st1.ida2a2 ) worker_name          	\n");
	    sql.append("			, TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) complete_date        		\n");
	    sql.append("	        , t2.classnamea2a2 ||':'|| t2.ida2a2 eca_obj_link_oid			\n");
	    sql.append("	        , t4.classnamea2a2 ||':'|| t4.ida2a2 eca_obj_oid					\n");
	    sql.append("	        , t5.documentnumber eca_obj_no					\n");
	    sql.append("	        , t5.name eca_obj_name				\n");
	    sql.append("	        , t2.preversion eca_obj_pre_rev			\n");
	    sql.append("	        , t2.afterversion eca_obj_after_rev			\n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date		\n");
	    sql.append("	        , t2.activitycomment eca_obj_act_comment	\n");
	    sql.append("	        , t2.changeyn change_yn					\n");
	    sql.append("	        , t2.dieno die_no						\n");
	    sql.append("	        , t2.scheduleid schedule_id				\n");
	    sql.append("	        , (	SELECT st1.classnamea2a2 ||':'|| st1.ida2a2 \n");
	    sql.append("	        	 FROM ketmoldchangeplan st1 \n");
	    sql.append("	            WHERE st1.scheduleid = t2.scheduleid ) plan_oid						\n");
	    sql.append("	        , '' masschange_yn			\n");
	    sql.append("	        , '' related_part_list			\n");
	    sql.append("	        , '' before_part_oid			\n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) plan_date					\n");
	    sql.append("	        , '' eca_parent_item_no		\n");
	    sql.append("	        , '' eca_parent_item_name		\n");

	    sql.append(" FROM ketprodchangeactivity t1 \n");
	    sql.append("	       , ketprodecaepmdoclink t2 \n");
	    sql.append("	       , epmdocument t4	\n");
	    sql.append("	       , epmdocumentmaster t5 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("	  AND t1.ida3a8 = ? \n");
	    sql.append("	  AND t1.workerid = ? \n");
	    sql.append("	  AND t2.ida3b5 = t1.ida2a2 \n");
	    sql.append("	  AND t4.ida2a2 = t2.ida3a5 \n");
	    sql.append("	  AND t5.ida2a2 = t4.ida3masterreference \n");
	    sql.append("	  AND t4.latestiterationinfo = 1 \n");
	}

	if (ecaType.equals("도면/BOM")) {
	    sql.append("UNION ALL \n");
	}

	if (ecaType.equals("BOM") || ecaType.equals("도면/BOM")) {

	    // I. 초도일 경우
	    sql.append("SELECT t1.activitytype activity_type				\n");
	    sql.append("		   , '' doc_type_desc				\n");
	    sql.append("	       , TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' ) create_date					\n");
	    sql.append("	       , 'BOM' activity_type_name 		\n");
	    sql.append("	       , 'BOM' obj_type						\n");

	    // 상세구분(내용)
	    sql.append("	       , t1.activitytypedesc AS activity_type_desc \n");

	    // 완료요청일
	    sql.append("	       , TO_CHAR( t1.completerequestdate, 'YYYY-MM-DD' ) AS complete_request_date \n");
	    // 수신확인일
	    sql.append("	       , TO_CHAR( t1.receiveconfirmeddate, 'YYYY-MM-DD' ) AS receive_confirmed_date \n");
	    // 내용(ToDo 에서 사용자 입력 정보)
	    sql.append("	       , t1.activitybackdesc AS activity_back_desc \n");

	    sql.append("	       , t1.classnamea2a2||':'|| t1.ida2a2 eca_oid						\n");
	    sql.append("	       , t1.activitystatus activity_status				\n");
	    sql.append("	       , t1.workerid worker_id        			\n");
	    sql.append("	       , ( SELECT st2.name \n");
	    sql.append("	        	FROM wtuser st1 \n");
	    sql.append("	          			, people st2 \n");
	    sql.append("	           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	    sql.append("	        	   AND st2.ida3b4 = st1.ida2a2 ) worker_name				\n");
	    sql.append("	        , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) complete_date				\n");
	    sql.append("	        , t2.classnamea2a2 ||':'|| t2.ida2a2 eca_obj_link_oid			\n");
	    sql.append("	        , t3.classnamea2a2 ||':'|| t3.ida2a2 eca_obj_oid					\n");
	    sql.append("	        , t3.ecoitemcode eca_obj_no					\n");
	    sql.append("	        , t3.description eca_obj_name				\n");
	    sql.append("	        , t2.preversion eca_obj_pre_rev			\n");
	    sql.append("	        , t2.afterversion eca_obj_after_rev			\n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date		\n");
	    sql.append("	        , t2.activitycomment eca_obj_act_comment   \n");
	    sql.append("	        , t2.changeyn change_yn					\n");
	    sql.append("	        , '' die_no						\n");
	    sql.append("	        , '' schedule_id				\n");
	    sql.append("	        , '' plan_oid						\n");
	    sql.append("	        , t2.masschangeyn masschange_yn			\n");
	    sql.append("	        , t2.relatedparentpartlist related_part_list			\n");
	    sql.append("	        , t2.beforepartoid before_part_oid			\n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) plan_date					\n");
	    sql.append("	        , '' eca_parent_item_no		\n");
	    sql.append("	        , '' eca_parent_item_name		\n");

	    sql.append(" FROM ketprodchangeactivity t1 \n");
	    sql.append("	       , ketprodecabomlink t2 \n");
	    sql.append("	       , ketbomheader t3 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("	  AND t1.ida3a8 = ? \n");
	    sql.append("	  AND t1.workerid = ? \n");
	    sql.append("	  AND t2.ida3b5 = t1.ida2a2 \n");
	    sql.append("	  AND t2.ida3a5 = t3.ida2a2 \n");

	    /*
	     * 일괄변경 스펙이 삭제되었기때문에 아래 코드는 필요없다.
	     */
	    /*
	     * //sql.append("	  AND t2.masschangeyn = 'N' \n"); sql.append("	  AND (t2.masschangeyn = 'N' OR t2.masschangeyn is null) \n");
	     */

	    // II. 초도가 아닐 경우
	    sql.append("UNION ALL \n");
	    sql.append("SELECT t1.activitytype activity_type				\n");
	    sql.append("		   , '' doc_type_desc				\n");
	    sql.append("	       , TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' ) create_date					\n");
	    sql.append("	       , 'BOM' activity_type_name 		\n");
	    sql.append("	       , 'BOM' obj_type						\n");

	    // 상세구분(내용)
	    sql.append("	       , t1.activitytypedesc AS activity_type_desc \n");

	    // 완료요청일
	    sql.append("	       , TO_CHAR( t1.completerequestdate, 'YYYY-MM-DD' ) AS complete_request_date \n");
	    // 수신확인일
	    sql.append("	       , TO_CHAR( t1.receiveconfirmeddate, 'YYYY-MM-DD' ) AS receive_confirmed_date \n");
	    // 내용(ToDo 에서 사용자 입력 정보)
	    sql.append("	       , t1.activitybackdesc AS activity_back_desc \n");

	    sql.append("	       , t1.classnamea2a2||':'|| t1.ida2a2 eca_oid						\n");
	    sql.append("	       , t1.activitystatus activity_status				\n");
	    sql.append("	       , t1.workerid worker_id        			\n");
	    sql.append("	       , ( SELECT st2.name \n");
	    sql.append("	        	FROM wtuser st1 \n");
	    sql.append("	          			, people st2 \n");
	    sql.append("	           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	    sql.append("	        	   AND st2.ida3b4 = st1.ida2a2 ) worker_name				\n");
	    sql.append("	        , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) complete_date				\n");
	    sql.append("	        , t2.classnamea2a2 ||':'|| t2.ida2a2 eca_obj_link_oid			\n");
	    sql.append("	        , t3.classnamea2a2 ||':'|| t3.ida2a2 eca_obj_oid					\n");
	    sql.append("	        , (SELECT wtpartnumber \n");
	    sql.append("              FROM wtpartmaster m \n");
	    sql.append("						, wtpart p \n");
	    sql.append("			  WHERE p.ida3masterreference = m.ida2a2 \n");
	    sql.append("		          AND p.ida2a2 = substr(t2.beforepartoid, 16) ) eca_obj_no					\n");
	    sql.append("	        , (SELECT name \n");
	    sql.append("              FROM wtpartmaster m \n");
	    sql.append("						, wtpart p \n");
	    sql.append("			  WHERE p.ida3masterreference = m.ida2a2 \n");
	    sql.append("		          AND p.ida2a2 = substr(t2.beforepartoid, 16) ) eca_obj_name				\n");
	    sql.append("	        , t2.preversion eca_obj_pre_rev			\n");
	    sql.append("	        , t2.afterversion eca_obj_after_rev			\n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date		\n");
	    sql.append("	        , t2.activitycomment eca_obj_act_comment   \n");
	    sql.append("	        , t2.changeyn change_yn					\n");
	    sql.append("	        , '' die_no						\n");
	    sql.append("	        , '' schedule_id				\n");
	    sql.append("	        , '' plan_oid						\n");
	    sql.append("	        , t2.masschangeyn masschange_yn			\n");
	    sql.append("	        , t2.relatedparentpartlist related_part_list			\n");
	    sql.append("	        , t2.beforepartoid before_part_oid			\n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) plan_date					\n");
	    sql.append("	        , t3.ecoitemcode eca_parent_item_no		\n");
	    sql.append("	        , (SELECT name \n");
	    sql.append("                     FROM wtpartmaster m \n");
	    sql.append("		    WHERE m.wtpartnumber = t3.ecoitemcode) eca_parent_item_name \n");

	    sql.append(" FROM ketprodchangeactivity t1 \n");
	    sql.append("	       , ketprodecabomlink t2 \n");
	    sql.append("	       , ketbomecoheader t3 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("	  AND t1.ida3a8 = ? \n");
	    sql.append("	  AND t1.workerid = ? \n");
	    sql.append("	  AND t2.ida3b5 = t1.ida2a2 \n");
	    sql.append("	  AND t2.ida3a5 = t3.ida2a2 \n");

	    /*
	     * 일괄변경 스펙이 삭제되었기때문에 아래 코드는 필요없다.
	     */
	    /*
	     * sql.append("	  AND t2.masschangeyn = 'Y' \n"); sql.append("	  AND ROWNUM = 1 \n");
	     */

	}

	if (ecaType.equals("문서")) {
	    // sql.append(
	    // "UNION ALL                                                                                     																				\n" );

	    sql.append("SELECT t1.activitytype activity_type				\n");
	    sql.append("		   , t2.doctypedesc doc_type_desc				\n");

	    /*
	     * sql.append(
	     * "	       , TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' )		                                                                    create_date       			\n"
	     * ); sql.append(
	     * "	       , ( SELECT n.name                                                                           																		\n");
	     * sql
	     * .append("	            FROM numbercode n                                                                      																	\n"
	     * ); sql.append(
	     * "	           WHERE n.codetype = 'PRODECODOCTYPE'                                                      	 													\n");
	     * sql.append("	               AND n.code = t2.doctype ) 																							activity_type_name		\n"); sql.append(
	     * "	       ,  t2.doctype                                                                           												obj_type						\n"
	     * );
	     */

	    // 작성일
	    sql.append("	       , DECODE(t4.ida2a2, null, TO_CHAR( t1.createstampa2, 'YYYY-MM-DD' ), TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' ) \n");
	    sql.append("	               ) AS create_date    \n");

	    // 후속업무 이름
	    sql.append("	       , DECODE(t4.ida2a2, null, t1.customname \n");
	    sql.append("	                               , DECODE(( SELECT n.name FROM numbercode n WHERE n.codetype = 'PRODECODOCTYPE' AND n.code = t2.doctype ), null, t1.customname \n");
	    sql.append("	                                           , ( SELECT n.name FROM numbercode n WHERE n.codetype = 'PRODECODOCTYPE' AND n.code = t2.doctype )) \n");
	    sql.append("	               ) AS activity_type_name \n");

	    // 후속업무 코드
	    sql.append("	       , DECODE(t4.ida2a2, null, t1.customcode ,t2.doctype) AS obj_type \n");

	    // 상세구분(내용)
	    sql.append("	       , t1.activitytypedesc AS activity_type_desc \n");

	    // 완료요청일
	    sql.append("	       , TO_CHAR( t1.completerequestdate, 'YYYY-MM-DD' ) AS complete_request_date \n");
	    // 수신확인일
	    sql.append("	       , TO_CHAR( t1.receiveconfirmeddate, 'YYYY-MM-DD' ) AS receive_confirmed_date \n");
	    // 내용(ToDo 에서 사용자 입력 정보)
	    sql.append("	       , t1.activitybackdesc AS activity_back_desc \n");

	    sql.append("	       , t1.classnamea2a2||':'|| t1.ida2a2 eca_oid						\n");
	    sql.append("	       , t1.activitystatus activity_status				\n");
	    sql.append("	       , t1.workerid worker_id					\n");
	    sql.append("	       , ( SELECT st2.name \n");
	    sql.append("	            FROM wtuser st1 \n");
	    sql.append("	                    , people st2 \n");
	    sql.append("	           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1	) + 1 ) ) \n");
	    sql.append("	               AND st2.ida3b4 = st1.ida2a2 ) worker_name				\n");
	    sql.append("	       , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) complete_date				\n");
	    sql.append("	       , DECODE( t2.ida2a2, NULL, '', t2.classnamea2a2 ||':'|| t2.ida2a2 ) eca_obj_link_oid        	\n");
	    sql.append("	       , DECODE( t4.ida2a2, NULL, '', t4.classnamea2a2 ||':'|| t4.ida2a2 ) eca_obj_oid		            \n");
	    sql.append("	       , t4.documentno eca_obj_no					\n");
	    sql.append("	       , t4.title eca_obj_name				\n");
	    sql.append("	       , t2.preversion eca_obj_pre_rev			\n");
	    sql.append("	       , t2.afterversion eca_obj_after_rev			\n");
	    sql.append("	       , TO_CHAR( '', 'YYYY-MM-DD' ) eca_obj_plan_date		\n");
	    sql.append("	       , '' eca_obj_act_comment	\n");
	    sql.append("	       , '' change_yn					\n");
	    sql.append("	       , '' die_no						\n");
	    sql.append("	       , '' schedule_id				\n");
	    sql.append("	       , '' plan_oid						\n");
	    sql.append("	       , '' masschange_yn			\n");
	    sql.append("	        , '' related_part_list			\n");
	    sql.append("	        , '' before_part_oid			\n");
	    sql.append("	        , '' plan_date					\n");
	    sql.append("	        , '' eca_parent_item_no		\n");
	    sql.append("	        , '' eca_parent_item_name		\n");

	    sql.append(" FROM ketprodchangeactivity t1 \n");
	    sql.append("	       , ketprodecadoclink t2 \n");
	    sql.append("	   	   , ketprojectdocument t4 \n");
	    sql.append("WHERE (t1.activitytype = '3' OR t1.activitytype = '4') \n");
	    sql.append("	  AND t1.ida3a8 = ? \n");
	    sql.append("	  AND t1.workerid = ? \n");

	    // sql.append("	  AND t2.ida3b5 = t1.ida2a2                                                                       																	\n");
	    // sql.append("	  AND t4.ida2a2 = t2.ida3a5                                                                      	 																\n");
	    sql.append("	  AND t1.ida2a2 = t2.ida3b5(+) \n");
	    sql.append("	  AND t2.ida3a5 = t4.ida2a2(+)  \n");

	    /*
	     * sql.append("UNION ALL                                                                                     																				\n"
	     * ); sql.append(
	     * "SELECT t1.activitytype                                                                        												activity_type				\n"
	     * ); sql.append("		   , t1.activitytypedesc																												doc_type_desc				\n"); sql.append(
	     * "	       , TO_CHAR( t1.createstampa2, 'YYYY-MM-DD' )		                                                                    create_date       			\n"
	     * ); sql.append("	       , '기타'										 																							activity_type_name		\n"); sql.append(
	     * "	       , 'DOC_06'	                                                                           												obj_type						\n"
	     * ); sql.append("	       , t1.classnamea2a2||':'|| t1.ida2a2 																								eca_oid						\n");
	     * sql.append("	       , t1.activitystatus 																													activity_status				\n");
	     * sql.append("	       , t1.workerid 																														worker_id					\n"); sql.append(
	     * "	       , ( SELECT st2.name                                                                          																		\n");
	     * sql.append(
	     * "	            FROM wtuser st1                                                                             																\n"
	     * ); sql.append(
	     * "	                    , people st2                                                                              																\n"
	     * ); sql.append(
	     * "	           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1	) + 1 ) )        									\n");
	     * sql.append("	               AND st2.ida3b4 = st1.ida2a2 ) 																						worker_name				\n");
	     * sql.append("	       , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) 																			complete_date				\n");
	     * sql.append("	       , ''																					 													eca_obj_link_oid        	\n");
	     * sql.append("	       , '' 																																		eca_obj_oid		            \n");
	     * sql.append("	       , ''					 																													eca_obj_no					\n");
	     * sql.append("	       , ''																																		eca_obj_name				\n");
	     * sql.append("	       , ''				 																														eca_obj_pre_rev			\n");
	     * sql.append("	       , ''					 																													eca_obj_after_rev			\n");
	     * sql.append("	       , TO_CHAR( '', 'YYYY-MM-DD' ) 																								eca_obj_plan_date		\n");
	     * sql.append("	       , '' 																																		eca_obj_act_comment	\n");
	     * sql.append("	       , '' 																																		change_yn					\n");
	     * sql.append("	       , '' 																																		die_no						\n");
	     * sql.append("	       , '' 																																		schedule_id				\n");
	     * sql.append("	       , '' 																																		plan_oid						\n");
	     * sql.append("	       , '' 																																		masschange_yn			\n");
	     * sql.append("	        , ''  																																	related_part_list			\n");
	     * sql.append("	        , ''  																																	before_part_oid			\n");
	     * sql.append("	        , ''																																		plan_date					\n");
	     * sql.append("	        , ''					 																													eca_parent_item_no		\n");
	     * sql.append(" FROM ketprodchangeactivity t1                                                                 																		\n"
	     * ); sql.append(
	     * "WHERE 1 = 1                                                                                      																			\n");
	     * sql
	     * .append("	  AND t1.ida3a8 = ?                                                                             																		\n"
	     * ); sql.append("	  AND t1.workerid = ?																																					\n"); sql.append(
	     * "	  AND t1.activitytype = '4'                                                                          																	\n");
	     */
	}

	// sql.append("ORDER BY 1, 2 \n");
	sql.append("ORDER BY activity_type DESC, eca_obj_no ASC \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ida2a2);
	    pstmt.setString(2, workerOid);

	    if (ecaType.equals("도면/BOM")) {
		pstmt.setString(3, ida2a2);
		pstmt.setString(4, workerOid);
		pstmt.setString(5, ida2a2);
		pstmt.setString(6, workerOid);
		/*
	         * } else if (ecaType.equals("BOM") || ecaType.equals("문서")) {
	         */
	    } else if (ecaType.equals("BOM")) {
		pstmt.setString(3, ida2a2);
		pstmt.setString(4, workerOid);
	    }

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		ecaHash = new Hashtable<String, String>();
		ecaHash.put("activity_type", StringUtil.checkNull(rs.getString("activity_type")));
		ecaHash.put("doc_type_desc", StringUtil.checkNull(rs.getString("doc_type_desc")));
		ecaHash.put("create_date", StringUtil.checkNull(rs.getString("create_date")));
		ecaHash.put("activity_type_name", StringUtil.checkNull(rs.getString("activity_type_name")));
		ecaHash.put("obj_type", StringUtil.checkNull(rs.getString("obj_type")));

		// 상세구분(내용)
		ecaHash.put("activity_type_desc", StringUtil.checkNull(rs.getString("activity_type_desc")));

		// 완료요청일
		ecaHash.put("complete_request_date", StringUtil.checkNull(rs.getString("complete_request_date")));
		// 수신확인일
		ecaHash.put("receive_confirmed_date", StringUtil.checkNull(rs.getString("receive_confirmed_date")));
		// 내용(ToDo 에서 사용자 입력 정보)
		ecaHash.put("activity_back_desc", StringUtil.checkNull(rs.getString("activity_back_desc")));

		ecaHash.put("eca_oid", StringUtil.checkNull(rs.getString("eca_oid")));
		ecaHash.put("activity_status", StringUtil.checkNull(rs.getString("activity_status")));
		ecaHash.put("worker_id", StringUtil.checkNull(rs.getString("worker_id")));
		ecaHash.put("worker_name", StringUtil.checkNull(rs.getString("worker_name")));
		ecaHash.put("complete_date", StringUtil.checkNull(rs.getString("complete_date")));
		ecaHash.put("eca_obj_link_oid", StringUtil.checkNull(rs.getString("eca_obj_link_oid")));
		ecaHash.put("eca_obj_oid", StringUtil.checkNull(rs.getString("eca_obj_oid")));
		ecaHash.put("eca_obj_no", StringUtil.checkNull(rs.getString("eca_obj_no")));
		ecaHash.put("eca_obj_name", StringUtil.checkNull(rs.getString("eca_obj_name")));
		ecaHash.put("eca_obj_pre_rev", StringUtil.checkNull(rs.getString("eca_obj_pre_rev")));
		ecaHash.put("eca_obj_after_rev", StringUtil.checkNull(rs.getString("eca_obj_after_rev")));
		ecaHash.put("eca_obj_plan_date", StringUtil.checkNull(rs.getString("eca_obj_plan_date")));
		ecaHash.put("eca_obj_act_comment", StringUtil.checkNull(rs.getString("eca_obj_act_comment")));
		ecaHash.put("change_yn", StringUtil.checkNull(rs.getString("change_yn")));
		ecaHash.put("die_no", StringUtil.checkNull(rs.getString("die_no")));
		ecaHash.put("schedule_id", StringUtil.checkNull(rs.getString("schedule_id")));
		ecaHash.put("plan_oid", StringUtil.checkNull(rs.getString("plan_oid")));
		ecaHash.put("masschange_yn", StringUtil.checkNull(rs.getString("masschange_yn")));
		ecaHash.put("related_part_list", StringUtil.checkNull(rs.getString("related_part_list")));
		ecaHash.put("before_part_oid", StringUtil.checkNull(rs.getString("before_part_oid")));
		ecaHash.put("plan_date", StringUtil.checkNull(rs.getString("plan_date")));
		ecaHash.put("eca_parent_item_no", StringUtil.checkNull(rs.getString("eca_parent_item_no")));
		ecaHash.put("eca_parent_item_name", StringUtil.checkNull(rs.getString("eca_parent_item_name")));

		// 도면 유형
		String value = "";
		String eca_obj_oid = StringUtil.checkNull(rs.getString("eca_obj_oid"));
		Persistable persistable = CommonUtil.getObject(eca_obj_oid);
		if (persistable instanceof EPMDocument) {
		    value = IBAUtil.getAttrValue((EPMDocument) persistable, EDMHelper.IBA_CAD_CATEGORY);
		}
		ecaHash.put("CADCategory", value);

		ecaList.add(ecaHash);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return ecaList;
    }

    public ArrayList<Hashtable<String, String>> getProdEcaList(String ida2a2, String ecaType) throws Exception {
	ArrayList<Hashtable<String, String>> ecaList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> ecaHash = null;

	StringBuffer sql = new StringBuffer();

	if (ecaType.equals("도면") || ecaType.equals("도면/BOM")) {
	    sql.append("SELECT t1.activitytype activity_type \n");
	    sql.append("		   , '' doc_type_desc \n");
	    sql.append("		   , TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' ) create_date \n");
	    sql.append("		   , '도면' activity_type_name \n");
	    sql.append("		   , t2.epmdoctype obj_type \n");

	    // 상세구분(내용)
	    sql.append("	       , t1.activitytypedesc AS activity_type_desc \n");

	    // 완료요청일
	    sql.append("	       , TO_CHAR( t1.completerequestdate, 'YYYY-MM-DD' ) AS complete_request_date \n");
	    // 수신확인일
	    sql.append("	       , TO_CHAR( t1.receiveconfirmeddate, 'YYYY-MM-DD' ) AS receive_confirmed_date \n");
	    // 내용(ToDo 에서 사용자 입력 정보)
	    sql.append("	       , t1.activitybackdesc AS activity_back_desc \n");

	    sql.append("		   , t1.classnamea2a2 ||':'|| t1.ida2a2 eca_oid \n");
	    sql.append("		   , t1.activitystatus activity_status \n");
	    sql.append("		   , t1.workerid worker_id \n");
	    sql.append("		   , ( SELECT st2.name \n");
	    sql.append("		   	    FROM wtuser st1 \n");
	    sql.append("		   	            , people st2 \n");
	    sql.append("		   	   WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	    sql.append("		   	  	   AND st2.ida3b4 = st1.ida2a2 ) worker_name \n");
	    sql.append("			, TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) complete_date \n");
	    sql.append("	        , t2.classnamea2a2 ||':'|| t2.ida2a2 eca_obj_link_oid \n");
	    sql.append("	        , t4.classnamea2a2 ||':'|| t4.ida2a2 eca_obj_oid \n");
	    sql.append("	        , t5.documentnumber eca_obj_no \n");
	    sql.append("	        , t5.name eca_obj_name \n");
	    sql.append("	        , t2.preversion eca_obj_pre_rev \n");
	    sql.append("	        , t2.afterversion eca_obj_after_rev \n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date \n");
	    sql.append("	        , t2.activitycomment eca_obj_act_comment \n");
	    sql.append("	        , t2.changeyn change_yn \n");
	    sql.append("	        , t2.dieno die_no \n");
	    sql.append("	        , t2.scheduleid schedule_id \n");
	    sql.append("	        , (	SELECT st1.classnamea2a2 ||':'|| st1.ida2a2 \n");
	    sql.append("	        	 FROM ketmoldchangeplan st1 \n");
	    sql.append("	            WHERE st1.scheduleid = t2.scheduleid ) plan_oid \n");
	    sql.append("	        , '' masschange_yn \n");
	    sql.append("	        , '' related_part_list \n");
	    sql.append("	        , '' before_part_oid \n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) plan_date \n");
	    sql.append("	        , '' eca_parent_item_no \n");
	    sql.append("	        , '' eca_parent_item_name \n");

	    // Die No
	    sql.append("	        , '' AS expect_cost \n");
	    sql.append("	        , '' AS secure_budget_yn	\n");
	    sql.append("	        , '' AS related_die_no \n");

	    sql.append(" FROM ketprodchangeactivity t1 \n");
	    sql.append("	       , ketprodecaepmdoclink t2 \n");
	    sql.append("	       , epmdocument t4	\n");
	    sql.append("	       , epmdocumentmaster t5 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("	  AND t1.ida3a8 = ? \n");
	    sql.append("	  AND t2.ida3b5 = t1.ida2a2 \n");
	    sql.append("	  AND t4.ida2a2 = t2.ida3a5 \n");
	    sql.append("	  AND t5.ida2a2 = t4.ida3masterreference \n");
	    // sql.append("	  AND t4.latestiterationinfo = 1 \n"); 해당 코드가 있으면 추후 도면 개정이 되면 해당 eco에서는 목록확인이 불가한데 왜 넣었는지 모르겠다 떄문에 주석처리한다
	    // 2015.11.03 by 황정태
	}

	if (ecaType.equals("도면/BOM")) {
	    sql.append("UNION ALL \n");
	}

	if (ecaType.equals("BOM") || ecaType.equals("도면/BOM")) {

	    // I. 초도일 경우
	    sql.append("SELECT t1.activitytype activity_type \n");
	    sql.append("		   , ''	doc_type_desc \n");
	    sql.append("	       , TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' ) create_date \n");
	    sql.append("	       , 'BOM' activity_type_name \n");
	    sql.append("	       , 'BOM' obj_type \n");

	    // 상세구분(내용)
	    sql.append("	       , t1.activitytypedesc AS activity_type_desc \n");

	    // 완료요청일
	    sql.append("	       , TO_CHAR( t1.completerequestdate, 'YYYY-MM-DD' ) AS complete_request_date \n");
	    // 수신확인일
	    sql.append("	       , TO_CHAR( t1.receiveconfirmeddate, 'YYYY-MM-DD' ) AS receive_confirmed_date \n");
	    // 내용(ToDo 에서 사용자 입력 정보)
	    sql.append("	       , t1.activitybackdesc AS activity_back_desc \n");

	    sql.append("	       , t1.classnamea2a2||':'|| t1.ida2a2 eca_oid \n");
	    sql.append("	       , t1.activitystatus activity_status \n");
	    sql.append("	       , t1.workerid worker_id \n");
	    sql.append("	       , ( SELECT st2.name \n");
	    sql.append("	        	FROM wtuser st1 \n");
	    sql.append("	          			, people st2 \n");
	    sql.append("	           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	    sql.append("	        	   AND st2.ida3b4 = st1.ida2a2 ) worker_name \n");
	    sql.append("	        , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) complete_date \n");
	    sql.append("	        , t2.classnamea2a2 ||':'|| t2.ida2a2 eca_obj_link_oid \n");
	    sql.append("	        , t3.classnamea2a2 ||':'|| t3.ida2a2 eca_obj_oid \n");
	    sql.append("	        , t3.ecoitemcode eca_obj_no \n");
	    sql.append("	        , t3.description eca_obj_name \n");
	    sql.append("	        , t2.preversion eca_obj_pre_rev	\n");
	    sql.append("	        , t2.afterversion eca_obj_after_rev \n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date \n");
	    sql.append("	        , t2.activitycomment eca_obj_act_comment \n");
	    sql.append("	        , t2.changeyn change_yn \n");
	    sql.append("	        , '' die_no \n");
	    sql.append("	        , '' schedule_id \n");
	    sql.append("	        , '' plan_oid \n");
	    sql.append("	        , t2.masschangeyn masschange_yn	\n");
	    sql.append("	        , t2.relatedparentpartlist related_part_list \n");
	    sql.append("	        , t2.beforepartoid before_part_oid \n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) plan_date \n");
	    sql.append("	        , '' eca_parent_item_no \n");
	    sql.append("	        , '' eca_parent_item_name \n");

	    // Die No
	    sql.append("	        , t2.expectcost AS expect_cost \n");
	    sql.append("	        , t2.secureBudgetYn AS secure_budget_yn	\n");
	    sql.append("	        , t2.relatedDieNo AS related_die_no \n");

	    sql.append(" FROM ketprodchangeactivity t1 \n");
	    sql.append("	       , ketprodecabomlink t2 \n");
	    sql.append("	       , ketbomheader  t3 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("	  AND t1.ida3a8 = ? \n");
	    sql.append("	  AND t2.ida3b5 = t1.ida2a2 \n");
	    sql.append("	  AND t2.ida3a5 = t3.ida2a2 \n");

	    // Todo에서 작업완료 하지 않았음을 의미한다.
	    sql.append("    AND t1.completedate IS NULL	\n");

	    /*
	     * 일괄변경 스펙이 삭제되었기때문에 아래 코드는 필요없다.
	     */
	    /*
	     * //sql.append("	  AND t2.masschangeyn = 'N' \n"); sql.append("	  AND (t2.masschangeyn = 'N' OR t2.masschangeyn is null) \n");
	     */

	    // II. 초도가 아닐 경우
	    sql.append("UNION ALL \n");
	    sql.append("SELECT t1.activitytype activity_type \n");
	    sql.append("		   , ''	doc_type_desc \n");
	    sql.append("	       , TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' ) create_date \n");
	    sql.append("	       , 'BOM' activity_type_name \n");
	    sql.append("	       , 'BOM' obj_type	\n");

	    // 상세구분(내용)
	    sql.append("	       , t1.activitytypedesc AS activity_type_desc \n");

	    // 완료요청일
	    sql.append("	       , TO_CHAR( t1.completerequestdate, 'YYYY-MM-DD' ) AS complete_request_date \n");
	    // 수신확인일
	    sql.append("	       , TO_CHAR( t1.receiveconfirmeddate, 'YYYY-MM-DD' ) AS receive_confirmed_date \n");
	    // 내용(ToDo 에서 사용자 입력 정보)
	    sql.append("	       , t1.activitybackdesc AS activity_back_desc \n");

	    sql.append("	       , t1.classnamea2a2||':'|| t1.ida2a2 eca_oid \n");
	    sql.append("	       , t1.activitystatus activity_status \n");
	    sql.append("	       , t1.workerid worker_id \n");
	    sql.append("	       , ( SELECT st2.name \n");
	    sql.append("	        	FROM wtuser st1 \n");
	    sql.append("	          			, people st2 \n");
	    sql.append("	           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	    sql.append("	        	   AND st2.ida3b4 = st1.ida2a2 ) worker_name \n");
	    sql.append("	        , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) complete_date \n");
	    sql.append("	        , t2.classnamea2a2 ||':'|| t2.ida2a2 eca_obj_link_oid \n");
	    sql.append("	        , t3.classnamea2a2 ||':'|| t3.ida2a2 eca_obj_oid \n");
	    sql.append("	        , (SELECT wtpartnumber \n");
	    sql.append("              FROM wtpartmaster m \n");
	    sql.append("						, wtpart p \n");
	    sql.append("			  WHERE p.ida3masterreference = m.ida2a2 \n");
	    sql.append("		          AND p.ida2a2 = substr(t2.beforepartoid, 16) ) eca_obj_no \n");
	    sql.append("	        , (SELECT name \n");
	    sql.append("              FROM wtpartmaster m \n");
	    sql.append("						, wtpart p \n");
	    sql.append("			  WHERE p.ida3masterreference = m.ida2a2 \n");
	    sql.append("		          AND p.ida2a2 = substr(t2.beforepartoid, 16) ) eca_obj_name \n");
	    sql.append("	        , t2.preversion eca_obj_pre_rev	\n");
	    sql.append("	        , t2.afterversion eca_obj_after_rev \n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date \n");
	    sql.append("	        , t2.activitycomment eca_obj_act_comment   \n");
	    sql.append("	        , t2.changeyn change_yn	\n");
	    sql.append("	        , '' die_no \n");
	    sql.append("	        , '' schedule_id \n");
	    sql.append("	        , '' plan_oid \n");
	    sql.append("	        , t2.masschangeyn masschange_yn	\n");
	    sql.append("	        , t2.relatedparentpartlist related_part_list \n");
	    sql.append("	        , t2.beforepartoid before_part_oid \n");
	    sql.append("	        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) plan_date \n");
	    sql.append("	        , t3.ecoitemcode eca_parent_item_no \n");
	    sql.append("	        , (SELECT name \n");
	    sql.append("                     FROM wtpartmaster m \n");
	    sql.append("		    WHERE m.wtpartnumber = t3.ecoitemcode) eca_parent_item_name \n");

	    // Die No
	    sql.append("	        , t2.expectcost AS expect_cost \n");
	    sql.append("	        , t2.secureBudgetYn AS secure_budget_yn	\n");
	    sql.append("	        , t2.relatedDieNo AS related_die_no \n");

	    sql.append(" FROM ketprodchangeactivity t1 \n");
	    sql.append("	       , ketprodecabomlink t2 \n");
	    sql.append("	       , ketbomecoheader t3 \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("	  AND t1.ida3a8 = ? \n");
	    sql.append("	  AND t2.ida3b5 = t1.ida2a2 \n");
	    sql.append("	  AND t2.ida3a5 = t3.ida2a2 \n");

	    // Todo에서 작업완료 하지 않았음을 의미한다.
	    sql.append("    AND t1.completedate IS NULL	\n");

	    /*
	     * 일괄변경 스펙이 삭제되었기때문에 아래 코드는 필요없다.
	     */
	    /*
	     * sql.append("	  AND t2.masschangeyn = 'Y' \n"); if (ecaType.equals("도면/BOM")) { sql.append("	  AND ROWNUM = 1 \n"); }
	     */
	}

	if (ecaType.equals("문서")) {
	    // sql.append(
	    // "UNION ALL                                                                                     																				\n" );

	    sql.append("SELECT t1.activitytype AS activity_type	\n");
	    sql.append("       , t2.doctypedesc AS doc_type_desc \n");

	    /*
	     * sql.append("	       , TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' ) AS create_date \n");
	     * sql.append("	       , ( SELECT n.name \n"); sql.append("	            FROM numbercode n \n");
	     * sql.append("	           WHERE n.codetype = 'PRODECODOCTYPE' \n");
	     * sql.append("	               AND n.code = t2.doctype ) AS activity_type_name \n");
	     * sql.append("	       ,  t2.doctype AS obj_type \n");
	     */

	    // 작성일
	    sql.append("	       , DECODE(t4.ida2a2, null, TO_CHAR( t1.createstampa2, 'YYYY-MM-DD' ), TO_CHAR( t2.createstampa2, 'YYYY-MM-DD' ) \n");
	    sql.append("	               ) AS create_date    \n");

	    // 후속업무 이름
	    sql.append("	       , DECODE(t4.ida2a2, null, t1.customname \n");
	    sql.append("	                               , DECODE(( SELECT n.name FROM numbercode n WHERE n.codetype = 'PRODECODOCTYPE' AND n.code = t2.doctype ), null, t1.customname \n");
	    sql.append("	                                           , ( SELECT n.name FROM numbercode n WHERE n.codetype = 'PRODECODOCTYPE' AND n.code = t2.doctype )) \n");
	    sql.append("	               ) AS activity_type_name \n");

	    // 후속업무 코드
	    sql.append("	       , DECODE(t4.ida2a2, null, t1.customcode, t2.doctype) AS obj_type \n");

	    // 상세구분(내용)
	    sql.append("	       , t1.activitytypedesc AS activity_type_desc \n");

	    // 완료요청일
	    sql.append("	       , TO_CHAR( t1.completerequestdate, 'YYYY-MM-DD' ) AS complete_request_date \n");
	    // 수신확인일
	    sql.append("	       , TO_CHAR( t1.receiveconfirmeddate, 'YYYY-MM-DD' ) AS receive_confirmed_date \n");
	    // 내용(ToDo 에서 사용자 입력 정보)
	    sql.append("	       , t1.activitybackdesc AS activity_back_desc \n");

	    sql.append("	       , t1.classnamea2a2||':'|| t1.ida2a2 AS eca_oid \n");
	    sql.append("	       , t1.activitystatus AS activity_status \n");
	    sql.append("	       , t1.workerid AS worker_id \n");
	    sql.append("	       , ( SELECT st2.name \n");
	    sql.append("	            FROM wtuser st1 \n");
	    sql.append("	                    , people st2 \n");
	    sql.append("	           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1	) + 1 ) ) \n");
	    sql.append("	               AND st2.ida3b4 = st1.ida2a2 ) AS worker_name \n");
	    sql.append("	       , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) AS complete_date \n");
	    sql.append("	       , DECODE( t2.ida2a2, NULL, '', t2.classnamea2a2 ||':'|| t2.ida2a2 ) AS eca_obj_link_oid \n");
	    sql.append("	       , DECODE( t4.ida2a2, NULL, '', t4.classnamea2a2 ||':'|| t4.ida2a2 ) AS eca_obj_oid \n");
	    sql.append("	       , t4.documentno AS eca_obj_no \n");
	    sql.append("	       , t4.title AS eca_obj_name \n");
	    sql.append("	       , t2.preversion AS eca_obj_pre_rev \n");
	    sql.append("	       , t2.afterversion AS eca_obj_after_rev \n");
	    sql.append("	       , TO_CHAR( '', 'YYYY-MM-DD' ) AS eca_obj_plan_date \n");
	    sql.append("	       , '' AS eca_obj_act_comment \n");
	    sql.append("	       , '' AS change_yn \n");
	    sql.append("	       , '' AS die_no \n");
	    sql.append("	       , '' AS schedule_id \n");
	    sql.append("	       , '' AS plan_oid	\n");
	    sql.append("	       , '' AS masschange_yn \n");
	    sql.append("	        , '' AS related_part_list \n");
	    sql.append("	        , '' AS before_part_oid	\n");
	    sql.append("	        , '' AS plan_date \n");
	    sql.append("	        , '' AS eca_parent_item_no \n");
	    sql.append("	        , '' AS eca_parent_item_name \n");

	    // Die No
	    sql.append("	        , '' AS expect_cost \n");
	    sql.append("	        , '' AS secure_budget_yn	\n");
	    sql.append("	        , '' AS related_die_no \n");

	    sql.append(" FROM ketprodchangeactivity t1 \n");
	    sql.append("	       , ketprodecadoclink t2 \n");
	    sql.append("	   	   , ketprojectdocument t4 \n");
	    sql.append("WHERE (t1.activitytype = '3' OR t1.activitytype = '4') \n");
	    sql.append("	  AND t1.ida3a8 = ? \n");

	    // sql.append( "	  AND t2.ida3b5 = t1.ida2a2 \n" );
	    // sql.append("	  AND t4.ida2a2 = t2.ida3a5 \n");
	    sql.append("	  AND t1.ida2a2 = t2.ida3b5(+) \n");
	    sql.append("	  AND t2.ida3a5 = t4.ida2a2(+)  \n");

	    /*
	     * sql.append("UNION ALL                                                                                     																				\n"
	     * ); sql.append(
	     * "SELECT t1.activitytype                                                                        												activity_type				\n"
	     * ); sql.append("		   , t1.activitytypedesc																												doc_type_desc				\n"); sql.append(
	     * "	       , TO_CHAR( t1.createstampa2, 'YYYY-MM-DD' )		                                                                    create_date       			\n"
	     * ); sql.append("	       , '기타'										 																							activity_type_name		\n"); sql.append(
	     * "	       , 'DOC_06'	                                                                           												obj_type						\n"
	     * ); sql.append("	       , t1.classnamea2a2||':'|| t1.ida2a2 																								eca_oid						\n");
	     * sql.append("	       , t1.activitystatus 																													activity_status				\n");
	     * sql.append("	       , t1.workerid 																														worker_id					\n"); sql.append(
	     * "	       , ( SELECT st2.name                                                                          																		\n");
	     * sql.append(
	     * "	            FROM wtuser st1                                                                             																\n"
	     * ); sql.append(
	     * "	                    , people st2                                                                              																\n"
	     * ); sql.append(
	     * "	           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1	) + 1 ) )        									\n");
	     * sql.append("	               AND st2.ida3b4 = st1.ida2a2 ) 																						worker_name				\n");
	     * sql.append("	       , TO_CHAR( t1.completedate, 'YYYY-MM-DD' ) 																			complete_date				\n");
	     * sql.append("	       , ''																					 													eca_obj_link_oid        	\n");
	     * sql.append("	       , '' 																																		eca_obj_oid		            \n");
	     * sql.append("	       , ''					 																													eca_obj_no					\n");
	     * sql.append("	       , ''																																		eca_obj_name				\n");
	     * sql.append("	       , ''				 																														eca_obj_pre_rev			\n");
	     * sql.append("	       , ''					 																													eca_obj_after_rev			\n");
	     * sql.append("	       , TO_CHAR( '', 'YYYY-MM-DD' ) 																								eca_obj_plan_date		\n");
	     * sql.append("	       , '' 																																		eca_obj_act_comment	\n");
	     * sql.append("	       , '' 																																		change_yn					\n");
	     * sql.append("	       , '' 																																		die_no						\n");
	     * sql.append("	       , '' 																																		schedule_id				\n");
	     * sql.append("	       , '' 																																		plan_oid						\n");
	     * sql.append("	       , '' 																																		masschange_yn			\n");
	     * sql.append("	        , ''  																																	related_part_list			\n");
	     * sql.append("	        , ''  																																	before_part_oid			\n");
	     * sql.append("	        , ''																																		plan_date					\n");
	     * sql.append("	        , ''					 																													eca_parent_item_no		\n");
	     * sql.append(" FROM ketprodchangeactivity t1                                                                 																		\n"
	     * ); sql.append(
	     * "WHERE 1 = 1                                                                                      																			\n");
	     * sql
	     * .append("	  AND t1.ida3a8 = ?                                                                             																		\n"
	     * ); sql.append(
	     * "	  AND t1.activitytype = '4'                                                                          																	\n");
	     */

	}

	// 생성된 순서로 나오게 한다.
	sql.append("ORDER BY activity_type DESC, eca_obj_no ASC \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ida2a2);

	    if (ecaType.equals("도면/BOM")) {
		pstmt.setString(2, ida2a2);
		pstmt.setString(3, ida2a2);
		/*
	         * } else if (ecaType.equals("BOM") || ecaType.equals("문서")) { pstmt.setString(2, ida2a2); }
	         */
	    } else if (ecaType.equals("BOM")) {
		pstmt.setString(2, ida2a2);
	    }

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		ecaHash = new Hashtable<String, String>();
		ecaHash.put("activity_type", StringUtil.checkNull(rs.getString("activity_type")));
		ecaHash.put("doc_type_desc", StringUtil.checkNull(rs.getString("doc_type_desc")));
		ecaHash.put("create_date", StringUtil.checkNull(rs.getString("create_date")));
		ecaHash.put("activity_type_name", StringUtil.checkNull(rs.getString("activity_type_name")));
		ecaHash.put("obj_type", StringUtil.checkNull(rs.getString("obj_type")));

		// 상세구분(내용)
		ecaHash.put("activity_type_desc", StringUtil.checkNull(rs.getString("activity_type_desc")));

		// 완료요청일
		ecaHash.put("complete_request_date", StringUtil.checkNull(rs.getString("complete_request_date")));
		// 수신확인일
		ecaHash.put("receive_confirmed_date", StringUtil.checkNull(rs.getString("receive_confirmed_date")));
		// 내용(ToDo 에서 사용자 입력 정보)
		ecaHash.put("activity_back_desc", StringUtil.checkNull(rs.getString("activity_back_desc")));

		ecaHash.put("eca_oid", StringUtil.checkNull(rs.getString("eca_oid")));
		ecaHash.put("activity_status", StringUtil.checkNull(rs.getString("activity_status")));
		ecaHash.put("worker_id", StringUtil.checkNull(rs.getString("worker_id")));
		ecaHash.put("worker_name", StringUtil.checkNull(rs.getString("worker_name")));
		ecaHash.put("complete_date", StringUtil.checkNull(rs.getString("complete_date")));
		ecaHash.put("eca_obj_link_oid", StringUtil.checkNull(rs.getString("eca_obj_link_oid")));
		ecaHash.put("eca_obj_oid", StringUtil.checkNull(rs.getString("eca_obj_oid")));
		ecaHash.put("eca_obj_no", StringUtil.checkNull(rs.getString("eca_obj_no")));
		ecaHash.put("eca_obj_name", StringUtil.checkNull(rs.getString("eca_obj_name")));
		ecaHash.put("eca_obj_pre_rev", StringUtil.checkNull(rs.getString("eca_obj_pre_rev")));
		ecaHash.put("eca_obj_after_rev", StringUtil.checkNull(rs.getString("eca_obj_after_rev")));
		ecaHash.put("eca_obj_plan_date", StringUtil.checkNull(rs.getString("eca_obj_plan_date")));
		ecaHash.put("eca_obj_act_comment", StringUtil.checkNull(rs.getString("eca_obj_act_comment")));
		ecaHash.put("change_yn", StringUtil.checkNull(rs.getString("change_yn")));
		ecaHash.put("die_no", StringUtil.checkNull(rs.getString("die_no")));
		ecaHash.put("schedule_id", StringUtil.checkNull(rs.getString("schedule_id")));
		ecaHash.put("plan_oid", StringUtil.checkNull(rs.getString("plan_oid")));
		ecaHash.put("masschange_yn", StringUtil.checkNull(rs.getString("masschange_yn")));
		ecaHash.put("related_part_list", StringUtil.checkNull(rs.getString("related_part_list")));
		ecaHash.put("before_part_oid", StringUtil.checkNull(rs.getString("before_part_oid")));
		ecaHash.put("plan_date", StringUtil.checkNull(rs.getString("plan_date")));
		ecaHash.put("eca_parent_item_no", StringUtil.checkNull(rs.getString("eca_parent_item_no")));
		ecaHash.put("eca_parent_item_name", StringUtil.checkNull(rs.getString("eca_parent_item_name")));

		// Die No
		ecaHash.put("expect_cost", StringUtil.checkNull(rs.getString("expect_cost")));
		ecaHash.put("secure_budget_yn", StringUtil.checkNull(rs.getString("secure_budget_yn")));
		ecaHash.put("related_die_no", StringUtil.checkNull(rs.getString("related_die_no")));

		// 도면 유형
		String value = "";
		String eca_obj_oid = StringUtil.checkNull(rs.getString("eca_obj_oid"));
		Persistable persistable = CommonUtil.getObject(eca_obj_oid);
		if (persistable instanceof EPMDocument) {
		    value = IBAUtil.getAttrValue((EPMDocument) persistable, EDMHelper.IBA_CAD_CATEGORY);
		}
		ecaHash.put("CADCategory", value);

		ecaList.add(ecaHash);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return ecaList;
    }

    public String getChangeReasonName(String chgReasonCode, String codeType) throws Exception {
	String chgReasonName = "";
	StringTokenizer st = new StringTokenizer(chgReasonCode, "|");
	int tokenTotalCnt = st.countTokens();
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT n.name			name							\n");
	sql.append("	FROM numbercode n								\n");
	sql.append("WHERE n.codetype = ?	 							\n");
	sql.append("    AND n.code = ?");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    int tokenCnt = 0;
	    while (st.hasMoreTokens()) {
		pstmt.setString(1, codeType);
		pstmt.setString(2, st.nextToken());

		tokenCnt++;

		rs = pstmt.executeQuery();

		if (rs.next()) {
		    if (chgReasonName.length() > 0) {
			chgReasonName += "/ " + rs.getString("name");
		    } else {
			chgReasonName += rs.getString("name");
		    }
		    // chgReasonName += rs.getString("name");
		    // if( !rs.getString("name").equals("기타") )
		    // {
		    // chgReasonName += "/";
		    // }

		    if (tokenCnt % 3 == 0 && tokenTotalCnt > 3) {
			chgReasonName += "<br>";
		    }
		}
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return chgReasonName;
    }

    public String getChangeReasonName(String chgReasonCode, String codeType, String locale) throws Exception {
	String chgReasonName = "";
	StringTokenizer st = new StringTokenizer(chgReasonCode, "|");
	int tokenTotalCnt = st.countTokens();
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT NC.CODE   AS CODE                \n");
	sql.append("      ,NCV.VALUE AS NAME                \n");
	sql.append("  FROM NUMBERCODE NC                    \n");
	sql.append("      ,NUMBERCODEVALUE NCV              \n");
	sql.append(" WHERE NC.CODETYPE = NCV.CODETYPE       \n");
	sql.append("   AND NC.CODE     = NCV.CODE           \n");
	// sql.append("   AND NC.DISABLED = 0                  \n");
	sql.append("   AND NC.CODETYPE = ?                  \n");
	sql.append("   AND NC.CODE     = ?                  \n");
	sql.append("   AND NCV.LANG    = '" + locale + "'   \n");
	sql.append(" ORDER BY TO_NUMBER(NC.SORTING)         \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    int tokenCnt = 0;
	    while (st.hasMoreTokens()) {
		pstmt.setString(1, codeType);
		pstmt.setString(2, st.nextToken());

		tokenCnt++;

		rs = pstmt.executeQuery();

		if (rs.next()) {
		    if (chgReasonName.length() > 0) {
			chgReasonName += " / " + rs.getString("name");
		    } else {
			chgReasonName += rs.getString("name");
		    }

		    /*
	             * if (tokenCnt % 3 == 0 && tokenTotalCnt > 3) { chgReasonName += "<br>"; }
	             */

		}
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return chgReasonName;
    }

    public ArrayList<Hashtable<String, String>> getProdEcaBomHeaderList(String ida2a2) throws Exception {
	ArrayList<Hashtable<String, String>> bomList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> bomHash = null;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT                                                                                   																			\n");
	sql.append("          h.ecoheadernumber                                                                       						eco_id                       		\n");

	sql.append("        , CASE WHEN NVL(h.bomversion, '0') = '0' THEN '신규' \n");
	// sql.append("        , CASE WHEN NVL(h.bomversion, '0') = '0' THEN 'Part' \n");
	sql.append("               WHEN NVL(h.attribute2, '0') = h.boxquantity THEN '변경없음' \n");
	sql.append("          	   ELSE  '변경' \n");
	sql.append("          END bom_type \n");

	sql.append("        , h.ecoitemcode parent_item_code \n");
	sql.append("	    , (SELECT name \n");
	sql.append("             FROM wtpartmaster m \n");
	sql.append("		WHERE m.wtpartnumber = h.ecoitemcode) parent_item_name \n");

	sql.append("        , '' child_item_code \n");
	sql.append("	    , '' child_item_name \n");

	sql.append("        , h.attribute2                                                                                        					before_qty						\n");
	sql.append("        , h.boxquantity                                                                                     					atfer_qty 						\n");
	sql.append("        , NVL(t2.afterversion, t2.preversion) AS eca_obj_after_rev \n");
	sql.append("        , to_char( t1.completedate, 'YYYY-MM-DD' )                                     	 						eca_complete_date    		\n");
	sql.append("        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' )                                         							eca_obj_plan_date    		\n");
	sql.append("        , t2.activitycomment                                                       										eca_obj_act_comment   	\n");
	sql.append("        , t2.changeyn                                                           												change_yn         				\n");
	sql.append("        , t2.masschangeyn                                                          										masschange_yn      			\n");
	sql.append("     	  , t1.workerid                                                             												worker_id                   	\n");
	sql.append("        , ( SELECT st2.name                                                                            														\n");
	sql.append("             FROM wtuser st1                                                                                                            						\n");
	sql.append("                     , people st2                                                                                                         						\n");
	sql.append("            WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) )                        		\n");
	sql.append("                AND st2.ida3b4 = st1.ida2a2 )                                             							worker_name            		\n");
	sql.append("			, ''  																													before_substitute				\n");
	sql.append("			, ''																													  	after_substitute				\n");
	sql.append("       ,t2.relateddieno																									\n");
	sql.append("       ,t2.expectcost																									\n");
	sql.append("       ,decode(t2.SECUREBUDGETYN,'Y','확보','N','미확보','') AS securebudgetyn																\n");
	sql.append("  FROM ketprodchangeactivity t1                                                              															\n");
	sql.append("          , ketprodecabomlink t2                                                                																\n");

	// sql.append("          , ketbomecoheader h                                                       					\n");
	sql.append("     , ( \n");
	sql.append("        SELECT ida2a2, ecoheadernumber, ecoitemcode, attribute2, boxquantity, bomversion \n");
	sql.append("	  FROM ketbomecoheader \n");
	sql.append("     UNION ALL \n");
	sql.append("        SELECT ida2a2, ecoheadernumber, ecoitemcode, attribute2, boxquantity, bomversion \n");
	sql.append("	  FROM ketbomheader \n");
	sql.append("       ) h \n");

	sql.append("WHERE 1=1                                                   																									\n");
	sql.append("   AND t1.ida3a8 = ?                                                                           																\n");
	sql.append("   AND t2.ida3b5 = t1.ida2a2                                                                    															\n");
	sql.append("   AND t2.ida3a5 = h.ida2a2                                                                    															\n");
	sql.append("   AND t1.completedate IS NOT NULL \n");

	sql.append(" ORDER BY h.ecoitemcode ASC \n");

	// HEENEETODO : 이 값이 언제 들어가는 지 모르지만 작업완료 후 조회화면에서 보이지 않게된다.
	// sql.append("	 AND h.boxquantity <> NVL(h.attribute2, '0')																										\n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ida2a2);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		bomHash = new Hashtable<String, String>();
		bomHash.put("eco_id", StringUtil.checkNull(rs.getString("eco_id")));
		bomHash.put("bom_type", StringUtil.checkNull(rs.getString("bom_type")));
		bomHash.put("parent_item_code", StringUtil.checkNull(rs.getString("parent_item_code")));
		bomHash.put("parent_item_name", StringUtil.checkNull(rs.getString("parent_item_name")));

		bomHash.put("child_item_code", StringUtil.checkNull(rs.getString("child_item_code")));
		bomHash.put("child_item_name", StringUtil.checkNull(rs.getString("child_item_name")));

		bomHash.put("before_qty", StringUtil.checkNull(rs.getString("before_qty")));
		bomHash.put("atfer_qty", StringUtil.checkNull(rs.getString("atfer_qty")));
		bomHash.put("eca_obj_after_rev", StringUtil.checkNull(rs.getString("eca_obj_after_rev")));
		bomHash.put("eca_complete_date", StringUtil.checkNull(rs.getString("eca_complete_date")));
		bomHash.put("eca_obj_plan_date", StringUtil.checkNull(rs.getString("eca_obj_plan_date")));
		bomHash.put("eca_obj_act_comment", StringUtil.checkNull(rs.getString("eca_obj_act_comment")));
		bomHash.put("change_yn", StringUtil.checkNull(rs.getString("change_yn")));
		bomHash.put("masschange_yn", StringUtil.checkNull(rs.getString("masschange_yn")));
		bomHash.put("worker_id", StringUtil.checkNull(rs.getString("worker_id")));
		bomHash.put("worker_name", StringUtil.checkNull(rs.getString("worker_name")));
		bomHash.put("before_substitute", StringUtil.checkNull(rs.getString("before_substitute")));
		bomHash.put("after_substitute", StringUtil.checkNull(rs.getString("after_substitute")));

		bomHash.put("relateddieno", StringUtil.checkNull(rs.getString("relateddieno")));
		bomHash.put("expectcost", StringUtil.checkNull(rs.getString("expectcost")));
		bomHash.put("securebudgetyn", StringUtil.checkNull(rs.getString("securebudgetyn")));

		bomList.add(bomHash);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return bomList;
    }

    public ArrayList<Hashtable<String, String>> getProdEcaBomCompList(String ida2a2, ArrayList<Hashtable<String, String>> bomList)
	    throws Exception {
	// ArrayList<Hashtable<String, String>> bomList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> bomHash = null;

	StringBuffer sql = new StringBuffer();

	/*
	 * sql.append("SELECT * \n"); sql.append("  FROM ( \n");
	 * 
	 * // 초도일 경우 sql.append("SELECT \n"); sql.append("          h.ecoheadernumber eco_id \n"); //
	 * sql.append("        , DECODE(c.bomversion, '0', '신규1', '0.0', '신규2', '추가3') bom_type \n");
	 * sql.append("        , CASE WHEN c.NEWFLAG = 'NEW' AND c.SECONDREMARK = 'NEW' THEN '신규' \n");
	 * sql.append("               WHEN c.NEWFLAG = 'NEW' AND c.SECONDREMARK = 'OLD' THEN '추가' \n");
	 * sql.append("               WHEN c.NEWFLAG = 'OLD' AND c.SECONDREMARK = 'NEW' THEN '-' \n");
	 * sql.append("               WHEN c.NEWFLAG = 'OLD' AND c.SECONDREMARK = 'OLD' THEN '-' \n");
	 * sql.append("               ELSE 'ERROR' END bom_type \n"); sql.append("        , c.parentitemcode parent_item_code \n");
	 * sql.append("	    , (SELECT name \n"); sql.append("             FROM wtpartmaster m \n");
	 * sql.append("		WHERE m.wtpartnumber = c.parentitemcode) parent_item_name \n");
	 * 
	 * sql.append("        , c.childitemcode child_item_code \n"); sql.append("	    , (SELECT name \n");
	 * sql.append("             FROM wtpartmaster m \n"); sql.append("		WHERE m.wtpartnumber = c.childitemcode) child_item_name \n");
	 * 
	 * sql.append("        , c.quantity before_qty \n"); sql.append("        , c.quantity atfer_qty \n");
	 * sql.append("	    , c.bomversion eca_obj_after_rev \n");
	 * sql.append("	    , to_char( t1.completedate, 'YYYY-MM-DD' ) eca_complete_date \n");
	 * sql.append("	    , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date \n");
	 * sql.append("	    , t2.activitycomment eca_obj_act_comment \n"); sql.append("	    , t2.changeyn change_yn \n");
	 * sql.append("	    , t2.masschangeyn masschange_yn \n"); sql.append("	    , t1.workerid worker_id \n");
	 * sql.append("		   , ( SELECT st2.name \n"); sql.append("		   	    FROM wtuser st1 \n");
	 * sql.append("		   	            , people st2 \n");
	 * sql.append("		   	   WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	 * sql.append("		   	  	   AND st2.ida3b4 = st1.ida2a2 ) worker_name 				\n"); sql.append("	   , '' before_substitute \n");
	 * sql.append("	   , '' after_substitute \n"); sql.append("	   , c.BOMLEVEL \n"); sql.append("	   , c.ITEMSEQ \n");
	 * sql.append("  FROM ketprodchangeactivity t1 \n"); sql.append("     , ketprodecabomlink t2 \n");
	 * sql.append("     , ketbomheader h \n"); sql.append("     , ketbomcomponent c \n"); sql.append(" WHERE 1=1 \n");
	 * sql.append("   AND t1.ida3a8 = ? \n"); sql.append("   AND t2.ida3b5 = t1.ida2a2 \n");
	 * sql.append("   AND t2.ida3a5 = h.ida2a2 \n");
	 * 
	 * sql.append("   AND c.newbomcode = h.newbomcode \n"); sql.append("   AND c.newflag = 'NEW' \n");
	 * 
	 * // Todo에서 작업완료하였음을 의미한다. sql.append("   AND t1.completedate IS NOT NULL \n");
	 * 
	 * // 설변일 경우 sql.append("UNION ALL \n");
	 * 
	 * sql.append("SELECT \n"); sql.append("          h.ecoheadernumber eco_id \n"); sql.append(
	 * "        , DECODE( c.ecocode, 'Add', DECODE(t2.afterversion, null, '신규', '추가'), 'Update', '변경', 'Remove', '삭제' ) bom_type \n");
	 * sql.append("        , c.parentitemcode parent_item_code \n"); sql.append("	    , (SELECT name \n");
	 * sql.append("             FROM wtpartmaster m \n"); sql.append("		WHERE m.wtpartnumber = c.parentitemcode) parent_item_name \n");
	 * 
	 * sql.append("        , c.childitemcode child_item_code \n"); sql.append("	    , (SELECT name \n");
	 * sql.append("             FROM wtpartmaster m \n"); sql.append("		WHERE m.wtpartnumber = c.childitemcode) child_item_name \n");
	 * 
	 * sql.append("        , c.beforequantity before_qty \n"); sql.append("        , c.afterquantity atfer_qty \n");
	 * sql.append("	      , t2.afterversion eca_obj_after_rev \n");
	 * sql.append("		  , to_char( t1.completedate, 'YYYY-MM-DD' ) eca_complete_date \n");
	 * sql.append("	      , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date \n");
	 * sql.append("	      , t2.activitycomment eca_obj_act_comment \n"); sql.append("	      , t2.changeyn change_yn \n");
	 * sql.append("	      , t2.masschangeyn masschange_yn \n"); sql.append("		  , t1.workerid worker_id \n");
	 * sql.append("		   , ( SELECT st2.name \n"); sql.append("		   	    FROM wtuser st1 \n");
	 * sql.append("		   	            , people st2 \n");
	 * sql.append("		   	   WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) ) \n");
	 * sql.append("		   	  	   AND st2.ida3b4 = st1.ida2a2 ) worker_name \n"); sql.append(
	 * "			, FN_STR_BOM_SUBSTITUTE( h.ecoheadernumber, c.ecoitemcode, c.parentitemcode, c.childitemcode, 'B' ) before_substitute \n");
	 * sql
	 * .append("			, FN_STR_BOM_SUBSTITUTE( h.ecoheadernumber, c.ecoitemcode, c.parentitemcode, c.childitemcode, 'A' ) after_substitute \n"
	 * ); sql.append("	   , c.BOMLEVEL \n"); sql.append("	   , c.ITEMSEQ \n"); sql.append("  FROM ketprodchangeactivity t1 \n");
	 * sql.append("          , ketprodecabomlink t2 \n");
	 * 
	 * sql.append("          , ketbomecoheader h                                                       					\n");
	 * 
	 * sql.append("     , ( \n"); sql.append("        SELECT ida2a2, ecoheadernumber, ecoitemcode \n");
	 * sql.append("	  FROM ketbomecoheader \n"); sql.append("     UNION ALL \n");
	 * sql.append("        SELECT ida2a2, ecoheadernumber, ecoitemcode \n"); sql.append("	  FROM ketbomheader \n");
	 * sql.append("       ) h \n");
	 * 
	 * 
	 * sql.append("          , ketbomecocomponent c \n"); sql.append("WHERE h.ecoheadernumber  = c.ecoheadernumber \n");
	 * sql.append("   AND h.ecoitemcode = c.parentitemcode \n"); // sql.append("   AND h.ecoitemcode = c.ecoitemcode \n");
	 * sql.append("   AND t1.ida3a8 = ? \n"); sql.append("   AND t2.ida3b5 = t1.ida2a2 \n");
	 * sql.append("   AND t2.ida3a5 = h.ida2a2 \n");
	 * 
	 * // Todo에서 작업완료하였음을 의미한다. sql.append("   AND t1.completedate IS NOT NULL																\n");
	 * sql.append("   AND c.ecocode is not null                                                      	 					\n");
	 * 
	 * sql.append("   ) HN \n"); // sql.append("   ORDER BY HN.BOMLEVEL, HN.ITEMSEQ \n");
	 * sql.append("   ORDER BY HN.parent_item_code, HN.child_item_code, HN.bom_type \n");
	 */

	sql.append(" SELECT * FROM (                                                                                                                                                 ");
	// 초도일 경우
	sql.append(" SELECT h.ecoheadernumber eco_id                                                                                                                                 ");
	sql.append("      , CASE WHEN NVL(h.bomversion, '0') = '0' THEN '신규'                                                                                                       ");
	sql.append("             WHEN NVL(h.attribute2, '0') = h.boxquantity THEN '변경없음'                                                                                         ");
	sql.append("        ELSE  '변경'                                                                                                                                             ");
	sql.append("        END bom_type                                                                                                                                             ");
	sql.append("      , h.ecoitemcode parent_item_code                                                                                                                           ");
	sql.append("      , (SELECT name                                                                                                                                             ");
	sql.append("           FROM wtpartmaster m                                                                                                                                   ");
	sql.append("          WHERE m.wtpartnumber = h.ecoitemcode) parent_item_name                                                                                                 ");
	sql.append("      , '' child_item_code                                                                                                                                       ");
	sql.append("      , '' child_item_name                                                                                                                                       ");
	sql.append("      , h.attribute2                                                                                                            before_qty                       ");
	sql.append("      , h.boxquantity                                                                                                         atfer_qty                          ");
	sql.append("      , NVL(t2.afterversion, t2.preversion) AS eca_obj_after_rev                                                                                                 ");
	sql.append("      , to_char( t1.completedate, 'YYYY-MM-DD' )                                                                  eca_complete_date                              ");
	sql.append("      , TO_CHAR( t2.plandate, 'YYYY-MM-DD' )                                                                     eca_obj_plan_date                               ");
	sql.append("      , t2.activitycomment                                                                                               eca_obj_act_comment                     ");
	sql.append("      , t2.changeyn                                                                                                           change_yn                          ");
	sql.append("      , t2.masschangeyn                                                                                                  masschange_yn                           ");
	sql.append("      , t1.workerid                                                                                                             worker_id                        ");
	sql.append("      , ( SELECT st2.name                                                                                                                                        ");
	sql.append("            FROM wtuser st1                                                                                                                                      ");
	sql.append("                , people st2                                                                                                                                     ");
	sql.append("           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) )                                                            ");
	sql.append("             AND st2.ida3b4 = st1.ida2a2 )                                                                         worker_name                                   ");
	sql.append("      , ''                                                                                                                      before_substitute                ");
	sql.append("      , ''                                                                                                                          after_substitute             ");
	sql.append("      , '' BOMLEVEL                                                                                                                                              ");
	sql.append("      , '' ITEMSEQ                                                                                                                                               ");
	sql.append("     , t2.relateddieno																	      ");
	sql.append("     , t2.expectcost																	      ");
	sql.append("     , decode(t2.SECUREBUDGETYN,'Y','확보','N','미확보','') AS securebudgetyn								      ");
	sql.append("  FROM ketprodchangeactivity t1                                                                                                                                  ");
	sql.append("     , ketprodecabomlink t2                                                                                                                                      ");
	sql.append("     , ( SELECT ida2a2, ecoheadernumber, ecoitemcode, attribute2, boxquantity, bomversion                                                                        ");
	sql.append("           FROM ketbomecoheader                                                                                                                                  ");
	sql.append("      UNION ALL                                                                                                                                                  ");
	sql.append("         SELECT ida2a2, ecoheadernumber, ecoitemcode, attribute2, boxquantity, bomversion                                                                        ");
	sql.append("           FROM ketbomheader                                                                                                                                     ");
	sql.append("       ) h                                                                                                                                                       ");
	sql.append(" WHERE 1=1                                                                                                                                                       ");
	sql.append("   AND t1.ida3a8 = ?                                                                                                                                             ");
	sql.append("   AND t2.ida3b5 = t1.ida2a2                                                                                                                                     ");
	sql.append("   AND t2.ida3a5 = h.ida2a2                                                                                                                                      ");
	// Todo에서 작업완료하였음을 의미한다.
	sql.append("   AND t1.completedate IS NOT NULL                                                                                                                               ");
	sql.append(" ORDER BY h.ecoitemcode ASC ) WHERE (BOM_TYPE IN ('신규','변경') or expectcost is not null)                                                                                                  ");
	// 설변일 경우
	sql.append(" UNION                                                                                                                                                           ");
	sql.append(" SELECT * FROM (                                                                                                                                                 ");
	sql.append(" SELECT *                                                                                                                                                        ");
	sql.append("   FROM (                                                                                                                                                        ");
	sql.append(" SELECT h.ecoheadernumber eco_id                                                                                                                                 ");
	sql.append(" 	 , CASE WHEN c.NEWFLAG = 'NEW' AND c.SECONDREMARK = 'NEW' THEN '신규'                                                                                         ");
	sql.append("             WHEN c.NEWFLAG = 'NEW' AND c.SECONDREMARK = 'OLD' THEN '추가'                                                                                       ");
	sql.append("             WHEN c.NEWFLAG = 'OLD' AND c.SECONDREMARK = 'NEW' THEN '-'                                                                                          ");
	sql.append("             WHEN c.NEWFLAG = 'OLD' AND c.SECONDREMARK = 'OLD' THEN '-'                                                                                          ");
	sql.append("        ELSE 'ERROR' END bom_type                                                                                                                                ");
	sql.append("      , c.parentitemcode parent_item_code                                                                                                                        ");
	sql.append("      , (SELECT name                                                                                                                                             ");
	sql.append("           FROM wtpartmaster m                                                                                                                                   ");
	sql.append("          WHERE m.wtpartnumber = c.parentitemcode) parent_item_name                                                                                              ");
	sql.append("      , c.childitemcode child_item_code                                                                                                                          ");
	sql.append(" 	 , (SELECT name                                                                                                                                              ");
	sql.append("           FROM wtpartmaster m                                                                                                                                   ");
	sql.append(" 		 WHERE m.wtpartnumber = c.childitemcode) child_item_name                                                                                             ");
	sql.append("      , c.quantity before_qty                                                                                                                                    ");
	sql.append("      , c.quantity atfer_qty                                                                                                                                     ");
	sql.append("      , c.bomversion eca_obj_after_rev                                                                                                                           ");
	sql.append("      , to_char( t1.completedate, 'YYYY-MM-DD' ) eca_complete_date                                                                                               ");
	sql.append("      , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date                                                                                                   ");
	sql.append("      , t2.activitycomment eca_obj_act_comment                                                                                                                   ");
	sql.append("      , t2.changeyn change_yn                                                                                                                                    ");
	sql.append("      , t2.masschangeyn masschange_yn                                                                                                                            ");
	sql.append("      , t1.workerid worker_id                                                                                                                                    ");
	sql.append("      , ( SELECT st2.name                                                                                                                                        ");
	sql.append("            FROM wtuser st1                                                                                                                                      ");
	sql.append("               , people st2                                                                                                                                      ");
	sql.append("           WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) )                                                            ");
	sql.append("             AND st2.ida3b4 = st1.ida2a2 ) worker_name                                                                                                           ");
	sql.append("      , '' before_substitute                                                                                                                                     ");
	sql.append("      , '' after_substitute                                                                                                                                      ");
	sql.append("      , c.BOMLEVEL                                                                                                                                               ");
	sql.append("      , c.ITEMSEQ                                                                                                                                                ");
	sql.append("     , t2.relateddieno																	      ");
	sql.append("     , t2.expectcost																	      ");
	sql.append("     , decode(t2.SECUREBUDGETYN,'Y','확보','N','미확보','') AS securebudgetyn								      ");
	sql.append("  FROM ketprodchangeactivity t1                                                                                                                                  ");
	sql.append("     , ketprodecabomlink t2                                                                                                                                      ");
	sql.append("     , ketbomheader h                                                                                                                                            ");
	sql.append("     , ketbomcomponent c                                                                                                                                         ");
	sql.append(" WHERE 1=1                                                                                                                                                       ");
	sql.append("   AND t1.ida3a8 = ?                                                                                                                                             ");
	sql.append("   AND t2.ida3b5 = t1.ida2a2                                                                                                                                     ");
	sql.append("   AND t2.ida3a5 = h.ida2a2                                                                                                                                      ");
	sql.append("   AND c.newbomcode = h.newbomcode                                                                                                                               ");
	sql.append("   AND c.newflag = 'NEW'                                                                                                                                         ");
	sql.append("   AND t1.completedate IS NOT NULL                                                                                                                               ");
	sql.append(" UNION ALL                                                                                                                                                       ");
	sql.append(" SELECT h.ecoheadernumber eco_id                                                                                                                                 ");
	sql.append("        , DECODE( c.ecocode, 'Add', DECODE(t2.afterversion, null, '신규', '추가'), 'Update', '변경', 'Remove', '삭제' ) bom_type                                   ");
	sql.append("        , c.parentitemcode parent_item_code                                                                                                                      ");
	sql.append("        , (SELECT name FROM wtpartmaster m WHERE m.wtpartnumber = c.parentitemcode) parent_item_name                                                             ");
	sql.append("        , c.childitemcode child_item_code                                                                                                                        ");
	sql.append("        , (SELECT name FROM wtpartmaster m WHERE m.wtpartnumber = c.childitemcode) child_item_name                                                               ");
	sql.append("        , c.beforequantity before_qty                                                                                                                            ");
	sql.append("        , c.afterquantity atfer_qty                                                                                                                              ");
	sql.append("        , t2.afterversion eca_obj_after_rev                                                                                                                      ");
	sql.append("        , to_char( t1.completedate, 'YYYY-MM-DD' ) eca_complete_date                                                                                             ");
	sql.append("        , TO_CHAR( t2.plandate, 'YYYY-MM-DD' ) eca_obj_plan_date                                                                                                 ");
	sql.append("        , t2.activitycomment eca_obj_act_comment                                                                                                                 ");
	sql.append("        , t2.changeyn change_yn                                                                                                                                  ");
	sql.append("        , t2.masschangeyn masschange_yn                                                                                                                          ");
	sql.append("        , t1.workerid worker_id                                                                                                                                  ");
	sql.append("        , ( SELECT st2.name FROM wtuser st1 , people st2                                                                                                         ");
	sql.append("             WHERE st1.ida2a2 = TO_NUMBER( SUBSTR( t1.workerid, INSTR( t1.workerid, ':', 1, 1 ) + 1 ) )                                                          ");
	sql.append("               AND st2.ida3b4 = st1.ida2a2 ) worker_name                                                                                                         ");
	sql.append("        , FN_STR_BOM_SUBSTITUTE( h.ecoheadernumber, c.ecoitemcode, c.parentitemcode, c.childitemcode, 'B' ) before_substitute                                    ");
	sql.append("        , FN_STR_BOM_SUBSTITUTE( h.ecoheadernumber, c.ecoitemcode, c.parentitemcode, c.childitemcode, 'A' ) after_substitute                                     ");
	sql.append("        , c.BOMLEVEL                                                                                                                                             ");
	sql.append("        , c.ITEMSEQ                                                                                                                                              ");
	sql.append("     , t2.relateddieno																	      ");
	sql.append("     , t2.expectcost																	      ");
	sql.append("     , decode(t2.SECUREBUDGETYN,'Y','확보','N','미확보','') AS securebudgetyn								      ");
	sql.append("   FROM ketprodchangeactivity t1                                                                                                                                 ");
	sql.append("      , ketprodecabomlink t2                                                                                                                                     ");
	sql.append("      , ketbomecoheader h                                                                                                                                        ");
	sql.append("      , ketbomecocomponent c                                                                                                                                     ");
	sql.append("  WHERE h.ecoheadernumber  = c.ecoheadernumber                                                                                                                   ");
	sql.append("    AND h.ecoitemcode = c.parentitemcode                                                                                                                         ");
	sql.append("    AND t1.ida3a8 = ?                                                                                                                                            ");
	sql.append("    AND t2.ida3b5 = t1.ida2a2                                                                                                                                    ");
	sql.append("    AND t2.ida3a5 = h.ida2a2                                                                                                                                     ");
	sql.append("    AND t1.completedate IS NOT NULL                                                                                                                              ");
	sql.append("    AND (c.ecocode is not null  or expectcost is not null)                                                                                                                                    ");
	sql.append(" ) HN                                                                                                                                                            ");
	sql.append(" ORDER BY HN.parent_item_code, HN.child_item_code, HN.bom_type)                                                                                                  ");

	HashMap<String, String> dupCostCheckMap = new HashMap<String, String>();
	String dupKey = "";

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ida2a2);
	    pstmt.setString(2, ida2a2);
	    pstmt.setString(3, ida2a2);

	    rs = pstmt.executeQuery();

	    String relateddieno = "";
	    String expectcost = "";
	    String securebudgetyn = "";
	    String bom_type = "";
	    while (rs.next()) {

		bom_type = StringUtil.checkNull(rs.getString("bom_type"));

		dupKey = StringUtil.checkNull(rs.getString("parent_item_code")) + StringUtil.checkNull(rs.getString("relateddieno"));

		if (StringUtils.isNotEmpty(StringUtil.checkNull(rs.getString("expectcost")))) {

		    if (StringUtils.isNotEmpty(dupCostCheckMap.get(dupKey))) {
			relateddieno = "";
			expectcost = "";
			securebudgetyn = "";

			if (StringUtils.isEmpty(bom_type) || "변경없음".equals(bom_type)) {
			    continue;
			}
		    } else {
			if (StringUtils.isEmpty(bom_type) || "변경없음".equals(bom_type)) {
			    bom_type = "비용확인";
			}
			relateddieno = StringUtil.checkNull(rs.getString("relateddieno"));
			expectcost = StringUtil.checkNull(rs.getString("expectcost"));
			securebudgetyn = StringUtil.checkNull(rs.getString("securebudgetyn"));

			dupCostCheckMap.put(dupKey, expectcost);
		    }

		} else {
		    relateddieno = "";
		    expectcost = "";
		    securebudgetyn = "";
		}

		bomHash = new Hashtable<String, String>();
		bomHash.put("eco_id", StringUtil.checkNull(rs.getString("eco_id")));
		bomHash.put("bom_type", bom_type);

		bomHash.put("parent_item_code", StringUtil.checkNull(rs.getString("parent_item_code")));
		bomHash.put("parent_item_name", StringUtil.checkNull(rs.getString("parent_item_name")));

		bomHash.put("child_item_code", StringUtil.checkNull(rs.getString("child_item_code")));
		bomHash.put("child_item_name", StringUtil.checkNull(rs.getString("child_item_name")));

		bomHash.put("before_qty", StringUtil.getDouble2(Double.toString(rs.getDouble("before_qty"))));
		bomHash.put("atfer_qty", StringUtil.getDouble2(Double.toString(rs.getDouble("atfer_qty"))));
		bomHash.put("eca_obj_after_rev", StringUtil.checkNull(rs.getString("eca_obj_after_rev")));
		bomHash.put("eca_complete_date", StringUtil.checkNull(rs.getString("eca_complete_date")));
		bomHash.put("eca_obj_plan_date", StringUtil.checkNull(rs.getString("eca_obj_plan_date")));
		bomHash.put("eca_obj_act_comment", StringUtil.checkNull(rs.getString("eca_obj_act_comment")));
		bomHash.put("change_yn", StringUtil.checkNull(rs.getString("change_yn")));
		bomHash.put("masschange_yn", StringUtil.checkNull(rs.getString("masschange_yn")));
		bomHash.put("worker_id", StringUtil.checkNull(rs.getString("worker_id")));
		bomHash.put("worker_name", StringUtil.checkNull(rs.getString("worker_name")));
		bomHash.put("before_substitute", StringUtil.checkNull(rs.getString("before_substitute")));
		bomHash.put("after_substitute", StringUtil.checkNull(rs.getString("after_substitute")));
		bomHash.put("relateddieno", relateddieno);
		bomHash.put("expectcost", expectcost);
		bomHash.put("securebudgetyn", securebudgetyn);

		bomList.add(bomHash);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return bomList;
    }

    public ArrayList<String> getAllEcrLinkOidList(String ecoIda2a2) throws Exception {
	ArrayList<String> ecrLinkOidList = new ArrayList<String>();

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT l.classnamea2a2 || ':' || l.ida2a2 		ecrlink_oid  \n");
	sql.append(" FROM ketprodchangelink l                              		\n");
	sql.append("        , ketprodchangeorder o                         			\n");
	sql.append("WHERE l.ida3a5 = o.ida2a2                              		\n");
	sql.append("   AND o.ida2a2 = ?                             					\n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ecoIda2a2);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		ecrLinkOidList.add(rs.getString("ecrlink_oid"));
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return ecrLinkOidList;
    }

    public ArrayList<String> getAllDqmLinkOidList(String ecoIda2a2) throws Exception {
	ArrayList<String> dqmLinkOidList = new ArrayList<String>();

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT l.classnamea2a2 || ':' || l.ida2a2 AS dqmlink_oid \n");
	sql.append(" FROM KETEcoDqmLink l \n");
	sql.append("        , ketprodchangeorder o \n");
	sql.append("WHERE l.ida3a5 = o.ida2a2 \n");
	sql.append("   AND o.ida2a2 = ? \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ecoIda2a2);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		dqmLinkOidList.add(rs.getString("dqmlink_oid"));
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return dqmLinkOidList;
    }

    public ArrayList<String> getAllPartLinkOidList(String ecoIda2a2) throws Exception {
	ArrayList<String> partLinkOidList = new ArrayList<String>();

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT l.classnamea2a2 || ':' || l.ida2a2 	partlink_oid  	\n");
	sql.append(" FROM ketprodecopartlink l                              		\n");
	sql.append("         , ketprodchangeorder o                          		\n");
	sql.append("WHERE l.ida3b5 = o.ida2a2                               		\n");
	sql.append("   AND o.ida2a2 = ?                               				\n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    pstmt.setString(1, ecoIda2a2);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		partLinkOidList.add(rs.getString("partlink_oid"));
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return partLinkOidList;
    }

    public ArrayList<String> getAllBomLinkOidList(String ecoIda2a2) throws Exception {
	ArrayList<String> bomLinkOidList = new ArrayList<String>();

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT ol.classnamea2a2 || ':' || ol.ida2a2 	bomlink_oid	\n");
	sql.append("  FROM ketprodecabomlink ol                                		\n");
	sql.append("         , ketprodchangeactivity a                         			\n");
	sql.append("WHERE ol.ida3b5 = a.ida2a2                                 		\n");
	sql.append("   AND a.ida3a8 = ?			                                  		\n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoIda2a2);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		bomLinkOidList.add(rs.getString("bomlink_oid"));
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return bomLinkOidList;
    }

    public ArrayList<String> getAllEpmDocLinkOidList(String ecoIda2a2) throws Exception {
	ArrayList<String> epmDocLinkOidList = new ArrayList<String>();

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT ol.classnamea2a2 || ':' || ol.ida2a2 	epmdoclink_oid 	\n");
	sql.append("  FROM ketprodecaepmdoclink ol                             		\n");
	sql.append("         , ketprodchangeactivity a                          				\n");
	sql.append(" WHERE ol.ida3b5 = a.ida2a2                                			\n");
	sql.append("    AND a.ida3a8 = ?                                 						\n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoIda2a2);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		epmDocLinkOidList.add(rs.getString("epmdoclink_oid"));
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return epmDocLinkOidList;
    }

    public ArrayList<String> getAllDocLinkOidList(String ecoIda2a2) throws Exception {
	ArrayList<String> docLinkOidList = new ArrayList<String>();

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT ol.classnamea2a2 || ':' || ol.ida2a2  	doclink_oid  	\n");
	sql.append(" FROM ketprodecadoclink ol                                			\n");
	sql.append("         , ketprodchangeactivity a                         				\n");
	sql.append("WHERE ol.ida3b5 = a.ida2a2                                			\n");
	sql.append("    AND a.ida3a8 = ?                              	 					\n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoIda2a2);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		docLinkOidList.add(rs.getString("doclink_oid"));
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return docLinkOidList;
    }

    public boolean isSucessSapInterface(String ecoId) throws Exception {
	boolean isSucessSapInterface = false;

	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT COUNT(ecoitemcode) AS failCnt \n");
	sql.append("   FROM ( \n");
	sql.append("         SELECT ecoitemcode \n");
	sql.append("           FROM ketbomecoheader \n");
	sql.append("          WHERE transferflag != 'Y' \n");
	sql.append("            AND ecoheadernumber = ? \n");
	sql.append("      UNION ALL \n");
	sql.append("         SELECT ecoitemcode \n");
	sql.append("           FROM ketbomheader \n");
	sql.append("          WHERE transferflag != 'Y' \n");
	sql.append("            AND ecoheadernumber = ? \n");
	sql.append("        ) \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ecoId);
	    pstmt.setString(2, ecoId);

	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		if (rs.getString("failCnt").equals("0")) {
		    isSucessSapInterface = true;
		}
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return isSucessSapInterface;
    }

    /**
     * 함수명 : statementRsClose 설명 :
     * 
     * @throws Exception
     *             작성자 : 오승연 작성일자 : 2010. 10. 14.
     */
    public void statementRsClose() throws Exception {
	if (rs != null)
	    rs.close();

	if (pstmt != null)
	    pstmt.close();
    }

}
