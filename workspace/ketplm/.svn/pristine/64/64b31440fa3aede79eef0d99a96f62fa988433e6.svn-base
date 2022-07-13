/*
 * @(#) RevisionServlet.java  Create on 2004. 11. 16.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.obj;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.clients.vc.CheckInOutTaskLogic;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.FormatContentHolder;
import wt.enterprise.Master;
import wt.epm.EPMDocument;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.folder.Foldered;
import wt.part.WTPart;
import wt.pom.PersistenceException;
import wt.query.QuerySpec;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.Versioned;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.NonLatestCheckoutException;
import wt.vc.wip.WorkInProgressException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.multipart.Multiparser;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.folder.FolderUtil;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.jdf.message.Message;
import e3ps.common.jdf.message.MessageBox;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import ext.ket.shared.log.Kogger;


/**
 * RevisionControlled 객체를 체크인/아웃 한다.
 * 
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2004. 11. 16.
 * @since 1.4
 */
public class CheckInOutServlet extends CommonServlet
{
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        Message message = MessageBox.getInstance("message");
        String cmd = req.getParameter("cmd");
        String oid = req.getParameter("oid");
        String nextPage = req.getParameter("nextpage");

        if ("checkout".equalsIgnoreCase(cmd))
        {
            String temp = checkOut(req, res);
            String msg = message.getMessage("checkout.fail");
            if (temp != null)
            {
                oid = temp;
                msg = message.getMessage("checkout.success");
            }
            alertNgo(res, msg, nextPage + "?oid=" + oid);
        }
        else if ("undoCheckOut".equalsIgnoreCase(cmd))
        {
            String temp = undoCheckOut(req, res);
            String msg = message.getMessage("undocheckout.fail");
            if (temp != null)
            {
                oid = temp;
                msg = message.getMessage("undocheckout.success");
            }
            alertNgo(res, msg, nextPage + "?oid=" + oid);
        }
        else if ("checkin".equalsIgnoreCase(cmd))
        {
            checkIn(req, res);
            alertNsubmitNclose(res, message.getMessage("checkin.success"));
        }
        else if ("revise".equalsIgnoreCase(cmd))
        {
            Workable workable = revise(req);
            String temp = CommonUtil.getOIDString(workable);
            String msg = message.getMessage("revise.fail");
            if (temp != null)
            {
                String location = null;
                if(workable instanceof Foldered)
                    location = ((Foldered)workable).getLocation();
                
                msg = message.getMessage("revise.success") + ". " + (location==null?"":"저장위치는 " + location+" 입니다");
//                msg = message.getMessage("revise.success") + ". 개인캐비넷에 저장되었습니다";
            }
            alertNclose(res, msg);
        }
    }

    /**
     * @param req
     * @param res
     * @return
     */
    protected Workable revise(HttpServletRequest req)
    {
        Config conf = ConfigImpl.getInstance();
        
        String oid = req.getParameter("oid");
        String note = req.getParameter("note");
        Versioned versioned = (Versioned) CommonUtil.getObject(oid);
        Workable workable = null;
        try
        {
//            String lifecycle = ((LifeCycleManaged)versioned).getLifeCycleName();
//            if(conf.getString("lifecycle.default").equals(lifecycle))?
//                lifecycle = conf.getString("lifecycle.e3ps.default");
            
            versioned = ObjectUtil.getLatestObject((Master) versioned.getMaster());
            
            // EPMDocument 를 개정할 경우 passive 링크인 경우는 부품과 재연결을 해준다.
            QueryResult qr =  null;
            if(versioned instanceof EPMDocument)
            {
                qr = wt.vc.struct.StructHelper.service.navigateDescribes((EPMDocument)versioned, false);
            }
            
            boolean isCo = Boolean.valueOf(ParamUtil.getStrParameter(req.getParameter("loc"), "false")).booleanValue();
            
            Folder folder = null;
            if(isCo)
                folder = FolderHelper.getFolder((Foldered)versioned);
            else
                folder = FolderUtil.getPersonalCabinet();
            
            versioned = ObjectUtil.revise(versioned, conf.getString("lifecycle.default"), folder, note);
            
            if(qr != null)
            {
                ArrayList partList = new ArrayList(); // 중복링크 연결 제거용
                EPMDocument epm = (EPMDocument)versioned;
                String ver = epm.getVersionIdentifier().getValue();
                while(qr.hasMoreElements())
                {
                    wt.epm.structure.EPMDescribeLink epmdescribelink = (wt.epm.structure.EPMDescribeLink) qr.nextElement();
                    WTPart tempPart = (WTPart) epmdescribelink.getDescribes();

                    if(partList.contains(tempPart.getNumber())) continue;
                    partList.add(tempPart.getNumber());
                    
                    WTPart lastPart = (WTPart)ObjectUtil.getLatestObject((Master)tempPart.getMaster());
                    
                }
            }
//            VersionControlHelper.setNote(versioned, note);

            workable = (Workable) versioned;
//            CheckoutLink checkoutlink = WorkInProgressHelper.service.checkout(workable, CheckInOutTaskLogic.getCheckoutFolder(), "");
//            workable = checkoutlink.getOriginalCopy();
        }
        catch (NonLatestCheckoutException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WorkInProgressException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (PersistenceException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        return workable;
    }

    protected String checkOut(HttpServletRequest req, HttpServletResponse res)
    {
        String oid = req.getParameter("oid");
        Workable workable = (Workable) CommonUtil.getObject(oid);
        try
        {
            if (!CheckInOutTaskLogic.isCheckedOut(workable))
            {
                CheckoutLink checkoutlink = WorkInProgressHelper.service.checkout(workable, CheckInOutTaskLogic
                        .getCheckoutFolder(), "");
                return CommonUtil.getOIDString(checkoutlink.getOriginalCopy());
            }
        }
        catch (WorkInProgressException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        return null;
    }

    protected String undoCheckOut(HttpServletRequest req, HttpServletResponse res)
    {
        String oid = req.getParameter("oid");
        Workable workable = null;
        try
        {
            workable = (Workable) CommonUtil.getObject(oid);
            if ((workable != null && CheckInOutTaskLogic.isCheckedOutByUser(workable)) || CommonUtil.isAdmin()) { return CommonUtil
                    .getOIDString(WorkInProgressHelper.service.undoCheckout(workable)); }
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
        return null;
    }

    protected void checkIn(HttpServletRequest req, HttpServletResponse res)
    {
        Multiparser multi = Multiparser.newMultiparser(req);
        String comment = multi.getParameter("comment");
        Workable workable = (Workable) CommonUtil.getObject(multi.getParameter("oid"));

        String primaryContentType = (String) multi.getParameter("primaryContentType");
        try
        {
            boolean flag = WorkInProgressHelper.isCheckedOut(workable);
            boolean flag1 = WorkInProgressHelper.isCheckedOut(workable, SessionHelper.manager.getPrincipal());

            if (flag && flag1)
            {
                workable = getWorkingCopy(workable);
                WTObject tempObj = (WTObject)workable;
                if (tempObj instanceof FormatContentHolder)
                {
                    FormatContentHolder fch = (FormatContentHolder) wt.content.ContentHelper.service
                            .getContents((ContentHolder) workable);
                    ContentItem contentItem = fch.getPrimary();

                    if ("file".equals(primaryContentType))
                    {
                        Vector files = multi.getFiles();
                        WBFile file = (WBFile) files.get(0);
                        if (contentItem == null && file != null)
                            E3PSContentHelper.service.attach((ContentHolder) workable, file, "");
                        else if (file != null)
                            E3PSContentHelper.service.replace((ContentHolder) workable, file, "", contentItem);
                    }
                    else if ("url".equals(primaryContentType))
                    {
                        String primaryUrl = multi.getParameter("primaryURL");
                        if (contentItem == null)
                            E3PSContentHelper.service.attachURL((ContentHolder) workable, primaryUrl, "",
                                                                ContentRoleType.PRIMARY);
                        else
                            E3PSContentHelper.service.replaceURL((ContentHolder) workable, primaryUrl, "", contentItem);
                    }
                }

                workable = WorkInProgressHelper.service.checkin(workable, comment);
            }
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (PropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
    }

    protected void setContent(Workable workable, Multiparser multi)
    {
        ContentHolder holder = (ContentHolder) getWorkingCopy(workable);
        String primaryContentType = (String) multi.getParameter("primaryContentType");

        try
        {
            FormatContentHolder fch = (FormatContentHolder) wt.content.ContentHelper.service.getContents(holder);
            ContentItem contentItem = fch.getPrimary();

            if ("url".equals(primaryContentType))
            {
                String primaryUrl = multi.getParameter("primaryURL");
                if (contentItem == null)
                    E3PSContentHelper.service.attachURL(fch, primaryUrl, "", ContentRoleType.PRIMARY);
                else
                    E3PSContentHelper.service.replaceURL(fch, primaryUrl, "", contentItem);
            }

            Vector files = multi.getFiles();
            for (int i = files.size() - 1; i > -1; i--)
            {
                WBFile file = (WBFile) files.get(i);
                if (file.getFieldName().equals("PRIMARY"))
                {
                    if (contentItem == null && file != null)
                        E3PSContentHelper.service.attach(fch, file, "");
                    else if (file != null)
                        E3PSContentHelper.service.replace(fch, file, "", contentItem);
                }
                else
                {
                    E3PSContentHelper.service.attach(fch, file, "");
                }
            }

        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (PropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
    }

    protected Workable getWorkingCopy(Workable workable)
    {
        try
        {
            if (!WorkInProgressHelper.isWorkingCopy(workable))
                return WorkInProgressHelper.service.workingCopyOf(workable);
            else
                return workable;
        }
        catch (WorkInProgressException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see e3ps.util.servlet.CommonServlet#getQuerySpec(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected QuerySpec getQuerySpec(HttpServletRequest arg0, HttpServletResponse arg1)
    {
        return null;
    }
}
