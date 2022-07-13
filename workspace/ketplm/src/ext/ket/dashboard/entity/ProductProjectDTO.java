package ext.ket.dashboard.entity;

import e3ps.common.util.PropertiesUtil;
import ext.ket.shared.dto.BaseDTO;

@SuppressWarnings("serial")
public class ProductProjectDTO extends BaseDTO {

    private String customer;           // OEM
    private String carType;            // 차종
    private String detailCar;          // 상세차종
    private String model;
    private String proto;              // PROTO
    private String p1;                 // P1
    private String p2;                 // P2
    private String m;
    private String sop;                // SOP
    private String itemType;
    private String pjtNO;
    private String state;
    private String type;
    private String formType;
    private String modelName;
    private String outsourcing;
    private String taskName;
    private String partnerNo;
    private String pjtName;
    private String devType;
    private String currentDate;
    private String currentDate1;
    private String nextDate;
    private String nextDate1;
    private String currentStep;
    private String nextStep;
    private String sopDate;
    private String currentGate;
    private String oid;
    private String moldTypeCheck;

    private int    completed;          // 완료
    private int    progress;           // 진행
    private int    withdrawn;          // 지연
    private int    moldCount;          // mold수
    private int    pressCount;         // press수
    private int    goodsCount;         // 구매품수
    private int    issueCount;         // 이슈수
    private int    itemCount;
    private int    projectCount;
    private int    projectDelayCount;
    private int    total;
    private int    delaytotal;
    private int    completeCount;
    private int    processCount;
    private int    processCount1;
    private int    delayCount;
    private int    taskCompleCount;
    private int    taskProcessCount;
    private int    taskDelayCount;
    private int    moldCompleteCount;
    private int    moldProcessCount;
    private int    moldProcessCount1;
    private int    moldDelayCount;
    private int    num;
    private int    productTotal;
    private int    moldTotal;
    private int    issueTotal;
    private int    qualityTotal;
    private int    itemMoldTotal;
    private int    itemPressTotal;
    private int    itemGoodsTotal;
    private int    completeTaskCount;
    private int    processTaskCount;
    private int    delayTaskCount;
    private int    taskTotal;

    private int    begin;
    private int    plan;
    private int    process;
    private int    firstTry;
    private int    debuging1;
    private int    debuging2;
    private int    debuging3;
    private int    debuging4;
    private int    moldtransfer;

    private int    beginDelay;
    private int    planDelay;
    private int    processDelay;
    private int    firstTryDelay;
    private int    debugingDelay1;
    private int    debugingDelay2;
    private int    debugingDelay3;
    private int    debugingDelay4;
    private int    moldtransferDelay;

    private int    startMold;
    private int    startMoFa;
    private int    production;
    private int    productionMoFa;
    private int    pjtCount;
    private int    pjtDelayCount;
    private int    taskTotalCount;
    private int    taskTotalDelayCount;

    private int    today;
    private int    sopDay;
    
    public String getCustomer() {
	return customer;
    }

    public void setCustomer(String customer) {
	this.customer = customer;
    }

    public String getCustomerEllipsis() {
	return e3ps.common.util.StringUtil.subByteData(customer, 10);
    }


    public String getCarType() {
	return carType;
    }

    public void setCarType(String carType) {
	this.carType = carType;
    }

    public String getDetailCar() {
	return detailCar;
    }

    public void setDetailCar(String detailCar) {
	this.detailCar = detailCar;
    }

    public String getProto() {
	return proto;
    }

    public void setProto(String proto) {
	this.proto = proto;
    }

    public String getP1() {
	return p1;
    }

    public void setP1(String p1) {
	this.p1 = p1;
    }

    public String getP2() {
	return p2;
    }

    public void setP2(String p2) {
	this.p2 = p2;
    }

    public String getSop() {
	return sop;
    }

    public void setSop(String sop) {
	this.sop = sop;
    }

    public int getCompleted() {
	return completed;
    }

    public void setCompleted(int completed) {
	this.completed = completed;
    }

    public int getProgress() {
	return progress;
    }

    public void setProgress(int progress) {
	this.progress = progress;
    }

    public int getWithdrawn() {
	return withdrawn;
    }

    public void setWithdrawn(int withdrawn) {
	this.withdrawn = withdrawn;
    }

    public String getItemType() {
	return itemType;
    }

    public void setItemType(String itemType) {
	this.itemType = itemType;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public int getMoldCount() {
	return moldCount;
    }

    public void setMoldCount(int moldCount) {
	this.moldCount = moldCount;
    }

    public int getPressCount() {
	return pressCount;
    }

    public void setPressCount(int pressCount) {
	this.pressCount = pressCount;
    }

    public int getGoodsCount() {
	return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
	this.goodsCount = goodsCount;
    }

    public int getIssueCount() {
	return issueCount;
    }

    public void setIssueCount(int issueCount) {
	this.issueCount = issueCount;
    }

    public String getPjtNO() {
	return pjtNO;
    }

    public void setPjtNO(String pjtNO) {
	this.pjtNO = pjtNO;
    }

    public int getItemCount() {
	return itemCount;
    }

    public void setItemCount(int itemCount) {
	this.itemCount = itemCount;
    }

    public int getTotal() {
	return total;
    }

    public void setTotal(int total) {
	this.total = total;
    }

    public int getBegin() {
	return begin;
    }

    public void setBegin(int begin) {
	this.begin = begin;
    }

    public int getPlan() {
	return plan;
    }

    public void setPlan(int plan) {
	this.plan = plan;
    }

    public int getProcess() {
	return process;
    }

    public void setProcess(int process) {
	this.process = process;
    }

    public int getFirstTry() {
	return firstTry;
    }

    public void setFirstTry(int firstTry) {
	this.firstTry = firstTry;
    }

    public int getDebuging1() {
	return debuging1;
    }

    public void setDebuging1(int debuging1) {
	this.debuging1 = debuging1;
    }

    public int getDebuging2() {
	return debuging2;
    }

    public void setDebuging2(int debuging2) {
	this.debuging2 = debuging2;
    }

    public int getDebuging3() {
	return debuging3;
    }

    public void setDebuging3(int debuging3) {
	this.debuging3 = debuging3;
    }

    public int getMoldtransfer() {
	return moldtransfer;
    }

    public void setMoldtransfer(int moldtransfer) {
	this.moldtransfer = moldtransfer;
    }

    public int getStartMold() {
	return startMold;
    }

    public void setStartMold(int startMold) {
	this.startMold = startMold;
    }

    public int getStartMoFa() {
	return startMoFa;
    }

    public void setStartMoFa(int startMoFa) {
	this.startMoFa = startMoFa;
    }

    public int getProduction() {
	return production;
    }

    public void setProduction(int production) {
	this.production = production;
    }

    public int getProductionMoFa() {
	return productionMoFa;
    }

    public void setProductionMoFa(int productionMoFa) {
	this.productionMoFa = productionMoFa;
    }

    public int getBeginDelay() {
	return beginDelay;
    }

    public void setBeginDelay(int beginDelay) {
	this.beginDelay = beginDelay;
    }

    public int getPlanDelay() {
	return planDelay;
    }

    public void setPlanDelay(int planDelay) {
	this.planDelay = planDelay;
    }

    public int getProcessDelay() {
	return processDelay;
    }

    public void setProcessDelay(int processDelay) {
	this.processDelay = processDelay;
    }

    public int getFirstTryDelay() {
	return firstTryDelay;
    }

    public void setFirstTryDelay(int firstTryDelay) {
	this.firstTryDelay = firstTryDelay;
    }

    public int getMoldtransferDelay() {
	return moldtransferDelay;
    }

    public void setMoldtransferDelay(int moldtransferDelay) {
	this.moldtransferDelay = moldtransferDelay;
    }

    public int getPjtCount() {
	return pjtCount;
    }

    public void setPjtCount(int pjtCount) {
	this.pjtCount = pjtCount;
    }

    public int getDelaytotal() {
	return delaytotal;
    }

    public void setDelaytotal(int delaytotal) {
	this.delaytotal = delaytotal;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public int getDebugingDelay1() {
	return debugingDelay1;
    }

    public void setDebugingDelay1(int debugingDelay1) {
	this.debugingDelay1 = debugingDelay1;
    }

    public int getDebugingDelay2() {
	return debugingDelay2;
    }

    public void setDebugingDelay2(int debugingDelay2) {
	this.debugingDelay2 = debugingDelay2;
    }

    public int getDebugingDelay3() {
	return debugingDelay3;
    }

    public void setDebugingDelay3(int debugingDelay3) {
	this.debugingDelay3 = debugingDelay3;
    }

    public int getDebuging4() {
	return debuging4;
    }

    public void setDebuging4(int debuging4) {
	this.debuging4 = debuging4;
    }

    public int getDebugingDelay4() {
	return debugingDelay4;
    }

    public void setDebugingDelay4(int debugingDelay4) {
	this.debugingDelay4 = debugingDelay4;
    }

    public String getFormType() {
	return formType;
    }

    public void setFormType(String formType) {
	this.formType = formType;
    }

    public String getModelName() {
	return modelName;
    }

    public void setModelName(String modelName) {
	this.modelName = modelName;
    }

    public int getCompleteCount() {
	return completeCount;
    }

    public void setCompleteCount(int completeCount) {
	this.completeCount = completeCount;
    }

    public String getProcessCount() {
	return "";
    }

    public void setProcessCount(int processCount) {
	this.processCount = processCount;
    }

    public int getDelayCount() {
	return delayCount;
    }

    public void setDelayCount(int delayCount) {
	this.delayCount = delayCount;
    }

    public int getTaskCompleCount() {
	return taskCompleCount;
    }

    public void setTaskCompleCount(int taskCompleCount) {
	this.taskCompleCount = taskCompleCount;
    }

    public int getTaskProcessCount() {
	return taskProcessCount;
    }

    public void setTaskProcessCount(int taskProcessCount) {
	this.taskProcessCount = taskProcessCount;
    }

    public int getTaskDelayCount() {
	return taskDelayCount;
    }

    public void setTaskDelayCount(int taskDelayCount) {
	this.taskDelayCount = taskDelayCount;
    }

    public int getMoldCompleteCount() {
	return moldCompleteCount;
    }

    public void setMoldCompleteCount(int moldCompleteCount) {
	this.moldCompleteCount = moldCompleteCount;
    }

    public String getMoldProcessCount() {
	return "";
    }

    public void setMoldProcessCount(int moldProcessCount) {
	this.moldProcessCount = moldProcessCount;
    }

    public int getMoldDelayCount() {
	return moldDelayCount;
    }

    public void setMoldDelayCount(int moldDelayCount) {
	this.moldDelayCount = moldDelayCount;
    }

    public String getOutsourcing() {
	return outsourcing;
    }

    public void setOutsourcing(String outsourcing) {
	this.outsourcing = outsourcing;
    }

    public int getProjectCount() {
	return projectCount;
    }

    public void setProjectCount(int projectCount) {
	this.projectCount = projectCount;
    }

    public int getProjectDelayCount() {
	return projectDelayCount;
    }

    public void setProjectDelayCount(int projectDelayCount) {
	this.projectDelayCount = projectDelayCount;
    }

    public int getNum() {
	return num;
    }

    public void setNum(int num) {
	this.num = num;
    }

    public String getTaskName() {
	return taskName;
    }

    public void setTaskName(String taskName) {
	this.taskName = taskName;
    }

    public int getTaskTotalCount() {
	return taskTotalCount;
    }

    public void setTaskTotalCount(int taskTotalCount) {
	this.taskTotalCount = taskTotalCount;
    }

    public int getTaskTotalDelayCount() {
	return taskTotalDelayCount;
    }

    public void setTaskTotalDelayCount(int taskTotalDelayCount) {
	this.taskTotalDelayCount = taskTotalDelayCount;
    }

    public String getPartnerNo() {
	return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
	this.partnerNo = partnerNo;
    }

    public String getPjtName() {
	return pjtName;
    }

    public void setPjtName(String pjtName) {
	this.pjtName = pjtName;
    }

    public String getDevType() {
	return devType;
    }

    public void setDevType(String devType) {
	this.devType = devType;
    }

    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    public String getM() {
	return m;
    }

    public void setM(String m) {
	this.m = m;
    }

    public int getProductTotal() {
	return productTotal;
    }

    public void setProductTotal(int productTotal) {
	this.productTotal = productTotal;
    }

    public int getMoldTotal() {
	return moldTotal;
    }

    public void setMoldTotal(int moldTotal) {
	this.moldTotal = moldTotal;
    }

    public int getIssueTotal() {
	return issueTotal;
    }

    public void setIssueTotal(int issueTotal) {
	this.issueTotal = issueTotal;
    }

    public int getQualityTotal() {
	return qualityTotal;
    }

    public void setQualityTotal(int qualityTotal) {
	this.qualityTotal = qualityTotal;
    }

    public String getCurrentDate() {
	return currentDate;
    }

    public void setCurrentDate(String currentDate) {
	this.currentDate = currentDate;
    }

    public String getNextDate() {
	return nextDate;
    }

    public void setNextDate(String nextDate) {
	this.nextDate = nextDate;
    }

    public String getCurrentStep() {
	return currentStep;
    }

    public void setCurrentStep(String currentStep) {
	this.currentStep = currentStep;
    }

    public String getNextStep() {
	return nextStep;
    }

    public void setNextStep(String nextStep) {
	this.nextStep = nextStep;
    }

    public int getItemMoldTotal() {
	return itemMoldTotal;
    }

    public void setItemMoldTotal(int itemMoldTotal) {
	this.itemMoldTotal = itemMoldTotal;
    }

    public int getItemPressTotal() {
	return itemPressTotal;
    }

    public void setItemPressTotal(int itemPressTotal) {
	this.itemPressTotal = itemPressTotal;
    }

    public int getItemGoodsTotal() {
	return itemGoodsTotal;
    }

    public void setItemGoodsTotal(int itemGoodsTotal) {
	this.itemGoodsTotal = itemGoodsTotal;
    }

    public int getCompleteTaskCount() {
	return completeTaskCount;
    }

    public void setCompleteTaskCount(int completeTaskCount) {
	this.completeTaskCount = completeTaskCount;
    }

    public String getProcessTaskCount() {
	return "";
    }

    public void setProcessTaskCount(int processTaskCount) {
	this.processTaskCount = processTaskCount;
    }

    public int getDelayTaskCount() {
	return delayTaskCount;
    }

    public void setDelayTaskCount(int delayTaskCount) {
	this.delayTaskCount = delayTaskCount;
    }

    public String getSopDate() {
	return sopDate;
    }

    public void setSopDate(String sopDate) {
	this.sopDate = sopDate;
    }

    public int getTaskTotal() {
	return taskTotal;
    }

    public void setTaskTotal(int taskTotal) {
	this.taskTotal = taskTotal;
    }

    public String getCurrentDate1() {
	return currentDate1;
    }

    public void setCurrentDate1(String currentDate1) {
	this.currentDate1 = currentDate1;
    }

    public String getNextDate1() {
	return nextDate1;
    }

    public void setNextDate1(String nextDate1) {
	this.nextDate1 = nextDate1;
    }

    public int getPjtDelayCount() {
	return pjtDelayCount;
    }

    public void setPjtDelayCount(int pjtDelayCount) {
	this.pjtDelayCount = pjtDelayCount;
    }

    public String getCurrentGate() {
	return currentGate;
    }

    public void setCurrentGate(String currentGate) {
	this.currentGate = currentGate;
    }

    public String getOid() {
	return oid;
    }

    public void setOid(String oid) {
	this.oid = oid;
    }

    public String getDisplayStateHtmlPrefix() {
	String str = "";
	
	if(this.getSopDay() <= this.getToday()){
	    	if(this.getSopDay() != 0){
	    	    str = "<img src='/plm/extcore/image/ico_red.png' />";
	    	}
	}else{
        	if ("R".equals(this.getCurrentGate())) {
        	    str = "<img src='/plm/extcore/image/ico_red.png' />";
        	}
        	else if ("Y".equals(this.getCurrentGate())) {
        	    str = "<img src='/plm/extcore/image/ico_yellow.png' />";
        	}
        	else if ("G".equals(this.getCurrentGate())) {
        	    str = "<img src='/plm/extcore/image/ico_green.png' />";
        	}
	}
	return str;

    }

    public String getDisplayStateHtmlPostfix() {
	return "";
    }

    public String getProductTotalHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getProductTotalHtmlPostfix() {
	return "</font>";
    }

    public String getCompleteCountHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getCompleteCountHtmlPostfix() {
	return "</font>";
    }

    public String getProcessCountHtmlPrefix() {
	String str = "";
	if (this.delayCount > 0) {
	    str = "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'><a href=\"javascript:opener.productProcessProjectPopup('" + this.customer + "','" + this.carType
		    + "');\">" + this.processCount + "</a>/" + "<a href=\"javascript:opener.productDelayProjectPopup('" + this.customer + "','" + this.carType + "');\">"
		    + "<span style=\"color:#ff0000\">" + this.delayCount + "</span>" + "</a>";
	}
	else {
	    str = "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'><a href=\"javascript:opener.productProcessProjectPopup('" + this.customer + "','" + this.carType
		    + "');\">" + this.processCount + "</a>";
	}
	return str;
    }

    public String getProcessTaskCountHtmlPrefix() {
	String str = "";
	if (this.delayTaskCount > 0) {
	    str = "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'><a href=\"javascript:opener.productProcessTaskPopup('" + this.customer + "','" + this.carType
		    + "');\">" + this.processTaskCount + "</a>/" + "<a href=\"javascript:opener.productDelayTaskPopup('" + this.customer + "','" + this.carType + "');\">"
		    + "<span style=\"color:#ff0000\">" + this.delayTaskCount + "</span>" + "</a>";
	}
	else {
	    str = "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'><a href=\"javascript:opener.productProcessTaskPopup('" + this.customer + "','" + this.carType
		    + "');\">" + this.processTaskCount + "</a>";
	}
	return str;
    }

    public String getMoldProcessCountHtmlPrefix() {
	String str = "";
	if (this.moldDelayCount > 0) {
	    str = "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'><a href=\"javascript:opener.moldProcessProjectPopup('" + this.customer + "','" + this.carType
		    + "');\">" + this.moldProcessCount + "</a>/" + "<a href=\"javascript:opener.moldDelayProjectPopup('" + this.customer + "','" + this.carType + "');\">"
		    + "<span style=\"color:#ff0000\">" + this.moldDelayCount + "</span>" + "</a>";
	}
	else {
	    str = "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'><a href=\"javascript:opener.moldProcessProjectPopup('" + this.customer + "','" + this.carType
		    + "');\">" + this.moldProcessCount + "</a>";
	}
	return str;
    }

    public String getProcessCountHtmlPostfix() {
	return "</font>";
    }

    public String getTaskTotalHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getTaskTotalHtmlPostfix() {
	return "</font>";
    }

    public String getCompleteTaskCountHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getCompleteTaskCountHtmlPostfix() {
	return "</font>";
    }

    public String getProcessTaskCountHtmlPostfix() {
	return "</font>";
    }

    public String getMoldTotalHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getMoldTotalHtmlPostfix() {
	return "</font>";
    }

    public String getMoldCompleteCountHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getMoldCompleteCountHtmlPostfix() {
	return "</font>";
    }

    public String getMoldProcessCountHtmlPostfix() {
	return "</font>";
    }

    public String getItemMoldTotalHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getItemMoldTotalHtmlPostfix() {
	return "</font>";
    }

    public String getItemPressTotalHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getItemPressTotalHtmlPostfix() {
	return "</font>";
    }

    public String getItemGoodsTotalHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getItemGoodsTotalHtmlPostfix() {
	return "</font>";
    }

    public String getIssueTotalHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getIssueTotalHtmlPostfix() {
	return "</font>";
    }

    public String getQualityTotalHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getQualityTotalHtmlPostfix() {
	return "</font>";
    }

    public String getDelayCountHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDelayCountHtmlPostfix() {
	return "</font>";
    }

    public String getDelayTaskCountHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDelayTaskCountHtmlPostfix() {
	return "</font>";
    }

    public String getMoldDelayCountHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getMoldDelayCountHtmlPostfix() {
	return "</font>";
    }

    public String getCarTypeHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getCarTypeHtmlPostfix() {
	return "</font>";
    }

    public String getMoldTypeCheck() {
	return moldTypeCheck;
    }

    public void setMoldTypeCheck(String moldTypeCheck) {
	this.moldTypeCheck = moldTypeCheck;
    }

    public int getProcessCount1() {
	return processCount1;
    }

    public void setProcessCount1(int processCount1) {
	this.processCount1 = processCount1;
    }

    public int getMoldProcessCount1() {
	return moldProcessCount1;
    }

    public void setMoldProcessCount1(int moldProcessCount1) {
	this.moldProcessCount1 = moldProcessCount1;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getSopDay() {
        return sopDay;
    }

    public void setSopDay(int sopDay) {
        this.sopDay = sopDay;
    }

    
}
