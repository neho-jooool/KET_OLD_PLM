package e3ps.project.beans;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.project.CostInfo;
import e3ps.project.MoldItemInfo;
import e3ps.project.ProductProject;
import ext.ket.shared.log.Kogger;

public class ProductProjectHelper implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    public static final ProductProjectHelper manager = new ProductProjectHelper();

    protected ProductProjectHelper() {
    }

    public static boolean CostInfoSave(Hashtable hash) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Hashtable.class };
	    Object args[] = new Object[] { hash };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("CostInfoSave", "e3ps.project.beans.ProductProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProductProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProductProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String oid = (String) hash.get("oid");
	    ProductProject project = (ProductProject) CommonUtil.getObject(oid);

	    QuerySpec specCost = new QuerySpec();
	    int idx_Cost = specCost.addClassList(CostInfo.class, true);
	    SearchCondition scCost = new SearchCondition(CostInfo.class, "projectReference.key.id", "=",
		    CommonUtil.getOIDLongValue(project));
	    specCost.appendWhere(scCost, new int[] { idx_Cost });
	    QueryResult rtCost = PersistenceHelper.manager.find(specCost);

	    while (rtCost.hasMoreElements()) {
		Object obj[] = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];
		PersistenceHelper.manager.delete(costInfo);
	    }

	    Vector costType = (Vector) hash.get("costType");
	    Vector partNoCost = (Vector) hash.get("partNoCost");
	    Vector dieNoCost = (Vector) hash.get("dieNoCost");
	    Vector moldTypeCost = (Vector) hash.get("moldTypeCost");
	    Vector costName = (Vector) hash.get("costName");
	    Vector order = (Vector) hash.get("order");
	    Vector targetCost = (Vector) hash.get("targetCost");
	    Vector decideCost = (Vector) hash.get("decideCost");
	    Vector executionCost = (Vector) hash.get("executionCost");
	    Vector editCost = (Vector) hash.get("editCost");
	    if (costType != null && costType.size() > 0) {
		for (int i = 0; i < costType.size(); i++) {
		    CostInfo costInfo = CostInfo.newCostInfo();
		    costInfo.setCostType((String) costType.get(i));
		    costInfo.setPartNo((String) partNoCost.get(i));
		    costInfo.setDieNo((String) dieNoCost.get(i));
		    NumberCode code = NumberCodeHelper.manager.getNumberCode("MOLDTYPE", (String) moldTypeCost.get(i));
		    if (code != null)
			costInfo.setMoldType(code);
		    costInfo.setCostName((String) costName.get(i));
		    costInfo.setOrderInvest((String) order.get(i));
		    costInfo.setTargetCost((String) targetCost.get(i));
		    costInfo.setDecideCost((String) decideCost.get(i));
		    costInfo.setExecutionCost((String) executionCost.get(i));
		    costInfo.setEditCost((String) editCost.get(i));
		    costInfo.setProject(project);
		    costInfo = (CostInfo) PersistenceHelper.manager.save(costInfo);

		    if ((String) costType.get(i) != null) {
			String costTypeValue = (String) costType.get(i);
			if ("금형".equals(costTypeValue)) {
			    QuerySpec specItem = new QuerySpec();
			    int idx_Item = specItem.addClassList(MoldItemInfo.class, true);
			    SearchCondition scItem = new SearchCondition(MoldItemInfo.class, "projectReference.key.id", "=",
				    CommonUtil.getOIDLongValue(project));
			    specItem.appendWhere(scItem, new int[] { idx_Item });

			    if ((String) dieNoCost.get(i) != null) {
				specItem.appendAnd();
				scItem = new SearchCondition(MoldItemInfo.class, "dieNo", "=", (String) dieNoCost.get(i));
				specItem.appendWhere(scItem, new int[] { idx_Item });
			    }

			    QueryResult rtItem = PersistenceHelper.manager.find(specItem);
			    while (rtItem.hasMoreElements()) {
				Object obj[] = (Object[]) rtItem.nextElement();
				MoldItemInfo moldItemInfo = (MoldItemInfo) obj[0];
				moldItemInfo.setCostInfo(costInfo);
				moldItemInfo = (MoldItemInfo) PersistenceHelper.manager.save(moldItemInfo);
			    }

			}
		    }
		}

	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProductProjectHelper.class, e);

	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return true;
    }

    public QueryResult getMoldItemInfo(ProductProject project) throws Exception {
	QuerySpec specMoldItem = new QuerySpec();
	int idx1 = specMoldItem.addClassList(MoldItemInfo.class, true);
	int idx2 = specMoldItem.addClassList(ProductProject.class, false);

	specMoldItem.appendWhere(new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { idx2 });

	if (specMoldItem.getConditionCount() > 0) {
	    specMoldItem.appendAnd();
	}

	specMoldItem.appendWhere(new SearchCondition(ProductProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
	        new int[] { idx2 });

	if (specMoldItem.getConditionCount() > 0) {
	    specMoldItem.appendAnd();
	}

	ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
	ClassAttribute ca1 = new ClassAttribute(ProductProject.class, "thePersistInfo.theObjectIdentifier.id");

	SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	sc.setFromIndicies(new int[] { idx1, idx2 }, 0);
	sc.setOuterJoin(0);
	specMoldItem.appendWhere(sc, new int[] { idx1, idx2 });

	if (specMoldItem.getConditionCount() > 0) {
	    specMoldItem.appendAnd();
	}

	SearchCondition scc = new SearchCondition(ProductProject.class, ProductProject.MASTER_REFERENCE + ".key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(project.getMaster()));
	specMoldItem.appendWhere(scc, new int[] { idx2 });
	// SearchUtil.setOrderBy(specMoldItem, MoldItemInfo.class, 0, MoldItemInfo.DIE_NO, false);
	SearchUtil.setOrderBy(specMoldItem, MoldItemInfo.class, 0, MoldItemInfo.DIE_NO, false);

	return PersistenceHelper.manager.find(specMoldItem);
    }

    public QueryResult getMoldItemInfo(ProductProject project, String partNo) throws Exception {
	QuerySpec specMoldItem = new QuerySpec();
	int idx1 = specMoldItem.addClassList(MoldItemInfo.class, true);
	int idx2 = specMoldItem.addClassList(ProductProject.class, false);

	specMoldItem.appendWhere(new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { idx2 });

	if (specMoldItem.getConditionCount() > 0) {
	    specMoldItem.appendAnd();
	}

	specMoldItem.appendWhere(new SearchCondition(ProductProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
	        new int[] { idx2 });

	if (specMoldItem.getConditionCount() > 0) {
	    specMoldItem.appendAnd();
	}

	specMoldItem.appendWhere(new SearchCondition(MoldItemInfo.class, "partNo", SearchCondition.EQUAL, partNo), new int[] { idx1 });

	if (specMoldItem.getConditionCount() > 0) {
	    specMoldItem.appendAnd();
	}

	ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
	ClassAttribute ca1 = new ClassAttribute(ProductProject.class, "thePersistInfo.theObjectIdentifier.id");

	SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	sc.setFromIndicies(new int[] { idx1, idx2 }, 0);
	sc.setOuterJoin(0);
	specMoldItem.appendWhere(sc, new int[] { idx1, idx2 });

	if (specMoldItem.getConditionCount() > 0) {
	    specMoldItem.appendAnd();
	}

	SearchCondition scc = new SearchCondition(ProductProject.class, ProductProject.MASTER_REFERENCE + ".key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(project.getMaster()));
	specMoldItem.appendWhere(scc, new int[] { idx2 });
	// SearchUtil.setOrderBy(specMoldItem, MoldItemInfo.class, 0, MoldItemInfo.DIE_NO, false);
	SearchUtil.setOrderBy(specMoldItem, MoldItemInfo.class, 0, MoldItemInfo.DIE_NO, false);

	return PersistenceHelper.manager.find(specMoldItem);
    }

    public QueryResult getCostInfo(ProductProject project) throws Exception {
	QuerySpec specCostInfo = new QuerySpec();
	int idx1 = specCostInfo.addClassList(CostInfo.class, true);
	int idx2 = specCostInfo.addClassList(ProductProject.class, false);

	specCostInfo.appendWhere(new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { idx2 });

	if (specCostInfo.getConditionCount() > 0) {
	    specCostInfo.appendAnd();
	}

	specCostInfo.appendWhere(new SearchCondition(ProductProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
	        new int[] { idx2 });

	if (specCostInfo.getConditionCount() > 0) {
	    specCostInfo.appendAnd();
	}

	ClassAttribute ca0 = new ClassAttribute(CostInfo.class, CostInfo.PROJECT_REFERENCE + ".key.id");
	ClassAttribute ca1 = new ClassAttribute(ProductProject.class, "thePersistInfo.theObjectIdentifier.id");

	SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	sc.setFromIndicies(new int[] { idx1, idx2 }, 0);
	sc.setOuterJoin(0);
	specCostInfo.appendWhere(sc, new int[] { idx1, idx2 });

	if (specCostInfo.getConditionCount() > 0) {
	    specCostInfo.appendAnd();
	}

	SearchCondition scc = new SearchCondition(ProductProject.class, ProductProject.MASTER_REFERENCE + ".key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(project.getMaster()));
	specCostInfo.appendWhere(scc, new int[] { idx2 });

	// SearchUtil.setOrderBy(specCostInfo, CostInfo.class, 0, "thePersistInfo.createStamp", false);

	SearchUtil.setOrderBy(specCostInfo, CostInfo.class, 0, CostInfo.COST_TYPE, false);

	// SearchUtil.setOrderBy(specCostInfo, CostInfo.class, 0, "dieNo", false);

	return PersistenceHelper.manager.find(specCostInfo);
    }

    public QueryResult getCostInfo(String dieNo) throws Exception {

	QuerySpec specCostInfo = new QuerySpec();
	int idx1 = specCostInfo.addClassList(CostInfo.class, true);

	specCostInfo.appendWhere(new SearchCondition(CostInfo.class, CostInfo.DIE_NO, SearchCondition.EQUAL, dieNo), new int[] { idx1 });

	if (specCostInfo.getConditionCount() > 0) {
	    specCostInfo.appendAnd();
	}

	specCostInfo.appendWhere(new SearchCondition(CostInfo.class, CostInfo.COST_TYPE, SearchCondition.LIKE, "%금형%"), new int[] { idx1 });

	if (specCostInfo.getConditionCount() > 0) {
	    specCostInfo.appendAnd();
	}

	specCostInfo.appendWhere(new SearchCondition(CostInfo.class, CostInfo.ORDER_INVEST, SearchCondition.NOT_NULL, true),
	        new int[] { idx1 });

	// SearchUtil.setOrderBy(specCostInfo, CostInfo.class, 0, "thePersistInfo.createStamp", false);

	SearchUtil.setOrderBy(specCostInfo, CostInfo.class, 0, CostInfo.COST_TYPE, false);

	// SearchUtil.setOrderBy(specCostInfo, CostInfo.class, 0, "dieNo", false);

	return PersistenceHelper.manager.find(specCostInfo);
    }

}
