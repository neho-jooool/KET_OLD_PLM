package e3ps.common.drm;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;

public class AuthCheck implements RemoteAccess{
	
	public static DrmAuth getAuth(String userId, String contentHolderId)throws Exception{
		
		 if (!RemoteMethodServer.ServerFlag)
	        {
	            try
	            {
	                RemoteMethodServer rms = RemoteMethodServer.getDefault();
	                Class[] argTypes = { String.class, String.class };
	                Object[] argValues = {userId,  contentHolderId};
	                Object obj = rms.invoke("getAuth", AuthCheck.class.getName(), null, argTypes, argValues);
	                
	                return (DrmAuth)obj;
	                
	            }
	            catch (Exception ex)
	            {
	                Kogger.error(AuthCheck.class, ex);
	                throw new WTException(ex);
	            }
	        }

	        SessionContext sessioncontext = SessionContext.newContext();
	        
	        int accessType = 0;
	        try
	        {
	            SessionHelper.manager.setAdministrator();
	            
	            String startDate = "0";
	            String endDate = "0";
	            Kogger.debug(AuthCheck.class, "#################           AuthCheck call           ####################");
	            Kogger.debug(AuthCheck.class, "#################           userId          : " + userId);
	            Kogger.debug(AuthCheck.class, "#################           contentHolderId : " + contentHolderId);
	            Object obj = CommonUtil.getObject(contentHolderId);
	            boolean isAccess = false;
	            
	            
	            Kogger.debug(AuthCheck.class, "#################           권한 Type : " + accessType);
	            
	            DrmAuth drmauth = new DrmAuth(accessType, "", startDate, endDate);
	            
	            return drmauth;
	        }finally
		    {
		        SessionContext.setContext(sessioncontext);
		    }
		
	}
	
	public static void main(String args[])throws Exception{
		String file = "6666.ffff.xxx";
		String suffix =  "";
        int suffixIndex = file.lastIndexOf(".");
        
		Kogger.debug(AuthCheck.class, file.substring(suffixIndex + 1));
	}

}
