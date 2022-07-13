package e3ps.ews.beans;

import java.io.File;
import java.util.Properties;
import java.util.StringTokenizer;

import wt.util.WTProperties;
import e3ps.common.util.CharUtil;
import e3ps.edm.util.PropertiesUtil;
import ext.ket.shared.log.Kogger;

public class EWSProperties {
	
	private static EWSProperties INSTANCE = null;
	private static Properties DEFAULT = null;
	
	
	private EWSProperties()
    {
        this.initialize();
    }
	
    private void initialize()
    {
        if (EWSProperties.DEFAULT == null)
        {
            try
            {   
            	WTProperties wtproperties = WTProperties.getLocalProperties();
    			String location = wtproperties.getProperty("wt.codebase.location");
    			
                EWSProperties.DEFAULT = PropertiesUtil.load(new Properties(), location+File.separator+"e3ps"+File.separator+"ews.properties");
                
            }
            catch (Exception e)
            {
                Kogger.error(getClass(), this.getClass().getName() + ":initialize() EWSProperties - Can't initialize : "
                        + e.getMessage());
            }
        }
    }
    
    public synchronized static EWSProperties getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new EWSProperties();
        return INSTANCE;
    }

    public String getContainer() {
    	String value = getString("ews.container");
    	if(value != null) {
    		return value;
    	}
    	return "";
    }
    
    public String getString(String key)
    {
        String value = DEFAULT.getProperty(key);
        if (value == null)
            return null;
        else
            value = CharUtil.E2K(value);
        return value;
    }
    
    public String getString(String _key, String _default) {
        String value = DEFAULT.getProperty(_key);
        return value == null ? _default : value;
    }
    
    public String[] getArray(String key)
    {
        String value = getString(key);
        if (value == null)
            return null;
        StringTokenizer st = new StringTokenizer(value, ";");
        String[] result = new String[st.countTokens()];

        int idx = 0;
        while (st.hasMoreElements())
        {
            result[idx++] = st.nextToken();
        }

        return result;
    }
}
