// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BorderList.java

package e3ps.bom.common.util;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

// Referenced classes of package e3ps.bom.common.util:
//            BlackTitledBorder

public class BorderList
{

    public BorderList()
    {
    }

    public static final Border emptyBorder0;
    public static final Border emptyBorder1;
    public static final Border emptyBorder3;    
    public static final Border emptyBorder5;

	public final static Border loweredBorder;
	public final static Border raisedBorder;

    static 
    {
		emptyBorder0 = new EmptyBorder(0, 0, 0, 0);
        emptyBorder1 = new EmptyBorder(1, 1, 1, 1);
        emptyBorder3 = new EmptyBorder(3, 3, 3, 3);
        emptyBorder5 = new EmptyBorder(5, 5, 5, 5);

		loweredBorder = new EtchedBorder(EtchedBorder.LOWERED);
		raisedBorder = new EtchedBorder(EtchedBorder.RAISED);
    }
}
