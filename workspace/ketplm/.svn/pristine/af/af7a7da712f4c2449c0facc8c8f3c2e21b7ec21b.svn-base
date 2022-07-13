// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   MessageBox.java

package e3ps.bom.framework.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import e3ps.bom.common.util.FontList;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

// Referenced classes of package com.framework.util:
//            ButtonLayout, HorizontalLayout, InterfaceExceptionStack, MLabel,
//            Registry, Separator, VerticalLayout

public class MessageBox extends JDialog
{
    public static final int INFORMATION = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;
    public static final int WORKING = 4;
    private int msgBoxType;
    private JScrollPane detailScroll;
    private Registry appReg;
    private String dialogMessage;
    private String dialogTitle;
    private String dialogDetailMessage;
    private int dialogType;
    protected JFrame parentFrame;
    protected JApplet parentApplet;

    public MessageBox(JApplet applet, Exception exception)
    {
        super();
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentApplet = null;
        appReg = Registry.getRegistry(this);
        parseExceptionStack(exception);
        parentApplet = applet;
        initializeDialog();
    }

    public MessageBox(JApplet applet, String s, String s1, int i)
    {
        super();
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentApplet = null;
        appReg = Registry.getRegistry(this);
        dialogMessage = new String(s);
        dialogTitle = new String(s1);
        dialogType = i;
        parentApplet = applet;
        initializeDialog();
    }

    public MessageBox(JApplet applet, String s, String s1, String s2, int i)
    {
        super();
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentApplet = null;
        appReg = Registry.getRegistry(this);
        dialogMessage = new String(s);
        if(s1 != null)
            dialogDetailMessage = new String(s1);
        dialogTitle = new String(s2);
        dialogType = i;
        parentApplet = applet;
        initializeDialog();
    }

    public MessageBox(JFrame frame, Exception exception)
    {
        super(frame);
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        parseExceptionStack(exception);
        parentFrame = frame;
        initializeDialog();
    }

    public MessageBox(JFrame frame, String s, Exception exception, String s1, int i)
    {
        super(frame);
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        dialogMessage = new String(s);
        dialogTitle = new String(s1);
        dialogType = i;
        parseExceptionStack(exception);
        parentFrame = frame;
        initializeDialog();
    }

    public MessageBox(JFrame frame, String s, String s1, int i)
    {
        super(frame);
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        dialogMessage = new String(s);
        dialogTitle = new String(s1);
        dialogType = i;
        parentFrame = frame;
        initializeDialog();
    }

    public MessageBox(JFrame frame, String s, String s1, String s2, int i)
    {
        super(frame);
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        dialogMessage = new String(s);
        if(s1 != null)
            dialogDetailMessage = new String(s1);
        dialogTitle = new String(s2);
        dialogType = i;
        parentFrame = frame;
        initializeDialog();
    }

    public MessageBox(JFrame frame, String s, String s1, String s2, int i, boolean flag)
    {
        super(frame);
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        setModal(flag);
        dialogMessage = new String(s);
        if(s1 != null)
            dialogDetailMessage = new String(s1);
        dialogTitle = new String(s2);
        dialogType = i;
        parentFrame = frame;
        initializeDialog();
    }

    public MessageBox(Exception exception)
    {
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        parseExceptionStack(exception);
        initializeDialog();
    }

    public MessageBox(String s, Exception exception, String s1, int i)
    {
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        dialogMessage = new String(s);
        dialogTitle = new String(s1);
        dialogType = i;
        parseExceptionStack(exception);
        initializeDialog();
    }

    public MessageBox(String s, String s1, int i)
    {
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        dialogMessage = new String(s);
        dialogTitle = new String(s1);
        dialogType = i;
        initializeDialog();
    }

    public MessageBox(String s, String s1, String s2, int i)
    {
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        dialogMessage = new String(s);
        if(s1 != null)
            dialogDetailMessage = new String(s1);
        dialogTitle = new String(s2);
        dialogType = i;
        initializeDialog();
    }

    public MessageBox(String s, String s1, String s2, int i, boolean flag)
    {
        msgBoxType = 3;
        dialogMessage = null;
        dialogTitle = null;
        dialogDetailMessage = null;
        parentFrame = null;
        appReg = Registry.getRegistry(this);
        setModal(flag);
        dialogMessage = new String(s);
        if(s1 != null)
            dialogDetailMessage = new String(s1);
        dialogTitle = new String(s2);
        dialogType = i;
        initializeDialog();
    }

    public void centerToScreen()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension1 = getPreferredSize();
        Insets insets = getInsets();
        if((int)((double)dimension1.height * 1.5D) > dimension1.width)
            dimension1.width = (int)((double)dimension1.height * 1.5D);
        int i = (dimension.width - dimension1.width) / 2;
        int j = (dimension.height - dimension1.height) / 2;
        setBounds(i, j, dimension1.width + insets.right, dimension1.height + insets.bottom);
    }

    private void initializeDialog()
    {
        Object obj = null;
        setTitle(dialogTitle);
        JPanel jpanel = new JPanel(new ButtonLayout());
        JPanel jpanel1 = new JPanel(new HorizontalLayout(10, 10, 10, 5, 5));
        MLabel mlabel = new MLabel(dialogMessage);
        mlabel.setTextAlignment(0);
        JLabel jlabel = new JLabel();
        JButton jbutton = new JButton();
        jlabel.setFont(FontList.defaultFont);
        jbutton.setFont(FontList.defaultFont);

Kogger.debug(getClass(), "############### : "+dialogType);


        if(dialogType == 4)
        {
            jlabel.setIcon(appReg.getImageIcon("working.ICON"));
            jbutton = new JButton(appReg.getStringMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00341")/*취소*/));
            String s;
            if((s = appReg.getString("cancel.mnemonic", null)) != null)
                jbutton.setMnemonic(s.charAt(0));
        } else
        if(dialogType == 1)
        {
            jlabel.setIcon(appReg.getImageIcon("information.ICON"));
            jbutton = new JButton(appReg.getStringMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/));
            String s1;
            if((s1 = appReg.getString("ok.mnemonic", null)) != null)
                jbutton.setMnemonic(s1.charAt(0));
        } else
        if(dialogType == 2)
        {
            jlabel.setIcon(appReg.getImageIcon("warning.ICON"));
            jbutton = new JButton(appReg.getStringMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/));
            String s2;
            if((s2 = appReg.getString("ok.mnemonic", null)) != null)
                jbutton.setMnemonic(s2.charAt(0));
        } else
        {
              jlabel.setIcon(appReg.getImageIcon("error.ICON"));
            jbutton = new JButton(appReg.getStringMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/));
            String s3;
            if((s3 = appReg.getString("ok.mnemonic", null)) != null)
                jbutton.setMnemonic(s3.charAt(0));
        }
        jpanel.add(jbutton);
        JToggleButton jtogglebutton = new JToggleButton(appReg.getStringMessage("detailsCollapsed"));
        jtogglebutton.setFont(FontList.defaultFont);

        jtogglebutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
                JToggleButton jtogglebutton1 = (JToggleButton)actionevent.getSource();
                jtogglebutton1.setFont(FontList.defaultFont);
                if(jtogglebutton1.isSelected())
                {
                    detailScroll.setVisible(true);
                    jtogglebutton1.setText(appReg.getStringMessage("detailsExpanded"));
                } else
                {
                    detailScroll.setVisible(false);
                    jtogglebutton1.setText(appReg.getStringMessage("detailsCollapsed"));
                }
                pack();
                resizeDialog();
                validate();
                repaint();
            }
        });

        jpanel.add(jtogglebutton);
        JTextArea jtextarea = new JTextArea(dialogDetailMessage);
        jtextarea.setLineWrap(true);
        jtextarea.setFont(FontList.defaultFont);
        detailScroll = new JScrollPane(jtextarea);
        detailScroll.setVisible(false);
        jbutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
                setVisible(false);
                dispose();
            }
        });

        jbutton.addKeyListener(new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
                int code = e.getKeyCode();

                if(code == KeyEvent.VK_ENTER)
                {
                    setVisible(false);
                    dispose();
                }
            }

            public void keyReleased(KeyEvent e)
            {}

            public void keyTyped(KeyEvent e)
            {}
        });

        jpanel1.add("left.bind.center.center", jlabel);
        jpanel1.add("unbound.bind.left.center", mlabel);
        Container container = getContentPane();
        container.setLayout(new VerticalLayout(2, 5, 5, 2, 2));
        container.add("top.bind.center.center", jpanel1);
        container.add("bottom.bind.center.center", jpanel);
        container.add("bottom.bind.center.center", new Separator());
        container.add("unbound.bind.center.center", detailScroll);
        if(dialogDetailMessage == null)
            jtogglebutton.setVisible(false);
        if(parentFrame == null)
            addWindowListener(new WindowAdapter() {

                public void windowDeactivated(WindowEvent windowevent)
                {
                    requestFocus();
                }

            });
        pack();
        validate();
        centerToScreen();
        jbutton.requestFocus();
    }

    public static void main(String args[])
    {
        post("Hi There", "Wow\nI can't believe\nthis works!", "Title", 1);
    }

    private void parseExceptionStack(Exception exception)
    {
        dialogType = 3;
        if(exception instanceof InterfaceExceptionStack)
        {
            InterfaceExceptionStack interfaceexceptionstack = (InterfaceExceptionStack)exception;
            int ai[] = interfaceexceptionstack.getErrorSeverities();
            switch(ai[0])
            {
            case 3: // '\003'
                dialogType = 3;
                if(dialogTitle == null)
                    dialogTitle = appReg.getStringMessage("error.TITLE");
                break;

            case 2: // '\002'
                dialogType = 2;
                if(dialogTitle == null)
                    dialogTitle = appReg.getStringMessage("warning.TITLE");
                break;

            case 1: // '\001'
                dialogType = 1;
                if(dialogTitle == null)
                    dialogTitle = appReg.getStringMessage("information.TITLE");
                break;

            case 4: // '\004'
                dialogType = 1;
                if(dialogTitle == null)
                    dialogTitle = appReg.getStringMessage("information.TITLE");
                break;

            default:
                dialogType = 3;
                if(dialogTitle == null)
                    dialogTitle = appReg.getStringMessage("error.TITLE");
                break;
            }
            String as[] = interfaceexceptionstack.getErrorStack();
            if(dialogMessage == null)
                dialogMessage = as[0];
            String s = new String();
            for(int i = 1; i < ai.length; i++)
                s = s + as[i] + '\n';

            if(s.length() > 0)
                dialogDetailMessage = s.substring(0, s.length() - 1);
        } else
        {
            dialogType = 3;
            if(dialogTitle == null)
                dialogTitle = appReg.getStringMessage("error.TITLE");
            if(dialogMessage == null)
                dialogMessage = exception.toString();
        }
    }


    public static void post(JApplet applet, String s, String s1, int i)
    {
        MessageBox messagebox = new MessageBox(applet, s, ((String) (null)), s1, i);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

    public static void post(JApplet applet, String s, String s1, String s2, int i)
    {
        MessageBox messagebox = new MessageBox(applet, s, s1, s2, i);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

    public static void post(JFrame frame, String s, String s1, int i)
    {
        MessageBox messagebox = new MessageBox(frame, s, ((String) (null)), s1, i);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

    public static void post(JFrame frame, String s, String s1, String s2, int i)
    {
        MessageBox messagebox = new MessageBox(frame, s, s1, s2, i);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

    public static void post(Exception exception)
    {
        MessageBox messagebox = new MessageBox(exception);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

    public static void post(String s, Exception exception, String s1, int i)
    {
        MessageBox messagebox = new MessageBox(s, exception, s1, i);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

    public static void post(String s, String s1, int i)
    {
        MessageBox messagebox = new MessageBox(s, ((String) (null)), s1, i);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

    public static void post(String s, String s1, String s2, int i)
    {
        MessageBox messagebox = new MessageBox(s, s1, s2, i);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

    private void resizeDialog()
    {
        Dimension dimension = getPreferredSize();
        Insets insets = getInsets();
        if((int)((double)dimension.height * 1.5D) > dimension.width)
            dimension.width = (int)((double)dimension.height * 1.5D);
        setSize(dimension.width + insets.right, dimension.height + insets.bottom);
    }

}
