/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : ProdEcrDao.java
 * 작성자 : 오승연
 * 작성일자 : 2010. 10. 19.
 */
package e3ps.ecm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import wt.part.WTPart;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;

/**
 * 클래스명 : ProdEcrDao 설명 : 작성자 : 오승연 작성일자 : 2010. 10. 19.
 */
public class ProdEcrDao {

    private Connection        conn;
    private PreparedStatement pstmt;
    //	private LoggableStatement pstmt;
    private ResultSet         rs;

    public ProdEcrDao(Connection conn) {
	this.conn = conn;
    }

    public String getProdEcrId() throws Exception {
	String ecrId = "";

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT fn_get_ecm_numbering('ECR') FROM dual");

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		ecrId = rs.getString(1);
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return ecrId;
    }

    public Hashtable<String, Object> getProdEcrInfo(String ida2a2) throws Exception {
	Hashtable<String, Object> prodEcr = new Hashtable<String, Object>();

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT 																						\n");
	sql.append("			 r.classnamea2a2 || ':' || r.ida2a2   				ecr_oid					\n");
	sql.append("			, r.ida2a2                                				ecr_ida2a2				\n");
	sql.append("			, r.ecrid                                					ecr_id						\n");
	sql.append(" 			, r.ecrname                            					ecr_name				\n");
	sql.append("			, r.devyn                               					dev_yn					\n");
	sql.append("			, ( SELECT  c.name																\n");
	sql.append("				 FROM numbercode c														\n");
	sql.append("				WHERE c.codetype = 'DEVYN'											\n");
	sql.append("				    AND c.code = r.devyn )      				dev_yn_desc			\n");
	sql.append("			, r.divisionflag                        					division					\n");
	sql.append("          , ( SELECT c.name																\n");
	sql.append("				 FROM numbercode c														\n");
	sql.append("				WHERE c.codetype = 'DIVISIONFLAG'									\n");
	sql.append("					AND c.code = r.divisionflag ) 			division_desc			\n");
	sql.append("			, r.deptname											dept_name				\n");
	sql.append("			, r.projectoid                          					pjt_oid					\n");
	sql.append("	 		, ( SELECT p.pjtno																\n");
	sql.append("				 FROM e3psprojectmaster p	 											\n");
	sql.append("				         , productproject pp	 												\n");
	sql.append("				WHERE p.ida2a2 = pp.ida3b8											 \n");
	sql.append("				   AND  pp.ida2a2 = substr(r.projectoid, instr( r.projectoid, ':')+1, length(r.projectoid) )  )			pjt_no  \n");
	sql.append("	 		, ( SELECT p.pjtname																\n");
	sql.append("				 FROM e3psprojectmaster p	 											\n");
	sql.append("				         , productproject pp	 												\n");
	sql.append("				WHERE p.ida2a2 = pp.ida3b8											 \n");
	sql.append("				   AND  pp.ida2a2 = substr(r.projectoid, instr( r.projectoid, ':')+1, length(r.projectoid) )  )			pjt_name  \n");
	sql.append(" 			, r.changetype                          				chg_type				\n");
	sql.append("			, ( SELECT c.name																\n");
	sql.append("				 FROM numbercode c														\n");
	sql.append("			    WHERE c.codetype = 'PRODCHANGETYPE'							\n");
	sql.append("				   AND  c.code = r.changetype )  			chg_type_desc			\n");
	sql.append(" 			, r.changereason                          			chg_reason				\n");
	sql.append(" 			, ( SELECT c.name																\n");
	sql.append("				 FROM numbercode c														\n");
	sql.append("			    WHERE c.codetype = 'PRODCHAGEREASON'						\n");
	sql.append("				    AND c.code = r.changereason )			chg_reason_desc		\n");
	sql.append("			, r.otherchangereasondesc              			other_reason			\n");
	sql.append("			, r.ecrdesc                                 				ecr_desc					\n");
	sql.append("			, r.expecteffect                             			expected_effect		\n");
	sql.append("			, r.stateState                                			status					\n");
	sql.append("	        ,( SELECT ph.name																\n");
	sql.append("	            FROM phasetemplate ph													\n");
	sql.append("	                    , phaselink pl															\n");
	sql.append("	           WHERE pl.ida3a5 = r.ida3a2state											\n");
	sql.append("	               AND pl.ida3b5 = ph.ida2a2											\n");
	sql.append("	               AND ph.phasestate = r.statestate )  	status_name			\n");
	sql.append("			, u.name                                   	 			creator_id				\n");
	sql.append("			, p.name                                   	 			creator_name			\n");
	sql.append("			, to_char( r.createstampa2, 'YYYY-MM-DD' ) 	create_date				\n");
	sql.append("			, to_char( r.modifystampa2, 'YYYY-MM-DD' ) modify_date			\n");

	sql.append("			, ( \n");
	sql.append("			   SELECT o.code \n");
	sql.append("			     FROM oemprojecttype o \n");
	sql.append("			    WHERE o.ida2a2 = ex.ida3e8 \n");
	sql.append("			  ) carcategory \n");
	sql.append("			, ( \n");
	sql.append("			   SELECT n.code \n");
	sql.append("			     FROM numbercode n , oemprojecttype o \n");
	sql.append("			    WHERE o.ida3a4 = n.ida2a2 \n");
	sql.append("			      AND o.ida2a2 = ex.ida3e8 \n");
	sql.append("			  ) maker \n");
	sql.append("			, ( \n");
	sql.append("			   SELECT n.code \n");
	sql.append("			     FROM numbercode n \n");
	sql.append("			    WHERE n.ida2a2 = ( \n");
	sql.append("			                      SELECT n.ida3a3 \n");
	sql.append("			                        FROM numbercode n , oemprojecttype o \n");
	sql.append("			                       WHERE o.ida3a4 = n.ida2a2 \n");
	sql.append("			                         AND o.ida2a2 = ex.ida3e8 \n");
	sql.append("			                     ) \n");
	sql.append("			  ) domain \n");

	sql.append(" FROM ketprodchangerequest r														\n");

	sql.append("    , KETChangeRequestExpansion ex \n");

	sql.append("		    ,wtuser u																			\n");
	sql.append("		    ,people p																			\n");
	sql.append("WHERE r.ida3b7 = u.ida2a2																\n");

	sql.append("  AND r.ida2a2 = ex.ida3a8(+) \n");

	sql.append("  AND p.ida3b4 = u.ida2a2																\n");
	sql.append("  AND r.ida2a2 = ?																		\n");


	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ida2a2);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		prodEcr.put("ecr_oid", StringUtil.checkNull(rs.getString("ecr_oid")));
		prodEcr.put("ecr_ida2a2", StringUtil.checkNull(rs.getString("ecr_ida2a2")));
		prodEcr.put("ecr_id", StringUtil.checkNull(rs.getString("ecr_id")));
		prodEcr.put("ecr_name", StringUtil.checkNull(rs.getString("ecr_name")));
		prodEcr.put("dev_yn", StringUtil.checkNull(rs.getString("dev_yn")));
		prodEcr.put("dev_yn_desc", StringUtil.checkNull(rs.getString("dev_yn_desc")));
		prodEcr.put("division", StringUtil.checkNull(rs.getString("division")));
		prodEcr.put("division_desc", StringUtil.checkNull(rs.getString("division_desc")));
		prodEcr.put("dept_name", StringUtil.checkNull(rs.getString("dept_name")));
		prodEcr.put("pjt_oid", StringUtil.checkNull(rs.getString("pjt_oid")));
		prodEcr.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		prodEcr.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		prodEcr.put("chg_type", StringUtil.checkNull(rs.getString("chg_type")));
		prodEcr.put("chg_type_desc", StringUtil.checkNull(rs.getString("chg_type_desc")));
		prodEcr.put("other_reason", StringUtil.checkNull(rs.getString("other_reason")));
		prodEcr.put("ecr_desc", StringUtil.checkNull(rs.getString("ecr_desc")));
		prodEcr.put("expected_effect", StringUtil.checkNull(rs.getString("expected_effect")));
		prodEcr.put("status", StringUtil.checkNull(rs.getString("status")));
		prodEcr.put("status_name", StringUtil.checkNull(rs.getString("status_name")));
		prodEcr.put("creator_id", StringUtil.checkNull(rs.getString("creator_id")));
		prodEcr.put("creator_name", StringUtil.checkNull(rs.getString("creator_name")));
		prodEcr.put("create_date", StringUtil.checkNull(rs.getString("create_date")));
		prodEcr.put("modify_date", StringUtil.checkNull(rs.getString("modify_date")));

		prodEcr.put("carcategory", StringUtil.checkNull(rs.getString("carcategory")));
		prodEcr.put("maker", StringUtil.checkNull(rs.getString("maker")));
		prodEcr.put("domain", StringUtil.checkNull(rs.getString("domain")));

	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return prodEcr;
    }

    /**
     * 함수명 : getRefPartList 설명 :
     * 
     * @param ida2a2
     *            - ECR ida2a2
     * @return 작성자 : 오승연 작성일자 : 2010. 11. 18.
     * @throws Exception
     */
    public ArrayList<Hashtable<String, String>> getRefPartList(String ida2a2) throws Exception {
	ArrayList<Hashtable<String, String>> refPartList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> part = null;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT pm.wtpartnumber part_no \n");
	sql.append("	 , pm.name part_name \n");
	sql.append("	 , p.versionida2versioninfo ver \n");
	sql.append("	 , pl.changereqcomment req_comment \n");
	sql.append("	 , p.classnamea2a2 || ':' || p.ida2a2 part_oid \n");
	sql.append("	 , pl.classnamea2a2 || ':' || pl.ida2a2 partlink_oid \n");

	sql.append("     , p." + PartSpecEnum.SpPartType.getColumnName() + " AS PART_TYPE_CODE \n");

	sql.append(" FROM ketprodecrpartlink pl \n");
	sql.append("  	   , wtpart p \n");
	sql.append("  	   , wtpartmaster pm \n");
	sql.append("  	   , ketprodchangerequest r \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("	  AND r.ida2a2 = ? \n");
	sql.append("	  AND r.ida2a2 = pl.ida3b5 \n");
	sql.append("	  AND p.ida2a2 = pl.ida3a5 \n");
	sql.append("	  AND pm.ida2a2 = p.ida3masterreference \n");
	sql.append("ORDER BY pl.createstampa2, pm.wtpartnumber \n");


	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, ida2a2);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		part = new Hashtable<String, String>();

		String part_oid = rs.getString("part_oid");


		part.put("part_no", rs.getString("part_no"));
		part.put("part_name", rs.getString("part_name"));
		part.put("ver", rs.getString("ver"));
		part.put("req_comment", StringUtil.checkNull(rs.getString("req_comment")));
		part.put("part_oid", part_oid);
		part.put("partlink_oid", rs.getString("partlink_oid"));


		part.put("PART_TYPE_CODE", rs.getString("PART_TYPE_CODE"));
		WTPart wtPart = (WTPart) CommonUtil.getObject(part_oid);
		String revDis = StringUtils.stripToEmpty(PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartRevision));
		part.put("revDis", revDis);


		refPartList.add(part);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    statementRsClose();
	}

	return refPartList;

    }

    /**
     * 함수명 : statementRsClose 설명 :
     * 
     * @throws Exception
     *             작성자 : 오승연 작성일자 : 2010. 10. 14.
     */
    public void statementRsClose() throws Exception {
	if (rs != null) rs.close();

	if (pstmt != null) pstmt.close();
    }

}
