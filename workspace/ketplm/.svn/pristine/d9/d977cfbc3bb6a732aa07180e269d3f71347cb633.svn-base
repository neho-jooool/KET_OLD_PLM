package e3ps.common.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import e3ps.common.treegrid.TgPagingControl;

/**
 * 
 * @클래스명 : DaoManager
 * @작성자 : tklee
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public interface DaoManager {

    public abstract <T> T queryForObject(String sSql, RowSetStrategy<T> strategy) throws Exception;

    public abstract <T> T queryForObject(String sSql, List alBindSql, RowSetStrategy<T> strategy) throws Exception;

    public abstract <T> List<T> queryForList(String sSql, RowSetStrategy<T> strategy) throws Exception;

    public abstract <T> List<T> queryForList(String sSql, List alBindSql, RowSetStrategy<T> strategy) throws Exception;

    public abstract <T> Map<String, T> queryForMap(String sSql, RowSetStrategyMap<T> strategy) throws Exception;

    public abstract <T> Map<String, T> queryForMap(String sSql, List alBindSql, RowSetStrategyMap<T> strategy) throws Exception;

    public abstract int update(String sSql) throws Exception;

    public abstract int update(String sSql, List alBindSql) throws Exception;

    public abstract boolean queryForBool(String sSql, RowBoolStrategy strategy) throws Exception;

    public abstract boolean queryForBool(String sSql, List alBindSql, RowBoolStrategy strategy) throws Exception;

    public abstract int queryForInt(String sSql, RowIntStrategy strategy) throws Exception;

    public abstract int queryForInt(String sSql, List alBindSql, RowIntStrategy strategy) throws Exception;

    public abstract int queryForCount(String sSql) throws Exception;

    public abstract int queryForCount(String sSql, List alBindVar) throws Exception;

    public abstract ResultSet queryForRowSet(String sSql) throws Exception;

    public abstract ResultSet queryForRowSet(String sSql, List alBindSql) throws Exception;

    public abstract Vector queryForRowSetVector(String sSql) throws Exception;

    public abstract Vector queryForRowSetVector(String sSql, List alBindSql) throws Exception;

    public abstract String getPagingQuery(String query, TgPagingControl pager);
    
    public abstract String getPagingQuery(String query, int currentPage, int pageSize);
    
    public abstract String getPagingQuery(String query, int currentPage, int pageSize, List<Object> alBindSql);

    public abstract String getOrderByQuery(String query, TgPagingControl pager);

}