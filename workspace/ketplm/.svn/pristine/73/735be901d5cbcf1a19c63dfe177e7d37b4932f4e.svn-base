// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   BOMTreeLoader.java

package e3ps.bom.common.jtreetable;

import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreePath;

import e3ps.bom.common.component.BOMAssyComponent;
import ext.ket.shared.log.Kogger;

// Referenced classes of package e3ps.bom.common.jtreetable:
//            BOMTreeNode, ConfirmTreeTableModel, JTreeTable

public class ConfirmTreeSearchLoader implements Runnable
{
    int preLevelNum;
    int nowLevelNum;
    int nextLevelNum;
    BOMTreeNode rootNode;
    BOMTreeNode parentNode;
    BOMTreeNode currentNode;
    BOMTreeNode nextNode;
    public Vector bomDataVec;
    public JTreeTable treeTable;
    public ConfirmTreeTableModel model;
    public BOMAssyComponent assyComponent;
	private final int MAX_LEVEL = 16;

    public ConfirmTreeSearchLoader(BOMTreeNode node, Vector bomDataVec, JTreeTable treeTable, ConfirmTreeTableModel model)
    {
        preLevelNum = 0;
        nowLevelNum = 0;
        nextLevelNum = 0;
        parentNode = null;
        currentNode = null;
        nextNode = null;

        this.bomDataVec = null;
        this.treeTable = null;
        this.model = null;
        rootNode = node;
        this.bomDataVec = bomDataVec;
        this.treeTable = treeTable;
        this.model = model;
    }

    public void run()
    {
        parentNode = rootNode;
        Vector expandNodeVec = new Vector();

		int []sortLevel = new int[MAX_LEVEL];

		for( int i = 0; i < MAX_LEVEL; i++ )
		{
			sortLevel[i] = 0;
		}

		BOMAssyComponent targetComponent = (BOMAssyComponent)rootNode.getBOMComponent();

        int rootLevel = targetComponent.getLevelInt().intValue();

        for( int i = 0; i < bomDataVec.size(); i++ )
        {
            BOMAssyComponent bomComponentData = (BOMAssyComponent)bomDataVec.elementAt(i);
            nowLevelNum = bomComponentData.getLevelInt().intValue();

			bomComponentData.setLevelInt(new Integer(nowLevelNum + rootLevel));

			sortLevel[nowLevelNum]++; // SortLevel 증가.

			if(parentNode != null)
			{
				BOMAssyComponent parentBomComponent = (BOMAssyComponent)parentNode.getBOMComponent();
				String sortOrder = parentBomComponent.getSortOrderStr();
				if (sortLevel[nowLevelNum] < 10)
					sortOrder = sortOrder + "000" + sortLevel[nowLevelNum];
				else if (sortLevel[nowLevelNum] < 100)
					sortOrder = sortOrder + "00" + sortLevel[nowLevelNum];
				else if (sortLevel[nowLevelNum] < 1000)
					sortOrder = sortOrder + "0" + sortLevel[nowLevelNum];
				else if (sortLevel[nowLevelNum] < 10000)
					sortOrder = sortOrder + sortLevel[nowLevelNum];
				bomComponentData.setSortOrderStr(sortOrder);

				currentNode = new BOMTreeNode(parentNode, bomComponentData);

				parentNode.addElement(currentNode);
				// // 2011-03-31 [윤도혁 J 요구사항 반영] 전체 레벨 펼쳐지도록 함
//				if(bomComponentData.getLevelInt().intValue() == 1)
//				{
					expandNodeVec.addElement(new TreePath(currentNode.getPath()));
//				}

				if( i + 1 != bomDataVec.size() )
				{
					BOMAssyComponent nextBomComponentData = (BOMAssyComponent)bomDataVec.elementAt(i + 1);
					nextLevelNum = nextBomComponentData.getLevelInt().intValue();
					if( nextLevelNum == nowLevelNum )
					{
						//bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
					}
					else if( nextLevelNum > nowLevelNum )
					{
						parentNode = currentNode;
						//bomComponentData.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
					}
					else
					{
						//bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);//원래꺼.
						for( int j = nextLevelNum; j < nowLevelNum; j++ )
						{
							parentNode = (BOMTreeNode)parentNode.getParent();
						}
					}
				}

				if( (bomComponentData.getSupplyTypeStr()).equals("People") )
				{
					bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
				}
				else
				{
					bomComponentData.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
				}
			}
        }

        Enumeration seqEnum = model.getRootNode().preorderEnumeration();
        int realSeqNumber = 2;

        while( seqEnum.hasMoreElements() )
        {
            BOMAssyComponent component = ((BOMTreeNode)seqEnum.nextElement()).getBOMComponent();
            if( component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE )
            {
                assyComponent = component;
                assyComponent.setSeqInt(new Integer(realSeqNumber++));
            }
        }

        for( int j = 0; j < expandNodeVec.size(); j++ )
        {
            treeTable.getTree().scrollPathToVisible((TreePath)expandNodeVec.elementAt(j));
        }

        // 새로 그리기.
//        model.fireTreeChanged(parentNode);
//        treeTable.repaint();

        TreePath treepath = new TreePath(parentNode.getPath());

        try
        {
            treeTable.getTree().fireTreeWillExpand(treepath);
            treeTable.getTree().scrollPathToVisible(treepath);
            treeTable.getTree().fireTreeExpanded(treepath);
            treeTable.getTree().setSelectionPath(treepath);

            treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));
        }
        catch (Exception ex)
        {
			Kogger.error(getClass(), ex);
        }
    }
}
