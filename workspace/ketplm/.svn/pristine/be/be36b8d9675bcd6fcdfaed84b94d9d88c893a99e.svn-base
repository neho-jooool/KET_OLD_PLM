package e3ps.groupware.folder;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.clients.folder.FolderTaskLogic;
import wt.fc.ReferenceFactory;
import wt.folder.Folder;
import wt.util.SortedEnumeration;
import e3ps.common.folder.FolderUtil;
import e3ps.common.web.CommonServlet;
import ext.ket.shared.log.Kogger;

public class FolderTreeServlet extends CommonServlet
{
    public void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        String action = req.getParameter("action");
        try
        {
            Vector list = null;
            if ("collapse".equals(action))
            {
                list = collapse(req, res);
            }
            else if ("expand".equals(action))
            {
                list = collapse(req, res);
            }
            else
            {
                list = drawTree(req, res);
            }
            req.setAttribute("list", list);
            this.gotoResult(req, res, "/jsp/groupware/folder/FolderTree.jsp");
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    public Vector drawTree(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        Vector list = new Vector();
        String folderPath = req.getParameter("folderpath");
        SortedEnumeration sen = null;
        if (folderPath == null)
            sen = FolderUtil.getAllCabinets(false);
        else
            sen = FolderUtil.getSubFolders(FolderTaskLogic.getFolder(folderPath));
        while (sen.hasMoreElements())
        {
            Folder folder = (Folder) sen.nextElement();
            list.add(new FolderTreeData(folder, 0, FolderTreeData.expand));
        }
        return list;
    }

    public Vector collapse(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        String[] nodes = req.getParameterValues("ptree");
        String[] states = req.getParameterValues("ptreeActions");
        String[] levels = req.getParameterValues("levels");
        String sindex = req.getParameter("index");
        int select = -1;
        if (sindex != null)
            select = Integer.parseInt(sindex);
        ReferenceFactory rf = new ReferenceFactory();
        Vector list = new Vector();
        FolderTreeData pdata = null;
        int slevel = -1;
        for (int i = 0; i < nodes.length; i++)
        {
            int plevel = Integer.parseInt(levels[i]);
            if (slevel >= 0 && slevel < plevel)
                continue;
            else
                slevel = -1;
            Folder folder = (Folder) rf.getReference(nodes[i]).getObject();
            list.add(pdata = new FolderTreeData(folder, plevel, states[i]));
            if (select == i)
            {
                pdata.toggle();
                if (FolderTreeData.expand.equals(pdata.image))
                    slevel = pdata.level;
                else
                {
                    SortedEnumeration en = FolderUtil.getSubFolders(folder);
                    if (en.size() == 0)
                    {
                        pdata.image = FolderTreeData.empty;
                    }
                    else
                        pdata.image = FolderTreeData.collapse;
                    while (en.hasMoreElements())
                    {
                        Folder subfolder = (Folder) en.nextElement();
                        list.add(new FolderTreeData(subfolder, plevel + 1, FolderTreeData.expand));
                    }
                }
            }
        }
        return list;
    }
}
