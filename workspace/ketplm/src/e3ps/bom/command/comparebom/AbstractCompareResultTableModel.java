package e3ps.bom.command.comparebom;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import e3ps.common.message.KETMessageService;

/**
 * class Name  : AbstractCompareResultTableModel
 * Description : StaticCompareResultTableModel과 ItemCompareResultTableModel을 subClass로 두는 class 두 class에 대한 공통 부분을 각각 빼내어서 상위 class로 만들었음
 */
public abstract class AbstractCompareResultTableModel extends AbstractTableModel
{
    public String[] cNames =  new String[18];
    protected Vector m_vector;

    public AbstractCompareResultTableModel(AbstractComparePanel parentPanel) throws Exception
    {
        try
        {
            tableModel();
            m_vector = new Vector();
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    public int getRowCount()
    {
        return m_vector==null ? 0 : m_vector.size();
    }

    public int getColumnCount()
    {
        return cNames.length;
    }

    public String getColumnName(int column)
    {
        return cNames[column];
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public boolean delete(int row)
    {
        if (row < 0 || row >= m_vector.size())
            return false;
        m_vector.remove(row);
            return true;
    }

    public void tableModel()
    {
        cNames[0] = "A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/;
        cNames[1] = "A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/;
        cNames[2] = "A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/;
        cNames[3] = "A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/;
        cNames[4] = "A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/;
        cNames[5] = "A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/;
        cNames[6] = "A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/;
        cNames[7] = "A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/;
        cNames[8] = "A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/;

        cNames[9]  = "B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/;
        cNames[10] = "B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/;
        cNames[11] = "B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/;
        cNames[12] = "B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/;
        cNames[13] = "B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/;
        cNames[14] = "B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/;
        cNames[15] = "B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/;
        cNames[16] = "B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/;
        cNames[17] = "B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/;
    }
}
