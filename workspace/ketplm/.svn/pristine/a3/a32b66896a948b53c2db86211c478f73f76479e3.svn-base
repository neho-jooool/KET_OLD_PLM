package ext.ket.part.spec.service;

import java.util.List;

import wt.method.RemoteInterface;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.entity.dto.PartAttributeDTO;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;

@RemoteInterface
public interface PartSpecService { // extends CommonServiceInterface<PartAttributeDTO, KETPartAttribute> {

    // insert ( migration )
    public KETPartAttribute insert(KETPartAttribute attr, boolean existNotInsert) throws Exception;

    // public boolean insertAll(List<KETPartAttribute> attrList) throws Exception;

    // 전체 쿼리 ( migration 테스트 확인 )
    public List<KETPartAttribute> searchFullList() throws Exception;

    // 분류의 속성 저장( migration 테스트 )
    public boolean insertPartAttrLinkList(PartClassAttrLinkDTO partClassAttrLinkDTO) throws Exception;

    // @분류체계 부품 속성 리스트 정보가져오기 - 기등록된 정보 : 선택된 분류의 속성
    public List<PartClassAttrLinkDTO> searchSelectedList(String clazOid) throws Exception;

    // @부품 전체 속성리스트 정보가져오기 - 기존 것은 체크 함
    public List<PartAttributeDTO> searchPartAttrList(String attrOidArray) throws Exception;

    // @분류체계 부품 속성 추가 삭제 정보 저장
    public void savePartClazAttrLinkList(List<PartClassAttrLinkDTO> partClazAttrLinkList, String clazOid) throws Exception;
    
    // @분류체계 프로젝트, 중량 필수 체크 - 등록 시점만
    public boolean[] checkProjectWeightEss(String clazOid) throws Exception;

}
