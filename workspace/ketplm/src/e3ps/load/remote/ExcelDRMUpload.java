/**
 *	@(#) ExcelDRMUpload.java
 *	Copyright (c) khkim. All rights reserverd
 * 
 *	@version 1.00
 *	@since jdk 1.5.02
 *	@createdate 2009. 06. 25.
 *	@author kim ki hong, khkim@e3ps.com
 *	@desc	
 */
package e3ps.load.remote;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import wt.util.WTException;
import e3ps.common.drm.E3PSDRMHelper;
import ext.ket.shared.log.Kogger;

public class ExcelDRMUpload extends FileUpload {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6222338063157311316L;

    public void excute(Hashtable hashtable, InputStream inputstream) throws WTException {
	try {
	    File file = File.createTempFile("load", ".xls", new File(E3PSDRMHelper.uploadFileTempFolder));

	    byte abyte0[] = new byte[8192];
	    FileOutputStream fout = new FileOutputStream(file);
	    int i = 0;
	    while ((i = inputstream.read(abyte0, 0, 8192)) > 0) {
		fout.write(abyte0, 0, i);
		fout.flush();
	    }
	    fout.close();
	    //Kogger.debug(getClass(), "drm............................입니다...");

	    File drmFile = E3PSDRMHelper.upload(file, file.getName());

	    Kogger.debug(getClass(), "drm path = " + drmFile.getAbsoluteFile());
	    returnObject = drmFile.getAbsolutePath();

	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	}

    }

}
