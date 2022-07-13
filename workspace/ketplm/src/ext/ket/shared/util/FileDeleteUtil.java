package ext.ket.shared.util;

import java.io.File;
import java.io.FilenameFilter;

import ext.ket.shared.log.Kogger;

public class FileDeleteUtil {

    public static void main(String[] args) {
	
	FileDeleteUtil checker = new FileDeleteUtil();
	
	String prefix ="hs_err_pid";
	
	final String rootDir = "D:\\ptc\\Windchill_10.2\\Windchill";
	final String _prefix = prefix.toLowerCase();
	File base = new File(rootDir);
	
	if(checker.isAvailable(base)){
		
		File[] matchingFiles = base.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
			return name.toLowerCase().startsWith(_prefix);
		    }
		});
		
		for(int index=0; index < matchingFiles.length; index++){
		    matchingFiles[index].delete();
		}
	}else{
	    Kogger.debug("Not exist or not a directory.");
	}
	
	
    }
    
    
    public boolean isAvailable(File directory) {
	        boolean result = false;
	         
	        if(directory.exists() && directory.isDirectory()) {
	            result = true;
	        }
	        return result;
	    }


}
