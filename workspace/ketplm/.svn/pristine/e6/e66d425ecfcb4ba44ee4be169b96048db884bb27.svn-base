/**
 * @(#) FileRenameCommandImpl.java Copyright (c) jerred. All rights reserverd
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2004. 3. 3.
 * @author Cho Sung Ok, jerred@bcline.com
 * @desc
 */

package e3ps.common.content;

import java.io.File;

/**
 * 이 소스는 까오기 보드(http://www.kkaok.pe.kr/)의 소스를 참고로 만들어 졌음을 밝힙니다.
 */
public class FileRenameCommandImpl implements FileRenameCommand {
	public File rename(File s, File f) {
		if (f.length () < 1) {
			s.renameTo ( f );
			return f;
		}
		String name = f.getName ();
		String body = null;
		String ext = null;

		int dot = name.lastIndexOf ( "." );
		if (dot != -1) {
			body = name.substring ( 0 , dot );
			ext = name.substring ( dot ); // includes "."
		} else {
			body = name;
			ext = "";
		} 

		int count = 0;
		while (f.length () > 0 && count < 99999) {
			count++;
			String newName = body + count + ext;
			f = new File ( f.getParent () , newName );
		}
		s.renameTo ( f );
		return f;
	}
}
