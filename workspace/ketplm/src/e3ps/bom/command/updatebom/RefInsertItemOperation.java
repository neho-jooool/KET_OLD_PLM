package e3ps.bom.command.updatebom;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class RefInsertItemOperation
{
    private BOMTreeNode parentNode;
    private BOMTreeNode targetNode;
    BOMAssyComponent rc = null;

    private String itemCodeStr;
    private final int MAX_LEVEL = 20;

    public RefInsertItemOperation(BOMTreeNode parentNode, BOMTreeNode targetNode , String itemCodeStr)
    {
        this.parentNode = parentNode;
        this.targetNode = targetNode;
        this.itemCodeStr = itemCodeStr;
    }

    public void executeOperation() throws java.lang.Exception
    {
        Vector resultList = null;

        try
        {
            BOMSearchDao dao = new BOMSearchDao();
            dao.downwardExplosionCurrentBom(itemCodeStr, "");

            resultList = dao.getResultListVec();

Kogger.debug(getClass(), ">>> result List size : " + resultList.size());
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
            throw ex;
        }

        if (resultList.size() == 0)
        {
            Exception ex = new Exception(itemCodeStr + " is a Item. You can copy child with an Assy.");
            throw ex;
        }

        // Vector 안의 정보를 읽어 들여서.. Node를 구성.
        int nowLevelNum = 0;
        int nextLevelNum = 0;

        BOMTreeNode upperNode = targetNode;
        BOMTreeNode currentNode = null;

        BOMAssyComponent targetComponent = (BOMAssyComponent)targetNode.getBOMComponent();
        if ( resultList.size() != 0 && targetComponent.getComponentTypeStr() != BOMAssyComponent.MODEL_TYPE)
        {
            targetComponent.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
        }
        else if ( targetComponent.getComponentTypeStr() != BOMAssyComponent.MODEL_TYPE)
        {
            // CopyChild를 위해서 조건이 하나 더 늘었음.
            targetComponent.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
        }

        // Sort Order와 Order Flag를 보정.
        int maxSortOrder = 0;
        if (targetNode.getChildCount() == 0)
        {
            maxSortOrder = 1;
        }
        else
        {
            Object[] childNodeArray = targetNode.getChildren();

            for (int x=0; x<childNodeArray.length; x++)
            {
                BOMTreeNode childNode = (BOMTreeNode)childNodeArray[x];
                String s = ((BOMAssyComponent)childNode.getBOMComponent()).getSortOrderStr();
                String ss = s.substring(s.length()-4, s.length());
                int sortNum = (new Integer(ss)).intValue();
                if( maxSortOrder < sortNum)
                    maxSortOrder = sortNum;
            }
            maxSortOrder++;
        }

        int []sortLevel = new int[MAX_LEVEL];
        for(int i=0; i<MAX_LEVEL; i++)
            sortLevel[i] = 0;

        int rootLevel = targetComponent.getLevelInt().intValue();

Kogger.debug(getClass(), "===>> rootLevel" + rootLevel);
Kogger.debug(getClass(), "===>> itemCode" + targetComponent.getItemCodeStr());

        sortLevel[1] = maxSortOrder;

        //DB에 Insert할 수 있는 노드들을 포함시키기 위해서 Vector 생성
        Vector addVec = new Vector();
        Vector addTmpVec = new Vector();

        // 조회 결과를 이용해서 여러가지 값을 세팅한다.
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

            if(parentBomComponent.getItemCodeStr().trim().equals(targetComponent.getItemCodeStr().trim()))
            {
                bomComponentData.setSecondMarkStr("NEW");

                BOMAssyComponent nextAssyComponent = new BOMAssyComponent("TEMP");
                if ( i+1 != resultList.size() )
                    nextAssyComponent = (BOMAssyComponent)resultList.elementAt(i+1);

                if (!bomComponentData.getItemCodeStr().equalsIgnoreCase(nextAssyComponent.getItemCodeStr()) || bomComponentData.getLevelInt().intValue() != nextAssyComponent.getLevelInt().intValue())
                {
                    if (bomComponentData.getStartDate().compareTo(Utility.currentDate()) < 0)
                    {
                        bomComponentData.setStartDate(Utility.currentDate());
                    }
                }
            }

            currentNode = new BOMTreeNode(upperNode, bomComponentData);
            upperNode.addElement(currentNode);

            if ( nowLevelNum == 1)
            {
                addVec.addElement(currentNode);
            }

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
            sortLevel[nowLevelNum]++; // SortLevel 증가.
        }

        BOMTreeNode [] parentsPathNode = targetNode.getPathNode();
        Hashtable parentHash = new Hashtable();
        for (int x=0; x<parentsPathNode.length; x++)
        {
            parentHash.put(parentsPathNode[x].getBOMComponent().getItemCodeStr(), parentsPathNode[x]);
        }

        Enumeration targetEnum = targetNode.preorderEnumeration();
        int i=0;
        while(targetEnum.hasMoreElements())
        {
            BOMTreeNode targetEnumNode = (BOMTreeNode)targetEnum.nextElement();
            i++;
            if (i== 1)
                continue;

            if (parentHash.containsKey(targetEnumNode.getBOMComponent().getItemCodeStr()))
            {
                Exception ex = new Exception(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00143")/*동일한 노드의 자부품입니다. \n 부품코드*/ + " : " + targetEnumNode.getBOMComponent().getItemCodeStr() );
                throw ex;
            }
        }

        // 실제 DB에 Insert.
        try
        {
            BOMSaveDao saveDao = new BOMSaveDao();
            saveDao.saveRefBomList(addVec);
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
            throw ex;
        }

        // Sequence Number 보정한 후에 Total Data Count를 Setting 한다.
        Enumeration parentEnum = parentNode.preorderEnumeration();
        int realSeqNumber = 1;
        while(parentEnum.hasMoreElements())
        {
            BOMAssyComponent component = ((BOMTreeNode)parentEnum.nextElement()).getBOMComponent();
            if (component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE)
            {
                BOMAssyComponent assyComponent = (BOMAssyComponent)component;
                assyComponent.setSeqInt(new Integer(realSeqNumber++));
            }
        }

        BOMBasicInfoPool.setPublicTotalDataCount(realSeqNumber);
    }
}
