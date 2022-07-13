package ext.ket.project.gate.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import ext.ket.project.gate.entity.GateAttribute;
import ext.ket.project.gate.entity.TemplateAssessSheet;
import ext.ket.project.gate.entity.TemplateGateCheckSheet;
import ext.ket.project.gate.entity.TemplateGateCheckSheetDTO;
import ext.ket.project.gate.entity.TmplAssShtTmplGateChkLink;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SessionUtil;

public class StandardTemplateGateCheckSheetService extends StandardManager implements TemplateGateCheckSheetService {
    private static final long serialVersionUID = 1L;

    public static StandardTemplateGateCheckSheetService newStandardTemplateGateCheckSheetService() throws WTException {
	StandardTemplateGateCheckSheetService instance = new StandardTemplateGateCheckSheetService();
	instance.initialize();
	return instance;
    }

    /**
     * 평가항목 조회화면에서 평가항목명, 달성기준 조건에 대한 쿼리스펙을 리턴하는 함수(현재 미사용)
     * 
     * @param TemplateAssessSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : getQuery
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQuery(TemplateGateCheckSheetDTO paramObject) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx = query.appendClassList(TemplateGateCheckSheet.class, true);
	if (!StringUtil.isTrimToEmpty(paramObject.getCheckSheetName())) {
	    sc = new SearchCondition(TemplateGateCheckSheet.class, TemplateGateCheckSheet.CHECK_SHEET_NAME, SearchCondition.LIKE, "%"
		    + paramObject.getCheckSheetName());
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getAchieveBase())) {
	    sc = new SearchCondition(TemplateGateCheckSheet.class, TemplateGateCheckSheet.ACHIEVE_BASE, SearchCondition.LIKE, "%"
		    + paramObject.getAchieveBase());
	    query.appendWhere(sc, new int[] { idx });
	}
	// if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	// if (paramObject.getSortName().startsWith("-")) {
	// SearchUtil.setOrderBy(query, TemplateGateCheckSheet.class, idx, paramObject.getSortName().substring(1), true);
	// } else {
	// SearchUtil.setOrderBy(query, TemplateGateCheckSheet.class, idx, paramObject.getSortName(), false);
	// }
	// } else {
	// SearchUtil.setOrderBy(query, TemplateGateCheckSheet.class, idx, TemplateGateCheckSheet.CHECK_SHEET_NAME, true);
	// }
	SearchUtil.setOrderBy(query, TemplateGateCheckSheet.class, idx, TemplateGateCheckSheet.ORDER_NO, false);
	return query;
    }

    /**
     * 평가시트 OID 하위에 있는 모든 평가항목을 조회하는 쿼리스펙을 리턴하는 함수
     * 
     * @param pjtOid
     * @param TemplateGateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : getQueryGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryGateCheckSheet(String pjtOid, TemplateGateCheckSheetDTO paramObject) throws Exception {

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	// int idx_assess = query.appendClassList(TemplateAssessSheet.class, false);
	int idx_link = query.appendClassList(TmplAssShtTmplGateChkLink.class, false);
	int idx_check = query.appendClassList(TemplateGateCheckSheet.class, true);
	int idx_number = query.appendClassList(NumberCode.class, false);
	// int idx_tagt_link = query.addClassList(GateChkSheetTagtTypeLink.class, false);

	ClassAttribute ca1 = null;
	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;
	ClassAttribute ca4 = null;
	ClassAttribute ca5 = null;
	ClassAttribute ca6 = null;
	ClassAttribute ca7 = null;
	ClassAttribute ca8 = null;

	// ca1 = new ClassAttribute(TemplateAssessSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca2 = new ClassAttribute(TemplateGateCheckSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(TmplAssShtTmplGateChkLink.class, "roleAObjectRef.key.id");
	// ca4 = new ClassAttribute(TmplAssShtTmplGateChkLink.class, "roleBObjectRef.key.id");
	// ca5 = new ClassAttribute(GateChkSheetTagtTypeLink.class, "roleAObjectRef.key.id");
	// ca6 = new ClassAttribute(GateChkSheetTagtTypeLink.class, "roleBObjectRef.key.id");
	ca7 = new ClassAttribute(TemplateGateCheckSheet.class, "targetTypeReference.key.id");
	ca8 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	// sc = new SearchCondition(ca6, SearchCondition.EQUAL, ca7);
	// query.appendAnd();
	// query.appendWhere(sc, new int[] { idx_tagt_link, idx_check });

	sc = new SearchCondition(ca7, SearchCondition.EQUAL, ca8);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idx_check, idx_number });

	if (!StringUtil.isTrimToEmpty(pjtOid)) {
	    long pjtOidLong = 0;
	    pjtOid = pjtOid.substring(pjtOid.indexOf(":") + 1, pjtOid.length());
	    if (!StringUtil.isEmpty(pjtOid)) {
		pjtOidLong = Long.parseLong(pjtOid);
		sc = new SearchCondition(TmplAssShtTmplGateChkLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, pjtOidLong);
		query.appendAnd();
		query.appendWhere(sc, new int[] { idx_link });
	    }
	}

	// if (paramObject != null && !StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	//
	// if ("targetType".equals(paramObject.getSortName())) {
	// SearchUtil.setOrderBy(query, NumberCode.class, idx_number, "name", false);
	// } else if ("-targetType".equals(paramObject.getSortName())) {
	// SearchUtil.setOrderBy(query, NumberCode.class, idx_number, "name", true);
	// } else {
	// if (paramObject.getSortName().startsWith("-")) {
	// SearchUtil.setOrderBy(query, TemplateGateCheckSheet.class, idx_check, paramObject.getSortName().substring(1), true);
	// } else {
	// SearchUtil.setOrderBy(query, TemplateGateCheckSheet.class, idx_check, paramObject.getSortName(), false);
	// }
	// }
	//
	// } else {
	// SearchUtil.setOrderBy(query, TemplateGateCheckSheet.class, idx_check, TemplateGateCheckSheet.CHECK_SHEET_NAME, true);
	// }
	SearchUtil.setOrderBy(query, TemplateGateCheckSheet.class, idx_check, TemplateGateCheckSheet.ORDER_NO, false);
	return query;
    }

    /**
     * 평가시트 조회시 호출되는 함수(현재 미사용)
     * 
     * @param TemplateAssessSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : find
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<TemplateGateCheckSheet> find(TemplateGateCheckSheetDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<TemplateGateCheckSheet> assList = new ArrayList<TemplateGateCheckSheet>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    assList.add((TemplateGateCheckSheet) objArr[0]);
	}
	return assList;
    }

    /**
     * 평가시트 OID 하위에 있는 모든 평가항목을 조회하는 함수
     * 
     * @param pjtOid
     * @param paramObject
     *            TemplateGateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : findGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<TemplateGateCheckSheet> findGateCheckSheet(String pjtOid, TemplateGateCheckSheetDTO paramObject) throws Exception {
	// Vector codeVec = NumberCodeHelper.manager.getNumberCodeForQuery("GATETYPE");
	// ArrayList arrCodeInfo = new ArrayList();
	// if (codeVec != null) {
	// for (int i = 0; i < codeVec.size(); i++) {
	// NumberCode code = (NumberCode) codeVec.get(i);
	// arrCodeInfo.add(code.getCode());
	// // code.getCode(), code.getName(),
	// // code.getPersistInfo().getObjectIdentifier().getStringValue()
	// }
	// }

	QuerySpec query = getQueryGateCheckSheet(pjtOid, paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<TemplateGateCheckSheet> checkList = new ArrayList<TemplateGateCheckSheet>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    checkList.add((TemplateGateCheckSheet) objArr[0]);
	}
	return checkList;
    }

    /**
     * 평가항목을 삭제하는 함수
     * 
     * @param TemplateGateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : delete
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public TemplateGateCheckSheet delete(TemplateGateCheckSheetDTO paramObject) throws Exception {
	TemplateGateCheckSheet ass = (TemplateGateCheckSheet) CommonUtil.getObject(paramObject.getOid());
	return (TemplateGateCheckSheet) PersistenceHelper.manager.delete(ass);
    }

    /**
     * 평가항목을 삭제하는 함수
     * 
     * @param TemplateGateCheckSheet
     * @return
     * @throws Exception
     * @메소드명 : deleteGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void deleteGateCheckSheet(TemplateGateCheckSheet gateCheck) throws Exception {

	QuerySpec query = getQueryTemplateGateCheckLink(gateCheck.getPersistInfo().getObjectIdentifier().getStringValue());
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    TemplateGateCheckSheet tGateCheck = (TemplateGateCheckSheet) objArr[1];
	    // 링크확인
	    TmplAssShtTmplGateChkLink assGateLink = (TmplAssShtTmplGateChkLink) objArr[0];

	    // 링크 삭제
	    PersistenceHelper.manager.delete(assGateLink);

	    // Gate항목삭제
	    PersistenceHelper.manager.delete(tGateCheck);

	}
    }

    /**
     * 평가시트의 링크정보를 조회하는 QuerySpec 리턴
     * 
     * @param pjtOid
     *            평가시트 OID
     * @return
     * @throws Exception
     * @메소드명 : getQueryTemplateGateCheckLink
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryTemplateGateCheckLink(String checkOid) throws Exception {

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx_link = query.appendClassList(TmplAssShtTmplGateChkLink.class, true);
	int idx_check = query.appendClassList(TemplateGateCheckSheet.class, true);

	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;

	ca2 = new ClassAttribute(TemplateGateCheckSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(TmplAssShtTmplGateChkLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	if (!StringUtil.isTrimToEmpty(checkOid)) {
	    long checkOidLong = 0;
	    checkOid = checkOid.substring(checkOid.indexOf(":") + 1, checkOid.length());
	    checkOidLong = Long.parseLong(checkOid);
	    sc = new SearchCondition(TmplAssShtTmplGateChkLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, checkOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	return query;
    }

    /**
     * 평가시트 조회시 호출되는 함수(페이지 쿼리)
     * 
     * @param TemplateAssessSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : findPaging
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public PageControl findPaging(TemplateGateCheckSheetDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		    pageSessionId);
	}
	return pageControl;
    }

    /**
     * 평가항목을 수정하는 함수
     * 
     * @param TemplateGateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : modify
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public TemplateGateCheckSheet modify(TemplateGateCheckSheetDTO paramObject) throws Exception {
	TemplateGateCheckSheet modify = (TemplateGateCheckSheet) CommonUtil.getObject(paramObject.getOid());

	// doc Check Out
	// TemplateAssessSheet assSheetCopy = checkoutDoc(modify);

	// desc 업데이트 정보세팅
	modify.setCheckSheetName(paramObject.getCheckSheetName());
	// modify.setDevDiv(paramObject.getDevDiv());
	// modify.setDevType(paramObject.getDevType());
	modify.setAchieveBase(paramObject.getAchieveBase());
	modify.setUnit(paramObject.getUnit());
	modify.setCriteria(paramObject.getCriteria());
	// modify.setState(paramObject.getStatus());
	// modify.setSheetDesc(paramObject.getSheetRev());
	// String isActive = paramObject.getIsActive();
	// if (StringUtil.isEmpty(isActive))
	// isActive = "0";
	// modify.setActive(isActive);

	// doc save
	modify = (TemplateGateCheckSheet) PersistenceHelper.manager.modify(modify);

	// doc Check In
	// assSheetCopy = (TemplateAssessSheet) wt.vc.wip.WorkInProgressHelper.service.checkin(assSheetCopy, "");
	// assSheetCopy = (TemplateAssessSheet) PersistenceHelper.manager.refresh(assSheetCopy, true, true); // refresh

	// KETContentHelper.service.updateContent(modify, paramObject);

	return modify;
    }

    /**
     * 평가항목을 저장하는 함수(현재미사용)
     * 
     * @param TemplateGateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : save
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public TemplateGateCheckSheet save(TemplateGateCheckSheetDTO paramObject) throws Exception {
	TemplateGateCheckSheet modify = TemplateGateCheckSheet.newTemplateGateCheckSheet();
	long todaytime = System.currentTimeMillis();
	SimpleDateFormat militime = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	String assessSeq = "ASS" + militime.format(new Date(todaytime));

	modify.setCheckSheetName(paramObject.getCheckSheetName());
	modify.setAchieveBase(paramObject.getAchieveBase());
	modify.setUnit(paramObject.getUnit());
	modify.setCriteria(paramObject.getCriteria());

	modify = (TemplateGateCheckSheet) PersistenceHelper.manager.save(modify);

	return modify;
    }

    /**
     * GRID에 추가된 평가항목(TemplateGateCheckSheetDTO)의 생성처리
     * 
     * @param pjtObjOid
     * @param TemplateGateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : saveProjectGate
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public TemplateGateCheckSheet saveProjectGate(String pjtObjOid, int ordNo, TemplateGateCheckSheetDTO paramObject) throws Exception {

	TemplateAssessSheet ass = (TemplateAssessSheet) CommonUtil.getObject(pjtObjOid);

	TemplateGateCheckSheet modify = TemplateGateCheckSheet.newTemplateGateCheckSheet();

	NumberCode targetNumber = new NumberCode();
	// 목표구분값 세팅
	String targetOid = (paramObject.getTargetType() == null) ? "" : (String) paramObject.getTargetType();
	if (!StringUtil.isEmpty(targetOid)) {
	    // if (targetOid.indexOf(":") <= 0)
	    // targetOid = "e3ps.common.code.NumberCode:" + targetOid;
	    // targetNumber = (NumberCode) CommonUtil.getObject(targetOid);
	    targetNumber = NumberCodeHelper.manager.getNumberCode("ASSESSTARGETTYPE", targetOid);
	    modify.setTargetType(targetNumber);
	}

	modify.setCheckSheetName(paramObject.getCheckSheetName());
	// modify.setDevDiv(paramObject.getDevDiv());
	// modify.setDevType(paramObject.getDevType());
	modify.setAchieveBase(paramObject.getAchieveBase());
	modify.setUnit(paramObject.getUnit());
	modify.setCriteria(paramObject.getCriteria());
	modify.setOrderNo(ordNo);
	// modify.setState(paramObject.getStatus());
	// modify.setSheetDesc(paramObject.getSheetRev());
	// modify.setActive(paramObject.getIsActive());
	GateAttribute gateAttrObj = GateAttribute.newGateAttribute();
	gateAttrObj.setSelect1(("1".equals(paramObject.getSelect1())) ? true : false);
	gateAttrObj.setSelect2(("1".equals(paramObject.getSelect2())) ? true : false);
	gateAttrObj.setSelect3(("1".equals(paramObject.getSelect3())) ? true : false);
	gateAttrObj.setSelect4(("1".equals(paramObject.getSelect4())) ? true : false);
	gateAttrObj.setSelect5(("1".equals(paramObject.getSelect5())) ? true : false);
	gateAttrObj.setSelect6(("1".equals(paramObject.getSelect6())) ? true : false);
	gateAttrObj.setSelect7(("1".equals(paramObject.getSelect7())) ? true : false);
	gateAttrObj.setSelect8(("1".equals(paramObject.getSelect8())) ? true : false);
	gateAttrObj.setSelect9(("1".equals(paramObject.getSelect9())) ? true : false);
	gateAttrObj.setSelect10(("1".equals(paramObject.getSelect10())) ? true : false);
	gateAttrObj.setSelect11(("1".equals(paramObject.getSelect11())) ? true : false);
	gateAttrObj.setSelect12(("1".equals(paramObject.getSelect12())) ? true : false);
	gateAttrObj.setSelect13(("1".equals(paramObject.getSelect13())) ? true : false);
	gateAttrObj.setSelect14(("1".equals(paramObject.getSelect14())) ? true : false);
	gateAttrObj.setSelect15(("1".equals(paramObject.getSelect15())) ? true : false);
	gateAttrObj.setSelect16(("1".equals(paramObject.getSelect16())) ? true : false);
	gateAttrObj.setSelect17(("1".equals(paramObject.getSelect17())) ? true : false);
	gateAttrObj.setSelect18(("1".equals(paramObject.getSelect18())) ? true : false);
	gateAttrObj.setSelect19(("1".equals(paramObject.getSelect19())) ? true : false);
	gateAttrObj.setSelect20(("1".equals(paramObject.getSelect20())) ? true : false);
	// ●선택한 항목만 값이 저장되도록 처리하기 2014.10.02
	if ("1".equals(paramObject.getSelect1()))
	    gateAttrObj.setTarget1(StringUtil.isEmpty((paramObject.getTarget1())) ? "" : paramObject.getTarget1());
	else
	    gateAttrObj.setTarget1("");
	if ("1".equals(paramObject.getSelect2()))
	    gateAttrObj.setTarget2(StringUtil.isEmpty((paramObject.getTarget2())) ? "" : paramObject.getTarget2());
	else
	    gateAttrObj.setTarget2("");
	if ("1".equals(paramObject.getSelect3()))
	    gateAttrObj.setTarget3(StringUtil.isEmpty((paramObject.getTarget3())) ? "" : paramObject.getTarget3());
	else
	    gateAttrObj.setTarget3("");
	if ("1".equals(paramObject.getSelect4()))
	    gateAttrObj.setTarget4(StringUtil.isEmpty((paramObject.getTarget4())) ? "" : paramObject.getTarget4());
	else
	    gateAttrObj.setTarget4("");
	if ("1".equals(paramObject.getSelect5()))
	    gateAttrObj.setTarget5(StringUtil.isEmpty((paramObject.getTarget5())) ? "" : paramObject.getTarget5());
	else
	    gateAttrObj.setTarget5("");
	if ("1".equals(paramObject.getSelect6()))
	    gateAttrObj.setTarget6(StringUtil.isEmpty((paramObject.getTarget6())) ? "" : paramObject.getTarget6());
	else
	    gateAttrObj.setTarget6("");
	if ("1".equals(paramObject.getSelect7()))
	    gateAttrObj.setTarget7(StringUtil.isEmpty((paramObject.getTarget7())) ? "" : paramObject.getTarget7());
	else
	    gateAttrObj.setTarget7("");
	if ("1".equals(paramObject.getSelect8()))
	    gateAttrObj.setTarget8(StringUtil.isEmpty((paramObject.getTarget8())) ? "" : paramObject.getTarget8());
	else
	    gateAttrObj.setTarget8("");
	if ("1".equals(paramObject.getSelect9()))
	    gateAttrObj.setTarget9(StringUtil.isEmpty((paramObject.getTarget9())) ? "" : paramObject.getTarget9());
	else
	    gateAttrObj.setTarget9("");
	if ("1".equals(paramObject.getSelect10()))
	    gateAttrObj.setTarget10(StringUtil.isEmpty((paramObject.getTarget10())) ? "" : paramObject.getTarget10());
	else
	    gateAttrObj.setTarget10("");
	if ("1".equals(paramObject.getSelect11()))
	    gateAttrObj.setTarget11(StringUtil.isEmpty((paramObject.getTarget11())) ? "" : paramObject.getTarget11());
	else
	    gateAttrObj.setTarget11("");
	if ("1".equals(paramObject.getSelect12()))
	    gateAttrObj.setTarget12(StringUtil.isEmpty((paramObject.getTarget12())) ? "" : paramObject.getTarget12());
	else
	    gateAttrObj.setTarget12("");
	if ("1".equals(paramObject.getSelect13()))
	    gateAttrObj.setTarget13(StringUtil.isEmpty((paramObject.getTarget13())) ? "" : paramObject.getTarget13());
	else
	    gateAttrObj.setTarget13("");
	if ("1".equals(paramObject.getSelect14()))
	    gateAttrObj.setTarget14(StringUtil.isEmpty((paramObject.getTarget14())) ? "" : paramObject.getTarget14());
	else
	    gateAttrObj.setTarget14("");
	if ("1".equals(paramObject.getSelect15()))
	    gateAttrObj.setTarget15(StringUtil.isEmpty((paramObject.getTarget15())) ? "" : paramObject.getTarget15());
	else
	    gateAttrObj.setTarget15("");
	if ("1".equals(paramObject.getSelect16()))
	    gateAttrObj.setTarget16(StringUtil.isEmpty((paramObject.getTarget16())) ? "" : paramObject.getTarget16());
	else
	    gateAttrObj.setTarget16("");
	if ("1".equals(paramObject.getSelect17()))
	    gateAttrObj.setTarget17(StringUtil.isEmpty((paramObject.getTarget17())) ? "" : paramObject.getTarget17());
	else
	    gateAttrObj.setTarget17("");
	if ("1".equals(paramObject.getSelect18()))
	    gateAttrObj.setTarget18(StringUtil.isEmpty((paramObject.getTarget18())) ? "" : paramObject.getTarget18());
	else
	    gateAttrObj.setTarget18("");
	if ("1".equals(paramObject.getSelect19()))
	    gateAttrObj.setTarget19(StringUtil.isEmpty((paramObject.getTarget19())) ? "" : paramObject.getTarget19());
	else
	    gateAttrObj.setTarget19("");
	if ("1".equals(paramObject.getSelect20()))
	    gateAttrObj.setTarget20(StringUtil.isEmpty((paramObject.getTarget20())) ? "" : paramObject.getTarget20());
	else
	    gateAttrObj.setTarget20("");
	modify.setAttr(gateAttrObj);

	// GateCheckSheet저장
	modify = (TemplateGateCheckSheet) PersistenceHelper.manager.save(modify);

	// 링크연결
	TmplAssShtTmplGateChkLink assGateLink = TmplAssShtTmplGateChkLink.newTmplAssShtTmplGateChkLink(modify, ass);
	// 저장
	assGateLink = (TmplAssShtTmplGateChkLink) PersistenceHelper.manager.save(assGateLink);
	// KETContentHelper.service.updateContent(modify, paramObject);

	return modify;
    }

    /**
     * GRID에 변경된 평가항목(TemplateGateCheckSheetDTO)의 수정처리
     * 
     * @param pjtObjOid
     * @param TemplateGateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : updateProjectGate
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public TemplateGateCheckSheet updateProjectGate(String pjtObjOid, int ordNo, TemplateGateCheckSheetDTO paramObject) throws Exception {

	TemplateAssessSheet ass = (TemplateAssessSheet) CommonUtil.getObject(pjtObjOid);

	// TemplateGateCheckSheet modify = TemplateGateCheckSheet.newTemplateGateCheckSheet();

	TemplateGateCheckSheet modify = (TemplateGateCheckSheet) CommonUtil.getObject(paramObject.getOid());
	Kogger.debug(getClass(), "update pjtObjOid:" + pjtObjOid);

	NumberCode targetNumber = new NumberCode();
	// 목표구분값 세팅
	String targetOid = (paramObject.getTargetType() == null) ? "" : (String) paramObject.getTargetType();
	if (!StringUtil.isEmpty(targetOid)) {
	    // if (targetOid.indexOf(":") <= 0)
	    // targetOid = "e3ps.common.code.NumberCode:" + targetOid;
	    // targetNumber = (NumberCode) CommonUtil.getObject(targetOid);
	    targetNumber = NumberCodeHelper.manager.getNumberCode("ASSESSTARGETTYPE", targetOid);
	    modify.setTargetType(targetNumber);
	}

	modify.setCheckSheetName(paramObject.getCheckSheetName());
	// modify.setDevDiv(paramObject.getDevDiv());
	// modify.setDevType(paramObject.getDevType());
	modify.setAchieveBase(paramObject.getAchieveBase());
	modify.setUnit(paramObject.getUnit());
	modify.setCriteria(paramObject.getCriteria());
	modify.setOrderNo(ordNo);
	// modify.setState(paramObject.getStatus());
	// modify.setSheetDesc(paramObject.getSheetRev());
	// modify.setActive(paramObject.getIsActive());
	GateAttribute gateAttrObj = GateAttribute.newGateAttribute();
	gateAttrObj.setSelect1(("1".equals(paramObject.getSelect1())) ? true : false);
	gateAttrObj.setSelect2(("1".equals(paramObject.getSelect2())) ? true : false);
	gateAttrObj.setSelect3(("1".equals(paramObject.getSelect3())) ? true : false);
	gateAttrObj.setSelect4(("1".equals(paramObject.getSelect4())) ? true : false);
	gateAttrObj.setSelect5(("1".equals(paramObject.getSelect5())) ? true : false);
	gateAttrObj.setSelect6(("1".equals(paramObject.getSelect6())) ? true : false);
	gateAttrObj.setSelect7(("1".equals(paramObject.getSelect7())) ? true : false);
	gateAttrObj.setSelect8(("1".equals(paramObject.getSelect8())) ? true : false);
	gateAttrObj.setSelect9(("1".equals(paramObject.getSelect9())) ? true : false);
	gateAttrObj.setSelect10(("1".equals(paramObject.getSelect10())) ? true : false);
	gateAttrObj.setSelect11(("1".equals(paramObject.getSelect11())) ? true : false);
	gateAttrObj.setSelect12(("1".equals(paramObject.getSelect12())) ? true : false);
	gateAttrObj.setSelect13(("1".equals(paramObject.getSelect13())) ? true : false);
	gateAttrObj.setSelect14(("1".equals(paramObject.getSelect14())) ? true : false);
	gateAttrObj.setSelect15(("1".equals(paramObject.getSelect15())) ? true : false);
	gateAttrObj.setSelect16(("1".equals(paramObject.getSelect16())) ? true : false);
	gateAttrObj.setSelect17(("1".equals(paramObject.getSelect17())) ? true : false);
	gateAttrObj.setSelect18(("1".equals(paramObject.getSelect18())) ? true : false);
	gateAttrObj.setSelect19(("1".equals(paramObject.getSelect19())) ? true : false);
	gateAttrObj.setSelect20(("1".equals(paramObject.getSelect20())) ? true : false);
	gateAttrObj.setTarget1(StringUtil.isEmpty((paramObject.getTarget1())) ? "" : paramObject.getTarget1());
	gateAttrObj.setTarget2(StringUtil.isEmpty((paramObject.getTarget2())) ? "" : paramObject.getTarget2());
	gateAttrObj.setTarget3(StringUtil.isEmpty((paramObject.getTarget3())) ? "" : paramObject.getTarget3());
	gateAttrObj.setTarget4(StringUtil.isEmpty((paramObject.getTarget4())) ? "" : paramObject.getTarget4());
	gateAttrObj.setTarget5(StringUtil.isEmpty((paramObject.getTarget5())) ? "" : paramObject.getTarget5());
	gateAttrObj.setTarget6(StringUtil.isEmpty((paramObject.getTarget6())) ? "" : paramObject.getTarget6());
	gateAttrObj.setTarget7(StringUtil.isEmpty((paramObject.getTarget7())) ? "" : paramObject.getTarget7());
	gateAttrObj.setTarget8(StringUtil.isEmpty((paramObject.getTarget8())) ? "" : paramObject.getTarget8());
	gateAttrObj.setTarget9(StringUtil.isEmpty((paramObject.getTarget9())) ? "" : paramObject.getTarget9());
	gateAttrObj.setTarget10(StringUtil.isEmpty((paramObject.getTarget10())) ? "" : paramObject.getTarget10());
	gateAttrObj.setTarget11(StringUtil.isEmpty((paramObject.getTarget11())) ? "" : paramObject.getTarget11());
	gateAttrObj.setTarget12(StringUtil.isEmpty((paramObject.getTarget12())) ? "" : paramObject.getTarget12());
	gateAttrObj.setTarget13(StringUtil.isEmpty((paramObject.getTarget13())) ? "" : paramObject.getTarget13());
	gateAttrObj.setTarget14(StringUtil.isEmpty((paramObject.getTarget14())) ? "" : paramObject.getTarget14());
	gateAttrObj.setTarget15(StringUtil.isEmpty((paramObject.getTarget15())) ? "" : paramObject.getTarget15());
	gateAttrObj.setTarget16(StringUtil.isEmpty((paramObject.getTarget16())) ? "" : paramObject.getTarget16());
	gateAttrObj.setTarget17(StringUtil.isEmpty((paramObject.getTarget17())) ? "" : paramObject.getTarget17());
	gateAttrObj.setTarget18(StringUtil.isEmpty((paramObject.getTarget18())) ? "" : paramObject.getTarget18());
	gateAttrObj.setTarget19(StringUtil.isEmpty((paramObject.getTarget19())) ? "" : paramObject.getTarget19());
	gateAttrObj.setTarget20(StringUtil.isEmpty((paramObject.getTarget20())) ? "" : paramObject.getTarget20());
	modify.setAttr(gateAttrObj);

	// GateCheckSheet저장
	modify = (TemplateGateCheckSheet) PersistenceHelper.manager.modify(modify);

	// 링크연결
	TmplAssShtTmplGateChkLink assGateLink = TmplAssShtTmplGateChkLink.newTmplAssShtTmplGateChkLink(modify, ass);

	// 저장
	if (assGateLink == null)
	    assGateLink = (TmplAssShtTmplGateChkLink) PersistenceHelper.manager.save(assGateLink);
	// KETContentHelper.service.updateContent(modify, paramObject);

	return modify;
    }

    /**
     * 체크아웃(현재 미사용)
     * 
     * @param pjtObjOid
     * @param TemplateGateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : checkoutDoc
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public TemplateAssessSheet checkoutDoc(TemplateAssessSheet obj) throws WTException {
	// ##begin checkoutDoc%4035514802E0.body preserve=yes
	wt.vc.wip.Workable workable = (wt.vc.wip.Workable) obj;
	wt.vc.wip.CheckoutLink checkOutLink = null;
	wt.vc.wip.Workable workingCopy = null;
	wt.vc.wip.Workable orgCopy = null;

	try {
	    if (!wt.vc.wip.WorkInProgressHelper.isWorkingCopy(workable)) {
		if (!wt.vc.wip.WorkInProgressHelper.isCheckedOut(workable)) {
		    wt.folder.Folder folder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
		    // Folder folder = FolderHelper.service.getFolder("/Default");
		    Kogger.debug(getClass(), "Folder is " + folder);
		    Kogger.debug(getClass(), "++++++++++++++++++++++ Document Check-out Started...");
		    Kogger.debug(getClass(), "checkOutLink Is: " + workable + " and Folder is : " + folder);
		    try {
			checkOutLink = (wt.vc.wip.CheckoutLink) wt.vc.wip.WorkInProgressHelper.service.checkout(workable, folder, "");
		    } catch (wt.util.WTPropertyVetoException wtpve) {
			Kogger.debug(getClass(), "++++++++++++++++++++++ Document Check-out wtpve error :" + wtpve.getLocalizedMessage());
			Kogger.error(getClass(), wtpve);
		    }
		    // get Original copy
		    orgCopy = checkOutLink.getOriginalCopy();

		    Kogger.debug(getClass(), "orgCopy is " + orgCopy);
		    // get worksing copy
		    workingCopy = checkOutLink.getWorkingCopy();

		    Kogger.debug(getClass(), "workingCopy is " + workingCopy);

		} else if (wt.vc.wip.WorkInProgressHelper.isCheckedOut(workable)) {
		    // get Original copy
		    orgCopy = wt.vc.wip.WorkInProgressHelper.service.originalCopyOf(workable);
		    // get working copy
		    workingCopy = wt.vc.wip.WorkInProgressHelper.service.workingCopyOf(workable);
		    Kogger.debug(getClass(), "workingCopy is " + workingCopy);
		}

	    } else if (wt.vc.wip.WorkInProgressHelper.isWorkingCopy(workable)) {
		workingCopy = workable;
	    }
	} catch (WTException wte) {
	    Kogger.error(getClass(), wte);

	    throw new WTException(wte.getMessage());
	}

	Kogger.debug(getClass(), "++++++++++++++++++++++ Document Check-outworkingCopy:"
	        + workingCopy.getPersistInfo().getObjectIdentifier().toString());

	return (TemplateAssessSheet) workingCopy;
    }
}
