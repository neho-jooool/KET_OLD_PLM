// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CancelCheckOutAction.java

package e3ps.bom.common.actions;

import javax.swing.JFrame;

import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.common.actions.AbstractAIFAction;
import e3ps.bom.framework.util.MessageBox;

public class CancelCheckOutAction extends AbstractAIFAction
{
    private AbstractAIFUIApplication app;

    public CancelCheckOutAction(AbstractAIFUIApplication abstractaifuiapplication, JFrame frame, String s)
    {
        super(abstractaifuiapplication, frame, s);
    }

    public CancelCheckOutAction(AbstractAIFUIApplication abstractaifuiapplication, String s)
    {
        this(abstractaifuiapplication, abstractaifuiapplication.getDesktop(), s);
        app = abstractaifuiapplication;
    }

    public void run()
    {
        try
        {
            String s = getCommandKey();
            if(s == null)
			{
                return;
			}

            AbstractAIFCommand abstractaifcommand = (AbstractAIFCommand)registry.newInstanceFor(s, new Object[] {
                parent, app
            });

            if(abstractaifcommand != null)
			{
                abstractaifcommand.executeModal();
			}
        }
        catch(Exception exception)
        {
            MessageBox mbox = new MessageBox(parent, exception);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
    }

}
