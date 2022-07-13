package com.dump.del;

import java.io.File;
import java.io.FilenameFilter;
import java.util.StringTokenizer;

import ext.ket.shared.log.Kogger;

public class FileDeleteUtil {

    public static void main(String[] args) {
	
	FileDeleteUtil checker = new FileDeleteUtil();
	
	String prefix ="hs_err_pid";
	String delimiter = "↕";
	String DirName = "D:\\ptc\\Windchill_10.2\\Windchill"+delimiter+"D:\\ptc\\Windchill_10.2\\Windchill\\WTBackup";
	
	final String[] rootDir = DirName.split(delimiter);
	final String _prefix = prefix.toLowerCase();
	
	for(int i=0; i<rootDir.length; i++){
	    File base = new File(rootDir[i]);
	    if(checker.isAvailable(base)){
		DelFile(base,_prefix);
            }else{
		Kogger.debug("Not exist or not a directory.");
	    }
	}
    }
    
    public static void DelFile(File base,final String _prefix){
	File[] matchingFiles = base.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
		return name.toLowerCase().startsWith(_prefix);
	    }
	});
	
	for(int index=0; index < matchingFiles.length; index++){
	    matchingFiles[index].delete();
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
