package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Repair;

public interface RepairDao {
	int doInsert(Repair repair);

	int doDelete(int id);

	int doUpdate(Repair repair);

	List<Repair> queryAll();

	int doDispose();

	Repair queryByBikeId(int bikeId);

	boolean isRepair(int bikeId);
}
