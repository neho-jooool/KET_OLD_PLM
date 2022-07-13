/*
 * @(#) FormUploader.java Create on 2007. 10. 15.
 * Copyright (c) e3ps. All rights reserved
 *
 * @version 1.00
 * @since jdk 1.5.09
 * @createdate 2007. 10. 15.
 * @author Seong Jin, Han
 * @email sjhan@e3ps.com
 */

package e3ps.common.content.fileuploader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import wt.util.WTProperties;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.web.WebUtil;
import ext.ket.shared.log.Kogger;

public class FormUploader {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext context;
    public Vector files;
    public Hashtable formParameters;
    public static boolean writeToFile;
    public static int MaxMemorySize;
    public static File TempDirectory;

    public FormUploader() {
	context = null;
	response = null;
	request = null;
	// files = new Vector();
	formParameters = new Hashtable();
	MaxMemorySize = 20 * 1024 * 1024;
	// TempDirectory = new File(ConfigImpl.getInstance().getString("upload.temp.dir"));
	WTProperties prop = null;
	try {
	    prop = WTProperties.getServerProperties();
	    // prop = WTProperties.getLocalProperties();

	    //
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	}
	TempDirectory = new File(new String(prop.getProperty("wt.temp").getBytes()));
    }

    public FormUploader(HttpServletRequest req, HttpServletResponse res, ServletContext context) {
	this();
	this.context = context;
	this.request = req;
	this.response = res;
    }

    public HttpServletRequest getRequest() {
	return request;
    }

    public HttpServletResponse getResponse() {
	return response;
    }

    public ServletContext getContext() {
	return context;
    }

    public static FormUploader newFormUploader(HttpServletRequest req) {
	FormUploader uploader = new FormUploader(req, null, null);
	return parseRequest(uploader, req);
    }

    public static FormUploader newFormUploader(HttpServletRequest req, HttpServletResponse res, ServletContext context) {
	FormUploader uploader = new FormUploader(req, res, context);
	return parseRequest(uploader, req);
    }

    public static FormUploader parseRequest(FormUploader uploader, HttpServletRequest req) {
	boolean isMultipart = ServletFileUpload.isMultipartContent(req);
	/*
	 * The simplest usage scenario is the following: - Uploaded items should be retained in memory as long as they are reasonably small. - Larger items should be written to a temporary file on
	 * disk. - Very large upload requests should not be permitted. - The built-in defaults for the maximum size of an item to be retained in memory, the maximum permitted size of an upload
	 * request, and the location of temporary files are acceptable.
	 */
	if (!isMultipart) {
	    Hashtable hash = WebUtil.getHttpParams(req);
	    uploader.setFormParameters(hash);
	} else {
	    // Create a factory for disk-based file items
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    // Set factory constraints
	    // factory.setSizeThreshold(MaxMemorySize);
	    // factory.setFileCleaningTracker(null);
	    factory.setRepository(TempDirectory);

	    // Create a new file upload handler
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    // upload.setHeaderEncoding("EUC-KR");
	    upload.setHeaderEncoding("utf-8");

	    // Parse the request
	    try {
		List items = upload.parseRequest(req);
		// Process the uploaded items
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
		    DiskFileItem item = (DiskFileItem) iter.next();

		    if (item.isFormField()) {
			processFormField(item, uploader);
		    } else {
			// if(!item.isInMemory()) continue;
			// if(item.getSize() == 0) continue;

			if ((item.getName() == null) || (item.getName().trim().length() == 0)) {
			    continue;
			}
			processUploadedFile(item, uploader);
		    }
		}
	    } catch (FileUploadException e) {
		Kogger.error(FormUploader.class, e);
	    }
	}
	return uploader;
    }

    public static void processUploadedFile(DiskFileItem item, FormUploader uploader) {
	// Process a file upload
	try {
	    // Kogger.debug(getClass(), "File Path === " + item.getStoreLocation().getPath() + " isExist = " + item.getStoreLocation().exists());
	    // Kogger.debug(getClass(), "pre File size === " + item.getStoreLocation().length());
	    // item.write(item.getStoreLocation());
	    // Kogger.debug(getClass(), "File size === " + item.getStoreLocation().length());
	    //
	    //
	    // WBFile wbfile = WBFile.newWBFile(item);

	    item.write(item.getStoreLocation());

	    WBFile wbfile = new WBFile();

	    wbfile.setContentType(item.getContentType());
	    wbfile.setFieldName(item.getFieldName());

	    wbfile.setFullPathName(item.getName());
	    wbfile.setName(FilenameUtils.getName(item.getName()));

	    File rfile = reWrite(item);

	    wbfile.setSize((int) rfile.length());

	    wbfile.setFile(rfile);

	    Vector v = uploader.getFiles();
	    if (v == null)
		v = new Vector();
	    v.addElement(wbfile);
	    uploader.setFiles(v);
	} catch (Exception e) {
	    Kogger.error(FormUploader.class, e);
	}
    }

    private static File reWrite(DiskFileItem item) {
	BufferedOutputStream outs = null;
	byte b[] = new byte[4096];
	BufferedInputStream fin = null;
	File temFile = null;
	int read = 0;
	try {
	    fin = new BufferedInputStream(new FileInputStream(item.getStoreLocation()));

	    temFile = File.createTempFile("wbsItem", "temp", TempDirectory);
	    outs = new BufferedOutputStream(new FileOutputStream(temFile));
	    while ((read = fin.read(b)) != -1) {
		// Kogger.debug(getClass(), read);
		outs.write(b, 0, read);
		outs.flush();
	    }
	} catch (Exception e) {
	    Kogger.error(FormUploader.class, e);
	} finally {
	    try {
		fin.close();
		outs.close();
	    } catch (IOException e) {
		fin = null;
		outs = null;

		Kogger.error(FormUploader.class, e);
	    }
	} // end try
	return temFile;
    }

    public static void processFormField(DiskFileItem item, FormUploader uploader) {
	// Process a regular form field
	if (item.isFormField()) {
	    String name = item.getFieldName();
	    String value = null;
	    try {
		// value = item.getString("EUC-KR");
		value = item.getString("utf-8");
	    } catch (UnsupportedEncodingException e) {
		Kogger.error(FormUploader.class, e);
	    }
	    Hashtable t = uploader.getFormParameters();
	    Vector vec = getParameterVector(name, t);
	    if (vec != null) {
		vec.add(value);
		t.put(name, vec);
	    } else {
		t.put(name, value);
	    }
	    uploader.setFormParameters(t);
	}
    }

    public Vector getFiles() {
	return files;
    }

    public void setFiles(Vector files) {
	this.files = files;
    }

    public Hashtable getFormParameters() {
	return formParameters;
    }

    public void setFormParameters(Hashtable formParameters) {
	this.formParameters = formParameters;
    }

    public String getFormParameter(String name) {
	Vector parameters = (Vector) this.formParameters.get(name);
	if (parameters == null) {
	    return null;
	}
	return (String) parameters.firstElement();
    }

    public String[] getFormParameters(String name) {
	Object oo = this.formParameters.get(name);
	if (oo == null) {
	    return null;
	}

	String[] paramValues;

	if (oo instanceof Vector) {
	    Vector parameters = (Vector) oo;

	    paramValues = new String[parameters.size()];
	    for (int i = 0; i < parameters.size(); i++) {
		paramValues[i] = (String) parameters.elementAt(i);
	    }

	} else {
	    paramValues = new String[1];
	    paramValues[0] = (String) oo;
	}
	return paramValues;
    }

    private static Vector getParameterVector(String name, Hashtable table) {
	Object o = table.get(name);
	if (o == null)
	    return null;
	if (o instanceof Vector)
	    return (Vector) o;
	if (o instanceof String) {
	    Vector v = new Vector();
	    v.add(o);
	    return v;
	}
	return null;
    }
}
