package tv.zhangjia.bike.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.data.Database;

public class Bill {
	private int id;// 账单记录ID
	private String billName; // 账单记录名称
	private int userId;
	private Date billDate; //账单记录产生时间
	private double money;
	private List<User> users = Database.USERS;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBillName() {
		return billName;
	}
	public void setBillName(String billName) {
		this.billName = billName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String username = "";
		String sm = "";
		for (User user : users) {
			if(user.getId() == userId) {
				username = user.getUsername();
			}
			
		}
		if(money >= 0) {
			sm = "+" + money;
		} else {
			sm = "" + money;
		}
		
		return + id + "\t" + username + "\t" + billName +  "\t" + sm  
				+ "\t" + sdf.format(billDate) + "\n";
	}
	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bill(int id, String billName, int userId, Date billDate, double money) {
		super();
		this.id = id;
		this.billName = billName;
		this.userId = userId;
		this.billDate = billDate;
		this.money = money;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billDate == null) ? 0 : billDate.hashCode());
		result = prime * result + ((billName == null) ? 0 : billName.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(money);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + userId;
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
		Bill other = (Bill) obj;
		if (billDate == null) {
			if (other.billDate != null)
				return false;
		} else if (!billDate.equals(other.billDate))
			return false;
		if (billName == null) {
			if (other.billName != null)
				return false;
		} else if (!billName.equals(other.billName))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	public Bill(String billName, int userId, Date billDate, double money) {
		super();
		this.billName = billName;
		this.userId = userId;
		this.billDate = billDate;
		this.money = money;
	}
	
	
}
