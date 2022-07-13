// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageObject.java

package e3ps.bom.framework.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.image.ImageProducer;
import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import ext.ket.shared.log.Kogger;

public class ImageObject extends JComponent
    implements Icon
{

    public ImageObject(File file)
    {
        initializeFilename(file.getAbsolutePath(), 2, 3, 3, true);
    }

    public ImageObject(Class class1, String s, int i, int j, int k, boolean flag)
    {
        initialize(class1, s, i, j, k, flag);
    }

    public ImageObject(Object obj, String s)
    {
        this(obj, s, 1, 3, 3, true);
    }

    public ImageObject(Object obj, String s, int i, int j, int k, boolean flag)
    {
        this(obj.getClass(), s, i, j, k, flag);
    }

    public ImageObject(String s)
    {
        this(s, 1, 3, 3, true);
    }

    public ImageObject(String s, int i, int j, int k, boolean flag)
    {
        this(((Class) (null)), s, i, j, k, flag);
    }

    public ImageObject(String s, String s1)
        throws ClassNotFoundException, Exception
    {
        try
        {
            Class class1 = Class.forName(s);
            initialize(class1, s1, 1, 3, 3, false);
            if(image == null)
                throw new Exception("Unresolved URL:" + s1);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            URL url = getClass().getResource(s1);
            if(url == null)
                throw classnotfoundexception;
            initializeURL(url, 1, 3, 3, false);
        }
    }

    public ImageObject(URL url, int i, int j, int k, boolean flag)
    {
        initializeURL(url, i, j, k, flag);
    }

    private Rectangle calculateImageSize()
    {
        Dimension dimension = getSize();
        Dimension dimension1 = getSize();
        Dimension dimension2 = getPreferredSize();
        int i = 0;
        int j = 0;
        if(imageSize == 1)
        {
            if(horizontalAlignment == 1)
                i = 0;
            else
            if(horizontalAlignment == 3)
                i = (dimension.width - image.getWidth(this)) / 2;
            else
                i = dimension.width - image.getWidth(this);
            if(i < 0)
                i = 0;
            if(verticalAlignment == 1)
                j = 0;
            else
            if(verticalAlignment == 3)
                j = (dimension.height - image.getHeight(this)) / 2;
            else
                j = dimension.height - image.getHeight(this);
            if(j < 0)
                j = 0;
        } else
        if(maintainAspectRatio && dimension2.width > 0 && dimension2.height > 0)
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

    public int getIconHeight()
    {
        if(image == null)
        {
            Kogger.debug(getClass(), "image == null in getIconHeight()");
            return 0;
        } else
        {
            return image.getHeight(this);
        }
    }

    public int getIconWidth()
    {
        if(image == null)
        {
            Kogger.debug(getClass(), "image == null in getIconWidth()");
            return 0;
        } else
        {
            return image.getWidth(this);
        }
    }

    public Image getImage()
    {
        return image;
    }

    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    public Dimension getPreferredSize()
    {
        Dimension dimension = new Dimension(0, 0);
        dimension.width = image.getWidth(this);
        dimension.height = image.getHeight(this);
        return dimension;
    }

    public boolean hasImage()
    {
        return image != null;
    }

    private void initialize(Class class1, String s, int i, int j, int k, boolean flag)
    {
        URL url = null;
        if(s == null)
            return;
        if(class1 != null)
            url = class1.getResource(s);
        if(url == null)
            url = getClass().getResource(s);
        if(url == null)
        {
            Kogger.debug(getClass(), "[" + s + "] does not create a URL");
            if(class1 != null)
        	Kogger.debug(getClass(), "Parent Object = [" + class1.getName() + "]");
            else
        	Kogger.debug(getClass(), "Reference Object = [" + getClass().getName() + "]");
            Kogger.debug(getClass(), "ImageObject Error");
        } else
        {
            initializeURL(url, i, j, k, flag);
        }
    }

    private void initializeFilename(String s, int i, int j, int k, boolean flag)
    {
        if(i == 2)
            imageSize = 2;
        else
            imageSize = 1;
        if(j == 1)
            horizontalAlignment = j;
        else
        if(j == 2)
            horizontalAlignment = j;
        else
            horizontalAlignment = 3;
        if(k == 1)
            verticalAlignment = k;
        else
        if(k == 2)
            verticalAlignment = k;
        else
            verticalAlignment = 3;
        maintainAspectRatio = flag;
        ImageIcon imageicon = new ImageIcon(s);
        image = imageicon.getImage();
    }

    private void initializeURL(URL url, int i, int j, int k, boolean flag)
    {
        MediaTracker mediatracker = new MediaTracker(this);
        imageURL = url;
        if(i == 2)
            imageSize = 2;
        else
            imageSize = 1;
        if(j == 1)
            horizontalAlignment = j;
        else
        if(j == 2)
            horizontalAlignment = j;
        else
            horizontalAlignment = 3;
        if(k == 1)
            verticalAlignment = k;
        else
        if(k == 2)
            verticalAlignment = k;
        else
            verticalAlignment = 3;
        maintainAspectRatio = flag;
        try
        {
            image = createImage((ImageProducer)imageURL.getContent());
        }
        catch(Exception _ex)
        {
            return;
        }
        mediatracker.addImage(image, 0);
        try
        {
            mediatracker.waitForID(0);
        }
        catch(InterruptedException interruptedexception) { }
    }

    public void paint(Graphics g)
    {
        Rectangle rectangle = calculateImageSize();
        if(imageSize == 1)
            g.drawImage(image, rectangle.x, rectangle.y, this);
        else
        if(maintainAspectRatio)
            g.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this);
        else
            g.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this);
    }

    public void paintIcon(Component component, Graphics g, int i, int j)
    {
        if(image != null)
            g.drawImage(image, i, j, this);
    }

    public void setBounds(int i, int j, int k, int l)
    {
        super.setBounds(i, j, k, l);
        repaint();
    }

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int CENTER = 3;
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int AS_LOADED = 1;
    public static final int AUTO_FIT = 2;
    private URL imageURL;
    private int imageSize;
    private int horizontalAlignment;
    private int verticalAlignment;
    private boolean maintainAspectRatio;
    private Image image;
}
