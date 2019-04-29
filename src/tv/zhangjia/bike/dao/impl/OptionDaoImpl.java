package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.entity.Options;
import tv.zhangjia.bike.util.CommonDao;

public class OptionDaoImpl extends CommonDao implements OptionDao {
	/**
	 * 查询所有的设置
	 * @return 所有的设置
	 */
	@Override
	public List<Options> queryAlloptions() {
		String sql = "SELECT * FROM options";
		return query4BeanList(sql,Options.class);
	}
	
	/**
	 * 更新某条设置
	 * @param value 设置名
	 * @param name 设置值
	 * @return 更新成功返回1，更新失败返回0
	 */
	@Override
	public int doUpdate(String value,String name) {
		String sql = "UPDATE options SET value=? WHERE name=?";
		return executeUpdate(sql, value,name);
	}

	/**
	 * 根据设置名称查找对应的值
	 * @param name 设置名
	 * @return 该设置所对应的值
	 */
	@Override
	public String queryValue(String name) {
		String sql = "SELECT options.value FROM options WHERE options.name = ?";
		return query4StringData(sql, name);
	}


}
