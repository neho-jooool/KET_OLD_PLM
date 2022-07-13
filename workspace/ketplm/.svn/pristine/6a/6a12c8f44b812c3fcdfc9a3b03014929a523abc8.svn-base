package ext.ket.shared.log4j;

public class Log4JSample {

    public void test() {

	try {

	    /**
	     * jsp
	     * 
	     * <%@page import = "ext.ket.shared.log4j"%>
	     * 
	     * <%
	     * 
	     * KetLogger.ket.debug("Test Debug"); KetLogger.ket.info("Test Info");
	     * 
	     * %>
	     */

	    /**
	     * method 실행 시점
	     * 
	     * try{ WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal(); PskLogger.psk.info(this.getClass().getSimpleName() + " : createEVR : " + orgPrincipal.getName() + " : " +
	     * ((WTUser)orgPrincipal).getFullName()); }catch(WTException e){PskLogger.psk.error(e);}
	     */

	    /**
	     * java
	     */
	    KetLogger.ket.debug("Test Debug");
	    KetLogger.ket.info("Test Info");

	} catch (Exception e) {

	    KetLogger.ket.error("Test Error");
	    KetLogger.ket.error(e);
	}

    }

    public static void main(String[] args) {

	Log4JSample sample = new Log4JSample();
	sample.test();

    }

}
