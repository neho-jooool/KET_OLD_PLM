cd /D D:\ptc\Windchill\codebase\e3ps\load\bom

set CLASSPATH=.;%CLASSPATH%
set PATH=D:\ptc\Java\bin;%PATH%

java e3ps.load.bom.ItemSyncBatchCmd wcadmin wcadmin

pause
