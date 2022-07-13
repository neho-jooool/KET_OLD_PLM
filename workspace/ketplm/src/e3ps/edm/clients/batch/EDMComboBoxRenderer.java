package e3ps.edm.clients.batch;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class EDMComboBoxRenderer extends BasicComboBoxRenderer {
	
    public Component getListCellRendererComponent( JList list, 
           Object value, int index, boolean isSelected, boolean cellHasFocus) {
    	
    	if (isSelected) {
    		setBackground(Color.lightGray);
    		setForeground(Color.black);
    		//setBackground(list.getSelectionBackground());
    		//setForeground(list.getSelectionForeground());
    		/*
    		if (-1 < index) {
    			list.setToolTipText(tooltips[index]);
    		}
    		*/
    	} else {
    		setBackground(Color.white);
    		setForeground(Color.black);
    		//setBackground(list.getBackground());
    		//setForeground(list.getForeground());
    	}
    	
    	setFont(list.getFont());
    	setText((value == null) ? "" : value.toString());
    	return this;
    }  
}
