package e3ps.bom.command.bomvalidation;

import javax.swing.*;

import e3ps.bom.common.jtreetable.*;
import e3ps.bom.common.util.Utility;
import e3ps.bom.common.clipboard.*;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.framework.aif.*;
import e3ps.bom.framework.util.*;

public class BOMValidationOperation extends AbstractAIFOperation 
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private AbstractAIFUIApplication app;
    private JFrame desktop;
    private BOMValidationDialog dlg;
    private BOMECOValidationDialog bomEcoDlg;
    public boolean checkgo = true;
    public BOMValidationOperation(AbstractAIFUIApplication app) 
	{
        this.desktop = app.getDesktop();
        this.app = app;
        this.checkgo = true;
    }
    
    public BOMValidationOperation(AbstractAIFUIApplication app,boolean chk) 
	{
        this.desktop = app.getDesktop();
        this.app = app;
        this.checkgo = chk;
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
		
		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals(""))
		{
			dlg = new BOMValidationDialog(desktop, app, checkgo);
		}
		else
		{
			bomEcoDlg = new BOMECOValidationDialog(desktop, app);
		}
    }

	private void loadWarningMessage(String message)
	{
		MessageBox messagebox = new MessageBox(desktop, message, "Warning", MessageBox.WARNING);
		messagebox.setVisible(true);
		messagebox.setModal(true);
	}	
}
