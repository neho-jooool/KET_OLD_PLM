/*
 * @(#) HtmlUtil.java  Create on 2004. 11. 22.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.web;

/**
 * HTML 태크를 생성해주는 유틸클래스
 * 
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2004. 11. 22.
 * @since 1.4
 */
public class HtmlTagUtil
{
    /**
     * Select Tag 생성
     * @param name 태그의 이름
     * @param key &lt;option value='key'&gt;
     * @param value &lt;option value='key'&gt;value&lt;/option&gt;
     * @return 태그 스트링
     */
    public static String selectTag(String name, String[] key, String[] value)
    {
        return selectTag(name, key, value, "");
    }
    
    /**
     * Select Tag 생성
     * @param name 태그의 이름
     * @param key &lt;option value='key'&gt;
     * @param value &lt;option value='key'&gt;value&lt;/option&gt;
     * @param option JavaScript이나 dHtml을 설정
     * @return 태그 스트링
     */
    public static String selectTag(String name, String[] key, String[] value, String option)
    {
        return selectTag(name, "", key, value, option);
    }
    
    /**
     * Select Tag 생성
     * @param name 태그의 이름
     * @param selected 특정값 선택 태그 &lt;option value='key' selected&gt;
     * @param key &lt;option value='key'&gt;
     * @param value &lt;option value='key'&gt;value&lt;/option&gt;
     * @param option JavaScript이나 dHtml을 설정
     * @return 태그 스트링
     */
    public static String selectTag(String name, String selected, String[] key, String[] value, String option)
    {
        String check = "";
        StringBuffer sb = new StringBuffer();
        
        sb.append("<SELECT NAME='"+name+"' "+option+">\n");
        sb.append("<option value=''>선택</option>\n");
        for (int i = 0; i < key.length; i++)
        {
            check = key[i].equals(selected) ? "selected" : "";
            sb.append("<option value=\""+key[i]+"\" "+check+">");
            sb.append(value[i]);
            sb.append("</option>\n");
        }
        sb.append("</SELECT>\n");
        
        return sb.toString();
    }
    
    /**
     * Select Tag 생성
     * isReadOnly 가 true 이면 text 태그로 생김
     * @param name
     * @param selected
     * @param key
     * @param value
     * @param option
     * @param _isReadOnly
     * @return
     */
    public static String selectTag(String name, String selected, String[] key, String[] value, String option, boolean _isReadOnly)
    {
        if(_isReadOnly)
            return textTag(name, selected, "", _isReadOnly);
        
        return selectTag(name, selected, key, value, option);
    }
    
    /**
     * text Tag 생성
     * isReadOnly 가 true 이면 읽기 전용 태그로 생김
     * @param _name
     * @param _value
     * @param _option
     * @param _isReadOnly
     * @return
     */
    public static String textTag(String _name, String _value, String _option, boolean _isReadOnly)
    {
        StringBuffer sb = new StringBuffer();

        sb.append("<input type=text");
        sb.append(" name='" + _name + "'");
        sb.append(" value='" + _value + "' ");
        sb.append( _isReadOnly?"style='border:0' readonly ":" ");
        sb.append(_option + ">");
        
        return sb.toString();
    }
}
