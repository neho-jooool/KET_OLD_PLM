package e3ps.common.folder;

import java.util.Vector;

import wt.access.AccessPermission;
import wt.admin.AdminDomainRef;
import wt.admin.AdministrativeDomainHelper;
import wt.clients.folder.FolderTaskLogic;
import wt.fc.IdentityCollationKeyFactory;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.Cabinet;
import wt.folder.CabinetBased;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.FolderNotFoundException;
import wt.folder.Foldered;
import wt.folder.SubFolder;
import wt.inf.container.ExchangeContainer;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.query.QueryException;
import wt.session.SessionHelper;
import wt.util.CollationKeyFactory;
import wt.util.SortedEnumeration;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;
import e3ps.common.util.WCUtil;
import ext.ket.shared.log.Kogger;

public class FolderUtil {
    /**
     * name 이란 이름으로 Cabinet을 생성한다.
     * 
     * @param name
     * @return name이란 이름의 Cabinet 반환
     */
    public static Cabinet createCabinet(String name) {
	Cabinet cabinet = null;
	try {
	    cabinet = FolderHelper.service.createCabinet(name, getRootDomain(), "", WTContainerHelper.getClassicRef());
	} catch (WTException e) {
	    Kogger.error(FolderUtil.class, e);
	}
	return cabinet;
    }

    public static Cabinet createCabinet(String name, AdminDomainRef ref) {
	Cabinet cabinet = null;
	try {
	    cabinet = FolderHelper.service.createCabinet(name, ref, "", WTContainerHelper.getClassicRef());
	} catch (WTException e) {
	    Kogger.error(FolderUtil.class, e);
	}
	return cabinet;
    }

    public static AdminDomainRef getRootDomain() {
	AdminDomainRef rootDomain = null;
	try {
	    ExchangeContainer container = WTContainerHelper.service.getExchangeContainer();
	    rootDomain = container.getDomainRef();
	} catch (WTException e) {
	    Kogger.error(FolderUtil.class, e);
	}

	return rootDomain;
    }

    /**
     * SubFolder를 생성한다
     * 
     * @param parentFolder
     *            부모폴더
     * @param name
     *            생성할 폴더의 이름
     * @throws Exception
     */
    public static Folder createFolder(Folder parentFolder, String name) throws Exception {
	SubFolder subfolder = SubFolder.newSubFolder(name);
	//        subfolder.setInheritedDomain(false);
	//        DomainAdministeredHelper.setAdminDomain(subfolder, getRootDomain());
	FolderHelper.assignLocation((FolderEntry) subfolder, parentFolder);
	return (Folder) PersistenceHelper.manager.save(subfolder);
    }

    // here
    public static Folder getFolder(String folderPath) {
	Folder folder = null;
	if (!availableFolder(folderPath)) {
	    folder = createFolder(folderPath);
	}
	else {
	    try {
		folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
	    } catch (Exception e) {
		Kogger.error(FolderUtil.class, e);
	    }
	}
	return folder;
    }

    public static Folder createFolder(String s) {
	Folder folder = null;
	//folder = FolderHelper.service.createSubFolder(s);
	try {
	    folder = FolderHelper.service.createSubFolder(s, WCUtil.getWTContainerRef());
	} catch (Exception e) {
	    Kogger.error(FolderUtil.class, e);
	}
	return folder;
    }

    public static boolean availableFolder(String s) {
	boolean exist = false;
	Folder folder = null;
	try {
	    //folder = FolderHelper.service.getFolder(s);
	    folder = FolderHelper.service.getFolder(s, WCUtil.getWTContainerRef());
	    if (folder != null) exist = true;
	    else return false;
	} catch (Exception e) {
	}
	return exist;
    }

    /**
     * 개인 캐비넷을 가져온다.
     * 
     * @return Personal Cabinet
     * @throws Exception
     */
    public static Cabinet getPersonalCabinet() throws WTException {
	return FolderTaskLogic.getPersonalCabinet(SessionHelper.manager.getPrincipal());
    }

    /**
     * 개인의 체크아웃 폴더를 가져온다.
     * 
     * @return Personal CheckOut Folder
     * @throws Exception
     */
    public static Folder getMyCheckoutFolder() throws Exception {
	Cabinet cabinet = FolderTaskLogic.getPersonalCabinet(SessionHelper.manager.getPrincipal());
	String checkoutFolder = cabinet.getFolderPath() + "/Checked Out";

	Folder folder = null;
	try {
	    folder = FolderHelper.service.getFolder(checkoutFolder);
	} catch (FolderNotFoundException e) {
	    FolderHelper.service.createSubFolder(checkoutFolder);
	    folder = FolderHelper.service.getFolder(checkoutFolder, WCUtil.getWTContainerRef());
	}

	return folder;
    }

    public static SortedEnumeration getSubFolders(Folder folder) throws WTException {
	return getSubFolders(folder, new IdentityCollationKeyFactory());
    }

    public static SortedEnumeration getSubFolders(Folder folder, CollationKeyFactory collationkeyfactory) throws WTException {
	SortedEnumeration sortedenumeration = null;
	QueryResult queryresult = FolderHelper.service.findSubFolders(folder);
	if (queryresult != null) sortedenumeration = new SortedEnumeration(queryresult.getEnumeration(), collationkeyfactory);
	return sortedenumeration;
    }

    public static Folder getSelectFolder(String name) throws QueryException, WTException {
	Folder folder = null;
	try {
	    folder = FolderTaskLogic.getFolder(name, WCUtil.getWTContainerRef());
	} catch (Exception e) {
	    Kogger.error(FolderUtil.class, e);
	}
	return folder;
    }

    public static FolderEntry moveSharedObject(Folder folder, FolderEntry folderEntry) throws WTException {
	if (FolderHelper.inPersonalCabinet((CabinetBased) folderEntry)) {
	    FolderEntry entry = (FolderEntry) FolderHelper.service.changeFolder(folderEntry, folder);
	    return entry;
	}
	else {
	    return null;
	}
    }

    public static FolderEntry moveSharedObject(String folderPath, FolderEntry folderEntry) throws WTException {
	if (FolderHelper.inPersonalCabinet((CabinetBased) folderEntry)) {
	    String folderName = folderPath.trim();

	    Folder folder = null;
	    try {
		folder = FolderHelper.service.getFolder(folderName);
	    } catch (FolderNotFoundException e) {
		FolderHelper.service.createSubFolder(folderName);
		folder = FolderHelper.service.getFolder(folderName);
	    }
	    FolderEntry entry = (FolderEntry) FolderHelper.service.changeFolder(folderEntry, folder);
	    return entry;
	}
	else {
	    return null;
	}
    }

    /**
     * 모든 캐비넷을 가져온다.
     * 
     * @param isIncludePersonal
     *            true:개인캐비넷포함 false:공용캐비넷만
     * @return
     * @throws QueryException
     * @throws WTException
     */
    public static SortedEnumeration getAllCabinets(boolean isIncludePersonal) throws QueryException, WTException {
	SortedEnumeration sortedenumeration = null;
	try {
	    QueryResult queryresult;
	    queryresult = FolderHelper.service.findCabinets(AccessPermission.READ, !isIncludePersonal, WCUtil.getWTContainerRef());
	    if (queryresult != null) sortedenumeration = new SortedEnumeration(queryresult.getEnumeration(), new IdentityCollationKeyFactory());
	} catch (Exception e) {
	    Kogger.error(FolderUtil.class, e);
	}
	return sortedenumeration;
    }

    /*
     * 하위 폴더 구조 리스트.... 구조로 가져오지 않음.
     */
    public static Vector getSubFolderList(Folder folder) throws WTException {
	Vector subs = new Vector();
	try {
	    getSubFolderList(folder, subs);
	} catch (Exception e) {
	    Kogger.error(FolderUtil.class, e);
	}
	return subs;
    }

    public static void getSubFolderList(Folder folder, Vector vec) {
	try {
	    SortedEnumeration en = getSubFolders(folder);
	    while (en.hasMoreElements()) {
		SubFolder sub = (SubFolder) en.nextElement();
		if (vec == null) vec = new Vector();

		vec.add(sub);

		getSubFolderList(sub, vec);
	    }
	} catch (Exception e) {
	    Kogger.error(FolderUtil.class, e);
	}
    }

    public static String getFolderLocation(FolderEntry entry) throws Exception {
	if (entry instanceof WTContained) {
	    WTContainerRef wcf = WTContainerHelper.getContainerRef((WTContained) entry);
	    return FolderUtil.getFolderLocation(wcf, entry);
	}
	return entry.getLocation();
    }

    public static String getFolderLocation(Folder folder) throws Exception {
	WTContainerRef wcf = WTContainerHelper.getContainerRef(folder);
	return FolderUtil.getFolderLocation(wcf, folder);
    }

    public static String getFolderLocation(WTContainerRef wtContainerRef, Persistable per) throws Exception {
	return getFolderLocation(wtContainerRef.getName(), per);
    }

    public static String getFolderLocation(String paramString, Persistable per) throws Exception {
	if (per instanceof Workable) {
	    if (WorkInProgressHelper.isWorkingCopy((Workable) per)) {
		per = WorkInProgressHelper.service.originalCopyOf((Workable) per);
	    }
	}
	String location = getLocationOfObject(per);
	String str = replaceDefaultCabinetName(location, paramString);
	return str;//((String)HtmlUtil.escapeFormattedHTMLContent(str));
    }

    public static String getLocationOfObject(Persistable per) throws WTException {

	String str1 = "";
	if (per instanceof Folder) {
	    return ((Folder) per).getFolderPath();
	}
	else if (per instanceof Foldered) {
	    return ((Foldered) per).getLocation();
	}
	return "";
    }

    public static String replaceDefaultCabinetName(String location, String paramString2) {
	int i = location.lastIndexOf(AdministrativeDomainHelper.DEFAULT_DOMAIN);
	int j = AdministrativeDomainHelper.DEFAULT_DOMAIN.length();
	if ((i >= 0) && (j <= location.length())) {
	    StringBuffer localStringBuffer = new StringBuffer(location);
	    localStringBuffer.replace(i, i + j, paramString2);
	    return localStringBuffer.toString();
	}
	return location;
    }
}
