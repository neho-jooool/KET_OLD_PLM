// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SignalOnClose.java

package e3ps.bom.framework.util;

import java.awt.Container;

// Referenced classes of package e3ps.bom.framework.util:
//            InterfaceSignalOnClose

public class SignalOnClose
{

    public SignalOnClose()
    {
    }

    public static void close(Container container)
    {
        java.awt.Component acomponent[] = container.getComponents();
        if(container instanceof InterfaceSignalOnClose)
            ((InterfaceSignalOnClose)container).closeSignaled();
        if(acomponent == null)
            return;
        for(int i = 0; i < acomponent.length; i++)
            if(acomponent[i] instanceof Container)
                close((Container)acomponent[i]);
            else
            if(acomponent[i] instanceof InterfaceSignalOnClose)
                ((InterfaceSignalOnClose)acomponent[i]).closeSignaled();

    }
}
