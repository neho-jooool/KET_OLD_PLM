/*
 * @(#) FileSizeCheck.java Create on 2005. 6. 15.
 * Copyright (c) e3ps. All rights reserved
 * 
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2005. 6. 15.
 * @author Choi Kang Hun, khchoi@e3ps.com
 */
package e3ps.common.util;

import java.util.Vector;

//import e3ps.common.content.uploader.WBFile;

/**
 * 
 */
public class FileSizeCheck {
    public static String checkFileSize(Vector file)
    {
        int wbFile_Size = 0;
//        for(int i = 0; i < file.size(); i++)
//        {
//            if(((WBFile)file.get(i)).getSize() > 20000000)
//            {
//                return "첨부된 파일 사이즈가 20MB를 넘었습니다. 첨부 파일에 사이즈를 확인해 주십시요.";
//            }
//            else
//            {
//                wbFile_Size += ((WBFile)file.get(i)).getSize();
//            	if(wbFile_Size > 35000000)
//            	{
//                	return "첨부된 파일에 총 사이즈가 35MB를 넘었습니다. 첨부 파일에 사이즈를 확인해 주십시요.";
//            	}
//            }
//        }
        return null;
    }
}
