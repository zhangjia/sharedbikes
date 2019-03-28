package tv.zhangjia.bike.dao;

import java.util.List;

public interface RecordDao<T> {
	/**
	 * 添加记录
	 * @param t
	 * @return
	 */
	boolean addRecord(T t);
	
	/**
	 * 查询所有记录,供管理员查看
	 * @return 所有的租赁记录
	 */
	List<T> queryAll();
	
	/**
	 * 查询用户记录，供用户查看
	 * @param id 用户id
	 * @return 该用户的所有记录
	 */
	List<T> queryByUserId(int id);
	
	/**
	 * 查询单个记录ID
	 * @param id 用户id
	 * @return 该用户的所有记录
	 */
	T queryById(int id);
	
	
}
