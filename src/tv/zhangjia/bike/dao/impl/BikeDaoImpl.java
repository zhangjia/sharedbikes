package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bike;

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
	@Override
	public boolean doInsert(Bike bike) {
		bike.setId(Database.nextBikeId());
		
		return bikes.add(bike);
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
				return false;
			}
			
		}
		
		return true;
	}

	@Override
	public List<Bike> queryAll() {
		return bikes;
	}

	@Override
	public Bike queryById(int bikeId) {
		for (Bike bike : bikes) {
			if(bike.getId() == bikeId) {
				return bike;
			}
		}
		return null;
	}

	@Override
	public int doLease(int id) {
		for (Bike bike : bikes) {
		
			if(bike.getId() == id ) {
				if (bike.getStatus() == 1) {
					bike.setStatus(0); //设置为借出状态
					bike.setAmount(bike.getAmount() + 1);
 					return 1; 	//如果有该ID，并且状态是可借,返回1代表可借
				}
				return -1; //-1 代表不可借
			} 
		}
		return 0; //0代表ID不存在
	}

	@Override
	public int doReturn(int id) {
		for (Bike bike : bikes) {
			if(bike.getId() == id ) {
				if (bike.getStatus() == 0) {  //如果是借出状态
					bike.setStatus(1); //设置为归还状态
 					return 1; 	//如果有该ID，并且状态是不可借,返回1代表归还
				}
				return -1; //-1 代表不可还
			} 
		}
		return 0; //0代表ID不存在
	}

}
