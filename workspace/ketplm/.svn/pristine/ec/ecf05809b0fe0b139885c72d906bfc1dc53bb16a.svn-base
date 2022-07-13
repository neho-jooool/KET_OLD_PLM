package e3ps.bom.common.jtreetable;

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import e3ps.bom.common.component.BOMAssyComponent;

public class BOMTreeNode extends DefaultMutableTreeNode
{
    protected BOMAssyComponent bomcomponent;
    private BOMTreeNode parent;
    private Vector children = new Vector();

    // 생성자 : Root Node
    public BOMTreeNode(BOMAssyComponent bomcomponent)
	{
        super(bomcomponent);
        this.bomcomponent = bomcomponent;
    }

    // 생성자 : Assy, Part Node
    public BOMTreeNode(BOMTreeNode parent, BOMAssyComponent bomcomponent)
	{
        super(bomcomponent);
        this.bomcomponent = bomcomponent;
        this.parent = parent;
    }

    /**
     * Returns the the string to be used to display this leaf in the JTree.
     */
    public String toString()
	{
        return bomcomponent.toString();
    }

    /**
     * Loads the children, caching the results in the children
     * instance variable.
     */
    public Object[] getChildren()
	{
        return children.toArray();
    }

    public void addElement(BOMTreeNode node) 
    {
        int pos = 0;
        String startDate = node.getBOMComponent().getStartDate();

        Object childObj[] = getChildren();
        if (childObj.length == 0) 
       {
            super.add(node);
            children.addElement(node);
        }
        else 
        {
            for(int i=0; i<childObj.length; i++) 
            {
                BOMTreeNode objNode = (BOMTreeNode)childObj[i];
                String startDateVal = objNode.getBOMComponent().getStartDate();
                int result = node.toString().compareToIgnoreCase(objNode.toString());

                if ( result == 0) 
                {
                    // 적용일에 따라서 Sorting을 한다.
                    if ( startDate.compareToIgnoreCase(startDateVal) > 0) 
                    {
                        pos++;
                        continue;
                    }
                    else 
                    {
                        break;
                    }
                }
                else if( result > 0) 
                {
                    pos++;
                    continue;
                }
                else 
                {
					pos++;
					continue;
//                    break;
                }
            }
            super.insert(node, pos);
            children.insertElementAt(node, pos);
        }
    }

    public void removeAllChildren()
	{
        super.removeAllChildren();
        children.removeAllElements();
    }

    public void removeElement(BOMTreeNode node)
	{
        super.remove(node);
        children.removeElement(node);
    }

    public BOMAssyComponent getBOMComponent()
	{
        return bomcomponent;
    }

    public void setBOMComponent(BOMAssyComponent component)
	{
        this.bomcomponent = component;
    }

    /**
     * Gets the path from the root to the receiver.
     */
    public BOMTreeNode[] getPathNode()
	{
        return getPathToRoot(this, 0);
    }

    protected BOMTreeNode[] getPathToRoot(BOMTreeNode aNode, int depth)
	{
        BOMTreeNode[] retNodes;

        if(aNode == null)
		{
            if(depth == 0)
			{
                return null;
			}
            else
			{
                retNodes = new BOMTreeNode[depth];
			}
        }
        else
		{
            depth++;
            retNodes = getPathToRoot((BOMTreeNode)aNode.getParent(), depth);
            retNodes[retNodes.length - depth] = aNode;
        }
        return retNodes;
    }

    /**
     * Returns true if the receiver represents a leaf, that is it is
     * isn't a directory.
     */
    public boolean isLeaf()
	{
        return (bomcomponent.getComponentTypeStr() == BOMAssyComponent.PART_TYPE) ? true: false;
    }
}
