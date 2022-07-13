package e3ps.bom.command.confirmbom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import wt.fc.Persistable;
import wt.org.WTPrincipalReference;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.entity.KETBomHeader;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.wfm.entity.KETWfmApprovalLine;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;

public class ConfirmBomFrame extends AbstractAIFDialog // implements InterfaceAIFOperationListener
{
    private static final long serialVersionUID = 1L;
    private BtnListener btnListener;
    JTable tblList = new JTable();
    JTable tblList02 = new JTable();
    public JTextArea comment;

    private ConfirmBomTableData dataList01;
    private DistributionBomTableData dataList02;
    Registry appReg;
    boolean keyCheck = false;

    private AbstractAIFUIApplication app;
    private JButton searchBtn, searchBtn02, okBtn, requestBtn, closeBtn;

    String itemCodeStr = "";
    String titleStr = "";

    private JFrame frmDesktop;
    BOMRegisterApplicationPanel pnl;
    private BOMRegisterDesktop desktop;

    private String agreeRadio = "noDiscuss";

    private String oid = "";

    public KETBomHeader bomHeader = null;

    class BtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if( e.getActionCommand().equals("Search01") )
            {
                confirmLineSearch();
            }
            else if( e.getActionCommand().equals("Search02") )
            {
                int confirmRowCnt = tblList.getRowCount();

                if( confirmRowCnt > 0 )
                {
                    distributionSearch();
                }
                else
                {
                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00147")/*먼저 결재선을 지정하십시요.*/, "Select Error", 0);
                    messagebox.setModal(true);
                    messagebox.setVisible(true);
                    return;
                }
            }
            else if( e.getActionCommand().equals("SAVE") )
            {
                KETWfmApprovalMaster chk_master =null;

                try
                {
                    chk_master = WorkflowSearchHelper.manager.getMaster(bomHeader);
                }
                catch( Exception ee )
                {

                }

                //if(chk_master.equals("") || chk_master == null)
                if(chk_master == null)
                {
                    if( getSaveBtn_process() != null )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00309")/*저장되었습니다.*/, "Message", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }
                    else
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00310")/*저장중 오류가 발생했습니다.*/, "Select Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }
                }
                else
                {
                    if( getUpdateBtn_process() != null )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00244")/*수정되었습니다.*/, "Message", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }
                    else
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00245")/*수정저장중 오류가 발생했습니다.*/, "Select Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }
                }
            }
            else if( e.getActionCommand().equals("CONFIRM") )
            {
                if( startProcess() )
                {
                    KetMainJTreeTable kj = new KetMainJTreeTable();
                    pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
                    pnl.clearBOMList();
                    kj.setGenMain(app);
                    disposeScreen();
                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00068")/*결재 요청 되었습니다.*/, "Message", 0);
                    messagebox.setModal(true);
                    messagebox.setVisible(true);
                    return;
                }
//                else{
//                    MessageBox messagebox = new MessageBox(desktop, "결재 요청중 오류가 발생했습니다.", "Select Error", 0);
//                    messagebox.setModal(true);
//                    messagebox.setVisible(true);
//                    return;
//                }
            }
            else if( e.getActionCommand().equals("Close") )
            {
               disposeScreen();
            }
        }
    }

    public ConfirmBomFrame()
    {

    }

    public ConfirmBomFrame(JFrame frame, AbstractAIFUIApplication app)
    {
        super(frame, true);

        initialize(frame, app);
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

            setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00067")/*결재 요청*/);
            setResizable(false);

            this.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    disposeScreen();
                }
            });

            try
            {
                dataList01 = new ConfirmBomTableData();
                dataList02 = new DistributionBomTableData();

                jInit();

                setSize(450,620);
                setResizable(false);

                ScreenCenterer scent = new ScreenCenterer();
                Dimension dimCenter = new Dimension(scent.getCenterDim(this));
                setLocation(dimCenter.width, dimCenter.height);
                setVisible(true);
            }
            catch(Exception e)
            {
                Kogger.error(getClass(), e);
            }
        }
        catch (Throwable ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    private void jInit() throws Exception
    {
        try
        {
            TableColumnModel columnModel = null;
            TableColumnModel columnModel02 = null;
            btnListener = new BtnListener();

            // Search Condition Panel /////////////////////////////////////////////////
            JPanel topPanel = new JPanel();
            JPanel bottomPanel = new JPanel();
            JPanel commentPanel = new JPanel();
            JPanel allPanel = new JPanel();

            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00073")/*결재선*/, 0, 0, FontList.defaultFont));
            topPanel.setFont(FontList.defaultFont);
            topPanel.setPreferredSize(new Dimension(430, 340));

            // Search / Clear 버튼 라인
            JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            searchBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00074")/*결재선 지정*/, appReg.getImageIcon("confirmlineIcon"));
            searchBtn.setFont(FontList.defaultFont);
            searchBtn.setActionCommand("Search01");
            searchBtn.addActionListener(btnListener);
            searchBtn.setMargin(new Insets(0,5,0,5));
            panel02.add(searchBtn);

            topPanel.add(panel02);

            // Search Result Panel /////////////////////////////////////////////////
            JPanel centerPanel = new JPanel(new BorderLayout());

            tblList.setAutoCreateColumnsFromModel(false);
            tblList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tblList.setModel(dataList01);
            tblList.repaint();

            for( int k = 0; k < ConfirmBomTableData.clmModelData.length; k++ )
            {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(ConfirmBomTableData.clmModelData[k].intAlignment);
                TableColumn column = new TableColumn(k, ConfirmBomTableData.clmModelData[k].intWidth, renderer, null);
                tblList.addColumn(column);

                columnModel = tblList.getColumnModel();
                TableColumn _column = columnModel.getColumn(k);
                _column.setCellRenderer(renderer);
            }

            JTableHeader headerModel = tblList.getTableHeader();
            headerModel.setUpdateTableInRealTime(false);

            JScrollPane ps = new JScrollPane();
            ps.getViewport().add(tblList);

            columnModel = tblList.getColumnModel();
            TableColumn column = columnModel.getColumn(5);
            column.setWidth(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setResizable(false);

            column = columnModel.getColumn(6);
            column.setWidth(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setResizable(false);

            centerPanel.add(ps, BorderLayout.CENTER);
            topPanel.add(centerPanel);

            //-------------------------------------------------------------------------------------------------------

            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
            bottomPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00156")/*배포처*/, 0, 0, FontList.defaultFont));
            bottomPanel.setFont(FontList.defaultFont);
            bottomPanel.setPreferredSize(new Dimension(430, 340));


            // Search / Clear 버튼 라인
            JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            searchBtn02 = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00157")/*배포처 지정*/, appReg.getImageIcon("distributionIcon"));
            searchBtn02.setFont(FontList.defaultFont);
            searchBtn02.setActionCommand("Search02");
            searchBtn02.addActionListener(btnListener);
            searchBtn02.setMargin(new Insets(0,5,0,5));
            panel03.add(searchBtn02);

            bottomPanel.add(panel03);

            // Search Result Panel /////////////////////////////////////////////////
            JPanel centerPanel02 = new JPanel(new BorderLayout());

            tblList02.setAutoCreateColumnsFromModel(false);
            tblList02.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tblList02.setModel(dataList02);
            tblList02.repaint();

            for( int t = 0; t < ConfirmBomTableData.clmModelData.length; t++ )
            {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(ConfirmBomTableData.clmModelData[t].intAlignment);
                TableColumn column02 = new TableColumn(t, ConfirmBomTableData.clmModelData[t].intWidth, renderer, null);
                tblList02.addColumn(column02);

                columnModel02 = tblList02.getColumnModel();
                TableColumn _column = columnModel02.getColumn(t);
                _column.setCellRenderer(renderer);
            }

            JTableHeader headerModel02 = tblList02.getTableHeader();
            headerModel02.setUpdateTableInRealTime(false);

            JScrollPane ps2 = new JScrollPane();
            ps2.getViewport().add(tblList02);

            columnModel02 = tblList02.getColumnModel();
            TableColumn column02 = columnModel02.getColumn(5);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);

            column02 = columnModel02.getColumn(6);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);

            centerPanel02.add(ps2, BorderLayout.CENTER);
            bottomPanel.add(centerPanel02);
            //------------------------------------------------------------------------------------

            //commentPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            commentPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00069")/*결재 요청 의견 (600자 이내)*/, 0, 0, FontList.defaultFont));
            commentPanel.setFont(FontList.defaultFont);

            comment = new JTextArea(330,80);
            comment.setLineWrap(true);
            comment.setWrapStyleWord(true);
            comment.setEditable(true);
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.getViewport().add(comment);
            jscrollpane.setPreferredSize(new Dimension(380,100));
            commentPanel.add(jscrollpane);

            topPanel.setPreferredSize( new Dimension( 400, 200 ) );
            bottomPanel.setPreferredSize( new Dimension( 400, 200 ) );
            commentPanel.setPreferredSize( new Dimension( 400, 130 ) );

            allPanel.add(topPanel);
            allPanel.add(bottomPanel);
            allPanel.add(commentPanel);

            this.getContentPane().add(allPanel, BorderLayout.CENTER);

            // Button Panel /////////////////////////////////////////////////
            JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00306")/*저장*/, appReg.getImageIcon("savebomIcon"));
            okBtn.setFont(FontList.defaultFont);
            okBtn.setActionCommand("SAVE");
            okBtn.addActionListener(btnListener);
            okBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(okBtn);

            requestBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00077")/*결재요청*/, appReg.getImageIcon("confirmIcon"));
            requestBtn.setFont(FontList.defaultFont);
            requestBtn.setActionCommand("CONFIRM");
            requestBtn.addActionListener(btnListener);
            requestBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(requestBtn);

            closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
            closeBtn.setFont(FontList.defaultFont);
            closeBtn.setActionCommand("Close");
            closeBtn.addActionListener(btnListener);
            closeBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(closeBtn);

            loadData();
            this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    private void loadData() throws Exception
    {
        String itemCode = "";
        KETBomHeader header = null;
        KETWfmApprovalMaster master = null;

        try
        {
            BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
            BOMTreeTableModel model = (BOMTreeTableModel)bomPanel.getTreeTableModel();
            BOMTreeNode rootNode = (BOMTreeNode)model.getRootNode();

            itemCode = rootNode.getBOMComponent().getItemCodeStr();
            KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
            header = query.getBOMHeader( itemCode );

            if( header != null )
            {
                master = WorkflowSearchHelper.manager.getMaster(header);
                bomHeader = header;
            }

            HashMap bReview = new HashMap();
            HashMap discuss = new HashMap();
            HashMap aReview = new HashMap();
            HashMap receiver = new HashMap();
            HashMap reference = new HashMap();

            if( master != null )
            {
                this.agreeRadio = master.getAgreementType();
                Vector vec = WorkflowSearchHelper.manager.getApprovalLine(master);

                for( int i = 0; i < vec.size(); i++ )
                {
                    KETWfmApprovalLine line = (KETWfmApprovalLine)vec.get(i);
                    People user = PeopleHelper.manager.getPeople(line.getApproverID());

                    if( line.getApprovalType().equals("beforeReview") )
                    {
                        bReview.put(line.getApprovalOrder(),user);
                    }
                    else if( line.getApprovalType().equals("discuss") )
                    {
                        discuss.put(line.getApprovalOrder(),user);
                    }
                    else if( line.getApprovalType().equals("afterReview") )
                    {
                        aReview.put(line.getApprovalOrder(),user);
                    }
                    else if( line.getApprovalType().equals("receiver") )
                    {
                        receiver.put(line.getApprovalOrder(),user);
                    }
                    else
                    {
                        reference.put(line.getApprovalOrder(),user);
                    }
                }

                String textMemo = master.getComments();
                comment.setText(textMemo);

                String gubun = "";
                int tableRowCnt = tblList.getRowCount();

                int appIndex = 0;
                int distIndex = 0;

                appIndex = aReview.size() + discuss.size() + bReview.size();
                distIndex = receiver.size() + reference.size();
                boolean approverFlag = true;

                // 결재선 로딩
//                for( int i = bReview.size() - 1; i >= 0; i-- )
                for( int i = 0; i < bReview.size(); i++ )
                {
                    gubun = (appIndex == 1) ? KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00250")/*승인*/ : KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00064")/*검토*/;
//                    gubun = approverFlag ? "승인" : "검토";

                    Vector vecModel01 = dataList01.vecWorkerData;
                    People userInfo = (People)bReview.get(i);
                    dataList01.vecWorkerData.add(tableRowCnt,new ConfirmBomData(
                        gubun
                        ,userInfo.getDepartment().getName()
                        ,userInfo.getDuty()
                        ,userInfo.getId()
                        ,userInfo.getName()
                        ,"A"
//                        ,String.valueOf(tableRowCnt+1)
                        ,String.valueOf( appIndex )
                    ));
                    dataList01.fireTableRowsInserted(0, dataList01.getRowCount()-1);
                    appIndex--;
                    approverFlag = false;
                }

//                for( int i = discuss.size() - 1; i >= 0; i-- )
                for( int i = 0; i < discuss.size(); i++ )
                {
                    Vector vecModel01 = dataList01.vecWorkerData;
                    People userInfo = (People)discuss.get(i);
                    dataList01.vecWorkerData.add(tableRowCnt,new ConfirmBomData(
                            KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00350")/*합의*/
                        ,userInfo.getDepartment().getName()
                        ,userInfo.getDuty()
                        ,userInfo.getId()
                        ,userInfo.getName()
                        ,"B"
//                        ,String.valueOf(tableRowCnt+1)
                        ,String.valueOf( appIndex )
                    ));

                    dataList01.fireTableRowsInserted(0, dataList01.getRowCount()-1);
                    appIndex--;
                }

//                for( int i = aReview.size() - 1; i >= 0; i-- )
                for( int i = 0; i < aReview.size(); i++ )
                {
                    gubun = (appIndex == 1) ? KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00250")/*승인*/ : KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00064")/*검토*/;
//                    gubun = approverFlag ? "승인" : "검토";

                    Vector vecModel01 = dataList01.vecWorkerData;
                    People userInfo = (People)aReview.get(i);
                    dataList01.vecWorkerData.add(tableRowCnt,new ConfirmBomData(
                        gubun
                        ,userInfo.getDepartment().getName()
                        ,userInfo.getDuty()
                        ,userInfo.getId()
                        ,userInfo.getName()
                        ,"C"
//                        ,String.valueOf(tableRowCnt+1)
                        ,String.valueOf( appIndex )
                    ));

                    dataList01.fireTableRowsInserted(0, dataList01.getRowCount()-1);
                    appIndex--;
                    approverFlag = false;
                }

                // 배포처 로딩
                int tableRowCnt02 = tblList02.getRowCount();

                for( int i = reference.size() - 1; i >= 0; i-- )
                {
                    Vector vecModel01 = dataList02.vecWorkerData;
                    People userInfo = (People)reference.get(i);
                    dataList02.vecWorkerData.add(tableRowCnt02,new ConfirmBomData(
                            KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00326")/*참조*/
                        ,userInfo.getDepartment().getName()
                        ,userInfo.getDuty()
                        ,userInfo.getId()
                        ,userInfo.getName()
                        ,"BB"
                        ,String.valueOf(tableRowCnt02+1)
//                        ,String.valueOf( distIndex )
                    ));
                    dataList02.fireTableRowsInserted(0, dataList02.getRowCount()-1);
                    distIndex--;
                }

                for( int i = receiver.size() - 1; i >= 0; i-- )
                {
                    Vector vecModel01 = dataList02.vecWorkerData;
                    People userInfo = (People)receiver.get(i);
                    dataList02.vecWorkerData.add(tableRowCnt02,new ConfirmBomData(
                            KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00236")/*수신*/
                        ,userInfo.getDepartment().getName()
                        ,userInfo.getDuty()
                        ,userInfo.getId()
                        ,userInfo.getName()
                        ,"AA"
                        ,String.valueOf(tableRowCnt02+1)
//                        ,String.valueOf( distIndex )
                    ));

                    dataList02.fireTableRowsInserted(0, dataList02.getRowCount()-1);
                    distIndex--;
                }
            }
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    private void confirmLineSearch()
    {
        try
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            ConfirmLineFrame lineSearchDlg = new ConfirmLineFrame( this.desktop , app, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00073")/*결재선*/, getConfJTable(), this.agreeRadio);

            //shin....결재선에서 OK 눌렀을때...
            if( lineSearchDlg.getOK() == true )
            {
                this.agreeRadio = lineSearchDlg.getRadioData();
                dataList01 = new ConfirmBomTableData(lineSearchDlg.getResultSetData());

                // 최종 검토자의 "구분" 값을 "승인"으로 변경해줌
                ConfirmBomData firstRowData = (ConfirmBomData)dataList01.vecWorkerData.elementAt(0);
                firstRowData.gubuns = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00250")/*승인*/;

                tblList.removeAll();
                tblList.setModel(dataList01);
                tblList.repaint();
                tblList.updateUI();
            }

            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch (Throwable ex)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
        }
    }

    private void distributionSearch()
    {
        try
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            DistributionFrame distriSearchDlg = new DistributionFrame( this.desktop , app, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00156")/*배포처*/, getConfJTable(), getDistJTable());

            if( distriSearchDlg.getOK() == true )
            {
                dataList01 = new ConfirmBomTableData(distriSearchDlg.getResultSetData());
                tblList02.removeAll();
                tblList02.setModel(dataList01);
                tblList02.repaint();
            }

            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch (Throwable ex)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
        }
    }

    public JTable getConfJTable()
    {
        JTable jt =  this.tblList;
        return jt;
    }

    public JTable getDistJTable()
    {
        JTable jt =  this.tblList02;
        return jt;
    }

    private void disposeScreen()
    {
        int count = getComponentCount();

        for( int i = 0; i < count; i++ )
        {
              Component c = getComponent(i);
              this.remove(c);
              c = null;
        }

        super.dispose();
        System.gc();
      }

    public void lostOwnership(Clipboard clip, Transferable transferable)
    {

    }

    private KETWfmApprovalMaster getSaveBtn_process()
    {
        String itemCode = "";
        String discussType = this.agreeRadio;
        String commentStr = comment.getText();

        boolean startFlag = false;  //이미 결재시작 된 경우 true. 아니면 false.

        String beforeReviewStr = "";
        String discussStr = "";
        String afterReviewStr = "";
        String receiverStr = "";
        String referenceStr = "";

        boolean saveResult = false;

        KETBomHeader header = null;
        KETWfmApprovalMaster master = null;

        try
        {
            BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
            BOMTreeTableModel model = (BOMTreeTableModel)bomPanel.getTreeTableModel();
            BOMTreeNode rootNode = (BOMTreeNode)model.getRootNode();

            itemCode = rootNode.getBOMComponent().getItemCodeStr();

            KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
            header = query.getBOMHeader( itemCode );

            int tableRowCnt = tblList.getRowCount();

//            for(int i=0;i<tableRowCnt;i++)
            for( int i = (tableRowCnt -1); i >= 0; i-- )
            {
                if( ((String)tblList.getValueAt(i, 6)).equals("A") )
                {
                    beforeReviewStr = beforeReviewStr + "," +(String)tblList.getValueAt(i, 5);
                }
                else if( ((String)tblList.getValueAt(i, 6)).equals("B") )
                {
                    discussStr = discussStr + "," +(String)tblList.getValueAt(i, 5);
                }
                else if( ((String)tblList.getValueAt(i, 6)).equals("C") )
                {
                    afterReviewStr = afterReviewStr + "," +(String)tblList.getValueAt(i, 5);
                }
            }

            int tableRowCnt2 = tblList02.getRowCount();

//            for(int i=0;i<tableRowCnt2;i++)
            for(int i = (tableRowCnt2 - 1); i >= 0; i-- )
            {
                if( ((String)tblList02.getValueAt(i, 6)).equals("AA") )
                {
                    receiverStr = receiverStr + "," +(String)tblList02.getValueAt(i, 5);
                }
                else if( ((String)tblList02.getValueAt(i, 6)).equals("BB") )
                {
                    referenceStr = referenceStr + "," +(String)tblList02.getValueAt(i, 5);
                }
            }

            String[] beforeReview = {};
            String[] discuss = {};
            String[] afterReview = {};
            String[] receiver = {};
            String[] reference = {};

            try
            {
                beforeReview = (beforeReviewStr.substring(1,beforeReviewStr.length())).split(",");
            }
            catch(Exception e){}

            try
            {
                discuss = (discussStr.substring(1,discussStr.length())).split(",");
            }
            catch(Exception e){}

            try
            {
                afterReview = (afterReviewStr.substring(1,afterReviewStr.length())).split(",");
            }
            catch(Exception e){}

            try
            {
                receiver = (receiverStr.substring(1,receiverStr.length())).split(",");
            }
            catch(Exception e){}

            try
            {
                reference = (referenceStr.substring(1,referenceStr.length())).split(",");
            }
            catch(Exception e){}

            try
            {
                WTPrincipalReference creatorID = SessionHelper.manager.getPrincipalReference();

                Hashtable hash = new Hashtable();
                hash.put("agreementType", discussType);
                hash.put("creator", creatorID);
                hash.put("comment", commentStr);
                hash.put("pbo", header);
                hash.put("startFlag", startFlag);

                bomHeader = header;

                master = (KETWfmApprovalMaster)KETBomHelper.service.createMaster( hash );

                if( beforeReview.length> 0 )
                    saveApprovalLine("beforeReview", beforeReview, master);

                if( discuss.length > 0 )
                    saveApprovalLine("discuss", discuss, master);

                if( afterReview.length > 0 )
                    saveApprovalLine("afterReview", afterReview, master);

                if( receiver.length > 0 )
                    saveApprovalLine("receiver", receiver, master);

                if( reference.length > 0 )
                    saveApprovalLine("reference", reference, master);

                saveResult = true;
            }
            catch( WTException e )
            {
                saveResult = false;
                Kogger.error(getClass(), e);
            }
        }
        catch(Exception ex)
        {
            saveResult = false;
            Kogger.error(getClass(), ex);
        }

        return master;
    }

    private KETWfmApprovalMaster getUpdateBtn_process()
    {
        String discussType = this.agreeRadio;
        String commentStr = comment.getText();
//        boolean startFlag = false;  //이미 결재시작 된 경우 true. 아니면 false.

        String beforeReviewStr = "";
        String discussStr = "";
        String afterReviewStr = "";
        String receiverStr = "";
        String referenceStr = "";
        boolean updateResult = false;
        KETWfmApprovalMaster master = null;

        try
        {
            int tableRowCnt = tblList.getRowCount();

//            for( int i = 0; i < tableRowCnt; i++ )
            for( int i = (tableRowCnt -1); i >= 0; i-- )
            {
                if( ((String)tblList.getValueAt(i, 6)).equals("A") ) // 합의전 검토
                {
                    beforeReviewStr = beforeReviewStr + "," +(String)tblList.getValueAt(i, 5);
                }
                else if( ((String)tblList.getValueAt(i, 6)).equals("B") ) // 합의
                {
                    discussStr = discussStr + "," +(String)tblList.getValueAt(i, 5);
                }
                else if( ((String)tblList.getValueAt(i, 6)).equals("C") ) // 합의후 검토
                {
                    afterReviewStr = afterReviewStr + "," +(String)tblList.getValueAt(i, 5);
                }
            }

            int tableRowCnt2 = tblList02.getRowCount();

//            for( int i = 0; i < tableRowCnt2; i++ )
            for( int i = (tableRowCnt2 -1); i >= 0; i-- )
            {
                if( ((String)tblList02.getValueAt(i, 6)).equals("AA") ) // 수신자
                {
                    receiverStr = receiverStr + "," +(String)tblList02.getValueAt(i, 5);
                }
                else if( ((String)tblList02.getValueAt(i, 6)).equals("BB") ) // 참조자
                {
                    referenceStr = referenceStr + "," +(String)tblList02.getValueAt(i, 5);
                }
            }

            String[] beforeReview = {};
            String[] discuss = {};
            String[] afterReview = {};
            String[] receiver = {};
            String[] reference = {};

            try
            {
                beforeReview = (beforeReviewStr.substring(1,beforeReviewStr.length())).split(",");
            }
            catch(Exception e){}

            try
            {
                discuss = (discussStr.substring(1,discussStr.length())).split(",");
            }
            catch(Exception e){}

            try
            {
                afterReview = (afterReviewStr.substring(1,afterReviewStr.length())).split(",");
            }
            catch(Exception e){}

            try
            {
                receiver = (receiverStr.substring(1,receiverStr.length())).split(",");
            }
            catch(Exception e){}

            try
            {
                reference = (referenceStr.substring(1,referenceStr.length())).split(",");
            }
            catch(Exception e){}

            if( bomHeader != null )
            {
                master = WorkflowSearchHelper.manager.getMaster(bomHeader);

                try
                {
                    KETBomHelper.service.updateMaster( CommonUtil.getOIDString(master), discussType, commentStr );

                    if( beforeReview.length > 0 )
                        saveApprovalLine("beforeReview", beforeReview, master);

                    if( discuss.length > 0 )
                        saveApprovalLine("discuss", discuss, master);

                    if( afterReview.length > 0 )
                        saveApprovalLine("afterReview", afterReview, master);

                    if( receiver.length > 0 )
                        saveApprovalLine("receiver", receiver, master);

                    if( reference.length > 0 )
                        saveApprovalLine("reference", reference, master);

                    updateResult = true;
                }
                catch( WTException e )
                {
                    updateResult = false;
                    Kogger.error(getClass(), e);
                }
            }
        }
        catch(Exception ex)
        {
            updateResult = false;
            Kogger.error(getClass(), ex);
        }

        return master;
    }

    protected void saveApprovalLine(String type, String[] target, Persistable obj)
    {
        try
        {
            for( int i = 0; i < target.length; i++ )
            {
                if( !target[i].equals("") )
                {
                    Hashtable hash = new Hashtable();
                    hash.put("order", i);
                    hash.put("type", type);
                    hash.put("userID", target[i]);
                    hash.put("master", obj);

                    KETBomHelper.service.createLine( hash );
                }
            }
        }
        catch( Exception e )
        {
            Kogger.error(getClass(), e);
        }
    }

    private boolean startProcess()
    {
        boolean flag = false;
        String oid = "";

        KETWfmApprovalMaster master = null;

        if( tblList.getRowCount() == 0 )
        {
            MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00076")/*결재선을 지정해주십시오.*/, "Error", MessageBox.ERROR);
            m.setModal(true);
            m.setVisible(true);

            return false;
        }

        try
        {
            if( bomHeader != null )
            {
                master = WorkflowSearchHelper.manager.getMaster(bomHeader);
                oid = CommonUtil.getOIDString(bomHeader);

                if( master != null )
                {
                    if( master.isStartFlag() )
                        master = this.getUpdateBtn_process();

                    flag = KETBomHelper.service.startProcess( oid, master );
                }
                else
                {
                    master = this.getSaveBtn_process();
                    flag = KETBomHelper.service.startProcess( oid, master );
                }
            }
        }
        catch( WTRuntimeException e )
        {
            MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00070")/*결재 요청중 오류가 발생했습니다.*/, "Select Error", 0);
            messagebox.setModal(true);
            messagebox.setVisible(true);

            Kogger.error(getClass(), e);
        }
        catch( WTException e )
        {
            MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00070")/*결재 요청중 오류가 발생했습니다.*/, "Select Error", 0);
            messagebox.setModal(true);
            messagebox.setVisible(true);

            Kogger.error(getClass(), e);
        }

        return flag;
    }
}
