/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : BomEcoInfoInterfaceQry.java
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

import org.apache.commons.lang.StringUtils;

import wt.part.WTPart;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.util.StringUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;

public class BomEcoInfoInterfaceQry {
    DBConnectionManager res = null;
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Registry registry = Registry.getRegistry("e3ps.bom.bom");
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    public BomEcoInfoInterfaceQry() {
    }

    // 금형부품 인터페이스 - 단레벨
    public Hashtable<String, Vector<Hashtable<String, String>>> getMoldInterfaceData(String econum, String ecoItemCode) {
	Hashtable<String, Vector<Hashtable<String, String>>> total = new Hashtable<String, Vector<Hashtable<String, String>>>();

	String headItem = "";
	String headItemRevision = "";

	String ecoHead = "";
	String strEcoUnit = "";
	StringBuffer strSql = new StringBuffer();
	Vector<Hashtable<String, String>> head = new Vector<Hashtable<String, String>>();
	Vector<Hashtable<String, String>> comp = new Vector<Hashtable<String, String>>();

	String strEcoCode = "";
	boolean delFlag = false;
	ResultSet rsComp = null;

	Hashtable<String, String> head_data = null;
	Hashtable<String, String> comp_data = null;
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    strSql.append("SELECT   bh.ecoheadernumber \n");

	    // 부품번호, 리비전
	    strSql.append("           , bh.ecoitemcode \n");
	    strSql.append("           , bh.bomversion \n");

	    strSql.append("           , bh.quantity \n");
	    strSql.append("           , nvl( bh.BOXQUANTITY, '1' ) as boxquantity \n");
	    strSql.append("	         , trim( replace( bh.unitofquantity, 'KET_', '' ) ) as unitofquantity \n");
	    strSql.append("           , bh.substitudebom \n");
	    strSql.append("           , bh.bomtext \n");
	    strSql.append("           , bh.substitudetext \n");
	    strSql.append("           , bh.bomuse \n");
	    strSql.append("           , to_char(bh.createstampa2,'yyyymmdd') as startdt \n");
	    strSql.append(" FROM	  ketbomecoheader bh \n");
	    // .append("           , ketwfmapprovalmaster wm \n")
	    // .append("           , ketmoldchangeorder c \n")
	    // .append(" WHERE    bh.ida3a7 = wm.ida3owner \n") // 작성자가 아니어도 결재요청할수 있으므로
	    strSql.append(" WHERE    bh.ecoheadernumber = '" + econum + "' \n");
	    // .append("   AND     c.ida2a2 = wm.ida3b4 \n")
	    strSql.append("   AND     bh.ecoitemcode = '" + ecoItemCode + "' \n");

	    Kogger.debug(getClass(), ">>>>> sql [금형 변경 getMoldInterfaceData]: " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    while (rs.next()) {
		head_data = new Hashtable<String, String>();
		ecoHead = StringUtil.checkNull(rs.getString("ecoheadernumber"));

		// 부품번호, 리비전
		headItem = StringUtil.checkNull(rs.getString("ecoitemcode"));
		headItemRevision = StringUtil.checkNull(rs.getString("bomversion"));

		strEcoUnit = StringUtil.checkNull(rs.getString("unitofquantity"));
		if (strEcoUnit != null && !strEcoUnit.equals("")) {
		    strEcoUnit = strEcoUnit.toUpperCase();
		} else {
		    strEcoUnit = "";
		}

		head_data.put("ECOCODE", ecoHead);

		// 부품번호, 리비전
		head_data.put("BOMCODE", headItem);
		head_data.put("BOMCODE_REVISION", headItemRevision);

		head_data.put("QUANTITY", StringUtil.checkNull(rs.getString("quantity")));
		head_data.put("UNIT", strEcoUnit);
		head_data.put("SUBSTITUDEBOM", StringUtil.checkNull(rs.getString("substitudebom")));
		head_data.put("BOMTEXT", StringUtil.checkNull(rs.getString("bomtext")));
		head_data.put("SUBSTITUDETEXT", StringUtil.checkNull(rs.getString("substitudetext")));
		head_data.put("BOMUSE", StringUtil.checkNull(rs.getString("bomuse")));
		head_data.put("STARTDT", StringUtil.checkNull(rs.getString("startdt")));
		head.add(head_data);

		strSql.append("SELECT   t.parentitemcode \n");

		// 부품번호, 리비전
		strSql.append("           , t.childitemcode \n");
		strSql.append("           , t.bomversion \n");

		strSql.append("           , w.name \n");
		strSql.append("           , t.beforequantity \n");
		strSql.append("           , t.afterquantity 	as quantity \n");
		strSql.append("           , trim( replace( t.afterunit, 'KET_', '' ) ) as unit \n");
		strSql.append("           , t.aftermaterial as material \n");
		strSql.append("           , t.afterhardnessfrom as hardnessfrom \n");
		strSql.append("           , t.afterhardnessto as hardnessto \n");
		strSql.append("           , to_char( to_date( t.afterdesigndate , 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt \n");
		strSql.append("           , t.ecocode	\n");
		strSql.append("  FROM   ketbomecocomponent t , wtpartmaster w \n");
		strSql.append("  WHERE t.childitemcode = w.wtpartnumber \n");
		strSql.append("  AND    t.ecoheadernumber = '" + ecoHead + "' \n");
		strSql.append("  AND    t.ecoitemcode = '" + headItem + "' \n");

		// '변경, 추가, 삭제'된 것만 가져오게 한다.
		strSql.append("  AND t.ecocode is not null  \n");

		strSql.append(" order by t.bomlevel, t.itemseq \n");

		Kogger.debug(getClass(), ">>>>> sql 2 [금형 변경 getMoldInterfaceData] : " + strSql.toString());
		pstmt = connection.prepareStatement(strSql.toString());
		rsComp = pstmt.executeQuery();
		strSql.delete(0, strSql.length());

		while (rsComp.next()) {
		    delFlag = false;

		    strEcoUnit = StringUtil.checkNull(rsComp.getString("unit"));
		    if (strEcoUnit != null && !strEcoUnit.equals("")) {
			strEcoUnit = strEcoUnit.toUpperCase();
		    } else {
			strEcoUnit = "";
		    }

		    // ecoCode 컬럼이 비어있는 경우는 변경이 일어나지 않은 것임
		    strEcoCode = StringUtil.checkNull(rsComp.getString("ecocode"));
		    if (!strEcoCode.equals("")) {
			delFlag = true;
		    }

		    // [2011-04-21] 임승영D 요구사항 반영 :: 금형 변경 BOM은 전체 Die 셋트를 모두 ERP로 전송함
		    // if(delFlag)
		    // {
		    comp_data = new Hashtable<String, String>();
		    comp_data.put("PBOMCODE", StringUtil.checkNull(rsComp.getString("parentitemcode")));

		    // 부품번호, 리비전
		    comp_data.put("BOMCODE", StringUtil.checkNull(rsComp.getString("childitemcode")));
		    comp_data.put("BOMCODE_REVISION", StringUtil.checkNull(rsComp.getString("bomversion")));

		    comp_data.put("DESC", StringUtil.checkNull(rsComp.getString("name")));
		    comp_data.put("BEFORQTY", StringUtil.checkNull(rsComp.getString("beforequantity")));
		    comp_data.put("QUANTITY", StringUtil.checkNull(rsComp.getString("quantity")));
		    comp_data.put("UNIT", strEcoUnit);
		    comp_data.put("MATERIAL", StringUtil.checkNull(rsComp.getString("material")));
		    comp_data.put("HARDNESSFROM", StringUtil.checkNull(rsComp.getString("hardnessfrom")));
		    comp_data.put("HARDNESSTO", StringUtil.checkNull(rsComp.getString("hardnessto")));
		    comp_data.put("STARTDT", StringUtil.checkNull(rsComp.getString("startdt")));
		    comp_data.put("ECOCODE", strEcoCode);
		    comp.add(comp_data);
		    // }
		}
		if (rsComp != null) {
		    try {
			rsComp.close();
		    } catch (Exception e) {
		    }
		}
		if (pstmt != null) {
		    try {
			pstmt.close();
		    } catch (Exception e) {
		    }
		}
	    }
	    Kogger.debug(getClass(), "%INTERFACE DATA% 금형 변경 head : " + head);
	    Kogger.debug(getClass(), "%INTERFACE DATA% 금형 변경 comp : " + comp);

	    total.put("HEAD", head);
	    total.put("COMP", comp);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		} catch (Exception e) {
		}
		if (rsComp != null) {
		    try {
			rsComp.close();
		    } catch (Exception e) {
		    }
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

	return total;
    }

    // 제품 인터페이스 - 멀티레벨
    public Hashtable<String, Vector<Hashtable<String, String>>> getGenInterfaceData(String econum, String ecoItemCode, String ecoCode) {
	Hashtable<String, Vector<Hashtable<String, String>>> total = new Hashtable<String, Vector<Hashtable<String, String>>>();

	String headItem = "";
	String headItemRevision = "";

	StringBuffer strSql = new StringBuffer();
	Vector<Hashtable<String, String>> head = new Vector<Hashtable<String, String>>();
	Vector<Hashtable<String, String>> comp = new Vector<Hashtable<String, String>>();

	Hashtable<String, String> head_data = null;
	Hashtable<String, String> comp_data = null;
	String ecoHead = "";
	String p_bomuse = "";
	String p_substitubom = "";
	String p_boxquantity = "";
	String p_beforeBoxquantity = "";
	String p_unit = "";
	String p_bomtext = "";
	String p_date = "";

	String strUnit = "";
	String strEcoCode = "";
	boolean delFlag = false;
	boolean isHeadOnlyChg = false;

	Double d_boxquantity = 0.0;
	Double d_beforeBoxquantity = 0.0;

	ResultSet rsComp = null;

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    // 1. 헤더정보 가져오는 SQL
	    strSql.append(" SELECT   bh.ecoheadernumber \n");
	    strSql.append("            , bh.ecoitemcode \n");
	    strSql.append("            , bh.bomversion \n");
	    strSql.append("            , bh.quantity \n");
	    strSql.append("            , nvl( bh.BOXQUANTITY, '1' ) as boxquantity \n");
	    strSql.append("            , nvl( bh.ATTRIBUTE2, '1' ) as beforeBoxquantity \n");
	    strSql.append("            , trim( replace( bh.unitofquantity, 'KET_', '' ) ) as unitofquantity \n");
	    strSql.append("            , bh.substitudebom \n");
	    strSql.append("            , bh.bomtext \n");
	    strSql.append("            , bh.substitudetext \n");
	    strSql.append("            , bh.bomuse \n");
	    strSql.append("            , to_char(bh.createstampa2,'yyyymmdd') as startdt \n");
	    strSql.append(" FROM \n");

	    strSql.append("       ( \n");
	    strSql.append("        SELECT ecoheadernumber, ecoitemcode, bomversion, quantity \n");
	    strSql.append("             , BOXQUANTITY, ATTRIBUTE2, unitofquantity, substitudebom, bomtext, substitudetext, bomuse, createstampa2 \n");
	    strSql.append("          FROM ketbomecoheader \n");
	    strSql.append("     UNION ALL \n");
	    strSql.append("        SELECT ecoheadernumber, ecoitemcode, bomversion, quantity \n");
	    strSql.append("             , BOXQUANTITY, ATTRIBUTE2, unitofquantity, substitudebom, bomtext, substitudetext, bomuse, createstampa2 \n");
	    strSql.append("          FROM ketbomheader \n");
	    strSql.append("       ) bh \n");

	    // .append("            , ketwfmapprovalmaster wm \n")
	    // .append("            , ketprodchangeorder c \n")
	    // .append(" WHERE    bh.ida3a7 = wm.ida3owner \n") // 작성자가 아니어도 결재요청할수 있으므로
	    strSql.append(" WHERE    bh.ecoheadernumber ='" + econum + "' \n");
	    // .append("   AND     c.ida2a2 = wm.ida3b4 \n")
	    strSql.append("   AND     bh.ecoitemcode = '" + ecoItemCode + "' \n");

	    Kogger.debug(getClass(), ">>>>> sql 1 [제품 변경 getGenInterfaceData] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    WTPart wtpart_Header = null;
	    String spPartType_Header = null;
	    PartTypeEnum ptype_Header = null;

	    WTPart wtpart_child = null;
	    String spPartType_child = null;
	    PartTypeEnum ptype_child = null;

	    while (rs.next()) {
		head_data = new Hashtable<String, String>();
		ecoHead = StringUtil.checkNull(rs.getString("ecoheadernumber"));

		// 부품번호, 리비전
		headItem = StringUtil.checkNull(rs.getString("ecoitemcode"));
		headItemRevision = StringUtil.checkNull(rs.getString("bomversion"));

		p_bomuse = StringUtil.checkNull(rs.getString("bomuse"));
		p_substitubom = StringUtil.checkNull(rs.getString("substitudebom"));
		p_boxquantity = StringUtil.checkNull(rs.getString("boxquantity"));
		p_beforeBoxquantity = StringUtil.checkNull(rs.getString("beforeBoxquantity"));
		p_unit = (StringUtil.checkNull(rs.getString("unitofquantity"))).toUpperCase();
		p_bomtext = StringUtil.checkNull(rs.getString("bomtext"));
		p_date = StringUtil.checkNull(rs.getString("startdt"));

		// 부품번호, 리비전
		head_data.put("BOMCODE", headItem);
		head_data.put("BOMCODE_REVISION", headItemRevision);

		head_data.put("QUANTITY", p_boxquantity); // BoxQuantity 정보가 헤더의 기준수량이 되어야 한다.
		head_data.put("BEFOREQUANTITY", p_beforeBoxquantity); // BoxQuantity 이전 정보
		head_data.put("UNIT", p_unit);
		head_data.put("SUBSTITUDEBOM", p_substitubom);
		head_data.put("BOMTEXT", p_bomtext);
		head_data.put("SUBSTITUDETEXT", StringUtil.checkNull(rs.getString("substitudetext")));
		head_data.put("BOMUSE", p_bomuse);
		head_data.put("STARTDT", p_date);
		head_data.put("ECOCODE", ""); // 헤더는 삭제되지 않으므로 빈 문자열로 넘긴다.
		head.add(head_data);

		// 헤더의 포장수량 변경여부 확인
		d_boxquantity = new Double(p_boxquantity);
		d_beforeBoxquantity = new Double(p_beforeBoxquantity);
		Kogger.debug(getClass(), "########## d_beforeBoxquantity : " + d_beforeBoxquantity);
		Kogger.debug(getClass(), "########## d_boxquantity : " + d_boxquantity);

		wtpart_Header = PartBaseHelper.service.getLatestPart(headItem);
		spPartType_Header = PartSpecGetter.getPartSpec(wtpart_Header, PartSpecEnum.SpPartType);
		ptype_Header = PartTypeEnum.toPartTypeEnum(spPartType_Header);

		if (d_boxquantity != d_beforeBoxquantity && PartTypeEnum.FERT == ptype_Header) {
		    isHeadOnlyChg = true;
		}

		// 2. 최상위 모부품 하위 1레벨만 가져오는 SQL (변경은 단레벨이므로, 최상위 이하 1레벨만 수정가능함)
		strSql.append("SELECT  t.parentitemcode \n");
		// 부품번호, 리비전
		strSql.append("          , t.childitemcode \n");
		strSql.append("          , t.bomversion \n");
		strSql.append("          , decode( substr( t.childitemcode,1,1), 'P', t.afterquantity, to_char( to_number( nvl( bh.boxquantity, '1' ) ) * to_number(DECODE(ECOCODE,'',nvl(beforequantity,1), nvl(t.afterquantity,1)) ) ) ) as quantity_erp \n");
		strSql.append("          , t.beforequantity \n");
		strSql.append("          , t.afterquantity as quantity \n");
		strSql.append("          , nvl( t.afterboxquantity, '1' ) as boxquantity \n");
		strSql.append("          , trim( replace( t.afterunit , 'KET_', '' ) ) as unit \n");
		strSql.append("          , to_char( to_date( t.afterstartdate , 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt \n");
		strSql.append("          , t.ecocode, t.afterict, t.beforeict  \n");
		strSql.append(" FROM   ketbomecocomponent t \n");

		strSql.append("     , ( \n");
		strSql.append("        SELECT ecoheadernumber, ecoitemcode, bomversion, quantity \n");
		strSql.append("             , BOXQUANTITY, ATTRIBUTE2, unitofquantity, substitudebom, bomtext, substitudetext, bomuse, createstampa2 \n");
		strSql.append("          FROM ketbomecoheader \n");
		strSql.append("     UNION ALL \n");
		strSql.append("        SELECT ecoheadernumber, ecoitemcode, bomversion, quantity \n");
		strSql.append("             , BOXQUANTITY, ATTRIBUTE2, unitofquantity, substitudebom, bomtext, substitudetext, bomuse, createstampa2 \n");
		strSql.append("          FROM ketbomheader \n");
		strSql.append("       ) bh \n");

		strSql.append("  WHERE   t.ecoheadernumber = bh.ecoheadernumber \n");
		strSql.append("  AND      t.ecoitemcode = bh.ecoitemcode \n");
		strSql.append("  AND      t.ecoheadernumber = '" + ecoHead + "' \n");
		// strSql.append("  AND      t.ecoitemcode = '" + headItem + "' \n");
		strSql.append("  AND      t.parentitemcode = '" + headItem + "' \n");

		if (StringUtils.isNotEmpty(ecoCode)) {

		    if ("Remove".equals(ecoCode)) {
			strSql.append("  AND      t.ecocode = 'Remove' \n");
		    } else {
			strSql.append("  AND      (t.ecocode != 'Remove' or t.ecocode is null) \n");
		    }

		}

		// '변경, 추가, 삭제'된 것만 가져오게 한다.
		// 근데 이렇게 하면 헤더 제품 포장수량 정보가 변경될 경우 반제품 포장수량을 erp에 보낼수가 없다.. 때문에 주석처리한다 젠장 2015.10.07 by 황정태
		// strSql.append("  AND t.ecocode is not null  \n");

		strSql.append(" order by  decode(ecocode,'Remove',1,'Add',2,'Update',3,4), t.bomlevel, t.itemseq \n");

		Kogger.debug(getClass(), ">>>>> sql 2 [제품 변경 getGenInterfaceData] : " + strSql.toString());
		pstmt = connection.prepareStatement(strSql.toString());
		rsComp = pstmt.executeQuery();
		// ResultSet rs2 = pstmt.executeQuery(); // 해당 자부품들이 헤더인지(하위에 자부품을 가지는지) 확인하기위해 저장해놓음
		strSql.delete(0, strSql.length());

		while (rsComp.next()) {
		    delFlag = false;

		    strUnit = StringUtil.checkNull(rsComp.getString("unit"));
		    if (strUnit != null && !strUnit.equals("")) {
			strUnit = strUnit.toUpperCase();
		    } else {
			strUnit = "";
		    }

		    // ecoCode 컬럼이 비어있는 경우는 변경이 일어나지 않은 것임
		    strEcoCode = StringUtil.checkNull(rsComp.getString("ecocode"));
		    if (!strEcoCode.equals("")) {
			delFlag = true;
		    }

		    // 모자 속성정보가 변경되었거나(delFlag) , 헤더의 포장수량 정보가 변경된 경우(isHeadOnlyChg)
		    if (delFlag || isHeadOnlyChg) {

			String compEcocode = StringUtil.checkNull(rsComp.getString("ecocode"));
			String childitemcode = StringUtil.checkNull(rsComp.getString("childitemcode"));

			// 제품 수량 변경시, 하위 1레벨의 반제품의 수량을 SAP에 반영해주기 위한 로직추가 2015.10.08 by 황정태
			wtpart_child = PartBaseHelper.service.getLatestPart(childitemcode);
			spPartType_child = PartSpecGetter.getPartSpec(wtpart_child, PartSpecEnum.SpPartType);
			ptype_child = PartTypeEnum.toPartTypeEnum(spPartType_child);

			if (delFlag || (isHeadOnlyChg && PartTypeEnum.HALB == ptype_child)) {

			    comp_data = new Hashtable<String, String>();
			    comp_data.put("PBOMCODE", StringUtil.checkNull(rsComp.getString("parentitemcode")));
			    // 부품번호, 리비전

			    comp_data.put("BOMCODE", childitemcode);
			    comp_data.put("BOMCODE_REVISION", StringUtil.checkNull(rsComp.getString("bomversion")));
			    comp_data.put("BEFORQTY", StringUtil.checkNull(rsComp.getString("beforequantity")));
			    comp_data.put("QUANTITY", StringUtil.checkNull(rsComp.getString("quantity_erp"))); // 자부품의 수량(quantity)에 모부품의
				                                                                               // 기준수량(boxQuantity)를 곱한 값
			    comp_data.put("UNIT", strUnit);
			    comp_data.put("STARTDT", StringUtil.checkNull(rsComp.getString("startdt")));

			    String afterict = StringUtil.checkNull(rsComp.getString("afterict"));
			    String beforeict = StringUtil.checkNull(rsComp.getString("beforeict"));

			    if (isHeadOnlyChg && StringUtils.isEmpty(compEcocode) && PartTypeEnum.HALB == ptype_child) {// 제품수량 변경되었고 반제품에
				                                                                                        // 대한 bom상의 변경 또는
				                                                                                        // 개정진행이 없을때
				// 제품수량 변경시 하위 1레벨 반제품 ECOCODE를 Update로 설정한다.
				compEcocode = "Update";
				// afterict와 beforeict 다를 경우 bom이 삭제된다. 어차피 반제품은 무조건 재고품목이므로 진행해야하므로 L로 세팅한다.
				afterict = "L";
				beforeict = "L";
			    }
			    comp_data.put("ECOCODE", compEcocode);
			    comp_data.put("ICT", afterict);
			    comp_data.put("DELICT", beforeict);

			    Kogger.debug(getClass(), "ICT======================>" + StringUtil.checkNull(rsComp.getString("afterict")));
			    comp.add(comp_data);
			}
		    }
		}
		if (rsComp != null) {
		    try {
			rsComp.close();
		    } catch (Exception e) {
		    }
		}
	    }
	    // Kogger.debug(getClass(), "@@@@@@ 2번");
	    // String parentitemcode = "";
	    // String childitemcode = "";
	    // String compBeforQty = "";
	    // String compBoxQty = "";
	    // String compUnit = "";
	    // String startDt = "";
	    // Kogger.debug(getClass(), "@@@@@@ rs2 :  " + rs2);
	    // // 해당 자부품들이 아래 자부품을 가지는지 확인
	    // while(rs2.next())
	    // {
	    // parentitemcode = StringUtil.checkNull(rs2.getString("parentitemcode"));
	    // Kogger.debug(getClass(), "@@@@ parentitemcode : " + parentitemcode);
	    // childitemcode = StringUtil.checkNull(rs2.getString("childitemcode"));
	    // Kogger.debug(getClass(), "@@@@ childitemcode : " + childitemcode);
	    // compBeforQty = StringUtil.checkNull(rs2.getString("beforequantity"));
	    // Kogger.debug(getClass(), "@@@@ compBeforQty : " + compBeforQty);
	    // compBoxQty = StringUtil.checkNull(rs2.getString("boxquantity"));
	    // Kogger.debug(getClass(), "@@@@ compBoxQty : " + compBoxQty);
	    // compUnit = StringUtil.checkNull(rs2.getString("unit"));
	    // if ( compUnit != null && !compUnit.equals("") ) {
	    // compUnit = compUnit.toUpperCase();
	    // } else {
	    // compUnit = "";
	    // }
	    // Kogger.debug(getClass(), "@@@@ compUnit : " + compUnit);
	    // startDt = StringUtil.checkNull(rs2.getString("startdt"));
	    // Kogger.debug(getClass(), "@@@@ startDt : " + startDt);
	    //
	    // // 현재 자부품인 목록중에 하위에 자부품을 가지는 경우 헤더로 추가한다
	    // Vector compvc = this.getCompData(connection, ecoHead, childitemcode);
	    // Kogger.debug(getClass(), "@@@@@ compvc : " + compvc);
	    // if(compvc.size() > 0)
	    // {
	    // head_data.put("BOMCODE", childitemcode);
	    // head_data.put("BEFORQTY", compBeforQty);
	    // head_data.put("QUANTITY", compBoxQty); // 기존의 수량정보는 모-자 수량정보이므로, BoxQuantity 정보가 헤더의 기준수량이 되어야 한다.
	    // head_data.put("UNIT", compUnit);
	    // head_data.put("SUBSTITUDEBOM", p_substitubom);
	    // head_data.put("BOMTEXT", p_bomtext);
	    // head_data.put("SUBSTITUDETEXT", getSubTrans(connection, ecoHead, parentitemcode, childitemcode));
	    // head_data.put("BOMUSE", p_bomuse);
	    // head_data.put("STARTDT", startDt);
	    // head.add(head_data);
	    // }
	    // }
	    Kogger.debug(getClass(), "%INTERFACE DATA% 제품 변경 head : " + head);
	    Kogger.debug(getClass(), "%INTERFACE DATA% 제품 변경 comp : " + comp);

	    total.put("HEAD", head);
	    total.put("COMP", comp);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		try {
		    rs.close();
		} catch (Exception e) {
		}
		if (rsComp != null) {
		    try {
			rsComp.close();
		    } catch (Exception e) {
		    }
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

	return total;
    }

    // KETBomEcoSubstitute 정보 가져오기
    public String getSubTrans(Connection conn, String ecoHead, String parentItem, String childItem) {
	String strReturn = "";
	StringBuffer strSql = new StringBuffer();
	ResultSet rs = null;

	try {

	    strSql.append("SELECT   substituteitemcode  								\n").append(" FROM    ketbomecosubstitute s							\n").append(" WHERE   s.ecoheadernumber = '" + ecoHead + "'			\n")
		    .append("   AND   s.parentitemcode = '" + parentItem + "'			\n").append("   AND   s.childitemcode = '" + childItem + "'				\n");

	    Kogger.debug(getClass(), ">>>>> sql [변경 대체부품정보 getSubTrans] : " + strSql.toString());
	    pstmt = conn.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		strReturn += rs.getString("substituteitemcode") + ",";
	    }

	    if (strReturn.indexOf(",") > 0) {
		strReturn = strReturn.substring(0, strReturn.length() - 1);
	    }
	    Kogger.debug(getClass(), "@@@@ strReturn [getSubTrans]  " + ecoHead + "(" + parentItem + "/" + childItem + ") : " + strReturn);
	} catch (SQLException e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (rs != null)
		try {
		    rs.close();
		} catch (SQLException e) {
		    Kogger.error(getClass(), e);
		}
	}
	return strReturn;
    }

    // 제품일때 component 하위 가져오는것.
    public Vector getCompData(Connection conn, String ecoHead, String parentItem) {
	PreparedStatement ppstmt = null;
	ResultSet result = null;

	StringBuffer strSql = new StringBuffer();
	Vector comp = new Vector();
	Hashtable comp_data = null;
	try {
	    strSql.append(" SELECT   t.parentitemcode  																		\n")
		    .append("            , t.childitemcode 																			\n")
		    .append("            , decode( substr( t.childitemcode,1,3), 'P10', t.afterquantity, 'P20', t.afterquantity, to_char( to_number( nvl( bh.boxquantity, '1' ) ) * to_number( t.afterquantity ) ) ) as quantity_erp  \n")
		    .append("            , t.beforequantity  																		\n").append("            , t.afterquantity as quantity																\n")
		    .append("            , nvl( t.afterboxquantity, '1' ) as boxquantity 											\n").append("            , trim( replace( t.afterunit, 'KET_', '' ) ) as unit  										\n")
		    .append("            , to_char( to_date( t.afterstartdate , 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt 		\n").append("            , t.ecocode, , t.afterict																					\n")
		    .append("  FROM    ketbomecocomponent t																	\n").append("            , ketbomecoheader bh																	\n")
		    .append("  WHERE   t.ecoheadernumber = bh.ecoheadernumber										\n").append("   AND     t.ecoheadernumber = '" + ecoHead + "'													\n")
		    .append("   AND     t.parentitemcode = '" + parentItem + "'													\n").append("  order by t.bomlevel, t.itemseq																		\n");

	    Kogger.debug(getClass(), ">>>>> comp sql [제품 변경 getCompData] : " + strSql.toString());

	    ppstmt = conn.prepareStatement(strSql.toString());
	    result = ppstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    while (result.next()) {
		comp_data = new Hashtable();
		comp_data.put("PBOMCODE", StringUtil.checkNull(result.getString("parentitemcode")));
		comp_data.put("BOMCODE", StringUtil.checkNull(result.getString("childitemcode")));
		comp_data.put("BEFORQTY", StringUtil.checkNull(result.getString("beforequantity")));
		comp_data.put("QUANTITY", StringUtil.checkNull(result.getString("quantity_erp"))); // 자부품의 수량(quantity)에 모부품의
		                                                                                   // 기준수량(boxQuantity)를 곱한 값
		comp_data.put("UNIT", (StringUtil.checkNull(result.getString("unit"))).toUpperCase());
		comp_data.put("STARTDT", StringUtil.checkNull(result.getString("startdt")));
		comp_data.put("ECOCODE", StringUtil.checkNull(result.getString("ecocode")));
		comp_data.put("ICT", StringUtil.checkNull(result.getString("afterict")));
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

    public void setFalseFlag(String econum, String ecoItemCode, String flag) {
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));
	    connection.setAutoCommit(false);

	    this.setFalseFlag(econum, ecoItemCode, flag, connection);

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

    public void setFalseFlag(String econum, String ecoItemCode, String flag, Connection connection) throws SQLException {
	StringBuffer strSql = new StringBuffer();

	/*
	 * // I. 초도일 경우 strSql.append("UPDATE  ketbomheader set transferflag = '" + flag + "' \n");
	 * strSql.append(" WHERE   ecoheadernumber = '" + econum + "' \n"); strSql.append("   AND    ecoitemcode = '" + ecoItemCode +
	 * "' \n"); pstmt = connection.prepareStatement(strSql.toString()); int updateCnt = pstmt.executeUpdate(); strSql.delete(0,
	 * strSql.length()); Kogger.debug(getClass(), ">>>>> SQL [ERP setFalseFlag] : " + strSql); Kogger.debug(getClass(),
	 * ">>>>>     [ERP updateCnt] : " + updateCnt);
	 */

	// II. 초도가 아닐 경우
	strSql.append("UPDATE  ketbomecoheader set transferflag = '" + flag + "' \n");
	strSql.append(" WHERE   ecoheadernumber = '" + econum + "' \n");
	strSql.append("   AND    ecoitemcode = '" + ecoItemCode + "' \n");
	try {
	    pstmt = connection.prepareStatement(strSql.toString());
	    int updateCnt = pstmt.executeUpdate();
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	    strSql.delete(0, strSql.length());
	    Kogger.debug(getClass(), ">>>>> SQL [ERP setFalseFlag] : " + strSql);
	    Kogger.debug(getClass(), ">>>>>     [ERP updateCnt] : " + updateCnt);

	    // String ecoHead = "";
	    // strSql.append("SELECT  ecoheadernumber \n")
	    // .append(" FROM   ketbomecoheader \n")
	    // .append(" WHERE  ida2a2 = '"+headCd+"' \n");
	    // Kogger.debug(getClass(), ">>>>> SQL [ERP setFalseFlag] : " + strSql);
	    //
	    // pstmt = connection.prepareStatement(strSql.toString());
	    // rs = pstmt.executeQuery();
	    // strSql.delete(0, strSql.length());
	    // if(rs.next())
	    // {
	    // ecoHead = StringUtil.checkNull(rs.getString("ecoheadernumber"));
	    // }

	    strSql.append("UPDATE  ketbomecocomponent SET transferflag = '" + flag + "' \n");
	    strSql.append(" WHERE   ecoheadernumber = '" + econum + "' \n");
	    strSql.append("    AND   ecoitemcode = '" + ecoItemCode + "' \n");
	    pstmt = connection.prepareStatement(strSql.toString());
	    updateCnt = pstmt.executeUpdate();
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	    Kogger.debug(getClass(), ">>>>> SQL [ERP setFalseFlag] : " + strSql);
	    Kogger.debug(getClass(), ">>>>>     [ERP updateCnt] : " + updateCnt);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
		pstmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    // 코드값으로 ItemCode 가져오기.
    public ArrayList<Hashtable> getItemCode(String headCd) {
	StringBuffer strSql = new StringBuffer();
	ArrayList<Hashtable> arrList = new ArrayList<Hashtable>();

	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    strSql.append("SELECT * \n");
	    strSql.append("  FROM ( \n");

	    strSql.append("SELECT bh.classnamea2a2||':'||bh.ida2a2 as headerOid \n");
	    strSql.append("     , bh.ida2a2 as headerLongValue \n");
	    strSql.append("     , bh.ecoitemcode as itemcode \n");
	    strSql.append("     , bh.ecoheadernumber as econum \n");
	    strSql.append("     , c.classnamea2a2||':'||c.ida2a2 as ecoOid \n");
	    strSql.append("  FROM \n");

	    strSql.append("       ( \n");
	    strSql.append("        SELECT classnamea2a2, ida2a2, ecoitemcode, ecoheadernumber \n");
	    strSql.append("          FROM ketbomecoheader \n");
	    strSql.append("     UNION ALL \n");
	    strSql.append("        SELECT classnamea2a2, ida2a2, ecoitemcode, ecoheadernumber \n");
	    strSql.append("          FROM ketbomheader \n");
	    strSql.append("       ) bh \n");

	    strSql.append("     , ketprodchangeorder c \n");
	    strSql.append(" WHERE bh.ecoheadernumber = c.ecoid \n");
	    strSql.append("   AND c.ida2a2 = '" + headCd + "' \n");
	    strSql.append("UNION \n");
	    strSql.append("SELECT bh.classnamea2a2||':'||bh.ida2a2 as headerOid \n");
	    strSql.append("     , bh.ida2a2 as headerLongValue \n");
	    strSql.append("     , bh.ecoitemcode as itemcode \n");
	    strSql.append("     , bh.ecoheadernumber as econum \n");
	    strSql.append("     , c.classnamea2a2||':'||c.ida2a2 as ecoOid \n");
	    strSql.append("  FROM \n");

	    strSql.append("       ( \n");
	    strSql.append("        SELECT classnamea2a2, ida2a2, ecoitemcode, ecoheadernumber \n");
	    strSql.append("          FROM ketbomecoheader \n");
	    strSql.append("     UNION ALL \n");
	    strSql.append("        SELECT classnamea2a2, ida2a2, ecoitemcode, ecoheadernumber \n");
	    strSql.append("          FROM ketbomheader \n");
	    strSql.append("       ) bh \n");

	    strSql.append("     , ketmoldchangeorder c \n");
	    strSql.append(" WHERE bh.ecoheadernumber = c.ecoid \n");
	    strSql.append("   AND c.ida2a2 = '" + headCd + "' \n");
	    /*
	     * BOM 편집시 초도 금형부품이 헤더에 포함되지 않고 COMPONENT에 INSERT되는 경우가 발생.. 이렇게 되면 ERP에 BOM만 생성되고 자재마스터는 생성되지 않는다 이를 커버하기 위하여 COMPONENT쪽 데이터도
	     * 긁어오도록 UNION 추가함 2015.10.28 BY 황정태
	     */
	    strSql.append("UNION \n");
	    strSql.append("SELECT 'componentOid_None_'||0 AS headerOid,         \n");
	    strSql.append("        0 AS headerLongValue,								 \n");
	    strSql.append("        A.CHILDITEMCODE AS itemcode,                       \n");
	    strSql.append("        A.ecoheadernumber AS econum,                          \n");
	    strSql.append("        B.classnamea2a2 || ':' || B.ida2a2 AS ecoOid          \n");
	    strSql.append("  FROM KETBOMECOCOMPONENT A, KETMOLDCHANGEORDER B       \n");
	    strSql.append(" WHERE B.ida2a2 = '" + headCd + "' \n");
	    strSql.append("   AND ECOHEADERNUMBER = B.ECOID						 \n");
	    strSql.append("   AND CHILDITEMCODE NOT IN (							 \n");
	    strSql.append("SELECT ECOITEMCODE										 \n");
	    strSql.append("  FROM (SELECT CLASSNAMEA2A2, \n");
	    strSql.append("                      IDA2A2,  \n");
	    strSql.append("                 ECOITEMCODE,  \n");
	    strSql.append("              ECOHEADERNUMBER  \n");
	    strSql.append("         FROM KETBOMECOHEADER  \n");
	    strSql.append("    UNION ALL  \n");
	    strSql.append("       SELECT CLASSNAMEA2A2,  \n");
	    strSql.append("              IDA2A2,  \n");
	    strSql.append("              ECOITEMCODE,  \n");
	    strSql.append("              ECOHEADERNUMBER  \n");
	    strSql.append("         FROM KETBOMHEADER) BH,  \n");
	    strSql.append("KETMOLDCHANGEORDER C  \n");
	    strSql.append("WHERE BH.ECOHEADERNUMBER = C.ECOID AND C.IDA2A2 = '" + headCd + "') \n");
	    strSql.append(" ) \n");
	    strSql.append(" ORDER BY itemcode ASC \n");

	    Kogger.debug(getClass(), ">>>> SQL [getItemCode] : " + strSql);

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    while (rs.next()) {
		Hashtable ht = new Hashtable();
		ht.put("headerOid", StringUtil.checkNull(rs.getString("headerOid")));
		ht.put("headerLongValue", StringUtil.checkNull(rs.getString("headerLongValue")));
		ht.put("itemcode", StringUtil.checkNull(rs.getString("itemcode")));
		ht.put("econum", StringUtil.checkNull(rs.getString("econum")));
		ht.put("ecoOid", StringUtil.checkNull(rs.getString("ecoOid")));

		arrList.add(ht);
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
	return arrList;
    }
}
