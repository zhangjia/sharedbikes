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
	 boolean doInsert(Bike bike);
	 
	 /**
	  * 根据单车ID删除ID
	  * @param bikeId
	  * @return
	  */
	 boolean doDelete(int id);
	 
	 /**
	  * 修改单车
	  * @param bike
	  * @return
	  */
	 boolean doUpdate(Bike bike);
	 /**
	  * 查询所有单车
	  * @return
	  */
	 List<Bike> queryAll();
	 /**
	  * 根据ID查询单车
	  * @param bikeid
	  * @return
	  */
	 Bike queryById(int id); 
	 
	 /**
	  * 根据ID借单车
	  * @param id
	  * @return
	  */
	 int doLease(int id,User user);
	 
	 /**
	  * 根据ID还单车
	  * @param id
	  * @return
	  */
	 int doReturn(int id);
	 
	 
}
