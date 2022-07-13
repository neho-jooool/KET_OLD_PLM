package e3ps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.DBProperties;
import wt.session.SessionContext;
import ext.ket.shared.log.Kogger;

public class FileUploadcheck implements RemoteAccess, Serializable {
	public static FileUploadcheck manager = new FileUploadcheck();

	public FileUploadcheck() {

	}

	private static final long serialVersionUID = 1L;
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

	public static void main(String[] args) {
		try {
			FileUploadcheck.manager.fileUpload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fileUpload() throws Exception {
		if (!SERVER) {
			try {

				Class aclass[] = { String.class };
				Object aobj[] = {};
				Kogger.debug(getClass(), "@		start");
				RemoteMethodServer.getDefault().invoke("fileUpload", null, this, null, null);
				Kogger.debug(getClass(), "@		end");
				return;

			} catch (RemoteException e) {
				Kogger.debug(getClass(), e);
			} catch (InvocationTargetException e) {
				Kogger.debug(getClass(), e);
			} catch (Exception e) {
				Kogger.debug(getClass(), e);
			}
		} else {
			executeTest();
		}
	}

	public void executeTest() throws Exception {
		SessionContext sessioncontext = SessionContext.newContext();
		try {
			String filepath = "E:\\ptc\\filevaults\\ket\\ketrootfolder_Folder_243\\0000000014FD2B";
			FileInputStream fin = new FileInputStream(filepath);

			File outputFile = new File("E:\\MG675435-41_025060110250_58M_SPC.dwg");

			FileOutputStream fo = new FileOutputStream(outputFile);

			try {

				int j = 0;
				byte abyte0[] = new byte[(int) DBProperties.LOB_CHUNK_SIZE];
				while ((j = fin.read(abyte0, 0, abyte0.length)) >= 0) {
					fo.write(abyte0, 0, j);
				}

				fo.flush();
				fo.close();
				fin.close();

			} catch (IOException e) {
				Kogger.error(getClass(), e);
				throw e;
			}

			/*
			 * KETTechnicalDocument dms = (KETTechnicalDocument) CommonUtil.getObject("e3ps.dms.entity.KETTechnicalDocument:992585721"); System.out.println("dms ====> " +
			 * dms.toString()); ContentHolder contentHolder = ContentHelper.service.getContents(dms); ApplicationDataFileUtil.getFile(contentHolder);
			 */
		} catch (Exception e) {

		} finally {
			SessionContext.setContext(sessioncontext);
		}
	}
}
