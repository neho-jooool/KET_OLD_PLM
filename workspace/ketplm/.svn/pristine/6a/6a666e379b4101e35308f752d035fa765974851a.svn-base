package e3ps.bom.command.bomproperty;

import javax.swing.JFrame;

import e3ps.bom.framework.aif.*;
import e3ps.bom.*;

public class BOMPropertyCmd extends AbstractAIFCommand
{
    private JFrame parent;
    AbstractAIFUIApplication app;

    public BOMPropertyCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

    protected void executeCommand() throws Exception
    {
		BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();

        bomPanel.showPartProperty();
    }
}
