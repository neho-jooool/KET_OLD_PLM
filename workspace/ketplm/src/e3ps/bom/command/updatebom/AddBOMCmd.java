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
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class AddBOMCmd extends AbstractAIFCommand
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
    boolean bomGubunFlag = false;
    String userInfo = "";

    public AddBOMCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

    public AddBOMCmd() {}

    protected void executeCommand() throws Exception
    {
        BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();

        JFrame desktop = app.getDesktop();

        if( nodes == null )
        {
            MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("add"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }
        else
        {
            BOMAssyComponent chkComponent = nodes[0].getBOMComponent();
            String strType = "";

            try
            {
//                String strItemCode = chkComponent.getItemCodeStr();
//                WTPart part = KETPartHelper.service.getPart(strItemCode);
//                strType = IBAUtil.getAttrValue(part, "PartType");
                strType = chkComponent.getIBAPartType();

                if( strType.equals("M") )
                {
                     MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00116")/*금형부품은 1레벨만 가능합니다. 최상위를 선택하세요.*/, "Error", MessageBox.ERROR);
                     m.setVisible(true);
                     m.setModal(true);
                     return;
                }
            }
            catch( Exception e )
            {
                Kogger.error(getClass(), e);
            }
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
        userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";

        // BOM ECO CheckIn 에서 사용하는 로직
        if(!bomGubunFlag)
        {
            BOMAssyComponent selectedComponent = nodes[0].getBOMComponent();

            if( !selectedComponent.getItemCodeStr().equals(BOMBasicInfoPool.getPublicModelName().trim()) )
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

        if ( !( checkOutStr.equalsIgnoreCase(userInfo) ) )
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
        bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setRunnable(new UpdateBOMDialog(app, nodes[0], bomPanel.getTreeTable(), bomPanel.getTreeTableModel(), "Add"));
        bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void autoCommand(String itemCode) throws Exception
    {
        BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();

        JFrame desktop = app.getDesktop();

        if (nodes == null)
        {
            MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("add"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }
        else
        {
            BOMAssyComponent chkComponent = nodes[0].getBOMComponent();
            String strType = "";

            try
            {
//                String strItemCode = chkComponent.getItemCodeStr();
//                WTPart part = KETPartHelper.service.getPart(strItemCode);
//                strType = IBAUtil.getAttrValue(part, "PartType");
                strType = chkComponent.getIBAPartType();

                if(strType.equals("M"))
                {
                     MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00116")/*금형부품은 1레벨만 가능합니다. 최상위를 선택하세요.*/, "Error", MessageBox.ERROR);
                     m.setVisible(true);
                     m.setModal(true);
                     return;
                }
            }
            catch( Exception e )
            {
                Kogger.error(getClass(), e);
            }
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
        userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";

        // BOM ECO CheckIn 에서 사용하는 로직
        if(!bomGubunFlag)
        {
            BOMAssyComponent selectedComponent = nodes[0].getBOMComponent();

            if( !selectedComponent.getItemCodeStr().equals(BOMBasicInfoPool.getPublicModelName().trim()) )
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

        checkOutStr = cmp.getCheckOutStr()==null?"":cmp.getCheckOutStr().toString().trim();

        if ( !( checkOutStr.equalsIgnoreCase(userInfo) ) )
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
        bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setRunnable(new UpdateBOMDialog(app, nodes[0], bomPanel.getTreeTable(), bomPanel.getTreeTableModel(), "Add", itemCode));
        bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
