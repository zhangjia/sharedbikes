package tv.zhangjia.bike.util;

import java.util.List;
import java.util.Scanner;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.RecordDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.dao.impl.LeaseRecordDaoImpl;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.dao.impl.WalletDaoImpl;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.User;
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

	/**
	 * 用户登录
	 */
	private void userLogin() {
		System.out.println("-----------------------------------");
		System.out.print("请输入您的用户名：");
		String username = input.next();
		System.out.print("请输入您的密码：");
		String password = input.next();
		User login = userDao.login(username, password);

		if (login == null) {
			System.out.println("登录失败，您的用户名或者密码错误");
			System.out.println("是否重新登录？y");
			String againLogIn = input.next();
			if (againLogIn.equals("y")) {
				userLogin();

			} else {
				mainMenu();
			}
		} else {
			this.user = login;
			if (user.isAdmin()) {
				adminMenu();
			} else {
				userMenu();
			}
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
		System.out.println("\t5.用户信息");
		System.out.println("\t6.租赁记录");
		System.out.println("\t7.退出登录");
		System.out.println("\t8.退出系统");
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
					personInfo();
					break;
				case 6:
					leaseRecord();
					break;
				case 7:
					logout();// 退出登录
					break;
				case 8:
					exit();// 退出
					break;
				default:
					System.out.print("没有该选项，请重新输入：");
				}
			} else {
				System.out.println("输入不合法，请重新输入：");
			}
		}

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

	private void personInfo() {
		System.out.println("下面是您的个人信息");
		User user = userDao.queryByUserId(this.user.getId());
		System.out.println("编号\t用户名\t用户手机号\t骑行时间\t注册时间");
		System.out.println(user);
		returnMenu();

	}

	/**
	 * 根据ID删除单车
	 */
	private void deleteBike() {
		System.out.println("-----------------------------------");
		System.out.println("请输入您要删除的单车ID：");
		int id = input.nextInt();

		if (bikeDao.queryById(id) == null) {
			System.out.println("不存在此id");

		} else {
			if (bikeDao.doDelete(id)) {
				System.out.println("删除成功");
			} else {
				System.out.println("删除失败");
			}
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
		System.out.println("-----------------------------------");
		System.out.println("请输入您要修改的单车ID");
		int id = input.nextInt();
		System.out.println("请输入单车类型：");
		String type = input.next();
		System.out.println("请输入价格：");
		double price = input.nextDouble();
		System.out.println("请输入位置ID：");
		int locationId = input.nextInt();
		System.out.println("请输入状态：");
		int status = input.nextInt();
		System.out.println("请输入次数：");
		int amount = input.nextInt();
		System.out.println("请输入qr");
		String qr = input.next();

		Bike bike = bikeDao.queryById(id);
		if (bike == null) {
			System.out.println("没有该ID");
		} else {
			Bike bike2 = new Bike(id, type, price, locationId, status, amount, qr);
			boolean doUpdate = bikeDao.doUpdate(bike2);
			if (doUpdate) {
				System.out.println("修改成功");
			} else {
				System.out.println("修改失败");
			}
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
		System.out.println("-----------------------------------");
		System.out.println("添加单车");
		System.out.println("请输入单车类型：");
		String type = input.next();
		System.out.println("请输入价格：");
		double price = input.nextDouble();
		System.out.println("请输入位置：");
		int locationId = input.nextInt();
		System.out.println("请输入qr");
		String qr = input.next();

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
		System.out.println("-----------------------------------");
		/*
		 * System.out.println("下面是所有的单车"); List<LeaseRecord> leaseRecordDaos =
		 * leaseRecordDao.queryAll(); System.out.println("编号\t类型\t价格\t位置\t状态\t次数\t二维码");
		 * for (LeaseRecord record : leaseRecordDaos) { System.out.println(record); }
		 * returnMenu();
		 */

		System.out.println("下面是所有的单车");
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
		System.out.println("-----------------------------------");
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
					personInfo();
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
				default:
					System.out.print("没有该选项，请重新输入：");
				}
			} else {
				System.out.println("输入不合法，请重新输入：");
			}
		}

	}

	private void recharge() {
		while (true) {
			System.out.println("请输入充值金额：");
			String money = input.next();
			if (iiv.isDouble(money)) {
				double m = Double.parseDouble(money);
				if(walletDao.recharge(user.getWalletID(), m) == 1) {
				System.out.println("充值成功");
				personWallet();
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
		Wallet wallet = walletDao.queryByUserId(user.getId());
		System.out.println("用户编号\t用户余额\t优惠券余额\t用户等级\tVIP时间");
		System.out.println(wallet);
		System.out.println("X：消费记录\t C：充值");
		String s = input.next();
		if (s.equals("x")) {

		} else if (s.equals("c")) {
			recharge();
		} else {

			userMenu();
		}
	}

	private void Setting() {
		// TODO Auto-generated method stub

	}

	private void returnBike() {
		System.out.println("-----------------------------------");
		System.out.println("请输入您要归还的单车Id");
		int bikeId = input.nextInt();
		int result = leaseRecordDao.returnBike(bikeId, user.getId());

		if (result == 1) {
			System.out.println("归还成功");
			userMenu();
		} else if (result == 0 || result == 11) {
			System.out.println("您未租借该单车");
			userMenu();
		} else {
			System.out.println("该ID不存在");
			userMenu();
		}
	}

	private void leaseBike() {
		System.out.println("-----------------------------------");
		System.out.println("请输入您要租借的单车ID：");
		int bikeId = input.nextInt();
		int result = leaseRecordDao.doInsert(user.getId(), bikeId);
		if (result == 1) {
			System.out.println("借出成功！");
			userMenu();
		} else if (result == 10 ) {
			System.out.println("该车辆已经被租出");
			userMenu();
		} else {
			System.out.println("该车辆ID不存在");
			userMenu();
		}
	}

	/**
	 * 用户注册
	 * 
	 * @Title userRegister
	 */
	private void userRegister() {
		System.out.println("-----------------------------------");
		System.out.print("请输入您的用户名：");
		String username = input.next();
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

		int register = userDao.register(username, password);

		if (register == -1) {
			System.out.println("注册失败，您的用户名已经存在");
			System.out.println("是否重新注册？");
			String againregister = input.next();
			if (againregister.equals("y")) {
				userRegister();

			} else {
				mainMenu();
			}
		} else {
			System.out.println("注册成功");
			userLogin();
		}
	}

	/**
	 * 用户退出系统
	 * 
	 * @Title exit
	 */
	private void exit() {
		System.out.println("-----------------------------------");
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

	public <T> T isTrueInput(T a) {

		return a;

	}
}
