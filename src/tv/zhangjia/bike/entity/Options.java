package tv.zhangjia.bike.entity;

public class Options {
	private Integer id; // 选项ID
	private String name; // 选项名称
	private String value; // 选项值

	/**
	 * 无参构造方法
	 */
	public Options() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param id
	 * @param name
	 * @param value
	 */
	public Options(Integer id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

}
