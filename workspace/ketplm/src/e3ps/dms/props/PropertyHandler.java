package e3ps.dms.props;

import java.io.File;
import java.util.Properties;

import wt.util.WTProperties;
import e3ps.edm.util.PropertiesUtil;

public class PropertyHandler {

	private static PropertyHandler instance = new PropertyHandler();
	private static Properties props;

	private PropertyHandler() {

		try {

			props = new Properties();
			WTProperties wtproperties = WTProperties.getLocalProperties();
			String location = wtproperties.getProperty("wt.codebase.location");

			// String path = location + File.separator + "e3ps" + File.separator + "ecm.properties";
			// InputStream in = PropertyHandler.class.getClassLoader().getResourceAsStream(path);
			System.out.println("location : " + location + File.separator + "e3ps" + File.separator + "dms" + File.separator + "docuinfo.properties");
			props = PropertiesUtil.load(new Properties(), location + File.separator + "e3ps" + File.separator + "dms" + File.separator + "docuinfo.properties");
			/*
			 * if (in == null) { System.out.println("없다.."); } else { System.out.println("있네.." + in.toString()); } props.load(in);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PropertyHandler getInstance() {

		return instance;
	}

	public String getString(String key) throws Exception {

		if (props == null) {
			instance = new PropertyHandler();

			if (props == null)
				throw new Exception("Properties Object is null - properties file empty ");
		}

		return props.getProperty(key);
	}

}
