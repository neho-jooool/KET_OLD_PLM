package e3ps.bom.command.mybom;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class MyBOMFrame extends JFrame implements Runnable
{
    private static final long serialVersionUID = 1L;
    private JFrame desktop;
    BOMRegisterApplicationPanel pnl;
    AbstractAIFUIApplication aifapp;
    Registry appReg;

    private JTabbedPane tabbedPane;
    private MyBomPanel myBomPanel;
    private MyBomEcoPanel myBomEcoPanel;

    Vector vecBom = new Vector();
    Vector vecBomEco = new Vector();
    Hashtable inputParams     = new Hashtable();
    Hashtable outputParams     = new Hashtable();

    public MyBOMFrame(JFrame frame, AbstractAIFUIApplication app) {
        super("My BOM/ECO");

        try {
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            aifapp = app;
            desktop = app.getDesktop();
            appReg = Registry.getRegistry(aifapp);

            pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();

            setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00124")/*내 작업목록*/);
            setSize(750, 350);
            setResizable(false);

            // 자신이 속한 BOM List 를 가져온다.
            Hashtable inputParams = new Hashtable();
            Vector vecItemCode = getBomList();
            inputParams.put("userId", BOMBasicInfoPool.getUserId());
            inputParams.put("vecItemCode", vecItemCode);
            inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());

            Vector vecBomHeader = new Vector();

Kogger.debug(getClass(), "vecItemCode : " + vecItemCode.size());

            vecBomHeader = KETBomHelper.service.myBom(inputParams);
            String strCreatedDate = "";

            MyBOMQry qry = new MyBOMQry();

            if (vecBomHeader.size() > 0) {
                for (int i=0; i < vecBomHeader.size(); i++) {

                    Hashtable hasBomHeader = new Hashtable();
                    Vector vecUsers = new Vector();

                    hasBomHeader = (Hashtable)vecBomHeader.elementAt(i);

                    if (hasBomHeader.get("createdDate").toString().length() > 10)     {
                        strCreatedDate = hasBomHeader.get("createdDate").toString().substring(0,10);
                    } else {
                        strCreatedDate = hasBomHeader.get("createdDate").toString();
                    }
                    vecUsers = (Vector)qry.getCoworkerInfo((String)hasBomHeader.get("itemCode"));

                    vecBom.addElement(new BOMData(hasBomHeader.get("itemCode").toString().trim(),
                                                                        (String)hasBomHeader.get("description"),
                                                                        (String)hasBomHeader.get("createdBy"),
                                                                        strCreatedDate,
                                                                        (String)hasBomHeader.get("bomState"),
                                                                        vecUsers,
                                                                        (String)hasBomHeader.get("bomOid")
                    ));
                }
            }

            JPanel tmpMyBomEcoPanel = new JPanel();

            myBomPanel     = new MyBomPanel(this, aifapp, vecBom);

            tabbedPane = new JTabbedPane();
            tabbedPane.setTabPlacement(SwingConstants.TOP);
            tabbedPane.addTab(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00253")/*신규 BOM*/, appReg.getImageIcon("bomdetailsIcon"), myBomPanel);
            tabbedPane.setFont(FontList.defaultFont);
            tabbedPane.setSelectedIndex(0);
            getContentPane().add("Center",tabbedPane);

            tabbedPane.addTab(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00159")/*변경 BOM*/, appReg.getImageIcon("bomecodetailsIcon"), tmpMyBomEcoPanel);

            tabbedPane.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JTabbedPane tab = (JTabbedPane)e.getSource();
                    if (tab.getSelectedIndex() == 0) {
                        if (myBomPanel == null) {
                            myBomPanel = new MyBomPanel(MyBOMFrame.this, aifapp, vecBom);
                            tabbedPane.removeTabAt(0);
                            tabbedPane.insertTab(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00253")/*신규 BOM*/, appReg.getImageIcon("bomdetailsIcon"), myBomPanel,KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00253")/*신규 BOM*/, 0);
                        }
                        tab.setSelectedIndex(0);
                    } else if(tab.getSelectedIndex() == 1) {
                        if (myBomEcoPanel == null) {
                            myBomEcoPanel = new MyBomEcoPanel(MyBOMFrame.this, aifapp, vecBomEco);
                            tabbedPane.removeTabAt(1);
                            tabbedPane.insertTab(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00159")/*변경 BOM*/, appReg.getImageIcon("bomecodetailsIcon"), myBomEcoPanel,KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00159")/*변경 BOM*/, 1);
                        }
                        tab.setSelectedIndex(1);
                    }
                }
            });

            run();
              frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
              frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void run() {
        pack();
        validate();
        ScreenCenterer scent = new ScreenCenterer();
        Dimension dimCenter = new Dimension(scent.getCenterDim(this));
        setLocation(dimCenter.width, dimCenter.height);

        setVisible(true);
    }

    public Vector getBomList()     {
        Vector itemCodeVec = new Vector();
        try    {
            itemCodeVec = KETBomHelper.service.searchWorkList();
Kogger.debug(getClass(), "================ getBomList ==================");
Kogger.debug(getClass(), "itemCodeVec : " + itemCodeVec.toString());
Kogger.debug(getClass(), "================ getBomList ==================");
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
        }

        return itemCodeVec;
    }

    public void closeBtn_process() {
        int count = getComponentCount();

        for(int i=0; i<count; i++) {
            Component c = getComponent(i);
            this.remove(c);
            c = null;
        }
        super.dispose();
        System.gc();
    }

}
