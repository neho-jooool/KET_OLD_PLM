package e3ps.bom.command.finditem;

import javax.swing.JFrame;

import e3ps.bom.framework.aif.*;
import e3ps.bom.*;

public class FindItemCmd extends AbstractAIFCommand
{
	private JFrame frame;
    AbstractAIFUIApplication app;

    public FindItemCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        this.frame = frame;
    }

   public FindItemCmd(){}

	protected void executeCommand() throws Exception
    {
		BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
	    new FindItemDialog(frame, app, false, bomPanel.getTreeTable(), bomPanel.getTreeTableModel(), bomPanel.getTotalDataCount());
    }

}
