// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractAIFCommand.java

package e3ps.bom.framework.aif;

import ext.ket.shared.log.Kogger;


public abstract class AbstractAIFCommand
    implements Runnable
{

    public AbstractAIFCommand()
    {
        runnable = null;
    }

    protected void executeCommand()
        throws Exception
    {
        if(runnable != null)
            runnable.run();
    }

    public void executeModal()
        throws Exception
    {
        executeCommand();
    }

    public void executeModeless()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    protected Runnable getRunnable()
    {
        return runnable;
    }

    public final void run()
    {
        try
        {
            executeCommand();
        }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
        }
    }

    protected void setRunnable(Runnable runnable1)
    {
        runnable = runnable1;
    }

    private Runnable runnable;
}
