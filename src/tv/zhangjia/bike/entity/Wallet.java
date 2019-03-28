package tv.zhangjia.bike.entity;

import java.util.Date;

public class Wallet {
	private int userId; //用户ID
	private double balance; //用户余额
	private double coupon; // 用户优惠券余额
	private boolean isVIP;//用户是否是VIP，用户类型
	private Date vipDate; //VIP时间
	private double vipPrice; //开通VIP的价格
	private double vipRebate; //vip打几折
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getCoupon() {
		return coupon;
	}
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	public boolean isVIP() {
		return isVIP;
	}
	public void setVIP(boolean isVIP) {
		this.isVIP = isVIP;
	}
	public Date getVipDate() {
		return vipDate;
	}
	public void setVipDate(Date vipDate) {
		this.vipDate = vipDate;
	}
	public double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public double getVipRebate() {
		return vipRebate;
	}
	public void setVipRebate(double vipRebate) {
		this.vipRebate = vipRebate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(coupon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (isVIP ? 1231 : 1237);
		result = prime * result + userId;
		result = prime * result + ((vipDate == null) ? 0 : vipDate.hashCode());
		temp = Double.doubleToLongBits(vipPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vipRebate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Wallet other = (Wallet) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (Double.doubleToLongBits(coupon) != Double.doubleToLongBits(other.coupon))
			return false;
		if (isVIP != other.isVIP)
			return false;
		if (userId != other.userId)
			return false;
		if (vipDate == null) {
			if (other.vipDate != null)
				return false;
		} else if (!vipDate.equals(other.vipDate))
			return false;
		if (Double.doubleToLongBits(vipPrice) != Double.doubleToLongBits(other.vipPrice))
			return false;
		if (Double.doubleToLongBits(vipRebate) != Double.doubleToLongBits(other.vipRebate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Wallet [userId=" + userId + ", balance=" + balance + ", coupon=" + coupon + ", isVIP=" + isVIP
				+ ", vipDate=" + vipDate + ", vipPrice=" + vipPrice + ", vipRebate=" + vipRebate + "]";
	}
	public Wallet(int userId, double balance, double coupon, boolean isVIP, Date vipDate, double vipPrice,
			double vipRebate) {
		super();
		this.userId = userId;
		this.balance = balance;
		this.coupon = coupon;
		this.isVIP = isVIP;
		this.vipDate = vipDate;
		this.vipPrice = vipPrice;
		this.vipRebate = vipRebate;
	}
	public Wallet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
