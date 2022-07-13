package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.ColumnListExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.query.TableColumn;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.outputtype.ModelPlan;
import e3ps.project.outputtype.OEMPlan;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.project.program.entity.KETProgramEvent;
import ext.ket.project.program.entity.KETProgramEventLink;
import ext.ket.project.program.entity.KETProgramSchedule;
import ext.ket.project.program.entity.ProgramDTO;
import ext.ket.project.program.entity.ProgramEventDTO;
import ext.ket.project.program.entity.ProgramProjectDTO;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.shared.log.Kogger;

public class ProgramManagerHelper implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    public static final ProgramManagerHelper manager = new ProgramManagerHelper();

    protected ProgramManagerHelper() {
    }

    public static ModelPlan save(HashMap map) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("save", ProgramManagerHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProgramManagerHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProgramManagerHelper.class, e);
		throw new WTException(e);
	    }
	    return (ModelPlan) obj;
	}

	Transaction trx = new Transaction();
	ModelPlan mp = null;
	try {
	    trx.start();
	    Calendar tempCal = Calendar.getInstance();

	    String oid = (String) map.get("oid");
	    if (oid == null) {
		mp = ModelPlan.newModelPlan();
	    } else {
		mp = (ModelPlan) CommonUtil.getObject(oid);

		// String aa = deleteOemPlan(mp.getPersistInfo().getObjectIdentifier().getStringValue());

	    }
	    OEMProjectType cartype = null;
	    // 차종
	    ReferenceFactory rf = new ReferenceFactory();
	    String cartypeOid = (String) map.get("cartypeOid");
	    if (cartypeOid != null && cartypeOid.length() > 0) {
		cartype = (OEMProjectType) rf.getReference(cartypeOid).getObject();
		cartype.setIsSavePlan(true);
		PersistenceHelper.manager.save(cartype);

		mp.setCarType(cartype);

	    }

	    String modelname = (String) map.get("modelname");
	    mp.setModelName(modelname);

	    // 형태
	    String formtypeOid = (String) map.get("formtypeOid");
	    NumberCode formtype = null;
	    if (formtypeOid != null && formtypeOid.length() > 0) {
		formtype = (NumberCode) rf.getReference(formtypeOid).getObject();
		mp.setFormType(formtype);
	    }

	    /*
	     * Kogger.debug(getClass(), "######################   형태  ################"); Kogger.debug(getClass(), "##formtypeOid   	 : " +
	     * formtypeOid); Kogger.debug(getClass(), "###########################################");
	     */

	    // 자동차사 설계 담당
	    String person = (String) map.get("person");
	    mp.setPerson(person);

	    // 생산량
	    String yield1 = (String) map.get("yield1");
	    String yield2 = (String) map.get("yield2");
	    String yield3 = (String) map.get("yield3");
	    String yield4 = (String) map.get("yield4");
	    String yield5 = (String) map.get("yield5");
	    String yield6 = (String) map.get("yield6");
	    String yield7 = (String) map.get("yield7");
	    String yield8 = (String) map.get("yield8");
	    String yield9 = (String) map.get("yield9");
	    String yield10 = (String) map.get("yield10");

	    int y1 = Integer.parseInt(yield1 == null || "".equals(yield1) ? "0" : yield1);
	    int y2 = Integer.parseInt(yield2 == null || "".equals(yield2) ? "0" : yield2);
	    int y3 = Integer.parseInt(yield3 == null || "".equals(yield3) ? "0" : yield3);
	    int y4 = Integer.parseInt(yield4 == null || "".equals(yield4) ? "0" : yield4);
	    int y5 = Integer.parseInt(yield5 == null || "".equals(yield5) ? "0" : yield5);
	    int y6 = Integer.parseInt(yield6 == null || "".equals(yield6) ? "0" : yield6);
	    int y7 = Integer.parseInt(yield7 == null || "".equals(yield7) ? "0" : yield7);
	    int y8 = Integer.parseInt(yield8 == null || "".equals(yield8) ? "0" : yield8);
	    int y9 = Integer.parseInt(yield9 == null || "".equals(yield9) ? "0" : yield9);
	    int y10 = Integer.parseInt(yield10 == null || "".equals(yield10) ? "0" : yield10);
	    int sum = y1 + y2 + y3 + y4 + y5 + y6 + y7 + y8 + y9 + y10;

	    /*
	     * Kogger.debug(getClass(), "######################   생산량  ################"); Kogger.debug(getClass(), "##yield1   : " +
	     * yield1); Kogger.debug(getClass(), "##yield2   : " + yield2); Kogger.debug(getClass(), "##yield3   : " + yield3);
	     * Kogger.debug(getClass(), "##yield4   : " + yield4); Kogger.debug(getClass(), "##yield5   : " + yield5);
	     * Kogger.debug(getClass(), "##yield6   : " + yield6); Kogger.debug(getClass(), "##yield7   : " + yield7);
	     * Kogger.debug(getClass(), "##yield8   : " + yield8); Kogger.debug(getClass(), "##yield9   : " + yield9);
	     * Kogger.debug(getClass(), "##yield10   : " + yield10); Kogger.debug(getClass(), "##sum   : " + sum); Kogger.debug(getClass(),
	     * "###########################################");
	     */

	    mp.setYield1(yield1);
	    mp.setYield2(yield2);
	    mp.setYield3(yield3);
	    mp.setYield4(yield4);
	    mp.setYield5(yield5);
	    mp.setYield6(yield6);
	    mp.setYield7(yield7);
	    mp.setYield8(yield8);
	    mp.setYield9(yield9);
	    mp.setYield10(yield10);
	    mp.setTotal(sum);
	    mp.setProgramBaseNo((String) map.get("programBaseNo"));
	    mp = (ModelPlan) PersistenceHelper.manager.save(mp);

	    if (oid != null) {
		Kogger.debug(ProgramManagerHelper.class, "delete할 mp oid ---" + oid);
		// deleteOemPlan(oid);
	    }

	    Hashtable oemtypeOid_ht = new Hashtable();
	    Hashtable oemEndDate_ht = new Hashtable();
	    Hashtable opOid_ht = new Hashtable();

	    oemtypeOid_ht = (Hashtable) map.get("oemtypeOid");
	    oemEndDate_ht = (Hashtable) map.get("oemEndDate");
	    opOid_ht = (Hashtable) map.get("opOid");

	    for (int i = 0; i < oemtypeOid_ht.size(); i++) {
		String key0 = "oemtypeOid" + i;
		String key1 = "oemEndDate" + i;
		String key2 = "opOid" + i;
		/*
	         * Kogger.debug(getClass(), "######################### OEMPlan Start###############["+i+"]"); Kogger.debug(getClass(),
	         * "oemType ["+i+"]" +(String)oemtypeOid_ht.get(key0)); Kogger.debug(getClass(), "oemEndDate_ht ["+i+"]"
	         * +(String)oemEndDate_ht.get(key1)); Kogger.debug(getClass(),
	         * "######################### OEMPlan end##################["+i+"]");
	         */
		OEMProjectType oemtypeObj = null;
		oemtypeObj = (OEMProjectType) CommonUtil.getObject((String) oemtypeOid_ht.get(key0));

		String opOid = null;
		if (opOid_ht != null) {
		    opOid = (String) opOid_ht.get(key2);
		}

		OEMPlan op = null;
		// OEMPlan op = OEMPlan.newOEMPlan();
		if (oid == null) {
		    op = OEMPlan.newOEMPlan();
		} else {
		    op = getCalendar(oid, oemtypeObj.getName());
		}

		if (oemEndDate_ht.get(key1).toString().length() > 0) {
		    tempCal.setTime(DateUtil.parseDateStr((String) oemEndDate_ht.get(key1)));
		    Timestamp ts = new Timestamp(tempCal.getTime().getTime());
		    op.setOemEndDate(ts);
		} else {
		    op.setOemEndDate(null);
		}

		op.setViewType(oemtypeObj.getOLevel());
		op.setOemType(oemtypeObj);
		op.setModelPlan(mp);

		op = (OEMPlan) PersistenceHelper.manager.save(op);

	    }
	    mp = (ModelPlan) PersistenceHelper.manager.save(mp);

	    // 해당 차종의 프로그램을 찾아 일정 동기화 Start 2016.12.07 by 황정태
	    OEMProjectType carType = mp.getCarType();
	    String CarTypeoid = CommonUtil.getOIDString(carType);
	    QuerySpec qs = getProgramByCarType(CarTypeoid);

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    ProgramDTO paramObject = null;
	    KETProgramSchedule pgSchedule = null;
	    ProgramEventDTO oldEventDto = null;
	    ProgramEventDTO modifyDto = null;
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		pgSchedule = (KETProgramSchedule) objArr[0];
		paramObject = new ProgramDTO(pgSchedule);
		paramObject.setProgramName(pgSchedule.getProgramName());
		paramObject.setProgramAdmin(CommonUtil.getOIDString(pgSchedule.getProgramAdmin()));
		paramObject.setLastUsingBuyer(CommonUtil.getOIDString(pgSchedule.getLastUsingBuyer()));
		paramObject.setSubContractor(CommonUtil.getOIDString(pgSchedule.getSubContractor()));

		List<ProgramEventDTO> newEventlist = ProgramHelper.service.findEventByCarType(CarTypeoid);

		List<ProgramEventDTO> sourceEvents = ProgramHelper.service.findEventByProgram(CommonUtil.getOIDString(pgSchedule));

		for (ProgramEventDTO eventdto : sourceEvents) {// PM이 추가로 입력한 고객이벤트 정보는 보존한다
		    KETProgramEventLink eventLink = (KETProgramEventLink) CommonUtil.getObject(eventdto.getOid());
		    KETProgramEvent event = eventLink.getEvent();

		    if (event.getOemPlan() == null) {
			oldEventDto = new ProgramEventDTO();
			oldEventDto.setCustomerEventDate(DateUtil.getDateString(event.getCustomerEventDate(), "date"));
			oldEventDto.setCustomerEventName(event.getCustomerEventName());
			newEventlist.add(oldEventDto);
		    } else {// 접점고객의 이벤트일정과 차종의 이벤트 일정이 상이할 경우 접점고객의 이벤트 일정을 보존한다
			String oemDate = DateUtil.getDateString(event.getOemPlan().getOemEndDate(), "date");
			String customerDate = DateUtil.getDateString(event.getCustomerEventDate(), "date");

			String oemName = event.getOemPlan().getOemType().getName();
			String customerName = event.getCustomerEventName();

			if (!oemDate.equals(customerDate) || !oemName.equals(customerName)) {

			    for (int i = 0; i < newEventlist.size(); i++) {
				ProgramEventDTO newEventdto = (ProgramEventDTO) newEventlist.get(i);

				if (CommonUtil.getOIDString(event.getOemPlan()).equals(newEventdto.getCarEventOid())) {
				    newEventdto.setCustomerEventDate(customerDate);
				    newEventdto.setCustomerEventName(customerName);
				    newEventlist.set(i, newEventdto);
				}

			    }
			}
		    }
		}

		Collections.sort(newEventlist, new Comparator<ProgramEventDTO>() {

		    @Override
		    public int compare(ProgramEventDTO o1, ProgramEventDTO o2) {
			// TODO Auto-generated method stub
			int a = 0;
			int b = 0;

			if (StringUtils.isEmpty(o1.getCarEventDate())) {
			    a = Integer.parseInt(StringUtils.remove(o1.getCustomerEventDate(), "-"));
			} else {
			    a = Integer.parseInt(StringUtils.remove(o1.getCarEventDate(), "-"));
			}

			if (StringUtils.isEmpty(o2.getCarEventDate())) {
			    b = Integer.parseInt(StringUtils.remove(o2.getCustomerEventDate(), "-"));
			} else {
			    b = Integer.parseInt(StringUtils.remove(o2.getCarEventDate(), "-"));
			}

			return (a < b) ? -1 : (a > b) ? 1 : 0;
		    }
		});

		paramObject.setEvents(newEventlist);
		List<ProgramProjectDTO> list = ProgramHelper.service.findProjectByProgram(CommonUtil.getOIDString(pgSchedule));
		paramObject.setProjects(list);
		ProgramHelper.service.modify(paramObject);
	    }
	    // 해당 차종의 프로그램을 찾아 일정 동기화 End 2016.12.07 by 황정태

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProgramManagerHelper.class, e);
	    trx.rollback();
	    mp = null;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return mp;
    }

    public static QuerySpec getProgramByCarType(String CarTypeoid) throws Exception {
	QuerySpec sqs = new QuerySpec();
	int idxSub = sqs.addClassList(KETProgramSchedule.class, false);
	ClassAttribute masterOidAttr = new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id");
	sqs.appendSelect(masterOidAttr, false);
	ClassAttribute versionAttr = new ClassAttribute(KETProgramSchedule.class, "versionInfo.identifier.versionId");
	SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
	sqs.appendSelect(max, false);
	sqs.appendGroupBy(new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id"), new int[] { idxSub }, false);

	QuerySpec qs = new QuerySpec();
	qs.setAdvancedQueryEnabled(true);
	int idx = qs.addClassList(KETProgramSchedule.class, true);
	int idx2 = qs.addClassList(WTUser.class, false);
	// int idx3 = qs.addClassList(NumberCode.class, false);
	// int idx4 = qs.addClassList(NumberCode.class, false);
	// int idx5 = qs.addClassList(NumberCode.class, false);
	TableColumn masterOidAttr1 = new TableColumn(qs.getFromClause().getAliasAt(idx), "IDA3MASTERREFERENCE");
	TableColumn versionAttr1 = new TableColumn(qs.getFromClause().getAliasAt(idx), "VERSIONIDA2VERSIONINFO");
	ColumnListExpression listExpression = new ColumnListExpression();
	listExpression.addColumn(masterOidAttr1);
	listExpression.addColumn(versionAttr1);
	SubSelectExpression selectExpression = new SubSelectExpression(sqs);
	selectExpression.setAccessControlRequired(false);
	qs.appendWhere(new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.LATEST_ITERATION, SearchCondition.IS_TRUE),
	        new int[] { idx });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(new ClassAttribute(KETProgramSchedule.class, "programAdminReference.key.id"),
	        SearchCondition.EQUAL, new ClassAttribute(WTUser.class, WTAttributeNameIfc.ID_NAME)), new int[] { idx, idx2 });
	qs.appendAnd();

	qs.appendWhere(new SearchCondition(listExpression, SearchCondition.IN, selectExpression), new int[] { idx });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}

	SearchCondition sc = new SearchCondition(KETProgramSchedule.class, "carTypeReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(CarTypeoid));
	qs.appendWhere(sc, new int[] { idx });

	return qs;
    }

    public static OEMPlan getCalendar(String modelplanOid, String OEMProjectTypeName) throws Exception {

	OEMPlan op = null;
	long mpLong = CommonUtil.getOIDLongValue(modelplanOid);

	QuerySpec qs = new QuerySpec();
	// int idxmp = qs.addClassList(ModelPlan.class, true);
	int idxop = qs.addClassList(OEMPlan.class, true);
	int idxopt = qs.addClassList(OEMProjectType.class, false);

	qs.appendOpenParen();
	SearchCondition sc1 = new SearchCondition(OEMPlan.class, "modelPlanReference.key.id", "=", mpLong);
	qs.appendWhere(sc1, new int[] { idxop });

	qs.appendAnd();

	ClassAttribute ca2 = new ClassAttribute(OEMPlan.class, "oemTypeReference.key.id");
	ClassAttribute ca3 = new ClassAttribute(OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id");

	SearchCondition sc2 = new SearchCondition(ca2, "=", ca3);

	qs.appendWhere(sc2, new int[] { idxop, idxopt });

	qs.appendAnd();

	SearchCondition sc3 = new SearchCondition(OEMProjectType.class, "name", "=", OEMProjectTypeName);
	qs.appendWhere(sc3, new int[] { idxopt });
	qs.appendCloseParen();
	QueryResult qr = PersistenceHelper.manager.find(qs);
	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    op = (OEMPlan) o[0];

	}

	return op;
    }

    public static OEMPlan getCalendar2(String modelplanOid, int OEMProjectTypeName) throws Exception {

	OEMPlan op = null;
	long mpLong = CommonUtil.getOIDLongValue(modelplanOid);

	QuerySpec qs = new QuerySpec();
	// int idxmp = qs.addClassList(ModelPlan.class, true);
	int idxop = qs.addClassList(OEMPlan.class, true);
	int idxopt = qs.addClassList(OEMProjectType.class, false);

	qs.appendOpenParen();

	SearchCondition sc1 = new SearchCondition(OEMPlan.class, "modelPlanReference.key.id", "=", mpLong);
	qs.appendWhere(sc1, new int[] { idxop });

	qs.appendAnd();

	SearchCondition sc2 = new SearchCondition(OEMPlan.class, "viewType", "=", OEMProjectTypeName);

	qs.appendWhere(sc2, new int[] { idxop });

	qs.appendCloseParen();

	QueryResult qr = PersistenceHelper.manager.find(qs);
	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    op = (OEMPlan) o[0];

	}

	return op;
    }

    public String delete(String oid) {
	try {
	    if (oid != null) {
		ReferenceFactory f = new ReferenceFactory();
		ModelPlan mp = (ModelPlan) f.getReference(oid).getObject();

		QuerySpec qs = new QuerySpec(OEMPlan.class);
		qs.appendWhere(new SearchCondition(OEMPlan.class, "modelPlanReference.key.id", "=", mp.getPersistInfo()
		        .getObjectIdentifier().getId()), new int[] { 0 });
		QueryResult qr = PersistenceHelper.manager.find(qs);
		while (qr.hasMoreElements()) {
		    OEMPlan op = (OEMPlan) qr.nextElement();
		    PersistenceHelper.manager.delete(op);
		}

		OEMProjectType opt = (OEMProjectType) CommonUtil.getObject(mp.getCarType().getPersistInfo().getObjectIdentifier()
		        .toString());
		opt.setIsSavePlan(false);
		PersistenceHelper.manager.save(opt);

		PersistenceHelper.manager.delete(mp);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return "삭제 되었습니다.";
    }

    public static String getOEMDate(String oid, ModelPlan mp) {
	String oemDate = "";

	try {

	    if (oid != null) {

		QuerySpec qs = new QuerySpec();
		int idx = qs.appendClassList(OEMPlan.class, true);
		SearchCondition sc = new SearchCondition(OEMPlan.class, "oemTypeReference.key.id", "=", CommonUtil.getOIDLongValue(oid));
		qs.appendWhere(sc, new int[] { idx });
		qs.appendAnd();
		SearchCondition sc2 = new SearchCondition(OEMPlan.class, "modelPlanReference.key.id", "=", CommonUtil.getOIDLongValue(mp));
		qs.appendWhere(sc2, new int[] { idx });

		QueryResult qr = PersistenceHelper.manager.find(qs);
		while (qr.hasMoreElements()) {
		    Object o[] = (Object[]) qr.nextElement();
		    OEMPlan op = (OEMPlan) o[0];
		    oemDate = DateUtil.getTimeFormat(op.getOemEndDate(), "yyyy-MM-dd");
		}

	    }
	} catch (Exception e) {
	    Kogger.error(ProgramManagerHelper.class, e);
	}
	return oemDate;
    }

    public static String deleteOemPlan(String oid) {
	String oemDate = "";

	try {

	    if (oid != null) {

		QuerySpec qs = new QuerySpec();
		int idx = qs.appendClassList(OEMPlan.class, true);
		SearchCondition sc = new SearchCondition(OEMPlan.class, "modelPlanReference.key.id", "=", CommonUtil.getOIDLongValue(oid));
		qs.appendWhere(sc, new int[] { idx });

		QueryResult qr = PersistenceHelper.manager.find(qs);
		while (qr.hasMoreElements()) {
		    Object o[] = (Object[]) qr.nextElement();
		    OEMPlan op = (OEMPlan) o[0];
		    Kogger.debug(ProgramManagerHelper.class, "delete.op.oid---"
			    + op.getPersistInfo().getObjectIdentifier().getStringValue());
		    PersistenceHelper.manager.delete(op);
		}

	    }
	} catch (Exception e) {
	    Kogger.error(ProgramManagerHelper.class, e);
	}
	return oemDate;
    }

    public static QueryResult getEventList(ModelPlan mp) throws Exception {
	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(OEMProjectType.class, true);

	String aa = ((OEMProjectType) mp.getCarType()).getMaker().getPersistInfo().getObjectIdentifier().getStringValue();

	Long makerOid = (Long) CommonUtil.getOIDLongValue(aa);

	ReferenceFactory rf = new ReferenceFactory();
	SearchCondition sc3 = new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", makerOid);

	qs.appendWhere(sc3, new int[] { idx });
	qs.appendAnd();

	SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "isDisabled", SearchCondition.IS_FALSE);
	qs.appendWhere(sc4, new int[] { idx });

	qs.appendAnd();
	SearchCondition sc2 = new SearchCondition(OEMProjectType.class, "oemType", "=", "CUSTOMEREVENT");
	qs.appendWhere(sc2, new int[] { idx });

	ClassAttribute ca = new ClassAttribute(OEMProjectType.class, "code");

	QueryResult qr = PersistenceHelper.manager.find(qs);

	return qr;
	/*
	 * String customName = ""; String customOid = ""; int plan = 0; while (qr.hasMoreElements()) { Object[] o = (Object[])
	 * qr.nextElement(); OEMProjectType oType = (OEMProjectType) o[0]; String oTypeOid =
	 * oType.getPersistInfo().getObjectIdentifier().getStringValue(); String endDate = ProgramManagerHelper.getOEMDate(oTypeOid, mp); }
	 */
    }

    public static QueryResult getPlanEventList(String modelPlanOid, String oemTypeOid) throws Exception {
	long modelPlanOid_ = CommonUtil.getOIDLongValue(modelPlanOid);
	long oemTypeOid_ = CommonUtil.getOIDLongValue(oemTypeOid);
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(OEMPlan.class, true);

	qs.appendWhere(new SearchCondition(OEMPlan.class, "modelPlanReference.key.id", "=", modelPlanOid_), new int[] { idx });

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(OEMPlan.class, "oemTypeReference.key.id", "=", oemTypeOid_), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    public static ModelPlan saveNewEvent(HashMap map) throws Exception {// 기준정보에서 최종고객처에 새로운 이벤트가 등록되었을 경우 해당 이벤트를 찾아 저장한다 2016.12.07 황정태 추가
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("saveNewEvent", ProgramManagerHelper.class.getName(), null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProgramManagerHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProgramManagerHelper.class, e);
		throw new WTException(e);
	    }
	    return (ModelPlan) obj;
	}

	Transaction trx = new Transaction();
	ModelPlan mp = null;
	try {
	    trx.start();

	    String oid = (String) map.get("oid");

	    mp = (ModelPlan) CommonUtil.getObject(oid);

	    QueryResult qr = getEventList(mp);

	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		OEMProjectType carType = (OEMProjectType) objArr[0];
		if (getPlanEventList(CommonUtil.getOIDString(mp), CommonUtil.getOIDString(carType)).size() < 1) {
		    OEMPlan op = OEMPlan.newOEMPlan();
		    op.setViewType(carType.getOLevel());
		    op.setOemType(carType);
		    op.setModelPlan(mp);
		    PersistenceHelper.manager.save(op);
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProgramManagerHelper.class, e);
	    trx.rollback();
	    mp = null;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return mp;
    }

}
