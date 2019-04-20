package tv.zhangjia.bike.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用的DAO，可以对任意表做增删改查
 *
 */
public abstract class CommonDao<T> extends JDBCUtils{

	/**
	 * 对任意表做增删改操作
	 * @return
	 */
	public int executeUpdate(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			//获取连接
			conn = getConnection();
			//创建语句对象
			pstm = conn.prepareStatement(sql);
			if(params != null && params.length > 0) {
				//需要设置占位符
				for(int i = 0; i < params.length; i++) {
					//设置占位符
					pstm.setObject(i+1, params[i]);
				}
			}
			//执行
			int i = pstm.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//释放资源
			close(pstm, conn);
		}
		return 0;
	}
	
	public List<T> query4BeanList(String sql, Object... params){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<>();
		try {
			//获取连接
			conn = getConnection();
			//创建语句对象
			pstm = conn.prepareStatement(sql);
			if(params != null && params.length > 0) {
				//需要设置占位符
				for(int i = 0; i < params.length; i++) {
					//设置占位符
					pstm.setObject(i+1, params[i]);
//					pstm.setDate(parameterIndex, x);
				}
			}
			//执行SQL语句
			rs = pstm.executeQuery();
			//处理结果
			while(rs.next()) {
				T t = getBeanFromResultSet(rs);
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public T query4Bean(String sql, Object... params) {
		List<T> list = query4BeanList(sql, params);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public abstract T getBeanFromResultSet(ResultSet rs) throws SQLException;
	
	public int queryCurrentId(String sql) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<>();
		try {
			//获取连接
			conn = getConnection();
			//创建语句对象
			pstm = conn.prepareStatement(sql);
			
			//执行SQL语句
			rs = pstm.executeQuery();
			//处理结果
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
