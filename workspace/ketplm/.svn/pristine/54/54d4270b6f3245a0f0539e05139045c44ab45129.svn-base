// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractAIFAction.java

package e3ps.bom.framework.aif.common.actions;

import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public abstract class AbstractAIFAction
    implements Runnable, ActionListener
{

    public AbstractAIFAction(JFrame frame, String s)
    {
        application = null;
        parent = frame;
        actionName = s;
        registry = Registry.getRegistry(parent);
    }

    public AbstractAIFAction(AbstractAIFUIApplication abstractaifuiapplication, JFrame frame, String s)
    {
        application = abstractaifuiapplication;
        parent = frame;
        actionName = s;
        registry = application.getRegistry();
    }

    public AbstractAIFAction(AbstractAIFUIApplication abstractaifuiapplication, Registry registry1, String s)
    {
        application = abstractaifuiapplication;
        parent = abstractaifuiapplication.getDesktop();
        actionName = s;
        registry = registry1;
    }

    public AbstractAIFAction(AbstractAIFUIApplication abstractaifuiapplication, String s)
    {
        application = abstractaifuiapplication;
        parent = abstractaifuiapplication.getDesktop();
        actionName = s;
        registry = application.getRegistry();
    }

    public AbstractAIFAction(Registry registry1, JFrame frame, String s)
    {
        application = null;
        parent = frame;
        actionName = s;
        registry = registry1;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    public String getCommandKey()
        throws IllegalArgumentException
    {
        String s = registry.getString(actionName + "." + "COMMAND", null);
        if(s == null)
            throw new IllegalArgumentException(registry.getString("noCommandEntryForAction", "No command is defined for") + " " + actionName);
        else
            return s;
    }

    public abstract void run();

    public static final String COMMAND = "COMMAND";
    protected String actionName;
    protected JFrame parent;
    protected AbstractAIFUIApplication application;
    protected Registry registry;
}
