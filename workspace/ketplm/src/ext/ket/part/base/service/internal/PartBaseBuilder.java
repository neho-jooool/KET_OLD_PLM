package ext.ket.part.base.service.internal;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanUtils;

import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartTypeInfo;
import wt.part.WTPartTypeInterface;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.vc.VersionControlHelper;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.project.ProjectMaster;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartProjectLink;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;

public class PartBaseBuilder {

    public WTPart buildDto2Pers(WTPart oWtPart, PartBaseDTO dto, boolean logging) throws Exception {

	WTPart wtPart = (oWtPart == null) ? WTPart.newWTPart() : oWtPart;

	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface) wtPart;
	WTPartTypeInfo wtPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();

	String userId = null;
	String userName = null;
	String deptCode = null;
	String deptName = null;

	if (logging) {

	    WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	    userId = user.getName();
	    userName = user.getFullName();
	    Department userDept = DepartmentHelper.manager.getDepartment(user);
	    deptCode = (userDept == null) ? "" : userDept.getName();
	    deptName = (userDept == null) ? "" : userDept.getCode();
	}

	PartSpecEnum[] partSpecEnumList = PartSpecEnum.values();
	for (PartSpecEnum partSpecEnum : partSpecEnumList) {

	    if (partSpecEnum == PartSpecEnum.SpProjectNo || partSpecEnum == PartSpecEnum.SpEoNo
		    || partSpecEnum == PartSpecEnum.SpPartNameHis || partSpecEnum == PartSpecEnum.SpPartRevision
		    || partSpecEnum == PartSpecEnum.SpIsDeleted || partSpecEnum == PartSpecEnum.SpBOMFlag
		    || partSpecEnum == PartSpecEnum.SpWeightUnit) {

		continue;
	    }

	    // YAZAKI 여부 : YES,NO
	    if (partSpecEnum == PartSpecEnum.SpIsYazaki && !"YES".equals(dto.getSpIsYazaki())) { // 비어 있으면 NO를 입력
		dto.setSpIsYazaki("NO");
	    }

	    if (logging) {
		String valueOld = StringUtil.checkNull(BeanUtils.getProperty(wtPartTypeInfo,
		        partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase()));
		String valueNew = StringUtil.checkNull(BeanUtils.getProperty(dto, partSpecEnum.getAttrCode()));
		if (!valueOld.equals(valueNew)) {
		    PartChangeLogHandler.changeLog(wtPart, partSpecEnum.getAttrCode(), partSpecEnum.getAttrName(), valueOld, valueNew,
			    userId, userName, deptCode, deptName);
		}

	    }

	    if (partSpecEnum == PartSpecEnum.SpScrabCode || partSpecEnum == PartSpecEnum.SpRepresentM) {
		// 공백제거
		String value = BeanUtils.getProperty(dto, partSpecEnum.getAttrCode());
		if (value != null)
		    value = value.trim();

		BeanUtils.setProperty(wtPartTypeInfo, partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase(), value);

	    } else if (partSpecEnum == PartSpecEnum.SpMTerminal || partSpecEnum == PartSpecEnum.SpMConnector
		    || partSpecEnum == PartSpecEnum.SpMClip || partSpecEnum == PartSpecEnum.SpMCover || partSpecEnum == PartSpecEnum.SpMRH
		    || partSpecEnum == PartSpecEnum.SpMWireSeal || partSpecEnum == PartSpecEnum.SpMatchBulb) {
		// 공백제거
		String value = BeanUtils.getProperty(dto, partSpecEnum.getAttrCode());
		if (value != null) {
		    value = value.trim();
		    value = value.replace(" ", "");
		}

		BeanUtils.setProperty(wtPartTypeInfo, partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase(), value);

	    } else {

		BeanUtils.setProperty(wtPartTypeInfo, partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase(),
		        BeanUtils.getProperty(dto, partSpecEnum.getAttrCode()));
	    }
	}

	return wtPart;

    }

    public PartBaseDTO buildPers2Dto(WTPart wtPart, KETPartClassLink classLink, KETPartProjectLink projectLink,
	    List<PartClassAttrLinkDTO> partClassAttrLinkDTOList, PartBaseDao partBaseDao, Locale locale) throws Exception {

	PartBaseDTO dto = new PartBaseDTO();

	if (wtPart == null)
	    return dto;

	// 사양정보
	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface) wtPart;
	WTPartTypeInfo wtPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();

	PartSpecEnum[] partSpecEnumList = PartSpecEnum.values();
	for (PartSpecEnum partSpecEnum : partSpecEnumList) {
	    if (partSpecEnum == PartSpecEnum.SpProjectNo || partSpecEnum == PartSpecEnum.SpEoNo)
		continue;

	    BeanUtils.setProperty(dto, partSpecEnum.getAttrCode(), PartSpecGetter.getPartSpecWithType(wtPartTypeInfo, partSpecEnum));
	}

	// 부품 OOTB 기본 정보
	WTPartMaster master = (WTPartMaster) wtPart.getMaster();

	dto.setPartOid(CommonUtil.getOIDString(wtPart));
	dto.setPartMastOid(CommonUtil.getOIDString(((WTPartMaster) wtPart.getMaster())));
	dto.setPartRevOid(CommonUtil.getVROID(wtPart));
	dto.setPartLatestVersion(ObjectUtil.isLatestVersion(wtPart));

	dto.setPartName(wtPart.getName());
	dto.setPartNumber(wtPart.getNumber());
	dto.setPartDefaultUnit(master.getDefaultUnit().toString());

	dto.setPartRevision(wtPart.getVersionIdentifier().getValue());
	dto.setPartIteration(wtPart.getIterationIdentifier().getValue());
	dto.setPartVersion(wtPart.getVersionIdentifier().getValue() + "." + wtPart.getIterationIdentifier().getValue());
	dto.setPartStateCode(wtPart.getState().getState().getStringValue());
	dto.setPartState(wtPart.getState().getState().getDisplay(locale));
	dto.setPartCheckState(wtPart.getCheckoutInfo().getState().toString());

	dto.setCreatorName(StringUtil.checkNull(wtPart.getIterationInfo().getCreator().getFullName()));
	dto.setModifierName(StringUtil.checkNull(wtPart.getIterationInfo().getModifier().getFullName()));

	WTUser wtuser = (WTUser) wtPart.getIterationInfo().getCreator().getPrincipal();
	Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
	if (userDept != null)
	    dto.setWriteDeptKrName(userDept.getName());

	dto.setCreateDate(DateUtil.getDateString(wtPart.getCreateTimestamp(), "d"));
	dto.setModifyDate(DateUtil.getDateString(wtPart.getModifyTimestamp(), "d"));

	// 분류체계 정보
	if (classLink != null) {
	    KETPartClassification claz = classLink.getClassific();
	    dto.setPartClazOid(CommonUtil.getOIDString(claz));
	    dto.setPartClazRoute(PartClazHelper.service.searchClazRoute(CommonUtil.getOIDLongValue2Str(claz)));
	    dto.setPartClazNameKr(claz.getClassKrName());
	    dto.setNumberingCode(claz.getNumberingCode());
	    dto.setPartNamingOid(PartBaseHelper.service.getPartNamingOid(CommonUtil.getOIDString(claz)));
	    dto.setClassPartType(claz.getClassPartType());
	    dto.setErpProdCode(claz.getErpProdCode());
	    dto.setClassCode(claz.getClassCode());
	    dto.setNumberingCharics(claz.getNumberingCharics());
	}

	// 프로젝트 정보 ??
	if (projectLink != null) {
	    ProjectMaster pjtMaster = projectLink.getProject();
	    dto.setProjectNo(pjtMaster.getPjtNo());
	    dto.setProjectName(pjtMaster.getPjtName());
	}

	// EO 정보
	long branchId = VersionControlHelper.getBranchIdentifier(wtPart);
	if (partBaseDao == null) {
	    PartBaseDao _partBaseDao = new PartBaseDao();
	    dto.setEcoNo(_partBaseDao.searchEONo(branchId));
	} else {
	    dto.setEcoNo(partBaseDao.searchEONo(branchId));
	}

	// propertys
	if (partClassAttrLinkDTOList != null) {
	    for (PartClassAttrLinkDTO partClazAttrLinkDto : partClassAttrLinkDTOList) {

		PartSpecEnum partSpecEnum = PartSpecEnum.toPartSpecEnumByAttrCode(partClazAttrLinkDto.getPartAttributeDTO().getAttrCode());
		String partStandardValue = PartSpecGetter.getPartSpecWithType(wtPartTypeInfo, partSpecEnum);
		partClazAttrLinkDto.setPartStandardValue(partStandardValue);
	    }

	    dto.setPartClassAttrLinkDTOList(partClassAttrLinkDTOList);
	}

	return dto;
    }

    public void test() throws QueryException {
	QuerySpec query = null;
	query.appendWhere(new SearchCondition(WTPart.class, PartSpecEnum.SpPartType.getQuerySpecCode(), SearchCondition.EQUAL, "M"),
	        new int[] { 0 });

    }

}
