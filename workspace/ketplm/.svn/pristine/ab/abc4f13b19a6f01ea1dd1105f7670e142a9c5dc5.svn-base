// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Timing.java

package e3ps.bom.framework.util.log;

import java.util.HashSet;
import java.util.StringTokenizer;

import ext.ket.shared.log.Kogger;

public class Timing
{

    public Timing()
    {
    }

    public static boolean isOn(String s)
    {
        return anyOn && onModules.contains(s);
    }

    public static void off(String s)
    {
        String s1;
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, ","); stringtokenizer.hasMoreTokens(); onModules.remove(s1))
            s1 = stringtokenizer.nextToken();

        anyOn = onModules.size() > 0;
    }

    public static void on(String s)
    {
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, ","); stringtokenizer.hasMoreTokens();)
        {
            String s1 = stringtokenizer.nextToken();
            onModules.add(s1);
            anyOn = true;
        }

    }

    public static void report(long l, String s, String s1)
    {
        if(l != 0L)
        {
            long l1 = System.currentTimeMillis() - l;
            Kogger.debug(Timing.class, "Timing: " + s + ": " + s1 + " took " + l1 + "ms.");
        }
    }

    public static long start(String s)
    {
        if(isOn(s))
            return System.currentTimeMillis();
        else
            return 0L;
    }

    public static void stop(long l, String s, String s1)
    {
        if(l != 0L)
            report(l, s, s1);
    }

    private static HashSet onModules = new HashSet();
    private static boolean anyOn = false;

}
