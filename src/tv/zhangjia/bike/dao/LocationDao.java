package tv.zhangjia.bike.dao;

import java.util.List;

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
	 * 添加位置
	 * @param id
	 * @return
	 */
	int doInsert(int id);
	
	/**
	 * 更新位置
	 * @param id
	 * @return
	 */
	int doUpdate(int id);
	
	
	/**
	 * 删除位置
	 * @param id
	 * @return
	 */
	int doDelete(int id);
	
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
	
	/*
	int changeBikeLocation(int bikeId,int locationId, int oldLocationId);
	
	
	int addBikeLocation(int bikeId, int locationId);*/
	
	boolean updateLocationBikes(int locationId);
	
	
	public boolean deleteLocationBikes(int locationId,int bikeID);
}
