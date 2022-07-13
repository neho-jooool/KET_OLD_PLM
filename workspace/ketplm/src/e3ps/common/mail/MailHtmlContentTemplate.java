/**
 * @(#) MailHtmlContentTemplate.java
 * Copyright (c) e3ps. All rights reserverd
 *
 *	@version 1.00
 *	@since jdk 1.4.02
 *	@createdate 2005. 11. 22.
 *	@author park, jai-sik
 *	@desc
 */
package e3ps.common.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import org.apache.commons.lang.StringEscapeUtils;

import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import ext.ket.shared.log.Kogger;

public class MailHtmlContentTemplate {
    protected java.util.Hashtable          args               = new java.util.Hashtable();
    private String                         htmlTemplateSource = null;
    public static Config                   conf               = ConfigImpl.getInstance();
    public static String                   TEMPDIR            = conf.getString("mail.temp.dir");

    private static MailHtmlContentTemplate instance           = null;

    public static MailHtmlContentTemplate getInstance() {
	if (instance == null) {
	    instance = new MailHtmlContentTemplate();
	}

	return instance;
    }

    public String htmlContent(Hashtable hash, String template) {
	String htmlContent = "";
	try {
	    args = hash;

	    if (template == null || template.equals("")) {
		template = "mail_notice.html";
	    }
	    setHtmlTemplate(template);

	    if (htmlTemplateSource != null) {
		htmlContent = parseTemplate(htmlTemplateSource);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return htmlContent;
    }

    public void setHtmlTemplate(String template) {
	BufferedReader in = null;

	try {

	    File file = new File(TEMPDIR, template);
	    //Kogger.debug(getClass(), "html template dir : " + file.getAbsolutePath());

	    //            in = new java.io.BufferedReader(new java.io.FileReader(file));
	    /*
	     * [PLM System 1차개선]
	     * 수정내용 : 메일 템플릿을 utf-8 형식으로 가져오도록 수정
	     * 수정일자 : 2013. 8.2
	     * 수정자 : 김종호
	     */
	    in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
	    StringBuffer buf = new StringBuffer();
	    String line;
	    while ((line = in.readLine()) != null) {
		buf.append(line + "\n");
	    }

	    htmlTemplateSource = buf.toString();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (in != null) in.close();
	    } catch (Exception e) {

	    }
	}
    }

    /**
     * @return java.lang.String
     * @param s
     *            java.lang.String
     */
    private String parseTemplate(String s) {
	StringBuffer content = new StringBuffer();
	try {
	    while (s.length() > 0) {
		int position = s.indexOf("<@");
		if (position == -1) {
		    content.append(s);
		    break;
		}

		if (position != 0) content.append(s.substring(0, position));

		if (s.length() == position + 2) break;
		String remainder = s.substring(position + 2);

		int markEndPos = remainder.indexOf(">");
		if (markEndPos == -1) break;

		String argname = remainder.substring(0, markEndPos).trim();
		String value = (String) args.get(argname);
		if (value != null) {
		    if (value.indexOf("<") != -1) content.append(StringEscapeUtils.unescapeHtml(value));
		    else content.append(StringEscapeUtils.escapeHtml(value));
		}
		if (remainder.length() == markEndPos + 1) break;

		s = remainder.substring(markEndPos + 1);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return content.toString();
    }

    public void setArg(String name, String value) {
	args.put(name, value);
    }
}
