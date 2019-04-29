package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Options;

public interface OptionDao {
	/**
	 * 查询所有的设置
	 * @return 所有的设置
	 */
	List<Options> queryAlloptions();

	/**
	 * 根据设置名称查找对应的值
	 * @param name 设置名
	 * @return 该设置所对应的值
	 */
	String queryValue(String name);

	/**
	 * 更新某条设置
	 * @param value 设置名
	 * @param name 设置值
	 * @return 更新成功返回1，更新失败返回0
	 */
	int doUpdate(String value, String name);
}
