package tv.zhangjia.bike.util;

import java.util.List;
import java.util.Scanner;

import tv.zhangjia.bike.dao.AdminSettingsDao;
import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.UserSettingsDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.dao.impl.AdminSettinsDaoImpl;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.dao.impl.BillDaoImpl;
import tv.zhangjia.bike.dao.impl.LeaseRecordDaoImpl;
import tv.zhangjia.bike.dao.impl.LocationDaoImpl;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.dao.impl.UserSettingsDaoImpl;
import tv.zhangjia.bike.dao.impl.WalletDaoImpl;
import tv.zhangjia.bike.entity.AdminSettings;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.UserSettings;
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
	private AdminSettingsDao as = new AdminSettinsDaoImpl();
	private UserSettingsDao us = new UserSettingsDaoImpl();

	/**
	 * 主菜单，进入该系统的用户看到的第一个界面
	 * 
	 * @Title mainMenu
	 */
	public void mainMenu() {
		System.out.println("---欢迎您使用共享单车租赁系统---");
		System.out.println("\t1.登录");
		System.out.println("\t2.注册");
		System.out.println("\t3.退出 ");
		System.out.println("-------你看到我的底线了-------");
		System.out.println();
		System.out.print("请选择您接下来的操作:");

		// 用while循环实现如果选项不存在，重新输入
		while (true) {
			String choose = input.next();
			// 判断输入的内容是否为整数
			if (iiv.isNumber(choose)) {
				int index = Integer.parseInt(choose);
				switch (index) {
				case 1:
					userLogin();
					break;
				case 2:
					userRegister();
					break;
				case 33:
					exit();
					break;
				default:
					System.out.print("没有该选项，请重新输入：");
				}
			} else {
				System.out.print("输入不合法,，请重新输入：");
			}

		}
	}

	private User retrievePassword(int userId) {
		System.out.println("密码错误，是否找回密码？");
		String s = input.next();
		if (s.equals("y")) {
			System.out.println("请输入您的手机号：");
			while (true) {
				String tel = input.next();

				int x = userDao.isTrueTel(userId, tel);
				if (x != 1) {
					System.out.println("该手机号和您的用户名不匹配,请重新输入：");
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
			System.out.println("   验证成功！");

			System.out.println("请输入新密码：");
			String newPassword = input.next();
			return userDao.retrievePassword(userId, newPassword);
		} else {
			mainMenu();
		}
		return null;
	}

	/**
	 * 用户登录
	 */
	private void userLogin() {

		System.out.println("-----------------------------------");
		System.out.print("请输入您的用户名：");
		String username;
		while (true) {
			username = input.next();
			if (userDao.isTrueUserName(username) != 1) {
				System.out.println("没有该用户名,请重新输入");
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
					login = retrievePassword(userDao.queryUserId(username));
					userLogin();
					break;
				}
				System.out.println("登录失败，您的密码错误,请重新输入：");
			} else {
				break;
			}
		}

		this.user = login;
		if (user.isAdmin()) {
			adminMenu();
		} else {
			userMenu();
		}

	}

	/**
	 * 管理员主界面
	 */
	private void adminMenu() {
		System.out.println("-----------------------------------");
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
		System.out.print("请选择您接下来的操作:");
		while (true) {
			String nextInt = input.next();
			if (iiv.isNumber(nextInt)) {
				int index = Integer.parseInt(nextInt);
				switch (index) {
				case 1:
					queryBike();
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
					queryLocation();//
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
					queryUsersWallet();//
					break;
				case 11:
					queryUsersBill();//
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
			} else {
				System.out.println("输入不合法，请重新输入：");
			}
		}

	}

	private void queryUsersBill() {
		System.out.println("下面是用户的钱包信息");
		List<Bill> bills = billDao.queryAll();
		for (Bill bill : bills) {
			System.out.println(bill);
		}

	}

	private void queryUsersWallet() {
		System.out.println("下面是用户的钱包信息");
		List<Wallet> wallets = walletDao.queryAll();
		for (Wallet wallet : wallets) {
			System.out.println(wallet);
		}

	}

	private void damage() {
		List<Bike> bikes = bikeDao.queryByDamage();
		System.out.println("下面是损坏的车辆");
		for (Bike bike : bikes) {

			System.out.println(bike);
		}

	}

	private void dispatch() {
		List<String> arr = locationDao.dispatch();
		for (String string : arr) {
			System.out.println(string);
		}
		locationDao.dispatch();

	}

	private void advertising(User user) {
		Wallet wt = walletDao.queryByUserId(user.getId());
		if (wt.isVIP()) {

		} else {
			AdminSettings ass = as.queryAdminSettings();
			System.out.println(ass.getAdvertising());
		}
	}

	private void systemSettings() {
		AdminSettings ass = as.queryAdminSettings();
		System.out.println("下面是管理员设置");
		System.out.println("1. 设置脚蹬车价格");
		System.out.println("2. 设置助力车价格");
		System.out.println("3. 设置开会员价格");
		System.out.println("4. 设置会员的折扣");
		System.out.println("5. 设置站内的广告");
		System.out.println("请选择设置：");
		while (true) {
			String index = input.next();
			if (iiv.isNumber(index)) {
				switch (Integer.parseInt(index)) {
				case 1:
					System.out.println("请输入脚蹬车价格：");
					while (true) {
						String price = input.next();
						if (iiv.isDouble(price)) {
							double dprice = Double.parseDouble(price);
							ass.setaBikePrice(dprice);
							System.out.println("设置成功");
							break;
						} else {
							System.out.println("输入不合法，请重新输入：");

						}
					}
					break;
				case 2:
					System.out.println("请输入助力车价格：");
					while (true) {
						String price = input.next();
						if (iiv.isDouble(price)) {
							double dprice = Double.parseDouble(price);
							ass.setbBikePrice(dprice);
							System.out.println("设置成功");
							break;
						} else {
							System.out.println("输入不合法，请重新输入：");

						}
					}
					break;
				case 3:
					System.out.println("请输入会员/月价格：");
					while (true) {
						String price = input.next();
						if (iiv.isDouble(price)) {
							double dprice = Double.parseDouble(price);
							ass.setVipPrice(dprice);
							System.out.println("设置成功");
							break;
						} else {
							System.out.println("输入不合法，请重新输入：");

						}
					}
					break;
				case 4:
					while (true) {
						String discount = input.next();
						if (iiv.isDouble(discount)) {
							double ddiscount = Double.parseDouble(discount);
							ass.setDiscount(ddiscount);
							System.out.println("设置成功");
							break;
						} else {
							System.out.println("输入不合法，请重新输入：");

						}
					}
					break;
				case 5:
					System.out.println("请输入广告内容：");
					String advertising = input.next();
					ass.setAdvertising(advertising);
					System.out.println("设置成功");
					break;
				default:
					System.out.println("没有该选项，请重新选择：");
				}
			} else {
				System.out.println("输入不合法，请重新选择：");
			}

			adminMenu();
		}

	}

	private void addBikequeryLocation() {
		System.out.println("下面是所有的位置信息：");
		List<Location> locations = locationDao.queryAll();
		System.out.println("编号\t位置名词\t车辆总数");
		for (Location location : locations) {
			System.out.println(location);
		}
	}

	private void queryLocation() {
		System.out.println("下面是所有的位置信息：");
		List<Location> locations = locationDao.queryAll();
		System.out.println("编号\t位置名词\t车辆总数");
		for (Location location : locations) {
			System.out.println(location);
		}

		while (true) {
			System.out.print("查询指定位置：");
			int id = input.nextInt();
			if (id == -1)
				break;
			Location lo = locationDao.queryLocation(id);
			System.out.println("编号\t位置名词\t车辆总数");
			List<Bike> bs = lo.getBikes();
			for (Bike bike : bs) {

				System.out.println(bike);
			}

		}

		adminMenu();
	}

	private void logout() {
		System.out.println("-----------------------------------");
		// user = null;
		mainMenu();

	}

	private void leaseRecord() {
		System.out.println("-----------------------------------");
		if (user.isAdmin()) {
			System.out.println("下面是所有的单车租赁记录");
			List<LeaseRecord> bike = leaseRecordDao.queryAll();
			System.out.println("编号\t自行车ID\t用户ID\t租赁用户\t租借时间\t归还时间\t消费金额");
			for (LeaseRecord leaseRecord : bike) {
				System.out.println(leaseRecord);
			}
			returnMenu();
		} else {
			System.out.println("下面是您的单车租赁记录");
			List<LeaseRecord> bike = leaseRecordDao.queryByUserId(user.getId());
			System.out.println("编号\t自行车ID\t用户ID\t租赁用户\t租借时间\t归还时间\t消费金额");
			for (LeaseRecord leaseRecord : bike) {
				System.out.println(leaseRecord);
			}
			returnMenu();
		}

	}

	private void userInfo() {
		if (user.isAdmin()) {
			System.out.println("下面是所有会员信息");
			List<User> user = userDao.queryAll();
			System.out.println("编号\t用户名\t用户手机号\t骑行时间\t注册时间");
			for (User user2 : user) {
				System.out.println(user2);
			}
			returnMenu();
		} else {
			System.out.println("下面是您的个人信息");
			User user = userDao.queryByUserId(this.user.getId());
			System.out.println("编号\t用户名\t用户手机号\t骑行时间\t注册时间");
			System.out.println(user);
			returnMenu();
		}

	}

	/**
	 * 根据ID删除单车
	 */
	private void deleteBike() {
		System.out.println("-----------------------------------");
		System.out.println("请输入您要删除的单车ID：");
		Bike bike = null;
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				bike = bikeDao.queryById(bikeId);
				if (bike == null) {
					System.out.println("不存在此id");
				} else if (bike.getStatus() == 0) {
					System.out.println("此单车不能被删除");
				} else {
					break;
				}
			} else {
				System.out.print("输入不合法，");
			}

			System.out.println("请重新输入要删除的ID");
		}

		int locationId = bike.getLocationId();
		if (bikeDao.doDelete(bikeId)) {
//			locationDao.deleteLocationBikes(locationId,bikeId);
			locationDao.updateLocationBikes(locationId);
			System.out.println("删除成功");
		} else {
			System.out.println("删除失败");
		}

		System.out.println("是否继续删除？");
		String againDe = input.next();
		if (againDe.equals("y")) {
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
		System.out.println("请输入您要修改的单车ID");
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				bike = bikeDao.queryById(bikeId);

				if (bike == null) {
					System.out.println("没有该ID");
				} else if (bike.getStatus() != 1) {
					System.out.println("此车不允许修改，请重新输入ID：");

				} else {
					break;
				}
			} else {
				System.out.println("输入不合法");
			}
		}

		System.out.println("请输入单车类型：");
		String type;
		double price;
		while (true) {
			type = input.next();
			if (type.equals("脚蹬车")) {
				price = as.queryAdminSettings().getaBikePrice();
				break;
			} else if (type.equals("助力车")) {
				price = as.queryAdminSettings().getbBikePrice();
				break;
			} else {
				System.out.println("没有该车型，请重新输入：");
			}
		}

		System.out.println("请输入位置ID：");
		int locationId = 1;
		while (true) {

			String str = input.next();
			if (iiv.isNumber(str)) {
				locationId = Integer.parseInt(str);
				if (locationDao.queryLocation(locationId) == null) {
					System.out.println("没有该位置");
				} else {
					break;
				}

			} else {
				System.out.print("输入不合法，");
			}
			System.out.println("请重新选择位置ID：");
		}

		System.out.println("请输入状态：");
		int status = 1;
		while (true) {
			String str = input.next();
			if (iiv.isInt(str)) {
				status = Integer.parseInt(str);
				if (status != 1 && status != 0 && status != -1) {
					System.out.println("没有此状态，请重新输入：");
				} else {
					break;
				}
			} else {
				System.out.println("状态输入不合法，请重新输入：");
			}
		}
		System.out.println("请输入次数：");
		int amount = 0;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				amount = Integer.parseInt(str);
				break;
			} else {
				System.out.println("输入不合法，请重新输入：");
			}
		}
		String qr = "y";

		Bike bike2 = new Bike(bikeId, type, price, locationId, status, amount, qr);
		boolean doUpdate = bikeDao.doUpdate(bike2);
		if (doUpdate) {
			System.out.println("修改成功");
		} else {
			System.out.println("修改失败");

		}
		System.out.println("是否继续修改？");
		String againEdit = input.next();
		if (againEdit.equals("y")) {
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
		// System.out.println("添加单车");
		System.out.print("请输入单车类型（脚蹬车/助力车）：");
		String type;
		double price;
		while (true) {
			type = input.next();
			if (type.equals("脚蹬车")) {
				price = as.queryAdminSettings().getaBikePrice();
				break;
			} else if (type.equals("助力车")) {
				price = as.queryAdminSettings().getbBikePrice();
				break;
			} else {
				System.out.println("没有该车型，请重新输入：");
			}
		}

		addBikequeryLocation();
		System.out.println("您要将该车添加到哪个位置？(ID)：");

		int locationId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				locationId = Integer.parseInt(str);
				if (locationDao.queryLocation(locationId) == null) {
					System.out.println("没有该位置");
				} else {
					break;
				}

			} else {
				System.out.print("输入不合法，");
			}
			System.out.println("请重新选择位置ID：");
		}
		String qr = "验证码";
		Bike bike = new Bike(type, price, locationId, 1, 0, qr);
		boolean doInsert = bikeDao.doInsert(bike);
		if (doInsert) {

			System.out.println("添加成功");
		} else {
			System.out.println("添加失败");
		}
		System.out.println("是否继续添加？");
		String againAdd = input.next();
		if (againAdd.equals("y")) {
			saveBike();

		} else {
			adminMenu();
		}
	}

	/**
	 * 查询所有的单车
	 */
	private void queryBike() {
		printBoundary();
		System.out.println("下面是系统内所有的单车相关信息");
		List<Bike> bike = bikeDao.queryAll();
		System.out.println("编号\t类型\t价格\t位置\t状态\t次数\t二维码");
		for (Bike bike2 : bike) {
			System.out.println(bike2);
		}
		returnMenu();
	}

	/**
	 * 普通用户主界面
	 */
	private void userMenu() {
		advertising(user);
		System.out.println("尊敬的" + user.getUsername() + "用户，您好！");
		System.out.println("\t1.查询单车");
		System.out.println("\t2.租借单车");
		System.out.println("\t3.归还单车");
		System.out.println("\t4.个人信息");
		System.out.println("\t5.个人钱包");
		System.out.println("\t6.租赁记录");
		System.out.println("\t7.个人设置"); // TODO 可以选择修改个人信息，还是其他设置
		System.out.println("\t8.退出登录");
		System.out.println("\t9.退出系统");
		System.out.println("\t10.个人设置");
		System.out.println("\t11.故障报修");

		System.out.print("请选择您接下来的操作:");
		while (true) {
			String nextInt = input.next();

			if (iiv.isNumber(nextInt)) {
				int index = Integer.parseInt(nextInt);
				switch (index) {
				case 1:
					queryBike();
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
					personWallet();
					break;
				case 6:
					leaseRecord();
					break;
				case 7:
					Setting();
					break;
				case 8:
					logout();// 退出登录
					break;
				case 9:
					exit();// 退出
					break;
				case 10:
					personSettings();// 退出
					break;
				case 11:
					awardByRepairs();// 报修奖励
					break;
				default:
					System.out.print("没有该选项，请重新输入：");
				}
			} else {
				System.out.println("输入不合法，请重新输入：");
			}
		}

	}

	private void awardByRepairs() {

		System.out.println("请输入损坏的车辆");
		String id = input.next();
		while (true) {
			if (iiv.isNumber(id)) {
				int bikeId = Integer.parseInt(id);
				int status = bikeDao.bikeStatus(bikeId);
				if (status != 11) {
					System.out.println("该车无法报修");
					break;
				}

				int walletId = bikeDao.setDamage(user, bikeId);

				billDao.awardByBike(user.getId(), walletId);

				break;
			} else {
				System.out.println("输入不合法，请重新输入：");
			}
		}

	}

	private void personSettings() {
		System.out.println("下面是您的设置：");
		UserSettings ps = us.queryUserSetting(user.getId());
		String s = ps.isActp() ? "开" : "关";
		System.out.println("自动支付：" + s);
		System.out.println("打开：t,关闭：f,任意键返回");
		String auto = input.next();
		if (auto.equals("t")) {
			ps.setActp(true);
		} else if (auto.equals("f")) {
			ps.setActp(false);
		} else {
			userMenu();

		}

	}

	private void recharge() {
		UserSettings ps = us.queryUserSetting(user.getId());
		while (true) {
			System.out.println("请输入充值金额：");
			String money = input.next();
			if (iiv.isDouble(money)) {
				double m = Double.parseDouble(money);

				boolean openPayPassword = ps.isActp() ? true : false;
				System.out.println("shifou" + openPayPassword);
				while (!openPayPassword) {
					System.out.println("请输入您的支付密码：");
					String payPassword = input.next();
					if (isTruePayPw(user, payPassword)) {
						break;
					} else {
						System.out.println("支付密码不正确");

					}
				}

				if (walletDao.recharge(user.getWalletID(), m) == 1) {
					System.out.println("充值成功");
					// personWallet();
					break;
				} else {
					System.out.println("充值失败");
				}
			} else {
				System.out.print("输入不合法，");
			}
		}
	}

	private void personWallet() {
		System.out.println("个人钱包显示界面");
		System.out.println(user.getId());
		Wallet wallet = walletDao.queryByUserId(user.getId());
		System.out.println(wallet.getId());
		System.out.println("编号\t用户名\t用户余额\t优惠券余额\t用户等级\tVIP时间");
		System.out.println(wallet);
		System.out.println("X：消费记录\t C：充值 v:会员");
		String s = input.next();
		if (s.equals("x")) {
			billMenu();
		} else if (s.equals("c")) {
			recharge();
		} else if (s.equals("v")) {
			becomeVIPMenu();
		} else {

			userMenu();
		}
	}

	private void becomeVIPMenu() {
		System.out.println("请输入您要充值的月份");

		int month = 0;
		while (true) {
			String m = input.next();
			if (iiv.isNumber(m)) {
				month = Integer.parseInt(m);
				break;
			} else {
				System.out.println("请重新输入：");
			}
		}

		UserSettings ps = us.queryUserSetting(user.getId());
		boolean openPayPassword = ps.isActp() ? true : false;

		while (!openPayPassword) {
			System.out.println("请输入您的支付密码：");
			String payPassword = input.next();
			if (isTruePayPw(user, payPassword)) {
				break;
			} else {
				System.out.println("支付密码不正确");

			}
		}
		while (true) {
			int result = walletDao.becomeVIP(user.getId(), month);
			if (result == -5) {
				recharge();
			} else {
				System.out.println("恭喜您开通成功");

				System.out.println("hahahahahahhah");
				break;
			}
		}
		userMenu();

	}

	private boolean isTruePayPw(User user, String payPassword) {
		List<User> users = userDao.queryAll();

		for (User user2 : users) {
			if (user2.getPayPassword().equals(payPassword) && user.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}

	private void billMenu() {
		System.out.println("下面是您的消费账单");
		// List<Bill> userBills = billDao.queryAll();
		List<Bill> userBills = billDao.queryUserBill(user.getId());
		System.out.println("用户编号\t用户余额\t优惠券余额\t用户等级\tVIP时间");
		System.out.println(userBills);
	}

	private void Setting() {
		// TODO Auto-generated method stub

	}

	private void returnBike() {
		System.out.println("请输入您要归还的单车Id");
		int bikeId = input.nextInt();
		UserSettings ps = us.queryUserSetting(user.getId());
		boolean openPayPassword = ps.isActp() ? true : false;

		while (!openPayPassword) {
			System.out.println("请输入您的支付密码：");
			String payPassword = input.next();
			if (isTruePayPw(user, payPassword)) {
				break;
			} else {
				System.out.println("支付密码不正确");

			}
		}

		int result = leaseRecordDao.returnBike(bikeId, user.getId());

		if (result == 1) {
			System.out.println("归还成功");
			userMenu();
		} else if (result == 0 || result == 11) {
			System.out.println("您未租借该单车");
			userMenu();
		} else if (result == -5) {
			recharge();
		} else {
			System.out.println("该ID不存在");
			userMenu();
		}
	}

	private void leaseBike() {
		System.out.println("请输入您要租借的单车ID：");
		int bikeId = input.nextInt();
		int result = leaseRecordDao.doInsert(user.getId(), bikeId);
		if (result == 1) {
			System.out.println("借出成功！");
			userMenu();
		} else if (result == 10) {
			System.out.println("该车辆已经被租出");
			userMenu();
		} else if (result == 5) {
			System.out.println("该车辆ID不存在");
			userMenu();
		} else if (result == -1) {
			System.out.println("该车辆已经损坏");
			userMenu();
		}
	}

	private void awardRe(int user1Id, int wallet1Id) {
		int user2Id;
		System.out.println("是否有推荐人？y");
		String y = input.next();
		if (y.equals("y")) {
			System.out.println("请输入推荐人ID");
			while (true) {
				String id = input.next();
				if (iiv.isNumber(id)) {
					user2Id = Integer.parseInt(id);
					break;
				} else {
					System.out.println("输入不合法");
				}
			}
			int x = billDao.awardByregister(user1Id, wallet1Id, user2Id);
			if (x == -1) {
				System.out.println("不存在该用户");
			} else {
			}

		}
	}

	/**
	 * 用户注册
	 * 
	 * @Title userRegister
	 */
	private void userRegister() {
		System.out.print("请输入您的用户名：");
		String username;
		while (true) {
			username = input.next();
			if (userDao.isTrueUserName(username) == 1) {
				System.out.println("该用户名已经存在,建议您使用：" + userDao.adviseUsername(username));

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
				System.out.println("两次密码不一致，请再次输入");
			} else {
				break;
			}
		}

		System.out.println("请输入您的支付密码：");
		String payPassword = input.next();

		String tel;
		while (true) {
			System.out.print("请输入您的手机号：");
			tel = input.next();
			if (userDao.isTelExist(tel)) {
				break;
			} else {
				System.out.println("该手机号已经存在");
			}
		}

		int register = userDao.register(username, password, tel, payPassword);

		if (register == 1) {
			int uid = userDao.queryUserId(username);
			User u = userDao.queryByUserId(uid);
			awardRe(uid, u.getWalletID());
			System.out.println("注册成功，是否登录？");
			String s = input.next();
			if (s.equals("y")) {
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
		input.close();
		System.out.println("您已经退出系统，没有后悔的余地了");
		System.exit(0);
	}

	/**
	 * 返回到mainMenu
	 * 
	 * @Title returnMenu
	 */
	private void returnMenu() {

		System.out.println("输入y继续,任意键退出");
		String next = input.next();

		if (next.equals("y")) {
			if (user == null) {
				mainMenu();
				return;
			}
			if (user.isAdmin()) {
				adminMenu();// 管理员菜单
			} else {
				userMenu();// 普通用户菜单
			}
		} else {
			// isTrueInput(0, 2); // 0代表登录注册页面，1代表adimin页面，2代表普通用户
			exit();
		}
	}

	private void printBoundary() {
		System.out.println("----------------------------------");
	}

}
