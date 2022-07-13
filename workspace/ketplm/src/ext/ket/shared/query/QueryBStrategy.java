package ext.ket.shared.query;

import wt.query.QuerySpec;

/**
 * 
 * @클래스명 : QueryBStrategy
 * @작성자 : tklee
 * @작성일 : 2014. 7. 21.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public interface QueryBStrategy {

    void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception;

}
