package e3ps.load.migration.edm.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
 
 
public class LogToFile {
	
    private static Logger logger = null;
    
    private FileHandler fileHandler = null;
	
    public LogToFile(String logFile, boolean append) throws IOException {
    	logger = Logger.getLogger(LogToFile.class.getName());         
		
		// Create an instance of FileHandler that write log to a file called 
        // app.log. Each new message will be appended at the at of the log file. 
        // 
        //boolean append = true;
		fileHandler = new FileHandler(logFile, append);         
		fileHandler.setFormatter(new CustomFormatter());
		logger.addHandler(fileHandler); 
		//logger.setUseParentHandlers(false);
    }
    
    public synchronized void log(String msg) {
    	LogRecord record = new LogRecord(Level.INFO, msg);
	    logger.log(record);
	    
	    fileHandler.flush();
	    fileHandler.close();
	    
    	//logger.info(msg); 
	}
	
	public static void main(String[] args) throws IOException { 
        
		LogToFile l = new LogToFile("app.log",true);
		l.log("xxxxx");
	} 
}

