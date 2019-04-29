package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Repair;

public interface RepairDao {

	/**
	 * 添加报修记录
	 * @param repair 报修对象
	 * @return 添加成功返回1，添加失败返回0
	 */
	int doInsert(Repair repair);

	/**
	 * 处理记录
	 * @param repair
	 * @return 处理成功返回1，处理失败返回0
	 */

	int doUpdate(Repair repair);

	/**
	 * 查询所有的报修记录
	 * @return 所有的报修记录
	 */
	List<Repair> queryAll();

	/**
	 * 根据单车iD查询单条记录
	 * @return  该条记录
	 */
	Repair queryByBikeId(int bikeId);

	/**
	 * 判断单车是否已经报修
	 * @param bikeId 单车ID
	 * @return 已经报修返回true，否则返回false
	 */
	boolean isRepair(int bikeId);
}
