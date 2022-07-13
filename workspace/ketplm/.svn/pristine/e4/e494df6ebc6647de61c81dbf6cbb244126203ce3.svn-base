/*
 * @(#) BoardHelper.java  Create on 2006. 3. 28.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.groupware.board;

import java.util.Enumeration;
import java.util.Vector;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.query.QueryException;
import wt.queue.QueueHelper;
import wt.queue.ScheduleQueue;
import wt.queue.ScheduleQueueEntry;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;

/**
 * 공지사항 자동 제거
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2006. 3. 28.
 * @since 1.4
 */
public class NotifyHelper implements RemoteAccess
{
    public static void createSchedule(Notify _notify)
    {
        if (!RemoteMethodServer.ServerFlag)
        {
            try
            {
                Class argTypes[] = { Notify.class };
                Object argValues[] = { _notify };
                RemoteMethodServer.getDefault().invoke("createSchedule", "e3ps.groupware.board.NotifyHelper", null,
                                                       argTypes, argValues);
                return;
            }
            catch (Exception ex)
            {
                Kogger.error(NotifyHelper.class, ex);
            }
        }

        if (_notify==null || _notify.getDeadline() == null) { return; }
        try
        {
            SessionHelper.manager.setAdministrator();
            ScheduleQueue queue = (ScheduleQueue) QueueHelper.manager.getQueue("WfScheduleQueue", ScheduleQueue.class);
            Class[] argClasses = { String.class };
            Object[] argObjects = { CommonUtil.getOIDString(_notify) };
            queue.addEntry(_notify.getOwner().getPrincipal(), "deleteNotify", "e3ps.groupware.board.NotifyHelper",
                           argClasses, argObjects, _notify.getDeadline());
        }
        catch (WTException e)
        {
            Kogger.error(NotifyHelper.class, e);
        }
    }

    public static void updateSchedule(Notify _notify)
    {
        if (!RemoteMethodServer.ServerFlag)
        {
            try
            {
                Class argTypes[] = { Notify.class };
                Object argValues[] = { _notify };
                RemoteMethodServer.getDefault().invoke("updateSchedule", "e3ps.groupware.board.NotifyHelper", null,
                                                       argTypes, argValues);
                return;
            }
            catch (Exception ex)
            {
                Kogger.error(NotifyHelper.class, ex);
            }
        }

        deleteSchedule(_notify);
        createSchedule(_notify);
    }

    public static void deleteSchedule(Notify _notify)
    {
        if (!RemoteMethodServer.ServerFlag)
        {
            try
            {
                Class argTypes[] = { Notify.class };
                Object argValues[] = { _notify };
                RemoteMethodServer.getDefault().invoke("deleteSchedule", "e3ps.groupware.board.NotifyHelper", null,
                                                       argTypes, argValues);
                return;
            }
            catch (Exception ex)
            {
                Kogger.error(NotifyHelper.class, ex);
            }
        }
        if (_notify == null) { return; }
        try
        {
            ScheduleQueue queue = (ScheduleQueue) QueueHelper.manager.getQueue("WfScheduleQueue", ScheduleQueue.class);

            Enumeration enm = QueueHelper.manager.queueEntries(queue);
            while (enm.hasMoreElements())
            {
                ScheduleQueueEntry temp = (ScheduleQueueEntry) enm.nextElement();
                Vector vec = temp.getArgs();
                for (int i = vec.size() - 1; i > -1; i--)
                {
                    Object v = vec.get(i);
                    if (v.toString().indexOf(CommonUtil.getOIDString(_notify)) > 0)
                    {
//                        QueueHelper.manager.deleteEntries(queue);
                        PersistenceHelper.manager.delete(temp);
                        break;
                    }
                }
            }
        }
        catch (WTException e)
        {
            Kogger.error(NotifyHelper.class, e);
        }
    }

    public static void deleteNotify(String _oid)
    {
        if (!RemoteMethodServer.ServerFlag)
        {
            try
            {
                Class argTypes[] = { String.class };
                Object argValues[] = { _oid };
                RemoteMethodServer.getDefault().invoke("deleteNotify", "e3ps.groupware.board.NotifyHelper", null,
                                                       argTypes, argValues);
                return;
            }
            catch (Exception ex)
            {
                Kogger.error(NotifyHelper.class, ex);
            }
        }

        try
        {
            Persistable per = CommonUtil.getObject(_oid);
            if (per != null)
                PersistenceHelper.manager.delete(per);
        }
        catch (QueryException e)
        {
            Kogger.error(NotifyHelper.class, e);
        }
        catch (WTException e)
        {
            Kogger.error(NotifyHelper.class, e);
        }
    }
}
