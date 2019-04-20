package tv.zhangjia.bike.dao.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.zxing.WriterException;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.util.CommonDao;
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
public class BikeDaoImpl extends CommonDao<Bike> implements BikeDao {
//	private List<Bike> bikes = Database.BIKES;

/*	@Override
	public boolean doInsert(Bike bike) {
		bike.setId(Database.nextBikeId());
		bike.setLastLocationId(bike.getLocationId());
		bikes.add(bike);

		try {
			Zxing.generateQR(bike);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 用于添加车辆的时候，位置的车辆总数+1
		LocationDao locationDao = new LocationDaoImpl();
		return locationDao.updateLocationBikes(bike.getLocationId());
		
	}*/
	@Override
	public int doInsert(Bike bike) {
		try {
			Zxing.generateQR(bike);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO bike VALUES(seq_bike.nextval,?,?,?,?,?,?)";
		return executeUpdate(sql, bike.getType(),bike.getLocationId(),bike.getLocationId(),
				bike.getStatus(),bike.getAmount(),bike.getQr());
		
	}

/*	@Override
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
	}*/
	@Override
	public int doDelete(int id) {
		String sql = "DELETE FROM bike WHERE id = ?";
		return executeUpdate(sql, id);
	}

/*	@Override
	public boolean doUpdate(Bike bike) {
		for (int i = 0; i < bikes.size(); i++) {
			if (bikes.get(i).getId() == bike.getId()) {
				bikes.set(i, bike);
				return true;
			}

		}

		return false;
	}*/
	@Override
	public int doUpdate(Bike bike) {
		String sql = "UPDATE bike SET type = ?,location_id = ?,lastlocation_id = ?,status = ?,amount = ?,qr = ? WHERE id = ?";
		return executeUpdate(sql,bike.getType(),bike.getLocationId(),bike.getLastLocationId(),bike.getStatus(),bike.getAmount(),bike.getQr(),bike.getId());
		
	}

//	@Override
//	public List<Bike> queryAll() {
//		return bikes;
//	}
	@Override
	public List<Bike> queryAll() {
		String sql = "SELECT * FROM bike";
		return query4BeanList(sql);
	}

//	@Override
//	public Bike queryById(int id) {
//		for (Bike bike : bikes) {
//			if (bike.getId() == id) {
//				return bike;
//			}
//		}
//		return null;
//	}
	@Override
	public Bike queryById(int id) {
		String sql = "SELECT * FROM bike WHERE id = ?";
		return query4Bean(sql,id);
	}
//
//	@Override
//	public int bikeStatus(int bikeId) {
//
//		// 11：可借
//		// 10：不可借
//		// 5：没有该Id
//		// -1：损坏
//
//		for (Bike bike : bikes) {
//			if (bike.getId() == bikeId) {
//				// 如果状态为可借，返回11
//				if (bike.getStatus() == 1) {
//					return 11;
//					// 如果状态为不可借，返回10
//				} else if (bike.getStatus() == 0) {
//					return 10;
//				} else if (bike.getStatus() == -1) {
//					return -1;
//				}
//			}
//		}
//		return 5; // 没有此ID
//	}
	@Override
	public int bikeStatus(int bikeId) {
		String sql = "SELECT * FROM bike WHERE id = ?";
		Bike bike = query4Bean(sql,bikeId);
		return bike.getStatus();
		
	}

//	@Override
//	public int setDamage(User user, int bikeId) {
//		Bike bike = queryById(bikeId);
//		bike.setStatus(-1);
//
////		return user.getWalletID();
//		return user.getId();
//	}
	@Override
	public int setDamage(User user, int bikeId) {
		String sql2 = "UPDATE bike SET status = -1 WHERE id = ?";	
		executeUpdate(sql2, bikeId);
		return user.getId();
	}

//	@Override
//	public List<Bike> queryByDamage() {
//		List<Bike> dbike = new ArrayList<>();
//		for (Bike bike : bikes) {
//			if (bike.getStatus() == -1) {
//				dbike.add(bike);
//			}
//		}
//		return dbike;
//	}
	@Override
	public List<Bike> queryByDamage() {
		String sql = "SELECT * FROM bike WHERE status = -1";
		return query4BeanList(sql);
	}
	
//	@Override
//	public void updatePrice() {
//		OptionDao as = new OptionDaoImpl();
//		for (Bike bike : bikes) {
//			if (bike.getType().equals("脚蹬车")) {
//				bike.setPrice(Double.parseDouble(as.queryValue("脚蹬车")));
//			} else {
//				bike.setPrice(Double.parseDouble(as.queryValue("助力车")));
//			}
//		}
//	}

	
	//重写抽象方法，用来返回具体的实例
	@Override
	public Bike getBeanFromResultSet(ResultSet rs) throws SQLException {
		Bike bike = new Bike();
		bike.setId(rs.getInt(1));
		bike.setType(rs.getString(2));
		bike.setLocationId(rs.getInt(3));
		bike.setLastLocationId(rs.getInt(4));
		bike.setStatus(rs.getInt(5));
		bike.setAmount(rs.getInt(6));
		bike.setQr(rs.getString(7));
		return bike;
	}

	@Override
	public double queryBikePrice(int bikeId) {
		String sql = "SELECT * FROM bike,options WHERE bike.type = options.name";
		return 0;
	}

	@Override
	public void updatePrice() {
		// TODO Auto-generated method stub
		
	}

}
