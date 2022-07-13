package e3ps.bom.command.clearbom;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.InterfaceAIFOperationListener;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class ClearBOMCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    AbstractAIFUIApplication app;
	BOMRegisterApplicationPanel m_pnl;
    BOMRegisterDesktop desktop;
    
    BOMTreeNode rootNode; //shin...
	BOMRegisterApplicationPanel pnl; //shin....
    JFrame parent;
    public ClearBOMCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
		m_pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
		desktop = (BOMRegisterDesktop)frame;
		this.parent = frame;
    }

     public ClearBOMCmd(){}

    protected void executeCommand() throws Exception
    {
		try 
		{
            final BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
            
            int n = JOptionPane.showConfirmDialog(app.getDesktop(), messageRegistry.getString("clearBom"), "Clear", JOptionPane.YES_NO_OPTION);
            if (n==JOptionPane.YES_NO_OPTION) 
			{
            	
                ClearBOMOperation op = new ClearBOMOperation(bomPanel);
				op.executeOperation();

                op.addOperationListener(new InterfaceAIFOperationListener() 
				{
                    public void startOperation(String s) 
					{
                        bomPanel.changeMouseShape(true);
                    }

                    public void endOperation() 
					{
                        bomPanel.changeMouseShape(false);
                    }
                });

                //shin.....................................
                KetMainJTreeTable km = new KetMainJTreeTable();
            	km.setGenMain(app);
          		//............................................................
                
				m_pnl.publicStatusPanel.setStatusBar();
            }
        }
		catch (Exception e) 
		{
            Kogger.error(getClass(), e);
        }
    }

}
