package e3ps.dms.beans;

import java.io.File;
import java.util.Properties;
import java.util.StringTokenizer;

import wt.util.WTProperties;
import e3ps.common.util.CharUtil;
import e3ps.edm.util.PropertiesUtil;
import ext.ket.shared.log.Kogger;

public class DMSProperties {
	
	private static DMSProperties INSTANCE = null;
	private static Properties DEFAULT = null;
	
	
	private DMSProperties()
    {
        this.initialize();
    }
	
    private void initialize()
    {
        if (DMSProperties.DEFAULT == null)
        {
            try
            {   
            	WTProperties wtproperties = WTProperties.getLocalProperties();
    			String location = wtproperties.getProperty("wt.codebase.location");
    			
                DMSProperties.DEFAULT = PropertiesUtil.load(new Properties(), location+File.separator+"e3ps"+File.separator+"dms.properties");
                
            }
            catch (Exception e)
            {
                Kogger.error(getClass(), this.getClass().getName() + ":initialize() DMSProperties - Can't initialize : "
                        + e.getMessage());
            }
        }
    }
    
    public synchronized static DMSProperties getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new DMSProperties();
        return INSTANCE;
    }

    public String getContainer() {
    	String value = getString("dms.container");
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
    
    public String getFolderPath(String type) {
    	String folderPath = "";
    	
    	if(type.equals("aRoot")){
    		folderPath = "/Default/자동차사업부/문서";
    	}else if(type.equals("eRoot")){
    		folderPath = "/Default/전자사업부/문서";
    	}else if(type.equals("adRoot")){
    		folderPath = "/Default/자동차사업부/개발의뢰";
    	}else if(type.equals("aeRoot")){
    		folderPath = "/Default/전자사업부/개발의뢰";
    	}else{
    		folderPath = "/Default";
    	}
    	
    	return folderPath;
    }
}
