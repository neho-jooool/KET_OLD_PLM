// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlackTitledBorder.java

package e3ps.bom.common.util;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

// Referenced classes of package e3ps.bom.common.util:
//            FontList

public class BlackTitledBorder extends TitledBorder
{

    public BlackTitledBorder(String title)
    {
        super(new EtchedBorder(), title);
        setTitleColor(Color.black);
        setTitleFont(FontList.regularFont);
    }
}
