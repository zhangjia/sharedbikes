package tv.zhangjia.bike.entity;

public class Options {
	private int id;
	private String name; //选项名称
	private String value;  //选项值
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Options(int id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
}
