package tv.zhangjia.bike.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.UserOptionsDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.dao.impl.BillDaoImpl;
import tv.zhangjia.bike.dao.impl.LeaseRecordDaoImpl;
import tv.zhangjia.bike.dao.impl.LocationDaoImpl;
import tv.zhangjia.bike.dao.impl.OptionDaoImpl;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.dao.impl.UserOptionsDaoImpl;
import tv.zhangjia.bike.dao.impl.WalletDaoImpl;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.UserOptions;
import tv.zhangjia.bike.entity.Wallet;

/**
 * 主菜单类
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.util
 * @ClassName Menu
 * @author ZhangJia
 * @Version v1.0
 * @date 2019年3月25日 下午6:37:45
 */
public class Menu {
	private Scanner input = new Scanner(System.in);
	private User user = null;
	private UserDao userDao = new UserDaoImpl();
	private BikeDao bikeDao = new BikeDaoImpl();
	private LeaseRecordDao leaseRecordDao = new LeaseRecordDaoImpl();
	private WalletDao walletDao = new WalletDaoImpl();
	private InputIsValid iiv = new InputIsValid();
	private BillDao billDao = new BillDaoImpl();
	private LocationDao locationDao = new LocationDaoImpl();
	// private AdminSettingsDao as = new AdminSettinsDaoImpl();
	private OptionDao as = new OptionDaoImpl();
	private UserOptionsDao us = new UserOptionsDaoImpl();
	// private UserSettingsDao us = new UserSettingsDaoImpl();

	/**
	 * 主菜单，进入该系统的用户看到的第一个界面
	 * 
	 * @Title mainMenu
	 */
	public void mainMenu() {
		System.out.println("----------欢迎您使用共享单车租赁系统----------");
		System.out.println("\t\t1.登录");
		System.out.println("\t\t2.注册");
		System.out.println("\t\t3.退出 ");
		System.out.println("-------------你看到我的底线了--------------");
		System.out.print("请选择您接下来的操作:");
		int index = 1;
		// 用while循环实现如果选项不存在，重新输入
		while (true) {
			String choose = input.next();
			// 判断输入的内容是否为整数
			if (iiv.isNumber(choose)) {
				index = Integer.parseInt(choose);
				if (index > 3) {
					System.out.print("不存在此选项,请重新输入：");
				} else {
					break;
				}
			} else {
				System.out.print("输入不合法,请重新输入：");
			}
		}

		switch (index) {
		case 1:
			userLogin();
			break;
		case 2:
			userRegister();
			break;
		case 3:
			exit();
			break;
		default:
			System.out.print("不存在此选项,请重新输入：");
		}

	}

	private int retrievePassword(int userId) {

		System.out.print("请输入您的手机号：");
		while (true) {
			String tel = input.next();

			if (!userDao.isTrueTel(tel, userId)) {
				System.out.print("该手机号和您的用户名不匹配,请重新输入：");
			} else {
				break;
			}
		}
		String codes = VerificationCode.randomCode();
		System.out.print("请输入您的验证码：");

		try {
			Thread.sleep(1500);//
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 没有手机给你发验证码，只能委屈你自己输出了
		System.out.print(codes);

		try {
			Thread.sleep(500);//
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("   验证码正确！");

		System.out.print("请输入新密码：");
		String newPassword = input.next();
		User user = userDao.queryByUserId(userId);
		user.setPassword(newPassword);
		return userDao.doUpdate(user);
	}

	/**
	 * 用户登录
	 */
	private void userLogin() {
		printBoundary();
		System.out.print("请输入您的用户名：");
		String username;
		while (true) {
			username = input.next();
			if (!userDao.isUserNameExist(username)) {
				System.out.print("没有该用户名,请重新输入：");
			} else {
				break;
			}
		}

		System.out.print("请输入您的密码：");
		String password;
		int wa = 0; // 判断输错密码几次
		User login;
		while (true) {
			password = input.next();
			login = userDao.login(username, password);
			if (login == null) {
				wa++;
				if (wa == 2) {
					System.out.println("密码错误，是否找回密码？[ 找回 ：y | 返回主菜单：r ] : ");
					String s = input.next();
					printBoundary();
					if (s.equalsIgnoreCase("y")) {
						// -1代表是找回密码，登录用户是修改密码，传入的是用户的id
						retrievePassword(-1);
						System.out.println("找回成功！请重新登录：");
						userLogin(); // TODO :调用自己了
					} else {
						mainMenu();
					}
					break;
				}
				System.out.print("登录失败，您的密码错误,请重新输入：");
			} else {
				break;
			}
		}

		this.user = login;
		if (user.getIsAdmin()) {
			adminMenu();
		} else {
			userMenu();
		}

	}

	/**
	 * 管理员主界面
	 */
	private void adminMenu() {
		printBoundary();
		System.out.println("尊敬的" + user.getUsername() + "管理员，您好！");
		System.out.println("\t1.查询单车");
		System.out.println("\t2.添加单车");
		System.out.println("\t3.修改单车");
		System.out.println("\t4.删除单车");
		System.out.println("\t5.已坏单车");
		System.out.println("\t6.查看位置");
		System.out.println("\t7.调度建议");
		System.out.println("\t8.租赁记录");
		System.out.println("\t9.用户信息");
		System.out.println("\t10.用户钱包");
		System.out.println("\t11.用户账单");
		System.out.println("\t12.系统设置");
		System.out.println("\t13.退出登录");
		System.out.println("\t14.退出系统");
		printBoundary();
		System.out.print("请选择您接下来的操作：");
		int index = 1;
		while (true) {
			String nextInt = input.next();
			if (iiv.isNumber(nextInt)) {
				index = Integer.parseInt(nextInt);
				if (index > 14) {
					System.out.print("不存在此选项,请重新输入：");
				} else {
					break;
				}

			} else {
				System.out.print("输入不合法，请重新输入：");
			}
		}

		switch (index) {
		case 1:
			queryBikes();
			break;
		case 2:
			saveBike();
			break;
		case 3:
			editBike();
			break;
		case 4:
			deleteBike();
			break;
		case 5:
			damage();
			break;
		case 6:
			Location();//
			break;
		case 7:
			dispatch();// 调度
			break;
		case 8:
			leaseRecord();
			break;
		case 9:
			userInfo();
			break;
		case 10:
			usersWallet();//
			break;
		case 11:
			usersBill();//
			break;
		case 12:
			systemSettings();//
			break;
		case 13:
			logout();//
			break;
		case 14:
			exit();//
			break;
		default:
			System.out.print("没有该选项，请重新输入：");
		}

	}

	private void usersBill() {

		if (user.getIsAdmin()) {
			System.out.println("-------------下面是所有用户的账单信息-----------");
			System.out.println("编号\t用户名\t账单名称\t余额变化\t\t产生时间");
			List<Bill> bills = billDao.queryAll();
			if (bills.isEmpty()) {
				System.out.println("目前没有任何账单记录！");
				printBoundary();
			} else {
				for (Bill bill : bills) {
					System.out.println(bill);
				}
				printBoundary();
				System.out.print("是否导出到本地？[ 是：y | 否：n ] ：");
				String s = input.next();
				if (s.equalsIgnoreCase("y")) {
					try {
						billDao.export();
						System.out.println("导出成功");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("导出失败");
					}

				}
			}
			returnMenu();
		} else {
			System.out.println("-------------下面是您的所有账单信息-------------");
			List<Bill> userBills = billDao.queryUserBill(user.getId());
			System.out.println("编号\t用户名\t账单名称\t余额变化\t\t产生时间");
			if (userBills.isEmpty()) {
				printBoundary();
				System.out.println("您目前没有任何账单记录！");
			} else {
				for (Bill bill : userBills) {
					System.out.println(bill);
				}
			}
			returnMenu();
		}
	}

	private void usersWallet() {

		if (user.getIsAdmin()) {
			System.out.println("-----------下面是所有用户的钱包信息-----------");
			System.out.println("编号\t用户名\t账户余额\t红包余额\t用户等级\t  VIP时间");
			List<Wallet> wallets = walletDao.queryAll();
			for (Wallet wallet : wallets) {
				System.out.println(wallet);
			}

			returnMenu();
		} else {
			System.out.println("-------------个人钱包显示界面-------------");
			// System.out.println(user.getId());
			Wallet wallet = walletDao.queryByUserId(user.getId());
			// System.out.println(wallet.getId());
			System.out.println("编号\t用户名\t用户余额\t优惠券余额\t用户等级\tVIP时间");
			System.out.println(wallet);
			returnMenu();
		}

	}

	private void damage() {
		List<Bike> bikes = bikeDao.queryByDamage();
		System.out.println("----------下面是已经损坏的车辆信息----------");
		if (bikes.isEmpty()) {
			System.out.println("太好了，目前没有车辆损坏！");

		} else {
			System.out.println("编号\t类型\t价格\t位置\t状态\t次数\t二维码");
			for (Bike bike : bikes) {
				System.out.println(bike);
			}
		}
		returnMenu();

	}

	private void dispatch() {
		System.out.println("---------下面是最新的位置信息---------");
		List<Location> locations = locationDao.queryAll();
		System.out.println("编号\t位置名词\t车辆总数");
		for (Location location : locations) {
			System.out.println(location);
		}
		printBoundary();
		System.out.println("根据上面的位置信息，人工智障建议您：");
		List<String> arr = locationDao.dispatch();
		for (String string : arr) {
			System.out.println(string);
		}
		returnMenu();

	}

	/**
	 * 显示广告
	 * @param user 登录的用户
	 */
	private void advertising(User user) {
		Wallet wt = walletDao.queryByUserId(user.getId());
		// 如果不是会员，就显示广告
		if (!wt.getIsVIP()) {
			System.out.print("您不是会员，所以我要给你看广告：\t");
			// 获取广告内容
			System.out.println(as.queryValue("广告"));
		}
	}

	private void systemSettings() {
		// AdminSettings ass = as.queryAdminSettings();
		System.out.println("-----------下面是管理员设置-----------");
		System.out.println("1. 设置脚蹬车价格");
		System.out.println("2. 设置助力车价格");
		System.out.println("3. 设置开会员价格");
		System.out.println("4. 设置会员的折扣");
		System.out.println("5. 设置站内的广告");
		System.out.print("请选择设置：");
		int index = -1;
		while (true) {
			String s = input.next();
			if (iiv.isNumber(s)) {
				index = Integer.parseInt(s);
				if (index > 5) {
					System.out.print("不存在此选项,请重新输入：");
				} else {
					break;
				}

			} else {
				System.out.print("输入不合法，请重新选择：");
			}

		}

		switch (index) {
		case 1:
			System.out.print("请输入脚蹬车价格：");
			while (true) {
				String price = input.next();
				if (iiv.isDouble(price)) {
					as.doUpdate(price, "脚蹬车");
					System.out.println("设置成功");
					break;
				} else {
					System.out.print("输入不合法，请重新输入脚蹬车价格：");
				}
			}
			break;
		case 2:
			System.out.print("请输入助力车价格：");
			while (true) {
				String price = input.next();
				if (iiv.isDouble(price)) {
					as.doUpdate(price, "助力车");
					System.out.println("设置成功");
					break;
				} else {
					System.out.print("输入不合法，请重新输入助力车价格：");

				}
			}
			break;
		case 3:
			System.out.print("请输入会员/月价格：");
			while (true) {
				String price = input.next();
				if (iiv.isDouble(price)) {
					as.doUpdate(price, "会员价格");
					System.out.println("设置成功");
					break;
				} else {
					System.out.print("输入不合法，请重新输入：");

				}
			}
			break;
		case 4:
			System.out.print("请输入会员享受的折扣：");
			while (true) {
				String discount = input.next();
				if (iiv.isDouble(discount)) {
					as.doUpdate(discount, "折扣");
					System.out.println("设置成功");
					break;
				} else {
					System.out.print("输入不合法，请重新输入：");

				}
			}
			break;
		case 5:
			System.out.print("请输入广告内容：");
			String advertising = input.next();
			// ass.setAdvertising(advertising);
			// as.queryAlloptions().get(4).setValue(advertising);
			as.doUpdate(advertising, "广告");
			System.out.println("设置成功");
			break;
		default:
			System.out.print("没有该选项，请重新选择：");
		}

		returnMenu();

	}

	/**
	 * 添加车辆的时候，显示所有位置的大体信息
	 */
	private void addBikequeryLocation() {
		printBoundary();
		System.out.println("下面是所有的位置信息：");
		List<Location> locations = locationDao.queryAll();
		System.out.println("编号\t位置名词\t车辆总数");
		for (Location location : locations) {
			System.out.println(location);
		}
	}

	private void Location() {
		System.out.println("------------下面是所有的位置信息------------");
		List<Location> locations = locationDao.queryAll();
		System.out.println("编号\t位置名词\t车辆总数");
		for (Location location : locations) {
			System.out.println(location);
		}

		System.out.print("查询指定位置：[指定： ID | 返回：r ]  ：");

		int locationID = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				locationID = Integer.parseInt(str);
				Location lo = locationDao.queryLocation(locationID);
				if (lo == null) {
					System.out.print("此位置不存在，请重新输入位置ID：");
				} else {
					break;
				}

			} else {
				adminMenu();
			}

		}

		printBoundary();
		Location lo = locationDao.queryLocation(locationID);
		System.out.println("编号\t类型\t价格\t位置\t状态\t次数\t二维码");

		List<Bike> bikesByLo = locationDao.queryBikesByLocation(lo.getId());
		// List<Bike> bs = lo.getBikes();
		for (Bike bike : bikesByLo) {

			System.out.println(bike);
		}
		returnMenu();
	}

	private void logout() {
		printBoundary();
		user = null;
		System.out.println("您已退出，期待您再次登录！");
		mainMenu();

	}

	private void leaseRecord() {
		if (user.getIsAdmin()) {
			System.out.println("----------下面是所有用户的单车租赁记录-----------");
			List<LeaseRecord> bike = leaseRecordDao.queryAll();

			System.out.println("编号\t自行车ID\t租赁用户\t租借时间\t\t归还时间\t\t起始位置\t\t\t骑行时间\t  消费金额");
			if (bike.isEmpty()) {
				printBoundary();
				System.out.println("你的生意惨淡，没有任何人借车");
			}
			for (LeaseRecord leaseRecord : bike) {
				System.out.println(leaseRecord);
			}
			returnMenu();
		} else {
			System.out.println("-----------下面是您的单车租赁记录----------");
			List<LeaseRecord> bike = leaseRecordDao.queryByUserId(user.getId());
			System.out.println("编号\t自行车ID\t租赁用户\t租借时间\t\t归还时间\t\t起始位置\t\t\t骑行时间\t  消费金额");
			if (bike.isEmpty()) {
				System.out.println("您没有租借任何单车，赶快租借一辆试试吧！");
			}
			for (LeaseRecord leaseRecord : bike) {
				System.out.println(leaseRecord);
			}
			returnMenu();
		}

	}

	private void userInfo() {
		if (user.getIsAdmin()) {
			System.out.println("----------------------下面是所有会员信息----------------------");
			List<User> user = userDao.queryAll();
			System.out.println("编号\t用户名\t用户手机号\t\t总骑行时间\t注册时间");
			for (User user2 : user) {
				System.out.println(user2);
			}
			returnMenu();
		} else {
			System.out.println("----------------------下面是您的个人信息----------------------");
			User user = userDao.queryByUserId(this.user.getId());
			System.out.println("编号\t用户名\t用户手机号\t\t总骑行时间\t注册时间");
			System.out.println(user);
			returnMenu();
		}

	}

	/**
	 * 根据ID删除单车
	 */
	private void deleteBike() {
		printBoundary();
		System.out.print("请输入您要删除的单车ID：");
		Bike bike = null;
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				bike = bikeDao.queryById(bikeId);
				if (bike == null) {
					System.out.print("不存在此id,");
				} else if (bike.getStatus() == 0) {
					System.out.print("此单车不能被删除");
				} else {
					break;
				}
			} else {
				System.out.print("输入不合法，");
			}

			System.out.print("请重新输入要删除的ID：");
		}

		if (bikeDao.doDelete(bikeId) == 1) {
			System.out.println("删除成功");
		} else {
			System.out.println("删除失败");
		}
		System.out.print("是否继续删除？[ 继续 ：y | 返回：r ]  : ");
		String againEdit = input.next();
		if (againEdit.equalsIgnoreCase("y")) {
			deleteBike();
		} else {
			adminMenu();
		}

	}

	/**
	 * 根据ID修改单车信息
	 */
	private void editBike() {
		printBoundary();
		Bike bike = null;
		System.out.print("请输入您要修改的单车ID：");
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				bike = bikeDao.queryById(bikeId);

				if (bike == null) {
					System.out.print("没有该ID,请重新输入ID：");
				} else if (bike.getStatus() == 0) {
					System.out.print("此车不允许修改，请重新输入ID：");

				} else {
					break;
				}
			} else {
				System.out.print("输入不合法,请重新输入：");
			}
		}

		System.out.print("请输入单车类型 [ 脚蹬车：1 | 助力车： 2 ] ：");
		String type;
		double price;
		while (true) {
			type = input.next();
			if (type.equals("1")) {
				// price = as.queryAdminSettings().getaBikePrice();
				price = Double.parseDouble(as.queryValue("脚蹬车"));
				break;
			} else if (type.equals("2")) {
				// price = as.queryAdminSettings().getbBikePrice();
				price = Double.parseDouble(as.queryValue("助力车"));
				break;
			} else {
				System.out.print("没有该车型，请重新输入：");
			}
		}

		System.out.print("请输入位置ID：");
		int locationId = 1;
		while (true) {

			String str = input.next();
			if (iiv.isNumber(str)) {
				locationId = Integer.parseInt(str);
				if (locationDao.queryLocation(locationId) == null) {
					System.out.print("没有该位置，");
				} else {
					break;
				}

			} else {
				System.out.print("输入不合法，");
			}
			System.out.print("请重新选择位置ID：");
		}

		System.out.print("请输入单车状态[ 可借：1 | 借出：0 | 损坏 ：-1 ] ：");
		int status = 1;
		while (true) {
			String str = input.next();
			if (iiv.isInt(str)) {
				status = Integer.parseInt(str);
				if (status != 1 && status != 0 && status != -1) {
					System.out.print("没有此状态，请重新输入：");
				} else {
					break;
				}
			} else {
				System.out.print("状态输入不合法，请重新输入：");
			}
		}
		System.out.print("请输入被租借次数：");
		int amount = 0;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				amount = Integer.parseInt(str);
				break;
			} else {
				System.out.print("输入不合法，请重新输入：");
			}
		}

		// Bike bike2 = new Bike(bikeId, type, price, locationId, status, amount);
		String types = (type.equals("1") ? "脚蹬车" : "助力车");
		// locationId = status == 0 ? -1 : locationId; // 如果将车辆修改为借出状态，更新车辆位置
		bike.setType(types);
		bike.setPrice(price);
		bike.setLastLocationId(bike.getLocationId());
		bike.setLocationId(locationId);
		bike.setStatus(status);
		bike.setAmount(amount);

		int doUpdate = bikeDao.doUpdate(bike);
		if (doUpdate == 1) {
			if (status == 0) {
				// locationDao.updateLocationBikes(bike.getLastLocationId());
			} else if (status == 1) {
				// locationDao.updateLocationBikes(bike.getLastLocationId());
				// locationDao.updateLocationBikes(locationId);
			} else {
				// locationDao.updateLocationBikes(bike.getLocationId());

			}
			System.out.print("修改成功！");
		} else {
			System.out.print("修改失败！");

		}
		System.out.print("是否继续修改？[ 继续 ：y | 返回：r ]  : ");
		String againEdit = input.next();
		if (againEdit.equalsIgnoreCase("y")) {
			editBike();

		} else {
			adminMenu();
		}

	}

	/**
	 * 添加单车
	 */
	private void saveBike() {
		printBoundary();
		System.out.print("请输入单车类型[ 脚蹬车：1 | 助力车： 2 ] ：");
		String type = "";
		while (true) {
			type = input.next();
			if (type.equals("1")) {
				System.out.println(as.queryValue("脚蹬车"));
				break;
			} else if (type.equals("2")) {
				break;
			} else {
				System.out.print("没有该车型，请重新输入：");
			}
		}

		addBikequeryLocation();
		System.out.print("您要将该车添加到哪个位置？(ID)：");
		int locationId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				locationId = Integer.parseInt(str);
				if (locationDao.queryLocation(locationId) == null) {
					System.out.print("该位置不存在");
				} else {
					break;
				}

			} else {
				System.out.print("输入不合法，");
			}
			System.out.print("请重新选择位置ID：");
		}

		// 根据输入选择车型
		String types = (type.equals("1") ? "脚蹬车" : "助力车");
		// 生成要添加到车辆信息，默认起始位置相同，状态为1（可借），次数为0，二维码添加的时候生成，所以这里随便传入一个字符串
		Bike bike = new Bike(types, locationId, locationId, 1, 0, "");
		int doInsert = bikeDao.doInsert(bike);
		if (doInsert == 1) {

			System.out.println("添加成功");
		} else {
			System.out.println("添加失败");
		}
		System.out.print("是否继续添加？[ 继续 ：Y | 返回：R ] :");
		String againAdd = input.next();
		if (againAdd.equalsIgnoreCase("y")) {
			saveBike(); // TODO 调用自身了
		} else {
			adminMenu();
		}
	}

	/**
	 * 查询所有的单车
	 */
	private void queryBikes() {
		System.out.println("---------------------下面是系统内所有的单车相关信息-----------------------");
		List<Bike> bike = bikeDao.queryAll();
		System.out.println("编号\t类型\t价格\t位置\t状态\t次数\t二维码");
		for (Bike bike2 : bike) {
			System.out.println(bike2);
		}
		returnMenu();
	}

	private void queryUserBikes() {
		System.out.println("---------------------下面是系统内所有的单车相关信息-----------------------");
		List<Bike> bike = bikeDao.queryAll();
		System.out.println("编号\t类型\t价格\t位置\t状态\t次数\t二维码");
		for (Bike bike2 : bike) {
			if (bike2.getStatus() != 1) {
				continue;
			}
			System.out.println(bike2);
		}
		returnMenu();
	}

	/**
	 * 普通用户主界面
	 */
	private void userMenu() {
		printBoundary();
		System.out.println("尊敬的" + user.getUsername() + "用户，您好！");
		advertising(user);
		System.out.println("\t1.查询单车");
		System.out.println("\t2.租借单车");
		System.out.println("\t3.归还单车");
		System.out.println("\t4.个人信息");
		System.out.println("\t5.个人钱包");
		System.out.println("\t6.租赁记录");
		System.out.println("\t7.故障报修");
		System.out.println("\t8.充值金额");
		System.out.println("\t9.消费记录");
		System.out.println("\t10.开通会员");
		System.out.println("\t11.个人设置");
		System.out.println("\t12.退出登录");
		System.out.println("\t13.退出系统");

		System.out.print("请选择您接下来的操作:");
		int index = 1;
		while (true) {
			String nextInt = input.next();

			if (iiv.isNumber(nextInt)) {
				index = Integer.parseInt(nextInt);
				if (index > 13) {
					System.out.print("没有该选项，请重新输入:");
				} else {
					break;
				}

			} else {
				System.out.print("输入不合法，请重新输入：");
			}
		}

		switch (index) {
		case 1:
			queryUserBikes();
			break;
		case 2:
			leaseBike();
			break;
		case 3:
			returnBike();
			break;
		case 4:
			userInfo();
			break;
		case 5:
			// personWallet();
			usersWallet();
			break;
		case 6:
			leaseRecord();
			break;
		case 7:
			awardByRepairs();// 报修奖励
			break;
		case 8:
			recharge(true);
			break;
		case 9:
			usersBill();
			break;
		case 10:
			becomeVIPMenu();
			break;
		case 11:
			userSettings();// 退出
			break;
		case 12:
			logout();// 退出登录
			break;
		case 13:
			exit();// 退出
			break;
		default:
			System.out.print("没有该选项，请重新输入：");
		}

	}

	private void userBikes() {
		printBoundary();
		Location lo = locationDao.queryLocation(user.getLocationID());
		System.out.print("您的当前位置是[ " + lo.getLocationName() + " ]");
		System.out.println("，下面是该位置下的所有的单车信息");
		List<Bike> bike = locationDao.queryBikesByLocation(lo.getId());
		System.out.println("编号\t类型\t价格\t位置\t状态\t次数\t二维码");
		for (Bike bike2 : bike) {
			System.out.println(bike2);
		}

	}

	private void awardByRepairs() {
		printBoundary();
		System.out.print("请输入损坏的车辆:");
		String id = input.next();
		while (true) {
			if (iiv.isNumber(id)) {
				int bikeId = Integer.parseInt(id);
				int status = bikeDao.bikeStatus(bikeId);
				if (status == -1) {
					System.out.print("该车已经损坏,");
					returnMenu();
				}

				int walletId = walletDao.queryByUserId(bikeDao.setDamage(user, bikeId)).getId();

				// billDao.awardByBike(user.getId(), walletId);
				walletDao.awardByBike(user.getId(), walletId);

				break;
			} else {
				System.out.print("输入不合法，请重新输入：");
			}
		}
		System.out.print("该车辆已经报修！感谢您为城市的环境做出贡献！");
		returnMenu();

	}

	private void userSettings() {
		// UserSettings ps = us.queryUserSetting(user.getId());
		;
		System.out.println("-----------下面是您的设置-----------");
		System.out.println("1.免密支付");
		System.out.println("2.修改密码");
		System.out.print("请选择设置：");
		int index = -1;
		while (true) {
			String s = input.next();
			if (iiv.isNumber(s)) {
				index = Integer.parseInt(s);
				if (index > 2) {
					System.out.print("不存在此选项,请重新输入：");
				} else {
					break;
				}

			} else {
				System.out.print("输入不合法，请重新选择：");
			}

		}

		switch (index) {
		case 1:
			System.out.println("----------免密支付设置----------");
			String s = us.queryUserSetting(user.getId(), "免密支付");
			System.out.println(s);
			System.out.println("您目前免密支付设置为：" + (s.equals("1") ? "开" : "关"));
			System.out.print("请更改您的设置 ： [ 打开：t | 关闭 f | 返回 r ] ：");
			// System.out.println("打开：t,关闭：f,任意键返回");
			String auto = input.next();
			if (auto.equalsIgnoreCase("t")) {
				// ps.setActp(true);
				// us.sestValues(new UserOptions(user.getId(), "免密支付", "1"));
				us.doUpdate(new UserOptions(user.getId(), "免密支付", "1"));
				System.out.print("设置成功！");
				returnMenu();

			} else if (auto.equalsIgnoreCase("f")) {
				System.out.println("设置成功！");
				// ps.setActp(false);
				// us.sestValues(user.getId(), "免密支付", "0");
				us.doUpdate(new UserOptions(user.getId(), "免密支付", "0"));
				returnMenu();
			} else {
				userMenu();
			}
			break;
		case 2:
			retrievePassword(user.getId());
			System.out.println("修改成功，请重新登录：");
			userLogin();
			break;
		}

	}

	private void recharge(boolean b) {
		// UserSettings ps = us.queryUserSetting(user.getId());
		double m = 0;
		System.out.print("请输入充值金额：");
		while (true) {
			String money = input.next();
			if (iiv.isDouble(money)) {
				m = Double.parseDouble(money);
				break;

			} else {
				System.out.print("输入不合法，请重新输入：");
			}
		}

		boolean openPayPassword = (us.queryUserSetting(user.getId(), "免密支付").equals("1") ? true : false);
		// System.out.println("shifou" + openPayPassword);
		while (!openPayPassword) {
			System.out.print("请输入您的支付密码：");
			String payPassword = input.next();
			if (isTruePayPw(user, payPassword)) {
				break;
			} else {
				System.out.print("支付密码不正确,请重新输入：");
			}
		}

		if (walletDao.recharge(user.getId(), m) == 1) {
			// if (walletDao.recharge(walletDao.queryByUserId(user.getId()).getId(),m) == 1)
			// {
			System.out.println("充值成功 ！\t");
		} else {
			System.out.println("充值失败  ！\t");

		}

		if (b) {
			returnMenu();
		}
	}

	// private void personWallet() {
	// System.out.println("个人钱包显示界面");
	// System.out.println(user.getId());
	// Wallet wallet = walletDao.queryByUserId(user.getId());
	// System.out.println(wallet.getId());
	// System.out.println("编号\t用户名\t用户余额\t优惠券余额\t用户等级\tVIP时间");
	// System.out.println(wallet);
	// returnMenu();
	// }

	private void becomeVIPMenu() {
		// double vipPrice = as.queryAdminSettings().getVipPrice();
		double vipPrice = Double.parseDouble(as.queryValue("会员价格"));
		// double zc = as.queryAdminSettings().getDiscount();
		double zc = Double.parseDouble(as.queryValue("折扣"));
		System.out.println("现在开通会员只需要" + vipPrice + "元/月，您可以享受免广告和租单车" + (int) (zc * 10) + "折优惠");
		System.out.print("请输入您要开通的月份：");

		int month = 0;
		while (true) {
			String m = input.next();
			if (iiv.isNumber(m)) {
				month = Integer.parseInt(m);
				break;
			} else {
				System.out.print("输入不合法，请重新输入：");
			}
		}

		// UserSettings ps = us.queryUserSetting(user.getId());
		boolean openPayPassword = (us.queryUserSetting(user.getId(), "免密支付").equals("1") ? true : false);

		while (!openPayPassword) {
			System.out.print("请输入您的支付密码：");
			String payPassword = input.next();
			if (isTruePayPw(user, payPassword)) {
				break;
			} else {
				System.out.print("支付密码不正确，");

			}
		}
		while (true) {
			int result = walletDao.becomeVIP(user.getId(), month);
			if (result == -5) {
				System.out.print("余额不足，");
				recharge(false);
			} else {
				System.out.print("恭喜您开通成功，");
				Wallet w = walletDao.queryByUserId(user.getId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String time = sdf.format(w.getVipDate());
				System.out.println("会员到期时间：" + time);
				break;
			}
		}
		returnMenu();

	}

	private boolean isTruePayPw(User user, String payPassword) {
		List<User> users = userDao.queryAll();

		for (User user2 : users) {
			if (user2.getPayPassword().equals(payPassword) && user2.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}

	private void returnBike() {
		List<LeaseRecord> usr = leaseRecordDao.queryNotReturnByUserId(user.getId());
		printBoundary();
		if (usr.isEmpty()) {
			System.out.println("您还没有租借过单车~");
			returnMenu();
		} else {
			List<LeaseRecord> ld = leaseRecordDao.queryNotReturnByUserId(user.getId());
			System.out.println("------------------------------您有以下车辆未归还---------------------------------");
			System.out.println("编号\t自行车ID\t租赁用户\t租借时间\t\t归还时间\t\t起始位置\t\t\t骑行时间\t  消费金额");
			for (LeaseRecord leaseRecord : ld) {
				System.out.println(leaseRecord);
			}
		}
		System.out.print("请输入您要归还的单车Id：");
		int bikeId = -1;
		// while (true) {
		// String str = input.next();
		// if (iiv.isNumber(str)) {
		// bikeId = Integer.parseInt(str);
		// if (bikeDao.bikeStatus(bikeId) != 10 ||
		// (!leaseRecordDao.isCurrentUserLease(user.getId(), bikeId))) {
		// System.out.print("您没有租借该单车,请重新输入：");
		// } else {
		// while (true) {
		// // UserSettings ps = us.queryUserSetting(user.getId());
		// boolean openPayPassword = (us.queryUserSetting(user.getId(),
		// "免密支付").equals("1") ? true
		// : false);
		//
		// while (!openPayPassword) {
		// System.out.print("请输入您的支付密码：");
		// String payPassword = input.next();
		// if (isTruePayPw(user, payPassword)) {
		// break;
		// } else {
		// System.out.print("支付密码不正确，");
		//
		// }
		// }
		// int i = leaseRecordDao.returnBike(bikeId, user.getId());
		// if (i == -5) {
		// System.out.print("余额不足，");
		// recharge(false);
		// } else {
		// System.out.println("归还成功!");
		// break;
		// }
		// }
		// break;
		// }
		// } else {
		// System.out.print("输入不合法,请重新输入：");
		// }
		// }
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				Bike bike = bikeDao.queryById(bikeId);
				if (bike == null) {
					System.out.print("该车辆不存在，请重新输入：");
					
				} else if (bike.getStatus() != 0 || leaseRecordDao.queryNotReturnRecordId(bikeId).getUserId() != user.getId()) {
					System.out.print("您没有租借该单车,请重新输入：");
				} else {
					//进入此层说明找到了该用户所借车的那条记录
					while (true) {
						boolean openPayPassword = (us.queryUserSetting(user.getId(), "免密支付").equals("1") ? true : false);
						while (!openPayPassword) {
							System.out.print("请输入您的支付密码：");
							String payPassword = input.next();
							if (isTruePayPw(user, payPassword)) {
								break;
							} else {
								System.out.print("支付密码不正确，");
							}
						}
						int i = leaseRecordDao.returnBike(bikeId, user.getId());
						if (i == -5) {
							System.out.print("余额不足，");
							recharge(false);
						} else {
							System.out.println("归还成功!");
							break;
						}
					}
					break;
				}
			} else {
				System.out.print("输入不合法,请重新输入：");
			}
		}
		returnMenu();

	}

	private void leaseBike() {
		userBikes();
		System.out.print("请输入您要租借的单车ID\t[ 租借：ID | 返回 ：r ] ：");
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (str.equalsIgnoreCase("r")) {
				userMenu();
				break;
			} else {
				if (iiv.isNumber(str)) {
					bikeId = Integer.parseInt(str);
					Bike bike = bikeDao.queryById(bikeId);
					if (bike == null) {
						System.out.print("此ID不存在，请重新输入单车Id：");
						// 如果
					} else if (bike.getLocationId() != user.getLocationID()) {
						System.out.print("该车辆不在当前位置中，请重新输入单车Id：");
					} else if (bike.getStatus() == 0) {
						System.out.println("该车辆已经被租出，请重新输入单车Id：");
					} else if (bike.getStatus() == -1) {
						System.out.println("该车辆已经损坏，请重新输入单车Id：");
					} else if (bike.getStatus() == 1) {
						int result = leaseRecordDao.doInsert(user.getId(), bikeId);
						if (result == 1) {
							System.out.print("借出成功！");
							break;
						}
					}
				} else {
					System.out.print("输入不合法,请重新输入：");
				}
			}
		}
		returnMenu();
	}

	/**
	 * 注册奖励
	 * @param user1Id 注册用户id
	 * @param wallet1Id 注册用户钱包
	 */
	private void awardByRegist(int user1Id, int wallet1Id) {
		int user2Id;
		System.out.print("是否有推荐人？[ 有：y | 没有 n ] :");
		String y = input.next();
		if (y.equalsIgnoreCase("y")) {
			System.out.print("请输入推荐人ID:");
			while (true) {
				String id = input.next();
				if (iiv.isNumber(id)) {
					user2Id = Integer.parseInt(id);
					break;
				} else {
					System.out.print("输入不合法,请重新输入：");
				}
			}
			int x = walletDao.awardByregister(user1Id, wallet1Id, user2Id);
			if (x != 1) {
				System.out.println("此推荐人无效！"); // TOOD 无效重新输入
			}
		}
	}

	/**
	 * 用户注册
	 */
	private void userRegister() {
		System.out.print("请输入您的用户名：");
		String username;
		while (true) {
			username = input.next();
			if (userDao.isUserNameExist(username)) {
				System.out.println("该用户名已经存在,建议您使用：" + userDao.adviseUsername(username));
				printBoundary();
				System.out.print("请重新输入用户名：");
			} else {
				break;
			}
		}

		String password, password2;
		while (true) {
			System.out.print("请输入您的密码：");
			password = input.next();
			System.out.print("再次输入您的密码：");
			password2 = input.next();
			if (!password.equals(password2)) {
				System.out.println("两次密码不一致，请再次输入：");
				printBoundary();
			} else {
				break;
			}
		}
		String payPassword = "";
		System.out.print("请输入您的支付密码：");
		while (true) {
			payPassword = input.next();
			if (payPassword.equals(password)) {
				System.out.print("支付密码不能和登录密码一致！请重新输入：");
			} else {
				break;
			}
		}

		String tel;
		System.out.print("请输入您的手机号：");
		while (true) {
			tel = input.next();
			if (iiv.isTrueTel(tel)) {
				// 手机号存在返回true
				if (userDao.isTelExist(tel)) {
					System.out.print("该手机号已经存在，请重新输入手机号：");
				} else {
					break;
				}
			} else {
				System.out.print("手机号不合法，哪有这种手机号啊，请重新输入手机号：");
			}

		}

		int register = userDao.register(username, password, tel, payPassword);

		if (register != 0) {

			awardByRegist(register, walletDao.queryByUserId(register).getId());
			System.out.print("注册成功，是否登录？[ 是：y | 否： n ] ：");
			String s = input.next();
			if (s.equalsIgnoreCase("y")) {
				userLogin();
			} else {
				mainMenu();
			}
		} else {
			System.out.println("注册失败");
		}
	}

	/**
	 * 用户退出系统
	 * 
	 * @Title exit
	 */
	private void exit() {
		printBoundary();
		System.out.println("确认退出？不是手抖？[ 确认：y | 手抖：n ] ");
		String s = input.next();
		if (!s.equalsIgnoreCase("y")) {
			if (user == null) {
				mainMenu();
				return;
			}
			if (user.getIsAdmin()) {
				adminMenu();// 管理员菜单
			} else {
				userMenu();// 普通用户菜单
			}
		} else {
			printBoundary();
			System.out.println("您已经退出系统，没有后悔的余地了,期待您再次使用！");
			input.close();
			System.exit(0);
		}
	}

	/**
	 * 返回到mainMenu
	 * 
	 * @Title returnMenu
	 */
	private void returnMenu() {

		System.out.print(" [ 返回：y | 退出 ：e ] :");
		String next = input.next();

		if (next.equalsIgnoreCase("y")) {
			if (user == null) {
				mainMenu();
				return;
			}
			if (user.getIsAdmin()) {
				adminMenu();// 管理员菜单
			} else {
				userMenu();// 普通用户菜单
			}
		} else {
			exit();
		}
	}

	/**
	 * 隔离选项，用来美化控制台输出的哈哈
	 */
	private void printBoundary() {
		System.out.println("---------------------------------------");
	}

}
