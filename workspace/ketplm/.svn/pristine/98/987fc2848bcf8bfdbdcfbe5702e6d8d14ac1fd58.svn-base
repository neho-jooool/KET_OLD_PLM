package e3ps.bom.command.managecoworker;

import java.awt.*;
import javax.swing.JFrame;

import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public class ManageCoWorkerCmd extends AbstractAIFCommand
{
    private JFrame parent;
    AbstractAIFUIApplication app;

	public ManageCoWorkerCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

    protected void executeCommand() throws Exception
    {
		JFrame desktop = app.getDesktop();
		parent = desktop;

        parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setRunnable(new ManageCoworkerDialog(parent, app));
		parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

}
