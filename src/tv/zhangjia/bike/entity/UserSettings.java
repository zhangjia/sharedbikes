package tv.zhangjia.bike.entity;

public class UserSettings {
	private  int id;
	private int userId;
	private boolean actp; //�Ƿ�����֧��
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
	public boolean isActp() {
		return actp;
	}
	public void setActp(boolean actp) {
		this.actp = actp;
	}
	public UserSettings() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserSettings(int id, int userId, boolean actp) {
		super();
		this.id = id;
		this.userId = userId;
		this.actp = actp;
	}
	public UserSettings(int userId, boolean actp) {
		super();
		this.userId = userId;
		this.actp = actp;
	}
	
}