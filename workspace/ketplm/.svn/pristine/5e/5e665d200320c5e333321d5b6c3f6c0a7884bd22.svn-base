package e3ps.edm.listener;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.clients.iba.container.NewValueCreator;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMAuthoringAppType;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.workspaces.EPMWorkspaceManagerEvent;
import wt.events.KeyedEvent;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceManagerEvent;
import wt.fc.QueryResult;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAHolder;
import wt.iba.value.litevalue.AbstractValueView;
import wt.iba.value.litevalue.StringValueDefaultView;
import wt.iba.value.service.IBAValueHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleServiceEvent;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.services.ServiceEventListenerAdapter;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import wt.vc.VersionControlServiceEvent;
import wt.vc.config.LatestConfigSpec;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.WorkInProgressServiceEvent;
import e3ps.bom.common.iba.IBAUtil;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.VersionUtil;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.edm.CADAppType;
import e3ps.edm.DrawingToModelReferenceLink;
import e3ps.edm.EDMDocumentHelper;
import e3ps.edm.EPMLink;
import e3ps.edm.ModelReferenceLink;
import e3ps.edm.PartToEPMLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.beans.EDMServiceHelper;
import e3ps.edm.util.EDMAttributeUtil;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import e3ps.edm.util.EDMProperties;
import e3ps.edm.util.VersionHelper;
import e3ps.project.ProjectMaster;
import ext.ket.edm.approval.KetEpmApprovalHelper;
import ext.ket.edm.cad2bom.service.internal.EpmInfoUpadator;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.stamping.jms.StampingQueueSender;
import ext.ket.edm.stamping.util.ModelStrucUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.VieawbleGenerator;
import ext.ket.part.base.service.internal.associate.PartEpmRelation;
import ext.ket.part.code.NumberCodeUtil;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class EDMEventListener extends ServiceEventListenerAdapter {
	public EDMEventListener(String s) {
		super(s);
	}

	public void notifyVetoableEvent(Object obj) throws WTException {
		if (!(obj instanceof KeyedEvent)) {
			return;
		}

		KeyedEvent keyedevent = (KeyedEvent) obj;
		String eventType = keyedevent.getEventType();
		Object eventTarget = keyedevent.getEventTarget();

		// Kogger.debug(getClass(), "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		// Kogger.debug(getClass(), "%%%%%%%% EventType " + eventType + " %%%%%%%%%%");
		// Kogger.debug(getClass(), "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

		if (!(eventType.equals(PersistenceManagerEvent.POST_STORE)) && !(eventType.equals(PersistenceManagerEvent.POST_MODIFY))
				&& !(eventType.equals(PersistenceManagerEvent.POST_DELETE)) && !(eventType.equals(PersistenceManagerEvent.PRE_DELETE))
				&& !(eventType.equals(VersionControlServiceEvent.NEW_VERSION)) && !(eventType.equals(LifeCycleServiceEvent.STATE_CHANGE))
				&& !(eventType.equals(WorkInProgressServiceEvent.POST_CHECKIN)) && !(eventType.equals(WorkInProgressServiceEvent.PRE_CHECKIN))
				&& !(eventType.equals(VersionControlServiceEvent.NEW_ITERATION)) && !(eventType.equals(PersistenceManagerEvent.PRE_STORE))
				&& !(eventType.equals(PersistenceManagerEvent.PRE_MODIFY)) && !(eventType.equals(WorkInProgressServiceEvent.POST_CHECKOUT))
				&& !(eventType.equals(EPMWorkspaceManagerEvent.CHECKOUT_TO_WORKSPACE))) {

			return;
		}

		if (!(eventTarget instanceof EPMDocument) && !(eventTarget instanceof WTPart) && !(eventTarget instanceof KETDrawingDistRequest)
				&& !(eventTarget instanceof KETProdChangeOrder) && !(eventTarget instanceof KETMoldChangeOrder)) {
			return;
		}

		Kogger.debug(getClass(), "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		Kogger.debug(getClass(), "%%%%%%%% EventType " + eventType + " %%%%%%%%%%");
		Kogger.debug(getClass(), "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

		if (eventType.equals(PersistenceManagerEvent.POST_STORE) || eventType.equals(PersistenceManagerEvent.POST_MODIFY)) {
			if (eventTarget instanceof EPMDocument) {
				System.out.println("POST_STORE done..................!!!");
				EDMDocumentHelper.service.addEPMDocumentNumberSuffix((EPMDocument) eventTarget);

				initAttr((EPMDocument) eventTarget);
				setManufacturingVersion((EPMDocument) eventTarget, false);
				updateCu_IBAupdate((EPMDocument) eventTarget);
				update2DPartProps((EPMDocument) eventTarget);
				updateCategoryForLibrary((EPMDocument) eventTarget);
				
				//자동 퍼블리싱 작업 추가함 2017.07.07 by 황정태 제품도면,autocad,proe에 한해.. 나중에 필요하면 전부 퍼블리싱해도 되지만 일단은.
				try{
				    
				    String authoringApplication = ((EPMDocument) eventTarget).getAuthoringApplication().toString();
				    if(EDMHelper.isProductDrawing((EPMDocument) eventTarget) && ("PROE".equals(authoringApplication) || "ACAD".equals(authoringApplication))){
					Kogger.debug(getClass(), "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
					Kogger.debug(getClass(), "%%%%%%%% doPublish Start  %%%%%%%%%%");
					Kogger.debug(getClass(), "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
					
					VieawbleGenerator gen = new VieawbleGenerator();
					gen.newGenerator((EPMDocument) eventTarget);
					    
					Kogger.debug(getClass(), "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
					Kogger.debug(getClass(), "%%%%%%%% doPublish End  %%%%%%%%%%");
					Kogger.debug(getClass(), "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				    }
				    
				}catch(Exception e){
				    Kogger.error(getClass(), e);
				}
			}
		} else if (eventType.equals(PersistenceManagerEvent.PRE_DELETE)) {
			if (eventTarget instanceof EPMDocument) {
				deleteIteration((EPMDocument) eventTarget);
			} else if (eventTarget instanceof WTPart) {
				deleteIteration((WTPart) eventTarget);
			}
		} else if (eventType.equals(PersistenceManagerEvent.POST_DELETE)) {
			if (eventTarget instanceof EPMDocument) {
				EDMServiceHelper.deleteEDMUserData((EPMDocument) eventTarget);
			}
		} else if (eventType.equals(wt.vc.VersionControlServiceEvent.NEW_VERSION)) {

			if (eventTarget instanceof EPMDocument) {

				// EDMDocumentHelper.service.addEPMDocumentNumberSuffix((EPMDocument) eventTarget);

				// 2차 고도화 YJLEE(TKLEE) 수정함.
				// 도면 Revision은 Inwork인 부품과만 붙는다.
				Kogger.debug(getClass(), "##################################################");
				Kogger.debug(getClass(), "################## New Revision  ################");
				Kogger.debug(getClass(), "##################################################");
				newVersion((EPMDocument) eventTarget, true);

			} else if (eventTarget instanceof WTPart) {

				// 2차 고도화 YJLEE(TKLEE) 수정함.
				// 도면 Revision은 Inwork인 부품과만 붙는다.
				Kogger.debug(getClass(), "##################################################");
				Kogger.debug(getClass(), "################## New Revision  ################");
				Kogger.debug(getClass(), "##################################################");
				newVersion((WTPart) eventTarget, false);
			}
		} else if (eventType.equals(LifeCycleServiceEvent.STATE_CHANGE)) {
			if (eventTarget instanceof EPMDocument) {
				syncState((EPMDocument) eventTarget);
				// 2차 고도화 YJLEE(TKLEE) 수정함. - SCH, PCB, AUTOCAD 별개로 등록, 수정, 개정함.
				// syncStateECAD((EPMDocument) eventTarget);

				// 2차 고도화 YJLEE(TKLEE) 추가함.
				try {

					EPMDocument epmDoc = (EPMDocument) eventTarget;
					Kogger.debug(getClass(), "##################################################");
					Kogger.debug(getClass(), "### EPMDocument :" + epmDoc.getNumber() + "<=>" + epmDoc.getVersionIdentifier().getValue());
					Kogger.debug(getClass(), "##################################################");
					State state = epmDoc.getLifeCycleState();
					Kogger.debug(getClass(), "### State" + state.toString());
					if (state == State.toState("APPROVED")) {
						Kogger.debug(getClass(), "### State : APPROVED ");
						Kogger.debug(getClass(), "### OID : " + CommonUtil.getOIDString(epmDoc));
						KetEpmApprovalHelper.service.updateEpmApprovalInfoWhenEcoApproved(epmDoc);
					}

				} catch (Exception e) {
					Kogger.error(getClass(), e);
				}

			} else if (eventTarget instanceof KETDrawingDistRequest) {
				// 2차 고도화 YJLEE(TKLEE) 추가함.
				Kogger.debug(getClass(), "##################################################");
				Kogger.debug(getClass(), "###### KETDrawingDistRequest STATE CHANGE  #######");
				Kogger.debug(getClass(), "##################################################");
				try {
					KETDrawingDistRequest drawingReqDoc = (KETDrawingDistRequest) eventTarget;
					Kogger.debug(getClass(), "##################################################");
					Kogger.debug(getClass(), "### 배포요청서 - KETDrawingDistRequest " + drawingReqDoc.getNumber());
					Kogger.debug(getClass(), "##################################################");
					State state = drawingReqDoc.getLifeCycleState();
					Kogger.debug(getClass(), "### State" + state.toString());
					if (state == State.toState("APPROVED")) {
						Kogger.debug(getClass(), "### State : APPROVED ");
						Kogger.debug(getClass(), "### OID : " + CommonUtil.getOIDString(drawingReqDoc));
						StampingQueueSender sender = StampingQueueSender.getInstance();
						sender.sendMessage(drawingReqDoc.getNumber(), CommonUtil.getOIDString(drawingReqDoc));
						Kogger.debug(getClass(), "### Queue : Sended");
					}
				} catch (Exception e) {
					Kogger.error(getClass(), e);
				}
			} else if (eventTarget instanceof KETProdChangeOrder || eventTarget instanceof KETMoldChangeOrder) {
				// 2차 고도화 YJLEE(TKLEE) 추가함.
				Kogger.info(getClass(), "##################################################");
				Kogger.info(getClass(), "######## ChangeOrder2 STATE CHANGE ###############");
				Kogger.info(getClass(), "##################################################");
				try {

					if (eventTarget instanceof KETProdChangeOrder) {
						KETProdChangeOrder changeOrder2 = (KETProdChangeOrder) eventTarget;
						Kogger.info(getClass(), "##################################################");
						Kogger.info(getClass(), "### KETProdChangeOrder :" + changeOrder2.getEcoId());
						Kogger.info(getClass(), "##################################################");
						State state = changeOrder2.getLifeCycleState();
						Kogger.info(getClass(), "### State" + state.toString());
						if (state == State.toState("APPROVED")) {
							Kogger.info(getClass(), "### State : APPROVED > KetEpmApprovalHelper.service.updateEpmApprovalInfoWhenEcoApproved ");
							Kogger.info(getClass(), "### OID : " + CommonUtil.getOIDString(changeOrder2));
							KetEpmApprovalHelper.service.updateEpmApprovalInfoWhenEcoApproved(changeOrder2);
						}
					} else if (eventTarget instanceof KETMoldChangeOrder) {
						KETMoldChangeOrder changeOrder2 = (KETMoldChangeOrder) eventTarget;
						Kogger.info(getClass(), "##################################################");
						Kogger.info(getClass(), "### KETMoldChangeOrder :" + changeOrder2.getEcoId());
						Kogger.info(getClass(), "##################################################");
						State state = changeOrder2.getLifeCycleState();
						Kogger.info(getClass(), "### State" + state.toString());
						if (state == State.toState("APPROVED")) {
							Kogger.info(getClass(), "### State : APPROVED > KetEpmApprovalHelper.service.updateEpmApprovalInfoWhenEcoApproved ");
							Kogger.info(getClass(), "### OID : " + CommonUtil.getOIDString(changeOrder2));
							KetEpmApprovalHelper.service.updateEpmApprovalInfoWhenEcoApproved(changeOrder2);
						}
					}

				} catch (Exception e) {
					Kogger.error(getClass(), e);
				}
			}
		} else if (eventType.equals(WorkInProgressServiceEvent.POST_CHECKIN)) {
			if (eventTarget instanceof EPMDocument) {
				eventTarget = (EPMDocument) VersionControlHelper.service.getLatestIteration((EPMDocument) eventTarget, false);
				EDMServiceHelper.setEDMUserData((EPMDocument) eventTarget, false);
				updateIBAValue((EPMDocument) eventTarget);
			}
		} else if (eventType.equals(WorkInProgressServiceEvent.POST_CHECKOUT)) {
			if (eventTarget instanceof EPMDocument) {
				updateIBAValue((EPMDocument) eventTarget);
			}
		}
	}

	public void updateIBAValue(EPMDocument epm) {
		try {
			if (!EDMHelper.isProductDrawing(epm)) {

				Hashtable hh = e3ps.common.iba.IBAUtil.getIBAAttributes("EPMAttributes");

				Iterator it = hh.keySet().iterator();
				while (it.hasNext()) {
					String varName = (String) it.next();
					if ("CADAppType".equals(varName)) {
						continue;
					}

					String value = IBAUtil.getAttrValue(epm, varName);

					EPMDocument preEpm = null;
					preEpm = (EPMDocument) VersionControlHelper.service.predecessorOf(epm);

					if (value.length() == 0) {

						if (preEpm != null) {
							value = IBAUtil.getAttrValue(preEpm, varName);
						}
					}

					e3ps.common.iba.IBAUtil.changeIBAValue(epm, varName, value);
				}

			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}

	// public void syncStateECAD(EPMDocument epm) throws WTException {
	//
	// HashMap ibaValues = EDMAttributes.getIBAValues(epm, Locale.KOREAN);
	// String category = "";
	//
	// if (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
	// category = EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY), Locale.KOREAN);
	// }
	//
	// if (!"ECAD_DRAWING".equals(category)) {
	// return;
	// }
	//
	// if ("ECAD_DRAWING".equals(category)) {
	// EDMProperties edmProperties = EDMProperties.getInstance();
	// ArrayList relateds = null;
	// WTPart part = null;
	// try {
	// relateds = EDMHelper.getReferencedByParts(epm, edmProperties.getReferenceType(category), EDMHelper.REQUIRED_STANDARD);
	// if ((relateds != null) && (relateds.size() > 0)) {
	// part = (WTPart) ((Object[]) relateds.get(0))[1];
	// }
	// } catch (Exception e) {
	// Kogger.error(getClass(), e);
	// }
	//
	// EPMDocument ecadPCB = null;
	// EPMDocument ecadSCH = null;
	// EPMDocument ecadDWG = null;
	//
	// if ((epm != null) && (part != null)) {
	// ArrayList ecads = EDMHelper.getAssociatedDocsByECAD(epm, part);
	// for (int a = 0; a < ecads.size(); a++) {
	// EPMDocument ecad = (EPMDocument) ecads.get(a);
	//
	// if ("PADS".equals(ecad.getAuthoringApplication().toString())) {
	// ecadPCB = ecad;
	// LifeCycleHelper.service.setLifeCycleState(ecadPCB, epm.getLifeCycleState(), false);
	// } else if ("PADS_SCH".equals(ecad.getAuthoringApplication().toString())) {
	// ecadSCH = ecad;
	// LifeCycleHelper.service.setLifeCycleState(ecadSCH, epm.getLifeCycleState(), false);
	// } else if ("ACAD".equals(ecad.getAuthoringApplication().toString())) {
	// ecadDWG = ecad;
	// LifeCycleHelper.service.setLifeCycleState(ecadDWG, epm.getLifeCycleState(), false);
	// }// end if
	// }// end for
	//
	// }
	// }
	// } // end syncStateECAD

	public void syncState(EPMDocument epm) throws WTException {
		if (EDMHelper.isRefedModel(epm)) {
			return;
		}

		String category = EDMHelper.getCategory(epm);
		if (category.length() == 0) {
			return;
		}

		EDMProperties edmProperties = EDMProperties.getInstance();

		String referenceType = edmProperties.getReferenceType(category);
		String state = epm.getLifeCycleState().toString();

		ArrayList ds = new ArrayList();
		ds.add(epm);

		if (edmProperties.isStateSyncByPart(category)) {
			ArrayList refedParts = null;
			try {
				refedParts = EDMHelper.getReferencedByParts(epm, referenceType, -1);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
				throw new WTException(e);
			}

			if ((refedParts != null) && (refedParts.size() > 0)) {
				EPMLink el = null;
				WTPart part = null;
				EPMDocument model = null;
				Object[] oo = null;

				for (int i = 0; i < refedParts.size(); i++) {
					oo = (Object[]) refedParts.get(i);
					el = (EPMLink) oo[0];
					part = (WTPart) oo[1];

					model = EDMHelper.getAssociatedModel(epm, part, referenceType, el.getRequired());

					if (part != null) {
						Kogger.debug(getClass(), "epm state == " + state);
						Kogger.debug(getClass(), "part number == " + part.getNumber());
						Kogger.debug(getClass(), "part == " + part.getLifeCycleState().getDisplay());
						LifeCycleHelper.service.setLifeCycleState(part, State.toState(state), false);
					}

					if (model != null) {
						Kogger.debug(getClass(), "epm state == " + state);
						Kogger.debug(getClass(), "model number = " + model.getNumber());
						Kogger.debug(getClass(), "model == " + model.getLifeCycleState().getDisplay());
						LifeCycleHelper.service.setLifeCycleState(model, State.toState(state), false);
					}

					if ((model != null) && !ds.contains(model)) {
						ds.add(model);
					}
				}
			}
		}

		if (edmProperties.isStateSyncByModel(category)) {
			ArrayList modelArry = EDMHelper.getReferencedByModels(epm, EDMHelper.REQUIRED_REFERENCE_MODEL);

			if ((modelArry != null) && (modelArry.size() > 0)) {
				EPMDocument model = null;
				Object[] oo = null;

				for (int i = 0; i < modelArry.size(); i++) {
					oo = (Object[]) modelArry.get(i);
					model = (EPMDocument) oo[1];

					if (!(state.equals(model.getLifeCycleState().toString())) && !(edmProperties.isReleasedState(model.getLifeCycleState().toString()))) {
						LifeCycleHelper.service.setLifeCycleState(model, State.toState(state), false);
					}

					if (!ds.contains(model)) {
						ds.add(model);
					}
				}
			}
		}

		for (int i = 0; i < ds.size(); i++) {
			drawingStateChange((EPMDocument) ds.get(i), state);
		}

		return;
	}

	public void drawingStateChange(EPMDocument epm, String state) throws WTException {
		if (epm == null) {
			return;
		}

		EDMProperties edmProperties = EDMProperties.getInstance();
		EPMDocument drawing = null;
		Vector refedDrawings = EDMHelper.getRelatedDrawings(epm, new LatestConfigSpec());

		for (int k = 0; k < refedDrawings.size(); k++) {
			drawing = (EPMDocument) refedDrawings.get(k);

			if (!(state.equals(drawing.getLifeCycleState().toString())) && !(edmProperties.isReleasedState(drawing.getLifeCycleState().toString()))) {
				LifeCycleHelper.service.setLifeCycleState(drawing, State.toState(state), false);
			}
		}

		return;
	}

	public void deleteIteration(WTPart part) throws WTException {

		// tklee test 중...
		// if (VersionControlHelper.service.isFirstIteration(part)) {
		// deleteLatestLink(part);
		// } else {
		// return;
		// }

		if (!VersionControlHelper.hasPredecessor(part)) {
			return;
		}

		WTPart prePart = (WTPart) VersionControlHelper.service.predecessorOf(part);
		EDMProperties edmProperties = EDMProperties.getInstance();
		ArrayList refedParts = null;

		try {
			refedParts = EDMHelper.getRefDocsHistory(prePart, null, -1, true);
		} catch (Exception e) {
			throw new WTException(e);
		}

		if ((refedParts != null) && (refedParts.size() > 0)) {
			PartToEPMLink el = null;
			EPMDocument epm = null;
			EPMDocument model = null;
			Object[] oo = null;

			for (int i = 0; i < refedParts.size(); i++) {
				oo = (Object[]) refedParts.get(i);
				el = (PartToEPMLink) oo[0];
				epm = (EPMDocument) oo[1];
				model = null;

				if (el.getReferenceType().equals(edmProperties.getReferenceTypeForModel(null))) {
					continue;
				}

				// HashMap ibaValues = EDMAttributes.getAttributeValues(epm, Locale.KOREAN);
				// String category = "";
				// if(ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
				// category =
				// EDMEnumeratedTypeUtil.getCADCategory(EDMAttributes.getLocalizedIBAValueDisplayString((AbstractValueView)ibaValues.get(EDMHelper.IBA_CAD_CATEGORY),Locale.KOREAN),Locale.KOREAN);
				// }

				// String category = EDMHelper.getCategory(epm);
				// if(edmProperties.isRefModel(category)) {

				ArrayList models = EDMHelper.getRefedModelHistory(epm, prePart, el.getReferenceType(), el.getRequired(), true);

				if ((models != null) && (models.size() > 0)) {
					DrawingToModelReferenceLink dml = null;
					EPMDocument e = null;
					Object[] moo = null;

					for (int k = 0; k < models.size(); k++) {
						moo = (Object[]) models.get(k);
						dml = (DrawingToModelReferenceLink) moo[0];
						e = (EPMDocument) moo[1];
						model = e;

						if (VersionHelper.isLatestRevision(model)) {
							PersistenceHelper.manager.delete(dml);
						}
					}
				}
				// }

				// 도면/부품/모델 링크 생성 ...
				try {
					EDMServiceHelper.setEPMReference(prePart, epm, model, el.getReferenceType(), el.getRequired());
				} catch (WTPropertyVetoException e) {
					Kogger.error(getClass(), e);
					throw new WTException(e);
				}

				if (VersionHelper.isLatestRevision(epm)) {
					PersistenceHelper.manager.delete(el);
				}
			}
		}

		try {
			refedParts = EDMHelper.getRefDocsHistory(prePart, edmProperties.getReferenceTypeForModel(null), -1, true);

			if ((refedParts != null) && (refedParts.size() > 0)) {
				PartToEPMLink el = null;
				EPMDocument epm = null;
				Object[] oo = null;

				for (int i = 0; i < refedParts.size(); i++) {
					oo = (Object[]) refedParts.get(i);
					el = (PartToEPMLink) oo[0];
					epm = (EPMDocument) oo[1];

					if (VersionHelper.isLatestRevision(epm)) {
						PersistenceHelper.manager.delete(el);
					}
				}
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		}
	}

	public void deleteIteration(EPMDocument epm) throws WTException {

		if (EDMHelper.getCategory(epm).length() == 0) {

			return;
		}

		// tklee test 중...
		// // 모든 링크 정보는 마스터-마스터이므로 최초 이터레이션이 아닌경우는 링크 삭제가 필요없음
		// if (VersionControlHelper.service.isFirstIteration(epm)) {
		//
		// // 설계변경 개정 정보 초기화.
		// // deleteRevisionByECO(epm);
		//
		// // 도면 관련 링크 삭제
		// deleteLatestLink(epm);
		// } else {
		//
		// return;
		// }

		// 선택된 도면 이터레이션이 선행 이터레이션 정보를 가지고 있는지 여부를 체크함
		// 현재 선택된 도면은 최신 버전의 최초 이터레이션이므로, 결과적으로 이전 버전의 최종 이터레이션 정보를 의미함
		if (!VersionControlHelper.hasPredecessor(epm)) {

			return;
		}

		// 이전 버전의 최종 이터레이션에 해당하는 EPMDocument Object
		EPMDocument preEPM = (EPMDocument) VersionControlHelper.service.predecessorOf(epm);
		EDMProperties edmProperties = EDMProperties.getInstance();

		String referenceType = edmProperties.getReferenceType(EDMHelper.getCategory(preEPM));
		ArrayList refedParts = null;

		try {

			// 이전 버전 도면이 가지고 있던 링크정보를 가져오기...
			refedParts = EDMHelper.getRefedByPartHistory(preEPM, referenceType, -1, true);
			// refedParts = EDMHelper.getReferencedByParts(preEPM,referenceType,-1);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		}

		if ((refedParts != null) && (refedParts.size() > 0)) {

			PartToEPMLink el = null;
			WTPart part = null;
			EPMDocument model = null;
			Object[] oo = null;

			for (int i = 0; i < refedParts.size(); i++) {

				oo = (Object[]) refedParts.get(i);
				el = (PartToEPMLink) oo[0];
				part = (WTPart) oo[1];
				model = null;

				// 부품과 모델 링크 history 삭제...
				ArrayList models = null;

				try {
					models = EDMHelper.getRefDocsHistory(part, edmProperties.getReferenceTypeForModel(null), el.getRequired(), true);
				} catch (WTPropertyVetoException e1) {
					throw new WTException(e1);
				}

				if ((models != null) && (models.size() > 0)) {

					Object[] moo = (Object[]) models.get(0);
					model = (EPMDocument) moo[1];

					if (VersionHelper.isLatestRevision(model)) {

						PersistenceHelper.manager.delete((PartToEPMLink) moo[0]);
					}
				}

				// 도면과 모델 링크 history 삭제...
				if (model != null) {

					QueryResult r = EDMHelper.getRefedModelHistory(preEPM, model, el.getRequired());

					while (r.hasMoreElements()) {

						Object[] ob = (Object[]) r.nextElement();
						DrawingToModelReferenceLink dml = (DrawingToModelReferenceLink) ob[0];

						if (VersionHelper.isLatestRevision(dml.getModel())) {

							PersistenceHelper.manager.delete((DrawingToModelReferenceLink) ob[0]);
						}
					}
				}

				// 도면/부품/모델 링크 생성 ...
				try {

					EDMServiceHelper.setEPMReference(part, preEPM, model, el.getReferenceType(), el.getRequired());
				} catch (WTPropertyVetoException e) {
					Kogger.error(getClass(), e);
					throw new WTException(e);
				}

				// 도면과 부품 링크 history 삭제...
				if (VersionHelper.isLatestRevision(part)) {

					PersistenceHelper.manager.delete(el);
				}
			}
		}

		QueryResult modelQr = null;

		try {

			modelQr = EDMHelper.getRefedModelHistory(preEPM, null, EDMHelper.REQUIRED_REFERENCE_MODEL);
			// 참조모델(도면과 모델만 연관하는 경우)
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		if (modelQr != null) {

			DrawingToModelReferenceLink dml = null;
			EPMDocument model = null;
			Object[] oo = null;

			while (modelQr.hasMoreElements()) {

				oo = (Object[]) modelQr.nextElement();

				dml = (DrawingToModelReferenceLink) oo[0];

				model = dml.getModel();

				try {

					EDMServiceHelper.setEPMReference(null, preEPM, model, null, dml.getRequired());
				} catch (WTPropertyVetoException e) {
					Kogger.error(getClass(), e);
					throw new WTException(e);
				}

				if (VersionHelper.isLatestRevision(model)) {

					PersistenceHelper.manager.delete(dml);
				}
			}
		}
	}

	public void deleteRevisionByECO(EPMDocument epm) throws WTException {
		EDMServiceHelper.deleteRevisionByECO(epm);
	}

	// tklee test 중...
	// public void deleteLatestLink(EPMDocument epm) throws WTException {
	// EDMServiceHelper.deleteLatestLink(epm);
	// }

	// tklee test 중...
	// public void deleteLatestLink(WTPart part) throws WTException {
	// EDMServiceHelper.deleteLatestLink(part);
	// }

	public void newVersion(EPMDocument epm, boolean isInWorkPartOnly) throws WTException {

		if (WorkInProgressHelper.isCheckedOut(epm)) {
			return;
		}

		if (!VersionControlHelper.hasPredecessor(epm)) {
			EDMServiceHelper.setEDMUserData(epm, true);
			return;
		}

		EPMDocument preEPM = (EPMDocument) VersionControlHelper.service.predecessorOf(epm);

		// 이전 Iteration과 동일 버전 여부 체크
		if (VersionControlHelper.inSameBranch(epm, preEPM)) {
			return;
		}

		// 프로젝트 copy
		ProjectMaster pjtMst = (ProjectMaster) EDMHelper.getProject(preEPM);

		if (pjtMst != null) {
			EDMServiceHelper.updateProjectLink(epm, pjtMst, true);
		}

		// 사업부 정보 copy
		NumberCode bizCode0 = (NumberCode) EDMHelper.getBizType(preEPM);
		if (bizCode0 != null) {
			EDMServiceHelper.updateTypeCodeLink(epm, bizCode0, true);
		}

		// 양산도면버전 update
		setManufacturingVersion(epm, true);
		// updateManufacturingVersion(epm);

		// 도면 구분 및 도면 유형 업데이트..
		// TRUE 제품, FASLE 금형

		if (!EDMHelper.isProductDrawing(epm)) {
			// 금형 관련 IBA 업데이트 작업 개정작업할시 이벤트 발생..

			IBAHolder ibaHolder = null;

			try {
				ibaHolder = IBAValueHelper.service.refreshAttributeContainer(epm, null, null, null);
			} catch (RemoteException e) {
				throw new WTException(e);
			}

			DefaultAttributeContainer container = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();

			if (container == null) {
				return;
			}

			ibaHolder.setAttributeContainer(container);
			EDMAttributeUtil.updateIBAHolder(ibaHolder);

			PersistenceHelper.manager.refresh(epm);

		}

		// 작성부서 update
		EDMServiceHelper.setEDMUserData(epm, true);

		// WGM으로 등록한 ProE 모델/드로잉...
		String category = EDMHelper.getCategory(epm);
		if (category.length() > 0) {
			setHistory(epm, isInWorkPartOnly);
		}
	}

	public void newVersion(WTPart part, boolean isInWorkPartOnly) throws WTException {

		if (!VersionControlHelper.hasPredecessor(part)) {
			return;
		}

		if (WorkInProgressHelper.isCheckedOut(part)) {
			return;
		}

		WTPart prePart = (WTPart) VersionControlHelper.service.predecessorOf(part);
		if (VersionControlHelper.inSameBranch(part, prePart)) {
			return;
		}

		setHistory(part, isInWorkPartOnly);
	}

	public void setHistory(WTPart part, boolean isInWorkPartOnly) throws WTException {
		WTPart prePart = (WTPart) VersionControlHelper.service.predecessorOf(part);

		if (prePart == null) {
			return;
		}

		EDMProperties edmProperties = EDMProperties.getInstance();
		ArrayList refedParts = null;

		try {
			// refedParts = EDMHelper.getReferenceDocs(part, null, -1);
			refedParts = EDMHelper.getRefDocMaster(prePart, null, -1);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		}

		if ((refedParts != null) && (refedParts.size() > 0)) {
			EPMLink el = null;
			EPMDocument epm = null;
			EPMDocument model = null;
			Object[] oo = null;

			for (int i = 0; i < refedParts.size(); i++) {
				oo = (Object[]) refedParts.get(i);
				el = (EPMLink) oo[0];
				epm = (EPMDocument) oo[1];

				if (el.getReferenceType().equals(edmProperties.getReferenceTypeForModel(null))) {
					continue;
				}

				model = EDMHelper.getAssociatedModel(epm, part, el.getReferenceType(), el.getRequired());
				epm = (EPMDocument) getReleasedVersioned(epm);

				if (model != null) {
					model = (EPMDocument) getReleasedVersioned(model);
				}

				try {
					EDMServiceHelper.setReferenceHistory(prePart, epm, model, el.getReferenceType(), el.getRequired(), el.isEcad(), isInWorkPartOnly);
				} catch (WTPropertyVetoException e) {
					Kogger.error(getClass(), e);
					throw new WTException(e);
				}
			}
		}

		return;
	}

	public void setHistory(EPMDocument epm, boolean isInWorkPartOnly) throws WTException {
		EPMDocument preEPM = (EPMDocument) VersionControlHelper.service.predecessorOf(epm);

		if (preEPM == null) {
			return;
		}
		/*
		 * HashMap ibaValues = EDMAttributes.getAttributeValues(preEPM, Locale.KOREAN); String category = null; if(ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) { category =
		 * EDMEnumeratedTypeUtil.getCADCategory(EDMAttributes.getLocalizedIBAValueDisplayString ((AbstractValueView)ibaValues.get(EDMHelper.IBA_CAD_CATEGORY),Locale.KOREAN
		 * ),Locale.KOREAN); }
		 */
		String category = EDMHelper.getCategory(preEPM);
		EDMProperties edmProperties = EDMProperties.getInstance();
		String referenceType = edmProperties.getReferenceType(category);

		/*
		 * if(EDMHelper.isRefedModel(preEPM)) { //EDMHelper.REQUIRED_REFERENCE_MODEL // } else { }
		 */
		ArrayList refedParts = null;

		try {
			refedParts = EDMHelper.getRefedByPartMasters(epm, referenceType, -1);// EDMHelper.getReferencedByParts(epm,referenceType,-1);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		}

		if (refedParts.size() > 0) {
			EPMLink el = null;
			WTPart part = null;
			EPMDocument model = null;
			Object[] oo = null;

			for (int i = 0; i < refedParts.size(); i++) {
				oo = (Object[]) refedParts.get(i);
				el = (EPMLink) oo[0];
				part = (WTPart) oo[1];
				model = EDMHelper.getAssociatedModel(epm, part, referenceType, el.getRequired());
				part = (WTPart) getReleasedVersioned(part);

				if (model != null) {
					model = (EPMDocument) getReleasedVersioned(model);
				}

				try {
					EDMServiceHelper.setReferenceHistory(part, preEPM, model, referenceType, el.getRequired(), el.isEcad(), isInWorkPartOnly);
				} catch (WTPropertyVetoException e) {
					throw new WTException(e);
				}
			}
		}

		ArrayList modelArry = EDMHelper.getReferencedByModels(epm, EDMHelper.REQUIRED_REFERENCE_MODEL);

		if (modelArry.size() > 0) {
			ModelReferenceLink ml = null;
			EPMDocument model = null;
			Object[] oo = null;

			for (int i = 0; i < modelArry.size(); i++) {
				oo = (Object[]) modelArry.get(i);
				ml = (ModelReferenceLink) oo[0];
				model = (EPMDocument) getReleasedVersioned((EPMDocument) oo[1]);

				if (model != null) {
					try {
						EDMServiceHelper.setReferenceHistory(null, preEPM, model, null, EDMHelper.REQUIRED_REFERENCE_MODEL, false, isInWorkPartOnly);
					} catch (WTPropertyVetoException e) {
						Kogger.error(getClass(), e);
						throw new WTException(e);
					}
				}
			}
		}
	}

	private RevisionControlled getReleasedVersioned(RevisionControlled rc) throws WTException {
		if (!(rc instanceof wt.lifecycle.LifeCycleManaged)) {
			return rc;
		}

		QueryResult qr = VersionControlHelper.service.allVersionsOf(rc.getMaster());
		while (qr.hasMoreElements()) {
			RevisionControlled d = (RevisionControlled) qr.nextElement();

			if (EDMProperties.getInstance().isReleasedState(d.getLifeCycleState().toString())) {
				return d;
			}
		}

		return rc;
	}

	// public void updateManufacturingVersion(EPMDocument epm) throws WTException {
	// /*
	// * 제품/금형도면 여부 체크 추가...
	// * 제품도면만 양산버전 관리 함.
	// */
	// if (!EDMHelper.isProductDrawing(epm)) {
	// return;
	// }
	//
	// IBAHolder ibaHolder = null;
	//
	// try {
	// ibaHolder = IBAValueHelper.service.refreshAttributeContainer(epm, null, null, null);
	// } catch (RemoteException e) {
	// throw new WTException(e);
	// }
	//
	// DefaultAttributeContainer container = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
	//
	// if (container == null) {
	// return;
	// }
	//
	// HashMap viewMap = EDMAttributes.getAttributeValues(epm, Locale.KOREAN);
	// if (viewMap.get(EDMHelper.IBA_MANUFACTURING_VERSION) == null) {
	// return;
	// }
	//
	// StringValueDefaultView valueView = (StringValueDefaultView) viewMap.get(EDMHelper.IBA_MANUFACTURING_VERSION);
	// String mVersion = valueView.getValue();
	//
	// // Modified by MJOH, 2011-02-23 //////////////////////////////////////////////////////////
	// // 고객(임승영D) 요청으로 최초양산버전 표기 방식 변경 : 버전 구분자 "." 삭제처리
	// /*
	// * String prefix = mVersion.substring(0, mVersion.lastIndexOf("."));
	// * int iteration = Integer.parseInt(mVersion.substring(mVersion.lastIndexOf(".")+1));
	// * String nextVer = prefix + "." + (++iteration);
	// */
	//
	// String prefix = mVersion.substring(0, 1);
	// int iteration = Integer.parseInt(mVersion.substring(1));
	// String nextVer = prefix + (++iteration);
	//
	// // /////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// try {
	// valueView.setValue(nextVer);
	// } catch (WTPropertyVetoException e) {
	// throw new WTException(e.getLocalizedMessage());
	// }
	//
	// container.updateAttributeValue(valueView);
	// ibaHolder.setAttributeContainer(container);
	// EDMAttributeUtil.updateIBAHolder(ibaHolder);
	//
	// PersistenceHelper.manager.refresh(epm);
	// }

	/**
	 * IBA 속성 중 'CAD 종류' Value 추가
	 * 
	 * @param epm
	 * @throws WTException
	 */
	public void initAttr(EPMDocument epm) throws WTException {

		String cadAppType = EDMHelper.getCADAppType(epm); // ACAD
		EDMProperties edmProperties = EDMProperties.getInstance();

		if ((cadAppType.length() == 0) && !edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())) { // PLMSYSTEM,
			IBAHolder ibaHolder = null;
			DefaultAttributeContainer container = null;
			CADAppType cadApp = null;

			try {
				ibaHolder = IBAValueHelper.service.refreshAttributeContainer(epm, null, null, null);
			} catch (RemoteException e) {
				Kogger.error(getClass(), e);
				throw new WTException(e);
			}
			EDMAttributes edmAttributes = EDMAttributes.getInstance();
			container = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();

			/*
			 * try{ Hashtable hh = e3ps.common.iba.IBAUtil.getIBAAttributes("EPMAttributes"); Iterator it = hh.keySet().iterator(); while(it.hasNext()){ String key =
			 * (String)it.next();// "DEVSTAGE" 이런거 나옴.. String value = e3ps.common.iba.IBAUtil.getAttrValue(epm, key); // 저장된 IBA값 나옴.. AttributeDefDefaultView dv =
			 * (AttributeDefDefaultView)edmAttributes.getEPMAttribute(key); AbstractValueView avv = (AbstractValueView) NewValueCreator.createNewValueObject(dv);
			 * Kogger.debug(getClass(), "dv null ? " + dv); Kogger.debug(getClass(), "avv null ? " + avv); Kogger.debug(getClass(), "value == " + value); if(value.length() > 0 &&
			 * value != null){ EDMAttributeUtil.setValue(avv, EDMAttributeUtil.getConvertedValue(value,avv.getClass().getDeclaredField("value").getType())); } } }catch(Exception
			 * e){ Kogger.error(getClass(), e); }
			 */
			cadApp = CADAppType.toCADAppType(epm.getAuthoringApplication().toString());

			AttributeDefDefaultView dv = (AttributeDefDefaultView) edmAttributes.getEPMAttribute(EDMHelper.IBA_CAD_APP_TYPE);
			AbstractValueView avv = (AbstractValueView) NewValueCreator.createNewValueObject(dv);
			try {
				EDMAttributeUtil.setValue(avv, EDMAttributeUtil.getConvertedValue(cadApp.getDisplay(Locale.KOREAN), avv.getClass().getDeclaredField("value").getType()));
			} catch (SecurityException e) {
				Kogger.error(getClass(), e);
			} catch (NoSuchFieldException e) {
				Kogger.error(getClass(), e);
			}

			container.addAttributeValue(avv);
			ibaHolder.setAttributeContainer(container);
			try {
				ibaHolder = IBAValueHelper.service.updateIBAHolder(ibaHolder, null, null, null);
			} catch (RemoteException e) {
				Kogger.error(getClass(), e);
			}
			// EDMAttributeUtil.updateIBAHolder(ibaHolder);
			PersistenceHelper.manager.refresh(epm);
		}
	}

	// 저장 후에 호출
	public void setManufacturingVersion(EPMDocument epm, boolean plusRevision) throws WTException {

		EDMAttributes edmAttributes = EDMAttributes.getInstance();

		EpmInfoUpadator epmInfoUpdator = new EpmInfoUpadator();

		// 개발단계가 없으면 양산 버전은 비워둔다. > CAD2BOM 시점 확인 필요

		IBAHolder ibaHolder = null;
		DefaultAttributeContainer container = null;
		try {
			ibaHolder = IBAValueHelper.service.refreshAttributeContainer(epm, null, null, null);
		} catch (Exception e) {
			Kogger.error(getClass(), e); // throw new WTException(e.getLocalizedMessage());
			ibaHolder = epm;
		}

		container = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();

		if (container == null) {
			return;
		}

		HashMap viewMap = new HashMap();
		AbstractValueView[] views = container.getAttributeValues();

		for (int i = 0; i < views.length; i++) {
			viewMap.put(views[i].getDefinition().getName(), views[i]);
		}

		AbstractValueView devStageValue = (AbstractValueView) viewMap.get(EDMHelper.IBA_DEV_STAGE);

		if (devStageValue == null) {
			return;
		}

		// 금형이면 > 0, 1, 2, 3.. 처럼 리비전과 동일하게
		if (!EDMHelper.isProductDrawing(epm)) {

			updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, epm.getVersionIdentifier().getValue());
			return;
		}

		// 제품은 D00, D01 이었다가 양산이후에는 0, 1, 2.. 로 변경
		String oldManufacVer = null;
		AbstractValueView manufacVereStringValue = (AbstractValueView) viewMap.get(EDMHelper.IBA_MANUFACTURING_VERSION);

		// DEVELOPMENT_STAGE, PRODUCTION_STAGE, DEV_REVIEW_STAGE
		String devStage = EDMEnumeratedTypeUtil.getDevStage(((StringValueDefaultView) devStageValue).getValue(), Locale.KOREAN);

		try {

			if ("DEVELOPMENT_STAGE".equals(devStage) || "DEV_REVIEW_STAGE".equals(devStage)) {

				if (manufacVereStringValue == null) {

					updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, "D00");

				} else {

					oldManufacVer = ((StringValueDefaultView) manufacVereStringValue).getValue();
					if (StringUtils.isEmpty((oldManufacVer))) {

						updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, "D00");

					} else {

						if (oldManufacVer != null && oldManufacVer.startsWith("D")) {
							if (plusRevision) {
								// 리비전 변경
								String revSuffixCode = String.valueOf(Integer.parseInt(oldManufacVer.substring(1)) + 1);
								try {
									revSuffixCode = NumberCodeUtil.getSerailNumberFormat(revSuffixCode, 2);
								} catch (Exception e) {
									Kogger.error(getClass(), e);
								}
								updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, "D" + revSuffixCode);
							}

						} else if (oldManufacVer != null && oldManufacVer.startsWith("P")) { // 만일을 대비해서 추가
							if (plusRevision) {
								String revSuffixCode = String.valueOf(Integer.parseInt(oldManufacVer.substring(1)) + 1);
								updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, revSuffixCode);
							}

						} else {
							// 이런 경우가 발생하지 않겠지만, 만일을 대비해서 코딩
							updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, "D01");
						}
					}
				}

			} else if ("PRODUCTION_STAGE".equals(devStage)) {

				oldManufacVer = ((StringValueDefaultView) manufacVereStringValue).getValue();
				if (StringUtils.isEmpty((oldManufacVer))) {
					String revision = epm.getVersionIdentifier().getValue();
					if ("0".equals(revision)) {
						updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, "0");
					} else {
						try {
							int oldRevision = Integer.parseInt(revision);
							oldRevision = oldRevision - 1;
							String oldRevisionStr = String.valueOf(oldRevision);
							EPMDocument preEpmDoc = (EPMDocument) VersionUtil.getVersionObject((EPMDocumentMaster) epm.getMaster(), oldRevisionStr);
							if (preEpmDoc != null) {
								String oldManufacVerRe = e3ps.common.iba.IBAUtil.getAttrValue(preEpmDoc, EDMHelper.IBA_MANUFACTURING_VERSION);
								if (StringUtils.isEmpty((oldManufacVerRe))) {
									updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, "0");
								}
							}
						} catch (Exception eparam) {

						}
					}

				} else {

					if (oldManufacVer != null && oldManufacVer.startsWith("D")) {
						updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, "0");
					} else if (oldManufacVer != null && oldManufacVer.startsWith("P")) { // 만일을 대비해서 추가
						if (plusRevision) {
							String revSuffixCode = String.valueOf(Integer.parseInt(oldManufacVer.substring(1)) + 1);
							updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, revSuffixCode);
						}
					} else {
						if (plusRevision) {
							int oldVersion = 0;
							try {
								oldVersion = Integer.parseInt(oldManufacVer);
								updateEpmStringValue(epmInfoUpdator, epm, EDMHelper.IBA_MANUFACTURING_VERSION, String.valueOf(oldVersion + 1));
							} catch (Exception e) {
								Kogger.error(getClass(), e);
							}
						}
					}
				}

			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		PersistenceHelper.manager.refresh(epm);
	}

	private void updateEpmStringValue(EpmInfoUpadator epmInfoUpdator, EPMDocument epm, String ibaDefType, String value) {

		if (epmInfoUpdator == null)
			return;
		try {
			e3ps.common.iba.IBAUtil.changeIBAValue(epm, ibaDefType, value);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}
	
	private void updateCategoryForLibrary(EPMDocument epm){
	    try{
		String category = "제품도면";
		if(epm == null){
		    return;
		}else{
		    
		    epm = (EPMDocument) PersistenceHelper.manager.refresh(epm);
		    
		    String epmNo = epm.getNumber();
		    
		    if(StringUtils.startsWith(epmNo.toUpperCase(), "LB")){
			
			EpmInfoUpadator updator = new EpmInfoUpadator();
			    
			updator.callupdateIBAValue(epm, EDMHelper.IBA_CAD_CATEGORY, category);
			updator.callupdateIBAValue(epm, EDMHelper.IBA_CAD_MANAGE_TYPE, category);
		    }
		    
		    
		}
		
	    }catch(Exception e){
		Kogger.error(getClass(), e);
	    }
	}

	private void updateCu_IBAupdate(EPMDocument epm) {// CU도면을 2D아닌 3D로 체크인하게 됨에 따라 도면유형을 고객제출도로 세팅 2015.04.01 황정태 추가
		// cu도면 체크인시 도면유형 update
		try {
			if (epm == null)
				return;

			epm = (EPMDocument) PersistenceHelper.manager.refresh(epm);

			String cateory = e3ps.common.iba.IBAUtil.getAttrValue(epm, EDMHelper.IBA_CAD_CATEGORY);
			if ("".equals(cateory) || cateory == null) {
				String cadName = epm.getCADName();
				String cadNumber = epm.getNumber();

				if (cadName == null) {
					return;
				}
				EpmInfoUpadator updator = new EpmInfoUpadator();
				if ((cadName.toLowerCase().endsWith(".drw") || cadName.toLowerCase().endsWith(".prt"))
						&& (cadNumber.toUpperCase().startsWith("CU_") || cadName.toUpperCase().startsWith("CU_"))) {
					updator.callupdateIBAValue(epm, EDMHelper.IBA_CAD_CATEGORY, "고객제출도면"); // "CADCategory" "PRODUCT_DRAWING"

					if (cadName.toLowerCase().endsWith(".prt")) {// 3D 고객제출도 일경우 부품과 자동연계한다.
						cadNumber = epm.getNumber().toUpperCase();

						// System.out.println("도면 찾기 : " + cadNumber);

						cadNumber = StringUtils.removeStart(cadNumber, "CU_");
						cadNumber = StringUtils.removeStart(cadNumber, "MG");
						cadNumber = StringUtils.removeStart(cadNumber, "ST");
						cadNumber = StringUtils.removeStart(cadNumber, "EM");
						cadNumber = StringUtils.removeStart(cadNumber, "JB");
						cadNumber = StringUtils.removeStart(cadNumber, "WH");
						cadNumber = StringUtils.removeEnd(cadNumber, "_3D");
						String partNo = cadNumber;

						if (!cadNumber.startsWith("K") && !cadNumber.startsWith("68")) {// K로 시작하는 부품 또는 상품이 아니면
							partNo = "H" + cadNumber;
						}

						// String partNo = Cad2BomUtil.getProdPartNoTypeByChangePrefix(cadNumber);
						WTPart part = PartBaseHelper.service.getLatestPart(partNo);

						if (part != null) {
							String epmOid = CommonUtil.getFullOIDString(epm);
							String partOid = CommonUtil.getFullOIDString(part);
							PartBaseHelper.service.connEpm2Part(epmOid, partOid, "CU_DRAWING", "1", "0");// 부품 연결

							EPMDocumentMaster cad3dMast = (EPMDocumentMaster) epm.getMaster();

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
							String teamType = claz.getDivisionFlag();

							if (teamType != null) {
								NumberCode typeCode = NumberCodeHelper.manager.getNumberCode("DIVISIONFLAG", teamType); // 사업부정보 세팅
								EDMServiceHelper.updateTypeCodeLink(epm, typeCode, true);
							}

						} else {
							throw new Exception("Part가 없음!");
						}
					}

				}
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	//
	private void update2DPartProps(EPMDocument epm) {
		try {

			if (epm == null)
				return;

			epm = (EPMDocument) PersistenceHelper.manager.refresh(epm);

			// 2D drw가 Checkin시
			String cadName = epm.getCADName();
			if (cadName == null) {
				return;
			}

			if (!cadName.toLowerCase().endsWith(".drw")) {
				return;
			}

			// 이미 부품과 연결되어 있는 경우 성능을 생각해서 return
			SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
			List<EPMLink> oldEpmLinkList = query.queryForListLinkByRoleA(EPMDocumentMaster.class, EPMLink.class, (EPMDocumentMaster) epm.getMaster());
			if (oldEpmLinkList != null && oldEpmLinkList.size() > 0) {
				return;
			}

			// 3D를 찾아 온다.
			ModelStrucUtil modelStrucUtil = new ModelStrucUtil();
			String epm2dOid = CommonUtil.getOIDString(epm);
			String epm2DNo = epm.getNumber();
			EPMDocument epmDocument3d = modelStrucUtil.getLatest3DBy2D(epm2dOid, epm2DNo);

			if (epmDocument3d == null) {
				return;
			}

			EPMDocumentMaster cad3dMast = (EPMDocumentMaster) epmDocument3d.getMaster();

			// 3D의 부품을 가져온다.
			List<WTPartMaster> retList = new ArrayList<WTPartMaster>();
			List<EPMLink> epmLinkList = query.queryForListLinkByRoleA(EPMDocumentMaster.class, EPMLink.class, cad3dMast);

			Map checkMap = new HashMap();
			if (epmLinkList != null && epmLinkList.size() > 0) {

				for (EPMLink link : epmLinkList) {

					WTPartMaster partMast = link.getPartMaster();

					if (partMast == null) {
						continue;
					}
					String referenceType = link.getReferenceType();

					if ("RELATED_MODEL".equals(referenceType) || "RELATED_DRAWING".equals(referenceType) || "CU_DRAWING".equals(referenceType)) {

					} else {
						continue;
					}

					if (checkMap.containsKey(partMast.getNumber())) {
						continue;
					} else {
						checkMap.put(partMast.getNumber(), null);
					}

					retList.add(partMast);
				}
			}

			// 부품이 없는 경우
			if (retList.size() == 0) {

			}
			// 부품이 있는 경우
			else {
				for (WTPartMaster partMast : retList) {
					WTPart part = PartBaseHelper.service.getLatestPart(partMast);
					PartEpmRelation rel = new PartEpmRelation();
					rel.associateCreate(part, epm, false, PartEpmRelation.TWOD_REPRESENT_PART_TYPE, PartEpmRelation.TWOD_REFERENCE_TYPE, true);
					break;
				}
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}
}
