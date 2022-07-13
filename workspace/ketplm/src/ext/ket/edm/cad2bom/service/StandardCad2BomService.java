package ext.ket.edm.cad2bom.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.bom.common.iba.IBAUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.edm.EPMLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import ext.ket.bom.query.KETCad2BomQueryBean;
import ext.ket.edm.cad2bom.service.internal.Cad2BomUtil;
import ext.ket.edm.cad2bom.tree.CompositeIterator;
import ext.ket.edm.cad2bom.tree.HierarchyComponent;
import ext.ket.edm.cad2bom.tree.TreeRootPart;
import ext.ket.edm.cad2bom.tree.dto.Cad2BomDTO;
import ext.ket.edm.cad2bom.tree.util.TreeCadUtil;
import ext.ket.edm.cad2bom.tree.util.TreePartController;
import ext.ket.edm.stamping.util.ModelStrucUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardCad2BomService extends StandardManager implements Cad2BomService {

	private static final long serialVersionUID = 1L;
	private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

	public static StandardCad2BomService newStandardCad2BomService() throws WTException {
		StandardCad2BomService instance = new StandardCad2BomService();
		instance.initialize();
		return instance;
	}

	// 부품 BOM을 구성할 수 있는가?
	// 부품 BOM을 구성할 수 없는 사유는?
	public String checkRootValid(String modelOid) throws Exception {
		String cad2BomFlag = "";

		try {

			KETCad2BomQueryBean cad2BomQueryBean = new KETCad2BomQueryBean();
			EPMDocument sourceEpmDoc = (EPMDocument) CommonUtil.getObject(modelOid);
			String topThreeDNo = sourceEpmDoc.getNumber();
			// 금형쪽인지 아닌지 체크한다.
			boolean isMoldSideCad = Cad2BomUtil.isDieNo(Cad2BomUtil.getDieCodeTypeStr(topThreeDNo));
			WTPart topPart = Cad2BomUtil.getPartBy3DNo(sourceEpmDoc, topThreeDNo, isMoldSideCad);

			HashMap ibaValues = EDMAttributes.getIBAValues(sourceEpmDoc, Locale.KOREAN);

			String cateory = "";
			if (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
				cateory = EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY), Locale.KOREAN);
			}

			if (topPart != null) {

				WTPart prodTopPart = Cad2BomUtil.getProdFertPart(sourceEpmDoc, isMoldSideCad);
				if (prodTopPart != null) {
					// -. 초도인가?
					if (!"0".equals(prodTopPart.getVersionIdentifier().getValue()) || State.INWORK != prodTopPart.getLifeCycleState()) {
						cad2BomFlag = "F↕" + prodTopPart.getNumber(); // ROOT_NOT_FIRST_CAD
						return cad2BomFlag;
					}
					// -. CAD2BOM을 하려는 최상위 도면(3D 모델)과 연결되는 부품이 EO에 포함되어 있어야 한다.
					if (!cad2BomQueryBean.existBOMHead(prodTopPart)) {
						cad2BomFlag = "E↕" + prodTopPart.getNumber(); // ROOT_NOT_INCLUDE_EOHEADER
						return cad2BomFlag;
					}
					// -. 초도가 아니라면 하위 BOM이 붙어 있는가?

					if (!"CUSTOMER_DRAWING".equals(cateory) && cad2BomQueryBean.hasBOMComp(prodTopPart)) {
						cad2BomFlag = "B↕" + prodTopPart.getNumber(); // ROOT_HAS_BOM_ALREADY
						return cad2BomFlag;
					}
				} else {
					// -. 초도인가?
					if (!"0".equals(topPart.getVersionIdentifier().getValue()) || State.INWORK != topPart.getLifeCycleState()) {
						cad2BomFlag = "F↕" + topPart.getNumber(); // ROOT_NOT_FIRST_CAD
						return cad2BomFlag;
					}
					// -. CAD2BOM을 하려는 최상위 도면(3D 모델)과 연결되는 부품이 EO에 포함되어 있어야 한다.
					if (!cad2BomQueryBean.existBOMHead(topPart)) {
						cad2BomFlag = "E↕" + topPart.getNumber(); // ROOT_NOT_INCLUDE_EOHEADER
						return cad2BomFlag;
					}
					// -. 초도가 아니라면 하위 BOM이 붙어 있는가?
					if (!"CUSTOMER_DRAWING".equals(cateory) && cad2BomQueryBean.hasBOMComp(topPart)) {
						cad2BomFlag = "B↕" + topPart.getNumber(); // ROOT_HAS_BOM_ALREADY
						return cad2BomFlag;
					}
				}

			}

			return cad2BomFlag;

		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw e;
		}
	}

	/*
	 * 
	 * CAD2BOM 정리 1. CAD2BOM을 하려는 최상위 도면(3D 모델)과 연결되는 부품이 EO에 포함되어 있어야 한다. 2. CAD2BOM을 하려는 최상위 도면(3D 모델)과 연결되는 부품은 하위에 BOM을 전혀 가지지 않아야 한다. 3. CAD2BOM - 상위가 초도 대상 - 즉 하위는 결재승인된 것
	 * 가능, INWORK-은 ECO 동일이면 그대로 아니면 APPROVED를 붙인다.
	 */
	@Override
	public List<Map<String, Object>> makeCadStructure(String modelOid) throws Exception {

		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

		try {

			KETCad2BomQueryBean cad2BomQueryBean = new KETCad2BomQueryBean();
			EPMDocument sourceEpmDoc = (EPMDocument) CommonUtil.getObject(modelOid);
			String topThreeDNo = sourceEpmDoc.getNumber();
			// // 금형쪽인지 아닌지 체크한다.
			boolean isMoldSideCad = Cad2BomUtil.isDieNo(Cad2BomUtil.getDieCodeTypeStr(topThreeDNo));

			TreeCadUtil util = new TreeCadUtil();
			TreeRootPart treeRootPart = util.makeCadStructure(sourceEpmDoc, isMoldSideCad);

			// tree loop를 따라서 validation check
			if (true) {

				Iterator<HierarchyComponent> iterator = new CompositeIterator(treeRootPart.getIterator());
				while (iterator.hasNext()) {

					HierarchyComponent bomComponent = iterator.next();
					Cad2BomDTO cad2BomDto = (Cad2BomDTO) bomComponent.getTreeDTO();
					try {

						EPMDocument epmDoc = cad2BomDto.getEpmDoc3D();
						String threeDNo = cad2BomDto.getTreeNumber();

						Kogger.debug(getClass(), " | " + threeDNo);

						// Latest 부품을 찾아서 넣기
						WTPart wtPart = Cad2BomUtil.getPartBy3DNo(epmDoc, threeDNo, isMoldSideCad);
						boolean hasPart = wtPart != null;
						boolean isFirstVersion = wtPart != null && "0".equals(wtPart.getVersionIdentifier().getValue());
						boolean isApproved = wtPart != null && State.toState("APPROVED") == wtPart.getLifeCycleState();
						// && "0".equals(wtPart.getVersionIdentifier().getValue())
						// && State.INWORK == wtPart.getLifeCycleState()
						;

						cad2BomDto.setWtPart(wtPart);

						if (wtPart != null)
							Kogger.debug(getClass(), " # " + wtPart.getNumber());
						else
							Kogger.debug(getClass(), " # wtPart Null");

						// 일품다도, 다품일도 Validation 처리
						// -. Relation이 기존에 동일하게 연결이 되어 있는가? 체크 > 동일도면이라도 초도면 가능함.
						// -. Relation이 기존에 도면에 다른 부품이 연결되어 있는가? 체크 (다품일도인 경우) - 미정 일단 불가
						// -. Relation이 기존에 부품에 다른 도면과 연결되어 있는가? 체크 (일품다도인 경우) - 미정 일단 불가
						if (hasPart) { // && isFirstVersion && !isApproved
							Kogger.debug(getClass(), " # wtPart Multi Check");
							setEpmPartInfo(cad2BomDto, wtPart, epmDoc);
						}

						Kogger.debug(getClass(), " # wtPart Can Conig CAD-PART Rel Check");
						// 부품 - 도면 Realtion validation
						bomComponent.checkCanConfigPartCadRel();

						// -. 현재 Parent부모 + this부품으로 BOM 구성이 가능한가?
						Kogger.debug(getClass(), " # wtPart Can Conig PART BOM Check1");

						// BOM 구성 가능/불가 판정
						// Released된 것 하위는 구성 불가 > 자기 아래 노드부터 불가
						// Released되지 않았지만 BOM 구성된 것 > 자기 노드 부터 불가
						if (hasPart && (cad2BomDto.isCanConfigPartCadRel() || cad2BomDto.isExistCadPartRelation()) && cad2BomDto.isCanConfigPartBom()/*
																																					 * 자식 Node에 값을 미리 넣어두므로 필요한 로직 -
																																					 * 지우지 말 것
																																					 */
						) {
							WTPart parentPart = bomComponent.getParent().getTreeDTO().getWtPart();
							boolean hasParentPart = parentPart != null;
							if (hasParentPart) { // 부모 부품이 존재할 경우

								if (isApproved) { // 결재완료상태일 경우

									Kogger.debug(getClass(), "##### BOM 구성 가능: " + threeDNo + ": 결재완료 하위bom 구성불가");
									cad2BomDto.setCanConfigPartBom(true); // BOM 구성 가능 - 모자의 자가 결재완료됨.
									bomComponent.checkCanConfigSubPartBom(); // 하위는 BOM 구성 불가

								} else if (!isApproved && isFirstVersion) { // In-Work 최초버전

									if (cad2BomQueryBean.isExistBOMComp(parentPart, wtPart)) { // 신규BOM 구성했다면

										Kogger.debug(getClass(), "##### BOM 구성 불가: " + threeDNo + ": Inwork 최초버전 신규BOM 이미 구성");
										cad2BomDto.setCanConfigPartBom(false); // BOM 구성 불가 - 신규BOM 구성
										bomComponent.checkCanConfigSubPartBom(); // 하위는 BOM 구성 불가

									} else {

										Kogger.debug(getClass(), "##### BOM 구성 가능: " + threeDNo + ": Inwork 최초버전");
										cad2BomDto.setCanConfigPartBom(true); // BOM 구성 가능 - 신규BOM 구성

									}

								} else if (!isApproved && !isFirstVersion) {// In-Work 나중버전

									// ///////////////////////////////////////
									// LOGIC 1
									// 자신의 ECO라면 붙이고
									// 자신의 ECO가 아니라면 Released를 붙인다.
									// 일단 안하는 걸로
									// ///////////////////////////////////////
									// String PartEcoNo = "";

									// ///////////////////////////////////////
									// LOGIC 2 선택됨
									// 무조건 Released를 붙인다.
									// ///////////////////////////////////////
									WTPart approevedPart = Cad2BomUtil.getBeforeApprovedPart(wtPart);
									if (approevedPart != null) {
										Kogger.debug(getClass(), "##### BOM 구성 가능: " + threeDNo + ": 개정부품 Inwork Relesad로 붙임 하위불가");
										cad2BomDto.setCanConfigPartBom(true); // BOM 구성 가능 - 모자의 자가 결재완료후 InWork 상태임
										bomComponent.checkCanConfigSubPartBom(); // 하위는 BOM 구성 불가
										cad2BomDto.setWtPart(approevedPart);
									} else {
										Kogger.debug(getClass(), "##### BOM 구성 불가: " + threeDNo + ": 0아닌 Inwork 버전 하위 리비전 없음");
										cad2BomDto.setCanConfigPartBom(false); // BOM 구성 가능 - 모자의 자가 결재완료후 InWork 상태임
										bomComponent.checkCanConfigSubPartBom(); // 하위는 BOM 구성 불가
									}
								}

							} else {

								// BOM 구성 불가 - 부모 부품 없음
								Kogger.debug(getClass(), "##### BOM 구성 불가: " + threeDNo + ": 부모 부품 없음");
								cad2BomDto.setCanConfigPartBom(false);

							}

						} else {

							if (!hasPart) {

								// BOM 구성 불가 - 부품 없음
								Kogger.debug(getClass(), "##### BOM 구성 불가: " + threeDNo + ": 부품No 없어 하위BOM 구성 불가");
								cad2BomDto.setCanConfigPartBom(false);
								bomComponent.checkCanConfigSubPartBom(); // 하위는 BOM 구성 불가

							} else if (!cad2BomDto.isCanConfigPartCadRel()) {

								// BOM 구성 불가 - 부품 없음
								Kogger.debug(getClass(), "##### BOM 구성 불가: " + threeDNo + ": CanConfigPartCadRel 하위BOM 구성 불가");
								cad2BomDto.setCanConfigPartBom(false);
								bomComponent.checkCanConfigSubPartBom(); // 하위는 BOM 구성 불가

							} else {

								// BOM 구성 불가 - / 상위 부품이 Released상태이거나 / 신규 BOM 구성했음
								Kogger.debug(getClass(), "##### BOM 구성 불가: " + threeDNo + ": 상위부품 R|신규 BOM존재");
								cad2BomDto.setCanConfigPartBom(false);
							}

						}

					} catch (Exception e) {
						Kogger.error(getClass(), e);
						throw e;
					}
				}
			}

			// tree loop를 따라서
			// BOM 구성 가능 체크
			// Kogger.debug(getClass(), " # wtPart Can Conig PART BOM Check2");
			// if (true) {
			// Iterator<HierarchyComponent> iterator = new CompositeIterator(treeRootPart.getIterator());
			// while (iterator.hasNext()) {
			//
			// HierarchyComponent bomComponent = iterator.next();
			// try {
			// recheck?!
			// break;
			//
			// } catch (Exception e) {
			// Kogger.error(getClass(), e);
			// throw e;
			// }
			// }
			// }

			// 결과 조회
			TreePartController treePartController = new TreePartController(treeRootPart);
			treePartController.printAllParts();
			// treePartController.calculateSubBomCount();

			// 결과 입력
			if (true) {
				ModelStrucUtil modelStrucUtil = new ModelStrucUtil();
				Iterator<HierarchyComponent> iterator = new CompositeIterator(treeRootPart.getIterator());
				while (iterator.hasNext()) {

					HierarchyComponent bomComponent = iterator.next();
					Cad2BomDTO cad2BomDto = (Cad2BomDTO) bomComponent.getTreeDTO();
					Cad2BomDTO parentCad2BomDto = (Cad2BomDTO) bomComponent.getParent().getTreeDTO();

					try {

						Map<String, Object> data = new Hashtable<String, Object>();
						EPMDocument oEpmDoc = cad2BomDto.getEpmDoc3D();
						WTPart oPart = cad2BomDto.getWtPart();
						WTPart parentPart = bomComponent.getParent().getTreeDTO().getWtPart();

						// 도면 oid
						String model3DOid = oEpmDoc == null ? "" : CommonUtil.getOIDString(oEpmDoc);
						data.put("modelOid", model3DOid);
						// level
						int depth = cad2BomDto.getDepth();
						data.put("lvl", String.valueOf(depth));
						// leaf 여부
						data.put("leaf", cad2BomDto.isLeaf());
						// icon
						if (cad2BomDto.isLeaf()) {
							data.put("iconCls", "task");
						} else {
							data.put("iconCls", "task-folder");
						}
						// image Url
						data.put("cad", model3DOid);
						// 부모 도면번호
						if (depth == 1 || parentCad2BomDto == null || parentCad2BomDto.getEpmDoc3D() == null) {
							data.put("parentModelNo", "");
						} else {
							data.put("parentModelNo", parentCad2BomDto.getEpmDoc3D().getNumber());
						}
						// 도면번호
						data.put("modelNo", oEpmDoc.getNumber());
						// Revision
						data.put("modelRev", oEpmDoc.getVersionIdentifier().getValue());
						data.put("modelRevWT", StringUtil.checkNull(IBAUtil.getAttrValue(oEpmDoc, "ManufacturingVersion")));
						// 도면명
						data.put("modelName", oEpmDoc.getName());
						// 수량
						data.put("qty", String.valueOf(((Long) cad2BomDto.getTreeAmount()).intValue()));

						// 부품연관 Relation 가능
						data.put("partCadRelation", cad2BomDto.isCanConfigPartCadRel() ? "NEW" : (oPart == null ? "" : "OLD"));
						// 부품BOM Relation 가능여부
						data.put("partBomRelation", cad2BomDto.isCanConfigPartBom() ? "NEW" : ((parentPart == null || oPart == null) ? "" : "OLD"));

						// 여기에서 BOM Relation 가능여부 확인하여 표시하는 부분
						if (oPart == null) {
							data.put("modelNo", "<font color='red'>" + oEpmDoc.getNumber() + "</font>");
						} else {
							data.put("modelNo", oEpmDoc.getNumber());
						}

						// 부품 Oid
						String partOid = oPart == null ? "" : CommonUtil.getOIDString(oPart);
						data.put("partOid", partOid);
						// 부모 부품번호
						if (parentCad2BomDto == null || parentCad2BomDto.getWtPart() == null) {
							data.put("parentPartNo", "");
						} else {
							data.put("parentPartNo", parentCad2BomDto.getWtPart().getNumber());
						}
						// 부품번호
						String partNo = oPart == null ? "" : oPart.getNumber();
						data.put("partNo", partNo);
						// Revision
						data.put("partRev", oPart == null ? "" : oPart.getVersionIdentifier().getValue());
						data.put("partRevWT", oPart == null ? "" : StringUtil.checkNull(PartSpecGetter.getPartSpec(oPart, PartSpecEnum.SpPartRevision)));
						// 부품명
						data.put("partName", oPart == null ? "" : oPart.getName());

						// CREO 2D DRW Validation에 3D의 No를 넣어준다.
						// 연결 안된 3D의 No를 넣어준다.
						// 조건 : 부품존재, 부품은 반제품만, CREO, 3D 초도(0리비전)
						boolean is2dCheckProblem = false;

						HashMap ibaValues = EDMAttributes.getIBAValues(oEpmDoc, Locale.KOREAN);

						String cateory = "";
						if (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
							cateory = EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY), Locale.KOREAN);
						}

						if (!"CUSTOMER_DRAWING".equals(cateory) && oPart != null && "0".equals(oEpmDoc.getVersionIdentifier().getValue())
								&& "PROE".equals(oEpmDoc.getAuthoringApplication().toString())) {
							String partType = PartSpecGetter.getPartSpec(oPart, PartSpecEnum.SpPartType);
							if ("H".equals(partType)) {

								// 3D에 연결된 2D 찾아서 없으면 validation에 걸림.
								EPMDocumentMaster master = (EPMDocumentMaster) oEpmDoc.getMaster();
								if (!modelStrucUtil.has2DBy3D(CommonUtil.getOIDString(master), oEpmDoc.getNumber())) {
									is2dCheckProblem = true;
								}
							}
						}

						if (is2dCheckProblem) {
							data.put("check2DInfo", oEpmDoc.getNumber());
						} else {
							data.put("check2DInfo", "");
						}

						retList.add(data);

					} catch (Exception e) {
						Kogger.error(getClass(), e);
						throw e;
					}
				}
			}

			return retList;

		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw e;
		}
	}

	// 도면과 부품의 연관 체크
	private void setEpmPartInfo(Cad2BomDTO cad2BomDto, WTPart wtPart, EPMDocument epmDoc) throws Exception {

		// A: EPMDocumentMaster.class L : EPMLink B: WTPartMaster.class
		if (wtPart == null)
			return;

		List<WTPartMaster> partList = query.queryForListBByRoleA(EPMDocumentMaster.class, EPMLink.class, WTPartMaster.class, (EPMDocumentMaster) epmDoc.getMaster());

		String partNo = wtPart.getNumber();
		Map checkMap = new HashMap();
		for (WTPartMaster partMast : partList) {

			if (partMast == null)
				continue;

			if (checkMap.containsKey(partMast.getNumber())) {
				continue;
			} else {
				checkMap.put(partMast.getNumber(), null);
			}

			if (partNo.equals(partMast.getNumber())) {
				cad2BomDto.setExistCadPartRelation(true);
				return;

			} else {

				cad2BomDto.setExistAnotherPartRelation(true);
				cad2BomDto.addAnotherWTPartMaster(partMast);
			}
		}
	}

}
