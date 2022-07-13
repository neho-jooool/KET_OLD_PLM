package e3ps.load.ecm;

import e3ps.common.util.KETObjectUtil;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;

public class WorkCompleteECACmd {

	/**
	 * 함수명 : main
	 * 설명 : 
	 * @param args
	 * 작성자 : 오승연
	 * 작성일자 : 2011. 4. 29.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if( args.length != 4 )
        {
            // 사용법 설명
            Kogger.debug(WorkCompleteECACmd.class, ">>> Usage  : java e3ps.load.ecm.WorkCompleteECACmd userID passwd flag(M/P) ida2a2 ");
            return;
        }
		
		// 사용자 인증
		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);

		String flag = args[2];
		String ida2a2 = args[3];

		try
		{
			Kogger.debug(WorkCompleteECACmd.class, "WorkComplete(ECA) Start=========");			
			
			if( flag.equals("M") )
			{
				KETMoldChangeActivity moldEca = (KETMoldChangeActivity) KETObjectUtil.getObject("e3ps.ecm.entity.KETMoldChangeActivity:"+ida2a2);
				WorkflowSearchHelper.manager.taskComplete( moldEca );
			}
			else
			{
				KETProdChangeActivity prodEca = (KETProdChangeActivity) KETObjectUtil.getObject("e3ps.ecm.entity.KETProdChangeActivity:"+ida2a2);
				WorkflowSearchHelper.manager.taskComplete( prodEca );
			}

			Kogger.debug(WorkCompleteECACmd.class, "WorkComplete(ECA) End=========");
		}
		catch (Exception e)
		{
			Kogger.debug(WorkCompleteECACmd.class, "Error : "+e);
		}
	}
}
