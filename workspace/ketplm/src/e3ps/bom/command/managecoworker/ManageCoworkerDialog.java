package e3ps.bom.command.managecoworker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.KETObjectUtil;
import ext.ket.shared.log.Kogger;

public class ManageCoworkerDialog extends  AbstractAIFDialog
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JTextField cNameTfl = null;
    private JTextField cDeptTfl = null;

    private JButton AddBtn = null;
    private JButton removeBtn = null;
    private JButton okBtn = null;
    private JButton cancelBtn = null;

    private JTable coworkerTbl = null;

    boolean keyCheck = false;
    private BtnListener btnListener;

    private JFrame frmDesktop;
    AbstractAIFUIApplication app;
    BOMRegisterApplicationPanel pnl;
    private BOMRegisterDesktop desktop;
    Registry appReg;

    CoworkerTableData coworkerData;

    private String aryUserData[] = new String[4];
    private String aryOwnerData[]= new String[4];
    private String aryCoworkerData[] = new String[4];
    private String strLoginUserObj ="";

    int coworkerTblRowCnt = 0;

    boolean isCoworkerTblSelected = false;
    boolean isOwnerData = false;
    boolean isMember = false;    //공동작업장의 멤버인지를 체크...

    String orgCode = "";
    String orgName = "";
    String itemCode = "";
    boolean bomGubunFlag = false;

    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    class BtnListener implements ActionListener, KeyListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand().equals("Add"))
            {
                coworkerAdd();
            }

            if(e.getActionCommand().equals("Remove"))
            {
                coworkerRemove();
            }

            if(e.getActionCommand().equals("OK"))
            {
                okManageCoworker();
            }

            if(e.getActionCommand().equals("Cancel"))
            {
                cancelManageCoworker();
            }
        };

        boolean keyCheck = false;

        public void keyPressed(KeyEvent e) {};

        public void keyReleased(KeyEvent e)
        {
            if ( keyCheck )
            {
                JTextField tfl = (JTextField)e.getSource();
                int pos = tfl.getCaretPosition();

                String s = tfl.getText();
                tfl.setText(s.toUpperCase());
                tfl.setCaretPosition(pos);
                keyCheck = false;
            }
        };

        public void keyTyped(KeyEvent e)
        {
            keyCheck = false;

            char c = e.getKeyChar();
            int cCode = (int)c;

            if ( cCode >= 97 && cCode <= 122 )
            {
                keyCheck = true;
            }
        };
    }

    public ManageCoworkerDialog()
    {
    }

    public ManageCoworkerDialog(JFrame frame, AbstractAIFUIApplication app)
    {
        super(frame, true);

        initialize(frame,app);

        setSize(500, 310);

        ScreenCenterer scent = new ScreenCenterer();
        Dimension dimCenter = new Dimension(scent.getCenterDim(this));
        setLocation(dimCenter.width, dimCenter.height);
        setVisible(true);
    }

    private void initialize(JFrame frame, AbstractAIFUIApplication app)
    {
        try
        {
            frmDesktop = frame;
            this.app = app;
            appReg = Registry.getRegistry(app);
            desktop = (BOMRegisterDesktop)frame;
            pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();

            itemCode = BOMBasicInfoPool.getPublicModelName();
            strLoginUserObj = KETObjectUtil.getOidString(KETObjectUtil.getUser(Utility.checkNVL(BOMBasicInfoPool.getUserId())));

            setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00091")/*공동작업자 정보*/);
            setResizable(false);

            orgCode = BOMBasicInfoPool.getOrgCode().trim();
            orgName = BOMBasicInfoPool.getOrgName().trim();
            bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

            coworkerData = new CoworkerTableData();

            // 로그인한 사용자 정보 가져오기...
            getUserData();

            // BOM 생성자 정보 가져오기...
            getBOMOwnerData();

            // 공동작업자 정보 가져오기...
            setCoworkerData();

            setContentInit();

            cNameTfl.requestFocus();
        }
        catch (Throwable ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    private void setContentInit() throws Exception
    {
        try
        {
            btnListener = new BtnListener();

            // Co-Worker Info Panel /////////////////////////////////////////////////
            JPanel coworkerPanel = new JPanel();
            coworkerPanel.setLayout(new BoxLayout(coworkerPanel, BoxLayout.Y_AXIS));
            coworkerPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), " 공동 작업자 정보 ", 0, 0, FontList.defaultFont));
            coworkerPanel.setFont(FontList.defaultFont);
            coworkerPanel.setPreferredSize(new Dimension(400, 220));

            JPanel panel08 = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel cNameLbl = new JLabel("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/ + " : ");
            cNameLbl.setFont(FontList.defaultFont);
            if(BOMBasicInfoPool.isAdmin() || (Utility.checkNVL(aryOwnerData[0]).equals(strLoginUserObj)))
            {
                cNameLbl.setEnabled(true);
            }
            else
            {
                cNameLbl.setEnabled(false);
            }
            panel08.add(cNameLbl);

            cNameTfl = new JTextField("", 16);
            cNameTfl.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    coworkerAdd();
                }
            });
            cNameTfl.setFont(FontList.defaultFont);
            if(BOMBasicInfoPool.isAdmin() || (Utility.checkNVL(aryOwnerData[0]).equals(strLoginUserObj)))
            {
                cNameTfl.setEnabled(true);
            }
            else
            {
                cNameTfl.setEnabled(false);
            }
            panel08.add(cNameTfl);

            JLabel cDeptLbl = new JLabel("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/ + " : ");
            cDeptLbl.setFont(FontList.defaultFont);
            if(BOMBasicInfoPool.isAdmin() || (Utility.checkNVL(aryOwnerData[0]).equals(strLoginUserObj)))
            {
                cDeptLbl.setEnabled(true);
            }
            else
            {
                cDeptLbl.setEnabled(false);
            }
            panel08.add(cDeptLbl);

            cDeptTfl = new JTextField("", 16);
            cDeptTfl.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    coworkerAdd();
                }
            });
            cDeptTfl.setFont(FontList.defaultFont);
            if(BOMBasicInfoPool.isAdmin() || (Utility.checkNVL(aryOwnerData[0]).equals(strLoginUserObj)))
            {
                cDeptTfl.setEnabled(true);
            }
            else
            {
                cDeptTfl.setEnabled(false);
            }
            panel08.add(cDeptTfl);

            AddBtn = new JButton(appReg.getImageIcon("managecoworkerIcon"));
            AddBtn.setActionCommand("Add");
            AddBtn.setToolTipText(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00089")/*공동작업자 검색*/);
            AddBtn.setBorder(BorderList.emptyBorder1);
            AddBtn.addActionListener(btnListener);
            AddBtn.setMargin(new Insets(0,5,0,5));
            if(BOMBasicInfoPool.isAdmin() || (Utility.checkNVL(aryOwnerData[0]).equals(strLoginUserObj)))
            {
                AddBtn.setEnabled(true);
            }
            else
            {
                AddBtn.setEnabled(false);
            }
            panel08.add(AddBtn);

            removeBtn = new JButton(appReg.getImageIcon("userremoveIcon"));
            removeBtn.setActionCommand("Remove");
            removeBtn.setToolTipText(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00090")/*공동작업자 삭제*/);
            removeBtn.setBorder(BorderList.emptyBorder1);
            removeBtn.addActionListener(btnListener);
            removeBtn.setMargin(new Insets(0,5,0,5));
            if(BOMBasicInfoPool.isAdmin() || (Utility.checkNVL(aryOwnerData[0]).equals(strLoginUserObj)))
            {
                removeBtn.setEnabled(true);
            }
            else
            {
                removeBtn.setEnabled(false);
            }
            panel08.add(removeBtn);

            coworkerPanel.add(panel08);

            // Search Result Panel /////////////////////////////////////////////////
            coworkerTbl = new JTable();
            Dimension dimCoWorkerTable = new Dimension(200, 300);
            coworkerTbl.setAutoCreateColumnsFromModel(false);
            coworkerTbl.setPreferredScrollableViewportSize(dimCoWorkerTable);
            coworkerTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            coworkerTbl.setModel(coworkerData);

            for(int k = 0; k < CoworkerTableData.clmWorkerData.length; k++)
            {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(CoworkerTableData.clmWorkerData[k].intAlignment);
                TableColumn column = new TableColumn(k, CoworkerTableData.clmWorkerData[k].intWidth, renderer, null);
                coworkerTbl.addColumn(column);
            }

            JScrollPane ps = new JScrollPane();
            ps.setColumnHeaderView(coworkerTbl.getTableHeader());
            ps.getViewport().add(coworkerTbl);

            coworkerPanel.add(ps);

            coworkerTbl.addMouseListener(new MouseAdapter()
            {
                public void mouseReleased(MouseEvent e)
                {
                    isCoworkerTblSelected = true;
                }
            });

            TableColumnModel columnModel = coworkerTbl.getColumnModel();
            TableColumn column = columnModel.getColumn(0);
            column.setWidth(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setResizable(false);

            this.getContentPane().add(coworkerPanel, BorderLayout.NORTH);

            // Button Panel /////////////////////////////////////////////////
            JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00306")/*저장*/, appReg.getImageIcon("okIcon"));
            okBtn.setFont(FontList.defaultFont);
            okBtn.setActionCommand("OK");
            okBtn.addActionListener(btnListener);
            okBtn.setMargin(new Insets(0,5,0,5));
            if(BOMBasicInfoPool.isAdmin() || (Utility.checkNVL(aryOwnerData[0]).equals(strLoginUserObj)))
            {
                okBtn.setEnabled(true);
            }
            else
            {
                okBtn.setEnabled(false);
            }
            btnFlowPanel.add(okBtn);

            cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
            cancelBtn.setFont(FontList.defaultFont);
            cancelBtn.setActionCommand("Cancel");
            cancelBtn.addActionListener(btnListener);
            cancelBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(cancelBtn);

            this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    private void setCoworkerData(String name, String dept)
    {
        try
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            FindUserDialog dlgFindUser = null;

            int coworkNameIdx0 = 0;
            int coworkNameIdx = 0;
            int coworkNameIdx2 = 0;

            for(int i=0; i<coworkerTbl.getColumnCount(); i++)
            {
                if (coworkerTbl.getColumnName(i).toString().equals("Id"))
                {
                    coworkNameIdx0 = i;
                }
            }

            for(int i=0; i<coworkerTbl.getColumnCount(); i++)
            {
                if (coworkerTbl.getColumnName(i).toString().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/))
                {
                    coworkNameIdx = i;
                }
            }
            /*
            for(int i=0; i<coworkerTbl.getColumnCount(); i++)
            {
                if (coworkerTbl.getColumnName(i).toString().equals("E-Mail"))
                {
                    coworkNameIdx2 = i;
                }
            }*/

            dlgFindUser = new FindUserDialog(cNameTfl.getText(), cDeptTfl.getText(), app, "공동 작업자");

            if(dlgFindUser.getOK() == true)
            {
                aryCoworkerData = dlgFindUser.getSelectedColumnData();
                int RowCnt = coworkerTbl.getRowCount();
                boolean isDuplicate = false;
                for(int i=0; i<RowCnt; i++)
                {
                    String UserId = (String)coworkerTbl.getValueAt(i, coworkNameIdx0);
                    //String UserName = (String)coworkerTbl.getValueAt(i, coworkNameIdx);
                    //String eMailStr = (String)coworkerTbl.getValueAt(i, coworkNameIdx2);

                    if(aryCoworkerData[0].equals(UserId))
                    {
                        isDuplicate = true;
                    }
                    /*if(aryCoworkerData[1].equals(UserName))
                    {
                        isDuplicate = true;
                    }*/
                }

                if(isDuplicate)
                {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(frmDesktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00293", aryCoworkerData[1])/*작업자 ({0}) 는 이미 존재합니다*/, "Input Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    cNameTfl.setText("");
                }
                else
                {
                    int tableRowCnt = coworkerTbl.getRowCount();
                    coworkerData.vecWorkerData.add(tableRowCnt,new CoworkerData(aryCoworkerData[0], aryCoworkerData[1], aryCoworkerData[2], aryCoworkerData[3]));
                    cNameTfl.setText("");
                    coworkerData.fireTableRowsInserted(0, coworkerData.getRowCount()-1);
                    coworkerTbl.clearSelection();
                }

                aryCoworkerData[0] = "";
                aryCoworkerData[1] = "";
                aryCoworkerData[2] = "";
                aryCoworkerData[3] = "";
            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch(Exception e)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), e);
        }
    }

    private void coworkerAdd()
    {
        try
        {
            // [2011-03-10] 윤도혁J 요구사항 반영 :: 공동작업자 추가 시, 검색조건 미입력 허용
//            ValidationChecker vc = new ValidationChecker();
//
//            boolean isEmpty = false;
//            if(vc.isEmpty(cNameTfl) && vc.isEmpty(cDeptTfl))
//            {
//                isEmpty = true;
//            }
//            else
//            {
//                isEmpty = false;
//            }
//
//            if(isEmpty)
//            {
//                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//                MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("inputCoworkerInfo"), "Input Error", 0);
//                mbox.setModal(true);
//                mbox.setVisible(true);
//                cNameTfl.requestFocus();
//                coworkerTbl.clearSelection();
//            }
//            else
//            {
                setCoworkerData(cNameTfl.getText().toString().trim(), cDeptTfl.getText().toString().trim());
//            }
        }
        catch (Throwable ex)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
        }
    }

    private void coworkerRemove()
    {
        try
        {
            int selRow = coworkerTbl.getSelectedRow();

            int nameIdx = 0;
            int emailIdx = 0;

            for(int i=0; i<coworkerTbl.getColumnCount(); i++)
            {
                if (coworkerTbl.getColumnName(i).toString().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/))
                {
                    nameIdx = i;
                }
                if (coworkerTbl.getColumnName(i).toString().equals("E-Mail"))
                {
                    emailIdx = i;
                }
            }

            if(isCoworkerTblSelected == false)     {
                MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("selectRow11"), "Remove Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                cNameTfl.setText("");
                cDeptTfl.setText("");
            } else {

                String strSelectedUserId =  (String)coworkerTbl.getValueAt(selRow, 0);
                String strSelectedUserObj = KETObjectUtil.getOidString(KETObjectUtil.getUser(strSelectedUserId));

                if(aryOwnerData[0].equals(strSelectedUserObj))
                {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("coworker1"), "Remove Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    cNameTfl.setText("");
                    cDeptTfl.setText("");
                }
                else if (BOMBasicInfoPool.getUserId().equals(strSelectedUserId))
                {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("coworker2"), "Remove Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    cNameTfl.setText("");
                    cDeptTfl.setText("");
                }
                else
                {
                    String rowStr = (String)coworkerTbl.getValueAt(selRow, nameIdx) + "(" + (String)coworkerTbl.getValueAt(selRow, emailIdx) + ")";

                    int n = JOptionPane.showConfirmDialog(this, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00220")/*선택된 공동작업자를 삭제하시겠습니까?*/ + " \n" + rowStr, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00217")/*삭제확인*/, JOptionPane.YES_NO_OPTION);
                    if(n==JOptionPane.YES_OPTION)
                    {
                        coworkerData.vecWorkerData.removeElementAt(selRow);
                        coworkerData.fireTableRowsDeleted(0, coworkerData.getRowCount());
                        isCoworkerTblSelected = false;
                        coworkerTbl.clearSelection();
                    }
                    cNameTfl.setText("");
                    cDeptTfl.setText("");
                }
            }
        }
        catch (Throwable ex)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
        }
    }

    private void getUserData()
    {
        try
        {
            aryUserData[0] = Utility.checkNVL(BOMBasicInfoPool.getUserId());
            aryUserData[1] = Utility.checkNVL(BOMBasicInfoPool.getUserName());
            aryUserData[2] = Utility.checkNVL(BOMBasicInfoPool.getUserDept());
            aryUserData[3] = Utility.checkNVL(BOMBasicInfoPool.getUserEMail());
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    private void getBOMOwnerData()
    {
        try
        {
            aryOwnerData[0] = Utility.checkNVL(BOMBasicInfoPool.getPublicOwnerId());
            aryOwnerData[1] = Utility.checkNVL(BOMBasicInfoPool.getPublicOwnerName());
            aryOwnerData[2] = Utility.checkNVL(BOMBasicInfoPool.getPublicOwnerDept());
            aryOwnerData[3] = Utility.checkNVL(BOMBasicInfoPool.getPublicOwnerEmail());

            isOwnerData = true;
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
            isOwnerData = false;
        }
    }

    private void setCoworkerData()
    {
        String SQL = "";

        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(appReg.getString("plm"));

            stmt = conn.createStatement();

            if(bomGubunFlag)
            {
                SQL =  " SELECT                                            ";
                SQL += " coworkerid id                                ";
                SQL += ", coworkername name                        ";
                SQL += ", coworkerdept dept                            ";
                SQL += ", coworkeremail mail                         ";
                SQL += " FROM                                            ";
                SQL += " ketbomcoworker                            ";
                SQL += " WHERE                                        ";
                SQL += " newbomcode = '" + itemCode + "'        ";
            }
            else
            {
                SQL =  " SELECT                                                                                                                ";
                SQL += " coworkerid id                                                                                                    ";
                SQL += ", coworkername name                                                                                            ";
                SQL += ", coworkerdept dept                                                                                                ";
                SQL += ", coworkeremail mail                                                                                            ";
                SQL += " FROM                                                                                                                ";
                SQL += " ketbomecocoworker                                                                                            ";
                SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'    ";
                SQL += " AND ecoitemcode = '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName()) + "'            ";
            }
Kogger.debug(getClass(), ">>>>  SQL : " + SQL);
            rs = stmt.executeQuery(SQL);

            coworkerData = new CoworkerTableData();

            while(rs.next())
            {
                if( ( rs.getString("id") == null ? "" : rs.getString("id").toString().trim() ).equals( BOMBasicInfoPool.getUserId().trim() ) )
                {
                    isMember = true;
                }

                coworkerData.vecWorkerData.addElement(new CoworkerData(rs.getString("id"), rs.getString("name"), rs.getString("mail"), rs.getString("dept")));
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
                if(rs != null)
                    rs.close();
                stmt.close();
            }
            catch(Exception e)
            {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
            finally
            {
                if(res != null)
                {
                    res.freeConnection(appReg.getString("plm"), conn);
                }
            }
        }
    }

    private void okManageCoworker()
    {
        String query = "";
        String query1 = "";
        String query2 = "";
        Vector totalVec = new Vector();

        totalVec.removeAllElements();
        int tblCnt = coworkerTbl.getRowCount();

        if(tblCnt == 0)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("checkCoworker"), "Input Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }

        if(bomGubunFlag)
        {
            try
            {
                Vector coworkers = new Vector();
                for( int i=0; i<tblCnt; i++ )
                {
                    coworkers.addElement((String)coworkerTbl.getValueAt(i, 0));
                }

    Kogger.debug(getClass(), "=====>> BOMBasicInfoPool.getPublicBOMOid() : " + BOMBasicInfoPool.getPublicBOMOid());

                String returnStr = KETBomHelper.service.manageCoworker((BOMBasicInfoPool.getPublicBOMOid()==null?"":BOMBasicInfoPool.getPublicBOMOid().trim()), coworkers);
                if(returnStr.equals("OK"))
                {
                    res = DBConnectionManager.getInstance();
                    conn = res.getConnection(appReg.getString("plm"));

                    stmt = conn.createStatement();

                    //update 하기전의 coworker 데이터를 가져온다
                    query = " SELECT                                                        \n"
                            + "        coworkerid                                                \n"
                            + "     ,    coworkername                                            \n"
                            + " ,    coworkerdept                                            \n"
                            + "     ,    coworkeremail                                            \n"
                            + "     ,    endworkingflag                                            \n"
                            + "     FROM ketbomcoworker                                    \n"
                            + "     WHERE newbomcode = '" + itemCode + "'               \n";

                    rs = stmt.executeQuery(query);

                    while (rs.next())
                    {
                        Vector coworkerVec = new Vector();
                        coworkerVec.removeAllElements();
                        coworkerVec.addElement(rs.getString("coworkerid"));
                        coworkerVec.addElement(rs.getString("coworkername"));
                        coworkerVec.addElement(rs.getString("coworkerdept"));
                        coworkerVec.addElement(rs.getString("coworkeremail"));
                        coworkerVec.addElement(rs.getString("endworkingflag"));
                        totalVec.addElement(coworkerVec);
                    }

                    //기존의 데이터를 모두 지운다
                    query1  = " DELETE FROM ketbomcoworker WHERE newbomcode = '" + itemCode + "' ";

                    stmt.executeUpdate(query1);


                    Hashtable inputParams = new Hashtable();
                    Vector itemVec = new Vector();
                    itemVec.add(itemCode);
                    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
                    inputParams.put("itemCode", itemVec);
                    Vector version = KETBomHelper.service.getItemVersion(inputParams);
                    String versionStr = "";
                    for(int k=0; k<version.size(); k++) {
                        if(version.size() > 0)     {
                            versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
                            //shin...
                            if(itemCode.equals(versionStr.substring(0, versionStr.indexOf("@")))) {
                                versionStr = versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf("("));
                            }
                        }
                    }

                    for(int indx=0; indx < tblCnt; indx++)
                    {
                        String tmpUserId = ((String)coworkerTbl.getValueAt(indx,0)).trim();
                        String tmpUserName = ((String)coworkerTbl.getValueAt(indx, 1)).trim();
                        String tmpUserMail = ((String)coworkerTbl.getValueAt(indx, 2)).trim();
                        String tmpUserDept = ((String)coworkerTbl.getValueAt(indx,3)).trim();

                        boolean isExistUser = false;
                        String  tmpStatus = "";

                        for (int idx = 0; idx < totalVec.size(); idx++)
                        {
                            Vector tVec = (Vector)totalVec.elementAt(idx);
                            if (tmpUserId.equals((String)(tVec.elementAt(0))))
                            {
                                isExistUser = true;
                                tmpStatus = (String)(tVec.elementAt(4));
                                break;
                            }
                        }

Kogger.debug(getClass(), ">>>itemCode : " + itemCode);
Kogger.debug(getClass(), ">>>tmpUserId : " + tmpUserId);
Kogger.debug(getClass(), ">>>tmpUserName : " + tmpUserName);
Kogger.debug(getClass(), ">>>tmpUserDept : " + tmpUserDept);
Kogger.debug(getClass(), ">>>tmpUserMail : " + tmpUserMail);
Kogger.debug(getClass(), ">>>tmpStatus : " + tmpStatus);
Kogger.debug(getClass(), ">>>isExistUser : " + isExistUser);


                        // 기존에 등록된 공동작업자
                        if (isExistUser)
                        {
                            query2 = " INSERT INTO ketbomcoworker (                            \n"
                                        + "        newbomcode                                            \n"
                                        + "     ,    coworkerid                                                \n"
                                        + "     ,    coworkername                                            \n"
                                        + " ,    coworkerdept                                            \n"
                                        + "     ,    coworkeremail                                            \n"
                                        + "     ,    endworkingflag , bomversion )                        \n"
                                        + "     VALUES (                                                    \n"
                                        + "     '" + itemCode + "'                                        \n"
                                        + "     ,    '" + tmpUserId + "'                                        \n"
                                        + "     ,    '" + tmpUserName + "'                                    \n"
                                        + "     ,    '" + tmpUserDept + "'                                    \n"
                                        + "     ,    '" + tmpUserMail + "'                                    \n"
                                        + "     ,    '" + tmpStatus + "' , '"+versionStr+"' )                \n";
                        }
                        else
                        {
                            query2 = " INSERT INTO ketbomcoworker (                            \n"
                                        + "        newbomcode                                            \n"
                                        + "     ,    coworkerid                                                \n"
                                        + "     ,    coworkername                                            \n"
                                        + " ,    coworkerdept                                            \n"
                                        + "     ,    coworkeremail                                            \n"
                                        + "     ,    endworkingflag ,bomversion )                        \n"
                                        + "     VALUES (                                                    \n"
                                        + "     '" + itemCode + "'                                        \n"
                                        + "     ,    '" + tmpUserId + "'                                        \n"
                                        + "     ,    '" + tmpUserName + "'                                    \n"
                                        + "     ,    '" + tmpUserDept + "'                                    \n"
                                        + "     ,    '" + tmpUserMail + "'                                    \n"
                                        + "     ,    '1' , '"+versionStr+"')                                    \n";
                        }

Kogger.debug(getClass(), ">>> query2 : " + query2);

                        stmt.executeUpdate(query2);
                    }
                    conn.commit();
                    cancelManageCoworker();
                }
                else
                {
                    cancelManageCoworker();
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                }
            }
            catch(Exception e)
            {
                Kogger.error(getClass(), e);
                try
                {
                    conn.rollback();
                }
                catch (Exception dbex)
                {}

                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
            finally
            {
                try
                {
                    if(rs != null)
                        rs.close();
                    stmt.close();
                }
                catch(Exception ex)
                {
                    Kogger.error(getClass(), ex);
                }
                finally
                {
                    if(res != null)
                    {
                        res.freeConnection(appReg.getString("plm"), conn);
                    }
                }
            }
        }
        else
        {
            try
            {
                Vector coworkers = new Vector();
                for( int i=0; i<tblCnt; i++ )
                {
                    coworkers.addElement((String)coworkerTbl.getValueAt(i, 0));
                }

Kogger.debug(getClass(), "=====>> BOMBasicInfoPool.getPublicBOMOid() : " + BOMBasicInfoPool.getPublicBOMOid());

                String returnStr = KETBomHelper.service.manageCoworker( (BOMBasicInfoPool.getPublicBOMOid()==null?"":BOMBasicInfoPool.getPublicBOMOid().trim()), coworkers);

                if(returnStr.equals("OK"))
                {
                    res = DBConnectionManager.getInstance();
                    conn = res.getConnection(appReg.getString("plm"));

                    stmt = conn.createStatement();

                    //update 하기전의 coworker 데이터를 가져온다
                    query = " SELECT                                                                                                                            \n"
                            + "        coworkerid                                                                                                                    \n"
                            + "     ,    coworkername                                                                                                                \n"
                            + " ,    coworkerdept                                                                                                                \n"
                            + "     ,    coworkeremail                                                                                                                \n"
                            + "     ,    endworkingflag                                                                                                                \n"
                            + "     FROM ketbomecocoworker                                                                                                    \n"
                            + "     WHERE ecoheadernumber = '" +Utility.checkNVL( BOMBasicInfoPool.getBomEcoNumber()) + "'                    \n"
                            + "       AND  ecoitemcode = '" +Utility.checkNVL( BOMBasicInfoPool.getPublicModelName()) + "'                        \n";

                    rs = stmt.executeQuery(query);

                    while (rs.next())
                    {
                        Vector coworkerVec = new Vector();
                        coworkerVec.removeAllElements();
                        coworkerVec.addElement(rs.getString("coworkerid"));
                        coworkerVec.addElement(rs.getString("coworkername"));
                        coworkerVec.addElement(rs.getString("coworkerdept"));
                        coworkerVec.addElement(rs.getString("coworkeremail"));
                        coworkerVec.addElement(rs.getString("endworkingflag"));
                        totalVec.addElement(coworkerVec);
                    }

                    //기존의 데이터를 모두 지운다
                    query  = " DELETE FROM ketbomecocoworker                                                                               \n"
                            +  " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'     \n"
                            +  "  AND   ecoitemcode = '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName()) + "'             \n";

                    stmt.executeUpdate(query);

                    for(int indx=0; indx < tblCnt; indx++)
                    {
                        String tmpUserId = ((String)coworkerTbl.getValueAt(indx,0)).trim();
                        String tmpUserName = ((String)coworkerTbl.getValueAt(indx, 1)).trim();
                        String tmpUserMail = ((String)coworkerTbl.getValueAt(indx, 2)).trim();
                        String tmpUserDept = ((String)coworkerTbl.getValueAt(indx, 3)).trim();

                        boolean isExistUser = false;
                        String  tmpStatus = "";

                        for (int idx = 0; idx < totalVec.size(); idx++)
                        {
                            Vector tVec = (Vector)totalVec.elementAt(idx);
                            if (tmpUserId.equals((String)(tVec.elementAt(0))))
                            {
                                isExistUser = true;
                                tmpStatus = (String)(tVec.elementAt(4));
                                break;
                            }
                        }

                        // 기존에 등록된 공동작업자
                        if (isExistUser)
                        {
                            query = " INSERT INTO ketbomecocoworker (                                                        \n"
                                    + "        ecoheadernumber                                                                        \n"
                                    + "     ,    ecoitemcode                                                                                \n"
                                    + "     ,    coworkerid                                                                                \n"
                                    + "     ,    coworkername                                                                            \n"
                                    + " ,    coworkerdept                                                                            \n"
                                    + "     ,    coworkeremail                                                                            \n"
                                    + "     ,    endworkingflag )                                                                        \n"
                                    + "     VALUES (                                                                                    \n"
                                    + "     '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'                \n"
                                    + " ,    '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName()) + "'            \n"
                                    + "     ,    '" + tmpUserId + "'                                                                        \n"
                                    + "     ,    '" + tmpUserName + "'                                                                    \n"
                                    + "     ,    '" + tmpUserDept + "'                                                                    \n"
                                    + "     ,    '" + tmpUserMail + "'                                                                    \n"
                                    + "     ,    '" + tmpStatus + "' )                                                                    \n";
                        }
                        else
                        {
                            query = " INSERT INTO ketbomecocoworker (                                                        \n"
                                    + "        ecoheadernumber                                                                        \n"
                                    + "     ,    ecoitemcode                                                                                \n"
                                    + "     ,    coworkerid                                                                                \n"
                                    + "     ,    coworkername                                                                            \n"
                                    + " ,    coworkerdept                                                                            \n"
                                    + "     ,    coworkeremail                                                                            \n"
                                    + "     ,    endworkingflag )                                                                        \n"
                                    + "     VALUES (                                                                                    \n"
                                    + "     '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'                \n"
                                    + " ,    '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName()) + "'            \n"
                                    + "     ,    '" + tmpUserId + "'                                                                        \n"
                                    + "     ,    '" + tmpUserName + "'                                                                    \n"
                                    + "     ,    '" + tmpUserDept + "'                                                                    \n"
                                    + "     ,    '" + tmpUserMail + "'                                                                    \n"
                                    + "     ,    '1' )                                                                                        \n";
                        }

                        stmt.executeUpdate(query);
                    }
                    conn.commit();
                    cancelManageCoworker();
                }
                else
                {
                    cancelManageCoworker();
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                }
            }
            catch(Exception e)
            {
                Kogger.error(getClass(), e);
                try
                {
                    conn.rollback();
                }
                catch (Exception dbex)
                {}

                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
            finally
            {
                try
                {
                    if(rs != null)
                        rs.close();
                    stmt.close();
                }
                catch(Exception ex)
                {
                    Kogger.error(getClass(), ex);
                }
                finally
                {
                    if(res != null)
                    {
                        res.freeConnection(appReg.getString("plm"), conn);
                    }
                }
            }
        }
    }

    private void cancelManageCoworker()
    {
        try
        {
            int count = getComponentCount();
            for(int i=0; i<count; i++)
            {
                Component c = getComponent(i);
                this.remove(c);
                c = null;
            }

            super.dispose();
            System.gc();
            return;
        }
        catch (Throwable ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

}
