package tv.zhangjia.bike.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Wallet {
	private Integer id; // 钱包ID
	private Integer userId; // 用户ID
	private String username; // 用户名
	private Double balance; // 用户余额
	private Double coupon; // 用户优惠券余额
	private Boolean isVIP; // 用户是否是VIP，用户类型
	private Date vipDate; // VIP到期时间

	/**
	 * 无参构造方法
	 */
	public Wallet() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param id
	 * @param userId
	 * @param balance
	 * @param coupon
	 * @param isVIP
	 * @param vipDate
	 */
	public Wallet(Integer id, Integer userId, Double balance, Double coupon, Boolean isVIP, Date vipDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.balance = balance;
		this.coupon = coupon;
		this.isVIP = isVIP;
		this.vipDate = vipDate;
	}

	/**
	 * 不带ID的构造方法
	 * @param userId
	 * @param balance
	 * @param coupon
	 * @param isVIP
	 * @param vipDate
	 */
	public Wallet(Integer userId, Double balance, Double coupon, Boolean isVIP, Date vipDate) {
		super();
		this.userId = userId;
		this.balance = balance;
		this.coupon = coupon;
		this.isVIP = isVIP;
		this.vipDate = vipDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getCoupon() {
		return coupon;
	}

	public void setCoupon(Double coupon) {
		this.coupon = coupon;
	}

	public Boolean getIsVIP() {
		return isVIP;
	}

	public void setIsVIP(Boolean isVIP) {
		this.isVIP = isVIP;
	}

	public Date getVipDate() {
		return vipDate;
	}

	public void setVipDate(Date vipDate) {
		this.vipDate = vipDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((coupon == null) ? 0 : coupon.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isVIP == null) ? 0 : isVIP.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((vipDate == null) ? 0 : vipDate.hashCode());
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
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (coupon == null) {
			if (other.coupon != null)
				return false;
		} else if (!coupon.equals(other.coupon))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isVIP == null) {
			if (other.isVIP != null)
				return false;
		} else if (!isVIP.equals(other.isVIP))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (vipDate == null) {
			if (other.vipDate != null)
				return false;
		} else if (!vipDate.equals(other.vipDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return id + "\t" + username + "\t" + balance + "\t" + coupon + "\t" + (isVIP ? "VIP用户" : "普通用户") + "\t"
				+ (vipDate == null ? "未开通" : sdf.format(vipDate));
	}

}
