package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Wallet;

public interface WalletDao {
	/**
	 * 添加钱包
	 * @param wt 钱包对象
	 * @return 返回1插入成功，返回0插入失败
	 */
	int doInsert(Wallet wt);

	/**
	 * 查询全部钱包
	 * @return 所有的钱包信息
	 */
	List<Wallet> queryAll();

	/**
	 * 更新钱包
	 * @param wallet 要更新的钱包
	 * @return 更新成功返回1，更新失败返回0
	 */
	int doUpdate(Wallet wallet);

	/**
	 * 根据用户ID返回她的钱包信息
	 * @param id 用户ID
	 * @return 用户的钱包
	 */
	Wallet queryByUserId(int id);

	/**
	 * 注册奖励
	 * @param user1Id  注册的用户ID
	 * @param wallet1Id 注册的用户钱包ID
	 * @param user2Id 推荐人的用户ID
	 * @return 添加成功返回1，添加失败返回0
	 */
	int awardByregister(int user1Id, int wallet1Id, int user2Id);

	/**
	 * 报修奖励
	 * @param userId 报修的用户
	 * @param walletId 报修用户的钱包
	 * @return 奖励成功返回1，奖励失败返回0
	 */
	int awardByBike(int userId, int walletId);

	/**
	 * 充值
	 * @param userId 要充值的用户ID
	 * @param money 要充值的金额
	 * @return 返回1充值成功，返回0充值失败
	 */
	int recharge(int userId, double money);

	/**
	 * 开通VIP
	 * @param userId 开通VIP的用户ID
	 * @param month 要开通的月份
	 * @return
	 */
	int becomeVIP(int userId, int month);

	/**
	 * 支付
	 * @param userId 用户ID
	 * @param money 支付金额
	 * @param type  支付类型
	 * @return 支付成功返回1，支付失败返回0,余额不足返回-5
	 */
	int pay(int userId, double money, String type);

}
