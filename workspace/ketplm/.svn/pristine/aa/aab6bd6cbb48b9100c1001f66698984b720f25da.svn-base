package e3ps.load.migration.edm.log;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
	
	public CustomFormatter() {
		super();
	}
	
	public synchronized String format(LogRecord record) {
		// Create a StringBuffer to contain the formatted record
		// start with the date.
		StringBuffer sb = new StringBuffer();
		/*
		// Get the date from the LogRecord and add it to the buffer
		Date date = new Date(record.getMillis());
		sb.append(date.toString());
		sb.append(" ");
		
		// Get the level name and add it to the buffer
		sb.append(record.getLevel().getName());
		sb.append(" ");
		*/ 
		// Get the formatted message (includes localization 
		// and substitution of paramters) and add it to the buffer
		sb.append(formatMessage(record));
		sb.append("\n");

		return sb.toString();
		
		/*    
		 	//String methodName = record.getSourceMethodName();
	    	String message = record.getMessage();
	    	StringBuffer buffer = new StringBuffer();
	    	//buffer.append(methodName);
	    	//buffer.append("=");
	    	buffer.append(message);
	    	return buffer.toString();
    	*/
  }
}
