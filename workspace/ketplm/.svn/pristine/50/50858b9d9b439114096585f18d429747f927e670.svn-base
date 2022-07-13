/*
 * @(#) NumberCodeHelper.java Create on 2004. 12. 21.
 * Copyright (c) e3ps. All rights reserved
 * 
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2004. 12. 21.
 * @author Choi Kang Hun, khchoi@e3ps.com
 */
package e3ps.common.code;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.common.query.SearchUtil;
import ext.ket.shared.log.Kogger;

/**
 * 
 */
public class GenNumberCode
{
    private String[] keys;
    private String[] values;
//    private String[] molds;
    private String[] desc;

    public void setKeyNValue(String codeType)
    {
        setKeyNValue(codeType, false);
    }

    /**
     * _flag 가 true이면 values에 (code) 이 추가된다.
     * @param codeType
     * @param _flag
     */
    public void setKeyNValue(String codeType, boolean _flag)
    {
        NumberCode numCode = null;
        int idx = 0;
        try
        {
            QuerySpec spec = new QuerySpec(NumberCode.class);
            spec.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
            SearchUtil.setOrderBy(spec, NumberCode.class, 0, "name", false);
            QueryResult result = PersistenceHelper.manager.find(spec);
            keys = new String[result.size()];
            values = new String[result.size()];
//            molds = new String[result.size()];
            desc = new String[result.size()];
            Object objs = null;

            if (_flag)
            {
                while (result.hasMoreElements())
                {
                    objs = (Object) result.nextElement();
                    numCode = (NumberCode) objs;
                    keys[idx] = numCode.getCode();
                    values[idx] = numCode.getName() + " (" + numCode.getCode() + ")";
//                    molds[idx] = numCode.getMoldCode();
                    desc[idx++] = numCode.getDescription();
                }
            }
            else
            {
                while (result.hasMoreElements())
                {
                    objs = (Object) result.nextElement();
                    numCode = (NumberCode) objs;
                    keys[idx] = numCode.getCode();
                    values[idx] = numCode.getName();
//                    molds[idx] = numCode.getMoldCode();
                    desc[idx++] = numCode.getDescription();
                }
            }
        }
        catch (QueryException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    /*
     * sortType명으로 코드를 정렬한다.
     * sortType : name | code | description | moldCode 그외의 Windchill 생성 필드명 
     */
    public void setKeyNValue(String codeType, String sortType)
    {
        if(sortType == null) sortType = "name";
        NumberCode numCode = null;
        int idx = 0;
        try
        {
            QuerySpec spec = new QuerySpec(NumberCode.class);
            spec.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
            SearchUtil.setOrderBy(spec, NumberCode.class, 0, sortType, false);
            QueryResult result = PersistenceHelper.manager.find(spec);
            keys = new String[result.size()];
            values = new String[result.size()];
//            molds = new String[result.size()];
            desc = new String[result.size()];
            Object objs = null;
            while (result.hasMoreElements())
            {
                objs = (Object) result.nextElement();
                numCode = (NumberCode) objs;
                keys[idx] = numCode.getCode();
                values[idx] = numCode.getName();
//                molds[idx] = numCode.getMoldCode();
                desc[idx++] = numCode.getDescription();
            }
        }
        catch (QueryException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }
    
    /**
     * codeType 으로 정렬한다. key : oid long
     * @param codeType
     * @param sortType
     */
    public void setOIDLongNValue(String codeType, String sortType)
    {
        if(sortType == null) sortType = "name";
        NumberCode numCode = null;
        int idx = 0;
        try
        {
            QuerySpec spec = new QuerySpec(NumberCode.class);
            spec.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
            SearchUtil.setOrderBy(spec, NumberCode.class, 0, sortType, false);
            QueryResult result = PersistenceHelper.manager.find(spec);
            keys = new String[result.size()];
            values = new String[result.size()];
//            molds = new String[result.size()];
            desc = new String[result.size()];

            while (result.hasMoreElements())
            {
                numCode = (NumberCode) result.nextElement();
                keys[idx] = ""+numCode.getPersistInfo().getObjectIdentifier().getId();
                values[idx] = numCode.getName();
//                molds[idx] = numCode.getMoldCode();
                desc[idx++] = numCode.getDescription();
            }
        }
        catch (QueryException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    /**
     * 특정 코드 타입에 코드가 code인 NumberCode를 반환
     * 
     * @param codeType
     * @param code
     * @return
     * @deprecated NumberCodeHelper.manager.getNumberCode(codeType, code) 사용
     */
    public static NumberCode getNumberCode(String codeType, String code)
    {
        return NumberCodeHelper.manager.getNumberCode(codeType, code);
    }

    public boolean checkCode(String codeType, String code)
    {
        try
        {
            QuerySpec select = new QuerySpec(NumberCode.class);
            select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
            select.appendAnd();
            select.appendWhere(new SearchCondition(NumberCode.class, "code", "=", code), new int[] { 0 });
            QueryResult result = PersistenceHelper.manager.find(select);
            while (result.hasMoreElements())
            {
                return true;
            }
        }
        catch (QueryException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        return false;
    }

    public boolean checkMoldCode(String codeType, String moldCode)
    {
        try
        {
            QuerySpec select = new QuerySpec(NumberCode.class);
            select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
            select.appendAnd();
            select.appendWhere(new SearchCondition(NumberCode.class, "moldCode", "=", moldCode), new int[] { 0 });
            QueryResult result = PersistenceHelper.manager.find(select);
            while (result.hasMoreElements())
            {
                return true;
            }
        }
        catch (QueryException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        return false;
    }

    public String[] getKeys()
    {
        return keys;
    }

    public String[] getValues()
    {
        return values;
    }

//    public String[] getMoldCode()
//    {
//        return molds;
//    }

    public String[] getDesc()
    {
        return desc;
    }
}
