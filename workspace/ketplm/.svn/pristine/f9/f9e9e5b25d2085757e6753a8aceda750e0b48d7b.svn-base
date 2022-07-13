// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AIFDesktopToolBar.java

package e3ps.bom.framework.aif;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import e3ps.bom.framework.util.HorizontalLayout;
import e3ps.bom.framework.util.Registry;

public class AIFDesktopToolBar extends JPanel
{

    public AIFDesktopToolBar(JFrame desktop)
        throws Exception
    {
        super(new HorizontalLayout(8, 2, 2, 2, 2));
        Registry registry = Registry.getRegistry(desktop);
        applicationToolbar = null;
    }

    public void setToolBar(JToolBar jtoolbar)
    {
        if(applicationToolbar != null)
            remove(applicationToolbar);
        if(jtoolbar != null)
        {
            jtoolbar.setFloatable(false);
            add("left", jtoolbar);
        }
        applicationToolbar = jtoolbar;
    }

    private JFrame desktop;
    private JToolBar applicationToolbar;
}
