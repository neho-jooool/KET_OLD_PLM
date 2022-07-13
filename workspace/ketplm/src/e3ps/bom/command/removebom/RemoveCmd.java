package e3ps.bom.command.removebom;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AIFSession;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class RemoveCmd extends AbstractAIFCommand
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;

    BOMRegisterApplicationPanel bomPanel;

    AIFSession session = new AIFSession();
    String userInfo = "";
    boolean bomGubunFlag = false;
    boolean isFromCut = false;

    DBConnectionManager resource = null;
    Connection connection = null;
    Registry appReg;

    public RemoveCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

    public RemoveCmd(JFrame frame, AbstractAIFUIApplication app, boolean isFromCut)
    {
        this.app = app;
        parent = null;
        this.isFromCut = isFromCut;
    }

    protected void executeCommand() throws Exception
    {
        bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();
        userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
        bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;
        appReg = Registry.getRegistry(app);

        try
        {
            if((((nodes[0]).getBOMComponent()).getLevelInt()).intValue() == 0)
            {
                MessageBox messagebox = new MessageBox(parent, messageRegistry.getString("remove2"), "Warning", MessageBox.WARNING);
                messagebox.setModal(true);
                messagebox.setVisible(true);
                return;
            }

            int n = 0;
            if (!isFromCut) {
                n = JOptionPane.showConfirmDialog(app.getDesktop(), messageRegistry.getString("remove1"), "Remove", JOptionPane.YES_NO_OPTION);
            }
            if (n!=0)
                return;

            // 선택된 Node가 있는지 검사.
            if (nodes == null)
            {
                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("selectRow8"), "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
                return;
            }

            BOMTreeNode [] parents = nodes[0].getPathNode();
            // 최 상위 Root Node가 아닌 경우.
            if (parents.length != 1)
            {
                BOMTreeNode parentNode = parents[parents.length -2];
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

            String strCheckOut = "";
            // 선택한 Item 의 차상위 모 Item Code 가 자신이 Check-Out 한 Item 인지 체크..
            for(int i=0; i<nodes.length; i++)
            {
                BOMTreeNode selectedNode = nodes[i];
//                Enumeration enum0 = selectedNode.preorderEnumeration();
//                while(enum0.hasMoreElements())
//                {
//                    BOMTreeNode sNode = (BOMTreeNode)enum0.nextElement();
Kogger.debug(getClass(), "##### selectedNode : " + selectedNode);
                    BOMAssyComponent cmp = ((BOMTreeNode)selectedNode.getParent()).getBOMComponent();
Kogger.debug(getClass(), "===>> parent itemCode : " + cmp.getItemCodeStr());
                    strCheckOut = cmp.getCheckOutStr() == null ? "" : cmp.getCheckOutStr().toString().trim();
                    if ( !( strCheckOut.equals(userInfo) ) || strCheckOut.equals("") )
                    {
                        MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00222")/*선택한 부품은 삭제 불가능합니다.*/, "Error", MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                        return;
                    }
//                }
            }

            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            RemoveOperation op = new RemoveOperation(connection, app, nodes, bomPanel.getTreeTableModel(), bomPanel.getTreeTable());
            session.queueOperation(op);

            connection.commit();
        }
        catch (Exception ex)
        {
            Kogger.error(getClass(), ex);

            try
            {
                connection.rollback();
            }
            catch (Exception dbex)
            {}

            MessageBox m = new MessageBox(app.getDesktop(), "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
        }
        finally
        {
            if(resource != null)
            {
                resource.freeConnection(appReg.getString("plm"), connection);
            }
        }
    }

}
