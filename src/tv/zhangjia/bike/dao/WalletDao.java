package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Wallet;

public interface WalletDao {
	
	int doInsert(Wallet wt);

	List<Wallet> queryAll();
	
	int doUpdate(Wallet wallet);
	
	Wallet queryByUserId(int id);

//	int queryUserId(int walletId);
	int awardByregister(int user1Id,int wallet1Id,int user2Id);
	
	int awardByBike(int userId, int walletId);
    int recharge(int userId, double money);

	int becomeVIP(int userId, int month);

	int pay(int userId,double money,String type);
	
	
	
	
}
