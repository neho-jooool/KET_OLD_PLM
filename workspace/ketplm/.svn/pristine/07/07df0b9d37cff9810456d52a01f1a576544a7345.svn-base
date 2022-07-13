package e3ps.bom.command.cut;

import java.awt.Cursor;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.copy.CopyCmd;
import e3ps.bom.command.removebom.RemoveCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class CutCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
    String userInfo = "";
	boolean bomGubunFlag = false;

    public CutCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = app.getDesktop();
    }

	public CutCmd(){}

	protected void executeCommand() throws Exception
	{
		try 
		{
            final BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
            BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();
            userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
//            userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserDept()) + "/" + Utility.checkNVL(BOMBasicInfoPool.getUserName());

		    int firstLevel = (((nodes[0]).getBOMComponent()).getLevelInt()).intValue();

			if(firstLevel == 0)
			{
				MessageBox messagebox = new MessageBox(parent, messageRegistry.getString("remove2"), "Warning", MessageBox.WARNING);
				messagebox.setModal(true);
				messagebox.setVisible(true);
				return;
			}

			for (int inx = 1; inx < nodes.length; inx++)
			{
				BOMAssyComponent bomassy = (nodes[inx]).getBOMComponent();
				if ((bomassy.getLevelInt()).intValue() < firstLevel)
				{
					MessageBox messagebox = new MessageBox(parent, messageRegistry.getString("checkLevel2"), "Warning", MessageBox.WARNING);
			        messagebox.setModal(true);
			        messagebox.setVisible(true);
					return;
				}
			}

			// 삭제할 것인지 확인 질문.
			int n = JOptionPane.showConfirmDialog( app.getDesktop(), messageRegistry.getString("cut"), "Cut", JOptionPane.YES_NO_OPTION);

			bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (n!=0) 
			{
				bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				return;
			}

            // 선택된 Node가 있는지 검사.
            if (nodes == null) 
			{
				bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("selectRow8"), "Error", MessageBox.ERROR);
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
					BOMTreeNode parentNode = parentsNode[parentsNode.length -2];
	                BOMAssyComponent parentcomponent = parentNode.getBOMComponent();

					if(bomGubunFlag)
					{
						if (!parentcomponent.getNewFlagStr().equalsIgnoreCase("NEW")) 
						{
							bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkParentNewItem"), "Error", MessageBox.ERROR);
							m.setVisible(true);
							m.setModal(true);
							return;
						}
					}

					if ( !((parentcomponent.getCheckOutStr()==null?"":parentcomponent.getCheckOutStr().toString().trim()).equalsIgnoreCase(userInfo)) ) 
					{
						bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkAdmin"), "Error", MessageBox.ERROR);
						m.setVisible(true);
						m.setModal(true);
						return;
					}
				}
            }

            for(int i=0; i<nodes.length; i++) 
			{
                BOMTreeNode selectedNode = nodes[i];
                Enumeration enum0 = selectedNode.preorderEnumeration();
                while(enum0.hasMoreElements()) 
				{
                    BOMTreeNode sNode = (BOMTreeNode)enum0.nextElement();
                    BOMAssyComponent cmp = sNode.getBOMComponent();

//shin... OOTB 체크아웃을 사용하지 않기때문에 주석처리 함.
/*
                    if (!(cmp.getCheckOutStr()==null?"":cmp.getCheckOutStr().toString().trim()).equals(""))
					{
						bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkAdmin"), "Error", MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                        return;
                    }
*/                    
                }
            }

			//Copy 로직
			CopyCmd cp = new CopyCmd(parent, app);
			cp.run();
			// Remove 로직
//			RemoveCmd del = new RemoveCmd(parent, app);
			RemoveCmd del = new RemoveCmd(parent, app, true);		// 삭제 메시지 한번 더 물어보는거 안보이게 하기위해 생성자 추가했음
			del.run();

			bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
		catch (Exception e) 
		{
            Kogger.error(getClass(), e);
        }
    }

}
