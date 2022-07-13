package ext.ket.part.base.service.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.vc.VersionControlHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.ecm.service.KETEcmHelper;
import ext.ket.edm.approval.internal.EpmApprovalDao;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETHalbPartDieSetPartLink;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.entity.KETPartClassAttrLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartValidationDTO;
import ext.ket.part.sap.ErpPartHandler;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryBStrategy;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class PartEcoValidator {

    // EO 결재상신 Validation - ERP 존재 여부 체크
    public List<PartValidationDTO> validErpExist(List<WTPart> targetPartList) throws Exception {

	List<PartValidationDTO> list = new ArrayList<PartValidationDTO>();

	if (targetPartList == null || targetPartList.size() == 0) {
	    return list;
	}

	ErpPartHandler erpPartHandler = new ErpPartHandler();
	for (WTPart mWtPart : targetPartList) {

	    if (mWtPart == null)
		continue;

	    WTPart wtPart = null;
	    if (!VersionControlHelper.isLatestIteration(mWtPart)) {
		wtPart = (WTPart) VersionControlHelper.getLatestIteration(mWtPart, false);
	    } else {
		wtPart = mWtPart;
	    }

	    if (State.INWORK != wtPart.getLifeCycleState()) {
		continue;
	    }

	    if (erpPartHandler.existErpPart(wtPart.getNumber())) {

		PartValidationDTO dto = new PartValidationDTO();
		dto.setValidType("ERP_PART_NO_EXIST");
		dto.setValidPartNumber(wtPart.getNumber());
		dto.setValidPartOid(CommonUtil.getOIDString(wtPart));
		dto.setValidContent(wtPart.getName());
		Kogger.debug(getClass(), "ERP_PART_NO_EXIST:" + dto.toString());
		list.add(dto);
	    }
	}

	return list;
    }

    // EO 결재상신 Validation - 체크 속성 누락 여부 체크
    public List<PartValidationDTO> validCheckedProps(List<WTPart> targetPartList, HashMap<String, String> EcoValidParam) throws Exception {

	List<PartValidationDTO> list = new ArrayList<PartValidationDTO>();

	if (targetPartList == null || targetPartList.size() == 0) {
	    return list;
	}

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	for (WTPart mWtPart : targetPartList) {

	    if (mWtPart == null)
		continue;

	    WTPart wtPart = null;
	    if (!VersionControlHelper.isLatestIteration(mWtPart)) {
		wtPart = (WTPart) VersionControlHelper.getLatestIteration(mWtPart, false);
	    } else {
		wtPart = mWtPart;
	    }

	    if (State.INWORK != wtPart.getLifeCycleState()) {
		continue;
	    }

	    // 분류체계
	    KETPartClassification claz = PartClazHelper.service.getPartClassification(wtPart);

	    if (claz == null) {

		PartValidationDTO dto = new PartValidationDTO();
		dto.setValidType("ERP_PART_PROPS_EXIST");
		dto.setValidPartNumber(wtPart.getNumber());
		dto.setValidPartOid(CommonUtil.getOIDString(wtPart));
		dto.setValidContent("분류체계 연결안됨");
		Kogger.debug(getClass(), "ERP_PART_PROPS_EXIST:" + dto.toString());
		list.add(dto);

		continue;
	    }

	    List<KETPartClassAttrLink> linkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class, KETPartClassification.class,
		    claz, new QueryBStrategy() {
		        @Override
		        public void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

			    SearchUtil.setOrderBy(query, classLink, indexLink, KETPartClassAttrLink.INDEX_ROW, false);
			    SearchUtil.setOrderBy(query, classLink, indexLink, KETPartClassAttrLink.INDEX_COL, false);
		        }
		    });

	    boolean propChecked = false;
	    PartValidationDTO dto = null;
	    String devLevel = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartDevLevel);
	    String isMass = EcoValidParam.get("isMass");
	    String Isyazaki = null;
	    String SpWaterProof = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpWaterProof);// 방수여부
	    // 속성 체크
	    for (KETPartClassAttrLink link : linkList) {
		KETPartAttribute attr = link.getAttr();
		boolean essential = link.isEssential();
		boolean checked = link.isChecked();

		String HomepageIF = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.HomepageIF);
		String Hompage3DIF = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.Hompage3DIF);

		// 홈페이지 제품사진은 실사 사진으로 올리는 것으로 협의되었기때문에 PLM에서 이미지를 첨부할필요없다 2016.07.29 황정태
		/*
	         * if ("hompageImgIF".equals(attr.getAttrCode())) {//홈페이지 관련 속성은 분류체계에 필수입력여부가 전부 no이기 때문에 이미지등록을 해야하는 지 판단해서 걸러줄건지 말건지 결정한다
	         * //개발단계가 양산 또는 eco 양산이관이고 홈페이지 등록여부가 YES이면서 홈페이지 3D등록여부가 NO일때 이미지는 필수 if ( ("PC004".equals(devLevel) || "Y".equals(isMass)
	         * ) && "YES".equals(HomepageIF) && "NO".equals(Hompage3DIF) ) { checked = true; } }
	         */
		// 필수와 체크ed 인 것이 들어가 있는가?
		if (!essential && !checked) {
		    continue;
		}

		boolean hasValidation = false;

		if (PartSpecEnum.SpProjectNo.getAttrCode().equals(attr.getAttrCode())) {
		    if ("PC004".equals(devLevel)) {// 양산단계이면 프로젝트 번호 유무 체크안함 2015.02.04 전상우 대리 요청 by 황정태
			continue;
		    }
		    QueryResult qr = PartUtil.getPartProjectLink(wtPart, null);
		    if (qr == null || qr.size() == 0) {
			hasValidation = true;
		    }

		} else if (PartSpecEnum.SpMWireSeal.getAttrCode().equals(attr.getAttrCode())) {// 방수구분이 '방수' 가 아니면 매칭 Wire Seal은 입력 할 필요가 없다
											       // 2016.07.22 황정태수정 박주미요청
		    if (!"SEALED010".equals(SpWaterProof)) {
			continue;
		    } else {
			hasValidation = true;
		    }
		} else {
		    String value = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.toPartSpecEnumByAttrCode(attr.getAttrCode()));
		    if (PartSpecEnum.SpYazakiNo.getAttrCode().equals(attr.getAttrCode())) {// Yazaki여부가 No인경우 Yazaki no 체크안함 2015.02.04 전상우
			                                                                   // 대리 요청 by 황정태
			Isyazaki = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpIsYazaki);
			if ("NO".equals(Isyazaki)) {
			    continue;
			}
		    }
		    if (PartSpecEnum.SpNetWeight.getAttrCode().equals(attr.getAttrCode())
			    || PartSpecEnum.SpTotalWeight.getAttrCode().equals(attr.getAttrCode())
			    || PartSpecEnum.SpScrabWeight.getAttrCode().equals(attr.getAttrCode())) {// 중량 정보공백입력 차단 by 황정태 2015.06.25
			value = value.replaceAll("\\p{Space}", "");
			value = StringUtils.remove(value, ",");
			String partType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
			PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(partType);
			
			if (StringUtils.isEmpty(value)) {
			    if (ptype == PartTypeEnum.HALB || ptype == PartTypeEnum.FERT || ptype == PartTypeEnum.HAWA) {// 중량 정보 null 체크(반제품, 제품, 상품에 한해 by 황정태 2017.03.17)
				hasValidation = true;
			    }
			    
			} else {
			    if(!threeDecimalCheck(value)){//소수점 셋째 자리포맷만 허용 초과시 ERP 로 못넘어간다
				hasValidation = true;
			    }
			    if (Double.parseDouble(value) > 0) {
			    }else{
				if (ptype == PartTypeEnum.HALB || ptype == PartTypeEnum.FERT || ptype == PartTypeEnum.HAWA) {// 중량 정보 0 체크(반제품, 제품, 상품에 한해 by 황정태 2017.03.17)
				    hasValidation = true;
				}
			    }
			}

		    }

		    if ("hompageImgIF".equals(attr.getAttrCode())) {
			continue;
//			try {
//			    // 실제 산출물을 체크하여 이미지 값이 있으면 value에 값을 넣는다.
//			    ContentHolder holder = (ContentHolder) wtPart;
//			    holder = ContentHelper.service.getContents(holder);
//			    Vector contentVec = ContentHelper.getContentListAll(holder);
//			    Iterator<ContentItem> iter = contentVec.iterator();
//			    while (iter.hasNext()) {
//				ContentItem item = iter.next();
//				if (item.getRole() == ContentRoleType.PRIMARY) {
//
//				    if (item instanceof ApplicationData) {
//					value = "Y";
//					break;
//				    }
//
//				}
//			    }
//
//			} catch (Exception e) {
//			    // TODO Auto-generated catch block
//			    e.printStackTrace();
//			}

		    }

		    if (StringUtils.isEmpty(value)) {
			hasValidation = true;
		    }
		}

		if (hasValidation) {
		    if (propChecked == false) {
			dto = new PartValidationDTO();
			dto.setValidType("ERP_PART_PROPS_EXIST");
			dto.setValidPartNumber(wtPart.getNumber());
			dto.setValidPartOid(CommonUtil.getOIDString(wtPart));
			dto.setValidContent("");
		    }

		    if ("".equals(dto.getValidContent())) {
			dto.setValidContent(attr.getAttrName());
		    } else {
			dto.setValidContent(dto.getValidContent() + ", " + attr.getAttrName());
		    }

		    propChecked = true;
		}

	    }

	    if (propChecked) {
		Kogger.debug(getClass(), "ERP_PART_PROPS_EXIST:" + dto.toString());
		list.add(dto);
	    }
	}

	return list;

    }
    
    private boolean threeDecimalCheck(String value){
	String pattern = "[0-9]*\\.[0-9]{3}";
	Pattern p = Pattern.compile(pattern);
	Matcher m = p.matcher(value);
	return m.matches();
    }

    // EO 결재상신 Validation - 반제품 ERP에 존재하는지 체크
    public List<PartValidationDTO> validHalbErpExistByDie(List<WTPart> targetPartList) throws Exception {

	List<PartValidationDTO> list = new ArrayList<PartValidationDTO>();

	if (targetPartList == null || targetPartList.size() == 0) {
	    return list;
	}

	ErpPartHandler erpPartHandler = new ErpPartHandler();
	DieHalbLinkUtil util = new DieHalbLinkUtil();
	for (WTPart mWtPart : targetPartList) {

	    if (mWtPart == null)
		continue;

	    WTPart wtPart = null;
	    if (!VersionControlHelper.isLatestIteration(mWtPart)) {
		wtPart = (WTPart) VersionControlHelper.getLatestIteration(mWtPart, false);
	    } else {
		wtPart = mWtPart;
	    }

	    if (State.INWORK != wtPart.getLifeCycleState()) {
		continue;
	    }

	    // 관련 반제품 찾기
	    List<KETHalbPartDieSetPartLink> dieHalbLinkList = util.getHalbDieLinkByDie((WTPartMaster) wtPart.getMaster());

	    for (KETHalbPartDieSetPartLink link : dieHalbLinkList) {
		WTPartMaster halbPartMast = link.getHalb();
		// ERP에 없다면...
		if (!erpPartHandler.existErpPart(halbPartMast.getNumber())) {
		    WTPart latestHalbPart = PartBaseHelper.service.getLatestPart(halbPartMast);
		    PartValidationDTO dto = new PartValidationDTO();
		    dto.setValidType("ERP_HALB_PART_EXIST");
		    dto.setValidPartNumber(latestHalbPart.getNumber());
		    dto.setValidPartOid(CommonUtil.getOIDString(latestHalbPart));
		    dto.setValidContent(latestHalbPart.getName());
		    Kogger.debug(getClass(), "ERP_HALB_PART_EXIST:" + dto.toString());
		    list.add(dto);
		}
	    }
	}

	return list;

    }

    // ECO Validation : 개정 버튼 누르면, 연계 부품이 먼저 개정되지 않으면 Validation Message 전달
    // epmDoc은 old 리비전을 넘겨준다.
    public String validRelatedPartRevised(EPMDocument epmDoc, String ecoOid) {

	String retStr = null;

	try {

	    if (StringUtils.isEmpty(ecoOid) || epmDoc == null) {
		return retStr;
	    }

	    // 먼저 도면이 개정 대상인지 체크
	    // Approved 상태가 아니면 return;
	    if (epmDoc.getLifeCycleState() != State.toState("APPROVED")) {
		return retStr;
	    }

	    // ECO에 연결된 개정안된 전체부품을 가져온다.
	    List<WTPart> notReviesedPartListByEco = KETEcmHelper.service.getWTPartListUnrevisedFromEco(ecoOid);

	    // 위에 해당하는 부품이 없으면 return; 한다.
	    if (notReviesedPartListByEco == null || notReviesedPartListByEco.size() == 0) {
		return retStr;
	    }

	    // 도면에 연계된 부품을 가져온다.
	    List<WTPartMaster> getRelatedPartListByEpm = new RelatedEpmHandler().getRelPartByEcoPMDoc(epmDoc);

	    if (getRelatedPartListByEpm == null || getRelatedPartListByEpm.size() == 0) {
		return epmDoc.getNumber() + " 도면에 연결된 부품이 없습니다.";
	    }

	    // 해당 부품이 개정되었으면, validation Message를 작성한다.
	    List<String> checkedPartList = new ArrayList<String>();
	    for (WTPart ecoPart : notReviesedPartListByEco) {
		for (WTPartMaster partMast : getRelatedPartListByEpm) {
		    if (ecoPart.getNumber().equals(partMast.getNumber())) {
			checkedPartList.add(partMast.getNumber());
		    }
		}
	    }

	    if (checkedPartList.size() > 0) {
		retStr = "'" + epmDoc.getNumber() + "' 도면에 연결된 부품 '";
		int idx = 0;
		for (String partNumber : checkedPartList) {
		    retStr = retStr + (idx == 0 ? "" : ",") + partNumber;
		    idx++;
		}
		retStr = retStr + "'을 먼저 개정해야 합니다.";
	    }

	    return retStr;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return retStr;
    }

    // ECO Validation : 개정 버튼 누르면, 연계 부품이 먼저 개정되지 않으면 Validation Message 전달
    // wtPart는 개정된 wtpart
    public String validRelatedEpmCancelRevised(WTPart wtPart, String ecoOid) {

	String retStr = null;

	try {

	    if (StringUtils.isEmpty(ecoOid) || wtPart == null) {
		return retStr;
	    }

	    // 먼저 부품이 개정 취소 대상인지 체크
	    // InWork 상태가 아니면 return;
	    if (wtPart.getLifeCycleState() != State.INWORK) {
		return retStr;
	    }

	    // INWORK상태 0리비전은 개정취소 대상이 아님 따라서 return;
	    if (wtPart.getVersionIdentifier().getValue().equals("0")) {
		return retStr;
	    }

	    // ECO에 연결된 개정된 도면을 가져온다.
	    List<EPMDocument> reviesedEpmListByEco = new EpmApprovalDao().getEpmDocByEco(ecoOid);

	    // 위에 해당하는 도면이 없으면 return; 한다.
	    if (reviesedEpmListByEco == null || reviesedEpmListByEco.size() == 0) {
		return retStr;
	    }

	    // 부품에 연계된 도면을 참조도면을 제외하고 찾는다.
	    List<EPMDocumentMaster> getRelatedEpmListByPart = new RelatedEpmHandler().getRelEPMDocByEcoPart(wtPart);

	    if (getRelatedEpmListByPart == null || getRelatedEpmListByPart.size() == 0) {
		return retStr;
	    }

	    // 해당 도면이 개정되었으면, validation Message를 작성한다.
	    List<String> checkedPartList = new ArrayList<String>();
	    for (EPMDocument ecoEpm : reviesedEpmListByEco) {
		for (EPMDocumentMaster relatedEpm : getRelatedEpmListByPart) {
		    if (ecoEpm.getNumber().equals(relatedEpm.getNumber())) {
			checkedPartList.add(relatedEpm.getNumber());
		    }
		}
	    }

	    if (checkedPartList.size() > 0) {
		retStr = "'" + wtPart.getNumber() + "' 부품에 연결된 도면 '";
		int idx = 0;
		for (String epmNumber : checkedPartList) {
		    retStr = retStr + (idx == 0 ? "" : ",") + epmNumber;
		    idx++;
		}
		retStr = retStr + "'을 먼저 개정 취소 해야 합니다.";
	    }

	    return retStr;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return retStr;
    }

}
