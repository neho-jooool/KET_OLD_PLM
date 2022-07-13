// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HorizontalLayout.java

package e3ps.bom.framework.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.StringTokenizer;
import java.util.Vector;

import ext.ket.shared.log.Kogger;

public class HorizontalLayout
    implements LayoutManager
{

    public HorizontalLayout()
    {
        this(5);
    }

    public HorizontalLayout(int i)
    {
        this(i, 0, 0, 0, 0);
    }

    public HorizontalLayout(int i, int j, int k, int l, int i1)
    {
        if(i < 0)
            horizontalGap = 5;
        else
            horizontalGap = i;
        topMargin = l;
        bottomMargin = i1;
        leftMargin = j;
        rightMargin = k;
        attachmentData = new Vector();
    }

    public void addLayoutComponent(String s, Component component)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, ".");
        Object aobj[] = new Object[7];
        int i = stringtokenizer.countTokens();
        aobj[0] = new Integer(1);
        aobj[1] = new Integer(1);
        aobj[2] = new Integer(3);
        aobj[3] = new Integer(3);
        aobj[4] = component;
        for(int j = 0; j < i; j++)
        {
            String s1 = stringtokenizer.nextToken();
            switch(j)
            {
            default:
                break;

            case 0: // '\0'
                if(s1.equalsIgnoreCase("RIGHT"))
                {
                    aobj[0] = new Integer(2);
                    break;
                }
                if(s1.equalsIgnoreCase("UNBOUND"))
                    aobj[0] = new Integer(3);
                break;

            case 1: // '\001'
                if(s1.equalsIgnoreCase("NOBIND"))
                    aobj[1] = new Integer(0);
                break;

            case 2: // '\002'
                if(s1.equalsIgnoreCase("LEFT"))
                {
                    aobj[2] = new Integer(1);
                    break;
                }
                if(s1.equalsIgnoreCase("RIGHT"))
                    aobj[2] = new Integer(2);
                break;

            case 3: // '\003'
                if(s1.equalsIgnoreCase("TOP"))
                {
                    aobj[3] = new Integer(1);
                    break;
                }
                if(s1.equalsIgnoreCase("BOTTOM"))
                    aobj[3] = new Integer(2);
                break;
            }
        }

        attachmentData.addElement(((Object) (aobj)));
    }

    private int askAttachmentType(int i)
    {
        int j = 0;
        Object aobj[] = (Object[])attachmentData.elementAt(i);
        if(aobj != null)
            j = ((Integer)aobj[0]).intValue();
        return j;
    }

    private int askBinding(int i)
    {
        int j = 0;
        Object aobj[] = (Object[])attachmentData.elementAt(i);
        if(aobj != null)
            j = ((Integer)aobj[1]).intValue();
        return j;
    }

    private int askHorizontalAlignment(int i)
    {
        int j = 0;
        j = 0;
        Object aobj[] = (Object[])attachmentData.elementAt(i);
        if(aobj != null)
            j = ((Integer)aobj[2]).intValue();
        return j;
    }

    private int askVerticalAlignment(int i)
    {
        int j = 0;
        Object aobj[] = (Object[])attachmentData.elementAt(i);
        if(aobj != null)
            j = ((Integer)aobj[3]).intValue();
        return j;
    }

    public int getBottomMargin()
    {
        return bottomMargin;
    }

    public int getHorizontalGap()
    {
        return horizontalGap;
    }

    public int getLeftMargin()
    {
        return leftMargin;
    }

    public int getRightMargin()
    {
        return rightMargin;
    }

    public int getTopMargin()
    {
        return topMargin;
    }

    public void layoutContainer(Container container)
    {
        int k = container.getComponentCount();
        if(k != attachmentData.size())
        {
            Kogger.debug(getClass(), "Mismatch of attachmentData\ndeveloper used add() instead of add(String)");
            return;
        } else
        {
            int i = locateLeftComponents(container);
            int j = locateRightComponents(container);
            locateUnBoundComponents(container, i, j);
            return;
        }
    }

    private int locateLeftComponents(Container container)
    {
        int i1 = container.getComponentCount();
        Insets insets = container.getInsets();
        int i = insets.left + leftMargin;
        Dimension dimension1 = container.getSize();
        for(int l = 0; l < i1; l++)
        {
            int j1 = askAttachmentType(l);
            if(j1 == 1)
            {
                Component component = container.getComponent(l);
                if(component.isVisible())
                {
                    int l1 = askBinding(l);
                    Dimension dimension = component.getPreferredSize();
                    int k;
                    if(l1 == 1)
                    {
                        k = insets.top + topMargin;
                        dimension.height = dimension1.height - insets.top - insets.bottom - bottomMargin - topMargin;
                    } else
                    {
                        int k1 = askVerticalAlignment(l);
                        if(k1 == 2)
                            k = dimension1.height - dimension.height - insets.bottom - bottomMargin;
                        else
                        if(k1 == 3)
                            k = insets.top + topMargin + (dimension1.height - insets.top - insets.bottom - topMargin - bottomMargin - dimension.height) / 2;
                        else
                            k = insets.top + topMargin;
                    }
                    component.setBounds(i, k, dimension.width, dimension.height);
                    i += dimension.width + horizontalGap;
                }
            }
        }

        return i;
    }

    private int locateRightComponents(Container container)
    {
        int i1 = container.getComponentCount();
        Insets insets = container.getInsets();
        Dimension dimension = container.getSize();
        int i = dimension.width - insets.right - rightMargin;
        for(int l = 0; l < i1; l++)
        {
            int j1 = askAttachmentType(l);
            if(j1 == 2)
            {
                Component component = container.getComponent(l);
                if(component.isVisible())
                {
                    int l1 = askBinding(l);
                    Dimension dimension1 = component.getPreferredSize();
                    int k;
                    if(l1 == 1)
                    {
                        k = insets.top + topMargin;
                        dimension1.height = dimension.height - insets.top - insets.bottom - bottomMargin - topMargin;
                        i -= dimension1.width;
                    } else
                    {
                        int k1 = askVerticalAlignment(l);
                        if(k1 == 2)
                            k = dimension.height - dimension1.height - insets.bottom - bottomMargin;
                        else
                        if(k1 == 3)
                            k = insets.top + topMargin + (dimension.height - insets.top - insets.bottom - topMargin - bottomMargin - dimension1.height) / 2;
                        else
                            k = insets.top + topMargin;
                        i -= dimension1.width;
                    }
                    component.setBounds(i, k, dimension1.width, dimension1.height);
                    i -= horizontalGap;
                }
            }
        }

        return i;
    }

    private void locateUnBoundComponents(Container container, int i, int j)
    {
        int k = 0;
        int j1 = container.getComponentCount();
        Insets insets = container.getInsets();
        Dimension dimension = container.getSize();
        for(int l = 0; l < j1; l++)
        {
            int k2 = askAttachmentType(l);
            if(k2 == 3)
            {
                Component component = container.getComponent(l);
                if(component.isVisible())
                    k++;
            }
        }

        if(k == 0)
            return;
        int k1 = (j - i - (k - 1) * horizontalGap) / k;
        if(k1 <= 0)
            return;
        int j2 = i;
        for(int i1 = 0; i1 < j1; i1++)
        {
            int l2 = askAttachmentType(i1);
            if(l2 == 3)
            {
                Component component1 = container.getComponent(i1);
                if(component1.isVisible())
                {
                    int i3 = askHorizontalAlignment(i1);
                    int j3 = askVerticalAlignment(i1);
                    int k3 = askBinding(i1);
                    Dimension dimension1 = component1.getPreferredSize();
                    if(k3 == 1)
                        dimension1.height = dimension.height - insets.top - insets.bottom - topMargin - bottomMargin;
                    int l1 = j2;
                    int i2 = insets.top + topMargin;
                    dimension1.width = k1;
                    if(i3 == 2)
                        l1 += k1 - dimension1.width;
                    else
                    if(i3 == 3)
                        l1 += (k1 - dimension1.width) / 2;
                    if(k3 == 0)
                    {
                        if(j3 == 2)
                            i2 = dimension.height - dimension1.height - insets.bottom - bottomMargin;
                        else
                        if(j3 == 3)
                            i2 = insets.top + topMargin + (dimension.height - insets.top - insets.bottom - topMargin - bottomMargin - dimension1.height) / 2;
                    } else
                    {
                        i2 = insets.top + topMargin;
                    }
                    if(l1 < j2)
                        l1 = j2;
                    if(i2 < insets.top)
                        i2 = insets.top;
                    component1.setBounds(l1, i2, dimension1.width, dimension1.height);
                    j2 += k1 + horizontalGap;
                }
            }
        }

    }

    public Dimension minimumLayoutSize(Container container)
    {
        int j = container.getComponentCount();
        int k = 0;
        int l = 0;
        Dimension dimension = new Dimension(0, 0);
        Insets insets = container.getInsets();
        for(int i = 0; i < j; i++)
        {
            Component component = container.getComponent(i);
            if(component.isVisible())
            {
                Dimension dimension1 = component.getMinimumSize();
                dimension.width += dimension1.width;
                dimension.height = Math.max(dimension.height, dimension1.height);
                k++;
            }
        }

        if(k > 1)
            l = (k - 1) * horizontalGap;
        l += leftMargin + rightMargin;
        dimension.width += insets.left + insets.right + l;
        dimension.height += insets.top + insets.bottom + topMargin + bottomMargin;
        return dimension;
    }

    public Dimension preferredLayoutSize(Container container)
    {
        int j = container.getComponentCount();
        int k = 0;
        int l = 0;
        Dimension dimension = new Dimension(0, 0);
        Insets insets = container.getInsets();
        for(int i = 0; i < j; i++)
        {
            Component component = container.getComponent(i);
            if(component.isVisible())
            {
                Dimension dimension1 = component.getPreferredSize();
                dimension.width += dimension1.width;
                dimension.height = Math.max(dimension.height, dimension1.height);
                k++;
            }
        }

        if(k > 1)
            l = (k - 1) * horizontalGap;
        l += leftMargin + rightMargin;
        dimension.width += insets.left + insets.right + l;
        dimension.height += insets.top + insets.bottom + topMargin + bottomMargin;
        return dimension;
    }

    public void removeLayoutComponent(Component component)
    {
        int j = -1;
        for(int i = 0; i < attachmentData.size(); i++)
        {
            Object aobj[] = (Object[])attachmentData.elementAt(i);
            if(aobj[4] == component)
                j = i;
        }

        if(j >= 0)
            attachmentData.removeElementAt(j);
    }

    public void setBottomMargin(int i)
    {
        bottomMargin = i;
    }

    public void setHorizontalGap(int i)
    {
        horizontalGap = i;
    }

    public void setLeftMargin(int i)
    {
        leftMargin = i;
    }

    public void setRightMargin(int i)
    {
        rightMargin = i;
    }

    public void setTopMargin(int i)
    {
        topMargin = i;
    }

    private int horizontalGap;
    private Vector attachmentData;
    private int leftMargin;
    private int rightMargin;
    private int topMargin;
    private int bottomMargin;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int CENTER = 3;
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int UNBOUND = 3;
    public static final int BIND = 1;
    public static final int NOBIND = 0;
    public static final int DEFAULTGAP = 5;
}
