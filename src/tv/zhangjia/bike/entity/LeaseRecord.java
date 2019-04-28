package tv.zhangjia.bike.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaseRecord {
	private Integer id; 			// 租赁记录的ID
	private Integer bikeId; 		// 自行车ID
	private Integer userId; 		// 租赁用户ID
	private String username;		// 租赁用户名
	private Date leaseTime; 		// 租借时间
	private Date returnTime;		// 归还时间
	private String journey;		// 起始位置
	private Double cost; 			// 本次租赁消费金额
	private Long time;				// 本次骑行时间
	private Integer deleteStatus;

	//无参构造方法
	public LeaseRecord() {
		super();
	}

	/**
	 * 构造方法
	 * @param id
	 * @param bikeId
	 * @param userId
	 * @param leaseTime
	 * @param returnTime
	 * @param locations
	 * @param cost
	 * @param time
	 */
	public LeaseRecord(Integer id, Integer bikeId, Integer userId, Date leaseTime, Date returnTime, String journey,
			Double cost, Long time,Integer deleteStatus) {
		super();
		this.id = id;
		this.bikeId = bikeId;
		this.userId = userId;
		this.leaseTime = leaseTime;
		this.returnTime = returnTime;
		this.journey = journey;
		this.cost = cost;
		this.time = time;
		this.deleteStatus = deleteStatus;
	}

	/**
	 * 不带ID的构造方法
	 * @param bikeId
	 * @param userId
	 * @param leaseTime
	 * @param returnTime
	 * @param locations
	 * @param cost
	 * @param time
	 */
	public LeaseRecord(Integer bikeId, Integer userId, Date leaseTime, Date returnTime, String journey, Double cost,
			Long time,Integer deleteStatus) {
		super();
		this.bikeId = bikeId;
		this.userId = userId;
		this.leaseTime = leaseTime;
		this.returnTime = returnTime;
		this.journey = journey;
		this.cost = cost;
		this.time = time;
		this.deleteStatus = deleteStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBikeId() {
		return bikeId;
	}

	public void setBikeId(Integer bikeId) {
		this.bikeId = bikeId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLeaseTime() {
		return leaseTime;
	}

	public void setLeaseTime(Date leaseTime) {
		this.leaseTime = leaseTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}



	public String getJourney() {
		return journey;
	}

	public void setJourney(String journey) {
		this.journey = journey;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}


	
	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bikeId == null) ? 0 : bikeId.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((deleteStatus == null) ? 0 : deleteStatus.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((journey == null) ? 0 : journey.hashCode());
		result = prime * result + ((leaseTime == null) ? 0 : leaseTime.hashCode());
		result = prime * result + ((returnTime == null) ? 0 : returnTime.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaseRecord other = (LeaseRecord) obj;
		if (bikeId == null) {
			if (other.bikeId != null)
				return false;
		} else if (!bikeId.equals(other.bikeId))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (deleteStatus == null) {
			if (other.deleteStatus != null)
				return false;
		} else if (!deleteStatus.equals(other.deleteStatus))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (journey == null) {
			if (other.journey != null)
				return false;
		} else if (!journey.equals(other.journey))
			return false;
		if (leaseTime == null) {
			if (other.leaseTime != null)
				return false;
		} else if (!leaseTime.equals(other.leaseTime))
			return false;
		if (returnTime == null) {
			if (other.returnTime != null)
				return false;
		} else if (!returnTime.equals(other.returnTime))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String lt = (leaseTime == null ? "骑行中\t" : sdf.format(leaseTime));
		String rt = (returnTime == null ? "骑行中\t" : sdf.format(returnTime));
		return id + "\t" + bikeId + "\t" + username + "\t" + lt + "\t" + rt + "\t" + journey + "\t" + time + "秒\t"
				+ cost + "元";
	}

}
