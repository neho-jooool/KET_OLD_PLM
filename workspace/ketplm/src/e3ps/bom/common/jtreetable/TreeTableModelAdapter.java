package e3ps.bom.common.jtreetable;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;

public class TreeTableModelAdapter extends AbstractTableModel
{
    JTree tree;
    TreeTableModel treeTableModel;

    public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree)
    {
        this.tree = tree;
        this.treeTableModel = treeTableModel;
        tree.addTreeExpansionListener(new TreeExpansionListener()
		{
            public void treeExpanded(TreeExpansionEvent event)
            {
                fireTableDataChanged();
            }

            public void treeCollapsed(TreeExpansionEvent event)
            {
                fireTableDataChanged();
            }
        });

        treeTableModel.addTreeModelListener(new TreeModelListener()
		{
            public void treeNodesChanged(TreeModelEvent e)
            {
                delayedFireTableDataChanged();
            }

            public void treeNodesInserted(TreeModelEvent e)
            {
                delayedFireTableDataChanged();
            }

            public void treeNodesRemoved(TreeModelEvent e)
            {
                delayedFireTableDataChanged();
            }

            public void treeStructureChanged(TreeModelEvent e)
            {
                delayedFireTableDataChanged();
            }
        });
    }

    public int getColumnCount()
    {
        return treeTableModel.getColumnCount();
    }

    public String getColumnName(int column)
    {
        return treeTableModel.getColumnName(column);
    }

    public Class getColumnClass(int column)
    {
        return treeTableModel.getColumnClass(column);
    }

    public int getRowCount()
    {
        return tree.getRowCount();
    }

    protected Object nodeForRow(int row)
    {
        TreePath treePath = tree.getPathForRow(row);
        if (treePath != null)
            return treePath.getLastPathComponent();

        return null;
    }

    public Object getValueAt(int row, int column)
    {
        return treeTableModel.getValueAt(nodeForRow(row), column);
    }

    public boolean isCellEditable(int row, int column)
    {
        return treeTableModel.isCellEditable(nodeForRow(row), column);
    }

    public void setValueAt(Object value, int row, int column)
    {
        treeTableModel.setValueAt(value, nodeForRow(row), column);
    }

    protected void delayedFireTableDataChanged()
    {
        SwingUtilities.invokeLater(new Runnable()
		{
            public void run()
            {
                fireTableDataChanged();
            }
        });
    }

}
