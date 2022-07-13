package ext.ket.shared.util;

import java.sql.Timestamp;
import java.util.Locale;

import wt.clients.folder.FolderTaskLogic;
import wt.fc.Persistable;
import wt.folder.Folder;
import wt.introspection.ClassInfo;
import wt.introspection.LinkInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.pds.ClassJoinCondition;
import wt.pds.QuerySpecStatementBuilder;
import wt.query.AttributeSearchSpecification;
import wt.query.ClassAttribute;
import wt.query.ClassTableExpression;
import wt.query.ClassViewExpression;
import wt.query.ExistsExpression;
import wt.query.NegatedExpression;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SearchTask;
import wt.query.StringSearch;
import wt.query.TableExpression;
import wt.util.SortedEnumeration;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.Iterated;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;

/**
 * @(#) SearchUtil.java Copyright (c) Digitek. All rights reserverd
 * @version 1.00
 * @since jdk1.6.0_18
 * @createdate 2010. 12. 14.
 * @author Seong Jin, Han. narrsass@naver.com
 * @desc
 */
public class SearchUtil {
    /**
     * @param target
     * @param sideTarget
     * @param linkClass
     * @param targetRole
     * @param sideTargeRole
     * @param sideTargetPersistable
     * @return
     * @throws Exception
     */
    public static QuerySpec makeJoinQuerySpec(Class<?> target, Class<?> sideTarget, Class<?> linkClass, String targetRole, String sideTargeRole, Persistable sideTargetPersistable) throws Exception {
	QuerySpec query = new QuerySpec();
	int targetIndex = query.appendClassList(target, true);
	int sideTagetIndex = query.appendClassList(sideTarget, false);
	int linkIndex = query.appendClassList(linkClass, true);

	query.appendJoin(linkIndex, targetRole, targetIndex);
	query.appendJoin(linkIndex, sideTargeRole, sideTagetIndex);

	long lperistable = CommonUtil.getOIDLongValue(sideTargetPersistable);
	query.appendWhere(new SearchCondition(sideTarget, "thePersistInfo.theObjectIdentifier.id", "=", lperistable), new int[] { sideTagetIndex });

	if (target.equals(Iterated.class)) {
	    query.appendAnd();
	    query.appendWhere(new SearchCondition(target, "iterationInfo.latest", "TRUE"), new int[] { targetIndex });
	}
	return query;
    }

    /**
     * @param spec
     * @param roleAClassPos
     * @param roleBClassPos
     * @param linkClassName
     * @param isLinkOuterJoin
     * @param isTargetOuterJoin
     * @return
     * @throws ClassNotFoundException
     * @throws WTPropertyVetoException
     * @throws WTIntrospectionException
     */
    @SuppressWarnings("deprecation")
    public static QuerySpec appendLinkJoin(QuerySpec spec, int roleAClassPos, int roleBClassPos, String linkClassName, boolean isLinkOuterJoin, boolean isTargetOuterJoin)
	    throws ClassNotFoundException, WTPropertyVetoException, WTIntrospectionException {
	QuerySpecStatementBuilder builder = (QuerySpecStatementBuilder) spec.getStatementBuilder();
	LinkInfo linkinfo = WTIntrospector.getLinkInfo(linkClassName);
	Class<?> linkInfoClass = linkinfo.getBusinessClass();
	int k = spec.appendFrom(((wt.query.TableExpression) (linkinfo.isConcrete() ? ((wt.query.TableExpression) (new ClassViewExpression(linkInfoClass)))
	        : ((wt.query.TableExpression) (new ClassTableExpression(linkInfoClass))))));

	ClassJoinCondition classjoincondition = new ClassJoinCondition(k, linkinfo.getRoleA().getName(), roleAClassPos);
	classjoincondition.setLinkOuterJoin(isLinkOuterJoin);
	classjoincondition.setTargetOuterJoin(isTargetOuterJoin);
	builder.appendJoin(classjoincondition);
	classjoincondition = new ClassJoinCondition(k, linkinfo.getRoleB().getName(), roleBClassPos);
	classjoincondition.setLinkOuterJoin(isTargetOuterJoin);
	classjoincondition.setTargetOuterJoin(isLinkOuterJoin);
	builder.appendJoin(classjoincondition);
	QuerySpec returnSpec = builder.getQuerySpec();
	return returnSpec;
    }

    /**
     * @param spec
     * @param targetClassPos
     * @param linkClassPos
     * @param rolename
     * @return
     * @throws ClassNotFoundException
     * @throws WTPropertyVetoException
     */
    @SuppressWarnings("deprecation")
    public static QuerySpec appendLinkRoleJoin(QuerySpec spec, int targetClassPos, int linkClassPos, String rolename) throws ClassNotFoundException, WTPropertyVetoException {
	QuerySpecStatementBuilder builder = (QuerySpecStatementBuilder) spec.getStatementBuilder();
	ClassJoinCondition classjoincondition = new ClassJoinCondition(linkClassPos, rolename, targetClassPos);
	classjoincondition.setLinkOuterJoin(false);
	classjoincondition.setTargetOuterJoin(false);
	builder.appendJoin(classjoincondition);
	QuerySpec returnSpec = builder.getQuerySpec();
	return returnSpec;
    }

    /**
     * @param targetClass
     * @param fieldName
     * @param searchValue
     * @return
     * @throws WTException
     */
    public static SearchCondition getSearchCondition(Class<?> targetClass, String fieldName, String searchValue) throws WTException {
	return getSearchCondition(targetClass, WTIntrospector.getClassInfo(targetClass), fieldName, searchValue, Locale.KOREA);
    }

    /**
     * @param class1
     * @param classinfo
     * @param s
     * @param s1
     * @param locale
     * @return
     * @throws WTException
     */
    @SuppressWarnings("deprecation")
    public static SearchCondition getSearchCondition(Class<?> class1, ClassInfo classinfo, String s, String s1, Locale locale) throws WTException {
	Object obj = SearchTask.getSearchSpecification(s, s1, classinfo, locale);
	String s2 = ((AttributeSearchSpecification) obj).getSearchExpression();

	if (s2.equals(" LIKE ")) {
	    try {
		String s3 = (String) ((AttributeSearchSpecification) obj).getValue();
		if (!AttributeSearchSpecification.areThereDBWildCards(s3)) {
		    obj = new StringSearch(s, "=");
		    ((AttributeSearchSpecification) obj).setValue(s1);
		}
	    } catch (WTPropertyVetoException wtpropertyvetoexception) {
		Object aobj[] = { class1.toString() + "->" + s };
		throw new WTException(wtpropertyvetoexception, "wt.query.queryResource", "26", aobj);
	    }
	}

	try {
	    SearchCondition searchcondition = ((AttributeSearchSpecification) obj).getSearchCondition(class1);
	    if (searchcondition.getOperator().equals(" LIKE ")) searchcondition.setOption("escape '/'");
	    return searchcondition;
	} catch (WTPropertyVetoException wtpropertyvetoexception1) {
	    Object aobj1[] = { class1.toString() + "->" + s };
	    throw new WTException(wtpropertyvetoexception1, "wt.query.queryResource", "26", aobj1);
	} catch (QueryException queryexception) {
	    Object aobj2[] = { class1.toString() + "->" + s };
	    throw new WTException(queryexception, "wt.query.queryResource", "26", aobj2);
	}
    }

    /**
     * @param spec
     * @param sortingClass
     * @param tableNo
     * @param field
     * @param sortingFlag
     * @throws WTPropertyVetoException
     * @throws QueryException
     * @throws WTIntrospectionException
     */
    public static void setOrderBy(QuerySpec spec, Class<?> sortingClass, int tableNo, String field, boolean sortingFlag) throws WTPropertyVetoException, QueryException, WTIntrospectionException {
	spec.appendOrderBy(new OrderBy(new ClassAttribute(sortingClass, field), sortingFlag), new int[] { tableNo });
    }

    /**
     * @param spec
     * @param sortingClass
     * @param tableNo
     * @param field
     * @param sort
     * @throws WTPropertyVetoException
     * @throws QueryException
     * @throws WTIntrospectionException
     */
    public static void setOrderBy(QuerySpec spec, Class<?> sortingClass, int tableNo, String field, String sort) throws WTPropertyVetoException, QueryException, WTIntrospectionException {
	boolean isDescSort = (sort == null || "desc".equalsIgnoreCase(sort)) ? true : false;
	spec.appendOrderBy(new OrderBy(new ClassAttribute(sortingClass, field), isDescSort), new int[] { tableNo });
    }

    /**
     * @param spec
     * @param sortingClass
     * @param tableNo
     * @param field
     * @param aliasName
     * @param sortingFlag
     * @throws WTPropertyVetoException
     * @throws QueryException
     * @throws WTIntrospectionException
     */
    public static void setOrderBy(QuerySpec spec, Class<?> sortingClass, int tableNo, String field, String aliasName, boolean sortingFlag) throws WTPropertyVetoException, QueryException,
	    WTIntrospectionException {
	ClassAttribute classattribute = new ClassAttribute(sortingClass, field);
	classattribute.setColumnAlias(aliasName + String.valueOf(0));
	int[] fieldNoArr = { tableNo };
	spec.appendSelect(classattribute, fieldNoArr, false);
	OrderBy orderby = new OrderBy(classattribute, sortingFlag, null);
	spec.appendOrderBy(orderby, new int[] { tableNo });

	// spec.appendOrderBy(new OrderBy(new ClassAttribute(sortingClass,
	// field), sortingFlag), new int[] { fieldNo });

    }

    /**
     * @param folder
     * @param spec
     * @param targetClass
     * @param targetClassPos
     * @param isFirst
     * @throws Exception
     */
    public static void searchInFolder(Folder folder, QuerySpec spec, Class<?> targetClass, int targetClassPos, boolean isFirst) throws Exception {
	if (!isFirst) spec.appendOr();

	long longFolder = CommonUtil.getOIDLongValue(folder);
	spec.appendWhere(new SearchCondition(targetClass, "thePersistInfo.theObjectIdentifier.id", " LIKE ", longFolder), new int[] { targetClassPos });
	SortedEnumeration en = FolderTaskLogic.getSubFolders(folder);
	while (en.hasMoreElements()) {
	    Folder subFolder = (Folder) en.nextElement();
	    searchInFolder(subFolder, spec, targetClass, targetClassPos, false);
	}
    }

    /**
     * @param folder
     * @param spec
     * @param targetClass
     * @param targetClassPos
     * @throws Exception
     */
    public static void searchInOnlyFolder(Folder folder, QuerySpec spec, Class<?> targetClass, int targetClassPos) throws Exception {
	if (spec.getConditionCount() > 0) spec.appendAnd();
	long longFolder = CommonUtil.getOIDLongValue(folder);
	spec.appendWhere(new SearchCondition(targetClass, "thePersistInfo.theObjectIdentifier.id", " LIKE ", longFolder), new int[] { targetClassPos });
    }

    /**
     * @param targetClass
     * @param linkClass
     * @param isRole_A
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public static QuerySpec makeOuterJoinQuerySpec(Class<?> targetClass, Class<?> linkClass, boolean isRole_A) throws Exception {
	QuerySpec mainQuery = new QuerySpec();

	int classIndex = mainQuery.appendClassList(targetClass, true);

	QuerySpec subQuery = new QuerySpec();
	subQuery.getFromClause().setAliasPrefix("B");
	int linkIndex = subQuery.appendClassList(linkClass, false);
	subQuery.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, linkIndex, true);

	TableExpression[] tables = new TableExpression[2];
	String[] aliases = new String[2];
	tables[0] = mainQuery.getFromClause().getTableExpressionAt(classIndex);
	aliases[0] = mainQuery.getFromClause().getAliasAt(classIndex);
	tables[1] = subQuery.getFromClause().getTableExpressionAt(linkIndex);
	aliases[1] = subQuery.getFromClause().getAliasAt(linkIndex);

	SearchCondition correlatedJoin = null;

	if (isRole_A) {
	    correlatedJoin = new SearchCondition(targetClass, WTAttributeNameIfc.ID_NAME, linkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID);
	}
	else {
	    correlatedJoin = new SearchCondition(targetClass, WTAttributeNameIfc.ID_NAME, linkClass, WTAttributeNameIfc.ROLEB_OBJECT_ID);
	}
	subQuery.appendWhere(correlatedJoin, tables, aliases);
	mainQuery.appendWhere(new NegatedExpression(new ExistsExpression(subQuery)));

	// outer join query
	if (validatedIteration(targetClass)) {
	    mainQuery.appendAnd();
	    mainQuery.appendWhere(new SearchCondition(targetClass, "iterationInfo.latest", "TRUE"), new int[] { classIndex });
	}

	return mainQuery;
    }

    private static boolean validatedIteration(Class<?> targetClass) throws Exception {
	try {
	    @SuppressWarnings("unused")
	    Iterated iterated = (Iterated) targetClass.newInstance();
	    return true;
	} catch (ClassCastException e) {
	    return false;
	}
    }

    /**
     * @param querySpec
     * @param fromClass
     * @param fieldName
     * @param fieldValue
     * @param idx
     * @throws QueryException
     */
    public static void appendLIKE(QuerySpec querySpec, Class<?> fromClass, String fieldName, String fieldValue, int idx) throws QueryException {
	if (StringUtil.checkString(fieldValue)) {
	    //	    if (fieldValue.indexOf("*") > 0) fieldValue = fieldValue.replaceAll("\\*", "%");
	    fieldValue = StringUtil.changeString(fieldValue, "*", "%");
	    if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(fromClass, fieldName, " LIKE ", fieldValue, false), new int[] { idx });
	}
    }

    public static void appendLIKE(QuerySpec querySpec, Class<?> fromClass, String fieldName, String fieldValue, int idx, boolean casesensitive) throws QueryException {
	if (StringUtil.checkString(fieldValue)) {
	    //	    if (fieldValue.indexOf("*") > 0) fieldValue = fieldValue.replaceAll("\\*", "%");
	    fieldValue = StringUtil.changeString(fieldValue, "*", "%");
	    if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(fromClass, fieldName, " LIKE ", fieldValue, false), new int[] { idx });
	}
    }

    public static void appendSLIKE(QuerySpec querySpec, Class<?> fromClass, String fieldName, String fieldValue, int idx) throws QueryException {
	if (StringUtil.checkString(fieldValue)) {
	    //	    if (fieldValue.indexOf("*") > 0) fieldValue = fieldValue.replaceAll("\\*", "%");
	    if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(fromClass, fieldName, " LIKE ", fieldValue + "%", false), new int[] { idx });
	}
    }

    public static void appendSLIKE(QuerySpec querySpec, Class<?> fromClass, String fieldName, String fieldValue, int idx, boolean casesensitive) throws QueryException {
	if (StringUtil.checkString(fieldValue)) {
	    //	    if (fieldValue.indexOf("*") > 0) fieldValue = fieldValue.replaceAll("\\*", "%");
	    if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(fromClass, fieldName, " LIKE ", fieldValue + "%", false), new int[] { idx });
	}
    }

    public static void appendTimeFrom(QuerySpec querySpec, Class<?> fromClass, String fieldName, Timestamp fieldValue, int idx) throws QueryException {
	if (fieldValue != null) {
	    if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(fromClass, fieldName, SearchCondition.GREATER_THAN, fieldValue), new int[] { idx });
	}
    }

    public static void appendTimeTo(QuerySpec querySpec, Class<?> fromClass, String fieldName, Timestamp fieldValue, int idx) throws QueryException {
	if (fieldValue != null) {
	    if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(fromClass, fieldName, SearchCondition.LESS_THAN, fieldValue), new int[] { idx });
	}
    }

    public static void appendGREATER_THAN(QuerySpec querySpec, Class<?> fromClass, String fieldName, int fieldValue, int idx) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(fromClass, fieldName, SearchCondition.GREATER_THAN, fieldValue), new int[] { idx });
    }

    public static void appendLESS_THAN(QuerySpec querySpec, Class<?> fromClass, String fieldName, int fieldValue, int idx) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(fromClass, fieldName, SearchCondition.LESS_THAN, fieldValue), new int[] { idx });
    }

    /**
     * @param querySpec
     * @param fromClass
     * @param fieldName
     * @param fieldValue
     * @param idx
     * @throws QueryException
     */
    public static void appendEQUAL(QuerySpec querySpec, Class<?> fromClass, String fieldName, String fieldValue, int idx) throws QueryException {
	if (StringUtil.checkString(fieldValue)) {
	    if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(fromClass, fieldName, "=", fieldValue, false), new int[] { idx });
	}
    }

    public static void appendEQUAL(QuerySpec querySpec, Class<?> fromClass, String fieldName, String fieldValue, int idx, boolean casesensitive) throws QueryException {
	if (StringUtil.checkString(fieldValue)) {
	    if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(fromClass, fieldName, "=", fieldValue, casesensitive), new int[] { idx });
	}
    }

    /**
     * @param querySpec
     * @param fromClass
     * @param fieldName
     * @param fieldValue
     * @param idx
     * @throws QueryException
     */
    public static void appendEQUAL(QuerySpec querySpec, Class<?> fromClass, String fieldName, int fieldValue, int idx) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(fromClass, fieldName, "=", fieldValue), new int[] { idx });
    }

    /**
     * @param querySpec
     * @param fromClass
     * @param fieldName
     * @param fieldValue
     * @param idx
     * @throws QueryException
     */
    public static void appendNOTEQUAL(QuerySpec querySpec, Class<?> fromClass, String fieldName, int fieldValue, int idx) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(fromClass, fieldName, "<>", fieldValue), new int[] { idx });
    }

    /**
     * @param querySpec
     * @param fromClass
     * @param fieldName
     * @param fieldValue
     * @param idx
     * @throws QueryException
     */
    public static void appendEQUAL(QuerySpec querySpec, Class<?> fromClass, String fieldName, long fieldValue, int idx) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(fromClass, fieldName, "=", fieldValue), new int[] { idx });
    }

    /**
     * @param querySpec
     * @param fromClass
     * @param fieldName
     * @param fieldValue
     * @param idx
     * @throws QueryException
     */
    public static void appendNOTEQUAL(QuerySpec querySpec, Class<?> fromClass, String fieldName, long fieldValue, int idx) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(fromClass, fieldName, "<>", fieldValue), new int[] { idx });
    }

    /**
     * @param querySpec
     * @param fromClass
     * @param fieldName
     * @param fieldValue
     * @param idx
     * @throws QueryException
     */
    public static void appendNOTEQUAL(QuerySpec querySpec, Class<?> fromClass, String fieldName, String fieldValue, int idx) throws QueryException {
	if (StringUtil.checkString(fieldValue)) {
	    if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	    querySpec.appendWhere(new SearchCondition(fromClass, fieldName, "<>", fieldValue, false), new int[] { idx });
	}
    }

    public static void appendNOTEQUAL(QuerySpec querySpec, Class<?> fromClass, String fieldName, String fieldValue, int idx, boolean casesensitive) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(fromClass, fieldName, "<>", fieldValue, casesensitive), new int[] { idx });
    }

    /**
     * @param querySpec
     * @param fromClass
     * @param fieldName
     * @param booleanValue
     * @param idx
     * @throws QueryException
     */
    public static void appendBOOLEAN(QuerySpec querySpec, Class<?> fromClass, String fieldName, String booleanValue, int idx) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(fromClass, fieldName, booleanValue), new int[] { idx });
    }

    /**
     * @param querySpec
     * @param fromClass
     * @param fieldName
     * @param nullvalue
     * @param idx
     * @throws QueryException
     * @메소드명 : appendNULL
     * @작성자 : Jason, Han
     * @작성일 : 2014. 8. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public static void appendNULL(QuerySpec querySpec, Class<?> fromClass, String fieldName, String nullvalue, boolean flag, int idx) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(fromClass, fieldName, nullvalue, flag), new int[] { idx });
    }

    /**
     * @param querySpec
     * @param roleAClass
     * @param roleAfieldName
     * @param roleAidx
     * @param roleBClass
     * @param roleBfieldName
     * @param roleBidx
     * @throws QueryException
     * @메소드명 : appendEQUALJoin
     * @작성자 : Jason, Han
     * @작성일 : 2014. 8. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public static void appendEQUALJoin(QuerySpec querySpec, Class<?> roleAClass, String roleAfieldName, int roleAidx, Class<?> roleBClass, String roleBfieldName, int roleBidx) throws QueryException {
	if (querySpec.getConditionCount() > 0) querySpec.appendAnd();
	querySpec.appendWhere(new SearchCondition(new ClassAttribute(roleAClass, roleAfieldName), "=", new ClassAttribute(roleBClass, roleBfieldName)), new int[] { roleAidx, roleBidx });
    }
}
