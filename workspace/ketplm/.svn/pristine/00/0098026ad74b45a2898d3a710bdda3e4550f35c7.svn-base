package ext.ket.shared.service;

import java.util.List;

import e3ps.common.web.PageControl;

/**
 * 모든 Service Interface에서 상속받을 Interface
 * 
 * <pre>
 * D : DTO 객체
 * E : Entity 객체
 * </pre>
 * 
 * @클래스명 : CommonServiceInterface
 * @작성자 : sw775.park
 * @작성일 : 2014. 5. 30.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public interface CommonServiceInterface<D, E> {

    /**
     * 조회 및 검색
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : find
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<E> find(D paramObject) throws Exception;

    /**
     * 조회 및 검색(Paging)
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : findPaging
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public PageControl findPaging(D paramObject) throws Exception;

    /**
     * 최초등록
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : save
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public E save(D paramObject) throws Exception;

    /**
     * 수정
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : modify
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public E modify(D paramObject) throws Exception;

    /**
     * 삭제
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : delete
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public E delete(D paramObject) throws Exception;
}
