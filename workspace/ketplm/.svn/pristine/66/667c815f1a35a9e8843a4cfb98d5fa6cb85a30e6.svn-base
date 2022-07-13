// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AIFUIApplicationMenuBar.java

package e3ps.bom.framework.aif;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Stack;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.MenuElement;
import javax.swing.UIManager;

import e3ps.bom.common.util.FontList;
import e3ps.bom.framework.util.Instancer;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;

// Referenced classes of package e3ps.bom.framework.aif:
//            AbstractAIFUIApplication

public class AIFUIApplicationMenuBar extends JMenuBar
{
    private class LookAndFeelMenuItem extends JCheckBoxMenuItem
        implements PropertyChangeListener
    {

        public void close()
        {
            UIManager.removePropertyChangeListener(this);
        }

        public void propertyChange(PropertyChangeEvent propertychangeevent)
        {
            Object obj = propertychangeevent.getSource();
            if(propertychangeevent.getPropertyName().equals("lookAndFeel"))
            {
                LookAndFeelMenuItem lookandfeelmenuitem = this;
                if(lookandfeelmenuitem.getText().equals(UIManager.getLookAndFeel().getName()))
				{
                    lookandfeelmenuitem.setSelected(true);
				}
                else
				{
                    lookandfeelmenuitem.setSelected(false);
				}
            }
        }

        public LookAndFeelMenuItem(String s, ImageIcon imageicon)
        {
            super(s, imageicon);
            UIManager.addPropertyChangeListener(this);
        }
    }


    public AIFUIApplicationMenuBar(AbstractAIFUIApplication abstractaifuiapplication)
    {
        application = abstractaifuiapplication;
        registry = abstractaifuiapplication.getRegistry();
    }

    public void addHelpMenu()
    {
        add(loadHelpMenu());
    }

    public JCheckBoxMenuItem addCheckBoxMenuItem(JMenu jmenu, String s)
    {
        return addCheckBoxMenuItem(jmenu, s, -1);
    }

    public JCheckBoxMenuItem addCheckBoxMenuItem(JMenu jmenu, String s, int i)
    {
        JCheckBoxMenuItem jcheckboxmenuitem = new JCheckBoxMenuItem();
        initializeMenuItem(jmenu, s, i, jcheckboxmenuitem);
        return jcheckboxmenuitem;
    }

    public JMenuItem addMenuItem(JMenu jmenu, String s)
    {
        return addMenuItem(jmenu, s, -1);
    }

    public JMenuItem addMenuItem(JMenu jmenu, String s, int i)
    {
        JMenuItem jmenuitem = new JMenuItem();
        initializeMenuItem(jmenu, s, i, jmenuitem);
        return jmenuitem;
    }

    public JRadioButtonMenuItem addRadioButtonMenuItem(JMenu jmenu, String s)
    {
        return addRadioButtonMenuItem(jmenu, s, -1);
    }

    public JRadioButtonMenuItem addRadioButtonMenuItem(JMenu jmenu, String s, int i)
    {
        JRadioButtonMenuItem jradiobuttonmenuitem = new JRadioButtonMenuItem();
        initializeMenuItem(jmenu, s, i, jradiobuttonmenuitem);
        return jradiobuttonmenuitem;
    }

    public void close()
    {
        for(; !lookAndFeelMenuItems.empty(); ((LookAndFeelMenuItem)lookAndFeelMenuItems.pop()).close());
    }

    protected JMenu findMenu(String s)
    {
        return findMenuEntry(s, getSubElements());
    }

    private JMenu findMenuEntry(String s, MenuElement amenuelement[])
    {
        if(amenuelement != null)
        {
            for(int i = 0; i < amenuelement.length; i++)
                if(amenuelement[i].getComponent() instanceof JMenu)
                {
                    JMenu jmenu = (JMenu)amenuelement[i].getComponent();
                    if(s.equals(jmenu.getActionCommand()))
                        return jmenu;
                }

            for(int j = 0; j < amenuelement.length;)
                return findMenuEntry(s, amenuelement[j].getSubElements());

        }
        return null;
    }

    private void initializeMenuItem(JMenu jmenu, String s, int i, JMenuItem jmenuitem)
    {
        String s1 = registry.getString(s + "." + "NAME");
        ImageIcon imageicon = registry.getImageIcon(s + "." + "ICON");
        jmenuitem.setIcon(imageicon);
        jmenuitem.setText(s1);
		jmenu.setFont(FontList.defaultFont);
		jmenuitem.setFont(FontList.defaultFont);
        if(i < 0)
            jmenu.add(jmenuitem);
        else
            jmenu.insert(jmenuitem, i);
        jmenuitem.setHorizontalTextPosition(4);
        jmenuitem.setVerticalTextPosition(0);
        char c = registry.getChar(s + "." + "MNEMONIC", ' ');
        if(c != ' ')
            jmenuitem.setMnemonic(c);
        jmenuitem.setActionCommand(s);
        char c1 = registry.getChar(s + "." + "ACCELERATOR", ' ');
        jmenuitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                application.performAction(((JMenuItem)actionevent.getSource()).getActionCommand());
            }

        });
        jmenuitem.addActionListener(application);
    }

    private JMenu loadDesktopMenu()
    {
        JMenu jmenu = new JMenu(registry.getString("desktopMenu.NAME"));
        jmenu.setMnemonic(registry.getChar("desktopMenu.MNEMONIC", 'D'));
        jmenu.setActionCommand("desktopMenu");
        JMenuItem jmenuitem = addMenuItem(jmenu, "newDesktopAction");
        jmenu.addSeparator();
        JMenu jmenu1 = new JMenu(registry.getString("lookAndFeelMenu.NAME"));
        jmenu1.setMnemonic(registry.getChar("lookAndFeelMenu.MNEMONIC", 'L'));
        jmenu1.setActionCommand("lookAndFeelMenu");
        jmenu.add(jmenu1);
        javax.swing.UIManager.LookAndFeelInfo alookandfeelinfo[] = UIManager.getInstalledLookAndFeels();
        ButtonGroup buttongroup = new ButtonGroup();
        lookAndFeelMenuItems = new Stack();
        for(int i = 0; i < alookandfeelinfo.length; i++)
        {
            LookAndFeelMenuItem lookandfeelmenuitem = new LookAndFeelMenuItem(alookandfeelinfo[i].getName(), registry.getImageIcon("LookAndFeel.ICON"));
            lookandfeelmenuitem.setActionCommand(alookandfeelinfo[i].getClassName());
            buttongroup.add(lookandfeelmenuitem);
            lookAndFeelMenuItems.push(lookandfeelmenuitem);
            if(alookandfeelinfo[i].getName().equals(UIManager.getLookAndFeel().getName()))
                lookandfeelmenuitem.setSelected(true);
            lookandfeelmenuitem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent actionevent)
                {
                    try
                    {
                        JCheckBoxMenuItem jcheckboxmenuitem = (JCheckBoxMenuItem)actionevent.getSource();
                        UIManager.setLookAndFeel((LookAndFeel)Instancer.newInstance(jcheckboxmenuitem.getActionCommand()));
                    }
                    catch(Exception exception)
                    {
                        MessageBox messagebox = new MessageBox(exception);
                        messagebox.setVisible(true);
                    }
                }

            });
            jmenu1.add(lookandfeelmenuitem);
        }

        jmenu.addSeparator();
        JMenuItem jmenuitem1 = jmenu.add(new JCheckBoxMenuItem(registry.getString("showToolBarAction.NAME"), true));
        jmenuitem1.setMnemonic(registry.getChar("showToolBarAction.MNEMONIC", 'T'));
        jmenuitem1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent1)
            {
            }

        });
        return jmenu;
    }

    private JMenu loadFileMenu()
    {
        JMenu jmenu = new JMenu(registry.getString("fileMenu.NAME"));
        jmenu.setMnemonic(registry.getChar("fileMenu.MNEMONIC", 'F'));
        jmenu.setActionCommand("fileMenu");
        addMenuItem(jmenu, "closeAction");
        addMenuItem(jmenu, "exitAction");
        return jmenu;
    }

    private JMenu loadHelpMenu()
    {
        JMenu jmenu = new JMenu(registry.getString("helpMenu.NAME"));
        jmenu.setMnemonic(registry.getChar("helpMenu.MNEMONIC", 'H'));
        jmenu.setActionCommand("helpMenu");
        JMenu jmenu1 = new JMenu(registry.getString("helpMenu.NAME"));
        jmenu1.setMnemonic(registry.getChar("helpMenu.MNEMONIC", 'H'));
        jmenu1.setActionCommand("helpMenu");
        jmenu.add(jmenu1);
        JMenuItem jmenuitem = addMenuItem(jmenu1, "frameworkHelpAction");
        JMenuItem jmenuitem1 = addMenuItem(jmenu1, "applicationHelpAction");
        jmenu.addSeparator();
        addMenuItem(jmenu, "aboutAction");
        return jmenu;
    }

    protected AbstractAIFUIApplication application;
    protected Registry registry;
    private static final String SEPARATOR = "separator";
    private Stack lookAndFeelMenuItems;
}
