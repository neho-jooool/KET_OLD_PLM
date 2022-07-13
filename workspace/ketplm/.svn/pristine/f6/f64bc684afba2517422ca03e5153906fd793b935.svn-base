package ext.ket.bom.util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.method.MethodContext;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.vc.config.ConfigSpec;
import wt.vc.config.LatestConfigSpec;
import e3ps.bom.common.util.Utility;
import e3ps.bom.entity.KETPartUsageLink;
import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.ecm.beans.ProdEcoBeans;
import ext.ket.bom.matersum.MCompositeIterator;
import ext.ket.bom.matersum.MHierarchyComponent;
import ext.ket.bom.matersum.MTreePartItem;
import ext.ket.bom.matersum.MTreeRootPart;
import ext.ket.bom.matersum.dto.MaterSumDTO;
import ext.ket.bom.matersum.util.BigDecimalUtil;
import ext.ket.bom.matersum.util.MTreePartController;
import ext.ket.bom.matersum.util.MTreePartUtil;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.KogDbUtil;
import ext.ket.shared.log.Kogger;

public class KETBomPartUtil {

    public KETBomPartUtil() {
	// TODO Auto-generated constructor stub
    }

    public static boolean bomValidationAssist(Map<String, Object> reqMap) throws Exception {
	// bom 구성 체크 로직 보완을 위해 추가된 메서드 (bom 에디터에서 검색추가시 최신 bom만 가져오기 때문에 이를 보완하기 위함이다
	// 6. 터미널 반제품의 경우 자부품에 후도금 부품코드가 존재하는지 체크 (품번 도금사양이 -2 일 경우) ---- H7*-2 부품의 자부품으로 H7*A-2 부품이 있는가
	// 7. 도금전 터미널 반제품 자부품으로 원재료, 스크랩 코드 존재하는지 체크 ---- H7*A-2 부품의 자부품으로 R*, S* 부품이 존재하는가
	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	boolean valid = false;

	try {
	    boolean isRpart = false;
	    boolean isSpart = false;
	    String ecoNo = (String) reqMap.get("ecoNo");
	    String parentItemCode = (String) reqMap.get("partNo");
	    String checkType = (String) reqMap.get("checkType");

	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();
	    StringBuffer sql = new StringBuffer();

	    sql.append(" SELECT CHILDITEMCODE FROM KETBOMECOCOMPONENT WHERE ECOHEADERNUMBER = '" + ecoNo + "' AND PARENTITEMCODE = '" + parentItemCode + "' AND ECOCODE != 'Remove' \n");
	    sql.append(" UNION                                                                                                                                              \n");
	    sql.append(" SELECT CHILDITEMCODE FROM KETBOMECOCOWORKER A, KETBOMHEADER B, KETBOMCOMPONENT C                                                                   \n");
	    sql.append("  WHERE A.ECOHEADERNUMBER = '" + ecoNo + "'                                                                                                             \n");
	    sql.append("    AND B.ECOITEMCODE =  '" + parentItemCode + "'                                                                                                       \n");
	    sql.append("    AND A.ECOITEMCODE = B.NEWBOMCODE                                                                                                                \n");
	    sql.append("    AND B.NEWBOMCODE = C.PARENTITEMCODE                                                                                                             \n");

	    rs = stat.executeQuery(sql.toString());

	    while (rs.next()) {
		String CHILDITEMCODE = rs.getString("CHILDITEMCODE");

		if ("type6".equals(checkType)) {
		    valid = CHILDITEMCODE.endsWith("A-2");
		    break;
		} else if ("type7".equals(checkType)) {
		    if (!isRpart) {
			isRpart = CHILDITEMCODE.startsWith("R");
		    }
		    if (!isSpart) {
			isSpart = CHILDITEMCODE.startsWith("S");
		    }

		    if (isRpart && isSpart) {
			valid = true;
			break;
		    }
		}

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	return valid;
    }

    public static WTPart getPart(String oid) throws WTException {
	WTPart part = null;
	ReferenceFactory referencefactory = new ReferenceFactory();
	WTReference wtreference = referencefactory.getReference(oid);

	if (wtreference != null) {
	    part = (WTPart) wtreference.getObject();
	}
	return part;
    }

    public static WTPart getLatestPart(String partNumber) throws Exception {
	WTPart part = null;

	KETBOMQueryBean bean = new KETBOMQueryBean();

	String oid = bean.getLatestPart(partNumber);

	part = getPart(oid);

	return part;
    }

    public static WTPart getLatestPart2(String partNumber, WTConnection conn) throws Exception {
	WTPart part = null;

	KETBOMQueryBean bean = new KETBOMQueryBean();
	String oid = bean.getLatestPart2(partNumber, conn);

	Kogger.debug(KETBomPartUtil.class, "Oid===>" + oid);
	if (oid != null && !oid.equals(""))
	    part = getPart(oid);

	return part;
    }

    public static KETPartUsageLink getUsageLink(String oid) throws WTException {
	KETPartUsageLink link = null;
	ReferenceFactory referencefactory = new ReferenceFactory();
	WTReference wtreference = referencefactory.getReference(oid);

	if (wtreference != null) {
	    link = (KETPartUsageLink) wtreference.getObject();
	}
	return link;
    }

    public static long getPartLongId(WTPart part) {
	Long sPartId = new Long(part.getPersistInfo().getObjectIdentifier().getId());

	return sPartId;
    }

    public static long getPartMasterLongId(WTPartMaster partMaster) {
	Long sPartId = new Long(partMaster.getPersistInfo().getObjectIdentifier().getId());

	return sPartId;
    }

    public static HashMap getNumberCode(String codeType) {
	HashMap map = new HashMap();
	try {
	    QuerySpec select = new QuerySpec(NumberCode.class);
	    select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
	    SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
	    QueryResult result = PersistenceHelper.manager.find(select);

	    NumberCode code = null;
	    while (result.hasMoreElements()) {
		code = (NumberCode) result.nextElement();
		map.put(code.getCode(), code.getName());
	    }
	} catch (QueryException e) {
	    Kogger.error(KETBomPartUtil.class, e);
	} catch (WTException e) {
	    Kogger.error(KETBomPartUtil.class, e);
	} catch (Exception e) {
	    Kogger.error(KETBomPartUtil.class, e);
	}
	return map;
    }

    public static String getNumberCode2(String code) {
	HashMap map = new HashMap();
	String resultStr = "";
	try {
	    QuerySpec select = new QuerySpec(NumberCode.class);
	    select.appendWhere(new SearchCondition(NumberCode.class, NumberCode.CODE, "=", code), new int[] { 0 });

	    QueryResult result = PersistenceHelper.manager.find(select);

	    NumberCode ncode = null;
	    while (result.hasMoreElements()) {
		ncode = (NumberCode) result.nextElement();
		resultStr = ncode.getName();
	    }
	} catch (QueryException e) {
	    Kogger.error(KETBomPartUtil.class, e);
	} catch (WTException e) {
	    Kogger.error(KETBomPartUtil.class, e);
	} catch (Exception e) {
	    Kogger.error(KETBomPartUtil.class, e);
	}
	return resultStr;
    }

    public static Hashtable getBomValidationResult(Hashtable params) {
	Hashtable result = new Hashtable();
	String errLog = "";

	String checkdata1 = "";
	String checkdata2 = "";
	String checkdata3 = "";
	String checkdata4 = "";
	String checkdata5 = "";
	String checkdata6 = "";
	String checkdata7 = "";
	String checkdata8 = "";
	String checkdata9 = "";

	KETBOMQueryBean bean = new KETBOMQueryBean();

	try {

	    String partNumber = "";
	    String boxQty = "";
	    String ecoNumber = (String) params.get("ecoNumber");
	    String gubun = (String) params.get("gubun");
	    String partType = (String) params.get("partType");

	    String[] partNo = (String[]) params.get("partNo");
	    String[] index = (String[]) params.get("index");
	    String[] lvl = (String[]) params.get("lvl");
	    String[] ict = (String[]) params.get("ict");
	    String[] seq = (String[]) params.get("seq");
	    String[] partName = (String[]) params.get("partName");
	    String[] qty = (String[]) params.get("qty");
	    String[] unit = (String[]) params.get("unit");
	    String[] rev = (String[]) params.get("rev");
	    String[] state = (String[]) params.get("state");
	    String[] econo = (String[]) params.get("econo");
	    String[] checkout = (String[]) params.get("checkout");
	    String[] reftop = (String[]) params.get("reftop");
	    String[] refbtm = (String[]) params.get("refbtm");
	    String[] material = (String[]) params.get("material");
	    String[] hardnessFrom = (String[]) params.get("hardnessFrom");
	    String[] hardnessTo = (String[]) params.get("hardnessTo");
	    String[] designDate = (String[]) params.get("designDate");
	    String[] parentNo = (String[]) params.get("parentNo");
	    String[] pver = (String[]) params.get("pver");

	    List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
	    Hashtable saveKeyHash = new Hashtable();

	    Hashtable check1Hash = new Hashtable();
	    Hashtable check8Hash = new Hashtable();

	    Hashtable childPartsHash = new Hashtable();
	    Hashtable childPartsHash2 = new Hashtable();
	    Hashtable childChecker = new Hashtable();

	    Map<String, Object> reqMap = new HashMap<String, Object>();
	    KETBomPartUtil bomUtil = new KETBomPartUtil();

	    if (index != null) {
		String bom_path = "";
		String child_partList = "";
		String child_partList2 = "";

		int old_lvl = 0;

		for (int i = 0; i < index.length; i++) {

		    Map<String, Object> data = new Hashtable();

		    int new_lvl = Integer.parseInt(lvl[i]);

		    if (new_lvl > old_lvl) {
			bom_path += lvl[i] + "^" + parentNo[i] + "|";
			// bom_path += parentNo[i] + "|";
			Kogger.debug(KETBomPartUtil.class, "bom_path====>" + bom_path);
		    } else if (new_lvl < old_lvl) {
			bom_path = bom_path.substring(0, bom_path.indexOf(lvl[i] + "^" + (String) parentNo[i] + "|")) + (String) lvl[i] + "^" + (String) parentNo[i] + "|";
			// bom_path = bom_path.substring(0, bom_path.indexOf((String)parentNo[i] + "|")) + (String)parentNo[i] + "|";
			Kogger.debug(KETBomPartUtil.class, "bom_path====>" + bom_path);
		    }

		    String full_path = bom_path + ">" + partNo[i] + "$";

		    // Kogger.debug(getClass(), "bomPath===>"+full_path.substring(full_path.indexOf(">")+1));
		    // Kogger.debug(getClass(), "full_path===>" + full_path);

		    data.put("partNo", partNo[i]);
		    data.put("index", index[i]);
		    data.put("lvl", lvl[i]);
		    data.put("ict", ict[i]);
		    data.put("seq", seq[i]);
		    data.put("partName", partName[i]);
		    data.put("qty", qty[i]);
		    data.put("unit", unit[i]);
		    data.put("rev", rev[i]);
		    data.put("state", state[i]);
		    data.put("econo", econo[i]);
		    data.put("checkout", checkout[i]);
		    data.put("reftop", reftop[i]);
		    data.put("refbtm", refbtm[i]);
		    data.put("material", material[i]);
		    data.put("hardnessFrom", hardnessFrom[i]);
		    data.put("hardnessTo", hardnessTo[i]);
		    data.put("designDate", designDate[i]);
		    data.put("parentNo", parentNo[i]);
		    data.put("pver", pver[i]);

		    data.put("bom_path", bom_path);
		    data.put("bom_full_path", full_path);

		    if (childChecker.containsKey(bom_path)) {

			String childCheck = (String) childChecker.get(bom_path);
			if (childCheck.indexOf(partNo[i]) != -1) {
			    checkdata1 += partNo[i] + ",";
			} else {
			    child_partList += partNo[i] + ",";
			    childChecker.put(bom_path, child_partList);
			}
		    }

		    if (bom_path.indexOf("^" + partNo[i] + "|") != -1) {
			checkdata2 += partNo[i] + ",";
		    }

		    String isDeleted = "";

		    isDeleted = bean.isDeleted(partNo[i], rev[i]);

		    if (isDeleted.equals("Y")) {
			checkdata5 += partNo[i] + ",";
		    }

		    check1Hash.put(full_path, bom_path);

		    old_lvl = new_lvl;

		    if (lvl[i].equals("0")) {
			partNumber = partNo[i];
			boxQty = qty[i];
		    }

		    String key = parentNo[i] + "^" + partNo[i];

		    if (new_lvl > 0)
			saveKeyHash.put(key, data);

		    saveList.add(data);

		    if (new_lvl > 0) {
			if (childPartsHash.containsKey(bom_path)) {
			    String childParts = (String) childPartsHash.get(bom_path);
			    childParts += "," + partNo[i];
			    childPartsHash.put(bom_path, childParts);
			} else {
			    childPartsHash.put(bom_path, partNo[i]);
			}

			if (childPartsHash2.containsKey(parentNo[i] + "↔" + lvl[i])) {
			    String childParts = (String) childPartsHash2.get(parentNo[i] + "↔" + lvl[i]);
			    childParts += "," + partNo[i];
			    childPartsHash2.put(parentNo[i] + "↔" + lvl[i], childParts);
			} else {
			    childPartsHash2.put(parentNo[i] + "↔" + lvl[i], partNo[i]);
			}

		    } else {
			// childPartsHash.put(bom_path, "");
			childPartsHash2.put(partNo[i] + "↔" + "1", "");
		    }

		    if (partNo[i].startsWith("R10") || partNo[i].startsWith("R20")) {
			if (!check8Hash.containsKey(parentNo[i])) {
			    Hashtable check8Data = new Hashtable();
			    check8Data.put("RWeight", qty[i]);
			    check8Hash.put(parentNo[i], check8Data);
			} else {
			    Hashtable check8Data = (Hashtable) check8Hash.get(parentNo[i]);
			    if (!check8Data.containsKey("RWeight")) {
				check8Data.put("RWeight", qty[i]);
				check8Hash.put(parentNo[i], check8Data);
			    }
			}
		    }

		    if (partNo[i].startsWith("S")) {
			if (!check8Hash.containsKey(parentNo[i])) {
			    Hashtable check8Data = new Hashtable();
			    check8Data.put("SWeight", qty[i]);
			    check8Data.put("SPartNo", partNo[i]);
			    check8Hash.put(parentNo[i], check8Data);
			} else {
			    Hashtable check8Data = (Hashtable) check8Hash.get(parentNo[i]);
			    if (!check8Data.containsKey("SWeight")) {
				check8Data.put("SWeight", qty[i]);
				check8Data.put("SPartNo", partNo[i]);
				check8Hash.put(parentNo[i], check8Data);
			    }
			}
		    }

		}
	    }

	    for (int i = 0; i < saveList.size(); i++) {
		Hashtable data = (Hashtable) saveList.get(i);
		String part_No = (String) data.get("partNo");
		String parent_No = (String) data.get("parentNo");
		String part_lvl = (String) data.get("lvl");
		int ipart_lvl = Integer.parseInt(part_lvl) + 1;
		String bom_path = (String) data.get("bom_path");
		String bom_full_path = (String) data.get("bom_full_path");

		String childList = "";
		String childList2 = "";

		if (childPartsHash.containsKey(bom_path)) {
		    childList = (String) childPartsHash.get(bom_path);
		    Kogger.debug(KETBomPartUtil.class, "***childList================" + childList);
		}

		if (childPartsHash2.containsKey(part_No + "↔" + Integer.toString(ipart_lvl))) {
		    childList2 = (String) childPartsHash2.get(part_No + "↔" + Integer.toString(ipart_lvl));
		    Kogger.debug(KETBomPartUtil.class, "******childList2================" + childList2);

		}

		StringTokenizer st;
		int t_cnt;
		String[] childPart;

		st = new StringTokenizer(childList, ",");
		t_cnt = st.countTokens();
		childPart = new String[t_cnt];

		int cnt = 0;

		while (st.hasMoreTokens()) {
		    childPart[cnt] = st.nextToken();
		    cnt++;
		}

		StringTokenizer st2;
		int t_cnt2;
		String[] childPart2;

		st2 = new StringTokenizer(childList2, ",");
		t_cnt2 = st2.countTokens();
		childPart2 = new String[t_cnt2];

		int cnt2 = 0;

		while (st2.hasMoreTokens()) {
		    childPart2[cnt2] = st2.nextToken();
		    cnt2++;
		}

		Kogger.debug(KETBomPartUtil.class, "childList================" + childList);
		for (int y = 0; y < childPart.length; y++) {
		    Kogger.debug(KETBomPartUtil.class, "childPart[" + y + "]====>" + childPart[y]);
		}

		Kogger.debug(KETBomPartUtil.class, "childList2================" + childList2);
		for (int y = 0; y < childPart2.length; y++) {
		    Kogger.debug(KETBomPartUtil.class, "childPart2[" + y + "]====>" + childPart2[y]);
		}

		WTPart ParentWtPart = null;
		String spPartType = null;
		PartTypeEnum ptype = null;
		boolean isCheck4 = true;
		// 부모의 parttype이 원자재 일 경우 1레벨 하위로 원자재 존재시 스크랩 존재여부 체크하지 않도록 수정
		if (StringUtils.isNotEmpty(parent_No)) {
		    ParentWtPart = PartBaseHelper.service.getLatestPart(parent_No);
		}

		if (ParentWtPart != null) {
		    spPartType = PartSpecGetter.getPartSpec(ParentWtPart, PartSpecEnum.SpPartType);
		    ptype = PartTypeEnum.toPartTypeEnum(spPartType);
		    if (PartTypeEnum.ROH == ptype && (parent_No.startsWith("R10") || parent_No.startsWith("R20"))) {
			isCheck4 = false;
		    }
		}

		if (isCheck4) {
		    if (part_No.startsWith("R10") || part_No.startsWith("R20")) {
			// Kogger.debug(getClass(), "childList================"+childList);
			String checkNo = "S" + part_No.substring(1);
			String tmp4 = "";

			for (int x = 0; x < childPart.length; x++) {

			    WTPart ScrapPart = PartBaseHelper.service.getLatestPart(childPart[x]);
			    KETPartClassification claz = PartClazHelper.service.getPartClassification(ScrapPart);
			    String classCode = claz.getClassCode();
			    boolean isSuji = PartClazHelper.service.isSuji(claz);// 애초에는 isSuji 플래그로 판단하였으나 마이그레이션 데이터 불일치문제로 할수없이
				                                                 // classcode로 판단..

			    if (classCode.startsWith("P30")) {// 수지일 경우 S로 시작하는 품번이 같아야한다
				if (childPart[x].equals(checkNo)) {
				    tmp4 += childPart[x] + ",";
				}
			    } else {// 비철일 경우 S로만 시작하면 통과
				if (childPart[x].startsWith("S")) {
				    tmp4 += childPart[x] + ",";
				}
			    }
			}

			Kogger.debug(KETBomPartUtil.class, "tmp4================" + tmp4);

			if (tmp4.equals("")) {
			    checkdata4 += part_No + ",";
			}
		    }
		}

		if (part_No.startsWith("H7") && part_No.endsWith("-2") && !part_No.contains("A-2")) {
		    String tmp6 = "";

		    for (int x = 0; x < childPart2.length; x++) {
			// if (childPart2[x] != null && childPart2[x].startsWith("H7") && childPart2[x].contains("A-2")) {
			// tmp6 += childPart2[x] + ",";
			// }
			// 기존로직 주석처리함. H7로 시작해야하는 체크로직을 빼고 추가함 2016.07.11 전상우 대리와 협의
			if (childPart2[x] != null && childPart2[x].contains("A-2")) {
			    tmp6 += childPart2[x] + ",";
			}
		    }

		    if (tmp6.equals("")) {
			reqMap.put("partNo", part_No);
			reqMap.put("ecoNo", ecoNumber);
			reqMap.put("checkType", "type6");
			if (!bomUtil.bomValidationAssist(reqMap)) {
			    checkdata6 += part_No + ",";
			}
		    }
		}

		if (part_No.startsWith("H7") && part_No.contains("A-2")) {
		    String tmpR7 = "";
		    String tmpS7 = "";

		    for (int x = 0; x < childPart2.length; x++) {
			Kogger.debug(KETBomPartUtil.class, "childPart2[" + x + "]====>" + childPart2[x]);

			if (childPart2[x] != null && childPart2[x].startsWith("R")) {
			    tmpR7 += childPart2[x] + ",";

			    Kogger.debug(KETBomPartUtil.class, "tmpR7 childPart2[" + x + "]====>" + tmpR7);
			}

			if (childPart2[x] != null && childPart2[x].startsWith("S")) {
			    tmpS7 += childPart2[x] + ",";
			    Kogger.debug(KETBomPartUtil.class, "tmpS7 childPart2[" + x + "]====>" + tmpS7);
			}
		    }

		    Kogger.debug(KETBomPartUtil.class, "tmpR7====>" + tmpR7);
		    Kogger.debug(KETBomPartUtil.class, "tmpS7====>" + tmpS7);

		    if (tmpR7.equals("") || tmpS7.equals("")) {
			reqMap.put("partNo", part_No);
			reqMap.put("ecoNo", ecoNumber);
			reqMap.put("checkType", "type7");
			if (!bomUtil.bomValidationAssist(reqMap)) {
			    checkdata7 += part_No + ",";
			}
		    }
		}

		if (check8Hash.containsKey(part_No)) {
		    Hashtable check8H = (Hashtable) check8Hash.get(part_No);
		    String rweight = "";
		    String sweight = "";
		    String spartno = "";
		    if (check8H.containsKey("RWeight"))
			rweight = (String) check8H.get("RWeight");
		    if (check8H.containsKey("SWeight"))
			sweight = (String) check8H.get("SWeight");
		    if (check8H.containsKey("SPartNo"))
			spartno = (String) check8H.get("SPartNo");

		    double irw = 0.0;
		    double isw = 0.0;

		    if (!rweight.equals(""))
			irw = Math.abs(Double.parseDouble(rweight));
		    if (!sweight.equals(""))
			isw = Math.abs(Double.parseDouble(sweight));

		    if (irw <= isw) {
			checkdata8 += part_No + ",";
		    }

		    if (!spartno.equals("") && isw == 0.0) {
			checkdata8 += spartno + ",";
		    }

		}

		Enumeration keys = childPartsHash.keys();
		while (keys != null && keys.hasMoreElements()) {
		    String keyInfo = (String) keys.nextElement();

		    if (!keyInfo.equals(bom_path)) {
			String pnum = keyInfo.substring(keyInfo.lastIndexOf("^") + 1, keyInfo.length() - 1);

			String ochildList = (String) childPartsHash.get(keyInfo);
			if (parent_No.equals(pnum)) {

			    Kogger.debug(KETBomPartUtil.class, pnum + "================" + parent_No);
			    if (!childList.equals(ochildList) && (childList.indexOf(ochildList) != -1 || ochildList.indexOf(childList) != -1) && checkdata3.indexOf(parent_No) == -1) {
				checkdata3 += parent_No + ",";
			    }
			}

		    }

		}

		if (!part_No.startsWith("H") && childPart2.length > 0) {
		    WTPart part = PartBaseHelper.service.getLatestPart(part_No);
		    spPartType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
		    ptype = PartTypeEnum.toPartTypeEnum(spPartType);
		    if (PartTypeEnum.HAWA == ptype) {
			checkdata9 += part_No + ",";
		    }

		}

	    }

	    Kogger.debug(KETBomPartUtil.class, "checkdata1===>" + checkdata1);
	    Kogger.debug(KETBomPartUtil.class, "checkdata2===>" + checkdata2);
	    Kogger.debug(KETBomPartUtil.class, "checkdata3===>" + checkdata3);
	    Kogger.debug(KETBomPartUtil.class, "checkdata4===>" + checkdata4);
	    Kogger.debug(KETBomPartUtil.class, "checkdata5===>" + checkdata5);
	    Kogger.debug(KETBomPartUtil.class, "checkdata6===>" + checkdata6);
	    Kogger.debug(KETBomPartUtil.class, "checkdata7===>" + checkdata7);
	    Kogger.debug(KETBomPartUtil.class, "checkdata8===>" + checkdata8);
	    Kogger.debug(KETBomPartUtil.class, "checkdata9===>" + checkdata9);

	    // 1. 모부품의 동일한 레벨에 같은 부품이 사용되었는지 체크 ---- 동일레벨에 동일부품이 있는가
	    // 2. 모부품이 하위 자부품으로 재사용되었는지 체크 ---- 모부품이 자신의 자부품으로 사용되었는가
	    // 3. 동일한 모부품이 서로 다른 구조의 자부품으로 사용되었는지 체크 ---- 모부품이 다른 모부품의 자부품으로 사용되었는가
	    // 4. 부품에 연결된 비철/수지 원재료 코드 밑에 올바른 스크랩 코드가 존재하는지 체크 ---- R11*, R20* 부품과 동레벨에 S* 부품이 있는가
	    // 5. 삭제된 부품이 존재하는지 체크 ---- 삭제 속성 값을 가진 부품이 있는가
	    // 6. 터미널 반제품의 경우 자부품에 후도금 부품코드가 존재하는지 체크 (품번 도금사양이 -2 일 경우) ---- H7*-2 부품의 자부품으로 H7*A-2 부품이 있는가
	    // 7. 도금전 터미널 반제품 자부품으로 원재료, 스크랩 코드 존재하는지 체크 ---- H7*A-2 부품의 자부품으로 R*, S* 부품이 존재하는가
	    // 8. R20, R10 수량 정보가 동일레벨 Scrap 중량보다 큰 지 비교 (적으면 오류 처리), 스크랩 중량이 기입되었는지 체크 (누락시 인터페이스 오류남)
	    // 9. 상품 하위에 자부품 존재여부 체크(하위부품 구성 불가)

	} catch (Exception e) {
	    errLog = e.toString();
	    Kogger.error(KETBomPartUtil.class, e);
	}

	result.put("checkdata1", checkdata1);
	result.put("checkdata2", checkdata2);
	result.put("checkdata3", checkdata3);
	result.put("checkdata4", checkdata4);
	result.put("checkdata5", checkdata5);
	result.put("checkdata6", checkdata6);
	result.put("checkdata7", checkdata7);
	result.put("checkdata8", checkdata8);
	result.put("checkdata9", checkdata9);
	result.put("errLog", errLog);

	return result;
    }

    private static String getWeightFormat(String weight) {

	if (weight.indexOf(".") != -1) {
	    if (weight.substring(weight.indexOf(".") + 1).length() == 1) {
		weight = weight + "00";
	    }

	    if (weight.substring(weight.indexOf(".") + 1).length() == 2) {
		weight = weight + "0";
	    }
	} else {
	    weight = weight + ".000";
	}

	return weight;
    }

    /**
     * BOM - 재질 중량 업데이트
     * 
     * @param params
     * @return
     * @메소드명 : getBomWeightMaterial
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static List<Map<String, Object>> getBomWeightMaterial(Hashtable params) {

	String errLog = "";
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

	KETBOMQueryBean bean = new KETBOMQueryBean();
	NumberFormat formater = new DecimalFormat("###.###");

	String logEcoOid = null;
	WTChangeOrder2 eco = null;
	String ecoId = (String) params.get("ecoNumber");
	if (ecoId != null) {
	    ecoId = ecoId.replace("ECO-", "");
	    ProdEcoBeans beans = new ProdEcoBeans();
	    eco = beans.getEcoByNo(ecoId);
	    if (eco != null) {
		logEcoOid = CommonUtil.getOIDString(eco);
	    }
	}

	try {

	    String partNumber = "";
	    String boxQty = "";
	    String ecoNumber = (String) params.get("ecoNumber");
	    String gubun = (String) params.get("gubun");
	    String partType = (String) params.get("partType");

	    String[] partNo = (String[]) params.get("partNo");
	    String[] index = (String[]) params.get("index");
	    String[] lvl = (String[]) params.get("lvl");
	    String[] ict = (String[]) params.get("ict");
	    String[] seq = (String[]) params.get("seq");
	    String[] partName = (String[]) params.get("partName");
	    String[] qty = (String[]) params.get("qty");
	    String[] unit = (String[]) params.get("unit");
	    String[] rev = (String[]) params.get("rev");
	    String[] state = (String[]) params.get("state");
	    String[] econo = (String[]) params.get("econo");
	    String[] checkout = (String[]) params.get("checkout");
	    String[] reftop = (String[]) params.get("reftop");
	    String[] refbtm = (String[]) params.get("refbtm");
	    String[] material = (String[]) params.get("material");
	    String[] hardnessFrom = (String[]) params.get("hardnessFrom");
	    String[] hardnessTo = (String[]) params.get("hardnessTo");
	    String[] designDate = (String[]) params.get("designDate");
	    String[] parentNo = (String[]) params.get("parentNo");
	    String[] pver = (String[]) params.get("pver");

	    if (partNo == null || partNo.length == 0) {
		return result;
	    }

	    // Kogger.debug(getClass(), nCode.values().toString());
	    Kogger.debug(KETBomPartUtil.class, "partNo.length================================================" + partNo.length);

	    /**
	     * TKLEE - OPEN 후에 로직을 새로 짬 1. 부품 BOM JAVA OBJECT를 구성한다. 2. 해당 객체에 부품정보, BOM 수량정보, 재질정보를 입력한다. 3. 먼저 트리 끝 노드에 있는 중량정보를 상위로 롤업해서
	     * 계산한다. 반제품 아래 스크랩은 상위 반제품의 스크랩중량으로 반제품 아래 원재료는 상위 반제품의 총중량으로 반제품 아래 구매품은 중량(단위가 G이면 총중량으로 계산하고, EA면 부품마스터의 부품중량 * 개수)로 상위 반제품의
	     * 총중량으로 반제품 아래 반제품은 총중량, 스크랩중량, 부품중량 각각에 * 개수를 하여 계산한다. 위와 같은 식으로 계속 롤업하며, 제품은 반영하지 않고 반부품만 반영한다. 결과식에서 H43*, H47*, H48*, H49*
	     * H55*, H56* H61*, H62*, H63*, H67* H71*, H72*, H73*, H74*, H75*, H76*, H78*, H79* 로 시작하는 반제품만 업데이트 해준다.
	     */
	    MTreePartUtil util = new MTreePartUtil();
	    MTreeRootPart treeRootPart = util.makePartStructure(partNo, rev, lvl);

	    // 결과 조회
	    MTreePartController treePartController = new MTreePartController(treeRootPart);
	    treePartController.printAllPartsStructure();

	    if (true) {

		Iterator<MHierarchyComponent> iterator = new MCompositeIterator(treeRootPart.getIterator());
		while (iterator.hasNext()) {

		    MHierarchyComponent bomComponent = iterator.next();
		    try {

			// Kogger.debug( KETBomPartUtil.class, "### PART NO:" + bomComponent.getTreeDTO().getTreeNumber());

			// ////////////////////////////////////////
			// / AS - IS 정보 / 계산하기 전 기본 정보 넣기
			// ////////////////////////////////////////

			MaterSumDTO treeDTO = (MaterSumDTO) bomComponent.getTreeDTO();
			// 부품 정보 => MTreeCadUtil의 setTreeDTO에서 정의
			WTPart part = treeDTO.getWtPart();

			// 부품 번호 => MTreeCadUtil의 setTreeDTO에서 정의
			String partsNo = treeDTO.getPartsNo();
			// 부품 OID => MTreeCadUtil의 setTreeDTO에서 정의
			String partOid = treeDTO.getPartOid();
			// 부품 유형
			String nodePartType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
			treeDTO.setNodePartType(nodePartType);
			// 부품 claz 유형 - 구매품(PartClassificType=C) 여부 체크
			KETPartClassification claz = PartClazHelper.service.getPartClassification(part);
			String partClazType = claz == null ? "" : claz.getPartClassificType();
			if (claz == null) {
			    if (partsNo.startsWith("R1") || partsNo.startsWith("R2")) {
				partClazType = "";
			    } else {
				partClazType = "C";
			    }
			}
			treeDTO.setNodePartClazType(partClazType);
			// 원재료 OID - 구매품 제외
			String rpartOid = ("R".equals(nodePartType) && !"C".equals(nodePartType)) ? partOid : "";
			// level
			String ketLevel = lvl[treeDTO.getSortIdx()];
			treeDTO.setLvl(ketLevel);
			// 단위
			String ketUnit = unit[treeDTO.getSortIdx()];
			treeDTO.setUnit(ketUnit);
			// 수량정보(중량정보 포함)
			String kebBomQty = qty[treeDTO.getSortIdx()];
			treeDTO.setKetBomQty(kebBomQty);

			String quantity = kebBomQty;
			if (quantity.indexOf(".") == -1) {
			    quantity = quantity + ".000"; // 자리수 맞추기위해 0001을 붙였음.
			}
			if (quantity.indexOf("-.") != -1) {
			    quantity = "0" + quantity.substring(quantity.indexOf("."));
			}

			// 스크랩 -(마이너스) 제거
			quantity = quantity.replace("-", "");

			treeDTO.setQuantity(new BigDecimal(quantity));
			// double iqty = Math.abs(Double.parseDouble(quantity));
			// iqty = round(iqty, 3);
			// quantity = getWeightFormat(formater.format(iqty));
			// treeDTO.setKetBomQty(quantity);

			// 재질정보
			List<PartSpecEnum> list = PartClazHelper.service.getMaterialPartSpecEnum(part);
			PartSpecEnum specenum = (list != null && list.size() > 0) ? (PartSpecEnum) list.get(0) : null;
			String materialType = (specenum != null) ? Utility.checkNVL(specenum.getAttrCode()) : "";
			String preMaterial = (specenum != null) ? PartSpecGetter.getPartSpec(part, specenum) : "";
			String prematerialDesc = PartBaseHelper.service.getMaterialInfo(preMaterial);

			Kogger.debug(KETBomPartUtil.class, "[" + partsNo + "]specenum======>" + specenum);

			// 기존 중량 정보
			String prerawW = getWeightFormat(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpNetWeight));
			String prescrapW = getWeightFormat(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpScrabWeight));
			String pretotalW = getWeightFormat(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpTotalWeight));

			// 필요한 정보 세팅
			treeDTO.setrPartOid(rpartOid);
			treeDTO.setPrerawW(new BigDecimal(prerawW)); // 부품중량
			treeDTO.setPrescrapW(new BigDecimal(prescrapW)); // 스크랩중량
			treeDTO.setPretotalW(new BigDecimal(pretotalW)); // 총중량 H43*, H48* 만 단위가 ("EA")이면

			treeDTO.setMaterialType(materialType); // 기존 소스는 H43*, H48* 만 위로 => newtype
			treeDTO.setPreMaterial(preMaterial); // 원자재는 위로(반제품) => newMaterial
			treeDTO.setPrematerialDesc(prematerialDesc); // 원자재는 위로(반제품) => newMaterial2

			treeDTO.setNewType(materialType); // 동일하게 맞춰주어야 한다.

		    } catch (Exception e) {
			Kogger.error(KETBomPartUtil.class, e);
			throw e;
		    }
		}
	    }

	    if (true) {

		Iterator<MHierarchyComponent> iterator = new MCompositeIterator(treeRootPart.getIterator());
		while (iterator.hasNext()) {

		    MHierarchyComponent bomComponent = iterator.next();
		    try {

			// Kogger.debug( KETBomPartUtil.class, "### PART NO:" + bomComponent.getTreeDTO().getTreeNumber());

			// ////////////////////////////////////////
			// / 최하위 node에서는 상위Tree에서 계산하라고 요청하는 메소드를 call한다.
			// / 해당 메서드가 실행되면서 위로 계속 전파된다.
			// ////////////////////////////////////////
			if (bomComponent instanceof MTreePartItem) {
			    bomComponent.checkWeightMaterial();
			}

		    } catch (Exception e) {
			Kogger.error(KETBomPartUtil.class, e);
			throw e;
		    }
		}
	    }

	    treePartController.printAllPartsDetail();

	    // String checkPNo = " 61, 62, 63 "; - 최상위 제품은 하지 않기로 함
	    // 중량/재질 업데이트 대상
	    String checkNo = " H43, H47, H48, H49, H55, H56, H61, H62, H63, H67, H71, H72, H73, H74, H75, H76, H78, H79 ";

	    if (true) {

		Iterator<MHierarchyComponent> iterator = new MCompositeIterator(treeRootPart.getIterator());
		while (iterator.hasNext()) {

		    MHierarchyComponent bomComponent = iterator.next();
		    try {

			// ////////////////////////////////////////
			// / 계산된 정보에서 data 추출
			// ////////////////////////////////////////
			MaterSumDTO treeDTO = (MaterSumDTO) bomComponent.getTreeDTO();
			String partsNo = treeDTO.getPartsNo();
			if (partsNo == null || partsNo.length() < 3) {
			    continue;
			}
			String checker = partsNo.substring(0, 3);

			if (checkNo.indexOf(checker) != -1) {
			    Hashtable data = new Hashtable();

			    data.put("partNo", partsNo);
			    data.put("partOid", treeDTO.getPartOid());
			    data.put("rPartOid", treeDTO.getrPartOid());
			    data.put("old_weight", treeDTO.getPrerawW().toString());
			    data.put("old_sweight", treeDTO.getPrescrapW().toString());
			    data.put("old_tweight", treeDTO.getPretotalW().toString());
			    data.put("old_type", treeDTO.getMaterialType());
			    data.put("old_material", treeDTO.getPreMaterial());
			    data.put("old_material2", treeDTO.getPrematerialDesc());
			    data.put("new_weight", treeDTO.getNewWeight().toString());
			    data.put("new_sweight", treeDTO.getNewSweight().toString());
			    data.put("new_tweight", treeDTO.getNewTweight().toString());
			    data.put("new_type", treeDTO.getNewType());
			    data.put("new_material", treeDTO.getNewMaterial());
			    data.put("new_material2", treeDTO.getNewMaterial2());

			    // 총중량이 크거나 부품중량이 -가 나오는 경우에 저장 불가 하도록 수정함.
			    boolean isTotalWSmall = BigDecimalUtil.isPreBiggerThanPost(treeDTO.getNewWeight(), treeDTO.getNewTweight());
			    boolean isPartWeightMinus = BigDecimalUtil.isPreBiggerThanPost(new BigDecimal("0.000"), treeDTO.getNewWeight());

			    if (isTotalWSmall || isPartWeightMinus) {
				data.put("new_problem", "Y");
			    } else {
				data.put("new_problem", "N");
			    }

			    Kogger.debug(KETBomPartUtil.class, partsNo + "==>" + data.toString());
			    result.add(data);
			}

		    } catch (Exception e) {
			Kogger.error(KETBomPartUtil.class, e);
			throw e;
		    }
		}
	    }

	    KogDbUtil.log("재질/중량업데이트 Info 생성", "Success", (String) logEcoOid, (String) null, null, null);

	} catch (Exception e) {
	    errLog = e.toString();
	    Kogger.error(KETBomPartUtil.class, e);

	    KogDbUtil.log("재질/중량업데이트 Info 생성", "Fail", (String) logEcoOid, e, null, null);
	}

	return result;
    }

    // /**
    // * BOM - 재질 중량 업데이트
    // *
    // * @param params
    // * @return
    // * @메소드명 : getBomWeightMaterial
    // * @작성자 : yjlee
    // * @작성일 : 2015. 1. 27.
    // * @설명 :
    // * @수정이력 - 수정일,수정자,수정내용
    // *
    // */
    // public static List<Map<String, Object>> getBomWeightMaterial2(Hashtable params) {
    //
    // String errLog = "";
    // List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    //
    // KETBOMQueryBean bean = new KETBOMQueryBean();
    // NumberFormat formater = new DecimalFormat("###.###");
    //
    // String logEcoOid = null;
    // WTChangeOrder2 eco = null;
    // String ecoId = (String) params.get("ecoNumber");
    // if(ecoId != null){
    // ecoId = ecoId.replace("ECO-", "");
    // ProdEcoBeans beans = new ProdEcoBeans();
    // eco = beans.getEcoByNo(ecoId);
    // if(eco != null){
    // logEcoOid = CommonUtil.getOIDString(eco);
    // }
    // }
    //
    // try {
    //
    // String partNumber = "";
    // String boxQty = "";
    // String ecoNumber = (String) params.get("ecoNumber");
    // String gubun = (String) params.get("gubun");
    // String partType = (String) params.get("partType");
    //
    // String[] partNo = (String[]) params.get("partNo");
    // String[] index = (String[]) params.get("index");
    // String[] lvl = (String[]) params.get("lvl");
    // String[] ict = (String[]) params.get("ict");
    // String[] seq = (String[]) params.get("seq");
    // String[] partName = (String[]) params.get("partName");
    // String[] qty = (String[]) params.get("qty");
    // String[] unit = (String[]) params.get("unit");
    // String[] rev = (String[]) params.get("rev");
    // String[] state = (String[]) params.get("state");
    // String[] econo = (String[]) params.get("econo");
    // String[] checkout = (String[]) params.get("checkout");
    // String[] reftop = (String[]) params.get("reftop");
    // String[] refbtm = (String[]) params.get("refbtm");
    // String[] material = (String[]) params.get("material");
    // String[] hardnessFrom = (String[]) params.get("hardnessFrom");
    // String[] hardnessTo = (String[]) params.get("hardnessTo");
    // String[] designDate = (String[]) params.get("designDate");
    // String[] parentNo = (String[]) params.get("parentNo");
    // String[] pver = (String[]) params.get("pver");
    //
    // // Kogger.debug(getClass(), nCode.values().toString());
    //
    // /*
    // * Set keys = nCode.keySet();
    // *
    // * Iterator<String> mkey = keys.iterator();
    // *
    // * String materialArr = ""; int ncCnt = 0; while(mkey.hasNext()) { ncCnt++; //Kogger.debug(getClass(), mkey.next()); String numCode =
    // * (String)mkey.next(); String numDesc = (String)nCode.get(numCode);
    // *
    // * materialArr+="[\""+numCode+"\",\""+numDesc+"\"]"; if(ncCnt!=keys.size()) materialArr+=","; }
    // */
    //
    // Kogger.debug(KETBomPartUtil.class, "partNo.length================================================" + partNo.length);
    //
    // // String checkPNo = " 61, 62, 63 ";
    // String checkNo = " H61, H62, H63, H67, H71, H72, H73, H74, H75, H76, H78, H79, H55, H56, H47, H49 ";
    // String checkNo2 = " H43, H48 ";
    //
    // Hashtable partData = new Hashtable();
    //
    // for (int i = 0; i < index.length; i++) {
    //
    // Kogger.debug(KETBomPartUtil.class, "==>" + i);
    // Hashtable data = new Hashtable();
    // String partsNo = partNo[i];
    // String partsRev = rev[i];
    // String parentPartNo = parentNo[i];
    // String parentPartRev = pver[i];
    // // String newMaterial = material[i];
    // String unitData = unit[i];
    //
    // Kogger.debug(KETBomPartUtil.class, "partNo[" + i + "]================================================" + partNo[i]);
    //
    // String partOid = bean.getPartOid(partsNo, partsRev);
    // Kogger.debug(KETBomPartUtil.class, "partOid==>" + partOid);
    // WTPart part = getPart(partOid);
    //
    // List list = PartClazHelper.service.getMaterialPartSpecEnum(part);
    //
    // PartSpecEnum specenum = null;
    // if (list != null && list.size() > 0)
    // specenum = (PartSpecEnum) list.get(0);
    //
    // String materialType = "";
    // if (specenum != null) {
    // materialType = Utility.checkNVL(specenum.getAttrCode());
    // }
    //
    // Kogger.debug(KETBomPartUtil.class, "[" + partsNo + "]specenum======>" + specenum);
    // // HashMap nCode = new HashMap();
    // // nCode = (HashMap) getNumberCode(specenum.toString());
    //
    // String quantity = qty[i];
    //
    // if (quantity.indexOf(".") == -1) {
    // quantity = quantity + ".000"; // 자리수 맞추기위해 0001을 붙였음.
    // }
    //
    // if (quantity.indexOf("-.") != -1)
    // quantity = "0" + quantity.substring(quantity.indexOf("."));
    //
    // double iqty = Math.abs(Double.parseDouble(quantity));
    //
    // iqty = round(iqty, 3);
    //
    // quantity = getWeightFormat(formater.format(iqty));
    //
    // String checker = partsNo.substring(0, 3);
    //
    // String pchecker = "";
    //
    // if (!parentPartNo.equals("") && parentPartNo.length() > 3)
    // pchecker = parentPartNo.substring(0, 3);
    //
    // // H61, H62, H63, H67, H71, H72, H73, H74, H75, H76, H78, H79, H55, H56, H47, H49
    // if (checkNo.indexOf(checker) != -1) {
    //
    // Kogger.debug(KETBomPartUtil.class, "checker================================================>" + checker);
    // String prerawW = getWeightFormat(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpNetWeight));
    // String prescrapW = getWeightFormat(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpScrabWeight));
    // String pretotalW = getWeightFormat(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpTotalWeight));
    // String preMaterial = "";
    // if (specenum != null)
    // preMaterial = PartSpecGetter.getPartSpec(part, specenum);
    //
    // Kogger.debug(KETBomPartUtil.class, "partsNo===>" + partsNo);
    // Kogger.debug(KETBomPartUtil.class, "prerawW===>" + prerawW);
    // Kogger.debug(KETBomPartUtil.class, "prescrapW===>" + prescrapW);
    // Kogger.debug(KETBomPartUtil.class, "pretotalW===>" + pretotalW);
    // Kogger.debug(KETBomPartUtil.class, "preMaterial===>" + preMaterial);
    // Kogger.debug(KETBomPartUtil.class, "old_type===>" + materialType);
    //
    // String prematerialDesc = PartBaseHelper.service.getMaterialInfo(preMaterial);
    //
    // data.put("partNo", partsNo);
    // data.put("partOid", partOid);
    // data.put("rPartOid", "");
    // data.put("old_weight", prerawW);
    // data.put("old_sweight", prescrapW);
    // data.put("old_tweight", pretotalW);
    // data.put("old_type", materialType);
    // data.put("old_material", preMaterial);
    // data.put("old_material2", prematerialDesc);
    // data.put("new_weight", "0.000");
    // data.put("new_sweight", "0.000");
    // data.put("new_tweight", "0.000");
    // data.put("new_type", "");
    // data.put("new_material", "");
    // data.put("new_material2", "");
    //
    // partData.put(partsNo, data);
    // }
    //
    // // H61, H62, H63, H67, H71, H72, H73, H74, H75, H76, H78, H79, H55, H56, H47, H49
    // if (checkNo.indexOf(pchecker) != -1) {
    //
    // Kogger.debug(KETBomPartUtil.class, "pchecker================================================>" + pchecker);
    //
    // // 총중량
    // if (partsNo.startsWith("R")) {
    //
    // data = (Hashtable) partData.get(parentPartNo);
    // String newMaterial = "";
    // if (specenum != null) {
    // newMaterial = PartSpecGetter.getPartSpec(part, specenum);
    // String materialDesc = PartBaseHelper.service.getMaterialInfo(newMaterial);
    // data.put("rPartOid", partOid);
    // data.put("new_type", materialType);
    // data.put("new_material", newMaterial);
    // data.put("new_material2", materialDesc);
    // }
    //
    // if (data.containsKey("new_tweight")) {
    // // Kogger.debug(getClass(), "순중량===>" + quantity.substring(quantity.indexOf(".") + 1));
    //
    // data.put("new_tweight", quantity);
    // }
    //
    // if (data.containsKey("new_weight")) {
    // String tmp = (String) data.get("new_weight");
    // double itmp = Math.abs(Double.parseDouble(tmp)) + iqty;
    // String stmp = getWeightFormat(formater.format(itmp));
    //
    // data.put("new_weight", stmp);
    // } else {
    //
    // quantity = getWeightFormat(quantity);
    // data.put("new_weight", quantity);
    // }
    //
    // partData.put(parentPartNo, data);
    //
    // }
    //
    // // 스크랩중량
    // if (partsNo.startsWith("S")) {
    //
    // data = (Hashtable) partData.get(parentPartNo);
    //
    // if (data.containsKey("new_sweight")) {
    // data.put("new_sweight", quantity);
    // }
    //
    // if (data.containsKey("new_weight")) {
    // String tmp = (String) data.get("new_weight");
    // double itmp = Math.abs(Double.parseDouble(tmp)) - iqty;
    // String stmp = getWeightFormat(formater.format(itmp));
    //
    // data.put("new_weight", stmp);
    // } else {
    //
    // quantity = getWeightFormat(quantity);
    // data.put("new_weight", quantity);
    // }
    //
    // partData.put(parentPartNo, data);
    // }
    // }
    //
    // // H43, H48
    // if (checkNo2.indexOf(checker) != -1) {
    //
    // String prerawW = getWeightFormat(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpNetWeight));
    // String prescrapW = getWeightFormat(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpScrabWeight));
    // String pretotalW = getWeightFormat(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpTotalWeight));
    // String preMaterial = "";
    // if (specenum != null)
    // preMaterial = PartSpecGetter.getPartSpec(part, specenum);
    //
    // Kogger.debug(KETBomPartUtil.class, "partsNo===>" + partsNo);
    // Kogger.debug(KETBomPartUtil.class, "prerawW===>" + prerawW);
    // Kogger.debug(KETBomPartUtil.class, "prescrapW===>" + prescrapW);
    // Kogger.debug(KETBomPartUtil.class, "pretotalW===>" + pretotalW);
    // Kogger.debug(KETBomPartUtil.class, "preMaterial===>" + preMaterial);
    // String prematerialDesc = PartBaseHelper.service.getMaterialInfo(preMaterial);
    //
    // data.put("partNo", partsNo);
    // data.put("partOid", partOid);
    // data.put("rPartOid", "");
    // data.put("old_weight", prerawW);
    // data.put("old_sweight", prescrapW);
    // data.put("old_tweight", pretotalW);
    // data.put("old_type", materialType);
    // data.put("old_material", preMaterial);
    // data.put("old_material2", prematerialDesc);
    // data.put("new_weight", "0.000");
    // data.put("new_sweight", "0.000");
    // data.put("new_tweight", "0.000");
    // data.put("new_type", "");
    // data.put("new_material", "");
    // data.put("new_material2", "");
    //
    // partData.put(partsNo, data);
    // }
    //
    // // H43, H48
    // if (checkNo2.indexOf(pchecker) != -1) {
    //
    // // 순중량
    // if (partsNo.startsWith("R")) {
    //
    // data = (Hashtable) partData.get(parentPartNo);
    // String newrawW = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpNetWeight);
    // String newMaterial = "";
    //
    // if (specenum != null) {
    // newMaterial = PartSpecGetter.getPartSpec(part, specenum);
    // String newmaterialDesc = PartBaseHelper.service.getMaterialInfo(newMaterial);
    //
    // Kogger.debug(KETBomPartUtil.class, "newMaterial===>" + newMaterial);
    // Kogger.debug(KETBomPartUtil.class, "new_type===>" + materialType);
    // Kogger.debug(KETBomPartUtil.class, "newmaterialDesc===>" + newmaterialDesc);
    //
    // data.put("rPartOid", partOid);
    // data.put("new_type", materialType);
    // data.put("new_material", newMaterial);
    // data.put("new_material2", newmaterialDesc);
    // }
    //
    // // data = (Hashtable) partData.get(parentPartNo);
    //
    // if (data.containsKey("new_tweight")) {
    // String tmp = (String) data.get("new_tweight");
    // double itmp = 0;
    //
    // if (unitData.indexOf("EA") != -1) {
    // itmp = Math.abs(Double.parseDouble(tmp)) + Math.abs(Double.parseDouble(newrawW)) * iqty;
    // String stmp = getWeightFormat(formater.format(itmp));
    //
    // data.put("new_tweight", stmp);
    // } else {
    // itmp = Double.parseDouble(tmp) + iqty;
    // String stmp = getWeightFormat(formater.format(itmp));
    //
    // data.put("new_tweight", stmp);
    // }
    // } else {
    // quantity = getWeightFormat(quantity);
    // data.put("new_tweight", quantity);
    // }
    //
    // if (data.containsKey("new_weight")) {
    // String tmp = (String) data.get("new_tweight");
    // String tmp2 = (String) data.get("new_weight");
    // double itmp = Math.abs(Double.parseDouble(tmp)) + Math.abs(Double.parseDouble(tmp2));
    //
    // String stmp = getWeightFormat(formater.format(itmp));
    // data.put("new_weight", stmp);
    // } else {
    // quantity = getWeightFormat(quantity);
    // data.put("new_weight", quantity);
    // }
    //
    // partData.put(parentPartNo, data);
    //
    // }
    //
    // // 스크랩중량
    // if (partsNo.startsWith("S")) {
    //
    // data = (Hashtable) partData.get(parentPartNo);
    //
    // if (data.containsKey("new_sweight")) {
    //
    // data.put("new_sweight", quantity);
    // }
    //
    // if (data.containsKey("new_weight")) {
    // String tmp = (String) data.get("new_weight");
    // double itmp = Double.parseDouble(tmp) - iqty;
    //
    // String stmp = getWeightFormat(formater.format(itmp));
    // data.put("new_weight", stmp);
    // } else {
    // quantity = getWeightFormat(quantity);
    // data.put("new_weight", quantity);
    // }
    //
    // partData.put(parentPartNo, data);
    // }
    // }
    //
    // }
    //
    // KogDbUtil.log("재질/중량업데이트", "Success", (String)logEcoOid, (String)null, null, null);
    //
    // Enumeration keys = partData.keys();
    //
    // while (keys != null && keys.hasMoreElements()) {
    // String keyInfo = (String) keys.nextElement();
    // Hashtable data = (Hashtable) partData.get(keyInfo);
    //
    // Kogger.debug(KETBomPartUtil.class, keyInfo + "==>" + data.toString());
    // result.add(data);
    //
    // }
    //
    // } catch (Exception e) {
    // errLog = e.toString();
    // Kogger.error(KETBomPartUtil.class, e);
    //
    // KogDbUtil.log("재질/중량업데이트", "Fail", (String)logEcoOid, e, null, null);
    // }
    //
    // return result;
    // }

    public static double round(double f, int n) {
	return (double) (Math.round(f * Math.pow(10, n)) / Math.pow(10, n));
    }

    public Vector searchItem(String itemCode) {
	Vector itemVector = new Vector();
	try {

	    QuerySpec qs = new QuerySpec(WTPart.class);
	    // qs.appendWhere(new SearchCondition(WTPart.class, WTPart.NAME, false), new int[] { 0 });

	    if (itemCode != null && itemCode.length() != 0) {
		if (itemCode.indexOf("*") > -1 || itemCode.indexOf("%") > -1) {
		    itemCode = itemCode.replace("*", "%");
		    // qs.appendAnd();
		    qs.appendWhere(new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.LIKE, itemCode), new int[] { 0 });
		} else {
		    // qs.appendAnd();
		    qs.appendWhere(new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, itemCode), new int[] { 0 });
		}
	    }

	    QueryResult qr = null;

	    Object obj = new LatestConfigSpec();
	    qs = ((ConfigSpec) (obj)).appendSearchCriteria(qs);

	    // Kogger.debug(getClass(), "================ searchItem Query ==================");
	    // Kogger.debug(getClass(), qs.toString());
	    // Kogger.debug(getClass(), "================ searchItem Query ==================");

	    qr = PersistenceHelper.manager.find((StatementSpec) qs);
	    qr = ((ConfigSpec) (obj)).process(qr);

	    Kogger.debug(getClass(), "--->> qr.size() : " + qr.size());

	    WTPart item = null;

	    while (qr.hasMoreElements()) {
		item = (WTPart) qr.nextElement();
		itemVector.addElement(item);
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return itemVector;
    }

    public static void main(String[] args) {
	KETBomPartUtil util = new KETBomPartUtil();
	util.searchItem("S10");
    }
}
