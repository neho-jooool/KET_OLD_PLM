package e3ps.common.content.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.HolderToContent;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTPropertyVetoException;
import e3ps.common.content.remote.ContentDownload;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import ext.ket.shared.log.Kogger;

public class DownLoadContentServletLocal extends CommonServlet {
    public static final String HOLDEROID = "holderOid";
    public static final String ADOID = "adOid";

    public static boolean isDRM = false;

    static {
	Config conf = ConfigImpl.getInstance();
	isDRM = conf.getBoolean("DRM");
    }

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String holderOid = req.getParameter(HOLDEROID);
	String applicationOid = req.getParameter(ADOID);

	Kogger.debug(getClass(), "applicationOid ========> " + applicationOid);
	ContentHolder contentHolder = (ContentHolder) CommonUtil.getObject(holderOid);
	ApplicationData ad = (ApplicationData) CommonUtil.getObject(applicationOid);

	Kogger.debug(getClass(), "ad ===> " + ad);
	String fileName = ad.getFileName();
	Kogger.debug(getClass(), "FileName1 =" + ad.getFileName());
	if (ad.getFileName().startsWith("{$CAD_NAME}") && contentHolder instanceof EPMDocument) {
	    Kogger.debug(getClass(), "################ EPMDEocument#####################");
	    EPMDocument epm = (EPMDocument) contentHolder;
	    fileName = epm.getCADName();
	}

	String utf8FileName = fileName;
	// Kogger.debug(getClass(),  "FileName2 =" + fileName);
	// String fileName = ad.getFileName().replaceAll(" ", "");
	fileName = new String(fileName.getBytes("euc-kr"), "8859_1");

	// fileName = URLEncoder.encode(fileName,"UTF8");
	// Kogger.debug(getClass(), fileName);
	try {
	    ad.setHolderLink(getHolderToContent(contentHolder, ad));
	} catch (WTPropertyVetoException e1) {
	    Kogger.error(getClass(), e1);
	} catch (Exception e1) {
	    Kogger.error(getClass(), e1);
	}

	ContentDownload down = new ContentDownload(req);
	// down.addContentStream((ApplicationData)vector.get(0));
	down.addContentStream(ad);
	down.execute();
	InputStream in = down.getInputStream();

	byte b[] = new byte[4096];
	// byte b[] = new byte[1024];
	String strClient = req.getHeader("User-Agent");
	if (strClient.indexOf("MSIE 5.5") > -1) {
	    res.setHeader("Content-Disposition", "filename=" + fileName + ";");
	} else {
	    res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
	} // end if

	BufferedInputStream fin = null;
	fin = new BufferedInputStream(in);

	BufferedOutputStream outs = new BufferedOutputStream(res.getOutputStream());
	int read = 0;
	try {
	    while ((read = fin.read(b)) > 0) {
		outs.write(b, 0, read);
		outs.flush();
	    }

	    // outs.flush();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (outs != null)
		    outs.close();
		if (in != null)
		    fin.close();
	    } catch (IOException e) {
		Kogger.error(getClass(), e);
	    }
	} // end try
    }

    private HolderToContent getHolderToContent(ContentHolder contentHolder, ApplicationData ad) throws Exception {

	QuerySpec spec = new QuerySpec(ApplicationData.class, wt.content.HolderToContent.class);

	spec.appendWhere(new SearchCondition(ApplicationData.class, "thePersistInfo.theObjectIdentifier.id", "=", ad.getPersistInfo().getObjectIdentifier().getId()));

	QueryResult queryresult = PersistenceHelper.manager.navigate(contentHolder, "theContentItem", spec, false);// (pp, "theContentItem", wt.content.HolderToContent.class, false);

	Kogger.debug(getClass(), queryresult.size());

	HolderToContent holdertocontent = (HolderToContent) queryresult.nextElement();

	return holdertocontent;
    }

    private String replaceMsg(String msg) {
	msg = msg.replaceAll("\"", "&quot;");
	return msg.replaceAll("\n", " ");
    }

    public static byte[] getBytesFromFile(File file) {

	// Get the size of the file
	long length = file.length();

	// You cannot create an array using a long type.
	// It needs to be an int type.
	// Before converting to an int type, check
	// to ensure that file is not larger than Integer.MAX_VALUE.
	if (length > Integer.MAX_VALUE) {
	    // File is too large
	}

	// Create the byte array to hold the data
	byte[] bytes = new byte[(int) length];

	try {
	    InputStream is = new FileInputStream(file);
	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
		offset += numRead;
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
		throw new IOException("Could not completely read file " + file.getName());
	    }

	    // Close the input stream and return bytes
	    is.close();
	} catch (IOException ioe) {
	    Kogger.error(DownLoadContentServlet.class, ioe);
	}
	return bytes;
    }

    public static void main(String args[]) throws Exception {

	/*
	 * ProcessMasterSchedule pp = (ProcessMasterSchedule)CommonUtil.getObject("e3ps.menufacture.processMgmt.ProcessMasterSchedule:78711");
	 * 
	 * ApplicationData ad = (ApplicationData)CommonUtil.getObject("wt.content.ApplicationData:78714");
	 * 
	 * QuerySpec spec = new QuerySpec(ApplicationData.class, wt.content.HolderToContent.class);
	 * 
	 * spec.appendWhere(new SearchCondition(ApplicationData.class, "thePersistInfo.theObjectIdentifier.id", "=", ad.getPersistInfo().getObjectIdentifier().getId()));
	 * 
	 * QueryResult queryresult = PersistenceHelper.manager.navigate(pp, "theContentItem" , spec, false);//(pp, "theContentItem", wt.content.HolderToContent.class, false);
	 * 
	 * Kogger.debug(getClass(), queryresult.size());
	 * 
	 * HolderToContent holdertocontent = (HolderToContent)queryresult.nextElement();
	 * 
	 * ad.setHolderLink(holdertocontent);
	 * 
	 * pp = (ProcessMasterSchedule)ContentHelper.service.getContents(pp);
	 * 
	 * Vector vector = ContentHelper.getApplicationData(pp);
	 * 
	 * //ad.setHolderLink(pp); ContentDownload down = new ContentDownload(); //down.addContentStream((ApplicationData)vector.get(0)); down.addContentStream(ad); down.execute();
	 * 
	 * InputStream in = down.getInputStream();
	 * 
	 * byte b[] = new byte[4096];
	 * 
	 * Kogger.debug(getClass(), vector.size());
	 * 
	 * BufferedInputStream fin = new BufferedInputStream(in); int read = 0;
	 * 
	 * try { while ((read = fin.read(b)) != -1){
	 * 
	 * Kogger.debug(getClass(), "...................."); } } catch (Exception e) { Kogger.error(getClass(), e); } finally { //download.done(); fin.close(); } // end try
	 */
	// wt.content.ApplicationData%3A78714&ContentHolder=e3ps.menufacture.processMgmt.ProcessMasterSchedule:78711
    }
}
