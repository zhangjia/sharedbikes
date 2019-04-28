package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
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
public class BikeDaoImpl extends CommonDao implements BikeDao {

	/**
	 * 添加单车
	 * @param bike 单车对象
	 * @return 添加成功返回1，添加失败返回0
	 */
	@Override
	public int doInsert(Bike bike) {
		String sqlid = "SELECT seq_users.nextval id FROM dual";
		// 获取要添加的单车的ID
		int bikeId = query4IntData(sqlid);
		// 设置单车ID，用户生成二维码
		bike.setId(bikeId);
		// 生成二维码
		try {
			Zxing.generateQR(bike);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "INSERT INTO bike VALUES(?,?,?,?,?,?,?,1)";

		int x = executeUpdate(sql, bikeId, bike.getType(), bike.getLocationId(), bike.getLocationId(), bike.getStatus(),
				bike.getAmount(), bike.getQr());

		return x;

	}

	/**
	 * 删除单车
	 * @param id 要删除的单车ID
	 * @return 删除成功返回1，删除失败返回0
	 */
	@Override
	public int doDelete(int id) {
		String sql = "DELETE FROM bike WHERE id = ?";
		return executeUpdate(sql, id);
	}

	/**
	 * 更新单车
	 * @param id 要更新的单车ID
	 * @return 更新成功返回1，更新失败返回0
	 */
	@Override
	public int doUpdate(Bike bike) {
		String sql = "UPDATE bike SET type = ?,location_id = ?,lastlocation_id = ?,status = ?,amount = ?,qr = ?,delete_status =  ? WHERE id = ?";
		return executeUpdate(sql, bike.getType(), bike.getLocationId(), bike.getLastLocationId(), bike.getStatus(),
				bike.getAmount(), bike.getQr(),bike.getDeleteStatus(), bike.getId());

	}

	/**
	 * 查询所有单车
	 * @return 所有单车
	 */
	@Override
	public List<Bike> queryAll() {
		// 通过和Option表连接，获取当前车型的价格存在price字段中
		String sql = "SELECT bike.*,options.value price ,location_name FROM bike,options,location WHERE bike.type = options.name AND bike.location_id = location.id ORDER BY bike.id ";
		return query4BeanList(sql, Bike.class);
	}
	
	/**
	 * 查询未删除的所有单车
	 * @return 所有单车
	 */
	@Override
	public List<Bike> queryAllByNotDelete() {
		// 通过和Option表连接，获取当前车型的价格存在price字段中
//		String sql = "SELECT bike.*,options.value price ,location_name FROM bike,options,location WHERE bike.type = options.name AND bike.location_id = location.id AND bike.delete_status = 1 ORDER BY bike.id";
		String sql = "SELECT bike.id,bike.type,bike.location_id,bike.lastlocation_id,bike.status,bike.amount,bike.qr,options.value price ,location_name FROM bike,options,location WHERE bike.type = options.name AND bike.location_id = location.id AND bike.delete_status = 1 ORDER BY bike.id";

		return query4BeanList(sql, Bike.class);
	}

	/**
	 * 根据单车Id返回单车信息
	 * @param id 单车ID
	 * @return 单车信息
	 */
	@Override
	public Bike queryById(int id) {
		String sql = "SELECT bike.*,options.value price ,location_name FROM bike,options,location WHERE bike.type = options.name AND bike.location_id = location.id AND delete_status = 1 AND bike.id = ?";
		return query4Bean(sql, Bike.class, id);
	}

	/**
	 * 查询单车状态
	 * @param bikeId
	 * @return
	 */
	@Override
	public int bikeStatus(int bikeId) {
		String sql = "SELECT status FROM bike WHERE id = ?";
		return query4IntData(sql, Bike.class, bikeId);

	}

	// TODO 删除，用更新的方法替换
	@Override
	public int setDamage(User user, int bikeId) {
		String sql2 = "UPDATE bike SET status = -1 WHERE id = ?";
		executeUpdate(sql2, bikeId);
		return user.getId();
	}

	// TODO 删除，已经有了报修类
	@Override
	public List<Bike> queryByDamage() {
		String sql = "SELECT * FROM bike WHERE status = -1";
		return null;
	}

	/**
	 * 根据单车ID查询其价格
	 * @param bikeId 要查询的单车
	 * @return 该单车的价格
	 */
	@Override
	public double queryBikePrice(int bikeId) {
		String sql = "SELECT TO_NUMBER(options.value) price FROM bike,options WHERE bike.type = options.name AND bike.id = ?";
		return query4DoubleData(sql, bikeId);
	}

	// TODO 删除，直接用查询来解决
	@Override
	public void updatePrice() {
		// TODO Auto-generated method stub

	}

}
