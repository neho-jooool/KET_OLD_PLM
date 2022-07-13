package ext.ket.project.gate.service;

import java.util.ArrayList;
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
import ext.ket.project.gate.entity.AssSheetGateChkSheetLink;
import ext.ket.project.gate.entity.AssessSheet;
import ext.ket.project.gate.entity.GateAttribute;
import ext.ket.project.gate.entity.GateCheckSheet;
import ext.ket.project.gate.entity.GateCheckSheetDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SessionUtil;

public class StandardGateCheckSheetService extends StandardManager implements GateCheckSheetService {
    private static final long serialVersionUID = 1L;

    public static StandardGateCheckSheetService newStandardGateCheckSheetService() throws WTException {
	StandardGateCheckSheetService instance = new StandardGateCheckSheetService();
	instance.initialize();
	return instance;
    }

    /**
     * 평가항목 조회화면에서 평가항목명, 달성기준 조건에 대한 쿼리스펙을 리턴하는 함수(현재 미사용)
     * 
     * @param AssessSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : getQuery
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQuery(GateCheckSheetDTO paramObject) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx = query.appendClassList(GateCheckSheet.class, true);
	if (!StringUtil.isTrimToEmpty(paramObject.getCheckSheetName())) {
	    sc = new SearchCondition(GateCheckSheet.class, GateCheckSheet.CHECK_SHEET_NAME, SearchCondition.LIKE, "%"
		    + paramObject.getCheckSheetName());
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getAchieveBase())) {
	    sc = new SearchCondition(GateCheckSheet.class, GateCheckSheet.ACHIEVE_BASE, SearchCondition.LIKE, "%"
		    + paramObject.getAchieveBase());
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    if (paramObject.getSortName().startsWith("-")) {
		SearchUtil.setOrderBy(query, GateCheckSheet.class, idx, paramObject.getSortName().substring(1), true);
	    } else {
		SearchUtil.setOrderBy(query, GateCheckSheet.class, idx, paramObject.getSortName(), false);
	    }
	} else {
	    SearchUtil.setOrderBy(query, GateCheckSheet.class, idx, GateCheckSheet.CHECK_SHEET_NAME, true);
	}
	return query;
    }

    /**
     * 평가시트 조회시 호출되는 함수(현재 미사용)
     * 
     * @param AssessSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : find
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<GateCheckSheet> find(GateCheckSheetDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<GateCheckSheet> assList = new ArrayList<GateCheckSheet>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    assList.add((GateCheckSheet) objArr[0]);
	}
	return assList;
    }

    /**
     * 평가항목을 삭제하는 함수
     * 
     * @param GateCheckSheetDTO
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
    public GateCheckSheet delete(GateCheckSheetDTO paramObject) throws Exception {
	GateCheckSheet ass = (GateCheckSheet) CommonUtil.getObject(paramObject.getOid());
	return (GateCheckSheet) PersistenceHelper.manager.delete(ass);
    }

    /**
     * 평가시트 조회시 호출되는 함수(페이지 쿼리)
     * 
     * @param AssessSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : findPaging
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public PageControl findPaging(GateCheckSheetDTO paramObject) throws Exception {
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
     * @param GateCheckSheetDTO
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
    public GateCheckSheet modify(GateCheckSheetDTO paramObject) throws Exception {
	GateCheckSheet modify = (GateCheckSheet) CommonUtil.getObject(paramObject.getOid());

	// desc 업데이트 정보세팅
	modify.setCheckSheetName(paramObject.getCheckSheetName());
	modify.setAchieveBase(paramObject.getAchieveBase());
	modify.setUnit(paramObject.getUnit());
	modify.setCriteria(paramObject.getCriteria());

	// doc save
	modify = (GateCheckSheet) PersistenceHelper.manager.modify(modify);

	return modify;
    }

    /**
     * 평가항목을 저장하는 함수(현재미사용)
     * 
     * @param GateCheckSheetDTO
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
    public GateCheckSheet save(GateCheckSheetDTO paramObject) throws Exception {
	GateCheckSheet modify = GateCheckSheet.newGateCheckSheet();

	modify.setCheckSheetName(paramObject.getCheckSheetName());
	modify.setAchieveBase(paramObject.getAchieveBase());
	modify.setUnit(paramObject.getUnit());
	modify.setCriteria(paramObject.getCriteria());

	modify = (GateCheckSheet) PersistenceHelper.manager.save(modify);

	return modify;
    }

    /**
     * GRID에 추가된 평가항목(GateCheckSheetDTO)의 생성처리
     * 
     * @param pjtObjOid
     * @param GateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : saveProjectGate
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public GateCheckSheet saveProjectGate(String assObjOid, int ordNo, GateCheckSheetDTO paramObject) throws Exception {

	AssessSheet ass = (AssessSheet) CommonUtil.getObject(assObjOid);

	GateCheckSheet modify = GateCheckSheet.newGateCheckSheet();

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
	modify = (GateCheckSheet) PersistenceHelper.manager.save(modify);

	// 링크연결
	AssSheetGateChkSheetLink assGateLink = AssSheetGateChkSheetLink.newAssSheetGateChkSheetLink(modify, ass);
	// 저장
	assGateLink = (AssSheetGateChkSheetLink) PersistenceHelper.manager.save(assGateLink);

	return modify;
    }

    /**
     * GRID에 변경된 평가항목(GateCheckSheetDTO)의 수정처리
     * 
     * @param pjtObjOid
     * @param GateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : updateProjectGate
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public GateCheckSheet updateProjectGate(String assObjOid, int ordNo, GateCheckSheetDTO paramObject) throws Exception {

	AssessSheet ass = (AssessSheet) CommonUtil.getObject(assObjOid);

	// GateCheckSheet modify = GateCheckSheet.newGateCheckSheet();

	GateCheckSheet modify = (GateCheckSheet) CommonUtil.getObject(paramObject.getOid());
	Kogger.debug(getClass(), "update assObjOid:" + assObjOid);

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
	modify = (GateCheckSheet) PersistenceHelper.manager.modify(modify);

	// 링크연결
	AssSheetGateChkSheetLink assGateLink = AssSheetGateChkSheetLink.newAssSheetGateChkSheetLink(modify, ass);
	// 저장
	if (assGateLink == null)
	    assGateLink = (AssSheetGateChkSheetLink) PersistenceHelper.manager.save(assGateLink);
	// KETContentHelper.service.updateContent(modify, paramObject);

	return modify;
    }

    /**
     * 평가항목을 삭제하는 함수
     * 
     * @param GateCheckSheet
     * @return
     * @throws Exception
     * @메소드명 : deleteGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void deleteGateCheckSheet(GateCheckSheet gateCheck) throws Exception {

	// QuerySpec query = getQueryGateCheckLink(gateCheck.getPersistInfo().getObjectIdentifier().getStringValue());
	// QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);

	// gateCheck 대상 객체, ASSESS_ROLE: 링크된 객체, 링크객체, false : 링크된객체(true:해당객체)
	QueryResult qr = PersistenceHelper.manager.navigate(gateCheck, AssSheetGateChkSheetLink.ASSESS_ROLE,
	        AssSheetGateChkSheetLink.class, false);

	while (qr.hasMoreElements()) {
	    // Object[] objArr = (Object[]) qr.nextElement();
	    // GateCheckSheet tGateCheckSheet = (GateCheckSheet) objArr[1];
	    // 링크확인
	    AssSheetGateChkSheetLink assGateLink = (AssSheetGateChkSheetLink) qr.nextElement();
	    // assGateLink.getCheck();

	    // 링크 삭제
	    PersistenceHelper.manager.delete(assGateLink);

	}
	// Gate항목삭제
	PersistenceHelper.manager.delete(gateCheck);

    }

    /**
     * 평가항목의 링크정보를 조회하는 QuerySpec 리턴
     * 
     * @param checkOid
     *            평가항목 OID
     * @return
     * @throws Exception
     * @메소드명 : getQueryGateCheckLink
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryGateCheckLink(String checkOid) throws Exception {

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx_link = query.appendClassList(AssSheetGateChkSheetLink.class, true);
	int idx_check = query.appendClassList(GateCheckSheet.class, true);

	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;

	ca2 = new ClassAttribute(GateCheckSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(AssSheetGateChkSheetLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	if (!StringUtil.isTrimToEmpty(checkOid)) {
	    long checkOidLong = 0;
	    checkOid = checkOid.substring(checkOid.indexOf(":") + 1, checkOid.length());
	    checkOidLong = Long.parseLong(checkOid);
	    sc = new SearchCondition(AssSheetGateChkSheetLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, checkOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	return query;
    }

}