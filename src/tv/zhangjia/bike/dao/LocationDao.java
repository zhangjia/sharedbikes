package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Location;

public interface LocationDao {
	/**
	 * 查询所有的位置信息
	 * @return
	 */
	List<Location> queryAll();
	
	/**
	 * 查询单个位置信息
	 * @param id
	 * @return
	 */
	Location queryLocation(int id);


	
	/**
	 * 归还单车时，模拟随机生成位置
	 * @return
	 */
	Location randomLocation(int loctionId, int bikeId,int leaseRecordId);
	
	/**
	 * 根据位置ID，查询Id名字
	 * @param locationId
	 * @return
	 */
	String queryLocationName(int locationId);
	
	
	/**
	 * 调度
	 * @return
	 */
	List<String> dispatch();
	
	List<Bike> queryBikesByLocation(int locationId);
	
//	
//	boolean updateLocationBikes(int locationId);
//	
//	
//	 boolean deleteLocationBikes(int locationId,int bikeID);
	
	Location randomUserLocation();
}
