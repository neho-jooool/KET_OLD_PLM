package e3ps.load.bom;

import java.io.IOException;
import java.sql.SQLException;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import e3ps.common.util.KETObjectUtil;
import ext.ket.shared.log.Kogger;

public class TestCmd {

	public static String strDirPath = "D:\\ptc\\Windchill\\logs\\bom";

	 public static void main(String args[]) throws SQLException, IOException {

    	if( args.length != 3 )
        {
            // 사용법 설명
            Kogger.debug(TestCmd.class, ">>> Usage  : windchill TestCmd userID passwd");
            return;
        }

    	// 사용자 인증
		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);

		String ecaOid = "";
		ecaOid = args[2];

		try
		{
			Kogger.debug(TestCmd.class, "Delete Link Start=========");
//			KETMoldECAEpmDocLink link = (KETMoldECAEpmDocLink) KETObjectUtil.getObject("e3ps.ecm.entity.KETMoldECAEpmDocLink:86736879");
			Persistable po = (Persistable) KETObjectUtil.getObject(ecaOid);
			PersistenceHelper.manager.delete( po );

			Kogger.debug(TestCmd.class, "Delete Link End=========");
		}
		catch (Exception e)
		{
			Kogger.debug(TestCmd.class, "Error : "+e);
		}

	 }

	public static boolean reSult(String itemtype)
	{
		boolean rs = true;
		 if( !(itemtype.equals("1") || itemtype.equals("2")) )
		 {
			Kogger.debug(TestCmd.class, "숫자 1 혹은 2 만 입력할 수 있습니다.");
		 }else{
			rs = false;
		 }
		 return rs;
	}
}
