echo off

echo -------------------------------------------------------------------
echo 메소드 서버 Heap 사이즈 조정 (wt.properties)
echo -------------------------------------------------------------------
call xconfmanager -s "wt.method.minHeap=256" -t %WT_HOME%\codebase\wt.properties -p
call xconfmanager -s "wt.method.maxHeap=512" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo BackgroundMethodServer & 2 MethodServer 설정 (wt.properties)
echo 운영서버에만 적용함
echo -------------------------------------------------------------------
REM call xconfmanager -s "wt.manager.monitor.services=MethodServer BackgroundMethodServer" -t %WT_HOME%\codebase\wt.properties -p
REM call xconfmanager -s "wt.manager.monitor.start.MethodServer=2" -t %WT_HOME%\codebase\wt.properties -p
REM call xconfmanager -s "wt.manager.monitor.start.BackgroundMethodServer=1" -t %WT_HOME%\codebase\wt.properties -p
REM call xconfmanager -s "wt.queue.executeQueues=false" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo 서버 시작 타임아웃 설정 (wt.properties)
echo 개인 Local PC에만 적용함
echo -------------------------------------------------------------------
call xconfmanager -s "wt.manager.serverStartTimeout=300" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo 클라이언트 소켓 사이즈 조정 (wt.properties)
echo -------------------------------------------------------------------
REM call xconfmanager -s "wt.rmi.socketSendBufferSize=65535" -t %WT_HOME%\codebase\wt.properties -p
REM call xconfmanager -s "wt.rmi.socketReceiveBufferSize=65535" -t %WT_HOME%\codebase\wt.properties -p
REM call xconfmanager -s "wt.rmi.serverSocketReceiveBufferSize=65535" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo 서버 소켓 사이즈 조정 (wt.properties)
echo -------------------------------------------------------------------
REM call xconfmanager -s "wt.manager.rmi.maxSockets=300" -t %WT_HOME%\codebase\wt.properties -p
REM call xconfmanager -s "wt.method.rmi.maxSockets=300" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo 각종 캐쉬 사이즈 조정 (wt.properties)
echo -------------------------------------------------------------------
REM call xconfmanager -s "wt.cache.size.AclCache=500" -t %WT_HOME%\codebase\wt.properties -p
REM call xconfmanager -s "wt.cache.size.NotificationListCache=1000" -t %WT_HOME%\codebase\wt.properties -p
REM call xconfmanager -s "wt.cache.size.PagingSessionCache=500" -t %WT_HOME%\codebase\wt.properties -p
REM call xconfmanager -s "wt.cache.size.ReferenceCache=200" -t %WT_HOME%\codebase\wt.properties -p

echo -------------------------------------------------------------------
echo DB Parameters 조정 (db.properties)
echo -------------------------------------------------------------------
REM call xconfmanager -s "wt.pom.maxDbConnections=10" -t %WT_HOME%\db\db.properties -p
REM call xconfmanager -s "wt.pom.statementCacheSize=50" -t %WT_HOME%\db\db.properties -p
REM call xconfmanager -s "wt.pom.cachedStatementReuseLimit=50" -t %WT_HOME%\db\db.properties -p
REM call xconfmanager -s "wt.pom.freeConnectionImmediate=false" -t %WT_HOME%\db\db.properties -p
