cd /D D:\ptc\Windchill\codebase\e3ps\load\bom

set CLASSPATH=.;%CLASSPATH%;D:\ptc\Windchill\lib\sapjco.jar;
set PATH=D:\ptc\Java\bin;%PATH%

java e3ps.load.bom.BomMigrationCmd wcadmin wcadmin

pause
