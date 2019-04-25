package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;

public class LeaseRecordDaoImpl implements LeaseRecordDao {
//	private AdminSettings as = Database.as;
	private OptionDao as = new OptionDaoImpl();
//	private List<LeaseRecord> lrs = Database.LEASERECORDS;
//	private List<User> users = Database.USERS;
	// 借车的时候，生成借车记录时传入用户名
	// TODO 创建根据用户ID返回用户名的方法
	// private UserDao userDao = new UserDaoImpl();

	// 借车的时候，查看车的状态，根据Id返回该车
	// private BikeDao bikeDao = new BikeDaoImpl();

	// 根据id返回钱包
	// private WalletDao walletDao = new WalletDaoImpl();

	// 根据locationId返回location
	// private LocationDao locationDao = new LocationDaoImpl();

	@Override
	public int doInsert(int userId, int bikeId) {
		UserDao userDao = new UserDaoImpl();
		BikeDao bikeDao = new BikeDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();
		int s = bikeDao.bikeStatus(bikeId);
		// 如果是11,说明状态可借
		if (s == 11) {
			Bike bike = bikeDao.queryById(bikeId);

			bike.setStatus(0); // 设置为借出状态
			bike.setAmount(bike.getAmount() + 1); // 借出次数+1
			bike.setLastLocationId(bike.getLocationId());
			bike.setLocationId(-1); // -1为骑行状态
			// System.out.println(bike.getLocationId());

			// 更新当前位置的信息
//			locationDao.updateLocationBikes(bike.getLastLocationId());

			// 获取当前位置的名称
			Location lo = locationDao.queryLocation(bike.getLastLocationId());

			// 设置路程名
			String loName = lo.getLocation() + " ---> " + "骑行中\t";
			// 生成借车记录
			LeaseRecord lr = new LeaseRecord(Database.nextLeaseRecordId(), bikeId, userId,
					userDao.queryUserName(userId), new Date(), null, loName, 0, 0);
			lrs.add(lr);

			return 1; // 借出成功
		}

		return s;
		// s = 10 ：单车已经被借
		// s = 5：单车ID不存在
		// s = -1：单车损坏
	}

	@Override
	public List<LeaseRecord> queryAll() {
		return lrs;
	}

	@Override
	public List<LeaseRecord> queryByUserId(int id) {
		List<LeaseRecord> ulr = new ArrayList<>();
		for (LeaseRecord leaseRecord : lrs) {
			if (leaseRecord.getUserId() == id) {
				ulr.add(leaseRecord);
			}
		}
		return ulr;
	}

	@Override
	public LeaseRecord queryById(int id) {
		for (LeaseRecord leaseRecord : lrs) {
			if (leaseRecord.getId() == id) {
				return leaseRecord;
			}
		}
		return null;
	}

	@Override
	public List<LeaseRecord> queryNotReturnByUserId(int userId) {
		List<LeaseRecord> records = new ArrayList<>();
		for (LeaseRecord record : this.lrs) {
			if (record.getUserId() == userId && record.getReturnTime() == null) {
				records.add(record);
			}
		}
		return records;
	}

	@Override
	public int returnBike(int bikeId, int userId) {
		UserDao userDao = new UserDaoImpl();
		BikeDao bikeDao = new BikeDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();
		int s = bikeDao.bikeStatus(bikeId);
		Wallet w = walletDao.queryByUserId(userId);
		if (s == 10) {
			// 获取要归还的单车
			Bike bike = bikeDao.queryById(bikeId);
			// 根据BikeID找到这辆车的骑行记录的ID

			// 根据骑行记录的ID返回这条记录信息
			LeaseRecord lr = queryNotReturnRecordId(bikeId);
			if (lr.getUserId() == userId) {
				Date returnTime = new Date();
				// 计算骑行时间（秒）
				Date lendTime = lr.getLeaseTime();
				long second = (returnTime.getTime() - lendTime.getTime()) / 1000;
				// 根据单车类型查找售价
//				double price = bike.getType().equals("助力车") ? as.getbBikePrice() : as.getaBikePrice();
				double price = bike.getType().equals("助力车") ? Double.parseDouble(as.queryValue("助力车")) : Double.parseDouble(as.queryValue("脚蹬车"));

//				double discount = w.isVIP() ? as.getDiscount() : 1;
				double discount = w.isVIP() ? Double.parseDouble(as.queryValue("折扣")): 1;
				// 计算消费金额
				double cost = second * price * discount;
				// 支付
				int i = walletDao.pay(userId, cost, "租车");

				// 如果没钱，返回-5
				if (i != 1) {
					return -5;
				}

				// 更改记录消费金额
				lr.setCost(cost);
				// 更改归还时间
				lr.setReturnTime(returnTime);
				// 添加骑行时间
				lr.setTime(second);
				// G: User user = queryUserByUserId(userId);
				User user = userDao.queryByUserId(userId);
				user.setCyclingTime(user.getCyclingTime() + second);

				// 更改车辆状态
				bike.setStatus(1);
				// 获取单车当前位置的ID
				int startLocationId = bike.getLastLocationId();
				// 获取单车当前位置的名字
				String start = locationDao.queryLocationName(startLocationId);

				// 获取归还的时候新的位置
				Location nowLocation = locationDao.randomLocation(bike.getLastLocationId(), bikeId, lr.getId());
				// 获取新的位置名字
				String end = nowLocation.getLocation();
				// 1. 修改单车的位置

				bike.setLocationId(nowLocation.getId());

				// 2. 修改骑行记录中的行程

				lr.setLocations(start + " ---> " + end);
				// 更新位置
//				locationDao.updateLocationBikes(bike.getLocationId());
				// locationDao.updateLocationBikes(bike.getLastLocationId());

				return 1;// 归还成功

			}
			return 0; // 不是该用户
		} else {
			return s;
		}

		// 0：不是该用户
		// -5 : 余额没钱了
		// s = 11：没有被借出
		// s = 5 ：没有该ID
		// s = -1：该车已经损坏

	}

	@Override
	public LeaseRecord queryNotReturnRecordId(int bikeId) {
		for (LeaseRecord lr : lrs) {
			// 因为一个车子可能产生多条记录，但是未归还的记录只有一条，所以通过这两个条件可以返回唯一的记录ID
			if (lr.getBikeId() == bikeId && lr.getReturnTime() == null) {
				return lr;
			}
		}
		return null;
	}

	@Override
	public boolean isCurrentUserLease(int userId, int bikeId) {
		// 根据骑行记录的ID返回这条记录信息
		LeaseRecord lr = queryNotReturnRecordId(bikeId);
		
		//如果该车是该用户借的，并且不是未还状态
		if ( lr != null && lr.getUserId() == userId ) {
			return true;
		}
		return false;
	}

	/*
	 * private User queryUserByUserId(int userId) { for (User user : users) {
	 * if(user.getId() == userId) { return user; } } return null; }
	 */

}
