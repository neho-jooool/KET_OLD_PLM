package ext.ket.shared.code.service;

import java.util.List;
import java.util.Map;

import wt.method.RemoteInterface;
import ext.ket.shared.code.entity.NumberCodeDTO;
import ext.ket.shared.service.CommonServiceInterface;

/**
 * @클래스명 : CodeService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@RemoteInterface
public interface CodeService extends CommonServiceInterface<NumberCodeDTO, NumberCodeDTO> {

    public void loadNumberCode() throws Exception;

    public void loadCarNumberCode() throws Exception;

    public List<Map<String, Object>> getNumberCodeList(Map<String, Object> parameter) throws Exception;

    public List<Map<String, Object>> getCarNumberCodeList(Map<String, Object> parameter) throws Exception;

    public String getNumberCodeValue(String codeType, String code, String locale) throws Exception;
    
    public String getNumberCodeDescription(String codeType, String code, String locale) throws Exception;
    
    public String getNumberCodeCodeByName(String codeType, String name, String locale) throws Exception;
}
