/**
 * @(#) NormalTreeHelper.java Copyright (c) e3ps. All rights reserverd
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.common.impl;

import java.util.ArrayList;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;

public class TreeHelper
{
    public static final TreeHelper manager = new TreeHelper();

    protected TreeHelper()
    {}

    public ArrayList getTopList(Class treeClass)
    {
        ArrayList returndata = new ArrayList();
        try
        {
            QuerySpec spec = getTopQuerySpec(treeClass);
            QueryResult qr = PersistenceHelper.manager.find(spec);
            while (qr.hasMoreElements())
            {
                Object[] obj = (Object[]) qr.nextElement();
                returndata.add(obj[0]);
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
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        return returndata;
    }

    public QuerySpec getTopQuerySpec(Class treeClass) throws QueryException, WTPropertyVetoException,
            WTIntrospectionException
    {
        QuerySpec spec = new QuerySpec();
        Class mainClass = treeClass;
        int idx = spec.addClassList(mainClass, true);
        spec.appendWhere(new SearchCondition(mainClass, "parentReference.key.id", "=", (long) 0), new int[] { idx });
        return spec;
    }

    public ArrayList getChildList(Class treeClass, Tree tree)
    {
        ArrayList returndata = new ArrayList();
        try
        {
            QuerySpec spec = getChildQuerySpec(treeClass, tree);
            QueryResult qr = PersistenceHelper.manager.find(spec);
            while (qr.hasMoreElements())
            {
                Object[] obj = (Object[]) qr.nextElement();
                returndata.add(obj[0]);
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
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        return returndata;
    }

    public QuerySpec getChildQuerySpec(Class treeClass, Tree tree) throws WTPropertyVetoException, QueryException,
            WTIntrospectionException
    {
        return getChildQuerySpec(treeClass, tree, null);
    }

    public ArrayList getAllChildList(Class treeClass, Tree tree, ArrayList returnlist)
    {
        return getAllChildList(treeClass, tree, null, returnlist);
    }

    public ArrayList getParentsList(Tree tree, ArrayList returnlist)
    {
        Tree parent = tree.getParent();
        if (parent != null)
        {
            returnlist.add(parent);
            getParentsList(parent, returnlist);
        }
        return returnlist;
    }

    /**
     * _tree 의 모든 하위 객체를 리턴한다.
     * @param _treeClass
     * @param _tree
     * @param _sort _tree의 필드(오름차순 정렬)
     * @param _returnlist new ArrayList() 로 무조건 세팅
     * @return
     */
    public ArrayList getAllChildList(Class _treeClass, Tree _tree, String _sort, ArrayList _returnlist)
    {
        try
        {
            QuerySpec spec = getChildQuerySpec(_treeClass, _tree, _sort);
            QueryResult qr = PersistenceHelper.manager.find(spec);
            while (qr.hasMoreElements())
            {
                Object[] obj = (Object[]) qr.nextElement();
                _returnlist.add(obj[0]);
                getAllChildList(_treeClass, (Tree) obj[0], _sort, _returnlist);
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
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        return _returnlist;
    }

    /**
     * 하위 객체의 검색을 위한 QuerySpec 을 반환
     * @param _treeClass
     * @param _tree
     * @param _sort _tree의 필드(오름차순 정렬)
     * @return
     * @throws WTPropertyVetoException
     * @throws QueryException
     * @throws WTIntrospectionException
     */
    public QuerySpec getChildQuerySpec(Class _treeClass, Tree _tree, String _sort) throws WTPropertyVetoException,
            QueryException, WTIntrospectionException
    {
        QuerySpec spec = new QuerySpec();
        int idx = spec.addClassList(_treeClass, true);
        
        SearchUtil.appendEQUAL(spec, _treeClass, "parentReference.key.id", CommonUtil.getOIDLongValue(_tree), idx);
        
        if(_sort == null)
            SearchUtil.setOrderBy(spec, _treeClass, idx, "thePersistInfo.createStamp", "createDate", true);
        else
            SearchUtil.setOrderBy(spec, _treeClass, idx, _sort, false);
        return spec;
    }
}

/* $Log: not supported by cvs2svn $
/* Revision 1.1  2009/08/11 04:16:22  administrator
/* Init Source
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.1  2009/02/25 01:25:20  smkim
/* 최초 작성
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.2  2007/10/17 04:54:54  sjhan
/* *** empty log message ***
/*
/* Revision 1.1.1.1  2007/04/17 01:21:26  administrator
/* no message
/*
/* Revision 1.1  2006/05/09 02:35:04  shchoi
/* *** empty log message ***
/*
/* Revision 1.1  2006/05/09 01:24:04  shchoi
/* *** empty log message ***
/*
/* Revision 1.3  2006/02/10 06:43:37  shchoi
/* getAllChildList 에 정렬 기능 추가
/**/
