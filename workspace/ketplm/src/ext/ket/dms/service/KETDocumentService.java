package ext.ket.dms.service;

import java.util.Map;

import e3ps.common.web.PageControl;
import ext.ket.dms.entity.KETSGDocument;
import ext.ket.dms.entity.KETSGDocumentDTO;
import wt.method.RemoteInterface;

/*********************************************************
 * @description 
 * @author dhkim
 * @date 2018. 10. 1. 오전 9:59:52
 * @Pakage ext.ket.dms.service
 * @class KETDocumentService
 *********************************************************/
@RemoteInterface
public interface KETDocumentService{

    /**
     * <pre>
     * @description 시스템 사용설명서 저장
     * @author dhkim
     * @date 2018. 10. 1. 오전 10:24:23
     * @method saveSGDocument
     * @param dto
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception 
     * </pre>
     */
    Map<String, Object> saveSGDocument(KETSGDocumentDTO dto, Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description 시스템 사용설명서 개정
     * @author dhkim
     * @date 2018. 10. 1. 오전 10:24:38
     * @method reviseSGDocument
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> reviseSGDocument(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description 시스템 사용설명서 삭제
     * @author dhkim
     * @date 2018. 10. 1. 오전 10:24:46
     * @method deleteSGDocument
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> deleteSGDocument(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description 시스템 사용설명서 조회
     * @author dhkim
     * @date 2018. 10. 1. 오후 4:19:56
     * @method findPagingList
     * @param dto
     * @return PageControl
     * @throws Exception 
     * </pre>
     */
    PageControl findPagingList(KETSGDocumentDTO dto) throws Exception;

}