package tv.zhangjia.bike.dao;

import java.io.IOException;
import java.util.List;

import tv.zhangjia.bike.entity.Bill;

public interface BillDao {
	/**
	 * 插入记录
	 * @param bill
	 * @return 添加成功返回1，添加失败返回0
	 */
	int doInsert(Bill bill);

	/**
	 * 查询所有账单（包括被用户删除的）
	 * @return 所有的账单
	 */
	List<Bill> queryAll();

	/**
	 * 根据账单id查询单条记录
	 * @param billId 账单ID
	 * @return 对应的记录
	 */
	Bill queryByBillId(int billId);

	/**
	 * 根据用户id查询指定用户的所有账单
	 * @param userId 用户id
	 * @return 该用户产生的所有账单
	 */
	List<Bill> queryUserBill(int userId);

	/**
	 * 导出记录到本地
	 * @return 导出成功返回true，导出失败返回false
	 * @throws IOException
	 */
	boolean export() throws IOException;

}
