package ext.ket.cost.code.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wt.fc.QueryResult;
import wt.method.RemoteInterface;
import ext.ket.cost.entity.CostCurrencyInfo;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostQuantity;

/**
 * 
 * 
 * @클래스명 : CostCodeService
 * @작성자 : 황정태
 * @작성일 : 2017. 12. 29.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@RemoteInterface
public interface CostCodeService {

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 * @메소드명 : getCostPartTypeLinkList
	 * @작성자 : 황정태
	 * @작성일 : 2017. 12. 29.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public List<Map<String, Object>> getCostPartTypeLinkList() throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : saveCostPartTypeLink
	 * @작성자 : 황정태
	 * @작성일 : 2017. 12. 29.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, Object> saveCostPartTypeLink(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param costPartTypeOid
	 * @return
	 * @throws Exception
	 * @메소드명 : getCostFactoryTreeInfoByPartType
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 5.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public List<Map<String, Object>> getCostFactoryTreeInfoByPartType(String costPartTypeOid) throws Exception;

	/**
	 * 
	 * 
	 * @param costPartTypeOid
	 * @throws Exception
	 * @메소드명 : purchasePriceSapInterface
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 9.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public void purchasePriceSapInterface() throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : getCostGridData
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 12.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public List<Map<String, Object>> getCostGridData(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : saveCostReduceCode
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 12.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, Object> saveCostReduceCode(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : saveCostLogiticsCode
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 13.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, Object> saveCostLogiticsCode(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : saveCostPacking
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 17.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, Object> saveCostPacking(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : saveCostMaterial
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 18.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, Object> saveCostMaterial(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param partNo
	 * @return
	 * @throws Exception
	 * @메소드명 : getMaterialCostBySap
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 18.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public List<Map<String, Object>> getMaterialInfoBySap(String partNo, CostPart part, String rate) throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : saveCostEtcInvest
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 19.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, Object> saveCostEtcInvest(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : saveCostQty
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 19.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, Object> saveCostQty(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : costQtyCaseCreate
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 22.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, Object> costQtyCaseCreate(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param ProductOid
	 * @return
	 * @throws Exception
	 * @메소드명 : QtyMaxVersion
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 22.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public String QtyMaxVersion(String ProductOid) throws Exception;

	/**
	 * 
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @메소드명 : costQtyCaseDelete
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 22.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, Object> costQtyCaseDelete(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param part
	 * @param codeList
	 * @throws Exception
	 * @메소드명 : syncPartByCodeMaster
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 25.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public void syncPartByCodeMaster(String partListOid, String version, String subVersion, String partOid) throws Exception;

	/**
	 * 
	 * 
	 * @param partListOid
	 * @param version
	 * @throws Exception
	 * @메소드명 : createCurrencyInfo
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 26.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public void createCurrencyInfo(CostPart part) throws Exception;

	/**
	 * 
	 * 
	 * @param partListOid
	 * @param version
	 * @return
	 * @throws Exception
	 * @메소드명 : getCostCurrencyQuery
	 * @작성자 : user
	 * @작성일 : 2018. 1. 26.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public QueryResult getCostCurrencyQuery(CostPart part) throws Exception;

	/**
	 * 
	 * 
	 * @param partListOid
	 * @param version
	 * @throws Exception
	 * @메소드명 : syncPartByMyCurrency
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 26.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public void syncPartByMyCurrency(CostPart part) throws Exception;

	/**
	 * 
	 * 
	 * @param partOid
	 * @return
	 * @throws Exception
	 * @메소드명 : getMyExRate
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 30.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, String> getMyExRate(String partOid) throws Exception;

	/**
	 * 
	 * 
	 * @param part
	 * @throws Exception
	 * @메소드명 : udtQtyInfoMySubTree
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 30.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public CostPart udtQtyInfoMySubTree(CostPart part, boolean subAll, boolean isSave) throws Exception;

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 * @메소드명 : getCostFomulaTreeList
	 * @작성자 : 황정태
	 * @작성일 : 2018. 1. 31.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public ArrayList<Map<String, Object>> getCostFomulaTreeList() throws Exception;

	/**
	 * 
	 * 
	 * @param part
	 * @throws Exception
	 * @메소드명 : copyRelatedObject
	 * @작성자 : user
	 * @작성일 : 2018. 2. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public void copyRelatedObject(List<Map<String, String>> targetList) throws Exception;

	/**
	 * 
	 * 
	 * @param partListOid
	 * @param version
	 * @throws Exception
	 * @메소드명 : copyCostCurrencyInfo
	 * @작성자 : 황정태
	 * @작성일 : 2018. 2. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public void copyCostCurrencyInfo(String partListOid, String version) throws Exception;

	/**
	 * 
	 * 
	 * @param part
	 * @return
	 * @throws Exception
	 * @메소드명 : getLastestCostQuantity
	 * @작성자 : 황정태
	 * @작성일 : 2018. 2. 7.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public List<CostQuantity> getLastestCostQuantity(CostPart part) throws Exception;

	/**
	 * 
	 * 
	 * @param partListOid
	 * @param version
	 * @throws Exception
	 * @메소드명 : transferInvest
	 * @작성자 : 황정태
	 * @작성일 : 2018. 2. 7.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public CostPart transferInvest(CostPart part) throws Exception;

	/**
	 * 
	 * 
	 * @param oid
	 * @param version
	 * @throws Exception
	 * @메소드명 : syncCost
	 * @작성자 : 황정태
	 * @작성일 : 2018. 2. 9.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public CostPart syncCost(CostPart part) throws Exception;

	/**
	 * 
	 * 
	 * @param part
	 * @throws Exception
	 * @메소드명 : deleteRelatedObject
	 * @작성자 : user
	 * @작성일 : 2018. 2. 22.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public void deleteRelatedObject(CostPart part) throws Exception;

	/**
	 * 
	 * 
	 * @param ProjectOid
	 * @param taskOid
	 * @return
	 * @throws Exception
	 * @메소드명 : getDrStepByTask
	 * @작성자 : 황정태
	 * @작성일 : 2018. 3. 22.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public String getDrStepByTask(String ProjectOid, String taskOid) throws Exception;

	/**
	 * 
	 * 
	 * @param beforePart
	 * @param afterPart
	 * @return
	 * @throws Exception
	 * @메소드명 : getCompareCostBasicInfo
	 * @작성자 : 황정태
	 * @작성일 : 2018. 4. 3.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public List<Map<String, Object>> getCompareCostBasicInfo(CostPart beforePart, CostPart afterPart) throws Exception;

	/**
	 * 
	 * 
	 * @param oid
	 * @param partListOid
	 * @return
	 * @throws Exception
	 * @메소드명 : initCost
	 * @작성자 : 황정태
	 * @작성일 : 2018. 4. 19.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public void initCost(Map<String, Object> reqMap) throws Exception;

	/**
	 * 
	 * 
	 * @param ProjectOid
	 * @param taskOid
	 * @return
	 * @throws Exception
	 * @메소드명 : isFirstCostRequset
	 * @작성자 : 황정태
	 * @작성일 : 2018. 4. 24.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public boolean isFirstCostRequset(String ProjectOid, String taskOid) throws Exception;

	/**
	 * 
	 * 
	 * @param partOid
	 * @return
	 * @throws Exception
	 * @메소드명 : getMyExRate2Code
	 * @작성자 : user
	 * @작성일 : 2018. 5. 11.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Map<String, String> getMyExRate2Code(String partOid) throws Exception;

	public List<Map<String, Object>> getCostWorkTimeList() throws Exception;

	public Map<String, Object> saveCostWorkTime(Map<String, Object> reqMap) throws Exception;

	public void refreshCurrencyInfo(String partOid) throws Exception;

	public boolean isOpenBomEditor(String taskOid) throws Exception;

	/**
	 * <pre>
	 * @description 환율정보 가져오기 (환율코드, 값)
	 * @author dhkim
	 * @date 2019. 5. 9. 오후 2:58:28
	 * @method getCostCurrency
	 * @param part
	 * @return Map<String,String>
	 * @throws Exception
	 * </pre>
	 */
	Map<String, String> getCostCurrency(CostPart part) throws Exception;

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2019. 5. 10. 오후 2:16:48
	 * @method copyCostCurrencyInfo
	 * @param oldPart
	 * @param newPart
	 * @return List<CostCurrencyInfo>
	 * @throws Exception
	 * </pre>
	 */
	List<CostCurrencyInfo> copyCostCurrencyInfo(CostPart oldPart, CostPart newPart) throws Exception;
	
	/**
	 * 
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @메소드명 : erpBom2CostPart
	 * @작성자 : 황정태
	 * @작성일 : 2019. 5. 22.
	 * @설명 : 
	 * @수정이력 - 수정일,수정자,수정내용  					   
	 *
	 */
	
	public List<CostPart> erpBom2CostPart(Map<String, Object> paramMap) throws Exception;
	
	
	public void NinvestPartiotionUpdate(CostPart part) throws Exception;//1레벨 part 투자비 분할

}