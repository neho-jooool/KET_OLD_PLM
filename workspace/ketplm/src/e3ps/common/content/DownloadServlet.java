/*
 * @(#) DownloadServlet.java  Create on 2006. 4. 28.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.content;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.jdf.log.DownloadLoggerWriter;
import e3ps.common.jdf.log.Logger;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import ext.ket.shared.log.Kogger;

/**
 *
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2006. 4. 28.
 * @since 1.4
 */
public class DownloadServlet extends CommonServlet
{

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        download(req, res);
    }

    private void download(HttpServletRequest req, HttpServletResponse res)
    {
        String oid = req.getParameter("oid");
        String appOid = req.getParameter("appoid");

        ApplicationData appData = (ApplicationData) CommonUtil.getObject(appOid);
        try
        {
            String context = WTProperties.getLocalProperties().getProperty("wt.server.id");
            ContentHolder holder = (ContentHolder) CommonUtil.getObject(oid);
            URL url = ContentHelper.getDownloadURL(holder, appData);

            String downloadUrl = url.getPath() + "?" + url.getQuery();
            downloadUrl = downloadUrl.substring(context.length() + 1, downloadUrl.length());
            String fileName = appData.getFileName();

            char[] c = fileName.toCharArray();
            int limit = 24;
            for (int i = 0; i < c.length; i++)
            {
                if (String.valueOf(c[i]).getBytes().length == 2) limit -= 2;
                else limit -= 1;
                if (limit < 0)
                {
                    fileName = fileName.substring(0, i) + ".." + fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    break;
                }
            }

            if (ConfigImpl.getInstance().getBoolean("log.download.trace", false))
            {
                DownloadLoggerWriter log = new DownloadLoggerWriter();
                WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
                log.println(holder.getClassInfo().getDisplayName(), user, holder, appData);
            }
            Logger.user.println("Parsed FileName = " + fileName);

//            fileName = URLEncoder.encode(fileName, "UTF8");
            // Modified by MJOH, 2011-03-24
            // 긴 한글파일명 파일을 직접 열기할때 엑셀에서 DDE 오류 발생 처리 & 파일명 깨져서 보이는거 처리도...
            fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
//          fileName = javax.mail.internet.MimeUtility.encodeText(fileName, "UTF8", "B");

            Logger.user.println("UTF8 FileName = " + fileName);
            // String contentType = "application";
            // if (fileName.endsWith(".xls"))
            // contentType = "application/vnd.ms-excel";
            // else if (fileName.endsWith(".ppt"))
            // contentType = "application/vnd.ms-powerpoint";
            // else if(fileName.endsWith(".doc"))
            // contentType = "application/msword";

            // _res.setContentType(contentType);
            res.setContentType("application/octet-stream; charset=euc-kr");
            res.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\";");

            this.gotoResult(req, res, downloadUrl);
        }
        catch (IOException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (ServletException e)
        {
            Kogger.error(getClass(), e);
        }
    }

}
