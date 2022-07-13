/**
 * @(#)	Multiparser.java
 * Copyright (c) jerred. All rights reserverd
 * 
 *	@version 1.00
 *	@since jdk 1.4.02
 *	@createdate 2004. 3. 3.
 *	@author Cho Sung Ok, jerred@bcline.com
 *	@desc	
 */

package e3ps.common.content.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.common.content.uploader.CannotReadFileException;
import e3ps.common.content.uploader.DataHeader;
import e3ps.common.content.uploader.NoFileContentException;
import e3ps.common.content.uploader.WBFile;
import ext.ket.shared.log.Kogger;

public class Multiparser {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext context;
    private Vector files;
    private Hashtable formParameters;

    protected Multiparser() {
        request = null;
        response = null;
        context = null;
        request = null;
        response = null;
        context = null; 
        files = null;
        formParameters = new Hashtable();
    }

    protected Multiparser(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        this();
        this.context = context;
        this.request = request;
        this.response = response;
    }	

    public Vector getFiles() { return files; }
	public void setFiles(Vector aFiles) { files = aFiles; }

    public String getParameter(String name) {
		Object o = formParameters.get(name);
		if(o instanceof String) return (String)o;
		return null;
    }

	public String[] getParameterValues(String key){ 
		try {
            Vector vector = (Vector)formParameters.get(key);
            if(vector == null || vector.size() == 0) {
                return null;
            } else {
                String as[] = new String[vector.size()];
                vector.copyInto(as);
                return as;
            } 
        } catch(Exception exception) {
            return null;
        }
	}

	public Hashtable getHashtable(){ return formParameters; }
	
	public static Multiparser newMultiparser(HttpServletRequest request) {
		try {
			Multiparser fileUploader = new Multiparser(null, request, null);
			return fillContents(fileUploader, request);
		} catch(NoFileContentException nfcf) {
		    Kogger.error(Multiparser.class, nfcf);
			return null;
		} catch(CannotReadFileException crfe) {
		    Kogger.error(Multiparser.class, crfe);
		}
		return null;
	}
	
    public static Multiparser newMultiparser(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        try {
            Multiparser fileUploader = new Multiparser(context, request, response);
            return fillContents(fileUploader, request);
        } catch(NoFileContentException nfcf) {
            Kogger.error(Multiparser.class, nfcf);
            return null;
        } catch(CannotReadFileException crfe) {
            Kogger.error(Multiparser.class, crfe);
        }
        return null;
    }

    public void putFormParameter(String name, String value) {
		Vector v = getParameterVector(name);
		if(v!=null){
			v.add(value);
			formParameters.put(name,v);
		} else {
			formParameters.put(name, value); 
		}
    }

	private Vector getParameterVector(String name){
		Object o = formParameters.get(name);
		if(o==null)return null;
		if(o instanceof Vector) return (Vector)o;
		if(o instanceof String) {
			Vector v = new Vector();
			v.add(o);
			return v;
		}
		return null;
	}
	
	public static Multiparser fillContents(Multiparser uploader, HttpServletRequest request)
        throws CannotReadFileException, NoFileContentException {
		Vector fileList = new Vector();
		int contentLength = request.getContentLength();
		byte binaryTemp[] = new byte[contentLength];
		InputStream is = null;
		
		try {
			int totalReadSize = 0;
			int readSize = 0;
			for(; totalReadSize < contentLength; totalReadSize += readSize) {
				is = request.getInputStream();
				readSize = is.read(binaryTemp, totalReadSize, contentLength - totalReadSize);
			}
		} catch(IOException ioe) {
		    Kogger.error(Multiparser.class, ioe);
		}

		int currentIndex = 0;
		String boundary = new String();
		for(; currentIndex < contentLength && binaryTemp[currentIndex] != 13; currentIndex++) boundary = boundary + (char)binaryTemp[currentIndex];
		if(currentIndex == 0) throw new NoFileContentException();		
		while(currentIndex < contentLength && (char)binaryTemp[currentIndex + 1] != '-') {
			currentIndex += 2;
			String dataHeaderStr = new String();
			int startHeaderPosition = currentIndex;
			for(; binaryTemp[currentIndex] != 13 || binaryTemp[currentIndex + 2] != 13; currentIndex++);
			int headerSize = currentIndex - startHeaderPosition;
			try{
				dataHeaderStr = new String(binaryTemp, startHeaderPosition, headerSize, "KSC5601");
			}catch(UnsupportedEncodingException uee){
			    Kogger.error(Multiparser.class, uee);
			}
			currentIndex += 4;
			
			DataHeader dataHeader = new DataHeader(dataHeaderStr);
			WBFile file = null;
			if(dataHeader.isFile()) file = new WBFile(dataHeader);
			int binaryStartPosition = currentIndex;
			int binaryEndPosition = currentIndex;
			int boundaryKeyPosition = 0;
			for(boolean binaryEndFound = false; currentIndex < contentLength && !binaryEndFound; currentIndex++) {
				if(binaryTemp[currentIndex] == boundary.charAt(boundaryKeyPosition)) {
					if(boundaryKeyPosition == boundary.length() - 1) {
						binaryEndPosition = ((currentIndex - boundary.length()) + 1) - 3;
						binaryEndFound = true;
					} else {
						boundaryKeyPosition++;
					}
				} else {
					boundaryKeyPosition = 0;
				}
			}

			int totalBinarySize = (binaryEndPosition - binaryStartPosition) + 1;
			if(file != null && dataHeader.isFile()) {
				file.setSize(totalBinarySize);
				if(!file.getName().equals("") && file.getName() != null) {
					byte binaryArray[] = new byte[totalBinarySize];
					for(int i = 0; i < totalBinarySize; i++)
					binaryArray[i] = binaryTemp[binaryStartPosition + i];

                    /***************************************************************
                     * DRM decrypted for FormUploader SJ,Han 2008-06-05
                     ***************************************************************/
					file.setContents(binaryArray);

					//file.setContents(E3PSDRMHelper.upload(binaryArray, dataHeader.getFileName()));
                    /***************************************************************
                     * DRM decrypted for FormUploader SJ,Han 2008-06-05
                     ***************************************************************/
//					file.setContents(binaryArray);
					fileList.addElement(file);
				}
			} else {
				uploader.putFormParameter(dataHeader.getFieldName(), new String(binaryTemp, binaryStartPosition, totalBinarySize));
			}
		}

		uploader.setFiles(fileList);
		return uploader;
	}
}
