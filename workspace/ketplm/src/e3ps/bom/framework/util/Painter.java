// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Painter.java

package e3ps.bom.framework.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

public class Painter
{

    public Painter()
    {
    }

    public static void paintIsRequired(Component component, Graphics g)
    {
        Dimension dimension = component.getSize();
        int ai[] = {
            dimension.width - 8, dimension.width, dimension.width, dimension.width - 8
        };
        int ai1[] = {
            0, 0, 7, 0
        };
        g.setColor(Color.red);
        g.fillPolygon(ai, ai1, 4);
    }
}
