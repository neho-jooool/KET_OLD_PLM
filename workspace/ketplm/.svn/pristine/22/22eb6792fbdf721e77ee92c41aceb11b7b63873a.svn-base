package e3ps.bom.common.jtreetable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import e3ps.bom.common.util.ColorList;
import e3ps.bom.common.util.FontList;

public class DefaultTreeTableCellRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 1L;

	public DefaultTreeTableCellRenderer()
    {
    }

    public void setValue(Object value)
    {
        super.setValue(value);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel comp = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        comp.setHorizontalAlignment(JLabel.LEFT);
        comp.setForeground(Color.blue);
        
        // 폰트설정
        setFont(FontList.treeFont);

        // 기본 전경색 설정
        setForeground( Color.black );

        // 기본 배경색 설정
        if( (row + 1) % 2 == 1 && !isSelected )
		{
            setBackground(ColorList.veryLightGray);
		}
        else
		{
            setBackground( Color.white );
		}

        // 컬럼별 색상 및 정렬 설정
        String columnName = table.getColumnName(column);
		// 레벨 컬럼인 경우 
//        boolean isModify = false;
//        String columnValue = (String) table.getValueAt(row, 3);
//		if( !BOMECOBasicInfoPool.getECONo().equals("") && columnValue.indexOf("*") == 0 )
//		{
//			isModify = true;
//		}
        
		if( columnName.equalsIgnoreCase(BOMTreeTableModel.cNames[6]) )
		{
            setHorizontalAlignment( JLabel.RIGHT );
//            if ( isModify ) 
//            {
//            	setForeground( Color.lightGray );
//            }
		}
		else if( columnName.equalsIgnoreCase(BOMTreeTableModel.cNames[5]) ||
				 columnName.equalsIgnoreCase(BOMTreeTableModel.cNames[15]) ||
				 columnName.equalsIgnoreCase(BOMTreeTableModel.cNames[17]) ||
				 columnName.equalsIgnoreCase(BOMTreeTableModel.cNames[18]) ||
				 columnName.equalsIgnoreCase(BOMTreeTableModel.cNames[19]) )
		{
			setHorizontalAlignment( JLabel.LEFT );
//            if ( isModify ) 
//            {
//            	setForeground( Color.lightGray );
//            }
		}
		else
		{
			setHorizontalAlignment( JLabel.CENTER );
//            if ( isModify ) 
//            {
//            	setForeground( Color.lightGray );
//            }
		}

		
//		if( columnName.equalsIgnoreCase(BOMTreeTableModel.cNames[7]) ||
//				columnName.equalsIgnoreCase(BOMTreeTableModel.cNames[13]) ||
//				columnName.equalsIgnoreCase(BOMTreeTableModel.cNames[14]) )
//		{
//            setBackground(ColorList.lightYellowColor);
//		}

        if( isSelected )
        {
            setBackground(ColorList.veryDarkBlueColor);
			setForeground(Color.white);
        }

		if ( column == 16 )   //'삭제여부' 컬럼은 빨간글씨로 보여줌
		{
			setForeground( Color.red  );
		}

        // 속도 문제로 툴팀은 막음
//        if(value != null && value.getClass().getName().endsWith("String"))
//		{
//            setToolTipText(value.toString());
//		}

        return this;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.lightGray);
        g.drawLine(0, 0, 0, height);
        g.drawLine(0, height - 1, width, height - 1);
    }
}
