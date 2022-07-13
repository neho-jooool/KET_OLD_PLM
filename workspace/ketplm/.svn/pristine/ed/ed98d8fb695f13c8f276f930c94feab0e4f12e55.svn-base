package ext.ket.part.verify;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.util.WTRuntimeException;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.service.KETDmsHelper;
import e3ps.edm.beans.EDMPBOHelper;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.service.KETWfmHelper;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.log4j.KetLogger;

/**
 * 
 * 개발산출문서를 무한으로 만들어서 결재 테스트를 한다.
 * with Excel
 * 
 */

// windchill ext.ket.part.verify.ProblemApprovalVerify 결재개수 결재받을UserId
public class ProblemApprovalVerify implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static ProblemApprovalVerify manager = new ProblemApprovalVerify();

    public ProblemApprovalVerify() {

    }

    // windchill ext.ket.part.verify.ProblemApprovalVerify 결재개수 결재받을UserId
    public static void main(String[] args) {

	try {
	    
	    String toDayTime = "";
	    try {
		
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	       
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(ProblemApprovalVerify.class, "@start:" + toDayTime);
	    ProblemApprovalVerify.manager.saveFromExcel(args);
	    
	    toDayTime = "";
	    try {
		
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	       
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(ProblemApprovalVerify.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(ProblemApprovalVerify.class, e);
	}
    }

    public void saveFromExcel(String[] args) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String[].class };
		Object aobj[] = { args };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
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
	    executeMigration(args);
	}
    }
    
    public void executeMigration(String[] args) throws Exception {
	
	// args[0] = > 만들 산출물 개수
	// args[1] = > 결재상신할 userId
	
	SessionContext sessioncontext = SessionContext.newContext();
	Transaction trx = null;
	 
	try {

	    SessionHelper.manager.setAdministrator();
		
	    KetLogger.migration.debug("## args :" + args);
	    
	    int testNo = Integer.parseInt(args[0]);
	    
	    for( int k=0; k< testNo; k++){
		
		// 1. 개발산출물 생성
		trx = new Transaction();
		trx.start();
		
		Hashtable hash = new Hashtable();
		KETProjectDocument docu = null;
		String oid = null;
		
		hash.put("categoryCode", "PD302017");
		
		SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy.MM.dd HH-mm-ss" );
		String toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
		       
		/**
		 isDRCheckSheet=f, isBuyerSummit=2, projectRank=C, 
		 security=S1, documentDescription=, dRCheckPoint=00, 
		 buyerCode=e3ps.common.code.NumberCode:448241513, firstRegistrationStage1=1, 
		 categoryCodeTxt=DR회의록, hdnBackgroundImage=, documentName=TKLEE 결재 테스트, 
		 categoryCode=PD302017, webEditor=결재테스트, hdnBackgroundColor=, 
		 partOid=[0, wt.part.WTPart:992281495], taskOid=null, buyerCodeTxt=ALPINE, 
		 projectOid=[0, e3ps.project.ProductProject:992279736], cmd=create, 
		 buyerCodeStr=e3ps.common.code.NumberCode:448241513, divisionCodeStr=CA, 
		 webEditorText=결재테스트, proj_number=A14T053, hdnBackgroundRepeat=, 
		 firstRegistrationStage=1, dRPoint=}
		 */
		String idx = null;
		if(k < 10){
		    idx = "00" + k;
		}else if( k < 100){
		    idx = "0" + k;
		}else if( k < 1000){
		    idx = "" + k;
		}
		hash.put("documentName", "Test TKLEE 개산 결재 " + idx + " " + toDayTime);
		hash.put("firstRegistrationStage", "1"); // integer Type
		hash.put("isBuyerSummit", "2"); 
		hash.put("buyerCode", "e3ps.common.code.NumberCode:448241513"); 
		hash.put("divisionCode", "CA"); 
		hash.put("dRCheckPoint", "00");  // integer Type
		hash.put("documentDescription", ""); // ""
		hash.put("security", "S1"); 
		hash.put("webEditor", "결재테스트\n"); 
		hash.put("webEditorText", "결재테스트"); 
		
		Vector partOid = new Vector();
		partOid.add("wt.part.WTPart:992281495");
		hash.put("partOid", new Vector((Vector) partOid)); 

		Vector projectOid = new Vector();
		projectOid.add("e3ps.project.ProductProject:992279736");
		hash.put("projectOid", new Vector((Vector) projectOid));
		
		String outputOid = null;
		hash.put("outputOid", new Vector()); // new String((String) outputOid)); 
		
		// 최종입력을 위해 저장용 임시 Hashtable을 Helper.service에 넘긴다.
		docu = KETDmsHelper.service.insertProjectDoc(hash);
		
//		if(docu != null){
//		    WBFile wbFile = new WBFile();
//		    File file = new File("D:\\Lighthouse.jpg");
//		    wbFile.setFieldName("Lighthouse.jpg");
//		    wbFile.setFile(file);
//		    wbFile.setSize((int)file.length() );
//		    E3PSContentHelper.service.attach(docu, wbFile, "", true);
//		    
//		}
		
		trx.commit();
		trx = null;
		
		// 2. 결재 완료
		trx = new Transaction();
		trx.start();
		
		String pboOid = CommonUtil.getOIDString(docu); //"e3ps.dms.entity.KETProjectDocument:992464332";
		String[] pboOids = null; 
		boolean startFlag = false;
		String[] beforeReview = new String[]{""};
		String[] discuss = new String[]{""}; 
		String[] afterReview = new String[]{args[1]};
		String[] receiver = new String[]{""};
		String[] reference = new String[]{""};
		String discussType = "noDiscuss";
		String comment = "test";
		startProcess(pboOid, pboOids, startFlag, beforeReview, discuss, afterReview, receiver, reference, discussType, comment);
			    
		trx.commit();
		trx = null;
		
		// 3. 결재 확인
		
		
	    }
		
	} catch (Exception e) {
	    
	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;
	    
	} finally {
	    
	    if(trx != null)
   		trx.rollback();
	    
	    SessionContext.setContext(sessioncontext);
	}
    }
    
    public boolean startProcess(String pboOid, String[] pboOids, boolean startFlag, String[] beforeReview, String[] discuss, String[] afterReview, String[] receiver
	    ,String[] reference,String discussType,String comment) {

	Kogger.info(getClass(), "##################################");
	Kogger.info(getClass(), "##### 결재 ( WorkFlow ) 시작");
	Kogger.info(getClass(), "##################################");
	
	boolean flag = false;
//	String pboOid = ParamUtil.getParameter(req, "pbo");// 결재를 요청할 대상 객체
//	String[] pboOids = req.getParameterValues("pboOids");
//	boolean startFlag = Boolean.parseBoolean(ParamUtil.getParameter(req, "startFlag"));
	
	Kogger.info(getClass(), "pboOid:" + pboOid);
	Kogger.info(getClass(), "pboOids:" + pboOids);
	Kogger.info(getClass(), "startFlag:" + startFlag);
	
	try {
	    
//	    String[] beforeReview = req.getParameter("bReview").split(",");
//	    String[] discuss = req.getParameter("discuss").split(",");
//	    String[] afterReview = req.getParameter("aReview").split(",");
//	    String[] receiver = req.getParameter("receiver").split(",");
//	    String[] reference = req.getParameter("reference").split(",");
//	    String discussType = req.getParameter("discussType");
//	    String comment = req.getParameter("textfield");
	    
	    Kogger.info(getClass(), "beforeReview:" + beforeReview);
	    Kogger.info(getClass(), "discuss:" + discuss);
	    Kogger.info(getClass(), "afterReview:" + afterReview);
	    Kogger.info(getClass(), "receiver:" + receiver);
	    Kogger.info(getClass(), "reference:" + reference);
	    Kogger.info(getClass(), "discussType:" + discussType);
	    Kogger.info(getClass(), "comment:" + comment);
		
		
	    if (pboOids != null && pboOids.length > 0) {
		
		for (String str : pboOids) {
		    
		    Persistable pbo = CommonUtil.getObject(str);
		    // 개발의뢰처럼 해석의뢰 KETAnalysisRequestMaster 추가
		    if (pbo instanceof KETDevelopmentRequest || pbo instanceof KETAnalysisRequestMaster) {
			Kogger.info(getClass(), "[startProcess] 개발의뢰 해석의뢰 discussType → reqReceiver");
			discussType = "reqReceiver";
		    }
		    
		    KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);
		    
		    if (master != null) {
			
			Kogger.info(getClass(), "KETWfmApprovalMaster 기존 oid :" + CommonUtil.getOIDString(master) );
			KETWfmHelper.service.updateMaster(CommonUtil.getOIDString(master), discussType, comment);
			master = (KETWfmApprovalMaster) PersistenceHelper.manager.refresh(master);
			if (beforeReview.length > 0)
			    saveApprovalLine("beforeReview", beforeReview, master);
			if (discuss.length > 0)
			    saveApprovalLine("discuss", discuss, master);
			if (afterReview.length > 0)
			    saveApprovalLine("afterReview", afterReview, master);
			if (receiver.length > 0)
			    saveApprovalLine("receiver", receiver, master);
			if (reference.length > 0)
			    saveApprovalLine("reference", reference, master);
			master.setStartFlag(true);
			master = (KETWfmApprovalMaster) PersistenceHelper.manager.save(master);
			flag = KETWfmHelper.service.startProcess(str, master);
			
		    } else {
			
			Kogger.info(getClass(), "결재마스터가 없어서 신규 결재마스터 생성");
			Hashtable<String, Object> hash = new Hashtable<String, Object>();
			hash.put("agreementType", discussType);
			hash.put("creator", SessionHelper.manager.getPrincipalReference());
			hash.put("comment", comment);
			hash.put("pbo", pbo);
			hash.put("startFlag", true);
			
			Kogger.info(getClass(), "agreementType:" + discussType );
			Kogger.info(getClass(), "creator:" + SessionHelper.manager.getPrincipalReference().getFullName() );
			Kogger.info(getClass(), "comment:" + comment );
			Kogger.info(getClass(), "pbo:" + pbo );
			Kogger.info(getClass(), "startFlag:" + true );
			
			
			master = (KETWfmApprovalMaster) KETWfmHelper.service.createMaster(hash); // 결재 마스터 생성
			if (beforeReview.length > 0)
			    saveApprovalLine("beforeReview", beforeReview, master);
			if (discuss.length > 0)
			    saveApprovalLine("discuss", discuss, master);
			if (afterReview.length > 0)
			    saveApprovalLine("afterReview", afterReview, master);
			if (receiver.length > 0)
			    saveApprovalLine("receiver", receiver, master);
			if (reference.length > 0)
			    saveApprovalLine("reference", reference, master);
			
			flag = KETWfmHelper.service.startProcess(str, master);
			flag = true;
			
		    } // if (master != null) {
		} // for (String str : pboOids) {
		
	    } else {
		
		Persistable pbo = CommonUtil.getObject(pboOid);
		if (pbo instanceof KETDevelopmentRequest || pbo instanceof KETAnalysisRequestMaster) {
		    Kogger.info(getClass(), "[startProcess] 개발의뢰 해석의뢰 discussType → reqReceiver");
		    discussType = "reqReceiver";
		}
		KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);

		if (master != null) {
		    
		    Kogger.info(getClass(), "KETWfmApprovalMaster 기존 oid :" + CommonUtil.getOIDString(master) );
		    
		    KETWfmHelper.service.updateMaster(CommonUtil.getOIDString(master), discussType, comment);
		    master = (KETWfmApprovalMaster) PersistenceHelper.manager.refresh(master);
		    if (beforeReview.length > 0)
			saveApprovalLine("beforeReview", beforeReview, master);
		    if (discuss.length > 0)
			saveApprovalLine("discuss", discuss, master);
		    if (afterReview.length > 0)
			saveApprovalLine("afterReview", afterReview, master);
		    if (receiver.length > 0)
			saveApprovalLine("receiver", receiver, master);
		    if (reference.length > 0)
			saveApprovalLine("reference", reference, master);
		    master.setStartFlag(true);
		    master = (KETWfmApprovalMaster) PersistenceHelper.manager.save(master);
		    
		    flag = KETWfmHelper.service.startProcess(pboOid, master);
		    
		} else {
		    
		    Kogger.info(getClass(), "결재마스터가 없어서 신규 결재마스터 생성");
		    Hashtable<String, Object> hash = new Hashtable<String, Object>();
		    hash.put("agreementType", discussType);
		    hash.put("creator", SessionHelper.manager.getPrincipalReference());
		    hash.put("comment", comment);
		    hash.put("pbo", pbo);
		    hash.put("startFlag", true);
		    
		    Kogger.info(getClass(), "agreementType:" + discussType );
		    Kogger.info(getClass(), "creator:" + SessionHelper.manager.getPrincipalReference().getFullName());
		    Kogger.info(getClass(), "comment:" + comment);
		    Kogger.info(getClass(), "pbo:" + pbo);
		    Kogger.info(getClass(), "startFlag:" + true);
		    
		    master = (KETWfmApprovalMaster) KETWfmHelper.service.createMaster(hash); // 결재 마스터 생성
		    
		    if (beforeReview.length > 0)
			saveApprovalLine("beforeReview", beforeReview, master);
		    if (discuss.length > 0)
			saveApprovalLine("discuss", discuss, master);
		    if (afterReview.length > 0)
			saveApprovalLine("afterReview", afterReview, master);
		    if (receiver.length > 0)
			saveApprovalLine("receiver", receiver, master);
		    if (reference.length > 0)
			saveApprovalLine("reference", reference, master);
		    
		    flag = KETWfmHelper.service.startProcess(pboOid, master);
		    
		    flag = true;
		}
	    }
	} catch (WTRuntimeException e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	}

	return flag;
    }    
    
    protected void saveApprovalLine(String type, String[] target, Persistable obj) {
	try {
	    for (int i = 0; i < target.length; i++) {
		if (!target[i].equals("")) {
		    Hashtable hash = new Hashtable();
		    hash.put("order", i); // 결재선 지정 시 순서
		    hash.put("type", type); // 결재 단계 (합의전검토/합의/검토및승인)
		    hash.put("userID", target[i]); // 해당 사용자 id
		    hash.put("master", obj); // 결재마스터
		    
		    Kogger.info(getClass(), "결재선 order:" +  i + " / type:" + type + " / userID:" + target[i] + " / obj:" + CommonUtil.getOIDString(obj));
		    
		    KETWfmHelper.service.createLine(hash);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }
    

}
