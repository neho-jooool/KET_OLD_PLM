// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AIFImageIcon.java

package e3ps.bom.framework.util;

import javax.swing.ImageIcon;

import ext.ket.shared.log.Kogger;

public class AIFImageIcon extends ImageIcon
{

    public AIFImageIcon()
    {
    }

    public static ImageIcon getImageIcon(Object obj, String s)
    {
        ImageIcon imageicon = null;
        if(obj == null || s == null)
            return null;
        Class class1 = obj.getClass();
        Class class2 = class1;
        java.net.URL url = null;
        while(class2 != null) 
        {
            url = class1.getResource(s);
            if(url != null)
                break;
            if(url == null)
            {
                class1 = class2;
                class2 = class1.getSuperclass();
            }
        }
        if(url != null)
            imageicon = new ImageIcon(url);
        return imageicon;
    }

    public static ImageIcon getImageIcon(String s, String s1)
    {
        ImageIcon imageicon = null;
        java.net.URL url = null;
        if(s == null || s1 == null)
            return null;
        try
        {
            url = Class.forName(s).getResource(s1);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            Kogger.debug(AIFImageIcon.class, classnotfoundexception);
        }
        if(url != null)
            imageicon = new ImageIcon(url);
        return imageicon;
    }
}
