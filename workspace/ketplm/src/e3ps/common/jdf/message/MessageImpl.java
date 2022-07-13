/**
 * @(#) MessageImpl.java
 * Copyright (c) jerred. All rights reserverd
 * 
 *	@version 1.00
 *	@since jdk 1.4.02
 *	@createdate 2004. 3. 3.
 *	@author Cho Sung Ok, jerred@bcline.com
 *	@desc	
 */

package e3ps.common.jdf.message;
 
import java.util.Properties;
import java.util.StringTokenizer;

import e3ps.common.util.CharUtil;

/**
*<b>이 소스는 <a href="http://www.javaservice.net/">JavaService Net</a>의 <br> Java Development Framework(JDF) ver1.0.0을 기초로 만들어졌음을 밝힙니다.</b>
*/
public class MessageImpl implements Message {
 	private static Properties INSTANCE = null;
	
	protected MessageImpl(Properties prop) {
		INSTANCE = prop;
	}
	
	/**
	* 특정 code에 해당하는 Message를 리턴한다.
	*/
	public String getMessage(String code) {	
		String value = INSTANCE.getProperty(code);
		if ( value == null ) throw new MessageException(this.getClass().getName() + ":getMessage(code) -  value of key(" +code+") is null" );
		else value = CharUtil.E2K(value);
		return value;
	}
	
	/**	 
	 * 
	 */
	public boolean contains(String code) {	
		return INSTANCE.contains(code);
	}
    
    /**
     * 주어진 key의 값이 ,로 구성되어있을 경우 배열로 리턴한다.
     * 
     * @param key
     * @return
     */
    public String[] getArray(String key)
    {
        String value = INSTANCE.getProperty(key);
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
