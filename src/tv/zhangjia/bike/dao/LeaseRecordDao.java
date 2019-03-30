package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.LeaseRecord;

public interface LeaseRecordDao {

	/**
	 * 添加记录
	 * @param lr
	 * @return
	 */
	public int doInsert(int userId, int bikeId);

	/**
	 * 查询所有租车记录
	 * @return
	 */
	public List<LeaseRecord> queryAll();

	/**
	 * 根据用户ID查询该用户的全部租车记录
	 * @param id
	 * @return
	 */
	public List<LeaseRecord> queryByUserId(int id);

	/**
	 * 根据记录ID，查询某条记录
	 * @param id
	 * @return
	 */
	public LeaseRecord queryById(int id);

	/**
	 * 根据用户的ID查询用户所有未归还的租车记录
	 * @param userid
	 * @return
	 */
	public List<LeaseRecord> queryNotReturnByUserId(int userid);
	
	/**
	 * 归还单车
	 * @param bikeId
	 * @param userId
	 * @return
	 */
	public int returnBike(int bikeId, int userId);
	
	/**
	 * 根据单车的ID返回该车辆未归还的记录ID
	 * @param bikeId
	 * @return
	 */
	public int queryNotReturnRecordId(int bikeId);
	
	
}
