package e3ps.bom.command.copy;

import java.awt.Cursor;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class CopyCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
	JFrame parent;
    AbstractAIFUIApplication app;

    public CopyCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = app.getDesktop();
    }
	
    protected void executeCommand() throws Exception
    {
		try 
		{
            final BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
            BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();
			
		    int firstLevel = (((nodes[0]).getBOMComponent()).getLevelInt()).intValue();
			for (int inx = 1; inx < nodes.length; inx++)
			{
				BOMAssyComponent bomassy = (nodes[inx]).getBOMComponent();
				if ((bomassy.getLevelInt()).intValue() < firstLevel)
				{
					MessageBox messagebox = new MessageBox(parent, messageRegistry.getString("checkLevel2"), "Warning", MessageBox.WARNING);
			        messagebox.setModal(true);
			        messagebox.setVisible(true);
					return;
				}
			}

            CopyOperation op = new CopyOperation(app, nodes, "BOMEdit");
			bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			op.executeOperation();
			bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
		catch (Exception e) 
		{
            Kogger.error(getClass(), e);
        }
    }

}
