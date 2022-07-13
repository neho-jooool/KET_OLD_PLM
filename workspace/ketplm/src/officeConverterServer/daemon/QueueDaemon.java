package officeConverterServer.daemon;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class QueueDaemon {

    public static final String ForderPath = "F:\\ConvertFileDownload";
    public static final String _PROGRESS_DIRECTORY_ = ForderPath +"//PROGRESS";
    public static final String _SUCCESS_DIRECTORY_ = ForderPath + "//SUCCESS";
    public static final String _FAIL_DIRECTORY_ = ForderPath + "//FAIL";

    public static void main(String[] args) throws Exception {

	ExecutorService execService = Executors.newFixedThreadPool(10);
	
	HashMap<String, Future<?>> FutureMap = new HashMap<String, Future<?>>();
	
	int checkCount = 0;
	
	String log4j = "F:\\converterServer\\BatchScript\\officeConverterServer\\log4j.xml";
	DOMConfigurator.configure(log4j);
	
	Logger log = Logger.getLogger("officeConverterServer");
	log.getRootLogger().setLevel(Level.INFO);//pdfbox에서 debug 로깅이 너무 많아 처리
	
	while (true) {
	    // Create a file object
	    File file = new File(ForderPath);

	    // 1. check if the file exists or not
	    boolean isExists = file.exists();

	    // 2. check if the object is directory or not.
	    
	    if (isExists && file.isDirectory()) {
		File[] fileList = file.listFiles();
		for (File tFile : fileList) {
		    if (!tFile.isDirectory()) {
			String ext = FilenameUtils.getExtension(tFile.getName());
			if ("json".equals(ext)) {
			    TimeUnit.SECONDS.sleep(1);
			    File PROGRESS = new File(_PROGRESS_DIRECTORY_);

			    if (new File(_PROGRESS_DIRECTORY_ + "//" + tFile.getName()).exists()) {
				tFile.delete();
			    } else {
				FileUtils.moveFileToDirectory(tFile, PROGRESS, true);
			    }
//			    execService.execute(new WorkTask(_PROGRESS_DIRECTORY_ + "//" + tFile.getName()));
			    Future<?> future = execService.submit(new WorkTask(_PROGRESS_DIRECTORY_ + "//" + tFile.getName()));
			    FutureMap.put(future.toString(), future);
			     
			    TimeUnit.SECONDS.sleep(1);
			} else {

			}
		    }
		}
	    } else {

	    }
	    
	    Set<String> st = FutureMap.keySet();
	    Iterator<String> it = st.iterator();

	    while (it.hasNext()) {
		String key = (String) it.next();

		Future<?> thread = (Future<?>) FutureMap.get(key);

		if (thread.isDone()) {
		    log.info("Thread [ "+key + " ] isDone... ");
		    it.remove();
		}
	    }
	    
	    if(FutureMap.isEmpty()){
		checkCount++;
	    }
	    
	    if(checkCount == 30000){
		checkCount = 0;
		if (!isExists) {
		    log.info("There is nothing..[ "+ForderPath+" ]");
		}else{
		    log.info("Waiting for work...");    
		}
		
	    }

	}

    }

}
