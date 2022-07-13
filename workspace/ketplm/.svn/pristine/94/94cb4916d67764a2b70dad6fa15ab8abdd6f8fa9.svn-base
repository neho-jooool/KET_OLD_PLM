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
//            BOMTreeNode, BOMTreeTableModel, JTreeTable

public class BOMTreeSearchLoader implements Runnable
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
    public BOMTreeTableModel model;
    public BOMAssyComponent assyComponent;
	private final int MAX_LEVEL = 16;
	
    public BOMTreeSearchLoader(BOMTreeNode node, Vector bomDataVec, JTreeTable treeTable, BOMTreeTableModel model)
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
		for(int i=0; i<MAX_LEVEL; i++)
			sortLevel[i] = 0;

		BOMAssyComponent targetComponent = (BOMAssyComponent)rootNode.getBOMComponent();
		targetComponent.setLevelInt(0);		// 2011-02-22  [임승영D 요구사항으로 수정] BOM 조회 시, Root Level 은 0으로 셋팅되어야 함
		targetComponent.setSeqInt(0);		// 2011-03-30  [임승영D 요구사항으로 수정] BOM 조회 시, Root SEQ 는 0으로 셋팅되어야 함
		
        int rootLevel = targetComponent.getLevelInt().intValue();
        
        for(int i = 0; i < bomDataVec.size(); i++)
        {
            BOMAssyComponent bomComponentData = (BOMAssyComponent)bomDataVec.elementAt(i);
            nowLevelNum = bomComponentData.getLevelInt().intValue();
Kogger.debug(getClass(), "@@@@@ bomComponentData : " + bomComponentData);
Kogger.debug(getClass(), "@@@@@ nowLevelNum : " + nowLevelNum);
			bomComponentData.setLevelInt(new Integer(nowLevelNum + rootLevel));

			sortLevel[nowLevelNum]++; // SortLevel 증가.
Kogger.debug(getClass(), "########## " + i + " 번째");
			if(parentNode != null)
			{
Kogger.debug(getClass(), "@@@@@ parentNode [1] : " + parentNode);				
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
				// 2011-03-31 [윤도혁 J 요구사항 반영] 전체 레벨 펼쳐지도록 함
//				if(bomComponentData.getLevelInt().intValue() == 1)
//				{
					expandNodeVec.addElement(new TreePath(currentNode.getPath()));
//				}

				if(i + 1 != bomDataVec.size())
				{
					BOMAssyComponent nextBomComponentData = (BOMAssyComponent)bomDataVec.elementAt(i + 1);
					nextLevelNum = nextBomComponentData.getLevelInt().intValue();
Kogger.debug(getClass(), "@@@@@ nextBomComponentData : " + nextBomComponentData);					
Kogger.debug(getClass(), "@@@@@ nextLevelNum : " + nextLevelNum);				

					if(nextLevelNum == nowLevelNum)
					{
						bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
					}
					else if(nextLevelNum > nowLevelNum)
					{
						parentNode = currentNode;
						bomComponentData.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
					}
					else
					{
						bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
						for(int j = nextLevelNum; j < nowLevelNum; j++)
						{
							parentNode = (BOMTreeNode)parentNode.getParent();
						}
					}
				}
Kogger.debug(getClass(), "@@@@@ parentNode [2] : " + parentNode);						
			}
        }

        Enumeration seqEnum = model.getRootNode().preorderEnumeration();
        int realSeqNumber = 1;		// Root 가 0부터 시작이므로 1로 변경함
        while(seqEnum.hasMoreElements()) 
        {
            BOMAssyComponent component = ((BOMTreeNode)seqEnum.nextElement()).getBOMComponent();
            if(component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE)
            {
                assyComponent = component;
                assyComponent.setSeqInt(new Integer(realSeqNumber++));
            }
        }
        
        for(int j = 0; j < expandNodeVec.size(); j++)
            treeTable.getTree().scrollPathToVisible((TreePath)expandNodeVec.elementAt(j));


        TreePath treepath = new TreePath(parentNode.getPath());
        try {
            treeTable.getTree().fireTreeWillExpand(treepath);
            treeTable.getTree().scrollPathToVisible(treepath);
            treeTable.getTree().fireTreeExpanded(treepath);
            treeTable.getTree().setSelectionPath(treepath);

            treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));

        } catch (Exception ex) {
			Kogger.error(getClass(), ex);
        }
        
        // 새로 그리기.
//        model.fireTreeChanged(parentNode);
        treeTable.repaint();
    }
}
