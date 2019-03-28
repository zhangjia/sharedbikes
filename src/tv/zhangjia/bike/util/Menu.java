package tv.zhangjia.bike.util;

import java.util.Scanner;

import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
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
	}

	/**
	 * 普通用户主界面
	 */
	private void userMenu() {
		System.out.println("尊敬的" + user.getUsername() + "用户，您好！");
		System.out.println("\t1.查询单车");
		System.out.println("\t2.租借单车");
		System.out.println("\t3.归还单车");
		System.out.println("\t4.删除单车");
		System.out.println("\t5.个人信息");
		System.out.println("\t6.租赁记录");
		System.out.println("\t7.退出登录");
		System.out.println("\t8.退出系统");

	}

	/**
	 * 用户注册
	 * 
	 * @Title userRegister
	 */
	private void userRegister() {
		System.out.print("请输入您的用户名：");
		String username = input.next();
		System.out.print("请输入您的密码：");
		String password = input.next();
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
		System.out.println("输入1继续,任意键退出");
		String next = input.next();
		if(next.equals("1")) {
			mainMenu();
		} else {
			exit();
		}
	}

}
