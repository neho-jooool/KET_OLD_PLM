// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Debug.java

package e3ps.bom.framework.util.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import ext.ket.shared.log.Kogger;

public final class Debug
{

    private Debug()
    {
    }

    public static final void addToDeletedTags(int i, String s)
    {
        if(!debug || i < 1)
            return;
        if(s == null)
            s = "[--not specified--]";
        Integer integer = new Integer(i);
        String s1 = (String)deletedObjs.get(integer);
        if(s1 == null)
            s1 = s;
        else
            s1 = s1 + ":" + s;
        deletedObjs.put(integer, s1);
        StringTokenizer stringtokenizer = new StringTokenizer(s1, ":");
        if(stringtokenizer.countTokens() > 1)
            printTheMessage("NOTE: Tag [" + i + "] has been deleted [" + stringtokenizer.countTokens() + "] times, type names are [" + s1 + "]");
    }

    public static final void addToLoadedTags(int i)
    {
        if(!debug || i < 1)
            return;
        int j = 1;
        boolean flag = false;
        for(int k = 0; k < loadedTags.length; k++)
        {
            if(loadedTags[k] == i)
                j++;
            if(loadedTags[k] != 0)
                continue;
            loadedTags[k] = i;
            flag = true;
            break;
        }

        if(j > 1)
            printTheMessage("Caution: Tag " + i + " has been loaded " + j + " times");
        if(!flag)
        {
            int ai[] = new int[loadedTags.length];
            for(int l = 0; l < loadedTags.length; l++)
                ai[l] = loadedTags[l];

            loadedTags = new int[ai.length > 2000 ? ai.length + 2000 : ai.length * 2];
            for(int i1 = 0; i1 < ai.length; i1++)
                loadedTags[i1] = ai[i1];

            loadedTags[ai.length + 1] = i;
        }
    }

    public static void classGeneralInfo(Class class1)
    {
        printSuperClasses(class1, "");
        printTheMessage(" -> Modifiers [" + Modifier.toString(class1.getModifiers()) + "]");
        printTheMessage(" -> Constructors:");
        Constructor aconstructor[] = class1.getConstructors();
        for(int i = 0; i < aconstructor.length; i++)
            printTheMessage("      " + aconstructor[i].toString());

        Class aclass[] = class1.getClasses();
        if(aclass.length > 0)
        {
            printTheMessage(" -> Classes():");
            for(int j = 0; j < aclass.length; j++)
                printTheMessage("      " + aclass[j].toString());

        }
        aclass = class1.getDeclaredClasses();
        if(aclass.length > 0)
        {
            printTheMessage(" -> Declared Classes:");
            for(int k = 0; k < aclass.length; k++)
                printTheMessage("      " + aclass[k].toString());

        }
        aclass = class1.getInterfaces();
        if(aclass.length > 0)
        {
            printTheMessage(" -> Implemented Interfaces:");
            for(int l = 0; l < aclass.length; l++)
                printTheMessage("      " + aclass[l].toString());

        }
        Field afield[] = class1.getFields();
        printTheMessage("Fields count is " + afield.length);
        for(int i1 = 0; i1 < afield.length; i1++)
            printTheMessage(" " + i1 + ".  " + afield[i1].toString() + ",");

        Method amethod[] = class1.getMethods();
        printTheMessage("Methods count is " + amethod.length);
        for(int j1 = 0; j1 < amethod.length; j1++)
            printTheMessage(" " + j1 + ".  " + amethod[j1].getReturnType().getName() + "  " + amethod[j1].getName() + "(...);");

    }

    public static void classInfo(Object obj)
    {
        classInfo(obj, "");
    }

    public static void classInfo(Object obj, String s)
    {
        if(obj == null)
        {
            return;
        } else
        {
            String s1 = obj.getClass().getName();
            classInfoForName(s1, s);
            return;
        }
    }

    public static void classInfoForName(String s)
    {
        classInfoForName(s, "");
    }

    public static void classInfoForName(String s, String s1)
    {
        if(!debug || s == null || s.trim().equals(""))
            return;
        if(s1 == null || s1.trim().equals(""))
            s1 = new String("general,methods");
        printTheMessage("===>BEGIN Class Info for [" + s + "]");
        Class class1 = null;
        try
        {
            class1 = Class.forName(s.trim());
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            Kogger.error(Debug.class, "??? Invalid calss name [" + s + "]");
            Kogger.error(Debug.class, classnotfoundexception);
        }
        if(class1 == null)
            return;
        for(StringTokenizer stringtokenizer = new StringTokenizer(s1, cd); stringtokenizer.hasMoreTokens();)
        {
            String s2 = stringtokenizer.nextToken().trim();
            if(s2.equalsIgnoreCase("general"))
                classGeneralInfo(class1);
            else
            if(s2.equalsIgnoreCase("methods"))
                classMethodsInfo(class1);
        }

        printTheMessage("===>END Class Info for [" + s + "]");
    }

    public static void classMethodsInfo(Class class1)
    {
        if(class1 == null)
            return;
        Method amethod[] = class1.getMethods();
        printTheMessage("Methods count is " + amethod.length);
        for(int i = 0; i < amethod.length; i++)
        {
            Class aclass[] = amethod[i].getParameterTypes();
            if(aclass.length > 0)
            {
                printTheMessage(" " + i + ".  " + amethod[i].getReturnType().getName() + " " + amethod[i].getName() + " (");
                for(int j = 0; j < aclass.length - 1; j++)
                    printTheMessage("      " + aclass[j].getName() + ",");

                printTheMessage("      " + aclass[aclass.length - 1].getName() + " );");
            } else
            {
                printTheMessage(" " + i + ".  " + amethod[i].getReturnType().getName() + " " + amethod[i].getName() + " ();");
            }
        }

    }

    public static void consoleOff()
    {
        consoleOn = false;
    }

    public static void consoleOn()
    {
        consoleOn = true;
    }

    public static void fileInfoOff()
    {
        fileInfo = false;
    }

    public static void fileInfoOn()
    {
        fileInfo = true;
    }

    protected void finalize()
        throws Throwable
    {
        super.finalize();
        if(syslogFile != null)
        {
            if(consoleOn)
            {
                Kogger.debug(getClass(), "\n\nPlease see the following log file for details:\n      " + syslogFileName);
                syslogFile.println("\n\nEND of " + syslogFileName);
            }
            syslogFile.flush();
            syslogFile.close();
            if(viewLogFile)
                Runtime.getRuntime().exec(editCommand);
        }
    }

    private static final boolean isDebugOption(String s, boolean flag)
    {
        if(flag)
        {
            if(s.equalsIgnoreCase("fileInfo"))
            {
        	Kogger.debug(Debug.class, "fileinfo is on");
                fileInfoOn();
            } else
            if(s.equalsIgnoreCase("packageInfo"))
                packageInfoOn();
            else
            if(s.equalsIgnoreCase("methodOnly"))
                methodOnlyOn();
            else
            if(s.equalsIgnoreCase("syslog"))
                syslogOn();
            else
            if(s.equalsIgnoreCase("console"))
                consoleOn();
            else
            if(s.equalsIgnoreCase("viewLogFile"))
                viewLogFileOn();
            else
            if(s.equalsIgnoreCase(printThread))
                threadInfoOn();
            else
                return false;
        } else
        if(s.equalsIgnoreCase("fileInfo"))
            fileInfoOff();
        else
        if(s.equalsIgnoreCase("packageInfo"))
            packageInfoOff();
        else
        if(s.equalsIgnoreCase("methodOnly"))
            methodOnlyOff();
        else
        if(s.equalsIgnoreCase("syslog"))
            syslogOff();
        else
        if(s.equalsIgnoreCase("console"))
            consoleOff();
        else
        if(s.equalsIgnoreCase("viewLogFile"))
            viewLogFileOff();
        else
        if(s.equalsIgnoreCase(printThread))
            threadInfoOff();
        else
            return false;
        return true;
    }

    public static boolean isOff()
    {
        return debug ^ true;
    }

    public static boolean isOff(String s)
    {
        return isOn(s) ^ true;
    }

    public static boolean isOn()
    {
        return debug;
    }

    public static boolean isOn(String s)
    {
        if(!debug)
            return false;
        if(unconditional)
            return true;
        if(s != null && !s.equals(""))
        {
            StringTokenizer stringtokenizer = new StringTokenizer(s.toUpperCase(), md);
            listModules("isOn(String debuggedModule)");
            while(stringtokenizer.hasMoreTokens()) 
            {
                String s1 = stringtokenizer.nextToken().trim();
                if(s1.indexOf("*") > -1)
                {
                    for(Enumeration enumeration = htable.keys(); enumeration.hasMoreElements();)
                    {
                        String s2 = (String)enumeration.nextElement();
                        if(matchedPattern(s1, s2))
                        {
                            Boolean boolean1 = (Boolean)htable.get(s2);
                            if(boolean1 != null && boolean1.booleanValue())
                                return true;
                        }
                    }

                } else
                {
                    Boolean boolean2 = (Boolean)htable.get(s1);
                    if(boolean2 != null && boolean2.booleanValue())
                        return true;
                }
            }
            return false;
        } else
        {
            return debug;
        }
    }

    public static void listModules()
    {
        if(!debugDebug)
            return;
        Kogger.debug(Debug.class, "Number of debugged modules is " + htable.size());
        for(Enumeration enumeration = htable.keys(); enumeration.hasMoreElements(); Kogger.debug(Debug.class, "---------------"))
        {
            String s = (String)enumeration.nextElement();
            Boolean boolean1 = (Boolean)htable.get(s);
            if(boolean1.booleanValue())
                Kogger.debug(Debug.class, "  [" + s + "] debug is ON");
            else
                Kogger.debug(Debug.class, "  [" + s + "] debug is OFF");
        }

        System.out.flush();
    }

    private static final void listModules(String s)
    {
        if(!debugDebug)
        {
            return;
        } else
        {
            Kogger.debug(Debug.class, "--> listModule() called from [" + s + "]");
            listModules();
            return;
        }
    }

    private static void log(String s)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, s, 0, false);
            return;
        }
    }

    private static void log(String s, String s1)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, s1, 0, false);
            return;
        }
    }

    private static void log(String s, String s1, int i, boolean flag)
    {
        if(!debug)
            return;
        if(!isOn(s))
            return;
        if(flag || !startNewLine)
            theMessage = s1;
        else
        if(packageInfo || fileInfo)
        {
            StringWriter stringwriter = new StringWriter();
            PrintWriter printwriter = new PrintWriter(stringwriter);
            (new Throwable()).printStackTrace(printwriter);
            printwriter.flush();
            BufferedReader bufferedreader = new BufferedReader(new StringReader(stringwriter.toString()));
            try
            {
                String s2 = bufferedreader.readLine();
                do
                {
                    s2 = bufferedreader.readLine();
                    if(s2 == null)
                    {
                        theMessage = "[] " + s1;
                        break;
                    }
                    if(s2.indexOf(debugClassName) == -1)
                        continue;
                    do
                    {
                        s2 = bufferedreader.readLine();
                        if(s2 == null)
                        {
                            theMessage = "[] " + s1;
                            break;
                        }
                        if(s2.indexOf(debugClassName) > -1)
                            continue;
                        boolean flag1 = false;
                        for(int j = 0; j < i; j++)
                        {
                            s2 = bufferedreader.readLine();
                            if(s2 == null)
                            {
                                flag1 = true;
                                theMessage = "[] " + s1;
                                break;
                            }
                            if(flag1)
                                break;
                        }

                        int k = s2.indexOf(ug);
                        if(k == -1)
                        {
                            k = s2.indexOf("at ");
                            offset = 3;
                        }
                        if(packageInfo && fileInfo)
                        {
                            s2 = s2.substring(k + offset);
                            if(methodOnly && i == 0)
                                s2 = s2.substring(s2.substring(0, s2.lastIndexOf(delimiter)).lastIndexOf(delimiter) + 1);
                        } else
                        if(packageInfo)
                        {
                            s2 = s2.substring(k + offset, s2.indexOf("("));
                            if(methodOnly && i == 0)
                                s2 = s2.substring(s2.lastIndexOf(delimiter) + 1);
                        } else
                        if(fileInfo)
                            s2 = s2.substring(s2.indexOf("(") + 1, s2.indexOf(")"));
                        int l = s2.indexOf("(Compiled Code)");
                        if(l > -1)
                            s2 = s2.substring(0, l);
                        theMessage = "[" + s2 + "] " + s1;
                        break;
                    } while(s2 != null);
                    break;
                } while(s2 != null);
            }
            catch(IOException ex)
            {
                theMessage = "[] " + s1;
            }
        } else
        {
            theMessage = s1;
        }
        if(flag)
        {
            printMessage(theMessage, false);
            startNewLine = false;
        } else
        {
            printMessage(theMessage, true);
            startNewLine = true;
        }
    }

    private static void logPrint(String s)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, s, 0, true);
            return;
        }
    }

    private static void logPrint(String s, String s1)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, s1, 0, true);
            return;
        }
    }

    private static void logUp(String s)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, s, 1, false);
            return;
        }
    }

    private static void logUp(String s, String s1)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, s1, 1, false);
            return;
        }
    }

    private static final boolean matchedPattern(String s, String s1)
    {
        if(s == null || s1 == null)
            return false;
        if(s.startsWith(wildChar))
        {
            if(s.endsWith(wildChar))
                return s1.indexOf(s.substring(1, s.length() - 2)) > -1;
            s = s.substring(1);
            int i = s1.lastIndexOf(s);
            if(i > -1)
                return s1.substring(i).equalsIgnoreCase(s);
            else
                return false;
        }
        if(s.endsWith(wildChar))
            return s1.startsWith(s.substring(0, s.length() - 2));
        else
            return s.equalsIgnoreCase(s1);
    }

    public static void methodOnlyOff()
    {
        methodOnly = false;
    }

    public static void methodOnlyOn()
    {
        methodOnly = true;
    }

    public static void off()
    {
        debug = false;
        Kogger.debug(Debug.class, "Debug is turned OFF");
    }

    public static void off(String s)
    {
        if(s == null || s.equals(""))
            return;
        for(StringTokenizer stringtokenizer = new StringTokenizer(s.toUpperCase(), md); stringtokenizer.hasMoreTokens();)
        {
            String s1 = stringtokenizer.nextToken().trim();
            if(!s1.equals(""))
                if(s1.equals("*"))
                {
                    unconditional = false;
                    off();
                    if(htable != null)
                        htable.clear();
                } else
                if(s1.equals("**"))
                {
                    unconditional = false;
                    packageInfoOff();
                    fileInfoOff();
                    off();
                    if(htable != null)
                        htable.clear();
                } else
                {
                    offModule(s1);
                }
        }

    }

    private static final void offModule(String s)
    {
        if(s == null || s.equals(""))
            return;
        if(isDebugOption(s, false))
            return;
        try
        {
            htable.remove(s);
        }
        catch(Exception exception)
        {
            printStackTrace(exception);
        }
        listModules("off(" + s + ")");
    }

    public static void on()
    {
        debug = true;
        if(initialized)
            return;
        initialized = true;
        loadedTags = new int[500];
        htable = new Hashtable();
        deletedObjs = new Hashtable();
        listModules("on() -- initialization");
        String s = null;
        if(System.getProperty("os.name").startsWith("Windows"))
            s = new String("C:") + File.separator + new String("temp") + File.separator;
        else
            s = File.separator + new String("tmp") + File.separator;
        try
        {
            File file1 = new File(System.getProperty("java.io.tmpdir", s));
            String s1 = null;
            if(file1.isDirectory())
                if(file1.exists())
                    s1 = System.getProperty("java.io.tmpdir", s);
                else
                if(file1.mkdir())
                {
                    s1 = System.getProperty("java.io.tmpdir", s);
                } else
                {
                    s1 = "";
                    Kogger.debug(Debug.class, "===> Unable to create a temp. directory for log files");
                    Kogger.debug(Debug.class, "     Use the working directory for log files");
                }
            syslogFileName = s1.concat(System.getProperty("user.name") + "_session" + System.currentTimeMillis() + ".log");
            file = new File(syslogFileName);
            syslogFile = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            Kogger.debug(Debug.class, "===> session log file name:  " + syslogFileName);
            syslogFile.println("===> session log file name:  " + syslogFileName);
            editCommand[0] = new String("i:" + File.separator + "vim" + File.separator + "gvim.exe");
            editCommand[1] = new String(syslogFileName);
            syslogFile.println((new Date()).toString());
            System.getProperties().list(syslogFile);
        }
        catch(IOException ioexception)
        {
            Kogger.error(Debug.class, " ??? Unable to create syslog file [" + syslogFileName + "]");
            Kogger.error(Debug.class, "     " + ioexception.toString());
            syslogOn = false;
            syslogFile = null;
        }
        System.getProperties().put("___debug.use", new Debug());
    }

    public static void on(String s)
    {
        if(s == null || s.equals(""))
            return;
        for(StringTokenizer stringtokenizer = new StringTokenizer(s.toUpperCase(), md); stringtokenizer.hasMoreTokens();)
        {
            String s1 = stringtokenizer.nextToken().trim();
            if(!s1.equals(""))
                if(s1.equals("*"))
                {
                    unconditional = true;
                    on();
                } else
                if(s1.equals("**"))
                {
                    packageInfoOn();
                    fileInfoOn();
                    threadInfoOn();
                    unconditional = true;
                    on();
                } else
                {
                    onModule(s1);
                }
        }

    }

    private static final void onModule(String s)
    {
        if(s == null || s.equals(""))
            return;
        on();
        if(isDebugOption(s, true))
            return;
        Object obj = htable.get(s);
        if(obj == null || !((Boolean)obj).booleanValue())
            try
            {
                htable.put(s, new Boolean(true));
            }
            catch(Exception exception)
            {
                printStackTrace(exception);
            }
        listModules("on(" + s + ")");
    }

    public static void packageInfoOff()
    {
        packageInfo = false;
    }

    public static void packageInfoOn()
    {
        packageInfo = true;
    }

    public static void print(char c)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(String.valueOf(c));
            return;
        }
    }

    public static void print(double d)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(String.valueOf(d));
            return;
        }
    }

    public static void print(float f)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(String.valueOf(f));
            return;
        }
    }

    public static void print(int i)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(String.valueOf(i));
            return;
        }
    }

    public static void print(long l)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(String.valueOf(l));
            return;
        }
    }

    public static void print(Object obj)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(obj.toString());
            return;
        }
    }

    public static void print(String s)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s);
            return;
        }
    }

    public static void print(String s, char c)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s, String.valueOf(c));
            return;
        }
    }

    public static void print(String s, double d)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s, String.valueOf(d));
            return;
        }
    }

    public static void print(String s, float f)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s, String.valueOf(f));
            return;
        }
    }

    public static void print(String s, int i)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s, String.valueOf(i));
            return;
        }
    }

    public static void print(String s, long l)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s, String.valueOf(l));
            return;
        }
    }

    public static void print(String s, Object obj)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s, obj.toString());
            return;
        }
    }

    public static void print(String s, String s1)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s, s1);
            return;
        }
    }

    public static void print(String s, boolean flag)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s, String.valueOf(flag));
            return;
        }
    }

    public static void print(String s, char ac[])
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(s, String.valueOf(ac));
            return;
        }
    }

    public static void print(boolean flag)
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(String.valueOf(flag));
            return;
        }
    }

    public static void print(char ac[])
    {
        if(!debug)
        {
            return;
        } else
        {
            logPrint(String.valueOf(ac));
            return;
        }
    }

    public static void printCurrentThread(String s)
    {
        logUp(s + "\n    Current thread is [" + Thread.currentThread().toString() + "]");
    }

    private static final void printMessage(String s, boolean flag)
    {
        if(syslogOn && syslogFile != null)
        {
            if(flag)
            {
                if(threadInfo)
                    syslogFile.print("{" + Thread.currentThread().toString() + "}##");
                syslogFile.println(s);
            } else
            {
                syslogFile.print(s);
            }
            syslogFile.flush();
        }
        if(consoleOn)
        {
            if((packageInfo || fileInfo) && syslogOn && syslogFile != null)
            {
                int i = s.indexOf("]");
                if(i > -1 && s.length() > i + 2)
                    s = s.substring(i + 2);
            }
            if(flag)
            {
                if(threadInfo)
                    System.out.print("{" + Thread.currentThread().toString() + "}##");
                Kogger.debug(Debug.class, s);
            } else
            {
                System.out.print(s);
            }
            System.out.flush();
        }
    }

    public static void printStackTrace()
    {
        String s = "DEBUG(not an exception): PRINT STACK TRACE:";
        if(syslogOn && syslogFile != null)
        {
            if(threadInfo)
                syslogFile.print("{" + Thread.currentThread().toString() + "}##");
            syslogFile.println(s);
            syslogFile.println("---START of stack trace---(DEBUG)");
            (new Throwable()).printStackTrace(syslogFile);
            syslogFile.println("---END of stack trace---(DEBUG)");
            syslogFile.flush();
        }
        if(consoleOn)
        {
            if(threadInfo)
                System.out.print("{" + Thread.currentThread().toString() + "}##");
            Kogger.debug(Debug.class, s);
            Kogger.debug(Debug.class, "---START of stack trace---(DEBUG)");
            Kogger.error(Debug.class, new Throwable());
            Kogger.debug(Debug.class, "---END of stack trace---(DEBUG)");
            System.out.flush();
        }
    }

    public static void printStackTrace(Exception exception)
    {
        if(exception == null || !debug)
            return;
        if(syslogOn && syslogFile != null)
        {
            if(threadInfo)
                syslogFile.print("{" + Thread.currentThread().toString() + "}##");
            Kogger.debug(Debug.class, "??? EXCEPTION THROWN----  See " + syslogFileName + "  for details");
            syslogFile.println("??? EXCEPTION THROWN:");
            syslogFile.println("---START of stack trace---");
            exception.printStackTrace(syslogFile);
            syslogFile.println("---END of stack trace---");
            syslogFile.flush();
        }
        if(consoleOn)
        {
            if(threadInfo)
                System.out.print("{" + Thread.currentThread().toString() + "}##");
            Kogger.debug(Debug.class, "??? EXCEPTION THROWN:");
            Kogger.debug(Debug.class, "---START of stack trace---");
            Kogger.error(Debug.class, exception);
            Kogger.debug(Debug.class, "---END of stack trace---");
            System.out.flush();
        }
    }

    public static void printStackTrace(String s)
    {
        if(s == null || s.equals(""))
            s = "DEBUG(not an exception): PRINT STACK TRACE:";
        else
            s = "DEBUG(not an exception): " + s;
        if(syslogOn && syslogFile != null)
        {
            if(threadInfo)
                syslogFile.print("{" + Thread.currentThread().toString() + "}##");
            syslogFile.println(s);
            syslogFile.println("---START of stack trace---(DEBUG)");
            (new Throwable()).printStackTrace(syslogFile);
            syslogFile.println("---END of stack trace---(DEBUG)");
            syslogFile.flush();
        }
        if(consoleOn)
        {
            if(threadInfo)
                System.out.print("{" + Thread.currentThread().toString() + "}##");
            Kogger.debug(Debug.class, s);
            Kogger.debug(Debug.class, "---START of stack trace---(DEBUG)");
            Kogger.error(Debug.class, new Throwable());
            Kogger.debug(Debug.class, "---END of stack trace---(DEBUG)");
            System.out.flush();
        }
    }

    public static void printStackTrace(String s, Exception exception)
    {
        if(exception == null || !debug)
            return;
        if(isOn(s))
            printStackTrace(exception);
    }

    public static void printStackTrace(String s, String s1)
    {
        if(!debug)
            return;
        if(isOn(s))
            printStackTrace(s1);
    }

    public static void printSuperClasses(Class class1, String s)
    {
        Class class2 = class1.getSuperclass();
        if(class2 == null)
        {
            printTheMessage(s + " -> No more super class");
        } else
        {
            printSuperClasses(class2, "  ");
            printTheMessage(s + " -> Superclass [" + class2 + "]");
            printTheMessage(s + " -> Modifiers [" + Modifier.toString(class2.getModifiers()) + "]");
            printTheMessage(s + " -> Constructors:");
            Constructor aconstructor[] = class2.getConstructors();
            for(int i = 0; i < aconstructor.length; i++)
                printTheMessage(s + "      " + aconstructor[i].toString());

        }
    }

    private static final void printTheMessage(String s)
    {
        printMessage(s, true);
    }

    public static void println(char c)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(String.valueOf(c));
            return;
        }
    }

    public static void println(double d)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(String.valueOf(d));
            return;
        }
    }

    public static void println(float f)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(String.valueOf(f));
            return;
        }
    }

    public static void println(int i)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(String.valueOf(i));
            return;
        }
    }

    public static void println(int i, char c)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, String.valueOf(c), i, false);
            return;
        }
    }

    public static void println(int i, double d)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, String.valueOf(d), i, false);
            return;
        }
    }

    public static void println(int i, float f)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, String.valueOf(f), i, false);
            return;
        }
    }

    public static void println(int i, int j)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, String.valueOf(j), i, false);
            return;
        }
    }

    public static void println(int i, long l)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, String.valueOf(l), i, false);
            return;
        }
    }

    public static void println(int i, Object obj)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, obj.toString(), i, false);
            return;
        }
    }

    public static void println(int i, String s)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, s, i, false);
            return;
        }
    }

    public static void println(int i, String s, char c)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(c), i, false);
            return;
        }
    }

    public static void println(int i, String s, double d)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(d), i, false);
            return;
        }
    }

    public static void println(int i, String s, float f)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(f), i, false);
            return;
        }
    }

    public static void println(int i, String s, int j)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(j), i, false);
            return;
        }
    }

    public static void println(int i, String s, long l)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(l), i, false);
            return;
        }
    }

    public static void println(int i, String s, Object obj)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, obj.toString(), i, false);
            return;
        }
    }

    public static void println(int i, String s, String s1)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, s1, i, false);
            return;
        }
    }

    public static void println(int i, String s, boolean flag)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(flag), i, false);
            return;
        }
    }

    public static void println(int i, String s, char ac[])
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(ac), i, false);
            return;
        }
    }

    public static void println(int i, boolean flag)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, String.valueOf(flag), i, false);
            return;
        }
    }

    public static void println(int i, char ac[])
    {
        if(!debug)
        {
            return;
        } else
        {
            log(null, String.valueOf(ac));
            return;
        }
    }

    public static void println(long l)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(String.valueOf(l));
            return;
        }
    }

    public static void println(Object obj)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(obj.toString());
            return;
        }
    }

    public static void println(String s)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s);
            return;
        }
    }

    public static void println(String s, char c)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(c));
            return;
        }
    }

    public static void println(String s, double d)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(d));
            return;
        }
    }

    public static void println(String s, float f)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(f));
            return;
        }
    }

    public static void println(String s, int i)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(i));
            return;
        }
    }

    public static void println(String s, long l)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(l));
            return;
        }
    }

    public static void println(String s, Object obj)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, obj.toString());
            return;
        }
    }

    public static void println(String s, String s1)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, s1);
            return;
        }
    }

    public static void println(String s, boolean flag)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(flag));
            return;
        }
    }

    public static void println(String s, char ac[])
    {
        if(!debug)
        {
            return;
        } else
        {
            log(s, String.valueOf(ac));
            return;
        }
    }

    public static void println(boolean flag)
    {
        if(!debug)
        {
            return;
        } else
        {
            log(String.valueOf(flag));
            return;
        }
    }

    public static void println(char ac[])
    {
        if(!debug)
        {
            return;
        } else
        {
            log(String.valueOf(ac));
            return;
        }
    }

    public static void printlnUp(char c)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(String.valueOf(c));
            return;
        }
    }

    public static void printlnUp(double d)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(String.valueOf(d));
            return;
        }
    }

    public static void printlnUp(float f)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(String.valueOf(f));
            return;
        }
    }

    public static void printlnUp(int i)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(String.valueOf(i));
            return;
        }
    }

    public static void printlnUp(long l)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(String.valueOf(l));
            return;
        }
    }

    public static void printlnUp(Object obj)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(obj.toString());
            return;
        }
    }

    public static void printlnUp(String s)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s);
            return;
        }
    }

    public static void printlnUp(String s, char c)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s, String.valueOf(c));
            return;
        }
    }

    public static void printlnUp(String s, double d)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s, String.valueOf(d));
            return;
        }
    }

    public static void printlnUp(String s, float f)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s, String.valueOf(f));
            return;
        }
    }

    public static void printlnUp(String s, int i)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s, String.valueOf(i));
            return;
        }
    }

    public static void printlnUp(String s, long l)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s, String.valueOf(l));
            return;
        }
    }

    public static void printlnUp(String s, Object obj)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s, obj.toString());
            return;
        }
    }

    public static void printlnUp(String s, String s1)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s, s1);
            return;
        }
    }

    public static void printlnUp(String s, boolean flag)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s, String.valueOf(flag));
            return;
        }
    }

    public static void printlnUp(String s, char ac[])
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(s, String.valueOf(ac));
            return;
        }
    }

    public static void printlnUp(boolean flag)
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(String.valueOf(flag));
            return;
        }
    }

    public static void printlnUp(char ac[])
    {
        if(!debug)
        {
            return;
        } else
        {
            logUp(String.valueOf(ac));
            return;
        }
    }

    public static void setEditor(String s)
    {
        editCommand[0] = new String(s);
    }

    public static void syslogOff()
    {
        syslogOn = false;
    }

    public static void syslogOn()
    {
        syslogOn = true;
    }

    public static void threadInfoOff()
    {
        threadInfo = false;
    }

    public static void threadInfoOn()
    {
        threadInfo = true;
    }

    public static void viewLogFileOff()
    {
        viewLogFile = false;
    }

    public static void viewLogFileOn()
    {
        viewLogFile = true;
    }

    private static boolean syslogOn = true;
    private static boolean consoleOn = true;
    private static boolean threadInfo = false;
    private static boolean debug = false;
    private static boolean initialized = false;
    private static PrintWriter syslogFile = null;
    private static boolean packageInfo = false;
    private static boolean methodOnly = true;
    private static boolean fileInfo = false;
    private static String theMessage = null;
    private static File file = null;
    private static String syslogFileName = null;
    private static boolean viewLogFile = false;
    private static boolean debugDebug = false;
    private static boolean unconditional = false;
    private static String editCommand[] = new String[2];
    private static int loadedTags[] = null;
    private static Hashtable htable = null;
    private static boolean startNewLine = true;
    private static Hashtable deletedObjs = null;
    private static final String delimiter = new String(".");
    private static final String debugClassName = new String("ext.lsis.bomugsolutions.util.log.Debug");
    private static final String ug = new String("ext.lsis.bom.ugsolutions.");
    private static final String md = new String(",");
    static final String cd = new String(",");
    static final String wildChar = new String("*");
    private static final String printThread = new String("ThreadInfo");
    private static int offset = ug.length();

}
