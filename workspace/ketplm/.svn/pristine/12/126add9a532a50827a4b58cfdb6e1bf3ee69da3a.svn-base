package e3ps.bom.command.bomvalidation;

import e3ps.bom.command.endworking.EndWorkingCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;
import gui.JMouseWheelFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BOMValidationResultFrame extends JMouseWheelFrame
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private AbstractAIFUIApplication app;
    private BOMValidationResultTable table;
    private BOMValidationResultTableModel model;
    private JFrame desktop;

    private JButton closeBtn;
    private Vector data;

    private String strValidationForEnd = BOMBasicInfoPool.getValidationForEnd();
    private int errorcount = 0;
    private int warningcount = 0;

    public BOMValidationResultFrame()
    {
        super("");
    }

    public BOMValidationResultFrame(AbstractAIFUIApplication app, Vector data, int errorCnt, int warningCnt)
    {
        this();
        this.app = app;
        this.desktop = app.getDesktop();
        this.data = data;

        errorcount = errorCnt;
        warningcount = warningCnt;

        setGUI();
        setEvent();
        setVisible(true);
        BOMBasicInfoPool.setValidationForEnd("");
    }

    private void setGUI()
    {
        Registry appReg = Registry.getRegistry(app);

        JPanel contentPane = (JPanel) this.getContentPane();

        JPanel buttonPanel = new JPanel();

        closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
        closeBtn.setFont(FontList.defaultFont);
        closeBtn.setMargin(new Insets(0,5,0,5));

        Vector names = new Vector();
        names.add("SEQ");
        names.add(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00343")/*타입*/);
        names.add(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/);
        names.add(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/);
        names.add(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00066")/*결과*/);

        table = new BOMValidationResultTable(this, app);
        BOMValidationResultTableModel model = new BOMValidationResultTableModel(names, data);
        table.setModel(model);

        JScrollPane jspane = new JScrollPane(table);
        table.updateTableView();
        table.sizeColumnsToFit(JTable.AUTO_RESIZE_ALL_COLUMNS );
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(800,35));
        buttonPanel.add(closeBtn);

        contentPane.setLayout(new BorderLayout());
        contentPane.add(jspane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00020")/*BOM 검증 결과 목록*/);
        setResizable(true);
        setSize(683,400);

        ScreenCenterer scent = new ScreenCenterer();
        Dimension dimCenter = new Dimension(scent.getCenterDim(this));
        setLocation(dimCenter.width, dimCenter.height);
    }

    private void setEvent()
    {
        closeBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);

                if(strValidationForEnd.equals("LAST"))
                {
                    if( BOMBasicInfoPool.getHasErrorInValidation() || BOMBasicInfoPool.getBomValidationResult() == false )
                    {
                        MessageBox m = new MessageBox(desktop, messageRegistry.getString("valid1"), "Error", MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                        return;
                    }
                    else
                    {
                        try
                        {
                            EndWorkingCmd cmd = new EndWorkingCmd(desktop, app);
                            cmd.executeModal();
                        }
                        catch (Exception ex)
                        {
                            Kogger.error(getClass(), ex);
                        }
                    }
                }
                else if(strValidationForEnd.equals("NOTLAST"))
                {
                    try{
                      EndWorkingCmd cmd = new EndWorkingCmd(desktop, app);
                      cmd.executeModal();
                    }
                    catch (Exception ex)
                    {
                        Kogger.error(getClass(), ex);
                    }
                }

                dispose();
            }
        });
    }

    public void loadWarningMessage(String message)
    {
        MessageBox messagebox = new MessageBox(this.desktop, message, "Warning", MessageBox.WARNING);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }
}
