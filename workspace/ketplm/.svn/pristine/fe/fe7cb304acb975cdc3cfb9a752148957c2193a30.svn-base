package e3ps.edm.clients.batch;

import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import wt.lifecycle.State;

import e3ps.common.message.KETMessageService;

public class EDMProjectSearchData
{
    public String projectNumberStr;
    public String projecNameStr;
    public String planStartDateStr;
    public String planEndDateStr;
	public String statusStr;
	public String oidStr;

	public EDMProjectSearchData( String projectNumber, String projecName, String planStartDate, String planEndDate, String status, String oid )
	{
		projectNumberStr = projectNumber;
		projecNameStr = projecName;
		planStartDateStr = planStartDate;
		planEndDateStr = planEndDate;
		statusStr = State.toState(status).getDisplay(KETMessageService.service.getLocale());
		oidStr = oid;
    }
}

class EDMProjectSearchDataColumnData
{
    public String  strTitle;
    public int     intWidth;
    public int     intAlignment;

    public EDMProjectSearchDataColumnData(String title, int width, int alignment)
    {
        strTitle = title;
        intWidth = width;
        intAlignment = alignment;
    }
}

class EDMProjectSearchTableData extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	static final public EDMProjectSearchDataColumnData clmModelData[] =
    {
        new EDMProjectSearchDataColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "03114")/*프로젝트번호*/, 100, JLabel.CENTER ),
        new EDMProjectSearchDataColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/, 220, JLabel.LEFT ),
        new EDMProjectSearchDataColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "00817")/*계획시작일*/, 80, JLabel.CENTER ),
        new EDMProjectSearchDataColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "00826")/*계획종료일*/, 80, JLabel.CENTER ),
		new EDMProjectSearchDataColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "02731")/*진행상태*/, 70, JLabel.CENTER ),
		new EDMProjectSearchDataColumnData( "OID", 100, JLabel.LEFT ),
    };
    protected Vector vecModelData;

    public EDMProjectSearchTableData(Vector data)
    {
        vecModelData = new Vector();
        vecModelData.removeAllElements();
        vecModelData = data;
    }

    public int getRowCount()
    {
        return vecModelData == null ? 0 : vecModelData.size();
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
        {
        	return "";
        }

        EDMProjectSearchData row = (EDMProjectSearchData)vecModelData.elementAt(nRow);

        switch (nCol)
        {
			case 0: return row.projectNumberStr;
	        case 1: return row.projecNameStr;
	        case 2: return row.planStartDateStr;
			case 3: return row.planEndDateStr;
			case 4: return row.statusStr;
			case 5: return row.oidStr;
        }

        return "";
    }
}
