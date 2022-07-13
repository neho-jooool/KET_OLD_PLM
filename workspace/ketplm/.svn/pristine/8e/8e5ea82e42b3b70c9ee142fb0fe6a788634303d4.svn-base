package ext.ket.edm.cad2bom.service.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.iba.definition.StringDefinition;
import wt.iba.value.IBAHolder;
import wt.iba.value.StringValue;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.edm.EPMDocProjectLink;
import e3ps.edm.EPMLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.beans.EDMServiceHelper;
import e3ps.project.E3PSProject;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMaster;
import e3ps.project.ReviewProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartProjectLink;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

final public class EpmInfoUpadator {

	// 부품정보로 도면의 기본정보 자동 생성

	// 사업부, 보안등급, 프로젝트번호 - 부품의 연계 프로젝트 정보
	// 도면구분 - 부품의 개발단계
	// 도면유형 - 제품도면으로 고정
	public void updateEpmInfo(WTPartMaster partMast, EPMDocumentMaster epmMast, WTPart wtPart, EPMDocument epmDocument) throws Exception {

		// 부품의 프로젝트 정보 : 사업부, 보안등급, 프로젝트번호
		KETPartProjectLink currentProjectLink = null;
		QueryResult qr = PartUtil.getPartProjectLink(wtPart, null);
		while (qr.hasMoreElements()) {
			Object[] objs = (Object[]) qr.nextElement();
			currentProjectLink = (KETPartProjectLink) objs[0];
		}

		if (currentProjectLink == null) {

			// PartType이 M 금형부품일 경우 Die의 Project 정보를 넣어준다.
			String partType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);

			if ("M".equals(partType)) {

				String moldNumber = wtPart.getNumber();
				String dieNumber = moldNumber;
				if (dieNumber.indexOf("-") != -1) {
					dieNumber = dieNumber.substring(0, dieNumber.indexOf("-"));
					WTPart diePart = PartBaseHelper.service.getLatestPart(dieNumber);
					if (diePart != null) {
						QueryResult qrDie = PartUtil.getPartProjectLink(diePart, null);
						while (qrDie.hasMoreElements()) {
							Object[] objs = (Object[]) qrDie.nextElement();
							currentProjectLink = (KETPartProjectLink) objs[0];
						}
					}
				}
			}
		}

		String securityLevel = null;
		if (currentProjectLink != null) { // 프로젝트 정보가 존재할 경우에 처리

			ProjectMaster projMast = currentProjectLink.getProject();
			// 프로젝트 정보
			EDMServiceHelper.updateProjectLink(epmDocument, projMast, true);

			// 사업부
			String teamType = null;

			E3PSProject e3psProject = ProjectHelper.getLastProject(projMast);

			// E3PSProject e3psProject = PartUtil.getE3PSProject(wtPart);
			//
			// if(e3psProject == null){
			// // PartType이 M 금형부품일 경우
			// String partType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);
			// if("M".equals(partType)){
			// String moldNumber = wtPart.getNumber();
			// String dieNumber = moldNumber;
			// if (dieNumber.indexOf("-") != -1) {
			// dieNumber = dieNumber.substring(0, dieNumber.indexOf("-"));
			// WTPart diePart = PartBaseHelper.service.getLatestPart(dieNumber);
			// if (diePart != null) {
			// e3psProject = PartUtil.getE3PSProject(diePart);
			// }
			// }
			// }
			// }

			if (e3psProject instanceof ProductProject) {
				teamType = ((ProductProject) e3psProject).getTeamType();
			}
			if (e3psProject instanceof ReviewProject) {
				teamType = ((ReviewProject) e3psProject).getAttr1();
			}

			if (teamType != null && teamType.indexOf("전자") != -1) {
				teamType = "E";
			} else if (teamType != null && teamType.indexOf("자동차") != -1) {
				teamType = "C";
			} else {
				teamType = null;
			}

			if (teamType != null) {
				NumberCode typeCode = NumberCodeHelper.manager.getNumberCode("DIVISIONFLAG", teamType);
				EDMServiceHelper.updateTypeCodeLink(epmDocument, typeCode, true);
			} else {

				EPMDocumentMaster cad3dMast = (EPMDocumentMaster) epmDocument.getMaster();

				SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

				List<EPMLink> epmLinkList = query.queryForListLinkByRoleA(EPMDocumentMaster.class, EPMLink.class, cad3dMast);

				WTPartMaster partMaster = null;

				if (epmLinkList != null && epmLinkList.size() > 0) {

					for (EPMLink link : epmLinkList) {

						partMaster = link.getPartMaster();
					}
				}
				WTPart wtpart = PartBaseHelper.service.getLatestPart(partMaster);
				KETPartClassification claz = PartUtil.getPartClassification(wtpart);
				teamType = claz.getDivisionFlag();

				if (teamType != null) {
					NumberCode typeCode = NumberCodeHelper.manager.getNumberCode("DIVISIONFLAG", teamType);
					EDMServiceHelper.updateTypeCodeLink(epmDocument, typeCode, true);
				}
			}

			// 보안등급
			// S2 대내비, S1 대외비,
			NumberCode rank = e3psProject.getRank();
			if (rank != null) {
				if ("S".equals(rank.getName())) {
					securityLevel = "S2";
				} else if ("A".equals(rank.getName())) {
					securityLevel = "S1";
				} else if ("B".equals(rank.getName())) {
					securityLevel = "S1";
				} else if ("C".equals(rank.getName())) {
					securityLevel = "S1";
				} else if ("D".equals(rank.getName())) {
					securityLevel = "S1";
				}
			}
		}

		if (StringUtils.isNotEmpty(securityLevel)) {
			updateIBAValue(epmDocument, EDMHelper.IBA_SECURITY, securityLevel); // "Security"
		}

		String partType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartType);

		if ("D".equals(partType)) {

			updateIBAValue(epmDocument, EDMHelper.IBA_CAD_MANAGE_TYPE, "금형도면"); // "CADManageType"

			// category
			KETPartClassification claz = PartUtil.getPartClassification(wtPart);
			if (claz != null && "M".equals(claz.getPartClassificType())) {
				updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "사출금형SET도면"); // "CADCategory" INJECTION_MOLD_SET_DRAWING
			} else if (claz != null && "S".equals(claz.getPartClassificType())) {
				updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "프레스금형SET도면"); // "CADCategory" PRESS_MOLD_SET_DRAWING
			} else {
				String spMMoldAt = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMMoldAt);
				if ("2".equals(spMMoldAt)) {
					updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "사출금형SET도면");
				} else if ("1".equals(spMMoldAt)) {
					updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "프레스금형SET도면");
				}
			}

			// 부품의 - 개발단계
			String spPartDevLevel = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartDevLevel);
			if ("PC003".equals(spPartDevLevel)) { // 개발단계
				updateIBAValue(epmDocument, EDMHelper.IBA_DEV_STAGE, "개발단계"); // "DevStage" "DEVELOPMENT_STAGE"
				updateIBAValue(epmDocument, EDMHelper.IBA_MANUFACTURING_VERSION, "0"); //
			} else if ("PC004".equals(spPartDevLevel)) { // 양산단계
				updateIBAValue(epmDocument, EDMHelper.IBA_DEV_STAGE, "양산단계"); // "DevStage" "PRODUCTION_STAGE"
				updateIBAValue(epmDocument, EDMHelper.IBA_MANUFACTURING_VERSION, "0"); //
			}

		} else if ("M".equals(partType)) {

			updateIBAValue(epmDocument, EDMHelper.IBA_CAD_MANAGE_TYPE, "금형도면"); // "CADManageType"

			// category
			String dieNo = wtPart.getNumber();
			if (dieNo.indexOf("-") != -1) {
				dieNo = dieNo.substring(0, dieNo.indexOf("-"));
				WTPart tempDiePart = PartBaseHelper.service.getLatestPart(dieNo);
				if (tempDiePart != null) {
					KETPartClassification claz = PartUtil.getPartClassification(tempDiePart);
					if (claz != null && "M".equals(claz.getPartClassificType())) {
						updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "사출금형도면"); // "CADCategory" INJECTION_MOLD_DRAWING
					} else if (claz != null && "S".equals(claz.getPartClassificType())) {
						updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "프레스금형도면"); // "CADCategory" PRESS_MOLD_DRAWING
					} else {
						String spMMoldAt = PartSpecGetter.getPartSpec(tempDiePart, PartSpecEnum.SpMMoldAt);
						if ("2".equals(spMMoldAt)) {
							updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "사출금형도면");
						} else if ("1".equals(spMMoldAt)) {
							updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "프레스금형도면");
						}
					}
				}
			}

			// 부품의 - 개발단계
			String spPartDevLevel = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartDevLevel);
			if ("PC003".equals(spPartDevLevel)) { // 개발단계
				updateIBAValue(epmDocument, EDMHelper.IBA_DEV_STAGE, "개발단계"); // "DevStage" "DEVELOPMENT_STAGE"
				updateIBAValue(epmDocument, EDMHelper.IBA_MANUFACTURING_VERSION, "0"); //
			} else if ("PC004".equals(spPartDevLevel)) { // 양산단계
				updateIBAValue(epmDocument, EDMHelper.IBA_DEV_STAGE, "양산단계"); // "DevStage" "PRODUCTION_STAGE"
				updateIBAValue(epmDocument, EDMHelper.IBA_MANUFACTURING_VERSION, "0"); //
			}

		} else {

			String cadName = epmDocument.getCADName();
			String cadNumber = epmDocument.getNumber();
			updateIBAValue(epmDocument, EDMHelper.IBA_CAD_MANAGE_TYPE, "제품도면"); // "CADManageType"

			// 도면유형 - 제품도면 (PRODUCT_DRAWING) CADCategory
			String cateory = e3ps.common.iba.IBAUtil.getAttrValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY);
			if ("".equals(cateory) || cateory == null) {
				if ((cadNumber.toUpperCase().startsWith("CU_") || cadName.toUpperCase().startsWith("CU_"))) {
					updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "고객제출도면"); // "CADCategory" "PRODUCT_DRAWING"
				} else {
					updateIBAValue(epmDocument, EDMHelper.IBA_CAD_CATEGORY, "제품도면"); // "CADCategory" "PRODUCT_DRAWING"
				}
			}

			// 부품의 - 개발단계
			String spPartDevLevel = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartDevLevel);
			if ("PC003".equals(spPartDevLevel)) { // 개발단계
				updateIBAValue(epmDocument, EDMHelper.IBA_DEV_STAGE, "개발단계"); // "DevStage" "DEVELOPMENT_STAGE"
				updateIBAValue(epmDocument, EDMHelper.IBA_MANUFACTURING_VERSION, "D00"); //
			} else if ("PC004".equals(spPartDevLevel)) { // 양산단계
				updateIBAValue(epmDocument, EDMHelper.IBA_DEV_STAGE, "양산단계"); // "DevStage" "PRODUCTION_STAGE"
				updateIBAValue(epmDocument, EDMHelper.IBA_MANUFACTURING_VERSION, "0"); //
			}
		}

		// epmDocument = (EPMDocument)EDMAttributeUtil.setAttributeValues(epmDocument, map);

	}

	private void updateIBAValue(EPMDocument epmDocument, String ibaDefType, String value) throws Exception {
		System.out.println("updateIBAValue call..");
		if (StringUtils.isEmpty(value))
			return;
		if (epmDocument != null) {
			System.out.println("updateIBAValue cadName : " + epmDocument.getCADName());
		}
		String stringValueOid = getExistStringValue(epmDocument, ibaDefType);
		System.out.println("updateIBAValue stringValueOid : " + stringValueOid);
		if (stringValueOid != null) {

			StringValue sv = (StringValue) CommonUtil.getObject(stringValueOid);
			if (value.equals(sv.getValue())) {

			} else {
				sv.setValue(value);
				PersistenceServerHelper.manager.update(sv);
			}

		} else {

			StringDefinition sdf = getStringDefinition(ibaDefType);
			StringValue sv = StringValue.newStringValue(sdf, (IBAHolder) epmDocument, value);
			PersistenceServerHelper.manager.insert(sv);
		}
	}

	public void callupdateIBAValue(EPMDocument epmDocument, String ibaDefType, String value) {
		try {
			updateIBAValue(epmDocument, ibaDefType, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private StringDefinition getStringDefinition(String attrName) throws WTException {

		QuerySpec select = new QuerySpec();
		int idx = select.addClassList(StringDefinition.class, true);
		select.appendWhere(new SearchCondition(StringDefinition.class, "name", "=", attrName), new int[] { idx });
		QueryResult re = PersistenceHelper.manager.find(select);
		while (re.hasMoreElements()) {
			Object[] obj = (Object[]) re.nextElement();
			StringDefinition sv = (StringDefinition) obj[0];
			return sv;
		}

		return null;
	}

	public String getExistStringValue(EPMDocument epmDocument, String ibaDefType) throws Exception {

		DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
		List aBindList = new ArrayList();

		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT CLASSNAMEA2A2||':'||IDA2A2  OID \n");
		buffer.append(" FROM STRINGVALUE \n");
		buffer.append(" WHERE 1 = 1 \n");
		buffer.append(" AND IDA3A4 = ? \n");
		buffer.append(" AND IDA3A6 IN (SELECT IDA2A2 FROM StringDefinition WHERE NAME = ? ) \n");
		// NAME = 'DevStage' OR NAME = 'CADCategory' OR NAME = ''

		aBindList.add(CommonUtil.getOIDLongValue(epmDocument));
		aBindList.add(ibaDefType);

		List<String> stringValueList = oDaoManager.queryForList(buffer.toString(), aBindList, new RowSetStrategy<String>() {

			@Override
			public String mapRow(ResultSet rs) throws SQLException {

				return rs.getString("OID");
			}
		});

		if (stringValueList.size() == 0) {
			return null;
		} else if (stringValueList.size() == 1) {
			return stringValueList.get(0);
		} else {

			Kogger.debug(getClass(), "IBA Value 2개 이상");

			for (int k = stringValueList.size() - 1; k > 0; k--) {
				StringValue sv = (StringValue) CommonUtil.getObject(stringValueList.get(k));
				PersistenceHelper.manager.delete(sv);
			}

			return stringValueList.get(0);
		}
	}

	// ////////////////////////////////////
	// 프로젝트 보안등급 업데이트
	// ////////////////////////////////////
	public void updateSecurityInfoByProject(String projectNo, String beforeGrade, String afterGrade) throws Exception {

		String securityLevel = null;
		if ("S".equals(beforeGrade) && !"S".equals(afterGrade)) { // 대외비로 변경
			securityLevel = "S1";
		} else if (!"S".equals(beforeGrade) && "S".equals(afterGrade)) { // 대내비로 변경
			securityLevel = "S2";
		}

		// 전제 Project에 연결된 최신 도면을 가져온다.
		// 모든 보안등급 업데이트
		if (securityLevel != null) {
			ProjectMaster pjtMaster = PartUtil.getProjectMasterByProjectNo(projectNo);

			QueryResult qr = getEPMDocProjectLink(pjtMaster);
			/**
			 * 전체 리비전에 대해서 보안등급을 업데이트 한다.
			 */
			Object oo[] = null;
			while (qr.hasMoreElements()) {
				oo = (Object[]) qr.nextElement();
				EPMDocProjectLink link = (EPMDocProjectLink) oo[0];
				EPMDocument epmDoc = link.getPjtDoc();
				EPMDocument epmDocument = (EPMDocument) VersionControlHelper.getLatestIteration(epmDoc, false);
				updateIBAValue(epmDocument, "Security", securityLevel);
			}

		}
	}

	public QueryResult getEPMDocProjectLink(ProjectMaster pjtMaster) throws WTException {

		QuerySpec qs = new QuerySpec();
		int idx_link = qs.appendClassList(EPMDocProjectLink.class, true);

		if (pjtMaster == null) {
			throw new WTException("ProjectMaster가 NULL이라서 프로젝트 정보를 확인할 수 없습니다.");
		}

		if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(EPMDocProjectLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(pjtMaster).getId()),
				new int[] { idx_link });

		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

		return qr;

	}

	public void updateIBAValueIncludeEmptyValue(EPMDocument epmDocument, String ibaDefType, String _value) throws Exception {

		String value = null;
		if (_value == null) {
			value = "";
		} else {
			value = _value;
		}

		String stringValueOid = getExistStringValue(epmDocument, ibaDefType);

		if (stringValueOid != null) {

			StringValue sv = (StringValue) CommonUtil.getObject(stringValueOid);
			if (value.equals(sv.getValue())) {

			} else {
				sv.setValue(value);
				PersistenceServerHelper.manager.update(sv);
			}

		} else {

			StringDefinition sdf = getStringDefinition(ibaDefType);
			StringValue sv = StringValue.newStringValue(sdf, (IBAHolder) epmDocument, value);
			PersistenceServerHelper.manager.insert(sv);
		}
	}

}
