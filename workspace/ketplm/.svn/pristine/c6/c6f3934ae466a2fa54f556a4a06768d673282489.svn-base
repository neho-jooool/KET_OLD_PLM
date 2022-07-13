package e3ps.bom.command.endworking;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.bomvalidation.BOMECOValidationOperation;
import e3ps.bom.command.bomvalidation.BOMValidationOperation;
import e3ps.bom.command.checkin.CheckInCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class EndWorkingCmd extends AbstractAIFCommand
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
    Registry appReg;

    DBConnectionManager    res = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    private String aryUserData [] = new String[4];
    boolean isCheckIn = false;

    String itemCodeStr = "";
    String wfStatus = "";
    String checkOuterIdStr = "";
    String checkOuterStr = "";
    String bomOid = "";
    String ecoItemCode = "";
    boolean bomGubunFlag = false;

    BOMRegisterApplicationPanel pnl;
    BOMTreeNode rootNode;

    boolean checkView = true;

    public EndWorkingCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = frame;

        appReg = Registry.getRegistry(app);

        res = null;
        conn = null;
        stmt = null;
        rs = null;

        bomOid = BOMBasicInfoPool.getPublicBOMOid() == null ? "" : BOMBasicInfoPool.getPublicBOMOid().trim();
        itemCodeStr = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();
        wfStatus = BOMBasicInfoPool.getPublicBOMStatus() == null ? "" : BOMBasicInfoPool.getPublicBOMStatus().trim();
        bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

        setUserData();

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
    }

    public EndWorkingCmd(JFrame frame, AbstractAIFUIApplication app, boolean chk)
    {
        this.app = app;
        parent = frame;

        appReg = Registry.getRegistry(app);

        res = null;
        conn = null;
        stmt = null;
        rs = null;
        this.checkView = chk;
        bomOid = BOMBasicInfoPool.getPublicBOMOid() == null ? "" : BOMBasicInfoPool.getPublicBOMOid().trim();
        itemCodeStr = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();
        wfStatus = BOMBasicInfoPool.getPublicBOMStatus() == null ? "" : BOMBasicInfoPool.getPublicBOMStatus().trim();
        bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

        setUserData();

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
    }

    protected void executeCommand() throws Exception
    {

        if(itemCodeStr.equalsIgnoreCase("Empty"))
        {
            MessageBox mbox = new MessageBox(parent, messageRegistry.getString("openBOMWorkspace"), "Warning", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
            return;
        }
        else
        {
//            setAutocheckin();      //자동 체크인 해준다.   ENDWorking 실행시에 체크인 하도록 함
//            setStatus();            // 체크아웃여부 확인하는 함수 (ENDWorking 실행시에 체크인 하도록 하기위해 확인 안함)

            if(wfStatus.equalsIgnoreCase("INWORK") || wfStatus.equalsIgnoreCase("REWORK"))
            {
Kogger.debug(getClass(), "====>> isCheckIn : " + isCheckIn);
Kogger.debug(getClass(), "====>> isEndWorking : " + isEndWorking());

                if(isEndWorking())
                {
Kogger.debug(getClass(), "@@@@@@ checkView : " + checkView);
                    if(checkView)
                    {
                        MessageBox mbox = new MessageBox(parent, messageRegistry.getString("alreadyEndWorking"), "Error", 0);
                        mbox.setModal(true);
                        mbox.setVisible(true);
                        return;
                    }
                }

                // 자신이 CheckIn 상태인 경우 :: 체크아웃여부 확인 (ENDWorking 실행시에 체크인 하도록 하기위해 확인 안함)
//                if(isCheckIn == true)
//                {
Kogger.debug(getClass(), "====>> isFinalWorking : " + isFinalWorking());
                    if (isFinalWorking())
                    {
Kogger.debug(getClass(), "@@@@@ getHasErrorInValidation() : " + BOMBasicInfoPool.getHasErrorInValidation() );
Kogger.debug(getClass(), "@@@@@ getBomValidationResult() : " + BOMBasicInfoPool.getBomValidationResult() );

                        if (!BOMBasicInfoPool.getHasErrorInValidation() && BOMBasicInfoPool.getBomValidationResult())
                        {
                            endWorking();

                            if (endAllWorking())
                            {
                                // 메시지 여러개 띄우지 말라고 함
//                                    MessageBox mbox = new MessageBox(parent, messageRegistry.getString("allCompletedEndWorking"), "모든 작업완료", 1);
//                                    mbox.setModal(true);
//                                    mbox.setVisible(true);

                                pnl.publicStatusPanel.setStatusBar();
                            }
                            else
                            {
                                MessageBox mbox = new MessageBox(parent, messageRegistry.getString("endWorkingError"), "Error", 1);
                                mbox.setModal(true);
                                mbox.setVisible(true);
                            }
                            return;
                        }
                        else
                        {
                            try
                            {
                                BOMBasicInfoPool.setValidationForEnd("LAST");
                                if(bomGubunFlag)
                                {
                                    BOMValidationOperation op = new BOMValidationOperation(app);
                                    op.executeOperation();
                                }
                                else
                                {
                                    BOMECOValidationOperation op = new BOMECOValidationOperation(app);
                                    op.executeOperation();
                                }
                            }
                            catch(Exception e)
                            {
                                Kogger.error(getClass(), e);
                            }
                            return;
                        }

                    }
                    // 마지막 EndWorking 이 아닌 경우
                    else
                    {
                        if (BOMBasicInfoPool.getBomValidationResult())
                        {
Kogger.debug(getClass(), "==============마지막 EndWorking 이 아닌 경우================");
                            endWorking();
                        }
                        else
                        {
                            if ( JOptionPane.showConfirmDialog(parent, messageRegistry.getString("pressEndWorking"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION )
                            {
                                BOMBasicInfoPool.setValidationForEnd("NOTLAST");
                                if(bomGubunFlag)
                                {
                                    BOMValidationOperation op = new BOMValidationOperation(app);
                                    op.executeOperation();
                                }
                                else
                                {
                                    BOMECOValidationOperation op = new BOMECOValidationOperation(app);
                                    op.executeOperation();
                                }
                            }
                        }
                    }

//                }
//                else
//                {
//                    // 자신이 CheckOut 상태인 경우
//                    if(checkView)
//                    {
//                        MessageBox mbox = new MessageBox(parent, messageRegistry.getString("checkOutStatus"), "Warning", 0);
//                        mbox.setModal(true);
//                        mbox.setVisible(true);
//                        return;
//                    }
//                }
            }
            else if(wfStatus.equalsIgnoreCase("UNDERREVIEW"))
            {
                MessageBox mbox = new MessageBox(parent, messageRegistry.getString("underApproveStatus"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00289")/*작업완료 불가*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;
            }
            else if(wfStatus.equalsIgnoreCase("REJECTED"))
            {
                MessageBox mbox = new MessageBox(parent, messageRegistry.getString("rejectStatus"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00289")/*작업완료 불가*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;
            }
            else if(wfStatus.equalsIgnoreCase("APPROVED"))
            {
                MessageBox mbox = new MessageBox(parent, messageRegistry.getString("completedStatus"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00289")/*작업완료 불가*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;
            }

        }
    }

    private void setUserData()
    {
        try
        {
            aryUserData[0] = Utility.checkNVL(BOMBasicInfoPool.getUserId());
            aryUserData[1] = Utility.checkNVL(BOMBasicInfoPool.getUserName());
            aryUserData[2] = Utility.checkNVL(BOMBasicInfoPool.getUserDept());
            aryUserData[3] = Utility.checkNVL(BOMBasicInfoPool.getUserEMail());
Kogger.debug(getClass(), "@@@@@ aryUserData : " + aryUserData[0] + ", " + aryUserData[1] + ", " + aryUserData[2] + ", " + aryUserData[3]);
        }
        catch(Exception e)
        {
            MessageBox mbox = new MessageBox(parent, e.toString(), messageRegistry.getString("bomError"), 0);
            mbox.setModal(true);
            mbox.setVisible(true);
         }
    }

    public void setStatus()
    {
        String SQL = "";
        try
        {
            connectDB(appReg.getString("plm"));

            if(bomGubunFlag)
            {
                SQL =  " SELECT endWorkingFlag status                            ";
                SQL += " FROM ketbomcoworker                                    ";
                SQL += " WHERE newbomcode = '" + itemCodeStr + "'            ";
                SQL += " AND coworkerid = '" + aryUserData[0] + "'            ";
            }
            else
            {
                SQL =  " SELECT endWorkingFlag status                                                                                ";
                SQL += " FROM ketbomecocoworker                                                                                    ";
                SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'    ";
                SQL += " AND ecoitemcode = '" + itemCodeStr + "'                                                                    ";
                SQL += " AND coworkerid = '" + aryUserData[0] + "'                                                                ";
            }
//Kogger.debug(getClass(), "@@@ sql : "+SQL);
            rs = stmt.executeQuery(SQL);

            if (rs.next())
            {
                if(rs.getString("status").equals("2"))
                {
                    isCheckIn = true;
                }
                else
                {
                    isCheckIn = false;
                }
            }
            else
            {
                isCheckIn = false;
            }
            closeDB(appReg.getString("plm"));
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    public boolean isEndWorking()
    {
        boolean isEnd = false;
        String status = "";
        String SQL = "";

        try
        {
            connectDB(appReg.getString("plm"));
            if(bomGubunFlag)
            {
                SQL =  " SELECT endWorkingFlag status                            ";
                SQL += " FROM ketbomcoworker                                    ";
                SQL += " WHERE newbomcode = '" + itemCodeStr + "'            ";
                SQL += " AND coworkerid = '" + aryUserData[0] + "'            ";
            }
            else
            {
                SQL =  " SELECT endWorkingFlag status                                                                                ";
                SQL += " FROM ketbomecocoworker                                                                                    ";
                SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'    ";
                SQL += " AND ecoitemcode = '" + itemCodeStr + "'                                                                    ";
                SQL += " AND coworkerid = '" + aryUserData[0] + "'                                                                ";
            }
            rs = stmt.executeQuery(SQL);

            while(rs.next())
            {
                status = rs.getString("status");
                if(status.equalsIgnoreCase("4"))
                {
                    isEnd = true;
                }
            }
            closeDB(appReg.getString("plm"));
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
            throw e;
        }
        finally
        {
            return isEnd;
        }
    }

    public boolean isConfirmWorking()
    {
        boolean isEnd = false;
        String status = "";
        String SQL = "";

        try
        {
            connectDB(appReg.getString("plm"));
            if(bomGubunFlag)
            {
                SQL =  " SELECT endWorkingFlag status                            ";
                SQL += " FROM ketbomcoworker                                    ";
                SQL += " WHERE newbomcode = '" + itemCodeStr + "'            ";
            }
            else
            {
                SQL =  " SELECT endWorkingFlag status                                                                                ";
                SQL += " FROM ketbomecocoworker                                                                                    ";
                SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'    ";
                SQL += " AND ecoitemcode = '" + itemCodeStr + "'                                                                    ";
            }
            rs = stmt.executeQuery(SQL);

            while(rs.next())
            {
                status = rs.getString("status");
                if(status.equalsIgnoreCase("4"))
                {
                    isEnd = true;
                }
            }
            closeDB(appReg.getString("plm"));
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
            throw e;
        }
        finally
        {
            return isEnd;
        }
    }


    private boolean isFinalWorking()
    {
        boolean isFinalEnd = false;
        String status = "";
        String SQL = "";

        try
        {
            connectDB(appReg.getString("plm"));

            if(bomGubunFlag)
            {
                SQL =  "SELECT endWorkingFlag status                                ";
                SQL += " FROM ketbomcoworker                                    ";
                SQL += " WHERE newbomcode = '" + itemCodeStr + "'            ";
                SQL += " AND endWorkingFlag <> '4'                                ";
                SQL += " AND coworkerid <> '"+aryUserData[0] + "'            ";
            }
            else
            {
                SQL =  "SELECT endWorkingFlag status                                                                                    ";
                SQL += " FROM ketbomecocoworker                                                                                    ";
                SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'    ";
                SQL += " AND ecoitemcode = '" + itemCodeStr + "'                                                                    ";
                SQL += " AND endWorkingFlag <> '4'                                                                                    ";
                SQL += " AND coworkerid <> '"+aryUserData[0] + "'                                                                ";
            }

            rs = stmt.executeQuery(SQL);

            if(rs.next())
            {
                isFinalEnd=false;
            }
            else
            {
                isFinalEnd = true;
            }
            closeDB(appReg.getString("plm"));
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
            throw e;
        }
        finally
        {
            return isFinalEnd;
        }
    }

    private void endWorking() throws Exception
    {
        try
        {
/*
            String resultStr = "";
            Vector checkOutItemVec = new Vector();
            BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();
            rootNode = (BOMTreeNode)model.getRootNode();
            Enumeration rootEnum = rootNode.preorderEnumeration();
            String userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
            while (rootEnum.hasMoreElements())
            {
                BOMTreeNode treeNode = (BOMTreeNode)rootEnum.nextElement();
                BOMAssyComponent bomComponent = treeNode.getBOMComponent();

                if((bomComponent.getCheckOutStr()==null?"":bomComponent.getCheckOutStr().toString().trim()).equals(userInfo))
                {
                    checkOutItemVec.addElement(bomComponent.getItemCodeStr()==null?"":(BOMBasicInfoPool.getOrgCode() + bomComponent.getItemCodeStr().toString().trim()));
                }
            }

            Hashtable inputParams = new Hashtable();
            inputParams.put("bomOid", bomOid);
            inputParams.put("flag", "N");
            inputParams.put("itemCode", checkOutItemVec);

            if(bomGubunFlag)
            {
                inputParams.put("bomEcoFlag", "N");
            }
            else
            {
                inputParams.put("bomEcoFlag", "Y");
            }

            LSISBOMHelper.service.setCoworkerCheckIn(inputParams);
            BOMBasicInfoPool.setPublicBOMOid(bomOid);

            Hashtable inputParamsData = new Hashtable();
            inputParamsData.put("bomOid", bomOid);
            inputParamsData.put("isEndWork", new Boolean(true));
            if(bomGubunFlag)
            {
                inputParamsData.put("header", "BOM");
            }
            else
            {
                inputParamsData.put("header", "BOMECO");
            }
            resultStr = LSISBOMHelper.service.changeEndWorkFlag(inputParamsData);

            if(resultStr.equals("OK"))
            {
*/
Kogger.debug(getClass(), "==============endWorking()================");

Kogger.debug(getClass(), "@@@@@ getBomEcoNumber : " + BOMBasicInfoPool.getBomEcoNumber());

                setAutocheckin();  //자동 체크인 해준다

                String SQL = "";
                connectDB(appReg.getString("plm"));

                if(bomGubunFlag)
                {
                    SQL =  "UPDATE ketbomcoworker                                ";
                    SQL += " SET endWorkingFlag = '4'                                ";
                    SQL += " WHERE newbomcode = '" + itemCodeStr + "'        ";
                    SQL += " AND coworkerid = '" + aryUserData[0] + "'        ";
                }
                else
                {
Kogger.debug(getClass(), "@@@@@ 이리로왔니?? ELSE ");
Kogger.debug(getClass(), "@@@@@ itemCodeStr : " + itemCodeStr);
Kogger.debug(getClass(), "@@@@@ aryUserData[0] : " + aryUserData[0]);
Kogger.debug(getClass(), "@@@@@ getBomEcoNumber : " + BOMBasicInfoPool.getBomEcoNumber());


                    SQL =  "UPDATE ketbomecocoworker                                                                                    ";
                    SQL += " SET endWorkingFlag = '4'                                                                                        ";
                    SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'    ";
                    SQL += " AND ecoitemcode = '" + itemCodeStr + "'                                                                    ";
                    SQL += " AND coworkerid = '" + aryUserData[0] + "'                                                                ";
                }

                stmt.executeUpdate(SQL);

                conn.commit();
                closeDB(appReg.getString("plm"));

                BOMBasicInfoPool.setPublicMyStatus("4");
                BOMBasicInfoPool.setBomValidationResult(false);
                BOMBasicInfoPool.setHasErrorInValidation(false);

                pnl.setMyStatus(4);
                pnl.setCheckStatus(0);

                MessageBox mbox = new MessageBox(parent, messageRegistry.getString("completedEndWorking"), "End Working Completed", 1);
                mbox.setModal(true);
                mbox.setVisible(true);
//            }
        }
        catch(Exception e)
        {
            conn.rollback();
            Kogger.error(getClass(), e);
            throw e;
        }
        finally
        {
            pnl.publicStatusPanel.setStatusBar();
            pnl.setMenuBarEnabled();
        }
    }

    private boolean endAllWorking()
    {
        boolean isOK = false;
        String changedFlag = "";
        String resultStr = "";

Kogger.debug(getClass(), "==>> wfStatus : " + wfStatus);

        try
        {
            if ( wfStatus.equalsIgnoreCase("REWORK") || wfStatus.equalsIgnoreCase("INWORK") )
            {
                String headerType = "";
                if(bomGubunFlag)
                {
                    headerType = "BOM";
                }
                else
                {
                    headerType = "BOMECO";
                }
                resultStr = KETBomHelper.service.setAllEndWorking(bomOid, headerType);
                BOMBasicInfoPool.setPublicMyStatus("4");
                pnl.setCheckStatus(0);
                pnl.publicStatusPanel.setStatusBar();
                pnl.setMenuBarEnabled();

                isOK = true;
            }else{
                isOK = false;
            }
/*
Kogger.debug(getClass(), "---->> resultStr : " + resultStr);
            if(resultStr.equals("OK"))
            {
                BOMBasicInfoPool.setPublicMyStatus("4");
                pnl.setCheckStatus(0);
                pnl.publicStatusPanel.setStatusBar();
                pnl.setMenuBarEnabled();

                isOK = true;
            }
            else
            {
                isOK = false;
            }
*/
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
            isOK = false;
        }
        finally
        {
            return isOK;
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
            Kogger.error(getClass(), e);
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
            MessageBox mbox = new MessageBox(parent, messageRegistry.getString("dbCloseError"), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
        finally
        {
            if(res != null)
            {
                res.freeConnection(dbname, conn);
            }
        }
    }

    //shin...add
    private void setAutocheckin()
    {
        try{
        BOMCheckOutInDao bcoi = new BOMCheckOutInDao();
        Vector vccom = new Vector();

        BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();
        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        rootNode = (BOMTreeNode)model.getRootNode();
        Enumeration rootEnum = rootNode.preorderEnumeration();

        while (rootEnum.hasMoreElements())
        {
            BOMTreeNode treeNode = (BOMTreeNode)rootEnum.nextElement();
            BOMAssyComponent bomComponent = treeNode.getBOMComponent();
            vccom.add(bomComponent.getItemCodeStr());
            bcoi.setCheckIn( bomComponent.getItemCodeStr(), BOMBasicInfoPool.getUserId() );
        }

        boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false ;

        CheckInCmd ci = new CheckInCmd(parent, app);
        ci.checkIn(vccom, bomGubunFlag);
        }catch(Exception e){

        }
    }
}
