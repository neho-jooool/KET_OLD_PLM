package e3ps.project.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.State;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.query.SearchUtil;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.common.web.ParamUtil;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.project.CostInfo;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.PerformType;
import e3ps.project.Performance;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.TemplateProject;
import e3ps.project.Weights;
import e3ps.project.sap.ProductPrice;
import e3ps.project.sap.SAPMoldPrice;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SessionUtil;

public class PerformanceHelper implements Serializable, RemoteAccess {
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    public static final PerformanceHelper manager = new PerformanceHelper();
    static final boolean printMsg = true;

    private PerformanceHelper() {
    }

    public static Weights saveAction(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("saveAction", PerformanceHelper.class.getName(), null, argTypes, args);

	    } catch (RemoteException e) {
		Kogger.error(PerformanceHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(PerformanceHelper.class, e);
	    }
	    return (Weights) obj;
	}
	Transaction trx = new Transaction();
	Weights wg = null;
	String oid = StringUtil.checkNull((String) map.get("oid"));
	String isProject = StringUtil.checkNull((String) map.get("isProject"));
	boolean isProjectFlag = false;
	if (isProject.length() > 0) {
	    isProjectFlag = true;
	}

	String wType = StringUtil.checkNull((String) map.get("wType"));
	String quality = StringUtil.checkNull((String) map.get("quality"));
	String cost = StringUtil.checkNull((String) map.get("cost"));
	String deliveryOne = StringUtil.checkNull((String) map.get("deliveryOne"));
	String deliveryTwo = StringUtil.checkNull((String) map.get("deliveryTwo"));
	String deliveryThree = StringUtil.checkNull((String) map.get("deliveryThree"));
	String technical = StringUtil.checkNull((String) map.get("technical"));

	String totalS = StringUtil.checkNull((String) map.get("totalS"));
	String totalA = StringUtil.checkNull((String) map.get("totalA"));
	String totalB = StringUtil.checkNull((String) map.get("totalB"));
	String totalC = StringUtil.checkNull((String) map.get("totalC"));
	String totalD = StringUtil.checkNull((String) map.get("totalD"));

	String qS = StringUtil.checkNull((String) map.get("qS"));
	String qA = StringUtil.checkNull((String) map.get("qA"));
	String qB = StringUtil.checkNull((String) map.get("qB"));
	String qC = StringUtil.checkNull((String) map.get("qC"));
	String qD = StringUtil.checkNull((String) map.get("qD"));
	String cS = StringUtil.checkNull((String) map.get("cS"));
	String cA = StringUtil.checkNull((String) map.get("cA"));
	String cB = StringUtil.checkNull((String) map.get("cB"));
	String cC = StringUtil.checkNull((String) map.get("cC"));
	String cD = StringUtil.checkNull((String) map.get("cD"));
	String c2S = StringUtil.checkNull((String) map.get("c2S"));
	String c2A = StringUtil.checkNull((String) map.get("c2A"));
	String c2B = StringUtil.checkNull((String) map.get("c2B"));
	String c2C = StringUtil.checkNull((String) map.get("c2C"));
	String c2D = StringUtil.checkNull((String) map.get("c2D"));
	String dOneS = StringUtil.checkNull((String) map.get("dOneS"));
	String dOneA = StringUtil.checkNull((String) map.get("dOneA"));
	String dOneB = StringUtil.checkNull((String) map.get("dOneB"));
	String dOneC = StringUtil.checkNull((String) map.get("dOneC"));
	String dOneD = StringUtil.checkNull((String) map.get("dOneD"));
	String dTwoS = StringUtil.checkNull((String) map.get("dTwoS"));
	String dTwoA = StringUtil.checkNull((String) map.get("dTwoA"));
	String dTwoB = StringUtil.checkNull((String) map.get("dTwoB"));
	String dTwoC = StringUtil.checkNull((String) map.get("dTwoC"));
	String dTwoD = StringUtil.checkNull((String) map.get("dTwoD"));
	String dThreeS = StringUtil.checkNull((String) map.get("dThreeS"));
	String dThreeA = StringUtil.checkNull((String) map.get("dThreeA"));
	String dThreeB = StringUtil.checkNull((String) map.get("dThreeB"));
	String dThreeC = StringUtil.checkNull((String) map.get("dThreeC"));
	String dThreeD = StringUtil.checkNull((String) map.get("dThreeD"));
	String tS = StringUtil.checkNull((String) map.get("tS"));
	String tA = StringUtil.checkNull((String) map.get("tA"));
	String tB = StringUtil.checkNull((String) map.get("tB"));
	String tC = StringUtil.checkNull((String) map.get("tC"));
	String tD = StringUtil.checkNull((String) map.get("tD"));
	String keyNo = StringUtil.checkNull((String) map.get("keyNo"));

	ReferenceFactory rf = new ReferenceFactory();
	try {
	    trx.start();
	    if (oid.length() > 0) {
		wg = (Weights) rf.getReference(oid).getObject();
	    } else {
		wg = Weights.newWeights();
	    }

	    wg.setIsProject(isProjectFlag);
	    wg.setWType(Integer.parseInt(wType));

	    if (quality.length() == 0) {
		wg.setQuality(0);
	    } else {
		wg.setQuality(Integer.parseInt(quality));
	    }

	    if (cost.length() == 0) {
		wg.setCost(0);
	    } else {
		wg.setCost(Integer.parseInt(cost));
	    }

	    if (deliveryOne.length() == 0) {
		wg.setDeliveryOne(0);
	    } else {
		wg.setDeliveryOne(Integer.parseInt(deliveryOne));
	    }

	    if (deliveryTwo.length() == 0) {
		wg.setDeliveryTwo(0);
	    } else {
		wg.setDeliveryTwo(Integer.parseInt(deliveryTwo));
	    }

	    if (deliveryThree.length() == 0) {
		wg.setDeliveryThree(0);
	    } else {
		wg.setDeliveryThree(Integer.parseInt(deliveryThree));
	    }

	    if (technical.length() == 0) {
		wg.setTechnical(0);
	    } else {
		wg.setTechnical(Integer.parseInt(technical));
	    }

	    wg.setTotalS(Integer.parseInt(totalS));
	    wg.setTotalA(Integer.parseInt(totalA));
	    wg.setTotalB(Integer.parseInt(totalB));
	    wg.setTotalC(Integer.parseInt(totalC));
	    wg.setTotalD(Integer.parseInt(totalD));

	    wg.setQS(qS);
	    wg.setQA(qA);
	    wg.setQB(qB);
	    wg.setQC(qC);
	    wg.setQD(qD);
	    wg.setCS(cS);
	    wg.setCA(cA);
	    wg.setCB(cB);
	    wg.setCC(cC);
	    wg.setCD(cD);
	    wg.setC2S(c2S);
	    wg.setC2A(c2A);
	    wg.setC2B(c2B);
	    wg.setC2C(c2C);
	    wg.setC2D(c2D);
	    wg.setDOneS(dOneS);
	    wg.setDOneA(dOneA);
	    wg.setDOneB(dOneB);
	    wg.setDOneC(dOneC);
	    wg.setDOneD(dOneD);
	    wg.setDTwoS(dTwoS);
	    wg.setDTwoA(dTwoA);
	    wg.setDTwoB(dTwoB);
	    wg.setDTwoC(dTwoC);
	    wg.setDTwoD(dTwoD);
	    wg.setDThreeS(dThreeS);
	    wg.setDThreeA(dThreeA);
	    wg.setDThreeB(dThreeB);
	    wg.setDThreeC(dThreeC);
	    wg.setDThreeD(dThreeD);
	    wg.setTS(tS);
	    wg.setTA(tA);
	    wg.setTB(tB);
	    wg.setTC(tC);
	    wg.setTD(tD);
	    wg.setKeyNo(keyNo);

	    wg = (Weights) PersistenceHelper.manager.save(wg);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	    trx.rollback();
	    return null;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return wg;
    }

    public static Performance savePerformanceAction(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("savePerformanceAction", PerformanceHelper.class.getName(), null, argTypes,
		        args);

	    } catch (RemoteException e) {
		Kogger.error(PerformanceHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(PerformanceHelper.class, e);
	    }
	    return (Performance) obj;
	}
	Performance pf = null;
	PerformType ptype = null;
	E3PSProject project = null;
	QueryResult qr = null;

	Transaction trx = new Transaction();

	String oid = StringUtil.checkNull((String) map.get("oid"));
	String pOid = StringUtil.checkNull((String) map.get("pOid"));
	String rankOne = StringUtil.checkNull((String) map.get("rankOne"));
	String rankTwo = StringUtil.checkNull((String) map.get("rankTwo"));

	// 필수 수행 완료 여부
	String isBom = StringUtil.checkNull((String) map.get("isBom"));
	String isPackage = StringUtil.checkNull((String) map.get("isPackage"));
	String isMass = StringUtil.checkNull((String) map.get("isMass"));
	String isAppraisal = StringUtil.checkNull((String) map.get("isAppraisal"));
	String isPrecedence = StringUtil.checkNull((String) map.get("isPrecedence"));
	String isEtc = StringUtil.checkNull((String) map.get("isEtc"));

	boolean checkPtype = false;
	boolean isBomOn = false;
	boolean isPackageOn = false;
	boolean isMassOn = false;
	boolean isAppraisalOn = false;
	boolean isPrecedenceOn = false;
	boolean isEtcOn = false;

	if (isBom.equals("on")) {
	    checkPtype = true;
	    isBomOn = true;
	}
	if (isPackage.equals("on")) {
	    checkPtype = true;
	    isPackageOn = true;
	}
	if (isMass.equals("on")) {
	    checkPtype = true;
	    isMassOn = true;
	}
	if (isAppraisal.equals("on")) {
	    checkPtype = true;
	    isAppraisalOn = true;
	}
	if (isPrecedence.equals("on")) {
	    checkPtype = true;
	    isPrecedenceOn = true;
	}
	if (isEtc.equals("on")) {
	    checkPtype = true;
	    isEtcOn = true;
	}

	// comment
	String descMsg = StringUtil.checkNull((String) map.get("descMsg"));

	// 목표
	String planQuality = StringUtil.checkNull((String) map.get("planQuality"));
	String planCost = StringUtil.checkNull((String) map.get("planCost"));
	String planDelivery1 = StringUtil.checkNull((String) map.get("planDelivery1"));
	String planTechnical = StringUtil.checkNull((String) map.get("planTechnical"));
	String planDelivery2 = StringUtil.checkNull((String) map.get("planDelivery2"));
	String planDelivery3 = StringUtil.checkNull((String) map.get("planDelivery3"));

	// 실적
	String resultQuality = "";
	String resultCost = "";
	String resultDelivery1 = "";
	String resultDelivery2 = "";
	String resultDelivery3 = "";
	String resultTechnical = "";

	// 점수
	String scoreQuality = "";
	String scoreCost = "";
	String scoreDelivery1 = "";
	String scoreDelivery2 = "";
	String scoreDelivery3 = "";
	String scoreTechnical = "";

	// 평가
	String evaluateQuality = "";
	String evaluateCost = "";
	String evaluateDelivery1 = "";
	String evaluateDelivery2 = "";
	String evaluateDelivery3 = "";
	String evaluateTechnical = "";
	ReferenceFactory rf = new ReferenceFactory();
	try {
	    trx.start();
	    if (oid.length() > 0) {
		project = (E3PSProject) rf.getReference(oid).getObject();

		if (project.getLifeCycleState().toString().equals("COMPLETED") && false) {
		    // 실적 점수 평가 Setting

		    resultQuality = "97";
		    resultCost = "100";
		    resultDelivery1 = "2011-02-27";
		    resultDelivery2 = "2011-02-27";
		    resultDelivery3 = "2011-02-27";
		    resultTechnical = "90";

		    scoreQuality = "20";
		    scoreCost = "16";
		    scoreDelivery1 = "20";
		    scoreDelivery2 = "20";
		    scoreDelivery3 = "20";
		    scoreTechnical = "18";

		    evaluateQuality = "S";
		    evaluateCost = "B";
		    evaluateDelivery1 = "S";
		    evaluateDelivery2 = "S";
		    evaluateDelivery3 = "S";
		    evaluateTechnical = "A";
		}
	    }

	    if (pOid.length() > 0) {
		pf = (Performance) rf.getReference(pOid).getObject();
	    } else {
		pf = Performance.newPerformance();
		// 폴더 , 기본 결재 LC
		String folderPath = ConfigImpl.getInstance().getString("folder.project");
		Folder folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
		FolderHelper.assignLocation((FolderEntry) pf, folder);

		LifeCycleTemplate lc = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", WCUtil.getWTContainerRef());
		LifeCycleHelper.setLifeCycle(pf, lc);
	    }
	    if (printMsg) {
		Kogger.debug(PerformanceHelper.class, "프로젝트 타입 ===>" + project.getPjtType());
	    }
	    // duplicate

	    if (printMsg) {
		Kogger.debug(PerformanceHelper.class, "rankOne ===>" + rankOne);
		Kogger.debug(PerformanceHelper.class, "성과관리 삭제 후 프로젝트 상태 완료로 변경시 rankOne의 값이 지금 없음..");
	    }

	    if (project != null && pOid.length() == 0) {
		pf.setKeyNo(project.getPjtNo());

		int keyInt = 0;
		boolean ncname = false;
		if (rankOne.length() > 0) {
		    if (project.getPjtType() == 0) {
			ncname = false;
		    } else if (project.getPjtType() == 1) {
			ncname = true;
		    } else if (project.getPjtType() == 2) {
			ncname = true;
		    } else if (project.getPjtType() == 3) {
			ncname = true;
		    } else if (project.getPjtType() == 4) {
			ncname = false;
		    }

		    if (rankOne.equals("Connector")) {
			if (ncname) {
			    keyInt = 1;
			} else {
			    keyInt = 4;
			}
		    } else if (rankOne.equals("Module")) {
			if (ncname) {
			    keyInt = 2;
			} else {
			    keyInt = 5;
			}
		    }
		} else {
		    keyInt = 3;
		}
		qr = searchWeights(keyInt, false, null);
		Object[] obj = null;
		Weights wg = null;
		Weights newwg = null;
		if (qr.hasMoreElements()) {
		    obj = (Object[]) qr.nextElement();
		    wg = (Weights) obj[0];
		}
		if (wg != null) {

		    newwg = PerformanceHelper.manager.duplicate(wg);
		    newwg.setIsProject(true);
		    newwg.setKeyNo(project.getPjtNo());
		    PersistenceHelper.manager.save(newwg);

		} else {
		    throw new Exception("평가 기준 정보가 없습니다.");
		}

	    }
	    if (project == null) {
		throw new Exception("관련 프로젝트 존재가 하지 않습니다.");
	    }
	    if (rankOne.length() > 0 && rankTwo.length() > 0) {
		pf.setRankOne(rankOne);
		pf.setRankTwo(rankTwo);

	    }

	    pf.setDescMsg(descMsg);

	    // 목표
	    pf.setPlanQuality(planQuality);
	    pf.setPlanCost(planCost);
	    pf.setPlanDelivery1(planDelivery1);
	    pf.setPlanDelivery2(planDelivery2);
	    pf.setPlanDelivery3(planDelivery3);
	    pf.setPlanTechnical(planTechnical);
	    if (!project.getLifeCycleState().toString().equals("COMPLETED")) {
		// 실적
		pf.setResultQuality(resultQuality);
		pf.setResultCost(resultCost);
		pf.setResultDelivery1(resultDelivery1);
		pf.setResultDelivery2(resultDelivery2);
		pf.setResultDelivery3(resultDelivery3);
		pf.setResultTechnical(resultTechnical);

		// 점수
		pf.setScoreQuality(scoreQuality);
		pf.setScoreCost(scoreCost);
		pf.setScoreDelivery1(scoreDelivery1);
		pf.setScoreDelivery2(scoreDelivery2);
		pf.setScoreDelivery3(scoreDelivery3);
		pf.setScoreTechnical(scoreTechnical);

		// 평가
		pf.setEvaluateQuality(evaluateQuality);
		pf.setEvaluateCost(evaluateCost);
		pf.setEvaluateDelivery1(evaluateDelivery1);
		pf.setEvaluateDelivery2(evaluateDelivery2);
		pf.setEvaluateDelivery3(evaluateDelivery3);
		pf.setEvaluateTechnical(evaluateTechnical);
	    }

	    pf = (Performance) PersistenceHelper.manager.save(pf);

	    if (checkPtype) {
		QueryResult ptQr = PerformanceHelper.manager.searchPerformType(CommonUtil.getOIDLongValue(pf));

		Object[] ptObj = null;
		if (ptQr.hasMoreElements()) {
		    ptObj = (Object[]) ptQr.nextElement();
		    ptype = (PerformType) ptObj[0];
		}

		if (ptype == null) {
		    ptype = new PerformType();
		}
		ptype.setIsBom(isBomOn);
		ptype.setIsPackage(isPackageOn);
		ptype.setIsMass(isMassOn);
		ptype.setIsAppraisal(isAppraisalOn);
		ptype.setIsPrecedence(isPrecedenceOn);
		ptype.setIsEtc(isEtcOn);
		ptype.setPerform(pf);
		ptype = (PerformType) PersistenceHelper.manager.save(ptype);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	    trx.rollback();
	    return null;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return pf;
    }

    public static QueryResult searchWeights(int type, boolean isProject, String projectNo) {

	QueryResult qr = null;
	QuerySpec spec = null;
	try {
	    spec = new QuerySpec();
	    int idx = spec.addClassList(Weights.class, true);
	    if (isProject) {
		spec.appendWhere(new SearchCondition(Weights.class, Weights.IS_PROJECT, SearchCondition.IS_TRUE, isProject),
		        new int[] { idx });
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(Weights.class, Weights.KEY_NO, SearchCondition.EQUAL, projectNo), new int[] { idx });
	    } else {
		spec.appendWhere(new SearchCondition(Weights.class, Weights.IS_PROJECT, SearchCondition.IS_FALSE, isProject),
		        new int[] { idx });
	    }
	    if (type != 0) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(Weights.class, "wType", "=", type), new int[] { idx });
	    }

	    /*
	     * OrderBy
	     * 
	     * ClassAttribute classattribute = new ClassAttribute(Weights.class, Weights.W_TYPE); classattribute.setColumnAlias("wtsort" +
	     * String.valueOf(0));
	     * 
	     * qs.appendSelect(classattribute, new int[]{idx}, false); OrderBy orderby = new OrderBy(classattribute, false, null);
	     * qs.appendOrderBy(orderby, new int[] {idx});
	     */
	    qr = PersistenceHelper.manager.find(spec);

	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	}

	return qr;
    }

    public static QueryResult searchPerformance(String projectNo) {

	QueryResult qr = null;
	QuerySpec spec = null;
	try {
	    spec = new QuerySpec();
	    int idx = spec.addClassList(Performance.class, true);
	    spec.appendWhere(new SearchCondition(Performance.class, Performance.KEY_NO, SearchCondition.EQUAL, projectNo),
		    new int[] { idx });
	    qr = PersistenceHelper.manager.find(spec);

	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	}

	return qr;
    }

    public static QueryResult searchPerformType(long id) {

	QueryResult qr = null;
	QuerySpec spec = null;
	try {
	    spec = new QuerySpec();
	    int idx = spec.addClassList(PerformType.class, true);
	    spec.appendWhere(new SearchCondition(PerformType.class, "performReference.key.id", SearchCondition.EQUAL, id),
		    new int[] { idx });
	    qr = PersistenceHelper.manager.find(spec);

	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	}

	return qr;
    }

    public Weights duplicate(Weights wg) throws WTException {
	Weights obj;
	try {
	    ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
	    ObjectOutputStream objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);
	    objectoutputstream.writeObject(wg);
	    objectoutputstream.flush();
	    ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(bytearrayoutputstream.toByteArray());
	    ObjectInputStream objectinputstream = new ObjectInputStream(bytearrayinputstream);
	    obj = (Weights) objectinputstream.readObject();
	    objectoutputstream.close();
	    objectinputstream.close();
	    if (obj instanceof Persistable) {
		PersistenceHelper.makeNonPersistent(obj);
	    }
	} catch (Exception exception) {
	    Object aobj[] = {};
	    throw new WTException(exception, "Object Copy Error", "OCE", aobj);
	}
	return obj;
    }

    public static String deleteAction(String oid) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = { String.class };
	    Object args[] = new Object[] { oid };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("deleteAction", PerformanceHelper.class.getName(), null, argTypes, args);

	    } catch (RemoteException e) {
		Kogger.error(PerformanceHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(PerformanceHelper.class, e);
	    }
	    return (String) obj;
	}
	String msg = "S";
	Performance pf = null;
	QueryResult wgQr = null;
	Weights wg = null;
	if (oid.length() > 0) {
	    pf = (Performance) CommonUtil.getObject(oid);
	    wgQr = PerformanceHelper.manager.searchWeights(0, true, pf.getKeyNo());
	    Object[] obj = null;
	    if (wgQr.hasMoreElements()) {
		obj = (Object[]) wgQr.nextElement();
		wg = (Weights) obj[0];
	    }
	}
	try {
	    PersistenceHelper.manager.delete(wg);
	    PersistenceHelper.manager.delete(pf);
	    return msg;
	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	    return null;
	} finally {

	}
    }

    public static Performance planPerformanceMoldProjectSave(TemplateProject project, int planCost) throws Exception {

	Performance pf = null;
	E3PSTask et1 = null;
	E3PSTask et2 = null;
	E3PSTask et3 = null;
	E3PSTaskData ed1 = null;
	E3PSTaskData ed2 = null;
	E3PSTaskData ed3 = null;
	// 목표
	// String planCost = "";
	String planDelivery1 = "";
	String planDelivery2 = "";
	String planDelivery3 = "";

	// 목표 Setting
	// Cost

	// Delivery1
	et1 = MoldProjectHelper.getTask((E3PSProject) project, "금형제작");
	if (et1 != null) {
	    ed1 = new E3PSTaskData(et1);
	    planDelivery1 = DateUtil.getDateString(ed1.taskPlanEndDate, "D");
	}

	// Delivery2
	et2 = MoldProjectHelper.getTask((E3PSProject) project, "제품합격");
	if (et2 != null) {
	    ed2 = new E3PSTaskData(et2);
	    planDelivery2 = DateUtil.getDateString(ed2.taskPlanEndDate, "D");
	}

	// Delivery3
	et3 = MoldProjectHelper.getTask((E3PSProject) project, "금형양산이관");
	if (et3 != null) {
	    ed3 = new E3PSTaskData(et3);
	    planDelivery3 = DateUtil.getDateString(ed3.taskPlanEndDate, "D");
	}

	HashMap map = new HashMap();
	map.put("oid", CommonUtil.getOIDString(project));
	map.put("planCost", "" + planCost);
	map.put("planDelivery1", planDelivery1);
	map.put("planDelivery2", planDelivery2);
	map.put("planDelivery3", planDelivery3);
	map.put("isEtc", "on");
	pf = (Performance) PerformanceHelper.manager.savePerformanceAction(map);

	return pf = (Performance) LifeCycleHelper.service.setLifeCycleState(pf, State.toState("APPROVED"), true);

    }

    public static HashMap getPlanResultCost(TemplateProject project) throws Exception {
	int targetCost1 = 0;
	int targetCost2 = 0;
	int targetCost3 = 0;
	int budget1 = 0;
	int budget2 = 0;
	int budget3 = 0;
	int resultsCost1 = 0;
	int resultsCost2 = 0;
	int resultsCost3 = 0;
	int balanceCost1 = 0;
	int balanceCost2 = 0;
	int balanceCost3 = 0;
	HashMap map = new HashMap();
	QuerySpec specCost = new QuerySpec();
	int idx_Cost = specCost.addClassList(CostInfo.class, true);
	SearchCondition scCost = new SearchCondition(CostInfo.class, "projectReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(project));
	specCost.appendWhere(scCost, new int[] { idx_Cost });

	QueryResult rtCost = PersistenceHelper.manager.find(specCost);
	while (rtCost.hasMoreElements()) {
	    Object[] obj = (Object[]) rtCost.nextElement();
	    CostInfo costInfo = (CostInfo) obj[0];

	    int budget = 0; // 예산
	    int executionCost = 0; // 초기집행가
	    int editCost = 0; // 추가집행가
	    int totalExpense = 0; // 총집행가
	    int balanceCost = 0; // 잔액

	    if (costInfo.getOrderInvest() != null) {
		Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		Integer totalPriceObj = (Integer) datas.get(ProductPrice.TOTALPRICE);
		Integer initExpenseObj = (Integer) datas.get(ProductPrice.INITEXPENSE);
		Integer addExpenseObj = (Integer) datas.get(ProductPrice.ADDEXPENSE);
		Integer totalExpenseObj = (Integer) datas.get(ProductPrice.TOTALEXPENSE);

		if (totalPriceObj != null)
		    budget = totalPriceObj.intValue(); // 예산
		if (initExpenseObj != null)
		    executionCost = initExpenseObj.intValue(); // 초기집행가
		if (addExpenseObj != null)
		    editCost = addExpenseObj.intValue(); // 추가집행가
		if (totalExpenseObj != null)
		    totalExpense = totalExpenseObj.intValue(); // 총집행가
		balanceCost = budget - totalExpense; // 잔액
	    } else {
		if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    budget = Integer.parseInt(costInfo.getTargetCost()); // 예산

		if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
		    executionCost = Integer.parseInt(costInfo.getExecutionCost()); // 초기집행가
		else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    executionCost = Integer.parseInt(costInfo.getTargetCost()); // 초기집행가

		if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
		    editCost = Integer.parseInt(costInfo.getEditCost()); // 추가집행가

		totalExpense = executionCost + editCost; // 총집행가
		balanceCost = 0; // 잔액
	    }

	    if ("금형".equals(costInfo.getCostType())) {
		if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    targetCost1 = targetCost1 + Integer.parseInt(costInfo.getTargetCost());
		budget1 = budget1 + budget;
		resultsCost1 = resultsCost1 + executionCost + editCost;
		balanceCost1 = balanceCost1 + balanceCost;
	    } else if ("설비".equals(costInfo.getCostType())) {
		if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    targetCost2 = targetCost2 + Integer.parseInt(costInfo.getTargetCost());
		budget2 = budget2 + budget;
		resultsCost2 = resultsCost2 + executionCost + editCost;
		balanceCost2 = balanceCost2 + balanceCost;
	    } else {
		if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    targetCost3 = targetCost3 + Integer.parseInt(costInfo.getTargetCost());
		budget3 = budget3 + budget;
		resultsCost3 = resultsCost3 + executionCost + editCost;
		balanceCost3 = balanceCost3 + balanceCost;
	    }
	}
	map.put("plan", budget1 + budget2 + budget3);
	map.put("result", resultsCost1 + resultsCost2 + resultsCost3);

	return map;
    }

    public static HashMap getMoldPlanResultCost(MoldProject moldProject) throws Exception {
	HashMap map = new HashMap();
	MoldItemInfo moldInfo = null;
	moldInfo = moldProject.getMoldInfo();
	CostInfo costInfo = moldInfo.getCostInfo();
	String orderNumber = null;
	Vector priceV = new Vector();
	Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);
	if (costInfo != null) {
	    orderNumber = costInfo.getOrderInvest();
	}
	if (orderNumber != null && orderNumber.length() > 0) {
	    Hashtable hash = ProductPrice.getPrice(orderNumber);
	    Integer integer = (Integer) hash.get(ProductPrice.TOTALPRICE);
	    if (integer != null) {
		map.put("plan", integer.intValue());
	    }

	    priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
	    int initMold = 0;
	    if (priceV.size() > 0) {
		Hashtable hhh = (Hashtable) priceV.get(0);
		integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);
		if (integer != null) {
		    initMold = integer.intValue();
		}
	    }
	    int debugingtotal = 0;
	    for (int i = 1; i < priceV.size(); i++) {
		Hashtable hhh = (Hashtable) priceV.get(i);
		integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		if (integer != null) {
		    debugingtotal += integer.intValue();
		}
	    }
	    map.put("result", initMold + debugingtotal);

	}

	return map;
    }

    public static String eventPerformance(TemplateProject project) throws Exception {
	Weights wg = null;
	QueryResult wgQr = null;
	Performance pf = null;
	HashMap map = null;
	boolean isMold = false;
	boolean isProduct = false;
	ProductProject productProject = null;
	MoldItemInfo moldInfo = null;
	MoldProject moldProject = null;
	if (project instanceof MoldProject) {
	    isMold = true;
	    moldProject = (MoldProject) project;
	    moldInfo = moldProject.getMoldInfo();
	    productProject = moldInfo.getProject();
	    map = getMoldPlanResultCost(moldProject);
	} else if (project instanceof ProductProject) {
	    isProduct = true;
	    productProject = (ProductProject) project;
	    map = getPlanResultCost(project);
	} else {
	    map = getPlanResultCost(project);
	}
	// Cost 예산
	int totalPlanValue = ((Integer) map.get("plan") == null) ? 0 : (Integer) map.get("plan");
	// Cost 실적
	int totalResultValue = ((Integer) map.get("result") == null) ? 0 : (Integer) map.get("result");

	// 프로젝트 성과관리 객체 가져오기
	QueryResult qrtest = PerformanceHelper.manager.searchPerformance(project.getPjtNo());
	Object[] pobj = null;
	if (qrtest.hasMoreElements()) {
	    pobj = (Object[]) qrtest.nextElement();
	    pf = (Performance) pobj[0];
	}

	// 성과관리가 승인완료 상태가 아니면 Return
	Kogger.debug(PerformanceHelper.class, "pf is Not Null ??  " + pf);
	if (pf != null) {
	    if (!pf.getState().toString().equals("APPROVED")) {
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "LifeCycleServiceEvent.SET_STATE : Performance No Equals APPROVED Return :"
			    + pf.getState().toString());
		}
		return "F 성과관리 목표가 승인 완료 상태 아닙니다.";
	    }
	}

	/**
	 * 제품 프로젝트 (Pilot), 전략개발, 수주개발 성과관리함, 연구개발, 추가금형 성과관리 안함 금형 프로젝트 (Pilot), 전략개발, 수주개발, 추가금형 성과관리함, 연구개발 성과관리 안함
	 */

	// 금형프로젝트이면서 pliot 프로젝트이여야 하며, 전략개발과 수주개발, 추가금형만 성과관리이고 성과관리 객체가 없을때
	boolean isMoldPerform = false; // 성과관리 할지 말지 체크..
	if (isMold && pf == null) { // 금형프로젝트이면서 성과관리가 안되었을 경우
	    Kogger.debug(PerformanceHelper.class, "금형 프로젝트 성과관리 시작!");
	    if (productProject.getProcess().getName().equals("Pilot")) { // Pilot 프로젝트 일경우
		if (productProject.getDevelopentType().getName().equals("전략개발")
		        || productProject.getDevelopentType().getName().equals("수주개발")
		        || productProject.getDevelopentType().getName().equals("추가금형")) { // 전략개발 프로젝트이거나 수주개발 프로젝트일 경우
		    isMoldPerform = true;
		}
	    }
	    if (isMoldPerform) {
		pf = planPerformanceMoldProjectSave(project, totalPlanValue);
	    } else {
		return "금형(Proto)프로젝트 및 금형프로젝트의 연구개발 프로젝트는 성과관리를 하지 않습니다.";
	    }
	    Kogger.debug(PerformanceHelper.class, "금형 프로젝트 성과관리 끝!");
	}

	// 제품프로젝트이면서 pliot 프로젝트이여야 하며, 전략개발과 수주개발 프로젝트만 성과관리이고 성과관리 객제가 없을때

	boolean isProductPerform = false;
	if (isProduct && pf == null) { // 제품프로젝트이면서 성과관리가 안되었을경우
	    Kogger.debug(PerformanceHelper.class, "제품 프로젝트 성과관리 시작!");
	    if (productProject.getProcess().getName().equals("Pilot")) { // Pilot 프로젝트일 경우
		if (productProject.getDevelopentType().getName().equals("전략개발")
		        || productProject.getDevelopentType().getName().equals("수주개발")) {
		    isProductPerform = true;
		}
	    }
	    if (isProductPerform) {
		pf = planPerformanceMoldProjectSave(project, totalPlanValue);
	    } else {
		return "제품(Proto)프로젝트 및 제품프로젝트의 연구개발, 추가금형 프로젝트는 성과관리를 하지 않습니다.";
	    }
	    Kogger.debug(PerformanceHelper.class, "제품 프로젝트 성과관리 끝!");
	}

	/*
	 * if(isMold && pf == null){ if(productProject.getProcess() != null){ if(productProject.getProcess().getName().equals("Pilot")){
	 * isPilotCheck = true; } } if(isPilotCheck){ pf = planPerformanceMoldProjectSave(project, totalPlanValue); }else{ return
	 * "Proto(금형 시작)프로젝트는 성과관리를 하지 않음"; //return "F Proto 프로젝트는  성과관리를 하지 않음"; } }
	 */

	// 평가 기준 객체 가져오기
	wgQr = PerformanceHelper.manager.searchWeights(0, true, pf.getKeyNo());
	Object[] wobj = null;
	if (wgQr.hasMoreElements()) {
	    wobj = (Object[]) wgQr.nextElement();
	    wg = (Weights) wobj[0];
	}

	// 성과 기준표 객체가 없으면 Return
	if (wg != null) {
	    if (printMsg) {
		Kogger.debug(PerformanceHelper.class, "Project Weights :" + wg.isIsProject());
	    }
	} else {
	    return "F 성과관리 평가 기준이 없습니다.";
	}

	// 실적
	String resultQuality = "0";
	String resultCost = "0";
	String resultDelivery1 = "";
	String resultDelivery2 = "";
	String resultDelivery3 = "";
	String resultTechnical = "0";

	// 점수
	String scoreQuality = "";
	String scoreCost = "";
	String scoreDelivery1 = "";
	String scoreDelivery2 = "";
	String scoreDelivery3 = "";
	String scoreTechnical = "";

	// 평가
	String evaluateQuality = "";
	String evaluateCost = "";
	String evaluateDelivery1 = "";
	String evaluateDelivery2 = "";
	String evaluateDelivery3 = "";
	String evaluateTechnical = "";

	try {

	    // 자동차 사업부 커텍터 / 자동차 사업부 모듈
	    if (wg.getWType() == 1 || wg.getWType() == 2) {

		// Quality
		E3PSTask et6 = MoldProjectHelper.getTask((E3PSProject) project, "DR6");
		if (et6 != null) {
		    e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(et6);
		    // 실적 Quality
		    if (drDocument != null) {
			resultQuality = "" + drDocument.getDRCheckPoint();
		    }
		}
		int wg_q = wg.getQuality();
		int rs_q = Integer.parseInt(resultQuality);
		if (wg_q != 0 && Integer.parseInt(resultQuality) > 0 && wg.getQS() != null && wg.getQA() != null && wg.getQB() != null
		        && wg.getQC() != null && wg.getQD() != null) {

		    if (Integer.parseInt(wg.getQS()) <= rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 100);
			evaluateQuality = "S";
		    } else if (Integer.parseInt(wg.getQA()) <= rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 90);
			evaluateQuality = "A";
		    } else if (Integer.parseInt(wg.getQB()) <= rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 80);
			evaluateQuality = "B";
		    } else if (Integer.parseInt(wg.getQC()) <= rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 70);
			evaluateQuality = "C";
		    } else if (Integer.parseInt(wg.getQD()) > rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 60);
			evaluateQuality = "D";
		    }

		} else {
		    scoreQuality = "" + Math.round((wg_q / 100.0) * 60);
		    evaluateQuality = "D";
		}
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "scoreQuality:evaluateQuality  ==>" + scoreQuality + ":" + evaluateQuality);
		}

		// 실적 Cost
		resultCost = "" + totalResultValue;

		double totalCost = 0;
		// resultCost=""+25000000;
		if (pf.getPlanCost() != null && !resultCost.equals("0")) {

		    int planCostInt = Integer.parseInt(pf.getPlanCost());
		    // planCostInt = 25000000;
		    int resultCostInt = Integer.parseInt(resultCost);
		    double costValue = planCostInt - resultCostInt;

		    if (resultCost.equals("0")) {
			totalCost = 0;
		    } else {
			totalCost = (costValue / planCostInt) * 100.0;
		    }

		    if (printMsg) {
			Kogger.debug(PerformanceHelper.class, "costValue/planCostInt *100 == totalCost :::" + costValue + "," + planCostInt + "," + totalCost);
		    }
		}

		int wg_c = wg.getCost();
		if (wg_c != 0) {
		    if (Integer.parseInt(wg.getCS()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 100);
			evaluateCost = "S";
		    } else if (Integer.parseInt(wg.getCA()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 90);
			evaluateCost = "A";
		    } else if (Integer.parseInt(wg.getCB()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 80);
			evaluateCost = "B";
		    } else if (Integer.parseInt(wg.getCC()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 70);
			evaluateCost = "C";
		    } else if (Integer.parseInt(wg.getCD()) > totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 60);
			evaluateCost = "D";
		    }
		} else {
		    scoreCost = "" + wg_c;
		    evaluateCost = "S";
		}
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "scoreCost:evaluateCost  ==>" + scoreCost + ":" + evaluateCost);
		}

		// Delivery
		ExtendScheduleData es = (ExtendScheduleData) project.getPjtSchedule().getObject();
		// 개발 시작일 (계획 기준)
		String planStartDate = DateUtil.getDateString(es.getExecStartDate(), "D");
		// 개발 완료일 (계획 기준)
		String devEndDate = DateUtil.getDateString(es.getExecEndDate(), "D");
		// 목표일
		String planDeliveryEndDate = "" + pf.getPlanDelivery1();

		int dura1 = 0;
		if (!("".equals(planDeliveryEndDate)) && !("".equals(planStartDate))) {
		    dura1 = DateUtil.getDaysFromTo(planDeliveryEndDate, planStartDate);
		}
		int dura2 = 0;
		if (!("".equals(devEndDate)) && !("".equals(planStartDate))) {
		    dura2 = DateUtil.getDaysFromTo(devEndDate, planStartDate);
		}
		int resultDeliveryInt = dura2 - dura1;

		// 실적 Delivery
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "dura2 - dura1 =" + dura2 + "-" + dura1);
		    Kogger.debug(PerformanceHelper.class, "resultDelivery1=>" + devEndDate + "    일 :" + resultDeliveryInt);
		}
		resultDelivery1 = devEndDate;
		int wg_d1 = wg.getDeliveryOne();
		if (wg_d1 != 0) {
		    if (Integer.parseInt(wg.getDOneS()) >= resultDeliveryInt) {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 100);
			evaluateDelivery1 = "S";
		    } else if (Integer.parseInt(wg.getDOneA()) >= resultDeliveryInt) {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 90);
			evaluateDelivery1 = "A";
		    } else if (Integer.parseInt(wg.getDOneB()) >= resultDeliveryInt) {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 80);
			evaluateDelivery1 = "B";
		    } else if (Integer.parseInt(wg.getDOneC()) >= resultDeliveryInt) {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 70);
			evaluateDelivery1 = "C";
		    } else if (Integer.parseInt(wg.getDOneD()) <= resultDeliveryInt) {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 60);
			evaluateDelivery1 = "D";
		    }
		} else {
		    scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 60);
		    evaluateDelivery1 = "D";
		}
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "scoreDelivery1:evaluateDelivery1  ==>" + scoreDelivery1 + ":" + evaluateDelivery1);
		}
		// Technical
		E3PSTask ett1 = MoldProjectHelper.getTask((E3PSProject) project, "DR1");
		E3PSTask ett2 = MoldProjectHelper.getTask((E3PSProject) project, "DR2");
		E3PSTask ett4 = MoldProjectHelper.getTask((E3PSProject) project, "DR4");
		int technicalInt = 0;
		int technicalCount = 0;
		if (ett1 != null) {
		    e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(ett1);
		    if (drDocument != null) {
			technicalInt = technicalInt + drDocument.getDRCheckPoint();
			technicalCount++;
		    }
		}
		if (ett2 != null) {
		    e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(ett2);
		    if (drDocument != null) {
			technicalInt = technicalInt + drDocument.getDRCheckPoint();
			technicalCount++;
		    }
		}
		if (ett4 != null) {
		    e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(ett4);
		    if (drDocument != null) {
			technicalInt = technicalInt + drDocument.getDRCheckPoint();
			technicalCount++;
		    }
		}
		// 실적 Technical
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "resultTechnical technicalInt/technicalCount=>" + technicalInt + "/" + technicalCount);
		}
		if (technicalInt != 0 && technicalCount != 0) {
		    resultTechnical = "" + technicalInt / technicalCount;
		}
		int wg_t = wg.getTechnical();
		int rs_t = Integer.parseInt(resultTechnical);
		if (wg_t != 0 && Integer.parseInt(resultTechnical) > 0) {
		    if (Integer.parseInt(wg.getTS()) <= rs_t) {
			scoreTechnical = "" + Math.round((wg_t / 100.0) * 100);
			evaluateTechnical = "S";
		    } else if (Integer.parseInt(wg.getTA()) <= rs_t) {
			scoreTechnical = "" + Math.round((wg_t / 100.0) * 90);
			evaluateTechnical = "A";
		    } else if (Integer.parseInt(wg.getTB()) <= rs_t) {
			scoreTechnical = "" + Math.round((wg_t / 100.0) * 80);
			evaluateTechnical = "B";
		    } else if (Integer.parseInt(wg.getTC()) <= rs_t) {
			scoreTechnical = "" + Math.round((wg_t / 100.0) * 70);
			evaluateTechnical = "C";
		    } else if (Integer.parseInt(wg.getTD()) > rs_t) {
			scoreTechnical = "" + Math.round((wg_t / 100.0) * 60);
			evaluateTechnical = "D";
		    }
		} else {
		    scoreTechnical = "" + Math.round((wg_t / 100.0) * 60);
		    evaluateTechnical = "D";
		}
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "scoreTechnical:evaluateTechnical  ==>" + scoreTechnical + ":" + evaluateTechnical);
		}

	    }

	    E3PSTask et1 = null;
	    E3PSTask et2 = null;
	    E3PSTask et3 = null;
	    E3PSTaskData ed1 = null;
	    E3PSTaskData ed2 = null;
	    E3PSTaskData ed3 = null;

	    // 금형
	    if (wg.getWType() == 3) {
		// 실적
		resultCost = "" + totalResultValue;
		double totalCost = 0;
		if (pf.getPlanCost() != null) {

		    int planCostInt = Integer.parseInt(pf.getPlanCost());
		    int resultCostInt = Integer.parseInt(resultCost);
		    double costValue = planCostInt - resultCostInt;

		    if (resultCost.equals("0")) {
			totalCost = 0;
		    } else {
			totalCost = (costValue / planCostInt) * 100.0;
		    }

		    if (printMsg) {
			Kogger.debug(PerformanceHelper.class, "##################################################################################");
			Kogger.debug(PerformanceHelper.class, "금형 totalCost, planCostInt=>" + totalCost + "," + planCostInt);
		    }
		}
		int wg_c = wg.getCost();
		if (wg_c != 0) {
		    if (Integer.parseInt(wg.getCS()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 100);
			evaluateCost = "S";
		    } else if (Integer.parseInt(wg.getCA()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 90);
			evaluateCost = "A";
		    } else if (Integer.parseInt(wg.getCB()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 80);
			evaluateCost = "B";
		    } else if (Integer.parseInt(wg.getCC()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 70);
			evaluateCost = "C";
		    } else if (Integer.parseInt(wg.getCD()) > totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 60);
			evaluateCost = "D";
		    }
		} else {
		    scoreCost = "" + Math.round((wg_c / 100.0) * 60);
		    evaluateCost = "D";
		}
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "금형 scoreCost:evaluateCost  ==>" + scoreCost + ":" + evaluateCost);
		}

		// Delivery1
		et1 = MoldProjectHelper.getTask((E3PSProject) project, "금형제작");
		if (et1 != null) {
		    ed1 = new E3PSTaskData(et1);
		}
		int wg_d1 = wg.getDeliveryOne();
		if (wg_d1 != 0 && ed1 != null) {
		    if (ed1.taskExecEndDate != null) {
			resultDelivery1 = DateUtil.getDateString(ed1.taskExecEndDate, "D");
			int dura1 = DateUtil.getDaysFromTo(DateUtil.getDateString(ed1.taskPlanEndDate, "D"),
			        DateUtil.getDateString(ed1.taskPlanStartDate, "D"));
			int dura2 = DateUtil.getDaysFromTo(DateUtil.getDateString(ed1.taskExecEndDate, "D"),
			        DateUtil.getDateString(ed1.taskExecStartDate, "D"));
			int resultDeliveryInt = dura2 - dura1;

			if (Integer.parseInt(wg.getDOneS()) >= resultDeliveryInt) {
			    scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 100);
			    evaluateDelivery1 = "S";
			} else if (Integer.parseInt(wg.getDOneA()) >= resultDeliveryInt) {
			    scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 90);
			    evaluateDelivery1 = "A";
			} else if (Integer.parseInt(wg.getDOneB()) >= resultDeliveryInt) {
			    scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 80);
			    evaluateDelivery1 = "B";
			} else if (Integer.parseInt(wg.getDOneC()) >= resultDeliveryInt) {
			    scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 70);
			    evaluateDelivery1 = "C";
			} else if (Integer.parseInt(wg.getDOneD()) < resultDeliveryInt) {
			    scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 60);
			    evaluateDelivery1 = "D";
			}
		    } else {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 60);
			evaluateDelivery1 = "D";
		    }
		    if (printMsg) {
			Kogger.debug(PerformanceHelper.class, "##################################################################################");
			Kogger.debug(PerformanceHelper.class, "금형  scoreDelivery1:evaluateDelivery1  ==>" + scoreDelivery1 + ":" + evaluateDelivery1);
		    }
		}

		// Delivery2
		et2 = MoldProjectHelper.getTask((E3PSProject) project, "제품합격");
		if (et2 != null) {
		    ed2 = new E3PSTaskData(et2);
		}
		int wg_d2 = wg.getDeliveryTwo();
		if (wg_d2 != 0) {
		    if (ed2 == null) {
			scoreDelivery2 = "" + wg_d2;
			evaluateDelivery2 = "S";
		    } else {
			if (ed2.taskExecEndDate != null) {
			    resultDelivery2 = DateUtil.getDateString(ed2.taskExecEndDate, "D");
			    int dura1 = DateUtil.getDaysFromTo(DateUtil.getDateString(ed2.taskPlanEndDate, "D"),
				    DateUtil.getDateString(ed2.taskPlanStartDate, "D"));

			    int dura2 = DateUtil.getDaysFromTo(DateUtil.getDateString(ed2.taskExecEndDate, "D"),
				    DateUtil.getDateString(ed2.taskExecStartDate, "D"));

			    int resultDeliveryInt = dura2 - dura1;
			    if (Integer.parseInt(wg.getDTwoS()) >= resultDeliveryInt) {
				scoreDelivery2 = "" + Math.round((wg_d2 / 100.0) * 100);
				evaluateDelivery2 = "S";
			    } else if (Integer.parseInt(wg.getDTwoA()) >= resultDeliveryInt) {
				scoreDelivery2 = "" + Math.round((wg_d2 / 100.0) * 90);
				evaluateDelivery2 = "A";
			    } else if (Integer.parseInt(wg.getDTwoB()) >= resultDeliveryInt) {
				scoreDelivery2 = "" + Math.round((wg_d2 / 100.0) * 80);
				evaluateDelivery2 = "B";
			    } else if (Integer.parseInt(wg.getDTwoC()) >= resultDeliveryInt) {
				scoreDelivery2 = "" + Math.round((wg_d2 / 100.0) * 70);
				evaluateDelivery2 = "C";
			    } else if (Integer.parseInt(wg.getDTwoD()) < resultDeliveryInt) {
				scoreDelivery2 = "" + Math.round((wg_d2 / 100.0) * 60);
				evaluateDelivery2 = "D";
			    }

			}
		    }
		}
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "금형  scoreDelivery2:evaluateDelivery2  ==>" + scoreDelivery2 + ":" + evaluateDelivery2);
		}

		// Delivery3
		et3 = MoldProjectHelper.getTask((E3PSProject) project, "금형양산이관");
		if (et3 != null) {
		    ed3 = new E3PSTaskData(et3);
		}
		int wg_d3 = wg.getDeliveryThree();
		if (wg_d3 != 0 && ed3 != null) {
		    if (ed3.taskExecEndDate != null) {
			resultDelivery3 = DateUtil.getDateString(ed3.taskExecEndDate, "D");
			int dura1 = DateUtil.getDaysFromTo(DateUtil.getDateString(ed3.taskPlanEndDate, "D"),
			        DateUtil.getDateString(ed3.taskPlanStartDate, "D"));
			int dura2 = DateUtil.getDaysFromTo(DateUtil.getDateString(ed3.taskExecEndDate, "D"),
			        DateUtil.getDateString(ed3.taskExecStartDate, "D"));
			int resultDeliveryInt = dura2 - dura1;

			if (Integer.parseInt(wg.getDThreeS()) >= resultDeliveryInt) {
			    scoreDelivery3 = "" + Math.round((wg_d3 / 100.0) * 100);
			    evaluateDelivery3 = "S";
			} else if (Integer.parseInt(wg.getDThreeA()) >= resultDeliveryInt) {
			    scoreDelivery3 = "" + Math.round((wg_d3 / 100.0) * 90);
			    evaluateDelivery3 = "A";
			} else if (Integer.parseInt(wg.getDThreeB()) >= resultDeliveryInt) {
			    scoreDelivery3 = "" + Math.round((wg_d3 / 100.0) * 80);
			    evaluateDelivery3 = "B";
			} else if (Integer.parseInt(wg.getDThreeC()) >= resultDeliveryInt) {
			    scoreDelivery3 = "" + Math.round((wg_d3 / 100.0) * 70);
			    evaluateDelivery3 = "C";
			} else if (Integer.parseInt(wg.getDThreeD()) < resultDeliveryInt) {
			    scoreDelivery3 = "" + Math.round((wg_d3 / 100.0) * 60);
			    evaluateDelivery3 = "D";
			}
		    } else {
			scoreDelivery3 = "" + Math.round((wg_d3 / 100.0) * 60);
			evaluateDelivery3 = "D";
		    }

		} else {
		    scoreDelivery3 = "" + Math.round((wg_d3 / 100.0) * 60);
		    evaluateDelivery3 = "D";
		}

		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "금형  scoreDelivery3:evaluateDelivery3  ==>" + scoreDelivery3 + ":" + evaluateDelivery3);
		}
	    }

	    // 전자 사업부 커텍터 / 전자 사업부 모듈
	    if (wg.getWType() == 4 || wg.getWType() == 5) {

		// Quality
		E3PSTask ett3 = MoldProjectHelper.getTask((E3PSProject) project, "DR6");

		if (ett3 == null) {
		    ett3 = MoldProjectHelper.getTask((E3PSProject) project, "DR3");
		}

		if (ett3 != null) {

		    e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(ett3);
		    // 실적 Quality
		    if (drDocument != null) {
			if (printMsg) {
			    Kogger.debug(PerformanceHelper.class, "##################################################################################");
			    Kogger.debug(PerformanceHelper.class, "전자 resultQuality=>" + drDocument.getDRCheckPoint());
			}
			resultQuality = "" + drDocument.getDRCheckPoint();
		    }
		}
		int wg_q = wg.getQuality();
		int rs_q = Integer.parseInt(resultQuality);
		if (wg_q != 0 && Integer.parseInt(resultQuality) > 0) {
		    if (Integer.parseInt(wg.getQS()) <= rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 100);
			evaluateQuality = "S";
		    } else if (Integer.parseInt(wg.getQA()) <= rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 90);
			evaluateQuality = "A";
		    } else if (Integer.parseInt(wg.getQB()) <= rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 80);
			evaluateQuality = "B";
		    } else if (Integer.parseInt(wg.getQC()) <= rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 70);
			evaluateQuality = "C";
		    } else if (Integer.parseInt(wg.getQD()) > rs_q) {
			scoreQuality = "" + Math.round((wg_q / 100.0) * 60);
			evaluateQuality = "D";
		    }
		} else {
		    scoreQuality = "" + Math.round((wg_q / 100.0) * 60);
		    evaluateQuality = "D";
		}
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "전자 scoreQuality:evaluateQuality  ==>" + scoreQuality + ":" + evaluateQuality);
		}
		// 실적 Cost
		resultCost = "" + totalResultValue;

		double totalCost = 0;
		if (pf.getPlanCost() != null && !resultCost.equals("0")) {

		    int planCostInt = Integer.parseInt(pf.getPlanCost());
		    int resultCostInt = Integer.parseInt(resultCost);
		    double costValue = planCostInt - resultCostInt;
		    totalCost = (costValue / planCostInt) * 100.0;

		    if (printMsg) {
			Kogger.debug(PerformanceHelper.class, "##################################################################################");
			Kogger.debug(PerformanceHelper.class, "전자 totalCost, planCostInt=>" + totalCost + "," + planCostInt);
		    }
		}

		int wg_c = wg.getCost();
		if (wg_c != 0 && !resultCost.equals("0")) {
		    if (Integer.parseInt(wg.getCS()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 100);
			evaluateCost = "S";
		    } else if (Integer.parseInt(wg.getCA()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 90);
			evaluateCost = "A";
		    } else if (Integer.parseInt(wg.getCB()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 80);
			evaluateCost = "B";
		    } else if (Integer.parseInt(wg.getCC()) <= totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 70);
			evaluateCost = "C";
		    } else if (Integer.parseInt(wg.getCD()) > totalCost) {
			scoreCost = "" + Math.round((wg_c / 100.0) * 60);
			evaluateCost = "D";
		    }
		} else {
		    scoreCost = "" + Math.round((wg_c / 100.0) * 60);
		    evaluateCost = "D";
		}
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "전자 scoreCost:evaluateCost  ==>" + scoreCost + ":" + evaluateCost);
		}
		// Delivery
		ExtendScheduleData es = (ExtendScheduleData) project.getPjtSchedule().getObject();
		// 개발 시작일 (계획 기준)
		String planStartDate = DateUtil.getDateString(es.getExecStartDate(), "D");
		// 개발 완료일 (계획 기준)
		String devEndDate = DateUtil.getDateString(es.getExecEndDate(), "D");
		// 목표일
		String planDeliveryEndDate = "" + pf.getPlanDelivery1();

		int dura1 = 0;
		if (!("".equals(planDeliveryEndDate)) && !("".equals(planStartDate))) {
		    dura1 = DateUtil.getDaysFromTo(planDeliveryEndDate, planStartDate);
		}
		int dura2 = 0;
		if (!("".equals(devEndDate)) && !("".equals(planStartDate))) {
		    dura2 = DateUtil.getDaysFromTo(devEndDate, planStartDate);
		}

		int resultDeliveryInt = dura2 - dura1;

		// 실적 Delivery
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "전자 resultDelivery1=>" + devEndDate + "    일 :" + resultDeliveryInt);
		}
		resultDelivery1 = devEndDate;
		int wg_d1 = wg.getDeliveryOne();
		Kogger.debug(PerformanceHelper.class, "============" + wg_d1);
		Kogger.debug(PerformanceHelper.class, "============" + wg.getDeliveryOne());
		Kogger.debug(PerformanceHelper.class, "S=" + wg.getDOneS());
		Kogger.debug(PerformanceHelper.class, "A=" + wg.getDOneA());
		Kogger.debug(PerformanceHelper.class, "B=" + wg.getDOneB());
		Kogger.debug(PerformanceHelper.class, "C=" + wg.getDOneC());
		Kogger.debug(PerformanceHelper.class, "D=" + wg.getDOneD());

		if (wg_d1 != 0) {
		    if (Integer.parseInt(wg.getDOneS()) >= resultDeliveryInt) {

			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 100);
			evaluateDelivery1 = "S";
		    } else if (Integer.parseInt(wg.getDOneA()) >= resultDeliveryInt) {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 90);
			evaluateDelivery1 = "A";
		    } else if (Integer.parseInt(wg.getDOneB()) >= resultDeliveryInt) {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 80);
			evaluateDelivery1 = "B";
		    } else if (Integer.parseInt(wg.getDOneC()) >= resultDeliveryInt) {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 70);
			evaluateDelivery1 = "C";
		    } else if (Integer.parseInt(wg.getDOneD()) < resultDeliveryInt) {
			scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 60);
			evaluateDelivery1 = "D";
		    }
		} else {
		    scoreDelivery1 = "" + Math.round((wg_d1 / 100.0) * 60);
		    evaluateDelivery1 = "D";
		}
		if (printMsg) {
		    Kogger.debug(PerformanceHelper.class, "##################################################################################");
		    Kogger.debug(PerformanceHelper.class, "전자 scoreDelivery1:evaluateDelivery1  ==>" + scoreDelivery1 + ":" + evaluateDelivery1);
		}

	    }

	    int totalScore = 0;
	    String totalEvaluate = "";
	    if (wg != null) {
		if (wg.getWType() == 1 || wg.getWType() == 2) {
		    if (pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null
			    && pf.getScoreTechnical() != null && pf.getScoreQuality().length() > 0 && pf.getScoreCost().length() > 0
			    && pf.getScoreDelivery1().length() > 0 && pf.getScoreTechnical().length() > 0) {
			totalScore = Integer.parseInt(pf.getScoreQuality()) + Integer.parseInt(pf.getScoreCost())
			        + Integer.parseInt(pf.getScoreDelivery1()) + Integer.parseInt(pf.getScoreTechnical());
		    }
		}
		if (wg.getWType() == 3) {
		    if (pf.getScoreCost() != null && pf.getScoreDelivery1() != null && pf.getScoreDelivery2() != null
			    && pf.getScoreDelivery3() != null && pf.getScoreCost().length() > 0 && pf.getScoreDelivery1().length() > 0
			    && pf.getScoreDelivery2().length() > 0 && pf.getScoreDelivery3().length() > 0) {
			totalScore = Integer.parseInt(pf.getScoreCost()) + Integer.parseInt(pf.getScoreDelivery1())
			        + Integer.parseInt(pf.getScoreDelivery2()) + Integer.parseInt(pf.getScoreDelivery3());
		    }
		}
		if (wg.getWType() == 4 || wg.getWType() == 5) {
		    if (pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null
			    && pf.getScoreQuality().length() > 0 && pf.getScoreCost().length() > 0 && pf.getScoreDelivery1().length() > 0) {
			totalScore = Integer.parseInt(pf.getScoreQuality()) + Integer.parseInt(pf.getScoreCost())
			        + Integer.parseInt(pf.getScoreDelivery1());
		    }
		}
		if (totalScore != 0) {
		    if (wg.getTotalS() <= totalScore) {
			totalEvaluate = "S";
		    } else if (wg.getTotalA() <= totalScore) {
			totalEvaluate = "A";
		    } else if (wg.getTotalB() <= totalScore) {
			totalEvaluate = "B";
		    } else if (wg.getTotalC() <= totalScore) {
			totalEvaluate = "C";
		    } else {
			totalEvaluate = "D";
		    }
		}
	    }
	    pf = (Performance) PersistenceHelper.manager.refresh(pf);

	    // 실적
	    pf.setResultQuality(resultQuality);
	    pf.setResultCost(resultCost);
	    pf.setResultDelivery1(resultDelivery1);
	    pf.setResultDelivery2(resultDelivery2);
	    pf.setResultDelivery3(resultDelivery3);
	    pf.setResultTechnical(resultTechnical);

	    // 점수
	    pf.setScoreQuality(scoreQuality);
	    pf.setScoreCost(scoreCost);
	    pf.setScoreDelivery1(scoreDelivery1);
	    pf.setScoreDelivery2(scoreDelivery2);
	    pf.setScoreDelivery3(scoreDelivery3);
	    pf.setScoreTechnical(scoreTechnical);

	    // 평가
	    pf.setEvaluateQuality(evaluateQuality);
	    pf.setEvaluateCost(evaluateCost);
	    pf.setEvaluateDelivery1(evaluateDelivery1);
	    pf.setEvaluateDelivery2(evaluateDelivery2);
	    pf.setEvaluateDelivery3(evaluateDelivery3);
	    pf.setEvaluateTechnical(evaluateTechnical);

	    pf = (Performance) PersistenceHelper.manager.save(pf);
	    return "S 성과 관리가 완료 되었습니다.";
	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	    throw new WTException(e);
	}

    }

    public static QuerySpec searchPerformance(HashMap map) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("searchPerformance", PerformanceHelper.class.getName(), null, argTypes, args);

	    } catch (RemoteException e) {
		Kogger.error(PerformanceHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(PerformanceHelper.class, e);
	    }
	    return (QuerySpec) obj;
	}
	QuerySpec qs = null;
	try {
	    String radioValue = ParamUtil.getStrParameter((String) map.get("radioValue"));
	    String searchPjtNo = ParamUtil.getStrParameter((String) map.get("searchPjtNo"));
	    String searchPjtName = ParamUtil.getStrParameter((String) map.get("searchPjtName"));
	    String searchPart = ParamUtil.getStrParameter((String) map.get("searchPart"));
	    String searchPm = ParamUtil.getStrParameter((String) map.get("searchPm"));
	    String searchProductPm = ParamUtil.getStrParameter((String) map.get("searchProductPm"));

	    String searchRank = ParamUtil.getStrParameter((String) map.get("searchRank"));
	    String searchDEVELOPENTTYPE = ParamUtil.getStrParameter((String) map.get("searchDEVELOPENTTYPE"));
	    String planStartStartDate = ParamUtil.getStrParameter((String) map.get("planStartStartDate"));
	    String planStartEndDate = ParamUtil.getStrParameter((String) map.get("planStartEndDate"));
	    String planEndStartDate = ParamUtil.getStrParameter((String) map.get("planEndStartDate"));
	    String planEndEndDate = ParamUtil.getStrParameter((String) map.get("planEndEndDate"));

	    qs = new QuerySpec();
	    Class pf_class = Performance.class;
	    Class pj_class = null;
	    int pjtType = 2;
	    if (radioValue.equals("1")) {
		pj_class = ProductProject.class;
		pjtType = 2;
	    } else if (radioValue.equals("2")) {
		pj_class = MoldProject.class;
		pjtType = 3;
	    } else {
		pj_class = ProductProject.class;
		pjtType = 4;
	    }

	    int sortIdx = 0;
	    String sort = (String) map.get("sortKey");
	    String isDesc = (String) map.get("dsc");
	    // Kogger.debug(getClass(), "sort=>>" +sort + "    isDesc=>>"+ isDesc);

	    int idx = qs.addClassList(pf_class, true);
	    int idx_pjt = qs.addClassList(pj_class, false);

	    SearchCondition sc1 = new SearchCondition(new ClassAttribute(pf_class, Performance.KEY_NO), "=", new ClassAttribute(pj_class,
		    "master>pjtNo"));
	    qs.appendWhere(sc1, new int[] { idx, idx_pjt });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(pf_class, "state.state", SearchCondition.EQUAL, "APPROVED"), new int[] { idx });

	    if (pjtType == 3) {
		String searchProductPjtNo = ParamUtil.getStrParameter((String) map.get("searchProductPjtNo"));
		String searchProductPartName = ParamUtil.getStrParameter((String) map.get("searchProductPartName"));
		String devDeptOid = ParamUtil.getStrParameter((String) map.get("devDeptOid"));
		String searchDieNo = ParamUtil.getStrParameter((String) map.get("searchDieNo"));
		String searchProductPart = ParamUtil.getStrParameter((String) map.get("searchProductPart"));

		// 금형분류
		String moldProductType = ParamUtil.getStrParameter((String) map.get("moldProductType"));
		// 제작구분
		String making = ParamUtil.getStrParameter((String) map.get("making"));
		// 금형Rank
		String moldRank = ParamUtil.getStrParameter((String) map.get("moldRank"));
		// 제작처
		String outsourcing = ParamUtil.getStrParameter((String) map.get("outsourcing"));

		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(pj_class, "state.state", "=", "COMPLETED"), new int[] { idx_pjt });

		qs.appendAnd();
		qs.appendWhere(new SearchCondition(pj_class, "historyNote", SearchCondition.NOT_EQUAL, "Migration"), new int[] { idx_pjt });

		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(pj_class, "pjtType", "=", pjtType), new int[] { idx_pjt });

		int index_item = qs.addClassList(MoldItemInfo.class, false);
		int index_productProject = qs.addClassList(ProductProject.class, false);
		ClassAttribute ca0 = new ClassAttribute(MoldProject.class, "moldInfoReference.key.id");
		ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, wt.util.WTAttributeNameIfc.ID_NAME);
		ClassAttribute ca2 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
		ClassAttribute ca3 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);

		SearchCondition sc = new SearchCondition(ca0, "=", ca1);
		sc.setFromIndicies(new int[] { idx_pjt, index_item }, 0);
		sc.setOuterJoin(0);
		qs.appendAnd();
		qs.appendWhere(sc, new int[] { idx_pjt, index_item });

		sc = new SearchCondition(ca2, "=", ca3);
		sc.setFromIndicies(new int[] { index_item, index_productProject }, 0);
		sc.setOuterJoin(0);
		qs.appendAnd();
		qs.appendWhere(sc, new int[] { index_item, index_productProject });

		// searchDieNo
		if (searchDieNo != null && searchDieNo.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.DIE_NO);
		    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
		    searchDieNo = StringUtil.changeString(searchDieNo, "*", "%");
		    ColumnExpression ce = ConstantExpression.newExpression(searchDieNo);
		    SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce);
		    qs.appendWhere(dsc, new int[] { index_item });
		}
		if (devDeptOid != null && devDeptOid.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    long deptid = CommonUtil.getOIDLongValue(devDeptOid);
		    int linkIndex = qs.addClassList(ProjectViewDepartmentLink.class, false);
		    ClassAttribute mca1 = null;
		    ClassAttribute mca2 = null;
		    mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
		    mca2 = new ClassAttribute(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id");
		    SearchCondition msc = new SearchCondition(mca1, "=", mca2);
		    msc.setFromIndicies(new int[] { index_productProject, linkIndex }, 0);
		    msc.setOuterJoin(0);
		    qs.appendWhere(msc, new int[] { index_productProject, linkIndex });
		    qs.appendAnd();
		    qs.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id", "=", deptid),
			    new int[] { linkIndex });
		}
		// searchProductPjtNo
		if (searchProductPjtNo != null && searchProductPjtNo.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    ClassAttribute mca = new ClassAttribute(ProductProject.class, ProductProject.PJT_NO);
		    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
		    searchProductPjtNo = StringUtil.changeString(searchProductPjtNo, "*", "%");
		    ColumnExpression ce = ConstantExpression.newExpression(searchProductPjtNo);
		    SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce);
		    qs.appendWhere(dsc, new int[] { index_productProject });
		}
		// searchProductPartName
		if (searchProductPartName != null && searchProductPartName.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NAME);
		    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
		    searchProductPartName = StringUtil.changeString(searchProductPartName, "*", "%");
		    ColumnExpression ce = ConstantExpression.newExpression(searchProductPartName);
		    SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce);
		    qs.appendWhere(dsc, new int[] { index_item });
		}
		// searchProductPart
		if (searchProductPart != null && searchProductPart.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
		    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
		    searchProductPart = StringUtil.changeString(searchProductPart, "*", "%");
		    ColumnExpression ce = ConstantExpression.newExpression(searchProductPart);
		    SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce);
		    qs.appendWhere(dsc, new int[] { index_item });
		}
		// moldProductType
		if (moldProductType != null && moldProductType.length() > 0) {
		    setNumberCodeQuery(qs, MoldItemInfo.class, index_item, MoldItemInfo.PRODUCT_TYPE_REFERENCE + ".key.id",
			    "MOLDPRODUCTSTYPE", moldProductType);
		}
		// moldRank
		if (moldRank.length() > 0) {
		    SearchUtil.appendEQUAL(qs, pj_class, "rankReference.key.id",
			    CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("RANK", moldRank)), idx_pjt);
		}
		// DEVELOPENTTYPE
		if (searchDEVELOPENTTYPE.length() > 0) {
		    SearchUtil.appendEQUAL(qs, ProductProject.class, "developentTypeReference.key.id",
			    CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", searchDEVELOPENTTYPE)),
			    index_productProject);
		} else {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    long[] numberLong = { CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", "DEV001")),
			    CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", "DEV002")) };
		    SearchCondition sc4 = new SearchCondition(ProductProject.class, "developentTypeReference.key.id", numberLong, false);
		    Kogger.debug(PerformanceHelper.class, "numberLong ::: " + numberLong + "\n");

		    qs.appendWhere(sc4, new int[] { index_productProject });
		}

		// making
		if (making != null && making.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    qs.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.MAKING, "=", making), new int[] { index_item });
		}
		// outsourcing
		if (outsourcing != null && outsourcing.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    ClassAttribute mca = new ClassAttribute(MoldProject.class, MoldProject.OUT_SOURCING);
		    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
		    outsourcing = StringUtil.changeString(outsourcing, "*", "%");
		    ColumnExpression ce = ConstantExpression.newExpression(outsourcing);
		    SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce);
		    qs.appendWhere(dsc, new int[] { idx_pjt });
		}
		if (searchProductPm.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }

		    long pmUser = 0;
		    if (searchProductPm != null && searchProductPm.length() > 0) {
			pmUser = CommonUtil.getOIDLongValue(searchProductPm);
		    }

		    Class prjectMember = ProjectMemberLink.class;
		    int idx_Member = qs.appendClassList(prjectMember, false);

		    ClassAttribute mca1 = null;
		    ClassAttribute mca2 = null;

		    mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
		    mca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
		    SearchCondition msc = new SearchCondition(mca1, "=", mca2);
		    msc.setFromIndicies(new int[] { index_productProject, idx_Member }, 0);
		    msc.setOuterJoin(0);
		    qs.appendWhere(msc, new int[] { index_productProject, idx_Member });

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    sc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL,
			    ProjectUserHelper.manager.PM);
		    qs.appendWhere(sc, new int[] { idx_Member });

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    sc = new SearchCondition(prjectMember, "roleBObjectRef.key.id", SearchCondition.EQUAL, pmUser);
		    qs.appendWhere(sc, new int[] { idx_Member });
		}
		if (searchPjtNo.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }

		    ClassAttribute pca = new ClassAttribute(ProductProject.class, ProductProject.PJT_NO);
		    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, pca);
		    searchPjtNo = StringUtil.changeString(searchPjtNo, "*", "%");
		    ColumnExpression ce = ConstantExpression.newExpression(searchPjtNo);
		    SearchCondition scf = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce);
		    qs.appendWhere(scf, new int[] { index_productProject });
		}
		if ((sort != null) && (sort.trim().length() > 0)) {
		    if ((isDesc == null) || (isDesc.trim().length() == 0)) {
			isDesc = "FALSE";
		    }
		    if (sort.equals(ProductProject.PJT_NO)) {
			SearchUtil.setOrderBy(qs, ProductProject.class, index_productProject, sort, "m_Sort" + sortIdx++,
			        "TRUE".equals(isDesc.toUpperCase()));
		    }
		    if (sort.equals(MoldItemInfo.PART_NO) || sort.equals(MoldItemInfo.PART_NAME) || sort.equals(MoldItemInfo.DIE_NO)) {
			SearchUtil.setOrderBy(qs, MoldItemInfo.class, index_item, sort, "m_Sort" + sortIdx++,
			        "TRUE".equals(isDesc.toUpperCase()));
		    }
		    if (sort.equals("planEndDate") || sort.equals("planStartDate")) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			Class schedule2 = ExtendScheduleData.class;
			int idx_schedule2 = qs.appendClassList(schedule2, false);
			ClassAttribute pca1 = null;
			ClassAttribute pca2 = null;
			pca1 = new ClassAttribute(ProductProject.class, "pjtSchedule.key.id");
			pca2 = new ClassAttribute(schedule2, wt.util.WTAttributeNameIfc.ID_NAME);
			SearchCondition sc2 = new SearchCondition(pca1, "=", pca2);
			sc2.setFromIndicies(new int[] { idx_pjt, idx_schedule2 }, 0);
			sc2.setOuterJoin(0);
			qs.appendWhere(sc2, new int[] { idx_pjt, idx_schedule2 });
			SearchUtil
			        .setOrderBy(qs, schedule2, idx_schedule2, sort, "m_Sort" + sortIdx++, "TRUE".equals(isDesc.toUpperCase()));
		    }
		}
	    } else {
		Kogger.debug(PerformanceHelper.class, "\n join query \n");
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(pj_class, "state.state", "=", "COMPLETED"), new int[] { idx_pjt });

		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(pj_class, ProductProject.PJT_TYPE, "=", pjtType), new int[] { idx_pjt });

		if (searchPjtNo.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    ClassAttribute pca = new ClassAttribute(ProductProject.class, ProductProject.PJT_NO);
		    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, pca);
		    searchPjtNo = StringUtil.changeString(searchPjtNo, "*", "%");
		    ColumnExpression ce = ConstantExpression.newExpression(searchPjtNo);
		    SearchCondition scf = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce);
		    qs.appendWhere(scf, new int[] { idx_pjt });
		}
		if (searchPjtName.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    searchPjtName = StringUtil.changeString(searchPjtName, "*", "%");
		    qs.appendWhere(new SearchCondition(pj_class, ProductProject.PJT_NAME, SearchCondition.LIKE, searchPjtName),
			    new int[] { idx_pjt });
		}
		if (searchPart.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    searchPart = StringUtil.changeString(searchPart, "*", "%");
		    qs.appendWhere(new SearchCondition(pj_class, ProductProject.PART_NO, SearchCondition.LIKE, searchPart),
			    new int[] { idx_pjt });
		}

		if (searchRank.length() > 0) {
		    SearchUtil.appendEQUAL(qs, pj_class, "rankReference.key.id",
			    CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("RANK", searchRank)), idx_pjt);
		}

		if (searchDEVELOPENTTYPE.length() > 0) {
		    SearchUtil.appendEQUAL(qs, pj_class, "developentTypeReference.key.id",
			    CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", searchDEVELOPENTTYPE)),
			    idx_pjt);
		} else {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    long[] numberLong = { CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", "DEV001")),
			    CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", "DEV002")) };
		    SearchCondition sc4 = new SearchCondition(pj_class, "developentTypeReference.key.id", numberLong, false);
		    qs.appendWhere(sc4, new int[] { idx_pjt });
		}

		SearchUtil.appendEQUAL(qs, pj_class, "processReference.key.id",
		        CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("PROCESS", "PC002")), idx_pjt);

		if ((sort != null) && (sort.trim().length() > 0)) {
		    if ((isDesc == null) || (isDesc.trim().length() == 0)) {
			isDesc = "FALSE";
		    }
		    if (sort.equals(ProductProject.PJT_NAME) || sort.equals(ProductProject.PJT_NO) || sort.equals(ProductProject.PART_NO)) {
			SearchUtil.setOrderBy(qs, ProductProject.class, idx_pjt, sort, "m_Sort" + sortIdx++,
			        "TRUE".equals(isDesc.toUpperCase()));
		    }
		    if (sort.equals("planEndDate") || sort.equals("planStartDate")) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			Class schedule2 = ExtendScheduleData.class;
			int idx_schedule2 = qs.appendClassList(schedule2, false);
			ClassAttribute pca1 = null;
			ClassAttribute pca2 = null;
			pca1 = new ClassAttribute(ProductProject.class, "pjtSchedule.key.id");
			pca2 = new ClassAttribute(schedule2, wt.util.WTAttributeNameIfc.ID_NAME);
			SearchCondition sc2 = new SearchCondition(pca1, "=", pca2);
			sc2.setFromIndicies(new int[] { idx_pjt, idx_schedule2 }, 0);
			sc2.setOuterJoin(0);
			qs.appendWhere(sc2, new int[] { idx_pjt, idx_schedule2 });
			SearchUtil
			        .setOrderBy(qs, schedule2, idx_schedule2, sort, "m_Sort" + sortIdx++, "TRUE".equals(isDesc.toUpperCase()));
		    }
		}
	    }

	    if (searchPm.length() > 0) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		long pmUser = 0;
		if (searchPm != null && searchPm.length() > 0) {
		    pmUser = CommonUtil.getOIDLongValue(searchPm);
		}
		Class prjectMember = ProjectMemberLink.class;
		int idx_Member = qs.appendClassList(prjectMember, false);
		ClassAttribute ca1 = null;
		ClassAttribute ca2 = null;
		ca1 = new ClassAttribute(pj_class, wt.util.WTAttributeNameIfc.ID_NAME);
		ca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
		SearchCondition sc = new SearchCondition(ca1, "=", ca2);
		sc.setFromIndicies(new int[] { idx_pjt, idx_Member }, 0);
		sc.setOuterJoin(0);
		qs.appendWhere(sc, new int[] { idx_pjt, idx_Member });
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		sc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL,
		        ProjectUserHelper.manager.PM);
		qs.appendWhere(sc, new int[] { idx_Member });
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		sc = new SearchCondition(prjectMember, "roleBObjectRef.key.id", SearchCondition.EQUAL, pmUser);
		qs.appendWhere(sc, new int[] { idx_Member });
	    }

	    // 계획 시작일자(시작 ~ 끝), 계획 종료일자(시작 ~ 끝)
	    if ((planStartStartDate != null && planStartStartDate.length() > 0)
		    || (planStartEndDate != null && planStartEndDate.length() > 0)
		    || (planEndStartDate != null && planEndStartDate.length() > 0)
		    || (planEndEndDate != null && planEndEndDate.length() > 0)) {

		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		Class schedule = ExtendScheduleData.class;
		int idx_schedule = qs.appendClassList(schedule, false);
		ClassAttribute ca1 = null;
		ClassAttribute ca2 = null;
		ca1 = new ClassAttribute(pj_class, "pjtSchedule.key.id");
		ca2 = new ClassAttribute(schedule, wt.util.WTAttributeNameIfc.ID_NAME);
		SearchCondition sc = new SearchCondition(ca1, "=", ca2);
		sc.setFromIndicies(new int[] { idx_pjt, idx_schedule }, 0);
		sc.setOuterJoin(0);
		qs.appendWhere(sc, new int[] { idx_pjt, idx_schedule });
		if (planStartStartDate != null && planStartStartDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
			    new ParsePosition(0)).getTime()));
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
			    convertDate);
		    qs.appendWhere(sc, new int[] { idx_schedule });
		}
		if (planStartEndDate != null && planStartEndDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
			    planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN, convertDate);
		    qs.appendWhere(sc, new int[] { idx_schedule });
		}
		if (planEndStartDate != null && planEndStartDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate, new ParsePosition(0))
			    .getTime()));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
		    qs.appendWhere(sc, new int[] { idx_schedule });
		}
		if (planEndEndDate != null && planEndEndDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(planEndEndDate + ":23-59-59",
			    new ParsePosition(0)).getTime()));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN, convertDate);
		    qs.appendWhere(sc, new int[] { idx_schedule });
		}
	    }

	    // Kogger.debug(getClass(), ">>>>>>>>>>>>>>>>>> performance sq \n " + qs);
	    return qs;
	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	    return null;
	} finally {

	}
    }

    public static int searchPerformancePagingCount(HashMap<String, String> hashMap, List<Map<String, String>> conditionList,
	    TgPagingControl pager) throws Exception {

	PageControl pCon = searchPerformancePaging(hashMap, conditionList, pager);
	int totalCount = (pCon == null) ? 0 : pCon.getTotalCount();

	return totalCount;
    }

    public static PageControl searchPerformancePaging(HashMap<String, String> hashMap, List<Map<String, String>> conditionList,
	    TgPagingControl pager) throws Exception {
	QuerySpec qs = null;
	try {
	    qs = searchPerformance(hashMap, conditionList);

	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	}
	String sPage = String.valueOf(pager.getCurrentPageNo() + 1);
	if (sPage.isEmpty())
	    sPage = "1";
	String sPageRowCnt = String.valueOf(pager.getPageSize());
	if (sPageRowCnt.isEmpty())
	    sPageRowCnt = "10";

	int perPage = StringUtil.getIntParameter(sPageRowCnt);
	int formPage = 20;
	int page = StringUtil.getIntParameter(sPage, 0);

	// int performanceTotalCount = searchPerformancePagingCount(hashMap, conditionList, pager);
	// perPage = performanceTotalCount % perPage;
	PageControl pCon = CommonSearchHelper.find(new Integer(perPage), new Integer(formPage), qs);
	int totalCount = (pCon == null) ? 0 : pCon.getTotalCount();
	// Kogger.debug(getClass(), "perPage:" + perPage + ", formPage:" + formPage + ", sPage:" + sPage);
	if ((page * perPage) > totalCount)
	    perPage = totalCount % perPage;
	if (perPage == 0)
	    perPage = StringUtil.getIntParameter(sPageRowCnt);

	if (sPage.isEmpty() || "0".equals(sPage)) {
	    return CommonSearchHelper.find(new Integer(perPage), new Integer(formPage), qs);
	} else {
	    return CommonSearchHelper.find(new Integer(perPage), new Integer(formPage), qs, new Integer(sPage));
	    // return findRow(new Integer(perPage), new Integer(formPage), qs, page);
	}

    }

    public static PageControl findRow(Integer perPage, Integer formPage, QuerySpec spec, Integer page) {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Integer.class, Integer.class, QuerySpec.class, Integer.class };
	    Object args[] = new Object[] { perPage, formPage, spec, page };
	    PageControl obj = null;
	    try {
		obj = (PageControl) wt.method.RemoteMethodServer.getDefault().invoke("find", "e3ps.common.web.CommonSearchHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(PerformanceHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(PerformanceHelper.class, e);
	    }
	    return obj;
	}

	try {
	    String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	    PagingQueryResult paging = PagingSessionHelper.fetchPagingSession((page.intValue() - 1) * perPage.intValue(),
		    perPage.intValue(), Long.parseLong(pageSessionId));
	    return new PageControl(paging, page.intValue(), formPage.intValue(), perPage.intValue());
	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	    return null;
	}
    }

    public static QuerySpec searchPerformance(HashMap<String, String> hashMap, List<Map<String, String>> conditionList) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = { HashMap.class, List.class };
	    Object args[] = new Object[] { hashMap, conditionList };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("searchPerformance", PerformanceHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(PerformanceHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(PerformanceHelper.class, e);
	    }
	    return (QuerySpec) obj;
	}
	QuerySpec qs = null;
	try {

	    String radioValue = ParamUtil.getStrParameter((String) hashMap.get("radioValue"));

	    qs = new QuerySpec();
	    Class pf_class = Performance.class;
	    Class pj_class = null;
	    int pjtType = 2;
	    if (radioValue.equals("1")) {
		pj_class = ProductProject.class;
		pjtType = 2;
	    } else if (radioValue.equals("2")) {
		pj_class = MoldProject.class;
		pjtType = 3;
	    } else {
		pj_class = ProductProject.class;
		pjtType = 4;
	    }

	    int idx = qs.addClassList(pf_class, true);
	    int idx_pjt = qs.addClassList(pj_class, false);

	    SearchCondition sc1 = new SearchCondition(new ClassAttribute(pf_class, Performance.KEY_NO), "=", new ClassAttribute(pj_class,
		    "master>pjtNo"));
	    qs.appendWhere(sc1, new int[] { idx, idx_pjt });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(pf_class, "state.state", SearchCondition.EQUAL, "APPROVED"), new int[] { idx });

	    for (Map<String, String> map : conditionList) {

		String searchPjtNo = ParamUtil.getStrParameter((String) map.get("searchPjtNo"));
		String searchPjtName = ParamUtil.getStrParameter((String) map.get("searchPjtName"));
		String searchPart = ParamUtil.getStrParameter((String) map.get("searchPart"));
		String searchPm = ParamUtil.getStrParameter((String) map.get("searchPm"));
		String searchProductPm = ParamUtil.getStrParameter((String) map.get("searchProductPm"));

		String searchRank = ParamUtil.getStrParameter((String) map.get("searchRank"));
		String searchDEVELOPENTTYPE = ParamUtil.getStrParameter((String) map.get("searchDEVELOPENTTYPE"));
		String planStartStartDate = ParamUtil.getStrParameter((String) map.get("planStartStartDate"));
		String planStartEndDate = ParamUtil.getStrParameter((String) map.get("planStartEndDate"));
		String planEndStartDate = ParamUtil.getStrParameter((String) map.get("planEndStartDate"));
		String planEndEndDate = ParamUtil.getStrParameter((String) map.get("planEndEndDate"));

		if (pjtType == 3) {
		    String searchProductPjtNo = ParamUtil.getStrParameter((String) map.get("searchProductPjtNo"));
		    String searchProductPartName = ParamUtil.getStrParameter((String) map.get("searchProductPartName"));
		    String devDeptOid = ParamUtil.getStrParameter((String) map.get("devDeptOid"));
		    String searchDieNo = ParamUtil.getStrParameter((String) map.get("searchDieNo"));
		    String searchProductPart = ParamUtil.getStrParameter((String) map.get("searchProductPart"));

		    // 금형분류
		    String moldProductType = ParamUtil.getStrParameter((String) map.get("moldProductType"));
		    // 제작구분
		    String making = ParamUtil.getStrParameter((String) map.get("making"));
		    // 금형Rank
		    String moldRank = ParamUtil.getStrParameter((String) map.get("moldRank"));
		    // 제작처
		    String outsourcing = ParamUtil.getStrParameter((String) map.get("outsourcing"));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(pj_class, "state.state", "=", "COMPLETED"), new int[] { idx_pjt });

		    qs.appendAnd();
		    qs.appendWhere(new SearchCondition(pj_class, "historyNote", SearchCondition.NOT_EQUAL, "Migration"),
			    new int[] { idx_pjt });

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(pj_class, "pjtType", "=", pjtType), new int[] { idx_pjt });

		    int index_item = qs.addClassList(MoldItemInfo.class, false);
		    int index_productProject = qs.addClassList(ProductProject.class, false);
		    ClassAttribute ca0 = new ClassAttribute(MoldProject.class, "moldInfoReference.key.id");
		    ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, wt.util.WTAttributeNameIfc.ID_NAME);
		    ClassAttribute ca2 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
		    ClassAttribute ca3 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);

		    SearchCondition sc = new SearchCondition(ca0, "=", ca1);
		    sc.setFromIndicies(new int[] { idx_pjt, index_item }, 0);
		    sc.setOuterJoin(0);
		    qs.appendAnd();
		    qs.appendWhere(sc, new int[] { idx_pjt, index_item });

		    sc = new SearchCondition(ca2, "=", ca3);
		    sc.setFromIndicies(new int[] { index_item, index_productProject }, 0);
		    sc.setOuterJoin(0);
		    qs.appendAnd();
		    qs.appendWhere(sc, new int[] { index_item, index_productProject });

		    // searchDieNo
		    if (searchDieNo != null && searchDieNo.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			KETQueryUtil
			        .setQuerySpecForMultiSearch(qs, MoldItemInfo.class, index_item, MoldItemInfo.DIE_NO, searchDieNo, false);
		    }
		    if (devDeptOid != null && devDeptOid.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			int linkIndex = qs.addClassList(ProjectViewDepartmentLink.class, false);

			ClassAttribute mca1 = null;
			ClassAttribute mca2 = null;

			mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
			mca2 = new ClassAttribute(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id");
			SearchCondition msc = new SearchCondition(mca1, "=", mca2);
			msc.setFromIndicies(new int[] { index_productProject, linkIndex }, 0);
			msc.setOuterJoin(0);
			qs.appendWhere(msc, new int[] { index_productProject, linkIndex });
			qs.appendAnd();

			qs.appendOpenParen();
			StringTokenizer devDeptOidToken = new StringTokenizer(devDeptOid, ", ");
			while (devDeptOidToken.hasMoreTokens()) {
			    qs.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id",
				    SearchCondition.EQUAL, CommonUtil.getOIDLongValue(devDeptOidToken.nextToken())),
				    new int[] { linkIndex });
			    if (devDeptOidToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    }
		    // searchProductPjtNo
		    if (searchProductPjtNo != null && searchProductPjtNo.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			KETQueryUtil.setQuerySpecForMultiSearch(qs, ProductProject.class, index_productProject, ProductProject.PJT_NO,
			        searchProductPjtNo, true);
		    }
		    // searchProductPartName
		    if (searchProductPartName != null && searchProductPartName.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			KETQueryUtil.setQuerySpecForMultiSearch(qs, MoldItemInfo.class, index_item, MoldItemInfo.PART_NAME,
			        searchProductPartName, false);
		    }
		    // searchProductPart
		    if (searchProductPart != null && searchProductPart.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			KETQueryUtil.setQuerySpecForMultiSearch(qs, MoldItemInfo.class, index_item, MoldItemInfo.PART_NO,
			        searchProductPart, false);
		    }
		    // moldProductType
		    if (moldProductType != null && moldProductType.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			qs.appendOpenParen();
			StringTokenizer moldProductTypeToken = new StringTokenizer(moldProductType, ", ");
			while (moldProductTypeToken.hasMoreTokens()) {
			    qs.appendWhere(
				    new SearchCondition(MoldItemInfo.class, MoldItemInfo.PRODUCT_TYPE_REFERENCE + ".key.id",
				            SearchCondition.EQUAL, CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode(
				                    "MOLDPRODUCTSTYPE", moldProductTypeToken.nextToken()))), new int[] { index_item });
			    if (moldProductTypeToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    }
		    // moldRank
		    if (moldRank != null && moldRank.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			qs.appendOpenParen();
			StringTokenizer rankToken = new StringTokenizer(moldRank, ", ");
			while (rankToken.hasMoreTokens()) {
			    qs.appendWhere(
				    new SearchCondition(pj_class, "rankReference.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("RANK", rankToken.nextToken()))),
				    new int[] { idx_pjt });
			    if (rankToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    }
		    // DEVELOPENTTYPE
		    if (searchDEVELOPENTTYPE != null && searchDEVELOPENTTYPE.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			qs.appendOpenParen();
			StringTokenizer developTypeToken = new StringTokenizer(searchDEVELOPENTTYPE, ", ");
			while (developTypeToken.hasMoreTokens()) {
			    qs.appendWhere(
				    new SearchCondition(ProductProject.class, "developentTypeReference.key.id", SearchCondition.EQUAL,
				            CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE",
				                    developTypeToken.nextToken()))), new int[] { index_productProject });
			    if (developTypeToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    } else {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			String[] numberLong = {
			        Long.toString(CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", "DEV001"))),
			        Long.toString(CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", "DEV002"))) };
			KETQueryUtil.setQuerySpecForMultiSearch(qs, ProductProject.class, index_productProject,
			        "developentTypeReference.key.id", numberLong, false);
		    }

		    // making
		    if (making != null && making.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			qs.appendOpenParen();
			StringTokenizer makingToken = new StringTokenizer(making, ", ");
			while (makingToken.hasMoreTokens()) {
			    qs.appendWhere(
				    new SearchCondition(MoldItemInfo.class, MoldItemInfo.MAKING, SearchCondition.EQUAL, makingToken
				            .nextToken()), new int[] { index_item });
			    if (makingToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    }
		    // outsourcing
		    if (outsourcing != null && outsourcing.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			KETQueryUtil
			        .setQuerySpecForMultiSearch(qs, MoldProject.class, idx_pjt, MoldProject.OUT_SOURCING, outsourcing, true);
		    }
		    if (searchProductPm != null && searchProductPm.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			Class prjectMember = ProjectMemberLink.class;
			int idx_Member = qs.appendClassList(prjectMember, false);

			ClassAttribute mca1 = null;
			ClassAttribute mca2 = null;

			mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
			mca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
			SearchCondition msc = new SearchCondition(mca1, "=", mca2);
			msc.setFromIndicies(new int[] { index_productProject, idx_Member }, 0);
			msc.setOuterJoin(0);
			qs.appendWhere(msc, new int[] { index_productProject, idx_Member });

			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			sc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL,
			        ProjectUserHelper.manager.PM);
			qs.appendWhere(sc, new int[] { idx_Member });

			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			qs.appendOpenParen();
			StringTokenizer setProductPmToken = new StringTokenizer(searchProductPm, ", ");
			while (setProductPmToken.hasMoreTokens()) {
			    qs.appendWhere(
				    new SearchCondition(prjectMember, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(setProductPmToken.nextToken())), new int[] { idx_Member });
			    if (setProductPmToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    }
		    if (searchPjtNo.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			KETQueryUtil.setQuerySpecForMultiSearch(qs, ProductProject.class, index_productProject, ProductProject.PJT_NO,
			        searchPjtNo, true);
		    }
		} else {
		    Kogger.debug(PerformanceHelper.class, "\n join query \n");
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(pj_class, "state.state", "=", "COMPLETED"), new int[] { idx_pjt });

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(pj_class, ProductProject.PJT_TYPE, "=", pjtType), new int[] { idx_pjt });

		    if (searchPjtNo != null && searchPjtNo.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			KETQueryUtil
			        .setQuerySpecForMultiSearch(qs, ProductProject.class, idx_pjt, ProductProject.PJT_NO, searchPjtNo, true);
		    }
		    if (searchPjtName != null && searchPjtName.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			KETQueryUtil.setQuerySpecForMultiSearch(qs, pj_class, idx_pjt, ProductProject.PJT_NAME, searchPjtName, true);
		    }
		    if (searchPjtName != null && searchPart.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			KETQueryUtil.setQuerySpecForMultiSearch(qs, pj_class, idx_pjt, ProductProject.PART_NO, searchPart, true);
		    }

		    if (searchRank != null && searchRank.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			qs.appendOpenParen();
			StringTokenizer rankToken = new StringTokenizer(searchRank, ", ");
			while (rankToken.hasMoreTokens()) {
			    qs.appendWhere(
				    new SearchCondition(pj_class, "rankReference.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("RANK", rankToken.nextToken()))),
				    new int[] { idx_pjt });
			    if (rankToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    }

		    if (searchDEVELOPENTTYPE != null && searchDEVELOPENTTYPE.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			qs.appendOpenParen();
			StringTokenizer developTypeToken = new StringTokenizer(searchDEVELOPENTTYPE, ", ");
			while (developTypeToken.hasMoreTokens()) {
			    qs.appendWhere(
				    new SearchCondition(ProductProject.class, "developentTypeReference.key.id", SearchCondition.EQUAL,
				            CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE",
				                    developTypeToken.nextToken()))), new int[] { idx_pjt });
			    if (developTypeToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    } else {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			long[] numberLong = {
			        CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", "DEV001")),
			        CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", "DEV002")) };
			SearchCondition sc4 = new SearchCondition(pj_class, "developentTypeReference.key.id", numberLong, false);
			qs.appendWhere(sc4, new int[] { idx_pjt });
		    }

		    SearchUtil.appendEQUAL(qs, pj_class, "processReference.key.id",
			    CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("PROCESS", "PC002")), idx_pjt);
		}

		if (searchPm != null && searchPm.length() > 0) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    Class prjectMember = ProjectMemberLink.class;
		    int idx_Member = qs.appendClassList(prjectMember, false);

		    ClassAttribute ca1 = null;
		    ClassAttribute ca2 = null;

		    ca1 = new ClassAttribute(pj_class, wt.util.WTAttributeNameIfc.ID_NAME);
		    ca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
		    SearchCondition sc = new SearchCondition(ca1, "=", ca2);
		    sc.setFromIndicies(new int[] { idx_pjt, idx_Member }, 0);
		    sc.setOuterJoin(0);
		    qs.appendWhere(sc, new int[] { idx_pjt, idx_Member });

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    sc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PM);
		    qs.appendWhere(sc, new int[] { idx_Member });

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    qs.appendOpenParen();
		    StringTokenizer setProductPmToken = new StringTokenizer(searchProductPm, ", ");
		    while (setProductPmToken.hasMoreTokens()) {
			qs.appendWhere(
			        new SearchCondition(prjectMember, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
			                .getOIDLongValue(setProductPmToken.nextToken())), new int[] { idx_Member });
			if (setProductPmToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}

		// 계획 시작일자(시작 ~ 끝), 계획 종료일자(시작 ~ 끝)
		if ((planStartStartDate != null && planStartStartDate.length() > 0)
		        || (planStartEndDate != null && planStartEndDate.length() > 0)
		        || (planEndStartDate != null && planEndStartDate.length() > 0)
		        || (planEndEndDate != null && planEndEndDate.length() > 0)) {

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    Class schedule = ExtendScheduleData.class;
		    int idx_schedule = qs.appendClassList(schedule, false);
		    ClassAttribute ca1 = null;
		    ClassAttribute ca2 = null;
		    ca1 = new ClassAttribute(pj_class, "pjtSchedule.key.id");
		    ca2 = new ClassAttribute(schedule, wt.util.WTAttributeNameIfc.ID_NAME);
		    SearchCondition sc = new SearchCondition(ca1, "=", ca2);
		    sc.setFromIndicies(new int[] { idx_pjt, idx_schedule }, 0);
		    sc.setOuterJoin(0);
		    qs.appendWhere(sc, new int[] { idx_pjt, idx_schedule });
		    if (planStartStartDate != null && planStartStartDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
			        new ParsePosition(0)).getTime()));
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
			        convertDate);
			qs.appendWhere(sc, new int[] { idx_schedule });
		    }
		    if (planStartEndDate != null && planStartEndDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
			        planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN, convertDate);
			qs.appendWhere(sc, new int[] { idx_schedule });
		    }
		    if (planEndStartDate != null && planEndStartDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate,
			        new ParsePosition(0)).getTime()));
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
			        convertDate);
			qs.appendWhere(sc, new int[] { idx_schedule });
		    }
		    if (planEndEndDate != null && planEndEndDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
			        planEndEndDate + ":23-59-59", new ParsePosition(0)).getTime()));
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN, convertDate);
			qs.appendWhere(sc, new int[] { idx_schedule });
		    }
		}
	    }
	    // Kogger.debug(getClass(), qs.toString());
	    return qs;
	} catch (Exception e) {
	    Kogger.error(PerformanceHelper.class, e);
	    return null;
	} finally {

	}
    }

    private static void setNumberCodeQuery(QuerySpec spec, Class target, int idx, String ref, String key, String value) throws WTException {
	if ((value == null) || (value.trim().length() == 0)) {
	    return;
	}
	SearchUtil.appendEQUAL(spec, target, ref, CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode(key, value)), idx);
    }

    public static void excelPerformanceDown(String radioValue, OutputStream out) throws Exception {
	WritableWorkbook workbook = Workbook.createWorkbook(out);
	WritableSheet sheet = workbook.createSheet("성과관리", 1);
	excelPerformanceSetting(radioValue, sheet);
	workbook.write();
	workbook.close();
    }

    public static void excelPerformanceSetting(String radioValue, WritableSheet sheet) throws Exception {
	int row = 0;
	int columnIndex = 0;
	WritableCellFormat titleformat = JExcelUtil.getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);
	String titles[] = new String[] { "번호", "Project No" };
	for (int i = 0; i < titles.length; i++) {
	    sheet.addCell(new Label(i, row, titles[i], titleformat));

	}
	sheet.setColumnView(0, 25);
	sheet.addCell(new Label(columnIndex++, row, "11"));
	sheet.addCell(new Label(columnIndex++, row, "22"));
    }

    public static int ecogetMax(E3PSProject project) throws Exception {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    Class argTypes[] = new Class[] { E3PSProject.class };
	    Object args[] = new Object[] { project };
	    try {
		Object obj = RemoteMethodServer.getDefault().invoke("ecogetMax", PerformanceHelper.class.getName(), null, argTypes, args);
		return ((Integer) obj).intValue();

	    } catch (RemoteException e) {
		Kogger.error(PerformanceHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(PerformanceHelper.class, e);
		throw new WTException(e);
	    }
	}
	// KETProdChangeOrder proEco = null;
	// KETMoldChangeOrder moldEco = null;
	boolean isPro = true;
	if (project instanceof MoldProject) {
	    isPro = false;
	}
	int result = 0;
	Class ecoClass = null;
	if (isPro) {
	    ecoClass = KETProdChangeOrder.class;
	} else {
	    ecoClass = KETMoldChangeOrder.class;
	}
	/*
	 * ClassInfo classinfo = WTIntrospector.getClassInfo(ecoClass); String seqColumnName = null;
	 * 
	 * seqColumnName = DatabaseInfoUtilities.getValidColumnName( classinfo, "projectOid");
	 * 
	 * QuerySpec spec = new QuerySpec(); spec.setAdvancedQueryEnabled(true); spec.setDescendantQuery(false); spec.addClassList(ecoClass,
	 * false); KeywordExpression ke = new KeywordExpression("NVL(count(" + seqColumnName +"), 0) "); ke.setColumnAlias("projet_seq");
	 * spec.appendSelect(ke, new int[]{0}, false);
	 */
	QuerySpec spec = new QuerySpec();
	int index = spec.addClassList(ecoClass, true);
	// spec.appendSelect(new KeywordExpression("count(*)"), index, false);
	spec.appendWhere(new SearchCondition(ecoClass, "projectOid", "=", CommonUtil.getOIDString(project)), new int[] { index });
	// Kogger.debug(getClass(), spec);
	QueryResult qr = PersistenceHelper.manager.find(spec);

	result = qr.size();
	/*
	 * if(qr.hasMoreElements()){ Object o[] =(Object[])qr.nextElement(); BigDecimal number = (BigDecimal)o[0]; result =
	 * number.intValue(); }
	 */
	// Kogger.debug(getClass(), "result = " + result);
	return result;
    }

    public static void main(String[] args) throws Exception {
	/*
	 * Performance pf = null; HashMap map = new HashMap(); map.put("oid", "e3ps.project.MoldProject:94642"); map.put("pOid", "");
	 * 
	 * map.put("descMsg", "descMsg"); map.put("planCost", "2000"); map.put("planDelivery1", "2010/12/22"); map.put("planDelivery2",
	 * "2010/12/23"); map.put("planDelivery3", "2010/12/24"); map.put("keyNo", "");
	 * 
	 * map.put("isBom", "false"); map.put("isPackage", "false"); map.put("isMass", "false"); map.put("isAppraisal", "false");
	 * map.put("isPrecedence", "false"); map.put("isEtc", "on"); pf = (Performance)PerformanceHelper.manager.savePerformanceAction(map);
	 */
	E3PSProject project = (E3PSProject) CommonUtil.getObject("e3ps.project.ProductProject:101019");
	int count = PerformanceHelper.manager.ecogetMax(project);
	Kogger.debug(PerformanceHelper.class, "ecogetMax===>>>>>" + count);
    }
}
