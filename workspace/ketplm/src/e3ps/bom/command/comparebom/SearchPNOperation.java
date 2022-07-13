package e3ps.bom.command.comparebom;

import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreePath;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.framework.aif.AbstractAIFOperation;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import ext.ket.shared.log.Kogger;

public class SearchPNOperation extends AbstractAIFOperation 
{
    AbstractAIFUIApplication app;
    private BOMTreeNode parentNode;
    private BOMTreeNode targetNode;
    private BOMTreeTableModel model;
    private JTreeTable treetable;

    private String itemCodeStr;

    private final int MAX_LEVEL = 30;   // 최대 Level 수를 설정.

    public SearchPNOperation(BOMTreeNode targetNode , String itemCodeStr, BOMTreeTableModel model, JTreeTable treetable)
	{
        this.parentNode = targetNode;
        this.targetNode = targetNode;
        this.itemCodeStr = itemCodeStr;
        this.model = model;
        this.treetable = treetable;
    }

    public void executeOperation() throws java.lang.Exception 
	{
        Vector resultList = new Vector();

        try 
		{
			BOMSearchDao searchDao = new BOMSearchDao();
			searchDao.downwardExplosionCurrentBom(itemCodeStr, "");				

			resultList = searchDao.getResultListVec();
        }
		catch(Exception ex) 
		{
        	Kogger.error(getClass(), ex);
            throw ex;
        }

        // Vector 안의 정보를 읽어 들여서.. Node를 구성.
        int preLevelNum = 0;
        int nowLevelNum = 0;
        int nextLevelNum = 0;

        BOMTreeNode upperNode = targetNode;
        BOMTreeNode currentNode = null;
        BOMTreeNode nextNode = null;

        BOMAssyComponent targetComponent = (BOMAssyComponent)targetNode.getBOMComponent();
        if ( resultList.size() != 0) 
		{
//            targetComponent.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
            targetComponent.setComponentTypeStr("2");  // Root Node 는 2여야 함 
        }
		else 
		{
            targetComponent.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
        }
        
		targetComponent.setLevelInt(0);		// 2011-02-22  [임승영D 요구사항으로 수정] BOM 조회 시, Root Level 은 0으로 셋팅되어야 함
		targetComponent.setSeqInt(0);		// 2011-03-30  [임승영D 요구사항으로 수정] BOM 조회 시, Root SEQ 는 0으로 셋팅되어야 함

        Vector expandNodeVec = new Vector();

        // Sort Order와 Order Flag를 보정.
        String rootSortOrder = targetComponent.getSortOrderStr();
        int []sortLevel = new int[MAX_LEVEL];
        for(int i=0; i<MAX_LEVEL; i++)
            sortLevel[i] = 0;

        int rootLevel = targetComponent.getLevelInt().intValue();

        for (int i=0; i<resultList.size(); i++) 
		{
            BOMAssyComponent bomComponentData = (BOMAssyComponent)resultList.elementAt(i);
            nowLevelNum = bomComponentData.getLevelInt().intValue();
            bomComponentData.setLevelInt(new Integer(nowLevelNum + rootLevel));

            sortLevel[nowLevelNum]++; // SortLevel 증가.

            BOMAssyComponent parentBomComponent = (BOMAssyComponent)upperNode.getBOMComponent();
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
            bomComponentData.setParentItemCodeStr(parentBomComponent.getItemCodeStr());

            currentNode = new BOMTreeNode(upperNode, bomComponentData);
            upperNode.addElement(currentNode);

//            if ( nowLevelNum == 1)  // 전체레벨 확장을 위해 주석처리함 
                expandNodeVec.addElement(new TreePath(currentNode.getPath()));

            if (i+1 != resultList.size()) 
			{
                // 제일 마지막 Data가 아닌 경우.
                BOMAssyComponent nextBomComponentData = (BOMAssyComponent)resultList.elementAt(i+1);
                nextLevelNum = nextBomComponentData.getLevelInt().intValue();
                if (nextLevelNum == nowLevelNum) 
				{
                    // 현재 Parent의 Node를 공유.
                    bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
                }
				else if (nextLevelNum > nowLevelNum) 
				{
                    // CurrentNode가 parent가 된다.
                    upperNode = currentNode;
                    bomComponentData.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
                }
				else 
				{
                    // NextBomComponentData의 Parent를 구한다.
                    bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
                    for (int j=nextLevelNum; j<nowLevelNum; j++) 
					{
                        upperNode = (BOMTreeNode)upperNode.getParent();
                        sortLevel[j+1] = 0;    // Sort Order Level 을 0으로 초기화
                    }
                }
            }
			else 
			{}
        }

        // Sequence Number 보정
        Enumeration enum0 = model.getRootNode().preorderEnumeration();
        int realSeqNumber = 1;
        while(enum0.hasMoreElements()) 
		{
//Kogger.debug(getClass(), "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ aasss : "+realSeqNumber);        	
            BOMAssyComponent component = ((BOMTreeNode)enum0.nextElement()).getBOMComponent();
            if (component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE) 
			{
                BOMAssyComponent assyComponent = (BOMAssyComponent)component;
                assyComponent.setSeqInt(new Integer(realSeqNumber++));
            }
        }

        for(int j=0; j<expandNodeVec.size(); j++)
            treetable.getTree().scrollPathToVisible((TreePath)expandNodeVec.elementAt(j));

        // 새로 그리기.
//        model.fireTreeChanged(parentNode);
//        treetable.repaint();

        TreePath treepath = new TreePath(targetNode.getPath());
        try 
		{
            treetable.getTree().fireTreeWillExpand(treepath);
            treetable.getTree().scrollPathToVisible(treepath);
            treetable.getTree().fireTreeExpanded(treepath);
            treetable.getTree().setSelectionPath(treepath);

            treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
        }
		catch (Exception ex) 
		{
			Kogger.error(getClass(), ex);
        }		
    }
    
}
