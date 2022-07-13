/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.common.content;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.content.ContentServiceEvent;
import wt.content.FormatContentHolder;
import wt.content.StreamData;
import wt.content.Streamed;
import wt.content.URLData;
import wt.fc.LobLocator;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.WTObject;
import wt.org.WTPrincipalReference;
import wt.pom.Transaction;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.content.multipart.UploadFile;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
//import e3ps.load.remote.CachFile;
import ext.ket.shared.log.Kogger;

/**
 * 
 * <p>
 * Use the <code>newStandardE3PSContentService</code> static factory method(s), not the <code>StandardE3PSContentService</code> constructor, to construct instances of this class.
 * Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

public class StandardE3PSContentService extends StandardManager implements E3PSContentService, Serializable {

	private static final String RESOURCE = "e3ps.common.content.contentResource";
	private static final String CLASSNAME = StandardE3PSContentService.class.getName();

	/**
	 * Returns the conceptual (modeled) name for the class.
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @deprecated
	 * 
	 * @return String
	 **/
	public String getConceptualClassname() {

		return CLASSNAME;
	}

	/**
	 * @exception wt.services.ManagerException
	 **/
	@Override
	protected void performStartupProcess() throws ManagerException {

		super.performStartupProcess();

		// for WGM DRM //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		ContentDownloadEventListener listener = new ContentDownloadEventListener(this.getConceptualClassname());
		getManagerService().addEventListener(listener, ContentServiceEvent.generateEventKey(ContentServiceEvent.PRE_DOWNLOAD));
		// getManagerService().addEventListener( listener, ContentServiceEvent.generateEventKey(ContentServiceEvent.POST_DOWNLOAD) );
		getManagerService().addEventListener(listener, ContentServiceEvent.generateEventKey(ContentServiceEvent.POST_UPLOAD));
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}

	/**
	 * Default factory for the class.
	 * 
	 * @return StandardE3PSContentService
	 * @exception wt.util.WTException
	 **/
	public static StandardE3PSContentService newStandardE3PSContentService() throws WTException {

		StandardE3PSContentService instance = new StandardE3PSContentService();
		instance.initialize();
		return instance;
	}

	@Override
	public ContentHolder attach(ContentHolder holder, Vector files) throws WTException {
		if (files == null)
			return holder;
		Transaction trx = new Transaction();
		try {
			trx.start();
			for (int i = 0; i < files.size(); i++) {
				Object obj = files.elementAt(i);

				if (obj instanceof WBFile) {
					WBFile uploadedFile = (WBFile) obj;
					String fileName = uploadedFile.getName();
					// InputStream is = uploadedFile.getInputStream();

					// if( isDRM )
					// {
					File drmFile = E3PSDRMHelper.upload(uploadedFile.getFile(), uploadedFile.getFile().getName());
					uploadedFile.setFile(drmFile);
					// }

					InputStream is = new FileInputStream(uploadedFile.getFile());

					ApplicationData ad = ApplicationData.newApplicationData(holder);
					ad.setFileName(fileName);
					ad.setFileSize(uploadedFile.getSize());

					if ((holder instanceof FormatContentHolder) && uploadedFile.getFieldName().equals("PRIMARY")) {
						ad.setRole(ContentRoleType.PRIMARY);
					} else {
						ad.setRole(ContentRoleType.SECONDARY);
					}

					ad.setDescription("");

					ad.setCreatedBy(SessionHelper.manager.getPrincipalReference());
					ContentServerHelper.service.updateContent(holder, ad, is);
				} else if (obj instanceof UploadFile) {
					UploadFile uploadedFile = (UploadFile) obj;

					ContentRoleType ctype = "PRIMARY".equals(uploadedFile.getFieldName()) ? ContentRoleType.PRIMARY : ContentRoleType.SECONDARY;
					attach(holder, uploadedFile.getFile(), "", ctype);
				}
				// else if( obj instanceof CachFile )
				// {
				// CachFile cfile = (CachFile)obj;
				// ContentRoleType ctype = "PRIMARY".equals(cfile.getContentType()) ? ContentRoleType.PRIMARY : ContentRoleType.SECONDARY;
				// attach(holder, cfile, "", ctype);
				//
				// }
			}

			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			Kogger.error(getClass(), e);
		} finally {
			trx = null;
		}
		return holder;
	}

	@Override
	public ContentHolder attach(ContentHolder holder, Vector files, Vector descs) throws WTException {
		if (files == null || descs == null)
			return holder;

		Transaction trx = new Transaction();
		try {
			trx.start();
			WTPrincipalReference principalReference = SessionHelper.manager.getPrincipalReference();

			for (int i = 0; i < files.size(); i++) {
				Object obj = files.elementAt(i);

				if (obj instanceof WBFile) {
					WBFile uploadedFile = (WBFile) files.elementAt(i);
					// InputStream is = uploadedFile.getInputStream();
					String fileName = uploadedFile.getName();
					// if(isDRM){

					File drmFile = E3PSDRMHelper.upload(uploadedFile.getFile(), uploadedFile.getFile().getName());

					uploadedFile.setFile(drmFile);

					// }

					InputStream is = new FileInputStream(uploadedFile.getFile());

					ApplicationData ad = ApplicationData.newApplicationData(holder);
					ad.setFileName(fileName);
					ad.setFileSize(uploadedFile.getSize());
					if ((holder instanceof FormatContentHolder) && uploadedFile.getFieldName().equals("PRIMARY"))
						ad.setRole(ContentRoleType.PRIMARY);
					else
						ad.setRole(ContentRoleType.SECONDARY);
					ad.setDescription((String) descs.elementAt(i) == null ? "" : (String) descs.elementAt(i));

					ad.setCreatedBy(principalReference);
					ContentServerHelper.service.updateContent(holder, ad, is);
				} else if (obj instanceof UploadFile) {
					UploadFile uploadedFile = (UploadFile) obj;
					ContentRoleType ctype = "PRIMARY".equals(uploadedFile.getFieldName()) ? ContentRoleType.PRIMARY : ContentRoleType.SECONDARY;
					attach(holder, uploadedFile.getFile(), "", ctype);
				}
				// else if( obj instanceof CachFile )
				// {
				// CachFile cfile = (CachFile)obj;
				// ContentRoleType ctype = "PRIMARY".equals(cfile.getContentType()) ? ContentRoleType.PRIMARY : ContentRoleType.SECONDARY;
				// attach(holder, cfile, (String) descs.elementAt(i) == null ? "" : (String) descs.elementAt(i), ctype);
				// }
			}
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			Kogger.error(getClass(), e);
		} finally {
			trx = null;
		}
		return holder;
	}

	@Override
	public ContentHolder attach(ContentHolder holder, WBFile file, String desc) throws WTException {

		Transaction trx = new Transaction();
		try {
			trx.start();

			WTPrincipalReference principalReference = SessionHelper.manager.getPrincipalReference();

			// InputStream is = file.getInputStream();
			InputStream is = new FileInputStream(file.getFile());
			ApplicationData ad = ApplicationData.newApplicationData(holder);
			ad.setFileName(file.getName());
			ad.setFileSize(file.getSize());
			if ((holder instanceof FormatContentHolder) && file.getFieldName().equals("PRIMARY"))
				ad.setRole(ContentRoleType.PRIMARY);
			else
				ad.setRole(ContentRoleType.SECONDARY);
			ad.setDescription(desc == null ? "" : desc);

			ad.setCreatedBy(principalReference);
			ContentServerHelper.service.updateContent(holder, ad, is);

			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			Kogger.error(getClass(), e);
		} finally {
			trx = null;
		}
		return holder;
	}

	@Override
	public ContentHolder attach(ContentHolder holder, WBFile file, String desc, boolean isPrimary) throws WTException {

		Transaction trx = new Transaction();
		try {
			trx.start();

			WTPrincipalReference principalReference = SessionHelper.manager.getPrincipalReference();
			String fileName = file.getName();
			// if(isDRM){

			File drmFile = E3PSDRMHelper.upload(file.getFile(), fileName);
			file.setFile(drmFile);

			// }

			// InputStream is = file.getInputStream();
			InputStream is = new FileInputStream(file.getFile());
			ApplicationData applicationData = ApplicationData.newApplicationData(holder);
			applicationData.setFileName(fileName);
			applicationData.setFileSize(file.getSize());
			if (isPrimary)
				applicationData.setRole(ContentRoleType.PRIMARY);
			else
				applicationData.setRole(ContentRoleType.SECONDARY);
			applicationData.setDescription(desc == null ? "" : desc);
			applicationData.setCreatedBy(principalReference);
			ContentServerHelper.service.updateContent(holder, applicationData, is);

			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			Kogger.error(getClass(), e);
		} finally {
			trx = null;
		}
		return holder;
	}

	@Override
	public ContentHolder attach(ContentHolder holder, ApplicationData data) throws WTException {

		Transaction transaction = new Transaction();
		try {
			transaction.start();

			LobLocator loblocator = null;
			data = (ApplicationData) PersistenceHelper.manager.refresh(data);
			Streamed streamed = (Streamed) PersistenceHelper.manager.refresh(data.getStreamData().getObjectId());
			try {
				loblocator.setObjectIdentifier(((ObjectReference) streamed).getObjectId());
				((StreamData) streamed).setLobLoc(loblocator);
			} catch (Exception exception) {
			}
			InputStream is = streamed.retrieveStream();

			ApplicationData saveData = ApplicationData.newApplicationData(holder);
			saveData.setIntendedForHttpOp(true);
			saveData.setFileName(data.getFileName());
			saveData.setFileSize(data.getFileSize());
			saveData.setCreatedBy(data.getCreatedBy());
			saveData.setDescription(data.getDescription() == null ? "" : data.getDescription());
			saveData.setRole(ContentRoleType.SECONDARY);
			ContentServerHelper.service.updateContent(holder, saveData, is);

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			Kogger.error(getClass(), e);
		} finally {
			transaction = null;
		}
		return holder;
	}

	@Override
	public ContentHolder attach(ContentHolder holder, ApplicationData data, boolean isPrimary) throws WTException {

		Transaction transaction = new Transaction();
		try {
			transaction.start();

			LobLocator loblocator = null;
			data = (ApplicationData) PersistenceHelper.manager.refresh(data);
			Streamed streamed = (Streamed) PersistenceHelper.manager.refresh(data.getStreamData().getObjectId());
			try {
				loblocator.setObjectIdentifier(((ObjectReference) streamed).getObjectId());
				((StreamData) streamed).setLobLoc(loblocator);
			} catch (Exception exception) {
			}
			InputStream is = streamed.retrieveStream();

			ApplicationData saveData = ApplicationData.newApplicationData(holder);
			saveData.setIntendedForHttpOp(true);
			saveData.setFileName(data.getFileName());
			saveData.setFileSize(data.getFileSize());
			saveData.setCreatedBy(data.getCreatedBy());
			saveData.setDescription(data.getDescription() == null ? "" : data.getDescription());
			if (isPrimary)
				saveData.setRole(ContentRoleType.PRIMARY);
			else
				saveData.setRole(ContentRoleType.SECONDARY);
			ContentServerHelper.service.updateContent(holder, saveData, is);

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			Kogger.error(getClass(), e);
		} finally {
			transaction = null;
		}
		return holder;
	}

	@Override
	public ContentHolder attach(ContentHolder holder, File file, String desc, ContentRoleType contentRoleType) throws WTException {

		Transaction trx = new Transaction();
		FileInputStream in = null;
		try {
			trx.start();
			String fileName = file.getName();
			if(isDRM){

			File drmFile = E3PSDRMHelper.upload(file, file.getName());
			file = drmFile;
			}

			ApplicationData applicationdata = ApplicationData.newApplicationData(holder);
			applicationdata.setFileName(fileName);
			// applicationdata.setUploadedFromPath(file.getPath());
			applicationdata.setRole(contentRoleType);
			applicationdata.setCreatedBy(SessionHelper.manager.getPrincipalReference());
			applicationdata.setDescription(desc);

			in = new FileInputStream(file);
			if (holder instanceof FormatContentHolder)
				ContentServerHelper.service.updateContent((FormatContentHolder) holder, applicationdata, in);
			else
				ContentServerHelper.service.updateContent(holder, applicationdata, in);
			if (holder instanceof FormatContentHolder)
				holder = ContentServerHelper.service.updateHolderFormat((FormatContentHolder) holder);
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			Kogger.error(getClass(), e);
		} finally {
			trx = null;
			if (file != null && file.exists()) {
				if (e3ps.common.jdf.config.ConfigImpl.getInstance().getBoolean("auto.delete.file", true))
					file.delete();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Kogger.error(getClass(), e);
				}
			}
		}
		return holder;
	}

	@Override
	public ContentHolder attach(ContentHolder holder, WBFile file, String desc, ContentRoleType contentRoleType) throws WTException {
		try {
			attach(holder, file, desc, "PRIMARY".equals(contentRoleType.toString()));
			// attach(holder, file, desc, false);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return holder;
	}

	@Override
	public ContentHolder attachURL(ContentHolder holder, String url, String desc, ContentRoleType contentRoleType) throws WTException {

		Transaction trx = new Transaction();
		try {
			trx.start();
			URLData urldata = URLData.newURLData(holder);
			urldata.setUrlLocation(url);
			urldata.setRole(contentRoleType);
			urldata.setCreatedBy(SessionHelper.manager.getPrincipalReference());
			if (holder instanceof FormatContentHolder) {
				ContentServerHelper.service.updatePrimary((FormatContentHolder) holder, urldata);
			} else
				ContentServerHelper.service.updateContent(holder, urldata);
			if (holder instanceof FormatContentHolder)
				holder = ContentServerHelper.service.updateHolderFormat((FormatContentHolder) holder);
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			Kogger.error(getClass(), e);
		} finally {
			trx = null;
		}
		return holder;
	}

	@Override
	public ContentHolder replace(ContentHolder holder, WBFile file, String desc, ContentItem replaceItem) throws WTException {

		Transaction transaction = new Transaction();
		try {
			transaction.start();

			boolean isPrimary = false;
			if (replaceItem.getRole().equals(ContentRoleType.PRIMARY))
				isPrimary = true;

			delete(holder, replaceItem);
			attach(holder, file, desc, isPrimary);

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new WTException(e);
		} finally {
			transaction = null;
		}
		return holder;
	}

	@Override
	public ContentHolder replace(ContentHolder holder, File file, String desc, ContentItem replaceItem) throws WTException {

		Transaction trx = new Transaction();
		try {
			trx.start();
			ContentRoleType type = replaceItem.getRole();
			ContentServerHelper.service.deleteContent(holder, replaceItem);
			attach(holder, file, desc, type);
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			throw new WTException(e);
		} finally {
			trx = null;
		}
		return holder;
	}

	@Override
	public ContentHolder replace(ContentHolder holder, ApplicationData data, ContentItem replaceItem) throws WTException {

		Transaction transaction = new Transaction();
		try {
			transaction.start();

			boolean isPrimary = false;
			if (replaceItem.getRole().equals(ContentRoleType.PRIMARY))
				isPrimary = true;

			delete(holder, replaceItem);
			attach(holder, data, isPrimary);

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new WTException(e);
		} finally {
			transaction = null;
		}
		return holder;
	}

	@Override
	public ContentHolder replaceURL(ContentHolder holder, String url, String desc, ContentItem contentItem) throws WTException {

		Transaction trx = new Transaction();
		try {
			trx.start();
			ContentRoleType type = contentItem.getRole();
			ContentServerHelper.service.deleteContent(holder, contentItem);
			attachURL(holder, url, desc, type);
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			throw new WTException(e);
		} finally {
			trx = null;
		}
		return holder;
	}

	@Override
	public ContentHolder delete(ContentHolder holder, ContentItem deleteItem) throws WTException {

		Transaction transaction = new Transaction();
		try {
			transaction.start();

			if (holder == null || deleteItem == null)
				return holder;
			holder = ContentHelper.service.getContents(holder);
			ContentServerHelper.service.deleteContent(holder, deleteItem);

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new WTException(e);
		} finally {
			transaction = null;
		}
		return holder;
	}

	@Override
	public ContentHolder delete(ContentHolder holder) throws WTException {

		Transaction transaction = new Transaction();
		try {
			holder = ContentHelper.service.getContents(holder);
			Vector files = ContentHelper.getContentListAll(holder);
			if (files != null) {
				for (int i = 0; i < files.size(); i++) {
					holder = delete(holder, (ApplicationData) files.get(i));
				}
			}
			transaction.commit();
		} catch (WTException e) {
			transaction.rollback();
			Kogger.error(getClass(), e);
		} catch (PropertyVetoException e) {
			transaction.rollback();
			Kogger.error(getClass(), e);
		} finally {
			transaction = null;
		}
		return holder;
	}

	@Override
	public String getIconImgTag(WTObject obj) throws WTException {

		String returnData = "";
		try {
			returnData = wt.enterprise.BasicTemplateProcessor.getObjectIconImgTag(obj);
		} catch (Exception e) {
		}

		if (returnData != null)
			return returnData;
		else
			return "";
	}

	public static boolean isDRM = false;

	static {
		Config conf = ConfigImpl.getInstance();
		isDRM = conf.getBoolean("DRM");
	}

}
