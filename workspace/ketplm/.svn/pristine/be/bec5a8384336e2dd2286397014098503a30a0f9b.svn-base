// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractAIFUIApplication.java

package e3ps.bom.framework.aif;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;

import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.framework.util.SignalOnClose;
import ext.ket.shared.log.Kogger;

// Referenced classes of package e3ps.bom.framework.aif:
//            AIFUIApplicationMenuBar, AbstractAIFUIApplicationToolBar

public abstract class AbstractAIFUIApplication
    implements ActionListener
{

    public AbstractAIFUIApplication(JFrame desktop)
        throws Exception
    {
        this.desktop = desktop;
        actions = new Hashtable();
        popupableMenuElements = new Hashtable();
        menuElementButtons = new Hashtable();
        menuBar = loadMenuBar();
        parseMenuBarActions(menuBar.getSubElements());
        toolBar = loadToolBar();
        appPanel = loadPanel();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = actionevent.getSource();
        AbstractButton abstractbutton = null;
        if(obj instanceof AbstractButton)
            abstractbutton = (AbstractButton)obj;
        else
            return;
        String s = abstractbutton.getActionCommand();
        if(s == null)
            return;
        JMenuItem jmenuitem = getMenuItem(s);
        if(jmenuitem != null && jmenuitem.isSelected() != abstractbutton.isSelected())
            jmenuitem.setSelected(abstractbutton.isSelected());
        AbstractButton abstractbutton1 = toolBar.findButton(s);
        if(abstractbutton1 != null && abstractbutton1.isSelected() != abstractbutton.isSelected())
            abstractbutton1.setSelected(abstractbutton.isSelected());
    }

    public Registry getRegistry()
    {
        Registry registry = null;
        registry = Registry.getRegistry(this);
        return registry;
    }

    protected boolean actionPopupAble(String s)
    {
        return getRegistry().getBoolean(s + "." + "POPUP", false);
    }

    public boolean close()
        throws Exception
    {
        SignalOnClose.close(getApplicationPanel());
        menuBar.close();
        return true;
    }

    public void expand()
    {
    }

    public void expandBelow()
    {
    }

    public ActionListener getAction(String s)
    {
        ActionListener actionlistener = (ActionListener)actions.get(s);
        if(actionlistener == null)
        {
            actionlistener = loadAction(s);
            if(actionlistener != null)
                actions.put(s, actionlistener);
        }
        return actionlistener;
    }

    public ActionListener getAction(String s, Registry registry)
    {
        ActionListener actionlistener = (ActionListener)actions.get(s);
        if(actionlistener == null)
        {
            actionlistener = loadAction(s, registry);
            if(actionlistener != null)
                actions.put(s, actionlistener);
        }
        if(actionlistener == null)
            return getAction(s);
        else
            return actionlistener;
    }

    public JMenuBar getApplicationMenuBar()
    {
        return menuBar;
    }

    public JPanel getApplicationPanel()
    {
        return appPanel;
    }

    public JToolBar getApplicationToolBar()
    {
        return toolBar;
    }

    public JFrame getDesktop()
    {
        return desktop;
    }

    public JMenu getMenu(String s)
    {
        AbstractButton abstractbutton = getMenuButton(s);
        if(abstractbutton != null && (abstractbutton instanceof JMenu))
            return (JMenu)abstractbutton;
        else
            return null;
    }

    public AbstractButton getMenuButton(String s)
    {
        return (AbstractButton)menuElementButtons.get(s);
    }

    public JMenuItem getMenuItem(String s)
    {
        AbstractButton abstractbutton = getMenuButton(s);
        Kogger.debug(getClass(), "");
        if(abstractbutton != null && (abstractbutton instanceof JMenuItem))
            return (JMenuItem)abstractbutton;
        else
            return null;
    }

    public void initializeDisplay()
    {
    }

    public boolean isPrintSupported()
    {
        return false;
    }

    private ActionListener loadAction(String s)
    {
        Registry registry = getRegistry();
        Object obj = (ActionListener)registry.newInstanceFor(s, new Object[] {
            this, s
        });
        return (ActionListener)obj;
    }

    private ActionListener loadAction(String s, Registry registry)
    {
        ActionListener actionlistener = (ActionListener)registry.newInstanceFor(s, new Object[] {
            this, registry, s
        });
        return actionlistener;
    }

    protected AIFUIApplicationMenuBar loadMenuBar()
        throws Exception
    {
        Object obj = null;
        Registry registry = getRegistry();
        String s = new String(getClass().getName() + "." + "MENUBAR");
        if(registry.getString(s, null) == null)
        {
            String s1 = registry.getString("DESKTOP_DEFAULT_APPLICATION");
            s = new String(s1 + "." + "MENUBAR");
        }
        return (AIFUIApplicationMenuBar)registry.newInstanceForEx(s, this);
    }

    protected JPanel loadPanel()
        throws Exception
    {
        Registry registry = getRegistry();
        String s = new String(getClass().getName() + "." + "PANEL");
        if(registry.getString(s, null) == null)
        {
            String s1 = registry.getString("DESKTOP_DEFAULT_APPLICATION");
            s = new String(s1 + "." + "PANEL");
        }
        return (JPanel)registry.newInstanceForEx(s, this);
    }

    protected AbstractAIFUIApplicationToolBar loadToolBar()
///        throws Exception
    {
		try
		{
			Registry registry = getRegistry();
			String s = new String(getClass().getName() + "." + "TOOLBAR");

			if(registry.getString(s, null) == null)
			{
				String s1 = registry.getString("DESKTOP_DEFAULT_APPLICATION");
				s = new String(s1 + "." + "TOOLBAR");
			}
	        return (AbstractAIFUIApplicationToolBar)registry.newInstanceForEx(s, this);			
		}
		catch(Exception ex)
		{
			Kogger.error(getClass(), ex);
			return null;
		}
    }

    public boolean needsServerDuringClose()
    {
        return false;
    }

    private void parseMenuBarActions(MenuElement amenuelement[])
    {
        if(amenuelement != null)
        {
            for(int i = 0; i < amenuelement.length; i++)
            {
                if(amenuelement[i].getComponent() instanceof AbstractButton)
                {
                    String s = ((AbstractButton)amenuelement[i].getComponent()).getActionCommand();
                    if(s != null)
                    {
                        menuElementButtons.put(s, amenuelement[i].getComponent());
                        if(actionPopupAble(s))
                            popupableMenuElements.put(s, amenuelement[i].getComponent());
                    }
                }
                parseMenuBarActions(amenuelement[i].getSubElements());
            }

        }
    }

    public void performAction(String s)
    {
        ActionListener actionlistener = getAction(s);
        if(actionlistener != null)
            actionlistener.actionPerformed(null);
    }

    public void print(boolean flag)
    {
        if(isPrintSupported())
        {
            Registry registry = Registry.getRegistry("ext.lsis.bom.framework.aif.aif");
            MessageBox.post(getDesktop(), registry.getString("noPrint.MSG"), registry.getString("noPrint.TITLE"), 3);
        }
    }

    protected void refreshMenuBarActions()
    {
        popupableMenuElements = new Hashtable();
        menuElementButtons = new Hashtable();
        parseMenuBarActions(menuBar.getSubElements());
    }

    public JPanel reloadPanel()
    {
        JPanel jpanel = null;
        try
        {
            jpanel = loadPanel();
        }
        catch(Exception exception) { }
        return jpanel;
    }

    public void setApplicationMenuBar(AIFUIApplicationMenuBar jmenubar)
    {
        menuBar = jmenubar;
    }

    public void setApplicationPanel(JPanel jpanel)
    {
        appPanel = jpanel;
    }

    public void setApplicationToolBar(AbstractAIFUIApplicationToolBar jtoolbar)
    {
        toolBar = jtoolbar;
    }

    public void toggleToolBarDisplayCommand()
    {
    }

    public void updateLookAndFeel()
    {
        JPanel jpanel = getApplicationPanel();
        JMenuBar jmenubar = getApplicationMenuBar();
        JToolBar jtoolbar = getApplicationToolBar();
        if(jpanel != null)
            SwingUtilities.updateComponentTreeUI(jpanel);
        if(jmenubar != null)
            SwingUtilities.updateComponentTreeUI(jmenubar);
        if(jtoolbar != null)
            SwingUtilities.updateComponentTreeUI(jtoolbar);
    }

    private JFrame desktop;
    private JPanel appPanel;
    private AIFUIApplicationMenuBar menuBar;
    private AbstractAIFUIApplicationToolBar toolBar;
    private Hashtable actions;
    private Hashtable popupableMenuElements;
    private Hashtable menuElementButtons;
}
