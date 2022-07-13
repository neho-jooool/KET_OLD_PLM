package e3ps.load.remote.edm;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.edm.CADAppType;
import e3ps.edm.CADCategory;
import e3ps.edm.DevStage;
import e3ps.edm.clients.batch.EPMCodeData;
import e3ps.edm.util.EDMProperties;
import ext.ket.shared.log.Kogger;

public class EPMCodeHelper implements RemoteAccess {
	
	public static Vector getCADAppTypeSet(String category) throws Exception {
		CADAppType[] cadAppTypeArr = null;
		
		if(category.length() > 0) {
			cadAppTypeArr = EDMProperties.getInstance().getCADAppTypeSet(category); 
		}
		
		Vector result = new Vector();
		if(cadAppTypeArr != null) {
			CADAppType cadApp = null;
			for(int i = 0; i < cadAppTypeArr.length; i++) {
				cadApp = cadAppTypeArr[i];
				result.add(new EPMCodeData("", cadApp.toString(),
						cadApp.getDisplay(Locale.KOREA),""));
			}
		}
		return result;
	}
	
	public static Vector getCADCatsSet(String manageType, String devStage) throws Exception {
		EDMProperties edmProperties = EDMProperties.getInstance();
		
		ArrayList catArr = new ArrayList();
		if( (manageType.length() > 0) && (devStage.length() > 0) ) {
			catArr = edmProperties.getCADCatsSet(manageType,devStage, true);
		}
		
		Vector result = new Vector();
		for(int i = 0; i < catArr.size(); i++) {
			CADCategory cadCat = (CADCategory)catArr.get(i);
			result.add(new EPMCodeData("", cadCat.toString(),
					cadCat.getDisplay(Locale.KOREA),""));
		}
		return result;
	}
	
	public static Vector getDevStageSet(String manageType) throws Exception {
		EDMProperties edmProperties = EDMProperties.getInstance();
        
		DevStage[] devStageArr = null;
		devStageArr =  edmProperties.getDevStageSet(manageType);
		
		Vector result = new Vector();
		
		if(devStageArr != null) {
			DevStage stage = null;
			for(int i = 0; i < devStageArr.length; i++) {
				stage = devStageArr[i];
				result.add(new EPMCodeData("", stage.toString(),
						stage.getDisplay(Locale.KOREA),""));
			}
		}
		return result;
	}
	
	public static Vector getBizCodes(String userId)throws Exception{
		EDMProperties edmProperties = EDMProperties.getInstance();
        String bizCodeType = edmProperties.getBizCodeType();
        
		if (!RemoteMethodServer.ServerFlag)
        {
            try
            {
            	Class argTypes[] = { String.class };
                Object argValues[] = { bizCodeType };
                Object obj = RemoteMethodServer.getDefault().invoke("_getNumberCode", EPMCodeHelper.class.getName(), null, argTypes, argValues);
                return (Vector)obj;
            }
            catch (Exception ex)
            {
                Kogger.error(EPMCodeHelper.class, ex);
            }
        }
		
        return _getNumberCode(bizCodeType);
	}
	
	public static Vector getNumberCode(String type)throws Exception{
		if (!RemoteMethodServer.ServerFlag)
        {
            try
            {
                Class argTypes[] = { String.class };
                Object argValues[] = { type };
                Object obj = RemoteMethodServer.getDefault().invoke("_getNumberCode", EPMCodeHelper.class.getName(), null, argTypes, argValues);
                return (Vector)obj;
            }
            catch (Exception ex)
            {
                Kogger.error(EPMCodeHelper.class, ex);
            }
        }
		
		return _getNumberCode(type);
	}
	
	
	public static Vector _getNumberCode(String type)throws Exception{
		Vector result = new Vector();
		
		SessionContext sessioncontext = SessionContext.newContext();
        try
        {
            SessionHelper.manager.setAdministrator();
		
            Vector codeVec = NumberCodeHelper.manager.getNumberCodeForQuery(type);
			if(codeVec != null) {
				for(int i = 0; i < codeVec.size(); i++) {
					NumberCode code = (NumberCode)codeVec.get(i);
					result.add(new EPMCodeData(type, code.getCode(), code.getName(),
							code.getPersistInfo().getObjectIdentifier().getStringValue())
					);
				}
			}
        }finally{
        	SessionContext.setContext(sessioncontext);
        }
        
        return result;
	}
}
