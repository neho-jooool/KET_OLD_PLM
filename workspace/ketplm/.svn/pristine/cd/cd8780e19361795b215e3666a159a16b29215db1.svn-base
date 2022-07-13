package ext.ket.shared.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import ext.ket.shared.dto.BaseDTO;

/**
 * CommonDao 구현 클래스
 * 
 * @클래스명 : CommonDaoImpl
 * @작성자 : sw775.park
 * @작성일 : 2014. 5. 8.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Repository("commonDao")
public class CommonDaoImpl extends SqlSessionDaoSupport implements CommonDao {
    @Inject
    protected void setSuperSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
	super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public void insert(String statementName, Object obj) {
	this.getSqlSession().insert(statementName, obj);
    }

    @Override
    public int update(String statementName, Object obj) {
	return getSqlSession().update(statementName, obj);
    }

    @Override
    public int delete(String statementName, Object obj) {
	return getSqlSession().delete(statementName, obj);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List find(String statementName) {
	return getSqlSession().selectList(statementName);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List find(String statementName, Map paramMap) {
	return getSqlSession().selectList(statementName, paramMap);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List find(String statementName, Object obj) {
	return getSqlSession().selectList(statementName, obj);
    }

    @Override
    public Object get(String statementName, Serializable id) {
	return getSqlSession().selectOne(statementName, id);
    }

    @Override
    public Object get(String statementName) {
	return getSqlSession().selectOne(statementName);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object get(String statementName, Map pkMap) {
	return getSqlSession().selectOne(statementName, pkMap);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int deleteAll(String statementName, Map paramMap) {
	return getSqlSession().delete(statementName, paramMap);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List findPaging(String statementName, Map paramMap, int pageIndex, int pageSize) {
	int offSet = pageSize * (pageIndex - 1) + 1;
	int limit = offSet + pageSize;
	paramMap.put("offSet", offSet);
	paramMap.put("limit", limit);
	return getSqlSession().selectList(statementName, paramMap);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List findPaging(String statementName, Object obj, int pageIndex, int pageSize) {
	int offSet = pageSize * (pageIndex - 1) + 1;
	int limit = offSet + pageSize;
	((BaseDTO) obj).setOffSet(offSet);
	((BaseDTO) obj).setLimit(limit);
	return getSqlSession().selectList(statementName, obj);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int getTotalRecordCount(String statementName, Map paramMap) {
	return getSqlSession().selectList(statementName, paramMap).size();
    }

    @Override
    public int getTotalRecordCount(String statementName, Object map) {
	return getSqlSession().selectList(statementName, map).size();
    }

}
