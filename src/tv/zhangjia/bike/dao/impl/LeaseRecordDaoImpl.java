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
	// @Override
	// public int doInsert(int userId, int bikeId) {
	// UserDao userDao = new UserDaoImpl();
	// BikeDao bikeDao = new BikeDaoImpl();
	// LocationDao locationDao = new LocationDaoImpl();
	// int s = bikeDao.bikeStatus(bikeId);
	// // 如果是11,说明状态可借
	// if (s == 11) {
	// Bike bike = bikeDao.queryById(bikeId);
	//
	// bike.setStatus(0); // 设置为借出状态
	// bike.setAmount(bike.getAmount() + 1); // 借出次数+1
	// bike.setLastLocationId(bike.getLocationId());
	// bike.setLocationId(-1); // -1为骑行状态
	// // System.out.println(bike.getLocationId());
	//
	// // 更新当前位置的信息
	//// locationDao.updateLocationBikes(bike.getLastLocationId());
	//
	// // 获取当前位置的名称
	// Location lo = locationDao.queryLocation(bike.getLastLocationId());
	//
	// // 设置路程名
	// String loName = lo.getLocation() + " ---> " + "骑行中\t";
	// // 生成借车记录
	// LeaseRecord lr = new LeaseRecord(Database.nextLeaseRecordId(), bikeId,
	// userId,
	// userDao.queryUserName(userId), new Date(), null, loName, 0, 0);
	// lrs.add(lr);
	//
	// return 1; // 借出成功
	// }
	//
	// return s;
	// // s = 10 ：单车已经被借
	// // s = 5：单车ID不存在
	// // s = -1：单车损坏
	// }
	@Override
	public int doInsert(int userId, int bikeId) {
		// UserDao userDao = new UserDaoImpl();
		BikeDao bikeDao = new BikeDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();

		Bike bike = bikeDao.queryById(bikeId);

		bike.setStatus(0); // 设置为借出状态
		bike.setAmount(bike.getAmount() + 1); // 借出次数+1
		bike.setLastLocationId(bike.getLocationId()); // 更新起点
		bike.setLocationId(-1); // -1为骑行状态

		// 设置路程名
		Location lo = locationDao.queryLocation(bike.getLastLocationId());
		String journey = lo.getLocationName() + " ---> " + "骑行中\t";
		// 生成借车记录
		// LeaseRecord lr = new LeaseRecord(Database.nextLeaseRecordId(), bikeId,
		// userId, userDao.queryUserName(userId),
		// new Date(), null, loName, 0, 0);
		String sql = "INSERT INTO lease_record VALUES(seq_lease_record.nextval,?,?,sysdate,null,?,0.0,0L)";
		return executeUpdate(sql, bikeId, userId, journey);

	}

	/**
	 * 查询所有的租车记录
	 * @return
	 */
	@Override
	public List<LeaseRecord> queryAll() {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id";
		return query4BeanList(sql, LeaseRecord.class);
	}

	/**
	 * 查询单个用户的所有租车记录
	 * @param id 用户id
	 * @return 该用户的所有租车记录
	 */
	@Override
	public List<LeaseRecord> queryByUserId(int id) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id AND users.id = ?";
		return query4BeanList(sql, LeaseRecord.class, id);
	}

	/**
	 * 根据账单ID查询单条记录
	 * @param id 要查询的账单id
	 * @return 该条账单的信息
	 */
	@Override
	public LeaseRecord queryById(int id) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id AND lease_record.id = ?";
		return query4Bean(sql, LeaseRecord.class, id);
	}

	/**
	 * 查询某个用户所有未归还的车辆
	 * @param userId
	 * @return
	 */
	@Override
	public List<LeaseRecord> queryNotReturnByUserId(int userId) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id AND users.id = ? AND lease_record.return_time IS NULL";
		return query4BeanList(sql, LeaseRecord.class, userId);
	}

	/**
	 * 归还单车
	 * @param bikeId 要归还的单车id
	 * @param userId 用户id
	 * @return 归还成功返回1，归还失败返回0，账户余额不足返回-5
	 */
	// @Override
	// public int returnBike(int bikeId, int userId) {
	// UserDao userDao = new UserDaoImpl();
	// BikeDao bikeDao = new BikeDaoImpl();
	// WalletDao walletDao = new WalletDaoImpl();
	// LocationDao locationDao = new LocationDaoImpl();
	// int s = bikeDao.bikeStatus(bikeId);
	// Wallet w = walletDao.queryByUserId(userId);
	// if (s == 10) {
	// // 获取要归还的单车
	// Bike bike = bikeDao.queryById(bikeId);
	// // 根据BikeID找到这辆车的骑行记录的ID
	//
	// // 根据骑行记录的ID返回这条记录信息
	// LeaseRecord lr = queryNotReturnRecordId(bikeId);
	// if (lr.getUserId() == userId) {
	// Date returnTime = new Date();
	// // 计算骑行时间（秒）
	// Date lendTime = lr.getLeaseTime();
	// long second = (returnTime.getTime() - lendTime.getTime()) / 1000;
	// // 根据单车类型查找售价
	// // double price = bike.getType().equals("助力车") ? as.getbBikePrice() :
	// // as.getaBikePrice();
	// double price = bike.getType().equals("助力车") ?
	// Double.parseDouble(as.queryValue("助力车"))
	// : Double.parseDouble(as.queryValue("脚蹬车"));
	//
	// // double discount = w.isVIP() ? as.getDiscount() : 1;
	// double discount = w.isVIP() ? Double.parseDouble(as.queryValue("折扣")) : 1;
	// // 计算消费金额
	// double cost = second * price * discount;
	// // 支付
	// int i = walletDao.pay(userId, cost, "租车");
	//
	// // 如果没钱，返回-5
	// if (i != 1) {
	// return -5;
	// }
	//
	// // 更改记录消费金额
	// lr.setCost(cost);
	// // 更改归还时间
	// lr.setReturnTime(returnTime);
	// // 添加骑行时间
	// lr.setTime(second);
	// // G: User user = queryUserByUserId(userId);
	// User user = userDao.queryByUserId(userId);
	// user.setCyclingTime(user.getCyclingTime() + second);
	//
	// // 更改车辆状态
	// bike.setStatus(1);
	// // 获取单车当前位置的ID
	// int startLocationId = bike.getLastLocationId();
	// // 获取单车当前位置的名字
	// String start = locationDao.queryLocationName(startLocationId);
	//
	// // 获取归还的时候新的位置
	// Location nowLocation = locationDao.randomLocation(bike.getLastLocationId(),
	// bikeId, lr.getId());
	// // 获取新的位置名字
	// String end = nowLocation.getLocation();
	// // 1. 修改单车的位置
	//
	// bike.setLocationId(nowLocation.getId());
	//
	// // 2. 修改骑行记录中的行程
	//
	// lr.setLocations(start + " ---> " + end);
	// // 更新位置
	// // locationDao.updateLocationBikes(bike.getLocationId());
	// // locationDao.updateLocationBikes(bike.getLastLocationId());
	//
	// return 1;// 归还成功
	//
	// }
	// return 0; // 不是该用户
	// } else {
	// return s;
	// }
	//
	// // 0：不是该用户
	// // -5 : 余额没钱了
	// // s = 11：没有被借出
	// // s = 5 ：没有该ID
	// // s = -1：该车已经损坏
	//
	// }
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
		//随机生成归还位置
		bike.setLastLocationId(locationDao.randomLocation(bikeId).getId()); 
		int w = bikeDao.doUpdate(bike);
		
		
		// 应支付价格= 售价 * 折扣 （会员才享受折扣）
		double price = bikeDao.queryBikePrice(bikeId) * (wt.getIsVIP() ? Double.valueOf(optionDao.queryValue("折扣")) : 1);
		// 计算好了归还时间，起始位置，消费金额，骑行时间

		String sql = "SELECT id,bike_id,user_id,lease_time,sysdate return_time,(SELECT location_name FROM location,bike WHERE bike.location_id = location.id AND bike.id = ?)||' ---> '||(SELECT location_name FROM location,bike WHERE bike.lastlocation_id = location.id AND bike.id = ?) journey,((sysdate - lease_time) * 24 * 60 * 60) * ? cost,((sysdate - lease_time) * 24 * 60 * 60) time FROM lease_record  WHERE bike_id = ? AND return_time IS NULL;";
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

		// 0：不是该用户
		// -5 : 余额没钱了
		// s = 11：没有被借出
		// s = 5 ：没有该ID
		// s = -1：该车已经损坏

	}

	/**
	 * 根据车辆id和车辆状态，确认唯一的订单记录
	 * @param bikeId
	 * @return
	 */
	@Override
	public LeaseRecord queryNotReturnRecordId(int bikeId) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id AND users.id = 1 AND lease_record.return_time IS NULL AND lease_record.bike_id = ?";
		return query4Bean(sql, LeaseRecord.class, bikeId);
	}

	@Override
	public boolean isCurrentUserLease(int userId, int bikeId) {
		// 根据骑行记录的ID返回这条记录信息
		LeaseRecord lr = queryNotReturnRecordId(bikeId);

		// 如果该车是该用户借的，并且不是未还状态
		if (lr != null && lr.getUserId() == userId) {
			return true;
		}
		return false;
	}

	@Override
	public int doUpdate(LeaseRecord lr) {
		String sql = "UPDATE lease_record SET(return_time=?,journey=?,cost=?,time=?) WHERE id = ?";
		return executeUpdate(sql,lr.getBikeId(),lr.getUserId(),lr.getJourney(),lr.getCost(),lr.getTime(),lr.getId());
	}



}
