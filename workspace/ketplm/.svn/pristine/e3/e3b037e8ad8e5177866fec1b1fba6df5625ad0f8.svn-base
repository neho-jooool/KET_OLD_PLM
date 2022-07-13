/**
 *	@(#) FileOnlyUpload.java
 *	Copyright (c) khkim. All rights reserverd
 *
 *	@version 1.00
 *	@since jdk 1.5.02
 *	@createdate 2009. 06. 25.
 *	@author kim ki hong, khkim@e3ps.com
 *	@desc
 */
package e3ps.bom.command.loadexcelbom;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
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

public abstract class FileOnlyUpload implements Serializable, RemoteAccess {

    private transient boolean consumed;

    private transient Hashtable maps;

    protected transient boolean upload;

    private transient String id;

    protected transient Object returnObject;

    private InputStream in;

    private transient URL url = null;

    private transient String serviceName;



    public FileOnlyUpload(URL url) {
        this.upload = true;
        this.url = url;
    }

    public FileOnlyUpload(URL url, String serviceName) {
        this.upload = true;
        this.serviceName = serviceName;
        this.url = url;
    }

    public FileOnlyUpload() {
        this.upload = true;
        consumed = false;
    }

    public void setData(String userid, Hashtable hashtable, InputStream in) {
        id = userid;
        maps = hashtable;
        this.in = in;
    }

    public void setDirectory(String directory){
    	maps.put("Directory", directory);
    }

    public void setData(Hashtable hashtable, InputStream in) {
        this.setData(null, hashtable, in);
    }

    public void excute() throws WTException {
        upload = true;
        if (!RemoteMethodServer.ServerFlag || url != null) {
            try {
                Kogger.debug(getClass(), "excute");
                upload = true;
                RemoteMethodServer remotemethodserver = null;
                if (url != null) {
                    Kogger.debug(getClass(), url);
                    remotemethodserver = RemoteMethodServer.getInstance(url, serviceName);
                } else {
                    remotemethodserver = RemoteMethodServer.getDefault();
                }

                Object obj = remotemethodserver.invoke("excute", getClass()
                        .getName(), this, null, null);

                //return obj;
            } catch (Exception ex) {
                throw new WTException(ex);
            }
        }
        //return returnObject;
    }

    public abstract void excute(Hashtable hashtable, InputStream inputstream)
            throws WTException;

    private void readObject(ObjectInputStream objectinputstream)
            throws IOException, ClassNotFoundException {
        boolean flag = objectinputstream.readBoolean();
        if (flag) {
            upload = false;

            id = (String) objectinputstream.readObject();

            if(id != null){
                Kogger.debug(getClass(), "userId = " + id);
                SessionContext sessioncontext = SessionContext.newContext();
                try {
                    SessionHelper.manager.setPrincipal(id);
                } catch (Exception ex) {
                    throw new IOException("Session Error");
                }
            }

            maps = (Hashtable) objectinputstream.readObject();

            boolean alreadyExcute = objectinputstream.readBoolean();

            Kogger.debug(getClass(), "alreadyExcute= "  + alreadyExcute);
            if(alreadyExcute){
                return;
            }


            try {
                FilterInputStream filterinputstream = new FilterInputStream(
                        objectinputstream) {
                    public void close() {
                    }
                };
                excute(maps, filterinputstream);
            } catch (Exception exception) {
                throw new WTIOException(null, exception);
            } finally{
                if(id != null){
                    SessionContext.setContext(null);
                }
            }

        } else {
            returnObject = objectinputstream.readObject();
        }

    }

    private void writeObject(ObjectOutputStream objectoutputstream)
            throws IOException {

        objectoutputstream.writeBoolean(upload);


        if (upload) {

        	InputStream inputStream  = null;
        	try {

        		objectoutputstream.writeObject(id);
                objectoutputstream.writeObject(maps);
                objectoutputstream.writeBoolean(consumed);
                objectoutputstream.flush();

                if (consumed){
                	//throw new IOException("already consumed..");
                    Kogger.debug(getClass(), "consumed...");
                	return;
                }

                byte abyte0[] = new byte[4096];
                int i;
                if (in != null) {
                    while ((i = in.read(abyte0, 0, 4096)) > 0) {
                        objectoutputstream.write(abyte0, 0, i);
                    }
                }
                consumed = true;
            } finally {
                if (inputStream != null)
                	inputStream.close();
                if(in != null){
                	in.close();
                }
            }

        }else{
            objectoutputstream.writeObject(returnObject);
            objectoutputstream.flush();
        }

        // in.close();
    }

}
