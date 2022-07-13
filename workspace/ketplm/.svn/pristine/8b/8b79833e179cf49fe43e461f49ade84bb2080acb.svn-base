package e3ps.edm.clients.batch;
import java.io.File;

import javax.swing.filechooser.FileFilter;


public class EPMFileFilter extends FileFilter {

	private String m_description = null;
	//private String m_extension = null;
	
	private String[] m_extensions = null;
	
	public EPMFileFilter(String extension, String description) {
		m_description = description;
		//m_extension = "."+extension.toLowerCase();
		
		m_extensions = new String[1];
		m_extensions[0] = extension;
	}
	
	public EPMFileFilter(String[] extensions, String description) {
		m_description = description;
		m_extensions = extensions;
	}
	
    public boolean accept(File f) {
    	if (f == null) {
			return false;
    	}
    	
    	if (f.isDirectory()) {
            return true;
        }
    	
    	if(m_extensions == null) {
    		return true;
    	}

        String extension = getExtension(f);
        if(extension == null) {
        	return false;
        }
        
        
        for(int i = 0; i < m_extensions.length; i++) {
        	if(extension.equals(m_extensions[i].toLowerCase())) {
        		return true;
        	}
        }
        /*
        if (extension != null) {
            if (extension.equals(Utils.tiff) ||
                extension.equals(Utils.tif) ||
                extension.equals(Utils.gif) ||
                extension.equals(Utils.jpeg) ||
                extension.equals(Utils.jpg) ||
                extension.equals(Utils.png)) {
                    return true;
            } else {
                return false;
            }
        }
        */

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return m_description;
    }
    
    public String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
