package ext.ket.part.base.service.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.KETHalbPartDieSetPartLink;
import ext.ket.part.entity.dto.CommonCodeDTO;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartSpecSetter;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class DieHalbLinkUtil {

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    // Die 로 Halb 등록
    public boolean createDieHalbLink(WTPartMaster dieMast, WTPartMaster halbPartMast) throws Exception {

	if (halbPartMast == null || dieMast == null) {
	    return false;
	}

	// A : HALB, B : DIE
	if (!existAlready(dieMast, halbPartMast)) {
	    KETHalbPartDieSetPartLink hdLink = KETHalbPartDieSetPartLink.newKETHalbPartDieSetPartLink(halbPartMast, dieMast);
	    PersistenceHelper.manager.save(hdLink);
	}

	return true;
    }

    // Die 로 Halb 등록
    public boolean updateHalbRepresentMold(WTPartMaster dieMast, WTPartMaster halbPartMast) throws Exception {

	WTPart latestPart = (WTPart) ObjectUtil.getLatestObject(halbPartMast);
	String spRepresentM = PartSpecGetter.getPartSpec(latestPart, PartSpecEnum.SpRepresentM);
	if (StringUtils.isEmpty(spRepresentM)) {

	    try {

		// workable 생성
		// WTPart wtPart = PartUtil.getWorkingCopy(latestPart);

		// PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpRepresentM, dieMast.getNumber());

		// save - stored & modify
		// wtPart = (WTPart) PersistenceHelper.manager.save(wtPart);
		// Check-in
		// wtPart = (WTPart) WorkInProgressHelper.service.checkin(wtPart, "");
		// refresh
		// wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);

		// 불필요한 리비전이 발생하지 않도록 한다 2021.05.28

		PartSpecSetter.setPartSpec(latestPart, PartSpecEnum.SpRepresentM, dieMast.getNumber());
		// save - update
		PersistenceServerHelper.manager.update(latestPart);

		latestPart = (WTPart) PersistenceHelper.manager.refresh(latestPart);

	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    }
	}

	return true;
    }

    public boolean existAlready(WTPartMaster dieMast, WTPartMaster halbPartMast) throws Exception {

	boolean checkHalb = false;

	List<KETHalbPartDieSetPartLink> oldClassLinkList = query.queryForListLinkByRoleB(KETHalbPartDieSetPartLink.class,
	        WTPartMaster.class, dieMast);
	if (oldClassLinkList != null) {

	    for (KETHalbPartDieSetPartLink link : oldClassLinkList) {
		WTPartMaster halbTemp = link.getHalb();
		if (halbPartMast.getNumber().equals(halbTemp.getNumber())) {
		    checkHalb = true;
		    break;
		}
	    }
	}

	return checkHalb;
    }

    public boolean deleteDieHalbLink(WTPartMaster dieMast, WTPartMaster halbPartMast) throws Exception {

	if (halbPartMast == null || dieMast == null) {
	    return false;
	}

	List<KETHalbPartDieSetPartLink> oldClassLinkList = query.queryForListLinkByRoleB(KETHalbPartDieSetPartLink.class,
	        WTPartMaster.class, dieMast);
	if (oldClassLinkList != null) {

	    for (KETHalbPartDieSetPartLink link : oldClassLinkList) {
		WTPartMaster halbTemp = link.getHalb();
		if (halbPartMast.getNumber().equals(halbTemp.getNumber())) {
		    PersistenceHelper.manager.delete(link);
		}
	    }
	}

	return true;
    }

    public boolean existPartDieHalbLink(String partNumber) throws Exception {

	if (StringUtils.isEmpty(partNumber)) {
	    return false;
	}

	WTPartMaster mast = PartBaseHelper.service.getMaster(partNumber);

	if (mast == null)
	    return false;

	List<KETHalbPartDieSetPartLink> link1 = getHalbDieLinkByDie(mast);

	if (link1 == null || link1.size() == 0) {
	    List<KETHalbPartDieSetPartLink> link2 = getHalbDieLinkByHalb(mast);
	    if (link2 == null || link2.size() == 0) {
		return false;
	    } else {
		return true;
	    }
	} else {
	    return true;
	}
    }

    public List<KETHalbPartDieSetPartLink> getHalbDieLinkByDie(WTPartMaster dieMast) throws Exception {

	SessionContext sessioncontext = SessionContext.newContext();
	List<KETHalbPartDieSetPartLink> oldClassLinkList = null;
	try {
	    SessionHelper.manager.setAdministrator();
	    oldClassLinkList = query.queryForListLinkByRoleB(KETHalbPartDieSetPartLink.class, WTPartMaster.class, dieMast);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}

	return oldClassLinkList;
    }

    public List<KETHalbPartDieSetPartLink> getHalbDieLinkByHalb(WTPartMaster halbMast) throws Exception {

	SessionContext sessioncontext = SessionContext.newContext();
	List<KETHalbPartDieSetPartLink> oldClassLinkList = null;
	try {
	    SessionHelper.manager.setAdministrator();
	    oldClassLinkList = query.queryForListLinkByRoleA(WTPartMaster.class, KETHalbPartDieSetPartLink.class, halbMast);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}

	return oldClassLinkList;
    }

    public List<CommonCodeDTO> getDieListOfHalb(String halbOid) throws Exception {

	List<CommonCodeDTO> list = new ArrayList<CommonCodeDTO>();

	if (StringUtils.isEmpty(halbOid)) {
	    return list;
	}

	if (halbOid.indexOf("wt.part.WTPartMaster") == -1) {
	    halbOid = "wt.part.WTPartMaster:" + halbOid;
	}

	WTPartMaster halbPartMast = (WTPartMaster) CommonUtil.getObject(halbOid);

	List<KETHalbPartDieSetPartLink> oldClassLinkList = query.queryForListLinkByRoleA(WTPartMaster.class,
	        KETHalbPartDieSetPartLink.class, halbPartMast);
	if (oldClassLinkList != null) {

	    for (KETHalbPartDieSetPartLink link : oldClassLinkList) {
		WTPartMaster dieTemp = link.getDieSet();
		CommonCodeDTO dto = new CommonCodeDTO();
		dto.setValue(CommonUtil.getOIDString(dieTemp));
		dto.setText(dieTemp.getNumber());
		list.add(dto);
	    }
	}

	return list;
    }

    public List<CommonCodeDTO> getDieDetailOfHalb(String dieOid, Locale locale) throws Exception {

	List<CommonCodeDTO> list = new ArrayList<CommonCodeDTO>();

	if (StringUtils.isEmpty(dieOid)) {
	    return list;
	}

	if (dieOid.indexOf("wt.part.WTPartMaster") == -1) {
	    dieOid = "wt.part.WTPartMaster:" + dieOid;
	}

	WTPartMaster latestDie = (WTPartMaster) CommonUtil.getObject(dieOid);

	if (latestDie == null) {
	    return list;
	}

	WTPart wtpart = PartBaseHelper.service.getLatestPart(latestDie);

	// 부품 단위 추가
	if (true) {
	    CommonCodeDTO dto = new CommonCodeDTO();
	    dto.setValue("partDefaultUnit");
	    dto.setText(latestDie.getDefaultUnit().toString());
	    dto.setType("SELECT");
	    Kogger.debug(getClass(), dto);
	    list.add(dto);
	}

	PartBaseDTO paramDTO = new PartBaseDTO();
	paramDTO.setPartOid(CommonUtil.getOIDString(wtpart));

	PartBaseDTO baseDTO = PartBaseHelper.service.viewDetailPart(paramDTO, locale);
	List<PartClassAttrLinkDTO> attrList = baseDTO.getPartClassAttrLinkDTOList();
	for (PartClassAttrLinkDTO partClassAttrLinkDTO : attrList) {
	    if (true) {
		CommonCodeDTO dto = new CommonCodeDTO();
		dto.setValue(partClassAttrLinkDTO.getPartAttributeDTO().getAttrCode());
		dto.setText(partClassAttrLinkDTO.getPartStandardValue());
		dto.setType(partClassAttrLinkDTO.getPartAttributeDTO().getAttrInputType());
		Kogger.debug(getClass(), dto);
		list.add(dto);
	    }

	    // spDieWhereTemp 처리
	    if ("spDieWhere".equals(partClassAttrLinkDTO.getPartAttributeDTO().getAttrCode())) {
		CommonCodeDTO dto = new CommonCodeDTO();
		dto.setValue("spDieWhereTemp");
		dto.setText(PartBaseHelper.service.getManufacPlaceName(partClassAttrLinkDTO.getPartStandardValue()));
		dto.setType("TEXT");
		Kogger.debug(getClass(), dto);
		list.add(dto);
	    }

	}

	// KETPartClassification claz = null;
	// KETPartClassLink classLink = null;
	// KETPartProjectLink projectLink = null;
	// List<PartClassAttrLinkDTO> attrLinkList = new ArrayList<PartClassAttrLinkDTO>();
	// if (wtpart != null) {
	// // 분류체계
	// classLink = query.queryForFirstLinkByRoleB(KETPartClassLink.class, WTPartMaster.class, latestDie);
	// if (classLink != null) {
	// claz = classLink.getClassific();
	// if (claz != null) {
	//
	// List<KETPartClassAttrLink> linkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class, KETPartClassification.class,
	// claz, new QueryBStrategy() {
	//
	// @Override
	// public void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception {
	//
	// SearchUtil.setOrderBy(query, classLink, indexLink, KETPartClassAttrLink.INDEX_ROW, false);
	// SearchUtil.setOrderBy(query, classLink, indexLink, KETPartClassAttrLink.INDEX_COL, false);
	// }
	// });
	//
	// for (KETPartClassAttrLink link : linkList) {
	//
	// KETPartAttribute attr = link.getAttr();
	// if (PartSpecEnum.SpProjectNo.getAttrCode().equals(attr.getAttrCode()) ||
	// PartSpecEnum.SpEoNo.getAttrCode().equals(attr.getAttrCode())// 프로젝트, 중량정보는 화면에 붙박이라서 뺀 후
	// PartSpecEnum.SpNetWeight.getAttrCode().equals(attr.getAttrCode())) { // 별도로 처리하여 필수를 체크한다.
	// continue;
	// }
	//
	// CommonCodeDTO dto = new CommonCodeDTO();
	// dto.setValue(CommonUtil.getOIDString(dieTemp));
	// dto.setText(attr.getAttrCode());
	// dto.setType(attr.getAttrInputType());
	// list.add(dto);
	// }
	// }
	// }
	// }

	return list;
    }

    // Die 로 Halb 등록
    public void createMigDieHalbLink(WTPartMaster dieMast, String partProdMigNumber) throws Exception {

	if (dieMast == null || StringUtils.isEmpty(partProdMigNumber)) {
	    return;
	}

	if (partProdMigNumber.indexOf(",") != -1) {

	    String[] elements = partProdMigNumber.split(",");
	    for (String element : elements) {
		if (StringUtils.isEmpty(element)) {
		    continue;
		} else {
		    WTPartMaster halbPartMast = PartBaseHelper.service.getMaster(element.trim());
		    if (halbPartMast != null) {
			createDieHalbLink(dieMast, halbPartMast);
		    }
		}
	    }

	} else {
	    WTPartMaster halbPartMast = PartBaseHelper.service.getMaster(partProdMigNumber.trim());
	    if (halbPartMast != null) {
		createDieHalbLink(dieMast, halbPartMast);
	    }
	}
    }

}
