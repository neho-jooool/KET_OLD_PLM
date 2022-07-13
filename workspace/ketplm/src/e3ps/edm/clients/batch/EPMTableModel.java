package e3ps.edm.clients.batch;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.common.message.KETMessageService;

public class EPMTableModel extends AbstractTableModel
{
	static final public EPMTableColumnData m_columns[] = {
		new EPMTableColumnData( "", 50, JLabel.CENTER, false ),
		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "03430")/*금형번호*/, 120, JLabel.CENTER, true ),
		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "01075")/*금형부품번호*/, 150, JLabel.CENTER, false ),
		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "01076")/*금형부품설명*/, 250, JLabel.LEFT, false ),

		//new EPMTableColumnData( "구금형부품", 100, JLabel.CENTER, true ),
		//new EPMTableColumnData( "BOM삭제대상(X)", 100, JLabel.CENTER, true ),
		//new EPMTableColumnData( "도면삭제대상(X)", 100, JLabel.CENTER, true ),
		//new EPMTableColumnData( "수명대상(X)", 100, JLabel.CENTER, true ),

		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "01925")/*수량*/, 50, JLabel.RIGHT, true ),
		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "01865")/*설계일자*/, 100, JLabel.CENTER, true ),

		//new EPMTableColumnData( "기준타발수", 100, JLabel.RIGHT, true ),

		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "02452")/*재질*/, 100, JLabel.CENTER, true ),
		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "00793")/*경도(From)*/, 100, JLabel.RIGHT, true ),
		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "00794")/*경도(To)*/, 100, JLabel.RIGHT, true ),

		//new EPMTableColumnData( "Size(1)", 70, JLabel.RIGHT, true ),
		//new EPMTableColumnData( "Size(2)", 70, JLabel.RIGHT, true ),
		//new EPMTableColumnData( "Size(3)", 70, JLabel.RIGHT, true ),
		//new EPMTableColumnData( "Rev.NO.", 70, JLabel.CENTER, true ),
		//new EPMTableColumnData( "Rev.Date", 70, JLabel.CENTER, true ),
		//new EPMTableColumnData( "안전재고(일반)", 100, JLabel.RIGHT, true ),
		//new EPMTableColumnData( "안전재고(초경)", 100, JLabel.RIGHT, true ),

		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "03431")/*CAD_Number(도면 유/무)*/, 150, JLabel.CENTER, false ),
		new EPMTableColumnData( KETMessageService.service.getString("e3ps.message.ket_message", "00932")/*관련부품*/, 100, JLabel.CENTER, true )
	};

	// NEW
	public static ImageIcon COLUMN_UP = null;//new ImageIcon("SortUp.gif");
	public static ImageIcon COLUMN_DOWN = null;//new ImageIcon("SortDown.gif");

	protected SimpleDateFormat m_frm;
	protected NumberFormat m_volumeFormat;
	protected Vector m_vector;
	protected Date	 m_date;

	public int		 m_sortCol = 0;
	public boolean m_sortAsc = true;

	public EPMTableModel()
	{
		m_frm = new SimpleDateFormat("yyyy/MM/dd");
		m_volumeFormat = NumberFormat.getInstance();
		m_volumeFormat.setGroupingUsed(true);
		m_volumeFormat.setMaximumFractionDigits(0);

		m_vector = new Vector();
		setDefaultData();
	}

	public void setDefaultData()
	{
		try
		{
			m_date = m_frm.parse("2004/12/18");
		}
		catch( ParseException ex )
		{
			m_date = null;
		}

		//sortData();		// NEW
	}

	public int getRowCount()
	{
		return m_vector==null ? 0 : m_vector.size();
	}

	public int getColumnCount()
	{
		return m_columns.length;
	}

	public String getColumnName(int column)
	{
		return m_columns[column].m_title;
	}

	public Icon getColumnIcon(int column) // NEW
	{
		if( column == m_sortCol )
		{
			return m_sortAsc ? COLUMN_UP : COLUMN_DOWN;
		}

		return null;
	}

	public boolean isCellEditable(int nRow, int nCol)
	{
		return false;
	}

	public Object getValueAt(int nRow, int nCol)
	{
		if( nRow < 0 || nRow>=getRowCount() )
		{
			return "";
		}

		EPMLoadData row = (EPMLoadData)m_vector.elementAt(nRow);
		int i = 0;

		if( row.isAlreadyGone )
		{
			i = 3;
		}
		else if( row.isSkipRow )
		{
			i = 2;
		}
		else if( !(row.isValidate) )
		{
			i = 1;
		}

		switch( nCol )
		{
			case 0: return new EPMTableCellData(row.m_rownum, i);
			case 1: return new EPMTableCellData(row.m_moldNumber, i);//금형번호
			case 2: return new EPMTableCellData(row.m_moldPartNumber, i);//금형부품번호
			case 3: return new EPMTableCellData(row.m_moldPartDesc, i);//금형부품설명

			//case 4: return new EPMTableCellData(row.m_oldMoldPart, i);//구금형부품
			//case 5: return new EPMTableCellData(row.m_deledTargetBOM, i);//BOM삭제대상(X)
			//case 6: return new EPMTableCellData(row.m_deledTargetDrw, i);//도면삭제대상(X)
			//case 7: return new EPMTableCellData(row.m_lifetimeTarget, i);//수명대상(X)
			case 4: return new EPMTableCellData(row.m_qty, i);//수량
			case 5: return new EPMTableCellData(row.m_designDate, i);//설계일자
			//case 10: return new EPMTableCellData(row.m_etc, i);//기준타발수
			case 6: return new EPMTableCellData(row.m_material, i);//재질
			case 7: return new EPMTableCellData(row.m_hardnessFrom, i);//경도(From)
			case 8: return new EPMTableCellData(row.m_hardnessTo, i);//경도(To)
			//case 14: return new EPMTableCellData(row.m_size1, i);//Size(1)
			//case 15: return new EPMTableCellData(row.m_size2, i);//Size(2)
			//case 16: return new EPMTableCellData(row.m_size3, i);//Size(3)
			//case 17: return new EPMTableCellData(row.m_revNo, i);//Rev.NO.
			//case 18: return new EPMTableCellData(row.m_revDate, i);//Rev.Date
			//case 19: return new EPMTableCellData(row.m_safetyStock, i);//안전재고(일반)
			//case 20: return new EPMTableCellData(row.m_initSafetyStock, i);//안전재고(초경)
			case 9: return new EPMTableCellData(row.m_cadNumber, i);
			case 10: return new EPMTableCellData(row.m_reledPart, i);
		}

		return "";
	}

	public String getTitle()
	{
		if( m_date == null )
		{
			return "Stock Quotes";
		}

		return "Stock Quotes at "+m_frm.format(m_date);
	}

	public EPMLoadData getRowData(int row)
	{
		return (EPMLoadData)m_vector.get(row);
	}

	public void addRow(EPMLoadData data)
	{
		m_vector.add(data);
		this.fireTableRowsInserted(m_vector.size() - 1, m_vector.size() - 1);
	}

	public Vector getDatas()
	{
		return m_vector;
	}

	public void removeAll()
	{
		m_vector.removeAllElements();
		fireTableDataChanged();
	}

	// NEW
	public void sortData()
	{
		Collections.sort(m_vector, new EPMTableComparator(m_sortCol, m_sortAsc));
	}
}
