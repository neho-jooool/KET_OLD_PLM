/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : WFBomEcoPartUsageQry.java
 * 작성자 : Shin.
 * 작성일자 : 2010. 12. 16.
 */
package e3ps.bom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.pom.WTConnection;
import wt.util.WTException;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMECOQueryBean;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.beans.EcmUtil;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class WFBomEcoPartUsageQry {

    Registry                registry        = Registry.getRegistry("e3ps.bom.bom");
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    Config                  conf            = ConfigImpl.getInstance();
    boolean                 mailEnable      = conf.getBoolean("e3ps.mail.enable");

    public WFBomEcoPartUsageQry() {
    }

    public boolean getPartUsageResult(String ecoLongValue) {
	Kogger.debug(getClass(), "######################## KETPartUsageLink 입력 시작!!!! ############################");
	boolean reSult = false;

	KETBomEcoHeader bomHeader = null;
	KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	KETBOMECOQueryBean bean = new KETBOMECOQueryBean();
	BOMSearchUtilDao dao = new BOMSearchUtilDao();
	KETBOMQueryBean bomqr = new KETBOMQueryBean();
	String ecoNumber = "";

	String strType = ""; // 제품:P, 금형:D, 금형부품:M
	Vector vc = null;

	try {
	    ArrayList<Hashtable> ecoList = getItemCode(ecoLongValue); // ECO에 부품이 여러개가 지정될 수 있으므로
	    // Object ecoObj = KETObjectUtil.getObject(headCd);

	    for (int inx = 0; inx < ecoList.size(); inx++) {

		Hashtable headHt = ecoList.get(inx);
		String itemCd = (String) headHt.get("itemcode");
		String econum = (String) headHt.get("econum");

		String headerOid = (String) headHt.get("oid");
		bomHeader = bean.getBOMECOHeader(econum, itemCd); // 대상 BOM Header 객체

		WTPart part = kh.searchItem(itemCd);
		strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
		Kogger.debug(getClass(), "################## KETPartUsageLink --> " + inx + " 번 째 입력 시작!!!! ##################");
		Kogger.debug(getClass(), "@@@@ itemCd : " + itemCd);
		Kogger.debug(getClass(), "@@@@ econum : " + econum);
		Kogger.debug(getClass(), "@@@@ strType : " + strType);

		if (PartUtil.isProductType(strType)) // if( strType.equals("P") )
		{
		    vc = this.getGenData(itemCd, econum);

		    // ArrayList arr = bomqr.getParentUsagelink(itemCd, econum);

		    // for (int i = 0; i < arr.size(); i++) {
		    // KETPartUsageLink ulink = (KETPartUsageLink) arr.get(i);

		    // Hashtable parent_data = new Hashtable();
		    // parent_data.put("MATNR", StringUtil.checkNull(ulink.getParentItemCode()));
		    // parent_data.put("IDNRK", StringUtil.checkNull(ulink.getChildItemCode()));
		    // parent_data.put("POSNR", Integer.toString(ulink.getItemSeq()));
		    // parent_data.put("MENGE", StringUtil.checkNull(ulink.getQuantity()));
		    // parent_data.put("BMENG", StringUtil.checkNull(ulink.getBoxQuantity()));
		    // parent_data.put("MEINS", StringUtil.checkNull(ulink.getUnit()));
		    // parent_data.put("DATUV", StringUtil.checkNull(ulink.getStartDate()));
		    // parent_data.put("CHILDV", StringUtil.checkNull(getItemVersion(itemCd)));
		    // parent_data.put("NBOMV", StringUtil.checkNull(getItemVersion(itemCd)));
		    // parent_data.put("ECOCODE", "VersionUp");

		    // parent_data.put("ICT", StringUtil.checkNull(ulink.getIct()));
		    // parent_data.put("REFTOP", StringUtil.checkNull(ulink.getRefTop()));
		    // parent_data.put("REFBOTTOM", StringUtil.checkNull(ulink.getRefBottom()));

		    // vc.add(parent_data);

		    // Kogger.debug(getClass(), "KETPartUsageLink======================>" + parent_data.toString());

		    // }

		    if (KETBomHelper.service.setWfKetPartUsageLinkEco(vc, 1)) {
			reSult = true;
		    }

		    if (reSult) {
			if (getSubTrans(econum, itemCd)) {
			    reSult = true;
			}
		    }
		}
		else if (strType.equals("D"))// || strType.equals("M")) //M이 올수 있을려나...??
		{
		    vc = this.getMoldData(itemCd, econum);

		    if (KETBomHelper.service.setWfKetPartUsageLinkEco(vc, 2)) {
			reSult = true;
		    }

		}
		else {
		    Kogger.debug(getClass(), "지정된 값이 없는 PartType 입니다.");
		}

		if (reSult) {

		    // 헤더 상태변경
		    Kogger.debug(getClass(), "@@@@@@@@@@@@ BomHeader change APPROVED ERP @@@@@@@@@@@ ");
		    Kogger.debug(getClass(), "@@@@ BomHeader : " + bomHeader.toString());

		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("APPROVED"));

		}

		/*
		 * WTConnection wtConn = EcmUtil.getWTConnection(); Connection connection = wtConn.getConnection(); if (reSult) //
		 * TransferFlag 를 세팅합니다. { setFalseFlag(itemCd, econum, "4", connection); if (PartUtil.isProductType(strType)) // if(
		 * strType.equals("P") ) {
		 * 
		 * setSubTransFlag(econum, itemCd, "4");
		 * 
		 * }
		 * 
		 * // 헤더 상태변경 Kogger.debug(getClass(), "@@@@@@@@@@@@ BomHeader change APPROVED ERP @@@@@@@@@@@ ");
		 * Kogger.debug(getClass(), "@@@@ BomHeader : " + bomHeader.toString());
		 * 
		 * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("APPROVED")); } else {
		 * setFalseFlag(itemCd, econum, "3", connection); if (PartUtil.isProductType(strType)) // if( strType.equals("P") ) {
		 * 
		 * setSubTransFlag(econum, itemCd, "3");
		 * 
		 * } }
		 */

		/*
		 * 공동작업자에 대한 스펙은 삭제되었다.
		 */
		/*
		 * // 실패시 메일 발송 if (!reSult && mailEnable) { Kogger.debug(getClass(), ">>>>>>>>>>>>>> 변경 BOM 인터페이스 실패 알림 메일 발송!!"); String
		 * contents = "";
		 * 
		 * // 공동작업자 전부에게 실패 알림 메일 보내기 ArrayList<Hashtable<String, String>> coworkers = dao.getCoworkerList(econum, itemCd);
		 * 
		 * for (int jnx = 0; jnx < coworkers.size(); jnx++) {
		 * 
		 * Hashtable<String, String> coworkerHash = coworkers.get(jnx); Hashtable contentHash = MailUtil.getMailContent("infFail",
		 * bomHeader, coworkerHash.get("userId"));
		 * 
		 * 
		 * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
		 * 
		 * String templateName = CommonUtil.getMailTemplateName(coworkerHash.get("userId"), "InterfaceFailNotice");
		 * 
		 * // contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, "InterfaceFailNotice.html"); contents =
		 * MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName); //
		 * ////////////////////////////////////////////////////////////////////////////////////////
		 * 
		 * Hashtable hash = MailUtil.prepareMailInfoHash(coworkerHash.get("toName"), coworkerHash.get("toEmail"), contents);
		 * 
		 * if (hash != null) MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle")); } }
		 */

	    }

	    /***************************************************************************/
	    // KYB 작업 - 베이스라인 시작
	    ecoNumber = bomqr.getEcoNumber(ecoLongValue);
	    WTConnection wtConn = EcmUtil.getWTConnection();
	    bomqr.updateECOBaseLine(ecoNumber, wtConn);

	    // KYB 작업 - 베이스라인 종료
	    /***************************************************************************/

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    reSult = false;
	}

	return reSult;
    }

    // 금형부품 KetPartusageLink 입력값 조회 - 단레벨 :: KETPartUsageLink는 모-자 구성관계 이므로 헤더정보는 조회안함
    public Vector getMoldData(String headItem, String ecoHead) {
	String qty = "";
	String beforQty = "";
	StringBuffer strSql = new StringBuffer();

	Vector comp = new Vector();
	Hashtable comp_data = null;

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    strSql.append(" SELECT   t.parentitemcode  																		\n").append("            , t.childitemcode   																		\n")
		    .append("            , t.bomversion  																			\n").append("            , t.itemseq   																				\n")
		    .append("            , t.beforequantity  																		\n").append("            , t.afterquantity as quantity  																\n")
		    .append("            , trim( replace( t.afterunit, 'KET_', '' ) ) as unit    										\n").append("            , t.aftermaterial as material  																\n")
		    .append("            , t.afterhardnessfrom as hardnessfrom  												\n").append("            , t.afterhardnessto as hardnessto   													\n")
		    .append("            , t.afterdesigndate   as designdate  													\n")
		    .append("            , to_char( to_date( t.afterstartdate, 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt  		\n").append("            , t.ecocode																					\n")
		    .append("  FROM   ketbomecocomponent t																	\n").append("            , ketbomecoheader bh																	\n")
		    .append(" WHERE   t.ecoheadernumber = bh.ecoheadernumber											\n").append("    AND   t.ecoitemcode = bh.ecoitemcode														\n")
		    .append("    AND   t.ecoheadernumber = '" + ecoHead + "'													\n").append("    AND   t.ecoitemcode = '" + headItem + "'														\n")
		    .append("  order by t.bomlevel, t.itemseq																		\n");

	    Kogger.debug(getClass(), ">>>>> sql 2  [금형 변경 WF getMoldData] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    String parentitemcode = "";
	    String childitemcode = "";
	    String itemseq = "";
	    String quantity = "";
	    String material = "";
	    String hardnessfrom = "";
	    String hardnessto = "";
	    String afterdesigndate = "";
	    String bomversion = "";
	    String strEcoUnit = "";
	    String strEcoCode = "";
	    boolean delFlag = false;
	    while (rs.next()) {
		delFlag = false;

		// ecoCode 컬럼이 비어있는 경우는 변경이 일어나지 않은 것임
		strEcoCode = StringUtil.checkNull(rs.getString("ecocode"));
		if (!strEcoCode.equals("")) {
		    delFlag = true;
		}

		if (delFlag) {
		    parentitemcode = StringUtil.checkNull(rs.getString("parentitemcode"));
		    childitemcode = StringUtil.checkNull(rs.getString("childitemcode"));
		    itemseq = StringUtil.checkNull(rs.getString("itemseq"));
		    quantity = StringUtil.checkNull(rs.getString("quantity"));
		    material = StringUtil.checkNull(rs.getString("material"));
		    hardnessfrom = StringUtil.checkNull(rs.getString("hardnessfrom"));
		    hardnessto = StringUtil.checkNull(rs.getString("hardnessto"));
		    afterdesigndate = StringUtil.checkNull(rs.getString("designdate"));
		    bomversion = StringUtil.checkNull(rs.getString("bomversion"));

		    strEcoUnit = StringUtil.checkNull(rs.getString("unit"));
		    if (strEcoUnit != null && !strEcoUnit.equals("")) {
			strEcoUnit = strEcoUnit.toUpperCase();
		    }
		    else {
			strEcoUnit = "";
		    }

		    comp_data = new Hashtable();
		    comp_data.put("MATNR", parentitemcode);
		    comp_data.put("IDNRK", childitemcode);
		    comp_data.put("ODNRK", itemseq);
		    comp_data.put("MENGE", quantity);
		    comp_data.put("MEINS", strEcoUnit);
		    comp_data.put("ATWRT", material);
		    comp_data.put("HARDN", hardnessfrom);
		    comp_data.put("HARDT", hardnessto);
		    comp_data.put("DATUV", afterdesigndate);
		    comp_data.put("CHILDV", getItemVersion(childitemcode));
		    comp_data.put("NBOMV", bomversion);
		    comp_data.put("ECOCODE", strEcoCode);
		    comp_data.put("ECONO", ecoHead);
		    comp.add(comp_data);
		}
	    }
	    Kogger.debug(getClass(), "%KETPARTUSAGELINK DATA% 금형 변경 comp : " + comp);
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

    // 제품 KetPartusageLink 입력값 조회 - 멀티레벨 :: KETPartUsageLink는 모-자 구성관계 이므로 헤더정보는 조회안함
    public Vector getGenData(String headItem, String ecoHead) {
	String qty = "";
	String beforQty = "";
	StringBuffer strSql = new StringBuffer();

	String p_boxquantity = "";
	String p_beforeBoxquantity = "";

	Double d_boxquantity = 0.0;
	Double d_beforeBoxquantity = 0.0;
	boolean isHeadOnlyChg = false;

	Vector comp = new Vector();
	Hashtable comp_data = null;

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    // 헤더를 모부품으로 가지는 자부품 목록 가져오는 SQL
	    strSql.append("SELECT   t.parentitemcode  																			\n")
		    .append("           , t.childitemcode  																			\n")
		    .append("           , t.itemseq  																					\n")
		    .append("           , t.bomversion																				\n")
		    .append("           , decode( substr( t.childitemcode,1,3), 'P10', t.afterquantity, 'P20', t.afterquantity, to_char( to_number( nvl( bh.boxquantity, '1' ) ) * to_number( t.afterquantity ) ) ) as quantity_erp  \n")
		    .append("           , t.beforequantity  																			\n")
		    .append("           , t.afterquantity as quantity  																\n")
		    // .append("           , nvl( t.afterboxquantity, '1' ) as boxquantity 											\n")
		    .append("           , nvl( bh.boxquantity, '1' ) as boxquantity 												\n")
		    // 모부품의 포장수량 정보를 저장해야함
		    .append("           , nvl( bh.ATTRIBUTE2, '1' ) as beforeBoxquantity 										\n")
		    // 모부품의 이전 포장수량 정보
		    .append("           , trim( replace( t.afterunit , 'KET_', '' ) ) as unit 										\n")
		    .append("           , to_char( to_date( t.afterstartdate , 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt    	\n")
		    .append("           , t.ecocode, t.afterict, t.afterreftop, t.afterrefbottom																					\n").append("  FROM   ketbomecocomponent t																	\n")
		    .append("            , ketbomecoheader bh																	\n").append(" WHERE   t.ecoheadernumber = bh.ecoheadernumber											\n")
		    .append("    AND   t.ecoitemcode = bh.ecoitemcode	\n")
		    .append("    AND   t.ecoheadernumber = '" + ecoHead + "' \n")
		    .append("    AND   t.ecoitemcode = '" + headItem + "' \n")
		    // ECO 설계변경시 BOM 수정을 위한 대상List 발췌시, 선별하는 대상 List 전체를 가져오기 위한 Query 조건 수정 ( By Sanghan.Lee - 20150623 )
		    //.append("    AND   t.parentitemcode = '" + headItem + "' \n")
		    .append(" order by t.bomlevel, t.itemseq \n");

	    Kogger.debug(getClass(), ">>>>> sql  [제품 변경 WF getGenData] : " + strSql.toString());
	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    String strEcoCode = "";
	    boolean delFlag = false;
	    while (rs.next()) {
		delFlag = false;
		isHeadOnlyChg = false;

		p_boxquantity = StringUtil.checkNull(rs.getString("boxquantity"));
		p_beforeBoxquantity = StringUtil.checkNull(rs.getString("beforeBoxquantity"));

		// 헤더의 포장수량 변경여부 확인
		d_boxquantity = new Double(p_boxquantity);
		d_beforeBoxquantity = new Double(p_beforeBoxquantity);
		Kogger.debug(getClass(), "########## d_beforeBoxquantity : " + d_beforeBoxquantity);
		Kogger.debug(getClass(), "########## d_boxquantity : " + d_boxquantity);

		if (d_boxquantity.compareTo(d_beforeBoxquantity) != 0) {
		    isHeadOnlyChg = true;
		}

		// ecoCode 컬럼이 비어있는 경우는 변경이 일어나지 않은 것임
		strEcoCode = StringUtil.checkNull(rs.getString("ecocode"));
		if (!strEcoCode.equals("")) {
		    delFlag = true;
		}
		Kogger.debug(getClass(), "########### delFlag : " + delFlag);
		Kogger.debug(getClass(), "########### isHeadOnlyChg : " + isHeadOnlyChg);

		// 모자 속성정보가 변경되었거나(delFlag) , 헤더의 포장수량 정보가 변경된 경우(isHeadOnlyChg)
		// if (delFlag || isHeadOnlyChg) {
		if (delFlag) {
		    String childItem = StringUtil.checkNull(rs.getString("childitemcode"));
		    comp_data = new Hashtable();
		    comp_data.put("MATNR", StringUtil.checkNull(rs.getString("parentitemcode")));
		    comp_data.put("IDNRK", childItem);
		    comp_data.put("POSNR", StringUtil.checkNull(rs.getString("itemseq")));
		    comp_data.put("MENGE", StringUtil.checkNull(rs.getString("quantity")));
		    comp_data.put("ERFMG", StringUtil.checkNull(rs.getString("quantity_erp")));
		    comp_data.put("BMENG", p_boxquantity);
		    comp_data.put("MEINS", (StringUtil.checkNull(rs.getString("unit"))).toUpperCase());
		    comp_data.put("DATUV", StringUtil.checkNull(rs.getString("startdt")));
		    comp_data.put("CHILDV", getItemVersion(childItem));
		    comp_data.put("NBOMV", StringUtil.checkNull(rs.getString("bomversion")));
		    comp_data.put("ECONO", ecoHead);

		    // if (!delFlag && isHeadOnlyChg) // 헤더만 변경된 경우 임의로 ecoCode 셋팅
		    if (!delFlag) {
			comp_data.put("ECOCODE", "Update");
		    }
		    else {
			comp_data.put("ECOCODE", strEcoCode);
		    }

		    comp_data.put("ICT", StringUtil.checkNull(rs.getString("afterict")));
		    comp_data.put("REFTOP", StringUtil.checkNull(rs.getString("afterreftop")));
		    comp_data.put("REFBOTTOM", StringUtil.checkNull(rs.getString("afterrefbottom")));

		    comp.add(comp_data);
		}
	    }

	    Kogger.debug(getClass(), "%KETPARTUSAGELINK DATA% 제품 변경 comp : " + comp);
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
    public Vector getCompData(Connection conn, String ecoHead, String parentItem) {
	PreparedStatement ppstmt = null;
	ResultSet result = null;

	String qty = "";
	String beforQty = "";
	StringBuffer strSql = new StringBuffer();

	Vector comp = new Vector();
	Hashtable comp_data = null;

	try {
	    strSql.append("SELECT   t.parentitemcode  																			\n")
		    .append("            , t.childitemcode  																			\n")
		    .append("            , t.itemseq 	 																				\n")
		    .append("            , t.bomversion																				\n")
		    .append("            , decode( substr( t.childitemcode,1,3), 'P10', t.afterquantity, 'P20', t.afterquantity, to_char( to_number( nvl( bh.boxquantity, '1' ) ) * to_number( t.afterquantity ) ) ) as quantity_erp  \n")
		    .append("            , t.beforequantity  																		\n").append("            , t.afterquantity as quantity  																\n")
		    .append("            , nvl( t.afterboxquantity, '1' ) as boxquantity 											\n").append("            , trim( replace( t.afterunit, 'KET_', '' ) ) as unit  										\n")
		    .append("            , to_char( to_date( t.afterstartdate , 'yyyy-mm-dd' ), 'yyyymmdd' ) as startdt 		\n")
		    .append("            , t.ecocode, t.afterict, t.afterreftop, t.afterrefbottom																					\n").append("  FROM   ketbomecocomponent t																	\n")
		    .append("            , ketbomecoheader bh																	\n").append("  WHERE   t.ecoheadernumber = bh.ecoheadernumber										\n")
		    .append("    AND    t.ecoitemcode = bh.ecoitemcode														\n").append("   AND     t.ecoheadernumber = '" + ecoHead + "'													\n")
		    .append("   AND     t.parentitemcode = '" + parentItem + "'													\n").append("  order by t.bomlevel, t.itemseq																		\n");

	    Kogger.debug(getClass(), ">>>>> sql  [제품 변경 getCompData] : " + strSql.toString());

	    ppstmt = conn.prepareStatement(strSql.toString());
	    result = ppstmt.executeQuery();
	    strSql.delete(0, strSql.length());

	    String strEcoCode = "";
	    boolean delFlag = false;
	    while (result.next()) {
		delFlag = false;

		// ecoCode 컬럼이 비어있는 경우는 변경이 일어나지 않은 것임
		strEcoCode = StringUtil.checkNull(result.getString("ecocode"));
		if (!strEcoCode.equals("")) {
		    delFlag = true;
		}

		if (delFlag) {
		    String childItem = StringUtil.checkNull(result.getString("childitemcode"));
		    comp_data = new Hashtable();
		    comp_data.put("MATNR", StringUtil.checkNull(result.getString("parentitemcode")));
		    comp_data.put("IDNRK", childItem);
		    comp_data.put("POSNR", StringUtil.checkNull(result.getString("itemseq")));
		    comp_data.put("MENGE", StringUtil.checkNull(result.getString("quantity")));
		    comp_data.put("ERFMG", StringUtil.checkNull(result.getString("quantity_erp")));
		    comp_data.put("BMENG", StringUtil.checkNull(result.getString("boxquantity")));
		    comp_data.put("MEINS", (StringUtil.checkNull(result.getString("unit"))).toUpperCase());
		    comp_data.put("DATUV", StringUtil.checkNull(result.getString("startdt")));
		    comp_data.put("CHILDV", getItemVersion(childItem));
		    comp_data.put("NBOMV", StringUtil.checkNull(result.getString("bomversion")));
		    comp_data.put("ECOCODE", StringUtil.checkNull(result.getString("ecocode")));
		    comp_data.put("ICT", StringUtil.checkNull(result.getString("afterict")));
		    comp_data.put("REFTOP", StringUtil.checkNull(result.getString("afterreftop")));
		    comp_data.put("REFBOTTOM", StringUtil.checkNull(result.getString("afterrefbottom")));
		    comp.add(comp_data);
		}
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
    public ArrayList<Hashtable> getItemCode(String headCd) {
	StringBuffer strSql = new StringBuffer();
	ArrayList<Hashtable> arrList = new ArrayList<Hashtable>();

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));

	    // 변경 BOM은 설계변경 객체와 함께 승인되므로 해당 객체의 OID가 넘어온다.
	    strSql.append("SELECT   bh.ecoitemcode as itemcode, bh.bomversion   			\n").append("           , bh.ecoheadernumber as econum 		\n").append("           , bh.ida2a2 as oid					 		\n")
		    .append(" FROM    ketbomecoheader bh 					\n").append("           , ketprodchangeorder c					\n").append("WHERE   bh.ecoheadernumber = c.ecoid 		\n")
		    .append("  AND    c.ida2a2 = '" + headCd + "' 					\n").append("UNION										   			\n").append("SELECT   bh.ecoitemcode as itemcode, bh.bomversion   			\n")
		    .append("           , bh.ecoheadernumber as econum 		\n").append("           , bh.ida2a2 as oid					 		\n").append(" FROM    ketbomecoheader bh 					\n")
		    .append("           , ketmoldchangeorder c					\n").append("WHERE   bh.ecoheadernumber = c.ecoid 		\n").append("  AND    c.ida2a2 = '" + headCd + "' 					\n");

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();
	    strSql.delete(0, strSql.length());
	    while (rs.next()) {
		Hashtable ht = new Hashtable();
		ht.put("itemcode", StringUtil.checkNull(rs.getString("itemcode")));
		ht.put("econum", StringUtil.checkNull(rs.getString("econum")));
		ht.put("bomversion", StringUtil.checkNull(rs.getString("bomversion")));

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

    // KetBomEcoSubstitute 입력.
    public boolean getSubTrans(String ecoNo, String headItem) {
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

	    strSql.append("SELECT  s.parentitemcode  															\n").append("           , s.childitemcode  															\n").append("           , s.substituteitemcode  													\n")
		    .append("           , s.afterquantity quantity  													\n").append("           , s.afterunit unit																\n")
		    .append("           , s.afterstartdate startdate  												\n").append("           , s.afterenddate enddate   												\n")
		    .append(" FROM   ketbomecosubstitute s  													\n").append("          , ketbomecocomponent c  												\n")
		    .append(" WHERE  c.ecoheadernumber = s.ecoheadernumber  							\n").append("  AND    c.ecoitemcode = s.ecoitemcode											\n")
		    .append("  AND    c.ecoheadernumber = '" + ecoNo + "'  									\n").append("  AND    c.ecoitemcode = '" + headItem + "'											\n")
		    .append("  AND    c.childitemcode = s.childitemcode  										\n");
	    // Kogger.debug(getClass(), ">>>>> sql [변경 WF getSubTrans] : " + strSql.toString());

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();

	    KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	    String parentItem = "";
	    String childItem = "";
	    String subItem = "";
	    String quantity = "";
	    String unit = "";
	    String startdate = "";
	    String enddate = "";
	    while (rs.next()) {
		parentItem = StringUtil.checkNull(rs.getString("parentitemcode"));
		childItem = StringUtil.checkNull(rs.getString("childitemcode"));
		subItem = StringUtil.checkNull(rs.getString("substituteitemcode"));
		quantity = StringUtil.checkNull(rs.getString("quantity"));
		unit = StringUtil.checkNull(rs.getString("unit"));
		startdate = StringUtil.checkNull(rs.getString("startdate"));
		enddate = StringUtil.checkNull(rs.getString("enddate"));

		// WTPart childPart = kh.searchItem( childItem );
		// WTPartMaster childPartMaster = (WTPartMaster) (childPart.getMaster());
		// Long oids = CommonUtil.getOIDLongValue(childPartMaster);
		// String oid2 = String.valueOf(oids);

		// 모부품의 oid 를 저장
		WTPart parentIPart = kh.searchItem(parentItem);
		String parentOid = KETObjectUtil.getIda2a2(parentIPart);

		strSql.delete(0, strSql.length());
		strSql.append("INSERT INTO  ketbomsubstitutemaster 	\n").append("	        ( PARENTITEM  						\n").append("           , CHILDITEM  						\n")
		        .append("           , SUBSTITUTEITEM  				\n").append("           , QUANTITY  						\n").append("           , UNIT  								\n").append("           , STARTDATE 						\n")
		        .append("           , ENDDATE  							\n").append("           , VERSIONITEMCODE ) 			\n").append("	values ( ?, ?, ?, ?, ?, ?, ?, ? )				\n");

		pstmt = connection.prepareStatement(strSql.toString());
		pstmt.setString(1, parentItem);
		pstmt.setString(2, childItem);
		pstmt.setString(3, subItem);
		pstmt.setString(4, quantity);
		pstmt.setString(5, unit);
		pstmt.setString(6, startdate);
		pstmt.setString(7, enddate);
		pstmt.setString(8, parentOid);
		pstmt.executeUpdate();

		strSql.delete(0, strSql.length());
		strSql.append("UPDATE  ketbomecosubstitute set transferflag='Y' 	\n").append(" WHERE  ecoheadernumber  = ?  						\n").append("   AND   ecoitemcode = ?  								\n")
		        .append("   AND   parentitemcode = ?  							\n").append("   AND   childitemcode = ? 								\n").append("   AND   substituteitemcode = ? 						\n");

		pstmt = connection.prepareStatement(strSql.toString());
		pstmt.setString(1, ecoNo);
		pstmt.setString(2, headItem);
		pstmt.setString(3, parentItem);
		pstmt.setString(4, childItem);
		pstmt.setString(5, subItem);
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
	Hashtable inputParams = new Hashtable();
	Vector itemVec = new Vector();
	itemVec.add(itemCd);
	inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
	inputParams.put("itemCode", itemVec);
	Vector version = KETBomHelper.service.getItemVersion(inputParams);
	String versionStr = "";
	for (int k = 0; k < version.size(); k++) {
	    if (version.size() > 0) {
			versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
			// shin...
			if (itemCd.equals(versionStr.substring(0, versionStr.indexOf("@")))) {
			    //versionStr = versionStr.substring(versionStr.indexOf("@") + 1, versionStr.indexOf("("));
				//위의 substing으로는 커버할 수 없는 품번들이 존재하여 substringBetween 을 사용 및 공백제거 2015.06.30 by 황정태
				versionStr = StringUtils.substringBetween(versionStr, "@", "(").replaceAll("\\p{Space}","");
			}
	    }
	}
	return versionStr;
    }

    public void setFalseFlag(String ecoItemCode, String ecoNo, String flag) throws Exception {
	this.setFalseFlag(ecoItemCode, ecoNo, flag, null);

    }

    public void setFalseFlag(String ecoItemCode, String ecoNo, String flag, Connection conn) {
	StringBuffer strSql = new StringBuffer();

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {

	    if (conn != null) {
		connection = conn;

	    }
	    else {
		res = DBConnectionManager.getInstance();
		connection = res.getConnection(registry.getString("plm"));
		connection.setAutoCommit(false);

	    }

	    strSql.append("UPDATE  ketbomecoheader set transferflag = '" + flag + "'  			\n").append(" WHERE   ecoHeaderNumber = '" + ecoNo + "' 						\n")
		    .append("    AND   ecoitemcode = '" + ecoItemCode + "' 						\n");

	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.executeUpdate();
	    strSql.delete(0, strSql.length());
	    Kogger.debug(getClass(), ">>>>> SQL [WF setFalseFlag] : " + strSql);

	    strSql.append("UPDATE   ketbomecocomponent set transferflag = '" + flag + "' 	\n").append(" WHERE    ecoheadernumber = '" + ecoNo + "'							\n")
		    .append("    AND    ecoitemcode = '" + ecoItemCode + "' 						\n");
	    pstmt = connection.prepareStatement(strSql.toString());
	    pstmt.executeUpdate();
	    Kogger.debug(getClass(), ">>>>> SQL [WF setFalseFlag] : " + strSql);

	    if (conn != null && connection != null) connection.commit();
	} catch (Exception e) {
	    try {
		if (conn != null && connection != null) connection.rollback();
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
		    if (conn != null && connection != null) res.freeConnection(registry.getString("plm"), connection);
		}
	    }
	}
    }

    /**
     * 
     * @param econo
     * @param headItem
     * @param sFlag
     * @메소드명 : setSubTransFlag
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     * @deprecated
     * 
     */
    public void setSubTransFlag(String econo, String headItem, String sFlag) {
	StringBuffer strSql = new StringBuffer();

	DBConnectionManager res = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    res = DBConnectionManager.getInstance();
	    connection = res.getConnection(registry.getString("plm"));
	    connection.setAutoCommit(false);

	    strSql.append("SELECT   s.parentitemcode  											\n").append("           , s.childitemcode  												\n").append("           , s.substituteitemcode  										\n")
		    .append("  FROM   ketbomecosubstitute s										\n").append("           , ketbomecocomponent c  									\n").append(" WHERE  c.ecoitemcode = s.ecoitemcode  							\n")
		    .append("   AND   c.ecoheadernumber = '" + econo + "'							\n").append("   AND   c.ecoitemcode = '" + headItem + "'								\n")
		    .append("   AND   c.childitemcode = s.childitemcode  							\n");
	    Kogger.debug(getClass(), ">>>>> sql [변경 WF setSubTransFlag] : " + strSql.toString());

	    pstmt = connection.prepareStatement(strSql.toString());
	    rs = pstmt.executeQuery();

	    String parentItem = "";
	    String childItem = "";
	    String subItem = "";

	    strSql.delete(0, strSql.length());
	    strSql.append("UPDATE  ketbomecosubstitute set transferflag='" + sFlag + "' 	\n").append(" WHERE   ecoheadernumber  = ? 									\n").append("   AND    parentitemcode = ?  										\n")
		    .append("   AND    childitemcode = ? 										\n").append("   AND    substituteitemcode = ? 									\n");

	    pstmt = connection.prepareStatement(strSql.toString());

	    while (rs.next()) {
		parentItem = StringUtil.checkNull(rs.getString("parentitemcode"));
		childItem = StringUtil.checkNull(rs.getString("childitemcode"));
		subItem = StringUtil.checkNull(rs.getString("substituteitemcode"));

		pstmt.clearParameters();
		pstmt.setString(1, econo);
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
}
