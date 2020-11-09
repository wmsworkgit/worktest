package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import com.mysql.jdbc.Statement;

/**
 * @author Michael
 *
 */
public class mysqlcon {
	static Connection conn = null;
	static PreparedStatement pst = null;

	public static Connection getcon() {
		Connection conn = null;
		try {
			// 得到连接
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/tp5", "root", "root");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return conn;

	}

	/**
	 * 增删改
	 * 
	 * @param sql
	 * @param data
	 * @return boolean true为执行成功 false为执行失败
	 */
	public static boolean dml(String sql, Object... data) {
		Connection conn = getcon();
		PreparedStatement pst = null;
		try {
			// 得到连接对象 传入sql
			pst = conn.prepareStatement(sql);
			// 对站位符进行赋值
			for (int i = 0; i < data.length; i++) {
				pst.setObject(i + 1, data[i]);
			}
			// 执行sql
			int i = pst.executeUpdate();
			if (i > 0) {

				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 释放资源
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	/**
	 * @param String
	 *            sql
	 * @param Object
	 *            data 可选参数
	 * @return int 返回自增主键id
	 */
	public static int retPk(String sql, Object... data) {
		Connection conn = getcon();
		PreparedStatement pst = null;
		ResultSet rest = null;
		try {
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < data.length; i++) {
				pst.setObject(i + 1, data[i]);
			}
			int i = pst.executeUpdate();
			if (i > 0) {
				rest = pst.getGeneratedKeys();
				while (rest.next()) {
					return rest.getInt(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 释放资源
			try {
				pst.close();
				conn.close();
				rest.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;

	}

	// 查
	/**
	 * @param String
	 *            sql
	 * @param Object
	 *            data
	 * @return ResultSet
	 */
	public static ResultSet dql(String sql, Object... data) {
		conn = getcon();
		PreparedStatement pst = null;
		try {

			pst = conn.prepareStatement(sql);
			for (int i = 0; i < data.length; i++) {
				pst.setObject(i + 1, data[i]);
			}
			return pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// 关闭连接
	public static void close() {
		try {
			conn.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(getcon());
	}

}
