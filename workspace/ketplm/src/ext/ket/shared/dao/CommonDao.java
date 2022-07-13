package ext.ket.shared.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @클래스명 : CommonDao
 * @작성자 : sw775.park
 * @작성일 : 2014. 5. 8.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public interface CommonDao {

    public int delete(String statementName, Object obj);

    @SuppressWarnings("rawtypes")
    public int deleteAll(String statementName, Map paramMap);

    @SuppressWarnings("rawtypes")
    public List find(String statementName);

    @SuppressWarnings("rawtypes")
    public List find(String statementName, Map paramMap);

    @SuppressWarnings("rawtypes")
    public List find(String statementName, Object obj);

    /**
     * Paging list
     * 
     * <pre>
     * -- 아래와 같은 형식으로 query를 mapping 합니다.
     * SELECT * 
     *   FROM(
     *         SELECT ROWNUM as rnum, A.aaa, A.bbb, 블라블라~ 
     *           FROM TABLE A
     *          WHERE A.USER_NM like #{searchText}||'%'
     * )WHERE rnum >= #{offSet} AND rnum < #{limit}
     * </pre>
     * 
     * @param statementName
     * @param paramMap
     * @param paging
     *            index(default=1)
     * @param 화면에
     *            보여줄 레코드 사이즈
     * @return
     * @메소드명 : findPaging
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 17.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("rawtypes")
    public List findPaging(String statementName, Map paramMap, int pageIndex, int pageSize);

    /**
     * Paging list 아래와 같은 형식으로 테스트 합니다.
     * 
     * <pre>
     * -- 아래와 같은 형식으로 query를 mapping 합니다.
     * SELECT * 
     *   FROM(
     *         SELECT ROWNUM as rnum, A.aaa, A.bbb, 블라블라~ 
     *           FROM TABLE A
     *          WHERE A.USER_NM like #{searchText}||'%'
     * )WHERE rnum >= #{offSet} AND rnum < #{limit}
     * </pre>
     * 
     * @param statementName
     * @param obj
     * @param paging
     *            index(default=1)
     * @param 화면에
     *            보여줄 레코드 사이즈
     * @return
     * @메소드명 : findPaging
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 17.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("rawtypes")
    public List findPaging(String statementName, Object obj, int pageIndex, int pageSize);

    public Object get(String statementName);

    @SuppressWarnings("rawtypes")
    public Object get(String statementName, Map pkMap);

    public Object get(String statementName, Serializable id);

    @SuppressWarnings("rawtypes")
    public int getTotalRecordCount(String statementName, Map paramMap);

    public int getTotalRecordCount(String statementName, Object map);

    public void insert(String statementName, Object obj);

    public int update(String statementName, Object obj);

}
