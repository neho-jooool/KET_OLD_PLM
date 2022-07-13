package e3ps.load.ecm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.util.WTException;
import e3ps.common.util.DateUtil;
import e3ps.ecm.entity.KETMoldChangePlan;
import e3ps.ecm.service.KETEcmHelper;
import ext.ket.shared.log.Kogger;

public class UpdateMoldPlanStatusCmd {
	
	public static String strDirPath = "D:\\ptc\\Windchill\\logs\\ecm";

	public static void main(String[] args) throws IOException {
		
    	if( args.length != 2 )
        {
            // 사용법 설명
            Kogger.debug(UpdateMoldPlanStatusCmd.class, ">>> Usage  : java e3ps.load.ecm.UpdateMoldPlanStatusCmd userID passwd");
            return;
        }
    	
    	// 사용자 인증
		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);
		
		FileWriter fw= null;
    	String currentDate = DateUtil.getCurrentDateString( new SimpleDateFormat("yyyy-MM-dd"));
    	String currentTime = DateUtil.getCurrentDateString( new SimpleDateFormat("HH:mm:ss"));
    	String strFileName = "UpdateMoldPlanStatusBatchLog_" + currentDate + "_" + currentTime.replace(":", "") + ".log";
    	
    	File dir = new File(strDirPath);
    	if (!dir.exists()) {		// 로그 저장 폴더가 존재하지 않는 경우 생성함
    		dir.mkdir();
    	}
    	
    	Kogger.debug(UpdateMoldPlanStatusCmd.class, "@@@@@@@@@ strFilePath : " + strDirPath + "\\" + strFileName);
    	File file = new File(strDirPath + "\\" + strFileName);
    	if (!file.exists()) {		// 로그 저장 파일이 존재하지 않는 경우 생성함
    		file.createNewFile();
    	}
    	
    	String scheduleId = "";
    	int cnt =1;
    	try
    	{
			// 로그 남길 파일 생성
    		fw = new FileWriter(file, true);
    		
 		   QuerySpec spec = new QuerySpec(KETMoldChangePlan.class);
		   QueryResult result = PersistenceHelper.manager.find(spec);
		   KETMoldChangePlan plan = null;
		   
    		fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
    		fw.write("::::::총 개조수정일정 :"+result.size()+"\n" );
    		fw.write(":::::: START TIME :: " + currentDate + " " + currentTime + "\n");
    		fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
    		
    		
    		while( result.hasMoreElements() )
    		{
    			plan = (KETMoldChangePlan) result.nextElement();
    			
    			scheduleId = plan.getScheduleId();
    			try
    			{
    				KETEcmHelper.service.updateDailyMoldPlanStatus( plan );
        			fw.write("::::::: "+cnt +" : " +scheduleId+" --> Success \n");
    			}
    			catch( WTException e )
    			{
    				fw.write("::::::: "+cnt +" : " +scheduleId+" --> Fail \n");
    			}
    		    cnt++;
    		}

    	}
    	catch (wt.query.QueryException e) 
    	{
			Kogger.error(UpdateMoldPlanStatusCmd.class, e);
		} 
    	catch (WTException e) 
		{
			Kogger.error(UpdateMoldPlanStatusCmd.class, e);
		}
    	finally
    	{
        	currentDate = DateUtil.getCurrentDateString( new SimpleDateFormat("yyyy-MM-dd"));
        	currentTime = DateUtil.getCurrentDateString( new SimpleDateFormat("HH:mm:ss"));
    		fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
    		fw.write(":::::: END TIME :: " + currentDate + " " + currentTime + "\n");
    		fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
    		fw.flush();
			fw.close();
    	}


	}

}
