package e3ps.edm.beans;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.Cabinet;
import wt.folder.Folder;
import wt.folder.Foldered;
import wt.folder.IteratedFolderMemberLink;
import wt.folder.SubFolder;
import wt.iba.definition.StringDefinition;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.value.IBAHolder;
import wt.iba.value.StringValue;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.introspection.WTIntrospector;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.part.WTPartMaster;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.CompoundQuerySpec;
import wt.query.ConstantExpression;
import wt.query.ExistsExpression;
import wt.query.KeywordExpression;
import wt.query.NegatedExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.query.SubSelectExpression;
import wt.util.WTException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.views.ViewManageable;
import e3ps.common.code.NumberCode;
import e3ps.common.folder.FolderUtil;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.edm.CADCategory;
import e3ps.edm.DrawingToModelReferenceLink;
import e3ps.edm.EDMUserData;
import e3ps.edm.EPMDocProjectLink;
import e3ps.edm.EPMDocTypeCodeLink;
import e3ps.edm.EPMLink;
import e3ps.edm.ModelReferenceLink;
import e3ps.edm.PartToEPMLink;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.VersionHelper;
import e3ps.groupware.company.People;
import e3ps.project.E3PSProjectMaster;
import ext.ket.shared.log.Kogger;

public class EDMQueryHelper implements wt.method.RemoteAccess, java.io.Serializable {
    static final boolean  SERVER     = wt.method.RemoteMethodServer.ServerFlag;

    private static String ibaIdAlias = "ibaID";

    public static boolean isRunningData(EPMDocument doc) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { EPMDocument.class };
	    Object args[] = new Object[] { doc };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("isRunningData", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return ((Boolean) obj).booleanValue();
	}

	try {

	    String ida2a2 = "ida2a2";
	    long mstid = PersistenceHelper.getObjectIdentifier(doc.getMaster()).getId();

	    //ModelReferenceLink
	    QuerySpec qs0 = new QuerySpec();
	    int idx0 = qs0.appendClassList(ModelReferenceLink.class, false);

	    ClassAttribute ca = new ClassAttribute(ModelReferenceLink.class, "thePersistInfo.theObjectIdentifier.id");
	    ca.setColumnAlias(ida2a2);
	    qs0.appendSelect(ca, new int[] { idx0 }, false);

	    qs0.appendWhere(new SearchCondition(ModelReferenceLink.class, "roleAObjectRef.key.id", "=", mstid), new int[] { idx0 });
	    qs0.appendOr();
	    qs0.appendWhere(new SearchCondition(ModelReferenceLink.class, "roleBObjectRef.key.id", "=", mstid), new int[] { idx0 });


	    //EPMLink
	    QuerySpec qs1 = new QuerySpec();
	    int idx1 = qs1.appendClassList(EPMLink.class, false);

	    ca = new ClassAttribute(EPMLink.class, "thePersistInfo.theObjectIdentifier.id");
	    ca.setColumnAlias(ida2a2);
	    qs1.appendSelect(ca, new int[] { idx1 }, false);

	    qs1.appendWhere(new SearchCondition(EPMLink.class, "roleAObjectRef.key.id", "=", mstid), new int[] { idx1 });


	    //DrawingToModelReferenceLink
	    QuerySpec qs2 = new QuerySpec();
	    int idx2 = qs2.appendClassList(DrawingToModelReferenceLink.class, false);
	    int idx2_1 = qs2.appendClassList(EPMDocument.class, false);

	    ca = new ClassAttribute(DrawingToModelReferenceLink.class, "thePersistInfo.theObjectIdentifier.id");
	    ca.setColumnAlias(ida2a2);
	    qs2.appendSelect(ca, new int[] { idx2 }, false);

	    qs2.appendWhere(new SearchCondition(EPMDocument.class, "masterReference.key.id", "=", mstid), new int[] { idx2_1 });
	    qs2.appendAnd();

	    qs2.appendOpenParen();//(
	    qs2.appendOpenParen();//(
	    SearchCondition sc = new SearchCondition(new ClassAttribute(EPMDocument.class, "iterationInfo.branchId"), "=", new ClassAttribute(DrawingToModelReferenceLink.class,
		    "roleBObjectRef.key.branchId"));
	    sc.setFromIndicies(new int[] { idx2_1, idx2 }, 0);
	    sc.setOuterJoin(0);
	    qs2.appendWhere(sc, new int[] { idx2_1, idx2 });
	    qs2.appendCloseParen();//)

	    qs2.appendOr();

	    qs2.appendOpenParen();//(
	    sc = new SearchCondition(new ClassAttribute(EPMDocument.class, "iterationInfo.branchId"), "=", new ClassAttribute(DrawingToModelReferenceLink.class, "roleAObjectRef.key.branchId"));
	    sc.setFromIndicies(new int[] { idx2_1, idx2 }, 0);
	    sc.setOuterJoin(0);
	    qs2.appendWhere(sc, new int[] { idx2_1, idx2 });
	    qs2.appendCloseParen();//)
	    qs2.appendCloseParen();//)


	    //PartToEPMLink
	    QuerySpec qs3 = new QuerySpec();
	    int idx3 = qs3.appendClassList(PartToEPMLink.class, false);
	    int idx3_1 = qs3.appendClassList(EPMDocument.class, false);

	    ca = new ClassAttribute(PartToEPMLink.class, "thePersistInfo.theObjectIdentifier.id");
	    ca.setColumnAlias(ida2a2);
	    qs3.appendSelect(ca, new int[] { idx3 }, false);

	    qs3.appendWhere(new SearchCondition(EPMDocument.class, "masterReference.key.id", "=", mstid), new int[] { idx3_1 });
	    qs3.appendAnd();

	    qs3.appendOpenParen();//(
	    sc = new SearchCondition(new ClassAttribute(EPMDocument.class, "iterationInfo.branchId"), "=", new ClassAttribute(PartToEPMLink.class, "roleAObjectRef.key.branchId"));
	    sc.setFromIndicies(new int[] { idx3_1, idx3 }, 0);
	    sc.setOuterJoin(0);
	    qs3.appendWhere(sc, new int[] { idx3_1, idx3 });
	    qs3.appendCloseParen();//)


	    CompoundQuerySpec compound = new CompoundQuerySpec();
	    compound.setSetOperator(SetOperator.UNION_ALL);
	    compound.addComponent(qs0);
	    compound.addComponent(qs1);
	    compound.addComponent(qs2);
	    compound.addComponent(qs3);


	    QuerySpec spec = new QuerySpec();
	    int idx_main = spec.appendFrom(new SubSelectExpression(compound));

	    KeywordExpression rnumCol = new KeywordExpression("count(*)");
	    rnumCol.setFromAlias(new String[] { spec.getFromClause().getAliasAt(idx_main) }, 0);
	    spec.appendSelect(rnumCol, false);

	    spec.setAdvancedQueryEnabled(true);
	    QueryResult qr = PersistenceServerHelper.manager.query(spec);

	    int count = 0;
	    if (qr.hasMoreElements()) {
		Object[] oo = (Object[]) qr.nextElement();
		BigDecimal bd = (BigDecimal) oo[0];
		count = bd.intValue();
	    }

	    if (count > 0) {
		return true;
	    }
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}
	return false;
    }

    public static QueryResult find(Map map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Map.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("find", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}

	try {
	    return serverQuery(getQuerySpec(map));
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}

	return null;
    }

    /*
     * 결과내 재검색용(Search In Result)
     */
    public static QueryResult findSIR(List mapList) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { List.class };
	    Object args[] = new Object[] { mapList };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("findSIR", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}

	try {
	    return serverQuery(getQuerySpecSIR(mapList));
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}

	return null;
    }

    public static QueryResult serverQuery(QuerySpec spec) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { QuerySpec.class };
	    Object args[] = new Object[] { spec };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("serverQuery", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}

	try {
	    return PersistenceServerHelper.manager.query(spec);
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}

	return null;
    }

    public static QueryResult find(QuerySpec spec) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { QuerySpec.class };
	    Object args[] = new Object[] { spec };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("find", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}

	try {
	    return PersistenceHelper.manager.find(spec);
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}

	return null;
    }

    public static PagingQueryResult openPagingSession(HashMap map, int start, int size) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class, int.class, int.class };
	    Object args[] = new Object[] { map, new Integer(start), new Integer(size) };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("openPagingSession", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return (PagingQueryResult) obj;
	}
	PagingQueryResult result = null;
	try {
	    QuerySpec qs = getQuerySpec(map);
	    result = openPagingSession(start, size, qs);
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}
	return result;
    }

    // 2013.05.14 shkim added
    public static PagingQueryResult openPagingSession2(HashMap map, int start, int size) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class, int.class, int.class };
	    Object args[] = new Object[] { map, new Integer(start), new Integer(size) };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("openPagingSession2", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return (PagingQueryResult) obj;
	}
	PagingQueryResult result = null;
	try {
	    QuerySpec qs = getQuerySpec2(map);
	    result = openPagingSession(start, size, qs);
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}
	return result;
    }

    public static PagingQueryResult openPagingSession(int start, int size, QuerySpec spec) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { int.class, int.class, QuerySpec.class };
	    Object args[] = new Object[] { new Integer(start), new Integer(size), spec };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("openPagingSession", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return (PagingQueryResult) obj;
	}
	PagingQueryResult result = null;
	try {
	    result = PagingSessionHelper.openPagingSession(start, size, spec);

	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}
	return result;
    }

    public static PagingQueryResult fetchPagingSession(int start, int size, long sessionId) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { int.class, int.class, long.class };
	    Object args[] = new Object[] { new Integer(start), new Integer(size), new Long(sessionId) };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("fetchPagingSession", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return (PagingQueryResult) obj;
	}
	PagingQueryResult result = null;
	try {
	    result = PagingSessionHelper.fetchPagingSession(start, size, sessionId);
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}
	return result;
    }

    /*
     * [PLM System 1차개선]
     * 수정내용 : 다중검색/멀티콤보 적용
     * 수정일자 : 2013. 06. 24
     * 수정자 : 김무준
     */
    public static QuerySpec getQuerySpec(Map _map) throws Exception {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);
	Kogger.debug(EDMPBOHelper.class, "=======================================================getQuerySpec: map=" + map);

	QuerySpec spec = null;
	try {
	    String className = map.getString("className");

	    String command = map.getString("command");

	    String number = map.getString("number");
	    String name = map.getString("name");

	    String[] creatorAry = map.getStringArray("creator");
	    String modifier = map.getString("modifier");

	    String createStart = map.getString("create_start");
	    String createEnd = map.getString("create_end");
	    String modifyStart = map.getString("modify_start");
	    String modifyEnd = map.getString("modify_end");

	    String latest = map.getString("latest");
	    if (latest.length() == 0) {
		latest = String.valueOf(true);
	    }

	    ReferenceFactory rf = new ReferenceFactory();

	    Class cls_itr = Class.forName(className);
	    Class cls_master = WTIntrospector.getClassInfo(cls_itr).getOtherSideRole("master").getValidClassInfo().getBusinessClass();
	    /*
	    RoleDescriptor[] rdarr = WTIntrospector.getClassInfo(cls_itr).getOtherSideRoles();
	    for(int i = 0; i < rdarr.length; i++) {
	        if("master".equals(rdarr[i].getName())) {
	            ClassInfo mstclsinfo = WTIntrospector.getClassInfo(rdarr[i].getValidClassInfo().getBusinessClass());
	            Class itrcls = mstclsinfo.getOtherSideRole("iteration").getValidClassInfo().getBusinessClass();
	            if(itrcls.isAssignableFrom(cls_itr)) {
	                cls_master = rdarr[i].getValidClassInfo().getBusinessClass();
	            }
	        }
	    }
	    */

	    spec = new QuerySpec();
	    //spec.getFromClause().setAliasPrefix("S");

	    spec.setAdvancedQueryEnabled(true);
	    spec.setDescendantQuery(false);

	    int idx_m = spec.appendClassList(cls_master, false);
	    int idx_cls = spec.appendClassList(cls_itr, false);

	    spec.appendHint("RULE");

	    KeywordExpression ke = null;
	    ClassAttribute ca = null;

	    ClassAttribute attr0 = new ClassAttribute(cls_itr, "thePersistInfo.theObjectIdentifier.classname");
	    ClassAttribute attr1 = new ClassAttribute(cls_itr, "thePersistInfo.theObjectIdentifier.id");
	    String alias0 = spec.getFromClause().getAliasAt(idx_cls);
	    spec.appendSelect(new KeywordExpression(alias0 + "." + attr0 + "||':'||" + alias0 + "." + attr1), new int[] { idx_cls }, false);

	    spec.appendSelect(new ClassAttribute(cls_master, "number"), new int[] { idx_m }, false);
	    spec.appendSelect(new ClassAttribute(cls_master, "name"), new int[] { idx_m }, false);
	    spec.appendSelect(new ClassAttribute(cls_itr, "versionInfo.identifier.versionId"), new int[] { idx_cls }, false);
	    spec.appendSelect(new ClassAttribute(cls_itr, "state.state"), new int[] { idx_cls }, false);
	    spec.appendSelect(new ClassAttribute(cls_itr, "thePersistInfo.createStamp"), new int[] { idx_cls }, false);
	    spec.appendSelect(new ClassAttribute(cls_itr, "thePersistInfo.modifyStamp"), new int[] { idx_cls }, false);


	    attr0 = new ClassAttribute(cls_itr, "iterationInfo.creator.key.classname");
	    attr1 = new ClassAttribute(cls_itr, "iterationInfo.creator.key.id");
	    spec.appendSelect(new KeywordExpression(alias0 + "." + attr0 + "||':'||" + alias0 + "." + attr1), new int[] { idx_cls }, false);

	    attr0 = new ClassAttribute(cls_itr, "iterationInfo.modifier.key.classname");
	    attr1 = new ClassAttribute(cls_itr, "iterationInfo.modifier.key.id");
	    spec.appendSelect(new KeywordExpression(alias0 + "." + attr0 + "||':'||" + alias0 + "." + attr1), new int[] { idx_cls }, false);

	    SQLFunction upper = null;
	    ColumnExpression ce = null;
	    SearchCondition sc = null;

	    if (number.length() > 0) {
		KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_master, idx_m, "number", number, true);
	    }

	    if (name.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_master, idx_m, "name", name, true);
	    }

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(cls_master, "thePersistInfo.theObjectIdentifier.id", cls_itr, "masterReference.key.id"), new int[] { idx_m, idx_cls });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }

	    spec.appendWhere(wt.vc.VersionControlHelper.getSearchCondition(cls_itr, true), new int[] { idx_cls });


	    /*
	    if (creatorAry.length>0) {
	        if(spec.getConditionCount() > 0) { spec.appendAnd(); }

	        String[] creatorIdAry = new String[creatorAry.length];
	        for (int i = 0; i < creatorAry.length; ++i) {
	            creatorIdAry[i] = String.valueOf(CommonUtil.getOIDLongValue(creatorAry[i]));
	        }

	        KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_itr, idx_cls, "iterationInfo.creator.key.id", creatorIdAry, false);
	    }
	    */

	    ArrayList<String> creatorList = null;
	    int creatorAryLength = (creatorAry != null) ? creatorAry.length : 0;
	    for (int i = 0; i < creatorAryLength; ++i) {
		if (creatorAry[i] != null && !creatorAry[i].equals("")) {
		    if (creatorList == null) creatorList = new ArrayList<String>();
		    creatorList.add(creatorAry[i]);
		}
	    }
	    if (creatorList != null && creatorList.size() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		String[] creatorIdAry = new String[creatorList.size()];
		for (int i = 0; i < creatorList.size(); ++i) {
		    creatorIdAry[i] = String.valueOf(CommonUtil.getOIDLongValue(creatorList.get(i)));
		}

		KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_itr, idx_cls, "iterationInfo.creator.key.id", creatorIdAry, false);
	    }


	    if (createStart.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.createStamp", ">=", DateUtil.convertStartDate2(createStart.trim())), new int[] { idx_cls });
	    }

	    if (createEnd.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.createStamp", "<", DateUtil.convertEndDate2(createEnd.trim())), new int[] { idx_cls });
	    }

	    if (modifier.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		wt.org.WTUser user = (wt.org.WTUser) rf.getReference(modifier).getObject();
		spec.appendWhere(new SearchCondition(cls_itr, "iterationInfo.modifier.key.id", "=", user.getPersistInfo().getObjectIdentifier().getId()), new int[] { idx_cls });
	    }

	    if (modifyStart.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.modifyStamp", ">=", DateUtil.convertStartDate2(modifyStart.trim())), new int[] { idx_cls });
	    }

	    if (modifyEnd.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.modifyStamp", "<", DateUtil.convertEndDate2(modifyEnd.trim())), new int[] { idx_cls });
	    }

	    VersionHelper.appendFilterTerminalsAndWorkingCopies(cls_itr, spec, new int[] { idx_cls });

	    if ("wt.part.WTPart".equals(cls_itr.getName())) {
		String viewName = map.getString("view");

		View view = null;
		if (viewName.length() > 0) {
		    view = ViewHelper.service.getView(viewName);
		}

		if (view != null) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendWhere(new SearchCondition(cls_itr, "view.key.id", "=", view.getPersistInfo().getObjectIdentifier().getId()), new int[] { idx_cls });
		}
	    }

	    if ("wt.epm.EPMDocument".equals(cls_itr.getName())) {

		String partNumber = map.getString("partNumber");
		String edmCreateDeptName = map.getString("edmCreateDeptName");
		String edmModifyDeptName = map.getString("edmModifyDeptName");

		String projectNo = map.getString("projectNo");

		String[] businessTypeAry = map.getStringArray("businessType");

		String[] oas = map.getStringArray("ownerApplication");
		String[] aas = map.getStringArray("authoringApplication");


		if (oas != null && oas.length > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    sc = new SearchCondition(cls_master, "ownerApplication", oas, true);
		    spec.appendWhere(sc, new int[] { idx_m });
		}

		if (aas != null && aas.length > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    sc = new SearchCondition(cls_master, "authoringApplication", aas, true);
		    spec.appendWhere(sc, new int[] { idx_m });
		}

		//작성부서.
		if ((edmCreateDeptName.length() > 0) || (edmModifyDeptName.length() > 0)) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendOpenParen();

		    int idx_ud = spec.appendClassList(EDMUserData.class, false);

		    SearchCondition udsc = new SearchCondition(new ClassAttribute(cls_itr, "iterationInfo.branchId"), "=", new ClassAttribute(EDMUserData.class, EDMUserData.OBJ_BRANCH_ID));
		    udsc.setFromIndicies(new int[] { idx_cls, idx_ud }, 0);
		    udsc.setOuterJoin(0);
		    spec.appendWhere(udsc, new int[] { idx_cls, idx_ud });

		    if (edmCreateDeptName.length() > 0) {
			spec.appendAnd();

			upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(EDMUserData.class, EDMUserData.CREATOR_DEPT_NAME));

			sc = new SearchCondition(upper, SearchCondition.LIKE, ConstantExpression.newExpression(likeCharConvert(edmCreateDeptName).toUpperCase()));
			spec.appendWhere(sc, new int[] { idx_ud });
		    }

		    if (edmModifyDeptName.length() > 0) {
			spec.appendAnd();

			upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(EDMUserData.class, EDMUserData.MODIFIER_DEPT_NAME));

			sc = new SearchCondition(upper, SearchCondition.LIKE, ConstantExpression.newExpression(likeCharConvert(edmModifyDeptName).toUpperCase()));
			spec.appendWhere(sc, new int[] { idx_ud });
		    }

		    spec.appendCloseParen();
		}

		if (partNumber.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendOpenParen();

		    int idx_epmlink = spec.appendClassList(EPMLink.class, false);
		    int idx_pmst = spec.appendClassList(WTPartMaster.class, false);

		    spec.appendWhere(new SearchCondition(cls_master, "thePersistInfo.theObjectIdentifier.id", EPMLink.class, "roleAObjectRef.key.id"), new int[] { idx_m, idx_epmlink });
		    spec.appendAnd();

		    //spec.appendWhere(new SearchCondition(EPMLink.class, "referenceType",
		    //        SearchCondition.NOT_EQUAL, EDMHelper.REFERENCE_TYPE_MODEL), new int[]{idx_epmlink});
		    //spec.appendAnd();

		    spec.appendWhere(new SearchCondition(EPMLink.class, "roleBObjectRef.key.id", WTPartMaster.class, "thePersistInfo.theObjectIdentifier.id"), new int[] { idx_epmlink, idx_pmst });
		    spec.appendAnd();

		    KETQueryUtil.setQuerySpecForMultiSearch(spec, WTPartMaster.class, idx_pmst, "number", partNumber, true);

		    spec.appendCloseParen();
		}

		// TODO TKLEE 3D도면 결재시 쿼리 수정 필요
		//		if (businessTypeAry.length > 0) {
		//		    if (spec.getConditionCount() > 0) {
		//			spec.appendAnd();
		//		    }
		//		    spec.appendOpenParen();
		//
		//		    for (String businessType : businessTypeAry) {
		//			NumberCode bizCode = (NumberCode) rf.getReference(businessType).getObject();
		//			int idx_codelink = spec.appendClassList(EPMDocTypeCodeLink.class, false);
		//
		//			spec.appendWhere(new SearchCondition(cls_itr, "iterationInfo.branchId", EPMDocTypeCodeLink.class, "roleBObjectRef.key.branchId"), new int[] { idx_cls, idx_codelink });
		//
		//			spec.appendAnd();
		//
		//			spec.appendWhere(new SearchCondition(EPMDocTypeCodeLink.class, "roleAObjectRef.key.id", "=", bizCode.getPersistInfo().getObjectIdentifier().getId()),
		//			        new int[] { idx_codelink });
		//		    }
		//
		//		    spec.appendCloseParen();
		//		}

		if (projectNo.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    Class cls_pjtlink = EPMDocProjectLink.class;
		    Class cls_pjt = E3PSProjectMaster.class;

		    int idx_pjtlink = spec.appendClassList(cls_pjtlink, false);
		    int idx_pjt = spec.appendClassList(cls_pjt, false);

		    spec.appendOpenParen();

		    spec.appendWhere(new SearchCondition(cls_itr, "iterationInfo.branchId", cls_pjtlink, "roleBObjectRef.key.branchId"), new int[] { idx_cls, idx_pjtlink });

		    spec.appendAnd();

		    spec.appendWhere(new SearchCondition(cls_pjtlink, "roleAObjectRef.key.id", cls_pjt, "thePersistInfo.theObjectIdentifier.id"), new int[] { idx_pjtlink, idx_pjt });

		    spec.appendAnd();

		    // Modified by MJOH, 2011-01-26
		    //                    spec.appendWhere(new SearchCondition(cls_pjt, E3PSProjectMaster.PJT_NO,
		    //                            "like", likeCharConvert(projectNo)), new int[] { idx_pjt });
		    KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_pjt, idx_pjt, E3PSProjectMaster.PJT_NO, projectNo, true);

		    spec.appendCloseParen();
		}
	    }

	    if (Foldered.class.isAssignableFrom(cls_itr)) {//if(FolderEntry.class.isAssignableFrom(cls_itr)) {
		String folderOid = map.getString("folderOid");
		if (folderOid.length() > 0) {
		    Folder folder = (Folder) (new ReferenceFactory()).getReference(folderOid).getObject();
		    if (folder instanceof SubFolder) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}

			Vector folders = new Vector();
			folders.add(folder);
			FolderUtil.getSubFolderList(folder, folders);

			int f_idx = spec.appendClassList(IteratedFolderMemberLink.class, false);

			spec.appendOpenParen();

			for (int k = 0; k < folders.size(); k++) {
			    Folder sf = (Folder) folders.get(k);
			    if (k > 0) {
				spec.appendOr();
			    }
			    spec.appendWhere(new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, sf.getPersistInfo().getObjectIdentifier().getId()),
				    new int[] { f_idx });
			}
			spec.appendCloseParen();

			spec.appendAnd();

			sc = new SearchCondition(new ClassAttribute(IteratedFolderMemberLink.class, "roleBObjectRef.key.branchId"), "=", new ClassAttribute(cls_itr, "iterationInfo.branchId"));
			sc.setFromIndicies(new int[] { f_idx, idx_cls }, 0);
			sc.setOuterJoin(0);
			spec.appendWhere(sc, new int[] { f_idx, idx_cls });

		    }
		    else if (folder instanceof Cabinet) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}

			sc = new SearchCondition(cls_itr, "folderingInfo.cabinet.key.id", "=", folder.getPersistInfo().getObjectIdentifier().getId());
			spec.appendWhere(sc, new int[] { idx_cls });
		    }
		}
	    }

	    if (WTContained.class.isAssignableFrom(cls_itr)) {
		String containerOid = map.getString("containerOid");

		if (containerOid.length() > 0) {
		    WTContainer container = (WTContainer) rf.getReference(containerOid).getObject();

		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    sc = new SearchCondition(cls_itr, "containerReference.key.id", "=", container.getPersistInfo().getObjectIdentifier().getId());
		    spec.appendWhere(sc, new int[] { idx_cls });
		}
	    }

	    //상태 조건.
	    if (LifeCycleManaged.class.isAssignableFrom(cls_itr)) {
		//String state = StringUtil.trimToEmpty((String)map.get("state"));

		String[] state = null;
		//                Object stateobj = map.get("state");
		//                if(stateobj != null) {
		//                    if(stateobj instanceof String[]) {
		//                        state = (String[])stateobj;
		//                    } else if(stateobj instanceof String) {
		//                        if( ((String)stateobj).trim().length() > 0 ) {
		//                            state = new String[1];
		//                            state[0] = (String)stateobj;
		//                        }
		//                    }
		//                }
		state = map.getStringArray("state");

		if ((state != null) && (state.length > 0)) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    /*
		    spec.appendWhere(
		            new SearchCondition(cls_itr, "state.state",state, false),
		            new int[]{idx_cls});
		    */
		    KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_itr, idx_cls, "state.state", state, true);
		}

		String[] notState = null;
		Object notstateobj = map.get("notstate");
		if (notstateobj != null) {
		    if (notstateobj instanceof String[]) {
			notState = (String[]) notstateobj;
		    }
		    else if (notstateobj instanceof String) {
			if (((String) notstateobj).trim().length() > 0) {
			    notState = new String[1];
			    notState[0] = (String) notstateobj;
			}
		    }
		}

		if ((notState != null) && (notState.length > 0)) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    /*
		    spec.appendWhere(
		            new SearchCondition(cls_itr, "state.state",notState, false, true),
		            new int[]{idx_cls});
		    */
		    spec.appendOpenParen();
		    for (int i = 0; i < notState.length; i++) {
			if (i > 0) {
			    spec.appendAnd();
			}
			spec.appendWhere(new SearchCondition(cls_itr, "state.state", SearchCondition.NOT_EQUAL, State.toState(notState[i])), new int[] { idx_cls });
		    }
		    spec.appendCloseParen();
		}
	    }

	    if (Boolean.parseBoolean(latest)) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		Boolean paramBoolean = new Boolean(ViewManageable.class.isAssignableFrom(cls_itr));
		spec.appendWhere(new NegatedExpression(new ExistsExpression(VersionHelper.getSuccessorVersionQuerySpec(cls_itr, paramBoolean, spec, idx_cls))), null);
	    }

	    if (IBAHolder.class.isAssignableFrom(cls_itr)) {
		KETParamMapUtil ibaMap = KETParamMapUtil.getMap();

		AttributeDefDefaultView defDefaultView = null;

		ArrayList attrs = EDMAttributes.getAttributes();

		for (int i = 0; i < attrs.size(); i++) {
		    defDefaultView = (AttributeDefDefaultView) attrs.get(i);
		    String[] valueAry = map.getStringArray(defDefaultView.getName());
		    if (valueAry != null && valueAry.length > 0) {
			Kogger.debug(EDMPBOHelper.class, "getName() == " + defDefaultView.getName());
			ibaMap.put(defDefaultView.getName(), valueAry);
		    }
		}

		// IBA 관련 속성 검색 추가..
		if (ibaMap.size() > 0) {

		    Set set = ibaMap.keySet();
		    Iterator iter = set.iterator();

		    String key = "";
		    String[] valueAry;

		    while (iter.hasNext()) {
			key = (String) iter.next();
			valueAry = ibaMap.getStringArray(key);

			//Kogger.debug(getClass(), "key == " + key);
			//Kogger.debug(getClass(), "value == " + value);

			for (String value : valueAry) {
			    SearchUtil.addIBAQuery(spec, cls_itr, idx_cls, key, value);
			}
		    }
		}
	    }

	    Vector sorts = (Vector) map.get("sort");

	    if ((sorts != null) && (sorts.size() > 0)) {

		ClassAttribute sortCa = null;
		OrderBy orderby = null;
		int sortIdx = 0;

		for (int i = 0; i < sorts.size(); i++) {
		    Object so[] = (Object[]) sorts.get(i);
		    String keycolumn = (String) so[0];
		    Class sortcls = (Class) so[1];
		    Boolean descending = (Boolean) so[2];


		    if (sortcls.equals(People.class)) {
			Class cls_people = People.class;

			int idx_people = spec.appendClassList(cls_people, false);

			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}

			spec.appendOpenParen();
			spec.appendWhere(new SearchCondition(cls_itr, "iterationInfo.creator.key.id", cls_people, "userReference.key.id"), new int[] { idx_cls, idx_people });
			spec.appendCloseParen();

			sortCa = new ClassAttribute(cls_people, keycolumn);
			sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			spec.appendSelect(sortCa, new int[] { idx_people }, true);

			orderby = new OrderBy(sortCa, descending.booleanValue(), null);
			spec.appendOrderBy(orderby, new int[] { idx_people });
			continue;
		    }

		    if (sortcls.equals(StringDefinition.class)) {
			Class cls_ibavalue = StringValue.class;
			Class cls_ibadef = StringDefinition.class;

			int idx_ibavalue = spec.appendClassList(cls_ibavalue, false);
			int idx_ibadef = spec.appendClassList(cls_ibadef, false);

			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}

			spec.appendOpenParen();
			spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.theObjectIdentifier.id", cls_ibavalue, "theIBAHolderReference.key.id"), new int[] { idx_cls, idx_ibavalue });

			spec.appendAnd();

			spec.appendWhere(new SearchCondition(cls_ibavalue, "definitionReference.key.id", cls_ibadef, "thePersistInfo.theObjectIdentifier.id"), new int[] { idx_ibavalue, idx_ibadef });

			spec.appendAnd();

			spec.appendWhere(new SearchCondition(cls_ibadef, "name", "=", keycolumn), new int[] { idx_ibadef });

			spec.appendCloseParen();

			sortCa = new ClassAttribute(cls_ibavalue, "value");
			sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			spec.appendSelect(sortCa, new int[] { idx_ibavalue }, true);

			orderby = new OrderBy(sortCa, descending.booleanValue(), null);
			spec.appendOrderBy(orderby, new int[] { idx_ibavalue });
			continue;
		    }

		    for (int k = 0; k < spec.getFromClause().getCount(); k++) {
			Class lcls = spec.getFromClause().getTableExpressionAt(k).getTableClass();
			if ((lcls != null) && lcls.equals(sortcls)) {
			    sortCa = new ClassAttribute(sortcls, keycolumn);
			    //sortCa = new ClassAttribute(cls_itr, "thePersistInfo.modifyStamp");
			    sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			    spec.appendSelect(sortCa, new int[] { idx_m }, true);

			    orderby = new OrderBy(sortCa, descending.booleanValue(), null);
			    spec.appendOrderBy(orderby, new int[] { k });

			    continue;
			}
		    }
		}
	    }

	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}

	Kogger.debug(EDMPBOHelper.class, "EDMQueryHelper.getQuerySpec: 쿼리 == \n" + spec);
	return spec;
    }

    /*
     * 결과내 재검색용(Search In Result)
     */
    public static QuerySpec getQuerySpecSIR(List<Map> mapList) throws Exception {
	QuerySpec spec = null;
	try {
	    ReferenceFactory rf = new ReferenceFactory();

	    String className = null;
	    if (mapList.size() > 0) {
		KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(0));
		className = paramMap.getString("className"); // 고정값
	    }
	    Class cls_itr = Class.forName(className);
	    Class cls_master = WTIntrospector.getClassInfo(cls_itr).getOtherSideRole("master").getValidClassInfo().getBusinessClass();
	    /*
	    RoleDescriptor[] rdarr = WTIntrospector.getClassInfo(cls_itr).getOtherSideRoles();
	    for(int i = 0; i < rdarr.length; i++) {
	        if("master".equals(rdarr[i].getName())) {
	            ClassInfo mstclsinfo = WTIntrospector.getClassInfo(rdarr[i].getValidClassInfo().getBusinessClass());
	            Class itrcls = mstclsinfo.getOtherSideRole("iteration").getValidClassInfo().getBusinessClass();
	            if(itrcls.isAssignableFrom(cls_itr)) {
	                cls_master = rdarr[i].getValidClassInfo().getBusinessClass();
	            }
	        }
	    }
	    */

	    spec = new QuerySpec();
	    //spec.getFromClause().setAliasPrefix("S");

	    spec.setAdvancedQueryEnabled(true);
	    spec.setDescendantQuery(false);

	    int idx_m = spec.appendClassList(cls_master, false);
	    int idx_cls = spec.appendClassList(cls_itr, false);

	    spec.appendHint("RULE");

	    KeywordExpression ke = null;
	    ClassAttribute ca = null;

	    ClassAttribute attr0 = new ClassAttribute(cls_itr, "thePersistInfo.theObjectIdentifier.classname");
	    ClassAttribute attr1 = new ClassAttribute(cls_itr, "thePersistInfo.theObjectIdentifier.id");
	    String alias0 = spec.getFromClause().getAliasAt(idx_cls);
	    spec.appendSelect(new KeywordExpression(alias0 + "." + attr0 + "||':'||" + alias0 + "." + attr1), new int[] { idx_cls }, false);

	    spec.appendSelect(new ClassAttribute(cls_master, "number"), new int[] { idx_m }, false);
	    spec.appendSelect(new ClassAttribute(cls_master, "name"), new int[] { idx_m }, false);
	    spec.appendSelect(new ClassAttribute(cls_itr, "versionInfo.identifier.versionId"), new int[] { idx_cls }, false);
	    spec.appendSelect(new ClassAttribute(cls_itr, "state.state"), new int[] { idx_cls }, false);
	    spec.appendSelect(new ClassAttribute(cls_itr, "thePersistInfo.createStamp"), new int[] { idx_cls }, false);
	    spec.appendSelect(new ClassAttribute(cls_itr, "thePersistInfo.modifyStamp"), new int[] { idx_cls }, false);


	    attr0 = new ClassAttribute(cls_itr, "iterationInfo.creator.key.classname");
	    attr1 = new ClassAttribute(cls_itr, "iterationInfo.creator.key.id");
	    spec.appendSelect(new KeywordExpression(alias0 + "." + attr0 + "||':'||" + alias0 + "." + attr1), new int[] { idx_cls }, false);

	    attr0 = new ClassAttribute(cls_itr, "iterationInfo.modifier.key.classname");
	    attr1 = new ClassAttribute(cls_itr, "iterationInfo.modifier.key.id");
	    spec.appendSelect(new KeywordExpression(alias0 + "." + attr0 + "||':'||" + alias0 + "." + attr1), new int[] { idx_cls }, false);

	    SQLFunction upper = null;
	    ColumnExpression ce = null;
	    SearchCondition sc = null;

	    // 결과내 재검색 loop
	    for (int i = 0; i < mapList.size(); ++i) {
		KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		String number = paramMap.getString("number");
		if (number.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_master, idx_m, "number", number, true);
		}
	    }

	    // 결과내 재검색 loop
	    for (int i = 0; i < mapList.size(); ++i) {
		KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		String name = paramMap.getString("name");
		if (name.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_master, idx_m, "name", name, true);
		}
	    }

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(cls_master, "thePersistInfo.theObjectIdentifier.id", cls_itr, "masterReference.key.id"), new int[] { idx_m, idx_cls });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }

	    spec.appendWhere(wt.vc.VersionControlHelper.getSearchCondition(cls_itr, true), new int[] { idx_cls });

	    // 결과내 재검색 loop
	    for (int i = 0; i < mapList.size(); ++i) {
		KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));

		String[] creatorAry = paramMap.getStringArray("creator");
		if (creatorAry.length > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    String[] creatorIdAry = new String[creatorAry.length];
		    for (int j = 0; j < creatorAry.length; ++j) {
			creatorIdAry[j] = String.valueOf(CommonUtil.getOIDLongValue(creatorAry[j]));
		    }

		    KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_itr, idx_cls, "iterationInfo.creator.key.id", creatorIdAry, false);
		}

		String createStart = paramMap.getString("create_start");
		if (createStart.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.createStamp", ">=", DateUtil.convertStartDate2(createStart.trim())), new int[] { idx_cls });
		}

		String createEnd = paramMap.getString("create_end");
		if (createEnd.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.createStamp", "<", DateUtil.convertEndDate2(createEnd.trim())), new int[] { idx_cls });
		}
	    }

	    // 결과내 재검색 loop
	    for (int i = 0; i < mapList.size(); ++i) {
		KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));

		String modifier = paramMap.getString("modifier");
		if (modifier.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    wt.org.WTUser user = (wt.org.WTUser) rf.getReference(modifier).getObject();
		    spec.appendWhere(new SearchCondition(cls_itr, "iterationInfo.modifier.key.id", "=", user.getPersistInfo().getObjectIdentifier().getId()), new int[] { idx_cls });
		}

		String modifyStart = paramMap.getString("modify_start");
		if (modifyStart.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.modifyStamp", ">=", DateUtil.convertStartDate2(modifyStart.trim())), new int[] { idx_cls });
		}

		String modifyEnd = paramMap.getString("modify_end");
		if (modifyEnd.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.modifyStamp", "<", DateUtil.convertEndDate2(modifyEnd.trim())), new int[] { idx_cls });
		}
	    }

	    VersionHelper.appendFilterTerminalsAndWorkingCopies(cls_itr, spec, new int[] { idx_cls });

	    if ("wt.part.WTPart".equals(cls_itr.getName())) {
		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));

		    String viewName = paramMap.getString("view");

		    View view = null;
		    if (viewName.length() > 0) {
			view = ViewHelper.service.getView(viewName);
		    }

		    if (view != null) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}
			spec.appendWhere(new SearchCondition(cls_itr, "view.key.id", "=", view.getPersistInfo().getObjectIdentifier().getId()), new int[] { idx_cls });
		    }
		}
	    }

	    if ("wt.epm.EPMDocument".equals(cls_itr.getName())) {
		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    String[] oas = paramMap.getStringArray("ownerApplication");
		    if (oas != null && oas.length > 0) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}
			sc = new SearchCondition(cls_master, "ownerApplication", oas, true);
			spec.appendWhere(sc, new int[] { idx_m });
		    }
		}

		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    String[] aas = paramMap.getStringArray("authoringApplication");
		    if (aas != null && aas.length > 0) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}
			sc = new SearchCondition(cls_master, "authoringApplication", aas, true);
			spec.appendWhere(sc, new int[] { idx_m });
		    }
		}

		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    //작성부서.
		    String edmCreateDeptName = paramMap.getString("edmCreateDeptName");
		    String edmModifyDeptName = paramMap.getString("edmModifyDeptName");
		    if ((edmCreateDeptName.length() > 0) || (edmModifyDeptName.length() > 0)) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}
			spec.appendOpenParen();

			int idx_ud = spec.appendClassList(EDMUserData.class, false);

			SearchCondition udsc = new SearchCondition(new ClassAttribute(cls_itr, "iterationInfo.branchId"), "=", new ClassAttribute(EDMUserData.class, EDMUserData.OBJ_BRANCH_ID));
			udsc.setFromIndicies(new int[] { idx_cls, idx_ud }, 0);
			udsc.setOuterJoin(0);
			spec.appendWhere(udsc, new int[] { idx_cls, idx_ud });

			if (edmCreateDeptName.length() > 0) {
			    spec.appendAnd();

			    upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(EDMUserData.class, EDMUserData.CREATOR_DEPT_NAME));

			    sc = new SearchCondition(upper, SearchCondition.LIKE, ConstantExpression.newExpression(likeCharConvert(edmCreateDeptName).toUpperCase()));
			    spec.appendWhere(sc, new int[] { idx_ud });
			}

			if (edmModifyDeptName.length() > 0) {
			    spec.appendAnd();

			    upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(EDMUserData.class, EDMUserData.MODIFIER_DEPT_NAME));

			    sc = new SearchCondition(upper, SearchCondition.LIKE, ConstantExpression.newExpression(likeCharConvert(edmModifyDeptName).toUpperCase()));
			    spec.appendWhere(sc, new int[] { idx_ud });
			}

			spec.appendCloseParen();
		    }
		}

		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    String partNumber = paramMap.getString("partNumber");
		    if (partNumber.length() > 0) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}
			spec.appendOpenParen();

			int idx_epmlink = spec.appendClassList(EPMLink.class, false);
			int idx_pmst = spec.appendClassList(WTPartMaster.class, false);

			spec.appendWhere(new SearchCondition(cls_master, "thePersistInfo.theObjectIdentifier.id", EPMLink.class, "roleAObjectRef.key.id"), new int[] { idx_m, idx_epmlink });
			spec.appendAnd();

			//spec.appendWhere(new SearchCondition(EPMLink.class, "referenceType",
			//        SearchCondition.NOT_EQUAL, EDMHelper.REFERENCE_TYPE_MODEL), new int[]{idx_epmlink});
			//spec.appendAnd();

			spec.appendWhere(new SearchCondition(EPMLink.class, "roleBObjectRef.key.id", WTPartMaster.class, "thePersistInfo.theObjectIdentifier.id"), new int[] { idx_epmlink, idx_pmst });
			spec.appendAnd();

			KETQueryUtil.setQuerySpecForMultiSearch(spec, WTPartMaster.class, idx_pmst, "number", partNumber, true);

			spec.appendCloseParen();
		    }
		}

		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    String[] businessTypeAry = paramMap.getStringArray("businessType");
		    if (businessTypeAry.length > 0) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}
			spec.appendOpenParen();

			for (String businessType : businessTypeAry) {
			    NumberCode bizCode = (NumberCode) rf.getReference(businessType).getObject();
			    int idx_codelink = spec.appendClassList(EPMDocTypeCodeLink.class, false);

			    spec.appendWhere(new SearchCondition(cls_itr, "iterationInfo.branchId", EPMDocTypeCodeLink.class, "roleBObjectRef.key.branchId"), new int[] { idx_cls, idx_codelink });

			    spec.appendAnd();

			    spec.appendWhere(new SearchCondition(EPMDocTypeCodeLink.class, "roleAObjectRef.key.id", "=", bizCode.getPersistInfo().getObjectIdentifier().getId()),
				    new int[] { idx_codelink });
			}

			spec.appendCloseParen();
		    }
		}

		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    String projectNo = paramMap.getString("projectNo");
		    if (projectNo.length() > 0) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}

			Class cls_pjtlink = EPMDocProjectLink.class;
			Class cls_pjt = E3PSProjectMaster.class;

			int idx_pjtlink = spec.appendClassList(cls_pjtlink, false);
			int idx_pjt = spec.appendClassList(cls_pjt, false);

			spec.appendOpenParen();

			spec.appendWhere(new SearchCondition(cls_itr, "iterationInfo.branchId", cls_pjtlink, "roleBObjectRef.key.branchId"), new int[] { idx_cls, idx_pjtlink });

			spec.appendAnd();

			spec.appendWhere(new SearchCondition(cls_pjtlink, "roleAObjectRef.key.id", cls_pjt, "thePersistInfo.theObjectIdentifier.id"), new int[] { idx_pjtlink, idx_pjt });

			spec.appendAnd();

			// Modified by MJOH, 2011-01-26
			//                        spec.appendWhere(new SearchCondition(cls_pjt, E3PSProjectMaster.PJT_NO,
			//                                "like", likeCharConvert(projectNo)), new int[] { idx_pjt });
			KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_pjt, idx_pjt, E3PSProjectMaster.PJT_NO, projectNo, true);

			spec.appendCloseParen();
		    }
		}
	    }

	    if (Foldered.class.isAssignableFrom(cls_itr)) {//if(FolderEntry.class.isAssignableFrom(cls_itr)) {
		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    String folderOid = paramMap.getString("folderOid");
		    if (folderOid.length() > 0) {
			Folder folder = (Folder) (new ReferenceFactory()).getReference(folderOid).getObject();
			if (folder instanceof SubFolder) {
			    if (spec.getConditionCount() > 0) {
				spec.appendAnd();
			    }

			    Vector folders = new Vector();
			    folders.add(folder);
			    FolderUtil.getSubFolderList(folder, folders);

			    int f_idx = spec.appendClassList(IteratedFolderMemberLink.class, false);

			    spec.appendOpenParen();

			    for (int k = 0; k < folders.size(); k++) {
				Folder sf = (Folder) folders.get(k);
				if (k > 0) {
				    spec.appendOr();
				}
				spec.appendWhere(
				        new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, sf.getPersistInfo().getObjectIdentifier().getId()),
				        new int[] { f_idx });
			    }
			    spec.appendCloseParen();

			    spec.appendAnd();

			    sc = new SearchCondition(new ClassAttribute(IteratedFolderMemberLink.class, "roleBObjectRef.key.branchId"), "=", new ClassAttribute(cls_itr, "iterationInfo.branchId"));
			    sc.setFromIndicies(new int[] { f_idx, idx_cls }, 0);
			    sc.setOuterJoin(0);
			    spec.appendWhere(sc, new int[] { f_idx, idx_cls });

			}
			else if (folder instanceof Cabinet) {
			    if (spec.getConditionCount() > 0) {
				spec.appendAnd();
			    }

			    sc = new SearchCondition(cls_itr, "folderingInfo.cabinet.key.id", "=", folder.getPersistInfo().getObjectIdentifier().getId());
			    spec.appendWhere(sc, new int[] { idx_cls });
			}
		    }
		}
	    }

	    if (WTContained.class.isAssignableFrom(cls_itr)) {
		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    String containerOid = paramMap.getString("containerOid");
		    if (containerOid.length() > 0) {
			WTContainer container = (WTContainer) rf.getReference(containerOid).getObject();

			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}

			sc = new SearchCondition(cls_itr, "containerReference.key.id", "=", container.getPersistInfo().getObjectIdentifier().getId());
			spec.appendWhere(sc, new int[] { idx_cls });
		    }
		}
	    }

	    //상태 조건.
	    if (LifeCycleManaged.class.isAssignableFrom(cls_itr)) {
		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    String[] state = null;
		    state = paramMap.getStringArray("state");

		    if ((state != null) && (state.length > 0)) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}
			/*
			spec.appendWhere(
			        new SearchCondition(cls_itr, "state.state",state, false),
			        new int[]{idx_cls});
			*/
			KETQueryUtil.setQuerySpecForMultiSearch(spec, cls_itr, idx_cls, "state.state", state, true);
		    }
		}

		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		    String[] notState = null;
		    Object notstateobj = paramMap.get("notstate");
		    if (notstateobj != null) {
			if (notstateobj instanceof String[]) {
			    notState = (String[]) notstateobj;
			}
			else if (notstateobj instanceof String) {
			    if (((String) notstateobj).trim().length() > 0) {
				notState = new String[1];
				notState[0] = (String) notstateobj;
			    }
			}
		    }

		    if ((notState != null) && (notState.length > 0)) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}
			/*
			spec.appendWhere(
			        new SearchCondition(cls_itr, "state.state",notState, false, true),
			        new int[]{idx_cls});
			*/
			spec.appendOpenParen();
			for (int j = 0; j < notState.length; j++) {
			    if (j > 0) {
				spec.appendAnd();
			    }
			    spec.appendWhere(new SearchCondition(cls_itr, "state.state", SearchCondition.NOT_EQUAL, State.toState(notState[j])), new int[] { idx_cls });
			}
			spec.appendCloseParen();
		    }
		}
	    }

	    // 결과내 재검색 loop
	    for (int i = 0; i < mapList.size(); ++i) {
		KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
		String latest = paramMap.getString("latest");
		if (latest.length() == 0) {
		    latest = String.valueOf(true);
		}
		if (Boolean.parseBoolean(latest)) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    Boolean paramBoolean = new Boolean(ViewManageable.class.isAssignableFrom(cls_itr));
		    spec.appendWhere(new NegatedExpression(new ExistsExpression(VersionHelper.getSuccessorVersionQuerySpec(cls_itr, paramBoolean, spec, idx_cls))), null);
		}
	    }

	    if (IBAHolder.class.isAssignableFrom(cls_itr)) {
		// 결과내 재검색 loop
		for (int i = 0; i < mapList.size(); ++i) {
		    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));

		    KETParamMapUtil ibaMap = KETParamMapUtil.getMap();

		    AttributeDefDefaultView defDefaultView = null;

		    ArrayList attrs = EDMAttributes.getAttributes();

		    for (int j = 0; j < attrs.size(); j++) {
			defDefaultView = (AttributeDefDefaultView) attrs.get(j);
			String[] valueAry = paramMap.getStringArray(defDefaultView.getName());
			if (valueAry != null && valueAry.length > 0) {
			    Kogger.debug(EDMPBOHelper.class, "getName() == " + defDefaultView.getName());
			    ibaMap.put(defDefaultView.getName(), valueAry);
			}
		    }

		    // IBA 관련 속성 검색 추가..
		    if (ibaMap.size() > 0) {

			Set set = ibaMap.keySet();
			Iterator iter = set.iterator();

			String key = "";
			String[] valueAry;

			while (iter.hasNext()) {
			    key = (String) iter.next();
			    valueAry = ibaMap.getStringArray(key);

			    //Kogger.debug(EDMPBOHelper.class, "key == " + key);
			    //Kogger.debug(getClass(), "value == " + value);

			    for (String value : valueAry) {
				SearchUtil.addIBAQuery(spec, cls_itr, idx_cls, key, value);
			    }
			}
		    }
		}
	    }

	    // 결과내 재검색 loop
	    for (int i = 0; i < mapList.size(); ++i) {
		KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));

		Vector sorts = (Vector) paramMap.get("sort");
		if ((sorts != null) && (sorts.size() > 0)) {
		    ClassAttribute sortCa = null;
		    OrderBy orderby = null;
		    int sortIdx = 0;

		    for (int j = 0; j < sorts.size(); j++) {
			Object so[] = (Object[]) sorts.get(j);
			String keycolumn = (String) so[0];
			Class sortcls = (Class) so[1];
			Boolean descending = (Boolean) so[2];

			if (sortcls.equals(People.class)) {
			    Class cls_people = People.class;

			    int idx_people = spec.appendClassList(cls_people, false);

			    if (spec.getConditionCount() > 0) {
				spec.appendAnd();
			    }

			    spec.appendOpenParen();
			    spec.appendWhere(new SearchCondition(cls_itr, "iterationInfo.creator.key.id", cls_people, "userReference.key.id"), new int[] { idx_cls, idx_people });
			    spec.appendCloseParen();

			    sortCa = new ClassAttribute(cls_people, keycolumn);
			    sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			    spec.appendSelect(sortCa, new int[] { idx_people }, true);

			    orderby = new OrderBy(sortCa, descending.booleanValue(), null);
			    spec.appendOrderBy(orderby, new int[] { idx_people });
			    continue;
			}

			if (sortcls.equals(StringDefinition.class)) {
			    Class cls_ibavalue = StringValue.class;
			    Class cls_ibadef = StringDefinition.class;

			    int idx_ibavalue = spec.appendClassList(cls_ibavalue, false);
			    int idx_ibadef = spec.appendClassList(cls_ibadef, false);

			    if (spec.getConditionCount() > 0) {
				spec.appendAnd();
			    }

			    spec.appendOpenParen();
			    spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.theObjectIdentifier.id", cls_ibavalue, "theIBAHolderReference.key.id"), new int[] { idx_cls, idx_ibavalue });

			    spec.appendAnd();

			    spec.appendWhere(new SearchCondition(cls_ibavalue, "definitionReference.key.id", cls_ibadef, "thePersistInfo.theObjectIdentifier.id"),
				    new int[] { idx_ibavalue, idx_ibadef });

			    spec.appendAnd();

			    spec.appendWhere(new SearchCondition(cls_ibadef, "name", "=", keycolumn), new int[] { idx_ibadef });

			    spec.appendCloseParen();

			    sortCa = new ClassAttribute(cls_ibavalue, "value");
			    sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			    spec.appendSelect(sortCa, new int[] { idx_ibavalue }, true);

			    orderby = new OrderBy(sortCa, descending.booleanValue(), null);
			    spec.appendOrderBy(orderby, new int[] { idx_ibavalue });
			    continue;
			}

			for (int k = 0; k < spec.getFromClause().getCount(); k++) {
			    Class lcls = spec.getFromClause().getTableExpressionAt(k).getTableClass();
			    if ((lcls != null) && lcls.equals(sortcls)) {
				sortCa = new ClassAttribute(sortcls, keycolumn);
				//sortCa = new ClassAttribute(cls_itr, "thePersistInfo.modifyStamp");
				sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
				spec.appendSelect(sortCa, new int[] { idx_m }, true);

				orderby = new OrderBy(sortCa, descending.booleanValue(), null);
				spec.appendOrderBy(orderby, new int[] { k });

				continue;
			    }
			}
		    }
		}
	    }

	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}

	Kogger.debug(EDMPBOHelper.class, "EDMQueryHelper.getQuerySpecSIR: 쿼리 == \n" + spec);
	return spec;
    }

    public static void setIBACondition(QuerySpec spec, Class target, int index, HashMap map) throws WTException {
	try {
	    if (map == null || map.size() == 0) {
		return;
	    }
	    if (!spec.isAdvancedQuery()) {
		spec.setAdvancedQueryEnabled(true);
	    }

	    if (spec.getConditionCount() > 0) spec.appendAnd();


	    QuerySpec ibaSpec = ibaSpec(map);
	    SubSelectExpression subfrom = new SubSelectExpression(ibaSpec);
	    subfrom.setFromAlias(new String[] { "IBA0" }, 0);

	    int iba_idx = spec.appendFrom(subfrom);
	    SearchCondition sc = null;

	    sc = new SearchCondition(new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id"), "=", new KeywordExpression(spec.getFromClause().getAliasAt(iba_idx) + "." + ibaIdAlias));
	    sc.setFromIndicies(new int[] { index, iba_idx }, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[] { index, iba_idx });
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}
    }

    public static QuerySpec ibaSpec(HashMap map) throws WTException {
	QuerySpec spec = null;
	try {

	    /*
	         select A1.idA3A4
	        FROM StringValue A1, StringDefinition A2
	        where (A1.hierarchyIDA6 = A2.hierarchyID)
	              AND (
	                                     ((upper(A1.value2) = 'TRUE') AND (A2.name = 'LatestVersionFlag'))
	                                or (( upper(A1.value2) like '46X%') AND (A2.name = 'SPECIFICATION'))
	                                or (( upper(A1.value2) like 'EA%') AND (A2.name = 'Unit'))
	                  )
	        group by A1.idA3A4
	        having  count(A1.idA3A4) = 3
	    */
	    Class valueClass = StringValue.class;
	    Class defClass = StringDefinition.class;
	    spec = new QuerySpec();
	    int value_idx = spec.appendClassList(valueClass, false);
	    int def_idx = spec.appendClassList(defClass, false);

	    SearchCondition sc = null;
	    ClassAttribute ca = null;
	    ClassAttribute ca1 = null;
	    SQLFunction sqlfunction = null;
	    ColumnExpression ce = null;

	    ca = new ClassAttribute(valueClass, "theIBAHolderReference.key.id");
	    ca.setColumnAlias(ibaIdAlias);
	    spec.appendSelect(ca, false);

	    ca = new ClassAttribute(valueClass, "definitionReference.key.id");
	    ca1 = new ClassAttribute(defClass, "thePersistInfo.theObjectIdentifier.id");
	    sc = new SearchCondition(ca, "=", ca1);
	    sc.setFromIndicies(new int[] { value_idx, def_idx }, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[] { value_idx, def_idx });


	    spec.appendAnd();

	    spec.appendOpenParen();

	    Set set = map.keySet();
	    Iterator iter = set.iterator();
	    String key = "";
	    String value = "";
	    int ibacount = 0;
	    while (iter.hasNext()) {
		key = (String) iter.next();
		value = (String) map.get(key);

		Kogger.debug(EDMPBOHelper.class, "key == " + key);
		Kogger.debug(EDMPBOHelper.class, "value == " + value);
		if (ibacount > 0) spec.appendOr();

		spec.appendOpenParen();

		ce = ConstantExpression.newExpression(value.trim().toUpperCase());
		spec.appendWhere(new SearchCondition(new ClassAttribute(valueClass, "value"), SearchCondition.EQUAL, ce), new int[] { value_idx });

		//ca = new ClassAttribute(valueClass, "value2");
		//sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
		//ce = ConstantExpression.newExpression(value.toUpperCase()+"%");
		//sc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
		//spec.appendWhere(sc, new int[] {value_idx });

		spec.appendAnd();
		spec.appendWhere(new SearchCondition(defClass, "name", "=", key), new int[] { def_idx });

		spec.appendCloseParen();
		ibacount++;
	    }

	    spec.appendCloseParen();

	    ca = new ClassAttribute(valueClass, "theIBAHolderReference.key.id");
	    spec.appendGroupBy(ca, new int[] { value_idx }, false);

	    sqlfunction = SQLFunction.newSQLFunction(SQLFunction.COUNT, new ClassAttribute(valueClass, "theIBAHolderReference.key.id"));
	    ce = ConstantExpression.newExpression(new Integer(map.size()));
	    spec.appendHaving(new SearchCondition(sqlfunction, SearchCondition.EQUAL, ce), new int[] { value_idx });
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}
	return spec;
    }

    public static String likeCharConvert(String str) {

	return str.replace('*', '%');
	/*
	if(str.startsWith("*")) {
	    str = "%" + str.substring(1);
	}

	if(str.endsWith("*")) {
	    str = str.substring(0, str.lastIndexOf("*"))+"%";
	}

	return str;
	*/
    }


    public static QueryResult getReferenceSetByDie(String dieNo, String state) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { String.class, String.class };
	    Object args[] = new Object[] { dieNo, state };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getReferenceSetByDie", EDMQueryHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPBOHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}

	try {
	    QuerySpec spec = new QuerySpec();

	    spec.setAdvancedQueryEnabled(true);

	    Class cls_mst = EPMDocumentMaster.class;
	    Class cls_itr = EPMDocument.class;
	    Class cls_ibavalue = StringValue.class;
	    Class cls_ibadef = StringDefinition.class;

	    int idx_mst = spec.appendClassList(cls_mst, false);
	    int idx_itr = spec.appendClassList(cls_itr, true);
	    int idx_ibavalue = spec.appendClassList(cls_ibavalue, false);
	    int idx_ibadef = spec.appendClassList(cls_ibadef, false);

	    spec.appendWhere(new SearchCondition(cls_mst, "number", SearchCondition.LIKE, dieNo + "-%"), new int[] { idx_mst });

	    spec.appendAnd();

	    spec.appendWhere(new SearchCondition(cls_mst, "thePersistInfo.theObjectIdentifier.id", cls_itr, "masterReference.key.id"), new int[] { idx_mst, idx_itr });

	    spec.appendAnd();

	    spec.appendWhere(wt.vc.VersionControlHelper.getSearchCondition(cls_itr, true), new int[] { idx_itr });

	    if ((state != null) && (state.trim().length() > 0)) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(cls_itr, "state.state", "=", state), new int[] { idx_itr });
	    }

	    VersionHelper.appendFilterTerminalsAndWorkingCopies(cls_itr, spec, new int[] { idx_itr });

	    spec.appendAnd();

	    spec.appendOpenParen();

	    spec.appendWhere(new SearchCondition(cls_itr, "thePersistInfo.theObjectIdentifier.id", cls_ibavalue, "theIBAHolderReference.key.id"), new int[] { idx_itr, idx_ibavalue });

	    spec.appendAnd();

	    spec.appendOpenParen();
	    CADCategory injCat = CADCategory.toCADCategory("INJECTION_MOLD_SET_DRAWING");
	    CADCategory moldCat = CADCategory.toCADCategory("PRESS_MOLD_SET_DRAWING");
	    spec.appendWhere(new SearchCondition(cls_ibavalue, "value2", "=", injCat.getDisplay(Locale.KOREAN)), new int[] { idx_ibavalue });
	    spec.appendOr();
	    spec.appendWhere(new SearchCondition(cls_ibavalue, "value2", "=", moldCat.getDisplay(Locale.KOREAN)), new int[] { idx_ibavalue });
	    spec.appendCloseParen();

	    spec.appendAnd();

	    spec.appendWhere(new SearchCondition(cls_ibavalue, "definitionReference.key.id", cls_ibadef, "thePersistInfo.theObjectIdentifier.id"), new int[] { idx_ibavalue, idx_ibadef });

	    spec.appendAnd();

	    spec.appendWhere(new SearchCondition(cls_ibadef, "name", "=", "CADCategory"), new int[] { idx_ibadef });

	    spec.appendCloseParen();

	    spec.appendAnd();

	    Boolean paramBoolean = new Boolean(ViewManageable.class.isAssignableFrom(cls_itr));
	    spec.appendWhere(new NegatedExpression(new ExistsExpression(VersionHelper.getSuccessorVersionQuerySpec(cls_itr, paramBoolean, spec, idx_itr))), null);

	    return serverQuery(spec);
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}
	return null;
    }

    public static QuerySpec getQuerySpec2(HashMap map) throws Exception {
	QuerySpec spec = null;
	try {
	    //String className = StringUtil.trimToEmpty((String)map.get("className"));
	    String number = StringUtil.trimToEmpty((String) map.get("number"));
	    String name = StringUtil.trimToEmpty((String) map.get("name"));
	    String latest = StringUtil.trimToEmpty((String) map.get("latest"));

	    ReferenceFactory rf = new ReferenceFactory();

	    Class cls_itr = EPMDocument.class;
	    Class cls_master = EPMDocumentMaster.class;

	    spec = new QuerySpec();

	    spec.setAdvancedQueryEnabled(true);

	    int idx_m = spec.appendClassList(cls_master, false);
	    int idx_cls = spec.appendClassList(cls_itr, true);

	    SQLFunction upper = null;
	    SearchCondition sc = null;

	    if (number.length() > 0) {
		upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(cls_master, "number"));
		sc = new SearchCondition(upper, SearchCondition.LIKE, ConstantExpression.newExpression(likeCharConvert(number).toUpperCase()));
		spec.appendWhere(sc, new int[] { idx_m });
	    }


	    if (name.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(cls_master, "name"));
		sc = new SearchCondition(upper, SearchCondition.LIKE, ConstantExpression.newExpression(likeCharConvert(name).toUpperCase()));
		spec.appendWhere(sc, new int[] { idx_m });
	    }

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(cls_master, "thePersistInfo.theObjectIdentifier.id", cls_itr, "masterReference.key.id"), new int[] { idx_m, idx_cls });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }

	    spec.appendWhere(wt.vc.VersionControlHelper.getSearchCondition(cls_itr, true), new int[] { idx_cls });

	    VersionHelper.appendFilterTerminalsAndWorkingCopies(cls_itr, spec, new int[] { idx_cls });

	    String[] oas = (String[]) map.get("ownerApplication");
	    String[] aas = (String[]) map.get("authoringApplication");


	    if (oas != null) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		sc = new SearchCondition(cls_master, "ownerApplication", oas, true);
		spec.appendWhere(sc, new int[] { idx_m });
	    }

	    if (aas != null) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		sc = new SearchCondition(cls_master, "authoringApplication", aas, true);
		spec.appendWhere(sc, new int[] { idx_m });
	    }

	    if (Foldered.class.isAssignableFrom(cls_itr)) {
		String folderOid = StringUtil.trimToEmpty((String) map.get("folderOid"));
		if (folderOid.length() > 0) {
		    Folder folder = (Folder) (new ReferenceFactory()).getReference(folderOid).getObject();
		    if (folder instanceof SubFolder) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}

			Vector folders = new Vector();
			folders.add(folder);
			FolderUtil.getSubFolderList(folder, folders);

			int f_idx = spec.appendClassList(IteratedFolderMemberLink.class, false);

			spec.appendOpenParen();

			for (int k = 0; k < folders.size(); k++) {
			    Folder sf = (Folder) folders.get(k);
			    if (k > 0) {
				spec.appendOr();
			    }
			    spec.appendWhere(new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, sf.getPersistInfo().getObjectIdentifier().getId()),
				    new int[] { f_idx });
			}
			spec.appendCloseParen();

			spec.appendAnd();

			sc = new SearchCondition(new ClassAttribute(IteratedFolderMemberLink.class, "roleBObjectRef.key.branchId"), "=", new ClassAttribute(cls_itr, "iterationInfo.branchId"));
			sc.setFromIndicies(new int[] { f_idx, idx_cls }, 0);
			sc.setOuterJoin(0);
			spec.appendWhere(sc, new int[] { f_idx, idx_cls });

		    }
		    else if (folder instanceof Cabinet) {
			if (spec.getConditionCount() > 0) {
			    spec.appendAnd();
			}

			sc = new SearchCondition(cls_itr, "folderingInfo.cabinet.key.id", "=", folder.getPersistInfo().getObjectIdentifier().getId());
			spec.appendWhere(sc, new int[] { idx_cls });
		    }
		}
	    }

	    if (WTContained.class.isAssignableFrom(cls_itr)) {
		String containerOid = StringUtil.trimToEmpty((String) map.get("containerOid"));

		if (containerOid.length() > 0) {
		    WTContainer container = (WTContainer) rf.getReference(containerOid).getObject();

		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    sc = new SearchCondition(cls_itr, "containerReference.key.id", "=", container.getPersistInfo().getObjectIdentifier().getId());
		    spec.appendWhere(sc, new int[] { idx_cls });
		}
	    }

	    //상태 조건.
	    if (LifeCycleManaged.class.isAssignableFrom(cls_itr)) {

		String[] state = null;
		Object stateobj = map.get("state");
		if (stateobj != null) {
		    if (stateobj instanceof String[]) {
			state = (String[]) stateobj;
		    }
		    else if (stateobj instanceof String) {
			if (((String) stateobj).trim().length() > 0) {
			    state = new String[1];
			    state[0] = (String) stateobj;
			}
		    }
		}

		if ((state != null) && (state.length > 0)) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendOpenParen();
		    for (int i = 0; i < state.length; i++) {
			if (i > 0) {
			    spec.appendOr();
			}
			spec.appendWhere(new SearchCondition(cls_itr, "state.state", SearchCondition.EQUAL, State.toState(state[i])), new int[] { idx_cls });
		    }
		    spec.appendCloseParen();
		}

		String[] notState = null;
		Object notstateobj = map.get("notstate");
		if (notstateobj != null) {
		    if (notstateobj instanceof String[]) {
			notState = (String[]) notstateobj;
		    }
		    else if (notstateobj instanceof String) {
			if (((String) notstateobj).trim().length() > 0) {
			    notState = new String[1];
			    notState[0] = (String) notstateobj;
			}
		    }
		}

		if ((notState != null) && (notState.length > 0)) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }
		    spec.appendOpenParen();
		    for (int i = 0; i < notState.length; i++) {
			if (i > 0) {
			    spec.appendAnd();
			}
			spec.appendWhere(new SearchCondition(cls_itr, "state.state", SearchCondition.NOT_EQUAL, State.toState(notState[i])), new int[] { idx_cls });
		    }
		    spec.appendCloseParen();
		}
	    }

	    if (Boolean.parseBoolean(latest)) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		Boolean paramBoolean = new Boolean(ViewManageable.class.isAssignableFrom(cls_itr));
		spec.appendWhere(new NegatedExpression(new ExistsExpression(VersionHelper.getSuccessorVersionQuerySpec(cls_itr, paramBoolean, spec, idx_cls))), null);
	    }

	    spec.appendOrderBy(new OrderBy(new ClassAttribute(cls_itr, "master>number"), true), new int[] { idx_cls });
	} catch (Exception e) {
	    Kogger.error(EDMPBOHelper.class, e);
	}

	Kogger.debug(EDMPBOHelper.class, "쿼리2 == " + spec);
	return spec;
    }

}
