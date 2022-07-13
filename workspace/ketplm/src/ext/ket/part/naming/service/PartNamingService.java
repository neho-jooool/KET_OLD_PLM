package ext.ket.part.naming.service;

import java.util.List;
import java.util.Locale;

import wt.method.RemoteInterface;
import ext.ket.part.entity.KETPartNaming;
import ext.ket.part.entity.dto.PartNamingDTO;

@RemoteInterface
public interface PartNamingService { 

    // insert ( migration )
    public KETPartNaming insert(KETPartNaming partNaming, boolean existNotInsert) throws Exception;
    
    // @부품 전체 속성리스트 정보가져오기 - 기존 것은 체크 함
    public List<PartNamingDTO> searchFullList(Locale locale) throws Exception;
    
    // @품명코드 리스트 중 선택된 품명코드 정보 조회
    public PartNamingDTO searchSelectedPartNaming(String partNamingOid, Locale locale)throws Exception;
    
    // @품명 저장 ( 등록, 수정 )
    public String savePartNaming(PartNamingDTO partNamingDTO)throws Exception;
    
    // @품명코드 삭제
    public void deletePartNaming(String partNamingOid)throws Exception;
    

}
