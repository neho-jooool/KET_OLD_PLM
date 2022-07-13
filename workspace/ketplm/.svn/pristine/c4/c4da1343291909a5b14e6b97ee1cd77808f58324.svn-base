package e3ps.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import wt.method.RemoteAccess;
import wt.util.WTException;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import e3ps.common.jdf.config.ConfigImpl;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : ProjectScheduleFileUploader
 * 설명     : Project Schedule Mpp File Upload
 * 작성자   : 김종호
 * 작성일자 : 2013. 7. 23.
 */
public class ProjectScheduleFileUploader implements java.io.Serializable, RemoteAccess {

	private static final long serialVersionUID = -7277643209320587027L;

	private static final String UPLOAD_BASE_PATH = ConfigImpl.getInstance().getString(  "msproject.base.path" );
	private static final String UPLOAD_SIZE_LIMIT = ConfigImpl.getInstance().getString(  "msproject.size.limit" );

	private static String uploadFolderPath = "";

	static 	{

		initUpload();
	}

	/**
	 * Constructor<br>
	 *
	 * @param
	 * @return
	 * @throws
	 **/
	public static void initUpload() {

		try {

	        File uploadFolder = new File(UPLOAD_BASE_PATH);
            if ( !uploadFolder.isDirectory() ) {
                uploadFolder.mkdir();
            }
            uploadFolder = new File(UPLOAD_BASE_PATH + File.separator + "UploadTemp");
            if ( !uploadFolder.isDirectory() ) {
                uploadFolder.mkdir();
            }

			uploadFolderPath = uploadFolder.getAbsolutePath();
		}
		catch( Exception e )
		{
			Kogger.error(ProjectScheduleFileUploader.class, e);
		}
	}

	/**
	 * Mulipart Form File Upload<br>
	 *
	 * @param HttpServletRequest
	 * @return String
	 * @throws WTException
	 **/
	public static String upload( HttpServletRequest req ) throws WTException {

		String uploadedFileFullPath = "";
		MultipartRequest mReq = null;
		DefaultFileRenamePolicy dfrp = new DefaultFileRenamePolicy();

		try
		{
			mReq = new MultipartRequest(req, uploadFolderPath, Integer.parseInt(UPLOAD_SIZE_LIMIT), "UTF-8", dfrp);
			@SuppressWarnings("rawtypes")
			Enumeration fileEnum = mReq.getFileNames();
			uploadedFileFullPath = uploadFolderPath + File.separator + mReq.getFilesystemName((String)fileEnum.nextElement());
		}
		catch( Exception e )
		{
			Kogger.error(ProjectScheduleFileUploader.class, e);
			throw new WTException(e);
		}

		return uploadedFileFullPath;
	}

	/**
	 * General Form File Upload<br>
	 *
	 * @param File
	 * @param String
	 * @return String
	 * @throws WTException
	 **/
	public static String upload( File file, String fileName ) throws WTException
	{
		String uploadedFileFullPath = "";
		InputStream in = null;
		long fileLength = 0;
		File fileOut = null;

		try
		{
			in = new FileInputStream( file );
			fileLength = file.length();
			fileOut = upload( in, fileName, fileLength );

			uploadedFileFullPath = uploadFolderPath + File.separator + fileOut.getName();
		}
		catch( Exception e )
		{
			Kogger.error(ProjectScheduleFileUploader.class, e);
			throw new WTException(e);
		}
		finally
		{
			try
			{
				if( in != null )
				{
					in.close();
				}
			}
			catch( Exception e )
			{
				Kogger.error(ProjectScheduleFileUploader.class, e);
			}
		 }

		return uploadedFileFullPath;
	}

	/**
	 * General Form File Upload<br>
	 *
	 * @param InputStream
	 * @param String
	 * @param long
	 * @return File
	 * @throws
	 **/
	public static File upload( InputStream inputStream, String fileName, long fileLength )
	{
		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		File fileOut = null;

		try
		{
			in = new BufferedInputStream( inputStream );
			fileOut = new File( uploadFolderPath + File.separator + getUploadFileName(fileName) );

		    out = new BufferedOutputStream( new FileOutputStream(fileOut) );
	        int read = 0;
	        byte b[] = new byte[4096];

            while( (read = in.read(b)) > 0 )
            {
            	out.write( b, 0, read );
            	out.flush();
            }

			if( out != null )
			{
				out.close();
			}
		}
		catch( Exception e )
		{
			Kogger.error(ProjectScheduleFileUploader.class, e);
		}
		finally
		{
			try
			{
				if( in != null )
				{
					in.close();
				}

				if( out != null )
				{
					out.close();
				}
			}
			catch( Exception e )
			{
				Kogger.error(ProjectScheduleFileUploader.class, e);
			}
		}

		return fileOut;
	}

	/**
	 * Get File Extension String<br>
	 *
	 * @param String
	 * @return String
	 * @throws
	 **/
    public static String getFileExtension( String fileName )
    {
    	String extStr = "";

        int idx = -1;
		idx = fileName.lastIndexOf('.');

		if( idx < 0 )
		{
			Kogger.debug(ProjectScheduleFileUploader.class,  "###Error : Filename was wrong.###" );
		}
		else
		{
			extStr = fileName.substring(idx+1);
		}

		return extStr;
    }

	/**
	 * Get File Name String Without Extension<br>
	 *
	 * @param String
	 * @return String
	 * @throws
	 **/
    public static String getFileNameWithoutExt( String fileName )
    {
		String returnStr = new String();
        int idx = -1;
		idx = fileName.lastIndexOf('.');

		if( idx < 0 )
		{
			Kogger.debug(ProjectScheduleFileUploader.class,  "###Error : Filename was wrong.###" );
		}
		else
		{
			returnStr = fileName.substring(0, idx);
		}

		return returnStr;
    }

	/**
	 * Get Uploaded File Name String With Extension<br>
	 *
	 * @param String
	 * @return String
	 * @throws
	 **/
    public static String getUploadFileName( String fileName )
    {
		Random randomNumber = new Random();
		StringBuffer stringbuffer = new StringBuffer();
		//stringbuffer.append( getFileNameWithoutExt(fileName) ).append( "_" );
		stringbuffer.append( Math.abs(randomNumber.nextInt()) ).append( "." ).append( getFileExtension(fileName) );

		return stringbuffer.toString();
    }
}
