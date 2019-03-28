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
	public boolean doDelete(int bikeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doUpdate(Bike book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Bike> queryAll() {
		return bikes;
	}

	@Override
	public Bike queryByBid(int bikeid) {
		// TODO Auto-generated method stub
		return null;
	}

}
