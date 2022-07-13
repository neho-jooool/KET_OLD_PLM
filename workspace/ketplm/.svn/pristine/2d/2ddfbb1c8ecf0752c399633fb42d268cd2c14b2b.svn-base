package ext.ket.shared.query;

import java.util.List;

import wt.fc.Persistable;
import wt.fc.QueryResult;

/**
 * 
 * @클래스명 : SimpleQuerySpec
 * @작성자 : tklee
 * @작성일 : 2014. 7. 21.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public interface SimpleQuerySpec {

    // ///////////////////////////////////////////////////////////////////////////
    // One Class - Get QuerySpec
    // ///////////////////////////////////////////////////////////////////////////
    public <E> List<E> queryForListByOneClass(Class classSource, QueryStrategy strategy) throws Exception;

    public <E> List<E> queryForListByOneClass(Class classSource, boolean withAdminAuth, boolean advancedQuery, QueryStrategy strategy) throws Exception;

    public QueryResult getQueryResultByOneClass(Class classSource, QueryStrategy strategy) throws Exception;

    public QueryResult getQueryResultByOneClass(Class classSource, boolean withAdminAuth, boolean advancedQuery, QueryStrategy strategy) throws Exception;

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET ROLE A LIST BY ROLE B
    // ///////////////////////////////////////////////////////////////////////////
    public <E> List<E> queryForListAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB) throws Exception;

    public <E> List<E> queryForListAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, QueryLinkStrategy strategy) throws Exception;

    public <E> List<E> queryForListAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery) throws Exception;

    public <E> List<E> queryForListAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy)
	    throws Exception;

    public QueryResult getQueryResultByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB) throws Exception;

    public QueryResult getQueryResultByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, QueryLinkStrategy strategy) throws Exception;

    public QueryResult getQueryResultByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy)
	    throws Exception;

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET ROLE B LIST BY ROLE A
    // ///////////////////////////////////////////////////////////////////////////
    public <E> List<E> queryForListBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA) throws Exception;

    public <E> List<E> queryForListBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, QueryLinkStrategy strategy) throws Exception;

    public <E> List<E> queryForListBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery) throws Exception;

    public <E> List<E> queryForListBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy)
	    throws Exception;

    public QueryResult getQueryResultByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA) throws Exception;

    public QueryResult getQueryResultByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, QueryLinkStrategy strategy) throws Exception;

    public QueryResult getQueryResultByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy)
	    throws Exception;

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET LINK LIST BY ROLE B
    // ///////////////////////////////////////////////////////////////////////////
    public <E> List<E> queryForListLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB) throws Exception;

    public <E> List<E> queryForListLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, QueryBStrategy strategy) throws Exception;

    public <E> List<E> queryForListLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery) throws Exception;

    public <E> List<E> queryForListLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryBStrategy strategy) throws Exception;

    // link index = 0, B index = 1
    public QueryResult getQueryResultLinkBByRoleB(Class classLink, Class classB, Persistable objectRoleB) throws Exception;

    // link index = 0, B index = 1
    public QueryResult getQueryResultLinkBByRoleB(Class classLink, Class classB, Persistable objectRoleB, QueryBStrategy strategy) throws Exception;

    // link index = 0, B index = 1
    public QueryResult getQueryResultLinkBByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryBStrategy strategy) throws Exception;

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET LINK LIST BY ROLE A
    // ///////////////////////////////////////////////////////////////////////////
    public <E> List<E> queryForListLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA) throws Exception;

    public <E> List<E> queryForListLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery) throws Exception;

    public <E> List<E> queryForListLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, QueryAStrategy strategy) throws Exception;

    public <E> List<E> queryForListLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryAStrategy strategy) throws Exception;

    public QueryResult getQueryResultALinkByRoleA(Class classA, Class classLink, Persistable objectRoleA) throws Exception;

    public QueryResult getQueryResultALinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, QueryAStrategy strategy) throws Exception;

    public QueryResult getQueryResultALinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryAStrategy strategy) throws Exception;

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET LINK LIST BY ROLE A + ROLE B
    // ///////////////////////////////////////////////////////////////////////////
    public <E> List<E> queryForListLinkByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB) throws Exception;

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - Object with two instance
    // ///////////////////////////////////////////////////////////////////////////

    public <T> T queryForObjectLinkByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB) throws Exception;

    public <T> T queryForObjectLinkByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery)
	    throws Exception;

    // ///////////////////////////////////////////////////////////////////////////
    // List중 1개만 가져오는 method ( 1:1 관계를 위해서 만듬) - 없으면 NULL 반환 됨
    // ///////////////////////////////////////////////////////////////////////////
    // ONE
    public <T> T queryForFirstByOneClass(Class classSource, QueryStrategy strategy) throws Exception;

    public <T> T queryForFirstByOneClass(Class classSource, boolean withAdminAuth, boolean advancedQuery, QueryStrategy strategy) throws Exception;

    // ROLE A LIST BY ROLE B
    public <T> T queryForFirstAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB) throws Exception;

    public <T> T queryForFirstAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, QueryLinkStrategy strategy) throws Exception;

    public <T> T queryForFirstAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery) throws Exception;

    public <T> T queryForFirstAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy) throws Exception;

    // ROLE B LIST BY ROLE A
    public <T> T queryForFirstBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA) throws Exception;

    public <T> T queryForFirstBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, QueryLinkStrategy strategy) throws Exception;

    public <T> T queryForFirstBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery) throws Exception;

    public <T> T queryForFirstBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy) throws Exception;

    // LINK BY ROLE B
    public <T> T queryForFirstLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB) throws Exception;

    public <T> T queryForFirstLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, QueryBStrategy strategy) throws Exception;

    public <T> T queryForFirstLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery) throws Exception;

    public <T> T queryForFirstLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryBStrategy strategy) throws Exception;

    // LINK BY ROLE A
    public <T> T queryForFirstLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA) throws Exception;

    public <T> T queryForFirstLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery) throws Exception;

    public <T> T queryForFirstLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, QueryAStrategy strategy) throws Exception;

    public <T> T queryForFirstLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryAStrategy strategy) throws Exception;

    // Link By Role A, B
    public <T> T queryForFirstLinkByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB) throws Exception;

}