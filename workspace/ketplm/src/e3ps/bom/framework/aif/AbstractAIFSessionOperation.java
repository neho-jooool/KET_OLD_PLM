// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractAIFSessionOperation.java

package e3ps.bom.framework.aif;


// Referenced classes of package e3ps.bom.framework.aif:
//            AbstractAIFOperation, AbstractAIFSession

public abstract class AbstractAIFSessionOperation extends AbstractAIFOperation
{

    public AbstractAIFSessionOperation(AbstractAIFSession abstractaifsession)
    {
        session = null;
        session = abstractaifsession;
    }

    public AbstractAIFSessionOperation(AbstractAIFSession abstractaifsession, String s)
    {
        super(s);
        session = null;
        session = abstractaifsession;
    }

    public void execute()
        throws Exception
    {
        super.execute();
    }

    public void execute(boolean flag)
        throws Exception
    {
        super.execute(flag);
    }

    protected AbstractAIFSession session;
}
