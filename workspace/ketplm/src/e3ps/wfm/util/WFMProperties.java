package e3ps.wfm.util;

import java.io.File;
import java.util.Properties;
import java.util.StringTokenizer;

import wt.util.WTProperties;
import e3ps.common.util.CharUtil;
import e3ps.edm.util.PropertiesUtil;
import ext.ket.shared.log.Kogger;

public class WFMProperties
{
	private static WFMProperties INSTANCE = null;
	private static Properties DEFAULT = null;

	private WFMProperties()
    {
		this.initialize();
    }

	private void initialize()
    {
        if( WFMProperties.DEFAULT == null )
        {
            try
            {
            	WTProperties wtproperties = WTProperties.getLocalProperties();
    			String location = wtproperties.getProperty("wt.codebase.location");

    			WFMProperties.DEFAULT = PropertiesUtil.load(new Properties(), location+File.separator+"e3ps"+File.separator+"wfm.properties");
            }
            catch( Exception e )
            {
                Kogger.error(getClass(),  this.getClass().getName() + ":initialize() WFMProperties - Can't initialize : " + e.getMessage() );
            }
        }
    }

	public synchronized static WFMProperties getInstance()
    {
        if( INSTANCE == null )
            INSTANCE = new WFMProperties();

        return INSTANCE;
    }

	public Properties getProperties()
    {
		return DEFAULT;
    }

    public String getString(String key)
    {
        String value = DEFAULT.getProperty(key);

        if( value == null )
            return null;
        else
            value = CharUtil.E2K(value);

        return value;
    }

    public Object[] getArray(String key)
    {
        String value = getString(key);

        if( value == null )
            return null;

        StringTokenizer st = new StringTokenizer(value, ";");
        Object[] result = new Object[st.countTokens()];

        int idx = 0;
        while (st.hasMoreElements())
        {
            result[idx++] = st.nextToken();
        }

        return result;
    }
}
