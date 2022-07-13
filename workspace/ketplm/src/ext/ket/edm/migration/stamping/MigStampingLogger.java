package ext.ket.edm.migration.stamping;

import java.io.File;
import java.io.FilenameFilter;

import ext.ket.shared.log.Kogger;

public class MigStampingLogger {

    private static final String prefixPath = "\\\\192.168.1.112\\e$";
    private static final String changedPath = "e:";
    
    public static void main(String[] args) throws Exception {

	//String filePath1 = "\\\\192.168.1.112\\e$\\stamping\\download\\2014\\12\\03";
	String filePath2 = "\\\\192.168.1.112\\e$\\stamping\\download\\2014\\12\\07";
	
	String[] filePaths = new String[] { filePath2 };
	doBatch(filePaths);
    }

    public static void doBatch(String[] filePaths) throws Exception {

	for (String filePath : filePaths) {

	    String prefix = "TAG";
	    String subPrefix = "1";
	    String suffix = "_s.pdf";
	    boolean logShow = false;
	    boolean cadShow = false;
	    boolean closeAfter = true;
	    checkStampingFolderProblem(filePath, prefix, subPrefix, suffix, logShow, cadShow, closeAfter);

	    prefix = "TAG";
	    subPrefix = "1";
	    suffix = ".stp";
	    // checkStepFolder(filePath, prefix, subPrefix, suffix);
	}
    }

    public static void checkStepFolderProblem(String filePath, String prefix, String subPrefix, String suffix, boolean logShow, boolean cadShow, boolean closeAfter) throws Exception {

	File targetFolder = new File(filePath);

	if (targetFolder.isDirectory()) {

	    File[] fileTypeFoldArray = findFileByPattern(filePath, prefix, "");
	    if (fileTypeFoldArray != null) {
		for (File requFold : fileTypeFoldArray) {
		    System.out.print("foldname:" + requFold.getName() + ":");

		    File[] fileSubFileArray = findFileByPattern(requFold.getAbsolutePath(), subPrefix, "");
		    if (fileSubFileArray != null && fileSubFileArray.length != 0) {
			for (File subFold : fileSubFileArray) {
			    System.out.print(":" + subFold.getName());
			    File[] fileStmpingFileArray = findFileByPattern(subFold.getAbsolutePath(), "", suffix);
			    if (fileStmpingFileArray != null && fileStmpingFileArray.length != 0) {
				for (File targetFile : fileStmpingFileArray) {
				    System.out.print(":" + targetFile.getName());
				}
			    } else {
				Kogger.debug(MigStampingLogger.class, "E:\\stamping\\acadworker\\acadworker.exe -log=" + logShow + " -AutoCAD=" + cadShow + " -close_after=" + closeAfter + " -file="
				        + requFold.getAbsolutePath().replace(prefixPath, changedPath) + "\\" + subPrefix + "\\" + requFold.getName() + ".xml");
			    }
			}
		    }
		}
	    }
	}
    }

    // public static void checkStepFolder(String filePath, String prefix, String subPrefix, String suffix) throws Exception {
    //
    // File targetFolder = new File(filePath);
    //
    // if (targetFolder.isDirectory()) {
    //
    // File[] fileTypeFoldArray = findFileByPattern(filePath, prefix, "");
    // if (fileTypeFoldArray != null) {
    // for (File requFold : fileTypeFoldArray) {
    // System.out.print("foldname:" + requFold.getName() + ":");
    //
    // File[] fileSubFileArray = findFileByPattern(requFold.getAbsolutePath(), subPrefix, "");
    // if (fileSubFileArray != null && fileSubFileArray.length == 0) {
    // for (File subFold : fileSubFileArray) {
    // System.out.print(":" + subFold.getName());
    // File[] fileStmpingFileArray = findFileByPattern(subFold.getAbsolutePath() , "", suffix);
    // if (fileStmpingFileArray != null && fileStmpingFileArray.length != 0) {
    // for (File targetFile : fileStmpingFileArray) {
    // System.out.print(":" + targetFile.getName());
    // }
    // }
    // }
    // }else{
    // Kogger.debug(getClass(), "<=");
    // }
    // }
    // }
    // }
    // }

    public static void checkStampingFolderProblem(String filePath, String prefix, String subPrefix, String suffix, boolean logShow, boolean cadShow, boolean closeAfter) throws Exception {

	File targetFolder = new File(filePath);

	if (targetFolder.isDirectory()) {

	    File[] fileTypeFoldArray = findFileByPattern(filePath, prefix, "");
	    if (fileTypeFoldArray != null) {
		for (File requFold : fileTypeFoldArray) {
		    // System.out.print("foldname:" + requFold.getName() + ":");

		    File[] fileSubFileArray = findFileByPattern(requFold.getAbsolutePath(), subPrefix, "");
		    if (fileSubFileArray != null && fileSubFileArray.length != 0) {
			for (File subFold : fileSubFileArray) {
			    // System.out.print(":" + subFold.getName());
			    File[] fileStmpingFileArray = findFileByPattern(subFold.getAbsolutePath(), "", suffix);
			    if (fileStmpingFileArray != null && fileStmpingFileArray.length != 0) {
				for (File targetFile : fileStmpingFileArray) {
				    // System.out.print(":" + targetFile.getName());
				}
			    } else {
				Kogger.debug(MigStampingLogger.class, "E:\\stamping\\acadworker\\acadworker.exe -log=" + logShow + " -AutoCAD=" + cadShow + " -close_after=" + closeAfter + " -file="
				        + requFold.getAbsolutePath().replace(prefixPath, changedPath) + "\\" + subPrefix + "\\" + requFold.getName() + ".xml");
			    }
			}
		    }
		    // Kogger.debug(getClass(), "<=");
		}
	    }
	}
    }

    // public static void checkStampingFolder(String filePath, String prefix, String subPrefix, String suffix) throws Exception {
    //
    // File targetFolder = new File(filePath);
    //
    // if (targetFolder.isDirectory()) {
    //
    // File[] fileTypeFoldArray = findFileByPattern(filePath, prefix, "");
    // if (fileTypeFoldArray != null) {
    // for (File requFold : fileTypeFoldArray) {
    // System.out.print("foldname:" + requFold.getName() + ":");
    //
    // File[] fileSubFileArray = findFileByPattern(requFold.getAbsolutePath(), subPrefix, "");
    // if (fileSubFileArray != null && fileSubFileArray.length == 0) {
    // for (File subFold : fileSubFileArray) {
    // System.out.print(":" + subFold.getName());
    // File[] fileStmpingFileArray = findFileByPattern(subFold.getAbsolutePath() , "", suffix);
    // if (fileStmpingFileArray != null && fileStmpingFileArray.length != 0) {
    // for (File targetFile : fileStmpingFileArray) {
    // System.out.print(":" + targetFile.getName());
    // }
    // }
    // }
    // }else{
    // Kogger.debug(getClass(), "<=");
    // }
    // }
    // }
    // }
    // }

    // 특정 Directory의 파일을 찾아오기
    private static File[] findFileByPattern(String dirPath, String prefix, String suffix) throws Exception {

	final String _prefix = prefix.toLowerCase();
	final String _suffix = suffix.toLowerCase();
	File rootDir = new File(dirPath);

	File[] matchingFiles = rootDir.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
		return name.toLowerCase().startsWith(_prefix) && name.toLowerCase().endsWith(_suffix);
	    }
	});

	return matchingFiles;
    }

}
