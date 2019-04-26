package tv.zhangjia.bike.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.util.CommonDao;

public class BillDaoImpl extends CommonDao implements BillDao {
	/**
	 * 插入记录
	 * @param bill
	 * @return
	 */
	@Override
	public int doInsert(Bill bill) {
		String sql = "INSERT INTO bill VALUES(seq_bill.nextval,?,?,sysdate,?)";
		return executeUpdate(sql, bill.getUserId(),bill.getBillName(),bill.getMoney());
	}
	
	/**
	 * 查询所有账单
	 * @return
	 */
	@Override
	public List<Bill> queryAll() {
		String sql = "SELECT bill.*, users.username FROM bill,users WHERE bill.user_id = users.id;";
		return query4BeanList(sql,Bill.class);
	}

	/**
	 * 根据账单id查询单条记录
	 * @param billId 账单ID
	 * @return 对应的记录
	 */
	@Override
	public Bill queryByBillId(int billId) {
		String sql = "SELECT bill.*, users.username FROM bill,users WHERE bill.user_id = users.id AND bill.id=?";
		return query4Bean(sql, Bill.class,billId);
	}


	/**
	 * 根据用户id查询指定用户的所有账单
	 * @param userId 用户id
	 * @return 该用户产生的所有账单
	 */
	@Override
	public List<Bill> queryUserBill(int userId) {
		String sql = "SELECT bill.*, users.username FROM bill,users WHERE bill.user_id = users.id AND bill.user_id = ?";
		return query4BeanList(sql, Bill.class,userId);
	}
	
	/**
	 * 导出记录到本地
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean export() throws IOException {
		List<Bill> record = queryAll();

		File file = new File("E:" + File.separator + "bike" + File.separator + "Bill" + File.separator + "bill.txt");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		Writer writer = new FileWriter(file, true);
		String str = "编号\t" + "用户名\t" + "账单名称\t\t" + "余额变化\t" + "产生时间\t" + "\r\n";
		String str1 = "";

		for (Bill bill : record) {
			str1 += bill.toString();
		}
		// 3.2开始写入
		writer.write(str);
		writer.write(str1);

		// 4释放资源
		writer.close();

		return true;


	}


}
