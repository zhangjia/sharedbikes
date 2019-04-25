package tv.zhangjia.bike.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.CommonDao;

public class WalletDaoImpl extends CommonDao implements WalletDao {

	/**
	 * 添加钱包
	 * @param wt 钱包对象
	 * @return 返回1插入成功，返回0插入失败
	 */
	@Override
	public int doInsert(Wallet wt) {
		String sql = "INSERT INTO wallet VALUES(seq_wallet.nextval,?,?,?,?,?)";
		return executeUpdate(sql, wt.getUserId(), wt.getBalance(), wt.getCoupon(), wt.getIsVIP(), wt.getVipDate());
	}

	/**
	 * 查询全部钱包
	 * @return 所有的钱包信息
	 * @see tv.zhangjia.bike.dao.WalletDao#queryAll()
	 */
	@Override
	public List<Wallet> queryAll() {
		String sql = "SELECT * FROM wallet";
		return query4BeanList(sql, Wallet.class);
	}

	/**
	 * 根据用户ID返回她的钱包信息
	 * @param id 用户ID
	 * @return 用户的钱包
	 */
	@Override
	public Wallet queryByUserId(int id) {
		String sql = "SELECT * FROM wallet WHERE user_Id = ?";
		return query4Bean(sql, Wallet.class, id);
	}

	/**
	 * 更新钱包
	 * @param wallet 要更新的钱包
	 * @return 更新成功返回1，更新失败返回0
	 */
	@Override
	public int doUpdate(Wallet wallet) {
		String sql = "UPDATE wallet SET balance=?,coupon=?,is_vip = ?,vipDate=? WHERE id =?";
		// return executeUpdate(sql, wallet.getBalance(), wallet.getCoupon(),
		// wallet.getIsVIP() ? 1 : 0,
		// wallet.getVipDate() == null ? null : new Date(wallet.getVipDate().getTime())
		// // 保证传进来的时间不变和空指针异常
		// , wallet.getId());
		return executeUpdate(sql, wallet.getBalance(), wallet.getCoupon(), wallet.getIsVIP() ? 1 : 0,
				wallet.getVipDate() == null ? null : wallet.getVipDate() // 保证传进来的时间不变和空指针异常
				, wallet.getId());
	}

	/**
	 * 注册奖励
	 * @param user1Id  注册的用户ID
	 * @param wallet1Id 注册的用户钱包ID
	 * @param user2Id 推荐人的用户ID
	 * @return 添加成功返回1，添加失败返回0
	 * @see tv.zhangjia.bike.dao.WalletDao#awardByregister(int, int, int)
	 */
	@Override
	public int awardByregister(int user1Id, int wallet1Id, int user2Id) {
		UserDao userDao = new UserDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		BillDao billDao = new BillDaoImpl();
		// 判断推荐人是否存在
		User user2 = userDao.queryByUserId(user2Id);
		if (user2 == null) {
			return -1; // 不存在该推荐人
		}
		// 获取注册用户的钱包
		Wallet wallet1 = walletDao.queryByUserId(user1Id);
		// 获取推荐人的钱包
		Wallet wallet2 = walletDao.queryByUserId(user2Id);
		// 给注册用户添加奖励
		wallet1.setCoupon(wallet1.getCoupon() + 100);
		// 给推荐人添加奖励
		wallet2.setCoupon(wallet2.getCoupon() + 100);
		// 更新注册用户和推荐人的钱包
		int x = walletDao.doUpdate(wallet1);
		int y = walletDao.doUpdate(wallet2);

		Bill bill = new Bill("注册奖励", user1Id, 100.0);
		Bill bill2 = new Bill("推广奖励", user2Id, 100.0);
		int x2 = billDao.doInsert(bill);
		int y2 = billDao.doInsert(bill2);
		return x * x2 * y * y2;
	}

	//报修奖励
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
		doInsert(new Bill(userId, "报修奖励", 100, new Date(System.currentTimeMillis())));

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

		// 1.使用FIle确定要操作的文件

	}

	/**
	 * 充值
	 * 
	 * @param userId
	 *            要充值的用户ID
	 * @param money
	 *            要充值的金额
	 * @return 返回1充值成功，返回0充值失败
	 * @see tv.zhangjia.bike.dao.WalletDao#recharge(int, double)
	 */
	@Override
	public int recharge(int userId, double money) {
		BillDao billDao = new BillDaoImpl();
		// 获取当前用户的钱包
		Wallet w = queryByUserId(userId);
		// 添加账户余额
		w.setBalance(w.getBalance() + money);
		// 更新钱包信息
		int x = doUpdate(w);
		// 生成一条记录对象
		Bill bill = new Bill("充值余额", userId, money);
		int y = billDao.doInsert(bill);
		return x * y;
	}

	/**
	 * 开通VIP
	 * 
	 * @param userId
	 *            开通VIP的用户ID
	 * @param month
	 *            要开通的月份
	 * @return
	 * @see tv.zhangjia.bike.dao.WalletDao#becomeVIP(int, int)
	 */
	@Override
	public int becomeVIP(int userId, int month) {
		Wallet wallet = queryByUserId(userId);
		OptionDao op = new OptionDaoImpl();
		Calendar c = Calendar.getInstance();
		c.setTime(beforDate);
		c.add(Calendar.MONTH, month);

		Date d = new Date(c.getTimeInMillis());
		if (pay(userId, month * Double.parseDouble(op.queryValue("会员价格")), "开通会员") != 1) {
			return -5;
		} else {

			String sql = "UPDATE wallet SET is_vip = 1,vipDate=? WHERE user_Id = ?";

			return executeUpdate(sql, d, userId);
		}
	}

	@Override
	public int pay(int userId, double money, String type) {
		BillDao billDao = new BillDaoImpl();
		Wallet pw = queryByUserId(userId);
		double coupon = pw.getCoupon();
		double balance = pw.getBalance();
		double sum = pw.getBalance() + pw.getCoupon(); // 获取账户总金额
		// 如果总金额不够，返回没钱
		if (sum - money < 0) {
			return -5;
			// 如果红包余额不够,那么使用红包和余额一起支付
		} else if (coupon < money) {
			double h = money - coupon;
			pw.setCoupon(0);
			pw.setBalance(pw.getBalance() - h);
			// billDao.doInsert(queryUserId(pw.getId()), type, -money);
			billDao.doInsert(new Bill(userId, type, -money, new Date(System.currentTimeMillis())));
			return doUpdate(pw);
		} else {
			// 如果红包余额够，只扣红包的钱
			pw.setCoupon(coupon - money);
			// billDao.doInsert(queryUserId(pw.getId()), type, -money);
			billDao.doInsert(new Bill(userId, type, -money, new Date(System.currentTimeMillis())));
			return doUpdate(pw);
		}
	}

}
