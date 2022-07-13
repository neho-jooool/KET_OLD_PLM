package e3ps.bom.command.multiplebomeco;

import java.awt.Frame;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class EndWorkingCmd extends AbstractAIFCommand {

    private JFrame parent;
    AbstractAIFUIApplication app;
    Registry appReg;

    BOMRegisterApplicationPanel pnl;
    BOMTreeNode rootNode;

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
    String parentItems = "";
    Vector parentItemVec;

    public EndWorkingCmd(JFrame frame, AbstractAIFUIApplication app) {
        this.app = app;
        parent = frame;

        appReg = Registry.getRegistry(app);

        res = null;
        conn = null;
        stmt = null;
        rs = null;

        parentItems = BOMECOBasicInfoPool.getBomParentItemCodes();

        StringTokenizer token = null;
        String strToekn = "";
        parentItemVec = new Vector();

        token = new StringTokenizer(parentItems, ",");
        while(token.hasMoreElements()) {
            strToekn = token.nextToken();
            parentItemVec.add(strToekn);
        }

        bomOid = BOMECOBasicInfoPool.getPublicBOMOid() == null ? "" : BOMECOBasicInfoPool.getPublicBOMOid().trim();
        itemCodeStr = BOMECOBasicInfoPool.getPublicModelName() == null ? "" : BOMECOBasicInfoPool.getPublicModelName().trim();
        wfStatus = BOMBasicInfoPool.getPublicBOMStatus() == null ? "" : BOMBasicInfoPool.getPublicBOMStatus().trim(); //BOMECOBasicInfoPool 은 한글임

        setUserData();
    }

    protected void executeCommand() throws Exception {
        if (itemCodeStr.equalsIgnoreCase("Empty")) {
            MessageBox mbox = new MessageBox(parent, "부품을 선택하세요", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/*경고*/, 0);
            mbox.setModal(true);
            mbox.setVisible(true);

        } else {
//            setStatus();
Kogger.debug(getClass(), "@@@@@ wfStatus : " + wfStatus);
            if (wfStatus.equalsIgnoreCase("INWORK") || wfStatus.equalsIgnoreCase("REWORK")) {
Kogger.debug(getClass(), "@@@@@ isEndWorking() : " + isEndWorking());

                if (isEndWorking() == true) {
                    MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00271")/*이미 작업완료 상태입니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00291")/*작업완료 에러*/, 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;
                }

                // 자신이 CheckIn 상태인 경우
//                if (isCheckIn == true) {
                    if (isFinalWorking()) {
Kogger.debug(getClass(), "@@@@@ isFinalWorking() : " + isFinalWorking());
Kogger.debug(getClass(), "@@@@@ BOMECOBasicInfoPool.getBomValidationResult() : " + BOMECOBasicInfoPool.getBomValidationResult());
//                        if (!BOMECOBasicInfoPool.getHasErrorInValidation() && BOMECOBasicInfoPool.getBomValidationResult())
//                        {
                            endWorking();
                            if (endAllWorking())
                            {
                                MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00149")/*모든 작업이 완료되었습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00315")/*전체 작업완료*/, 1);
                                mbox.setModal(true);
                                mbox.setVisible(true);

                                Frame[] allFrames = Frame.getFrames();

                                for (int i = 0; i < allFrames.length; i++)
                                {
                                    Frame f = allFrames[i];

                                    if (f.getTitle().toString().equalsIgnoreCase("내작업목록") || f.getTitle().toString().equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/) ||
                                        f.getTitle().toString().equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/) || f.getTitle().toString().equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/))
                                    {
                                        f.dispose();
                                        System.gc();
                                    }
                                }

                                BOMBasicInfoPool.setPublicBOMOid("");
                                DBConnectionManager.getInstance().release();
                                app.getDesktop().dispose();
                            }
                            else
                            {
                                MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00146")/*마지막 작업완료시 에러가 발생했습니다. \n 작업재개 후, 다시 작업완료 처리해 주십시오.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00263")/*에러*/, 1);
                                mbox.setModal(true);
                                mbox.setVisible(true);
                            }
                            return;
//                        } else {
//                            return;
//                        }

                    // 마지막 EndWorking 이 아닌 경우
                    } else {
                        if (BOMECOBasicInfoPool.getBomValidationResult()) {
                            endWorking();
                        }
                    }

//                } else {
//                    // 자신이 CheckIn 상태가 아닌 경우
//                    MessageBox mbox = new MessageBox(parent, "체크인 상태가 아닙니다. 체크인 해주세요", "경고", 0);
//                    mbox.setModal(true);
//                    mbox.setVisible(true);
//                }
            } else if (wfStatus.equalsIgnoreCase("UNDERREVIEW")) {
                MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00362")/*현재 상태는 검토중입니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00289")/*작업완료 불가*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } else if (wfStatus.equalsIgnoreCase("REJECTED")) {
                MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00363")/*현재 상태는 반려됨입니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00289")/*작업완료 불가*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } else if (wfStatus.equalsIgnoreCase("APPROVED")) {
                MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00364")/*현재 상태는 승인완료입니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00289")/*작업완료 불가*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
        }
    }

    private void setUserData() {
        try {
            aryUserData[0] = BOMBasicInfoPool.getUserId();
            aryUserData[1] = BOMBasicInfoPool.getUserName();
            aryUserData[2] = BOMBasicInfoPool.getUserDept();
            aryUserData[3] = BOMBasicInfoPool.getUserEMail();
        } catch (Exception e) {
            MessageBox mbox = new MessageBox(parent, e.toString(), "BOM System Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
    }

    public void setStatus() {
        String SQL = "";
        try {
            connectDB(appReg.getString("plm"));

            SQL = " SELECT endWorkingFlag status ";
            SQL += " FROM ketbomecocoworker ";
            SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMECOBasicInfoPool.getBomEcoNumber()) + "' ";
            SQL += " AND ecoitemcode = '" + itemCodeStr + "'                                                                    ";
            SQL += " AND coworkerid = '" + aryUserData[0] + "' ";

            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                if (rs.getString("status").equals("2")) {
                    isCheckIn = true;
                } else {
                    isCheckIn = false;
                }
            } else {
                isCheckIn = false;
            }
            closeDB(appReg.getString("plm"));
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    private boolean isEndWorking() {
        boolean isEnd = false;
        String status = "";
        String SQL = "";

        try {
            connectDB(appReg.getString("plm"));

            SQL = " SELECT endWorkingFlag status ";
            SQL += " FROM ketbomecocoworker ";
            SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMECOBasicInfoPool.getBomEcoNumber()) + "' ";
//            SQL += " AND ecoitemcode = '" + itemCodeStr + "'                                                                    ";
            SQL += " AND coworkerid = '" + aryUserData[0] + "' ";

            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                status = rs.getString("status");
                if (status.equalsIgnoreCase("4")) {
                    isEnd = true;
                }
            }
            closeDB(appReg.getString("plm"));
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            throw e;
        } finally {
            return isEnd;
        }
    }

    private boolean isFinalWorking() {
        boolean isFinalEnd = false;
        String SQL = "";

        try {
            connectDB(appReg.getString("plm"));

            SQL = "SELECT endWorkingFlag status ";
            SQL += " FROM ketbomecocoworker ";
            SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMECOBasicInfoPool.getBomEcoNumber()) + "' ";
//            SQL += " AND ecoitemcode = '" + itemCodeStr + "'                                                                    ";
            SQL += " AND endWorkingFlag <> '4' ";
            SQL += " AND coworkerid <> '" + aryUserData[0] + "' ";

            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                isFinalEnd = false;
            } else {
                isFinalEnd = true;
            }
            closeDB(appReg.getString("plm"));
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            throw e;
        } finally {
            return isFinalEnd;
        }
    }

    private void endWorking() throws Exception {

        BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

        try {
            String SQL = "";
            connectDB(appReg.getString("plm"));

            //자동 체크인 해준다
            if ( parentItemVec != null )
            {
                for (int inx = 0; inx < parentItemVec.size(); inx++) {
                    // 체크인 처리
                    checkoutDao.setCheckIn( (String)parentItemVec.get(inx), aryUserData[0] );
                }
            }

Kogger.debug(getClass(), "@@@@@ 이리로왔니?? 일괄변경  ");
Kogger.debug(getClass(), "@@@@@ itemCodeStr : " + itemCodeStr);
Kogger.debug(getClass(), "@@@@@ aryUserData[0] : " + aryUserData[0]);
Kogger.debug(getClass(), "@@@@@ getBomEcoNumber : " + BOMECOBasicInfoPool.getBomEcoNumber());

            SQL = "UPDATE ketbomecocoworker                                                                                         ";
            SQL += " SET endWorkingFlag = '4'                                                                                         ";
            SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMECOBasicInfoPool.getBomEcoNumber()) + "' ";
//            SQL += " AND ecoitemcode = '" + itemCodeStr + "'                                                                        ";
            SQL += " AND coworkerid = '" + aryUserData[0] + "'                                                                     ";

            stmt.executeUpdate(SQL);

            conn.commit();
            closeDB(appReg.getString("plm"));

            BOMECOBasicInfoPool.setPublicMyStatus("4");
            BOMECOBasicInfoPool.setBomValidationResult(false);
            BOMECOBasicInfoPool.setHasErrorInValidation(false);

            MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00292")/*작업완료 처리 되었습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00268")/*완료*/, 1);
            mbox.setModal(true);
            mbox.setVisible(true);

        } catch (Exception e) {
            Kogger.error(getClass(), e);
            throw e;
        }
    }

//shin....
/*
    private boolean endAllWorking() {
        boolean isOK = false;
        String resultStr = "";

        try {
            if (wfStatus.equalsIgnoreCase("IN_WORK") || wfStatus.equalsIgnoreCase("Rejected")) {
                String headerType = "BOMECO";
                resultStr = KETBomHelper.service.setAllEndWorking(bomOid, headerType);
            }

            if (resultStr.equals("OK")) {
                BOMECOBasicInfoPool.setPublicMyStatus("4");
                isOK = true;
            } else {
                isOK = false;
            }
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            isOK = false;
        } finally {
            return isOK;
        }
    }
*/

private boolean endAllWorking() {
        boolean isOK = false;
        String resultStr = "";

        try {
            if (wfStatus.equalsIgnoreCase("INWORK") || wfStatus.equalsIgnoreCase("REWORK")) {
                String headerType = "BOMECO";
                BOMECOBasicInfoPool.setPublicMyStatus("4");
                isOK = true;
            }else{
                isOK = false;
            }
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            isOK = false;
        } finally {
            return isOK;
        }
    }

    private void connectDB(String dbname) throws ConnectException {
        try {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(dbname);

            conn.setAutoCommit(false);
            stmt = conn.createStatement();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    private void closeDB(String dbname) throws ConnectException {
        try {
            if (rs != null)
                rs.close();
            stmt.close();
        } catch (Exception e) {
            MessageBox mbox = new MessageBox(parent, "DB Close Failure", "DB Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        } finally {
            if (res != null) {
                res.freeConnection(dbname, conn);
            }
        }
    }
}
