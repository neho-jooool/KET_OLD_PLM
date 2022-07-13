package e3ps.bom.command.loadexcelbom;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import wt.util.WTException;
import e3ps.common.drm.E3PSDRMHelper;
import ext.ket.shared.log.Kogger;


public class CachFileUpload extends FileOnlyUpload {

    /**
	 *
	 */
    public static String      CASHFOLDERID     = "CASHFOLDERID";
    //public static Config conf = ConfigImpl.getInstance();
    public static String      TEMPDIR          = "D:\\ptc\\Windchill\\temp\\drm\\DownloadTemp";
    //conf.getString("process.temp.dir");
    //public static String TEMPDIR = "/usr/plm/Windchill/temp";


    private static final long serialVersionUID = 8561169755647787281L;

    public void excute(Hashtable hashtable, InputStream inputstream) throws WTException {
	String folderId = (String) hashtable.get("CASHFOLDERID");
	//Config conf = ConfigImpl.getInstance();

	byte b[] = new byte[4096];

	BufferedInputStream fin = new BufferedInputStream(inputstream);

	File dir = new File(TEMPDIR, folderId);
	if (!dir.exists()) {
	    dir.mkdir();
	}

	String fileName = (String) hashtable.get("FileName");

	File file = new File(dir, fileName);


	BufferedOutputStream outs = null;

	try {
	    outs = new BufferedOutputStream(new FileOutputStream(file));
	} catch (FileNotFoundException e1) {
	    throw new WTException(e1);
	}
	int read = 0;
	try {

	    while ((read = fin.read(b)) != -1) {
		//Kogger.debug(getClass(), read);
		outs.write(b, 0, read);
		outs.flush();
	    }
	    outs.close();

	    String drm = (String) hashtable.get("DRM");
	    if (drm != null) {
		//Kogger.debug(getClass(), "복호화 begin ...");

		//if(!file.exists()) {
		//	Kogger.debug(getClass(), "Encrypted File is null!!!");
		//} else {
		//	Kogger.debug(getClass(), "Encrypted File Length : " + file.length());
		//}

		File unPackFile = E3PSDRMHelper.upload(file, file.getName());

		//if( (unPackFile == null) || !unPackFile.exists()) {
		//	Kogger.debug(getClass(), "Decrypted File is null!!!");
		//} else {
		//	Kogger.debug(getClass(), "Decrypted File Length : " + unPackFile.length());
		//}

		copyFile(unPackFile, file);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    try {
		fin.close();

	    } catch (IOException e) {
		fin = null;
		Kogger.error(getClass(), e);
	    }
	} // end try

    }

    private void copyFile(File unPackFile, File packaFile) throws WTException {

	byte b[] = new byte[4096];

	int read = 0;

	BufferedInputStream fin = null;
	BufferedOutputStream outs = null;
	try {
	    fin = new BufferedInputStream(new FileInputStream(unPackFile));
	    outs = new BufferedOutputStream(new FileOutputStream(packaFile));
	    while ((read = fin.read(b)) != -1) {
		//Kogger.debug(getClass(), read);
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
		fin = null;
		outs = null;
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	} // end try

    }
}
