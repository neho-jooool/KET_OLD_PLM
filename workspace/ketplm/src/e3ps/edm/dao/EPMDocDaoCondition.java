package e3ps.edm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.StringUtil;
import e3ps.edm.beans.EDMHelper;

public class EPMDocDaoCondition {

    private List<String> businessTypeList = new ArrayList<String>();
    private List<String> partNumberList = new ArrayList<String>();
    private List<String> epmNoList = new ArrayList<String>();
    private List<String> epmNameList = new ArrayList<String>();
    private List<String> createDateFromList = new ArrayList<String>();
    private List<String> createDateToList = new ArrayList<String>();
    private List<String> creatorList = new ArrayList<String>();
    private List<String> createDeptNameList = new ArrayList<String>();
    private boolean isLatestReVision = false;
    private List<String> projectNoList = new ArrayList<String>();
    private List<String> statusList = new ArrayList<String>();
    private List<String> cadTypeList = new ArrayList<String>();
    private List<String> devStageList = new ArrayList<String>();
    private List<String> cadCategoryList = new ArrayList<String>();

    public EPMDocDaoCondition(List<Map> conditionList) {

	KETParamMapUtil map = null;
	Map<String, String> keyCheck = new HashMap<String, String>();
	for (Map<String, Object> condition : conditionList) {
	    map = KETParamMapUtil.getMap(condition);

	    // 사업부 구분이 있는 경우
	    String[] businessTypeArray = map.getStringArray("businessType");
	    if (businessTypeArray != null && businessTypeArray.length > 0) {
		for (String businessType : businessTypeArray) {
		    if (!keyCheck.containsKey("businessType_" + businessType)) {
			addBusinessTypeList(businessType);
			keyCheck.put("businessType_" + businessType, null);
		    }
		}
	    }

	    // 부품번호가 있는 경우
	    String[] partNumberArray = map.getStringArray("partNumberMasterOid");
	    if (partNumberArray != null && partNumberArray.length > 0) {
		for (String partNumber : partNumberArray) {
		    if (!keyCheck.containsKey("partNumber_" + partNumber)) {
			addPartNumberList(partNumber);
			keyCheck.put("partNumber_" + partNumber, null);
		    }
		}
	    }

	    // 도면명이 있는 경우
	    if (StringUtil.checkString(map.getString("name"))) {
		if (!keyCheck.containsKey("empName_" + map.getString("name"))) {
		    addEpmNameList(map.getString("name"));
		    keyCheck.put("empName_" + map.getString("name"), null);
		}
	    }

	    // 도면번호가 있는 경우
	    if (StringUtil.checkString(map.getString("number"))) {
		if (!keyCheck.containsKey("empNumber_" + map.getString("number"))) {
		    addEpmNoList(map.getString("number"));
		    keyCheck.put("empNumber_" + map.getString("number"), null);
		}
	    }

	    // 작성일자(from)가 있는 경우
	    if (StringUtil.checkString(map.getString("create_start"))) {
		String create_start = map.getString("create_start");
		create_start = create_start.substring(0, 4) + create_start.substring(5, 7) + create_start.substring(8, 10);
		if (!keyCheck.containsKey("create_start_" + create_start)) {
		    addCreateDateFromList(create_start);
		    keyCheck.put("create_start_" + create_start, null);
		}

	    }

	    // 작성일자(to)가 있는 경우
	    if (StringUtil.checkString(map.getString("create_end"))) {
		String create_end = map.getString("create_end");
		create_end = create_end.substring(0, 4) + create_end.substring(5, 7) + create_end.substring(8, 10);
		if (!keyCheck.containsKey("create_end_" + create_end)) {
		    addCreateDateToList(create_end);
		    keyCheck.put("create_end_" + create_end, null);
		}
	    }

	    // 작성자가 있는 경우
	    String creator = map.getString("creator");
	    if (creator != null && creator.length() > 0) {
		if (!keyCheck.containsKey("creator_" + creator)) {
		    addCreatorList(creator);
		    keyCheck.put("creator_" + creator, null);
		}
	    }

	    // 작성부서가 있는 경우
	    String edmCreateDept = map.getString("edmCreateDeptName");
	    if (edmCreateDept != null && edmCreateDept.length() > 0) {
		if (!keyCheck.containsKey("edmCreateDeptName_" + edmCreateDept)) {
		    addCreateDeptNameList(edmCreateDept);
		    keyCheck.put("edmCreateDeptName_" + edmCreateDept, null);
		}
	    }

	    // 최신 버전일 경우
	    if (!isLatestReVision) {
		String latest = map.getString("latest");
		if (latest.length() == 0) {
		    isLatestReVision = true;
		}
	    }

	    // 프로젝트번호가 있는 경우
	    String projectNo = map.getString("projectNo");
	    if (projectNo != null && projectNo.length() > 0) {
		if (!keyCheck.containsKey("projectNo_" + projectNo)) {
		    addProjectNoList(projectNo);
		    keyCheck.put("projectNo_" + projectNo, null);
		}
	    }

	    // 상태가 있는 경우
	    String[] stateArray = map.getStringArray("state");
	    if (stateArray != null && stateArray.length > 0) {
		for (String status : stateArray) {
		    if (!keyCheck.containsKey("state_" + status)) {
			addStatusList(status);
			keyCheck.put("state_" + status, null);
		    }
		}
	    }

	    // CAD종류가 있는 경우
	    String[] cadAppTypeArray = map.getStringArray(EDMHelper.IBA_CAD_APP_TYPE);
	    if (cadAppTypeArray != null && cadAppTypeArray.length > 0) {
		for (String cadAppType : cadAppTypeArray) {
		    if (!keyCheck.containsKey("cadType_" + cadAppType)) {
			addCadTypeList(cadAppType);
			keyCheck.put("cadType_" + cadAppType, null);
		    }
		}
	    }

	    // 도면구분이 있는 경우
	    String[] devStageAry = map.getStringArray(EDMHelper.IBA_DEV_STAGE);
	    if (devStageAry != null && devStageAry.length > 0) {
		for (String devStage : devStageAry) {
		    if (!keyCheck.containsKey("devStage_" + devStage)) {
			addDevStageList(devStage);
			keyCheck.put("devStage_" + devStage, null);
		    }
		}
	    }

	    // 도면유형이 있는 경우
	    String[] categoryAry = map.getStringArray(EDMHelper.IBA_CAD_CATEGORY);
	    if (categoryAry != null && categoryAry.length > 0) {
		for (String category : categoryAry) {
		    if (!keyCheck.containsKey("category_" + category)) {
			addCadCategoryList(category);
			keyCheck.put("category_" + category, null);
		    }
		}
	    }
	}
    }

    public List<String> getBusinessTypeList() {
	return businessTypeList;
    }

    public void addBusinessTypeList(String businessType) {
	this.businessTypeList.add(businessType);
    }

    public List<String> getPartNumberList() {
	return partNumberList;
    }

    public void addPartNumberList(String partNumber) {
	this.partNumberList.add(partNumber);
    }

    public List<String> getEpmNoList() {
	return epmNoList;
    }

    public void addEpmNoList(String epmNo) {
	this.epmNoList.add(epmNo);
    }

    public List<String> getEpmNameList() {
	return epmNameList;
    }

    public void addEpmNameList(String epmName) {
	this.epmNameList.add(epmName);
    }

    public List<String> getCreateDateFromList() {
	return createDateFromList;
    }

    public void addCreateDateFromList(String createDateFrom) {
	this.createDateFromList.add(createDateFrom);
    }

    public List<String> getCreateDateToList() {
	return createDateToList;
    }

    public void addCreateDateToList(String createDateTo) {
	this.createDateToList.add(createDateTo);
    }

    public List<String> getCreatorList() {
	return creatorList;
    }

    public void addCreatorList(String creator) {
	this.creatorList.add(creator);
    }

    public List<String> getCreateDeptNameList() {
	return createDeptNameList;
    }

    public void addCreateDeptNameList(String createDept) {
	this.createDeptNameList.add(createDept);
    }

    public boolean isLatestReVision() {
	return isLatestReVision;
    }

    public void setLatestReVision(boolean isLatestReVision) {
	this.isLatestReVision = isLatestReVision;
    }

    public List<String> getProjectNoList() {
	return projectNoList;
    }

    public void addProjectNoList(String projectNo) {
	this.projectNoList.add(projectNo);
    }

    public List<String> getStatusList() {
	return statusList;
    }

    public void addStatusList(String status) {
	this.statusList.add(status);
    }

    public List<String> getCadTypeList() {
	return cadTypeList;
    }

    public void addCadTypeList(String cadType) {
	this.cadTypeList.add(cadType);
    }

    public List<String> getDevStageList() {
	return devStageList;
    }

    public void addDevStageList(String devStage) {
	this.devStageList.add(devStage);
    }

    public List<String> getCadCategoryList() {
	return cadCategoryList;
    }

    public void addCadCategoryList(String cadCategory) {
	this.cadCategoryList.add(cadCategory);
    }

}
