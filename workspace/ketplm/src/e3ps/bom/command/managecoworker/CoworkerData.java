package e3ps.bom.command.managecoworker;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.common.message.KETMessageService;

class CoworkerData
{
    public String strId;
    public String strName;
    public String strMail;
    public String strDept;

    public CoworkerData(String id, String name, String mail, String dept)
    {
        strId = id;
        strName = name;
        strMail = mail;
        strDept = dept;
    }
}

class CoworkerColumnData
{
    public String strTitle;
    public int intWidth;
    public int intAlignment;

    public CoworkerColumnData(String title, int width, int alignment)
    {
        strTitle = title;
        intWidth = width;
        intAlignment = alignment;
    }
}

class CoworkerTableData extends AbstractTableModel
{
    static final public CoworkerColumnData clmWorkerData[] =
    {
        new CoworkerColumnData("Id", 30, JLabel.CENTER),
        new CoworkerColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/, 120, JLabel.CENTER),
        new CoworkerColumnData("E-Mail", 191, JLabel.CENTER),
        new CoworkerColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/, 150, JLabel.CENTER)
    };
    protected Vector vecWorkerData;

    public CoworkerTableData()
    {
        vecWorkerData = new Vector();
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }


    public int getColumnCount()
    {
        return clmWorkerData.length;
    }

    public String getColumnName(int column)
    {
        return clmWorkerData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";
        CoworkerData row = (CoworkerData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
            case 0: return row.strId;
            case 1: return row.strName;
            case 2: return row.strMail;
            case 3: return row.strDept;
        }
        return "";
    }

}
