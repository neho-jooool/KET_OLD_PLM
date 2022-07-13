package e3ps.wfm;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipalReference;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import e3ps.wfm.entity.KETWfmMultiReqDocLink;
import e3ps.wfm.util.WFMProperties;
import ext.ket.shared.log.Kogger;

public class WFMHelper implements RemoteAccess {

    public static final WFMHelper manager = new WFMHelper();

    static final boolean	  SERVER  = wt.method.RemoteMethodServer.ServerFlag;

    public static QueryResult getRefWorklistQuery(String predate, String postdate, String sorting) throws WTException {
	if (!SERVER) {
	    Class[] argTypes = new Class[] { String.class, String.class, String.class };
	    Object[] args = new Object[] { predate, postdate, sorting };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("getRefWorklistQuery", "e3ps.wfm.WFMHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(WFMHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(WFMHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}
	// QueryResult result = new QueryResult();
	WTPrincipalReference prinRef = SessionHelper.manager.getPrincipalReference();

	QuerySpec subQuery = new QuerySpec();
	Class subClass = KETWfmApprovalHistory.class;
	String actType = WFMProperties.getInstance().getString("wfm.reference");
	int historyIdx = subQuery.appendClassList(subClass, false);

	subQuery.appendSelect(new ClassAttribute(subClass, "appMasterReference.key.id"), new int[] { historyIdx }, false);
	subQuery.appendWhere(new SearchCondition(subClass, "owner.key.id", SearchCondition.EQUAL, Long.parseLong(KETObjectUtil.getIda2a2(prinRef.getObject()))), new int[] { historyIdx });

	if (subQuery.getConditionCount() > 0)
	    subQuery.appendAnd();
	subQuery.appendWhere(new SearchCondition(subClass, "activityName", SearchCondition.EQUAL, actType), new int[] { historyIdx });

	QuerySpec spec = new QuerySpec();
	Class targetClass = KETWfmApprovalMaster.class;
	int masterIdx = spec.appendClassList(targetClass, true);
	ClassAttribute targetExpression = new ClassAttribute(targetClass, "thePersistInfo.theObjectIdentifier.id");

	spec.appendWhere(new SearchCondition(targetClass, "completedDate", SearchCondition.NOT_NULL, true), new int[] { masterIdx });

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(targetClass, "pboReference.key.classname", SearchCondition.NOT_NULL, true), new int[] { masterIdx });

	if (!predate.equals("")) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(targetClass, "completedDate", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate2(predate)), new int[] { masterIdx });
	}

	if (!postdate.equals("")) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(targetClass, "completedDate", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil.convertEndDate2(postdate)), new int[] { masterIdx });
	}

	String filterClass = StringUtil.checkNull(sorting);
	if (filterClass.length() > 0) {
	    if (filterClass != null && filterClass.trim().length() > 0 && !filterClass.equalsIgnoreCase("null")) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendOpenParen();
		StringTokenizer filterClassToken = new StringTokenizer(filterClass, ",");
		while (filterClassToken.hasMoreTokens()) {
		    spec.appendWhere(new SearchCondition(targetClass, "businessobjectRef.key.classname", SearchCondition.LIKE, "%" + filterClassToken.nextToken() + "%"), new int[] { masterIdx });
		    if (filterClassToken.hasMoreTokens())
			spec.appendOr();
		}
		spec.appendCloseParen();
	    }
	}

	// if(sorting == null || sorting.length() == 0)
	// {
	// sorting = "true";
	// }
	//
	// boolean sort = Boolean.parseBoolean(sorting);

	ClassAttribute ca = new ClassAttribute(KETWfmApprovalMaster.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
	OrderBy orderBy = new OrderBy(ca, false);
	spec.appendOrderBy(orderBy, new int[] { historyIdx });

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(targetExpression, SearchCondition.IN, new SubSelectExpression(subQuery)), new int[] { masterIdx });

	spec.setAdvancedQueryEnabled(true);

	// //Kogger.debug(getClass(), "spec == " + spec);

	QueryResult result = PersistenceServerHelper.manager.query(spec);

	return result;
    }

    public static void createDocLink(String docOid, KETWfmMultiApprovalRequest multiReq) throws WTException {
	if (!SERVER) {
	    Class[] argTypes = new Class[] { String.class, KETWfmMultiApprovalRequest.class };
	    Object[] args = new Object[] { docOid, multiReq };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("createDocLink", "e3ps.wfm.WFMHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(WFMHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(WFMHelper.class, e);
		throw new WTException(e);
	    }
	}

	Transaction trs = new Transaction();
	try {
	    trs.start();
	    KETWfmMultiReqDocLink multiDoc = new KETWfmMultiReqDocLink();

	    KETProjectDocument document = (KETProjectDocument) CommonUtil.getObject(docOid);
	    WTDocument wtDocument = (WTDocument) document;
	    /*
	     * if( !wtDocument.getLifeCycleTemplate().getName().equals("KET_EPM_LC") )
	     * {
	     * LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_EPM_LC", WCUtil.getPDMLinkProduct().getContainerReference());
	     * if( LCtemplate != null )
	     * {
	     * wtDocument = (WTDocument)LifeCycleHelper.service.reassign((LifeCycleManaged)wtDocument, LCtemplate.getLifeCycleTemplateReference());
	     * }
	     * }
	     */
	    // Kogger.debug(getClass(), "");
	    multiDoc.setDoc(wtDocument);
	    multiDoc.setRequest(multiReq);
	    PersistenceHelper.manager.save(multiDoc);

	    trs.commit();
	    trs = null;
	} catch (Exception e) {
	    Kogger.error(WFMHelper.class, e);
	} finally {
	    if (trs != null)
		trs.rollback();
	}
    }
}
