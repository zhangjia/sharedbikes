package tv.zhangjia.bike.util;

import java.util.List;
import java.util.Scanner;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.RecordDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.dao.impl.LeaseRecordDaoImpl;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.User;

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
	private UserDao userdao = new UserDaoImpl();
	private BikeDao bikedao = new BikeDaoImpl();
	private RecordDao<LeaseRecord> leaseRecordDao = new LeaseRecordDaoImpl();

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
		int choose = input.nextInt();
		switch (choose) {
		case 1:
			userLogin();
			break;
		case 2:
			userRegister();
			break;
		case 3:
			exit();
			break;
		}
		returnMenu();
	}

	/**
	 * 用户登录
	 */
	private void userLogin() {
		System.out.print("请输入您的用户名：");
		String username = input.next();
		System.out.print("请输入您的密码：");
		String password = input.next();
		User login = userdao.login(username, password);

		if (login == null) {
			System.out.println("登录失败，您的用户名或者密码错误");
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
		int nextInt = input.nextInt();
		switch (nextInt) {
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

		}

	}

	private void logout() {
//		user = null;
		mainMenu();

	}

	private void leaseRecord() {
		if(user.isAdmin()) {
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
		// TODO Auto-generated method stub

	}

	/**
	 * 根据ID删除单车
	 */
	private void deleteBike() {
		System.out.println("请输入您要删除的单车ID：");
		int id = input.nextInt();

		if (bikedao.queryById(id) == null) {
			System.out.println("不存在此id");

		} else {
			if (bikedao.doDelete(id)) {
				System.out.println("删除成功");
			} else {
				System.out.println("删除失败");
			}
		}
		returnMenu();
	}

	/**
	 * 根据ID修改单车信息
	 */
	private void editBike() {
		System.out.println("请输入您要修改的单车ID");
		int id = input.nextInt();
		System.out.println("请输入单车类型：");
		String type = input.next();
		System.out.println("请输入价格：");
		double price = input.nextDouble();
		System.out.println("请输入位置：");
		String location = input.next();
		System.out.println("请输入状态：");
		int status = input.nextInt();
		System.out.println("请输入次数：");
		int amount = input.nextInt();
		System.out.println("请输入qr");
		String qr = input.next();

		Bike bike = bikedao.queryById(id);
		if (bike == null) {
			System.out.println("没有该ID");
		} else {
			Bike bike2 = new Bike(id,type, price, location, status, amount, qr);
			boolean doUpdate = bikedao.doUpdate(bike2);
			if (doUpdate) {
				System.out.println("修改成功");
			} else {
				System.out.println("修改失败");
			}
		}

		returnMenu();

	}

	/**
	 * 添加单车
	 */
	private void saveBike() {
		System.out.println("添加单车");
		System.out.println("请输入单车类型：");
		String type = input.next();
		System.out.println("请输入价格：");
		double price = input.nextDouble();
		System.out.println("请输入位置：");
		String location = input.next();
		System.out.println("请输入qr");
		String qr = input.next();

		Bike bike = new Bike(type, price, location, 1, 0, "二维码");
		boolean doInsert = bikedao.doInsert(bike);
		if (doInsert) {
			System.out.println("添加成功");
		} else {
			System.out.println("添加失败");
		}

		System.out.println();
		returnMenu();
	}

	/**
	 * 查询所有的单车
	 */
	private void queryBike() {
		/*System.out.println("下面是所有的单车");
		List<LeaseRecord> leaseRecordDaos = leaseRecordDao.queryAll();
		System.out.println("编号\t类型\t价格\t位置\t状态\t次数\t二维码");
		for (LeaseRecord record : leaseRecordDaos) {
			System.out.println(record);
		}
		returnMenu();*/
		
		System.out.println("下面是所有的单车");
		List<Bike> bike = bikedao.queryAll();
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
		System.out.println("尊敬的" + user.getUsername() + "用户，您好！");
		System.out.println("\t1.查询单车");
		System.out.println("\t2.租借单车");
		System.out.println("\t3.归还单车");
		System.out.println("\t4.个人信息");
		System.out.println("\t5.充值金额");
		System.out.println("\t6.租赁记录");
		System.out.println("\t7.个人设置"); // TODO 可以选择修改个人信息，还是其他设置
		System.out.println("\t8.退出登录");
		System.out.println("\t9.退出系统");
		System.out.print("请选择您接下来的操作:");
		int nextInt = input.nextInt();
		switch (nextInt) {
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
			recharge();
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
		}

	}

	private void Setting() {
		// TODO Auto-generated method stub
		
	}



	private void recharge() {
		// TODO Auto-generated method stub
		
	}

	private void returnBike() {
		System.out.println("请输入您要归还的单车Id");
		int id = input.nextInt();
		int result = bikedao.doReturn(id);
		
		if(result == 1) {
			System.out.println("归还成功！");
			userMenu();
		} else if(result == 0) {
			System.out.println("ID不存在");
			userMenu();
		} else {
			System.out.println("该ID状态不可借");
			userMenu();
		}
	}

	private void leaseBike() {
		System.out.println("请输入您要租借的单车ID：");
		int id = input.nextInt();
		int result = bikedao.doLease(id,user);
		if(result == 1) {
			System.out.println("借出成功！");
			userMenu();
		} else if(result == 0) {
			System.out.println("ID不存在");
			userMenu();
		} else {
			System.out.println("该ID状态不可借");
			userMenu();
		}
	}

	/**
	 * 用户注册
	 * 
	 * @Title userRegister
	 */
	private void userRegister() {
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

		int register = userdao.register(username, password);

		if (register == -1) {
			System.out.println("注册失败，您的用户名已经存在");
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
			if(user == null ) {
				mainMenu();
				return;
			}
			if (user.isAdmin()) {
				adminMenu();// 管理员菜单
			} else {
				userMenu();// 普通用户菜单
			}
		} else {
			isTrueInput(0,2); //0代表登录注册页面，1代表adimin页面，2代表普通用户
		}
	}

	
	public void isTrueInput(int i, int j) {
		switch(i) {
		case 1:
			System.out.println("确定退出？（y/n）不是手滑？");
			String y = input.next();
			// 不区分大小写
			if (y.equalsIgnoreCase("y")) {
				System.out.println("感谢您的使用，再见！");
				System.exit(0);
			}  else if (j == 1) {
				mainMenu();
			} else if (j == 2) {
				adminMenu();
			} else if (j == 3) {
				userMenu();
			}
		}
	}
}
