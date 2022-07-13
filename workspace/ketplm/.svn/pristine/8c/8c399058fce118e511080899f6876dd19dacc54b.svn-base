/*
 * @(#) ReflectUtil.java  Create on 2004. 11. 23.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.obj;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ext.ket.shared.log.Kogger;

/**
 * 
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2004. 11. 23.
 * @since 1.4
 */
public class ReflectUtil
{
    public static void callSetMethod(Object obj, String methodName, Object[] args)
    {
        callSetMethod(obj, methodName, args, new Class[]{String.class});
    }
    
    public static void callSetMethod(Object obj, String methodName, Object[] args, Class[] classes)
    {
        try
        {
            Method method = obj.getClass().getMethod("set"+methodName, classes);
            method.invoke(obj, args);
        }
        catch (SecurityException e)
        {
            Kogger.error(ReflectUtil.class, e);
        }
        catch (NoSuchMethodException e)
        {
            Kogger.error(ReflectUtil.class, e);
        }
        catch (IllegalArgumentException e)
        {
            Kogger.error(ReflectUtil.class, e);
        }
        catch (IllegalAccessException e)
        {
            Kogger.error(ReflectUtil.class, e);
        }
        catch (InvocationTargetException e)
        {
            Kogger.error(ReflectUtil.class, e);
        }
    }
    
    /**
     * String 및 기본형을 반환시 사용한다.
     * @param obj
     * @param methodName
     * @return
     */
    public static String callGetMethod(Object obj, String methodName)
    {
        return (String)callGetMethod(obj,methodName,true);
    }
    
    public static Object callGetMethod(Object obj, String methodName, boolean flag)
    {
        Object result=null;
        try
        {
            Method method = obj.getClass().getMethod("get"+methodName, null);
            result = method.invoke(obj, null);
        }
        catch (Exception e)
        {
            Kogger.error(ReflectUtil.class, "Exception = " + e.getLocalizedMessage());
        }
        return result;
    }
    
}
