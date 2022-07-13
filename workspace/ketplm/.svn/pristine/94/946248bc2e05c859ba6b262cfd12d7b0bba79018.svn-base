package e3ps.bom;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.ColorList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class BOMStatusPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JLabel workflowStateLbl, workingStateValLbl, levelLbl; //displayLbl, levelLbl, blankLbl, currentOrgLbl, currentOrgValLbl;
    private JLabel versionLbl, versionValueLbl;
    private JLabel qtyLbl, qtyLbl2, qtyValueLbl;
    private JComboBox levelCmb;
    private JButton levelBtn;
    private BtnListener btnListener;
    private JFrame desktop;
    private MainEditorPanel parent;
    private AbstractAIFUIApplication app;
    private Registry appReg;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    private BOMSearchUtilDao dao = new BOMSearchUtilDao();

    private boolean isProduct = false;

    class BtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("Level"))
            {
                try
                {
                    String levelStr = (String)levelCmb.getSelectedItem();
                    Integer levelInt = null;
                    if (levelStr.equalsIgnoreCase("All"))
                    {
                        levelInt = new Integer(20);
                    }
                    else
                    {
                        levelInt = new Integer(levelStr);
                    }

                    int levelNum = levelInt.intValue();
                    if (levelNum <= 0)
                    {
                        MessageBox m = new MessageBox(desktop, messageRegistry.getString("checkLevel"), "Number ERROR", MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                    }
                    else
                    {
                        parent.expandTreeTable(levelInt.intValue());
                    }
                }
                catch (Exception ex)
                {
                    MessageBox m = new MessageBox(desktop, messageRegistry.getString("checkLevel1"), "Number ERROR", MessageBox.ERROR);
                    m.setVisible(true);
                    m.setModal(true);
                }
            }
        }
    }

    public BOMStatusPanel(MainEditorPanel parent, AbstractAIFUIApplication app)
    {
        this.parent = parent;
        this.app = app;
        this.desktop = app.getDesktop();

        try
        {
            jInit();

            // BOM이 Open 되는 경우, 정보를 Update 할 수 있도록 함수 구성.
            setStatusBar();
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    private void jInit() throws Exception
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        appReg = Registry.getRegistry(app);
        //////////////////////////////////////////////////////////////////
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(ColorList.veryVeryLightGreenColor);

        btnListener = new BtnListener();

        JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel01.setBackground(ColorList.veryVeryLightGreenColor);

        JLabel blankLbl = new JLabel("      ");
        JLabel blankLbl1 = new JLabel("      ");
        JLabel blankLbl2 = new JLabel("      ");

        workflowStateLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00024")/*BOM 결재상태*/ + " : ");
        workflowStateLbl.setFont(FontList.shotKeyFont);

        workingStateValLbl = new JLabel();
        workingStateValLbl.setFont(FontList.shotKeyFont);
        workingStateValLbl.setText("None");

        versionLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/ + " : ");
        versionLbl.setFont(FontList.shotKeyFont);

        versionValueLbl = new JLabel();
        versionValueLbl.setFont(FontList.shotKeyFont);
        versionValueLbl.setText("None");

        levelLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/ + " : ");
        levelLbl.setFont(FontList.shotKeyFont);

        String levelObj[] = {"All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
        levelCmb = new JComboBox(levelObj);
        levelCmb.setBackground(Color.white);
        levelCmb.setFont(FontList.shotKeyFont);

        levelBtn = new JButton(appReg.getImageIcon("levelviewIcon"));
        levelBtn.setMargin(new Insets(0,0,0,0));
        levelBtn.setToolTipText(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00046")/*Level 확장*/);
        levelBtn.setActionCommand("Level");
        levelBtn.setOpaque(false); // 투명 버튼 사용
        levelBtn.setBorder(BorderList.emptyBorder1);
        levelBtn.addActionListener(btnListener);

        // 포장수량정보 (제품인 경우만 Display)
        qtyLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00345")/*포장수량*/ + " : ");
        qtyLbl.setFont(FontList.shotKeyFont);

        qtyLbl2 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00122")/*기준수량*/ + " : ");
        qtyLbl2.setFont(FontList.shotKeyFont);

        qtyValueLbl = new JLabel();
        qtyValueLbl.setFont(FontList.shotKeyFont);
        qtyValueLbl.setText("None");

        panel01.add(workflowStateLbl);
        panel01.add(workingStateValLbl);
        panel01.add(blankLbl);
        panel01.add(versionLbl);
        panel01.add(versionValueLbl);
        panel01.add(blankLbl1);
        panel01.add(levelLbl);
        panel01.add(levelCmb);
        panel01.add(levelBtn);
        panel01.add(blankLbl2);
        panel01.add(qtyLbl);
        panel01.add(qtyLbl2);
        panel01.add(qtyValueLbl);
        panel01.setBackground(ColorList.veryVeryLightGreenColor);
        bottomPanel.add(panel01);

        /////////////////////////////////////////////////////////////////////
        add(bottomPanel);
        setBorder(BorderList.raisedBorder);
    }

    public void setStatusBar()
    {
Kogger.debug(getClass(), "================ setStatusBar ==================");
        String strModel = BOMBasicInfoPool.getPublicModelName();
        String strType = BOMBasicInfoPool.getBomHeaderPartType();

        // root node 가 제품인지 금형인지 구분
//        BOMAssyComponent component =  parent.model.getRootNode().getBOMComponent();

        if (!strModel.equalsIgnoreCase("Empty")) {
//            WTPart part;
//            String strType = "";
//            try {
//                part = KETPartHelper.service.getPart(strModel);
//                strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
//            } catch (Exception e) {
//                Kogger.error(getClass(), e);
//            }

            if (PartUtil.isProductType(strType)) {    // 제품인 경우
                isProduct = true;
            } else {
                isProduct = false;
            }
        }

Kogger.debug(getClass(), "strModel : " + strModel);
        // BOM 결재상태 정보 셋팅
        if(strModel.equalsIgnoreCase("Empty"))
        {
            workingStateValLbl.setText("None");
        }
        else
        {
            String bomStatus = BOMBasicInfoPool.getPublicBOMStatusKr();
            bomStatus = bomStatus.toUpperCase();
            bomStatus = bomStatus.replace("_", "");

            workingStateValLbl.setText( CommonUtil.ViewState(bomStatus) );
        }

        // 버전 정보 셋팅
        String version = parent.model.getRootNode().getBOMComponent().getVersionStr();
        if (version != null && !version.equals("")) {
            if (version.indexOf(".") == -1 ) {
                versionValueLbl.setText(version);
            } else {
                versionValueLbl.setText(version.substring(0, version.indexOf(".")));
            }
        } else {
            versionValueLbl.setText("None");
        }

        // 제품인 경우 포장수량 정보 Display
        String strTemp = "";
        boolean isTop = false;
        if (isProduct) {

            // 부품번호가 완제품인지 여부 확인
            if ( strModel != null && !strModel.equals("") )
            {
                strTemp = strModel.substring(0, 1);
                if ( strTemp.equalsIgnoreCase("0") || strTemp.equalsIgnoreCase("1") || strTemp.equalsIgnoreCase("2") || strTemp.equalsIgnoreCase("3") ||
                     strTemp.equalsIgnoreCase("4") || strTemp.equalsIgnoreCase("5") || strTemp.equalsIgnoreCase("6") || strTemp.equalsIgnoreCase("7") ||
                     strTemp.equalsIgnoreCase("8") || strTemp.equalsIgnoreCase("9") || strTemp.equalsIgnoreCase("K") )
                {
                    isTop = true;
                }
            }

            if ( dao.getIsNewBomCode( strModel )  || isTop )
            {
                qtyLbl.setVisible(true);        // 포장수량
                qtyLbl2.setVisible(false);    // 기준수량
                qtyValueLbl.setVisible(true);
            }
            else {
                qtyLbl.setVisible(false);
                qtyLbl2.setVisible(true);
                qtyValueLbl.setVisible(true);
            }

//Kogger.debug(getClass(), "@@@@@@@ Root Component : " +parent.model.getRootNode().getBOMComponent() );
//Kogger.debug(getClass(), "@@@@@@@ Box Quantity : " +parent.model.getRootNode().getBOMComponent().getBoxQuantityDbl() );
            Double boxQuantityDbl = parent.model.getRootNode().getBOMComponent().getBoxQuantityDbl();
            if (boxQuantityDbl != null) {
                qtyValueLbl.setText(boxQuantityDbl + "");
            } else {
                qtyValueLbl.setText("None");
            }
        } else {
            qtyLbl.setVisible(false);
            qtyLbl2.setVisible(false);
            qtyValueLbl.setVisible(false);
        }


Kogger.debug(getClass(), "================ setStatusBar ==================");
    }

}
