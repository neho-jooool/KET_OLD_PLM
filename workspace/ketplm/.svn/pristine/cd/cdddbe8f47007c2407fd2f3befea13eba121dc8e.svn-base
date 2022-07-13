/**
 *	@(#) MessageBox.java
 *	Copyright (c) jerred. All rights reserverd
 *
 *	@version 1.00
 *	@since jdk 1.4.02
 *	@createdate 2004. 3. 3.
 *	@author Cho Sung Ok, jerred@bcline.com
 *	@desc
 */

package e3ps.common.jdf.message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import e3ps.common.jdf.log.Logger;
import ext.ket.shared.log.Kogger;

;
/**
 *
 */
public class MessageBox
{
    private static Hashtable INSTANCE = new Hashtable();

    private MessageBox()
    {}

    /**
     * 특정 키에 해당하는 Configuration File을 초기화 시켜주는 Method
     */
    public synchronized static MessageImpl getInstance(String msgCode)
    {
        if (INSTANCE == null) INSTANCE = new Hashtable();

        if (!INSTANCE.contains(msgCode))
        {
            new MessageBox().initialize(msgCode);
        }

        return (MessageImpl) INSTANCE.get(msgCode);
    }

    /**
     * Message Definition File을 찾을수 없거나 load할 수 없을때 발생한다.
     */
    private synchronized void initialize(String _fileName) throws MessageException
    {
    	InputStream is = null;

        try
        {
            // Config conf = ConfigImpl.getInstance();
            //
            // String message_file = conf.getString(msgCode);
            // File file = new File(message_file);
            // if (!file.canRead())
            // throw new
            // MessageException("e3ps.jdf.message.MessageBox:initialize(msgCode)
            // - Can't open message file : " + message_file);
            // FileInputStream fin = new FileInputStream(file);
            // Properties props = new Properties();
            // props.load(fin);
            // fin.close();

            is = this.getClass().getResourceAsStream("/e3ps/"+_fileName+".properties");

            Properties props = new Properties();
            props.load(is);

            MessageImpl instance = new MessageImpl(props);
            INSTANCE.put(_fileName, instance);
        }
        catch (Exception e)
        {
            Logger.err.println("e3ps.jdf.message.MessageBox:initialize(msgCode) - Can't initialize msgBox : " + _fileName + "  error : "
                    + e.getMessage());
            throw new MessageException("e3ps.jdf.message.MessageBox:initialize(msgCode) - Can't initialize msgBox : " + _fileName + "  error : "
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
