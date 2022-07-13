/*
 * @(#) ApplicationDataFileUtil.java  Create on 2006. 08. 25
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.content;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentServerHelper;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.util.WTException;
import wt.util.WTProperties;
import ext.ket.shared.log.Kogger;
 
/**
 * ApplicationData를 File로 생성한다.
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2006. 08. 25
 * @since 1.4
 */
public class ApplicationDataFileUtil implements RemoteAccess
{
    public static File[] getFile(ContentHolder holder)
    {
        if (!RemoteMethodServer.ServerFlag)
        {
            try
            {
                RemoteMethodServer rms = RemoteMethodServer.getDefault();
                Class[] argTypes = { ContentHolder.class };
                Object[] argValues = { holder };
                return (File[]) rms.invoke("getFile", "e3ps.common.content.ApplicationDataFileUtil", null, argTypes, argValues);
            }
            catch (RemoteException e)
            {
                Kogger.error(ApplicationDataFileUtil.class, e);
            }
            catch (InvocationTargetException e)
            {
                Kogger.error(ApplicationDataFileUtil.class, e);
            }
        }

        try
        {
            holder = ContentHelper.service.getContents(holder);
            Vector list = ContentHelper.getContentListAll(holder);
            if (list == null && list.size() == 0) return null;

            File[] files = new File[list.size()];
            byte[] buffer = new byte[1024];
            String tempDir = WTProperties.getLocalProperties().getProperty("wt.temp");

            for (int i = 0; i < list.size(); i++)
            {
                ContentItem temp = (ContentItem) list.get(i);
                if (temp instanceof ApplicationData)
                {
                    ApplicationData adata = (ApplicationData) temp;

                    InputStream is = ContentServerHelper.service.findContentStream(adata);

                    files[i] = new File(tempDir + File.separator + adata.getFileName());
                    FileOutputStream fos = new FileOutputStream(files[i]);
                    int j = 0;
                    while ((j = is.read(buffer, 0, 1024)) > 0)
                        fos.write(buffer, 0, j);
                    
                    fos.close();
                    is.close();
                }
            }

            return files;
        }
        catch (WTException e)
        {
            Kogger.error(ApplicationDataFileUtil.class, e);
        }
        catch (PropertyVetoException e)
        {
            Kogger.error(ApplicationDataFileUtil.class, e);
        }
        catch (IOException e)
        {
            Kogger.error(ApplicationDataFileUtil.class, e);
        }

        return null;
    }
}
