package e3ps.bom.command.multiplebomeco;

import java.util.Vector;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class MultipleBOMECOValidationResultTableModel extends DefaultTableModel
{
	private static final long serialVersionUID = 1L;
	protected JPanel bpanel;
	int number = 0;
	int startnumber = 0;
	
	public MultipleBOMECOValidationResultTableModel()
	{
	}
	
	public MultipleBOMECOValidationResultTableModel(String [] names, Object [][] data)
	{
		this();
		setData(names, data);
	}
	
	public MultipleBOMECOValidationResultTableModel(Vector names, Vector data)
	{
		this();
		setData(names, data);
	}
	
	public void setData(String[] names,Object[][] data)
	{
		setDataVector(data, names);
	}	
	
	public void setData(Vector names,Vector data)
	{
		setDataVector(data,names);
	}	
	
	public void addColumn(Object columnName, Vector columnData) 
	{
    	if (columnName == null)
      		throw new IllegalArgumentException("addColumn() - null parameter");
    	columnIdentifiers.addElement(columnName);
    	int index = 0;
    	Enumeration enumeration = dataVector.elements();
    	while (enumeration.hasMoreElements()) 
		{
      		Object value;
      		if ((columnData != null) && (index < columnData.size()))
	  			value = columnData.elementAt(index);
      		else
				value = null;
      		((Vector)enumeration.nextElement()).addElement(value);
      		index++;
    	}    
    	fireTableStructureChanged();
  	}
	
  	public void addRow(Vector rowData) 
	{
    	if (rowData == null)
		{
      		rowData = new Vector(getColumnCount());
    	}
    	else 
		{
      		rowData.setSize(getColumnCount());
    	}
    	dataVector.addElement(rowData);	
   		newRowsAdded(new TableModelEvent(this, getRowCount()-1, getRowCount()-1,TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  	}
	
  	public void insertRow(int row, Vector rowData) 
	{
    	if (rowData == null) 
		{
      		rowData = new Vector(getColumnCount());
    	}
    	else 
		{
      		rowData.setSize(getColumnCount());
    	}

    	dataVector.insertElementAt(rowData, row);
    	newRowsAdded(new TableModelEvent(this, row, row,TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  	}
	
	public void removeAllRow()
	{
		int rowCount = getRowCount();
		for(int i=0;i < rowCount;i++)
			removeRow(0);
	}	
	
	public boolean isCellEditable(int rowindex,int columnindex)
    {
        return false;
    }

}
