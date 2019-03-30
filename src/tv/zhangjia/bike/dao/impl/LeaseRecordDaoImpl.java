package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.AdminSettings;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.Wallet;

public class LeaseRecordDaoImpl implements LeaseRecordDao {
	private AdminSettings as = Database.as;
	private List<LeaseRecord> lrs = Database.LEASERECORDS;
	// 借车的时候，生成借车记录时传入用户名
	//TODO 创建根据用户ID返回用户名的方法
	private UserDao userDao = new UserDaoImpl();
	
	// 借车的时候，查看车的状态，根据Id返回该车
	private BikeDao bikeDao = new BikeDaoImpl();
	
	//根据id返回钱包
	private WalletDao walletDao = new WalletDaoImpl();
	
	//根据locationId返回location
	private LocationDao locationDao = new LocationDaoImpl();

	@Override
	public int doInsert(int userId, int bikeId) {
		int s = bikeDao.bikeStatus(bikeId);
		if (s == 11) {
			Bike bike = bikeDao.queryById(bikeId);
//			locationDao.changeBikeLocation(bikeId,-1, bike.getLocationId());
			
			bike.setStatus(0); // 设置为借出状态
			bike.setAmount(bike.getAmount() + 1); // 借出次数+1
			bike.setLastLocationId(bike.getLocationId());
			bike.setLocationId(-1); //-1为骑行状态
			locationDao.updateLocationBikes(bike.getLastLocationId());
			//生成借车记录
			LeaseRecord lr = new LeaseRecord(Database.nextLeaseRecordId(), bikeId, userId,
					userDao.queryUserName(userId), new Date(), null, "骑行中",0);
			lrs.add(lr);
			
			
			return 1; // 借出成功
		}

		return s; // 返回10 ：单车已经被借，返回-1：单车ID不存在
		// 1:借出成功
		// s：
		// 10:该车辆已经被借走了
		// -1：ID不存在
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

		int s = bikeDao.bikeStatus(bikeId);
		Wallet w = walletDao.queryByUserId(userId);
		if (s == 10) {
			Bike bike = bikeDao.queryById(bikeId);
			// 找到需要变更的记录ID
			int recordId = queryNotReturnRecordId(bikeId);
			// 返回该记录
			LeaseRecord lr = queryById(recordId);
			if (lr.getUserId() == userId) {
				Date returnTime = new Date();
				
				//计算租赁时长（秒）
				Date lendTime = lr.getLeaseTime();
				long second = (returnTime.getTime() - lendTime.getTime()) / 1000;
				//根据单车类型查找售价
				System.out.println("车价格" + as.getaBikePrice());
				
				double price = bike.getType().equals("助力车") ? as.getbBikePrice() : as.getaBikePrice();
				
				//计算消费金额
				int i = walletDao.pay(userId, second * price,"租车");
				System.out.println("i" + i);
				if(i != 1) {
					return -5;
				}
				
//				locationDao.changeBikeLocation(bike.getId(), bike.getLocationId(), -1);
				//更改记录消费金额
				lr.setCost(second * price);
				//更改记录时间
				lr.setReturnTime(returnTime);
				//更改车辆状态
				bike.setStatus(1);
				//获取单车当前位置的ID
				int startLocationId = bike.getLastLocationId();
				//获取单车当前位置的名字
				String start = locationDao.queryLocationName(startLocationId);
				
				//获取新的位置
				Location nowLocation =locationDao.randomLocation(bike.getLastLocationId(), bikeId, lr.getId());
				//获取新的位置名字
				String end = nowLocation.getLocation();
				//1.  修改单车的位置
				
						
				bike.setLocationId(nowLocation.getId());
				
				//2.  修改租赁记录的位置
				
				lr.setLocations(start + " ---> " + end);
				
				
				locationDao.updateLocationBikes(bike.getLocationId());
//				locationDao.updateLocationBikes(bike.getLastLocationId());
				
				return 1;// 归还成功

			}
			return 0;
		} else {
			return s;
		}

		// 1：归还成功
		// 0：不是该用户
		// s：
		// -1：没有此ID
		// 11：此单车没有被借
	}

	@Override
	public int queryNotReturnRecordId(int bikeId) {
		for (LeaseRecord lr : lrs) {
			// 因为一个车子可能产生多条记录，但是未归还的记录只有一条，所以通过这两个条件可以返回唯一的记录ID
			if (lr.getBikeId() == bikeId && lr.getReturnTime() == null) {
				return lr.getId();
			}
		}
		return -1;// 没找到返回-1
	}

}
