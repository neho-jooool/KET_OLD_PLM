/**
 *	@(#) CachFile.java
 *	Copyright (c) khkim. All rights reserverd
 * 
 *	@version 1.00
 *	@since jdk 1.5.02
 *	@createdate 2009. 06. 25.
 *	@author kim ki hong, khkim@e3ps.com
 *	@desc	
 */
package e3ps.load.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.content.multipart.UploadFile;
import e3ps.common.content.uploader.WBFile;
import ext.ket.shared.log.Kogger;

public class CachFile extends FileUpload {


    private static final long serialVersionUID = -992124419791110341L;
    /**
	 * 
	 */
    private transient File    file;
    private transient String  fileName;
    private transient String  contentType;

    public CachFile(WBFile file) throws WTException {
	this(SessionHelper.manager.getPrincipal().getName(), file);
    }

    public CachFile(String userId, WBFile file) throws WTException {
	this(userId, file.getName(), file.getFieldName(), file.getInputStream());
	/*
	serverPlay = true;
	Hashtable hashtable = new Hashtable();
	hashtable.put("FileName", file.getName());
	hashtable.put("ContentRole", file.getFieldName());
	setData(userId, hashtable, file.getInputStream());
	*/
    }

    public CachFile(String fileName, String contentRole, InputStream is) throws WTException {
	this(SessionHelper.manager.getPrincipal().getName(), fileName, contentRole, is);
    }

    public CachFile(String userId, String fileName, String contentRole, InputStream is) throws WTException {
	serverPlay = true;
	Hashtable hashtable = new Hashtable();
	hashtable.put("FileName", fileName);
	hashtable.put("ContentRole", contentRole);
	setData(userId, hashtable, is);
    }

    public CachFile(UploadFile file) throws WTException {
	this(SessionHelper.manager.getPrincipal().getName(), file);
    }

    public CachFile(String userId, UploadFile file) throws WTException {
	serverPlay = true;
	Hashtable hashtable = new Hashtable();
	hashtable.put("FileName", file.getFile().getName());
	hashtable.put("ContentRole", file.getFieldName());
	BufferedInputStream in = null;
	try {
	    in = new BufferedInputStream(new FileInputStream(file.getFile()));
	} catch (FileNotFoundException e) {
	    Kogger.error(getClass(), e);
	}
	setData(userId, hashtable, in);
    }

    public CachFile() {
	super();

    }

    public String getName() {
	return fileName;
    }

    public long getSize() {
	if (file != null) {
	    return file.length();
	}
	return 0;
    }

    public InputStream getInputStream() throws IOException {
	BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
	return in;
    }

    public File getFile() {
	return this.file;
    }

    public String getContentType() {
	return this.contentType;
    }


    public void excute(Hashtable hashtable, InputStream inputstream) throws WTException {



	//String folderId = (String)hashtable.get("CASHFOLDERID");

	byte b[] = new byte[8192];

	BufferedInputStream fin = new BufferedInputStream(inputstream);


	fileName = (String) hashtable.get("FileName");
	contentType = (String) hashtable.get("ContentRole");


	try {
	    file = File.createTempFile(fileName, "windchill");

	} catch (IOException e2) {
	    Kogger.error(getClass(), e2);
	}


	BufferedOutputStream outs = null;

	try {
	    outs = new BufferedOutputStream(new FileOutputStream(file));
	} catch (FileNotFoundException e1) {
	    throw new WTException(e1);
	}
	int read = 0;
	try {
	    while ((read = fin.read(b, 0, 8192)) > 0) {
		outs.write(b, 0, read);
		outs.flush();
	    }
	    //outs.flush();
	    //outs.close();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		fin.close();
	    } catch (IOException e) {
		Kogger.error(getClass(), e);
	    }
	} // end try

    }

    public static void main(String args[]) throws Exception {

	File file = File.createTempFile("prefix", "suffix");
	Kogger.debug(CachFile.class, file.getPath());


    }


}
