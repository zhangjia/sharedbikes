package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.LeaseRecord;

public interface LeaseRecordDao {

	/**
	 * 添加记录
	 * @param lr
	 * @return
	 */
	 int doInsert(int userId, int bikeId);

	/**
	 * 查询所有租车记录
	 * @return
	 */
	 List<LeaseRecord> queryAll();

	/**
	 * 根据用户ID查询该用户的全部租车记录
	 * @param id
	 * @return
	 */
	 List<LeaseRecord> queryByUserId(int id);

	/**
	 * 根据记录ID，查询某条记录
	 * @param id
	 * @return
	 */
	 LeaseRecord queryById(int id);

	/**
	 * 根据用户的ID查询用户所有未归还的租车记录
	 * @param userid
	 * @return
	 */
	 List<LeaseRecord> queryNotReturnByUserId(int userid);
	
	/**
	 * 归还单车
	 * @param bikeId
	 * @param userId
	 * @return
	 */
	 int returnBike(int bikeId, int userId);
	
	/**
	 * 根据单车的ID返回该车辆未归还的记录ID
	 * @param bikeId
	 * @return
	 */
	 LeaseRecord queryNotReturnRecordId(int bikeId);
	
	 
	 boolean isCurrentUserLease(int userId,int bikeId);
	
	
	
}
