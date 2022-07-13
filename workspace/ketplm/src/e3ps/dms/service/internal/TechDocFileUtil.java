package e3ps.dms.service.internal;

import java.beans.PropertyVetoException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentServerHelper;
import wt.method.RemoteAccess;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.dms.props.PropertyHandler;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class TechDocFileUtil implements RemoteAccess {
	public File[] getFile(ContentHolder holder, String filePath, String ip, String userOid) {
		/*
		 * if (!RemoteMethodServer.ServerFlag) { try { RemoteMethodServer rms = RemoteMethodServer.getDefault(); Class[] argTypes = { ContentHolder.class }; Object[] argValues = {
		 * holder }; return (File[]) rms.invoke("getFile", "e3ps.common.content.ApplicationDataFileUtil", null, argTypes, argValues); } catch (RemoteException e) {
		 * Kogger.error(TechDocFileUtil.class, e); } catch (InvocationTargetException e) { Kogger.error(TechDocFileUtil.class, e); } }
		 */
		String tempDir = "";
		try {
			tempDir = PropertyHandler.getInstance().getString("folder") + "\\" + filePath;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			holder = ContentHelper.service.getContents(holder);
			Vector list = ContentHelper.getContentListAll(holder);
			if (list == null && list.size() == 0)
				return null;

			File[] files = new File[list.size()];
			byte[] buffer = new byte[1024];

			File dir = new File(tempDir);
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}
			String oid = "";
			String[] filename = new String[list.size()];
			SessionContext sessioncontext = SessionContext.newContext();
			try {
				SessionHelper.manager.setAdministrator();
				for (int i = 0; i < list.size(); i++) {
					ContentItem temp = (ContentItem) list.get(i);
					if (temp instanceof ApplicationData) {
						ApplicationData adata = (ApplicationData) temp;

						InputStream is = ContentServerHelper.service.findContentStream(adata);
						filename[i] = adata.getFileName();
						files[i] = new File(tempDir + File.separator + adata.getFileName());
						oid = CommonUtil.getOIDString(adata);
						oid = oid.substring(oid.lastIndexOf(":") + 1);
						files[i].setWritable(true);
						FileOutputStream fos = new FileOutputStream(files[i]);
						int j = 0;
						while ((j = is.read(buffer, 0, 1024)) > 0)
							fos.write(buffer, 0, j);

						fos.close();
						is.close();
					}
				}
			} catch (Exception e) {
				Kogger.error(TechDocFileUtil.class, e);
			} finally {
				SessionContext.setContext(sessioncontext);
			}
			if (DRMHelper.useDrm) {
				for (int i = 0; i < files.length; i++) {
					drmRun(oid, files[i], userOid, ip, "DesignDoc", filename[i], tempDir);
				}
			} else {
				for (int i = 0; i < files.length; i++) {
					files[i].setReadOnly();
				}
			}
			return files;
		} catch (WTException e) {
			Kogger.error(TechDocFileUtil.class, e);
		} catch (PropertyVetoException e) {
			Kogger.error(TechDocFileUtil.class, e);
		}

		return null;
	}

	public void drmRun(String oid, File file, String people, String ip, String option, String filename, String tempDir) {
		System.out.println("::::::: 설계가이드 drm run   " + filename);
		File org_file = null;
		File tar_file = null;
		try {
			org_file = DRMHelper.encryptFile(oid, file, people, ip, option);
			tar_file = new File(tempDir + File.separator + filename);
			copyFile(org_file, tar_file);
			file.setReadOnly();
		} catch (WTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 파일 복사
	 * </pre>
	 * 
	 * @param orgFile
	 *            File
	 * @param tarFile
	 *            File
	 * @return void
	 * @throws WTException
	 */
	public void copyFile(File orgFile, File tarFile) throws WTException {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(new FileInputStream(orgFile));
			bos = new BufferedOutputStream(new FileOutputStream(tarFile));

			int len = 0;
			byte[] b = new byte[2048];

			while ((len = bis.read(b)) > 0) {
				bos.write(b, 0, len);
				bos.flush();
			}
		} catch (Exception e) {
			throw new WTException(e.toString());
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
				}
			}

			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
