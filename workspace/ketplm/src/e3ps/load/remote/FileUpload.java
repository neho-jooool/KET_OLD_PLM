/**
 *	@(#) FileUpload.java
 *	Copyright (c) khkim. All rights reserverd
 * 
 *	@version 1.00
 *	@since jdk 1.5.02
 *	@createdate 2009. 06. 25.
 *	@author kim ki hong, khkim@e3ps.com
 *	@desc	
 */
package e3ps.load.remote;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Hashtable;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTIOException;
import ext.ket.shared.log.Kogger;

public abstract class FileUpload
    implements Serializable, RemoteAccess
{
	
	private transient boolean consumed;
    private transient Hashtable maps;
    protected transient boolean serverPlay;
    private transient String id;
    protected transient Object returnObject;
    private InputStream in;
    private URL url = null;
    
    public FileUpload(URL url){
    	this.url = url;
 
    }
    
    
    public FileUpload()
    {
        consumed = false;
    }

    public void setData(String userid, Hashtable hashtable, InputStream in)
    {
    	id = userid;
        maps = hashtable;
        this.in = in;
    }

    public Object excute()
        throws WTException
    {
        serverPlay = true;
        if(!RemoteMethodServer.ServerFlag || url != null){
            try
            {   Kogger.debug(getClass(), "excute");
                serverPlay = true;
                RemoteMethodServer remotemethodserver = null;
                if(url != null){
                	Kogger.debug(getClass(), url);
                	remotemethodserver = RemoteMethodServer.getInstance(url);
                }else {
                	remotemethodserver = RemoteMethodServer.getDefault();
                }
                
               
                Object obj = remotemethodserver.invoke("excute", getClass().getName(), this, null, null);
                
                return obj;
               
            }
            catch(Exception ex)
            {
                throw new WTException(ex);
            }
        }
        
        return returnObject;
        
    }
    
    public Object excuteFromServer()throws WTException{
    	try
        {   Kogger.debug(getClass(), "excute");
            serverPlay = true;
            RemoteMethodServer remotemethodserver = null;
            if(url != null){
            	Kogger.debug(getClass(), url);
            	remotemethodserver = RemoteMethodServer.getInstance(url);
            }else {
            	remotemethodserver = RemoteMethodServer.getDefault();
            }
            
           
            Object obj = remotemethodserver.invoke("excute", getClass().getName(), this, null, null);
            
            return obj;
           
        }catch(Exception ex)
        {
            throw new WTException(ex);
        }
    
    }

    public abstract void excute(Hashtable hashtable, InputStream inputstream)
        throws WTException;
    
    private void readObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        if(objectinputstream.readBoolean())
        {   
        	id = (String)objectinputstream.readObject();
            SessionContext sessioncontext = SessionContext.newContext();
            try
            {
                SessionHelper.manager.setPrincipal(id);
            }
            catch(Exception ex)
            {
                throw new IOException("Session Error");
            }
        
      
	        maps = (Hashtable)objectinputstream.readObject();
	        try
	        {
	        	
	        	FilterInputStream filterinputstream = new FilterInputStream(objectinputstream) {
	                public void close()
	                {
	                }
	            };
	            excute(maps, filterinputstream);
	        }
	        catch(Exception exception)
	        {
	            throw new WTIOException(null, exception);
	        }
        }
        
    }

    private void writeObject(ObjectOutputStream objectoutputstream)
        throws IOException
    {
    	try{
    		objectoutputstream.writeBoolean(serverPlay);
    		objectoutputstream.writeObject(id);
    		
            objectoutputstream.writeObject(maps);
            if(consumed)
                throw new NotSerializableException("Input stream already consumed");
            objectoutputstream.flush();
            byte abyte0[] = new byte[8192];
            consumed = true;
            int i;
            
            if(in != null){
	            while((i = in.read(abyte0, 0, 8192)) > 0) 
	            {
	                objectoutputstream.write(abyte0, 0, i);
	            }
            }
    		
    	}
        finally
        {
            if(in != null)
            	in.close();
        }
        //in.close();
    }

    
}
