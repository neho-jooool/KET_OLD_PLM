package ext.ket.shared.query;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;

import wt.fc.Persistable;
import wt.fc.QueryResult;

/**
 * 
 * @클래스명 : DefaultSimpleQuerySpec
 * @작성자 : tklee
 * @작성일 : 2014. 7. 21.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */

public final class DefaultSimpleQuerySpec extends AbsSimpleQuerySpec implements SimpleQuerySpec {

    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    // ///////////////////////////////////////////////////////////////////////////
    // One Class - Get QuerySpec
    // ///////////////////////////////////////////////////////////////////////////
    public <E> List<E> queryForListByOneClass(Class classSource, QueryStrategy strategy) throws Exception {
	return queryForListByOneClass(classSource, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy);
    }

    public <E> List<E> queryForListByOneClass(Class classSource, boolean withAdminAuth, boolean advancedQuery, QueryStrategy strategy) throws Exception {
	return queryForListByOneClassOnly(classSource, withAdminAuth, advancedQuery, strategy);
    }

    public QueryResult getQueryResultByOneClass(Class classSource, QueryStrategy strategy) throws Exception {
	return getQueryResultByOneClass(classSource, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy);
    }

    public QueryResult getQueryResultByOneClass(Class classSource, boolean withAdminAuth, boolean advancedQuery, QueryStrategy strategy) throws Exception {
	return getQueryResultByOneClassOnly(classSource, withAdminAuth, advancedQuery, strategy);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET ROLE A LIST BY ROLE B
    // ///////////////////////////////////////////////////////////////////////////
    @Override
    public <E> List<E> queryForListAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB) throws Exception {
	return queryForListAByRoleB(classA, classLink, classB, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT);
    }

    @Override
    public <E> List<E> queryForListAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery) throws Exception {

	if (objectRoleB == null)
	    throw new Exception("# queryForListAByRoleB : objectRoleB is null!!");

	Persistable objectRoleA = null;
	QueryLinkStrategy strategy = null;
	return queryForListByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    @Override
    public <E> List<E> queryForListAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, QueryLinkStrategy strategy) throws Exception {
	return queryForListAByRoleB(classA, classLink, classB, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy);
    }

    @Override
    public <E> List<E> queryForListAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy)
	    throws Exception {

	if (objectRoleB == null)
	    throw new Exception("# queryForListAByRoleB : objectRoleB is null!!");

	Persistable objectRoleA = null;
	return queryForListByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    @Override
    public QueryResult getQueryResultByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB) throws Exception {

	if (objectRoleB == null)
	    throw new Exception("# getQueryResultByRoleB : objectRoleB is null!!");

	QueryLinkStrategy strategy = null;
	Persistable objectRoleA = null;
	return getQueryResultByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy, IS_OUTPUT_A_DEFAULT, IS_OUTPUT_LINK_DEFAULT,
	        IS_OUTPUT_B_DEFAULT);
    }

    @Override
    public QueryResult getQueryResultByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, QueryLinkStrategy strategy) throws Exception {

	if (objectRoleB == null)
	    throw new Exception("# getQueryResultByRoleB : objectRoleB is null!!");

	Persistable objectRoleA = null;
	return getQueryResultByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy, IS_OUTPUT_A_DEFAULT, IS_OUTPUT_LINK_DEFAULT,
	        IS_OUTPUT_B_DEFAULT);
    }

    @Override
    public QueryResult getQueryResultByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy)
	    throws Exception {

	if (objectRoleB == null)
	    throw new Exception("# getQueryResultByRoleB : objectRoleB is null!!");

	Persistable objectRoleA = null;
	return getQueryResultByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, withAdminAuth, advancedQuery, strategy, IS_OUTPUT_A_DEFAULT, IS_OUTPUT_LINK_DEFAULT, IS_OUTPUT_B_DEFAULT);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET LINK LIST BY ROLE B
    // ///////////////////////////////////////////////////////////////////////////
    @Override
    public <E> List<E> queryForListLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB) throws Exception {
	return queryForListLinkByRoleB(classLink, classB, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT);
    }

    @Override
    public <E> List<E> queryForListLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery) throws Exception {
	if (objectRoleB == null)
	    throw new Exception("# queryForListLinkByRoleB : objectRoleB is null!!");

	QueryBStrategy strategy = null;
	return queryForListByLinkRoleB(classLink, classB, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    @Override
    public <E> List<E> queryForListLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, QueryBStrategy strategy) throws Exception {
	return queryForListLinkByRoleB(classLink, classB, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy);
    }

    @Override
    public <E> List<E> queryForListLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryBStrategy strategy) throws Exception {
	if (objectRoleB == null)
	    throw new Exception("# queryForListLinkByRoleB : objectRoleB is null!!");

	return queryForListByLinkRoleB(classLink, classB, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    @Override
    public QueryResult getQueryResultLinkBByRoleB(Class classLink, Class classB, Persistable objectRoleB) throws Exception {

	if (objectRoleB == null)
	    throw new Exception("# getQueryResultLinkBByRoleB : objectRoleB is null!!");

	QueryBStrategy strategy = null;
	Persistable objectLink = null;
	return getQueryResultByLinkRoleB(classLink, classB, objectLink, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy, IS_OUTPUT_LINK_DEFAULT, IS_OUTPUT_B_DEFAULT);
    }

    @Override
    public QueryResult getQueryResultLinkBByRoleB(Class classLink, Class classB, Persistable objectRoleB, QueryBStrategy strategy) throws Exception {

	if (objectRoleB == null)
	    throw new Exception("# getQueryResultLinkBByRoleB : objectRoleB is null!!");

	Persistable objectLink = null;
	return getQueryResultByLinkRoleB(classLink, classB, objectLink, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy, IS_OUTPUT_LINK_DEFAULT, IS_OUTPUT_B_DEFAULT);
    }

    @Override
    public QueryResult getQueryResultLinkBByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryBStrategy strategy) throws Exception {

	if (objectRoleB == null)
	    throw new Exception("# getQueryResultLinkBByRoleB : objectRoleB is null!!");

	Persistable objectLink = null;
	return getQueryResultByLinkRoleB(classLink, classB, objectLink, objectRoleB, withAdminAuth, advancedQuery, strategy, IS_OUTPUT_LINK_DEFAULT, IS_OUTPUT_B_DEFAULT);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET LINK LIST BY ROLE A
    // ///////////////////////////////////////////////////////////////////////////

    @Override
    public <E> List<E> queryForListLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA) throws Exception {
	return queryForListLinkByRoleA(classA, classLink, objectRoleA, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT);
    }

    @Override
    public <E> List<E> queryForListLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery) throws Exception {
	if (objectRoleA == null)
	    throw new Exception("# queryForListLinkByRoleA : objectRoleA is null!!");

	QueryAStrategy strategy = null;
	return queryForListByLinkRoleA(classA, classLink, objectRoleA, withAdminAuth, advancedQuery, strategy);
    }

    @Override
    public <E> List<E> queryForListLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, QueryAStrategy strategy) throws Exception {
	return queryForListLinkByRoleA(classA, classLink, objectRoleA, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy);
    }

    @Override
    public <E> List<E> queryForListLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryAStrategy strategy) throws Exception {
	if (objectRoleA == null)
	    throw new Exception("# queryForListLinkByRoleA : objectRoleA is null!!");

	return queryForListByLinkRoleA(classA, classLink, objectRoleA, withAdminAuth, advancedQuery, strategy);
    }

    @Override
    public QueryResult getQueryResultALinkByRoleA(Class classA, Class classLink, Persistable objectRoleA) throws Exception {

	if (objectRoleA == null)
	    throw new Exception("# getQueryResultALinkByRoleA : objectRoleA is null!!");

	QueryAStrategy strategy = null;
	Persistable objectLink = null;
	return getQueryResultByLinkRoleA(classA, classLink, objectRoleA, objectLink, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy, IS_OUTPUT_LINK_DEFAULT, IS_OUTPUT_B_DEFAULT);
    }

    @Override
    public QueryResult getQueryResultALinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, QueryAStrategy strategy) throws Exception {

	if (objectRoleA == null)
	    throw new Exception("# getQueryResultALinkByRoleA : objectRoleA is null!!");

	Persistable objectLink = null;
	return getQueryResultByLinkRoleA(classA, classLink, objectRoleA, objectLink, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy, IS_OUTPUT_LINK_DEFAULT, IS_OUTPUT_B_DEFAULT);
    }

    @Override
    public QueryResult getQueryResultALinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryAStrategy strategy) throws Exception {

	if (objectRoleA == null)
	    throw new Exception("# getQueryResultALinkByRoleA : objectRoleA is null!!");

	Persistable objectLink = null;
	return getQueryResultByLinkRoleA(classA, classLink, objectRoleA, objectLink, withAdminAuth, advancedQuery, strategy, IS_OUTPUT_LINK_DEFAULT, IS_OUTPUT_B_DEFAULT);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET LINK LIST BY ROLE A
    // ///////////////////////////////////////////////////////////////////////////

    @Override
    public <E> List<E> queryForListLinkByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB) throws Exception {
	QueryLinkStrategy strategy = null;
	return queryForListByRoleABOnlyLink(classA, classLink, classB, objectRoleA, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET ROLE B LIST BY ROLE A
    // ///////////////////////////////////////////////////////////////////////////
    @Override
    public <E> List<E> queryForListBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA) throws Exception {
	return queryForListBByRoleA(classA, classLink, classB, objectRoleA, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT);
    }

    @Override
    public <E> List<E> queryForListBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery) throws Exception {

	if (objectRoleA == null)
	    throw new Exception("# queryForListBByRoleA : objectRoleA is null!!");

	Persistable objectRoleB = null;
	QueryLinkStrategy strategy = null;
	return queryForListByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    @Override
    public <E> List<E> queryForListBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, QueryLinkStrategy strategy) throws Exception {
	return queryForListBByRoleA(classA, classLink, classB, objectRoleA, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy);
    }

    @Override
    public <E> List<E> queryForListBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy)
	    throws Exception {

	if (objectRoleA == null)
	    throw new Exception("# queryForListBByRoleA : objectRoleA is null!!");

	Persistable objectRoleB = null;
	return queryForListByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    @Override
    public QueryResult getQueryResultByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA) throws Exception {

	if (objectRoleA == null)
	    throw new Exception("# getQueryResultByRoleA : objectRoleA is null!!");

	QueryLinkStrategy strategy = null;
	Persistable objectRoleB = null;
	return getQueryResultByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy, IS_OUTPUT_A_DEFAULT, IS_OUTPUT_LINK_DEFAULT,
	        IS_OUTPUT_B_DEFAULT);
    }

    @Override
    public QueryResult getQueryResultByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, QueryLinkStrategy strategy) throws Exception {

	if (objectRoleA == null)
	    throw new Exception("# getQueryResultByRoleA : objectRoleA is null!!");

	Persistable objectRoleB = null;
	return getQueryResultByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT, strategy, IS_OUTPUT_A_DEFAULT, IS_OUTPUT_LINK_DEFAULT,
	        IS_OUTPUT_B_DEFAULT);
    }

    @Override
    public QueryResult getQueryResultByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy)
	    throws Exception {

	if (objectRoleA == null)
	    throw new Exception("# getQueryResultByRoleA : objectRoleA is null!!");

	Persistable objectRoleB = null;
	return getQueryResultByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, withAdminAuth, advancedQuery, strategy, IS_OUTPUT_A_DEFAULT, IS_OUTPUT_LINK_DEFAULT, IS_OUTPUT_B_DEFAULT);
    }

    @Override
    public <T> T queryForObjectLinkByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB) throws Exception {
	return queryForObjectLinkByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, WITH_ADMIN_AUTH_DEFAULT, ADVANCED_QUERY_DEFAULT);
    }

    @Override
    public <T> T queryForObjectLinkByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery)
	    throws Exception {

	QueryLinkStrategy strategy = null;
	T t = null;

	try {

	    if (objectRoleA == null || objectRoleB == null)
		throw new Exception("Query role Object is null!!");

	    boolean isOutputA = false;
	    boolean isOutputLink = true;
	    boolean isOutputB = false;

	    QueryResult queryResult = getQueryResultByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, withAdminAuth, advancedQuery, strategy, isOutputA, isOutputLink, isOutputB);

	    if (queryResult == null || (queryResult.size() < 0)) {
		log.debug("result is null!!");
	    } else if (queryResult.size() > 0) {

		if (queryResult.hasMoreElements()) {

		    Object[] objs = (Object[]) queryResult.nextElement();
		    log.debug("objs : " + objs);

		    t = (T) objs[0];
		    log.debug("objItem : " + t);
		}
	    }

	} catch (Exception err) {
	    log.error(ExceptionUtils.getFullStackTrace(err));
	    throw err;
	} finally {
	}

	return t;
    }

    // //////////////////////////////
    // test => if( 1 : 1) return first value
    // but, (size == 0) reutrn null;
    // /////////////////////////////
    @Override
    public <T> T queryForFirstByOneClass(Class classSource, QueryStrategy strategy) throws Exception {
	List<T> list = queryForListByOneClass(classSource, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstByOneClass(Class classSource, boolean withAdminAuth, boolean advancedQuery, QueryStrategy strategy) throws Exception {
	List<T> list = queryForListByOneClass(classSource, withAdminAuth, advancedQuery, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB) throws Exception {
	List<T> list = queryForListAByRoleB(classA, classLink, classB, objectRoleB);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, QueryLinkStrategy strategy) throws Exception {
	List<T> list = queryForListAByRoleB(classA, classLink, classB, objectRoleB, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery) throws Exception {
	List<T> list = queryForListAByRoleB(classA, classLink, classB, objectRoleB, withAdminAuth, advancedQuery);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstAByRoleB(Class classA, Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy) throws Exception {
	List<T> list = queryForListAByRoleB(classA, classLink, classB, objectRoleB, withAdminAuth, advancedQuery, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA) throws Exception {
	List<T> list = queryForListBByRoleA(classA, classLink, classB, objectRoleA);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, QueryLinkStrategy strategy) throws Exception {
	List<T> list = queryForListBByRoleA(classA, classLink, classB, objectRoleA, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery) throws Exception {
	List<T> list = queryForListBByRoleA(classA, classLink, classB, objectRoleA, withAdminAuth, advancedQuery);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstBByRoleA(Class classA, Class classLink, Class classB, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryLinkStrategy strategy) throws Exception {
	List<T> list = queryForListBByRoleA(classA, classLink, classB, objectRoleA, withAdminAuth, advancedQuery, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB) throws Exception {
	List<T> list = queryForListLinkByRoleB(classLink, classB, objectRoleB);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, QueryBStrategy strategy) throws Exception {
	List<T> list = queryForListLinkByRoleB(classLink, classB, objectRoleB, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery) throws Exception {
	List<T> list = queryForListLinkByRoleB(classLink, classB, objectRoleB, withAdminAuth, advancedQuery);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstLinkByRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryBStrategy strategy) throws Exception {
	List<T> list = queryForListLinkByRoleB(classLink, classB, objectRoleB, withAdminAuth, advancedQuery, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA) throws Exception {
	List<T> list = queryForListLinkByRoleA(classA, classLink, objectRoleA);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery) throws Exception {
	List<T> list = queryForListLinkByRoleA(classA, classLink, objectRoleA, withAdminAuth, advancedQuery);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, QueryAStrategy strategy) throws Exception {
	List<T> list = queryForListLinkByRoleA(classA, classLink, objectRoleA, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstLinkByRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryAStrategy strategy) throws Exception {
	List<T> list = queryForListLinkByRoleA(classA, classLink, objectRoleA, withAdminAuth, advancedQuery, strategy);
	return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public <T> T queryForFirstLinkByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB) throws Exception {
	List<T> list = queryForListLinkByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB);
	return list.size() == 0 ? null : list.get(0);
    }

}
