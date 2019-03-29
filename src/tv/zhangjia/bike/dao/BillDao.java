package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bill;

public interface BillDao {

	/**
	 * 插入账单记录
	 * @param userId
	 * @return
	 */
	boolean doInsert(int userId, String event, double money);

	/**
	 * 查询所有的账单记录
	 * @return
	 */
	List<Bill> queryAll();

	/**
	 * 根据账单ID返回账单记录
	 * @param billId
	 * @return
	 */
	Bill queryByBillId(int billId);

	
	/**
	 * 根据该用户的ID，返回该用户的全部账单记录
	 * @param userId
	 * @return
	 */
	List<Bill> queryUserBill(int userId);

	
	
	

}
