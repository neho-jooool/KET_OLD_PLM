package e3ps.groupware.folder;

import wt.admin.AdminDomainRef;
import wt.admin.AdministrativeDomainHelper;
import wt.admin.DomainAdministered;
import wt.admin.DomainAdministeredHelper;
import wt.fc.PersistenceHelper;
import wt.folder.Cabinet;
import wt.folder.Folder;

public class FolderTreeData
{
    public static String expand = "expand";
    public static String collapse = "collapse";
    public static String empty = "icon_none";

    public int level;
    public String name;
    public String oid;
    public String image;
    public String type;
    public String slevel;
    public String domain;

    public FolderTreeData(Folder folder, int level, String action)
    {
        this.name = folder.getName();
        this.oid = PersistenceHelper.getObjectIdentifier(folder).getStringValue();
        this.level = level;
        this.image = action;
        this.type = "folder";
        slevel = getLevel(level);
        if (folder instanceof Cabinet)
            type = "cabinet";
        domain = getDomain(folder);
    }

    public void toggle()
    {
        if (expand.equals(image))
            image = collapse;
        else if (collapse.equals(image))
            image = expand;
    }

    private String getLevel(int l)
    {
        return l== 0 ? "캐비넷" : "폴더";
    }

    private String getDomain(Folder folder)
    {
        try
        {
            AdminDomainRef ref = DomainAdministeredHelper.getAdminDomainRef((DomainAdministered) folder);
            return AdministrativeDomainHelper.manager.getDomainPath(ref);
        }
        catch (Exception e)
        {
            return "";
        }
    }
}
