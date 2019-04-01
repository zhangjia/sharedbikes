package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;

public class BillDaoImpl implements BillDao{
	private List<Bill> bills = Database.BILLS;
	private List<Wallet> wallets = Database.WALLETS;
	private List<User> users = Database.USERS;
	@Override
	public boolean doInsert(int userId, String event, double money) {
		Bill bill  = new Bill(Database.nextBillId(),event,userId,new Date(),money);
		return bills.add(bill);
	}

	@Override
	public List<Bill> queryAll() {
		return bills;
	}

	@Override
	public Bill queryByBillId(int billId) {
		for (Bill bill : bills) {
			if(bill.getId() == billId) {
				return bill;
			}
		}
		return null;
	}

	@Override
	public List<Bill> queryUserBill(int userId) {
		List<Bill> newBill = new ArrayList<>();
		for (Bill bill : bills) {
			if(bill.getUserId() == userId) {
				newBill.add(bill);
			}
		} 
		return newBill;
	}

	@Override
	public int awardByregister(int user1Id, int wallet1Id, int user2Id) {
		UserDao userDao = new UserDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		User user2 = userDao.queryByUserId(user2Id);
		if(user2 == null) {
//		G:	if(queryUser(user2Id) == null) {
			return -1; //不存在该用户
		} 
//	G:	Wallet wallet1 = queryWallet(wallet1Id);
//	G:	Wallet wallet2 = queryWallet(user2.getWalletID());
		Wallet wallet1 = walletDao.queryByUserId(user1Id);
		Wallet wallet2 = walletDao.queryByUserId(user2Id);
		
		
		wallet1.setCoupon(wallet1.getCoupon() + 5);
		wallet2.setCoupon(wallet2.getCoupon() + 5);
		
		doInsert(user1Id,"注册奖励",5);
		doInsert(user2Id,"推广奖励",5);
		
		return 0;
	}

	@Override
	public int awardByBike(int userId, int walletId) {
		UserDao userDao = new UserDaoImpl();
		if(userDao.queryByUserId(userId) == null) {
//		G:	if(queryUser(userId) == null) {
			return -1; //不存在该用户
		} 
		WalletDao walletDao = new WalletDaoImpl();
		Wallet wallet = walletDao.queryByUserId(userId);
//	G:	Wallet wallet = queryWallet(walletId);

		
		wallet.setCoupon(5);
		doInsert(userId,"报修奖励",5);
		
		return 0;
	}
	
	
//	private User queryUser(int userId) {
//		for (User user : users) {
//			if(user.getId() == userId) {
//				return user;
//			}
//		}
//		return null;
//	}
//	
//	private Wallet queryWallet(int walletId) {
//		for (Wallet wallet : wallets) {
//			if(wallet.getId() == walletId) {
//				return wallet;
//			}
//		}
//		return null;
//	}
}
