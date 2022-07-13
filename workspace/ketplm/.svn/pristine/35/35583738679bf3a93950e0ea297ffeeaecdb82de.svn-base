package ext.ket.part.migration.dao;

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
public interface StampDaoManager {

    public abstract <T> T queryForObject(String sSql, StampRowSetStrategy<T> strategy) throws Exception;

    public abstract <T> T queryForObject(String sSql, List alBindSql, StampRowSetStrategy<T> strategy) throws Exception;

    public abstract <T> List<T> queryForList(String sSql, StampRowSetStrategy<T> strategy) throws Exception;

    public abstract <T> List<T> queryForList(String sSql, List alBindSql, StampRowSetStrategy<T> strategy) throws Exception;

    public abstract <T> Map<String, T> queryForMap(String sSql, StampRowSetStrategyMap<T> strategy) throws Exception;

    public abstract <T> Map<String, T> queryForMap(String sSql, List alBindSql, StampRowSetStrategyMap<T> strategy) throws Exception;

    public abstract int update(String sSql) throws Exception;

    public abstract int update(String sSql, List alBindSql) throws Exception;
    
    public abstract int update(String sSql, List alBindSql, boolean autoCommit) throws Exception;

    public abstract boolean queryForBool(String sSql, StampRowBoolStrategy strategy) throws Exception;

    public abstract boolean queryForBool(String sSql, List alBindSql, StampRowBoolStrategy strategy) throws Exception;

    public abstract int queryForInt(String sSql, StampRowIntStrategy strategy) throws Exception;

    public abstract int queryForInt(String sSql, List alBindSql, StampRowIntStrategy strategy) throws Exception;

    public abstract int queryForCount(String sSql) throws Exception;

    public abstract int queryForCount(String sSql, List alBindVar) throws Exception;

    public abstract ResultSet queryForRowSet(String sSql) throws Exception;

    public abstract ResultSet queryForRowSet(String sSql, List alBindSql) throws Exception;

    public abstract Vector queryForRowSetVector(String sSql) throws Exception;

    public abstract Vector queryForRowSetVector(String sSql, List alBindSql) throws Exception;

    public abstract String getPagingQuery(String query, TgPagingControl pager);

    public abstract String getOrderByQuery(String query, TgPagingControl pager);

}