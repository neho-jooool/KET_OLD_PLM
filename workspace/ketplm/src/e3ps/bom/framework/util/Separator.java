// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Separator.java

package e3ps.bom.framework.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.SystemColor;

import javax.swing.JComponent;

public class Separator extends JComponent
{

    public Separator()
    {
        this(defaultStyle, defaultOrientation, defaultThickness);
    }

    public Separator(int i)
    {
        this(i, defaultOrientation, defaultThickness);
    }

    public Separator(int i, int j, int k)
    {
        style = i;
        orientation = j;
        thickness = k;
        setSize(thickness, thickness);
    }

    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    public int getOrientation()
    {
        return orientation;
    }

    public Dimension getPreferredSize()
    {
        Dimension dimension = new Dimension(0, 0);
        if(orientation == 1)
            dimension.height = thickness;
        else
            dimension.width = thickness;
        return dimension;
    }

    public int getStyle()
    {
        return style;
    }

    public void paint(Graphics g)
    {
        if(orientation == 1)
        {
            if(style == 1)
                paintHorizontal(g, SystemColor.controlShadow, SystemColor.controlLtHighlight);
            else
            if(style == 2)
                paintHorizontal(g, SystemColor.controlLtHighlight, SystemColor.controlShadow);
            else
                paintHorizontal(g, SystemColor.controlShadow, SystemColor.controlShadow);
        } else
        if(style == 1)
            paintVertical(g, SystemColor.controlLtHighlight, SystemColor.controlShadow);
        else
        if(style == 2)
            paintVertical(g, SystemColor.controlShadow, SystemColor.controlLtHighlight);
        else
            paintVertical(g, SystemColor.controlShadow, SystemColor.controlShadow);
    }

    private void paintHorizontal(Graphics g, Color color, Color color1)
    {
        Dimension dimension = getSize();
        g.setColor(color);
        g.fillRect(0, dimension.height / 2 - thickness / 2, dimension.width, thickness / 2);
        g.setColor(color1);
        g.fillRect(0, dimension.height / 2, dimension.width, thickness / 2);
    }

    private void paintVertical(Graphics g, Color color, Color color1)
    {
        Dimension dimension = getSize();
        g.setColor(color);
        g.fillRect(dimension.width / 2 - thickness / 2, 0, thickness / 2, dimension.height);
        g.setColor(color1);
        g.fillRect(dimension.width / 2, 0, thickness / 2, dimension.height);
    }

    public void setOrientation(int i)
    {
        if(i == 1)
            orientation = i;
        else
            orientation = 2;
    }

    public void setStyle(int i)
    {
        if(i == 1)
            style = i;
        else
        if(i == 2)
            style = i;
        else
            style = 3;
    }

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    public static final int ETCHED_IN = 1;
    public static final int ETCHED_OUT = 2;
    public static final int LINE = 3;
    private static int defaultStyle = 1;
    private static int defaultThickness = 2;
    private static int defaultOrientation = 1;
    private int orientation;
    private int thickness;
    private int style;

}
