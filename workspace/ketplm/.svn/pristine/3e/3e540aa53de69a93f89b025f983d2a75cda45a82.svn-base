package ext.ket.shared.log;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import wt.method.AuthenticationException;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import ext.ket.shared.log.entity.dto.EcmLogDTO;
import ext.ket.shared.log.service.KetLogHelper;

final public class KogDbUtil {

    public static void log(String eventName, String eventResult, String ecoOid, Throwable e, String logLog, String reEventResult) {

	String errMessage = null;
	if(e != null){
	    errMessage = e.getLocalizedMessage() + " / " + ExceptionUtils.getStackTrace(e);
	}
	log(eventName, eventResult, ecoOid, errMessage, logLog, reEventResult);
    }
    
    public static void log(String eventName, String eventResult, KETMoldChangeOrder eco, Throwable e, String logLog, String reEventResult) {
	
	String errMessage = null;
	if(e != null){
	    errMessage = e.getLocalizedMessage() + " / " + ExceptionUtils.getStackTrace(e);
	}
	log(eventName, eventResult, eco, errMessage, logLog, reEventResult);
    }
    
    public static void log(String eventName, String eventResult, KETProdChangeOrder eco, Throwable e, String logLog, String reEventResult) {
	String errMessage = null;
	if(e != null){
	    errMessage = e.getLocalizedMessage() + " / " + ExceptionUtils.getStackTrace(e);
	}
	log(eventName, eventResult, eco, errMessage, logLog, reEventResult);
    }
    
    public static void log(String eventName, String eventResult, String ecoOid, String errMessage, String logLog, String reEventResult) {

	try {

	    String ecoNo = null;
	    String ecoState = null;

	    if (StringUtils.isNotEmpty(ecoOid)) {

		if (ecoOid.indexOf("Mold") != -1) {

		    KETMoldChangeOrder eco = (KETMoldChangeOrder) CommonUtil.getObject(ecoOid);
		    ecoNo = eco.getEcoId();
		    ecoOid = CommonUtil.getOIDString(eco);
		    ecoState = eco.getLifeCycleState().getDisplay();

		} else if (ecoOid.indexOf("Prod") != -1) {

		    KETProdChangeOrder eco = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);
		    ecoNo = eco.getEcoId();
		    ecoOid = CommonUtil.getOIDString(eco);
		    ecoState = eco.getLifeCycleState().getDisplay();
		}
	    }

	    String ecaName = null;
	    String ecaOid = null;
	    String ecaState = null;
	    log(eventName, eventResult, ecoNo, ecoOid, ecoState, ecaName, ecaOid, ecaState, errMessage, logLog, reEventResult);
	    
	} catch (Exception e) {
	    Kogger.error(KogDbUtil.class, e);
	}
    }
    
    public static void log(String eventName, String eventResult, KETMoldChangeOrder eco, String errMessage, String logLog, String reEventResult) {
	try {
	    
	    String ecoNo = eco==null?null:eco.getEcoId();
	    String ecoOid = eco==null?null:CommonUtil.getOIDString(eco);
	    String ecoState = eco==null?null:eco.getLifeCycleState().getDisplay();
	    String ecaName = null;
	    String ecaOid = null;
	    String ecaState = null;
	    log(eventName, eventResult, ecoNo, ecoOid, ecoState, ecaName, ecaOid, ecaState, errMessage, logLog, reEventResult);
	} catch (Exception e) {
	    Kogger.error(KogDbUtil.class, e);
	}
    }

    public static void log(String eventName, String eventResult, KETProdChangeOrder eco, String errMessage, String logLog, String reEventResult) {
	try {
	    
	    String ecoNo = eco==null?null:eco.getEcoId();
	    String ecoOid = eco==null?null:CommonUtil.getOIDString(eco);
	    String ecoState = eco==null?null:eco.getLifeCycleState().getDisplay();
	    String ecaName = null;
	    String ecaOid = null;
	    String ecaState = null;
	    log(eventName, eventResult, ecoNo, ecoOid, ecoState, ecaName, ecaOid, ecaState, errMessage, logLog, reEventResult);
	} catch (Exception e) {
	    Kogger.error(KogDbUtil.class, e);
	}
    }
    
    /**
     * 
     * @param eventName : 승인완료(Admin) - 결과가 성공했는지 아닌지가 중요함
     * @param eventResult : Success or Fail
     * @param ecoNo
     * @param ecoOid
     * @param ecoState
     * @param ecaName
     * @param ecaOid
     * @param ecaState
     * @param errMessage
     * @param logLog
     * @param reEventResult
     * @throws Exception
     * @메소드명 : log
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 20.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public static void log(String eventName, String eventResult, String ecoNo, String ecoOid, String ecoState, String ecaName, String ecaOid, String ecaState
	    , String errMessage, String logLog, String reEventResult) {

	try {

	    EcmLogDTO log = new EcmLogDTO();
	    
	    // 사용자 정보
	    String[] userInfo = getUserInfo();
	    String userId = userInfo[0];
	    String userName = userInfo[1];
	    
	    log.setEcoNo(StringUtil.checkNull(ecoNo));
	    log.setEcoOid(StringUtil.checkNull(ecoOid));
	    
	    if(StringUtils.isNotEmpty(ecoOid)){
		log.setEcoIda2a2(CommonUtil.getOIDLongValue2Str(ecoOid));
	    }else{
		log.setEcoIda2a2("0");
	    }
	    
	    log.setEcoState(StringUtil.checkNull(ecoState));

	    log.setEcaName(StringUtil.checkNull(ecaName));
	    log.setEcaOid(StringUtil.checkNull(ecaOid));
	    
	    if(StringUtils.isNotEmpty(ecaOid)){
		log.setEcaIda2a2(CommonUtil.getOIDLongValue2Str(ecaOid));
	    }else{
		log.setEcaIda2a2("0");
	    }
	    
	    log.setEcaState(StringUtil.checkNull(ecaState));

	    log.setEventName(StringUtil.checkNull(eventName));
	    log.setEventResult(StringUtil.checkNull(eventResult));
	    
	    // 로그참고정보 길이 제한
	    if(logLog != null && logLog.length() > 1300){
		logLog = logLog.substring(0, 1300);
	    }
	    log.setLogLog(StringUtil.checkNull(logLog));
	    
	    // 에러 길이 제한
	    if(errMessage != null && errMessage.length() > 1300){
		errMessage = errMessage.substring(0, 1300);
	    }
	    log.setErrMessage(StringUtil.checkNull(errMessage));
	    
	    log.setReEventResult(StringUtil.checkNull(reEventResult));

	    log.setChangeUserId(userId);
	    log.setChangeUserName(userName);

//	    log.setLogDate(DateUtil.getCurrentTimestamp());
		
	    //KetLogHelper.service.insertLog(log);
	    
	} catch (Exception e) {
	    Kogger.error(KogDbUtil.class, e);
	}
    }
    
    public static String[] getUserInfo() {

	WTUser user = null;
	try {
	    user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	} catch (AuthenticationException e) {
	} catch (WTException e) {
	} catch (Exception e) {
	}
	String userId = (user != null) ? user.getName() : Kogger.NULLUSER;
	String userName = (user != null) ? user.getFullName() : Kogger.NULLUSER;

	return new String[] { userId, userName };
    }

}
