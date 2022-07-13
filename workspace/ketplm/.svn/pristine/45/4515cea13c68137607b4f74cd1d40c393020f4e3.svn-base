/*
 * @(#) ConfigBox.java  Create on 2006. 6. 15.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.jdf.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import e3ps.common.jdf.log.Logger;
import e3ps.common.jdf.message.MessageException;
import ext.ket.shared.log.Kogger;

/**
 * MessageImpl의 확장된 기능을 ConfigEx로 옮겨 Property 값을 다양하게 출력한다.
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2006. 6. 15.
 * @since 1.4
 */
public class ConfigEx
{
    private static Hashtable INSTANCE = new Hashtable();

    private ConfigEx()
    {}

    /**
     * 특정 키에 해당하는 Configuration File을 초기화 시켜주는 Method
     */
    public synchronized static ConfigExImpl getInstance(String _propertiesFileName)
    {
        if (INSTANCE == null) INSTANCE = new Hashtable();

        if (!INSTANCE.contains(_propertiesFileName))
        {
            new ConfigEx().initialize(_propertiesFileName);
        }

        return (ConfigExImpl) INSTANCE.get(_propertiesFileName);
    }

    /**
     * Message Definition File을 찾을수 없거나 load할 수 없을때 발생한다.
     */
    private synchronized void initialize(String _fileName) throws MessageException
    {
        InputStream is = null;

        try
        {
            is = this.getClass().getResourceAsStream("/e3ps/"+_fileName+".properties");

            Properties props = new Properties();
            props.load(is);

            Config instance = new ConfigExImpl(props);
            INSTANCE.put(_fileName, instance);
        }
        catch (Exception e)
        {
            Logger.err.println("e3ps.common.jdf.config.ConfigEx:initialize() - Can't initialize ConfigEx : " + _fileName + "  error : "
                    + e.getMessage());
            throw new MessageException("e3ps.common.jdf.config.ConfigEx:initialize() - Can't initialize ConfigEx : " + _fileName + "  error : "
                    + e.getMessage());
        }
        finally
        {
    		try
    		{
    			if( is != null )
    			{
					is.close();
				}
        	}
    		catch( IOException e )
    		{
				Kogger.error(getClass(), e);
			}
        }
    }
}
