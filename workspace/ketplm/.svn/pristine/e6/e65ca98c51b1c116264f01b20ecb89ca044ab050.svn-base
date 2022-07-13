package e3ps.bom.command.updatebom;

import java.awt.Cursor;
import java.util.Enumeration;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.util.Utility;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;

public class ReplaceBOMCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
	String userInfo = "";
	boolean bomGubunFlag = false;

    public ReplaceBOMCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

	public ReplaceBOMCmd() { }

	protected void executeCommand() throws Exception
	{
		BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();
		userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";

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

        if (nodes[0].getBOMComponent().getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE)
		{
            MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("replace"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }

        BOMTreeNode [] parents = nodes[0].getPathNode();
		bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

        // 최 상위 Root Node가 아닌 경우.
        if (parents.length != 1)
		{
			for(int i=0; i<nodes.length; i++)
			{
				BOMTreeNode [] parentsNode = nodes[i].getPathNode();
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
				else
				{
					// BOM ECO CheckIn 에서 사용하는 로직
					BOMAssyComponent selectedComponent = nodes[0].getBOMComponent();
					if(!parentcomponent.getItemCodeStr().equals(BOMBasicInfoPool.getPublicModelName().trim()))
					{
						MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("replace1"), "Error", MessageBox.ERROR);
						m.setVisible(true);
						m.setModal(true);
						return;
					}
				}

				if ( !(( parentcomponent.getCheckOutStr() == null ? "" : parentcomponent.getCheckOutStr().toString().trim()).equalsIgnoreCase(userInfo)) )
				{
					MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("add3"), "Error", MessageBox.ERROR);
					m.setVisible(true);
					m.setModal(true);
					return;
				}
			}
        }

		Enumeration enum0 = nodes[0].preorderEnumeration();
		while(enum0.hasMoreElements())
		{
			BOMTreeNode sNode = (BOMTreeNode)enum0.nextElement();
			BOMAssyComponent cmp = sNode.getBOMComponent();

			//shin... OOTB 체크아웃을 사용하지 않기때문에 주석처리 함.
			/*if (!(cmp.getCheckOutStr()==null?"":cmp.getCheckOutStr().toString().trim()).equals(""))
			{
				MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkAdmin"), "Error", MessageBox.ERROR);
				m.setVisible(true);
				m.setModal(true);
				return;
			}*/
		}

		bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setRunnable(new UpdateBOMDialog(app, nodes[0], bomPanel.getTreeTable(), bomPanel.getTreeTableModel(), "Replace"));
		bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
