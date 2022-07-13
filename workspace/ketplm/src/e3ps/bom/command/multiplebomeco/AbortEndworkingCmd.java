package e3ps.bom.command.multiplebomeco;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class AbortEndworkingCmd extends AbstractAIFCommand {

    private JFrame parent;
    AbstractAIFUIApplication app;
    Registry appReg;

    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    String bomOid = "";
    String itemCodeStr = "";
    String wfStatus = "";

    public boolean isOK = false;

    public AbortEndworkingCmd(JFrame frame, AbstractAIFUIApplication app) {
        this.app = app;
        parent = frame;

        appReg = Registry.getRegistry(app);

        res = null;
        conn = null;
        stmt = null;
        rs = null;

        bomOid = BOMECOBasicInfoPool.getPublicBOMOid() == null ? "" : BOMECOBasicInfoPool.getPublicBOMOid().trim();
        wfStatus = BOMBasicInfoPool.getPublicBOMStatus() == null ? "" : BOMBasicInfoPool.getPublicBOMStatus().trim();        //BOMECOBasicInfoPool 은 한글임
        itemCodeStr = BOMECOBasicInfoPool.getPublicModelName() == null ? "" : BOMECOBasicInfoPool.getPublicModelName().trim();
Kogger.debug(getClass(), "@@@@@@@@@ [AbortEndworkingCmd] bomOid : " + bomOid);
Kogger.debug(getClass(), "@@@@@@@@@ [AbortEndworkingCmd] wfStatus : " + wfStatus);
Kogger.debug(getClass(), "@@@@@@@@@ [AbortEndworkingCmd] itemCodeStr : " + itemCodeStr);
    }

    protected void executeCommand() throws Exception {
        if (itemCodeStr.equalsIgnoreCase("Empty")) {
            MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00194")/*부품을 선택해주세요.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, 0);
            mbox.setModal(true);
            mbox.setVisible(true);

        } else {
            if (wfStatus.equalsIgnoreCase("INWORK") || wfStatus.equalsIgnoreCase("REWORK") ) {
                if(isAllEndWorking() && !(wfStatus.equalsIgnoreCase("REWORK"))) {
                    MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00365")/*현재 작업완료 상태입니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;
                }

                if (isEndWorking()) {
                    int n = JOptionPane.showConfirmDialog(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00297")/*작업재개 하시려면 [예] 버튼을 누르세요.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        abortEndworking();
                    }
                } else {
                    MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00290")/*작업완료 상태가 아닙니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/*경고*/, 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                }
            } else if (wfStatus.equalsIgnoreCase("UNDERREVIEW")) {
                MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00362")/*현재 상태는 검토중입니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00295")/*작업재개 불가능*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } else if (wfStatus.equalsIgnoreCase("REJECTED")) {
                MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00363")/*현재 상태는 반려됨입니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00295")/*작업재개 불가능*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } else if (wfStatus.equalsIgnoreCase("APPROVED")) {
                MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00364")/*현재 상태는 승인완료입니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00295")/*작업재개 불가능*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
        }
    }

    private boolean isEndWorking() {
        boolean isEnd = false;
        String status = "";
        String SQL = "";

        try {
            connectDB(appReg.getString("plm"));

            SQL = "SELECT endWorkingFlag status ";
            SQL += " FROM ketbomecocoworker ";
            SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMECOBasicInfoPool.getBomEcoNumber()) + "' ";
//            SQL += " AND ecoitemcode='" + itemCodeStr + "'                                                                            ";
            SQL += " AND coworkerid='" + BOMBasicInfoPool.getUserId() + "' ";

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
        }
        return isEnd;
    }

    private boolean isAllEndWorking() {
        boolean isAllEndWorking = false;
        String SQL = "";

        try {
            connectDB(appReg.getString("plm"));

            SQL = "SELECT endWorkingFlag status    ";
            SQL += " FROM ketbomecocoworker ";
            SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMECOBasicInfoPool.getBomEcoNumber()) + "'    ";
//            SQL += " AND ecoitemcode='" + itemCodeStr + "'                                                                            ";
            SQL += " AND endWorkingFlag <> '4' ";

            rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                isAllEndWorking = false;
            } else {
                isAllEndWorking = true;
            }
            closeDB(appReg.getString("plm"));
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            throw e;
        } finally {
            return isAllEndWorking;
        }
    }

    private void abortEndworking() {
        String SQL = "";
        try {
            connectDB(appReg.getString("plm"));

            SQL = "UPDATE ketbomecocoworker ";
            SQL += " SET endWorkingFlag = '2' ";
            SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMECOBasicInfoPool.getBomEcoNumber()) + "' ";
//            SQL += " AND ecoitemcode='" + itemCodeStr + "'                                                                            ";
            SQL += " AND coworkerid='" + BOMBasicInfoPool.getUserId() + "' ";

            stmt.executeUpdate(SQL);

            conn.commit();
            closeDB(appReg.getString("plm"));

            BOMECOBasicInfoPool.setValidationForEnd("");
            BOMECOBasicInfoPool.setBomValidationResult(false);
            BOMECOBasicInfoPool.setHasErrorInValidation(false);
            BOMECOBasicInfoPool.setPublicMyStatus("2");

            isOK = true;

            MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00298")/*작업재개가 완료되었습니다. 작업을 수행하세요.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00296")/*작업재개 완료*/, 1);
            mbox.setModal(true);
            mbox.setVisible(true);

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    private void connectDB(String dbname) throws ConnectException {
        try {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(dbname);

            conn.setAutoCommit(false);
            stmt = conn.createStatement();
        } catch (Exception e) {
            Kogger.debug(getClass(), "DB connection error\n");
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
