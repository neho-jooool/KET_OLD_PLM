package e3ps.bom.command.confirmbom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreePath;

import wt.fc.QueryResult;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMConfTreeRenderer;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.ConfirmTreeSearchLoader;
import e3ps.bom.common.jtreetable.ConfirmTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.ValidationChecker;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.CompanyState;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.People;
import ext.ket.shared.log.Kogger;

public class DistributionFrame extends AbstractAIFDialog implements ClipboardOwner
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private BtnListener btnListener;
    JTable tblList01 = new JTable();

    JTable jtblList01 = new JTable();
    JTable jtblList02 = new JTable();

    private ConfirmLineSchData dataList;
    private DistributionBomData01 dataList01;
    private DistributionBomData02 dataList02;

    private Vector resultList = new Vector();
    Registry appReg;
    boolean keyCheck = false;

    private AbstractAIFUIApplication app;
    private JFrame desktop;

    private JLabel searchResultLbl;
    private JButton searchBtn, searchBtn02, clearBtn;
    private JButton okBtn, closeBtn;
    private JButton addAfterBtn01, addAfterBtn02, deleteLineBtn, allDeleteBtn;
    private JButton moveUpBtn01, moveDownBtn01, moveUpBtn02, moveDownBtn02;

    String itemCodeStr = "";
    String titleStr = "";
    private boolean isOK = false;
    private int intRowCount = -1;
    private String aryResult [] = new String[8];

    Vector resultVector = new Vector();
    Vector vecModel = new Vector();
    Vector vecModel01 = new Vector();
    Vector vecModel02 = new Vector();

    private Clipboard clipboard;

    //shin....
    private JTextField cNameTfl = null;

    private JTreeTable treeTable;
    private BOMAssyComponent rootComponent;
    private BOMAssyComponent    assyComponent;
    private BOMAssyComponent    subComponent;
    private BOMAssyComponent    peopleComponent;
    private ConfirmTreeTableModel model;

    private JTabbedPane tPane;
    private int jtabSelectNo= 0;

    public Vector bomDataVec;
    private JTable confTable ;
    private JTable distTable ;

    class BtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("Search"))
            {
                searchBtn_process();
            }
            else if (e.getActionCommand().equals("OK"))
            {
                okBtn_process();
            }
            else if (e.getActionCommand().equals("Close"))
            {
                disposeScreen();
            }
        }
    }

    public DistributionFrame(JFrame frame, AbstractAIFUIApplication app, String title, JTable jt, JTable djt)
    {
        super(frame, title , true);
        this.desktop = frame;
        this.app = app;
        itemCodeStr = "";
        titleStr = title;
        appReg = Registry.getRegistry(app);
        confTable = jt;
        distTable = djt;
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                disposeScreen();
            }
        });

        try
        {
            dataList = new ConfirmLineSchData(vecModel);
            dataList01 = new DistributionBomData01();
            dataList02 = new DistributionBomData02();

            jInit();
            setConfTable(distTable);
            setSize(780,680);
            setResizable(false);

            ScreenCenterer scent = new ScreenCenterer();
            Dimension dimCenter = new Dimension(scent.getCenterDim(this));
            setLocation(dimCenter.width, dimCenter.height);
            setVisible(true);
            //itemCodeTfl.requestFocus();
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    private void jInit() throws Exception
    {
        try
        {
            //Left Start ..................................................................................................................................

            TableColumnModel columnModel01 = null;
            TableColumnModel columnModel02 = null;
            btnListener = new BtnListener();

            JPanel Lpan = new JPanel();
            JPanel Rpan = new JPanel();

            //this.getContentPane().add(tPane, BorderLayout.CENTER);

            // Search Condition Panel /////////////////////////////////////////////////
            JPanel schPanel = new JPanel();
            JPanel orgPanel = new JPanel();
            JPanel allLPanel = new JPanel();

            schPanel.setLayout(new BoxLayout(schPanel, BoxLayout.Y_AXIS));
            schPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00209")/*사용자 이름검색*/, 0, 0, FontList.defaultFont));
            schPanel.setFont(FontList.defaultFont);
            schPanel.setPreferredSize(new Dimension(350, 600));

            JPanel userSchPanel = new JPanel();
            JPanel nmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            cNameTfl = new JTextField("", 18);
            cNameTfl.addFocusListener(new FocusAdapter()
            {
                public void focusGained(FocusEvent e)
                {
                    InputContext ctx = getInputContext();
                    Character.Subset[] subset = {Character.UnicodeBlock.HANGUL_SYLLABLES};
                    ctx.setCharacterSubsets(subset);
                }
            });
            cNameTfl.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    searchBtn_process();
                }
            });
            cNameTfl.setFont(FontList.defaultFont);
            nmPanel.add(cNameTfl);

            // Search / Clear 버튼 라인
            JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            searchBtn = new JButton("Search", appReg.getImageIcon("searchitemIcon"));
            searchBtn.setFont(FontList.defaultFont);
            searchBtn.setActionCommand("Search");
            searchBtn.addActionListener(btnListener);
            searchBtn.setMargin(new Insets(0,5,0,5));
            panel02.add(searchBtn);

            userSchPanel.add(nmPanel,BorderLayout.WEST);
            userSchPanel.add(panel02,BorderLayout.EAST);
            schPanel.add(userSchPanel);

            // Search Result Panel /////////////////////////////////////////////////
            JPanel centerPanel = new JPanel(new BorderLayout());

            tblList01.setAutoCreateColumnsFromModel(false);
            tblList01.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tblList01.setModel(dataList);
            tblList01.repaint();

            for( int k = 0; k < ConfirmLineSchData.clmModelData.length; k++ )
            {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(ConfirmLineSchData.clmModelData[k].intAlignment);
                TableColumn column = new TableColumn(k, ConfirmLineSchData.clmModelData[k].intWidth, renderer, null);
                tblList01.addColumn(column);

                columnModel01 = tblList01.getColumnModel();
                TableColumn _column = columnModel01.getColumn(k);
                _column.setCellRenderer(renderer);
            }

            JTableHeader headerModel = tblList01.getTableHeader();
            headerModel.setUpdateTableInRealTime(false);

            JScrollPane ps = new JScrollPane();
            ps.getViewport().add(tblList01);

            columnModel01 = tblList01.getColumnModel();

            //shin...테이블에서 숨김.
            TableColumn column = columnModel01.getColumn(0);
            column.setWidth(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setResizable(false);

            column = columnModel01.getColumn(4);
            column.setWidth(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setResizable(false);

            centerPanel.add(ps, BorderLayout.CENTER);
            schPanel.add(centerPanel);

            //-------------------------------------------------------------------------------------------------------

            //------------------------------------------------------------------------------------------------------- 조직도 Start
            orgPanel.setLayout(new BoxLayout(orgPanel, BoxLayout.Y_AXIS));
            orgPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), "", 0, 0, FontList.defaultFont));
            orgPanel.setFont(FontList.defaultFont);

            Vector subAssyVec = new Vector();
            subAssyVec.removeAllElements();

            rootComponent = new BOMAssyComponent(String.valueOf(CompanyState.companyName), true);
            rootComponent.setOrgViewStr(CompanyState.companyName);
            rootComponent.setUserDeptStr("");
            rootComponent.setUserNmStr("");
            rootComponent.setUserDutyStr("");
            rootComponent.setUserIdStr("");
            rootComponent.setUserEmailStr("");
            rootComponent.setComponentTypeStr("1");

            Department underDepartment = null;
            ArrayList topList = DepartmentHelper.manager.getTopList();
            for( int i = 0 ; i < topList.size() ; i++ )
            {
                underDepartment = (Department)topList.get(i);
                //long topnodeid = CommonUtil.getOIDLongValue(topDepartment);

                assyComponent = new BOMAssyComponent(underDepartment.getName());
                assyComponent.setOrgViewStr(underDepartment.getName());
                assyComponent.setUserDeptStr(underDepartment.getName());
                assyComponent.setUserNmStr("");
                assyComponent.setUserDutyStr("");
                assyComponent.setUserIdStr("");
                assyComponent.setUserEmailStr("");
                assyComponent.setLevelInt(1);
                assyComponent.setNewFlagStr("WHITE");
                subAssyVec.addElement(assyComponent);

                ArrayList underList = DepartmentHelper.manager.getAllChildList(underDepartment, new ArrayList());

                if( underList.size() > 0 )
                {
                    Hashtable hts = new Hashtable();

                    for( int j = 0 ; j < underList.size() ; j++ )
                    {
                        int lev = 0;
                        underDepartment = (Department)underList.get(j);
                        Department parentDepartment = (Department)underDepartment.getParent();

                        underDepartment.getSort();
                        long undernodeid = CommonUtil.getOIDLongValue(underDepartment);
                        long parentnodeid = CommonUtil.getOIDLongValue(parentDepartment);
                        String lv = StringUtil.checkNull((String)hts.get(parentnodeid));

                        if( lv.equals("") )
                        {
                            lev = 2;
                        }
                        else
                        {
                            lev = new Integer(lv)+1;
                        }

                        hts.put(undernodeid, String.valueOf(lev));

                        subComponent = new BOMAssyComponent(underDepartment.getName());
                        subComponent.setOrgViewStr(underDepartment.getName());
                        subComponent.setUserDeptStr(underDepartment.getName());
                        subComponent.setUserNmStr("");
                        subComponent.setUserDutyStr("");
                        subComponent.setUserIdStr("");
                        subComponent.setUserEmailStr("");
                        subComponent.setLevelInt(lev);
                        subComponent.setNewFlagStr("WHITE");
                        subComponent.setSupplyTypeStr("Dept");
                        subAssyVec.addElement(subComponent);

                        QueryResult qr = DepartmentHelper.manager.getDepartmentPeople(underDepartment);
                        People pdata = new People();

                        while( qr.hasMoreElements() )
                        {
                            Object[] data = (Object[])qr.nextElement();
                            pdata = (People)data[0];

                            if( pdata.getDepartment().equals(underDepartment) )
                            {
                                peopleComponent = new BOMAssyComponent(pdata.getName());
                                peopleComponent.setOrgViewStr(pdata.getName());
                                peopleComponent.setUserDeptStr(underDepartment.getName());
                                peopleComponent.setUserNmStr(pdata.getName());
                                peopleComponent.setUserDutyStr(pdata.getDuty());
                                peopleComponent.setUserIdStr(pdata.getId());
                                peopleComponent.setUserEmailStr(pdata.getEmail());
                                peopleComponent.setLevelInt((lev+1));
                                peopleComponent.setNewFlagStr("WHITE");
                                peopleComponent.setSupplyTypeStr("People");
                                subAssyVec.addElement(peopleComponent);
                            }
                        }
                    }
                }
                else
                {
                    QueryResult qr = DepartmentHelper.manager.getDepartmentPeople(underDepartment);
                    People pdata = new People();

                    while( qr.hasMoreElements() )
                    {
                        Object[] data = (Object[])qr.nextElement();
                        pdata = (People)data[0];
                        peopleComponent = new BOMAssyComponent(pdata.getName());
                        peopleComponent = new BOMAssyComponent(pdata.getName());
                        peopleComponent.setOrgViewStr(pdata.getName());
                        peopleComponent.setUserDeptStr(underDepartment.getName());
                        peopleComponent.setUserNmStr(pdata.getName());
                        peopleComponent.setUserDutyStr(pdata.getDuty());
                        peopleComponent.setUserIdStr(pdata.getId());
                        peopleComponent.setUserEmailStr(pdata.getEmail());
                        peopleComponent.setLevelInt(2);
                        peopleComponent.setNewFlagStr("WHITE");
                        peopleComponent.setSupplyTypeStr("People");
                        subAssyVec.addElement(peopleComponent);
                    }
                }
            }

            model = new ConfirmTreeTableModel(rootComponent);
            treeTable = new JTreeTable(model);
            treeTable.getTree().setCellRenderer(new BOMConfTreeRenderer(app));

            treeTable.setShowGrid(false);

            openBOMData(rootComponent, subAssyVec);

            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00321")/*조직도*/).setPreferredWidth(290);
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/).setPreferredWidth(20);
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/).setPreferredWidth(20);
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/).setPreferredWidth(20);
            treeTable.getColumn("ID").setPreferredWidth(20);
            treeTable.getColumn("EMAIL").setPreferredWidth(20);

            TableColumnModel columnModel21 = treeTable.getColumnModel();
            TableColumn column21 = columnModel21.getColumn(1);
            column21.setWidth(0);
            column21.setMinWidth(0);
            column21.setMaxWidth(0);
            column21.setResizable(false);

            column21 = columnModel21.getColumn(2);
            column21.setWidth(0);
            column21.setMinWidth(0);
            column21.setMaxWidth(0);
            column21.setResizable(false);

            column21 = columnModel21.getColumn(3);
            column21.setWidth(0);
            column21.setMinWidth(0);
            column21.setMaxWidth(0);
            column21.setResizable(false);

            column21 = columnModel21.getColumn(4);
            column21.setWidth(0);
            column21.setMinWidth(0);
            column21.setMaxWidth(0);
            column21.setResizable(false);

            column21 = columnModel21.getColumn(5);
            column21.setWidth(0);
            column21.setMinWidth(0);
            column21.setMaxWidth(0);
            column21.setResizable(false);

            column21 = columnModel21.getColumn(6);
            column21.setWidth(0);
            column21.setMinWidth(0);
            column21.setMaxWidth(0);
            column21.setResizable(false);

            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.getViewport().add(treeTable);
            jscrollpane.getViewport().setBackground(Color.white);
            jscrollpane.setBackground(Color.white);
            jscrollpane.setPreferredSize(new Dimension(340,100));
            orgPanel.add(jscrollpane);
            orgPanel.setBackground(Color.white);
            orgPanel.setBackground(new Color(240,240,240));
            //----------------------------------------------------------------- 조직도 End

            schPanel.setPreferredSize( new Dimension( 300, 550 ) );
            orgPanel.setPreferredSize( new Dimension( 300, 550 ) );

            Lpan.add(schPanel);
            Rpan.add(orgPanel);

            JTabbedPane tPane = new JTabbedPane();
            tPane.addTab(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00053")/*검색*/,Lpan);
            tPane.addTab(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00321")/*조직도*/,Rpan);

            tPane.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent e)
                {
                    JTabbedPane tab = (JTabbedPane)e.getSource();
                    jtabSelectNo = tab.getSelectedIndex();

                    if( jtabSelectNo == 0 )
                    {
                        treeTable.clearSelection();
                    }
                    else
                    {
                        tblList01.clearSelection();
                    }
                }
            });

            allLPanel.add(tPane);
            this.getContentPane().add(allLPanel, BorderLayout.WEST);

            //Left End...................................................................................................................................

            //Center Start.......................................................................................................................................................
            JPanel spaceupPanel = new JPanel();
            JPanel spacecenterPanel = new JPanel();

            spaceupPanel.setPreferredSize(new Dimension(180, 100));
            spacecenterPanel.setPreferredSize(new Dimension(180, 20));

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            addAfterBtn01 = new JButton("   " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00237")/*수신자*/ + "   ", appReg.getImageIcon("addconfirmuserIcon"));
            addAfterBtn01.setMargin(new Insets(0, 5, 0, 5));
            addAfterBtn01.setFont(FontList.defaultFont);
            addAfterBtn01.setActionCommand("AddAfter");
            addAfterBtn01.addActionListener(btnListener);
            addAfterBtn01.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent actionevent)
                {
                    int confirmRowCnt = confTable.getRowCount();

                    if( jtabSelectNo == 0 )
                    {
                        if( tblList01.getSelectedRow() == -1 )
                        {
                            MessageBox messagebox = new MessageBox(desktop, messageRegistry.getString("selectRow12"), "Select Error", 0);
                            messagebox.setModal(true);
                            messagebox.setVisible(true);
                            return;
                        }
                        else
                        {
                            intRowCount = tblList01.getSelectedRow();

                            int tableRowCnt02 = jtblList02.getRowCount();
                            for( int i = 0; i < tableRowCnt02; i++ )
                            {
                                if( ((String)jtblList02.getValueAt(i, 5)).equals((String)tblList01.getValueAt(intRowCount, 4)) )
                                {
                                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00329")/*참조자에 이미 지정된 사용자가 있습니다.*/, "Select Error", 0);
                                    messagebox.setModal(true);
                                    messagebox.setVisible(true);
                                    return;
                                }
                            }

                            for( int i = 0; i < confirmRowCnt; i++ )
                            {
                                if( ((String)confTable.getValueAt(i, 5)).equals((String)tblList01.getValueAt(intRowCount, 4)) )
                                {
                                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00075")/*결재선에 이미 지정된 사용자가 있습니다.*/, "Select Error", 0);
                                    messagebox.setModal(true);
                                    messagebox.setVisible(true);
                                    return;
                                }
                            }

                            int tableRowCnt = jtblList01.getRowCount();
                            for( int i = 0; i < tableRowCnt; i++ )
                            {
                                if( ((String)jtblList01.getValueAt(i, 5)).equals((String)tblList01.getValueAt(intRowCount, 4)) )
                                {
                                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00238")/*수신자에 동일한 사용자가 있습니다.*/, "Select Error", 0);
                                    messagebox.setModal(true);
                                    messagebox.setVisible(true);
                                    return;
                                }
                            }
                            dataList01.vecWorkerData.add(tableRowCnt,new ConfirmBomData(
                                    KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00236")/*수신*/
                                ,(String)tblList01.getValueAt(intRowCount, 1)
                                ,(String)tblList01.getValueAt(intRowCount, 2)
                                ,(String)tblList01.getValueAt(intRowCount, 4)
                                ,(String)tblList01.getValueAt(intRowCount, 3)
                                ,"AA"
                                ,String.valueOf(tableRowCnt+1)
                            ));
                            dataList01.fireTableRowsInserted(0, dataList01.getRowCount()-1);
                            tblList01.clearSelection();
                        }
                    }
                    else
                    {
                        if( treeTable.getSelectedRow() == -1 )
                        {
                            MessageBox messagebox = new MessageBox(desktop, messageRegistry.getString("selectRow12"), "Select Error", 0);
                            messagebox.setModal(true);
                            messagebox.setVisible(true);
                            return;
                        }
                        else
                        {
                            TreePath [] selectedPath = treeTable.getTree().getSelectionPaths();

                            for( int inx = 0; inx < selectedPath.length; inx++ )
                            {
                                BOMTreeNode tempnode1 = (BOMTreeNode)selectedPath[inx].getLastPathComponent();
                                BOMAssyComponent assy1 = tempnode1.getBOMComponent();

                                if( (!(assy1.getUserIdStr()).equals("")) && (assy1.getUserIdStr()) != null )
                                {
                                    int tableRowCnt02 = jtblList02.getRowCount();
                                    for( int i = 0; i < tableRowCnt02; i++ )
                                    {
                                        if( ((String)jtblList02.getValueAt(i, 5)).equals(assy1.getUserIdStr()) )
                                        {
                                            MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00329")/*참조자에 이미 지정된 사용자가 있습니다.*/, "Select Error", 0);
                                            messagebox.setModal(true);
                                            messagebox.setVisible(true);
                                            return;
                                        }
                                    }

                                    for( int i = 0; i < confirmRowCnt; i++ )
                                    {
                                        if( ((String)confTable.getValueAt(i, 5)).equals(assy1.getUserIdStr()) )
                                        {
                                            MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00075")/*결재선에 이미 지정된 사용자가 있습니다.*/, "Select Error", 0);
                                            messagebox.setModal(true);
                                            messagebox.setVisible(true);
                                            return;
                                        }
                                    }

                                    int tableRowCnt = jtblList01.getRowCount();
                                    for( int i = 0; i < tableRowCnt; i++ )
                                    {
                                        if( ((String)jtblList01.getValueAt(i, 5)).equals(assy1.getUserIdStr()) )
                                        {
                                            MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00238")/*수신자에 동일한 사용자가 있습니다.*/, "Select Error", 0);
                                            messagebox.setModal(true);
                                            messagebox.setVisible(true);
                                            return;
                                        }
                                    }
                                    dataList01.vecWorkerData.add(tableRowCnt,new ConfirmBomData(
                                            KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00236")/*수신*/ //구분
                                        ,assy1.getUserDeptStr()
                                        ,assy1.getUserDutyStr()
                                        ,assy1.getUserIdStr()
                                        ,assy1.getUserNmStr()
                                        ,"AA"
                                        ,String.valueOf(tableRowCnt+1)
                                    ));
                                    dataList01.fireTableRowsInserted(0, dataList01.getRowCount()-1);
                                }
                            }

                            treeTable.clearSelection();
                        }
                    }
                }
            });

            addAfterBtn02 = new JButton("   " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00327")/*참조자*/ + "   ", appReg.getImageIcon("addconfirmuserIcon"));
            addAfterBtn02.setMargin(new Insets(0, 5, 0, 5));
            addAfterBtn02.setFont(FontList.defaultFont);
            addAfterBtn02.setActionCommand("AddAfter");
            addAfterBtn02.addActionListener(btnListener);
            addAfterBtn02.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent actionevent)
                {
                    int confirmRowCnt = confTable.getRowCount();

                    if( jtabSelectNo == 0 )
                    {
                        if( tblList01.getSelectedRow() == -1 )
                        {
                            MessageBox messagebox = new MessageBox(desktop, messageRegistry.getString("selectRow12"), "Select Error", 0);
                            messagebox.setModal(true);
                            messagebox.setVisible(true);
                            return;
                        }
                        else
                        {
                            intRowCount = tblList01.getSelectedRow();

                            int tableRowCnt01 = jtblList01.getRowCount();
                            for( int i = 0; i < tableRowCnt01; i++ )
                            {
                                if( ((String)jtblList01.getValueAt(i, 5)).equals((String)tblList01.getValueAt(intRowCount, 4)) )
                                {
                                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00239")/*수신자에 이미 지정된 사용자가 있습니다.*/, "Select Error", 0);
                                    messagebox.setModal(true);
                                    messagebox.setVisible(true);
                                    return;
                                }
                            }

                            for( int i = 0; i < confirmRowCnt; i++ )
                            {
                                if( ((String)confTable.getValueAt(i, 5)).equals((String)tblList01.getValueAt(intRowCount, 4)) )
                                {
                                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00075")/*결재선에 이미 지정된 사용자가 있습니다.*/, "Select Error", 0);
                                    messagebox.setModal(true);
                                    messagebox.setVisible(true);
                                    return;
                                }
                            }

                            int tableRowCnt = jtblList02.getRowCount();
                            for( int i = 0; i < tableRowCnt; i++ )
                            {
                                if( ((String)jtblList02.getValueAt(i, 5)).equals((String)tblList01.getValueAt(intRowCount, 4)) )
                                {
                                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00328")/*참조자에 동일한 사용자가 있습니다.*/, "Select Error", 0);
                                    messagebox.setModal(true);
                                    messagebox.setVisible(true);
                                    return;
                                }
                            }
                            dataList02.vecWorkerData.add(tableRowCnt,new ConfirmBomData(
                                    KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00326")/*참조*/
                                ,(String)tblList01.getValueAt(intRowCount, 1)
                                ,(String)tblList01.getValueAt(intRowCount, 2)
                                ,(String)tblList01.getValueAt(intRowCount, 4)
                                ,(String)tblList01.getValueAt(intRowCount, 3)
                                ,"BB"
                                ,String.valueOf(tableRowCnt+1)
                            ));
                            dataList02.fireTableRowsInserted(0, dataList02.getRowCount()-1);
                            tblList01.clearSelection();
                        }
                    }
                    else
                    {
                        if( treeTable.getSelectedRow() == -1 )
                        {
                            MessageBox messagebox = new MessageBox(desktop, messageRegistry.getString("selectRow12"), "Select Error", 0);
                            messagebox.setModal(true);
                            messagebox.setVisible(true);
                            return;
                        }
                        else
                        {
                            TreePath [] selectedPath = treeTable.getTree().getSelectionPaths();

                            for( int inx = 0; inx < selectedPath.length; inx++ )
                            {
                                BOMTreeNode tempnode1 = (BOMTreeNode)selectedPath[inx].getLastPathComponent();
                                BOMAssyComponent assy1 = tempnode1.getBOMComponent();

                                if( (!(assy1.getUserIdStr()).equals("")) && (assy1.getUserIdStr()) != null )
                                {
                                    int tableRowCnt01 = jtblList01.getRowCount();
                                    for( int i = 0; i < tableRowCnt01; i++ )
                                    {
                                        if( ((String)jtblList01.getValueAt(i, 5)).equals(assy1.getUserIdStr()) )
                                        {
                                            MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00239")/*수신자에 이미 지정된 사용자가 있습니다.*/, "Select Error", 0);
                                            messagebox.setModal(true);
                                            messagebox.setVisible(true);
                                            return;
                                        }
                                    }

                                    for( int i = 0; i < confirmRowCnt; i++ )
                                    {
                                        if( ((String)confTable.getValueAt(i, 5)).equals(assy1.getUserIdStr()) )
                                        {
                                            MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00075")/*결재선에 이미 지정된 사용자가 있습니다.*/, "Select Error", 0);
                                            messagebox.setModal(true);
                                            messagebox.setVisible(true);
                                            return;
                                        }
                                    }

                                    int tableRowCnt = jtblList02.getRowCount();
                                    for( int i = 0; i < tableRowCnt; i++ )
                                    {
                                        if( ((String)jtblList02.getValueAt(i, 5)).equals(assy1.getUserIdStr()) )
                                        {
                                            MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00328")/*참조자에 동일한 사용자가 있습니다.*/, "Select Error", 0);
                                            messagebox.setModal(true);
                                            messagebox.setVisible(true);
                                            return;
                                        }
                                    }
                                    dataList02.vecWorkerData.add(tableRowCnt,new ConfirmBomData(
                                            KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00326")/*참조*/ //구분
                                        ,assy1.getUserDeptStr()
                                        ,assy1.getUserDutyStr()
                                        ,assy1.getUserIdStr()
                                        ,assy1.getUserNmStr()
                                        ,"BB"
                                        ,String.valueOf(tableRowCnt+1)
                                    ));
                                    dataList02.fireTableRowsInserted(0, dataList02.getRowCount()-1);
                                }
                            }

                            treeTable.clearSelection();
                        }
                    }
                }
            });

            deleteLineBtn  = new JButton("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/ + "      ", appReg.getImageIcon("deleteuserIcon"));
            deleteLineBtn.setMargin(new Insets(0,5,0,5));
            deleteLineBtn.setFont(FontList.defaultFont);
            deleteLineBtn.setActionCommand("AddBefore");
            deleteLineBtn.addActionListener(btnListener);
            deleteLineBtn.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent actionevent)
                {
                    int[] selRows01 = jtblList01.getSelectedRows();
                    for( int i = 0; i < selRows01.length; i++ )
                    {
                        dataList01.vecWorkerData.removeElementAt( selRows01[i] - i );
                    }
                    dataList01.fireTableRowsDeleted(0, dataList01.getRowCount());
                    jtblList01.clearSelection();

                    int[] selRows02 = jtblList02.getSelectedRows();
                    for( int j = 0; j < selRows02.length; j++ )
                    {
                        dataList02.vecWorkerData.removeElementAt( selRows02[j] - j );
                    }
                    dataList02.fireTableRowsDeleted(0, dataList02.getRowCount());
                    jtblList02.clearSelection();
                }
            });

            allDeleteBtn    = new JButton("  " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00148")/*모두삭제*/ + "   ", appReg.getImageIcon("deleteuserallIcon"));
            allDeleteBtn.setMargin(new Insets(0,5,0,5));
            allDeleteBtn.setFont(FontList.defaultFont);
            allDeleteBtn.setActionCommand("Clear");
            allDeleteBtn.addActionListener(btnListener);
            allDeleteBtn.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent actionevent)
                {
                    dataList01 = new DistributionBomData01();
                    jtblList01.setModel(dataList01);
                    jtblList01.repaint();

                    dataList02 = new DistributionBomData02();
                    jtblList02.setModel(dataList02);
                    jtblList02.repaint();
                }
            });

            btnPanel.add(spaceupPanel);
            btnPanel.add(addAfterBtn01);
            btnPanel.add(addAfterBtn02);
            btnPanel.add(spacecenterPanel);
            btnPanel.add(deleteLineBtn);
            btnPanel.add(allDeleteBtn);

            this.getContentPane().add(btnPanel, BorderLayout.CENTER);
            //Center End......................................................................................................................................................

            //Right Start..........................................................................................................................................................shin...
            TableColumnModel reviewModel = null;
            TableColumnModel agreeModel = null;
            TableColumnModel confirmModel = null;

            // Search Condition Panel /////////////////////////////////////////////////
            JPanel reviewPanel = new JPanel();
            JPanel agreePanel = new JPanel();
            JPanel allRPanel = new JPanel();

            JPanel btnpanel01 = new JPanel();
            JPanel udPanel01 = new JPanel();
            JPanel btnpanel02 = new JPanel();
            JPanel udPanel02 = new JPanel();

            reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
            reviewPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), "", 0, 0, FontList.defaultFont));
            reviewPanel.setFont(FontList.defaultFont);
            reviewPanel.setPreferredSize(new Dimension(350, 210));

            moveUpBtn01 = new JButton("", appReg.getImageIcon("moveupIcon"));
            moveUpBtn01.setMargin(new Insets(0,5,0,5));
            moveUpBtn01.setFont(FontList.defaultFont);
            moveUpBtn01.setActionCommand("moveUp");
            moveUpBtn01.addActionListener(btnListener);
            moveUpBtn01.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent actionevent)
                {
                    if( jtblList01.getSelectedRowCount() != 1 )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00348")/*한개의 Row를 선택하세요.*/, "Select Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }

                    if( jtblList01.getSelectedRow() == 0 )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00335")/*최상위 Row는 위로 이동할 수 없습니다.*/, "Input Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }
                    else
                    {
                        intRowCount = jtblList01.getSelectedRow();
                        int tableRowCnt = jtblList01.getRowCount();

                        vecModel01 = dataList01.vecWorkerData;
                        vecModel01.add(intRowCount-1,new ConfirmBomData(
                            String.valueOf(jtblList01.getValueAt(intRowCount, 1))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 2))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 3))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 5))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 4))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 6))
                            ,""
                        ));

                        vecModel01.removeElementAt(intRowCount+1);
                        dataList01 = new DistributionBomData01(vecModel01);
                        jtblList01.setModel(dataList01);
                        jtblList01.repaint();
                        jtblList01.setRowSelectionInterval(intRowCount-1, intRowCount-1);

                        jtblList01.clearSelection();
                    }
                }
            });

            moveDownBtn01 = new JButton("", appReg.getImageIcon("movedownIcon"));
            moveDownBtn01.setMargin(new Insets(0,5,0,5));
            moveDownBtn01.setFont(FontList.defaultFont);
            moveDownBtn01.setActionCommand("moveDown");
            moveDownBtn01.addActionListener(btnListener);
            moveDownBtn01.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent actionevent)
                {
                    int tableRowCnt = jtblList01.getRowCount();
                    if( jtblList01.getSelectedRowCount() != 1 )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00348")/*한개의 Row를 선택하세요.*/, "Select Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }

                    if( jtblList01.getSelectedRow() == tableRowCnt-1 )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00338")/*최하위 Row는 아래로 이동할 수 없습니다.*/, "Input Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }
                    else
                    {
                        intRowCount = jtblList01.getSelectedRow();
                        vecModel01 = dataList01.vecWorkerData;
                        vecModel01.add((intRowCount+2),new ConfirmBomData(
                            String.valueOf(jtblList01.getValueAt(intRowCount, 1))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 2))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 3))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 5))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 4))
                            ,String.valueOf(jtblList01.getValueAt(intRowCount, 6))
                            ,""
                        ));

                        vecModel01.removeElementAt(intRowCount);
                        dataList01 = new DistributionBomData01(vecModel01);
                        jtblList01.setModel(dataList01);
                        jtblList01.repaint();
                        jtblList01.setRowSelectionInterval(intRowCount+1, intRowCount+1);

                        jtblList01.clearSelection();
                    }
                }
            });

            JLabel titleLbl01 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00237")/*수신자*/);
            JLabel blankLbl01 = new JLabel("                                                   ");
            titleLbl01.setFont(FontList.defaultFont);

            btnpanel01.add(moveUpBtn01);
            btnpanel01.add(moveDownBtn01);
            udPanel01.add(titleLbl01,    BorderLayout.WEST);
            udPanel01.add(blankLbl01,  BorderLayout.CENTER);
            udPanel01.add(btnpanel01, BorderLayout.EAST);

            jtblList01.setAutoCreateColumnsFromModel(false);
            jtblList01.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            jtblList01.setModel(dataList01);
            jtblList01.repaint();

            for( int k = 0; k < DistributionBomData01.clmModelData.length; k++ )
            {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(DistributionBomData01.clmModelData[k].intAlignment);
                TableColumn column01 = new TableColumn(k, DistributionBomData01.clmModelData[k].intWidth, renderer, null);
                jtblList01.addColumn(column01);

                reviewModel = jtblList01.getColumnModel();
                TableColumn _column = reviewModel.getColumn(k);
                _column.setCellRenderer(renderer);
            }

            JTableHeader headerModel01 = jtblList01.getTableHeader();
            headerModel01.setUpdateTableInRealTime(false);

            JScrollPane ps01 = new JScrollPane();
            ps01.getViewport().add(jtblList01);

            reviewModel = jtblList01.getColumnModel();
            TableColumn column01 = reviewModel.getColumn(5);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);

            column01 = reviewModel.getColumn(6);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);

            reviewPanel.add(udPanel01);
            reviewPanel.add(ps01);

            //-------------------------------------------------------------------------------------------------------

            agreePanel.setLayout(new BoxLayout(agreePanel, BoxLayout.Y_AXIS));
            agreePanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), "", 0, 0, FontList.defaultFont));
            agreePanel.setFont(FontList.defaultFont);
            agreePanel.setPreferredSize(new Dimension(350, 210));

            moveUpBtn02 = new JButton("", appReg.getImageIcon("moveupIcon"));
            moveUpBtn02.setMargin(new Insets(0,5,0,5));
            moveUpBtn02.setFont(FontList.defaultFont);
            moveUpBtn02.setActionCommand("moveUp");
            moveUpBtn02.addActionListener(btnListener);
            moveUpBtn02.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent actionevent)
                {
                    if( jtblList02.getSelectedRowCount() != 1 )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00348")/*한개의 Row를 선택하세요.*/, "Select Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }
                    if( jtblList02.getSelectedRow() == 0 )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00335")/*최상위 Row는 위로 이동할 수 없습니다.*/, "Input Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }
                    else
                    {
                        intRowCount = jtblList02.getSelectedRow();
                        int tableRowCnt = jtblList02.getRowCount();

                        vecModel02 = dataList02.vecWorkerData;
                        vecModel02.add(intRowCount-1,new ConfirmBomData(
                            String.valueOf(jtblList02.getValueAt(intRowCount, 1))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 2))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 3))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 5))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 4))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 6))
                            ,""
                        ));

                        vecModel02.removeElementAt(intRowCount+1);
                        dataList02 = new DistributionBomData02(vecModel02);
                        jtblList02.setModel(dataList02);
                        jtblList02.repaint();
                        jtblList02.setRowSelectionInterval(intRowCount-1, intRowCount-1);

                        jtblList02.clearSelection();
                    }
                }
            });

            moveDownBtn02 = new JButton("", appReg.getImageIcon("movedownIcon"));
            moveDownBtn02.setMargin(new Insets(0,5,0,5));
            moveDownBtn02.setFont(FontList.defaultFont);
            moveDownBtn02.setActionCommand("moveDown");
            moveDownBtn02.addActionListener(btnListener);
            moveDownBtn02.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent actionevent)
                {
                    int tableRowCnt = jtblList02.getRowCount();
                    if( jtblList02.getSelectedRowCount() != 1 )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00348")/*한개의 Row를 선택하세요.*/, "Select Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }

                    if( jtblList02.getSelectedRow() == tableRowCnt - 1 )
                    {
                        MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00338")/*최하위 Row는 아래로 이동할 수 없습니다.*/, "Input Error", 0);
                        messagebox.setModal(true);
                        messagebox.setVisible(true);
                        return;
                    }
                    else
                    {
                        intRowCount = jtblList02.getSelectedRow();
                        vecModel02 = dataList02.vecWorkerData;
                        vecModel02.add((intRowCount+2),new ConfirmBomData(
                            String.valueOf(jtblList02.getValueAt(intRowCount, 1))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 2))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 3))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 5))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 4))
                            ,String.valueOf(jtblList02.getValueAt(intRowCount, 6))
                            ,""
                        ));

                        vecModel02.removeElementAt(intRowCount);
                        dataList02 = new DistributionBomData02(vecModel02);
                        jtblList02.setModel(dataList02);
                        jtblList02.repaint();
                        jtblList02.setRowSelectionInterval(intRowCount+1, intRowCount+1);

                        jtblList02.clearSelection();
                    }
                }
            });

            JLabel titleLbl02 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00327")/*참조자*/);
            JLabel blankLbl02 = new JLabel("                                                   ");
            titleLbl02.setFont(FontList.defaultFont);

            btnpanel02.add(moveUpBtn02);
            btnpanel02.add(moveDownBtn02);
            udPanel02.add(titleLbl02,    BorderLayout.WEST);
            udPanel02.add(blankLbl02,  BorderLayout.CENTER);
            udPanel02.add(btnpanel02, BorderLayout.EAST);

            jtblList02.setAutoCreateColumnsFromModel(false);
            jtblList02.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            jtblList02.setModel(dataList02);
            jtblList02.repaint();

            for( int t = 0; t < DistributionBomData02.clmModelData.length; t++ )
            {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(DistributionBomData02.clmModelData[t].intAlignment);
                TableColumn column02 = new TableColumn(t, DistributionBomData02.clmModelData[t].intWidth, renderer, null);
                jtblList02.addColumn(column02);

                agreeModel = jtblList02.getColumnModel();
                TableColumn _column = agreeModel.getColumn(t);
                _column.setCellRenderer(renderer);
            }

            JTableHeader headerModel02 = jtblList02.getTableHeader();
            headerModel02.setUpdateTableInRealTime(false);

            JScrollPane ps02 = new JScrollPane();
            ps02.getViewport().add(jtblList02);

            agreeModel = jtblList02.getColumnModel();
            TableColumn column02 = agreeModel.getColumn(5);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);

            column02 = agreeModel.getColumn(6);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);

            agreePanel.add(udPanel02);
            agreePanel.add(ps02);

            reviewPanel.setPreferredSize( new Dimension( 300, 290 ) );
            agreePanel.setPreferredSize( new Dimension( 300, 290 ) );
            //confirmPanel.setPreferredSize( new Dimension( 300, 193 ) );

            allRPanel.add(reviewPanel, BorderLayout.NORTH);
            allRPanel.add(agreePanel, BorderLayout.SOUTH);
            //allRPanel.add(confirmPanel, BorderLayout.SOUTH);

            allRPanel.setPreferredSize( new Dimension( 320, 650 ) );

            this.getContentPane().add(allRPanel, BorderLayout.EAST);

    //Right End............................................................................................................................................................

            // Button Panel /////////////////////////////////////////////////
            JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, appReg.getImageIcon("okIcon"));
            okBtn.setFont(FontList.defaultFont);
            okBtn.setActionCommand("OK");
            okBtn.addActionListener(btnListener);
            okBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(okBtn);

            closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
            closeBtn.setFont(FontList.defaultFont);
            closeBtn.setActionCommand("Close");
            closeBtn.addActionListener(btnListener);
            closeBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(closeBtn);

            this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    private void searchBtn_process()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try
        {
            String strNameCode = cNameTfl.getText() == null ? "" : cNameTfl.getText().trim();
            if( strNameCode.equals("") )
            {
                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("searchCondition"), "Input Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);

                //itemCodeTfl.requestFocus();
            }
            else
            {
                ConfirmLineQry cq = new ConfirmLineQry();
                vecModel = cq.getCoworkerData(strNameCode);

                cNameTfl.setText("");

                dataList = new ConfirmLineSchData(vecModel);
                tblList01.setModel(dataList);
                tblList01.repaint();
            }
        }
        catch( Exception e )
        {
            Kogger.error(getClass(), e);
        }
        finally
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void okBtn_process()
    {
//        if( ( (dataList01.vecWorkerData).size() + (dataList02.vecWorkerData).size() ) == 0)
//        {
//            MessageBox mbox = new MessageBox(desktop, "지정된 사용자가 없습니다.", "Select Error", 0);
//            mbox.setModal(true);
//            mbox.setVisible(true);
//
//            return;
//        }

        Vector td = new Vector();

        // 수신자
        for( int i = 0; i < (dataList01.vecWorkerData).size(); i++ )
        {
            td.add((dataList01.vecWorkerData).elementAt(i));
        }

        // 참조자
        for( int p = 0; p < (dataList02.vecWorkerData).size(); p++ )
        {
            td.add((dataList02.vecWorkerData).elementAt(p));
        }

        resultVector = td;

        isOK = true;
        disposeScreen();

        setVisible(false);
        dispose();
    }

    public Vector getResultSetData()
    {
        return resultVector;
    }

    public String[] getSelectedColumnData()
    {
        return aryResult;
    }

    public boolean getOK()
    {
        return isOK;
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

     public int getReceiverInt()
     {
            return jtblList01.getRowCount();
     }

     public int getReferenceInt()
     {
            return jtblList02.getRowCount();
     }

    //shin.....
    private void coworkerAdd()
    {
        try
        {
            ValidationChecker vc = new ValidationChecker();
            boolean isEmpty = false;

            if( vc.isEmpty(cNameTfl) )
            {
                isEmpty = true;
            }
            else
            {
                isEmpty = false;
            }

            if( isEmpty )
            {
                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("inputCoworkerInfo"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                cNameTfl.requestFocus();
                tblList01.clearSelection();
            }
            else
            {
                //setCoworkerData(cNameTfl.getText().toString().trim(), cDeptTfl.getText().toString().trim());
            }
        }
        catch (Throwable ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    public void openBOMData(BOMAssyComponent rootComp, Vector bomData)
    {
        try
        {
            this.bomDataVec = bomData;

            rootComponent = rootComp;

            model.getRootNode().setBOMComponent(rootComponent);

            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    new Thread(new ConfirmTreeSearchLoader(model.getRootNode(), bomDataVec , treeTable, model)).start();
                }
            });
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    //결재요청 화면의 리스트를 뿌려준다.
     private void setConfTable(JTable confTb)
     {
         int tableRowCnt = confTb.getRowCount();
         for( int i = 0; i < tableRowCnt; i++ )
        {
             if( ((String)confTb.getValueAt(i, 6)).equals("AA") )
            {
                int tableRowCnt01 = jtblList01.getRowCount();
                dataList01.vecWorkerData.add(tableRowCnt01,new ConfirmBomData(
                    (String)confTb.getValueAt(i, 1)
                    ,(String)confTb.getValueAt(i, 2)
                    ,(String)confTb.getValueAt(i, 3)
                    ,(String)confTb.getValueAt(i, 5)
                    ,(String)confTb.getValueAt(i, 4)
                    ,(String)confTb.getValueAt(i, 6)
                    ,String.valueOf(tableRowCnt01+1)
                ));
                dataList01.fireTableRowsInserted(0, dataList01.getRowCount()-1);
            }
             else if( ((String)confTb.getValueAt(i, 6)).equals("BB") )
            {
                int tableRowCnt02 = jtblList02.getRowCount();
                dataList02.vecWorkerData.add(tableRowCnt02,new ConfirmBomData(
                    (String)confTb.getValueAt(i, 1)
                    ,(String)confTb.getValueAt(i, 2)
                    ,(String)confTb.getValueAt(i, 3)
                    ,(String)confTb.getValueAt(i, 5)
                    ,(String)confTb.getValueAt(i, 4)
                    ,(String)confTb.getValueAt(i, 6)
                    ,String.valueOf(tableRowCnt02+1)
                ));
                dataList02.fireTableRowsInserted(0, dataList02.getRowCount()-1);
            }
        }
     }
}
