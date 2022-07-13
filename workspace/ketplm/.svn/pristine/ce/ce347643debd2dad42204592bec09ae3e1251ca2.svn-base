package ext.ket.common.files.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import wt.util.WTProperties;

public class DownloadView extends AbstractView {

    private static final Logger LOGGER = Logger.getLogger(DownloadView.class);
    public final static String CODEBASEPATH;
    public final static String TEMPPATH;

    static {

	String tempDir = "";
	String codebaseDir = "";
	try {

	    WTProperties wtproperties = WTProperties.getLocalProperties();
	    codebaseDir = wtproperties.getProperty("wt.codebase.location", "");
	    tempDir = codebaseDir + File.separator + "temp";

	    File dirChk = new File(tempDir);
	    if (!dirChk.exists())
		dirChk.mkdir();

	} catch (IOException e) {
	    e.printStackTrace();
	}

	TEMPPATH = tempDir;
	CODEBASEPATH = codebaseDir;
    }

    public void Download() {

	setContentType("application/download; utf-8");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res) throws Exception {
	// TODO Auto-generated method stub
	File file = (File) model.get("downloadFile");
	LOGGER.info("DownloadView --> file.getPath() : " + file.getPath());
	LOGGER.info("DownloadView --> file.getName() : " + file.getName());

	res.setContentType(getContentType());
	res.setContentLength((int) file.length());

	String userAgent = req.getHeader("User-Agent");

	boolean ie = userAgent.indexOf("MSIE") > -1;

	String fileName = null;

	if (ie) {

	    // fileName = URLEncoder.encode(file.getName(), "utf-8");
	    fileName = URLEncoder.encode(file.getName(), "utf-8").replaceAll("\\+", "%20");

	} else if (userAgent.indexOf("Chrome") > -1) {
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < file.getName().length(); i++) {
		char c = file.getName().charAt(i);
		if (c > '~') {
		    sb.append(URLEncoder.encode("" + c, "UTF-8"));
		} else {
		    sb.append(c);
		}
	    }
	    fileName = sb.toString();
	} else {

	    fileName = new String(file.getName().getBytes("utf-8"));

	}

	res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");

	res.setHeader("Content-Transfer-Encoding", "binary");

	OutputStream out = res.getOutputStream();

	FileInputStream fis = null;

	try {

	    fis = new FileInputStream(file);

	    FileCopyUtils.copy(fis, out);

	} catch (Exception e) {

	    e.printStackTrace();

	} finally {

	    if (fis != null) {

		try {
		    fis.close();
		} catch (Exception e) {
		}
	    }

	}

	out.flush();
    }
}