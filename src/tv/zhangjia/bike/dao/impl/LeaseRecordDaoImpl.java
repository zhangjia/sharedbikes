package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.CommonDao;

public class LeaseRecordDaoImpl extends CommonDao implements LeaseRecordDao {

	/**
	 * 添加借车记录（借车功能）
	 * @param userId 借车的用户ID
	 * @param bikeId 借的车
	 * @return 借车成功返回1，借车失败返回0
	 */

	@Override
	public int doInsert(int userId, int bikeId) {
		BikeDao bikeDao = new BikeDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();

		Bike bike = bikeDao.queryById(bikeId);

		bike.setStatus(0); // 设置为借出状态
		bike.setAmount(bike.getAmount() + 1); // 借出次数+1
		bike.setLastLocationId(bike.getLocationId()); // 更新起点
		// bike.setLocationId(-1); // -1为骑行状态

		// 设置路程名
		Location lo = locationDao.queryLocation(bike.getLocationId());
		String journey = lo.getLocationName() + " ---> " + "骑行中\t";
		// 生成借车记录

		String sql = "INSERT INTO lease_record VALUES(seq_lease_record.nextval,?,?,sysdate,null,?,0.0,0.0,1)";
		int x = bikeDao.doUpdate(bike);
		int y = executeUpdate(sql, bikeId, userId, journey);
		return x * y;

	}

	/**
	 * 查询所有的租车记录（包括用户删除的)
	 * @return 所有的租车记录
	 */
	@Override
	public List<LeaseRecord> queryAll() {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.user_id";
		return query4BeanList(sql, LeaseRecord.class);
	}

	/**
	 * 查询单个用户的所有租车记录
	 * @param id 用户id
	 * @return 该用户的所有租车记录
	 */
	@Override
	public List<LeaseRecord> queryByUserId(int id) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.user_id AND users.id = ? AND delete_status =1";
		return query4BeanList(sql, LeaseRecord.class, id);
	}

	/**
	 * 根据账单ID查询单条记录
	 * @param id 要查询的账单id
	 * @return 该条账单的信息
	 */
	@Override
	public LeaseRecord queryById(int id) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.user_id AND lease_record.id = ?";
		return query4Bean(sql, LeaseRecord.class, id);
	}

	/**
	 * 查询某个用户所有未归还的车辆
	 * @param userId
	 * @return 该用户当前所有未归还的车辆
	 */
	@Override
	public List<LeaseRecord> queryNotReturnByUserId(int userId) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.user_id AND users.id = ? AND lease_record.return_time IS NULL";
		return query4BeanList(sql, LeaseRecord.class, userId);
	}

	/**
	 * 归还单车
	 * @param bikeId 要归还的单车id
	 * @param userId 用户id
	 * @return 归还成功返回1，归还失败返回0，账户余额不足返回-5
	 */

	@Override
	public int returnBike(int bikeId, int userId) {
		UserDao userDao = new UserDaoImpl();
		BikeDao bikeDao = new BikeDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();
		OptionDao optionDao = new OptionDaoImpl();
		Wallet wt = walletDao.queryByUserId(userId);
		/**
		 * 1.记录
		 * 		修改归还时间
		 * 		修改起始位置
		 * 		修改消费金额
		 * 		修改骑行时间
		 * 2.钱包
		 * 		扣费
		 * 		3. 账单
		 * 		   生成账单记录
		 * 4. 用户信息
		 * 		修改用户总骑行时间
		 * 5. 单车
		 * 		修改单车的终点位置
		 */

		// 更改车辆状态
		Bike bike = bikeDao.queryById(bikeId);
		bike.setStatus(1);
		// 随机生成归还位置
		bike.setLocationId(locationDao.randomLocation(bikeId).getId());
		int w = bikeDao.doUpdate(bike);

		// 应支付价格= 售价 * 折扣 （会员才享受折扣）

		double price = bikeDao.queryBikePrice(bikeId)
				* (wt.getIsVIP() ? Double.valueOf(optionDao.queryValue("折扣")) : 1);
		// 计算好了归还时间，起始位置，消费金额，骑行时间
		String sql = "SELECT id,bike_id,user_id,lease_time,sysdate return_time,(SELECT location_name FROM location,bike WHERE bike.lastlocation_id = location.id AND bike.id = ?)||' ---> '||(SELECT location_name FROM location,bike WHERE bike.location_id = location.id AND bike.id = ?) journey,((sysdate - lease_time) * 24 * 60 * 60) * ? cost,((sysdate - lease_time) * 24 * 60 * 60) time FROM lease_record  WHERE bike_id = ? AND return_time IS NULL";
		LeaseRecord lr = query4Bean(sql, LeaseRecord.class, bikeId, bikeId, price, bikeId);

		// 如果账户余额不足，直接返回-5
		int x = walletDao.pay(userId, lr.getCost(), "租车");
		if (x != 1) {
			return -5;
		}
		// 修改用户总骑行时间
		User user = userDao.queryByUserId(userId);
		user.setCyclingTime(user.getCyclingTime() + lr.getTime());
		int y = userDao.doUpdate(user);

		int z = doUpdate(lr);
		return w * x * y * z;
	}

	/**
	 * 根据车辆id和车辆状态，确认唯一的订单记录
	 * @param bikeId
	 * @return 该条记录
	 */
	@Override
	public LeaseRecord queryNotReturnRecordId(int bikeId) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.user_id AND lease_record.return_time IS NULL AND lease_record.bike_id = ?";
		return query4Bean(sql, LeaseRecord.class, bikeId);
	}

	/**
	 * 更新记录
	 * @param lr 要更新的记录对象
	 * @return 更新成功返回1，更是失败返回0
	 */
	@Override
	public int doUpdate(LeaseRecord lr) {
		String sql = "UPDATE lease_record SET return_time=sysdate,journey=?,cost=?,time=? WHERE id = ?";
		return executeUpdate(sql, lr.getJourney(), lr.getCost(), lr.getTime(), lr.getId());
	}

	/**
	 * 删除记录（假删除）
	 * @param id 记录ID
	 * @return 删除成功返回1，删除失败返回0
	 */
	@Override
	public int doDelele(int id) {
		String sql = "UPDATE lease_record SET delete_status = 0 WHERE id = ?";
		return executeUpdate(sql, id);
	}

}
