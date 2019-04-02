package tv.zhangjia.bike.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaseRecord {
	private int id; 		 	//租赁记录的ID
	private int bikeId; 	 	//自行车ID
	private int userId;		//租赁用户ID
	private String username;	//租赁用户名
	private Date leaseTime;	//租借时间
	private Date returnTime;	//归还时间
	private String locations;
	private double cost;		//本次租赁消费金额
	
	private long time;




	public LeaseRecord(int bikeId, int userId, String username, Date leaseTime, Date returnTime, String locations,
			double cost, long time) {
		super();
		this.bikeId = bikeId;
		this.userId = userId;
		this.username = username;
		this.leaseTime = leaseTime;
		this.returnTime = returnTime;
		this.locations = locations;
		this.cost = cost;
		this.time = time;
	}




	public LeaseRecord() {
		super();
		// TODO Auto-generated constructor stub
	}




	public LeaseRecord(int id, int bikeId, int userId, String username, Date leaseTime, Date returnTime,
			String locations, double cost, long time) {
		super();
		this.id = id;
		this.bikeId = bikeId;
		this.userId = userId;
		this.username = username;
		this.leaseTime = leaseTime;
		this.returnTime = returnTime;
		this.locations = locations;
		this.cost = cost;
		this.time = time;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bikeId;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((leaseTime == null) ? 0 : leaseTime.hashCode());
		result = prime * result + ((locations == null) ? 0 : locations.hashCode());
		result = prime * result + ((returnTime == null) ? 0 : returnTime.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
		result = prime * result + userId;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		if (bikeId != other.bikeId)
			return false;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (id != other.id)
			return false;
		if (leaseTime == null) {
			if (other.leaseTime != null)
				return false;
		} else if (!leaseTime.equals(other.leaseTime))
			return false;
		if (locations == null) {
			if (other.locations != null)
				return false;
		} else if (!locations.equals(other.locations))
			return false;
		if (returnTime == null) {
			if (other.returnTime != null)
				return false;
		} else if (!returnTime.equals(other.returnTime))
			return false;
		if (time != other.time)
			return false;
		if (userId != other.userId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public int getBikeId() {
		return bikeId;
	}




	public void setBikeId(int bikeId) {
		this.bikeId = bikeId;
	}




	public int getUserId() {
		return userId;
	}




	public void setUserId(int userId) {
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




	public String getLocations() {
		return locations;
	}




	public void setLocations(String locations) {
		this.locations = locations;
	}




	public double getCost() {
		return cost;
	}




	public void setCost(double cost) {
		this.cost = cost;
	}




	public long getTime() {
		return time;
	}




	public void setTime(long time) {
		this.time = time;
	}




	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String lt = (leaseTime == null ? "骑行中\t" : sdf.format(leaseTime));
		String rt = (returnTime == null ? "骑行中\t" : sdf.format(returnTime));
		return id + "\t"  + bikeId + "\t"  + username
				+ "\t" + lt + "\t"  + rt + "\t"  + locations + "\t" +  time + "秒\t" + cost + "元" ;
	}






}
