package ext.ket.edm.stamping.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import org.apache.commons.io.FileUtils;

import ext.ket.shared.log.Kogger;

public class FileUtil {
    public static void checkDir(String dir) {
	File createDir = new File(dir);
	if (!createDir.exists())
	    createDir.mkdir();
	if (!createDir.exists())
	    createDir.mkdirs();
    }

    public static void checkDir(File dir) {
	if (!dir.exists())
	    dir.mkdir();
	if (!dir.exists())
	    dir.mkdirs();
    }

    public static String getFileSizeStr(long filesize) {
	DecimalFormat df = new DecimalFormat(".#");
	String fSize = "";
	if ((filesize > 1024) && (filesize < 1024 * 1024)) {
	    fSize = df.format((float) filesize / 1024).toString() + " KB";
	} else if (filesize >= 1024 * 1024) {
	    fSize = df.format((float) filesize / (1024 * 1024)).toString() + " MB";
	} else if (filesize < 1024 && filesize > 1) {
	    fSize = "1 KB";
	} else {
	    fSize = "0 Bytes";
	}
	return fSize;
    }

    public static int CopyServerToServer(String szOriginFileName, String szCopyFileName) {
	if (szOriginFileName == null || szCopyFileName == null || szCopyFileName.length() < 1) {
	    return -1;
	}
	char delim = '/';
	if (szCopyFileName.indexOf('\\') != -1) {
	    delim = '\\';
	}
	int idx = szCopyFileName.lastIndexOf(delim);
	if (idx == -1 || idx == 0) {
	    idx = szCopyFileName.length();
	}
	File CopyFullPath = new File(szCopyFileName.substring(0, idx));
	byte[] buf = new byte[1024];
	if (!CopyFullPath.exists()) {
	    if (!CopyFullPath.mkdirs())
		return -1;
	}
	try {
	    FileInputStream fin = new FileInputStream(szOriginFileName);
	    FileOutputStream fout = new FileOutputStream(szCopyFileName);

	    for (int iBytesThisTime = fin.read(buf); iBytesThisTime > 0; iBytesThisTime = fin.read(buf)) {
		fout.write(buf, 0, iBytesThisTime);
	    }
	    fin.close();
	    fout.close();
	} catch (Exception e) {
	    Kogger.debug(FileUtil.class, e);
	    return -1;
	}

	return 0;
    }

    public static void main(String[] args) throws Exception {
	File srcFile = new File("D:\\temp\\iadebug.log");
	File destFile = new File("D:\\tmp\\iadebug.log");
	FileUtils.copyFile(srcFile, destFile);
    }
}
