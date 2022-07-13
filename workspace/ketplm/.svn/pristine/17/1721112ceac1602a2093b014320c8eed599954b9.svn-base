package ext.ket.shared.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.org.WTPrincipal;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;

public abstract class AbsSimpleQuerySpec {

    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbsSimpleQuerySpec.class);

    protected static final String ROLE_A_OBJECT = "roleAObject";
    protected static final String ROLE_B_OBJECT = "roleBObject";
    protected static final String PERS_OBJECT_IDENTITY_ID = "thePersistInfo.theObjectIdentifier.id";
    protected static final ReferenceFactory rf = new ReferenceFactory();

    protected static final boolean WITH_ADMIN_AUTH_DEFAULT = true;
    protected static final boolean ADVANCED_QUERY_DEFAULT = false;

    protected static final boolean IS_OUTPUT_A_DEFAULT = true;
    protected static final boolean IS_OUTPUT_LINK_DEFAULT = true;
    protected static final boolean IS_OUTPUT_B_DEFAULT = true;

    // ///////////////////////////////////////////////////////////////////////////
    // Common Link Query
    // ///////////////////////////////////////////////////////////////////////////

    // A나 B를 반환함
    protected <E> List<E> queryForListByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery,
	    QueryLinkStrategy strategy) throws Exception {

	List<E> list = new ArrayList<E>();
	try {

	    if (objectRoleA == null && objectRoleB == null)
		throw new Exception("Query role Object is null!!");

	    boolean isOutputA = objectRoleA == null;
	    boolean isOutputLink = false;
	    boolean isOutputB = objectRoleB == null;

	    QueryResult queryResult = getQueryResultByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, withAdminAuth, advancedQuery, strategy, isOutputA, isOutputLink, isOutputB);

	    if (queryResult == null || (queryResult.size() < 0)) {
		log.debug("result is null!!");
	    } else if (queryResult.size() > 0) {

		while (queryResult.hasMoreElements()) {

		    Object[] objs = (Object[]) queryResult.nextElement();
		    log.debug("objs : " + objs);

		    Object objItem = objs[0];
		    log.debug("objItem : " + objs);

		    if (isOutputA) {
			if (classA.isInstance(objItem)) {
			    list.add((E) classA.cast(objItem));
			}
		    } else {
			if (classB.isInstance(objItem)) {
			    list.add((E) classB.cast(objItem));
			}
		    }
		}
	    }

	} catch (Exception e) {
	    log.error(ExceptionUtils.getFullStackTrace(e));
	    throw e;
	} finally {
	}

	return list;

	// Persistable objectRoleLink = null;
	// return queryForListByRoleALinkB(classA, classLink, classB, objectRoleA, objectRoleLink, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    // Link를 반환함
    protected <E> List<E> queryForListByRoleABOnlyLink(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery,
	    QueryLinkStrategy strategy) throws Exception {

	List<E> list = new ArrayList<E>();
	try {

	    if (objectRoleA == null && objectRoleB == null)
		throw new Exception("Query role Object is null!!");

	    boolean isOutputA = false;
	    boolean isOutputLink = true;
	    boolean isOutputB = false;

	    QueryResult queryResult = getQueryResultByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB, withAdminAuth, advancedQuery, strategy, isOutputA, isOutputLink, isOutputB);

	    if (queryResult == null || (queryResult.size() < 0)) {
		log.debug("result is null!!");
	    } else if (queryResult.size() > 0) {

		while (queryResult.hasMoreElements()) {

		    Object[] objs = (Object[]) queryResult.nextElement();
		    log.debug("objs : " + objs);

		    Object objItem = objs[0];
		    log.debug("objItem : " + objs);

		    if (isOutputLink) {
			if (classLink.isInstance(objItem)) {
			    list.add((E) classLink.cast(objItem));
			}
		    }
		}
	    }

	} catch (Exception e) {
	    log.error(ExceptionUtils.getFullStackTrace(e));
	    throw e;
	} finally {
	}

	return list;

	// Persistable objectRoleLink = null;
	// return queryForListByRoleALinkB(classA, classLink, classB, objectRoleA, objectRoleLink, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    // 편리하게 줄임
    protected QueryResult getQueryResultByRoleAB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery,
	    QueryLinkStrategy strategy, boolean isOutputA, boolean isOutputLink, boolean isOutputB) throws Exception {
	Persistable objectLink = null;
	return getQueryResultByRoleALinkB(classA, classLink, classB, objectRoleA, objectLink, objectRoleB, withAdminAuth, advancedQuery, strategy, isOutputA, isOutputLink, isOutputB);
    }

    // A LINK B에 대한 실제 QuerySpec :
    protected QueryResult getQueryResultByRoleALinkB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectLink, Persistable objectRoleB, boolean withAdminAuth,
	    boolean advancedQuery, QueryLinkStrategy strategy, boolean isOutputA, boolean isOutputLink, boolean isOutputB) throws Exception {

	WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
	if (withAdminAuth) {
	    SessionHelper.manager.setAdministrator();
	}

	QueryResult queryResult = null;

	try {

	    QuerySpec query = new QuerySpec();
	    query.setAdvancedQueryEnabled(advancedQuery);

	    // boolean outputIsA = (objectRoleA == null) ? true : false;
	    int indexA = query.appendClassList(classA, isOutputA);
	    int indexLink = query.appendClassList(classLink, isOutputLink);
	    int indexB = query.appendClassList(classB, isOutputB);

	    query.appendJoin(indexLink, ROLE_A_OBJECT, indexA);
	    query.appendJoin(indexLink, ROLE_B_OBJECT, indexB);

	    if (objectRoleA == null && objectRoleB == null) {
		throw new Exception("Query Instance all null Exception");
	    }

	    // if (objectRoleA != null && objectRoleB != null) {
	    // throw new Exception("Query Instance all not null Exception");
	    // }

	    if (objectRoleA != null) {
		query.appendWhere(new SearchCondition(classA, PERS_OBJECT_IDENTITY_ID, SearchCondition.EQUAL, objectRoleA.getPersistInfo().getObjectIdentifier().getId()), new int[] { indexA });
	    }

	    if (objectRoleB != null) {

		// if (objectRoleA != null)
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classB, PERS_OBJECT_IDENTITY_ID, SearchCondition.EQUAL, objectRoleB.getPersistInfo().getObjectIdentifier().getId()), new int[] { indexB });
	    }

	    if (objectLink != null) {

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classLink, PERS_OBJECT_IDENTITY_ID, SearchCondition.EQUAL, objectLink.getPersistInfo().getObjectIdentifier().getId()), new int[] { indexLink });
	    }

	    if (strategy != null) {
		strategy.handleQuery(query, classA, indexA, classLink, indexLink, classB, indexB);
	    }

	    log.debug("## REQUEST Query:" + query.toString());

	    queryResult = PersistenceHelper.manager.find(query);

	} catch (Exception e) {
	    log.error(ExceptionUtils.getStackTrace(e));
	    throw e;
	} finally {
	    if (withAdminAuth) {
		SessionHelper.manager.setPrincipal(orgPrincipal.getName());
	    }
	}

	return queryResult;
    }

    // Link를 반환함 B로 조회해서
    protected <E> List<E> queryForListByLinkRoleB(Class classLink, Class classB, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery, QueryBStrategy strategy) throws Exception {

	List<E> list = new ArrayList<E>();
	try {

	    Persistable objectLink = null;
	    if (objectLink == null && objectRoleB == null)
		throw new Exception("Query role Object is null!!");

	    // boolean isOutputA = objectRoleA == null;
	    boolean isOutputLink = objectLink == null;
	    boolean isOutputB = objectRoleB == null;

	    QueryResult queryResult = getQueryResultByLinkRoleB(classLink, classB, objectLink, objectRoleB, withAdminAuth, advancedQuery, strategy, isOutputLink, isOutputB);

	    if (queryResult == null || (queryResult.size() < 0)) {
		log.debug("result is null!!");
	    } else if (queryResult.size() > 0) {

		while (queryResult.hasMoreElements()) {

		    Object[] objs = (Object[]) queryResult.nextElement();
		    log.debug("objs : " + objs);

		    Object objItem = objs[0];
		    log.debug("objItem : " + objs);

		    if (isOutputLink) {
			if (classLink.isInstance(objItem)) {
			    list.add((E) classLink.cast(objItem));
			}
		    } else {
			if (classB.isInstance(objItem)) {
			    list.add((E) classB.cast(objItem));
			}
		    }
		}
	    }

	} catch (Exception e) {
	    log.error(ExceptionUtils.getFullStackTrace(e));
	    throw e;
	} finally {
	}

	return list;

	// Persistable objectRoleLink = null;
	// return queryForListByRoleALinkB(classA, classLink, classB, objectRoleA, objectRoleLink, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    // Link를 반환함 B로 조회하는 실제 QuerySpec
    protected QueryResult getQueryResultByLinkRoleB(Class classLink, Class classB, Persistable objectLink, Persistable objectRoleB, boolean withAdminAuth, boolean advancedQuery,
	    QueryBStrategy strategy, boolean isOutputLink, boolean isOutputB) throws Exception {

	WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
	if (withAdminAuth) {
	    SessionHelper.manager.setAdministrator();
	}

	QueryResult queryResult = null;

	try {

	    QuerySpec query = new QuerySpec();
	    query.setAdvancedQueryEnabled(advancedQuery);

	    // boolean outputIsA = (objectRoleA == null) ? true : false;
	    // int indexA = query.appendClassList(classA, isOutputA);
	    int indexLink = query.appendClassList(classLink, isOutputLink);
	    int indexB = query.appendClassList(classB, isOutputB);

	    // query.appendJoin(indexLink, ROLE_A_OBJECT, indexA);
	    query.appendJoin(indexLink, ROLE_B_OBJECT, indexB);

	    if (objectLink == null && objectRoleB == null) {
		throw new Exception("Query Instance all null Exception");
	    }

	    // if (objectRoleA != null && objectRoleB != null) {
	    // throw new Exception("Query Instance all not null Exception");
	    // }

	    // if (objectRoleA != null) {
	    // query.appendWhere(new SearchCondition(classA, PERS_OBJECT_IDENTITY_ID, SearchCondition.EQUAL, objectRoleA.getPersistInfo().getObjectIdentifier().getId()), new int[] { indexA });
	    // }

	    if (objectRoleB != null) {

		// if (objectRoleA != null)
		// if (query.getConditionCount() > 0)
		// query.appendAnd();

		query.appendWhere(new SearchCondition(classB, PERS_OBJECT_IDENTITY_ID, SearchCondition.EQUAL, objectRoleB.getPersistInfo().getObjectIdentifier().getId()), new int[] { indexB });
	    }

	    if (objectLink != null) {
		if (objectRoleB != null)
		    if (query.getConditionCount() > 0)
			query.appendAnd();

		query.appendWhere(new SearchCondition(classLink, PERS_OBJECT_IDENTITY_ID, SearchCondition.EQUAL, objectLink.getPersistInfo().getObjectIdentifier().getId()), new int[] { indexLink });
	    }

	    if (strategy != null) {
		strategy.handleQuery(query, classLink, indexLink, classB, indexB);
	    }

	    log.debug("## REQUEST Query:" + query.toString());

	    queryResult = PersistenceHelper.manager.find(query);

	} catch (Exception e) {
	    log.error(ExceptionUtils.getStackTrace(e));
	    throw e;
	} finally {
	    if (withAdminAuth) {
		SessionHelper.manager.setPrincipal(orgPrincipal.getName());
	    }
	}

	return queryResult;
    }

    // Link를 반환함 A로 조회해서
    protected <E> List<E> queryForListByLinkRoleA(Class classA, Class classLink, Persistable objectRoleA, boolean withAdminAuth, boolean advancedQuery, QueryAStrategy strategy) throws Exception {

	List<E> list = new ArrayList<E>();
	try {

	    Persistable objectLink = null;
	    if (objectRoleA == null && objectLink == null)
		throw new Exception("Query role Object is null!!");

	    boolean isOutputA = objectRoleA == null;
	    boolean isOutputLink = objectLink == null;
	    // boolean isOutputB = objectRoleB == null;

	    QueryResult queryResult = getQueryResultByLinkRoleA(classA, classLink, objectRoleA, objectLink, withAdminAuth, advancedQuery, strategy, isOutputA, isOutputLink);

	    if (queryResult == null || (queryResult.size() < 0)) {
		log.debug("result is null!!");
	    } else if (queryResult.size() > 0) {

		while (queryResult.hasMoreElements()) {

		    Object[] objs = (Object[]) queryResult.nextElement();
		    log.debug("objs : " + objs);

		    Object objItem = objs[0];
		    log.debug("objItem : " + objs);

		    if (isOutputLink) {
			if (classLink.isInstance(objItem)) {
			    list.add((E) classLink.cast(objItem));
			}
		    } else {
			if (classA.isInstance(objItem)) {
			    list.add((E) classA.cast(objItem));
			}
		    }
		}
	    }

	} catch (Exception e) {
	    log.error(ExceptionUtils.getFullStackTrace(e));
	    throw e;
	} finally {
	}

	return list;

	// Persistable objectRoleLink = null;
	// return queryForListByRoleALinkB(classA, classLink, classB, objectRoleA, objectRoleLink, objectRoleB, withAdminAuth, advancedQuery, strategy);
    }

    // Link를 반환함 A로 조회하는 실제 QuerySpec
    protected QueryResult getQueryResultByLinkRoleA(Class classA, Class classLink, Persistable objectRoleA, Persistable objectLink, boolean withAdminAuth, boolean advancedQuery,
	    QueryAStrategy strategy, boolean isOutputA, boolean isOutputLink) throws Exception {

	WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
	if (withAdminAuth) {
	    SessionHelper.manager.setAdministrator();
	}

	QueryResult queryResult = null;

	try {

	    QuerySpec query = new QuerySpec();
	    query.setAdvancedQueryEnabled(advancedQuery);

	    // boolean outputIsA = (objectRoleA == null) ? true : false;
	    int indexA = query.appendClassList(classA, isOutputA);
	    int indexLink = query.appendClassList(classLink, isOutputLink);
	    // int indexB = query.appendClassList(classB, isOutputB);

	    query.appendJoin(indexLink, ROLE_A_OBJECT, indexA);
	    // query.appendJoin(indexLink, ROLE_B_OBJECT, indexB);

	    if (objectRoleA == null && objectLink == null) {
		throw new Exception("Query Instance all null Exception");
	    }

	    // if (objectRoleA != null && objectRoleB != null) {
	    // throw new Exception("Query Instance all not null Exception");
	    // }

	    if (objectRoleA != null) {
		query.appendWhere(new SearchCondition(classA, PERS_OBJECT_IDENTITY_ID, SearchCondition.EQUAL, objectRoleA.getPersistInfo().getObjectIdentifier().getId()), new int[] { indexA });
	    }

	    // if (objectRoleB != null) {
	    //
	    // // if (objectRoleA != null)
	    // // if (query.getConditionCount() > 0)
	    // // query.appendAnd();
	    //
	    // query.appendWhere(new SearchCondition(classB, PERS_OBJECT_IDENTITY_ID, SearchCondition.EQUAL, objectRoleB.getPersistInfo().getObjectIdentifier().getId()), new int[] { indexB });
	    // }

	    if (objectLink != null) {
		if (objectRoleA != null)
		    if (query.getConditionCount() > 0)
			query.appendAnd();

		query.appendWhere(new SearchCondition(classLink, PERS_OBJECT_IDENTITY_ID, SearchCondition.EQUAL, objectLink.getPersistInfo().getObjectIdentifier().getId()), new int[] { indexLink });
	    }

	    if (strategy != null) {
		strategy.handleQuery(query, classA, indexA, classLink, indexLink);
	    }

	    log.debug("## REQUEST Query:" + query.toString());

	    queryResult = PersistenceHelper.manager.find(query);

	} catch (Exception e) {
	    log.error(ExceptionUtils.getStackTrace(e));
	    throw e;
	} finally {
	    if (withAdminAuth) {
		SessionHelper.manager.setPrincipal(orgPrincipal.getName());
	    }
	}

	return queryResult;
    }

    // ///////////////////////////////////////////////////////////////////////////
    // One Class 처리
    // ///////////////////////////////////////////////////////////////////////////
    protected <E> List<E> queryForListByOneClassOnly(Class classSource, boolean withAdminAuth, boolean advancedQuery, QueryStrategy strategy) throws Exception {

	List<E> list = new ArrayList<E>();
	try {

	    QueryResult queryResult = getQueryResultByOneClassOnly(classSource, withAdminAuth, advancedQuery, strategy);

	    if (queryResult == null || (queryResult.size() < 0)) {
		log.debug("result is null!!");
	    } else if (queryResult.size() > 0) {

		while (queryResult.hasMoreElements()) {

		    Object objItem = queryResult.nextElement();
		    log.debug("objItem : " + objItem.toString());

		    if (classSource.isInstance(objItem)) {
			list.add((E) classSource.cast(objItem));
		    }
		}
	    }

	} catch (Exception e) {
	    log.error(ExceptionUtils.getFullStackTrace(e));
	    throw e;
	} finally {
	}

	return list;
    }

    protected QueryResult getQueryResultByOneClassOnly(Class classSource, boolean withAdminAuth, boolean advancedQuery, QueryStrategy strategy) throws Exception {

	WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
	if (withAdminAuth) {
	    SessionHelper.manager.setAdministrator();
	}

	QueryResult queryResult = null;

	try {

	    QuerySpec query = new QuerySpec(classSource);
	    query.setAdvancedQueryEnabled(advancedQuery);

	    int index = 0;

	    if (strategy != null) {
		strategy.handleQuery(query, classSource, index);
	    }

	    log.debug("## REQUEST Query:" + query.toString());

	    queryResult = PersistenceHelper.manager.find(query);

	} catch (Exception e) {
	    log.error(ExceptionUtils.getStackTrace(e));
	    throw e;
	} finally {
	    if (withAdminAuth) {
		SessionHelper.manager.setPrincipal(orgPrincipal.getName());
	    }
	}

	return queryResult;
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Test Data
    // ///////////////////////////////////////////////////////////////////////////

    // private <E> List<E> queryForListByRoleALinkB(Class classA, Class classLink, Class classB, Persistable objectRoleA, Persistable objectRoleLink, Persistable objectRoleB, boolean withAdminAuth,
    // boolean advancedQuery, QueryLinkStrategy strategy) throws Exception {
    //
    // List<E> list = new ArrayList<E>();
    // try {
    //
    // if (objectRoleA == null && objectRoleLink == null && objectRoleB == null)
    // throw new Exception("Query role Object is null!!");
    //
    // boolean isOutputA = objectRoleA == null;
    // boolean isOutputLink = objectRoleLink == null;
    // boolean isOutputB = objectRoleB == null;
    //
    // // validation
    // if (isOutputA && !isOutputLink && !isOutputB) {
    // log.debug("# Get A");
    // } else if (!isOutputA && isOutputLink && !isOutputB) {
    // log.debug("# Get LINK");
    // } else if (!isOutputA && !isOutputLink && isOutputB) {
    // log.debug("# Get B");
    // } else {
    // log.debug("# Get try to too many object ");
    // throw new Exception("# Get try to too many object ");
    // }
    //
    // QueryResult queryResult = getQueryResultByRoleALinkB(classA, classLink, classB, objectRoleA, objectRoleLink, objectRoleB, withAdminAuth, advancedQuery, strategy, isOutputA, isOutputLink,
    // isOutputB);
    //
    // if (queryResult == null || (queryResult.size() < 0)) {
    //
    // log.debug("result is null!!");
    //
    // } else if (queryResult.size() > 0) {
    //
    // while (queryResult.hasMoreElements()) {
    //
    // Object[] objs = (Object[]) queryResult.nextElement();
    // log.debug("objs : " + objs);
    //
    // Object objItem = objs[0];
    // log.debug("objItem : " + objs);
    //
    // if (isOutputA) {
    // if (classA.isInstance(objItem)) {
    // list.add((E) classA.cast(objItem));
    // }
    // } else if (isOutputLink) {
    // if (classLink.isInstance(objItem)) {
    // list.add((E) classLink.cast(objItem));
    // }
    // } else {
    // if (classB.isInstance(objItem)) {
    // list.add((E) classB.cast(objItem));
    // }
    // }
    // }
    // }
    //
    // } catch (Exception e) {
    // log.error(ExceptionUtils.getFullStackTrace(e));
    // throw e;
    // } finally {
    // }
    //
    // return list;
    // }
}
