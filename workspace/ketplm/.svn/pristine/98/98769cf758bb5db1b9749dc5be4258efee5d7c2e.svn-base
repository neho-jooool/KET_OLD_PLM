package e3ps.bom.command.movedown;

import java.awt.Rectangle;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.tree.TreePath;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class MoveDownCmd extends AbstractAIFCommand
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    AbstractAIFUIApplication app;
    Registry appReg;

    String userInfo = "";
    boolean bomGubunFlag = false;

    DBConnectionManager resource = null;
    Connection connection = null;

    public MoveDownCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
    }

    protected void executeCommand() throws Exception
    {
        try
        {
            appReg = Registry.getRegistry(app);
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));
            BOMSaveDao saveDao = new BOMSaveDao();

            BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
            BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();
            userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
//            userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserDept()) + "/" + Utility.checkNVL(BOMBasicInfoPool.getUserName());

            if (nodes == null)
            {
                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("selectRow13"), "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
                return;
            }

            // 둘 이상의 Node 를 선택한 경우 에러
            if (nodes.length != 1)
            {
                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("selectRow3"), "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
                return;
            }

            BOMTreeNode [] parents = nodes[0].getPathNode();

            // 최 상위 Root Node가 아닌 경우.
            if (parents.length != 1)
            {
                BOMTreeNode parentNode = parents[parents.length - 2];
                BOMAssyComponent parentComponent = parentNode.getBOMComponent();

                bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

                if(bomGubunFlag)
                {
                    // Parent Item Code 가 New 인 상태인 경우 Move Down 가능.
                    if ( !parentComponent.getNewFlagStr().equalsIgnoreCase("NEW") )
                    {
                        MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkParentNewItem"), "Error", MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                        return;
                    }
                }

                if ( !( (parentComponent.getCheckOutStr()==null?"":parentComponent.getCheckOutStr().toString().trim()).equalsIgnoreCase(userInfo) ) )
                {
//                    MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkAdmin"), "Error", MessageBox.ERROR);
                    MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00223")/*선택한 부품은 이동할 수 없습니다.*/, "Error", MessageBox.ERROR);
                    m.setVisible(true);
                    m.setModal(true);
                    return;
                }
            }

            // 동일 레벨안에서 Move Down 가능.
            BOMTreeNode targetParentNode = (BOMTreeNode)nodes[0].getNextNode();

            if (targetParentNode == null)
            {
                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("notSameNodeItem"), "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
                return;
            }

            BOMAssyComponent targetCmp = targetParentNode.getBOMComponent();
            BOMAssyComponent selectedCmp = nodes[0].getBOMComponent();

            int seqInt = nodes[0].getBOMComponent().getSeqInt().intValue();
            String sortOrderStr = nodes[0].getBOMComponent().getSortOrderStr();
            String parentItemCode = nodes[0].getBOMComponent().getParentItemCodeStr();
            int itemSeqInt = nodes[0].getBOMComponent().getItemSeqInt().intValue();

            if (nodes[0].getChildCount() > 0)
            {
//                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("moveDown"), "Error", MessageBox.ERROR);
                MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00050")/*Ass'y 는 이동 불가능합니다.*/, "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
                return;
            }

            if (targetParentNode.getChildCount() > 0)
            {
//                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("moveUp"), "Error", MessageBox.ERROR);
                MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00050")/*Ass'y 는 이동 불가능합니다.*/, "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
                return;
            }

            if (nodes[0].getBOMComponent().getLevelInt().intValue() != targetParentNode.getBOMComponent().getLevelInt().intValue())
            {
//                MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("item1"), "Error", MessageBox.ERROR);
                MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00139")/*동일 레벨 안에 이동 가능한 부품이 존재하지 않습니다.*/, "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
                return;
            }
            else
            {
                if (nodes.length != 1)
                {
                    selectedCmp.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
                }
                else
                {
                    selectedCmp.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
                }

                selectedCmp.setSeqInt(targetCmp.getSeqInt());
                selectedCmp.setSortOrderStr(targetCmp.getSortOrderStr());
                selectedCmp.setItemSeqInt(targetCmp.getItemSeqInt());
                selectedCmp.setParentItemCodeStr(targetCmp.getParentItemCodeStr());

                if (targetParentNode.getChildCount() > 0)
                {
                    targetCmp.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
                }
                else
                {
                    targetCmp.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
                }

                targetCmp.setSeqInt(new Integer(seqInt));
                targetCmp.setSortOrderStr(sortOrderStr);
                targetCmp.setItemSeqInt(new Integer(itemSeqInt));
                targetCmp.setParentItemCodeStr(parentItemCode);

                targetParentNode.setBOMComponent(selectedCmp);
                nodes[0].setBOMComponent(targetCmp);

                BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();
                model.fireTreeChanged(targetParentNode);
                model.fireTreeChanged(nodes[0]);
                JTreeTable treetable = bomPanel.getTreeTable();

                saveDao.changeMoveItem(connection, selectedCmp, targetCmp);

                if (targetParentNode == nodes[0])
                {
                    treetable.repaint();
                    TreePath treepath = new TreePath(nodes[0].getPathNode()[nodes[0].getPathNode().length - 2]);
                    try
                    {
                        treetable.getTree().fireTreeWillExpand(treepath);
                        treetable.getTree().scrollPathToVisible(treepath);
                        treetable.getTree().fireTreeExpanded(treepath);
                        treetable.getTree().setSelectionPath(treepath);

                        treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
                    }
                    catch (Exception ex)
                    {}
                }

                // 전체레벨 펼치기
                treetable.getTree().setSelectionRow(0);
                bomPanel.mainEditorPane.expandTreeTable(20);

                connection.commit();
            }
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
