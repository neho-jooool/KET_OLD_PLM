// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractAIFDialog.java

package e3ps.bom.framework.aif;

import ext.ket.shared.log.Kogger;
import gui.JMouseWheelDialog;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public abstract class AbstractAIFDialog extends JMouseWheelDialog
    implements Runnable
{

    public AbstractAIFDialog()
    {
        manageModal = false;
        setDefaultCloseOperation(2);
        addDebugMouseListener();
    }

    public AbstractAIFDialog(JFrame frame)
    {
        super(frame);
        manageModal = false;
        setDefaultCloseOperation(2);
        addDebugMouseListener();
    }

    public AbstractAIFDialog(JFrame frame, String s)
    {
        super(frame, s);
        manageModal = false;
        setDefaultCloseOperation(2);
        addDebugMouseListener();
    }

    public AbstractAIFDialog(JFrame frame, String s, boolean flag)
    {
        super(frame, s, flag);
        manageModal = false;
        setDefaultCloseOperation(2);
        addDebugMouseListener();
    }

    public AbstractAIFDialog(JFrame frame, boolean flag)
    {
        super(frame, flag);
        manageModal = false;
        setDefaultCloseOperation(2);
        addDebugMouseListener();
    }

    public AbstractAIFDialog(boolean flag)
    {
        manageModal = false;
        manageModal = flag;
        setModal(flag);
        setDefaultCloseOperation(2);
        addDebugMouseListener();
    }

    private void addDebugMouseListener()
    {
        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent mouseevent)
            {
                if(mouseevent.isShiftDown() && mouseevent.isControlDown() && (mouseevent.isAltDown() || mouseevent.isMetaDown()))
                {
                    int i = mouseevent.getModifiers();
                    doMouseDebug((i & 0x10) == 16 ? 1 : (i & 4) == 4 ? 3 : (i & 8) == 8 ? 2 : 0);
                }
            }

        });
    }

    public void centerToScreen()
    {
        centerToScreen(1.0D, 1.0D, 0.0D);
    }

    public void centerToScreen(double d, double d1)
    {
        centerToScreen(d, d1, 0.0D);
    }

    public void centerToScreen(double d, double d1, double d2)
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension1 = getPreferredSize();
        Insets insets = getInsets();
        if(d1 == 1.0D && d2 > 0.0D && d2 < 1.0D)
        {
            int k = (int)(d2 * (double)dimension.height);
            if(dimension1.height > k)
                dimension1.height = k;
        } else
        if(d == 1.0D && d2 > 0.0D && d2 < 1.0D)
        {
            int l = (int)(d2 * (double)dimension.width);
            if(dimension1.width > l)
                dimension1.width = l;
        }
        if(d1 != 1.0D && d1 > 0.0D)
        {
            if((int)((double)dimension1.width * d1) > dimension1.height)
                dimension1.height = (int)((double)dimension1.width * d1);
        } else
        if(d != 1.0D && d > 0.0D && (int)((double)dimension1.height * d) > dimension1.width)
            dimension1.width = (int)((double)dimension1.height * d);
        int i = (dimension.width - dimension1.width) / 2;
        int j = (dimension.height - dimension1.height) / 2;
        setBounds(i, j, dimension1.width + insets.right, dimension1.height + insets.bottom);
    }

    public void centerToScreen(double d, double d1, double d2, double d3)
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension1 = getPreferredSize();
        Insets insets = getInsets();
        if(d1 != 1.0D && d1 > 0.0D)
        {
            if((int)((double)dimension1.width * d1) > dimension1.height)
                dimension1.height = (int)((double)dimension1.width * d1);
        } else
        if(d != 1.0D && d > 0.0D && (int)((double)dimension1.height * d) > dimension1.width)
            dimension1.width = (int)((double)dimension1.height * d);
        if(d2 > 0.0D && d2 <= 1.0D && (double)dimension1.width > d2 * (double)dimension.width)
            dimension1.width = (int)(d2 * (double)dimension.width);
        if(d3 > 0.0D && d3 <= 1.0D && (double)dimension1.height > d3 * (double)dimension.height)
            dimension1.height = (int)(d3 * (double)dimension.height);
        int i = (dimension.width - dimension1.width) / 2;
        int j = (dimension.height - dimension1.height) / 2;
        setBounds(i, j, dimension1.width + insets.right, dimension1.height + insets.bottom);
    }

    public void dispose()
    {
        super.dispose();
    }

    protected void doMouseDebug(int i)
    {
        printComponent("", this);
        printKids("  ", getContentPane().getComponents());
        if(i == 3)
        {
            Container container = getContentPane();
            for(Container container1 = container.getParent(); container1 != null; container1 = container1.getParent())
                container = container1;

            printKids("  ", new Component[] {
                container
            });
        }
    }

    public boolean managesModal()
    {
        return manageModal;
    }

    private void printComponent(String s, Component component)
    {
        String s1 = s + component.getClass().getName() + " [" + Integer.toString(component.getWidth()) + "," + Integer.toString(component.getHeight()) + "]";
        if(component instanceof JLabel)
            s1 = s1 + " (\"" + ((JLabel)component).getText() + "\")";
        else
        if(component instanceof AbstractButton)
            s1 = s1 + " (\"" + ((AbstractButton)component).getText() + "\")";
        Kogger.debug(getClass(), s1 + (component.isVisible() ? "" : " (invisible)"));
    }

    private void printKids(String s, Component acomponent[])
    {
        String s1 = new String(s + "  ");
        for(int i = 0; i < acomponent.length; i++)
        {
            printComponent(s, acomponent[i]);
            if(acomponent[i] instanceof Container)
                printKids(s1, ((Container)acomponent[i]).getComponents());
        }

    }

    public void run()
    {
        pack();
        validate();
        centerToScreen();
        setVisible(true);
    }

    protected boolean safeToDispose()
    {
        return true;
    }

    private boolean manageModal;
}
