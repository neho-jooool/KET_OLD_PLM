package e3ps.bom.command.bomvalidation;

import javax.swing.JFrame;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class BOMValidationCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
	AbstractAIFUIApplication app;
	
    String itemCodeStr = "";
    private JFrame parent;

	public BOMValidationCmd(JFrame frame, AbstractAIFUIApplication app)
	{
		this.app = app;
		parent = app.getDesktop();

		itemCodeStr = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();
	}

	protected void executeCommand() throws Exception
	{
		if(itemCodeStr.equalsIgnoreCase("Empty"))
		{
			MessageBox mbox = new MessageBox(parent, messageRegistry.getString("openBOMWorkspace"), "Warning", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
		}

		try 
		{
			BOMValidationOperation op = new BOMValidationOperation(app);
			Thread executeOperation = new Thread(op);
			executeOperation.start();
        }
		catch (Exception ex) 
		{
            Kogger.error(getClass(), ex);
        }
    }

}
