package tv.zhangjia.bike.entity;

/**
 * �û���
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.entity
 * @ClassName User
 * @author ZhangJia
 * @Version v1.0
 * @date 2019��3��25�� ����6:10:17
 */
public class User {
	private int id; // �û�id
	private String username; // �û���,Ҫ��Ψһ
	private String password; // ����
	private String tel; // �û��ֻ���
	private boolean isAdmin; // �û��Ƿ��ǹ���Ա
	private double balance; // �û����
	private double coupon; // �û��Ż�ȯʣ��
	private long cyclingTime; // �û�������ʱ��
	private String registerTime;// �û�ע��ʱ��

	/**
	 * �޲ι��췽��
	 */
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * ��ID�Ĵ��ι��췽��
	 * @param id	�û�id
	 * @param username	�û���
	 * @param password	����
	 * @param tel	�ֻ���
	 * @param isAdmin	�Ƿ��ǹ���Ա
	 * @param balance	���
	 * @param coupon	�Ż�ȯ���
	 * @param cyclingTime	������ʱ��
	 * @param registerTime	ע��ʱ��
	 */

	public User(int id, String username, String password, String tel, boolean isAdmin, double balance, double coupon,
			long cyclingTime, String registerTime) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.tel = tel;
		this.isAdmin = isAdmin;
		this.balance = balance;
		this.coupon = coupon;
		this.cyclingTime = cyclingTime;
		this.registerTime = registerTime;
	}

	/**
	 * ����ID�Ĵ��ι��췽��
	 * @param username	�û���
	 * @param password	����
	 * @param tel	�ֻ���
	 * @param isAdmin	�Ƿ��ǹ���Ա
	 * @param balance	���
	 * @param coupon	�Ż�ȯ���
	 * @param cyclingTime	������ʱ��
	 * @param registerTime	ע��ʱ��
	 */
	public User(String username, String password, String tel, boolean isAdmin, double balance, double coupon,
			long cyclingTime, String registerTime) {
		super();
		this.username = username;
		this.password = password;
		this.tel = tel;
		this.isAdmin = isAdmin;
		this.balance = balance;
		this.coupon = coupon;
		this.cyclingTime = cyclingTime;
		this.registerTime = registerTime;
	}

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

	public long getCyclingTime() {
		return cyclingTime;
	}

	public void setCyclingTime(long cyclingTime) {
		this.cyclingTime = cyclingTime;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
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
		result = prime * result + (int) (cyclingTime ^ (cyclingTime >>> 32));
		result = prime * result + id;
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((registerTime == null) ? 0 : registerTime.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
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
		User other = (User) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (Double.doubleToLongBits(coupon) != Double.doubleToLongBits(other.coupon))
			return false;
		if (cyclingTime != other.cyclingTime)
			return false;
		if (id != other.id)
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
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
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", tel=" + tel + ", isAdmin="
				+ isAdmin + ", balance=" + balance + ", coupon=" + coupon + ", cyclingTime=" + cyclingTime
				+ ", registerTime=" + registerTime + "]";
	}

	
}