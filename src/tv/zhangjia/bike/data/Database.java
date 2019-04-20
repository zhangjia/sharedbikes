package tv.zhangjia.bike.data;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.zxing.WriterException;

import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.impl.LocationDaoImpl;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.Options;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.UserOptions;
import tv.zhangjia.bike.entity.UserSettings;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.Zxing;

/**
 * 模拟数据库
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.data
 * @ClassName Database
 * @author ZhangJia
 * @Version V1.0
 * @date 2019年3月25日 下午6:31:16
 */
public class Database {
	public static final List<User> USERS = new ArrayList<>();
	public static final List<Bike> BIKES = new ArrayList<>();
	public static final List<Location> LOCATIONS = new ArrayList<>();
	public static final List<LeaseRecord> LEASERECORDS = new ArrayList<>();
	public static final List<Wallet> WALLETS = new ArrayList<>();
	public static final List<Bill> BILLS = new ArrayList<>();
	public static List<Options> OPTIONS = new ArrayList<>();
	public static List<UserOptions> USEROPTIONS = new ArrayList<>();
	private static LocationDao locationDao = new LocationDaoImpl();
	static {
		OPTIONS.add(new Options(nextOptionsId(), "折扣", "0.5"));
		OPTIONS.add(new Options(nextOptionsId(), "会员价格", "10"));
		OPTIONS.add(new Options(nextOptionsId(), "脚蹬车", "10"));
		OPTIONS.add(new Options(nextOptionsId(), "助力车", "20"));
		OPTIONS.add(new Options(nextOptionsId(), "广告", "甲骨文欢迎您"));

		String str = "2019-03-28";
		// 将String转换为Date
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 向数据库中默认添加一个管理员
		USERS.add(new User(1, "admin", "1", "13863313959", true, 0, date, 1, "zhangjia"));
		// 向数据库中默认添加二个用户
		USERS.add(new User(2, "Luffy", "3", "15666335517", false, 0, date, 2,  "3"));

		USEROPTIONS.add(new UserOptions(nextUserOptionsId(), 1,"免密支付", "0"));
		USEROPTIONS.add(new UserOptions(nextUserOptionsId(), 2,"免密支付", "0"));

		WALLETS.add(new Wallet(1, 1, 1000, 0, true, new Date()));
		WALLETS.add(new Wallet(2, 2, 0, 0, false, null));

		BIKES.add((new Bike(1, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 1, 1, 1, 0, "二维码")));
		BIKES.add((new Bike(2, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 2, 2, 1, 0, "二维码")));
		BIKES.add((new Bike(3, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 3, 1, 0, "二维码")));
		BIKES.add((new Bike(4, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 4, 4, 1, 0, "二维码")));
		BIKES.add((new Bike(5, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 1, 1, 1, 0, "二维码")));
		BIKES.add((new Bike(6, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 2, 2, 1, 0, "二维码")));
		BIKES.add((new Bike(7, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 2, 4, 1, 0, "二维码")));
		BIKES.add((new Bike(8, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 3, 1, 0, "二维码")));
		BIKES.add((new Bike(9, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 1, 1, 1, 0, "二维码")));
		BIKES.add((new Bike(10, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 2, 2, 1, 0, "二维码")));
		BIKES.add((new Bike(11, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 3, 3, 1, 0, "二维码")));
		BIKES.add((new Bike(12, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 4, 1, 0, "二维码")));
		BIKES.add((new Bike(13, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 3, 4, 1, 0, "二维码")));
		BIKES.add((new Bike(14, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 4, 1, 0, "二维码")));
		BIKES.add((new Bike(15, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 2, 1, 1, 0, "二维码")));
		BIKES.add((new Bike(16, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 4, 4, 1, 0, "二维码")));
		BIKES.add((new Bike(17, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 3, 4, 1, 0, "二维码")));
		BIKES.add((new Bike(18, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 4, 1, 0, "二维码")));
		BIKES.add((new Bike(19, "助力车", Double.parseDouble(OPTIONS.get(3).getValue()), 2, 1, 1, 0, "二维码")));
		BIKES.add((new Bike(20, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 2, 4, 1, 0, "二维码")));
		BIKES.add((new Bike(21, "脚蹬车", Double.parseDouble(OPTIONS.get(2).getValue()), 2, 2, 1, 0, "二维码")));

		for (Bike bike : BIKES) {
			try {
				Zxing.generateQR(bike);
			} catch (WriterException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		LOCATIONS.add((new Location(1, "创意大厦")));

		LOCATIONS.add((new Location(2, "绿色家园")));
		LOCATIONS.add((new Location(3, "青年公寓")));
		LOCATIONS.add((new Location(4, "万达广场")));

		locationDao.updateLocationBikes(1);
		locationDao.updateLocationBikes(2);
		locationDao.updateLocationBikes(3);
		locationDao.updateLocationBikes(4);

	}

	/**
	 * 生成下一个用户的ID
	 * 
	 * @return 下一个用户的ID
	 */
	public static int nextUserId() {
		if (USERS.isEmpty()) {
			return 1;
		}
		return USERS.get(USERS.size() - 1).getId() + 1;
	}

	/**
	 * 生成下一个Bike的ID
	 * 
	 * @return 下一个Bike的ID
	 */
	public static int nextBikeId() {
		if (BIKES.isEmpty()) {
			return 1;
		}
		return BIKES.get(BIKES.size() - 1).getId() + 1;
	}

	/**
	 * 生成下一个记录的ID
	 * 
	 * @return 下一个Bike的ID
	 */
	public static int nextLeaseRecordId() {
		if (LEASERECORDS.isEmpty()) {
			return 1;
		}
		return LEASERECORDS.get(LEASERECORDS.size() - 1).getId() + 1;
	}

	/**
	 * 生成下一个位置的ID
	 * 
	 * @return 下一个Bike的ID
	 */
	public static int nextLocationId() {
		if (LOCATIONS.isEmpty()) {
			return 1;
		}
		return LOCATIONS.get(LOCATIONS.size() - 1).getId() + 1;
	}

	/**
	 * 生成下一个钱包的ID
	 * 
	 * @return 下一个Bike的ID
	 */
	public static int nextWalletId() {
		if (WALLETS.isEmpty()) {
			return 1;
		}
		return WALLETS.get(WALLETS.size() - 1).getId() + 1;
	}

	/**
	 * 生成下一个账单的ID
	 * 
	 * @return 下一个Bike的ID
	 */
	public static int nextBillId() {
		if (BILLS.isEmpty()) {
			return 1;
		}
		return BILLS.get(BILLS.size() - 1).getId() + 1;
	}

	/**
	 * 生成下一个用户设置的Id
	 * 
	 * @return 下一个Bike的ID
	 */
	public static int nextOptionsId() {
		if (OPTIONS.isEmpty()) {
			return 1;
		}
		return OPTIONS.get(OPTIONS.size() - 1).getId() + 1;
	}
	
	/**
	 * 生成下一个用户设置的Id
	 * 
	 * @return 下一个Bike的ID
	 */
	public static int nextUserOptionsId() {
		if (USEROPTIONS.isEmpty()) {
			return 1;
		}
		return USEROPTIONS.get(USEROPTIONS.size() - 1).getId() + 1;
	}

}
