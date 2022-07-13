/**
 * @(#) FileUtil.java Copyright (c) jerred. All rights reserverd
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2004. 3. 22.
 * @author Cho Sung Ok, jerred@bcline.com
 * @desc
 */

package e3ps.common.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Vector;

//import e3ps.common.content.uploader.WBFile;

/**
 * 
 */
public class FileUtil {
	public static void checkDir(String dir) {
		File createDir = new File ( dir );
		if (!createDir.exists ()) createDir.mkdir ();
		if (!createDir.exists ()) createDir.mkdirs ();
	}

	public static void checkDir(File dir) {
		if (!dir.exists ()) dir.mkdir ();
		if (!dir.exists ()) dir.mkdirs ();
	}

	public static String getFileSizeStr(long filesize) {
		DecimalFormat df = new DecimalFormat ( ".#" );
		String fSize = "";
		if ( ( filesize > 1024 ) && ( filesize < 1024 * 1024 )) {
			fSize = df.format ( (float) filesize / 1024 ).toString () + " KB";
		} else if (filesize >= 1024 * 1024) {
			fSize = df.format ( (float) filesize / ( 1024 * 1024 ) ).toString ()	+ " MB";
		} else if (filesize < 1024 && filesize > 1) {
			fSize = "1 KB";
		} else {
			fSize = "0 Bytes";
		}
		return fSize;
	}
	 public static String checkFileSize(Vector file)
	    {
	        int wbFile_Size = 0;
//	        for(int i = 0; i < file.size(); i++)
//	        {
//	            if(((WBFile)file.get(i)).getSize() > 20000000)
//	            {
//	                return "첨부된 파일 사이즈가 20MB를 넘었습니다. 첨부 파일에 사이즈를 확인해 주십시요.";
//	            }
//	            else
//	            {
//	                wbFile_Size += ((WBFile)file.get(i)).getSize();
//	            	if(wbFile_Size > 35000000)
//	            	{
//	                	return "첨부된 파일에 총 사이즈가 35MB를 넘었습니다. 첨부 파일에 사이즈를 확인해 주십시요.";
//	            	}
//	            }
//	        }
	        return null;
	    }
}
