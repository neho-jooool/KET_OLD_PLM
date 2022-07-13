// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractAIFOperation.java

package e3ps.bom.framework.aif;

import java.util.Vector;

// Referenced classes of package e3ps.bom.framework.aif:
//            InterfaceAIFOperationListener

public abstract class AbstractAIFOperation
    implements Runnable
{

    public AbstractAIFOperation()
    {
        listeners = new Vector();
        startMessage = null;
        abortRequested = false;
        suspended = false;
    }

    public AbstractAIFOperation(String s)
    {
        listeners = new Vector();
        startMessage = null;
        abortRequested = false;
        suspended = false;
        startMessage = s;
    }

    public void addOperationListener(InterfaceAIFOperationListener interfaceaifoperationlistener)
    {
        listeners.addElement(interfaceaifoperationlistener);
    }

    public void cancelOperation()
    {
        for(int i = 0; i < listeners.size(); i++)
        {
            InterfaceAIFOperationListener interfaceaifoperationlistener = (InterfaceAIFOperationListener)listeners.elementAt(i);
            interfaceaifoperationlistener.endOperation();
        }

    }

    protected void execute()
        throws Exception
    {
        execute(true);
    }

    protected void execute(boolean flag)
        throws Exception
    {
        if(flag)
        {
            runEx();
        } else
        {
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    public void executeModal()
        throws Exception
    {
        execute(true);
    }

    public void executeModeless()
    {
        try
        {
            execute(false);
        }
        catch(Exception exception) { }
    }

    public abstract void executeOperation()
        throws Exception;

    public Vector getOperationListeners()
    {
        return listeners;
    }

    public String getStartMessage()
    {
        return startMessage;
    }

    public boolean isAbortRequested()
    {
        return abortRequested;
    }

    public boolean isSuspended()
    {
        return suspended;
    }

    public String objectString()
    {
        String s = toString();
        int i = s.indexOf("[");
        return i > -1 ? s.substring(0, i) : s;
    }

    public void removeOperationListener(Object obj)
    {
        listeners.removeElement(obj);
    }

    public void resume()
    {
        suspended = false;
    }

    public final void run()
    {
        try
        {
            runEx();
        }
        catch(Exception exception) { }
    }

    public void runEx()
        throws Exception
    {
        Exception exception = null;
        Vector vector;
        synchronized(this)
        {
            vector = (Vector)listeners.clone();
        }
        for(int i = 0; i < vector.size(); i++)
        {
            InterfaceAIFOperationListener interfaceaifoperationlistener = (InterfaceAIFOperationListener)vector.elementAt(i);
            interfaceaifoperationlistener.startOperation(startMessage);
        }

        try
        {
            executeOperation();
        }
        catch(Exception exception1)
        {
            exception = exception1;
        }
        for(int j = 0; j < vector.size(); j++)
        {
            InterfaceAIFOperationListener interfaceaifoperationlistener1 = (InterfaceAIFOperationListener)vector.elementAt(j);
            interfaceaifoperationlistener1.endOperation();
        }

        if(exception != null)
            throw exception;
        else
            return;
    }

    public void setAbortRequested(boolean flag)
    {
        abortRequested = flag;
    }

    public void setStartMessage(String s)
    {
        startMessage = s;
    }

    public void suspend()
    {
        suspended = true;
    }

    private Vector listeners;
    protected String startMessage;
    protected boolean abortRequested;
    private boolean suspended;
}
