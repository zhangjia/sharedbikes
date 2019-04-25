package tv.zhangjia.bike.entity;

import java.util.Date;

public class Repair {
	Integer id;			//报修记录ID
	Integer bikeId;		//被报修的车辆ID
	Integer userId;		//报修的用户ID
	String userName; 	//报修的用户名
	Date repairDate;	//报修时间
	String result; 		//处理结果
	Integer admin_Id; 	//处理人ID
	String adminName;	//处理人用户名
	Date disposeDate;	//处理时间
	
	/**
	 * 无参构造方法
	 */
	public Repair() {
		super();
	}
	/**
	 * 构造方法
	 * @param id
	 * @param bikeId
	 * @param userId
	 * @param repairDate
	 * @param result
	 * @param admin_Id
	 * @param disposeDate
	 */
	public Repair(Integer id, Integer bikeId, Integer userId, Date repairDate, String result, Integer admin_Id,
			Date disposeDate) {
		super();
		this.id = id;
		this.bikeId = bikeId;
		this.userId = userId;
		this.repairDate = repairDate;
		this.result = result;
		this.admin_Id = admin_Id;
		this.disposeDate = disposeDate;
	}

	/**
	 * 不带ID的构造方法
	 * @param bikeId
	 * @param userId
	 * @param repairDate
	 * @param result
	 * @param admin_Id
	 * @param disposeDate
	 */
	public Repair(Integer bikeId, Integer userId, Date repairDate, String result, Integer admin_Id, Date disposeDate) {
		super();
		this.bikeId = bikeId;
		this.userId = userId;
		this.repairDate = repairDate;
		this.result = result;
		this.admin_Id = admin_Id;
		this.disposeDate = disposeDate;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getRepairDate() {
		return repairDate;
	}
	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getAdmin_Id() {
		return admin_Id;
	}
	public void setAdmin_Id(Integer admin_Id) {
		this.admin_Id = admin_Id;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public Date getDisposeDate() {
		return disposeDate;
	}
	public void setDisposeDate(Date disposeDate) {
		this.disposeDate = disposeDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adminName == null) ? 0 : adminName.hashCode());
		result = prime * result + ((admin_Id == null) ? 0 : admin_Id.hashCode());
		result = prime * result + ((bikeId == null) ? 0 : bikeId.hashCode());
		result = prime * result + ((disposeDate == null) ? 0 : disposeDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((repairDate == null) ? 0 : repairDate.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Repair other = (Repair) obj;
		if (adminName == null) {
			if (other.adminName != null)
				return false;
		} else if (!adminName.equals(other.adminName))
			return false;
		if (admin_Id == null) {
			if (other.admin_Id != null)
				return false;
		} else if (!admin_Id.equals(other.admin_Id))
			return false;
		if (bikeId == null) {
			if (other.bikeId != null)
				return false;
		} else if (!bikeId.equals(other.bikeId))
			return false;
		if (disposeDate == null) {
			if (other.disposeDate != null)
				return false;
		} else if (!disposeDate.equals(other.disposeDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (repairDate == null) {
			if (other.repairDate != null)
				return false;
		} else if (!repairDate.equals(other.repairDate))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Repair [id=" + id + ", bikeId=" + bikeId + ", userName=" + userName + ", repairDate=" + repairDate
				+ ", result=" + result + ", adminName=" + adminName + ", disposeDate=" + disposeDate + "]";
	}
	
	
	
}
