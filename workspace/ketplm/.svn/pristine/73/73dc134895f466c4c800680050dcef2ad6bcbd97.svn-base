package e3ps.bom.command.checkin;

import java.awt.Cursor;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.savebom.SaveBOMCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import ext.ket.shared.log.Kogger;

public class CheckInCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
	BOMRegisterApplicationPanel pnl;
	BOMTreeNode rootNode;
	
    DBConnectionManager	res = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

	String strCurStatus = "";
	String userInfo = "";
	boolean bomGubunFlag = false;

	Registry appReg;

    public CheckInCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = frame;

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();

		appReg = Registry.getRegistry(app);

		strCurStatus = BOMBasicInfoPool.getPublicBOMStatus();
	}

    protected void executeCommand() throws Exception
    {
		pnl.setCursor(Cursor.getPredefinedCursor(3));

		bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

		BOMTreeNode[] nodes = pnl.getSelectedTreeNode();

		userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
//		userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserDept()) + "/" + Utility.checkNVL(BOMBasicInfoPool.getUserName());
		BOMTreeNode[] selectedNode = pnl.getSelectedNode();

		if (nodes == null) 
		{
			pnl.setCursor(Cursor.getPredefinedCursor(0));
			MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkin0"), "Error", MessageBox.ERROR);
			m.setModal(true);
			m.setVisible(true);
			return;
		}
		else
		{
			// BOM ECO CheckIn 에서 사용하는 로직
			if(!bomGubunFlag)
			{
				if (nodes.length != 1) 
				{
					pnl.setCursor(Cursor.getPredefinedCursor(0));
					MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("selectRow3"), "Error", MessageBox.ERROR);
					m.setVisible(true);
					m.setModal(true);
					return;
				}

				BOMAssyComponent selectedComponent = nodes[0].getBOMComponent();
Kogger.debug(getClass(), ">>> Component Item Code : " + selectedComponent.getItemCodeStr());
Kogger.debug(getClass(), ">>> ModelName : " + BOMBasicInfoPool.getPublicModelName().trim());
				if( !( ( selectedComponent.getParentItemCodeStr().equals(BOMBasicInfoPool.getPublicModelName().trim()) ) || 
				      ( selectedComponent.getItemCodeStr().equals(BOMBasicInfoPool.getPublicModelName().trim()) ) ) 
				  )
				{
					pnl.setCursor(Cursor.getPredefinedCursor(0));
					MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkin1"), "Error", MessageBox.ERROR);
					m.setVisible(true);
					m.setModal(true);
					return;
				}
			}

			// Admin or 멤버가 아니면
			if(!isMember(bomGubunFlag) && !BOMBasicInfoPool.isAdmin())
			{
				pnl.setCursor(Cursor.getPredefinedCursor(0));
				MessageBox mbox = new MessageBox(parent, messageRegistry.getString("notCoworker"), "Error", 0);
				mbox.setModal(true);
				mbox.setVisible(true);
				return;
			}

Kogger.debug(getClass(), "================ CheckInCmd Not RootNode ==================");
			for (int inx = 0; inx < selectedNode.length; inx++)
			{
				BOMTreeNode node = selectedNode[inx];
				BOMAssyComponent selectedComponent = node.getBOMComponent();

				if (selectedComponent.getCheckOutStr().equalsIgnoreCase ("")) 
				{
					pnl.setCursor(Cursor.getPredefinedCursor(0));
					MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkin2"), "Error", MessageBox.ERROR);
					m.setModal(true);
					m.setVisible(true);
					return;
				}
			}
Kogger.debug(getClass(), "================ CheckInCmd Not RootNode ==================");

			boolean isCheckOut = false;
			Vector itemCodeVec = new Vector();

Kogger.debug(getClass(), "===>> selectedNode.length : " + selectedNode.length);

			for (int inx = 0; inx < selectedNode.length; inx++)
			{
                BOMTreeNode node = selectedNode[inx];
				BOMAssyComponent selectedComponent = node.getBOMComponent();

				if ( !(( selectedComponent.getCheckOutStr()==null?"":selectedComponent.getCheckOutStr().toString().trim() ).equals(userInfo)) )
				{
					isCheckOut = true;
					pnl.setCursor(Cursor.getPredefinedCursor(0));
					MessageBox mbox = new MessageBox(parent, messageRegistry.getString("checkin3"), "Error", 0);
					mbox.setModal(true);
					mbox.setVisible(true);
					return;
				}
				else
				{
					if ( ( selectedComponent.getCheckOutStr()==null?"":selectedComponent.getCheckOutStr().toString().trim() ).equals("") )
					{
						isCheckOut = true;
						pnl.setCursor(Cursor.getPredefinedCursor(0));
						MessageBox mbox = new MessageBox(parent, messageRegistry.getString("checkin4"), "Error", 0);
						mbox.setModal(true);
						mbox.setVisible(true);
						return;
					}
				}

Kogger.debug(getClass(), "===>> selectedComponent.getItemCodeStr() : " + selectedComponent.getItemCodeStr());
				itemCodeVec.addElement(selectedComponent.getItemCodeStr());
			}

			if(!isCheckOut)
			{
				if( strCurStatus.equalsIgnoreCase("INWORK") || strCurStatus.equalsIgnoreCase("REWORK") ) 
				{
					checkIn(itemCodeVec, bomGubunFlag);
					
					//add shin...................................................................................................................................................
					BOMCheckOutInDao bcoi = new BOMCheckOutInDao();
					for(int i=0;i<itemCodeVec.size();i++)
					{
						bcoi.setCheckIn( (String)itemCodeVec.elementAt(i), BOMBasicInfoPool.getUserId() );
					}
					//.....................................................................................................................................................................
					
					pnl.setCursor(Cursor.getPredefinedCursor(0));
					MessageBox mbox = new MessageBox(parent, messageRegistry.getString("checkin5"), "Check-In", 1);
					mbox.setModal(true);
					mbox.setVisible(true);
					return;
				}
				else if(strCurStatus.equalsIgnoreCase("UNDERREVIEW"))
				{
					if(!BOMBasicInfoPool.isAdmin())	// Admin 아니면
					{
						pnl.setCursor(Cursor.getPredefinedCursor(0));
						MessageBox mbox = new MessageBox(parent, messageRegistry.getString("underApproveStatus"), "Error", 0);
						mbox.setModal(true);
						mbox.setVisible(true);
						return;
					}
					else // Admin 이면
					{
						checkIn(itemCodeVec, bomGubunFlag);
						pnl.setCursor(Cursor.getPredefinedCursor(0));
						MessageBox mbox = new MessageBox(parent, messageRegistry.getString("checkin5"), "Check-In", 1);
						mbox.setModal(true);
						mbox.setVisible(true);
						return;
					}
				}
				else if(strCurStatus.equalsIgnoreCase("APPROVED")) 
				{
					pnl.setCursor(Cursor.getPredefinedCursor(0));
					MessageBox mbox = new MessageBox(parent, messageRegistry.getString("completedStatus"), "Error", 0);
					mbox.setModal(true);
					mbox.setVisible(true);
					return;
				}
			}
		}
		pnl.setCursor(Cursor.getPredefinedCursor(0));
    }

	private boolean isMember(boolean flag) 
	{
		String SQL = "";
		boolean memberFlag = false;

		try
		{
			connectDB(appReg.getString("plm"));

			if(flag)
			{
				SQL =  " SELECT newbomcode 																							";
				SQL += " FROM ketbomcoworker 																						";
				SQL += " WHERE newbomcode = '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName()) + "' 		";
				SQL += " AND coworkerid = '" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'							";
			}
			else
			{
				SQL =  " SELECT ecoheadernumber 																						";
				SQL += " FROM ketbomecocoworker 																					";
				SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'	";
				SQL += " AND coworkerid = '" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'							";
			}

			rs = stmt.executeQuery(SQL);

			if (rs.next())
				memberFlag = true;
			else
				memberFlag = false;
		}
		catch(Exception e) 
		{
			Kogger.error(getClass(), e);
		}
		finally 
		{
			try
			{
				closeDB(appReg.getString("plm"));
			}
			catch(Exception e){}
			return memberFlag;
		}
	}

    private String workingStatusUpdate(boolean flag)
    {
        String status = "";
        String SQL = "";
        try
        {
            connectDB(appReg.getString("plm"));
Kogger.debug(getClass(), "@@@@@ ECOITEMCODE : " + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName()) );

			if(flag)
			{
				SQL =  "UPDATE ketbomcoworker																					";
				SQL += "SET endWorkingFlag = '2'																					";
				SQL += "WHERE newbomcode = '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName()) + "'	";
				SQL += "AND coworkerid = '" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'						";
				SQL += "AND endWorkingFlag <> '4'																				"; //shin...add
			}
			else
			{
				SQL =  "UPDATE ketbomecocoworker																					";
				SQL += "SET endWorkingFlag = '2'																						";
				SQL += "WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'	";
				SQL += "AND ecoitemcode = '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName()) + "'			";
				SQL += "AND coworkerid = '" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'							";
				SQL += "AND endWorkingFlag <> '4'																					";
			}

            stmt.executeUpdate(SQL);

			conn.commit();
        }
        catch(Exception e) 
        {
			conn.rollback();
            Kogger.error(getClass(), e);
        }
        finally 
        {
            try 
            {
                 closeDB(appReg.getString("plm"));
			}
            catch(Exception e){}
            return status;
        }
    }

    public void checkIn(Vector checkOutItemVec, boolean flag)
    {
        try
        {
			pnl.setCursor(Cursor.getPredefinedCursor(3));

			BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();
			JTreeTable treeTable = pnl.getTreeTable();

			rootNode = (BOMTreeNode)model.getRootNode();
			Enumeration rootEnum = rootNode.preorderEnumeration();
			Enumeration rootEnum1 = rootNode.preorderEnumeration();
			Enumeration rootEnum2 = rootNode.preorderEnumeration();
			Enumeration rootEnum3 = rootNode.preorderEnumeration();
			boolean isMyAllCheckIn = true;
			boolean isBomAllCheckIn = true;
			Vector checkVec = new Vector();
			String checkOutFlag = "";
			int idx = 0;
			String coworkerItem = "";
			String checkInItem = "";

			while (rootEnum.hasMoreElements()) 
			{
                BOMTreeNode treeNode = (BOMTreeNode)rootEnum.nextElement();
				BOMAssyComponent bomComponent = treeNode.getBOMComponent();

				checkVec.addElement(bomComponent.getItemCodeStr()==null?"":(bomComponent.getItemCodeStr().toString().trim()));
			}

			Vector coworkerVec = KETBomHelper.service.getCheckOuter(checkVec);

			String checkOutItemStr = "";
			for(int i=0; i<coworkerVec.size(); i++)
			{
				checkOutItemStr = coworkerVec.elementAt(i).toString().substring(0, coworkerVec.elementAt(i).toString().indexOf("|"));
				if(checkOutItemVec.toString().indexOf(checkOutItemStr) == -1)
				{
					checkOutFlag = "Y";
					isBomAllCheckIn = false;
					break;
				}
			}

Kogger.debug(getClass(), "===>> isBomAllCheckIn : " + isBomAllCheckIn);

			if(!isBomAllCheckIn)
			{
				String iStr = "";
				String cStr = "";

				while (rootEnum1.hasMoreElements()) 
				{
					BOMTreeNode treeNode = (BOMTreeNode)rootEnum1.nextElement();
					BOMAssyComponent bomComponent = treeNode.getBOMComponent();

					iStr = bomComponent.getItemCodeStr() == null ? "" : bomComponent.getItemCodeStr().trim();
					cStr = bomComponent.getCheckOutStr() == null ? "" : bomComponent.getCheckOutStr().trim();

					if( cStr.equalsIgnoreCase(userInfo) )
					{
						if(checkOutItemVec.toString().indexOf(iStr) == -1)
						{
							isMyAllCheckIn = false;
						}
					}
				}
			}

Kogger.debug(getClass(), "===>> isMyAllCheckIn : " + isMyAllCheckIn);

String bomoid = "";
if(BOMBasicInfoPool.getPublicBOMOid() != null)
{
	bomoid = BOMBasicInfoPool.getPublicBOMOid();
}

			Hashtable inputParams = new Hashtable();
			inputParams.put("bomOid", bomoid);
			inputParams.put("flag", checkOutFlag);
			inputParams.put("itemCode", checkOutItemVec);

			if(flag)
			{
				inputParams.put("bomEcoFlag", "N");
			}
			else
			{
				inputParams.put("bomEcoFlag", "Y");
			}
						
			// TODO 기능확인 필요:: 잠시 주석처리함
//			KETBomHelper.service.setCoworkerCheckIn(inputParams);

			Vector version = new Vector();
			Vector itemVec = new Vector();
			Hashtable itemHash = new Hashtable();

			while (rootEnum2.hasMoreElements()) 
			{
                BOMTreeNode treeNode = (BOMTreeNode)rootEnum2.nextElement();
				BOMAssyComponent bomComponent = treeNode.getBOMComponent();

				for (int inx = 0; inx < checkOutItemVec.size(); inx++)
				{
					if ( ( bomComponent.getItemCodeStr().trim()).equals( checkOutItemVec.elementAt(inx).toString().trim() ) )
					{
						bomComponent.setCheckOutStr("");
						itemVec.addElement(bomComponent.getItemCodeStr());

//						model.fireTreeChanged(treeNode);
//						treeTable.repaint();
					}
				}
			}

			itemHash.put("orgCode", BOMBasicInfoPool.getOrgCode());
			itemHash.put("itemCode", itemVec);

			version = KETBomHelper.service.getItemVersion(itemHash);
			String versionStr = "";

			if(version.size() > 0)
			{
				while (rootEnum3.hasMoreElements()) 
				{
					BOMTreeNode treeNode = (BOMTreeNode)rootEnum3.nextElement();
					BOMAssyComponent bomComponent = treeNode.getBOMComponent();

					for(int k=0; k<version.size(); k++)
					{
						versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();

						if(bomComponent.getItemCodeStr().equals(versionStr.substring(3, versionStr.indexOf("@"))))
						{
							bomComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));

//							model.fireTreeChanged(treeNode);
						}
					}
				}
			}

			if(isBomAllCheckIn)
			{
				BOMBasicInfoPool.setPublicCheckOutStatus("1");
			}

			if(isMyAllCheckIn)
			{
Kogger.debug(getClass(), "############################################ : "+flag);				
				workingStatusUpdate(flag);
	            pnl.setCheckStatus(1);
	            pnl.setMenuBarEnabled();
				pnl.publicStatusPanel.setStatusBar();
			}

			SaveBOMCmd saveBom = new SaveBOMCmd(parent, app, "Check-In");
			saveBom.executeModal();
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);

			pnl.setCursor(Cursor.getPredefinedCursor(0));
			MessageBox mbox = new MessageBox(parent, e.toString() + "\n" + messageRegistry.getString("checkInValid"), "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
        }
    }
    
    private void connectDB(String dbname) throws ConnectException
    {
        try
        {
            res = DBConnectionManager.getInstance();
			
            conn = res.getConnection(dbname);

            conn.setAutoCommit(false);
            stmt = conn.createStatement();
        }
        catch(Exception e)
        {
        }
    }

    private void closeDB(String dbname) throws ConnectException
    {
        try
        {
            if(rs != null)
                rs.close();
            stmt.close();
        }
        catch(Exception e)
        {
			pnl.setCursor(Cursor.getPredefinedCursor(0));
            MessageBox mbox = new MessageBox(parent, messageRegistry.getString("dbCloseError"), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
			return;
        }
        finally
        {
            if(res != null)
            {
                  res.freeConnection(dbname, conn);
            }
        }
    }    
}
