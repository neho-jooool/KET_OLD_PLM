package e3ps.sap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import wt.util.WTProperties;

import com.sap.mw.jco.JCO;

import ext.ket.shared.log.Kogger;

public class RFCConnect {
    protected static String     properties_filename = "sapLogin.properties";
    protected static Properties login_properties    = null;

    public static JCO.Client getConnection() {
	JCO.Client client = null;

	try {
	    client = JCO.createClient(getLoginProperties());

	    if (client == null) {
		Kogger.debug(RFCConnect.class, "######## JCO.Client is Null");
		Kogger.debug(RFCConnect.class, "JCO.Client Failed");
	    } else {
		// Kogger.debug(RFCConnect.class, "######## JCO.Client is not Null");
		// Kogger.debug(RFCConnect.class, "JCO.Client OK");
	    }
	} catch (JCO.Exception ex) {
	    Kogger.error(RFCConnect.class, ex);
	} catch (Exception ex) {
	    Kogger.error(RFCConnect.class, ex);
	}

	return client;
    }

    public static Properties getLoginProperties() {
	if (login_properties == null) {
	    login_properties = new Properties();

	    try {
		String wt_home = WTProperties.getServerProperties().getProperty("wt.home");

		StringBuffer sb = new StringBuffer();
		sb.append(wt_home);
		sb.append(File.separator);
		sb.append("codebase");
		sb.append(File.separator);
		sb.append("e3ps");
		sb.append(File.separator);
		sb.append(properties_filename);

		// Kogger.debug(RFCConnect.class, "path : " + sb.toString());

		login_properties.load(new FileInputStream(sb.toString()));
	    } catch (IOException ex) {
		Kogger.error(RFCConnect.class, ex);
		// System.exit(1);
	    }
	}

	return login_properties;
    }

    public static void main(String args[]) throws Exception {
	getConnection();
	JCO.Client mConnection = JCO.createClient("100"				// SAP client
		, "PLMTFT"	// userid
		, "onefire13"	// password
		, null				// language
		, "192.168.1.221"		// application server host name
		, "00");				// system number
	mConnection.connect();
	// Kogger.debug(RFCConnect.class, mConnection.getAttributes());
	mConnection.disconnect();

	// Kogger.debug(RFCConnect.class, "success");
    }
}
