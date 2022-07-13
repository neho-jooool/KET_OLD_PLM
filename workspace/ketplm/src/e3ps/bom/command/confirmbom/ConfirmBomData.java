package e3ps.bom.command.confirmbom;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.common.message.KETMessageService;

public class ConfirmBomData
{
    public String gubuns;
    public String depts;
    public String dutys;
    public String userids;
    public String usernms;
    public String statusStr;
    public String orders;

    public ConfirmBomData(String gubun, String dept, String duty, String userid, String usernm, String status, String order)
    {
        gubuns = gubun;
        depts = dept;
        dutys = duty;
        userids = userid;
        usernms = usernm;
        statusStr = status;
        orders = order;
    }
}

class ConfirmUserSearchColumnData
{
    public String  strTitle;
    public int     intWidth;
    public int     intAlignment;

    public ConfirmUserSearchColumnData(String title, int width, int alignment)
    {
        strTitle = title;
        intWidth = width;
        intAlignment = alignment;
    }
}

class ConfirmBomTableData extends AbstractTableModel
{
    static final public ConfirmUserSearchColumnData clmModelData[] =
    {
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00248")/*순서*/, 20, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/, 50, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/, 100, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "USERID", 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "statusStr", 80, JLabel.CENTER ),
    };

    protected Vector vecWorkerData;

    public ConfirmBomTableData()
    {
        vecWorkerData = new Vector();
    }

    public ConfirmBomTableData(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }

    public int getColumnCount()
    {
        return clmModelData.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if( nRow < 0 || nRow >= getRowCount() )
            return "";

        ConfirmBomData row = (ConfirmBomData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
//            case 0: return nRow+1;  //순서...??
            case 0: return (vecWorkerData.size() - nRow);
            case 1: return row.gubuns;
            case 2: return row.depts;
            case 3: return row.dutys;
            case 4: return row.usernms;
            case 5: return row.userids;
            case 6: return row.statusStr;
        }

        return "";
    }
}

class DistributionBomTableData extends AbstractTableModel
{
    static final public ConfirmUserSearchColumnData clmModelData[] =
    {
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00248")/*순서*/, 20, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/, 50, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/, 100, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "USERID", 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "statusStr", 80, JLabel.CENTER ),
    };

    protected Vector vecWorkerData;

    public DistributionBomTableData()
    {
        vecWorkerData = new Vector();
    }

    public DistributionBomTableData(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }

    public int getColumnCount()
    {
        return clmModelData.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";

        ConfirmBomData row = (ConfirmBomData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
//            case 0: return nRow+1;  //순서...??
            case 0: return (vecWorkerData.size() - nRow);
            case 1: return row.gubuns;
            case 2: return row.depts;
            case 3: return row.dutys;
            case 4: return row.usernms;
            case 5: return row.userids;
            case 6: return row.statusStr;
        }

        return "";
    }
}

class ConfirmLineSchData extends AbstractTableModel
{
    static final public ConfirmUserSearchColumnData clmModelData[] =
    {
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/, 50, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/, 100, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "USERID", 80, JLabel.CENTER ),
    };

    protected Vector vecWorkerData;

    public ConfirmLineSchData(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }

    public int getColumnCount()
    {
        return clmModelData.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";

        ConfirmBomData row = (ConfirmBomData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
            case 0: return row.gubuns;
            case 1: return row.depts;
            case 2: return row.dutys;
            case 3: return row.usernms;
            case 4: return row.userids;
        }

        return "";
    }
}

class ConfirmBomLineData01 extends AbstractTableModel
{
    static final public ConfirmUserSearchColumnData clmModelData[] =
    {
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00248")/*순서*/, 30, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/, 40, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/, 60, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "USERID", 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "statusStr", 80, JLabel.CENTER ),
    };

    protected Vector vecWorkerData;

    public ConfirmBomLineData01()
    {
        vecWorkerData = new Vector();
    }

    public ConfirmBomLineData01(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }

    public int getColumnCount()
    {
        return clmModelData.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";

        ConfirmBomData row = (ConfirmBomData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
            case 0: return nRow+1;  //순서...??
            case 1: return row.gubuns;
            case 2: return row.depts;
            case 3: return row.dutys;
            case 4: return row.usernms;
            case 5: return row.userids;
            case 6: return row.statusStr;
        }

        return "";
    }
}

class ConfirmBomLineData02 extends AbstractTableModel
{
    static final public ConfirmUserSearchColumnData clmModelData[] =
    {
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00248")/*순서*/, 30, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/, 40, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/, 60, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "USERID", 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "statusStr", 80, JLabel.CENTER ),
    };

    protected Vector vecWorkerData;

    public ConfirmBomLineData02()
    {
        vecWorkerData = new Vector();
    }

    public ConfirmBomLineData02(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }

    public int getColumnCount()
    {
        return clmModelData.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";

        ConfirmBomData row = (ConfirmBomData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
            case 0: return nRow+1;  //순서
            case 1: return row.gubuns;
            case 2: return row.depts;
            case 3: return row.dutys;
            case 4: return row.usernms;
            case 5: return row.userids;
            case 6: return row.statusStr;
        }

        return "";
    }
}

class ConfirmBomLineData03 extends AbstractTableModel
{
    static final public ConfirmUserSearchColumnData clmModelData[] =
    {
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00248")/*순서*/, 30, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/, 40, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/, 60, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "USERID", 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "statusStr", 80, JLabel.CENTER ),
    };

    protected Vector vecWorkerData;

    public ConfirmBomLineData03()
    {
        vecWorkerData = new Vector();
    }

    public ConfirmBomLineData03(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }

    public int getColumnCount()
    {
        return clmModelData.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";

        ConfirmBomData row = (ConfirmBomData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
            case 0: return nRow+1;  //순서
            case 1: return row.gubuns;
            case 2: return row.depts;
            case 3: return row.dutys;
            case 4: return row.usernms;
            case 5: return row.userids;
            case 6: return row.statusStr;
        }

        return "";
    }
}

//------------------------------------------------------------------------------------------------------------------------

class DistributionBomData01 extends AbstractTableModel
{
    static final public ConfirmUserSearchColumnData clmModelData[] =
    {
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00248")/*순서*/, 30, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/, 40, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/, 60, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "USERID", 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "statusStr", 80, JLabel.CENTER ),
    };

    protected Vector vecWorkerData;

    public DistributionBomData01()
    {
        vecWorkerData = new Vector();
    }

    public DistributionBomData01(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }

    public int getColumnCount()
    {
        return clmModelData.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";

        ConfirmBomData row = (ConfirmBomData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
            case 0: return nRow+1;  //순서...??
            case 1: return row.gubuns;
            case 2: return row.depts;
            case 3: return row.dutys;
            case 4: return row.usernms;
            case 5: return row.userids;
            case 6: return row.statusStr;
        }

        return "";
    }
}

class DistributionBomData02 extends AbstractTableModel
{
    static final public ConfirmUserSearchColumnData clmModelData[] =
    {
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00248")/*순서*/, 30, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/, 40, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00325")/*직급*/, 60, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/, 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "USERID", 80, JLabel.CENTER ),
        new ConfirmUserSearchColumnData( "statusStr", 80, JLabel.CENTER ),
    };

    protected Vector vecWorkerData;

    public DistributionBomData02()
    {
        vecWorkerData = new Vector();
    }

    public DistributionBomData02(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }

    public int getColumnCount()
    {
        return clmModelData.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";

        ConfirmBomData row = (ConfirmBomData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
            case 0: return nRow+1;  //순서
            case 1: return row.gubuns;
            case 2: return row.depts;
            case 3: return row.dutys;
            case 4: return row.usernms;
            case 5: return row.userids;
            case 6: return row.statusStr;
        }

        return "";
    }
}
