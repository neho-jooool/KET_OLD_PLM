// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonAssyTreeLoader.java

package e3ps.bom.common.jtreetable;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreePath;

import e3ps.bom.common.component.BOMAssyComponent;
import ext.ket.shared.log.Kogger;

// Referenced classes of package e3ps.bom.common.jtreetable:
//            BOMTreeNode, BOMTreeTableModel, JTreeTable

public class CommonAssyTreeLoader
{

    public CommonAssyTreeLoader(BOMTreeNode node, Vector bomDataVec, JTreeTable treeTable, BOMTreeTableModel model)
    {
        preLevelNum = 0;
        nowLevelNum = 0;
        nextLevelNum = 0;
        rootNode = null;
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
        if(bomDataVec.size() <= 1)
            return;
        parentNode = rootNode;
        Vector expandNodeVec = new Vector();
        try
        {
            for(int i = 0; i < bomDataVec.size(); i++)
            {
                BOMAssyComponent bomComponentData = (BOMAssyComponent)bomDataVec.elementAt(i);
                nowLevelNum = bomComponentData.getLevelInt().intValue();
                currentNode = new BOMTreeNode(parentNode, bomComponentData);
                parentNode.addElement(currentNode);
                // 2011-03-31 [윤도혁 J 요구사항 반영] 전체 레벨 펼쳐지도록 함
//                if(bomComponentData.getLevelInt().intValue() == 1)
                    expandNodeVec.addElement(new TreePath(currentNode.getPath()));
                    
                if(i + 1 != bomDataVec.size())
                {
                    BOMAssyComponent nextBomComponentData = (BOMAssyComponent)bomDataVec.elementAt(i + 1);
                    nextLevelNum = nextBomComponentData.getLevelInt().intValue();
                    if(nextLevelNum == nowLevelNum)
                        bomComponentData.setComponentTypeStr("3");
                    else
                    if(nextLevelNum > nowLevelNum)
                    {
                        parentNode = currentNode;
                        bomComponentData.setComponentTypeStr("1");
                    } else
                    {
                        bomComponentData.setComponentTypeStr("3");
                        for(int j = nextLevelNum; j < nowLevelNum; j++)
                            parentNode = (BOMTreeNode)parentNode.getParent();

                    }
                }
            }

        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
        Enumeration seqEnum = model.getRootNode().preorderEnumeration();
        int realSeqNumber = 1;		// Root 가 0부터 시작이므로 1로 변경함
        while(seqEnum.hasMoreElements()) 
        {
            BOMAssyComponent component = ((BOMTreeNode)seqEnum.nextElement()).getBOMComponent();
            if(component.getComponentTypeStr() == "1" || component.getComponentTypeStr() == "3")
            {
                BOMAssyComponent assyComponent = component;
                assyComponent.setSeqInt(new Integer(realSeqNumber++));
            }
        }
        if(treeTable != null)
        {
            for(int j = 0; j < expandNodeVec.size(); j++)
                treeTable.getTree().scrollPathToVisible((TreePath)expandNodeVec.elementAt(j));

        }
//        model.fireTreeChanged(rootNode);
//        if(treeTable != null)
//            treeTable.repaint();
    }

    public BOMTreeNode getCommonAssyNode()
    {
        return model.getRootNode();
    }

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
}
