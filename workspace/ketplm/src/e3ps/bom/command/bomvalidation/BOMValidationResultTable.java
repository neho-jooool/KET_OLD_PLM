package e3ps.bom.command.bomvalidation;

import java.awt.Rectangle;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;

public  class BOMValidationResultTable extends JTable
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private AbstractAIFUIApplication app;
    public int lastSelectedRow;
    public boolean isInserted;
    private JFrame frame;

    class TableSelectionListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();

            if (lsm.isSelectionEmpty())
            {
                loadWarningMessage(messageRegistry.getString("valid2"));
            }
            else
            {
                BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
                int rowNum = getSelectedRow();
                int colNum = getSelectedColumn();
                startMouseOperation(rowNum, colNum);
            }
        }
    }

    public BOMValidationResultTable(JFrame frame, AbstractAIFUIApplication app)
    {
        super();
        this.frame = frame;
        this.app = app;

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getSelectionModel().addListSelectionListener(new TableSelectionListener());
    }

    public BOMValidationResultTable()
    {
    }

    public void updateTableView()
    {
        getColumn("SEQ").setMaxWidth(40);
        getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00343")/*타입*/).setMaxWidth(60);
        getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/).setMinWidth(100);
        getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/).setMinWidth(100);
        getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00066")/*결과*/).setMinWidth(372);

        for(int inx=0;inx<getColumnCount();inx++)
        {
            setDefaultRenderer(getColumnClass(inx),new BOMValidationTableCellRenderer());
        }
    }

    private void startMouseOperation(int selectedRow, int selectedColumn)
    {
        String sequence = "";
        BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        JTreeTable treeTable = bomPanel.getTreeTable();
        BOMTreeTableModel model = (BOMTreeTableModel)bomPanel.getTreeTableModel();
        BOMTreeNode rootNode = model.getRootNode();
        Enumeration enum0 = rootNode.preorderEnumeration();
        sequence = getValueAt(selectedRow, 0).toString().trim();

        while ( enum0.hasMoreElements() )
        {
            BOMTreeNode allListNode = (BOMTreeNode)enum0.nextElement();
            BOMAssyComponent bomassy = (BOMAssyComponent)allListNode.getBOMComponent();

            if (bomassy.getSeqInt().toString().trim().equals(sequence))
            {
                TreePath treepath = new TreePath(allListNode.getPath());
                try
                {
                    treeTable.getTree().fireTreeWillExpand(treepath);
                    treeTable.getTree().scrollPathToVisible(treepath);
                    treeTable.getTree().fireTreeExpanded(treepath);
                    treeTable.getTree().setSelectionPath(treepath);
                    treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));
                }
                catch (Exception ex)
                {
                    loadWarningMessage(ex.toString());
                }
            }
        }
    }

      public void columnSelectionChanged(ListSelectionEvent e)
    {
        repaint();
      }

      public void valueChanged(ListSelectionEvent e)
    {
        int firstIndex = e.getFirstIndex();
        int  lastIndex = e.getLastIndex();
        if (firstIndex == -1 && lastIndex == -1)
        {
              repaint();
        }
        Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
        int numCoumns = getColumnCount();
        int index = firstIndex;

        for (int i=0;i<numCoumns;i++)
        {
              dirtyRegion.add(getCellRect(index, i, false));
        }
        index = lastIndex;
        for (int i=0;i<numCoumns;i++)
        {
              dirtyRegion.add(getCellRect(index, i, false));
        }
        repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
      }

    private void loadWarningMessage(String message)
    {
        MessageBox messagebox = new MessageBox(app.getDesktop(), message, "Warning", MessageBox.WARNING);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

}
