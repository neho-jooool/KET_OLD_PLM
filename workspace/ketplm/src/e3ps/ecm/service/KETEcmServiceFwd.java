/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.ecm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.enterprise.Managed;
import wt.method.RemoteAccess;
import wt.part.WTPart;
import wt.services.ServiceFactory;
import wt.util.WTException;
import e3ps.common.util.KETParamMapUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETMoldChangePlan;
import e3ps.ecm.entity.KETMoldChangeRequestVO;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ecm.servlet.MoldEcoChangeServlet;

@Deprecated
public class KETEcmServiceFwd implements KETEcmService, RemoteAccess {

    public String createMoldPlan(Hashtable<String, String> moldPlan, KETProjectDocument[] refDocs, Vector attachFiles) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).createMoldPlan(moldPlan, refDocs, attachFiles);
    }

    public String modifyMoldPlan(Hashtable<String, String> moldPlan, KETProjectDocument[] refDocs, Vector attachFiles) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).modifyMoldPlan(moldPlan, refDocs, attachFiles);
    }

    public KETMoldChangeOrderVO createMoldEcoBasicInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).createMoldEcoBasicInfo(ketMoldChangeOrderVO);
    }

    public KETMoldChangeOrderVO createMoldEcoActivitycInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).createMoldEcoActivitycInfo(ketMoldChangeOrderVO);
    }

    public KETMoldChangeOrderVO createMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).createMoldEcoInfo(ketMoldChangeOrderVO);
    }

    public KETMoldChangeOrderVO updateMoldEcoBasicInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).updateMoldEcoBasicInfo(ketMoldChangeOrderVO);
    }

    public KETMoldChangeOrderVO updateMoldEcoActivityInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).updateMoldEcoActivityInfo(ketMoldChangeOrderVO);
    }

    public KETMoldChangeOrderVO updateMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).updateMoldEcoInfo(ketMoldChangeOrderVO);
    }

    public int deleteMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).deleteMoldEcoInfo(ketMoldChangeOrderVO);
    }

    public KETMoldChangePlan getMoldPlan(String planOid) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).getMoldPlan(planOid);
    }

    public boolean deleteMoldPlan(String planOid) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).deleteMoldPlan(planOid);
    }

    public KETMoldChangeRequestVO createMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).createMoldEcrInfo(ketMoldChangeRequestVO);
    }

    public KETMoldChangeRequestVO updateMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).updateMoldEcrInfo(ketMoldChangeRequestVO);
    }

    public int deleteMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).deleteMoldEcrInfo(ketMoldChangeRequestVO);
    }

    public String createProdEcr(Hashtable<String, String> reqData, String[] partList, String[] reqCommentList, String[] issueList,
	    Vector attachFiles) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).createProdEcr(reqData, partList, reqCommentList, issueList, attachFiles);
    }

    public String modifyProdEcr(Hashtable<String, String> reqData, Hashtable partHash, Hashtable issueHash, Vector attachFiles)
	    throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).modifyProdEcr(reqData, partHash, issueHash, attachFiles);
    }

    public boolean deleteProdEcr(String ecrOid) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).deleteProdEcr(ecrOid);
    }

    public KETProdChangeRequest getProdEcr(String ecrOid) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).getProdEcr(ecrOid);
    }

    public String createProdEco(Hashtable<String, String> reqData, String[] refEcrList, ArrayList<Hashtable<String, String>> refPartList,
	    ArrayList<Hashtable<String, String>> refEpmDocList, ArrayList<Hashtable<String, String>> refBomList,
	    ArrayList<Hashtable<String, String>> refDocList, ArrayList<Hashtable<String, String>> refDqmList, Vector attachFileList)
	    throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).createProdEco(reqData, refEcrList, refPartList, refEpmDocList, refBomList,
	        refDocList, refDqmList, attachFileList);
    }

    public String modifyProdEco(Hashtable reqData, String[] ecrList,
	    Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash, Hashtable<String, ArrayList<String>> delObjListHash,
	    Vector attachFileList) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).modifyProdEco(reqData, ecrList, addObjListHash, delObjListHash,
	        attachFileList);
    }

    public String reviseEpmDocInProdEco(String prodEcoOid, String[] epmDocLinkOidList, String[] epmDocOidList, String[] reviseChkFlagList,
	    String[] epmDocTypeList) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).reviseEpmDocInProdEco(prodEcoOid, epmDocLinkOidList, epmDocOidList,
	        reviseChkFlagList, epmDocTypeList);
    }

    public String reviseBomInProdEco(String prodEcoOid, String bomHeaderLinkOid, String partNo, String reviseChkFlag, String massChgYn,
	    String parentPartListStr) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).reviseBomInProdEco(prodEcoOid, bomHeaderLinkOid, partNo, reviseChkFlag,
	        massChgYn, parentPartListStr);
    }

    public String changeEpmDoc(String prodEcoOid, String[] epmDocListLink, String[] changeFlagList) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).changeEpmDoc(prodEcoOid, epmDocListLink, changeFlagList);
    }

    public boolean deleteProdEco(String ecoOid) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).deleteProdEco(ecoOid);
    }

    public boolean cancelReviseBom(String partNo) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).cancelReviseBom(partNo);
    }

    public String cancelReviseBomInProdEco(String prodEcoOid, String bomHeaderLinkOid, String partNo, String reviseChkFlag,
	    String massChgYn, String parentPartListStr) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).cancelReviseBomInProdEco(prodEcoOid, bomHeaderLinkOid, partNo, reviseChkFlag,
	        massChgYn, parentPartListStr);
    }

    public String modifyProdEcoNotOwner(String ecoOid, Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash,
	    Hashtable<String, ArrayList<String>> delObjListHash, String activityType) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).modifyProdEcoNotOwner(ecoOid, addObjListHash, delObjListHash, activityType);
    }

    public KETMoldChangeOrder createMoldEcoByProdEco(String ecoOid) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).createMoldEcoByProdEco(ecoOid);
    }

    public String cancelReviseEpmDocInProdEco(String prodEcoOid, String[] epmDocLinkOidList, String[] epmDocOidList,
	    String[] cancelChkFlagList) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).cancelReviseEpmDocInProdEco(prodEcoOid, epmDocLinkOidList, epmDocOidList,
	        cancelChkFlagList);
    }

    public String modifyProdEcoBasic(Hashtable reqData, String[] ecrList, ArrayList<Hashtable<String, String>> refPartList,
	    Hashtable<String, ArrayList<String>> delObjListHash, Vector attachFileList) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).modifyProdEcoBasic(reqData, ecrList, refPartList, delObjListHash,
	        attachFileList);
    }

    public String completeRegistMoldEco(KETMoldChangeOrder moldEco, ArrayList ketMoldECALinkVOList) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).completeRegistMoldEco(moldEco, ketMoldECALinkVOList);
    }

    public ArrayList<Hashtable<String, String>> saveMoldStdPartLine(String docLinkOid, String[] partOidList, String[] partNoList,
	    String[] chgTypeList, String[] descList, String ecoOid) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(KETEcmService.class).saveMoldStdPartLine(docLinkOid, partOidList, partNoList, chgTypeList,
	        descList, ecoOid);
    }

    public void updateDailyMoldPlanStatus(KETMoldChangePlan plan) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	ServiceFactory.getService(KETEcmService.class).updateDailyMoldPlanStatus(plan);
    }

    @Override
    public String createProdEcr(KETParamMapUtil paramMap, Vector attachFiles) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String modifyProdEcr(String ecrOid, KETParamMapUtil paramMap, Vector attachFiles) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String modifyEcrCompetentDepartment(Hashtable<String, String> reqData, Vector attachFiles) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String modifyEcrMeetingMinutes(Hashtable<String, String> reqData, Vector attachFiles, Hashtable<String, String[]> ecnList)
	    throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String modifyEcrExpansion(WTChangeRequest2 ecr, Hashtable<String, String> reqData) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String modifyProdEco(String ecoOid, KETParamMapUtil paramMap, Vector attachFiles) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String reviseRelDocInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String cancelReviseRelDocInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String receiveConfirmInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String modifyProdEcoNotOwner2(String ecoOid, Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash,
	    Hashtable<String, ArrayList<String>> delObjListHash, String activityType, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String reviseBomInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String cancelReviseBomInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String completeInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String stopEcn(KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String callMeeting(String ecrOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String resendERP(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KETMoldChangeOrderVO updateMoldEcoInfo(MoldEcoChangeServlet moldEcoChangeServlet) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String validateBeforeActivityReg(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String validateBeforeRequestApprove(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String validateBeforeCompleteReg(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String updateEcoAfterSaveBomEditor(String ecoNumber, String partNumber) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String restartEcn(KETParamMapUtil paramMap) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ArrayList<WTPart> getWTPartListUnrevisedFromEco(String ecoOid) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void saveKETEcnWeightHistory(String ecoOid, String[] part_no, String[] part_sp_net_weight, String[] part_sp_total_weight,
	    String[] part_oid, String[] part_sp_scrab_weight) throws WTException {
	// TODO Auto-generated method stub

    }

    @Override
    public HashMap<WTChangeRequest2, ArrayList<Managed>> getEcrRelatedMeetingDelay() throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public HashMap<WTChangeOrder2, ArrayList<Managed>> getEcoRelatedEcnDelay() throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String validateEcoPartRelatedEpmDoc(WTChangeOrder2 eco) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String initEco(String ecoOid) throws WTException {
	// TODO Auto-generated method stub
	return null;
    }

}
