package e3ps.bom.command.paste;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.ClipBoardPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.Utility;
import e3ps.bom.framework.aif.AIFSession;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class PasteCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");	
    private JFrame parent;
    AbstractAIFUIApplication app;
	AIFSession session = new AIFSession();

	String userInfo = "";
	boolean bomGubunFlag = false;

    public PasteCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }
	
	protected void executeCommand() throws Exception
    {
        try 
		{
            BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();

			// 현재 선택된 Node 정보를 가져온다.
            BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();
            BOMTreeTableModel model = bomPanel.getTreeTableModel();
            JTreeTable treeTable = bomPanel.getTreeTable();
			userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
//			userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserDept()) + "/" + Utility.checkNVL(BOMBasicInfoPool.getUserName());

            if (nodes == null)
			{
                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("paste"), "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
				return;
			}

			// Clipboard에 저장이 되어 있지 않은 경우에는 아무런 동작을 하지 않는다.
			Object[] obj = ClipBoardPool.getSavedTreeNode();
			if (obj == null || obj.length == 0)
			{
				return;
			}

            if (nodes.length > 1) 
			{
                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("paste1"), "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
                return;
            }

			// 선택한 Item Code 가 자신이 Check-Out 한 Item 인지 체크..
			BOMAssyComponent cmp = nodes[0].getBOMComponent();
Kogger.debug(getClass(), "@@@@@@ Selected Comp : " + cmp);

			String checkOutStr = cmp.getCheckOutStr() == null ? "" : cmp.getCheckOutStr().toString().trim();

			if( !( checkOutStr.equalsIgnoreCase(userInfo) ) )
			{
				MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("paste2"), "Error", MessageBox.ERROR);
				m.setVisible(true);
				m.setModal(true);
				return;
			}

			bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

			if(bomGubunFlag)
			{
				if ( !cmp.getNewFlagStr().equalsIgnoreCase("NEW") ) 
				{
					MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("paste3"), "Error", MessageBox.ERROR);
					m.setVisible(true);
					m.setModal(true);
					return;
				}
			}

            PasteOperation op = new PasteOperation(app.getDesktop() , nodes, model, treeTable, app);
            session.queueOperation(op);
        }
		catch (Exception ex) 
		{
			Kogger.error(getClass(), ex);
        }
    }

}
