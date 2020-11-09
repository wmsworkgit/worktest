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
			// �õ�����
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
	 * ��ɾ��
	 * 
	 * @param sql
	 * @param data
	 * @return boolean trueΪִ�гɹ� falseΪִ��ʧ��
	 */
	public static boolean dml(String sql, Object... data) {
		Connection conn = getcon();
		PreparedStatement pst = null;
		try {
			// �õ����Ӷ��� ����sql
			pst = conn.prepareStatement(sql);
			// ��վλ�����и�ֵ
			for (int i = 0; i < data.length; i++) {
				pst.setObject(i + 1, data[i]);
			}
			// ִ��sql
			int i = pst.executeUpdate();
			if (i > 0) {

				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ͷ���Դ
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
	 *            data ��ѡ����
	 * @return int ������������id
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
			// �ͷ���Դ
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

	// ��
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

	// �ر�����
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
