/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : WFBomPartUsageQry.java
 * 작성자 : Shin.
 * 작성일자 : 2010. 12. 16.
 */
package e3ps.bom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.util.WTException;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class WFBomPartUsageQry {

    Registry registry = Registry.getRegistry("e3ps.bom.bom");
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    public WFBomPartUsageQry() {
    }

    public boolean getPartUsageResult(String headCd) {
	boolean reSult = false;
	KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	String strType = ""; // 제품:P, 금형:D, 금형부품:M
	try {
	    String itemCd = getItemCode(headCd);
	    WTPart part = kh.searchItem(itemCd);
	    if (part != null) {
		strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

		Vector vc = null;
		if (PartUtil.isProductType(strType)) {
		    vc = this.getGenData(itemCd);

		    if (KETBomHelper.service.setWfKetPartUsageLink(vc, 1)) {
			reSult = true;
		    }

		    if (reSult) {
			if (getSubTrans(itemCd)) {
			    reSult = true;
			}
		    }

		} else if (strType.equals("D")) {
		    vc = this.getMoldData(itemCd);

		    if (KETBomHelper.service.setWfKetPartUsageLink(vc, 2)) {
			reSult = true;
		    }

		} else {
		    Kogger.debug(getClass(), "지정된 값이 없는 PartType 입니다.");
		    reSult = true;
		}

	    }

	    /*
	     * if (reSult) // TransferFlag 를 세팅합니다. { setFalseFlag(headCd, "4"); if (PartUtil.isProductType(strType)) {
	     * setSubTransFlag(itemCd, "4"); }
	     * 
	     * } else { setFalseFlag(headCd, "3"); if (PartUtil.isProductType(strType)) { setSubTransFlag(itemCd, "3"); } }
	     */

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    reSult = false;
	}
	return reSult;
    }

    // 금형부품 KETPartUsageLink 입력값 조회 - 단레벨 :: KETPartUsageLink는 모-자 구성관계 이므로 헤더정보는 조회안함
    public Vector getMoldData(String headItem) {
	StringBuffer strSql = new StringBuffer();
	Vector comp = new Vector();
	Hashtable comp_data = null;

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

	    String econo = getEcoNumber(headItem);

	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    strSql.append(" SELECT  parentitemcode  															\n").append("           , childitemcode  															\n")
		    .append("           , quantity  																	\n")
		    .append("           , trim( replace( unit, 'KET_', '' ) ) as unit 								\n")
		    .append("           , bomversion  																\n").append("           , itemseq  																	\n")
		    .append("           , material , hardnessfrom , hardnessto , designdate 					\n")
		    .append("         --, to_char(to_date(startdate,'yyyy-mm-dd'),'yyyymmdd') as startdt  	\n")
		    .append(" FROM    ketbomcomponent t														\n").append(" WHERE   newbomcode = '" + headItem + "'										\n")
		    .append("  AND     secondremark = 'NEW'													\n").append(" order by sequencenumber															\n");

	    Kogger.debug(getClass(), ">>>>> sql  [금형 신규 WF getMoldData] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());
	    while (rs.next()) {
		String childItem = StringUtil.checkNull(rs.getString("childitemcode"));
		comp_data = new Hashtable();
		comp_data.put("MATNR", StringUtil.checkNull(rs.getString("parentitemcode")));
		comp_data.put("IDNRK", childItem);
		comp_data.put("ODNRK", StringUtil.checkNull(rs.getString("itemseq")));
		comp_data.put("MENGE", StringUtil.checkNull(rs.getString("quantity")));
		comp_data.put("MEINS", (StringUtil.checkNull(rs.getString("unit"))).toUpperCase());
		comp_data.put("ATWRT", StringUtil.checkNull(rs.getString("material")));
		comp_data.put("HARDN", StringUtil.checkNull(rs.getString("hardnessfrom")));
		comp_data.put("HARDT", StringUtil.checkNull(rs.getString("hardnessto")));
		comp_data.put("DATUV", StringUtil.checkNull(rs.getString("designdate")));
		comp_data.put("CHILDV", getItemVersion(childItem));
		comp_data.put("NBOMV", StringUtil.checkNull(rs.getString("bomversion")));
		comp_data.put("ECONO", econo);
		comp.add(comp_data);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    } catch (Exception e) {
		MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
	return comp;
    }

    // 제품 KETPartUsageLink 입력값 조회 - 멀티레벨 :: KETPartUsageLink는 모-자 구성관계 이므로 헤더정보는 조회안함
    public Vector getGenData(String headItem) {
	StringBuffer strSql = new StringBuffer();
	Vector comp = new Vector();
	Hashtable comp_data = null;

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    String econo = getEcoNumber(headItem);
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));
	    // 헤더를 모부품으로 가지는 자부품 목록 가져오는 SQL
	    strSql.append("SELECT  t.parentitemcode  																	\n")
		    .append("         , t.childitemcode  																	\n")
		    .append("         , bh.bomversion 																		\n")
		    .append("         , decode(substr(t.childitemcode,1,3),'P10',t.quantity,'P20',t.quantity,to_char(to_number(nvl(bh.boxquantity,'1')) * to_number(t.quantity)) ) as quantity_erp  \n")
		    .append("         , t.itemseq 																				\n")
		    .append("         , t.quantity 																			\n")
		    .append("         , nvl( bh.boxquantity, '1' ) as boxquantity 										\n")
		    .append("         , trim( replace( t.unit, 'KET_', '' ) ) as unit  										\n")
		    .append("         , to_char( to_date( t.startdate, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt , t.ict, t.reftop, t.refbottom  	\n")
		    .append(" FROM   ketbomcomponent t																\n").append("           , ketbomheader bh																	\n")
		    .append(" WHERE  t.newbomcode = bh.newbomcode												\n")
		    .append("  AND    t.newbomcode = '" + headItem + "'												\n")
		    .append("  AND    t.parentitemcode = '" + headItem + "'												\n")
		    .append("  AND    t.secondremark = 'NEW'															\n").append(" order by t.bomlevel, t.itemseq																\n");

	    Kogger.debug(getClass(), ">>>>> sql [제품 신규 WF getGenData] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    while (rs.next()) {
		String childItem = StringUtil.checkNull(rs.getString("childitemcode"));
		comp_data = new Hashtable();
		comp_data.put("MATNR", StringUtil.checkNull(rs.getString("parentitemcode")));
		comp_data.put("IDNRK", childItem);
		comp_data.put("POSNR", StringUtil.checkNull(rs.getString("itemseq")));
		comp_data.put("MENGE", StringUtil.checkNull(rs.getString("quantity")));
		comp_data.put("ERFMG", StringUtil.checkNull(rs.getString("quantity_erp")));
		comp_data.put("BMENG", StringUtil.checkNull(rs.getString("boxquantity")));
		comp_data.put("MEINS", (StringUtil.checkNull(rs.getString("unit"))).toUpperCase());
		comp_data.put("DATUV", StringUtil.checkNull(rs.getString("startdt")));
		comp_data.put("CHILDV", getItemVersion(childItem));
		comp_data.put("NBOMV", StringUtil.checkNull(rs.getString("bomversion")));
		comp_data.put("ICT", StringUtil.checkNull(rs.getString("ict")));
		comp_data.put("REFTOP", StringUtil.checkNull(rs.getString("reftop")));
		comp_data.put("REFBOTTOM", StringUtil.checkNull(rs.getString("refbottom")));
		comp_data.put("ECONO", econo);
		comp.add(comp_data);
	    }

	    String compItem = "";

	    strSql.append("SELECT  parentitemcode, childitemcode     			\n").append(" FROM   ketbomcomponent t							\n")
		    .append(" WHERE  newbomcode = '" + headItem + "'				\n").append(" order by bomlevel, itemseq								\n");

	    Kogger.debug(getClass(), ">>>>> sql 3: " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    while (rs.next()) {
		compItem = StringUtil.checkNull(rs.getString("childitemcode"));
		Vector compvc = this.getCompData(connection, headItem, compItem);
		if (compvc.size() > 0) {
		    for (int p = 0; p < compvc.size(); p++) {
			comp.add((Hashtable) compvc.elementAt(p));
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    } catch (Exception e) {
		MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
	return comp;
    }

    // 제품일때 component 하위 가져오는것.
    private Vector getCompData(Connection conn, String headItem, String parentItem) {
	PreparedStatement ppstmt = null;
	ResultSet result = null;

	StringBuffer strSql = new StringBuffer();
	Vector comp = new Vector();
	Hashtable<String, String> comp_data = null;
	try {

	    String econo = getEcoNumber(headItem);
	    // strSql.append("SELECT  t.parentitemcode  																	\n")
	    // .append("          , t.childitemcode  																	\n")
	    // .append("          , bh.bomversion  																	\n")
	    // .append("          , decode(substr(t.childitemcode,1,3),'P10',t.quantity,'P20',t.quantity,to_char(to_number(nvl(bh.boxquantity,'1')) * to_number(t.quantity)) ) as quantity_erp  \n")
	    // .append("          , t.itemseq  																			\n")
	    // .append("          , t.quantity  																			\n")
	    // .append("          , nvl( t.boxquantity, '1' ) as boxquantity											\n")
	    // .append("          , trim( replace( t.unit, 'KET_', '' ) ) as unit 										\n")
	    // .append("          , to_char( to_date( t.startdate, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt    	\n")
	    // .append(" FROM   ketbomcomponent t																\n")
	    // .append("           , ketbomheader bh																	\n")
	    // .append(" WHERE   t.newbomcode = bh.newbomcode												\n")
	    // .append("  AND     t.newbomcode = '"+headItem+"'												\n")
	    // .append("  AND     t.parentitemcode = '"+parentItem+"'											\n")
	    // .append("  AND     t.secondremark = 'NEW'															\n")
	    // .append("  order by t.bomlevel, t.itemseq																\n");

	    // component 의 BoxQuantity는 자신의 모부품의 기준수량 정보는 담는다.
	    strSql.append("SELECT  t.parentitemcode  																	\n")
		    .append("          , t.childitemcode  																	\n")
		    .append("          , bh.bomversion  																	\n")
		    .append("          , decode(substr(t.childitemcode,1,3),'P10',t.quantity,'P20',t.quantity,to_char(to_number(nvl(temp.boxquantity,'1')) * to_number(t.quantity)) ) as quantity_erp  \n")
		    .append("          , t.itemseq  																			\n")
		    .append("          , t.quantity  																			\n")
		    .append("          , nvl( temp.boxquantity, '1' ) as boxquantity										\n")
		    .append("          , trim( replace( t.unit, 'KET_', '' ) ) as unit 										\n")
		    .append("          , to_char( to_date( t.startdate, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt ,t.ict, t.reftop, t.refbottom   	\n")
		    .append(" FROM   ketbomcomponent t																\n").append("           , ketbomheader bh																	\n")
		    .append("           , ( SELECT  *																			\n")
		    .append("               FROM   ketbomcomponent t													\n")
		    .append("              WHERE   t.newbomcode =  '" + headItem + "'									\n")
		    .append("                AND    t.childitemcode =  '" + parentItem + "' ) temp						\n")
		    .append(" WHERE   t.newbomcode = bh.newbomcode												\n")
		    .append("  AND     t.newbomcode = '" + headItem + "'												\n")
		    .append("  AND     t.parentitemcode = '" + parentItem + "'											\n")
		    .append("  AND     t.secondremark = 'NEW'															\n")
		    .append("  AND     temp.bomlevel = ( t.bomlevel - 1 ) 											\n")
		    .append("  order by t.bomlevel, t.itemseq																\n");

	    Kogger.debug(getClass(), ">>>>> comp sql [제품 신규 WF getCompData] : " + strSql.toString());

	    ppstmt = conn.prepareStatement(strSql.toString());
	    result = ppstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    String childitemcode = "";
	    String parentitemcode = "";
	    String itemseq = "";
	    String quantity = "";
	    String quantity_erp = "";
	    String boxquantity = "";
	    String unit = "";
	    String startdt = "";
	    String bomversion = "";
	    String ict = "";
	    String reftop = "";
	    String refbottom = "";

	    while (result.next()) {
		childitemcode = StringUtil.checkNull(result.getString("childitemcode"));
		parentitemcode = StringUtil.checkNull(result.getString("parentitemcode"));
		itemseq = StringUtil.checkNull(result.getString("itemseq"));
		quantity = StringUtil.checkNull(result.getString("quantity"));
		quantity_erp = StringUtil.checkNull(result.getString("quantity_erp"));
		boxquantity = StringUtil.checkNull(result.getString("boxquantity"));
		unit = StringUtil.checkNull(result.getString("unit"));
		startdt = StringUtil.checkNull(result.getString("startdt"));
		bomversion = StringUtil.checkNull(result.getString("bomversion"));
		ict = StringUtil.checkNull(result.getString("ict"));
		reftop = StringUtil.checkNull(result.getString("reftop"));
		refbottom = StringUtil.checkNull(result.getString("refbottom"));

		comp_data = new Hashtable<String, String>();
		comp_data.put("MATNR", parentitemcode);
		comp_data.put("IDNRK", childitemcode);
		comp_data.put("POSNR", itemseq);
		comp_data.put("MENGE", quantity);
		comp_data.put("ERFMG", quantity_erp);
		comp_data.put("BMENG", boxquantity);
		comp_data.put("MEINS", unit);
		comp_data.put("DATUV", startdt);
		comp_data.put("CHILDV", getItemVersion(childitemcode));
		comp_data.put("NBOMV", bomversion);
		comp_data.put("ICT", ict);
		comp_data.put("REFTOP", reftop);
		comp_data.put("REFBOTTOM", refbottom);
		comp_data.put("ECONO", econo);

		comp.add(comp_data);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    result.close();
		} catch (Exception e) {
		}
		try {
		    ppstmt.close();
		} catch (Exception e) {
		}
	    } catch (Exception e) {
		MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		/*
	         * if(res != null) { res.freeConnection(registry.getString("plm"), conn); }
	         */
	    }
	}
	return comp;
    }

    // 코드값으로 ItemCode 가져오기.
    public String getItemCode(String headCd) {
	String itemCode = "";
	StringBuffer strSql = new StringBuffer();

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    strSql.append("SELECT bh.newbomcode as itemcode \n");
	    strSql.append("  FROM ketbomheader bh \n");
	    strSql.append("          , ketwfmapprovalmaster wm \n");
	    // strSql.append(" WHERE  bh.ida3a7 = wm.ida3owner \n") // 작성자가 아니어도 결재요청할수 있으므로
	    /*
	     * strSql.append(" WHERE bh.ida2a2 = wm.ida3b4 \n");
	     */
	    strSql.append(" WHERE bh.ida2a2 = wm.ida3b4(+) \n");

	    strSql.append("   AND bh.ida2a2 = '" + headCd + "' \n");

	    strSql.append("UNION ALL \n");

	    strSql.append("SELECT bh.ecoitemcode as itemcode \n");
	    strSql.append("  FROM KETBOMECOHEADER bh \n");
	    strSql.append("          , ketwfmapprovalmaster wm \n");
	    // strSql.append(" WHERE  bh.ida3a7 = wm.ida3owner \n") // 작성자가 아니어도 결재요청할수 있으므로
	    /*
	     * strSql.append(" WHERE bh.ida2a2 = wm.ida3b4 \n");
	     */
	    strSql.append(" WHERE bh.ida2a2 = wm.ida3b4(+) \n");

	    strSql.append("   AND bh.ida2a2 = '" + headCd + "' \n");

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());
	    while (rs.next()) {
		itemCode = StringUtil.checkNull(rs.getString("itemcode"));
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    } catch (Exception e) {
		MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
	return itemCode;
    }

    // KETBomSubstitute 입력.
    public boolean getSubTrans(String headItem) {
	boolean reSult = false;
	StringBuffer strSql = new StringBuffer();

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));
	    connection.setAutoCommit(false);

	    strSql.append("SELECT   s.parentitemcode  								\n").append("           , s.childitemcode  									\n")
		    .append("           , s.substituteitemcode  							\n").append("           , s.quantity 											\n")
		    .append("           , c.unit  												\n").append("           , s.startdate 											\n")
		    .append("           , s.enddate   										\n").append(" FROM    ketbomsubstitute s, ketbomcomponent c 	\n")
		    .append(" WHERE   c.newbomcode = s.newbomcode  			\n").append(" AND      c.newbomcode = '" + headItem + "'				\n")
		    .append(" AND      c.childitemcode = s.childitemcode  			\n");

	    Kogger.debug(getClass(), ">>>>> sql 1 [신규 WF getSubTrans] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();

	    KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	    String parentItem = "";
	    String childItem = "";
	    String subItem = "";

	    while (rs.next()) {
		parentItem = StringUtil.checkNull(rs.getString("parentitemcode"));
		childItem = StringUtil.checkNull(rs.getString("childitemcode"));
		subItem = StringUtil.checkNull(rs.getString("substituteitemcode"));

		WTPart childPart = kh.searchItem(childItem);
		WTPartMaster childPartMaster = (WTPartMaster) (childPart.getMaster());
		Long oids = CommonUtil.getOIDLongValue(childPartMaster);
		String oid2 = String.valueOf(oids);

		strSql.delete(0, strSql.length());
		strSql.append("INSERT  INTO  ketbomsubstitutemaster 	\n").append("          ( PARENTITEM  						\n")
		        .append("           ,CHILDITEM 							\n").append("           ,SUBSTITUTEITEM 					\n")
		        .append("           ,QUANTITY 							\n").append("           ,UNIT 								\n")
		        .append("           ,STARTDATE 						\n").append("           ,ENDDATE  							\n")
		        .append("           ,VERSIONITEMCODE) 				\n").append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )				\n");
		Kogger.debug(getClass(), ">>>>> sql 2 [신규 WF getSubTrans] : " + strSql.toString());

		pstmt = connection.prepareStatement(strSql.toString());
		pstmt.setString(1, parentItem);
		pstmt.setString(2, childItem);
		pstmt.setString(3, subItem);
		pstmt.setString(4, StringUtil.checkNull(rs.getString("QUANTITY")));
		pstmt.setString(5, StringUtil.checkNull(rs.getString("UNIT")));
		pstmt.setString(6, StringUtil.checkNull(rs.getString("STARTDATE")));
		pstmt.setString(7, StringUtil.checkNull(rs.getString("ENDDATE")));
		pstmt.setString(8, oid2);
		pstmt.executeUpdate();
	    }
	    connection.commit();
	    reSult = true;

	} catch (Exception e) {
	    try {
		connection.rollback();
		reSult = false;
	    } catch (SQLException e1) {
		Kogger.error(getClass(), e1);
	    }
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    } catch (Exception e) {
		MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
	return reSult;
    }

    public String getItemVersion(String itemCd) throws WTException {
	String versionStr = "";
	Hashtable inputParams = new Hashtable();
	Vector itemVec = new Vector();
	itemVec.add(itemCd);
	inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
	inputParams.put("itemCode", itemVec);
	Vector version = KETBomHelper.service.getItemVersion(inputParams);

	for (int k = 0; k < version.size(); k++) {
	    if (version.size() > 0) {
		versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
		// shin...
		if (itemCd.equals(versionStr.substring(0, versionStr.indexOf("@")))) {
		    versionStr = versionStr.substring(versionStr.indexOf("@") + 1, versionStr.indexOf("("));
		}
	    }
	}

	return versionStr;
    }

    public void setFalseFlag(String headCd, String flag) {
	String headItem = "";
	StringBuffer strSql = new StringBuffer();

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));
	    connection.setAutoCommit(false);

	    strSql.append("UPDATE  ketbomheader set transferflag = '" + flag + "'  	\n").append(
		    " WHERE   ida2a2 = '" + headCd + "' 							\n");

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());
	    Kogger.debug(getClass(), ">>>>> SQL [WF setFalseFlag] : " + strSql);

	    strSql.append("SELECT  newbomcode   							\n").append(" FROM   ketbomheader 							\n")
		    .append("WHERE  ida2a2 = '" + headCd + "' 				\n");

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());
	    if (rs.next()) {
		headItem = StringUtil.checkNull(rs.getString("newbomcode"));
	    }

	    strSql.append("UPDATE  ketbomcomponent set transferflag = '" + flag + "' 	\n").append(
		    " WHERE  newbomcode = '" + headItem + "'						\n");

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    Kogger.debug(getClass(), ">>>>> SQL [WF setFalseFlag] : " + strSql);

	    connection.commit();
	} catch (Exception e) {
	    try {
		connection.rollback();
	    } catch (SQLException e1) {
		Kogger.error(getClass(), e1);
	    }
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    } catch (Exception e) {
		MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
    }

    public void setSubTransFlag(String headItem, String sFlag) {
	StringBuffer strSql = new StringBuffer();
	Vector comp = new Vector();
	Hashtable comp_data = null;
	String parentItem = "";
	String childItem = "";
	String subItem = "";
	KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));
	    connection.setAutoCommit(false);

	    strSql.append("SELECT   s.parentitemcode												\n").append("           , s.childitemcode  												\n")
		    .append("           , s.substituteitemcode 											\n").append("           , s.quantity  														\n")
		    .append("           , s.unit 															\n").append("           , s.startdate  													\n")
		    .append("           , s.enddate 														\n").append("FROM    ketbomsubstitute s  											\n")
		    .append("          , ketbomcomponent c  										\n").append(" WHERE  c.newbomcode = s.newbomcode  							\n")
		    .append(" AND     c.newbomcode = '" + headItem + "'							\n")
		    .append(" AND     c.childitemcode = s.childitemcode  							\n");
	    Kogger.debug(getClass(), ">>>>> sql [신규 WF setSubTransFlag] : " + strSql.toString());

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		parentItem = StringUtil.checkNull(rs.getString("parentitemcode"));
		childItem = StringUtil.checkNull(rs.getString("childitemcode"));
		subItem = StringUtil.checkNull(rs.getString("substituteitemcode"));

		strSql.delete(0, strSql.length());
		strSql.append("UPDATE  ketbomsubstitute SET  transferflag='" + sFlag + "' 	\n")
		        .append(" WHERE  newbomcode  = ?  									\n").append(" AND     parentitemcode = ?  									\n")
		        .append(" AND     childitemcode = ? 										\n").append(" AND     substituteitemcode = ? 								\n");

		pstmt = connection.prepareStatement(strSql.toString());
		pstmt.setString(1, headItem);
		pstmt.setString(2, parentItem);
		pstmt.setString(3, childItem);
		pstmt.setString(4, subItem);
		pstmt.executeUpdate();

	    }
	    connection.commit();
	} catch (Exception e) {
	    try {
		connection.rollback();
	    } catch (SQLException e1) {
		Kogger.error(getClass(), e1);
	    }
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    } catch (Exception e) {
		MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
    }

    public String getEcoNumber(String newbomcode) {
	String econo = "";
	StringBuffer strSql = new StringBuffer();

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    strSql.append("  SELECT ECOHEADERNUMBER FROM KETBOMHEADER WHERE NEWBOMCODE ='" + newbomcode + "' ");

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());
	    while (rs.next()) {
		econo = StringUtil.checkNull(rs.getString("ECOHEADERNUMBER"));
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		} catch (Exception e) {
		}
	    } catch (Exception e) {
		MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
	return econo;
    }
}
