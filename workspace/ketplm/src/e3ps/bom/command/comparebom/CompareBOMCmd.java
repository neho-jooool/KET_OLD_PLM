package e3ps.bom.command.comparebom;

import java.awt.*;
import javax.swing.JFrame;

import e3ps.bom.framework.aif.*;

public class CompareBOMCmd extends AbstractAIFCommand
{
    private JFrame parent;
    AbstractAIFUIApplication app;

    public CompareBOMCmd(Frame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

    protected void executeCommand() throws Exception
    {
        JFrame desktop = app.getDesktop();
        parent = desktop;
		CompareFrame frame = new CompareFrame(parent, app);
    }
}
