package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.List;

import tv.zhangjia.bike.dao.RecordDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.LeaseRecord;

public class LeaseRecordDaoImpl implements RecordDao<LeaseRecord>{
	private List<LeaseRecord> lrs = Database.LEASERECORD;
	@Override
	public boolean addRecord(LeaseRecord lr) {
		lr.setId(Database.nextLeaseRecordId());
		return lrs.add(lr);
	}

	@Override
	public List<LeaseRecord> queryAll() {
		return lrs;
	}

	@Override
	public List<LeaseRecord> queryByUserId(int id) {
		List<LeaseRecord> ulr = new ArrayList<>();
		for (LeaseRecord leaseRecord : lrs) {
			if(leaseRecord.getUserId() == id) {
				ulr.add(leaseRecord);
			}
		}
		return ulr;
	}

	@Override
	public LeaseRecord queryById(int id) {
		for (LeaseRecord leaseRecord : lrs) {
			if(leaseRecord.getBikeId() == id) {
				return leaseRecord;
			}
		}
		return null;
	}
	
	
	public List<LeaseRecord> queryNotReturnByUserId(int userid) {
		List<LeaseRecord> records = new ArrayList<>();
		for (LeaseRecord record : this.lrs) {
			if(record.getUserId() == userid && record.getReturnTime() == null) {
				records.add(record);
			}
		}
		return records;
	}
	
}
