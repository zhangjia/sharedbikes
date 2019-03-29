package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Wallet;

public interface WalletDao {
	boolean doInsert(Wallet wt);
	
	List<Wallet> queryAll();
	
	Wallet queryByUserId(int id);
	
	int recharge(int walletId,double money);
}
