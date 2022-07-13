package e3ps.common.content;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.ContentServiceEvent;
import wt.content.Streamed;
import wt.epm.EPMDocument;
import wt.events.KeyedEvent;
import wt.fc.PersistenceHelper;
import wt.fv.FileFolder;
import wt.fv.FvItem;
import wt.fv.FvMount;
import wt.fv.FvVault;
import wt.fv.StandardFvService;
import wt.fv.configurator.FolderDesc;
import wt.fv.contentmover.FvFileStreamWriter;
import wt.fv.master.MasteredOnReplicaItem;
import wt.fv.replica.StandardReplicaService;
import wt.fv.uploadtocache.BackupedFile;
import wt.intersvrcom.StandardInterSvrComService;
import wt.services.ManagerServiceFactory;
import wt.services.ServiceEventListenerAdapter;
import wt.util.WTException;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import ext.ket.shared.log.Kogger;

public class ContentDownloadEventListener extends ServiceEventListenerAdapter {
    public static boolean isDRM = false;
    static final boolean VERBOSE = ConfigImpl.getInstance().getBoolean("develop.verbose", false);

    static {
	Config conf = ConfigImpl.getInstance();
	isDRM = conf.getBoolean("DRM");
    }

    public ContentDownloadEventListener(String manager_name) {
	super(manager_name);
    }

    public void notifyVetoableEvent(Object obj) throws Exception {
	KeyedEvent event = (KeyedEvent) obj;
	Object eventObj = ((KeyedEvent) obj).getEventTarget();

	if (event.getEventType().equals(ContentServiceEvent.POST_UPLOAD)) {
	    Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: notifyVetoableEvent() :: 1");

	    if (!isDRM) {
		if (VERBOSE) {
		    Kogger.debug(getClass(), "===> ContentDownloadEventListener : notifyVetoableEvent() : isDRM false!!!");
		}

		return;
	    }

	    ContentServiceEvent contentEvent = (ContentServiceEvent) obj;
	    ContentHolder holder = contentEvent.getContentHolder();
	    EPMDocument epmDoc = null;

	    if (holder instanceof EPMDocument) {
		Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: notifyVetoableEvent() :: 2");

		epmDoc = (EPMDocument) holder;
		// epmDoc.getAuthoringApplication().toString();

		// PLM에서 등록한 경우...
		if (epmDoc.getOwnerApplication().toString().equals("PLMSYSTEM")) {
		    if (VERBOSE) {
			Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: notifyVetoableEvent() : Web Form Creation, Not WGM!!!");
		    }

		    return;
		}
	    } else {
		Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: notifyVetoableEvent() :: 3");
		return;
	    }

	    String fileName = epmDoc.getCADName();
	    ApplicationData data = contentEvent.getApplicationData();
	    Streamed streamed = (Streamed) data.getStreamData().getObject();

	    // FileInputStream fileIn = new FileInputStream(file);
	    if (streamed instanceof FvItem) {
		Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: notifyVetoableEvent() :: 4");

		FvItem fvItem = (FvItem) streamed;
		FileFolder filefolder = fvItem.getFolder();
		FvMount fvmount = null;
		fvmount = StandardFvService.getLocalMount(filefolder);

		String folderPath = fvmount.getPath();
		File orgFile = new File(folderPath, fvItem.getName());

		// DRM 관련 작업 Service(복호화)
		File file = E3PSDRMHelper.upload(orgFile, fileName);

		if (file.length() == 0) {
		    throw new WTException("file size 0");
		}

		if (file.equals(orgFile)) {
		    return;
		}

		copyFile(file, orgFile);

		orgFile = new File(folderPath, fvItem.getName());

		if (orgFile.length() == 0) {
		    throw new WTException("file size 0");
		}

		// FvFileStreamWriter fvfilestreamwriter = new FvFileStreamWriter();
		// long l = fvfilestreamwriter.storeStream(fileIn, backupedfile, fvItem.getName());
		// Kogger.debug(getClass(), "l ===== > " + l);

		// test(localFvVault, fvItem.getName(), fileIn);
	    } else if (streamed instanceof MasteredOnReplicaItem) {
		Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: notifyVetoableEvent() :: 5");

		MasteredOnReplicaItem mItem = (MasteredOnReplicaItem) streamed;
		FileFolder filefolder = mItem.getFolder();

		FvMount fvmount = null;
		fvmount = StandardFvService.getLocalMount(filefolder);

		String folderPath = fvmount.getPath();
		File orgFile = new File(folderPath, mItem.getName());

		// DRM 관련 작업 Service(복호화)
		File file = E3PSDRMHelper.upload(orgFile, fileName);

		if (file.length() == 0) {
		    throw new WTException("file size 0");
		}

		if (file.equals(orgFile)) {
		    return;
		}

		copyFile(file, orgFile);
		orgFile = new File(folderPath, mItem.getName());

		if (orgFile.length() == 0) {
		    throw new WTException("file size 0");
		}
	    } else {
		Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: notifyVetoableEvent() :: 6");

		// 암호화/복호화 대상 파일여부(확장자 체크)
		if (E3PSDRMHelper.isDrmApprove(data.getFileName())) {
		    return;
		}

		InputStream in = streamed.retrieveStream();

		// DRM 관련 작업 Service(복호화)
		File file = E3PSDRMHelper.upload(in, fileName, data.getFileSize());

		if (file.length() == 0) {
		    throw new WTException("file size 0");
		}

		FileInputStream fileIn = new FileInputStream(file);

		streamed.storeStream(holder, data, fileIn);
	    }
	}
    }

    private void copyFile(File unPackFile, File packaFile) throws WTException {
	Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: copyFile() :: 1");
	Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: copyFile() :: UnPackFile Name :: " + unPackFile.getName());
	Kogger.debug(getClass(), "===========> ContentDownloadEventListener.java :: copyFile() :: PackFile Name :: " + packaFile.getName());

	byte b[] = new byte[4096];

	int read = 0;

	BufferedInputStream fin = null;
	BufferedOutputStream outs = null;

	try {
	    fin = new BufferedInputStream(new FileInputStream(unPackFile));
	    outs = new BufferedOutputStream(new FileOutputStream(packaFile));
	    while ((read = fin.read(b)) != -1) {
		outs.write(b, 0, read);
		outs.flush();
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    try {
		if (fin != null) {
		    fin.close();
		}

		if (outs != null) {
		    outs.close();
		}
	    } catch (IOException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	}
    }

    public void test(FvVault vault, String name, InputStream inputstream) throws WTException {
	// StandardReplicaService standardreplicaservice = new StandardReplicaService();
	StandardReplicaService standardreplicaservice = (StandardReplicaService) ManagerServiceFactory.getDefault().getManager(wt.fv.replica.StandardReplicaService.class);
	String str = StandardInterSvrComService.MASTER_URL;
	Long localLong = new Long(PersistenceHelper.getObjectIdentifier(vault).getId());

	Kogger.debug(getClass(), "masterUrl = " + str);

	FolderDesc folderdesc = standardreplicaservice.findFolderToSaveFile(str, localLong.longValue(), new Vector());

	String s1 = folderdesc.getPath();
	String s2 = name;

	Kogger.debug(getClass(), "gggg  = " + s1 + " " + s2);

	BackupedFile backupedfile = new BackupedFile(s1, s2);
	FvFileStreamWriter fvfilestreamwriter = new FvFileStreamWriter();
	long l = fvfilestreamwriter.storeStream(inputstream, backupedfile, s2);

	Kogger.debug(getClass(), "l ===== > " + l);

    }

    /*
     * private Object[] storeStreamInternal( PayloadInputStreamDesc paramPayloadInputStreamDesc, FvVault vault, FvItem fvItem ) throws WTException { Object[] arrayOfObject = null;
     * 
     * try { PayloadShippingItem localPayloadShippingItem = ShippingHelper.service.createPayloadShippingItem(); MethodInvocationDesc localMethodInvocationDesc =
     * MethodInvocationDesc.newMethodInvocationDesc();
     * 
     * localMethodInvocationDesc.setTargetClass("wt.fv.replica.StandardReplicaService"); localMethodInvocationDesc.setTargetMethod("storeStreamOnReplica");
     * 
     * Long localObject = new Long(fvItem.getUniqueSequenceNumber()); Long localLong = new Long(PersistenceHelper.getObjectIdentifier(vault).getId()); String str =
     * StandardInterSvrComService.MASTER_URL; String[] arrayOfString = { localObject.getClass().getName(), localLong.getClass().getName(), str.getClass().getName() }; Serializable[]
     * arrayOfSerializable = { localObject, localLong, str };
     * 
     * localMethodInvocationDesc.setArgTypes(arrayOfString); localMethodInvocationDesc.setArgs(arrayOfSerializable);
     * 
     * localPayloadShippingItem.setDesc(localMethodInvocationDesc); localPayloadShippingItem.setPayload(paramPayloadInputStreamDesc);
     * 
     * arrayOfObject = sendShippingItem(localPayloadShippingItem, vault);
     * 
     * } catch( WTPropertyVetoException localWTPropertyVetoException ) { Kogger.error(getClass(), localWTPropertyVetoException); throw new FvFileCanNotBeStored(localWTPropertyVetoException); }
     * 
     * long l1 = paramPayloadInputStreamDesc.getSize();
     * 
     * return arrayOfObject; }
     */

    /*
     * Object[] sendShippingItem( ActionShippingItem paramActionShippingItem, FvVault vault ) throws WTPropertyVetoException, WTException { Object[] arrayOfObject = null; SiteAddress localSiteAddress
     * = SiteAddress.newSiteAddress(); TransportType localTransportType = TransportType.newTransportType(); localSiteAddress.setAddress(vault.getSite().getUrl());
     * localTransportType.setTransportType(null);
     * 
     * try { InputStream localInputStream = ShippingHelper.service.sendImmediateItem(paramActionShippingItem, localSiteAddress, localTransportType, 1);
     * 
     * // ObjectInputStream localObjectInputStream1 = new ObjectInputStream(localInputStream); // localObjectInputStream1.readObject(); // ObjectInputStream localObjectInputStream2 = new
     * ObjectInputStream(localInputStream); // arrayOfObject = (Object[])(Object[])localObjectInputStream2.readObject(); } catch( Exception localException ) {  Kogger.error(getClass(), localException); throw
     * new FvFileCanNotBeStored(localException); }
     * 
     * return arrayOfObject; }
     */
}
