package e3ps.bom.common.actions;

import javax.swing.JFrame;

import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.common.actions.AbstractAIFAction;
import e3ps.bom.framework.util.MessageBox;

public class PCodeIFAction extends AbstractAIFAction {
	private AbstractAIFUIApplication app;

    public PCodeIFAction(AbstractAIFUIApplication abstractaifuiapplication, JFrame frame, String s)
    {
        super(abstractaifuiapplication, frame, s);
    }

    public PCodeIFAction(AbstractAIFUIApplication abstractaifuiapplication, String s)
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
