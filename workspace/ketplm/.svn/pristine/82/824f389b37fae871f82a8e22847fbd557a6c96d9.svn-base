/*
 *	@(#) WebEditerStateData.java
 *	Copyright (c) jerred. All rights reserverd
 * 
 *	@version 1.00
 *	@since jdk 1.4.2
 *	@createdate 2004. 3. 5.
 *	@author Cho Sung Ok, jerred@bcline.com
 *	@desc	
 */
 
package e3ps.common.content.multipart;

import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
/**
 * 
 */
public class UploadStateData {
	private static final Config config = ConfigImpl.getInstance();
		
	public static final int UPLOAD_FILE_MAXSIZE = config.getInt("upload.file.maxsize") * 1024 * 1024;
	
	public static final String UPLOAD_DEFAULT_URL = config.getString("upload.default.dir");		
	public static String UPLOAD_FILE_URL;			
	public static String UPLOAD_TEMP_URL;
	static {
		UPLOAD_FILE_URL = UPLOAD_DEFAULT_URL + "/" + config.getString("upload.file.dir");
		UPLOAD_TEMP_URL = UPLOAD_DEFAULT_URL + "/" + config.getString("upload.temp.dir");		 
	}
}
