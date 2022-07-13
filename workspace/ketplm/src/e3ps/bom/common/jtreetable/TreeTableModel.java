package e3ps.bom.common.jtreetable;

import javax.swing.tree.TreeModel;

public interface TreeTableModel extends TreeModel
{
    public abstract int getColumnCount();

    public abstract String getColumnName(int i);

    public abstract Class getColumnClass(int i);

    public abstract Object getValueAt(Object obj, int i);

    public abstract boolean isCellEditable(Object obj, int i);

    public abstract void setValueAt(Object obj, Object obj1, int i);
}
