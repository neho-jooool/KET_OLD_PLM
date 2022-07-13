// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractAIFUIApplicationToolBar.java

package e3ps.bom.framework.aif;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import e3ps.bom.common.util.BorderList;
import e3ps.bom.framework.util.AIFImageIcon;
import e3ps.bom.framework.util.Registry;

// Referenced classes of package e3ps.bom.framework.aif:
//            AbstractAIFUIApplication

public abstract class AbstractAIFUIApplicationToolBar extends JToolBar
{
    public AbstractAIFUIApplicationToolBar(AbstractAIFUIApplication abstractaifuiapplication)
    {
        application = null;
        registry = null;
        application = abstractaifuiapplication;
        registry = abstractaifuiapplication.getRegistry();
        setBorderPainted(false);
    }

    protected JButton addToolBarButton(String s)
    {
        javax.swing.ImageIcon imageicon = registry.getImageIcon(s + "." + "ICON");
        if(imageicon == null)
            imageicon = AIFImageIcon.getImageIcon(this, "images/defaultactionicon.gif");
        JButton jbutton = new JButton(imageicon);
        add(jbutton);
        initializeButton(s, jbutton);
        return jbutton;
    }

    protected JButton addToolBarSmallButton(String s)
    {
        javax.swing.ImageIcon imageicon = registry.getImageIcon(s + "." + "ICON");
        if(imageicon == null)
            imageicon = AIFImageIcon.getImageIcon(this, "images/defaultactionicon.gif");
        JButton jbutton = new JButton(imageicon);
		jbutton.setBorder(BorderList.emptyBorder5);
		jbutton.setRequestFocusEnabled(true);
        add(jbutton);
        initializeButton(s, jbutton);
        return jbutton;
    }

    protected JToggleButton addToolBarToggleButton(String s)
    {
        javax.swing.ImageIcon imageicon = registry.getImageIcon(s + "." + "ICON");
        if(imageicon == null)
            imageicon = AIFImageIcon.getImageIcon(this, "images/defaultactionicon.gif");
        JToggleButton jtogglebutton = new JToggleButton(imageicon);
        add(jtogglebutton);
        initializeButton(s, jtogglebutton);
        return jtogglebutton;
    }

    public AbstractButton findButton(String s)
    {
        AbstractButton abstractbutton = null;
        java.awt.Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            java.awt.Component component = acomponent[i];
            if(!(component instanceof AbstractButton))
                continue;
            AbstractButton abstractbutton1 = (AbstractButton)component;
            String s1 = abstractbutton1.getActionCommand();
            if(!s.equals(s1))
                continue;
            abstractbutton = abstractbutton1;
            break;
        }

        return abstractbutton;
    }

    protected void initializeButton(String s, AbstractButton abstractbutton)
    {
        abstractbutton.setToolTipText(registry.getString(s + "." + "TIP", null));
        abstractbutton.setMargin(new Insets(2, 2, 2, 2));
        abstractbutton.setActionCommand(s);
        abstractbutton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                application.performAction(((AbstractButton)actionevent.getSource()).getActionCommand());
            }

        });
        abstractbutton.addActionListener(application);
    }

    public AbstractAIFUIApplication application;
    public Registry registry;

}
