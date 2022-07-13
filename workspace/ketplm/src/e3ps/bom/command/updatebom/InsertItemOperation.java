package e3ps.bom.command.updatebom;

import java.awt.Rectangle;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.tree.TreePath;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class InsertItemOperation
{
    private BOMTreeNode parentNode;
    private BOMTreeNode targetNode;
    private BOMTreeTableModel model;
    private JTreeTable treetable;
    private Connection connection = null;

    private String itemCode;
    private boolean isRepaint;
    private final int MAX_LEVEL = 30;

    Registry registry = Registry.getRegistry("e3ps.bom.bom");
    boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

    public InsertItemOperation(Connection connection, BOMTreeNode parentNode, BOMTreeNode targetNode , String itemCode, BOMTreeTableModel model, JTreeTable treetable, boolean isRepaint)
    {
        this.connection = connection;
        this.parentNode = parentNode;
        this.targetNode = targetNode;
        this.itemCode = itemCode;
        this.model = model;
        this.treetable = treetable;
        this.isRepaint = isRepaint;
    }

    public void executeOperation() throws Exception
    {
        long dataCount = 0;
        Vector resultList = null;

        try
        {
            // insert 할 Old Assy 의 하위를 조회한다.
            BOMSearchDao dao = new BOMSearchDao();
            dao.downwardExplosionCurrentBom(itemCode, "");

            dataCount = dao.getDataCount();
            resultList = dao.getResultListVec();
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
        if(dataCount != 0 && targetComponent.getComponentTypeStr() != BOMAssyComponent.MODEL_TYPE)
        {
            targetComponent.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
        }
        else
        {
            targetComponent.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
        }

        Vector expandNodeVec = new Vector();

        try
        {
            if(dataCount !=0)
            {
                // Sort Order와 Order Flag를 보정.
                String rootSortOrder = targetComponent.getSortOrderStr();
                int []sortLevel = new int[MAX_LEVEL];
                for(int i=0; i<MAX_LEVEL; i++)
                    sortLevel[i] = 0;

                int rootLevel = targetComponent.getLevelInt().intValue();

                for(int i=0; i<resultList.size(); i++)
                {
                    BOMAssyComponent bomComponentData = (BOMAssyComponent)resultList.elementAt(i);
                    nowLevelNum = bomComponentData.getLevelInt().intValue();
                    bomComponentData.setLevelInt(new Integer(nowLevelNum + rootLevel));

                    sortLevel[nowLevelNum]++; // SortLevel 증가.

                    BOMAssyComponent parentBomComponent = (BOMAssyComponent)upperNode.getBOMComponent();
                    String sortOrder = parentBomComponent.getSortOrderStr();

                    if(sortLevel[nowLevelNum] < 10)
                        sortOrder = sortOrder + "000" + sortLevel[nowLevelNum];
                    else if(sortLevel[nowLevelNum] < 100)
                        sortOrder = sortOrder + "00" + sortLevel[nowLevelNum];
                    else if(sortLevel[nowLevelNum] < 1000)
                        sortOrder = sortOrder + "0" + sortLevel[nowLevelNum];
                    else if(sortLevel[nowLevelNum] < 10000)
                        sortOrder = sortOrder + sortLevel[nowLevelNum];

                    bomComponentData.setSortOrderStr(sortOrder);
                    bomComponentData.setParentItemCodeStr(parentBomComponent.getItemCodeStr());

                    currentNode = new BOMTreeNode(upperNode, bomComponentData);
                    upperNode.addElement(currentNode);

                    if(i+1 != resultList.size())
                    {
                        // 제일 마지막 Data가 아닌 경우.
                        BOMAssyComponent nextBomComponentData = (BOMAssyComponent)resultList.elementAt(i+1);
                        nextLevelNum = nextBomComponentData.getLevelInt().intValue();
                        if(nextLevelNum == nowLevelNum)
                        {
                            // 현재 Parent의 Node를 공유.
                            bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
                        }
                        else if(nextLevelNum > nowLevelNum)
                        {
                            // CurrentNode가 parent가 된다.
                            upperNode = currentNode;
                            bomComponentData.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
                        }
                        else
                        {
                            // NextBomComponentData의 Parent를 구한다.
                            bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
                            for(int j=nextLevelNum; j<nowLevelNum; j++)
                            {
                                upperNode = (BOMTreeNode)upperNode.getParent();
                                sortLevel[j+1] = 0;    // Sort Order Level 을 0으로 초기화
                            }
                        }
                    }
                    else
                    {}
                }
            }

            // 붙이고자 하는 Assy의 하위 구조에 있는 모든 Item Code 중에서 상위 노드와 같은 Item Code 가 있는 경우에는 Error 처리.
            BOMTreeNode [] parentsPathNode = parentNode.getPathNode();
            Hashtable parentHash = new Hashtable();
            for(int x=0; x<parentsPathNode.length; x++)
            {
                parentHash.put(parentsPathNode[x].getBOMComponent().getItemCodeStr(), parentsPathNode[x]);
            }

            Enumeration targetEnum = targetNode.preorderEnumeration();
            while(targetEnum.hasMoreElements())
            {
                BOMTreeNode targetEnumNode = (BOMTreeNode)targetEnum.nextElement();
                if(parentHash.containsKey(targetEnumNode.getBOMComponent().getItemCodeStr()))
                {
                    // 같은 노드 발견 된 경우.. 에러 발생을 시켜야함.
                    Exception ex = new Exception(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00143")/*동일한 노드의 자부품입니다. \n 부품코드*/ + " : " + targetEnumNode.getBOMComponent().getItemCodeStr() );
                    throw ex;
                }
            }

            if(bomGubunFlag)
            {
                Enumeration enum0 = targetNode.preorderEnumeration();
                Vector bomVec = new Vector();
                while(enum0.hasMoreElements())
                {
                    BOMTreeNode node = (BOMTreeNode)enum0.nextElement();
                    BOMAssyComponent ac = (BOMAssyComponent)node.getBOMComponent();
                    if(!ac.getItemCodeStr().trim().equals(BOMBasicInfoPool.getPublicModelName().trim()))
                    {
                        bomVec.add(ac);
                    }
                }
//Kogger.debug(getClass(), "######### bomVec [InsertItemOperation] : " + bomVec);
                BOMSaveDao saveDao = new BOMSaveDao();
                saveDao.saveBomList(connection, BOMBasicInfoPool.getPublicModelName().trim(), bomVec);
            }
        }
        catch(Exception ex)
        {
            // ----------------------- Component rollback -----------------------------------------
            parentNode.removeElement(targetNode);
            if(parentNode.getChildCount()==0)
            {
                if(parentNode.getBOMComponent().getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE)
                {
                    parentNode.getBOMComponent().setComponentTypeStr(BOMAssyComponent.MODEL_TYPE);
                }
                else
                {
                    parentNode.getBOMComponent().setComponentTypeStr(BOMAssyComponent.PART_TYPE);
                }
            }
            else
            {
                if(parentNode.getBOMComponent().getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE)
                {
                    parentNode.getBOMComponent().setComponentTypeStr(BOMAssyComponent.MODEL_TYPE);
                }
                else
                {
                    parentNode.getBOMComponent().setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
                }
            }
            // ----------------------- Component rollback -----------------------------------------
            Kogger.error(getClass(), ex);
            throw ex;
        }

        // Sequence Number 보정한 후에 Total Data Count를 Setting 한다.
        Enumeration enum0 = model.getRootNode().preorderEnumeration();
        int realSeqNumber = 1;
        while(enum0.hasMoreElements())
        {
            BOMAssyComponent component = ((BOMTreeNode)enum0.nextElement()).getBOMComponent();
            if (component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE)
            {
                BOMAssyComponent assyComponent = (BOMAssyComponent)component;
                assyComponent.setSeqInt(new Integer(realSeqNumber++));
            }
        }

        BOMBasicInfoPool.setPublicTotalDataCount(realSeqNumber);

        model.fireTreeChanged(parentNode);
        treetable.repaint();

        if(isRepaint)
        {
            TreePath treepath = new TreePath(targetNode.getPath());
            try
            {
                treetable.getTree().fireTreeWillExpand(treepath);
                treetable.getTree().scrollPathToVisible(treepath);
                treetable.getTree().fireTreeExpanded(treepath);
                treetable.getTree().setSelectionPath(treepath);

                treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
            }
            catch(Exception ex)
            {
                Kogger.error(getClass(), ex);
                throw ex;
            }
        }
    }
}
