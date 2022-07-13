package ext.ket.shared.log4j;

public class ExLogger {

    private static final String EMPTY = "";
    public org.apache.log4j.Logger logger;

    public ExLogger() {
	logger = null;
    }

    /**
     * 소스 건들지 말것 System.out.println 절대 건들지 말것
     * 
     * @param message
     * @메소드명 : debug
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 14.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public void debug(Object message) {
	if (logger != null)
	    logger.debug(message);
	else
	    System.out.println(message);
    }

    public void debug(Object message, Throwable t) {
	if (logger != null)
	    logger.debug(message, t);
	else
	    System.out.println(message);
    }

    public void debug(Throwable t) {
	if (logger != null)
	    logger.debug(EMPTY, t);
	else
	    System.out.println(EMPTY);
    }

    public void error(Object message) {
	if (logger != null)
	    logger.error(message);
	else
	    System.out.println(message);
    }

    public void error(Object message, Throwable t) {
	if (logger != null)
	    logger.error(message, t);
	else
	    System.out.println(message);
    }

    public void error(Throwable t) {
	if (logger != null)
	    logger.error(EMPTY, t);
	else
	    System.out.println(EMPTY);
    }

    public void fatal(Object message) {
	if (logger != null)
	    logger.fatal(message);
	else
	    System.out.println(message);
    }

    public void fatal(Object message, Throwable t) {
	if (logger != null)
	    logger.fatal(message, t);
	else
	    System.out.println(message);
    }

    public void fatal(Throwable t) {
	if (logger != null)
	    logger.fatal(EMPTY, t);
	else
	    System.out.println(EMPTY);
    }

    public void info(Object message) {
	if (logger != null)
	    logger.info(message);
	else
	    System.out.println(message);
    }

    public void info(Object message, Throwable t) {
	if (logger != null)
	    logger.info(message, t);
	else
	    System.out.println(message);
    }

    public void info(Throwable t) {
	if (logger != null)
	    logger.info(EMPTY, t);
	else
	    System.out.println(EMPTY);
    }

    public void warn(Object message) {
	if (logger != null)
	    logger.warn(message);
	else
	    System.out.println(message);
    }

    public void warn(Object message, Throwable t) {
	if (logger != null)
	    logger.warn(message, t);
	else
	    System.out.println(message);
    }

    public void warn(Throwable t) {
	if (logger != null)
	    logger.warn(EMPTY, t);
	else
	    System.out.println(EMPTY);
    }

}