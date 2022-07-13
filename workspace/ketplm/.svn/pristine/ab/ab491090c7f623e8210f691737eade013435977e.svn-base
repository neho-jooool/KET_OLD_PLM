package e3ps.bom.command.bomproperty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import wt.part.QuantityUnit;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.ecodetails.EcoDetailsCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMCodeData;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.util.FontList;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Painter;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class BOMPropertyDialog extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private BOMAssyComponent assyComponent;
    private BOMTreeTableModel model;
    public TotalPropertyDialog parent;
    private AbstractAIFUIApplication app;
    BOMRegisterApplicationPanel pnl;
    private JFrame desktop;
    Registry appReg;

    BOMTreeNode selectedNode = null;

    private JLabel itemCodeLbl, descriptionLbl, boxQuantityLbl, unitLbl, statusLbl, quantityLbl, ecoNoLbl; // typeLbl
    private JTextField itemCodeTfl, descriptionTfl, boxQuantityTfl, statusTfl, quantityTfl, ecoNoTfl;
    private JComboBox unitCmb; // typeCmb
    private JButton detailsBtn;

    private JLabel materialLbl, hardnessFromLbl, hardnessToLbl, designDateLbl;
    private JTextField materialTfl, hardnessFromTfl, hardnessToTfl, designDateTfl;

    private boolean isOK = false;
    public boolean isChanged = false;
    private boolean isView = true;
    public boolean isProduct = true;

    private String status = "";
    private Double orgBoxQuantityVal;
    private Double orgQuantityVal;
    private String orgUnit = "";
//    private String orgType = "";
    private String orgMaterial = "";
    private String orgHardnessFrom = "";
    private String orgHardnessTo = "";

//    private Hashtable<String, String> typeHash;
//    private Vector<BOMCodeData> typeVec = new Vector<BOMCodeData>();

    private Hashtable<String, String> unitTypeHash;
    private Vector<String[]> unitSameTypeVec;
    private Vector<BOMCodeData> unitTypeVec = new Vector<BOMCodeData>();

    boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

    public BOMPropertyDialog(BOMAssyComponent component, JFrame desktop, BOMTreeTableModel model, boolean isView,
                                     TotalPropertyDialog parent, AbstractAIFUIApplication app, boolean isProduct) {
        this.desktop = desktop;
        this.model = model;
        this.isView = isView;
        this.parent = parent;
        this.app = app;
        this.isProduct = isProduct;        // 제품, 금형 구분 ( true: 제품, false: 금형)

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        appReg = Registry.getRegistry(app);

        try {
            this.assyComponent = component;

            unitTypeHash = new Hashtable<String, String>();
//            typeHash = new Hashtable<String, String>();

            // 단위 필드 ComboBox 에 추가될 정보
            String strDesc = null;
            String strCode = null;
            String[] strValue = null;
            QuantityUnit [] qUnit = QuantityUnit.getQuantityUnitSet();
            for( int inx = 0; inx < qUnit.length; inx++ ) {
                strCode = qUnit[inx].toString();

                if (strCode != null && !strCode.equals("")) {
                    if (strCode.equals(assyComponent.getUitStr())) {
                        strDesc = qUnit[inx].getLongDescription();
                        if( strDesc != null && !strDesc.equals("") ) {
                            if (strDesc.equalsIgnoreCase("NA")) {            // NA인 경우는 호환가능한 다른 단위가 존재하지 않는 것임
                                BOMCodeData codeData = new BOMCodeData(strCode, qUnit[inx].getDisplay());
                                unitTypeHash.put("KET_" + strCode ,qUnit[inx].getDisplay());
                                unitTypeVec.addElement(codeData);
                                break;
                            } else {                                                // 호환 가능한 단위를 저장
                                unitSameTypeVec = getSameUnitValue(strDesc);
                                if ( unitSameTypeVec != null && unitSameTypeVec.size() > 0 ) {
                                    for (int jnx = 0; jnx < unitSameTypeVec.size(); jnx++) {
                                        strValue = unitSameTypeVec.elementAt(jnx);
                                        BOMCodeData codeData = new BOMCodeData(strValue[0], strValue[1]);
                                        unitTypeHash.put(strValue[0] , strValue[1]);
                                        unitTypeVec.addElement(codeData);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            // [2011-03-09] 윤도혁J  요구사항 반영 :: 타입 필드 삭제
            // 구분 필드 ComboBox 에 추가될 정보 (공용부품 여부)
//            String[] keyStr = {"new", "co"};
//            String[] valStr = {"New", "C/O"};
//            for (int inx = 0; inx < keyStr.length; inx++) {
//                BOMCodeData codeData = new BOMCodeData(keyStr[inx], valStr[inx]);
//                typeHash.put(keyStr[inx], valStr[inx]);
//                typeVec.addElement(codeData);
//            }

            jInit();

            setComponentData();
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // 해당 단위와 같은 분류에 속하는 단위 정보(Code, Display Value)를 리턴
    private Vector<String[]> getSameUnitValue(String strDesc) {
        Vector<String[]> rtnVec = new Vector<String[]>();

        QuantityUnit [] qUnit = QuantityUnit.getQuantityUnitSet();
        for( int inx = 0; inx < qUnit.length; inx++ ) {
            if (qUnit[inx].getLongDescription().equalsIgnoreCase(strDesc)) {
                String[] strValue = new String[2];
                strValue[0] = qUnit[inx].toString();
                strValue[1] = qUnit[inx].getDisplay();
                rtnVec.addElement(strValue);
            }
        }
        return rtnVec;
    }

    private void setComponentData() {
        if (assyComponent.getItemCodeStr() != null) {
            itemCodeTfl.setText(assyComponent.getItemCodeStr().trim());
        }
        if (assyComponent.getDescStr() != null) {
            descriptionTfl.setText(assyComponent.getDescStr());
        }

        // 제품인 경우 정보
        if (isProduct) {

            // 기준수량 (소숫점 제거하고 표현)
            String boxQuantity = "";
            if (assyComponent.getBoxQuantityDbl() != null) {
                boxQuantity = assyComponent.getBoxQuantityDbl() + "";
                if (!boxQuantity.equals("") && boxQuantity.indexOf(".") >= 0) {
                    boxQuantity = boxQuantity.substring(0, boxQuantity.indexOf("."));
                }
                boxQuantityTfl.setText(boxQuantity);
            }

            // 단위
            if (assyComponent.getUitStr() != null) {

                String strItemCode = itemCodeTfl.getText().trim();
                String strTemp = "";
                String code = "";
                if ( strItemCode != null && !strItemCode.equals("") )
                {
                    strTemp =     strItemCode.substring(0, 3);
                }

                // 선택한 부품이 원재료인 경우 단위를 'G'으로 셋팅한다
                if ( strTemp.equals("R20") || strTemp.equals("R10") || strTemp.equals("S20") || strTemp.equals("S10")  || strTemp.equals("S18"))
                {
                    code = "KET_G";
                }
                else
                {
                    code = assyComponent.getUitStr().trim();
                }

                String val = (String)unitTypeHash.get(code);
                if (val == null) {
                    val = "";
                }
                BOMCodeData codeData = new BOMCodeData(code, val.trim());
                unitCmb.setSelectedItem(codeData);
            }

            // 결재상태
            if (assyComponent.getStatusKrStr() != null) {
                statusTfl.setText(assyComponent.getStatusKrStr());
                status = assyComponent.getStatusStr();
            }

            // 타입 [2011-03-09] 윤도혁J  요구사항 반영 :: 타입 필드 삭제
//            if (assyComponent.getBomType() != null) {
//                String code = assyComponent.getBomType().trim();
//                String val = (String)typeHash.get(code);
//                if (val == null) {
//                    val = "";
//                }
//                BOMCodeData codeData = new BOMCodeData(code, val.trim());
//                typeCmb.setSelectedItem(codeData);
//            }
        }

        // 소요수량
        String quantity = "";
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);
        if (assyComponent.getQuantityDbl() != null) {

            if (isProduct) {    // 제품인 경우 소숫점 표현
                quantityTfl.setText(format.format(assyComponent.getQuantityDbl()));
            } else {
                // [2011-03-04] 임승영D 요구사항 반영 :: 금형인 경우 소숫점을 지운다
                quantity = assyComponent.getQuantityDbl() + "";
                if (!quantity.equals("") && quantity.indexOf(".") >= 0) {
                    quantity = quantity.substring(0, quantity.indexOf("."));
                }
                quantityTfl.setText(quantity);
            }
        }

        if (!bomGubunFlag) {        // 설계변경인 경우
            ecoNoTfl.setText(BOMBasicInfoPool.getBomEcoNumber().trim());
            detailsBtn.setEnabled(true);
        } else {
            ecoNoTfl.setText("");
        }

        // 금형인 경우 정보
        if (!isProduct) {
            if (assyComponent.getMaterialStr() != null) {
                materialTfl.setText(assyComponent.getMaterialStr());
            }
            if (assyComponent.getHardnessFrom() != null) {
                hardnessFromTfl.setText(assyComponent.getHardnessFrom());
            }
            if (assyComponent.getHardnessTo() != null) {
                hardnessToTfl.setText(assyComponent.getHardnessTo());
            }
            if (assyComponent.getDesignDate() != null) {
                designDateTfl.setText(assyComponent.getDesignDate());
            }
        }

        orgBoxQuantityVal = assyComponent.getBoxQuantityDbl();
        if (orgBoxQuantityVal == null) {
            orgBoxQuantityVal = new Double(0);
        }

        orgQuantityVal = assyComponent.getQuantityDbl();
        if (orgQuantityVal == null) {
            orgQuantityVal = new Double(0);
        }
        orgUnit = "KET_" + assyComponent.getUitStr().trim().toUpperCase();
        if (orgUnit == null) {
            orgUnit = "";
        }
        // [2011-03-09] 윤도혁J  요구사항 반영 :: 타입 필드 삭제
//        orgType = assyComponent.getBomType();
//        if (orgType == null) {
//            orgType = "";
//        }
        orgMaterial = assyComponent.getMaterialStr();
        if (orgMaterial == null) {
            orgMaterial = "";
        }
        orgHardnessFrom = assyComponent.getHardnessFrom();
        if (orgHardnessFrom == null) {
            orgHardnessFrom = "";
        }
        orgHardnessTo = assyComponent.getHardnessTo();
        if (orgHardnessTo == null) {
            orgHardnessTo = "";
        }

        quantityTfl.requestFocus();
    }

    public void disposeScreen() {
        int count = getComponentCount();
        for(int i=0; i<count; i++) {
              Component c = getComponent(i);
              this.remove(c);
              c = null;
        }
        System.gc();
      }

    // ECO 조회 버튼 클릭 시
    public void detailsBtn_process() {
        try {
            EcoDetailsCmd ecoDetails = new EcoDetailsCmd(desktop, app);
            ecoDetails.run();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // 저장 버튼 클릭 시
    public boolean setOK() {
        String strQuantity= "";

        try {
            strQuantity = quantityTfl.getText().trim();
            if ( strQuantity.indexOf(",") >= 0 ) {
                strQuantity = strQuantity.replaceAll(",", "");
            }
            assyComponent.setQuantityDbl(new Double(strQuantity));
            assyComponent.setStatusStr(status);
            if (isProduct) {
                assyComponent.setBoxQuantityDbl(new Double(boxQuantityTfl.getText().trim()));
                assyComponent.setStatusKrStr(statusTfl.getText().trim());
                assyComponent.setUitStr(((BOMCodeData)unitCmb.getSelectedItem()).getCode());
//                assyComponent.setBomType(((BOMCodeData)typeCmb.getSelectedItem()).getCode());
            }else {
                assyComponent.setMaterialStr(materialTfl.getText());
                assyComponent.setHardnessFrom(hardnessFromTfl.getText());
                assyComponent.setHardnessTo(hardnessToTfl.getText());
                assyComponent.setDesignDate(designDateTfl.getText());
            }
            isOK = true;
        } catch (Exception e) {
            isOK = false;
            return false;
        }
        return true;
    }

    public BOMAssyComponent getChangedAssyComponent() {
        return assyComponent;
    }

    public boolean dataValidate() {

        try {
//            int cnt = 0;
//            char value;
            Double checkDbl;
            String strQuantity = "";

            if (isProduct) {
                checkDbl = new Double(boxQuantityTfl.getText().trim());
                if(checkDbl.doubleValue() == 0) {
                    MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00123", 0)/*기준수량은 '{0}'이 될 수 없습니다.*/, "Error", 0);
                    m.setModal(true);
                    m.setVisible(true);

                    return false;
                }
            }

            strQuantity = quantityTfl.getText().trim();
            if ( strQuantity.indexOf(",") >= 0 ) {
                strQuantity = strQuantity.replaceAll(",", "");
            }
            checkDbl = new Double(strQuantity);
//            cnt = quantityTfl.getText().length();
            if(checkDbl.doubleValue() == 0) {
                MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00229", 0)/*소요수량은 '{0}'이 될 수 없습니다.*/, "Error", 0);
                m.setModal(true);
                m.setVisible(true);

                return false;
            }

// [2011-01-17] 윤도혁 주임 요구사항 반영 :: 수량에 (-) 입력 가능하도록 주석처리함
//            for (int inx = 0; inx < cnt; inx++) {
//                value = quantityTfl.getText().charAt(inx);
//
//                if ((value == '-')) {
//                    MessageBox m = new MessageBox(desktop, messageRegistry.getString("quantityFormatError"), "Error", 0);
//                    m.setModal(true);
//                    m.setVisible(true);
//
//                    return false;
//                }
//            }

        } catch (NumberFormatException e) {
            quantityTfl.setCaretPosition(0);
            quantityTfl.selectAll();
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("quantityFormatError"), "Error", 0);
            m.setModal(true);
            m.setVisible(true);

            return false;
        }
        return true;
    }

    public boolean getOKvalue() {
        if (isChanged) {
            return isOK;
        }
        return false;
    }

    public void isChanged() {

        String quantity = quantityTfl.getText().trim();
        if ( quantity.indexOf(",") >= 0 ){
            quantity = quantity.replaceAll(",", "");
        }
        Double quantityDbl = new Double(quantity);
        if (isProduct) {        // 제품인 경우
            String boxQuantity = boxQuantityTfl.getText().trim();
            Double boxQuantityDbl = new Double(boxQuantity);
            BOMCodeData unitCodeData = (BOMCodeData)unitCmb.getSelectedItem();
//            BOMCodeData typeCodeData = (BOMCodeData)typeCmb.getSelectedItem();
            if (unitCodeData.getCode().equalsIgnoreCase(orgUnit) &&
//                typeCodeData.getCode().equalsIgnoreCase(orgType) &&
                quantityDbl.doubleValue() == orgQuantityVal.doubleValue() &&
                boxQuantityDbl.doubleValue() == orgBoxQuantityVal.doubleValue() ) {
                isChanged = false;
            } else {
                isChanged = true;
            }
        } else {                // 금형인 경우
            String strMaterial = materialTfl.getText().trim();
            String strHardnessFrom = hardnessFromTfl.getText().trim();
            String strHardnessTo = hardnessToTfl.getText().trim();

            if ( (quantityDbl.doubleValue() == orgQuantityVal.doubleValue()) && strMaterial.equals(orgMaterial) &&
                  strHardnessFrom.equals(orgHardnessFrom) && strHardnessTo.equals(orgHardnessTo) ) {
                isChanged = false;
            } else {
                isChanged = true;
            }
        }
    }

    private void jInit() {

        this.setLayout(new BorderLayout());

        ////////////////////////////////////////////////////////////
        JPanel totalPanel = new JPanel(new BorderLayout());

        JPanel propertyPanel01 = new JPanel();
        propertyPanel01.setLayout(new BoxLayout(propertyPanel01, BoxLayout.Y_AXIS));
        propertyPanel01.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00119")/*기본정보*/, 0, 0, FontList.defaultFont));

        // 부품번호 Panel
        JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemCodeLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : ");
        itemCodeLbl.setPreferredSize(new Dimension(100,21));
        itemCodeLbl.setHorizontalAlignment(JLabel.RIGHT);
        itemCodeLbl.setFont(FontList.defaultFont);
        itemCodeLbl.setEnabled(false);
        panel01.add(itemCodeLbl);

        itemCodeTfl = new JTextField(17);
        itemCodeTfl.setPreferredSize(new Dimension(150, 21));
        itemCodeTfl.setEnabled(false);
        itemCodeTfl.setFont(FontList.defaultFont);
        panel01.add(itemCodeTfl);

        // [2011-03-07] 임승영D 요구사항 반영 :: 제품 BOM 속성 추가 '기준수량'
        if (isProduct) {
            boxQuantityLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00122")/*기준수량*/ + " : ");
            boxQuantityLbl.setPreferredSize(new Dimension(100,21));
            boxQuantityLbl.setHorizontalAlignment(JLabel.RIGHT);
            boxQuantityLbl.setFont(FontList.defaultFont);
//            if(isView) {
                boxQuantityLbl.setEnabled(false);
//            }
            panel01.add(boxQuantityLbl);

            boxQuantityTfl = new JTextField(17) {
                private static final long serialVersionUID = 1L;
                public void paint(Graphics g)    {
                    super.paint(g);
                    Painter.paintIsRequired(this, g);
                }
            };
            boxQuantityTfl.setPreferredSize(new Dimension(150, 21));
            boxQuantityTfl.setFont(FontList.defaultFont);
            boxQuantityTfl.addKeyListener(new KeyListener() {
                // 숫자만 입력 가능하도록 함(소수점, "-" 포함)
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if ( ! (Character.isDigit(c) || Character.toString(c).equals(".") || Character.toString(c).equals("-")) ) {
                        e.consume();
                        return;
                    }
                }
                public void keyReleased(KeyEvent e) {
                }
                public void keyPressed(KeyEvent e) {
                }
            });
//            if(isView) {
                boxQuantityTfl.setEnabled(false);
//            }
            panel01.add(boxQuantityTfl);
        }
        propertyPanel01.add(panel01);


        // 부품명 Panel
        JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descriptionLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/ + " : ");
        descriptionLbl.setPreferredSize(new Dimension(100,21));
        descriptionLbl.setHorizontalAlignment(JLabel.RIGHT);
        descriptionLbl.setFont(FontList.defaultFont);
        descriptionLbl.setEnabled(false);
        panel02.add(descriptionLbl);

        descriptionTfl = new JTextField();
        descriptionTfl.setPreferredSize(new Dimension(425, 21));
        descriptionTfl.setEnabled(false);
        descriptionTfl.setFont(FontList.defaultFont);
        panel02.add(descriptionTfl);

        propertyPanel01.add(panel02);

        JPanel propertyPanel02 = new JPanel();
        propertyPanel02.setLayout(new BoxLayout(propertyPanel02, BoxLayout.Y_AXIS));
        propertyPanel02.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00218")/*상세정보*/, 0, 0, FontList.defaultFont));

        // 제품 :: 소요수량 / 단위 Panel
        // 금형 :: 소요수량 / 재질
        JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00228")/*소요수량*/ + " : ");
        quantityLbl.setPreferredSize(new Dimension(100,21));
        quantityLbl.setHorizontalAlignment(JLabel.RIGHT);
        quantityLbl.setFont(FontList.defaultFont);
        if(isView) {
            quantityLbl.setEnabled(false);
        }
        panel03.add(quantityLbl);

        quantityTfl = new JTextField(17) {
            private static final long serialVersionUID = 1L;
            public void paint(Graphics g)    {
                super.paint(g);
                Painter.paintIsRequired(this, g);
            }
        };
        quantityTfl.setPreferredSize(new Dimension(150, 21));
        quantityTfl.setFont(FontList.defaultFont);
        quantityTfl.addKeyListener(new KeyListener() {
            // 숫자만 입력 가능하도록 함(소수점, "-" 포함)
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if ( ! (Character.isDigit(c) || Character.toString(c).equals(".") || Character.toString(c).equals("-")) ) {
                    e.consume();
                    return;
                }
            }
            public void keyReleased(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
            }
        });
        if(isView) {
            quantityTfl.setEnabled(false);
        }
        panel03.add(quantityTfl);

        if (isProduct) {    // 제품인 경우
            unitLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/ + " : ");
            unitLbl.setPreferredSize(new Dimension(100,21));
            unitLbl.setHorizontalAlignment(JLabel.RIGHT);
            unitLbl.setFont(FontList.defaultFont);
            unitLbl.setEnabled(false);
            panel03.add(unitLbl);

            unitCmb = new JComboBox(unitTypeVec) {
                private static final long serialVersionUID = 1L;
                public void paint(Graphics g)    {
                    super.paint(g);
                    Painter.paintIsRequired(this, g);
                }
            };
            unitCmb.setPreferredSize(new Dimension(155, 21));
            unitCmb.setFont(FontList.defaultFont);
            unitCmb.setBackground(Color.white);
            if(isView) {
                unitCmb.setEnabled(false);
            }
            panel03.add(unitCmb);

        }else {            // 금형인 경우
            materialLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/ + " : ");
            materialLbl.setPreferredSize(new Dimension(100,21));
            materialLbl.setHorizontalAlignment(JLabel.RIGHT);
            materialLbl.setFont(FontList.defaultFont);
            panel03.add(materialLbl);

            materialTfl = new JTextField(17);
            materialTfl.setPreferredSize(new Dimension(150, 21));
            materialTfl.setFont(FontList.defaultFont);
            if(isView) {
                materialTfl.setEnabled(false);
            }
            panel03.add(materialTfl);
        }
        propertyPanel02.add(panel03);

        // 제품 :: 결재상태 / 구분 Panel
        // 금형 :: 경도(From) / 경도(To)
        JPanel panel04 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        if (isProduct) {
            statusLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/ + " : ");
            statusLbl.setPreferredSize(new Dimension(100,21));
            statusLbl.setHorizontalAlignment(JLabel.RIGHT);
            statusLbl.setFont(FontList.defaultFont);
            statusLbl.setEnabled(false);
            panel04.add(statusLbl);

            statusTfl = new JTextField(17);
            statusTfl.setPreferredSize(new Dimension(150, 21));
            statusTfl.setEnabled(false);
            statusTfl.setFont(FontList.defaultFont);
            panel04.add(statusTfl);

            // [2011-03-09] 윤도혁J  요구사항 반영 :: 타입 필드 삭제
//            typeLbl = new JLabel("구분 : ");
//            typeLbl.setPreferredSize(new Dimension(100,21));
//            typeLbl.setHorizontalAlignment(JLabel.RIGHT);
//            typeLbl.setFont(FontList.defaultFont);
//            if(isView) {
//                typeLbl.setEnabled(false);
//            }
//            panel04.add(typeLbl);
//
//            typeCmb = new JComboBox(typeVec);
//            typeCmb.setPreferredSize(new Dimension(155, 21));
//            typeCmb.setFont(FontList.defaultFont);
//            typeCmb.setBackground(Color.white);
//            if(isView) {
//                typeCmb.setEnabled(false);
//            }
//            panel04.add(typeCmb);

            ecoNoLbl = new JLabel("ECO No : ");
            ecoNoLbl.setPreferredSize(new Dimension(100,21));
            ecoNoLbl.setHorizontalAlignment(JLabel.RIGHT);
            ecoNoLbl.setFont(FontList.defaultFont);
            ecoNoLbl.setEnabled(false);
            panel04.add(ecoNoLbl);

            ecoNoTfl = new JTextField(17);
            ecoNoTfl.setPreferredSize(new Dimension(150, 21));
            ecoNoTfl.setFont(FontList.defaultFont);
            ecoNoTfl.setEnabled(false);
            panel04.add(ecoNoTfl);

            detailsBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00322")/*조회*/, appReg.getImageIcon("bomecodetailsIcon"));
            detailsBtn.setActionCommand("Details");
            detailsBtn.setFont(FontList.defaultFont);
            detailsBtn.setMargin(new Insets(0,5,0,5));
            if((ecoNoTfl.getText() == null ? "" : ecoNoTfl.getText().trim()).equals("")) {
                detailsBtn.setEnabled(false);
            } else {
                detailsBtn.setEnabled(true);
            }
            detailsBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("Details")) {
                        detailsBtn_process();
                    }
                }
            });

            panel04.add(detailsBtn);


        } else {
            hardnessFromLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/ + " : ");
            hardnessFromLbl.setPreferredSize(new Dimension(100,21));
            hardnessFromLbl.setHorizontalAlignment(JLabel.RIGHT);
            hardnessFromLbl.setFont(FontList.defaultFont);
            panel04.add(hardnessFromLbl);

            hardnessFromTfl = new JTextField(17);
            hardnessFromTfl.setPreferredSize(new Dimension(150, 21));
            hardnessFromTfl.setFont(FontList.defaultFont);
            hardnessFromTfl.addKeyListener(new KeyListener() {
                // 숫자만 입력 가능하도록 함
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if (!Character.isDigit(c)) {
                        e.consume();
                        return;
                    }
                }
                public void keyReleased(KeyEvent e) {
                }
                public void keyPressed(KeyEvent e) {
                }
            });
            if(isView) {
                hardnessFromTfl.setEnabled(false);
            }
            panel04.add(hardnessFromTfl);

            hardnessToLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/ + " : ");
            hardnessToLbl.setPreferredSize(new Dimension(100,21));
            hardnessToLbl.setHorizontalAlignment(JLabel.RIGHT);
            hardnessToLbl.setFont(FontList.defaultFont);
            panel04.add(hardnessToLbl);

            hardnessToTfl = new JTextField(17);
            hardnessToTfl.setPreferredSize(new Dimension(150, 21));
            hardnessToTfl.setFont(FontList.defaultFont);
            hardnessToTfl.addKeyListener(new KeyListener() {
                // 숫자만 입력 가능하도록 함
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if (!Character.isDigit(c)) {
                        e.consume();
                        return;
                    }
                }
                public void keyReleased(KeyEvent e) {
                }
                public void keyPressed(KeyEvent e) {
                }
            });
            if(isView) {
                hardnessToTfl.setEnabled(false);
            }
            panel04.add(hardnessToTfl);
        }
        propertyPanel02.add(panel04);

        // 제품 :: ECO No Panel
        // 금형 :: 설계일자 / ECO No Panel
        JPanel panel05 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        if (!isProduct) {
            designDateLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/ + " : ");
            designDateLbl.setPreferredSize(new Dimension(100,21));
            designDateLbl.setHorizontalAlignment(JLabel.RIGHT);
            designDateLbl.setFont(FontList.defaultFont);
            designDateLbl.setEnabled(false);
            panel05.add(designDateLbl);

            designDateTfl = new JTextField(17);
            designDateTfl.setPreferredSize(new Dimension(150, 21));
            designDateTfl.setFont(FontList.defaultFont);
            designDateTfl.setEnabled(false);
            panel05.add(designDateTfl);

            ecoNoLbl = new JLabel("ECO No : ");
            ecoNoLbl.setPreferredSize(new Dimension(100,21));
            ecoNoLbl.setHorizontalAlignment(JLabel.RIGHT);
            ecoNoLbl.setFont(FontList.defaultFont);
            ecoNoLbl.setEnabled(false);
            panel05.add(ecoNoLbl);

            ecoNoTfl = new JTextField(17);
            ecoNoTfl.setPreferredSize(new Dimension(150, 21));
            ecoNoTfl.setFont(FontList.defaultFont);
            ecoNoTfl.setEnabled(false);
            panel05.add(ecoNoTfl);

            detailsBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00322")/*조회*/, appReg.getImageIcon("bomecodetailsIcon"));
            detailsBtn.setActionCommand("Details");
            detailsBtn.setFont(FontList.defaultFont);
            detailsBtn.setMargin(new Insets(0,5,0,5));
            if((ecoNoTfl.getText() == null ? "" : ecoNoTfl.getText().trim()).equals("")) {
                detailsBtn.setEnabled(false);
            } else {
                detailsBtn.setEnabled(true);
            }
            detailsBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("Details")) {
                        detailsBtn_process();
                    }
                }
            });

            panel05.add(detailsBtn);

            propertyPanel02.add(panel05);
        }

        totalPanel.add(propertyPanel01, BorderLayout.NORTH);
        totalPanel.add(propertyPanel02, BorderLayout.CENTER);

        this.add(totalPanel, BorderLayout.NORTH);
    }
}
