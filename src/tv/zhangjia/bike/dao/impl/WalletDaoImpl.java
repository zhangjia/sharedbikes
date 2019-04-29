package tv.zhangjia.bike.dao.impl;

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
	 */
	@Override
	public List<Wallet> queryAll() {
		String sql = "SELECT wallet.*,username FROM wallet,users WHERE wallet.user_id = users.id ORDER BY wallet.id";
		return query4BeanList(sql, Wallet.class);
	}

	/**
	 * 根据用户ID返回她的钱包信息
	 * @param id 用户ID
	 * @return 用户的钱包
	 */
	@Override
	public Wallet queryByUserId(int id) {
		String sql = "SELECT wallet.*,username FROM wallet,users WHERE wallet.user_id = users.id AND user_Id = ?";
		return query4Bean(sql, Wallet.class, id);
	}

	/**
	 * 更新钱包
	 * @param wallet 要更新的钱包
	 * @return 更新成功返回1，更新失败返回0
	 */
	@Override
	public int doUpdate(Wallet wallet) {
		String sql = "UPDATE wallet SET balance=?,coupon=?,is_vip = ?,vip_date=? WHERE id =?";
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

	/**
	 * 报修奖励
	 * @param userId 报修的用户
	 * @param walletId 报修用户的钱包
	 * @return 奖励成功返回1，奖励失败返回0
	 */
	@Override
	public int awardByBike(int userId, int walletId) {
		BillDao billDao = new BillDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		// 获取报修用户的钱包
		Wallet wallet = walletDao.queryByUserId(userId);
		// 添加优惠券
		wallet.setCoupon(100 + wallet.getCoupon());
		// 更新用户钱包
		int x = walletDao.doUpdate(wallet);
		Bill bill = new Bill("报修奖励", userId, 100.0);
		int y = billDao.doInsert(bill);

		return x * y;
	}

	/**
	 * 充值
	 * @param userId 要充值的用户ID
	 * @param money 要充值的金额
	 * @return 返回1充值成功，返回0充值失败
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
	 * @param userId 开通VIP的用户ID
	 * @param month 要开通的月份
	 * @return
	 */
	@Override
	public int becomeVIP(int userId, int month) {
		OptionDao op = new OptionDaoImpl();
		// 如果余额不足,返回-5
		if (pay(userId, +(month * Double.parseDouble(op.queryValue("会员价格"))), "开通会员") != 1) {
			return -5;
		} else {
			// 如果没开通VIP,在当前时间的基础上加month，如果已经开通vip时间，在到期时间基础上加month
			String sql = "UPDATE wallet SET is_vip = 1,vip_date= ADD_MONTHS(NVL(vip_date,sysdate),?) WHERE user_Id = ?";
			return executeUpdate(sql, month, userId);
		}
	}

	/**
	 * 支付
	 * @param userId 用户ID
	 * @param money 支付金额
	 * @param type  支付类型
	 * @return 支付成功返回1，支付失败返回0,余额不足返回-5
	 */
	@Override
	public int pay(int userId, double money, String type) {
		BillDao billDao = new BillDaoImpl();
		Wallet pw = queryByUserId(userId);
		// 获取当前用户的优惠券余额
		double coupon = pw.getCoupon();
		// 获取当前用户的账户余额
		double balance = pw.getBalance();
		// 获取当前用户的账户总额
		double sum = coupon + balance; // 获取账户总金额

		// 如果总金额不够，返回没钱
		if (sum - money < 0) {
			return -5;
			// 如果红包余额不够,那么使用红包和余额一起支付
		} else if (coupon < money) {
			// 计算需要从余额拿出多少钱支付
			double h = money - coupon;
			// 将优惠券清零
			pw.setCoupon(0.0);
			// 从余额扣除
			pw.setBalance(pw.getBalance() - h);

		} else {
			// 如果红包余额够，只扣红包的钱
			pw.setCoupon(coupon - money);
		}

		// 更新当前用户钱包
		int x = doUpdate(pw);
		// 生成记录
		if (x == 1) {
			Bill bill = new Bill(type, userId, -money);
			int y = billDao.doInsert(bill);
			return x * y;
		} else {
			return 0;
		}
	}

}
