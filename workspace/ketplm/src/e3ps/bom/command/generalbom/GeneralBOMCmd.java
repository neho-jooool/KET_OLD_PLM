package e3ps.bom.command.generalbom;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.*;

public class GeneralBOMCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;

	public GeneralBOMCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

    protected void executeCommand() throws Exception
    {
        if(!(BOMBasicInfoPool.getUserGroup().equalsIgnoreCase(UserData.SYS_ADMIN) ||
			BOMBasicInfoPool.getUserGroup().equalsIgnoreCase(UserData.BIZ_ADMIN) ||
			BOMBasicInfoPool.getUserRole().equalsIgnoreCase(UserData.OWNER)))
		{
            MessageBox mbox = new MessageBox(app.getDesktop(), messageRegistry.getString("createBomAuth"), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
			return ;
        }
       /* 
        if( !(BOMBasicInfoPool.getPublicModelName().trim()).equals("Empty") )
        {
        MessageBox mbox = new MessageBox(app.getDesktop(), "기존 작업중인 BOM구조는 자동 저장됩니다.", "Message", 0);
        mbox.setModal(true);
        mbox.setVisible(true);
        
        BOMRegisterApplicationPanel pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        pnl.clearBOMList();
        }*/
        
		JFrame desktop = app.getDesktop();
		parent = desktop;

		setRunnable(new GeneralBOMDialog(parent, app));
    }

}
