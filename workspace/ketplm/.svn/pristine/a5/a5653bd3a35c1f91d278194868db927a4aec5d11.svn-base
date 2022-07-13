// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CalendarPanel.java

package e3ps.bom.framework.util;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;

// Referenced classes of package e3ps.bom.framework.util:
//            VerticalLayout, HorizontalLayout, ValidatingTextField

public class CalendarPanel extends JPanel
{
    private class DateButton extends JToggleButton implements ChangeListener
    {
        private void doPressed()
        {
            calendar.set(5, number);
            selected.setTime(calendar.getTime());
            changed();
        }

        public void setNumber(int i)
        {
            number = i;
            if(i == 0)
            {
                setText("");
                ((AbstractButton)this).setEnabled(false);
            } else
            {
                setText(Integer.toString(number));
                if(isEnabled())
                    ((AbstractButton)this).setEnabled(true);
            }
        }

        public void stateChanged(ChangeEvent changeevent)
        {
            setBorderPainted(isSelected());
        }

        int number;


        public DateButton()
        {
            setNumber(0);
            addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent actionevent)
                {
                    doPressed();
                }

            });
            ((AbstractButton)this).addChangeListener(this);
        }
    }

    private class TextFieldUpdater extends FocusAdapter implements ActionListener
    {
        public void actionPerformed(ActionEvent actionevent)
        {
            getAllText();
            updateDatePanel();
        }

        public void focusLost(FocusEvent focusevent)
        {
            getAllText();
            updateDatePanel();
        }

        TextFieldUpdater()
        {
        }
    }

    public CalendarPanel()
    {
        changeEvent = new ChangeEvent(this);
        constructPanel(new Date());
        setTime(calendar.getTime());
    }

    public CalendarPanel(Date date)
    {
        changeEvent = new ChangeEvent(this);
        if(date == null)
            date = new Date();

        constructPanel(date);
    }

    public void addChangeListener(ChangeListener changelistener)
    {
        super.listenerList.add(javax.swing.event.ChangeListener.class, changelistener);
    }

    private void changed()
    {
        fireStateChanged();
    }

    private void constructPanel(Date date)
    {
        calendar = new GregorianCalendar();
        selected = new GregorianCalendar();
        DateFormatSymbols dateformatsymbols = new DateFormatSymbols();
        setLayout(new VerticalLayout(2, 2, 2, 2, 2));
        Font font = (new JLabel("foo")).getFont();
        Font font1 = new Font(font.getName(), 0, font.getSize() - 2);
        JPanel jpanel = new JPanel(new HorizontalLayout(1, 0, 0, 0, 0));
        prev = new BasicArrowButton(7);
        prev.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent actionevent)
            {
                decMonth();
                updateText();
                updateDatePanel();
            }

        });
        jpanel.add("left", prev);
        year = new ValidatingTextField(4, 4, true);
        year.setFont(font1);
        TextFieldUpdater textfieldupdater = new TextFieldUpdater();
        year.addActionListener(textfieldupdater);
        year.addFocusListener(textfieldupdater);

        month = new ValidatingTextField(2, 2, true);
        month.setFont(font1);
        month.addActionListener(textfieldupdater);
        month.addFocusListener(textfieldupdater);
        
        next = new BasicArrowButton(3);
        next.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent actionevent)
            {
                incMonth();
                updateText();
                updateDatePanel();
            }

        });

        jpanel.add("right", next);
		jpanel.add("right", month);
        jpanel.add("right", year);
        add("top", jpanel);
        jpanel = new JPanel(new GridLayout(7, 7));
        String as[] = dateformatsymbols.getShortWeekdays();
        for(int i = 1; i <= 7; i++)
        {
            JLabel jlabel = new JLabel(String.valueOf(as[i].charAt(0)));
            jlabel.setHorizontalAlignment(0);
            jlabel.setFont(font1);
            jpanel.add(jlabel);
        }

        int j = 1;
        int k = 2;
        dates = new DateButton[42];
        ButtonGroup buttongroup = new ButtonGroup();
        for(int l = 0; l < 42; l++)
        {
            DateButton datebutton = new DateButton();
            datebutton.setNumber(l);
            datebutton.setFocusPainted(false);
            datebutton.setBorderPainted(false);
            datebutton.setMargin(new Insets(1, 1, 1, 1));
            datebutton.setFont(font1);
            dates[l] = datebutton;
            jpanel.add(datebutton);
            buttongroup.add(datebutton);
            if(++j == 8)
            {
                j = 1;
                k++;
            }
        }

        clearSelection = new JToggleButton("clear");
        buttongroup.add(clearSelection);
        jpanel.setBorder(BorderFactory.createEtchedBorder());
        add("top", jpanel);
        jpanel = new JPanel(new GridLayout(1, 6));
        
        setTime(date);

        updateText();
        updateDatePanel();
    }

    private void decMonth()
    {
        if(calendar.get(1) == 1 && calendar.get(2) == 0)
        {
            return;
        }
		else
        {
            calendar.add(2, -1);
            return;
        }
    }

    public void fireStateChanged()
    {
        getAllText();
        Object aobj[] = super.listenerList.getListenerList();
        for(int i = aobj.length - 2; i >= 0; i -= 2)
            if(aobj[i] == javax.swing.event.ChangeListener.class)
                ((ChangeListener)aobj[i + 1]).stateChanged(changeEvent);

    }

    private void getAllText()
    {
        try
        {
            int i = Integer.parseInt(year.getText());
            if(i == 0)
                i = 1;
            calendar.set(1, i);

			calendar.set(Calendar.MONTH, Integer.parseInt(month.getText())-1);
        }
        catch(NumberFormatException numberformatexception) { }
        updateText();
    }

    public Date getTime()
    {
        return calendar.getTime();
    }

    private void incMonth()
    {
        if(calendar.get(1) == 9999 && calendar.get(2) == 11)
        {
            return;
        }
		else
        {
            calendar.add(2, 1);
            return;
        }
    }

    public void removeChangeListener(ChangeListener changelistener)
    {
        super.listenerList.remove(javax.swing.event.ChangeListener.class, changelistener);
    }

    public void setEnabled(boolean flag)
    {
        super.setEnabled(flag);
        for(int i = 0; i < dates.length; i++)
            dates[i].setEnabled(flag);

        year.setEnabled(flag);
		month.setEnabled(flag);
        prev.setEnabled(flag);
        next.setEnabled(flag);
        clearSelection.setEnabled(flag);
    }

    public void setTime(Date date)
    {
        if(date == null)
            date = new Date();
        calendar.setTime(date);
        selected.setTime(date);
        updateText();
        updateDatePanel();
    }

    private void updateDatePanel()
    {
        setEnabled(true);
        int i = calendar.get(5);
        calendar.set(5, 1);
        int j = calendar.get(7) - 1;
        calendar.set(5, i);
        for(int k = 0; k < j; k++)
            dates[k].setNumber(0);

        int l = calendar.getActualMaximum(5);
        for(int i1 = 0; i1 < l; i1++)
            dates[i1 + j].setNumber(i1 + 1);

        for(int j1 = j + l; j1 < 42; j1++)
            dates[j1].setNumber(0);

        if(calendar.get(2) == selected.get(2))
        {
            dates[(j + selected.get(5)) - 1].setSelected(true);
        }
        else
        {
            clearSelection.setSelected(true);
        }

        invalidate();
        repaint();
    }

    private void updateText()
    {
        year.setText(String.valueOf(calendar.get(1)));
   		month.setText(String.valueOf(calendar.get(Calendar.MONTH)+1));        
    }

    private GregorianCalendar calendar;
    private GregorianCalendar selected;
    private DateButton dates[];
    private ValidatingTextField year;
	private ValidatingTextField month;
    private BasicArrowButton prev;
    private BasicArrowButton next;
    private JToggleButton clearSelection;
    private ChangeEvent changeEvent;
}
