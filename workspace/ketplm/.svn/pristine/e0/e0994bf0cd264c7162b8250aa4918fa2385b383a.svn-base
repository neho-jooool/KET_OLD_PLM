package e3ps.bom.command.loadexcelbom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import wt.part.WTPart;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkout.CheckOutCmd;
import e3ps.bom.command.mybom.MyBOMQry;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.TableSorter;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.StringUtil;
import e3ps.edm.beans.EDMPartHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;

public class LoadExcelBOMFrame extends AbstractAIFDialog
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private BtnListener btnListener;
    JTable tblList01 = new JTable();
    JTable tblList02 = new JTable();

    private TableSorter sorter;
    private extItemSchData dataList01;
    private newItemSchData dataList02;


    private Vector resultList ;
    Registry appReg;
    boolean keyCheck = false;

    private AbstractAIFUIApplication app;
    private JFrame desktop;

    private JButton okBtn, closeBtn;

    String itemCodeStr = "";
    String titleStr = "";
    private boolean isOK = false;
    private int intRowCount = -1;
    private String aryResult [] = new String[8];
    Vector vecModel01 = new Vector();
    Vector vecModel02 = new Vector();

    private BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

    class BtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("OK"))
            {
                okBtn_process();
            }
            else if (e.getActionCommand().equals("Close"))
            {
                disposeScreen();
            }
        }
    }

    public LoadExcelBOMFrame(JFrame frame, AbstractAIFUIApplication app, String title,Vector vc)
    {
        super(frame, title , true);
        this.desktop = frame;
        this.app = app;
        itemCodeStr = "";
        titleStr = title;
        appReg = Registry.getRegistry(app);
        this.resultList = vc;

        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                disposeScreen();
            }
        });

        try
        {
            searchItem_process(vc);

            dataList01 = new extItemSchData(vecModel01);
            dataList02 = new newItemSchData(vecModel02);

            jInit();

            setSize(800,420);
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

    private void jInit() throws Exception
    {
        try
        {
            //Left Start ..................................................................................................................................

            TableColumnModel columnModel01 = null;
            TableColumnModel columnModel02 = null;
            btnListener = new BtnListener();

            JPanel Allpan = new JPanel();
            JPanel Lpan = new JPanel();
            JPanel Rpan = new JPanel();

            // Search Condition Panel /////////////////////////////////////////////////
            JPanel extPanel = new JPanel();
            JPanel newPanel = new JPanel();
            //JPanel allLPanel = new JPanel();

            extPanel.setLayout(new BoxLayout(extPanel, BoxLayout.Y_AXIS));
            extPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00121")/*기존 부품 목록*/, 0, 0, FontList.defaultFont));
            extPanel.setFont(FontList.defaultFont);
            extPanel.setPreferredSize(new Dimension(400, 360));

            // Search Result Panel /////////////////////////////////////////////////
            JPanel centerPanel01 = new JPanel(new BorderLayout());

            tblList01.setAutoCreateColumnsFromModel(false);
            tblList01.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            sorter = new TableSorter(dataList01);
            tblList01.setModel(sorter);
            sorter.addMouseListenerToHeaderInTable(tblList01);
            tblList01.repaint();

            for(int k = 0; k < extItemSchData.clmModelData.length; k++)
            {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(extItemSchData.clmModelData[k].intAlignment);
                TableColumn column01 = new TableColumn(k, extItemSchData.clmModelData[k].intWidth, renderer, null);
                tblList01.addColumn(column01);

                columnModel01 = tblList01.getColumnModel();
                TableColumn _column = columnModel01.getColumn(k);
                _column.setCellRenderer(renderer);
            }

            JTableHeader headerModel01 = tblList01.getTableHeader();
            headerModel01.setUpdateTableInRealTime(false);

            JScrollPane ps01 = new JScrollPane();
            ps01.getViewport().add(tblList01);

            columnModel01 = tblList01.getColumnModel();

            //shin...테이블에서 숨김.
            TableColumn column01;
            column01 = columnModel01.getColumn(3);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);
            column01 = columnModel01.getColumn(4);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);
            column01 = columnModel01.getColumn(5);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);
            column01 = columnModel01.getColumn(6);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);

            column01 = columnModel01.getColumn(7);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);
            column01 = columnModel01.getColumn(8);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);
            column01 = columnModel01.getColumn(9);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);
            column01 = columnModel01.getColumn(10);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);
            column01 = columnModel01.getColumn(11);
            column01.setWidth(0);
            column01.setMinWidth(0);
            column01.setMaxWidth(0);
            column01.setResizable(false);

            centerPanel01.add(ps01, BorderLayout.CENTER);
            extPanel.add(centerPanel01);

            extPanel.setPreferredSize( new Dimension( 390, 350 ) );
            //-------------------------------------------------------------------------------------------------------

            newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
            newPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00040")/*ERP 신규 등록 부품*/, 0, 0, FontList.defaultFont));
            newPanel.setFont(FontList.defaultFont);
            newPanel.setPreferredSize(new Dimension(400, 360));

            // Search Result Panel /////////////////////////////////////////////////
            JPanel centerPanel02 = new JPanel(new BorderLayout());

            tblList02.setAutoCreateColumnsFromModel(false);
            tblList02.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            sorter = new TableSorter(dataList02);
            tblList02.setModel(sorter);
            sorter.addMouseListenerToHeaderInTable(tblList02);
            tblList02.repaint();

            for(int k = 0; k < newItemSchData.clmModelData2.length; k++)
            {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(newItemSchData.clmModelData2[k].intAlignment);
                TableColumn column02 = new TableColumn(k, newItemSchData.clmModelData2[k].intWidth, renderer, null);
                tblList02.addColumn(column02);

                columnModel02 = tblList02.getColumnModel();
                TableColumn _column = columnModel02.getColumn(k);
                _column.setCellRenderer(renderer);
            }

            JTableHeader headerModel02 = tblList02.getTableHeader();
            headerModel02.setUpdateTableInRealTime(false);

            JScrollPane ps02 = new JScrollPane();
            ps02.getViewport().add(tblList02);

            columnModel02 = tblList02.getColumnModel();

            //shin...테이블에서 숨김.
            TableColumn column02;
            column02 = columnModel02.getColumn(3);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);
            column02 = columnModel02.getColumn(4);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);
            column02 = columnModel02.getColumn(5);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);
            column02 = columnModel02.getColumn(6);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);

            column02 = columnModel02.getColumn(7);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);
            column02 = columnModel02.getColumn(8);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);
            column02 = columnModel02.getColumn(9);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);
            column02 = columnModel02.getColumn(10);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);
            column02 = columnModel02.getColumn(11);
            column02.setWidth(0);
            column02.setMinWidth(0);
            column02.setMaxWidth(0);
            column02.setResizable(false);


            centerPanel02.add(ps02, BorderLayout.CENTER);
            newPanel.add(centerPanel02);

            newPanel.setPreferredSize( new Dimension( 390, 350 ) );
            //--------------------------------------------------------------------------------------------------------


            Lpan.add(extPanel);
            Rpan.add(newPanel);
            Allpan.add(extPanel, BorderLayout.WEST);
            Allpan.add(newPanel, BorderLayout.EAST);

            this.getContentPane().add(Allpan, BorderLayout.CENTER);

            // End...................................................................................................................................

            // Button Panel /////////////////////////////////////////////////
            JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00042")/*ERP 전송*/, appReg.getImageIcon("okIcon"));
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

    private void searchItem_process(Vector vc)
    {
        LoadExcelBOMQry cq = new LoadExcelBOMQry();
        vecModel01 = (Vector)(cq.getBomItemData(vc)).elementAt(0);
        vecModel02 = (Vector)(cq.getBomItemData(vc)).elementAt(1);

        dataList01 = new extItemSchData(vecModel01);
        dataList02 = new newItemSchData(vecModel02);

        if( (vecModel01.size()+vecModel02.size()) > 0)
        {
            intRowCount = vecModel01.size()+vecModel02.size();
        }

        //sorter = new TableSorter(dataList01);
        //tblList01.setModel(sorter);
        //sorter.addMouseListenerToHeaderInTable(tblList01);
        //tblList01.repaint();
    }

    private void okBtn_process()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
        Vector chk_vc = resultList;
        String p_itemcd = StringUtil.checkNull((String)((Hashtable)chk_vc.elementAt(0)).get("PARENTITEMCODE"));
Kogger.debug(getClass(), "@@@@ [okBtn_process] Die Part No : " + p_itemcd);
        KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
        WTPart part = kh.searchItem(p_itemcd);
Kogger.debug(getClass(), "@@@@ [okBtn_process] Die Part : " + part);

            if(part == null)
            {
                MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00192")/*부품으로 등록되어 있지 않은 금형번호입니다.*/, "Select Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);

                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                return;
            }
        }catch(Exception e){
            Kogger.debug(getClass(), "############ head item exist check error : "+e);
        }

        if(intRowCount == -1)
        {
            MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("compare2"), "Select Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
            return;
        }
        else
        {
            try{

                //이미 header 에 등록된 부품인지 검사한다.
                Vector vc = resultList;
                String rootItemCode = (String)((Hashtable)vc.elementAt(0)).get("PARENTITEMCODE");

                boolean isDupItemCode = isDupItemCodeData(rootItemCode);
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                    if(isDupItemCode)
                    {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00196", rootItemCode)/*부품코드 ({0})는 이미 PLM에 존재합니다.*/, "Input Error", 0);
                        mbox.setModal(true);
                        mbox.setVisible(true);
                    }else{

                        //shin....
                        //1. 신규일 경우 ERP에 등록 (기존만 있을 경우는 바로 메세지 띄우고 createBOM() 실행)
                        LoadExcelBOMQry cq = new LoadExcelBOMQry();
                        Vector newMold = (Vector)(cq.getBomItemData(resultList)).elementAt(2);
                        if(newMold.size() > 0)
                        {
                            for(int i=0;i<newMold.size();i++)
                            {
                                Hashtable hash = (Hashtable)newMold.elementAt(i);

                                Vector<String> vecResult =  KETPartHelper.service.createSapPart(hash);
                                String strSuccess = vecResult.elementAt(0);

                                if (strSuccess != null && strSuccess.equals("S")) // ERP 전송이 성공한 경우
                                {
                                    // 2. 신규 금형부품 생성 (PLM)
                                    Hashtable hashRsltPart = KETPartHelper.service.create(hash);
                                    if (hashRsltPart == null) {
                                        MessageBox mbox = new MessageBox(desktop,  KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00049")/*PLM에 신규금형부품 생성이 실패하였습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00191")/*부품생성실패*/, 1);
                                        mbox.setModal(true);
                                        mbox.setVisible(true);
                                        return;
                                    }
                                }else{
Kogger.debug(getClass(), "################# ERP Trans Err Code : "+strSuccess);
                                    MessageBox mbox = new MessageBox(desktop,  KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00044")/*ERP에 신규금형부품 등록이 실패하였습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00180")/*부품등록실패*/, 1);
                                    mbox.setModal(true);
                                    mbox.setVisible(true);
                                    return;
                                }
                            }

                            MessageBox mbox = new MessageBox(desktop,  KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00257")/*신규금형부품 생성이 완료 되었습니다. \n BOM을 생성하시겠습니까?.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00043")/*ERP 전송완료*/, 1);
                            mbox.setModal(true);
                            mbox.setVisible(true);

                            createBOM();
                        }else{
                            MessageBox mbox = new MessageBox(desktop,  KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00036")/*BOM을 생성하시겠습니까?*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00034")/*BOM생성*/, 1);
                            mbox.setModal(true);
                            mbox.setVisible(true);

                            createBOM();
                        }
                    }
            }catch(Exception e)
            {
                MessageBox mbox = new MessageBox(desktop,  KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00105")/*금형부품 생성중 오류가 발생하였습니다. \n 내역 :*/ + " "+e, "Error", 1);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
        }
    }

    public boolean isDupItemCodeData(String itemCode) throws Exception
    {
        boolean isDep = false;
        try
        {
            Hashtable inputParams = new Hashtable();

            //shin.....
            inputParams.put("itemCode", (itemCode));
            inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());

            String queryResult = KETBomHelper.service.searchDuplicationItem(inputParams);

            Kogger.debug(getClass(), "searchDuplicationItem  : "+queryResult);

            if(queryResult.equals("Y"))
            {
                isDep = true;
            }
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
        return isDep;
    }

    //shin...
    private void createBOM() {

        KetMainJTreeTable km = new KetMainJTreeTable();

        Vector vc = resultList;
        try
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean(); //cad no 처리...

            BOMRegisterApplicationPanel pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();

            pnl.clearBOMList();  //현재화면을 클리어 함.

            km.setMoldMain(app);

            //3. 컴포넌트 등록
            if(insertNewBOMData(vc))  //작업자 등록
            {
                CheckOutCmd coc = new CheckOutCmd(desktop, app); //checkout 용 추가.

                isOK=false;

                //화면에 세팅하는곳.
                Vector version;// = new Vector();
                Vector itemVec; //= new Vector();
                Hashtable inputParams = new Hashtable();

                MyBOMQry qry = new MyBOMQry();

                String rootItemCode= "";
                String rootItemDesc= "";
                String rootItemStatus= "";
                String rootItemStatusKr= "";
                String rootItemUnit= "";
                String subItemCode= "";
                String subItemDesc= "";
                String subItemStatus= "";
                String subItemUnit= "";
                String versionStr = "";
                // Added by MJOH, 2011-04-07
                String partTypeStr = "";

                BOMAssyComponent rootComponent = null;
                BOMAssyComponent assyComponent = null;

                rootItemCode = (String)((Hashtable)vc.elementAt(0)).get("PARENTITEMCODE");
                try
                {
                    Vector queryResult = new Vector();
                    Hashtable inputHash = new Hashtable();
                    Hashtable hasSearchItemResult = new Hashtable();

                    inputHash.put("itemCode", rootItemCode);
                    inputHash.put("orgCode", BOMBasicInfoPool.getOrgCode());
                    queryResult = KETBomHelper.service.searchItem(inputHash);

                    hasSearchItemResult = (Hashtable)queryResult.elementAt(0);
                    rootItemDesc = (String)hasSearchItemResult.get("description");
                    rootItemStatus = (String)hasSearchItemResult.get("status");
                    rootItemStatusKr = (String)hasSearchItemResult.get("statusKr");
                    rootItemUnit = (String)hasSearchItemResult.get("defaultunit");
                    versionStr = (String)hasSearchItemResult.get("version");
                    // Added by MJOH, 2011-04-07
                    partTypeStr = (String)hasSearchItemResult.get("typeValue");
                }catch(Exception e){
                    rootItemDesc = "";
                    rootItemStatus = "";
                    rootItemUnit = "";
                    versionStr = "0";
                    // Added by MJOH, 2011-04-07
                    partTypeStr = "";
                    Kogger.debug(getClass(), "Excel Item Search Error 001");
                }

                BOMBasicInfoPool.setPublicBOMStatusKr("INWORK");        // 신규 BOM 상태 저장

                rootComponent = new BOMAssyComponent(rootItemCode, true);
                rootComponent.setLevelInt(new Integer(0));
                rootComponent.setSeqInt(new Integer(0));
                rootComponent.setSortOrderStr("0001");
                rootComponent.setNewFlagStr("NEW");
                rootComponent.setSecondMarkStr("OLD");

                //rootComponent.setDescStr((String)((Hashtable)vc.elementAt(0)).get("DESC"));
                rootComponent.setDescStr(rootItemDesc); //엑셀것을 쓰지않고 부품명을 db 에서 읽어 옴.

                rootComponent.setQuantityDbl(  Double.parseDouble((String)((Hashtable)vc.elementAt(0)).get("QUANTITY"))) ;  //수량
                rootComponent.setStatusStr(rootItemStatus);  //결재상태
                rootComponent.setStatusKrStr(rootItemStatusKr);  //결재상태 한글
                rootComponent.setUitStr(rootItemUnit); //기본단위....
                rootComponent.setItemSeqInt(new Integer(10));
                // Added by MJOH, 2011-04-07
                rootComponent.setIBAPartType( partTypeStr );
                BOMBasicInfoPool.setBomHeaderPartType( partTypeStr );

                //버젼 처리
                itemVec = new Vector();
                itemVec.addElement(rootItemCode);
                rootComponent.setVersionStr(versionStr);
                //--------------------------------------------------
                versionStr = (rootComponent.getVersionStr()) ==  null ? "0" : rootComponent.getVersionStr();
//Kogger.debug(getClass(), "@@@@@@@ versionStr : " + versionStr);
                rootComponent.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
                BOMTreeNode[] nodes = pnl.getSelectedTreeNode();
                coc.checkOut( nodes, itemVec, BOMBasicInfoPool.getUserName(), true, versionStr );
//                checkoutDao.setCheckOut( rootItemCode, versionStr , BOMBasicInfoPool.getUserId() );

                Vector subAssyVec = new Vector();
                subAssyVec.removeAllElements();

                String sortOrder = "0001";
                for(int p=0 ; p < vc.size() ; p++)
                {
                    versionStr ="";
                    if ((p+1) < 10)
                        sortOrder = "000" + (p+1);
                    else if ((p+1) < 100)
                        sortOrder = "00" + (p+1);
                    else if ((p+1) < 1000)
                        sortOrder = "0" + (p+1);
                    else if ((p+1) < 10000)
                        sortOrder = String.valueOf(p+1);

                    subItemCode = (String)((Hashtable)vc.elementAt(p)).get("CHILDITEMCODE"); // 금형 부품 번호..

//Kogger.debug(getClass(), "@@@@@@@@@@@ subItemCode : " + subItemCode);
                    Vector queryResult = new Vector();
                    Hashtable inputHash = new Hashtable();
                    Hashtable hasSearchItemResult = new Hashtable();

                    try
                    {
                        WTPart childPart = bean.searchItem( subItemCode ); // 금형부품 번호를 통해 WTPart 객치 가져옴..

                        // 도면 - 부품 연관정보 생성... 금형부품 WTPart 객체, CONITEM : 대표부품번호(Excel 데이터기준..)
                        Kogger.debug(getClass(), "ppppppppppppppppp==============" + p);
                        String conitem = (String)((Hashtable)vc.get(p)).get("CONITEM"); // 대표부품체크..
                        String cadno = (String)((Hashtable)vc.get(p)).get("CADNO"); // CADNO 유무..
                        EDMPartHelper.setReferencedBy(childPart, conitem, cadno);
                        //EDMPartHelper.setReferencedBy(childPart, (String)((Hashtable)vc.elementAt(p)).get("CONITEM"), (String)((Hashtable)vc.elementAt(p)).get("CADNO"));

                        inputHash.put("itemCode", subItemCode);
                        inputHash.put("orgCode", BOMBasicInfoPool.getOrgCode());
                        queryResult = KETBomHelper.service.searchItem(inputHash);

                        hasSearchItemResult = (Hashtable)queryResult.elementAt(0);
                        subItemDesc = (String)hasSearchItemResult.get("description");
                        subItemStatus = (String)hasSearchItemResult.get("status");
                        subItemUnit = (String)hasSearchItemResult.get("defaultunit");
                        versionStr = (String)hasSearchItemResult.get("version");
                        partTypeStr = (String)hasSearchItemResult.get("typeValue");
                    }
                    catch(Exception e)
                    {
                        subItemDesc = "";
                        subItemStatus = "";
                        subItemUnit = "";
                        versionStr = "0";
                        partTypeStr = "";
                        Kogger.debug(getClass(), "Excel Item Search Error 002-"+p);
                    }

                    String strType = "NEW";
                    WTPart part = bean.searchItem(subItemCode);
                    strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpBOMFlag);

                    //assyComponent = new BOMAssyComponent(subItemCode,true);  //shin...이걸 쓰면 마지막이 최상위껄루 나옴.
                    assyComponent = new BOMAssyComponent(subItemCode);

                    assyComponent.setParentItemCodeStr(rootItemCode);
                    assyComponent.setSortOrderStr("0001"+sortOrder);
                    assyComponent.setDescStr(subItemDesc);
                    assyComponent.setQuantityDbl(  Double.parseDouble((String)((Hashtable)vc.elementAt(p)).get("QUANTITY"))) ;  //수량
                    assyComponent.setLevelInt(new Integer(1));
                    assyComponent.setSeqInt(new Integer(0));
                    assyComponent.setItemSeqInt(new Integer((p+1)+"0"));
                    assyComponent.setNewFlagStr(strType);
                    assyComponent.setSecondMarkStr("NEW");
                    assyComponent.setStartDate(Utility.currentDate());
                    assyComponent.setStatusStr(subItemStatus);  //결재상태
                    assyComponent.setStatusKrStr(rootItemStatusKr);  //결재상태 한글
                    assyComponent.setUitStr(subItemUnit); //기본단위....
                    assyComponent.setMaterialStr((String)((Hashtable)vc.elementAt(p)).get("MATERIAL")); //재질....
                    assyComponent.setHardnessFrom((String)((Hashtable)vc.elementAt(p)).get("HARDNESSFROM")); //경도(From)....
                    assyComponent.setHardnessTo((String)((Hashtable)vc.elementAt(p)).get("HARDNESSTO")); //경도(To)....
                    assyComponent.setDesignDate((String)((Hashtable)vc.elementAt(p)).get("DESIGNDATE")); //설계일자....
                    // Added by MJOH, 2011-04-07
                    assyComponent.setIBAPartType( partTypeStr );

                    //버젼 처리
                    itemVec = new Vector();
                    itemVec.addElement(subItemCode);
                    assyComponent.setVersionStr(versionStr);
                    subAssyVec.addElement(assyComponent);

                    //체크아웃 처리
//                    versionStr = (assyComponent.getVersionStr()).equals("") ? "0" : assyComponent.getVersionStr();
//Kogger.debug(getClass(), "@@@@@@@@ versionStr : " + versionStr);
//                    assyComponent.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");

                    // TODO 자부품은 체크아웃 할 필요 없으므로 주석처리함
//                    coc.checkOut(nodes, itemVec, BOMBasicInfoPool.getUserName(), true);
//                    checkoutDao.setCheckOut( subItemCode, versionStr , BOMBasicInfoPool.getUserId() );
                }

                //버튼 활성화 등등...
                /*BOMBasicInfoPool.setPublicCheckOutStatus("2");
                pnl.setCheckStatus(2);
                pnl.setMyStatus(3);
                pnl.setMenuBarEnabled();
                pnl.publicStatusPanel.setStatusBar();*/

                componentBOM(rootItemCode, subAssyVec);

                //shin....
                BOMBasicInfoPool.setPublicModelName(rootItemCode);
                BOMBasicInfoPool.setPublicModelDesc((String)((Hashtable)vc.elementAt(0)).get("DESC"));

                String ownerid = Utility.checkNVL(BOMBasicInfoPool.getUserId());
                BOMBasicInfoPool.setPublicOwnerId(ownerid);

                //shin....
                BOMBasicInfoPool.setCoWorkerVec(qry.getCoworkerData(rootItemCode));

                BOMBasicInfoPool.setPublicBOMStatus("INWORK");
                BOMBasicInfoPool.setPublicMyStatus("3");

                if(BOMBasicInfoPool.isAdmin() == true)
                {
                    BOMBasicInfoPool.setIsAdminFlag(true);
                }
                pnl.setCheckStatus(2);
                pnl.publicStatusPanel.setStatusBar();

                BOMTreeNode selectedNode = new BOMTreeNode(rootComponent);

                pnl.openBOMData(rootComponent, subAssyVec);
                pnl.requestFocus();

                if(isOK) {
                    pnl.setMenuBarEnabled();
                    cancelBOM();
                }
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                disposeScreen();
            }else{
                isOK = true;
                disposeScreen();
            }
        }catch(Exception e){
            Kogger.debug(getClass(), "######################### Component err: "+e);
            MessageBox mbox = new MessageBox(desktop, String.valueOf(e) , "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
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
        for(int i=0; i<count; i++)
        {
              Component c = getComponent(i);
              this.remove(c);
              c = null;
        }
        super.dispose();
        System.gc();
      }

    //작업자 insert
    public boolean insertNewBOMData(Vector vec) throws Exception
    {
        String SQL = "";

        if(!setWindChill(vec)) {
            return false;
        }

        DBConnectionManager res = null;
        Connection conn = null;
        Statement stmt = null;

        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(appReg.getString("plm"));
            stmt = conn.createStatement();

            String itemCd = BOMBasicInfoPool.getPublicModelName();
            Hashtable inputParams = new Hashtable();
            Vector itemVec = new Vector();
            itemVec.add(itemCd);
            inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
            inputParams.put("itemCode", itemVec);
            Vector version = KETBomHelper.service.getItemVersion(inputParams);
            String versionStr = "";
            for(int k=0; k<version.size(); k++) {
                if(version.size() > 0)     {
                    versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
                    //shin...
                    if(itemCd.equals(versionStr.substring(0, versionStr.indexOf("@")))) {
                        versionStr = versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf("("));
                    }
                }
            }

            SQL  = " INSERT INTO ketbomcoworker                                                                                    ";
            SQL += " (newbomcode, coworkerid, coworkername, coworkeremail, coworkerdept, endworkingflag, bomversion)            ";
            SQL += " VALUES ( '" + BOMBasicInfoPool.getPublicModelName() + "'                                                ";
            SQL += ", '" + BOMBasicInfoPool.getUserId() + "'                                                                            ";
            SQL += ", '" + BOMBasicInfoPool.getUserName() + "'                                                                    ";
            SQL += ", '" + BOMBasicInfoPool.getUserEMail() + "'                                                                        ";
            SQL += ", '" + BOMBasicInfoPool.getUserDept() + "'                                                                        ";
            SQL += ", '2' , '"+versionStr+"' )                                                                                                                    ";

            stmt.executeUpdate(SQL);
            conn.commit();

        } catch(Exception e) {
            Kogger.error(getClass(), e);
            return false;
        } finally {
            try    {
                if(stmt != null)
                    stmt.close();
            } catch(Exception e)     {
                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null) {
                    res.freeConnection(appReg.getString("plm"), conn);
                }
            }
            return true;
        }
    }

    //데이타를 ketbomheader 테이블에 입력.
    public boolean setWindChill(Vector vc)
    {
        String descStr="";
        String statusStr="";
        String unitStr="";
        String versionStr = "";
        Hashtable inputParams = new Hashtable();

        try    {
            Vector queryResult = new Vector();
            Hashtable inputHash = new Hashtable();
            Hashtable hasSearchItemResult = new Hashtable();

            try {
                inputHash.put("itemCode", (String)((Hashtable)vc.elementAt(0)).get("PARENTITEMCODE"));
                inputHash.put("orgCode", BOMBasicInfoPool.getOrgCode());
                queryResult = KETBomHelper.service.searchItem(inputHash);

                hasSearchItemResult = (Hashtable)queryResult.elementAt(0);
                descStr = (String)hasSearchItemResult.get("description");
                statusStr = (String)hasSearchItemResult.get("status");
                unitStr = (String)hasSearchItemResult.get("defaultunit");
                versionStr = (String)hasSearchItemResult.get("version");
            }catch(Exception e){
                Kogger.debug(getClass(), "Head Save value get Error");
            }

            String  rootItemCd = (String)((Hashtable)vc.elementAt(0)).get("PARENTITEMCODE");
            Vector itemVec = new Vector();
            itemVec.add(rootItemCd);

            inputParams.put("newBomCode", rootItemCd);
            inputParams.put("description", descStr);
            inputParams.put("status", statusStr);
            inputParams.put("quantity", (String)((Hashtable)vc.elementAt(0)).get("QUANTITY"));
            inputParams.put("unit", unitStr);
            inputParams.put("creatorDept", BOMBasicInfoPool.getUserDept());
            inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
            inputParams.put("itemCode", itemVec);
            if (versionStr != null && !versionStr.equals(""))
                inputParams.put("versionStr", versionStr);
            else
                inputParams.put("versionStr", "0");

            Vector coworkerVec = new Vector();
            coworkerVec.addElement(BOMBasicInfoPool.getUserId());
            coworkerVec.addElement(BOMBasicInfoPool.getUserName());
            coworkerVec.addElement(BOMBasicInfoPool.getUserEMail());
            coworkerVec.addElement(BOMBasicInfoPool.getUserDept());
            inputParams.put("coworkers", coworkerVec);

            String oid = KETBomHelper.service.createBom(inputParams);

            if(oid==null || oid.length()==0){
                return false;
            }

            if(oid != null){
                Hashtable hasHeader = new Hashtable();
                hasHeader = KETBomHelper.service.getBom(oid);

                //shin.....
                BOMBasicInfoPool.setPublicModelName((String)((Hashtable)vc.elementAt(0)).get("PARENTITEMCODE"));
                BOMBasicInfoPool.setPublicBOMOid(oid);
            }

            return true;
        }catch (Exception ex){
            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00097")/*관리자에게 문의하세요.*/ + " \n\n"+ex.toString(), "BOM System Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
            Kogger.error(getClass(), ex);

            return false;
        }
    }

    private void cancelBOM()
    {
        try {
            int count = getComponentCount();
            for(int i=0; i<count; i++) {
                Component c = getComponent(i);
                this.remove(c);
                c = null;
            }

            super.dispose();
            System.gc();
            return;
        } catch (Throwable ex) {
            Kogger.error(getClass(), ex);
        }
    }

    //자부품 등록
    private void componentBOM(String rootItemCd, Vector bomVec)
    {
        DBConnectionManager res = null;
        Connection conn = null;
        Statement stmt = null;

        try    {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(appReg.getString("plm"));
            stmt = conn.createStatement();

            BOMSaveDao saveDao = new BOMSaveDao();
            saveDao.saveExcelBomList(conn, rootItemCd, bomVec);
            conn.commit();

        } catch(Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            try    {
                if(stmt != null)
                    stmt.close();
            } catch(Exception e)    {
                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null) {
                    res.freeConnection(appReg.getString("plm"), conn);
                }
            }
        }
    }
}
