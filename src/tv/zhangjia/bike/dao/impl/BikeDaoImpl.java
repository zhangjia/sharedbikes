package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Location;

/**
 * BikeDao接口的实现类
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.dao.impl	
 * @ClassName	BikeDaoImpl	
 * @author	ZhangJia
 * @Version	
 * @date	2019年3月26日 下午4:47:51
 */
public class BikeDaoImpl implements BikeDao{
	private List<Bike> bikes = Database.BIKES;
	
	//用于添加车辆的时候，位置的车辆总数+1
	//TODO 创建queryLocation方法
	private LocationDao locationDao = new LocationDaoImpl();
	@Override
	public boolean doInsert(Bike bike) {
		bike.setId(Database.nextBikeId());
//		locationDao.addBikeLocation(bike.getId(), bike.getLocationId());
		bikes.add(bike);
		
		
		return locationDao.updateLocationBikes(bike.getLocationId());
	}

	@Override
	public boolean doDelete(int id) {
		Bike d = new Bike();
		for (Bike bike : bikes) {
			if(bike.getId() == id) {
				d = bike;
				bikes.remove(d);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean doUpdate(Bike bike) {
		for (int i = 0; i < bikes.size(); i++) {
			if(bikes.get(i).getId() == bike.getId()) {
				bikes.set(i, bike);
				return true;
			}
			
		}
		
		return false;
	}

	@Override
	public List<Bike> queryAll() {
		return bikes;
	}

	@Override
	public Bike queryById(int id) {
		for (Bike bike : bikes) {
			if(bike.getId() == id) {
				return bike;
			}
		}
		return null;
	}
//
//	@Override
//	public int doLease(int id,User user) {
//		for (Bike bike : bikes) {
//		
//			if(bike.getId() == id ) {
//				if (bike.getStatus() == 1) {
//					
//					bike.setStatus(0); //设置为借出状态
//					bike.setAmount(bike.getAmount() + 1); //借出次数+1
//					
//					leaseRecordDao.addRecord(new LeaseRecord(id,user.getId(),user.getUsername(),"0","0","1"));
//					
// 					return 1; 	//如果有该ID，并且状态是可借,返回1代表可借
//				}
//				return -1; //-1 代表不可借
//			} 
//		}
//		return 0; //0代表ID不存在
//	}
//
//	@Override
//	public int doReturn(int bikeId, int userId, int recordId) {
//		for (Bike bike : bikes) {
//			if(bike.getId() == id ) {
//				if (bike.getStatus() == 0) {  //如果是借出状态
//					bike.setStatus(1); //设置为归还状态
//					leaseRecordDao.queryById(bike.getId()).setReturnTime("9");
// 					return 1; 	//如果有该ID，并且状态是不可借,返回1代表归还
//				}
//				return -1; //-1 代表不可还
//			} 
//		}
//		return 0; //0代表ID不存在
//	}

	@Override
	public int bikeStatus(int bikeId) {
		
//		11：可借
//		10：不可借
//		5：没有该Id
//		-1：损坏
		
		for (Bike bike : bikes) {
			if(bike.getId() == bikeId) {
				//如果状态为可借，返回11
				if(bike.getStatus() == 1) {
					return 11;
					//如果状态为不可借，返回10
				} else if(bike.getStatus() == 0) {
					return 10;
				} else if(bike.getStatus() == -1) {
					return -1; 
				}
			}
		}
		return 5; //没有此ID
	}

}
