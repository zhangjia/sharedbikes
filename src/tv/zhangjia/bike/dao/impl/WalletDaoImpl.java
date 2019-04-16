package tv.zhangjia.bike.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.Wallet;

public class WalletDaoImpl implements WalletDao {
	private List<Wallet> wallets = Database.WALLETS;
	private List<Bill> bills = Database.BILLS;
//	private BillDao billDao = new BillDaoImpl();
//	private AdminSettings as = Database.as;
	private OptionDao as = new OptionDaoImpl();

	@Override
	public boolean doInsert(Wallet wt) {
		wt.setId(Database.nextWalletId());
		return wallets.add(wt);
	}

	@Override
	public List<Wallet> queryAll() {
		return wallets;
	}

	@Override
	public Wallet queryByUserId(int id) {
		for (Wallet wallet : wallets) {
			if (wallet.getUserId() == id) {
				return wallet;
			}
		}

		return null;
	}

	@Override
	public int recharge(int walletId, double money) {
		BillDao billDao = new BillDaoImpl();
		for (int i = 0; i < wallets.size(); i++) {
			Wallet w = wallets.get(i);
			// System.out.println("waID" + w.getId());
			// System.out.println("wa2" + walletId);
			if (w.getId() == walletId) {
				// 增加账户余额
				w.setBalance(w.getBalance() + money);
				billDao.doInsert(queryUserId(walletId), "充值余额", money);
				return 1;
			}

		}

		return 0;
	}

	@Override
	public int becomeVIP(int userId, int month) {
		Wallet w = queryByUserId(userId);

		Date date = w.getVipDate() == null ? new Date() : w.getVipDate();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);

		if(pay(userId, month * Double.parseDouble(as.queryValue("会员价格")),"开通会员" )!= 1) {
			return -5;
		} else {
			w.setVIP(true);
			w.setVipDate(c.getTime());
			
		}
		
		return 0;
	}

	@Override
	public int pay(int userId, double money, String type) {
		BillDao billDao = new BillDaoImpl();
		Wallet pw = queryByUserId(userId);
		double coupon = pw.getCoupon();
		double balance = pw.getBalance();
		double sum = pw.getBalance() + pw.getCoupon(); //获取账户总金额
		//如果总金额不够，返回没钱
		if (sum - money < 0) {
			return -5;
			//如果红包余额不够,那么使用红包和余额一起支付
		} else if(coupon < money){
			double h = money - coupon;
			pw.setCoupon(0);
			pw.setBalance(pw.getBalance() - h);
			billDao.doInsert(queryUserId(pw.getId()), type, -money);
			return 1;
		} else {
			//如果红包余额够，只扣红包的钱
			pw.setCoupon(coupon - money);
			billDao.doInsert(queryUserId(pw.getId()), type, -money);
			return 1;
		}
	}

	@Override
	public int queryUserId(int walletId) {
		for (Wallet ws : wallets) {
			if (ws.getId() == walletId) {
				return ws.getUserId();
			}
		}
		return -1;
	}
	
	

}
