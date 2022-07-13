// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractAIFSession.java

package e3ps.bom.framework.aif;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.MissingResourceException;
import java.util.Vector;

import javax.swing.SwingUtilities;

import e3ps.bom.framework.util.Registry;
import e3ps.bom.framework.util.log.Debug;
import ext.ket.shared.log.Kogger;

// Referenced classes of package e3ps.bom.framework.aif:
//            AIFSessionOperationManager, AbstractAIFCommand, AIFSessionManager, AbstractAIFOperation

public abstract class AbstractAIFSession
{

    public AbstractAIFSession()
    {
        userName = new String("anonymous");
        aifBaseURL = null;
        loggedIn = false;
        sessionManager = null;
        registry = null;
        propChangeListeners = new Vector();
        connectionEventListeners = new Vector();
        status = null;
        operationManager = null;
        operationSuspended = false;
        referenceCount = 0;
        oldIdleState = true;
        autoLogout = true;
        sessionID = new String("ID");
        componentEventSuspended = false;
        try
        {
            registry = Registry.getRegistry(this);
        }
        catch(MissingResourceException ex)
        {
            Kogger.error(getClass(), "No resource associated with this class [" + getClass().getName() + "]");
        }
        String s = "OPERATION_MANAGER";
        Object obj = null;
        if(registry.getString(s, null) != null)
            obj = registry.newInstanceFor(s);
        if(obj != null)
            operationManager = (AIFSessionOperationManager)obj;
        else
            operationManager = new AIFSessionOperationManager();
        operationManager.initialize(this);
    }

    public void abortOperations()
    {
        operationManager.abortOperations();
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        if(!propChangeListeners.contains(propertychangelistener))
            propChangeListeners.addElement(propertychangelistener);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener propertychangelistener, boolean flag)
    {
        if(!propChangeListeners.contains(propertychangelistener))
        {
            propChangeListeners.addElement(propertychangelistener);
            if(flag)
            {
                firePropertyChangeEvent(propertychangelistener, "STATUS", status, status);
                Boolean boolean1 = new Boolean(isIdle());
                firePropertyChangeEvent(propertychangelistener, "STATE", boolean1, boolean1);
            }
        }
    }

    public void addReference()
    {
        referenceCount++;
    }

    void evaluateState()
    {
        final boolean newIdleState = isIdle();
        if(oldIdleState != newIdleState)
        {
            oldIdleState = newIdleState;
            SwingUtilities.invokeLater(new Runnable() {

                public void run()
                {
                    firePropertyChangeEvent("STATE", new Boolean(newIdleState ^ true), new Boolean(newIdleState));
                }

            });
        }
    }

    private void firePropertyChangeEvent(PropertyChangeListener propertychangelistener, String s, Object obj, Object obj1)
    {
        PropertyChangeEvent propertychangeevent = new PropertyChangeEvent(this, s, obj, obj1);
        propertychangelistener.propertyChange(propertychangeevent);
    }

    private void firePropertyChangeEvent(String s, Object obj, Object obj1)
    {
        PropertyChangeEvent propertychangeevent = new PropertyChangeEvent(this, s, obj, obj1);
        Vector vector;
        synchronized(this)
        {
            vector = (Vector)propChangeListeners.clone();
        }
        int i = vector.size();
        for(int j = 0; j < i; j++)
        {
            PropertyChangeListener propertychangelistener = (PropertyChangeListener)vector.elementAt(j);
            propertychangelistener.propertyChange(propertychangeevent);
        }

    }

    public AbstractAIFOperation getCurrentOperation()
    {
        return operationManager.getCurrentOperation();
    }

    public String getDescription()
    {
        return userName + registry.getString("sessionDescriptionUserToIDDelimiter", ":") + sessionID;
    }

    public AbstractAIFCommand getLoginCommand()
        throws Exception
    {
        return getLoginCommand(null);
    }

    public AbstractAIFCommand getLoginCommand(Object aobj[])
        throws Exception
    {
        String s = "LOGIN_COMMAND";
        if(getRegistry().getString(s, null) == null)
            throw new Exception(getRegistry().getString("noLoginCommand", "No Login command registered for this session") + " [" + getClass().getName() + "]");
        Object obj = null;
        if(aobj == null)
            obj = getRegistry().newInstanceForEx(s, this);
        else
            obj = getRegistry().newInstanceForEx(s, aobj);
        if(!(obj instanceof AbstractAIFCommand))
            throw new Exception("[" + obj.getClass().getName() + "] " + getRegistry().getString("notAIFCommand", "is not an instance of an AbstractAIFCommand!"));
        else
            return (AbstractAIFCommand)obj;
    }

    public AbstractAIFCommand getOpenCommand()
        throws Exception
    {
        return getOpenCommand(null);
    }

    public AbstractAIFCommand getOpenCommand(Object aobj[])
        throws Exception
    {
        String s = "OPEN_COMMAND";
        if(getRegistry().getString(s, null) == null)
            throw new Exception(getRegistry().getString("noOpenCommand", "No Open command registered for this session") + " [" + getClass().getName() + "]");
        Object obj = null;
        if(aobj == null)
            obj = getRegistry().newInstanceForEx(s);
        else
            obj = getRegistry().newInstanceForEx(s, aobj);
        if(!(obj instanceof AbstractAIFCommand))
            throw new Exception("[" + obj.getClass().getName() + "] " + getRegistry().getString("notAIFCommand", "is not an instance of an AbstractAIFCommand!"));
        else
            return (AbstractAIFCommand)obj;
    }

    public int getReferenceCount()
    {
        return referenceCount;
    }

    public Registry getRegistry()
    {
        return registry;
    }

    public final String getSessionID()
    {
        return sessionID;
    }

    protected AIFSessionManager getSessionManager()
    {
        return sessionManager;
    }

    public String getStatus()
    {
        return status;
    }

    public String getUserName()
    {
        return userName;
    }

    public synchronized boolean hasComponentEventsSuspended()
    {
        return componentEventSuspended;
    }

    public boolean isAutoLogout()
    {
        return autoLogout;
    }

    public boolean isIdle()
    {
        return operationManager.isIdle();
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public void login()
        throws Exception
    {
        if(isLoggedIn())
        {
            return;
        } else
        {
            AbstractAIFCommand abstractaifcommand = getLoginCommand();
            abstractaifcommand.executeModal();
            return;
        }
    }

    public void performOperation(AbstractAIFOperation abstractaifoperation)
        throws Exception
    {
        operationManager.performOperation(abstractaifoperation);
    }

    public void queueOperation(AbstractAIFOperation abstractaifoperation)
    {
        operationManager.queueOperation(abstractaifoperation);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        if(propChangeListeners.contains(propertychangelistener))
            propChangeListeners.removeElement(propertychangelistener);
    }

    private void resetState()
    {
        Boolean boolean1 = new Boolean(isIdle());
        firePropertyChangeEvent("STATE", boolean1, boolean1);
    }

    private void resetStatus()
    {
        firePropertyChangeEvent("STATUS", status, status);
    }

    public synchronized void resumeComponentEvents()
    {
        componentEventSuspended = false;
        synchronized(this)
        {
            Debug.println("dispatcher", "Notify waiting threads (monitor is session object)");
            notifyAll();
        }
    }

    public void resumeOperation(AbstractAIFOperation abstractaifoperation)
    {
        operationManager.resumeOperation(abstractaifoperation);
    }

    public void setAutoLogout(boolean flag)
    {
        autoLogout = flag;
    }

    protected void setLogin(boolean flag)
    {
        loggedIn = flag;
        if(sessionManager != null && loggedIn)
            sessionManager.addSession(this);
    }

    protected void setOperationConcurrencyCount(short word0)
    {
        operationManager.setOperationConcurrencyCount(word0);
    }

    public void setReadyStatus()
    {
        setStatus(null);
    }

    protected void setRegistry(Registry registry1)
    {
        registry = registry1;
    }

    public void setSessionID(String s)
    {
        sessionID = s;
    }

    public void setStatus(String s)
    {
        String s1 = status;
        status = s;
        firePropertyChangeEvent("STATUS", s1, s);
    }

    public void setUserName(String s)
    {
        userName = new String(s);
    }

    public void suspendOperation(AbstractAIFOperation abstractaifoperation)
    {
        operationManager.suspendOperation(abstractaifoperation);
    }

    public String toString()
    {
        return getUserName();
    }

    public static final String SESSION = "SESSION";
    public static final String STATUS = "STATUS";
    public static final String STATE = "STATE";
    private String userName;
    private String aifBaseURL;
    private boolean loggedIn;
    private AIFSessionManager sessionManager;
    private Registry registry;
    private Vector propChangeListeners;
    private Vector connectionEventListeners;
    private String status;
    protected AIFSessionOperationManager operationManager;
    protected boolean operationSuspended;
    private int referenceCount;
    private boolean oldIdleState;
    private boolean autoLogout;
    private String sessionID;
    private boolean componentEventSuspended;

}
