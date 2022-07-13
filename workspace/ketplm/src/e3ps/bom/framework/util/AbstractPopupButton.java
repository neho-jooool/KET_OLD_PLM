// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractPopupButton.java

package e3ps.bom.framework.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

// Referenced classes of package e3ps.bom.framework.util:
//            InterfaceSignalOnClose, SignalOnClose, Registry, HorizontalLayout

public abstract class AbstractPopupButton extends JToggleButton
    implements InterfaceSignalOnClose
{
    public class RestoreButtonState extends Thread
    {
        public void run()
        {
            try
            {
                Thread.sleep(0L);
            }
            catch(InterruptedException interruptedexception) { }
            if(isSelected())
                setSelected(false);
        }

        public RestoreButtonState()
        {
        }
    }

    private class Banner extends JPanel
    {

        public String getTitle()
        {
            return title.getText();
        }

        public boolean isCloseable()
        {
            boolean flag;
            if(close.getParent() != null)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public boolean isPinnedUp()
        {
            return pinup.isSelected();
        }

        public void setCloseable(boolean flag)
        {
            remove(close);
            if(flag)
                add("right", close);
        }

        public void setPinnableButtonVisible(boolean flag)
        {
            pinup.setVisible(flag);
        }

        public void setPinnedUp(boolean flag)
        {
            pinup.setSelected(flag);
        }

        public void setTitle(String s)
        {
            title.setText(s);
            validate();
            repaint();
        }

        public Banner(String s)
        {
            Registry registry = Registry.getRegistry(this);
            setLayout(new HorizontalLayout(1, 1, 1, 1, 1));
            setBackground(SystemColor.controlShadow);
            pinup = new JToggleButton(registry.getImageIcon("notpinned.ICON"));
            pinup.addActionListener(new ActionListener() 
			{
                public void actionPerformed(ActionEvent actionevent)
                {
                    if(!pinup.isSelected())
					{
                        if(popupWindow.getSubElements().length > 0)
                        {
                            MenuElement amenuelement[] = new MenuElement[2];
                            amenuelement[0] = popupWindow;
                            amenuelement[1] = popupWindow.getSubElements()[0];
                            MenuSelectionManager.defaultManager().setSelectedPath(amenuelement);
                        } else
                        {
                            MenuElement amenuelement1[] = new MenuElement[1];
                            amenuelement1[0] = popupWindow;
                            MenuSelectionManager.defaultManager().setSelectedPath(amenuelement1);
                        }
					}
                }

            });
            pinup.setBackground(SystemColor.controlShadow);
            pinup.setSelectedIcon(registry.getImageIcon("pinned.ICON"));
            pinup.setBorderPainted(false);
            pinup.setFocusPainted(false);
            pinup.setMargin(new Insets(0, 0, 0, 0));
            title = new JLabel(s);
            Font font = title.getFont();
            font = new Font(font.getName(), 2, font.getSize() - 2);
            title.setFont(font);
            title.setForeground(SystemColor.activeCaptionText);
            title.addMouseListener(new MouseAdapter()
			{
                public void mousePressed(MouseEvent mouseevent)
                {
                    if(mouseevent.getClickCount() == 2 && tearoff)
                        tearoff();
                }

            });
            close = new JLabel(registry.getImageIcon("close.ICON"));
            close.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent mouseevent)
                {
                    if(mouseevent.getClickCount() == 1)
                    {
                        requestFocus();
                        postDown();
                    }
                }

            });
            add("left", pinup);
            add("right", close);
            add("unbound", title);
        }
    }


    public AbstractPopupButton()
    {
        popupWindow = null;
        dlg = null;
        lightWeightPopup = JPopupMenu.getDefaultLightWeightPopupEnabled();
        titleBanner = null;
        pinAsPersistent = false;
        latestPinStatus = false;
        orientation = 2;
        suggestedHorizontalAlignment = 1;
        suggestedVerticalAlignment = 2;
        setSelected(false);
        panel = null;
        titleBanner = null;
        tearoff = true;
        addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent actionevent)
            {
                if(isSelected())
                {
                    if(okToPostUp())
                        postUp();
                    else
                        restoreButtonState();
                } else
                {
                    postDown();
                }
            }

        });
    }

    public AbstractPopupButton(String s)
    {
        this();
        setText(s);
    }

    public AbstractPopupButton(String s, Icon icon)
    {
        this();
        setText(s);
        setIcon(icon);
    }

    public AbstractPopupButton(String s, Icon icon, boolean flag)
    {
        this();
        setText(s);
        setIcon(icon);
        setSelected(flag);
    }

    public AbstractPopupButton(String s, boolean flag)
    {
        this();
        setText(s);
        setSelected(flag);
    }

    public AbstractPopupButton(Icon icon)
    {
        this();
        setIcon(icon);
    }

    public AbstractPopupButton(Icon icon, boolean flag)
    {
        this();
        setIcon(icon);
        setSelected(flag);
    }

    public void asPopupWindowComesUp()
    {
    }

    public void beforePopupWindowGoesDown()
    {
    }

    public void closeSignaled()
    {
        if(panel != null)
            SignalOnClose.close(panel);
    }

    private void constructPopupWindow()
    {
        popupWindow = new JPopupMenu() 
		{
            public void paint(Graphics g)
            {
                Dimension dimension = getSize();
                dimension.width--;
                dimension.height--;
                super.paint(g);
                g.setColor(SystemColor.controlShadow);
                g.drawLine(dimension.width, 0, dimension.width, dimension.height);
                g.drawLine(0, dimension.height, dimension.width, dimension.height);
                g.setColor(SystemColor.activeCaptionText);
                g.drawLine(0, 0, dimension.width, 0);
                g.drawLine(0, 0, 0, dimension.height);
            }

            public void setVisible(boolean flag)
            {
                if(!isPinnedUp())
                {
                    if(!flag)
                    {
                        MenuSelectionManager menuselectionmanager = MenuSelectionManager.defaultManager();
                        MenuElement amenuelement[] = menuselectionmanager.getSelectedPath();
                        if(amenuelement.length != 0 && amenuelement[0] != this)
                            MenuSelectionManager.defaultManager().clearSelectedPath();
                    }
                    super.setVisible(flag);
                } else
                {
                    super.setVisible(true);
                }
            }

        };
        popupWindow.setLightWeightPopupEnabled(lightWeightPopup);
        popupWindow.setBorderPainted(false);
        popupWindow.addPopupMenuListener(new PopupMenuListener() 
		{
            public void popupMenuCanceled(PopupMenuEvent popupmenuevent1)
            {
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent popupmenuevent)
            {
                beforePopupWindowGoesDown();
                restoreButtonState();
            }

            public void popupMenuWillBecomeVisible(PopupMenuEvent popupmenuevent)
            {
                asPopupWindowComesUp();
            }

        });
        panel = new JPanel(new BorderLayout());
        initPopupWindow();
        popupWindow.add(panel);
        popupWindow.pack();
        popupWindow.validate();
    }

    public JDialog constructTearoffDialog()
    {
        Object obj = this;
        Frame frame = null;
        for(; obj != null; obj = ((Component)obj).getParent())
        {
            if(!(obj instanceof Frame))
                continue;
            frame = (Frame)obj;
            break;
        }

        JDialog jdialog;
        if(frame != null)
            jdialog = new JDialog(frame);
        else
            jdialog = new JDialog();
        jdialog.setModal(true);
        jdialog.setTitle(getTitle());
        jdialog.getContentPane().setLayout(new BorderLayout());
        jdialog.getContentPane().add("Center", panel);
        return jdialog;
    }

    public JDialog getDialog()
    {
        return dlg;
    }

    public int getOrientation()
    {
        return orientation;
    }

    public JPanel getPanel()
    {
        return panel;
    }

    public JPopupMenu getPopupWindow()
    {
        return popupWindow;
    }

    public int getSuggestedHorizontalAlignment()
    {
        return suggestedHorizontalAlignment;
    }

    public int getSuggestedVerticalAlignment()
    {
        return suggestedVerticalAlignment;
    }

    public String getTitle()
    {
        if(titleBanner != null)
            return titleBanner.getTitle();
        else
            return null;
    }

    public abstract void initPopupWindow();

    public boolean isCloseable()
    {
        boolean flag = false;
        if(titleBanner != null)
            flag = titleBanner.isCloseable();
        return flag;
    }

    public boolean isPinnedUp()
    {
        boolean flag = false;
        if(titleBanner != null)
            flag = titleBanner.isPinnedUp();
        return flag;
    }

    public boolean okToPostUp()
    {
        return true;
    }

    public final void postDown()
    {
        latestPinStatus = isPinnedUp();
        setPinnedUp(false);
        if(popupWindow != null)
            popupWindow.setVisible(false);
        if(!isPinnedUp() && dlg != null)
        {
            dlg.setVisible(false);
            dlg.dispose();
        }
        popupWindow.setFocusable(false);
    }

    public void postUp()
    {
        if(popupWindow == null)
            constructPopupWindow();
        setWindowLocation(popupWindow);
        if(pinAsPersistent || latestPinStatus)
            setPinnedUp(true);
        popupWindow.setInvoker(this);
        popupWindow.setVisible(true);
        popupWindow.requestFocus();
        popupWindow.setFocusable(false);
    }

    public void resetPopupWindow()
    {
        popupWindow.setFocusable(false);
        popupWindow = null;
    }

    private void restoreButtonState()
    {
        RestoreButtonState restorebuttonstate = new RestoreButtonState();
        restorebuttonstate.start();
        popupWindow.setFocusable(false);
    }

    public void setCloseable(boolean flag)
    {
        if(titleBanner != null)
            titleBanner.setCloseable(flag);
        popupWindow.setFocusable(false);
    }

    public void setLightWeightPopupEnabled(boolean flag)
    {
        if(popupWindow != null)
            popupWindow.setLightWeightPopupEnabled(flag);
        lightWeightPopup = flag;
    }

    public void setOrientation(int i)
    {
        if(i == 1 || i == 2)
            orientation = i;
        else
            i = 1;
    }

    public void setPinPersistent(boolean flag)
    {
        pinAsPersistent = flag;
    }

    public void setPinnableButtonVisible(boolean flag)
    {
        if(titleBanner != null)
        {
            titleBanner.setPinnedUp(false);
            titleBanner.setPinnableButtonVisible(flag);
        }
    }

    public void setPinnedUp(boolean flag)
    {
        if(titleBanner != null)
            titleBanner.setPinnedUp(flag);
    }

    public void setPopupTitle(String s)
    {
        if(panel == null)
            return;
        if(titleBanner == null)
        {
            titleBanner = new Banner(s);
            panel.add("North", titleBanner);
        } else
        {
            titleBanner.setTitle(s);
            panel.validate();
            popupWindow.validate();
        }
    }

    public void setPopupTitle(String s, boolean flag, boolean flag1)
    {
        setPopupTitle(s);
        setPinnedUp(flag);
        setPinPersistent(flag1);
    }

    public void setSuggestedHorizontalAlignment(int i)
    {
        if(i == 1 || i == 2)
            suggestedHorizontalAlignment = i;
        else
            i = 1;
    }

    public void setSuggestedVerticalAlignment(int i)
    {
        if(i == 1 || i == 2)
            suggestedVerticalAlignment = i;
        else
            i = 1;
    }

    public void setTearoff(boolean flag)
    {
        tearoff = flag;
    }

    private void setWindowLocation(Container container)
    {
        Point point = getLocationOnScreen();
        Dimension dimension = getSize();
        Dimension dimension1 = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension2 = container.getPreferredSize();
        Point point1 = new Point(0, 0);
        for(int i = 0; i < 8; i++)
        {
            int j = (i & 4) == 4 ? 3 - orientation : orientation;
            int k = (i & 2) == 2 ? 3 - suggestedVerticalAlignment : suggestedVerticalAlignment;
            int l = (i & 1) == 1 ? 3 - suggestedHorizontalAlignment : suggestedHorizontalAlignment;
            if(j == 1)
            {
                if(l == 1)
                    point1.x = point.x - dimension2.width;
                else
                    point1.x = point.x + dimension.width;
                if(k == 1)
                    point1.y = point.y;
                else
                    point1.y = (point.y + dimension.height) - dimension2.height;
            } else
            {
                if(l == 1)
                    point1.x = point.x;
                else
                    point1.x = (point.x + dimension.width) - dimension2.width;
                if(k == 1)
                    point1.y = point.y - dimension2.height;
                else
                    point1.y = point.y + dimension.height;
            }
            if(point1.x > 0 && point1.y > 0 && point1.x + dimension2.width < dimension1.width && point1.y + dimension2.height < dimension1.height)
            {
                container.setLocation(point1.x, point1.y);
                return;
            }
        }

        container.setLocation(point1.x, point1.y);
    }

    public void tearoff()
    {
        setPinnedUp(false);
        postDown();
        popupWindow.remove(panel);
        if(titleBanner != null)
            panel.remove(titleBanner);
        dlg = constructTearoffDialog();
        dlg.setDefaultCloseOperation(0);
        dlg.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                beforePopupWindowGoesDown();
                dlg.setVisible(false);
                dlg.dispose();
            }

        });
        dlg.pack();
        dlg.validate();
        setWindowLocation(dlg);
        dlg.setVisible(true);
        panel.add("North", titleBanner);
        popupWindow.add(panel);
    }

    private JToggleButton pinup;
    private JLabel title;
    private JLabel close;
    private JPopupMenu popupWindow;
    private JDialog dlg;
    private int orientation;
    private int suggestedHorizontalAlignment;
    private int suggestedVerticalAlignment;
    private boolean tearoff;
    private boolean lightWeightPopup;
    private JPanel panel;
    private Banner titleBanner;
    private boolean pinAsPersistent;
    private boolean latestPinStatus;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
}
