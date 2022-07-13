package e3ps.bom.command.removebom;

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
import e3ps.bom.common.util.CheckSameNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.service.KETBomHelper;
import ext.ket.shared.log.Kogger;

/////////////////////////////////////////////////////////////////////////////
// RemoveOperationWithoutCommit.java
// : 01. 이 클래스는 내부 로직이 DeleteOperation과 거의 같다.
// : 02. 사용 용도는 Change Old P/N를 수행하기 위해서 이전 값을 Delete 하여야 하는데 이를 수행하기 위해서 별도로 만들어진 클래스이다.
// : 03. Remove 를 하게되면 Parent Item Code 의 값이 바뀌게 되어 있는데, 이 경우에는 선택된 Assy가 삭제되고 난 후에는 다시 새로운 Item Code 가 붙어 질 것이므로 부모 Item Code 를 수정할 필요가 없다.
// : 04. 변경 리스트는 Change Old P/N 으로 기록될 것이므로 별도로 삭제 변경리스트를 작성할 필요가 없다.

public class RemoveOperationWithoutCommit 
{
    private BOMTreeNode[] nodes;
    private BOMTreeTableModel model = null;
    private JTreeTable treeTable = null;
    AbstractAIFUIApplication app;
    private Connection connection = null;

	boolean bomGubunFlag = false;
	String userInfo = "";

    public RemoveOperationWithoutCommit(Connection connection, AbstractAIFUIApplication app, BOMTreeNode[] nodes, BOMTreeTableModel model, JTreeTable treeTable) 
	{
		this.connection = connection;
        this.app = app;
        this.nodes = nodes;
        this.model = model;
        this.treeTable = treeTable;
    }

    public void executeOperation() throws Exception 
	{
        // Validation 관련 처리는 이 클래스를 호출하는 클래스에서 이미 모두 다 처리해야함.

        BOMTreeNode willSelectedNode = null;
		bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;
		userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
		Vector removeVec = new Vector();

		try
		{
			for(int i=0; i<nodes.length; i++) 
			{
				// 현재 BOM Model 내에.. 같은 Item Code, Parent Item Code 를 가진 node가 있는지 조사하여 모든 변화를 같이 적용시킨다.
				BOMAssyComponent selectedComponent = nodes[i].getBOMComponent();
				CheckSameNode check = new CheckSameNode(model.getRootNode().preorderEnumeration(), selectedComponent);
				Vector sameNodeResult = check.getResultList();

				for (int x=0; x<sameNodeResult.size(); x++) 
				{
					BOMTreeNode sameNode = (BOMTreeNode)sameNodeResult.elementAt(x);
					BOMAssyComponent bomassycomponent = sameNode.getBOMComponent();

Kogger.debug(getClass(), "Remove Checkouter : " + bomassycomponent.getCheckOutStr());
Kogger.debug(getClass(), "Remove userInfo : " + userInfo);
	
					if( Utility.checkNVL(bomassycomponent.getCheckOutStr()).equals(userInfo) )
					{
						removeVec.addElement(Utility.checkNVL(bomassycomponent.getItemCodeStr()));
					}

					BOMTreeNode [] parents = sameNode.getPathNode();
					BOMTreeNode parentNode = parents[parents.length -2];

					parentNode.removeElement(sameNode);

					BOMSaveDao saveDao = new BOMSaveDao();
					if(bomGubunFlag)
					{
						saveDao.removeBomList(connection, bomassycomponent.getSortOrderStr());
					}

					model.fireTreeChanged(parentNode);
					if (nodes[i] == sameNode && i==0 && x==0 )
					{
						willSelectedNode = parentNode;
					}
				}
			}

	Kogger.debug(getClass(), "====>> removeVec : " + removeVec.toString());			
			if(removeVec.size() > 0)
			{
				Hashtable inputParams = new Hashtable();
				inputParams.put("bomOid", Utility.checkNVL(BOMBasicInfoPool.getPublicBOMOid()));
				inputParams.put("flag", "Y");
				inputParams.put("itemCode", removeVec);

				if(bomGubunFlag)
				{
					inputParams.put("bomEcoFlag", "N");
				}
				else
				{
					inputParams.put("bomEcoFlag", "Y");
				}
							
				KETBomHelper.service.setCoworkerCancelCheckOut(inputParams);
			}
		}
		catch(Exception ex) 
		{
			Kogger.error(getClass(), ex);

			throw ex;
		}

		try 
		{
			TreePath treepath = new TreePath(willSelectedNode.getPath());
			treeTable.getTree().fireTreeWillExpand(treepath);
			treeTable.getTree().scrollPathToVisible(treepath);
			treeTable.getTree().fireTreeExpanded(treepath);
			treeTable.getTree().setSelectionPath(treepath);

			treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));
		}
		catch (Exception ex) {}

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
		
        treeTable.repaint();
    }

}
