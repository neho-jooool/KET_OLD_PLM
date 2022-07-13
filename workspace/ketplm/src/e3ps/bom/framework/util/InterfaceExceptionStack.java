// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InterfaceExceptionStack.java

package e3ps.bom.framework.util;


public interface InterfaceExceptionStack
{

    public abstract int[] getErrorCodes();

    public abstract int[] getErrorSeverities();

    public abstract String[] getErrorStack();

    public static final int SEVERITY_INFORMATION = 1;
    public static final int SEVERITY_WARNING = 2;
    public static final int SEVERITY_ERROR = 3;
    public static final int SEVERITY_USER_ERROR = 4;
}
