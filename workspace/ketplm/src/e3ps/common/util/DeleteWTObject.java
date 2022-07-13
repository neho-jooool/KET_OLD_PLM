/*
 * @(#) DeleteWTObject.java  Create on 2005. 3. 24.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.util;

import java.util.Locale;

import wt.access.WTAclEntry;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.lifecycle.LifeCycleManaged;
import wt.method.RemoteAccess;
import wt.org.WTUser;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.team.TeamException;
import wt.team.TeamHelper;
import wt.team.TeamManaged;
import wt.util.WTException;
import e3ps.common.jdf.log.Logger;
//import e3ps.common.workflow.E3PSWorkflowHelper;
//import e3ps.groupware.workprocess.beans.WFItemHelper;
import ext.ket.shared.log.Kogger;

/**
 * 
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2005. 3. 24.
 * @since 1.4
 */
public class DeleteWTObject implements RemoteAccess
{
    public static String delete(Persistable persistable)
    {
        String msg = "성공적으로 삭제되었습니다";
        if (persistable instanceof LifeCycleManaged)
//            E3PSWorkflowHelper.manager.deleteWfProcess((LifeCycleManaged) persistable);

        try
        {
            String temp = persistable.getIdentity();
//            WFItemHelper.manager.deleteWFItem(persistable);
            if(persistable instanceof WTObject)
                deleteAclObject((WTObject) persistable);
            PersistenceHelper.manager.delete(persistable);
            
            WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
            Logger.info.println(temp + " 삭제 - by " + user.getFullName() +", "+ user.getName() );
        }
        catch (WTException e)
        {
            msg = e.getLocalizedMessage(Locale.KOREA);
            Logger.info.println(msg);
            Kogger.error(DeleteWTObject.class, e);
        }
        catch (Exception e)
        {
            Kogger.error(DeleteWTObject.class, e);
            msg = e.getLocalizedMessage();
            Logger.info.println(msg);
        }
        return msg;
    }

    public static void deleteAclObject(WTObject obj) throws Exception
    {
        try
        {
            QuerySpec query = new QuerySpec(WTAclEntry.class);
            if (obj instanceof TeamManaged)
            {
                Team team = TeamHelper.service.getTeam((TeamManaged) obj);
            	if(team != null) {
            		query.appendWhere(new SearchCondition(WTAclEntry.class, "aclReference.key.id", "=", CommonUtil
                            .getOIDLongValue(team)), new int[] { 0 });
                    query.appendOr();
            	}
            }
            query.appendWhere(new SearchCondition(WTAclEntry.class, "aclReference.key.id", "=", CommonUtil
                    .getOIDLongValue(obj)), new int[] { 0 });

            QueryResult qr = PersistenceHelper.manager.find(query);

            while (qr.hasMoreElements())
            {
                WTAclEntry element = (WTAclEntry) qr.nextElement();
                PersistenceHelper.manager.delete(element);
            }
            // PersistenceHelper.manager.delete(obj);
        }
        catch (QueryException e)
        {
            Kogger.error(DeleteWTObject.class, e);
            throw new Exception(e.getLocalizedMessage());
        }
        catch (TeamException e)
        {
            Kogger.error(DeleteWTObject.class, e);
            throw new Exception(e.getLocalizedMessage());
        }
        catch (WTException e)
        {
            Kogger.error(DeleteWTObject.class, e);
            throw new Exception(e.getLocalizedMessage());
        }
    }
}
/*
 * $Log: not supported by cvs2svn $
 * Revision 1.3  2009/10/25 07:57:39  jspark
 *
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.2  2009/10/25 07:46:27  jspark
 *
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.1  2009/08/11 04:16:21  administrator
 * Init Source
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.1  2009/02/25 01:25:49  smkim
 * 최초 작성
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.5  2008/04/04 07:24:32  smkim
 * 수정
 *
 * Revision 1.3  2008/03/10 06:01:56  hyun
 * 설계미팅
 *
 * Revision 1.1  2008/01/29 06:25:02  sjhan
 * 주성 기본 패키지 정리작업 완료
 *
 * Revision 1.2  2007/10/17 04:54:55  sjhan
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2007/04/17 01:21:30  administrator
 * no message
 *
 * Revision 1.1.1.1  2007/02/14 07:53:56  administrator
 * no message
 *
 * Revision 1.1  2006/05/09 02:35:06  shchoi
 * *** empty log message ***
 *
 * Revision 1.7  2006/04/28 08:26:23  shchoi
 * 샥제시 WTObject 여부 체크
 *
 * Revision 1.6  2006/02/10 08:33:13  shchoi
 * Logger 추가
 *
 * Revision 1.5  2006/01/10 11:26:31  shchoi
 * *** empty log message ***
 *
 * Revision 1.4  2006/01/10 11:03:13  shchoi
 * *** empty log message ***
 *
 * Revision 1.3  2005/12/19 07:52:48  shchoi
 * *** empty log message ***
 * /* Revision 1.2 2005/12/12 10:45:31 shchoi /* *** empty log message *** /* /*
 * Revision 1.1 2005/12/09 12:20:35 shchoi /* *** empty log message *** /* /*
 * Revision 1.3 2005/12/05 10:25:02 shchoi /* *** empty log message *** /* /*
 * Revision 1.2 2005/10/12 06:44:25 shchoi /* 각 Util 수정 /* /* Revision 1.1
 * 2005/10/06 05:19:46 shchoi /* *** empty log message *** /* /* Revision 1.6
 * 2005/03/31 02:33:19 skyprda /* *** empty log message *** /* /* Revision 1.5
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* $Log: not supported by cvs2svn $
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.3  2009/10/25 07:57:39  jspark
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Committed on the Free edition of March Hare Software CVSNT Server.
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Upgrade to CVS Suite for more features and support:
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* http://march-hare.com/cvsnt/
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.2  2009/10/25 07:46:27  jspark
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Committed on the Free edition of March Hare Software CVSNT Server.
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Upgrade to CVS Suite for more features and support:
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* http://march-hare.com/cvsnt/
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.1  2009/08/11 04:16:21  administrator
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Init Source
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Committed on the Free edition of March Hare Software CVSNT Server.
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Upgrade to CVS Suite for more features and support:
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* http://march-hare.com/cvsnt/
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.1  2009/02/25 01:25:49  smkim
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* 최초 작성
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Committed on the Free edition of March Hare Software CVSNT Server.
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Upgrade to CVS Suite for more features and support:
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* http://march-hare.com/cvsnt/
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.5  2008/04/04 07:24:32  smkim
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* 수정
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.3  2008/03/10 06:01:56  hyun
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* 설계미팅
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.1  2008/01/29 06:25:02  sjhan
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* 주성 기본 패키지 정리작업 완료
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.2  2007/10/17 04:54:55  sjhan
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* *** empty log message ***
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.1.1.1  2007/04/17 01:21:30  administrator
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* no message
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.1.1.1  2007/02/14 07:53:56  administrator
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* no message
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.1  2006/05/09 02:35:06  shchoi
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* *** empty log message ***
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.7  2006/04/28 08:26:23  shchoi
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* 샥제시 WTObject 여부 체크
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.6  2006/02/10 08:33:13  shchoi
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Logger 추가
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.5  2006/01/10 11:26:31  shchoi
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* *** empty log message ***
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.4  2006/01/10 11:03:13  shchoi
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* *** empty log message ***
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /*
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* Revision 1.3  2005/12/19 07:52:48  shchoi
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* *** empty log message ***
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* ======= /* /*
 * Revision 1.2 2005/12/12 10:45:31 shchoi /* *** empty log message *** /* /*
 * Revision 1.1 2005/12/09 12:20:35 shchoi /* *** empty log message *** /* /*
 * Revision 1.3 2005/12/05 10:25:02 shchoi /* *** empty log message *** /* /*
 * Revision 1.2 2005/10/12 06:44:25 shchoi /* 각 Util 수정 /* /* Revision 1.1
 * 2005/10/06 05:19:46 shchoi /* *** empty log message *** /* /* Revision 1.6
 * 2005/03/31 02:33:19 skyprda /* *** empty log message *** /* /* Revision 1.5
 * 2005/03/24 01:59:54 skyprda /* *** empty log message *** /* /* Revision 1.4
 * 2005/03/24 01:42:19 ljh /* *** empty log message *** /* >>>>>>> 1.4 /*
 * Revision 1.2 2005/03/24 01:24:40 skyprda /* delete 변경 /* /* Revision 1.1
 * 2005/03/24 01:03:24 skyprda /* 윈칠객체 삭제 /*
 */
