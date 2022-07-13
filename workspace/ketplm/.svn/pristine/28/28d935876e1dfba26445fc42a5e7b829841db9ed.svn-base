package e3ps.bom.common.jtreetable;

import java.util.Vector;

import javax.swing.tree.TreePath;

import e3ps.bom.common.component.BOMAssyComponent;

public class ExpandTreeNode implements Runnable
{
    private BOMTreeNode selectedNode;
    private int levelNum;
    private JTreeTable treeTable;
    private Vector expandNodeVec;

    public ExpandTreeNode(BOMTreeNode selectedNode, int levelNum, JTreeTable treeTable)
    {
        expandNodeVec = new Vector();
        this.selectedNode = selectedNode;
        this.levelNum = levelNum;
        this.treeTable = treeTable;
    }

    public void run()
    {
        Object childNode[] = selectedNode.getChildren();
        boolean existMoreNode = false;
        if(childNode == null)
		{
            return;
		}

        expandNodeVec.add(new TreePath(selectedNode.getPath()));
        for(int i = 0; i < childNode.length; i++)
		{
            if(levelNum == 1)
            {
                expandNodeVec.add(new TreePath(((BOMTreeNode)childNode[i]).getPath()));
            }
			else
            {
                BOMTreeNode chNode = (BOMTreeNode)childNode[i];
                BOMAssyComponent childComponent = chNode.getBOMComponent();
                if(childComponent.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE)
                {
                    existMoreNode = true;
                    expandChild(chNode, 2);
                }
				else if(childComponent.getComponentTypeStr() == BOMAssyComponent.PART_TYPE && !existMoreNode && i == childNode.length - 1)
				{
                    if (!existMoreNode && i==childNode.length-1 )
					{
						expandNodeVec.add(new TreePath(chNode.getPath()));
					}
				}
            }
		}
        expandNodeFromVec();
    }

    private void expandChild(BOMTreeNode node, int nowLevel)
    {
        Object childNode[] = node.getChildren();
        boolean existMoreNode = false;
        if(nowLevel == levelNum && childNode != null && childNode.length != 0)
        {
            for(int i = 0; i < childNode.length; i++)
			{
                expandNodeVec.add(new TreePath(((BOMTreeNode)childNode[i]).getPath()));
			}
            return;
        }
		else if(nowLevel == levelNum)
		{
            return;
		}

        for(int i = 0; i < childNode.length; i++)
        {
            BOMAssyComponent childComponent = ((BOMTreeNode)childNode[i]).getBOMComponent();
            if(childComponent.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE)
            {
                existMoreNode = true;
                expandChild((BOMTreeNode)childNode[i], nowLevel + 1);
            }
			else if(childComponent.getComponentTypeStr() == BOMAssyComponent.PART_TYPE && !existMoreNode && i == childNode.length - 1)
			{
				if (!existMoreNode && i==childNode.length-1 )
				{
					expandNodeVec.add(new TreePath(((BOMTreeNode)childNode[i]).getPath()));
				}
			}
        }
    }

    private void expandNodeFromVec()
    {
        try
        {
            for(int i = 0; i < expandNodeVec.size(); i++)
            {
                treeTable.getTree().fireTreeWillCollapse((TreePath)expandNodeVec.elementAt(i));
                treeTable.getTree().scrollPathToVisible((TreePath)expandNodeVec.elementAt(i));
                treeTable.getTree().fireTreeCollapsed((TreePath)expandNodeVec.elementAt(i));
            }
        }
        catch(Exception exception) { }
    }

    public void start()
    {
        Thread expandThread = new Thread(this, "Expand");
        expandThread.start();
    }

}
