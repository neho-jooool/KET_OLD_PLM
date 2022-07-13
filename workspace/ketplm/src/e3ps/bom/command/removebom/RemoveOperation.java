package e3ps.bom.command.removebom;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.tree.TreePath;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.CheckSameNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.framework.aif.AbstractAIFOperation;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import ext.ket.shared.log.Kogger;

public class RemoveOperation extends AbstractAIFOperation 
{
	BOMRegisterApplicationPanel pnl;
    AbstractAIFUIApplication app;
	private BOMTreeNode[] nodes;
    private BOMTreeTableModel model = null;
    private JTreeTable treeTable = null;
    private Connection connection = null;
    
	Registry appReg;
	String dbName = "";

    int selectedRow = 0;
	int checkStatus = 0;
	int userStatus = 0;
	String bomStatus = "";
	int myStatus = 0;
	String ownerId = "";
	Vector adjustVec = new Vector();
	Vector coWorkerVec = new Vector();
	String userInfo = "";

	boolean bomGubunFlag = false;
	
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");	
	
	//shin....
	private BOMCheckOutInDao bcoi = new BOMCheckOutInDao();

    public RemoveOperation(Connection connection, AbstractAIFUIApplication app, BOMTreeNode[] nodes, BOMTreeTableModel model, JTreeTable treeTable) 
	{
		this.connection = connection;
        this.app = app;
        this.nodes = nodes;
        this.model = model;
        this.treeTable = treeTable;

	    int selectedRows[] = treeTable.getTree().getSelectionRows();
		selectedRow = selectedRows[0];

		pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();

		appReg = Registry.getRegistry(app);
		dbName = appReg.getString("plm");
    }

    public void executeOperation() throws Exception 
	{
        // 선택된 Node가 Null 아닌 경우에만 Clipboard에 저장한다.
        if(!validationCheck()) 
		{
            MessageBox m = new MessageBox(new JFrame(), messageRegistry.getString("remove2"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }

		pnl.setCursor(Cursor.getPredefinedCursor(3));

        BOMTreeNode willSelectedNode = null;
		bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;
		userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
//		userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserDept()) + "/" + Utility.checkNVL(BOMBasicInfoPool.getUserName());
		Vector removeVec = new Vector();

		try
		{
			for(int i=0; i<nodes.length; i++) 
			{
				// 현재 BOM Model 내에서 같은 Item Code, Parent Item Code 를 가진 node가 있는지 조사하여 모든 변화를 같이 적용시킨다.
				BOMAssyComponent selectedComponent = nodes[i].getBOMComponent();
				CheckSameNode check = new CheckSameNode(model.getRootNode().preorderEnumeration(), selectedComponent);
				Vector sameNodeResult = check.getResultList();

				for(int x=0; x<sameNodeResult.size(); x++) 
				{
					BOMTreeNode sameNode = (BOMTreeNode)sameNodeResult.elementAt(x);
					BOMAssyComponent bomassycomponent = sameNode.getBOMComponent();

					BOMTreeNode [] parents = sameNode.getPathNode();
					BOMTreeNode parentNode = parents[parents.length -2];

Kogger.debug(getClass(), "Remove ItemCode : " + sameNode.getBOMComponent().getItemCodeStr());
					parentNode.removeElement(sameNode);

					BOMSaveDao saveDao = new BOMSaveDao();
					if(bomGubunFlag)
					{
						saveDao.removeBomList(connection, bomassycomponent.getSortOrderStr());
					}

					// 부모 노드가 하위 Part를 가지고 있지 않는 경우 Type을 바꿔준다.
					if(parentNode.getBOMComponent().getComponentTypeStr() != BOMAssyComponent.MODEL_TYPE && parentNode.getChildCount() ==0) 
					{
						BOMAssyComponent bomcomponent = parentNode.getBOMComponent();
						bomcomponent.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
						bomcomponent.setFirstMarkStr("");

						if(bomGubunFlag)
						{
							saveDao.addItemUpdate(connection, bomcomponent);
						}
					}

Kogger.debug(getClass(), "Remove Checkouter : " + bomassycomponent.getCheckOutStr());
					if( Utility.checkNVL(bomassycomponent.getCheckOutStr()).equals(userInfo) )
					{
						removeVec.addElement(Utility.checkNVL(bomassycomponent.getItemCodeStr()));
						
						//shin....
						bcoi.setCheckIn( bomassycomponent.getItemCodeStr() , BOMBasicInfoPool.getUserId() );
					}

					model.fireTreeChanged(parentNode);

					if(nodes[i] == sameNode && i==0 && x==0)
						willSelectedNode = parentNode;
				}
			}
			
			connection.commit();		// 커밋을 안하니까 .. 편집시 rock 걸리는 현상 발생함 
			
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

			Enumeration enum0 = model.getRootNode().preorderEnumeration();
			int realSeqNumber = 1;
			BOMAssyComponent component = null;
			while(enum0.hasMoreElements()) 
			{
				component = ((BOMTreeNode)enum0.nextElement()).getBOMComponent();
				if(component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE) 
				{
					BOMAssyComponent assyComponent = (BOMAssyComponent)component;
					assyComponent.setSeqInt(new Integer(realSeqNumber++));
				}
			}

			BOMBasicInfoPool.setPublicTotalDataCount(realSeqNumber);
			
			try 
			{
				TreePath treepath = new TreePath(willSelectedNode.getPath());

				treeTable.getTree().fireTreeWillExpand(treepath);
				treeTable.getTree().scrollPathToVisible(treepath);
				treeTable.getTree().fireTreeExpanded(treepath);
				treeTable.getTree().setSelectionRow(selectedRow - 1);

				treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));
			}
			catch(Exception ex) {}
			
			// 전체레벨 펼치기 
			treeTable.getTree().setSelectionRow(0);
			pnl.mainEditorPane.expandTreeTable(20);
		}
		catch(Exception ex) 
		{
			connection.rollback();
			Kogger.error(getClass(), ex);
			pnl.setCursor(Cursor.getPredefinedCursor(0));

			throw ex;
		}

        treeTable.repaint();
		pnl.setCursor(Cursor.getPredefinedCursor(0));
    }

    public boolean validationCheck() 
	{
        BOMAssyComponent bomcomponent = nodes[0].getBOMComponent();
        if(bomcomponent.getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE) 
		{
            return false;
        }
        return true;
    }
}
