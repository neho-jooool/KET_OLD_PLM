// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Instancer.java

package e3ps.bom.framework.util;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ext.ket.shared.log.Kogger;

public final class Instancer
{

    public Instancer()
    {
    }

    public static Object newInstance(String s)
    {
        Object obj = null;
        try
        {
            obj = newInstanceEx(s);
        }
        catch(Exception exception) { }
        return obj;
    }

    public static Object newInstance(String s, Object obj)
    {
        Object obj1 = null;
        try
        {
            obj1 = newInstanceEx(s, obj);
        }
        catch(Exception exception) { }
        return obj1;
    }

    public static Object newInstance(String s, Object aobj[])
    {
        Object obj = null;
        try
        {
            obj = newInstanceEx(s, aobj);
        }
        catch(Exception exception) { }
        return obj;
    }

    public static Object newInstanceEx(String s)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Object obj = null;
        Class class1;
        try
        {
            class1 = Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            Kogger.error(Instancer.class, "Unable to find class = [" + s + "]");
            throw classnotfoundexception;
        }
        try
        {
            obj = class1.newInstance();
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            Kogger.error(Instancer.class, "Illegal Access Exception in instantiating class Name [" + s + "]");
            throw illegalaccessexception;
        }
        catch(InstantiationException instantiationexception)
        {
            Kogger.error(Instancer.class, "Error in instantiating class Name [" + s + "]");
            throw instantiationexception;
        }
        return obj;
    }

    public static Object newInstanceEx(String s, Object obj)
        throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        return newInstanceEx(s, new Object[] {
            obj
        });
    }

    public static Object newInstanceEx(String s, Object aobj[])
        throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Object obj = null;
        Class class1;
        try
        {
            class1 = Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            Kogger.error(Instancer.class, "Class Not Found [" + s + "]");
            throw classnotfoundexception;
        }
        if(class1.isInterface())
        {
            Kogger.error(Instancer.class, "Class specification name [" + s + "] is an Interface and cannot be instanced!");
            return null;
        }
        Constructor aconstructor[] = class1.getConstructors();
        Constructor constructor = null;
        for(int i = 0; i < aconstructor.length; i++)
        {
            Class aclass[] = aconstructor[i].getParameterTypes();
            if(aclass.length != aobj.length)
                continue;
            boolean flag = true;
            for(int k = 0; k < aclass.length; k++)
            {
                if(aobj[k] == null || aclass[k].isInstance(aobj[k]))
                    continue;
                flag = false;
                break;
            }

            if(!flag)
                continue;
            constructor = aconstructor[i];
            break;
        }

        if(constructor == null)
        {
            Kogger.debug(Instancer.class, "Unable to find the constructor for Class specification name [" + s + "]");
            Kogger.debug(Instancer.class, "with object parameter list having classes of:");
            for(int j = 0; j < aobj.length; j++)
                if(aobj[j] != null)
                    Kogger.debug(Instancer.class, " Argument #" + (j + 1) + " of class [" + aobj[j].getClass().getName() + "]");
                else
                    Kogger.debug(Instancer.class, " Argument #" + (j + 1) + " of class [null]");

            return null;
        }
        try
        {
            obj = constructor.newInstance(aobj);
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            throw illegalaccessexception;
        }
        catch(InstantiationException instantiationexception)
        {
            throw instantiationexception;
        }
        catch(InvocationTargetException invocationtargetexception)
        {
            Kogger.error(Instancer.class, "Error in instantiating class Name [" + s + "]");
            Kogger.error(Instancer.class, "Original Execption:");
            Kogger.error(Instancer.class, invocationtargetexception.getTargetException().toString());
            Kogger.error(Instancer.class, invocationtargetexception.getTargetException().getMessage());
            Kogger.error(Instancer.class, invocationtargetexception);
            throw invocationtargetexception;
        }
        return obj;
    }
}
