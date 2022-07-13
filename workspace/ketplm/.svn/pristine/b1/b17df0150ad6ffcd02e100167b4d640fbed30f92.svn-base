echo off

echo -------------------------------------------------------------------
echo 메일 서버 셋팅 (wt.properties)
echo -------------------------------------------------------------------
call xconfmanager -s "wt.mail.mailhost=localhost" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo 서버 시간대 설정 (wt.properties)
echo -------------------------------------------------------------------
call xconfmanager -s "wt.method.timezone=JST" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo 부재중위임 달력기능 활성화 (wt.properties)
echo -------------------------------------------------------------------
call xconfmanager -s "wt.calendar.calculateDefaults=false" -t  %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo SunOne 웹서버 설정 (wt.properties)
echo -------------------------------------------------------------------
call xconfmanager -s "wt.content.SunOne=true" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo 버전 지정체계 변경 (wt.properties)
echo -------------------------------------------------------------------
call xconfmanager -s "wt.series.HarvardSeries.IntegerSeries.delimiter=." -t %WT_HOME%\codebase\wt.properties -p
call xconfmanager -s "wt.series.HarvardSeries.IntegerSeries.depth=16" -t %WT_HOME%\codebase\wt.properties -p
call xconfmanager -s "wt.series.HarvardSeries.IntegerSeries.level.1=wt.series.IntegerSeries" -t %WT_HOME%\codebase\wt.properties -p
call xconfmanager -s "wt.series.HarvardSeries.seriesNames=MilSpec,IntegerSeries" -t %WT_HOME%\codebase\wt.properties -p
call xconfmanager -s "wt.series.IntegerSeries.min=0" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo File Vault 설정 (wt.properties)
echo -------------------------------------------------------------------
call xconfmanager -s "wt.fv.forceContentToVault=true" -t %WT_HOME%\codebase\wt.properties -p
call xconfmanager -s "wt.fv.uploadtocache.revaultOnCommit=true" -t %WT_HOME%\codebase\wt.properties -p
call xconfmanager -s "wt.fv.purgeUnreferencedFvItemsOlderThan=10" -t %WT_HOME%\codebase\wt.properties -p
call xconfmanager -s "wt.fv.useFvFileThreshold=true" -t %WT_HOME%\codebase\wt.properties -p
call xconfmanager -s "wt.fv.fvFileThreshold=5000" -t %WT_HOME%\codebase\wt.properties -p
