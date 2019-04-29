package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Location;

public interface LocationDao {
	/**
	 * 查询所有的位置信息
	 * @return 所有的位置信息
	 */
	List<Location> queryAll();

	/**
	 * 根据位置id查询单条位置信息
	 * @param id 位置id
	 * @return 该位置信息
	 */
	Location queryLocation(int id);

	/**
	 * 随机生成一个不同于当前位置的位置
	 * @param locationId 当前位置id
	 * @return 随机生成的位置
	 */
	Location randomLocation(int loctionId);

	/**
	 * 根据位置Id查询位置名词
	 * @param locationId 位置ID
	 * @return 位置名称
	 */
	String queryLocationName(int locationId);

	/**
	 * 调度建议
	 * @return 所有的建议信息
	 */
	List<String> dispatch();

	/**
	 * 根据位置ID查询当前位置下的所有车辆
	 * @param locationId 位置ID
	 * @return 当前位置下的所有车辆
	 */
	List<Bike> queryBikesByLocation(int locationId);

	/**
	 * 随机生成一个用户的位置
	 * @return 生成的随机位置
	 */
	Location randomUserLocation();
}
