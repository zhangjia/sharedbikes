package tv.zhangjia.bike.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC的工具类，负责：加载驱动，建立连接，释放资源
 *
 */
public class JDBCUtils {

	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private static final String USER = "bike";
	private static final String PASSWORD = "z";

	static {
		// 因为驱动只需要加载一次，所以在静态语句块中进行加载
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立和数据库的连接
	 * 
	 * @return 连接
	 */
	public /*static*/ Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 释放资源
	 * 
	 * @param rs
	 *            结果集
	 * @param stm
	 *            语句对象
	 * @param conn
	 *            连接
	 */
	public /*static*/ void close(ResultSet rs, Statement stm, Connection conn) {
		try {
			// 关闭结果集
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭语句对象
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					// 关闭连接
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 释放资源
	 * 
	 * @param stm
	 *            语句对象
	 * @param conn
	 *            连接
	 */
	public /*static*/ void close(Statement stm, Connection conn) {
		close(null, stm, conn);
	}

}
