package e3ps.bom.command.copy;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import ext.ket.shared.log.Kogger;

public class CellCopyCmd extends AbstractAIFCommand implements ClipboardOwner
{
	private JFrame parent;
	AbstractAIFUIApplication app;
	private Clipboard clipboard;

	public CellCopyCmd(JFrame frame, AbstractAIFUIApplication app)
    {
		this.app = app;
		parent = app.getDesktop();
    }

	public CellCopyCmd(){}

    protected void executeCommand() throws Exception
    {
		try 
		{
            final BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
			clipboard = parent.getToolkit().getSystemClipboard();

			StringSelection contents = new StringSelection(bomPanel.getTreeTable().getValueAt(bomPanel.getTreeTable().getSelectedRow(),bomPanel.getTreeTable().getSelectedColumn()).toString());
			clipboard.setContents(contents, this);
        }
		catch (Exception e) 
		{
            Kogger.error(getClass(), e);
        }
    }

	public void lostOwnership(Clipboard clip, Transferable transferable)
	{
	}

}
