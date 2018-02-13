package cc.gzvtc.common.service;

import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;


import com.github.abel533.sql.SqlMapper;

import cc.gzvtc.common.dao.BaseMapper;


/**
 * @author hzc 通用接口实现类
 */
public class BaseService<T> implements IService<T> {

	protected BaseMapper<T> mapper;

	protected SqlMapper sqlMapper;

	@Autowired
	public void setMapper(BaseMapper<T> mapper) {
		this.mapper = mapper;
	}

	@Resource(name = "sqlMapper")
	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	@Override
	public int insert(T entity) {
		return mapper.insert(entity);
	}

	@Override
	public int insertSelective(T entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	public int insertUseGeneratedKeys(T entity) {
		return mapper.insertUseGeneratedKeys(entity);
	}

	@Override
	public int delete(T entity) {
		return mapper.delete(entity);
	}

	@Override
	public int deleteByPrimaryKey(Object id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByExample(Example example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int updateNotNull(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int updateByPrimaryKey(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<T> selectByExample(Example example) {
		return mapper.selectByExample(example);
	}

	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public List<T> select(T entity) {
		return mapper.select(entity);
	}

	@Override
	public int selectCount(T entity) {
		
		return mapper.selectCount(entity);
	}

	@Override
	public int selectCountByExample(Example example) {
		return mapper.selectCountByExample(example);
	}

	@Override
	public List<Map<String, Object>> selectBySQL(String sql) {
		return sqlMapper.selectList(sql);
	}

	@Override
	public int insertBySQL(String sql) {
		return sqlMapper.insert(sql);
	}

	@Override
	public int updateBySQL(String sql) {
		return sqlMapper.update(sql);
	}

	@Override
	public int deleteBySQL(String sql) {
		return sqlMapper.delete(sql);
	}

	@Override
	public List<T> selectByExampleAndRowBounds(Example example, RowBounds rowBounds) {
		return mapper.selectByExampleAndRowBounds(example, rowBounds);
	}
	
	@Override
	public List<T> selectByRowBounds(T entity, RowBounds rowBounds) {
		return mapper.selectByRowBounds(entity, rowBounds);
	}
	
	@Override
	public List<Map<String, Object>> selectPageBySQL(String sql,int pageNo,int pageSize){
		return sqlMapper.selectList(sql + " LIMIT "+ pageNo*pageSize+","+ pageSize);
	}

	@Override
	public List<T> selectPageBySQL1(String sql, Class<T> entityClass, int pageNo, int pageSize) {
		return sqlMapper.selectList(sql + " LIMIT "+ pageNo*pageSize+","+ pageSize,entityClass);
	}
	
}
