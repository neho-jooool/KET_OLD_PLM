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
import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.util.WTException;
import e3ps.common.util.KETParamMapUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETMoldChangePlan;
import e3ps.ecm.entity.KETMoldChangeRequestVO;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ecm.servlet.MoldEcoChangeServlet;

/**
 * 
 * @version 1.0
 **/

@RemoteInterface
public interface KETEcmService {

    /**
     * @param moldPlan
     * @param refDocs
     * @param attachFiles
     * @return String
     * @exception wt.util.WTException
     **/
    public String createMoldPlan(Hashtable<String, String> moldPlan, KETProjectDocument[] refDocs, Vector attachFiles) throws WTException;

    /**
     * @param moldPlan
     * @param refDocs
     * @param attachFiles
     * @return String
     * @exception wt.util.WTException
     **/
    public String modifyMoldPlan(Hashtable<String, String> moldPlan, KETProjectDocument[] refDocs, Vector attachFiles) throws WTException;

    /**
     * @param ketMoldChangeOrderVO
     * @return KETMoldChangeOrderVO
     * @exception wt.util.WTException
     **/
    public KETMoldChangeOrderVO createMoldEcoBasicInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException;

    /**
     * @param ketMoldChangeOrderVO
     * @return KETMoldChangeOrderVO
     * @exception wt.util.WTException
     **/
    public KETMoldChangeOrderVO createMoldEcoActivitycInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException;

    /**
     * @param ketMoldChangeOrderVO
     * @return KETMoldChangeOrderVO
     * @exception wt.util.WTException
     **/
    public KETMoldChangeOrderVO createMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException;

    /**
     * @param ketMoldChangeOrderVO
     * @return KETMoldChangeOrderVO
     * @exception wt.util.WTException
     **/
    public KETMoldChangeOrderVO updateMoldEcoBasicInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException;

    /**
     * @param ketMoldChangeOrderVO
     * @return KETMoldChangeOrderVO
     * @exception wt.util.WTException
     **/
    public KETMoldChangeOrderVO updateMoldEcoActivityInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException;

    /**
     * @param ketMoldChangeOrderVO
     * @return KETMoldChangeOrderVO
     * @exception wt.util.WTException
     **/
    public KETMoldChangeOrderVO updateMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException;

    /**
     * @param ketMoldChangeOrderVO
     * @return int
     * @exception wt.util.WTException
     **/
    public int deleteMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException;

    /**
     * @param planOid
     * @return KETMoldChangePlan
     * @exception wt.util.WTException
     **/
    public KETMoldChangePlan getMoldPlan(String planOid) throws WTException;

    /**
     * @param planOid
     * @return boolean
     * @exception wt.util.WTException
     **/
    public boolean deleteMoldPlan(String planOid) throws WTException;

    /**
     * @param ketMoldChangeRequestVO
     * @return KETMoldChangeRequestVO
     * @exception wt.util.WTException
     **/
    public KETMoldChangeRequestVO createMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException;

    /**
     * @param ketMoldChangeRequestVO
     * @return KETMoldChangeRequestVO
     * @exception wt.util.WTException
     **/
    public KETMoldChangeRequestVO updateMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException;

    /**
     * @param ketMoldChangeRequestVO
     * @return int
     * @exception wt.util.WTException
     **/
    public int deleteMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException;

    /**
     * @param reqData
     * @param partList
     * @param reqCommentList
     * @param issueList
     * @param attachFiles
     * @return String
     * @exception wt.util.WTException
     * 
     * @deprecated
     * 
     **/
    public String createProdEcr(Hashtable<String, String> reqData, String[] partList, String[] reqCommentList, String[] issueList,
	    Vector attachFiles) throws WTException;

    /**
     * @param reqData
     * @param partHash
     * @param issueHash
     * @param attachFiles
     * @return String
     * @exception wt.util.WTException
     * 
     * @deprecated
     * 
     **/
    public String modifyProdEcr(Hashtable<String, String> reqData, Hashtable partHash, Hashtable issueHash, Vector attachFiles)
	    throws WTException;

    /**
     * @param ecrOid
     * @return boolean
     * @exception wt.util.WTException
     **/
    public boolean deleteProdEcr(String ecrOid) throws WTException;

    /**
     * @param ecrOid
     * @return KETProdChangeRequest
     * @exception wt.util.WTException
     **/
    public KETProdChangeRequest getProdEcr(String ecrOid) throws WTException;

    /**
     * @param reqData
     * @param refEcrList
     * @param refPartList
     * @param refEpmDocList
     * @param refBomList
     * @param refDocList
     * @param attachFileList
     * @return String
     * @exception wt.util.WTException
     **/
    public String createProdEco(Hashtable<String, String> reqData, String[] refEcrList, ArrayList<Hashtable<String, String>> refPartList,
	    ArrayList<Hashtable<String, String>> refEpmDocList, ArrayList<Hashtable<String, String>> refBomList,
	    ArrayList<Hashtable<String, String>> refDocList, ArrayList<Hashtable<String, String>> refDqmList, Vector attachFileList)
	    throws WTException;

    /**
     * @param reqData
     * @param ecrList
     * @param addObjListHash
     * @param delObjListHash
     * @param attachFileList
     * @return String
     * @exception wt.util.WTException
     * 
     * @deprecated
     **/
    public String modifyProdEco(Hashtable reqData, String[] ecrList,
	    Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash, Hashtable<String, ArrayList<String>> delObjListHash,
	    Vector attachFileList) throws WTException;

    /**
     * @param prodEcoOid
     * @param epmDocLinkOidList
     * @param epmDocOidList
     * @param reviseChkFlagList
     * @param epmDocTypeList
     * @return String
     * @exception wt.util.WTException
     **/
    public String reviseEpmDocInProdEco(String prodEcoOid, String[] epmDocLinkOidList, String[] epmDocOidList, String[] reviseChkFlagList,
	    String[] epmDocTypeList) throws WTException;

    /**
     * @param prodEcoOid
     * @param bomHeaderLinkOid
     * @param partNo
     * @param reviseChkFlag
     * @param massChgYn
     * @param parentPartListStr
     * @return String
     * @exception wt.util.WTException
     * 
     * @deprecated
     **/
    public String reviseBomInProdEco(String prodEcoOid, String bomHeaderLinkOid, String partNo, String reviseChkFlag, String massChgYn,
	    String parentPartListStr) throws WTException;

    /**
     * @param prodEcoOid
     * @param epmDocListLink
     * @param changeFlagList
     * @return String
     * @exception wt.util.WTException
     **/
    public String changeEpmDoc(String prodEcoOid, String[] epmDocListLink, String[] changeFlagList) throws WTException;

    /**
     * @param ecoOid
     * @return boolean
     * @exception wt.util.WTException
     **/
    public boolean deleteProdEco(String ecoOid) throws WTException;

    /**
     * @param partNo
     * @return boolean
     * @exception wt.util.WTException
     **/
    public boolean cancelReviseBom(String partNo) throws WTException;

    /**
     * @param prodEcoOid
     * @param bomHeaderLinkOid
     * @param partNo
     * @param reviseChkFlag
     * @param massChgYn
     * @param parentPartListStr
     * @return String
     * @exception wt.util.WTException
     * 
     * @deprecated
     **/
    public String cancelReviseBomInProdEco(String prodEcoOid, String bomHeaderLinkOid, String partNo, String reviseChkFlag,
	    String massChgYn, String parentPartListStr) throws WTException;

    /**
     * @param ecoOid
     * @param addObjListHash
     * @param delObjListHash
     * @param activityType
     * @return String
     * @exception wt.util.WTException
     **/
    public String modifyProdEcoNotOwner(String ecoOid, Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash,
	    Hashtable<String, ArrayList<String>> delObjListHash, String activityType) throws WTException;

    /**
     * @param ecoOid
     * @return KETMoldChangeOrder
     * @exception wt.util.WTException
     **/
    public KETMoldChangeOrder createMoldEcoByProdEco(String ecoOid) throws WTException;

    /**
     * @param prodEcoOid
     * @param epmDocLinkOidList
     * @param epmDocOidList
     * @param cancelChkFlagList
     * @return String
     * @exception wt.util.WTException
     **/
    public String cancelReviseEpmDocInProdEco(String prodEcoOid, String[] epmDocLinkOidList, String[] epmDocOidList,
	    String[] cancelChkFlagList) throws WTException;

    /**
     * @param reqData
     * @param ecrList
     * @param refPartList
     * @param delObjListHash
     * @param attachFileList
     * @return String
     * @exception wt.util.WTException
     **/
    public String modifyProdEcoBasic(Hashtable reqData, String[] ecrList, ArrayList<Hashtable<String, String>> refPartList,
	    Hashtable<String, ArrayList<String>> delObjListHash, Vector attachFileList) throws WTException;

    /**
     * @param moldEco
     * @param ketMoldECALinkVOList
     * @return String
     * @exception wt.util.WTException
     **/
    public String completeRegistMoldEco(KETMoldChangeOrder moldEco, ArrayList ketMoldECALinkVOList) throws WTException;

    /**
     * @param docLinkOid
     * @param partOidList
     * @param partNoList
     * @param chgTypeList
     * @param descList
     * @param ecoOid
     * @return ArrayList<Hashtable<String,String>>
     * @exception wt.util.WTException
     **/
    public ArrayList<Hashtable<String, String>> saveMoldStdPartLine(String docLinkOid, String[] partOidList, String[] partNoList,
	    String[] chgTypeList, String[] descList, String ecoOid) throws WTException;

    /**
     * @param plan
     * @exception wt.util.WTException
     **/
    public void updateDailyMoldPlanStatus(KETMoldChangePlan plan) throws WTException;

    /**
     * 
     * @param paramMap
     * @param attachFiles
     * @return
     * @throws WTException
     * @메소드명 : createProdEcr
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String createProdEcr(KETParamMapUtil paramMap, Vector attachFiles) throws WTException;

    /**
     * 
     * @param ecrOid
     * @param paramMap
     * @param attachFiles
     * @return
     * @throws WTException
     * @메소드명 : modifyProdEcr
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String modifyProdEcr(String ecrOid, KETParamMapUtil paramMap, Vector attachFiles) throws WTException;

    /**
     * 
     * @param reqData
     * @param attachFiles
     * @return
     * @throws WTException
     * @메소드명 : modifyEcrCompetentDepartment
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String modifyEcrCompetentDepartment(Hashtable<String, String> reqData, Vector attachFiles) throws WTException;

    /**
     * 
     * @param reqData
     * @param attachFiles
     * @param ecnList
     * @return
     * @throws WTException
     * @메소드명 : modifyEcrMeetingMinutes
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String modifyEcrMeetingMinutes(Hashtable<String, String> reqData, Vector attachFiles, Hashtable<String, String[]> ecnList)
	    throws WTException;

    /**
     * 
     * @param ecr
     * @param reqData
     * @return
     * @throws WTException
     * @메소드명 : modifyEcrExpansion
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String modifyEcrExpansion(WTChangeRequest2 ecr, Hashtable<String, String> reqData) throws WTException;

    /**
     * 설계부품/도면 저장(수정시)
     * 
     * @param ecoOid
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : modifyProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String modifyProdEco(String ecoOid, KETParamMapUtil paramMap, Vector attachFiles) throws WTException;

    /**
     * 문서 개정
     * 
     * @param ecoOid
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : reviseRelDocInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String reviseRelDocInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * 문서 개정 취소
     * 
     * @param ecoOid
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : cancelReviseRelDocInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String cancelReviseRelDocInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * 수신확인
     * 
     * @param ecoOid
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : receiveConfirmInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String receiveConfirmInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * ToDo 에서 저장
     * 
     * @param ecoOid
     * @param addObjListHash
     * @param delObjListHash
     * @param activityType
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : modifyProdEcoNotOwner2
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String modifyProdEcoNotOwner2(String ecoOid, Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash,
	    Hashtable<String, ArrayList<String>> delObjListHash, String activityType, KETParamMapUtil paramMap) throws WTException;

    /**
     * BOM 개정
     * 
     * @param ecoOid
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : reviseBomInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String reviseBomInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * BOM 개정 취소
     * 
     * @param ecoOid
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : cancelReviseBomInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String cancelReviseBomInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * 작업완료
     * 
     * @param ecoOid
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : completeInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String completeInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * ECN 중지
     * 
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : stopEcn
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String stopEcn(KETParamMapUtil paramMap) throws WTException;

    /**
     * ECN 재시작
     * 
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : restartEcn
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String restartEcn(KETParamMapUtil paramMap) throws WTException;

    /**
     * 회의소집
     * 
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : receiveConfirmInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String callMeeting(String ecrOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * 재전송(ERP)
     * 
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : receiveConfirmInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String resendERP(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * @param ketMoldChangeOrderVO
     * @return KETMoldChangeOrderVO
     * @exception wt.util.WTException
     **/
    public KETMoldChangeOrderVO updateMoldEcoInfo(MoldEcoChangeServlet moldEcoChangeServlet) throws WTException;

    /**
     * 등록완료전 유효성 체크
     * 
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : receiveConfirmInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String validateBeforeActivityReg(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * 활동완료전 유효성 체크
     * 
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : receiveConfirmInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String validateBeforeCompleteReg(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * 결재상신전 유효성 체크
     * 
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : receiveConfirmInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String validateBeforeRequestApprove(String ecoOid, KETParamMapUtil paramMap) throws WTException;

    /**
     * BOM Editor에서 '저장'후 ECO에 '저장했다'는 상황을 알려준다.
     * 
     * @param paramMap
     * @return
     * @throws WTException
     * @메소드명 : receiveConfirmInProdEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String updateEcoAfterSaveBomEditor(String ecoNumber, String partNumber) throws WTException;

    /**
     * ECO에 설변으로 추가된 부품중 아직 개정되지 않은 부품 리스트를 가져온다.
     * 
     * @param ecoOid
     * @return
     * @throws WTException
     * @메소드명 : getWTPartListUnrevisedFromEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 11. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public ArrayList<WTPart> getWTPartListUnrevisedFromEco(String ecoOid) throws WTException;

    /**
     * 
     * @param ecoOid
     * @param part_no
     * @param part_sp_net_weight
     * @param part_sp_total_weight
     * @param part_oid
     * @param part_sp_scrab_weight
     * @return
     * @throws Exception
     * @메소드명 : actualWeightBomSave
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 11. 19.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void saveKETEcnWeightHistory(String ecoOid, String part_no[], String part_sp_net_weight[], String part_sp_total_weight[],
	    String part_oid[], String part_sp_scrab_weight[]) throws WTException;

    /**
     * ECR 승인완료 7일 이후 매일 발송
     * 
     * @return
     * @throws WTException
     * @메소드명 : getEcrRelatedMeetingDelay
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 11. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public HashMap<WTChangeRequest2, ArrayList<Managed>> getEcrRelatedMeetingDelay() throws WTException;

    /**
     * 완료예정일까지 ECN 후속활동이 완료되지 않으면 일단위로 메일 발송
     * 
     * @return
     * @throws WTException
     * @메소드명 : getEcoRelatedEcnDelay
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 11. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public HashMap<WTChangeOrder2, ArrayList<Managed>> getEcoRelatedEcnDelay() throws WTException;

    /**
     * 
     * @param eco
     * @return
     * @throws WTException
     * @메소드명 : validateEcoPartRelatedEpmDoc
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 12. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String validateEcoPartRelatedEpmDoc(WTChangeOrder2 eco) throws WTException;

    /**
     * 
     * 
     * @param ecoOid
     * @return
     * @throws WTException
     * @메소드명 : initEco
     * @작성자 : admin
     * @작성일 : 2020. 10. 21.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String initEco(String ecoOid) throws WTException;
}
