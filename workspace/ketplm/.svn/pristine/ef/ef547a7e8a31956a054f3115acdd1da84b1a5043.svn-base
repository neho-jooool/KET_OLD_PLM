// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateButton.java

package e3ps.bom.framework.util;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// Referenced classes of package e3ps.bom.framework.util:
//            AbstractPopupButton, CalendarPanel, ButtonLayout

public class DateButton extends AbstractPopupButton
{

    public DateButton()
    {
        this(new Date());

        GregorianCalendar gregoriancalendar = new GregorianCalendar();
        gregoriancalendar.setTime(date);
        setDate(gregoriancalendar.getTime());
    }

    public DateButton(Date date1)
    {
        this(date1, defaultDateFormat, false);
    }

    public DateButton(Date date1, String currentDate)
    {
        this(date1, defaultDateFormat, false, currentDate);
    }

    public DateButton(Date date1, boolean flag)
    {
        this(date1, defaultDateFormat, flag, "Due Date");
    }

    public DateButton(Date date1, String s, boolean flag)
    {
        doTearoff = false;
        displayFormat = s;
        if(displayFormat != null)
            displayFormatter = new SimpleDateFormat(displayFormat);
        lovStyle = flag;
        nullDate = "";
        if(lovStyle)
        {
            setMargin(new Insets(0, 3, 0, 3));
            setBorder(BorderFactory.createEtchedBorder());
        }
        date = date1;
        updateButtonText();
        calendarPanel = new CalendarPanel(date1);
        setHorizontalTextPosition(2);
        Font font = getFont();
        setFont(new Font(font.getName(), 0, font.getSize() - 2));
    }

    public DateButton(Date date1, String s, boolean flag, String nullDate)
    {
        doTearoff = false;
        displayFormat = s;
        if(displayFormat != null)
            displayFormatter = new SimpleDateFormat(displayFormat);
        lovStyle = flag;
        this.nullDate = nullDate;
        if(lovStyle)
        {
            setMargin(new Insets(0, 3, 0, 3));
            setBorder(BorderFactory.createEtchedBorder());
        }
        date = date1;
        updateButtonText();
        calendarPanel = new CalendarPanel(date1);
        setHorizontalTextPosition(2);
        Font font = getFont();
        setFont(new Font(font.getName(), 0, font.getSize() - 2));
    }

    public void allowTearoff(boolean flag)
    {
        doTearoff = flag;
    }

    public Date getDate()
    {
        return date;
    }

    public String getDisplayFormat()
    {
        return displayFormat;
    }

    public void initPopupWindow()
    {
        getPanel().add("Center", calendarPanel);
        calendarPanel.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent changeevent)
            {
                setDate(calendarPanel.getTime());
            }

        });
        JButton jbutton = new JButton("OK");
        jbutton.setFont(getFont());
        jbutton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                calendarPanel.fireStateChanged();
                postDown();
            }

        });
        JButton jbutton1 = new JButton("Clear");
        jbutton1.setFont(getFont());
        jbutton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                setDate(null);
                postDown();
            }

        });
        JPanel jpanel = new JPanel(new ButtonLayout());
        jpanel.add("left", jbutton);
        jpanel.add("right", jbutton1);
        if(calendarTitle != null)
            setPopupTitle(calendarTitle);
        getPanel().add("South", jpanel);
    }

    public static void main(String args[])
    {
        JFrame jframe = new JFrame();
        jframe.getContentPane().setLayout(new BorderLayout());
        jframe.getContentPane().add("Center", new DateButton());
        jframe.pack();
        jframe.setVisible(true);
    }

    public void postUp()
    {
        super.postUp();
        if(doTearoff)
            tearoff();
    }

    public void setDate(Date date1)
    {
        date = date1;
        calendarPanel.setTime(date1);
        updateButtonText();
    }

    public void setDisplayFormat(String s)
    {
        displayFormat = s;
        displayFormatter = new SimpleDateFormat(displayFormat);
    }

    public void setTitle(String s)
    {
        calendarTitle = s;
    }

    protected void updateButtonText()
    {
        if(date == null)
        {
            setText(nullDate);
        } else
        {
            String s = displayFormatter.format(date);
            setText(s);
        }
        validate();
        repaint();
    }

    protected Date date;
    protected CalendarPanel calendarPanel;
    private String nullDate;
    private String displayFormat;
    private SimpleDateFormat displayFormatter;
    private boolean lovStyle;
    private boolean doTearoff;
    private String calendarTitle;
    protected static final String defaultDateFormat = new String("d-MMM-yyyy H:m:s");

}
