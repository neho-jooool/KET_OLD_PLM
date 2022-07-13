// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransmitLog.java

package e3ps.bom.common.util;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import ext.ket.shared.log.Kogger;

public class TransmitLog
{

    public TransmitLog()
    {
    }

    public static void addLogger(String serverCodeBase, Throwable log)
    {
        try
        {
            String targetURL = serverCodeBase + "servlet/ext.lge.cpc.bomcol.util.LogListener";
            URL url = new URL(targetURL);
            URLConnection urlCo = url.openConnection();
            urlCo.setDoOutput(true);
            urlCo.setDoInput(true);
            urlCo.setAllowUserInteraction(false);
            String llog = log.toString() + "\n";
            StackTraceElement st[] = log.getStackTrace();
            for(int i = 0; i < st.length; i++)
                llog = llog + "\tat " + st[i] + "\n";

            OutputStream os = urlCo.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(llog);
            close(os, osw, bw);
            InputStream is = urlCo.getInputStream();
            is.close();
        }
        catch(Exception e)
        {
            Kogger.error(TransmitLog.class, e);
        }
    }

    private static void close(OutputStream os, OutputStreamWriter ows, BufferedWriter bw)
    {
        try
        {
            if(bw != null)
                bw.close();
            if(ows != null)
                ows.close();
            if(os != null)
                os.close();
        }
        catch(Exception e)
        {
            Kogger.error(TransmitLog.class, e);
        }
    }
}
