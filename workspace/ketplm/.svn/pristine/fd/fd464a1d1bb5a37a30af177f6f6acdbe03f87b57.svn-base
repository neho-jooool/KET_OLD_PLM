package e3ps.bom.command.cancelcheckout;

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
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class CancelCheckOutCmd extends AbstractAIFCommand
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
    JFrame desktop;
    BOMRegisterApplicationPanel pnl;
    BOMTreeNode rootNode;

    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    private String aryOwnerData [] = new String[4];
    String bomOid = "";
    String strCurStatus = "";

    Registry appReg;
    String userInfo = "";
    boolean bomGubunFlag = false;

    public CancelCheckOutCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = frame;

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        pnl.setCursor(Cursor.getPredefinedCursor(3));

        appReg = Registry.getRegistry(app);

        bomOid = BOMBasicInfoPool.getPublicBOMOid();
        strCurStatus = BOMBasicInfoPool.getPublicBOMStatus();

        setUserData();

        pnl.setCursor(Cursor.getPredefinedCursor(0));
    }

    public CancelCheckOutCmd(){}

    protected void executeCommand() throws Exception
    {
        pnl.setCursor(Cursor.getPredefinedCursor(3));

        try
        {
            bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;
            BOMTreeNode[] nodes = pnl.getSelectedTreeNode();

            String userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
//            String userInfo =  Utility.checkNVL(BOMBasicInfoPool.getUserDept()) + "/" + Utility.checkNVL(BOMBasicInfoPool.getUserName());
            BOMTreeNode[] selectedNode = pnl.getSelectedNode();

            if (nodes == null)
            {
                pnl.setCursor(Cursor.getPredefinedCursor(0));
                MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00331")/*체크아웃을 취소 할 부품을 선택하십시오.*/, "Error", MessageBox.ERROR);
                m.setModal(true);
                m.setVisible(true);
                return;
            }
            else
            {
                // BOM ECO CheckOut 에서 사용하는 로직
                if(!bomGubunFlag)
                {
                    if (nodes.length != 1)
                    {
                        pnl.setCursor(Cursor.getPredefinedCursor(0));
                        MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00347")/*하나의 부품을 선택하십시오.*/, "Error", MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                        return;
                    }

                    BOMAssyComponent selectedComponent = nodes[0].getBOMComponent();

                    if(!((selectedComponent.getParentItemCodeStr().equals(BOMBasicInfoPool.getPublicModelName().trim())) || (selectedComponent.getItemCodeStr().equals(BOMBasicInfoPool.getPublicModelName().trim()))))
                    {
                        pnl.setCursor(Cursor.getPredefinedCursor(0));
                        MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00224")/*선택한 부품은 최상위 부품이 아닙니다.\n체크아웃을 취소 할 최상위 부품을 선택하십시오.*/, "Error", MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                        return;
                    }
                }

                // Admin or 멤버가 아니면
                if(!isMember(bomGubunFlag) && !BOMBasicInfoPool.isAdmin())
                {
                    pnl.setCursor(Cursor.getPredefinedCursor(0));
                    MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00294")/*작업자 또는 공동작업자가 아닙니다.*/, "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;
                }

    Kogger.debug(getClass(), "================ Cancel CheckOutCmd Not RootNode ==================");
                for (int inx = 0; inx < selectedNode.length; inx++)
                {
                    BOMTreeNode node = selectedNode[inx];
                    BOMAssyComponent selectedComponent = node.getBOMComponent();

                    if (selectedComponent.getCheckOutStr().equalsIgnoreCase(""))
                    {
                        pnl.setCursor(Cursor.getPredefinedCursor(0));
                        MessageBox m = new MessageBox(app.getDesktop(),KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00035")/*BOM은 이미 체크인 상태입니다.*/, "Error", MessageBox.ERROR);
                        m.setModal(true);
                        m.setVisible(true);
                        return;
                    }
                }
    Kogger.debug(getClass(), "================ Cancel CheckOutCmd Not RootNode ==================");

                boolean isCheckOut = false;
                Vector itemCodeVec = new Vector();

                for (int inx = 0; inx < selectedNode.length; inx++)
                {
                    BOMTreeNode node = selectedNode[inx];
                    BOMAssyComponent selectedComponent = node.getBOMComponent();

                    if ( !(( selectedComponent.getCheckOutStr() == null ? "" : selectedComponent.getCheckOutStr().toString().trim() ).equals(userInfo)) )
                    {
                        isCheckOut = true;
                        pnl.setCursor(Cursor.getPredefinedCursor(0));
                        MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00330")/*체크아웃 사용자가 아닙니다.*/, "Error", 0);
                        mbox.setModal(true);
                        mbox.setVisible(true);
                        return;
                    }
                    else
                    {
                        if( ( selectedComponent.getCheckOutStr() == null ? "" : selectedComponent.getCheckOutStr().toString().trim() ).equals("") )
                        {
                            isCheckOut = true;
                            pnl.setCursor(Cursor.getPredefinedCursor(0));
                            MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00035")/*BOM은 이미 체크인 상태입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return;
                        }
                    }
                    itemCodeVec.addElement(BOMBasicInfoPool.getOrgCode() + selectedComponent.getItemCodeStr());
                }

                if(!isCheckOut)
                {
                    if( strCurStatus.equalsIgnoreCase("INWORK") || strCurStatus.equalsIgnoreCase("REWORK") )    // 작업중, 재작업 상태인 경우
                    {
                        if(!getMyStatus(bomGubunFlag).equalsIgnoreCase("4"))
                        {
                            cancelCheckOut(itemCodeVec, bomGubunFlag);
                            pnl.setCursor(Cursor.getPredefinedCursor(0));
                            MessageBox mbox = new MessageBox(parent, messageRegistry.getString("successCancelCheckOut"), "Information", 1);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return;
                        }
                        else
                        {
                            pnl.setCursor(Cursor.getPredefinedCursor(0));
                            MessageBox mbox = new MessageBox(parent, messageRegistry.getString("endWorkingStatus"), "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return;
                        }
                    }
                    else if(strCurStatus.equalsIgnoreCase("UNDERREVIEW"))        // 검토중
                    {
                        if(!BOMBasicInfoPool.isAdmin())    // Admin 아니면
                        {
                            pnl.setCursor(Cursor.getPredefinedCursor(0));
                            MessageBox mbox = new MessageBox(parent, messageRegistry.getString("underApproveStatus"), "Warning", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return;
                        }
                        else // Admin 이면
                        {
                            // 아무도 체크아웃 아니면..
                            cancelCheckOut(itemCodeVec, bomGubunFlag);
                            pnl.setCursor(Cursor.getPredefinedCursor(0));
                            MessageBox mbox = new MessageBox(parent, messageRegistry.getString("successCancelCheckOut"), "Information", 1);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return;
                        }
                    }
                    else if(strCurStatus.equalsIgnoreCase("APPROVED"))         // 승인완료
                    {
                        pnl.setCursor(Cursor.getPredefinedCursor(0));
                        MessageBox mbox = new MessageBox(parent, messageRegistry.getString("completedStatus"), "Warning", 0);
                        mbox.setModal(true);
                        mbox.setVisible(true);
                        return;
                    }
                }
            }
            pnl.setCursor(Cursor.getPredefinedCursor(0));
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
            pnl.setCursor(Cursor.getPredefinedCursor(0));
        }
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
                SQL =  " SELECT newbomcode                                                                                ";
                SQL += " FROM ketbomcoworker                                                                            ";
                SQL += " WHERE newbomcode = '" + BOMBasicInfoPool.getPublicModelName().trim() + "'        ";
                SQL += " AND coworkerid = '" + aryOwnerData[0] + "'                                                    ";
            }
            else
            {
                SQL =  " SELECT ecoheadernumber                                                                            ";
                SQL += " FROM ketbomecocoworker                                                                        ";
                SQL += " WHERE ecoheadernumber = '" + BOMBasicInfoPool.getBomEcoNumber().trim() + "'    ";
                SQL += " AND coworkerid = '" + aryOwnerData[0] + "'                                                    ";
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

    private void setUserData()
    {
        try
        {
            aryOwnerData[0] = Utility.checkNVL(BOMBasicInfoPool.getUserId());
            aryOwnerData[1] = Utility.checkNVL(BOMBasicInfoPool.getUserName());
            aryOwnerData[2] = Utility.checkNVL(BOMBasicInfoPool.getUserDept());
            aryOwnerData[3] = Utility.checkNVL(BOMBasicInfoPool.getUserEMail());
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
            pnl.setCursor(Cursor.getPredefinedCursor(0));
        }
    }

    private String getMyStatus(boolean flag)
    {
        String status = "";
        String SQL = "";
        try
        {
            connectDB(appReg.getString("plm"));

            if(flag)
            {
                SQL =  " SELECT endWorkingFlag                                                                            ";
                SQL += " FROM ketbomcoworker                                                                            ";
                SQL += " WHERE newbomcode = '" + BOMBasicInfoPool.getPublicModelName().trim() + "'        ";
                SQL += " AND coworkerid = '" + aryOwnerData[0] + "'                                                    ";
            }
            else
            {
                SQL =  " SELECT endWorkingFlag                                                                            ";
                SQL += " FROM ketbomecocoworker                                                                        ";
                SQL += " WHERE ecoheadernumber = '" + BOMBasicInfoPool.getBomEcoNumber().trim() + "'    ";
                SQL += " AND coworkerid = '" + aryOwnerData[0] + "'                                                    ";
            }

            rs = stmt.executeQuery(SQL);

            while(rs.next())
            {
                status = rs.getString("endWorkingFlag");
            }

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
            return status;
        }
    }

    private String workingUpdate(boolean flag)
    {
        String status = "";
        String SQL = "";
        try
        {
            connectDB(appReg.getString("plm"));

            if(flag)
            {
                SQL =  "UPDATE ketbomcoworker                                                                                ";
                SQL += "SET endWorkingFlag = '2'                                                                                ";
                SQL += "WHERE newbomcode = '" + BOMBasicInfoPool.getPublicModelName().trim() + "'            ";
                SQL += "AND coworkerid = '" + aryOwnerData[0] + "'                                                        ";
            }
            else
            {
                SQL =  "UPDATE ketbomecocoworker                                                                            ";
                SQL += "SET endWorkingFlag = '2'                                                                                ";
                SQL += "WHERE ecoheadernumber = '" + BOMBasicInfoPool.getBomEcoNumber().trim() + "'            ";
                SQL += "AND coworkerid = '" + aryOwnerData[0] + "'                                                        ";
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

    private void cancelCheckOut(Vector checkOutItemVec, boolean flag)
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

                checkVec.addElement(bomComponent.getItemCodeStr()==null?"":(BOMBasicInfoPool.getOrgCode() + bomComponent.getItemCodeStr().toString().trim()));
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

            if(!isBomAllCheckIn)
            {
                String iStr = "";
                String cStr = "";
                while (rootEnum1.hasMoreElements())
                {
                    BOMTreeNode treeNode = (BOMTreeNode)rootEnum1.nextElement();
                    BOMAssyComponent bomComponent = treeNode.getBOMComponent();

                    iStr = bomComponent.getItemCodeStr() == null ? "" : bomComponent.getItemCodeStr().trim();
                    cStr = bomComponent.getItemCodeStr() == null ? "" : bomComponent.getItemCodeStr().trim();

                    for(int k=0; k<checkOutItemVec.size(); k++)
                    {
                        if( iStr.equals(checkOutItemVec.elementAt(k).toString().trim()) && cStr.equalsIgnoreCase(userInfo) )
                        {
                        }
                        else
                        {
                            isMyAllCheckIn = false;
                            break;
                        }
                    }

                    if(!isMyAllCheckIn)
                    {
                        break;
                    }
                }
            }

            Hashtable inputParams = new Hashtable();
            inputParams.put("bomOid", BOMBasicInfoPool.getPublicBOMOid());
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

            KETBomHelper.service.setCoworkerCancelCheckOut(inputParams);

            Vector version = new Vector();
            Vector itemVec = new Vector();
            Hashtable itemHash = new Hashtable();

            while (rootEnum2.hasMoreElements())
            {
                BOMTreeNode treeNode = (BOMTreeNode)rootEnum2.nextElement();
                BOMAssyComponent bomComponent = treeNode.getBOMComponent();

                for (int inx = 0; inx < checkOutItemVec.size(); inx++)
                {
                    if((BOMBasicInfoPool.getOrgCode().trim() + bomComponent.getItemCodeStr().trim()).equals(checkOutItemVec.elementAt(inx).toString().trim()))
                    {
                        bomComponent.setCheckOutStr("");
                        itemVec.addElement(BOMBasicInfoPool.getOrgCode() + bomComponent.getItemCodeStr());

                        model.fireTreeChanged(treeNode);
                        treeTable.repaint();
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

                        if(bomComponent.getItemCodeStr().equals(versionStr.substring(0, versionStr.indexOf("@"))))
                        {
                            bomComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));

                            model.fireTreeChanged(treeNode);
                            treeTable.repaint();
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
                workingUpdate(flag);
                pnl.setCheckStatus(1);
                pnl.setMenuBarEnabled();
                pnl.publicStatusPanel.setStatusBar();
            }
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
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
            Kogger.error(getClass(), e);
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
