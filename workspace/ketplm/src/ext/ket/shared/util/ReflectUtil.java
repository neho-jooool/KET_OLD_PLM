package ext.ket.shared.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Timestamp;

import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * @(#) ReflectUtil.java Copyright (c) Digitek. All rights reserverd
 * @version 1.00
 * @since jdk1.6.0_18
 * @createdate 2010. 11. 30.
 * @author Seong Jin, Han. narrsass@naver.com
 * @desc
 */
public class ReflectUtil {

    public static void callSetMethod(Object obj, String methodName, Object[] args) {
	callSetMethod(obj, methodName, args, new Class[] { String.class });
    }

    public static void callSetMethod(Object obj, String methodName, Object[] args, Class<?>[] classes) {
	try {
	    methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1, methodName.length());
	    Method method = obj.getClass().getMethod("set" + methodName, classes);
	    method.invoke(obj, args);
	} catch (SecurityException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callSetMethod - " + methodName + " [" + e.toString() + "]");
	} catch (NoSuchMethodException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callSetMethod - " + methodName + " [" + e.toString() + "]");
	} catch (IllegalArgumentException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callSetMethod - " + methodName + " [" + e.toString() + "]");
	} catch (IllegalAccessException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callSetMethod - " + methodName + " [" + e.toString() + "]");
	} catch (InvocationTargetException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callSetMethod - " + methodName + " [" + e.toString() + "]");
	}
    }

    public static String getSetterMethodName(String fieldname) {

	return "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1, fieldname.length());

    }

    public static Method getMethod(Object obj, String fieldname) throws ClassNotFoundException {

	Class<?> c = getAnnotationClass(obj);
	Method[] allMethods = c.getDeclaredMethods();
	Method method = null;
	for (Method m : allMethods) {
	    if (m.getName().equals(getSetterMethodName(fieldname))) {
		method = m;
	    }
	}

	return method;
    }

    /**
     * @param obj
     * @param methodName
     * @return
     */
    public static String callGetMethod(Object obj, String fieldname) {
	String value = "";
	if (obj == null)
	    return value;

	Object valueObj = callGetMethod(obj, fieldname, true);
	if (valueObj == null)
	    value = "";

	else if (valueObj instanceof Timestamp) {
	    value = DateUtil.getDateString((Timestamp) valueObj, "d");
	} else
	    value = StringUtil.checkNull(String.valueOf(valueObj));

	return value.trim();
    }

    public static Object callGetMethod(Object obj, String fieldname, boolean flag) {
	Object result = null;
	try {
	    Class<?> c = getAnnotationClass(obj);
	    Method[] allMethods = c.getDeclaredMethods();
	    Method method = null;
	    for (Method m : allMethods) {
		if (m.getName().equals(getSetterMethodName(fieldname))) {
		    Type[] gpType = m.getGenericParameterTypes();
		    if (gpType[0].equals(Boolean.TYPE)) {
			fieldname = fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1, fieldname.length());
			method = obj.getClass().getMethod("is" + fieldname, null);
			result = method.invoke(obj, null);
		    } else {
			fieldname = fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1, fieldname.length());
			method = obj.getClass().getMethod("get" + fieldname, null);
			result = method.invoke(obj, null);
		    }
		}
	    }
	} catch (SecurityException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callGetMethod - " + fieldname + " [" + e.toString() + "]");
	} catch (NoSuchMethodException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callSetMethod - " + fieldname + " [" + e.toString() + "]");
	} catch (IllegalArgumentException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callGetMethod - " + fieldname + " [" + e.toString() + "]");
	} catch (IllegalAccessException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callGetMethod - " + fieldname + " [" + e.toString() + "]");
	} catch (InvocationTargetException e) {
	    Kogger.debug(ReflectUtil.class, "ReflectUtil:callGetMethod - " + fieldname + " [" + e.toString() + "]");
	}
	return result;
    }

    public static Object getFieldClass(Object targetObj, String fieldname) throws ClassNotFoundException {

	Object rtn = null;
	Class<?> c = getAnnotationClass(targetObj);
	Field[] allFields = c.getDeclaredFields();
	for (Field f : allFields) {

	    String fname = f.getName();
	    if (fname.equals(fieldname)) {
		rtn = f.getType();
		break;
	    }
	}
	return rtn;
    }

    /**
     * @param targetObj
     * @return
     */
    private static Class<?> getAnnotationClass(Object targetObj) {

	String className = targetObj.getClass().getPackage().getName() + "._" + targetObj.getClass().getSimpleName();
	Class<?> c = null;
	try {
	    c = Class.forName(className);
	} catch (ClassNotFoundException e) {
	    try {
		c = Class.forName(targetObj.getClass().getName());
	    } catch (ClassNotFoundException e1) {
		Kogger.debug(ReflectUtil.class, "Class Not Found :: " + targetObj.getClass().getName());
	    }
	}
	return c;
    }

    public static void toString(Object targetObj) throws ClassNotFoundException {

	Class<?> c = getAnnotationClass(targetObj);
	Field[] allFields = c.getDeclaredFields();

	for (Field f : allFields) {
	    String fname = f.getName();
	    Kogger.debug(ReflectUtil.class, fname + " : " + callGetMethod(targetObj, fname));
	}
    }

    @SuppressWarnings("unchecked")
    public static Object copyProperty(Object targetObj, Object sourceObj) throws Exception {

	Class targetClass = getAnnotationClass(targetObj);
	Class sourceClass = getAnnotationClass(sourceObj);
	Field[] targetAllFields = targetClass.getDeclaredFields();
	// Field[] sourceAllFields = sourceClass.getDeclaredFields();
	Method[] targetAllMethods = targetClass.getDeclaredMethods();
	// Method[] sourceAllMethods = sourceClass.getDeclaredMethods();

	for (Field f : targetAllFields) {

	    String fname = f.getName();

	    for (Method targetMethod : targetAllMethods) {

		String methodname = targetMethod.getName();

		if (methodname.equals(getSetterMethodName(fname))) {

		    Type[] gpType = targetMethod.getGenericParameterTypes();
		    Object value = sourceClass.getMethod(fname, null);

		    if (value == null)
			continue;
		    // if( value == null || !StringUtil.checkString( value.toString() )) continue;

		    if (gpType[0].equals(Timestamp.class)) {
			Timestamp stamp = DateUtil.convertDate(value.toString());
			if (fname.toUpperCase().indexOf("FROM") != -1)
			    stamp = DateUtil.convertStartDate(value.toString());
			if (fname.toUpperCase().indexOf("TO") != -1)
			    stamp = DateUtil.convertEndDate(value.toString());
			targetMethod.invoke(targetObj, new Object[] { stamp });
		    } else if (gpType[0].equals(Byte.TYPE)) {
			Byte b = Byte.valueOf(value.toString());
			targetMethod.invoke(targetObj, new Object[] { b });

		    } else if (gpType[0].equals(Short.TYPE)) {
			Short b = Short.valueOf(value.toString());
			targetMethod.invoke(targetObj, new Object[] { b });

		    } else if (gpType[0].equals(Integer.TYPE)) {
			Integer integer = StringUtil.parseInt(value.toString(), 0);
			targetMethod.invoke(targetObj, new Object[] { integer });

		    } else if (gpType[0].equals(Long.TYPE)) {
			Long bl = StringUtil.parseLong(value.toString(), 0);
			targetMethod.invoke(targetObj, new Object[] { bl });

		    } else if (gpType[0].equals(Float.TYPE)) {
			Float bl = Float.valueOf(value.toString());
			targetMethod.invoke(targetObj, new Object[] { bl });

		    } else if (gpType[0].equals(Double.TYPE)) {
			Double db = Double.valueOf(value.toString());
			targetMethod.invoke(targetObj, new Object[] { db });

		    } else if (gpType[0].equals(Boolean.TYPE)) {
			Boolean bl = StringUtil.parseBoolean(value.toString());
			targetMethod.invoke(targetObj, new Object[] { bl });

		    } else if (gpType[0].equals(String.class)) {
			String str = value.toString();
			targetMethod.invoke(targetObj, new Object[] { str });
		    }
		}
	    }
	}

	return targetObj;
    }
}
