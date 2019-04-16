package tv.zhangjia.bike.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.zxing.WriterException;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.util.Zxing;

/**
 * BikeDao接口的实现类
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.dao.impl
 * @ClassName BikeDaoImpl
 * @author ZhangJia
 * @Version
 * @date 2019年3月26日 下午4:47:51
 */
public class BikeDaoImpl implements BikeDao {
	private List<Bike> bikes = Database.BIKES;

	@Override
	public boolean doInsert(Bike bike) {
		bike.setId(Database.nextBikeId());
		// locationDao.addBikeLocation(bike.getId(), bike.getLocationId());
		bike.setLastLocationId(bike.getLocationId());
		bikes.add(bike);

		try {
			Zxing.generateQR(bike);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 用于添加车辆的时候，位置的车辆总数+1
		// TODO 创建queryLocation方法
		LocationDao locationDao = new LocationDaoImpl();
		return locationDao.updateLocationBikes(bike.getLocationId());
	}

	@Override
	public boolean doDelete(int id) {
		Bike d = new Bike();
		for (Bike bike : bikes) {
			if (bike.getId() == id) {
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
			if (bikes.get(i).getId() == bike.getId()) {
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
			if (bike.getId() == id) {
				return bike;
			}
		}
		return null;
	}

	@Override
	public int bikeStatus(int bikeId) {

		// 11：可借
		// 10：不可借
		// 5：没有该Id
		// -1：损坏

		for (Bike bike : bikes) {
			if (bike.getId() == bikeId) {
				// 如果状态为可借，返回11
				if (bike.getStatus() == 1) {
					return 11;
					// 如果状态为不可借，返回10
				} else if (bike.getStatus() == 0) {
					return 10;
				} else if (bike.getStatus() == -1) {
					return -1;
				}
			}
		}
		return 5; // 没有此ID
	}

	@Override
	public int setDamage(User user, int bikeId) {
		Bike bike = queryById(bikeId);
		bike.setStatus(-1);

		return user.getWalletID();
	}

	@Override
	public List<Bike> queryByDamage() {
		List<Bike> dbike = new ArrayList<>();
		for (Bike bike : bikes) {
			if (bike.getStatus() == -1) {
				dbike.add(bike);
			}
		}
		return dbike;
	}
	@Override
	public void updatePrice() {
		OptionDao as = new OptionDaoImpl();
		for (Bike bike : bikes) {
			if (bike.getType().equals("脚蹬车")) {
				bike.setPrice(Double.parseDouble(as.queryValue("脚蹬车")));
			} else {
				bike.setPrice(Double.parseDouble(as.queryValue("助力车")));
			}
		}
	}

}
