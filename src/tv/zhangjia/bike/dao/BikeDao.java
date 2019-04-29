package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bike;

/**
 * Bike接口
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.dao	
 * @ClassName	BikeDao	
 * @author	ZhangJia
 * @Version	
 * @date	2019年3月26日 下午5:37:20
 */
public interface BikeDao {

	/**
	 * 添加单车
	 * @param bike 单车对象
	 * @return 添加成功返回1，添加失败返回0
	 */
	int doInsert(Bike bike);

	/**
	 * 删除单车
	 * @param id 要删除的单车ID
	 * @return 删除成功返回1，删除失败返回0
	 */
	int doDelete(int id);

	/**
	 * 更新单车
	 * @param id 要更新的单车ID
	 * @return 更新成功返回1，更新失败返回0
	 */
	int doUpdate(Bike bike);

	/**
	 * 查询所有单车
	 * @return 所有单车
	 */
	List<Bike> queryAll();

	/**
	 * 根据单车Id返回单车信息
	 * @param id 单车ID
	 * @return 单车信息
	 */
	Bike queryById(int id);

	/**
	 * 根据单车ID查询其价格
	 * @param bikeId 要查询的单车
	 * @return 该单车的价格
	 */
	double queryBikePrice(int bikeId);

	/**
	 * 查询未删除的所有单车
	 * @return 所有单车
	 */
	List<Bike> queryAllByNotDelete();
}
