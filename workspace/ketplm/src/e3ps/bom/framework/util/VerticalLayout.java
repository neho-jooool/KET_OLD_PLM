// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VerticalLayout.java

package e3ps.bom.framework.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.StringTokenizer;
import java.util.Vector;

import ext.ket.shared.log.Kogger;

public class VerticalLayout
    implements LayoutManager
{

    public VerticalLayout()
    {
        this(5);
    }

    public VerticalLayout(int i)
    {
        this(i, 0, 0, 0, 0);
    }

    public VerticalLayout(int i, int j, int k, int l, int i1)
    {
        if(i < 0)
            verticalGap = 5;
        else
            verticalGap = i;
        attachmentData = new Vector();
        leftMargin = j;
        rightMargin = k;
        topMargin = l;
        bottomMargin = i1;
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
                if(s1.equalsIgnoreCase("BOTTOM"))
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

    public int getVerticalGap()
    {
        return verticalGap;
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
            int i = locateTopComponents(container);
            int j = locateBottomComponents(container);
            locateUnBoundComponents(container, i, j);
            return;
        }
    }

    private int locateBottomComponents(Container container)
    {
        int i1 = container.getComponentCount();
        Insets insets = container.getInsets();
        Dimension dimension = container.getSize();
        int k = dimension.height - insets.bottom - bottomMargin;
        for(int l = 0; l < i1; l++)
        {
            int j1 = askAttachmentType(l);
            if(j1 == 2)
            {
                Component component = container.getComponent(l);
                if(component.isVisible())
                {
                    Dimension dimension1 = component.getPreferredSize();
                    int l1 = askBinding(l);
                    int j;
                    if(l1 == 1)
                    {
                        j = insets.left + leftMargin;
                        dimension1.width = dimension.width - insets.left - insets.right - leftMargin - rightMargin;
                    } else
                    {
                        int k1 = askHorizontalAlignment(l);
                        if(k1 == 1)
                            j = insets.left + leftMargin;
                        else
                        if(k1 == 3)
                            j = insets.left + leftMargin + (dimension.width - dimension1.width - insets.right - insets.left - rightMargin - leftMargin) / 2;
                        else
                            j = dimension.width - insets.right - rightMargin - dimension1.width;
                    }
                    k -= dimension1.height;
                    component.setBounds(j, k, dimension1.width, dimension1.height);
                    k -= verticalGap;
                }
            }
        }

        return k;
    }

    private int locateTopComponents(Container container)
    {
        int i1 = container.getComponentCount();
        Insets insets = container.getInsets();
        int k = insets.top + topMargin;
        Dimension dimension1 = container.getSize();
        for(int l = 0; l < i1; l++)
        {
            int j1 = askAttachmentType(l);
            if(j1 == 1)
            {
                Component component = container.getComponent(l);
                if(component.isVisible())
                {
                    Dimension dimension = component.getPreferredSize();
                    int l1 = askBinding(l);
                    int j;
                    if(l1 == 1)
                    {
                        j = insets.left + leftMargin;
                        dimension.width = dimension1.width - insets.left - insets.right - leftMargin - rightMargin;
                    } else
                    {
                        int k1 = askHorizontalAlignment(l);
                        if(k1 == 1)
                            j = insets.left + leftMargin;
                        else
                        if(k1 == 3)
                            j = insets.left + leftMargin + (dimension1.width - dimension.width - insets.right - insets.left - rightMargin - leftMargin) / 2;
                        else
                            j = dimension1.width - insets.right - rightMargin - dimension.width;
                    }
                    component.setBounds(j, k, dimension.width, dimension.height);
                    k += dimension.height + verticalGap;
                }
            }
        }

        return k;
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
        int k1 = (j - i - (k - 1) * verticalGap) / k;
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
                        dimension1.width = dimension.width - insets.right - insets.left - leftMargin - rightMargin;
                    int l1 = insets.left + leftMargin;
                    int i2 = j2;
                    dimension1.height = k1;
                    if(k3 == 0)
                    {
                        if(i3 == 2)
                            l1 = dimension.width - insets.right - rightMargin - dimension1.width;
                        else
                        if(i3 == 3)
                            l1 = insets.left + leftMargin + (dimension.width - dimension1.width - insets.right - insets.left - rightMargin - leftMargin) / 2;
                    } else
                    {
                        l1 = insets.left + leftMargin;
                    }
                    if(j3 == 2)
                        i2 += k1 - dimension1.height;
                    else
                    if(j3 == 3)
                        i2 += (k1 - dimension1.height) / 2;
                    if(l1 < insets.left)
                        l1 = insets.left;
                    if(i2 < j2)
                        i2 = j2;
                    component1.setBounds(l1, i2, dimension1.width, dimension1.height);
                    j2 += k1 + verticalGap;
                }
            }
        }

    }

    public Dimension minimumLayoutSize(Container container)
    {
        int j = container.getComponentCount();
        int k = 0;
        int l = 0;
        Insets insets = container.getInsets();
        Dimension dimension = new Dimension(0, 0);
        for(int i = 0; i < j; i++)
        {
            Component component = container.getComponent(i);
            if(component.isVisible())
            {
                Dimension dimension1 = component.getMinimumSize();
                dimension.width = Math.max(dimension.width, dimension1.width);
                dimension.height += dimension1.height;
                k++;
            }
        }

        if(k > 1)
            l = (k - 1) * verticalGap;
        l += topMargin + bottomMargin;
        dimension.width += insets.left + insets.right + leftMargin + rightMargin;
        dimension.height += insets.top + insets.bottom + l;
        return dimension;
    }

    public Dimension preferredLayoutSize(Container container)
    {
        int j = container.getComponentCount();
        int k = 0;
        int l = 0;
        Insets insets = container.getInsets();
        Dimension dimension = new Dimension(0, 0);
        for(int i = 0; i < j; i++)
        {
            Component component = container.getComponent(i);
            if(component.isVisible())
            {
                Dimension dimension1 = component.getPreferredSize();
                dimension.width = Math.max(dimension.width, dimension1.width);
                dimension.height += dimension1.height;
                k++;
            }
        }

        if(k > 1)
            l = (k - 1) * verticalGap;
        l += topMargin + bottomMargin;
        dimension.width += insets.left + insets.right + leftMargin + rightMargin;
        dimension.height += insets.top + insets.bottom + l;
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

    public void setVerticalGap(int i)
    {
        verticalGap = i;
    }

    private int verticalGap;
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
    public static final int GAP = 5;
}
