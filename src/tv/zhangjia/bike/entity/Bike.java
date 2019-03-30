package tv.zhangjia.bike.entity;
/**
 * 单车类
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.entity	
 * @ClassName	Bike	
 * @author	ZhangJia
 * @Version	v1.0
 * @date	2019年3月26日 下午5:36:44
 */
public class Bike {
	private int id; 		//单车ID
	private String type; 	//单车类型
	private double price;	//单车价格
	private int locationId;//单车位置
	private int lastLocationId; //上一次单车位置
	private int status;		//单车状态
	private int amount;		//单车骑行次数
	private String qr;		//单车二维码
	
	
	
	public Bike(String type, double price, int locationId, int status, int amount, String qr) {
		super();
		this.type = type;
		this.price = price;
		this.locationId = locationId;
		this.status = status;
		this.amount = amount;
		this.qr = qr;
	}
	public Bike(int id, String type, double price, int locationId, int status, int amount, String qr) {
		super();
		this.id = id;
		this.type = type;
		this.price = price;
		this.locationId = locationId;
		this.status = status;
		this.amount = amount;
		this.qr = qr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public int getLastLocationId() {
		return lastLocationId;
	}
	public void setLastLocationId(int lastLocationId) {
		this.lastLocationId = lastLocationId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getQr() {
		return qr;
	}
	public void setQr(String qr) {
		this.qr = qr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + id;
		result = prime * result + lastLocationId;
		result = prime * result + locationId;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((qr == null) ? 0 : qr.hashCode());
		result = prime * result + status;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Bike other = (Bike) obj;
		if (amount != other.amount)
			return false;
		if (id != other.id)
			return false;
		if (lastLocationId != other.lastLocationId)
			return false;
		if (locationId != other.locationId)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (qr == null) {
			if (other.qr != null)
				return false;
		} else if (!qr.equals(other.qr))
			return false;
		if (status != other.status)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Bike [id=" + id + ", type=" + type + ", price=" + price + ", locationId=" + locationId
				+ ", lastLocationId=" + lastLocationId + ", status=" + status + ", amount=" + amount + ", qr=" + qr
				+ "] + \n";
	}
	public Bike(int id, String type, double price, int locationId, int lastLocationId, int status, int amount,
			String qr) {
		super();
		this.id = id;
		this.type = type;
		this.price = price;
		this.locationId = locationId;
		this.lastLocationId = lastLocationId;
		this.status = status;
		this.amount = amount;
		this.qr = qr;
	}
	public Bike() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bike(String type, double price, int locationId, int lastLocationId, int status, int amount, String qr) {
		super();
		this.type = type;
		this.price = price;
		this.locationId = locationId;
		this.lastLocationId = lastLocationId;
		this.status = status;
		this.amount = amount;
		this.qr = qr;
	}
	
	
}
