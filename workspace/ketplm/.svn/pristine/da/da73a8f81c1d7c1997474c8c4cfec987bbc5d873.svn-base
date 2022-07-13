// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ButtonLayout.java

package e3ps.bom.framework.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class ButtonLayout
    implements LayoutManager
{

    public ButtonLayout()
    {
        this(1, 1, 10);
    }

    public ButtonLayout(int i)
    {
        this(i, 1, 10);
    }

    public ButtonLayout(int i, int j, int k)
    {
        if(i != 1 && i != 2)
            i = 1;
        if(j != 1 && j != 2 && j != 3 && j != 4 && j != 5)
            j = 1;
        if(k < 0)
            k = 10;
        gap = k;
        orientation = i;
        alignment = j;
    }

    public void addLayoutComponent(String s1, Component component1)
    {
    }

    public int getAlignment()
    {
        return alignment;
    }

    private Dimension getButtonMinimumSize(Container container)
    {
        int i = container.getComponentCount();
        Dimension dimension = new Dimension(0, 0);
        for(int j = 0; j < i; j++)
        {
            Component component = container.getComponent(j);
            Dimension dimension1 = component.getMinimumSize();
            dimension.width = Math.max(dimension.width, dimension1.width);
            dimension.height = Math.max(dimension.height, dimension1.height);
        }

        dimension.width = Math.max(dimension.width, 20);
        dimension.height = Math.max(dimension.height, 15);
        return dimension;
    }

    private Dimension getButtonPreferredSize(Container container)
    {
        int i = container.getComponentCount();
        Dimension dimension = new Dimension(0, 0);
        for(int j = 0; j < i; j++)
        {
            Component component = container.getComponent(j);
            Dimension dimension1 = component.getPreferredSize();
            dimension.width = Math.max(dimension.width, dimension1.width);
            dimension.height = Math.max(dimension.height, dimension1.height);
        }

        dimension.width = Math.max(dimension.width, 20);
        dimension.height = Math.max(dimension.height, 15);
        return dimension;
    }

    public int getGap()
    {
        return gap;
    }

    public int getOrientation()
    {
        return orientation;
    }

    public void layoutContainer(Container container)
    {
        Insets insets = container.getInsets();
        int k = container.getComponentCount();
        Dimension dimension = container.getPreferredSize();
        Dimension dimension1 = container.getSize();
        Dimension dimension2 = getButtonPreferredSize(container);
        if(orientation == 1)
        {
            int l;
            int j1;
            if(alignment == 2)
            {
                l = insets.left;
                j1 = (dimension1.height - dimension.height) / 2;
            } else
            if(alignment == 1)
            {
                l = (dimension1.width - dimension.width) / 2;
                j1 = (dimension1.height - dimension.height) / 2;
            } else
            {
                l = dimension1.width - dimension.width;
                j1 = (dimension1.height - dimension.height) / 2;
            }
            for(int i = 0; i < k; i++)
            {
                Component component = container.getComponent(i);
                if(component.isVisible())
                {
                    component.setBounds(l, j1, dimension2.width, dimension2.height);
                    l += dimension2.width + gap;
                }
            }

        } else
        {
            int i1;
            int k1;
            if(alignment == 4)
            {
                i1 = (dimension1.width - dimension.width) / 2;
                k1 = insets.top;
            } else
            if(alignment == 1)
            {
                i1 = (dimension1.width - dimension.width) / 2;
                k1 = (dimension1.height - dimension.height) / 2;
            } else
            {
                i1 = (dimension1.width - dimension.width) / 2;
                k1 = dimension1.height - dimension.height;
            }
            for(int j = 0; j < k; j++)
            {
                Component component1 = container.getComponent(j);
                if(component1.isVisible())
                {
                    component1.setBounds(i1, k1, dimension2.width, dimension2.height);
                    k1 += dimension2.height + gap;
                }
            }

        }
    }

    public Dimension minimumLayoutSize(Container container)
    {
        int j = container.getComponentCount();
        int k = 0;
        Dimension dimension1 = new Dimension(0, 0);
        Insets insets = container.getInsets();
        Dimension dimension = getButtonMinimumSize(container);
        for(int i = 0; i < j; i++)
        {
            Component component = container.getComponent(i);
            if(component.isVisible())
                k++;
        }

        if(orientation == 1 && k > 0)
        {
            dimension1.width = dimension.width * k + gap * (k - 1) + insets.left + insets.right;
            dimension1.height = dimension.height + insets.top + insets.bottom;
        } else
        if(k > 0)
        {
            dimension1.width = insets.left + dimension.width + insets.right;
            dimension1.height = dimension.height * k + gap * (k - 1) + insets.top + insets.bottom;
        }
        return dimension1;
    }

    public Dimension preferredLayoutSize(Container container)
    {
        int j = container.getComponentCount();
        int k = 0;
        Dimension dimension1 = new Dimension(0, 0);
        Insets insets = container.getInsets();
        Dimension dimension = getButtonPreferredSize(container);
        for(int i = 0; i < j; i++)
        {
            Component component = container.getComponent(i);
            if(component.isVisible())
                k++;
        }

        if(orientation == 1 && k > 0)
        {
            dimension1.width = dimension.width * k + gap * (k - 1) + insets.left + insets.right;
            dimension1.height = dimension.height + insets.top + insets.bottom;
        } else
        if(k > 0)
        {
            dimension1.width = insets.left + dimension.width + insets.right;
            dimension1.height = dimension.height * k + gap * (k - 1) + insets.top + insets.bottom;
        }
        return dimension1;
    }

    public void removeLayoutComponent(Component component1)
    {
    }

    public void setAlignment(int i)
    {
        alignment = i;
    }

    public void setGap(int i)
    {
        gap = i;
    }

    public void setOrientation(int i)
    {
        if(i == 1)
            orientation = 1;
        else
            orientation = 2;
    }

    private int gap;
    private int orientation;
    private int alignment;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    public static final int CENTER = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int TOP = 4;
    public static final int BOTTOM = 5;
    public static final int DEFAULTGAP = 10;
    public static final int MINBUTTONWIDTH = 20;
    public static final int MINBUTTONHEIGHT = 15;
}
