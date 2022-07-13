/**
 *	@(#) CashFileUploader.java
 *	Copyright (c) khkim. All rights reserverd
 * 
 *	@version 1.00
 *	@since jdk 1.5.02
 *	@createdate 2009. 06. 25.
 *	@author kim ki hong, khkim@e3ps.com
 *	@desc	
 */
package e3ps.load.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.util.WTException;
import ext.ket.shared.log.Kogger;


public class CashFileUploader implements Serializable, RemoteAccess{
	
	Vector cachFiles = new Vector();
	private long cachId = -1l;
	
	private static Object lock = new Object();
	
	private String userId = "wcadmin";
	
	public CashFileUploader(){
		
	}
	
	public CashFileUploader(String userId){
		this.userId = userId;
	}
	
	public String getFolder(){
		return String.valueOf(cachId);
	}
	
	public long getCashId()throws WTException{
		try{
			if(!RemoteMethodServer.ServerFlag){
				RemoteMethodServer remotemethodserver = RemoteMethodServer.getDefault();
				Long obj = (Long)remotemethodserver.invoke("getCashId", getClass().getName(), this, null, null);
	            return obj.longValue();
	               
	        }
		}
        catch(Exception ex)
        {
            throw new WTException(ex);
        }
            
		synchronized(lock){
			return System.currentTimeMillis();
		}
	}
	
	
	public void addUploadFile(File file) throws WTException, FileNotFoundException{
		
		addUploadFile(file, false);
	}
	
	public void addUploadFile(File file, boolean isDRM) throws WTException, FileNotFoundException{
		
		if(cachId < 0){
			cachId = getCashId();
		}
		
		//Kogger.debug(getClass(), cachId);
		
		CachFileUpload upload =new CachFileUpload();
		Hashtable hashtable = new Hashtable();
		hashtable.put(CachFileUpload.CASHFOLDERID, String.valueOf(cachId));
		hashtable.put("FileName", file.getName());
		if(isDRM){
			hashtable.put("DRM", "DRM");
		}
		upload.setData(userId, hashtable, new FileInputStream(file));
		cachFiles.add(upload);
	}
	
	public void excute(){
		//Vector vector = new Vector();
		for(int i = 0; i < cachFiles.size(); i++){
			CachFileUpload upload = (CachFileUpload)cachFiles.get(i);
			try {
				upload.excute();
			} catch (WTException e) {
				Kogger.error(getClass(), e);
			}
		}
		//return vector;
	}
	

}

