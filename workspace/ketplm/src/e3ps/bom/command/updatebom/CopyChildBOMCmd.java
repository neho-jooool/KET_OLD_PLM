package e3ps.bom.command.updatebom;

import java.awt.Cursor;
import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;

public class CopyChildBOMCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
	String userInfo = "";
	boolean bomGubunFlag = false;

    public CopyChildBOMCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

	public CopyChildBOMCmd() { }

	protected void executeCommand() throws Exception
	{
		BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();
		userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
//		userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserDept()) + "/" + Utility.checkNVL(BOMBasicInfoPool.getUserName());

        // Validation Check ....
        JFrame desktop = app.getDesktop();
        // 노드 선택 조사
        if (nodes == null)
		{
            MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("add"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }

        // 둘 이상의 Node를 선택한 경우 에러
        if (nodes.length != 1)
		{
            MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("add1"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }

		bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

		// BOM ECO CheckIn 에서 사용하는 로직
		if(!bomGubunFlag)
		{
			BOMAssyComponent selectedComponent = nodes[0].getBOMComponent();

			if(!selectedComponent.getItemCodeStr().equals(BOMBasicInfoPool.getPublicModelName().trim()))
			{
				MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("add2"), "Error", MessageBox.ERROR);
				m.setVisible(true);
				m.setModal(true);
				return;
			}
		}

		String checkOutStr = "";

		// 선택한 Item Code 가 자신이 Check-Out 한 Item 인지 체크..
		BOMAssyComponent cmp = nodes[0].getBOMComponent();

		checkOutStr = cmp.getCheckOutStr() == null ? "" : cmp.getCheckOutStr().toString().trim();

		if( !( checkOutStr.equalsIgnoreCase(userInfo) ) )
		{
			MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("add3"), "Error", MessageBox.ERROR);
			m.setVisible(true);
			m.setModal(true);
			return;
		}

		if(bomGubunFlag)
		{
			if (!cmp.getNewFlagStr().equalsIgnoreCase("NEW"))
			{
				MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("add4"), "Error", MessageBox.ERROR);
				m.setVisible(true);
				m.setModal(true);
				return;
			}
		}

        BOMTreeNode [] parents = nodes[0].getPathNode();

        // 최 상위 Root Node가 아닌 경우.
        if (parents.length != 1)
		{
            BOMTreeNode parentNode = parents[parents.length -2]; // 상위 노드
            BOMAssyComponent parentcomponent = parentNode.getBOMComponent();

			if(bomGubunFlag)
			{
				if (!parentcomponent.getNewFlagStr().equalsIgnoreCase("NEW"))
				{
					MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkParentNewItem"), "Error", MessageBox.ERROR);
					m.setVisible(true);
					m.setModal(true);
					return;
				}
			}
        }

		bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setRunnable(new UpdateBOMDialog(app, nodes[0], bomPanel.getTreeTable(), bomPanel.getTreeTableModel(), "CopyChild"));
		bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
