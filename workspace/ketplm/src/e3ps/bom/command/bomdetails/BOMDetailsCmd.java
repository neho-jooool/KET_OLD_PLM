package e3ps.bom.command.bomdetails;

import java.awt.Cursor;
import javax.swing.JFrame;

import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public class BOMDetailsCmd extends AbstractAIFCommand
{
    private JFrame parent;
    AbstractAIFUIApplication app;

	public BOMDetailsCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

    protected void executeCommand() throws Exception
    {
		JFrame desktop = app.getDesktop();
		parent = desktop;

        parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setRunnable(new BOMDetailsDialog(parent, app));
		parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
