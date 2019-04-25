package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Options;

public interface OptionDao {
	
	List<Options> queryAlloptions();
	String queryValue(String name);
	 int  doUpdate(String value,String name);
}
