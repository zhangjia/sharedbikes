package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Options;

public class OptionDaoImpl implements OptionDao {
	List<Options> op = Database.OPTIONS;


	@Override
	public List<Options> queryAlloptions() {
		// TODO Auto-generated method stub
		return op;
	}

	@Override
	public String queryValue(String name) {
		
		for (Options options : op) {
			if(options.getName().equals(name)) {
				return options.getValue();
			}
		}
		return null;
	}

}
