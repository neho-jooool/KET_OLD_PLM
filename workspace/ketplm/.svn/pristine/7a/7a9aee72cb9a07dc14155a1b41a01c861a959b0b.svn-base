package ext.ket.cost.service;

import java.util.List;
import java.util.Map;

import wt.fc.Persistable;
import wt.method.RemoteInterface;
import e3ps.common.web.PageControl;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import ext.ket.cost.entity.CSInfo;
import ext.ket.cost.entity.CostAnalysis;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostReport;
import ext.ket.cost.entity.CostTrackingDTO;
import ext.ket.cost.entity.costErpInterfaceDTO;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2017. 11. 17. 오전 10:49:30
 * @Pakage ext.ket.project.cost.service
 * @class CostBomService
 *********************************************************/
@RemoteInterface
public interface CostService {

    void reCalcReduceCost(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 11. 17. 오전 10:51:25
     * @method saveCostPart
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> saveCostPart(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 30. 오후 3:04:55
     * @method saveCostPartInfo
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> saveCostPartInfo(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 8. 오전 11:46:14
     * @method saveFormula
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    Map<String, Object> saveCostFormula(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 8. 오후 1:46:17
     * @method getCostRootList
     * @param reqMap
     * @return List<Persistable>
     * @throws Exception
     * </pre>
     */
    List<Persistable> getCostRootList(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 31. 오후 1:37:34
     * @method getCostRootMapList
     * @param reqMap
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    List<Map<String, Object>> getCostRootMapList(Map<String, Object> reqMap) throws Exception;

    /**
     * 
     * 
     * @param reqMap
     * @return
     * @throws Exception
     * @메소드명 : saveCostPartType
     * @작성자 : 황정태
     * @작성일 : 2017. 12. 26.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    Map<String, Object> saveCostPartType(Map<String, Object> reqMap) throws Exception;

    /**
     * 
     * 
     * @param reqMap
     * @return
     * @throws Exception
     * @메소드명 : getCostPartTypeTreeList
     * @작성자 : 황정태
     * @작성일 : 2017. 12. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */

    List<Map<String, Object>> getCostPartTypeTreeList() throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 3. 오전 10:54:57
     * @method saveNMetalCostStdInfo
     * @param reqMap
     * </pre>
     * 
     * @throws Exception
     */
    Map<String, Object> saveCSInfo(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 31. 오후 1:37:24
     * @method reviseCSInfo
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> reviseCSInfo(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 31. 오후 1:37:29
     * @method deleteCSInfo
     * @param csInfo
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> deleteCSInfo(CSInfo csInfo) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 31. 오후 2:56:49
     * @method createCasePart
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> createCasePart(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 2. 1. 오전 11:34:43
     * @method saveCasePart
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     * 
     * @throws Exception
     */
    Map<String, Object> saveCasePart(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 2. 7. 오전 11:01:37
     * @method saveCostAnalysis
     * @param part
     * @return List<CostAnalysis>
     * @throws Exception
     * </pre>
     */
    List<CostAnalysis> saveCostAnalysis(CostPart part) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 2. 8. 오후 2:45:01
     * @method saveCostAnalysisInfo
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> saveCostAnalysisInfo(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 2. 26. 오후 1:27:14
     * @method saveCostInvestInfo
     * @param reqMap
     * @return
     * @throws Exception Map<String,Object>
     * </pre>
     */
    Map<String, Object> saveCostInvestInfo(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description map Parameter partListOid, oid (COSTPART)
     * @author dhkim
     * @date 2018. 2. 26. 오후 1:27:12
     * @method deleteCostPart
     * @param reqMap
     * @throws Exception void
     * </pre>
     */
    void deleteCostPart(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description COST BOM, 제품 삭제 로직 PARAMETER : partListOid, oid (COSTPART)
     * @author dhkim
     * @date 2019. 4. 25. 오전 11:10:04
     * @method deleteCostPartNonTrx
     * @param reqMap
     * @throws Exception
     * </pre>
     */
    void deleteCostPartNonTrx(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 9. 오전 10:39:05
     * @method copyNewVersionPart
     * @param task
     * @return E3PSTask
     * @throws Exception
     * </pre>
     */
    E3PSTask copyNewVersionPart(E3PSTask task) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 13. 오후 5:17:57
     * @method reCalculateCostPart
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> reCalculateCostPart(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 19. 오전 9:50:14
     * @method saveCostReport
     * @param reqMap
     * @return CostReport
     * @throws Exception
     * </pre>
     */
    CostReport saveCostReport(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 8. 22. 오전 11:37:16
     * @method findPaging
     * @param paramObject
     * @return PageControl
     * @throws Exception
     * </pre>
     */
    PageControl findPaging(CostTrackingDTO paramObject) throws Exception;

    /**
     * <pre>
     * @description 원가 수식 개정 처리
     * @author dhkim
     * @date 2018. 8. 22. 오전 11:37:38
     * @method reviseCostFormula
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> reviseCostFormula(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description 제품 원가 수식버전 변경 처리
     * @author dhkim
     * @date 2018. 9. 14. 오전 10:18:07
     * @method changeFormulaVersion
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> changeFormulaVersion(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description 원가 수식 개정 취소 처리
     * @author dhkim
     * @date 2018. 10. 17. 오후 1:36:47
     * @method reviseCancel
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> reviseCancel(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description CASE 삭제
     * @author dhkim
     * @date 2018. 10. 30. 오후 3:22:57
     * @method deleteCasePart
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> deleteCasePart(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 4. 30. 오후 4:53:19
     * @method loadCostFormula
     * @throws Exception
     * </pre>
     */
    void loadCostFormula() throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 4. 30. 오후 4:53:26
     * @method loadCostPartTypeInfo
     * @throws Exception
     * </pre>
     */
    void loadCostPartTypeInfo() throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 4. 30. 오후 4:54:21
     * @method getProjectPartList
     * @param partNo
     * @param master
     * @return List<CostPart>
     * </pre>
     */
    List<CostPart> getProjectPartList(String partNo, E3PSProjectMaster master) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 4. 30. 오후 5:07:35
     * @method getProjectPartMapList
     * @param partNo
     * @param project
     * @return
     * @throws Exception List<Map<String,Object>>
     * </pre>
     */
    List<Map<String, Object>> getProjectPartMapList(String partNo, E3PSProject project) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 5. 2. 오후 2:34:19
     * @method getPartE3PSTask
     * @param part
     * @return E3PSTask
     * @throws Exception
     * </pre>
     */
    E3PSTask getPartE3PSTask(CostPart part) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 5. 7. 오전 11:13:33
     * @method bomCopyAdd
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> bomCopyAdd(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 5. 7. 오후 4:27:14
     * @method getCostProductList
     * @param master
     * @return List<CostPart>
     * @throws Exception
     * </pre>
     */
    List<CostPart> getCostProductList(E3PSProjectMaster master) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 5. 7. 오후 4:31:25
     * @method copyNewProduct
     * @param task
     * @param copyProduct
     * @return E3PSTask
     * @throws Exception
     * </pre>
     */
    E3PSTask copyNewProduct(E3PSTask task, List<CostPart> copyProduct) throws Exception;

    /**
     * 
     * <pre>
     * @description  
     * @author Administrator
     * @date 2019. 9. 3. 오전 8:00:50
     * @method setCostPartCSInfo
     * @param part
     * @param costCurrency
     * @return
     * @throws Exception CostPart
     * </pre>
     */
    CostPart setCostPartCSInfo(CostPart part, Map<String, String> costCurrency) throws Exception;

    PageControl findInterfacePaging(costErpInterfaceDTO paramObject) throws Exception;
}