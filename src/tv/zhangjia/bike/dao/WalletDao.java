package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Wallet;

public interface WalletDao {
	/**
	 * 新建钱包
	 * @param wt
	 * @return
	 */
	boolean doInsert(Wallet wt);
	/**
	 * 查询全部钱包状态
	 * @return
	 */
	List<Wallet> queryAll();
	
	/**
	 * 根据用户Id查询该用户的钱包状态
	 * @param id
	 * @return
	 */
	Wallet queryByUserId(int id);
	
	/**
	 * 根据钱包ID查询用户ID
	 * @param walletId
	 * @return
	 */
	int queryUserId(int walletId);
	
	/**
	 * 充值功能
	 * @param walletId
	 * @param money
	 * @return
	 */
	int recharge(int walletId, double money);
	
	/**
	 * 开通VIP功能
	 * @param userId
	 * @return
	 */
	int becomeVIP(int userId, int month);
	
	/**
	 * 扣费
	 * @param money
	 * @return
	 */
	int pay(int userId,double money,String type);
	
	
}
