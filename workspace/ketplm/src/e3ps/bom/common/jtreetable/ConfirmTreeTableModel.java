package e3ps.bom.common.jtreetable;

import java.util.Vector;

import javax.swing.tree.TreePath;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.common.message.KETMessageService;

public class ConfirmTreeTableModel extends AbstractTreeTableModel
{
    int reloadCount;
    String publicModelName;
    protected BOMTreeNode reloadNode;

    protected static String cNames[] = new String[7];
    protected static Class cTypes[] = new Class[7];

    protected static int COL_VIEW;
    protected static int COL_USERDEPT;
    protected static int COL_USERNM;
    protected static int COL_USERDUTY;
    protected static int COL_USERID;
    protected static int COL_EMAIL;
    protected static int COL_NEWFLAG;

    public void tableModel()
    {
        cNames[0] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00321")/*조직도*/;
        cNames[1] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/;
        cNames[2] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/;
        cNames[3] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/;
        cNames[4] = "ID";
        cNames[5] = "EMAIL";
        cNames[6] = "New Flag";

        cTypes[0] = e3ps.bom.common.jtreetable.TreeTableModel.class;
        cTypes[1] = java.lang.String.class;
        cTypes[2] = java.lang.String.class;
        cTypes[3] = java.lang.String.class;
        cTypes[4] = java.lang.Integer.class;
        cTypes[5] = java.lang.Integer.class;
        cTypes[6] = java.lang.Integer.class;

        COL_VIEW = 0;
        COL_USERDEPT = 1;
        COL_USERNM = 2;
        COL_USERDUTY = 3;
        COL_USERID = 4;
        COL_EMAIL = 5;
        COL_NEWFLAG = 6;
    }

    public ConfirmTreeTableModel()
    {
        super(null);
        reloadCount = 0;
        publicModelName = "";
        tableModel();
        root = new BOMTreeNode(new BOMAssyComponent());
    }

    public ConfirmTreeTableModel(BOMAssyComponent rootBom)
    {
        super(null);
        reloadCount = 0;
        publicModelName = "";
        tableModel();
        root = new BOMTreeNode(rootBom);
        reloadCount = 1;
    }

    public void setModel(BOMTreeNode rootNode)
    {
        this.root = rootNode;
        fireTreeChanged(rootNode);
    }

    public void reloadChildren()
    {
    }

    public BOMTreeNode getRootNode()
    {
        return (BOMTreeNode)root;
    }

    public Object getChild(Object node, int i)
    {
        return getChildren(node)[i];
    }

    protected Object[] getChildren(Object node)
    {
        BOMTreeNode bomNode = (BOMTreeNode)node;
        return bomNode.getChildren();
    }

    public int getChildCount(Object node)
    {
        Object children[] = getChildren(node);
        return children != null ? children.length : 0;
    }

    public int getColumnCount()
    {
        return cNames.length;
    }

    public String getColumnName(int column)
    {
        return cNames[column];
    }

    public Class getColumnClass(int column)
    {
        return cTypes[column];
    }

    public void fireTreeChanged(BOMTreeNode node)
    {
        fireTreeStructureChanged(node, node.getPath(), null, node.getChildren());
    }

    public Object getValueAt(Object node, int column)
    {
        if( node == null )
        {
            return "";
        }

        BOMTreeNode bomNode = (BOMTreeNode)node;
        BOMAssyComponent nodeComponent = bomNode.getBOMComponent();

        if( nodeComponent == null )
        {
            return "";
        }

        if( nodeComponent instanceof BOMAssyComponent )
        {
            try
            {
                Vector subAssys;
                StringBuffer subAssyBf;
                BOMAssyComponent assyComponent = nodeComponent;

                switch(column)
                {
                    case 0:
                            return assyComponent;

                    case 1:
                            return assyComponent.getUserDeptStr();

                    case 2:
                            return assyComponent.getUserNmStr();

                    case 3:
                            return assyComponent.getUserDutyStr();

                    case 4:
                            return assyComponent.getUserIdStr();

                    case 5:
                            return assyComponent.getUserEmailStr();

                    case 6:
                            return assyComponent.getNewFlagStr();
                }
            }
            catch(SecurityException securityexception) { }

            /*
            newComponent.setOrgViewStr(getOrgViewStr());
            newComponent.setUserDeptStr(getUserDeptStr());
            newComponent.setUserNmStr(getUserNmStr());
            newComponent.setUserDutyStr(getUserDutyStr());
            newComponent.setUserIdStr(getUserIdStr());
            newComponent.setUserEmailStr(getUserEmailStr());
            */
        }

        return null;
    }

    public boolean isReloading()
    {
        return reloadCount > 0;
    }

    public TreePath getPathLoading()
    {
        BOMTreeNode rn = reloadNode;
        if( rn != null )
        {
            return new TreePath(rn.getPath());
        }

        return null;
    }
}
