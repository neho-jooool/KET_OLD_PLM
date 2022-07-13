// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   ScreenCenterer.java

package e3ps.edm.clients.batch.util;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

//import java.awt.;

public class ScreenCenterer
{

    public ScreenCenterer()
    {
    }

    public Dimension getCenterDim(Dialog dlg)
    {
        Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimDlg = dlg.getSize();
        if(dimDlg.height > dimScreenSize.height)
            dimDlg.height = dimScreenSize.height;
        if(dimDlg.width > dimScreenSize.width)
            dimDlg.width = dimScreenSize.width;
        dimCenter = new Dimension((dimScreenSize.width - dimDlg.width) / 2, (dimScreenSize.height - dimDlg.height) / 2);
        return dimCenter;
    }

    public Dimension getCenterDim(Frame fr)
    {
        Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimFr = fr.getSize();
        if(dimFr.height > dimScreenSize.height)
            dimFr.height = dimScreenSize.height;
        if(dimFr.width > dimScreenSize.width)
            dimFr.width = dimScreenSize.width;
        dimCenter = new Dimension((dimScreenSize.width - dimFr.width) / 2, (dimScreenSize.height - dimFr.height) / 2);
        return dimCenter;
    }

    Dimension dimCenter;
}
