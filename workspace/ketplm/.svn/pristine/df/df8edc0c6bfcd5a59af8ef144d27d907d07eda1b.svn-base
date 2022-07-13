/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : InfoInterfaceQry.java
 * 작성자 : Shin.
 * 작성일자 : 2010. 12. 02.
 */

package e3ps.sap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import wt.part.WTPart;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.shared.log.Kogger;

public class InfoInterfaceQry {
    DBConnectionManager     res             = null;
    Connection              connection      = null;

    Registry                registry        = Registry.getRegistry("e3ps.bom.bom");
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    public InfoInterfaceQry() {
    }

    // 금형부품 인터페이스 - 단레벨
    public Hashtable getMoldInterfaceData(String headCd) {
	Hashtable total = new Hashtable();

	String headItem = "";
	String headItemRevision = "";

	StringBuffer strSql = new StringBuffer();
	Vector head = new Vector();
	Vector comp = new Vector();

	Hashtable head_data = null;
	Hashtable comp_data = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    String boxquantity = "";
	    strSql.append(" SELECT bh.newbomcode \n");
	    strSql.append("      , bh.bomversion \n");
	    strSql.append("      , bh.quantity \n");
	    strSql.append("      , nvl( bh.BOXQUANTITY, '1' ) as boxquantity \n");
	    strSql.append("      , trim( replace( bh.unitofquantity, 'KET_', '' ) ) as unitofquantity \n");
	    strSql.append("      , bh.substitudebom \n");
	    strSql.append("      , bh.bomtext \n");
	    strSql.append("      , bh.substitudetext \n");
	    strSql.append("      , bh.bomuse \n");
	    strSql.append("      , to_char( bh.createstampa2, 'yyyymmdd' ) as startdt \n");
	    strSql.append("   FROM ketbomheader bh, ketwfmapprovalmaster wm \n");
	    // strSql.append("	  WHERE  bh.ida3a7 = wm.ida3owner \n"); // 작성자가 아니어도 결재요청할수 있으므로

	    /*
	     * strSql.append("  WHERE  bh.ida2a2 = wm.ida3b4 \n");
	     */
	    strSql.append("  WHERE bh.ida2a2 = wm.ida3b4(+) \n");

	    strSql.append("    AND bh.ida2a2 = '" + headCd + "' \n");

	    Kogger.debug(getClass(), ">>>>> sql [금형 신규 getMoldInterfaceData] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    while (rs.next()) {
		head_data = new Hashtable();

		// 부품번호, 리비전
		headItem = StringUtil.checkNull(rs.getString("newbomcode"));
		headItemRevision = StringUtil.checkNull(rs.getString("bomversion"));

		boxquantity = StringUtil.checkNull(rs.getString("boxquantity"));

		// 부품번호, 리비전
		head_data.put("BOMCODE", headItem);
		head_data.put("BOMCODE_REVISION", headItemRevision);

		head_data.put("QUANTITY", StringUtil.checkNull(rs.getString("quantity")));
		head_data.put("UNIT", (StringUtil.checkNull(rs.getString("unitofquantity"))).toUpperCase());
		head_data.put("SUBSTITUDEBOM", StringUtil.checkNull(rs.getString("substitudebom")));
		head_data.put("BOMTEXT", StringUtil.checkNull(rs.getString("bomtext")));
		head_data.put("SUBSTITUDETEXT", StringUtil.checkNull(rs.getString("substitudetext")));
		head_data.put("BOMUSE", StringUtil.checkNull(rs.getString("bomuse")));
		head_data.put("STARTDT", StringUtil.checkNull(rs.getString("startdt")));
		head.add(head_data);
	    }
	    
	    try {
			try {
			    rs.close();
			    rs = null;
			} catch (Exception e) {
			}
			try {
			    pstmt.close();
			    pstmt = null;
			} catch (Exception e) {
			}
		} catch (Exception e) {
			MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
		}

	    strSql.append(" SELECT t.parentitemcode \n");

	    strSql.append("      , t.childitemcode \n");
	    strSql.append("      , t.bomversion \n");

	    strSql.append("      , w.name \n");
	    strSql.append("      , t.quantity \n");
	    strSql.append("      , trim( replace( t.unit, 'KET_', '' ) ) as unit \n");
	    strSql.append("      , t.material \n");
	    strSql.append("      , t.hardnessfrom \n");
	    strSql.append("      , t.hardnessto \n");
	    strSql.append("      , to_char(to_date(t.designdate,'yyyy-mm-dd'),'yyyymmdd') as startdt \n");
	    strSql.append("   FROM ketbomcomponent t , wtpartmaster w \n");
	    strSql.append("  WHERE t.childitemcode = w.wtpartnumber \n");
	    strSql.append("    AND t.newbomcode = '" + headItem + "' \n");
	    strSql.append("  order by t.bomlevel, t.itemseq \n");

	    Kogger.debug(getClass(), ">>>>> sql 2 [금형 신규 getMoldInterfaceData] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    while (rs.next()) {
		comp_data = new Hashtable();
		comp_data.put("PBOMCODE", StringUtil.checkNull(rs.getString("parentitemcode")));

		// 부품번호, 리비전
		comp_data.put("BOMCODE", StringUtil.checkNull(rs.getString("childitemcode")));
		comp_data.put("BOMCODE_REVISION", StringUtil.checkNull(rs.getString("bomversion")));

		comp_data.put("DESC", StringUtil.checkNull(rs.getString("name")));
		comp_data.put("QUANTITY", StringUtil.checkNull(rs.getString("quantity")));
		comp_data.put("UNIT", (StringUtil.checkNull(rs.getString("unit"))).toUpperCase());
		comp_data.put("MATERIAL", StringUtil.checkNull(rs.getString("material")));
		comp_data.put("HARDNESSFROM", StringUtil.checkNull(rs.getString("hardnessfrom")));
		comp_data.put("HARDNESSTO", StringUtil.checkNull(rs.getString("hardnessto")));
		comp_data.put("STARTDT", StringUtil.checkNull(rs.getString("startdt")));
		comp.add(comp_data);
	    }
	    Kogger.debug(getClass(), "%INTERFACE DATA% 금형 신규 head : " + head);
	    Kogger.debug(getClass(), "%INTERFACE DATA% 금형 신규 comp : " + comp);

	    total.put("HEAD", head);
	    total.put("COMP", comp);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		    rs = null;
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		    pstmt = null;
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
	return total;
    }

    // 제품 인터페이스 - 멀티레벨
    public Hashtable<String, Vector<Hashtable<String, String>>> getGenInterfaceData(String headCd) {
	Hashtable<String, Vector<Hashtable<String, String>>> total = new Hashtable<String, Vector<Hashtable<String, String>>>();

	String headItem = "";
	String headItemRevision = "";

	StringBuffer strSql = new StringBuffer();
	Vector head = new Vector();
	Vector comp = new Vector();

	Hashtable head_data = null;
	Hashtable comp_data = null;

	String parentItem = "";

	String childItem = "";
	String childItemRevision = "";

	String p_bomuse = "";
	String p_substitubom = "";
	String p_boxquantity = "";
	String p_unit = "";
	String p_bomtext = "";
	String p_startdt = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    // 1. 신규 BOM 헤더정보 가져오는 SQL

	    strSql.append("  SELECT bh.newbomcode \n");
	    strSql.append("       , bh.bomversion \n");
	    // strSql.append("    , decode(substr(bh.newbomcode,1,3),'P10',bh.quantity,'P20',bh.quantity, to_char(to_number(bh.quantity) * to_number(nvl(bh.BOXQUANTITY,'1'))) ) as quantity  \n")
	    strSql.append("       , bh.quantity \n");
	    // 포장수량과 곱해서 넘기지 않음
	    strSql.append("       , nvl(bh.BOXQUANTITY,'1') as boxquantity \n");
	    strSql.append("       , trim(replace(bh.unitofquantity,'KET_','')) as unitofquantity \n");
	    strSql.append("       , bh.substitudebom, bh.bomtext , bh.substitudetext , bh.bomuse 	\n");
	    strSql.append("       , to_char(bh.createstampa2,'yyyymmdd') as startdt \n");
	    strSql.append("    FROM ketbomheader bh, ketwfmapprovalmaster wm \n");
	    // strSql.append("	  WHERE   bh.ida3a7 = wm.ida3owner \n"); // 작성자가 아니어도 결재요청할수 있으므로

	    /*
	     * strSql.append("   WHERE bh.ida2a2 = wm.ida3b4	\n");
	     */
	    strSql.append("   WHERE bh.ida2a2 = wm.ida3b4(+)	\n");

	    strSql.append("     AND bh.ida2a2 = '" + headCd + "' \n");
	    //초도인데 KETBOMECOHEADER로 데이터가 넘어오는 경우가 있다 그러므로 UNION하기로 한다 썩을.. 2015.09.18 황정태 추가
	    strSql.append("  UNION \n");
	    strSql.append("  SELECT bh.ecoitemcode \n");
	    strSql.append("       , bh.bomversion \n");
	    // strSql.append("    , decode(substr(bh.newbomcode,1,3),'P10',bh.quantity,'P20',bh.quantity, to_char(to_number(bh.quantity) * to_number(nvl(bh.BOXQUANTITY,'1'))) ) as quantity  \n")
	    strSql.append("       , bh.quantity \n");
	    // 포장수량과 곱해서 넘기지 않음
	    strSql.append("       , nvl(bh.BOXQUANTITY,'1') as boxquantity \n");
	    strSql.append("       , trim(replace(bh.unitofquantity,'KET_','')) as unitofquantity \n");
	    strSql.append("       , bh.substitudebom, bh.bomtext , bh.substitudetext , bh.bomuse 	\n");
	    strSql.append("       , to_char(bh.createstampa2,'yyyymmdd') as startdt \n");
	    strSql.append("    FROM ketbomecoheader bh, ketwfmapprovalmaster wm \n");
	    // strSql.append("	  WHERE   bh.ida3a7 = wm.ida3owner \n"); // 작성자가 아니어도 결재요청할수 있으므로

	    /*
	     * strSql.append("   WHERE bh.ida2a2 = wm.ida3b4	\n");
	     */
	    strSql.append("   WHERE bh.ida2a2 = wm.ida3b4(+)	\n");

	    strSql.append("     AND bh.ida2a2 = '" + headCd + "' \n");

	    Kogger.debug(getClass(), ">>>>> sql 1 [제품 신규 Header 1 getGenInterfaceData] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    while (rs.next()) {
		head_data = new Hashtable();

		// 부품번호, 리비전
		headItem = StringUtil.checkNull(rs.getString("newbomcode"));
		headItemRevision = StringUtil.checkNull(rs.getString("bomversion"));

		p_bomuse = StringUtil.checkNull(rs.getString("bomuse"));
		p_substitubom = StringUtil.checkNull(rs.getString("substitudebom"));
		p_boxquantity = StringUtil.checkNull(rs.getString("boxquantity"));
		p_unit = (StringUtil.checkNull(rs.getString("unitofquantity"))).toUpperCase();
		p_bomtext = StringUtil.checkNull(rs.getString("bomtext"));
		p_startdt = StringUtil.checkNull(rs.getString("startdt"));

		// 부품번호, 리비전
		head_data.put("BOMCODE", headItem);
		head_data.put("BOMCODE_REVISION", headItemRevision);

		head_data.put("QUANTITY", p_boxquantity);
		head_data.put("UNIT", p_unit);
		head_data.put("SUBSTITUDEBOM", p_substitubom);
		head_data.put("BOMTEXT", p_bomtext);
		head_data.put("SUBSTITUDETEXT", StringUtil.checkNull(rs.getString("substitudetext")));
		head_data.put("BOMUSE", p_bomuse);
		head_data.put("STARTDT", p_startdt);
		head.add(head_data);
	    }
	    
	    try {
	    	try {
			    rs.close();
			    rs = null;
			} catch (Exception e) {
			}
	    	
			try {
			    pstmt.close();
			    pstmt = null;
			} catch (Exception e) {
			}
			
		} catch (Exception e) {
			MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
		}

	    // 2. ERP에 헤더로 전송될 목록 가져오는 SQL
	    strSql.append(" SELECT parentitemcode   \n ");
	    strSql.append("       ,childitemcode           \n ");
	    strSql.append("       ,bomversion           \n ");
	    strSql.append("       ,quantity_erp            \n ");
	    strSql.append("       ,quantity             \n ");
	    strSql.append("       ,boxquantity          \n ");
	    strSql.append("       ,unit                 \n ");
	    strSql.append("       ,substitudebom        \n ");
	    strSql.append("       ,bomtext              \n" );
	    strSql.append("       ,bomuse               \n" );
	    strSql.append("       ,startdt2             \n" );
	    strSql.append("       ,ict FROM             \n" );
	    strSql.append("(");
	    strSql.append("SELECT t.parentitemcode \n");
	    strSql.append("      , t.childitemcode \n");
	    strSql.append("      , t.bomversion \n");
	    strSql.append("      , decode(substr(t.childitemcode,1,3),'P10',t.quantity,'P20',t.quantity,to_char(to_number(nvl(bh.boxquantity,'1')) * to_number(t.quantity)) ) as quantity_erp  \n");
	    strSql.append("      , t.quantity \n");
	    strSql.append("      , nvl( t.boxquantity, '1.0' ) as boxquantity \n");
	    strSql.append("      , trim( replace( t.unit,'KET_' ,'' ) ) as unit \n");
	    strSql.append("      , bh.substitudebom, bh.bomtext , bh.bomuse \n");
	    strSql.append("      , to_char( to_date( t.startdate, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt2, t.ict as ict,itemseq,bomlevel \n");
	    strSql.append("   FROM ketbomcomponent t \n");
	    strSql.append("      , ketbomheader bh \n");
	    strSql.append("  WHERE t.newbomcode = bh.newbomcode \n");
	    strSql.append("    AND t.newbomcode = '" + headItem + "' \n");
	    strSql.append("    AND newflag = 'NEW' \n");
	    //초도인데 KETBOMECOCOMPNENT로 데이터가 넘어오는 경우가 있다 그러므로 UNION하기로 한다 썩을.. 2015.09.18 황정태 추가
	    strSql.append("  UNION \n");
	    strSql.append("SELECT t.parentitemcode \n");
	    strSql.append(", t.childitemcode \n");
	    strSql.append(", t.bomversion \n");
	    strSql.append(", decode(substr(t.childitemcode,1,3),'P10',t.AFTERQUANTITY,'P20',t.AFTERQUANTITY,to_char(to_number(nvl(bh.boxquantity,'1')) * to_number(t.AFTERQUANTITY)) ) as quantity_erp \n");  
	    strSql.append(", t.AFTERQUANTITY \n");
	    strSql.append(", nvl( t.AFTERBOXQUANTITY, '1.0' ) as boxquantity \n"); 
	    strSql.append(", trim( replace( t.AFTERUNIT,'KET_' ,'' ) ) as unit \n"); 
	    strSql.append(", bh.substitudebom, bh.bomtext , bh.bomuse \n"); 
	    strSql.append(", to_char( to_date( t.AFTERSTARTDATE, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt2, t.AFTERICT as ict,itemseq,bomlevel \n"); 
	    strSql.append("FROM ketbomECOcomponent t  \n");
	    strSql.append(", ketbomECOheader bh \n"); 
	    strSql.append("WHERE t.ECOITEMCODE = bh.ECOITEMCODE \n"); 
	    strSql.append("    AND t.ECOITEMCODE = '" + headItem + "' \n");
	    strSql.append(")");
	    /*
	     * strSql.append("    AND t.firstremark = 'NEW' \n");
	     */
	    strSql.append("  order by bomlevel, itemseq \n");

	    Kogger.debug(getClass(), ">>>>> sql 2 [제품 신규 Header 2 getGenInterfaceData] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    String strUnit = "";
	    String strBomText = "";
	    String strDate = "";
	    String strIct = "";
	    while (rs.next()) {
		head_data = new Hashtable();
		parentItem = StringUtil.checkNull(rs.getString("parentitemcode"));

		// 부품번호, 리비전
		childItem = StringUtil.checkNull(rs.getString("childitemcode"));
		childItemRevision = StringUtil.checkNull(rs.getString("bomversion"));

		p_bomuse = StringUtil.checkNull(rs.getString("bomuse"));
		p_substitubom = StringUtil.checkNull(rs.getString("substitudebom"));
		p_boxquantity = StringUtil.checkNull(rs.getString("boxquantity"));
		strUnit = (StringUtil.checkNull(rs.getString("unit"))).toUpperCase();
		strBomText = StringUtil.checkNull(rs.getString("bomtext"));
		strDate = StringUtil.checkNull(rs.getString("startdt2"));
		strIct = StringUtil.checkNull(rs.getString("ict"));

		// 부품번호, 리비전
		head_data.put("BOMCODE", childItem);
		head_data.put("BOMCODE_REVISION", childItemRevision);

		head_data.put("QUANTITY", p_boxquantity);
		head_data.put("UNIT", strUnit);
		head_data.put("SUBSTITUDEBOM", p_substitubom);
		head_data.put("BOMTEXT", strBomText);
		head_data.put("SUBSTITUDETEXT", getSubTrans(connection, headItem, parentItem, childItem));
		head_data.put("BOMUSE", p_bomuse);
		head_data.put("STARTDT", strDate);
		head_data.put("ICT", strIct);

		head.add(head_data);
	    }
	    
	    try {
	    	try {
			    rs.close();
			    rs = null;
			} catch (Exception e) {
			}
	    	
			try {
			    pstmt.close();
			    pstmt = null;
			} catch (Exception e) {
			}
			
		} catch (Exception e) {
			MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
		}

	    String compItem = "";
	    String parentCompItem = "";

	    strSql.append("SELECT parentitemcode, childitemcode,bomlevel,itemseq \n");
	    strSql.append("  FROM ketbomcomponent t \n");
	    strSql.append(" WHERE newbomcode = '" + headItem + "' \n");
	    strSql.append("   AND newflag = 'NEW' \n");
	    //초도인데 KETBOMECOCOMPNENT로 데이터가 넘어오는 경우가 있다 그러므로 UNION하기로 한다 썩을.. 2015.09.18 황정태 추가
	    strSql.append("  UNION \n");
	    strSql.append("SELECT parentitemcode, childitemcode,bomlevel,itemseq \n");
	    strSql.append("  FROM ketbomecocomponent t \n");
	    strSql.append(" WHERE ecoitemcode = '" + headItem + "' \n");
	    strSql.append(" order by bomlevel, itemseq \n");
	    Kogger.debug(getClass(), ">>>>> sql 3: " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    while (rs.next()) {
		parentCompItem = StringUtil.checkNull(rs.getString("parentitemcode"));
		compItem = StringUtil.checkNull(rs.getString("childitemcode"));
		Vector compvc = this.getCompData(connection, headItem, parentCompItem, compItem);
		if (compvc.size() > 0) {
		    for (int p = 0; p < compvc.size(); p++) {
			comp.add((Hashtable) compvc.elementAt(p));
		    }
		}
	    }

	    // 3. ERP로 전송될 Component 목록 가져오는 SQL
	    // strSql.append(" SELECT  t.parentitemcode  																	\n")
	    // .append("           , t.childitemcode     																\n")
	    // .append("           , decode(substr(t.childitemcode,1,3),'P10',t.quantity,'P20',t.quantity,to_char(to_number(nvl(temp.boxquantity, bh.boxquantity)) * to_number(t.quantity)) ) as quantity_erp  \n")
	    // .append("           , t.quantity  																			\n")
	    // .append("           , nvl( temp.boxquantity, bh.boxquantity ) as boxquantity 						\n")
	    // .append("           , trim( replace( t.unit,'KET_' ,'' ) ) as unit  										\n")
	    // .append("           , to_char( to_date( t.startdate, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt3   	\n")
	    // .append("  FROM    ketbomcomponent t																\n")
	    // .append("            , ketbomheader bh																	\n")
	    // .append("            ,( SELECT  *  FROM  ketbomcomponent t										\n")
	    // .append("              WHERE  t.newbomcode = '"+headItem+"') temp							\n")
	    // .append("  WHERE   t.newbomcode = bh.newbomcode												\n")
	    // .append("  AND      t.newbomcode = '"+headItem+"'												\n")
	    // .append("  AND      t.secondremark = 'NEW'															\n")
	    // .append("  AND      temp.childitemcode(+) = t.parentitemcode									\n")
	    // .append("  AND      temp.bomlevel(+) = (t.bomlevel -1)    											\n")
	    // .append("  order by t.bomlevel, t.itemseq																\n");
	    //
	    // Kogger.debug(getClass(), ">>>>> sql 3 [제품 신규 Comp getGenInterfaceData] : "+strSql.toString());
	    // pstmt = connection.prepareStatement(strSql.toString());
	    // rs = pstmt.executeQuery();
	    // strSql.delete(0, strSql.length());
	    //
	    // String strQuantity = "";
	    // while(rs.next())
	    // {
	    // comp_data = new Hashtable();
	    // parentItem = StringUtil.checkNull(rs.getString("parentitemcode"));
	    // childItem = StringUtil.checkNull(rs.getString("childitemcode"));
	    // strQuantity = StringUtil.checkNull(rs.getString("quantity_erp"));
	    // strUnit = (StringUtil.checkNull(rs.getString("unit"))).toUpperCase();
	    // strDate= StringUtil.checkNull(rs.getString("startdt3"));
	    //
	    // comp_data.put("PBOMCODE", parentItem);
	    // comp_data.put("BOMCODE", childItem);
	    // comp_data.put("QUANTITY", strQuantity); // 자부품의 수량(quantity)에 모부품의 기준수량(boxQuantity)를 곱한 값
	    // comp_data.put("UNIT", strUnit);
	    // comp_data.put("SUBSTITUDETEXT", getSubTrans(connection, headItem, parentItem, childItem));
	    // comp_data.put("STARTDT", strDate);
	    // comp.add(comp_data);
	    // }

	    Kogger.debug(getClass(), "%INTERFACE DATA% 제품 신규 head : " + head);
	    Kogger.debug(getClass(), "%INTERFACE DATA% 제품 신규 comp : " + comp);

	    total.put("HEAD", head);
	    total.put("COMP", comp);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		    rs = null;
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		    pstmt = null;
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
	return total;
    }

    // 제품일때 component 하위 가져오는것.
    public Vector getCompData(Connection conn, String headItem, String parentItem, String childCompItem) {
	boolean isParentHeader = false;

	StringBuffer strSql = new StringBuffer();
	Vector comp = new Vector();
	Hashtable<String, String> comp_data = null;

	// 모부품이 헤더인 경우
	if (parentItem.equals(headItem)) {
	    isParentHeader = true;
	}

	PreparedStatement ppstmt = null;
	ResultSet result = null;
	try {
	    // component 의 BoxQuantity는 자신의 모부품의 기준수량 정보는 담는다.
	    strSql.append("SELECT  t.parentitemcode \n");
	    strSql.append("          , t.childitemcode \n");
	    strSql.append("          , bh.bomversion \n");
	    if (isParentHeader) {
		strSql.append("          , decode(substr(t.childitemcode,1,3),'P10',t.quantity,'P20',t.quantity,to_char(to_number(nvl(bh.boxquantity,'1')) * to_number(t.quantity)) ) as quantity_erp  \n");
	    }
	    else {
		strSql.append("          , decode(substr(t.childitemcode,1,3),'P10',t.quantity,'P20',t.quantity,to_char(to_number(nvl(temp.boxquantity,'1')) * to_number(t.quantity)) ) as quantity_erp  \n");
	    }
	    strSql.append("          , t.itemseq \n");
	    strSql.append("          , t.quantity \n");
	    strSql.append("          , nvl( temp.boxquantity, '1' ) as boxquantity \n");
	    strSql.append("          , trim( replace( t.unit, 'KET_', '' ) ) as unit \n");
	    strSql.append("          , to_char( to_date( t.startdate, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt, t.ict, t.bomlevel    	\n");
	    strSql.append(" FROM   ketbomcomponent t \n");
	    strSql.append("           , ketbomheader bh	\n");
	    strSql.append("           , ( SELECT  * \n");
	    strSql.append("               FROM   ketbomcomponent t \n");
	    strSql.append("              WHERE   t.newbomcode =  '" + headItem + "' \n");

	    if (isParentHeader) {
		strSql.append("                AND t.childitemcode =  '" + childCompItem + "' \n");
	    }
	    else {
		strSql.append("                AND t.childitemcode =  '" + parentItem + "' \n");
	    }
	    strSql.append("             ) temp \n");
	    strSql.append(" WHERE   t.newbomcode = bh.newbomcode \n");
	    strSql.append("  AND     t.newbomcode = '" + headItem + "'\n");
	    strSql.append("  AND     t.parentitemcode = '" + parentItem + "' \n");
	    strSql.append("  AND     t.secondremark = 'NEW' \n");
	    strSql.append("  AND     t.childitemcode = '" + childCompItem + "' \n");
	    if (!isParentHeader) {
		strSql.append("  AND     temp.bomlevel = ( t.bomlevel - 1 ) \n");
	    }
	    strSql.append("  union \n");
	    strSql.append("SELECT  t.parentitemcode \n");
	    strSql.append("          , t.childitemcode \n");
	    strSql.append("          , bh.bomversion \n");
	    if (isParentHeader) {
		strSql.append("          , decode(substr(t.childitemcode,1,3),'P10',t.AFTERquantity,'P20',t.AFTERquantity,to_char(to_number(nvl(bh.boxquantity,'1')) * to_number(t.AFTERquantity)) ) as quantity_erp  \n");
	    }
	    else {
		strSql.append("          , decode(substr(t.childitemcode,1,3),'P10',t.AFTERquantity,'P20',t.AFTERquantity,to_char(to_number(nvl(temp.AFTERboxquantity,'1')) * to_number(t.AFTERquantity)) ) as quantity_erp  \n");
	    }
	    strSql.append("          , t.itemseq \n");
	    strSql.append("          , t.AFTERquantity as quantity \n");
	    strSql.append("          , nvl( temp.AFTERboxquantity, '1' ) as boxquantity \n");
	    strSql.append("          , trim( replace( t.AFTERunit, 'KET_', '' ) ) as unit \n");
	    strSql.append("          , to_char( to_date( t.AFTERstartdate, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt, t.afterict as ict, t.bomlevel    	\n");
	    strSql.append(" FROM   ketbomecocomponent t \n");
	    strSql.append("           , ketbomecoheader bh	\n");
	    strSql.append("           , ( SELECT  * \n");
	    strSql.append("               FROM   ketbomecocomponent t \n");
	    strSql.append("              WHERE   t.ecoitemcode =  '" + headItem + "' \n");

	    if (isParentHeader) {
		strSql.append("                AND t.childitemcode =  '" + childCompItem + "' \n");
	    }
	    else {
		strSql.append("                AND t.childitemcode =  '" + parentItem + "' \n");
	    }
	    strSql.append("             ) temp \n");
	    strSql.append(" WHERE   t.ecoitemcode = bh.ecoitemcode \n");
	    strSql.append("  AND     t.ecoitemcode = '" + headItem + "'\n");
	    strSql.append("  AND     t.parentitemcode = '" + parentItem + "' \n");
	    strSql.append("  AND     t.childitemcode = '" + childCompItem + "' \n");
	    if (!isParentHeader) {
		strSql.append("  AND     temp.bomlevel = ( t.bomlevel - 1 ) \n");
	    }
	    strSql.append("  order by bomlevel, itemseq \n");

	    Kogger.debug(getClass(), ">>>>> sql 3 [제품 신규 Comp getGenInterfaceData] : " + strSql.toString());
	    ppstmt = conn.prepareStatement(strSql.toString());
	    result = ppstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    String strQuantity = "";
	    String parentItemCode = "";

	    // 부품번호, 버전
	    String childItem = "";
	    String childItemVersion = "";

	    String strUnit = "";
	    String strDate = "";
	    String strIct = "";

	    while (result.next()) {
		comp_data = new Hashtable();
		parentItemCode = StringUtil.checkNull(result.getString("parentitemcode"));

		// 부품번호, 버전
		childItem = StringUtil.checkNull(result.getString("childitemcode"));
		childItemVersion = StringUtil.checkNull(result.getString("bomversion"));

		strQuantity = StringUtil.checkNull(result.getString("quantity_erp"));
		strUnit = (StringUtil.checkNull(result.getString("unit"))).toUpperCase();
		strDate = StringUtil.checkNull(result.getString("startdt"));
		strIct = StringUtil.checkNull(result.getString("ict"));

		comp_data.put("PBOMCODE", parentItemCode);

		// 부품번호, 버전
		comp_data.put("BOMCODE", childItem);
		comp_data.put("BOMCODE_REVISION", childItemVersion);

		comp_data.put("QUANTITY", strQuantity); // 자부품의 수량(quantity)에 모부품의 기준수량(boxQuantity)를 곱한 값
		comp_data.put("UNIT", strUnit);
		comp_data.put("SUBSTITUDETEXT", getSubTrans(connection, headItem, parentItemCode, childItem));
		comp_data.put("STARTDT", strDate);
		comp_data.put("ICT", strIct);
		comp.add(comp_data);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    result.close();
		    result = null;
		} catch (Exception e) {
		}
		try {
		    ppstmt.close();
		    ppstmt = null;
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

    // KETBomSubstitute 정보 가져오기
    public String getSubTrans(Connection conn, String headItem, String parentItem, String childItem) {
	String strReturn = "";
	StringBuffer strSql = new StringBuffer();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {

	    strSql.append("SELECT   substituteitemcode  								\n").append(" FROM    ketbomsubstitute s								\n").append(" WHERE   s.newbomcode = '" + headItem + "'				\n")
		    .append("   AND   s.parentitemcode = '" + parentItem + "'			\n").append("   AND   s.childitemcode = '" + childItem + "'				\n");

	    Kogger.debug(getClass(), ">>>>> sql [신규 대체부품정보 getSubTrans] : " + strSql.toString());
	    pstmt = conn.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		strReturn += rs.getString("substituteitemcode") + ",";
	    }

	    if (strReturn.indexOf(",") > 0) {
		strReturn = strReturn.substring(0, strReturn.length() - 1);
	    }
	    Kogger.debug(getClass(), "@@@@ strReturn [getSubTrans]  " + headItem + "(" + parentItem + "/" + childItem + ") : " + strReturn);
	} catch (SQLException e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException e) {
		Kogger.error(getClass(), e);
	    }
	}
	return strReturn;
    }

    // 제품일때 component 하위 가져오는것.
    // public Vector getCompData(Connection conn,String headItem, String parentItem, String p_boxquantity)
    // {
    // PreparedStatement ppstmt = null;
    // ResultSet result = null;
    //
    // StringBuffer strSql = new StringBuffer();
    // Vector comp = new Vector();
    // Hashtable comp_data = null;
    // try
    // {
    // strSql.append("SELECT   t.parentitemcode  																	\n")
    // .append("           , t.childitemcode  																	\n")
    // .append("           , decode(substr(t.childitemcode,1,3),'P10',t.quantity,'P20',t.quantity,to_char(to_number(nvl(bh.boxquantity,'1')) * to_number(t.quantity)) ) as quantity_erp  \n")
    // .append("           , t.quantity  																			\n")
    // .append("           , nvl(t.boxquantity,'1') as boxquantity 											\n")
    // .append("           , trim( replace( t.unit, 'KET_', '' ) ) as unit 										\n")
    // .append("           , to_char( to_date( t.startdate, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt    	\n")
    // .append(" FROM    ketbomcomponent t																\n")
    // .append("            , ketbomheader bh																\n")
    // .append(" WHERE   t.newbomcode = bh.newbomcode												\n")
    // .append("  AND     t.newbomcode = '"+headItem+"'												\n")
    // .append("  AND     t.parentitemcode = '"+parentItem+"'											\n")
    // .append("  AND     t.firstremark = 'NEW'																\n")
    // .append(" order by t.bomlevel, t.itemseq																\n");
    //
    // Kogger.debug(getClass(), ">>>>> comp sql [제품 신규 getCompData] : "+strSql.toString());
    //
    // ppstmt = conn.prepareStatement(strSql.toString());
    // result = ppstmt.executeQuery();
    // strSql.delete(0, strSql.length());
    //
    // while(result.next())
    // {
    // comp_data = new Hashtable();
    // comp_data.put("PBOMCODE", StringUtil.checkNull(result.getString("parentitemcode")));
    // comp_data.put("BOMCODE", StringUtil.checkNull(result.getString("childitemcode")));
    // comp_data.put("QUANTITY", StringUtil.checkNull(result.getString("quantity_erp"))); // 자부품의 수량(quantity)에 모부품의 기준수량(boxQuantity)를 곱한 값
    // comp_data.put("UNIT", (StringUtil.checkNull(result.getString("unit"))).toUpperCase());
    // comp_data.put("STARTDT", StringUtil.checkNull(result.getString("startdt")));
    // comp.add(comp_data);
    // }
    // }
    // catch(Exception e)
    // {
    // Kogger.error(getClass(), e);
    // }
    // finally
    // {
    // try
    // {
    // try{result.close(); } catch(Exception e){}
    // try{ppstmt.close();} catch(Exception e){}
    // }
    // catch(Exception e)
    // {
    // MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
    // mbox.setModal(true);
    // mbox.setVisible(true);
    // }
    // finally
    // {
    // /*if(res != null)
    // {
    // res.freeConnection(registry.getString("plm"), conn);
    // }*/
    // }
    // }
    // return comp;
    // }

    public void setFalseFlag(String headCd, String flag) {
	String headItem = "";
	StringBuffer strSql = new StringBuffer();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));
	    connection.setAutoCommit(false);

	    this.setFalseFlag(headCd, flag, connection);

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

    public void setFalseFlag(String headCd, String flag, Connection connection) throws SQLException {
	String headItem = "";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    StringBuffer strSql = new StringBuffer();

	    strSql.append("UPDATE ketbomheader set transferflag = '" + flag + "' \n");
	    strSql.append(" WHERE ida2a2 = '" + headCd + "' \n");

	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.executeUpdate();
	    strSql.delete(0, strSql.length());
	    Kogger.debug(getClass(), ">>>>> SQL [ERP setFalseFlag] : " + strSql);
	    try {
	    	if (pstmt != null) {
	    		pstmt.close();
	    		pstmt = null;
			}
	    } catch (SQLException sqle) {
	    	sqle.printStackTrace();
	    }

	    strSql.append("SELECT newbomcode \n");
	    strSql.append("  FROM ketbomheader \n");
	    strSql.append(" WHERE ida2a2 = '" + headCd + "' \n");

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());
	    if (rs.next()) {
		headItem = StringUtil.checkNull(rs.getString("newbomcode"));
	    }
	    
	    try {
	    	if (rs != null) {
	    		rs.close();
			    rs = null;
	    }
	    	if (pstmt != null) {
			    pstmt.close();
			    pstmt = null;
			}
	    } catch (SQLException sqle) {
	    	sqle.printStackTrace();
	    }

	    strSql.append("UPDATE ketbomcomponent set transferflag = '" + flag + "' \n");
	    strSql.append(" WHERE newbomcode = '" + headItem + "' \n");
	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.executeUpdate();
	    Kogger.debug(getClass(), ">>>>> SQL [ERP setFalseFlag] : " + strSql);

	}catch(Exception e){
		e.printStackTrace();
	}finally {
	    try {
		if (rs != null) {
		    rs.close();
		    rs = null;
		}
		if (pstmt != null) {
		    pstmt.close();
		    pstmt = null;
		}
	    } catch (SQLException sqle) {

	    }
	}

    }

    // 코드값(OID)으로 itemcode 가져오기.
    public String getItemCode(String headCd) {
	String itemCode = "";
	StringBuffer strSql = new StringBuffer();

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    strSql.append("SELECT  bh.newbomcode as itemcode   	\n").append(" FROM   ketbomheader bh 					\n").append(" WHERE  bh.ida2a2 = '" + headCd + "' 		\n");

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
		    rs = null;
		} catch (Exception e) {
		}
		try {
		    pstmt.close();
		    pstmt = null;
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


    public ArrayList<WTPart> getPartList4SapIFbyPartMaster(Vector<Hashtable<String, String>> vc, Vector<Hashtable<String, String>> vtr) {

	return this.getPartList4SapIFbyPartMaster(vc, vtr, null);
    }

    public ArrayList<WTPart> getPartList4SapIFbyPartMaster(Vector<Hashtable<String, String>> vc, Vector<Hashtable<String, String>> vtr, ArrayList<WTPart> partList) {
	if (partList == null) partList = new ArrayList<WTPart>();

	try {
	    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();

	    int vcSize = (vc != null) ? vc.size() : 0;
	    int vtrSize = (vtr != null) ? vtr.size() : 0;

	    for (int i = 0; i < vcSize; i++) {
		Hashtable<String, String> head = (Hashtable<String, String>) vc.elementAt(i);
		String partNumber = (String) head.get("BOMCODE");
		String rev = (String) head.get("BOMCODE_REVISION");
		String partOid = ketBOMQueryBean.getPartOid(partNumber, rev);

		WTPart wtPart = (WTPart) CommonUtil.getObject(partOid);
		if (!partList.contains(wtPart)) partList.add(wtPart);
	    }
	    // III-3. Comp
	    for (int i = 0; i < vtrSize; i++) {
		Hashtable<String, String> comp = (Hashtable<String, String>) vtr.elementAt(i);
		String partNumber = (String) comp.get("BOMCODE");
		String rev = (String) comp.get("BOMCODE_REVISION");
		String partOid = ketBOMQueryBean.getPartOid(partNumber, rev);

		WTPart wtPart = (WTPart) CommonUtil.getObject(partOid);
		if (!partList.contains(wtPart)) partList.add(wtPart);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return partList;
    }
}
