package e3ps.common.content.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.HolderToContent;
import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.content.remote.ContentDownload;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.AuthUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.dms.entity.KETProjectDocument;
import ext.ket.shared.log.Kogger;

public class DownLoadContentServlet extends CommonServlet {
    /**
     * 
     */
    private static final long  serialVersionUID = 1064122075389805870L;
    public static final String HOLDEROID        = "holderOid";
    public static final String ADOID            = "adOid";
    public static boolean      isDRM            = false;

    static {
	Config conf = ConfigImpl.getInstance();
	isDRM = conf.getBoolean("DRM");
    }

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String holderOid = req.getParameter(HOLDEROID);
	String applicationOid = req.getParameter(ADOID);
	String clientIP = req.getRemoteAddr();

	ContentHolder contentHolder = (ContentHolder) CommonUtil.getObject(holderOid);
	ApplicationData ad = (ApplicationData) CommonUtil.getObject(applicationOid);

	String fileName = ad.getFileName();
	boolean isSecu = true;
	String str = "";
	String msg = "권한이 없습니다.";
	//res.setContentType("text/html;charset=KSC5601");
	//PrintWriter pwriter = res.getWriter();

	if (contentHolder instanceof EPMDocument) {
	    try {
		fileName = wt.epm.util.EPMContentHelper.getContentDisplayName((EPMDocument) contentHolder, ad);

		isSecu = AuthUtil.isContentSecu(holderOid);
		if (!isSecu) {
		    res.setContentType("text/html;charset=KSC5601");
		    PrintWriter pwriter = res.getWriter();
		    str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");" + "\n self.close();" + "\n </script>";
		    str += "\n <script language=\"javascript\">\n history.go(-1)" + "\n </script>";
		    pwriter.println(str);
		    pwriter.close();
		    return;
		}
	    } catch (Exception e) {
		fileName = ad.getFileName();
		Kogger.error(getClass(), e);
	    }
	}
	else if (contentHolder instanceof WTDocument) {

	    if (contentHolder instanceof KETProjectDocument) {
		isSecu = AuthUtil.isContentSecu(holderOid);
		if (!isSecu) {
		    res.setContentType("text/html;charset=KSC5601");
		    PrintWriter pwriter = res.getWriter();
		    str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");" + "\n self.close();" + "\n </script>";
		    str += "\n <script language=\"javascript\">\n history.go(-1)" + "\n </script>";
		    pwriter.println(str);
		    pwriter.close();
		    return;
		}
	    }

	}

	/*
	if(ad.getFileName().startsWith("{$CAD_NAME}") && contentHolder instanceof EPMDocument){
	Kogger.debug(getClass(), "################ EPMDEocument#####################");
		EPMDocument epm = (EPMDocument)contentHolder;
		fileName =epm.getCADName();
	}
	*/
	if (isSecu) {
	    String drmFileName = fileName;

	    //			String fileName = ad.getFileName().replaceAll(" ", "");
	    //			fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
	    // Modified by MJOH, 2011-03-24
	    // 긴 한글파일명 파일을 직접 열기할때 엑셀에서 DDE 오류 발생 처리 & 파일명 깨져서 보이는거 처리도...
	    Kogger.debug(getClass(), "=====> DownLoadContentServlet :: File Name Encoding 1 : " + wt.util.EncodingConverter.escape(fileName));
	    Kogger.debug(getClass(), "=====> DownLoadContentServlet :: File Name Encoding 2 : " + URLEncoder.encode(fileName, "UTF8"));
	    Kogger.debug(getClass(), "=====> DownLoadContentServlet :: File Name Encoding 3 : " + URLEncoder.encode(fileName, "euc-kr"));
	    Kogger.debug(getClass(), "=====> DownLoadContentServlet :: File Name Encoding 4 : " + new String(fileName.getBytes("euc-kr"), "8859_1"));

	    //			fileName = URLEncoder.encode(fileName,"UTF8");
	    fileName = wt.util.EncodingConverter.escape(fileName);
	    //			fileName = new String(fileName.getBytes("euc-kr"), "8859_1"));

	    try {
		ad.setHolderLink(getHolderToContent(contentHolder, ad));
	    } catch (WTPropertyVetoException e1) {
		Kogger.error(getClass(), e1);
	    } catch (Exception e1) {
		Kogger.error(getClass(), e1);
	    }

	    ContentDownload down = new ContentDownload(req);
	    //down.addContentStream((ApplicationData)vector.get(0));
	    down.addContentStream(ad);
	    down.execute();
	    InputStream in = down.getInputStream();
	    Kogger.debug(getClass(), "down.getInputStream() : " + in.available());

	    byte b[] = new byte[4096];
	    //byte b[] = new byte[1024];

	    String strClient = req.getHeader("User-Agent");

	    if (strClient.indexOf("MSIE 5.5") > -1) {
		res.setHeader("Content-Disposition", "filename=" + fileName + ";");
	    }
	    else {
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
	    }  // end if

	    BufferedInputStream fin = null;

	    if (isDRM && E3PSDRMHelper.isDrmApprove(fileName)) {
		try {
		    File file = E3PSDRMHelper.download(in, drmFileName, holderOid, clientIP);
		    fin = new BufferedInputStream(new FileInputStream(file));
		} catch (WTException e1) {
		    Kogger.error(getClass(), e1);
		}
	    }
	    else {
		fin = new BufferedInputStream(in);
	    }

	    BufferedOutputStream outs = new BufferedOutputStream(res.getOutputStream());
	    int read = 0;

	    try {
		while ((read = fin.read(b)) > 0) {
		    outs.write(b, 0, read);
		    outs.flush();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    } finally {
		try {
		    if (outs != null) {
			outs.close();
		    }

		    if (in != null) {
			fin.close();
		    }
		} catch (IOException e) {
		    Kogger.error(getClass(), e);
		}
	    }
	}

    }

    private HolderToContent getHolderToContent(ContentHolder contentHolder, ApplicationData ad) throws Exception {
	QuerySpec spec = new QuerySpec(ApplicationData.class, wt.content.HolderToContent.class);
	spec.appendWhere(new SearchCondition(ApplicationData.class, "thePersistInfo.theObjectIdentifier.id", "=", ad.getPersistInfo().getObjectIdentifier().getId()));

	QueryResult queryresult = PersistenceHelper.manager.navigate(contentHolder, "theContentItem", spec, false);	//(pp, "theContentItem", wt.content.HolderToContent.class, false);

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

	/*		ProcessMasterSchedule pp = (ProcessMasterSchedule)CommonUtil.getObject("e3ps.menufacture.processMgmt.ProcessMasterSchedule:78711");

			ApplicationData ad = (ApplicationData)CommonUtil.getObject("wt.content.ApplicationData:78714");

			QuerySpec spec = new QuerySpec(ApplicationData.class, wt.content.HolderToContent.class);

			spec.appendWhere(new SearchCondition(ApplicationData.class, "thePersistInfo.theObjectIdentifier.id", "=",  ad.getPersistInfo().getObjectIdentifier().getId()));


			QueryResult queryresult = PersistenceHelper.manager.navigate(pp, "theContentItem" , spec, false);//(pp, "theContentItem", wt.content.HolderToContent.class, false);


			Kogger.debug(getClass(), queryresult.size());


			HolderToContent holdertocontent = (HolderToContent)queryresult.nextElement();

			ad.setHolderLink(holdertocontent);

			pp = (ProcessMasterSchedule)ContentHelper.service.getContents(pp);

			Vector vector = ContentHelper.getApplicationData(pp);

			//ad.setHolderLink(pp);
			ContentDownload down = new ContentDownload();
			//down.addContentStream((ApplicationData)vector.get(0));
			down.addContentStream(ad);
			down.execute();

			InputStream in = down.getInputStream();

			byte b[] = new byte[4096];

			Kogger.debug(getClass(), vector.size());

			BufferedInputStream fin = new BufferedInputStream(in);
			int read = 0;

			try {
				while ((read = fin.read(b)) != -1){

					Kogger.debug(getClass(), "....................");

				}
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			} finally {
				//download.done();
				fin.close();
			} // end try
	*/
	//wt.content.ApplicationData%3A78714&ContentHolder=e3ps.menufacture.processMgmt.ProcessMasterSchedule:78711
    }
}
