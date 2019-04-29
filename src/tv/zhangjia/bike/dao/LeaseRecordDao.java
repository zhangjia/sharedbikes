package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.LeaseRecord;

public interface LeaseRecordDao {
	/**
	 * 添加借车记录（借车功能）
	 * @param userId 借车的用户ID
	 * @param bikeId 借的车
	 * @return 借车成功返回1，借车失败返回0
	 */
	int doInsert(int userId, int bikeId);

	/**
	 * 查询所有的租车记录（包括用户删除的)
	 * @return 所有的租车记录
	 */
	List<LeaseRecord> queryAll();

	/**
	 * 查询单个用户的所有租车记录
	 * @param id 用户id
	 * @return 该用户的所有租车记录
	 */
	List<LeaseRecord> queryByUserId(int id);

	/**
	 * 根据账单ID查询单条记录
	 * @param id 要查询的账单id
	 * @return 该条账单的信息
	 */
	LeaseRecord queryById(int id);

	/**
	 * 查询某个用户所有未归还的车辆
	 * @param userId
	 * @return 该用户当前所有未归还的车辆
	 */
	List<LeaseRecord> queryNotReturnByUserId(int userid);

	/**
	 * 归还单车
	 * @param bikeId 要归还的单车id
	 * @param userId 用户id
	 * @return 归还成功返回1，归还失败返回0，账户余额不足返回-5
	 */
	int returnBike(int bikeId, int userId);

	/**
	 * 根据车辆id和车辆状态，确认唯一的订单记录
	 * @param bikeId
	 * @return 该条记录
	 */
	LeaseRecord queryNotReturnRecordId(int bikeId);

	/**
	 * 删除记录（假删除）
	 * @param id 记录ID
	 * @return 删除成功返回1，删除失败返回0
	 */
	int doUpdate(LeaseRecord lr);

	/**
	 * 删除记录（假删除）
	 * @param id 记录ID
	 * @return 删除成功返回1，删除失败返回0
	 */
	int doDelele(int id);

}
