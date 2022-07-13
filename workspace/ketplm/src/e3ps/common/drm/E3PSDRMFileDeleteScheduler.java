package e3ps.common.drm;

import java.io.File;

import ext.ket.shared.log.Kogger;

public class E3PSDRMFileDeleteScheduler
{
    /**
     * @param args
     */
    public static void main( String[] args )
    {

//        wt.method.RemoteMethodServer remotemethodserver = wt.method.RemoteMethodServer.getDefault();
//
//        remotemethodserver.setUserName(adminId);
//        remotemethodserver.setPassword(adminPw);

        Kogger.debug(E3PSDRMFileDeleteScheduler.class, "E3PSDRMFileDeleteScheduler START============");
        E3PSDRMFileDeleteScheduler.manager.doDelete();
        Kogger.debug(E3PSDRMFileDeleteScheduler.class, "E3PSDRMFileDeleteScheduler END   ============");
    }

    public static E3PSDRMFileDeleteScheduler manager = new E3PSDRMFileDeleteScheduler();

    public static void doDelete()
    {
    	try
    	{
	        E3PSDRMHelper.drmInit();

	        String downloadFolderStr = E3PSDRMHelper.downloadFileTempFolder;
	        String uploadFolderStr = E3PSDRMHelper.uploadFileTempFolder;
	        String wtTempFolderStr = E3PSDRMHelper.wtTempFolder;

	        File downloadFolder = new File( downloadFolderStr );
	        File uploadFolder = new File(uploadFolderStr);
	        File wtTempFolder = new File(wtTempFolderStr);

	        if( downloadFolder.isDirectory() )
	        {
	            File[] temp = downloadFolder.listFiles();

	            for( int i = 0; i < temp.length; i++ )
	            {
	                System.out.print( "[DELETE] " +temp[i].getAbsolutePath() );
	                temp[i].delete();
	                Kogger.debug(E3PSDRMFileDeleteScheduler.class,  "   ..... done." );
	            }
	        }

	        if( uploadFolder.isDirectory() )
	        {
	            File[] temp = uploadFolder.listFiles();

	            for( int i = 0; i < temp.length; i++ )
	            {
	                System.out.print( "[DELETE] " +temp[i].getAbsolutePath() );
	                temp[i].delete();
	                Kogger.debug(E3PSDRMFileDeleteScheduler.class,  "   ..... done." );
	            }
	        }

	        if( wtTempFolder.isDirectory() )
	        {
	            File[] temp = wtTempFolder.listFiles();

	            for( int i = 0; i < temp.length; i++ )
	            {
	                System.out.print( "[DELETE] " +temp[i].getAbsolutePath() );
	                temp[i].delete();
	                Kogger.debug(E3PSDRMFileDeleteScheduler.class,  "   ..... done." );
	            }
	        }
    	}
    	catch( Exception e )
    	{
    		Kogger.error(E3PSDRMFileDeleteScheduler.class, e);
    	}
    }
}
