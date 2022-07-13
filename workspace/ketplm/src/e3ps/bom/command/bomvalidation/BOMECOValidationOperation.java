package e3ps.bom.command.bomvalidation;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.framework.aif.AbstractAIFOperation;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;

public class BOMECOValidationOperation extends AbstractAIFOperation 
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private AbstractAIFUIApplication app;
    private JFrame desktop;
    private BOMECOValidationDialog bomEcoDlg;

    public BOMECOValidationOperation(AbstractAIFUIApplication app) 
	{
        this.desktop = app.getDesktop();
        this.app = app;
    }

    public void executeOperation() throws Exception 
	{
		BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();

		BOMTreeTableModel model = (BOMTreeTableModel)bomPanel.getTreeTableModel();
		BOMTreeNode firstNode = model.getRootNode();
		
		BOMBasicInfoPool.setBomValidationResult(false);
		BOMBasicInfoPool.setHasErrorInValidation(false);
		
		if (firstNode.getChildCount() == 0)
		{
			loadWarningMessage(messageRegistry.getString("valid"));
			return;
		}
		
		bomEcoDlg = new BOMECOValidationDialog(desktop, app);
    }

	private void loadWarningMessage(String message)
	{
		MessageBox messagebox = new MessageBox(desktop, message, "Warning", MessageBox.WARNING);
		messagebox.setVisible(true);
		messagebox.setModal(true);
	}	
}
