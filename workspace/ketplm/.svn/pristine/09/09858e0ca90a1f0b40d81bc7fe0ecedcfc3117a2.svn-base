package e3ps.bom.command.bomproperty;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class TotalPropertyDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JButton okBtn, cancelBtn;

    public BOMAssyComponent assyComponent;
    private BOMTreeTableModel model;
    private AbstractAIFUIApplication app;

    private boolean isOK = false;
//    private boolean isChanged = false;
    private boolean isView = true;
    private boolean isProduct = true;

    private JFrame desktop;
    private JTabbedPane centerPanel;
    private JPanel centerPanel2;
    public BOMPropertyDialog bomPropertyPane;
    public SubstitutePropertyDialog substitutePropertyPane;

    public TotalPropertyDialog(BOMAssyComponent component, JFrame desktop, boolean modal, BOMTreeTableModel model, boolean isView,
                                     AbstractAIFUIApplication app, boolean isProduct) {
        super(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00029")/*BOM 속성변경*/ , modal);
        this.desktop = desktop;
        this.model = model;
        this.isView = isView;
        this.app = app;
        this.isProduct = isProduct;        // 제품, 금형 구분 ( true: 제품, false: 금형)

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                confirmAutoSave();
            }
        });

        try {
            this.assyComponent = component;
            jInit();
            this.setSize(660,350);        // BOM 속성변경 팝업 전체 사이즈 지정

            ScreenCenterer scent = new ScreenCenterer();
            Dimension dimCenter = new Dimension(scent.getCenterDim(this));
            this.setLocation(dimCenter.width, dimCenter.height);
            this.setVisible(true);
        } catch(Exception e)    {
            Kogger.error(getClass(), e);
        }
    }

    public void disposeScreen() {
        int count = getComponentCount();
        for(int i=0; i<count; i++) {
              Component c = getComponent(i);
              this.remove(c);
              c = null;
        }
        super.dispose();
        System.gc();
      }

    private void setOK() {
        isOK = true;
    }

    public BOMAssyComponent getChangedAssyComponent() {
        return assyComponent;
    }

    public boolean getOKvalue() {
        return isOK;
    }

    private void confirmAutoSave() {
        if (!isView) {
            if (JOptionPane.showConfirmDialog(this, messageRegistry.getString("save"), "choose one", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (bomPropertyPane.dataValidate() ) {
                    bomPropertyPane.isChanged();
                    substitutePropertyPane.isChanged();

                    if(bomPropertyPane.isChanged || substitutePropertyPane.isChanged) {
                        if(bomPropertyPane.isProduct) {            // 제품인 경우
                            if (bomPropertyPane.setOK() && substitutePropertyPane.setOK()) {
                                setOK();
                            } else {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                return;
                            }
                        } else {                                        // 금형인 경우
                            if (bomPropertyPane.setOK()) {
                                setOK();
                            } else {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                return;
                            }
                        }
                    }
                    disposeScreen();
                }
            }
        }
        disposeScreen();
        setVisible(false);
    }

    protected AbstractAIFUIApplication getApplication() {
        return app;
    }

    private void jInit() {
        Registry appReg = Registry.getRegistry(app);

        bomPropertyPane = new BOMPropertyDialog(assyComponent, desktop, model, isView, this, app, isProduct);
        substitutePropertyPane = new SubstitutePropertyDialog(assyComponent, assyComponent.getSubAssyComponent(), desktop, isView, this, app);

        if (isProduct) {        // 제품인 경우
            centerPanel = new JTabbedPane();
            centerPanel.setTabPlacement(SwingConstants.TOP);
            centerPanel.setFont(FontList.defaultFont);
            centerPanel.addTab(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00028")/*BOM 속성*/, appReg.getImageIcon("bompropertyIcon"), bomPropertyPane);
            centerPanel.addTab(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00131")/*대체부품 정보*/, appReg.getImageIcon("substitutepropertyIcon"), substitutePropertyPane);

            this.getContentPane().add(new JScrollPane(centerPanel), BorderLayout.CENTER);

        } else {                // 금형인 경우
            centerPanel2 = new JPanel();
            centerPanel2.setFont(FontList.defaultFont);
            centerPanel2.add(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00028")/*BOM 속성*/, bomPropertyPane);

            this.getContentPane().add(new JScrollPane(centerPanel2), BorderLayout.CENTER);
        }

        // 하단부 버튼 패널
        JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00306")/*저장*/, appReg.getImageIcon("okIcon"));
        okBtn.setMargin(new Insets(0,5,0,5));
        okBtn.setDefaultCapable(true);
        okBtn.setFont(FontList.defaultFont);
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (bomPropertyPane.dataValidate() ) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    // 바뀐 값이 있어야만 OK 리턴.
                    bomPropertyPane.isChanged();
                    substitutePropertyPane.isChanged();

                    if(bomPropertyPane.isChanged || substitutePropertyPane.isChanged) {
                        if(bomPropertyPane.isProduct) {            // 제품인 경우
                            if (bomPropertyPane.setOK() && substitutePropertyPane.setOK()) {
                                setOK();
                            } else {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                return;
                            }
                        } else {                                        // 금형인 경우
                            if (bomPropertyPane.setOK()) {
                                setOK();
                            } else {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                return;
                            }
                        }
                    }
                    disposeScreen();
                }
            }
        });
        if(isView) { // Search 또는 조회용을 위해서 로딩된 경우에는 OK 버튼을 비활성 시킨다.
            okBtn.setEnabled(false);
        }
        btnFlowPanel.add(okBtn);

        cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
        cancelBtn.setActionCommand("Cancel");
        cancelBtn.setMargin(new Insets(0,5,0,5));
        cancelBtn.setFont(FontList.defaultFont);
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                disposeScreen();
            }
        });
        btnFlowPanel.add(cancelBtn);

        this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
    }
}
