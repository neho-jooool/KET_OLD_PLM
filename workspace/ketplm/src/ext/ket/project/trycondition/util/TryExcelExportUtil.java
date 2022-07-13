package ext.ket.project.trycondition.util;

import java.util.Collections;
import java.util.Vector;

import wt.org.WTUser;
import e3ps.common.util.DateUtil;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.project.trycondition.entity.KETTryCondition;
import ext.ket.project.trycondition.entity.KETTryMold;
import ext.ket.project.trycondition.entity.KETTryPress;
import ext.ket.project.trycondition.entity.TryConditionDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.shared.util.ExcelLoader;
import ext.ket.wfm.entity.KETWfmApprovalHistoryDTO;

public class TryExcelExportUtil extends ExcelLoader {
    private KETTryCondition tryCondition;
    private static final int CELL_E = 4;
    private static final int CELL_O = 14;
    private static final int CELL_Y = 24;
    private static final String TRY_MOLD_TEMPLATE_FILE = "D:/ptc/Windchill_10.2/Windchill/codebase/extcore/jsp/project/trycondition/금형Try조건표(Mold)_개정v1.0.xlsx";
    private static final String TRY_PRESS_TEMPLATE_FILE = "D:/ptc/Windchill_10.2/Windchill/codebase/extcore/jsp/project/trycondition/금형Try조건표(Press)_개정v2.0.xlsx";

    public TryExcelExportUtil(KETTryCondition tryCondition) throws Exception {
	this.tryCondition = tryCondition;
	if (tryCondition instanceof KETTryMold) {
	    this.setExcelFileName(TRY_MOLD_TEMPLATE_FILE);
	} else if (tryCondition instanceof KETTryPress) {
	    this.setExcelFileName(TRY_PRESS_TEMPLATE_FILE);
	}
    }

    @Override
    public void setExcelFileName(String fileName) {
	this.excelFileName = fileName;
    }

    /**
     * TryMold data를 excel로 변환한다.
     * 
     * @param tryMold
     * @throws Exception
     * @메소드명 : exportByTryMold
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("unchecked")
    public void exportByTryMold(KETTryMold tryMold) throws Exception {
	TryConditionDTO dto = new TryConditionDTO(tryMold);
	// 작성(작성자 부서 작성일)
	this.setCellValue(2, 18, tryMold.getCreatorFullName() + "\n" + dto.getCreatedDeptName() + "\n" + DateUtil.getDateString(tryMold.getCreateTimestamp(), "D"));
	// 검토(검토자 부서 검토일)
	String reviewUserName = "";
	String reviewUserDeptName = "";
	String reviewDate = "";
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(tryMold);
	if (approvalMaster != null) {
	    Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	    Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	    for (KETWfmApprovalHistory history : lineVec) {
		String activityName = history.getActivityName();
		if (!history.isLast() && KETWfmApprovalHistoryDTO.REVIEW.equals(activityName)) {
		    WTUser reviewUser = (WTUser) history.getOwner().getObject();
		    PeopleData peopleData = new PeopleData(reviewUser);
		    reviewUserName = reviewUser.getFullName();
		    reviewUserDeptName = peopleData.departmentName;
		    reviewDate = DateUtil.getDateString(history.getCompletedDate(), "D");
		    break;
		}
	    }
	}
	this.setCellValue(2, 22, reviewUserName + "\n" + reviewUserDeptName + "\n" + reviewDate);
	// 승인(승인자 부서 승인일)
	this.setCellValue(2, 26, dto.getApprover() + "\n" + dto.getApprovedDeptName() + "\n" + (StringUtil.checkString(dto.getApprovedDate()) ? dto.getApprovedDate().substring(0, 10) : ""));
	// Try No. Sub ver.
	this.setCellValue(5, 0, "▣ Try No. / " + dto.getTryNo() + "       Sub ver." + dto.getSubVer());
	// Die No.
	this.setCellValue(7, CELL_E, tryMold.getDieNo());
	// 제품설계
	this.setCellValue(7, CELL_O, tryMold.getProductDesignRole());
	// 금형제작
	this.setCellValue(7, CELL_Y, tryMold.getMoldDesignRole());
	// Part No
	this.setCellValue(8, CELL_E, tryMold.getPartNo());
	// 금형설계
	this.setCellValue(8, CELL_O, tryMold.getMoldDesignRole());
	// 금형Try
	this.setCellValue(8, CELL_Y, tryMold.getMoldTryRole());
	// Part Name
	this.setCellValue(9, CELL_E, tryMold.getPartName());
	// 금형난이도
	this.setCellValue(10, CELL_E, tryMold.getMoldRank());
	// Try 일자
	this.setCellValue(10, CELL_O, DateUtil.getDateString(tryMold.getTryDate(), "D"));
	// Try 장소
	this.setCellValue(10, CELL_Y, tryMold.getTryPlace());
	// 참석자
	this.setCellValue(11, CELL_E, tryMold.getAttendant());
	// 디버깅 사유
	this.setCellValue(12, CELL_E, tryMold.getDebugReason());
	// 특이사항
	this.setCellValue(13, CELL_E, tryMold.getDetail());
	// Maker
	this.setCellValue(16, CELL_E, tryMold.getMaterial().getMaterialMaker().getName());
	// Grade
	this.setCellValue(16, CELL_O, tryMold.getGrade());
	// Color
	this.setCellValue(16, CELL_Y, tryMold.getColor());
	// 건조온도
	this.setCellValue(17, CELL_E, tryMold.getDryTemp());
	// 건조시간
	this.setCellValue(17, CELL_O, tryMold.getDryTime());
	// 수분율
	this.setCellValue(17, CELL_Y, tryMold.getMoistureRate());
	// 금형구조
	this.setCellValue(20, CELL_E, tryMold.getMoldStruc().getName() + " " + StringUtil.checkNull(tryMold.getMoldStrucEtc()));
	// Mold Base Size(가로)
	this.setCellValue(20, CELL_O + 4, tryMold.getMoldBaseSizeWidth());
	// Mold Base Size(세로)
	this.setCellValue(20, CELL_O + 9, tryMold.getMoldBaseSizeLength());
	// Mold Base Size(높이)
	this.setCellValue(20, CELL_O + 14, tryMold.getMoldBaseSizeHeight());
	// Weight
	this.setCellValue(21, CELL_E, tryMold.getWeight());
	// 게이트방식
	this.setCellValue(21, CELL_O, tryMold.getGateType().getName());
	// Cavity
	this.setCellValue(21, CELL_Y, tryMold.getCavityCount());
	// 취부판두께
	this.setCellValue(22, CELL_E, tryMold.getMountThickness().getName() + " " + StringUtil.checkNull(tryMold.getMountThicknessEtc()));
	// 온도센서 홀
	this.setCellValue(22, CELL_O, tryMold.getTemperatureSensor().getName());
	// 기계사양
	this.setCellValue(25, CELL_E, tryMold.getMachineSpec());
	// Screw 직경
	this.setCellValue(25, CELL_O, tryMold.getScrewDiameter());
	// Nozzle Type
	this.setCellValue(25, CELL_Y, tryMold.getNozzleType());
	// 온유
	this.setCellValue(26, CELL_E, tryMold.getOilTemp());
	// 톤수
	this.setCellValue(26, CELL_O, tryMold.getTone());
	// 타이바 간격
	this.setCellValue(26, CELL_Y, tryMold.getTiebarInterval());
	// 실린더 온도
	String cylinderTemp = "";
	if (tryMold.getCylinderTempNH() != null)
	    cylinderTemp += "NH: " + tryMold.getCylinderTempNH() + " ℃   ";
	if (tryMold.getCylinderTempN1() != null)
	    cylinderTemp += "N1: " + tryMold.getCylinderTempN1() + " ℃   ";
	if (tryMold.getCylinderTempN2() != null)
	    cylinderTemp += "N2: " + tryMold.getCylinderTempN2() + " ℃   ";
	if (tryMold.getCylinderTempN3() != null)
	    cylinderTemp += "N3: " + tryMold.getCylinderTempN3() + " ℃  ";
	if (tryMold.getCylinderTempN4() != null)
	    cylinderTemp += "N4: " + tryMold.getCylinderTempN4() + " ℃";
	this.setCellValue(27, CELL_E, cylinderTemp);
	// 냉각수 온도
	String collWaterTemp = "";
	if (tryMold.getCoolWaterTempHigh() != null)
	    collWaterTemp += "상: " + tryMold.getCoolWaterTempHigh() + " ℃   ";
	if (tryMold.getCoolWaterTempLow() != null)
	    collWaterTemp += "하: " + tryMold.getCoolWaterTempLow() + " ℃";
	this.setCellValue(28, CELL_E, collWaterTemp);
	// 사출압력
	this.setCellValue(31, 0, "사출압력 (" + tryMold.getInjectPressUnit().getName() + ")");
	String injectPress = "";
	if (tryMold.getInjectPress1() != null)
	    injectPress += "1단: " + tryMold.getInjectPress1() + "  ";
	if (tryMold.getInjectPress2() != null)
	    injectPress += "2단: " + tryMold.getInjectPress2() + "  ";
	if (tryMold.getInjectPress3() != null)
	    injectPress += "3단: " + tryMold.getInjectPress3() + "  ";
	if (tryMold.getInjectPress4() != null)
	    injectPress += "4단: " + tryMold.getInjectPress4() + "  ";
	if (tryMold.getInjectPress5() != null)
	    injectPress += "5단: " + tryMold.getInjectPress5() + "  ";
	this.setCellValue(31, CELL_E, injectPress);
	// 사출속도
	String injectSpeed = "";
	if (tryMold.getInjectSpeed1() != null)
	    injectSpeed += "1단: " + tryMold.getInjectSpeed1() + "  ";
	if (tryMold.getInjectSpeed2() != null)
	    injectSpeed += "2단: " + tryMold.getInjectSpeed2() + "  ";
	if (tryMold.getInjectSpeed3() != null)
	    injectSpeed += "3단: " + tryMold.getInjectSpeed3() + "  ";
	if (tryMold.getInjectSpeed4() != null)
	    injectSpeed += "4단: " + tryMold.getInjectSpeed4() + "  ";
	if (tryMold.getInjectSpeed5() != null)
	    injectSpeed += "5단: " + tryMold.getInjectSpeed5() + "  ";
	this.setCellValue(32, CELL_E, injectSpeed);
	// 다단거리전환
	String shortTransition = "";
	if (tryMold.getShortTransition1() != null)
	    shortTransition += "1단: " + tryMold.getShortTransition1() + "  ";
	if (tryMold.getShortTransition2() != null)
	    shortTransition += "2단: " + tryMold.getShortTransition2() + "  ";
	if (tryMold.getShortTransition3() != null)
	    shortTransition += "3단: " + tryMold.getShortTransition3() + "  ";
	if (tryMold.getShortTransition4() != null)
	    shortTransition += "4단: " + tryMold.getShortTransition4() + "  ";
	if (tryMold.getShortTransition5() != null)
	    shortTransition += "5단: " + tryMold.getShortTransition5() + "  ";
	this.setCellValue(33, CELL_E, shortTransition);
	// 보압
	this.setCellValue(34, 0, "보압 (" + tryMold.getPackingPressUnit().getName() + ")");
	String holdPress = "";
	if (tryMold.getHoldPress1() != null)
	    holdPress += "1단: " + tryMold.getHoldPress1() + "  ";
	if (tryMold.getHoldPress2() != null)
	    holdPress += "2단: " + tryMold.getHoldPress2() + "  ";
	if (tryMold.getHoldPress3() != null)
	    holdPress += "3단: " + tryMold.getHoldPress3() + "  ";
	this.setCellValue(34, CELL_E, holdPress);
	// 보압속도
	this.setCellValue(35, 0, "보압속도 (" + tryMold.getPackingPressUnit().getName() + ")");
	String holdPressSpeed = "";
	if (tryMold.getHoldPressSpeed1() != null)
	    holdPressSpeed += "1단: " + tryMold.getHoldPress1() + "  ";
	if (tryMold.getHoldPressSpeed2() != null)
	    holdPressSpeed += "2단: " + tryMold.getHoldPress2() + "  ";
	if (tryMold.getHoldPressSpeed3() != null)
	    holdPressSpeed += "3단: " + tryMold.getHoldPress3() + "  ";
	this.setCellValue(35, CELL_E, holdPressSpeed);
	// 형개속도
	String moldOpenSpeed = "";
	if (tryMold.getMoldOpenSpeed1() != null)
	    moldOpenSpeed += "1단: " + tryMold.getMoldOpenSpeed1() + "  ";
	if (tryMold.getMoldOpenSpeed2() != null)
	    moldOpenSpeed += "2단: " + tryMold.getMoldOpenSpeed2() + "  ";
	if (tryMold.getMoldOpenSpeed3() != null)
	    moldOpenSpeed += "3단: " + tryMold.getMoldOpenSpeed3() + "  ";
	this.setCellValue(36, CELL_E, moldOpenSpeed);
	// 형개거리
	String moldOpenDist = "";
	if (tryMold.getMoldOpenDist1() != null)
	    moldOpenDist += "1단: " + tryMold.getMoldOpenDist1() + "  ";
	if (tryMold.getMoldOpenDist2() != null)
	    moldOpenDist += "2단: " + tryMold.getMoldOpenDist2() + "  ";
	if (tryMold.getMoldOpenDist3() != null)
	    moldOpenDist += "3단: " + tryMold.getMoldOpenDist3() + "  ";
	this.setCellValue(37, CELL_E, moldOpenDist);
	// Stroke
	this.setCellValue(38, CELL_E, tryMold.getStroke());
	// Cycle Time
	this.setCellValue(38, CELL_O, tryMold.getCycleTime());
	// Shot 중량
	this.setCellValue(38, CELL_Y, tryMold.getShotWeight());
	// 속도
	this.setCellValue(39, CELL_E, tryMold.getSpeed());
	// 사출시간
	this.setCellValue(39, CELL_O, tryMold.getPressTime());
	// Sprue
	this.setCellValue(39, CELL_Y, tryMold.getSprue());
	// 압력
	this.setCellValue(40, CELL_E, tryMold.getPressures());
	// 냉각시간
	this.setCellValue(40, CELL_O, tryMold.getCoolingTime());
	// 총C/V중량
	this.setCellValue(40, CELL_Y, tryMold.getCvWeight());
	// 횟수
	this.setCellValue(41, CELL_E, tryMold.getPressCount());
	// 보압시간
	this.setCellValue(41, CELL_O, tryMold.getHoldPressTime());
	// 금형온도 고정측(입력값,실제값)
	this.setCellValue(41, CELL_Y, "입력값:" + StringUtil.checkNull(tryMold.getFixedSideTempInput()) + "(실제값:" + tryMold.getFixedSideTemp() + ")");
	// 전진지연시간
	this.setCellValue(42, CELL_E, tryMold.getDelayTime());
	// 형폐시간
	this.setCellValue(42, CELL_O, tryMold.getMoldCloseTime());
	// 금형온도 이동측(입력값,실제값)
	this.setCellValue(42, CELL_Y, "입력값:" + StringUtil.checkNull(tryMold.getMovingSideTempInput()) + "(실제값:" + tryMold.getMovingSideTemp() + ")");
	// 배압
	this.setCellValue(43, CELL_E, tryMold.getBackPress());
	// 계량
	this.setCellValue(43, CELL_O, tryMold.getMensuration());
	// 저압형체
	this.setCellValue(43, CELL_Y, tryMold.getLowPressShape());
	// 저압형체 단위
	this.setCellValue(43, CELL_Y + 4, tryMold.getLowPressShapeUnit().getName());
	// 보압전환점
	this.setCellValue(44, CELL_E, tryMold.getHoldPressTurnPoint());
	// 계량시간
	this.setCellValue(44, CELL_O, tryMold.getMensurationTime());
	// 고압형체구간
	this.setCellValue(44, CELL_Y, tryMold.getHighPressShapeSection());
	// 쿠션량
	this.setCellValue(45, CELL_E, tryMold.getCushionAmount());
	// 흘림방지설정
	this.setCellValue(45, CELL_O, tryMold.getSpillResistentCfg());
	// Shot수
	this.setCellValue(45, CELL_Y, tryMold.getShotCount());
	// 계량거리
	this.setCellValue(46, CELL_E, tryMold.getMensurationDist());
	// 흘림방지속도
	this.setCellValue(46, CELL_O, tryMold.getSpillResistantSpeed());
	// 최고충진압
	this.setCellValue(46, CELL_Y, tryMold.getMaxPress());
	// 비고
	this.setCellValue(49, 0, tryMold.getDescription());
    }

    /**
     * TryPress data를 excel로 변환한다.
     * 
     * @param tryPress
     * @throws Exception
     * @메소드명 : exportByTryPress
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("unchecked")
    public void exportByTryPress(KETTryPress tryPress) throws Exception {
	TryConditionDTO dto = new TryConditionDTO(tryPress);
	// 작성(작성자 부서 작성일)
	this.setCellValue(2, 18, tryPress.getCreatorFullName() + "\n" + dto.getCreatedDeptName() + "\n" + DateUtil.getDateString(tryPress.getCreateTimestamp(), "D"));
	// 검토(검토자 부서 검토일)
	this.setCellValue(2, 24, "");
	String reviewUserName = "";
	String reviewUserDeptName = "";
	String reviewDate = "";
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(tryPress);
	if (approvalMaster != null) {
	    Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	    Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	    for (KETWfmApprovalHistory history : lineVec) {
		String activityName = history.getActivityName();
		if (!history.isLast() && KETWfmApprovalHistoryDTO.REVIEW.equals(activityName)) {
		    WTUser reviewUser = (WTUser) history.getOwner().getObject();
		    PeopleData peopleData = new PeopleData(reviewUser);
		    reviewUserName = reviewUser.getFullName();
		    reviewUserDeptName = peopleData.departmentName;
		    reviewDate = DateUtil.getDateString(history.getCompletedDate(), "D");
		    break;
		}
	    }
	}
	this.setCellValue(2, 22, reviewUserName + "\n" + reviewUserDeptName + "\n" + reviewDate);
	// 승인(승인자 부서 승인일)
	this.setCellValue(2, 26, dto.getApprover() + "\n" + dto.getApprovedDeptName() + "\n" + (StringUtil.checkString(dto.getApprovedDate()) ? dto.getApprovedDate().substring(0, 10) : ""));
	// Try No. Sub ver.
	this.setCellValue(5, 0, "▣ Try No. / " + dto.getTryNo() + "       Sub ver." + dto.getSubVer());
	// Die No.
	this.setCellValue(7, CELL_E, tryPress.getDieNo());
	// 제품설계
	this.setCellValue(7, CELL_O, tryPress.getProductDesignRole());
	// 금형제작
	this.setCellValue(7, CELL_Y, tryPress.getMoldDesignRole());
	// Part No
	this.setCellValue(8, CELL_E, tryPress.getPartNo());
	// 금형설계
	this.setCellValue(8, CELL_O, tryPress.getMoldDesignRole());
	// 금형Try
	this.setCellValue(8, CELL_Y, tryPress.getMoldTryRole());
	// Part Name
	this.setCellValue(9, CELL_E, tryPress.getPartName());
	// 금형난이도
	this.setCellValue(10, CELL_E, tryPress.getMoldRank());
	// Try 일자
	this.setCellValue(10, CELL_O, DateUtil.getDateString(tryPress.getTryDate(), "D"));
	// Try 장소
	this.setCellValue(10, CELL_Y, tryPress.getTryPlace());
	// 참석자
	this.setCellValue(11, CELL_E, tryPress.getAttendant());
	// 디버깅 사유
	this.setCellValue(12, CELL_E, tryPress.getDebugReason());
	// 특이사항
	this.setCellValue(13, CELL_E, tryPress.getDetail());
	// Grade
	this.setCellValue(16, CELL_E, tryPress.getMaterial().getGrade());
	// 두께 x 폭
	this.setCellValue(16, CELL_O, tryPress.getThickness());
	// 도금
	this.setCellValue(16, CELL_Y, tryPress.getPlating());
	// 금형구조
	this.setCellValue(19, CELL_E, tryPress.getMoldStruc().getName() + " " + StringUtil.checkNull(tryPress.getMoldStrucEtc()));
	// Mold Base Size(가로)
	this.setCellValue(19, CELL_O + 2, tryPress.getMoldSizeWidth());
	// Mold Base Size(세로)
	this.setCellValue(19, CELL_O + 7, tryPress.getMoldSizeLength());
	// Mold Base Size(높이)
	this.setCellValue(19, CELL_O + 12, tryPress.getMoldSizeHeight());
	// 제품방식
	this.setCellValue(20, CELL_E, tryPress.getProductMethod().getName() + " " + StringUtil.checkNull(tryPress.getProductMethodEtc()));
	// 금형 중량(전체)
	try {
	    this.setCellValue(20, CELL_O + 2, Integer.parseInt(tryPress.getMoldWeightTop()) + Integer.parseInt(tryPress.getMoldWeightLower()) + "");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	// 금형 중량(상형)
	this.setCellValue(20, CELL_O + 7, tryPress.getMoldWeightTop());
	// 금형 중량(하형)
	this.setCellValue(20, CELL_O + 12, tryPress.getMoldWeightLower());
	// Die Height
	this.setCellValue(21, CELL_E, tryPress.getDieHeight());
	// pitch
	this.setCellValue(21, CELL_O, tryPress.getPitch());
	// 타발속도
	this.setCellValue(21, CELL_Y, tryPress.getPunchingSpeed());
	// 제품 취출 수
	this.setCellValue(22, CELL_E, tryPress.getProductOutputCol() + " 열 X " + tryPress.getProductOutputPitch() + "피치");
	// 스크랩 처리
	this.setCellValue(22, CELL_O, tryPress.getScrapProcess().getName());
	// 안전센서
	String saftSensor = "";
	String[] saftSensorArr = tryPress.getSaftySensor().split(",");
	for (int i = 0; i < saftSensorArr.length; i++) {
	    String code = saftSensorArr[i];
	    if (i == 0) {
		saftSensor += NumberCodeUtil.getNumberCodeValue("PRESSSAFETYSENSOR", code);
	    } else {
		saftSensor += ", " + NumberCodeUtil.getNumberCodeValue("PRESSSAFETYSENSOR", code);
	    }
	}
	this.setCellValue(22, CELL_Y, saftSensor);
	// 타발유
	this.setCellValue(23, CELL_E, tryPress.getPunchingOil().getName());
	// 타발수량
	this.setCellValue(23, CELL_O, tryPress.getPunchingCount());
	// Press
	this.setCellValue(26, CELL_E, tryPress.getPress());
	// 톤수
	this.setCellValue(26, CELL_O, tryPress.getTone());
	// 설비정보
	this.setCellValue(26, CELL_Y, "Stroke: " + tryPress.getStroke() + " mm   " + "SPM: " + tryPress.getSpm());
	// Bolster 정보
	this.setCellValue(27, CELL_E, "가로: " + tryPress.getBolsterWidth() + "   세로: " + tryPress.getBolsterHeight() + " mm");
	// 피더기
	String feederMachine = "";
	String[] feederMachineArr = tryPress.getFeederMachine().split(",");
	for (int i = 0; i < feederMachineArr.length; i++) {
	    String code = feederMachineArr[i];
	    if (i == 0) {
		feederMachine += NumberCodeUtil.getNumberCodeValue("PRESSFIDER", code);
	    } else {
		feederMachine += ", " + NumberCodeUtil.getNumberCodeValue("PRESSFIDER", code);
	    }
	}
	this.setCellValue(27, CELL_O, feederMachine + "  " + StringUtil.checkNull(tryPress.getFeederMachineEtc()));
	// Sample 검사 결과
	this.setCellValue(30, 0, tryPress.getTestResult());
	// 비고
	this.setCellValue(33, 0, tryPress.getDescription());
    }

    @Override
    public void setWorkBookData() {
	try {
	    if (tryCondition instanceof KETTryMold) {
		this.exportByTryMold((KETTryMold) tryCondition);
	    } else if (tryCondition instanceof KETTryPress) {
		this.exportByTryPress((KETTryPress) tryCondition);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

}
