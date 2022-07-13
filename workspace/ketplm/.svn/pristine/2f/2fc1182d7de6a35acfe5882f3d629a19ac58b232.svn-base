package ext.ket.edm.stamping.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import wt.session.SessionHelper;
import wt.session.SessionServerHelper;

import com.ptc.jws.servlet.JaxWsWebService;

import ext.ket.edm.stamping.service.StampingHelper;
import ext.ket.edm.stamping.util.ErrorWhereEnum;
import ext.ket.shared.log.Kogger;

@WebService()
public class StampingWS extends JaxWsWebService {

    // if( deploy request doc was worked already ), return true > do noting
    @WebMethod(operationName = "isReceivedAlready")
    public boolean isReceivedAlready(String reqOid) throws Exception {

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(false);
	//SessionContext sessioncontext = SessionContext.newContext();
	
	boolean receivedAlready = false;
	try {
	     
	    SessionHelper.manager.setAdministrator();
	    wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
	    if (methodServer.getUserName() == null) {
		methodServer.setUserName("wcadmin");
		methodServer.setPassword("wcadmin");
	    }
	    
	    receivedAlready = StampingHelper.service.isReceivedAlready(reqOid);
	   
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    StampingHelper.service.saveErrorLog(reqOid, ErrorWhereEnum.CheckExecuted.getWhereValue(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
	    throw e;
	}finally{
	    //sessioncontext.clear();
	}

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(true);

	return receivedAlready;
    }

    // request xmls and cad Data download , only 1 call - with not workspace
    @WebMethod(operationName = "getXmlNCadDataByOid")
    public String getXmlNCadDataByOid(String reqOid) throws Exception {

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(false);
	//SessionContext sessioncontext = SessionContext.newContext();
	
	String xmlFilePath = null;
	try {

	    SessionHelper.manager.setAdministrator();
	    wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
	    if (methodServer.getUserName() == null) {
		methodServer.setUserName("wcadmin");
		methodServer.setPassword("wcadmin");
	    }
	    
	    // request oid에서 해당 도면들 가져와서 CAD 파일 내려주고 xml 만들고 path 전달
	    xmlFilePath = StampingHelper.service.makeXmlDownCadDataByOid(reqOid);
	    // xmlFilePath = "D:\\ket\\workspace\\ketplm\\src\\ext\\ket\\edm\\stamping\\xml\\DrawingRequest2.xml";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    StampingHelper.service.saveErrorLog(reqOid, ErrorWhereEnum.CadDownloadXmlCreate.getWhereValue(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
	    throw e;
	}finally{
	    //sessioncontext.clear();
	}

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(true);

	return xmlFilePath;
    }

    @WebMethod(operationName = "saveDrawingDeployRequest")
    public boolean saveDrawingDeployRequest(String reqOid, String xmlFileFullPath) throws Exception {
	// remove access control
	SessionServerHelper.manager.setAccessEnforced(false);
	//SessionContext sessioncontext = SessionContext.newContext();
	
	boolean success = false;
	try {

	    SessionHelper.manager.setAdministrator();
	    wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
	    if (methodServer.getUserName() == null) {
		methodServer.setUserName("wcadmin");
		methodServer.setPassword("wcadmin");
	    }
	    
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "################ saveDrawingDeployRequest ##############");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    
	    // dwg 파일과 pdf 파일, step 파일을 저장한다. NX의 3D CAD 파일들도 저장한다?!
	    success = StampingHelper.service.saveDrawingDeployRequest(reqOid, xmlFileFullPath);
	    // success = true;

	} catch (Exception e) {
	    
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "############ saveDrawingDeployRequest error ############");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    Kogger.debug(getClass(), "########################################################");
	    
	    Kogger.debug(getClass(), e.getMessage());
	    
	    Kogger.error(getClass(), e);
	    StampingHelper.service.saveErrorLog(reqOid, ErrorWhereEnum.SaveResult.getWhereValue(), StringUtils.EMPTY, StringUtils.EMPTY, xmlFileFullPath);
	    throw e;
	}finally{
	    //sessioncontext.clear();
	}

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(true);

	return success;
    }

    @WebMethod(operationName = "saveErrorLog")
    public boolean saveErrorLog(String reqOid, String errorWhere, String errorType, String errorLog, String xmlFilePath) throws Exception {

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(false);
	//SessionContext sessioncontext = SessionContext.newContext();
	
	boolean success = false;
	try {
	    
	    SessionHelper.manager.setAdministrator();
	    wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
	    if (methodServer.getUserName() == null) {
		methodServer.setUserName("wcadmin");
		methodServer.setPassword("wcadmin");
	    }
	    success = StampingHelper.service.saveErrorLog(reqOid, errorWhere, errorType, errorLog, xmlFilePath);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}finally{
	    //sessioncontext.clear();
	}

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(true);

	return success;
    }

    // when shutdown - stamping server request this method with interval
    @WebMethod(operationName = "inputQueueDrawingDistReq")
    public boolean inputQueueDrawingDistReq() throws Exception {

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(false);
	//SessionContext sessioncontext = SessionContext.newContext();
	
	boolean isInputQueue = false;
	try {
	    
	    SessionHelper.manager.setAdministrator();
	    wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
	    if (methodServer.getUserName() == null) {
		methodServer.setUserName("wcadmin");
		methodServer.setPassword("wcadmin");
	    }
	    // receivedAlready = false;
	    isInputQueue = StampingHelper.service.inputQueueDrawingDistReq();

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}finally{
	    //sessioncontext.clear();
	}

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(true);

	return isInputQueue;
    }

    // ///////////////////////////////////////////////////////////
    //
    // Workspace 사용할 경우
    //
    // ///////////////////////////////////////////////////////////

    // request xmls and cad Data download , only n call - with workspace
    @WebMethod(operationName = "getSomeXmlNCadDataByOid")
    public String getSomeXmlNCadDataByOid(String reqOid) throws Exception {

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(false);
	//SessionContext sessioncontext = SessionContext.newContext();
	
	String xmlFilePath = null;
	try {
	    
	    SessionHelper.manager.setAdministrator();
	    wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
	    if (methodServer.getUserName() == null) {
		methodServer.setUserName("wcadmin");
		methodServer.setPassword("wcadmin");
	    }
	    xmlFilePath = "D:\\ket\\workspace\\ketplm\\src\\ext\\ket\\edm\\stamping\\xml\\DrawingRequest2.xml";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}finally{
	    //sessioncontext.clear();
	}

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(true);
	return xmlFilePath;
    }

    @WebMethod(operationName = "addWorkspace")
    public boolean addWorkspace(String... cadOid) throws Exception {
	// remove access control
	SessionServerHelper.manager.setAccessEnforced(false);
	//SessionContext sessioncontext = SessionContext.newContext();
	
	boolean success = false;
	try {

	    SessionHelper.manager.setAdministrator();
	    success = true;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}finally{
	    //sessioncontext.clear();
	}

	// remove access control
	SessionServerHelper.manager.setAccessEnforced(true);
	return success;
    }

}
