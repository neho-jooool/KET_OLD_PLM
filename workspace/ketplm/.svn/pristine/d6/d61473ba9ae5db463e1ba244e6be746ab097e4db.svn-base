package ext.ket.shared.log4j;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import wt.method.RemoteAccess;
import wt.util.WTException;

public class KetLogger implements RemoteAccess {

    public static ExLogger ket = null;
    public static ExLogger migration = null;

    static {

	ket = new ExLogger();
	migration = new ExLogger();

	refreshLogConfig();

    }

    public static void refreshLogConfigFwd() throws WTException {

	if (!wt.method.RemoteMethodServer.ServerFlag) {

	    try {

		Class[] argTypes = {};
		Object[] args = {};
		wt.method.RemoteMethodServer.getDefault().invoke("refreshLogConfig", "ext.ket.shared.log4j.KetLogger", null, argTypes, args);

	    } catch (java.lang.reflect.InvocationTargetException e) {

		Throwable targetE = e.getTargetException();

		if (targetE instanceof wt.util.WTException)
		    throw (WTException) targetE;

		Object[] param = { "refreshLogConfig" };
		throw new WTException(targetE, "wt.fc.fcResource", wt.fc.fcResource.OPERATION_FAILURE, param);

	    } catch (java.rmi.RemoteException rme) {

		Object[] param = { "refreshLogConfig" };
		throw new WTException(rme, "wt.fc.fcResource", wt.fc.fcResource.OPERATION_FAILURE, param);

	    }
	
	}else{
	    
	    refreshLogConfig();
	}

    }

    public static void refreshLogConfig() {

	String log4j_WebServer = "";
	String log4j_MethoedServer = "";

	try {

	    wt.util.WTProperties wtproperties = wt.util.WTProperties.getLocalProperties();
	    log4j_WebServer = wtproperties.getProperty("log4j.webserver", "");
	    log4j_MethoedServer = wtproperties.getProperty("log4j.methodserver", "");

	} catch (IOException ioe) {
	    KetLogger.ket.error(ioe);
	}

	if (!wt.method.RemoteMethodServer.ServerFlag) {

	    if (log4j_WebServer != null && !log4j_WebServer.equals("")) {

		DOMConfigurator.configure(log4j_WebServer);

		ket.logger = Logger.getLogger("ket");
		migration.logger = Logger.getLogger("migration");

	    }

	} else if (log4j_MethoedServer != null && !log4j_MethoedServer.equals("")) {

	    DOMConfigurator.configure(log4j_MethoedServer);

	    ket.logger = Logger.getLogger("ket");
	    migration.logger = Logger.getLogger("migration");

	}

    }

}
