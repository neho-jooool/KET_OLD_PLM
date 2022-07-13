package e3ps.bom.common;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.util.ColorList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.Utility;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;
import gui.JMouseWheelDialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ItemTableDialog extends JMouseWheelDialog {

    private static final long serialVersionUID = 1L;
    private BtnListener btnListener;
    private JButton okBtn, cancelBtn;
    private Vector resultList;

    private boolean isOK = false;
    private AbstractAIFUIApplication app;
    private JFrame parent;
    private JTable m_table;
    private ItemTableModel m_data;

    private JLabel descLbl;
    private BOMAssyComponent selectedComponent = null;

    class BtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("OK"))
            {
                if (!setOK())
                    return;
                disposeScreen();
            }
            else
            {
                disposeScreen();
            }
        }
    }

    class TableMouseAdapter extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2)
            {
                if (!setOK())
                    return;
                disposeScreen();
            }
        }
    }

    public ItemTableDialog(JFrame parent, Vector resultList, AbstractAIFUIApplication app)
    {
        super(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00177")/*부품 검색결과*/ , true);
        this.parent = parent;
        this.resultList = resultList;
        this.app = app;

        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                disposeScreen();
            }
        });

        try
        {
            jInit();

            setComponentData();
            setSize(600,380);

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
        // 버튼 리스너 정의
        btnListener = new BtnListener();
        Registry appReg = Registry.getRegistry(app);

        // 01 번째.
        JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));   // Lable 패널
        descLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00055", resultList.size())/*검색결과 : {0}개*/, JLabel.LEFT); // 설명
        descLbl.setForeground(ColorList.darkBlue);
        descLbl.setFont(FontList.defaultFont);
        panel01.add(descLbl);
        this.getContentPane().add(panel01, BorderLayout.NORTH);

        ///////////////////////////////////////////////////////////////////////////////////

        m_data = new ItemTableModel();

        m_table = new JTable();
        m_table.setAutoCreateColumnsFromModel(false);
        m_table.setModel(m_data);
        m_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Cell Renderer 와 Cell Editor 를 연결시켜준다.
        for (int k = 0; k < ItemTableModel.m_columns.length; k++)
        {
            TableCellRenderer renderer;
            ItemTableCellRenderer itemRenderer = new ItemTableCellRenderer(app);
            itemRenderer.setHorizontalAlignment(ItemTableModel.m_columns[k].m_alignment);
            renderer = itemRenderer;

            TableCellEditor editor = null;
            TableColumn column = new TableColumn(k, ItemTableModel.m_columns[k].m_width, renderer, editor);
            m_table.addColumn(column);
        }

        m_table.addMouseListener(new TableMouseAdapter());

        JTableHeader header = m_table.getTableHeader();
        header.setUpdateTableInRealTime(false);

        JScrollPane ps = new JScrollPane();
        ps.setSize(600, 350);
        ps.getViewport().add(m_table);

        this.getContentPane().add(ps, BorderLayout.CENTER);
        ///////////////////////////////////////////////////////////////////////////////////
        // 하단부 버튼 패널
        JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, appReg.getImageIcon("okIcon"));
        okBtn.setMargin(new Insets(0,3,0,3));
        okBtn.setActionCommand("OK");
        okBtn.setDefaultCapable(true);
        okBtn.setFont(FontList.defaultFont);
        okBtn.addActionListener(btnListener); // setting action listener ... click event
        btnFlowPanel.add(okBtn);

        cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
        cancelBtn.setActionCommand("Cancel");
        cancelBtn.addActionListener(btnListener);
        cancelBtn.setMargin(new Insets(0,3,0,3));
        cancelBtn.setFont(FontList.defaultFont);
        btnFlowPanel.add(cancelBtn);

        this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
    }

    public boolean getOK()
    {
        return isOK;
    }

    private void disposeScreen()
    {
        int count = getComponentCount();
        for(int i=0; i<count; i++) {
              Component c = getComponent(i);
              this.remove(c);
              c = null;
        }
        super.dispose();
        System.gc();
      }

    private boolean setOK()
    {
        selectedComponent = m_data.getComponent(m_table.getSelectedRow());

        isOK = true;
        return true;
    }

    public BOMAssyComponent getSelectedComponent()
    {
        return selectedComponent;
    }

    private void setComponentData()
    {
        for(long i=0; i<resultList.size(); i++)
        {
            ItemTableData data = (ItemTableData)resultList.elementAt((int)i);

            BOMAssyComponent bomComponent = new BOMAssyComponent(Utility.checkNVL(data.getItemCode()));
            bomComponent.setDescStr(data.getDesc());
            bomComponent.setUitStr(data.getUitStr());
            bomComponent.setUserNmStr(data.getUserNmStr());
            bomComponent.setStartDate(data.getStartDate());
            bomComponent.setStatusKrStr(data.getStatusKrStr());


            m_data.insert(bomComponent);
        }
    }
}


