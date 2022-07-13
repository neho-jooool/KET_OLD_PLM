package ext.ket.part.base.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.State;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.part.QuantityUnit;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartMasterIdentity;
import wt.part.WTPartTypeInfo;
import wt.part.WTPartTypeInterface;
import wt.pdmlink.PDMLinkProduct;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.vc.Mastered;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import wt.vc.views.ViewHelper;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.beans.ECMProperties;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.EDMProperties;
import e3ps.edm.util.VersionHelper;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectMaster;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.material.MoldMaterial;
import e3ps.qms.QmsPartInterfaceUtil;
import e3ps.sap.ErpPartInterFace;
import ext.ket.common.util.ObjectUtil;
import ext.ket.part.base.service.internal.DieHalbLinkUtil;
import ext.ket.part.base.service.internal.PartAssociationUtil;
import ext.ket.part.base.service.internal.PartBaseBuilder;
import ext.ket.part.base.service.internal.PartBaseDao;
import ext.ket.part.base.service.internal.PartChangeLogHandler;
import ext.ket.part.base.service.internal.PartDeleteUtil;
import ext.ket.part.base.service.internal.PartDieInfoHandler;
import ext.ket.part.base.service.internal.PartEcoValidator;
import ext.ket.part.base.service.internal.PartExtDao;
import ext.ket.part.base.service.internal.PartMatchingHandler;
import ext.ket.part.base.service.internal.RelatedEpmHandler;
import ext.ket.part.base.service.internal.VieawbleGenerator;
import ext.ket.part.base.service.internal.associate.AssociateClaz;
import ext.ket.part.base.service.internal.associate.AssociateProject;
import ext.ket.part.base.service.internal.associate.PartClassificationRelation;
import ext.ket.part.base.service.internal.associate.PartEpmRelation;
import ext.ket.part.base.service.internal.associate.PartProjectRelation;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.classify.service.internal.PartClazBuilder;
import ext.ket.part.code.PartGenDTO;
import ext.ket.part.code.PartNoGenerable;
import ext.ket.part.code.PartNumberingFactory;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.entity.KETPartClassAttrLink;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassNamingLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartMassPlan;
import ext.ket.part.entity.KETPartNaming;
import ext.ket.part.entity.KETPartProjectLink;
import ext.ket.part.entity.dto.CommonCodeDTO;
import ext.ket.part.entity.dto.KETMassPartDTO;
import ext.ket.part.entity.dto.PartAttributeDTO;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.entity.dto.PartCatalogueDTO;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;
import ext.ket.part.entity.dto.PartClassificationDTO;
import ext.ket.part.entity.dto.PartDieProjectHelpDTO;
import ext.ket.part.entity.dto.PartListDTO;
import ext.ket.part.entity.dto.PartMatchingDTO;
import ext.ket.part.entity.dto.PartSearchMainDTO;
import ext.ket.part.entity.dto.PartValidationDTO;
import ext.ket.part.replacePart.util.ReplacePartUtil;
import ext.ket.part.sap.ErpPartHandler;
import ext.ket.part.spec.service.internal.PartSpecBuilder;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartSpecSetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryBStrategy;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.QueryStrategy;
import ext.ket.shared.query.SimpleQuerySpec;
import ext.ket.shared.util.SessionUtil;

public class StandardPartBaseService extends StandardManager implements PartBaseService {

    private static final long serialVersionUID = 1L;
    private PartSpecBuilder partSpecBuilder = new PartSpecBuilder();
    private PartClazBuilder partClazBuilder = new PartClazBuilder();
    private PartBaseBuilder partBaseBuilder = new PartBaseBuilder();
    private PartBaseDao partBaseDao = new PartBaseDao();
    private PartExtDao partExtDao = new PartExtDao();
    private ErpPartHandler erpPartHandler = new ErpPartHandler();
    private PartDieInfoHandler partDieInfoHandler = new PartDieInfoHandler();
    private DieHalbLinkUtil dieHalbLinkUtil = new DieHalbLinkUtil();
    // private PartNamingBuilder partNamingBuilder = new PartNamingBuilder();

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    public static StandardPartBaseService newStandardPartBaseService() throws WTException {
	StandardPartBaseService instance = new StandardPartBaseService();
	instance.initialize();
	return instance;
    }

    // 부품 검색
    @Override
    public PageControl searchMainPartList(PartSearchMainDTO partSearchMainDTO, String pageTotalSize) throws Exception {
	return partBaseDao.searchMainPartList(partSearchMainDTO, pageTotalSize);
    }

    // 부품 검색시 화면에 분류체계의 결합된 속성 조회
    @Override
    public List<PartClassAttrLinkDTO> getPartPropsMix(String clazOidArray, Locale locale) throws Exception {

	List<PartClassAttrLinkDTO> list = new ArrayList<PartClassAttrLinkDTO>();
	List<KETPartClassification> clazList = new ArrayList<KETPartClassification>();

	if (StringUtils.isEmpty(clazOidArray))
	    return list;
	else {
	    String[] wtClazOidSplitArray = clazOidArray.split(",");
	    for (int i = 0; i < wtClazOidSplitArray.length; i++) {

		String item = wtClazOidSplitArray[i];

		if (!StringUtils.isEmpty(item)) {

		    KETPartClassification claz = (KETPartClassification) CommonUtil.getObject(item);
		    clazList.add(claz);
		}
	    }
	}

	List<String> oidList = partBaseDao.searchUniqueClazAttrOidList(clazList);
	for (String attrOid : oidList) {

	    KETPartAttribute attr = (KETPartAttribute) CommonUtil.getObject(attrOid);

	    if (PartSpecEnum.SpProjectNo.getAttrCode().equals(attr.getAttrCode()) || // 프로젝트 No는 화면 붙박이라 가져올 필요 없음.
		    PartSpecEnum.SpIsYazaki.getAttrCode().equals(attr.getAttrCode()) // YAZAKI 여부도 화면 붙박이라 가져올 필요 없음.
	    ) {
		continue;
	    }

	    PartClassAttrLinkDTO dto = new PartClassAttrLinkDTO();

	    // dto.setEssential(link.isEssential());
	    // dto.setChecked(link.isChecked());
	    // dto.setIndexRow(link.getIndexRow());
	    // dto.setIndexCol(link.getIndexCol());

	    PartAttributeDTO attrDTO = dto.getPartAttributeDTO();
	    partSpecBuilder.buildPers2Dto(attr, attrDTO);

	    PartClassificationDTO clazDTO = dto.getPartClassificationDTO();
	    // partClazBuilder.buildPers2Dto(clazDTO, claz);

	    dto.setPartAttrOid(CommonUtil.getOIDString(attr));
	    // dto.setPartClazOid(CommonUtil.getOIDString(claz));
	    // dto.setPartClazAttrLinkOid(CommonUtil.getOIDString(link));
	    dto.setPartAttributeDTO(attrDTO);
	    list.add(dto);
	}

	return list;
    }

    // 부품 등록
    @Override
    public WTPart createWTPart(PartBaseDTO dto) throws Exception {

	boolean erpPartRegist = false;
	boolean halbRegist = false;
	boolean copyRegist = false;
	boolean scrabAutoGen = false;
	String scrabAutoPartNo = null;
	boolean halbAutoGen = false;
	String halbAutoPartNo = null;
	// regist Part
	WTPart wtPart = registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist);

	// 원자재 수지(R20)의 경우 스크랩 자동 생성 체크박스 체크되면 - 추가로 스크랩 자동 채번
	if ("Y".equals(dto.getPartScrabAutoGen()) || "R20".equals(dto.getPartNumber())) {
	    scrabAutoGen = true;
	    dto.setSpPartType("S"); // 스크랩 유형으로 변경
	    scrabAutoPartNo = "S" + wtPart.getNumber().substring(1);
	    registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist);
	} else {
	    this.addGeneratePart(dto, wtPart.getNumber());
	    /*
	     * String spPartType = dto.getSpPartType(); PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(spPartType); KETPartClassification
	     * classific = (KETPartClassification) CommonUtil.getObject(dto.getPartClazOid()); halbAutoGen = true; String beforeNo =
	     * wtPart.getNumber(); if (beforeNo.indexOf("-") == -1) { halbAutoPartNo = beforeNo + "A"; } else { halbAutoPartNo =
	     * beforeNo.replaceAll("-", "A-"); } if (beforeNo.startsWith("H7") && ptype == PartTypeEnum.HALB) { // 반제품 터미널 if
	     * ("C".equals(classific.getDivisionFlag())) { // 자동차 사업부 if ("2".equals(dto.getSpPlating())) { // Tin // need to generator
	     * registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist); } }
	     * else if ("E".equals(classific.getDivisionFlag())) { // 전자 사업부 if (!"1".equals(dto.getSpPlating()) &&
	     * !"3".equals(dto.getSpPlating())) { // 무도금, Pre-Tin // need to generator registWTPart(dto, halbRegist, copyRegist,
	     * scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist); } } } else if
	     * (wtPart.getNumber().startsWith("K2") && ptype == PartTypeEnum.HALB) { // 전자 K2 반제품 if (!"1".equals(dto.getSpPlating()) &&
	     * !"3".equals(dto.getSpPlating())) { // 무도금, Pre-Tin // need to generator registWTPart(dto, halbRegist, copyRegist,
	     * scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist); } }
	     */
	}

	// 첨부 파일
	KETContentHelper.service.updateContent(wtPart, dto);

	return wtPart;
    }

    // 부품 반제품 등록
    @Override
    public WTPart createWTPartByHalb(PartBaseDTO dto) throws Exception {
	boolean erpPartRegist = false;
	boolean halbRegist = true;
	boolean copyRegist = false;
	boolean scrabAutoGen = false;
	String scrabAutoPartNo = null;
	boolean halbAutoGen = false;
	String halbAutoPartNo = null;
	WTPart wtpart = registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist);
	this.addGeneratePart(dto, wtpart.getNumber());
	return wtpart;
    }

    // 부품 복사 등록
    @Override
    public WTPart createWTPartByCopy(PartBaseDTO dto) throws Exception {
	boolean erpPartRegist = false;
	boolean halbRegist = false;
	boolean copyRegist = true;
	boolean scrabAutoGen = false;
	String scrabAutoPartNo = null;
	boolean halbAutoGen = false;
	String halbAutoPartNo = null;
	return registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist);
    }

    // 부품[ERP] 등록
    @Override
    public WTPart createWTPartByErp(PartBaseDTO dto) throws Exception {
	boolean erpPartRegist = true;
	boolean halbRegist = false;
	boolean copyRegist = false;
	boolean scrabAutoGen = false;
	String scrabAutoPartNo = null;
	boolean halbAutoGen = false;
	String halbAutoPartNo = null;
	return registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist);
    }

    private void qmsPartMasterInterface(WTPart wtPart) throws Exception { // TBL_ERP_GOODS

	WTProperties properties = WTProperties.getLocalProperties();
	String hostName = properties.getProperty("java.rmi.server.hostname");
	if (hostName.equals("plmapwas")) {
	    String version = wtPart.getVersionInfo().getIdentifier().getValue();
	    if ("0".equals(version)) {//
		QmsPartInterfaceUtil.manager.qmsPartSave(wtPart.getNumber());
	    }
	}

    }

    private WTPart registWTPart(PartBaseDTO dto, boolean halbRegist, boolean copyRegist, boolean scrabAutoGen, String scrabAutoPartNo,
	    boolean halbAutoGen, String halbAutoPartNo, boolean erpPartRegist) throws Exception {

	WTPart wtPart = null;

	try {

	    KETPartClassification classific = null;

	    if (scrabAutoGen) {
		String clazCode = "Q20";
		classific = PartClazHelper.service.getPartClassificationByClazCode(clazCode);
	    } else {
		classific = (KETPartClassification) CommonUtil.getObject(dto.getPartClazOid());
	    }

	    wtPart = partBaseBuilder.buildDto2Pers(null, dto, false);

	    // 품명
	    if (StringUtils.isEmpty(dto.getPartName())) {
		throw new Exception(" 부품명을 입력해 주십시요!!");
	    }

	    if (dto.getPartName().matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
		throw new Exception("부품명에 한글을 포함 할 수 없습니다!");
	    }

	    if (scrabAutoGen) {
		wtPart.setName("SCRAP-" + dto.getPartName());
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartNameHis, "SCRAP-" + dto.getPartName()); // 품명 History
	    } else {
		wtPart.setName(dto.getPartName());
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartNameHis, dto.getPartName()); // 품명 History
	    }

	    // 품번
	    if (classific == null || StringUtils.isEmpty(dto.getPartNumber()))
		throw new Exception(" 부품번호가 비어 있습니다.!!");

	    if (scrabAutoGen) {

		wtPart.setNumber(scrabAutoPartNo);

	    } else if (halbRegist) {

		wtPart.setNumber(dto.getPartNumber());

	    } else if (erpPartRegist) {

		wtPart.setNumber(dto.getPartNumber());

	    } else if (halbAutoGen) {

		wtPart.setNumber(halbAutoPartNo);

	    } else {

		String numberingCode = classific.getNumberingCode();

		if (copyRegist) {
		    if (numberingCode != null && numberingCode.equals(dto.getPartNumber().trim()) || "Y".equals(dto.getPartNumberAutoGen())) {

			PartNoGenerable partNoGen = PartNumberingFactory.getInstance().getGenerator(dto.getSpPartType(), numberingCode);
			PartGenDTO genDto = new PartGenDTO();
			genDto.setNumberingCode(numberingCode);
			genDto.setDivisionFlag(classific.getDivisionFlag());
			genDto.setClassPartType(classific.getClassPartType());
			genDto.setClassCode(classific.getClassCode());
			genDto.setNumberingCharics(classific.getNumberingCharics());
			genDto.setNumberingMinNo(classific.getNumberingMinNo());
			String partNo = partNoGen.generatePartNo(genDto, dto);
			wtPart.setNumber(partNo);

		    } else {

			wtPart.setNumber(dto.getPartNumber());

		    }
		} else {
		    PartNoGenerable partNoGen = PartNumberingFactory.getInstance().getGenerator(dto.getSpPartType(), numberingCode);
		    PartGenDTO genDto = new PartGenDTO();
		    genDto.setNumberingCode(numberingCode);
		    genDto.setDivisionFlag(classific.getDivisionFlag());
		    genDto.setClassPartType(classific.getClassPartType());
		    genDto.setClassCode(classific.getClassCode());
		    genDto.setNumberingCharics(classific.getNumberingCharics());
		    genDto.setNumberingMinNo(classific.getNumberingMinNo());
		    String partNo = partNoGen.generatePartNo(genDto, dto);
		    wtPart.setNumber(partNo);
		}
	    }

	    // 스크랩 자동생성되는 원자재에 대해서 - 스크랩 코드 처리
	    if (!scrabAutoGen && ("Y".equals(dto.getPartScrabAutoGen()) || "R20".equals(dto.getPartNumber()))) {
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpScrabCode, "S" + wtPart.getNumber().substring(1));
	    }

	    if (scrabAutoGen) {
		// 스크랩코드 속성 제거
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpScrabCode, "");
	    }

	    // 리비전(KET 요청사항) - windchill의 리비전과 동일하게 올라 감

	    // 개발단계 : [개발, 양산]
	    // 개발단계 별 별도의 리비전 체계 관리
	    // [개발] : D00
	    // [양산] : 0, 1, 2, 3…
	    // 단, 금형은 제외
	    String partType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
	    if (PartUtil.isProductType(partType)) {

		String checkPartNumber = dto.getPartNumber().toUpperCase();

		int checkLen = 10;

		if ("H".equals(partType)) {
		    checkLen = 11;
		}

		if (checkPartNumber.length() > checkLen) {// 바코드 속라벨 출력시 10자리 이상이면 표현을 못하기 때문에 2018.06.18 차주원 차장님 요청 .. 반제품은 11자리까지 인정
			                                  // 2021.10.14 적용
		    throw new Exception("부품번호는 " + checkLen + "자리를 초과 할 수 없습니다.");
		}

		if (checkPartNumber.contains("Q") || checkPartNumber.contains("Z")) {
		    throw new Exception("부품번호에 Q 또는 Z가 포함되면 안됩니다."); // 2021.09.16 차주원 팀장님 지시
		}

		if ("PC004".equals(dto.getSpPartDevLevel())) {
		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartRevision, "0");
		} else {
		    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartRevision, "D00");
		}
	    } else {
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartRevision, "0");
	    }

	    if (wtPart.getNumber().startsWith("RC")) {// 무상사급자재 품번은 PLM에서 채번불가 2020.01.07 차주원 차장님 요청
		throw new Exception("RC로 시작하는 품번을 채번할 수 없습니다.(무상사급자재)");
	    }

	    // 삭제 구분 : Y,N
	    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "N");

	    // BOM 신규 구분 : OLD,NEW
	    if (erpPartRegist) {
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpBOMFlag, "OLD");
	    } else {
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpBOMFlag, "NEW");
	    }

	    // 단위
	    wtPart.setDefaultUnit(QuantityUnit.toQuantityUnit(dto.getPartDefaultUnit()));

	    // Container
	    PDMLinkProduct e3psProduct = WCUtil.getPDMLinkProduct();
	    WTContainerRef wtContainerRef = WTContainerRef.newWTContainerRef(e3psProduct);
	    wtPart.setContainer(e3psProduct);

	    // View Setting (default "Design")
	    ViewHelper.assignToView(wtPart, ViewHelper.service.getView("Design"));

	    // Folder Setting
	    FolderHelper.assignLocation((FolderEntry) wtPart, KETPartHelper.service.getPartFolder(messageRegistry.getString("part")));

	    // Life Cycle Setting (Default)
	    LifeCycleTemplate partLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC", wtContainerRef);
	    wtPart = (WTPart) LifeCycleHelper.setLifeCycle(wtPart, partLifeCycle);

	    // Part Create
	    wtPart = (WTPart) PersistenceHelper.manager.save(wtPart);

	    // Life Cycle State INWORK 할당
	    if (erpPartRegist || wtPart.getNumber().startsWith("CP")/* 개발 검토 도번일 경우 Released 해달라 */) {
		LifeCycleHelper.service.setLifeCycleState(wtPart, State.toState("APPROVED"), true);
	    } else {
		LifeCycleHelper.service.setLifeCycleState(wtPart, State.INWORK, true);
	    }

	    // project Relation
	    ProjectMaster project = null;
	    if (StringUtils.isNotEmpty(dto.getProjectNo())) {
		project = PartUtil.getProjectMasterByProjectNo(dto.getProjectNo());
	    }

	    // TKLEE 연관 문서, 도면 : 복사생성, 반제품생성, 신규생성
	    PartAssociationUtil associateUtil = new PartAssociationUtil();

	    KETProjectDocument projectDocument = null;
	    EPMDocument epmDocument = null;
	    if (copyRegist) {
		// TKLEE 복사 생성일 경우 처리 필요
		WTPart beforePart = (WTPart) CommonUtil.getObject(dto.getPartCopyOid());
		String partCopyEpm = dto.getPartCopyEpm();
		String partCopyDoc = dto.getPartCopyDoc();
		String partCopyBom = dto.getPartCopyBom();
		associateUtil.associateWTPartCopyCreate(beforePart, wtPart, classific, project, partCopyEpm, partCopyDoc, partCopyBom,
		        dto.getEcoNo());
	    } else {
		associateUtil.associateWTPartCreate(wtPart, classific, projectDocument, epmDocument, project);
	    }

	    // 대표금형은 DIE HALB LINK 생성
	    if ("H".equals(partType) && StringUtils.isNotEmpty(dto.getSpRepresentM())) {
		WTPartMaster diePartMast = getMaster(dto.getSpRepresentM().trim());
		dieHalbLinkUtil.createDieHalbLink(diePartMast, (WTPartMaster) wtPart.getMaster());
	    }

	    // Die Set 등록 시에 Halb Part를 연결해 준다.
	    if (StringUtils.isNotEmpty(dto.getPartProdOid())) {

		String halbOid = dto.getPartProdOid();

		if (halbOid.indexOf("wt.part.WTPartMaster") == -1) {
		    halbOid = "wt.part.WTPartMaster:" + halbOid;
		}

		WTPartMaster halbPart = (WTPartMaster) CommonUtil.getObject(halbOid);

		dieHalbLinkUtil.createDieHalbLink((WTPartMaster) wtPart.getMaster(), halbPart);
		dieHalbLinkUtil.updateHalbRepresentMold((WTPartMaster) wtPart.getMaster(), halbPart);
	    }

	    qmsPartMasterInterface(wtPart);

	    if (erpPartRegist) {
		if (StringUtils.isNotEmpty(dto.getPartProdMigNumber())) {
		    Kogger.debug(getClass(), "PartProdMigNumber() :" + dto.getPartProdMigNumber());
		    dieHalbLinkUtil.createMigDieHalbLink((WTPartMaster) wtPart.getMaster(), dto.getPartProdMigNumber());
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return wtPart;
    }

    // 부품 No 체크
    @Override
    public boolean existWTPartNumber(String _partNumber, String _partType) throws Exception {

	if (StringUtils.isEmpty(_partNumber)) {
	    return false;
	} else {

	    String partNumber = _partNumber;
	    if (partNumber.indexOf(",") != -1) {
		boolean retBool = false;
		String[] keywordAry = partNumber.split(",");
		for (String keyword : keywordAry) {
		    keyword = keyword.trim();
		    if (keyword.length() > 0) {
			retBool = existCheckWTPartNumber(keyword, _partType);
			if (retBool == false) {
			    return false;
			}
		    }
		}

		return true;

	    } else {
		return existCheckWTPartNumber(partNumber.trim(), _partType);
	    }
	}

    }

    private boolean existCheckWTPartNumber(String _partNumber, String _partType) throws Exception {

	final String partNumber = _partNumber;
	final String partType = _partType;

	if (StringUtils.isEmpty(partType)) {

	    WTPartMaster master = query.queryForFirstByOneClass(WTPartMaster.class, new QueryStrategy() {
		@Override
		public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		    query.appendWhere(new SearchCondition(class1, WTPartMaster.NUMBER, SearchCondition.EQUAL, partNumber),
			    new int[] { index });
		}
	    });
	    return master != null;

	} else {

	    WTPart part = query.queryForFirstByOneClass(WTPart.class, new QueryStrategy() {
		@Override
		public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		    query.appendWhere(new SearchCondition(class1, WTPart.NUMBER, SearchCondition.EQUAL, partNumber), new int[] { index });
		    if (query.getConditionCount() > 0)
			query.appendAnd();
		    query.appendWhere(new SearchCondition(class1, PartSpecEnum.SpPartType.getQuerySpecCode(), SearchCondition.EQUAL,
			    partType), new int[] { index });
		    if (query.getConditionCount() > 0)
			query.appendAnd();
		    query.appendWhere(new SearchCondition(class1, WTPart.LATEST_ITERATION, SearchCondition.IS_TRUE), new int[] { index });
		}
	    });
	    return part != null;
	}

    }

    // 부품 No ERP 체크
    public boolean existErpPartNumber(String partNumber) throws Exception {

	if (StringUtils.isEmpty(partNumber))
	    return false;

	return erpPartHandler.existErpPart(partNumber);
    }

    // 부품 연관 DieHalbLink
    public boolean existPartDieHalbLink(String partNumber) throws Exception {

	if (StringUtils.isEmpty(partNumber))
	    return false;

	return dieHalbLinkUtil.existPartDieHalbLink(partNumber);
    }

    // 부품 등록시 분류체계별 속성 조회 - 수정, 조회화면에서도 그대로 사용
    @Override
    public List<PartClassAttrLinkDTO> getPartProps(String clazOid, Locale locale) throws Exception {

	KETPartClassification claz = (KETPartClassification) CommonUtil.getObject(clazOid);

	List<PartClassAttrLinkDTO> list = new ArrayList<PartClassAttrLinkDTO>();

	List<KETPartClassAttrLink> linkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class, KETPartClassification.class, claz,
	        new QueryBStrategy() {

		    @Override
		    public void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

		        SearchUtil.setOrderBy(query, classLink, indexLink, KETPartClassAttrLink.INDEX_ROW, false);
		        SearchUtil.setOrderBy(query, classLink, indexLink, KETPartClassAttrLink.INDEX_COL, false);
		    }
	        });

	PartClassAttrLinkDTO attrDevSpecDTO = null;
	PartClassAttrLinkDTO attrHImgIFDTO = null;

	PartClassAttrLinkDTO attrHIFDTO = null;
	PartClassAttrLinkDTO attrH2DIFDTO = null;
	PartClassAttrLinkDTO attrH3DIFDTO = null;

	for (KETPartClassAttrLink link : linkList) {

	    PartClassAttrLinkDTO dto = new PartClassAttrLinkDTO();
	    dto.setEssential(link.isEssential());
	    dto.setChecked(link.isChecked());

	    dto.setIndexRow(link.getIndexRow());
	    dto.setIndexCol(link.getIndexCol());
	    // 16.06.15 추가
	    dto.setHpYn(link.isHpYn());
	    KETPartAttribute attr = link.getAttr();

	    PartAttributeDTO attrDTO = dto.getPartAttributeDTO();
	    partSpecBuilder.buildPers2Dto(attr, attrDTO);
	    // 프로젝트, 중량정보는 화면에 붙박이라서 뺀 후
	    if (PartSpecEnum.SpProjectNo.getAttrCode().equals(attrDTO.getAttrCode())
		    || PartSpecEnum.SpNetWeight.getAttrCode().equals(attrDTO.getAttrCode())
		    || PartSpecEnum.HomepageIF.getAttrCode().equals(attrDTO.getAttrCode())
		    || PartSpecEnum.Homepage2DIF.getAttrCode().equals(attrDTO.getAttrCode())
		    || PartSpecEnum.Hompage3DIF.getAttrCode().equals(attrDTO.getAttrCode())

	    ) { // 별도로 처리하여 필수를 체크한다.

		continue;
	    }

	    PartClassificationDTO clazDTO = dto.getPartClassificationDTO();
	    partClazBuilder.buildPers2Dto(clazDTO, claz);

	    // naming 자동 채번용은 별도로 처리함
	    // KETPartClassNamingLink namingLink = query.queryForFirstLinkByRoleB(KETPartClassNamingLink.class, KETPartClassification.class,
	    // claz);
	    // if(namingLink != null){
	    //
	    // KETPartNaming partNaming = namingLink.getNaming();
	    // clazDTO.setPartNamingOid(CommonUtil.getOIDString(partNaming));
	    // }

	    dto.setPartAttrOid(CommonUtil.getOIDString(attr));
	    dto.setPartClazOid(CommonUtil.getOIDString(claz));
	    dto.setPartClazAttrLinkOid(CommonUtil.getOIDString(link));
	    dto.setPartAttributeDTO(attrDTO);
	    if (link.getAttr().getAttrCode().toString().equals("spDevSpec")) {
		attrDevSpecDTO = dto;
	    } else if (link.getAttr().getAttrCode().toString().equals("hompageImgIF")) {
		attrHImgIFDTO = dto;
	    } else if (link.getAttr().getAttrCode().toString().equals("homepageIF")) {
		attrHIFDTO = dto;
	    } else if (link.getAttr().getAttrCode().toString().equals("homepage2DIF")) {
		attrH2DIFDTO = dto;
	    } else if (link.getAttr().getAttrCode().toString().equals("hompage3DIF")) {
		attrH3DIFDTO = dto;
	    } else {
		list.add(dto);
	    }
	}
	if (attrDevSpecDTO != null) {
	    list.add(attrDevSpecDTO);
	}
	if (attrHImgIFDTO != null) {
	    list.add(attrHImgIFDTO);
	}

	if (attrHIFDTO != null) {
	    list.add(attrHIFDTO);
	}
	if (attrH2DIFDTO != null) {
	    list.add(attrH2DIFDTO);
	}
	if (attrH3DIFDTO != null) {
	    list.add(attrH3DIFDTO);
	}

	return list;
    }

    // 부품 등록시 채번 코드, 제품/반제품 코드 확인
    @Override
    public KETPartClassification getPartClassification(String clazOid) throws Exception {
	if (clazOid == null)
	    return null;

	KETPartClassification claz = (KETPartClassification) CommonUtil.getObject(clazOid);
	return claz;
    }

    // 부품 등록시 품명 자동완성 관련 naming 가져옴
    @Override
    public String getPartNamingOid(String clazOid) throws Exception {

	if (clazOid == null)
	    return null;

	KETPartClassification claz = (KETPartClassification) CommonUtil.getObject(clazOid);
	KETPartClassNamingLink oldNamingLink = query.queryForFirstLinkByRoleB(KETPartClassNamingLink.class, KETPartClassification.class,
	        claz);
	if (oldNamingLink != null) {
	    KETPartNaming naming = oldNamingLink.getNaming();
	    return CommonUtil.getOIDString(naming);
	} else {
	    return null;
	}
    }

    // // 부품 수정
    // @Override
    // public WTPart updateWTPart(PartBaseDTO dto) throws Exception {
    //
    // WTPart wtPart = null;
    // WTPart sWtPart = null;
    // String sPart = "";
    // String cPart = "";
    // WTConnection wtConn = null;
    // Connection conn = null;
    //
    // try {
    //
    // Kogger.debug(getClass(), "before partOid :" + dto.getPartOid());
    // WTPart beforePart = (WTPart) CommonUtil.getObject(dto.getPartOid());
    //
    // if (!VersionControlHelper.isLatestIteration(beforePart)) {
    // beforePart = (WTPart) VersionControlHelper.getLatestIteration(beforePart, false);
    // }
    // // 반제품, 제품 중 둘중 하나라도 이미지 등록 하면 같은 제품 번호(제품,반제품)에 동시 저장 되도록..
    // // 2016.05.26
    // cPart = beforePart.getNumber();
    // if ("".equals(StringUtil.checkNull(cPart))) {
    // if (cPart.length() > cPart.replaceFirst("H", "").length()) {
    // sPart = cPart.replaceFirst("H", "");
    // } else {
    // sPart = "H" + cPart;
    // }
    // }
    //
    // if (!"".equals(sPart)) {
    // wtConn = EcmUtil.getWTConnection();
    // conn = wtConn.getConnection();
    // ProdEcoBeans ecoBean = new ProdEcoBeans();
    //
    // sWtPart = ecoBean.getLastestPart(sPart, conn);
    // }
    //
    // // workable 생성
    // wtPart = PartUtil.getWorkingCopy(beforePart);
    // Kogger.debug(getClass(), "after partOid :" + wtPart.toString());
    // // Kogger.debug(getClass(), "sWtPart partOid :" + sWtPart.toString());
    //
    // // dto의 값을 입력한다.
    // wtPart = partBaseBuilder.buildDto2Pers(wtPart, dto, false);
    //
    // // 부품명 이력
    // if (StringUtils.isEmpty(dto.getPartName()))
    // throw new Exception("## 부품명을 입력해 주십시요!!");
    //
    // PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartNameHis, dto.getPartName());
    //
    // // 첨부 파일
    // if (dto.getPrimaryFileOid() == null) {
    // if (dto.getPrimaryFile() != null && dto.getPrimaryFile().getSize() != 0) {
    // // 첨부파일 수정
    // KETContentHelper.service.updateContent(wtPart, dto);
    // // 동일 제번의 반제품이나 제품을 이미지 수정
    // if (sWtPart != null) {
    // KETContentHelper.service.updateContent(sWtPart, dto);
    // }
    // } else {
    // // 첨부파일 삭제
    // ContentDTO primaryDto = KETContentHelper.manager.getPrimaryContent(wtPart);
    // ContentDTO sPrimaryDto = KETContentHelper.manager.getPrimaryContent(sWtPart);
    // if (primaryDto != null) {
    // KETContentHelper.service.delete(wtPart, (ContentItem) CommonUtil.getObject(primaryDto.getContentoid()));
    // }
    // if (sPrimaryDto != null) {
    // KETContentHelper.service.delete(sWtPart, (ContentItem) CommonUtil.getObject(sPrimaryDto.getContentoid()));
    // }
    // }
    // }
    //
    // // save - stored & modify
    // wtPart = (WTPart) PersistenceHelper.manager.save(wtPart);
    //
    // // Check-in
    // wtPart = (WTPart) WorkInProgressHelper.service.checkin(wtPart, "");
    //
    // // refresh
    // wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
    //
    // // Life Cycle Setting (Default)
    // LifeCycleTemplate partLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC", wtPart.getContainerReference());
    // // wtPart = (WTPart) LifeCycleHelper.setLifeCycle(wtPart, partLifeCycle);
    // wtPart = (WTPart) LifeCycleHelper.service.reassign(wtPart, partLifeCycle.getLifeCycleTemplateReference());
    //
    // // Life Cycle State INWORK 할당
    // if (wtPart.getLifeCycleState() != State.INWORK) {
    // LifeCycleHelper.service.setLifeCycleState(wtPart, State.INWORK);
    // // LifeCycleServerHelper.service.setState((LifeCycleManaged)wtPart, State.INWORK);
    // }
    //
    // // 부품명
    // WTPartMaster partMaster = (WTPartMaster) (wtPart.getMaster());
    // if (!wtPart.getName().equals(dto.getPartName())) {
    // WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();
    // identity.setName(dto.getPartName());
    // partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
    // }
    //
    // // 단위
    // if (!(partMaster.getDefaultUnit().toString()).equalsIgnoreCase(dto.getPartDefaultUnit())) {
    //
    // partMaster.setDefaultUnit(QuantityUnit.toQuantityUnit(dto.getPartDefaultUnit()));
    // partMaster = (WTPartMaster) PersistenceHelper.manager.modify(partMaster);
    // }
    //
    // // 연관 문서, 도면
    // PartAssociationUtil associateUtil = new PartAssociationUtil();
    // // project Relation
    // ProjectMaster project = null;
    // if (StringUtils.isNotEmpty(dto.getProjectNo())) {
    // project = PartUtil.getProjectMasterByProjectNo(dto.getProjectNo());
    // }
    // // 분류체계
    // KETPartClassification classific = (KETPartClassification) CommonUtil.getObject(dto.getPartClazOid());
    // KETProjectDocument projectDocument = null;
    // EPMDocument epmDocument = null;
    // associateUtil.associateWTPartModify(wtPart, classific, projectDocument, epmDocument, project);
    //
    // // 부품연관된 프로젝트 정보 업데이트
    // partDieInfoHandler.updatePartDieInfo(wtPart);
    //
    // } catch (Exception e) {
    // Kogger.error(getClass(), e);
    // throw e;
    // }
    // return wtPart;
    // }

    @Override
    public WTPart updateWTPart(PartBaseDTO dto) throws Exception {
	// 이터레이션이 필요하다면 위의 updateWTPart 를 사용하면 된다. 이터레이션이 필요없기 때문에 아래 메서드를 사용 2019.06.29 by 황정태
	return this.updateWTPartAfterApp_(dto, false);
    }

    public WTPart updateWTPartAfterApp_(PartBaseDTO dto, boolean erpTransfer) throws Exception {

	WTPart wtPart = null;

	try {

	    Kogger.debug(getClass(), "modify partOid :" + dto.getPartOid());
	    wtPart = (WTPart) CommonUtil.getObject(dto.getPartOid());

	    // dto의 값을 입력한다.
	    wtPart = partBaseBuilder.buildDto2Pers(wtPart, dto, true);

	    // log 생성 필요시 필요
	    String valueOld = null;
	    String valueNew = null;
	    WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	    String userId = user.getName();
	    String userName = user.getFullName();
	    Department userDept = DepartmentHelper.manager.getDepartment(user);
	    String deptCode = (userDept == null) ? "" : userDept.getName();
	    String deptName = (userDept == null) ? "" : userDept.getCode();

	    // 부품명 이력
	    if (StringUtils.isEmpty(dto.getPartName())) {
		throw new Exception("## 부품명을 입력해 주십시요!!");
	    }

	    if (dto.getPartName().matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
		throw new Exception("부품명에 한글을 포함 할 수 없습니다!");
	    }

	    valueOld = StringUtil.checkNull(wtPart.getName());
	    valueNew = StringUtil.checkNull(dto.getPartName());

	    if (!valueOld.equals(valueNew)) {
		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpPartNameHis, dto.getPartName());
		PartChangeLogHandler.changeLog(wtPart, "partName", "부품명", valueOld, valueNew, userId, userName, deptCode, deptName);
	    }

	    // save - update
	    PersistenceServerHelper.manager.update(wtPart);

	    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);

	    SessionHelper.manager.setAdministrator();
	    // 부품명
	    WTPartMaster partMaster = (WTPartMaster) (wtPart.getMaster());
	    if (!wtPart.getName().equals(dto.getPartName())) {
		WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();
		identity.setName(dto.getPartName());
		partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
	    }
	    SessionHelper.manager.setPrincipal(userId);

	    // 단위
	    if (!(partMaster.getDefaultUnit().toString()).equalsIgnoreCase(dto.getPartDefaultUnit())) {

		PartChangeLogHandler.changeLog(wtPart, "partDefaultUnit", "부품기본단위", valueOld, valueNew, userId, userName, deptCode,
		        deptName);

		partMaster.setDefaultUnit(QuantityUnit.toQuantityUnit(dto.getPartDefaultUnit()));
		partMaster = (WTPartMaster) PersistenceHelper.manager.modify(partMaster);
	    }

	    // 연관 문서, 도면
	    PartAssociationUtil associateUtil = new PartAssociationUtil();
	    // project Relation
	    ProjectMaster project = null;
	    if (StringUtils.isNotEmpty(dto.getProjectNo())) {
		project = PartUtil.getProjectMasterByProjectNo(dto.getProjectNo());
	    }

	    valueOld = StringUtil.checkNull(PartUtil.getPartProjectNo(wtPart));
	    valueNew = StringUtil.checkNull(project == null ? null : project.getPjtNo());

	    if (!valueOld.equals(valueNew)) {
		PartChangeLogHandler.changeLog(wtPart, "projectNo", "프로젝트명", valueOld, valueNew, userId, userName, deptCode, deptName);
	    }

	    // 분류체계
	    KETPartClassification classific = (KETPartClassification) CommonUtil.getObject(dto.getPartClazOid());
	    KETProjectDocument projectDocument = null;
	    EPMDocument epmDocument = null;
	    associateUtil.associateWTPartModify(wtPart, classific, projectDocument, epmDocument, project);

	    if (classific != null) {
		AssociateClaz ac = new PartClassificationRelation();
		ac.deAssociate(wtPart);
		ac.associateCreate(wtPart, classific);
	    }

	    partDieInfoHandler.updateProudctInfo(wtPart);

	    // 부품연관된 프로젝트 정보 업데이트
	    partDieInfoHandler.updatePartDieInfo(wtPart);

	    // 커넥터대체품 관리
	    Map<String, Object> reqMap = new HashMap<String, Object>();
	    reqMap.put("partNo", wtPart.getNumber());
	    ReplacePartUtil.manager.syncKETPartInfo(reqMap);

	    // qms 자재마스터 인터페이스
	    qmsPartMasterInterface(wtPart);

	    if (erpTransfer) {
		// ERP I/F
		List<WTPart> partList = new ArrayList<WTPart>();
		partList.add(wtPart);

		// long branchId = VersionControlHelper.getBranchIdentifier(wtPart);
		// ECO No 없어도 저장되도록 수정
		String ecoNo = ""; // partBaseDao.searchEONo(branchId); ECO-1410-222
		Map<String, String> workerNameMap = new HashMap<String, String>();
		ErpPartHandler erpPartHandler = new ErpPartHandler();

		if (!erpPartHandler.existErpPart(partMaster.getNumber())) {// 금형부품의 경우 승인완료됐는데 자재마스터가 미생성된 경우가 발생하여 가상의 eco를 따서 erp에 자재마스터를
			                                                   // 수동으로 만들어주기위한 임시조치임
		    ProdEcoBeans prodEcoBeans = new ProdEcoBeans();
		    ecoNo = prodEcoBeans.getNewProdEcoId();
		}
		// if (erpPartHandler.existErpPart(partMaster.getNumber())) {

		ErpPartInterFace erpPartInterFace = new ErpPartInterFace();
		Kogger.biz("부품 Admin ERP I/F 시작");
		Map resultMap = erpPartInterFace.sendPartInfoToErp(partList, ecoNo, workerNameMap, false);
		Kogger.biz("부품 Admin ERP I/F 종료");

		boolean success = (Boolean) resultMap.get(ErpPartInterFace.SUCCESS);
		List<String> errorLogList = (List<String>) resultMap.get(ErpPartInterFace.PLM_ERROR_LIST);
		String erpMsg = (String) resultMap.get(ErpPartInterFace.ERP_ERROR_MSG);

		if (!success) {

		    Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		    Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		    Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$ ERP IF ERROR $$$$$$$$$$$$$$$$$");
		    Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		    Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

		    Kogger.debug(getClass(), erpMsg);

		    for (String errorLog : errorLogList) {
			Kogger.debug(getClass(), errorLog);
		    }

		    throw new Exception("ERP Interface Error !!");
		}
		// }
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	return wtPart;

    }

    // 부품 결재완료 후 수정 - Iteration 증가 없이 속성을 수정후 ERP 전송
    @Override
    public WTPart updateWTPartAfterApp(PartBaseDTO dto) throws Exception {

	return this.updateWTPartAfterApp_(dto, true);
    }

    // 부품 삭제
    @Override
    public void deletePart(PartBaseDTO dto, Locale locale) throws Exception {

	final String partOid = dto.getPartOid();
	WTPart wtpart = (WTPart) CommonUtil.getObject(partOid);

	PartDeleteUtil.deleteWTPartMast(wtpart);
    }

    // 부품 조회 - 수정시, 복사생성 시도 조회함
    @Override
    public PartBaseDTO viewDetailPart(PartBaseDTO dto, Locale locale) throws Exception {

	final String partOid = dto.getPartOid();
	WTPart wtpart = (WTPart) CommonUtil.getObject(partOid);

	KETPartClassification claz = null;
	KETPartClassLink classLink = null;
	KETPartProjectLink projectLink = null;
	List<PartClassAttrLinkDTO> attrLinkList = new ArrayList<PartClassAttrLinkDTO>();

	String tempClazOid = StringUtil.checkNull(dto.getTempClazOid());
	if (wtpart != null) {
	    // 분류체계

	    if (StringUtils.isNotEmpty(tempClazOid)) {// 관리자가 분류체계 변경시
		claz = (KETPartClassification) CommonUtil.getObject(tempClazOid);
		if (claz != null) {
		    attrLinkList = getPartProps(CommonUtil.getOIDString(claz), locale);
		}

	    }
	    if (claz == null) {
		classLink = query.queryForFirstLinkByRoleB(KETPartClassLink.class, WTPartMaster.class, ((WTPartMaster) wtpart.getMaster()));
		if (classLink != null) {
		    claz = classLink.getClassific();
		    if (claz != null) {
			attrLinkList = getPartProps(CommonUtil.getOIDString(claz), locale);
		    }
		}
	    }

	    // Project Info
	    QueryResult qr = PartUtil.getPartProjectLink(wtpart, null);
	    while (qr.hasMoreElements()) {
		Object[] objs = (Object[]) qr.nextElement();
		projectLink = (KETPartProjectLink) objs[0];
	    }
	}

	PartBaseDTO retDto = partBaseBuilder.buildPers2Dto(wtpart, classLink, projectLink, attrLinkList, partBaseDao, locale);

	if (classLink == null && claz != null) {// 관리자가 분류체계 변경시
	    retDto.setPartClazOid(CommonUtil.getOIDString(claz));
	    retDto.setPartClazNameKr(claz.getClassKrName());
	    retDto.setNumberingCode(claz.getNumberingCode());
	    retDto.setPartNamingOid(PartBaseHelper.service.getPartNamingOid(CommonUtil.getOIDString(claz)));
	    retDto.setClassPartType(claz.getClassPartType());
	    retDto.setErpProdCode(claz.getErpProdCode());
	    retDto.setClassCode(claz.getClassCode());
	    retDto.setNumberingCharics(claz.getNumberingCharics());
	}

	retDto.setIsHawaRegist(dto.getIsHawaRegist());// 상품등록 플래그
	return retDto;
    }

    public KETPartClassification RK38getClaz(final String clazParam) throws Exception {
	KETPartClassification claz = null;
	claz = query.queryForFirstByOneClass(KETPartClassification.class, new QueryStrategy() {
	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		query.appendWhere(new SearchCondition(class1, KETPartClassification.CLASS_CODE, SearchCondition.EQUAL, clazParam),
		        new int[] { index });
	    }
	});
	return claz;
    }

    // 부품 조회 - 채번코드가 RK30인 경우에 한해 반제품 등록시 사용한다(전자사업부 박주미 대리 요청)
    @Override
    public PartBaseDTO RK30viewDetailPart(PartBaseDTO dto, Locale locale) throws Exception {

	final String partOid = dto.getPartOid();
	WTPart wtpart = (WTPart) CommonUtil.getObject(partOid);

	KETPartClassification claz = null;
	KETPartClassLink classLink = null;
	KETPartProjectLink projectLink = null;
	List<PartClassAttrLinkDTO> attrLinkList = new ArrayList<PartClassAttrLinkDTO>();

	final String clazL3030 = "L3030";
	final String clazM6030 = "M6030";
	final String clazO6030 = "O6030";
	if (wtpart != null) {
	    // 분류체계
	    classLink = query.queryForFirstLinkByRoleB(KETPartClassLink.class, WTPartMaster.class, ((WTPartMaster) wtpart.getMaster()));
	    if (classLink != null) {
		claz = classLink.getClassific();
		if (claz != null) {
		    if ("L4010".equals(claz.getClassCode())) {// Mobile_전자용부품 (원자재)
			claz = RK38getClaz(clazL3030); // Mobile_일반가공 (반제품)
		    } else if ("M7010".equals(claz.getClassCode())) {// Display_전자용부품 (원자재)
			claz = RK38getClaz(clazM6030); // Display_일반가공 (반제품)
		    } else if ("O7010".equals(claz.getClassCode())) {// Fakra_전자용 부품 (원자재)
			claz = RK38getClaz(clazO6030); // Fakra_일반가공(반제품)
		    }
		    attrLinkList = getPartProps(CommonUtil.getOIDString(claz), locale);
		}
	    }
	    classLink = null; // classLink 초기화함(반제품 buildPers2Dto에사 분류체계속성세팅을 생략하기 위함.밑에서 retDto를 다시 세팅한다)
	    // Project Info
	    QueryResult qr = PartUtil.getPartProjectLink(wtpart, null);
	    while (qr.hasMoreElements()) {
		Object[] objs = (Object[]) qr.nextElement();
		projectLink = (KETPartProjectLink) objs[0];
	    }
	}

	PartBaseDTO retDto = partBaseBuilder.buildPers2Dto(wtpart, classLink, projectLink, attrLinkList, partBaseDao, locale);

	retDto.setPartClazOid(CommonUtil.getOIDString(claz));
	retDto.setPartClazNameKr(claz.getClassKrName());
	retDto.setNumberingCode(claz.getNumberingCode());
	retDto.setPartNamingOid(PartBaseHelper.service.getPartNamingOid(CommonUtil.getOIDString(claz)));
	retDto.setClassPartType(claz.getClassPartType());
	retDto.setErpProdCode(claz.getErpProdCode());

	return retDto;
    }

    // 부품 Part List 조회
    @Override
    public PartListDTO viewPartList(PartListDTO dto, Locale locale) throws Exception {

	PartListDTO partList = partExtDao.searchPartList(dto.getPartOid());

	return partList;
    }

    @Override
    public String reviseWTPartNGetVersion(WTPart sourcePart, String developLevel) throws Exception {

	WTPart afterPart = revisePart(sourcePart, developLevel);
	if (afterPart == null)
	    return "";
	else {
	    return afterPart.getVersionIdentifier().getSeries().getValue();
	}
    }

    // 부품 개정
    private WTPart revisePart(WTPart beforePart, String developLevel) throws Exception {

	WTPart afterPartLatest = null;
	Versioned versioned = null;

	try {

	    if (beforePart == null)
		return null;

	    ECMProperties ecmProperties = ECMProperties.getInstance();
	    ReferenceFactory rf = new ReferenceFactory();

	    versioned = (Versioned) rf.getReference(beforePart).getObject();

	    if (!VersionHelper.isLatestRevision(versioned)) {
		// throw new WTException("최신버전이 아닙니다.");
		KETMessageService messageService = KETMessageService.getMessageService();
		WTPart latestWTPart = (WTPart) VersionHelper.getLatestRevision(versioned);
		String latestWTPartNumber = latestWTPart.getNumber();
		String latestWTPartRevision = latestWTPart.getVersionInfo().getIdentifier().getValue();

		// <entry key="04014">상위 버전[{0}]이 이미 존재합니다.</entry>
		throw new WTException(messageService.getString("e3ps.message.ket_message", "04014", latestWTPartNumber + " ("
		        + latestWTPartRevision + ")"));
		// throw new WTException("최신버전이 아닙니다.");
	    }

	    String location = null;
	    String lifecycle = null;

	    if (versioned instanceof WTPart) {
		location = ecmProperties.getString("part.folder"); // /Default/부품
		lifecycle = ecmProperties.getString("ecm.lifecyle.part"); // "KET_PART_LC"
	    }

	    PartUtil.doRevise(versioned, null, lifecycle, location, "INWORK", null, null, developLevel);

	    QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf((Mastered) beforePart.getMaster());

	    if (qr.hasMoreElements()) {
		afterPartLatest = (WTPart) qr.nextElement();
	    }

	    PartAssociationUtil associateUtil = new PartAssociationUtil();
	    associateUtil.reviseAssociateWTPart(beforePart, afterPartLatest);

	    return afterPartLatest;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    // 도면 개정 - 일반제품일 경우 개발단계 변경 by eco
    @Override
    public String reviseEpmDocNGetVersion(EPMDocument sourceEpm, String developLevel) throws Exception {

	EPMDocument afterEpm = reviseEpmDoc(sourceEpm, developLevel);
	if (afterEpm == null)
	    return "";
	else {
	    return afterEpm.getVersionIdentifier().getSeries().getValue();
	}
    }

    public EPMDocument reviseEpmDoc(EPMDocument beforeEpm, String developLevel) throws Exception {

	EPMDocument afterEpmLatest = null;
	Versioned versioned = null;

	try {

	    if (beforeEpm == null)
		return null;

	    ReferenceFactory rf = new ReferenceFactory();

	    versioned = (Versioned) rf.getReference(beforeEpm).getObject();

	    if (!VersionHelper.isLatestRevision(versioned)) {
		// throw new WTException("최신버전이 아닙니다.");
		KETMessageService messageService = KETMessageService.getMessageService();
		EPMDocument latestEpm = (EPMDocument) VersionHelper.getLatestRevision(versioned);
		String latestEpmNumber = latestEpm.getNumber();
		String latestEpmRevision = latestEpm.getVersionInfo().getIdentifier().getValue();

		// <entry key="04014">상위 버전[{0}]이 이미 존재합니다.</entry>
		throw new WTException(messageService.getString("e3ps.message.ket_message", "04014", latestEpmNumber + " ("
		        + latestEpmRevision + ")"));
		// throw new WTException("최신버전이 아닙니다.");
	    }

	    if (!VersionHelper.isLatestRevision(versioned)) {
		throw new WTException("최신버전이 아닙니다.");
	    }

	    EDMProperties edmProperties = EDMProperties.getInstance();
	    if (versioned instanceof LifeCycleManaged) {
		if (!edmProperties.isReleasedState(((LifeCycleManaged) versioned).getState().getState().toString())) {
		    throw new WTException("미승인상태의 데이터가 있습니다.");
		}
	    }

	    String location = null;
	    String lifecycle = null;

	    if (versioned instanceof EPMDocument) {
		location = FolderHelper.service.getFolder((FolderEntry) versioned).getFolderPath(); //
		Kogger.debug(getClass(), "=======> 일반 EPM Revise : location : " + location);
		lifecycle = edmProperties.getEPMDefaultLC(); //
		Kogger.debug(getClass(), "=======> 일반 EPM Revise : lifecycle : " + lifecycle);
	    }
	    WTPrincipal session = null;
	    WTGroup group = null;
	    // ChangeSession set Administrator
	    // KET_LIB 인 경우 Revise Administrators 권한에 한하여 일시적으로 admin권한을 줘서 개정이 가능하도록 한다. (정책적 방침을 가지고 고민해서 진행하는것이 아니고. 걍 떔빵용..) 2017.01.04
	    // by 황정태
	    if (StringUtils.contains(location, "LIBRARY")) {
		session = SessionHelper.manager.getPrincipal();
		SessionHelper.manager.setAdministrator();
		group = KETObjectUtil.getGroup("Revise Administrators");
		group.addMember(session);
		SessionHelper.manager.setPrincipal(session.getName());

		PartUtil.doReviseEpm(versioned, null, lifecycle, location, "INWORK", null, null, developLevel);

		// Restore Original Session
		SessionHelper.manager.setAdministrator();
		group.removeMember(session);
		SessionHelper.manager.setPrincipal(session.getName());
	    } else {
		PartUtil.doReviseEpm(versioned, null, lifecycle, location, "INWORK", null, null, developLevel);
	    }

	    QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf((Mastered) beforeEpm.getMaster());

	    if (qr.hasMoreElements()) {
		afterEpmLatest = (EPMDocument) qr.nextElement();
	    }

	    return afterEpmLatest;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    // 부품 개정 취소
    @Override
    public void cancelReviseWTPart(WTPart sourcePart) throws Exception {

	PartDeleteUtil.deleteWTPartRev(sourcePart);
    }

    @Override
    public String notUsePart(String partOid) throws Exception {

	WTPart wtPart = (WTPart) CommonUtil.getObject(partOid);

	// -. 이미 사용하지 않는 부품인가?
	String deleteFlag = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpIsDeleted);
	if ("Y".equals(deleteFlag)) {
	    return "O";
	}

	// -. ERP에 존재하는 부품인가
	boolean isExisErp = false;
	try {
	    isExisErp = erpPartHandler.existErpPart(wtPart.getNumber());
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "E";
	}
	if (!isExisErp) {
	    return "W";
	}

	// -. ERP 삭제 시도
	boolean notUsedSuccess = false;
	try {
	    notUsedSuccess = erpPartHandler.notUsePart(wtPart.getNumber());
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "E";
	}

	if (notUsedSuccess) {

	    // PLM 삭제
	    try {

		PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpIsDeleted, "Y");
		PersistenceServerHelper.manager.update(wtPart);
		wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);

		// -. 부품 삭제 로그 남김
		// log 생성 필요시 필요
		String valueOld = "N";
		String valueNew = "Y";
		WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
		String userId = user.getName();
		String userName = user.getFullName();
		Department userDept = DepartmentHelper.manager.getDepartment(user);
		String deptCode = (userDept == null) ? "" : userDept.getName();
		String deptName = (userDept == null) ? "" : userDept.getCode();
		PartChangeLogHandler.changeLog(wtPart, "spIsDeleted", "부품삭제구분", valueOld, valueNew, userId, userName, deptCode, deptName);

	    } catch (Exception e) {

		Kogger.error(getClass(), e);
		return "E";
	    }

	    return "Y";

	} else {

	    return "N";
	}

    }

    // ERP 부품 가져오기
    @Override
    public PartBaseDTO migPartFromErpForm(String partNumber, Locale locale) throws Exception {

	// WTPart wtpart = getLatestPart(partNumber);
	//
	// if(wtpart == null){
	// return new PartBaseDTO();
	// }
	//
	// PartBaseDTO dto = new PartBaseDTO();
	// String partOid = CommonUtil.getOIDString(wtpart);
	// dto.setPartOid(partOid);
	// PartBaseDTO partBaseDTO = viewDetailPart(dto, locale) ;

	PartBaseDTO partBaseDTO = erpPartHandler.migrateErpPart(partNumber);

	return partBaseDTO;
    }

    // 최신 부품 가져오기
    @Override
    public WTPart getLatestPart(String partNumber) throws Exception {

	WTPartMaster wtPartMast = getMaster(partNumber);

	return getLatestPart(wtPartMast);
    }

    // 최신 부품 가져오기
    public WTPart getLatestPart(WTPartMaster wtPartMast) throws Exception {

	if (wtPartMast == null)
	    return null;

	WTPart latest = null;
	QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf(wtPartMast);

	while (qr.hasMoreElements()) {

	    WTPart obj = ((WTPart) qr.nextElement());

	    if (latest == null || obj.getVersionIdentifier().getSeries().greaterThan(latest.getVersionIdentifier().getSeries())) {
		latest = obj;
	    }
	}

	if (latest != null)
	    return (WTPart) VersionControlHelper.getLatestIteration(latest, false);
	else
	    return latest;
    }

    // 부품의 Master 가져오기
    @Override
    public WTPartMaster getMaster(String partNo) throws WTException {

	WTPartMaster master = null;
	QuerySpec query = new QuerySpec(WTPartMaster.class);
	query.appendWhere(new SearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, SearchCondition.EQUAL, partNo.trim()),
	        new int[] { 0 });
	QueryResult qr = PersistenceHelper.manager.find(query);
	if (qr.hasMoreElements()) {
	    master = (WTPartMaster) qr.nextElement();
	}

	return master;
    }

    // 최신 EPM 가져오기
    @Override
    public EPMDocument getLatestEPM(String epmNumber) throws Exception {

	EPMDocumentMaster epmMast = getEpmMaster(epmNumber);

	return getLatestEPM(epmMast);
    }

    // 최신 EPM 가져오기
    public EPMDocument getLatestEPM(EPMDocumentMaster epmMast) throws Exception {

	if (epmMast == null)
	    return null;

	EPMDocument latest = null;
	QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf(epmMast);

	while (qr.hasMoreElements()) {

	    EPMDocument obj = ((EPMDocument) qr.nextElement());

	    if (latest == null || obj.getVersionIdentifier().getSeries().greaterThan(latest.getVersionIdentifier().getSeries())) {
		latest = obj;
	    }
	}

	if (latest != null)
	    return (EPMDocument) VersionControlHelper.getLatestIteration(latest, false);
	else
	    return latest;
    }

    // EPM 마스터 가져오기
    @Override
    public EPMDocumentMaster getEpmMaster(String epmNumber) throws WTException {

	EPMDocumentMaster master = null;
	QuerySpec query = new QuerySpec(EPMDocumentMaster.class);
	query.appendWhere(new SearchCondition(EPMDocumentMaster.class, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, epmNumber.trim()),
	        new int[] { 0 });
	QueryResult qr = PersistenceHelper.manager.find(query);
	if (qr.hasMoreElements()) {
	    master = (EPMDocumentMaster) qr.nextElement();
	}

	return master;
    }

    // 부품의 생산처명 정보 가져오기
    @Override
    public String getManufacPlaceName(String manufacPlaceCode) throws Exception {

	if (StringUtils.isEmpty(manufacPlaceCode)) {
	    return "";
	}

	// 0000102064 / DEP30
	String codeOfNumberCodeValue = "";
	if (manufacPlaceCode.length() == 10) {
	    codeOfNumberCodeValue = partBaseDao.getPartnerName(manufacPlaceCode);
	} else {
	    codeOfNumberCodeValue = CodeHelper.manager.getCodeValue("PRODUCTIONDEPT", manufacPlaceCode);
	}

	return StringUtil.checkNull(codeOfNumberCodeValue);
    }

    // 연계도면 가져오기
    @Override
    public List<EPMDocument> getRelatedEPMDocument(List<WTPart> partList) throws Exception {
	RelatedEpmHandler relatedEpmHandler = new RelatedEpmHandler();
	return relatedEpmHandler.getReferenceEPMDocsByWTPartList(partList);
    }

    // ECO WTPart의 연관된 EPM 가져오기
    @Override
    public Map<String, List<EPMDocument>> getRelatedEPMDocByEcoPart(WTPart wtPart) throws Exception {
	RelatedEpmHandler relatedEpmHandler = new RelatedEpmHandler();
	return relatedEpmHandler.getRelatedEPMDocByEcoPart(wtPart, true);
    }

    public Map<String, List<EPMDocument>> getRelatedEPMDocByEcoPart(WTPart wtPart, boolean isOnlyApproved) throws Exception {
	RelatedEpmHandler relatedEpmHandler = new RelatedEpmHandler();
	return relatedEpmHandler.getRelatedEPMDocByEcoPart(wtPart, isOnlyApproved);
    }

    // 프로젝트 개발단계 정보 가져오기
    @Override
    public String getProjectDevLevel(String projectNumber) throws Exception {

	String devLevel = "";

	try {

	    E3PSProject e3psProject = ProjectHelper.getProject(projectNumber);
	    return getProjectDevLevel(e3psProject);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return devLevel;
    }

    // 프로젝트 개발단계 정보 가져오기
    @Override
    public String getProjectDevLevel(E3PSProject e3psProject) throws Exception {

	String devLevel = "";

	try {

	    NumberCode devLevelNC = e3psProject.getProcess();
	    if (devLevelNC != null) {
		// PC001:Proto, PC002:Pilot, PC003:T-CAR
		String devLevelCode = devLevelNC.getCode();
		if ("PC001".equals(devLevelCode) || "PC002".equals(devLevelCode) || "PC003".equals(devLevelCode)) {
		    devLevel = "PC003";
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return devLevel;
    }

    // Die - Halb Link 생성
    @Override
    public boolean addDieHalbLink(String dieOid, String halbOid) throws Exception {

	if (StringUtils.isEmpty(dieOid) || StringUtils.isEmpty(halbOid))
	    return false;

	DieHalbLinkUtil util = new DieHalbLinkUtil();
	WTPart diePart = (WTPart) CommonUtil.getObject(dieOid);
	WTPart halbPart = (WTPart) CommonUtil.getObject(halbOid);
	return util.createDieHalbLink((WTPartMaster) diePart.getMaster(), (WTPartMaster) halbPart.getMaster());

    }

    // Die - Halb Linkt 삭제
    @Override
    public boolean deleteDieHalbLink(String dieOid, String halbOid) throws Exception {

	if (StringUtils.isEmpty(dieOid) || StringUtils.isEmpty(halbOid))
	    return false;

	DieHalbLinkUtil util = new DieHalbLinkUtil();
	WTPart diePart = (WTPart) CommonUtil.getObject(dieOid);
	WTPart halbPart = (WTPart) CommonUtil.getObject(halbOid);
	return util.deleteDieHalbLink((WTPartMaster) diePart.getMaster(), (WTPartMaster) halbPart.getMaster());
    }

    // Material Info 가져오기 : 재질(수지), 재질(비철)
    @Override
    public String getMaterialInfo(String materialOid) throws Exception {

	if (StringUtils.isEmpty(materialOid))
	    return "";

	try {

	    MoldMaterial moldMaterial = (MoldMaterial) CommonUtil.getObject("e3ps.project.material.MoldMaterial:" + materialOid);
	    return moldMaterial.getGrade();

	} catch (Exception e) {
	    // 잘못된 데이터를 ERP로 넘기기 위해서는 필요함
	    // Exception 풀지 마세요.
	    return "";
	}
    }

    // Material Info Update : 원재료 정보 업데이트 by BOM의 일반제품일 경우
    @Override
    public void updateMaterialInfo(WTPart halbParentPart, WTPart rohSonPart) throws Exception {

	if (halbParentPart == null || rohSonPart == null) {
	    Kogger.debug(getClass(), "# 원재료의 부품 중 하나가 'null' 입니다.");
	    return;
	}

	// -. 분류체계 정보가 있는지 체크한다.
	KETPartClassification claz = PartClazHelper.service.getPartClassification(halbParentPart);
	if (claz == null) {
	    Kogger.debug(getClass(), "# 상위 Halb의 Classficaiton이 'null' 입니다.");
	    return;
	}

	List<KETPartClassAttrLink> linkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class, KETPartClassification.class, claz);
	if (linkList == null || linkList.size() == 0) {
	    Kogger.debug(getClass(), "# 상위 Halb의 Classficaiton에 연관된 전체 속성이 없습니다.");
	    return;
	}

	boolean hasMaterial = false;
	boolean isSuji = false;
	boolean isNotFe = false;
	for (KETPartClassAttrLink link : linkList) {
	    KETPartAttribute attr = link.getAttr();
	    if (PartSpecEnum.SpMaterialInfo.getAttrCode().equals(attr.getAttrCode())) {
		Kogger.debug(getClass(), "# 수지 입니다.");
		hasMaterial = true;
		isSuji = true;
	    } else if (PartSpecEnum.SpMaterNotFe.getAttrCode().equals(attr.getAttrCode())) {
		Kogger.debug(getClass(), "# 비철 입니다.");
		hasMaterial = true;
		isNotFe = true;
	    }
	}

	if (!hasMaterial) {
	    Kogger.debug(getClass(), "# 상위 Halb의 Classficaiton에 연관된 재질 속성이 없어 업데이트 할 수 없습니다.");
	    return;
	}

	if (isSuji) {

	    String spMaterMaker = PartSpecGetter.getPartSpec(rohSonPart, PartSpecEnum.SpMaterMaker);
	    String spMaterType = PartSpecGetter.getPartSpec(rohSonPart, PartSpecEnum.SpMaterType);
	    String spMaterialInfo = PartSpecGetter.getPartSpec(rohSonPart, PartSpecEnum.SpMaterialInfo);

	    PartSpecSetter.setPartSpec(halbParentPart, PartSpecEnum.SpMaterMaker, spMaterMaker);
	    PartSpecSetter.setPartSpec(halbParentPart, PartSpecEnum.SpMaterType, spMaterType);
	    PartSpecSetter.setPartSpec(halbParentPart, PartSpecEnum.SpMaterialInfo, spMaterialInfo);

	    PersistenceServerHelper.manager.update(halbParentPart);

	    halbParentPart = (WTPart) PersistenceHelper.manager.refresh(halbParentPart);

	} else if (isNotFe) {

	    String spMaterMaker = PartSpecGetter.getPartSpec(rohSonPart, PartSpecEnum.SpMaterMaker);
	    String spMaterType = PartSpecGetter.getPartSpec(rohSonPart, PartSpecEnum.SpMaterType);
	    String spMaterNotFe = PartSpecGetter.getPartSpec(rohSonPart, PartSpecEnum.SpMaterNotFe);

	    PartSpecSetter.setPartSpec(halbParentPart, PartSpecEnum.SpMaterMaker, spMaterMaker);
	    PartSpecSetter.setPartSpec(halbParentPart, PartSpecEnum.SpMaterType, spMaterType);
	    PartSpecSetter.setPartSpec(halbParentPart, PartSpecEnum.SpMaterNotFe, spMaterNotFe);

	    PersistenceServerHelper.manager.update(halbParentPart);

	    halbParentPart = (WTPart) PersistenceHelper.manager.refresh(halbParentPart);
	}

	return;
    }

    // Material Type List

    public boolean isSuji(String clazOid) throws Exception {
	boolean isSuji = false;

	if ("MAT1000".equals(clazOid)) {
	    isSuji = true;
	} else if ("MAT2000".equals(clazOid)) {
	    isSuji = false;
	} else {
	    isSuji = PartClazHelper.service.isSuji(clazOid);
	}
	return isSuji;
    }

    @Override
    public Map<String, Object> getMaterialTypeList(String clazOid, String spMaterMaker) throws Exception {

	return getMaterialTypeList(this.isSuji(clazOid), spMaterMaker);
    }

    // Material Type List
    @Override
    public Map<String, Object> getMaterialTypeList(boolean isSuji, String spMaterMaker) throws Exception {

	String materialGubun = isSuji ? "수지" : "비철";

	List<CommonCodeDTO> codeList = partBaseDao.getMaterialMaker(materialGubun, spMaterMaker);
	Map map = new HashMap();
	map.put("options", codeList);
	return map;
    }

    // Material Info List : 재질 (수지), 재질 (비철)
    @Override
    public Map<String, Object> getMaterialInfoList(String clazOid, String spMaterMaker, String spMaterType) throws Exception {
	return getMaterialInfoList(this.isSuji(clazOid), spMaterMaker, spMaterType);
    }

    // Material Info List : 재질 (수지), 재질 (비철)
    @Override
    public Map<String, Object> getMaterialInfoList(boolean isSuji, String spMaterMaker, String spMaterType) throws Exception {

	String materialGubun = (isSuji) ? "수지" : "비철";

	List<CommonCodeDTO> codeList = partBaseDao.getMaterial(materialGubun, spMaterMaker, spMaterType);
	Map map = new HashMap();
	map.put("options", codeList);
	return map;
    }

    // Die List of Halb part related
    @Override
    public Map<String, Object> getDieListOfHalb(String halbOid) throws Exception {

	List<CommonCodeDTO> codeList = dieHalbLinkUtil.getDieListOfHalb(halbOid);
	Map map = new HashMap();
	map.put("options", codeList);
	return map;
    }

    // Die Detail of Halb part related
    @Override
    public Map<String, Object> getDieDetailOfHalb(String dieOid, Locale locale) throws Exception {

	List<CommonCodeDTO> codeList = dieHalbLinkUtil.getDieDetailOfHalb(dieOid, locale);
	Map map = new HashMap();
	map.put("props", codeList);
	return map;
    }

    // 프로젝트와 원재료 등 정보 공유 및 Sync : 부품 수정 시점 등
    @Override
    public PartDieProjectHelpDTO getPartDieInfoForSync(String partNo, String dieNo) throws Exception {

	return partDieInfoHandler.getPartDieInfoForSync(partNo, dieNo);
    }

    // BOM - Mold Upload 시점에 부품과 연결된 도면 링크 처리 : 금형부품의 프로젝트를 Die의 프로젝트로 넣어줌
    public boolean makeMoldPartToEpmDocRelation(WTPart diePart, WTPart moldPart, String optionModel) throws Exception {

	try {

	    if (diePart == null || moldPart == null) {
		return false;
	    }

	    // 프로젝트 정보 업데이트
	    KETPartProjectLink currentProjectLink = null;
	    QueryResult qr = PartUtil.getPartProjectLink(diePart, null);
	    while (qr.hasMoreElements()) {
		Object[] objs = (Object[]) qr.nextElement();
		currentProjectLink = (KETPartProjectLink) objs[0];
	    }

	    if (currentProjectLink != null) { // 프로젝트 정보가 존재할 경우에 처리

		ProjectMaster projMast = currentProjectLink.getProject();
		// 프로젝트 정보
		AssociateProject ap = new PartProjectRelation();
		ap.associateCreate(moldPart, projMast);
	    }

	    PartEpmRelation partEpmRelation = new PartEpmRelation();

	    if ("NA".equalsIgnoreCase(optionModel) || "N/A".equalsIgnoreCase(optionModel)) {

		return true;

	    } else if (StringUtils.isEmpty(optionModel)) {

		// 주도면 찾아 넣기
		EPMDocument epmDocument1 = getLatestEPM(moldPart.getNumber() + "_PRT");
		EPMDocument epmDocument2 = getLatestEPM(moldPart.getNumber() + "_DWG");
		EPMDocument epmDocument3 = getLatestEPM(moldPart.getNumber() + "_PLS");

		int required = 1;
		String referType = null;
		if (epmDocument1 != null) {
		    referType = "RELATED_MODEL"; // "RELATED_DRAWING"; // "RELATED_MODEL"; - VIEW UI에서 부품은 보이고 수정화면은 않보임
		    partEpmRelation.associateCreate(moldPart, epmDocument1, required, referType);
		} else if (epmDocument2 != null) {
		    referType = "RELATED_DRAWING";
		    partEpmRelation.associateCreate(moldPart, epmDocument2, required, referType);
		} else if (epmDocument3 != null) {
		    referType = "RELATED_DRAWING";
		    partEpmRelation.associateCreate(moldPart, epmDocument3, required, referType);
		} else {
		    return false;
		}

		return true;

	    } else {

		// 1품 다도 도면 찾아 넣기
		EPMDocument epmDocument = getLatestEPM(optionModel);
		if (epmDocument == null) {
		    return false;
		}

		int required = 1;
		String referType = null;
		if (optionModel.endsWith("_PRT")) {
		    referType = "RELATED_MODEL"; // "RELATED_DRAWING"; // "RELATED_MODEL"; - VIEW UI에서 부품은 보이고 수정화면은 않보임
		} else if (optionModel.endsWith("_DWG") || optionModel.endsWith("_PLS")) {
		    referType = "RELATED_DRAWING";
		} else {
		    referType = "RELATED_MODEL"; // "RELATED_DRAWING"; // "RELATED_MODEL"; - VIEW UI에서 부품은 보이고 수정화면은 않보임
		}

		partEpmRelation.associateCreate(moldPart, epmDocument, required, referType);

		return true;
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return false;
	}
    }

    // EO 결재상신 Validation - ERP 존재 여부 체크
    @Override
    public List<PartValidationDTO> validErpExist(List<WTPart> targetPartList) throws Exception {
	return new PartEcoValidator().validErpExist(targetPartList);
    }

    // EO 결재상신 Validation - 체크 속성 누락 여부 체크
    @Override
    public List<PartValidationDTO> validCheckedProps(List<WTPart> targetPartList, HashMap<String, String> EcoValidParam) throws Exception {
	return new PartEcoValidator().validCheckedProps(targetPartList, EcoValidParam);
    }

    // EO 결재상신 Validation - 반제품 ERP에 존재하는지 체크 (금형 ECO의 경우 관련제품이 아직 ERP에 생성되지 않았습니다. )
    @Override
    public List<PartValidationDTO> validHalbErpExistByDie(List<WTPart> targetPartList) throws Exception {
	return new PartEcoValidator().validHalbErpExistByDie(targetPartList);
    }

    // 프로젝트 부품 저장 시점에 부품의 프로젝트 No가 없으면 넣어준다.
    @Override
    public void makeProjectPartRelationByProject(String projectNo, String partNo) throws Exception {

	if (projectNo == null || partNo == null) {
	    return;
	}

	WTPart wtPart = getLatestPart(partNo);
	if (wtPart == null) {
	    return;
	}

	QueryResult qr = PartUtil.getPartProjectLink(wtPart, null);
	if (qr != null && qr.size() > 0) {
	    return;
	}

	ProjectMaster project = PartUtil.getProjectMasterByProjectNo(projectNo);

	if (project == null)
	    return;

	AssociateProject ap = new PartProjectRelation();
	ap.associateCreate(wtPart, project);

    }

    @Override
    public void updateManufacPlaceByProject(Map<String, Object> reqMap) throws Exception {
	String partNo = (String) reqMap.get("partNo");
	String mfType = (String) reqMap.get("mfType");
	String place = (String) reqMap.get("place");

	WTPart wtPart = getLatestPart(partNo);
	if (wtPart == null || StringUtils.isEmpty(mfType) || StringUtils.isEmpty(place)) {
	    return;
	}

	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface) wtPart;
	WTPartTypeInfo wtPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();
	BeanUtils.setProperty(wtPartTypeInfo, "ptc_str_25", mfType);
	BeanUtils.setProperty(wtPartTypeInfo, "ptc_str_13", place);

	PersistenceServerHelper.manager.update(wtPart);
    }

    // Admin 체크
    public boolean isAdmin() throws Exception {
	boolean isAdmin = false;
	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	if (user != null) {
	    String id = user.getAuthenticationName();
	    if ("wcadmin".equals(id) && CommonUtil.isAdmin()) {
		isAdmin = true;
	    }
	}

	return isAdmin;
    }

    // Biz Admin 체크
    public boolean isBizAdmin() throws Exception {
	return CommonUtil.isBizAdmin();
    }

    // member 체크
    public boolean isMember(String group) throws Exception {
	return CommonUtil.isMember(group);
    }

    // Member List 체크
    public boolean[] isMember(String... groupList) throws Exception {

	if (groupList == null) {
	    boolean[] retBool = new boolean[0];
	    return retBool;
	}

	boolean[] auth = new boolean[groupList.length];
	for (int k = 0; k < groupList.length; k++) {
	    auth[k] = false;
	}

	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	Enumeration en = user.parentGroupNames();
	while (en.hasMoreElements()) {
	    String st = (String) en.nextElement();
	    for (int k = 0; k < groupList.length; k++) {
		String group = groupList[k];
		if (st.equals(group)) {
		    auth[k] = true;
		}
	    }
	}

	return auth;
    }

    // Multi Part No A Link
    @Override
    public String getALinkByMultiPartNo(String partNos) throws Exception {
	return partBaseDao.getALink(partNos, "openViewPart");
    }

    // 썸네일용 EPM 가져오기
    @Override
    public EPMDocument getThumbViewEPMDocByPart(String wtPartOid) throws Exception {
	WTPart wtPart = (WTPart) CommonUtil.getObject(wtPartOid);
	return this.getThumbViewEPMDocByPart(wtPart);
    }

    @Override
    public EPMDocument getThumbViewEPMDocByPart(WTPart wtPart) throws Exception {

	if (wtPart == null)
	    return null;

	ArrayList refDocs = new ArrayList();

	// 관련 모델 조회.
	refDocs = EDMHelper.getReferenceDocs(wtPart, EDMHelper.REFERENCE_TYPE_MODEL, -1);

	// 대표 연관 도면
	if (refDocs.size() == 0) {
	    refDocs = EDMHelper.getReferenceDocs(wtPart, EDMHelper.REFERENCE_TYPE_RELATED, EDMHelper.REQUIRED_STANDARD);
	}

	// 관련 연관 도면
	if (refDocs.size() == 0) {
	    refDocs = EDMHelper.getReferenceDocs(wtPart, EDMHelper.REFERENCE_TYPE_RELATED, EDMHelper.REQUIRED_RELATED);
	}

	String epmOid = null;
	ReferenceFactory rf = new ReferenceFactory();
	if (refDocs.size() > 0) {
	    epmOid = rf.getReferenceString((EPMDocument) ((Object[]) refDocs.get(0))[1]);
	    Object o = rf.getReference(epmOid).getObject();
	    if (o instanceof EPMDocument) {
		return (EPMDocument) o;
	    } else {
		return null;
	    }
	} else {
	    return null;
	}

    }

    // 프로젝트 부품 저장 시점에 부품의 프로젝트 No가 없으면 넣어준다.
    @Override
    public int getMatchingPartCount(String partOid) throws Exception {

	if (StringUtils.isEmpty(partOid)) {
	    return 0;
	}

	PartMatchingHandler partMatchingHandler = new PartMatchingHandler();
	int count = partMatchingHandler.getMatchingPartCount(partOid);

	return count;
    }

    // ECM에서 연관된 부품을 볼 때 사용함.
    @Override
    public List<PartMatchingDTO> getMatchingPartList(String partOid) throws Exception {

	if (StringUtils.isEmpty(partOid)) {
	    return new ArrayList<PartMatchingDTO>();
	}

	PartMatchingHandler partMatchingHandler = new PartMatchingHandler();
	List<PartMatchingDTO> dtoList = partMatchingHandler.getMatchingPartList(partOid);

	return dtoList;

    }

    // ECO Validation : 개정 버튼 누르면, 연계 부품이 먼저 개정되지 않으면 Validation Message 전달
    @Override
    public String validRelatedPartRevised(EPMDocument epmDoc, String ecoOid) {

	return new PartEcoValidator().validRelatedPartRevised(epmDoc, ecoOid);
    }

    // ECO Validation : 개정 버튼 누르면, 연계 부품이 먼저 개정되지 않으면 Validation Message 전달
    @Override
    public String validRelatedEpmCancelRevised(WTPart wtPart, String ecoOid) {

	return new PartEcoValidator().validRelatedEpmCancelRevised(wtPart, ecoOid);
    }

    // ECO 부품상태 Released 되는 시점에 - 고객 Rev 올려줌. 고객 요청 속성 체크일 때만
    @Override
    public WTPart checkReviseCustomerVersion(WTPart sourcePart) throws Exception {

	if (sourcePart == null)
	    return sourcePart;

	String spCustomRev = PartSpecGetter.getPartSpec(sourcePart, PartSpecEnum.SpCustomRev);

	if (StringUtils.isEmpty(spCustomRev)) {
	    return sourcePart;
	}

	if (!spCustomRev.startsWith("C")) {
	    return sourcePart;
	}

	final String prefix = "C";
	String suffix = spCustomRev.substring(1);

	int suffixInt = Integer.parseInt(suffix);
	suffixInt = suffixInt + 1;

	String suffixTemp = String.valueOf(suffixInt);

	String customRev = null;
	if (suffixTemp.length() == 1) {
	    customRev = prefix + "0" + suffixTemp;
	} else {
	    customRev = prefix + suffixTemp;
	}

	PartSpecSetter.setPartSpec(sourcePart, PartSpecEnum.SpCustomRev, customRev);
	PersistenceServerHelper.update(sourcePart);

	sourcePart = (WTPart) PersistenceHelper.manager.refresh(sourcePart);

	return sourcePart;

    }

    // Catalogue Export
    @Override
    public List<PartCatalogueDTO> getCatalogueList(PartSearchMainDTO partSearchMainDTO) throws Exception {
	List<PartCatalogueDTO> list = partExtDao.getCatalogueList(partSearchMainDTO);
	return list;
    }

    // 부품 도면 연계
    @Override
    public boolean connEpm2Part(String epmOid, String partOid, String referenceType, String requied, String ecad) throws Exception {
	boolean ret = false;

	EPMDocument empDoc = (EPMDocument) CommonUtil.getObject(epmOid);
	WTPart part = null;

	if (partOid != null) {
	    if (partOid.indexOf("wt.part.WTPart") == -1) {
		partOid = "wt.part.WTPartMaster:" + partOid;
		WTPartMaster mast = (WTPartMaster) CommonUtil.getObject(partOid);
		part = getLatestPart(mast);

	    } else {
		part = (WTPart) CommonUtil.getObject(partOid);
	    }
	}

	PartEpmRelation partEpmRelation = new PartEpmRelation();
	partEpmRelation.associateCreate(part, empDoc, "1".equals(ecad), Integer.parseInt(requied), referenceType, true);
	ret = true;

	return ret;
    }

    // 도면 (부품화면) viewable 재생성
    @Override
    public boolean regenerateViewable(String oid) throws Exception {
	boolean ret = false;

	if (StringUtils.isEmpty(oid)) {

	    return ret;

	}

	EPMDocument epmDoc = null;

	ReferenceFactory rf = new ReferenceFactory();
	Object o = rf.getReference(oid).getObject();

	if (o instanceof WTPart) {
	    ArrayList refDocs = new ArrayList();

	    // 관련 모델 조회.
	    refDocs = EDMHelper.getReferenceDocs((WTPart) o, EDMHelper.REFERENCE_TYPE_MODEL, -1);

	    // 대표 연관 도면
	    if (refDocs.size() == 0) {
		refDocs = EDMHelper.getReferenceDocs((WTPart) o, EDMHelper.REFERENCE_TYPE_RELATED, EDMHelper.REQUIRED_STANDARD);
	    }

	    // 관련 연관 도면
	    if (refDocs.size() == 0) {
		refDocs = EDMHelper.getReferenceDocs((WTPart) o, EDMHelper.REFERENCE_TYPE_RELATED, EDMHelper.REQUIRED_RELATED);
	    }

	    if (refDocs.size() > 0) {
		oid = rf.getReferenceString((EPMDocument) ((Object[]) refDocs.get(0))[1]);
	    }
	}

	o = rf.getReference(oid).getObject();
	if (o instanceof EPMDocument) {
	    VieawbleGenerator gen = new VieawbleGenerator();
	    return gen.reGenerator((EPMDocument) o);
	}

	return ret;
    }

    @Override
    public void testPart(Object... args) throws Exception {

	// validRelatedPartRevised((EPMDocument) args[0], (String) args[1]);
	// validRelatedEpmCancelRevised((WTPart) args[2], (String) args[3]);

	// checkReviseCustomerVersion((WTPart)args[0]);

	EDMProperties props = EDMProperties.getInstance();
	String ret = props.convertPrefixNumber((String) args[0], (String) args[1]);
	Kogger.debug(getClass(), "ret:" + ret);

    }

    @Override
    public void addGeneratePart(PartBaseDTO dto, String partNumber) throws Exception {
	// TODO Auto-generated method stub
	String spPartType = dto.getSpPartType();
	PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(spPartType);
	KETPartClassification classific = (KETPartClassification) CommonUtil.getObject(dto.getPartClazOid());
	boolean halbAutoGen = true;
	boolean halbRegist = false;
	boolean copyRegist = false;
	boolean scrabAutoGen = false;
	boolean erpPartRegist = false;

	String scrabAutoPartNo = null;
	String halbAutoPartNo = null;
	String beforeNo = partNumber;
	if (beforeNo.indexOf("-") == -1) {
	    halbAutoPartNo = beforeNo + "A";
	} else {
	    halbAutoPartNo = beforeNo.replaceAll("-", "A-");
	}
	if (beforeNo.startsWith("H7") && ptype == PartTypeEnum.HALB) { // 반제품 터미널
	    if ("C".equals(classific.getDivisionFlag())) { // 자동차 사업부
		if ("2".equals(dto.getSpPlating())) { // Tin
		    // need to generator
		    registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist);
		}
	    } else if ("E".equals(classific.getDivisionFlag())) { // 전자 사업부
		if (!"1".equals(dto.getSpPlating()) && !"3".equals(dto.getSpPlating())) { // 무도금, Pre-Tin
		    // need to generator
		    registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist);
		}
	    }
	} else if (beforeNo.startsWith("K2") && ptype == PartTypeEnum.HALB) { // 전자 K2 반제품
	    if (!"1".equals(dto.getSpPlating()) && !"3".equals(dto.getSpPlating())) { // 무도금, Pre-Tin
		// need to generator
		registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist);
	    }
	}
    }

    @Override
    public boolean scrapAutoGen(PartBaseDTO dto) throws Exception {

	boolean erpPartRegist = false;
	boolean halbRegist = false;
	boolean copyRegist = false;
	boolean scrabAutoGen = false;
	String scrabAutoPartNo = null;
	boolean halbAutoGen = false;
	String halbAutoPartNo = null;

	// 원자재 수지(R20)의 경우 스크랩 생성가능
	dto.setPartScrabAutoGen("Y");
	scrabAutoGen = true;
	dto.setSpPartType("S"); // 스크랩 유형으로 변경
	scrabAutoPartNo = "S" + dto.getPartNumber().substring(1);
	registWTPart(dto, halbRegist, copyRegist, scrabAutoGen, scrabAutoPartNo, halbAutoGen, halbAutoPartNo, erpPartRegist);

	return true;
    }

    @Override
    public PageControl findPartMassPagingList(KETMassPartDTO dto) throws Exception {
	// TODO Auto-generated method stub

	QuerySpec query = getPartMassList(dto);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(dto.getFormPage(), dto.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(dto.getFormPage(), dto.getFormPage(), dto.getPage() + 1, pageSessionId);
	}
	return pageControl;
    }

    private QuerySpec getPartMassList(KETMassPartDTO dto) throws Exception {
	QuerySpec qs = new QuerySpec();

	int index1 = qs.addClassList(KETPartMassPlan.class, true);

	qs.setAdvancedQueryEnabled(true);

	ClassAttribute createStampA2 = new ClassAttribute(KETPartMassPlan.class, "thePersistInfo.createStamp");

	SQLFunction createStamp = SQLFunction.newSQLFunction(SQLFunction.TO_CHAR, createStampA2,
	        ConstantExpression.newExpression("YYYY-MM-DD"));
	createStamp.setColumnAlias("createDate");
	qs.appendSelect(createStamp, false);

	ClassAttribute modifyStampA2 = new ClassAttribute(KETPartMassPlan.class, "thePersistInfo.modifyStamp");

	SQLFunction modifyStamp = SQLFunction.newSQLFunction(SQLFunction.TO_CHAR, modifyStampA2,
	        ConstantExpression.newExpression("YYYY-MM-DD"));
	createStamp.setColumnAlias("modifyDate");
	qs.appendSelect(modifyStamp, false);

	String aliasAt = qs.getFromClause().getAliasAt(index1);

	KeywordExpression ke = new KeywordExpression("CASE WHEN " + aliasAt + ".MASSTARTDATE IS NULL OR SYSDATE <  ADD_MONTHS(" + aliasAt
	        + ".MASSTARTDATE ,'12') THEN 'Y' ELSE 'N' END");
	ke.setColumnAlias("newPart");
	qs.appendSelect(ke, false);

	String pjtNo = dto.getPjtNo();
	String partNo = dto.getPartNo();
	String devSteps = dto.getDevSteps();

	if (StringUtils.isNotEmpty(devSteps)) {

	    String arr[] = devSteps.split(",");
	    boolean isDev = false;
	    boolean isMass = false;
	    for (String dev : arr) {
		if (dev.equals("신제품")) {
		    isDev = true;
		}
		if (dev.equals("양산")) {
		    isMass = true;
		}
	    }
	    if (isDev && isMass) {

	    } else {
		if (isDev) {

		    KeywordExpression kexp = new KeywordExpression(aliasAt + ".PARTNO");
		    KeywordExpression kexp2 = new KeywordExpression(
			    "(SELECT PARTNO FROM KETPARTMASSPLAN WHERE (MASSTARTDATE IS NULL OR SYSDATE < ADD_MONTHS(MASSTARTDATE,'12')))");

		    SearchCondition sc = new SearchCondition(kexp, SearchCondition.IN, kexp2);
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    qs.appendWhere(sc, new int[] { index1 });

		}
		if (isMass) {
		    KeywordExpression kexp = new KeywordExpression(aliasAt + ".PARTNO");
		    KeywordExpression kexp2 = new KeywordExpression(
			    "(SELECT PARTNO FROM KETPARTMASSPLAN WHERE (MASSTARTDATE IS NULL OR SYSDATE < ADD_MONTHS(MASSTARTDATE,'12')))");

		    SearchCondition sc = new SearchCondition(kexp, SearchCondition.NOT_IN, kexp2);
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    qs.appendWhere(sc, new int[] { index1 });
		}
	    }

	}

	if (StringUtils.isNotEmpty(pjtNo)) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(KETPartMassPlan.class, KETPartMassPlan.PJT_NO, "=", pjtNo), new int[] { index1 });
	}

	if (StringUtils.isNotEmpty(partNo)) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(KETPartMassPlan.class, KETPartMassPlan.PART_NO, "=", partNo), new int[] { index1 });
	}

	if (StringUtils.isNotEmpty(dto.getMasStartDate())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    Timestamp stamp = DateUtil.convertStartDate2(dto.getMasStartDate());
	    qs.appendWhere(new SearchCondition(KETPartMassPlan.class, KETPartMassPlan.MAS_START_DATE, ">=", stamp), new int[] { index1 });
	}
	if (StringUtils.isNotEmpty(dto.getMasEndDate())) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    Timestamp stamp = DateUtil.convertStartDate2(dto.getMasEndDate());
	    qs.appendWhere(new SearchCondition(KETPartMassPlan.class, KETPartMassPlan.MAS_START_DATE, "<", stamp), new int[] { index1 });
	}

	return qs;
    }

    @Override
    public void migPartMass() throws Exception {
	List<Map<String, Object>> partList = partBaseDao.getMigMassDataList();

	Transaction trx = new Transaction();
	String partNo = "";
	try {
	    trx.start();

	    KETMassPartDTO dto = new KETMassPartDTO();

	    for (Map<String, Object> map : partList) {
		partNo = (String) map.get("WTPARTNUMBER");
		String mpDate = (String) map.get("MPDATE");
		WTPartMaster master = getMaster(partNo);
		if (master != null) {

		    dto.setPartNo(partNo);
		    QuerySpec qs = this.getPartMassList(dto);
		    QueryResult qr = PersistenceHelper.manager.find(qs);

		    KETPartMassPlan massPlanPart = null;

		    while (qr.hasMoreElements()) {
			Object[] objArr = (Object[]) qr.nextElement();
			massPlanPart = (KETPartMassPlan) objArr[0];
		    }

		    if (massPlanPart == null) {
			massPlanPart = KETPartMassPlan.newKETPartMassPlan();
			massPlanPart.setOwner(SessionHelper.manager.getPrincipalReference());
			massPlanPart.setPartMaster(master);
			massPlanPart.setPartNo(master.getNumber());
			massPlanPart.setPartName(master.getName());
		    }

		    massPlanPart.setMasStartDate(DateUtil.convertStartDate2(mpDate));
		    PersistenceHelper.manager.save(massPlanPart);
		}
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    System.out.println("partNo : " + partNo + " 마이그레이션 중 오류 발생 !");
	    throw e;

	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

    }

    public boolean isMassPart(List<Map<String, Object>> massPartList, String PartNo) {
	boolean isMass = false;
	for (Map<String, Object> massPartMap : massPartList) {
	    if (massPartMap.get("PARTNO").equals(PartNo)) {
		isMass = true;
		break;
	    }
	}

	return isMass;
    }

    @Override
    public void partMassSync() throws Exception {

	List<Map<String, Object>> massPartList = partBaseDao.getMassPartDataList();
	List<Map<String, Object>> partList = partBaseDao.getUpdateTargetMassPartList();

	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	Department userDept = DepartmentHelper.manager.getDepartment(user);
	System.out.println("자재기준 양산시작일 업데이트 대상 건수 " + partList.size() + "건 동기화 시작");
	for (Map<String, Object> map : partList) {
	    String state = (String) map.get("STATESTATE");
	    String version = (String) map.get("VERSION");
	    if (!"APPROVED".equals(state) && "0".equals(version)) {
		continue;
	    }
	    WTPart part = (WTPart) CommonUtil.getObject((String) map.get("PARTOID"));
	    String partNo = part.getNumber();
	    List<Map<String, Object>> pjtInfoList = partBaseDao.getMassPlanByProjectInfo(partNo);
	    partMassPlanUpdate(part, pjtInfoList, user, userDept);
	    System.out.println("자재기준 양산시작일 업데이트 자재 : " + partNo + " 처리 완료");
	}
	System.out.println("자재기준 양산시작일 업데이트 대상 건수 " + partList.size() + "건 동기화 완료");
	List<Map<String, Object>> pjtList = partBaseDao.getProductList();
	System.out.println("프로젝트 기준 제품정보별 하위 BOM 기준으로 업데이트 대상 건수 " + pjtList.size() + "건 동기화 시작");
	for (Map<String, Object> pjtMap : pjtList) {
	    String rootPartNo = (String) pjtMap.get("PARTNO");
	    WTPart wtpart = PartBaseHelper.service.getLatestPart(rootPartNo);
	    if (wtpart != null) {
		List<Map<String, Object>> pjtListParam = new ArrayList<Map<String, Object>>();
		pjtListParam.add(pjtMap);
		partList = partBaseDao.getExtractBomInfo(CommonUtil.getOIDLongValue2Str(wtpart));

		for (Map<String, Object> partMap : partList) {

		    if ("1".equals(partMap.get("LEV"))) {
			continue;
		    }

		    if (isMassPart(massPartList, (String) partMap.get("PARTNO"))) {
			continue;
		    }

		    WTPart part = (WTPart) CommonUtil.getObject("wt.part.WTPart" + ":" + (String) partMap.get("LAST_WTPART_OID"));

		    partMassPlanUpdate(part, pjtListParam, user, userDept);

		    System.out.println("프로젝트 기준 제품정보별 하위 BOM 기준으로 업데이트 : " + (String) partMap.get("PARTNO") + " 처리 완료");
		}
	    }

	}

	System.out.println("프로젝트 기준 제품정보별 하위 BOM 기준으로 업데이트 대상 건수 " + pjtList.size() + "건 동기화 완료");
    }

    private void createMassPartObject(Map<String, Object> param) throws Exception {
	KETMassPartDTO dto = new KETMassPartDTO();
	dto.setPartNo((String) param.get("partNo"));
	QuerySpec qs = this.getPartMassList(dto);
	QueryResult qr = PersistenceHelper.manager.find(qs);
	KETPartMassPlan massPlanPart = null;

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    massPlanPart = (KETPartMassPlan) objArr[0];
	}

	if (massPlanPart != null && massPlanPart.getMasStartDate() != null) {

	} else {
	    if (massPlanPart == null) {
		massPlanPart = KETPartMassPlan.newKETPartMassPlan();
		massPlanPart.setOwner(SessionHelper.manager.getPrincipalReference());
	    }

	    ObjectUtil.manager.convertMapToObject(param, massPlanPart);
	    WTPartMaster master = (WTPartMaster) param.get("masterObject");
	    massPlanPart.setPartMaster(master);

	    PersistenceHelper.manager.save(massPlanPart);
	}

    }

    private void partMassPlanUpdate(WTPart part, List<Map<String, Object>> pjtInfoList, WTUser user, Department userDept) throws Exception {

	if (part != null) {

	    WTPartMaster master = (WTPartMaster) part.getMaster();

	    String partNo = part.getNumber();
	    String partName = part.getName();

	    List<Map<String, Object>> ecoInfoList = partBaseDao.getMassPlanByEcoInfo(partNo);

	    String pjtNo = "";
	    String pjtName = "";
	    String pjtNos = "";
	    String processGb = "";
	    String ecoNo = "";
	    String projectState = "";

	    String modifyName = user.getFullName();
	    String modifyCode = user.getName();
	    String modifyDeptName = "";
	    String modifyDeptCode = "";

	    if (userDept != null) {
		modifyDeptName = userDept.getName();
		modifyDeptCode = userDept.getCode();
	    }

	    Timestamp pjtEndDate = null;
	    Timestamp dr6EndDate = null;
	    Timestamp masStartDate = null;
	    Timestamp ecoApproveDate = null;

	    if (pjtInfoList != null) {
		for (Map<String, Object> pjtMap : pjtInfoList) {
		    if (StringUtils.isEmpty(pjtNo)) {
			pjtNo = (String) pjtMap.get("PJTNO");
			pjtName = (String) pjtMap.get("PJTNAME");
			projectState = (String) pjtMap.get("STATESTATE");
			if (StringUtils.isNotEmpty((String) pjtMap.get("DR6ENDDATE"))) {
			    dr6EndDate = DateUtil.convertStartDate2((String) pjtMap.get("DR6ENDDATE"));
			}
			if ("COMPLETED".equals(projectState)) {
			    masStartDate = DateUtil.convertStartDate2((String) pjtMap.get("EXECENDDATE"));
			    pjtEndDate = masStartDate;
			}
			processGb = (String) pjtMap.get("PROCESS");
		    } else {
			pjtNos = (String) pjtMap.get("PJTNO") + ",";
		    }
		}
	    }
	    pjtNos = StringUtils.removeEnd(pjtNos, ",");

	    String version = "";
	    if (ecoInfoList != null) {
		for (Map<String, Object> ecoMap : ecoInfoList) {
		    ecoApproveDate = DateUtil.convertStartDate2((String) ecoMap.get("ECO_APPROVED_DATE"));
		    ecoNo = (String) ecoMap.get("ECOID");
		    version = (String) ecoMap.get("VERSION");
		    break;
		}
	    }
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("partNo", partNo);
	    paramMap.put("partName", partName);
	    paramMap.put("pjtNo", pjtNo);
	    paramMap.put("pjtName", pjtName);
	    paramMap.put("pjtNos", pjtNos);
	    paramMap.put("processGb", processGb);
	    paramMap.put("ecoNo", ecoNo);
	    paramMap.put("masStartDate", masStartDate);
	    paramMap.put("pjtEndDate", pjtEndDate);
	    paramMap.put("dr6EndDate", dr6EndDate);
	    paramMap.put("ecoApproveDate", ecoApproveDate);
	    paramMap.put("modifyName", modifyName);
	    paramMap.put("modifyCode", modifyCode);
	    paramMap.put("modifyDeptName", modifyDeptName);
	    paramMap.put("modifyDeptCode", modifyDeptCode);
	    paramMap.put("masterObject", master);

	    if ("0".equals(version)) {
		paramMap.put("masStartDate", ecoApproveDate);
		paramMap.put("bigo", "양산단계로 최초 생성된 자재의 ECO승인일");
	    } else {
		if (masStartDate != null) {
		    paramMap.put("bigo", "프로젝트 종료일");
		}
	    }

	    createMassPartObject(paramMap);

	}
    }

    @Override
    public Map<String, Object> saveMassPart(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub

	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String jsonDataStr = (String) reqMap.get("jsonData");

	Transaction transaction = new Transaction();

	try {

	    WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();

	    Department userDept = DepartmentHelper.manager.getDepartment(user);

	    String modifyName = user.getFullName();
	    String modifyCode = user.getName();
	    String modifyDeptName = "";
	    String modifyDeptCode = "";

	    if (userDept != null) {
		modifyDeptName = userDept.getName();
		modifyDeptCode = userDept.getCode();
	    }

	    transaction.start();

	    List<Map<String, Object>> jsonData = mapper.readValue(jsonDataStr, new TypeReference<List<Map<String, Object>>>() {
	    });

	    if (jsonData != null) {

		for (Map<String, Object> data : jsonData) {

		    String oid = (String) data.get("oid");
		    String massStartDate = (String) data.get("masStartDate");

		    KETPartMassPlan part = (KETPartMassPlan) CommonUtil.getObject(oid);

		    String oldMassStartDate = DateUtil.getDateString(part.getMasStartDate(), "d");
		    String bigo = (String) data.get("bigo");
		    String oldBigo = part.getBigo();

		    if (!massStartDate.equals(oldMassStartDate) || !bigo.equals(oldBigo)) {
			part.setMasStartDate(DateUtil.convertStartDate2(massStartDate));
			part.setModifyCode(modifyCode);
			part.setModifyDeptCode(modifyDeptCode);
			part.setModifyDeptName(modifyDeptName);
			part.setModifyName(modifyName);
			part.setBigo(bigo);
			part = (KETPartMassPlan) PersistenceHelper.manager.save(part);
		    }

		}

	    }

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    transaction.rollback();
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

	return resMap;
    }

}
