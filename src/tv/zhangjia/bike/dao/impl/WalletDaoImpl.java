package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Wallet;

public class WalletDaoImpl implements WalletDao {
	private List<Wallet> wallets = Database.WALLETS;

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
			if(wallet.getUserId() == id) {
			return wallet;
			}
		}
			
		return null;
	}

	@Override
	public int recharge(int walletId,double money) {
		for (int i = 0; i < wallets.size(); i++) {
			Wallet w = wallets.get(i);
//			System.out.println("waID" + w.getId());
//			System.out.println("wa2" + walletId);
			if(w.getId() == walletId) {
				w.setBalance(w.getBalance() + money);
				return 1;
			}
			
		}
		
		return 0;
	}

	
}
