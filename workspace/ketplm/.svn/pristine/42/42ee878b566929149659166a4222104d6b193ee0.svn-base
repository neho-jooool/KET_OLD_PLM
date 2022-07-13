package e3ps.edm.util;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Vector;

import wt.epm.EPMDocument;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.Cabinet;
import wt.folder.CabinetMember;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderException;
import wt.folder.FolderServerHelper;
import wt.folder.Foldered;
import wt.pom.Transaction;
import wt.util.WTException;
import wt.vc.Iterated;
import e3ps.common.folder.FolderUtil;
import ext.ket.shared.log.Kogger;

public class EDMFolderUtil implements wt.method.RemoteAccess, java.io.Serializable {
	
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
	
	public static void changeFolders(Iterated iterated, Folder folder) throws FolderException, WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Iterated.class, Folder.class};
			Object args[] = new Object[]{iterated, folder};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"changeFolders",
						EDMFolderUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(EDMFolderUtil.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(EDMFolderUtil.class, e);
				throw new WTException(e);
			}
			return;
		}
		
		Transaction trx = new Transaction();
		try {
			trx.start();
			
			changeFolders(iterated, folder, true, true);
			
			trx.commit();
			trx = null;
			
		}
		catch(Exception e) {
			trx.rollback();
			Kogger.error(EDMFolderUtil.class, e);
			throw new WTException(e);
		}
		finally {
			if(trx != null) {
				trx = null;
			}
		}
	}
	
	
	public static void changeFolders(Iterated iterated, Folder folder, boolean bool, boolean bool1) throws FolderException, WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Iterated.class, Folder.class, boolean.class, boolean.class};
			Object args[] = new Object[]{iterated, folder, Boolean.valueOf(bool), Boolean.valueOf(bool1)};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"changeFolders",
						EDMFolderUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(EDMFolderUtil.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(EDMFolderUtil.class, e);
				throw new WTException(e);
			}
			return;
		}
		try {
			Vector v = new Vector();
			
			QueryResult result = null;
			if(bool) {
				result = wt.vc.VersionControlHelper.service.allIterationsOf(iterated.getMaster());
			} else {
				if(bool1) {
					result = wt.vc.VersionControlHelper.service.iterationsOf(iterated);
				} else {
					v.add(iterated);
				}
			}
			
			if(result != null) {
				while(result.hasMoreElements()) {
					//Iterated itr0 = (Iterated)result.nextElement();
					v.add((Iterated)result.nextElement());
				}
			}
			
			setFolderChange(v, folder);
			
		}
		catch(Exception e) {
			Kogger.error(EDMFolderUtil.class, e);
			throw new WTException(e);
		}
	}
	
	public static void setFolderChange(Vector entries, Folder folder) throws FolderException, WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Vector.class, Folder.class};
			Object args[] = new Object[]{entries, folder};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"setFolderChange",
						EDMFolderUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(EDMFolderUtil.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(EDMFolderUtil.class, e);
				throw new WTException(e);
			}
			return;
		}
		try {
			if( (entries != null) && (entries.size() > 0) ) {
				for(int i = 0; i < entries.size(); i++) {
					setFolderChange((FolderEntry)entries.get(i), folder);
				}
			}
		}
		catch(Exception e) {
			Kogger.error(EDMFolderUtil.class, e);
			throw new WTException(e);
		}
	}
	
	
	private static void setFolderChange(FolderEntry fe, Folder folder) throws FolderException, WTException {
		try {			
			if (fe instanceof Foldered)
				wt.folder.FolderServerHelper.service.setFolderChange((Foldered)fe, folder);
			if (fe instanceof CabinetMember)
				FolderServerHelper.service.setCabinetChange((CabinetMember)fe, (Cabinet)folder);
			
			PersistenceServerHelper.manager.update(fe);
		}
		catch(Exception e) {
			Kogger.error(EDMFolderUtil.class, e);
			throw new WTException(e);
		}
	}
	
	
	public static String getFolderLocation(EPMDocument epm) throws Exception {
		return FolderUtil.getFolderLocation((FolderEntry)epm);
	}
	
	public static String getFolderLocation(FolderEntry entry) throws Exception {
		return FolderUtil.getFolderLocation(entry);
	}
	
	public static String getFolderLocation(Folder folder) throws Exception {
		return FolderUtil.getFolderLocation(folder);
	}
}
