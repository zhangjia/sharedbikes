package tv.zhangjia.bike.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.AdminSettings;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.Wallet;

public class WalletDaoImpl implements WalletDao {
	private List<Wallet> wallets = Database.WALLETS;
	private List<Bill> bills = Database.BILLS;
	private BillDao billDao = new BillDaoImpl();
	private AdminSettings as = Database.as;

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
		for (int i = 0; i < wallets.size(); i++) {
			Wallet w = wallets.get(i);
			// System.out.println("waID" + w.getId());
			// System.out.println("wa2" + walletId);
			if (w.getId() == walletId) {
				// 增加账户余额
				w.setBalance(w.getBalance() + money);
				billDao.doInsert(queryUserId(walletId), "充值", money);
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

		if(pay(userId, month * as.getVipPrice(),"充值会员" )!= 1) {
			return -5;
		} else {
			w.setVIP(true);
			w.setVipDate(c.getTime());
			
		}
		
		return 0;
	}

	@Override
	public int pay(int userId, double money, String type) {
		Wallet pw = queryByUserId(userId);
		if (pw.getBalance() - money < 0) {
			return -5;
		} else {
			System.out.println(pw.getBalance());
			pw.setBalance(pw.getBalance() - money);
			System.out.println(pw.getBalance());
			billDao.doInsert(queryUserId(pw.getId()), type, money);
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
