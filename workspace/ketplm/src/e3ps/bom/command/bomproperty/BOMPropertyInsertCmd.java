package e3ps.bom.command.bomproperty;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public class BOMPropertyInsertCmd extends AbstractAIFCommand {
    private JFrame parent;
    AbstractAIFUIApplication app;

    public BOMPropertyInsertCmd(JFrame frame, AbstractAIFUIApplication app) {
        this.app = app;
        parent = null;
    }

    protected void executeCommand() throws Exception {
    	
		BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        bomPanel.showPartPropertyInsert();
    	
//    	BOMPropertyInsertDialog dialog = new BOMPropertyInsertDialog(parent, true, app);
//    	dialog.doLayout();
    }
}
