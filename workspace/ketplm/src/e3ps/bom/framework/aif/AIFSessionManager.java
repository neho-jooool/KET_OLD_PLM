// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AIFSessionManager.java

package e3ps.bom.framework.aif;

import java.util.Vector;

// Referenced classes of package e3ps.bom.framework.aif:
//            AbstractAIFSession

public class AIFSessionManager
{

    public AIFSessionManager()
    {
        sessions = new Vector();
        defaultSession = null;
    }

    public void addSession(AbstractAIFSession abstractaifsession)
    {
        sessions.addElement(abstractaifsession);
    }

    private Vector sessions;
    private AbstractAIFSession defaultSession;
    static Class class$com$framework$aif$AIFSession;
}
