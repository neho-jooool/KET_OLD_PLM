package e3ps.bom.command.mybom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.common.message.KETMessageService;

public class BOMECOTableRender extends DefaultTableCellRenderer
{
    private static final long serialVersionUID = 1L;

    public BOMECOTableRender()
    {
    }

    public void setValue(Object value)
    {
        super.setValue(value);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        boolean isCoworkerColumn = false;
        if(table.getColumnName(column).trim().equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00088")/*공동작업자*/)) isCoworkerColumn = true;

        int approveCol = 0;
        int coworkerCol = 0;
        for (int i=0; i < table.getColumnCount(); i++)
        {
            if (table.getColumnName(i).trim().equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00088")/*공동작업자*/))
            {
                coworkerCol = i;
            }

            if (table.getColumnName(i).trim().equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/))
            {
                approveCol = i;
            }
        }

        boolean isOwnEndWorking = false;
        boolean isApproved = false;

        if (table.getValueAt(row, coworkerCol) instanceof Vector)
        {
            Vector tmpVec = (Vector)table.getValueAt(row, coworkerCol);
            for (int i=0; i < tmpVec.size(); i++)
            {
                JCheckBox tmpCheck = (JCheckBox)(tmpVec.elementAt(i));
                if (tmpCheck.getText().trim().indexOf(BOMBasicInfoPool.getUserId()) > 0 && tmpCheck.isSelected())
                {
                    isOwnEndWorking = true;
                    break;
                }
            }
        }

        if (!isSelected)
        {
            String tmpStr = (String)table.getValueAt(row, approveCol);
            if (tmpStr.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00251")/*승인완료*/))
            {
                isApproved = true;
//                setBackground(Color.red);
            }
            else if (isOwnEndWorking)
            {
                setBackground(Color.cyan);
            }
            else
            {
                setBackground(Color.white);
            }
        }
        //색깔처리 끝

        if (value == null || !isCoworkerColumn) return this;

        JComboBox combo = new JComboBox();

        return combo;

    }

    public void setHorizontalAlignment(int alignment) {
        if (alignment == super.getHorizontalAlignment()) return;
        int oldValue = super.getHorizontalAlignment();
        super.setHorizontalAlignment(checkHorizontalKey(alignment, "horizontalAlignment"));
        firePropertyChange("horizontalAlignment", oldValue, super.getHorizontalAlignment());
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

}
