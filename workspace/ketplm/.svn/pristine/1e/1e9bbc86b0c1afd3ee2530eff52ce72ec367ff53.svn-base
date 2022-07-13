// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Picture.java

package e3ps.bom.framework.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Picture extends JComponent
{

    public Picture()
    {
        imageSizing = 2;
        horizontalAlignment = 3;
        verticalAlignment = 3;
        image = null;
        preferredSize = new Dimension(0, 0);
    }

    public Picture(File file)
    {
        image = new ImageIcon(file.getAbsolutePath());
        preferredSize = new Dimension(image.getIconWidth(), image.getIconHeight());
    }

    public Picture(Object obj, String s)
    {
        URL url = obj.getClass().getResource(s);
        image = new ImageIcon(url);
        preferredSize = new Dimension(image.getIconWidth(), image.getIconHeight());
    }

    public Picture(URL url)
    {
        image = new ImageIcon(url);
        preferredSize = new Dimension(image.getIconWidth(), image.getIconHeight());
    }

    public Picture(ImageIcon imageicon)
    {
        image = imageicon;
        preferredSize = new Dimension(image.getIconWidth(), image.getIconHeight());
    }

    private Rectangle calculateImageSize()
    {
        Dimension dimension = getSize();
        Dimension dimension1 = getSize();
        Dimension dimension2 = getPreferredSize();
        int i = 0;
        int j = 0;
        if(imageSizing == 1)
        {
            if(horizontalAlignment == 1)
                i = 0;
            else
            if(horizontalAlignment == 3)
                i = (dimension.width - image.getIconWidth()) / 2;
            else
                i = dimension.width - image.getIconWidth();
            if(i < 0)
                i = 0;
            if(verticalAlignment == 1)
                j = 0;
            else
            if(verticalAlignment == 3)
                j = (dimension.height - image.getIconHeight()) / 2;
            else
                j = dimension.height - image.getIconHeight();
            if(j < 0)
                j = 0;
        } else
        if(dimension2.width > 0 && dimension2.height > 0)
        {
            double d = (double)dimension.width / (double)dimension2.width;
            if((int)((double)dimension2.height * d) < dimension.height)
            {
                dimension.height = (int)((double)dimension2.height * d);
            } else
            {
                double d1 = (double)dimension.height / (double)dimension2.height;
                dimension.width = (int)((double)dimension2.width * d1);
            }
            i = (dimension1.width - dimension.width) / 2;
            j = (dimension1.height - dimension.height) / 2;
        }
        return new Rectangle(i, j, dimension.width, dimension.height);
    }

    public int getHorizontalAlignment()
    {
        return horizontalAlignment;
    }

    public ImageIcon getImage()
    {
        return image;
    }

    public int getImageSizing()
    {
        return imageSizing;
    }

    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    public Dimension getPreferredSize()
    {
        Dimension dimension = new Dimension(0, 0);
        dimension.width = preferredSize.width;
        dimension.height = preferredSize.height;
        return dimension;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if(image != null)
        {
            Rectangle rectangle = calculateImageSize();
            if(imageSizing == 1)
                g.drawImage(image.getImage(), rectangle.x, rectangle.y, this);
            else
                g.drawImage(image.getImage(), rectangle.x, rectangle.y, rectangle.width, rectangle.height, this);
        }
    }

    public void setBounds(int i, int j, int k, int l)
    {
        super.setBounds(i, j, k, l);
        repaint();
    }

    public void setHorizontalAlignment(int i)
    {
        horizontalAlignment = i;
    }

    public void setImage(ImageIcon imageicon)
    {
        image = imageicon;
    }

    public void setImageSizing(int i)
    {
        imageSizing = i;
    }

    public void setPreferredSize(Dimension dimension)
    {
        preferredSize.width = dimension.width;
        preferredSize.height = dimension.height;
    }

    public void setVerticalAlignment(int i)
    {
        verticalAlignment = i;
    }

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int CENTER = 3;
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int AS_LOADED = 1;
    public static final int AUTO_FIT = 2;
    private int imageSizing;
    private int horizontalAlignment;
    private int verticalAlignment;
    private ImageIcon image;
    private Dimension preferredSize;
}
