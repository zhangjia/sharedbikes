package tv.zhangjia.bike.entity;

import java.util.Date;

/**
 * 用户类
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.entity
 * @ClassName User
 * @author ZhangJia
 * @Version v1.0
 * @date 2019年3月25日 下午6:10:17new ArrayList<Bike>().add(BIKES.get(0))
 */
public class User {
	private int id; // 用户id
	private String username; // 用户名,要求唯一
	private String password; // 密码
	private String tel; // 用户手机号
	private boolean isAdmin; // 用户是否是管理员
	private long cyclingTime; // 用户总骑行时间
	private Date registerTime;// 用户注册时间
	private int locationID;
	private int walletID;
	private String payPassword;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public long getCyclingTime() {
		return cyclingTime;
	}
	public void setCyclingTime(long cyclingTime) {
		this.cyclingTime = cyclingTime;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public int getWalletID() {
		return walletID;
	}
	public void setWalletID(int walletID) {
		this.walletID = walletID;
	}
	
	
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cyclingTime ^ (cyclingTime >>> 32));
		result = prime * result + id;
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + locationID;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((payPassword == null) ? 0 : payPassword.hashCode());
		result = prime * result + ((registerTime == null) ? 0 : registerTime.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + walletID;
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
		User other = (User) obj;
		if (cyclingTime != other.cyclingTime)
			return false;
		if (id != other.id)
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (locationID != other.locationID)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (payPassword == null) {
			if (other.payPassword != null)
				return false;
		} else if (!payPassword.equals(other.payPassword))
			return false;
		if (registerTime == null) {
			if (other.registerTime != null)
				return false;
		} else if (!registerTime.equals(other.registerTime))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (walletID != other.walletID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return id + "\t" + username + ", password=" + password + ", tel=" + tel + ", isAdmin="
				+ isAdmin + ", cyclingTime=" + cyclingTime + ", registerTime=" + registerTime + ", locationID="
				+ locationID + ", walletID=" + walletID + "]";
	}
	public User(int id, String username, String password, String tel, boolean isAdmin, long cyclingTime,
			Date registerTime, int locationID, int walletID) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.tel = tel;
		this.isAdmin = isAdmin;
		this.cyclingTime = cyclingTime;
		this.registerTime = registerTime;
		this.locationID = locationID;
		this.walletID = walletID;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String username, String password, String tel, boolean isAdmin, long cyclingTime, Date registerTime,
			int locationID, int walletID) {
		super();
		this.username = username;
		this.password = password;
		this.tel = tel;
		this.isAdmin = isAdmin;
		this.cyclingTime = cyclingTime;
		this.registerTime = registerTime;
		this.locationID = locationID;
		this.walletID = walletID;
	}
	
	
	
	
}
