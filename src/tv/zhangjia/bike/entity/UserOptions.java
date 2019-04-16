package tv.zhangjia.bike.entity;

public class UserOptions {
	private int id;
	private int userId;
	private String name; //选项名称
	private String value;  //选项值
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public UserOptions(int id, int userId, String name, String value) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.value = value;
	}
	public UserOptions(int userId, String name, String value) {
		super();
		this.userId = userId;
		this.name = name;
		this.value = value;
	}
	
	
	
}
