/**
 * @(#)	NotifyData.java
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.3
 * @author Cho Sung Ok, jerred@e3ps.com
 * @modify
 *
 */

package e3ps.groupware.board;

import java.sql.Timestamp;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.org.WTUser;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.content.ContentUtil;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.groupware.company.PeopleData;
import ext.ket.shared.log.Kogger;

public class NotifyData
{
    public Notify notify;
    public String oid = "";
    public String title = "";
    public String writer = "";
    public String department = "";
    public String writeDate = "";
    public String writeListDate = "";
    public boolean isAttache = false;
    public boolean isNew = false;
    public Vector attacheList = new Vector();
    public int readCount;
    public String contents = "";
    public Timestamp deadline;

    // [PLM System 1차개선] 수정내용 : 검색결과 Grid 적용, 2013. 06. 14, 김무준
    public String webEditor = "";
    public String webEditorText = "";

    /**
     * @param _notify
     * @param type :
     *            내부 Attr을 어떻게 구성해 줄것인가에 대한 type(0:simple-리스트에 필요한 정보, 1:all-모든
     *            정보)
     */
    public NotifyData(Notify _notify, int type)
    {
        this.notify = _notify;
        this.oid = CommonUtil.getOIDString(_notify);
        this.title = _notify.getTitle();
        Timestamp timeStamp = _notify.getPersistInfo().getCreateStamp();
        this.writeDate = DateUtil.getDateToWeb(timeStamp);
        this.writer = _notify.getOwner().getFullName();
        try
        {
            this.department = new PeopleData(_notify.getOwner().getPrincipal()).departmentName;
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
        this.isNew = DateUtil.isToday(timeStamp);
        this.readCount = getReadCount();
        this.attacheList = ContentUtil.getSecondaryContents(_notify);
        if (this.attacheList.size() != 0)
            this.isAttache = true;

        if (type == 1)
        {
            this.writeDate = DateUtil.getDateString(timeStamp, "all");
            this.contents = (String) _notify.getContents();

            // [PLM System 1차개선] 수정내용 : 검색결과 Grid 적용, 2013. 06. 14, 김무준
            this.webEditor = (String) _notify.getWebEditor();
            this.webEditorText = (String) _notify.getWebEditorText();
        }
        this.deadline = _notify.getDeadline();
    }

    private int getReadCount()
    {
        try
        {
            QueryResult qr = PersistenceHelper.manager.navigate(this.notify, NotifyWTUserLink.USER_ROLE,
                                                                NotifyWTUserLink.class, true);
            return qr.size();
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
            return 0;
        }
    }

    public void updateReadCount()
    {
        if (!checkRead())
        {
            try
            {
                NotifyWTUserLink link = NotifyWTUserLink.newNotifyWTUserLink((WTUser) SessionHelper.manager
                        .getPrincipal(), this.notify);
                PersistenceHelper.manager.save(link);
            }
            catch (WTException e)
            {
                Kogger.error(getClass(), e);
            }
        }
    }

    private boolean checkRead()
    {
        try
        {
            QuerySpec spec = new QuerySpec();
            Class mainClass = NotifyWTUserLink.class;
            spec.addClassList(mainClass, true);

            spec.appendWhere(new SearchCondition(mainClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
                    .getOIDLongValue(this.oid)));
            spec.appendAnd();
            spec.appendWhere(new SearchCondition(mainClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
                    .getOIDLongValue((WTUser) SessionHelper.manager.getPrincipal())));

            QueryResult rs = PersistenceHelper.manager.find(spec);
            if (rs.hasMoreElements())
                return true;
            else
                return false;
        }
        catch (QueryException e)
        {
            Kogger.error(getClass(), e);
            return false;
        }
        catch (WTIntrospectionException e)
        {
            Kogger.error(getClass(), e);
            return false;
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
            return false;
        }
    }

    public boolean isPreferred() {
        return (notify.getPreferred() == 1);
    }

    public static NotifyData getLastNotify() throws Exception
    {
        QuerySpec spec = new QuerySpec();
        Class mainClass = Notify.class;
        int mainClassPos = spec.addClassList(mainClass, true);
        SearchUtil.setOrderBy(spec, mainClass, mainClassPos, "thePersistInfo.createStamp", "date", true);
        QueryResult result = PersistenceHelper.manager.find(spec);
        if (result.hasMoreElements())
        {
            Object[] obj = (Object[]) result.nextElement();
            return new NotifyData((Notify) obj[0], 0);
        }
        return null;
    }
}
