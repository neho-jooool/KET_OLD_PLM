set ANT_ARGS=-noclasspath -logger com.ptc.tools.build.PTCLogger -lib D:\ptc\Windchill\srclib\tool\PtcBuildSupport-ant.jar

set CLASSPATH=d:/ptc/Windchill/codebase;d:/ptc/Windchill/codebase/WEB-INF/lib/activation.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/archiveClientWeb.jar;	d:/ptc/Windchill/codebase/WEB-INF/lib/archiveServerWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/archiveWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/commons-collections-3.1.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/commons-dbcp-1.2.1.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/commons-fileupload-1.2.1.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/commons-httpclient.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/commons-io-1.3.2.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/commons-lang-2.4.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/commons-pool-1.3.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/cos.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/CounterPartWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/dpimplWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/dpinfraWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/Gantt.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/ie3rdpartylibs.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/ie.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/ieWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/install.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/jmousewheel.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/jviews-chart-all.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/jviews-framework-all.jar;	d:/ptc/Windchill/codebase/WEB-INF/lib/jviews-gantt-all.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/jxl.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/libJNICipherUni.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/mail.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/netscape.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/pdmlWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/pjlWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/prowtWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/quartz.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/sapjco.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/servlet.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/servlet-api.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/sumaWeb.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/wc3rdpartylibs.jar;d:/ptc/Windchill/codebase/WEB-INF/lib/wncWeb.jar
set JAVA_HOME=D:\ptc\Java
set PATH=D:\ptc\Windchill\bin;D:\ptc\Windchill\ant\bin;D:\ptc\Java\jre\bin;D:\ptc\Java\bin;D:\oracle\BIN;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;D:\ptc\Java\bin
set PWD=D:/ptc/Windchill/bin
set SQLPATH=D:\ptc\Windchill\db\sql
set WT_HOME=D:\ptc\Windchill\bin\..

windchill e3ps.load.bom.ItemMigrationCmd wcadmin wcadmin
