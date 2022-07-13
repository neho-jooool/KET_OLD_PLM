// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AIFSessionOperationManager.java

package e3ps.bom.framework.aif;

import java.util.Enumeration;
import java.util.Vector;

import e3ps.bom.framework.util.log.Debug;
import e3ps.bom.framework.util.log.Timing;
import ext.ket.shared.log.Kogger;

// Referenced classes of package e3ps.bom.framework.aif:
//            AbstractAIFOperation, AbstractAIFSession

class AIFSessionOperationManager
{
    private class OperationWrapper extends Thread
    {

        public AbstractAIFOperation getOperation()
        {
            return operation;
        }

        public String getOperationStartMessage()
        {
            return operation.getStartMessage();
        }

        public String getPreviousSessionStatus()
        {
            return previousSessionStatus;
        }

        public boolean isModal()
        {
            return modal;
        }

        public boolean isSuspended()
        {
            return operation.isSuspended();
        }

        private String objectString()
        {
            String s = toString();
            int i = s.indexOf("[");
            return (i > -1 ? s.substring(0, i) : s) + " " + operation.objectString();
        }

        public void run()
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
            if(!performOperation)
                return;
            long l = Timing.start(AIFSessionOperationManager.TIMING_TAG);
            manager.operationRunning();
            if(operation.getStartMessage() != null)
            {
                previousSessionStatus = session.getStatus();
                session.setStatus(operation.getStartMessage());
            }
            try
            {
                operation.runEx();
            }
            catch(Exception exception1)
            {
                exception = exception1;
            }
            manager.completedOperationWrapper(this);
            Timing.stop(l, AIFSessionOperationManager.TIMING_TAG, operation.getClass().getName());
            if(exception != null)
                throw exception;
            else
                return;
        }

        public void setPerformOperation(boolean flag)
        {
            performOperation = flag;
        }

        private AIFSessionOperationManager manager;
        private AbstractAIFOperation operation;
        private boolean modal;
        private boolean performOperation;
        private String previousSessionStatus;


        public OperationWrapper(AIFSessionOperationManager aifsessionoperationmanager1, AbstractAIFOperation abstractaifoperation)
        {
            manager = null;
            operation = null;
            modal = false;
            performOperation = true;
            previousSessionStatus = null;
            manager = aifsessionoperationmanager1;
            operation = abstractaifoperation;
        }

        public OperationWrapper(AIFSessionOperationManager aifsessionoperationmanager1, AbstractAIFOperation abstractaifoperation, boolean flag)
        {
            manager = null;
            operation = null;
            modal = false;
            performOperation = true;
            previousSessionStatus = null;
            manager = aifsessionoperationmanager1;
            operation = abstractaifoperation;
            modal = flag;
            performOperation = modal ^ true;
        }
    }


    protected AIFSessionOperationManager()
    {
        session = null;
        concurrentOperationCount = 100;
        operationList = new Vector();
        pendingOperations = new Vector();
        setOperationConcurrencyCount((short)1);
    }

    public void abortOperations()
    {
        pendingOperations.removeAllElements();
        Vector vector = null;
        synchronized(this)
        {
            vector = (Vector)operationList.clone();
        }
        Object obj = null;
        for(int i = 0; i < vector.size(); i++)
        {
            OperationWrapper operationwrapper = (OperationWrapper)vector.elementAt(i);
            operationwrapper.getOperation().setAbortRequested(true);
        }

    }

    private void completedOperationWrapper(OperationWrapper operationwrapper)
    {
        synchronized(this)
        {
            operationList.removeElement(operationwrapper);
            if(Debug.isOn("operation"))
                Debug.println("[" + operationwrapper.objectString() + "] removed from operation list (COMPLETED)");
        }
        processNextOperationWrapper();
        if(operationwrapper.getOperationStartMessage() != null)
            session.setStatus(operationwrapper.getPreviousSessionStatus());
    }

    public short getConcurrentOperationCount()
    {
        return concurrentOperationCount;
    }

    public AbstractAIFOperation getCurrentOperation()
    {
        AbstractAIFOperation abstractaifoperation = null;
        try
        {
            OperationWrapper operationwrapper = (OperationWrapper)operationList.firstElement();
            abstractaifoperation = operationwrapper.getOperation();
        }
        catch(Exception exception) { }
        return abstractaifoperation;
    }

    void initialize(AbstractAIFSession abstractaifsession)
    {
        session = abstractaifsession;
    }

    public boolean isIdle()
    {
        boolean flag = true;
        synchronized(this)
        {
            short word0 = (short)pendingOperations.size();
            short word1 = (short)operationList.size();
            if(word1 < concurrentOperationCount)
            {
                flag = true;
            } else
            {
                short word2 = 0;
                for(short word3 = 0; word3 < word1; word3++)
                {
                    OperationWrapper operationwrapper = (OperationWrapper)operationList.elementAt(word3);
                    if(!operationwrapper.isSuspended())
                        word2++;
                }

                flag = word2 < concurrentOperationCount;
            }
        }
        return flag;
    }

    private void operationRunning()
    {
        session.evaluateState();
    }

    void performOperation(AbstractAIFOperation abstractaifoperation)
        throws Exception
    {
        if(Debug.isOn("operation"))
            Debug.println("[" + abstractaifoperation.objectString() + "] performing");
        performOperationWrapper(new OperationWrapper(this, abstractaifoperation, true));
        if(Debug.isOn("operation"))
            Debug.println("[" + abstractaifoperation.objectString() + "] performed");
    }

    void performOperationWrapper(OperationWrapper operationwrapper)
        throws Exception
    {
        queueOperationWrapper(operationwrapper);
        operationwrapper.join();
        operationwrapper.setPerformOperation(true);
        operationwrapper.runEx();
    }

    private boolean processNextOperationWrapper()
    {
        OperationWrapper operationwrapper = null;
        boolean flag = false;
        if(isIdle())
            synchronized(this)
            {
                if(pendingOperations.size() > 0)
                {
                    operationwrapper = (OperationWrapper)pendingOperations.elementAt(0);
                    pendingOperations.removeElementAt(0);
                    operationList.addElement(operationwrapper);
                }
            }
        if(operationwrapper != null)
        {
            if(Debug.isOn("operation"))
                Debug.println("Processing [" + operationwrapper.objectString() + "] from pending operation list");
            processOperationWrapper(operationwrapper);
        } else
        {
            Debug.println("operation", "No more operations to process");
            session.evaluateState();
            if(!session.hasComponentEventsSuspended())
                synchronized(session)
                {
                    Debug.println("dispatcher", "Notify waiting threads (monitor is session object)");
                    session.notifyAll();
                }
            else
            if(Debug.isOn("dispatcher"))
                Debug.println("Session has suspended events, DO NOT notify waiting threads");
        }
        return operationwrapper != null;
    }

    private void processOperationWrapper(OperationWrapper operationwrapper)
    {
        if(Debug.isOn("operation"))
            Debug.println("[" + operationwrapper.objectString() + "] started");
        operationwrapper.start();
    }

    void queueOperation(AbstractAIFOperation abstractaifoperation)
    {
        try
        {
            if(Debug.isOn("operation"))
                Debug.println("[" + abstractaifoperation.objectString() + "] queueing");
            queueOperationWrapper(new OperationWrapper(this, abstractaifoperation));
            if(Debug.isOn("operation"))
                Debug.println("[" + abstractaifoperation.objectString() + "] queued");
        }
        catch(Exception exception)
        {
            if(Debug.isOn("exception,operation"))
            {
                Debug.println("Caught exception in queueOperation()");
                Debug.printStackTrace("exception", exception);
            }
            Kogger.error(getClass(), exception);
        }
    }

    private void queueOperationWrapper(OperationWrapper operationwrapper)
    {
        synchronized(this)
        {
            pendingOperations.addElement(operationwrapper);
            if(Debug.isOn("operation"))
                Debug.println("[" + operationwrapper.objectString() + "] added to pending operation list");
        }
        boolean flag = processNextOperationWrapper();
    }

    void resumeOperation(AbstractAIFOperation abstractaifoperation)
    {
        if(Debug.isOn("operation"))
            Debug.println("[" + abstractaifoperation.objectString() + "] resuming");
        boolean flag = false;
        synchronized(this)
        {
            for(Enumeration enumeration = operationList.elements(); enumeration.hasMoreElements();)
            {
                OperationWrapper operationwrapper = (OperationWrapper)enumeration.nextElement();
                if(operationwrapper.getOperation() == abstractaifoperation)
                {
                    flag = true;
                    break;
                }
            }

        }
        if(flag)
        {
            abstractaifoperation.resume();
            if(Debug.isOn("operation"))
                Debug.println("[" + abstractaifoperation.objectString() + "] resumed");
            session.evaluateState();
        } else
        if(Debug.isOn("operation"))
            Debug.println("[" + abstractaifoperation.objectString() + "] NOT resumed");
    }

    public void setOperationConcurrencyCount(short word0)
    {
        if(word0 > 1)
            concurrentOperationCount = word0;
        else
            concurrentOperationCount = 1;
    }

    void suspendOperation(AbstractAIFOperation abstractaifoperation)
    {
        if(Debug.isOn("operation"))
            Debug.println("[" + abstractaifoperation.objectString() + "] suspending");
        boolean flag = false;
        synchronized(this)
        {
            for(Enumeration enumeration = operationList.elements(); enumeration.hasMoreElements();)
            {
                OperationWrapper operationwrapper = (OperationWrapper)enumeration.nextElement();
                if(operationwrapper.getOperation() == abstractaifoperation)
                {
                    flag = true;
                    break;
                }
            }

        }
        if(flag)
        {
            abstractaifoperation.suspend();
            if(Debug.isOn("operation"))
                Debug.println("[" + abstractaifoperation.objectString() + "] suspended");
            boolean flag1 = processNextOperationWrapper();
        } else
        if(Debug.isOn("operation"))
            Debug.println("[" + abstractaifoperation.objectString() + "] NOT suspended");
    }

    private AbstractAIFSession session;
    private short concurrentOperationCount;
    private Vector operationList;
    private Vector pendingOperations;
    private static String TIMING_TAG = "operations";





}
