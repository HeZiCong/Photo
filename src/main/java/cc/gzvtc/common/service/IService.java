package cc.gzvtc.common.service;

import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

/**
 * @author hzc 通用接口
 */
public interface IService<T> {

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 */
	public int delete(T entity);

	public int deleteByPrimaryKey(Object id);

	public int deleteByExample(Example example);

	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public int updateNotNull(T entity);

	public int updateByPrimaryKey(T entity);

	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	public int insert(T entity);

	public int insertSelective(T entity);

	public int insertUseGeneratedKeys(T entity);

	/**
	 * 查询
	 * 
	 * @param key
	 * @return
	 */
	public T selectByPrimaryKey(Object key);

	public List<T> select(T entity);

	public List<T> selectByExample(Example example);

	public List<T> selectAll();

	public int selectCount(T entity);

	public int selectCountByExample(Example example);

	/**
	 * 通过sql增删改查
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> selectBySQL(String sql);

	public int insertBySQL(String sql);

	public int updateBySQL(String sql);

	public int deleteBySQL(String sql);
	
	
	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @return
	 */
	public List<T> selectByExampleAndRowBounds(Example example,RowBounds rowBounds);
	public List<T> selectByRowBounds(T entity, RowBounds rowBounds);
	/**
	 * 分页查询 
	 * @param sql sql语句
	 * @param pageNo 当前页数 从0开始
	 * @param pageSize 分页大小
	 * @return List Map
	 */
	public List<Map<String, Object>> selectPageBySQL(String sql,int pageNo,int pageSize);
	
	/**
	 * 分页查询 
	 * @param sql sql语句
	 * @param pageNo 当前页数 从0开始
	 * @param pageSize 分页大小
	 * @return List T
	*/
	
	public List<T> selectPageBySQL1(String sql,Class<T> entityClass, int pageNo, int pageSize);
}
