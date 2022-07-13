package ext.ket.shared.query;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

import wt.epm.EPMDocumentMaster;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.edm.EPMLink;
import ext.ket.shared.log.Kogger;

public class SimpleQuerySample implements RemoteAccess, Serializable {

    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static SimpleQuerySample manager = new SimpleQuerySample();

    public SimpleQuerySample() {

    }

    public static void main(String[] args) {

	try {

	    Kogger.debug(SimpleQuerySample.class, "@start");
	    SimpleQuerySample.manager.saveFromExcel();
	    Kogger.debug(SimpleQuerySample.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(SimpleQuerySample.class, e);
	}
    }

    public void saveFromExcel() throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {};
		Object aobj[] = {};

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration();
	}
    }

    public void executeMigration() throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    doTestEasyQuerySpec();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    // //////////////////////////////////////////////////////////
    // EasyQuerySpec
    // //////////////////////////////////////////////////////////
    private static final boolean withAdminAuth = true;
    private static final boolean advancedQuery = false;

    public void doTestEasyQuerySpec() throws Exception {

	ReferenceFactory rf = new ReferenceFactory();

	// ONE CLASS
	log.debug("**************** testQueryForListByOneClassClassOfTQueryStrategy ****");
	testQueryForListByOneClassClassOfTQueryStrategy();
	log.debug("**************** testQueryForListByOneClassClassOfTBooleanBooleanQueryStrategy ****");
	testQueryForListByOneClassClassOfTBooleanBooleanQueryStrategy();
	log.debug("**************** testGetQueryResultByOneClassClassQueryStrategy ****");
	testGetQueryResultByOneClassClassQueryStrategy();
	log.debug("**************** testGetQueryResultByOneClassClassBooleanBooleanQueryStrategy ****");
	testGetQueryResultByOneClassClassBooleanBooleanQueryStrategy();
	// ROLE A BY ROLE B
	log.debug("**************** testQueryForListAByRoleBClassOfTClassClassPersistable ****");
	testQueryForListAByRoleBClassOfTClassClassPersistable();
	log.debug("**************** testQueryForListAByRoleBClassOfTClassClassPersistableBooleanBoolean ****");
	testQueryForListAByRoleBClassOfTClassClassPersistableBooleanBoolean();
	log.debug("**************** testQueryForListAByRoleBClassOfTClassClassPersistableQueryLinkStrategy ****");
	testQueryForListAByRoleBClassOfTClassClassPersistableQueryLinkStrategy();
	log.debug("**************** testQueryForListAByRoleBClassOfTClassClassPersistableBooleanBooleanQueryLinkStrategy ****");
	testQueryForListAByRoleBClassOfTClassClassPersistableBooleanBooleanQueryLinkStrategy();
	log.debug("**************** testGetQueryResultByRoleBClassClassClassPersistable ****");
	testGetQueryResultByRoleBClassClassClassPersistable();
	log.debug("**************** testGetQueryResultByRoleBClassClassClassPersistableQueryLinkStrategy ****");
	testGetQueryResultByRoleBClassClassClassPersistableQueryLinkStrategy();
	log.debug("**************** testGetQueryResultByRoleBClassClassClassPersistableBooleanBooleanQueryLinkStrategy ****");
	testGetQueryResultByRoleBClassClassClassPersistableBooleanBooleanQueryLinkStrategy();
	log.debug("**************** testQueryForListBByRoleAClassClassClassOfTPersistable ****");
	// ROLE B BY ROLE A
	testQueryForListBByRoleAClassClassClassOfTPersistable();
	log.debug("**************** testQueryForListBByRoleAClassClassClassOfTPersistableBooleanBoolean ****");
	testQueryForListBByRoleAClassClassClassOfTPersistableBooleanBoolean();
	log.debug("**************** testQueryForListBByRoleAClassClassClassOfTPersistableQueryLinkStrategy ****");
	testQueryForListBByRoleAClassClassClassOfTPersistableQueryLinkStrategy();
	log.debug("**************** testQueryForListBByRoleAClassClassClassOfTPersistableBooleanBooleanQueryLinkStrategy ****");
	testQueryForListBByRoleAClassClassClassOfTPersistableBooleanBooleanQueryLinkStrategy();
	log.debug("**************** testGetQueryResultByRoleAClassClassClassPersistable ****");
	testGetQueryResultByRoleAClassClassClassPersistable();
	log.debug("**************** testGetQueryResultByRoleAClassClassClassPersistableQueryLinkStrategy ****");
	testGetQueryResultByRoleAClassClassClassPersistableQueryLinkStrategy();
	log.debug("**************** testGetQueryResultByRoleAClassClassClassPersistableBooleanBooleanQueryLinkStrategy ****");
	testGetQueryResultByRoleAClassClassClassPersistableBooleanBooleanQueryLinkStrategy();
	log.debug("**************** testGetQueryForObjectLinkByRoleABClassClassALinkBPersistableRoleARoleB ****");
	testGetQueryForObjectLinkByRoleABClassClassALinkBPersistableRoleARoleB();
	// log.debug("**************** testQueryForObjectAByLinkRoleB ****");
	// testQueryForObjectAByLinkRoleB();
	// log.debug("**************** testQueryForObjectBByRoleALink ****");
	// testQueryForObjectBByRoleALink();
	// ROLE LINK BY ROLE B
	log.debug("**************** testQueryForListLinkByRoleB1 ****");
	testQueryForListLinkByRoleB1();
	log.debug("**************** testQueryForListLinkByRoleB2 ****");
	testQueryForListLinkByRoleB2();
	log.debug("**************** testGetQueryResultLinkBByRoleB1 ****");
	testGetQueryResultLinkBByRoleB1();
	log.debug("**************** testGetQueryResultLinkBByRoleB2 ****");
	testGetQueryResultLinkBByRoleB2();
	// ROLE LINK BY ROLE A
	log.debug("**************** testQueryForListLinkByRoleA1 ****");
	testQueryForListLinkByRoleA1();
	log.debug("**************** testQueryForListLinkByRoleA2 ****");
	testQueryForListLinkByRoleA2();
	log.debug("**************** testGetQueryResultALinkByRoleA1 ****");
	testGetQueryResultALinkByRoleA1();
	log.debug("**************** testGetQueryResultALinkByRoleA2 ****");
	testGetQueryResultALinkByRoleA2();
	log.debug("**************** testQueryForListLinkByRoleAB ****");
	testQueryForListLinkByRoleAB();
	// 파일 다운로드

    }

    // ///////////////////////////////////////////////////////////////////////////
    // One Class - Get QuerySpec
    // ///////////////////////////////////////////////////////////////////////////

    public void testQueryForListByOneClassClassOfTQueryStrategy() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	Class classSource = WTPart.class;
	List<WTPart> wtPartList = query.queryForListByOneClass(classSource, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {

		query.appendWhere(new SearchCondition(class1, WTPart.NUMBER, SearchCondition.EQUAL, "10861B00-D10-6"), new int[] { index });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(class1, WTPart.NAME, SearchCondition.EQUAL, "BENDING DIE"), new int[] { index });

	    }

	});

	log.debug("# Result queryForListByOneClass 1:" + wtPartList.size());

	WTPart wtpart = query.queryForFirstByOneClass(classSource, new QueryStrategy() {
	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		query.appendWhere(new SearchCondition(class1, WTPart.NUMBER, SearchCondition.EQUAL, "10861B00-D10-6"), new int[] { index });
		if (query.getConditionCount() > 0)
		    query.appendAnd();
		query.appendWhere(new SearchCondition(class1, WTPart.NAME, SearchCondition.EQUAL, "BENDING DIE"), new int[] { index });

	    }
	});

	log.debug("# Result queryForListByOneClass first:" + wtpart);
    }

    public void testQueryForListByOneClassClassOfTBooleanBooleanQueryStrategy() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	Class classSource = WTPart.class;
	List<WTPart> wtPartList = query.queryForListByOneClass(classSource, this.withAdminAuth, this.advancedQuery, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {

		query.appendWhere(new SearchCondition(class1, WTPart.NUMBER, SearchCondition.EQUAL, "10861B00-D10-6"), new int[] { index });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(class1, WTPart.NAME, SearchCondition.EQUAL, "BENDING DIE"), new int[] { index });

	    }

	});

	log.debug("# Result queryForListByOneClass 2:" + wtPartList.size());

	WTPart wtPart = query.queryForFirstByOneClass(classSource, this.withAdminAuth, this.advancedQuery, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {

		query.appendWhere(new SearchCondition(class1, WTPart.NUMBER, SearchCondition.EQUAL, "10861B00-D10-6"), new int[] { index });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(class1, WTPart.NAME, SearchCondition.EQUAL, "BENDING DIE"), new int[] { index });

	    }

	});

	log.debug("# Result queryForListByOneClass first:" + wtPart);
    }

    public void testGetQueryResultByOneClassClassQueryStrategy() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	Class classSource = WTPart.class;
	QueryResult queryResult = query.getQueryResultByOneClass(classSource, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {

		query.appendWhere(new SearchCondition(class1, WTPart.NUMBER, SearchCondition.EQUAL, "10861B00-D10-6"), new int[] { index });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(class1, WTPart.NAME, SearchCondition.EQUAL, "BENDING DIE"), new int[] { index });

	    }
	});

	log.debug("# Result getQueryResultByOneClass 1:" + queryResult.size());
    }

    public void testGetQueryResultByOneClassClassBooleanBooleanQueryStrategy() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	Class classSource = WTPart.class;
	QueryResult queryResult = query.getQueryResultByOneClass(classSource, this.withAdminAuth, this.advancedQuery, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {

		query.appendWhere(new SearchCondition(class1, WTPart.NUMBER, SearchCondition.EQUAL, "10861B00-D10-6"), new int[] { index });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(class1, WTPart.NAME, SearchCondition.EQUAL, "BENDING DIE"), new int[] { index });

	    }
	});

	log.debug("# Result getQueryResultByOneClass 2:" + queryResult.size());
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Link관계 - GET ROLE A LIST BY ROLE B
    // ///////////////////////////////////////////////////////////////////////////

    Class classA = EPMDocumentMaster.class;
    Class classLink = EPMLink.class;
    Class classB = WTPartMaster.class;

    private EPMDocumentMaster objectRoleA = (EPMDocumentMaster) CommonUtil.getObject("wt.epm.EPMDocumentMaster:77424514");
    private WTPartMaster objectRoleB = (WTPartMaster) CommonUtil.getObject("wt.part.WTPartMaster:1684974");
    private EPMLink objectLink = (EPMLink) CommonUtil.getObject("e3ps.edm.EPMLink:77424535");

    // //////////////////////
    // / A By RoleB
    // //////////////////////

    public void testQueryForListAByRoleBClassOfTClassClassPersistable() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<EPMDocumentMaster> list = query.queryForListAByRoleB(classA, classLink, classB, objectRoleB);

	log.debug("# Result queryForListAByRoleB 1:" + list.size());

	EPMDocumentMaster epmMaster = query.queryForFirstAByRoleB(classA, classLink, classB, objectRoleB);
	log.debug("# Result queryForListAByRoleB first:" + epmMaster);
    }

    public void testQueryForListAByRoleBClassOfTClassClassPersistableBooleanBoolean() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<EPMDocumentMaster> list = query.queryForListAByRoleB(classA, classLink, classB, objectRoleB, new QueryLinkStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7_PLS"), new int[] { indexA });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexA });

	    }

	});

	log.debug("# Result queryForListAByRoleB 2:" + list.size());

	EPMDocumentMaster first = query.queryForFirstAByRoleB(classA, classLink, classB, objectRoleB, new QueryLinkStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7_PLS"), new int[] { indexA });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexA });

	    }

	});

	log.debug("# Result queryForListAByRoleB first:" + first);
    }

    public void testQueryForListAByRoleBClassOfTClassClassPersistableQueryLinkStrategy() throws Exception {
    }

    public void testQueryForListAByRoleBClassOfTClassClassPersistableBooleanBooleanQueryLinkStrategy() throws Exception {
    }

    public void testGetQueryResultByRoleBClassClassClassPersistable() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult queryResult = query.getQueryResultByRoleB(classA, classLink, classB, objectRoleB);
	log.debug("# Result getQueryResultByRoleB 1:" + queryResult.size());
    }

    public void testGetQueryResultByRoleBClassClassClassPersistableQueryLinkStrategy() throws Exception {

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult queryResult = query.getQueryResultByRoleB(classA, classLink, classB, objectRoleB, new QueryLinkStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7_PLS"), new int[] { indexA });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexA });

	    }

	});
	log.debug("# Result getQueryResultByRoleB 2:" + queryResult.size());
    }

    public void testGetQueryResultByRoleBClassClassClassPersistableBooleanBooleanQueryLinkStrategy() throws Exception {
    }

    // //////////////////////
    // / B By RoleA
    // //////////////////////

    public void testQueryForListBByRoleAClassClassClassOfTPersistable() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<WTPartMaster> list = query.queryForListBByRoleA(classA, classLink, classB, objectRoleA);

	log.debug("# Result queryForListBByRoleA 1:" + list.size());

	WTPartMaster first = query.queryForFirstBByRoleA(classA, classLink, classB, objectRoleA);

	log.debug("# Result queryForListBByRoleA first:" + first);
    }

    public void testQueryForListBByRoleAClassClassClassOfTPersistableBooleanBoolean() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<WTPartMaster> list = query.queryForListBByRoleA(classA, classLink, classB, objectRoleA, new QueryLinkStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7_PLS"), new int[] { indexA });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexA });

	    }

	});

	log.debug("# Result queryForListBByRoleA 2:" + list.size());

	WTPartMaster first = query.queryForFirstBByRoleA(classA, classLink, classB, objectRoleA, new QueryLinkStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7_PLS"), new int[] { indexA });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexA });

	    }

	});

	log.debug("# Result queryForListBByRoleA first:" + first);
    }

    public void testQueryForListBByRoleAClassClassClassOfTPersistableQueryLinkStrategy() throws Exception {
    }

    public void testQueryForListBByRoleAClassClassClassOfTPersistableBooleanBooleanQueryLinkStrategy() throws Exception {
    }

    public void testGetQueryResultByRoleAClassClassClassPersistable() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult queryResult = query.getQueryResultByRoleA(classA, classLink, classB, objectRoleA);
	log.debug("# Result getQueryResultByRoleA 1:" + queryResult.size());
    }

    public void testGetQueryResultByRoleAClassClassClassPersistableQueryLinkStrategy() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult queryResult = query.getQueryResultByRoleA(classA, classLink, classB, objectRoleA, new QueryLinkStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7_PLS"), new int[] { indexA });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexA });

	    }

	});
	log.debug("# Result getQueryResultByRoleA 2:" + queryResult.size());
    }

    public void testGetQueryResultByRoleAClassClassClassPersistableBooleanBooleanQueryLinkStrategy() throws Exception {
    }

    public void testGetQueryForObjectLinkByRoleABClassClassALinkBPersistableRoleARoleB() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	EPMLink epmLink = query.queryForObjectLinkByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB);
	log.debug("# Result queryForObjectLinkByRoleAB 1:" + epmLink);
    }

    // public void testQueryForObjectAByLinkRoleB() throws Exception {
    // SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    // EPMDocumentMaster EpmDocMaster = query.queryForObjectAByLinkRoleB(classA, classLink, classB, objectLink, objectRoleB);
    // log.debug("# Result queryForObjectAByLinkRoleB 1:" + EpmDocMaster);
    // }
    //
    // public void testQueryForObjectBByRoleALink() throws Exception {
    // SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    // WTPartMaster partMaster = query.queryForObjectBByRoleALink(classA, classLink, classB, objectRoleA, objectLink);
    // log.debug("# Result queryForObjectBByRoleALink 1:" + partMaster);
    // }

    // //////////////////////
    // / Link By RoleB
    // //////////////////////

    public void testQueryForListLinkByRoleB1() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(classLink, classB, objectRoleB);
	log.debug("# Result testQueryForListLinkByRoleB 1:" + epmLinkList.size());

	EPMLink first = query.queryForFirstLinkByRoleB(classLink, classB, objectRoleB);
	log.debug("# Result testQueryForListLinkByRoleB first:" + first);
    }

    public void testQueryForListLinkByRoleB2() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(classLink, classB, objectRoleB, new QueryBStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classB, WTPartMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7"), new int[] { indexB });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classB, WTPartMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexB });
	    }

	});
	log.debug("# Result testGetQueryResultLinkBByRoleB 2:" + epmLinkList.size());

	EPMLink first = query.queryForFirstLinkByRoleB(classLink, classB, objectRoleB, new QueryBStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classB, WTPartMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7"), new int[] { indexB });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classB, WTPartMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexB });
	    }

	});
	log.debug("# Result testGetQueryResultLinkBByRoleB first:" + first);
    }

    public void testGetQueryResultLinkBByRoleB1() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult qr = query.getQueryResultLinkBByRoleB(classLink, classB, objectRoleB);
	log.debug("# Result testGetQueryResultLinkBByRoleB 1:" + qr.size());
    }

    public void testGetQueryResultLinkBByRoleB2() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult qr = query.getQueryResultLinkBByRoleB(classLink, classB, objectRoleB, new QueryBStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classB, WTPartMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7"), new int[] { indexB });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classB, WTPartMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexB });
	    }

	});
	log.debug("# Result testGetQueryResultLinkBByRoleB 2:" + qr.size());
    }

    // //////////////////////
    // / Link By RoleA
    // //////////////////////

    public void testQueryForListLinkByRoleA1() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<EPMLink> epmLinkList = query.queryForListLinkByRoleA(classA, classLink, objectRoleA);
	log.debug("# Result testQueryForListLinkByRoleA1 1:" + epmLinkList.size());

	EPMLink first = query.queryForFirstLinkByRoleA(classA, classLink, objectRoleA);
	log.debug("# Result testQueryForListLinkByRoleA1 first:" + first);
    }

    public void testQueryForListLinkByRoleA2() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<EPMLink> epmLinkList = query.queryForListLinkByRoleA(classA, classLink, objectRoleA, new QueryAStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink) throws Exception {

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7_PLS"), new int[] { indexA });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexA });
	    }

	});
	log.debug("# Result testQueryForListLinkByRoleA2 2:" + epmLinkList.size());

	EPMLink first = query.queryForFirstLinkByRoleA(classA, classLink, objectRoleA, new QueryAStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink) throws Exception {

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7_PLS"), new int[] { indexA });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexA });
	    }

	});

	log.debug("# Result testQueryForListLinkByRoleA2 first:" + first);
    }

    public void testGetQueryResultALinkByRoleA1() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult qr = query.getQueryResultALinkByRoleA(classA, classLink, objectRoleA);
	log.debug("# Result testGetQueryResultALinkByRoleA1 1:" + qr.size());
    }

    public void testGetQueryResultALinkByRoleA2() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult qr = query.getQueryResultALinkByRoleA(classA, classLink, objectRoleA, new QueryAStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink) throws Exception {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, "10936000-P11-7_PLS"), new int[] { indexA });

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		query.appendWhere(new SearchCondition(classA, EPMDocumentMaster.NAME, SearchCondition.LIKE, "%BACKING%"), new int[] { indexA });
	    }

	});
	log.debug("# Result testGetQueryResultALinkByRoleA2 2:" + qr.size());
    }

    public void testQueryForListLinkByRoleAB() throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<EPMLink> epmLinkList = query.queryForListLinkByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB);
	log.debug("# Result testQueryForListLinkByRoleAB 1:" + epmLinkList.size());

	EPMLink epmLink = query.queryForFirstLinkByRoleAB(classA, classLink, classB, objectRoleA, objectRoleB);
	log.debug("# Result queryForFirstLinkByRoleAB first:" + epmLink);
    }

}
