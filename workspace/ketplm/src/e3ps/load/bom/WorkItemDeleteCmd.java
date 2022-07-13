package e3ps.load.bom;

import e3ps.common.util.KETObjectUtil;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;

public class WorkItemDeleteCmd {

	/**
	 * 함수명 : main
	 * 설명 : 
	 * @param args
	 * 작성자 : 오승연
	 * 작성일자 : 2011. 5. 12.
	 */
	public static void main(String[] args) {
		
		if( args.length != 4 )
        {
            // 사용법 설명
            Kogger.debug(WorkItemDeleteCmd.class, ">>> Usage  : java e3ps.load.bom.WorkItemDeleteCmd userID passwd flag(M/P) ida2a2 ");
            return;
        }

    	// 사용자 인증
		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);

		String flag = args[2];
		String ida2a2 = args[3];

		try
		{
			Kogger.debug(WorkItemDeleteCmd.class, "WorkItemDelete(ECA) Start=========");			
			
			if( flag.equals("M") )
			{
				KETMoldChangeActivity moldEca = (KETMoldChangeActivity) KETObjectUtil.getObject("e3ps.ecm.entity.KETMoldChangeActivity:"+ida2a2);
				WorkflowSearchHelper.manager.deleteWorkItem( moldEca );
			}
			else
			{
				KETProdChangeActivity prodEca = (KETProdChangeActivity) KETObjectUtil.getObject("e3ps.ecm.entity.KETProdChangeActivity:"+ida2a2);
				WorkflowSearchHelper.manager.deleteWorkItem( prodEca );
			}

			Kogger.debug(WorkItemDeleteCmd.class, "WorkItemDelete(ECA) End=========");
		}
		catch (Exception e)
		{
			Kogger.debug(WorkItemDeleteCmd.class, "Error : "+e);
		}
	}
}
