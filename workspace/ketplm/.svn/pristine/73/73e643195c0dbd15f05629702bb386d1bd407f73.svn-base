// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Registry.java

package e3ps.bom.framework.util;

import java.awt.Color;
import java.awt.Font;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

// Referenced classes of package e3ps.bom.framework.util:
//            Instancer

public class Registry
{
    private class RegistrySearchEntry
    {

        public String getBaseName()
        {
            return baseName;
        }

        public ResourceBundle getResourceBundle()
        {
            return resourceBundle;
        }

        private ResourceBundle resourceBundle;
        private String baseName;

        public RegistrySearchEntry(String s, ResourceBundle resourcebundle)
        {
            baseName = s;
            resourceBundle = resourcebundle;
        }
    }


    private Registry(String s)
        throws MissingResourceException
    {
        registrySearchPath = null;
        registryBaseName = null;
        registryBaseName = s;
        registrySearchPath = new LinkedList();
        loadPackage(s);
        registryCache.put(s, new SoftReference(this));
    }

    private String[] createStringArray(String s, String s1)
    {
        String as[] = (String[])null;
        if(s != null)
        {
            if(s1 == null)
                s1 = ",";
            StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
            int i = stringtokenizer.countTokens();
            if(i > 0)
            {
                as = new String[i];
                for(int j = 0; j < i; j++)
                    as[j] = (new String(stringtokenizer.nextToken())).trim();

            }
        }
        return as;
    }

    public static void flushRegistryCache()
    {
        registryCache = new Hashtable();
        overrideTable = new Hashtable();
    }

    public boolean getBoolean(String s, boolean flag)
    {
        try
        {
            String s1 = getStringValue(s);
            return s1.equals("1") || s1.equalsIgnoreCase("TRUE");
        }
        catch(MissingResourceException _ex)
        {
            return flag;
        }
    }

    private static Registry getCachedRegistry(String s)
    {
        SoftReference softreference = (SoftReference)registryCache.get(s);
        if(softreference != null)
            return (Registry)softreference.get();
        else
            return null;
    }

    public char getChar(String s, char c)
    {
        try
        {
            String s1 = getStringValue(s);
            if(s1.length() > 0)
                return s1.charAt(0);
            else
                return c;
        }
        catch(MissingResourceException _ex)
        {
            return c;
        }
    }

    public Color getColor(String s)
    {
        return getColor(s, Color.white);
    }

    public Color getColor(String s, Color color)
    {
        String as[] = (String[])null;
        Color color1 = color;
        as = getStringArray(s);
        if(as == null)
            return color;
        if(as.length == 1)
        {
            String s1 = as[0];
            if(s1.equalsIgnoreCase("black"))
                color1 = Color.black;
            else
            if(s1.equalsIgnoreCase("blue"))
                color1 = Color.blue;
            else
            if(s1.equalsIgnoreCase("cyan"))
                color1 = Color.cyan;
            else
            if(s1.equalsIgnoreCase("darkGray"))
                color1 = Color.darkGray;
            else
            if(s1.equalsIgnoreCase("gray"))
                color1 = Color.gray;
            else
            if(s1.equalsIgnoreCase("green"))
                color1 = Color.green;
            else
            if(s1.equalsIgnoreCase("lightGray"))
                color1 = Color.lightGray;
            else
            if(s1.equalsIgnoreCase("magenta"))
                color1 = Color.magenta;
            else
            if(s1.equalsIgnoreCase("orange"))
                color1 = Color.orange;
            else
            if(s1.equalsIgnoreCase("pink"))
                color1 = Color.pink;
            else
            if(s1.equalsIgnoreCase("red"))
                color1 = Color.red;
            else
            if(s1.equalsIgnoreCase("white"))
                color1 = Color.white;
            else
            if(s1.equalsIgnoreCase("yellow"))
                color1 = Color.yellow;
        } else
        if(as.length == 3)
        {
            Integer integer = new Integer(as[0]);
            integer.intValue();
            int i = integer.intValue();
            integer = new Integer(as[1]);
            int j = integer.intValue();
            integer = new Integer(as[2]);
            int k = integer.intValue();
            color1 = new Color(i, j, k);
        }
        return color1;
    }

    public Font getFont(String s)
    {
        return getFont(s, null);
    }

    public Font getFont(String s, Font font)
    {
        String s1 = "TimesRoman";
        int i = 0;
        int j = 10;
        String as[] = getStringArray(s);
        if(as != null && as.length == 3)
        {
            s1 = as[0];
            if(as[1].equals("PLAIN"))
            {
                i = 0;
            } else
            {
                String s2 = "|";
                StringTokenizer stringtokenizer = new StringTokenizer(as[1], s2);
                int l = stringtokenizer.countTokens();
                if(l > 0)
                {
                    i = 0;
                    for(int i1 = 0; i1 < l; i1++)
                    {
                        String s3 = stringtokenizer.nextToken().trim();
                        if(s3.equals("ITALIC"))
                            i |= 2;
                        else
                        if(s3.equals("BOLD"))
                            i |= 1;
                    }

                }
            }
            int k = 0;
            try
            {
                k = Integer.parseInt(as[2]);
            }
            catch(Exception exception) { }
            if(k > 0)
                j = k;
        }
        return new Font(s1, i, j);
    }

    public ImageIcon getImageIcon(String s)
    {
        ImageIcon imageicon = null;
        java.net.URL url = null;
        if(s == null)
            return null;
        String s1;
        try
        {
            s1 = getStringValue(s);
        }
        catch(MissingResourceException _ex)
        {
            return null;
        }
        if(s1.startsWith("/"))
        {
            url = getClass().getResource(s1);
        } else
        {
            for(ListIterator listiterator = registrySearchPath.listIterator(); listiterator.hasNext();)
            {
                RegistrySearchEntry registrysearchentry = (RegistrySearchEntry)listiterator.next();
                String s2 = registrysearchentry.getBaseName();
                int i = s2.lastIndexOf(".");
                String s4 = "/" + s2.substring(0, i + 1);
                String s3 = s4.replace('.', '/') + s1;
                url = getClass().getResource(s3);
                if(url != null)
                    break;
            }

        }
        if(url != null)
            imageicon = new ImageIcon(url);
        return imageicon;
    }

    public int getInt(String s, int i)
    {
        try
        {
            String s1 = getStringValue(s);
            Integer integer = new Integer(s1);
            return integer.intValue();
        }
        catch(MissingResourceException _ex)
        {
            return i;
        }
    }

    public long getLong(String s, long l)
    {
        try
        {
            String s1 = getStringValue(s);
            Long long1 = new Long(s1);
            return long1.longValue();
        }
        catch(MissingResourceException _ex)
        {
            return l;
        }
    }

    public static Registry getRegistry(Object obj)
        throws MissingResourceException
    {
        String s = null;
        String s1 = obj.getClass().getName();

        int i = s1.lastIndexOf('.');
        if(i != -1)
        {
            int j = s1.lastIndexOf('.', i - 1);
            s = s1.substring(0, i + 1) + s1.substring(j + 1, i);
        } else
        {
            s = s1;
        }
        return initialize(s);
    }

    public static Registry getRegistry(Object obj, String s)
        throws MissingResourceException
    {
        String s1 = null;
        String s2 = obj.getClass().getName();
        int i = s2.lastIndexOf('.');
        if(i > 0)
            s1 = s2.substring(0, i + 1) + s;
        else
            s1 = s2 + '.' + s;
        return initialize(s1);
    }

    public static Registry getRegistry(String s)
        throws MissingResourceException
    {
        return initialize(s);
    }

    public String getRegistryBaseName()
    {
        return registryBaseName;
    }

    public String getString(String s)
    {
        try
        {
            String str = getNewStringFromXml(s);
            if (str != null) {
                return str;
            }

            //return getStringValue(s);

            String new_str = null;
            try {
                    if (s == null)
                    {
                        new_str = "";
                    }else{
                        new_str = new String(getStringValue(s).getBytes("8859_1"), "KSC5601");
                    }
            } catch (Exception ex) {
            Kogger.debug(getClass(), "asc2ksc : " + ex.getMessage());
            }
            return new_str;

        }
        catch(MissingResourceException missingresourceexception)
        {
            Kogger.error(getClass(), missingresourceexception); // for test
            return s;
        }
    }

    public String getStringMessage(String s)
    {
        try
        {
            String str = getNewStringFromXml(s);
            if (str != null) {
                return str;
            }

            return getStringValue(s);
        }
        catch(MissingResourceException missingresourceexception)
        {
            Kogger.error(getClass(), missingresourceexception); // for test
            return s;
        }
    }

    public String getString(String s, String s1)
    {
        try
        {
            String str = getNewStringFromXml(s);
            if (str != null) {
                return str;
            }

            String new_str = null;
            try {
                    if (s == null)
                    {
                        new_str = "";
                    }else{
                        new_str = new String(getStringValue(s).getBytes("8859_1"), "KSC5601");
                    }
            } catch (Exception ex) {
            Kogger.debug(getClass(), "asc2ksc : " + ex.getMessage());
            }
            return new_str;
            //return getStringValue(s);
        }
        catch(MissingResourceException ex)
        {
            Kogger.error(getClass(), ex); // for test
            return s1;
        }
    }

    public String[] getStringArray(String s)
    {
        return getStringArray(s, null);
    }

    public String[] getStringArray(String s, String s1)
    {
        String s2 = null;
        String as[] = (String[])null;
        try
        {
            s2 = getStringValue(s);
        }
        catch(MissingResourceException _ex)
        {
            return null;
        }
        as = createStringArray(s2, s1);
        return as;
    }

    public String[] getStringArray(String s, String s1, String s2)
    {
        String s3 = null;
        String as[] = (String[])null;
        try
        {
            s3 = getStringValue(s);
        }
        catch(MissingResourceException _ex)
        {
            s3 = s2;
        }
        as = createStringArray(s3, s1);
        return as;
    }

    private String getStringValue(String s)
        throws MissingResourceException
    {
        MissingResourceException missingresourceexception = null;
        String s1 = null;
        s1 = (String)overrideTable.get(s);
        if(s1 != null)
            return s1.trim();
        for(ListIterator listiterator = registrySearchPath.listIterator(); listiterator.hasNext();)
        {
            RegistrySearchEntry registrysearchentry = (RegistrySearchEntry)listiterator.next();
            try
            {
                ResourceBundle resourcebundle = registrysearchentry.getResourceBundle();
                s1 = resourcebundle.getString(s);
                break;
            }
            catch(MissingResourceException missingresourceexception1)
            {
                missingresourceexception = missingresourceexception1;
            }
        }

        if(s1 != null)
            return s1.trim();
        else
            throw missingresourceexception;
    }

    private static Registry initialize(String s)
        throws MissingResourceException
    {
        Registry registry = getCachedRegistry(s);
        if(registry == null)
            return new Registry(s);
        else
            return registry;
    }

    private boolean isLoaded(String s)
    {
        boolean flag = false;
        for(ListIterator listiterator = registrySearchPath.listIterator(); listiterator.hasNext();)
        {
            RegistrySearchEntry registrysearchentry = (RegistrySearchEntry)listiterator.next();
            String s1 = registrysearchentry.getBaseName();
            if(s1.equals(s))
            {
                flag = true;
                break;
            }
        }

        return flag;
    }

    private void loadImportPackages(ResourceBundle resourcebundle, ResourceBundle resourcebundle1, ResourceBundle resourcebundle2)
    {
        String s = null;
        try
        {
            if(resourcebundle2 != null)
                s = resourcebundle2.getString("import");
        }
        catch(Exception ex)
        {
        }

        try
        {
            if(s == null && resourcebundle1 != null)
                s = resourcebundle1.getString("import");
        }
        catch(Exception ex)
        {
        }

        try
        {
            if(s == null && resourcebundle != null)
                s = resourcebundle.getString("import");
        }
        catch(Exception ex)
        {
        }

        if(s != null)
        {
            String as[] = createStringArray(s, ",");
            for(int i = 0; i < as.length; i++)
                try
                {
                    if(!isLoaded(as[i]))
                        loadPackage(as[i]);
                }
                catch(Exception ex)
                {
                }

        }
    }

    private void loadPackage(String s)
        throws MissingResourceException
    {
        String s1 = s;
        ResourceBundle resourcebundle = ResourceBundle.getBundle(s1);
        registrySearchPath.add(new RegistrySearchEntry(s1, resourcebundle));
        loadImportPackages(resourcebundle, null, null);
    }

    public Object newInstanceFor(String s)
    {
        Object obj = null;
        try
        {
            obj = newInstanceForEx(s);
        }
        catch(Exception exception) { }
        return obj;
    }

    public Object newInstanceFor(String s, Object obj)
    {
        Object obj1 = null;
        try
        {
            obj1 = newInstanceForEx(s, obj);
        }
        catch(Exception exception) { }
        return obj1;
    }

    public Object newInstanceFor(String s, Object aobj[])
    {
        Object obj = null;
        try
        {
            obj = newInstanceForEx(s, aobj);
        }
        catch(Exception exception) { }
        return obj;
    }

    public Object newInstanceForEx(String s)
        throws MissingResourceException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        String s1 = getStringValue(s);
        return Instancer.newInstanceEx(s1);
    }

    public Object newInstanceForEx(String s, Object obj)
        throws MissingResourceException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        String s1 = getStringValue(s);
        return Instancer.newInstanceEx(s1, obj);
    }

    public Object newInstanceForEx(String s, Object aobj[])
        throws MissingResourceException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        String s1 = getStringValue(s);
        return Instancer.newInstanceEx(s1, aobj);
    }

    public void printRegistryHierarchy()
    {
        RegistrySearchEntry registrysearchentry;
        for(ListIterator listiterator = registrySearchPath.listIterator(); listiterator.hasNext(); Kogger.debug(getClass(), registrysearchentry.getBaseName()))
            registrysearchentry = (RegistrySearchEntry)listiterator.next();

        Kogger.debug(getClass(), "\nEnd of Printout........\n\n");
    }

    public static void setString(String s, String s1)
    {
        if(s1 == null)
            s1 = new String();
        overrideTable.put(s, s1);
    }

    /*
     * [PLM System 1차개선] 내용 : 다국어 처리, 2013. 08. 13, 김무준
     */
    private String getNewStringFromXml(String key) {
        String str = null;
        try {
            // from new 'bom_editor_message' bundle
            if ( "e3ps.bom.message".equals(registryBaseName) // for 'message.properties'
                    || ("e3ps.bom.bom".equals(registryBaseName) && (key.endsWith(".NAME") || key.endsWith(".TIP"))) ) { // for 'NAME' and 'TIP' part of 'bom.properties'
                str = KETMessageService.service.getString("e3ps.bom.bom_editor_message", key);
            }
        }
        catch(Exception e) {
            Kogger.debug(getClass(), "getNewStringFromXml: " + e.toString());
        }
        return str;
    }

    private static Hashtable registryCache = new Hashtable();
    private LinkedList registrySearchPath;
    private static Hashtable overrideTable = new Hashtable();
    private String registryBaseName;

}
