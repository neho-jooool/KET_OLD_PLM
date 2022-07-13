package ext.ket.project.gate.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.pds.StatementSpec;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.project.gate.entity.TemplateAssessSheet;
import ext.ket.project.gate.entity.TemplateAssessSheetDTO;
import ext.ket.project.gate.entity.TemplateGateCheckSheet;
import ext.ket.project.gate.entity.TmplAssShtTmplGateChkLink;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SessionUtil;

public class StandardTemplateAssessSheetService extends StandardManager implements TemplateAssessSheetService {
    private static final long serialVersionUID = 1L;

    public static StandardTemplateAssessSheetService newStandardTemplateAssessSheetService() throws WTException {
	StandardTemplateAssessSheetService instance = new StandardTemplateAssessSheetService();
	instance.initialize();
	return instance;
    }

    /**
     * 평가시트 조회화면에서 조건에 대한 쿼리스펙을 리턴하는 함수
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
    private QuerySpec getQuery(TemplateAssessSheetDTO paramObject) throws Exception {
	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;
	int idx = query.appendClassList(TemplateAssessSheet.class, true);
	int idx_number1 = query.appendClassList(NumberCode.class, false);
	int idx_number2 = query.appendClassList(NumberCode.class, false);
	int idx_number3 = query.appendClassList(NumberCode.class, false);
	int idx_number4 = query.appendClassList(KETPartClassification.class, false);

	// query.appendSelectAttribute(TemplateAssessSheet.DEV_TYPE, idx, false);
	// query.appendSelectAttribute(TemplateAssessSheet.DEV_DIV, idx, false);
	// query.appendSelectAttribute(TemplateAssessSheet.DIVISION, idx, false);
	// query.appendSelectAttribute(TemplateAssessSheet.PROD_CATEGORY, idx, false);
	// query.appendSelectAttribute(TemplateAssessSheet.SHEET_NAME, idx, false);
	// query.appendSelectAttribute(TemplateAssessSheet.SHEET_DESC, idx, false);
	// query.appendSelectAttribute(TemplateAssessSheet.ACTIVE, idx, false);
	// query.appendSelectAttribute(TemplateAssessSheet.CREATE_TIMESTAMP, idx, false);
	// query.appendSelectAttribute(TemplateAssessSheet.MODIFY_TIMESTAMP, idx, false);

	ClassAttribute ca1 = null;
	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;
	ClassAttribute ca4 = null;
	ClassAttribute ca5 = null;
	ClassAttribute ca6 = null;
	ClassAttribute ca7 = null;
	ClassAttribute ca8 = null;
	ca1 = new ClassAttribute(TemplateAssessSheet.class, "devDivReference.key.id");
	ca2 = new ClassAttribute(TemplateAssessSheet.class, "devTypeReference.key.id");
	ca3 = new ClassAttribute(TemplateAssessSheet.class, "divisionReference.key.id");
	ca4 = new ClassAttribute(TemplateAssessSheet.class, "partOid");
	ca5 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
	ca6 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
	ca7 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
	ca8 = new ClassAttribute(KETPartClassification.class, "thePersistInfo.theObjectIdentifier.id");

	sc = new SearchCondition(ca1, SearchCondition.EQUAL, ca5);
	sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idx, idx_number1 });

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca6);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idx, idx_number2 });

	sc = new SearchCondition(ca3, SearchCondition.EQUAL, ca7);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idx, idx_number3 });

	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.SUB_STRING, new ColumnExpression[] {
	        new ClassAttribute(TemplateAssessSheet.class, "partOid"), new ConstantExpression(Integer.valueOf(43)) });

	SQLFunction sqlfunction2 = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, sqlfunction);

	sc = new SearchCondition(sqlfunction2, SearchCondition.EQUAL, ca8);
	sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idx, idx_number4 });

	String arrSplit[] = null;
	if (!StringUtil.isTrimToEmpty(paramObject.getDevType())) {
	    // NumberCode devTypeNumber = new NumberCode();
	    // String devTypeOid = paramObject.getDevType();
	    // if (devTypeOid.indexOf(":") <= 0)
	    // devTypeOid = "e3ps.common.code.NumberCode:" + devTypeOid;
	    // devTypeNumber = (NumberCode) CommonUtil.getObject(devTypeOid);
	    String devTypeOid = paramObject.getDevType();
	    if (devTypeOid != null)
		arrSplit = devTypeOid.split(",");
	    if (arrSplit != null && arrSplit.length == 1) {
		sc = new SearchCondition(TemplateAssessSheet.class, "devTypeReference.key.id", SearchCondition.EQUAL,
		        Long.parseLong(arrSplit[0]));
	    } else if (arrSplit != null && arrSplit.length > 1) {
		long arrLongOid[] = new long[arrSplit.length];
		for (int i = 0; i < arrSplit.length; i++) {
		    arrLongOid[i] = Long.parseLong(arrSplit[i]);
		}
		ClassAttribute targetExpression = new ClassAttribute(TemplateAssessSheet.class, "devTypeReference.key.id");
		sc = new SearchCondition(targetExpression, SearchCondition.IN, new ArrayExpression(arrLongOid));
	    }

	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDevDiv())) {

	    String devDivOid = paramObject.getDevDiv();
	    arrSplit = null;
	    if (devDivOid != null)
		arrSplit = devDivOid.split(",");

	    if (arrSplit != null && arrSplit.length == 1) {
		sc = new SearchCondition(TemplateAssessSheet.class, "devDivReference.key.id", SearchCondition.EQUAL,
		        Long.parseLong(arrSplit[0]));
	    } else if (arrSplit != null && arrSplit.length > 1) {
		long arrLongOid[] = new long[arrSplit.length];
		for (int i = 0; i < arrSplit.length; i++) {
		    arrLongOid[i] = Long.parseLong(arrSplit[i]);
		}
		ClassAttribute targetExpression = new ClassAttribute(TemplateAssessSheet.class, "devDivReference.key.id");
		sc = new SearchCondition(targetExpression, SearchCondition.IN, new ArrayExpression(arrLongOid));

	    }

	    sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDivision())) {
	    String divisionOid = paramObject.getDivision();
	    arrSplit = null;
	    if (divisionOid != null)
		arrSplit = divisionOid.split(",");

	    if (arrSplit != null && arrSplit.length == 1) {
		sc = new SearchCondition(TemplateAssessSheet.class, "divisionReference.key.id", SearchCondition.EQUAL,
		        Long.parseLong(arrSplit[0]));
	    } else if (arrSplit != null && arrSplit.length > 1) {
		long arrLongOid[] = new long[arrSplit.length];
		for (int i = 0; i < arrSplit.length; i++) {
		    arrLongOid[i] = Long.parseLong(arrSplit[i]);
		}
		ClassAttribute targetExpression = new ClassAttribute(TemplateAssessSheet.class, "divisionReference.key.id");
		sc = new SearchCondition(targetExpression, SearchCondition.IN, new ArrayExpression(arrLongOid));
	    }
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx });
	}

	// if (!StringUtil.isTrimToEmpty(paramObject.getProdCategory())) {
	if (!StringUtil.isTrimToEmpty(paramObject.getPartMultiOids())) {
	    String partOids = paramObject.getPartMultiOids();
	    arrSplit = null;
	    if (partOids != null)
		arrSplit = partOids.split(",");

	    if (arrSplit != null && arrSplit.length == 1) {
		// String partOidPerStr = arrSplit[0];
		// if (partOidPerStr != null && partOidPerStr.indexOf(":") > 0) {
		// partOidPerStr = partOidPerStr.substring(partOidPerStr.indexOf(":") + 1);
		// }
		sc = new SearchCondition(TemplateAssessSheet.class, TemplateAssessSheet.PART_OID, SearchCondition.EQUAL, arrSplit[0]);
	    } else if (arrSplit != null && arrSplit.length > 1) {
		// String arrLongOid[] = new String[arrSplit.length];
		// for (int i = 0; i < arrSplit.length; i++) {
		// String arrSplitStr = arrSplit[i];
		// if (arrSplitStr != null && arrSplitStr.indexOf(":") > 0) {
		// arrSplitStr = arrSplitStr.substring(arrSplitStr.indexOf(":") + 1);
		// }
		// arrLongOid[i] = arrSplitStr;
		// }
		ClassAttribute targetExpression = new ClassAttribute(TemplateAssessSheet.class, TemplateAssessSheet.PART_OID);
		sc = new SearchCondition(targetExpression, SearchCondition.IN, new ArrayExpression(arrSplit));
	    }
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx });
	} else if (!StringUtil.isTrimToEmpty(paramObject.getPartMultiNames())) {
	    String partNames = paramObject.getPartMultiNames();
	    // arrSplit = null;
	    // if (partNames != null)
	    // arrSplit = partNames.split(",");

	    // if (arrSplit != null && arrSplit.length == 1) {
	    // sc = new SearchCondition(TemplateAssessSheet.class, TemplateAssessSheet.PART_OID, SearchCondition.EQUAL, arrSplit[0]);
	    // } else if (arrSplit != null && arrSplit.length > 1) {
	    // ClassAttribute targetExpression = new ClassAttribute(TemplateAssessSheet.class, TemplateAssessSheet.PART_OID);
	    // sc = new SearchCondition(targetExpression, SearchCondition.IN, new ArrayExpression(arrSplit));
	    // }
	    sc = new SearchCondition(KETPartClassification.class, KETPartClassification.CLASS_KR_NAME, SearchCondition.LIKE, "%"
		    + partNames + "%");
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_number4 });
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSheetName())) {
	    sc = new SearchCondition(TemplateAssessSheet.class, TemplateAssessSheet.SHEET_NAME, SearchCondition.LIKE, "%"
		    + paramObject.getSheetName());
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx });
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getActive())) {
	    String actives = paramObject.getActive();
	    arrSplit = null;
	    if (actives != null)
		arrSplit = actives.split(",");

	    if (arrSplit != null && arrSplit.length == 1) {
		sc = new SearchCondition(TemplateAssessSheet.class, TemplateAssessSheet.ACTIVE, SearchCondition.EQUAL, arrSplit[0]);
	    } else if (arrSplit != null && arrSplit.length > 1) {

		ClassAttribute targetExpression = new ClassAttribute(TemplateAssessSheet.class, TemplateAssessSheet.ACTIVE);
		// sc = new SearchCondition(targetExpression, SearchCondition.IN, new ArrayExpression(arrLongOid));
		sc = new SearchCondition(targetExpression, SearchCondition.IN, new ArrayExpression(arrSplit));
	    }
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx });
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getStartDate())) {
	    sc = new SearchCondition(TemplateAssessSheet.class, TemplateAssessSheet.CREATE_TIMESTAMP,
		    SearchCondition.GREATER_THAN_OR_EQUAL, Timestamp.valueOf(paramObject.getStartDate() + " 00:00:00.000000000"));
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx });
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getEndDate())) {
	    sc = new SearchCondition(TemplateAssessSheet.class, TemplateAssessSheet.CREATE_TIMESTAMP, SearchCondition.LESS_THAN_OR_EQUAL,
		    Timestamp.valueOf(paramObject.getEndDate() + " 23:59:59.999999999"));
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx });
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    if ("devDiv".equals(paramObject.getSortName())) {
		SearchUtil.setOrderBy(query, NumberCode.class, idx_number1, "name", false);
	    } else if ("-devDiv".equals(paramObject.getSortName())) {
		SearchUtil.setOrderBy(query, NumberCode.class, idx_number1, "name", true);
	    } else if ("devType".equals(paramObject.getSortName())) {
		SearchUtil.setOrderBy(query, NumberCode.class, idx_number2, "name", false);
	    } else if ("-devType".equals(paramObject.getSortName())) {
		SearchUtil.setOrderBy(query, NumberCode.class, idx_number2, "name", true);
	    } else if ("division".equals(paramObject.getSortName())) {
		SearchUtil.setOrderBy(query, NumberCode.class, idx_number3, "name", false);
	    } else if ("-division".equals(paramObject.getSortName())) {
		SearchUtil.setOrderBy(query, NumberCode.class, idx_number3, "name", true);
		// } else if ("prodCategory".equals(paramObject.getSortName())) {
		// SearchUtil.setOrderBy(query, NumberCode.class, idx_number4, "name", false);
		// } else if ("-prodCategory".equals(paramObject.getSortName())) {
		// SearchUtil.setOrderBy(query, NumberCode.class, idx_number4, "name", true);
	    } else {
		if (paramObject.getSortName().startsWith("-")) {
		    SearchUtil.setOrderBy(query, TemplateAssessSheet.class, idx, paramObject.getSortName().substring(1), true);
		} else {
		    SearchUtil.setOrderBy(query, TemplateAssessSheet.class, idx, paramObject.getSortName(), false);
		}
	    }
	} else {
	    SearchUtil.setOrderBy(query, TemplateAssessSheet.class, idx, TemplateAssessSheet.CREATE_TIMESTAMP, true);
	}
	return query;
    }

    /**
     * 평가시트 조회시 호출되는 함수
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
    public List<TemplateAssessSheet> find(TemplateAssessSheetDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<TemplateAssessSheet> assList = new ArrayList<TemplateAssessSheet>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    assList.add((TemplateAssessSheet) objArr[0]);
	}
	return assList;
    }

    /**
     * 평가시트 삭제시 호출되는 함수 평가시트에 링크된 평가항목이 있으면 조회하여 삭제후 평가시트 삭제함
     * 
     * @param TemplateAssessSheetDTO
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
    public TemplateAssessSheet delete(TemplateAssessSheetDTO paramObject) throws Exception {
	TemplateAssessSheet ass = (TemplateAssessSheet) CommonUtil.getObject(paramObject.getOid());

	QuerySpec query = getQueryTemplateGateCheckSheet(paramObject.getOid());
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
	return (TemplateAssessSheet) PersistenceHelper.manager.delete(ass);
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
    public PageControl findPaging(TemplateAssessSheetDTO paramObject) throws Exception {
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
     * 평가시트 객체정보를 DTO객체로 변환하여 리턴하는 함수
     * 
     * @param TemplateAssessSheet
     * @return
     * @throws Exception
     * @메소드명 : getAssessSheetCodeDTO
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public TemplateAssessSheetDTO getAssessSheetCodeDTO(TemplateAssessSheet tmpAssessSheet) throws Exception {
	TemplateAssessSheetDTO assSheetDto = new TemplateAssessSheetDTO(tmpAssessSheet);
	return assSheetDto;
    }

    /**
     * 평가시트(TemplateAssessSheet) 수정요청시 평가시트 내용을 업데이트 함수
     * 
     * @param paramObject
     *            수정된 평가시트 DTO(TemplateAssessSheetDTO) 정보
     * @return
     * @throws Exception
     * @메소드명 : modify
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public TemplateAssessSheet modify(TemplateAssessSheetDTO paramObject) throws Exception {
	TemplateAssessSheet modify = (TemplateAssessSheet) CommonUtil.getObject(paramObject.getOid());

	// doc Check Out
	// TemplateAssessSheet assSheetCopy = checkoutMottrolDoc(modify);

	modify = setAssessAttribute(modify, paramObject, "M");

	// doc save
	modify = (TemplateAssessSheet) PersistenceHelper.manager.modify(modify);

	// doc Check In
	// assSheetCopy = (TemplateAssessSheet) wt.vc.wip.WorkInProgressHelper.service.checkin(assSheetCopy, "");
	// assSheetCopy = (TemplateAssessSheet) PersistenceHelper.manager.refresh(assSheetCopy, true, true); // refresh

	// KETContentHelper.service.updateContent(modify, paramObject);

	return modify;
    }

    /**
     * 평가시트(TemplateAssessSheet) 생성요청시 평가시트 내용을 생성하는 함수
     * 
     * @param paramObject
     *            수정된 평가시트 DTO(TemplateAssessSheetDTO) 정보
     * @return
     * @throws Exception
     * @메소드명 : save
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public TemplateAssessSheet save(TemplateAssessSheetDTO paramObject) throws Exception {
	TemplateAssessSheet modify = TemplateAssessSheet.newTemplateAssessSheet();
	// long todaytime = System.currentTimeMillis();
	// SimpleDateFormat militime = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	// String assessSeq = "ASS" + militime.format(new Date(todaytime));

	modify = setAssessAttribute(modify, paramObject, "M");

	modify = (TemplateAssessSheet) PersistenceHelper.manager.save(modify);

	// KETContentHelper.service.updateContent(modify, paramObject);

	return modify;
    }

    /**
     * 체크아웃(현재 미사용)
     * 
     * @param TemplateAssessSheet
     * @return
     * @throws Exception
     * @메소드명 : checkoutDoc
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
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
		    try {
			checkOutLink = (wt.vc.wip.CheckoutLink) wt.vc.wip.WorkInProgressHelper.service.checkout(workable, folder, "");
		    } catch (wt.util.WTPropertyVetoException wtpve) {
			Kogger.error(getClass(), wtpve);
		    }
		    // get Original copy
		    orgCopy = checkOutLink.getOriginalCopy();

		    // get working copy
		    workingCopy = checkOutLink.getWorkingCopy();

		} else if (wt.vc.wip.WorkInProgressHelper.isCheckedOut(workable)) {
		    // get Original copy
		    orgCopy = wt.vc.wip.WorkInProgressHelper.service.originalCopyOf(workable);
		    // get working copy
		    workingCopy = wt.vc.wip.WorkInProgressHelper.service.workingCopyOf(workable);
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

    /**
     * 평가시트 수정작업시 DTO(TemplateAssessSheetDTO) 에서 값이 넙어올때 CODE 데이터들은 NumberCode객체 OID정보를 넘기게 되는데 코드성 데이터를 NumberCode객체 형태로
     * 평가시트(TemplateAssessSheet) 객체에 세팅하는 작업
     * 
     * @param modify
     *            평가시트(TemplateAssessSheet)
     * @param paramObject
     *            수정된 평가시트 DTO(TemplateAssessSheetDTO) 정보
     * @param mode
     *            M(수정) : 수정화면에서는 DTO의 코드정보(개발구분,개발유형, 제품군, 사업부)값으로 NumberCode code 정보가 넘어옴, S(생성) : 생성화면에서는 DTO의 코드정보값으로 NumberCode OID정보가
     *            넘어옴 현재 수정화면, 생성화면 모두 NumberCode OID정보가 넘어오는 상황임
     * @return
     * @throws Exception
     * @메소드명 : setAssessAttribute
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public TemplateAssessSheet setAssessAttribute(TemplateAssessSheet modify, TemplateAssessSheetDTO paramObject, String mode)
	    throws Exception {
	// 사업부 정보 처리....
	NumberCode divisionNumber = null;
	ReferenceFactory rf = new ReferenceFactory();

	String divisionOid = (paramObject.getDivision() == null) ? "" : (String) paramObject.getDivision();
	if ("M".equals(mode)) {
	    if (divisionOid.indexOf(":") <= 0)
		divisionOid = "e3ps.common.code.NumberCode:" + divisionOid;
	    divisionNumber = (NumberCode) CommonUtil.getObject(divisionOid);
	} else {
	    divisionNumber = NumberCodeHelper.manager.getNumberCode("DIVISIONNUMBER", divisionOid);
	}
	modify.setDivision(divisionNumber);

	// 개발유형=프로젝트 유형
	NumberCode devTypeNumber = null;

	String devTypeOid = (paramObject.getDevType() == null) ? "" : (String) paramObject.getDevType();
	if ("M".equals(mode)) {
	    if (devTypeOid.indexOf(":") <= 0)
		devTypeOid = "e3ps.common.code.NumberCode:" + devTypeOid;
	    devTypeNumber = (NumberCode) CommonUtil.getObject(devTypeOid);
	} else {
	    devTypeNumber = NumberCodeHelper.manager.getNumberCode("PROJECTTYPE", devTypeOid);
	}
	modify.setDevType(devTypeNumber);

	// 평가시트 등록시 개발유형이 제품(2)인 경우만 개발구분 정보가 입력됨
	if (devTypeNumber != null && "2".equals(devTypeNumber.getCode())) {

	    // 개발구분
	    NumberCode devDivNumber = null;
	    String devDivOid = (paramObject.getDevDiv() == null) ? "" : (String) paramObject.getDevDiv();
	    if ("M".equals(mode)) {
		if (devDivOid.indexOf(":") <= 0)
		    devDivOid = "e3ps.common.code.NumberCode:" + devDivOid;
		devDivNumber = (NumberCode) CommonUtil.getObject(devDivOid);
	    } else {
		devDivNumber = NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", devDivOid);
	    }
	    modify.setDevDiv(devDivNumber);
	}

	// 제품구분
	// NumberCode prodCateNumber = null;
	// String prodCateOid = (paramObject.getProdCategory() == null) ? "" : (String) paramObject.getProdCategory();
	// if ("M".equals(mode)) {
	// if (prodCateOid.indexOf(":") <= 0)
	// prodCateOid = "e3ps.common.code.NumberCode:" + prodCateOid;
	// prodCateNumber = (NumberCode) CommonUtil.getObject(prodCateOid);
	// } else {
	// prodCateNumber = NumberCodeHelper.manager.getNumberCode("PRODCATEGORYCODE", prodCateOid);
	// }
	// modify.setProdCategory(prodCateNumber);

	modify.setPartOid(paramObject.getPartOid());

	modify.setSheetName(paramObject.getSheetName());
	modify.setSheetDesc(paramObject.getSheetDesc());
	// modify.setState(paramObject.getStatus());
	// modify.setSheetDesc(paramObject.getSheetRev());
	String isActive = paramObject.getActive();
	if (StringUtil.isEmpty(isActive))
	    isActive = "N";

	modify.setActive(isActive);

	// 작성자 추가
	WTPrincipal principal = SessionHelper.manager.getPrincipal();
	WTPrincipalReference wtref = WTPrincipalReference.newWTPrincipalReference(principal);
	modify.setOwner(wtref);

	return modify;
    }

    /**
     * 평가시트 OID 하위에 있는 평가항목을 조회하는 QuerySpec 리턴
     * 
     * @param pjtOid
     *            평가시트 OID
     * @return
     * @throws Exception
     * @메소드명 : getQueryTemplateGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryTemplateGateCheckSheet(String pjtOid) throws Exception {
	TemplateAssessSheet sheet = (TemplateAssessSheet) CommonUtil.getObject(pjtOid);

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

	if (!StringUtil.isTrimToEmpty(pjtOid)) {
	    long pjtOidLong = 0;
	    pjtOid = pjtOid.substring(pjtOid.indexOf(":") + 1, pjtOid.length());
	    pjtOidLong = Long.parseLong(pjtOid);
	    sc = new SearchCondition(TmplAssShtTmplGateChkLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, pjtOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	return query;
    }

    /**
     * 평가시트 조회
     * 
     * @param findTemplateAssess
     * @return
     * @throws Exception
     * @메소드명 : find
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public TemplateAssessSheet findTemplateAssess(TemplateAssessSheetDTO paramObject) throws Exception {
	TemplateAssessSheet tmpAss = null;
	QuerySpec query = getQuery(paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<TemplateAssessSheet> assList = new ArrayList<TemplateAssessSheet>();
	if (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    tmpAss = (TemplateAssessSheet) objArr[0];
	}
	return tmpAss;
    }

}
