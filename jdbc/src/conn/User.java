package conn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	
	public void selectData(){
		String sql = "select * from tp_user where user_id = ?";
		
		Object[] objData = {"0001"};
		ResultSet res = mysqlcon.dql(sql,objData);
		try {
			while(res.next()){
				System.out.print(res.getString("USER_ID")+"  ");
				System.out.print(res.getString("USER_NAME")+"  ");
				System.out.print(res.getString("USER_PASSWORD")+"  ");
				System.out.print(res.getString("USER_EMAIL")+"  ");
				System.out.print(res.getInt("AUTH_TYPE")+"  ");
				System.out.print(res.getInt("IS_DELETE")+"  ");
				System.out.print(res.getString("CREATED_AT")+"  ");
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				mysqlcon.close();
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
	}
	public void insertData(){
		String sql = "insert into tp_user (USER_ID,USER_NAME,USER_PASSWORD,USER_EMAIL,CREATED_AT) values(?,?,MD5(?),?,now())";
		Object[] objData = {"0007","王二麻子","123456","2521@qq.com"};
		boolean bool = mysqlcon.dml(sql,objData);
		System.out.println( bool);
	}
	
	public void insertReturnPk(){
		String sql = "insert into tp_book (name,content,pice,created_at,update_at) values(?,?,?,now(),now())";
		Object[] objData = {"mybatis","持久层框架","130.25",};
		int pk = mysqlcon.retPk(sql, objData);
		System.out.println(pk);
	}
	public static void main(String[] args) {
		User user = new User();
		user.insertReturnPk();
	}
	
}
