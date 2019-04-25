package tv.zhangjia.bike.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.CommonDao;

public class BillDaoImpl extends CommonDao implements BillDao {
//	private List<Bill> bills = Database.BILLS;
//	private List<Wallet> wallets = Database.WALLETS;
//	private List<User> users = Database.USERS;

//	@Override
//	public boolean doInsert(int userId, String event, double money) {
//		Bill bill = new Bill(Database.nextBillId(), event, userId, new Date(), money);
//		return bills.add(bill);
//	}
	@Override
	public int doInsert(Bill bill) {
		String sql = "INSERT INTO bill VALUES(seq_bill.nextval,?,?,?,?)";
		return executeUpdate(sql, bill.getUserId(),bill.getBillName(),bill.getBillDate(),bill.getMoney());
	}

	@Override
	public List<Bill> queryAll() {
		String sql = "SELECT * FROM bill";
		return query4BeanList(sql);
	}

//	@Override
//	public Bill queryByBillId(int billId) {
//		for (Bill bill : bills) {
//			if (bill.getId() == billId) {
//				return bill;
//			}
//		}
//		return null;
//	}
	@Override
	public Bill queryByBillId(int billId) {
		String sql = "SELECT * FROM bill WHERE id = ?";
		return query4Bean(sql, billId);
	}
//
//	@Override
//	public List<Bill> queryUserBill(int userId) {
//		List<Bill> newBill = new ArrayList<>();
//		for (Bill bill : bills) {
//			if (bill.getUserId() == userId) {
//				newBill.add(bill);
//			}
//		}
//		return newBill;
//	}

	@Override
	public List<Bill> queryUserBill(int userId) {
		String sql = "SELECT * FROM bill WHERE user_id = ?";
		return query4BeanList(sql, userId);
	}
//
//	@Override
//	public int awardByregister(int user1Id, int wallet1Id, int user2Id) {
//		UserDao userDao = new UserDaoImpl();
//		WalletDao walletDao = new WalletDaoImpl();
//		User user2 = userDao.queryByUserId(user2Id);
//		if (user2 == null) {
//			// G: if(queryUser(user2Id) == null) {
//			return -1; // 不存在该用户
//		}
//		// G: Wallet wallet1 = queryWallet(wallet1Id);
//		// G: Wallet wallet2 = queryWallet(user2.getWalletID());
//		Wallet wallet1 = walletDao.queryByUserId(user1Id);
//		Wallet wallet2 = walletDao.queryByUserId(user2Id);
//
//		wallet1.setCoupon(wallet1.getCoupon() + 100);
//		wallet2.setCoupon(wallet2.getCoupon() + 100);
//
//		doInsert(user1Id, "注册奖励", 100);
//		doInsert(user2Id, "推广奖励", 100);
//
//		return 0;
//	}
	@Override
	public int awardByregister(int user1Id, int wallet1Id, int user2Id) {
		UserDao userDao = new UserDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		User user2 = userDao.queryByUserId(user2Id);
		if (user2 == null) {
			// G: if(queryUser(user2Id) == null) {
			return -1; // 不存在该用户
		}
		// G: Wallet wallet1 = queryWallet(wallet1Id);
		// G: Wallet wallet2 = queryWallet(user2.getWalletID());
		Wallet wallet1 = walletDao.queryByUserId(user1Id);
		Wallet wallet2 = walletDao.queryByUserId(user2Id);

		wallet1.setCoupon(wallet1.getCoupon() + 100);
		wallet2.setCoupon(wallet2.getCoupon() + 100);
		walletDao.doUpdate(wallet1);
		walletDao.doUpdate(wallet2);

		
		doInsert( new Bill(user1Id, "注册奖励", 100,new Date(System.currentTimeMillis())));
		doInsert( new Bill(user2Id, "推广奖励", 100,new Date(System.currentTimeMillis())));

		return 0;
	}



//	@Override
//	public int awardByBike(int userId, int walletId) {
//		UserDao userDao = new UserDaoImpl();
//		if (userDao.queryByUserId(userId) == null) {
//			// G: if(queryUser(userId) == null) {
//			return -1; // 不存在该用户
//		}
//		WalletDao walletDao = new WalletDaoImpl();
//		Wallet wallet = walletDao.queryByUserId(userId);
//		// G: Wallet wallet = queryWallet(walletId);
//
//		wallet.setCoupon(100 + wallet.getCoupon());
//		doInsert(userId, "报修奖励", 100);
//
//		return 0;
//	}
	@Override
	public int awardByBike(int userId, int walletId) {
		UserDao userDao = new UserDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		
		if (userDao.queryByUserId(userId) == null) {
			// G: if(queryUser(userId) == null) {
			return -1; // 不存在该用户
		}
		Wallet wallet = walletDao.queryByUserId(userId);
		// G: Wallet wallet = queryWallet(walletId);

		wallet.setCoupon(100 + wallet.getCoupon());
		walletDao.doUpdate(wallet);
		doInsert(new Bill(userId, "报修奖励", 100,new Date(System.currentTimeMillis())));

		return 0;
	}

	// private User queryUser(int userId) {
	// for (User user : users) {
	// if(user.getId() == userId) {
	// return user;
	// }
	// }
	// return null;
	// }
	//
	// private Wallet queryWallet(int walletId) {
	// for (Wallet wallet : wallets) {
	// if(wallet.getId() == walletId) {
	// return wallet;
	// }
	// }
	// return null;
	// }

	@Override
	public boolean export() throws IOException {
		List<Bill> record = Database.BILLS;

		File file = new File(
				"E:" + File.separator + "bike" + File.separator + "Bill" + File.separator + "bill.txt");
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

		// 1.使用FIle确定要操作的文件

	}

	@Override
	public Bill getBeanFromResultSet(ResultSet rs) throws SQLException {
		
		return new Bill(rs.getInt(1),rs.getString(3),rs.getInt(2),rs.getDate(4),rs.getDouble(5));
	}
}
