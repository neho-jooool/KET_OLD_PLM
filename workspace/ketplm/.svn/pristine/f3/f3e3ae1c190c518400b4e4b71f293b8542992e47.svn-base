package e3ps.groupware.company.beans;

import wt.method.RemoteMethodServer;
import e3ps.groupware.company.E3PSCompanyHelper;
import ext.ket.shared.log.Kogger;

public class UpdateUserInfo
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			RemoteMethodServer methodServer = RemoteMethodServer.getDefault();

			if( methodServer.getUserName() == null )
			{
				if( args.length != 2 )
				{
					Kogger.debug(UpdateUserInfo.class, "UpdateUserInfo Failed :: ID & Password Check!");
				}

				methodServer.setUserName( args[0] );
				methodServer.setPassword( args[1] );

				Kogger.debug(UpdateUserInfo.class, "AUTHENTICATION SUCCESS");
			}
			else
			{
				Kogger.debug(UpdateUserInfo.class, "AUTHENTICATION FAILE");
			}

			Kogger.debug(UpdateUserInfo.class, "===============================================");
			Kogger.debug(UpdateUserInfo.class, "=========== Start to Update Department Information!");

//			E3PSCompanyHelper.service.syncWTGroupUser();
			E3PSCompanyHelper.service.syncDepartment();

			Kogger.debug(UpdateUserInfo.class, "=========== End to Update Department Information!");
			Kogger.debug(UpdateUserInfo.class, "===============================================");
			Kogger.debug(UpdateUserInfo.class, "=========== Start to update User Information!");

			E3PSCompanyHelper.service.syncPeopleWTUser();

			Kogger.debug(UpdateUserInfo.class, "=========== End to Update User Information!");
			Kogger.debug(UpdateUserInfo.class, "===============================================");
			Kogger.debug(UpdateUserInfo.class, "=========== Start to Check Chief User Information!");

			E3PSCompanyHelper.service.checkChiefInfo();

			Kogger.debug(UpdateUserInfo.class, "=========== End to Check Chief User Information!");
			Kogger.debug(UpdateUserInfo.class, "===============================================");
		}
		catch( Exception e )
		{
			Kogger.error(UpdateUserInfo.class, e);
		}
	}
}
