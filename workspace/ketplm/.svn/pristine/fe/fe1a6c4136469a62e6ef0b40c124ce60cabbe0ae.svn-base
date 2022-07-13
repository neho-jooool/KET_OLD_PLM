package ext.ket.invest.service;

import java.util.Map;

import e3ps.common.web.PageControl;
import ext.ket.invest.entity.KETInvestMasterDTO;
import ext.ket.invest.entity.KETInvestTaskDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface InvestService {
    /**
     * <pre>
     * @description 고객 요구사항 저장
     * @author dhkim
     * @date 2018. 5. 25. 오후 12:10:12
     * @method saveInvestMaster
     * @param investTO
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception 
     * </pre>
     */
    Map<String, Object> saveInvestMaster(KETInvestMasterDTO investTO, Map<String, Object> reqMap) throws Exception;
    
    
    /**
     * 
     * 
     * @param im
     * @return
     * @throws Exception
     * @메소드명 : findPagingList
     * @작성자 : user
     * @작성일 : 2019. 9. 9.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */

    public PageControl findPagingList(KETInvestMasterDTO im) throws Exception;
    
    /**
     * 
     * 
     * @param itDTO
     * @param reqMap
     * @return
     * @throws Exception
     * @메소드명 : saveInvestTask
     * @작성자 : user
     * @작성일 : 2019. 9. 18.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public Map<String, Object> saveInvestTask(KETInvestTaskDTO itDTO, Map<String, Object> reqMap) throws Exception;
    
    /**
     * 
     * 
     * @param reqMap
     * @return
     * @throws Exception
     * @메소드명 : deleteInvest
     * @작성자 : user
     * @작성일 : 2019. 9. 23.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public Map<String, Object> deleteInvest(Map<String, Object> reqMap) throws Exception;
}
