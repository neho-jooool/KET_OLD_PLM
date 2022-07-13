package e3ps.bom.common.clipboard;

import java.util.Vector;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public class ClipBoardPool
{
    private static Vector treeNodeVec = new Vector();
    private static int totalNodeCount = 0;
    private static AbstractAIFUIApplication app;
	private static String typeStr = "";

    public ClipBoardPool()
    {
    }

    public static final void addTreeNode(AbstractAIFUIApplication appa, BOMTreeNode nodes[], String type)
    {
        app = appa;
		typeStr = type;

        if(nodes == null)
		{
            return;
		}

        removeAllData();

        for(int i = 0; i < nodes.length; i++)
        {
            BOMAssyComponent assy = nodes[i].getBOMComponent();
            BOMTreeNode newNode = new BOMTreeNode(assy.createNewComponent());
            totalNodeCount++;
            Object children[] = nodes[i].getChildren();
            for(int j = 0; j < children.length; j++)
			{
                addChildNode(newNode, (BOMTreeNode)children[j]);
			}

            treeNodeVec.addElement(newNode);
        }
    }

    public static final Object[] getSavedTreeNode()
    {
        return treeNodeVec.toArray();
    }

    public static final int getTotalNodeCount()
    {
        return totalNodeCount;
    }

	public static final String getType()
	{
		return typeStr;
	}

    public static void removeAllData()
    {
        totalNodeCount = 0;
        treeNodeVec.removeAllElements();
    }

    private static void addChildNode(BOMTreeNode newParentNode, BOMTreeNode childNode)
    {
        BOMAssyComponent assy = childNode.getBOMComponent();
        BOMTreeNode newChildNode = new BOMTreeNode(newParentNode, assy.createNewComponent());
        totalNodeCount++;
        newParentNode.addElement(newChildNode);
        Object children[] = childNode.getChildren();
        for(int i = 0; i < children.length; i++)
		{
            addChildNode(newChildNode, (BOMTreeNode)children[i]);
		}
    }

}
