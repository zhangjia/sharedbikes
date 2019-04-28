package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.User;
/**
 * Bike接口
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.dao	
 * @ClassName	BikeDao	
 * @author	ZhangJia
 * @Version	
 * @date	2019年3月26日 下午5:37:20
 */
public interface BikeDao {
	/**
	 * 添加单车
	 * @param bike
	 * @return 
	 */
	 int doInsert(Bike bike);
	 
	 /**
	  * 根据单车ID删除单车
	  * @param bikeId
	  * @return
	  */
	 int doDelete(int id);
	 
	 /**
	  * 修改单车
	  * @param bike
	  * @return
	  */
	 int doUpdate(Bike bike);
	 /**
	  * 查询所有单车
	  * @return
	  */
	 List<Bike> queryAll();
	 /**
	  * 根据单车ID查询单车
	  * @param bikeid
	  * @return
	  */
	 Bike queryById(int id); 

	 /**
	  * 根据单车id查询单车的状态
	  * @param bikeId
	  * @return
	  */
	 int bikeStatus(int bikeId);
	 
	 /**
	  * 根据单车ID修改单车的状态为损坏
	  * @param user
	  * @param bikeId
	  * @return
	  */
	 int setDamage(User user, int bikeId);
	 
	 /**
	  * 查询已经损坏的单车
	  * @return
	  */
	 List<Bike> queryByDamage();
	 
	 /**
	  * 更新单车价格
	  */
	 void updatePrice();
	 
	 double queryBikePrice(int bikeId);
	 
	 List<Bike> queryAllByNotDelete();
}
