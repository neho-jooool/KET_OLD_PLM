package ext.ket.shared.mail;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.wfm.entity.WorkItemDTO;

public class MigMailSend implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    public static MigMailSend manager = new MigMailSend();

    public static void main(String[] args) {
	try {

	    String toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigMailSend.class, "@start:" + toDayTime);
	    MigMailSend.manager.saveFromExcel();

	    toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigMailSend.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigMailSend.class, e);
	}

    }

    public void saveFromExcel() throws Exception {

	if (!SERVER) {
	    try {

		// Class aclass[] = { String[].class };
		// Object aobj[] = { args };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, null, null);
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

    public void executeMigration() throws Exception {
	// List<Map<String, String>> delay_list = this.getDelayWorkItem();
	List<WTUser> listToUser = null;
	WorkItemDTO dto = new WorkItemDTO();
	WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");

	List<Map<String, Object>> targetList = getRetireTargetList();

	for (Map<String, Object> map : targetList) {
	    listToUser = new ArrayList<WTUser>();
	    listToUser.add((WTUser) map.get("chief"));
	    dto.setCommand((String) map.get("names"));
	    KETMailHelper.service.sendFormMail("08150", "NoticeMailLine2.html", dto, wtUserFrom, listToUser);
	}
    }

    public List<Map<String, Object>> getRetireTargetList() throws Exception {

	QuerySpec qs = new QuerySpec();

	Map<String, Object> chiefMap = new HashMap<String, Object>();
	Map<String, Object> retireInfoMap = new HashMap<String, Object>();
	List<Map<String, Object>> retireInfoList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> targetList = new ArrayList<Map<String, Object>>();

	int idx = qs.appendClassList(People.class, true);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(People.class, People.IS_DISABLE, SearchCondition.IS_FALSE), new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(People.class, People.RETIRE_TARGET_DATE, SearchCondition.NOT_NULL, true), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {

	    Object[] o = (Object[]) qr.nextElement();
	    People peo = (People) o[0];
	    WTUser chief = null;

	    if (peo.getDepartment() != null) {
		chief = PeopleHelper.manager.getChiefUser(peo.getDepartment());
	    } else {
		continue;
	    }
	    if (chief == null) {
		continue;
	    }
	    retireInfoMap = new HashMap<String, Object>();
	    retireInfoMap.put("name", peo.getName());
	    retireInfoMap.put("chief", chief);
	    retireInfoList.add(retireInfoMap);

	    chiefMap.put(CommonUtil.getOIDLongValue2Str(chief), chief);
	}

	Set<String> st = chiefMap.keySet();
	Iterator<String> it = st.iterator();

	String chiefOid = "";

	while (it.hasNext()) {
	    chiefOid = (String) it.next();
	    String names = "";
	    for (Map<String, Object> map : retireInfoList) {
		if (chiefOid.equals(CommonUtil.getOIDLongValue2Str((WTUser) (map.get("chief"))))) {
		    names += (String) map.get("name") + ",";
		}
	    }
	    retireInfoMap = new HashMap<String, Object>();
	    retireInfoMap.put("chief", chiefMap.get(chiefOid));
	    retireInfoMap.put("names", StringUtils.removeEnd(names, ","));

	    targetList.add(retireInfoMap);

	}

	return targetList;
    }

}
