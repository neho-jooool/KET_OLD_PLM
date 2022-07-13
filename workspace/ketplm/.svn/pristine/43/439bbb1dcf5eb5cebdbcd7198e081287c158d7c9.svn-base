package e3ps.project.moldPart.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospector;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.pds.DatabaseInfoUtilities;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import e3ps.bom.entity.KETPartUsageLink;
import e3ps.common.db.DBCPManager;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldECAEpmDocLink;
import e3ps.ecm.entity.KETMoldStdPartLine;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.beans.ProjectTaskHelper;
import e3ps.project.historyprocess.HistoryHelper;
import e3ps.project.moldPart.MoldPartManager;
import e3ps.project.moldPart.MoldSubPart;
import ext.ket.shared.log.Kogger;

public class MoldPartHelper implements RemoteAccess {


    public static MoldPartManager create(HashMap map) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {

	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = RemoteMethodServer.getDefault().invoke("create", MoldPartHelper.class.getName(), null, argTypes, args);
	    return (MoldPartManager) obj;
	}

	Transaction transaction = new Transaction();
	MoldPartManager manager = null;
	try {
	    transaction.start();
	    String dieNo = (String) map.get("dieNumber");
	    String projectOid = (String) map.get("projectOid");
	    String ecoNo = (String) map.get("ecoNumber");
	    //String levelType = (String)map.get("levType");
	    //String createType = (String)map.get("createType");
	    String partProcess = (String) map.get("partProcess");
	    String planDate = (String) map.get("completeDate");
	    String moldDate = (String) map.get("exhibitDate");

	    manager = MoldPartManager.newMoldPartManager();
	    manager.setOwner(SessionHelper.manager.getPrincipalReference());

	    manager.setDieNo(dieNo);

	    MoldProject project = null;
	    if (projectOid != null && projectOid.length() > 0) {
		project = (MoldProject) CommonUtil.getObject(projectOid);
	    }
	    if (project == null) {
		return null;
	    }

	    manager.setProject(project);


	    int cha = ProjectTaskHelper.getDeBugChaSh(project);
	    String levelType = "";
	    int subCha = 0;
	    if (cha > 1) {
		levelType = "디버깅";
		cha--;
		subCha = getDebugSubCh(dieNo, cha);

	    }
	    else {
		levelType = "금형제작";
		cha = getTypeChaSh(dieNo, levelType);
	    }

	    manager.setEcoNo(ecoNo);
	    manager.setLevelType(levelType);
	    manager.setSubCha(subCha);
	    //		manager.setCreateType(createType);

	    //		Kogger.debug(getClass(), "levelType = " + levelType);
	    //		int ncha = ProjectTaskHelper.manager.getDeBugChaSh(project);



	    manager.setCha(cha);

	    if (partProcess != null && partProcess.length() > 0) {
		WTUser user = (WTUser) CommonUtil.getObject(partProcess);
		manager.setPartUser(WTPrincipalReference.newWTPrincipalReference(user));
	    }

	    if (planDate != null && planDate.length() > 0) {
		manager.setPlanDate(DateUtil.getTimestampFormat(planDate, "yyyy-MM-dd"));
	    }

	    if (moldDate != null && moldDate.length() > 0) {
		manager.setMoldDate(DateUtil.getTimestampFormat(moldDate, "yyyy-MM-dd"));
	    }

	    manager = (MoldPartManager) PersistenceHelper.manager.save(manager);

	    String usageOids[] = (String[]) map.get("usageOid");

	    String partClass[] = (String[]) map.get("partClass");
	    String partType[] = (String[]) map.get("partType");
	    String endDate[] = (String[]) map.get("endDate");
	    String mType[] = (String[]) map.get("mType");
	    String actionType[] = (String[]) map.get("actionType");
	    String transferDate[] = (String[]) map.get("transferDate");
	    String etc[] = (String[]) map.get("etc");

	    for (int i = 0; i < usageOids.length; i++) {
		MoldSubPart moldSubPart = MoldSubPart.newMoldSubPart();
		moldSubPart.setManager(manager);
		String usageOid = usageOids[i];
		Object link = CommonUtil.getObject(usageOid);

		String quantity = "";
		String material = "";
		String number = "";
		String name = "";
		if (link instanceof KETPartUsageLink) {

		    KETPartUsageLink ketLink = (KETPartUsageLink) link;
		    material = ketLink.getMaterial();
		    quantity = ketLink.getQuantity();
		    WTPartMaster master = (WTPartMaster) ketLink.getUses();
		    number = master.getNumber();
		    name = master.getName();

		}
		else if (link instanceof WTPartUsageLink) {
		    WTPartUsageLink usageLink = (WTPartUsageLink) link;
		    int q = (int) usageLink.getQuantity().getAmount();
		    quantity = String.valueOf(q);
		    WTPartMaster master = (WTPartMaster) usageLink.getUses();
		    number = master.getNumber();
		    name = master.getName();
		}

		moldSubPart.setMoldNo(number);
		moldSubPart.setMoldName(name);


		moldSubPart.setEa(quantity);

		moldSubPart.setMaterial(material);
		moldSubPart.setPartClass(partClass[i]);

		if (partType != null && partType[i].length() > 0) {
		    moldSubPart.setPartType(partType[i]);
		}

		if (endDate != null && endDate[i] != null && endDate[i].length() > 0) {

		    Timestamp stamp = DateUtil.getTimestampFormat(endDate[i], "yyyy-MM-dd");
		    moldSubPart.setEndDate(stamp);

		}
		//moldSubPart.setEndDate();

		if (mType != null && mType[i].length() > 0) {
		    moldSubPart.setMType(mType[i]);
		}

		if (actionType != null && actionType[i].length() > 0) {
		    moldSubPart.setActionType(actionType[i]);
		}

		if (transferDate != null && transferDate[i] != null && transferDate[i].length() > 0) {
		    Timestamp stamp = DateUtil.getTimestampFormat(transferDate[i], "yyyy-MM-dd");
		    moldSubPart.setTransferDate(stamp);
		}

		if (etc != null && etc[i].length() > 0) {
		    moldSubPart.setEtcDesc(etc[i]);
		}

		moldSubPart = (MoldSubPart) PersistenceHelper.manager.save(moldSubPart);
	    }
	    manager = setState(manager);

	    transaction.commit();
	    transaction = null;

	} catch (Exception e) {
	    Kogger.error(MoldPartHelper.class, e);
	    throw new WTException(e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

	return manager;
    }

    public static MoldPartManager update(HashMap map) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {

	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = RemoteMethodServer.getDefault().invoke("update", MoldPartHelper.class.getName(), null, argTypes, args);
	    return (MoldPartManager) obj;
	}

	Transaction transaction = new Transaction();
	MoldPartManager manager = null;
	try {
	    transaction.start();
	    String managerOid = (String) map.get("managerOid");

	    String partProcess = (String) map.get("partProcess");
	    String planDate = (String) map.get("completeDate");
	    String moldDate = (String) map.get("exhibitDate");

	    manager = (MoldPartManager) CommonUtil.getObject(managerOid);

	    if (planDate != null && planDate.length() > 0) {
		manager.setPlanDate(DateUtil.getTimestampFormat(planDate, "yyyy-MM-dd"));
	    }
	    else {
		manager.setPlanDate(null);
	    }

	    if (moldDate != null && moldDate.length() > 0) {
		manager.setMoldDate(DateUtil.getTimestampFormat(moldDate, "yyyy-MM-dd"));
	    }
	    else {
		manager.setMoldDate(null);
	    }

	    if (partProcess != null && partProcess.length() > 0) {
		WTUser user = (WTUser) CommonUtil.getObject(partProcess);
		manager.setPartUser(WTPrincipalReference.newWTPrincipalReference(user));
	    }

	    manager = (MoldPartManager) PersistenceHelper.manager.save(manager);


	    String subPartOid[] = (String[]) map.get("subPartOid");

	    String partClass[] = (String[]) map.get("partClass");
	    String partType[] = (String[]) map.get("partType");
	    String endDate[] = (String[]) map.get("endDate");
	    String mType[] = (String[]) map.get("mType");
	    String actionType[] = (String[]) map.get("actionType");
	    String transferDate[] = (String[]) map.get("transferDate");
	    String etc[] = (String[]) map.get("etc");

	    for (int i = 0; i < subPartOid.length; i++) {

		String partOid = subPartOid[i];


		MoldSubPart moldSubPart = (MoldSubPart) CommonUtil.getObject(partOid);


		moldSubPart.setPartClass(partClass[i]);
		Kogger.debug(MoldPartHelper.class, "partType[" + i + "] = " + partType[i]);

		if ("도면정리".equals(partClass[i])) {
		    moldSubPart.setPartType("");
		}
		else if (partType != null && partType[i].length() > 0) {
		    moldSubPart.setPartType(partType[i]);
		}

		if (endDate[i] != null && endDate[i].length() > 0) {
		    Timestamp stamp = DateUtil.getTimestampFormat(endDate[i], "yyyy-MM-dd");
		    moldSubPart.setEndDate(stamp);
		}
		else {
		    moldSubPart.setEndDate(null);
		}

		if (mType != null && mType[i].length() > 0) {
		    moldSubPart.setMType(mType[i]);
		}
		else {
		    moldSubPart.setMType("");
		}

		boolean isCopy = false;

		if (actionType != null && actionType[i].length() > 0) {
		    String preActionType = moldSubPart.getActionType();
		    boolean isDisabled = ("가공".equals(preActionType) || "수정".equals(preActionType));

		    if (!isDisabled && ("가공".equals(actionType[i]) || "수정".equals(actionType[i]))) {
			isCopy = true;
		    }

		    moldSubPart.setActionType(actionType[i]);

		}
		else {
		    moldSubPart.setActionType("");
		}

		if (transferDate[i] != null && transferDate[i].length() > 0) {
		    Timestamp stamp = DateUtil.getTimestampFormat(transferDate[i], "yyyy-MM-dd");
		    moldSubPart.setTransferDate(stamp);
		}
		moldSubPart.setEtcDesc(etc[i]);
		moldSubPart = (MoldSubPart) PersistenceHelper.manager.save(moldSubPart);

		if (isCopy) {
		    MoldSubPart copySubPart = (MoldSubPart) HistoryHelper.duplicate(moldSubPart);
		    copySubPart.setEndDate(null);
		    copySubPart.setMType("");
		    copySubPart.setActionType("");
		    copySubPart.setTransferDate(null);
		    copySubPart.setEtcDesc("");
		    PersistenceHelper.manager.save(copySubPart);

		}
	    }
	    manager = setState(manager);

	    transaction.commit();
	    transaction = null;

	} catch (Exception e) {
	    Kogger.error(MoldPartHelper.class, e);
	    throw new WTException(e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

	return manager;
    }

    public static void delete(HashMap map) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {

	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = RemoteMethodServer.getDefault().invoke("delete", MoldPartHelper.class.getName(), null, argTypes, args);
	    return; //(MoldPartManager)obj;
	}
	String oid = (String) map.get("managerOid");
	MoldPartManager moldManager = (MoldPartManager) CommonUtil.getObject(oid);
	PersistenceHelper.manager.delete(moldManager);

    }


    public static MoldPartManager setState(MoldPartManager manager) throws Exception {

	settingSubParts(manager);

	Vector vector = manager.getSubParts();

	boolean isCompleted = true;
	for (int i = 0; i < vector.size(); i++) {
	    MoldSubPart part = (MoldSubPart) vector.get(i);
	    String actionType = part.getActionType();
	    String partClass = part.getPartClass();

	    if ("가공".equals(actionType) || "수정".equals(actionType) || "도면정리".equals(partClass)) {
		continue;
	    }

	    if (part.getTransferDate() == null) {
		isCompleted = false;
		break;
	    }
	}
	if (isCompleted) {
	    String today = DateUtil.getToDay("yyyy-MM-dd");
	    if (manager.getEndDate() == null) {
		manager.setEndDate(DateUtil.getTimestampFormat(today, "yyyy-MM-dd"));
	    }

	    manager.setMoldState("완료");
	}
	else {
	    manager.setMoldState("");
	}

	manager = (MoldPartManager) PersistenceHelper.manager.save(manager);
	return manager;
    }

    public static int getDebugSubCh(String dieNo, int cha) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { String.class, int.class };
	    Object args[] = new Object[] { dieNo, cha };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("getDebugSubCh", MoldPartHelper.class.getName(), null, argTypes, args);
		return ((Integer) obj).intValue();

	    } catch (RemoteException e) {
		Kogger.error(MoldPartHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(MoldPartHelper.class, e);
		throw new WTException(e);
	    }
	}

	String tableName = "";

	Class taskClass = MoldPartManager.class;

	ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
	    tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
	}
	else {
	    tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
	}

	String ncha = DatabaseInfoUtilities.getValidColumnName(classinfo, MoldPartManager.CHA);

	String subCha = DatabaseInfoUtilities.getValidColumnName(classinfo, MoldPartManager.SUB_CHA);

	String levelTypeColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, MoldPartManager.LEVEL_TYPE);

	String dieNoColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, MoldPartManager.DIE_NO);

	Connection con = null;
	PreparedStatement st = null;
	ResultSet rs = null;

	int childLength = 0;
	try {

	    con = DBCPManager.getConnection("plm");
	    String sql = "select nvl(max(" + subCha + "), '0') from " + tableName + " where " + levelTypeColumnName + " = ? and " + dieNoColumnName + "=? and " + ncha + "=?";
	    //Kogger.debug(MoldPartHelper.class, "###### sql = "+sql);
	    st = con.prepareStatement(sql);

	    st.setString(1, "디버깅");
	    st.setString(2, dieNo);
	    st.setInt(3, cha);

	    rs = st.executeQuery();

	    if (rs.next()) {
		childLength = rs.getInt(1);
	    }
	    else {
		throw new Exception("error..");
	    }
	    //write(root);

	} catch (Exception e) {
	    Kogger.error(MoldPartHelper.class, e);
	    throw e;
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}
		if (st != null) {
		    st.close();
		}
		if (con != null) con.close();
	    } catch (SQLException e) {
		Kogger.error(MoldPartHelper.class, e);
	    }
	}
	return childLength + 1;

    }

    public static int getTypeChaSh(String dieNo, String levelType) throws Exception {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { String.class, String.class };
	    Object args[] = new Object[] { dieNo, levelType };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("getTypeChaSh", MoldPartHelper.class.getName(), null, argTypes, args);
		return ((Integer) obj).intValue();

	    } catch (RemoteException e) {
		Kogger.error(MoldPartHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(MoldPartHelper.class, e);
		throw new WTException(e);
	    }
	}


	String tableName = "";

	Class taskClass = MoldPartManager.class;

	ClassInfo classinfo = WTIntrospector.getClassInfo(taskClass);
	if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
	    tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
	}
	else {
	    tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
	}


	String ncha = DatabaseInfoUtilities.getValidColumnName(classinfo, MoldPartManager.CHA);

	String levelTypeColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, MoldPartManager.LEVEL_TYPE);

	String dieNoColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, MoldPartManager.DIE_NO);

	Connection con = null;
	PreparedStatement st = null;
	ResultSet rs = null;

	int childLength = 0;
	try {

	    con = DBCPManager.getConnection("plm");
	    String sql = "select nvl(max(" + ncha + "), '0') from " + tableName + " where " + levelTypeColumnName + " = ? and " + dieNoColumnName + "=?";
	    //Kogger.debug(MoldPartHelper.class, "###### sql = "+sql);
	    st = con.prepareStatement(sql);

	    st.setString(1, levelType);
	    st.setString(2, dieNo);

	    rs = st.executeQuery();

	    if (rs.next()) {
		childLength = rs.getInt(1);
	    }
	    else {
		throw new Exception("error..");
	    }
	    //write(root);

	} catch (Exception e) {
	    Kogger.error(MoldPartHelper.class, e);
	    throw e;
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}
		if (st != null) {
		    st.close();
		}
		if (con != null) con.close();
	    } catch (SQLException e) {
		Kogger.error(MoldPartHelper.class, e);
	    }
	}
	return childLength + 1;
    }

    public static QueryResult descentPart(String number, View view) throws Exception {
	return descentPart(number, view, WTPartUsageLink.class);
    }

    public static QueryResult descentPart(String number, View view, Class targetUsage, String ecoOid) throws Exception {

	WTPart part = getWTPart(number, view);
	QueryResult rs = new QueryResult();

	if (part != null) {
	    Kogger.debug(MoldPartHelper.class, "==========" + part);
	    rs = descentPart(part, targetUsage, ecoOid);
	}
	return rs;
    }

    public static QueryResult descentPart(String number, View view, Class targetUsage) throws Exception {
	WTPart part = getWTPart(number, view);
	QueryResult rs = new QueryResult();

	if (part != null) {
	    rs = descentPart(part, targetUsage, null);
	}

	return rs;
    }

    public static WTPart getWTPart(String number, View view) throws Exception {
	QuerySpec qs = new QuerySpec(WTPart.class);

	qs.appendWhere(new SearchCondition(WTPart.class, "iterationInfo.latest", SearchCondition.IS_TRUE, true), new int[] { 0 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(WTPart.class, "checkoutInfo.state", "<>", "c/o"), new int[] { 0 });

	if (view != null) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(WTPart.class, "view.key.id", "=", view.getPersistInfo().getObjectIdentifier().getId()), new int[] { 0 });
	}

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.LIKE, number), new int[] { 0 });


	QueryResult rs = PersistenceHelper.manager.find(qs);
	settingLastVersion(rs.getObjectVectorIfc().getVector());

	WTPart part = null;
	if (rs.hasMoreElements()) {
	    part = (WTPart) rs.nextElement();
	}

	return part;
    }

    public static void settingLastVersion(Vector vector) {

	Vector convertVector = new Vector();
	Hashtable hash = new Hashtable();

	for (int i = 0; i < vector.size(); i++) {

	    Object o = vector.get(i);
	    WTPart part = null;

	    part = getPartFromObject(o);

	    String number = part.getNumber();

	    Object preO = (Object) hash.get(number);

	    WTPart prePart = (WTPart) hash.get(number);


	    if (prePart != null) {
		long bi = prePart.getBranchIdentifier();
		if (part.getBranchIdentifier() >= bi) {
		    int index = convertVector.indexOf(preO);
		    convertVector.remove(preO);
		    convertVector.insertElementAt(o, index);
		    hash.put(number, o);
		}
	    }
	    else {
		convertVector.add(o);
		hash.put(number, o);
	    }
	}

	vector.clear();
	vector.addAll(convertVector);
    }

    public static WTPart getPartFromObject(Object o) {
	WTPart part = null;
	if (o instanceof WTPart) {
	    part = (WTPart) o;
	}
	else if (o instanceof Object[]) {
	    part = (WTPart) ((Object[]) o)[0];
	}
	return part;
    }

    public static QueryResult descentPartEco(String ecoOid) throws WTException {
	try {
	    QueryResult qr = null;
	    QuerySpec qs = new QuerySpec();
	    //int index1 = qs.addClassList(KETPartUsageLink.class, true);

	    int index6 = qs.addClassList(EPMDocument.class, true);
	    int index5 = qs.addClassList(KETMoldECAEpmDocLink.class, true);
	    int index4 = qs.addClassList(KETMoldChangeActivity.class, false);

	    long ecoId = CommonUtil.getOIDLongValue(ecoOid);

	    SearchCondition sc = new SearchCondition();

	    qs.appendWhere(new SearchCondition(KETMoldChangeActivity.class, "moldECOReference.key.id", "=", ecoId), new int[] { index4 });

	    sc = new SearchCondition(new ClassAttribute(KETMoldChangeActivity.class, "thePersistInfo.theObjectIdentifier.id"), "=", new ClassAttribute(KETMoldECAEpmDocLink.class,
		    "roleBObjectRef.key.id"));

	    sc.setOuterJoin(0);
	    qs.appendAnd();
	    qs.appendWhere(sc, new int[] { index4, index5 });


	    sc = new SearchCondition(new ClassAttribute(EPMDocument.class, "thePersistInfo.theObjectIdentifier.id"), "=", new ClassAttribute(KETMoldECAEpmDocLink.class, "roleAObjectRef.key.id"));

	    sc.setOuterJoin(0);

	    qs.appendAnd();
	    qs.appendWhere(sc, new int[] { index6, index5 });

	    //Kogger.debug(MoldPartHelper.class, "descentPartEco query ::: \n" + qs + " \n ===============================");

	    qr = PersistenceHelper.manager.find(qs);
	    return qr;

	} catch (Exception e) {

	}
	return null;
    }

    // ECO 부품 현황 가져오기

    public static QueryResult descentPartECOState(String ecoOid, String number) throws WTException {

	/*
	 KETMOldStdPartLine Attr
	 - partOid
	 - partNo
	 - changeType
	 - moldEcoOid

	 */

	if (ecoOid == null || ecoOid.length() == 0) {
	    return null;
	}

	QuerySpec qs = null;
	try {
	    qs = new QuerySpec();
	    Class idx_cls = KETMoldStdPartLine.class;

	    //Class targetUsage = KETPartUsageLink.class;
	    int idx = qs.appendClassList(idx_cls, true);
	    int[] intidx = new int[] { idx };
	    SearchCondition sc = null;

	    /*
	    int index1 = qs.addClassList(targetUsage, true);
	    int index2 = qs.addClassList(WTPart.class, true);

	    qs.appendWhere(new SearchCondition(targetUsage, "roleAObjectRef.key.id", "=", part
	            .getPersistInfo().getObjectIdentifier().getId()), new int[] { index1 });
	    sc = new SearchCondition(
	            new ClassAttribute(targetUsage, "roleBObjectRef.key.id"), "=", new ClassAttribute(
	                    WTPart.class, "masterReference.key.id"));

	    sc.setOuterJoin(0);
	    qs.appendAnd();
	    qs.appendWhere(sc, new int[] { index1, index2 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(WTPart.class, "iterationInfo.latest", SearchCondition.IS_TRUE, true),
	                   new int[] { index2 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(WTPart.class, "checkoutInfo.state", "<>", "c/o"),
	            new int[] { index2 });
	     */
	    sc = new SearchCondition(idx_cls, KETMoldStdPartLine.MOLD_ECO_OID, SearchCondition.EQUAL, ecoOid);
	    qs.appendWhere(sc, intidx);
	    //SearchUtil.setOrderBy(qs, WTPart.class, index2, WTPart.NUMBER , "partNo", false);

	    //Kogger.debug(MoldPartHelper.class, "descentPartECOState ::: " + qs);


	} catch (Exception e) {
	    Kogger.error(MoldPartHelper.class, e);
	}
	return PersistenceHelper.manager.find(qs);
    }

    public static ArrayList ArrayEpm(EPMDocument epm) {

	Kogger.debug(MoldPartHelper.class, "=======join========");
	ArrayList result = new ArrayList();

	QueryResult re = new QueryResult();

	try {
	    QuerySpec qs = new QuerySpec();

	    Class clsEpm = EPMDocument.class;
	    QuerySpec qsth = new QuerySpec();
	    int idxEpm = qsth.appendClassList(clsEpm, true);
	    qsth.appendWhere(new SearchCondition(clsEpm, "thePersistInfo.theObjectIdentifier.id", "=", epm.getPersistInfo().getObjectIdentifier().getId()), new int[] { idxEpm });

	    Kogger.debug(MoldPartHelper.class, "qsth :::::::::: \n" + qsth);


	    re = PersistenceHelper.manager.find((StatementSpec) qsth);

	    EPMDocument epmDoc = null;

	    Object oo[] = null;

	    while (re.hasMoreElements()) {
		oo = (Object[]) re.nextElement();
		epmDoc = (EPMDocument) oo[0];
		result.add(epmDoc);
	    }

	} catch (Exception e) {

	}
	return result;
    }

    public static QueryResult descentPart(WTPart part, Class targetUsage, String ecoOid) throws WTException {
	QueryResult re = new QueryResult();

	//Kogger.debug(MoldPartHelper.class, "ECOOID == " + ecoOid);
	try {
	    QuerySpec qs = new QuerySpec();
	    int index2 = qs.addClassList(WTPart.class, true);
	    int index1 = qs.addClassList(targetUsage, true);
	    qs.appendWhere(new SearchCondition(targetUsage, "roleAObjectRef.key.id", "=", part.getPersistInfo().getObjectIdentifier().getId()), new int[] { index1 });
	    SearchCondition sc = new SearchCondition(new ClassAttribute(targetUsage, "roleBObjectRef.key.id"), "=", new ClassAttribute(WTPart.class, "masterReference.key.id"));

	    sc.setOuterJoin(0);
	    qs.appendAnd();
	    qs.appendWhere(sc, new int[] { index1, index2 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(WTPart.class, "iterationInfo.latest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	    View view = ViewHelper.getView(part);
	    if (view != null) {
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(WTPart.class, "view.key.id", "=", view.getPersistInfo().getObjectIdentifier().getId()), new int[] { index2 });
	    }
	    qs.appendAnd();

	    qs.appendWhere(new SearchCondition(WTPart.class, "checkoutInfo.state", "<>", "c/o"), new int[] { index2 });

	    if (ecoOid != null && ecoOid.length() > 0) {
		QueryResult qrEpm = descentPartEco(ecoOid);
		Kogger.debug(MoldPartHelper.class, "qrEpm ::: " + qrEpm.size());
		if (qrEpm.size() > 0) {
		    Object[] obj = null;
		    ArrayList refsParts = null;
		    int epmCount = 0;
		    while (qrEpm.hasMoreElements()) {
			obj = (Object[]) qrEpm.nextElement();
			EPMDocument epm = (EPMDocument) obj[0];
			Kogger.debug(MoldPartHelper.class, "while epm ::: " + epm);
			//refsParts = EDMHelper.getReferencedByParts(epm,null,-1);
			refsParts = ArrayEpm(epm);
			Kogger.debug(MoldPartHelper.class, "refsParts.size ::: " + refsParts.size());
			if (refsParts.size() > 0) {
			    WTPart refsPart = null;
			    long wtpartId = 0;
			    if (epmCount == 0) {
				if (qs.getConditionCount() > 0) {
				    qs.appendAnd();
				}
				qs.appendOpenParen();
			    }
			    for (int i = 0; i < refsParts.size(); i++) {
				//								refsPart = (WTPart)((Object[])refsParts.get(i))[1];
				String start = epm.getNumber().substring(0, epm.getNumber().lastIndexOf("_"));
				//								if(!start.equals(refsPart.getNumber()))
				//								{
				//									continue;
				//								}
				//
				//
				//								wtpartId = CommonUtil.getOIDLongValue(refsPart);
				if (i == 0) {
				    qs.appendOpenParen();
				}
				//			            		qs.appendWhere(new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", "=", wtpartId ),new int[] { index2 });
				qs.appendWhere(new SearchCondition(WTPart.class, "master>number", "=", start), new int[] { index2 });
				if (refsParts.size() - 1 == i) {
				    qs.appendCloseParen();
				}
				else {

				    if (!start.equals(refsPart.getNumber())) {
					//continue;
					qs.appendOr();
				    }
				    qs.appendCloseParen();
				}
			    }
			    if (qrEpm.size() - 1 == epmCount) {
				qs.appendCloseParen();
			    }
			    else {

				qs.appendOr();
			    }
			    epmCount++;
			}
			else {
			    if (qs.getConditionCount() > 0) {
				qs.appendAnd();
			    }
			    qs.appendWhere(new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", "=", 0), new int[] { index2 });
			}
		    }
		}
		else {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    qs.appendWhere(new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", "=", 0), new int[] { index2 });
		}
	    }
	    SearchUtil.setOrderBy(qs, WTPart.class, index2, WTPart.NUMBER, "partNo", false);

	    //Kogger.debug(MoldPartHelper.class, ">>>>>>>>>>>>>>>>>>> ecoOid ::: " + ecoOid + "\n쿼리문 \n" + qs);
	    re = PersistenceHelper.manager.find(qs);
	    settingLastVersion(re.getObjectVectorIfc().getVector());

	} catch (Exception ex) {
	}
	return re;

    }

    public static Vector getMoldPartManager(MoldProject project) throws Exception {

	QuerySpec qs = new QuerySpec();

	int index1 = qs.addClassList(MoldPartManager.class, true);
	int index3 = qs.addClassList(MoldProject.class, false);

	SearchCondition sc = new SearchCondition(new ClassAttribute(MoldPartManager.class, "projectReference.key.id"), "=", new ClassAttribute(MoldProject.class,
	        "thePersistInfo.theObjectIdentifier.id"));

	sc.setOuterJoin(0);

	qs.appendWhere(sc, new int[] { index1, index3 });


	long masterId = project.getMaster().getPersistInfo().getObjectIdentifier().getId();

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(MoldProject.class, MoldProject.MASTER_REFERENCE + ".key.id", "=", masterId), new int[] { index3 });

	QueryResult rs = PersistenceHelper.manager.find(qs);
	Vector v = new Vector();
	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();

	    MoldPartManager manager = (MoldPartManager) o[0];
	    v.add(manager);
	}

	Collections.sort(v, new MoldPartManagerComparator());
	return v;
    }

    public static Vector getMoldPartManagerListWithSubPart(String projectOid) throws Exception {

	return getMoldPartManagerListWithSubPart((MoldProject) CommonUtil.getObject(projectOid));
    }


    public static Vector getMoldPartManagerListWithSubPart(MoldProject project) throws Exception {

	QuerySpec qs = new QuerySpec();

	int index1 = qs.addClassList(MoldPartManager.class, false);
	int index2 = qs.addClassList(MoldSubPart.class, true);
	int index3 = qs.addClassList(MoldProject.class, false);

	SearchCondition sc = new SearchCondition(new ClassAttribute(MoldPartManager.class, "thePersistInfo.theObjectIdentifier.id"), "=", new ClassAttribute(MoldSubPart.class,
	        "managerReference.key.id"));

	sc.setOuterJoin(0);

	qs.appendWhere(sc, new int[] { index1, index2 });

	qs.appendAnd();



	sc = new SearchCondition(new ClassAttribute(MoldPartManager.class, "projectReference.key.id"), "=", new ClassAttribute(MoldProject.class, "thePersistInfo.theObjectIdentifier.id"));

	sc.setOuterJoin(0);

	qs.appendWhere(sc, new int[] { index1, index3 });

	qs.appendAnd();

	long masterId = project.getMaster().getPersistInfo().getObjectIdentifier().getId();

	qs.appendWhere(new SearchCondition(MoldProject.class, MoldProject.MASTER_REFERENCE + ".key.id", "=", masterId), new int[] { index3 });

	SearchUtil.setOrderBy(qs, MoldSubPart.class, index2, MoldSubPart.MOLD_NO, "moldNo", false);
	SearchUtil.setOrderBy(qs, MoldSubPart.class, index2, WTAttributeNameIfc.CREATE_STAMP_NAME, "time", false);

	QueryResult rs = PersistenceHelper.manager.find(qs);

	Hashtable hash = new Hashtable();


	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    MoldSubPart part = (MoldSubPart) o[0];
	    MoldPartManager manager = part.getManager();
	    String key = CommonUtil.getOIDString(manager);
	    MoldPartManager hManager = (MoldPartManager) hash.get(key);
	    if (hManager != null) {
		manager = hManager;
	    }
	    Vector vector = manager.getSubParts();
	    if (vector == null) {
		vector = new Vector();
	    }
	    vector.add(part);
	    manager.setSubParts(vector);
	    hash.put(key, manager);
	}

	Vector v = new Vector();
	v.addAll(hash.values());
	Collections.sort(v, new MoldPartManagerComparator());
	return v;

    }

    public static void settingSubParts(MoldPartManager manager) throws Exception {
	QuerySpec qs = new QuerySpec();
	int index1 = qs.addClassList(MoldSubPart.class, true);

	long managerId = manager.getPersistInfo().getObjectIdentifier().getId();
	qs.appendWhere(new SearchCondition(MoldSubPart.class, MoldSubPart.MANAGER_REFERENCE + ".key.id", "=", managerId), new int[] { index1 });

	SearchUtil.setOrderBy(qs, MoldSubPart.class, index1, MoldSubPart.MOLD_NO, "moldNo", false);

	SearchUtil.setOrderBy(qs, MoldSubPart.class, index1, WTAttributeNameIfc.CREATE_STAMP_NAME, "time", false);
	QueryResult rs = PersistenceHelper.manager.find(qs);
	Vector vector = new Vector();
	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    MoldSubPart part = (MoldSubPart) o[0];

	    vector.add(part);

	}
	manager.setSubParts(vector);
    }



    public static QueryResult getMoldProject(String dieNo) throws Exception {

	QuerySpec qs = new QuerySpec();
	int index1 = qs.addClassList(MoldProject.class, true);
	int index2 = qs.addClassList(MoldItemInfo.class, false);

	SearchCondition sc = new SearchCondition(new ClassAttribute(MoldProject.class, "moldInfoReference.key.id"), "=",
	        new ClassAttribute(MoldItemInfo.class, "thePersistInfo.theObjectIdentifier.id"));

	sc.setOuterJoin(0);

	qs.appendWhere(sc, new int[] { index1, index2 });

	if (qs.getConditionCount() > 0) qs.appendAnd();
	qs.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")), new int[] { index1 });

	if (qs.getConditionCount() > 0) qs.appendAnd();
	qs.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { index1 });

	if (dieNo != null && dieNo.length() > 0) {
	    dieNo = StringUtil.changeString(dieNo, "*", "%");
	    qs.appendAnd();
	    dieNo = dieNo.toUpperCase();
	    ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.DIE_NO);
	    SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
	    ColumnExpression ce = ConstantExpression.newExpression(dieNo);
	    SearchCondition sc0 = new SearchCondition(upper, SearchCondition.LIKE, ce);
	    qs.appendWhere(sc0, new int[] { index2 });

	}



	SearchUtil.setOrderBy(qs, MoldItemInfo.class, index2, MoldItemInfo.DIE_NO, "dieNo", false);

	return PersistenceHelper.manager.find(qs);
	//return null;
    }

    public static void write(MoldPartManager manager, OutputStream out) throws Exception {
	write(manager, out, false);
    }

    public static void write(MoldPartManager manager, OutputStream out, boolean isModify) throws Exception {

	//Excel file download ...
	java.util.Date date = new java.util.Date();
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
	String fileName = "MoldProjectList_" + formatter.format(date) + ".xls";

	try {

	    settingSubParts(manager);

	    //	        WritableWorkbook workbook = Workbook.createWorkbook(out);
	    WritableWorkbook workbook = Workbook.createWorkbook(new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName));
	    WritableSheet sheet = workbook.createSheet("list", 1);
	    WritableCellFormat titleformat = getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

	    int row = 0;
	    int column = 0;

	    String titles[] = new String[] { "Title", "Die No", "전체/진행/완료", "부품완료예정일", "출도일", "부품공정 담당자" };

	    for (int i = 0; i < titles.length; i++) {
		sheet.addCell(new Label(i, row, titles[i], titleformat));
	    }


	    MoldPartManagerData data = new MoldPartManagerData(manager);
	    ++row;

	    String dieNumber = data.dieNo;
	    String cType = data.createType;
	    String mlevelType = data.levelType;
	    String title = data.title;
	    String mcreatorName = data.creator;
	    String pUserName = data.partUser;
	    String createTime = data.createDate;
	    String planTime = data.planDate;
	    String moldDate = data.moldDate;

	    if (planTime == null || planTime.length() == 0) {
		planTime = "";
	    }

	    int columnIndex = 0;
	    sheet.addCell(new Label(columnIndex++, row, title));
	    sheet.addCell(new Label(columnIndex++, row, dieNumber));
	    sheet.addCell(new Label(columnIndex++, row, data.totalState));
	    sheet.addCell(new Label(columnIndex++, row, planTime));
	    sheet.addCell(new Label(columnIndex++, row, moldDate));
	    sheet.addCell(new Label(columnIndex++, row, pUserName));

	    row += 3;

	    if (isModify) {
		titles = new String[] { "OID", "No", "금형부품번호", "금형부품설명", "수량", "재질", "부품유형", "부품분류", "부품측정완료일", "측정구분", "불합격조치사항", "인계일", "비고" };
	    }
	    else {
		titles = new String[] { "No", "금형부품번호", "금형부품설명", "수량", "재질", "부품유형", "부품분류", "부품측정완료일", "측정구분", "불합격조치사항", "인계일", "비고" };
	    }

	    for (int i = 0; i < titles.length; i++) {
		sheet.addCell(new Label(i, row, titles[i], titleformat));
	    }


	    Vector subParts = manager.getSubParts();

	    for (int i = 0; i < subParts.size(); i++) {
		++row;
		columnIndex = 0;


		MoldSubPart part = (MoldSubPart) subParts.get(i);
		MoldSubPartData subpart = new MoldSubPartData(part);

		String partNumber = subpart.partNumber;
		String partName = subpart.partName;

		String quantity = subpart.quantity;
		String material = subpart.material;

		if (material == null) {
		    material = "";
		}

		String partClass = subpart.partClass;
		String partType = subpart.partType;
		String endDate = subpart.endDate;

		String mType = subpart.mType;
		if (mType == null) {
		    mType = "";
		}
		String actionType = subpart.actionType;
		if (actionType == null) {
		    actionType = "";
		}

		String transferDate = subpart.transferDate;
		String etc = subpart.etc;
		if (etc == null) {
		    etc = "";
		}

		if (isModify) {
		    if ("도면정리".equals(partClass) || transferDate.length() > 0) {
			--row;
			continue;
		    }
		    sheet.addCell(new Label(columnIndex++, row, subpart.oid));
		}

		sheet.addCell(new Label(columnIndex++, row, String.valueOf(i + 1)));
		sheet.addCell(new Label(columnIndex++, row, partNumber));
		sheet.addCell(new Label(columnIndex++, row, partName));
		sheet.addCell(new Label(columnIndex++, row, quantity));
		sheet.addCell(new Label(columnIndex++, row, material));
		sheet.addCell(new Label(columnIndex++, row, partClass));
		sheet.addCell(new Label(columnIndex++, row, partType));
		sheet.addCell(new Label(columnIndex++, row, endDate));
		sheet.addCell(new Label(columnIndex++, row, mType));
		sheet.addCell(new Label(columnIndex++, row, actionType));
		sheet.addCell(new Label(columnIndex++, row, transferDate));
		sheet.addCell(new Label(columnIndex++, row, etc));
	    }

	    //QuerySpec spec = getQuerySpec(req, res);
	    //QueryResult result = PersistenceHelper.manager.find(spec);


	    /*while(result.hasMoreElements()){
	        	++row;
	        	int columnIndex = 0;
	        	Object[] obj = (Object[])result.nextElement();
	    		MoldPartManager manager = (MoldPartManager)obj[0];
	    		MoldPartManagerData data = new MoldPartManagerData(manager);

	    		String dieNumber = data.dieNo;
	    		String cType = data.createType;
	    		String mlevelType = data.levelType;
	    		String title = data.title;
	    		String mcreatorName = data.creator;
	    		String pUserName = data.partUser;
	    		String createTime = data.createDate;
	    		String planTime = data.planDate;

	    		if(planTime == null || planTime.length() == 0){
	    			planTime = "";
	    		}

	    		String state = data.state;

	    		sheet.addCell(new Label(columnIndex++, row, dieNumber));
	    		sheet.addCell(new Label(columnIndex++, row, cType));
	    		sheet.addCell(new Label(columnIndex++, row, mlevelType));
	    		sheet.addCell(new Label(columnIndex++, row, title));
	    		sheet.addCell(new Label(columnIndex++, row, mcreatorName));
	    		sheet.addCell(new Label(columnIndex++, row, pUserName));
	    		sheet.addCell(new Label(columnIndex++, row, createTime));
	    		sheet.addCell(new Label(columnIndex++, row, planTime));
	        }
	     */
	    workbook.write();
	    workbook.close();
	} catch (Exception ex) {
	    Kogger.error(MoldPartHelper.class, ex);
	}

	File drmFile = new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName);

	try {
	    /*if(!isModify){
	    		drmFile = E3PSDRMHelper.downloadExcel( drmFile, fileName, fileName.substring(0, fileName.lastIndexOf(".")), "" );
	    	}*/
	    FileInputStream fin = new FileInputStream(drmFile);
	    int ifilesize = (int) drmFile.length();
	    byte b[] = new byte[ifilesize];

	    fin.read(b);
	    out.write(b, 0, ifilesize);

	    out.flush();
	    out.close();
	    fin.close();
	} catch (Exception wte) {
	    Kogger.error(MoldPartHelper.class, wte);
	}
    }

    private static WritableCellFormat getCellFormat(jxl.format.Alignment alignment, jxl.format.Colour color) {
	WritableCellFormat format = null;
	try {
	    format = new WritableCellFormat();
	    if (color != null) format.setBackground(color);

	    format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

	    if (alignment != null) format.setAlignment(alignment);
	} catch (Exception e) {
	    Kogger.error(MoldPartHelper.class, e);
	}
	return format;
    }

    /*
        public static boolean moldPartSendMail(MoldPart moldPart){
    	try {
    	    HashMap toHash = new HashMap();
    	    String subject = "";
    	    String content = "";

    	    WTPrincipal wtprincipal = moldPart.getPartUser().getPrincipal();

    	    if( wtprincipal instanceof WTUser ) {
    		WTUser memberUser = (WTUser)wtprincipal;
    		toHash.put(memberUser.getEMail(), memberUser.getFullName());
    	    }

    	    subject = "";
    	    content = "";

    	    Hashtable mailHash = new Hashtable();
    	    mailHash.put("TO", toHash);
    	    mailHash.put("SUBJECT", subject);
    	    mailHash.put("CONTENT", content);

    	    return MailUtil.sendMail3(mailHash);

    	} catch (Exception e) {
    	    Kogger.error(MoldPartHelper.class, e);
    	}

    	return false;
        }
    */
    public static MoldProject getLastMoldProject(MoldProject project) throws Exception {
	MoldProject moldProject = null;

	String dieNo = CommonUtil.getOIDString(project.getMoldInfo());

	QuerySpec qs = new QuerySpec();
	int index1 = qs.addClassList(MoldProject.class, true);


	if (qs.getConditionCount() > 0) qs.appendAnd();
	qs.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")), new int[] { index1 });

	if (qs.getConditionCount() > 0) qs.appendAnd();
	qs.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { index1 });



	qs.appendAnd();

	long masterId = project.getMaster().getPersistInfo().getObjectIdentifier().getId();

	qs.appendWhere(new SearchCondition(MoldProject.class, MoldProject.MASTER_REFERENCE + ".key.id", "=", masterId), new int[] { index1 });




	QueryResult rs = PersistenceHelper.manager.find(qs);

	if (rs.hasMoreElements()) {
	    Object obj[] = (Object[]) rs.nextElement();
	    moldProject = (MoldProject) obj[0];
	}

	return moldProject;
    }

    public static Hashtable uploadExcel(File file) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {

	    Class argTypes[] = new Class[] { File.class };
	    Object args[] = new Object[] { file };
	    Object obj = RemoteMethodServer.getDefault().invoke("uploadExcel", MoldPartHelper.class.getName(), null, argTypes, args);
	    return (Hashtable) obj;
	}
	file = E3PSDRMHelper.upload(file, file.getName());
	Transaction transaction = new Transaction();
	Hashtable hash = new Hashtable();
	int cnt = 0;
	try {
	    transaction.start();

	    Workbook wb = null;
	    //file = E3PSDRMHelper.upload(file, file.getName());
	    try {

		wb = Workbook.getWorkbook(file);
	    } catch (Exception ex) {
		throw new Exception("엑셀 양식이 적합하지 않습니다.");
	    }
	    Sheet[] sheets = wb.getSheets();
	    Sheet sheet = sheets[0];
	    int rows = sheet.getRows();

	    for (int i = 5; i < rows; i++) {
		cnt = i + 1;
		Cell[] cell = sheet.getRow(i);

		String oid = JExcelUtil.getContent(cell, 0).trim();
		String partClass = JExcelUtil.getContent(cell, 6).trim(); // 부품유형
		String partType = JExcelUtil.getContent(cell, 7).trim(); // 부품분류
		String endDate = JExcelUtil.getContent(cell, 8).trim(); // 부품측정완료일
		String mType = JExcelUtil.getContent(cell, 9).trim(); // 측정구분
		String actionType = JExcelUtil.getContent(cell, 10).trim(); // 불합격조치사유
		String transferDate = JExcelUtil.getContent(cell, 11).trim(); // 인계일

		Kogger.debug(MoldPartHelper.class, "oid == " + oid);
		Kogger.debug(MoldPartHelper.class, "partClass == " + partClass);
		Kogger.debug(MoldPartHelper.class, "partType == " + partType);
		Kogger.debug(MoldPartHelper.class, "endDate == " + endDate);
		Kogger.debug(MoldPartHelper.class, "mType == " + mType);
		Kogger.debug(MoldPartHelper.class, "actionType == " + actionType);
		Kogger.debug(MoldPartHelper.class, "transferDate == " + transferDate);

		MoldSubPart moldSubPart = (MoldSubPart) CommonUtil.getObject(oid);

		if (!StringUtil.checkString(oid)) {
		    break;
		}

		if (StringUtil.checkString(partClass)) {
		    moldSubPart.setPartClass(partClass);
		}

		if (StringUtil.checkString(partType)) {
		    moldSubPart.setPartType(partType);
		}

		boolean check = false;
		if (StringUtil.checkString(mType)) {
		    moldSubPart.setMType(mType);

		    check = true;
		}

		if (StringUtil.checkString(endDate)) {
		    Timestamp stamp = DateUtil.getTimestampFormat(endDate, "yyyy-MM-dd");
		    moldSubPart.setEndDate(stamp);
		}
		else if (check) {
		    throw new Exception(cnt + "번째줄의 부품측정 완료일을 입력하십시오.");
		}

		if (StringUtil.checkString(actionType)) {
		    moldSubPart.setActionType(actionType);
		}

		if (StringUtil.checkString(transferDate)) {
		    Timestamp stamp = DateUtil.getTimestampFormat(transferDate, "yyyy-MM-dd");
		    moldSubPart.setTransferDate(stamp);
		}

		PersistenceHelper.manager.save(moldSubPart);

	    }

	    hash.put("result", "true");

	    transaction.commit();
	    transaction = null;

	} catch (Exception e) {
	    Kogger.error(MoldPartHelper.class, e);
	    hash.put("result", "false");
	    if (cnt > 4) {
		hash.put("message", cnt + "번째줄의 입력값의 오류가 있습니다.");
	    }
	    else {
		hash.put("message", e.getLocalizedMessage());
	    }
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

	return hash;
    }

    public static void main(String args[]) throws Exception {
	//GC000003

	HashMap map = new HashMap();
	map.put("kk", null);

	Kogger.debug(MoldPartHelper.class, map.get("kk"));


	RemoteMethodServer server = RemoteMethodServer.getDefault();
	server.setUserName("wcadmin");
	server.setPassword("wcadmin");

	WTPart part = getWTPart("0000000001", null);
	Kogger.debug(MoldPartHelper.class, "part = " + part.getVersionIdentifier().getSeries().getValue());

	QueryResult rs = descentPart("GC000003", null);
	Kogger.debug(MoldPartHelper.class, "descentPart.size() === " + rs.size());

	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    Kogger.debug(MoldPartHelper.class, o[1]);
	}

    }


}
